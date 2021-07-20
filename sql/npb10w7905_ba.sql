CREATE OR REPLACE PROCEDURE BA.NPB10W7905_BA(p_jobno IN VARCHAR2,
                                          p_all   IN VARCHAR2) authid definer IS

  --p_jobno               mmjoblog.job_no%TYPE := PKG_BATCH.GET_SEQ_MASK('JOBCENTER');

BEGIN
  -- ============================================================================
  --- 程式名稱 : 產生對外服務主檔、核定檔資料
  -- 程式目的 :  產生對外服務主檔、核定檔資料
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
    PROCEDURE NBAB10W7905(p_jobno     IN mmjoblog.job_no%TYPE,
                          o_count     OUT NUMBER, --總件數
                          o_countok   OUT NUMBER, --成功筆數
                          o_countfail OUT NUMBER, --失敗筆數
                          p_all       IN VARCHAR2 --若傳入值為Y或y，直接執行全量資料 Added By Kiyomi 2013/04/11
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
      --              NPMEAPPSTS  國/勞保年金給付受理狀況檔
      --              NPMEDAPR    國/勞保年金給付清單檔
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
      --      ChungYu    2011/01/17  v.16 NPMEAPPSTS新增平均薪資、核定金額、員工分機(承辦)三個欄位
      --      ChungYu    2011/02/21  v.17 CASETYP = 3 (不給付案)，STATUS加入不給付註記說明
      --      ChungYu    2011/05/03  v.18 LSUBNO(最後單位)修改為APUBNO(申請單位)，修改承辦人員分案原則
      --      ChungYu    2012/02/15  v.19 因失能遺屬尚未上線，故現行將處理狀態修改為"作業流程中，如有疑義請洽承辦人"
      --      ChungYu    2013/01/18  v.20 因DB 三合一後，本支Procedure執行時間常常過久，故將讀取核定檔資料修改為分段讀取。
      --      ChungYu    2013/02/05  v.21 再次調整讀取核定檔資料的條件。
      --      Kiyomi     2013/04/11  v.22 判斷月核後的星期六、日抓全部資料，其餘日子只抓「PROCSTAT < "50"」的資料
      -- ============================================================================

      DECLARE
        --使用參數
        --BAAPPBASE
        v_mapno       BA.BAAPPBASE.MAPNO%TYPE := ''; -- 主受理編號
        v_apno        BA.BAAPPBASE.APNO%TYPE := ''; -- 受理編號
        v_seqno       BA.BAAPPBASE.SEQNO%TYPE := ''; -- 序號
        v_uno         BA.BAAPPBASE.APUBNO%TYPE := ''; -- 申請單位保險證號  Modify By ChungYu 2011/05/03
        v_paykindname VARCHAR2(100) := ''; -- 給付種類(中文)
        v_appdate     BA.BAAPPBASE.APPDATE%TYPE := ''; -- 申請日期(收件日期)
        v_evteename   BA.BAAPPBASE.EVTNAME%TYPE := ''; -- 事故者姓名
        v_evtidnno    BA.BAAPPBASE.EVTIDNNO%TYPE := ''; -- 事故者身分證號
        v_evteebirt   BA.BAAPPBASE.EVTBRDATE%TYPE := ''; -- 事故者出生日期
        v_evtjobdate  BA.BAAPPBASE.EVTJOBDATE%TYPE := ''; -- 事故日期
        v_casetyp     BA.BAAPPBASE.CASETYP%TYPE := ''; -- 案件狀態  Add By ChungYu  2011/02/21
        v_ndomkname   VARCHAR2(300) := ''; -- 處理註記名稱  Add By ChungYu  2011/02/21
        --BADAPR
        v_payym    BA.BADAPR.PAYYM%TYPE := ''; -- 給付年月
        v_issueamt BA.BADAPR.ISSUEAMT%TYPE := 0; -- 核定金額
        v_recamt   BA.BADAPR.RECAMT%TYPE := 0; -- 收回金額
        v_supamt   BA.BADAPR.SUPAMT%TYPE := 0; -- 補發金額

        --共用參數
        v_beneename BA.BAAPPBASE.BENNAME%TYPE := ''; -- 受益人姓名
        v_benidnno  BA.BAAPPBASE.BENIDNNO%TYPE := ''; -- 受益人身分證號
        v_beneebirt BA.BAAPPBASE.BENBRDATE%TYPE := ''; -- 受益人出生日期

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
        v_recremtmp1 NUMBER := 0; -- 給付年月應收總額
        v_recremtmp2 NUMBER := 0; -- 給付年月已收總額

        -- 2013/01/18 Add By ChungYu 核定檔核付資料
        v_RowCount  NUMBER := 0;
        v_RowBadapr BA.BADAPR%ROWTYPE := NULL;
        -- 2013/01/18 Add By ChungYu 核定檔核付資料

        -- 2013/04/11 Added By Kiyomi
        v_Today     VARCHAR2(8) := ''; --  SYSDATE
        v_NextSat   VARCHAR2(8) := ''; -- 月核日後第一個星期六
        v_NextSun   VARCHAR2(8) := ''; -- 月核日後第一個星期日
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
          STATUS      VARCHAR2(100));
        r_base t_emprec;
        -- 2013/04/11 Added By Kiyomi

        -- ----------------------------------------------------------------------------
        -- CURSOR BEGIN
        -------------------------------------------------------------------------------
        --取得主檔資料
        /*       CURSOR c_base IS
        SELECT APNO,
               SEQNO,
               PAYKIND,
                CASE  WHEN (PAYKIND = '35') THEN '失能年金'
                      WHEN (PAYKIND = '36') THEN '國保年金併計失能'
                      WHEN (PAYKIND = '37') THEN '職災失能補償一次金'
                      WHEN (PAYKIND = '38') THEN '失能年金併計國保年金'
                      WHEN (PAYKIND = '39') THEN '請領一次失能給付差額'
                      WHEN (PAYKIND = '45') THEN '老年年金'
                      WHEN (PAYKIND = '48') THEN '老年年金併計國保年資'
                      WHEN (PAYKIND = '49') THEN '請領一次老年給付差額'
                      WHEN (PAYKIND = '57') THEN '職災死亡補償一次金'
                      WHEN (PAYKIND = '58') THEN '喪葬津貼'
                      WHEN (PAYKIND IN ('55','56','59')) THEN '遺屬年金'
                      END AS PAYKINDNAME,
                APPDATE,
                EVTIDNNO,
                EVTNAME,
                EVTBRDATE,
                EVTJOBDATE,
                BENIDNNO,
                BENNAME,
                BENBRDATE,
                APUBNO,
                CASETYP,
                PROCSTAT,
                CASE WHEN ( (PAYKIND IN ('45','48','49')) AND (CASETYP = '1') AND ( PROCSTAT = '00'))  THEN '新案受理中'
                     WHEN ( (PAYKIND IN ('45','48','49')) AND (CASETYP = '1') AND ( PROCSTAT <> '00')) THEN '新案審核中'
                     WHEN ( (PAYKIND IN ('45','48','49')) AND (CASETYP = '2') AND ( PROCSTAT = '50'))  THEN '續發案核付　' || ISSUEAMT  -- 2013/04/11 Modified By Kiyomi
                     WHEN ( (PAYKIND IN ('45','48','49')) AND (CASETYP = '2') AND ( PROCSTAT <> '50')) THEN '續發案改核'
                     WHEN ( (PAYKIND IN ('45','48','49')) AND (CASETYP = '3')) THEN '不給付案'
                     WHEN ( (PAYKIND IN ('45','48','49')) AND (CASETYP = '4')) THEN '結案'
                     WHEN ( (PAYKIND IN ('45','48','49')) AND (CASETYP = '6')) THEN '暫緩給付案'                           -- Modify By ChungYu 2011/05/02
                     ELSE '作業流程中，如有疑義請洽承辦人'
                     END AS STATUS
           FROM BA.BAAPPBASE
          WHERE SEQNO != (CASE WHEN (PAYKIND LIKE ('5%')) THEN '0000'
                          ELSE 'XXXX' END)
            AND (CASEMK <> 'D' OR CASEMK is NULL)
            AND BENEVTREL NOT IN ('A','C','F','N','Z','O')
           ORDER BY APNO, SEQNO;*/

        -------------------------------------------------------------------------------
        --取得核定金額資料
        CURSOR c_dapr IS
          SELECT DISTINCT A.APNO,
                          A.SEQNO,
                          A.PAYYM,
                          SUM(A.APLPAYAMT) AS APLPAYAMT,
                          SUM(A.RECAMT) AS RECAMT,
                          SUM(A.SUPAMT) AS SUPAMT
            FROM BA.BADAPR A
           WHERE A.APNO = v_mapno --v_mapno
             AND A.SEQNO = v_seqno
             AND A.PAYYM >=
                 (select TO_CHAR(ADD_MONTHS(TO_DATE(max(B.PAYYM), 'YYYYMM'),
                                            -7),
                                 'YYYYMM')
                    from BA.BADAPR B
                   where B.APNO = A.APNO
                     and B.SEQNO = A.SEQNO
                     and B.MTESTMK = 'F'
                     and B.APLPAYMK = '3'
                     and B.APLPAYDATE is not null) -- 2013/04/11 Modified By Kiyomi
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
                       '              WHEN (PAYKIND = ''39'') THEN ''請領一次失能給付差額''' ||
                       '              WHEN (PAYKIND = ''45'') THEN ''老年年金''' ||
                       '              WHEN (PAYKIND = ''48'') THEN ''老年年金併計國保年資''' ||
                       '              WHEN (PAYKIND = ''49'') THEN ''請領一次老年給付差額''' ||
                       '              WHEN (PAYKIND = ''57'') THEN ''職災死亡補償一次金''' ||
                       '              WHEN (PAYKIND = ''58'') THEN ''喪葬津貼''' ||
                       '              WHEN (PAYKIND IN (''55'',''56'',''59'')) THEN ''遺屬年金''' ||
                       '              END AS PAYKINDNAME,' ||
                       '        APPDATE,' || '        EVTIDNNO,' ||
                       '        EVTNAME,' || '        EVTBRDATE,' ||
                       '        EVTJOBDATE,' || '        BENIDNNO,' ||
                       '        BENNAME,' || '        BENBRDATE,' ||
                       '        APUBNO,' || '        CASETYP,' ||
                       '        PROCSTAT,' ||
                       '        CASE WHEN ( (PAYKIND IN (''45'',''48'',''49'')) AND (CASETYP = ''1'') AND ( PROCSTAT = ''00''))  THEN ''新案受理中''' ||
                       '             WHEN ( (PAYKIND IN (''45'',''48'',''49'')) AND (CASETYP = ''1'') AND ( PROCSTAT <> ''00'')) THEN ''新案審核中''' ||
                       '             WHEN ( (PAYKIND IN (''45'',''48'',''49'')) AND (CASETYP = ''2'') AND ( PROCSTAT = ''50''))  THEN ''續發案核付　'' || ISSUEAMT' ||
                       '             WHEN ( (PAYKIND IN (''45'',''48'',''49'')) AND (CASETYP = ''2'') AND ( PROCSTAT <> ''50'')) THEN ''續發案改核''' ||
                       '             WHEN ( (PAYKIND IN (''45'',''48'',''49'')) AND (CASETYP = ''3'')) THEN ''不給付案''' ||
                       '             WHEN ( (PAYKIND IN (''45'',''48'',''49'')) AND (CASETYP = ''4'')) THEN ''結案''' ||
                       '             WHEN ( (PAYKIND IN (''45'',''48'',''49'')) AND (CASETYP = ''6'')) THEN ''暫緩給付案''' ||
                       '             ELSE ''作業流程中，如有疑義請洽承辦人''' ||
                       '             END AS STATUS' ||
                       '   FROM BA.BAAPPBASE' ||
                       '  WHERE SEQNO != (CASE WHEN (PAYKIND LIKE (''5%'')) THEN ''0000''' ||
                       '                  ELSE ''XXXX'' END)' ||
                       '    AND (CASEMK <> ''D'' OR CASEMK is NULL)' ||
                       '    AND BENEVTREL NOT IN (''A'',''C'',''F'',''N'',''Z'',''O'')';

        --取得月核定日期參數資料
        BEGIN
          SELECT TO_CHAR(SYSDATE, 'YYYYMMDD') AS TODAY,
                 TO_CHAR(NEXT_DAY(TO_DATE(ISSUDATE, 'YYYYMMDD'), 7),
                         'YYYYMMDD') AS NEXTSAT,
                 TO_CHAR(NEXT_DAY(TO_DATE(ISSUDATE, 'YYYYMMDD'), 1),
                         'YYYYMMDD') AS NEXTSUN
            INTO v_Today, v_NextSat, v_NextSun
            FROM BA.BAPAISSUDATE
           WHERE ISSUYM = to_char(sysdate, 'YYYYMM')
             AND ISSUTYP = '1'
             AND PAYCODE = 'L'
             AND BANKKIND = '1';
        EXCEPTION
          WHEN OTHERS THEN
            v_Today   := NULL;
            v_NextSat := NULL;
            v_NextSun := NULL;
        END;

        -- Added By Kiyomi 2013/04/15
        IF (v_Today = v_NextSat OR v_Today = v_NextSun OR p_all = 'Y' OR
           p_all = 'y') THEN
          v_stmt_str2 := ' ORDER BY APNO, SEQNO';

          DELETE FROM BA.BAMEAPPSTS;
          DELETE FROM BA.BAMEDAPR;
          COMMIT;

        ELSE
          v_stmt_str2 := ' AND PROCSTAT < ''50'' ORDER BY APNO, SEQNO';

          delete from BA.BAMEAPPSTS
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

          delete from BA.Bamedapr
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

        /*FOR r_base IN c_base LOOP*/

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
              v_evtjobdate  := r_base.EVTJOBDATE;
              v_casetyp     := r_base.CASETYP; --Add By ChungYu 2011/02/21

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
                            FROM BA.BAPANDOMK
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
                  FROM BA.BAUNACPDTL
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
                                  FROM BBCMF09
                                 WHERE NVL(TRIM(TAL20), ' ') <> '0099'
                                   AND (PAYTYP || GROUPMK) LIKE
                                       (SUBSTR(r_base.APNO, 1, 2) || '%') -- Modify By ChungYu 2011/05/02
                                   AND (SUBSTR(r_base.APNO, 12, 1) IN
                                        (TAL11, TAL12, TAL13, TAL14, TAL15))
                                   AND (TRIM(STATUS) IS NULL OR STATUS <> 'D')
                                UNION
                                SELECT '2' ORDERID, DATA2
                                  FROM BBCMF09
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
                                  FROM BBCMF09
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

              INSERT INTO BAMEAPPSTS -- Modified By Kiyomi 2013/04/11
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
                 EMPEXT)
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
                 v_valseni,
                 v_recrem,
                 v_sisid,
                 v_wage,
                 '',
                 v_empext);
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
                        FROM BA.BADAPR A
                       WHERE A.APNO = v_apno --v_mapno
                         AND A.SEQNO = v_seqno
                         AND A.PAYYM = v_payym
                         AND A.ISSUYM =
                             (SELECT MAX(B.ISSUYM)
                                FROM BA.BADAPR B
                               WHERE B.APNO = v_apno
                                 AND B.SEQNO = v_seqno
                                 AND B.PAYYM = v_payym
                                 AND B.MTESTMK = 'F'
                                 AND B.APLPAYMK = '3'
                                 AND B.APLPAYDATE IS NOT NULL)
                         AND A.MTESTMK = 'F'
                         AND A.APLPAYMK = '3'
                         AND A.APLPAYDATE IS NOT NULL;

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

                      v_issueamt := v_RowBadapr.ISSUEAMT;

                      IF (v_RowCount = '1') THEN
                        v_valseni := CASE
                                       WHEN (v_RowBadapr.APLPAYSENIY Is Null) And
                                            (v_RowBadapr.APLPAYSENIM Is Null) THEN
                                        ''
                                       ELSE
                                        v_RowBadapr.APLPAYSENIY || '年' || LPAD(v_RowBadapr.APLPAYSENIM, 2, '0') || '月'
                                     END;

                        v_wage := v_RowBadapr.INSAVGAMT;
                      END IF;
                    ELSE
                      v_tmp      := '';
                      v_issueamt := 0;
                      v_valseni  := '00年00月';
                      v_wage     := 0;
                    END IF;
                    -- 2013/01/18 Add By ChungYu

                    --取得給付年月當月應收總額
                    BEGIN
                      SELECT SUM(RECAMT) RECAMT1
                        INTO v_recremtmp1
                        FROM BA.BAUNACPDTL
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
                        FROM BA.BAACPDTL
                       WHERE APNO = v_apno
                         AND SEQNO = v_seqno
                         AND PAYYM <= v_payym;
                    EXCEPTION
                      WHEN OTHERS THEN
                        v_recremtmp2 := 0;
                    END;

                    --計算給付年月當月未收金額
                    v_recremdetl := v_recremtmp1 - v_recremtmp2;

                    INSERT INTO BAMEDAPR -- Modified By Kiyomi 2013/04/11
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
                       RECREM)
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
                       v_recremdetl);

                  END IF;
                EXCEPTION
                  WHEN OTHERS THEN
                    NPS.RECORD_JOB_LOG(p_job_no => p_jobno,
                                   p_job_id => 'NPB10W7905_BA',
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
              NPS.RECORD_JOB_LOG(p_job_no => p_jobno,
                             p_job_id => 'NPB10W7905_BA',
                             p_step   => 'EXCEPTION',
                             p_memo   => 'FOR r_base 錯誤:' || SQLERRM ||
                                         DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
          END;

        END LOOP;

        -- 2013/04/11 Added By Kiyomi
        Insert Into NPMEAPPSTS
          Select * From BA.BAMEAPPSTS;
        Insert Into NPMEDAPR
          Select * From BA.BAMEDAPR;
        -- 2013/04/11 Added By Kiyomi

        COMMIT;
        o_count     := v_count;
        o_countok   := v_countok;
        o_countfail := v_countfail;

      END;
    EXCEPTION
      WHEN OTHERS THEN
        dbms_output.put_line('FOR r_base 錯誤: ' || SQLERRM /*||
                                                                                                                    DBMS_UTILITY.FORMAT_ERROR_BACKTRACE*/);
        NPS.RECORD_JOB_LOG(p_job_no => p_jobno,
                       p_job_id => 'NPB10W7905_BA',
                       p_step   => 'EXCEPTION',
                       p_memo   => '錯誤:' || SQLERRM ||
                                   DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    END NBAB10W7905;

    -- ============================================================================
    -- MAIN PROCESS
    -------------------------------------------------------------------------------
  BEGIN
    --勞保年金======================================================================================================
    BEGIN

      v_lstarttime := SYSTIMESTAMP;

      NPS.RECORD_JOB_LOG(p_job_no => p_jobno,
                     p_job_id => 'NPB10W7905_BA',
                     p_step   => 'BEGIN',
                     p_memo   => '開始產生資料,開始時間：' ||
                                 TO_CHAR(v_lstarttime,
                                         'YYYY/MM/DD HH24:MI:SS'));

      NBAB10W7905(p_jobno     => p_jobno,
                  o_count     => v_lcount,
                  o_countok   => v_lcountok,
                  o_countfail => v_lcountfail,
                  p_all       => p_all);

      v_lendtime := SYSTIMESTAMP;
      v_lspdtime := NPS.GET_SPEND_TIME(TO_CHAR(v_lstarttime, 'YYYYMMDDHH24MISS'),
                                   TO_CHAR(v_lendtime, 'YYYYMMDDHH24MISS'));
      v_lspdnum  := ROUND(86400 *
                          (TO_DATE(TO_CHAR(v_lendtime, 'YYYYMMDDHH24MISS'),
                                   'YYYYMMDDHH24MISS') -
                          TO_DATE(TO_CHAR(v_lstarttime, 'YYYYMMDDHH24MISS'),
                                   'YYYYMMDDHH24MISS')),
                          2);

      NPS.RECORD_JOB_LOG(p_job_no => p_jobno,
                     p_job_id => 'NPB10W7905_BA',
                     p_step   => 'END',
                     p_memo   => '勞保資料======================================================' ||
                                 CHR(13) || CHR(10) || '　全部件數：' || v_lcount || '件；' ||
                                 CHR(13) || CHR(10) || '　成功件數：' ||
                                 v_lcountok || '件；' || CHR(13) || CHR(10) ||
                                 '　失敗件數：' || v_lcountfail || '件；' || CHR(13) ||
                                 CHR(10) ||
                                 '  -----------------------------------' ||
                                 CHR(13) || CHR(10) || '　開始時間：' ||
                                 TO_CHAR(v_lstarttime,
                                         'YYYY/MM/DD HH24:MI:SS') || CHR(13) ||
                                 CHR(10) || '　結束時間：' ||
                                 TO_CHAR(v_lendtime, 'YYYY/MM/DD HH24:MI:SS') ||
                                 CHR(13) || CHR(10) || '　執行時間：' ||
                                 v_lspdtime || ' (' || v_lspdnum || ' 秒)' ||
                                 CHR(13) || CHR(10) ||
                                 '==============================================================');

    EXCEPTION
      WHEN OTHERS THEN
        NPS.RECORD_JOB_LOG(p_job_no => p_jobno,
                       p_job_id => 'NPB10W7905_BA',
                       p_step   => 'EXCEPTION',
                       p_memo   => 'NBAB10W7905 EXEC NBAB10W7905 FAIL!  錯誤:' ||
                                   SQLERRM ||
                                   DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    END;

  END;
EXCEPTION
  WHEN OTHERS THEN
    --ROLLBACK;
    NPS.RECORD_JOB_LOG(p_job_no => p_jobno,
                   p_job_id => 'NPB10W7905_BA',
                   p_step   => 'EXCEPTION',
                   p_memo   => '錯誤:' || SQLERRM ||
                               DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);

END NPB10W7905_BA;
/

