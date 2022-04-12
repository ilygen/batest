CREATE OR REPLACE PACKAGE BA.PKG_BAAPPLYDATA AUTHID DEFINER IS

    TYPE ba_baappbase_rec IS RECORD(
         APNO             BAAPPBASE.APNO%TYPE            -- 受理編號
        ,SEQNO            BAAPPBASE.SEQNO%TYPE           -- 序號
        ,SYSCODE          VARCHAR2(2)                    -- 受理類別碼
        ,PAYCODE          VARCHAR2(1)                    -- 給付別
        ,IMK              BAAPPBASE.IMK%TYPE             -- 保險別
        ,PAYKIND          BAAPPBASE.PAYKIND%TYPE         -- 給付種類
        ,APPDATE          BAAPPBASE.APPDATE%TYPE         -- 申請日期
        ,CASETYP          BAAPPBASE.CASETYP%TYPE         -- 案件類別
        ,MAPNO            BAAPPBASE.MAPNO%TYPE           -- 相關受理編號
        ,MAPROOTMK        BAAPPBASE.MAPROOTMK%TYPE       -- 相關受理編號註記
        ,PROCSTAT         BAAPPBASE.PROCSTAT%TYPE        -- 處理狀態
        ,ACCEPTMK         BAAPPBASE.ACCEPTMK%TYPE        -- 合格註記
        ,MANCHKMK         BAAPPBASE.MANCHKMK%TYPE        -- 人工審核結果
        ,EXESTAT          BAAPPBASE.EXESTAT%TYPE         -- 決行狀況
        ,APUBNO           BAAPPBASE.APUBNO%TYPE          -- 申請單位保險證號
        ,APUBNOCK         BAAPPBASE.APUBNOCK%TYPE        -- 申請單位保險證號檢查碼
        ,LSUBNO           BAAPPBASE.LSUBNO%TYPE          -- 最後單位保險證號
        ,LSUBNOCK         BAAPPBASE.LSUBNOCK%TYPE        -- 最後單位保險證號檢查碼
        ,EVTIDS           BAAPPBASE.EVTIDS%TYPE          -- 事故者社福識別碼
        ,EVTIDNNO         BAAPPBASE.EVTIDNNO%TYPE        -- 事故者身分證號
        ,EVTBRDATE        BAAPPBASE.EVTBRDATE%TYPE       -- 事故者出生日期
        ,EVTNAME          BAAPPBASE.EVTNAME%TYPE         -- 事故者姓名
        ,EVTAPPNAME       BAAPPBASE.EVTAPPNAME%TYPE      -- 事故者申請時姓名
        ,EVTAPPIDNNO      BAAPPBASE.EVTAPPIDNNO%TYPE     -- 事故者申請時身分證號
        ,EVTAPPBRDATE     BAAPPBASE.EVTAPPBRDATE%TYPE    -- 事故者申請時出生日期
        ,EVTSEX           BAAPPBASE.EVTSEX%TYPE          -- 事故者性別
        ,EVTNATIONTPE     BAAPPBASE.EVTNATIONTPE%TYPE    -- 事故者國籍別
        ,EVTNATIONCODE    BAAPPBASE.EVTNATIONCODE%TYPE   -- 事故者國籍
        ,EVTIDENT         BAAPPBASE.EVTIDENT%TYPE        -- 事故者身分別
        ,EVTJOBDATE       BAAPPBASE.EVTJOBDATE%TYPE      -- 事故日期
        ,EVTEXPIREDATE    BAAPPBASE.EVTEXPIREDATE%TYPE   -- 事故者屆齡日期
        ,EVTELIGIBLEDATE  BAAPPBASE.EVTELIGIBLEDATE%TYPE -- 事故者符合日期
        ,EVTAGE           BAAPPBASE.EVTAGE%TYPE          -- 事故者申請時年齡
        ,DABAPNO          BAAPPBASE.DABAPNO%TYPE         -- 已領其他年金受理編號
        ,DABANNUAMT       BAAPPBASE.DABANNUAMT%TYPE      -- 已領其他年金金額
        ,ISSUYM           BAAPPBASE.ISSUYM%TYPE          -- 核定年月
        ,PAYYM            BAAPPBASE.PAYYM%TYPE           -- 給付年月
        ,ISSUEAMT         BAAPPBASE.ISSUEAMT%TYPE        -- 總核定金額
        ,PAYDATE          BAAPPBASE.PAYDATE%TYPE         -- 首次核定日期
        ,PAYYMS           BAAPPBASE.PAYYMS%TYPE          -- 首次發放起月
        ,PAYYME           BAAPPBASE.PAYYME%TYPE          -- 首次發放迄月
        ,PAYAMTS          BAAPPBASE.PAYAMTS%TYPE         -- 首次核定總金額
        ,ANNUAMT          BAAPPBASE.ANNUAMT%TYPE         -- 累計已領年金金額
        ,MEXCLVL          BAAPPBASE.MEXCLVL%TYPE         -- 應決行層級
        ,REALEXCLVL       BAAPPBASE.REALEXCLVL%TYPE      -- 實際決行層級
        ,CHKDATE          BAAPPBASE.CHKDATE%TYPE         -- 審核日期
        ,CHKMAN           BAAPPBASE.CHKMAN%TYPE          -- 審核人員
        ,RECHKDATE        BAAPPBASE.RECHKDATE%TYPE       -- 複核日期
        ,RECHKMAN         BAAPPBASE.RECHKMAN%TYPE        -- 複核人員
        ,EXEDATE          BAAPPBASE.EXEDATE%TYPE         -- 決行日期
        ,EXEMAN           BAAPPBASE.EXEMAN%TYPE          -- 決行人員
        ,ARCDATE          BAAPPBASE.ARCDATE%TYPE         -- 歸檔日期
        ,ARCPG            BAAPPBASE.ARCPG%TYPE           -- 歸檔頁次
        ,CLOSEDATE        BAAPPBASE.CLOSEDATE%TYPE       -- 結案日期
        ,CLOSECAUSE       BAAPPBASE.CLOSECAUSE%TYPE      -- 結案原因
        ,CHECKIN          BAAPPBASE.CHECKIN%TYPE         -- 來源別
        ,EVAPPTYP         BAAPPEXPAND.EVAPPTYP%TYPE      -- 申請傷病分類
        ,EVCODE           BAAPPEXPAND.EVCODE%TYPE        -- 傷病原因
        ,EVTYP            BAAPPEXPAND.EVTYP%TYPE         -- 傷病分類
        ,CRIINPART1       BAAPPEXPAND.CRIINPART1%TYPE    -- 受傷部位1
        ,CRIINPART2       BAAPPEXPAND.CRIINPART2%TYPE    -- 受傷部位2
        ,CRIINPART3       BAAPPEXPAND.CRIINPART3%TYPE    -- 受傷部位3
        ,CRIINJNME1       BAAPPEXPAND.CRIINJNME1%TYPE    -- 國際疾病代碼1
        ,CRIINJNME2       BAAPPEXPAND.CRIINJNME2%TYPE    -- 國際疾病代碼2
        ,CRIINJNME3       BAAPPEXPAND.CRIINJNME3%TYPE    -- 國際疾病代碼3
        ,CRIINJNME4       BAAPPEXPAND.CRIINJNME4%TYPE    -- 國際疾病代碼4
        ,HOSID            BAAPPEXPAND.HOSID%TYPE         -- 職病醫療院所代號
        ,DOCTORNAME1      BAAPPEXPAND.DOCTORNAME1%TYPE   -- 醫師姓名1
        ,DOCTORNAME2      BAAPPEXPAND.DOCTORNAME2%TYPE   -- 醫師姓名2
        ,OCACCIDENTMK     BAAPPEXPAND.OCACCIDENTMK%TYPE  -- 符合第20條之1註記
        ,BENIDNNO         BAAPPBASE.BENIDNNO%TYPE        -- 受益人身分證號
        ,BENNAME          BAAPPBASE.BENNAME%TYPE         -- 受益人姓名
        ,BENBRDATE        BAAPPBASE.BENBRDATE%TYPE       -- 受益人出生日期
        ,BENSEX           BAAPPBASE.BENSEX%TYPE          -- 受益人性別
        ,BENNATIONTYP     BAAPPBASE.BENNATIONTYP%TYPE    -- 受益人國籍別
        ,BENNATIONCODE    BAAPPBASE.BENNATIONCODE%TYPE   -- 受益人國籍
        ,BENEVTREL        BAAPPBASE.BENEVTREL%TYPE       -- 受益人與事故者關係
        ,TEL1             BAAPPBASE.TEL1%TYPE            -- 電話1
        ,TEL2             BAAPPBASE.TEL2%TYPE            -- 電話2
        ,MOBILEPHONE      BAAPPBASE.MOBILEPHONE%TYPE     -- 手機複驗
        ,COMMTYP          BAAPPBASE.COMMTYP%TYPE         -- 地址別
        ,COMMZIP          BAAPPBASE.COMMZIP%TYPE         -- 郵遞區號
        ,COMMADDR         BAAPPBASE.COMMADDR%TYPE        -- 現地址
        ,EMAIL            BAAPPBASE.EMAIL%TYPE           -- 電子郵件
        ,PAYTYP           BAAPPBASE.PAYTYP%TYPE          -- 給付方式
        ,BANKNATIONCODE   BAAPPBASE.BANKNATIONCODE%TYPE  -- 金融機構國籍
        ,BANKNAME         BAAPPBASE.BANKNAME%TYPE        -- 金融機構名稱
        ,BRANCHNAME       BAAPPBASE.BRANCHNAME%TYPE      -- 金融機構分行名稱
        ,PAYBANKID        BAAPPBASE.PAYBANKID%TYPE       -- 金融機構總代號
        ,BRANCHID         BAAPPBASE.BRANCHID%TYPE        -- 分支代號
        ,PAYEEACC         BAAPPBASE.PAYEEACC%TYPE        -- 銀行帳號
        ,GRDIDNNO         BAAPPBASE.GRDIDNNO%TYPE        -- 法定代理人身分證號
        ,GRDNAME          BAAPPBASE.GRDNAME%TYPE         -- 法定代理人姓名
        ,GRDBRDATE        BAAPPBASE.GRDBRDATE%TYPE       -- 法定代理人出生日期
        );
    TYPE ba_baappbase_tab IS TABLE OF ba_baappbase_rec;

    TYPE ba_badapr_rec IS RECORD(
         APNO        BADAPR.APNO%TYPE           -- 受理編號
        ,SEQNO       BADAPR.SEQNO%TYPE          -- 序號
        ,PAYYM       BADAPR.PAYYM%TYPE          -- 給付年月
        ,PAYKIND     BADAPR.PAYKIND%TYPE        -- 給付種類
        ,BENIDNNO    BAAPPBASE.BENIDNNO%TYPE    -- 受益人身分證號
        ,BENNAME     BAAPPBASE.BENNAME%TYPE     -- 受益人姓名
        ,BENBRDATE   BAAPPBASE.BENBRDATE%TYPE   -- 受益人出生日期
        ,INSAVGAMT   BADAPR.INSAVGAMT%TYPE      -- 平均薪資
        ,OLDAB       BADAPR.OLDAB%TYPE          -- 第一式/第二式
        ,OLDAAMT     BADAPR.OLDAAMT%TYPE        -- 勞保計算金額
        ,OLDBAMT     BADAPR.OLDBAMT%TYPE        -- 勞保給付金額
        ,OLDRATE     BADAPR.OLDRATE%TYPE        -- 加計比率
        ,OCACCADDAMT BADAPR.OCACCADDAMT%TYPE    -- 已領職災增給金額
        ,ISSUEAMT    BADAPR.ISSUEAMT%TYPE       -- 受款人核定金額
        ,RECAMT      BADAPR.RECAMT%TYPE         -- 收回金額
        ,SUPAMT      BADAPR.SUPAMT%TYPE         -- 補發金額
        ,APLPAYMK    BADAPR.APLPAYMK%TYPE       -- 帳務註記
        ,APLPAYDATE  BADAPR.APLPAYDATE%TYPE     -- 帳務日期
        ,REMITMK     BADAPR.REMITMK%TYPE        -- 後續註記
        ,REMITDATE   BADAPR.REMITDATE%TYPE      -- 處理註記日期
        );
    TYPE ba_badapr_tab IS TABLE OF ba_badapr_rec;

FUNCTION ba_baappbase(
    p_idn   IN VARCHAR2
   ,p_brith IN VARCHAR2
   ,p_name  IN VARCHAR2
) RETURN ba_baappbase_tab PIPELINED;

FUNCTION ba_badapr(
    p_idn   IN VARCHAR2
   ,p_brith IN VARCHAR2
   ,p_name  IN VARCHAR2
) RETURN ba_badapr_tab PIPELINED;

END PKG_BAAPPLYDATA;
/

CREATE OR REPLACE PACKAGE BODY BA.PKG_BAAPPLYDATA IS

    CURSOR baappbase_cursor(
        p_idn   IN VARCHAR2
       ,p_brith IN VARCHAR2
       ,p_name  IN VARCHAR2
    ) RETURN ba_baappbase_rec IS
        SELECT A.APNO,
               A.SEQNO,
               'AA' AS SYSCODE, --系統類別
               SUBSTR(A.APNO, 1, 1) AS PAYCODE,
               A.IMK,
               A.PAYKIND,
               A.APPDATE,
               A.CASETYP,
               A.MAPNO,
               A.MAPROOTMK,
               A.PROCSTAT,
               A.ACCEPTMK,
               A.MANCHKMK,
               A.EXESTAT,
               A.APUBNO,
               A.APUBNOCK,
               A.LSUBNO,
               A.LSUBNOCK,
               A.EVTIDS,
               A.EVTIDNNO,
               A.EVTBRDATE,
               A.EVTNAME,
               A.EVTAPPNAME,
               A.EVTAPPIDNNO,
               A.EVTAPPBRDATE,
               A.EVTSEX,
               A.EVTNATIONTPE,
               A.EVTNATIONCODE,
               A.EVTIDENT,
               A.EVTJOBDATE,
               A.EVTEXPIREDATE,
               A.EVTELIGIBLEDATE,
               A.EVTAGE,
               A.DABAPNO,
               A.DABANNUAMT,
               A.ISSUYM,
               A.PAYYM,
               A.ISSUEAMT,
               A.PAYDATE,
               A.PAYYMS,
               A.PAYYME,
               A.PAYAMTS,
               A.ANNUAMT,
               A.MEXCLVL,
               A.REALEXCLVL,
               A.CHKDATE,
               A.CHKMAN,
               A.RECHKDATE,
               A.RECHKMAN,
               A.EXEDATE,
               A.EXEMAN,
               A.ARCDATE,
               A.ARCPG,
               A.CLOSEDATE,
               A.CLOSECAUSE,
               A.CHECKIN,
               B.EVAPPTYP,
               B.EVCODE,
               B.EVTYP,
               B.CRIINPART1,
               B.CRIINPART2,
               B.CRIINPART3,
               B.CRIINJNME1,
               B.CRIINJNME2,
               B.CRIINJNME3,
               B.CRIINJNME4,
               B.HOSID, --醫療院所代號 在BAAPPEXPAND,
               B.DOCTORNAME1,
               B.DOCTORNAME2,
               B.OCACCIDENTMK,
               A.BENIDNNO,
               A.BENNAME,
               A.BENBRDATE,
               A.BENSEX,
               A.BENNATIONTYP,
               A.BENNATIONCODE,
               A.BENEVTREL,
               A.TEL1,
               A.TEL2,
               A.MOBILEPHONE,
               A.COMMTYP,
               A.COMMZIP,
               A.COMMADDR,
               A.EMAIL,
               A.PAYTYP,
               A.BANKNATIONCODE,
               A.BANKNAME,
               A.BRANCHNAME,
               A.PAYBANKID,
               A.BRANCHID,
               A.PAYEEACC,
               A.GRDIDNNO,
               A.GRDNAME,
               A.GRDBRDATE
          FROM BAAPPBASE A
          LEFT OUTER JOIN BAAPPEXPAND B
            ON A.APNO = B.APNO
           AND A.SEQNO = B.SEQNO
         WHERE NVL(A.CASEMK, 'X') != 'D'
           AND EXISTS (SELECT 1
                  FROM (SELECT APNO
                          FROM BAAPPBASE
                         WHERE BENIDNNO = P_IDN
                        UNION ALL
                        SELECT APNO
                          FROM BAAPPBASE
                         WHERE BENBRDATE = P_BRITH
                           AND BENNAME = P_NAME) T
                 WHERE T.APNO = A.APNO)
         ORDER BY A.APNO, A.SEQNO;

    CURSOR badapr_cursor(
        p_idn   IN VARCHAR2
       ,p_brith IN VARCHAR2
       ,p_name  IN VARCHAR2
    ) RETURN ba_badapr_rec IS
        SELECT DISTINCT BASE.APNO,
                        BASE.SEQNO,
                        DAPR.PAYYM,
                        BASE.PAYKIND,
                        BASE.BENIDNNO,
                        BASE.BENNAME,
                        BASE.BENBRDATE,
                        DAPR.INSAVGAMT,
                        DAPR.OLDAB,
                        DAPR.OLDAAMT,
                        DAPR.OLDBAMT,
                        DAPR.OLDRATE,
                        DAPR.OCACCADDAMT,
                        --,SUM(DAPR).ISSUEAMT
                        SUM(DAPR.ISSUEAMT) OVER(PARTITION BY DAPR.PAYYM) ISSUEAMT,
                        --,SUM(DAPR).RECAMT
                        SUM(DAPR.RECAMT) OVER(PARTITION BY DAPR.PAYYM) RECAMT,
                        --,SUM(DAPR).SUPAMT
                        SUM(DAPR.SUPAMT) OVER(PARTITION BY DAPR.PAYYM) SUPAMT,
                        DAPR.APLPAYMK,
                        DAPR.APLPAYDATE,
                        DAPR.REMITMK,
                        DAPR.REMITDATE
          FROM BAAPPBASE BASE
          JOIN BADAPR DAPR
            ON BASE.APNO = DAPR.APNO
           AND BASE.SEQNO = DAPR.SEQNO
           AND DAPR.MTESTMK = 'F'
           AND DAPR.APLPAYMK = '3'
           AND DAPR.APLPAYDATE IS NOT NULL
           AND DAPR.PAYKIND NOT IN ('34', '44', '54')
         WHERE NVL(BASE.CASEMK, 'X') != 'D' --D-本案註銷
           AND EXISTS (SELECT 1
                  FROM (SELECT APNO
                          FROM BAAPPBASE
                         WHERE BENIDNNO = p_idn
                        UNION ALL
                        SELECT APNO
                          FROM BAAPPBASE
                         WHERE BENBRDATE = p_brith
                           AND BENNAME = p_name) EXIST
                 WHERE EXIST.APNO = BASE.APNO);

FUNCTION ba_baappbase(
    p_idn   IN VARCHAR2
   ,p_brith IN VARCHAR2
   ,p_name  IN VARCHAR2
) RETURN ba_baappbase_tab PIPELINED IS
BEGIN
    FOR l_row IN baappbase_cursor(p_idn, p_brith, p_name)
    LOOP
        PIPE ROW(l_row);
    END LOOP;
END;

FUNCTION ba_badapr
(
    p_idn   IN VARCHAR2
   ,p_brith IN VARCHAR2
   ,p_name  IN VARCHAR2
) RETURN ba_badapr_tab
    PIPELINED IS
BEGIN
    FOR l_row IN badapr_cursor(p_idn, p_brith, p_name)
    LOOP
        PIPE ROW(l_row);
    END LOOP;
END;

END PKG_BAAPPLYDATA;
/
