CREATE OR REPLACE Package BA.PKG_BA_PRAPI
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_PRAPI
    PURPOSE:         勞保年金給付系統與出納系統資料介接API(for 出納系統介接)

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2009/07/16  Angela Wu    Created this Package.
    1.1   2023/01/18  William      babaweb-62, sp_BA_CancelAmendment要多一個參數(帳號)

    NOTES:
    1.各 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/
authid definer is
    v_g_rowCount             Number;
    v_g_procMsgCode          varChar2(2);
    v_g_procMsg              varChar2(4000);
    v_g_errMsg               varChar2(4000);
    v_g_ProgName             varChar2(200);

    procedure sp_BA_PayQuery (
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_issuym           in      varChar2,
        v_i_payym            in      varChar2,
        v_i_paykind          in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_o_apno             out     varChar2,
        v_o_seqno            out     varChar2,
        v_o_issukind         out     varChar2,
        v_o_bliaccount       out     varChar2,
        v_o_evtname          out     varChar2,
        v_o_benidnno         out     varChar2,
        v_o_benname          out     varChar2,
        v_o_issuym           out     varChar2,
        v_o_payym            out     varChar2,
        v_o_accidn           out     varChar2,
        v_o_accname          out     varChar2,
        v_o_aplpayamt        out     varChar2,
        v_o_paytyp           out     varChar2,
        v_o_payeeacc         out     varChar2,
        v_o_commzip          out     varChar2,
        v_o_commaddr         out     varChar2,
        v_o_commtel          out     varChar2,
        v_o_aplpaydate       out     varChar2,
        v_o_remitmk          out     varChar2,
        v_o_remitdate        out     varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    );

    procedure sp_BA_Amendment (
        v_i_transactionid    in      varChar2,
        v_i_transactionseq   in      varChar2,
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_afchkdate        in      varChar2,
        v_i_mk               in      varChar2,
        v_i_accidn           in      varChar2,
        v_i_accname          in      varChar2,
        v_i_paytyp           in      varChar2,
        v_i_payeeacc         in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_i_paydate          in      varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    );

    procedure sp_BA_CancelAmendment (
        v_i_transactionid    in      varChar2,
        v_i_transactionseq   in      varChar2,
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_i_acctno           in      varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    );

    procedure sp_BA_ManRemitAplpay (
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_remitdate        in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    );
    /*
    procedure sp_BA_ManRefundment (
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    );

    procedure sp_BA_CancelManRefundment (
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    );
    */
    function fn_BA_ChkInData (
        v_i_transactionid    in      varChar2,
        v_i_transactionseq   in      varChar2,
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_afchkdate        in      varChar2,
        v_i_mk               in      varChar2,
        v_i_accidn           in      varChar2,
        v_i_accname          in      varChar2,
        v_i_paytyp           in      varChar2,
        v_i_payeeacc         in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2
    ) return varChar2;

End;
/

CREATE OR REPLACE Package Body BA.PKG_BA_PRAPI
is
    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_PRAPI.sp_BA_PayQuery
        PURPOSE:         提供出納系統查詢勞保年金給付系統相關案件資料。

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_issuym        (varChar2)       --核定年月
                        *v_i_payym         (varChar2)       --給付年月
                        *v_i_paykind       (varChar2)       --給付種類
                        *v_i_procempno     (varChar2)       --作業人員
                        *v_i_procdeptid    (varChar2)       --作業人員單位ID
                        *v_i_procip        (varChar2)       --作業人員IP

        PARAMETER(OUT): *v_o_apno          (varChar2)       --受理編號
                        *v_o_seqno         (varChar2)       --受款人序
                        *v_o_issukind      (varChar2)       --核發種類
                        *v_o_bliaccount    (varChar2)       --局帳戶代號
                        *v_o_evtname       (varChar2)       --事故者姓名(被保險人姓名)
                        *v_o_benidnno      (varChar2)       --受款人身份證號
                        *v_o_benname       (varChar2)       --受款人姓名
                        *v_o_issuym        (varChar2)       --核定年月
                        *v_o_payym         (varChar2)       --給付年月
                        *v_o_accidn        (varChar2)       --戶名IDN
                        *v_o_accname       (varChar2)       --戶名
                        *v_o_aplpayamt     (varChar2)       --核發金額
                        *v_o_paytyp        (varChar2)       --核發方式/給付方式
                        *v_o_payeeacc      (varChar2)       --轉帳帳號(總行+分行+帳號)
                        *v_o_commzip       (varChar2)       --郵遞區號
                        *v_o_commaddr      (varChar2)       --地址
                        *v_o_commtel       (varChar2)       --電話
                        *v_o_aplpaydate    (varChar2)       --核付日期
                        *v_o_remitmk       (varChar2)       --帳務後續處理註記
                        *v_o_remitdate     (varChar2)       --帳務後續處理日期
                        *v_o_procMsgCode   (varChar2)       --回傳處理結果訊息代碼
                        *v_o_procMsg       (varChar2)       --回傳處理結果訊息

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.
        1.1   2017/12/07  ChungYu      加入執行期間的Log紀錄.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。
        2.於上方的PARAMETER(OUT)中,打"*"者為必傳出之參數值。

    ********************************************************************************/
    procedure sp_BA_PayQuery (
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_issuym           in      varChar2,
        v_i_payym            in      varChar2,
        v_i_paykind          in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_o_apno             out     varChar2,
        v_o_seqno            out     varChar2,
        v_o_issukind         out     varChar2,
        v_o_bliaccount       out     varChar2,
        v_o_evtname          out     varChar2,
        v_o_benidnno         out     varChar2,
        v_o_benname          out     varChar2,
        v_o_issuym           out     varChar2,
        v_o_payym            out     varChar2,
        v_o_accidn           out     varChar2,
        v_o_accname          out     varChar2,
        v_o_aplpayamt        out     varChar2,
        v_o_paytyp           out     varChar2,
        v_o_payeeacc         out     varChar2,
        v_o_commzip          out     varChar2,
        v_o_commaddr         out     varChar2,
        v_o_commtel          out     varChar2,
        v_o_aplpaydate       out     varChar2,
        v_o_remitmk          out     varChar2,
        v_o_remitdate        out     varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    ) is
        v_apno               varChar2(12);
        v_seqno              varChar2(4);
        v_paykind            varChar2(2);
        v_bliaccount         varChar2(4);
        v_evtname            varChar2(50 char);
        v_benidnno           varChar2(12);
        v_benname            varChar2(50 char);
        v_issuym             varChar2(6);
        v_payym              varChar2(6);
        v_accidn             varChar2(12);
        v_accname            varChar2(50 char);
        v_paytyp             varChar2(1);
        v_aplpayamt          Number(7);
        v_paybankid          varChar2(21);
        v_branchid           varChar2(4);
        v_payeeacc           varChar2(14);
        v_commzip            varChar2(6);
        v_commaddr           varChar2(240 char);
        v_commtel            varChar2(20);
        v_aplpaydate         varChar2(8);
        v_remitmk            varChar2(1);
        v_remitdate          varChar2(8);
        v_jobno              mmjoblog.job_no%TYPE;
        v_starttime          TIMESTAMP;

        begin
            v_g_ProgName := 'PKG_BA_PRAPI.sp_BA_PayQuery';
            v_g_rowCount := 0;
            v_g_procMsgCode := '0';
            v_g_procMsg := '';
            v_g_errMsg := '';
            v_apno := '';
            v_seqno := '';
            v_paykind := '';
            v_bliaccount := '0053';
            v_evtname := '';
            v_benidnno := '';
            v_benname := '';
            v_issuym := '';
            v_payym := '';
            v_accidn := '';
            v_accname := '';
            v_paytyp := '';
            v_aplpayamt := 0;
            v_paybankid := '';
            v_branchid := '';
            v_payeeacc := '';
            v_commzip := '';
            v_commaddr := '';
            v_commtel := '';
            v_aplpaydate := '';
            v_remitmk := '';
            v_remitdate := '';

            --  2017/12/07 寫入開始LOG Add By ChungYu
            v_jobno  := REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','');
            v_starttime := SYSTIMESTAMP;

            SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                             p_job_id => 'PKG_BA_PRAPI.sp_BA_PayQuery',
                             p_step   => '出納系統查詢勞保年金給付系統相關案件資料',
                             p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||'),'||
                                         '受理編號：('||v_i_apno||'),'||CHR(10)||
                                         '受款人序號：('||v_i_seqno||'),'||CHR(10)||
                                         '核定年月：('||v_i_issuym||'),'||CHR(10)||
                                         '給付年月：('||v_i_payym||'),'||CHR(10)||
                                         '給付種類：('||v_i_paykind||'),'||CHR(10)||
                                         '作業人員：('||v_i_procempno||'),'||CHR(10)||
                                         '作業人員單位ID：('||v_i_procdeptid||'),'||CHR(10)||
                                         '作業人員IP：('||v_i_procip||'),');

            --  2017/12/07 寫入開始LOG Add By ChungYu

            --檢核傳入的查詢條件:受理編號是否為空值
            if nvl(trim(v_i_apno),' ')=' ' then
                v_g_procMsgCode := '1';
                v_g_procMsg := v_g_procMsg || '受理編號不得為空值、';
            end if;

            --檢核傳入的查詢條件:受款人序號是否為空值
            if nvl(trim(v_i_seqno),' ')=' ' then
                v_g_procMsgCode := '1';
                v_g_procMsg := v_g_procMsg || '受款人序號不得為空值、';
            end if;

            --檢核傳入的查詢條件:核定年月是否為空值
            if nvl(trim(v_i_issuym),' ')=' ' then
                v_g_procMsgCode := '1';
                v_g_procMsg := v_g_procMsg || '核定年月不得為空值、';
            end if;

            --檢核傳入的查詢條件:給付年月是否為空值
            if nvl(trim(v_i_payym),' ')=' ' then
                v_g_procMsgCode := '1';
                v_g_procMsg := v_g_procMsg || '給付年月不得為空值、';
            end if;

            --檢核傳入的查詢條件:作業人員是否為空值
            if nvl(trim(v_i_procempno),' ')=' ' then
                v_g_procMsgCode := '1';
                v_g_procMsg := v_g_procMsg || '作業人員不得為空值、';
            end if;

            --檢核傳入的查詢條件:作業人員單位ID否為空值
            if nvl(trim(v_i_procdeptid),' ')=' ' then
                v_g_procMsgCode := '1';
                v_g_procMsg := v_g_procMsg || '作業人員單位ID不得為空值、';
            end if;

            --檢核傳入的查詢條件:作業人員IP是否為空值
            if nvl(trim(v_i_procip),' ')=' ' then
                v_g_procMsgCode := '1';
                v_g_procMsg := v_g_procMsg || '作業人員IP不得為空值、';
            end if;

            if v_g_procMsgCode = '0' then
                v_g_rowCount := 0;

                select Count(*) into v_g_rowCount
                  from (select tt1.BAAPPBASEID
                              ,tt1.APNO
                              ,tt1.SEQNO
                          from BAAPPBASE tt1
                         where tt1.APNO = v_i_apno
                           and tt1.SEQNO = v_i_seqno) t1
                      ,(select tt2.BAAPPBASEID
                              ,tt2.APNO
                              ,tt2.SEQNO
                          from BADAPR tt2
                         where tt2.APNO = v_i_apno
                           and tt2.SEQNO = v_i_seqno
                           and tt2.ISSUYM = v_i_issuym
                           and tt2.PAYYM = v_i_payym
                           and tt2.MTESTMK = 'F'
                           and tt2.PAYKIND = v_i_paykind) t2  --20150703 add by Angela
                 where t1.BAAPPBASEID = t2.BAAPPBASEID
                   and t1.APNO = t2.APNO
                   and t1.SEQNO = t2.SEQNO;

                if v_g_rowCount>0 then
                    --依所傳入的查詢條件,查詢勞保年金給付系統相關案件資料
                    select distinct
                           t1.APNO
                          ,t1.SEQNO
                          ,t2.PAYKIND                  --20150703 Modify by Angela
                          ,t1.EVTNAME
                          ,t1.BENIDNNO
                          ,t1.BENNAME
                          ,t2.ISSUYM
                          ,t2.PAYYM
                          ,t1.ACCIDN
                          ,t1.ACCNAME
                          --,t2.ACCIDN                  --20100601 Modify by Angela:出納要求,回傳最新的資料,故改取主檔的值
                          --,t2.ACCNAME                 --20100601 Modify by Angela:出納要求,回傳最新的資料,故改取主檔的值
                          ,t2.PAYTYP
                          ,t2.APLPAYAMT
                          ,t2.PAYBANKID
                          ,deCode(t2.PAYBANKID,'700',t2.BRANCHID,deCode(t2.PAYTYP,'1','0000',t2.BRANCHID)) as BRANCHID
                          ,t2.PAYEEACC
                          ,t1.COMMZIP
                          ,t1.COMMADDR
                          ,t1.TEL1
                          ,t2.APLPAYDATE
                          ,t2.REMITMK
                          ,t2.REMITDATE
                      into v_apno
                          ,v_seqno
                          ,v_paykind
                          ,v_evtname
                          ,v_benidnno
                          ,v_benname
                          ,v_issuym
                          ,v_payym
                          ,v_accidn
                          ,v_accname
                          ,v_paytyp
                          ,v_aplpayamt
                          ,v_paybankid
                          ,v_branchid
                          ,v_payeeacc
                          ,v_commzip
                          ,v_commaddr
                          ,v_commtel
                          ,v_aplpaydate
                          ,v_remitmk
                          ,v_remitdate
                      from (select tt1.BAAPPBASEID
                                  ,tt1.APNO
                                  ,tt1.SEQNO
                                  ,tt1.PAYKIND
                                  ,tt1.EVTNAME
                                  ,tt1.BENIDNNO
                                  ,tt1.BENNAME
                                  ,tt1.COMMZIP
                                  ,tt1.COMMADDR
                                  ,tt1.TEL1
                                  ,deCode(tt1.PAYTYP,'A',nvl(trim(tt1.ACCIDN),tt1.BENIDNNO),tt1.ACCIDN) ACCIDN
                                  ,deCode(tt1.PAYTYP,'A',nvl(trim(tt1.ACCNAME),tt1.BENNAME),tt1.ACCNAME) ACCNAME
                                  --,tt1.ACCIDN     --20130911 Modify by Angela:出納要求,若給付方式=A，且戶名IDN為空，回傳受款人身分證號
                                  --,tt1.ACCNAME    --20130911 Modify by Angela:出納要求,若給付方式=A，且戶名為空，回傳受款人姓名
                              from BAAPPBASE tt1
                             where tt1.APNO = v_i_apno
                               and tt1.SEQNO = v_i_seqno) t1
                          ,(select tt2.BAAPPBASEID
                                  ,tt2.APNO
                                  ,tt2.SEQNO
                                  ,tt2.ISSUYM
                                  ,tt2.PAYYM
                                  ,tt2.PAYKIND                  --20150703 add by Angela
                                  ,tt2.ACCIDN
                                  ,tt2.ACCNAME
                                  ,tt2.PAYTYP
                                  ,tt2.APLPAYAMT
                                  ,tt2.PAYBANKID
                                  ,tt2.BRANCHID
                                  ,tt2.PAYEEACC
                                  ,tt2.APLPAYDATE
                                  ,tt2.REMITMK
                                  ,tt2.REMITDATE
                              from BADAPR tt2
                             where tt2.APNO = v_i_apno
                               and tt2.SEQNO = v_i_seqno
                               and tt2.ISSUYM = v_i_issuym
                               and tt2.PAYYM = v_i_payym
                               and tt2.MTESTMK = 'F'
                               and tt2.PAYKIND = v_i_paykind) t2  --20150703 add by Angela
                     where t1.BAAPPBASEID = t2.BAAPPBASEID
                       and t1.APNO = t2.APNO
                       and t1.SEQNO = t2.SEQNO;

                    v_g_procMsgCode := '0';
                    v_g_procMsg := '';
                else
                    v_g_procMsgCode := '1';
                    v_g_procMsg := '(勞保年金給付系統)查無該受理案件的給付資料';
                end if;
            else
                v_g_procMsgCode := '1';
                v_g_procMsg := '(勞保年金給付系統)查詢受理案件資料作業失敗!!傳入參數錯誤:'||substr(v_g_procMsg,1,length(v_g_procMsg)-1);
            end if;

            --回傳查詢結果值
            v_o_apno := v_apno;
            v_o_seqno := v_seqno;
            v_o_issukind := v_paykind;
            v_o_bliaccount := v_bliaccount;
            v_o_evtname := v_evtname;
            v_o_benidnno := v_benidnno;
            v_o_benname := v_benname;
            v_o_issuym := v_issuym;
            v_o_payym := v_payym;
            v_o_accidn := v_accidn;
            v_o_accname := v_accname;
            v_o_paytyp := v_paytyp;
            v_o_aplpayamt := v_aplpayamt;
            v_o_payeeacc := LPAD(v_paybankid,3,'0')||LPAD(v_branchid,4,'0')||LPAD(v_payeeacc,14,'0');
            v_o_commzip := v_commzip;
            v_o_commaddr := v_commaddr;
            v_o_commtel := v_commtel;
            v_o_aplpaydate := v_aplpaydate;
            v_o_remitmk := v_remitmk;
            v_o_remitdate := v_remitdate;
            v_o_procMsgCode := v_g_procMsgCode;
            v_o_procMsg := v_g_procMsg;

            --  2017/12/07 寫入結束LOG Add By ChungYu

            SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                             p_job_id => 'PKG_BA_PRAPI.sp_BA_PayQuery',
                             p_step   => '出納系統查詢勞保年金給付系統相關案件資料',
                             p_memo   => '執行結果：('||v_g_procMsgCode||'),'||CHR(10)||
                                         '執行結果訊息：('||v_g_procMsg||'),');

            --  2017/12/07 寫入結束LOG Add By ChungYu
        End;
    --procedure sp_BA_PayQuery End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_PRAPI.sp_BA_Amendment
        PURPOSE:         提供出納系統執行改匯作業時,將改匯資料同步回勞保年金給付系統。

        PARAMETER(IN):  *v_i_transactionid       (varChar2)        --交易編號
                        *v_i_transactionseq      (varChar2)        --交易編號的序號
                        *v_i_apno                (varChar2)        --受理編號
                        *v_i_seqno               (varChar2)        --受款人序
                        *v_i_oriissuym           (varChar2)        --原始核定年月
                        *v_i_payym               (varChar2)        --給付年月
                        *v_i_afchkdate           (varChar2)        --改匯初核日期
                        *v_i_mk                  (varChar2)        --退改匯狀態
                        *v_i_accidn              (varChar2)        --戶名IDN
                        *v_i_accname             (varChar2)        --戶名
                        *v_i_paytyp              (varChar2)        --核發方式/給付方式
                        *v_i_payeeacc            (varChar2)        --轉帳帳號(總行+分行+帳號)
                        *v_i_procempno           (varChar2)        --作業人員
                        *v_i_procdeptid          (varChar2)        --作業人員單位ID
                        *v_i_procip              (varChar2)        --作業人員IP
                        *v_i_paydate             (varChar2)        --核付日期(入帳日期)

        PARAMETER(OUT): *v_o_procMsgCode         (varChar2)        --回傳處理結果訊息代碼
                        *v_o_procMsg             (varChar2)        --回傳處理結果訊息

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.
        1.1   2017/12/07  ChungYu      加入執行期間的Log紀錄.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。
        2.於上方的PARAMETER(OUT)中,打"*"者為必傳出之參數值。

    ********************************************************************************/
    procedure sp_BA_Amendment (
        v_i_transactionid    in      varChar2,
        v_i_transactionseq   in      varChar2,
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_afchkdate        in      varChar2,
        v_i_mk               in      varChar2,
        v_i_accidn           in      varChar2,
        v_i_accname          in      varChar2,
        v_i_paytyp           in      varChar2,
        v_i_payeeacc         in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_i_paydate          in      varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    ) is
        v_baappbaseID        Number;
        v_fieID              varChar2(2000);
        v_befimg             varChar2(2000);
        v_aftimg             varChar2(2000);
        v_accname_b          varChar2(50 char);
        v_accidn_b           varChar2(12);
        v_bankname_b         varChar2(120 char);
        v_paybankid_b        varChar2(3);
        v_branchid_b         varChar2(5);
        v_payeeacc_b         varChar2(14);
        v_paytyp_b           varChar2(1);
        v_accname_a          varChar2(50 char);
        v_accidn_a           varChar2(12);
        v_bankname_a         varChar2(120 char);
        v_paybankid_a        varChar2(3);
        v_branchid_a         varChar2(5);
        v_payeeacc_a         varChar2(14);
        v_paytyp_a           varChar2(1);
        v_bankname           varChar2(120 char);
        v_paybankid          varChar2(3);
        v_branchid           varChar2(4);
        v_payeeacc           varChar2(14);
        v_issuym             varChar2(6);
        v_jobno              mmjoblog.job_no%TYPE;
        v_starttime          TIMESTAMP;
        --v_rs                 varChar2(1000);

        --查詢已核定(決行)的案件於給付主檔的帳號資料
        Cursor c_dataCur is
            select t1.BAAPPBASEID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ACCNAME
                  ,t1.ACCIDN
                  ,t1.BANKNAME
                  ,t1.PAYBANKID
                  ,deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID)) AS BRANCHID
                  ,t1.PAYEEACC
                  ,t1.PAYTYP
              from BAAPPBASE t1,BADAPR t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.APNO = v_i_apno
               and nvl(trim(t1.ACCSEQNO),t1.SEQNO) = v_i_seqno
               and t2.ISSUYM = v_i_oriissuym
               and t2.PAYYM = v_i_payym
               and t2.MTESTMK = 'F'
               and t2.MANCHKMK = 'Y'
               --and (t2.ACCEPTMK = 'Y' or nvl(trim(t2.ACCEPTMK),' ') = ' ')    -Mark by Angela 20190927
               and (t2.REMITDATE is not null and nvl(trim(t2.REMITDATE),' ')<>' ')
               and (t2.APLPAYMK is not null and nvl(trim(t2.APLPAYMK),' ')<>' ')
               and (t2.APLPAYDATE is not null and nvl(trim(t2.APLPAYDATE),' ')<>' ');

        begin
            v_g_ProgName := 'PKG_BA_PRAPI.sp_BA_Amendment';
            v_g_rowCount := 0;
            v_g_procMsgCode := '0';
            v_g_procMsg := '';
            v_g_errMsg := '';
            v_baappbaseID := 0;
            v_fieID := '';
            v_befimg := '';
            v_aftimg := '';
            v_accname_b := ' ';
            v_accidn_b := ' ';
            v_bankname_b := ' ';
            v_paybankid_b := ' ';
            v_branchid_b := ' ';
            v_payeeacc_b := ' ';
            v_paytyp_b := ' ';
            v_accname_a := ' ';
            v_accidn_a := ' ';
            v_bankname_a := ' ';
            v_paybankid_a := ' ';
            v_branchid_a := ' ';
            v_payeeacc_a := ' ';
            v_paytyp_a := ' ';
            v_o_procMsgCode := ' ';
            v_o_procMsg := ' ';
            v_paybankid := substr(v_i_payeeacc,1,3);
            v_branchid := substr(v_i_payeeacc,4,4);
            v_payeeacc := substr(v_i_payeeacc,8,14);
            v_bankname := ' ';
            v_issuym := ' ';
            v_jobno := '';

            --  2017/12/07 寫入開始LOG Add By ChungYu
            v_jobno  := REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','');
            v_starttime := SYSTIMESTAMP;

            SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                             p_job_id => 'PKG_BA_PRAPI.sp_BA_Amendment',
                             p_step   => '出納系統執行改匯作業將改匯資料同步回勞保年金給付系統',
                             p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||'),'||CHR(10)||
                                         '交易編號：('||v_i_transactionid||'),'||CHR(10)||
                                         '交易編號序號：('||v_i_transactionseq||'),'||CHR(10)||
                                         '受理編號：('||v_i_apno||'),'||CHR(10)||
                                         '受款人序號：('||v_i_seqno||'),'||CHR(10)||
                                         '核定年月：('||v_i_oriissuym||'),'||CHR(10)||
                                         '給付年月：('||v_i_payym||'),'||CHR(10)||
                                         '改匯初核日期：('||v_i_afchkdate||'),'||CHR(10)||
                                         '退改匯狀態：('||v_i_mk||'),'||CHR(10)||
                                         '戶名IDN：('||v_i_accidn||'),'||CHR(10)||
                                         '轉帳帳號：('||v_i_accname||'),'||CHR(10)||
                                         '給付方式：('||v_i_paytyp||'),'||CHR(10)||
                                         '轉帳帳號：('||v_i_payeeacc||'),'||CHR(10)||
                                         '作業人員：('||v_i_procempno||'),'||CHR(10)||
                                         '作業人員單位ID：('||v_i_procdeptid||'),'||CHR(10)||
                                         '作業人員IP：('||v_i_procip||'),');

            --  2017/12/07 寫入開始LOG Add By ChungYu

            --檢核傳入之參數值是否正確
            if nvl(fn_BA_ChkInData(v_i_transactionid
                                  ,v_i_transactionseq
                                  ,v_i_apno
                                  ,v_i_seqno
                                  ,v_i_oriissuym
                                  ,v_i_payym
                                  ,v_i_afchkdate
                                  ,v_i_mk
                                  ,v_i_accidn
                                  ,v_i_accname
                                  ,v_i_paytyp
                                  ,v_i_payeeacc
                                  ,v_i_procempno
                                  ,v_i_procdeptid
                                  ,v_i_procip),' ')=' ' then
                begin

                    --查詢改匯的核定年月(因改匯的月核時間可能會在下個月,故需找出實際月核的核定年月)
                    select Count(*) into v_g_rowCount
                      from BAPAISSUDATE t
                     where t.ISSUTYP in ('2','3','4')
                       and t.PAYCODE = substr(v_i_apno,1,1)
                       and t.ISSUDATE >= (v_i_afchkdate)
                       and t.BANKKIND = '1';

                    if v_g_rowCount>0 then
                        v_g_rowCount := 0;
                        select distinct substr(Min(t.ISSUDATE),1,6) into v_issuym
                          from BAPAISSUDATE t
                         where t.ISSUTYP in ('2','3','4')
                           and t.PAYCODE = substr(v_i_apno,1,1)
                           and t.ISSUDATE >= (v_i_afchkdate)
                           and t.BANKKIND = '1';

                        if nvl(trim(v_issuym),' ')=' ' then
                           v_issuym := to_Char(to_date(v_i_afchkdate,'YYYYMMDD'),'YYYYMM');
                        end if;
                    end if;

                    --查詢改匯資料檔是否有待改匯的核付資料(條件:受理編號、交易編號、交易編號的序號、原始核定年月、給付年月)
                    select Count(*) into v_g_rowCount
                      from BAREGIVEDTL t
                     where t.APNO = v_i_apno
                       and t.TRANSACTIONID = v_i_transactionid
                       and t.TRANSACTIONSEQ = v_i_transactionseq
                       and t.ORIISSUYM = v_i_oriissuym
                       and t.PAYYM = v_i_payym;

                    if v_g_rowCount>0 then
                        v_g_rowCount := 0;

                        --查詢改匯資料檔是否有待改匯的核付資料(條件:受理編號、交易編號、交易編號的序號、原始核定年月、給付年月、尚未沖抵、尚未核付)
                        select Count(*) into v_g_rowCount
                          from BAREGIVEDTL t
                         where t.APNO = v_i_apno
                           and t.TRANSACTIONID = v_i_transactionid
                           and t.TRANSACTIONSEQ = v_i_transactionseq
                           and t.ORIISSUYM = v_i_oriissuym
                           and t.PAYYM = v_i_payym
                           and t.MK in ('1','2')
                           and (trim(t.WORKMK) is null or nvl(trim(t.WORKMK),' ') = ' ')
                           and (trim(t.AFPAYDATE) is null or nvl(trim(t.AFPAYDATE),' ')=' ');

                        if v_g_rowCount>0 then
                            v_g_rowCount := 0;

                            select Count(*)
                              into v_g_rowCount
                              from BAAPPBASE t1,BADAPR t2
                             where t1.APNO = t2.APNO
                               and t1.SEQNO = t2.SEQNO
                               and t1.APNO = v_i_apno
                               and nvl(trim(t1.ACCSEQNO),t1.SEQNO) = v_i_seqno
                               and t2.ISSUYM = v_i_oriissuym
                               and t2.PAYYM = v_i_payym
                               and t2.MTESTMK = 'F'
                               and t2.MANCHKMK = 'Y'
                               --and (t2.ACCEPTMK = 'Y' or nvl(trim(t2.ACCEPTMK),' ') = ' ')    -Mark by Angela 20190927
                               and (t2.REMITDATE is not null and nvl(trim(t2.REMITDATE),' ')<>' ')
                               and (t2.APLPAYMK is not null and nvl(trim(t2.APLPAYMK),' ')<>' ')
                               and (t2.APLPAYDATE is not null and nvl(trim(t2.APLPAYDATE),' ')<>' ');

                            if v_g_rowCount>0 then
                                v_g_rowCount := 0;
                                v_g_procMsgCode := '0';
                                v_g_procMsg := '';

                                v_fieID := '';
                                v_befimg := '';
                                v_aftimg := '';
                                v_baappbaseID := 0;
                                v_accname_b := ' ';
                                v_accidn_b := ' ';
                                v_bankname_b := ' ';
                                v_paybankid_b := ' ';
                                v_branchid_b := ' ';
                                v_payeeacc_b := ' ';
                                v_paytyp_b := ' ';
                                v_accname_a := v_i_accname;
                                v_accidn_a := v_i_accidn;
                                v_paytyp_a := v_i_paytyp;

                                --給付方式='A'時,金融機構名稱、帳號(總行代碼)、帳號(分行代碼)、帳號資料皆為空
                                if v_i_paytyp = 'A' then
                                    v_bankname_a := ' ';
                                    v_paybankid_a := ' ';
                                    v_branchid_a := ' ';
                                    v_payeeacc_a := ' ';
                                else
                                    if v_paytyp_a='7' then
                                        if v_paybankid='700' then
                                            v_paytyp_a := '2';
                                        else
                                            v_paytyp_a := '1';
                                        end if;
                                    end if;

                                    if v_paytyp_a='1' then
                                        --PayTyp=1,取得金融機構名稱(銀行)

                                        --20130621 Modify by Angela:原讀國保金融機構資料檔(NPBANKLIST)判斷帳號資料，改讀現金給付中的行庫檔(BCBPF)
                                        /*
                                        select Count(*) into v_g_rowCount
                                          from NPBANKLIST t
                                         where (t.MAIN_CODE || t.BRANCH_CODE) = (substr(nvl(trim(v_i_payeeacc),' '),1,7));

                                        if v_g_rowCount>0 then
                                            v_g_rowCount := 0;
                                            select SHORT_NAME into v_bankname
                                              from NPBANKLIST t
                                             where (t.MAIN_CODE || t.BRANCH_CODE) = (substr(nvl(trim(v_i_payeeacc),' '),1,7));
                                        end if;
                                        */
                                        select Count(*) into v_g_rowCount
                                          from BCBPF t
                                         where (t.BNKALL || t.BNKONO) = (substr(nvl(trim(v_i_payeeacc),' '),1,7));

                                        if v_g_rowCount>0 then
                                            v_g_rowCount := 0;
                                            select t.BNKNME into v_bankname
                                              from BCBPF t
                                             where (t.BNKALL || t.BNKONO) = (substr(nvl(trim(v_i_payeeacc),' '),1,7));
                                        end if;

                                    elsif v_paytyp_a = '2' then
                                        --PayTyp=2,取得金融機構名稱(郵局)
                                        select Count(*) into v_g_rowCount
                                          from BAPARAM t
                                         where t.PARAMGROUP = 'PAYPOSTNAME'
                                           and t.PARAMCODE = (substr(nvl(trim(v_i_payeeacc),' '),1,7))
                                           and t.PARAMUSEMK = 'Y';

                                        if v_g_rowCount>0 then
                                            v_g_rowCount := 0;
                                            select PARAMNAME into v_bankname
                                              from BAPARAM t
                                             where t.PARAMGROUP = 'PAYPOSTNAME'
                                               and t.PARAMCODE = (substr(nvl(trim(v_i_payeeacc),' '),1,7))
                                               and t.PARAMUSEMK = 'Y';
                                        end if;
                                    end if;
                                    v_bankname_a := v_bankname;
                                    v_paybankid_a := v_paybankid;
                                    v_branchid_a := v_branchid;
                                    v_payeeacc_a := v_payeeacc;
                                end if;

                                begin
                                    for v_dataCur in c_dataCur Loop
                                        v_baappbaseID := v_dataCur.BAAPPBASEID;
                                        v_accname_b := v_dataCur.ACCNAME;
                                        v_accidn_b := v_dataCur.ACCIDN;
                                        v_bankname_b := v_dataCur.BANKNAME;
                                        v_paybankid_b := v_dataCur.PAYBANKID;
                                        v_branchid_b := v_dataCur.BRANCHID;
                                        v_payeeacc_b := v_dataCur.PAYEEACC;
                                        v_paytyp_b := v_dataCur.PAYTYP;

                                        --若帳號資料於出納系統改匯時已更改,除了需將新的欄位資料寫回給付主檔外,另外也將改前值及改後值一併寫入BAAPPLog
                                        --比對給付主檔中的"戶名"與出納系統變更改匯資料的"戶名"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if v_i_paytyp <> 'A' then
                                            if (nvl(trim(v_accname_b),' ') <> nvl(trim(v_accname_a),' ')) then
                                                 v_fieID := v_fieID || 'ACCNAME,';
                                                 v_befimg := v_befimg || v_accname_b || ',';
                                                 v_aftimg := v_aftimg || v_accname_a || ',';
                                                 PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                                   ,'U'
                                                                                   ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                                   ,v_i_procempno
                                                                                   ,'戶名'
                                                                                   ,v_accname_b
                                                                                   ,v_accname_a
                                                                                   );
                                            end if;

                                            --比對給付主檔中的"戶名IDN"與出納系統變更改匯資料的"戶名IDN"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                            if (nvl(trim(v_accidn_b),' ') <> nvl(trim(v_accidn_a),' ')) then
                                                 v_fieID := v_fieID || 'ACCIDN,';
                                                 v_befimg := v_befimg || v_accidn_b || ',';
                                                 v_aftimg := v_aftimg || v_accidn_a || ',';
                                                 PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                                   ,'U'
                                                                                   ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                                   ,v_i_procempno
                                                                                   ,'戶名IDN'
                                                                                   ,v_accidn_b
                                                                                   ,v_accidn_a
                                                                                   );
                                            end if;
                                        end if;

                                        --比對給付主檔中的"金融機構名稱"與出納系統變更改匯資料的"金融機構名稱"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if (nvl(trim(v_bankname_b),' ') <> nvl(trim(v_bankname_a),' ')) then
                                             v_fieID := v_fieID || 'BANKNAME,';
                                             v_befimg := v_befimg || v_bankname_b || ',';
                                             v_aftimg := v_aftimg || v_bankname_a || ',';
                                             PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                               ,'U'
                                                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                               ,v_i_procempno
                                                                               ,'金融機構名稱'
                                                                               ,v_bankname_b
                                                                               ,v_bankname_a
                                                                               );
                                        end if;

                                        --比對給付主檔中的"帳號(總行代碼)"與出納系統變更改匯資料的"帳號(總行代碼)"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if (nvl(trim(v_paybankid_b),' ') <> nvl(trim(v_paybankid_a),' ')) then
                                             v_fieID := v_fieID || 'PAYBANKID,';
                                             v_befimg := v_befimg || v_paybankid_b || ',';
                                             v_aftimg := v_aftimg || v_paybankid_a || ',';
                                             PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                               ,'U'
                                                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                               ,v_i_procempno
                                                                               ,'金融機構總代號'
                                                                               ,v_paybankid_b
                                                                               ,v_paybankid_a
                                                                               );
                                        end if;

                                        --比對給付主檔中的"帳號(分行代碼)"與出納系統變更改匯資料的"帳號(分行代碼)"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if (nvl(trim(v_branchid_b),' ') <> nvl(trim(v_branchid_a),' ')) then
                                             v_fieID := v_fieID || 'BRANCHID,';
                                             v_befimg := v_befimg || v_branchid_b || ',';
                                             v_aftimg := v_aftimg || v_branchid_a || ',';
                                             PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                               ,'U'
                                                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                               ,v_i_procempno
                                                                               ,'分支代號'
                                                                               ,v_branchid_b
                                                                               ,v_branchid_a
                                                                               );
                                        end if;

                                        --比對給付主檔中的"帳號"與出納系統變更改匯資料的"帳號"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if (nvl(trim(v_payeeacc_b),' ') <> nvl(trim(v_payeeacc_a),' ')) then
                                             v_fieID := v_fieID || 'PAYEEACC,';
                                             v_befimg := v_befimg || v_payeeacc_b || ',';
                                             v_aftimg := v_aftimg || v_payeeacc_a || ',';
                                             PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                               ,'U'
                                                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                               ,v_i_procempno
                                                                               ,'銀行帳號'
                                                                               ,v_payeeacc_b
                                                                               ,v_payeeacc_a
                                                                               );
                                        end if;

                                        --比對給付主檔中的"給付方式"與出納系統變更改匯資料的"給付方式"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if (nvl(trim(v_paytyp_b),' ') <> nvl(trim(v_paytyp_a),' ')) then
                                             v_fieID := v_fieID || 'PAYTYP,';
                                             v_befimg := v_befimg || v_paytyp_b || ',';
                                             v_aftimg := v_aftimg || v_paytyp_a || ',';
                                             PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                               ,'U'
                                                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                               ,v_i_procempno
                                                                               ,'給付方式'
                                                                               ,v_paytyp_b
                                                                               ,v_paytyp_a
                                                                               );
                                        end if;

                                        --若帳號資料於出納系統改匯時已更改,除了需回寫給付主檔的帳號相關欄位外,需一併記錄MMAPLog
                                        --Modify by 20120410 修改MMAPLog無法寫入的問題(因MMAPLog.PGMNAME欄位長度過長)
                                        if nvl(trim(v_fieID),' ') <> ' ' then
                                            PKG_BA_RecordLog.sp_BA_recMMAPLog('BAAPPBASE'
                                                                             ,'BAAPPBASEID='||v_baappbaseID
                                                                             ,(fn_BA_transDateValue(to_Char(Sysdate,'YYYYMMDD'),'1')||substr(to_Char(systimestamp,'HH24MISSFF'),1,8))
                                                                             ,'[BA]PKG_BA_PRAPI.sp_BA_Amendment'
                                                                             ,'DB(SP)'
                                                                             ,v_i_procdeptid
                                                                             ,v_i_procempno
                                                                             ,v_i_procip
                                                                             ,'U'
                                                                             ,v_fieID
                                                                             ,substr(v_befimg,1,length(v_befimg)-1)
                                                                             ,substr(v_aftimg,1,length(v_aftimg)-1)
                                                                             ,''
                                                                             ,''
                                                                             ,'0'
                                                                             );

                                            --更新給付主檔的受款人帳戶相關欄位
                                            update BAAPPBASE t set t.ACCNAME = deCode(v_paytyp_a,'A',t.ACCNAME,v_i_accname)
                                                                  ,t.ACCIDN = deCode(v_paytyp_a,'A',t.ACCIDN,v_i_accidn)
                                                                  ,t.BANKNAME = deCode(v_paytyp_a,'A','',v_bankname)
                                                                  ,t.PAYBANKID = deCode(v_paytyp_a,'A','',v_paybankid)
                                                                  ,t.BRANCHID = deCode(v_paytyp_a,'A','',v_branchid)
                                                                  ,t.PAYEEACC = deCode(v_paytyp_a,'A','',v_payeeacc)
                                                                  ,t.PAYTYP = v_paytyp_a
                                                                  ,t.UPDUSER = v_i_procempno
                                                                  ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                             where t.BAAPPBASEID = v_baappbaseID;
                                        end if;
                                    end Loop;
                                exception
                                    when others
                                        then
                                            v_g_errMsg := SQLErrm;
                                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                                            v_g_procMsgCode := '1';
                                            v_g_procMsg := '(勞保年金給付系統)改匯資料同步作業失敗:更新給付主檔帳戶資料失敗。失敗原因:'||v_g_errMsg;

                                            --  2017/12/07 寫入結束LOG Add By ChungYu

                                            SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                                             p_job_id => 'PKG_BA_PRAPI.sp_BA_Amendment',
                                                             p_step   => '出納系統執行改匯作業將改匯資料同步回勞保年金給付系統',
                                                             p_memo   => '執行結果：('||v_g_procMsgCode||'),'||CHR(10)||
                                                                         '執行結果訊息：('||v_g_procMsg||'),');

                                            --  2017/12/07 寫入結束LOG Add By ChungYu
                                end;

                                if v_g_procMsgCode = '0' then
                                    --於出納系統改匯完成時,更新改匯資料檔中的相關資料
                                    update BAREGIVEDTL t set t.ISSUTYP = 'A'
                                                            ,t.ISSUYM = v_issuym
                                                            ,t.AFCHKDATE = v_i_afchkdate
                                                            ,t.MK = deCode(v_i_mk,'8','2',v_i_mk)
                                                            ,t.ACCNAME = deCode(v_i_paytyp,'A',t.ACCNAME,v_i_accname)
                                                            ,t.ACCIDN = deCode(v_i_paytyp,'A',t.ACCIDN,v_i_accidn)
                                                            ,t.PAYBANKID = deCode(v_i_paytyp,'A','',v_paybankid)
                                                            ,t.BRANCHID = deCode(v_i_paytyp,'A','',v_branchid)
                                                            ,t.PAYEEACC = deCode(v_i_paytyp,'A','',v_payeeacc)
                                                            ,t.PAYTYP = v_i_paytyp
                                                            ,t.PROCUSER = v_i_procempno
                                                            ,t.PROCDEPTID = v_i_procdeptid
                                                            ,t.PROCIP = v_i_procip
                                                            ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                       where t.APNO = v_i_apno
                                                         and t.TRANSACTIONID = v_i_transactionid
                                                         and t.TRANSACTIONSEQ = v_i_transactionseq
                                                         and t.ORIISSUYM = v_i_oriissuym
                                                         and t.PAYYM = v_i_payym
                                                         and t.MK in ('1','2')
                                                         and (trim(t.WORKMK) is null or nvl(trim(t.WORKMK),' ') = ' ')
                                                         and (trim(t.AFPAYDATE) is null or nvl(trim(t.AFPAYDATE),' ')=' ');
                                end if;

                                --add by Angela 20121107
                                if v_i_paytyp = '7' then

                                    PKG_BA_PRAPI.sp_BA_ManRemitAplpay(v_i_apno,v_i_seqno,v_i_oriissuym,v_i_payym,v_i_paydate,v_i_procempno,v_i_procdeptid,v_i_procip,v_g_procMsgCode,v_g_procMsg);

                                    /*
                                    -- Removed by EthanChen 20220620 for babaweb-34
                                    if v_g_procMsgCode = '0' then
                                        PKG_BA_ProcCashier.sp_BA_expSingleChangRemit(v_i_apno,v_i_seqno,v_i_oriissuym,v_i_payym,v_i_paydate,v_g_procMsgCode,v_g_procMsg);
                                    end if;
                                    */
                                end if;
                            else
                                v_g_procMsgCode := '1';
                                v_g_procMsg := '(勞保年金給付系統)改匯資料同步作業失敗:給付主檔無該受理案件資料';
                            end if;
                        else
                            v_g_procMsgCode := '1';
                            v_g_procMsg := '(勞保年金給付系統)改匯資料同步作業失敗:';

                            v_g_rowCount := 0;

                            --查詢改匯資料同步作業失敗的原因(原因:已核付或已轉給付沖抵)
                            select Count(*) into v_g_rowCount
                              from BAREGIVEDTL t
                             where t.APNO = v_i_apno
                               and t.TRANSACTIONID = v_i_transactionid
                               and t.TRANSACTIONSEQ = v_i_transactionseq
                               and t.ORIISSUYM = v_i_oriissuym
                               and t.PAYYM = v_i_payym
                               and (trim(t.AFPAYDATE) is not null and nvl(trim(t.AFPAYDATE),' ')<>' ');

                            if v_g_rowCount>0 then
                                v_g_procMsg := '該筆案件的改匯金額已核付或已入帳';
                            else
                                v_g_procMsg := '該筆案件的改匯金額已轉為「給付收回」';
                            end if;
                        end if;

                    else
                        v_g_procMsgCode := '1';
                        v_g_procMsg := '查無該受理案件可改匯的資料:';

                        v_g_rowCount := 0;

                        --查詢改匯資料檔無待改匯的核付資料的原因(原因:交易編號錯誤)
                        select Count(*) into v_g_rowCount
                          from BAREGIVEDTL t
                         where t.TRANSACTIONID = v_i_transactionid
                           and t.TRANSACTIONSEQ = v_i_transactionseq;

                        if v_g_rowCount>0 then
                            v_g_rowCount := 0;

                            --查詢改匯資料檔無待改匯的核付資料的原因(原因:受理編號錯誤)
                            select Count(*) into v_g_rowCount
                              from BAREGIVEDTL t
                             where t.APNO = v_i_apno;

                            if v_g_rowCount>0 then
                                v_g_rowCount := 0;

                                --查詢改匯資料檔無待改匯的核付資料的原因(原因:受理編號與交易編號無關連性)
                                select Count(*) into v_g_rowCount
                                  from BAREGIVEDTL t
                                 where t.APNO = v_i_apno
                                   and t.TRANSACTIONID = v_i_transactionid
                                   and t.TRANSACTIONSEQ = v_i_transactionseq;

                                if v_g_rowCount>0 then
                                    v_g_procMsg := v_g_procMsg || '核定年月或給付年月錯誤(該筆案件資料待改匯的年月資料與傳入的年月資料不符)';
                                else
                                    v_g_procMsg := v_g_procMsg || '受理編號或交易編號錯誤(該受理編號與交易編號無關連性)';
                                end if;
                            else
                                v_g_procMsg := v_g_procMsg || '受理編號錯誤錯誤(無此受理編號)';
                            end if;
                        else
                            v_g_procMsg := v_g_procMsg || '交易編號錯誤(無此交易編號)';
                        end if;
                    end if;
                end;
            else
                v_g_procMsgCode := '1';
                v_g_procMsg := '(勞保年金給付系統)改匯資料同步作業失敗!!傳入參數錯誤:' ||
                               nvl(fn_BA_ChkInData(v_i_transactionid
                                                  ,v_i_transactionseq
                                                  ,v_i_apno
                                                  ,v_i_seqno
                                                  ,v_i_oriissuym
                                                  ,v_i_payym
                                                  ,v_i_afchkdate
                                                  ,v_i_mk
                                                  ,v_i_accidn
                                                  ,v_i_accname
                                                  ,v_i_paytyp
                                                  ,v_i_payeeacc
                                                  ,v_i_procempno
                                                  ,v_i_procdeptid
                                                  ,v_i_procip),' ');
            end if;
            v_o_procMsgCode := v_g_procMsgCode;
            v_o_procMsg := v_g_procMsg;

            --  2017/12/07 寫入結束LOG Add By ChungYu

            SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                             p_job_id => 'PKG_BA_PRAPI.sp_BA_Amendment',
                             p_step   => '出納系統執行改匯作業將改匯資料同步回勞保年金給付系統',
                             p_memo   => '執行結果：('||v_g_procMsgCode||'),'||CHR(10)||
                                         '執行結果訊息：('||v_g_procMsg||'),');

            --  2017/12/07 寫入結束LOG Add By ChungYu

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    v_o_procMsgCode := '1';
                    v_o_procMsg := '(勞保年金給付系統)改匯資料同步作業失敗!!失敗原因:'||v_g_errMsg;

                    --  2017/12/07 寫入結束LOG Add By ChungYu

                    SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                     p_job_id => 'PKG_BA_PRAPI.sp_BA_Amendment',
                                     p_step   => '出納系統執行改匯作業將改匯資料同步回勞保年金給付系統',
                                     p_memo   => '執行結果：('||v_g_procMsgCode||'),'||CHR(10)||
                                                 '執行結果訊息：('||v_g_procMsg||'),');

                    --  2017/12/07 寫入結束LOG Add By ChungYu
                    rollback;
        end;
    --procedure sp_BA_Amendment End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_PRAPI.sp_BA_CancelAmendment
        PURPOSE:         提供出納系統執行取消改匯作業時,將勞保年金給付系統中的帳戶資料更新回原退匯資料。

        PARAMETER(IN):  *v_i_transactionid       (varChar2)        --交易編號
                        *v_i_transactionseq      (varChar2)        --交易編號的序號
                        *v_i_apno                (varChar2)        --受理編號
                        *v_i_seqno               (varChar2)        --受款人序
                        *v_i_oriissuym           (varChar2)        --原始核定年月
                        *v_i_payym               (varChar2)        --給付年月
                        *v_i_procempno           (varChar2)        --作業人員
                        *v_i_procdeptid          (varChar2)        --作業人員單位ID
                        *v_i_procip              (varChar2)        --作業人員IP
                        *v_i_acctno              (varChar2)        --帳號
        PARAMETER(OUT): *v_o_procMsgCode         (varChar2)        --回傳處理結果訊息代碼
                        *v_o_procMsg             (varChar2)        --回傳處理結果訊息

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/09/10  Angela Wu    Created this procedure.
        1.1   2017/12/07  ChungYu      加入執行期間的Log紀錄.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。
        2.於上方的PARAMETER(OUT)中,打"*"者為必傳出之參數值。

    ********************************************************************************/
    procedure sp_BA_CancelAmendment (
        v_i_transactionid    in      varChar2,
        v_i_transactionseq   in      varChar2,
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_i_acctno           in      varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    ) is
        v_baappbaseID        Number;
        v_fieID              varChar2(2000);
        v_befimg             varChar2(2000);
        v_aftimg             varChar2(2000);
        v_baappbase_updflag  Number;
        v_accname_b          varChar2(50 char);
        v_accidn_b           varChar2(12);
        v_bankname_b         varChar2(120 char);
        v_paybankid_b        varChar2(3);
        v_branchid_b         varChar2(5);
        v_payeeacc_b         varChar2(14);
        v_paytyp_b           varChar2(1);
        v_accname_a          varChar2(50 char);
        v_accidn_a           varChar2(12);
        v_bankname_a         varChar2(120 char);
        v_paybankid_a        varChar2(3);
        v_branchid_a         varChar2(5);
        v_payeeacc_a         varChar2(14);
        v_paytyp_a           varChar2(1);
        v_jobno              mmjoblog.job_no%TYPE;
        v_starttime          TIMESTAMP;
        --v_seqno              varChar2(4);
        --v_i_transactionseq   varChar2(2);

        --查詢已核定(決行)的案件於給付主檔的帳號資料
        Cursor c_dataCur(v_accseqno varChar2) is
            select t1.BAAPPBASEID
                  ,t1.APNO
                  ,t1.SEQNO
                  ,t1.ACCNAME as ACCNAME_B
                  ,t1.ACCIDN as ACCIDN_B
                  ,t1.PAYBANKID as PAYBANKID_B
                  ,deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID)) as BRANCHID_B
                  ,t1.PAYEEACC as PAYEEACC_B
                  ,t1.PAYTYP as PAYTYP_B
                  ,t1.BANKNAME as BANKNAME_B
                  ,t2.ACCNAME as ACCNAME_A
                  ,t2.ACCIDN as ACCIDN_A
                  ,t2.PAYBANKID as PAYBANKID_A
                  ,deCode(t2.PAYBANKID,'700',t2.BRANCHID,deCode(t2.PAYTYP,'1','0000',t2.BRANCHID)) as BRANCHID_A
                  ,t2.PAYEEACC as PAYEEACC_A
                  ,t2.PAYTYP as PAYTYP_A
              from BAAPPBASE t1,BAPFLBAC t2,BADAPR t3
             where t1.APNO = t2.APNO
               and t1.APNO = t3.APNO
               and t1.SEQNO = t3.SEQNO
               and t1.APNO = v_i_apno
               and nvl(trim(t1.ACCSEQNO),t1.SEQNO) = t2.SEQNO
               and nvl(trim(t1.ACCSEQNO),t1.SEQNO) = v_accseqno
               and t2.ORIISSUYM = v_i_oriissuym
               and t2.PAYYM = v_i_payym
               and t3.ISSUYM = v_i_oriissuym
               and t3.PAYYM = v_i_payym
               and t3.MTESTMK = 'F'
               and t3.MANCHKMK = 'Y'
               --and (t3.ACCEPTMK = 'Y' or nvl(trim(t3.ACCEPTMK),' ') = ' ')    -Mark by Angela 20190927
               and (t3.REMITDATE is not null and nvl(trim(t3.REMITDATE),' ')<>' ')
               and (t3.APLPAYMK is not null and nvl(trim(t3.APLPAYMK),' ')<>' ')
               and (t3.APLPAYDATE is not null and nvl(trim(t3.APLPAYDATE),' ')<>' ');

        begin
            v_g_ProgName := 'PKG_BA_PRAPI.sp_BA_CancelAmendment';
            v_g_rowCount := 0;
            v_g_procMsgCode := '0';
            v_g_procMsg := '';
            v_g_errMsg := '';
            v_baappbaseID := 0;
            v_fieID := '';
            v_befimg := '';
            v_aftimg := '';
            v_baappbase_updflag := 0;
            v_accname_b := ' ';
            v_accidn_b := ' ';
            v_bankname_b := ' ';
            v_paybankid_b := ' ';
            v_branchid_b := ' ';
            v_payeeacc_b := ' ';
            v_paytyp_b := ' ';
            v_accname_a := ' ';
            v_accidn_a := ' ';
            v_bankname_a := ' ';
            v_paybankid_a := ' ';
            v_branchid_a := ' ';
            v_payeeacc_a := ' ';
            v_paytyp_a := ' ';
            v_o_procMsgCode := ' ';
            v_o_procMsg := ' ';
            --v_seqno := '0000';
            --v_i_transactionseq := '1';

            begin

                --  2017/12/07 寫入開始LOG Add By ChungYu
                v_jobno  := REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','');
                v_starttime := SYSTIMESTAMP;

                SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                 p_job_id => 'PKG_BA_PRAPI.sp_BA_CancelAmend',
                                 p_step   => '取消改匯將BA帳戶資料更新回原退匯資料',
                                 p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||'),'||CHR(10)||
                                             '交易編號：('||v_i_transactionid||'),'||CHR(10)||
                                             '交易編號序號：('||v_i_transactionseq||'),'||CHR(10)||
                                             '受理編號：('||v_i_apno||'),'||CHR(10)||
                                             '受款人序號：('||v_i_seqno||'),'||CHR(10)||
                                             '核定年月：('||v_i_oriissuym||'),'||CHR(10)||
                                             '給付年月：('||v_i_payym||'),'||CHR(10)||
                                             '作業人員：('||v_i_procempno||'),'||CHR(10)||
                                             '作業人員單位ID：('||v_i_procdeptid||'),'||CHR(10)||
                                             '作業人員IP：('||v_i_procip||'),'||CHR(10)||
                                             '帳號：('||v_i_acctno||'),');

                 --  2017/12/07 寫入開始LOG Add By ChungYu


                /*if length(v_i_seqno)=4 then
                   v_seqno := v_i_seqno;
                end if;*/
                /*
                --查詢改匯資料檔中,該筆受理案件最大筆的退改匯次數資料(條件:受理編號、交易編號、原始核定年月、給付年月)
                select Max(t.TRANSACTIONSEQ) into v_i_transactionseq
                  from BAREGIVEDTL t
                 where t.APNO = v_i_apno
                   and t.TRANSACTIONID = v_i_transactionid
                   and t.ORIISSUYM = v_i_oriissuym
                   and t.PAYYM = v_i_payym;
                */
                --查詢改匯資料檔是否有改匯中的核付資料(條件:受理編號、交易編號、交易編號的序號、原始核定年月、給付年月)
                select Count(*) into v_g_rowCount
                  from BAREGIVEDTL t
                 where t.APNO = v_i_apno
                   and t.TRANSACTIONID = v_i_transactionid
                   and t.TRANSACTIONSEQ = v_i_transactionseq
                   and t.ORIISSUYM = v_i_oriissuym
                   and t.PAYYM = v_i_payym;

                if v_g_rowCount>0 then
                    v_g_rowCount := 0;

                    --查詢改匯資料檔是否有改匯中的核付資料(條件:受理編號、交易編號、交易編號的序號、原始核定年月、給付年月、尚未核付)
                    select Count(*) into v_g_rowCount
                      from BAREGIVEDTL t
                     where t.APNO = v_i_apno
                       and t.TRANSACTIONID = v_i_transactionid
                       and t.TRANSACTIONSEQ = v_i_transactionseq
                       and t.ORIISSUYM = v_i_oriissuym
                       and t.PAYYM = v_i_payym
                       and t.MK = '2'
                       and (trim(t.AFPAYDATE) is null or nvl(trim(t.AFPAYDATE),' ')=' ');

                    if v_g_rowCount>0 then
                        v_g_rowCount := 0;

                        --因出納系統的受款人序尚未擴檔,故先以給付系統中改匯資料檔的受款人序為主
                        /*select t.SEQNO into v_seqno
                          from BAREGIVEDTL t
                         where t.APNO = v_i_apno
                           and t.TRANSACTIONID = v_i_transactionid
                           and t.TRANSACTIONSEQ = v_i_transactionseq
                           and t.ORIISSUYM = v_i_oriissuym
                           and t.PAYYM = v_i_payym;*/

                        select Count(*)
                          into v_g_rowCount
                          from BAAPPBASE t1,BADAPR t2
                         where t1.APNO = t2.APNO
                           and t1.SEQNO = t2.SEQNO
                           and t1.APNO = v_i_apno
                           and nvl(trim(t1.ACCSEQNO),t1.SEQNO) = v_i_seqno
                           and t2.ISSUYM = v_i_oriissuym
                           and t2.PAYYM = v_i_payym
                           and t2.MTESTMK = 'F'
                           and t2.MANCHKMK = 'Y'
                           --and (t2.ACCEPTMK = 'Y' or nvl(trim(t2.ACCEPTMK),' ') = ' ')    -Mark by Angela 20190927
                           and (t2.REMITDATE is not null and nvl(trim(t2.REMITDATE),' ')<>' ')
                           and (t2.APLPAYMK is not null and nvl(trim(t2.APLPAYMK),' ')<>' ')
                           and (t2.APLPAYDATE is not null and nvl(trim(t2.APLPAYDATE),' ')<>' ');

                        if v_g_rowCount>0 then
                            v_g_rowCount := 0;
                            v_g_procMsgCode := '0';
                            v_g_procMsg := '';

                            v_fieID := '';
                            v_befimg := '';
                            v_aftimg := '';
                            v_baappbaseID := 0;
                            v_accname_b := ' ';
                            v_accidn_b := ' ';
                            v_bankname_b := ' ';
                            v_paybankid_b := ' ';
                            v_branchid_b := ' ';
                            v_payeeacc_b := ' ';
                            v_paytyp_b := ' ';
                            v_accname_a := ' ';
                            v_accidn_a := ' ';
                            v_bankname_a := ' ';
                            v_paybankid_a := ' ';
                            v_branchid_a := ' ';
                            v_payeeacc_a := ' ';
                            v_paytyp_a := ' ';

                            begin
                                for v_dataCur in c_dataCur(v_i_seqno) Loop
                                    v_baappbaseID := v_dataCur.BAAPPBASEID;
                                    v_accname_b := v_dataCur.ACCNAME_B;
                                    v_accidn_b := v_dataCur.ACCIDN_B;
                                    v_bankname_b := v_dataCur.BANKNAME_B;
                                    v_paybankid_b := v_dataCur.PAYBANKID_B;
                                    v_branchid_b := v_dataCur.BRANCHID_B;
                                    v_payeeacc_b := v_dataCur.PAYEEACC_B;
                                    v_paytyp_b := v_dataCur.PAYTYP_B;
                                    v_accname_a := v_dataCur.ACCNAME_A;
                                    v_accidn_a := v_dataCur.ACCIDN_A;
                                    v_paybankid_a := v_dataCur.PAYBANKID_A;
                                    v_branchid_a := v_dataCur.BRANCHID_A;
                                    v_payeeacc_a := v_dataCur.PAYEEACC_A;
                                    v_paytyp_a := v_dataCur.PAYTYP_A;

                                    --給付方式='A'時,金融機構名稱、帳號(總行代碼)、帳號(分行代碼)、帳號資料皆為空
                                    if v_paytyp_a = 'A' then
                                        v_bankname_a := ' ';
                                        v_paybankid_a := ' ';
                                        v_branchid_a := ' ';
                                        v_payeeacc_a := ' ';
                                    else
                                        v_bankname_a := ' ';

                                        if v_paytyp_a = '1' then
                                            --PayTyp=1,取得金融機構名稱(銀行)

                                            --20130621 Modify by Angela:原讀國保金融機構資料檔(NPBANKLIST)判斷帳號資料，改讀現金給付中的行庫檔(BCBPF)
                                            /*
                                            select Count(*) into v_g_rowCount
                                              from NPBANKLIST t
                                             where (t.MAIN_CODE || t.BRANCH_CODE) = (v_paybankid_a||v_branchid_a);

                                            if v_g_rowCount>0 then
                                                v_g_rowCount := 0;
                                                select SHORT_NAME into v_bankname_a
                                                  from NPBANKLIST t
                                                 where (t.MAIN_CODE || t.BRANCH_CODE) = (v_paybankid_a||v_branchid_a);
                                            end if;
                                            */
                                            select Count(*) into v_g_rowCount
                                              from BCBPF t
                                             where (t.BNKALL || t.BNKONO) = (v_paybankid_a||v_branchid_a);

                                            if v_g_rowCount>0 then
                                                v_g_rowCount := 0;
                                                select t.BNKNME into v_bankname_a
                                                  from BCBPF t
                                                 where (t.BNKALL || t.BNKONO) = (v_paybankid_a||v_branchid_a);
                                            end if;

                                        elsif v_paytyp_a = '2' then
                                            --PayTyp=2,取得金融機構名稱(郵局)
                                            select Count(*) into v_g_rowCount
                                              from BAPARAM t
                                             where t.PARAMGROUP = 'PAYPOSTNAME'
                                               and t.PARAMCODE = (v_paybankid_a||v_branchid_a)
                                               and t.PARAMUSEMK = 'Y';

                                            if v_g_rowCount>0 then
                                                v_g_rowCount := 0;
                                                select PARAMNAME into v_bankname_a
                                                  from BAPARAM t
                                                 where t.PARAMGROUP = 'PAYPOSTNAME'
                                                   and t.PARAMCODE = (v_paybankid_a||v_branchid_a)
                                                   and t.PARAMUSEMK = 'Y';
                                            end if;
                                        end if;
                                    end if;

                                    --Modify by 20120410 需先更新改匯檔的資料，才有辦法判斷是否有與給付主檔的帳號資料不同
                                    update BAREGIVEDTL t set t.ISSUTYP = 'B'
                                                            ,t.ISSUYM = ''
                                                            ,t.AFCHKDATE = ''
                                                            ,t.MK = '1'
                                                            ,t.ACCNAME = deCode(v_paytyp_a,'A',t.ACCNAME,v_accname_a)
                                                            ,t.ACCIDN = deCode(v_paytyp_a,'A',t.ACCIDN,v_accidn_a)
                                                            ,t.PAYBANKID = deCode(v_paytyp_a,'A','',v_paybankid_a)
                                                            ,t.BRANCHID = deCode(v_paytyp_a,'A','',v_branchid_a)
                                                            ,t.PAYEEACC = deCode(v_paytyp_a,'A','',v_payeeacc_a)
                                                            ,t.PAYTYP = v_paytyp_a
                                                            ,t.PROCUSER = v_i_procempno
                                                            ,t.PROCDEPTID = v_i_procdeptid
                                                            ,t.PROCIP = v_i_procip
                                                            ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                       where t.APNO = v_i_apno
                                                         and t.TRANSACTIONID = v_i_transactionid
                                                         and t.TRANSACTIONSEQ = v_i_transactionseq
                                                         and t.ORIISSUYM = v_i_oriissuym
                                                         and t.PAYYM = v_i_payym
                                                         and t.MK = '2'
                                                         and (trim(t.AFPAYDATE) is null or nvl(trim(t.AFPAYDATE),' ')=' ');

                                    --取消改匯資料時,若給付主檔的帳號資料已由給付處修改時,則不異動給付主檔的帳號資料,僅異動改匯檔的資料
                                    --判斷改匯資料檔與給付主檔的帳號資料是否相同
                                    select Count(*) into v_baappbase_updflag
                                      from BAAPPBASE t1,BAREGIVEDTL t2
                                     where t1.APNO = t2.APNO
                                       and t1.SEQNO = t2.SEQNO
                                       and t1.APNO = v_i_apno
                                       and t2.TRANSACTIONID = v_i_transactionid
                                       and t2.TRANSACTIONSEQ = v_i_transactionseq
                                       and t2.ORIISSUYM = v_i_oriissuym
                                       and t2.PAYYM = v_i_payym
                                       and t1.PAYTYP = t2.PAYTYP
                                       and t1.BRANCHID = t2.BRANCHID
                                       and t1.PAYBANKID = t2.PAYBANKID
                                       and t1.PAYEEACC = t2.PAYEEACC;

                                    --取消改匯資料時,若給付主檔的帳號資料已由給付處修改時,則不異動給付主檔的帳號資料,僅異動改匯檔的資料
                                    if v_baappbase_updflag <> 0 then
                                        --若於出納系統取消改匯時,除了需將已改匯的資料還原寫回給付主檔外,另外也將改前值及改後值一併寫入BAAPPLog
                                        --比對給付主檔中的"戶名"與退匯資料檔中的原退匯資料的"戶名"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if v_paytyp_a <> 'A' then
                                            if (nvl(trim(v_accname_b),' ') <> nvl(trim(v_accname_a),' ')) then
                                                 v_fieID := v_fieID || 'ACCNAME,';
                                                 v_befimg := v_befimg || v_accname_b || ',';
                                                 v_aftimg := v_aftimg || v_accname_a || ',';
                                                 PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                                   ,'U'
                                                                                   ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                                   ,v_i_procempno
                                                                                   ,'戶名'
                                                                                   ,v_accname_b
                                                                                   ,v_accname_a
                                                                                   );
                                            end if;

                                            --比對給付主檔中的"戶名IDN"與退匯資料檔中的原退匯資料的"戶名IDN"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                            if (nvl(trim(v_accidn_b),' ') <> nvl(trim(v_accidn_a),' ')) then
                                                 v_fieID := v_fieID || 'ACCIDN,';
                                                 v_befimg := v_befimg || v_accidn_b || ',';
                                                 v_aftimg := v_aftimg || v_accidn_a || ',';
                                                 PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                                   ,'U'
                                                                                   ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                                   ,v_i_procempno
                                                                                   ,'戶名IDN'
                                                                                   ,v_accidn_b
                                                                                   ,v_accidn_a
                                                                                   );
                                            end if;
                                        end if;

                                        --比對給付主檔中的"金融機構名稱"與退匯資料檔中的原退匯資料的"金融機構名稱"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if (nvl(trim(v_bankname_b),' ') <> nvl(trim(v_bankname_a),' ')) then
                                             v_fieID := v_fieID || 'BANKNAME,';
                                             v_befimg := v_befimg || v_bankname_b || ',';
                                             v_aftimg := v_aftimg || v_bankname_a || ',';
                                             PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                               ,'U'
                                                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                               ,v_i_procempno
                                                                               ,'金融機構名稱'
                                                                               ,v_bankname_b
                                                                               ,v_bankname_a
                                                                               );
                                        end if;

                                        --比對給付主檔中的"帳號(總行代碼)"與退匯資料檔中的原退匯資料的"帳號(總行代碼)"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if (nvl(trim(v_paybankid_b),' ') <> nvl(trim(v_paybankid_a),' ')) then
                                             v_fieID := v_fieID || 'PAYBANKID,';
                                             v_befimg := v_befimg || v_paybankid_b || ',';
                                             v_aftimg := v_aftimg || v_paybankid_a || ',';
                                             PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                               ,'U'
                                                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                               ,v_i_procempno
                                                                               ,'金融機構總代號'
                                                                               ,v_paybankid_b
                                                                               ,v_paybankid_a
                                                                               );
                                        end if;

                                        --比對給付主檔中的"帳號(分行代碼)"與退匯資料檔中的原退匯資料的"帳號(分行代碼)"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if (nvl(trim(v_branchid_b),' ') <> nvl(trim(v_branchid_a),' ')) then
                                             v_fieID := v_fieID || 'BRANCHID,';
                                             v_befimg := v_befimg || v_branchid_b || ',';
                                             v_aftimg := v_aftimg || v_branchid_a || ',';
                                             PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                               ,'U'
                                                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                               ,v_i_procempno
                                                                               ,'分支代號'
                                                                               ,v_branchid_b
                                                                               ,v_branchid_a
                                                                               );
                                        end if;

                                        --比對給付主檔中的"帳號"與退匯資料檔中的原退匯資料的"帳號"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if (nvl(trim(v_payeeacc_b),' ') <> nvl(trim(v_payeeacc_a),' ')) then
                                             v_fieID := v_fieID || 'PAYEEACC,';
                                             v_befimg := v_befimg || v_payeeacc_b || ',';
                                             v_aftimg := v_aftimg || v_payeeacc_a || ',';
                                             PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                               ,'U'
                                                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                               ,v_i_procempno
                                                                               ,'銀行帳號'
                                                                               ,v_payeeacc_b
                                                                               ,v_payeeacc_a
                                                                               );
                                        end if;

                                        --比對給付主檔中的"給付方式"與退匯資料檔中的原退匯資料的"給付方式"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                                        if (nvl(trim(v_paytyp_b),' ') <> nvl(trim(v_paytyp_a),' ')) then
                                             v_fieID := v_fieID || 'PAYTYP,';
                                             v_befimg := v_befimg || v_paytyp_b || ',';
                                             v_aftimg := v_aftimg || v_paytyp_a || ',';
                                             PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                               ,'U'
                                                                               ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                               ,v_i_procempno
                                                                               ,'給付方式'
                                                                               ,v_paytyp_b
                                                                               ,v_paytyp_a
                                                                               );
                                        end if;

                                        --若還原改匯帳號資料有更改時,除了需回寫給付主檔的帳號相關欄位外,需一併記錄MMAPLog
                                        --Modify by 20120410 修改MMAPLog無法寫入的問題(因MMAPLog.PGMNAME欄位長度過長)
                                        if nvl(trim(v_fieID),' ') <> ' ' then
                                            PKG_BA_RecordLog.sp_BA_recMMAPLog('BAAPPBASE'
                                                                             ,'BAAPPBASEID='||v_baappbaseID
                                                                             ,(fn_BA_transDateValue(to_Char(Sysdate,'YYYYMMDD'),'1')||substr(to_Char(systimestamp,'HH24MISSFF'),1,8))
                                                                             ,'[BA]PKG_BA_PRAPI.sp_BA_CancelAmend'
                                                                             ,'DB(SP)'
                                                                             ,v_i_procdeptid
                                                                             ,v_i_procempno
                                                                             ,v_i_procip
                                                                             ,'U'
                                                                             ,v_fieID
                                                                             ,substr(v_befimg,1,length(v_befimg)-1)
                                                                             ,substr(v_aftimg,1,length(v_aftimg)-1)
                                                                             ,''
                                                                             ,''
                                                                             ,'0'
                                                                             );

                                            --更新給付主檔的受款人帳戶相關欄位
                                            update BAAPPBASE t set t.ACCNAME = deCode(v_paytyp_a,'A',t.ACCNAME,v_accname_a)
                                                                  ,t.ACCIDN = deCode(v_paytyp_a,'A',t.ACCIDN,v_accidn_a)
                                                                  ,t.BANKNAME = deCode(v_paytyp_a,'A','',v_bankname_a)
                                                                  ,t.PAYBANKID = deCode(v_paytyp_a,'A','',v_paybankid_a)
                                                                  ,t.BRANCHID = deCode(v_paytyp_a,'A','',v_branchid_a)
                                                                  ,t.PAYEEACC = deCode(v_paytyp_a,'A','',v_payeeacc_a)
                                                                  ,t.PAYTYP = v_paytyp_a
                                                                  ,t.UPDUSER = v_i_procempno
                                                                  ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                             where t.BAAPPBASEID = v_baappbaseID;
                                        end if;
                                    end if;
                                end Loop;
                            exception
                                when others
                                    then
                                        v_g_errMsg := SQLErrm;
                                        dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                                        v_g_procMsgCode := '1';
                                        v_g_procMsg := '(勞保年金給付系統)取消改匯資料作業失敗:更新給付主檔帳戶資料失敗。失敗原因:'||v_g_errMsg;

                                        --  2017/12/07 寫入結束LOG Add By ChungYu

                                        SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                                         p_job_id => 'PKG_BA_PRAPI.sp_BA_CancelAmend',
                                                         p_step   => '取消改匯將BA帳戶資料更新回原退匯資料',
                                                         p_memo   => '執行結果：('||v_g_procMsgCode||'),'||CHR(10)||
                                                                     '執行結果訊息：('||v_g_procMsg||'),');

                                       --  2017/12/07 寫入結束LOG Add By ChungYu
                            end;

                            /*
                            --Mark by 20120410 搬到上面(需先更新改匯檔的資料，才有辦法判斷是否有與給付主檔的帳號資料不同)
                            if v_g_procMsgCode = '0' then
                                --於出納系統改匯完成時,更新改匯資料檔中的相關資料
                                update BAREGIVEDTL t set t.ISSUTYP = 'B'
                                                        ,t.ISSUYM = ''
                                                        ,t.AFCHKDATE = ''
                                                        ,t.MK = '1'
                                                        ,t.ACCNAME = deCode(v_paytyp_a,'A',t.ACCNAME,v_accname_a)
                                                        ,t.ACCIDN = deCode(v_paytyp_a,'A',t.ACCIDN,v_accidn_a)
                                                        ,t.PAYBANKID = deCode(v_paytyp_a,'A','',v_paybankid_a)
                                                        ,t.BRANCHID = deCode(v_paytyp_a,'A','',v_branchid_a)
                                                        ,t.PAYEEACC = deCode(v_paytyp_a,'A','',v_payeeacc_a)
                                                        ,t.PAYTYP = v_paytyp_a
                                                        ,t.PROCUSER = v_i_procempno
                                                        ,t.PROCDEPTID = v_i_procdeptid
                                                        ,t.PROCIP = v_i_procip
                                                        ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                   where t.APNO = v_i_apno
                                                     and t.TRANSACTIONID = v_i_transactionid
                                                     and t.TRANSACTIONSEQ = v_i_transactionseq
                                                     and t.ORIISSUYM = v_i_oriissuym
                                                     and t.PAYYM = v_i_payym
                                                     and t.MK = '2'
                                                     and (trim(t.AFPAYDATE) is null or nvl(trim(t.AFPAYDATE),' ')=' ');
                            end if;
                            */
                        else
                            v_g_procMsgCode := '1';
                            v_g_procMsg := '(勞保年金給付系統)取消改匯資料作業失敗:給付主檔無該受理案件資料';
                        end if;
                    else
                        v_g_procMsgCode := '1';
                        v_g_procMsg := '(勞保年金給付系統)取消改匯資料作業失敗:';

                        v_g_rowCount := 0;
                        --查詢改匯資料同步作業失敗的原因(原因:已核付或已轉給付沖抵)
                        select Count(*) into v_g_rowCount
                          from BAREGIVEDTL t
                         where t.APNO = v_i_apno
                           and t.TRANSACTIONID = v_i_transactionid
                           and t.TRANSACTIONSEQ = v_i_transactionseq
                           and t.ORIISSUYM = v_i_oriissuym
                           and t.PAYYM = v_i_payym
                           and (trim(t.AFPAYDATE) is not null and nvl(trim(t.AFPAYDATE),' ')<>' ');

                        if v_g_rowCount>0 then
                            v_g_procMsg := '該筆案件的改匯金額已核付';
                        else
                            v_g_procMsg := '該筆案件的改匯資料已取消或不存在';
                        end if;
                    end if;

                else
                    v_g_procMsgCode := '1';
                    v_g_procMsg := '查無該受理案件可改匯的資料:';

                    v_g_rowCount := 0;

                    --查詢改匯資料檔無待改匯的核付資料的原因(原因:交易編號錯誤)
                    select Count(*) into v_g_rowCount
                      from BAREGIVEDTL t
                     where t.TRANSACTIONID = v_i_transactionid
                       and t.TRANSACTIONSEQ = v_i_transactionseq;

                    if v_g_rowCount>0 then
                        v_g_rowCount := 0;

                        --查詢改匯資料檔無待改匯的核付資料的原因(原因:受理編號錯誤)
                        select Count(*) into v_g_rowCount
                          from BAREGIVEDTL t
                         where t.APNO = v_i_apno;

                        if v_g_rowCount>0 then
                            v_g_rowCount := 0;

                            --查詢改匯資料檔無待改匯的核付資料的原因(原因:受理編號與交易編號無關連性)
                            select Count(*) into v_g_rowCount
                              from BAREGIVEDTL t
                             where t.APNO = v_i_apno
                               and t.TRANSACTIONID = v_i_transactionid
                               and t.TRANSACTIONSEQ = v_i_transactionseq;

                            if v_g_rowCount>0 then
                                v_g_procMsg := v_g_procMsg || '核定年月或給付年月錯誤(該筆案件資料待改匯的年月資料與傳入的年月資料不符)';
                            else
                                v_g_procMsg := v_g_procMsg || '受理編號或交易編號錯誤(該受理編號與交易編號無關連性)';
                            end if;
                        else
                            v_g_procMsg := v_g_procMsg || '受理編號錯誤(無此受理編號)';
                        end if;
                    else
                        v_g_procMsg := v_g_procMsg || '交易編號錯誤(無此交易編號)';
                    end if;
                end if;
            end;

            v_o_procMsgCode := v_g_procMsgCode;
            v_o_procMsg := v_g_procMsg;

            --  2017/12/07 寫入結束LOG Add By ChungYu

            SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                             p_job_id => 'PKG_BA_PRAPI.sp_BA_CancelAmend',
                             p_step   => '取消改匯將BA帳戶資料更新回原退匯資料',
                             p_memo   => '執行結果：('||v_g_procMsgCode||'),'||CHR(10)||
                                         '執行結果訊息：('||v_g_procMsg||'),');

            --  2017/12/07 寫入結束LOG Add By ChungYu

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    v_o_procMsgCode := '1';
                    v_o_procMsg := '(勞保年金給付系統)取消改匯資料作業失敗!!失敗原因:'||v_g_errMsg;

                    --  2017/12/07 寫入結束LOG Add By ChungYu

                    SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                     p_job_id => 'PKG_BA_PRAPI.sp_BA_CancelAmend',
                                     p_step   => '取消改匯將BA帳戶資料更新回原退匯資料',
                                     p_memo   => '執行結果：('||v_g_procMsgCode||'),'||CHR(10)||
                                     '執行結果訊息：('||v_g_procMsg||'),');

                    --  2017/12/07 寫入結束LOG Add By ChungYu
                    rollback;
        end;
    --procedure sp_BA_CancelAmendment End


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_PRAPI.sp_BA_ManRemitAplpay
        PURPOSE:         提供出納系統回寫人工電匯的入帳資訊給給付系統(for PayType=7)。

        PARAMETER(IN):  *v_i_apno                (varChar2)        --受理編號
                        *v_i_seqno               (varChar2)        --受款人序
                        *v_i_oriissuym           (varChar2)        --原始核定年月
                        *v_i_payym               (varChar2)        --給付年月
                        *v_i_remitdate           (varChar2)        --後續處理日期
                        *v_i_procempno           (varChar2)        --作業人員
                        *v_i_procdeptid          (varChar2)        --作業人員單位ID
                        *v_i_procip              (varChar2)        --作業人員IP

        PARAMETER(OUT): *v_o_procMsgCode         (varChar2)        --回傳處理結果訊息代碼
                        *v_o_procMsg             (varChar2)        --回傳處理結果訊息

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/08/22  Angela Wu    Created this procedure.
        1.1   2017/12/07  ChungYu      加入執行期間的Log紀錄.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。
        2.於上方的PARAMETER(OUT)中,打"*"者為必傳出之參數值。

    ********************************************************************************/
    procedure sp_BA_ManRemitAplpay (
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_remitdate        in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    ) is
        v_badaprID           Number;
        v_remitmk_b          varChar2(1);
        v_remitdate_b        varChar2(8);
        v_jobno              mmjoblog.job_no%TYPE;
        v_starttime          TIMESTAMP;

        begin
            v_g_ProgName := 'PKG_BA_PRAPI.sp_BA_ManRemitAplpay';
            v_g_rowCount := 0;
            v_g_procMsgCode := '0';
            v_g_procMsg := '';
            v_g_errMsg := '';
            v_o_procMsgCode := ' ';
            v_o_procMsg := ' ';
            v_badaprID := 0;
            v_remitmk_b := '';
            v_remitdate_b := '';

            --  2017/12/07 寫入開始LOG Add By ChungYu
            v_jobno  := REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','');
            v_starttime := SYSTIMESTAMP;

            SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                             p_job_id => 'PKG_BA_PRAPI.sp_BA_ManRemit',
                             p_step   => '出納回寫人工電匯的入帳資訊給BA',
                             p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||'),'||CHR(10)||
                                         '受理編號：('||v_i_apno||'),'||CHR(10)||
                                         '受款人序號：('||v_i_seqno||'),'||CHR(10)||
                                         '核定年月：('||v_i_oriissuym||'),'||CHR(10)||
                                         '給付年月：('||v_i_payym||'),'||CHR(10)||
                                         '後續處理日期：('||v_i_remitdate||'),'||CHR(10)||
                                         '作業人員：('||v_i_procempno||'),'||CHR(10)||
                                         '作業人員單位ID：('||v_i_procdeptid||'),'||CHR(10)||
                                         '作業人員IP：('||v_i_procip||'),');

            --  2017/12/07 寫入開始LOG Add By ChungYu

            --查詢入帳資訊是否可以回寫給付系統(檢核符合條件：是否符合核付資料及退改匯資料的狀態)
            select Count(*) into v_g_rowCount
              from BADAPR t1,BAREGIVEDTL t2
             where t1.APNO = t2.APNO
               and t1.SEQNO = t2.SEQNO
               and t1.ISSUYM = t2.ORIISSUYM
               and t1.PAYYM = t2.PAYYM
               and t1.APNO = v_i_apno
               and t1.SEQNO = v_i_seqno
               and t1.ISSUYM = v_i_oriissuym
               and t1.PAYYM = v_i_payym
               and t1.REMITMK in ('2','3')
               and t2.MK in ('1','2')
               and t1.MTESTMK = 'F'
               and t1.MANCHKMK = 'Y'
               and t1.APLPAYMK = '3'
               and (t1.CHKDATE is not null and nvl(trim(t1.CHKDATE),' ')<>' ')
               and (t1.REMITDATE is not null and nvl(trim(t1.REMITDATE),' ')<>' ')
               and (t1.APLPAYDATE is not null and nvl(trim(t1.APLPAYDATE),' ')<>' ')
               and (t2.AFCHKDATE is not null and nvl(trim(t2.AFCHKDATE),' ')<>' ')
               and (t2.WORKMK is null or nvl(trim(t2.WORKMK),' ') = ' ')
               and (t2.AFPAYDATE is null or nvl(trim(t2.AFPAYDATE),' ')=' ');

            if v_g_rowCount>0 then
                v_g_rowCount := 0;

                --查詢入帳資訊是否可以回寫給付系統(檢核符合條件：給付方式是否符合可以人工電匯的給付方式)
                select Count(*) into v_g_rowCount
                  from BAREGIVEDTL t
                 where t.APNO = v_i_apno
                   and t.SEQNO = v_i_seqno
                   and t.ORIISSUYM = v_i_oriissuym
                   and t.PAYYM = v_i_payym
                   and t.MK in ('1','2')
                   and t.PAYTYP = '7';

                if v_g_rowCount>0 then
                    v_g_rowCount := 0;
                    --查詢入帳資訊是否可以回寫給付系統(檢核符合條件：改匯資料尚未進行改匯初核作業)
                    select Count(*) into v_g_rowCount
                      from BAREGIVEDTL t
                     where t.APNO = v_i_apno
                       and t.SEQNO = v_i_seqno
                       and t.ORIISSUYM = v_i_oriissuym
                       and t.PAYYM = v_i_payym
                       and t.MK in ('1','2')
                       and t.PAYTYP = '7'
                       and (trim(t.AFCHKDATE) is not null and nvl(trim(t.AFCHKDATE),' ')<>' ');

                    if v_g_rowCount>0 then
                        v_g_rowCount := 0;
                        --更新改匯記錄檔中,改匯核付資料的核付相關欄位
                        Update BAREGIVEDTL t set t.AFPAYDATE = v_i_remitdate
                                                ,t.AFMK = '2'
                                                ,t.BLIACCOUNT = '0053'
                                                ,t.ACTTITLEAPCODE = 'KLA'
                                                ,t.RECHKDATE = v_i_remitdate
                                                ,t.RECHKMAN = v_i_procempno
                                                ,t.EXEMAN = v_i_procempno
                                                ,t.EXEDATE = v_i_remitdate
                                                ,t.CPRNDATE = to_Char(Sysdate,'YYYYMMDD')
                                                ,t.RPTPAGE = '560001'
                                                ,t.RPTROWS = '1'
                                                ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.ORIISSUYM = v_i_oriissuym
                           --and t.ISSUYM = v_i_oriissuym
                           and t.PAYYM = v_i_payym
                           and t.MK = '2'
                           and t.PAYTYP = '7'
                           and nvl(trim(t.WORKMK),' ') <> 'Y'
                           and (trim(t.AFCHKDATE) is not null and nvl(trim(t.AFCHKDATE),' ')<>' ')
                           and (trim(t.AFPAYDATE) is null or nvl(trim(t.AFPAYDATE),' ')=' ');

                        --更新退匯記錄檔中的核付相關欄位
                        Update BAPFLBAC t set t.AFMK = '2'
                                             ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.ORIISSUYM = v_i_oriissuym
                           and t.PAYYM = v_i_payym
                           and t.AFMK = '1';

                        --因介接出納系統，故由出納系統異動的核定檔資訊需記錄改前值及改後值
                        select t.BADAPRID
                              ,t.REMITMK
                              ,t.REMITDATE
                          into v_badaprID
                              ,v_remitmk_b
                              ,v_remitdate_b
                          from BADAPR t
                         where t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.ISSUYM = v_i_oriissuym
                           and t.PAYYM = v_i_payym
                           and t.REMITMK in ('2','3')
                           and t.MTESTMK = 'F'
                           and t.MANCHKMK = 'Y'
                           and t.APLPAYMK = '3'
                           and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                           and (t.REMITDATE is not null and nvl(trim(t.REMITDATE),' ')<>' ')
                           and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');

                        --更新給付核定檔中,後續處理狀況的相關欄位
                        Update BADAPR t set t.REMITDATE = v_i_remitdate
                                           ,t.REMITMK = '1'
                                           ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.ISSUYM = v_i_oriissuym
                           and t.PAYYM = v_i_payym
                           and t.MTESTMK = 'F'
                           and t.MANCHKMK = 'Y'
                           and t.REMITMK in ('2','3')
                           and t.APLPAYMK = '3'
                           and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                           and (t.REMITDATE is not null and nvl(trim(t.REMITDATE),' ')<>' ')
                           and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');

                        --因介接出納系統，故由出納系統異動的核定檔資訊需記錄MMAPLog
                        PKG_BA_RecordLog.sp_BA_recMMAPLog('BADAPR'
                                                         ,'BADAPRID='||v_badaprID
                                                         ,(fn_BA_transDateValue(to_Char(Sysdate,'YYYYMMDD'),'1')||substr(to_Char(systimestamp,'HH24MISSFF'),1,8))
                                                         ,'[BA]PKG_BA_PRAPI.sp_BA_ManRemitAplpay'
                                                         ,'DB(SP)'
                                                         ,v_i_procdeptid
                                                         ,v_i_procempno
                                                         ,v_i_procip
                                                         ,'U'
                                                         ,'REMITMK,REMITDATE'
                                                         ,v_remitmk_b||','||v_remitdate_b
                                                         ,'1,'||v_i_remitdate
                                                         ,''
                                                         ,''
                                                         ,'0'
                                                         );
                    else
                        v_g_procMsgCode := '1';
                        v_g_procMsg := v_g_procMsg || '該筆資料尚未進行改匯初核作業';
                    end if;
                else
                    v_g_procMsgCode := '1';
                    v_g_procMsg := v_g_procMsg || '該筆給付方式不符合可以人工電匯的給付方式(可以人工改匯的給付方式為「7」)';
                end if;
            else
                v_g_rowCount := 0;
                v_g_procMsgCode := '1';

                --查詢無法回寫給付系統的原因(原因:受理編號錯誤(無此受理編號))
                select Count(*) into v_g_rowCount
                  from BADAPR t
                 where t.APNO = v_i_apno;

                if v_g_rowCount>0 then
                    v_g_rowCount := 0;

                    --查詢無法回寫給付系統的原因(原因:受款人序錯誤)
                    select Count(*) into v_g_rowCount
                      from BADAPR t
                     where t.APNO = v_i_apno
                       and t.SEQNO = v_i_seqno;

                    if v_g_rowCount>0 then
                        v_g_rowCount := 0;

                        --查詢無法回寫給付系統的原因(原因:該筆受理案件查無核付資料(核定年月錯誤或給付年月錯誤))
                        select Count(*) into v_g_rowCount
                          from BADAPR t
                         where t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.ISSUYM = v_i_oriissuym
                           and t.PAYYM = v_i_payym
                           and t.MTESTMK = 'F'
                           and t.MANCHKMK = 'Y'
                           and t.APLPAYMK = '3'
                           and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                           and (t.REMITDATE is not null and nvl(trim(t.REMITDATE),' ')<>' ')
                           and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');

                        if v_g_rowCount>0 then
                            v_g_rowCount := 0;

                            --查詢無法回寫給付系統的原因(原因:該筆受理案件的後續狀況非退匯中或改匯中/該筆受理案件無退匯中或改匯中的記錄)
                            select Count(*) into v_g_rowCount
                              from BADAPR t
                             where t.APNO = v_i_apno
                               and t.SEQNO = v_i_seqno
                               and t.ISSUYM = v_i_oriissuym
                               and t.PAYYM = v_i_payym
                               and t.REMITMK in ('2','3')
                               and t.MTESTMK = 'F'
                               and t.MANCHKMK = 'Y'
                               and t.APLPAYMK = '3'
                               and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                               and (t.REMITDATE is not null and nvl(trim(t.REMITDATE),' ')<>' ')
                               and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');

                            if v_g_rowCount>0 then
                                v_g_procMsg := v_g_procMsg || '該筆受理案件無退匯中或改匯中的記錄';
                            else
                                v_g_procMsg := v_g_procMsg || '該筆受理案件的後續狀況非退匯中或改匯中';
                            end if;
                        else
                            v_g_procMsg := v_g_procMsg || '該筆受理案件查無核付資料(核定年月錯誤或給付年月錯誤)';
                        end if;
                    else
                        v_g_procMsg := v_g_procMsg || '受款人序錯誤';
                    end if;
                else
                    v_g_procMsg := v_g_procMsg || '受理編號錯誤(無此受理編號)';
                end if;
            end if;

            v_o_procMsgCode := v_g_procMsgCode;
            v_o_procMsg := v_g_procMsg;

            --  2017/12/07 寫入結束LOG Add By ChungYu
             SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                             p_job_id => 'PKG_BA_PRAPI.sp_BA_ManRemit',
                             p_step   => '出納回寫人工電匯的入帳資訊給BA',
                             p_memo   => '執行結果：('||v_g_procMsgCode||'),'||CHR(10)||
                                         '執行結果訊息：('||v_g_procMsg||'),');

            --  2017/12/07 寫入結束LOG Add By ChungYu

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    v_o_procMsgCode := '1';
                    v_o_procMsg := '回寫人工電匯的入帳資訊失敗!!失敗原因:'||v_g_errMsg;

                    --  2017/12/07 寫入結束LOG Add By ChungYu
                    SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                     p_job_id => 'PKG_BA_PRAPI.sp_BA_ManRemit',
                                     p_step   => '出納回寫人工電匯的入帳資訊給BA',
                                     p_memo   => '執行結果：('||v_g_procMsgCode||'),'||CHR(10)||
                                                 '執行結果訊息：('||v_g_procMsg||'),');

                    --  2017/12/07 寫入結束LOG Add By ChungYu

                    rollback;
        end;
    --procedure sp_BA_ManRemit End
    /*
    \********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_PRAPI.sp_BA_ManRefundment
        PURPOSE:         提供出納系統新增人工退匯的資料。

        PARAMETER(IN):  *v_i_apno                (varChar2)        --受理編號
                        *v_i_seqno               (varChar2)        --受款人序
                        *v_i_oriissuym           (varChar2)        --原始核定年月
                        *v_i_payym               (varChar2)        --給付年月
                        *v_i_procempno           (varChar2)        --作業人員
                        *v_i_procdeptid          (varChar2)        --作業人員單位ID
                        *v_i_procip              (varChar2)        --作業人員IP

        PARAMETER(OUT): *v_o_procMsgCode         (varChar2)        --回傳處理結果訊息代碼
                        *v_o_procMsg             (varChar2)        --回傳處理結果訊息

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/08/24  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。
        2.於上方的PARAMETER(OUT)中,打"*"者為必傳出之參數值。

    ********************************************************************************\
    procedure sp_BA_ManRefundment (
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    ) is
        v_badaprID           Number;
        v_remitmk_b          varChar2(1);
        v_remitdate_b        varChar2(8);

        begin
            v_g_ProgName := 'PKG_BA_PRAPI.sp_BA_ManRefundment';
            v_g_rowCount := 0;
            v_g_procMsgCode := '0';
            v_g_procMsg := '';
            v_g_errMsg := '';
            v_o_procMsgCode := ' ';
            v_o_procMsg := ' ';
            v_badaprID := 0;
            v_remitmk_b := '';
            v_remitdate_b := '';

            --查詢是否符合可以新增人工退匯的資料(檢核符合條件：是否有已核付的資料)
            select Count(*) into v_g_rowCount
              from BADAPR t
             where t.APNO = v_i_apno
               and t.SEQNO = v_i_seqno
               and t.ISSUYM = v_i_oriissuym
               and t.PAYYM = v_i_payym
               and t.MTESTMK = 'F'
               and t.MANCHKMK = 'Y'
               and t.REMITMK = '1'
               and t.APLPAYMK = '3'
               and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');

            if v_g_rowCount>0 then
                v_g_rowCount := 0;

                --查詢是否符合可以新增人工退匯的資料(檢核符合條件：給付方式是否符合可以人工改匯的給付方式)
                select Count(*) into v_g_rowCount
                  from BADAPR t
                 where t.APNO = v_i_apno
                   and t.SEQNO = v_i_seqno
                   and t.ISSUYM = v_i_oriissuym
                   and t.PAYYM = v_i_payym
                   and t.MTESTMK = 'F'
                   and t.MANCHKMK = 'Y'
                   and t.REMITMK = '1'
                   and t.APLPAYMK = '3'
                   and t.PAYTYP = 'A'
                   and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');

                if v_g_rowCount>0 then
                    v_g_rowCount := 0;

                    --查詢是否符合可以新增人工退匯的資料(檢核符合條件：查詢是否有退匯中或改匯中的資料)
                    select Count(*) into v_g_rowCount
                      from BAREGIVEDTL t
                     where t.APNO = v_i_apno
                       and t.SEQNO = v_i_seqno
                       and t.ORIISSUYM = v_i_oriissuym
                       and t.PAYYM = v_i_payym
                       and t.MK in ('1','2')
                       and (t.AFPAYDATE is null or nvl(trim(t.AFPAYDATE),' ')=' ');

                    if v_g_rowCount>0 then
                        v_g_rowCount := 0;

                        --因介接出納系統，故由出納系統異動的核定檔資訊需記錄改前值及改後值
                        select t.BADAPRID
                              ,t.REMITMK
                              ,t.REMITDATE
                          into v_badaprID
                              ,v_remitmk_b
                              ,v_remitdate_b
                          from BADAPR t
                         where t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.ISSUYM = v_i_oriissuym
                           and t.PAYYM = v_i_payym
                           and t.REMITMK = '1'
                           and t.MTESTMK = 'F'
                           and t.MANCHKMK = 'Y'
                           and t.APLPAYMK = '3'
                           and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                           and (t.REMITDATE is not null and nvl(trim(t.REMITDATE),' ')<>' ')
                           and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');

                        --更新給付核定檔中,後續處理狀況的相關欄位
                        Update BADAPR t set t.REMITDATE = to_Char(Sysdate,'YYYYMMDD')
                                           ,t.REMITMK = '2'
                                           ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t.APNO = v_i_apno
                           and t.SEQNO = v_i_seqno
                           and t.ISSUYM = v_i_oriissuym
                           and t.PAYYM = v_i_payym
                           and t.MTESTMK = 'F'
                           and t.MANCHKMK = 'Y'
                           and t.REMITMK = '1'
                           and t.APLPAYMK = '3'
                           and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                           and (t.REMITDATE is not null and nvl(trim(t.REMITDATE),' ')<>' ')
                           and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');

                        --因介接出納系統，故由出納系統異動的核定檔資訊需記錄MMAPLog
                        PKG_BA_RecordLog.sp_BA_recMMAPLog('BADAPR'
                                                         ,'BADAPRID='||v_badaprID
                                                         ,(fn_BA_transDateValue(to_Char(Sysdate,'YYYYMMDD'),'1')||substr(to_Char(systimestamp,'HH24MISSFF'),1,8))
                                                         ,'[BA]PKG_BA_PRAPI.sp_BA_ManRefundment'
                                                         ,'DB(SP)'
                                                         ,v_i_procdeptid
                                                         ,v_i_procempno
                                                         ,v_i_procip
                                                         ,'U'
                                                         ,'REMITMK,REMITDATE'
                                                         ,v_remitmk_b||','||v_remitdate_b
                                                         ,'2,'||to_Char(Sysdate,'YYYYMMDD')
                                                         ,''
                                                         ,''
                                                         ,'0'
                                                         );
                        --新增退匯資料檔
                        insert into BAPFLBAC (APNO
                                             ,SEQNO
                                             ,PAYDATE
                                             ,BRISSUYM
                                             ,ORIISSUYM
                                             ,ISSUYM
                                             ,PAYYM
                                             ,INSDK
                                             ,ISSUKIND
                                             ,BRCHKDATE
                                             ,AFMK
                                             ,BRMK
                                             ,BRNOTE
                                             ,BLIACCOUNT
                                             ,BENTYP
                                             ,BENIDS
                                             ,BENIDN
                                             ,BENNAME
                                             ,ISSUTYP
                                             ,PAYTYP
                                             ,PAYBANKID
                                             ,BRANCHID
                                             ,PAYEEACC
                                             ,ACCIDN
                                             ,ACCNAME
                                             ,ACCSEQNO
                                             ,COMMZIP
                                             ,COMMADDR
                                             ,BRADDR
                                             ,COMMTEL
                                             ,REMITAMT
                                             ,ACTTITLEAPCODE
                                             ,CPRNDATE
                                             ,RECHKMAN
                                             ,RECHKDATE
                                             ,CONFIRMMAN
                                             ,CONFIRMDATE
                                             ,CONFIRMRECDATE
                                             ,PROCUSER
                                             ,PROCDEPTID
                                             ,PROCIP
                                             ,UPDTIME)
                                       select T1.APNO
                                             ,T1.SEQNO
                                             ,T1.PAYDATE
                                             ,to_Char(Sysdate,'YYYYMM')
                                             ,T1.ORIISSUYM
                                             ,to_Char(Sysdate,'YYYYMM')
                                             ,T1.PAYYM
                                             ,T1.INSDK
                                             ,T1.ISSUKIND
                                             ,to_Char(Sysdate,'YYYYMMDD')
                                             ,'3'
                                             ,'3'
                                             ,T1.BRNOTE
                                             ,'0053'
                                             ,T1.BENTYP
                                             ,T1.BENIDS
                                             ,T1.BENIDN
                                             ,T1.BENNAME
                                             ,T1.ISSUTYP
                                             ,T1.PAYTYP
                                             ,T1.PAYBANKID
                                             ,deCode(T1.PAYBANKID,'700',T1.BRANCHID,deCode(T1.PAYTYP,'1','0000',T1.BRANCHID))
                                             ,T1.PAYEEACC
                                             ,T1.ACCIDN
                                             ,T1.ACCNAME
                                             ,T1.ACCSEQNO
                                             ,T1.COMMZIP
                                             ,T1.COMMADDR
                                             ,T1.BRADDR
                                             ,T1.COMMTEL
                                             ,'$(QryData_2).REMITAMT(退匯金額)'
                                             ,T1.ACTTITLEAPCODE
                                             ,to_Char(Sysdate,'YYYYMMDD')
                                             ,v_i_procempno
                                             ,to_Char(Sysdate,'YYYYMMDD')
                                             ,v_i_procempno
                                             ,to_Char(Sysdate,'YYYYMMDD')
                                             ,to_Char(Sysdate,'YYYYMMDD')
                                             ,v_i_procempno
                                             ,v_i_procdeptid
                                             ,v_i_procip
                                             ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                        from BAPFLBAC T1
                                       where T1.APNO = v_i_apno
                                         and T1.SEQNO = v_i_seqno
                                         and T1.ORIISSUYM = v_i_oriissuym
                                         and T1.PAYYM = v_i_payym
                                         and T1.BRMK = '2'
                                         and T1.AFMK = '4'
                                         and (T1.REPAYSEQNO = T1.SEQNO
                                           or nvl(trim(T1.REPAYSEQNO),' ') = ' ')
                                         and ROWNUM = 1;

                    else
                        v_g_procMsg := v_g_procMsg || '該筆受理案件已有退匯中或改匯中的資料，無法再新增退匯資料';
                    end if;
                else
                    v_g_procMsg := v_g_procMsg || '該筆受理案件的給付方式不符合可以人工新增退匯資料的給付方式(可以人工新增退匯資料的給付方式為「A」)';
                end if;
            else
                v_g_rowCount := 0;

                --查詢無法新增人工退匯的原因(原因:受理編號錯誤(無此受理編號))
                select Count(*) into v_g_rowCount
                  from BADAPR t
                 where t.APNO = v_i_apno;

                if v_g_rowCount>0 then
                    v_g_rowCount := 0;

                    --查詢無法新增人工退匯的原因(原因:受款人序錯誤)
                    select Count(*) into v_g_rowCount
                      from BADAPR t
                     where t.APNO = v_i_apno
                       and t.SEQNO = v_i_seqno;

                    if v_g_rowCount>0 then
                        v_g_procMsg := v_g_procMsg || '該筆受理案件查無核付資料(核定年月錯誤或給付年月錯誤)';
                    else
                        v_g_procMsg := v_g_procMsg || '受款人序錯誤';
                    end if;
                else
                    v_g_procMsg := v_g_procMsg || '受理編號錯誤(無此受理編號)';
                end if;
            end if;

            v_o_procMsgCode := v_g_procMsgCode;
            v_o_procMsg := v_g_procMsg;

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    v_o_procMsgCode := '1';
                    v_o_procMsg := '(勞保年金給付系統)人工新增退匯資料失敗!!失敗原因:'||v_g_errMsg;
                    rollback;
        end;
    --procedure sp_BA_ManRefundment End

    \********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_PRAPI.sp_BA_CancelManRefundment
        PURPOSE:         提供出納系統取消新增人工退匯的資料。

        PARAMETER(IN):  *v_i_apno                (varChar2)        --受理編號
                        *v_i_seqno               (varChar2)        --受款人序
                        *v_i_oriissuym           (varChar2)        --原始核定年月
                        *v_i_payym               (varChar2)        --給付年月
                        *v_i_procempno           (varChar2)        --作業人員
                        *v_i_procdeptid          (varChar2)        --作業人員單位ID
                        *v_i_procip              (varChar2)        --作業人員IP

        PARAMETER(OUT): *v_o_procMsgCode         (varChar2)        --回傳處理結果訊息代碼
                        *v_o_procMsg             (varChar2)        --回傳處理結果訊息

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/08/24  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。
        2.於上方的PARAMETER(OUT)中,打"*"者為必傳出之參數值。

    ********************************************************************************\
    procedure sp_BA_CancelManRefundment (
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2,
        v_o_procMsgCode      out     varChar2,
        v_o_procMsg          out     varChar2
    ) is
        begin
            v_g_ProgName := 'PKG_BA_PRAPI.sp_BA_CancelManRefundment';
            v_g_rowCount := 0;
            v_g_procMsgCode := '0';
            v_g_procMsg := '';
            v_g_errMsg := '';
            v_o_procMsgCode := ' ';
            v_o_procMsg := ' ';


            v_o_procMsgCode := v_g_procMsgCode;
            v_o_procMsg := v_g_procMsg;

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    v_o_procMsgCode := '1';
                    v_o_procMsg := '(勞保年金給付系統)取消新增人工退匯資料失敗!!失敗原因:'||v_g_errMsg;
                    rollback;
        end;
    --procedure sp_BA_CancelManRefundment End
    */
    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_PRAPI.fn_BA_ChkInData
        PURPOSE:         提供出納系統執行改匯作業時,傳入API時的參數值檢核。

        PARAMETER(IN):  *v_i_transactionid       (varChar2)        --交易編號
                        *v_i_transactionseq      (varChar2)        --交易編號的序號
                        *v_i_apno                (varChar2)        --受理編號
                        *v_i_seqno               (varChar2)        --受款人序
                        *v_i_oriissuym           (varChar2)        --原始核定年月
                        *v_i_payym               (varChar2)        --給付年月
                        *v_i_afchkdate           (varChar2)        --改匯初核日期
                        *v_i_mk                  (varChar2)        --退改匯狀態
                        *v_i_accidn              (varChar2)        --戶名IDN
                        *v_i_accname             (varChar2)        --戶名
                        *v_i_paytyp              (varChar2)        --核發方式/給付方式
                        *v_i_payeeacc            (varChar2)        --轉帳帳號(總行+分行+帳號)
                        *v_i_procempno           (varChar2)        --作業人員
                        *v_i_procdeptid          (varChar2)        --作業人員單位ID
                        *v_i_procip              (varChar2)        --作業人員IP

        PARAMETER(OUT):

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
    function fn_BA_ChkInData (
        v_i_transactionid    in      varChar2,
        v_i_transactionseq   in      varChar2,
        v_i_apno             in      varChar2,
        v_i_seqno            in      varChar2,
        v_i_oriissuym        in      varChar2,
        v_i_payym            in      varChar2,
        v_i_afchkdate        in      varChar2,
        v_i_mk               in      varChar2,
        v_i_accidn           in      varChar2,
        v_i_accname          in      varChar2,
        v_i_paytyp           in      varChar2,
        v_i_payeeacc         in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_procdeptid       in      varChar2,
        v_i_procip           in      varChar2
    ) return varChar2 is
        v_rtnMsg             varChar2(1000);

        begin
            v_g_ProgName := 'PKG_BA_PRAPI.fn_BA_ChkInData';
            v_rtnMsg := '';
            v_g_rowCount := 0;

            if nvl(trim(v_i_transactionid),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '交易編號不得為空值、';
            end if;

            if nvl(trim(v_i_transactionseq),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '交易編號的序號不得為空值、';
            end if;

            if nvl(trim(v_i_apno),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '受理編號不得為空值、';
            end if;

            if nvl(trim(v_i_seqno),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '受款人序號不得為空值、';
            end if;

            if nvl(trim(v_i_oriissuym),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '核定年月不得為空值、';
            end if;

            if nvl(trim(v_i_payym),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '給付年月不得為空值、';
            end if;

            if nvl(trim(v_i_afchkdate),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '改匯初核日期不得為空值、';
            end if;

            if nvl(trim(v_i_mk),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '退改匯狀態不得為空值、';
            else
                if nvl(trim(v_i_mk),' ')<>'8' then
                    v_rtnMsg := v_rtnMsg || '退改匯狀態輸入資料錯誤(退改匯狀態需為「8」)、';
                end if;
            end if;

            if nvl(trim(v_i_procempno),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '作業人員不得為空值、';
            end if;

            if nvl(trim(v_i_procdeptid),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '作業人員單位ID不得為空值、';
            end if;

            if nvl(trim(v_i_procip),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '作業人員IP不得為空值、';
            end if;

            if nvl(trim(v_i_paytyp),' ')=' ' then
                v_rtnMsg := v_rtnMsg || '給付方式不得為空值、';
            else
                if (nvl(trim(v_i_paytyp),' ')='A') then
                    /*
                    if nvl(trim(v_i_accidn),' ')<>' ' then
                        v_rtnMsg := v_rtnMsg || '戶名IDN(受款人身分證號)需為空值、';
                    end if;

                    if nvl(trim(v_i_accname),' ')<>' ' then
                        v_rtnMsg := v_rtnMsg || '戶名(受款人姓名)需為空值、';
                    end if;
                    */

                    if nvl(trim(v_i_payeeacc),' ')<>' ' then
                        v_rtnMsg := v_rtnMsg || '轉帳帳號需為空值、';
                    end if;

                elsif (nvl(trim(v_i_paytyp),' ')='1' or nvl(trim(v_i_paytyp),' ')='2' or nvl(trim(v_i_paytyp),' ')='7') then
                --elsif (nvl(trim(v_i_paytyp),' ')='1' or nvl(trim(v_i_paytyp),' ')='2') then
                    if nvl(trim(v_i_accidn),' ')=' ' then
                        v_rtnMsg := v_rtnMsg || '戶名IDN(受款人身分證號)不得為空值、';
                    end if;

                    if nvl(trim(v_i_accname),' ')=' ' then
                        v_rtnMsg := v_rtnMsg || '戶名(受款人姓名)不得為空值、';
                    end if;

                    if nvl(trim(v_i_payeeacc),' ')=' ' then
                        if nvl(trim(v_i_payeeacc),' ')=' ' then
                            v_rtnMsg := v_rtnMsg || '轉帳帳號不得為空值、';
                        end if;
                    else
                        if nvl(trim(v_i_paytyp),' ')='2' then
                            if ((substr(nvl(trim(v_i_payeeacc),' '),1,7)<>'7000010') and (substr(nvl(trim(v_i_payeeacc),' '),1,7)<>'7000021'))  then
                                v_rtnMsg := v_rtnMsg || '轉帳帳號資料輸入錯誤(轉帳帳號前7碼需為「7000010」或「7000021」)、';
                            end if;
                        elsif nvl(trim(v_i_paytyp),' ')='1' then
                            if ((substr(nvl(trim(v_i_payeeacc),' '),1,7)='7000010') or (substr(nvl(trim(v_i_payeeacc),' '),1,7)='7000021'))  then
                                v_rtnMsg := v_rtnMsg || '轉帳帳號資料輸入錯誤(轉帳帳號前7碼不得為「7000010」或「7000021」)、';
                            else
                                v_g_rowCount := 0;

                                --20130621 Modify by Angela:原讀國保金融機構資料檔(NPBANKLIST)判斷帳號資料，改讀現金給付中的行庫檔(BCBPF)
                                /*
                                select Count(*)
                                  into v_g_rowCount
                                  from NPBANKLIST t
                                 where (t.MAIN_CODE || t.BRANCH_CODE) = (substr(nvl(trim(v_i_payeeacc),' '),1,7));
                                */
                                select Count(*)
                                  into v_g_rowCount
                                  from BCBPF t
                                 where (t.BNKALL || t.BNKONO) = (substr(nvl(trim(v_i_payeeacc),' '),1,7));

                                if v_g_rowCount=0 then
                                    v_rtnMsg := v_rtnMsg || '轉帳帳號資料輸入錯誤(轉帳帳號的銀行資料不存在)、';
                                end if;
                            end if;
                        elsif nvl(trim(v_i_paytyp),' ')='7' then
                            v_g_rowCount := 0;

                            --20130621 Modify by Angela:原讀國保金融機構資料檔(NPBANKLIST)判斷帳號資料，改讀現金給付中的行庫檔(BCBPF)
                            /*
                            select Count(*)
                              into v_g_rowCount
                              from NPBANKLIST t
                             where (t.MAIN_CODE || t.BRANCH_CODE) = (substr(nvl(trim(v_i_payeeacc),' '),1,7));
                            */
                            select Count(*)
                              into v_g_rowCount
                              from BCBPF t
                             where (t.BNKALL || t.BNKONO) = (substr(nvl(trim(v_i_payeeacc),' '),1,7));

                            if v_g_rowCount=0 then
                                v_rtnMsg := v_rtnMsg || '轉帳帳號資料輸入錯誤(轉帳帳號的銀行資料不存在)、';
                            end if;
                        end if;
                    end if;
                else
                    v_rtnMsg := v_rtnMsg || '給付方式資料輸入錯誤(給付方式需為「1」或「2」或「7」或「A」)、';
                    --v_rtnMsg := v_rtnMsg || '給付方式資料輸入錯誤(給付方式需為「1」或「2」或「A」)、';
                end if;
            end if;

            if nvl(trim(v_rtnMsg),' ')<>' ' then
                v_rtnMsg := substr(v_rtnMsg,1,length(v_rtnMsg)-1);
            end if;

            return v_rtnMsg;
        end;
    --function fn_BA_ChkInData
End;
/