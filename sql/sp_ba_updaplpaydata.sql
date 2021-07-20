
create or replace procedure ba.SP_BA_UpdAPLPayData
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            SP_BA_UpdAPLPayData
    PURPOSE:         更新給付核付資料

    PARAMETER(IN):  *v_i_issutyp       (varChar2)       --核付處理類別
                    *v_i_issuym        (varChar2)       --核定年月
                    *v_i_paycode       (varChar2)       --給付別
                    *v_i_paydate       (varChar2)       --核付日期
                    *v_i_mtestmk       (varChar2)       --是否回押註記
                     v_i_procempno     (varChar2)       --執行作業人員員工編號
                     v_i_procdeptid    (varChar2)       --執行作業人員單位代碼
                     v_i_procip        (varChar2)       --執行作業人員IP
                     v_i_proctype      (varChar2)       --處理類型( 1:線上出媒體 5.批次出媒體)
                     v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                ：2為第二次出媒體(僅出失能國並勞36案部份)
                                                        --2011/12/08 Add by ChungYu
                     *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua

    PARAMETER(OUT):
                     *v_o_flag          (varChar2)       --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2009/02/10  Angela Wu    Created this procedure.
    1.1   2009/04/07  Angela Wu    Modify.
    2.0   2009/07/01  Angela Wu    Modify.

    NOTES:
    1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

********************************************************************************/
(
    v_i_issutyp        in      varChar2,
    v_i_issuym         in      varChar2,
    v_i_paycode        in      varChar2,
    v_i_paydate        in      varChar2,
    v_i_mtestmk        in      varChar2,
    v_i_procempno      in      varChar2,
    v_i_procdeptid     in      varChar2,
    v_i_procip         in      varChar2,
    v_i_payseqno       in      varChar2,
    v_i_bajobid        in      Varchar2,
    v_i_chkdate        in      varchar2,
    v_i_proctype       in      varChar2,
    v_o_flag           out     varchar2
)
authid definer is
    v_ProgName                 varChar2(200) := 'SP_BA_UpdAPLPayData';
    v_errMsg                   varChar2(2000);
    v_rowCount                 Number;
    v_annuAMT                  Number;
    v_baappbaseID              Number;
    v_fieID                    varChar2(2000);
    v_befimg                   varChar2(2000);
    v_aftimg                   varChar2(2000);
    v_chkdate                  varChar2(8);
    v_procempno                varChar2(10);
    v_procdeptid               varChar2(5);
    v_procip                   varChar2(50);
    v_accname_b                varChar2(50 char);
    v_accidn_b                 varChar2(12);
    v_paybankid_b              varChar2(3);
    v_branchid_b               varChar2(5);
    v_payeeacc_b               varChar2(14);
    v_paytyp_b                 varChar2(1);

    v_accname_a                varChar2(50 char);
    v_accidn_a                 varChar2(12);
    v_paybankid_a              varChar2(3);
    v_branchid_a               varChar2(5);
    v_payeeacc_a               varChar2(14);
    v_paytyp_a                 varChar2(1);
    v_flag                     varchar2(1);
    --查詢待更新核付資料的受理案件資料(含改匯資料)
    Cursor c_dataCur_1 is
        select t1.APNO                                                          --受理編號
              ,t1.SEQNO                                                         --序號
              ,t1.PAYKIND                                                       --給付種類
              ,t1.BENIDN                                                        --受益人身分證號
              ,t1.BENEVTREL                                                     --受益人與事故者關係
              ,t1.ISSUYM                                                        --核定年月
              ,t1.ORIISSUYM                                                     --(原始)核定年月
              ,t1.PAYYM                                                         --給付年月
              ,t1.PAYTYP                                                        --給付方式
              ,t1.ISSUTYP                                                       --核付類別
              ,t1.ACCNAME                                                       --戶名
              ,t1.ACCIDN                                                        --戶名IDN
              ,t1.PAYBANKID                                                     --金融機構總代號
              ,t1.BRANCHID                                                      --分支代號
              ,t1.PAYEEACC                                                      --銀行帳號
              ,t1.NLWKMK                                                        --普職註記
              ,t1.ADWKMK                                                        --加職註記
              ,t1.NACHGMK                                                       --普職互改註記
          from BAPAY t1
         where t1.APNO like (v_i_paycode||'%')
           and t1.ISSUYM = v_i_issuym
           and t1.PAYDATE = v_i_paydate
           and t1.PAYSEQNO = v_i_payseqno;

    --查詢待更新給付沖銷/收回的受理案件資料
    --20130625 Modify by Angela
    /*
    Cursor c_dataCur_2 is
        select t1.APNO                                                          --受理編號
              ,t1.SEQNO                                                         --序號
              ,t1.ISSUYM                                                        --核定年月
              ,t2.ORIISSUYM                                                     --(原始)核定年月
              ,t2.BRISSUYM                                                      --退匯核定年月
              ,t2.PAYYM                                                         --給付年月
              ,t2.AFMK                                                          --退改匯狀態
          from BAPAY t1,BAREGIVEDTL t2
         where t1.APNO = t2.APNO
           and t1.ACCSEQNO = t2.SEQNO
           and t1.APNO like (v_i_paycode||'%')
           and t1.ISSUYM = t2.ORIISSUYM
           and t2.ISSUYM = v_i_issuym
           and t2.AFPAYDATE = v_i_paydate;
    */
    Cursor c_dataCur_2 is
        select t.APNO                                                          --受理編號
              ,t.SEQNO                                                         --序號
              ,t.ORIISSUYM                                                     --(原始)核定年月
              ,t.BRISSUYM                                                      --退匯核定年月
              ,t.PAYYM                                                         --給付年月
              ,t.AFMK                                                          --退改匯狀態
          from BAREGIVEDTL t
         where t.APNO like (v_i_paycode||'%')
           and t.ISSUYM = v_i_issuym
           and t.AFPAYDATE = v_i_paydate
           and nvl(trim(t.WORKMK),' ') = 'Y'
           and t.MK = '4';

    --查詢待更新改匯資料的受理案件資料(因具名領取為整批退改匯,故需再查詢相關連的受款人序號,一同更新)
    Cursor c_dataCur_3(v_apno varChar2,v_seqno varChar2,v_paykind varChar2) is
        select t.BAAPPBASEID
              ,t.APNO
              ,t.SEQNO
          from BAAPPBASE t
         where t.APNO = v_apno
           and nvl(trim(t.ACCSEQNO),t.SEQNO) = v_seqno
           and t.PAYKIND = v_paykind
           and t.MANCHKMK = 'Y'
           --20120212 mark by chungyu
           --and (t.ACCEPTMK = 'Y' or nvl(trim(t.ACCEPTMK),' ') = ' ')
           and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
           and (t.EXEDATE is not null and nvl(trim(t.EXEDATE),' ')<>' ');

    --查詢待更新收回資料的不給付案件資料(全額收回)
    --add by ChungYu 2012/05/12
    Cursor c_dataCur_4 is
        select t1.APNO                                                          --受理編號
              ,t1.SEQNO                                                         --序號
              ,t1.PAYKIND                                                       --給付種類
              ,t1.BENIDN                                                        --受益人身分證號
              ,t1.BENEVTREL                                                     --受益人與事故者關係
              ,t1.ISSUYM                                                        --核定年月
              ,t1.PAYYM                                                         --給付年月
              ,t1.PAYTYP                                                        --給付方式
              ,t1.ISSUTYP                                                       --核付類別
              ,t1.ACCNAME                                                       --戶名
              ,t1.ACCIDN                                                        --戶名IDN
              ,t1.PAYBANKID                                                     --金融機構總代號
              ,t1.BRANCHID                                                      --分支代號
              ,t1.PAYEEACC                                                      --銀行帳號
              ,t1.NLWKMK                                                        --普職註記
              ,t1.ADWKMK                                                        --加職註記
              ,t1.NACHGMK                                                       --普職互改註記
          from BAISSUDATATEMP t1
         where t1.APNO like (v_i_paycode||'%')
           and t1.ISSUYM = v_i_issuym
           and t1.PAYSEQNO = v_i_payseqno
           and t1.CASETYP IN ('3','4')
           and t1.MANCHKMK = 'N'
           and t1.ISSUEAMT = 0
           and t1.UNACPAMT > 0;

    --查詢待更新退匯止付且已做給付收回的資料
    --20130626 add by Angela
    Cursor c_dataCur_5(v_i_chkdate varChar2) is
        select t3.APNO                                             --受理編號
              ,t3.SEQNO                                            --受款人序
              ,t3.ORIISSUYM                                        --最原始的核定年月
              ,t3.PAYYM                                            --給付年月
              ,t3.AFMK                                             --退改匯別
              ,t3.STEXPNDRECDATE                                   --止付收回日期(出納核付日期)
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
    v_errMsg := ' ';
    v_rowCount := 0;
    --v_annuAMT := 0;
    v_baappbaseID := 0;
    --v_badaprID := 0;
    v_fieID := '';
    v_befimg := '';
    v_aftimg := '';
    v_chkdate := '';
    v_procempno := '';
    v_procdeptid := '';
    v_procip := '';
    v_accname_b := ' ';
    v_accidn_b := ' ';
    v_paybankid_b := ' ';
    v_branchid_b := ' ';
    v_payeeacc_b := ' ';
    v_paytyp_b := ' ';

    v_accname_a := ' ';
    v_accidn_a := ' ';
    v_paybankid_a := ' ';
    v_branchid_a := ' ';
    v_payeeacc_a := ' ';
    v_paytyp_a := ' ';
    v_flag   := '0';
    begin
        v_rowCount := 0;
        v_chkdate := '';

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

        --查詢核定年月的月核定/改匯核定日期
        v_chkdate := PKG_BA_getPayData.fn_BA_getChkDate(v_i_issutyp,'1',v_i_paycode,v_i_issuym,'N',v_i_chkdate);

           for v_dataCur_1 in c_dataCur_1 Loop
              v_rowCount := 0;
              v_fieID := '';
              v_befimg := '';
              v_aftimg := '';
              v_annuAMT := 0;
              v_baappbaseID := 0;
              --v_badaprID := 0;
              v_accname_b := ' ';
              v_accidn_b := ' ';
              v_paybankid_b := ' ';
              v_branchid_b := ' ';
              v_payeeacc_b := ' ';
              v_paytyp_b := ' ';
              v_accname_a := ' ';
              v_accidn_a := ' ';
              v_paybankid_a := ' ';
              v_branchid_a := ' ';
              v_payeeacc_a := ' ';
              v_paytyp_a := ' ';

              if v_dataCur_1.ISSUTYP = '1' or v_dataCur_1.ISSUTYP = '2' or v_dataCur_1.ISSUTYP = '3' or v_dataCur_1.ISSUTYP = '4' or v_dataCur_1.ISSUTYP = '5' then
                  --更新給付核定檔的核付相關欄位(for 核付資料,不含改匯資料)
                  Update BADAPR t set t.APLPAYDATE = v_i_paydate
                                     ,t.APLPAYMK = '3'
                                     --,t.ANNUAMT = v_annuAMT
                                     ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                   where t.APNO = v_dataCur_1.APNO
                     and t.ISSUYM = v_dataCur_1.ISSUYM
                     and t.PAYYM = v_dataCur_1.PAYYM
                     and t.MTESTMK = v_i_mtestmk
                     --2012/08/28 Modify by ChungYu 因為遺屬有可能某一位遺屬不合格MANCHKMK = 'N'，其他
                     --遺屬合格MANCHKMK = 'Y'，但是於月核過後不管合格或不合格，均統一要押帳務註記，此舉
                     --主要是為了將來該位不合格遺屬舉證成功其為合格，會影響核定金額分配。
                     --and t.MANCHKMK = 'Y'
                     and Trim(t.MANCHKMK) is not null
                     --20120212 mark by chungyu
                     --and (t.ACCEPTMK = 'Y' or nvl(trim(t.ACCEPTMK),' ') = ' ')
                     and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                     and (t.APLPAYMK is null or nvl(trim(t.APLPAYMK),' ')=' ')
                     and (t.APLPAYDATE is null or nvl(trim(t.APLPAYDATE),' ')=' ');

              elsif v_dataCur_1.ISSUTYP = 'A' then
                if v_i_proctype = '5' then --大批量才做退改匯
                  --若帳號資料於出納系統已更改時,除了回寫帳號相關欄位外,需一併將變更的資料寫入相關Log檔
                  for v_dataCur_3 in c_dataCur_3(v_dataCur_1.APNO,v_dataCur_1.SEQNO,v_dataCur_1.PAYKIND) Loop
                      v_rowCount := 0;
                      select Count(*)
                        into v_rowCount
                        from BAAPPBASE t
                       where t.APNO = v_dataCur_1.APNO
                         and t.SEQNO = v_dataCur_3.SEQNO
                         and t.PAYKIND = v_dataCur_1.PAYKIND
                         and t.MANCHKMK = 'Y'
                         and (t.ACCEPTMK = 'Y' or nvl(trim(t.ACCEPTMK),' ') = ' ')
                         and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                         and (t.EXEDATE is not null and nvl(trim(t.EXEDATE),' ')<>' ');

                      if v_rowCount>0 then
                          v_fieID := '';
                          v_befimg := '';
                          v_aftimg := '';
                          v_baappbaseID := 0;
                          v_accname_b := ' ';
                          v_accidn_b := ' ';
                          v_paybankid_b := ' ';
                          v_branchid_b := ' ';
                          v_payeeacc_b := ' ';
                          v_paytyp_b := ' ';
                          v_accname_a := v_dataCur_1.ACCNAME;
                          v_accidn_a := v_dataCur_1.ACCIDN;
                          v_paybankid_a := v_dataCur_1.PAYBANKID;
                          v_branchid_a := v_dataCur_1.BRANCHID;
                          v_payeeacc_a := v_dataCur_1.PAYEEACC;
                          v_paytyp_a := v_dataCur_1.PAYTYP;

                          begin
                              --查詢欲改匯的案件於給付主檔的帳號資料
                              select t.BAAPPBASEID
                                    ,t.ACCNAME
                                    ,t.ACCIDN
                                    ,t.PAYBANKID
                                    ,t.BRANCHID
                                    ,t.PAYEEACC
                                    ,t.PAYTYP
                                into v_baappbaseID
                                    ,v_accname_b
                                    ,v_accidn_b
                                    ,v_paybankid_b
                                    ,v_branchid_b
                                    ,v_payeeacc_b
                                    ,v_paytyp_b
                                from BAAPPBASE t
                               where t.APNO = v_dataCur_1.APNO
                                 and t.SEQNO = v_dataCur_3.SEQNO
                                 and t.PAYKIND = v_dataCur_1.PAYKIND
                                 and t.MANCHKMK = 'Y'
                                 --20120212 mark by chungyu
                                 --and (t.ACCEPTMK = 'Y' or nvl(trim(t.ACCEPTMK),' ') = ' ')
                                 and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                                 and (t.EXEDATE is not null and nvl(trim(t.EXEDATE),' ')<>' ');

                              --若帳號資料於出納系統改匯時已更改,除了需將新的欄位資料寫回給付主檔外,另外也將改前值及改後值一併寫入BAAPPLog
                              --比對給付主檔中的"戶名"與出納系統變更改匯資料的"戶名"。若有變更者,需將改前值及改後值一併寫入BAAPPLog
                              if (nvl(trim(v_accname_b),' ') <> nvl(trim(v_accname_a),' ')) then
                                   v_fieID := v_fieID || 'ACCNAME,';
                                   v_befimg := v_befimg || v_accname_b || ',';
                                   v_aftimg := v_aftimg || v_accname_a || ',';
                                   PKG_BA_RecordLog.sp_BA_recBaAPPLog(v_baappbaseID
                                                                     ,'U'
                                                                     ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                                     ,v_procempno
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
                                                                     ,v_procempno
                                                                     ,'戶名IDN'
                                                                     ,v_accidn_b
                                                                     ,v_accidn_a
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
                                                                     ,v_procempno
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
                                                                     ,v_procempno
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
                                                                     ,v_procempno
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
                                                                     ,v_procempno
                                                                     ,'給付方式'
                                                                     ,v_paytyp_b
                                                                     ,v_paytyp_a
                                                                     );
                              end if;

                              --若帳號資料於出納系統改匯時已更改,除了需回寫給付主檔的帳號相關欄位外,需一併記錄MMAPLog
                              if nvl(trim(v_fieID),' ') <> ' ' then
                                  PKG_BA_RecordLog.sp_BA_recMMAPLog('BAAPPBASE'
                                                                   ,'BAAPPBASEID='||v_baappbaseID
                                                                   ,(fn_BA_transDateValue(to_Char(Sysdate,'YYYYMMDD'),'1')||substr(to_Char(systimestamp,'HH24MISSFF'),1,8))
                                                                   ,'[BA]改匯核付後回押作業(批次-BA_UpdAPLPayData)'
                                                                   ,'DB(SP)'
                                                                   ,v_procdeptid
                                                                   ,v_procempno
                                                                   ,v_procip
                                                                   ,'U'
                                                                   ,v_fieID
                                                                   ,substr(v_befimg,1,length(v_befimg)-1)
                                                                   ,substr(v_aftimg,1,length(v_aftimg)-1)
                                                                   ,''
                                                                   ,''
                                                                   ,'0'
                                                                   );
                              end if;

                          exception
                              when others
                                  then
                                      v_errMsg := SQLErrm;
                                      dbms_output.put_line(RPAD('**Err:'||v_ProgName,85,'-')||'>>(RecordLog):'||v_errMsg);
                                      --修改log作法 by TseHua 20180419
                                      sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                                       RPAD('**Err:' || v_ProgName, 85, '-') || '>>' || v_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                                      
                                      v_flag := '1';
                                      v_o_flag := v_flag;
                          end;

                          --更新給付主檔的受款人帳戶相關欄位(for 改匯資料,不含核付資料)
                          Update BAAPPBASE t set t.ACCNAME = v_dataCur_1.ACCNAME
                                                ,t.ACCIDN = v_dataCur_1.ACCIDN
                                                ,t.PAYBANKID = v_dataCur_1.PAYBANKID
                                                ,t.BRANCHID = v_dataCur_1.BRANCHID
                                                ,t.PAYEEACC = v_dataCur_1.PAYEEACC
                                                ,t.PAYTYP = v_dataCur_1.PAYTYP
                                                ,t.UPDUSER = v_procempno
                                                ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')

                           where t.APNO = v_dataCur_1.APNO
                             and t.SEQNO = v_dataCur_3.SEQNO
                             and t.PAYKIND = v_dataCur_1.PAYKIND
                             and t.MANCHKMK = 'Y'
                             --20120212 mark by chungyu
                             --and (t.ACCEPTMK = 'Y' or nvl(trim(t.ACCEPTMK),' ') = ' ')
                             and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                             and (t.EXEDATE is not null and nvl(trim(t.EXEDATE),' ')<>' ');

                      end if;
                  end Loop;

                  --更新改匯記錄檔中,改匯核付資料的核付相關欄位
                  Update BAREGIVEDTL t set t.AFPAYDATE = v_i_paydate
                                          ,t.AFMK = '2'
                                          ,t.PAYTYP = v_dataCur_1.PAYTYP
                                          ,t.BLIACCOUNT = '0053'
                                          ,t.ACTTITLEAPCODE = 'KLA'
                                          ,t.RECHKDATE = v_i_chkdate
                                          ,t.RECHKMAN = v_i_procempno
                                          ,t.EXEMAN = v_i_procempno
                                          ,t.EXEDATE = to_Char(Sysdate,'YYYYMMDD')
                                          ,t.CPRNDATE = to_Char(Sysdate,'YYYYMMDD')
                                          ,t.RPTPAGE = '560001'
                                          ,t.RPTROWS = '1'
                                          ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                   where t.APNO = v_dataCur_1.APNO
                     and t.SEQNO = v_dataCur_1.SEQNO
                     and t.ORIISSUYM = v_dataCur_1.ORIISSUYM
                     and t.ISSUYM = v_dataCur_1.ISSUYM
                     and t.PAYYM = v_dataCur_1.PAYYM
                     and t.MK = '2'
                     and nvl(trim(t.WORKMK),' ') <> 'Y'
                     and (trim(t.AFCHKDATE) is not null and nvl(trim(t.AFCHKDATE),' ')<>' ')
                     and (trim(t.AFPAYDATE) is null or nvl(trim(t.AFPAYDATE),' ')=' ');

                  --20120822 add by Angela
                  --更新退匯記錄檔中的核付相關欄位
                  Update BAPFLBAC t set t.AFMK = '2'
                                       ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                   where t.APNO = v_dataCur_1.APNO
                     and t.SEQNO = v_dataCur_1.SEQNO
                     and t.ORIISSUYM = v_dataCur_1.ORIISSUYM
                     and t.PAYYM = v_dataCur_1.PAYYM
                     and t.AFMK = '1';

                  --更新給付核定檔中,改匯資料的核付相關欄位
                  Update BADAPR t set t.REMITDATE = v_i_paydate
                                     ,t.REMITMK = '3'
                                     ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                   where t.APNO = v_dataCur_1.APNO
                     and t.SEQNO = v_dataCur_1.SEQNO
                     and t.ISSUYM = v_dataCur_1.ORIISSUYM
                     and t.PAYYM = v_dataCur_1.PAYYM
                     and t.MTESTMK = v_i_mtestmk
                     and t.MANCHKMK = 'Y'
                     --20120212 mark by chungyu
                     --and (t.ACCEPTMK = 'Y' or nvl(trim(t.ACCEPTMK),' ') = ' ')
                     and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                     and t.REMITMK in ('2','3')
                     and (t.REMITDATE is not null and nvl(trim(t.REMITDATE),' ')<>' ')
                     and (t.APLPAYMK is not null and nvl(trim(t.APLPAYMK),' ')<>' ')
                     and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');
                end if;
              end if;
          end Loop;
          --LOG BATCHJOB detail Add By Zehua 20140722
          if v_i_proctype = '5' then --大批量才做退改匯
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '更新給付核定檔、給付主檔、退匯記錄檔、改匯記錄檔、給付核定檔、不給付案件 完成', replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
           
          else
            
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                 '更新給付核定檔、給付主檔 完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
           
          end if;
       if v_i_proctype = '5' then --大批量才做退改匯
        if v_i_issutyp ='1' then
            --更新改匯記錄檔中,給付沖銷資料的核付相關欄位
            Update BAREGIVEDTL t set t.AFPAYDATE = v_i_paydate
                                    ,t.AFMK = '4'
                                    ,t.BLIACCOUNT = '0053'
                                    ,t.ACTTITLEAPCODE = 'GGG'
                                    ,t.RECHKDATE = v_i_chkdate
                                    ,t.RECHKMAN = v_i_procempno
                                    ,t.EXEMAN = v_i_procempno
                                    ,t.EXEDATE = to_Char(Sysdate,'YYYYMMDD')
                                    ,t.CPRNDATE = to_Char(Sysdate,'YYYYMMDD')
                                    ,t.RPTPAGE = '560001'
                                    ,t.RPTROWS = '1'
                                    ,t.PROCUSER = v_i_procempno
                                    ,t.PROCDEPTID = v_i_procdeptid
                                    ,t.PROCIP = v_i_procip
                                    ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
            where t.ISSUYM = v_i_issuym
              and t.APNO like (v_i_paycode||'%')
              and nvl(trim(t.WORKMK),' ') = 'Y'
              and ((t.MK = '1' and nvl(trim(t.AFCHKDATE),' ')<>' ' and trim(t.AFCHKDATE) is not null)
                or (t.MK = '2' and (nvl(trim(t.AFPAYDATE),' ')=' ' or trim(t.AFPAYDATE) is null))
                or (t.MK = '4' and (nvl(trim(t.AFPAYDATE),' ')=' ' or trim(t.AFPAYDATE) is null)));
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '0',
               '更新改匯記錄檔中,給付沖銷資料的核付相關欄位完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            
           
            for v_dataCur_2 in c_dataCur_2 Loop
                --更新給付核定檔中,給付沖銷資料的核付相關欄位
                Update BADAPR t set t.REMITDATE = deCode(v_dataCur_2.AFMK,'4',v_i_paydate,t.REMITDATE)
                                   ,t.REMITMK = deCode(v_dataCur_2.AFMK,'4','4',t.REMITMK)
                                   ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                 where t.APNO = v_dataCur_2.APNO
                   and t.SEQNO = v_dataCur_2.SEQNO
                   and t.ISSUYM = v_dataCur_2.ORIISSUYM
                   and t.PAYYM = v_dataCur_2.PAYYM
                   and t.MTESTMK = v_i_mtestmk
                   and t.MANCHKMK = 'Y'
                   --and (t.ACCEPTMK = 'Y' or nvl(trim(t.ACCEPTMK),' ') = ' ')
                   and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                   and t.REMITMK = '2'
                   and (t.REMITDATE is not null and nvl(trim(t.REMITDATE),' ')<>' ')
                   and (t.APLPAYMK is not null and nvl(trim(t.APLPAYMK),' ')<>' ')
                   and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');

                --更新退匯記錄檔中的核付相關欄位
                Update BAPFLBAC t set t.AFMK = v_dataCur_2.AFMK
                                     ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                 where t.APNO = v_dataCur_2.APNO
                   and t.SEQNO = v_dataCur_2.SEQNO
                   and t.ORIISSUYM = v_dataCur_2.ORIISSUYM
                   and t.BRISSUYM = v_dataCur_2.BRISSUYM
                   and t.PAYYM = v_dataCur_2.PAYYM;
            end Loop;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '更新改匯記錄檔中,給付沖銷資料的核付欄位、更新退匯記錄檔中的核付相關欄位 完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
           
            --更新不給付案件(全額收回)核定檔中,核付相關欄位(for 核付資料)  Add By ChungYu 2012/05/16
            for v_dataCur_4 in c_dataCur_4 Loop
                --更新給付核定檔中,給付沖銷資料的核付相關欄位
                Update BADAPR t set t.APLPAYDATE = v_i_paydate
                                   ,t.APLPAYMK = '3'
                                   ,t.ANNUAMT = 0
                                   ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.APNO = v_dataCur_4.APNO
                       and t.ISSUYM = v_dataCur_4.ISSUYM
                       --and t.PAYYM = v_dataCur_4.PAYYM         --核定年月下所有給付年月全押帳務註記
                       and t.MTESTMK = v_i_mtestmk
                       and t.MANCHKMK = 'N'
                       and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                       and (t.APLPAYMK is null or nvl(trim(t.APLPAYMK),' ')=' ')
                       and (t.APLPAYDATE is null or nvl(trim(t.APLPAYDATE),' ')=' ');
            end Loop;
            
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '更新不給付案件(全額收回)核定檔中,核付相關欄位(for 核付資料) 完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            
            for v_dataCur_5 in c_dataCur_5(v_chkdate) Loop
                --更新給付核定檔中,退匯止付且已做給付收回的資料
                Update BADAPR t set t.REMITDATE = deCode(v_dataCur_5.AFMK,'4',v_dataCur_5.STEXPNDRECDATE,t.REMITDATE)
                                   ,t.REMITMK = deCode(v_dataCur_5.AFMK,'4','4',t.REMITMK)
                                   ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                 where t.APNO = v_dataCur_5.APNO
                   and t.SEQNO = v_dataCur_5.SEQNO
                   and t.ISSUYM = v_dataCur_5.ORIISSUYM
                   and t.PAYYM = v_dataCur_5.PAYYM
                   and t.MTESTMK = v_i_mtestmk
                   and t.MANCHKMK = 'Y'
                   --and (t.ACCEPTMK = 'Y' or nvl(trim(t.ACCEPTMK),' ') = ' ')
                   and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                   and t.REMITMK = '2'
                   and (t.REMITDATE is not null and nvl(trim(t.REMITDATE),' ')<>' ')
                   and (t.APLPAYMK is not null and nvl(trim(t.APLPAYMK),' ')<>' ')
                   and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');
            end Loop;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '更新給付核定檔中,退匯止付且已做給付收回的資料 完成',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            
         end if;
            --執行改匯核付回寫出納系統作業
            PKG_BA_ProcCashier.sp_BA_expChangRemitAplpay(v_i_issuym,v_i_paycode,v_i_paydate,v_i_bajobid,v_flag);

            --執行退匯止付且已做給付收回回寫出納系統作業
            PKG_BA_ProcCashier.sp_BA_expStexpndRec(v_i_paycode,v_chkdate,v_procempno,v_procdeptid,v_procip,v_i_bajobid,v_flag);

            --執行應收款立帳及應收款已收作業
            PKG_BA_ProcACPDtl.sp_BA_MonthApprove(v_i_issuym,v_i_paycode,v_chkDate,v_i_mtestmk,v_procempno,v_i_bajobid,v_flag);

        end if;

        v_o_flag := v_flag;
    exception
        when others
            then
                rollback;
                v_errMsg := SQLErrm;
                dbms_output.put_line(RPAD('**Err:'||v_ProgName,85,'-')||'>>'||v_errMsg);
                --修改log作法 by TseHua 20180419
                  sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '1',
                   RPAD('**Err:'||v_ProgName,85,'-')||'>>'||v_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
               
                v_flag   := '1';
                v_o_flag := v_flag;

    end;
exception
    when others
        then
            rollback;
            v_errMsg := SQLErrm;
            dbms_output.put_line(RPAD('**Err:'||v_ProgName,85,'-')||'>>'||v_errMsg);
            
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '1',
                RPAD('**Err:'||v_ProgName,85,'-')||'>>'||v_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
            
             v_flag   := '1';
             v_o_flag := v_flag;
end SP_BA_UpdAPLPayData;
/

