create or replace package ba.PKG_BA_UpdAnnuamt
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            SP_BA_UpdAnnuamt
    PURPOSE:         更新已領年金金額資料

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2009/11/30  Evelyn Hsu    Created this procedure.


    NOTES:
    1.各 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/

authid definer is
    v_g_errMsg                   varChar2(2000);

    --已領金額更新
    procedure SP_BA_UpdAnnuamt(
        v_i_issuym         in      varChar2,
        v_i_paycode        in      varChar2
    );

end PKG_BA_UpdAnnuamt;
/

create or replace package body ba.PKG_BA_UpdAnnuamt
is
    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            SP_BA_UpdAnnuamt
        PURPOSE:         更新已領年金金額資料

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/11/30  Evelyn Hsu    Created this procedure.
        1.2   2017/11/08  ChugnYU       加入更新老年差額金
        1.3   2022/12/15  William       依babaweb-53 調整
        1.4   2023/03/30  William       依babaweb-71修改
                                        老年差額金=一次給付金額 – 老年累計已領年金金額 – 失能累計已領年金金額

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure SP_BA_UpdAnnuamt(
        v_i_issuym         in      varChar2,
        v_i_paycode        in      varChar2
    )
    is
        v_jobno                    mmjoblog.job_no%TYPE;
        v_starttime                TIMESTAMP;             --開始時間
        v_rowCount                 Number ;
        v_genFlag                  Number;
        v_annuAMT                  Number;
        v_dabannuamt               Number;
        v_marginAMT                Number;    

        --查詢待更新核付資料的受理案件資料
        Cursor c_dataCur_1 is
            select t.APNO              --受理編號
                  ,t.SEQNO             --受款人序
                  ,t.ONCEPAYMK         -- 一次給付符合註記  2017/11/08 Add By ChungUu
                  ,t.ONCEISSUEAMT      -- 一次給付金額      2017/11/08 Add By ChungUu
              from BAAPPBASE t
             where t.APNO like (v_i_paycode||'%')
               and t.SEQNO = '0000'
               and t.PAYKIND in ('35','36','38','45','48','55','56','59')
               --2022/12/15 Modify by William
               and t.CASETYP in ('2','3','4','6')
               -- 2017/10/24 Modify By ChugnYu
               and t.PROCSTAT >= '10'
               and t.PROCSTAT < '99'
               -- and t.PROCSTAT in ('50','80','90')
               -- 2017/10/24 Modify By ChugnYu
               and trim(t.CASEMK) is null
               and t.PROCSTAT <> '99'
               and t.APNO in (select distinct t1.APNO from BADAPR t1
                               where t1.APNO like (v_i_paycode||'%')
                                 and t1.SEQNO = '0000'
                                 and t1.MTESTMK = 'F');

        --查詢已領年金金額資料(核定年月當期的+過去已領+補發-收回)
        Cursor c_AnnuAmtCur(v_apno varChar2,v_seqno varChar2) is
            select nvl(Sum(ANNUAMT),0) ANNUAMT                             --核定年月當期總核定金額
              from (select nvl(Sum(t1.BEFISSUEAMT),0) ANNUAMT
                     from BADAPR t1
                    where t1.APNO = v_apno
                      and t1.SEQNO = v_seqno
                      and t1.ISSUYM = v_i_issuym
                      and t1.MTESTMK = 'F'
                      and nvl(trim(t1.SUPRECMK),' ') = ' '
                      and t1.MANCHKMK = 'Y'
                      --and (t1.ACCEPTMK = 'Y' or nvl(trim(t1.ACCEPTMK),' ') = ' ')
                      and t1.APLPAYMK = '3'
                      and (t1.PAYKIND <> '37' and t1.PAYKIND <> '49')     -- 2017/10/24 Modify By ChugnYu
                    union all
                   select nvl(Sum(t1.BEFISSUEAMT),0) ANNUAMT              --已領年金金額
                     from BADAPR t1
                    where t1.APNO = v_apno
                      and t1.SEQNO = v_seqno
                      and t1.ISSUYM < v_i_issuym
                      and t1.MTESTMK = 'F'
                      and nvl(trim(t1.SUPRECMK),' ') = ' '
                      and t1.MANCHKMK = 'Y'
                      --and (t1.ACCEPTMK = 'Y' or nvl(trim(t1.ACCEPTMK),' ') = ' ')
                      and t1.APLPAYMK = '3'
                      and (t1.PAYKIND <> '37' and t1.PAYKIND <> '49')     -- 2017/10/24 Modify By ChugnYu
                    union all
                   select nvl(Sum(t1.SUPAMT),0) ANNUAMT                   --補發金額(含核定年月當期核定的資料)
                     from BADAPR t1
                    where t1.APNO = v_apno
                      and t1.SEQNO = v_seqno
                      and t1.ISSUYM <= v_i_issuym
                      and t1.MTESTMK = 'F'
                      and t1.ISSUYM > t1.PAYYM
                      and t1.SUPRECMK = 'C'
                      and t1.APLPAYMK = '3'
                      and t1.MANCHKMK = 'Y'
                      and (t1.PAYKIND <> '37' and t1.PAYKIND <> '49')     -- 2017/10/24 Modify By ChugnYu
                      --and (t1.ACCEPTMK = 'Y' or nvl(trim(t1.ACCEPTMK),' ') = ' ')
                    union all
                   select nvl(Sum(t1.SUPAMT),0) ANNUAMT                   --補發金額(含核定年月當期核定的資料)
                     from BADAPR t1                                       --For 老年，因事故者去逝年金補發至眷屬
                    where substr(t1.APNO, 1, 1) = 'L'
                      and t1.APNO = v_apno
                      and t1.SEQNO <> '0000'
                      and t1.benevtrel not in ('F', 'Z')
                      and t1.ISSUYM <= v_i_issuym
                      and t1.MTESTMK = 'F'
                      and t1.ISSUYM > t1.PAYYM
                      and t1.SUPRECMK = 'C'
                      and t1.APLPAYMK = '3'
                      and t1.MANCHKMK = 'Y'
                      and (t1.PAYKIND <> '37' and t1.PAYKIND <> '49')     -- 2017/10/24 Modify By ChugnYu
                    union all
                   select (0-nvl(Sum(t1.RECAMT),0)) ANNUAMT               --收回金額(含核定年月當期核定的資料)
                     from BADAPR t1
                    where t1.APNO = v_apno
                      and t1.SEQNO = v_seqno
                      and t1.ISSUYM <= v_i_issuym
                      and t1.MTESTMK = 'F'
                      and t1.ISSUYM > t1.PAYYM
                      and t1.SUPRECMK = 'D'
                      and (t1.MANCHKMK is not null and nvl(trim(t1.MANCHKMK),' ') <> ' ')
                      and t1.APLPAYMK = '3'
                      and (t1.PAYKIND <> '37' and t1.PAYKIND <> '49')     -- 2017/10/24 Modify By ChugnYu
                  );
-- ============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
        PROCEDURE get_jobno
        IS
        BEGIN
           SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','')
                  into v_jobno
           FROM DUAL;
        END get_jobno;
-- ============================================================================

--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------
        begin
            v_genFlag  :=0;
            v_rowCount :=0;
            --寫入開始LOG --(S)
            get_jobno;
            v_starttime := SYSTIMESTAMP;
            SP_BA_RECJOBLOG (p_job_no => v_jobno,
                            p_job_id => 'SP_BA_UpdAnnuamt',
                            p_step   => '更新已領年金金額資料',
                            p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');
            --寫入開始LOG --(E)
            for v_dataCur_1 in c_dataCur_1 Loop
                v_genFlag := 1;
                v_rowCount := v_rowCount + 1;
                v_annuAMT   := 0;
                v_marginAMT := null;

                --查詢已領年金金額資料
                for v_AnnuAmtCur in c_AnnuAmtCur(v_dataCur_1.APNO,v_dataCur_1.SEQNO) Loop
                    v_annuAMT := v_AnnuAmtCur.ANNUAMT;
                end Loop;

                --更新給付核定檔的核付相關欄位
                Update BADAPR t set t.ANNUAMT = v_annuAMT
                                   ,t.PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                 where t.APNO = v_dataCur_1.APNO
                   and t.SEQNO = v_dataCur_1.SEQNO
                   and t.ISSUYM = v_i_issuym
                   and t.MTESTMK = 'F';
              
                --  2017/11/08 加入更新老年差額金欄位
                if ( v_i_paycode = 'L' ) then
                    --v1.4 20230330 加入失能金額計算
                    begin
                       select nvl(t1.DABANNUAMT,0) into v_dabannuamt
                         from baappbase t1
                       where  t1.APNO = v_dataCur_1.APNO
                        and t1.SEQNO = '0000' ;
                    Exception when no_data_found then
                         v_dabannuamt :=0;
                    end;

                    if ((v_dataCur_1.ONCEPAYMK <> 'N') And ((v_dataCur_1.ONCEISSUEAMT-v_annuAMT-v_dabannuamt)>0)) then 
                         v_marginAMT := v_dataCur_1.ONCEISSUEAMT-v_annuAMT-v_dabannuamt;
                    else
                         v_marginAMT := 0;
                    end if;
                    
                end if;    
                --  2017/11/08 加入更新老年差額金欄位

                --更新給付主檔的核付相關欄位
                Update BAAPPBASE t set t.ANNUAMT   = v_annuAMT
                                      ,t.MARGINAMT = v_marginAMT
                                      ,t.UPDTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                 where t.APNO = v_dataCur_1.APNO
                   and t.SEQNO = v_dataCur_1.SEQNO;

                if mod(v_rowCount,500)=0 then
                    commit;
                end if;
            end Loop;

            commit;
            if v_genFlag>0 then
               dbms_output.put_line('  SP_BA_UpdAnnuamt-------------------------------->>更新'||v_i_paycode||'年金已領年金金額資料筆數-[ '||v_rowCount||' ]筆資料已更新');
            else
               dbms_output.put_line('  SP_BA_UpdAnnuamt-------------------------------->>更新'||v_i_paycode||'年金已領年金金額資料筆數-[ '||v_rowCount||' ]-NoData');
            end if;
            SP_BA_RECJOBLOG (p_job_no      => v_jobno,
                            p_job_id       => 'SP_BA_UpdAnnuamt',
                            p_step         => '更新已領年金金額資料',
                            p_proc_count   => v_rowCount,
                            p_memo         => '更新'||v_i_paycode ||'年金已領年金金額資料結束時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');
    exception
        when others
            then
                rollback;
                v_g_errMsg := SQLErrm;
                dbms_output.put_line('**Err:PKG_BA_UpdAnnuamt.sp_BA_UpdAnnuamt-------->>'||v_g_errMsg);
                SP_BA_RECJOBLOG(p_job_no => v_jobno,
                     p_job_id => 'SP_BA_UpdAnnuamt',
                     p_step   => '更新已領年金金額資料錯誤',
                     p_memo   => '錯誤代碼：'||SQLCODE||CHR(10)||
                                 '錯誤訊息：'||SQLERRM||CHR(10)||
                                 '錯誤軌跡：'||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    end SP_BA_UpdAnnuamt;
    --procedure sp_BA_UpdAnnuamt End

end PKG_BA_UpdAnnuamt;
/
