CREATE OR REPLACE Package BA.PKG_BA_genPayRPT
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_genPayRPT
    PURPOSE:         產生月核付相關報表

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2009/07/20  Angela Wu    Created this Package.

    NOTES:
    1.各 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/
authid definer is
    v_g_ProgName             varChar2(200);
    v_g_errMsg               varChar2(4000);
    v_g_LogMsg               varChar2(4000);
    v_g_rowCount             Number;
    v_g_genFlag              Number;
    v_g_rptPage              Number;
    v_g_flag                 varchar2(1);

    Procedure sp_BA_genPayRPT_1 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     Varchar2
    );

    Procedure sp_BA_genPayRPT_1_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_2 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     Varchar2
    );

    Procedure sp_BA_genPayRPT_2_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_3 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_3_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_4 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_4_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_5 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2
    );

    Procedure sp_BA_genPayRPT_6 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varChar2
    );

    Procedure sp_BA_genPayRPT_7 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_7_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_8 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_8_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_9 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_mtestmk             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_9_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_mtestmk             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_10 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_mtestmk             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_10_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_mtestmk             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_11 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

    Procedure sp_BA_genPayRPT_12 (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_acpdate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2
    );

    Procedure sp_BA_genPayRPT_12_S (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_acpdate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2
    );

    Procedure sp_BA_genPayRPT_13 (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_acpdate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2
    );

    Procedure sp_BA_genPayRPT_13_S (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_acpdate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2
    );

    Procedure sp_BA_genPayRPT_14 (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_canceldate          in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2
    );

    Procedure sp_BA_genPayRPT_14_S (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_canceldate          in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2
    );

    Procedure sp_BA_genPayRPT_15 (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_canceldate          in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2
    );

    Procedure sp_BA_genPayRPT_15_S (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_canceldate          in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2
    );
    Procedure sp_BA_genPayRPT_16 (
        v_i_paycode             in      varChar2,
        v_i_procYm              in      varChar2,
        v_i_cprndt              in      varChar2,
        v_o_flag                out     varchar2
    );
     Procedure sp_BA_genPayRPT_16_S (
        v_i_paycode             in      varChar2,
        v_i_procYm              in      varChar2,
        v_i_cprndt              in      varChar2,
        v_o_flag                out     varchar2
    );
    Procedure sp_BA_genPayRPT_17 (
        v_i_paycode             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_deadyy              in      varChar2,
        v_i_apno                in      varChar2,
        v_o_flag                out     varchar2
    );
    Procedure sp_BA_genPayRPT_17_S (
        v_i_paycode             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_deadyy              in      varChar2,
        v_i_apno                in      varChar2,
        v_o_flag                out     varchar2
    );
    Procedure sp_BA_genPayRPT_19_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varChar2,
        v_o_flag                out     varchar2
    );

End;
/

CREATE OR REPLACE Package Body BA.PKG_BA_genPayRPT
is
    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_1
        PURPOSE:         產生月核定合格清冊

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)

                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_1 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --月核定合格清冊 [月核定撥付總表]
        Cursor c_dataCur_rpt1 is
            select t1.APNO                                                                      --受理編號
                  ,t1.SEQNO                                                                     --受款人序
                  ,t1.PAYKIND                                                                   --給付種類
                  ,t1.INSKD                                                                     --保險別
                  ,t1.ISSUYM as ORIISSUYM                                                       --(原)核定年月
                  ,t1.ISSUYM                                                                    --核定年月
                  ,t1.PAYYM                                                                     --給付年月
                  ,t1.CHKDATE                                                                   --核定日期
                  ,t1.APLPAYDATE                                                                --核付日期
                  ,t1.CASETYP                                                                   --案件類別
                  ,t1.ISSUTYP                                                                   --核付分類
                  ,t1.BENEVTREL                                                                 --受益人與事故者關係
                  ,t1.BENIDS                                                                    --受益人社福識別碼
                  ,t1.BENIDN                                                                    --受益人身分證號
                  ,t1.BENNAME                                                                   --受款人姓名
                  ,t1.SOURCEPAYTYP                                                              --給付方式(核付前)
                  ,t1.PAYTYP                                                                    --給付方式
                  ,t1.ACCIDN                                                                    --戶名IDN
                  ,t1.ACCNAME                                                                   --戶名
                  ,t1.PAYBANKID                                                                 --金融機構總代號
                  ,t1.BRANCHID                                                                  --分支代號
                  ,t1.PAYEEACC                                                                  --銀行帳號
                  ,t1.ISSUEAMT                                                                  --核定金額
                  ,t1.APLPAYAMT                                                                 --實付金額
                  ,t1.PAYRATE                                                                   --匯款匯費
                  ,t1.OTHERAAMT                                                                 --另案扣減(同類保險)金額
                  ,t1.OTHERBAMT                                                                 --另案扣減(他類保險)金額
                  ,t1.OFFSETAMT                                                                 --抵銷紓困
                  ,decode(t1.PAYCODE,'L',t1.COMPENAMT,0) as COMPENAMT                           --代扣補償金
                  ,t1.NLWKMK                                                                    --普職註記
                  ,t1.ADWKMK                                                                    --加職註記
                  ,t1.NACHGMK                                                                   --普職互改註記       2012/02/25  Ads By Chungyu
                  ,t1.UNACPAMT                                                                  --收回金額
              from BAISSUDATATEMP t1
             where t1.APNO like (v_i_paycode||'%')
               --  and t1.SEQNO = '0000'                                           -- 2012/06/06 Mark by ChungYu
               and t1.BENEVTREL NOT IN ('F','N','Z')                               -- 2012/06/06 Add by ChungYu
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and (t1.ISSUEAMT >0
                 or t1.APLPAYAMT >0
                 or t1.OTHERAAMT >0
                 or t1.OTHERBAMT >0
                 or t1.PAYRATE >0
                 or t1.OFFSETAMT >0
                 or (t1.COMPENAMT >0 And v_i_paycode = 'L')                        --2012/07/30  Mark By ChungYu 僅有老年要出補償金
                 or (t1.UNACPAMT > 0 And t1.NACHGMK Is Not Null)
                 )
               and t1.PAYSEQNO = v_i_payseqno                                      -- 2012/02/22  Add by Chungyu
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        --月核定合格清冊 [月核定撥付總表] (補償金資料)
        Cursor c_dataCur_rptZ1 is
            select distinct
                   t1.APNO                                                                      --受理編號
                  ,t1.SEQNO                                                                     --受款人序
                  ,t1.PAYKIND                                                                   --給付種類
                  ,t1.INSKD                                                                     --保險別
                  ,t1.ISSUYM as ORIISSUYM                                                       --(原)核定年月
                  ,t1.ISSUYM                                                                    --核定年月
                  ,t1.PAYYM                                                                     --給付年月
                  ,t1.CHKDATE                                                                   --核定日期
                  ,t1.APLPAYDATE                                                                --核付日期
                  ,t1.CASETYP                                                                   --案件類別
                  ,t1.ISSUTYP                                                                   --核付分類
                  ,t2.BENEVTREL                                                                 --受益人與事故者關係
                  ,t1.BENIDS                                                                    --受益人社福識別碼
                  ,t1.BENIDN                                                                    --受益人身分證號
                  ,t1.BENNAME                                                                   --受款人姓名
                  ,t2.SOURCEPAYTYP                                                              --給付方式(核付前)
                  ,t2.PAYTYP                                                                    --給付方式
                  ,t2.ACCIDN                                                                    --戶名IDN
                  ,t2.ACCNAME                                                                   --戶名
                  ,t1.PAYBANKID                                                                 --金融機構總代號
                  ,t1.BRANCHID                                                                  --分支代號
                  ,t1.PAYEEACC                                                                  --銀行帳號
                  ,t1.COMPENAMT as ISSUEAMT                                                     --核定金額(代扣補償金)
                  ,t2.COMPID1                                                                   --代扣補償金單位ID
                  ,t2.COMPNAME1                                                                 --代扣補償金單位名稱
                  ,t1.NLWKMK                                                                    --普職註記
                  ,t1.ADWKMK                                                                    --加職註記
                  ,t1.NACHGMK                                                                   --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.ISSUYM
                          ,t21.PAYYM
                          ,t21.BENIDN as COMPID1
                          ,replace(trim(t22.GVNAME),'　','') as COMPNAME1
                          ,t21.BENIDN as ACCIDN
                          ,t22.GVNAME as ACCNAME
                          ,t21.BENEVTREL
                          ,t21.PAYTYP
                          ,t21.SOURCEPAYTYP
                      from BAISSUDATATEMP t21,BCCMF08 t22
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.BENEVTREL = 'Z'
                       and t21.BENIDN = t22.GVCD1
                       and t21.ISSUYM = v_i_issuym
                       and t21.CHKDATE = v_i_chkdate
                       and (t21.APLPAYDATE is not null and nvl(trim(t21.APLPAYDATE),' ')<>' ')
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
               and t1.PAYSEQNO = v_i_payseqno                                      -- 2012/02/22  Add by Chungyu
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_1';
            v_g_genFlag := 1;
            v_g_rowCount := 0;
            v_g_rptPage := 1;
            v_g_flag := '0';

            for v_dataCur1 in c_dataCur_rpt1 Loop
                v_g_genFlag := 1;
                v_g_rowCount := v_g_rowCount + 1;

                if (v_g_rowCount MOD 20)= 0 then
                    v_g_rptPage := v_g_rptPage +1;
                end if;

                --寫入月核定合格清冊 [月核定撥付總表]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,RPTPAGE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,BENEVTREL
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ACCIDN
                                           ,ACCNAME
                                           ,ISSUEAMT
                                           ,PAYAMT
                                           ,MITRATE
                                           ,OTHERAAMT
                                           ,OTHERBAMT
                                           ,OFFSETAMT
                                           ,COMPENAMT
                                           ,PROCTIME
                                           ,PAYSEQNO                                 -- 2012/02/25 Add by Chungyu
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                           ,UNACPAMT                                 -- 2012/08/17 Add by ChungYu 收回金額
                                          ) values (
                                            '1'
                                           ,v_i_cprndt
                                           ,v_g_rptPage
                                           ,v_dataCur1.APLPAYDATE
                                           ,v_dataCur1.CHKDATE
                                           ,v_dataCur1.APNO
                                           ,v_dataCur1.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur1.PAYKIND
                                           ,v_dataCur1.ISSUTYP
                                           ,v_dataCur1.CASETYP
                                           ,v_i_issuym
                                           ,v_dataCur1.PAYYM
                                           ,v_dataCur1.PAYTYP
                                           ,v_dataCur1.SOURCEPAYTYP
                                           ,v_dataCur1.BENEVTREL
                                           ,v_dataCur1.BENIDS
                                           ,v_dataCur1.BENIDN
                                           ,v_dataCur1.BENNAME
                                           ,v_dataCur1.PAYBANKID
                                           ,v_dataCur1.BRANCHID
                                           ,v_dataCur1.PAYEEACC
                                           ,v_dataCur1.ACCIDN
                                           ,v_dataCur1.ACCNAME
                                           ,v_dataCur1.ISSUEAMT
                                           ,v_dataCur1.APLPAYAMT
                                           ,v_dataCur1.PAYRATE
                                           ,v_dataCur1.OTHERAAMT
                                           ,v_dataCur1.OTHERBAMT
                                           ,v_dataCur1.OFFSETAMT
                                           ,v_dataCur1.COMPENAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno
                                           ,v_dataCur1.NLWKMK
                                           ,v_dataCur1.ADWKMK
                                           ,v_dataCur1.NACHGMK
                                           ,v_dataCur1.UNACPAMT
                );
            end Loop;
            v_g_rowCount := 0;
            for v_dataCurZ1 in c_dataCur_rptZ1 Loop
                v_g_genFlag := 1;
                v_g_rowCount := v_g_rowCount + 1;

                if (v_g_rowCount MOD 20)= 0 then
                    v_g_rptPage := v_g_rptPage +1;
                end if;

                --寫入月核定合格清冊 [月核定撥付總表]資料 (補償金資料)
                --月核定合格清冊 [月核定撥付總表] 內容不列印關係BENEVTREL=F、N、Z,但撥付總表的會計科目及子報表需要列印補償金的資料,故寫入
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,RPTPAGE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,BENEVTREL
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ACCIDN
                                           ,ACCNAME
                                           ,ISSUEAMT
                                           ,COMPID1
                                           ,COMPNAME1
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK
                                          ) values (
                                            '1'
                                           ,v_i_cprndt
                                           ,v_g_rptPage
                                           ,v_dataCurZ1.APLPAYDATE
                                           ,v_dataCurZ1.CHKDATE
                                           ,v_dataCurZ1.APNO
                                           ,v_dataCurZ1.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCurZ1.PAYKIND
                                           ,v_dataCurZ1.ISSUTYP
                                           ,v_dataCurZ1.CASETYP
                                           ,v_i_issuym
                                           ,v_dataCurZ1.PAYYM
                                           ,v_dataCurZ1.PAYTYP
                                           ,v_dataCurZ1.SOURCEPAYTYP
                                           ,v_dataCurZ1.BENEVTREL
                                           ,v_dataCurZ1.BENIDS
                                           ,v_dataCurZ1.BENIDN
                                           ,v_dataCurZ1.BENNAME
                                           ,v_dataCurZ1.PAYBANKID
                                           ,v_dataCurZ1.BRANCHID
                                           ,v_dataCurZ1.PAYEEACC
                                           ,v_dataCurZ1.ACCIDN
                                           ,v_dataCurZ1.ACCNAME
                                           ,v_dataCurZ1.ISSUEAMT
                                           ,v_dataCurZ1.COMPID1
                                           ,v_dataCurZ1.COMPNAME1
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno
                                           ,v_dataCurZ1.NLWKMK
                                           ,v_dataCurZ1.ADWKMK
                                           ,v_dataCurZ1.NACHGMK
                );

                --更新補償金的單位資料 (撥付總表的會計科及子報表需要列印補償金的資料,故需將該補償金單位資料回填)
                Update BAPAYRPTRECORD t set t.COMPID1 = v_dataCurZ1.COMPID1
                                           ,t.COMPNAME1 = v_dataCurZ1.COMPNAME1
                                      where t.RPTTYP = '1'
                                        and t.CPRNDATE = v_i_cprndt
                                        and t.APNO = v_dataCurZ1.APNO
                                        and t.SEQNO = '0000'
                                        and t.PAYKIND = v_dataCurZ1.PAYKIND
                                        and t.ISSUYM = v_i_issuym
                                        and t.PAYYM = v_dataCurZ1.PAYYM
                                        and t.BENEVTREL = '1'
                                        and t.CHKDATE = v_i_chkdate;
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>月核定合格清冊-['||v_i_paycode||']');

            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>月核定合格清冊-['||v_i_paycode||']-NoData');
            end if;


            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '月核定合格清冊資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>月核定合格清冊-['||v_i_paycode||']:'||v_g_errMsg);


                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>月核定合格清冊-['||v_i_paycode||']:'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                     --LOG BATCHJOB detail Add By Zehua 20140722

                      v_g_flag := '1';
                      v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_1 End


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_1_S
        PURPOSE:         產生月核定合格清冊

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)

                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_1_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        v_tempPAYTYP            BAPAYRPTACCOUNT.PAYTYP%TYPE   := null;                          --給付方式

        --月核定合格清冊 [月核定撥付總表]
        Cursor c_dataCur_rpt1 is
            select t1.APNO                                                                      --受理編號
                  ,t1.SEQNO                                                                     --受款人序
                  ,t1.PAYKIND                                                                   --給付種類
                  ,t1.INSKD                                                                     --保險別
                  ,t1.ISSUYM as ORIISSUYM                                                       --(原)核定年月
                  ,t1.ISSUYM                                                                    --核定年月
                  ,t1.PAYYM                                                                     --給付年月
                  ,t1.CHKDATE                                                                   --核定日期
                  ,t1.APLPAYDATE                                                                --核付日期
                  ,t1.CASETYP                                                                   --案件類別
                  ,t1.ISSUTYP                                                                   --核付分類
                  ,t1.BENEVTREL                                                                 --受益人與事故者關係
                  ,t1.BENIDS                                                                    --受益人社福識別碼
                  ,t1.BENIDN                                                                    --受益人身分證號
                  ,t1.BENNAME                                                                   --受款人姓名
                  ,t1.SOURCEPAYTYP                                                              --給付方式(核付前)
                  ,t1.PAYTYP                                                                    --給付方式
                  ,t1.ACCIDN                                                                    --戶名IDN
                  ,t1.ACCNAME                                                                   --戶名
                  ,t1.PAYBANKID                                                                 --金融機構總代號
                  ,t1.BRANCHID                                                                  --分支代號
                  ,t1.PAYEEACC                                                                  --銀行帳號
                  --,t1.ISSUEAMT                                                                --核定金額
                 /* ,Decode(t1.NACHGMK,NULL,Decode(t1.SEQNO,'0000',(t1.OTHERAAMT+t1.OTHERBAMT+t1.OFFSETAMT),
                                                                 (t1.APLPAYAMT+t1.PAYRATE+t1.OTHERAAMT+t1.OTHERBAMT+t1.OTHERBAMT)),
                                          Decode(t1.SEQNO,'0000',(t1.OTHERAAMT+t1.OTHERBAMT+t1.OFFSETAMT),
                                                                  Decode((t1.APLPAYAMT+t1.PAYRATE+t1.OTHERAAMT+t1.OTHERBAMT+t1.OTHERBAMT),0,t1.ISSUEAMT,   -- 普職互改不補不收遺屬要抓原始分配的核定金額，其餘核定金額要抓扣除事故者扣項的總和
                                                                                      (t1.APLPAYAMT+t1.PAYRATE+t1.OTHERAAMT+t1.OTHERBAMT+t1.OTHERBAMT))))             */
                   ,Decode(t1.NACHGMK,NULL,Decode(t1.SEQNO,'0000',(t1.OTHERAAMT+t1.OTHERBAMT+t1.OFFSETAMT),
                                                                 (t1.APLPAYAMT+t1.PAYRATE+t1.OTHERAAMT+t1.OTHERBAMT+t1.OFFSETAMT+t1.INHERITORAMT)),          -- 2017/11/02 Modify by ChungYu
                                           Decode(t1.SEQNO,'0000',(t1.OTHERAAMT+t1.OTHERBAMT+t1.OFFSETAMT),
                                                                   t1.ISSUEAMT))
                   AS "ISSUEAMT"                                                                --核定金額
                                                                                                --2012/08/27 Modify By ChungYu 遺屬事故者紓困貸款及另案扣減要算遺屬事故者的核定金額
                  ,t1.APLPAYAMT                                                                 --實付金額
                  ,t1.PAYRATE                                                                   --匯款匯費
                  ,t1.OTHERAAMT                                                                 --另案扣減(同類保險)金額
                  ,t1.OTHERBAMT                                                                 --另案扣減(他類保險)金額
                  ,t1.OFFSETAMT                                                                 --抵銷紓困
                  ,t1.COMPENAMT                                                                 --代扣補償金
                  ,t1.NLWKMK                                                                    --普職註記
                  ,t1.ADWKMK                                                                    --加職註記
                  ,t1.NACHGMK                                                                   --普職互改註記       2012/02/25  Ads By Chungyu
                  ,t1.UNACPAMT                                                                  --收回金額
                  ,t1.INHERITORAMT                                                              --計息存儲金額(保留遺屬金津貼)  2017/09/08  Add By ChungYu
              from BAISSUDATATEMP t1
             where t1.APNO like (v_i_paycode||'%')
              -- and t1.SEQNO <> '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (((t1.ISSUEAMT >0
                   or t1.APLPAYAMT >0
                   or t1.OTHERAAMT >0
                   or t1.OTHERBAMT >0
                   or t1.PAYRATE >0
                   or t1.OFFSETAMT >0
                   or t1.INHERITORAMT >0                                --計息存儲金額(保留遺屬金津貼)  2017/09/08  Add By ChungYu
                   or (t1.UNACPAMT > 0 And t1.NACHGMK Is Not Null))
                   and SEQNO <> '0000')                            --  以上為遺屬合格清冊及撥付總表條件
                 or ((t1.OTHERAAMT >0
                   or t1.OTHERBAMT >0
                   or t1.OFFSETAMT >0)
                   and SEQNO = '0000'))                            --  以上為事故者合格清冊及撥付總表條件
             order by t1.PAYTYP,t1.NLWKMK,t1.ADWKMK,t1.NACHGMK,t1.APNO,t1.PAYYM,t1.SEQNO;  --修改Order by條件 20190320 Modify by Angela

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_1_S';
            v_g_genFlag := 1;
            v_g_rowCount := 0;
            v_g_rptPage := 1;

            for v_dataCur1 in c_dataCur_rpt1 Loop
                v_g_genFlag := 1;
                v_g_rowCount := v_g_rowCount + 1;

                if (v_g_rowCount MOD 20)= 0 then
                    v_g_rptPage := v_g_rptPage +1;
                end if;

                if ( v_dataCur1.SEQNO = '0000' ) then
                    -- 遺屬事故者並無給付種類，其撥付總表是跟著最小的給付種類呈現，故合格清冊亦相同
                    SELECT NVL(MIN(T1.PAYTYP),'1') Into v_tempPAYTYP
                      FROM BAISSUDATATEMP T1
                     WHERE T1.ISSUYM = v_i_issuym
                       AND T1.APNO = v_dataCur1.APNO
                       AND T1.CHKDATE = v_i_chkdate;
                else
                    v_tempPAYTYP := v_dataCur1.PAYTYP;
                end if;

                --寫入月核定合格清冊 [月核定撥付總表]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,RPTPAGE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,BENEVTREL
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ACCIDN
                                           ,ACCNAME
                                           ,ISSUEAMT
                                           ,PAYAMT
                                           ,MITRATE
                                           ,OTHERAAMT
                                           ,OTHERBAMT
                                           ,OFFSETAMT
                                           ,COMPENAMT
                                           ,PROCTIME
                                           ,PAYSEQNO                                 -- 2012/02/25 Add by Chungyu
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                           ,UNACPAMT                                 -- 2012/08/17 Add by ChungYu 收回金額
                                           ,INHERITORAMT                             -- 2017/09/08 Add by ChungYu 計息存儲金額
                                          ) values (
                                            '1'
                                           ,v_i_cprndt
                                           ,v_g_rptPage
                                           ,v_dataCur1.APLPAYDATE
                                           ,v_dataCur1.CHKDATE
                                           ,v_dataCur1.APNO
                                           ,v_dataCur1.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur1.PAYKIND
                                           ,v_dataCur1.ISSUTYP
                                           ,v_dataCur1.CASETYP
                                           ,v_i_issuym
                                           ,v_dataCur1.PAYYM
                                           ,v_tempPAYTYP
                                           ,v_dataCur1.SOURCEPAYTYP
                                           ,v_dataCur1.BENEVTREL
                                           ,v_dataCur1.BENIDS
                                           ,v_dataCur1.BENIDN
                                           ,v_dataCur1.BENNAME
                                           ,v_dataCur1.PAYBANKID
                                           ,v_dataCur1.BRANCHID
                                           ,v_dataCur1.PAYEEACC
                                           ,v_dataCur1.ACCIDN
                                           ,v_dataCur1.ACCNAME
                                           ,v_dataCur1.ISSUEAMT
                                           ,v_dataCur1.APLPAYAMT
                                           ,v_dataCur1.PAYRATE
                                           ,v_dataCur1.OTHERAAMT
                                           ,v_dataCur1.OTHERBAMT
                                           ,v_dataCur1.OFFSETAMT
                                           ,v_dataCur1.COMPENAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno
                                           ,v_dataCur1.NLWKMK
                                           ,v_dataCur1.ADWKMK
                                           ,v_dataCur1.NACHGMK
                                           ,v_dataCur1.UNACPAMT
                                           ,v_dataCur1.INHERITORAMT                  -- 2017/09/08 Add by ChungYu 計息存儲金額
                 );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>月核定合格清冊(遺屬)-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>月核定合格清冊(遺屬)-['||v_i_paycode||']-NoData');
            end if;


            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '月核定合格清冊(遺屬)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
             v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>月核定合格清冊(遺屬)-['||v_i_paycode||']:'||v_g_errMsg);


                     --修改log作法 by TseHua 20180419
                      sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>月核定合格清冊(遺屬)-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_1_S End


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_2
        PURPOSE:         產生給付抵銷紓困貸款明細表

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)

                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_2 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --給付抵銷紓困貸款明細
        --20130223 Modify by Angela：增加 Union。因勞貸資料結清後會搬檔至紓困結案檔，可能會導致出媒體檔時無資料，所以需要 Union 紓困結案檔。
        cursor c_dataCur_rpt2 is
            select t1.APNO                                                                                 --受理編號
                  ,t1.SEQNO                                                                                --序號
                  ,t1.PAYKIND                                                                              --給付種類
                  ,t1.BENEVTREL                                                                            --受益人與事故者關係
                  ,t1.BENIDS                                                                               --受益人社福識別碼
                  ,t1.BENIDN                                                                               --身分證號
                  ,t1.BENNAME                                                                              --姓名
                  ,t1.PAYTYP                                                                               --給付方式
                  ,t1.SOURCEPAYTYP                                                                         --給付方式(核付前)
                  ,t2.PAYBANKID                                                                            --金融機構總代號
                  ,t2.BRANCHID                                                                             --分支代號
                  ,t2.PAYEEACC                                                                             --銀行帳號
                  ,t1.ACCIDN                                                                               --戶名IDN
                  ,t1.ACCNAME                                                                              --戶名
                  ,t1.CASETYP                                                                              --案件類別
                  ,t1.ISSUTYP                                                                              --核付分類
                  ,t1.ISSUYM                                                                               --核定年月
                  ,t1.PAYYM                                                                                --給付年月
                  ,t1.OFFSETAMT as APLPAYAMT                                                               --抵銷金額
                  ,t1.CHKDATE                                                                              --核定日期
                  ,t1.APLPAYDATE                                                                           --核付日期
                  ,t1.DLINEDATE                                                                            --勞貸本息截止日
                  ,t2.DLINESEQNO                                                                           --流水號(勞貸)
                  ,t1.PAYSEQNO                                                                             --指定核付序號       2012/02/25  Ads By Chungyu
                  ,t1.NLWKMK                                                                               --普職註記
                  ,t1.ADWKMK                                                                               --加職註記
                  ,t1.NACHGMK                                                                              --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
                  ,(select t21.IDN_APLY
                          ,t21.IDN_LIB
                          ,substr(t21.BRNO,1,3) as BRANCHID
                          ,((deCode(length(t21.SEQNO),6,(deCode(substr(t21.SEQNO,1,1),'0','092'
                                                                                     ,'3','093'
                                                                                     ,'5','095'
                                                                                     ,'6','096'
                                                                                     ,'7','097'
                                                                                     ,'8','098'
                                                                                     ,'9','099'
                                                                                     ,substr(t21.SEQNO,1,1))
                                                        ),substr(t21.SEQNO,1,1))
                            )||substr(t21.SEQNO,2,8)) as DLINESEQNO
                          ,t21.ACCOUNT_PAY as PAYEEACC
                          ,' ' as PAYBANKID
                      from MBLNM t21
                     where nvl(trim(t21.EMRK),' ') <> '1'
                   ) t2
             where t1.APNO like (v_i_paycode||'%')
               and (t1.BENIDN = t2.IDN_APLY or t1.BENIDN = t2.IDN_LIB)
               and t1.SEQNO = '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and t1.OFFSETAMT > 0
               and t1.PAYSEQNO = v_i_payseqno                                                              -- 2012/02/22  Add by Chungyu
            /* union
            select t1.APNO                                                                                 --受理編號
                  ,t1.SEQNO                                                                                --序號
                  ,t1.PAYKIND                                                                              --給付種類
                  ,t1.BENEVTREL                                                                            --受益人與事故者關係
                  ,t1.BENIDS                                                                               --受益人社福識別碼
                  ,t1.BENIDN                                                                               --身分證號
                  ,t1.BENNAME                                                                              --姓名
                  ,t1.PAYTYP                                                                               --給付方式
                  ,t1.SOURCEPAYTYP                                                                         --給付方式(核付前)
                  ,t2.PAYBANKID                                                                            --金融機構總代號
                  ,t2.BRANCHID                                                                             --分支代號
                  ,t2.PAYEEACC                                                                             --銀行帳號
                  ,t1.ACCIDN                                                                               --戶名IDN
                  ,t1.ACCNAME                                                                              --戶名
                  ,t1.CASETYP                                                                              --案件類別
                  ,t1.ISSUTYP                                                                              --核付分類
                  ,t1.ISSUYM                                                                               --核定年月
                  ,t1.PAYYM                                                                                --給付年月
                  ,t1.OFFSETAMT as APLPAYAMT                                                               --抵銷金額
                  ,t1.CHKDATE                                                                              --核定日期
                  ,t1.APLPAYDATE                                                                           --核付日期
                  ,t1.DLINEDATE                                                                            --勞貸本息截止日
                  ,t2.DLINESEQNO                                                                           --流水號(勞貸)
                  ,t1.PAYSEQNO                                                                             --指定核付序號       2012/02/25  Ads By Chungyu
                  ,t1.NLWKMK                                                                               --普職註記
                  ,t1.ADWKMK                                                                               --加職註記
                  ,t1.NACHGMK                                                                              --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
                  ,(select t21.IDN_APLY
                          ,t21.IDN_LIB
                          ,substr(t21.BRNO,1,3) as BRANCHID
                          ,((deCode(length(t21.SEQNO),6,(deCode(substr(t21.SEQNO,1,1),'0','092'
                                                                                     ,'3','093'
                                                                                     ,'5','095'
                                                                                     ,'6','096'
                                                                                     ,'7','097'
                                                                                     ,'8','098'
                                                                                     ,'9','099'
                                                                                     ,substr(t21.SEQNO,1,1))
                                                        ),substr(t21.SEQNO,1,1))
                            )||substr(t21.SEQNO,2,8)) as DLINESEQNO
                          ,t21.ACCOUNT_PAY as PAYEEACC
                          ,' ' as PAYBANKID
                      from MBLNC t21
                     where nvl(trim(t21.EMRK),' ') = '1'
                       and t21.EDATE like (to_Char(Sysdate,'YYYY')||'%')
                   ) t2
             where t1.APNO like (v_i_paycode||'%')
               and (t1.BENIDN = t2.IDN_APLY or t1.BENIDN = t2.IDN_LIB)
               and t1.SEQNO = '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and t1.OFFSETAMT > 0
               and t1.PAYSEQNO = v_i_payseqno                                      -- 2012/02/22  Add by Chungyu
             order by APNO,SEQNO,PAYYM*/;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_2';
            v_g_genFlag := 0;
            v_g_rptPage := 1;
            v_g_flag    := '0';
            for v_dataCur2 in c_dataCur_rpt2 Loop
                v_g_genFlag := 1;
                v_g_rowCount := v_g_rowCount + 1;

                if (v_g_rowCount MOD 20)= 0 then
                    v_g_rptPage := v_g_rptPage +1;
                end if;

                --寫入給付抵銷紓困貸款明細資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,RPTPAGE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,PAYAMT
                                           ,DLINESEQNO
                                           ,DLINEDATE
                                           ,BENEVTREL
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,PROCTIME
                                           ,PAYSEQNO                                 -- 2012/02/25 Add by Chungyu
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                          ) values (
                                            '2'
                                           ,v_i_cprndt
                                           ,v_g_rptPage
                                           ,v_dataCur2.APLPAYDATE
                                           ,v_dataCur2.CHKDATE
                                           ,v_dataCur2.APNO
                                           ,v_dataCur2.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur2.PAYKIND
                                           ,v_dataCur2.ISSUTYP
                                           ,v_dataCur2.CASETYP
                                           ,v_i_issuym
                                           ,v_dataCur2.PAYYM
                                           ,v_dataCur2.BENIDN
                                           ,v_dataCur2.BENNAME
                                           ,v_dataCur2.PAYBANKID
                                           ,v_dataCur2.BRANCHID
                                           ,v_dataCur2.PAYEEACC
                                           ,v_dataCur2.APLPAYAMT
                                           ,v_dataCur2.DLINESEQNO
                                           ,v_dataCur2.DLINEDATE
                                           ,v_dataCur2.BENEVTREL
                                           ,v_dataCur2.PAYTYP
                                           ,v_dataCur2.SOURCEPAYTYP
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno
                                           ,v_dataCur2.NLWKMK
                                           ,v_dataCur2.ADWKMK
                                           ,v_dataCur2.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>給付抵銷紓困貸款明細-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>給付抵銷紓困貸款明細-['||v_i_paycode||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '給付抵銷紓困貸款明細資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
             v_o_flag := v_g_flag;
       exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>給付抵銷紓困貸款明細-['||v_i_paycode||']:'||v_g_errMsg);


     	          --修改log作法 by TseHua 20180419
                sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                RPAD('**Err:'||v_g_ProgName,85,'-')||'>>給付抵銷紓困貸款明細-['||v_i_paycode||']:'||v_g_errMsg,
                   replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                 v_g_flag := '1';
                 v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_2 End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_2_S
        PURPOSE:         產生給付抵銷紓困貸款明細表

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)

                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_2_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --給付抵銷紓困貸款明細
        --20130223 Modify by Angela：增加 Union。因勞貸資料結清後會搬檔至紓困結案檔，可能會導致出媒體檔時無資料，所以需要 Union 紓困結案檔。
        cursor c_dataCur_rpt2 is
            select t1.APNO                                                                                 --受理編號
                  ,t1.SEQNO                                                                                --序號
                  ,t1.PAYKIND                                                                              --給付種類
                  ,t1.BENEVTREL                                                                            --受益人與事故者關係
                  ,t1.BENIDS                                                                               --受益人社福識別碼
                  ,t1.BENIDN                                                                               --身分證號
                  ,t1.BENNAME                                                                              --姓名
                  ,t1.PAYTYP                                                                               --給付方式
                  ,t1.SOURCEPAYTYP                                                                         --給付方式(核付前)
                  ,t2.PAYBANKID                                                                            --金融機構總代號
                  ,t2.BRANCHID                                                                             --分支代號
                  ,t2.PAYEEACC                                                                             --銀行帳號
                  ,t1.ACCIDN                                                                               --戶名IDN
                  ,t1.ACCNAME                                                                              --戶名
                  ,t1.CASETYP                                                                              --案件類別
                  ,t1.ISSUTYP                                                                              --核付分類
                  ,t1.ISSUYM                                                                               --核定年月
                  ,t1.PAYYM                                                                                --給付年月
                  ,t1.OFFSETAMT as APLPAYAMT                                                               --抵銷金額
                  ,t1.CHKDATE                                                                              --核定日期
                  ,t1.APLPAYDATE                                                                           --核付日期
                  ,t1.DLINEDATE                                                                            --勞貸本息截止日
                  ,t2.DLINESEQNO                                                                           --流水號(勞貸)
                  ,t1.NLWKMK                                                                               --普職註記
                  ,t1.ADWKMK                                                                               --加職註記
                  ,t1.NACHGMK                                                                              --普職互改註記
              from BAISSUDATATEMP t1
                  ,(select t21.IDN_APLY
                          ,t21.IDN_LIB
                          ,substr(t21.BRNO,1,3) as BRANCHID
                          ,((deCode(length(t21.SEQNO),6,(deCode(substr(t21.SEQNO,1,1),'0','092'
                                                                                     ,'3','093'
                                                                                     ,'5','095'
                                                                                     ,'6','096'
                                                                                     ,'7','097'
                                                                                     ,'8','098'
                                                                                     ,'9','099'
                                                                                     ,substr(t21.SEQNO,1,1))
                                                        ),substr(t21.SEQNO,1,1))
                            )||substr(t21.SEQNO,2,8)) as DLINESEQNO
                          ,t21.ACCOUNT_PAY as PAYEEACC
                          ,' ' as PAYBANKID
                      from MBLNM t21
                     where nvl(trim(t21.EMRK),' ') <> '1'
                   ) t2
             where t1.APNO like (v_i_paycode||'%')
               and (t1.BENIDN = t2.IDN_APLY or t1.BENIDN = t2.IDN_LIB)
               and t1.SEQNO = '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and t1.OFFSETAMT > 0
            /* union
            select t1.APNO                                                                                 --受理編號
                  ,t1.SEQNO                                                                                --序號
                  ,t1.PAYKIND                                                                              --給付種類
                  ,t1.BENEVTREL                                                                            --受益人與事故者關係
                  ,t1.BENIDS                                                                               --受益人社福識別碼
                  ,t1.BENIDN                                                                               --身分證號
                  ,t1.BENNAME                                                                              --姓名
                  ,t1.PAYTYP                                                                               --給付方式
                  ,t1.SOURCEPAYTYP                                                                         --給付方式(核付前)
                  ,t2.PAYBANKID                                                                            --金融機構總代號
                  ,t2.BRANCHID                                                                             --分支代號
                  ,t2.PAYEEACC                                                                             --銀行帳號
                  ,t1.ACCIDN                                                                               --戶名IDN
                  ,t1.ACCNAME                                                                              --戶名
                  ,t1.CASETYP                                                                              --案件類別
                  ,t1.ISSUTYP                                                                              --核付分類
                  ,t1.ISSUYM                                                                               --核定年月
                  ,t1.PAYYM                                                                                --給付年月
                  ,t1.OFFSETAMT as APLPAYAMT                                                               --抵銷金額
                  ,t1.CHKDATE                                                                              --核定日期
                  ,t1.APLPAYDATE                                                                           --核付日期
                  ,t1.DLINEDATE                                                                            --勞貸本息截止日
                  ,t2.DLINESEQNO                                                                           --流水號(勞貸)
                  ,t1.NLWKMK                                                                               --普職註記
                  ,t1.ADWKMK                                                                               --加職註記
                  ,t1.NACHGMK                                                                              --普職互改註記
              from BAISSUDATATEMP t1
                  ,(select t21.IDN_APLY
                          ,t21.IDN_LIB
                          ,substr(t21.BRNO,1,3) as BRANCHID
                          ,((deCode(length(t21.SEQNO),6,(deCode(substr(t21.SEQNO,1,1),'0','092'
                                                                                     ,'3','093'
                                                                                     ,'5','095'
                                                                                     ,'6','096'
                                                                                     ,'7','097'
                                                                                     ,'8','098'
                                                                                     ,'9','099'
                                                                                     ,substr(t21.SEQNO,1,1))
                                                        ),substr(t21.SEQNO,1,1))
                            )||substr(t21.SEQNO,2,8)) as DLINESEQNO
                          ,t21.ACCOUNT_PAY as PAYEEACC
                          ,' ' as PAYBANKID
                      from MBLNC t21
                     where nvl(trim(t21.EMRK),' ') = '1'
                       and t21.EDATE like (to_Char(Sysdate,'YYYY')||'%')
                   ) t2
             where t1.APNO like (v_i_paycode||'%')
               and (t1.BENIDN = t2.IDN_APLY or t1.BENIDN = t2.IDN_LIB)
               and t1.SEQNO = '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and t1.OFFSETAMT > 0
             order by APNO,SEQNO,PAYYM*/;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_2_S';
            v_g_genFlag := 0;
            v_g_rptPage := 1;
            v_g_flag    := '0';
            for v_dataCur2 in c_dataCur_rpt2 Loop
                v_g_genFlag := 1;
                v_g_rowCount := v_g_rowCount + 1;

                if (v_g_rowCount MOD 20)= 0 then
                    v_g_rptPage := v_g_rptPage +1;
                end if;

                --寫入給付抵銷紓困貸款明細資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,RPTPAGE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,PAYAMT
                                           ,DLINESEQNO
                                           ,DLINEDATE
                                           ,BENEVTREL
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                          ) values (
                                            '2'
                                           ,v_i_cprndt
                                           ,v_g_rptPage
                                           ,v_dataCur2.APLPAYDATE
                                           ,v_dataCur2.CHKDATE
                                           ,v_dataCur2.APNO
                                           ,v_dataCur2.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur2.PAYKIND
                                           ,v_dataCur2.ISSUTYP
                                           ,v_dataCur2.CASETYP
                                           ,v_i_issuym
                                           ,v_dataCur2.PAYYM
                                           ,v_dataCur2.BENIDN
                                           ,v_dataCur2.BENNAME
                                           ,v_dataCur2.PAYBANKID
                                           ,v_dataCur2.BRANCHID
                                           ,v_dataCur2.PAYEEACC
                                           ,v_dataCur2.APLPAYAMT
                                           ,v_dataCur2.DLINESEQNO
                                           ,v_dataCur2.DLINEDATE
                                           ,v_dataCur2.BENEVTREL
                                           ,v_dataCur2.PAYTYP
                                           ,v_dataCur2.SOURCEPAYTYP
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno
                                           ,v_dataCur2.NLWKMK
                                           ,v_dataCur2.ADWKMK
                                           ,v_dataCur2.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>給付抵銷紓困貸款明細(遺屬)-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>給付抵銷紓困貸款明細(遺屬)-['||v_i_paycode||']-NoData');
            end if;


            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '給付抵銷紓困貸款明細資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
             v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>給付抵銷紓困貸款明細(遺屬)-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                     sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                      RPAD('**Err:'||v_g_ProgName,85,'-')||'>>給付抵銷紓困貸款明細(遺屬)-['||v_i_paycode||']:'||v_g_errMsg,
                      replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_2_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_3
        PURPOSE:         產生核付清單

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/

    Procedure sp_BA_genPayRPT_3 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        v_payType               varChar2(1);

        --查詢需產出核付清單的給付方式
        cursor c_dataCur_rpt3 is
            select PARAMCODE from BAPARAM t
             where t.PARAMGROUP = 'PAYTYPE'
               and t.PARAMUSEMK = 'Y'
               and t.PARAMCODE<>'1' and t.PARAMCODE<>'2'
             order by t.PARAMSEQ;

        --核付清單
        Cursor c_dataCur_rpt31(v_payTyp varChar2) is
            select t1.APNO                                                                      --受理編號
                  ,t1.SEQNO                                                                     --受款人序
                  ,t1.PAYKIND                                                                   --給付種類
                  ,t1.INSKD                                                                     --保險別
                  ,t1.ISSUYM as ORIISSUYM                                                       --(原)核定年月
                  ,t1.ISSUYM                                                                    --核定年月
                  ,t1.PAYYM                                                                     --給付年月
                  ,t1.CHKDATE                                                                   --核定日期
                  ,t1.APLPAYDATE                                                                --核付日期
                  ,t1.CASETYP                                                                   --案件類別
                  ,t1.ISSUTYP                                                                   --核付分類
                  ,t1.BENEVTREL                                                                 --受益人與事故者關係
                  ,t1.BENIDS                                                                    --受益人社福識別碼
                  ,t1.BENIDN                                                                    --受益人身分證號
                  ,t1.BENNAME                                                                   --受款人姓名
                  ,t1.SOURCEPAYTYP                                                              --給付方式(核付前)
                  ,t1.PAYTYP                                                                    --給付方式
                  ,t1.ACCIDN                                                                    --戶名IDN
                  ,t1.ACCNAME                                                                   --戶名
                  ,t1.PAYBANKID                                                                 --金融機構總代號
                  ,t1.BRANCHID                                                                  --分支代號
                  ,t1.PAYEEACC                                                                  --銀行帳號
                  ,t1.ISSUEAMT                                                                  --核定金額
                  ,t1.APLPAYAMT                                                                 --實付金額
                  ,t1.PAYRATE                                                                   --匯款匯費
                  ,t1.PAYSEQNO                                                                  --指定核付序號       2012/02/25  Ads By Chungyu
                  ,t1.NLWKMK                                                                    --普職註記
                  ,t1.ADWKMK                                                                    --加職註記
                  ,t1.NACHGMK                                                                   --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
             where t1.APNO like (v_i_paycode||'%')
               --and t1.SEQNO = '0000'
               and t1.BENEVTREL NOT IN ('F','N','Z')
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and t1.PAYTYP = v_payTyp
               and t1.PAYTYP not in ('1','2')
               and (t1.ISSUEAMT >0
                 or t1.APLPAYAMT >0)
               and t1.PAYSEQNO = v_i_payseqno                                      -- 2012/02/22  Add by Chungyu
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        /*
        --查詢需產出核付清單的補償金資料,其給付方式為何
        --Mark by Angela 20121121
        cursor c_dataCur_rpt32 is
            select distinct t2.PAYTYP
              from BAISSUDATATEMP t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.ISSUYM
                          ,t21.PAYYM
                          ,t21.BENEVTREL
                          ,t21.PAYTYP
                          ,t21.APLPAYDATE
                      from BAISSUDATATEMP t21,BCCMF08 t22
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.BENEVTREL = 'Z'
                       and t21.BENIDN = t22.GVCD1
                       and t21.ISSUYM = v_i_issuym
                       and t21.CHKDATE = v_i_chkdate
                       and (t21.APLPAYDATE is not null and nvl(trim(t21.APLPAYDATE),' ')<>' ')
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
               and (t1.APLPAYDATE > 0
                 or t1.COMPENAMT >0)
               and t1.PAYSEQNO = v_i_payseqno;                                      -- 2012/02/22  Add by Chungyu
        */

        --核付清單 (補償金資料)
        Cursor c_dataCur_rptZ3(v_payTyp varChar2) is
            select distinct
                   t1.APNO                                                                      --受理編號
                  ,t1.SEQNO                                                                     --受款人序
                  ,t1.PAYKIND                                                                   --給付種類
                  ,t1.INSKD                                                                     --保險別
                  ,t1.ISSUYM as ORIISSUYM                                                       --(原)核定年月
                  ,t1.ISSUYM                                                                    --核定年月
                  ,t1.PAYYM                                                                     --給付年月
                  ,t1.CHKDATE                                                                   --核定日期
                  ,t1.APLPAYDATE                                                                --核付日期
                  ,t1.CASETYP                                                                   --案件類別
                  ,t1.ISSUTYP                                                                   --核付分類
                  ,t2.BENEVTREL                                                                 --受益人與事故者關係
                  ,t1.BENIDS                                                                    --受益人社福識別碼
                  ,t1.BENIDN                                                                    --受益人身分證號
                  ,t1.BENNAME                                                                   --受款人姓名
                  ,t2.SOURCEPAYTYP                                                              --給付方式(核付前)
                  ,t2.PAYTYP                                                                    --給付方式
                  ,t2.ACCIDN                                                                    --戶名IDN
                  ,t2.ACCNAME                                                                   --戶名
                  ,t1.PAYBANKID                                                                 --金融機構總代號
                  ,t1.BRANCHID                                                                  --分支代號
                  ,t1.PAYEEACC                                                                  --銀行帳號
                  ,t1.COMPENAMT as APLPAYAMT                                                    --實付金額(代扣補償金)
                  ,t1.COMPENAMT                                                                 --代扣補償金
                  ,t2.COMPID1                                                                   --代扣補償金單位ID
                  ,t2.COMPNAME1                                                                 --代扣補償金單位名稱
                  ,t4.UBNO as COMPID2                                                           --代算單位ID
                  ,t4.UNAME as COMPNAME2                                                        --代算單位名稱
                  ,t1.PAYSEQNO                                                                  --指定核付序號       2012/02/25  Ads By Chungyu
                  ,t1.NLWKMK                                                                    --普職註記
                  ,t1.ADWKMK                                                                    --加職註記
                  ,t1.NACHGMK                                                                   --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.ISSUYM
                          ,t21.PAYYM
                          ,t21.BENIDN as ACCIDN
                          ,t22.GVNAME as ACCNAME
                          ,t21.BENIDN as COMPID1
                          ,replace(trim(t22.GVNAME),'　','') as COMPNAME1
                          ,t21.BENEVTREL
                          ,t21.PAYTYP
                          ,t21.SOURCEPAYTYP
                      from BAISSUDATATEMP t21,BCCMF08 t22
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.BENEVTREL = 'Z'
                       and t21.BENIDN = t22.GVCD1
                       and t21.SEQNO <> '0000'
                       and t21.ISSUYM = v_i_issuym
                       and t21.PAYTYP = v_payTyp
                       and t21.PAYTYP not in ('1','2')
                       and t21.CHKDATE = v_i_chkdate
                       and (t21.APLPAYDATE is not null and nvl(trim(t21.APLPAYDATE),' ')<>' ')
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
               and t2.PAYTYP = v_payTyp
               and t2.PAYTYP not in ('1','2')
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
               and t1.PAYSEQNO = v_i_payseqno                                      -- 2012/02/22  Add by Chungyu
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_3';
            v_payType := '';
            v_g_genFlag := 0;
            v_g_flag    := '0';
            for v_dataCur3 in c_dataCur_rpt3 Loop
                v_g_rowCount := 0;
                v_payType := v_dataCur3.PARAMCODE;

                for v_dataCur31 in c_dataCur_rpt31(v_payType) Loop
                    v_g_genFlag := 1;
                    v_g_rowCount := v_g_rowCount + 1;

                    --寫入核付清單資料
                    insert into BAPAYRPTRECORD (RPTTYP
                                               ,CPRNDATE
                                               ,PAYDATE
                                               ,CHKDATE
                                               ,APNO
                                               ,SEQNO
                                               ,PAYCODE
                                               ,PAYKIND
                                               ,ISSUTYP
                                               ,CASETYP
                                               ,ISSUYM
                                               ,PAYYM
                                               ,PAYTYP
                                               ,SOURCEPAYTYP
                                               ,BENEVTREL
                                               ,BENIDS
                                               ,BENIDN
                                               ,BENNAME
                                               ,PAYBANKID
                                               ,BRANCHID
                                               ,PAYEEACC
                                               ,ACCIDN
                                               ,ACCNAME
                                               ,ISSUEAMT
                                               ,PAYAMT
                                               ,PROCTIME
                                               ,PAYSEQNO
                                               ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                               ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                               ,NACHGMK
                                              ) values (
                                                '3'
                                               ,v_i_cprndt
                                               ,v_dataCur31.APLPAYDATE
                                               ,v_dataCur31.CHKDATE
                                               ,v_dataCur31.APNO
                                               ,v_dataCur31.SEQNO
                                               ,v_i_paycode
                                               ,v_dataCur31.PAYKIND
                                               ,v_dataCur31.ISSUTYP
                                               ,v_dataCur31.CASETYP
                                               ,v_i_issuym
                                               ,v_dataCur31.PAYYM
                                               ,v_dataCur31.PAYTYP
                                               ,v_dataCur31.SOURCEPAYTYP
                                               ,v_dataCur31.BENEVTREL
                                               ,v_dataCur31.BENIDS
                                               ,v_dataCur31.BENIDN
                                               ,v_dataCur31.BENNAME
                                               ,v_dataCur31.PAYBANKID
                                               ,v_dataCur31.BRANCHID
                                               ,v_dataCur31.PAYEEACC
                                               ,v_dataCur31.ACCIDN
                                               ,v_dataCur31.ACCNAME
                                               ,v_dataCur31.ISSUEAMT
                                               ,v_dataCur31.APLPAYAMT
                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                               ,v_dataCur31.PAYSEQNO
                                               ,v_dataCur31.NLWKMK
                                               ,v_dataCur31.ADWKMK
                                               ,v_dataCur31.NACHGMK
                    );

                    Update BADAPR t set t.CPRNDATE = v_i_cprndt
                                  where t.APNO = v_dataCur31.APNO
                                    and t.SEQNO = v_dataCur31.SEQNO
                                    and t.ISSUYM = v_i_issuym
                                    and t.PAYYM = v_dataCur31.PAYYM
                                    and t.PAYKIND = v_dataCur31.PAYKIND
                                    and t.BENEVTREL = v_dataCur31.BENEVTREL
                                    and t.CHKDATE = v_i_chkdate;
                end Loop;
            end Loop;

            v_payType := ' ';
            for v_dataCur3 in c_dataCur_rpt3 Loop
                v_g_rowCount := 0;
                v_payType := v_dataCur3.PARAMCODE;

                for v_dataCurZ3 in c_dataCur_rptZ3(v_payType) Loop
                    v_g_genFlag := 1;
                    v_g_rowCount := v_g_rowCount + 1;

                    --寫入給核付清單資料 (補償金資料)
                    insert into BAPAYRPTRECORD (RPTTYP
                                               ,CPRNDATE
                                               ,PAYDATE
                                               ,CHKDATE
                                               ,APNO
                                               ,SEQNO
                                               ,PAYCODE
                                               ,PAYKIND
                                               ,ISSUTYP
                                               ,CASETYP
                                               ,ISSUYM
                                               ,PAYYM
                                               ,PAYTYP
                                               ,SOURCEPAYTYP
                                               ,BENEVTREL
                                               ,BENIDS
                                               ,BENIDN
                                               ,BENNAME
                                               ,PAYBANKID
                                               ,BRANCHID
                                               ,PAYEEACC
                                               ,ACCIDN
                                               ,ACCNAME
                                               ,PAYAMT
                                               ,COMPENAMT
                                               ,COMPID1
                                               ,COMPNAME1
                                               ,COMPID2
                                               ,COMPNAME2
                                               ,PROCTIME
                                               ,PAYSEQNO
                                               ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                               ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                               ,NACHGMK
                                              ) values (
                                                '3'
                                               ,v_i_cprndt
                                               ,v_dataCurZ3.APLPAYDATE
                                               ,v_dataCurZ3.CHKDATE
                                               ,v_dataCurZ3.APNO
                                               ,v_dataCurZ3.SEQNO
                                               ,v_i_paycode
                                               ,v_dataCurZ3.PAYKIND
                                               ,v_dataCurZ3.ISSUTYP
                                               ,v_dataCurZ3.CASETYP
                                               ,v_i_issuym
                                               ,v_dataCurZ3.PAYYM
                                               ,v_dataCurZ3.PAYTYP
                                               ,v_dataCurZ3.SOURCEPAYTYP
                                               ,v_dataCurZ3.BENEVTREL
                                               ,v_dataCurZ3.BENIDS
                                               ,v_dataCurZ3.BENIDN
                                               ,v_dataCurZ3.BENNAME
                                               ,v_dataCurZ3.PAYBANKID
                                               ,v_dataCurZ3.BRANCHID
                                               ,v_dataCurZ3.PAYEEACC
                                               ,v_dataCurZ3.ACCIDN
                                               ,v_dataCurZ3.ACCNAME
                                               ,v_dataCurZ3.APLPAYAMT
                                               ,v_dataCurZ3.COMPENAMT
                                               ,v_dataCurZ3.COMPID1
                                               ,v_dataCurZ3.COMPNAME1
                                               ,v_dataCurZ3.COMPID2
                                               ,v_dataCurZ3.COMPNAME2
                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                               ,v_dataCurZ3.PAYSEQNO
                                               ,v_dataCurZ3.NLWKMK
                                               ,v_dataCurZ3.ADWKMK
                                               ,v_dataCurZ3.NACHGMK
                    );

                    --更新補償金的單位資料 (因受款人ID及受款人姓名欄位為補償金的受款人資料,故需將該補償金單位資料回填)
                    Update BAPAYRPTRECORD t set t.COMPID1 = v_dataCurZ3.COMPID1
                                               ,t.COMPNAME1 = v_dataCurZ3.COMPNAME1
                                          where t.RPTTYP = '3'
                                            and t.CPRNDATE = v_i_cprndt
                                            and t.PAYDATE = v_dataCurZ3.APLPAYDATE
                                            and t.APNO = v_dataCurZ3.APNO
                                            and t.SEQNO = '0000'
                                            and t.PAYKIND = v_dataCurZ3.PAYKIND
                                            and t.ISSUYM = v_i_issuym
                                            and t.PAYYM = v_dataCurZ3.PAYYM
                                            and t.BENEVTREL = '1'
                                            and t.CHKDATE = v_i_chkdate;

                    Update BADAPR t set t.CPRNDATE = v_i_cprndt
                                  where t.APNO = v_dataCurZ3.APNO
                                    and t.SEQNO = v_dataCurZ3.SEQNO
                                    and t.ISSUYM = v_i_issuym
                                    and t.PAYYM = v_dataCurZ3.PAYYM
                                    and t.PAYKIND = v_dataCurZ3.PAYKIND
                                    and t.BENEVTREL = v_dataCurZ3.BENEVTREL
                                    and t.CHKDATE = v_i_chkdate;
                end Loop;
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>核付清單-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>核付清單-['||v_i_paycode||']-NoData');
            end if;


            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '核付清單資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
             v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>核付清單-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
            	        RPAD('**Err:'||v_g_ProgName,85,'-')||'>>核付清單-['||v_i_paycode||']:'||v_g_errMsg ,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                    v_g_flag := '1';
                    v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_3 End


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_3_S
        PURPOSE:         產生核付清單

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_3_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        v_payType               varChar2(1);

        --查詢需產出核付清單的給付方式
        cursor c_dataCur_rpt3 is
            select PARAMCODE from BAPARAM t
             where t.PARAMGROUP = 'PAYTYPE'
               and t.PARAMUSEMK = 'Y'
               and t.PARAMCODE<>'1' and t.PARAMCODE<>'2'
             order by t.PARAMSEQ;

        --核付清單
        Cursor c_dataCur_rpt31(v_payTyp varChar2) is
            select t1.APNO                                                                      --受理編號
                  ,t1.SEQNO                                                                     --受款人序
                  ,t1.PAYKIND                                                                   --給付種類
                  ,t1.INSKD                                                                     --保險別
                  ,t1.ISSUYM as ORIISSUYM                                                       --(原)核定年月
                  ,t1.ISSUYM                                                                    --核定年月
                  ,t1.PAYYM                                                                     --給付年月
                  ,t1.CHKDATE                                                                   --核定日期
                  ,t1.APLPAYDATE                                                                --核付日期
                  ,t1.CASETYP                                                                   --案件類別
                  ,t1.ISSUTYP                                                                   --核付分類
                  ,t1.BENEVTREL                                                                 --受益人與事故者關係
                  ,t1.BENIDS                                                                    --受益人社福識別碼
                  ,t1.BENIDN                                                                    --受益人身分證號
                  ,t1.BENNAME                                                                   --受款人姓名
                  ,t1.SOURCEPAYTYP                                                              --給付方式(核付前)
                  ,t1.PAYTYP                                                                    --給付方式
                  ,t1.ACCIDN                                                                    --戶名IDN
                  ,t1.ACCNAME                                                                   --戶名
                  ,t1.PAYBANKID                                                                 --金融機構總代號
                  ,t1.BRANCHID                                                                  --分支代號
                  ,t1.PAYEEACC                                                                  --銀行帳號
                  ,t1.ISSUEAMT                                                                  --核定金額
                  ,t1.APLPAYAMT                                                                 --實付金額
                  ,t1.PAYRATE                                                                   --匯款匯費
                  ,t1.NLWKMK                                                                    --普職註記
                  ,t1.ADWKMK                                                                    --加職註記
                  ,t1.NACHGMK                                                                   --普職互改註記
              from BAISSUDATATEMP t1
             where t1.APNO like (v_i_paycode||'%')
               and t1.SEQNO <> '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and t1.PAYTYP = v_payTyp
               and t1.PAYTYP not in ('1','2')
               and (t1.ISSUEAMT >0
                 or t1.APLPAYAMT >0)
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_3_S';
            v_payType := '';
            v_g_genFlag := 0;
            v_g_flag    := '0';
            for v_dataCur3 in c_dataCur_rpt3 Loop
                v_g_rowCount := 0;
                v_payType := v_dataCur3.PARAMCODE;

                for v_dataCur31 in c_dataCur_rpt31(v_payType) Loop
                    v_g_genFlag := 1;
                    v_g_rowCount := v_g_rowCount + 1;

                    --寫入核付清單資料
                    insert into BAPAYRPTRECORD (RPTTYP
                                               ,CPRNDATE
                                               ,PAYDATE
                                               ,CHKDATE
                                               ,APNO
                                               ,SEQNO
                                               ,PAYCODE
                                               ,PAYKIND
                                               ,ISSUTYP
                                               ,CASETYP
                                               ,ISSUYM
                                               ,PAYYM
                                               ,PAYTYP
                                               ,SOURCEPAYTYP
                                               ,BENEVTREL
                                               ,BENIDS
                                               ,BENIDN
                                               ,BENNAME
                                               ,PAYBANKID
                                               ,BRANCHID
                                               ,PAYEEACC
                                               ,ACCIDN
                                               ,ACCNAME
                                               ,ISSUEAMT
                                               ,PAYAMT
                                               ,PROCTIME
                                               ,PAYSEQNO
                                               ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                               ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                               ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                              ) values (
                                                '3'
                                               ,v_i_cprndt
                                               ,v_dataCur31.APLPAYDATE
                                               ,v_dataCur31.CHKDATE
                                               ,v_dataCur31.APNO
                                               ,v_dataCur31.SEQNO
                                               ,v_i_paycode
                                               ,v_dataCur31.PAYKIND
                                               ,v_dataCur31.ISSUTYP
                                               ,v_dataCur31.CASETYP
                                               ,v_i_issuym
                                               ,v_dataCur31.PAYYM
                                               ,v_dataCur31.PAYTYP
                                               ,v_dataCur31.SOURCEPAYTYP
                                               ,v_dataCur31.BENEVTREL
                                               ,v_dataCur31.BENIDS
                                               ,v_dataCur31.BENIDN
                                               ,v_dataCur31.BENNAME
                                               ,v_dataCur31.PAYBANKID
                                               ,v_dataCur31.BRANCHID
                                               ,v_dataCur31.PAYEEACC
                                               ,v_dataCur31.ACCIDN
                                               ,v_dataCur31.ACCNAME
                                               ,v_dataCur31.ISSUEAMT
                                               ,v_dataCur31.APLPAYAMT
                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                               ,v_i_payseqno
                                               ,v_dataCur31.NLWKMK
                                               ,v_dataCur31.ADWKMK
                                               ,v_dataCur31.NACHGMK
                    );

                    Update BADAPR t set t.CPRNDATE = v_i_cprndt
                                  where t.APNO = v_dataCur31.APNO
                                    and t.SEQNO = v_dataCur31.SEQNO
                                    and t.ISSUYM = v_i_issuym
                                    and t.PAYYM = v_dataCur31.PAYYM
                                    and t.PAYKIND = v_dataCur31.PAYKIND
                                    and t.BENEVTREL = v_dataCur31.BENEVTREL
                                    and t.CHKDATE = v_i_chkdate;
                end Loop;
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>核付清單(遺屬)-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>核付清單(遺屬)-['||v_i_paycode||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
             '核付清單資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
             v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>核付清單(遺屬)-['||v_i_paycode||']:'||v_g_errMsg);

                     --修改log作法 by TseHua 20180419
                      sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>核付清單(遺屬)-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_3_S End


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_4
        PURPOSE:         產生核付明細表

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_4 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --核付明細表
        Cursor c_dataCur_rpt4 is
            select t1.APNO                                                                      --受理編號
                  ,t1.SEQNO                                                                     --受款人序
                  ,t1.PAYKIND                                                                   --給付種類
                  ,t1.INSKD                                                                     --保險別
                  ,t1.ISSUYM as ORIISSUYM                                                       --(原)核定年月
                  ,t1.ISSUYM                                                                    --核定年月
                  ,t1.PAYYM                                                                     --給付年月
                  ,t1.CHKDATE                                                                   --核定日期
                  ,t1.APLPAYDATE                                                                --核付日期
                  ,t1.CASETYP                                                                   --案件類別
                  ,t1.ISSUTYP                                                                   --核付分類
                  ,t1.BENEVTREL                                                                 --受益人與事故者關係
                  ,t1.BENIDS                                                                    --受益人社福識別碼
                  ,t1.BENIDN                                                                    --受益人身分證號
                  ,t1.BENNAME                                                                   --受款人姓名
                  ,t1.SOURCEPAYTYP                                                              --給付方式(核付前)
                  ,t1.PAYTYP                                                                    --給付方式
                  ,t1.ACCIDN                                                                    --戶名IDN
                  ,t1.ACCNAME                                                                   --戶名
                  ,t1.PAYBANKID                                                                 --金融機構總代號
                  ,t1.BRANCHID                                                                  --分支代號
                  ,t1.PAYEEACC                                                                  --銀行帳號
                  ,t1.ISSUEAMT                                                                  --核定金額
                  ,t1.APLPAYAMT                                                                 --實付金額
                  ,t1.PAYRATE                                                                   --匯款匯費
                  ,t1.PAYSEQNO                                                                  --指定核付序號       2012/02/25  Ads By Chungyu
                  ,t1.NLWKMK                                                                    --普職註記
                  ,t1.ADWKMK                                                                    --加職註記
                  ,t1.NACHGMK                                                                   --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
             where t1.APNO like (v_i_paycode||'%')
             -- and t1.SEQNO = '0000'                                                           -- 2012/07/11 Mark By 納入死亡結案 ChungYu
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and t1.PAYTYP in ('1','2')
               and (t1.ISSUEAMT >0
                 or t1.APLPAYAMT >0)
               and t1.BENEVTREL in ('1','2','3','4','5','6','7')                                --20120717 add by Angela
               and t1.PAYSEQNO = v_i_payseqno                                                   -- 2012/02/22  Add by Chungyu
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        --核付明細表 (補償金資料)
        Cursor c_dataCur_rptZ4 is
            select distinct
                   t1.APNO                                                                      --受理編號
                  ,t1.SEQNO                                                                     --受款人序
                  ,t1.PAYKIND                                                                   --給付種類
                  ,t1.INSKD                                                                     --保險別
                  ,t1.ISSUYM as ORIISSUYM                                                       --(原)核定年月
                  ,t1.ISSUYM                                                                    --核定年月
                  ,t1.PAYYM                                                                     --給付年月
                  ,t1.CHKDATE                                                                   --核定日期
                  ,t1.APLPAYDATE                                                                --核付日期
                  ,t1.CASETYP                                                                   --案件類別
                  ,t1.ISSUTYP                                                                   --核付分類
                  ,t2.BENEVTREL                                                                 --受益人與事故者關係
                  ,t1.BENIDS                                                                    --受益人社福識別碼
                  ,t1.BENIDN                                                                    --受益人身分證號
                  ,t1.BENNAME                                                                   --受款人姓名
                  ,t2.SOURCEPAYTYP                                                              --給付方式(核付前)
                  ,t2.PAYTYP                                                                    --給付方式
                  ,t2.ACCIDN                                                                    --戶名IDN
                  ,t2.ACCNAME                                                                   --戶名
                  ,t1.PAYBANKID                                                                 --金融機構總代號
                  ,t1.BRANCHID                                                                  --分支代號
                  ,t1.PAYEEACC                                                                  --銀行帳號
                  ,t1.COMPENAMT as APLPAYAMT                                                    --實付金額(代扣補償金)
                  ,t1.COMPENAMT                                                                 --代扣補償金
                  ,t2.COMPID1                                                                   --代扣補償金單位ID
                  ,t2.COMPNAME1                                                                 --代扣補償金單位名稱
                  ,t4.UBNO as COMPID2                                                           --代算單位ID
                  ,t4.UNAME as COMPNAME2                                                        --代算單位名稱
                  ,t1.PAYSEQNO                                                                  --指定核付序號       2012/02/25  Ads By Chungyu
                  ,t1.NLWKMK                                                                    --普職註記
                  ,t1.ADWKMK                                                                    --加職註記
                  ,t1.NACHGMK                                                                   --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.ISSUYM
                          ,t21.PAYYM
                          ,t21.BENIDN as ACCIDN
                          ,t22.GVNAME as ACCNAME
                          ,t21.BENIDN as COMPID1
                          ,replace(trim(t22.GVNAME),'　','') as COMPNAME1
                          ,t21.BENEVTREL
                          ,t21.PAYTYP
                          ,t21.SOURCEPAYTYP
                      from BAISSUDATATEMP t21,BCCMF08 t22
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.BENEVTREL = 'Z'
                       and t21.BENIDN = t22.GVCD1
                       and t21.SEQNO <> '0000'
                       and t21.ISSUYM = v_i_issuym
                       and t21.PAYTYP in ('1','2')
                       and t21.CHKDATE = v_i_chkdate
                       and (t21.APLPAYDATE is not null and nvl(trim(t21.APLPAYDATE),' ')<>' ')
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
               and t2.PAYTYP in ('1','2')
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
               and t1.PAYSEQNO = v_i_payseqno                                      -- 2012/02/22  Add by Chungyu
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_4';
            v_g_genFlag := 0;
            v_g_flag := '0';
            for v_dataCur4 in c_dataCur_rpt4 Loop
                v_g_genFlag := 1;

                --寫入核付明細表資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,BENEVTREL
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ACCIDN
                                           ,ACCNAME
                                           ,ISSUEAMT
                                           ,PAYAMT
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK
                                          ) values (
                                            '4'
                                           ,v_i_cprndt
                                           ,v_dataCur4.APLPAYDATE
                                           ,v_dataCur4.CHKDATE
                                           ,v_dataCur4.APNO
                                           ,v_dataCur4.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur4.PAYKIND
                                           ,v_dataCur4.ISSUTYP
                                           ,v_dataCur4.CASETYP
                                           ,v_i_issuym
                                           ,v_dataCur4.PAYYM
                                           ,v_dataCur4.PAYTYP
                                           ,v_dataCur4.SOURCEPAYTYP
                                           ,v_dataCur4.BENEVTREL
                                           ,v_dataCur4.BENIDS
                                           ,v_dataCur4.BENIDN
                                           ,v_dataCur4.BENNAME
                                           ,v_dataCur4.PAYBANKID
                                           ,v_dataCur4.BRANCHID
                                           ,v_dataCur4.PAYEEACC
                                           ,v_dataCur4.ACCIDN
                                           ,v_dataCur4.ACCNAME
                                           ,v_dataCur4.ISSUEAMT
                                           ,v_dataCur4.APLPAYAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur4.PAYSEQNO
                                           ,v_dataCur4.NLWKMK
                                           ,v_dataCur4.ADWKMK
                                           ,v_dataCur4.NACHGMK
                );

                Update BADAPR t set t.CPRNDATE = v_i_cprndt
                              where t.APNO = v_dataCur4.APNO
                                and t.SEQNO = v_dataCur4.SEQNO
                                and t.ISSUYM = v_i_issuym
                                and t.PAYYM = v_dataCur4.PAYYM
                                and t.PAYKIND = v_dataCur4.PAYKIND
                                and t.BENEVTREL = v_dataCur4.BENEVTREL
                                and t.CHKDATE = v_i_chkdate;
            end Loop;

            for v_dataCurZ4 in c_dataCur_rptZ4 Loop
                v_g_genFlag := 1;

                --寫入核付明細表 (補償金資料)
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,BENEVTREL
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ACCIDN
                                           ,ACCNAME
                                           ,PAYAMT
                                           ,COMPENAMT
                                           ,COMPID1
                                           ,COMPNAME1
                                           ,COMPID2
                                           ,COMPNAME2
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                          ) values (
                                            '4'
                                           ,v_i_cprndt
                                           ,v_dataCurZ4.APLPAYDATE
                                           ,v_dataCurZ4.CHKDATE
                                           ,v_dataCurZ4.APNO
                                           ,v_dataCurZ4.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCurZ4.PAYKIND
                                           ,v_dataCurZ4.ISSUTYP
                                           ,v_dataCurZ4.CASETYP
                                           ,v_i_issuym
                                           ,v_dataCurZ4.PAYYM
                                           ,v_dataCurZ4.PAYTYP
                                           ,v_dataCurZ4.SOURCEPAYTYP
                                           ,v_dataCurZ4.BENEVTREL
                                           ,v_dataCurZ4.BENIDS
                                           ,v_dataCurZ4.BENIDN
                                           ,v_dataCurZ4.BENNAME
                                           ,v_dataCurZ4.PAYBANKID
                                           ,v_dataCurZ4.BRANCHID
                                           ,v_dataCurZ4.PAYEEACC
                                           ,v_dataCurZ4.ACCIDN
                                           ,v_dataCurZ4.ACCNAME
                                           ,v_dataCurZ4.APLPAYAMT
                                           ,v_dataCurZ4.COMPENAMT
                                           ,v_dataCurZ4.COMPID1
                                           ,v_dataCurZ4.COMPNAME1
                                           ,v_dataCurZ4.COMPID2
                                           ,v_dataCurZ4.COMPNAME2
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCurZ4.PAYSEQNO
                                           ,v_dataCurZ4.NLWKMK
                                           ,v_dataCurZ4.ADWKMK
                                           ,v_dataCurZ4.NACHGMK
                );

                Update BADAPR t set t.CPRNDATE = v_i_cprndt
                              where t.APNO = v_dataCurZ4.APNO
                                and t.SEQNO = v_dataCurZ4.SEQNO
                                and t.ISSUYM = v_i_issuym
                                and t.PAYYM = v_dataCurZ4.PAYYM
                                and t.PAYKIND = v_dataCurZ4.PAYKIND
                                and t.BENEVTREL = v_dataCurZ4.BENEVTREL
                                and t.CHKDATE = v_i_chkdate;
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>核付明細表-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>核付明細表-['||v_i_paycode||']-NoData');
            end if;

             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
           '核付明細表處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>核付明細表-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                   sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                	 RPAD('**Err:'||v_g_ProgName,85,'-')||'>>核付明細表-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                    v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_4 End


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_4_S
        PURPOSE:         產生核付明細表

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_4_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --核付明細表
        Cursor c_dataCur_rpt4 is
            select t1.APNO                                                                      --受理編號
                  ,t1.SEQNO                                                                     --受款人序
                  ,t1.PAYKIND                                                                   --給付種類
                  ,t1.INSKD                                                                     --保險別
                  ,t1.ISSUYM as ORIISSUYM                                                       --(原)核定年月
                  ,t1.ISSUYM                                                                    --核定年月
                  ,t1.PAYYM                                                                     --給付年月
                  ,t1.CHKDATE                                                                   --核定日期
                  ,t1.APLPAYDATE                                                                --核付日期
                  ,t1.CASETYP                                                                   --案件類別
                  ,t1.ISSUTYP                                                                   --核付分類
                  ,t1.BENEVTREL                                                                 --受益人與事故者關係
                  ,t1.BENIDS                                                                    --受益人社福識別碼
                  ,t1.BENIDN                                                                    --受益人身分證號
                  ,t1.BENNAME                                                                   --受款人姓名
                  ,t1.SOURCEPAYTYP                                                              --給付方式(核付前)
                  ,t1.PAYTYP                                                                    --給付方式
                  ,t1.ACCIDN                                                                    --戶名IDN
                  ,t1.ACCNAME                                                                   --戶名
                  ,t1.PAYBANKID                                                                 --金融機構總代號
                  ,t1.BRANCHID                                                                  --分支代號
                  ,t1.PAYEEACC                                                                  --銀行帳號
                  ,t1.ISSUEAMT                                                                  --核定金額
                  ,t1.APLPAYAMT                                                                 --實付金額
                  ,t1.PAYRATE                                                                   --匯款匯費
                  ,t1.NLWKMK                                                                    --普職註記
                  ,t1.ADWKMK                                                                    --加職註記
                  ,t1.NACHGMK                                                                   --普職互改註記
              from BAISSUDATATEMP t1
             where t1.APNO like (v_i_paycode||'%')
               and t1.SEQNO <> '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and t1.PAYTYP in ('1','2')
               and (t1.ISSUEAMT >0
                 or t1.APLPAYAMT >0)
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_4_S';
            v_g_genFlag := 0;

            for v_dataCur4 in c_dataCur_rpt4 Loop
                v_g_genFlag := 1;

                --寫入核付明細表資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,BENEVTREL
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ACCIDN
                                           ,ACCNAME
                                           ,ISSUEAMT
                                           ,PAYAMT
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                          ) values (
                                            '4'
                                           ,v_i_cprndt
                                           ,v_dataCur4.APLPAYDATE
                                           ,v_dataCur4.CHKDATE
                                           ,v_dataCur4.APNO
                                           ,v_dataCur4.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur4.PAYKIND
                                           ,v_dataCur4.ISSUTYP
                                           ,v_dataCur4.CASETYP
                                           ,v_i_issuym
                                           ,v_dataCur4.PAYYM
                                           ,v_dataCur4.PAYTYP
                                           ,v_dataCur4.SOURCEPAYTYP
                                           ,v_dataCur4.BENEVTREL
                                           ,v_dataCur4.BENIDS
                                           ,v_dataCur4.BENIDN
                                           ,v_dataCur4.BENNAME
                                           ,v_dataCur4.PAYBANKID
                                           ,v_dataCur4.BRANCHID
                                           ,v_dataCur4.PAYEEACC
                                           ,v_dataCur4.ACCIDN
                                           ,v_dataCur4.ACCNAME
                                           ,v_dataCur4.ISSUEAMT
                                           ,v_dataCur4.APLPAYAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno
                                           ,v_dataCur4.NLWKMK
                                           ,v_dataCur4.ADWKMK
                                           ,v_dataCur4.NACHGMK
                );

                Update BADAPR t set t.CPRNDATE = v_i_cprndt
                              where t.APNO = v_dataCur4.APNO
                                and t.SEQNO = v_dataCur4.SEQNO
                                and t.ISSUYM = v_i_issuym
                                and t.PAYYM = v_dataCur4.PAYYM
                                and t.PAYKIND = v_dataCur4.PAYKIND
                                and t.BENEVTREL = v_dataCur4.BENEVTREL
                                and t.CHKDATE = v_i_chkdate;
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>核付明細表(遺屬)-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>核付明細表(遺屬)-['||v_i_paycode||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
             '核付明細表資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>核付明細表(遺屬)-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                       sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>核付明細表(遺屬)-['||v_i_paycode||']:'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_4_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_5
        PURPOSE:         產生退匯清冊 [退匯核定清單]

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_5 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2

    ) is
        --退匯清冊 [退匯核定清單]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt5 is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO                                                                 --受款人序
                  ,t1.ISSUKIND as PAYKIND                                                   --給付種類
                  ,t1.ORIISSUYM as ISSUYM                                                   --(原)核定年月
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.BRCHKDATE as CHKDATE                                                  --核定日期
                  ,' ' as APLPAYDATE                                                        --核付日期
                  ,t1.ISSUTYP as CASETYP                                                    --案件類別
                  ,t1.ISSUTYP                                                               --核付分類
                  ,'1' as BENEVTREL                                                         --受益人與事故者關係
                  ,t1.BENIDS                                                                --受款人社福識別碼
                  ,t1.BENIDN                                                                --受款人身份證號
                  ,t1.BENNAME                                                               --受款人姓名
                  ,deCode(t1.PAYTYP,'1','1','2','1',t1.PAYTYP) PAYTYP                       --給付方式
                  ,deCode(t1.PAYTYP,'1',(deCode(substr(t1.PAYBANKID,1,3),'700','2','1'))
                                   ,t1.PAYTYP) SOURCEPAYTYP                                 --給付方式(核付前)
                  ,t1.ACCIDN                                                                --戶名IDN
                  ,t1.ACCNAME                                                               --戶名
                  ,t1.PAYBANKID                                                             --金融機構總代號
                  ,t1.BRANCHID                                                              --金融機構分支代號
                  ,t1.PAYEEACC                                                              --銀行帳號
                  ,t1.REMITAMT as BEFISSUEAMT                                               --核定金額
                  ,t1.REMITAMT as APLPAYAMT                                                 --實付金額
                  ,t1.NLWKMK                                                                --普職註記
                  ,t1.ADWKMK                                                                --加職註記
              from BAPFLBAC t1
             where t1.BRISSUYM = v_i_issuym
               and t1.APNO like (v_i_paycode||'%')
               and t1.PAYDATE = v_i_paydate
               and t1.REMITAMT > 0
               and t1.CPRNDATE = v_i_cprndt                                    --Modify by Angela 20150601
               --and (t1.CPRNDATE is null or nvl(trim(t1.CPRNDATE),' ')=' ')   --Modify by Angela 20150601
               and (t1.RPTPAGE is null or nvl(trim(t1.RPTPAGE),' ')=' ')       --Modify by Angela 20150601
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_5';
            v_g_genFlag := 0;

            for v_dataCur5 in c_dataCur_rpt5 Loop
                v_g_genFlag := 1;

                --寫入退匯清冊 [退匯核定清單]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,BENEVTREL
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ACCIDN
                                           ,ACCNAME
                                           ,ISSUEAMT
                                           ,PAYAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                          ) values (
                                            '5'
                                           ,v_i_cprndt
                                           ,v_dataCur5.APLPAYDATE
                                           ,v_dataCur5.CHKDATE
                                           ,v_dataCur5.APNO
                                           ,v_dataCur5.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur5.PAYKIND
                                           ,v_dataCur5.ISSUTYP
                                           ,v_dataCur5.CASETYP
                                           ,v_dataCur5.ISSUYM
                                           ,v_dataCur5.PAYYM
                                           ,v_dataCur5.PAYTYP
                                           ,v_dataCur5.SOURCEPAYTYP
                                           ,v_dataCur5.BENEVTREL
                                           ,v_dataCur5.BENIDS
                                           ,v_dataCur5.BENIDN
                                           ,v_dataCur5.BENNAME
                                           ,v_dataCur5.PAYBANKID
                                           ,v_dataCur5.BRANCHID
                                           ,v_dataCur5.PAYEEACC
                                           ,v_dataCur5.ACCIDN
                                           ,v_dataCur5.ACCNAME
                                           ,v_dataCur5.BEFISSUEAMT
                                           ,v_dataCur5.APLPAYAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur5.NLWKMK
                                           ,v_dataCur5.ADWKMK
                );

                --Modify by Angela 20150601
                /**
                Update BAPFLBAC t set t.CPRNDATE = v_i_cprndt
                                     ,t.RPTROWS = '1'
                                where t.APNO = v_dataCur5.APNO
                                  and t.SEQNO = v_dataCur5.SEQNO
                                  and t.BRISSUYM = v_i_issuym
                                  and t.PAYDATE = v_i_paydate
                                  and t.PAYYM = v_dataCur5.PAYYM;
                **/
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>退匯清冊-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>退匯清冊-['||v_i_paycode||']-NoData');
            end if;


            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '退匯清冊資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>退匯清冊-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                     sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                     RPAD('**Err:'||v_g_ProgName,85,'-')||'>>退匯清冊-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


        end;
    --Procedure sp_BA_genPayRPT_5 End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_6
        PURPOSE:         產生改匯清冊 [改匯核定清單]

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)

                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_6 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --改匯清冊 [改匯核定清單]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt6 is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO                                                                 --受款人序
                  ,t1.ISSUKIND as PAYKIND                                                   --給付種類
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.RECHKDATE as CHKDATE                                                  --核定日期
                  ,t1.AFPAYDATE as APLPAYDATE                                               --核付日期
                  ,t1.ISSUTYP as CASETYP                                                    --案件類別
                  ,t1.ISSUTYP                                                               --核付分類
                  ,'1' as BENEVTREL                                                         --受益人與事故者關係
                  ,t1.BENIDS                                                                --受款人社福識別碼
                  ,t1.BENIDN                                                                --受款人身份證號
                  ,t1.BENNAME                                                               --受款人姓名
                  ,deCode(t1.PAYTYP,'1','1','2','1',t1.PAYTYP) PAYTYP                       --給付方式
                  ,deCode(t1.PAYTYP,'1',(deCode(substr(t1.PAYBANKID,1,3)
                                              ,'700','2','1'))
                                   ,t1.PAYTYP) SOURCEPAYTYP                                 --給付方式(核付前)
                  ,t1.ACCIDN                                                                --戶名IDN
                  ,t1.ACCNAME                                                               --戶名
                  ,t1.PAYBANKID                                                             --金融機構總代號
                  ,t1.BRANCHID                                                              --金融機構分支代號
                  ,t1.PAYEEACC                                                              --銀行帳號
                  ,t1.REMITAMT as BEFISSUEAMT                                               --核定金額
                  ,t1.REMITAMT as APLPAYAMT                                                 --實付金額
                  ,t1.NLWKMK                                                                --普職註記
                  ,t1.ADWKMK                                                                --加職註記
              from BAREGIVEDTL t1
             where t1.APNO like (v_i_paycode||'%')
               and t1.ISSUYM = v_i_issuym
               and t1.AFMK = '2'
               and nvl(trim(t1.WORKMK),' ') <> 'Y'
               and (t1.AFCHKDATE is not null and nvl(trim(t1.AFCHKDATE),' ')<>' ')
               and (t1.AFPAYDATE is not null and nvl(trim(t1.AFPAYDATE),' ')<>' ')
               and t1.AFPAYDATE = v_i_paydate
               and t1.REMITAMT > 0
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_6';
            v_g_genFlag := 0;
            v_g_flag    := '0';
            for v_dataCur6 in c_dataCur_rpt6 Loop
                v_g_genFlag := 1;

                --寫入改匯清冊 [改匯核定清單]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,BENEVTREL
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ACCIDN
                                           ,ACCNAME
                                           ,ISSUEAMT
                                           ,PAYAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                          ) values (
                                            '6'
                                           ,v_i_cprndt
                                           ,v_dataCur6.APLPAYDATE
                                           ,v_dataCur6.CHKDATE
                                           ,v_dataCur6.APNO
                                           ,v_dataCur6.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur6.PAYKIND
                                           ,v_dataCur6.ISSUTYP
                                           ,v_dataCur6.CASETYP
                                           ,v_i_issuym
                                           ,v_dataCur6.PAYYM
                                           ,v_dataCur6.PAYTYP
                                           ,v_dataCur6.SOURCEPAYTYP
                                           ,v_dataCur6.BENEVTREL
                                           ,v_dataCur6.BENIDS
                                           ,v_dataCur6.BENIDN
                                           ,v_dataCur6.BENNAME
                                           ,v_dataCur6.PAYBANKID
                                           ,v_dataCur6.BRANCHID
                                           ,v_dataCur6.PAYEEACC
                                           ,v_dataCur6.ACCIDN
                                           ,v_dataCur6.ACCNAME
                                           ,v_dataCur6.BEFISSUEAMT
                                           ,v_dataCur6.APLPAYAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur6.NLWKMK
                                           ,v_dataCur6.ADWKMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>改匯清冊-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>改匯清冊-['||v_i_paycode||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '改匯清冊資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
             v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>改匯清冊-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                     sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                     RPAD('**Err:'||v_g_ProgName,85,'-')||'>>改匯清冊-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                      v_g_flag := '1';
                      v_o_flag := v_g_flag;
         end;
    --Procedure sp_BA_genPayRPT_6 End


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_7
        PURPOSE:         產生應收款立帳清冊 [應收款立帳核定清單]

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_7 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --應收款立帳清冊 [應收款立帳核定清單]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt7 is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO                                                                 --受款人序
                  ,t1.PAYKIND                                                               --給付種類
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.CHKDATE                                                               --核定日期
                  ,t1.ISSUTYP                                                               --核付分類
                  ,t1.BENIDS                                                                --受款人社福識別碼
                  ,t1.BENIDNNO                                                              --受款人身份證號
                  ,t1.ISSUEAMT                                                              --核定金額
                  ,t1.UNACPAMT                                                              --應收金額
                  ,t2.BENNAME                                                               --受款人姓名
                  ,t1.NLWKMK                                                                --普職註記
                  ,t1.ADWKMK                                                                --加職註記
                  ,t1.NACHGMK                                                               --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYKIND
                          ,t11.PAYYM
                          ,t11.UNACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.RECAMT as UNACPAMT
                          ,t11.NLWKMK   as NLWKMK
                          ,t11.ADWKMK   as ADWKMK
                          ,t11.NACHGMK  as NACHGMK
                      from BAUNACPDTL t11
                     where t11.APNO like (v_i_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.UNACPDATE = v_i_chkdate) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDNNO
                          ,t21.BENNAME
                      from BAAPPBASE t21
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.PAYKIND = decode(v_i_payseqno,'2','36',t21.PAYKIND)                                --2012/02/29 Add by chungyu
                       --and (t21.PROCSTAT = '50' or ( t21.MANCHKMK  = 'N' And t21.PROCSTAT = '90')) ) t2         --2012/05/28 Add by chungyu
                       and (t21.PROCSTAT = '50' or (trim(t21.MANCHKMK) is not null and t21.PROCSTAT = '90'))) t2  --20131117 Modify by Angela
                where t1.APNO = t2.APNO
                  and t1.SEQNO = t2.SEQNO
                  and t1.BENIDNNO = t2.BENIDNNO
                order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_7';
            v_g_genFlag := 0;
            v_g_flag := '0';
            for v_dataCur7 in c_dataCur_rpt7 Loop
                v_g_genFlag := 1;

                --寫入應收款立帳清冊 [應收款立帳核定清單]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,ISSUEAMT
                                           ,UNACPAMT
                                           ,PROCTIME
                                           ,PAYSEQNO                                        --  2012/02/29  Add By Chungyu
                                           ,NLWKMK                                          -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                          -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK
                                          ) values (
                                            '7'
                                           ,v_i_cprndt
                                           ,v_i_paydate
                                           ,v_dataCur7.CHKDATE
                                           ,v_dataCur7.APNO
                                           ,v_dataCur7.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur7.PAYKIND
                                           ,v_dataCur7.ISSUTYP
                                           ,v_i_issuym
                                           ,v_dataCur7.PAYYM
                                           ,v_dataCur7.BENIDS
                                           ,v_dataCur7.BENIDNNO
                                           ,v_dataCur7.BENNAME
                                           ,v_dataCur7.ISSUEAMT
                                           ,v_dataCur7.UNACPAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno                                    --  2012/02/29  Add By Chungyu
                                           ,v_dataCur7.NLWKMK
                                           ,v_dataCur7.ADWKMK
                                           ,v_dataCur7.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收款立帳清冊-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收款立帳清冊-['||v_i_paycode||']-NoData');
            end if;


            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '應收款立帳清冊資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
             v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收款立帳清冊-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                      sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                      RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收款立帳清冊-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                      v_g_flag := '1';
                      v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_7 End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_7_S
        PURPOSE:         產生應收款立帳清冊 [應收款立帳核定清單]

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_7_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --應收款立帳清冊 [應收款立帳核定清單]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt7 is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO                                                                 --受款人序
                  ,t1.PAYKIND                                                               --給付種類
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.CHKDATE                                                               --核定日期
                  ,t1.ISSUTYP                                                               --核付分類
                  ,t1.BENIDS                                                                --受款人社福識別碼
                  ,t1.BENIDNNO                                                              --受款人身份證號
                  ,t1.ISSUEAMT                                                              --核定金額
                  ,t1.UNACPAMT                                                              --應收金額
                  ,t2.BENNAME                                                               --受款人姓名
                  ,t1.NLWKMK                                                                --普職註記
                  ,t1.ADWKMK                                                                --加職註記
                  ,t1.NACHGMK                                                               --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYKIND
                          ,t11.PAYYM
                          ,t11.UNACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.RECAMT as UNACPAMT
                          ,t11.NLWKMK   as NLWKMK
                          ,t11.ADWKMK   as ADWKMK
                          ,t11.NACHGMK  as NACHGMK
                      from BAUNACPDTL t11
                     where t11.APNO like (v_i_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.UNACPDATE = v_i_chkdate) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENNAME
                          ,t21.BENEVTREL
                      from BAAPPBASE t21
                     where t21.APNO like (v_i_paycode||'%')
                       --and (t21.PROCSTAT = '50' or (t21.MANCHKMK = 'N' and t21.PROCSTAT = '90'))) t2            --20131113 Modify by Angela
                       and (t21.PROCSTAT = '50' or (trim(t21.MANCHKMK) is not null and t21.PROCSTAT = '90'))) t2  --20131117 Modify by Angela
                where t1.APNO = t2.APNO
                  and t1.SEQNO = t2.SEQNO
                  --and t1.BENIDS = t2.BENIDS
                  and t2.SEQNO <> '0000'
                  and t2.BENEVTREL in ('2','3','4','5','6','7','O')
                order by t1.NLWKMK,t1.ADWKMK,t1.NACHGMK,t1.APNO,t1.PAYYM,t1.SEQNO;  --修改Order by條件 20190320 Modify by Angela

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_7_S';
            v_g_genFlag := 0;
            v_g_flag    := '0';
            for v_dataCur7 in c_dataCur_rpt7 Loop
                v_g_genFlag := 1;

                --寫入應收款立帳清冊 [應收款立帳核定清單]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,ISSUEAMT
                                           ,UNACPAMT
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                          ) values (
                                            '7'
                                           ,v_i_cprndt
                                           ,v_i_paydate
                                           ,v_dataCur7.CHKDATE
                                           ,v_dataCur7.APNO
                                           ,v_dataCur7.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur7.PAYKIND
                                           ,v_dataCur7.ISSUTYP
                                           ,v_i_issuym
                                           ,v_dataCur7.PAYYM
                                           ,v_dataCur7.BENIDS
                                           ,v_dataCur7.BENIDNNO
                                           ,v_dataCur7.BENNAME
                                           ,v_dataCur7.ISSUEAMT
                                           ,v_dataCur7.UNACPAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno
                                           ,v_dataCur7.NLWKMK
                                           ,v_dataCur7.ADWKMK
                                           ,v_dataCur7.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收款立帳清冊(遺屬)-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收款立帳清冊(遺屬)-['||v_i_paycode||']-NoData');
            end if;


            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '應收款立帳清冊資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

             v_o_flag  := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收款立帳清冊(遺屬)-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                     sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                      RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收款立帳清冊(遺屬)-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_7_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_8
        PURPOSE:         產生應收已收清冊 [應收已收核定清單]

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)

                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_8 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --應收已收清冊 [應收已收核定清單]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt8 is
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO like (v_i_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       --and t11.TYPEMK <> 'D') t1
                       and t11.TYPEMK in ('0','F')
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDNNO
                          ,t21.BENNAME
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                      from BAAPPBASE t21
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.PAYKIND = decode(v_i_payseqno,'2','36',t21.PAYKIND)                          --2012/02/29 Add by chungyu
                       --and t21.PROCSTAT = '50') t2
                       and (t21.PROCSTAT = '50' or (t21.MANCHKMK is not null and t21.PROCSTAT = '90'))) t2  --20131117 Modify by Angela
                where t1.APNO = t2.APNO
                  and t1.SEQNO = t2.SEQNO
                  --and t1.BENIDS = t2.BENIDS
                  and t1.BENIDNNO = t2.BENIDNNO
             union all
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO like (v_i_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK = 'D'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDN
                          ,t21.BENNAME
                          ,t21.TRANSACTIONID
                          ,t21.TRANSACTIONSEQ
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                      from BAREGIVEDTL t21
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.MK = '4'
                       and nvl(trim(t21.WORKMK),' ') = 'Y'
                       --and t21.AFPAYDATE = v_i_paydate
                       ) t2
                where t1.APNO = t2.APNO
                  and t1.SEQNO = t2.SEQNO
                  --and t1.BENIDS = t2.BENIDS
                  and t1.BENIDNNO = t2.BENIDN
                  and t1.TRANSACTIONID = t2.TRANSACTIONID
                  and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
             union all
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO like (v_i_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK = 'C'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDN
                          ,t21.BENNAME
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                          ,t21.PAYYM
                      from BAPFLBAC t21
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.AFMK = '4'
                       and t21.STEXPNDRECMK = 'A'
                       and t21.STEXPNDRECDATE = v_i_chkdate) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               --and t1.BENIDS = t2.BENIDS
               and t1.PAYYM = t2.PAYYM
               and t1.BENIDNNO = t2.BENIDN
             order by ISSUTYP,APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_8';
            v_g_genFlag := 0;

            for v_dataCur8 in c_dataCur_rpt8 Loop
                v_g_genFlag := 1;

                --寫入應收已收清冊 [應收已收核定清單]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,PAYSEQNO                                 --  2012/02/29  Add BY chungyu
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK
                                          ) values (
                                            '8'
                                           ,v_i_cprndt
                                           ,v_i_paydate
                                           ,v_dataCur8.CHKDATE
                                           ,v_dataCur8.APNO
                                           ,v_dataCur8.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur8.PAYKIND
                                           ,v_dataCur8.ISSUTYP
                                           ,v_i_issuym
                                           ,v_dataCur8.PAYYM
                                           ,v_dataCur8.BENIDS
                                           ,v_dataCur8.BENIDNNO
                                           ,v_dataCur8.BENNAME
                                           ,v_dataCur8.PAYBANKID
                                           ,v_dataCur8.BRANCHID
                                           ,v_dataCur8.PAYEEACC
                                           ,v_dataCur8.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno
                                           ,v_dataCur8.NLWKMK
                                           ,v_dataCur8.ADWKMK
                                           ,v_dataCur8.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊-['||v_i_paycode||']-NoData');
            end if;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '應收已收清冊資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊-['||v_i_paycode||']:'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                    v_g_flag := '1';
                    v_o_flag := v_g_flag;
       end;
    --Procedure sp_BA_genPayRPT_8 End


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_8_S
        PURPOSE:         產生應收已收清冊 [應收已收核定清單]

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_8_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --應收已收清冊 [應收已收核定清單]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt8 is
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO like (v_i_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       --and t11.TYPEMK <> 'D') t1
                       and t11.TYPEMK in ('0','F')
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENNAME
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                          ,t21.BENEVTREL
                      from BAAPPBASE t21
                     where t21.APNO like (v_i_paycode||'%')
                       --and t21.PROCSTAT = '50') t2
                       and (t21.PROCSTAT = '50' or (t21.MANCHKMK is not null and t21.PROCSTAT = '90'))) t2  --20131117 Modify by Angela
                where t1.APNO = t2.APNO
                  and t1.SEQNO = t2.SEQNO
                 -- and t1.BENIDS = t2.BENIDS
                  and t2.SEQNO <> '0000'
                  and t2.BENEVTREL in ('2','3','4','5','6','7','O')
             union all
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO like (v_i_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK = 'D'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENNAME
                          ,t21.TRANSACTIONID
                          ,t21.TRANSACTIONSEQ
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                          ,t22.BENEVTREL
                      from BAREGIVEDTL t21, BAAPPBASE t22
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.APNO = t22.APNO
                       and t21.SEQNO = t22.SEQNO
                       and t21.MK = '4'
                       and nvl(trim(t21.WORKMK),' ') = 'Y'
                       and t21.AFPAYDATE = v_i_paydate) t2
                where t1.APNO = t2.APNO
                  and t1.SEQNO = t2.SEQNO
                --  and t1.BENIDS = t2.BENIDS
                  and t1.TRANSACTIONID = t2.TRANSACTIONID
                  and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
                  and t1.SEQNO <> '0000'
                  and t2.BENEVTREL in ('2','3','4','5','6','7','O')
             union all
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO like (v_i_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK = 'C'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDN
                          ,t21.BENNAME
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                          ,t22.BENEVTREL
                      from BAPFLBAC t21,BAAPPBASE t22
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.AFMK = '4'
                       and t21.STEXPNDRECMK = 'A'
                       and t21.STEXPNDRECDATE = v_i_chkdate) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               --and t1.BENIDS = t2.BENIDS
               and t1.SEQNO <> '0000'
               and t2.BENEVTREL in ('2','3','4','5','6','7','O')
             order by ISSUTYP,NLWKMK,ADWKMK,NACHGMK,APNO,PAYYM,SEQNO;  --修改Order by條件 20190320 Modify by Angela

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_8_S';
            v_g_genFlag := 0;
            v_g_flag    := '0';
            for v_dataCur8 in c_dataCur_rpt8 Loop
                v_g_genFlag := 1;

                --寫入應收已收清冊 [應收已收核定清單]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                          ) values (
                                            '8'
                                           ,v_i_cprndt
                                           ,v_i_paydate
                                           ,v_dataCur8.CHKDATE
                                           ,v_dataCur8.APNO
                                           ,v_dataCur8.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur8.PAYKIND
                                           ,v_dataCur8.ISSUTYP
                                           ,v_i_issuym
                                           ,v_dataCur8.PAYYM
                                           ,v_dataCur8.BENIDS
                                           ,v_dataCur8.BENIDNNO
                                           ,v_dataCur8.BENNAME
                                           ,v_dataCur8.PAYBANKID
                                           ,v_dataCur8.BRANCHID
                                           ,v_dataCur8.PAYEEACC
                                           ,v_dataCur8.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno
                                           ,v_dataCur8.NLWKMK
                                           ,v_dataCur8.ADWKMK
                                           ,v_dataCur8.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊(遺屬)-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊(遺屬)-['||v_i_paycode||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '應收已收清冊資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊(遺屬)-['||v_i_paycode||']:'||v_g_errMsg);

                     --修改log作法 by TseHua 20180419
                      sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                      RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊(遺屬)-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_8_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_9
        PURPOSE:         產生退回現金轉暫收及待結轉清單

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_mtestmk       (varChar2)       --處理註記
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_9 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_mtestmk             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --退回現金轉暫收及待結轉清單
        cursor c_dataCur_rpt9 is
            select t2.APNO                               --年金受理編號
                  ,t2.SEQNO                              --序號
                  ,t2.PAYKIND                            --給付種類
                  ,t2.ISSUYM                             --核定年月
                  ,t2.PAYYM                              --給付年月
                  ,t2.BENNAME                            --受款人姓名
                  ,t2.BENIDN                             --受款人身分證號
                  ,t2.ISSUEAMT                           --金額
                  ,t2.DATAKD                             --資料種類
                  ,t2.OTHERAPNO1                         --他類案件受理編號1
                  ,t2.OTHERSEQNO1                        --他類案件受款人序號1
                  ,t2.HJMK                               --移至註記
                  ,t1.ISSUTYP                            --核定分類
                  ,t1.PAYTYP                             --給付方式
                  ,t1.SOURCEPAYTYP                       --給付方式(核付前)
                  ,t1.BENIDS                             --受益人社福識別碼
                  ,t1.PAYSEQNO                           --指定核付序號       2012/02/25  Ads By Chungyu
                  ,t1.NLWKMK                             --普職註記
                  ,t1.ADWKMK                             --加職註記
                  ,t1.NACHGMK                            --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
                  ,(select t21.OTAPNO as APNO
                          ,t21.SEQNO as SEQNO
                          ,t21.PAYKIND
                          ,t21.ISSUYM
                          ,t21.PAYYM
                          ,t21.GVNAME as BENNAME
                          ,t21.GVIDNNO as BENIDN
                          ,t21.RBAMT as ISSUEAMT
                          ,t21.DATAKD as DATAKD
                          ,t21.APNO as OTHERAPNO1
                          ,t21.HJSEQ as OTHERSEQNO1
                          ,(case t21.Datakd when '9' then deCode(substr(t21.APNO,5,2),'82','8'
                                                                                     ,'84','8'
                                                                                     ,'85','8'
                                                                                     ,'91','8'
                                                                                     ,'92','8',' ')
                            else ' '
                            end) as HJMK
                       from BARBF t21
                      where t21.OTAPNO like (v_i_paycode||'%')
                        and t21.ISSUYM = v_i_issuym
                        and t21.MTESTMK = v_i_mtestmk
                        and t21.DATAKD = '9' ) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.ISSUYM = t2.ISSUYM
               and t1.PAYYM = t2.PAYYM
               and t1.APNO like (v_i_paycode||'%')
               and t1.SEQNO = '0000'
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and t1.OTHERBAMT >0
               and t1.PAYSEQNO = v_i_payseqno                                      -- 2012/02/22  Add by Chungyu
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_9';
            v_g_genFlag := 0;
            v_g_rptPage := 1;

            for v_dataCur9 in c_dataCur_rpt9 Loop
                v_g_genFlag := 1;

                --寫入退回現金轉暫收及待結轉清單資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,RPTPAGE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,ISSUEAMT
                                           ,DATAKD
                                           ,HJMK
                                           ,OTHERAPNO1
                                           ,OTHERSEQNO1
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK
                                          ) values (
                                            '9'
                                           ,v_i_cprndt
                                           ,v_g_rptPage
                                           ,v_i_paydate
                                           ,v_i_chkdate
                                           ,v_dataCur9.APNO
                                           ,v_dataCur9.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur9.PAYKIND
                                           ,v_dataCur9.ISSUTYP
                                           ,v_i_issuym
                                           ,v_dataCur9.PAYYM
                                           ,v_dataCur9.BENIDS
                                           ,v_dataCur9.BENIDN
                                           ,v_dataCur9.BENNAME
                                           ,v_dataCur9.PAYTYP
                                           ,v_dataCur9.SOURCEPAYTYP
                                           ,v_dataCur9.ISSUEAMT
                                           ,v_dataCur9.DATAKD
                                           ,v_dataCur9.HJMK
                                           ,v_dataCur9.OTHERAPNO1
                                           ,v_dataCur9.OTHERSEQNO1
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur9.PAYSEQNO
                                           ,v_dataCur9.NLWKMK
                                           ,v_dataCur9.ADWKMK
                                           ,v_dataCur9.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>退回現金轉暫收及待結轉清單-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>退回現金轉暫收及待結轉清單-['||v_i_paycode||']-NoData');
            end if;


            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '退回現金轉暫收及待結轉清單資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>退回現金轉暫收及待結轉清單-['||v_i_paycode||']:'||v_g_errMsg);


                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>退回現金轉暫收及待結轉清單-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_9 End


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_9_S
        PURPOSE:         產生退回現金轉暫收及待結轉清單

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_mtestmk       (varChar2)       --處理註記
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_9_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_mtestmk             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --退回現金轉暫收及待結轉清單
        cursor c_dataCur_rpt9 is
            select t2.APNO                               --年金受理編號
                  ,t2.SEQNO                              --序號
                  ,t2.PAYKIND                            --給付種類
                  ,t2.ISSUYM                             --核定年月
                  ,t2.PAYYM                              --給付年月
                  ,t2.BENNAME                            --受款人姓名
                  ,t2.BENIDN                             --受款人身分證號
                  ,t2.ISSUEAMT                           --金額
                  ,t2.DATAKD                             --資料種類
                  ,t2.OTHERAPNO1                         --他類案件受理編號1
                  ,t2.OTHERSEQNO1                        --他類案件受款人序號1
                  ,t2.HJMK                               --移至註記
                  ,t1.ISSUTYP                            --核定分類
                  ,t1.PAYTYP                             --給付方式
                  ,t1.SOURCEPAYTYP                       --給付方式(核付前)
                  ,t1.BENIDS                             --受益人社福識別碼
                  ,t1.NLWKMK                             --普職註記
                  ,t1.ADWKMK                             --加職註記
                  ,t1.NACHGMK                            --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
                  ,(select t21.OTAPNO as APNO
                          ,t21.SEQNO as SEQNO
                          ,t21.PAYKIND
                          ,t21.ISSUYM
                          ,t21.PAYYM
                          ,t21.GVNAME as BENNAME
                          ,t21.GVIDNNO as BENIDN
                          ,t21.RBAMT as ISSUEAMT
                          ,t21.DATAKD as DATAKD
                          ,t21.APNO as OTHERAPNO1
                          ,t21.HJSEQ as OTHERSEQNO1
                          ,(case t21.Datakd when '9' then deCode(substr(t21.APNO,5,2),'82','8'
                                                                                     ,'84','8'
                                                                                     ,'85','8'
                                                                                     ,'91','8'
                                                                                     ,'92','8',' ')
                            else ' '
                            end) as HJMK
                       from BARBF t21
                      where t21.OTAPNO like (v_i_paycode||'%')
                        and t21.ISSUYM = v_i_issuym
                        and t21.MTESTMK = v_i_mtestmk
                        and t21.DATAKD = '9' ) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.ISSUYM = t2.ISSUYM
               and t1.PAYYM = t2.PAYYM
               and t1.APNO like (v_i_paycode||'%')
               and t1.SEQNO <> '0000'
               and t1.BENEVTREL in ('2','3','4','5','6','7','O')
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and t1.OTHERBAMT >0
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_9_S';
            v_g_genFlag := 0;
            v_g_rptPage := 1;
            v_g_flag    := '0';
            for v_dataCur9 in c_dataCur_rpt9 Loop
                v_g_genFlag := 1;

                --寫入退回現金轉暫收及待結轉清單資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,RPTPAGE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,ISSUEAMT
                                           ,DATAKD
                                           ,HJMK
                                           ,OTHERAPNO1
                                           ,OTHERSEQNO1
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                          ) values (
                                            '9'
                                           ,v_i_cprndt
                                           ,v_g_rptPage
                                           ,v_i_paydate
                                           ,v_i_chkdate
                                           ,v_dataCur9.APNO
                                           ,v_dataCur9.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur9.PAYKIND
                                           ,v_dataCur9.ISSUTYP
                                           ,v_i_issuym
                                           ,v_dataCur9.PAYYM
                                           ,v_dataCur9.BENIDS
                                           ,v_dataCur9.BENIDN
                                           ,v_dataCur9.BENNAME
                                           ,v_dataCur9.PAYTYP
                                           ,v_dataCur9.SOURCEPAYTYP
                                           ,v_dataCur9.ISSUEAMT
                                           ,v_dataCur9.DATAKD
                                           ,v_dataCur9.HJMK
                                           ,v_dataCur9.OTHERAPNO1
                                           ,v_dataCur9.OTHERSEQNO1
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno
                                           ,v_dataCur9.NLWKMK
                                           ,v_dataCur9.ADWKMK
                                           ,v_dataCur9.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>退回現金轉暫收及待結轉清單(遺屬)-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>退回現金轉暫收及待結轉清單(遺屬)-['||v_i_paycode||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '退回現金轉暫收及待結轉清單資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
             v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>退回現金轉暫收及待結轉清單(遺屬)-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                      sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                      RPAD('**Err:'||v_g_ProgName,85,'-')||'>>退回現金轉暫收及待結轉清單(遺屬)-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_9_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_10
        PURPOSE:         產生勞保年金退回現金清冊資料

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_mtestmk       (varChar2)       --處理註記
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.
        1.1   2024/05/02  William      依據BABAWEB-108修改，增加撈職保一次金及年金資料的條件

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_10 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_mtestmk             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --勞保年金退回現金清冊資料
        cursor c_dataCur_rpt10 is
            select t2.APNO                               --年金受理編號
                  ,t2.SEQNO                              --序號
                  ,t2.PAYKIND                            --給付種類
                  ,t2.ISSUYM                             --核定年月
                  ,t2.PAYYM                              --給付年月
                  ,t2.BENNAME                            --受款人姓名
                  ,t2.BENIDN                             --受款人身分證號
                  ,t2.ISSUEAMT                           --金額
                  ,t2.DATAKD                             --資料種類
                  ,t2.COMMTEL                            --聯絡電話
                  ,t2.OTHERAPNO1                         --他類案件受理編號1
                  ,t2.OTHERSEQNO1                        --他類案件受款人序號1
                  ,t2.HJMK                               --移至註記
                  ,t1.ISSUTYP                            --核定分類
                  ,t1.PAYTYP                             --給付方式
                  ,t1.SOURCEPAYTYP                       --給付方式(核付前)
                  ,t1.BENIDS                             --受益人社福識別碼
                  ,t1.PAYSEQNO                           --指定核付序號       2012/02/25  Ads By Chungyu
                  ,t1.NLWKMK                             --普職註記
                  ,t1.ADWKMK                             --加職註記
                  ,t1.NACHGMK                            --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
                  ,(select tt2.OTAPNO as APNO
                          ,tt2.SEQNO as SEQNO
                          ,tt2.PAYKIND
                          ,tt2.ISSUYM
                          ,tt2.PAYYM
                          ,tt2.GVNAME as BENNAME
                          ,tt2.GVIDNNO as BENIDN
                          ,tt2.RBAMT as ISSUEAMT
                          ,tt2.DATAKD as DATAKD
                          ,tt2.TEL1 as COMMTEL
                          ,tt2.APNO as OTHERAPNO1
                          ,tt2.HJSEQ as OTHERSEQNO1
                          ,(case tt2.Datakd when '1' then deCode(substr(tt2.APNO,5,2),'11','1'
                                                                                     ,'21','2'
                                                                                     ,'31','3'
                                                                                     ,'39','3'
                                                                                     ,'41','4'
                                                                                     ,'49','4'
                                                                                     ,'51','5'
                                                                                     ,'52','5'
                                                                                     ,'61','6',' ')
                                            when '9' then deCode(substr(tt2.APNO,5,2),'82','8'
                                                                                     ,'84','8'
                                                                                     ,'85','8'
                                                                                     ,'91','8'
                                                                                     ,'92','8',' ')
                                            when '2' then deCode(substr(tt2.APNO,5,2),'13','1'
                                                                                     ,'14','1'
                                                                                     ,'33','3'
                                                                                     ,'53','5',' ')
                                            when '3' then deCode(substr(tt2.APNO,5,2),'21','2'
                                                                                     ,'31','3',' ') 
                                            when 'B' then deCode(substr(tt2.APNO,5,2),'71','7'
                                                                                     ,'73','7'
                                                                                     ,'75','7'
                                                                                     ,'72','7'        --2018/01/31  Add By ChugnYu 就保給付新增給付種類72、78
                                                                                     ,'78','7'        --2018/01/31  Add By ChugnYu 就保給付新增給付種類72、78
                         ,'77','7',' ')
                            else ' '
                            end) as HJMK
                       from BARBF tt2
                      where tt2.OTAPNO like (v_i_paycode||'%')
                        and tt2.ISSUYM = v_i_issuym
                        and tt2.MTESTMK = v_i_mtestmk
                        and tt2.DATAKD in ('1','2','3','4','6','7','8','A','B')) t2
               where t1.APNO = t2.APNO
                 and t1.SEQNO = t2.SEQNO
                 and t1.ISSUYM = t2.ISSUYM
                 and t1.PAYYM = t2.PAYYM
                 and t1.PAYKIND = t2.PAYKIND
                 and t1.CHKDATE = v_i_chkdate
                 and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
                 and t1.PAYSEQNO = v_i_payseqno                                      -- 2012/02/22  Add by Chungyu
               order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_10';
            v_g_genFlag := 0;
            v_g_rptPage := 1;
            v_g_flag := '0';
            for v_dataCur10 in c_dataCur_rpt10 Loop
                v_g_genFlag := 1;

                --寫入勞保年金退回現金清冊資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,RPTPAGE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,ISSUEAMT
                                           ,DATAKD
                                           ,HJMK
                                           ,OTHERAPNO1
                                           ,OTHERSEQNO1
                                           ,COMMTEL
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK
                                          ) values (
                                            '10'
                                           ,v_i_cprndt
                                           ,v_g_rptPage
                                           ,v_i_paydate
                                           ,v_i_chkdate
                                           ,v_dataCur10.APNO
                                           ,v_dataCur10.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur10.PAYKIND
                                           ,v_dataCur10.ISSUTYP
                                           ,v_i_issuym
                                           ,v_dataCur10.PAYYM
                                           ,v_dataCur10.BENIDS
                                           ,v_dataCur10.BENIDN
                                           ,v_dataCur10.BENNAME
                                           ,v_dataCur10.PAYTYP
                                           ,v_dataCur10.SOURCEPAYTYP
                                           ,v_dataCur10.ISSUEAMT
                                           ,v_dataCur10.DATAKD
                                           ,v_dataCur10.HJMK
                                           ,v_dataCur10.OTHERAPNO1
                                           ,v_dataCur10.OTHERSEQNO1
                                           ,v_dataCur10.COMMTEL
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur10.PAYSEQNO
                                           ,v_dataCur10.NLWKMK
                                           ,v_dataCur10.ADWKMK
                                           ,v_dataCur10.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>勞保年金退回現金清冊-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>勞保年金退回現金清冊-['||v_i_paycode||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
           '勞保年金退回現金清冊資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
             v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>勞保年金退回現金清冊-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                     sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                     RPAD('**Err:'||v_g_ProgName,85,'-')||'>>勞保年金退回現金清冊-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
    end;
    --Procedure sp_BA_genPayRPT_10 End


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_10_S
        PURPOSE:         產生勞保年金退回現金清冊資料

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_mtestmk       (varChar2)       --處理註記
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.
        1.1   2024/05/02  William      依據BABAWEB-108修改，增加撈職保一次金及年金資料的條件

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_10_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_mtestmk             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is
        --勞保年金退回現金清冊資料
        cursor c_dataCur_rpt10 is
            select t2.APNO                               --年金受理編號
                  ,t2.SEQNO                              --序號
                  ,t2.PAYKIND                            --給付種類
                  ,t2.ISSUYM                             --核定年月
                  ,t2.PAYYM                              --給付年月
                  ,t2.BENNAME                            --受款人姓名
                  ,t2.BENIDN                             --受款人身分證號
                  ,t2.ISSUEAMT                           --金額
                  ,t2.DATAKD                             --資料種類
                  ,t2.COMMTEL                            --聯絡電話
                  ,t2.OTHERAPNO1                         --他類案件受理編號1
                  ,t2.OTHERSEQNO1                        --他類案件受款人序號1
                  ,t2.HJMK                               --移至註記
                  ,t1.ISSUTYP                            --核定分類
                  ,t1.PAYTYP                             --給付方式
                  ,t1.SOURCEPAYTYP                       --給付方式(核付前)
                  ,t1.BENIDS                             --受益人社福識別碼
                  ,t1.NLWKMK                             --普職註記
                  ,t1.ADWKMK                             --加職註記
                  ,t1.NACHGMK                            --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
                  ,(select tt2.OTAPNO as APNO
                          ,tt2.SEQNO as SEQNO
                          ,tt2.PAYKIND
                          ,tt2.ISSUYM
                          ,tt2.PAYYM
                          ,tt2.GVNAME as BENNAME
                          ,tt2.GVIDNNO as BENIDN
                          ,tt2.RBAMT as ISSUEAMT
                          ,tt2.DATAKD as DATAKD
                          ,tt2.TEL1 as COMMTEL
                          ,tt2.APNO as OTHERAPNO1
                          ,tt2.HJSEQ as OTHERSEQNO1
                          ,(case tt2.Datakd when '1' then deCode(substr(tt2.APNO,5,2),'11','1'
                                                                                     ,'21','2'
                                                                                     ,'31','3'
                                                                                     ,'39','3'
                                                                                     ,'41','4'
                                                                                     ,'49','4'
                                                                                     ,'51','5'
                                                                                     ,'52','5'
                                                                                     ,'61','6',' ')
                                            when '9' then deCode(substr(tt2.APNO,5,2),'82','8'
                                                                                     ,'84','8'
                                                                                     ,'85','8'
                                                                                     ,'91','8'
                                                                                     ,'92','8',' ')
                                            when '2' then deCode(substr(tt2.APNO,5,2),'13','1'
                                                                                     ,'14','1'
                                                                                     ,'33','3'
                                                                                     ,'53','5',' ')
                                            when '3' then deCode(substr(tt2.APNO,5,2),'21','2'
                                                                                     ,'31','3',' ') 
                                            when 'B' then deCode(substr(tt2.APNO,5,2),'71','7'
                                                                                     ,'73','7'
                                                                                     ,'75','7'
                                                                                     ,'72','7'        --2018/01/31  Add By ChugnYu 就保給付新增給付種類72、78
                                                                                     ,'78','7'        --2018/01/31  Add By ChugnYu 就保給付新增給付種類72、78
                         ,'77','7',' ')
                            else ' '
                            end) as HJMK
                       from BARBF tt2
                      where tt2.OTAPNO like (v_i_paycode||'%')
                        and tt2.ISSUYM = v_i_issuym
                        and tt2.MTESTMK = v_i_mtestmk
                        and tt2.DATAKD in ('1','2','3','4','6','7','8','A','B')) t2
               where t1.APNO = t2.APNO
                 and t1.SEQNO = t2.SEQNO
                 and t1.ISSUYM = t2.ISSUYM
                 and t1.PAYYM = t2.PAYYM
                 and t1.APNO like (v_i_paycode||'%')
                 --2012/08/28 Mark By ChungYu 退回現金清冊必須納入事故者
                 --and t1.SEQNO <> '0000'
                 --and t1.BENEVTREL in ('2','3','4','5','6','7')
                 and t1.CHKDATE = v_i_chkdate
                 and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_10_S';
            v_g_genFlag := 0;
            v_g_rptPage := 1;
            v_g_flag    := '0';
            for v_dataCur10 in c_dataCur_rpt10 Loop
                v_g_genFlag := 1;

                --寫入勞保年金退回現金清冊資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,RPTPAGE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,ISSUEAMT
                                           ,DATAKD
                                           ,HJMK
                                           ,OTHERAPNO1
                                           ,OTHERSEQNO1
                                           ,COMMTEL
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                          ) values (
                                            '10'
                                           ,v_i_cprndt
                                           ,v_g_rptPage
                                           ,v_i_paydate
                                           ,v_i_chkdate
                                           ,v_dataCur10.APNO
                                           ,v_dataCur10.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur10.PAYKIND
                                           ,v_dataCur10.ISSUTYP
                                           ,v_i_issuym
                                           ,v_dataCur10.PAYYM
                                           ,v_dataCur10.BENIDS
                                           ,v_dataCur10.BENIDN
                                           ,v_dataCur10.BENNAME
                                           ,v_dataCur10.PAYTYP
                                           ,v_dataCur10.SOURCEPAYTYP
                                           ,v_dataCur10.ISSUEAMT
                                           ,v_dataCur10.DATAKD
                                           ,v_dataCur10.HJMK
                                           ,v_dataCur10.OTHERAPNO1
                                           ,v_dataCur10.OTHERSEQNO1
                                           ,v_dataCur10.COMMTEL
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_i_payseqno
                                           ,v_dataCur10.NLWKMK
                                           ,v_dataCur10.ADWKMK
                                           ,v_dataCur10.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>勞保年金退回現金清冊(遺屬)-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>勞保年金退回現金清冊(遺屬)-['||v_i_paycode||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '勞保年金退回現金清冊資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            v_o_flag := v_g_flag;

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>勞保年金退回現金清冊(遺屬)-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>勞保年金退回現金清冊(遺屬)-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
    end;
    --Procedure sp_BA_genPayRPT_10_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_11
        PURPOSE:         產生代扣補償金清冊

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2010/10/01  Kiyomi       Created this procedure.
        1.1   2023/03/09  William      依據babaweb-68修改，增加查詢的條件，
                                       只產製已同意代扣之補償金單位
        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_11 (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is

        --代扣補償金清冊
        Cursor c_dataCur_rptZ11 is
           select distinct
                   t1.APNO                                                                      --受理編號
                  ,t1.SEQNO                                                                     --受款人序
                  ,t1.PAYKIND                                                                   --給付種類
                  ,t1.ISSUYM                                                                    --核定年月
                  ,t1.PAYYM                                                                     --給付年月
                  ,t1.CHKDATE                                                                   --核定日期
                  ,t1.APLPAYDATE                                                                --核付日期
                  ,t1.CASETYP                                                                   --案件類別
                  ,t1.ISSUTYP                                                                   --核付分類
                  ,t2.BENEVTREL                                                                 --受益人與事故者關係
                  ,t1.BENIDS                                                                    --受益人社福識別碼
                  ,t1.BENIDN                                                                    --受益人身分證號
                  ,t1.BENNAME                                                                   --受款人姓名
                  ,t2.SOURCEPAYTYP                                                              --給付方式(核付前)
                  ,t2.PAYTYP                                                                    --給付方式
                  ,t2.ACCIDN                                                                    --戶名IDN
                  ,t2.ACCNAME                                                                   --戶名
                  ,t2.PAYBANKID                                                                 --金融機構總代號
                  ,t2.BRANCHID                                                                  --分支代號
                  ,t2.PAYEEACC                                                                  --銀行帳號
                  ,t1.COMPENAMT as ISSUEAMT                                                     --核定金額(代扣補償金)
                  ,t1.COMPENAMT as APLPAYAMT                                                    --實付金額(代扣補償金)
                  ,t1.COMPENAMT                                                                 --代扣補償金
                  ,t2.COMPID1                                                                   --代扣補償金單位ID
                  ,t2.COMPNAME1                                                                 --代扣補償金單位名稱
                  ,t4.UBNO as COMPID2                                                           --代算單位ID
                  ,t4.UNAME as COMPNAME2                                                        --代算單位名稱
                  ,t1.PAYSEQNO                                                                  --指定核付序號       2012/02/25  Ads By Chungyu
              --    ,t1.NACHGMK                                                                   --普職互改註記       2012/02/25  Ads By Chungyu
              from BAISSUDATATEMP t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.ISSUYM
                          ,t21.PAYYM
                          ,t21.BENIDN as ACCIDN
                          ,t22.GVNAME as ACCNAME
                          ,t21.BENIDN as COMPID1
                          ,replace(trim(t22.GVNAME),'　','') as COMPNAME1
                          ,t21.BENEVTREL
                          ,t21.PAYTYP
                          ,t21.SOURCEPAYTYP
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                      from BAISSUDATATEMP t21,BCCMF08 t22
                     where t21.APNO like (v_i_paycode||'%')
                       and t21.BENEVTREL = 'Z'
                       and t21.BENIDN = t22.GVCD1
                       and t21.SEQNO <> '0000'
                       and t21.ISSUYM = v_i_issuym
                       and t21.PAYTYP in ('1','2','3')
                       and t21.CHKDATE = v_i_chkdate
                       and (t21.APLPAYDATE is not null and nvl(trim(t21.APLPAYDATE),' ')<>' ')
                   ) t2
                   ,(SELECT b.* from
                      (select apno ,benidnno from baappbase where seqno='0000') a1,
                      (select apno, oldapldpt from baappbase where  seqno<>'0000' and benevtrel='Z') a2,
                     PBBMSA b
                      where  a1.benidnno = b.BMEVIDNO
                      and a2.oldapldpt=b.bmoldapldpt
                      AND a1.apno = a2.apno ) t3  --20230309 babaweb-68
                  ,CAUB t4
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
               and t2.PAYTYP in ('1','2','3')
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
               and t1.PAYSEQNO = v_i_payseqno                                      -- 2012/02/22  Add by Chungyu
               and t1.NACHGMK is null                                              -- 2012/02/22  Add by Chungyu
             order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_11';
            v_g_genFlag := 0;
            v_g_rptPage := 1;
            v_g_flag    := '0';
            for v_dataCurZ11 in c_dataCur_rptZ11 Loop
                v_g_genFlag := 1;

                --寫入核付後報表清單紀錄檔
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,RPTPAGE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,PAYTYP
                                           ,SOURCEPAYTYP
                                           ,BENEVTREL
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ACCIDN
                                           ,ACCNAME
                                           ,ISSUEAMT
                                           ,PAYAMT
                                           ,COMPENAMT
                                           ,COMPID1
                                           ,COMPNAME1
                                           ,COMPID2
                                           ,COMPNAME2
                                           ,PROCTIME
                                           ,PAYSEQNO
                                       --    ,NACHGMK
                                          ) values (
                                            '11'
                                           ,v_i_cprndt
                                           ,v_g_rptPage
                                           ,v_dataCurZ11.APLPAYDATE
                                           ,v_dataCurZ11.CHKDATE
                                           ,v_dataCurZ11.APNO
                                           ,v_dataCurZ11.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCurZ11.PAYKIND
                                           ,v_dataCurZ11.ISSUTYP
                                           ,v_dataCurZ11.CASETYP
                                           ,v_i_issuym
                                           ,v_dataCurZ11.PAYYM
                                           ,v_dataCurZ11.PAYTYP
                                           ,v_dataCurZ11.SOURCEPAYTYP
                                           ,v_dataCurZ11.BENEVTREL
                                           ,v_dataCurZ11.BENIDS
                                           ,v_dataCurZ11.BENIDN
                                           ,v_dataCurZ11.BENNAME
                                           ,v_dataCurZ11.PAYBANKID
                                           ,v_dataCurZ11.BRANCHID
                                           ,v_dataCurZ11.PAYEEACC
                                           ,v_dataCurZ11.ACCIDN
                                           ,v_dataCurZ11.ACCNAME
                                           ,v_dataCurZ11.ISSUEAMT
                                           ,v_dataCurZ11.APLPAYAMT
                                           ,v_dataCurZ11.COMPENAMT
                                           ,v_dataCurZ11.COMPID1
                                           ,v_dataCurZ11.COMPNAME1
                                           ,v_dataCurZ11.COMPID2
                                           ,v_dataCurZ11.COMPNAME2
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCurZ11.PAYSEQNO
                                        --   ,v_dataCurZ11.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>代扣補償金清冊-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>代扣補償金清冊-['||v_i_paycode||']-NoData');
            end if;

             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '代扣補償金清冊資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>代扣補償金清冊-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                     sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                      RPAD('**Err:'||v_g_ProgName,85,'-')||'>>代扣補償金清冊-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_11 End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_12
        PURPOSE:         產生應收已收清冊(單筆退現收回) [應收已收核定清單(退現收回)]

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受理人序
                        *v_i_acpdate       (varChar2)       --已收日期
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/11  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_12 (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_acpdate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2
    ) is
        --應收已收清冊(單筆退現收回) [應收已收核定清單(退現收回)]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt12 is
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.ISSUYM                                                                         --核定年月
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.ACPDATE = v_i_acpdate
                       and t11.TYPEMK = 'E'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDNNO
                          ,t21.BENNAME
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                      from BAAPPBASE t21
                     where t21.APNO = v_i_apno
                       and t21.SEQNO = v_i_seqno
                       and (t21.PROCSTAT = '50' or (t21.MANCHKMK is not null and t21.PROCSTAT = '90'))) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDNNO
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_12';
            v_g_genFlag := 0;

            for v_dataCur12 in c_dataCur_rpt12 Loop
                v_g_genFlag := 1;

                --寫入應收已收清冊(單筆退現收回) [應收已收核定清單(退現收回)]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                          ) values (
                                            '12'
                                           ,v_i_cprndt
                                           ,v_i_acpdate
                                           ,v_dataCur12.CHKDATE
                                           ,v_dataCur12.APNO
                                           ,v_dataCur12.SEQNO
                                           ,substr(v_i_apno,1,1)
                                           ,v_dataCur12.PAYKIND
                                           ,v_dataCur12.ISSUTYP
                                           ,v_dataCur12.ISSUYM
                                           ,v_dataCur12.PAYYM
                                           ,v_dataCur12.BENIDS
                                           ,v_dataCur12.BENIDNNO
                                           ,v_dataCur12.BENNAME
                                           ,v_dataCur12.PAYBANKID
                                           ,v_dataCur12.BRANCHID
                                           ,v_dataCur12.PAYEEACC
                                           ,v_dataCur12.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur12.NLWKMK
                                           ,v_dataCur12.ADWKMK
                                           ,v_dataCur12.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退現收回)-['||v_i_apno||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退現收回)-['||v_i_apno||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
             '應收已收清冊(單筆退現收回)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退現收回)-['||v_i_apno||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退現收回)-['||v_i_apno||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --Procedure sp_BA_genPayRPT_12 End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_12_S
        PURPOSE:         產生應收已收清冊(單筆退現收回)(for 遺屬) [應收已收核定清單(退現收回)]

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受理人序
                        *v_i_acpdate       (varChar2)       --已收日期
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/11  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_12_S (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_acpdate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2
    ) is
        --應收已收清冊(單筆退現收回)(for 遺屬) [應收已收核定清單(退現收回)]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt12 is
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.ISSUYM                                                                         --核定年月
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.ACPDATE = v_i_acpdate
                       and t11.TYPEMK = 'E'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENNAME
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                          ,t21.BENEVTREL
                      from BAAPPBASE t21
                     where t21.APNO = v_i_apno
                       and t21.SEQNO = v_i_seqno
                       and (t21.PROCSTAT = '50' or (t21.MANCHKMK is not null and t21.PROCSTAT = '90'))) t2
                where t1.APNO = t2.APNO
                  and t1.SEQNO = t2.SEQNO
                  and t2.SEQNO <> '0000'
                  and t2.BENEVTREL in ('2','3','4','5','6','7','O')
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_12_S';
            v_g_genFlag := 0;

            for v_dataCur12 in c_dataCur_rpt12 Loop
                v_g_genFlag := 1;

                --寫入應收已收清冊(單筆退現收回)(for 遺屬) [應收已收核定清單(退現收回)]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                          ) values (
                                            '12'
                                           ,v_i_cprndt
                                           ,v_i_acpdate
                                           ,v_dataCur12.CHKDATE
                                           ,v_dataCur12.APNO
                                           ,v_dataCur12.SEQNO
                                           ,substr(v_i_apno,1,1)
                                           ,v_dataCur12.PAYKIND
                                           ,v_dataCur12.ISSUTYP
                                           ,v_dataCur12.ISSUYM
                                           ,v_dataCur12.PAYYM
                                           ,v_dataCur12.BENIDS
                                           ,v_dataCur12.BENIDNNO
                                           ,v_dataCur12.BENNAME
                                           ,v_dataCur12.PAYBANKID
                                           ,v_dataCur12.BRANCHID
                                           ,v_dataCur12.PAYEEACC
                                           ,v_dataCur12.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur12.NLWKMK
                                           ,v_dataCur12.ADWKMK
                                           ,v_dataCur12.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退現收回)(遺屬)-['||v_i_apno||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退現收回)(遺屬)-['||v_i_apno||']-NoData');
            end if;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
             '應收已收清冊(單筆退現收回)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退現收回)(遺屬)-['||v_i_apno||']:'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                     sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                     RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退現收回)(遺屬)-['||v_i_apno||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --Procedure sp_BA_genPayRPT_12_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_13
        PURPOSE:         產生應收已收清冊(單筆退匯收回) [應收已收核定清單(退匯收回)]

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受理人序
                        *v_i_acpdate       (varChar2)       --已收日期
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/12  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_13 (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_acpdate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2
    ) is
        --應收已收清冊(單筆退匯收回) [應收已收核定清單(退匯收回)]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt13 is
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.ISSUYM                                                                         --核定年月
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.ACPDATE = v_i_acpdate
                       and t11.TYPEMK = 'D'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDN
                          ,t21.BENNAME
                          ,t21.TRANSACTIONID
                          ,t21.TRANSACTIONSEQ
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                      from BAREGIVEDTL t21
                     where t21.APNO = v_i_apno
                       and t21.SEQNO = v_i_seqno
                       and t21.MK = '4'
                       and nvl(trim(t21.WORKMK),' ') = 'Y'
                       and t21.AFPAYDATE = v_i_acpdate) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDN
               and t1.TRANSACTIONID = t2.TRANSACTIONID
               and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_13';
            v_g_genFlag := 0;

            for v_dataCur13 in c_dataCur_rpt13 Loop
                v_g_genFlag := 1;

                --寫入應收已收清冊(單筆退匯收回) [應收已收核定清單(退匯收回)]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                          ) values (
                                            '13'
                                           ,v_i_cprndt
                                           ,v_i_acpdate
                                           ,v_dataCur13.CHKDATE
                                           ,v_dataCur13.APNO
                                           ,v_dataCur13.SEQNO
                                           ,substr(v_i_apno,1,1)
                                           ,v_dataCur13.PAYKIND
                                           ,v_dataCur13.ISSUTYP
                                           ,v_dataCur13.ISSUYM
                                           ,v_dataCur13.PAYYM
                                           ,v_dataCur13.BENIDS
                                           ,v_dataCur13.BENIDNNO
                                           ,v_dataCur13.BENNAME
                                           ,v_dataCur13.PAYBANKID
                                           ,v_dataCur13.BRANCHID
                                           ,v_dataCur13.PAYEEACC
                                           ,v_dataCur13.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur13.NLWKMK
                                           ,v_dataCur13.ADWKMK
                                           ,v_dataCur13.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退匯收回)-['||v_i_apno||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退匯收回)-['||v_i_apno||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '應收已收清冊(單筆退匯收回)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退匯收回)-['||v_i_apno||']:'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退匯收回)-['||v_i_apno||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --Procedure sp_BA_genPayRPT_13 End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_13_S
        PURPOSE:         產生應收已收清冊(單筆退匯收回)(for 遺屬) [應收已收核定清單(退匯收回)]

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受理人序
                        *v_i_acpdate       (varChar2)       --已收日期
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/11  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_13_S (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_acpdate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2
    ) is
        --應收已收清冊(單筆退匯收回)(for 遺屬) [應收已收核定清單(退匯收回)]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt13 is
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.ISSUYM                                                                         --核定年月
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.ACPDATE = v_i_acpdate
                       and t11.TYPEMK = 'D'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENNAME
                          ,t21.TRANSACTIONID
                          ,t21.TRANSACTIONSEQ
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                          ,t22.BENEVTREL
                      from BAREGIVEDTL t21, BAAPPBASE t22
                     where t21.APNO = t22.APNO
                       and t21.SEQNO = t22.SEQNO
                       and t21.APNO = v_i_apno
                       and t21.SEQNO = v_i_seqno
                       and t21.MK = '4'
                       and nvl(trim(t21.WORKMK),' ') = 'Y'
                       and t21.AFPAYDATE = v_i_acpdate) t2
            where t1.APNO = t2.APNO
              and t1.SEQNO = t2.SEQNO
              and t1.TRANSACTIONID = t2.TRANSACTIONID
              and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
              and t1.SEQNO <> '0000'
              and t2.BENEVTREL in ('2','3','4','5','6','7','O')
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_13_S';
            v_g_genFlag := 0;

            for v_dataCur13 in c_dataCur_rpt13 Loop
                v_g_genFlag := 1;

                --寫入應收已收清冊(單筆退匯收回)(for 遺屬) [應收已收核定清單(退匯收回)]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                          ) values (
                                            '13'
                                           ,v_i_cprndt
                                           ,v_i_acpdate
                                           ,v_dataCur13.CHKDATE
                                           ,v_dataCur13.APNO
                                           ,v_dataCur13.SEQNO
                                           ,substr(v_i_apno,1,1)
                                           ,v_dataCur13.PAYKIND
                                           ,v_dataCur13.ISSUTYP
                                           ,v_dataCur13.ISSUYM
                                           ,v_dataCur13.PAYYM
                                           ,v_dataCur13.BENIDS
                                           ,v_dataCur13.BENIDNNO
                                           ,v_dataCur13.BENNAME
                                           ,v_dataCur13.PAYBANKID
                                           ,v_dataCur13.BRANCHID
                                           ,v_dataCur13.PAYEEACC
                                           ,v_dataCur13.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur13.NLWKMK
                                           ,v_dataCur13.ADWKMK
                                           ,v_dataCur13.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退匯收回)(遺屬)-['||v_i_apno||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退匯收回)(遺屬)-['||v_i_apno||']-NoData');
            end if;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '應收已收清冊(單筆退匯收回)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退匯收回)(遺屬)-['||v_i_apno||']:'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收清冊(單筆退匯收回)(遺屬)-['||v_i_apno||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --Procedure sp_BA_genPayRPT_13_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_14
        PURPOSE:         產生應收已收註銷清冊(單筆退現收回註銷) [應收已收註銷核定清單(退現收回註銷)]

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受理人序
                        *v_i_canceldate    (varChar2)       --註銷日期
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/11  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_14 (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_canceldate          in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2
    ) is
        --應收已收註銷清冊(單筆退現收回註銷) [應收已收註銷核定清單(退現收回註銷)]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt14 is
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.ISSUYM                                                                         --核定年月
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.CANCELDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.CANCELDATE = v_i_canceldate
                       and t11.TYPEMK = 'E'
                       and nvl(trim(t11.PROCFUN),' ') = 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDNNO
                          ,t21.BENNAME
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                      from BAAPPBASE t21
                     where t21.APNO = v_i_apno
                       and t21.SEQNO = v_i_seqno
                       and (t21.PROCSTAT = '50' or (t21.MANCHKMK is not null and t21.PROCSTAT = '90'))) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDNNO
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_14';
            v_g_genFlag := 0;

            for v_dataCur14 in c_dataCur_rpt14 Loop
                v_g_genFlag := 1;

                --寫入應收已收註銷清冊(單筆退現收回註銷) [應收已收註銷核定清單(退現收回註銷)]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                          ) values (
                                            '14'
                                           ,v_i_cprndt
                                           ,v_i_canceldate
                                           ,v_dataCur14.CHKDATE
                                           ,v_dataCur14.APNO
                                           ,v_dataCur14.SEQNO
                                           ,substr(v_i_apno,1,1)
                                           ,v_dataCur14.PAYKIND
                                           ,v_dataCur14.ISSUTYP
                                           ,v_dataCur14.ISSUYM
                                           ,v_dataCur14.PAYYM
                                           ,v_dataCur14.BENIDS
                                           ,v_dataCur14.BENIDNNO
                                           ,v_dataCur14.BENNAME
                                           ,v_dataCur14.PAYBANKID
                                           ,v_dataCur14.BRANCHID
                                           ,v_dataCur14.PAYEEACC
                                           ,v_dataCur14.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur14.NLWKMK
                                           ,v_dataCur14.ADWKMK
                                           ,v_dataCur14.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退現收回註銷)-['||v_i_apno||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退現收回註銷)-['||v_i_apno||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
           '應收已收註銷清冊(單筆退現收回註銷)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退現收回註銷)-['||v_i_apno||']:'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退現收回註銷)-['||v_i_apno||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --Procedure sp_BA_genPayRPT_14 End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_14_S
        PURPOSE:         產生應收已收註銷清冊(單筆退現收回註銷)(for 遺屬) [應收已收註銷核定清單(退現收回註銷)]

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受理人序
                        *v_i_canceldate    (varChar2)       --註銷日期
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/11  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_14_S (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_canceldate          in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2
    ) is
        --應收已收註銷清冊(單筆退現收回註銷)(for 遺屬) [應收已收註銷核定清單(退現收回註銷)]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt14 is
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.ISSUYM                                                                         --核定年月
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.CANCELDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.CANCELDATE = v_i_canceldate
                       and t11.TYPEMK = 'E'
                       and nvl(trim(t11.PROCFUN),' ') = 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENNAME
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                          ,t21.BENEVTREL
                      from BAAPPBASE t21
                     where t21.APNO = v_i_apno
                       and t21.SEQNO = v_i_seqno
                       and (t21.PROCSTAT = '50' or (t21.MANCHKMK is not null and t21.PROCSTAT = '90'))) t2
                where t1.APNO = t2.APNO
                  and t1.SEQNO = t2.SEQNO
                  and t2.SEQNO <> '0000'
                  and t2.BENEVTREL in ('2','3','4','5','6','7','O')
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_14_S';
            v_g_genFlag := 0;

            for v_dataCur14 in c_dataCur_rpt14 Loop
                v_g_genFlag := 1;

                --寫入應收已收註銷清冊(單筆退現收回註銷)(for 遺屬) [應收已收註銷核定清單(退現收回註銷)]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                          ) values (
                                            '14'
                                           ,v_i_cprndt
                                           ,v_i_canceldate
                                           ,v_dataCur14.CHKDATE
                                           ,v_dataCur14.APNO
                                           ,v_dataCur14.SEQNO
                                           ,substr(v_i_apno,1,1)
                                           ,v_dataCur14.PAYKIND
                                           ,v_dataCur14.ISSUTYP
                                           ,v_dataCur14.ISSUYM
                                           ,v_dataCur14.PAYYM
                                           ,v_dataCur14.BENIDS
                                           ,v_dataCur14.BENIDNNO
                                           ,v_dataCur14.BENNAME
                                           ,v_dataCur14.PAYBANKID
                                           ,v_dataCur14.BRANCHID
                                           ,v_dataCur14.PAYEEACC
                                           ,v_dataCur14.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur14.NLWKMK
                                           ,v_dataCur14.ADWKMK
                                           ,v_dataCur14.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退現收回註銷)(遺屬)-['||v_i_apno||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退現收回註銷)(遺屬)-['||v_i_apno||']-NoData');
            end if;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '應收已收註銷清冊(單筆退現收回註銷)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退現收回註銷)(遺屬)-['||v_i_apno||']:'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退現收回註銷)(遺屬)-['||v_i_apno||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


        end;
    --Procedure sp_BA_genPayRPT_14_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_15
        PURPOSE:         產生應收已收註銷清冊(單筆退匯收回註銷) [應收已收核定註銷清單(退匯收回註銷)]

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受理人序
                        *v_i_canceldate    (varChar2)       --註銷日期
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/12  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_15 (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_canceldate          in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2
    ) is
        --應收已收註銷清冊(單筆退匯收回註銷) [應收已收註銷核定清單(退匯收回註銷)]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt15 is
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.ISSUYM                                                                         --核定年月
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.CANCELDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.CANCELDATE = v_i_canceldate
                       and t11.TYPEMK = 'D'
                       and nvl(trim(t11.PROCFUN),' ') = 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDN
                          ,t21.BENNAME
                          ,t21.TRANSACTIONID
                          ,t21.TRANSACTIONSEQ
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                      from BAREGIVEDTL t21
                     where t21.APNO = v_i_apno
                       and t21.SEQNO = v_i_seqno
                       and t21.MK = '1'
                       and t21.AFMK = '1'
                       and (nvl(trim(t21.ISSUYM),' ') = ' ' or t21.ISSUYM is null)
                       and (nvl(trim(t21.WORKMK),' ') = ' ' or t21.WORKMK is null)
                       and (nvl(trim(t21.AFPAYDATE),' ') = ' ' or t21.AFPAYDATE is null)) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDN
               and t1.TRANSACTIONID = t2.TRANSACTIONID
               and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_15';
            v_g_genFlag := 0;

            for v_dataCur15 in c_dataCur_rpt15 Loop
                v_g_genFlag := 1;

                --寫入應收已收註銷清冊(單筆退匯收回註銷) [應收已收註銷核定清單(退匯收回註銷)]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                          ) values (
                                            '15'
                                           ,v_i_cprndt
                                           ,v_i_canceldate
                                           ,v_dataCur15.CHKDATE
                                           ,v_dataCur15.APNO
                                           ,v_dataCur15.SEQNO
                                           ,substr(v_i_apno,1,1)
                                           ,v_dataCur15.PAYKIND
                                           ,v_dataCur15.ISSUTYP
                                           ,v_dataCur15.ISSUYM
                                           ,v_dataCur15.PAYYM
                                           ,v_dataCur15.BENIDS
                                           ,v_dataCur15.BENIDNNO
                                           ,v_dataCur15.BENNAME
                                           ,v_dataCur15.PAYBANKID
                                           ,v_dataCur15.BRANCHID
                                           ,v_dataCur15.PAYEEACC
                                           ,v_dataCur15.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur15.NLWKMK
                                           ,v_dataCur15.ADWKMK
                                           ,v_dataCur15.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退匯收回註銷)-['||v_i_apno||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退匯收回註銷)-['||v_i_apno||']-NoData');
            end if;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
             '應收已收註銷清冊(單筆退匯收回註銷)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退匯收回註銷)-['||v_i_apno||']:'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退匯收回註銷)-['||v_i_apno||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --Procedure sp_BA_genPayRPT_15 End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_15_S
        PURPOSE:         產生應收已收註銷清冊(單筆退匯收回註銷)(for 遺屬) [應收已收註銷核定清單(退匯收回註銷)]

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受理人序
                        *v_i_canceldate    (varChar2)       --註銷日期
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/11  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_15_S (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_canceldate          in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2
    ) is
        --應收已收註銷清冊(單筆退匯收回註銷)(for 遺屬) [應收已收註銷核定清單(退匯收回註銷)]
        --注意!! 若有異動條件，需一併更改 PKG_BA_genPFMPFD 對應的 Procedure 中的條件
        cursor c_dataCur_rpt15 is
            select t1.APNO                                                                           --受理編號
                  ,t1.SEQNO                                                                          --受款人序
                  ,t1.PAYKIND                                                                        --給付種類
                  ,t1.ISSUYM                                                                         --核定年月
                  ,t1.PAYYM                                                                          --給付年月
                  ,t1.CHKDATE                                                                        --核定日期
                  ,t1.ISSUTYP                                                                        --核付分類
                  ,t1.BENIDS                                                                         --受款人社福識別碼
                  ,t1.BENIDNNO                                                                       --受款人身份證號
                  ,t2.BENNAME                                                                        --受款人姓名
                  ,t2.PAYBANKID                                                                      --金融機構總代號
                  ,t2.BRANCHID                                                                       --金融機構分支代號
                  ,t2.PAYEEACC                                                                       --銀行帳號
                  ,t1.ISSUEAMT                                                                       --核定金額
                  ,t1.NLWKMK                                                                         --普職註記
                  ,t1.ADWKMK                                                                         --加職註記
                  ,t1.NACHGMK                                                                        --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.DISPAYKIND as PAYKIND
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.CANCELDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.CANCELDATE = v_i_canceldate
                       and t11.TYPEMK = 'D'
                       and nvl(trim(t11.PROCFUN),' ') = 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENNAME
                          ,t21.TRANSACTIONID
                          ,t21.TRANSACTIONSEQ
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                          ,t22.BENEVTREL
                      from BAREGIVEDTL t21, BAAPPBASE t22
                     where t21.APNO = t22.APNO
                       and t21.SEQNO = t22.SEQNO
                       and t21.APNO = v_i_apno
                       and t21.SEQNO = v_i_seqno
                       and t21.MK = '1'
                       and t21.AFMK = '1'
                       and (nvl(trim(t21.ISSUYM),' ') = ' ' or t21.ISSUYM is null)
                       and (nvl(trim(t21.WORKMK),' ') = ' ' or t21.WORKMK is null)
                       and (nvl(trim(t21.AFPAYDATE),' ') = ' ' or t21.AFPAYDATE is null)) t2
            where t1.APNO = t2.APNO
              and t1.SEQNO = t2.SEQNO
              and t1.TRANSACTIONID = t2.TRANSACTIONID
              and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
              and t1.SEQNO <> '0000'
              and t2.BENEVTREL in ('2','3','4','5','6','7','O')
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_15_S';
            v_g_genFlag := 0;

            for v_dataCur15 in c_dataCur_rpt15 Loop
                v_g_genFlag := 1;

                --寫入應收已收註銷清冊(單筆退匯收回註銷)(for 遺屬) [應收已收註銷核定清單(退匯收回註銷)]資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,PAYBANKID
                                           ,BRANCHID
                                           ,PAYEEACC
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                          ) values (
                                            '15'
                                           ,v_i_cprndt
                                           ,v_i_canceldate
                                           ,v_dataCur15.CHKDATE
                                           ,v_dataCur15.APNO
                                           ,v_dataCur15.SEQNO
                                           ,substr(v_i_apno,1,1)
                                           ,v_dataCur15.PAYKIND
                                           ,v_dataCur15.ISSUTYP
                                           ,v_dataCur15.ISSUYM
                                           ,v_dataCur15.PAYYM
                                           ,v_dataCur15.BENIDS
                                           ,v_dataCur15.BENIDNNO
                                           ,v_dataCur15.BENNAME
                                           ,v_dataCur15.PAYBANKID
                                           ,v_dataCur15.BRANCHID
                                           ,v_dataCur15.PAYEEACC
                                           ,v_dataCur15.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur15.NLWKMK
                                           ,v_dataCur15.ADWKMK
                                           ,v_dataCur15.NACHGMK
                );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退匯收回註銷)(遺屬)-['||v_i_apno||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退匯收回註銷)(遺屬)-['||v_i_apno||']-NoData');
            end if;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '應收已收註銷清冊(單筆退匯收回註銷)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退匯收回註銷)(遺屬)-['||v_i_apno||']:'||v_g_errMsg);
                     --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>應收已收註銷清冊(單筆退匯收回註銷)(遺屬)-['||v_i_apno||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


        end;
    --Procedure sp_BA_genPayRPT_15_S End
/********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_16
        PURPOSE:         產生轉催核定清單

        PARAMETER(IN):  *v_i_paycode       (varchar2)       --給付別
                        *v_i_issuym        (varchar2)       --核定年月
                        *v_i_payseqno      (varchar2)       --序號
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_cprndt        (varchar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/12  Zehua Chen    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_16 (
        v_i_paycode             in      varChar2,
        v_i_procYm              in      varChar2,
        v_i_cprndt              in      varChar2,
        v_o_flag                out     varchar2
    ) is
        --轉催核定清單
        cursor c_dataCur_rpt16 is
           select t1.APNO                                                                   --受理編號
                  ,t1.SEQNO                                                                 --受款人序
                  ,t1.PAYKIND                                                               --給付種類
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.CHKDATE                                                               --核定日期
                  ,t1.ISSUTYP                                                               --核付分類
                  ,t1.BENIDS                                                                --受款人社福識別碼
                  ,t1.BENIDNNO                                                              --受款人身份證號
                  ,t1.ISSUEAMT                                                              --核定金額
                  ,t1.UNACPAMT                                                              --應收金額
                  ,t2.BENNAME                                                               --受款人姓名
                  ,t1.NLWKMK                                                                --普職註記
                  ,t1.ADWKMK                                                                --加職註記
                  ,t1.NACHGMK                                                               --普職互改註記
                  ,t1.BAUNACPDTLID
                  ,t1.ISSUYM
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYKIND
                          ,t11.PAYYM
                          ,t11.UNACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.RECREM  as UNACPAMT
                          ,t11.NLWKMK as NLWKMK
                          ,t11.ADWKMK as ADWKMK
                          ,t11.NACHGMK as NACHGMK
                          ,t11.BAUNACPDTLID
                          ,t11.Issuym
                      from BAUNACPDTL t11
                     where t11.APNO like (v_i_paycode||'%')
                       and  TO_CHAR(ADD_MONTHS(TO_DATE(SUBSTR(t11.UNACPDATE,1,6) ,'YYYYMM'), 6),'YYYYMM') <= v_i_procYm
                       and  t11.RECREM > 0
                       and  t11.STS = '1'
                       and  t11.nachgmk is null
                       and  (NVL(TRIM(t11.DEMMK),' ') = ' ' or TRIM(t11.DEMMK) IS NULL)
                       and  (NVL(TRIM(t11.BDEBTMK),' ') = ' ' or TRIM(t11.BDEBTMK) IS NULL)) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDNNO
                          ,t21.BENNAME
                      from BAAPPBASE t21
                     where t21.APNO like (v_i_paycode||'%')
                         and (t21.PROCSTAT = '50' or (trim(t21.MANCHKMK) is not null and t21.PROCSTAT = '90'))
                    ) t2
                where t1.APNO = t2.APNO
                  and t1.SEQNO = t2.SEQNO
                  and t1.BENIDNNO = t2.BENIDNNO
                order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_16';
            v_g_genFlag := 0;

            for v_dataCur16 in c_dataCur_rpt16 Loop
                v_g_genFlag := 1;
                --寫入資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                           ,PAYDATE
                                           ,ISSUYM
                                           ,UNACPAMT
                                          ) values (
                                            '16'
                                           ,v_i_cprndt
                                           ,v_i_cprndt
                                           ,v_dataCur16.APNO
                                           ,v_dataCur16.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur16.PAYKIND
                                           ,v_dataCur16.ISSUTYP
                                           ,v_dataCur16.PAYYM
                                           ,v_dataCur16.BENIDS
                                           ,v_dataCur16.BENIDNNO
                                           ,v_dataCur16.BENNAME
                                           ,v_dataCur16.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur16.NLWKMK
                                           ,v_dataCur16.ADWKMK
                                           ,v_dataCur16.NACHGMK
                                           ,v_i_procYm
                                           ,v_dataCur16.ISSUYM
                                           ,v_dataCur16.UNACPAMT
                );

                update BAUNACPDTL t
                 set t.DEMMK = 'Y',
                     t.STS = '2',
                     t.DEMDATE = v_i_cprndt,
                     t.TDEMWORKTYPE = 'FHG'--轉催收業務別(業務大類(1)+案類代碼(2))
                where t.BAUNACPDTLID = v_dataCur16.BAUNACPDTLID;

            end Loop;



            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉催核定清單產生完成-['||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉催核定清單產生完成-['||']-NoData');
            end if;
             v_o_flag := v_g_genFlag;
            --LOG BATCHJOB detail Add By Zehua 20140722
            /*insert into BABATCHJOBDTL
              (BAJOBDTLID, BAJOBID, FLAG, FLAGMSG, FLAGTIME)
            values
              (
               BAJOBDTLID.Nextval,
               v_i_bajobid,
               '0',
               '轉催核定清單產生資料處理完成',
               replace(to_char(systimestamp,'yyyyMMddHH24missxff'), '.', ''));*/
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>產生轉催核定清單-['||']:'||v_g_errMsg);
                    --LOG BATCHJOB detail Add By Zehua 20140722
                    /*insert into BABATCHJOBDTL
                      (BAJOBDTLID, BAJOBID, FLAG, FLAGMSG, FLAGTIME)
                    values
                      (
                       BAJOBDTLID.Nextval,
                       v_i_bajobid,
                       '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>產生轉催核定清單-['||']:'||v_g_errMsg,
                       replace(to_char(systimestamp,'yyyyMMddHH24missxff'), '.', ''));*/
        end;

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_16_S
        PURPOSE:         產生轉催核定清單

        PARAMETER(IN):  *v_i_paycode       (varchar2)       --給付別
                        *v_i_issuym        (varchar2)       --核定年月
                        *v_i_payseqno      (varchar2)       --序號
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_cprndt        (varchar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua
        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0                            初版
        1.1   2024/06/28  William      依據BABAWEB-112修改，
                                       查詢應收未收資料，無需加上baappbase.prostat的條件
    ********************************************************************************/
    Procedure sp_BA_genPayRPT_16_S (
        v_i_paycode             in      varChar2,
        v_i_procYm              in      varChar2,
        v_i_cprndt              in      varChar2,
        v_o_flag                out     varchar2
    ) is
        --產生轉催核定清單(遺屬)
        cursor c_dataCur_rpt16_S is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO                                                                 --受款人序
                  ,t1.PAYKIND                                                               --給付種類
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.CHKDATE                                                               --核定日期
                  ,t1.ISSUTYP                                                               --核付分類
                  ,t1.BENIDS                                                                --受款人社福識別碼
                  ,t1.BENIDNNO                                                              --受款人身份證號
                  ,t1.ISSUEAMT                                                              --核定金額
                  ,t1.UNACPAMT                                                              --應收金額
                  ,t2.BENNAME                                                               --受款人姓名
                  ,t1.NLWKMK                                                                --普職註記
                  ,t1.ADWKMK                                                                --加職註記
                  ,t1.NACHGMK
                  ,t1.baunacpdtlid
                  ,t1.ISSUYM                                                              --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYKIND
                          ,t11.PAYYM
                          ,t11.UNACPDATE as CHKDATE
                          ,t11.TYPEMK as ISSUTYP
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.RECREM as UNACPAMT
                          ,t11.NLWKMK   as NLWKMK
                          ,t11.ADWKMK   as ADWKMK
                          ,t11.NACHGMK  as NACHGMK
                          ,t11.baunacpdtlid
                          ,t11.ISSUYM
                      from BAUNACPDTL t11
                     where t11.APNO like (v_i_paycode||'%')
                       and TO_CHAR(ADD_MONTHS(TO_DATE(SUBSTR(t11.UNACPDATE,1,6) ,'YYYYMM'), 6),'YYYYMM') <= v_i_procYm
                       and t11.RECREM > 0
                       and  t11.STS = '1'
                       and (NVL(TRIM(t11.DEMMK),' ') = ' ' or TRIM(t11.DEMMK) IS NULL)
                       and (NVL(TRIM(t11.BDEBTMK),' ') = ' ' or TRIM(t11.BDEBTMK) IS NULL)
                       and t11.nachgmk is null) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENNAME
                          ,t21.BENEVTREL
                      from BAAPPBASE t21
                     where t21.APNO like (v_i_paycode||'%')
                       -- and (t21.PROCSTAT = '50' or (trim(t21.MANCHKMK) is not null and t21.PROCSTAT = '90')) -- babaweb-112
                       ) t2  --20131117 Modify by Angela
                where t1.APNO = t2.APNO
                  and t1.SEQNO = t2.SEQNO
                  and t2.SEQNO <> '0000'
                  and t2.BENEVTREL in ('2','3','4','5','6','7','O')
                order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_16_S';
            v_g_genFlag := 0;
            v_g_flag    := '0';

            for v_dataCur16S in c_dataCur_rpt16_S Loop
                v_g_genFlag := 1;

                --寫入轉催核定清單(遺屬)資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,ISSUEAMT
                                           ,UNACPAMT
                                           ,PROCTIME
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                           ,PAYDATE
                                           ,ISSUYM
                                          ) values (
                                            '16'
                                           ,v_i_cprndt
                                           ,v_i_cprndt
                                           ,v_dataCur16S.APNO
                                           ,v_dataCur16S.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur16S.PAYKIND
                                           ,v_dataCur16S.ISSUTYP
                                           ,v_dataCur16S.PAYYM
                                           ,v_dataCur16S.BENIDS
                                           ,v_dataCur16S.BENIDNNO
                                           ,v_dataCur16S.BENNAME
                                           ,v_dataCur16S.ISSUEAMT
                                           ,v_dataCur16S.UNACPAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur16S.NLWKMK
                                           ,v_dataCur16S.ADWKMK
                                           ,v_dataCur16S.NACHGMK
                                           ,v_i_procYm
                                           ,v_dataCur16S.ISSUYM
                );

               update BAUNACPDTL t
               set t.DEMMK = 'Y',
                   t.STS='2',
                   t.DEMDATE = v_i_cprndt,
                   t.TDEMWORKTYPE = 'FHG'--轉催收業務別(業務大類(1)+案類代碼(2))
               where t.BAUNACPDTLID = v_dataCur16S.BAUNACPDTLID;
            end Loop;
            v_o_flag :=  v_g_genFlag;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉催核定清單(遺屬)-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉催核定清單(遺屬)-['||v_i_paycode||']-NoData');
            end if;
            --LOG BATCHJOB detail Add By Zehua 20140722
          /*  insert into BABATCHJOBDTL
              (BAJOBDTLID, BAJOBID, FLAG, FLAGMSG, FLAGTIME)
            values
              (
               BAJOBDTLID.Nextval,
               v_i_bajobid,
               '0',
               '轉催核定清單(遺屬)處理完成',
               replace(to_char(systimestamp,'yyyyMMddHH24missxff'), '.', ''));*/

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>轉催核定清單(遺屬)-['||v_i_paycode||']:'||v_g_errMsg);
        end;
    --Procedure sp_BA_genPayRPT_16_S End

        /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_17
        PURPOSE:         產生轉呆核定清單

        PARAMETER(IN):  *v_i_paycode       (varchar2)       --給付別
                        *v_i_issuym        (varchar2)       --核定年月
                        *v_i_payseqno      (varchar2)       --序號
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_cprndt        (varchar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/12  Zehua Chen    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_17 (
        v_i_paycode             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_deadyy              in      varChar2,
        v_i_apno                in      varChar2,
        v_o_flag                out     varchar2
    ) is
        --轉呆核定清單
        cursor c_dataCur_rpt17 is
           select t11.APNO,
                  t11.SEQNO,
                  t11.PAYKIND,
                  t11.PAYYM,
                  t11.UNACPDATE as CHKDATE,
                  t11.TYPEMK    as ISSUTYP,
                  t11.BENIDS,
                  t31.BENNAME,
                  t11.BENIDNNO,
                  t11.RECAMT    as ISSUEAMT,
                  t11.RECREM    as UNACPAMT,
                  t11.NLWKMK    as NLWKMK,
                  t11.ADWKMK    as ADWKMK,
                  t11.NACHGMK   as NACHGMK,
                  t11.ISSUYM,
                  t11.BAUNACPDTLID
             from BAUNACPDTL t11,
                  (select t3.APNO,
                          t3.SEQNO,
                          t3.BENIDS,
                          t3.BENIDNNO,
                          t3.BENNAME
                     from BAAPPBASE t3
                    where t3.APNO like (v_i_paycode || '%')
                      and t3.APNO = NVL(v_i_apno,APNO)
                      and (t3.PROCSTAT = '50' or (trim(t3.MANCHKMK) is not null and
                          t3.PROCSTAT = '90'))) t31
            where t11.APNO like (v_i_paycode||'%')
              and t11.APNO = t31.APNO
              and t11.SEQNO = t31.SEQNO
              and t11.BENIDNNO = t31.BENIDNNO
              and t11.bdebtyear = v_i_deadyy
              and t11.APNO = NVL(v_i_apno,t11.APNO)
              and t11.STS <> '3'
              and t11.nachgmk is null
            order by t11.APNO, t11.SEQNO, t11.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_17';
            v_g_genFlag := 0;
            for v_dataCur17 in c_dataCur_rpt17 Loop
                v_g_genFlag := 1;
                --寫入資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,PAYYM
                                           ,BENIDS
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                           ,Unacpamt
                                           ,PAYDATE
                                           ,ISSUYM
                                           ,BENNAME
                                          ) values (
                                            '17'
                                           ,v_i_cprndt
                                           ,v_i_cprndt
                                           ,v_dataCur17.APNO
                                           ,v_dataCur17.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur17.PAYKIND
                                           ,v_dataCur17.ISSUTYP
                                           ,v_dataCur17.PAYYM
                                           ,v_dataCur17.BENIDS
                                           ,v_dataCur17.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur17.NLWKMK
                                           ,v_dataCur17.ADWKMK
                                           ,v_dataCur17.NACHGMK
                                           ,v_dataCur17.UNACPAMT
                                           ,v_i_deadyy
                                           ,v_dataCur17.Issuym
                                           ,v_dataCur17.Benname
                );

                update BAUNACPDTL t
                   set t.BDEBTMK = 'Y',
                       t.STS = '3',
                       t.BDEBTDATE = v_i_cprndt,
                       t.BDEBTWORKTYPE = 'FJG'--轉呆收業務別(業務大類(1)+案類代碼(2))
                 where t.baunacpdtlid = v_dataCur17.Baunacpdtlid;

                end Loop;

            v_o_flag := v_g_genFlag;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉呆核定清單產生完成-['||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉呆核定清單產生完成-['||']-NoData');
            end if;
            --LOG BATCHJOB detail Add By Zehua 20140722
            /*insert into BABATCHJOBDTL
              (BAJOBDTLID, BAJOBID, FLAG, FLAGMSG, FLAGTIME)
            values
              (
               BAJOBDTLID.Nextval,
               v_i_bajobid,
               '0',
               '轉呆核定清單產生資料處理完成',
               replace(to_char(systimestamp,'yyyyMMddHH24missxff'), '.', ''));*/
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>產生轉呆核定清單-['||']:'||v_g_errMsg);
                    --LOG BATCHJOB detail Add By Zehua 20140722
                   /* insert into BABATCHJOBDTL
                      (BAJOBDTLID, BAJOBID, FLAG, FLAGMSG, FLAGTIME)
                    values
                      (
                       BAJOBDTLID.Nextval,
                       v_i_bajobid,
                       '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>產生轉呆核定清單-['||']:'||v_g_errMsg,
                       replace(to_char(systimestamp,'yyyyMMddHH24missxff'), '.', ''));*/
        end;

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_17_S
        PURPOSE:         產生轉呆核定清單

        PARAMETER(IN):  *v_i_paycode       (varchar2)       --給付別
                        *v_i_issuym        (varchar2)       --核定年月
                        *v_i_payseqno      (varchar2)       --序號
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_cprndt        (varchar2)       --列印日期(清單印表日期)
                        *v_i_bajobid       (varChar2)       --線上批次id
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_17_S (
        v_i_paycode             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_deadyy              in      varChar2,
        v_i_apno                in      varChar2,
        v_o_flag                out     varchar2
    ) is
        --產生轉呆核定清單(遺屬)
        cursor c_dataCur_rpt17_S is
            select t11.APNO,
                  t11.SEQNO,
                  t11.PAYKIND,
                  t11.PAYYM,
                  t11.UNACPDATE as CHKDATE,
                  t11.TYPEMK    as ISSUTYP,
                  t11.BENIDS,
                  t11.BENIDNNO,
                  t11.RECAMT    as ISSUEAMT,
                  t11.RECREM    as UNACPAMT,
                  t11.NLWKMK    as NLWKMK,
                  t11.ADWKMK    as ADWKMK,
                  t11.NACHGMK   as NACHGMK,
                  t11.ISSUYM,
                  t11.BAUNACPDTLID,
                  t31.BENEVTREL,
                  t31.BENNAME
             from BAUNACPDTL t11,
                  (select t3.APNO
                          ,t3.SEQNO
                          ,t3.BENIDS
                          ,t3.BENNAME
                          ,t3.BENEVTREL
                     from BAAPPBASE t3
                    where t3.APNO like (v_i_paycode || '%')
                      and t3.APNO = NVL(v_i_apno,APNO)
                      and (t3.PROCSTAT = '50' or (trim(t3.MANCHKMK) is not null and
                          t3.PROCSTAT = '90'))) t31
            where t11.bdebtyear = v_i_deadyy
              and t11.APNO = NVL(v_i_apno,t11.APNO)
              and t11.APNO like (v_i_paycode||'%')
              and t11.APNO = t31.APNO
              and t11.SEQNO = t31.SEQNO
              and t11.SEQNO <> '0000'
              and t31.BENEVTREL in ('2','3','4','5','6','7','O')
              and t11.STS <> '3'
              and t11.nachgmk is null
            order by t11.APNO, t11.SEQNO, t11.PAYYM;
        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_17_S';
            v_g_genFlag := 0;
            v_g_flag    := '0';

            for v_dataCur17S in c_dataCur_rpt17_S Loop
                v_g_genFlag := 1;
                --寫入轉呆核定清單(遺屬)資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,PAYYM
                                           ,BENIDS
                                           ,ISSUEAMT
                                           ,PROCTIME
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                           ,PAYDATE
                                           ,UNACPAMT
                                           ,ISSUYM
                                           ,BENNAME
                                          ) values (
                                            '17'
                                           ,v_i_cprndt
                                           ,v_i_cprndt
                                           ,v_dataCur17S.APNO
                                           ,v_dataCur17S.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur17S.PAYKIND
                                           ,v_dataCur17S.ISSUTYP
                                           ,v_dataCur17S.PAYYM
                                           ,v_dataCur17S.BENIDS
                                           ,v_dataCur17S.ISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_dataCur17S.NLWKMK
                                           ,v_dataCur17S.ADWKMK
                                           ,v_dataCur17S.NACHGMK
                                           ,v_i_deadyy
                                           ,v_dataCur17S.UNACPAMT
                                           ,v_dataCur17S.Issuym
                                           ,v_dataCur17S.Benname
                );

                update BAUNACPDTL t
                   set t.BDEBTMK       = 'Y',
                       t.STS           = '3',
                       t.BDEBTDATE     = v_i_cprndt,
                       t.BDEBTWORKTYPE = 'FJG' --轉呆收業務別(業務大類(1)+案類代碼(2))
                 where t.baunacpdtlid = v_dataCur17S.Baunacpdtlid;

            end Loop;
            v_o_flag := v_g_genFlag;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉呆核定清單(遺屬)-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉呆核定清單(遺屬)-['||v_i_paycode||']-NoData');
            end if;
            --LOG BATCHJOB detail Add By Zehua 20140722
           /* insert into BABATCHJOBDTL
              (BAJOBDTLID, BAJOBID, FLAG, FLAGMSG, FLAGTIME)
            values
              (
               BAJOBDTLID.Nextval,
               v_i_bajobid,
               '0',
               '轉呆核定清單(遺屬)處理完成',
               replace(to_char(systimestamp,'yyyyMMddHH24missxff'), '.', ''));*/

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>轉呆核定清單(遺屬)-['||v_i_paycode||']:'||v_g_errMsg);
                    --LOG BATCHJOB detail Add By Zehua 20140722
                    /*insert into BABATCHJOBDTL
                      (BAJOBDTLID, BAJOBID, FLAG, FLAGMSG, FLAGTIME)
                    values
                      (
                       BAJOBDTLID.Nextval,
                       v_i_bajobid,
                       '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>轉呆核定清單(遺屬)-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp,'yyyyMMddHH24missxff'), '.', ''));*/
                       v_g_flag := '1';

        end;
    --Procedure sp_BA_genPayRPT_17_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayRPT.sp_BA_genPayRPT_19_S
        PURPOSE:         產生保留遺屬年金計息存儲清冊

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_cprndt        (varChar2)       --列印日期(清單印表日期)

                        *v_i_bajobid       (varChar2)       --線上批次id

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2017/09/11  ChungYu      Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genPayRPT_19_S (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_cprndt              in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
    ) is

        --保留遺屬年金計息存儲清冊 [保留遺屬年金計息存儲核定清單]
        /*** 因本清冊所有金額已在撥付總表及合格清冊中呈現過，故僅提供計息存儲金額資料呈現，其餘金額均帶入0 ，
             計息存儲清冊相關資訊僅是報表功能，不須寫入PFM & PFD
        ***/
        Cursor c_dataCur_rpt1 is
            select t1.APNO                                                                      --受理編號
                  ,t1.SEQNO                                                                     --受款人序
                  ,t1.PAYKIND                                                                   --給付種類
                  ,t1.INSKD                                                                     --保險別
                  ,t1.ISSUYM as ORIISSUYM                                                       --(原)核定年月
                  ,t1.ISSUYM                                                                    --核定年月
                  ,t1.PAYYM                                                                     --給付年月
                  ,t1.CHKDATE                                                                   --核定日期
                  ,t1.APLPAYDATE                                                                --核付日期
                  ,t1.CASETYP                                                                   --案件類別
                  ,t1.ISSUTYP                                                                   --核付分類
                  ,t1.BENIDS                                                                    --受益人社福識別碼
                  ,t1.BENIDN                                                                    --受益人身分證號
                  ,t1.BENNAME                                                                   --受款人姓名
                  ,t1.PAYTYP                                                                    --給付方式
                  ,t1.INHERITORAMT AS "ISSUEAMT"                                                --核定金額(因本清冊所有金額已在)
                  ,0 AS "PAYRATE"                                                               --匯款匯費
                  ,0 AS "OTHERAAMT"                                                             --另案扣減(同類保險)金額
                  ,0 AS "OTHERBAMT"                                                             --另案扣減(他類保險)金額
                  ,0 AS "OFFSETAMT"                                                             --抵銷紓困
                  ,0 AS "COMPENAMT"                                                             --代扣補償金
                  ,t1.NLWKMK                                                                    --普職註記
                  ,t1.ADWKMK                                                                    --加職註記
                  ,t1.NACHGMK                                                                   --普職互改註記
                  ,0 AS "UNACPAMT"                                                              --收回金額
                  ,t1.INHERITORAMT                                                              --計息存儲金額(保留遺屬金津貼)
				          ,(Select t2.EVTNAME
                      From BAAPPBASE t2
				             Where t2.APNO = t1.APNO
					             And t2.SEQNO = '0000') AS "EVTNAME"                                      --事故者姓名
              from BAISSUDATATEMP t1
             where t1.APNO like (v_i_paycode||'%')
               and t1.ISSUYM = v_i_issuym
               and t1.CHKDATE = v_i_chkdate
		      	   and t1.PAYTYP = '3'
               and t1.INHERITORAMT >0
               and SEQNO <> '0000'
             order by t1.PAYTYP,t1.ISSUTYP,t1.APNO,t1.SEQNO,t1.PAYYM;                           --20190708 Modify by Angela 增加排序條件

        begin
            v_g_ProgName := 'PKG_BA_genPayRPT.sp_BA_genPayRPT_19_S';
            v_g_genFlag := 1;
            v_g_rowCount := 0;
            v_g_rptPage := 1;

            for v_dataCur1 in c_dataCur_rpt1 Loop
                v_g_genFlag := 1;
                v_g_rowCount := v_g_rowCount + 1;

                if (v_g_rowCount MOD 20)= 0 then
                    v_g_rptPage := v_g_rptPage +1;
                end if;

                --寫入保留遺屬年金計息存儲清冊資料
                insert into BAPAYRPTRECORD (RPTTYP
                                           ,CPRNDATE
                                           ,RPTPAGE
                                           ,PAYDATE
                                           ,CHKDATE
                                           ,APNO
                                           ,SEQNO
                                           ,PAYCODE
                                           ,PAYKIND
                                           ,ISSUTYP
                                           ,CASETYP
                                           ,ISSUYM
                                           ,PAYYM
                                           ,PAYTYP
                                           ,BENIDS
                                           ,BENIDN
                                           ,BENNAME
                                           ,ISSUEAMT
                                           ,MITRATE
                                           ,OTHERAAMT
                                           ,OTHERBAMT
                                           ,OFFSETAMT
                                           ,COMPENAMT
                                           ,PROCTIME
                                           ,PAYSEQNO
                                           ,NLWKMK
                                           ,ADWKMK
                                           ,NACHGMK
                                           ,UNACPAMT
                                           ,INHERITORAMT                             -- 2017/09/11 Add by ChungYu 計息存儲金額
									                    	   ,EVTNAME                                  -- 2017/09/11 Add by ChungYu 計息存儲金額
                                          ) values (
                                            '19'
                                           ,v_i_cprndt
                                           ,v_g_rptPage
                                           ,v_dataCur1.APLPAYDATE
                                           ,v_dataCur1.CHKDATE
                                           ,v_dataCur1.APNO
                                           ,v_dataCur1.SEQNO
                                           ,v_i_paycode
                                           ,v_dataCur1.PAYKIND
                                           ,v_dataCur1.ISSUTYP
                                           ,v_dataCur1.CASETYP
                                           ,v_i_issuym
                                           ,v_dataCur1.PAYYM
                                           ,v_dataCur1.PAYTYP
                                           ,v_dataCur1.BENIDS
                                           ,v_dataCur1.BENIDN
                                           ,v_dataCur1.BENNAME
                                           ,v_dataCur1.ISSUEAMT
                                           ,v_dataCur1.PAYRATE
                                           ,v_dataCur1.OTHERAAMT
                                           ,v_dataCur1.OTHERBAMT
                                           ,v_dataCur1.OFFSETAMT
                                           ,v_dataCur1.COMPENAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,'1'
                                           ,v_dataCur1.NLWKMK
                                           ,v_dataCur1.ADWKMK
                                           ,v_dataCur1.NACHGMK
                                           ,v_dataCur1.UNACPAMT
                                           ,v_dataCur1.INHERITORAMT                  -- 2017/09/11 Add by ChungYu 計息存儲金額
										                       ,v_dataCur1.EVTNAME                       -- 2017/09/11 Add by ChungYu 計息存儲金額
                 );
            end Loop;

            if v_g_genFlag>0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>保留遺屬年金計息存儲清冊-['||v_i_paycode||']');
            else
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>保留遺屬年金計息存儲清冊-['||v_i_paycode||']-NoData');
            end if;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
            '保留遺屬年金計息存儲清冊資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

               v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>保留遺屬年金計息存儲清冊-['||v_i_paycode||']:'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>保留遺屬年金計息存儲清冊-['||v_i_paycode||']:'||v_g_errMsg,
                       replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --Procedure sp_BA_genPayRPT_19_S End

End;
/

