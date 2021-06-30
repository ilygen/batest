CREATE OR REPLACE PROCEDURE BA.BA_LOG2ADM(p_date IN VARCHAR2)
authid definer IS
/* ============================================================================
   程式目的：依據輸入日期將BA.MMACCESSLG複製到BLIADM.MMACCESSLG_NEW
   執行方式：EXEC BA_LOG2ADM(資料日期)
   傳入參數：p_date   1.空值-取系統日前一天, 2.ALL-取全量資料, 3.日期-取傳入之日期
   傳出參數：無
   相關表格：
   維護人員        日期    說明
   -----------   --------  ----------------------------------------------------
   Justin Yu     20130322  V1.0
   ============================================================================*/
BEGIN

  DECLARE

    TYPE t_mmaccesslg_new IS TABLE OF mmaccesslg_new%ROWTYPE;

    mmaccesslg_new_tab    t_mmaccesslg_new := t_mmaccesslg_new();

    v_bajoblog4adm      bajoblog4adm%ROWTYPE;

    v_date          VARCHAR2(8) := '';
    v_limit         NUMBER(5)   := 3000;
    v_chkflg        BOOLEAN     := TRUE;
    v_i             NUMBER(10)  := 0;
    v_cnt           NUMBER(10)  := 0;
    v_ins_cnt       NUMBER(10)  := 0;
    v_starttime     TIMESTAMP;                  --開始時間
    v_endtime       TIMESTAMP;                  --結束時間
-- ============================================================================
-- CURSOR AREA
-------------------------------------------------------------------------------
    CURSOR c_main IS
      SELECT *
        FROM mmaccesslg_bli
       WHERE acstime LIKE v_date||'%';

   TYPE t_fetch_tab IS TABLE OF c_main%ROWTYPE;
   fetch_table t_fetch_tab;
-- ============================================================================
-- FUNCTION   PROCEDURE AREA
-------------------------------------------------------------------------------
  PROCEDURE init_joblog
  IS
  BEGIN
    DECLARE
      sv_maxseq NUMBER(7) := 0;
    BEGIN
      SELECT NVL(MAX(SUBSTR(job_no,9)),0)
        INTO sv_maxseq
        FROM bajoblog4adm
       WHERE job_no LIKE TO_CHAR(SYSDATE,'YYYYMMDD')||'%';
      v_bajoblog4adm.job_no := TO_CHAR(SYSDATE,'YYYYMMDD')||LPAD(sv_maxseq+1,7,'0');
    EXCEPTION
      WHEN OTHERS THEN
        sv_maxseq := 1;
        v_bajoblog4adm.job_no := TO_CHAR(SYSDATE,'YYYYMMDD')||LPAD(sv_maxseq,7,'0');
    END;
  END init_joblog;
-- ============================================================================
-- MAIN PROCESS
-------------------------------------------------------------------------------
  BEGIN
    v_chkflg := TRUE;
    IF p_date IS NULL THEN
       --當沒有輸入日期參數時
       --直接以系統日期前一天取代
       v_date := TO_CHAR(SYSDATE - 1,'YYYYMMDD');
    ELSIF p_date = 'ALL' THEN
       --當輸入'ALL'字串時,取得所有資料
       v_date := '';
    ELSE
       --若有輸入日期參數時
       --須先檢查日期是否符合萬年曆規則
       --符合則以輸入的日期取代
       --不符合則直接跳開不執行
       BEGIN
         v_date := TO_CHAR(TO_DATE(p_date,'YYYYMMDD'),'YYYYMMDD');
       EXCEPTION
         WHEN OTHERS THEN
           v_chkflg := FALSE;
       END;
    END IF;
    v_bajoblog4adm := NULL;
    init_joblog;
    v_starttime := SYSTIMESTAMP;
    v_bajoblog4adm.seq_no := 0;
    v_bajoblog4adm.job_id := 'BA_LOG2ADM';
    v_bajoblog4adm.job_name := '轉換BA.MMACCESSLG_BLI到BLIADM.MMACCESSLG_NEW';
    v_bajoblog4adm.job_step := '開始複製BA.MMACCESSLG_BLI到BLIADM.MMACCESSLG_NEW';
    v_bajoblog4adm.execute_tmstmp := v_starttime;
    v_bajoblog4adm.in_count   := 0;
    v_bajoblog4adm.out_count  := 0;
    v_bajoblog4adm.proc_count := 0;
    v_bajoblog4adm.err_count  := 0;
    v_bajoblog4adm.memo       := '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS.FF')||CHR(13)||CHR(10)||
                             '日期(傳入參數一)：'||NVL(p_date,'('||v_date||')');
    INSERT INTO bajoblog4adm VALUES v_bajoblog4adm;
    COMMIT;
    IF v_chkflg THEN
       --當檢查無誤才執行
       mmaccesslg_new_tab.EXTEND(v_limit);
       OPEN c_main;
       LOOP
         --將所讀取的資料放到陣列中
         FETCH c_main BULK COLLECT INTO fetch_table LIMIT v_limit;
         v_cnt := v_cnt + fetch_table.COUNT;

         FOR v_i IN 1..fetch_table.COUNT
         LOOP
           mmaccesslg_new_tab(v_i).acstime  := fetch_table(v_i).acstime ;
           mmaccesslg_new_tab(v_i).apname   := fetch_table(v_i).apname  ;

           mmaccesslg_new_tab(v_i).psno     := fetch_table(v_i).psno    ;
           mmaccesslg_new_tab(v_i).depid    := fetch_table(v_i).depid   ;
           mmaccesslg_new_tab(v_i).trnsid   := fetch_table(v_i).trnsid  ;
           mmaccesslg_new_tab(v_i).termid   := fetch_table(v_i).termid  ;
           mmaccesslg_new_tab(v_i).stype    := fetch_table(v_i).stype   ;
           mmaccesslg_new_tab(v_i).uno      := fetch_table(v_i).uno     ;
           mmaccesslg_new_tab(v_i).ym       := fetch_table(v_i).ym      ;
           mmaccesslg_new_tab(v_i).idno     := fetch_table(v_i).idno    ;
           mmaccesslg_new_tab(v_i).proposer := fetch_table(v_i).proposer;
           mmaccesslg_new_tab(v_i).proc     := fetch_table(v_i).proc    ;
           mmaccesslg_new_tab(v_i).apno     := fetch_table(v_i).apno    ;
           mmaccesslg_new_tab(v_i).evbrth   := fetch_table(v_i).evbrth  ;
           mmaccesslg_new_tab(v_i).qmk      := fetch_table(v_i).qmk     ;
           mmaccesslg_new_tab(v_i).acctype  := fetch_table(v_i).acctype ;
           mmaccesslg_new_tab(v_i).source   := fetch_table(v_i).source  ;
           mmaccesslg_new_tab(v_i).npids    := fetch_table(v_i).npids   ;
           --mmaccesslg_new_tab(v_i).aptmk    := fetch_table(v_i).aptmk   ;
           --mmaccesslg_new_tab(v_i).aptnm    := fetch_table(v_i).apidn   ;
           --mmaccesslg_new_tab(v_i).prtmk    := fetch_table(v_i).prtmk   ;
         END LOOP;
         FORALL v_i IN 1..fetch_table.COUNT
           INSERT INTO mmaccesslg_new VALUES mmaccesslg_new_tab(v_i);

         v_ins_cnt := v_ins_cnt + SQL%ROWCOUNT;
         COMMIT;
         --當沒有資料時離開迴圈
         EXIT WHEN c_main%NOTFOUND;
       END LOOP;
       v_endtime := SYSTIMESTAMP;
       v_bajoblog4adm.seq_no := v_bajoblog4adm.seq_no + 1;
       v_bajoblog4adm.job_step := '完成複製BA_MMACCESSLG到MMACCESSLG_NEW';
       v_bajoblog4adm.execute_tmstmp := v_endtime;
       v_bajoblog4adm.in_count   := v_cnt;
       v_bajoblog4adm.out_count  := v_ins_cnt;
       v_bajoblog4adm.proc_count := v_cnt;
       v_bajoblog4adm.err_count  := v_cnt - v_ins_cnt;
       v_bajoblog4adm.memo       := '程式名稱：BA_LOG2ADM(PROCEDURE)'||CHR(13)||CHR(10)||
                                '來源檔案：BA_MMACCESSLG'||CHR(13)||CHR(10)||
                                '轉換日期：'||v_date||CHR(13)||CHR(10)||
                                '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||
                                '           結束時間：'||TO_CHAR(v_endtime  ,'YYYY/MM/DD HH24:MI:SS')||CHR(13)||CHR(10)||
                                '======================================================================'||CHR(13)||CHR(10)||
                                '                  讀取     處理       輸出     新增       修改'||CHR(13)||CHR(10)||
                                '處理檔案        總筆數     筆數     總筆數     筆數       筆數'||CHR(13)||CHR(10)||
                                '----------------------------------------------------------------------'||CHR(13)||CHR(10)||
                                'BA_MMACCESSLG  '||LPAD(v_cnt, 9,' ')||
                                                    LPAD(v_cnt, 9,' ')||
                                                    LPAD('0'  ,11,' ')||
                                                    LPAD('0'  , 9,' ')||
                                                    LPAD('0'  , 9,' ')||CHR(10)||
                                'MMACCESSLG_NEW    '||LPAD('0'      , 9,' ')||
                                                    LPAD('0'      , 9,' ')||
                                                    LPAD(v_cnt    ,11,' ')||
                                                    LPAD(v_ins_cnt, 9,' ')||
                                                    LPAD(v_cnt - v_ins_cnt, 9,' ')||CHR(10)||
                                   '----------------------------------------------------------------------';
       INSERT INTO bajoblog4adm VALUES v_bajoblog4adm;

    ELSE
       v_endtime := SYSTIMESTAMP;
       v_bajoblog4adm.seq_no := v_bajoblog4adm.seq_no + 1;
       v_bajoblog4adm.job_step := '輸入參數錯誤';
       v_bajoblog4adm.execute_tmstmp := v_endtime;
       v_bajoblog4adm.memo       := '結束時間：'||TO_CHAR(v_endtime,'YYYY/MM/DD HH24:MI:SS.FF')||CHR(13)||CHR(10)||
                                '輸入日期參數( '||p_date||' )錯誤，處理中斷';
       INSERT INTO bajoblog4adm VALUES v_bajoblog4adm;

    END IF;
    COMMIT;
  END;
END BA_LOG2ADM;
/

