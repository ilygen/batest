CREATE OR REPLACE PACKAGE BA.PKG_BA_ANALYZE_TABLE AS

  PROCEDURE SP_ANALYZE(p_target_in IN VARCHAR2);  
/******************************************************************************
   程式目的:BA整檔作業(含rebuild index及分析)
   撰寫人員:70186
   執行方式:於線上驅動執行
   傳入參數:p_target_in  VARCHAR2(100 Char)  整檔標的(需為BA SCHEMA下的TABLE，多檔以全形逗號分隔)
   傳出參數:無
           
   相關 TABLE:
   I    PLOG            作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
   70186         2021/06/18  防止REBUILD INDEX時遇到系統自建的LOB TYPE INDEX造成錯誤
                             並使單一INDEX REBUILD 失敗時，程式能繼續進行
 ******************************************************************************/
END PKG_BA_ANALYZE_TABLE;
/

CREATE OR REPLACE PACKAGE BODY BA.PKG_BA_ANALYZE_TABLE
AS
   PROCEDURE sp_analyze(
        p_target_in IN VARCHAR2
   )
   IS   
/******************************************************************************
   程式目的:BA整檔作業(含rebuild index及分析)
   撰寫人員:70186
   執行方式:於線上驅動執行
   傳入參數:p_target_in  VARCHAR2(100 Char)  整檔標的(需為BA SCHEMA下的TABLE，多檔以全形逗號分隔)
   傳出參數:無
           
   相關 TABLE:
   I    PLOG            作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/

    v_sql             VARCHAR2(300);
    vc_procname CONSTANT VARCHAR2(31 BYTE) := 'PKG_BA_ANALYZE_TABLE.SP_ANALYZE';

   BEGIN
     
     --初始設定
      pkg_plog.sp_doinit(vc_procname);

      pkg_plog.info('ANALYZEBA整檔作業啟動，整檔標的：',p_target_in);

      pkg_plog.info('＊＊＊＊＊勞保年金給付　BA整檔作業＊＊＊＊＊');      

      FOR c_tables IN (
        SELECT * FROM USER_TABLES
        WHERE table_name IN(
            SELECT DBMS_LOB.SUBSTR(REGEXP_SUBSTR(p_target_in, '[^，]+', 1, x.n)) AS target
　         FROM DUAL a, (SELECT ROWNUM n FROM DUAL CONNECT BY ROWNUM < 10)  x))
      LOOP  
        pkg_plog.info('目前整檔標的：',c_tables.TABLE_NAME);        
        v_sql := 'ALTER TABLE ' || c_tables.TABLE_NAME || ' MOVE TABLESPACE '||c_tables.TABLESPACE_NAME;        
        pkg_plog.info('整檔中...，SQL：',v_sql);        
        EXECUTE IMMEDIATE  v_sql;
        
        FOR c_indexes IN (
            SELECT * FROM USER_INDEXES
            WHERE TABLE_OWNER='BA'
            AND TABLE_NAME=c_tables.TABLE_NAME
            AND INDEX_TYPE ='NORMAL' )
        LOOP
          BEGIN
            v_sql := 'ALTER INDEX '||c_indexes.TABLE_OWNER|| '.' || c_indexes.INDEX_NAME || ' REBUILD NOCOMPRESS '||
                         ' TABLESPACE '|| c_indexes.TABLESPACE_NAME;
            pkg_plog.info('REBUILD INDEX...，SQL：',v_sql);
            EXECUTE IMMEDIATE    v_sql;
          EXCEPTION
            WHEN OTHERS THEN
              pkg_plog.error('REBUILD INDEX錯誤：錯誤代碼='||sqlcode||' ， '||'錯誤訊息='||SQLERRM,DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);            
          END;
        END LOOP;
        
        v_sql :='ANALYZE TABLE ' || c_tables.TABLE_NAME|| ' COMPUTE STATISTICS';
        pkg_plog.info('ANALYZE TABLE...，SQL：', v_sql);
        EXECUTE IMMEDIATE  v_sql;      
        pkg_plog.info('．．．．．．．．．．．．．．．．．');
        
      END LOOP;
      pkg_plog.info('ANALYZE BA整檔作業完成');

   EXCEPTION
      WHEN OTHERS THEN
        pkg_plog.error('BA整檔作業失敗：錯誤代碼='||sqlcode||' ， '||'錯誤訊息='||SQLERRM,DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
   END SP_ANALYZE;
END PKG_BA_ANALYZE_TABLE;
/
