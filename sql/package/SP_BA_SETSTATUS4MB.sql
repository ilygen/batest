create or replace procedure BA.SP_BA_SETSTATUS4MB(p_status IN VARCHAR2) 
authid definer is
/* *******************************************************************************
    程式目的：設定是否允許MB系統抽檔的flag
    傳入參數：傳入Y/N varchar2(1)
    傳出參數：N/A
    維護人員        日期        說明
    -----------   --------  ----------------------------------------------------
    William      2023/02/23   初版，依據babaweb-70新增
******************************************************************************** */
  v_rec_plog plog%ROWTYPE;
  v_msg varchar(30);
  v_cnt number(3);
begin

  if p_status = 'Y' or  p_status = 'N' then
    select count(*)  into v_cnt
    from baproperty where name='update.status.for.mb'; 
    if v_cnt<=0 then
      insert into baproperty (name,value) values ('update.status.for.mb',p_status);  
    else
      update baproperty 
      set value = p_status
      where name='update.status.for.mb';  
      end if;
    v_msg := '更新為'||p_status;
  else
    v_msg := '未更新'||p_status;
  end if;  
  
  v_rec_plog.userid    := 'BATCH';
  v_rec_plog.jobid     := TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISSSSS');
  v_rec_plog.starttime := SYSDATE;
  v_rec_plog.typemk    := '1';
  v_rec_plog.levelmk   := '1'; --INFO
  v_rec_plog.pseq      := '1';
  v_rec_plog.proctime  := SYSDATE;
  v_rec_plog.procname  := 'PR_SETSTATUS4MB';
  v_rec_plog.msg1      := 'update.status.for.mb：'||v_msg;
  pkg_plog.sp_ins_log(v_rec_plog);
  commit;
EXCEPTION WHEN OTHERS THEN
      pkg_plog.error('BA整檔作業失敗：錯誤代碼='||sqlcode||' ， '||'錯誤訊息='||SQLERRM,DBMS_UTILITY.FORMAT_ERROR_BACKTRACE); 
      v_rec_plog.userid    := 'BATCH';
      v_rec_plog.jobid     := TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISSSSS');
      v_rec_plog.starttime := SYSDATE;
      v_rec_plog.typemk    := '1';
      v_rec_plog.levelmk   := '3'; --ERROR
      v_rec_plog.pseq      := '1';
      v_rec_plog.proctime  := SYSDATE;
      v_rec_plog.procname  := 'PR_SETSTATUS4MB';
      v_rec_plog.msg1      := SQLCODE || SQLERRM;
      v_rec_plog.msg2      := dbms_utility.format_error_backtrace;
      pkg_plog.sp_ins_log(v_rec_plog);
end SP_BA_SETSTATUS4MB;
/