Create or replace function BA.FN_BA_GETMBSTATUS 
RETURN VARCHAR2 
authid definer  is
/* *******************************************************************************
    程式目的：回傳是否允許MB系統抽檔的旗標
    傳入參數：N/A
    傳出參數：狀態 'Y'-可抽檔，'N'-不可抽檔 ，'E'-BA系統發生錯誤
    維護人員        日期        說明
    -----------   --------  ----------------------------------------------------
    William      2023/02/23   初版，依據babaweb-70新增
******************************************************************************** */
  v_status varchar2(1) := 'E';
BEGIN
  select value into v_status
  from baproperty
  where name ='update.status.for.mb';
  return v_status;
  Exception when others then
      return v_status;
END FN_BA_GETMBSTATUS;
/