------------------------------------------------
-- Export file for user BA                    --
-- Created by a70348 on 2019/4/1, 下午 04:07:18 --
------------------------------------------------

set define off
spool 1080401PKG_BA_DATA.log

prompt
prompt Creating package PKG_BA_DATA
prompt ============================
prompt
CREATE OR REPLACE PACKAGE BA.PKG_BA_DATA
AUTHID DEFINER IS
   PROCEDURE SP_BA_MOVETAB(p_table_name IN VARCHAR2);
   PROCEDURE SP_BA_BACKUPTAB(
      p_table_name IN VARCHAR2,
      p_clause  IN VARCHAR2);

END;
/

prompt
prompt Creating package body PKG_BA_DATA
prompt =================================
prompt
CREATE OR REPLACE PACKAGE BODY BA.PKG_BA_DATA IS
  -- ============================================================================
  -- 程式目的：整理TABLE程式
  -- 撰寫人員：Justin Yu
  -- 修改紀錄：
  -- 維護人員        日期    說明
  -- -----------   --------  ----------------------------------------------------
  -- Justin Yu     20190314  v 1.0
  --
  -- ============================================================================
  -- ============================================================================
  -- PROCEDURE & FUNCTION AREA
    --------------------------------------------------------------------------------
    v_jobno        VARCHAR2(20) DEFAULT null;
    --===================================================================================================================================
    FUNCTION get_time(p_starttime TIMESTAMP)
      RETURN VARCHAR2
    IS
    BEGIN
      DECLARE
        v_spdtime   VARCHAR2(200);
      BEGIN
        v_spdtime := FN_BA_GETSPENDTIME(TO_CHAR(p_starttime   ,'YYYYMMDDHH24MISS'),
                                        TO_CHAR(SYSTIMESTAMP  ,'YYYYMMDDHH24MISS'));
        RETURN v_spdtime;

      EXCEPTION
        WHEN OTHERS THEN
          RETURN '--';
      END;
    END get_time;
    --===================================================================================================================================

    --===================================================================================================================================
 		PROCEDURE get_jobno
    IS
    BEGIN
       SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_jobno FROM DUAL;
    END get_jobno;
    --===================================================================================================================================

    --===================================================================================================================================
    PROCEDURE create_or_insert_table (
      p_table_name       VARCHAR2,
      create_table_query VARCHAR2,
      insert_table_query VARCHAR2) IS
    n  NUMBER;
    BEGIN
    -- ============================================================================
       SELECT COUNT(*) INTO n FROM user_tables WHERE table_name = UPPER(p_table_name);

       IF (n = 0) THEN
         EXECUTE IMMEDIATE create_table_query;
         SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'create_or_insert_table',
                        p_step       => 'CREATE TABLE ' || p_table_name,
                        p_memo       => 'SQL:[' || create_table_query || '] Insert:[' || TO_CHAR(SQL%ROWCOUNT) || '] rows');
       ELSE
         EXECUTE IMMEDIATE insert_table_query;
         SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'create_or_insert_table',
                        p_step       => 'INSERT INTO ' || p_table_name,
                        p_memo       => 'SQL:[' || insert_table_query || '], Insert:[' || TO_CHAR(SQL%ROWCOUNT) || '] rows');
       END IF;
    END create_or_insert_table;
    --===================================================================================================================================


    --===================================================================================================================================
    PROCEDURE delete_table (
      p_table_name       VARCHAR2,
      delete_table_query VARCHAR2) IS
    BEGIN
    -- ============================================================================
       EXECUTE IMMEDIATE delete_table_query;
       SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                      p_job_id     => 'delete_table',
                      p_step       => 'DELETE ' || p_table_name,
                      p_memo       => 'SQL:[' || delete_table_query || '] , Delete:[' || TO_CHAR(SQL%ROWCOUNT) || '] rows');

    END delete_table;
    --===================================================================================================================================

    --===================================================================================================================================
    PROCEDURE backup_table (
      p_table_name   VARCHAR2,
      p_str          VARCHAR2)   IS
    BEGIN
    -- ============================================================================
    DECLARE
          v_starttime                TIMESTAMP;
          v_tabname_h                VARCHAR2(50);
          v_create_table_sql         VARCHAR2(500);
          v_insert_table_sql         VARCHAR2(500);
          v_delete_table_sql         VARCHAR2(500);
    -- ============================================================================
    --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
    -- ============================================================================
    BEGIN
        v_starttime := SYSTIMESTAMP;
        v_tabname_h := p_table_name || '_H';
        v_create_table_sql := 'CREATE TABLE BA.' || v_tabname_h || ' AS SELECT * FROM BA.' || p_table_name || ' ' || p_str;
        v_insert_table_sql := 'INSERT INTO BA.' || v_tabname_h || ' VALUE SELECT * FROM BA.' || p_table_name || ' ' || p_str;
        v_delete_table_sql := 'DELETE FROM BA.' || p_table_name ||  ' ' || p_str;

        SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'backup_table',
                        p_step       => 'BEGIN',
                        p_memo       => '執行開始:[' || p_table_name ||']');

        create_or_insert_table(v_tabname_h , v_create_table_sql, v_insert_table_sql);
        delete_table(p_table_name, v_delete_table_sql);


        SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'backup_table',
                        p_step       => 'END BACKUP',
                        p_memo       => '結束 BACKUP。執行時間：' || get_time(v_starttime));

    END;

    EXCEPTION
     WHEN OTHERS THEN
        SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'backup_table',
                        p_step       => 'EXCEPTION',
                        p_memo       => 'v_table_name：' || p_table_name || '；錯誤：' || SQLERRM || DBMS_UTILITY.FORMAT_ERROR_BACKTRACE );

    END backup_table;

  --===================================================================================================================================



  --===================================================================================================================================
    PROCEDURE SP_BA_MOVETAB(p_table_name IN VARCHAR2)   IS
    BEGIN
    -- ============================================================================
    DECLARE
          v_starttime    TIMESTAMP;
          v_table_name   VARCHAR2(200);
          v_str          VARCHAR2(2000);
    -- ============================================================================
    -- CURSOR AREA
    --------------------------------------------------------------------------------
        --取得INDEX_NAME
        CURSOR c_ind IS
           SELECT DISTINCT INDEX_NAME
             FROM USER_IND_COLUMNS
            WHERE UPPER(TABLE_NAME) = v_table_name
            ORDER BY 1;
    -- ============================================================================
    -- 程式目的：搬檔分析程式
    -- 撰寫人員：Justin Yu
    -- 修改紀錄：
    -- 維護人員        日期    說明
    -- -----------   --------  ----------------------------------------------------
    -- Justin Yu     20190314  v 1.0
    -- ============================================================================
    --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
    -- ============================================================================
    BEGIN
        v_table_name := UPPER(trim(p_table_name));

        SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'SP_BA_MOVETAB',
                        p_step       => 'BEGIN',
                        p_memo       => '執行開始' || v_table_name);

        -- TABLE MOVE
        v_starttime := SYSTIMESTAMP;

        v_str := 'ALTER TABLE BA.' || v_table_name || ' MOVE ';

        SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'SP_BA_MOVETAB',
                        p_step       => 'BEGIN MOVE',
                        p_memo       => '開始 MOVE SQL：[' || v_str || ']');

        EXECUTE IMMEDIATE (v_str);

        SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'SP_BA_MOVETAB',
                        p_step       => 'END MOVE',
                        p_memo       => '結束 MOVE。執行時間：' || get_time(v_starttime));

        -- REBUILD INDEX
        FOR r_ind IN c_ind LOOP

          v_starttime := SYSTIMESTAMP;

          v_str := 'ALTER INDEX BA.' || r_ind.index_name || ' REBUILD ';

          SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                          p_job_id     => 'SP_BA_MOVETAB',
                          p_step       => 'BEGIN REBUILD',
                          p_memo       => '開始 REBUILD SQL：[' || v_str || ']');

          EXECUTE IMMEDIATE (v_str);

          SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                          p_job_id     => 'SP_BA_MOVETAB',
                          p_step       => 'END REBUILD',
                          p_memo       => '結束 REBUILD。執行時間：' || get_time(v_starttime));

        END LOOP;

        -- TABLE 分析
        SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'SP_BA_MOVETAB',
                        p_step       => 'BEGIN ANALYZE',
                        p_memo       => '開始 ANALYZE：' || v_table_name);

        v_starttime := SYSTIMESTAMP;

        DBMS_STATS.GATHER_TABLE_STATS('BA',v_table_name);

        SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'SP_BA_MOVETAB',
                        p_step       => 'END ANALYZE',
                        p_memo       => '結束 ANALYZE。執行時間：' || get_time(v_starttime));

        SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'SP_BA_MOVETAB',
                        p_step       => 'END',
                        p_memo       => '執行結束' || v_table_name);

    END;

    EXCEPTION
     WHEN OTHERS THEN
        SP_BA_RECJOBLOG (p_job_no     => v_jobno,
                        p_job_id     => 'SP_BA_MOVETAB',
                        p_step       => 'EXCEPTION',
                        p_memo       => 'p_table_name：' || p_table_name || '；錯誤：' || SQLERRM || DBMS_UTILITY.FORMAT_ERROR_BACKTRACE );

    END SP_BA_MOVETAB;

    --===================================================================================================================================


    --===================================================================================================================================
    PROCEDURE SP_BA_BACKUPTAB (
      p_table_name VARCHAR2,
      p_clause  VARCHAR2)        IS
          v_table_name               VARCHAR2(50);
    BEGIN
    -- ============================================================================
    DECLARE
          v_str                      VARCHAR2(500);
          v_Year                     VARCHAR2(10);
    -- ============================================================================
    -- PARAMETER(IN):  *p_table_name     (varChar2)       --欲搬檔備份之table名稱
    --                 *p_clause         (varChar2)       --欲搬檔備份之WHERE條件SQL
    --
    -- 程式目的：勞保年金資料庫備份與搬檔分析程式
    -- 撰寫人員：Justin Yu
    -- 修改紀錄：
    -- 維護人員        日期    說明
    -- -----------   --------  ----------------------------------------------------
    -- Justin Yu     20190314  v 1.0
    --1.若有輸入參數,則執行該參數的table搬檔備份
    --2.若無輸入參數,則執行Default的搬檔備份:5個table做備份與搬檔分析:
    --  BADAPR          --核定檔
    --  BAGIVEDTL       --給付入帳媒體明細錄檔
    --  BAGIVETMPDTL    --給付媒體明細錄檔
    --  BAPAY           --核付檔
    --  BAPAYRPTRECORD  --報表檔
    --3.未來若有固定需要搬檔備份的talbe,可在此設定
    -- ============================================================================
    --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
    -- ============================================================================
    BEGIN
        get_jobno;
        ------若有輸入table name與搬檔條件SQL,則執行該table的搬檔備份--------------------
        IF trim(p_table_name) IS NOT NULL  THEN
           v_table_name   := p_table_name;
           IF trim(p_clause) IS NOT NULL THEN
              v_str := p_clause ;
           END IF;
           --A.備份資料到歷史區--
           backup_table(v_table_name, v_str);
           --B.再做搬檔分析--
           SP_BA_MOVETAB(v_table_name);

        ELSE
        ------若無輸入table name與搬檔條件SQL,則執行Default的搬檔備份--------------------
        ------1.BADAPR 核定檔------------------------------------------------------------
            v_table_name   := 'BADAPR';

            --A.備份資料到歷史區的條件:核定檔只留1年的日編審資料,月試核與月核定資料不刪除--
            SELECT TO_CHAR(add_months(sysdate,-12), 'YYYY') into v_Year FROM DUAL;
            v_str := 'WHERE MTESTMK = ''D''  AND ISSUYM < ''' || v_Year || '01''' ;

            backup_table(v_table_name, v_str);

            --B.再做搬檔分析--
            SP_BA_MOVETAB(v_table_name);
        ---------------------------------------------------------------------------------------------------

        ------2.BAGIVEDTL 給付入帳媒體明細錄檔-------------------------------------------------------------
            v_table_name   := 'BAGIVEDTL';

            --A.備份資料到歷史區的條件:給付入帳媒體明細錄檔只留5年的資料--
            SELECT TO_CHAR(add_months(sysdate,-60), 'YYYY') into v_Year FROM DUAL;
            v_str := 'WHERE COMPAREMK IS NOT NULL AND ISSUYM2 < ''' || fn_BA_transDateValue(v_Year||'01', '2')|| '''' ;

            backup_table(v_table_name, v_str);

            --B.再做搬檔分析--
            SP_BA_MOVETAB(v_table_name);
        ----------------------------------------------------------------------------------------

        ------3.BAGIVETMPDTL 給付媒體明細錄檔---------------------------------------------------
            v_table_name   := 'BAGIVETMPDTL';

            --A.備份資料到歷史區的條件:給付媒體明細錄檔只留5年的資料--
            SELECT TO_CHAR(add_months(sysdate,-60), 'YYYY') into v_Year FROM DUAL;
            v_str := 'WHERE MFILEREMK = ''1'' AND ISSUYM2 < ''' ||  fn_BA_transDateValue(v_Year||'01', '2')|| '''' ;

            backup_table(v_table_name, v_str);

            --B.再做搬檔分析--
            SP_BA_MOVETAB(v_table_name);
        ----------------------------------------------------------------------------------------

        ------4.BAPAY 核付檔--------------------------------------------------------------------
            v_table_name   := 'BAPAY';

            --A.備份資料到歷史區的條件:核付檔只留5年的資料--
            SELECT TO_CHAR(add_months(sysdate,-60), 'YYYY') into v_Year FROM DUAL;
            v_str := 'WHERE ISSUYM < ''' || v_Year || '01''' ;

            backup_table(v_table_name, v_str);

            --B.再做搬檔分析--
            SP_BA_MOVETAB(v_table_name);
        ----------------------------------------------------------------------------------------

        ------5.BAPAYRPTRECORD 報表檔-----------------------------------------------------------
            v_table_name   := 'BAPAYRPTRECORD';

            --A.備份資料到歷史區的條件:BAPAYRPTRECORD只留5年的資料--
            SELECT TO_CHAR(add_months(sysdate,-60), 'YYYY') into v_Year FROM DUAL;
            v_str := 'WHERE ISSUYM < ''' || v_Year || '01''' ;

            backup_table(v_table_name, v_str);

            --B.再做搬檔分析--
            SP_BA_MOVETAB(v_table_name);
        ----------------------------------------------------------------------------------------
        END IF;

    END;
    END SP_BA_BACKUPTAB;
    --===================================================================================================================================


END PKG_BA_DATA;
/


spool off
