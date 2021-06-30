CREATE OR REPLACE PROCEDURE BA.SP_BA_PROCFILE(p_type in varchar2)
 authid definer IS

  -- 系統參數設定
  --v_sysdate       varchar2(8);                   --取得系統日期
  --v_sysdatetime   varchar2(14);                  --取得系統時間
  --v_systime       varchar2(6);                   --取得系統日期
  --V_STAFF         varchar2(5);
  --V_KDTE          varchar2(8);
  --V_KNO           NUMBER := 0;
  --V_MLDTE         VARCHAR2(8);
  
    v_starttime            CI.CIPB.UPD_DATETIME%TYPE ;
    v_success              mmjoblog.proc_count%TYPE DEFAULT 0;       --處理筆數
    v_tatol                mmjoblog.in_count%TYPE DEFAULT 0;         --進入筆數
    v_failure              mmjoblog.err_count%TYPE DEFAULT 0;        --錯誤筆數

   v_jobno              VARCHAR2(20) DEFAULT null;                     --批號
   v_incnt               mmjoblog.in_count%TYPE DEFAULT 0;              -- 進入筆數
   v_dstr varchar2(32767);
   v_infile              utl_file.file_type;                             --文字檔資料
   v_file_name           VARCHAR2(4000);                                 --文字檔資料
   v_linebuf             VARCHAR2(1800);
   v_outfile             utl_file.file_type;
   exist                 BOOLEAN;                                         -- 判斷文字檔是否存在
   file_length           NUMBER;                                          -- 文字檔大小
   blocksize             NUMBER;
   proc_exception        EXCEPTION;
   clobvard              CLOB;
   clobvar               CLOB;
   v_row number;               --行數
   V_UERR  NUMBER := 0;
  --V_TATOL NUMBER := 0;
  V_CNT   NUMBER := 0;  
-- ============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
    PROCEDURE get_jobno
    IS
    BEGIN
       SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_jobno FROM DUAL;
    END get_jobno;
-- ============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------
  --紀錄JOBLOG
  PROCEDURE setJobLog(v_kind IN VARCHAR2, v_step IN VARCHAR2,v_memo IN VARCHAR2)
  IS
  BEGIN
     get_jobno;
     SP_BA_RECJOBLOG (p_job_no      => v_jobno,
                     p_job_id      => 'SP_BA_PROCFILE',
                     p_step        => v_step,
                     p_in_count    => v_tatol,
                     p_out_count   => 0,
                     p_proc_count  => v_success,
                     p_err_count   => v_failure,
                     p_memo        => v_memo,
                     p_table_name  => '');
  END setJobLog;
  --關檔
  PROCEDURE close_all
  IS
  BEGIN
    IF utl_file.is_open(v_infile) THEN
      UTL_FILE.FCLOSE(v_infile);
    End if;
    IF utl_file.is_open(v_outfile) THEN
      UTL_FILE.FCLOSE(v_outfile);
    End if;
    --v_bhfile.pdte := v_sysdate;
    --INSERT INTO cibhfile VALUES v_bhfile; 
  END close_all;
  -- ============================================================================
  --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
  -- ============================================================================
BEGIN  
  -- 記錄mmjoblog啟動
  v_starttime  := to_char(systimestamp,'YYYYMMDDHH24MISS');
  setJobLog('1', 'SP_BA_PROCFILE啟動執行','啟動');
  UTL_FILE.FGETATTR('EXCHANGE','BL100510505162016051601001.txt',exist,file_length,blocksize);
  IF (exist)THEN
      v_infile := utl_file.fopen ('EXCHANGE','BL100510505162016051601001.txt', 'R');
      --v_outfile := utl_file.FOPEN('CITXTFILE', v_file_name||'.txt', 'w'); --媒體檔案
    LOOP
      BEGIN
        utl_file.get_line (v_infile , v_linebuf);
        v_incnt := v_incnt + 1;
        --因收utf-8檔案有時前面開頭會有EF BB BF碼存在，要先去除，否則產檔會有?出現
         If  (v_incnt = 1) and inStr(v_linebuf,CHR(15711167)) > 0 then
           v_linebuf := replace(v_linebuf,CHR(15711167),'');
         End if;
        clobvar  := clobvar  || v_linebuf ; 
        clobvard := clobvard || v_linebuf || CHR (13) || CHR (10);                
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
           exit;
        WHEN Others THEN
           /*v_bhfile.file_name  := v_linebuf||CHR(13)||CHR(10)||
                                  SQLCODE || SQLERRM || DBMS_UTILITY.format_error_backtrace;*/
           RAISE proc_exception;
      END;
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE(clobvard);
    IF p_type = 'S' THEN
      FOR REC IN (
      SELECT REGEXP_SUBSTR(clobvar, '[^;]+', 1, LEVEL, 'i') AS STR
      FROM DUAL
      CONNECT BY LEVEL <= LENGTH(clobvar) - LENGTH(REGEXP_REPLACE(clobvar, ';', ''))
      )
      LOOP        
        DBMS_OUTPUT.PUT_LINE('S:'||REC.STR);
        EXECUTE IMMEDIATE REC.STR;
        COMMIT;
      END LOOP;
    ELSIF p_type = 'P' THEN
      DBMS_OUTPUT.PUT_LINE('P:'||clobvard);
      EXECUTE IMMEDIATE clobvard;
    END IF;
    setJobLog ('2','SP_BA_PROCFILE 執行中',clobvard);
  ELSE    
    setJobLog ('2','SP_BA_PROCFILE','SP_BA_PROCFILE失敗');  
  END IF;
  close_all;
  
  -- 記錄mmjoblog完成
  setJobLog('2', 'SP_BA_PROCFILE完成執行', '成功');

  COMMIT;
  PKG_BA_EMAIL.SP_BA_SENDEMAIL(v_jobno , 'SP_BA_PROCFILE','處理SQL/PROC檔案',v_starttime);
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    -- 記錄mmjoblog失敗
    setJobLog('3', 'SP_BA_PROCFILE執行失敗', 
                   '錯誤代碼：'||SQLCODE||CHR(10)||
                   '錯誤訊息：'||SQLERRM||CHR(10)||
                   '錯誤軌跡：'||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);

end SP_BA_PROCFILE;
/

