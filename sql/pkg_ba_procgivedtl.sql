CREATE OR REPLACE Package BA.PKG_BA_ProcGiveDtl
authid definer is
    v_g_ProgName             varChar2(200);
    v_g_errMsg               varChar2(4000);
    v_g_rowCount             Number;
    v_g_procempno            varChar2(10);
    v_g_procdeptid           varChar2(5);
    v_g_procip               varChar2(50);
    v_g_procMsgCode          varChar2(2);
    v_g_procMsg              varChar2(4000);
    v_g_i                    Number;
    v_g_j                    Number;

    procedure sp_BA_chkReturnPayFile(
        v_i_babatchrecid     in       varChar2,
        v_i_paycode          in       varChar2,
        v_i_procempno        in       varChar2,
        v_o_procMsgCode      out      varChar2,
        v_o_procMsg          out      varChar2
    );

    procedure sp_BA_updPaidMarkBJ(
        v_i_babatchrecid     in       varChar2,
        v_i_paycode          in       varChar2,
        v_i_procempno        in       varChar2,
        v_i_procdeptid       in       varChar2,
        v_i_procip           in       varChar2,
        v_o_procMsgCode      out      varChar2,
        v_o_procMsg          out      varChar2
    );

End;
/

CREATE OR REPLACE Package Body BA.PKG_BA_ProcGiveDtl
is
    procedure sp_BA_chkReturnPayFile (
        v_i_babatchrecid     in       varChar2,
        v_i_paycode          in       varChar2,
        v_i_procempno        in       varChar2,
        v_o_procMsgCode      out      varChar2,
        v_o_procMsg          out      varChar2
    ) is
        v_filename               varChar2(50);
        v_tatyp                  varChar2(3);
        v_paydate                varChar2(8);
        v_apno                   varChar2(12);
        v_seq                    varChar2(4);
        v_issuym                 varChar2(6);
        v_payym                  varChar2(6);
        v_paytyp                 varChar2(2);
        v_stat                   varChar2(2);

        v_sunit                  varChar2(8);
        v_hbank                  varChar2(3);
        v_bbank                  varChar2(5);
        v_accno                  varChar2(15);
        v_amt                    varChar2(14);
        v_space                  varChar2(2);
        v_idn                    varChar2(10);
        v_name                   varChar2(50 char);
        v_emgmk                  varChar2(1);
        v_nc                     varChar2(4);
        v_rc                     varChar2(1);

        v_sunit_t                varChar2(8);
        v_hbank_t                varChar2(3);
        v_bbank_t                varChar2(5);
        v_accno_t                varChar2(15);
        v_amt_t                  varChar2(14);
        v_space_t                varChar2(2);
        v_idn_t                  varChar2(10);
        v_name_t                 varChar2(50 char);
        v_emgmk_t                varChar2(1);
        v_nc_t                   varChar2(4);
        v_rc_t                   varChar2(1);
        v_updflag                varChar2(1);
        v_mfilename              varChar2(100);

        v_flag_sunit             Number;
        v_flag_hbank             Number;
        v_flag_bbank             Number;
        v_flag_accno             Number;
        v_flag_amt               Number;
        v_flag_space             Number;
        v_flag_idn               Number;
        v_flag_name              Number;
        v_flag_emgmk             Number;
        v_flag_nc                Number;
        v_flag_rc                Number;
        v_dataCount              Number;
        v_statflag               Number;
        v_flagCount              Number := 11;
        v_updGivedtlRowCount     Number := 0;
        v_updGiveTmpRowCount     Number := 0;

        begin
            v_g_ProgName := 'PKG_BA_ProcGiveDtl.sp_BA_chkReturnPayFile';
            v_g_errMsg := '';
            v_g_rowCount := 0;
            v_g_procMsgCode := '0';
            v_g_procMsg := '';
            v_g_i := 1;
            v_statflag := 0;

            --若無傳入執行作業人員員工編號,則取出參數檔的設定
            if nvl(trim(v_i_procempno),' ')<>' ' then
                v_g_procempno := v_i_procempno;
            else
                v_g_procempno := PKG_BA_getPayData.fn_BA_getProcEmpInfo('EMPNO');
            end if;

            --若傳入的給付別不為 L、K、S 時，則不執行轉入作業，
            --並將批次作業記錄檔中的處理狀態回押回待"已入排程"，且回傳錯誤訊息至 AP 端顯示
            if nvl(trim(UPPER(v_i_paycode)),' ')='L' or nvl(trim(UPPER(v_i_paycode)),' ')='K' or nvl(trim(UPPER(v_i_paycode)),' ')='S' then
                --查詢該給付媒體批次轉入序號其狀態其處理狀態是否為"排程處理中"
                select Count(*) into v_g_rowCount
                  from BABATCHREC t1
                 where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                   and t1.PROCSTAT = '2'
                   and t1.BATCHTYP = '01';

                if v_g_rowCount>0 then
                    select t1.TATYP
                          ,fn_BA_transDateValue(t1.PAYDATE,'1')
                          ,t1.FILENAME
                          ,t1.DATACOUNT
                      into v_tatyp
                          ,v_paydate
                          ,v_filename
                          ,v_datacount
                      from BABATCHREC t1
                     where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                       and t1.PROCSTAT = '2'
                       and t1.BATCHTYP = '01';

                    loop
                        begin
                            v_g_rowCount := 0;
                            v_updflag := '0';
                            v_mfilename := '';
                            v_apno := '';
                            v_seq := '';
                            v_paytyp := '';
                            v_issuym := '';
                            v_payym := '';
                            v_stat := '';

                            v_flag_sunit := 0;
                            v_flag_hbank := 0;
                            v_flag_bbank := 0;
                            v_flag_accno := 0;
                            v_flag_amt := 0;
                            v_flag_space := 0;
                            v_flag_idn := 0;
                            v_flag_name := 0;
                            v_flag_emgmk := 0;
                            v_flag_nc := 0;
                            v_flag_rc := 0;

                            v_sunit := '';
                            v_hbank := '';
                            v_bbank := '';
                            v_accno := '';
                            v_amt := '';
                            v_space := '';
                            v_idn := '';
                            v_name := '';
                            v_emgmk := '';
                            v_nc := '';
                            v_rc := '';

                            v_sunit_t := '';
                            v_hbank_t := '';
                            v_bbank_t := '';
                            v_accno_t := '';
                            v_amt_t := '';
                            v_space_t := '';
                            v_idn_t := '';
                            v_name_t := '';
                            v_emgmk_t := '';
                            v_nc_t := '';
                            v_rc_t := '';

                            select Count(*) into v_g_rowCount
                              from BAGIVEDTL t1
                             where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                               and t1.SEQNO = to_Char(v_g_i)
                               and t1.TATYP2 = v_tatyp
                               and t1.PAYDATE2 = v_paydate
                               and t1.MFILENAME = v_filename
                               and t1.APNO2 like (UPPER(v_i_paycode)||'%');

                            if v_g_rowCount>0 then
                                --逐筆讀出給付媒體批次轉入序號下的媒體入帳明細資料,與給付媒體明細錄檔(轉出)資料比對
                                select t1.SUNIT2
                                      ,t1.RC2
                                      ,t1.APNO2
                                      ,t1.SEQ2
                                      ,t1.PAYTYP2
                                      ,t1.ISSUYM2
                                      ,t1.PAYYM2
                                      ,t1.HBANK2
                                      ,t1.BBANK2
                                      ,t1.ACCNO2
                                      ,t1.AMT2
                                      ,t1.SPACE2
                                      ,t1.IDN2
                                      ,convert(substr(RPAD(fn_BA_transCharValue(replace(t1.NAME2,' ','　'),'1'),30,'　'),1,15)||t1.INSKD2, 'ZHT16BIG5', 'UTF8')
                                      ,t1.EMGMK2
                                      ,t1.NC2
                                      ,t1.STAT2
                                  into v_sunit,v_rc,v_apno,v_seq,v_paytyp,v_issuym
                                      ,v_payym,v_hbank,v_bbank,v_accno,v_amt
                                      ,v_space,v_idn,v_name,v_emgmk,v_nc,v_stat
                                  from BAGIVEDTL t1
                                 where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                                   and t1.SEQNO = to_Char(v_g_i)
                                   and t1.TATYP2 = v_tatyp
                                   and t1.PAYDATE2 = v_paydate
                                   and t1.MFILENAME = v_filename
                                   and t1.APNO2 like (UPPER(v_i_paycode)||'%');

                                v_g_rowCount := 0;
                                select Count(*) into v_g_rowCount
                                  from BAPARAM t1
                                 where t1.PARAMGROUP = 'MFILERESTAT'
                                   and t1.PARAMCODE = v_stat;

                                if v_g_rowCount>0 then
                                    v_g_rowCount := 0;
                                    select Count(*) into v_g_rowCount
                                      from BAGIVETMPDTL t1
                                     where t1.APNO2 = v_apno
                                       and t1.SEQ2 = v_seq
                                       and t1.TATYP2 = v_tatyp
                                       and t1.PAYDATE2 = v_paydate
                                       and t1.ISSUYM2 = v_issuym
                                       and t1.PAYYM2 = v_payym
                                       and t1.PAYTYP2 = v_paytyp;

                                    if v_g_rowCount>0 then
                                        --讀取給付媒體明細錄檔(轉出)資料,與相對應的媒體入帳明細資料比對
                                        select t1.SUNIT2
                                              ,t1.RC2
                                              ,t1.HBANK2
                                              ,t1.BBANK2
                                              ,t1.ACCNO2
                                              ,t1.AMT2
                                              ,t1.SPACE2
                                              ,t1.IDN2
                                              ,t1.COMPARENAME
                                              ,t1.EMGMK2
                                              ,t1.NC2
                                              ,(t1.MFILENAME||t1.MFILEDATE||t1.SEQNO)
                                          into v_sunit_t,v_rc_t,v_hbank_t,v_bbank_t,v_accno_t
                                              ,v_amt_t,v_space_t,v_idn_t,v_name_t
                                              ,v_emgmk_t,v_nc_t,v_mfilename
                                          from BAGIVETMPDTL t1
                                         where t1.APNO2 = v_apno
                                           and t1.SEQ2 = v_seq
                                           and t1.TATYP2 = v_tatyp
                                           and t1.PAYDATE2 = v_paydate
                                           and t1.ISSUYM2 = v_issuym
                                           and t1.PAYYM2 = v_payym
                                           and t1.PAYTYP2 = v_paytyp;

                                        --比對"發件單位"
                                        if v_sunit = v_sunit_t then
                                            v_flag_sunit := 1;
                                        end if;

                                        --比對"總行代號"
                                        if v_tatyp <> 'BLA' then
                                            if v_hbank = v_hbank_t then
                                                v_flag_hbank := 1;
                                            end if;
                                        else
                                            v_flag_hbank := 1;
                                        end if;

                                        --比對"分行代號"
                                        if v_tatyp <> 'BLA' then
                                            if v_bbank = v_bbank_t then
                                                v_flag_bbank := 1;
                                            end if;
                                        else
                                            v_flag_bbank := 1;
                                        end if;

                                        --比對"入帳帳號"
                                        if v_tatyp <> 'BLA' then
                                            if v_accno = v_accno_t then
                                                v_flag_accno := 1;
                                            end if;
                                        else
                                            v_flag_accno := 1;
                                        end if;

                                        --比對"交易金額"
                                        if v_amt = v_amt_t then
                                            v_flag_amt := 1;
                                        end if;

                                        --比對"空白"
                                        if v_space = v_space_t then
                                            v_flag_space := 1;
                                        end if;

                                        --比對"身分證號"
                                        if v_idn = v_idn_t then
                                            v_flag_idn := 1;
                                        end if;

                                        --比對"受款人姓名(Big5)"
                                        if v_name = v_name_t then
                                            v_flag_name := 1;
                                        end if;

                                        --比對"緊急專案註記"
                                        if v_emgmk = v_emgmk_t then
                                            v_flag_emgmk := 1;
                                        end if;

                                        --比對"備註代碼"
                                        if v_nc = v_nc_t then
                                            v_flag_nc := 1;
                                        end if;

                                        --比對"區別碼"
                                        if v_rc = v_rc_t then
                                            v_flag_rc := 1;
                                        end if;

                                        /*dbms_output.put_line('v_flag_sunit:'||v_flag_name||'-v_flag_hbank:'||v_flag_hbank
                                                          ||'-v_flag_bbank:'||v_flag_bbank||'-v_flag_accno:'||v_flag_accno
                                                          ||'-v_flag_amt:'||v_flag_amt||'-v_flag_space:'||v_flag_space
                                                          ||'-v_flag_idn:'||v_flag_idn||'-v_flag_name:'||v_flag_name
                                                          ||'-v_flag_emgmk:'||v_flag_emgmk||'-v_flag_nc:'||v_flag_nc
                                                          ||'-v_flag_rc:'||v_flag_rc
                                        );*/

                                        --需比對的資料欄位總計值=v_flagCount,故其比對結果的flag總和需=v_flagCount
                                        if (v_flag_sunit+v_flag_hbank+v_flag_bbank+v_flag_accno+v_flag_amt+v_flag_space+v_flag_idn+v_flag_name+v_flag_emgmk+v_flag_nc+v_flag_rc)=v_flagCount then
                                            v_updflag := '1';
                                        else
                                            v_updflag := '2';
                                            v_mfilename := '';
                                            v_statflag := 1;
                                        end if;
                                    else
                                        v_updflag := '2';
                                        v_statflag := 1;
                                    end if;
                                else
                                    v_updflag := '3';
                                    v_statflag := 1;
                                end if;
                            end if;

                            --給付媒體明細錄檔(轉出)資料,與相對應的媒體入帳明細資料相等時,則更新媒體入帳明細檔
                            if v_updflag<>'3' then
                                update BAGIVEDTL t1 set t1.COMPAREMK = v_updflag
                                                       ,t1.SOURCEFILESEQ = v_mfilename
                                                       ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                                   and t1.SEQNO = to_Char(v_g_i)
                                   and t1.TATYP2 = v_tatyp
                                   and t1.PAYDATE2 = v_paydate
                                   and t1.MFILENAME = v_filename
                                   and t1.APNO2 = v_apno
                                   and t1.SEQ2 = v_seq
                                   and t1.PAYTYP2 = v_paytyp
                                   and t1.PAYYM2 = v_payym
                                   and t1.ISSUYM2 = v_issuym;

                                if SQL%ROWCOUNT<>1 then
                                    rollback;
                                    v_statflag := 2;
                                    update BABATCHREC t1 set t1.PROCSTAT = '6'
                                                            ,t1.PROCFLAG = '3'
                                                            ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                            ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                     where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                                       and t1.PROCSTAT = '2'
                                       and t1.BATCHTYP = '01';
                                    commit;

                                    v_g_procMsgCode := '1';
                                    v_g_procMsg := 'W0060 資料轉入有誤，請確認後重新執行。原因：無法更新給付入帳媒體明細檔(BAGIVEDTL)，受理編號：'||v_apno;
                                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>無法更新給付入帳媒體明細檔(BAGIVEDTL)，受理編號：'||v_apno);
                                    exit;
                                else
                                    v_updGivedtlRowCount := v_updGivedtlRowCount+SQL%ROWCOUNT;

                                    update BAGIVETMPDTL t1 set t1.MFILEREMK = v_updflag
                                                              ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                     where t1.APNO2 = v_apno
                                       and t1.SEQ2 = v_seq
                                       and t1.TATYP2 = v_tatyp
                                       and t1.PAYDATE2 = v_paydate
                                       and t1.ISSUYM2 = v_issuym
                                       and t1.PAYYM2 = v_payym
                                       and t1.PAYTYP2 = v_paytyp;

                                    v_updGiveTmpRowCount := v_updGiveTmpRowCount+SQL%ROWCOUNT;
                                end if;
                            else
                                rollback;
                                exit;
                            end if;
                        exception
                            when others
                                then
                                    rollback;
                                    v_statflag := 2;
                                    update BABATCHREC t1 set t1.PROCSTAT = '6'
                                                            ,t1.PROCFLAG = '3'
                                                            ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                            ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                     where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                                       and t1.PROCSTAT = '2'
                                       and t1.BATCHTYP = '01';
                                    commit;

                                    v_g_procMsgCode := '1';
                                    v_g_procMsg := 'W0060 資料轉入有誤，請確認後重新執行。原因：'||SQLErrm;
                                    v_g_errMsg := SQLErrm;
                                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                                    exit;
                        end;

                        v_g_i := v_g_i + 1;
                        exit when v_g_i>v_datacount;
                    end loop;
                    v_g_i := v_g_i-1;

                    if v_g_i>0 then
                        --更新批次作業記錄檔
                        if v_statflag = 0 or v_statflag = 1 then
                            if v_statflag = 0 then
                                update BABATCHREC t1 set t1.PROCSTAT = '3'
                                                        ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                        ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                                   and t1.PROCSTAT = '2'
                                   and t1.BATCHTYP = '01';
                            else
                                update BABATCHREC t1 set t1.PROCSTAT = '6'
                                                        ,t1.PROCFLAG = '3'
                                                        ,t1.PROCUSER = v_g_procempno
                                                        ,t1.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                        ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                        ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                                   and t1.PROCSTAT = '2'
                                   and t1.BATCHTYP = '01';
                            end if;

                            commit;
                            v_g_procMsg := 'G0004 資料轉入完成。檢核筆數：'||v_g_i||' 筆、更新入帳檔比對註記筆數：'||v_updGivedtlRowCount
                                         ||' 筆、更新出帳檔比對註記筆數：'||v_updGiveTmpRowCount||' 筆。';
                                         --||'(批次序號：'||v_i_babatchrecid||'、給付別：'||v_i_paycode||'、'||'作業人員：'||v_i_procempno||')';

                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>給付媒體轉入檢核完成。檢核筆數：'||v_g_i||' 筆、更新入帳檔比對註記筆數：'||v_updGivedtlRowCount
                                                    ||' 筆、更新出帳檔比對註記筆數：'||v_updGiveTmpRowCount||' 筆。(批次序號：'||v_i_babatchrecid||'、給付別：'||v_i_paycode||'、'||'作業人員：'||v_i_procempno||')');
                        end if;
                    else
                        if v_statflag <> 2 then
                            rollback;

                            update BABATCHREC t1 set t1.PROCSTAT = '6'
                                                    ,t1.PROCFLAG = '3'
                                                    ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                    ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                             where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                               and t1.PROCSTAT = '2'
                               and t1.BATCHTYP = '01';
                            commit;

                            v_g_procMsgCode := '1';
                            v_g_procMsg := 'W0060 資料轉入有誤，請確認後重新執行。原因：查無待檢核的給付媒體資料。';
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>查無待檢核的給付媒體資料。');
                        end if;
                    end if;
                else
                    if v_statflag <> 2 then
                        rollback;

                        update BABATCHREC t1 set t1.PROCSTAT = '6'
                                                ,t1.PROCFLAG = '3'
                                                ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                         where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                           and t1.PROCSTAT = '2'
                           and t1.BATCHTYP = '01';
                        commit;

                        v_g_procMsgCode := '1';
                        v_g_procMsg := 'W0060 資料轉入有誤，請確認後重新執行。原因：查無待處理的批次記錄檔。';
                        dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>查無待處理的批次記錄檔。');
                    end if;
                end if;
            else
                rollback;

                update BABATCHREC t1 set t1.PROCSTAT = '6'
                                        ,t1.PROCFLAG = '3'
                                        ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                        ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                 where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                   and t1.PROCSTAT = '2'
                   and t1.BATCHTYP = '01';
                commit;

                v_g_procMsgCode := '1';
                v_g_procMsg := 'W0060 資料轉入有誤，請確認後重新執行。原因：給付別傳入錯誤('||nvl(trim(UPPER(v_i_paycode)),' ')||')，請重新檢核傳入值。';
                dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>給付別傳入錯誤('||nvl(trim(UPPER(v_i_paycode)),' ')||')');
            end if;
            v_o_procMsgCode := v_g_procMsgCode;
            v_o_procMsg := v_g_procMsg;
        exception
            when others
                then
                    rollback;

                    update BABATCHREC t1 set t1.PROCSTAT = '6'
                                            ,t1.PROCFLAG = '3'
                                            ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                            ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                       and t1.PROCSTAT = '2'
                       and t1.BATCHTYP = '01';
                    commit;

                    v_g_procMsgCode := '1';
                    v_g_procMsg := 'W0060 資料轉入有誤，請確認後重新執行。原因：'||SQLErrm;
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    v_o_procMsgCode := v_g_procMsgCode;
                    v_o_procMsg := v_g_procMsg;
        end;
    --procedure sp_BA_chkReturnPayFile End

    procedure sp_BA_updPaidMarkBJ(
        v_i_babatchrecid     in       varChar2,
        v_i_paycode          in       varChar2,
        v_i_procempno        in       varChar2,
        v_i_procdeptid       in       varChar2,
        v_i_procip           in       varChar2,
        v_o_procMsgCode      out      varChar2,
        v_o_procMsg          out      varChar2
    ) is
        v_paydate            varChar2(8);
        v_paycode            varChar2(1);
        v_tatyp              varChar2(3);
        v_stexpndrecmk       varChar2(1);

        --媒體回押產生的退匯資料
        Cursor c_dataCur_1 is
            select distinct
                   nvl(trim(t1.APNO2),' ') as APNO                                      --受理編號
                  ,nvl(trim(t1.SEQ2),' ') as SEQNO                                      --受款人序
                  ,fn_BA_transDateValue(nvl(trim(t1.PAYDATE2),' '),'3') as PAYDATE      --核付日期
                  ,to_Char(SysDate,'YYYYMM') as BRISSUYM                                --退匯核定年月
                  ,nvl(trim(t2.ORIISSUYM),t2.ISSUYM) as ORIISSUYM                       --原始核定年月
                  ,fn_BA_transDateValue(nvl(trim(t1.ISSUYM2),' '),'4') as ISSUYM        --核定年月
                  ,fn_BA_transDateValue(nvl(trim(t1.PAYYM2),' '),'4') as PAYYM          --給付年月
                  ,nvl(trim(t1.INSKD2),' ') as INSKD                                    --保險別
                  ,nvl(trim(t1.PAYTYP2),' ') as PAYKIND                                 --給付種類
                  ,deCode(t1.STAT2,'00','0'
                                  ,'04',deCode((select count(*)
                                                  from BAPARAM
                                                 where PARAMGROUP = 'REALSTEXPND'
                                                   and PARAMCODE = t4.STEXPNDREASON),0,'3','1')
                                  ,'3') as BRMK                                         --退改匯別
                  ,deCode(t1.STAT2,'04',deCode((select count(*)
                                                  from BAPARAM
                                                 where PARAMGROUP = 'REALSTEXPND'
                                                   and PARAMCODE = t4.STEXPNDREASON),0,'99','04')
                                  ,t1.STAT2) as BRNOTE                                  --退匯原因
                  --,nvl(trim(t1.STAT2),' ') as BRNOTE                                    --退匯原因
                  ,fn_BA_transDateValue(t1.RPAYDATE2,'3') as BRCHKDATE                  --退匯初核日期
                  ,t3.BENIDS as BENIDS                                                  --受益人社福識別碼
                  ,t3.BENIDNNO as BENIDN                                                --受款人身分證號
                  ,t3.BENNAME as BENNAME                                                --受款人姓名
                  ,t2.PAYTYP as PAYTYP                                                  --核發方式/給付方式
                  ,deCode(t2.PAYTYP,'A',substr(nvl(trim(t1.RPAYDATE2),' '),1,3)
                                   ,substr(nvl(trim(t1.HBANK2),' '),1,3)) as PAYBANKID  --轉帳帳號(總行)
                  ,deCode(t2.PAYTYP,'A',substr(nvl(trim(t1.RPAYDATE2),' '),4,4)
                      ,deCode( substr(nvl(trim(t1.HBANK2),' '),1,3),'700',substr(nvl(trim(t1.BBANK2),' '),1,4) ,deCode(t2.PAYTYP ,'1','0000', substr(nvl(trim(t1.BBANK2),' '),1,4))
                         )) as BRANCHID                  --轉帳帳號(分行)
                  ,substr(nvl(trim(t1.ACCNO2),' '),1,21) as PAYEEACC                    --轉帳帳號(帳號)
                  ,nvl(trim(t1.IDN2),' ') as ACCIDN                                     --戶名
                  ,nvl(trim(t1.NAME2),' ') as ACCNAME                                   --戶名IDN
                  ,t3.ACCSEQNO as ACCSEQNO                                              --被共同具領之受款人員序號
                  ,t3.COMMZIP as COMMZIP                                                --郵遞區號
                  ,t3.COMMADDR as COMMADDR                                              --地址
                  ,t3.COMMADDR as BRADDR                                                --地址(轉出納用)
                  ,t3.TEL1 as COMMTEL                                                   --電話
                  ,to_Number(nvl(trim(t1.AMT2),'0')) as REMITAMT                        --退匯金額
                  ,deCode(t1.STAT2,'04',deCode((select count(*)
                                                  from BAPARAM
                                                 where PARAMGROUP = 'REALSTEXPND'
                                                   and PARAMCODE = t4.STEXPNDREASON),0,'','3')
                                  ,'') as STEXPNDMK                                     --止付註記
                  ,deCode(t1.STAT2,'00','1'
                                  ,'04',deCode((select count(*)
                                                  from BAPARAM
                                                 where PARAMGROUP = 'REALSTEXPND'
                                                   and PARAMCODE = t4.STEXPNDREASON),0,'2','4')
                                  ,'2') as REMITMK                                      --帳務後續處理註記
                  ,fn_BA_transDateValue(t1.RPAYDATE2,'3') as REMITDATE                  --帳務後續處理日期
                  ,deCode(t3.BENEVTREL,'1','1','2','1','3','1'
                                      ,'4','1','5','1','6','1'
                                      ,'7','1','2') as BENTYP                           --受款人種類
                  ,deCode(v_i_paycode,'L','',t4.NLWKMK) as NLWKMK                       --普職註記
                  ,deCode(v_i_paycode,'L','',t4.ADWKMK) as ADWKMK                       --加職註記
                  ,t4.GENMEDDATE                                                        -- 產出媒體日期 2018/12/24 Modify By ChugnYu
                                                                                        --因應死亡改匯，新增繼承人未回押和後續處理註記
              from BAGIVEDTL t1,BAPAY t2,BAAPPBASE t3,BADAPR t4
             where t1.BABATCHRECID = v_i_babatchrecid
               and t1.APNO2 = t2.APNO
               and t1.APNO2 = t3.APNO
               and t1.APNO2 = t4.APNO
               and t1.SEQ2 = t2.ACCSEQNO
               and t2.SEQNO = t3.SEQNO
               and t2.SEQNO = t4.SEQNO
               and t2.PAYTYP = t4.PAYTYP

               -- Modify By ChugnYu 2018/06/08 修改死亡改匯給付媒體無法回押條件

               and ((fn_BA_transDateValue(t1.ISSUYM2,'4') = t2.ISSUYM) or
                    (fn_BA_transDateValue(t1.ISSUYM2,'4') = t2.ORIISSUYM
                     And t1.APNO2 in (Select t5.APNO From BAREGIVEDTL t5
                                       Where t5.APNO = t1.APNO2
                                         And t5.REPAYSEQNO is not Null)))

               -- Modify By ChugnYu 2018/06/08 修改死亡改匯給付媒體無法回押條件

               and fn_BA_transDateValue(t1.PAYYM2,'4') = t2.PAYYM
               and fn_BA_transDateValue(t1.ISSUYM2,'4') = t4.ISSUYM
               and fn_BA_transDateValue(t1.PAYYM2,'4') = t4.PAYYM
               and t4.MTESTMK = 'F'
               and t1.COMPAREMK = '1'
               and t3.ACCREL = '1'
               and t1.APNO2 like (UPPER(v_i_paycode)||'%')
               and t2.APNO like (UPPER(v_i_paycode)||'%')
               and t3.APNO like (UPPER(v_i_paycode)||'%')
               and t4.APNO like (UPPER(v_i_paycode)||'%')
             order by APNO,SEQNO,ISSUYM,PAYYM;

        --待轉入給付系統的退改匯資料
        Cursor c_dataCur_2(v_paycode varChar2,v_paydate varChar2) is
            select distinct
                   t1.APNO as APNO                                                                  --受理編號
                  ,t1.GVSEQ as SEQNO                                                                --受款人序
                  ,t3.ORIISSUYM                                                                     --原核定年月
                  ,t3.BRISSUYM as BRISSUYM                                                          --退匯核定年月
                  ,t1.PAYYM as PAYYM                                                                --給付年月
                  ,t1.INSKD as INSKD                                                                --保險別
                  ,t1.ACCEPT_ISSUE_CD as ISSUKIND                                                   --核發種類/給付種類
                  ,t1.TRANSACTIONID as TRANSACTIONID                                                --交易編號
                  ,nvl(trim((select to_Char(Max(tt2.SEQNO))
                               from PFLBAC tt1,PFLBACEVENT tt2
                              where tt1.TRANSACTIONID = tt2.TRANSACTIONID
                                and tt1.TRANSACTIONID = t1.TRANSACTIONID
                                and t3.APNO = tt1.APNO
                                and t3.ORIISSUYM = tt1.ISSUYM
                                and t3.PAYYM = tt1.PAYYM
                                and t3.ISSUKIND = tt1.ACCEPT_ISSUE_CD
                                and tt1.INSKD = tt2.INSKD
                                and tt1.MK = tt2.MK
                                and tt1.INSKD = '1'
                                and tt1.BNMK = '3'
                                and ((tt2.MK = '1' and nvl(trim(tt2.PAYDTE),' ')<>' ' and trim(tt2.PAYDTE) is not null)
                                  or (tt2.MK = '2' and (nvl(trim(tt2.PAYDTE),' ')=' ' or trim(tt2.PAYDTE) is null)))
                            )),'1') as TRANSACTIONSEQ                                               --交易編號的序號
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
                  ,deCode(v_i_paycode,'L','',t3.NLWKMK) as NLWKMK                                   --普職註記
                  ,deCode(v_i_paycode,'L','',t3.ADWKMK) as ADWKMK                                   --加職註記
              from PFLBAC t1,PFLBACEVENT t2,BAPFLBAC t3
             where t1.TRANSACTIONID = t2.TRANSACTIONID
               and t1.SEQNO = t2.SEQNO
               and t1.INSKD = t2.INSKD
               and t3.APNO = t1.APNO
               and t3.SEQNO = t1.GVSEQ
               and t1.APNO like (v_paycode||'%')
               and t1.ISSUYM = t3.ISSUYM
               and t1.PAYYM = t3.PAYYM
               and t3.ISSUKIND = t1.ACCEPT_ISSUE_CD
               and t3.PAYDATE = v_paydate
               and t1.MK = t2.MK
               and t1.INSKD = '1'
               and t1.BNMK = '3'
               and ((t2.MK = '1' and nvl(trim(t2.PAYDTE),' ')<>' ' and trim(t2.PAYDTE) is not null)
                 or (t2.MK = '2' and (nvl(trim(t2.PAYDTE),' ')=' ' or trim(t2.PAYDTE) is null)))
               order by APNO,ORIISSUYM,PAYYM,GVSEQ;

        --待產生退匯核付報表總表紀錄檔及核付報表總表會計科目紀錄檔的資料
        --add by 20121105 Angela 新版核定清單檔程式異動
        Cursor c_dataCur_3(v_issuym varChar2,v_paycode varChar2,v_paydate varChar2) is
            select distinct
                   t1.ORIISSUYM as ISSUYM                                                   --(原)核定年月
                  ,t1.BRCHKDATE as CHKDATE                                                  --核定日期
              from BAPFLBAC t1
             where t1.BRISSUYM = v_issuym
               and t1.APNO like (v_paycode||'%')
               and t1.PAYDATE = v_paydate
               and t1.REMITAMT > 0
               and (t1.CPRNDATE is not null and nvl(trim(t1.CPRNDATE),' ')<>' ');

        --查詢給付媒體檔中的給付別資料
        Cursor c_PayCodeCur is
            select distinct substr(t2.APNO2,1,1) as PAYCODE
              from BABATCHREC t1,BAGIVEDTL t2
             where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
               and t1.BABATCHRECID = t2.BABATCHRECID;

        begin
            v_g_ProgName := 'PKG_BA_ProcGiveDtl.sp_BA_updPaidMarkBJ';
            v_g_procMsgCode := '0';
            v_g_procMsg := '';
            v_g_rowCount := 0;
            v_g_i := 0;
            v_g_j := 0;
            v_paycode := '';
            v_paydate := '';
            v_tatyp := '';
            v_stexpndrecmk := '';

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

            --若無傳入執行作業人員IP,則取出參數檔的設定
            if nvl(trim(v_i_procip),' ')<>' ' then
                v_g_procip := v_i_procip;
            else
                v_g_procip := PKG_BA_getPayData.fn_BA_getProcEmpInfo('IP');
            end if;

            --若傳入的給付別不為 L、K、S 時，則不執行轉入作業，
            --並將批次作業記錄檔中的處理狀態回押回"媒體資料已轉入"，且回傳錯誤訊息至 AP 端顯示
            if nvl(trim(UPPER(v_i_paycode)),' ')='L' or nvl(trim(UPPER(v_i_paycode)),' ')='K' or nvl(trim(UPPER(v_i_paycode)),' ')='S' then
                --查詢該給付媒體批次轉入序號其狀態其處理狀態是否為"媒體資料回押中"
                select Count(*) into v_g_rowCount
                  from BABATCHREC t1
                 where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                   and t1.PROCSTAT = '4'
                   and t1.BATCHTYP = '01';

                if v_g_rowCount>0 then
                    select t1.PAYDATE
                          ,t1.TATYP
                      into v_paydate
                          ,v_tatyp
                      from BABATCHREC t1
                     where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                       and t1.PROCSTAT = '4'
                       and t1.BATCHTYP = '01';

                    begin
                        --逐筆回押媒體入帳明細檔的資料回給付核定檔
                        for v_dataCur_1 in c_dataCur_1 Loop
                            v_g_i := v_g_i + 1;
                            v_stexpndrecmk := '';

                            --更新給付核定檔相關的核付資料(給付方式='1'、'2')
                            -- 2018/12/24 Modify By ChugnYu 因應死亡改匯，新增繼承人未回押和後續處理註記，增加判斷產出媒體日期為空條件
                            If (Trim(v_dataCur_1.GENMEDDATE) Is Null) And (v_dataCur_1.SEQNO <> '0000') Then
                               update BADAPR t1 set t1.REMITMK = v_dataCur_1.REMITMK
                                                   ,t1.REMITDATE = v_dataCur_1.REMITDATE
                                                   ,t1.STEXPNDMK = deCode(v_dataCur_1.STEXPNDMK,'',t1.STEXPNDMK,null,t1.STEXPNDMK,v_dataCur_1.STEXPNDMK)
                                                   ,t1.GENMEDDATE = v_dataCur_1.PAYDATE
                                where t1.APNO = v_dataCur_1.APNO
                                  and t1.SEQNO = v_dataCur_1.SEQNO
                                  and t1.ISSUYM = v_dataCur_1.ISSUYM
                                  and t1.PAYYM = v_dataCur_1.PAYYM
                                  and t1.PAYKIND = v_dataCur_1.PAYKIND
                                  and t1.MTESTMK = 'F'
                                  and t1.MANCHKMK = 'Y'
                                  --and (t1.ACCEPTMK = 'Y' or nvl(trim(t1.ACCEPTMK),' ') = ' ')    -Mark by Angela 20190927
                                  and (t1.CHKDATE is not null and nvl(trim(t1.CHKDATE),' ')<>' ')
                                  and Trim(t1.GENMEDDATE) is null
                                  and t1.PAYTYP in ('1','2');
                            else
                               update BADAPR t1 set t1.REMITMK = v_dataCur_1.REMITMK
                                                   ,t1.REMITDATE = v_dataCur_1.REMITDATE
                                                   ,t1.STEXPNDMK = deCode(v_dataCur_1.STEXPNDMK,'',t1.STEXPNDMK,null,t1.STEXPNDMK,v_dataCur_1.STEXPNDMK)
                                where t1.APNO = v_dataCur_1.APNO
                                  and t1.SEQNO = v_dataCur_1.SEQNO
                                  and t1.ISSUYM = v_dataCur_1.ISSUYM
                                  and t1.PAYYM = v_dataCur_1.PAYYM
                                  and t1.PAYKIND = v_dataCur_1.PAYKIND
                                  and t1.MTESTMK = 'F'
                                  and t1.MANCHKMK = 'Y'
                                  --and (t1.ACCEPTMK = 'Y' or nvl(trim(t1.ACCEPTMK),' ') = ' ')    -Mark by Angela 20190927
                                  and (t1.CHKDATE is not null and nvl(trim(t1.CHKDATE),' ')<>' ')
                                  and (t1.GENMEDDATE is not null and nvl(trim(t1.GENMEDDATE),' ')<>' ')
                                  and t1.PAYTYP in ('1','2');
                            end if;
                            -- 2018/12/24 Modify By ChugnYu 因應死亡改匯，新增繼承人未回押和後續處理註記，增加判斷產出媒體日期為空條件

                            --更新給付核定檔相關的核付資料(給付方式='A')
                            update BADAPR t1 set t1.PAYBANKID = v_dataCur_1.PAYBANKID
                                                ,t1.BRANCHID = v_dataCur_1.BRANCHID
                                                ,t1.PAYEEACC = v_dataCur_1.PAYEEACC
                                                ,t1.REMITMK = v_dataCur_1.REMITMK
                                                ,t1.REMITDATE = v_dataCur_1.REMITDATE
                             where t1.APNO = v_dataCur_1.APNO
                               and t1.SEQNO = v_dataCur_1.SEQNO
                               and t1.ISSUYM = v_dataCur_1.ISSUYM
                               and t1.ISSUYM = v_dataCur_1.ORIISSUYM
                               and t1.PAYYM = v_dataCur_1.PAYYM
                               and t1.PAYKIND = v_dataCur_1.PAYKIND
                               and t1.MTESTMK = 'F'
                               and t1.MANCHKMK = 'Y'
                               --and (t1.ACCEPTMK = 'Y' or nvl(trim(t1.ACCEPTMK),' ') = ' ')    -Mark by Angela 20190927
                               and (t1.CHKDATE is not null and nvl(trim(t1.CHKDATE),' ')<>' ')
                               and (t1.GENMEDDATE is not null and nvl(trim(t1.GENMEDDATE),' ')<>' ')
                               and t1.PAYTYP = 'A';

                            --當給付媒體檔回覆的處理狀況非"00"時,則需將資料寫入退匯資料檔
                            if v_tatyp='BL1' then
                                if v_dataCur_1.BRMK = '1' or v_dataCur_1.BRMK = '3' then
                                    v_g_j := v_g_j+1;

                                    if v_dataCur_1.BRNOTE = '04' and v_dataCur_1.BRMK = '1' then
                                        v_stexpndrecmk := 'N';
                                    else
                                        v_stexpndrecmk := '';
                                    end if;

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
                                                         ,STEXPNDRECMK
                                                         ,NLWKMK
                                                         ,ADWKMK
                                                         ,PROCUSER
                                                         ,PROCDEPTID
                                                         ,PROCIP
                                                         ,UPDTIME
                                                         ) values (
                                                          trim(v_dataCur_1.APNO)
                                                         ,trim(v_dataCur_1.SEQNO)
                                                         ,trim(v_dataCur_1.PAYDATE)
                                                         ,trim(v_dataCur_1.BRISSUYM)
                                                         ,trim(v_dataCur_1.ORIISSUYM)
                                                         ,trim(v_dataCur_1.ISSUYM)
                                                         ,trim(v_dataCur_1.PAYYM)
                                                         ,trim(v_dataCur_1.INSKD)
                                                         ,trim(v_dataCur_1.PAYKIND)
                                                         ,trim(v_dataCur_1.BRCHKDATE)
                                                         ,trim(v_dataCur_1.BRMK)
                                                         ,trim(v_dataCur_1.BRMK)
                                                         ,trim(v_dataCur_1.BRNOTE)
                                                         ,'0053'
                                                         ,trim(v_dataCur_1.BENTYP)
                                                         ,trim(v_dataCur_1.BENIDS)
                                                         ,trim(v_dataCur_1.BENIDN)
                                                         ,trim(v_dataCur_1.BENNAME)
                                                         ,'B'
                                                         ,trim(v_dataCur_1.PAYTYP)
                                                         ,trim(v_dataCur_1.PAYBANKID)
                                                         ,trim(v_dataCur_1.BRANCHID)
                                                         ,trim(v_dataCur_1.PAYEEACC)
                                                         ,trim(v_dataCur_1.ACCIDN)
                                                         ,trim(v_dataCur_1.ACCNAME)
                                                         ,trim(v_dataCur_1.ACCSEQNO)
                                                         ,trim(v_dataCur_1.COMMZIP)
                                                         ,trim(v_dataCur_1.COMMADDR)
                                                         ,trim(v_dataCur_1.BRADDR)
                                                         ,trim(v_dataCur_1.COMMTEL)
                                                         ,trim(v_dataCur_1.REMITAMT)
                                                         ,'KKB'
                                                         ,to_Char(Sysdate,'YYYYMMDD')
                                                         ,v_g_procempno
                                                         ,to_Char(Sysdate,'YYYYMMDD')
                                                         ,v_g_procempno
                                                         ,to_Char(Sysdate,'YYYYMMDD')
                                                         ,to_Char(Sysdate,'YYYYMMDD')
                                                         ,v_stexpndrecmk
                                                         ,trim(v_dataCur_1.NLWKMK)
                                                         ,trim(v_dataCur_1.ADWKMK)
                                                         ,v_g_procempno
                                                         ,v_g_procdeptid
                                                         ,v_g_procip
                                                         ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                    );
                                end if;
                            end if;
                        end Loop;

                        --Call 退改匯資料與出納系統介接程式,將退匯資料寫入出納系統
                        if v_tatyp='BL1' then
                            for v_PayCodeCur in c_PayCodeCur Loop
                                v_paycode := v_PayCodeCur.PAYCODE;

                                PKG_BA_genPayRPT.sp_BA_genPayRPT_5(to_Char(SysDate,'YYYYMM'),v_paycode,v_paydate,to_Char(SysDate,'YYYYMMDD'),'');                                       --退匯清冊(退匯核定清單)報表資料

                                for v_dataCur_3 in c_dataCur_3(to_Char(SysDate,'YYYYMM'),v_paycode,v_paydate) Loop
                                     PKG_BA_genPayRPTSUMAccount.sp_BA_genPaySUMRPT_5(v_dataCur_3.issuym,v_paycode,v_dataCur_3.chkdate,to_Char(SysDate,'YYYYMMDD'),'');                   --退匯核定清單資料
                                     if v_paycode = 'L' then
                                         PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_5_L(v_dataCur_3.issuym,v_paycode,v_dataCur_3.chkdate,to_Char(SysDate,'YYYYMMDD'),'');         --退匯核定清單會計科目資料
                                         PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_5_L(v_dataCur_3.issuym,to_Char(SysDate,'YYYYMM'),v_dataCur_3.chkdate,v_paydate,to_Char(SysDate,'YYYYMMDD'),'');
                                     elsif v_paycode = 'K' then
                                         PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_5_K(v_dataCur_3.issuym,v_paycode,v_dataCur_3.chkdate,to_Char(SysDate,'YYYYMMDD'),'');         --退匯核定清單會計科目資料
                                         PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_5_K(v_dataCur_3.issuym,to_Char(SysDate,'YYYYMM'),v_dataCur_3.chkdate,v_paydate,to_Char(SysDate,'YYYYMMDD'),'');
                                     elsif v_paycode = 'S' then
                                         PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_5_S(v_dataCur_3.issuym,v_paycode,v_dataCur_3.chkdate,to_Char(SysDate,'YYYYMMDD'),'');         --退匯核定清單會計科目資料
                                         PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_5_S(v_dataCur_3.issuym,to_Char(SysDate,'YYYYMM'),v_dataCur_3.chkdate,v_paydate,to_Char(SysDate,'YYYYMMDD'),'');
                                     end if;
                                end Loop;

                                PKG_BA_ProcCashier.sp_BA_expRefundment(to_Char(SysDate,'YYYYMM'),v_paycode,v_paydate,v_g_procMsgCode,v_g_procMsg);

                                if v_g_procMsgCode = '0' then
                                    --將已寫入出納系統的退改匯資料轉回給付系統
                                    for v_dataCur_2 in c_dataCur_2(v_paycode,v_paydate) Loop
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
                                                                ,NLWKMK
                                                                ,ADWKMK
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
                                                                ,v_dataCur_2.BENIDN
                                                                ,v_dataCur_2.BENNAME
                                                                ,v_dataCur_2.ACCSEQNO
                                                                ,v_dataCur_2.ISSUTYP
                                                                ,v_dataCur_2.PAYTYP
                                                                ,v_dataCur_2.PAYBANKID
                                                                ,v_dataCur_2.BRANCHID
                                                                ,v_dataCur_2.PAYEEACC
                                                                ,v_dataCur_2.COMMZIP
                                                                ,v_dataCur_2.COMMADDR
                                                                ,v_dataCur_2.COMMTEL
                                                                ,v_dataCur_2.REMITAMT
                                                                ,v_dataCur_2.NLWKMK
                                                                ,v_dataCur_2.ADWKMK
                                                                ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                        );
                                    end Loop;
                                else
                                    exit;
                                end if;
                            end Loop;
                        end if;

                        if v_g_i>0 then
                            if v_g_procMsgCode = '0' then
                                v_g_procMsg := 'G1007 回押作業完成。回押資料總筆數：'||v_g_i||' 筆；寫入退匯資料檔筆數：'||v_g_j||' 筆。';
                                dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>給付媒體回押作業完成。回押資料總筆數：'||v_g_i||' 筆；寫入退匯資料檔筆數：'||v_g_j||' 筆。');

                                --更新批次作業記錄檔
                                update BABATCHREC t1 set t1.PROCSTAT = '5'
                                                        ,t1.PROCFLAG = '1'
                                                        ,t1.PROCUSER = v_g_procempno
                                                        ,t1.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                        ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                                   and t1.PROCSTAT = '4'
                                   and t1.BATCHTYP = '01';

                                commit;
                            else
                                rollback;

                                --更新批次作業記錄檔
                                update BABATCHREC t1 set t1.PROCSTAT = '7'
                                                        ,t1.PROCFLAG = '4'
                                                        ,t1.PROCUSER = v_g_procempno
                                                        ,t1.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                        ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                                   and t1.PROCSTAT = '4'
                                   and t1.BATCHTYP = '01';

                                commit;
                                v_g_procMsgCode := '1';
                                v_g_procMsg := 'W1005 資料回押有誤，請確認後重新執行。原因：'||v_g_procMsg;
                                dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>給付媒體回押作業失敗：'||v_g_procMsg);
                            end if;
                        else
                            rollback;

                            update BABATCHREC t1 set t1.PROCSTAT = '3'
                                                    ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                    ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                             where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                               and t1.PROCSTAT = '4'
                               and t1.BATCHTYP = '01';
                            commit;

                            v_g_procMsgCode := '1';
                            v_g_procMsg := 'W1005 資料回押有誤，請確認後重新執行。原因：查無待回押的媒體入帳明細檔資料。';
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>查無待回押的媒體入帳明細檔資料。');
                        end if;
                    exception
                        when others
                            then
                                rollback;

                                update BABATCHREC t1 set t1.PROCSTAT = '3'
                                                        ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                        ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                                   and t1.PROCSTAT = '4'
                                   and t1.BATCHTYP = '01';
                                commit;

                                v_g_procMsgCode := '1';
                                v_g_procMsg := 'W1005 資料回押有誤，請確認後重新執行。原因：'||SQLErrm;
                                v_g_errMsg := SQLErrm;
                                dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>給付媒體回押作業失敗：'||v_g_errMsg);
                    end;
                else
                    rollback;

                    update BABATCHREC t1 set t1.PROCSTAT = '3'
                                            ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                            ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                       and t1.PROCSTAT = '4'
                       and t1.BATCHTYP = '01';
                    commit;

                    v_g_procMsgCode := '1';
                    v_g_procMsg := 'W1005 資料回押有誤，請確認後重新執行。原因：查無待處理的批次記錄檔。';
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>查無待處理的批次記錄檔。');
                end if;
            else
                rollback;

                update BABATCHREC t1 set t1.PROCSTAT = '3'
                                        ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                        ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                 where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                   and t1.PROCSTAT = '4'
                   and t1.BATCHTYP = '01';
                commit;

                v_g_procMsgCode := '1';
                v_g_procMsg := 'W1005 資料回押有誤，請確認後重新執行。原因：給付別傳入錯誤('||nvl(trim(UPPER(v_i_paycode)),' ')||')，請重新檢核傳入值。';
                dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>給付別傳入錯誤('||nvl(trim(UPPER(v_i_paycode)),' ')||')');
            end if;

            v_o_procMsgCode := v_g_procMsgCode;
            v_o_procMsg := v_g_procMsg;
        exception
            when others
                then
                    rollback;

                    update BABATCHREC t1 set t1.PROCSTAT = '3'
                                            ,t1.ENDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                            ,t1.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t1.BABATCHRECID = to_Number(v_i_babatchrecid)
                       and t1.PROCSTAT = '4'
                       and t1.BATCHTYP = '01';
                    commit;

                    v_g_procMsgCode := '1';
                    v_g_procMsg := 'W1005 資料回押有誤，請確認後重新執行。原因：'||SQLErrm;
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
                    v_o_procMsgCode := v_g_procMsgCode;
                    v_o_procMsg := v_g_procMsg;
        end;
    --procedure sp_BA_updPaidMarkBJ End

End PKG_BA_ProcGiveDtl;
/