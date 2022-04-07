CREATE OR REPLACE PACKAGE BA.PKG_BAAPPLYDATA AUTHID DEFINER IS

    TYPE ba_baappbase_rec IS RECORD(
         apno             baappbase.apno%TYPE -- 受理編號
        ,seqno            baappbase.seqno%TYPE -- 序號
        ,syscode          VARCHAR2(2)  -- 受理類別碼
        ,paycode          VARCHAR2(1)   -- 給付別
        ,imk              baappbase.imk%TYPE -- 保險別
        ,paykind          baappbase.paykind%TYPE --給付種類
        ,appdate          baappbase.appdate%TYPE --申請日期
        --,bbappdate        baappbase.bbappdate%TYPE --受理日期
        ,casetyp          baappbase.casetyp%TYPE --案件類別
        ,mapno            baappbase.mapno%TYPE --相關受理編號
        ,maprootmk        baappbase.maprootmk%TYPE --相關受理編號註記
        ,procstat         baappbase.procstat%TYPE --處理狀態
        ,acceptmk         baappbase.acceptmk%TYPE -- 合格註記
        ,manchkmk         baappbase.manchkmk%TYPE -- 人工審核結果
        ,exestat          baappbase.exestat%TYPE -- 決行狀況
        ,apubno           baappbase.apubno%TYPE -- 申請單位保險證號
        ,apubnock         baappbase.apubnock%TYPE -- 申請單位保險證號檢查碼
        ,lsubno           baappbase.lsubno%TYPE -- 最後單位保險證號
        ,lsubnock         baappbase.lsubnock%TYPE -- 最後單位保險證號檢查碼
        ,evtids           baappbase.evtids%TYPE -- 事故者社福識別碼
        ,evtidnno         baappbase.evtidnno%TYPE -- 事故者身分證號
        ,evtbrdate        baappbase.evtbrdate%TYPE --事故者出生日期
        ,evtname          baappbase.evtname%TYPE --事故者姓名
        ,evtappname       baappbase.evtappname%TYPE -- 事故者申請時姓名
        ,evtappidnno      baappbase.evtappidnno%TYPE -- 事故者申請時身分證號
        ,evtappbrdate     baappbase.evtappbrdate%TYPE -- 事故者申請時出生日期
        ,evtsex           baappbase.evtsex%TYPE -- 事故者性別
        ,evtnationtpe     baappbase.evtnationtpe%TYPE -- 事故者國籍別
        ,evtnationcode    baappbase.evtnationcode%TYPE -- 事故者國籍
        ,evtident         baappbase.evtident%TYPE -- 事故者身分別
        ,evtjobdate       baappbase.evtjobdate%TYPE -- 事故日期
        ,evtexpiredate    baappbase.evtexpiredate%TYPE -- 事故者屆齡日期
        ,evteligibledate  baappbase.evteligibledate%TYPE -- 事故者符合日期
        ,evtage           baappbase.evtage%TYPE -- 事故者申請時年齡
        ,dabapno          baappbase.dabapno%TYPE -- 已領其他年金受理編號
        ,dabannuamt       baappbase.dabannuamt%TYPE -- 已領其他年金金額
        ,issuym           baappbase.issuym%TYPE -- 核定年月
        ,payym            baappbase.payym%TYPE -- 給付年月
        ,issueamt         baappbase.issueamt%TYPE -- 總核定金額
        ,paydate          baappbase.paydate%TYPE -- 首次核定日期
        ,payyms           baappbase.payyms%TYPE -- 首次發放起月
        ,payyme           baappbase.payyme%TYPE -- 首次發放迄月
        ,payamts          baappbase.payamts%TYPE -- 首次核定總金額
        ,annuamt          baappbase.annuamt%TYPE -- 累計已領年金金額
        ,mexclvl          baappbase.mexclvl%TYPE -- 應決行層級
        ,realexclvl       baappbase.realexclvl%TYPE -- 實際決行層級
        ,chkdate          baappbase.chkdate%TYPE -- 審核日期
        ,chkman           baappbase.chkman%TYPE -- 審核人員
        ,rechkdate        baappbase.rechkdate%TYPE -- 複核日期
        ,rechkman         baappbase.rechkman%TYPE -- 複核人員
        ,exedate          baappbase.exedate%TYPE -- 決行日期
        ,exeman           baappbase.exeman%TYPE -- 決行人員
        ,arcdate          baappbase.arcdate%TYPE -- 歸檔日期
        ,arcpg            baappbase.arcpg%TYPE -- 歸檔頁次
        ,closedate        baappbase.closedate%TYPE -- 結案日期
        ,closecause       baappbase.closecause%TYPE -- 結案原因
        ,checkin          baappbase.checkin%TYPE -- 來源別
        ,evapptyp         baappexpand.evapptyp%TYPE -- 申請傷病分類
        ,evcode           baappexpand.evcode%TYPE -- 傷病原因
        ,evtyp            baappexpand.evtyp%TYPE -- 傷病分類
        ,criinpart1       baappexpand.criinpart1%TYPE -- 受傷部位1
        ,criinpart2       baappexpand.criinpart2%TYPE -- 受傷部位2
        ,criinpart3       baappexpand.criinpart3%TYPE -- 受傷部位3
        ,criinjnme1       baappexpand.criinjnme1%TYPE -- 國際疾病代碼1
        ,criinjnme2       baappexpand.criinjnme2%TYPE -- 國際疾病代碼2
        ,criinjnme3       baappexpand.criinjnme3%TYPE -- 國際疾病代碼3
        ,criinjnme4       baappexpand.criinjnme4%TYPE -- 國際疾病代碼4
        ,hosid            baappexpand.hosid%TYPE -- 職病醫療院所代號
        ,doctorname1      baappexpand.doctorname1%TYPE -- 醫師姓名1
        ,doctorname2      baappexpand.doctorname2%TYPE -- 醫師姓名2
        --,crimedium        baappbase.crimedium%TYPE -- 媒介物/職業病種類
        --,accdate          baappbase.accdate%TYPE -- 傷害發生日期
        --,acctyp           baappbase.acctyp%TYPE -- 災害發生地址別
        --,acczip           baappbase.acczip%TYPE -- 災害發生郵遞區號
        --,accareacode      baappbase.accareacode%TYPE -- 災害發生地區
        --,acccounty        baappbase.acccounty%TYPE -- 災害發生縣市
        --,acctown          baappbase.acctown%TYPE -- 災害發生鄉鎮市區
        --,accaddr          baappbase.accaddr%TYPE -- 災害發生地點
        --,acccaubaddr      baappbase.acccaubaddr%TYPE -- 投保單位地址
        --,lawbasis         baappbase.lawbasis%TYPE -- 適用法規(法源依據)
        --,policycase       baappbase.policycase%TYPE -- 政策放寬案件
        --,ocacchosid       baappbase.ocacchosid%TYPE -- 職病醫療院所代碼
        --,ocaccdoctorname1 baappbase.ocaccdoctorname1%TYPE -- 職病醫師姓名1
        --,ocaccdoctorname2 baappbase.ocaccdoctorname2%TYPE -- 職病醫師姓名2
        --,dxreason         baappbase.dxreason%TYPE -- 職病診斷來源
        --,majordiskind     baappbase.majordiskind%TYPE -- 重大災害種類
        --,disasterkind     baappbase.disasterkind%TYPE -- 重大災害種類細項代碼
        --,reparationmk     baappbase.reparationmk%TYPE -- 應追償案件註記
        --,ciid             baappbase.ciid%TYPE -- 勞就保識別碼
        ,ocaccidentmk     baappexpand.ocaccidentmk%TYPE -- 符合第20條之1註記
        ,benidnno         baappbase.benidnno%TYPE -- 受益人身分證號
        ,benname          baappbase.benname%TYPE -- 受益人姓名
        ,benbrdate        baappbase.benbrdate%TYPE -- 受益人出生日期
        ,bensex           baappbase.bensex%TYPE -- 受益人性別
        ,bennationtyp     baappbase.bennationtyp%TYPE -- 受益人國籍別
        ,bennationcode    baappbase.bennationcode%TYPE -- 受益人國籍
        ,benevtrel        baappbase.benevtrel%TYPE -- 受益人與事故者關係
        ,tel1             baappbase.tel1%TYPE -- 電話1
        ,tel2             baappbase.tel2%TYPE -- 電話2
        ,mobilephone      baappbase.mobilephone%TYPE -- 手機複驗
        ,commtyp          baappbase.commtyp%TYPE -- 地址別
        ,commzip          baappbase.commzip%TYPE -- 郵遞區號
        --,commareacode     baappbase.commareacode%TYPE -- 地區代碼
        --,commcounty       baappbase.commcounty%TYPE -- 縣市
        --,commtown         baappbase.commtown%TYPE -- 鄉鎮市區
        ,commaddr         baappbase.commaddr%TYPE -- 現地址
        --,cvladdr          baappbase.cvladdr%TYPE -- 戶籍地址
        ,email            baappbase.email%TYPE -- 電子郵件
        ,paytyp           baappbase.paytyp%TYPE -- 給付方式
        ,banknationcode   baappbase.banknationcode%TYPE -- 金融機構國籍
        ,bankname         baappbase.bankname%TYPE -- 金融機構名稱
        ,branchname       baappbase.branchname%TYPE -- 金融機構分行名稱
        ,paybankid        baappbase.paybankid%TYPE -- 金融機構總代號
        ,branchid         baappbase.branchid%TYPE -- 分支代號
        ,payeeacc         baappbase.payeeacc%TYPE -- 銀行帳號
        ,grdidnno         baappbase.grdidnno%TYPE -- 法定代理人身分證號
        ,grdname          baappbase.grdname%TYPE -- 法定代理人姓名
        ,grdbrdate        baappbase.grdbrdate%TYPE -- 法定代理人出生日期
        );
    TYPE ba_baappbase_tab IS TABLE OF ba_baappbase_rec;

    TYPE ba_badapr_rec IS RECORD(
         apno        badapr.apno%TYPE -- 受理編號
        ,seqno       badapr.seqno%TYPE -- 序號
        ,payym       badapr.payym%TYPE -- 給付年月
        ,paykind     badapr.paykind%TYPE -- 給付種類
        ,benidnno    baappbase.benidnno%TYPE -- 受益人身分證號
        ,benname     baappbase.benname%TYPE -- 受益人姓名
        ,benbrdate   baappbase.benbrdate%TYPE -- 受益人出生日期
        ,insavgamt   badapr.insavgamt%TYPE -- 平均薪資
        ,oldab       badapr.oldab%TYPE -- 第一式/第二式
        ,oldaamt     badapr.oldaamt%TYPE -- 勞保計算金額
        ,oldbamt     badapr.oldbamt%TYPE -- 勞保給付金額
        ,oldrate     badapr.oldrate%TYPE -- 加計比率
        ,ocaccaddamt badapr.ocaccaddamt%TYPE -- 已領職災增給金額
        ,issueamt    badapr.issueamt%TYPE -- 受款人核定金額
        ,recamt      badapr.recamt%TYPE -- 收回金額
        ,supamt      badapr.supamt%TYPE -- 補發金額
        ,aplpaymk    badapr.aplpaymk%TYPE -- 帳務註記
        ,aplpaydate  badapr.aplpaydate%TYPE -- 帳務日期
        ,remitmk     badapr.remitmk%TYPE -- 後續註記
        ,remitdate   badapr.remitdate%TYPE -- 處理註記日期
        );
    TYPE ba_badapr_tab IS TABLE OF ba_badapr_rec;



    FUNCTION ba_baappbase
    (
        p_idn   IN VARCHAR2
       ,p_brith IN VARCHAR2
       ,p_name  IN VARCHAR2
    ) RETURN ba_baappbase_tab
        PIPELINED;

    FUNCTION ba_badapr
    (
        p_idn   IN VARCHAR2
       ,p_brith IN VARCHAR2
       ,p_name  IN VARCHAR2
    ) RETURN ba_badapr_tab
        PIPELINED;


END PKG_BAAPPLYDATA;
/

CREATE OR REPLACE PACKAGE BODY BA.PKG_BAAPPLYDATA IS

    CURSOR baappbase_cursor
    (
        p_idn   IN VARCHAR2
       ,p_brith IN VARCHAR2
       ,p_name  IN VARCHAR2
    ) RETURN ba_baappbase_rec IS
        SELECT a.apno,
       a.seqno,
       'AA' AS SYSCODE, --系統類別
       substr(a.apno, 1, 1) as paycode,
       a.imk,
       a.paykind,
       a.appdate,
       -- ,a.bbappdate,
       a.casetyp,
       a.mapno,
       a.maprootmk,
       a.procstat,
       a.acceptmk,
       a.manchkmk,
       a.exestat,
       a.apubno,
       a.apubnock,
       a.lsubno,
       a.lsubnock,
       a.evtids,
       a.evtidnno,
       a.evtbrdate,
       a.evtname,
       a.evtappname,
       a.evtappidnno,
       a.evtappbrdate,
       a.evtsex,
       a.evtnationtpe,
       a.evtnationcode,
       a.evtident,
       a.evtjobdate,
       a.evtexpiredate,
       a.evteligibledate,
       a.evtage,
       a.dabapno,
       a.dabannuamt,
       a.issuym,
       a.payym,
       a.issueamt,
       a.paydate,
       a.payyms,
       a.payyme,
       a.payamts,
       a.annuamt,
       a.mexclvl,
       a.realexclvl,
       a.chkdate,
       a.chkman,
       a.rechkdate,
       a.rechkman,
       a.exedate,
       a.exeman,
       a.arcdate,
       a.arcpg,
       a.closedate,
       a.closecause,
       a.checkin,
       b.evapptyp,
       b.evcode,
       b.evtyp,
       b.criinpart1,
       b.criinpart2,
       b.criinpart3,
       b.criinjnme1,
       b.criinjnme2,
       b.criinjnme3,
       b.criinjnme4,
       b.hosid, --醫療院所代號 在baappexpand,
       b.doctorname1,
       b.doctorname2,
       b.ocaccidentmk,
       -- ,a.crimedium
       -- ,a.accdate
       -- ,a.acctyp
       -- ,a.acczip
       -- ,a.accareacode
       -- ,a.acccounty
       -- ,a.acctown
       -- ,a.accaddr
       -- ,a.acccaubaddr
       -- ,a.lawbasis
       -- ,a.policycase
       -- ,a.ocacchosid --職病醫療院所代碼
       -- ,a.ocaccdoctorname1 --職病醫師姓名1
       -- ,a.ocaccdoctorname2 --職病醫師姓名2
       -- ,a.dxreason 無此欄位
       -- ,a.majordiskind 無此欄位
       -- ,a.disasterkind 無此欄位
       -- ,a.reparationmk
       -- ,a.ciid 無此欄位,
       a.benidnno,
       a.benname,
       a.benbrdate,
       a.bensex,
       a.bennationtyp,
       a.bennationcode,
       a.benevtrel,
       a.tel1,
       a.tel2,
       a.mobilephone,
       a.commtyp,
       a.commzip,
       -- ,a.commcounty
       -- ,a.commtown,
       a.commaddr,
       a.email,
       a.paytyp,
       a.banknationcode,
       a.bankname,
       a.branchname,
       a.paybankid,
       a.branchid,
       a.payeeacc,
       a.grdidnno,
       a.grdname,
       a.grdbrdate
  FROM baappbase a
  left outer join baappexpand b
    on a.apno = b.apno
   and a.seqno = b.seqno
 WHERE nvl(a.casemk, 'X') != 'D'
   AND EXISTS (SELECT 1
          FROM (SELECT apno
                  FROM baappbase
                 WHERE benidnno = p_idn
                UNION ALL
                SELECT apno
                  FROM baappbase
                 WHERE benbrdate = p_brith
                   AND benname = p_name
                ) t
         WHERE t.apno = a.apno)
 ORDER BY a.apno, a.seqno;

    CURSOR badapr_cursor
    (
        p_idn   IN VARCHAR2
       ,p_brith IN VARCHAR2
       ,p_name  IN VARCHAR2
    ) RETURN ba_badapr_rec IS
     SELECT a.apno
              ,a.seqno
              ,a.payym
              ,A.paykind
              ,A.benidnno
              ,A.benname
              ,A.benbrdate
              ,b.insavgamt
              ,b.oldab
              ,b.oldaamt
              ,b.oldbamt
              ,b.oldrate
              ,b.ocaccaddamt
              ,c.issueamt
              ,a.recamt
              ,a.supamt
              ,b.aplpaymk
              ,b.aplpaydate
              ,b.remitmk
              ,b.remitdate
          FROM (SELECT a.apno
                      ,a.seqno
                      ,a.paykind
                      ,a.benidnno
                      ,a.benname
                      ,a.benbrdate
                      ,c.payym
                      ,c.issuym
                      ,c.supamt
                      ,c.recamt
                  FROM baappbase a
                -- INNER JOIN bbben b
               --     ON a.apno = b.apno
               LEFT OUTER JOIN BAAPPEXPAND B
               ON A.APNO = B.APNO
               AND A.SEQNO = B.SEQNO
                 OUTER apply (SELECT x.payym
                                   ,MAX(x.issuym) AS issuym
                                   ,SUM(x.recamt) AS recamt
                                   ,SUM(x.supamt) AS supamt
                               FROM badapr x
                              WHERE x.apno = b.apno
                                AND x.seqno = b.seqno
                                AND x.payym > (SELECT to_char(add_months(to_date(MAX(t.payym), 'YYYYMM'), -1 * 6), 'YYYYMM')
                                                 FROM badapr t
                                                WHERE t.apno = b.apno
                                                  AND t.seqno = b.seqno
                                                  AND t.mtestmk = 'F'
                                                  AND t.aplpaymk = '3'
                                                  AND t.aplpaydate IS NOT NULL)
                                AND x.mtestmk = 'F'
                                AND x.aplpaymk = '3'
                                AND x.aplpaydate IS NOT NULL
                              GROUP BY x.apno
                                      ,x.seqno
                                      ,x.payym) c
                 WHERE nvl(a.casemk, 'X') != 'D' --D-本案註銷
                   AND EXISTS (SELECT 1
                          FROM (
                                SELECT apno
                                  FROM baappbase
                                 WHERE benidnno = p_idn
                                UNION ALL
                                SELECT apno
                                  FROM baappbase
                                 WHERE benbrdate = p_brith
                                   AND benname = p_name) t
                         WHERE t.apno = a.apno
                           )
                ) a
         INNER JOIN badapr b
            ON b.apno = a.apno
           AND b.seqno = a.seqno
           AND b.payym = a.payym
           AND b.paykind NOT IN ('34', '44', '54')
           AND b.mtestmk = 'F'
           AND b.aplpaymk = '3'
           AND b.aplpaydate IS NOT NULL
           AND b.issuym = a.issuym
         OUTER apply (SELECT SUM(t.issueamt) AS issueamt
                        FROM badapr t
                       WHERE t.apno = a.apno
                         AND t.seqno = a.seqno
                         AND t.payym = a.payym
                         AND t.mtestmk = 'F'
                         AND t.aplpaymk = '3'
                         AND t.aplpaydate IS NOT NULL
                         AND t.issuym = a.issuym) c --c: 月核後已核付的總核定金額
         ORDER BY a.apno
                 ,a.seqno
                 ,a.payym;


    FUNCTION ba_baappbase
    (
        p_idn   IN VARCHAR2
       ,p_brith IN VARCHAR2
       ,p_name  IN VARCHAR2
    ) RETURN ba_baappbase_tab
        PIPELINED IS
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
