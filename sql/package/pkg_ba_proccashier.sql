CREATE OR REPLACE Package BA.PKG_BA_ProcCashier
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_ProcCashier
    PURPOSE:         勞保年金給付系統與出納系統資料介接API(for 給付系統內部資料處理)

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
    v_g_ProgName             varChar2(200);
    v_g_errMsg               varChar2(4000);
    v_g_rs                   varChar2(200);
    v_g_flag                 Number;
    v_g_rowCount             Number;
    v_g_refundmentCount      Number;
    v_g_amendmentCount       Number;
    v_g_stopCount            Number;
    v_g_paybananceCount      Number;
    v_g_procMsgCode          varChar2(2);
    v_g_procMsg              varChar2(4000);
    v_g_j                    Number;
	v_transactionid          varChar2(16);

    procedure sp_BA_expRefundment (
        v_i_issuym            in      varChar2,
        v_i_paycode           in      varChar2,
        v_i_paydate           in      varChar2,
        v_o_procMsgCode       out     varChar2,
        v_o_procMsg           out     varChar2
    );

    procedure sp_BA_expChangRemitAplpay (
        v_i_issuym            in      varChar2,
        v_i_paycode           in      varChar2,
        v_i_paydate           in      varChar2,
        v_i_bajobid           in      varChar2,
        v_o_flag              out     varChar2
    );

    procedure sp_BA_expSingleChangRemit(
        v_i_apno              in      varChar2,
        v_i_seqno             in      varChar2,
        v_i_oriissuym         in      varChar2,
        v_i_payym             in      varChar2,
        v_i_paydate           in      varChar2,
        v_o_procMsgCode       out     varChar2,
        v_o_procMsg           out     varChar2
    );

    procedure sp_BA_expSingleRec (
        v_i_apno              in      varChar2,
        v_i_seqno             in      varChar2,
        v_i_issuym            in      varChar2,
        v_i_payym             in      varChar2,
        v_i_paydate           in      varChar2
    );

    procedure sp_BA_expCancelSingleRec(
        v_i_apno              in      varChar2,
        v_i_seqno             in      varChar2,
        v_i_oriissuym         in      varChar2,
        v_i_payym             in      varChar2,
        v_i_brchkdate         in      varChar2
    );

    procedure sp_BA_expStexpndRec (
        v_i_paycode           in      varChar2,
        v_i_chkdate           in      varChar2,
        v_i_procempno         in      varChar2,
        v_i_procdeptid        in      varChar2,
        v_i_procip            in      varChar2,
        v_i_bajobid           in      varChar2,
        v_o_flag              out     varchar2
    );

    procedure sp_BA_expDeathRePayRec (
        v_i_apno              in      varChar2,
        v_i_seqno             in      varChar2,
        v_i_oriissuym         in      varChar2,
        v_i_payym             in      varChar2,
        v_i_afpaydate         in      varChar2,
        v_i_issukind          in      varChar2,
        v_o_procMsgCode       out     varChar2,
        v_o_procMsg           out     varChar2
    );

    procedure sp_BA_DeathRePayRefundment (
        v_i_apno              in      varChar2,
        v_i_seqno             in      varChar2,
        v_i_oriissuym         in      varChar2,
        v_i_payym             in      varChar2,
        v_i_brchkdate         in      varChar2,
        v_i_issukind          in      varChar2,
        v_o_procMsgCode       out     varChar2,
        v_o_procMsg           out     varChar2
    );

End;


CREATE OR REPLACE Package body BA.PKG_BA_ProcCashier
is
    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_ProcCashier.sp_BA_expRefundment
        PURPOSE:         將退匯資料轉入出納系統

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_paydate       (varChar2)       --核付日期

        PARAMETER(OUT):  v_o_procMsgCode   (varChar2)       --回傳處理狀態
                         v_o_procMsg       (varChar2)       --回傳處理訊息

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
    procedure sp_BA_expRefundment (
        v_i_issuym            in      varChar2,
        v_i_paycode           in      varChar2,
        v_i_paydate           in      varChar2,
        v_o_procMsgCode       out     varChar2,
        v_o_procMsg           out     varChar2
    )is
        --查詢待轉入出納系統的退匯資料
        Cursor c_dataCur is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO as SEQNO                                                        --受款人序
                  ,t1.ORIISSUYM as ISSUYM                                                   --核定年月
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.INSDK                                                                 --保險別
                  ,t1.ISSUKIND                                                              --核發種類/給付種類
                  ,to_Date(t1.BRCHKDATE,'YYYYMMDD') as BRCHKDATE                            --退匯初核日期
                  ,t1.BRMK                                                                  --退匯狀態
                  ,t1.BRNOTE                                                                --退匯原因
                  ,t1.BLIACCOUNT                                                            --退匯局帳戶代號
                  ,t1.BENTYP                                                                --受款人種類
                  ,t1.ACCIDN                                                                --受款人身分證號(戶名IDN)
                  ,t1.ACCNAME                                                               --受款人姓名(戶名)
                  ,t1.PAYTYP                                                                --核發方式/給付方式
                  ,(t1.PAYBANKID||deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID))||LPAD(t1.PAYEEACC,14,'0')) as BANKACCOUNT     --轉帳帳號(總行+分行+帳號)
                  ,t1.COMMZIP                                                               --郵遞區號
                  ,t1.COMMADDR                                                              --地址(轉出納用)
                  ,t1.COMMTEL                                                               --電話
                  ,t1.REMITAMT                                                              --退匯金額
                  ,t1.RECHKMAN                                                              --複核人員
                  ,to_Date(t1.RECHKDATE,'YYYYMMDD') as RECHKDATE                            --複核日期
                  ,t1.CONFIRMMAN                                                            --確認人員
                  ,to_Date(t1.CONFIRMDATE,'YYYYMMDD') as CONFIRMDATE                        --確認日期
                  ,to_Date(t1.CONFIRMRECDATE,'YYYYMMDD') as CONFIRMRECDATE                  --核收日期
                  ,t1.ACTTITLEAPCODE                                                        --會計科目案類代號
                  ,to_Date(t1.CPRNDATE,'YYYYMMDD') as CPRNDATE                              --清單印表日期
                  ,t1.RPTPAGE                                                               --清單印表頁次
                  ,t1.RPTROWS                                                               --清單印表行次
                  ,t1.PROCUSER                                                              --作業人員代號
                  ,t1.PROCDEPTID                                                            --作業人員的部室別
                  ,t1.PROCIP                                                                --作業人員的IP
                  ,deCode(v_i_paycode,'L','1',t1.NLWKMK) as NLWKMK                          --普職註記
                  ,deCode(v_i_paycode,'L',null,t1.ADWKMK) as ADWKMK                         --加職註記
              from BAPFLBAC t1
             where t1.BRISSUYM = v_i_issuym
               and t1.PAYDATE = v_i_paydate
               and t1.APNO Like (v_i_paycode||'%')
             order by t1.BRNOTE,t1.APNO,t1.ISSUYM,t1.PAYYM,t1.SEQNO;

        begin
            v_g_ProgName := 'PKG_BA_ProcCashier.sp_BA_expRefundment';
            v_g_errMsg := '';
            v_g_rs := '';
            v_g_flag := 0;
            v_g_rowCount := 0;
            v_g_refundmentCount := 0;
            v_g_stopCount := 0;
            v_g_procMsgCode := '0';
            v_g_procMsg := '';
            for v_dataCur in c_dataCur Loop
                v_g_rowCount := v_g_rowCount + 1;

                if v_dataCur.BRNOTE <> '04' then
                    v_g_refundmentCount := v_g_refundmentCount +1;
                else
                    v_g_stopCount := v_g_stopCount +1;
                end if;

                begin
					--Mark by Angela 20190226 Begin--
					--v_g_j := 0;
                    ----為出納系統transaction_id重複設定變數，因為迴圈call的速度太快導致key值重複，用這個方法延長下一次call出納系統的時間 add by TseHua 20190213
                    --for i in 1..1000000 loop
                    --    v_g_j:=i+1;
                    --end loop;
					--v_transactionid  :=  LPAD(SUBSTR(TO_CHAR(CURRENT_TIMESTAMP,'YYYYMMDDHH24MIFF'),1,17)-19110000000000000,16,'0');
                    --dbms_output.put_line('ba_transactionid = ['||v_transactionid ||']');
					--Mark by Angela End--

					--將退匯資料轉入出納系統
                    v_g_rs := PKG_PFXX0W020N.fp_insert_lbac(v_dataCur.INSDK
															   ,v_dataCur.APNO
															   ,v_dataCur.SEQNO
															   ,v_dataCur.PAYYM
															   ,v_dataCur.BRMK
															   ,v_dataCur.ISSUKIND
															   ,v_dataCur.BRNOTE
															   ,v_dataCur.BENTYP
															   ,v_dataCur.PROCUSER
															   ,v_dataCur.BRCHKDATE
															   ,v_dataCur.PAYTYP
															   ,v_dataCur.ACCNAME
															   ,v_dataCur.ACCIDN
															   ,v_dataCur.REMITAMT
															   ,v_dataCur.COMMZIP
															   ,v_dataCur.COMMADDR
															   ,v_dataCur.COMMTEL
															   ,v_dataCur.BLIACCOUNT
															   ,v_dataCur.BANKACCOUNT
															   ,v_dataCur.RECHKMAN
															   ,v_dataCur.RECHKDATE
															   ,v_dataCur.ACTTITLEAPCODE
															   ,v_dataCur.CPRNDATE
															   ,v_dataCur.RPTPAGE
															   ,v_dataCur.RPTROWS
															   ,v_dataCur.CONFIRMMAN
															   ,v_dataCur.CONFIRMDATE
															   ,v_dataCur.CONFIRMRECDATE
															   ,v_dataCur.ISSUYM
															   ,v_dataCur.PROCDEPTID
															   ,v_dataCur.PROCIP
															   ,v_dataCur.NLWKMK
															   ,'1'
															   ,v_dataCur.ADWKMK
															   ,null
															   );

                    if v_g_rs like 'false%' then
                        dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>DataRow:'||v_g_rowCount);
                        dbms_output.put_line('Err Msg:'||v_g_rs);
                        dbms_output.put_line('保險別:'||v_dataCur.INSDK);
                        dbms_output.put_line('受理編號:'||v_dataCur.APNO);
                        dbms_output.put_line('受款人序:'||v_dataCur.SEQNO);
                        dbms_output.put_line('核定年月:'||v_dataCur.ISSUYM);
                        dbms_output.put_line('發放年月:'||v_dataCur.PAYYM);
                        dbms_output.put_line('退匯狀態(別):'||v_dataCur.BRMK);
                        dbms_output.put_line('收繳/核發種類:'||v_dataCur.ISSUKIND);
                        dbms_output.put_line('退匯原因:'||v_dataCur.BRNOTE);
                        dbms_output.put_line('個人或單位:'||v_dataCur.BENTYP);
                        dbms_output.put_line('作業人員代號:'||v_dataCur.PROCUSER);
                        dbms_output.put_line('退匯初核日期:'||v_dataCur.BRCHKDATE);
                        dbms_output.put_line('收繳/核發方式:'||v_dataCur.PAYTYP);
                        dbms_output.put_line('個人或單位名稱:'||v_dataCur.ACCNAME);
                        dbms_output.put_line('身分證號:'||v_dataCur.ACCIDN);
                        dbms_output.put_line('退匯金額:'||v_dataCur.REMITAMT);
                        dbms_output.put_line('郵遞區號:'||v_dataCur.COMMZIP);
                        dbms_output.put_line('地址:'||v_dataCur.COMMADDR);
                        dbms_output.put_line('電話:'||v_dataCur.COMMTEL);
                        dbms_output.put_line('退匯局帳戶代號:'||v_dataCur.BLIACCOUNT);
                        dbms_output.put_line('受款人帳號:'||v_dataCur.BANKACCOUNT);
                        dbms_output.put_line('複核人員:'||v_dataCur.RECHKMAN);
                        dbms_output.put_line('複核日期:'||v_dataCur.RECHKDATE);
                        dbms_output.put_line('會計科目案類代號:'||v_dataCur.ACTTITLEAPCODE);
                        dbms_output.put_line('清單印表日期:'||v_dataCur.CPRNDATE);
                        dbms_output.put_line('清單印表頁次:'||v_dataCur.RPTPAGE);
                        dbms_output.put_line('清單印表行次:'||v_dataCur.RPTROWS);
                        dbms_output.put_line('確認人員:'||v_dataCur.CONFIRMMAN);
                        dbms_output.put_line('確認日期:'||v_dataCur.CONFIRMDATE);
                        dbms_output.put_line('核收日期:'||v_dataCur.CONFIRMRECDATE);
                        dbms_output.put_line('核定年月:'||v_dataCur.ISSUYM);
                        dbms_output.put_line('作業人員的部室別:'||v_dataCur.PROCDEPTID);
                        dbms_output.put_line('作業人員的IP:'||v_dataCur.PROCIP);
                        dbms_output.put_line('普職註記:'||v_dataCur.NLWKMK);
                        dbms_output.put_line('現醫註記:'||'1');
                        dbms_output.put_line('加職註記:'||v_dataCur.ADWKMK);
                        dbms_output.put_line('暫存(死亡改匯傳1，其餘傳空值):'||null);
                        --修改log作法 by TseHua 20180601
                        sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                        'PKG_BA_ProcCashier.sp_BA_expRefundment 失敗，受理編號:' || v_dataCur.APNO || '退匯狀態(別):'||v_dataCur.BRMK || '核定年月:'||v_dataCur.ISSUYM
                         || '發放年月:'||v_dataCur.PAYYM || '受款人序:'||v_dataCur.SEQNO  || '退匯金額:'|| v_dataCur.REMITAMT,
                        replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                        v_g_flag := 1;
                        v_g_procMsgCode := '1';
                        v_g_procMsg := v_g_ProgName||':Err Msg:'||v_g_rs;
                        exit;
                    end if;
                exception
                    when others
                        then
                            v_g_flag := 1;
                            v_g_errMsg := SQLErrm;
                            v_g_procMsgCode := '1';
                            v_g_procMsg := v_g_ProgName||':Error-'||v_g_errMsg;
							              --修改log作法 by TseHua 20180601
                            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                            v_g_ProgName||':Error-'||v_g_errMsg ,
                            replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                            exit;
                end;
            end Loop;

            if v_g_flag=0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉入出納系統退匯(含止付)資料完成。退匯資料筆數: '||v_g_rowCount||' 筆。'||'(退改匯: '||v_g_refundmentCount||'、止付: '||v_g_stopCount||' )');
                --修改log作法 by TseHua 20180601
                sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','0',
                RPAD(v_g_ProgName,85,'-')||'>>轉入出納系統退匯(含止付)資料完成。退匯資料筆數: '||v_g_rowCount||' 筆。'||'(退改匯: '||v_g_refundmentCount||'、止付: '||v_g_stopCount||' )',
                replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            else
                v_g_procMsgCode := '1';
                v_g_procMsg := v_g_ProgName||':Error-'||v_g_procMsg;
                --修改log作法 by TseHua 20180601
                sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                ':Error-(v_g_errMsg)'||v_g_errMsg||'(v_g_procMsg)'||v_g_procMsg ,
                replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            end if;

            v_o_procMsgCode := v_g_procMsgCode;
            v_o_procMsg := v_g_procMsg;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    v_g_procMsgCode := '1';
                    v_g_procMsg := v_g_errMsg;
					 sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                         	  RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg ,
                            replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    v_o_procMsgCode := v_g_procMsgCode;
                    v_o_procMsg := v_g_procMsg;
        end;
    --procedure sp_BA_expRefundment End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_ProcCashier.sp_BA_expChangRemitAplpay
        PURPOSE:         將改匯核付(含給付沖抵)資料轉入出納系統

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_paydate       (varChar2)       --核付日期

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
    procedure sp_BA_expChangRemitAplpay(
        v_i_issuym            in      varChar2,
        v_i_paycode           in      varChar2,
        v_i_paydate           in      varChar2,
        v_i_bajobid           in      varChar2,
        v_o_flag              out     varChar2
    )is
        --查詢待轉入出納系統的改匯核付資料
        Cursor c_dataCur is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO as SEQNO                                                        --受款人序
                  ,t1.TRANSACTIONID                                                         --交易編號
                  ,t1.ISSUYM as ISSUYM                                                      --核定年月
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.INSKD                                                                 --保險別
                  ,to_Date(t1.AFCHKDATE,'YYYYMMDD') as AFCHKDATE                            --改匯初核日期
                  ,t1.BRMK                                                                  --退匯狀態
                  ,t1.AFMK                                                                  --退改匯別
                  ,t1.BLIACCOUNT                                                            --退匯局帳戶代號
                  ,t1.BENTYP                                                                --受款人種類
                  ,t1.ACCIDN                                                                --受款人身分證號(戶名IDN)
                  ,t1.ACCNAME                                                               --受款人姓名(戶名)
                  ,t1.PAYTYP                                                                --核發方式/給付方式
				  ,(t1.PAYBANKID||deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID))||LPAD(t1.PAYEEACC,14,'0')) as BANKACCOUNT     --轉帳帳號(總行+分行+帳號)
                  ,t1.COMMZIP                                                               --郵遞區號
                  ,t1.COMMADDR as BRADDR                                                    --地址
                  ,t1.COMMTEL                                                               --電話
                  ,t1.REMITAMT                                                              --退匯金額
                  ,t1.RECHKMAN                                                              --複核人員
                  ,to_Date(t1.RECHKDATE,'YYYYMMDD') as RECHKDATE                            --複核日期
                  ,t1.EXEMAN                                                                --確認人員
                  ,to_Date(t1.EXEDATE,'YYYYMMDD') as EXEDATE                                --確認日期
                  ,to_Date(t1.AFPAYDATE,'YYYYMMDD') as AFPAYDATE                            --出納核付日期
                  ,t1.ACTTITLEAPCODE                                                        --會計科目案類代號
                  ,to_Date(t1.CPRNDATE,'YYYYMMDD') as CPRNDATE                              --清單印表日期
                  ,t1.RPTPAGE                                                               --清單印表頁次
                  ,t1.RPTROWS                                                               --清單印表行次
                  ,t1.PROCUSER                                                              --作業人員代號
                  ,t1.PROCDEPTID                                                            --作業人員的部室別
                  ,t1.PROCIP                                                                --作業人員的IP
              from BAREGIVEDTL t1
             where t1.ISSUYM = v_i_issuym
               and t1.AFPAYDATE = v_i_paydate
               and t1.APNO Like (v_i_paycode||'%')
               and t1.AFMK in ('2','4')
             order by t1.APNO,t1.ISSUYM,t1.PAYYM,t1.SEQNO;

        begin
            v_g_ProgName := 'PKG_BA_ProcCashier.sp_BA_expChangRemitAplpay';
            v_g_errMsg := '';
            v_g_rs := '';
            v_g_flag := 0;
            v_g_rowCount := 0;
            v_g_amendmentCount := 0;
            v_g_paybananceCount := 0;

            for v_dataCur in c_dataCur Loop
                v_g_rowCount := v_g_rowCount + 1;

                if v_dataCur.AFMK = '2' then
                    v_g_amendmentCount := v_g_amendmentCount +1;
                else
                    v_g_paybananceCount := v_g_paybananceCount +1;
                end if;

                begin
                    if v_dataCur.AFMK = '2' then
                        --將改匯核付資料轉入出納系統(for 改匯核付,不含給付沖抵)
                        v_g_rs := PKG_PFXX0W020N.fp_doPayment(v_dataCur.INSKD
                                                             ,v_dataCur.TRANSACTIONID
                                                             ,v_dataCur.AFMK
                                                             ,v_dataCur.AFCHKDATE
                                                             ,v_dataCur.PAYTYP
                                                             ,v_dataCur.ACCNAME
                                                             ,v_dataCur.ACCIDN
                                                             ,v_dataCur.REMITAMT
                                                             ,v_dataCur.COMMZIP
                                                             ,v_dataCur.BRADDR
                                                             ,v_dataCur.COMMTEL
                                                             ,v_dataCur.BLIACCOUNT
                                                             ,v_dataCur.BANKACCOUNT
                                                             ,v_dataCur.RECHKMAN
                                                             ,v_dataCur.RECHKDATE
                                                             ,v_dataCur.ACTTITLEAPCODE
                                                             ,v_dataCur.CPRNDATE
                                                             ,v_dataCur.RPTPAGE
                                                             ,v_dataCur.RPTROWS
                                                             ,v_dataCur.EXEMAN
                                                             ,v_dataCur.EXEDATE
                                                             ,v_dataCur.AFPAYDATE
                                                             ,v_dataCur.PROCUSER
                                                             ,v_dataCur.PROCDEPTID
                                                             ,v_dataCur.PROCIP
                                                             );

                        if v_g_rs like 'false%' then
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>DataRow:'||v_g_rowCount);
                            dbms_output.put_line('Err Msg:'||v_g_rs);
                            dbms_output.put_line('受理編號:'||v_dataCur.APNO);
                            dbms_output.put_line('保險別:'||v_dataCur.INSKD);
                            dbms_output.put_line('交易編號:'||v_dataCur.TRANSACTIONID);
                            dbms_output.put_line('退改匯狀態:'||v_dataCur.AFMK);
                            dbms_output.put_line('改匯初核日期:'||v_dataCur.AFCHKDATE);
                            dbms_output.put_line('收繳/核發方式:'||v_dataCur.PAYTYP);
                            dbms_output.put_line('個人或單位名稱:'||v_dataCur.ACCNAME);
                            dbms_output.put_line('身分證號:'||v_dataCur.ACCIDN);
                            dbms_output.put_line('退匯金額:'||v_dataCur.REMITAMT);
                            dbms_output.put_line('郵遞區號:'||v_dataCur.COMMZIP);
                            dbms_output.put_line('地址:'||v_dataCur.BRADDR);
                            dbms_output.put_line('電話:'||v_dataCur.COMMTEL);
                            dbms_output.put_line('退匯局帳戶代號:'||v_dataCur.BLIACCOUNT);
                            dbms_output.put_line('受款人帳號:'||v_dataCur.BANKACCOUNT);
                            dbms_output.put_line('複核人員:'||v_dataCur.RECHKMAN);
                            dbms_output.put_line('複核日期:'||v_dataCur.RECHKDATE);
                            dbms_output.put_line('會計科目案類代號:'||v_dataCur.ACTTITLEAPCODE);
                            dbms_output.put_line('清單印表日期:'||v_dataCur.CPRNDATE);
                            dbms_output.put_line('清單印表頁次:'||v_dataCur.RPTPAGE);
                            dbms_output.put_line('清單印表行次:'||v_dataCur.RPTROWS);
                            dbms_output.put_line('確認人員:'||v_dataCur.EXEMAN);
                            dbms_output.put_line('確認日期:'||v_dataCur.EXEDATE);
                            dbms_output.put_line('出納核付日期:'||v_dataCur.AFPAYDATE);
                            dbms_output.put_line('作業人員的ID:'||v_dataCur.PROCUSER);
                            dbms_output.put_line('作業人員的部室別:'||v_dataCur.PROCDEPTID);
                            dbms_output.put_line('作業人員的IP:'||v_dataCur.PROCIP);

                            v_g_flag := 1;
                            --修改log作法 by TseHua 20180601
                            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                             '將改匯核付資料轉入出納系統(for 改匯核付,不含給付沖抵)失敗-受理編號為:' || v_dataCur.APNO || '-退改匯狀態:' || v_dataCur.AFMK ,
                            replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                              v_o_flag := v_g_flag;
                            exit;
                        end if;

                    elsif v_dataCur.AFMK = '4' then
                        --將給付沖抵資料轉入出納系統(for 給付沖抵,不含改匯核付)
                        v_g_rs := PKG_PFXX0W020N.fp_doRetrieve(v_dataCur.TRANSACTIONID
                                                              ,v_dataCur.INSKD
                                                              ,v_dataCur.APNO
                                                              ,v_dataCur.SEQNO
                                                              ,v_dataCur.PAYYM
                                                              ,v_dataCur.AFMK
                                                              ,v_dataCur.AFPAYDATE
                                                              ,v_dataCur.REMITAMT
                                                              ,v_dataCur.PROCUSER
                                                              ,v_dataCur.PROCDEPTID
                                                              ,v_dataCur.PROCIP
                                                              );
                        if v_g_rs like 'false%' then
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>DataRow:'||v_g_rowCount);
                            dbms_output.put_line('Err Msg:'||v_g_rs);
                            dbms_output.put_line('交易編號:'||v_dataCur.TRANSACTIONID);
                            dbms_output.put_line('保險別:'||v_dataCur.INSKD);
                            dbms_output.put_line('受理編號:'||v_dataCur.APNO);
                            dbms_output.put_line('受款人序:'||v_dataCur.SEQNO);
                            dbms_output.put_line('給付年月(發放年月):'||v_dataCur.PAYYM);
                            dbms_output.put_line('退改匯狀態:'||v_dataCur.AFMK);
                            dbms_output.put_line('收回日期:'||v_dataCur.AFPAYDATE);
                            dbms_output.put_line('收回金額:'||v_dataCur.REMITAMT);
                            dbms_output.put_line('作業人員的ID:'||v_dataCur.PROCUSER);
                            dbms_output.put_line('作業人員的部室別:'||v_dataCur.PROCDEPTID);
                            dbms_output.put_line('作業人員的IP:'||v_dataCur.PROCIP);

                            v_g_flag := 1;

                            --修改log作法 by TseHua 20180601
                            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                            '將給付沖抵資料轉入出納系統(for 給付沖抵,不含改匯核付)失敗-受理編號為:' || v_dataCur.APNO || '-退改匯狀態:' ||  v_dataCur.AFMK ,
                            replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                              v_o_flag := v_g_flag;
                            exit;
                        end if;
                    else
                        v_g_flag := 1;
                        v_o_flag := v_g_flag;
                        dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_rs||':'||v_dataCur.APNO||'-退改匯狀態:'||v_dataCur.AFMK);
                        --修改log作法 by TseHua 20180601
                        sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                        '改匯核付(含給付沖抵)資料轉入出納系統失敗' || v_dataCur.APNO ||'-退改匯狀態:' || v_dataCur.AFMK,
                        replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                        exit;
                    end if;
                exception
                    when others
                        then
                            v_g_flag := 1;
                            v_g_errMsg := SQLErrm;
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                            --修改log作法 by TseHua 20180601
                            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                            RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg ,
                            replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                             v_o_flag := v_g_flag;
                            exit;
                end;
            end Loop;

            if v_g_flag=0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉入出納系統改匯核付-批次(含給付沖銷)資料完成。改匯核付(含給付沖銷)資料筆數: '||v_g_rowCount||' 筆。'||'(改匯核付: '||v_g_amendmentCount||'、給付沖銷: '||v_g_paybananceCount||' )');
                --修改log作法 by TseHua 20180601
                 sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'0',
                 RPAD(v_g_ProgName,85,'-')||'>>轉入出納系統改匯核付-批次(含給付沖銷)資料完成。改匯核付(含給付沖銷)資料筆數: '||v_g_rowCount||' 筆。'||'(改匯核付: '||v_g_amendmentCount||'、給付沖銷: '||v_g_paybananceCount||' )',
                 replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                 v_o_flag := v_g_flag;
            end if;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
					--修改log作法 by TseHua 20180601
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg ,
                    replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                    v_g_flag := '1';
                     v_o_flag := v_g_flag;
        end;
    --procedure sp_BA_expChangRemitAplpay End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_ProcCashier.sp_BA_expSingleChangRemit
        PURPOSE:         將改匯核付資料轉入出納系統(單筆)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_oriissuym     (varChar2)       --(原)核定年月
                        *v_i_payym         (varChar2)       --給付年月
                        *v_i_paydate       (varChar2)       --核付日期


        PARAMETER(OUT):  v_o_procMsgCode   (varChar2)       --回傳處理狀態
                         v_o_procMsg       (varChar2)       --回傳處理訊息

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/11/05  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_expSingleChangRemit(
        v_i_apno              in      varChar2,
        v_i_seqno             in      varChar2,
        v_i_oriissuym         in      varChar2,
        v_i_payym             in      varChar2,
        v_i_paydate           in      varChar2,
        v_o_procMsgCode       out     varChar2,
        v_o_procMsg           out     varChar2
    )is
        --查詢待轉入出納系統的改匯核付資料
        Cursor c_dataCur is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO as SEQNO                                                        --受款人序
                  ,t1.TRANSACTIONID                                                         --交易編號
                  ,t1.ISSUYM as ISSUYM                                                      --核定年月
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.INSKD                                                                 --保險別
                  ,to_Date(t1.AFCHKDATE,'YYYYMMDD') as AFCHKDATE                            --改匯初核日期
                  ,t1.BRMK                                                                  --退匯狀態
                  ,t1.AFMK                                                                  --退改匯別
                  ,t1.BLIACCOUNT                                                            --退匯局帳戶代號
                  ,t1.BENTYP                                                                --受款人種類
                  ,t1.ACCIDN                                                                --受款人身分證號(戶名IDN)
                  ,t1.ACCNAME                                                               --受款人姓名(戶名)
                  ,t1.PAYTYP                                                                --核發方式/給付方式
                  ,(t1.PAYBANKID||deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID))||LPAD(t1.PAYEEACC,14,'0')) as BANKACCOUNT     --轉帳帳號(總行+分行+帳號)
                  ,t1.COMMZIP                                                               --郵遞區號
                  ,t1.COMMADDR as BRADDR                                                    --地址
                  ,t1.COMMTEL                                                               --電話
                  ,t1.REMITAMT                                                              --退匯金額
                  ,t1.RECHKMAN                                                              --複核人員
                  ,to_Date(t1.RECHKDATE,'YYYYMMDD') as RECHKDATE                            --複核日期
                  ,t1.EXEMAN                                                                --確認人員
                  ,to_Date(t1.EXEDATE,'YYYYMMDD') as EXEDATE                                --確認日期
                  ,to_Date(t1.AFPAYDATE,'YYYYMMDD') as AFPAYDATE                            --出納核付日期
                  ,t1.ACTTITLEAPCODE                                                        --會計科目案類代號
                  ,to_Date(t1.CPRNDATE,'YYYYMMDD') as CPRNDATE                              --清單印表日期
                  ,t1.RPTPAGE                                                               --清單印表頁次
                  ,t1.RPTROWS                                                               --清單印表行次
                  ,t1.PROCUSER                                                              --作業人員代號
                  ,t1.PROCDEPTID                                                            --作業人員的部室別
                  ,t1.PROCIP                                                                --作業人員的IP
              from BAREGIVEDTL t1
             where t1.APNO = v_i_apno
               and t1.SEQNO = v_i_seqno
               and t1.ORIISSUYM = v_i_oriissuym
               and t1.PAYYM = v_i_payym
               and t1.AFPAYDATE = v_i_paydate
               and t1.AFMK = '2'
             order by t1.APNO,t1.ISSUYM,t1.PAYYM,t1.SEQNO;

        begin
            v_g_ProgName := 'PKG_BA_ProcCashier.sp_BA_expSingleChangRemit';
            v_g_errMsg := '';
            v_g_rs := '';
            v_g_flag := 0;
            v_g_rowCount := 0;

            for v_dataCur in c_dataCur Loop
                v_g_rowCount := v_g_rowCount + 1;

                begin
                    --將改匯核付資料轉入出納系統(for 改匯核付,不含給付沖抵)
                    v_g_rs := PKG_PFXX0W020N.fp_doPayment(v_dataCur.INSKD
                                                         ,v_dataCur.TRANSACTIONID
                                                         ,v_dataCur.AFMK
                                                         ,v_dataCur.AFCHKDATE
                                                         ,v_dataCur.PAYTYP
                                                         ,v_dataCur.ACCNAME
                                                         ,v_dataCur.ACCIDN
                                                         ,v_dataCur.REMITAMT
                                                         ,v_dataCur.COMMZIP
                                                         ,v_dataCur.BRADDR
                                                         ,v_dataCur.COMMTEL
                                                         ,v_dataCur.BLIACCOUNT
                                                         ,v_dataCur.BANKACCOUNT
                                                         ,v_dataCur.RECHKMAN
                                                         ,v_dataCur.RECHKDATE
                                                         ,v_dataCur.ACTTITLEAPCODE
                                                         ,v_dataCur.CPRNDATE
                                                         ,v_dataCur.RPTPAGE
                                                         ,v_dataCur.RPTROWS
                                                         ,v_dataCur.EXEMAN
                                                         ,v_dataCur.EXEDATE
                                                         ,v_dataCur.AFPAYDATE
                                                         ,v_dataCur.PROCUSER
                                                         ,v_dataCur.PROCDEPTID
                                                         ,v_dataCur.PROCIP
                                                         );
                    if v_g_rs like 'false%' then
                        dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>DataRow:'||v_g_rowCount);
                        dbms_output.put_line('Err Msg:'||v_g_rs);
                        dbms_output.put_line('受理編號:'||v_dataCur.APNO);
                        dbms_output.put_line('保險別:'||v_dataCur.INSKD);
                        dbms_output.put_line('交易編號:'||v_dataCur.TRANSACTIONID);
                        dbms_output.put_line('退改匯狀態:'||v_dataCur.AFMK);
                        dbms_output.put_line('改匯初核日期:'||v_dataCur.AFCHKDATE);
                        dbms_output.put_line('收繳/核發方式:'||v_dataCur.PAYTYP);
                        dbms_output.put_line('個人或單位名稱:'||v_dataCur.ACCNAME);
                        dbms_output.put_line('身分證號:'||v_dataCur.ACCIDN);
                        dbms_output.put_line('退匯金額:'||v_dataCur.REMITAMT);
                        dbms_output.put_line('郵遞區號:'||v_dataCur.COMMZIP);
                        dbms_output.put_line('地址:'||v_dataCur.BRADDR);
                        dbms_output.put_line('電話:'||v_dataCur.COMMTEL);
                        dbms_output.put_line('退匯局帳戶代號:'||v_dataCur.BLIACCOUNT);
                        dbms_output.put_line('受款人帳號:'||v_dataCur.BANKACCOUNT);
                        dbms_output.put_line('複核人員:'||v_dataCur.RECHKMAN);
                        dbms_output.put_line('複核日期:'||v_dataCur.RECHKDATE);
                        dbms_output.put_line('會計科目案類代號:'||v_dataCur.ACTTITLEAPCODE);
                        dbms_output.put_line('清單印表日期:'||v_dataCur.CPRNDATE);
                        dbms_output.put_line('清單印表頁次:'||v_dataCur.RPTPAGE);
                        dbms_output.put_line('清單印表行次:'||v_dataCur.RPTROWS);
                        dbms_output.put_line('確認人員:'||v_dataCur.EXEMAN);
                        dbms_output.put_line('確認日期:'||v_dataCur.EXEDATE);
                        dbms_output.put_line('出納核付日期:'||v_dataCur.AFPAYDATE);
                        dbms_output.put_line('作業人員的ID:'||v_dataCur.PROCUSER);
                        dbms_output.put_line('作業人員的部室別:'||v_dataCur.PROCDEPTID);
                        dbms_output.put_line('作業人員的IP:'||v_dataCur.PROCIP);

                        v_g_flag := 1;
                        v_g_errMsg := v_g_ProgName||':Err Msg:'||v_g_rs;

                        --修改log作法 by TseHua 20180601
                        sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                        'PKG_BA_ProcCashier.expSingleChangRemit 失敗，受理編號:' || v_dataCur.APNO || '退改匯狀態:'||v_dataCur.AFMK,
                        replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                        exit;
                    end if;
                exception
                    when others
                        then
                            v_g_flag := 1;
                            v_g_errMsg := SQLErrm;
                            --修改log作法 by TseHua 20180601
                            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                            RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg ,
                            replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                            exit;
                end;
            end Loop;

            if v_g_rowCount>=1 and v_g_flag=0 then
                v_g_flag := 0;
                v_g_errMsg := '';
                 --修改log作法 by TseHua 20180601
                 sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','0',
                 '轉入出納系統改匯核付成功' ,
                 replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                 dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
            else
                if v_g_rowCount=0 then
                    v_g_flag := 1;
                     sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                     v_g_ProgName||':轉入出納系統改匯核付失敗!!查無待核付之改匯資料。',
                     replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                    v_g_errMsg := v_g_ProgName||':轉入出納系統改匯核付失敗!!查無待核付之改匯資料。';
                end if;
            end if;
            v_o_procMsgCode := v_g_flag;
            v_o_procMsg := v_g_errMsg;

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180601
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg ,
                    replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
        end;
    --procedure sp_BA_expSingleChangRemit End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_ProcCashier.sp_BA_expSingleRec
        PURPOSE:         將給付沖抵資料轉入出納系統(單筆)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_issuym        (varChar2)       --核定年月
                        *v_i_payym         (varChar2)       --核定年月
                        *v_i_paydate       (varChar2)       --核付日期

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2010/11/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_expSingleRec(
        v_i_apno              in      varChar2,
        v_i_seqno             in      varChar2,
        v_i_issuym            in      varChar2,
        v_i_payym             in      varChar2,
        v_i_paydate           in      varChar2
    )is
        --查詢待轉入出納系統的給付沖抵資料
        Cursor c_dataCur is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO as SEQNO                                                        --受款人序
                  ,t1.TRANSACTIONID                                                         --交易編號
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.INSKD                                                                 --保險別
                  ,t1.AFMK                                                                  --退改匯別
                  ,t1.REMITAMT                                                              --退匯金額
                  ,to_Date(t1.AFPAYDATE,'YYYYMMDD') as AFPAYDATE                            --出納核付日期
                  ,t1.PROCUSER                                                              --作業人員代號
                  ,t1.PROCDEPTID                                                            --作業人員的部室別
                  ,t1.PROCIP                                                                --作業人員的IP
              from BAREGIVEDTL t1
             where t1.APNO = v_i_apno
               and t1.SEQNO = v_i_seqno
               and t1.ISSUYM = v_i_issuym
               and t1.PAYYM = v_i_payym
               and t1.AFPAYDATE = v_i_paydate
               and t1.AFMK = '4'
               and t1.MK = '4'
             order by t1.APNO,t1.ISSUYM,t1.PAYYM,t1.SEQNO;

        begin
            v_g_ProgName := 'PKG_BA_ProcCashier.sp_BA_expSingleRec';
            v_g_errMsg := '';
            v_g_rs := '';
            v_g_flag := 0;

            for v_dataCur in c_dataCur Loop
                begin
                    if v_dataCur.AFMK = '4' then
                        --將給付沖抵資料轉入出納系統
                        v_g_rs := PKG_PFXX0W020N.fp_doRetrieve(v_dataCur.TRANSACTIONID
                                                              ,v_dataCur.INSKD
                                                              ,v_dataCur.APNO
                                                              ,v_dataCur.SEQNO
                                                              ,v_dataCur.PAYYM
                                                              ,v_dataCur.AFMK
                                                              ,v_dataCur.AFPAYDATE
                                                              ,v_dataCur.REMITAMT
                                                              ,v_dataCur.PROCUSER
                                                              ,v_dataCur.PROCDEPTID
                                                              ,v_dataCur.PROCIP
                                                               );
                        if v_g_rs like 'false%' then
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>DataRow:'||v_g_rowCount);
                            dbms_output.put_line('Err Msg:'||v_g_rs);
                            dbms_output.put_line('交易編號:'||v_dataCur.TRANSACTIONID);
                            dbms_output.put_line('保險別:'||v_dataCur.INSKD);
                            dbms_output.put_line('受理編號:'||v_dataCur.APNO);
                            dbms_output.put_line('受款人序:'||v_dataCur.SEQNO);
                            dbms_output.put_line('給付年月(發放年月):'||v_dataCur.PAYYM);
                            dbms_output.put_line('退改匯狀態:'||v_dataCur.AFMK);
                            dbms_output.put_line('收回日期:'||v_dataCur.AFPAYDATE);
                            dbms_output.put_line('收回金額:'||v_dataCur.REMITAMT);
                            dbms_output.put_line('作業人員的ID:'||v_dataCur.PROCUSER);
                            dbms_output.put_line('作業人員的部室別:'||v_dataCur.PROCDEPTID);
                            dbms_output.put_line('作業人員的IP:'||v_dataCur.PROCIP);

                            v_g_flag := 1;
                            --修改log作法 by TseHua 20180601
                            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                            'PKG_BA_ProcCashier.expSingleRec 失敗，受理編號:' || v_dataCur.APNO || '退改匯狀態' || v_dataCur.AFMK ,
                            replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                            exit;
                        end if;
                    else
                        v_g_flag := 1;
                        dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_rs||':'||v_dataCur.APNO||'-退改匯狀態:'||v_dataCur.AFMK);
                        --修改log作法 by TseHua 20180601
                        sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                        RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_rs||':'||v_dataCur.APNO||'-退改匯狀態:'||v_dataCur.AFMK ,
                        replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                        exit;
                    end if;
                exception
                    when others
                        then
                            v_g_flag := 1;
                            v_g_errMsg := SQLErrm;
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                            --修改log作法 by TseHua 20180601
                              sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                              RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg ,
                              replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                            exit;
                end;
            end Loop;

            if v_g_flag=0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉入出納系統-退匯收回(單筆)完成。');
                --修改log作法 by TseHua 20180601
                sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','0',
                RPAD(v_g_ProgName,85,'-')||'>>轉入出納系統-退匯收回(單筆)完成。',
                replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            end if;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180601
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg ,
                    replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
        end;
    --procedure sp_BA_expSingleRec End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_ProcCashier.sp_BA_expCancelSingleRec
        PURPOSE:         將退匯收回取消的資料轉入出納系統(單筆)

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_issuym        (varChar2)       --核定年月(原)
                        *v_i_payym         (varChar2)       --核定年月
                        *v_i_brchkdate     (varChar2)       --退匯初核日期

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/12/10  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_expCancelSingleRec(
        v_i_apno              in      varChar2,
        v_i_seqno             in      varChar2,
        v_i_oriissuym         in      varChar2,
        v_i_payym             in      varChar2,
        v_i_brchkdate         in      varChar2
    )is
        v_transactionid        varChar2(16) := '';

        --查詢待轉入出納系統的退匯收回取消資料
        Cursor c_dataCur is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO as SEQNO                                                        --受款人序
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.INSDK                                                                 --保險別
                  ,to_Date(t1.BRCHKDATE,'YYYYMMDD') as BRCHKDATE                            --退匯初核日期
                  ,t1.REMITAMT                                                              --退匯金額
                  ,t1.PROCUSER                                                              --作業人員代號
                  ,t1.PROCDEPTID                                                            --作業人員的部室別
                  ,t1.PROCIP                                                                --作業人員的IP
              from BAPFLBAC t1
             where t1.APNO = v_i_apno
               and t1.SEQNO = v_i_seqno
               and t1.ORIISSUYM = v_i_oriissuym
               and t1.PAYYM = v_i_payym
               and t1.BRCHKDATE = v_i_brchkdate
               and t1.AFMK = '3'
               and t1.BRMK = '3'
             order by t1.BRNOTE,t1.APNO,t1.ISSUYM,t1.PAYYM,t1.SEQNO;

        begin
            v_g_ProgName := 'PKG_BA_ProcCashier.sp_BA_expCancelSingleRec';
            v_g_errMsg := '';
            v_g_rs := '';
            v_g_flag := 0;

            for v_dataCur in c_dataCur Loop
                begin
                    select TRANSACTIONID into v_transactionid
                      from (select t.TRANSACTIONID
                              from BAREGIVEDTL t
                             where t.APNO = v_i_apno
                               and t.SEQNO = v_i_seqno
                               and t.ORIISSUYM = v_i_oriissuym
                               and t.PAYYM = v_i_payym
                               and t.MK = '4'
                               and t.AFMK = '4'
                             order by t.TRANSACTIONID desc)
                      where rownum = 1;

                    --將退匯收回取消資料轉入出納系統
                    v_g_rs := PKG_PFXX0W020N.fp_cancel_rb(v_transactionid
                                                         ,v_dataCur.INSDK
                                                         ,v_dataCur.APNO
                                                         ,v_dataCur.SEQNO
                                                         ,v_dataCur.PAYYM
                                                         ,'1'
                                                         ,v_dataCur.BRCHKDATE
                                                         ,v_dataCur.REMITAMT
                                                         ,v_dataCur.PROCUSER
                                                         ,v_dataCur.PROCDEPTID
                                                         ,v_dataCur.PROCIP
                                                         );

                    if v_g_rs like 'false%' then
                        dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>DataRow:'||v_g_rowCount);
                        dbms_output.put_line('Err Msg:'||v_g_rs);
                        dbms_output.put_line('交易編號:'||v_transactionid);
                        dbms_output.put_line('保險別:'||v_dataCur.INSDK);
                        dbms_output.put_line('受理編號:'||v_dataCur.APNO);
                        dbms_output.put_line('受款人序:'||v_dataCur.SEQNO);
                        dbms_output.put_line('發放年月:'||v_dataCur.PAYYM);
                        dbms_output.put_line('退匯狀態(別):'||'1');
                        dbms_output.put_line('收回註銷日期:'||v_dataCur.BRCHKDATE);
                        dbms_output.put_line('收回註銷金額:'||v_dataCur.REMITAMT);
                        dbms_output.put_line('作業人員代號:'||v_dataCur.PROCUSER);
                        dbms_output.put_line('作業人員的部室別:'||v_dataCur.PROCDEPTID);
                        dbms_output.put_line('作業人員的IP:'||v_dataCur.PROCIP);

                        --修改log作法 by TseHua 20180601
                        sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                        'PKG_BA_ProcCashier.expCancelSingleRec 失敗，退匯狀態(別)'||'1' || '受理編號:' ||v_dataCur.APNO ,
                        replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                    end if;

                exception
                    when others
                        then
                            v_g_flag := 1;
                            v_g_errMsg := SQLErrm;
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                             --修改log作法 by TseHua 20180601
                            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                            RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,
                            replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                            exit;
                end;
            end Loop;

            if v_g_flag=0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉入出納系統-退匯收回取消(單筆)完成。');
                --修改log作法 by TseHua 20180601
                sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','0',
                RPAD(v_g_ProgName,85,'-')||'>>轉入出納系統-退匯收回取消(單筆)完成。',
                replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            end if;
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180601
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, '','1',
                    RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,
                    replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
        end;
    --procedure sp_BA_expCancelSingleRec End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_ProcCashier.sp_BA_expStexpndRec
        PURPOSE:         將退匯止付且已做給付收回的資料轉入出納系統

        PARAMETER(IN):  *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期(月核定日期\立已帳日期)
                         v_i_procempno     (varChar2)       --執行作業人員員工編號
                         v_i_procdeptid    (varChar2)       --執行作業人員單位代碼
                         v_i_procip        (varChar2)       --執行作業人員IP
                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

        PARAMETER(OUT):
                        *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua
        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/06/26  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_expStexpndRec(
        v_i_paycode           in      varChar2,
        v_i_chkdate           in      varChar2,
        v_i_procempno         in      varChar2,
        v_i_procdeptid        in      varChar2,
        v_i_procip            in      varChar2,
        v_i_bajobid           in      varChar2,
        v_o_flag              out     varChar2
    )is
        v_procempno                   varChar2(10);
        v_procdeptid                  varChar2(5);
        v_procip                      varChar2(50);
        --查詢待轉入出納系統的退匯止付且已做給付收回的資料
        Cursor c_dataCur is
            select t3.APNO                                             --受理編號
                  ,t3.SEQNO                                            --受款人序
                  ,t1.TRANSACTIONID                                    --交易編號
                  ,t1.SEQNO as TRANSACTIONSEQ                          --交易編號的序號
                  ,t3.PAYYM                                            --給付年月
                  ,t1.INSKD                                            --保險別
                  ,t3.AFMK                                             --退改匯別
                  ,t3.REMITAMT                                         --退匯金額
                  ,to_Date(t3.STEXPNDRECDATE,'YYYYMMDD') as AFPAYDATE  --止付收回日期(出納核付日期)
                  ,t3.ORIISSUYM                                        --最原始的核定年月
                  ,t3.ISSUYM                                           --核定年月
                  ,t3.ISSUKIND                                         --給付種類
                  ,t3.BRISSUYM                                         --退匯核定年月
                  ,t3.BRCHKDATE                                        --退匯初核日期
              from PFLBAC t1,PFLBACEVENT t2,BAPFLBAC t3
             where t1.TRANSACTIONID = t2.TRANSACTIONID
               and t1.SEQNO = t2.SEQNO
               and t1.INSKD = t2.INSKD
               and t3.APNO = t1.APNO
               and t3.SEQNO = t1.GVSEQ
               and t1.ISSUYM = t3.ISSUYM
               and t1.PAYYM = t3.PAYYM
               and t3.APNO like (v_i_paycode||'%')
               and t1.MK = t2.MK
               and t1.INSKD = '1'
               and t1.BNMK = '1'
               and t3.BRMK = '1'
               and t3.AFMK = '4'
               and t3.BRNOTE = '04'
               and nvl(trim(t3.STEXPNDRECMK),' ') = 'Y'
               and nvl(trim(t3.STEXPNDRECDATE),' ') = v_i_chkdate
             order by t1.APNO,t1.ISSUYM,t1.PAYYM,t1.SEQNO;

        begin
            v_g_ProgName := 'PKG_BA_ProcCashier.sp_BA_expStexpndRec';
            v_g_errMsg := '';
            v_g_rs := '';
            v_g_flag := 0;
            v_procempno := '';
            v_procdeptid := '';
            v_procip := '';

            --若無傳入執行作業人員員工編號,則取出參數檔的設定
            if nvl(trim(v_i_procempno),' ')<>' ' then
                v_procempno := v_i_procempno;
            else
                v_procempno := PKG_BA_getPayData.fn_BA_getProcEmpInfo('EMPNO');
            end if;

            --若無傳入執行作業人員單位代碼,則取出參數檔的設定
            if nvl(trim(v_i_procdeptid),' ')<>' ' then
                v_procdeptid := v_i_procdeptid;
            else
                v_procdeptid := PKG_BA_getPayData.fn_BA_getProcEmpInfo('DEPTID');
            end if;

            --若無傳入執行作業人員IP,則取出參數檔的設定
            if nvl(trim(v_i_procip),' ')<>' ' then
                v_procip := v_i_procip;
            else
                v_procip := PKG_BA_getPayData.fn_BA_getProcEmpInfo('IP');
            end if;

            for v_dataCur in c_dataCur Loop
                begin
                    --將退匯止付且已做給付收回的資料轉入出納系統
                    v_g_rs := PKG_PFXX0W020N.fp_doRetrieve(v_dataCur.TRANSACTIONID
                                                          ,v_dataCur.INSKD
                                                          ,v_dataCur.APNO
                                                          ,v_dataCur.SEQNO
                                                          ,v_dataCur.PAYYM
                                                          ,v_dataCur.AFMK
                                                          ,v_dataCur.AFPAYDATE
                                                          ,v_dataCur.REMITAMT
                                                          ,v_procempno
                                                          ,v_procdeptid
                                                          ,v_procip
                                                           );
                    if v_g_rs like 'false%' then
                        dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>DataRow:'||v_g_rowCount);
                        dbms_output.put_line('Err Msg:'||v_g_rs);
                        dbms_output.put_line('交易編號:'||v_dataCur.TRANSACTIONID);
                        dbms_output.put_line('保險別:'||v_dataCur.INSKD);
                        dbms_output.put_line('受理編號:'||v_dataCur.APNO);
                        dbms_output.put_line('受款人序:'||v_dataCur.SEQNO);
                        dbms_output.put_line('給付年月(發放年月):'||v_dataCur.PAYYM);
                        dbms_output.put_line('退改匯狀態:'||v_dataCur.AFMK);
                        dbms_output.put_line('收回日期:'||v_dataCur.AFPAYDATE);
                        dbms_output.put_line('收回金額:'||v_dataCur.REMITAMT);
                        dbms_output.put_line('作業人員的ID:'||v_procempno);
                        dbms_output.put_line('作業人員的部室別:'||v_procdeptid);
                        dbms_output.put_line('作業人員的IP:'||v_procip);

                        v_g_flag := 1;

                        --修改log作法 by TseHua 20180601
                        sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                        '將退匯止付且已做給付收回的資料轉入出納系統作業失敗，受理編號:'||v_dataCur.APNO  || '退改匯狀態:'||v_dataCur.AFMK,
                        replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                         v_o_flag := v_g_flag;
                        exit;
                    else
                        update BAPFLBAC t set t.STEXPNDRECMK = 'A'
                         where t.APNO = v_dataCur.APNO
                           and t.SEQNO = v_dataCur.SEQNO
                           and t.ISSUKIND = v_dataCur.ISSUKIND
                           and t.ORIISSUYM = v_dataCur.ORIISSUYM
                           and t.ISSUYM = v_dataCur.ISSUYM
                           and t.PAYYM = v_dataCur.PAYYM
                           and t.BRISSUYM = v_dataCur.BRISSUYM
                           and t.BRCHKDATE = v_dataCur.BRCHKDATE
                           and t.BRMK = '1'
                           and t.AFMK = '4'
                           and t.BRNOTE = '04'
                           and nvl(trim(t.STEXPNDRECMK),' ') = 'Y'
                           and nvl(trim(t.STEXPNDRECDATE),'') = v_i_chkdate;
                    end if;

                exception
                    when others
                        then
                            v_g_flag := 1;
                            v_g_errMsg := SQLErrm;
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                            --修改log作法 by TseHua 20180601
                            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                            RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,
                            replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                            v_o_flag := v_g_flag;
                            exit;
                end;
            end Loop;

            if v_g_flag=0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>轉入出納系統-退匯止付且已做給付收回的資料完成。');
                --修改log作法 by TseHua 20180601
                sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'0',
                '轉入出納系統-退匯止付且已做給付收回的資料完成。',
                 replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                v_o_flag := v_g_flag;
            end if;
        exception
            when others
                then
                    v_g_flag := 1;
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    --修改log作法 by TseHua 20180601
                        sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                        RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg ,
                        replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                       v_o_flag := v_g_flag;
        end;
    --procedure sp_BA_expStexpndRec End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_ProcCashier.sp_BA_expDeathRePayRec
        PURPOSE:         死亡改匯處理作業，將給付收回資料轉入出納系統完成

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序(原受款人序)
                        *v_i_oriissuym     (varChar2)       --原核定年月
                        *v_i_payym         (varChar2)       --給付年月
                        *v_i_afpaydate     (varChar2)       --改匯初核日期

        PARAMETER(OUT):  v_o_procMsgCode   (varChar2)       --回傳處理狀態
                         v_o_procMsg       (varChar2)       --回傳處理訊息

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/08/13  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_expDeathRePayRec(
        v_i_apno               in      varChar2,
        v_i_seqno              in      varChar2,
        v_i_oriissuym          in      varChar2,
        v_i_payym              in      varChar2,
        v_i_afpaydate          in      varChar2, 
        v_i_issukind           in      varChar2,
        v_o_procMsgCode        out     varChar2,
        v_o_procMsg            out     varChar2
    )is
        v_jobno              mmjoblog.job_no%TYPE;
        v_starttime          TIMESTAMP;
        --查詢待轉入出納系統的給付收回資料
        Cursor c_dataCur is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO                                                                 --受款人序
                  ,t1.TRANSACTIONID                                                         --交易編號
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.INSKD                                                                 --保險別
                  ,t1.AFMK                                                                  --退改匯別
                  ,t1.REMITAMT                                                              --退匯金額
                  ,to_Date(t1.AFPAYDATE,'YYYYMMDD') as AFPAYDATE                            --出納核付日期
                  ,t1.PROCUSER                                                              --作業人員代號
                  ,t1.PROCDEPTID                                                            --作業人員的部室別
                  ,t1.PROCIP                                                                --作業人員的IP
              from BAREGIVEDTL t1
             where t1.APNO = v_i_apno
               and t1.SEQNO = v_i_seqno
               and t1.ORIISSUYM = v_i_oriissuym
               and t1.PAYYM = v_i_payym
               and t1.AFPAYDATE = v_i_afpaydate
               and t1.ISSUKIND = v_i_issukind
               and t1.AFMK = '4'
               and t1.MK = '4'
             order by t1.APNO,t1.ISSUYM,t1.PAYYM,t1.SEQNO;

        begin
            v_g_ProgName := 'PKG_BA_ProcCashier.sp_BA_expDeathRePayRec';
            v_g_rs := '';
            v_g_flag := 0;
            v_g_errMsg := '';
            v_g_rowCount := 0;

            for v_dataCur in c_dataCur Loop
                v_g_rowCount := v_g_rowCount+1;

                begin

                    --  2017/12/21 寫入開始LOG Add By ChungYu
                    v_jobno  := REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','');
                    v_starttime := SYSTIMESTAMP;

                    SP_BA_RECJOBLOG (p_job_no => v_jobno,
                                     p_job_id => 'sp_BA_expDeathRePayRec',
                                     p_step   => '死亡改匯將BA資料轉入PF',
                                     p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||'),'||CHR(10)||
                                                 '核定年月:'||v_i_oriissuym||'),'||CHR(10)||
                                                 '給付種類:'||v_i_issukind||'),'||CHR(10)||
                                                 '交易編號:'||v_dataCur.TRANSACTIONID||'),'||CHR(10)||
                                                 '保險別:'||v_dataCur.INSKD||'),'||CHR(10)||
                                                 '受理編號:'||v_dataCur.APNO||'),'||CHR(10)||
                                                 '受款人序:'||v_dataCur.SEQNO||'),'||CHR(10)||                                                    
                                                 '給付年月(發放年月):'||v_dataCur.PAYYM||'),'||CHR(10)||
                                                 '退改匯狀態:'||v_dataCur.AFMK||'),'||CHR(10)||
                                                 '收回日期:'||v_dataCur.AFPAYDATE||'),'||CHR(10)||
                                                 '收回金額:'||v_dataCur.REMITAMT||'),'||CHR(10)||
                                                 '作業人員的ID:'||v_dataCur.PROCUSER||'),'||CHR(10)||
                                                 '作業人員的部室別:'||v_dataCur.PROCDEPTID||'),'||CHR(10)||
                                                 '作業人員IP：('||v_dataCur.PROCIP||'),');

                    --  2017/12/21 寫入開始LOG Add By ChungYu

                    --將給付沖抵資料轉入出納系統
                    v_g_rs := PKG_PFXX0W020N.fp_doRetrieve(v_dataCur.TRANSACTIONID
                                                          ,v_dataCur.INSKD
                                                          ,v_dataCur.APNO
                                                          ,v_dataCur.SEQNO
                                                          ,v_dataCur.PAYYM
                                                          ,v_dataCur.AFMK
                                                          ,v_dataCur.AFPAYDATE
                                                          ,v_dataCur.REMITAMT
                                                          ,v_dataCur.PROCUSER
                                                          ,v_dataCur.PROCDEPTID
                                                          ,v_dataCur.PROCIP
                                                           );
                    if v_g_rs like 'false%' then
                        dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>DataRow:'||v_g_rowCount);
                        dbms_output.put_line('Err Msg:'||v_g_rs);
                        dbms_output.put_line('交易編號:'||v_dataCur.TRANSACTIONID);
                        dbms_output.put_line('保險別:'||v_dataCur.INSKD);
                        dbms_output.put_line('受理編號:'||v_dataCur.APNO);
                        dbms_output.put_line('受款人序:'||v_dataCur.SEQNO);
                        dbms_output.put_line('給付年月(發放年月):'||v_dataCur.PAYYM);
                        dbms_output.put_line('退改匯狀態:'||v_dataCur.AFMK);
                        dbms_output.put_line('收回日期:'||v_dataCur.AFPAYDATE);
                        dbms_output.put_line('收回金額:'||v_dataCur.REMITAMT);
                        dbms_output.put_line('作業人員的ID:'||v_dataCur.PROCUSER);
                        dbms_output.put_line('作業人員的部室別:'||v_dataCur.PROCDEPTID);
                        dbms_output.put_line('作業人員的IP:'||v_dataCur.PROCIP);

                        v_g_flag := 1;
                        v_g_errMsg := v_g_ProgName||':Err Msg:'||v_g_rs;

                        --  2017/12/21 寫入結束LOG Add By ChungYu
                        SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                         p_job_id =>  'sp_BA_expDeathRePayRec',
                                         p_step   =>  '死亡改匯將BA資料轉入PF',
                                         p_memo   =>  'DataRow：('||v_g_rowCount||'),'||CHR(10)||
                                                      'Err Msg：('||v_g_rs||'),');
                        --  2017/12/21 寫入結束LOG Add By ChungYu

                        exit;
                    end if;
                exception
                    when others
                        then
                            v_g_flag := 1;
                            v_g_errMsg := SQLErrm;
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                             --  2017/12/21 寫入結束LOG Add By ChungYu
                             SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                              p_job_id =>  'sp_BA_expDeathRePayRec',
                                              p_step   =>  '死亡改匯將BA資料轉入PF',
                                              p_memo   =>  'DataRow：('||v_g_rowCount||'),'||CHR(10)||
                                                           'Err Msg：('||v_g_errMsg||'),');
                             --  2017/12/21 寫入結束LOG Add By ChungYu
                            exit;
                end;
            end Loop;

            if v_g_rowCount>=1 and v_g_flag=0 then
                v_g_flag := 0;
                v_g_errMsg := '';
            else
                if v_g_rowCount=0 then
                    v_g_flag := 2;
                    v_g_errMsg := v_g_ProgName||':(死亡改匯處理作業)無給付收回資料。';
                    --  2017/12/21 寫入結束LOG Add By ChungYu
                    SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                     p_job_id =>  'sp_BA_expDeathRePayRec',
                                     p_step   =>  '死亡改匯將BA資料轉入PF',
                                     p_memo   =>  'DataRow：('||v_g_rowCount||'),'||CHR(10)||
                                                  'Err Msg：('||v_g_errMsg||'),');
                    --  2017/12/21 寫入結束LOG Add By ChungYu
                end if;
            end if;

            v_o_procMsgCode := v_g_flag;
            v_o_procMsg := v_g_errMsg;

            if v_g_flag=0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>(死亡改匯處理作業)給付收回資料轉入出納系統完成。');
            else
                rollback;
            end if;

        exception
            when others
                then
                    v_o_procMsgCode := 1;
                    v_o_procMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_o_procMsg);
                    --  2017/12/21 寫入結束LOG Add By ChungYu
                    SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                     p_job_id =>  'sp_BA_expDeathRePayRec',
                                     p_step   =>  '死亡改匯將BA資料轉入PF',
                                     p_memo   =>  'cMsgCode：('||v_o_procMsgCode||'),'||CHR(10)||
                                                  'Err Msg：('||v_o_procMsg||'),');
                    --  2017/12/21 寫入結束LOG Add By ChungYu
                    rollback;
        end;
    --procedure sp_BA_expDeathRePayRec End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_ProcCashier.sp_BA_DeathRePayRefundment
        PURPOSE:         死亡改匯處理作業，將新增的退匯資料轉回出納系統，再將新增的改匯資料轉入給付系統

        PARAMETER(IN):  *v_i_apno          (varChar2)       --受理編號
                        *v_i_seqno         (varChar2)       --受款人序
                        *v_i_oriissuym     (varChar2)       --原核定年月
                        *v_i_payym         (varChar2)       --給付年月
                        *v_i_brchkdate     (varChar2)       --退匯初核日期

        PARAMETER(OUT):  v_o_procMsgCode   (varChar2)       --回傳處理狀態
                         v_o_procMsg       (varChar2)       --回傳處理訊息

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/08/13  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_DeathRePayRefundment(
        v_i_apno               in      varChar2,
        v_i_seqno              in      varChar2,
        v_i_oriissuym          in      varChar2,
        v_i_payym              in      varChar2,
        v_i_brchkdate          in      varChar2,
        v_i_issukind           in      varChar2,
        v_o_procMsgCode        out     varChar2,
        v_o_procMsg            out     varChar2
    )is
        v_dataCount            Number := 0;
        v_transactionid        varChar2(16) := '';
        v_jobno                mmjoblog.job_no%TYPE;
        v_starttime            TIMESTAMP;

        --查詢待轉入出納系統的退匯資料
        Cursor c_dataCur_1 is
            select t1.APNO                                                                  --受理編號
                  ,t1.SEQNO as SEQNO                                                        --受款人序
                  ,t1.ORIISSUYM as ISSUYM                                                   --核定年月
                  ,t1.PAYYM                                                                 --給付年月
                  ,t1.INSDK                                                                 --保險別
                  ,t1.ISSUKIND                                                              --核發種類/給付種類
                  ,to_Date(t1.BRCHKDATE,'YYYYMMDD') as BRCHKDATE                            --退匯初核日期
                  ,t1.BRMK                                                                  --退匯狀態
                  ,t1.BRNOTE                                                                --退匯原因
                  ,t1.BLIACCOUNT                                                            --退匯局帳戶代號
                  ,t1.BENTYP                                                                --受款人種類
                  ,deCode(t1.PAYTYP,'A',t1.BENIDN,t1.ACCIDN) as ACCIDN                      --受款人身分證號(戶名IDN)
                  ,deCode(t1.PAYTYP,'A',t1.BENNAME,t1.ACCNAME) as ACCNAME                   --受款人姓名(戶名)
                  ,t1.PAYTYP                                                                --核發方式/給付方式
                  ,(t1.PAYBANKID||deCode(t1.PAYBANKID,'700',t1.BRANCHID,deCode(t1.PAYTYP,'1','0000',t1.BRANCHID))||LPAD(t1.PAYEEACC,14,'0')) as BANKACCOUNT     --轉帳帳號(總行+分行+帳號)
                  ,t1.COMMZIP                                                               --郵遞區號
                  ,t1.COMMADDR                                                              --地址(轉出納用)
                  ,t1.COMMTEL                                                               --電話
                  ,t1.REMITAMT                                                              --退匯金額
                  ,t1.RECHKMAN                                                              --複核人員
                  ,to_Date(t1.RECHKDATE,'YYYYMMDD') as RECHKDATE                            --複核日期
                  ,t1.CONFIRMMAN                                                            --確認人員
                  ,to_Date(t1.CONFIRMDATE,'YYYYMMDD') as CONFIRMDATE                        --確認日期
                  ,to_Date(t1.CONFIRMRECDATE,'YYYYMMDD') as CONFIRMRECDATE                  --核收日期
                  ,t1.ACTTITLEAPCODE                                                        --會計科目案類代號
                  ,to_Date(t1.CPRNDATE,'YYYYMMDD') as CPRNDATE                              --清單印表日期
                  ,t1.RPTPAGE                                                               --清單印表頁次
                  ,t1.RPTROWS                                                               --清單印表行次
                  ,t1.PROCUSER                                                              --作業人員代號
                  ,t1.PROCDEPTID                                                            --作業人員的部室別
                  ,t1.PROCIP                                                                --作業人員的IP
                  ,deCode(substr(v_i_apno,1,1),'L','1',t1.NLWKMK) as NLWKMK                 --普職註記
                  ,deCode(substr(v_i_apno,1,1),'L',null,t1.ADWKMK) as ADWKMK                --加職註記
              from BAPFLBAC t1
             where t1.APNO = v_i_apno
               and t1.SEQNO = v_i_seqno
               and t1.ORIISSUYM = v_i_oriissuym
               and t1.PAYYM = v_i_payym
               and t1.BRCHKDATE = v_i_brchkdate
               and t1.ISSUKIND = v_i_issukind
               and t1.AFMK = '3'
               and t1.BRMK = '3'
             order by t1.BRNOTE,t1.APNO,t1.ISSUYM,t1.PAYYM,t1.SEQNO;

        --待轉入給付系統的退改匯資料
        Cursor c_dataCur_2(v_apno varChar2,v_seqno varChar2,v_brchkdate varChar2,v_issukind varChar2) is
            select t1.APNO as APNO                                                                  --受理編號
                  ,t1.GVSEQ as SEQNO                                                                --受款人序
                  ,t3.ORIISSUYM                                                                     --原核定年月
                  ,t3.BRISSUYM as BRISSUYM                                                          --退匯核定年月
                  ,t1.PAYYM as PAYYM                                                                --給付年月
                  ,t1.INSKD as INSKD                                                                --保險別
                  ,t1.ACCEPT_ISSUE_CD as ISSUKIND                                                   --核發種類/給付種類
                  ,t1.TRANSACTIONID as TRANSACTIONID                                                --交易編號
                  ,nvl(trim(t2.SEQNO),'1') as TRANSACTIONSEQ                                        --交易編號的序號
                  ,t2.MK as MK                                                                      --退改匯別
                  ,t1.BNMK as BRMK                                                                  --退匯狀態
                  ,to_Char(t2.CHKSDATE, 'YYYYMMDD') as AFCHKDATE                                    --改匯初核日期
                  ,t2.BLI_ACCOUNT_CODE as BLIACCOUNT                                                --退改匯局帳戶代號
                  ,t1.PER_UNIT_CD as BENTYP                                                         --受款人種類
                  ,t3.BENIDS                                                                        --受款人社福識別碼
                  ,t3.BENIDN                                                                        --受款人身分證號
                  ,t3.BENNAME                                                                       --受款人姓名
                  ,t2.IDNO as ACCIDN                                                                --戶名IDN
                  ,t2.PER_UNIT_NAME as ACCNAME                                                      --戶名
                  ,DECODE(length(to_Char(t1.GVSEQ)),1,('0'||to_Char(t1.GVSEQ)||'00')
                                                   ,(to_Char(t1.GVSEQ)||'00'),'0000') as ACCSEQNO   --被共同具領之受款人員序號
                  ,'B' as ISSUTYP                                                                   --核付分類
                  ,t2.WDCDTYPE as PAYTYP                                                            --核發方式/給付方式
                  ,substr(t2.BANKACCOUNT,1,3) as PAYBANKID                                          --轉帳帳號(總行)
				  ,deCode(substr(t2.BANKACCOUNT,1,3),'700',substr(t2.BANKACCOUNT,4,4),deCode(t2.WDCDTYPE,'1','0000', substr(t2.BANKACCOUNT,4,4))) as BRANCHID --轉帳帳號(分行)
                  ,substr(t2.BANKACCOUNT,8,21) as PAYEEACC                                          --轉帳帳號(帳號)
                  ,t2.ZIPCD as COMMZIP                                                              --郵遞區號
                  ,t2.ADDRESS as COMMADDR                                                           --地址
                  ,t2.PHONENO as COMMTEL                                                            --電話
                  ,t2.REMITAMT as REMITAMT                                                          --退匯金額
                  ,t3.REPAYSEQNO as REPAYSEQNO                                                      --繼承來源之受款人序
              from PFLBAC t1 ,PFLBACEVENT t2,BAPFLBAC t3
             where t1.TRANSACTIONID = t2.TRANSACTIONID
               and t1.SEQNO = t2.SEQNO
               and t1.INSKD = t2.INSKD
               and t3.APNO = t1.APNO
               and t3.SEQNO = t1.GVSEQ
               and t1.ISSUYM = t3.ORIISSUYM
               and t1.PAYYM = t3.PAYYM
               and t1.APNO = v_apno
               and t1.GVSEQ = v_seqno
               and t3.BRCHKDATE = v_brchkdate
               and t3.ORIISSUYM = v_i_oriissuym
               and t3.PAYYM = v_i_payym
               and t1.MK = t2.MK           
               and t1.ACCEPT_ISSUE_CD = t3.ISSUKIND
               and t3.ISSUKIND = v_issukind
               and t1.INSKD = '1'
               and t1.BNMK = '3'
               and t3.BRMK = '3'
               and t3.AFMK = '3'
               and ((t2.MK = '1' and nvl(trim(t2.PAYDTE),' ')<>' ' and trim(t2.PAYDTE) is not null)
                 or (t2.MK = '2' and (nvl(trim(t2.PAYDTE),' ')=' ' or trim(t2.PAYDTE) is null)))
               and t2.SEQNO = (select to_Char(Max(tt2.SEQNO))
                                 from PFLBAC tt1,PFLBACEVENT tt2
                                where tt1.TRANSACTIONID = tt2.TRANSACTIONID
                                  and tt1.TRANSACTIONID = t1.TRANSACTIONID
                                  and t3.APNO = tt1.APNO
                                  and t3.ORIISSUYM = tt1.ISSUYM
                                  and t3.PAYYM = tt1.PAYYM
                                  and tt1.INSKD = tt2.INSKD
                                  and tt1.MK = tt2.MK
                                  and tt1.ACCEPT_ISSUE_CD = v_issukind
                                  and tt1.INSKD = '1'
                                  and tt1.BNMK = '3'
                                  and ((tt2.MK = '1' and nvl(trim(tt2.PAYDTE),' ')<>' ' and trim(tt2.PAYDTE) is not null)
                                    or (tt2.MK = '2' and (nvl(trim(tt2.PAYDTE),' ')=' ' or trim(tt2.PAYDTE) is null)))
                               )
               order by t1.APNO,t1.ISSUYM,t1.PAYYM,t1.GVSEQ;

        begin
            v_g_ProgName := 'PKG_BA_ProcCashier.sp_BA_DeathRePayRefundment';
            v_g_rs := '';
            v_g_flag := 0;
            v_g_errMsg := '';
            v_g_rowCount := 0;
            v_dataCount := 0;
            v_transactionid := '';

            for v_dataCur_1 in c_dataCur_1 Loop
                v_g_rowCount := v_g_rowCount+1;

                begin
                    select Count(*) into v_dataCount
                      from BAREGIVEDTL t
                     where t.APNO = v_i_apno
                       and t.SEQNO = v_i_seqno
                       and t.ORIISSUYM = v_i_oriissuym
                       and t.PAYYM = v_i_payym
                       and t.ISSUKIND = v_i_issukind
                       and t.MK = '4'
                       and t.AFMK = '4';

                    --需先判斷，該筆資料是否已有給付收回的記錄。
                    --若已有給付收回的記錄時，則 Call 給付收回註銷；
                    --反之，若無給付收回的記錄時，則直接新增新的退改匯資料。
                    if v_dataCount>0 then
                        select TRANSACTIONID into v_transactionid
                          from (select t.TRANSACTIONID
                                  from BAREGIVEDTL t
                                 where t.APNO = v_i_apno
                                   and t.SEQNO = v_i_seqno
                                   and t.ORIISSUYM = v_i_oriissuym
                                   and t.PAYYM = v_i_payym
                                   and t.ISSUKIND = v_i_issukind
                                   and t.MK = '4'
                                   and t.AFMK = '4'
                                 order by t.TRANSACTIONID desc)
                          where rownum = 1;


                        --  2017/12/21 寫入開始LOG Add By ChungYu
                        v_jobno  := REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','');
                        v_starttime := SYSTIMESTAMP;

                        SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                         p_job_id =>  'sp_BA_DeathRePayRefundment',
                                         p_step   => '新增的退匯資料轉回出納系統',
                                         p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||'),'||CHR(10)||
                                                     'PKG_PFXX0W020N:'||'fp_cancel_rb'||'),'||CHR(10)||
                                                     '交易編號:'||v_transactionid||'),'||CHR(10)||
                                                     '保險別:'||v_dataCur_1.INSDK||'),'||CHR(10)||
                                                     '受理編號:'||v_dataCur_1.APNO||'),'||CHR(10)||
                                                     '受款人序:'||v_dataCur_1.SEQNO||'),'||CHR(10)||
                                                     '發放年月:'||v_dataCur_1.PAYYM||'),'||CHR(10)||
                                                     '退匯狀態(別):'||'1'||'),'||CHR(10)||
                                                     '收回註銷日期:'||v_dataCur_1.BRCHKDATE||'),'||CHR(10)||
                                                     '收回註銷金額:'||v_dataCur_1.REMITAMT||'),'||CHR(10)||
                                                     '作業人員的ID:'||v_dataCur_1.PROCUSER||'),'||CHR(10)||
                                                     '作業人員的部室別:'||v_dataCur_1.PROCDEPTID||'),'||CHR(10)||
                                                     '作業人員IP：('||v_dataCur_1.PROCIP||'),');

                        --  2017/12/21 寫入開始LOG Add By ChungYu

                        v_g_rs := PKG_PFXX0W020N.fp_cancel_rb(v_transactionid
                                                             ,v_dataCur_1.INSDK
                                                             ,v_dataCur_1.APNO
                                                             ,v_dataCur_1.SEQNO
                                                             ,v_dataCur_1.PAYYM
                                                             ,'1'
                                                             ,v_dataCur_1.BRCHKDATE
                                                             ,v_dataCur_1.REMITAMT
                                                             ,v_dataCur_1.PROCUSER
                                                             ,v_dataCur_1.PROCDEPTID
                                                             ,v_dataCur_1.PROCIP
                                                             );

                        if v_g_rs like 'false%' then
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>DataRow:'||v_g_rowCount);
                            dbms_output.put_line('Err Msg:'||v_g_rs);
                            dbms_output.put_line('交易編號:'||v_transactionid);
                            dbms_output.put_line('保險別:'||v_dataCur_1.INSDK);
                            dbms_output.put_line('受理編號:'||v_dataCur_1.APNO);
                            dbms_output.put_line('受款人序:'||v_dataCur_1.SEQNO);
                            dbms_output.put_line('發放年月:'||v_dataCur_1.PAYYM);
                            dbms_output.put_line('退匯狀態(別):'||'1');
                            dbms_output.put_line('收回註銷日期:'||v_dataCur_1.BRCHKDATE);
                            dbms_output.put_line('收回註銷金額:'||v_dataCur_1.REMITAMT);
                            dbms_output.put_line('作業人員代號:'||v_dataCur_1.PROCUSER);
                            dbms_output.put_line('作業人員的部室別:'||v_dataCur_1.PROCDEPTID);
                            dbms_output.put_line('作業人員的IP:'||v_dataCur_1.PROCIP);

                            --  2017/12/21 寫入結束LOG Add By ChungYu
                            SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                             p_job_id =>  'sp_BA_DeathRePayRefundment',
                                             p_step   =>  '死亡改匯將BA資料轉入PF',
                                             p_memo   =>  'DataRow：('||v_g_rowCount||'),'||CHR(10)||
                                                          'Err Msg：('||v_g_rs||'),');
                            --  2017/12/21 寫入結束LOG Add By ChungYu
                        end if;
                    else
                       --  2017/12/21 寫入開始LOG Add By ChungYu
                        v_jobno  := REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','');
                        v_starttime := SYSTIMESTAMP;

                        SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                         p_job_id =>  'sp_BA_DeathRePayRefundment',
                                         p_step   => '新增的退匯資料轉回出納系統',
                                         p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||'),'||CHR(10)||
                                                     'PKG_PFXX0W020N:'||'fp_insert_lbac'||'),'||CHR(10)||
                                                     '保險別:'||v_dataCur_1.INSDK||'),'||CHR(10)||
                                                     '受理編號:'||v_dataCur_1.APNO||'),'||CHR(10)||
                                                     '受款人序:'||v_dataCur_1.SEQNO||'),'||CHR(10)||
                                                     '發放年月:'||v_dataCur_1.PAYYM||'),'||CHR(10)||
                                                     '退匯狀態(別):'||v_dataCur_1.BRMK||'),'||CHR(10)||
                                                     '收繳/核發種類:'||v_dataCur_1.ISSUKIND||'),'||CHR(10)||
                                                     '退匯原因:'||v_dataCur_1.BRNOTE||'),'||CHR(10)||
                                                     '個人或單位:'||v_dataCur_1.BENTYP||'),'||CHR(10)||
                                                     '作業人員代號:'||v_dataCur_1.PROCUSER||'),'||CHR(10)||
                                                     '退匯初核日期:'||v_dataCur_1.BRCHKDATE||'),'||CHR(10)||
                                                     '收繳/核發方式:'||v_dataCur_1.PAYTYP||'),'||CHR(10)||
                                                     '個人或單位名稱:'||v_dataCur_1.ACCNAME||'),'||CHR(10)||
                                                     '身分證號:'||v_dataCur_1.ACCIDN||'),'||CHR(10)||
                                                     '退匯金額:'||v_dataCur_1.REMITAMT||'),'||CHR(10)||
                                                     '郵遞區號:'||v_dataCur_1.COMMZIP||'),'||CHR(10)||
                                                     '地址:'||v_dataCur_1.COMMADDR||'),'||CHR(10)||
                                                     '電話:'||v_dataCur_1.COMMTEL||'),'||CHR(10)||
                                                     '退匯局帳戶代號:'||v_dataCur_1.BLIACCOUNT||'),'||CHR(10)||
                                                     '受款人帳號:'||v_dataCur_1.BANKACCOUNT||'),'||CHR(10)||
                                                     '複核人員:'||v_dataCur_1.RECHKMAN||'),'||CHR(10)||
                                                     '複核日期:'||v_dataCur_1.RECHKDATE||'),'||CHR(10)||
                                                     '會計科目案類代號:'||v_dataCur_1.ACTTITLEAPCODE||'),'||CHR(10)||
                                                     '清單印表日期:'||v_dataCur_1.CPRNDATE||'),'||CHR(10)||
                                                     '清單印表頁次:'||v_dataCur_1.RPTPAGE||'),'||CHR(10)||
                                                     '清單印表行次:'||v_dataCur_1.RPTROWS||'),'||CHR(10)||
                                                     '確認人員:'||v_dataCur_1.CONFIRMMAN||'),'||CHR(10)||
                                                     '確認日期:'||v_dataCur_1.CONFIRMDATE||'),'||CHR(10)||
                                                     '核收日期:'||v_dataCur_1.CONFIRMRECDATE||'),'||CHR(10)||
                                                     '核定年月:'||v_dataCur_1.ISSUYM||'),'||CHR(10)||
                                                     '作業人員的部室別:'||v_dataCur_1.PROCDEPTID||'),'||CHR(10)||
                                                     '作業人員的IP:'||v_dataCur_1.PROCIP||'),'||CHR(10)||
                                                     '普職註記:'||v_dataCur_1.NLWKMK||'),'||CHR(10)||
                                                     '現醫註記:'||'1'||'),'||CHR(10)||
                                                     '加職註記:'||v_dataCur_1.ADWKMK||'),'||CHR(10)||
                                                     '暫存(死亡改匯傳1，其餘傳空值):'||'1'||'),');

                        --  2017/12/21 寫入開始LOG Add By ChungYu

                        --將退匯資料轉入出納系統
                        v_g_rs := PKG_PFXX0W020N.fp_insert_lbac(v_dataCur_1.INSDK
                                                               ,v_dataCur_1.APNO
                                                               ,v_dataCur_1.SEQNO
                                                               ,v_dataCur_1.PAYYM
                                                               ,v_dataCur_1.BRMK
                                                               ,v_dataCur_1.ISSUKIND
                                                               ,v_dataCur_1.BRNOTE
                                                               ,v_dataCur_1.BENTYP
                                                               ,v_dataCur_1.PROCUSER
                                                               ,v_dataCur_1.BRCHKDATE
                                                               ,v_dataCur_1.PAYTYP
                                                               ,v_dataCur_1.ACCNAME
                                                               ,v_dataCur_1.ACCIDN
                                                               ,v_dataCur_1.REMITAMT
                                                               ,v_dataCur_1.COMMZIP
                                                               ,v_dataCur_1.COMMADDR
                                                               ,v_dataCur_1.COMMTEL
                                                               ,v_dataCur_1.BLIACCOUNT
                                                               ,v_dataCur_1.BANKACCOUNT
                                                               ,v_dataCur_1.RECHKMAN
                                                               ,v_dataCur_1.RECHKDATE
                                                               ,v_dataCur_1.ACTTITLEAPCODE
                                                               ,v_dataCur_1.CPRNDATE
                                                               ,v_dataCur_1.RPTPAGE
                                                               ,v_dataCur_1.RPTROWS
                                                               ,v_dataCur_1.CONFIRMMAN
                                                               ,v_dataCur_1.CONFIRMDATE
                                                               ,v_dataCur_1.CONFIRMRECDATE
                                                               ,v_dataCur_1.ISSUYM
                                                               ,v_dataCur_1.PROCDEPTID
                                                               ,v_dataCur_1.PROCIP
                                                               ,v_dataCur_1.NLWKMK
                                                               ,'1'
                                                               ,v_dataCur_1.ADWKMK
                                                               ,'1'                          --for死亡後改匯的特殊註記
                                                               );
                        if v_g_rs like 'false%' then
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>DataRow:'||v_g_rowCount);
                            dbms_output.put_line('Err Msg:'||v_g_rs);
                            dbms_output.put_line('保險別:'||v_dataCur_1.INSDK);
                            dbms_output.put_line('受理編號:'||v_dataCur_1.APNO);
                            dbms_output.put_line('受款人序:'||v_dataCur_1.SEQNO);
                            dbms_output.put_line('發放年月:'||v_dataCur_1.PAYYM);
                            dbms_output.put_line('退匯狀態(別):'||v_dataCur_1.BRMK);
                            dbms_output.put_line('收繳/核發種類:'||v_dataCur_1.ISSUKIND);
                            dbms_output.put_line('退匯原因:'||v_dataCur_1.BRNOTE);
                            dbms_output.put_line('個人或單位:'||v_dataCur_1.BENTYP);
                            dbms_output.put_line('作業人員代號:'||v_dataCur_1.PROCUSER);
                            dbms_output.put_line('退匯初核日期:'||v_dataCur_1.BRCHKDATE);
                            dbms_output.put_line('收繳/核發方式:'||v_dataCur_1.PAYTYP);
                            dbms_output.put_line('個人或單位名稱:'||v_dataCur_1.ACCNAME);
                            dbms_output.put_line('身分證號:'||v_dataCur_1.ACCIDN);
                            dbms_output.put_line('退匯金額:'||v_dataCur_1.REMITAMT);
                            dbms_output.put_line('郵遞區號:'||v_dataCur_1.COMMZIP);
                            dbms_output.put_line('地址:'||v_dataCur_1.COMMADDR);
                            dbms_output.put_line('電話:'||v_dataCur_1.COMMTEL);
                            dbms_output.put_line('退匯局帳戶代號:'||v_dataCur_1.BLIACCOUNT);
                            dbms_output.put_line('受款人帳號:'||v_dataCur_1.BANKACCOUNT);
                            dbms_output.put_line('複核人員:'||v_dataCur_1.RECHKMAN);
                            dbms_output.put_line('複核日期:'||v_dataCur_1.RECHKDATE);
                            dbms_output.put_line('會計科目案類代號:'||v_dataCur_1.ACTTITLEAPCODE);
                            dbms_output.put_line('清單印表日期:'||v_dataCur_1.CPRNDATE);
                            dbms_output.put_line('清單印表頁次:'||v_dataCur_1.RPTPAGE);
                            dbms_output.put_line('清單印表行次:'||v_dataCur_1.RPTROWS);
                            dbms_output.put_line('確認人員:'||v_dataCur_1.CONFIRMMAN);
                            dbms_output.put_line('確認日期:'||v_dataCur_1.CONFIRMDATE);
                            dbms_output.put_line('核收日期:'||v_dataCur_1.CONFIRMRECDATE);
                            dbms_output.put_line('核定年月:'||v_dataCur_1.ISSUYM);
                            dbms_output.put_line('作業人員的部室別:'||v_dataCur_1.PROCDEPTID);
                            dbms_output.put_line('作業人員的IP:'||v_dataCur_1.PROCIP);
                            dbms_output.put_line('普職註記:'||v_dataCur_1.NLWKMK);
                            dbms_output.put_line('現醫註記:'||'1');
                            dbms_output.put_line('加職註記:'||v_dataCur_1.ADWKMK);
                            dbms_output.put_line('暫存(死亡改匯傳1，其餘傳空值):'||'1');
                        end if;
                    end if;

                    if v_g_rs like 'false%' then
                        v_g_flag := 1;
                        v_g_errMsg := v_g_ProgName||':Err Msg:'||v_g_rs;

                        --  2017/12/21 寫入結束LOG Add By ChungYu
                        SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                         p_job_id =>  'sp_BA_DeathRePayRefundment',
                                         p_step   =>  '死亡改匯將BA資料轉入PF',
                                         p_memo   =>  'PKG_PFXX0W020N:'||'fp_insert_lbac'||'),'||CHR(10)||
                                                      'Err Msg：('||v_g_rs||'),');
                        --  2017/12/21 寫入結束LOG Add By ChungYu
                        exit;
                    else
                        for v_dataCur_2 in c_dataCur_2(v_i_apno,v_i_seqno,v_i_brchkdate,v_i_issukind) Loop
                            insert into BAREGIVEDTL (APNO
                                                    ,SEQNO
                                                    ,ORIISSUYM
                                                    ,BRISSUYM
                                                    ,PAYYM
                                                    ,TRANSACTIONID
                                                    ,TRANSACTIONSEQ
                                                    ,INSKD
                                                    ,ISSUKIND
                                                    ,MK
                                                    ,BRMK
                                                    ,AFCHKDATE
                                                    ,BLIACCOUNT
                                                    ,BENTYP
                                                    ,BENIDS
                                                    ,BENIDN
                                                    ,BENNAME
                                                    ,ACCIDN
                                                    ,ACCNAME
                                                    ,ACCSEQNO
                                                    ,ISSUTYP
                                                    ,PAYTYP
                                                    ,PAYBANKID
                                                    ,BRANCHID
                                                    ,PAYEEACC
                                                    ,COMMZIP
                                                    ,COMMADDR
                                                    ,COMMTEL
                                                    ,REPAYSEQNO
                                                    ,REMITAMT
                                                    ,UPDTIME
                                                    ) values (
                                                     v_dataCur_2.APNO
                                                    ,v_dataCur_2.SEQNO
                                                    ,v_dataCur_2.ORIISSUYM
                                                    ,v_dataCur_2.BRISSUYM
                                                    ,v_dataCur_2.PAYYM
                                                    ,v_dataCur_2.TRANSACTIONID
                                                    ,v_dataCur_2.TRANSACTIONSEQ
                                                    ,v_dataCur_2.INSKD
                                                    ,v_dataCur_2.ISSUKIND
                                                    ,v_dataCur_2.MK
                                                    ,v_dataCur_2.BRMK
                                                    ,v_dataCur_2.AFCHKDATE
                                                    ,v_dataCur_2.BLIACCOUNT
                                                    ,v_dataCur_2.BENTYP
                                                    ,v_dataCur_2.BENIDS
                                                    ,v_dataCur_2.BENIDN
                                                    ,v_dataCur_2.BENNAME
                                                    ,deCode(v_dataCur_2.PAYTYP,'A','',v_dataCur_2.BENIDN)
                                                    ,deCode(v_dataCur_2.PAYTYP,'A','',v_dataCur_2.BENNAME)
                                                    ,v_dataCur_2.ACCSEQNO
                                                    ,v_dataCur_2.ISSUTYP
                                                    ,v_dataCur_2.PAYTYP
                                                    ,v_dataCur_2.PAYBANKID
                                                    ,v_dataCur_2.BRANCHID
                                                    ,v_dataCur_2.PAYEEACC
                                                    ,v_dataCur_2.COMMZIP
                                                    ,v_dataCur_2.COMMADDR
                                                    ,v_dataCur_2.COMMTEL
                                                    ,v_dataCur_2.REPAYSEQNO
                                                    ,v_dataCur_2.REMITAMT
                                                    ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                            );
                        end Loop;
                    end if;
                exception
                    when others
                        then
                            v_g_flag := 1;
                            v_g_errMsg := SQLErrm;

                            --  2017/12/21 寫入結束LOG Add By ChungYu
                            SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                             p_job_id =>  'sp_BA_DeathRePayRefundment',
                                             p_step   =>  '死亡改匯將BA資料轉入PF',
                                             p_memo   =>  'insert:'||'BAREGIVEDTL'||'),'||CHR(10)||
                                                          'Err Msg：('||v_g_errMsg||'),');
                            --  2017/12/21 寫入結束LOG Add By ChungYu
                            exit;
                end;
            end Loop;

            if v_g_rowCount>=1 and v_g_flag=0 then
                v_g_flag := 0;
                v_g_errMsg := '';
            else
                if v_g_rowCount=0 then
                    v_g_flag := 2;
                    v_g_errMsg := v_g_ProgName||':(死亡改匯處理作業)無退匯資料。';
                    --  2017/12/21 寫入結束LOG Add By ChungYu
                    SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                     p_job_id =>  'sp_BA_DeathRePayRefundment',
                                     p_step   =>  '死亡改匯將BA資料轉入PF',
                                     p_memo   =>  'flag:'||'2'||'),'||CHR(10)||
                                                  'Err Msg：('||v_g_errMsg||'),');
                    --  2017/12/21 寫入結束LOG Add By ChungYu
                end if;
            end if;

            v_o_procMsgCode := v_g_flag;
            v_o_procMsg := v_g_errMsg;

            if v_g_flag=0 then
                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>(死亡改匯處理作業)退匯資料轉入出納系統完成。');
            else
                rollback;
            end if;
        exception
            when others
                then
                    v_o_procMsgCode := 1;
                    v_o_procMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_o_procMsg);

                    --  2017/12/21 寫入結束LOG Add By ChungYu
                    SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                     p_job_id =>  'sp_BA_DeathRePayRefundment',
                                     p_step   =>  '死亡改匯將BA資料轉入PF',
                                     p_memo   =>  'procMsgCode:'||'1'||'),'||CHR(10)||
                                                  'Err Msg：('||v_o_procMsg||'),');
                    --  2017/12/21 寫入結束LOG Add By ChungYu
                    rollback;
        end;
    --procedure sp_BA_DeathRePayRefundment End
End;