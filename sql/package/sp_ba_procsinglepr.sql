create or replace procedure ba.SP_BA_ProcSinglePR(
    v_i_procempno            in varChar2,
    v_i_procdeptid           in varChar2,
    v_i_procip               in varChar2
) authid definer is
    v_g_errMsg               varChar2(4000);
    v_g_rs                   varChar2(200);
    v_g_procempno            varChar2(10);
    v_g_procdeptid           varChar2(5);
    v_g_procip               varChar2(16);
    v_g_rowCount             Number;
    v_dataCount              Number;

    Cursor c_bapflbac_1 is
        select t1.APNO as APNO
              ,t1.SEQNO as SEQNO
              ,t1.PAYDATE as PAYDATE
              ,t1.BRISSUYM as BRISSUYM
              ,t1.ORIISSUYM as ORIISSUYM
              ,t1.ORIISSUYM as ISSUYM
              ,t1.PAYYM as PAYYM
              ,'1' as INSKD
              ,t1.ISSUKIND as ISSUKIND
              ,t3.REMITDATE as BRCHKDATE
              ,t1.MK as AFMK
              ,'3' as BRMK
              ,t1.BRNOTE
              ,'0053' as BLIACCOUNT
              ,deCode(t2.BENEVTREL,'1','1','2','1','3','1'
                                  ,'4','1','5','1','6','1'
                                  ,'7','1','2') as BENTYP
              ,t2.BENIDS as BENIDS
              ,t2.BENIDNNO as BENIDN
              ,t2.BENNAME as BENNAME
              ,'B' as ISSUTYP
              ,t3.PAYTYP as PAYTYP
              ,t3.PAYBANKID as PAYBANKID
              ,t3.BRANCHID as BRANCHID
              ,t3.PAYEEACC as PAYEEACC
              ,t3.ACCIDN as ACCIDN
              ,t3.ACCNAME as ACCNAME
              ,nvl(t2.ACCSEQNO,t2.SEQNO) as ACCSEQNO
              ,t2.COMMZIP as COMMZIP
              ,t2.COMMADDR as COMMADDR
              ,t2.COMMADDR as BRADDR
              ,t2.TEL1 as COMMTEL
              ,t1.PAYAMT as PAYAMT
              ,'KKB' as ACTTITLEAPCODE
              ,'550001' as RPTPAGE
              ,'1' as RPTROWS
              ,t3.REMITDATE as CPRNDATE
              ,v_g_procempno as RECHKMAN
              ,t3.REMITDATE as RECHKDATE
              ,v_g_procempno as CONFIRMMAN
              ,t3.REMITDATE as CONFIRMDATE
              ,t3.REMITDATE as CONFIRMRECDATE
              ,v_g_procempno as PROCUSER
              ,v_g_procdeptid as PROCDEPTID
              ,v_g_procip as PROCIP
          from BAREGIVEDTLTMP t1,BAAPPBASE t2,BADAPR t3
         where t1.APNO = t2.APNO
           and t1.APNO = t3.APNO
           and t2.APNO = t3.APNO
           and t1.SEQNO = t2.SEQNO
           and t1.SEQNO = t3.SEQNO
           and t2.SEQNO = t3.SEQNO
           and t1.ORIISSUYM = t3.ISSUYM
           and t1.PAYYM = t3.PAYYM
           and t1.PROCFLAG = '0'
           and (t2.CHKDATE is not null and nvl(trim(t2.CHKDATE),' ')<>' ')
           and (t2.EXEDATE is not null and nvl(trim(t2.EXEDATE),' ')<>' ')
           and t3.MTESTMK = 'F'
           and t3.MANCHKMK = 'Y'
           and (t3.ACCEPTMK = 'Y' or nvl(trim(t3.ACCEPTMK),' ') = ' ')
           and t3.REMITMK in ('2','3');

    Cursor c_bapflbac_2 is
        select t1.APNO
              ,t1.SEQNO
              ,t1.ISSUYM
              ,t1.PAYYM
              ,t1.INSDK
              ,t1.ISSUKIND
              ,to_Date(t1.BRCHKDATE,'YYYYMMDD') as BRCHKDATE
              ,t1.BRMK
              ,t1.BRNOTE
              ,t1.BLIACCOUNT
              ,t1.BENTYP
              ,t1.ACCIDN
              ,t1.ACCNAME
              ,t1.PAYTYP
              ,(t1.PAYBANKID||t1.BRANCHID||LPAD(t1.PAYEEACC,14,'0')) as BANKACCOUNT
              ,t1.COMMZIP
              ,t1.COMMADDR
              ,t1.COMMTEL
              ,t1.REMITAMT
              ,t1.RECHKMAN
              ,to_Date(t1.RECHKDATE,'YYYYMMDD') as RECHKDATE
              ,t1.CONFIRMMAN
              ,to_Date(t1.CONFIRMDATE,'YYYYMMDD') as CONFIRMDATE
              ,to_Date(t1.CONFIRMRECDATE,'YYYYMMDD') as CONFIRMRECDATE
              ,t1.ACTTITLEAPCODE
              ,to_Date(t1.CPRNDATE,'YYYYMMDD') as CPRNDATE
              ,t1.RPTPAGE
              ,t1.RPTROWS
              ,t1.PROCUSER
              ,t1.PROCDEPTID
              ,t1.PROCIP
          from BAPFLBAC t1,BAREGIVEDTLTMP t2
         where t1.APNO = t2.APNO
           and t1.SEQNO = t2.SEQNO
           and t1.ORIISSUYM = t2.ORIISSUYM
           and t1.PAYYM = t2.PAYYM
           and t2.PROCFLAG = '0'
           and t2.MK = '1'
           and t1.AFMK = '3'
         order by t1.BRNOTE,t1.APNO,t1.ISSUYM,t1.PAYYM,t1.SEQNO;

    Cursor c_bapflbac_3 is
        select t1.APNO as APNO
              ,t1.GVSEQ as SEQNO
              ,t3.ORIISSUYM as ORIISSUYM
              ,t3.BRISSUYM as BRISSUYM
              ,t1.PAYYM as PAYYM
              ,t1.INSKD as INSKD
              ,t1.ACCEPT_ISSUE_CD as ISSUKIND
              ,t1.TRANSACTIONID as TRANSACTIONID
              ,nvl(trim((select to_Char(Max(tt2.SEQNO))
                           from PFLBAC tt1,PFLBACEVENT tt2
                          where tt1.TRANSACTIONID = tt2.TRANSACTIONID
                            and t3.APNO = tt1.APNO
                            and t3.ORIISSUYM = tt1.ISSUYM
                            and t3.PAYYM = tt1.PAYYM
                            and tt1.INSKD = tt2.INSKD
                            and tt1.MK = tt2.MK
                            and tt1.INSKD = '1'
                            and tt1.BNMK = '3'
                            and ((tt2.MK = '1' and nvl(trim(tt2.PAYDTE),' ')<>' ' and trim(tt2.PAYDTE) is not null)
                              or (tt2.MK = '2' and (nvl(trim(tt2.PAYDTE),' ')=' ' or trim(tt2.PAYDTE) is null)))
                        )),'1') as TRANSACTIONSEQ
              ,t2.MK as MK
              ,t1.BNMK as BRMK
              ,to_Char(t2.CHKSDATE, 'YYYYMMDD') as AFCHKDATE
              ,'0053' as BLIACCOUNT
              ,t3.BENTYP as BENTYP
              ,t3.BENIDS
              ,t3.BENIDN
              ,t3.BENNAME
              ,t2.IDNO as ACCIDN
              ,t2.PER_UNIT_NAME as ACCNAME
              ,DECODE(length(to_Char(t1.GVSEQ)),1,('0'||to_Char(t1.GVSEQ)||'00')
                                                 ,(to_Char(t1.GVSEQ)||'00'),'0000') as ACCSEQNO
              ,'B' as ISSUTYP
              ,t2.WDCDTYPE as PAYTYP
              ,substr(t2.BANKACCOUNT,1,3) as PAYBANKID
              ,substr(t2.BANKACCOUNT,4,4) as BRANCHID
              ,substr(t2.BANKACCOUNT,8,21) as PAYEEACC
              ,t2.ZIPCD as COMMZIP
              ,t2.ADDRESS as COMMADDR
              ,t2.PHONENO as COMMTEL
              ,t2.REMITAMT as REMITAMT
          from PFLBAC t1 ,PFLBACEVENT t2,BAPFLBAC t3,BAREGIVEDTLTMP t4
         where t1.TRANSACTIONID = t2.TRANSACTIONID
           and t1.SEQNO = t2.SEQNO
           and t1.INSKD = t2.INSKD
           and t3.APNO = t1.APNO
           and t3.APNO = t4.APNO
           and t3.SEQNO = t4.SEQNO
           and t3.ORIISSUYM = t4.ORIISSUYM
           and t3.PAYYM = t4.PAYYM
           and t4.PROCFLAG = '0'
           and t4.MK = '1'
           and t1.ISSUYM = t3.ISSUYM
           and t1.PAYYM = t3.PAYYM
           and t1.MK = t2.MK
           and t1.INSKD = '1'
           and t1.BNMK = '3'
           and t3.AFMK = '3'
           and ((t2.MK = '1' and nvl(trim(t2.PAYDTE),' ')<>' ' and trim(t2.PAYDTE) is not null)
             or (t2.MK = '2' and (nvl(trim(t2.PAYDTE),' ')=' ' or trim(t2.PAYDTE) is null)))
           order by t1.APNO,t1.ISSUYM,t1.PAYYM,t1.GVSEQ;

    Cursor c_bapflbac_4 is
          select t1.APNO as APNO
              ,t1.SEQNO as SEQNO
              ,t1.ORIISSUYM as ORIISSUYM
              ,t1.BRISSUYM as BRISSUYM
              ,t1.ISSUYM as ISSUYM
              ,t1.PAYYM as PAYYM
              ,to_Char(Sysdate,'YYYYMMDDHH24MISS') as TRANSACTIONID
              ,'1' as TRANSACTIONSEQ
              ,'1' as INSKD
              ,t1.ISSUKIND as ISSUKIND
              ,'2' as MK
              ,'3' as BRMK
              ,'2' as AFMK
              ,' ' as WORKMK
              ,t1.AFPAYDATE as AFCHKDATE
              ,' ' as AFPAYDATE
              ,'0053' as BLIACCOUNT
              ,deCode(t2.BENEVTREL,'1','1','2','1','3','1'
                                  ,'4','1','5','1','6','1'
                                  ,'7','1','2') as BENTYP
              ,t2.BENIDS as BENIDS
              ,t2.BENIDNNO as BENIDN
              ,t2.BENNAME as BENNAME
              ,'B' as ISSUTYP
              ,t1.PAYTYP as PAYTYP
              ,t1.PAYBANKID as PAYBANKID
              ,t1.BRANCHID as BRANCHID
              ,t1.PAYEEACC as PAYEEACC
              ,t2.ACCIDN as ACCIDN
              ,t2.ACCNAME as ACCNAME
              ,nvl(t2.ACCSEQNO,t2.SEQNO) as ACCSEQNO
              ,t2.COMMZIP as COMMZIP
              ,t2.COMMADDR as COMMADDR
              ,t2.COMMADDR as BRADDR
              ,t2.TEL1 as COMMTEL
              ,t1.PAYAMT as PAYAMT
              ,'0' as RECAMT
              ,v_g_procempno as RECHKMAN
              ,t1.AFPAYDATE as RECHKDATE
              ,t1.AFPAYDATE as EXEDATE
              ,v_g_procempno EXEMAN
              ,' ' as ACTTITLEAPCODE
              ,t1.AFPAYDATE as CPRNDATE
              ,'560001' as RPTPAGE
              ,'1' as RPTROWS
              ,' ' as GENMEDDATE
              ,v_g_procempno as PROCUSER
              ,v_g_procdeptid as PROCDEPTID
              ,v_g_procip as PROCIP
          from BAREGIVEDTLTMP t1,BAAPPBASE t2,BADAPR t3
         where t1.APNO = t2.APNO
           and t1.APNO = t3.APNO
           and t2.APNO = t3.APNO
           and t1.SEQNO = t2.SEQNO
           and t1.SEQNO = t3.SEQNO
           and t2.SEQNO = t3.SEQNO
           and t1.MK = '2'
           and t1.ORIISSUYM = t3.ISSUYM
           and t1.PAYYM = t3.PAYYM
           and t1.PROCFLAG = '0'
           and (t2.CHKDATE is not null and nvl(trim(t2.CHKDATE),' ')<>' ')
           and (t2.EXEDATE is not null and nvl(trim(t2.EXEDATE),' ')<>' ')
           and t3.MTESTMK = 'F'
           and t3.MANCHKMK = 'Y'
           and (t3.ACCEPTMK = 'Y' or nvl(trim(t3.ACCEPTMK),' ') = ' ')
           and t3.REMITMK in ('2','3');

    begin
        --若無傳入執行作業人員員工編號,則取出參數檔的設定
        if nvl(trim(v_i_procempno),' ')<>' ' then
            v_g_procempno := v_i_procempno;
        else
            v_g_procempno := PKG_BA_getPayData.fn_BA_getProcEmpInfo('EMPNO');
        end if;

        --若無傳入執行作業人員單位代碼,則取出參數檔的設定
        if nvl(trim(v_i_procdeptid),' ')<>' ' then
            v_g_procdeptid := v_i_procdeptid;
        else
            v_g_procdeptid := PKG_BA_getPayData.fn_BA_getProcEmpInfo('DEPTID');
        end if;

        --若無傳入執行作業人員單位代碼,則取出參數檔的設定
        if nvl(trim(v_i_procip),' ')<>' ' then
            v_g_procip := v_i_procip;
        else
            v_g_procip := PKG_BA_getPayData.fn_BA_getProcEmpInfo('IP');
        end if;

        begin
            v_g_errMsg := '';
            v_g_rowCount := 0;

            for v_dataCur1 in c_bapflbac_1 Loop
                v_g_rowCount := v_g_rowCount + 1;

                v_dataCount := 0;
                select Count(*) into v_dataCount
                  from BAPFLBAC t1
                 where t1.APNO = v_dataCur1.APNO
                   and t1.SEQNO = v_dataCur1.SEQNO
                   and t1.ORIISSUYM = v_dataCur1.ORIISSUYM
                   and t1.PAYYM = v_dataCur1.PAYYM
                   and t1.PAYDATE = v_dataCur1.PAYDATE
                   and t1.BRCHKDATE = v_dataCur1.BRCHKDATE;

                if v_dataCount = 0 then
                    --寫入退匯資料檔
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
                                         ,RPTPAGE
                                         ,RPTROWS
                                         ,RECHKMAN
                                         ,RECHKDATE
                                         ,CONFIRMMAN
                                         ,CONFIRMDATE
                                         ,CONFIRMRECDATE
                                         ,PROCUSER
                                         ,PROCDEPTID
                                         ,PROCIP
                                         ,UPDTIME
                                        ) values (
                                          v_dataCur1.APNO
                                         ,v_dataCur1.SEQNO
                                         ,v_dataCur1.PAYDATE
                                         ,v_dataCur1.BRISSUYM
                                         ,v_dataCur1.ORIISSUYM
                                         ,v_dataCur1.ISSUYM
                                         ,v_dataCur1.PAYYM
                                         ,v_dataCur1.INSKD
                                         ,v_dataCur1.ISSUKIND
                                         ,v_dataCur1.BRCHKDATE
                                         ,v_dataCur1.AFMK
                                         ,v_dataCur1.BRMK
                                         ,v_dataCur1.BRNOTE
                                         ,v_dataCur1.BLIACCOUNT
                                         ,v_dataCur1.BENTYP
                                         ,v_dataCur1.BENIDS
                                         ,v_dataCur1.BENIDN
                                         ,v_dataCur1.BENNAME
                                         ,v_dataCur1.ISSUTYP
                                         ,v_dataCur1.PAYTYP
                                         ,v_dataCur1.PAYBANKID
                                         ,v_dataCur1.BRANCHID
                                         ,v_dataCur1.PAYEEACC
                                         ,v_dataCur1.ACCIDN
                                         ,v_dataCur1.ACCNAME
                                         ,v_dataCur1.ACCSEQNO
                                         ,v_dataCur1.COMMZIP
                                         ,v_dataCur1.COMMADDR
                                         ,v_dataCur1.BRADDR
                                         ,v_dataCur1.COMMTEL
                                         ,v_dataCur1.PAYAMT
                                         ,v_dataCur1.ACTTITLEAPCODE
                                         ,v_dataCur1.CPRNDATE
                                         ,v_dataCur1.RPTPAGE
                                         ,v_dataCur1.RPTROWS
                                         ,v_g_procempno
                                         ,v_dataCur1.RECHKDATE
                                         ,v_g_procempno
                                         ,v_dataCur1.CONFIRMDATE
                                         ,v_dataCur1.CONFIRMRECDATE
                                         ,v_g_procempno
                                         ,v_g_procdeptid
                                         ,v_g_procip
                                         ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                        );
                end if;
            end Loop;

            if v_g_rowCount>0 then
                dbms_output.put_line('  SP_BA_ProcSinglePR------------------------------>>退匯資料檔-[ '||v_g_rowCount||' ]');
            else
                dbms_output.put_line('  SP_BA_ProcSinglePR------------------------------>>退匯資料檔-[ NoData ]');
            end if;
        end;

        begin
            v_g_errMsg := '';
            v_g_rs := '';
            v_g_rowCount := 0;

            for v_dataCur2 in c_bapflbac_2 Loop
                v_g_rowCount := v_g_rowCount + 1;

                --當 BAREGIVEDTLTMP.MK = 1 ,先將退匯資料轉入出納系統
                begin
                    v_g_rs := PKG_PFXX0W020N.fp_insert_lbac(v_dataCur2.INSDK
                                                                 ,v_dataCur2.APNO
                                                                 ,v_dataCur2.SEQNO
                                                                 ,v_dataCur2.PAYYM
                                                                 ,v_dataCur2.BRMK
                                                                 ,v_dataCur2.ISSUKIND
                                                                 ,v_dataCur2.BRNOTE
                                                                 ,v_dataCur2.BENTYP
                                                                 ,v_dataCur2.PROCUSER
                                                                 ,v_dataCur2.BRCHKDATE
                                                                 ,v_dataCur2.PAYTYP
                                                                 ,v_dataCur2.ACCNAME
                                                                 ,v_dataCur2.ACCIDN
                                                                 ,v_dataCur2.REMITAMT
                                                                 ,v_dataCur2.COMMZIP
                                                                 ,v_dataCur2.COMMADDR
                                                                 ,v_dataCur2.COMMTEL
                                                                 ,v_dataCur2.BLIACCOUNT
                                                                 ,v_dataCur2.BANKACCOUNT
                                                                 ,v_dataCur2.RECHKMAN
                                                                 ,v_dataCur2.RECHKDATE
                                                                 ,v_dataCur2.ACTTITLEAPCODE
                                                                 ,v_dataCur2.CPRNDATE
                                                                 ,v_dataCur2.RPTPAGE
                                                                 ,v_dataCur2.RPTROWS
                                                                 ,v_dataCur2.CONFIRMMAN
                                                                 ,v_dataCur2.CONFIRMDATE
                                                                 ,v_dataCur2.CONFIRMRECDATE
                                                                 ,v_dataCur2.ISSUYM
                                                                 ,v_dataCur2.PROCDEPTID
                                                                 ,v_dataCur2.PROCIP
                                                                 ,'1'
                                                                 ,'1'
                                                                 ,null
                                                                );

                    if v_g_rs like 'false%' then
                        dbms_output.put_line('**Err:SP_BA_ProcSinglePR(退匯資料轉入出納系統)---->>DataRow:'||v_g_rowCount);
                        dbms_output.put_line('Err Msg:'||v_g_rs);
                        dbms_output.put_line('保險別:'||v_dataCur2.INSDK);
                        dbms_output.put_line('受理編號:'||v_dataCur2.APNO);
                        dbms_output.put_line('受款人序:'||v_dataCur2.SEQNO);
                        dbms_output.put_line('核定年月:'||v_dataCur2.ISSUYM);
                        dbms_output.put_line('發放年月:'||v_dataCur2.PAYYM);
                        dbms_output.put_line('退匯狀態(別):'||v_dataCur2.BRMK);
                        dbms_output.put_line('收繳/核發種類:'||v_dataCur2.ISSUKIND);
                        dbms_output.put_line('退匯原因:'||v_dataCur2.BRNOTE);
                        dbms_output.put_line('個人或單位:'||v_dataCur2.BENTYP);
                        dbms_output.put_line('作業人員代號:'||v_dataCur2.PROCUSER);
                        dbms_output.put_line('退匯初核日期:'||v_dataCur2.BRCHKDATE);
                        dbms_output.put_line('收繳/核發方式:'||v_dataCur2.PAYTYP);
                        dbms_output.put_line('個人或單位名稱:'||v_dataCur2.ACCNAME);
                        dbms_output.put_line('身分證號:'||v_dataCur2.ACCIDN);
                        dbms_output.put_line('退匯金額:'||v_dataCur2.REMITAMT);
                        dbms_output.put_line('郵遞區號:'||v_dataCur2.COMMZIP);
                        dbms_output.put_line('地址:'||v_dataCur2.COMMADDR);
                        dbms_output.put_line('電話:'||v_dataCur2.COMMTEL);
                        dbms_output.put_line('退匯局帳戶代號:'||v_dataCur2.BLIACCOUNT);
                        dbms_output.put_line('受款人帳號:'||v_dataCur2.BANKACCOUNT);
                        dbms_output.put_line('複核人員:'||v_dataCur2.RECHKMAN);
                        dbms_output.put_line('複核日期:'||v_dataCur2.RECHKDATE);
                        dbms_output.put_line('會計科目案類代號:'||v_dataCur2.ACTTITLEAPCODE);
                        dbms_output.put_line('清單印表日期:'||v_dataCur2.CPRNDATE);
                        dbms_output.put_line('清單印表頁次:'||v_dataCur2.RPTPAGE);
                        dbms_output.put_line('清單印表行次:'||v_dataCur2.RPTROWS);
                        dbms_output.put_line('確認人員:'||v_dataCur2.CONFIRMMAN);
                        dbms_output.put_line('確認日期:'||v_dataCur2.CONFIRMDATE);
                        dbms_output.put_line('核收日期:'||v_dataCur2.CONFIRMRECDATE);
                        dbms_output.put_line('核定年月:'||v_dataCur2.ISSUYM);
                        dbms_output.put_line('作業人員的部室別:'||v_dataCur2.PROCDEPTID);
                        dbms_output.put_line('作業人員的IP:'||v_dataCur2.PROCIP);
                        dbms_output.put_line('現醫註記:'||'1');
                        dbms_output.put_line('普職註記:'||'1');
                        dbms_output.put_line('加職註記:'||null);

                        exit;
                    end if;
                exception
                    when others
                        then
                            v_g_errMsg := SQLErrm;
                            dbms_output.put_line('**Err:SP_BA_ProcSinglePR(退匯資料轉入出納系統)---->>'||v_g_errMsg);
                            exit;
                end;
            end Loop;

            if v_g_rowCount>0 then
                dbms_output.put_line('  SP_BA_ProcSinglePR------------------------------>>退匯資料轉入出納系統-[ '||v_g_rowCount||' ]');
            else
                dbms_output.put_line('  SP_BA_ProcSinglePR------------------------------>>退匯資料轉入出納系統-[ NoData ]');
            end if;
        end;

        begin
            v_g_errMsg := '';
            v_g_rowCount := 0;

            for v_dataCur3 in c_bapflbac_3 Loop
                v_g_rowCount := v_g_rowCount + 1;

                --將退改匯資料轉入給付系統
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
                                        ,REMITAMT
                                        ,UPDTIME
                                        ) values (
                                         v_dataCur3.APNO
                                        ,v_dataCur3.SEQNO
                                        ,v_dataCur3.ORIISSUYM
                                        ,v_dataCur3.BRISSUYM
                                        ,v_dataCur3.PAYYM
                                        ,v_dataCur3.TRANSACTIONID
                                        ,v_dataCur3.TRANSACTIONSEQ
                                        ,v_dataCur3.INSKD
                                        ,v_dataCur3.ISSUKIND
                                        ,v_dataCur3.MK
                                        ,v_dataCur3.BRMK
                                        ,v_dataCur3.AFCHKDATE
                                        ,v_dataCur3.BLIACCOUNT
                                        ,v_dataCur3.BENTYP
                                        ,v_dataCur3.BENIDS
                                        ,v_dataCur3.BENIDN
                                        ,v_dataCur3.BENNAME
                                        ,v_dataCur3.BENIDN
                                        ,v_dataCur3.BENNAME
                                        ,v_dataCur3.ACCSEQNO
                                        ,v_dataCur3.ISSUTYP
                                        ,v_dataCur3.PAYTYP
                                        ,v_dataCur3.PAYBANKID
                                        ,v_dataCur3.BRANCHID
                                        ,v_dataCur3.PAYEEACC
                                        ,v_dataCur3.COMMZIP
                                        ,v_dataCur3.COMMADDR
                                        ,v_dataCur3.COMMTEL
                                        ,v_dataCur3.REMITAMT
                                        ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                        );
            end Loop;

            if v_g_rowCount>0 then
                dbms_output.put_line('  SP_BA_ProcSinglePR------------------------------>>退改匯資料轉入給付系統(退匯中)-[ '||v_g_rowCount||' ]');
            else
                dbms_output.put_line('  SP_BA_ProcSinglePR------------------------------>>退改匯資料轉入給付系統(退匯中)-[ NoData ]');
            end if;
        end;

        begin
            v_g_errMsg := '';
            v_g_rowCount := 0;

            for v_dataCur4 in c_bapflbac_4 Loop
                v_g_rowCount := v_g_rowCount + 1;

                --當 BAREGIVEDTLTMP.MK = 2 , 直接產生改匯資料檔
                insert into BAREGIVEDTL (APNO
                                        ,SEQNO
                                        ,ORIISSUYM
                                        ,BRISSUYM
                                        ,ISSUYM
                                        ,PAYYM
                                        ,TRANSACTIONID
                                        ,TRANSACTIONSEQ
                                        ,INSKD
                                        ,ISSUKIND
                                        ,MK
                                        ,BRMK
                                        ,AFMK
                                        ,WORKMK
                                        ,AFCHKDATE
                                        ,AFPAYDATE
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
                                        ,COMMTEL
                                        ,REMITAMT
                                        ,RECAMT
                                        ,RECHKMAN
                                        ,RECHKDATE
                                        ,EXEDATE
                                        ,EXEMAN
                                        ,ACTTITLEAPCODE
                                        ,CPRNDATE
                                        ,RPTPAGE
                                        ,RPTROWS
                                        ,GENMEDDATE
                                        ,PROCUSER
                                        ,PROCDEPTID
                                        ,PROCIP
                                        ,UPDTIME
                                        ) values (
                                         v_dataCur4.APNO
                                        ,v_dataCur4.SEQNO
                                        ,v_dataCur4.ORIISSUYM
                                        ,v_dataCur4.BRISSUYM
                                        ,v_dataCur4.ISSUYM
                                        ,v_dataCur4.PAYYM
                                        ,v_dataCur4.TRANSACTIONID
                                        ,v_dataCur4.TRANSACTIONSEQ
                                        ,v_dataCur4.INSKD
                                        ,v_dataCur4.ISSUKIND
                                        ,v_dataCur4.MK
                                        ,v_dataCur4.BRMK
                                        ,v_dataCur4.AFMK
                                        ,v_dataCur4.WORKMK
                                        ,v_dataCur4.AFCHKDATE
                                        ,v_dataCur4.AFPAYDATE
                                        ,v_dataCur4.BLIACCOUNT
                                        ,v_dataCur4.BENTYP
                                        ,v_dataCur4.BENIDS
                                        ,v_dataCur4.BENIDN
                                        ,v_dataCur4.BENNAME
                                        ,v_dataCur4.ISSUTYP
                                        ,v_dataCur4.PAYTYP
                                        ,v_dataCur4.PAYBANKID
                                        ,v_dataCur4.BRANCHID
                                        ,v_dataCur4.PAYEEACC
                                        ,v_dataCur4.ACCIDN
                                        ,v_dataCur4.ACCNAME
                                        ,v_dataCur4.ACCSEQNO
                                        ,v_dataCur4.COMMZIP
                                        ,v_dataCur4.COMMADDR
                                        ,v_dataCur4.COMMTEL
                                        ,v_dataCur4.PAYAMT
                                        ,v_dataCur4.RECAMT
                                        ,v_g_procempno
                                        ,v_dataCur4.RECHKDATE
                                        ,v_dataCur4.EXEDATE
                                        ,v_g_procempno
                                        ,v_dataCur4.ACTTITLEAPCODE
                                        ,v_dataCur4.CPRNDATE
                                        ,v_dataCur4.RPTPAGE
                                        ,v_dataCur4.RPTROWS
                                        ,v_dataCur4.GENMEDDATE
                                        ,v_g_procempno
                                        ,v_g_procdeptid
                                        ,v_g_procip
                                        ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                       );
            end Loop;

            if v_g_rowCount>0 then
                dbms_output.put_line('  SP_BA_ProcSinglePR------------------------------>>產生改匯資料檔(改匯中)-[ '||v_g_rowCount||' ]');
            else
                dbms_output.put_line('  SP_BA_ProcSinglePR------------------------------>>產生改匯資料檔(改匯中)-[ NoData ]');
            end if;
        end;

        begin
            v_g_errMsg := '';
            v_g_rowCount := 0;

            for v_dataCur5 in c_bapflbac_1 Loop
                v_g_rowCount := v_g_rowCount + 1;

                --更新BAREGIVEDTLTMP(人工改匯暫存檔).PROCFLAG(處理註記)='1'
                begin
                    Update BAREGIVEDTLTMP
                       set PROCFLAG = '1'
                     where APNO = v_dataCur5.APNO
                       and PROCFLAG = '0';

                    dbms_output.put_line('  SP_BA_ProcGiveDtl------------------------------->>更新人工改匯暫存檔處理註記完成。寫入人工改匯暫存檔筆數: '||v_g_rowCount||' 筆。');

                exception
                    when others
                        then
                            rollback;
                    end;
                end loop;
        end;
    exception
        when others
            then
                rollback;
                v_g_errMsg := SQLErrm;
                begin
                    dbms_session.close_database_link('BLIDB');
                exception
                    when others
                        then
                            null;
                end;
                dbms_output.put_line('**Err:SP_BA_ProcSinglePR-------------------------->>'||v_g_errMsg);

end SP_BA_ProcSinglePR;
/

