CREATE OR REPLACE PACKAGE BA.PKG_BA_REPORTFORBC
AUTHID DEFINER  IS
    /********************************************************************************
       修改紀錄：
       維護人員     日期          說明
       -----------  --------      ----------------------------------------------------
       NieNie       2023.12.13    #8632 CREATED THIS FUNCTION
    ********************************************************************************/

  TYPE LFSC_REPORT_RTN IS RECORD
   (
     CATEGORY      VARCHAR2(200),
     BMAPDTE       VARCHAR2(8),
     BMAPNO        VARCHAR2(20),
     BMEVTDTE      VARCHAR2(8),
     BMPAYKIND     VARCHAR2(3),
     BMEVNAME      VARCHAR2(200),
     BMCHKDATE     VARCHAR2(8),
     BMAPLPAYDATE  VARCHAR2(8),
     BMAPUBNO      VARCHAR2(10),
     BMLSUBNO      VARCHAR2(10),
     APLPAYAMT     NUMBER
   );

   TYPE LFSC_REPORT_TAB IS TABLE OF LFSC_REPORT_RTN;

   type STAT_OLD_REGION_ANNUAL_RTN IS RECORD
   (
        APNO         BANSF.APNO%TYPE,
        PAYDATE      BANSF.PAYDATE%TYPE,
        APPDATE      BANSF.APPDATE%TYPE,
        ISSUYM       BANSF.ISSUYM%TYPE,
        SEX          BANSF.SEX%TYPE,
        PAYAGE       BANSF.PAYAGE%TYPE,
        AGE          BANSF.AGE%TYPE,
        NITRMY       BANSF.NITRMY%TYPE,
        NITRMM       BANSF.NITRMM%TYPE,
        PAYCNT       BANSF.PAYCNT%TYPE,
        PAMTS        BANSF.PAMTS%TYPE,
        FIRSTPAY     BANSF.FIRSTPAY%TYPE,
        ONCEPAYMK    BAAPPBASE.ONCEPAYMK%TYPE,
        ONCETY       BAAPPBASE.ONCETY%TYPE,
        SAMTY        BAAPPBASE.SAMTY%TYPE,
        SAMTD        BAAPPBASE.SAMTD%TYPE,
        ONCEPAYM     BAAPPBASE.ONCEPAYM%TYPE,
        ONCEISSUEAMT BAAPPBASE.ONCEISSUEAMT%TYPE,
        MARGINAMT    BAAPPBASE.MARGINAMT%TYPE,
        COMMZIP      BAAPPBASE.COMMZIP%TYPE,
        COMMADDR     BAAPPBASE.COMMADDR%TYPE
   );
  TYPE STAT_OLD_REGION_ANNUAL_TAB IS TABLE OF STAT_OLD_REGION_ANNUAL_RTN;

  TYPE  OLD_WARNING_ONCE_RTN IS RECORD
  (
     BMAPNO   PBBMSA.BMAPNO%TYPE,--AS "受理編號",
     BMEVIDNO PBBMSA.BMEVIDNO%TYPE,--AS "事故者身分證號",
     BMEVTDTE PBBMSA.BMEVTDTE%TYPE,--AS "事故日期",
     BMMLDTE  PBBMSA.BMMLDTE%TYPE,--AS "郵戳日期",
     BMPAYDTE PBBMSA.BMPAYDTE%TYPE,--AS "核付日期",
     BMCHKAMT PBBMSA.BMCHKAMT%TYPE--AS "核定金額"
  );
  TYPE OLD_WARNING_ONCE_TAB IS TABLE OF OLD_WARNING_ONCE_RTN;

  TYPE OLD_WARNING_ANNUAL_RTN IS RECORD
  (
      APNO       BAAPPBASE.APNO%TYPE,--AS "受理編號",
      APPDATE    BAAPPBASE.APPDATE%TYPE,--AS "申請日期",
      CASETYP    BAAPPBASE.CASETYP%TYPE,--AS "案件類別",
      EVTIDNNO   BAAPPBASE.EVTIDNNO%TYPE,--AS "事故者身分證號",
      EVTNAME    BAAPPBASE.EVTNAME%TYPE,--AS "事故者姓名",
      EVTJOBDATE BAAPPBASE.EVTJOBDATE%TYPE,--AS "事故日期",
      APLPAYDATE BADAPR.APLPAYDATE%TYPE,--AS "帳務日期",
      CHKLIST    BADAPR.CHKLIST%TYPE --AS "編審註記"
  );
  TYPE OLD_WARNING_ANNUAL_TAB IS TABLE OF OLD_WARNING_ANNUAL_RTN;

  TYPE RPT_HW_RTN IS RECORD
  (

      APNO       VARCHAR2(20),--"受理編號",
      EVTIDNNO   VARCHAR2(20),--"身份證字號" ,
      EVTNAME    VARCHAR2(200),--"姓名",
      EVTBRDATE  VARCHAR2(8),--"出生日期",
      PAYKIND    VARCHAR2(10),--"給付種類",
      ISSUYM     VARCHAR2(8),--"核定年月",
      APLPAYDATE VARCHAR2(8),--"帳務日期",
      ISSUEAMT   NUMBER(7),--"受款人核定金額",
      BENIDNNO   VARCHAR2(20),--"受益人身分證號",
      BENNAME    VARCHAR2(200) --"受益人姓名"
  );
  TYPE RPT_HW_TAB IS TABLE OF RPT_HW_RTN;

  TYPE RPT_OLD_ONCE_RTN IS RECORD
  (
      APTYPE      VARCHAR2(3),--"給付種類",
      IDNO        VARCHAR2(10),--"事故者身分證字號",
      UBNO    VARCHAR2(20),--AS "保險證字號",
      IDSNO   VARCHAR2(20),--AS "業別",
      APNO    VARCHAR2(14),--AS "受理編號",
      SEX     VARCHAR2(2),--AS "性別",
      AGE     NUMBER(3,0),--AS "年齡",
      PWAGE     NUMBER(8,0),--AS "平均薪資",
      WAGE    NUMBER(8,0),--AS "投保薪資",
      INSYR     NUMBER(3,0),--AS "年資",
      EVTYPE    VARCHAR2(20),--AS "傷病分類",
      EVCODE    VARCHAR2(20),--AS "事故原因",
      BTIMES    NUMBER(2,0),--AS "產次",
      INJCL     VARCHAR2(20),--AS "失能等級",
      INJDP     VARCHAR2(20),--AS "失能部位",
      INJNO     VARCHAR2(20),--AS "失能項目",
      PCNT    NUMBER(3,0),--AS "核付件數",
      CHKDAY    NUMBER(5,0),--AS "核付日數",
      PAMTS     NUMBER(8,0),--AS "核付金額",
      INJPART   VARCHAR2(200),--AS "受傷部位",
      PAYDATE   VARCHAR2(8) --AS "核付日期"
  );
  TYPE RPT_OLD_ONCE_TAB IS TABLE OF RPT_OLD_ONCE_RTN;

  TYPE RPT_OLD_ANNUAL_RTN IS RECORD
  (
      APNO          BANSF.APNO%TYPE,-- AS "受理編號",
      APITEM        BANSF.APITEM%TYPE,-- AS "申請項目",
      PAYNO         BANSF.PAYNO%TYPE,-- AS "給付種類",
      MCHKTYP       BANSF.MCHKTYP%TYPE,-- AS "案件類別",
      EVTIDNNO      VARCHAR2(10),-- AS "事故者身分證號",
      EVTBRDATE     BANSF.EVTBRDATE%TYPE,-- AS "事故者出生日期",
      SEX           BANSF.SEX%TYPE,-- AS "事故者性別",
      EVTNATIONTPE  BANSF.EVTNATIONTPE%TYPE,-- AS "事故者國籍別",
      AGE           BANSF.AGE%TYPE,-- AS "事故者申請時年齡",
      EVTDIEDATE    BANSF.EVTDIEDATE%TYPE,-- AS "事故者死亡日期",
      PAMTS         BANSF.PAMTS%TYPE,-- AS "核定金額",
      FIRSTPAY      BANSF.FIRSTPAY%TYPE,-- AS "首發註記",
      ANNUAMT       BANSF.ANNUAMT%TYPE,-- AS "累計已領年金金額 ",
      PWAGE         BANSF.PWAGE%TYPE,-- AS "平均薪資",
      NITRMY        BANSF.NITRMY%TYPE,-- AS "投保年資(年-年金制)",
      NITRMM        BANSF.NITRMM%TYPE,-- AS "投保年資(月-年金制)",
      OLDEXTRARATE  BANSF.OLDEXTRARATE%TYPE,-- AS "展延/減額比率",
      ISSUYM        BANSF.ISSUYM%TYPE,-- AS "核定年月",
      PAYYM         VARCHAR2(6),-- AS "給付年月",
      PAYDATE       BANSF.PAYDATE%TYPE,-- AS "核付年月",
      PAYCNT        BANSF.PAYCNT%TYPE,-- AS "核付件數",
      IDSTA         BANSF.IDSTA%TYPE,-- AS "大業別",
      INDS          BANSF.INDS%TYPE,-- AS "小業別",
      AREA          BANSF.AREA%TYPE -- AS "地區別"
  );
  TYPE RPT_OLD_ANNUAL_TAB IS TABLE OF RPT_OLD_ANNUAL_RTN;

  TYPE RPT_GET_SPECIALACC_LST_RTN IS RECORD
  (
     ISSUYM        BAAPPBASE.ISSUYM%TYPE,     -- 核定年月
     PAYYM         BAAPPBASE.PAYYM%TYPE,      -- 給付年月
     CASETYP       BAAPPBASE.CASETYP%TYPE,    -- 案件類別
     APNO          BAAPPBASE.APNO%TYPE,       -- 受理編號
     APPDATE       BAAPPBASE.APPDATE%TYPE,    -- 申請日期
     PAYKIND       BAAPPBASE.PAYKIND%TYPE,    -- 給付種類
     SPECIALACC    BAAPPBASE.SPECIALACC%TYPE, -- 專戶註記
     SPEACCDATE    BAAPPBASE.SPEACCDATE%TYPE, -- 專戶日期
     PAYTYP        BAAPPBASE.PAYTYP%TYPE,     -- 給付方式
     UPDUSER       BAAPPBASE.UPDUSER%TYPE,    -- 異動者代號
     UPDTIME       BAAPPBASE.UPDTIME%TYPE,    -- 異動日期時間
     BENIDNNO      BAAPPBASE.BENIDNNO%TYPE,   -- 受益人身分證號
     SEQNO         BAAPPBASE.SEQNO%TYPE       -- 序號
  );
  TYPE RPT_GET_SPECIALACC_LST_TAB IS TABLE OF RPT_GET_SPECIALACC_LST_RTN;
  /*
   監理會外部訪視單位資料
  */
  FUNCTION RPT_LFSC_REPORT (  P_SDATE VARCHAR2, P_EDATE VARCHAR2 ,P_UBNO1 VARCHAR2,P_UBNO2 VARCHAR2 default NULL,
    P_UBNO3 VARCHAR2 default NULL,P_UBNO4 VARCHAR2 default NULL,P_UBNO5 VARCHAR2 default NULL,P_UBNO6 VARCHAR2 default NULL,
    P_UBNO7 VARCHAR2 default NULL,P_UBNO8 VARCHAR2 default NULL,P_UBNO9 VARCHAR2 default NULL,P_UBNO10 VARCHAR2 default NULL) RETURN LFSC_REPORT_TAB PIPELINED ;
  /*
  /*
    老年給付按地區別統計件數
  */
  FUNCTION RPT_STAT_OLD_REGION_ANNUAL ( P_SDATE VARCHAR2, P_EDATE VARCHAR2) RETURN STAT_OLD_REGION_ANNUAL_TAB PIPELINED;

  /**
    給付預警案件 - 年金
  */
  FUNCTION RPT_OLD_WARNING_ANNUAL ( P_SDATE VARCHAR2, P_EDATE VARCHAR2) RETURN OLD_WARNING_ANNUAL_TAB PIPELINED;
  /*
    衛福部31,41,51,45核付資料
  */
 FUNCTION RPT_HW ( P_ISSUYM VARCHAR2, P_CPI VARCHAR2) RETURN RPT_HW_TAB PIPELINED;
  /*
    勞委會統計處一老年年金
  */
  FUNCTION RPT_OLD_ANNUAL ( P_PAYDATES VARCHAR2 ) RETURN RPT_OLD_ANNUAL_TAB PIPELINED;
  /*
    BCba0m514_年金專戶資料
  */
  FUNCTION RPT_GET_SPECIALACC_LST RETURN RPT_GET_SPECIALACC_LST_TAB PIPELINED;

END PKG_BA_REPORTFORBC;
/
CREATE OR REPLACE PACKAGE BODY BA.PKG_BA_REPORTFORBC
IS

    /******************************************************************************
        NEC 20221216 上版註記
    ******************************************************************************/

  /*
    勞保年金外籍資料 - CURSOR  --BCBA0M520F_監理會外部訪視單位資料
  */

  CURSOR LFSC_REPORT_CUR (
    I_DTES IN VARCHAR2,
    I_DTEE IN VARCHAR2,
    I_UBNO1 IN VARCHAR2,
    I_UBNO2 IN VARCHAR2,
    I_UBNO3 IN VARCHAR2,
    I_UBNO4 IN VARCHAR2,
    I_UBNO5 IN VARCHAR2,
    I_UBNO6 IN VARCHAR2,
    I_UBNO7 IN VARCHAR2,
    I_UBNO8 IN VARCHAR2,
    I_UBNO9 IN VARCHAR2,
    I_UBNO10 IN VARCHAR2
  ) RETURN LFSC_REPORT_RTN
  IS
    SELECT '年金給付受理案件'       AS CATEGORY,
           SUBSTR(A.CRTTIME, 1, 8) APDTE,
           A.APNO,
           A.EVTJOBDATE,
           A.PAYKIND,
           A.EVTNAME,
           MIN(B.CHKDATE) CHKDTE,
           MIN(B.APLPAYDATE) PAYDTE,
           A.APUBNO UBNO1,
           A.LSUBNO UBNO,
           B.APLPAYAMT
      FROM BAAPPBASE A, BADAPR B
     WHERE A.SEQNO = B.SEQNO
       AND A.APNO = B.APNO
       AND B.SEQNO = '0000'
       AND A.PAYKIND IN ('45','48')
       AND SUBSTR(A.CRTTIME, 1, 8) BETWEEN I_DTES AND I_DTEE
       AND A.LSUBNO IN (I_UBNO1, I_UBNO2, I_UBNO3, I_UBNO4, I_UBNO5, I_UBNO6, I_UBNO7, I_UBNO8, I_UBNO9, I_UBNO10)
  GROUP BY A.CRTTIME, A.APNO, A.EVTJOBDATE, A.PAYKIND, A.EVTNAME, A.APUBNO, A.LSUBNO, B.APLPAYAMT
 UNION ALL
    SELECT '年金給付核定(核付)案件'       AS CATEGORY,
           SUBSTR(A.CRTTIME, 1, 8) APDTE,
           A.APNO APNO,
           A.EVTJOBDATE,
           A.PAYKIND PAYKIND,
           A.EVTNAME EVTNAME,
           B.CHKDATE CHKDTE,
           B.APLPAYDATE PAYDTE,
           A.APUBNO UBNO1,
           A.LSUBNO UBNO,
           B.APLPAYAMT
      FROM BAAPPBASE A, BADAPR B
     WHERE A.SEQNO = B.SEQNO
       AND A.APNO = B.APNO
       AND B.MTESTMK = 'F'
       AND B.SEQNO = '0000'
       AND A.PAYKIND IN ('45','48')
       AND B.MANCHKMK = 'Y'
       AND B.PAYYM = A.PAYYMS
       AND B.APLPAYDATE BETWEEN I_DTES AND I_DTEE
       AND B.APLPAYMK = '3'
       AND A.LSUBNO IN (I_UBNO1, I_UBNO2, I_UBNO3, I_UBNO4, I_UBNO5, I_UBNO6, I_UBNO7, I_UBNO8, I_UBNO9, I_UBNO10)
 UNION ALL
    SELECT '年金給付受理案件'     AS CATEGORY,
           SUBSTR(A.CRTTIME, 1, 8) APDTE,
           A.APNO,
           A.EVTJOBDATE,
           A.PAYKIND,
           A.EVTNAME,
           MIN(B.CHKDATE) CHKDTE,
           MIN(B.APLPAYDATE) PAYDTE,
           A.APUBNO UBNO1,
           A.LSUBNO UBNO,
           B.APLPAYAMT
      FROM BAAPPBASE A , BADAPR B
     WHERE A.SEQNO = B.SEQNO
       AND A.APNO = B.APNO
       AND B.SEQNO = '0000'
       AND A.PAYKIND IN ('35','38','55','56','59')
       AND SUBSTR(A.CRTTIME, 1, 8) BETWEEN I_DTES AND I_DTEE
       AND A.APUBNO IN (I_UBNO1, I_UBNO2, I_UBNO3, I_UBNO4, I_UBNO5, I_UBNO6, I_UBNO7, I_UBNO8, I_UBNO9, I_UBNO10)
  GROUP BY A.CRTTIME, A.APNO, A.EVTJOBDATE, A.PAYKIND, A.EVTNAME, A.APUBNO, A.LSUBNO, B.APLPAYAMT
 UNION ALL
    SELECT '年金給付核定(核付)案件'       AS CATEGORY,
           SUBSTR(A.CRTTIME, 1, 8) APDTE,
           A.APNO APNO,
           A.EVTJOBDATE,
           A.PAYKIND PAYKIND,
           A.EVTNAME EVTNAME,
           B.CHKDATE CHKDTE,
           B.APLPAYDATE PAYDTE,
           A.APUBNO UBNO1,
           A.LSUBNO UBNO,
           B.APLPAYAMT
      FROM BAAPPBASE A, BADAPR B
     WHERE A.SEQNO = B.SEQNO
       AND A.APNO = B.APNO
       AND B.MTESTMK = 'F'
       AND B.SEQNO = '0000'
       AND A.PAYKIND IN ('35','38','55','56','59')
       AND B.MANCHKMK = 'Y'
       AND B.PAYYM = A.PAYYMS
       AND B.APLPAYDATE BETWEEN I_DTES AND I_DTEE
       AND B.APLPAYMK = '3'
       AND A.APUBNO IN (I_UBNO1, I_UBNO2, I_UBNO3, I_UBNO4, I_UBNO5, I_UBNO6, I_UBNO7, I_UBNO8, I_UBNO9, I_UBNO10);

  /*
    年金專戶資料 - 年金 - CURSOR     --Bcba0m501_產生老年給付按地區別統計件數檔
  */

  CURSOR STAT_OLD_REGION_CUR2 (
   I_SDATE VARCHAR2,
   I_EDATE VARCHAR2
  ) RETURN STAT_OLD_REGION_ANNUAL_RTN
  IS
    SELECT A.APNO         AS "受理編號",       -- 受理編號
           A.PAYDATE      AS "核付日期",       -- 核付日期
           A.APPDATE      AS "申請日期",       -- 申請日期
           A.ISSUYM       AS "核定年月",       -- 核定年月
           A.SEX          AS "事故者性別",     -- 事故者性別
           A.PAYAGE       AS "核付年齡",       -- 核付年齡
           A.AGE          AS "事故者申請年齡", -- 事故者申請年齡
           A.NITRMY       AS "投保年資-年",    -- 投保年資-年
           A.NITRMM       AS "投保年資-月",    -- 投保年資-月
           A.PAYCNT       AS "核付件數",       -- 核付件數
           A.PAMTS        AS "核付金額",       -- 核付金額
           A.FIRSTPAY     AS "首發註記",       -- 首發註記
           B.ONCEPAYMK    AS "一次符合",       -- 一次符合
           B.ONCETY       AS "一次年資",       -- 一次年資
           B.SAMTY        AS "同單位年",       -- 同單位年
           B.SAMTD        AS "同單位日",       -- 同單位日
           B.ONCEPAYM     AS "一次月數",       -- 一次月數
           B.ONCEISSUEAMT AS "一次金額",       -- 一次金額
           B.MARGINAMT    AS "老年差額",       -- 老年差額
           B.COMMZIP      AS "郵遞區號",       -- 郵遞區號
           B.COMMADDR     AS "地址"            -- 地址
      FROM BANSF A, BAAPPBASE B
     WHERE A.APNO LIKE 'L%'
       AND A.FIRSTPAY = '1'
       AND (SUBSTR(A.PAYDATE,1,6) BETWEEN I_SDATE AND I_EDATE)
       AND A.APNO = B.APNO
       AND B.SEQNO = '0000'
  ORDER BY A.PAYDATE;

  /*
    給付預警案件 - 年金 - CURSOR    --Bcba0m503_產生老年給付預警案件檔
  */

  CURSOR OLD_WARNING_CUR2 (
    I_SDATE VARCHAR2,
    I_EDATE VARCHAR2
  ) RETURN OLD_WARNING_ANNUAL_RTN
  IS
    SELECT A.APNO        AS "受理編號" ,       -- 受理編號
           A.APPDATE     AS "申請日期" ,       -- 申請日期
           A.CASETYP     AS "案件類別" ,       -- 案件類別
           A.EVTIDNNO    AS "事故者身分證號" , -- 事故者身分證號
           A.EVTNAME     AS "事故者姓名" ,     -- 事故者姓名
           A.EVTJOBDATE  AS "事故日期" ,       -- 事故日期
           B.APLPAYDATE  AS "帳務日期" ,       -- 帳務日期
           B.CHKLIST     AS "編審註記"         -- 編審註記
      FROM BAAPPBASE A, BADAPR B
     WHERE A.APNO = B.APNO
       AND A.SEQNO = B.SEQNO
       AND A.SEQNO = '0000'
       AND A.PAYKIND IN ('45', '48')
       AND (A.APPDATE BETWEEN I_SDATE AND I_EDATE)
       AND (B.CHKLIST LIKE '%P1%' OR B.CHKLIST LIKE '%P2%' OR B.CHKLIST LIKE '%P3%')
  ORDER BY A.APNO;

  /*
    衛福部45核付資料 - CURSOR    --Bcba0m701_衛福部31,41,51,45核付資料
  */

  CURSOR RPT_HW_45_CUR (
    P_ISSUYM VARCHAR2
  ) RETURN RPT_HW_RTN
  IS
    --45 核付資料
    SELECT A.APNO                                         AS "受理編號",
           A.EVTIDNNO                                     AS "身份證字號",
           A.EVTNAME                                      AS "姓名" ,
           TRIM(TO_CHAR(A.EVTBRDATE-19110000,'0000000'))  AS "出生日期",
           A.PAYKIND                                      AS "給付種類",
           TRIM(TO_CHAR(B.ISSUYM-191100,'00000'))         AS "核定年月",
           TRIM(TO_CHAR(B.APLPAYDATE-19110000,'0000000')) AS "帳務日期",
           B.ISSUEAMT                                     AS "受款人核定金額",
           A.BENIDNNO                                     AS "受益人身分證號",
           A.BENNAME                                      AS "受益人姓名"
      FROM BAAPPBASE A, BADAPR B
     WHERE A.APNO = B.APNO
       AND A.SEQNO = B.SEQNO
       AND A.SEQNO = '0000'
       AND B.MTESTMK = 'F'
       AND SUBSTR(B.APLPAYDATE,1,6) = P_ISSUYM    --頁面輸入核付年月
       AND B.PAYKIND <> '49'
       AND A.APNO LIKE 'L%'
       AND B.ISSUEAMT > 0
       AND B.BENEVTREL NOT IN ('F','Z','N')
       ORDER BY A.APNO, A.SEQNO, B.PAYYM;

  /*
    衛福部45CPI核付資料 - CURSOR    --Bcba0m701_衛福部31,41,51,45核付資料
  */

  CURSOR RPT_HW_CPI_CUR (
    P_ISSUYM VARCHAR2
  ) RETURN RPT_HW_RTN
  IS
    --45 核付資料
    SELECT A.APNO                                         AS "受理編號",
           A.EVTIDNNO                                     AS "身份證字號",
           A.EVTNAME                                      AS "姓名" ,
           TRIM(TO_CHAR(A.EVTBRDATE-19110000,'0000000'))  AS "出生日期",
           A.PAYKIND                                      AS "給付種類",
           TRIM(TO_CHAR(B.ISSUYM-191100,'00000'))         AS "核定年月",
           TRIM(TO_CHAR(B.APLPAYDATE-19110000,'0000000')) AS "帳務日期",
           SUM(B.ISSUEAMT)                                AS "受款人核定金額",
           A.BENIDNNO                                     AS "受益人身分證號",
           A.BENNAME                                      AS "受益人姓名"
      FROM BAAPPBASE A, BADAPR B
     WHERE A.APNO = B.APNO
       AND A.SEQNO = B.SEQNO
       AND A.SEQNO = '0000'
       AND B.MTESTMK = 'F'
       AND SUBSTR(B.APLPAYDATE,1,6) = P_ISSUYM    --頁面輸入核付年月
       AND B.PAYKIND <> '49'
       AND A.APNO LIKE 'L%'
       AND B.ISSUEAMT > 0
       AND B.BENEVTREL NOT IN ('F','Z','N')
       GROUP BY A.APNO, A.EVTIDNNO, A.EVTNAME, TRIM(TO_CHAR(A.EVTBRDATE - 19110000, '0000000')), A.PAYKIND,
                TRIM(TO_CHAR(B.ISSUYM - 191100, '00000')), TRIM(TO_CHAR(B.APLPAYDATE - 19110000, '0000000')),
                A.BENIDNNO, A.BENNAME, A.SEQNO, B.ISSUYM
       ORDER BY A.APNO, A.SEQNO, B.ISSUYM;


  /*
    勞委會統計處一老年年金   --BCba0m706_勞委會統計處一次請領及老年年金
  */

  CURSOR RPT_OLD_ANNUAL_CUR (
    P_PAYDATES VARCHAR2
  ) RETURN RPT_OLD_ANNUAL_RTN
  IS
    SELECT A.APNO                                  AS "受理編號",
           A.APITEM                                AS "申請項目",
           A.PAYNO                                 AS "給付種類",
           A.MCHKTYP                               AS "案件類別",
           PKG_BA_UTILITY.FN_ENCODE_IDN(A.EVTIDNNO)AS "事故者身分證號",
           A.EVTBRDATE                             AS "事故者出生日期",
           A.SEX                                   AS "事故者性別",
           A.EVTNATIONTPE                          AS "事故者國籍別",
           A.AGE                                   AS "事故者申請時年齡",
           A.EVTDIEDATE                            AS "事故者死亡日期",
           A.PAMTS                                 AS "核定金額",
           A.FIRSTPAY                              AS "首發註記",
           A.ANNUAMT                               AS "累計已領年金金額",
           A.PWAGE                                 AS "平均薪資",
           A.NITRMY                                AS "投保年資(年-年金制)",
           A.NITRMM                                AS "投保年資(月-年金制)",
           A.OLDEXTRARATE                          AS "展延/減額比率",
           A.ISSUYM                                AS "核定年月",
           A.PAYYM                                 AS "給付年月",
           SUBSTR(A.PAYDATE, 1, 6)                 AS "核付年月",
           A.PAYCNT                                AS "核付件數",
           A.IDSTA                                 AS "大業別",
           A.INDS                                  AS "小業別",
           A.AREA                                  AS "地區別"
      FROM BANSF A
     WHERE A.PAYNO NOT IN ('35', '36', '37', '38', '39', '49', '55', '56', '59')
       AND SUBSTR(A.PAYDATE, 1, 6) = (P_PAYDATES + 191100)    --傳入參數
  ORDER BY SUBSTR(A.PAYDATE, 1, 6), A.APNO;

  /*
    BCba0m514_年金專戶資料
  */

  CURSOR RPT_GET_SPECIALACC_LST_CUR
  RETURN RPT_GET_SPECIALACC_LST_RTN
  IS
    select ISSUYM,             -- 核定年月
           PAYYM,              -- 給付年月
           CASETYP,            -- 案件類別
           APNO,               -- 受理編號
           APPDATE,            -- 申請日期
           PAYKIND,            -- 給付種類
           SPECIALACC,         -- 專戶註記
           SPEACCDATE,         -- 專戶日期
           PAYTYP,             -- 給付方式
           UPDUSER,            -- 異動者代號
           UPDTIME,            -- 異動日期時間
           BENIDNNO,           -- 受益人身分證號
           SEQNO               -- 序號
      FROM BAAPPBASE
     WHERE SPECIALACC IS NOT NULL
  ORDER BY PAYKIND, APNO;

  --CURSOR END

  FUNCTION RPT_LFSC_REPORT (
    P_SDATE  VARCHAR2,              -- 起始日期
    P_EDATE  VARCHAR2 ,             -- 結束日期
    P_UBNO1  VARCHAR2,              -- 保險證號1
    P_UBNO2  VARCHAR2 DEFAULT NULL, -- 保險證號2
    P_UBNO3  VARCHAR2 DEFAULT NULL, -- 保險證號3
    P_UBNO4  VARCHAR2 DEFAULT NULL, -- 保險證號4
    P_UBNO5  VARCHAR2 DEFAULT NULL, -- 保險證號5
    P_UBNO6  VARCHAR2 DEFAULT NULL, -- 保險證號6
    P_UBNO7  VARCHAR2 DEFAULT NULL, -- 保險證號7
    P_UBNO8  VARCHAR2 DEFAULT NULL, -- 保險證號8
    P_UBNO9  VARCHAR2 DEFAULT NULL, -- 保險證號9
    P_UBNO10 VARCHAR2 DEFAULT NULL  -- 保險證號10
  ) RETURN LFSC_REPORT_TAB PIPELINED

    /********************************************************************************
        專案名稱 : BLI-BaWeb 勞保年金給付系統
        程式名稱 : PKG_BA_REPORTFORBC.RPT_LFSC_REPORT
        中文名稱 : 勞保年金外籍資料(含一次)

        輸入參數 : P_SDATE  -- 起始日期
                   P_EDATE  -- 結束日期
                   P_UBNO1  -- 保險證號1
                   P_UBNO2  -- 保險證號2
                   P_UBNO3  -- 保險證號3
                   P_UBNO4  -- 保險證號4
                   P_UBNO5  -- 保險證號5
                   P_UBNO6  -- 保險證號6
                   P_UBNO7  -- 保險證號7
                   P_UBNO8  -- 保險證號8
                   P_UBNO9  -- 保險證號9
                   P_UBNO10 -- 保險證號10

        程式說明 : 勞保年金外籍資料(含一次)

        備    註 :

        使用物件 :
        I/O  物件名稱
        ---  ------------------------------------------------------------------------

        版    本 :
        Ver  日期        異動者      異動描述
        ---  ----------  ----------  ------------------------------------------------
        0.1  2023.12.13  NieNie      CREATED THIS FUNCTION
    ********************************************************************************/

  IS
  BEGIN
    FOR L_ROW IN LFSC_REPORT_CUR( P_SDATE, P_EDATE, P_UBNO1, P_UBNO2,
                                  P_UBNO3, P_UBNO4, P_UBNO5, P_UBNO6,
                                  P_UBNO7, P_UBNO8, P_UBNO9, P_UBNO10 )
      LOOP
        PIPE ROW(L_ROW);
    END LOOP;
  END RPT_LFSC_REPORT;


  FUNCTION RPT_STAT_OLD_REGION_ANNUAL (
    P_SDATE VARCHAR2,
    P_EDATE VARCHAR2
  ) RETURN STAT_OLD_REGION_ANNUAL_TAB PIPELINED

    /********************************************************************************
        專案名稱 : BLI-BaWeb 勞保年金給付系統
        程式名稱 : PKG_BA_REPORTFORBC.RPT_STAT_OLD_REGION_ANNUAL
        中文名稱 : 老年給付按地區別統計件數

        輸入參數 : P_SDATE  -- 起始日期
                   P_EDATE  -- 結束日期

        程式說明 : 老年給付按地區別統計件數

        備    註 :

        使用物件 :
        I/O  物件名稱
        ---  ------------------------------------------------------------------------

        版    本 :
        Ver  日期        異動者      異動描述
        ---  ----------  ----------  ------------------------------------------------
        0.1  2023.12.13  NieNie      CREATED THIS FUNCTION
    ********************************************************************************/

  IS
  BEGIN
    FOR L_ROW IN STAT_OLD_REGION_CUR2(P_SDATE, P_EDATE)
      LOOP
        PIPE ROW(L_ROW);
    END LOOP;
  END RPT_STAT_OLD_REGION_ANNUAL;


  FUNCTION RPT_OLD_WARNING_ANNUAL (
    P_SDATE VARCHAR2,
    P_EDATE VARCHAR2
  ) RETURN OLD_WARNING_ANNUAL_TAB PIPELINED

    /********************************************************************************
        專案名稱 : BLI-BaWeb 勞保年金給付系統
        程式名稱 : PKG_BA_REPORTFORBC.RPT_OLD_WARNING_ANNUAL
        中文名稱 : 給付預警案件 - 年金

        輸入參數 : P_SDATE  -- 起始日期
                   P_EDATE  -- 結束日期

        程式說明 : 給付預警案件 - 年金

        備    註 :

        使用物件 :
        I/O  物件名稱
        ---  ------------------------------------------------------------------------

        版    本 :
        Ver  日期        異動者      異動描述
        ---  ----------  ----------  ------------------------------------------------
        0.1  2023.12.13  NieNie      CREATED THIS FUNCTION
    ********************************************************************************/

  IS
  BEGIN
    FOR L_ROW IN OLD_WARNING_CUR2(P_SDATE, P_EDATE)
      LOOP
        PIPE ROW(L_ROW);
    END LOOP;
  END RPT_OLD_WARNING_ANNUAL;


   FUNCTION RPT_HW (
    P_ISSUYM VARCHAR2,
    P_CPI    VARCHAR2
  ) RETURN RPT_HW_TAB PIPELINED

    /********************************************************************************
        專案名稱 : BLI-BaWeb 勞保年金給付系統
        程式名稱 : PKG_BA_REPORTFORBC.RPT_HW
        中文名稱 : 衛福部45核付資料

        輸入參數 : P_ISSUYM
                   P_CPI

        程式說明 : 衛福部31,41,51,45核付資料

        備    註 :

        使用物件 :
        I/O  物件名稱
        ---  ------------------------------------------------------------------------

        版    本 :
        Ver  日期        異動者      異動描述
        ---  ----------  ----------  ------------------------------------------------
        0.1  2023.12.13  NieNie      CREATED THIS FUNCTION
    ********************************************************************************/
  IS
  BEGIN
    IF nvl(P_CPI,'@')='@' THEN
      FOR L_ROW IN RPT_HW_45_CUR(P_ISSUYM)
        LOOP
          PIPE ROW(L_ROW);
      END LOOP;
    ELSE
      FOR L_ROW IN RPT_HW_CPI_CUR(P_ISSUYM)
        LOOP
          PIPE ROW(L_ROW);
      END LOOP;
    END IF;
  END RPT_HW;


  FUNCTION RPT_OLD_ANNUAL (
    P_PAYDATES VARCHAR2
  ) RETURN RPT_OLD_ANNUAL_TAB PIPELINED

    /********************************************************************************
        專案名稱 : BLI-BaWeb 勞保年金給付系統
        程式名稱 : PKG_BA_REPORTFORBC.RPT_OLD_ANNUAL
        中文名稱 : 勞委會統計處一老年年金

        輸入參數 : P_FLAG
                   P_PAYDATES

        程式說明 : 勞委會統計處一老年年金

        備    註 :

        使用物件 :
        I/O  物件名稱
        ---  ------------------------------------------------------------------------

        版    本 :
        Ver  日期        異動者      異動描述
        ---  ----------  ----------  ------------------------------------------------
        0.1  2023.12.13  NieNie      CREATED THIS FUNCTION
    ********************************************************************************/

  IS
  BEGIN
    FOR L_ROW IN RPT_OLD_ANNUAL_CUR(P_PAYDATES)
      LOOP
        PIPE ROW(L_ROW);
    END LOOP;
  END RPT_OLD_ANNUAL;

  FUNCTION RPT_GET_SPECIALACC_LST
    RETURN RPT_GET_SPECIALACC_LST_TAB PIPELINED

    /********************************************************************************
        專案名稱 : BLI-BaWeb 勞保年金給付系統
        程式名稱 : PKG_BA_REPORTFORBC.RPT_GET_SPECIALACC_LST
        中文名稱 : 年金專戶資料

        輸入參數 : P_FLAG

        程式說明 : 年金專戶資料

        備    註 :

        使用物件 :
        I/O  物件名稱
        ---  ------------------------------------------------------------------------

        版    本 :
        Ver  日期        異動者      異動描述
        ---  ----------  ----------  ------------------------------------------------
        0.1  2023.12.13  NieNie      CREATED THIS FUNCTION
    ********************************************************************************/

  IS
  BEGIN
    FOR L_ROW IN RPT_GET_SPECIALACC_LST_CUR
      LOOP
        PIPE ROW(L_ROW);
    END LOOP;
  END RPT_GET_SPECIALACC_LST;

END PKG_BA_REPORTFORBC;
/