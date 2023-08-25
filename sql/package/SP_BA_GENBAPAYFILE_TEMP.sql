create or replace procedure ba.SP_BA_GENBAPAYFILE_TEMP
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            SP_BA_GENBAPAYFILE_TEMP
    PURPOSE:         產生月核付媒體檔及相關月核付報表資料

    PARAMETER(IN):  *v_i_issutyp       (varChar2)       --核付處理類別
                     v_i_issuym        (varChar2)       --核定年月
                     v_i_chkdate       (varChar2)       --核定日期 2014/10/16 Add by Zehua
                     v_i_paycode       (varChar2)       --給付別
                     v_i_mtestmk       (varChar2)       --處理註記
                     v_i_procempno     (varChar2)       --執行作業人員員工編號
                     v_i_procdeptid    (varChar2)       --執行作業人員單位代碼
                     v_i_procip        (varChar2)       --執行作業人員IP
                     v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                ：2為第二次出媒體(僅出失能國並勞36案部份)
                                                        --2011/12/08 Add by ChungYu
                     v_i_bajobid       (varChar2)       --線上批次id  2014/10/16 Add by Zehua
                     v_i_proctype      (varChar2)       --處理類型(1:線上出媒體 5:批次出媒體) 2014/12/19 Add by Zehua

    PARAMETER(OUT):
                     v_o_flag          (varChar2)       --傳出值 0-成功；1-失敗
    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2009/02/10  Angela Wu    Created this procedure.
    1.1   2009/04/07  Angela Wu    Modify.
    2.0   2009/07/01  Angela Wu    Modify.
    2.1   2009/10/08  Angela Wu    Modify.
    2.2   2010/11/02  Angela Wu    Modify.

    3.0   2012/02/08  ChungYu      加入失能遺屬上線出帳及二次出帳的部份(勞並國_36案).
    4.0   2016/05/03  Justin Yu    為了讓前端WEB呼叫,因此執行完畢後直接COMMIT.
    5.0   2016/10/12  ChungYu      加入月核付出帳時,才去撈主檔及核定檔待核付資料,改匯時並不撈;
                                   若發生錯誤則跳離出帳程序.(作廢)

    6.0   2017/09/13  ChungYu      加入遺屬計息存儲核定清單及清冊.

    NOTES:
    1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。
    2.v_i_golivedate=核付案件申請期限日期。當審核、決行上線後,此日期不需再傳入。

********************************************************************************/
(
    v_i_issutyp              in      varChar2,
    v_i_issuym               in      varChar2,
    v_i_chkdate              in      varchar2,
    v_i_paycode              in      varChar2,
    v_i_mtestmk              in      varChar2,
    v_i_procempno            in      varChar2,
    v_i_procdeptid           in      varChar2,
    v_i_procip               in      varChar2,
    v_i_payseqno             in      varChar2,      -- Add By ChungYu  //2012/02/08
    v_i_proctype             in      varChar2,      -- Add By ZeHua    //2014/12/29
    v_i_bajobid              in      varchar2       -- Add By ZeHua    //2014/07/22
    --v_o_flag                 out     varChar2       -- Add By ZeHua    //2014/07/22
)
authid definer is
    v_ProgName               varChar2(200) := 'SP_BA_GENBAPAYFILE_TEMP';
    v_errMsg                 varChar2(2000);
    v_issuym                 varChar2(6);
    v_paycode                varChar2(1);
    v_chkdate                varChar2(8);
    v_paydate                varChar2(8);
    v_proctime               varChar2(14);
    v_mtestmk                varChar2(1);
    v_procempno              varChar2(10);
    v_procdeptid             varChar2(5);
    v_procip                 varChar2(50);
    v_rowCount               Number;
    v_proctype               varChar2(2);
    v_payseqno               varChar2(1);           -- Add By ChungYu 2012/02/08
    v_tempstr                varChar2(30);
    v_cprndt                 varChar2(8) := to_Char(Sysdate,'YYYYMMDD');
    v_g_flag                 varchar2(1);
    v_o_flag                 varchar2(1);
    v_jobno                  mmjoblog.job_no%TYPE;
    v_starttime              TIMESTAMP;             --開始時間

    --查詢待轉出媒體檔的給付別
    Cursor c_dataCur is
        select PARAMCODE from BAPARAM t
         where t.PARAMGROUP = 'PAYCODE'
           and t.PARAMUSEMK = 'Y'
           and nvl(trim(v_i_paycode),PARAMCODE)=nvl(v_i_paycode,PARAMCODE)
           and deCode(nvl(trim(v_i_paycode),' '),' ','1',PARAMCODE) = deCode(nvl(trim(v_i_paycode),' '),' ','1',trim(v_i_paycode))
         order by t.PARAMSEQ;

-- ============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
    PROCEDURE get_jobno
    IS
    BEGIN
       SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_jobno FROM DUAL;
    END get_jobno;

begin
    v_errMsg := ' ';
    v_issuym := ' ';
    v_paycode := ' ';
    v_mtestmk := 'F';
    v_rowCount := 0;
    v_payseqno := 1;         -- Add By ChungYu 2012/02/08
    v_g_flag := '0';         -- Add By Zehua 2014/10/16

    --若傳入"處理註記"時,則以傳入的值為條件;無傳入時,則預設為月核定(F)
    if nvl(trim(v_i_mtestmk),' ')<>' ' then
        v_mtestmk := UPPER(v_i_mtestmk);
    end if;

    --若無傳入核定年月,則預設系統年月為核定年月
    if nvl(trim(v_i_issuym),' ')<>' ' then
        v_issuym := v_i_issuym;
    else
        v_issuym := to_Char(sysdate,'YYYYMM');
    end if;

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

     --若無傳入執行出媒體序號,則預設為第一次出媒體
    if nvl(trim(v_i_payseqno),' ')<>' ' then
        v_payseqno := v_i_payseqno;
    else
        v_payseqno := '1';
    end if;

     --若無傳入執行出媒體序號,則預設為第批次出媒體
    if nvl(trim(v_i_proctype),' ')<>' ' then
        v_proctype := v_i_proctype;
    else
        v_proctype := '5';
    end if;

    --取出執行時間(西元年月日)
    v_proctime := TO_CHAR(Sysdate,'YYYYMMDDHH24MISS');

-- ============================================================================

--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------
  BEGIN
    --寫入開始LOG --(S)
    get_jobno;
    v_starttime := SYSTIMESTAMP;
    SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'SP_BA_GENBAPAYFILE_TEMP',
                    p_step   => '產生月核付媒體檔及相關月核付報表資料',
                    p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');
    --寫入開始LOG --(E)

       for v_dataCur in c_dataCur Loop
            v_paycode := v_dataCur.PARAMCODE;
             --查詢核定年月的月核定/改匯核定日期
            v_chkdate := PKG_BA_getPayData.fn_BA_getChkDate(v_i_issutyp,'1',v_paycode,v_i_issuym,'N', v_i_chkdate);
            --查詢核定年月的核付/改匯核付日期
            v_paydate := PKG_BA_getPayData.fn_BA_getPayDate(v_i_issutyp,'1',v_paycode,v_i_issuym,'N', v_i_chkdate);

            if nvl(trim(v_paydate),' ') <> ' ' and nvl(trim(v_chkdate),' ') <> ' ' then
                /*********************************************************************************************
                 下列Procedure執行動作步驟如下:
                 ◎步驟1~11動作執行順序不可變更,因有資料產生的先後順序;而步驟12內的各小點其執行順序則可調整
                 ◎各支Procedure所需傳入的參數請參考該Procedure內參數值說明

                 步驟1.產生月處理資料暫存紀錄檔(BBAISSUDATATEMP)的資料,並先commit(為效能考量)
                 步驟2.刪除該核定年月-核付日期的核付資料(BAPAY)
                 步驟3.刪除該核定年月-核付日期的給付媒體明細錄資料(BAGIVETMPDTL)
                 步驟4.刪除該核定年月-核付日期的給付媒體明細錄資料(BAGIVEDTL)
                 步驟5.刪除該核定年月-核付日期的相關月核付報表資料(BAPAYRPTRECORD)

                 步驟6.查詢月處理資料暫存紀錄檔(BBAISSUDATATEMP)是否有資料。若有資料時,才需執行步驟7~12的動作

                 ◎當月處理資料暫存紀錄檔(BBAISSUDATATEMP)有資料時,才需執行下列步驟:
                 步驟7.產生給付核付檔(BAPAY)的資料
                 步驟8.產生給付媒體明細錄檔(BAGIVETMPDTL)的資料
                 步驟9.產生給付媒體檔
                 步驟10.更新給付核付資料
                 步驟11.因已更新給付核付資料,故需重新產生月處理資料暫存紀錄檔(BBAISSUDATATEMP)的資料
                 步驟12.產生月核付相關報表
                        12.1.產生月核定合格清冊及月核定撥付總表(BAPAYRPTRECORD)的資料
                        12.2.產生給付抵銷紓困貸款明細(BAPAYRPTRECORD)的資料
                        12.3.產生核付清單(BAPAYRPTRECORD)的資料
                        12.4.產生核付明細表(BAPAYRPTRECORD)的資料
                        12.5.產生改匯清冊及改匯核定清單(BAPAYRPTRECORD)的資料
                        12.6.產生應收款立帳清冊及應收款立帳核定清單(BAPAYRPTRECORD)的資料
                        12.7.產生應收已收清冊及應收已收核定清單(BAPAYRPTRECORD)的資料
                        12.8.產生退回現金轉暫收及待結轉清單(BAPAYRPTRECORD)的資料
                        12.9.產生勞保年金退回現金清冊(BAPAYRPTRECORD)的資料
                        12.10.產生代扣補償金清冊(BAPAYRPTRECORD)的資料
                *********************************************************************************************/


/*                v_rowCount := 0;
                if v_i_issutyp = '1' then
                    select Count(*)
                      into v_rowCount
                      from BAISSUDATATEMP
                     WHERE PAYCODE = v_i_paycode
                       AND PAYSEQNO = v_i_payseqno;
                else
                    v_rowCount := 1;
                end if;
                if v_rowCount > 0 then

									 --執行應收款立帳及應收款已收作業
                   PKG_BA_ProcACPDtl.sp_BA_MonthApprove(v_issuym,v_paycode,v_chkDate,v_mtestmk,v_procempno,v_i_bajobid,v_g_flag);
                 
                   PKG_BA_genPayRPT.sp_BA_genPayRPT_7_S(v_issuym,v_paycode,v_chkdate,v_paydate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);              --應收款立帳清冊 (應收款立帳核定清單)
                   PKG_BA_genPayRPT.sp_BA_genPayRPT_8_S(v_issuym,v_paycode,v_chkdate,v_paydate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);              --應收已收清冊 (應收已收核定清單)

                   PKG_BA_genPayRPTSUMAccount.sp_BA_genPaySUMRPT_7_S(v_issuym,v_paycode,v_chkdate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);
                   PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_7_S(v_issuym,v_paycode,v_chkdate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);
                   
                   PKG_BA_genPayRPTSUMAccount.sp_BA_genPaySUMRPT_8_S(v_issuym,v_paycode,v_chkdate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);
                   PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_8_S(v_issuym,v_paycode,v_chkdate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);
                   
                   PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_7_S(v_issuym,v_chkdate,v_payseqno,v_cprndt,v_i_bajobid,v_paydate,v_g_flag);
                   PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_8_S(v_issuym,v_chkdate,v_paydate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);

                   --修改log作法 by TseHua 20180419
                   sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                      '媒體產製完成:(給付別-' || v_paycode || ')' || v_i_issuym,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                    v_o_flag := v_g_flag;
                else

                     --修改log作法 by TseHua 20180419
                    sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '0',
                        '媒體產製完成:核定檔無該核定日期的資料:(給付別-' || v_paycode || ')' || v_i_issuym || v_chkdate,
                        replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                        v_g_flag := '0';
                        v_o_flag := v_g_flag;
                end if;*/
                
                   --執行應收款立帳及應收款已收作業
                   PKG_BA_ProcACPDtl.sp_BA_MonthApprove(v_issuym,v_paycode,v_chkDate,v_mtestmk,v_procempno,v_i_bajobid,v_g_flag);

                   PKG_BA_genPayRPT.sp_BA_genPayRPT_7(v_issuym,v_paycode,v_chkdate,v_paydate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);              --應收款立帳清冊 (應收款立帳核定清單)
                   PKG_BA_genPayRPT.sp_BA_genPayRPT_8(v_issuym,v_paycode,v_chkdate,v_paydate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);              --應收已收清冊 (應收已收核定清單)

                   PKG_BA_genPayRPTSUMAccount.sp_BA_genPaySUMRPT_7_l(v_issuym,v_paycode,v_chkdate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);
                   PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_7_l(v_issuym,v_paycode,v_chkdate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);

                   PKG_BA_genPayRPTSUMAccount.sp_BA_genPaySUMRPT_8_l(v_issuym,v_paycode,v_chkdate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);
                   PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_8_l(v_issuym,v_paycode,v_chkdate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);

                   PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_7_l(v_issuym,v_chkdate,v_payseqno,v_cprndt,v_i_bajobid,v_paydate,v_g_flag);
                   PKG_BA_genPFMPFD.sp_BA_genPFMPFDRPT_8_l(v_issuym,v_chkdate,v_paydate,v_payseqno,v_cprndt,v_i_bajobid,v_g_flag);

                   --修改log作法 by TseHua 20180419
                   sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '0',
                      '媒體產製完成:(給付別-' || v_paycode || ')' || v_i_issuym,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                    v_o_flag := v_g_flag;                
                
                
            else
                 --修改log作法 by TseHua 20180419
                  sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '0',
                    '媒體產製完成:月核定日期參數檔無該核定日期的資料:(給付別-' || v_paycode || ')' || v_i_issuym,
                    replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

                  dbms_output.put_line(RPAD('**Err:'||v_ProgName,85,'-')||'>>月核定日期參數檔無該核定年月的資料:(給付別-'||v_paycode||')'||v_i_issuym);
                  v_g_flag := '0';
                  v_o_flag := v_g_flag;
            end if;
        end Loop;

        --2016/05/03 Add by Justin Yu 為了讓前端WEB呼叫,因此執行完畢後直接COMMIT
        if v_o_flag = '0' then
           commit;
        else
           rollback;
        end if;
        SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'SP_BA_GENBAPAYFILE_TEMP',
                    p_step   => '產生月核付媒體檔及相關月核付報表資料',
                    p_memo   => '結束時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')'||CHR(10)||
                                '執行結果:('||v_o_flag||')');
    exception
        when others
            then
                v_errMsg := SQLErrm;
                dbms_output.put_line(RPAD('**Err:'||v_ProgName,85,'-')||'>>'||v_errMsg);

                --修改log作法 by TseHua 20180419
                sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,  '1',
                   RPAD('**Err:' || v_ProgName, 85, '-') || '>>' ||v_errMsg,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));


                   v_g_flag := '1';
                   v_o_flag := v_g_flag;
                 SP_BA_RECJOBLOG(p_job_no => v_jobno,
                     p_job_id => 'SP_BA_GENBAPAYFILE_TEMP',
                     p_step   => '產生月核付媒體檔及相關月核付報表資料',
                     p_memo   => '錯誤代碼：'||SQLCODE||CHR(10)||
                                 '錯誤訊息：'||SQLERRM||CHR(10)||
                                 '錯誤軌跡：'||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
    end;
    dbms_output.put_line(RPAD(v_ProgName,85,'-')||'>>Final');
exception
    when others
        then
            v_errMsg := SQLErrm;
            --修改log作法 by TseHua 20180419
            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
               RPAD('**Err:' || v_ProgName, 85, '-') || '>>' || v_errMsg || v_i_paycode || v_i_mtestmk || v_i_procempno ||v_i_procdeptid || v_i_procip || v_i_payseqno || v_i_chkdate ||v_i_bajobid,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));

            v_g_flag := '1';
            v_o_flag := v_g_flag;
            dbms_output.put_line(RPAD('**Err:'||v_ProgName,85,'-')||'>>'||v_errMsg||v_i_issutyp||v_i_issuym||v_i_paycode||v_i_mtestmk||v_i_procempno||v_i_procdeptid||v_i_procip||v_i_payseqno||v_i_chkdate||v_i_bajobid);
            SP_BA_RECJOBLOG(p_job_no => v_jobno,
                     p_job_id => 'SP_BA_GENBAPAYFILE_TEMP',
                     p_step   => '產生月核付媒體檔及相關月核付報表資料',
                     p_memo   => '錯誤代碼：'||SQLCODE||CHR(10)||
                                 '錯誤訊息：'||SQLERRM||CHR(10)||
                                 '錯誤軌跡：'||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
end SP_BA_GENBAPAYFILE_TEMP;
/

