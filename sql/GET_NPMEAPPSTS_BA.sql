CREATE OR REPLACE FUNCTION BA.GET_NPMEAPPSTS_BA(p_idn IN VARCHAR2, p_ebdate IN VARCHAR2, p_name IN VARCHAR2)

--
-- 程式目的：產生對外服務主檔資料
-- 參數：
--     名稱                  輸出入     型態       預設值     備註說明
-- ----------------------------------------------------------------------------
--     p_idn                 IN      VARCHAR2                身份證號
--     p_ebdate              IN      VARCHAR2                出生日期
--     p_name                IN      VARCHAR2                姓名
-- 程式紀錄
--     人員                  日期      備註說明
-- ----------------------------------------------------------------------------
--     ChugnYu            2019/09/18    v1.0 初版
-- ============================================================================
   RETURN NPS.NPMEAPPSTS_TAB
IS

BEGIN

   DECLARE

        NPMEAPPSTS    NPS.NPMEAPPSTS_TAB := NPS.NPMEAPPSTS_TAB();

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

        --共用參數
        v_beneename BAAPPBASE.BENNAME%TYPE := ''; -- 受益人姓名
        v_benidnno  BAAPPBASE.BENIDNNO%TYPE := ''; -- 受益人身分證號
        v_beneebirt BAAPPBASE.BENBRDATE%TYPE := ''; -- 受益人出生日期
        v_recrem     NUMBER := 0; -- 未收總金額
        v_sisid      VARCHAR2(2) := 'BA'; -- 系統別  NP:國保   BA:勞保年金
        v_empext     VARCHAR2(12) := ''; -- 員工分機(承辦人)

        --計算用參數
        v_tmp        VARCHAR2(100) := '';
        v_secretmk   VARCHAR2(1) := '';
        v_paytyp     VARCHAR2(2) := '';
        v_payyms     VARCHAR2(6) := '';
        v_valseni    VARCHAR2(10) := ''; -- 事故者實付年資(XX年XX月)
        v_wage       NUMBER := 0; -- 平均薪資
        v_RowBadapr  BADAPR%ROWTYPE := NULL;
-- ============================================================================
-- CURSOR AREA
-------------------------------------------------------------------------------
    --取得勞保年金主檔資料
    CURSOR c_main IS

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
                PAYYMS,                                                      --  2015/04/09 Add By ChungYu
                CASE WHEN ( (PAYKIND NOT IN ('36','38')) AND (CASETYP = '1') AND ( PROCSTAT = '00'))  THEN '新案受理中'
                     WHEN ( (PAYKIND NOT IN ('36','38')) AND (CASETYP = '1') AND ( PROCSTAT <> '00')) THEN '新案審核中'
                     WHEN ( (PAYKIND NOT IN ('36','38')) AND (CASETYP = '2') AND ( PROCSTAT = '50'))  THEN '續發案核付　' || ISSUEAMT
                     WHEN ( (PAYKIND NOT IN ('36','38')) AND (CASETYP = '2') AND ( PROCSTAT <> '50')) THEN '續發案改核'
                     WHEN ( (PAYKIND NOT IN ('36','38')) AND (CASETYP = '3')) THEN '不給付案'
                     WHEN ( (PAYKIND NOT IN ('36','38')) AND (CASETYP = '4')) THEN '結案'
                     WHEN ( (PAYKIND NOT IN ('36','38')) AND (CASETYP = '6')) THEN '暫緩給付案'                           -- Modify By ChungYu 2013/12/31 所有案件均提供查詢
                     ELSE '作業流程中，如有疑義請洽承辦人'                                                                -- 除失能 36 & 38 案除外。
                     END AS STATUS
           FROM BAAPPBASE
          WHERE SEQNO != (CASE WHEN (PAYKIND LIKE ('5%')) THEN '0000'
                          ELSE 'XXXX' END)
            AND (CASEMK <> 'D' OR CASEMK is NULL)
            AND BENEVTREL NOT IN ('A','C','F','N','Z','O')
            AND ((EVTIDNNO = p_idn OR BENIDNNO = p_idn) OR
                 (EVTBRDATE = p_ebdate AND EVTNAME = p_name) OR
                 (BENBRDATE = p_ebdate AND BENNAME = p_name))
          ORDER BY APNO, SEQNO;

-------------------------------------------------------------------------------
   --寫入資料檔
   PROCEDURE into_NPMEAPPSTSDATA

   IS

   BEGIN

      NPMEAPPSTS.EXTEND;

      NPMEAPPSTS(NPMEAPPSTS.LAST) := NPS.NPMEAPPSTS_OBJ(v_mapno,       -- 主受理編號
                                                        v_apno,        -- 受理編號
                                                        v_seqno,       -- 序號
                                                        v_uno,         -- 申請單位保險證號
                                                        v_paykindname, -- 給付種類(中文)
                                                        v_benidnno,    -- 受益人身分證號
                                                        v_beneename,   -- 受益人姓名
                                                        v_beneebirt,   -- 受益人出生日期
                                                        v_evtidnno,    -- 事故者身分證號
                                                        v_evteename,   -- 事故者姓名
                                                        v_evteebirt,   -- 事故者出生日期
                                                        '2',           -- 保險別 1:國保 2:勞保年金 3:老基保
                                                        v_appdate,     -- 受理日期
                                                        v_tmp,         -- 處理狀況
                                                        v_evtjobdate,  -- 事故日期
                                                        v_valseni,     -- 年資
                                                        v_recrem,      -- 未收總金額
                                                        v_sisid,       -- 系統別  NP:國保   BA:勞保年金  TO:老基保
                                                        v_wage,        -- 平均薪資
                                                        '',            -- 核定金額
                                                        v_empext,      -- 員工分機(承辦)
                                                        v_payyms,      -- 發放起月
                                                        v_secretmk     -- 保密案件註記
                                                        );
    END;
-------------------------------------------------------------------------------
-- ============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
-- ============================================================================
-------------------------------------------------------------------------------

   BEGIN

      FOR r_base IN c_main LOOP

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
              -- 2013/01/18 Add By ChungYu
                    v_valseni  := '';
                    v_wage     := 0;
                    BEGIN
                      SELECT *
                        INTO v_RowBadapr
                        FROM BADAPR A
                       WHERE A.APNO = v_apno --v_mapno
                         AND A.SEQNO = '0000'
                         AND A.ISSUYM =
                             (SELECT MAX(B.ISSUYM)
                                FROM BADAPR B
                               WHERE B.APNO = v_apno
                                 AND B.SEQNO = v_seqno
                                 AND B.PAYKIND NOT IN ('34', '44', '54') -- 2015/09/16 ChungYu Add
                                 AND B.MTESTMK = 'F'
                                 AND B.APLPAYMK = '3'
                                 AND B.APLPAYDATE IS NOT NULL)
                         AND A.PAYKIND NOT IN ('34', '44', '54') -- 2015/09/16 ChungYu Add
                         AND A.MTESTMK = 'F'
                         AND A.APLPAYMK = '3'
                         AND A.APLPAYDATE IS NOT NULL
                         AND ROWNUM = 1;


                    EXCEPTION
                      WHEN OTHERS THEN
                        -- v_tmp := '';
                        v_RowBadapr := Null;
                    END;
                    IF v_RowBadapr.BADAPRID Is Not Null Then

                        v_valseni := CASE
                                       WHEN (v_RowBadapr.APLPAYSENIY Is Null) And
                                            (v_RowBadapr.APLPAYSENIM Is Null) THEN
                                        ''
                                       ELSE
                                        v_RowBadapr.APLPAYSENIY || '年' || LPAD(v_RowBadapr.APLPAYSENIM, 2, '0') || '月'
                                     END;

                        v_wage := v_RowBadapr.INSAVGAMT;

                    ELSE

                      v_valseni  := '00年00月';
                      v_wage     := 0;
                    END IF;
                    -- 2013/01/18 Add By ChungYu



        into_NPMEAPPSTSDATA;

      END LOOP;

   RETURN NPMEAPPSTS;

   EXCEPTION
      WHEN OTHERS THEN
         NULL;
   END;

END GET_NPMEAPPSTS_BA;
/