CREATE OR REPLACE PROCEDURE BA.SP_BA_NPB10W7905(p_all IN VARCHAR2) authid definer IS

 v_jobno      MMJOBLOG.JOB_NO%type := to_Char(Sysdate, 'YYYYMMDDHH24MISS');

BEGIN
      -- ============================================================================
      -- 程式名稱 :  產生對外服務主檔、核定檔資料
      -- 程式目的 :  產生對外服務主檔、核定檔資料
      -- 輸入參數 :  p_jobno          處理批號
      -- 輸出參數 :  無
      -- ----------- ----------  ----------------------------------------------------
      -- TABLE    :   BAAPPBASE   給付主檔
      --              BADAPR      給付核定檔
      --
      --              BAMEAPPSTS  國/勞保年金給付受理狀況檔
      --              BAMEDAPR    國/勞保年金給付清單檔
      --
      -- 程式紀錄
      --     人員       日期          備註說明
      -- ----------- ----------  ----------------------------------------------------
      --      evelyn     2009/10/28  v.10 初版
      --      evelyn     2009/12/11  v.11 增加回傳值(總件數,成功筆數,失敗筆數)
      --      ChungYu    2010/06/11  v.12 只提供最近核發的六筆資料
      --      ChungYu    2010/07/08  v.13 因應客服系統，新增事故日期、年資、未收總金額
      --      ChungYu    2010/08/13  v.14 年資寫入改為年月，及新增寫入系統別欄位
      --      ChungYu    2010/09/10  v.15 新增寫入每個給付月份未收金額
      --      ChungYu    2011/01/17  v.16 BAMEAPPSTS新增平均薪資、核定金額、員工分機(承辦)三個欄位
      --      ChungYu    2011/02/21  v.17 CASETYP = 3 (不給付案)，STATUS加入不給付註記說明
      --      ChungYu    2011/05/03  v.18 LSUBNO(最後單位)修改為APUBNO(申請單位)，修改承辦人員分案原則
      --      ChungYu    2012/02/15  v.19 因失能遺屬尚未上線，故現行將處理狀態修改為"作業流程中，如有疑義請洽承辦人"
      --      ChungYu    2013/01/18  v.20 因DB 三合一後，本支Procedure執行時間常常過久，故將讀取核定檔資料修改為分段讀取。
      --      ChungYu    2013/02/05  v.21 再次調整讀取核定檔資料的條件。
      --      ChungYu    2013/12/31  v.22 將失能35案及遺屬案件加入對外查詢，唯失能36及38案尚未開放。
      --      ChungYu    2015/01/22  v.23 BAMEDAPR 需新增案件類別欄位，提供對外服務區查詢顯示。
      --      ChungYu    2015/04/09  v.24 BAMEAPPSTS 需新增發放起月欄位，提供WebIR查詢顯示。
      --      ChungYu    2015/09/16  v.25 因CPI加計物價指數核定檔分拆為二筆，於轉檔提供對外服務區查詢時，需將合併為一筆。
      --      ChungYu    2015/10/27  v.26 BAMEAPPSTS新增欄位SECRETMK(保密案件註記)，並查詢BCCMF42若事故者、受款人及眷屬有在其中，
      --                                  SECRETMK(保密案件註記)欄位值設為Y。
      --      Kiyomi     2015/11/02  v.27 判斷月核後的星期六、日抓全部資料，其餘日子只抓「PROCSTAT < "50"」的資料
      --      Kiyomi     2016/01/06  v.28 原本月核後的星期六、日抓全部資料，改成每星期的六、日都要抓全部資料
      --      Kiyomi     2016/01/11  v.29 原本每星期的六、日都要抓全部資料，改成每星期的六抓全部資料
      --      Kiyomi     2018/01/15  v.30 案件處理狀態 (PayKind) 加入 35、36、38、55、56、59
      --      Kiyomi     2019/01/17  v.31 (1) 因執行時間過久，修改清空 BAMEAPPSTS 及 BAMEDAPR TABLE 寫法
      --                                  (2) 因星期六全量可能執行超過 24 小時，因此星期日不執行此程式
      --      ChungYu    2019/09/04  v.32 因案件數量龐大，於每月月核後全量執行時間過長，修改為分流寫法，將搬資料邏輯移至SP_BA_NPB10W7905_THREAD
      --                                  , 本支Procedure 修改為分流資訊處理。
      --      ChungYu    2019/10/22  v.33 原修改月核後的星期六才會執行全量資料轉檔，調整為每個星期六執行全量資料轉檔，星期日不轉。
      --
      -- ============================================================================

  DECLARE

      v_alldata    varchar2(1);
      v_instno     BINARY_INTEGER;
      v_thread     INTEGER := 20;  --  設20 個thread
      v_thread_cnt NUMBER(10);
      --v_starttime  TIMESTAMP := SYSTIMESTAMP; --開始時間

      r_apno_tab   varchar2_tab := varchar2_tab();
      v_apno_idx   NUMBER(10) := 0;
      v_bgn_apno   BAAPPBASE.apno%TYPE;
      v_end_apno   BAAPPBASE.apno%TYPE;

      -- 宣告型態
      TYPE job_tab IS TABLE OF BINARY_INTEGER;
      job_table job_tab;
      TYPE jobno_tab IS TABLE OF mmjoblog.job_no%TYPE;
      jobno_table jobno_tab;

      -- 2019/01/17 Added By Kiyomi
      v_Today     VARCHAR2(8) := ''; --  SYSDATE

  -- ============================================================================
  -- PROCEDURE & FUNCTION AREA
  -------------------------------------------------------------------------------
      PROCEDURE get_apno
      IS
      BEGIN

        SELECT A.APNO
          BULK COLLECT INTO r_apno_tab
          FROM BAAPPBASE A
         WHERE SEQNO = '0000'
        ORDER BY APNO;

      END get_apno;
  -- ============================================================================
  --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
  ------------------------------------------------------------------------------

  BEGIN
    --勞保年金======================================================================================================
    BEGIN

      -- 取得日期資料
      BEGIN
        SELECT TO_CHAR(SYSDATE, 'D')
          INTO v_Today
          FROM DUAL;
      EXCEPTION
        WHEN OTHERS THEN
          v_Today := NULL;
      END;

      -- 於每個星期六或傳入參數p_all為Y，才執行全量轉檔  2019/10/22 Modify By ChugnYu 
      IF (v_Today = '7') Or (p_all = 'Y') OR (p_all = 'y') THEN
          v_alldata := 'Y';
      ELSIF (v_Today = '1') THEN  -- 星期日不轉檔   2019/10/22 Modify By ChugnYu 
          v_alldata := 'N';    
      ELSE
          v_alldata := 'D';
      END IF;



      SP_BA_RECJOBLOG(p_job_no => v_jobno,
                      p_job_id => 'SP_BA_NPB10W7905',
                      p_step   => '開始分流-產生勞保年金對外服務主檔、核定檔資料',
                      p_memo   => '開始時間：' ||
                                   TO_CHAR(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS') ||
                                  '傳入參數：' || p_all );
     IF (v_alldata = 'Y') THEN
          -- 全量產生資料，先清空BAMEAPPSTS、BAMEDAPR 資料
          EXECUTE IMMEDIATE('truncate table BAMEAPPSTS');
          EXECUTE IMMEDIATE('truncate table BAMEDAPR');

          EXECUTE IMMEDIATE('alter session set "_gby_hash_aggregation_enabled"=false');
          --取得待處理之受理編號
          get_apno;
          -- 設定分流資料量
          v_thread     := CASE WHEN r_apno_tab.count < v_thread THEN 1 ELSE v_thread END;
          v_thread_cnt := TRUNC(r_apno_tab.count/v_thread,0);
          --設定取回JOB編號的變數
          job_table := job_tab();
          jobno_table := jobno_tab();
          job_table.EXTEND(v_thread);
          jobno_table.EXTEND(v_thread);
          --取得分流之JOBNO
          FOR v_i IN 1..jobno_table.COUNT
          LOOP
            jobno_table(v_i) := TO_CHAR(sysdate, 'YYYYMMDD')|| BABATCHRECID.NEXTVAL;
          END LOOP;
          --設定ORACLE的INSTANCE編號
          v_instno := SYS.INSTANCE_NUM;
          FOR v_i IN 1..v_thread
          LOOP
            v_apno_idx := v_apno_idx + 1;
            continue when not r_apno_tab.exists(v_i);
            v_bgn_apno := r_apno_tab(v_apno_idx);

            IF v_i = v_thread THEN
               v_end_apno := r_apno_tab(r_apno_tab.last);
            ELSE
               v_apno_idx := v_apno_idx + v_thread_cnt;
               v_end_apno := r_apno_tab(v_apno_idx);
            END IF;

            DBMS_JOB.SUBMIT( JOB       => job_table(v_i),
                             WHAT      => 'BEGIN' || CHR(10) ||
                                          'SP_BA_NPB10W7905_THREAD('''    || v_bgn_apno         -- 起始受理編號
                                                               || ''',''' || v_end_apno         -- 結束受理編號
                                                               || ''',''' || v_i                -- 分流編號
                                                               || ''',''' || jobno_table(v_i)   -- 批次處理編號
                                                               || ''',''' || v_alldata          -- 全量資料
                                                               || ''');' || CHR(10) ||
                                          'END;',
                             NEXT_DATE => SYSDATE,
                             INSTANCE  => v_instno);

            COMMIT;



           SP_BA_RECJOBLOG(p_job_no => v_jobno,
                           p_job_id => 'SP_BA_NPB10W7905',
                           p_step   =>  '資料分流-'||v_i,
                           p_memo   =>  '開始時間：'||TO_CHAR(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||
                                        'LOG 批號：'||jobno_table(v_i)||CHR(10)||
                                        '開始序號：'||v_bgn_apno||CHR(10)||
                                        '結束序號：'||v_end_apno||CHR(10)||
                                        'JOB 編號：'||job_table(v_i)||CHR(10)||
                                        'INSTANCE：'||v_instno);
          END LOOP;
     ELSIF (v_alldata = 'D') THEN
         SP_BA_NPB10W7905_THREAD('','','',v_jobno,v_alldata); -- 每日異動資料
     END IF;

     SP_BA_RECJOBLOG(p_job_no => v_jobno,
                      p_job_id => 'SP_BA_NPB10W7905',
                      p_step   => '完成分流-產生勞保年金對外服務主檔、核定檔資料',
                      p_memo   => '結束時間：' ||
                                   TO_CHAR(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS'));
    EXCEPTION
      WHEN OTHERS THEN
        SP_BA_RECJOBLOG(p_job_no => v_jobno,
                        p_job_id => 'SP_BA_NPB10W7905',
                        p_step   => 'EXCEPTION',
                        p_memo   => 'SP_BA_NPB10W7905_01 EXEC SP_BA_NPB10W7905 FAIL!  錯誤:' ||
                                    SQLERRM ||
                                    DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    END;

  END;
EXCEPTION
  WHEN OTHERS THEN
    --ROLLBACK;
    SP_BA_RECJOBLOG(p_job_no => v_jobno,
                    p_job_id => 'SP_BA_NPB10W7905',
                    p_step   => 'EXCEPTION',
                    p_memo   => '錯誤:' || SQLERRM ||
                                DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);

END SP_BA_NPB10W7905;

