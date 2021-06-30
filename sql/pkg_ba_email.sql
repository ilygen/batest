create or replace package ba.PKG_BA_EMAIL authid definer is

FUNCTION FN_BA_INSERTEMAIL(
  p_jobno         MMJOBLOG.JOB_NO%TYPE,
  p_mail_kind     CH.UTL_MAIL_DATA.MAIL_KIND%TYPE,
  p_email         CH.UTL_MAIL_DATA.EMAIL%TYPE,
  p_subject       CH.UTL_MAIL_DATA.SUBJECT%TYPE,
  p_message       CH.UTL_MAIL_DATA.MESSAGE%TYPE,
  p_file_path     CH.UTL_MAIL_DATA.FILE_PATH%TYPE,
  p_file_type     CH.UTL_MAIL_DATA.FILE_TYPE%TYPE
) RETURN  VARCHAR2;

PROCEDURE SP_BA_SENDEMAIL (
  p_jobno         MMJOBLOG.JOB_NO%TYPE,
  p_mailkind      CH.UTL_MAIL_DATA.MAIL_KIND%TYPE,
  p_subject       CH.UTL_MAIL_DATA.SUBJECT%TYPE,
  p_starttime     CI.CIPB.UPD_DATETIME%TYPE
  );
  
PROCEDURE SP_BA_SENDEMAIL (
  p_jobno         MMJOBLOG.JOB_NO%TYPE,
  p_mailkind      CH.UTL_MAIL_DATA.MAIL_KIND%TYPE,
  p_subject       CH.UTL_MAIL_DATA.SUBJECT%TYPE,
  p_starttime     CI.CIPB.UPD_DATETIME%TYPE,
  p_file_path     CH.UTL_MAIL_DATA.FILE_PATH%TYPE,
  p_file_type     CH.UTL_MAIL_DATA.FILE_TYPE%TYPE
  );  

end PKG_BA_EMAIL;
/

create or replace package body ba.PKG_BA_EMAIL is

FUNCTION FN_BA_INSERTEMAIL(
  p_jobno         MMJOBLOG.JOB_NO%TYPE,
  p_mail_kind     CH.UTL_MAIL_DATA.MAIL_KIND%TYPE,
  p_email         CH.UTL_MAIL_DATA.EMAIL%TYPE,
  p_subject       CH.UTL_MAIL_DATA.SUBJECT%TYPE,
  p_message       CH.UTL_MAIL_DATA.MESSAGE%TYPE,
  p_file_path     CH.UTL_MAIL_DATA.FILE_PATH%TYPE,
  p_file_type     CH.UTL_MAIL_DATA.FILE_TYPE%TYPE
)  RETURN  Varchar2
--
-- 程式目的：寫入UTL_MAIL_DATA
-- 參數：
--     名稱                  輸出入     型態            預設值     備註說明
-- ------------------------------------------------------------------------------
--   p_mail_kind             INPUT      VARCHAR2(20)               系統別或功能別
--   p_email                 INPUT      VARCHAR2(512)              收件者mail address
--   p_subject               INPUT      VARCHAR2(4000)             主旨
--   p_message               INPUT      VARCHAR2(4000)             內容(可HTML格式)
--   p_file_path             INPUT      VARCHAR2(1024)             附件路徑
--   p_file_type             INPUT      VARCHAR2(20)               附件類別 TXT, PDF, ...
--   RETURN                  OUTPUT     VARCHAR2                   Y:寫入成功/N:寫入失敗
-- 程式紀錄
--     人員                 日期      備註說明
-- ------------------------------------------------------------------------------
--
--
IS
PRAGMA AUTONOMOUS_TRANSACTION;
   v_mail               CH.UTL_MAIL_DATA%ROWTYPE;
BEGIN
-- 取得批號
  v_mail.batch_no := p_jobno;
  v_mail.seq_no   := '1';
  v_mail.mail_kind := p_mail_kind;
  v_mail.sender    := 'ch@ebs.bli.gov.tw';
  v_mail.email := p_email;
  v_mail.subject := p_subject;
  v_mail.message := p_message;
  v_mail.file_path := p_file_path;
  v_mail.file_type := p_file_type;
  v_mail.is_mailed := 'N';                                     -- 是否已發信

  INSERT INTO CH.UTL_MAIL_DATA VALUES v_mail;
  commit;

  RETURN 'Y';
EXCEPTION
   WHEN OTHERS
   THEN
      RETURN 'N';
END;

PROCEDURE SP_BA_SENDEMAIL (
  p_jobno         MMJOBLOG.JOB_NO%TYPE,
  p_mailkind      CH.UTL_MAIL_DATA.MAIL_KIND%TYPE,
  p_subject       CH.UTL_MAIL_DATA.SUBJECT%TYPE,
  p_starttime     CI.CIPB.UPD_DATETIME%TYPE
  )
  IS
    p_mail            CH.UTL_MAIL_DATA%rowtype;
  BEGIN
    SP_BA_SENDEMAIL(p_jobno, p_mailkind, p_subject, p_starttime,'','');

END SP_BA_SENDEMAIL;

PROCEDURE SP_BA_SENDEMAIL (
  p_jobno         MMJOBLOG.JOB_NO%TYPE,
  p_mailkind      CH.UTL_MAIL_DATA.MAIL_KIND%TYPE,
  p_subject       CH.UTL_MAIL_DATA.SUBJECT%TYPE,
  p_starttime     CI.CIPB.UPD_DATETIME%TYPE,
  p_file_path     CH.UTL_MAIL_DATA.FILE_PATH%TYPE,
  p_file_type     CH.UTL_MAIL_DATA.FILE_TYPE%TYPE
  )
  IS
BEGIN
 DECLARE
    v_endtime         CI.CIPB.UPD_DATETIME%TYPE := to_char(systimestamp,'YYYYMMDDHH24MISS');
    bamail            CH.UTL_MAIL_DATA%rowtype;
    v_rtn_code        VARCHAR2(2);
  BEGIN
    bamail.batch_no   := p_jobno;
    bamail.mail_kind  := p_mailkind;
    bamail.email      := '70348@ms.bli.gov.tw';
    bamail.subject    := p_subject;
    bamail.message    := 'Dear all:' || CHR (13) || CHR (10) ||CHR (13) || CHR (10) ||
                         '       本次Stored Procedure:('||p_mailkind||'),('|| p_subject ||')'|| CHR (13) || CHR (10) ||CHR (13) || CHR (10) ||
                         '       開始時間：'||p_starttime|| CHR (13) || CHR (10) ||
                         '       結束時間：'||v_endtime|| CHR (13) || CHR (10);
    bamail.file_path   := p_file_path;
    bamail.file_type   := p_file_type;

    v_rtn_code := FN_BA_INSERTEMAIL(p_jobno,bamail.mail_kind, bamail.email, bamail.subject, bamail.message, bamail.file_path, bamail.file_type);
 END;

END SP_BA_SENDEMAIL;



end PKG_BA_EMAIL;
/

