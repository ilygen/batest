CREATE OR REPLACE Package BA.PKG_BA_genPayFile
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_genPayFile
    PURPOSE:         產生待發的核付資料及核付媒體檔案

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2009/07/06  Angela Wu    Created this Package.

    NOTES:
    1.各 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/
authid definer is
    v_g_errMsg                     varChar2(4000);
    v_g_ProgName                   varChar2(200);
    v_g_flag                       varchar2(1);
    procedure sp_BA_genBAPay (
        v_i_issuym         in      varChar2,
        v_i_paycode        in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_mtestmk        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_bajobid        in      varchar2,
        v_i_proctype       in      varChar2,
        v_o_flag           out     varchar2
    );

    procedure sp_BA_insBAPay
    (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_paykind        in      varChar2,
        v_i_inskd          in      varChar2,
        v_i_oriissuym      in      varChar2,
        v_i_issuym         in      varChar2,
        v_i_payym          in      varChar2,
        v_i_issutyp        in      varChar2,
        v_i_casetyp        in      varChar2,
        v_i_benevtrel      in      varChar2,
        v_i_benids         in      varChar2,
        v_i_benidnno       in      varChar2,
        v_i_benname        in      varChar2,
        v_i_paytyp         in      varChar2,
        v_i_accseqno       in      varChar2,
        v_i_accname        in      varChar2,
        v_i_accidn         in      varChar2,
        v_i_paybankid      in      varChar2,
        v_i_branchid       in      varChar2,
        v_i_payeeacc       in      varChar2,
        v_i_aplpayamt      in      varChar2,
        v_i_payrate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_dlinedate      in      varChar2,
        v_i_dlineseqno     in      varChar2,
        v_i_dlinemk        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_nlwkmk         in      varChar2,
        v_i_adwkmk         in      varChar2,
        v_i_nachgmk        in      varChar2
    );

    procedure sp_BA_genBAGivetmpDtl (
        v_i_issuym         in      varChar2,
        v_i_paycode        in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_proctime       in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_bajobid        in      varChar2,
        v_o_flag           out     varChar2
    );

    procedure sp_BA_genBAGivePayFile (
        v_i_issuym         in      varChar2,
        v_i_paycode        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_mtestmk        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_bajobid        in      varChar2,
        v_i_userid         in      varChar2,
        v_o_flag           out     varchar2
    );

    procedure sp_BA_genBAPay_S (
        v_i_issuym         in      varChar2,
        v_i_paycode        in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_mtestmk        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_bajobid        in      varchar2,
        v_i_proctype       in      varChar2,
        v_o_flag           out     varchar2
    );
End;
/

CREATE OR REPLACE Package Body BA.PKG_BA_genPayFile
is
    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayFile.sp_BA_genBAPay
        PURPOSE:         產生已核付待發的資料(BAPay)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_mtestmk       (varChar2)       --處理註記
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                                                            -2012/02/08 Add by ChungYu
                        *v_i_bajobid       (varChar2)       --批次線上id add by Zehua
                        *v_i_proctype      (varChar2)       --處理類型(5:批次出媒體  1:線上出媒體)add by Zehua

        PARAMETER(OUT):
                         *v_o_flag         (varChar2)       --傳出值 0:成功；1失敗
        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/06  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genBAPay (
        v_i_issuym       in      varChar2,
        v_i_paycode      in      varChar2,
        v_i_chkdate      in      varChar2,
        v_i_paydate      in      varChar2,
        v_i_mtestmk      in      varChar2,
        v_i_payseqno     in      varChar2,
        v_i_bajobid      in      varchar2,
        v_i_proctype     in      varChar2,
        v_o_flag         out     varchar2
    ) is
        --查詢已核付待發的核付資料(不分給付方式,全抓)
        Cursor c_dataCur_PayData is
            select t1.APNO                                                 --受理編號
                  ,t1.SEQNO                                                --序號
                  ,t1.PAYKIND                                              --給付種類
                  ,t1.INSKD                                                --保險別
                  ,t1.BENEVTREL                                            --受益人與事故者關係
                  ,t1.BENIDS                                               --受益人社福識別碼
                  ,t1.BENIDN                                               --受益人身分證號
                  ,t1.BENNAME                                              --姓名
                  ,t1.SOURCEPAYTYP as PAYTYP                               --給付方式
                  ,t1.ACCIDN                                               --戶名IDN
                  ,t1.ACCNAME                                              --戶名
                  ,t1.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t1.CASETYP                                              --案件類別
                  ,t1.ISSUTYP                                              --核付分類
                  ,t1.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  ,t1.ISSUYM                                               --核定年月
                  ,t1.PAYYM                                                --給付年月
                  ,t1.APLPAYAMT                                            --實付金額
                  ,t1.PAYRATE                                              --匯款匯費
                  ,t1.PAYBANKID                                            --金融機構總代號
                  ,deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID)) AS BRANCHID--分支代號
                  ,t1.PAYEEACC                                             --銀行帳號
                  ,' ' as DLINEDATE                                        --勞貸本息截止日
                  ,' ' as DLINESEQNO                                       --流水號(勞貸)
                  ,' ' as DLINEMK                                          --勞貸戶結清註記
                  ,t1.NLWKMK                                               --普職註記
                  ,t1.ADWKMK                                               --加職註記
                  ,t1.NACHGMK                                              --普職互改註記
              from BAISSUDATATEMP t1
             where t1.APNO like (v_i_paycode||'%')
              -- and t1.SEQNO = '0000'                                     -- 2012/06/06 Mark by ChungYu
               and t1.BENEVTREL NOT IN ('F','N','Z')                       -- 2012/06/06 Add by ChungYu
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and t1.PAYSEQNO = v_i_payseqno
               and t1.MANCHKMK = 'Y';

        --查詢已核付待發的勞貸資料(不分給付方式,全抓)
        --20130223 Modify by Angela：增加 Union。因勞貸資料結清後會搬檔至紓困結案檔，可能會導致出媒體檔時無資料，所以需要 Union 紓困結案檔。
        Cursor c_dataCur_FPayData is
            select t1.APNO                                                 --受理編號
                  ,t1.SEQNO                                                --序號
                  ,t1.PAYKIND                                              --給付種類
                  ,t1.INSKD                                                --保險別
                  ,t1.BENEVTREL                                            --受益人與事故者關係
                  ,t1.BENIDS                                               --受益人社福識別碼
                  ,t1.BENIDN                                               --受益人身分證號
                  ,t1.BENNAME                                              --姓名
                  ,'3' as PAYTYP                                           --給付方式
                  ,t1.ACCIDN                                               --戶名IDN
                  ,t1.ACCNAME                                              --戶名
                  ,t1.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t1.CASETYP                                              --案件類別
                  ,t1.ISSUTYP                                              --核付分類
                  ,t1.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  ,t1.ISSUYM                                               --核定年月
                  ,t1.PAYYM                                                --給付年月
                  ,t1.OFFSETAMT as APLPAYAMT                               --抵銷金額
                  ,t1.PAYRATE                                              --匯款匯費
                  ,t2.PAYBANKID                                            --金融機構總代號
                  ,deCode(t2.PAYBANKID,'700',t2.BRANCHID,deCode('3','1','0000',t2.BRANCHID)) AS BRANCHID--分支代號
                  ,t2.PAYEEACC                                             --銀行帳號
                  ,t1.DLINEDATE                                            --勞貸本息截止日
                  ,t2.DLINESEQNO                                           --流水號(勞貸)
                  ,t1.DLINEMK                                              --勞貸戶結清註記
                  ,t1.NLWKMK                                               --普職註記
                  ,t1.ADWKMK                                               --加職註記
                  ,t1.NACHGMK                                              --普職互改註記
              from BAISSUDATATEMP t1
                  ,(select tt2.IDN_APLY
                          ,tt2.IDN_LIB
                          ,substr(tt2.BRNO,1,3) as BRANCHID
                          ,((deCode(length(tt2.SEQNO),6,(deCode(substr(tt2.SEQNO,1,1),'0','092'
                                                                                     ,'3','093'
                                                                                     ,'5','095'
                                                                                     ,'6','096'
                                                                                     ,'7','097'
                                                                                     ,'8','098'
                                                                                     ,'9','099'
                                                                                     ,substr(tt2.SEQNO,1,1))
                                                        ),substr(tt2.SEQNO,1,1))
                            )||substr(tt2.SEQNO,2,8)) as DLINESEQNO
                          ,tt2.ACCOUNT_PAY as PAYEEACC
                          ,' ' as PAYBANKID
                      from MBLNM tt2
                     where nvl(trim(tt2.EMRK),' ') <> '1'
                   ) t2
             where t1.APNO like (v_i_paycode||'%')
               and (t1.BENIDN = t2.IDN_APLY or t1.BENIDN = t2.IDN_LIB)
               and t1.SEQNO = '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and t1.PAYSEQNO = v_i_payseqno
               and t1.MANCHKMK = 'Y'
               and t1.OFFSETAMT > 0                                        -- 2012/06/19 ChungYu Add 勞貸金額大於0才抓
            /* union
            select t1.APNO                                                 --受理編號
                  ,t1.SEQNO                                                --序號
                  ,t1.PAYKIND                                              --給付種類
                  ,t1.INSKD                                                --保險別
                  ,t1.BENEVTREL                                            --受益人與事故者關係
                  ,t1.BENIDS                                               --受益人社福識別碼
                  ,t1.BENIDN                                               --受益人身分證號
                  ,t1.BENNAME                                              --姓名
                  ,'3' as PAYTYP                                           --給付方式
                  ,t1.ACCIDN                                               --戶名IDN
                  ,t1.ACCNAME                                              --戶名
                  ,t1.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t1.CASETYP                                              --案件類別
                  ,t1.ISSUTYP                                              --核付分類
                  ,t1.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  ,t1.ISSUYM                                               --核定年月
                  ,t1.PAYYM                                                --給付年月
                  ,t1.OFFSETAMT as APLPAYAMT                               --抵銷金額
                  ,t1.PAYRATE                                              --匯款匯費
                  ,t2.PAYBANKID                                            --金融機構總代號
                  ,deCode(t2.PAYBANKID,'700',t2.BRANCHID,deCode('3','1','0000',t2.BRANCHID)) AS BRANCHID--分支代號
                  ,t2.PAYEEACC                                             --銀行帳號
                  ,t1.DLINEDATE                                            --勞貸本息截止日
                  ,t2.DLINESEQNO                                           --流水號(勞貸)
                  ,'1' as DLINEMK                                          --勞貸戶結清註記(已結清)
                  ,t1.NLWKMK                                               --普職註記
                  ,t1.ADWKMK                                               --加職註記
                  ,t1.NACHGMK                                              --普職互改註記
              from BAISSUDATATEMP t1
                  ,(select tt2.IDN_APLY
                          ,tt2.IDN_LIB
                          ,substr(tt2.BRNO,1,3) as BRANCHID
                          ,((deCode(length(tt2.SEQNO),6,(deCode(substr(tt2.SEQNO,1,1),'0','092'
                                                                                     ,'3','093'
                                                                                     ,'5','095'
                                                                                     ,'6','096'
                                                                                     ,'7','097'
                                                                                     ,'8','098'
                                                                                     ,'9','099'
                                                                                     ,substr(tt2.SEQNO,1,1))
                                                        ),substr(tt2.SEQNO,1,1))
                            )||substr(tt2.SEQNO,2,8)) as DLINESEQNO
                          ,tt2.ACCOUNT_PAY as PAYEEACC
                          ,' ' as PAYBANKID
                      from MBLNC tt2
                     where nvl(trim(tt2.EMRK),' ') = '1'
                       and tt2.EDATE like (to_Char(Sysdate,'YYYY')||'%')
                   ) t2
             where t1.APNO like (v_i_paycode||'%')
               and (t1.BENIDN = t2.IDN_APLY or t1.BENIDN = t2.IDN_LIB)
               and t1.SEQNO = '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and t1.PAYSEQNO = v_i_payseqno
               and t1.MANCHKMK = 'Y'
               and t1.OFFSETAMT > 0*/;

        --查詢已核付待發的補償金資料(不分給付方式,全抓)
        Cursor c_dataCur_ZPayData is
            select distinct
                   t2.APNO                                                 --受理編號
                  ,t2.SEQNO                                                --序號
                  ,t2.PAYKIND                                              --給付種類
                  ,t2.INSKD                                                --保險別
                  ,t2.BENEVTREL                                            --受益人與事故者關係
                  ,t2.BENIDS                                               --受益人社福識別碼
                  ,t2.BENIDN                                               --受益人身分證號
                  ,t2.BENNAME                                              --姓名
                  ,t2.PAYTYP                                               --給付方式
                  ,t2.ACCIDN                                               --戶名IDN
                  ,t2.ACCNAME                                              --戶名
                  ,t2.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t2.CASETYP                                              --案件類別
                  ,t2.ISSUTYP                                              --核付分類
                  ,t2.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  ,t2.ISSUYM                                               --核定年月
                  ,t2.PAYYM                                                --給付年月
                  ,t1.COMPENAMT as APLPAYAMT                               --抵銷金額
                  ,t2.PAYRATE                                              --匯款匯費
                  ,t2.PAYBANKID                                            --金融機構總代號
                  ,deCode(t2.PAYBANKID,'700',t2.BRANCHID,deCode(t2.PAYTYP,'1','0000',t2.BRANCHID)) AS BRANCHID--分支代號
                  ,t2.PAYEEACC                                             --銀行帳號
                  ,' ' as DLINESEQNO                                       --流水號(勞貸)
                  ,' ' as DLINEDATE                                        --勞貸本息截止日
                  ,' ' as DLINEMK                                          --勞貸戶結清註記
                  ,t1.NLWKMK                                               --普職註記
                  ,t1.ADWKMK                                               --加職註記
                  ,t1.NACHGMK                                              --普職互改註記
              from BAISSUDATATEMP t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.INSKD
                          ,t21.PAYKIND
                          ,t21.ISSUYM as ORIISSUYM
                          ,t21.ISSUYM
                          ,t21.PAYYM
                          ,t21.CASETYP
                          ,t21.ISSUTYP
                          ,t21.BENEVTREL
                          ,t21.BENIDS
                          ,(nvl(trim(t22.VATNUMBER),t21.BENIDN)) as BENIDN
                          ,t21.BENNAME
                          ,t21.PAYTYP as PAYTYP
                          ,t21.BENIDN as ACCIDN
                          ,t22.GVNAME as ACCNAME
                          ,t21.ACCSEQNO
                          ,t21.PAYRATE
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                          ,t21.BENIDN as COMPID1
                          ,replace(trim(t22.GVNAME),'　','') as COMPNAME1
                      from BAISSUDATATEMP t21,BCCMF08 t22
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.BENEVTREL = 'Z'
                       and t21.BENIDN = t22.GVCD1
                       and t21.ISSUYM = v_i_issuym
                       and t21.CHKDATE = v_i_chkdate
                   ) t2,PBBMSA t3,CAUB t4
             where t1.APNO = t2.APNO
               and t1.ISSUYM = t2.ISSUYM
               and t1.PAYYM = t2.PAYYM
               and t1.BENIDN = t3.BMEVIDNO
               and t1.APNO like (v_i_paycode||'%')
               and t3.BMPAYKND = '4'
               and t1.SEQNO = '0000'
               and t1.BENEVTREL = '1'
               and t2.SEQNO <> '0000'
               and t2.BENEVTREL = 'Z'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               --修改讀取補償金單位的條件 Modify by Angela 20120725
               --and ((substr(t3.BMAPNO,5,3)='460')
               --  or (substr(t3.BMAPNO,5,3)>='461'
               --  and substr(t3.BMAPNO,5,3)<='469'
               --  and nvl(trim(t3.BMEXMDTE),' ')<>' '))
               and ((substr(t3.BMAPNO,5,3)='469')
                 or (substr(t3.BMAPNO,5,3)>='460'
                 and substr(t3.BMAPNO,5,3)<='468'
                 and nvl(trim(t3.BMEXMDTE),' ')<>' '
                 and t3.BMEXMDTE is not null))
               and t4.UBNO = t3.BMOLDAPLDPT
               and nvl(trim(t3.BMOLDAPLDPT),' ')<>' '
               and t1.COMPENAMT > 0
               and t1.payseqno = v_i_payseqno
               and t1.MANCHKMK = 'Y';

        --查詢已核付待發不含補償金單位的改匯資料(不分給付方式,全抓)
        Cursor c_dataCur_LBAC is
            select t1.APNO                                                 --受理編號
                  ,t1.SEQNO                                                --序號
                  ,t1.PAYKIND                                              --給付種類
                  ,t3.INSKD                                                --保險別
                  ,t3.BENEVTREL                                            --受益人與事故者關係
                  ,t3.BENIDS                                               --受益人社福識別碼
                  ,t3.BENIDN                                               --受益人身分證號
                  ,t3.BENNAME                                              --姓名
                  ,t1.PAYTYP                                               --給付方式
                  ,t1.ACCIDN                                               --戶名IDN
                  ,t1.ACCNAME                                              --戶名
                  ,t1.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t2.CASETYP                                              --案件類別
                  ,t1.ISSUTYP                                              --核付分類
                  ,t2.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  --,t1.ISSUYM                                             --核定年月
                  ,v_i_issuym as ISSUYM                                    --核定年月
                  ,t1.PAYYM                                                --給付年月
                  ,t1.APLPAYAMT                                            --抵銷金額
                  ,t2.PAYRATE                                              --匯款匯費
                  ,t1.PAYBANKID                                            --金融機構總代號
                  ,deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID)) AS BRANCHID--分支代號
                  ,t1.PAYEEACC                                             --銀行帳號
                  ,' ' as DLINESEQNO                                       --流水號(勞貸)
                  ,' ' as DLINEDATE                                        --勞貸本息截止日
                  ,' ' as DLINEMK                                          --勞貸戶結清註記
                  ,''  as NLWKMK                                           --普職註記
                  ,''  as ADWKMK                                           --加職註記
                  ,''  as NACHGMK                                          --普職互改註記
              from (select tt1.APNO
                          ,tt1.SEQNO
                          ,tt1.ISSUYM
                          ,tt1.ORIISSUYM
                          ,tt1.PAYYM
                          ,tt1.PAYTYP
                          ,tt1.PAYBANKID
                          ,tt1.BRANCHID
                          ,tt1.PAYEEACC
                          ,tt1.ACCIDN
                          ,tt1.ACCNAME
                          ,nvl(trim(tt1.ACCSEQNO),tt1.SEQNO) ACCSEQNO
                          ,tt1.REMITAMT as APLPAYAMT
                          ,tt1.ISSUKIND as PAYKIND                         --add by 20150723 Angela
                          ,'A' as ISSUTYP
                      from BAREGIVEDTL tt1
                     where tt1.APNO like (v_i_paycode||'%')
                       --and tt1.ISSUYM = v_i_issuym
                       and tt1.MK = '2'
                       and nvl(trim(tt1.WORKMK),' ') <> 'Y'
                       and (tt1.AFCHKDATE is not null and nvl(trim(tt1.AFCHKDATE),' ')<>' ')
                       and (tt1.AFPAYDATE is null or nvl(trim(tt1.AFPAYDATE),' ')=' ')
                   ) t1
                  ,(select tt2.APNO
                          ,tt2.SEQNO
                          ,tt2.ISSUYM
                          ,tt2.PAYYM
                          ,tt2.MCHKTYP as CASETYP
                          ,tt2.PAYKIND                                     --add by 20150723 Angela
                          ,0 as PAYRATE
                          ,' ' as DLINEDATE
                          ,' ' as DLINESEQNO
                      from BADAPR tt2
                     where tt2.APNO like (v_i_paycode||'%')
                       and tt2.MTESTMK = v_i_mtestmk
                       and tt2.MANCHKMK = 'Y'
                       and tt2.BENEVTREL <> 'Z'
                       and tt2.REMITMK in ('2','3')
                       --and (tt2.ACCEPTMK = 'Y' or nvl(trim(tt2.ACCEPTMK),' ') = ' ')    -Mark by Angela 20190927
                       and (tt2.CHKDATE is not null and nvl(trim(tt2.CHKDATE),' ')<>' ')
                       and (tt2.REMITDATE is not null and nvl(trim(tt2.REMITDATE),' ')<>' ')
                   ) t2
                  ,(select tt3.APNO
                          ,tt3.SEQNO
                          ,tt3.IMK as INSKD
                          ,tt3.PAYKIND
                          ,tt3.BENIDS
                          ,tt3.BENIDNNO as BENIDN
                          ,tt3.BENNAME
                          ,tt3.BENEVTREL
                      from BAAPPBASE tt3
                     where tt3.APNO like (v_i_paycode||'%')
                       and tt3.BENEVTREL<>'Z'
                   ) t3
             where t1.APNO = t2.APNO
               and t1.APNO = t3.APNO
               and t2.APNO = t3.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.SEQNO = t3.SEQNO
               and t2.SEQNO = t3.SEQNO
               and t1.ORIISSUYM = t2.ISSUYM
               and t1.PAYYM = t2.PAYYM
               and t1.PAYKIND = t2.PAYKIND                       --add by 20150723 Angela
               and t1.APNO like (v_i_paycode||'%');

        --查詢已核付待發只有補償金單位的改匯資料(不分給付方式,全抓)
        Cursor c_dataCur_LBAC_Z is
            select t1.APNO                                                 --受理編號
                  ,t1.SEQNO                                                --序號
                  ,t1.PAYKIND                                              --給付種類
                  ,t3.INSKD                                                --保險別
                  ,t3.BENEVTREL                                            --受益人與事故者關係
                  ,t3.BENIDS                                               --受益人社福識別碼
                  ,t3.BENIDN                                               --受益人身分證號
                  ,t3.BENNAME                                              --姓名
                  ,t1.PAYTYP                                               --給付方式
                  ,t3.ACCIDN                                               --戶名IDN
                  ,t3.ACCNAME                                              --戶名
                  ,t1.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t2.CASETYP                                              --案件類別
                  ,t1.ISSUTYP                                              --核付分類
                  ,t2.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  --,t1.ISSUYM                                             --核定年月
                  ,v_i_issuym as ISSUYM                                    --核定年月
                  ,t1.PAYYM                                                --給付年月
                  ,t1.APLPAYAMT                                            --抵銷金額
                  ,t2.PAYRATE                                              --匯款匯費
                  ,t1.PAYBANKID                                            --金融機構總代號
                  ,deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID)) AS BRANCHID--分支代號
                  ,t1.PAYEEACC                                             --銀行帳號
                  ,' ' as DLINESEQNO                                       --流水號(勞貸)
                  ,' ' as DLINEDATE                                        --勞貸本息截止日
                  ,' ' as DLINEMK                                          --勞貸戶結清註記
                  ,''  as NLWKMK                                           --普職註記
                  ,''  as ADWKMK                                           --加職註記
                  ,''  as NACHGMK                                          --普職互改註記
              from (select tt1.APNO
                          ,tt1.SEQNO
                          ,tt1.ISSUYM
                          ,tt1.ORIISSUYM
                          ,tt1.PAYYM
                          ,tt1.PAYTYP
                          ,tt1.PAYBANKID
                          ,tt1.BRANCHID
                          ,tt1.PAYEEACC
                          ,nvl(trim(tt1.ACCSEQNO),tt1.SEQNO) ACCSEQNO
                          ,tt1.REMITAMT as APLPAYAMT
                          ,tt1.ISSUKIND as PAYKIND                         --add by 20150723 Angela
                          ,'A' as ISSUTYP
                      from BAREGIVEDTL tt1
                     where tt1.APNO like (v_i_paycode||'%')
                       --and tt1.ISSUYM = v_i_issuym
                       and tt1.MK = '2'
                       and nvl(trim(tt1.WORKMK),' ') <> 'Y'
                       and (tt1.AFCHKDATE is not null and nvl(trim(tt1.AFCHKDATE),' ')<>' ')
                       and (tt1.AFPAYDATE is null or nvl(trim(tt1.AFPAYDATE),' ')=' ')
                   ) t1
                  ,(select tt2.APNO
                          ,tt2.SEQNO
                          ,tt2.ISSUYM
                          ,tt2.PAYYM
                          ,tt2.MCHKTYP as CASETYP
                          ,0 as PAYRATE
                          ,' ' as DLINEDATE
                          ,' ' as DLINESEQNO
                          ,tt2.PAYKIND                                     --add by 20150723 Angela
                      from BADAPR tt2
                     where tt2.APNO like (v_i_paycode||'%')
                       and tt2.MTESTMK = v_i_mtestmk
                       and tt2.MANCHKMK = 'Y'
                       and tt2.BENEVTREL = 'Z'
                       and tt2.REMITMK in ('2','3')
                       --and (tt2.ACCEPTMK = 'Y' or nvl(trim(tt2.ACCEPTMK),' ') = ' ')    -Mark by Angela 20190927
                       and (tt2.CHKDATE is not null and nvl(trim(tt2.CHKDATE),' ')<>' ')
                       and (tt2.REMITDATE is not null and nvl(trim(tt2.REMITDATE),' ')<>' ')
                   ) t2
                  ,(select tt31.APNO
                          ,tt31.SEQNO
                          ,tt31.IMK as INSKD
                          ,tt31.PAYKIND
                          ,tt31.BENIDS
                          ,tt31.BENEVTREL
                          ,(nvl(trim(tt32.VATNUMBER),tt31.BENIDNNO)) as BENIDN
                          ,tt31.BENNAME
                          ,tt31.BENIDNNO as ACCIDN
                          ,tt32.GVNAME as ACCNAME
                      from BAAPPBASE tt31,BCCMF08 tt32
                     where tt31.APNO like (v_i_paycode||'%')
                       and tt31.BENEVTREL = 'Z'
                       and tt31.BENIDNNO = tt32.GVCD1
                   ) t3
             where t1.APNO = t2.APNO
               and t1.APNO = t3.APNO
               and t2.APNO = t3.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.SEQNO = t3.SEQNO
               and t2.SEQNO = t3.SEQNO
               and t1.ORIISSUYM = t2.ISSUYM
               and t1.PAYYM = t2.PAYYM
               and t1.PAYKIND = t2.PAYKIND                       --add by 20150723 Angela
               and t1.APNO like (v_i_paycode||'%');

        begin
            v_g_errMsg := ' ';
            v_g_ProgName := 'PKG_BA_genPayFile.sp_BA_genBAPay';
            v_g_flag := '0';
            --將已核付待發的核付資料寫入BAPay

            for v_CurPayData in c_dataCur_PayData Loop
                sp_BA_insBAPay(v_CurPayData.APNO
                              ,v_CurPayData.SEQNO
                              ,v_CurPayData.PAYKIND
                              ,v_CurPayData.INSKD
                              ,v_CurPayData.ORIISSUYM
                              ,v_CurPayData.ISSUYM
                              ,v_CurPayData.PAYYM
                              ,v_CurPayData.ISSUTYP
                              ,v_CurPayData.CASETYP
                              ,v_CurPayData.BENEVTREL
                              ,v_CurPayData.BENIDS
                              ,v_CurPayData.BENIDN
                              ,v_CurPayData.BENNAME
                              ,v_CurPayData.PAYTYP
                              ,v_CurPayData.ACCSEQNO
                              ,v_CurPayData.ACCNAME
                              ,v_CurPayData.ACCIDN
                              ,v_CurPayData.PAYBANKID
                              ,v_CurPayData.BRANCHID
                              ,v_CurPayData.PAYEEACC
                              ,v_CurPayData.APLPAYAMT
                              ,v_CurPayData.PAYRATE
                              ,v_i_paydate
                              ,v_CurPayData.DLINEDATE
                              ,v_CurPayData.DLINESEQNO
                              ,v_CurPayData.DLINEMK
                              ,v_i_payseqno
                              ,v_CurPayData.NLWKMK
                              ,v_CurPayData.ADWKMK
                              ,v_CurPayData.NACHGMK);

            end Loop;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '將已核付待發的核付資料寫入核付檔(BAPay)',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));



            --將已核付待發的勞貸資料寫入BAPay
            for v_CurFPayData in c_dataCur_FPayData Loop
                sp_BA_insBAPay(v_CurFPayData.APNO
                              ,v_CurFPayData.SEQNO
                              ,v_CurFPayData.PAYKIND
                              ,v_CurFPayData.INSKD
                              ,v_CurFPayData.ORIISSUYM
                              ,v_CurFPayData.ISSUYM
                              ,v_CurFPayData.PAYYM
                              ,v_CurFPayData.ISSUTYP
                              ,v_CurFPayData.CASETYP
                              ,v_CurFPayData.BENEVTREL
                              ,v_CurFPayData.BENIDS
                              ,v_CurFPayData.BENIDN
                              ,v_CurFPayData.BENNAME
                              ,v_CurFPayData.PAYTYP
                              ,v_CurFPayData.ACCSEQNO
                              ,v_CurFPayData.ACCNAME
                              ,v_CurFPayData.ACCIDN
                              ,v_CurFPayData.PAYBANKID
                              ,v_CurFPayData.BRANCHID
                              ,v_CurFPayData.PAYEEACC
                              ,v_CurFPayData.APLPAYAMT
                              ,v_CurFPayData.PAYRATE
                              ,v_i_paydate
                              ,v_CurFPayData.DLINEDATE
                              ,v_CurFPayData.DLINESEQNO
                              ,v_CurFPayData.DLINEMK
                              ,v_i_payseqno
                              ,v_CurFPayData.NLWKMK
                              ,v_CurFPayData.ADWKMK
                              ,v_CurFPayData.NACHGMK);
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '將已核付待發的勞貸資料寫入核付檔(BAPay)',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


            --將已核付待發的補償金資料寫入BAPay
            for v_CurZPayData in c_dataCur_ZPayData Loop
                sp_BA_insBAPay(v_CurZPayData.APNO
                              ,v_CurZPayData.SEQNO
                              ,v_CurZPayData.PAYKIND
                              ,v_CurZPayData.INSKD
                              ,v_CurZPayData.ORIISSUYM
                              ,v_CurZPayData.ISSUYM
                              ,v_CurZPayData.PAYYM
                              ,v_CurZPayData.ISSUTYP
                              ,v_CurZPayData.CASETYP
                              ,v_CurZPayData.BENEVTREL
                              ,v_CurZPayData.BENIDS
                              ,v_CurZPayData.BENIDN
                              ,v_CurZPayData.BENNAME
                              ,v_CurZPayData.PAYTYP
                              ,v_CurZPayData.ACCSEQNO
                              ,v_CurZPayData.ACCNAME
                              ,v_CurZPayData.ACCIDN
                              ,v_CurZPayData.PAYBANKID
                              ,v_CurZPayData.BRANCHID
                              ,v_CurZPayData.PAYEEACC
                              ,v_CurZPayData.APLPAYAMT
                              ,v_CurZPayData.PAYRATE
                              ,v_i_paydate
                              ,v_CurZPayData.DLINEDATE
                              ,v_CurZPayData.DLINESEQNO
                              ,v_CurZPayData.DLINEMK
                              ,v_i_payseqno
                              ,v_CurZPayData.NLWKMK
                              ,v_CurZPayData.ADWKMK
                              ,v_CurZPayData.NACHGMK);
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '將已核付待發的補償金資料寫入核付檔(BAPay)',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


            if v_i_proctype = '5' then
                --將已核付待發不含補償金單位的改匯資料寫入BAPay
                for v_LBACCur in c_dataCur_LBAC Loop
                    sp_BA_insBAPay(v_LBACCur.APNO
                                  ,v_LBACCur.SEQNO
                                  ,v_LBACCur.PAYKIND
                                  ,v_LBACCur.INSKD
                                  ,v_LBACCur.ORIISSUYM
                                  ,v_LBACCur.ISSUYM
                                  ,v_LBACCur.PAYYM
                                  ,v_LBACCur.ISSUTYP
                                  ,v_LBACCur.CASETYP
                                  ,v_LBACCur.BENEVTREL
                                  ,v_LBACCur.BENIDS
                                  ,v_LBACCur.BENIDN
                                  ,v_LBACCur.BENNAME
                                  ,v_LBACCur.PAYTYP
                                  ,v_LBACCur.ACCSEQNO
                                  ,v_LBACCur.ACCNAME
                                  ,v_LBACCur.ACCIDN
                                  ,v_LBACCur.PAYBANKID
                                  ,v_LBACCur.BRANCHID
                                  ,v_LBACCur.PAYEEACC
                                  ,v_LBACCur.APLPAYAMT
                                  ,v_LBACCur.PAYRATE
                                  ,v_i_paydate
                                  ,v_LBACCur.DLINEDATE
                                  ,v_LBACCur.DLINESEQNO
                                  ,v_LBACCur.DLINEMK
                                  ,v_i_payseqno
                                  ,v_LBACCur.NLWKMK
                                  ,v_LBACCur.ADWKMK
                                  ,v_LBACCur.NACHGMK);
                end Loop;

                --修改log作法 by TseHua 20180419
                 sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                '將已核付待發不含補償金單位的改匯資料寫入核付檔(BAPay)',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


                --將已核付待發不含補償金單位的改匯資料寫入BAPay
                for v_LBACCur_Z in c_dataCur_LBAC_Z Loop
                    sp_BA_insBAPay(v_LBACCur_Z.APNO
                                  ,v_LBACCur_Z.SEQNO
                                  ,v_LBACCur_Z.PAYKIND
                                  ,v_LBACCur_Z.INSKD
                                  ,v_LBACCur_Z.ORIISSUYM
                                  ,v_LBACCur_Z.ISSUYM
                                  ,v_LBACCur_Z.PAYYM
                                  ,v_LBACCur_Z.ISSUTYP
                                  ,v_LBACCur_Z.CASETYP
                                  ,v_LBACCur_Z.BENEVTREL
                                  ,v_LBACCur_Z.BENIDS
                                  ,v_LBACCur_Z.BENIDN
                                  ,v_LBACCur_Z.BENNAME
                                  ,v_LBACCur_Z.PAYTYP
                                  ,v_LBACCur_Z.ACCSEQNO
                                  ,v_LBACCur_Z.ACCNAME
                                  ,v_LBACCur_Z.ACCIDN
                                  ,v_LBACCur_Z.PAYBANKID
                                  ,v_LBACCur_Z.BRANCHID
                                  ,v_LBACCur_Z.PAYEEACC
                                  ,v_LBACCur_Z.APLPAYAMT
                                  ,v_LBACCur_Z.PAYRATE
                                  ,v_i_paydate
                                  ,v_LBACCur_Z.DLINEDATE
                                  ,v_LBACCur_Z.DLINESEQNO
                                  ,v_LBACCur_Z.DLINEMK
                                  ,v_i_payseqno
                                  ,v_LBACCur_Z.NLWKMK
                                  ,v_LBACCur_Z.ADWKMK
                                  ,v_LBACCur_Z.NACHGMK);
                end Loop;

                --修改log作法 by TseHua 20180419
                 sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                 '將已核付待發不含補償金單位的改匯資料寫入核付檔(BAPay)',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

            end if;

             v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    --LOG BATCHJOB detail Add By Zehua 20140722
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                     v_g_flag := '1';
                     v_o_flag := v_g_flag;
        end;
    --procedure sp_BA_genBAPay End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayFile.sp_BA_insBAPay
        PURPOSE:         新增核付檔的資料(BAPay)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --序號
                        *v_i_paykind       (varChar2)       --給付種類
                        *v_i_inskd         (varChar2)       --保險別
                        *v_i_oriissuym     (varChar2)       --(原)核定年月
                        *v_i_issuym        (varChar2)       --核定年月
                        *v_i_payym         (varChar2)       --給付年月
                        *v_i_issutyp       (varChar2)       --核付分類
                        *v_i_casetyp       (varChar2)       --案件類別
                        *v_i_benevtrel     (varChar2)       --受益人與事故者關係
                        *v_i_benids        (varChar2)       --受益人社福識別碼
                        *v_i_benidnno      (varChar2)       --受益人身分證號
                        *v_i_benname       (varChar2)       --姓名
                        *v_i_paytyp        (varChar2)       --給付方式
                        *v_i_accseqno      (varChar2)       --被共同具領之受款人員序號
                        *v_i_accname       (varChar2)       --戶名
                        *v_i_accidn        (varChar2)       --戶名IDN
                        *v_i_paybankid     (varChar2)       --金融機構總代號
                        *v_i_branchid      (varChar2)       --分支代號
                        *v_i_payeeacc      (varChar2)       --銀行帳號
                        *v_i_aplpayamt     (varChar2)       --實付金額
                        *v_i_payrate       (varChar2)       --匯款匯費
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_dlinedate     (varChar2)       --勞貸本息截止日
                        *v_i_dlineseqno    (varChar2)       --流水號(勞貸)
                        *v_i_dlinemk       (varChar2)       --勞貸戶結清註記
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                                                            -2012/02/08 Add by ChungYu

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/06  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_insBAPay
    (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_paykind        in      varChar2,
        v_i_inskd          in      varChar2,
        v_i_oriissuym      in      varChar2,
        v_i_issuym         in      varChar2,
        v_i_payym          in      varChar2,
        v_i_issutyp        in      varChar2,
        v_i_casetyp        in      varChar2,
        v_i_benevtrel      in      varChar2,
        v_i_benids         in      varChar2,
        v_i_benidnno       in      varChar2,
        v_i_benname        in      varChar2,
        v_i_paytyp         in      varChar2,
        v_i_accseqno       in      varChar2,
        v_i_accname        in      varChar2,
        v_i_accidn         in      varChar2,
        v_i_paybankid      in      varChar2,
        v_i_branchid       in      varChar2,
        v_i_payeeacc       in      varChar2,
        v_i_aplpayamt      in      varChar2,
        v_i_payrate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_dlinedate      in      varChar2,
        v_i_dlineseqno     in      varChar2,
        v_i_dlinemk        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_nlwkmk         in      varChar2,
        v_i_adwkmk         in      varChar2,
        v_i_nachgmk        in      varChar2
    ) is
        begin
            v_g_errMsg := ' ';
            v_g_ProgName := 'PKG_BA_genPayFile.sp_BA_insBAPay';

            insert into BAPAY (APNO                                               --受理編號
                              ,SEQNO                                              --序號
                              ,PAYKIND                                            --給付種類
                              ,INSKD                                              --保險別
                              ,ORIISSUYM                                          --原核定年月
                              ,ISSUYM                                             --核定年月
                              ,PAYYM                                              --給付年月
                              ,PAYDATE                                            --核付日期
                              ,ISSUTYP                                            --核付分類
                              ,CASETYP                                            --案件類別
                              ,BENEVTREL                                          --受益人與事故者關係
                              ,BENIDS                                             --受益人社福識別碼
                              ,BENIDN                                             --受益人身分證號
                              ,BENNAME                                            --受益人姓名
                              ,PAYTYP                                             --給付方式
                              ,ACCSEQNO                                           --被共同具領之受款人員序號
                              ,ACCNAME                                            --戶名
                              ,ACCIDN                                             --戶名IDN
                              ,PAYBANKID                                          --金融機構總代號
                              ,BRANCHID                                           --分支代號
                              ,PAYEEACC                                           --銀行帳號
                              ,PAYAMT                                             --交易金額
                              ,MITRATE                                            --匯款匯費
                              ,DLINEDATE                                          --勞貸本息截止日
                              ,DLINESEQNO                                         --流水號(勞貸)
                              ,DLINEMK                                            --勞貸戶結清註記
                              ,PROCTIME                                           --處理日期時間
                              ,PAYSEQNO                                           --指定核付序號
                              ,NLWKMK                                             --普職註記
                              ,ADWKMK                                             --加職註記
                              ,NACHGMK                                            --普職互改註記
                             ) values (
                               v_i_apno
                              ,v_i_seqno
                              ,v_i_paykind
                              ,v_i_inskd
                              ,v_i_oriissuym
                              ,v_i_issuym
                              ,v_i_payym
                              ,v_i_paydate
                              ,v_i_issutyp
                              ,v_i_casetyp
                              ,v_i_benevtrel
                              ,v_i_benids
                              ,v_i_benidnno
                              ,v_i_benname
                              ,v_i_paytyp
                              ,v_i_accseqno
                              ,v_i_accname
                              ,v_i_accidn
                              ,v_i_paybankid
                              ,v_i_branchid
                              ,v_i_payeeacc
                              ,v_i_aplpayamt
                              ,v_i_payrate
                              ,v_i_dlinedate
                              ,v_i_dlineseqno
                              ,v_i_dlinemk
                              ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                              ,v_i_payseqno
                              ,v_i_nlwkmk
                              ,v_i_adwkmk
                              ,v_i_nachgmk
            );

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
        end;
    --procedure sp_BA_insBAPay End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayFile.sp_BA_genBAGivetmpDtl
        PURPOSE:         產生給付媒體明細錄資料(BAGivetmpDtl)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_proctime      (varChar2)       --處理日期時間
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                                                            -2012/02/08 Add by ChungYu
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua
        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/06  Angela Wu    Created this procedure.
        1.1   2023/01/03  William      依BABAWEB-59修改，
                                       共同具領案件的媒體檔應合併為1筆,
                                       當其中一筆為ISSUTYP=2,一筆為ISSUTYP=5, 則產出2筆媒體
        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genBAGivetmpDtl (
        v_i_issuym         in      varChar2,
        v_i_paycode        in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_proctime       in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_bajobid        in      varChar2,
        v_o_flag           out     Varchar2
    ) is
        v_rowCount         Number;

        --查詢待發媒體檔的給付方式
        --PayTyp=1、2:BL1(PayTyp=1、2會統一出一個BL1的媒體檔)
        --PayTyp=A   :BLA
        --PayTyp=3   :LAB(只有關係=F、N且PayTyp=3時,才要出LAB的檔)
        Cursor c_dataCur is
            select PARAMCODE from BAPARAM t
             where t.PARAMGROUP = 'PAYTYPE'
               and t.PARAMUSEMK = 'Y'
               and t.PARAMCODE in ('1','3','A')
             order by t.PARAMSEQ;

        --依傳入的給付方式查詢已核付待發的核付資料
        --因共同具領只要出一筆媒體資料,故要重新計算每一筆待發金額。共同具領的部份,要將核發金額總計,出一筆媒體資料即可
        Cursor c_PayDataCur(v_paytyp varChar2) is
            select t1.APNO                                                                   --受理編號
                  ,t1.SEQNO                                                                  --受款人序
                  ,t1.INSKD                                                                  --保險別
                  ,t1.PAYKIND                                                                --給付種類
                  ,t1.ISSUTYP                                                                --核定類別
                  ,t1.ISSUYM as PAYISSUYM                                                    --核定年月
                  ,t1.ORIISSUYM as ISSUYM                                                    --(原始)核定年月
                  ,t1.PAYYM                                                                  --給付年月
                  ,t1.BENIDN                                                                 --身分證號
                  ,t1.BENNAME                                                                --姓名
                  ,t1.PAYBANKID                                                              --總行代碼
                  ,deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID)) AS BRANCHID--分行代碼
                  ,t1.TATYP                                                                  --出帳類別
                  ,t1.PAYEEACC                                                               --銀行帳號
                  ,t1.DLINEDATE                                                              --勞貸本息截止日
                  ,t1.DLINESEQNO                                                             --流水號(勞貸)
                  ,t1.DLINEMK                                                                --勞貸戶結清註記
                  ,t2.PAYAMT                                                                 --交易金額
              from (select /*+index(tt1 BAPAYS03)*/
                           tt1.APNO
                          ,tt1.SEQNO
                          ,tt1.INSKD
                          ,tt1.PAYKIND
                          ,(deCode(tt1.PAYTYP,'1','BL1','2','BL1','3',(deCode(tt1.BENEVTREL,'Z',' ','LAB')),'A','BLA',' ')) as TATYP
                          ,tt1.PAYTYP
                          ,tt1.BENEVTREL
                          ,(deCode(tt1.BENEVTREL,'Z',(nvl(trim(tt1.BENIDN),tt1.ACCIDN)),(nvl(trim(tt1.ACCIDN),tt1.BENIDN)))) as BENIDN
                          ,(deCode(tt1.BENEVTREL,'Z',(nvl(trim(tt1.BENNAME),tt1.ACCNAME)),(nvl(trim(tt1.ACCNAME),tt1.BENNAME)))) as BENNAME
                          ,tt1.BRANCHID
                          ,tt1.PAYBANKID
                          ,tt1.PAYEEACC
                          ,tt1.ISSUYM
                          ,tt1.ORIISSUYM
                          ,tt1.PAYYM
                          ,tt1.ISSUTYP
                          ,tt1.DLINEDATE
                          ,tt1.DLINESEQNO
                          ,tt1.DLINEMK
                          ,tt1.PAYDATE
                          ,tt1.PAYSEQNO
                      from BAPAY tt1
                     where tt1.APNO like (v_i_paycode||'%')
                       and tt1.ISSUYM = v_i_issuym
                       and tt1.PAYDATE = v_i_paydate
                       and deCode(tt1.PAYTYP,'1','1','2','1','3',(deCode(tt1.BENEVTREL,'Z',' ','3')),tt1.PAYTYP) = v_paytyp
                       and tt1.payseqno = v_i_payseqno
                   ) t1,
                   (select /*+index(tt2 BAPAYS03)*/
                           tt2.APNO
                          ,tt2.ACCSEQNO
                          ,tt2.PAYKIND
                          ,tt2.PAYTYP
                          ,tt2.ISSUYM
                          ,tt2.PAYYM
                          ,Sum(tt2.PAYAMT) as PAYAMT
                          ,tt2.PAYSEQNO
                      from BAPAY tt2
                     where tt2.APNO like (v_i_paycode||'%')
                       and tt2.ISSUYM = v_i_issuym
                       and tt2.PAYDATE = v_i_paydate
                       and deCode(tt2.PAYTYP,'1','1','2','1','3',(deCode(tt2.BENEVTREL,'Z',' ','3')),tt2.PAYTYP) = v_paytyp
                       and tt2.payseqno = v_i_payseqno
                     group by tt2.APNO,tt2.ACCSEQNO,tt2.PAYKIND,tt2.PAYTYP,tt2.ISSUYM,tt2.PAYYM,tt2.PAYSEQNO
                   ) t2
               where t1.APNO = t2.APNO
                 and t1.APNO like (v_i_paycode||'%')
                 and (t1.APNO||t1.SEQNO||t1.PAYTYP) = (t2.APNO||t2.ACCSEQNO||t1.PAYTYP)
                 and t1.ISSUYM = t2.ISSUYM
                 and t1.PAYYM = t2.PAYYM
                 and t1.PAYDATE = v_i_paydate
                 and t1.PAYKIND = t2.PAYKIND
                 and t1.PAYTYP = t2.PAYTYP
                 --and t1.ISSUTYP = t2.ISSUTYP
                 and t2.PAYAMT > 0
                 and deCode(t1.PAYTYP,'1','1','2','1','3',(deCode(t1.BENEVTREL,'Z',' ','3')),t1.PAYTYP) = v_paytyp
                 and t1.payseqno = v_i_payseqno
               order by t1.APNO,t1.SEQNO,t1.ISSUYM,t1.PAYYM,t1.ISSUTYP;

        begin
            v_g_errMsg := ' ';
            v_g_ProgName := 'PKG_BA_genPayFile.sp_BA_genBAGivetmpDtl';
            v_g_flag := '0';
            for v_dataCur in c_dataCur Loop
                v_rowCount := 0;

                for v_PayDataCur in c_PayDataCur(v_dataCur.PARAMCODE) Loop
                    v_rowCount := v_rowCount + 1;

                    --將已核付待發的核付資料寫入BAGiveTmpDtl (for PayTyp=1、2、A)
                    if v_PayDataCur.TATYP = 'BL1' or v_PayDataCur.TATYP='BLA' then
                        insert into BAGIVETMPDTL (MFILENAME
                                                 ,MFILEDATE
                                                 ,SEQNO
                                                 ,RC2
                                                 ,SUNIT2
                                                 ,HBANK2
                                                 ,BBANK2
                                                 ,TATYP2
                                                 ,PAYDATE2
                                                 ,ACCNO2
                                                 ,AMT2
                                                 ,STAT2
                                                 ,APNO2
                                                 ,SEQ2
                                                 ,PAYTYP2
                                                 ,SPACE2
                                                 ,PAYYM2
                                                 ,IDN2
                                                 ,NAME2
                                                 ,INSKD2
                                                 ,EMGMK2
                                                 ,RPAYDATE2
                                                 ,ISSUYM2
                                                 ,NC2
                                                 ,ISSUTYP
                                                 ,PAYISSUYM
                                                 ,MFILEREMK
                                                 ,DLINEDATE
                                                 ,DLINESEQNO
                                                 ,DLINEMK
                                                 ,COMPARENAME
                                                 ,CHKDATE
                                                 ,UPDTIME
                                                 ,PAYSEQNO
                                                ) values (
                                                 (v_PayDataCur.TATYP||'005'||fn_BA_transDateValue(v_i_paydate,'1')||substr(v_i_proctime,1,12)||deCode(substr(v_PayDataCur.APNO,1,1),'L','1','K','2','S','3','0')||'.txt')
                                                 ,v_i_proctime
                                                 ,to_Char(v_rowCount)
                                                 ,'2'
                                                 ,RPAD('BLI',8,' ')
                                                 ,RPAD(nvl(trim(v_PayDataCur.PAYBANKID),' '),3,' ')
                                                 ,deCode(v_PayDataCur.TATYP,'BLA',(RPAD(nvl(trim(v_PayDataCur.BRANCHID),' '),5,' '))
                                                                                 --,(RPAD(nvl(trim(v_PayDataCur.BRANCHID),'0'),5,'0'))
                                                                                 ,(RPAD(nvl(trim(v_PayDataCur.BRANCHID),' '),5,' '))
                                                        )
                                                 ,v_PayDataCur.TATYP
                                                 ,fn_BA_transDateValue(v_i_paydate,'1')
                                                 ,deCode(v_PayDataCur.TATYP,'BLA',(LPAD(nvl(trim(v_PayDataCur.PAYEEACC),' '),14,' '))
                                                                                 ,(LPAD(nvl(trim(v_PayDataCur.PAYEEACC),'0'),14,'0'))
                                                        )
                                                 ,LPAD(nvl(trim(v_PayDataCur.PAYAMT),'0'),14,'0')
                                                 ,LPAD(' ',2,' ')
                                                 ,v_PayDataCur.APNO
                                                 ,v_PayDataCur.SEQNO
                                                 ,v_PayDataCur.PAYKIND
                                                 ,LPAD(' ',2,' ')
                                                 ,fn_BA_transDateValue(v_PayDataCur.PAYYM,'2')
                                                 ,RPAD(nvl(trim(v_PayDataCur.BENIDN),' '),10,' ')
                                                 ,v_PayDataCur.BENNAME
                                                 ,v_PayDataCur.INSKD
                                                 ,' '
                                                 ,LPAD(' ',7,' ')
                                                 ,fn_BA_transDateValue(v_PayDataCur.ISSUYM,'2')
                                                 ,LPAD(' ',4,' ')
                                                 ,v_PayDataCur.ISSUTYP
                                                 ,v_PayDataCur.PAYISSUYM
                                                 ,deCode(v_PayDataCur.TATYP,'BLA','3','0')
                                                 ,' '
                                                 ,' '
                                                 ,' '
                                                 ,convert(substr(RPAD(fn_BA_transCharValue(replace(v_PayDataCur.BENNAME,' ','　'),'1'),30,'　'),1,15)||v_PayDataCur.INSKD, 'ZHT16BIG5', 'UTF8')
                                                 ,v_i_chkdate
                                                 ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                 ,v_i_payseqno
                        );

                    --將已核付待發的核付資料寫入BAGiveTmpDtl (for PayTyp=3)
                    elsif v_PayDataCur.TATYP = 'LAB' then
                        insert into BAGIVETMPDTL (MFILENAME
                                                 ,MFILEDATE
                                                 ,SEQNO
                                                 ,RC2
                                                 ,SUNIT2
                                                 ,HBANK2
                                                 ,BBANK2
                                                 ,TATYP2
                                                 ,PAYDATE2
                                                 ,ACCNO2
                                                 ,AMT2
                                                 ,STAT2
                                                 ,APNO2
                                                 ,SEQ2
                                                 ,PAYTYP2
                                                 ,SPACE2
                                                 ,PAYYM2
                                                 ,IDN2
                                                 ,NAME2
                                                 ,EMGMK2
                                                 ,INSKD2
                                                 ,RPAYDATE2
                                                 ,ISSUYM2
                                                 ,NC2
                                                 ,ISSUTYP
                                                 ,PAYISSUYM
                                                 ,MFILEREMK
                                                 ,DLINEDATE
                                                 ,DLINESEQNO
                                                 ,DLINEMK
                                                 ,COMPARENAME
                                                 ,CHKDATE
                                                 ,UPDTIME
                                                 ,PAYSEQNO
                                               ) values (
                                                 (v_PayDataCur.TATYP||'OR'||'_'||v_i_paycode||'0'||fn_BA_transDateValue(v_i_paydate,'1')||'.txt')
                                                 ,v_i_proctime
                                                 ,to_Char(v_rowCount)
                                                 ,'2'
                                                 ,RPAD('LIB',3,' ')
                                                 ,' '
                                                 ,RPAD(nvl(trim(v_PayDataCur.BRANCHID),' '),3,' ')
                                                 ,v_PayDataCur.TATYP
                                                 ,fn_BA_transDateValue(v_i_paydate,'1')
                                                 ,LPAD(nvl(trim(v_PayDataCur.PAYEEACC),'0'),15,'0')
                                                 ,LPAD(nvl(trim(v_PayDataCur.PAYAMT),'0'),6,'0')
                                                 ,' '
                                                 ,v_PayDataCur.APNO
                                                 ,'0000'
                                                 ,v_PayDataCur.PAYKIND
                                                 ,' '
                                                 ,fn_BA_transDateValue(v_PayDataCur.PAYYM,'2')
                                                 ,RPAD(nvl(trim(v_PayDataCur.BENIDN),' '),10,' ')
                                                 ,v_PayDataCur.BENNAME
                                                 ,' '
                                                 ,v_PayDataCur.INSKD
                                                 ,' '
                                                 ,fn_BA_transDateValue(v_PayDataCur.ISSUYM,'2')
                                                 ,' '
                                                 ,v_PayDataCur.ISSUTYP
                                                 ,v_PayDataCur.PAYISSUYM
                                                 ,'3'
                                                 ,fn_BA_transDateValue(v_PayDataCur.DLINEDATE,'1')
                                                 ,v_PayDataCur.DLINESEQNO
                                                 ,v_PayDataCur.DLINEMK
                                                 ,convert(substr(RPAD(fn_BA_transCharValue(replace(v_PayDataCur.BENNAME,' ','　'),'1'),30,'　'),1,15)||v_PayDataCur.INSKD, 'ZHT16BIG5', 'UTF8')
                                                 ,v_i_chkdate
                                                 ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                 ,v_i_payseqno
                            );
                    end if;
                end Loop;
            end Loop;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
             '將已核付待發的核付資料寫入給付媒體明細錄檔(BAGiveTmpDtl)',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

             v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                     sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                     RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                      v_g_flag := '1';
                      v_o_flag := v_g_flag;
        end;
    --procedure sp_BA_genBAGivetmpDtl End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayFile.sp_BA_genBAGivePayFile
        PURPOSE:         產生核付媒體檔案

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_mtestmk       (varChar2)       --處理註記
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                                                            -2012/02/08 Add by ChungYu
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
         PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/06  Angela Wu    Created this procedure.
        2.0   2023/06/13  ttlin        額外產檔至MG.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genBAGivePayFile (
        v_i_issuym         in      varChar2,
        v_i_paycode        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_mtestmk        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_bajobid        in      varChar2,
        v_i_userid         in      varChar2,
        v_o_flag           out     Varchar2
    ) is
        v_filepath         varChar2(50) := 'EXCHANGE';         --轉出給付媒體檔的存放目錄
        v_PayNum           Number(8) := 0;
        v_PayAMT           Number(12) := 0;
        v_fileid           UTL_FILE.FILE_TYPE;
        --增加MG參數
        v_fileid_mg        MG.PKG_MG_FILE_UTIL.MG_FILE_TYPE;
        v_file_dir  VARCHAR2(100) := 'BA_FILES';
        v_msgCode   VARCHAR2(10000);
        v_msg       VARCHAR2(10000);

        --查詢及計算各媒體檔的首錄及尾錄金額
        Cursor c_dataCur is
            select t.SUNIT2                                    --發件單位
                  ,t.TATYP2                                    --出帳類別
                  ,t.PAYDATE2                                  --出帳日期
                  ,t.INSKD2                                    --保險別
                  ,t.EMGMK2                                    --緊急專案註記
                  ,t.PAYDATE2 as BLPAYDT                       --出納核付日期
                  ,t.MFILENAME                                 --檔案名稱
                  ,t.MFILEDATE                                 --檔案產生日期
                  ,Count(t.MFILENAME) as DATACOUNT             --轉帳總件數
                  ,Sum(t.AMT2) as AMT                          --轉帳總金額
              from BAGIVETMPDTL t
             where t.PAYISSUYM = v_i_issuym
               and t.PAYDATE2 = fn_BA_transDateValue(v_i_paydate,'1')
               and t.APNO2 like (v_i_paycode||'%')
               and t.PAYSEQNO = v_i_payseqno
             group by t.SUNIT2,t.TATYP2,t.INSKD2,t.EMGMK2,t.PAYDATE2,t.MFILENAME,t.MFILEDATE;

        --依傳入的出帳類別查詢該媒體檔待發的核付資料
        Cursor c_PayDataCur(v_tatyp2 varChar2) is
            select t.RC2                                     --區別碼
                  ,t.SUNIT2                                  --發件單位
                  ,t.HBANK2                                  --總行代碼
                  ,t.BBANK2                                  --分行代號
                  ,t.TATYP2                                  --出帳類別
                  ,t.PAYDATE2                                --出帳日期
                  ,t.ACCNO2                                  --入帳帳號
                  ,t.AMT2                                    --交易金額
                  ,t.STAT2                                   --狀況代號
                  ,t.APNO2                                   --受理編號
                  ,t.SEQ2                                    --受款人序
                  ,t.PAYTYP2                                 --給付種類
                  ,t.SPACE2                                  --空白
                  ,t.PAYYM2                                  --給付年月
                  ,t.IDN2                                    --身分證號
                  ,t.NAME2                                   --受款人姓名
                  ,t.INSKD2                                  --保險別
                  ,t.EMGMK2                                  --緊急專案註記
                  ,t.RPAYDATE2                               --實際核付日期
                  ,t.ISSUYM2                                 --核定年月
                  ,t.NC2                                     --備註代碼
                  ,t.DLINESEQNO                              --流水號
                  ,t.DLINEDATE                               --勞貸本息截止日
                  ,t.DLINEMK                                 --勞貸戶結清註記
                  ,t.ISSUTYP                                 --核付分類
                  ,t.PAYISSUYM                               --實際核付年月
              from BAGIVETMPDTL t
             where t.PAYISSUYM = v_i_issuym
               and t.PAYDATE2 = fn_BA_transDateValue(v_i_paydate,'1')
               and t.TATYP2 = v_tatyp2
               and t.APNO2 like (v_i_paycode||'%')
               AND T.PAYSEQNO = v_i_payseqno
             order by t.APNO2,to_number(t.SEQNO),t.ISSUYM2,t.PAYYM2,t.ISSUTYP;

        begin
            v_g_ProgName := 'PKG_BA_genPayFile.sp_BA_genBAGivePayFile';
            v_g_flag := '0';
            for v_dataCur in c_dataCur Loop
                v_PayNum := 0;
                v_PayAMT := 0;

                v_fileid := utl_file.fopen(v_filepath,v_dataCur.MFILENAME,'w');
                MG.PKG_MG_FILE_UTIL.FOPEN(v_file_dir, v_dataCur.MFILENAME, 'W', null, v_i_userid, v_fileid_mg, v_msgCode, v_msg);

                --產生給付媒體檔(BL1&BLA)
                if v_dataCur.TATYP2<>'LAB' then
                    --組合及產生首錄資料錄
                    utl_file.put_line(v_fileid
                                     ,'1'
                                    ||v_dataCur.SUNIT2
                                    ||RPAD('005',8,' ')
                                    ||v_dataCur.TATYP2
                                   ||v_dataCur.PAYDATE2
                                    ||'1'
                                    ||RPAD('139862',17,' ')
                                    ||LPAD(v_dataCur.AMT,14,'0')
                                    ||LPAD(v_dataCur.DATACOUNT,10,'0')
                                    ||v_dataCur.INSKD2
                                    ||v_dataCur.EMGMK2
                                    ||v_dataCur.BLPAYDT
                                    ||RPAD(' ',62,' '));
                    -- MG
                    MG.PKG_MG_FILE_UTIL.PUT_LINE(v_fileid_mg
                                     ,'1'
                                    ||v_dataCur.SUNIT2
                                    ||RPAD('005',8,' ')
                                    ||v_dataCur.TATYP2
                                    ||v_dataCur.PAYDATE2
                                    ||'1'
                                    ||RPAD('139862',17,' ')
                                    ||LPAD(v_dataCur.AMT,14,'0')
                                    ||LPAD(v_dataCur.DATACOUNT,10,'0')
                                    ||v_dataCur.INSKD2
                                    ||v_dataCur.EMGMK2
                                    ||v_dataCur.BLPAYDT
                                    ||RPAD(' ',62,' '));
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                     v_dataCur.TATYP2 || '組合及產生首錄資料錄',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                    for v_PayDataCur in c_PayDataCur(v_dataCur.TATYP2) Loop
                        v_PayNum := v_PayNum + 1;
                        v_PayAMT := v_PayAMT + to_Number(v_PayDataCur.AMT2);

                        --組合及產生明細錄資料錄
                        utl_file.put_line(v_fileid
                                         ,v_PayDataCur.RC2
                                        ||v_PayDataCur.SUNIT2
                                        ||v_PayDataCur.HBANK2
                                        ||v_PayDataCur.BBANK2
                                        ||v_PayDataCur.TATYP2
                                        ||v_PayDataCur.PAYDATE2
                                        ||v_PayDataCur.ACCNO2
                                        ||v_PayDataCur.AMT2
                                        ||v_PayDataCur.STAT2
                                        ||v_PayDataCur.APNO2
                                        ||v_PayDataCur.SEQ2
                                        ||v_PayDataCur.PAYTYP2
                                        ||v_PayDataCur.SPACE2
                                        ||v_PayDataCur.PAYYM2
                                        ||v_PayDataCur.IDN2
                                        ||substr(RPAD(fn_BA_transCharValue(replace(v_PayDataCur.NAME2,' ','　'),'1'),30,'　'),1,15)||v_PayDataCur.INSKD2
                                        ||v_PayDataCur.EMGMK2
                                        ||v_PayDataCur.RPAYDATE2
                                        ||v_PayDataCur.ISSUYM2
                                        ||v_PayDataCur.NC2);
                        -- MG
                        MG.PKG_MG_FILE_UTIL.PUT_LINE(v_fileid_mg
                                         ,v_PayDataCur.RC2
                                        ||v_PayDataCur.SUNIT2
                                        ||v_PayDataCur.HBANK2
                                        ||v_PayDataCur.BBANK2
                                        ||v_PayDataCur.TATYP2
                                        ||v_PayDataCur.PAYDATE2
                                        ||v_PayDataCur.ACCNO2
                                        ||v_PayDataCur.AMT2
                                        ||v_PayDataCur.STAT2
                                        ||v_PayDataCur.APNO2
                                        ||v_PayDataCur.SEQ2
                                        ||v_PayDataCur.PAYTYP2
                                        ||v_PayDataCur.SPACE2
                                        ||v_PayDataCur.PAYYM2
                                        ||v_PayDataCur.IDN2
                                        ||substr(RPAD(fn_BA_transCharValue(replace(v_PayDataCur.NAME2,' ','　'),'1'),30,'　'),1,15)||v_PayDataCur.INSKD2
                                        ||v_PayDataCur.EMGMK2
                                        ||v_PayDataCur.RPAYDATE2
                                        ||v_PayDataCur.ISSUYM2
                                        ||v_PayDataCur.NC2);

                        if v_PayDataCur.ISSUTYP = '1' or v_PayDataCur.ISSUTYP = '2' or v_PayDataCur.ISSUTYP = '4' or v_PayDataCur.ISSUTYP = '5' then
                            --更新給付核定檔的核付相關欄位及媒體轉出相關欄位(for 核付資料,不含改匯資料)
                            update BADAPR set GENMEDDATE = to_Char(Sysdate,'YYYYMMDD')
                                             ,PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                             where APNO = v_PayDataCur.APNO2
                               and SEQNO in (select SEQNO
                                               from BAPAY
                                              where APNO = v_PayDataCur.APNO2
                                                and ACCSEQNO = v_PayDataCur.SEQ2
                                                and ISSUYM = fn_BA_transDateValue(v_PayDataCur.ISSUYM2,'4')
                                                and PAYYM = fn_BA_transDateValue(v_PayDataCur.PAYYM2,'4')
                                                and PAYDATE = fn_BA_transDateValue(v_PayDataCur.PAYDATE2,'3'))
                               and ISSUYM = fn_BA_transDateValue(v_PayDataCur.ISSUYM2,'4')
                               and PAYYM = fn_BA_transDateValue(v_PayDataCur.PAYYM2,'4')
                               and MANCHKMK = 'Y'
                               -- 20120212 mark by chungyu
                               --and (ACCEPTMK = 'Y' or nvl(trim(ACCEPTMK),' ') = ' ')
                               and (CHKDATE is not null and nvl(trim(CHKDATE),' ')<>' ')
                               and MTESTMK = v_i_mtestmk
                               and (APLPAYMK is null or nvl(trim(APLPAYMK),' ')=' ')
                               and (APLPAYDATE is null or nvl(trim(APLPAYDATE),' ')=' ');
                        end if;
                        if v_PayDataCur.ISSUTYP = 'A' then
                            --更新改匯資料檔的媒體轉出相關欄位(for 改匯資料,不含核付資料)
                            update BAREGIVEDTL set GENMEDDATE = to_Char(Sysdate,'YYYYMMDD')
                                                  ,ISSUYM = v_PayDataCur.PAYISSUYM
                             where APNO = v_PayDataCur.APNO2
                               and SEQNO = v_PayDataCur.SEQ2
                               and MK = '2'
                               and nvl(trim(WORKMK),' ') <> 'Y'
                               and ORIISSUYM = fn_BA_transDateValue(v_PayDataCur.ISSUYM2,'4')
                               and PAYYM = fn_BA_transDateValue(v_PayDataCur.PAYYM2,'4')
                               and (trim(AFCHKDATE) is not null and nvl(trim(AFCHKDATE),' ')<>' ')
                               and (trim(AFPAYDATE) is null or nvl(trim(AFPAYDATE),' ')=' ');
                        end if;
                    end Loop;

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                    v_dataCur.TATYP2 || '組合及產生明細錄資料錄完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                    --組合及產生尾錄資料錄
                    utl_file.put_line(v_fileid
                                     ,'3'
                                    ||v_dataCur.SUNIT2
                                    ||RPAD('005',8,' ')
                                    ||v_dataCur.TATYP2
                                    ||v_dataCur.PAYDATE2
                                    ||'1'
                                    ||RPAD('139862',17,' ')
                                    ||v_dataCur.PAYDATE2
                                    ||LPAD(v_dataCur.AMT,14,'0')
                                    ||LPAD(v_dataCur.DATACOUNT,10,'0')
                                    ||LPAD('0',14,'0')
                                    ||LPAD('0',10,'0')
                                    ||v_dataCur.EMGMK2
                                    ||v_dataCur.BLPAYDT
                                    ||LPAD(' ',32,' '));
                    -- MG
                    MG.PKG_MG_FILE_UTIL.PUT_LINE(v_fileid_mg
                                     ,'3'
                                    ||v_dataCur.SUNIT2
                                    ||RPAD('005',8,' ')
                                    ||v_dataCur.TATYP2
                                    ||v_dataCur.PAYDATE2
                                    ||'1'
                                    ||RPAD('139862',17,' ')
                                    ||v_dataCur.PAYDATE2
                                    ||LPAD(v_dataCur.AMT,14,'0')
                                    ||LPAD(v_dataCur.DATACOUNT,10,'0')
                                    ||LPAD('0',14,'0')
                                    ||LPAD('0',10,'0')
                                    ||v_dataCur.EMGMK2
                                    ||v_dataCur.BLPAYDT
                                    ||LPAD(' ',32,' '));

                     utl_file.fclose(v_fileid);
                     MG.PKG_MG_FILE_UTIL.FCLOSE(v_fileid_mg);

                     --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                    v_dataCur.TATYP2 || '組合及產生尾錄資料錄完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                --產生紓困貸款媒體檔(LAB)
                else
                     --組合及產生首錄資料錄
                     utl_file.put_line(v_fileid
                                      ,'1'
                                     ||RPAD(v_dataCur.SUNIT2,3,' ')
                                     ||'005'
                                     ||'245-03'
                                     ||LPAD(nvl(trim(v_dataCur.PAYDATE2),'0'),8,'0')
                                     ||RPAD(' ',55,' '));
                      -- MG
                     MG.PKG_MG_FILE_UTIL.PUT_LINE(v_fileid_mg
                                      ,'1'
                                     ||RPAD(v_dataCur.SUNIT2,3,' ')
                                     ||'005'
                                     ||'245-03'
                                     ||LPAD(nvl(trim(v_dataCur.PAYDATE2),'0'),8,'0')
                                     ||RPAD(' ',55,' '));

                     --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                    v_dataCur.TATYP2 || '組合及產生首錄資料錄完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                     for v_PayDataCur in c_PaydataCur(v_dataCur.TATYP2) Loop
                         v_PayNum := v_PayNum + 1;
                         v_PayAMT := v_PayAMT + to_Number(v_PayDataCur.AMT2);

                         --組合及產生明細錄資料錄
                         utl_file.put_line(v_fileid
                                          ,v_PayDataCur.RC2
                                         ||v_PayDataCur.ACCNO2
                                         ||v_PayDataCur.BBANK2
                                         ||v_PayDataCur.DLINESEQNO
                                         ||v_PayDataCur.IDN2
                                         ||LPAD(nvl(trim(v_PayDataCur.DLINEDATE),'0'),8,'0')
                                         ||LPAD(v_PayDataCur.AMT2,6,'0')
                                         ||v_PayDataCur.APNO2
                                         ||LPAD(v_PayDataCur.ISSUYM2,6,'0')
                                         ||LPAD(v_PayDataCur.PAYYM2,6,'0')
                                         ||v_PayDataCur.DLINEMK
                                         );
                         -- MG
                         MG.PKG_MG_FILE_UTIL.PUT_LINE(v_fileid_mg
                                          ,v_PayDataCur.RC2
                                         ||v_PayDataCur.ACCNO2
                                         ||v_PayDataCur.BBANK2
                                         ||v_PayDataCur.DLINESEQNO
                                         ||v_PayDataCur.IDN2
                                         ||LPAD(nvl(trim(v_PayDataCur.DLINEDATE),'0'),8,'0')
                                         ||LPAD(v_PayDataCur.AMT2,6,'0')
                                         ||v_PayDataCur.APNO2
                                         ||LPAD(v_PayDataCur.ISSUYM2,6,'0')
                                         ||LPAD(v_PayDataCur.PAYYM2,6,'0')
                                         ||v_PayDataCur.DLINEMK
                                         );

                         if v_PayDataCur.ISSUTYP = '1' or v_PayDataCur.ISSUTYP = '2' or v_PayDataCur.ISSUTYP = '4' or v_PayDataCur.ISSUTYP = '5' then
                             --更新給付核定檔的核付相關欄位及媒體轉出相關欄位(for 核付資料,不含改匯資料)
                             update BADAPR set GENMEDDATE = to_Char(Sysdate,'YYYYMMDD')
                                              ,PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                              where APNO = v_PayDataCur.APNO2
                                and (BENEVTREL = 'F' or BENEVTREL = 'N')
                                and ISSUYM = fn_BA_transDateValue(v_PayDataCur.ISSUYM2,'4')
                                and PAYYM = fn_BA_transDateValue(v_PayDataCur.PAYYM2,'4')
                                and MANCHKMK = 'Y'
                                --20120212 mark By chungyu
                                --and (ACCEPTMK = 'Y' or nvl(trim(ACCEPTMK),' ') = ' ')
                                and (CHKDATE is not null and nvl(trim(CHKDATE),' ')<>' ')
                                and MTESTMK = v_i_mtestmk
                                and (APLPAYMK is null or nvl(trim(APLPAYMK),' ')=' ')
                                and (APLPAYDATE is null or nvl(trim(APLPAYDATE),' ')=' ');
                         end if;
                     end Loop;

                     --修改log作法 by TseHua 20180419
                      sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                       v_dataCur.TATYP2 || '組合及產生明細錄資料錄完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                     --組合及產生尾錄資料錄
                     utl_file.put_line(v_fileid
                                      ,'3'
                                     ||LPAD(v_dataCur.DATACOUNT,15,'0')
                                     ||RPAD(' ',27,' ')
                                     ||LPAD(v_dataCur.AMT,14,'0')
                                     ||LPAD(' ',19,' '));
                     -- MG
                     MG.PKG_MG_FILE_UTIL.PUT_LINE(v_fileid_mg
                                      ,'3'
                                     ||LPAD(v_dataCur.DATACOUNT,15,'0')
                                     ||RPAD(' ',27,' ')
                                     ||LPAD(v_dataCur.AMT,14,'0')
                                     ||LPAD(' ',19,' '));

                      utl_file.fclose(v_fileid);
                      MG.PKG_MG_FILE_UTIL.FCLOSE(v_fileid_mg);

                     --修改log作法 by TseHua 20180419
                      sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                      v_dataCur.TATYP2 || '組合及產生尾錄資料錄完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                end if;
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>'||RPAD(v_dataCur.MFILENAME,32,' ')||'    -PayNum:'||RPAD(v_PayNum,8,' ')||'    -PayAMT:'||v_PayAMT);

            end Loop;

             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '產生核付媒體檔案完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

            v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                     v_g_flag := '1';
                     v_o_flag := v_g_flag;

        end;
    --procedure sp_BA_genBAGivePayFile End


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayFile.sp_BA_genBAPay_S
        PURPOSE:         產生已核付待發的資料(BAPay)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_mtestmk       (varChar2)       --處理註記
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                                                            -2012/02/08 Add by ChungYu
                        *v_i_bajobid       (varChar2)       --批次線上id add by Zehua
                        *v_i_proctype      (varChar2)       --處理類型(5:批次出媒體  1:線上出媒體)add by Zehua


       PARAMETER(OUT):
                        v_o_flag          (varChar2)       --傳出值 0-成功；1-失敗

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/06  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genBAPay_S (
        v_i_issuym       in      varChar2,
        v_i_paycode      in      varChar2,
        v_i_chkdate      in      varChar2,
        v_i_paydate      in      varChar2,
        v_i_mtestmk      in      varChar2,
        v_i_payseqno     in      varChar2,
        v_i_bajobid      in      varchar2,
        v_i_proctype     in      varChar2,
        v_o_flag         out     varChar2
    ) is
        --查詢已核付待發的核付資料(不分給付方式,全抓)
        Cursor c_dataCur_PayData is
            select t1.APNO                                                 --受理編號
                  ,t1.SEQNO                                                --序號
                  ,t1.PAYKIND                                              --給付種類
                  ,t1.INSKD                                                --保險別
                  ,t1.BENEVTREL                                            --受益人與事故者關係
                  ,t1.BENIDS                                               --受益人社福識別碼
                  ,t1.BENIDN                                               --受益人身分證號
                  ,t1.BENNAME                                              --姓名
                  ,t1.SOURCEPAYTYP as PAYTYP                               --給付方式
                  ,t1.ACCIDN                                               --戶名IDN
                  ,t1.ACCNAME                                              --戶名
                  ,t1.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t1.CASETYP                                              --案件類別
                  ,t1.ISSUTYP                                              --核付分類
                  ,t1.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  ,t1.ISSUYM                                               --核定年月
                  ,t1.PAYYM                                                --給付年月
                  ,t1.APLPAYAMT                                            --實付金額
                  ,t1.PAYRATE                                              --匯款匯費
                  ,t1.PAYBANKID                                            --金融機構總代號
                  ,deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID)) AS BRANCHID--分支代號
                  ,t1.PAYEEACC                                             --銀行帳號
                  ,' ' as DLINEDATE                                        --勞貸本息截止日
                  ,' ' as DLINESEQNO                                       --流水號(勞貸)
                  ,' ' as DLINEMK                                          --勞貸戶結清註記
                  ,t1.NLWKMK                                               --普職註記
                  ,t1.ADWKMK                                               --加職註記
                  ,t1.NACHGMK                                              --普職互改註記
              from BAISSUDATATEMP t1
             where t1.APNO like (v_i_paycode||'%')
               and t1.SEQNO <> '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and t1.PAYSEQNO = v_i_payseqno
               and t1.MANCHKMK = 'Y';

        --查詢已核付待發的勞貸資料(不分給付方式,全抓
    --20130223 Modify by Angela：增加 Union。因勞貸資料結清後會搬檔至紓困結案檔，可能會導致出媒體檔時無資料，所以需要 Union 紓困結案檔。
        Cursor c_dataCur_FPayData is
            select t1.APNO                                                 --受理編號
                  ,t1.SEQNO                                                --序號
                  ,t1.PAYKIND                                              --給付種類
                  ,t1.INSKD                                                --保險別
                  ,t1.BENEVTREL                                            --受益人與事故者關係
                  ,t1.BENIDS                                               --受益人社福識別碼
                  ,t1.BENIDN                                               --受益人身分證號
                  ,t1.BENNAME                                              --姓名
                  ,'3' as PAYTYP                                           --給付方式
                  ,t1.ACCIDN                                               --戶名IDN
                  ,t1.ACCNAME                                              --戶名
                  ,t1.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t1.CASETYP                                              --案件類別
                  ,t1.ISSUTYP                                              --核付分類
                  ,t1.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  ,t1.ISSUYM                                               --核定年月
                  ,t1.PAYYM                                                --給付年月
                  ,t1.OFFSETAMT as APLPAYAMT                               --抵銷金額
                  ,t1.PAYRATE                                              --匯款匯費
                  ,t2.PAYBANKID                                            --金融機構總代號
                  ,deCode(t2.PAYBANKID,'700',t2.BRANCHID,deCode('3','1','0000',t2.BRANCHID)) AS BRANCHID --分支代號
                  ,t2.PAYEEACC                                             --銀行帳號
                  ,t1.DLINEDATE                                            --勞貸本息截止日
                  ,t2.DLINESEQNO                                           --流水號(勞貸)
                  ,t1.DLINEMK                                              --勞貸戶結清註記
                  ,t1.NLWKMK                                               --普職註記
                  ,t1.ADWKMK                                               --加職註記
                  ,t1.NACHGMK                                              --普職互改註記
              from BAISSUDATATEMP t1
                  ,(select tt2.IDN_APLY
                          ,tt2.IDN_LIB
                          ,substr(tt2.BRNO,1,3) as BRANCHID
                          ,((deCode(length(tt2.SEQNO),6,(deCode(substr(tt2.SEQNO,1,1),'0','092'
                                                                                     ,'3','093'
                                                                                     ,'5','095'
                                                                                     ,'6','096'
                                                                                     ,'7','097'
                                                                                     ,'8','098'
                                                                                     ,'9','099'
                                                                                     ,substr(tt2.SEQNO,1,1))
                                                        ),substr(tt2.SEQNO,1,1))
                            )||substr(tt2.SEQNO,2,8)) as DLINESEQNO
                          ,tt2.ACCOUNT_PAY as PAYEEACC
                          ,' ' as PAYBANKID
                      from MBLNM tt2
                     where nvl(trim(tt2.EMRK),' ') <> '1'
                   ) t2,
                   (Select Distinct tt31.Apno, tt31.EVTIDNNO From Baappbase tt31
                     Where tt31.Apno in (Select tt32.Apno From BAISSUDATATEMP tt32
                                          Where tt32.APNO like (v_i_paycode||'%')
                                            and tt32.SEQNO = '0000'
                                            and tt32.ISSUYM = v_i_issuym
                                            and tt32.CHKDATE = v_i_chkdate
                                            and tt32.MANCHKMK = 'Y'
                                            and tt32.OFFSETAMT > 0
                                        )
                   ) t3                                                      -- 2012/06/19 Add By ChungYu 遺屬勞貸必須串回主檔取得事故者IDN
             where t1.APNO like (v_i_paycode||'%')
               and t3.APNO like (v_i_paycode||'%')
               and t1.APNO = t3.APNO
               and (t3.EVTIDNNO = t2.IDN_APLY or t3.EVTIDNNO = t2.IDN_LIB)
               and t1.SEQNO = '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and t1.MANCHKMK = 'Y'
               and t1.OFFSETAMT > 0
            /* union
            select t1.APNO                                                 --受理編號
                  ,t1.SEQNO                                                --序號
                  ,t1.PAYKIND                                              --給付種類
                  ,t1.INSKD                                                --保險別
                  ,t1.BENEVTREL                                            --受益人與事故者關係
                  ,t1.BENIDS                                               --受益人社福識別碼
                  ,t1.BENIDN                                               --受益人身分證號
                  ,t1.BENNAME                                              --姓名
                  ,'3' as PAYTYP                                           --給付方式
                  ,t1.ACCIDN                                               --戶名IDN
                  ,t1.ACCNAME                                              --戶名
                  ,t1.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t1.CASETYP                                              --案件類別
                  ,t1.ISSUTYP                                              --核付分類
                  ,t1.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  ,t1.ISSUYM                                               --核定年月
                  ,t1.PAYYM                                                --給付年月
                  ,t1.OFFSETAMT as APLPAYAMT                               --抵銷金額
                  ,t1.PAYRATE                                              --匯款匯費
                  ,t2.PAYBANKID                                            --金融機構總代號
                  ,deCode(t2.PAYBANKID,'700',t2.BRANCHID,deCode('3','1','0000',t2.BRANCHID)) AS BRANCHID --分支代號
                  ,t2.PAYEEACC                                             --銀行帳號
                  ,t1.DLINEDATE                                            --勞貸本息截止日
                  ,t2.DLINESEQNO                                           --流水號(勞貸)
                  ,'1' as DLINEMK                                          --勞貸戶結清註記(已結清)
                  ,t1.NLWKMK                                               --普職註記
                  ,t1.ADWKMK                                               --加職註記
                  ,t1.NACHGMK                                              --普職互改註記
              from BAISSUDATATEMP t1
                  ,(select tt2.IDN_APLY
                          ,tt2.IDN_LIB
                          ,substr(tt2.BRNO,1,3) as BRANCHID
                          ,((deCode(length(tt2.SEQNO),6,(deCode(substr(tt2.SEQNO,1,1),'0','092'
                                                                                     ,'3','093'
                                                                                     ,'5','095'
                                                                                     ,'6','096'
                                                                                     ,'7','097'
                                                                                     ,'8','098'
                                                                                     ,'9','099'
                                                                                     ,substr(tt2.SEQNO,1,1))
                                                        ),substr(tt2.SEQNO,1,1))
                            )||substr(tt2.SEQNO,2,8)) as DLINESEQNO
                          ,tt2.ACCOUNT_PAY as PAYEEACC
                          ,' ' as PAYBANKID
                      from MBLNC tt2
                     where nvl(trim(tt2.EMRK),' ') = '1'
                       and tt2.EDATE like (to_Char(Sysdate,'YYYY')||'%')
                   ) t2,
                   (select distinct tt31.APNO, tt31.EVTIDNNO from BAAPPBASE tt31
                     where tt31.APNO in (select tt32.APNO from BAISSUDATATEMP tt32
                                          where tt32.APNO like (v_i_paycode||'%')
                                            and tt32.SEQNO = '0000'
                                            and tt32.ISSUYM = v_i_issuym
                                            and tt32.CHKDATE = v_i_chkdate
                                            and tt32.MANCHKMK = 'Y'
                                            and tt32.OFFSETAMT > 0
                                        )
                   ) t3
             where t1.APNO like (v_i_paycode||'%')
               and t3.APNO like (v_i_paycode||'%')
               and t1.APNO = t3.APNO
               and (t3.EVTIDNNO = t2.IDN_APLY or t3.EVTIDNNO = t2.IDN_LIB)
               and t1.SEQNO = '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and t1.MANCHKMK = 'Y'
               and t1.OFFSETAMT > 0*/ ;

        --查詢已核付待發的補償金資料(不分給付方式,全抓)
        /**
        Cursor c_dataCur_ZPayData is
            select distinct
                   t2.APNO                                                 --受理編號
                  ,t2.SEQNO                                                --序號
                  ,t2.PAYKIND                                              --給付種類
                  ,t2.INSKD                                                --保險別
                  ,t2.BENEVTREL                                            --受益人與事故者關係
                  ,t2.BENIDS                                               --受益人社福識別碼
                  ,t2.BENIDN                                               --受益人身分證號
                  ,t2.BENNAME                                              --姓名
                  ,t2.PAYTYP                                               --給付方式
                  ,t2.ACCIDN                                               --戶名IDN
                  ,t2.ACCNAME                                              --戶名
                  ,t2.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t2.CASETYP                                              --案件類別
                  ,t2.ISSUTYP                                              --核付分類
                  ,t2.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  ,t2.ISSUYM                                               --核定年月
                  ,t2.PAYYM                                                --給付年月
                  ,t1.COMPENAMT as APLPAYAMT                               --抵銷金額
                  ,t2.PAYRATE                                              --匯款匯費
                  ,t2.PAYBANKID                                            --金融機構總代號
                  ,deCode(t2.PAYBANKID,'700',t2.BRANCHID,deCode(t2.PAYTYP,'1','0000',t2.BRANCHID)) AS BRANCHID --分支代號
                  ,t2.PAYEEACC                                             --銀行帳號
                  ,' ' as DLINESEQNO                                       --流水號(勞貸)
                  ,' ' as DLINEDATE                                        --勞貸本息截止日
                  ,' ' as DLINEMK                                          --勞貸戶結清註記
              from BAISSUDATATEMP t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.INSKD
                          ,t21.PAYKIND
                          ,t21.ISSUYM as ORIISSUYM
                          ,t21.ISSUYM
                          ,t21.PAYYM
                          ,t21.CASETYP
                          ,t21.ISSUTYP
                          ,t21.BENEVTREL
                          ,t21.BENIDS
                          ,(nvl(trim(t22.VATNUMBER),t21.BENIDN)) as BENIDN
                          ,t21.BENNAME
                          ,t21.PAYTYP as PAYTYP
                          ,t21.BENIDN as ACCIDN
                          ,t22.GVNAME as ACCNAME
                          ,t21.ACCSEQNO
                          ,t21.PAYRATE
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                          ,t21.BENIDN as COMPID1
                          ,replace(trim(t22.GVNAME),'　','') as COMPNAME1
                      from BAISSUDATATEMP t21,BCCMF08 t22
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.BENEVTREL = 'Z'
                       and t21.BENIDN = t22.GVCD1
                       and t21.ISSUYM = v_i_issuym
                       and t21.CHKDATE = v_i_chkdate
                   ) t2,PBBMSA t3,CAUB t4
             where t1.APNO = t2.APNO
               and t1.ISSUYM = t2.ISSUYM
               and t1.PAYYM = t2.PAYYM
               and t1.BENIDN = t3.BMEVIDNO
               and t1.APNO like (v_i_paycode||'%')
               and t3.BMPAYKND = '4'
               and t1.SEQNO = '0000'
               and t1.BENEVTREL = '1'
               and t2.SEQNO <> '0000'
               and t2.BENEVTREL = 'Z'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               --修改讀取補償金單位的條件 Modify by Angela 20120725
               --and ((substr(t3.BMAPNO,5,3)='460')
               --  or (substr(t3.BMAPNO,5,3)>='461'
               --  and substr(t3.BMAPNO,5,3)<='469'
               --  and nvl(trim(t3.BMEXMDTE),' ')<>' '))
               and ((substr(t3.BMAPNO,5,3)='469')
                 or (substr(t3.BMAPNO,5,3)>='460'
                 and substr(t3.BMAPNO,5,3)<='468'
                 and nvl(trim(t3.BMEXMDTE),' ')<>' '
                 and t3.BMEXMDTE is not null))
               and t4.UBNO = t3.BMOLDAPLDPT
               and nvl(trim(t3.BMOLDAPLDPT),' ')<>' '
               and t1.COMPENAMT > 0;
               **/

        --查詢已核付待發不含補償金單位的改匯資料(不分給付方式,全抓)
        Cursor c_dataCur_LBAC is
            select t1.APNO                                                 --受理編號
                  ,t1.SEQNO                                                --序號
                  ,t3.PAYKIND                                              --給付種類
                  ,t3.INSKD                                                --保險別
                  ,t3.BENEVTREL                                            --受益人與事故者關係
                  ,t3.BENIDS                                               --受益人社福識別碼
                  ,t3.BENIDN                                               --受益人身分證號
                  ,t3.BENNAME                                              --姓名
                  ,t1.PAYTYP                                               --給付方式
                  ,t1.ACCIDN                                               --戶名IDN
                  ,t1.ACCNAME                                              --戶名
                  ,t1.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t2.CASETYP                                              --案件類別
                  ,t1.ISSUTYP                                              --核付分類
                  ,t2.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  --,t1.ISSUYM                                             --核定年月
                  ,v_i_issuym as ISSUYM                                    --核定年月
                  ,t1.PAYYM                                                --給付年月
                  ,t1.APLPAYAMT                                            --抵銷金額
                  ,t2.PAYRATE                                              --匯款匯費
                  ,t1.PAYBANKID                                            --金融機構總代號
                  ,deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID)) AS BRANCHID--分支代號
                  ,t1.PAYEEACC                                             --銀行帳號
                  ,' ' as DLINESEQNO                                       --流水號(勞貸)
                  ,' ' as DLINEDATE                                        --勞貸本息截止日
                  ,' ' as DLINEMK                                          --勞貸戶結清註記
                  ,''  as NLWKMK                                           --普職註記
                  ,''  as ADWKMK                                           --加職註記
                  ,''  as NACHGMK                                          --普職互改註記
              from (select tt1.APNO
                          ,tt1.SEQNO
                          ,tt1.ISSUYM
                          ,tt1.ORIISSUYM
                          ,tt1.PAYYM
                          ,tt1.PAYTYP
                          ,tt1.PAYBANKID
                          ,tt1.BRANCHID
                          ,tt1.PAYEEACC
                          ,tt1.ACCIDN
                          ,tt1.ACCNAME
                          ,nvl(trim(tt1.ACCSEQNO),tt1.SEQNO) ACCSEQNO
                          ,tt1.REMITAMT as APLPAYAMT
                          ,'A' as ISSUTYP
                      from BAREGIVEDTL tt1
                     where tt1.APNO like (v_i_paycode||'%')
                       --and tt1.ISSUYM = v_i_issuym
                       and tt1.MK = '2'
                       and nvl(trim(tt1.WORKMK),' ') <> 'Y'
                       and (tt1.AFCHKDATE is not null and nvl(trim(tt1.AFCHKDATE),' ')<>' ')
                       and (tt1.AFPAYDATE is null or nvl(trim(tt1.AFPAYDATE),' ')=' ')
                   ) t1
                  ,(select tt2.APNO
                          ,tt2.SEQNO
                          ,tt2.ISSUYM
                          ,tt2.PAYYM
                          ,tt2.MCHKTYP as CASETYP
                          ,0 as PAYRATE
                          ,' ' as DLINEDATE
                          ,' ' as DLINESEQNO
                      from BADAPR tt2
                     where tt2.APNO like (v_i_paycode||'%')
                       and tt2.MTESTMK = v_i_mtestmk
                       and tt2.MANCHKMK = 'Y'
                       and tt2.BENEVTREL <> 'Z'
                       and tt2.REMITMK in ('2','3')
                       --and (tt2.ACCEPTMK = 'Y' or nvl(trim(tt2.ACCEPTMK),' ') = ' ')    -Mark by Angela 20190927
                       and (tt2.CHKDATE is not null and nvl(trim(tt2.CHKDATE),' ')<>' ')
                       and (tt2.REMITDATE is not null and nvl(trim(tt2.REMITDATE),' ')<>' ')
                   ) t2
                  ,(select tt3.APNO
                          ,tt3.SEQNO
                          ,tt3.IMK as INSKD
                          ,tt3.PAYKIND
                          ,tt3.BENIDS
                          ,tt3.BENIDNNO as BENIDN
                          ,tt3.BENNAME
                          ,tt3.BENEVTREL
                      from BAAPPBASE tt3
                     where tt3.APNO like (v_i_paycode||'%')
                       and tt3.BENEVTREL<>'Z'
                   ) t3
             where t1.APNO = t2.APNO
               and t1.APNO = t3.APNO
               and t2.APNO = t3.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.SEQNO = t3.SEQNO
               and t2.SEQNO = t3.SEQNO
               and t1.ORIISSUYM = t2.ISSUYM
               and t1.PAYYM = t2.PAYYM
               and t1.APNO like (v_i_paycode||'%');

        --查詢已核付待發只有補償金單位的改匯資料(不分給付方式,全抓)
        /**
        Cursor c_dataCur_LBAC_Z is
            select t1.APNO                                                 --受理編號
                  ,t1.SEQNO                                                --序號
                  ,t3.PAYKIND                                              --給付種類
                  ,t3.INSKD                                                --保險別
                  ,t3.BENEVTREL                                            --受益人與事故者關係
                  ,t3.BENIDS                                               --受益人社福識別碼
                  ,t3.BENIDN                                               --受益人身分證號
                  ,t3.BENNAME                                              --姓名
                  ,t1.PAYTYP                                               --給付方式
                  ,t3.ACCIDN                                               --戶名IDN
                  ,t3.ACCNAME                                              --戶名
                  ,t1.ACCSEQNO                                             --被共同具領之受款人員序號
                  ,t2.CASETYP                                              --案件類別
                  ,t1.ISSUTYP                                              --核付分類
                  ,t2.ISSUYM as ORIISSUYM                                  --(原)核定年月
                  --,t1.ISSUYM                                             --核定年月
                  ,v_i_issuym as ISSUYM                                    --核定年月
                  ,t1.PAYYM                                                --給付年月
                  ,t1.APLPAYAMT                                            --抵銷金額
                  ,t2.PAYRATE                                              --匯款匯費
                  ,t1.PAYBANKID                                            --金融機構總代號
                  ,deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID)) AS BRANCHID--分支代號
                  ,t1.PAYEEACC                                             --銀行帳號
                  ,' ' as DLINESEQNO                                       --流水號(勞貸)
                  ,' ' as DLINEDATE                                        --勞貸本息截止日
                  ,' ' as DLINEMK                                          --勞貸戶結清註記
              from (select tt1.APNO
                          ,tt1.SEQNO
                          ,tt1.ISSUYM
                          ,tt1.ORIISSUYM
                          ,tt1.PAYYM
                          ,tt1.PAYTYP
                          ,tt1.PAYBANKID
                          ,tt1.BRANCHID
                          ,tt1.PAYEEACC
                          ,nvl(trim(tt1.ACCSEQNO),tt1.SEQNO) ACCSEQNO
                          ,tt1.REMITAMT as APLPAYAMT
                          ,'A' as ISSUTYP
                      from BAREGIVEDTL tt1
                     where tt1.APNO like (v_i_paycode||'%')
                       --and tt1.ISSUYM = v_i_issuym
                       and tt1.MK = '2'
                       and nvl(trim(tt1.WORKMK),' ') <> 'Y'
                       and (tt1.AFCHKDATE is not null and nvl(trim(tt1.AFCHKDATE),' ')<>' ')
                       and (tt1.AFPAYDATE is null or nvl(trim(tt1.AFPAYDATE),' ')=' ')
                   ) t1
                  ,(select tt2.APNO
                          ,tt2.SEQNO
                          ,tt2.ISSUYM
                          ,tt2.PAYYM
                          ,tt2.MCHKTYP as CASETYP
                          ,0 as PAYRATE
                          ,' ' as DLINEDATE
                          ,' ' as DLINESEQNO
                      from BADAPR tt2
                     where tt2.APNO like (v_i_paycode||'%')
                       and tt2.MTESTMK = v_i_mtestmk
                       and tt2.MANCHKMK = 'Y'
                       and tt2.BENEVTREL = 'Z'
                       and tt2.REMITMK in ('2','3')
                       and (tt2.ACCEPTMK = 'Y' or nvl(trim(tt2.ACCEPTMK),' ') = ' ')
                       and (tt2.CHKDATE is not null and nvl(trim(tt2.CHKDATE),' ')<>' ')
                       and (tt2.REMITDATE is not null and nvl(trim(tt2.REMITDATE),' ')<>' ')
                   ) t2
                  ,(select tt31.APNO
                          ,tt31.SEQNO
                          ,tt31.IMK as INSKD
                          ,tt31.PAYKIND
                          ,tt31.BENIDS
                          ,tt31.BENEVTREL
                          ,(nvl(trim(tt32.VATNUMBER),tt31.BENIDNNO)) as BENIDN
                          ,tt31.BENNAME
                          ,tt31.BENIDNNO as ACCIDN
                          ,tt32.GVNAME as ACCNAME
                      from BAAPPBASE tt31,BCCMF08 tt32
                     where tt31.APNO like (v_i_paycode||'%')
                       and tt31.BENEVTREL = 'Z'
                       and tt31.BENIDNNO = tt32.GVCD1
                   ) t3
             where t1.APNO = t2.APNO
               and t1.APNO = t3.APNO
               and t2.APNO = t3.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.SEQNO = t3.SEQNO
               and t2.SEQNO = t3.SEQNO
               and t1.ORIISSUYM = t2.ISSUYM
               and t1.PAYYM = t2.PAYYM
               and t1.APNO like (v_i_paycode||'%');
**/
        begin
            v_g_errMsg := ' ';
            v_g_ProgName := 'PKG_BA_genPayFile.sp_BA_genBAPay_S';
            v_g_flag := '0';
            --將已核付待發的核付資料寫入BAPay
            for v_CurPayData in c_dataCur_PayData Loop
                sp_BA_insBAPay(v_CurPayData.APNO
                              ,v_CurPayData.SEQNO
                              ,v_CurPayData.PAYKIND
                              ,v_CurPayData.INSKD
                              ,v_CurPayData.ORIISSUYM
                              ,v_CurPayData.ISSUYM
                              ,v_CurPayData.PAYYM
                              ,v_CurPayData.ISSUTYP
                              ,v_CurPayData.CASETYP
                              ,v_CurPayData.BENEVTREL
                              ,v_CurPayData.BENIDS
                              ,v_CurPayData.BENIDN
                              ,v_CurPayData.BENNAME
                              ,v_CurPayData.PAYTYP
                              ,v_CurPayData.ACCSEQNO
                              ,v_CurPayData.ACCNAME
                              ,v_CurPayData.ACCIDN
                              ,v_CurPayData.PAYBANKID
                              ,v_CurPayData.BRANCHID
                              ,v_CurPayData.PAYEEACC
                              ,v_CurPayData.APLPAYAMT
                              ,v_CurPayData.PAYRATE
                              ,v_i_paydate
                              ,v_CurPayData.DLINEDATE
                              ,v_CurPayData.DLINESEQNO
                              ,v_CurPayData.DLINEMK
                              ,v_i_payseqno
                              ,v_CurPayData.NLWKMK
                              ,v_CurPayData.ADWKMK
                              ,v_CurPayData.NACHGMK);
            end Loop;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '將已核付待發的核付資料寫入核付檔(BAPay)',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

            --將已核付待發的勞貸資料寫入BAPay
            for v_CurFPayData in c_dataCur_FPayData Loop
                sp_BA_insBAPay(v_CurFPayData.APNO
                              ,v_CurFPayData.SEQNO
                              ,v_CurFPayData.PAYKIND
                              ,v_CurFPayData.INSKD
                              ,v_CurFPayData.ORIISSUYM
                              ,v_CurFPayData.ISSUYM
                              ,v_CurFPayData.PAYYM
                              ,v_CurFPayData.ISSUTYP
                              ,v_CurFPayData.CASETYP
                              ,v_CurFPayData.BENEVTREL
                              ,v_CurFPayData.BENIDS
                              ,v_CurFPayData.BENIDN
                              ,v_CurFPayData.BENNAME
                              ,v_CurFPayData.PAYTYP
                              ,v_CurFPayData.ACCSEQNO
                              ,v_CurFPayData.ACCNAME
                              ,v_CurFPayData.ACCIDN
                              ,v_CurFPayData.PAYBANKID
                              ,v_CurFPayData.BRANCHID
                              ,v_CurFPayData.PAYEEACC
                              ,v_CurFPayData.APLPAYAMT
                              ,v_CurFPayData.PAYRATE
                              ,v_i_paydate
                              ,v_CurFPayData.DLINEDATE
                              ,v_CurFPayData.DLINESEQNO
                              ,v_CurFPayData.DLINEMK
                              ,v_i_payseqno
                              ,v_CurFPayData.NLWKMK
                              ,v_CurFPayData.ADWKMK
                              ,v_CurFPayData.NACHGMK);
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '將已核付待發的勞貸資料寫入核付檔(BAPay)',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

            --將已核付待發的補償金資料寫入BAPay
            /**
            for v_CurZPayData in c_dataCur_ZPayData Loop
                sp_BA_insBAPay(v_CurZPayData.APNO
                              ,v_CurZPayData.SEQNO
                              ,v_CurZPayData.PAYKIND
                              ,v_CurZPayData.INSKD
                              ,v_CurZPayData.ORIISSUYM
                              ,v_CurZPayData.ISSUYM
                              ,v_CurZPayData.PAYYM
                              ,v_CurZPayData.ISSUTYP
                              ,v_CurZPayData.CASETYP
                              ,v_CurZPayData.BENEVTREL
                              ,v_CurZPayData.BENIDS
                              ,v_CurZPayData.BENIDN
                              ,v_CurZPayData.BENNAME
                              ,v_CurZPayData.PAYTYP
                              ,v_CurZPayData.ACCSEQNO
                              ,v_CurZPayData.ACCNAME
                              ,v_CurZPayData.ACCIDN
                              ,v_CurZPayData.PAYBANKID
                              ,v_CurZPayData.BRANCHID
                              ,v_CurZPayData.PAYEEACC
                              ,v_CurZPayData.APLPAYAMT
                              ,v_CurZPayData.PAYRATE
                              ,v_i_paydate
                              ,v_CurZPayData.DLINEDATE
                              ,v_CurZPayData.DLINESEQNO
                              ,v_CurZPayData.DLINEMK);
            end Loop;
            **/

            if v_i_proctype = '5' then
                 --將已核付待發不含補償金單位的改匯資料寫入BAPay
                for v_LBACCur in c_dataCur_LBAC Loop
                    sp_BA_insBAPay(v_LBACCur.APNO
                                  ,v_LBACCur.SEQNO
                                  ,v_LBACCur.PAYKIND
                                  ,v_LBACCur.INSKD
                                  ,v_LBACCur.ORIISSUYM
                                  ,v_LBACCur.ISSUYM
                                  ,v_LBACCur.PAYYM
                                  ,v_LBACCur.ISSUTYP
                                  ,v_LBACCur.CASETYP
                                  ,v_LBACCur.BENEVTREL
                                  ,v_LBACCur.BENIDS
                                  ,v_LBACCur.BENIDN
                                  ,v_LBACCur.BENNAME
                                  ,v_LBACCur.PAYTYP
                                  ,v_LBACCur.ACCSEQNO
                                  ,v_LBACCur.ACCNAME
                                  ,v_LBACCur.ACCIDN
                                  ,v_LBACCur.PAYBANKID
                                  ,v_LBACCur.BRANCHID
                                  ,v_LBACCur.PAYEEACC
                                  ,v_LBACCur.APLPAYAMT
                                  ,v_LBACCur.PAYRATE
                                  ,v_i_paydate
                                  ,v_LBACCur.DLINEDATE
                                  ,v_LBACCur.DLINESEQNO
                                  ,v_LBACCur.DLINEMK
                                  ,v_i_payseqno
                                  ,v_LBACCur.NLWKMK
                                  ,v_LBACCur.ADWKMK
                                  ,v_LBACCur.NACHGMK);
                end Loop;

                --修改log作法 by TseHua 20180419
                sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                '將已核付待發不含補償金單位的改匯資料寫入核付檔(BAPay)',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                --將已核付待發不含補償金單位的改匯資料寫入BAPay
                /**
                for v_LBACCur_Z in c_dataCur_LBAC_Z Loop
                    sp_BA_insBAPay(v_LBACCur_Z.APNO
                                  ,v_LBACCur_Z.SEQNO
                                  ,v_LBACCur_Z.PAYKIND
                                  ,v_LBACCur_Z.INSKD
                                  ,v_LBACCur_Z.ORIISSUYM
                                  ,v_LBACCur_Z.ISSUYM
                                  ,v_LBACCur_Z.PAYYM
                                  ,v_LBACCur_Z.ISSUTYP
                                  ,v_LBACCur_Z.CASETYP
                                  ,v_LBACCur_Z.BENEVTREL
                                  ,v_LBACCur_Z.BENIDS
                                  ,v_LBACCur_Z.BENIDN
                                  ,v_LBACCur_Z.BENNAME
                                  ,v_LBACCur_Z.PAYTYP
                                  ,v_LBACCur_Z.ACCSEQNO
                                  ,v_LBACCur_Z.ACCNAME
                                  ,v_LBACCur_Z.ACCIDN
                                  ,v_LBACCur_Z.PAYBANKID
                                  ,v_LBACCur_Z.BRANCHID
                                  ,v_LBACCur_Z.PAYEEACC
                                  ,v_LBACCur_Z.APLPAYAMT
                                  ,v_LBACCur_Z.PAYRATE
                                  ,v_i_paydate
                                  ,v_LBACCur_Z.DLINEDATE
                                  ,v_LBACCur_Z.DLINESEQNO
                                  ,v_LBACCur_Z.DLINEMK);
                end Loop;
                **/
            end if;
            v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                 	 RPAD('**Err:' || v_g_ProgName, 85, '-') || '>>' ||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                    v_g_flag := '1';
                    v_o_flag := v_g_flag;
        end;

    --procedure sp_BA_genBAPay_S End
end;
/