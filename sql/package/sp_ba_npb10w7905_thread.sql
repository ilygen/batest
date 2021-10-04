CREATE OR REPLACE PROCEDURE BA.SP_BA_NPB10W7905_THREAD( p_bgn_apno IN VARCHAR2,
                                                     p_end_apno IN VARCHAR2,
                                                     p_thread   IN INTEGER,
                                                     p_jobno    IN VARCHAR2,
                                                     p_alldata  IN VARCHAR2) authid definer IS

BEGIN
  -- ============================================================================
  --  程式名稱 : 產生對外服務主檔、核定檔資料
  --  程式目的 : 產生對外服務主檔、核定檔資料
  -- ============================================================================

  DECLARE

    --勞保計算用參數
    v_lcount     NUMBER := 0;
    v_lcountfail NUMBER := 0; -- 失敗數
    v_lcountok   NUMBER := 0; -- 成功數

    v_lstarttime TIMESTAMP;
    v_lendtime   TIMESTAMP;
    v_lspdtime   varchar2(100);
    v_lspdnum    number;

    -- ----------------------------------------------------------------------------
    -- CURSOR AREA
    -------------------------------------------------------------------------------
    --勞保年金
    PROCEDURE SP_BA_NPB10W7905_THREAD01(p_job_no    IN mmjoblog.job_no%TYPE,
                                        p_bgn_apno  IN VARCHAR2,
                                        p_end_apno  IN VARCHAR2,
                                        o_count     OUT NUMBER, --總件數
                                        o_countok   OUT NUMBER, --成功筆數
                                        o_countfail OUT NUMBER, --失敗筆數
                                        p_alldata   IN VARCHAR2 --若傳入值為Y，直接執行全量資料 Added By Kiyomi 2015/11/02
                                        ) IS
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
      --                                  , 本支Procedure 修改為SP_BA_NPB10W7905_THREAD 搬資料邏輯改為分流處理。          
      --      ChungYu    2019/09/13  v.33 因國保異動更新尚未修改，因此調整每日異動搬檔 NPS.NPMEAPPSTS、BAMEAPPSTS、NPS.NPMEDAPR、BAMEDAPR均寫。
      --      EthanChen  2021/08/19  v.34 新增案類代號bameappsts.pmrk以及移除寫入國保npmeappsts(資料查詢改由BA Restful API中呼叫，因此不跟五科介接)
      -- ============================================================================

      DECLARE
        --使用參數
        --BAAPPBASE
        v_mapno       BAAPPBASE.MAPNO%TYPE := ''; -- 主受理編號
        v_apno        BAAPPBASE.APNO%TYPE := ''; -- 受理編號
        v_seqno       BAAPPBASE.SEQNO%TYPE := ''; -- 序號
        v_uno         BAAPPBASE.APUBNO%TYPE := ''; -- 申請單位保險證號  Modify By ChungYu 2011/05/03
        v_paykindname VARCHAR2(100) := ''; -- 給付種類(中文)
        v_appdate     BAAPPBASE.APPDATE%TYPE := ''; -- 申請日期(收件日期)
        v_evteename   BAAPPBASE.EVTNAME%TYPE := ''; -- 事故者姓名
        v_evtidnno    BAAPPBASE.EVTIDNNO%TYPE := ''; -- 事故者身分證號
        v_evteebirt   BAAPPBASE.EVTBRDATE%TYPE := ''; -- 事故者出生日期
        v_evtjobdate  BAAPPBASE.EVTJOBDATE%TYPE := ''; -- 事故日期
        v_casetyp     BAAPPBASE.CASETYP%TYPE := ''; -- 案件狀態  Add By ChungYu  2011/02/21
        v_ndomkname   VARCHAR2(300) := ''; -- 處理註記名稱  Add By ChungYu  2011/02/21
        --BADAPR
        v_payym    BADAPR.PAYYM%TYPE := ''; -- 給付年月
        v_issueamt BADAPR.ISSUEAMT%TYPE := 0; -- 核定金額
        v_recamt   BADAPR.RECAMT%TYPE := 0; -- 收回金額
        v_supamt   BADAPR.SUPAMT%TYPE := 0; -- 補發金額

        --共用參數
        v_beneename BAAPPBASE.BENNAME%TYPE := ''; -- 受益人姓名
        v_benidnno  BAAPPBASE.BENIDNNO%TYPE := ''; -- 受益人身分證號
        v_beneebirt BAAPPBASE.BENBRDATE%TYPE := ''; -- 受益人出生日期

        v_valseni    VARCHAR2(10) := ''; -- 事故者實付年資(XX年XX月)
        v_recrem     NUMBER := 0; -- 未收總金額
        v_recremdetl NUMBER := 0; -- 未收金額(依給付年月計算)
        v_sisid      VARCHAR2(2) := 'BA'; -- 系統別  NP:國保   BA:勞保年金
        v_wage       NUMBER := 0; -- 平均薪資
        v_empext     VARCHAR2(12) := ''; -- 員工分機(承辦人)

        --計算用參數
        v_count      NUMBER := 0;
        v_countfail  NUMBER := 0; -- 失敗數
        v_countok    NUMBER := 0; -- 成功數
        v_tmp        VARCHAR2(100) := '';
        v_pmrk       VARCHAR2(2) := '';
        v_recremtmp1 NUMBER := 0; -- 給付年月應收總額
        v_recremtmp2 NUMBER := 0; -- 給付年月已收總額

        -- 2013/01/18 Add By ChungYu 核定檔核付資料
        v_RowCount  NUMBER := 0;
        v_RowBadapr BADAPR%ROWTYPE := NULL;
        -- 2013/01/18 Add By ChungYu 核定檔核付資料

        -- 2015/11/02 Added By Kiyomi
        --v_Today     VARCHAR2(8) := ''; --  SYSDATE
        --v_NextSat   VARCHAR2(8) := ''; -- 月核日後第一個星期六
        --v_NextSun   VARCHAR2(8) := ''; -- 月核日後第一個星期日
        v_stmt_str1 VARCHAR2(5000) := '';
        v_stmt_str2 VARCHAR2(100) := '';
        TYPE EmpCurTyp IS REF CURSOR;
        c_base EmpCurTyp;
        TYPE t_emprec IS RECORD(
          APNO        BA.BAAPPBASE.APNO%type := '',
          SEQNO       BA.BAAPPBASE.SEQNO%type := '',
          PAYKIND     BA.BAAPPBASE.PAYKIND%type := '',
          PAYKINDNAME VARCHAR2(100),
          APPDATE     BA.BAAPPBASE.APPDATE%type := '',
          EVTIDNNO    BA.BAAPPBASE.EVTIDNNO%type := '',
          EVTNAME     BA.BAAPPBASE.EVTNAME%type := '',
          EVTBRDATE   BA.BAAPPBASE.EVTBRDATE%type := '',
          EVTJOBDATE  BA.BAAPPBASE.EVTJOBDATE%type := '',
          BENIDNNO    BA.BAAPPBASE.BENIDNNO%type := '',
          BENNAME     BA.BAAPPBASE.BENNAME%type := '',
          BENBRDATE   BA.BAAPPBASE.BENBRDATE%type := '',
          APUBNO      BA.BAAPPBASE.APUBNO%type := '',
          CASETYP     BA.BAAPPBASE.CASETYP%type := '',
          PROCSTAT    BA.BAAPPBASE.PROCSTAT%type := '',
          PAYYMS      BA.BAAPPBASE.PAYYMS%type := '',
          STATUS      VARCHAR2(100),
          PMRK        VARCHAR2(2));
        r_base t_emprec;
        -- 2015/11/02 Added By Kiyomi

        -- 2015/01/22 Add By ChungYu BAMEDAPR 新增案件類別欄位
        v_mchktyp VARCHAR2(30) := '';

        -- 2015/04/09 Add By ChungYu BAMEAPPSTS 新增發放起月欄位
        v_payyms VARCHAR2(6) := '';

        -- 2015/10/27 Add By ChungYu BAMEAPPSTS 新增保密案件註記欄位
        v_secretmk VARCHAR2(1) := '';
        v_paytyp   VARCHAR2(2) := '';

        -- ----------------------------------------------------------------------------
        -- CURSOR BEGIN
        -------------------------------------------------------------------------------
        --取得核定金額資料
        CURSOR c_dapr IS
          SELECT DISTINCT A.APNO,
                          A.SEQNO,
                          A.PAYYM,
                          SUM(A.APLPAYAMT) AS APLPAYAMT,
                          SUM(A.RECAMT) AS RECAMT,
                          SUM(A.SUPAMT) AS SUPAMT
            FROM BADAPR A
           WHERE A.APNO = v_mapno --v_mapno
             AND A.SEQNO = v_seqno
             AND A.PAYYM >=
                 (SELECT TO_CHAR(ADD_MONTHS(TO_DATE(MAX(A1.PAYYM), 'YYYYMM'),
                                            -5),
                                 'YYYYMM')
                    FROM BADAPR A1
                   WHERE A1.APNO = A.APNO
                     AND A1.SEQNO = A.SEQNO
                     AND A1.MTESTMK = 'F'
                     AND A1.APLPAYMK = '3'
                     AND A1.APLPAYDATE IS NOT NULL)
             AND A.MTESTMK = 'F'
             AND A.APLPAYMK = '3'
             AND A.APLPAYDATE IS NOT NULL
           GROUP BY A.APNO, A.SEQNO, A.PAYYM
           ORDER BY A.PAYYM DESC;

        -- ----------------------------------------------------------------------------
        -- CURSOR END
        -------------------------------------------------------------------------------

        -- ----------------------------------------------------------------------------
        -- PROCEDURE BEGIN
        -------------------------------------------------------------------------------
        -- ============================================================================
        -- FUNCTION BEGIN
        -- ============================================================================

      BEGIN

        v_stmt_str1 := 'SELECT APNO,' || '       SEQNO,' ||
                       '       PAYKIND,' ||
                       '        CASE  WHEN (PAYKIND = ''35'') THEN ''失能年金''' ||
                       '              WHEN (PAYKIND = ''36'') THEN ''國保年金併計失能''' ||
                       '              WHEN (PAYKIND = ''37'') THEN ''職災失能補償一次金''' ||
                       '              WHEN (PAYKIND = ''38'') THEN ''失能年金併計國保年金''' ||
                       '              WHEN (PAYKIND = ''45'') THEN ''老年年金''' ||
                       '              WHEN (PAYKIND = ''48'') THEN ''老年年金併計國保年資''' ||
                       '              WHEN (PAYKIND = ''49'') THEN ''請領一次老年給付差額''' ||
                       '              WHEN (PAYKIND IN (''55'',''56'',''59'')) THEN ''遺屬年金''' ||
                       '              END AS PAYKINDNAME,' ||
                       '        APPDATE,' || '        EVTIDNNO,' ||
                       '        EVTNAME,' || '        EVTBRDATE,' ||
                       '        EVTJOBDATE,' || '        BENIDNNO,' ||
                       '        BENNAME,' || '        BENBRDATE,' ||
                       '        APUBNO,' || '        CASETYP,' ||
                       '        PROCSTAT,' || '        PAYYMS,' ||
                       '        CASE WHEN ( (CASETYP = ''1'') AND ( PROCSTAT = ''00''))  THEN ''新案受理中''' ||     -- Modified By Kiyomi 2018/01/15
                       '             WHEN ( (CASETYP = ''1'') AND ( PROCSTAT <> ''00'')) THEN ''新案審核中''' ||     -- Modified By Kiyomi 2018/01/15
                       '             WHEN ( (CASETYP = ''2'') AND ( PROCSTAT = ''50''))  THEN ''續發案核付　'' || ISSUEAMT' ||     -- Modified By Kiyomi 2018/01/15
                       '             WHEN ( (CASETYP = ''2'') AND ( PROCSTAT <> ''50'')) THEN ''續發案改核''' ||     -- Modified By Kiyomi 2018/01/15
                       '             WHEN ( (CASETYP = ''3'')) THEN ''不給付案''' ||     -- Modified By Kiyomi 2018/01/15
                       '             WHEN ( (CASETYP = ''4'')) THEN ''結案''' ||     -- Modified By Kiyomi 2018/01/15
                       '             WHEN ( (CASETYP = ''6'')) THEN ''暫緩給付案''' ||     -- Modified By Kiyomi 2018/01/15
                       '             ELSE ''作業流程中，如有疑義請洽承辦人''' ||
                       '             END AS STATUS,' ||
                       '        CASE WHEN ( (CASETYP = ''1'') AND ( PROCSTAT = ''00''))  THEN ''1''' ||
                       '             WHEN ( (CASETYP = ''1'') AND ( PROCSTAT <> ''00'')) THEN ''2''' ||
                       '             WHEN ( (CASETYP = ''2'') AND ( PROCSTAT = ''50''))  THEN ''3''' ||
                       '             WHEN ( (CASETYP = ''2'') AND ( PROCSTAT <> ''50'')) THEN ''4''' ||
                       '             WHEN ( (CASETYP = ''3'')) THEN ''5''' ||
                       '             WHEN ( (CASETYP = ''4'')) THEN ''6''' ||
                       '             WHEN ( (CASETYP = ''6'')) THEN ''7''' ||
                       '             ELSE ''8''' ||
                       '             END AS PMRK' ||
                       '   FROM BA.BAAPPBASE' ||
                       '  WHERE SEQNO != (CASE WHEN (PAYKIND LIKE (''5%'')) THEN ''0000''' ||
                       '                  ELSE ''XXXX'' END)' ||
                       '    AND (CASEMK <> ''D'' OR CASEMK is NULL)' ||
                       '    AND BENEVTREL NOT IN (''A'',''C'',''F'',''N'',''Z'',''O'')';

        IF ( p_alldata = 'Y' ) THEN
             v_stmt_str2 := ' AND APNO >= ''' ||  p_bgn_apno ||''''||
                            ' AND APNO <= ''' ||  p_end_apno ||''''||
                            ' ORDER BY APNO, SEQNO ';            
         ELSE
             v_stmt_str2 := ' AND PROCSTAT < ''50'' ORDER BY APNO, SEQNO';
          
          -- 2019/09/05 Modify By ChungYu 日異動資料直接寫到NPS，國保每日不在全量搬檔，所以刪資料也直接刪NPS
          delete from NPS.NPMEAPPSTS 
           WHERE APPNO IN
                 (SELECT DISTINCT APNO
                    FROM BA.BAAPPBASE
                   WHERE SEQNO != (CASE
                           WHEN (PAYKIND LIKE ('5%')) THEN
                            '0000'
                           ELSE
                            'XXXX'
                         END)
                     AND (CASEMK <> 'D' OR CASEMK is NULL)
                     AND BENEVTREL NOT IN ('A', 'C', 'F', 'N', 'Z', 'O')
                     AND PROCSTAT < '50');

          delete from NPS.NPMEDAPR 
           WHERE APPNO IN
                 (SELECT DISTINCT APNO
                    FROM BA.BAAPPBASE
                   WHERE SEQNO != (CASE
                           WHEN (PAYKIND LIKE ('5%')) THEN
                            '0000'
                           ELSE
                            'XXXX'
                         END)
                     AND (CASEMK <> 'D' OR CASEMK is NULL)
                     AND BENEVTREL NOT IN ('A', 'C', 'F', 'N', 'Z', 'O')
                     AND PROCSTAT < '50');
                     
        -- Add by ChugnYu 2019/09/14 每日異動搬檔二邊都要寫
        delete from BAMEAPPSTS 
           WHERE APPNO IN
                 (SELECT DISTINCT APNO
                    FROM BA.BAAPPBASE
                   WHERE SEQNO != (CASE
                           WHEN (PAYKIND LIKE ('5%')) THEN
                            '0000'
                           ELSE
                            'XXXX'
                         END)
                     AND (CASEMK <> 'D' OR CASEMK is NULL)
                     AND BENEVTREL NOT IN ('A', 'C', 'F', 'N', 'Z', 'O')
                     AND PROCSTAT < '50');

          delete from BAMEDAPR
           WHERE APPNO IN
                 (SELECT DISTINCT APNO
                    FROM BA.BAAPPBASE
                   WHERE SEQNO != (CASE
                           WHEN (PAYKIND LIKE ('5%')) THEN
                            '0000'
                           ELSE
                            'XXXX'
                         END)
                     AND (CASEMK <> 'D' OR CASEMK is NULL)
                     AND BENEVTREL NOT IN ('A', 'C', 'F', 'N', 'Z', 'O')
                     AND PROCSTAT < '50');
                     
        END IF;
        -- Added By Kiyomi 2013/04/15

        v_count := 0;

        /* FOR r_base IN c_base LOOP */
        OPEN c_base FOR v_stmt_str1 || v_stmt_str2;
        LOOP
          FETCH c_base
            INTO r_base;
          EXIT WHEN c_base%NOTFOUND;

          BEGIN
            --取得件數
            v_count := v_count + 1;

            BEGIN
              --BAAPPBASE基本參數資料
              v_mapno       := r_base.APNO;
              v_apno        := r_base.APNO;
              v_seqno       := r_base.SEQNO;
              v_uno         := r_base.APUBNO;
              v_paykindname := r_base.PAYKINDNAME;
              v_benidnno    := r_base.BENIDNNO;
              v_beneename   := r_base.BENNAME;
              v_beneebirt   := r_base.BENBRDATE;
              v_evtidnno    := r_base.EVTIDNNO;
              v_evteename   := r_base.EVTNAME;
              v_evteebirt   := r_base.EVTBRDATE;
              v_appdate     := r_base.APPDATE;
              v_tmp         := r_base.STATUS;
              v_pmrk        := r_base.PMRK;
              v_evtjobdate  := r_base.EVTJOBDATE;
              v_casetyp     := r_base.CASETYP; --Add By ChungYu 2011/02/21
              v_payyms      := r_base.PAYYMS; --Add By ChungYu 2015/04/09

              --取得不給付案處理註記說明
              IF (v_casetyp = '3') THEN
                BEGIN
                  SELECT T2.NDOMKNAME
                    INTO v_ndomkname
                    FROM (SELECT NDOMK1
                            FROM MAADMREC
                           WHERE APNO = v_apno
                             AND LETTERTYPE = '21'
                             AND TRIM(DELMK) IS NULL) T1,
                         (SELECT NDOMKNAME, NDOMK
                            FROM BAPANDOMK
                           WHERE LETTERTYPE = '21'
                             AND SYSID = 'BA') T2
                   WHERE T1.NDOMK1 = T2.NDOMK;
                EXCEPTION
                  WHEN OTHERS THEN
                    v_ndomkname := NULL;
                END;

                IF (v_ndomkname is not Null) THEN
                  v_tmp := v_tmp || '_' || v_ndomkname;
                END IF;
              END IF;

              --取得未收總金額
              BEGIN
                SELECT SUM(RECREM)
                  INTO v_recrem
                  FROM BAUNACPDTL
                 WHERE APNO = v_apno
                   AND SEQNO = v_seqno
                   AND RECREM > 0;
              EXCEPTION
                WHEN OTHERS THEN
                  v_recrem := 0;
              END;

              --取得承辦人分機號碼 Add 2011/01/17
              BEGIN
                SELECT DATA2 --承辦人分機號碼
                  INTO v_empext
                  FROM (SELECT DATA2
                          FROM (SELECT '1' ORDERID, DATA2
                                  FROM BCCMF09
                                 WHERE NVL(TRIM(TAL20), ' ') <> '0099'
                                   AND (PAYTYP || GROUPMK) LIKE
                                       (SUBSTR(r_base.APNO, 1, 2) || '%') -- Modify By ChungYu 2011/05/02
                                   AND (SUBSTR(r_base.APNO, 12, 1) IN
                                        (TAL11, TAL12, TAL13, TAL14, TAL15))
                                   AND (TRIM(STATUS) IS NULL OR STATUS <> 'D')
                                UNION
                                SELECT '2' ORDERID, DATA2
                                  FROM BCCMF09
                                 WHERE NVL(TRIM(TAL20), ' ') <> '0099'
                                   AND (PAYTYP || GROUPMK) LIKE
                                       (SUBSTR(r_base.APNO, 1, 2) || '%') -- Modify By ChungYu 2011/05/02
                                   AND (SUBSTR(TAL20, 1, 2) <=
                                        SUBSTR(r_base.APNO, 11, 2) AND
                                        SUBSTR(TAL20, 3, 2) >=
                                        SUBSTR(r_base.APNO, 11, 2))
                                   AND (TRIM(STATUS) IS NULL OR STATUS <> 'D')
                                UNION
                                SELECT '3' ORDERID, DATA2
                                  FROM BCCMF09
                                 WHERE NVL(TRIM(TAL20), ' ') <> '0099'
                                   AND (PAYTYP || GROUPMK) LIKE
                                       (SUBSTR(r_base.APNO, 1, 2) || '%') -- Modify By ChungYu 2011/05/02
                                   AND (SUBSTR(r_base.APNO, 11, 2) IN
                                        (TAL21, TAL22, TAL23, TAL24, TAL25))
                                   AND (TRIM(STATUS) IS NULL OR STATUS <> 'D'))
                         ORDER BY ORDERID)
                 WHERE ROWNUM = 1;
              EXCEPTION
                WHEN OTHERS THEN
                  v_empext := NULL;
              END;

              --查詢是否列為保密案件 Add By ChungYu 2015/10/27
              BEGIN
                IF (SUBSTR(v_apno, 1, 1) <> 'K') Then
                  SELECT NVL(PAYTYP, '00')
                    INTO v_paytyp
                    FROM BCCMF42
                   WHERE INSKD = '1'
                     AND (IDN = v_evtidnno OR IDN = v_benidnno)
                     AND ROWNUM = 1;
                ELSE
                  SELECT NVL(PAYTYP, '00')
                    INTO v_paytyp
                    FROM BCCMF42
                   WHERE INSKD = '1'
                     AND (IDN = v_evtidnno OR IDN = v_benidnno OR
                         IDN IN
                         (SELECT FAMIDNNO FROM BAFAMILY WHERE apno = v_apno))
                     AND ROWNUM = 1;
                END IF;

                IF (v_paytyp = '00') THEN
                  v_secretmk := 'Y';
                ELSIF ((SUBSTR(v_paytyp, 1, 1) = '4') AND
                      (SUBSTR(v_apno, 1, 1) = 'L')) THEN
                  v_secretmk := 'Y';
                ELSIF (SUBSTR(v_paytyp, 1, 1) = '5') AND
                      (SUBSTR(v_apno, 1, 1) = 'S') THEN
                  v_secretmk := 'Y';
                ELSIF (SUBSTR(v_paytyp, 1, 1) = '3') AND
                      (SUBSTR(v_apno, 1, 1) = 'K') THEN
                  v_secretmk := 'Y';
                ELSE
                  v_secretmk := NULL;
                END IF;
              EXCEPTION
                WHEN OTHERS THEN
                  v_secretmk := NULL;
                  v_paytyp   := NULL;
              END;
              --查詢是否列為保密案件 Add By ChungYu 2015/10/27

             -- IF ( p_alldata = 'Y' ) THEN  Modufy By ChugnYu 2019/09/13
                   INSERT INTO BAMEAPPSTS
                          (MAPNO,
                           APPNO,
                           SEQNO,
                           UNO,
                           PAYKIND,
                           IDN,
                           NAME,
                           BRDTE,
                           EVTIDNNO,
                           EVTEENAME,
                           EVTEEBIRT,
                           FTYPE,
                           APPDATE,
                           STATUS,
                           PMRK,
                           EVTDT,
                           VALSENI,
                           RECREM,
                           SYSID,
                           WAGE,
                           ISSUEAMT,
                           EMPEXT,
                           PAYYMS, -- 2015/04/09  Add By ChungYu
                           SECRETMK -- 2015/10/27  Add By ChungYu
                           )
                        VALUES
                          (v_mapno,
                           v_apno,
                           v_seqno,
                           v_uno,
                           v_paykindname,
                           v_benidnno,
                           v_beneename,
                           v_beneebirt,
                           v_evtidnno,
                           v_evteename,
                           v_evteebirt,
                           '2',
                           v_appdate,
                           v_tmp,
                           v_pmrk,
                           v_evtjobdate,
                           '',
                           v_recrem,
                           v_sisid,
                           '',
                           '',
                           v_empext,
                           v_payyms,
                           v_secretmk);
            IF ( p_alldata <> 'Y' ) THEN  --Modufy By ChugnYu 2019/09/13
                 INSERT INTO NPS.NPMEAPPSTS
                          (MAPNO,
                           APPNO,
                           SEQNO,
                           UNO,
                           PAYKIND,
                           IDN,
                           NAME,
                           BRDTE,
                           EVTIDNNO,
                           EVTEENAME,
                           EVTEEBIRT,
                           FTYPE,
                           APPDATE,
                           STATUS,
                           EVTDT,
                           VALSENI,
                           RECREM,
                           SYSID,
                           WAGE,
                           ISSUEAMT,
                           EMPEXT,
                           PAYYMS, -- 2015/04/09  Add By ChungYu
                           SECRETMK -- 2015/10/27  Add By ChungYu
                           )
                        VALUES
                          (v_mapno,
                           v_apno,
                           v_seqno,
                           v_uno,
                           v_paykindname,
                           v_benidnno,
                           v_beneename,
                           v_beneebirt,
                           v_evtidnno,
                           v_evteename,
                           v_evteebirt,
                           '2',
                           v_appdate,
                           v_tmp,
                           v_evtjobdate,
                           '',
                           v_recrem,
                           v_sisid,
                           '',
                           '',
                           v_empext,
                           v_payyms,
                           v_secretmk);
            END IF;
            EXCEPTION
              WHEN OTHERS THEN
                v_mapno     := '';
                v_countfail := v_countfail + 1;

            END;

            IF (v_mapno IS NOT NULL) THEN
              -- 取得核定檔資料
              v_RowCount := 0;
              FOR r_dapr IN c_dapr LOOP
                BEGIN
                  --BADAPR基本參數資料
                  IF (v_RowCount < 6) THEN
                    v_RowCount := v_RowCount + 1;
                    v_mapno    := r_dapr.APNO;
                    v_apno     := r_dapr.APNO;
                    v_payym    := r_dapr.PAYYM;
                    v_seqno    := r_dapr.SEQNO;
                    v_supamt   := r_dapr.SUPAMT;
                    v_recamt   := r_dapr.RECAMT;
                    --   v_tmp          := r_dapr.STATUS;

                    -- 2013/01/18 Add By ChungYu
                    v_tmp      := '';
                    v_issueamt := 0;
                    v_valseni  := '';
                    v_wage     := 0;
                    BEGIN
                      SELECT *
                        INTO v_RowBadapr
                        FROM BADAPR A
                       WHERE A.APNO = v_apno --v_mapno
                         AND A.SEQNO = v_seqno
                         AND A.PAYYM = v_payym
                         AND A.ISSUYM =
                             (SELECT MAX(B.ISSUYM)
                                FROM BADAPR B
                               WHERE B.APNO = v_apno
                                 AND B.SEQNO = v_seqno
                                 AND B.PAYYM = v_payym
                                 AND B.PAYKIND NOT IN ('34', '44', '54') -- 2015/09/16 ChungYu Add
                                 AND B.MTESTMK = 'F'
                                 AND B.APLPAYMK = '3'
                                 AND B.APLPAYDATE IS NOT NULL)
                         AND A.PAYKIND NOT IN ('34', '44', '54') -- 2015/09/16 ChungYu Add
                         AND A.MTESTMK = 'F'
                         AND A.APLPAYMK = '3'
                         AND A.APLPAYDATE IS NOT NULL;

                      -- 2015/09/16 ChungYu Add
                      SELECT SUM(ISSUEAMT)
                        INTO v_issueamt
                        FROM BADAPR A
                       WHERE A.APNO = v_apno --v_mapno
                         AND A.SEQNO = v_seqno
                         AND A.PAYYM = v_payym
                         AND A.ISSUYM =
                             (SELECT MAX(B.ISSUYM)
                                FROM BADAPR B
                               WHERE B.APNO = v_apno
                                 AND B.SEQNO = v_seqno
                                 AND B.PAYYM = v_payym
                                 AND B.MTESTMK = 'F'
                                 AND B.APLPAYMK = '3'
                                 AND B.APLPAYDATE IS NOT NULL)
                         AND A.MTESTMK = 'F'
                         AND A.APLPAYMK = '3'
                         AND A.APLPAYDATE IS NOT NULL;
                      -- 2015/09/16 ChungYu Add
                    EXCEPTION
                      WHEN OTHERS THEN
                        -- v_tmp := '';
                        v_RowBadapr := Null;
                    END;

                    IF v_RowBadapr.BADAPRID Is Not Null Then
                      v_tmp := CASE
                                 WHEN ((v_RowBadapr.STEXPNDMK IS NOT NULL) AND
                                      (v_RowBadapr.STEXPNDDATE IS NOT NULL)) THEN
                                  '止付' || '/'
                                 WHEN ((v_RowBadapr.REMITMK = '1') AND (v_RowBadapr.REMITDATE IS NOT NULL)) THEN
                                  '入帳' || '/'
                                 WHEN (v_RowBadapr.REMITMK = '2') THEN
                                  '退匯' || '/'
                                 WHEN (v_RowBadapr.REMITMK = '3') THEN
                                  '改匯' || '/'
                                 WHEN ((v_RowBadapr.APLPAYMK = '3') AND
                                      (v_RowBadapr.APLPAYDATE IS NOT NULL)) THEN
                                  '核付' || '/'
                                 ELSE
                                  ''
                               END;

                      v_mchktyp := CASE
                                     WHEN (v_RowBadapr.MCHKTYP = '1') THEN
                                      '新案'
                                     WHEN (v_RowBadapr.MCHKTYP = '2') THEN
                                      '續發案'
                                     WHEN (v_RowBadapr.MCHKTYP = '3') THEN
                                      '不給付案'
                                     WHEN (v_RowBadapr.MCHKTYP = '4') THEN
                                      '結案'
                                     WHEN (v_RowBadapr.MCHKTYP = '5') THEN
                                      '補發案'
                                     WHEN (v_RowBadapr.MCHKTYP = '6') THEN
                                      '暫緩給付案'
                                     ELSE
                                      ''
                                   END;

                      --      v_issueamt :=  v_RowBadapr.ISSUEAMT;   2015/09/16 Mark By ChungYu

                      IF (v_RowCount = '1') THEN
                        v_valseni := CASE
                                       WHEN (v_RowBadapr.APLPAYSENIY Is Null) And
                                            (v_RowBadapr.APLPAYSENIM Is Null) THEN
                                        ''
                                       ELSE
                                        v_RowBadapr.APLPAYSENIY || '年' || LPAD(v_RowBadapr.APLPAYSENIM, 2, '0') || '月'
                                     END;

                        v_wage := v_RowBadapr.INSAVGAMT;

                        -- 2013/06/07 ChungYu Add
                        Update BAMEAPPSTS
                           Set VALSENI = v_valseni, WAGE = v_wage
                         Where APPNO = v_apno
                           And SEQNO = v_seqno;
                        -- 2013/06/07 ChungYu Add

                      END IF;
                    ELSE
                      v_tmp      := '';
                      v_issueamt := 0;
                      v_valseni  := '00年00月';
                      v_wage     := 0;
                      v_mchktyp  := '';
                    END IF;
                    -- 2013/01/18 Add By ChungYu

                    --取得給付年月當月應收總額
                    BEGIN
                      SELECT SUM(RECAMT) RECAMT1
                        INTO v_recremtmp1
                        FROM BAUNACPDTL
                       WHERE APNO = v_apno
                         AND SEQNO = v_seqno
                         AND PAYYM <= v_payym;
                    EXCEPTION
                      WHEN OTHERS THEN
                        v_recremtmp1 := 0;
                    END;

                    --取得給付年月當月已收總額
                    BEGIN
                      SELECT SUM(RECAMT) RECAMT2
                        INTO v_recremtmp2
                        FROM BAACPDTL
                       WHERE APNO = v_apno
                         AND SEQNO = v_seqno
                         AND PAYYM <= v_payym;
                    EXCEPTION
                      WHEN OTHERS THEN
                        v_recremtmp2 := 0;
                    END;

                    --計算給付年月當月未收金額
                    v_recremdetl := v_recremtmp1 - v_recremtmp2;

                  --IF ( p_alldata = 'Y' ) THEN Modify By ChungYu 2019/09/13
                       INSERT INTO BAMEDAPR
                               (MAPNO,
                                APPNO,
                                SEQNO,
                                PAYYM,
                                IDN,
                                NAME,
                                BRDTE,
                                APPNAME,
                                ISSUEAMT,
                                SUPAMT,
                                RECAMT,
                                FTYPE,
                                STATUS,
                                SYSID,
                                RECREM,
                                MCHKTYP)
                             VALUES
                               (v_mapno,
                                v_apno,
                                v_seqno,
                                v_payym,
                                v_benidnno,
                                v_beneename,
                                v_beneebirt,
                                v_beneename,
                                v_issueamt,
                                v_supamt,
                                v_recamt,
                                '2',
                                v_tmp,
                                v_sisid,
                                v_recremdetl,
                                v_mchktyp);
                   IF ( p_alldata <> 'Y' ) THEN --Modify By ChungYu 2019/09/13
                       INSERT INTO NPS.NPMEDAPR
                               (MAPNO,
                                APPNO,
                                SEQNO,
                                PAYYM,
                                IDN,
                                NAME,
                                BRDTE,
                                APPNAME,
                                ISSUEAMT,
                                SUPAMT,
                                RECAMT,
                                FTYPE,
                                STATUS,
                                SYSID,
                                RECREM,
                                MCHKTYP)
                             VALUES
                               (v_mapno,
                                v_apno,
                                v_seqno,
                                v_payym,
                                v_benidnno,
                                v_beneename,
                                v_beneebirt,
                                v_beneename,
                                v_issueamt,
                                v_supamt,
                                v_recamt,
                                '2',
                                v_tmp,
                                v_sisid,
                                v_recremdetl,
                                v_mchktyp);
                     END IF;
                  END IF;
                EXCEPTION
                  WHEN OTHERS THEN
                    SP_BA_RECJOBLOG(p_job_no => p_jobno,
                                    p_job_id => 'SP_BA_NPB10W7905_THREAD01',
                                    p_step   => 'EXCEPTION',
                                    p_memo   => 'FOR r_dapr 錯誤:' || SQLERRM ||
                                                DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
                END;

              END LOOP;
              v_countok := v_countok + 1;
            END IF;

            IF (MOD(v_count, 4000) = 0) THEN
              COMMIT;
            END IF;

          EXCEPTION
            WHEN OTHERS THEN
              v_countfail := v_countfail + 1;
              SP_BA_RECJOBLOG(p_job_no => p_jobno,
                              p_job_id => 'SP_BA_NPB10W7905_THREAD01',
                              p_step   => 'EXCEPTION',
                              p_memo   => 'FOR r_base 錯誤:' || SQLERRM ||
                                          DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
          END;
        END LOOP;
        COMMIT;
        o_count     := v_count;
        o_countok   := v_countok;
        o_countfail := v_countfail;

      END;
    EXCEPTION
      WHEN OTHERS THEN
        SP_BA_RECJOBLOG(p_job_no => p_jobno,
                        p_job_id => 'SP_BA_NPB10W7905_THREAD01',
                        p_step   => 'EXCEPTION',
                        p_memo   => '錯誤:' || SQLERRM ||
                                    DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    END SP_BA_NPB10W7905_THREAD01;

    -- ============================================================================
    -- MAIN PROCESS
    -------------------------------------------------------------------------------
  BEGIN
    --勞保年金======================================================================================================
    BEGIN

      v_lstarttime := SYSTIMESTAMP;
      SP_BA_NPB10W7905_THREAD01( p_job_no     => p_jobno,
                                 p_bgn_apno   => p_bgn_apno,
                                 p_end_apno   => p_end_apno,
                                 o_count      => v_lcount,
                                 o_countok    => v_lcountok,
                                 o_countfail  => v_lcountfail,
                                 p_alldata    => p_alldata);

      v_lendtime := SYSTIMESTAMP;
      v_lspdtime := FN_BA_GETSPENDTIME(TO_CHAR(v_lstarttime, 'YYYYMMDDHH24MISS'),
                                   TO_CHAR(v_lendtime, 'YYYYMMDDHH24MISS'));
      v_lspdnum  := ROUND(86400 *
                          (TO_DATE(TO_CHAR(v_lendtime, 'YYYYMMDDHH24MISS'),
                                   'YYYYMMDDHH24MISS') -
                          TO_DATE(TO_CHAR(v_lstarttime, 'YYYYMMDDHH24MISS'),
                                   'YYYYMMDDHH24MISS')),
                          2);

      SP_BA_RECJOBLOG(p_job_no => p_jobno,
                      p_job_id => 'SP_BA_NPB10W7905_THREAD'||p_thread,
                      p_step   => 'END',
                      p_memo   => '勞保資料======================================================' ||
                                  CHR(13) || CHR(10) || '　全部件數：' || v_lcount || '件；' ||
                                  CHR(13) || CHR(10) || '　成功件數：' ||
                                  v_lcountok || '件；' || CHR(13) || CHR(10) ||
                                  '　失敗件數：' || v_lcountfail || '件；' ||
                                  CHR(13) || CHR(10) ||
                                  '  -----------------------------------' ||
                                  CHR(13) || CHR(10) || '　開始時間：' ||
                                  TO_CHAR(v_lstarttime,
                                          'YYYY/MM/DD HH24:MI:SS') ||
                                  CHR(13) || CHR(10) || '　結束時間：' ||
                                  TO_CHAR(v_lendtime,
                                          'YYYY/MM/DD HH24:MI:SS') ||
                                  CHR(13) || CHR(10) || '　執行時間：' ||
                                  v_lspdtime || ' (' || v_lspdnum || ' 秒)' ||
                                  CHR(13) || CHR(10) ||
                                  '==============================================================');

    EXCEPTION
      WHEN OTHERS THEN
        SP_BA_RECJOBLOG(p_job_no => p_jobno,
                        p_job_id => 'SP_BA_NPB10W7905_THREAD'||p_thread,
                        p_step   => 'EXCEPTION',
                        p_memo   => 'SP_BA_NPB10W7905_THREAD EXEC SP_BA_NPB10W7905_THREAD FAIL!  錯誤:' ||
                                    SQLERRM ||
                                    DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    END;

  END;
EXCEPTION
  WHEN OTHERS THEN
    --ROLLBACK;
    SP_BA_RECJOBLOG(p_job_no => p_jobno,
                    p_job_id => 'SP_BA_NPB10W7905_THREAD'||p_thread,
                    p_step   => 'EXCEPTION',
                    p_memo   => '錯誤:' || SQLERRM ||
                                DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);

END SP_BA_NPB10W7905_THREAD;
/