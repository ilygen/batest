CREATE OR REPLACE PACKAGE BA.PKG_BA_ANALYZE_TABLE 
AUTHID DEFINER AS

  PROCEDURE SP_ANALYZE( p_target_in IN VARCHAR2,
                        p_move_in  IN CHAR,
                        p_rebuild_in IN CHAR,
                        p_stats_in IN CHAR,
                        p_sleep_in  IN  PLS_INTEGER,
                        p_parel_in  IN PLS_INTEGER);  
/******************************************************************************
   程式目的:BA整檔作業(含rebuild index及分析)
   撰寫人員:70186
   執行方式:於線上驅動執行
   p_target_in  VARCHAR2(100 Char)  整檔標的(需為BA SCHEMA下的TABLE，多檔以全形逗號分隔)
   p_move_in    CHAR(1)             是否整檔(Y：N)
   p_rebuild_in CHAR(1)             是否重建索引(Y：N)
   p_stats_in   CHAR(1)             是否重新分析(Y：N)
   p_sleep_in   PLS_INTEGER         設定延遲時間(單位:分鐘)
   p_parel_in   PLS_INTEGER         設定PARELLEL數
   傳出參數:無
           
   相關 TABLE:
   I    PLOG            作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
   70186         2022/01/10  增加整檔、重建索引、重新分析設定,可分項執行
                             增加延遲作業時間(單位:分鐘)
 ******************************************************************************/
END PKG_BA_ANALYZE_TABLE;
/

CREATE OR REPLACE PACKAGE BODY BA.PKG_BA_ANALYZE_TABLE
AS
   g_owner CHAR(2) :='BA';
   g_parel VARCHAR2(30) := ' NOPARALLEL';
   PROCEDURE sp_move(
     p_rec_table_in IN USER_TABLES%ROWTYPE)
   IS
/******************************************************************************
   程式目的:BA整檔作業
   撰寫人員:70186
   執行方式:內部副程式
   傳入參數:p_rec_table_in  VARCHAR2(100 Char)  整檔標的(需為BA SCHEMA下的TABLE)
   傳出參數:無

   相關 TABLE:
   I    PLOG            作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2022/01/10  將整檔動作獨立
 ******************************************************************************/
     v_sql             VARCHAR2(300);
   BEGIN
     v_sql := 'ALTER TABLE ' || p_rec_table_in.TABLE_NAME || ' MOVE TABLESPACE '||p_rec_table_in.TABLESPACE_NAME;
     v_sql := v_sql || g_parel;
     pkg_plog.info('整檔中...，SQL：',v_sql);
     EXECUTE IMMEDIATE  v_sql;
   END sp_move;

   PROCEDURE sp_rebuild(
     p_rec_table_in IN USER_TABLES%ROWTYPE)
   IS
/******************************************************************************
   程式目的:BA重建索引
   撰寫人員:70186
   執行方式:內部副程式
   傳入參數:p_rec_table_in  VARCHAR2(100 Char)  整檔標的(需為BA SCHEMA下的TABLE)
   傳出參數:無

   相關 TABLE:
   I    PLOG            作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2022/01/10  將重建索引動作獨立
   70186         2022/01/19  當使用PARALLEL後,將Index調整回NOPARALLEL
 ******************************************************************************/
     v_sql             VARCHAR2(300);
   BEGIN
     FOR c_indexes IN (
         SELECT * FROM USER_INDEXES
         WHERE TABLE_OWNER = g_owner
         AND TABLE_NAME=p_rec_table_in.TABLE_NAME
         AND INDEX_TYPE ='NORMAL')
     LOOP
        BEGIN
         v_sql := 'ALTER INDEX '||c_indexes.TABLE_OWNER|| '.' || c_indexes.INDEX_NAME || ' REBUILD NOCOMPRESS '||
                      ' TABLESPACE '|| c_indexes.TABLESPACE_NAME;
         v_sql := v_sql || g_parel;
         pkg_plog.info('REBUILD INDEX...，SQL：',v_sql);
         EXECUTE IMMEDIATE    v_sql;
         
        EXCEPTION
         WHEN OTHERS THEN
           pkg_plog.error('REBUILD INDEX錯誤：錯誤代碼='||sqlcode||' ， '||'錯誤訊息='||SQLERRM,DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
           pkg_plog.info('5分鐘後會再嘗試一次REBUILD INDEX');
           DBMS_SESSION.SLEEP(5*60);
           pkg_plog.info('REBUILD INDEX...，SQL：',v_sql);
           EXECUTE IMMEDIATE    v_sql;
        END;
        IF (g_parel <> ' NOPARALLEL') THEN
          v_sql := 'ALTER INDEX '||c_indexes.TABLE_OWNER|| '.' || c_indexes.INDEX_NAME ||' NOPARALLEL';
          pkg_plog.info('ALTER INDEX NOPARALLEL...，SQL：',v_sql);
          EXECUTE IMMEDIATE    v_sql;
        END IF;
        
      END LOOP;

   END sp_rebuild;

   PROCEDURE sp_stats(
     p_rec_table_in IN USER_TABLES%ROWTYPE,
     p_degree_in IN PLS_INTEGER)
   IS
/******************************************************************************
   程式目的:BA分析作業
   撰寫人員:70186
   執行方式:內部副程式
   傳入參數:
   p_rec_table_in  VARCHAR2(100 Char)  整檔標的(需為BA SCHEMA下的TABLE)
   p_degree_in     PLS_INTEGER         並行數量
   傳出參數:無

   相關 TABLE:
   I    PLOG            作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2022/01/10  將重新分析動作獨立
 ******************************************************************************/
   BEGIN
     DBMS_STATS.GATHER_TABLE_STATS(g_owner,p_rec_table_in.TABLE_NAME,degree=>p_degree_in);
     pkg_plog.info('ANALYZE TABLE...，SQL：','DBMS_STATS.GATHER_TABLE_STATS(g_owner,p_rec_table_in.TABLE_NAME,degree=>p_degree_in)',
                            p_rec_table_in.TABLE_NAME||'/'||p_degree_in);
   END sp_stats;

   PROCEDURE sp_analyze(
        p_target_in IN VARCHAR2,
        p_move_in  IN CHAR,
        p_rebuild_in IN CHAR,
        p_stats_in IN CHAR,
        p_sleep_in  IN PLS_INTEGER,
        p_parel_in  IN PLS_INTEGER
   )
   IS
/******************************************************************************
   程式目的:BA整檔作業(含rebuild index及分析)
   撰寫人員:70186
   執行方式:於線上驅動執行
   傳入參數:
   p_target_in  VARCHAR2(100 Char)  整檔標的(需為BA SCHEMA下的TABLE，多檔以全形逗號分隔)
   p_move_in    CHAR(1)             是否整檔(Y：N)
   p_rebuild_in CHAR(1)             是否重建索引(Y：N)
   p_stats_in   CHAR(1)             是否重新分析(Y：N)
   p_sleep_in   PLS_INTEGER         設定延遲時間(單位:分鐘)
   p_parel_in   PLS_INTEGER         設定PARELLEL數
   傳出參數:無

   相關 TABLE:
   I    PLOG            作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
   70186         2022/01/10  增加整檔、重建索引、重新分析設定,可分項執行
                             增加延遲作業時間(單位:分鐘)
 ******************************************************************************/
  vc_procname CONSTANT VARCHAR2(31 BYTE) := 'PKG_BA_ANALYZE_TABLE.SP_ANALYZE';
  rec_table USER_TABLES%ROWTYPE;
  v_delay PLS_INTEGER :=0;
  v_degree PLS_INTEGER :=0;
  v_delay_msg VARCHAR2(30 CHAR):='再n分鐘後開始作業...';
  BEGIN
     --初始設定
      pkg_plog.sp_doinit(vc_procname);

      pkg_plog.info('ANALYZEBA整檔作業啟動，整檔標的：',p_target_in);

      pkg_plog.info('＊＊＊＊＊勞保年金給付　BA整檔作業＊＊＊＊＊');

      --提供延遲
      IF (p_sleep_in IS NOT NULL OR p_sleep_in >0 ) THEN
         v_delay := p_sleep_in;
         pkg_plog.info(REPLACE(v_delay_msg,'n',v_delay));
      END IF;

      WHILE v_delay >0
      LOOP
          DBMS_SESSION.SLEEP(60);
          v_delay :=  v_delay -1;
      END LOOP;

      --設定PARALLEL
      IF (p_parel_in > 0) THEN
        g_parel  := ' PARALLEL ' || p_parel_in;
        v_degree := p_parel_in;
      END IF;

      FOR c_tables IN (
            SELECT target,n FROM (
              SELECT DBMS_LOB.SUBSTR(REGEXP_SUBSTR(p_target_in, '[^，]+', 1, x.n)) AS target,x.n
              FROM DUAL a, (SELECT ROWNUM n FROM DUAL CONNECT BY ROWNUM < 10)  x)
            WHERE target IS NOT NULL
            ORDER BY n)
      LOOP
        pkg_plog.info('目前整檔標的：',c_tables.target);

        SELECT * INTO rec_table FROM USER_TABLES
        WHERE TABLE_NAME= c_tables.target;

        IF ( p_move_in = 'Y' ) THEN
           sp_move(rec_table);
        END IF;

        IF ( p_rebuild_in = 'Y' ) THEN
           sp_rebuild(rec_table);
        END IF;

        IF (p_stats_in = 'Y') THEN
          sp_stats(rec_table,v_degree);
        END IF;

        pkg_plog.info('．．．．．．．．．．．．．．．．．');
     END LOOP;
     pkg_plog.info('ANALYZE BA整檔作業完成');
  EXCEPTION
      WHEN OTHERS THEN
        pkg_plog.error('BA整檔作業失敗：錯誤代碼='||sqlcode||' ， '||'錯誤訊息='||SQLERRM,DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
  END sp_analyze;
END PKG_BA_ANALYZE_TABLE;
/
