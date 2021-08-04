
create or replace procedure ba.SP_BA_RECBATCHJOBDTL(

  --
-- 程式目的：紀錄 RECBATCHJOBDTL LOG
-- 參數：
--     名稱           輸出入     型態       預設值     備註說明
-- -----------------------------------------------------------------------------------
--   V_I_BAJOBDTLID     IN     VARCHAR2                 
--   V_I_BAJOBID        IN     VARCHAR2                 
--   V_I_FLAG           IN     VARCHAR2
--   V_I_FLAGMSG        IN     VARCHAR2         
--   V_I_FLAGTIME       IN     VARCHAR2          


-- 程式紀錄
--     人員           日期       備註說明
-- ------------------------------------------------------------------------------
--     TseHua         2018/4/0    v1.0 初版
   v_i_bajobdtlid    IN   VARCHAR2,
   v_i_bajobid       IN   VARCHAR2,
   v_i_flag          IN   VARCHAR2,
   v_i_flagmsg       IN   VARCHAR2,
   v_i_flagtime      IN   VARCHAR2
  
)
authid definer IS
   PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
    insert into BABATCHJOBDTL
                     (BAJOBDTLID, BAJOBID, FLAG, FLAGMSG, FLAGTIME)
                     values
                       (v_i_bajobdtlid,
                        v_i_bajobid,
                        v_i_flag,
                        v_i_flagmsg,
                        substr(v_i_flagtime,1,20));

   COMMIT;
EXCEPTION
   WHEN OTHERS
   THEN
      INSERT INTO BABATCHJOBDTL
                  (BAJOBDTLID, BAJOBID, FLAG, FLAGMSG, FLAGTIME)
           VALUES (
                   v_i_bajobdtlid,
                   v_i_bajobid,
                   '1',
                   'SP_BA_RECBATCHJOBDTL INSERT_JOB_LOG_ERROR',
                    v_i_flagtime);

      COMMIT;
end SP_BA_RECBATCHJOBDTL;
/
