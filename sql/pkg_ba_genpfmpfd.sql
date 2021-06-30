CREATE OR REPLACE Package BA.PKG_BA_genPFMPFD
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_genPFMPFD
    PURPOSE:         產生核定清單檔及核定清單明細檔的資料

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2012/09/04  Angela Wu    Created this Package.

    NOTES:
    1.各 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/
authid definer is
    v_g_errMsg                         varChar2(4000);
    v_g_ProgName                       varChar2(200);
    v_g_flag                           varchar2(1);
    procedure sp_BA_genPFMPFDRPT_1_L (
        v_i_issuym             in      varChar2,
        v_i_chkdate            in      varChar2,
        v_i_paydate            in      varChar2,
        v_i_payseqno           in      varChar2,
        v_i_cprndt             in      varChar2,
        v_i_bajobid            in      varchar2,
        v_o_flag               out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_1_K (
        v_i_issuym             in      varChar2,
        v_i_chkdate            in      varChar2,
        v_i_paydate            in      varChar2,
        v_i_payseqno           in      varChar2,
        v_i_cprndt             in      varChar2,
        v_i_bajobid            in      varchar2,
        v_o_flag               out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_1_S (
        v_i_issuym             in      varChar2,
        v_i_chkdate            in      varChar2,
        v_i_paydate            in      varChar2,
        v_i_payseqno           in      varChar2,
        v_i_cprndt             in      varChar2,
        v_i_bajobid            in      varchar2,
        v_o_flag               out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_5_L (
        v_i_issuym         in      varChar2,
        v_i_brissuym       in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_5_K (
        v_i_issuym         in      varChar2,
        v_i_brissuym       in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_5_S (
        v_i_issuym         in      varChar2,
        v_i_brissuym       in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_6_L (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_o_flag           out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_6_K (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_o_flag           out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_6_S (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_o_flag           out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_7_L (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_i_paydate        in      varchar2,
        v_o_flag           out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_7_K (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_i_paydate        in      varChar2,
        v_o_flag           out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_7_S (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_i_paydate        in      varchar2,
        v_o_flag           out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_8_L (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_o_flag           out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_8_K (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_o_flag           out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_8_S (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_o_flag           out     varchar2
    );

    procedure sp_BA_genPFMPFDRPT_12_L (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_12_K (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_12_S (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_13_L (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_13_K (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_13_S (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_14_L (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_14_K (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_14_S (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_15_L (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_15_K (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_genPFMPFDRPT_15_S (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    );

    procedure sp_BA_insPFPFM
    (
        v_i_cprndt             in      varChar2,
        v_i_cprnpage           in      Number,
        v_i_worktype           in      varChar2,
        v_i_accept_issue_kind  in      varChar2,
        v_i_banty              in      varChar2,
        v_i_accountno          in      varChar2,
        v_i_bookedbook         in      varChar2,
        v_i_js_resource        in      varChar2,
        v_i_issue_amt          in      Number,
        v_i_accept_amt         in      Number,
        v_i_pfd_cnt            in      Number,
        v_i_rechkdept          in      varChar2,
        v_i_rckdate            in      varChar2,
        v_i_confirm_dt         in      varChar2,
        v_i_paydte             in      varChar2,
        v_i_putfile_page       in      Number,
        v_i_bajobid            in      varchar2
    );

    procedure sp_BA_insBatchPFPFD
    (
        v_i_cprndt             in      varChar2,
        v_i_cprnpage           in      Number,
        v_i_lcnt               in      Number,
        v_i_accept_pay_type    in      varChar2,
        v_i_decide_amt         in      Number,
        v_i_cash_amt           in      Number,
        v_i_cmemo              in      varChar2,
        v_i_chksdate           in      varChar2,
        v_i_cnt                in      Number,
        v_i_bli_account_code   in      varChar2,
        v_i_acttitle_ap_code   in      varChar2,
        v_i_actdb              in      varChar2,
        v_i_actcr              in      varChar2,
        v_i_data_cd            in      varChar2,
        v_i_putfile_page       in      Number,
        v_i_accept_issue_cd    in      varChar2,
        v_i_issuym             in      varChar2,
        v_i_bajobid            in      varchar2
    );

    procedure sp_BA_insSinglePFPFD
    (
        v_i_cprndt             in      varChar2,
        v_i_cprnpage           in      Number,
        v_i_lcnt               in      Number,
        v_i_cdetail_keyfield   in      varChar2,
        v_i_per_unit_cd        in      varChar2,
        v_i_id_no              in      varChar2,
        v_i_per_unit_name      in      varChar2,
        v_i_accept_pay_type    in      varChar2,
        v_i_account_no         in      varChar2,
        v_i_decide_amt         in      varChar2,
        v_i_cash_amt           in      varChar2,
        v_i_cmemo              in      varChar2,
        v_i_chksdate           in      varChar2,
        v_i_opcfm              in      varChar2,
        v_i_cnt                in      Number,
        v_i_bli_account_code   in      varChar2,
        v_i_acttitle_ap_code   in      varChar2,
        v_i_actdb              in      varChar2,
        v_i_actcr              in      varChar2,
        v_i_data_cd            in      varChar2,
        v_i_putfile_page       in      Number,
        v_i_insuranced_name    in      varChar2,
        v_i_accept_issue_cd    in      varChar2,
        v_i_apno               in      varChar2,
        v_i_gvseq              in      varChar2,
        v_i_payym              in      varChar2,
        v_i_issuym	           in      varChar2,
        v_i_bajobid            in      varchar2
    );

    function fn_BA_getWORKTYPE (
        v_i_paycode            in      varChar2,
        v_i_rpttyp             in      varChar2,
        v_i_benevtrel          in      varChar2,
        v_i_nachgmk            in      varChar2
    ) return varChar2;

    function fn_BA_getJSRESOURCE (
        v_i_rpttyp             in      varChar2
    ) return varChar2;

    function fn_BA_getCPRNPAGE (
        v_i_cprndt             in      varChar2
    ) return Number;

    function fn_BA_getPUTFILEPAGE (
        v_i_cprndt             in      varChar2,
        v_i_paykind            in      varChar2
    ) return Number;

    function fn_BA_getRECHKDEPT (
        v_i_paycode             in     varChar2
    ) return varChar2;

    function fn_BA_getBLIACCOUNTCODE (
        v_i_paytyp             in      varChar2,
        v_i_actcr              in      varChar2
    ) return varChar2;

    function fn_BA_getBANTYACCOUNTNO (
        v_i_paytyp             in      varChar2
    ) return varChar2;

    function fn_BA_chkACCOUNTNOMK (
        v_i_accountno          in      varChar2
    ) return varChar2;

End;
/

CREATE OR REPLACE Package Body PKG_BA_genPFMPFD
is
    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_1_L
        PURPOSE:         產生月核定撥付總表的核定清單檔及核定清單明細檔(for 老年年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_payseqno      (varChar2)       --媒體產生序號
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua
        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/09/04  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_1_L (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '1';
        v_paycode          varChar2(1)  := 'L';
        v_paykind          varChar2(2)  := '45';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.RPTTYP
                  ,t.PAYTYP
                  ,t.BENEVTREL
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.RPTTYP,t.PAYTYP,t.BENEVTREL
             order by t.RPTTYP,t.BENEVTREL desc,t.PAYTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_paytyp varChar2,v_benevtrel varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.PAYTYP
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DECIDEAMT
                  ,t.CASHAMT
                  ,t.CUTAMT
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
                  ,t.CNT
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.PAYTYP,' ') = nvl(v_paytyp,' ')
               and nvl(t.BENEVTREL,' ') = nvl(v_benevtrel,' ')
             order by to_Number(t.SEQNO);

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_1_L';
            v_g_flag     := '0';
            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt := 0;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,v_dataCur_1.BENEVTREL,' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --將核定資料逐筆寫入PFD
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.PAYTYP,v_dataCur_1.BENEVTREL) Loop
                    v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                    v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                    v_lcnt             := v_lcnt+1;
                    v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_1.PAYTYP,v_dataCur_2.ACTCR);
                    v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),1,3);
                    v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),4,1);

                    if v_dataCur_2.DATACD <> '3' and v_dataCur_2.DATACD <> '5' then
                        v_issue_amt        := v_issue_amt+v_dataCur_2.DECIDEAMT-v_dataCur_2.CUTAMT;
                        v_accept_amt       := v_accept_amt+v_dataCur_2.CASHAMT;
                        v_pfd_cnt          := v_pfd_cnt+v_dataCur_2.CNT;
                    end if;

                    sp_BA_insBatchPFPFD(v_i_cprndt
                                       ,v_cprnpage
                                       ,v_lcnt
                                       ,v_dataCur_2.ACCEPTPAYTYPE
                                       ,v_dataCur_2.DECIDEAMT
                                       ,v_dataCur_2.CASHAMT
                                       ,v_dataCur_2.ACCOUNTMEMO
                                       ,v_dataCur_2.CHKDATE
                                       ,v_dataCur_2.CNT
                                       ,v_bli_account_code
                                       ,v_dataCur_2.ACTTITLEAPCODE
                                       ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                       ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                       ,v_dataCur_2.DATACD
                                       ,v_putfilepage
                                       ,v_paykind
                                       ,v_dataCur_2.ISSUYM
                                       ,v_i_bajobid
                                        );

                    --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                    if v_lcnt=20 then
                        sp_BA_insPFPFM(v_i_cprndt
                                      ,v_cprnpage
                                      ,v_worktype
                                      ,v_paykind
                                      ,v_banty
                                      ,v_accountno
                                      ,v_dataCur_1.PAYTYP
                                      ,v_js_resource
                                      ,v_issue_amt
                                      ,v_accept_amt
                                      ,v_pfd_cnt
                                      ,v_rechkdept
                                      ,v_i_paydate
                                      ,v_i_paydate
                                      ,v_i_paydate
                                      ,v_putfilepage
                                      ,v_i_bajobid
                                       );

                        v_lcnt       := 0;
                        v_issue_amt  := 0;
                        v_accept_amt := 0;
                        v_pfd_cnt    := 0;
                    end if;

                    --更新核定清單日期及頁次的相關Table及欄位
                    update BAPAYRPTSUM t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.RPTPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.RPTTYP = v_rpttyp
                       and t.PAYCODE = v_paycode
                       and t.ISSUYM = v_i_issuym
                       and t.CHKDATE = v_i_chkdate
                       and t.PAYSEQNO = v_i_payseqno
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                    update BAPAYRPTACCOUNT t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.RPTPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.RPTTYP = v_rpttyp
                       and t.PAYCODE = v_paycode
                       and t.ISSUYM = v_i_issuym
                       and t.CHKDATE = v_i_chkdate
                       and t.PAYSEQNO = v_i_payseqno
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                    update BAPAYRPTRECORD t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.RPTPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.RPTTYP in ('3','4')
                       and t.PAYCODE = v_paycode
                       and t.ISSUYM = v_i_issuym
                       and t.CHKDATE = v_i_chkdate
                       and t.PAYSEQNO = v_i_payseqno
                       and t.PAYDATE = v_i_paydate
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       --and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                    update BADAPR t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.CPRNPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.APNO like (v_paycode||'%')
                       and t.ISSUYM = v_i_issuym
                       and t.MTESTMK = 'F'
                       and t.APLPAYMK = '3'
                       and t.APLPAYDATE = v_i_paydate
                       and t.CHKDATE = v_i_chkdate
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       --and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ')
                       and (t.CPRNPAGE is null or nvl(trim(t.CPRNPAGE),' ')=' ');
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,v_dataCur_1.PAYTYP
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_pfd_cnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.PAYCODE = v_paycode
                   and t.ISSUYM = v_i_issuym
                   and t.CHKDATE = v_i_chkdate
                   and t.PAYSEQNO = v_i_payseqno
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                   and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ');

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>PayTyp-'||v_dataCur_1.PAYTYP||'：產製完成');
            end Loop;
             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生月核定撥付總表的核定清單檔及核定清單明細檔(for 老年年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

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
    --procedure sp_BA_genPFMPFDRPT_1_L End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_1_K
        PURPOSE:         產生月核定撥付總表的核定清單檔及核定清單明細檔(for 失能年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_payseqno      (varChar2)       --媒體產生序號
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/10/23  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_1_K (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varChar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '1';
        v_paycode          varChar2(1)  := 'K';
        v_paykind          varChar2(2)  := '35';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select nvl(t.NACHGMK,' ') as NACHGMK
                  ,t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
                  ,t.PAYTYP
                  ,t.BENEVTREL
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by nvl(t.NACHGMK,' '),t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP,t.BENEVTREL
             order by nvl(t.NACHGMK,' '),t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP,t.BENEVTREL desc;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nachgmk varChar2,v_nlwkmk varChar2,v_adwkmk varChar2,v_paytyp varChar2,v_benevtrel varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.PAYTYP
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DECIDEAMT
                  ,t.CASHAMT
                  ,t.CUTAMT
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
                  ,t.CNT
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.PAYTYP,' ') = nvl(v_paytyp,' ')
               and nvl(t.BENEVTREL,' ') = nvl(v_benevtrel,' ')
               and nvl(t.NACHGMK,' ') = nvl(v_nachgmk,' ')
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
             order by nvl(t.NACHGMK,' '),t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_1_K';
            v_g_flag     := '0';
            --讀取回寫PFM&PFD的報表種類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt := 0;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,v_dataCur_1.BENEVTREL,v_dataCur_1.NACHGMK);

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --將核定資料逐筆寫入PFD
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NACHGMK,v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.PAYTYP,v_dataCur_1.BENEVTREL) Loop
                    v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                    v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                    v_lcnt             := v_lcnt+1;
                    v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_1.PAYTYP,v_dataCur_2.ACTCR);
                    v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),1,3);
                    v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),4,1);

                    if v_dataCur_2.DATACD <> '3' and v_dataCur_2.DATACD <> '5' then
                        v_issue_amt        := v_issue_amt+v_dataCur_2.DECIDEAMT-v_dataCur_2.CUTAMT;
                        v_accept_amt       := v_accept_amt+v_dataCur_2.CASHAMT;
                        v_pfd_cnt          := v_pfd_cnt+v_dataCur_2.CNT;
                    end if;

                    sp_BA_insBatchPFPFD(v_i_cprndt
                                       ,v_cprnpage
                                       ,v_lcnt
                                       ,v_dataCur_2.ACCEPTPAYTYPE
                                       ,v_dataCur_2.DECIDEAMT
                                       ,v_dataCur_2.CASHAMT
                                       ,v_dataCur_2.ACCOUNTMEMO
                                       ,v_dataCur_2.CHKDATE
                                       ,v_dataCur_2.CNT
                                       ,v_bli_account_code
                                       ,v_dataCur_2.ACTTITLEAPCODE
                                       ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                       ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                       ,v_dataCur_2.DATACD
                                       ,v_putfilepage
                                       ,v_paykind
                                       ,v_dataCur_2.ISSUYM
                                       ,v_i_bajobid
                                        );

                    --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                    if v_lcnt=20 then
                        sp_BA_insPFPFM(v_i_cprndt
                                      ,v_cprnpage
                                      ,v_worktype
                                      ,v_paykind
                                      ,v_banty
                                      ,v_accountno
                                      ,v_dataCur_1.PAYTYP
                                      ,v_js_resource
                                      ,v_issue_amt
                                      ,v_accept_amt
                                      ,v_pfd_cnt
                                      ,v_rechkdept
                                      ,v_i_paydate
                                      ,v_i_paydate
                                      ,v_i_paydate
                                      ,v_putfilepage
                                      ,v_i_bajobid
                                       );

                        v_lcnt       := 0;
                        v_issue_amt  := 0;
                        v_accept_amt := 0;
                        v_pfd_cnt    := 0;
                    end if;

                    --更新核定清單日期及頁次的相關Table及欄位
                    update BAPAYRPTSUM t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.RPTPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.RPTTYP = v_rpttyp
                       and t.PAYCODE = v_paycode
                       and t.ISSUYM = v_i_issuym
                       and t.CHKDATE = v_i_chkdate
                       and t.PAYSEQNO = v_i_payseqno
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and nvl(t.NACHGMK,' ') = nvl(v_dataCur_1.NACHGMK,' ')
                       and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                       and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                       and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                    update BAPAYRPTACCOUNT t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.RPTPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.RPTTYP = v_rpttyp
                       and t.PAYCODE = v_paycode
                       and t.ISSUYM = v_i_issuym
                       and t.CHKDATE = v_i_chkdate
                       and t.PAYSEQNO = v_i_payseqno
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and nvl(t.NACHGMK,' ') = nvl(v_dataCur_1.NACHGMK,' ')
                       and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                       and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                       and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                    update BAPAYRPTRECORD t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.RPTPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.RPTTYP in ('3','4')
                       and t.PAYCODE = v_paycode
                       and t.ISSUYM = v_i_issuym
                       and t.CHKDATE = v_i_chkdate
                       and t.PAYSEQNO = v_i_payseqno
                       and t.PAYDATE = v_i_paydate
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       --and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and nvl(t.NACHGMK,' ') = nvl(v_dataCur_1.NACHGMK,' ')
                       and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                       and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                       and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                    update BADAPR t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.CPRNPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.APNO like (v_paycode||'%')
                       and t.ISSUYM = v_i_issuym
                       and t.MTESTMK = 'F'
                       and t.APLPAYMK = '3'
                       and t.APLPAYDATE = v_i_paydate
                       and t.CHKDATE = v_i_chkdate
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       --and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and nvl(t.NACHGMK,' ') = nvl(v_dataCur_1.NACHGMK,' ')
                       and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                       and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                       and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ')
                       and (t.CPRNPAGE is null or nvl(trim(t.CPRNPAGE),' ')=' ');
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,v_dataCur_1.PAYTYP
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_pfd_cnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.PAYCODE = v_paycode
                   and t.ISSUYM = v_i_issuym
                   and t.CHKDATE = v_i_chkdate
                   and t.PAYSEQNO = v_i_payseqno
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                   and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                   and nvl(t.NACHGMK,' ') = nvl(v_dataCur_1.NACHGMK,' ')
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ');

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>PayTyp-'||v_dataCur_1.PAYTYP||'：產製完成');
            end Loop;

             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               ' 產生月核定撥付總表的核定清單檔及核定清單明細檔(for 失能年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                     --修改log作法 by TseHua 20180419
                      sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg, replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --procedure sp_BA_genPFMPFDRPT_1_K End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_1_S
        PURPOSE:         產生月核定撥付總表的核定清單檔及核定清單明細檔(for 遺屬年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_payseqno      (varChar2)       --媒體產生序號
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/11/20  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_1_S (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varChar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '1';
        v_paycode          varChar2(1)  := 'S';
        v_paykind          varChar2(2)  := '55';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select nvl(t.NACHGMK,' ') as NACHGMK
                  ,t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
                  ,t.PAYTYP
                  ,t.BENEVTREL
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by nvl(t.NACHGMK,' '),t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP,t.BENEVTREL
             order by nvl(t.NACHGMK,' '),t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP,t.BENEVTREL desc;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nachgmk varChar2,v_nlwkmk varChar2,v_adwkmk varChar2,v_paytyp varChar2,v_benevtrel varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.PAYTYP
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DECIDEAMT
                  ,t.CASHAMT
                  ,t.CUTAMT
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
                  ,t.CNT
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.PAYTYP,' ') = nvl(v_paytyp,' ')
               and nvl(t.BENEVTREL,' ') = nvl(v_benevtrel,' ')
               and nvl(t.NACHGMK,' ') = nvl(v_nachgmk,' ')
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
             order by nvl(t.NACHGMK,' '),t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_1_S';
            v_o_flag     := '0';
            --讀取回寫PFM&PFD的報表種類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt := 0;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,v_dataCur_1.BENEVTREL,v_dataCur_1.NACHGMK);

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --將核定資料逐筆寫入PFD
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NACHGMK,v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.PAYTYP,v_dataCur_1.BENEVTREL) Loop
                    v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                    v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                    v_lcnt             := v_lcnt+1;
                    v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_1.PAYTYP,v_dataCur_2.ACTCR);
                    v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),1,3);
                    v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),4,1);

                    if v_dataCur_2.DATACD <> '3' and v_dataCur_2.DATACD <> '5' then
                        v_issue_amt        := v_issue_amt+v_dataCur_2.DECIDEAMT-v_dataCur_2.CUTAMT;
                        v_accept_amt       := v_accept_amt+v_dataCur_2.CASHAMT;
                        v_pfd_cnt          := v_pfd_cnt+v_dataCur_2.CNT;
                    end if;

                    sp_BA_insBatchPFPFD(v_i_cprndt
                                       ,v_cprnpage
                                       ,v_lcnt
                                       ,v_dataCur_2.ACCEPTPAYTYPE
                                       ,v_dataCur_2.DECIDEAMT
                                       ,v_dataCur_2.CASHAMT
                                       ,v_dataCur_2.ACCOUNTMEMO
                                       ,v_dataCur_2.CHKDATE
                                       ,v_dataCur_2.CNT
                                       ,v_bli_account_code
                                       ,v_dataCur_2.ACTTITLEAPCODE
                                       ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                       ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                       ,v_dataCur_2.DATACD
                                       ,v_putfilepage
                                       ,v_paykind
                                       ,v_dataCur_2.ISSUYM
                                       ,v_i_bajobid
                                        );

                    --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                    if v_lcnt=20 then
                        sp_BA_insPFPFM(v_i_cprndt
                                      ,v_cprnpage
                                      ,v_worktype
                                      ,v_paykind
                                      ,v_banty
                                      ,v_accountno
                                      ,v_dataCur_1.PAYTYP
                                      ,v_js_resource
                                      ,v_issue_amt
                                      ,v_accept_amt
                                      ,v_pfd_cnt
                                      ,v_rechkdept
                                      ,v_i_paydate
                                      ,v_i_paydate
                                      ,v_i_paydate
                                      ,v_putfilepage
                                      ,v_i_bajobid
                                       );

                        v_lcnt       := 0;
                        v_issue_amt  := 0;
                        v_accept_amt := 0;
                        v_pfd_cnt    := 0;
                    end if;

                    --更新核定清單日期及頁次的相關Table及欄位
                    update BAPAYRPTSUM t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.RPTPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.RPTTYP = v_rpttyp
                       and t.PAYCODE = v_paycode
                       and t.ISSUYM = v_i_issuym
                       and t.CHKDATE = v_i_chkdate
                       and t.PAYSEQNO = v_i_payseqno
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and nvl(t.NACHGMK,' ') = nvl(v_dataCur_1.NACHGMK,' ')
                       and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                       and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                       and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                    update BAPAYRPTACCOUNT t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.RPTPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.RPTTYP = v_rpttyp
                       and t.PAYCODE = v_paycode
                       and t.ISSUYM = v_i_issuym
                       and t.CHKDATE = v_i_chkdate
                       and t.PAYSEQNO = v_i_payseqno
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and nvl(t.NACHGMK,' ') = nvl(v_dataCur_1.NACHGMK,' ')
                       and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                       and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                       and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                    update BAPAYRPTRECORD t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.RPTPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.RPTTYP in ('3','4')
                       and t.PAYCODE = v_paycode
                       and t.ISSUYM = v_i_issuym
                       and t.CHKDATE = v_i_chkdate
                       and t.PAYSEQNO = v_i_payseqno
                       and t.PAYDATE = v_i_paydate
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       --and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and nvl(t.NACHGMK,' ') = nvl(v_dataCur_1.NACHGMK,' ')
                       and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                       and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                       and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                    update BADAPR t
                       set t.CPRNDATE = v_i_cprndt
                          ,t.CPRNPAGE = v_cprnpage
                          ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.APNO like (v_paycode||'%')
                       and t.ISSUYM = v_i_issuym
                       and t.MTESTMK = 'F'
                       and t.APLPAYMK = '3'
                       and t.APLPAYDATE = v_i_paydate
                       and t.CHKDATE = v_i_chkdate
                       and t.CPRNDATE = v_i_cprndt
                       and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                       --and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                       and nvl(t.NACHGMK,' ') = nvl(v_dataCur_1.NACHGMK,' ')
                       and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                       and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                       and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ')
                       and (t.CPRNPAGE is null or nvl(trim(t.CPRNPAGE),' ')=' ' or nvl(trim(t.CPRNPAGE),0)=0);
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,v_dataCur_1.PAYTYP
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_pfd_cnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.PAYCODE = v_paycode
                   and t.ISSUYM = v_i_issuym
                   and t.CHKDATE = v_i_chkdate
                   and t.PAYSEQNO = v_i_payseqno
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                   and nvl(t.BENEVTREL,' ') = nvl(v_dataCur_1.BENEVTREL,' ')
                   and nvl(t.NACHGMK,' ') = nvl(v_dataCur_1.NACHGMK,' ')
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ');

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>PayTyp-'||v_dataCur_1.PAYTYP||'：產製完成');
            end Loop;
             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生月核定撥付總表的核定清單檔及核定清單明細檔(for 遺屬年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


               v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                     --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                    v_g_flag := '1';
                    v_o_flag := v_g_flag;
        end;
    --procedure sp_BA_genPFMPFDRPT_1_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_5_L
        PURPOSE:         產生退匯核定清單檔及核定清單明細檔(for 老年年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --(原)核定年月
                        *v_i_brissuym      (varChar2)       --退匯核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_5_L (
        v_i_issuym         in      varChar2,
        v_i_brissuym       in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varChar2
    ) is
        v_rpttyp           varChar2(1)  := '5';
        v_paycode          varChar2(1)  := 'L';
        v_paykind          varChar2(2)  := '45';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.RPTTYP
                  ,t.PAYTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.RPTTYP,t.PAYTYP
             order by t.RPTTYP,t.PAYTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_paytyp varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.PAYTYP
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.PAYTYP,' ') = nvl(v_paytyp,' ')
             order by to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_paytyp varChar2) is
            select t.APNO
                  ,t.SEQNO
                  ,t.PAYDATE
                  ,t.BRISSUYM
                  ,t.ORIISSUYM
                  ,t.ISSUYM
                  ,t.PAYYM
                  ,t.BRCHKDATE
                  ,t.BENTYP
                  ,t.BENIDN
                  ,t.BENNAME
                  ,t.PAYTYP
                  ,t.PAYBANKID
                  ,t.BRANCHID
                  ,t.PAYEEACC
                  ,t.ACCIDN
                  ,t.ACCNAME
                  ,t.REMITAMT
              from BAPFLBAC t
             where t.APNO like (v_paycode||'%')
               and t.ORIISSUYM = v_i_issuym
               and t.BRISSUYM = v_i_brissuym
               and t.BRCHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.PAYDATE = v_i_paydate
               and t.REMITAMT > 0
               and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_paytyp,' ')
             order by t.APNO,t.SEQNO,t.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_5_L';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.PAYTYP) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.PAYTYP) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_1.PAYTYP,v_dataCur_2.ACTCR);
                        v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),1,3);
                        v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),4,1);
                        v_issue_amt        := v_issue_amt+v_dataCur_3.REMITAMT;
                        v_accept_amt       := v_accept_amt+v_dataCur_3.REMITAMT;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := (v_dataCur_3.APNO
                                             ||v_dataCur_3.SEQNO
                                             ||v_dataCur_3.PAYDATE
                                             ||v_dataCur_3.BRISSUYM
                                             ||v_dataCur_3.ORIISSUYM
                                             ||v_dataCur_3.ISSUYM
                                             ||v_dataCur_3.PAYYM
                                             ||v_dataCur_3.BRCHKDATE);
                        v_payeeacc         := (v_dataCur_3.PAYBANKID||v_dataCur_3.BRANCHID||v_dataCur_3.PAYEEACC);

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,v_dataCur_3.BENTYP
                                            ,v_dataCur_3.ACCIDN
                                            ,v_dataCur_3.ACCNAME
                                            ,v_dataCur_3.PAYTYP
                                            ,v_payeeacc
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPFLBAC t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ORIISSUYM = v_i_issuym
                           and t.BRISSUYM = v_i_brissuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.BRCHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and t.PAYDATE = v_i_paydate
                           and t.REMITAMT > 0
                           and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ');

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,v_dataCur_1.PAYTYP
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,v_dataCur_1.PAYTYP
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ');

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>PayTyp-'||v_dataCur_1.PAYTYP||'：產製完成');
            end Loop;

             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生退匯核定清單檔及核定清單明細檔(for 老年年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                     --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_genPFMPFDRPT_5_L End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_5_K
        PURPOSE:         產生退匯核定清單檔及核定清單明細檔(for 失能年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --(原)核定年月
                        *v_i_brissuym      (varChar2)       --退匯核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_5_K (
        v_i_issuym         in      varChar2,
        v_i_brissuym       in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      Varchar2
    ) is
        v_rpttyp           varChar2(1)  := '5';
        v_paycode          varChar2(1)  := 'K';
        v_paykind          varChar2(2)  := '35';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
                  ,t.PAYTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2,v_paytyp varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.PAYTYP
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_paytyp,' ')
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2,v_paytyp varChar2) is
            select t.APNO
                  ,t.SEQNO
                  ,t.PAYDATE
                  ,t.BRISSUYM
                  ,t.ORIISSUYM
                  ,t.ISSUYM
                  ,t.PAYYM
                  ,t.BRCHKDATE
                  ,t.BENTYP
                  ,t.BENIDN
                  ,t.BENNAME
                  ,t.PAYTYP
                  ,t.PAYBANKID
                  ,t.BRANCHID
                  ,t.PAYEEACC
                  ,t.ACCIDN
                  ,t.ACCNAME
                  ,t.REMITAMT
              from BAPFLBAC t
             where t.APNO like (v_paycode||'%')
               and t.ORIISSUYM = v_i_issuym
               and t.BRISSUYM = v_i_brissuym
               and t.BRCHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.PAYDATE = v_i_paydate
               and t.REMITAMT > 0
               and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_paytyp,' ')
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
             order by t.APNO,t.SEQNO,t.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_5_K';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.PAYTYP) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.PAYTYP) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_1.PAYTYP,v_dataCur_2.ACTCR);
                        v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),1,3);
                        v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),4,1);
                        v_issue_amt        := v_issue_amt+v_dataCur_3.REMITAMT;
                        v_accept_amt       := v_accept_amt+v_dataCur_3.REMITAMT;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := (v_dataCur_3.APNO
                                             ||v_dataCur_3.SEQNO
                                             ||v_dataCur_3.PAYDATE
                                             ||v_dataCur_3.BRISSUYM
                                             ||v_dataCur_3.ORIISSUYM
                                             ||v_dataCur_3.ISSUYM
                                             ||v_dataCur_3.PAYYM
                                             ||v_dataCur_3.BRCHKDATE);
                        v_payeeacc         := (v_dataCur_3.PAYBANKID||v_dataCur_3.BRANCHID||v_dataCur_3.PAYEEACC);

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,v_dataCur_3.BENTYP
                                            ,v_dataCur_3.ACCIDN
                                            ,v_dataCur_3.ACCNAME
                                            ,v_dataCur_3.PAYTYP
                                            ,v_payeeacc
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPFLBAC t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ORIISSUYM = v_i_issuym
                           and t.BRISSUYM = v_i_brissuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.BRCHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and t.PAYDATE = v_i_paydate
                           and t.REMITAMT > 0
                           and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ');

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,v_dataCur_1.PAYTYP
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,v_dataCur_1.PAYTYP
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ');

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>PayTyp-'||v_dataCur_1.PAYTYP||'：產製完成');
            end Loop;

             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'0',
               '產生退匯核定清單檔及核定清單明細檔(for 失能年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                     --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_genPFMPFDRPT_5_K End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_5_S
        PURPOSE:         產生退匯核定清單檔及核定清單明細檔(for 遺屬年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --(原)核定年月
                        *v_i_brissuym      (varChar2)       --退匯核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_5_S (
        v_i_issuym         in      varChar2,
        v_i_brissuym       in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varChar2
    ) is
        v_rpttyp           varChar2(1)  := '5';
        v_paycode          varChar2(1)  := 'S';
        v_paykind          varChar2(2)  := '55';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
                  ,t.PAYTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2,v_paytyp varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.PAYTYP
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_paytyp,' ')
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2,v_paytyp varChar2) is
            select t.APNO
                  ,t.SEQNO
                  ,t.PAYDATE
                  ,t.BRISSUYM
                  ,t.ORIISSUYM
                  ,t.ISSUYM
                  ,t.PAYYM
                  ,t.BRCHKDATE
                  ,t.BENTYP
                  ,t.BENIDN
                  ,t.BENNAME
                  ,t.PAYTYP
                  ,t.PAYBANKID
                  ,t.BRANCHID
                  ,t.PAYEEACC
                  ,t.ACCIDN
                  ,t.ACCNAME
                  ,t.REMITAMT
              from BAPFLBAC t
             where t.APNO like (v_paycode||'%')
               and t.ORIISSUYM = v_i_issuym
               and t.BRISSUYM = v_i_brissuym
               and t.BRCHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.PAYDATE = v_i_paydate
               and t.REMITAMT > 0
               and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_paytyp,' ')
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
             order by t.APNO,t.SEQNO,t.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_5_S';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.PAYTYP) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.PAYTYP) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_1.PAYTYP,v_dataCur_2.ACTCR);
                        v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),1,3);
                        v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),4,1);
                        v_issue_amt        := v_issue_amt+v_dataCur_3.REMITAMT;
                        v_accept_amt       := v_accept_amt+v_dataCur_3.REMITAMT;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := (v_dataCur_3.APNO
                                             ||v_dataCur_3.SEQNO
                                             ||v_dataCur_3.PAYDATE
                                             ||v_dataCur_3.BRISSUYM
                                             ||v_dataCur_3.ORIISSUYM
                                             ||v_dataCur_3.ISSUYM
                                             ||v_dataCur_3.PAYYM
                                             ||v_dataCur_3.BRCHKDATE);
                        v_payeeacc         := (v_dataCur_3.PAYBANKID||v_dataCur_3.BRANCHID||v_dataCur_3.PAYEEACC);

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,v_dataCur_3.BENTYP
                                            ,v_dataCur_3.ACCIDN
                                            ,v_dataCur_3.ACCNAME
                                            ,v_dataCur_3.PAYTYP
                                            ,v_payeeacc
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPFLBAC t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ORIISSUYM = v_i_issuym
                           and t.BRISSUYM = v_i_brissuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.BRCHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and t.PAYDATE = v_i_paydate
                           and t.REMITAMT > 0
                           and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ');

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,v_dataCur_1.PAYTYP
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,v_dataCur_1.PAYTYP
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ');

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>PayTyp-'||v_dataCur_1.PAYTYP||'：產製完成');
            end Loop;

             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生退匯核定清單檔及核定清單明細檔(for 遺屬年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                     --修改log作法 by TseHua 20180419
                  sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                    end;
    --procedure sp_BA_genPFMPFDRPT_5_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_6_L
        PURPOSE:         產生改匯核定清單檔及核定清單明細檔(for 老年年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua
        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_6_L (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varChar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '6';
        v_paycode          varChar2(1)  := 'L';
        v_paykind          varChar2(2)  := '45';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.RPTTYP
                  ,t.PAYTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.RPTTYP,t.PAYTYP
             order by t.RPTTYP,t.PAYTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_paytyp varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.PAYTYP
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.PAYTYP,' ') = nvl(v_paytyp,' ')
             order by to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_paytyp varChar2) is
            select t.APNO
                  ,t.SEQNO
                  ,t.ORIISSUYM
                  ,t.BRISSUYM
                  ,t.ISSUYM
                  ,t.PAYYM
                  ,t.TRANSACTIONID
                  ,t.TRANSACTIONSEQ
                  ,t.RECHKDATE
                  ,t.BENTYP
                  ,t.BENIDN
                  ,t.BENNAME
                  ,t.PAYTYP
                  ,t.PAYBANKID
                  ,t.BRANCHID
                  ,t.PAYEEACC
                  ,t.ACCIDN
                  ,t.ACCNAME
                  ,t.REMITAMT
              from BAREGIVEDTL t
             where t.APNO like (v_paycode||'%')
               and t.ISSUYM = v_i_issuym
               and t.AFMK = '2'
               and nvl(trim(t.WORKMK),' ') <> 'Y'
               and (t.AFCHKDATE is not null and nvl(trim(t.AFCHKDATE),' ')<>' ')
               and (t.AFPAYDATE is not null and nvl(trim(t.AFPAYDATE),' ')<>' ')
               and t.RECHKDATE = v_i_chkdate
               and t.AFPAYDATE = v_i_paydate
               and t.CPRNDATE = v_i_cprndt
               and t.REMITAMT > 0
               and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_paytyp,' ')
             order by t.APNO,t.SEQNO,t.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_6_L';
            v_g_flag     := '0';
            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.PAYTYP) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.PAYTYP) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_1.PAYTYP,v_dataCur_2.ACTCR);
                        v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),1,3);
                        v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),4,1);
                        v_issue_amt        := v_issue_amt+v_dataCur_3.REMITAMT;
                        v_accept_amt       := v_accept_amt+v_dataCur_3.REMITAMT;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := (v_dataCur_3.APNO
                                             ||v_dataCur_3.SEQNO
                                             ||v_dataCur_3.ORIISSUYM
                                             ||v_dataCur_3.BRISSUYM
                                             ||v_dataCur_3.ISSUYM
                                             ||v_dataCur_3.PAYYM
                                             ||v_dataCur_3.TRANSACTIONID
                                             ||v_dataCur_3.TRANSACTIONSEQ
                                             ||v_dataCur_3.RECHKDATE);
                        v_payeeacc         := (v_dataCur_3.PAYBANKID||v_dataCur_3.BRANCHID||v_dataCur_3.PAYEEACC);


                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,v_dataCur_3.BENTYP
                                            ,v_dataCur_3.ACCIDN
                                            ,v_dataCur_3.ACCNAME
                                            ,v_dataCur_3.PAYTYP
                                            ,v_payeeacc
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAREGIVEDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ORIISSUYM = v_dataCur_3.ORIISSUYM
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.RECHKDATE = v_i_chkdate
                           and t.AFPAYDATE = v_i_paydate
                           and t.CPRNDATE = v_i_cprndt
                           and t.REMITAMT > 0
                           and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ');

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,v_dataCur_1.PAYTYP
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,v_dataCur_1.PAYTYP
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ');

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>PayTyp-'||v_dataCur_1.PAYTYP||'：產製完成');
            end Loop;

             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生改匯核定清單檔及核定清單明細檔(for 老年年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

               v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                     --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --procedure sp_BA_genPFMPFDRPT_6_L End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_6_K
        PURPOSE:         產生改匯核定清單檔及核定清單明細檔(for 失能年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua
        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_6_K (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      Varchar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '6';
        v_paycode          varChar2(1)  := 'K';
        v_paykind          varChar2(2)  := '35';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
                  ,t.PAYTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2,v_paytyp varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.PAYTYP
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.PAYTYP,' ') = nvl(v_paytyp,' ')
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2,v_paytyp varChar2) is
            select t.APNO
                  ,t.SEQNO
                  ,t.ORIISSUYM
                  ,t.BRISSUYM
                  ,t.ISSUYM
                  ,t.PAYYM
                  ,t.TRANSACTIONID
                  ,t.TRANSACTIONSEQ
                  ,t.RECHKDATE
                  ,t.BENTYP
                  ,t.BENIDN
                  ,t.BENNAME
                  ,t.PAYTYP
                  ,t.PAYBANKID
                  ,t.BRANCHID
                  ,t.PAYEEACC
                  ,t.ACCIDN
                  ,t.ACCNAME
                  ,t.REMITAMT
              from BAREGIVEDTL t
             where t.APNO like (v_paycode||'%')
               and t.ISSUYM = v_i_issuym
               and t.AFMK = '2'
               and nvl(trim(t.WORKMK),' ') <> 'Y'
               and (t.AFCHKDATE is not null and nvl(trim(t.AFCHKDATE),' ')<>' ')
               and (t.AFPAYDATE is not null and nvl(trim(t.AFPAYDATE),' ')<>' ')
               and t.RECHKDATE = v_i_chkdate
               and t.AFPAYDATE = v_i_paydate
               and t.CPRNDATE = v_i_cprndt
               and t.REMITAMT > 0
               and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_paytyp,' ')
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
             order by t.APNO,t.SEQNO,t.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_6_K';
            v_g_flag     := '0';
            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.PAYTYP) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.PAYTYP) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_1.PAYTYP,v_dataCur_2.ACTCR);
                        v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),1,3);
                        v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),4,1);
                        v_issue_amt        := v_issue_amt+v_dataCur_3.REMITAMT;
                        v_accept_amt       := v_accept_amt+v_dataCur_3.REMITAMT;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := (v_dataCur_3.APNO
                                             ||v_dataCur_3.SEQNO
                                             ||v_dataCur_3.ORIISSUYM
                                             ||v_dataCur_3.BRISSUYM
                                             ||v_dataCur_3.ISSUYM
                                             ||v_dataCur_3.PAYYM
                                             ||v_dataCur_3.TRANSACTIONID
                                             ||v_dataCur_3.TRANSACTIONSEQ
                                             ||v_dataCur_3.RECHKDATE);
                        v_payeeacc         := (v_dataCur_3.PAYBANKID||v_dataCur_3.BRANCHID||v_dataCur_3.PAYEEACC);


                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,v_dataCur_3.BENTYP
                                            ,v_dataCur_3.ACCIDN
                                            ,v_dataCur_3.ACCNAME
                                            ,v_dataCur_3.PAYTYP
                                            ,v_payeeacc
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAREGIVEDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ORIISSUYM = v_dataCur_3.ORIISSUYM
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.RECHKDATE = v_i_chkdate
                           and t.AFPAYDATE = v_i_paydate
                           and t.CPRNDATE = v_i_cprndt
                           and t.REMITAMT > 0
                           and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ');

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,v_dataCur_1.PAYTYP
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,v_dataCur_1.PAYTYP
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ');

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>PayTyp-'||v_dataCur_1.PAYTYP||'：產製完成');
            end Loop;
             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'0',
               '產生改匯核定清單檔及核定清單明細檔(for 失能年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

               v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                     --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --procedure sp_BA_genPFMPFDRPT_6_K End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_6_S
        PURPOSE:         產生改匯核定清單檔及核定清單明細檔(for 遺屬年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

         PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua
        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_6_S (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '6';
        v_paycode          varChar2(1)  := 'S';
        v_paykind          varChar2(2)  := '55';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
                  ,t.PAYTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP,t.PAYTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2,v_paytyp varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.PAYTYP
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.PAYTYP,' ') = nvl(v_paytyp,' ')
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2,v_paytyp varChar2) is
            select t.APNO
                  ,t.SEQNO
                  ,t.ORIISSUYM
                  ,t.BRISSUYM
                  ,t.ISSUYM
                  ,t.PAYYM
                  ,t.TRANSACTIONID
                  ,t.TRANSACTIONSEQ
                  ,t.RECHKDATE
                  ,t.BENTYP
                  ,t.BENIDN
                  ,t.BENNAME
                  ,t.PAYTYP
                  ,t.PAYBANKID
                  ,t.BRANCHID
                  ,t.PAYEEACC
                  ,t.ACCIDN
                  ,t.ACCNAME
                  ,t.REMITAMT
              from BAREGIVEDTL t
             where t.APNO like (v_paycode||'%')
               and t.ISSUYM = v_i_issuym
               and t.AFMK = '2'
               and nvl(trim(t.WORKMK),' ') <> 'Y'
               and (t.AFCHKDATE is not null and nvl(trim(t.AFCHKDATE),' ')<>' ')
               and (t.AFPAYDATE is not null and nvl(trim(t.AFPAYDATE),' ')<>' ')
               and t.RECHKDATE = v_i_chkdate
               and t.AFPAYDATE = v_i_paydate
               and t.CPRNDATE = v_i_cprndt
               and t.REMITAMT > 0
               and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_paytyp,' ')
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
             order by t.APNO,t.SEQNO,t.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_6_S';
            v_g_flag     := '0';
            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.PAYTYP) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.PAYTYP) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_1.PAYTYP,v_dataCur_2.ACTCR);
                        v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),1,3);
                        v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_1.PAYTYP),4,1);
                        v_issue_amt        := v_issue_amt+v_dataCur_3.REMITAMT;
                        v_accept_amt       := v_accept_amt+v_dataCur_3.REMITAMT;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := (v_dataCur_3.APNO
                                             ||v_dataCur_3.SEQNO
                                             ||v_dataCur_3.ORIISSUYM
                                             ||v_dataCur_3.BRISSUYM
                                             ||v_dataCur_3.ISSUYM
                                             ||v_dataCur_3.PAYYM
                                             ||v_dataCur_3.TRANSACTIONID
                                             ||v_dataCur_3.TRANSACTIONSEQ
                                             ||v_dataCur_3.RECHKDATE);
                        v_payeeacc         := (v_dataCur_3.PAYBANKID||v_dataCur_3.BRANCHID||v_dataCur_3.PAYEEACC);


                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,v_dataCur_3.BENTYP
                                            ,v_dataCur_3.ACCIDN
                                            ,v_dataCur_3.ACCNAME
                                            ,v_dataCur_3.PAYTYP
                                            ,v_payeeacc
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_3.REMITAMT
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAREGIVEDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ORIISSUYM = v_dataCur_3.ORIISSUYM
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.RECHKDATE = v_i_chkdate
                           and t.AFPAYDATE = v_i_paydate
                           and t.CPRNDATE = v_i_cprndt
                           and t.REMITAMT > 0
                           and nvl(deCode(t.PAYTYP,'2','1',t.PAYTYP),' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ');

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,v_dataCur_1.PAYTYP
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,v_dataCur_1.PAYTYP
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ')
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and nvl(t.PAYTYP,' ') = nvl(v_dataCur_1.PAYTYP,' ');

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>PayTyp-'||v_dataCur_1.PAYTYP||'：產製完成');
            end Loop;

             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生改匯核定清單檔及核定清單明細檔(for 遺屬年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

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
    --procedure sp_BA_genPFMPFDRPT_6_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_7_L
        PURPOSE:         產生應收款立帳核定清單檔及核定清單明細檔(for 老年年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --媒體產生序號
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
         PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_7_L (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varChar2,
        v_i_paydate        in      varChar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '7';
        v_paycode          varChar2(1)  := 'L';
        v_paykind          varChar2(2)  := '45';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.RPTTYP
             order by t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2 is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             order by to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3 is
            select t1.BAUNACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.BENIDNNO
                  ,t1.ISSUEAMT
                  ,t2.BENNAME
                  ,t2.PAYTYP
                  ,t2.PAYBANKID
                  ,t2.BRANCHID
                  ,t2.PAYEEACC
              from (select t11.BAUNACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.UNACPDATE as CHKDATE
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                      from BAUNACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.UNACPDATE = v_i_chkdate) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDNNO
                          ,t21.BENNAME
                          ,t21.PAYTYP
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                      from BAAPPBASE t21
                     where t21.APNO like (v_paycode||'%')
                       and t21.PAYKIND = deCode(v_i_payseqno,'2','36',t21.PAYKIND)
                       and (t21.PROCSTAT = '50' or (trim(t21.MANCHKMK) is not null and t21.PROCSTAT = '90'))
                   ) t2
              where t1.APNO = t2.APNO
                and t1.SEQNO = t2.SEQNO
                and t1.BENIDNNO = t2.BENIDNNO
              order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_7_L';
            v_g_flag := '0';
            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2 Loop
                    for v_dataCur_3 in c_dataCur_3 Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_3.PAYTYP,v_dataCur_2.ACTCR);
                        v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_3.PAYTYP),1,3);
                        v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_3.PAYTYP),4,1);
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAUNACPDTLID;
                        v_payeeacc         := (v_dataCur_3.PAYBANKID||v_dataCur_3.BRANCHID||v_dataCur_3.PAYEEACC);

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,v_dataCur_3.PAYTYP
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAUNACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.TXDATE = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAUNACPDTLID = v_dataCur_3.BAUNACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.PAYSEQNO = v_i_payseqno
                   and t.CPRNDATE = v_i_cprndt;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;
             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收款立帳核定清單檔及核定清單明細檔(for 老年年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

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
    --procedure sp_BA_genPFMPFDRPT_7_L End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_7_K
        PURPOSE:         產生應收款立帳核定清單檔及核定清單明細檔(for 失能年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --媒體產生序號
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
         PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_7_K (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_i_paydate        in      varchar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '7';
        v_paycode          varChar2(1)  := 'K';
        v_paykind          varChar2(2)  := '35';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        --搭配 PKG_BA_genPayRPTSUMAccount，應收款立帳普職互改不出(因為已經在撥付總表呈現過)
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t1.BAUNACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.BENIDNNO
                  ,t1.ISSUEAMT
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
                  ,t2.PAYTYP
                  ,t2.PAYBANKID
                  ,t2.BRANCHID
                  ,t2.PAYEEACC
              from (select t11.BAUNACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.UNACPDATE as CHKDATE
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAUNACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.UNACPDATE = v_i_chkdate
                       and nvl(t11.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t11.ADWKMK,' ') = nvl(v_adwkmk,' ')) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDNNO
                          ,t21.BENNAME
                          ,t21.PAYTYP
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                      from BAAPPBASE t21
                     where t21.APNO like (v_paycode||'%')
                       and t21.PAYKIND = deCode(v_i_payseqno,'2','36',t21.PAYKIND)
                       and (t21.PROCSTAT = '50' or (trim(t21.MANCHKMK) is not null and t21.PROCSTAT = '90'))
                   ) t2
              where t1.APNO = t2.APNO
                and t1.SEQNO = t2.SEQNO
                and t1.BENIDNNO = t2.BENIDNNO
                and nvl(t1.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                and nvl(t1.ADWKMK,' ') = nvl(v_adwkmk,' ')
              order by t1.APNO,t1.SEQNO,t1.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_7_K';
            v_g_flag     := '0';
            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_3.PAYTYP,v_dataCur_2.ACTCR);
                        v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_3.PAYTYP),1,3);
                        v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_3.PAYTYP),4,1);
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAUNACPDTLID;
                        v_payeeacc         := (v_dataCur_3.PAYBANKID||v_dataCur_3.BRANCHID||v_dataCur_3.PAYEEACC);

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,v_dataCur_3.PAYTYP
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAUNACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.TXDATE = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAUNACPDTLID = v_dataCur_3.BAUNACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.PAYSEQNO = v_i_payseqno
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;
             --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收款立帳核定清單檔及核定清單明細檔(for 失能年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

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
    --procedure sp_BA_genPFMPFDRPT_7_K End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_7_S
        PURPOSE:         產生應收款立帳核定清單檔及核定清單明細檔(for 遺屬年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_payseqno      (varChar2)       --媒體產生序號
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
         PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_7_S (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_i_paydate        in      varChar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '7';
        v_paycode          varChar2(1)  := 'S';
        v_paykind          varChar2(2)  := '55';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number 	    := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.NLWKMK,t.ADWKMK,t.NACHGMK,t.RPTTYP
             order by t.NLWKMK,t.ADWKMK,t.NACHGMK,t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
             order by t.NLWKMK,t.ADWKMK,t.NACHGMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t1.BAUNACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.BENIDNNO
                  ,t1.ISSUEAMT
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t1.NACHGMK
                  ,t2.BENNAME
                  ,t2.PAYTYP
                  ,t2.PAYBANKID
                  ,t2.BRANCHID
                  ,t2.PAYEEACC
              from (select t11.BAUNACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.UNACPDATE as CHKDATE
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                          ,t11.NACHGMK
                      from BAUNACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.UNACPDATE = v_i_chkdate
                       and nvl(t11.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t11.ADWKMK,' ') = nvl(v_adwkmk,' ')
                       and t11.NACHGMK is null) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDNNO
                          ,t21.BENNAME
                          ,t21.PAYTYP
                          ,t21.PAYBANKID
                          ,t21.BRANCHID
                          ,t21.PAYEEACC
                          ,t21.BENEVTREL
                      from BAAPPBASE t21
                     where t21.APNO like (v_paycode||'%')
                       and (t21.PROCSTAT = '50' or (trim(t21.MANCHKMK) is not null and t21.PROCSTAT = '90'))) t2
                where t1.APNO = t2.APNO
                  and t1.SEQNO = t2.SEQNO
                  and t2.SEQNO <> '0000'
                  and t2.BENEVTREL in ('2','3','4','5','6','7','O')
                  and nvl(t1.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                  and nvl(t1.ADWKMK,' ') = nvl(v_adwkmk,' ')
                order by t1.NLWKMK,t1.ADWKMK,t1.NACHGMK,t1.APNO,t1.PAYYM,t1.SEQNO;  --修改Order by條件 20190320 Modify by Angela

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_7_S';
            v_g_flag := '0';
            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := fn_BA_getBLIACCOUNTCODE(v_dataCur_3.PAYTYP,v_dataCur_2.ACTCR);
                        v_banty            := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_3.PAYTYP),1,3);
                        v_accountno        := substr(fn_BA_getBANTYACCOUNTNO(v_dataCur_3.PAYTYP),4,1);
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAUNACPDTLID;
                        v_payeeacc         := (v_dataCur_3.PAYBANKID||v_dataCur_3.BRANCHID||v_dataCur_3.PAYEEACC);

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,v_dataCur_3.PAYTYP
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAUNACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.TXDATE = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAUNACPDTLID = v_dataCur_3.BAUNACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.PAYSEQNO = v_i_payseqno
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收款立帳核定清單檔及核定清單明細檔(for 遺屬年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                    v_g_flag := '1';
                    v_o_flag := v_g_flag;

        end;
    --procedure sp_BA_genPFMPFDRPT_7_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_8_L
        PURPOSE:         產生應收款已收核定清單檔及核定清單明細檔(for 老年年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_payseqno      (varChar2)       --媒體產生序號
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
         PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_8_L (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varChar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '8';
        v_paycode          varChar2(1)  := 'L';
        v_paykind          varChar2(2)  := '45';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.RPTTYP
                  ,t.ISSUTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.RPTTYP,t.ISSUTYP
             order by t.RPTTYP,t.ISSUTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_issutyp varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.ISSUTYP = v_issutyp
             order by to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_issutyp varChar2) is
            /**
            --目前僅針對退匯收回及止付出應收已收核定清單
            --因給付收回在撥付總表已呈現，故在應收已收核定清單不出，只出清冊
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                      from BAACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK in ('0','F')
                       and v_issutyp in ('0','F')
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDNNO
                          ,t21.BENNAME
                      from BAAPPBASE t21
                     where t21.APNO like (v_paycode||'%')
                       and t21.PAYKIND = deCode(v_i_payseqno,'2','36',t21.PAYKIND)
                       and (t21.PROCSTAT = '50' or (trim(t21.MANCHKMK) is not null and t21.PROCSTAT = '90'))) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               --and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDNNO
             union all
            **/
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                      from BAACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK = 'D'
                       and v_issutyp = 'D'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDN
                          ,t21.BENNAME
                          ,t21.TRANSACTIONID
                          ,t21.TRANSACTIONSEQ
                      from BAREGIVEDTL t21
                     where t21.APNO like (v_paycode||'%')
                       and t21.MK = '4'
                       and nvl(trim(t21.WORKMK),' ') = 'Y'
                       and t21.AFPAYDATE = v_i_paydate
                       ) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               --and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDN
               and t1.TRANSACTIONID = t2.TRANSACTIONID
               and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
             union all
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                      from BAACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK = 'C'
                       and v_issutyp = 'C'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D') t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDN
                          ,t21.BENNAME
			  ,t21.PAYYM
                      from BAPFLBAC t21
                     where t21.APNO like (v_paycode||'%')
                       and t21.AFMK = '4'
                       and t21.STEXPNDRECMK = 'A'
                       and t21.STEXPNDRECDATE = v_i_chkdate) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               --and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDN
	       and t1.PAYYM = t2.PAYYM
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_8_L';
            v_g_flag := '0';
            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.ISSUTYP) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.ISSUTYP) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                           and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                                   and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                                   and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                           and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                           and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.PAYSEQNO = v_i_payseqno
                   and t.CPRNDATE = v_i_cprndt
                   and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;
            v_o_flag := v_g_flag;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收款已收核定清單檔及核定清單明細檔(for 老年年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                    --LOG BATCHJOB detail Add By Zehua 20140722
                    v_g_flag := '1';
                    v_o_flag := v_g_flag;
        end;
    --procedure sp_BA_genPFMPFDRPT_8_L End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_8_K
        PURPOSE:         產生應收款已收核定清單檔及核定清單明細檔(for 失能年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_payseqno      (varChar2)       --媒體產生序號
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
         PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_8_K (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '8';
        v_paycode          varChar2(1)  := 'K';
        v_paykind          varChar2(2)  := '35';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
                  ,t.ISSUTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP,t.ISSUTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP,t.ISSUTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2,v_issutyp varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
               and t.ISSUTYP = v_issutyp
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2,v_issutyp varChar2) is
            /**
            --目前僅針對退匯收回及止付出應收已收核定清單
            --因給付收回在撥付總表已呈現，故在應收已收核定清單不出，只出清冊
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK in ('0','F')
                       and v_issutyp in ('0','F')
                       and nvl(trim(t11.PROCFUN),' ') <> 'D'
                       and nvl(t11.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t11.ADWKMK,' ') = nvl(v_adwkmk,' ')
                       and t11.NACHGMK is null) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDNNO
                          ,t21.BENNAME
                      from BAAPPBASE t21
                     where t21.APNO like (v_paycode||'%')
                       and t21.PAYKIND = deCode(v_i_payseqno,'2','36',t21.PAYKIND)
                       and (t21.PROCSTAT = '50' or (trim(t21.MANCHKMK) is not null and t21.PROCSTAT = '90'))) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               --and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDNNO
             union all
            **/
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK = 'D'
                       and v_issutyp = 'D'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D'
                       and nvl(t11.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t11.ADWKMK,' ') = nvl(v_adwkmk,' ')
                       and t11.NACHGMK is null) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDN
                          ,t21.BENNAME
                          ,t21.TRANSACTIONID
                          ,t21.TRANSACTIONSEQ
                      from BAREGIVEDTL t21
                     where t21.APNO like (v_paycode||'%')
                       and t21.MK = '4'
                       and nvl(trim(t21.WORKMK),' ') = 'Y'
                       and t21.AFPAYDATE = v_i_paydate
                       and nvl(t21.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t21.ADWKMK,' ') = nvl(v_adwkmk,' ')) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               --and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDN
               and t1.TRANSACTIONID = t2.TRANSACTIONID
               and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
             union all
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK = 'C'
                       and v_issutyp = 'C'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D'
                       and nvl(t11.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t11.ADWKMK,' ') = nvl(v_adwkmk,' ')
                       and t11.NACHGMK is null) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDN
                          ,t21.BENNAME
			  ,t21.PAYYM
                      from BAPFLBAC t21
                     where t21.APNO like (v_paycode||'%')
                       and t21.AFMK = '4'
                       and t21.STEXPNDRECMK = 'A'
                       and t21.STEXPNDRECDATE = v_i_chkdate
                       and nvl(t21.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t21.ADWKMK,' ') = nvl(v_adwkmk,' ')) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               --and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDN
               and t1.PAYYM = t2.PAYYM
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_8_K';
            v_g_flag     := '0';
            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.ISSUTYP) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.ISSUTYP) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                           and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                                   and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                                   and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                           and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                           and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.PAYSEQNO = v_i_payseqno
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null
                   and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收款已收核定清單檔及核定清單明細檔(for 失能年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

               v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --procedure sp_BA_genPFMPFDRPT_8_K End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_8_S
        PURPOSE:         產生應收款已收核定清單檔及核定清單明細檔(for 遺屬年金)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_paydate       (varChar2)       --核付日期
                        *v_i_payseqno      (varChar2)       --媒體產生序號
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
         PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_8_S (
        v_i_issuym         in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_payseqno       in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_bajobid        in      varchar2,
        v_o_flag           out     varchar2
    ) is
        v_rpttyp           varChar2(1)  := '8';
        v_paycode          varChar2(1)  := 'S';
        v_paykind          varChar2(2)  := '55';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
                  ,t.ISSUTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.ISSUTYP,t.NLWKMK,t.ADWKMK,t.NACHGMK,t.RPTTYP
             order by t.ISSUTYP,t.NLWKMK,t.ADWKMK,t.NACHGMK,t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2,v_issutyp varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.PAYCODE = v_paycode
               and t.ISSUYM = v_i_issuym
               and t.CHKDATE = v_i_chkdate
               and t.PAYSEQNO = v_i_payseqno
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
               and t.ISSUTYP = v_issutyp
             order by t.NLWKMK,t.ADWKMK,t.NACHGMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2,v_issutyp varChar2) is
            /**
            --目前僅針對退匯收回及止付出應收已收核定清單
            --因給付收回在撥付總表已呈現，故在應收已收核定清單不出，只出清冊
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t1.NACHGMK
                  ,t2.BENNAME
                  ,t1.ISSUTYP
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                          ,t11.NACHGMK 
                          ,t11.TYPEMK as ISSUTYP
                      from BAACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK in ('0','F')
                       and v_issutyp in ('0','F')
                       and nvl(trim(t11.PROCFUN),' ') <> 'D'
                       and nvl(t11.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t11.ADWKMK,' ') = nvl(v_adwkmk,' ')
                       and t11.NACHGMK is null) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDNNO
                          ,t21.BENNAME
                          ,t21.BENEVTREL
                      from BAAPPBASE t21
                     where t21.APNO like (v_paycode||'%')
                       and (t21.PROCSTAT = '50' or (trim(t21.MANCHKMK) is not null and t21.PROCSTAT = '90'))) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t2.SEQNO <> '0000'
               and t2.BENEVTREL in ('2','3','4','5','6','7','O')
             union all
            **/
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t1.NACHGMK
                  ,t2.BENNAME
                  ,t1.ISSUTYP
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                          ,t11.NACHGMK
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.TYPEMK as ISSUTYP
                      from BAACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK = 'D'
                       and v_issutyp = 'D'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D'
                       and nvl(t11.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t11.ADWKMK,' ') = nvl(v_adwkmk,' ')
                       and t11.NACHGMK is null) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDN
                          ,t21.BENNAME
                          ,t22.BENEVTREL
                          ,t21.TRANSACTIONID
                          ,t21.TRANSACTIONSEQ
                      from BAREGIVEDTL t21, BAAPPBASE t22
                     where t21.APNO like (v_paycode||'%')
                       and t21.APNO = t22.APNO
                       and t21.SEQNO = t22.SEQNO
                       and t21.MK = '4'
                       and nvl(trim(t21.WORKMK),' ') = 'Y'
                       and t21.AFPAYDATE = v_i_paydate
                       and nvl(t21.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t21.ADWKMK,' ') = nvl(v_adwkmk,' ')) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.TRANSACTIONID = t2.TRANSACTIONID
               and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
               and t1.SEQNO <> '0000'
               and t2.BENEVTREL in ('2','3','4','5','6','7','O')
             union all
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,v_i_issuym as ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t1.NACHGMK
                  ,t2.BENNAME
                  ,t1.ISSUTYP
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                          ,t11.NACHGMK
                          ,t11.TYPEMK as ISSUTYP
                      from BAACPDTL t11
                     where t11.APNO like (v_paycode||'%')
                       and t11.ISSUYM = v_i_issuym
                       and t11.ACPDATE = v_i_chkdate
                       and t11.TYPEMK = 'C'
                       and v_issutyp = 'C'
                       and nvl(trim(t11.PROCFUN),' ') <> 'D'
                       and nvl(t11.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t11.ADWKMK,' ') = nvl(v_adwkmk,' ')
                       and t11.NACHGMK is null) t1
                  ,(select t21.APNO
                          ,t21.SEQNO
                          ,t21.BENIDS
                          ,t21.BENIDN
                          ,t21.BENNAME
			              ,t21.PAYYM
                          ,t22.BENEVTREL
                      from BAPFLBAC t21,BAAPPBASE t22
                     where t21.APNO like (v_paycode||'%')
                       and t21.AFMK = '4'
                       and t21.STEXPNDRECMK = 'A'
                       and t21.STEXPNDRECDATE = v_i_chkdate
                       and nvl(t21.NLWKMK,' ') = nvl(v_nlwkmk,' ')
                       and nvl(t21.ADWKMK,' ') = nvl(v_adwkmk,' ')) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.SEQNO <> '0000'
               and t2.BENEVTREL in ('2','3','4','5','6','7','O')
	           and t1.PAYYM = t2.PAYYM
             order by ISSUTYP,NLWKMK,ADWKMK,NACHGMK,APNO,PAYYM,SEQNO;  --修改Order by條件 20190320 Modify by Angela

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_8_S';
            v_g_flag := '0';
            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.ISSUTYP) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK,v_dataCur_1.ISSUTYP) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_dataCur_3.APNO
                           and t.SEQNO = v_dataCur_3.SEQNO
                           and t.ISSUYM = v_i_issuym
                           and t.PAYYM = v_dataCur_3.PAYYM
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                           and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                                   and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.ISSUYM = v_i_issuym
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.PAYSEQNO = v_i_payseqno
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                                   and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                           and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.ISSUYM = v_i_issuym
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.PAYSEQNO = v_i_payseqno
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0)
                           and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.ISSUYM = v_i_issuym
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.PAYSEQNO = v_i_payseqno
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null
                   and t.ISSUTYP = v_dataCur_1.ISSUTYP;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'0',
               '產生應收款已收核定清單檔及核定清單明細檔(for 遺屬年金)資料處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

               v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                     sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                       v_g_flag := '1';
                       v_o_flag := v_g_flag;
        end;
    --procedure sp_BA_genPFMPFDRPT_8_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_12_L
        PURPOSE:         產生應收已收核定清單(單筆退現收回)檔及核定清單明細檔(for 老年年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(已收日期)
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
         PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/17  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_12_L (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(1)  := '12';
        v_paycode          varChar2(1)  := 'L';
        v_paykind          varChar2(2)  := '45';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.RPTTYP
             order by t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2 is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             order by to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3 is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.ACPDATE = v_i_chkdate
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
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_12_L';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2 Loop
                    for v_dataCur_3 in c_dataCur_3 Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收已收核定清單(單筆退現收回)檔及核定清單明細檔(for 老年年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_genPFMPFDRPT_12_L End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_12_K
        PURPOSE:         產生應收已收核定清單(單筆退現收回)檔及核定清單明細檔(for 失能年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(已收日期)
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
         PARAMETER(OUT):


        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_12_K (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(2)  := '12';
        v_paycode          varChar2(1)  := 'K';
        v_paykind          varChar2(2)  := '35';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.ACPDATE = v_i_chkdate
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
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_12_K';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '0',
               '產生應收已收核定清單(單筆退現收回)檔及核定清單明細檔(for 失能年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_genPFMPFDRPT_12_K End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_12_S
        PURPOSE:         產生應收已收核定清單(單筆退現收回)檔及核定清單明細檔(for 遺屬年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(已收日期)
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_12_S (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(1)  := '12';
        v_paycode          varChar2(1)  := 'S';
        v_paykind          varChar2(2)  := '55';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.ACPDATE = v_i_chkdate
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
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_12_S';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'0',
               '產生應收已收核定清單(單筆退現收回)檔及核定清單明細檔(for 遺屬年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_genPFMPFDRPT_12_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_13_L
        PURPOSE:         產生應收已收核定清單(單筆退匯收回)檔及核定清單明細檔(for 老年年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(已收日期)
                        *v_i_cprndt        (varChar2)       --列印日期

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/17  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_13_L (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(1)  := '13';
        v_paycode          varChar2(1)  := 'L';
        v_paykind          varChar2(2)  := '45';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.RPTTYP
             order by t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2 is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             order by to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3 is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
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
                     where t21.APNO = v_i_apno
                       and t21.SEQNO = v_i_seqno
                       and t21.MK = '4'
                       and nvl(trim(t21.WORKMK),' ') = 'Y'
                       and t21.AFPAYDATE = v_i_chkdate) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDN
               and t1.TRANSACTIONID = t2.TRANSACTIONID
               and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_13_L';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2 Loop
                    for v_dataCur_3 in c_dataCur_3 Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收已收核定清單(單筆退匯收回)檔及核定清單明細檔(for 老年年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_genPFMPFDRPT_13_L End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_13_K
        PURPOSE:         產生應收已收核定清單(單筆退匯收回)檔及核定清單明細檔(for 失能年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(已收日期)
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_13_K (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(1)  := '13';
        v_paycode          varChar2(1)  := 'K';
        v_paykind          varChar2(2)  := '35';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
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
                     where t21.APNO = v_i_apno
                       and t21.SEQNO = v_i_seqno
                       and t21.MK = '4'
                       and nvl(trim(t21.WORKMK),' ') = 'Y'
                       and t21.AFPAYDATE = v_i_chkdate) t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.BENIDS = t2.BENIDS
               and t1.BENIDNNO = t2.BENIDN
               and t1.TRANSACTIONID = t2.TRANSACTIONID
               and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_13_K';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收已收核定清單(單筆退匯收回)檔及核定清單明細檔(for 失能年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


        end;
    --procedure sp_BA_genPFMPFDRPT_13_K End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_13_S
        PURPOSE:         產生應收已收核定清單(單筆退匯收回)檔及核定清單明細檔(for 遺屬年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(已收日期)
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_13_S (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(1)  := '13';
        v_paycode          varChar2(1)  := 'S';
        v_paykind          varChar2(2)  := '55';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.ACPDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
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
                     where t21.APNO = t22.APNO
                       and t21.SEQNO = t22.SEQNO
                       and t21.APNO = v_i_apno
                       and t21.SEQNO = v_i_seqno
                       and t21.MK = '4'
                       and nvl(trim(t21.WORKMK),' ') = 'Y'
                       and t21.AFPAYDATE = v_i_chkdate) t2
            where t1.APNO = t2.APNO
              and t1.SEQNO = t2.SEQNO
              and t1.TRANSACTIONID = t2.TRANSACTIONID
              and t1.TRANSACTIONSEQ = t2.TRANSACTIONSEQ
              and t1.SEQNO <> '0000'
              and t2.BENEVTREL in ('2','3','4','5','6','7','O')
             order by APNO,SEQNO,PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_13_S';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.RPTROWS = v_lcnt
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收已收核定清單(單筆退匯收回)檔及核定清單明細檔(for 遺屬年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid ,'1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_genPFMPFDRPT_13_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_14_L
        PURPOSE:         產生應收已收註銷清冊(單筆退現收回註銷)檔及核定清單明細檔(for 老年年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(註銷日期)
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/17  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_14_L (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(1)  := '14';
        v_paycode          varChar2(1)  := 'L';
        v_paykind          varChar2(2)  := '45';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.RPTTYP
             order by t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2 is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             order by to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3 is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.CANCELDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.CANCELDATE = v_i_chkdate
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
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_14_L';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2 Loop
                    for v_dataCur_3 in c_dataCur_3 Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收已收註銷清冊(單筆退現收回註銷)檔及核定清單明細檔(for 老年年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_genPFMPFDRPT_14_L End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_14_K
        PURPOSE:         產生應收已收註銷清冊(單筆退現收回註銷)檔及核定清單明細檔(for 失能年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(註銷日期)
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_14_K (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(2)  := '14';
        v_paycode          varChar2(1)  := 'K';
        v_paykind          varChar2(2)  := '35';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.CANCELDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.CANCELDATE = v_i_chkdate
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
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_14_K';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null;

            end Loop;
            dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收已收註銷清冊(單筆退現收回註銷)檔及核定清單明細檔(for 失能年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_genPFMPFDRPT_14_K End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_14_S
        PURPOSE:         產生應收已收註銷清冊(單筆退現收回註銷)檔及核定清單明細檔(for 遺屬年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(註銷日期)
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_14_S (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(1)  := '14';
        v_paycode          varChar2(1)  := 'S';
        v_paykind          varChar2(2)  := '55';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.CANCELDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.CANCELDATE = v_i_chkdate
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
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_14_S';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收已收註銷清冊(單筆退現收回註銷)檔及核定清單明細檔(for 遺屬年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                      sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_genPFMPFDRPT_14_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_15_L
        PURPOSE:         產生應收已收註銷清冊(單筆退匯收回註銷)檔及核定清單明細檔(for 老年年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(註銷日期)
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/17  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_15_L (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(1)  := '15';
        v_paycode          varChar2(1)  := 'L';
        v_paykind          varChar2(2)  := '45';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             group by t.RPTTYP
             order by t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2 is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
             order by to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3 is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.CANCELDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.CANCELDATE = v_i_chkdate
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
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_15_L';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2 Loop
                    for v_dataCur_3 in c_dataCur_3 Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '0',
               '產生應收已收註銷清冊(單筆退匯收回註銷)檔及核定清單明細檔(for 老年年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


        end;
    --procedure sp_BA_genPFMPFDRPT_15_L End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_15_K
        PURPOSE:         產生應收已收註銷清冊(單筆退匯收回註銷)檔及核定清單明細檔(for 失能年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(註銷日期)
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_15_K (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varChar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(1)  := '15';
        v_paycode          varChar2(1)  := 'K';
        v_paykind          varChar2(2)  := '35';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.CANCELDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.CANCELDATE = v_i_chkdate
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
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_15_K';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收已收註銷清冊(單筆退匯收回註銷)檔及核定清單明細檔(for 失能年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_genPFMPFDRPT_15_K End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_15_S
        PURPOSE:         產生應收已收註銷清冊(單筆退匯收回註銷)檔及核定清單明細檔(for 遺屬年金)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_chkdate       (varChar2)       --核定日期(註銷日期)
                        *v_i_cprndt        (varChar2)       --列印日期
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/08/01  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_genPFMPFDRPT_15_S (
        v_i_apno           in      varChar2,
        v_i_seqno          in      varChar2,
        v_i_chkdate        in      varChar2,
        v_i_cprndt         in      varChar2,
        v_i_paydate        in      varchar2,
        v_i_bajobid        in      varchar2
    ) is
        v_rpttyp           varChar2(1)  := '15';
        v_paycode          varChar2(1)  := 'S';
        v_paykind          varChar2(2)  := '55';
        v_cprnpage         Number       := 0;
        v_putfilepage      Number       := 0;
        v_lcnt             Number       := 0;
        v_issue_amt        Number       := 0;
        v_accept_amt       Number       := 0;
        v_pfd_cnt          Number       := 0;
        v_rechkdept        varChar2(5)  := '';
        v_worktype         varChar2(3)  := '';
        v_js_resource      varChar2(20) := '';
        v_bli_account_code varChar2(4)  := '';
        v_banty            varChar2(3)  := '';
        v_accountno        varChar2(1)  := '';
        v_cdetail_keyfield varChar2(70) := '';
        v_payeeacc         varChar2(21) := '';
        v_beingpageflag    boolean      := false;

        --待產生PFM&PFD的報表種類
        Cursor c_dataCur_1 is
            select t.NLWKMK
                  ,t.ADWKMK
                  ,t.RPTTYP
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and t.NACHGMK is null
             group by t.NLWKMK,t.ADWKMK,t.RPTTYP
             order by t.NLWKMK,t.ADWKMK,t.RPTTYP;

        --待產生PFM&PFD的資料
        Cursor c_dataCur_2(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t.RPTTYP
                  ,t.PAYCODE
                  ,t.ISSUYM
                  ,t.CHKDATE
                  ,deCode(t.ACCOUNTSEQ,'0',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTDB
                  ,deCode(t.ACCOUNTSEQ,'1',t.ACCOUNTNO,t.OPPACCOUNTNO) as ACTCR
                  ,t.ACCOUNTMEMO
                  ,t.DATACD
                  ,t.ACTTITLEAPCODE
                  ,t.SEQNO
                  ,t.ACCEPTPAYTYPE
              from BAPAYRPTACCOUNT t
             where t.RPTTYP = v_rpttyp
               and t.APNO = v_i_apno
               and t.APSEQNO = v_i_seqno
               and t.PAYCODE = v_paycode
               and t.CHKDATE = v_i_chkdate
               and t.CPRNDATE = v_i_cprndt
               and t.ACCOUNTTOPFMK = 'Y'
               and nvl(t.NLWKMK,' ') = nvl(v_nlwkmk,' ')
               and nvl(t.ADWKMK,' ') = nvl(v_adwkmk,' ')
               and t.NACHGMK is null
             order by t.NLWKMK,t.ADWKMK,t.PAYTYP,t.ACCOUNTSEQ,to_Number(t.SEQNO);

        --待產生PFM&PFD的資料(寫PFD明細)
        Cursor c_dataCur_3(v_nlwkmk varChar2,v_adwkmk varChar2) is
            select t1.BAACPDTLID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.CHKDATE
                  ,t1.ISSUEAMT
                  ,t1.BENIDNNO
                  ,t1.NLWKMK
                  ,t1.ADWKMK
                  ,t2.BENNAME
              from (select t11.BAACPDTLID
                          ,t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.CANCELDATE as CHKDATE
                          ,t11.BENIDS
                          ,t11.BENIDNNO
                          ,t11.RECAMT as ISSUEAMT
                          ,t11.TRANSACTIONID
                          ,t11.TRANSACTIONSEQ
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                      from BAACPDTL t11
                     where t11.APNO = v_i_apno
                       and t11.SEQNO = v_i_seqno
                       and t11.CANCELDATE = v_i_chkdate
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
            v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_15_S';

            --讀取回寫PFM&PFD的分頁大類
            for v_dataCur_1 in c_dataCur_1 Loop
                v_lcnt          := 0;
                v_beingpageflag := false;

                --讀取各報表的業務別
                v_worktype := fn_BA_getWORKTYPE(v_paycode,v_dataCur_1.RPTTYP,' ',' ');

                --讀取報表的資料來源檔案
                v_js_resource := fn_BA_getJSRESOURCE(v_dataCur_1.RPTTYP);

                --讀取複核人員單位別
                v_rechkdept := fn_BA_getRECHKDEPT(v_paycode);

                --讀取回寫PFM&PFD的會計科目資料
                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                    for v_dataCur_3 in c_dataCur_3(v_dataCur_1.NLWKMK,v_dataCur_1.ADWKMK) Loop
                        v_cprnpage         := fn_BA_getCPRNPAGE(v_i_cprndt);
                        v_putfilepage      := fn_BA_getPUTFILEPAGE(v_i_cprndt,v_paykind);
                        v_lcnt             := v_lcnt+1;
                        v_bli_account_code := '';
                        v_banty            := '';
                        v_accountno        := '';
                        v_issue_amt        := v_issue_amt+v_dataCur_3.ISSUEAMT;
                        v_accept_amt       := 0;
                        v_pfd_cnt          := 1;
                        v_cdetail_keyfield := v_dataCur_3.BAACPDTLID;
                        v_payeeacc         := '';

                        sp_BA_insSinglePFPFD(v_i_cprndt
                                            ,v_cprnpage
                                            ,v_lcnt
                                            ,v_cdetail_keyfield
                                            ,'1'
                                            ,v_dataCur_3.BENIDNNO
                                            ,v_dataCur_3.BENNAME
                                            ,''
                                            ,v_payeeacc
                                            ,v_dataCur_3.ISSUEAMT
                                            ,0
                                            ,v_dataCur_2.ACCOUNTMEMO
                                            ,v_i_chkdate
                                            ,'Y'
                                            ,v_pfd_cnt
                                            ,v_bli_account_code
                                            ,v_dataCur_2.ACTTITLEAPCODE
                                            ,(v_dataCur_2.ACTDB||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTDB))
                                            ,(v_dataCur_2.ACTCR||fn_BA_chkACCOUNTNOMK(v_dataCur_2.ACTCR))
                                            ,v_dataCur_2.DATACD
                                            ,v_putfilepage
                                            ,v_dataCur_3.BENNAME
                                            ,v_paykind
                                            ,v_dataCur_3.APNO
                                            ,v_dataCur_3.SEQNO
                                            ,v_dataCur_3.PAYYM
                                            ,v_dataCur_3.ISSUYM
                                            ,v_i_bajobid
                                             );

                        --更新核定清單日期及頁次的相關Table及欄位
                        update BAPAYRPTRECORD t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAACPDTL t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTROWS = v_lcnt
                              ,t.CPRNPAGE = v_cprnpage
                              ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.BAACPDTLID = v_dataCur_3.BAACPDTLID;

                        --當PFD筆數=20筆時，需匯整20筆的PFD資料，寫入PFM
                        if v_lcnt=20 then
                            sp_BA_insPFPFM(v_i_cprndt
                                          ,v_cprnpage
                                          ,v_worktype
                                          ,v_paykind
                                          ,v_banty
                                          ,v_accountno
                                          ,''
                                          ,v_js_resource
                                          ,v_issue_amt
                                          ,v_accept_amt
                                          ,v_lcnt
                                          ,v_rechkdept
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_i_paydate
                                          ,v_putfilepage
                                          ,v_i_bajobid
                                           );

                            v_lcnt       := 0;
                            v_issue_amt  := 0;
                            v_accept_amt := 0;
                            v_pfd_cnt    := 0;

                            --更新核定清單日期及頁次的相關Table及欄位
                            if v_beingpageflag = false then
                                update BAPAYRPTSUM t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                update BAPAYRPTACCOUNT t
                                   set t.CPRNDATE = v_i_cprndt
                                      ,t.RPTPAGE = v_cprnpage
                                      ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t.RPTTYP = v_rpttyp
                                   and t.APNO = v_i_apno
                                   and t.APSEQNO = v_i_seqno
                                   and t.PAYCODE = v_paycode
                                   and t.CHKDATE = v_i_chkdate
                                   and t.CPRNDATE = v_i_cprndt
                                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                                   and t.NACHGMK is null
                                   and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                                v_beingpageflag := true;
                            end if;
                        end if;
                    end Loop;
                end Loop;

                --判斷是否還有尚未寫入PFM的PFD資料
                if v_lcnt>=1 then
                    sp_BA_insPFPFM(v_i_cprndt
                                  ,v_cprnpage
                                  ,v_worktype
                                  ,v_paykind
                                  ,v_banty
                                  ,v_accountno
                                  ,''
                                  ,v_js_resource
                                  ,v_issue_amt
                                  ,v_accept_amt
                                  ,v_lcnt
                                  ,v_rechkdept
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_i_paydate
                                  ,v_putfilepage
                                  ,v_i_bajobid
                                   );

                    v_lcnt       := 0;
                    v_issue_amt  := 0;
                    v_accept_amt := 0;
                    v_pfd_cnt    := 0;

                    --更新核定清單日期及頁次的相關Table及欄位
                    if v_beingpageflag = false then
                        update BAPAYRPTSUM t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        update BAPAYRPTACCOUNT t
                           set t.CPRNDATE = v_i_cprndt
                              ,t.RPTPAGE = v_cprnpage
                              ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.RPTTYP = v_rpttyp
                           and t.APNO = v_i_apno
                           and t.APSEQNO = v_i_seqno
                           and t.PAYCODE = v_paycode
                           and t.CHKDATE = v_i_chkdate
                           and t.CPRNDATE = v_i_cprndt
                           and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                           and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                           and t.NACHGMK is null
                           and (t.RPTPAGE is null or nvl(trim(t.RPTPAGE),' ')=' ' or nvl(trim(t.RPTPAGE),0)=0);

                        v_beingpageflag := true;
                    end if;
                end if;

                --更新分頁大類的最大頁次
                update BAPAYRPTACCOUNT t set t.ERPTPAGE = v_cprnpage
                 where t.RPTTYP = v_rpttyp
                   and t.APNO = v_i_apno
                   and t.APSEQNO = v_i_seqno
                   and t.PAYCODE = v_paycode
                   and t.CHKDATE = v_i_chkdate
                   and t.CPRNDATE = v_i_cprndt
                   and nvl(t.NLWKMK,' ') = nvl(v_dataCur_1.NLWKMK,' ')
                   and nvl(t.ADWKMK,' ') = nvl(v_dataCur_1.ADWKMK,' ')
                   and t.NACHGMK is null;

                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>產製完成');
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '產生應收已收註銷清冊(單筆退匯收回註銷)檔及核定清單明細檔(for 遺屬年金)資料處理完成',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


        end;
    --procedure sp_BA_genPFMPFDRPT_15_S End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_insPFPFM
        PURPOSE:         新增核定清單檔的資料(PFPFM)

        PARAMETER(IN):  *v_i_cprndt            (varChar2)     --清單印表日期
                        *v_i_cprnpage          (Number)       --清單印表頁次
                        *v_i_worktype          (varChar2)     --業務別
                        *v_i_accept_issue_kind (varChar2)     --核發種類
                        *v_i_banty             (varChar2)     --銀行別
                        *v_i_accountno         (varChar2)     --銀行帳戶別
                        *v_i_bookedbook        (varChar2)     --入帳方式(給付方式)
                        *v_i_js_resource       (varChar2)     --資料來源檔案
                        *v_i_issue_amt         (Number)       --本頁核定金額
                        *v_i_accept_amt        (Number)       --本頁實收付金額
                        *v_i_pfd_cnt           (Number)       --核定清單明細總行數
                        *v_i_rechkdept         (varChar2)     --複核人員單位別
                        *v_i_rckdate           (varChar2)     --複核日期
                        *v_i_confirm_dt        (varChar2)     --確認日期
                        *v_i_paydte            (varChar2)     --核付日期
                        *v_i_putfile_page      (Number)       --歸檔頁次
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/09/25  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_insPFPFM
    (
        v_i_cprndt             in      varChar2,
        v_i_cprnpage           in      Number,
        v_i_worktype           in      varChar2,
        v_i_accept_issue_kind  in      varChar2,
        v_i_banty              in      varChar2,
        v_i_accountno          in      varChar2,
        v_i_bookedbook         in      varChar2,
        v_i_js_resource        in      varChar2,
        v_i_issue_amt          in      Number,
        v_i_accept_amt         in      Number,
        v_i_pfd_cnt            in      Number,
        v_i_rechkdept          in      varChar2,
        v_i_rckdate            in      varChar2,
        v_i_confirm_dt         in      varChar2,
        v_i_paydte             in      varChar2,
        v_i_putfile_page       in      Number,
        v_i_bajobid            in      varchar2
    ) is
        begin
            v_g_errMsg := '';

            insert into PFPFM (INSKD                          --保險別
                              ,CPRNDT                         --清單印表日期
                              ,CPRNPAGE                       --清單印表頁次
                              ,WORKTYPE                       --業務別
                              ,WDCD                           --收支區分
                              ,ACCEPT_ISSUE_KIND              --核發種類
                              ,BANTY                          --銀行別
                              ,ACCOUNTNO                      --銀行帳戶別
                              ,BOOKEDBOOK                     --入帳方式(給付方式)
                              ,JS_RESOURCE                    --來源檔案(勞保年金給付核定檔=BADAPR)
                              ,ISSUE_AMT                      --本頁核定金額
                              ,ACCEPT_AMT                     --本頁實收付金額
                              ,PFD_CNT                        --核定清單明細總行數
                              ,RECHKDEPT                      --複核人員單位別
                              ,RECHKSTAFF                     --複核人員
                              ,RCKDATE                        --複核日期
                              ,CONFIRM_DT                     --確認日期
                              ,CONFIRM_PER                    --確認人員
                              ,PAYDTE                         --出納核付日期
                              ,PUTFILE_PAGE                   --歸檔頁次
                              ,BRANCHCODE                     --分局別
                            ) values (
                              '1'
                              ,v_i_cprndt
                              ,v_i_cprnpage
                              ,v_i_worktype
                              ,'2'
                              ,v_i_accept_issue_kind
                              ,v_i_banty
                              ,v_i_accountno
                              ,v_i_bookedbook
                              ,v_i_js_resource
                              ,v_i_issue_amt
                              ,v_i_accept_amt
                              ,v_i_pfd_cnt
                              ,v_i_rechkdept
                              ,'99999'
                              ,v_i_rckdate
                              ,v_i_confirm_dt
                              ,'99999'
                              ,v_i_paydte
                              ,v_i_putfile_page
                              ,'1'
                            );

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '0',
               '新增核定清單檔的資料(PFPFM)處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_insPFPFM';
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,   '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_insPFPFM End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_insBatchPFPFD
        PURPOSE:         新增核定清單明細檔的資料(批次)(PFPFD)

        PARAMETER(IN):  *v_i_cprndt            (varChar2)     --清單印表日期
                        *v_i_cprnpage          (Number)       --清單印表頁次
                        *v_i_lcnt              (Number)       --行次
                        *v_i_accept_pay_type   (varChar2)     --給付方式
                        *v_i_decide_amt        (Number)       --核定金額
                        *v_i_cash_amt          (Number)       --現金金額(應付金額)
                        *v_i_cmemo             (varChar2)     --特殊會計科目(XXX)中的XXX
                                                                特殊會計科目指同一會計科目，(XXX)為其明細
                        *v_i_chksdate          (varChar2)     --初核日期
                        *v_i_cnt               (Number)       --件數
                        *v_i_bli_account_code  (varChar2)     --局帳戶代號
                        *v_i_acttitle_ap_code  (varChar2)     --會計科目案類代號
                        *v_i_actdb             (varChar2)     --會計科目借方
                        *v_i_actcr             (varChar2)     --會計科目貸方
                        *v_i_data_cd           (varChar2)     --資料區分
                        *v_i_putfile_page      (Number)       --歸檔頁次
                        *v_i_accept_issue_cd   (varChar2)     --核發種類
                        *v_i_issuym            (varChar2)     --核定年月
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/09/25  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_insBatchPFPFD
    (
        v_i_cprndt             in      varChar2,
        v_i_cprnpage           in      Number,
        v_i_lcnt               in      Number,
        v_i_accept_pay_type    in      varChar2,
        v_i_decide_amt         in      Number,
        v_i_cash_amt           in      Number,
        v_i_cmemo              in      varChar2,
        v_i_chksdate           in      varChar2,
        v_i_cnt                in      Number,
        v_i_bli_account_code   in      varChar2,
        v_i_acttitle_ap_code   in      varChar2,
        v_i_actdb              in      varChar2,
        v_i_actcr              in      varChar2,
        v_i_data_cd            in      varChar2,
        v_i_putfile_page       in      Number,
        v_i_accept_issue_cd    in      varChar2,
        v_i_issuym             in      varChar2,
        v_i_bajobid            in      varchar2
    ) is
        begin
            v_g_errMsg := '';

            insert into PFPFD (INSKD                          --保險別
                              ,CPRNDT                         --清單印表日期
                              ,CPRNPAGE                       --清單印表頁次
                              ,LCNT                           --行次
                              ,ACCEPT_PAY_TYPE                --給付方式
                              ,DECIDE_AMT                     --核定金額
                              ,CASH_AMT                       --現金金額(應付金額)
                              ,CMEMO                          --特殊會計科目(XXX)中的XXX
                              ,CHKSTAFF                       --初核人員
                              ,CHKSDATE                       --初核日期
                              ,OPCFM                          --作業確認
                              ,CNT                            --件數
                              ,BLI_ACCOUNT_CODE               --局帳戶代號
                              ,ACTTITLE_AP_CODE               --會計科目案類代號
                              ,ACTDB                          --會計科目借方
                              ,ACTCR                          --會計科目貸方
                              ,DATA_CD                        --資料區分
                              ,PUTFILE_PAGE                   --歸檔頁次
                              ,ACCEPT_ISSUE_CD                --核發種類
                              ,ISSUYM                         --核定年月
                            ) values (
                              '1'
                              ,v_i_cprndt
                              ,v_i_cprnpage
                              ,v_i_lcnt
                              ,v_i_accept_pay_type
                              ,v_i_decide_amt
                              ,v_i_cash_amt
                              ,v_i_cmemo
                              ,'99999'
                              ,v_i_chksdate
                              ,'Y'
                              ,v_i_cnt
                              ,v_i_bli_account_code
                              ,v_i_acttitle_ap_code
                              ,v_i_actdb
                              ,v_i_actcr
                              ,v_i_data_cd
                              ,v_i_putfile_page
                              ,v_i_accept_issue_cd
                              ,v_i_issuym
                            );


            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '0',
               '新增核定清單明細檔的資料(批次)(PFPFD)處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_insBatchPFPFD';
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


        end;
    --procedure sp_BA_insBatchPFPFD End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.sp_BA_insSinglePFPFD
        PURPOSE:         新增核定清單明細檔的資料(單筆)(PFPFD)

        PARAMETER(IN):  *v_i_cprndt            (varChar2)     --清單印表日期
                        *v_i_cprnpage          (Number)       --清單印表頁次
                        *v_i_lcnt              (Number)       --行次
                        *v_i_cdetail_keyfield  (varChar2)     --清單明細關鍵欄
                        *v_i_per_unit_cd       (varChar2)     --單位或個人
                        *v_i_id_no             (varChar2)     --身分證統一號碼
                        *v_i_per_unit_name     (varChar2)     --單位或個人名稱
                        *v_i_accept_pay_type   (varChar2)     --給付方式
                        *v_i_account_no        (varChar2)     --全帳號
                        *v_i_decide_amt        (Number)       --核定金額
                        *v_i_cash_amt          (Number)       --現金金額(應付金額)
                        *v_i_cmemo             (varChar2)     --特殊會計科目(XXX)中的XXX
                                                                特殊會計科目指同一會計科目，(XXX)為其明細
                        *v_i_chksdate          (varChar2)     --初核日期
                        *v_i_opcfm             (varChar2)     --作業確認
                        *v_i_cnt               (Number)       --件數
                        *v_i_bli_account_code  (varChar2)     --局帳戶代號
                        *v_i_acttitle_ap_code  (varChar2)     --會計科目案類代碼
                        *v_i_actdb             (varChar2)     --會計科目借方
                        *v_i_actcr             (varChar2)     --會計科目貸方
                        *v_i_data_cd           (varChar2)     --資料區分
                        *v_i_putfile_page      (Number)       --歸檔頁次
                        *v_i_insuranced_name   (varChar2)     --被保險人姓名
                        *v_i_accept_issue_cd   (varChar2)     --核發種類
                        *v_i_apno              (varChar2)     --受理編號
                        *v_i_gvseq             (varChar2)     --受款人序
                        *v_i_payym             (varChar2)     --給付年月
                        *v_i_issuym             (varChar2)     --核定年月
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/09/25  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_insSinglePFPFD
    (
        v_i_cprndt             in      varChar2,
        v_i_cprnpage           in      Number,
        v_i_lcnt               in      Number,
        v_i_cdetail_keyfield   in      varChar2,
        v_i_per_unit_cd        in      varChar2,
        v_i_id_no              in      varChar2,
        v_i_per_unit_name      in      varChar2,
        v_i_accept_pay_type    in      varChar2,
        v_i_account_no         in      varChar2,
        v_i_decide_amt         in      varChar2,
        v_i_cash_amt           in      varChar2,
        v_i_cmemo              in      varChar2,
        v_i_chksdate           in      varChar2,
        v_i_opcfm              in      varChar2,
        v_i_cnt                in      Number,
        v_i_bli_account_code   in      varChar2,
        v_i_acttitle_ap_code   in      varChar2,
        v_i_actdb              in      varChar2,
        v_i_actcr              in      varChar2,
        v_i_data_cd            in      varChar2,
        v_i_putfile_page       in      Number,
        v_i_insuranced_name    in      varChar2,
        v_i_accept_issue_cd    in      varChar2,
        v_i_apno               in      varChar2,
        v_i_gvseq              in      varChar2,
        v_i_payym              in      varChar2,
        v_i_issuym             in      varChar2,
        v_i_bajobid            in      varchar2
    ) is
        begin
            v_g_errMsg := '';

            insert into PFPFD (INSKD                          --保險別
                              ,CPRNDT                         --清單印表日期
                              ,CPRNPAGE                       --清單印表頁次
                              ,LCNT                           --行次
                              ,CDETAIL_KEYFIELD               --清單明細關鍵欄
                              ,PER_UNIT_CD                    --單位或個人
                              ,ID_NO                          --身分證統一號碼
                              ,PER_UNIT_NAME                  --單位或個人名稱
                              ,ACCEPT_PAY_TYPE                --給付方式
                              ,ACCOUNT_NO                     --全帳號
                              ,DECIDE_AMT                     --核定金額
                              ,CASH_AMT                       --現金金額(應付金額)
                              ,CMEMO                          --特殊會計科目(XXX)中的XXX
                              ,CHKSTAFF                       --初核人員
                              ,CHKSDATE                       --初核日期
                              ,OPCFM                          --作業確認
                              ,CNT                            --件數
                              ,BLI_ACCOUNT_CODE               --局帳戶代號
                              ,ACTTITLE_AP_CODE               --會計科目案類代碼
                              ,ACTDB                          --會計科目借方
                              ,ACTCR                          --會計科目貸方
                              ,DATA_CD                        --資料區分
                              ,PUTFILE_PAGE                   --歸檔頁次
                              ,INSURANCED_NAME                --被保險人姓名
                              ,ACCEPT_ISSUE_CD                --核發種類
                              ,APNO                           --受理編號
                              ,GVSEQ                          --受款人序
                              ,PAYYM                          --給付年月
                              ,ISSUYM              --核定年月
                            ) values (
                              '1'
                              ,v_i_cprndt
                              ,v_i_cprnpage
                              ,v_i_lcnt
                              ,v_i_cdetail_keyfield
                              ,v_i_per_unit_cd
                              ,v_i_id_no
                              ,v_i_per_unit_name
                              ,v_i_accept_pay_type
                              ,v_i_account_no
                              ,v_i_decide_amt
                              ,v_i_cash_amt
                              ,v_i_cmemo
                              ,'99999'
                              ,v_i_chksdate
                              ,v_i_opcfm
                              ,v_i_cnt
                              ,v_i_bli_account_code
                              ,v_i_acttitle_ap_code
                              ,v_i_actdb
                              ,v_i_actcr
                              ,v_i_data_cd
                              ,v_i_putfile_page
                              ,v_i_insuranced_name
                              ,v_i_accept_issue_cd
                              ,v_i_apno
                              ,v_i_gvseq
                              ,v_i_payym
                              ,v_i_issuym
                            );

           --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '新增核定清單明細檔的資料(單筆)(PFPFD)處理完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    v_g_ProgName := 'PKG_BA_genPFMPFD.sp_BA_insSinglePFPFD';
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180419
                      sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_insSinglePFPFD End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.fn_BA_getCPRNPAGE
        PURPOSE:         讀取清單印表頁次。依傳入的清單印表日期查詢該日期目前最大的清單印表頁次+1。

        PARAMETER(IN):  *v_i_cprndt            (varChar2)       --清單印表日期

        PARAMETER(OUT):  v_o_rtncprnpage       (Number)         --清單印表頁次

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/09/05  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    function fn_BA_getCPRNPAGE (
        v_i_cprndt             in      varChar2
    ) return Number is
        v_o_rtncprnpage        Number := 0;

        begin
            begin
                select nvl(Max(t.CPRNPAGE),100000)+1
                  into v_o_rtncprnpage
                  from PFPFM t
                 where t.INSKD = '1'
                   and t.CPRNDT = v_i_cprndt
                   and t.CPRNPAGE >= 100000
                   and t.CPRNPAGE <= 150000;
            exception when others then
                v_o_rtncprnpage := 100001;
            end;
            return v_o_rtncprnpage;
        end;
    --function fn_BA_getCPRNPAGE

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.fn_BA_getPUTFILEPAGE
        PURPOSE:         讀取歸檔頁次。依傳入的清單印表日期+收繳核發種類查詢該日期目前最大的歸檔頁次+1。

        PARAMETER(IN):  *v_i_cprndt            (varChar2)       --清單印表日期
                        *v_i_paykind           (varChar2)       --收繳核發種類

        PARAMETER(OUT):  v_o_rtnputfilepage    (Number)         --歸檔頁次

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/09/05  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    function fn_BA_getPUTFILEPAGE (
        v_i_cprndt             in      varChar2,
        v_i_paykind            in      varChar2
    ) return Number is
        v_o_rtnputfilepage     Number := 0;

        begin
            begin
                select nvl(Max(t.PUTFILE_PAGE),0)+1
                  into v_o_rtnputfilepage
                  from PFPFM t
                 where t.INSKD = '1'
                   and t.CPRNDT = v_i_cprndt
                   and t.ACCEPT_ISSUE_KIND = v_i_paykind
                   and t.CPRNPAGE >= 100000
                   and t.CPRNPAGE <= 150000;
            exception when others then
                v_o_rtnputfilepage := 1;
            end;
            return v_o_rtnputfilepage;
        end;
    --function fn_BA_getPUTFILEPAGE

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.fn_BA_getWORKTYPE
        PURPOSE:         讀取業務別。依傳入的給付別、報表種類及關係查詢PFM對應的業務別。

        PARAMETER(IN):  *v_i_paycode           (varChar2)       --給付別
                        *v_i_rpttyp            (varChar2)       --報表種類
                        *v_i_benevtrel         (varChar2)       --關係
                        *v_i_nachgmk           (varChar2)       --普職互改註記

        PARAMETER(OUT):  v_o_rtnworktype       (varChar2)       --業務別

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/10/09  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    function fn_BA_getWORKTYPE (
        v_i_paycode            in      varChar2,
        v_i_rpttyp             in      varChar2,
        v_i_benevtrel          in      varChar2,
        v_i_nachgmk            in      varChar2
    ) return varChar2 is
        v_o_rtnworktype        varChar2(3) := '';

        begin
            --給付別=L(老年)
            if v_i_paycode = 'L' then
                --報表種類=1(月核定撥付總表)
                if v_i_rpttyp = '1' then
                    --關係=Z(補償金，支付代扣補償金)
                    if v_i_benevtrel = 'Z' then
                        v_o_rtnworktype := 'DZD';
                    --其他關係者
                    else
                        v_o_rtnworktype := 'ABA';
                    end if;

                --報表種類=5(退匯核定清單)
                elsif v_i_rpttyp = '5' then
                    v_o_rtnworktype := 'KKB';

                --報表種類=6(改匯核定清單)
                elsif v_i_rpttyp = '6' then
                    v_o_rtnworktype := 'KLA';

                --報表種類=7(應收款立帳核定清單)
                elsif v_i_rpttyp = '7' then
                    v_o_rtnworktype := 'FFA';

                --報表種類=8(應收已收核定清單)
                elsif v_i_rpttyp = '8' then
                    --WORKTYPE=GGA，乃是針對退匯收回及止付的業務別
                    --因給付收回在撥付總表已呈現，故在應收已收核定清單不出
                    --若日後需增列其他收回項目，WORKTYPE需變動
                    v_o_rtnworktype := 'GGA';

                --報表種類=12(應收已收核定清單-單筆退現收回)
                elsif v_i_rpttyp = '12' then
                    v_o_rtnworktype := 'GVA';

                --報表種類=13(應收已收核定清單-單筆退匯收回)
                elsif v_i_rpttyp = '13' then
                    v_o_rtnworktype := 'GGA';

                --報表種類=14(應收已收註銷清冊-單筆退現收回註銷)
                elsif v_i_rpttyp = '14' then
                    v_o_rtnworktype := 'GVA';

                --報表種類=15(應收已收註銷清冊-單筆退匯收回註銷)
                elsif v_i_rpttyp = '15' then
                    v_o_rtnworktype := 'GGA';

                else
                    v_o_rtnworktype := '';
                end if;

            --給付別=K(失能)
            elsif v_i_paycode = 'K' then
                --報表種類=1(月核定撥付總表)
                if v_i_rpttyp = '1' then
                    --普職互改註記=(空)
                    if nvl(trim(v_i_nachgmk),' ') = ' ' then
                        v_o_rtnworktype := 'ABA';
                    --普職互改註記<>(空)
                    else
                        v_o_rtnworktype := 'ADA';
                    end if;
                --報表種類=5(退匯核定清單)
                elsif v_i_rpttyp = '5' then
                    v_o_rtnworktype := 'KKB';

                --報表種類=6(改匯核定清單)
                elsif v_i_rpttyp = '6' then
                    v_o_rtnworktype := 'KLA';

                --報表種類=7(應收款立帳核定清單)
                elsif v_i_rpttyp = '7' then
                    v_o_rtnworktype := 'FFA';

                --報表種類=8(應收已收核定清單)
                elsif v_i_rpttyp = '8' then
                    --WORKTYPE=GGA，乃是針對退匯收回及止付的業務別
                    --因給付收回在撥付總表已呈現，故在應收已收核定清單不出
                    --若日後需增列其他收回項目，WORKTYPE需變動
                    v_o_rtnworktype := 'GGA';

                --報表種類=12(應收已收核定清單-單筆退現收回)
                elsif v_i_rpttyp = '12' then
                    v_o_rtnworktype := 'GVA';

                --報表種類=13(應收已收核定清單-單筆退匯收回)
                elsif v_i_rpttyp = '13' then
                    v_o_rtnworktype := 'GGA';

                --報表種類=14(應收已收註銷清冊-單筆退現收回註銷)
                elsif v_i_rpttyp = '14' then
                    v_o_rtnworktype := 'GVA';

                --報表種類=15(應收已收註銷清冊-單筆退匯收回註銷)
                elsif v_i_rpttyp = '15' then
                    v_o_rtnworktype := 'GGA';

                else
                    v_o_rtnworktype := '';
                end if;

            --給付別=S(遺屬)
            elsif v_i_paycode = 'S' then
                --報表種類=1(月核定撥付總表)
                if v_i_rpttyp = '1' then
                    --普職互改註記=(空)
                    if nvl(trim(v_i_nachgmk),' ') = ' ' then
                        v_o_rtnworktype := 'ABA';
                    --普職互改註記<>(空)
                    else
                        v_o_rtnworktype := 'ADA';
                    end if;

                --報表種類=5(退匯核定清單)
                elsif v_i_rpttyp = '5' then
                    v_o_rtnworktype := 'KKB';

                --報表種類=6(改匯核定清單)
                elsif v_i_rpttyp = '6' then
                    v_o_rtnworktype := 'KLA';

                --報表種類=7(應收款立帳核定清單)
                elsif v_i_rpttyp = '7' then
                    v_o_rtnworktype := 'FFA';

                --報表種類=8(應收已收核定清單)
                elsif v_i_rpttyp = '8' then
                    --WORKTYPE=GGA，乃是針對退匯收回及止付的業務別
                    --因給付收回在撥付總表已呈現，故在應收已收核定清單不出
                    --若日後需增列其他收回項目，WORKTYPE需變動
                    v_o_rtnworktype := 'GGA';

                --報表種類=12(應收已收核定清單-單筆退現收回)
                elsif v_i_rpttyp = '12' then
                    v_o_rtnworktype := 'GVA';

                --報表種類=13(應收已收核定清單-單筆退匯收回)
                elsif v_i_rpttyp = '13' then
                    v_o_rtnworktype := 'GGA';

                --報表種類=14(應收已收註銷清冊-單筆退現收回註銷)
                elsif v_i_rpttyp = '14' then
                    v_o_rtnworktype := 'GVA';

                --報表種類=15(應收已收註銷清冊-單筆退匯收回註銷)
                elsif v_i_rpttyp = '15' then
                    v_o_rtnworktype := 'GGA';

                else
                    v_o_rtnworktype := '';
                end if;
            else
                v_o_rtnworktype := '';
            end if;
            return v_o_rtnworktype;
        end;
    --function fn_BA_getWORKTYPE

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.fn_BA_getJSRESOURCE
        PURPOSE:         讀取報表資料來源檔案。依傳入的報表種類查詢報表資料來源檔案。

        PARAMETER(IN):  *v_i_rpttyp            (varChar2)       --報表種類

        PARAMETER(OUT):  v_o_rtnjsresource     (varChar2)       --業務別

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/10/11  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    function fn_BA_getJSRESOURCE (
        v_i_rpttyp             in      varChar2
    ) return varChar2 is
        v_o_rtnjsresource      varChar2(20) := '';

        begin
            --報表種類=1(月核定撥付總表)
            if v_i_rpttyp = '1' then
                v_o_rtnjsresource := 'BADAPR';

            --報表種類=5(退匯核定清單)
            elsif v_i_rpttyp = '5' then
                v_o_rtnjsresource := 'BAPFLBAC';

            --報表種類=6(改匯核定清單)
            elsif v_i_rpttyp = '6' then
                v_o_rtnjsresource := 'BAREGIVEDTL';

            --報表種類=7(應收款立帳核定清單)
            elsif v_i_rpttyp = '7' then
                v_o_rtnjsresource := 'BAUNACPDTL';

            --報表種類=8(應收已收核定清單)
            elsif v_i_rpttyp = '8' then
                v_o_rtnjsresource := 'BAACPDTL';

            --報表種類=12(應收已收核定清單-單筆退現收回)
            elsif v_i_rpttyp = '12' then
                v_o_rtnjsresource := 'BAACPDTL';

            --報表種類=13(應收已收核定清單-單筆退匯收回)
            elsif v_i_rpttyp = '13' then
                v_o_rtnjsresource := 'BAACPDTL';

            --報表種類=14(應收已收註銷清冊-單筆退現收回註銷)
            elsif v_i_rpttyp = '14' then
                v_o_rtnjsresource := 'BAACPDTL';

            --報表種類=15(應收已收註銷清冊-單筆退匯收回註銷)
            elsif v_i_rpttyp = '15' then
                v_o_rtnjsresource := 'BAACPDTL';

            else
                v_o_rtnjsresource := '';
            end if;

            return v_o_rtnjsresource;
        end;
    --function fn_BA_getJSRESOURCE

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.fn_BA_getRECHKDEPT
        PURPOSE:         讀取複核人員單位別。依傳入的給付別查詢該給付別的複核人員單位別。

        PARAMETER(IN):  *v_i_paycode           (varChar2)       --給付別

        PARAMETER(OUT):  v_o_rtnrechkdept      (varChar2)       --複核人員單位別

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/10/24  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    function fn_BA_getRECHKDEPT (
        v_i_paycode             in      varChar2
    ) return varChar2 is
        v_o_rtnrechkdept        varChar2(5) := '';

        begin
            begin
                select t.PARAMNAME into v_o_rtnrechkdept
                  from BAPARAM t
                 where t.PARAMGROUP = 'PFMRECHKDEPT'
                   and t.PARAMCODE = v_i_paycode;
            exception when others then
                v_o_rtnrechkdept := v_i_paycode;
            end;
            return v_o_rtnrechkdept;
        end;
    --function fn_BA_getRECHKDEPT

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.fn_BA_getBLIACCOUNTCODE
        PURPOSE:         讀取PFD.BLI_ACCOUNT_CODE的資料值。

        PARAMETER(IN):  *v_i_paytyp            (varChar2)     --入帳方式(給付方式)
                        *v_i_actcr             (varChar2)     --會計科目貸方

        PARAMETER(OUT):  v_o_bliaccountcode    (Number)       --PFD的局帳戶代號

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/10/24  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    function fn_BA_getBLIACCOUNTCODE (
        v_i_paytyp             in      varChar2,
        v_i_actcr              in      varChar2
    ) return varChar2 is
        v_o_bliaccountcode     varChar2(4) := '';

        begin
            --Modify by Angela 20141229 配合104年會計科目轉換
            /*if v_i_paytyp = '4' and v_i_actcr = '110212  7002  6' then
                v_o_bliaccountcode := '7002';
            else
                v_o_bliaccountcode := '0053';
            end if;*/
            --Modify by Angela 20150126 依據出納提供最新之會計科目修改
            /*if v_i_paytyp = '4' and v_i_actcr = '111212  7001  3' then
                v_o_bliaccountcode := '7001';
            else
                v_o_bliaccountcode := '0053';
            end if;*/
            if v_i_paytyp = '4' and v_i_actcr = '111212  7002  3' then
                v_o_bliaccountcode := '7002';
            else
                v_o_bliaccountcode := '0053';
            end if;
            return v_o_bliaccountcode;
        end;
    --function fn_BA_getBLIACCOUNTCODE

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.fn_BA_getBANTYACCOUNTNO
        PURPOSE:         讀取PFM.BANTY、PFD.ACCOUNTNO的資料值。

        PARAMETER(IN):  *v_i_paytyp            (varChar2)     --入帳方式(給付方式)

        PARAMETER(OUT):  v_o_bantyaccountno    (Number)       --PFM的銀行別及帳戶別

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/10/25  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    function fn_BA_getBANTYACCOUNTNO (
        v_i_paytyp             in      varChar2
    ) return varChar2 is
        v_o_bantyaccountno     varChar2(4) := '';

        begin
            if v_i_paytyp = '4' then
                v_o_bantyaccountno := '7002';
            else
                v_o_bantyaccountno := '0053';
            end if;
            return v_o_bantyaccountno;
        end;
    --function fn_BA_getBANTYACCOUNTNO

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPFMPFD.fn_BA_chkACCOUNTNOMK
        PURPOSE:         讀取會計科目檢核碼。

        PARAMETER(IN):  *v_i_accountno         (varChar2)     --會計科目代碼

        PARAMETER(OUT):  v_o_chkaccountnomk    (varChar2)     --會計科目檢核碼

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/11/27  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    function fn_BA_chkACCOUNTNOMK (
        v_i_accountno          in      varChar2
    ) return varChar2 is
        v_o_chkaccountnomk     varChar2(2) := '';
        v_sumNum               Number := 0;

        begin
            if length(v_i_accountno) = 13 then
                for i in 1..13 loop
                    if i <> 7 and i <> 8 then
                        v_sumNum := v_sumNum + (substr(v_i_accountno,i,1)*((i-1)+1));
                    end if;
                end Loop;

                v_sumNum := 11 - (v_sumNum mod 11);

                if v_sumNum = 10 then
                    v_o_chkaccountnomk := '0';
                elsif v_sumNum = 11 then
                    v_o_chkaccountnomk := 'A';
                else
                    v_o_chkaccountnomk := to_Char(v_sumNum);
                end if;
                v_o_chkaccountnomk := ' '||v_o_chkaccountnomk;
            else
                v_o_chkaccountnomk := '';
            end if;
            return v_o_chkaccountnomk;
        end;
    --function fn_BA_chkACCOUNTNOMK

end;

/
