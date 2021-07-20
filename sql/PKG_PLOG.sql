CREATE OR REPLACE PACKAGE BA.PKG_PLOG AS
/******************************************************************************
   程式目的:做為作業紀錄的共用元件
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定,
         訊息等級分為:
         1->INFO         
         2->WARM
         3->ERROR
         4->FETAL
           
   相關 TABLE:
   I    PLOG            作業紀錄檔
   S    BABATCHJOB      勞保年金線上批次啟動作業紀錄檔
   S    BAPROCEDUREDATA 資料庫處理程序資料檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
gc_level_debug CONSTANT PLS_INTEGER := '0';
gc_level_info CONSTANT PLS_INTEGER := '1';
gc_level_warm CONSTANT PLS_INTEGER := '2';
gc_level_error CONSTANT PLS_INTEGER := '3';
gc_level_fetal CONSTANT PLS_INTEGER := '4';
ge_init        EXCEPTION;  --初始處理時，發生錯誤
ge_noninit     EXCEPTION;  --未初始處理錯誤
ge_ins_log      EXCEPTION;  --新增log時，發生錯誤

PROCEDURE sp_doinit(p_procname_in IN plog.procname%TYPE);
/******************************************************************************
   程式目的:初始log,於作業開始時設定
   撰寫人員:70186
   執行方式:內部副程式 
   參數(IN) : p_procname_in  (plog.procname type)    處理代號/程式編號
   參數(OUT): 無
   註解：需提供處理代號以利使用線上批次執行紀錄取得登入資訊
           
   相關 TABLE:
   I    PLOG            作業紀錄檔
   S    BABATCHJOB      勞保年金線上批次啟動作業紀錄檔
   S    BAPROCEDUREDATA 資料庫處理程序資料檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
 
PROCEDURE sp_ins_log(p_rec_plog plog%ROWTYPE);
/******************************************************************************
   程式目的: 新增log至作業紀錄檔
   撰寫人員: 70186
   執行方式: 內部副程式 
   參數(IN) : p_rec_plog  (plog record type)    要新增的作業紀錄
   參數(OUT): 無
   註解：
           
   使用物件:
   CRUD/E  物件名稱
   ---- --------------- ------------------------------------------------------
   C    PLOG            作業紀錄檔
   
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
 
PROCEDURE debug(p_msg1_in IN VARCHAR2);
/******************************************************************************
   程式目的:寫入層級為DEBUG的log(開發時除錯用)
   撰寫人員:70186
   執行方式 :提供Storeprocedure呼叫使用
   參數(IN) : p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
 
PROCEDURE debug(
  p_msg1_in IN VARCHAR2,
  p_msg2_in IN VARCHAR2);
/******************************************************************************
   程式目的:寫入層級為DEBUG的log(開發時除錯用)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/  

PROCEDURE debug(
  p_msg1_in IN VARCHAR2,
  p_msg2_in IN VARCHAR2,
  p_msg3_in IN VARCHAR2);
/******************************************************************************
   程式目的:寫入層級為DEBUG的log(開發時除錯用)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2   
   p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/

PROCEDURE info(p_msg1_in IN VARCHAR2);
/******************************************************************************
   程式目的:寫入層級為INFO的log(應用程式平常運作時，有必要紀錄於log檔中的訊息)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN) : p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/

PROCEDURE info(
  p_msg1_in IN VARCHAR2,
  p_msg2_in IN VARCHAR2);
/******************************************************************************
   程式目的:寫入層級為INFO的log(應用程式平常運作時，有必要紀錄於log檔中的訊息)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2  
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/

PROCEDURE info(
  p_msg1_in IN VARCHAR2,
  p_msg2_in IN VARCHAR2,
  p_msg3_in IN VARCHAR2);
/******************************************************************************
   程式目的:寫入層級為INFO的log(應用程式平常運作時，有必要紀錄於log檔中的訊息)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2   
   p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期    說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/

PROCEDURE error(
  p_msg3_in IN VARCHAR2);
/******************************************************************************
   程式目的:寫入層級為ERROR的log(應用程式運行發生錯誤，足以影響系統的正常運作)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN) : p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
    
PROCEDURE error(
  p_msg2_in IN VARCHAR2,
  p_msg3_in IN VARCHAR2);
/******************************************************************************
   程式目的:寫入層級為ERROR的log(應用程式運行發生錯誤，足以影響系統的正常運作)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2   
   p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/

PROCEDURE error(
  p_msg1_in IN VARCHAR2,
  p_msg2_in IN VARCHAR2,
  p_msg3_in IN VARCHAR2);
/******************************************************************************
   程式目的:寫入層級為ERROR的log(應用程式運行發生錯誤，足以影響系統的正常運作)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2   
   p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
  
END PKG_PLOG;
/

CREATE OR REPLACE PACKAGE BODY BA.PKG_PLOG AS
/******************************************************************************
   程式目的:做為作業紀錄的共用元件
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定,
         訊息等級分為:
         1->INFO         
         2->WARM
         3->ERROR
         4->FETAL
           
   相關 TABLE:
   I    PLOG            作業紀錄檔
   S    BABATCHJOB      勞保年金線上批次啟動作業紀錄檔
   S    BAPROCEDUREDATA 資料庫處理程序資料檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
gc_length_msg CONSTANT PLS_INTEGER :=2000;
pv_rec_plog plog%ROWTYPE;
pv_pseq PLS_INTEGER :=0;

PROCEDURE sp_dolog(
  p_msg1_in IN VARCHAR2,
  p_msg2_in IN VARCHAR2,
  p_msg3_in IN VARCHAR2,
  p_level_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:檢核紀錄參數是否有初始,並協助新增紀錄
   撰寫人員:70186
   執行方式:內部副程式   
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2   
   p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   p_level_in VARCHAR2 (1 Byte)       1->INFO/2->WARM/3->ERROR/4->FETAL
   參數(OUT): 無
   註解：目前不提供外部使用，做為統一新增紀錄介面
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
v_rec_plog plog%ROWTYPE;
BEGIN
  --檢核是否有初始
  IF pv_rec_plog.userid IS NULL THEN
    RAISE ge_noninit;
  END IF;
  v_rec_plog :=pv_rec_plog;
  v_rec_plog.pseq := pv_pseq;
  v_rec_plog.levelmk := p_level_in;
  v_rec_plog.msg1 := p_msg1_in;
  v_rec_plog.msg2 := p_msg2_in;
  v_rec_plog.msg3 := p_msg3_in;
  sp_ins_log(v_rec_plog);
  pv_pseq := pv_pseq+1;
END sp_dolog;

PROCEDURE warm(
  p_msg1_in IN VARCHAR2,
  p_msg2_in IN VARCHAR2,
  p_msg3_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:寫入層級為WARM的log
   撰寫人員:70186
   執行方式:內部副程式
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2   
   p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   參數(OUT): 無
   註解：目前不提供外部使用，以分別為業務程式或plog元件自行寫入的log
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
BEGIN
  sp_dolog(p_msg1_in,p_msg2_in,p_msg3_in,gc_level_warm);
END warm;

PROCEDURE fetal(
  p_msg3_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:寫入層級為FETAL的log
   撰寫人員:70186
   執行方式:內部副程式 
   參數(IN) : p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)TAL
   參數(OUT): 無
   註解：目前不提供外部使用，以分別為業務程式或log元件自行寫入的log
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
v_rec_plog plog%ROWTYPE;
BEGIN
  IF pv_rec_plog.userid IS NULL THEN
    v_rec_plog.userid := 'BATCH';
    v_rec_plog.jobid  := 'batch'||TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')||'888j';
    v_rec_plog.starttime := SYSDATE;
    v_rec_plog.typemk :='1';
  ELSE
    v_rec_plog :=pv_rec_plog;
  END IF;
  v_rec_plog.levelmk := gc_level_fetal;
  v_rec_plog.pseq := pv_pseq;
  v_rec_plog.msg3 := SUBSTR(p_msg3_in,1,gc_length_msg);
  
  sp_ins_log(v_rec_plog);
  pv_pseq := pv_pseq+1;
END fetal;

PROCEDURE sp_ins_log(
    p_rec_plog plog%ROWTYPE)
IS
/******************************************************************************
   程式目的: 新增log至作業紀錄檔
   撰寫人員: 70186
   執行方式: 內部副程式 
   參數(IN) : p_rec_plog  (plog record type)    要新增的作業紀錄
   參數(OUT): 無
   註解：
           
   使用物件:
   CRUD/E  物件名稱
   ---- --------------- ------------------------------------------------------
   C    PLOG            作業紀錄檔
   
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
  PRAGMA AUTONOMOUS_TRANSACTION;
  v_rec_plog plog%ROWTYPE;
BEGIN
  v_rec_plog := p_rec_plog;
  IF v_rec_plog.proctime IS NULL THEN
    v_rec_plog.proctime := SYSDATE;
  END IF;  
  IF (LENGTH(v_rec_plog.msg1)>gc_length_msg) THEN
    v_rec_plog.msg1 := SUBSTR(v_rec_plog.msg1,1,gc_length_msg);
    warm('MSG1過長被截斷','JOBID='||v_rec_plog.jobid||'/PSEQ='||v_rec_plog.pseq,
       SUBSTR(v_rec_plog.msg1,gc_length_msg));
  END IF;
  IF (LENGTH(v_rec_plog.msg2)>gc_length_msg) THEN
    v_rec_plog.msg2 := SUBSTR(v_rec_plog.msg2,1,gc_length_msg);
    warm('MSG2過長被截斷','JOBID='||v_rec_plog.jobid||'/PSEQ='||v_rec_plog.pseq,
       SUBSTR(v_rec_plog.msg2,gc_length_msg));
  END IF;  
  IF (LENGTH(v_rec_plog.msg3)>gc_length_msg) THEN
    v_rec_plog.msg3 := SUBSTR(v_rec_plog.msg3,1,gc_length_msg);
    warm('MSG3過長被截斷','JOBID='||v_rec_plog.jobid||'/PSEQ='||v_rec_plog.pseq,
       SUBSTR(v_rec_plog.msg3,gc_length_msg));
  END IF;
  INSERT INTO plog VALUES v_rec_plog;
  COMMIT;
EXCEPTION
  WHEN OTHERS THEN
    RAISE ge_ins_log;
END sp_ins_log;

PROCEDURE sp_doinit(
/******************************************************************************
   程式目的:初始log,於作業開始時設定
   撰寫人員:70186
   執行方式:內部副程式 
   參數(IN) : p_procname_in  (plog.procname type)    處理代號/程式編號
   參數(OUT): 無
   註解：需提供處理代號以利使用線上批次執行紀錄取得登入資訊
           
   相關 TABLE:
   I    PLOG            作業紀錄檔
   S    BABATCHJOB      勞保年金線上批次啟動作業紀錄檔
   S    BAPROCEDUREDATA 資料庫處理程序資料檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
  p_procname_in IN plog.procname%TYPE)
IS
 v_job babatchjob%ROWTYPE;
BEGIN
  SELECT * INTO v_job FROM babatchjob j
  WHERE proctype = '9'
  AND   status = 'R'
  AND   EXISTS (
      SELECT * FROM ba.baproceduredata p
      WHERE j.bajobid = p.bajobid
      AND procedure_name = p_procname_in);
        
  pv_rec_plog.userid    := v_job.procempno;
  pv_rec_plog.jobid     := v_job.bajobid;
  pv_rec_plog.starttime := TO_DATE(v_job.procbegtime,'YYYYMMDDHH24MISS');
  pv_rec_plog.typemk    := '1'; 
  pv_rec_plog.procname  := p_procname_in;
  pv_pseq :=0;  
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    fetal('找不到對應的BABATCHJOB');
    RAISE ge_init;
END sp_doinit;

PROCEDURE info(
  p_msg1_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:寫入層級為INFO的log(應用程式平常運作時，有必要紀錄於log檔中的訊息)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN) : p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
BEGIN
  info(p_msg1_in,'','');
END info;

PROCEDURE info(
  p_msg1_in IN VARCHAR2,  
  p_msg2_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:寫入層級為INFO的log(應用程式平常運作時，有必要紀錄於log檔中的訊息)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2  
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
BEGIN
  info(p_msg1_in,p_msg2_in,'');
END info;

PROCEDURE info(
  p_msg1_in IN VARCHAR2,  
  p_msg2_in IN VARCHAR2,  
  p_msg3_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:寫入層級為INFO的log(應用程式平常運作時，有必要紀錄於log檔中的訊息)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2   
   p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期    說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
BEGIN  
 sp_dolog(p_msg1_in,p_msg2_in,p_msg3_in,gc_level_info);
END info;

PROCEDURE debug(
  p_msg1_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:寫入層級為DEBUG的log(開發時除錯用)
   撰寫人員:70186
   執行方式 :提供Storeprocedure呼叫使用
   參數(IN) : p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
BEGIN  
  debug(p_msg1_in,'','');
END debug;

PROCEDURE debug(
  p_msg1_in IN VARCHAR2,
  p_msg2_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:寫入層級為DEBUG的log(開發時除錯用)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
BEGIN
  debug(p_msg1_in,p_msg2_in,'');
END debug;

PROCEDURE debug(
  p_msg1_in IN VARCHAR2,
  p_msg2_in IN VARCHAR2,
  p_msg3_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:寫入層級為DEBUG的log(開發時除錯用)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2   
   p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
BEGIN
 sp_dolog(p_msg1_in,p_msg2_in,p_msg3_in,gc_level_debug);
END debug;

PROCEDURE error(
  p_msg3_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:寫入層級為ERROR的log(應用程式運行發生錯誤，足以影響系統的正常運作)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN) : p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
BEGIN
  error('','',p_msg3_in);
END error;

PROCEDURE error(
  p_msg2_in IN VARCHAR2,
  p_msg3_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:寫入層級為ERROR的log(應用程式運行發生錯誤，足以影響系統的正常運作)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2   
   p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
BEGIN  
  error('',p_msg2_in,p_msg3_in);
END error;

PROCEDURE error(
  p_msg1_in IN VARCHAR2,
  p_msg2_in IN VARCHAR2,
  p_msg3_in IN VARCHAR2)
IS
/******************************************************************************
   程式目的:寫入層級為ERROR的log(應用程式運行發生錯誤，足以影響系統的正常運作)
   撰寫人員:70186
   執行方式:提供Storeprocedure呼叫使用
   參數(IN):
   p_msg1_in  VARCHAR2 (2000 Char)    訊息內容1(debug,info預設使用)
   p_msg2_in  VARCHAR2 (2000 Char)    訊息內容2   
   p_msg3_in  VARCHAR2 (2000 Char)    訊息內容3(error,fetal預設使用)
   參數(OUT): 無
   註解：仿log4j模式,提供單一訊息參數即協助insert log,
         故程式起始前需呼叫sp_doinit進行初始設定
           
   相關 TABLE:
   I    PLOG    作業紀錄檔
   ----------------------------------------------------------------------------
   修改紀錄：
   維護人員        日期       說明
   -----------   ----------  ----------------------------------------------------
   70186         2021/06/04  初始撰寫
 ******************************************************************************/
BEGIN
 sp_dolog(p_msg1_in,p_msg2_in,p_msg3_in,gc_level_error);
END error;

END PKG_PLOG;
/
