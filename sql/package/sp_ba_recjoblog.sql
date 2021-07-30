CREATE OR REPLACE PROCEDURE BA.SP_BA_RECJOBLOG (
--
-- 程式目的：紀錄 JOB STEP LOG
-- 參數：
--     名稱           輸出入     型態       預設值     備註說明
-- -----------------------------------------------------------------------------------
--   P_JOB_NO          IN     VARCHAR2                 必填欄位
--   P_JOB_ID          IN     VARCHAR2                 請必填 JOB 名稱
--   P_STEP            IN     VARCHAR2
--   P_IN_COUNT        IN     NUMBER         0
--   P_OUT_COUNT       IN     NUMBER         0
--   P_PROC_COUNT      IN     NUMBER         0
--   P_ERR_COUNT       IN     NUMBER         0
--   P_MENO            IN     VARCHAR2               可紀錄  exception message
--   P_TABLE_NAME      IN     VARCHAR2               可紀錄  Table name
--
-- CALL 方式:
--         例: SP_BA_RECJOBLOG (p_job_no     => 'TEST001',
--                             p_memo       => 'TEST MEMO',
--                             p_job_id     => 'npC10xxxxxx',
--                             p_err_count  => 76,
--                             p_table_name => 'TTTXXX');
-- 程式紀錄
--     人員           日期       備註說明
-- ------------------------------------------------------------------------------
--     ALN         2008/07/11    v1.0 初版
--     ALN         2008/09/25    v1.1  增加可傳 "入 " p_table_name 參數
   p_job_no       IN   VARCHAR2,
   p_job_id       IN   VARCHAR2,
   p_step         IN   VARCHAR2 DEFAULT NULL,
   p_in_count     IN   NUMBER DEFAULT 0,
   p_out_count    IN   NUMBER DEFAULT 0,
   p_proc_count   IN   NUMBER DEFAULT 0,
   p_err_count    IN   NUMBER DEFAULT 0,
   p_memo         IN   VARCHAR2 DEFAULT NULL,
   p_table_name   IN   VARCHAR2 DEFAULT NULL
)
authid definer IS
   PRAGMA AUTONOMOUS_TRANSACTION;
   v_seq_no     NUMBER                   := 0;
   v_job_kind   mmjoblog.job_kind%TYPE   := '';
   v_job_name   mmjoblog.job_name%TYPE   := '';
-- v_table_name   mmjoblog.table_name%TYPE   := '';
   v_sqlcode    VARCHAR2 (1000);
   v_sqlerrm    VARCHAR2 (1000);
BEGIN
   SELECT MAX (TO_NUMBER (seq_no)) + 1
     INTO v_seq_no
     FROM mmjoblog
    WHERE job_no = p_job_no;

   v_seq_no := NVL (v_seq_no, 0);               -- if First step, v_seq_no = 0

   INSERT INTO mmjoblog
               (job_no, seq_no, job_kind, job_id, job_name, job_step,
                table_name, execute_tmstmp, in_count, out_count,
                proc_count, err_count, memo
               )
        VALUES (p_job_no, v_seq_no, v_job_kind, p_job_id, v_job_name, p_step,
                p_table_name, SYSTIMESTAMP, p_in_count, p_out_count,
                p_proc_count, p_err_count, p_memo
               );

   COMMIT;
EXCEPTION
   WHEN OTHERS
   THEN
      v_sqlcode := SQLCODE;
      v_sqlerrm := SQLERRM;

      DELETE FROM mmjoblog
            WHERE job_no = p_job_no AND seq_no = -911;

      INSERT INTO mmjoblog
                  (job_no, seq_no, job_kind, job_id, job_name,
                   job_step, table_name, execute_tmstmp, in_count,
                   out_count, proc_count, err_count, memo
                  )
           VALUES (p_job_no, -911, '', v_sqlcode, '',
                   'SP_BA_RECJOBLOG Error!', p_table_name, SYSTIMESTAMP, 0,
                   0, 0, 0, v_sqlerrm
                  );

      COMMIT;
END;
/

