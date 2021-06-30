CREATE OR REPLACE FUNCTION BA.GET_NPMEDAPR_BA(p_idn IN VARCHAR2, p_ebdate IN VARCHAR2, p_name IN VARCHAR2, p_months IN VARCHAR2)

--
-- 程式目的：產生對外服務主檔資料
-- 參數：
--     名稱                  輸出入     型態       預設值     備註說明
-- ----------------------------------------------------------------------------
--     p_idn                 IN      VARCHAR2                身份證號
--     p_ebdate              IN      VARCHAR2                出生日期
--     p_name                IN      VARCHAR2                姓名
--     p_months              IN      VARCHAR2                核定月數
-- 程式紀錄
--     人員                  日期      備註說明
-- ----------------------------------------------------------------------------
--     ChugnYu            2019/09/18    v1.0 初版
-- ============================================================================
   RETURN NPS.NPMEDAPR_TAB
IS

BEGIN

   DECLARE

        NPMEDAPR      NPS.NPMEDAPR_TAB := NPS.NPMEDAPR_TAB();

        --使用參數
        --BAAPPBASE
        v_mapno       BAAPPBASE.MAPNO%TYPE := ''; -- 主受理編號
        v_apno        BAAPPBASE.APNO%TYPE := ''; -- 受理編號
        v_seqno       BAAPPBASE.SEQNO%TYPE := ''; -- 序號

        --BADAPR
        v_payym    BADAPR.PAYYM%TYPE := ''; -- 給付年月
        v_issueamt BADAPR.ISSUEAMT%TYPE := 0; -- 核定金額
        v_recamt   BADAPR.RECAMT%TYPE := 0; -- 收回金額
        v_supamt   BADAPR.SUPAMT%TYPE := 0; -- 補發金額

        --共用參數
        v_beneename BAAPPBASE.BENNAME%TYPE := ''; -- 受益人姓名
        v_benidnno  BAAPPBASE.BENIDNNO%TYPE := ''; -- 受益人身分證號
        v_beneebirt BAAPPBASE.BENBRDATE%TYPE := ''; -- 受益人出生日期


        v_recremdetl NUMBER := 0; -- 未收金額(依給付年月計算)
        v_sisid      VARCHAR2(2) := 'BA'; -- 系統別  NP:國保   BA:勞保年金
        v_tmp        VARCHAR2(100) := '';
        v_recremtmp1 NUMBER := 0; -- 給付年月應收總額
        v_recremtmp2 NUMBER := 0; -- 給付年月已收總額

        -- 2013/01/18 Add By ChungYu 核定檔核付資料
        v_RowBadapr BADAPR%ROWTYPE := NULL;
        v_mchktyp VARCHAR2(30) := '';

-- ============================================================================
-- CURSOR AREA
-------------------------------------------------------------------------------
    --取得勞保年金主檔檔資料
    CURSOR c_main IS

        SELECT  APNO,
                SEQNO,
                EVTBRDATE,
                BENIDNNO,
                BENNAME,
                BENBRDATE,
                CASETYP
           FROM BAAPPBASE
          WHERE SEQNO != (CASE WHEN (PAYKIND LIKE ('5%')) THEN '0000'
                          ELSE 'XXXX' END)
            AND (CASEMK <> 'D' OR CASEMK is NULL)
            AND BENEVTREL NOT IN ('A','C','F','N','Z','O')
            AND ((EVTIDNNO = p_idn OR BENIDNNO = p_idn) OR
                 (EVTBRDATE = p_ebdate AND EVTNAME = p_name) OR
                 (BENBRDATE = p_ebdate AND BENNAME = p_name))
          ORDER BY APNO, SEQNO;

   --取得勞保年金主檔檔資料
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

-------------------------------------------------------------------------------
   --寫入資料檔
   PROCEDURE into_NPMEDAPRDATA

   IS

   BEGIN

      NPMEDAPR.EXTEND;

      NPMEDAPR(NPMEDAPR.LAST) := NPS.NPMEDAPR_OBJ(v_mapno,
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
                                                  v_tmp,
                                                  '',
                                                  '2',
                                                  v_sisid,
                                                  v_recremdetl,
                                                  '',
                                                  v_mchktyp
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
              v_benidnno    := r_base.BENIDNNO;
              v_beneename   := r_base.BENNAME;
              v_beneebirt   := r_base.BENBRDATE;

              FOR r_dapr IN c_dapr LOOP
                    v_payym    := r_dapr.PAYYM;
                    v_seqno    := r_dapr.SEQNO;
                    v_supamt   := r_dapr.SUPAMT;
                    v_recamt   := r_dapr.RECAMT;

                    -- 2013/01/18 Add By ChungYu
                    v_tmp      := '';
                    v_issueamt := 0;

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


                    ELSE
                      v_tmp      := '';
                      v_issueamt := 0;
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

                  into_NPMEDAPRDATA;
               END LOOP;
      END LOOP;

   RETURN NPMEDAPR;

   EXCEPTION
      WHEN OTHERS THEN
         NULL;
   END;

END GET_NPMEDAPR_BA;
/
