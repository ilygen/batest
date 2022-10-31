CREATE OR REPLACE Package BA.PKG_BA_ProcACPDtl
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_ProcACPDtl
    PURPOSE:         應收款立帳及應收款已收作業

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
    v_g_rowCount             Number;
    v_g_acpCount             Number;
    v_g_ins_unacpCount       Number;
    v_g_upd_unacpCount       Number;
    v_g_procempno            varChar2(10);
    v_g_flag                 varchar2(1);

    procedure sp_BA_MonthApprove (
        v_i_issuym           in      varChar2,
        v_i_paycode          in      varChar2,
        v_i_chkdate          in      varChar2,
        v_i_mtestmk          in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_bajobid          in      varchar2,
        v_o_flag             out     varchar2
    );

    /*procedure sp_BA_Refundment (
        v_i_issuym           in      varChar2,
        v_i_paycode          in      varChar2,
        v_i_chkdate          in      varChar2,
        v_i_procempno        in      varChar2
    );*/

    procedure sp_BA_ArtificialPay (
        v_i_issuym           in      varChar2,
        v_i_paycode          in      varChar2,
        v_i_chkdate          in      varChar2,
        v_i_paydate          in      varChar2,
        v_i_procempno        in      varChar2
    );

End;
/

CREATE OR REPLACE Package Body BA.PKG_BA_ProcACPDtl
is
    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_ProcACPDtl.sp_BA_MonthApprove
        PURPOSE:         產生應收款立帳及應收款已收相關資料(for 月核付)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                        *v_i_mtestmk       (varChar2)       --處理註記
                         v_i_procempno     (varChar2)       --執行作業人員員工編號
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
        1.1   2015/07/02  ChungYu      修改遺屬年金事故者勾稽到，事故者於老年及失能年金另案扣減時，
                                       由遺屬年金扣減時，寫入已收明細檔(BAACPDTL)需分攤至每位遺屬上，
                                       並更新應收未收明細檔(BAUNACPDTL).
        1.2   2015/11/25  ChungYu      修改全額立帳收回時，當有發生退匯沖抵會將應收及撥付給付年月均做
                                       應收回的情形。
        1.3   2018/01/31  ChungYu      修改遺屬年金事故者勾稽到，事故者於老年及失能年金另案扣減時，
                                       由遺屬年金扣減時，寫入已收明細檔(BAACPDTL)需分攤至每位遺屬上，
                                       並依照扣減記錄檔(BACUTREC)實際沖抵的金額寫入。
        1.4   2022/10/26  William      bcbcweb-46 sp_BA_MonthApprove 屬月核應收已收金額條件修改

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_MonthApprove (
        v_i_issuym           in      varChar2,
        v_i_paycode          in      varChar2,
        v_i_chkdate          in      varChar2,
        v_i_mtestmk          in      varChar2,
        v_i_procempno        in      varChar2,
        v_i_bajobid          in      varChar2,
        v_o_flag             out     varchar2
    ) is
        v_baunacpdtlid       Number;
        v_NLWKMK             varChar2(1);
        v_ADWKMK             varChar2(1);
        v_bacutrecid         Number;
        v_typemk             varChar2(1);
        v_disamt             Number;
        v_dispaykind         varChar2(2);
        v_transactionid      varChar2(16);
        v_transactionseq     varChar2(2);
        v_inskd              varChar2(1);
        v_actendmk           varChar2(1);
        v_nachgmk            varChar2(1);
        v_datarecnum         Number;

        v_DisApno            varChar2(13);      -- 2015/11/25 Add By ChungYu
        v_DisSeqno           varChar2(4);       -- 2015/11/25 Add By ChungYu
        v_DisIssuym          varChar2(6);       -- 2015/11/25 Add By ChungYu
        v_DisPayym           varChar2(6);       -- 2015/11/25 Add By ChungYu

        --查詢月核付後,待立帳及應收回的受理案件資料
        Cursor c_dataCur is
            select t1.BENIDS
                  ,t1.BENIDNNO
                  ,t2.MAPNO
                  ,t2.MSEQNO
                  ,t2.MISSUYM
                  ,t2.MPAYYM
                  ,t2.DISPAYKIND
                  ,t2.TYPEMK
                  ,t2.RECSEQ
                  ,t2.RECAMT
                  ,t2.RECREM
                  ,t2.DISAMT
                  ,t2.APNO
                  ,t2.SEQNO
                  ,t2.ISSUYM
                  ,t1.PAYYM
                  ,t2.PAYKIND
                  ,t2.TRANSACTIONID
                  ,t2.TRANSACTIONSEQ
                  ,t2.INSKD
                  ,t2.ACTENDMK
                  ,t2.BACUTRECID
                  ,t1.NLWKMK                                                                               --普職註記
                  ,t1.ADWKMK                                                                               --加職註記
                  ,t1.NACHGMK                                                                              --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.BENIDS
                          ,t11.BENIDN as BENIDNNO
                          ,t11.PAYKIND
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                          ,t11.NACHGMK
                      from BADAPR t11
                     where ((t11.APNO like (v_i_paycode||'%') and v_i_paycode <> 'S')
                        or (t11.APNO like (v_i_paycode||'%') and v_i_paycode = 'S' and t11.SEQNO <> '0000'))                 -- 2012/08/29 Add By ChungYu 遺屬事故者不應再立帳收回
                       and t11.ISSUYM = v_i_issuym
                       and t11.MTESTMK = v_i_mtestmk
                       and (t11.SUPRECMK = 'D' Or t11.OTHERAAMT > 0)                                 -- 2012/12/24 Add By ChungYu t11.OTHERAAMT > 0 已收有可能是扣前期應收的帳
                       --and (trim(t11.MANCHKMK) is not null                                         -- 2012/08/29 Mark By ChungYu 因遺屬有可能單一遺屬不合格其他遺屬合格的狀況發生
                       --      and (t11.MCHKTYP <> '3' and t11.MANCHKMK <> 'N'))                     --                            排除不給付案收回，不給付案另於後續處理
                       and ((trim(t11.MANCHKMK) is not null) and (t11.MCHKTYP <> '3'))               -- 20131117 Modify by Angela 放寬條件。將 MANCHKMK <> 'N' 刪除
                       --and t11.MANCHKMK = 'Y'
                       and (t11.CHKDATE is not null and nvl(trim(t11.CHKDATE),' ')<>' ')
                       and t11.CHKDATE = v_i_chkdate ) t1
                  ,(select t21.BACUTRECID
                          ,t21.MAPNO
                          ,t21.MSEQNO
                          ,t21.MISSUYM
                          ,t21.MPAYYM
                          ,t21.DISPAYKIND
                          ,t21.TYPEMK
                          ,t21.RECSEQ
                          ,t21.RECAMT
                          ,t21.RECREM
                          ,t21.DISAMT
                          ,t21.BENIDS
                          ,t21.APNO
                          ,t21.SEQNO
                          ,t21.ISSUYM
                          ,t21.PAYYM
                          ,t21.PAYKIND
                          ,t21.TRANSACTIONID
                          ,t21.TRANSACTIONSEQ
                          ,t21.INSKD
                          ,t21.ACTENDMK
                      from BACUTREC t21
                     where ((t21.APNO like (v_i_paycode||'%'))
                         or ((nvl(trim(t21.APNO),' ')=' ' or trim(t21.APNO) is null)
                          and t21.MAPNO like (v_i_paycode||'%')
                          /*and t21.TYPEMK = '0'*/))                                                 --20140325 Mark by Angela 增列其他應收記錄來源
                       and ((t21.ISSUYM = v_i_issuym)
                        or ((t21.MISSUYM = v_i_issuym)
                        and (nvl(trim(t21.ISSUYM),' ')=' '
                          or trim(t21.ISSUYM) is null)
                         /*and t21.TYPEMK = '0'*/))                                                  --20140325 Mark by Angela 增列其他應收記錄來源
                       and t21.MTESTMK = v_i_mtestmk
                       and t21.CHKDATE = v_i_chkdate ) t2
              where (t1.APNO = t2.MAPNO
                 and t1.SEQNO = t2.MSEQNO
                 and t1.ISSUYM = t2.MISSUYM
                 and t1.PAYYM = t2.MPAYYM
                 and t1.PAYKIND = t2.DISPAYKIND
                 and (nvl(trim(t2.RECSEQ),' ')=' ' or trim(t2.RECSEQ) is null))
                 or (t1.APNO = t2.APNO
                 and t1.SEQNO = t2.SEQNO
                 and t1.ISSUYM = t2.ISSUYM
                 and t1.PAYYM = t2.PAYYM
                 and t1.PAYKIND = t2.DISPAYKIND
                 and (nvl(trim(t2.RECSEQ),' ')<>' ' or trim(t2.RECSEQ) is not null))
               order by to_Number(t2.BACUTRECID),t2.APNO,t2.SEQNO,t2.ISSUYM,t2.PAYYM,t2.TYPEMK;

        --  查詢月核付後,遺屬年金收回老年或失能年金的受理案件資料
        --  2015/07/02 Add By ChungYu
        Cursor c_dataSurCur is
            select t1.BENIDS
                  ,t1.BENIDNNO
                  ,t2.MAPNO
                  ,t2.MSEQNO
                  ,t2.MISSUYM
                  ,t2.MPAYYM
                  ,t2.DISPAYKIND
                  ,t2.TYPEMK
                  ,t2.RECSEQ
                  ,t2.RECAMT
                  ,t2.RECREM
                  ,t2.DISAMT
                  ,t2.APNO
                  ,t2.SEQNO
                  ,t2.ISSUYM
                  ,t1.PAYYM
                  ,t2.PAYKIND
                  ,t2.TRANSACTIONID
                  ,t2.TRANSACTIONSEQ
                  ,t2.INSKD
                  ,t2.ACTENDMK
                  ,t2.BACUTRECID
                  ,t1.NLWKMK                                                                               --普職註記
                  ,t1.ADWKMK                                                                               --加職註記
                  ,t1.NACHGMK                                                                              --普職互改註記
              from (select t11.APNO
                          ,t11.SEQNO
                          ,t11.ISSUYM
                          ,t11.PAYYM
                          ,t11.BENIDS
                          ,t11.BENIDN as BENIDNNO
                          ,t11.PAYKIND
                          ,t11.NLWKMK
                          ,t11.ADWKMK
                          ,t11.NACHGMK
                      from BADAPR t11
                     where (t11.APNO like (v_i_paycode||'%') and v_i_paycode = 'S' and t11.SEQNO <> '0000')
                       and t11.ISSUYM = v_i_issuym
                       and t11.MTESTMK = v_i_mtestmk
                       and t11.evtotheraamt > 0
                       and ((trim(t11.MANCHKMK) is not null) and (t11.MCHKTYP <> '3'))
                       and (t11.CHKDATE is not null and nvl(trim(t11.CHKDATE),' ')<>' ')
                       and t11.CHKDATE = v_i_chkdate
                       and t11.APNO In (Select t12.Apno From BADAPR t12
                                         Where t12.Apno = t11.Apno
                                           And t12.Seqno = '0000'
                                           And t12.issuym = t11.issuym
                                           And t12.MTESTMK = t11.MTESTMK
                                           And t12.payym = t11.payym
                                           And t12.OTHERAAMT > 0 )) t1
                  ,(select t21.BACUTRECID
                          ,t21.MAPNO
                          ,t21.MSEQNO
                          ,t21.MISSUYM
                          ,t21.MPAYYM
                          ,t21.DISPAYKIND
                          ,t21.TYPEMK
                          ,t21.RECSEQ
                          ,t21.RECAMT
                          ,t21.RECREM
                          --,t22.EVTPAYOTHERAAMT AS "DISAMT"     -- 2018/01/31 Mark By ChugnYu 因會有部分收回的情況發生，所以要以實際沖抵的金額寫入
                          -- ,t21.DISAMT                       -- 2018/01/31 Add By ChugnYu
                          ,(case when t22.EVTPAYOTHERAAMT>t21.DISAMT then t21.DISAMT else t22.EVTPAYOTHERAAMT end) DISAMT --20221025 BA的案件未收沖抵,bacutrec為S事故者撥付案與未收案的對應切割資料,BASURVAMOR有紀錄到遺屬但沒有未收的對應,已收資料應紀錄遺屬與未收的對應
                          ,t21.BENIDS
                          ,t21.APNO
                          ,t22.SEQNO
                          ,t21.ISSUYM
                          ,t21.PAYYM
                          ,t21.PAYKIND
                          ,t21.TRANSACTIONID
                          ,t21.TRANSACTIONSEQ
                          ,t21.INSKD
                          ,t21.ACTENDMK
                      From
                   (Select t211.*
                      from BACUTREC t211
                     where t211.APNO like (v_i_paycode||'%')
                       and t211.MAPNO not like (v_i_paycode||'%')
                       and ((t211.ISSUYM = v_i_issuym)
                        or (t211.MISSUYM = v_i_issuym))
                       and t211.MTESTMK = v_i_mtestmk
                       and t211.CHKDATE = v_i_chkdate) t21,
                       (Select t212.* From BASURVAMOR t212
                         Where t212.APNO like ('S'||'%')
                           and t212.seqno <> '0000'
                           and t212.ISSUYM = v_i_issuym
                           and t212.MTESTMK = v_i_mtestmk
                           and t212.EVTPAYOTHERAAMT > 0) t22
                 Where t21.APNO = t22.APNO
                   And t21.ISSUYM = t22.ISSUYM
                   And t21.PAYYM = t22.PAYYM
                   And t21.MTESTMK = t22.MTESTMK) t2
              where  t1.APNO = t2.APNO
                 and t1.SEQNO = t2.SEQNO
                 and t1.ISSUYM = t2.ISSUYM
                 and t1.PAYYM = t2.PAYYM
                 and t1.PAYKIND = t2.DISPAYKIND
                 and (nvl(trim(t2.RECSEQ),' ')<>' ' or trim(t2.RECSEQ) is not null)
               order by to_Number(t2.BACUTRECID),t2.APNO,t2.SEQNO,t2.ISSUYM,t2.PAYYM,t2.TYPEMK;

        --查詢月核付後,全額立帳(不給付)應收回的受理案件資料
        Cursor c_dataRecCur is
             select t2.APNO
                   ,t2.SEQNO
                   ,t2.ISSUYM
                   ,t2.PAYYM
                   ,t2.BENIDS
                   ,t2.BENIDN as BENIDNNO
                   ,t2.PAYKIND
                   ,t2.RECAMT
                   ,t2.NLWKMK
                   ,t2.ADWKMK
                   ,t2.NACHGMK
               from (select distinct t11.Apno
                       from BAAPPBASE t11
                      where --t11.Apno Like (v_i_paycode||'%')  Modify By ChungYu 2013/05/15 修改遺屬不給付立帳
                            --排除事故者
                            ((t11.APNO like (v_i_paycode||'%') and v_i_paycode <> 'S')
                          or (t11.APNO like (v_i_paycode||'%') and v_i_paycode = 'S' and t11.SEQNO <> '0000'))
                        and t11.ISSUYM = v_i_issuym
                        and t11.CASETYP in ('3','4')
                        and t11.PROCSTAT = '90'
                        and t11.MANCHKMK = 'N' ) t1,
                    (select t21.APNO
                           ,t21.SEQNO
                           ,t21.ISSUYM
                           ,t21.PAYYM
                           ,t21.BENIDS
                           ,t21.BENIDN
                           ,t21.PAYKIND
                           ,t21.RECAMT
                           ,t21.NLWKMK
                           ,t21.ADWKMK
                           ,t21.NACHGMK
                       from BADAPR t21
                      where  --t21.APNO like (v_i_paycode||'%')  Modify By ChungYu 2013/05/15 修改遺屬不給付立帳
                            --排除事故者
                            ((t21.APNO like (v_i_paycode||'%') and v_i_paycode <> 'S')
                         or (t21.APNO like (v_i_paycode||'%') and v_i_paycode = 'S' and t21.SEQNO <> '0000'))
                        and t21.ISSUYM = v_i_issuym
                        and t21.MTESTMK = v_i_mtestmk
                        and t21.MCHKTYP = '3'
                        and t21.MANCHKMK = 'N'
                        and (t21.CHKDATE is not null and nvl(trim(t21.CHKDATE),' ')<>' ')
                        and t21.CHKDATE = v_i_chkdate
                        and t21.BENEVTREL not in ('F','Z')
                        and NVL(t21.PAYBANANCE,0) = 0
                        and NVL(t21.BEFISSUEAMT,0) = 0
                        and NVL(t21.RECAMT,0) > 0 ) t2
              where t1.APNO = t2.APNO
              order by t2.APNO,t2.SEQNO,t2.ISSUYM,t2.PAYYM;

        begin
            v_g_ProgName := 'PKG_BA_ProcACPDtl.sp_BA_MonthApprove';
            v_g_errMsg := '';
            v_g_rowCount := 0;
            v_g_acpCount := 0;
            v_g_ins_unacpCount := 0;
            v_g_upd_unacpCount := 0;
            v_g_procempno := ' ';
            v_baunacpdtlid := 0;
            v_g_flag := 0;
            --若無傳入執行作業人員員工編號,則取出參數檔的設定
            if nvl(trim(v_i_procempno),' ')<>' ' then
                v_g_procempno := v_i_procempno;
            else
                v_g_procempno := PKG_BA_getPayData.fn_BA_getProcEmpInfo('EMPNO');
            end if;

            delete from BAUNACPDTL t where t.APNO like (v_i_paycode||'%') and t.UNACPDATE = v_i_chkdate and t.ISSUYM = v_i_issuym;
            delete from BAACPDTL t where t.APNO like (v_i_paycode||'%') and t.ACPDATE = v_i_chkdate and t.ISSUYM = v_i_issuym;
            update BACUTREC t set t.UNACPMK = '' where t.APNO like (v_i_paycode||'%') and t.CHKDATE = v_i_chkdate and t.MTESTMK = v_i_mtestmk and t.UNACPMK = 'Y';

            /*********************************************************************************************
             應收款立帳及應收已收執行動作步驟如下:
             步驟1.判斷該帳務資料是否已有應收款立帳資料。
             步驟2.依結果執行不同動作:
                   ◎資料來源:扣減記錄檔。
                   2.1.已立帳:更新應收款立帳的相關資料。若有應收款已收則新增應收已收的資料。
                   2.2.未立帳:新增應收款立帳的資料。若有應收款已收則新增應收已收的資料。
             步驟3.更新扣減記錄檔的註記欄位。

             2015/07/02 Add By ChungYu
             步驟4.當給付別為遺屬時，扣減老年及失能年金應收款需另外處理，新增應收已收的資料必須由事故者
                   分攤至每位遺屬上。

             2012/05/28 Add By ChungYu
             步驟5.讀取核定檔中全額收回案件的立帳資料，全額收回案另外處理，直接從核定檔新增應收款立帳的資料。
            *********************************************************************************************/
            for v_dataCur in c_dataCur Loop
                v_baunacpdtlid := 0;

                --普職互改立帳收回，普職註記及加職註記要轉為前期
                v_NLWKMK := '';
                v_ADWKMK := '';
                if (v_dataCur.NACHGMK is not null) then
                    if (substr(v_dataCur.NACHGMK,1,1) = '1') then
                        v_NLWKMK := '1';
                        v_ADWKMK := '1';
                    elsif (substr(v_dataCur.NACHGMK,1,1) = '2') then
                        v_NLWKMK := '2';
                        v_ADWKMK := '1';
                    elsif (substr(v_dataCur.NACHGMK,1,1) = '3') then
                        v_NLWKMK := '2';
                        v_ADWKMK := '2';
                    end if;
                else
                    v_NLWKMK := v_dataCur.NLWKMK;
                    v_ADWKMK := v_dataCur.ADWKMK;
                end if;

                --判斷該帳務資料是否已有應收款立帳資料
                if nvl(trim(v_dataCur.TYPEMK),' ')<>'0' or (nvl(trim(v_dataCur.TYPEMK),' ')='0' and nvl(trim(v_dataCur.RECSEQ),' ')<>' ') then
                    if nvl(trim(v_dataCur.TYPEMK),' ')<>'0' and nvl(trim(v_dataCur.RECSEQ),' ')=' ' then
                        select BAUNACPDTLID into v_baunacpdtlid
                          from BAUNACPDTL t
                         where t.APNO = v_dataCur.MAPNO
                           and t.SEQNO = v_dataCur.MSEQNO
                           and t.ISSUYM = v_dataCur.MISSUYM
                           and t.PAYYM = v_dataCur.MPAYYM
                           and t.PAYKIND = v_dataCur.DISPAYKIND;
                    else
                        v_baunacpdtlid := v_dataCur.RECSEQ;
                    end if;

                    v_g_upd_unacpCount := v_g_upd_unacpCount + 1;

                    --更新應收款立帳的相關資料。
                    update BAUNACPDTL t set t.RECREM = v_dataCur.RECREM
                                           ,t.ACTENDMK = v_dataCur.ACTENDMK
                                           ,t.PAYKIND = v_dataCur.DISPAYKIND
                                           ,t.MDCHKMK = 'C'
                                           ,t.TXDATE = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                      where t.BAUNACPDTLID = v_baunacpdtlid;

                elsif (nvl(trim(v_dataCur.TYPEMK),' ')='0' and nvl(trim(v_dataCur.RECSEQ),' ')=' ') then

                    --取應收款立帳檔的資料列編號
                    select (to_Char(Sysdate,'YYYYMMDD')||LPAD(to_Char(BAUNACPDTLID.NEXTVAL),12,'0'))
                      into v_baunacpdtlid from dual;

                    v_g_ins_unacpCount := v_g_ins_unacpCount + 1;

                    --新增應收款立帳的資料
                    insert into BAUNACPDTL (BAUNACPDTLID
                                           ,APNO
                                           ,SEQNO
                                           ,ISSUYM
                                           ,PAYYM
                                           ,BENIDS
                                           ,BENIDNNO
                                           ,PAYKIND
                                           ,STS
                                           ,TYPEMK
                                           ,RECAMT
                                           ,RECREM
                                           ,UNACPDATE
                                           ,ACTENDMK
                                           ,MDCHKMK
                                           ,TXDATE
                                           ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                           ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                          ) values (
                                            v_baunacpdtlid
                                           ,v_dataCur.MAPNO
                                           ,v_dataCur.MSEQNO
                                           ,v_dataCur.MISSUYM
                                           ,v_dataCur.MPAYYM
                                           ,v_dataCur.BENIDS
                                           ,v_dataCur.BENIDNNO
                                           ,v_dataCur.DISPAYKIND
                                           ,'1'
                                           ,'0'
                                           ,v_dataCur.RECAMT
                                           ,v_dataCur.RECREM
                                           ,v_i_chkdate
                                           ,v_dataCur.ACTENDMK
                                           ,'A'
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           ,v_NLWKMK
                                           ,v_ADWKMK
                                           ,v_dataCur.NACHGMK
                    );
                end if;

                if nvl(trim(v_dataCur.DISAMT),0)>0 then
                    v_g_acpCount := v_g_acpCount + 1;

                    --新增應收已收的資料
                    insert into BAACPDTL (BAACPDTLID
                                         ,BAUNACPDTLID
                                         ,BENIDS
                                         ,BENIDNNO
                                         ,TYPEMK
                                         ,RECAMT
                                         ,DISPAYKIND
                                         ,APNO
                                         ,SEQNO
                                         ,ISSUYM
                                         ,PAYYM
                                         ,TRANSACTIONID
                                         ,TRANSACTIONSEQ
                                         ,INSKDREGIVE
                                         ,ACPDATE
                                         ,PROCFUN
                                         ,PROCUSER
                                         ,PROCTIME
                                         ,UPDTIME
                                         ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                         ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                         ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                        ) values (
                                          (to_Char(Sysdate,'YYYYMMDD')||LPAD(to_Char(BAACPDTLID.NEXTVAL),12,'0'))
                                         ,v_baunacpdtlid
                                         ,v_dataCur.BENIDS
                                         ,v_dataCur.BENIDNNO
                                         ,deCode(v_dataCur.TYPEMK,'1','F','2','D','3','E','4','F','5','C',' ')
                                         ,v_dataCur.DISAMT
                                         ,v_dataCur.DISPAYKIND
                                         ,v_dataCur.APNO
                                         ,v_dataCur.SEQNO
                                         ,v_dataCur.ISSUYM
                                         ,v_dataCur.PAYYM
                                         ,v_dataCur.TRANSACTIONID
                                         ,v_dataCur.TRANSACTIONSEQ
                                         ,v_dataCur.INSKD
                                         ,v_i_chkdate
                                         ,'A'
                                         ,v_i_procempno
                                         ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                         ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                         ,v_NLWKMK
                                         ,v_ADWKMK
                                         ,v_dataCur.NACHGMK
                    );

                    --更新扣減記錄檔的註記欄位
                    update BACUTREC t set t.UNACPMK = 'Y'
                                    where t.BACUTRECID = v_dataCur.BACUTRECID;
                end if;
            end Loop;

            -- 2015/07/03 Add By ChungYu 遺屬扣減老年及失能年金應收款，需分攤至每位遺屬上。
            if ( v_i_paycode = 'S' ) then
               for v_dataSurCur in c_dataSurCur Loop
                   v_baunacpdtlid := 0;

                   --普職互改立帳收回，普職註記及加職註記要轉為前期
                   v_NLWKMK := '';
                   v_ADWKMK := '';
                   if (v_dataSurCur.NACHGMK is not null) then
                       if (substr(v_dataSurCur.NACHGMK,1,1) = '1') then
                           v_NLWKMK := '1';
                           v_ADWKMK := '1';
                       elsif (substr(v_dataSurCur.NACHGMK,1,1) = '2') then
                           v_NLWKMK := '2';
                           v_ADWKMK := '1';
                       elsif (substr(v_dataSurCur.NACHGMK,1,1) = '3') then
                           v_NLWKMK := '2';
                           v_ADWKMK := '2';
                       end if;
                   else
                       v_NLWKMK := v_dataSurCur.NLWKMK;
                       v_ADWKMK := v_dataSurCur.ADWKMK;
                   end if;

                --取得應收款立帳紀錄編號資料
                   if nvl(trim(v_dataSurCur.TYPEMK),' ')<>'0' and nvl(trim(v_dataSurCur.RECSEQ),' ')=' ' then
                       select BAUNACPDTLID into v_baunacpdtlid
                         from BAUNACPDTL t
                        where t.APNO = v_dataSurCur.MAPNO
                          and t.SEQNO = v_dataSurCur.MSEQNO
                          and t.ISSUYM = v_dataSurCur.MISSUYM
                          and t.PAYYM = v_dataSurCur.MPAYYM
                          and t.PAYKIND = v_dataSurCur.DISPAYKIND;
                   else
                       v_baunacpdtlid := v_dataSurCur.RECSEQ;
                   end if;

                    v_g_upd_unacpCount := v_g_upd_unacpCount + 1;

                    --更新應收款立帳的相關資料。
                    update BAUNACPDTL t set t.RECREM = v_dataSurCur.RECREM
                                           ,t.ACTENDMK = v_dataSurCur.ACTENDMK
                                           ,t.MDCHKMK = 'C'
                                           ,t.TXDATE = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                      where t.BAUNACPDTLID = v_baunacpdtlid;

                   if nvl(trim(v_dataSurCur.DISAMT),0)>0 then
                       v_g_acpCount := v_g_acpCount + 1;

                       --新增應收已收的資料
                       insert into BAACPDTL (BAACPDTLID
                                            ,BAUNACPDTLID
                                            ,BENIDS
                                            ,BENIDNNO
                                            ,TYPEMK
                                            ,RECAMT
                                            ,DISPAYKIND
                                            ,APNO
                                            ,SEQNO
                                            ,ISSUYM
                                            ,PAYYM
                                            ,TRANSACTIONID
                                            ,TRANSACTIONSEQ
                                            ,INSKDREGIVE
                                            ,ACPDATE
                                            ,PROCFUN
                                            ,PROCUSER
                                            ,PROCTIME
                                            ,UPDTIME
                                            ,NLWKMK
                                            ,ADWKMK
                                            ,NACHGMK
                                           ) values (
                                             (to_Char(Sysdate,'YYYYMMDD')||LPAD(to_Char(BAACPDTLID.NEXTVAL),12,'0'))
                                             ,v_baunacpdtlid
                                             ,v_dataSurCur.BENIDS
                                             ,v_dataSurCur.BENIDNNO
                                             ,deCode(v_dataSurCur.TYPEMK,'1','F','2','D','3','E','4','F','5','C',' ')
                                             ,v_dataSurCur.DISAMT
                                             ,v_dataSurCur.DISPAYKIND
                                             ,v_dataSurCur.APNO
                                             ,v_dataSurCur.SEQNO
                                             ,v_dataSurCur.ISSUYM
                                             ,v_dataSurCur.PAYYM
                                             ,v_dataSurCur.TRANSACTIONID
                                             ,v_dataSurCur.TRANSACTIONSEQ
                                             ,v_dataSurCur.INSKD
                                             ,v_i_chkdate
                                             ,'A'
                                             ,v_i_procempno
                                             ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                             ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                             ,v_NLWKMK
                                             ,v_ADWKMK
                                             ,v_dataSurCur.NACHGMK
                       );

                       --更新扣減記錄檔的註記欄位
                       update BACUTREC t set t.UNACPMK = 'Y'
                                       where t.BACUTRECID = v_dataSurCur.BACUTRECID;
                   end if;
               end Loop;
            end if;
            -- 2015/07/03 Add By ChungYu

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
               '新增、更新應收款立帳的資料與更新扣減紀錄擋註記欄位',replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

            -- 全額收回立帳 Add By ChungYu
            for v_dataRecCur in c_dataRecCur Loop
                v_baunacpdtlid := 0;
                v_bacutrecid := 0;
                v_typemk := '';
                v_disamt := 0;
                v_dispaykind := '';
                v_transactionid := '';
                v_transactionseq := '';
                v_inskd := '';
                v_actendmk := '';
                v_nachgmk := '';
                v_datarecnum :=  0;

                --普職互改立帳收回，普職註記及加職註記要轉為前期
                v_NLWKMK := '';
                v_ADWKMK := '';
                if (v_dataRecCur.NACHGMK is not null) then
                    if (substr(v_dataRecCur.NACHGMK,1,1) = '1') then
                        v_NLWKMK := '1';
                        v_ADWKMK := '1';
                    elsif (substr(v_dataRecCur.NACHGMK,1,1) = '2') then
                        v_NLWKMK := '2';
                        v_ADWKMK := '1';
                    elsif (substr(v_dataRecCur.NACHGMK,1,1) = '3') then
                        v_NLWKMK := '2';
                        v_ADWKMK := '2';
                    end if;
                else
                    v_NLWKMK := v_dataRecCur.NLWKMK;
                    v_ADWKMK := v_dataRecCur.ADWKMK;
                end if;

                --取應收款立帳檔的資料列編號
                select (to_Char(Sysdate,'YYYYMMDD')||LPAD(to_Char(BAUNACPDTLID.NEXTVAL),12,'0'))
                  into v_baunacpdtlid from dual;

                v_g_ins_unacpCount := v_g_ins_unacpCount + 1;

                --新增全額收回立帳應收款立帳的資料
                insert into BAUNACPDTL (BAUNACPDTLID
                                       ,APNO
                                       ,SEQNO
                                       ,ISSUYM
                                       ,PAYYM
                                       ,BENIDS
                                       ,BENIDNNO
                                       ,PAYKIND
                                       ,STS
                                       ,TYPEMK
                                       ,RECAMT
                                       ,RECREM
                                       ,UNACPDATE
                                       ,MDCHKMK
                                       ,TXDATE
                                       ,NLWKMK
                                       ,ADWKMK
                                       ,NACHGMK
                                      ) values (
                                        v_baunacpdtlid
                                       ,v_dataRecCur.APNO
                                       ,v_dataRecCur.SEQNO
                                       ,v_dataRecCur.ISSUYM
                                       ,v_dataRecCur.PAYYM
                                       ,v_dataRecCur.BENIDS
                                       ,v_dataRecCur.BENIDNNO
                                       ,v_dataRecCur.PAYKIND
                                       ,'1'
                                       ,'0'
                                       ,v_dataRecCur.RECAMT
                                       ,v_dataRecCur.RECAMT
                                       ,v_i_chkdate
                                       ,'A'
                                       ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                       ,v_NLWKMK
                                       ,v_ADWKMK
                                       ,v_dataRecCur.NACHGMK
                );

                select count(t.BACUTRECID)
                  into v_dataRecNum
                  from BACUTREC t
                 where t.MAPNO = v_dataRecCur.APNO           -- 2015/11/25 Modify By ChungYu 避免退匯沖抵會將應收及撥付給付年月均做應收回的情形
                   and t.MSEQNO = v_dataRecCur.SEQNO         -- 2015/11/25 Modify By ChungYu
                   and t.MISSUYM = v_dataRecCur.ISSUYM       -- 2015/11/25 Modify By ChungYu
                   and t.MPAYYM = v_dataRecCur.PAYYM         -- 2015/11/25 Modify By ChungYu
                   and t.DISPAYKIND = v_dataRecCur.PAYKIND
                   and t.MTESTMK = v_i_mtestmk
                   and t.CHKDATE = v_i_chkdate
                   and (nvl(trim(t.DISDATE), ' ') <> ' ' or
                       trim(t.DISDATE) is not null)
                   and (nvl(trim(t.DISAMT), 0)) > 0;

                if(v_dataRecNum > 0)then
                            --add by Angela 20140523 全額立帳若已收回，則需再回寫已收檔資料
                          select t.BACUTRECID
                              ,t.DISPAYKIND
                              ,t.TYPEMK
                              ,t.DISAMT
                              ,t.TRANSACTIONID
                              ,t.TRANSACTIONSEQ
                              ,t.INSKD
                              ,t.ACTENDMK
                              ,t.NACHGMK
                              ,t.APNO             -- 2015/11/25 Add By ChungYu
                              ,t.SEQNO            -- 2015/11/25 Add By ChungYu
                              ,t.ISSUYM           -- 2015/11/25 Add By ChungYu
                              ,t.PAYYM            -- 2015/11/25 Add By ChungYu
                            into v_bacutrecid
                              ,v_dispaykind
                              ,v_typemk
                              ,v_disamt
                              ,v_transactionid
                              ,v_transactionseq
                              ,v_inskd
                              ,v_actendmk
                              ,v_nachgmk
                              ,v_DisApno          -- 2015/11/25 Add By ChungYu
                              ,v_DisSeqno         -- 2015/11/25 Add By ChungYu
                              ,v_DisIssuym        -- 2015/11/25 Add By ChungYu
                              ,v_DisPayym         -- 2015/11/25 Add By ChungYu
                            from BACUTREC t
                           where t.MAPNO = v_dataRecCur.APNO
                             and t.MSEQNO = v_dataRecCur.SEQNO
                             and t.MISSUYM = v_dataRecCur.ISSUYM
                             and t.MPAYYM = v_dataRecCur.PAYYM
                             and t.DISPAYKIND = v_dataRecCur.PAYKIND
                             and t.MTESTMK = v_i_mtestmk
                             and t.CHKDATE = v_i_chkdate
                             and (nvl(trim(t.DISDATE),' ')<>' ' or trim(t.DISDATE) is not null)
                             and (nvl(trim(t.DISAMT),0))>0;

                          --更新扣減記錄檔的註記欄位
                          update BACUTREC t set t.UNACPMK = 'Y'
                                  where t.BACUTRECID = v_bacutrecid;

                          if nvl(trim(v_disamt),0)>0 then
                            v_g_acpCount := v_g_acpCount + 1;

                            --新增應收已收的資料
                            insert into BAACPDTL (BAACPDTLID
                                       ,BAUNACPDTLID
                                       ,BENIDS
                                       ,BENIDNNO
                                       ,TYPEMK
                                       ,RECAMT
                                       ,DISPAYKIND
                                       ,APNO
                                       ,SEQNO
                                       ,ISSUYM
                                       ,PAYYM
                                       ,TRANSACTIONID
                                       ,TRANSACTIONSEQ
                                       ,INSKDREGIVE
                                       ,ACPDATE
                                       ,PROCFUN
                                       ,PROCUSER
                                       ,PROCTIME
                                       ,UPDTIME
                                       ,NLWKMK
                                       ,ADWKMK
                                       ,NACHGMK
                                      ) values (
                                        (to_Char(Sysdate,'YYYYMMDD')||LPAD(to_Char(BAACPDTLID.NEXTVAL),12,'0'))
                                       ,v_baunacpdtlid
                                       ,v_dataRecCur.BENIDS
                                       ,v_dataRecCur.BENIDNNO
                                       ,deCode(v_typemk,'1','F','2','D','3','E','4','F','5','C',' ')
                                       ,v_disamt
                                       ,v_dispaykind
                                       ,v_DisApno
                                       ,v_DisSeqno
                                       ,v_DisIssuym
                                       ,v_DisPayym
                                       ,v_transactionid
                                       ,v_transactionseq
                                       ,v_inskd
                                       ,v_i_chkdate
                                       ,'A'
                                       ,v_i_procempno
                                       ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                       ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                       ,v_NLWKMK
                                       ,v_ADWKMK
                                       ,v_nachgmk
                            );

                            --更新應收款立帳的相關資料。
                            update BAUNACPDTL t set t.RECREM = nvl(to_Number(t.RECREM),0)-nvl(to_Number(v_disamt),0)
                                         ,t.ACTENDMK = v_actendmk
                                         ,t.PAYKIND = v_dispaykind
                                         ,t.MDCHKMK = 'C'
                                         ,t.TXDATE = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                      where t.BAUNACPDTLID = v_baunacpdtlid;
                            end if;
                        end if;
            end Loop;

            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'0',
               '全額收回立帳處理完成，新增應收未收明細:'||v_g_ins_unacpCount||'筆'||'異動應收未收明細:'||v_g_upd_unacpCount||'筆'||'新增應收已收明細:'||v_g_acpCount||'筆',
               replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

               dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>新增應收未收明細:'||v_g_ins_unacpCount||'筆'||'    異動應收未收明細:'||v_g_upd_unacpCount||'筆'||'    新增應收已收明細:'||v_g_acpCount||'筆');
               v_o_flag := v_g_flag;
        exception
            when others
                then
                    v_g_flag := '1';
                    v_o_flag := v_g_flag;
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);

                    --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                       RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

        end;
    --procedure sp_BA_MonthApprove End

    /*
    \********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_ProcACPDtl.sp_BA_Refundment
        PURPOSE:         產生應收款立帳及應收款已收相關資料(for 媒體回押時,退匯原因為"止付")

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --核定日期
                         v_i_procempno     (varChar2)       --執行作業人員員工編號

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

    ********************************************************************************\
    procedure sp_BA_Refundment (
        v_i_issuym           in      varChar2,
        v_i_paycode          in      varChar2,
        v_i_chkdate          in      varChar2,
        v_i_procempno        in      varChar2
    ) is
        v_baunacpdtlid       Number;

        --查詢退匯資料檔中,退匯原因="止付"的相關資料
        Cursor c_dataCur is
            select t1.APNO
                  ,t1.SEQNO
                  ,t1.ISSUYM
                  ,t1.PAYYM
                  ,t1.BENIDS
                  ,t1.BENIDN
                  ,t1.ISSUKIND as PAYKIND
                  ,t1.REMITAMT as RECAMT
              from BAPFLBAC t1
             where t1.APNO Like (v_i_paycode||'%')
               and t1.BRISSUYM = v_i_issuym
               and t1.BRCHKDATE = v_i_chkdate
               and t1.BRMK = '1';

        begin
            v_g_ProgName := 'PKG_BA_ProcACPDtl.sp_BA_Refundment';
            v_g_errMsg := '';
            v_g_rowCount := 0;
            v_g_acpCount := 0;
            v_g_ins_unacpCount := 0;
            v_g_procempno := ' ';
            v_baunacpdtlid := 0;

            --若無傳入執行作業人員員工編號,則取出參數檔的設定
            if nvl(trim(v_i_procempno),' ')<>' ' then
                v_g_procempno := v_i_procempno;
            else
                v_g_procempno := PKG_BA_getPayData.fn_BA_getProcEmpInfo('EMPNO');
            end if;

            delete from BAUNACPDTL t where t.UNACPDATE = v_i_chkdate and t.ISSUYM = v_i_issuym;
            delete from BAACPDTL t where t.ACPDATE = v_i_chkdate and t.ISSUYM = v_i_issuym;

            \*********************************************************************************************
             應收款立帳及應收已收執行動作步驟如下:
             步驟1.直接新增應收款立帳的資料及應收已收的資料。
                   ◎資料來源:退匯資料檔。
            *********************************************************************************************\
            for v_dataCur in c_dataCur Loop
                --取應收款立帳檔的資料列編號
                select (to_Char(Sysdate,'YYYYMMDD')||LPAD(to_Char(BAUNACPDTLID.NEXTVAL),12,'0'))
                  into v_baunacpdtlid from dual;

                v_g_ins_unacpCount := v_g_ins_unacpCount + 1;

                --新增應收款立帳的資料
                insert into BAUNACPDTL (BAUNACPDTLID
                                       ,APNO
                                       ,SEQNO
                                       ,ISSUYM
                                       ,PAYYM
                                       ,BENIDS
                                       ,BENIDNNO
                                       ,PAYKIND
                                       ,STS
                                       ,TYPEMK
                                       ,RECAMT
                                       ,RECREM
                                       ,UNACPDATE
                                       ,ACTENDMK
                                       ,MDCHKMK
                                       ,TXDATE
                                      ) values (
                                        v_baunacpdtlid
                                       ,v_dataCur.APNO
                                       ,v_dataCur.SEQNO
                                       ,v_dataCur.ISSUYM
                                       ,v_dataCur.PAYYM
                                       ,v_dataCur.BENIDS
                                       ,v_dataCur.BENIDN
                                       ,v_dataCur.PAYKIND
                                       ,'1'
                                       ,'C'
                                       ,v_dataCur.RECAMT
                                       ,0
                                       ,v_i_chkdate
                                       ,'9'
                                       ,'A'
                                       ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                );

                v_g_acpCount := v_g_acpCount + 1;

                --新增應收已收的資料
                insert into BAACPDTL (BAACPDTLID
                                     ,BAUNACPDTLID
                                     ,BENIDS
                                     ,BENIDNNO
                                     ,TYPEMK
                                     ,RECAMT
                                     ,DISPAYKIND
                                     ,APNO
                                     ,SEQNO
                                     ,ISSUYM
                                     ,PAYYM
                                     ,ACPDATE
                                     ,PROCFUN
                                     ,PROCUSER
                                     ,PROCTIME
                                     ,UPDTIME
                                    ) values (
                                      (to_Char(Sysdate,'YYYYMMDD')||LPAD(to_Char(BAACPDTLID.NEXTVAL),12,'0'))
                                     ,v_baunacpdtlid
                                     ,v_dataCur.BENIDS
                                     ,v_dataCur.BENIDN
                                     ,'D'
                                     ,v_dataCur.RECAMT
                                     ,v_dataCur.PAYKIND
                                     ,v_dataCur.APNO
                                     ,v_dataCur.SEQNO
                                     ,v_dataCur.ISSUYM
                                     ,v_dataCur.PAYYM
                                     ,v_i_chkdate
                                     ,'A'
                                     ,v_i_procempno
                                     ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                     ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                );
            end Loop;
            dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>新增應收未收明細:'||v_g_ins_unacpCount||'筆'||'    新增應收已收明細:'||v_g_acpCount||'筆');
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
        end;
    --procedure sp_BA_Refundment End
    */

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_ProcACPDtl.sp_BA_ArtificialPay
        PURPOSE:         更新應收款立帳及應收款已收相關資料(for 人工核付)

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --人工核定日期
                        *v_i_paydate       (varChar2)       --人工核付日期
                         v_i_procempno     (varChar2)       --執行作業人員員工編號

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2010/02/23  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_ArtificialPay (
        v_i_issuym           in      varChar2,
        v_i_paycode          in      varChar2,
        v_i_chkdate          in      varChar2,
        v_i_paydate          in      varChar2,
        v_i_procempno        in      varChar2
    ) is
        v_recamt             Number;

        --查詢人工核付的資料中,已立帳的應收未收款(退匯收回)資料
        Cursor c_dataCur_1 is
            select t.APNO
                  ,t.SEQNO
                  ,t.ISSUYM
                  ,t.PAYYM
                  ,t.RECAMT
                  ,t.BAUNACPDTLID
                  ,t.PAYKIND
                  ,t.NLWKMK                                                                               --普職註記
                  ,t.ADWKMK                                                                               --加職註記
                  ,t.NACHGMK                                                                              --普職互改註記
              from BAUNACPDTL t
             where t.APNO Like (v_i_paycode||'%')
               and t.ISSUYM = v_i_issuym
               and t.UNACPDATE = v_i_chkdate
               and t.TYPEMK = '0'
               and t.STS = '1'
               and t.ACTENDMK <> '9'
               and t.RECAMT = t.RECREM;

        --查詢可列應收已收的退改匯資料(條件:受理編號、受款人序、給付年月)
        Cursor c_dataCur_2(v_apno varChar2,v_seqno varChar2,v_payym varChar2) is
            select t.APNO
                  ,t.SEQNO
                  ,t.PAYYM
                  ,t.REMITAMT as RECAMT
                  ,t.TRANSACTIONID
                  ,t.TRANSACTIONSEQ
                  ,t.INSKD
                  ,t.BENIDS
                  ,t.BENIDN
              from BAREGIVEDTL t
             where t.APNO = v_apno
               and t.SEQNO = v_seqno
               and t.PAYYM = v_payym
               and t.MK in ('1','2')
               and nvl(trim(t.WORKMK),' ') <> 'Y'
               and (trim(t.AFPAYDATE) is null or nvl(trim(t.AFPAYDATE),' ')=' ');

        begin
            v_g_ProgName := 'PKG_BA_ProcACPDtl.sp_BA_ArtificialPay';
            v_g_errMsg := '';
            v_g_rowCount := 0;
            v_g_acpCount := 0;
            v_g_ins_unacpCount := 0;
            v_g_procempno := ' ';
            v_recamt := 0;

            --若無傳入執行作業人員員工編號,則取出參數檔的設定
            if nvl(trim(v_i_procempno),' ')<>' ' then
                v_g_procempno := v_i_procempno;
            else
                v_g_procempno := PKG_BA_getPayData.fn_BA_getProcEmpInfo('EMPNO');
            end if;

            delete from BAACPDTL t where t.ACPDATE = v_i_chkdate and t.ISSUYM = v_i_issuym;

            /*********************************************************************************************
             應收已收執行動作步驟如下:
             步驟1.查詢可列應收已收的退改匯資料。
                   ◎資料來源:改匯資料檔。
            *********************************************************************************************/
            for v_dataCur_1 in c_dataCur_1 Loop
                v_recamt := 0;

                for v_dataCur_2 in c_dataCur_2(v_dataCur_1.APNO,v_dataCur_1.SEQNO,v_dataCur_1.PAYYM) Loop
                    v_recamt := v_recamt + v_dataCur_2.RECAMT;
                end Loop;

                if v_dataCur_1.RECAMT = v_recamt then
                    for v_dataCur_2 in c_dataCur_2(v_dataCur_1.APNO,v_dataCur_1.SEQNO,v_dataCur_1.PAYYM) Loop
                        --新增應收已收的資料
                        insert into BAACPDTL (BAACPDTLID
                                             ,BAUNACPDTLID
                                             ,APNO
                                             ,SEQNO
                                             ,ISSUYM
                                             ,PAYYM
                                             ,DISPAYKIND
                                             ,TYPEMK
                                             ,RECAMT
                                             ,TRANSACTIONID
                                             ,TRANSACTIONSEQ
                                             ,INSKDREGIVE
                                             ,BENIDS
                                             ,BENIDNNO
                                             ,ACPDATE
                                             ,PROCFUN
                                             ,PROCUSER
                                             ,PROCTIME
                                             ,UPDTIME
                                             ,NLWKMK                                   -- 2012/02/25 Add by Chungyu
                                             ,ADWKMK                                   -- 2012/02/25 Add by Chungyu
                                             ,NACHGMK                                  -- 2012/02/25 Add by Chungyu
                                            ) values (
                                              (to_Char(Sysdate,'YYYYMMDD')||LPAD(to_Char(BAACPDTLID.NEXTVAL),12,'0'))
                                             ,v_dataCur_1.BAUNACPDTLID
                                             ,v_dataCur_1.APNO
                                             ,v_dataCur_1.SEQNO
                                             ,v_dataCur_1.ISSUYM
                                             ,v_dataCur_1.PAYYM
                                             ,v_dataCur_1.PAYKIND
                                             ,'0'
                                             ,v_dataCur_2.RECAMT
                                             ,v_dataCur_2.TRANSACTIONID
                                             ,v_dataCur_2.TRANSACTIONSEQ
                                             ,v_dataCur_2.INSKD
                                             ,v_dataCur_2.BENIDS
                                             ,v_dataCur_2.BENIDN
                                             ,v_i_chkdate
                                             ,'A'
                                             ,v_i_procempno
                                             ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                             ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                             ,v_dataCur_1.NLWKMK
                                             ,v_dataCur_1.ADWKMK
                                             ,v_dataCur_1.NACHGMK
                        );
                    end Loop;

                    --更新應收款立帳的相關資料。
                    update BAUNACPDTL t set t.RECREM = 0
                                           ,t.ACTENDMK = '9'
                                           ,t.PAYKIND = v_dataCur_1.PAYKIND
                                           ,t.MDCHKMK = 'C'
                                           ,t.TXDATE = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                      where t.BAUNACPDTLID = v_dataCur_1.BAUNACPDTLID;

                    --更新改匯記錄檔中,給付收回資料的核付相關欄位
                    Update BAREGIVEDTL t set t.AFPAYDATE = v_i_paydate
                                            ,t.AFMK = '4'
                                            ,t.WORKMK = 'Y'
                                            ,t.RECHKDATE = v_i_paydate
                                            ,t.RECHKMAN = v_i_procempno
                                            ,t.EXEMAN = v_i_procempno
                                            ,t.EXEDATE = to_Char(Sysdate,'YYYYMMDD')
                                            ,t.CPRNDATE = to_Char(Sysdate,'YYYYMMDD')
                                            ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                    where t.APNO = v_dataCur_1.APNO
                      and t.SEQNO = v_dataCur_1.SEQNO
                      and t.PAYYM = v_dataCur_1.PAYYM
                      and nvl(trim(t.WORKMK),' ') <> 'Y'
                      and ((t.MK = '1' and nvl(trim(t.AFCHKDATE),' ')<>' ' and trim(t.AFCHKDATE) is not null)
                        or (t.MK = '2' and (nvl(trim(t.AFPAYDATE),' ')=' ' or trim(t.AFPAYDATE) is null))
                        or (t.MK = '4' and (nvl(trim(t.AFPAYDATE),' ')=' ' or trim(t.AFPAYDATE) is null)));

                    --更新退匯記錄檔中的核付相關欄位
                    Update BAPFLBAC t set t.AFMK = '4'
                                         ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.APNO = v_dataCur_1.APNO
                       and t.SEQNO = v_dataCur_1.SEQNO
                       and t.PAYYM = v_dataCur_1.PAYYM
                       and t.AFMK in ('1','2');

                    --更新給付核定檔中,給付沖銷資料的核付相關欄位
                    Update BADAPR t set t.REMITDATE = v_i_paydate
                                       ,t.REMITMK = '4'
                                       ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                     where t.APNO = v_dataCur_1.APNO
                       and t.SEQNO = v_dataCur_1.SEQNO
                       and t.ISSUYM = v_dataCur_1.ISSUYM
                       and t.PAYYM = v_dataCur_1.PAYYM
                       and t.MTESTMK = 'F'
                       and t.MANCHKMK = 'Y'
                       --and (t.ACCEPTMK = 'Y' or nvl(trim(t.ACCEPTMK),' ') = ' ')    -Mark by Angela 20190927
                       and (t.CHKDATE is not null and nvl(trim(t.CHKDATE),' ')<>' ')
                       and t.REMITMK = '2'
                       and (t.REMITDATE is not null and nvl(trim(t.REMITDATE),' ')<>' ')
                       and (t.APLPAYMK is not null and nvl(trim(t.APLPAYMK),' ')<>' ')
                       and (t.APLPAYDATE is not null and nvl(trim(t.APLPAYDATE),' ')<>' ');

                    PKG_BA_ProcCashier.sp_BA_expSingleRec(v_dataCur_1.APNO,v_dataCur_1.SEQNO,v_i_issuym,v_dataCur_1.PAYYM,v_i_paydate);
                    v_g_acpCount := v_g_acpCount + 1;

                end if;
            end Loop;
            dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>新增應收已收明細:'||v_g_acpCount||'筆');
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>'||v_g_errMsg);
        end;
    --procedure sp_BA_ArtificialPay End
End;
/