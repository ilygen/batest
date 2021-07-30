---------------------------------------------------
-- Export file for user BA                       --
-- Created by cht2002 on 2017/10/16, 下午 02:07:04 --
---------------------------------------------------

spool PKG_BA_WEBUPDSTATUS.log

prompt
prompt Creating package PKG_BA_WEBUPDSTATUS
prompt ====================================
prompt
CREATE OR REPLACE Package BA.PKG_BA_WebUpdStatus
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_WebUpdStatus
    PURPOSE:         1.前端更新主檔的處理狀態、案件類別、結案日期、結案原因
                       與行政支援紀錄檔的刪除註記資料
                     2.前端新增紓困貸款回覆檔資料
                       與主檔的勞貸戶註記
                     3.前端新增應收未收檔資料

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2017/09/30  Justin Yu    Created this Package.

    NOTES:
    1.各 Function 及 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/
authid definer is
    v_g_errMsg               varChar2(4000);

    Procedure sp_BA_updBaappbase (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_casetyp             in      varChar2
    );

    Procedure sp_BA_updBaappData (
        v_i_apno               in      varChar2,
        v_i_seqno              in      varChar2,
        v_i_procstat           in      varChar2,
        v_i_oldcasetyp         in      varChar2,
        v_i_newcasetyp         in      varChar2,
        v_i_closedate          in      varChar2,
        v_i_closecause         in      varChar2,
        v_i_ndomk1             in      varChar2,
        v_i_jobno              in      varChar2
    );

    Procedure sp_BA_insBalastreply (
        v_i_apno                in      varChar2,
        v_i_nrmoney             in      varChar2,
        v_i_owint               in      varChar2,
        v_i_offsetexp           in      varChar2
    );

    Procedure sp_BA_insLastreplyData (
        v_i_apno                in      varChar2,
        v_i_nrmoney             in      varChar2,
        v_i_owint               in      varChar2,
        v_i_offsetexp           in      varChar2,
        v_i_brno                in      varChar2,
        v_i_lmseqno             in      varChar2,
        v_i_accountPay          in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_lschkmk             in      varChar2,
        v_i_seqnum              in      Number,
        v_i_filename            in      varChar2,
        v_i_filedate            in      varChar2,
        v_i_paydate             in      varChar2,
        v_i_issuym              in      varChar2,
        v_i_payym               in      varChar2,
        v_i_jobno               in      varChar2
    );

    Procedure sp_BA_insBaunacpdtl (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_issuym              in      varChar2,
        v_i_payym               in      varChar2,
        v_i_recamt              in      varChar2,
        v_i_recrem              in      varChar2,
        v_i_unacpdate           in      varChar2,
        v_i_mdchkmk             in      varChar2,
        v_i_sts                 in      varChar2,
        v_i_typemk              in      varChar2
    );

    Procedure sp_BA_insUnacpdtlData (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_issuym              in      varChar2,
        v_i_payym               in      varChar2,
        v_i_recamt              in      varChar2,
        v_i_recrem              in      varChar2,
        v_i_unacpdate           in      varChar2,
        v_i_mdchkmk             in      varChar2,
        v_i_sts                 in      varChar2,
        v_i_typemk              in      varChar2,
        v_i_benids              in      varChar2,
        v_i_benidnno            in      varChar2,
        v_i_paykind             in      varChar2,
        v_i_nlwkmk              in      varChar2,
        v_i_adwkmk              in      varChar2,
        v_i_jobno               in      varChar2
    );


End;
/

prompt
prompt Creating package body PKG_BA_WEBUPDSTATUS
prompt =========================================
prompt
CREATE OR REPLACE Package Body BA.PKG_BA_WebUpdStatus
is

       /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_WebUpdStatus.sp_BA_updBaappbase
        PURPOSE:         前端更新主檔的處理狀態、案件類別、結案日期、結案原因
                         與行政支援紀錄檔的刪除註記資料

        PARAMETER(IN):   *v_i_apno          (varChar2)       -- 受理編號
        								 *v_i_seqno         (varChar2)       -- 欲變更的序號
                         *v_i_casetyp       (varChar2)       -- 更新後的案件類別

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2017/09/30  Justin Yu    Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_updBaappbase (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_casetyp             in      varChar2
    ) is

      v_jobno               mmjoblog.job_no%TYPE;
      v_starttime           TIMESTAMP;                       --開始時間
      v_casetyp             VARCHAR2(1)         := NULL;     --案件類別

      --讀取該案當下處理狀態
      CURSOR c_dataCur_Baapp IS
        Select A.APNO,                                             -- 受理編號
               A.SEQNO,                                            -- 序號
               A.PROCSTAT,                                         -- 處理狀態
               A.CASETYP,                                          -- 案件類別
               A.MANCHKMK,                                         -- 人工審核結果
               A.CLOSEDATE,                                        -- 結案日期
               A.CLOSECAUSE,                                       -- 結案原因
               B.NDOMK1,                                           -- 函別內容一 對應SDD-580byte的「處理註記」
               B.DELMK                                             -- 刪除註記
          From (Select A1.* From Baappbase A1
                 Where A1.APNO  = v_i_apno) A,
               (Select B1.APNO, B1.NDOMK1, B1.DELMK
                From MAADMREC B1
                 Where B1.LETTERTYPE = '21'
                   And (NVL(B1.DELMK, ' ') <> 'D')
                   And B1.APNO  = v_i_apno)B
                 Where A.APNO = B.APNO(+)
                 Order by A.APNO, A.SEQNO;
--=============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
    PROCEDURE get_jobno
    IS
    BEGIN
       SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_jobno FROM DUAL;

    END get_jobno;
--============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------

    Begin
    --寫入開始LOG
    get_jobno;
    v_starttime := SYSTIMESTAMP;
    BA.SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'sp_BA_updBaappbase',
                    p_step   => '前端介接更新BA.BAAPPBASE 處理狀態、案件類別、結案日期、結案原因',
                    p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');

    If (Trim(v_i_apno) Is Not Null And length(v_i_apno) = 12)   Then
          For v_CurBaapp In c_dataCur_Baapp Loop

              --前端有傳入v_i_casetyp(案件類別),則以前端傳入的值為更新值,若無傳入,則default都是更新為2:續發案
              If (v_i_casetyp is null)   Then
                   v_casetyp := '2';
                else
                   v_casetyp := v_i_casetyp;
              End if;

              --前端有傳入v_i_seqno(序號),則只更新前端所傳入的該筆序號,若無傳入,則default都是全部更新
              If ((v_i_seqno is null) Or
                  (v_i_seqno is not null And v_i_seqno = v_CurBaapp.seqno))    then

              sp_BA_updBaappData(v_CurBaapp.apno,
                            v_CurBaapp.seqno,
                            v_CurBaapp.procstat,
                            v_CurBaapp.casetyp,
                            v_casetyp,
                            v_CurBaapp.closedate,
                            v_CurBaapp.closecause,
                            v_CurBaapp.ndomk1,
                            v_jobno
                         );
              End if;
          End Loop;

        BA.SP_BA_RECJOBLOG (p_job_no => v_jobno,
                      p_job_id => 'sp_BA_updBaappbase',
                      p_step   => '前端介接更新BA.BAAPPBASE 處理狀態、案件類別、結案日期、結案原因',
                      p_memo   => '結束時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');

    Else
        BA.SP_BA_RECJOBLOG (p_job_no => v_jobno,
                        p_job_id => 'sp_BA_updBaappbase',
                        p_step   => '前端介接更新BA.BAAPPBASE 處理狀態、案件類別、結案日期、結案原因',
                        p_memo   => '受理編號：['||v_i_apno||']不得為空值或長度不符');
    End If;
    End;
    --Procedure sp_BA_updBaappbase

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_WebUpdStatus.sp_BA_updBaappData
        PURPOSE:         更新主檔的處理狀態、案件類別、結案日期、結案原因
                         與行政支援紀錄檔的刪除註記資料

        PARAMETER(IN):   *v_i_apno          (varChar2)       -- 勞保年金受理編號
                         *v_i_seqno         (varChar2)       -- 序號
                         *v_i_procstat      (varChar2)       -- 處理狀態
                         *v_i_oldcasetyp    (varChar2)       -- 原始的案件類別
                         *v_i_newcasetyp    (varChar2)       -- 更新後的案件類別
                         *v_i_closedate     (varChar2)       -- 結案日期
                         *v_i_closecause    (varChar2)       -- 結案原因
                         *v_i_ndomk1        (varChar2)       -- 函別內容一 對應SDD-580byte的「處理註記」
                         *v_i_jobno         (varChar2)       -- JobNumber

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/04/11  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_updBaappData (
        v_i_apno               in      varChar2,             -- 受理編號
        v_i_seqno              in      varChar2,             -- 序號
        v_i_procstat           in      varChar2,             -- 處理狀態
        v_i_oldcasetyp         in      varChar2,             -- 原始的案件類別
        v_i_newcasetyp         in      varChar2,             -- 更新後的案件類別
        v_i_closedate          in      varChar2,             -- 結案日期
        v_i_closecause         in      varChar2,             -- 結案原因
        v_i_ndomk1             in      varChar2,             -- 函別內容一 對應SDD-580byte的「處理註記」
        v_i_jobno              in      varChar2              -- JobNumber
    ) is
        v_sql                  varChar2(3000);               --案件類別
        v_errMsg               varChar2(2000);               --錯誤訊息
--=============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
-------------------------------------------------------------------------------
    Begin

    v_sql := ' UPDATE BAAPPBASE ' ||
             ' SET PROCSTAT   = ''12'','||
             '    CASETYP    = ''' ||v_i_newcasetyp ||''',' ||
             '   CLOSEDATE  = '''',' ||
             '   CLOSECAUSE = ''''' ||
             ' WHERE APNO = ''' || v_i_apno || '''' ||
             ' AND   SEQNO = ''' || v_i_seqno || '''' ||
             ' AND   PROCSTAT = ''' || v_i_procstat || '''' ||
             ' AND   CASETYP  = ''' || v_i_oldcasetyp || ''''   ;
    If (v_i_closedate is null) then
        v_sql := v_sql || ' AND   CLOSEDATE IS NULL';
    else
        v_sql := v_sql || ' AND   CLOSEDATE  = ''' || v_i_closedate || '''' ;
    End if;
    If (v_i_closecause is null) then
        v_sql := v_sql || ' AND   trim(CLOSECAUSE) IS NULL';
    else
        v_sql := v_sql || ' AND   trim(CLOSECAUSE)  = ''' || v_i_closecause || '''' ;
    End if;

    BA.SP_BA_RECJOBLOG (p_job_no => v_i_jobno,
                    p_job_id => 'sp_BA_updBaappData',
                    p_step   => '更新BA.BAAPPBASE 處理狀態、案件類別、結案日期、結案原因',
                    p_memo   => '更新內容：['|| v_sql || ']');

    execute immediate v_sql;

    If (v_i_ndomk1 is not null) then
        v_sql := ' UPDATE MAADMREC ' ||
                 ' SET DELMK  = ''D'','||
                 '    UPDUSER = ''70348'','||
                 '    UPDTIME = ' || TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') || '' ||
                 ' WHERE APNO = ''' || v_i_apno || '''' ||
                 ' AND   LETTERTYPE = ''21'''||
                 ' AND   NDOMK1 = ''' || v_i_ndomk1 ||'''';

        BA.SP_BA_RECJOBLOG (p_job_no => v_i_jobno,
                        p_job_id => 'sp_BA_updBaappData',
                        p_step   => '更新BLIADM.MAADMREC 刪除註記資料',
                        p_memo   => '更新內容：['|| v_sql || ']');

        execute immediate v_sql;

    End if;
    Exception
            When others
            then
                v_errMsg := SQLErrm;
                BA.SP_BA_RECJOBLOG (p_job_no => v_i_jobno,
                        p_job_id => 'sp_BA_updBaappData',
                        p_step   => '更新資料庫出現錯誤',
                        p_memo   => '錯誤訊息：[' || v_errMsg ||']');
                rollback;


    End;
    --Procedure sp_BA_updBaappData

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_WebUpdStatus.sp_BA_insBalastreply
        PURPOSE:         前端新增紓困貸款回覆檔資料
                         與主檔的勞貸戶註記

        PARAMETER(IN):   *v_i_apno          (varChar2)       -- 受理編號
                         *v_i_nrmoney       (varChar2)       -- 紓困貸款未還本金金額
                         *v_i_owint         (varChar2)       -- 紓困貸款未還利息金額
                         *v_i_offsetexp     (varChar2)       -- 紓困貸款未還其他費用

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2017/09/30  Justin Yu    Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_insBalastreply (
        v_i_apno                in      varChar2,
        v_i_nrmoney             in      varChar2,
        v_i_owint               in      varChar2,
        v_i_offsetexp           in      varChar2
    ) is
      v_jobno                  MMJOBLOG.JOB_NO%TYPE;
      v_starttime              TIMESTAMP;                             --開始時間
      v_brno                   BALASTREPLY.BRNO%TYPE;              --收件編號-分行代號
      v_lmseqno                BALASTREPLY.LMSEQNO%TYPE;           --收件編號-流水號
      v_accountPay             BALASTREPLY.ACCOUNTPAY%TYPE;        --放款帳號
      v_idnno                  BAAPPBASE.EVTIDNNO%TYPE;            --身分證號
      v_lschkmk                BAAPPBASE.LSCHKMK%TYPE;             --勞貸戶註記
      v_seqnum                 NUMBER                  := 0;       --流水號
      v_filename               BALASTREPLY.FILENAME%TYPE;          --回覆檔案名稱
      v_filedate               BALASTREPLY.FILEDATE%TYPE;          --檔案日期時間
      v_paydate                BALASTREPLY.PAYDATE%TYPE;           --勞保年金給付日(勞貸本息截止日)
      v_issuym                 BALASTREPLY.ISSUYM%TYPE :=
      												 '0'||fn_BA_transDateValue(to_char(SYSDATE,'YYYYMM'),'2') ;
      												                                     --核定年月
      v_payym                  BALASTREPLY.PAYYM%TYPE;             --給付年月

      --讀取該案當下處理狀態
      CURSOR c_dataCur_Baapp IS
        Select Distinct A.BRNO,
                A.SEQNO,
                A.ACCOUNT_PAY,
                B.LSCHKMK,
                B.EVTIDNNO
        From    MBLNM A, BAAPPBASE B
         Where  (EMRK <> '1' Or trim(EMRK) Is Null)
         And    (B.EVTIDNNO = A.IDN_APLY OR B.EVTIDNNO = A.IDN_LIB)
         And    B.APNO  = v_i_apno
         And    B.SEQNO = '0000';

      CURSOR c_dataCur_Lastreply IS
        Select  to_number(SEQNUM + 1) AS NEWSEQNUM,
                FILENAME,
                FILEDATE,
                PAYDATE,
                ISSUYM,
                PAYYM
        From BALASTREPLY A
         Where ISSUYM = v_issuym
           And SEQNUM = (Select Max(SEQNUM) From BA.BALASTREPLY A1
                         Where A1.ISSUYM = A.ISSUYM 
                           And A1.PAYDATE like A.ISSUYM||'%');
--=============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
    PROCEDURE get_jobno
    IS
    BEGIN
       SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_jobno FROM DUAL;

    END get_jobno;
--============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------

    Begin
    --寫入開始LOG
    get_jobno;
    v_starttime := SYSTIMESTAMP;
    BA.SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'sp_BA_insBalastreply',
                    p_step   => '前端新增BALASTREPLY 紓困貸款回覆檔資料',
                    p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');

    If (Trim(v_i_apno) Is Not Null And length(v_i_apno) = 12)   Then
          For v_CurBaapp In c_dataCur_Baapp Loop
          		v_brno       := substr(v_CurBaapp.BRNO,1,3);
          		v_lmseqno    := v_CurBaapp.SEQNO      ;
          		v_accountPay := v_CurBaapp.ACCOUNT_PAY;
          		v_idnno      := v_CurBaapp.EVTIDNNO   ;
          		v_lschkmk    := v_CurBaapp.LSCHKMK    ;
          End Loop;

          For v_CurLastreply In c_dataCur_Lastreply Loop
              v_seqnum   := v_CurLastreply.NEWSEQNUM ;
              v_filename := v_CurLastreply.FILENAME  ;
              v_filedate := v_CurLastreply.FILEDATE  ;
              v_paydate  := v_CurLastreply.PAYDATE   ;
              v_payym    := v_CurLastreply.PAYYM     ;
          End Loop;

					sp_BA_insLastreplyData(v_i_apno,
								        v_i_nrmoney,
								        v_i_owint,
								        v_i_offsetexp,
					              v_brno,
	                      v_lmseqno,
	                      v_accountPay,
	                      v_idnno,
	                      v_lschkmk,
	                      v_seqnum,
	                      v_filename,
	                      v_filedate,
	                      v_paydate,
	                      v_issuym,
	                      v_payym,
	                      v_jobno
	                     );

          BA.SP_BA_RECJOBLOG (p_job_no => v_jobno,
                      p_job_id => 'sp_BA_insBalastreply',
                      p_step   => '前端新增BALASTREPLY 紓困貸款回覆檔資料',
                      p_memo   => '結束時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');

    Else
        BA.SP_BA_RECJOBLOG (p_job_no => v_jobno,
                        p_job_id => 'sp_BA_insBalastreply',
                        p_step   => '前端新增BALASTREPLY 紓困貸款回覆檔資料',
                        p_memo   => '受理編號：['||v_i_apno||']不得為空值或長度不符');
    End If;

    End;
    --Procedure sp_BA_insBalastreply

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_WebUpdStatus.sp_BA_insLastreplyData
        PURPOSE:         新增紓困貸款回覆檔資料
                         與主檔的勞貸戶註記

        PARAMETER(IN):   *v_i_apno           (varChar2)       -- 勞保年金受理編號
                         *v_i_nrmoney        (varChar2)       -- 紓困貸款未還本金金額
                         *v_i_owint          (varChar2)       -- 紓困貸款未還利息金額
                         *v_i_offsetexp      (varChar2)       -- 紓困貸款未還其他費用
                         *v_i_brno           (varChar2)       -- 收件編號-分行代號
                         *v_i_lmseqno        (varChar2)       -- 收件編號-流水號
                         *v_i_accountPay     (varChar2)       -- 放款帳號
                         *v_i_idnno          (varChar2)       -- 身分證號
                         *v_i_lschkmk        (varChar2)       -- 勞貸戶註記
                         *v_i_seqnum         (Number)         -- 流水號
                         *v_i_filename       (varChar2)       -- 回覆檔案名稱
                         *v_i_filedate       (varChar2)       -- 檔案日期時間
                         *v_i_paydate        (varChar2)       -- 勞保年金給付日(勞貸本息截止日)
                         *v_i_issuym         (varChar2)       -- 核定年月
                         *v_i_payym          (varChar2)       -- 給付年月
                         *v_i_jobno          (varChar2)       -- JobNumber

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/04/11  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_insLastreplyData (
        v_i_apno                in      varChar2,             -- 勞保年金受理編號
        v_i_nrmoney             in      varChar2,             -- 紓困貸款未還本金金額
        v_i_owint               in      varChar2,             -- 紓困貸款未還利息金額
        v_i_offsetexp           in      varChar2,             -- 紓困貸款未還其他費用
        v_i_brno                in      varChar2,             -- 收件編號-分行代號
        v_i_lmseqno             in      varChar2,             -- 收件編號-流水號
        v_i_accountPay          in      varChar2,             -- 放款帳號
        v_i_idnno               in      varChar2,             -- 身分證號
        v_i_lschkmk             in      varChar2,             -- 勞貸戶註記
        v_i_seqnum              in      Number,               -- 流水號
        v_i_filename            in      varChar2,             -- 回覆檔案名稱
        v_i_filedate            in      varChar2,             -- 檔案日期時間
        v_i_paydate             in      varChar2,             -- 勞保年金給付日(勞貸本息截止日)
        v_i_issuym              in      varChar2,             -- 核定年月
        v_i_payym               in      varChar2,             -- 給付年月
        v_i_jobno               in      varChar2              -- JobNumber
    ) is
        v_sql                  varChar2(3000);               --案件類別
        v_errMsg               varChar2(2000);               --錯誤訊息
        v_nrmoney              varChar2(7):= '0';            -- 紓困貸款未還本金金額
        v_owint                varChar2(7):= '0';            -- 紓困貸款未還利息金額
        v_offsetexp            varChar2(7):= '0';            -- 紓困貸款未還其他費用

--=============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
-------------------------------------------------------------------------------
    Begin

    v_sql := ' UPDATE BAAPPBASE ' ||
             ' SET LSCHKMK   = ''4'''||
             ' WHERE APNO = ''' || v_i_apno || '''' ||
             ' AND   SEQNO = ''0000''' ||
             ' AND   LSCHKMK = ''' || v_i_lschkmk || ''''  ;

    BA.SP_BA_RECJOBLOG (p_job_no => v_i_jobno,
                    p_job_id => 'sp_BA_insLastreplyData',
                    p_step   => '更新BAAPPBASE 勞貸戶註記',
                    p_memo   => '更新內容：['|| v_sql || ']');

    execute immediate v_sql;

    If (v_i_nrmoney is not null) then
       v_nrmoney := v_i_nrmoney;
    End if ;

    If (v_i_owint is not null) then
       v_owint := v_i_owint;
    End if ;

    If (v_i_offsetexp is not null) then
       v_offsetexp := v_i_offsetexp;
    End if ;

    v_sql := ' INSERT INTO BALASTREPLY ' ||
             ' (FILENAME, FILEDATE, SEQNUM, ACCOUNTPAY,' ||
             '  BRNO, LMSEQNO, IDNNO, PAYDATE,' ||
             '  NRMONEY, OWINT, OFFSETEXP, APNO, ' ||
             '  ISSUYM, PAYYM, SEQNO) VALUES (' ||
             '  ''' || v_i_filename    || ''',' ||
             '  ''' || v_i_filedate    || ''',' ||
             '  '   || v_i_seqnum      || ',' ||
             '  ''' || v_i_accountpay  || ''',' ||
             '  ''' || v_i_brno        || ''',' ||
             '  ''' || v_i_lmseqno     || ''',' ||
             '  ''' || v_i_idnno       || ''',' ||
             '  ''' || v_i_paydate     || ''',' ||
             '  ''' || v_nrmoney       || ''',' ||
             '  ''' || v_owint         || ''',' ||
             '  ''' || v_offsetexp     || ''',' ||
             '  ''' || v_i_apno        || ''',' ||
             '  ''' || v_i_issuym      || ''',' ||
             '  ''' || v_i_payym       || ''',' ||
             '  ''0000'')' ;

    BA.SP_BA_RECJOBLOG (p_job_no => v_i_jobno,
                    p_job_id => 'sp_BA_insLastreplyData',
                    p_step   => '新增BALASTREPLY 紓困貸款回覆檔資料',
                    p_memo   => '更新內容：['|| v_sql || ']');

    execute immediate v_sql;

    Exception
            When others
            then
                v_errMsg := SQLErrm;
                BA.SP_BA_RECJOBLOG (p_job_no => v_i_jobno,
                        p_job_id => 'sp_BA_insLastreplyData',
                        p_step   => '更新資料庫出現錯誤',
                        p_memo   => '錯誤訊息：[' || v_errMsg ||']');
                rollback;


    End;
    --Procedure sp_BA_insLastreplyData

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_WebUpdStatus.sp_BA_insBaunacpdtl
        PURPOSE:         前端新增應收未收檔資料

        PARAMETER(IN):   *v_i_apno           (varChar2)       -- 受理編號
                         *v_i_seqno          (varChar2)       -- 序號
                         *v_i_issuym         (varChar2)       -- 核定年月
                         *v_i_payym          (varChar2)       -- 給付年月
                         *v_i_recamt         (varChar2)       -- 應收總金額
                         *v_i_recrem         (varChar2)       -- 未收總金額
                         *v_i_unacpdate      (varChar2)       -- 應收立帳日期
                         *v_i_mdchkmk        (varChar2)       -- 處理功能(Default值:A) A-新增、C-修改、D-註銷
                         *v_i_sts            (varChar2)       -- 資料狀態(Default值:1) 1-應收未收、2-催收款、3-呆帳
                         *v_i_typemk         (varChar2)       -- 應收記錄來源(Default值:0) 0-應收未收、C-止付、D-退匯收回
        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2017/09/30  Justin Yu    Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_insBaunacpdtl (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_issuym              in      varChar2,
        v_i_payym               in      varChar2,
        v_i_recamt              in      varChar2,
        v_i_recrem              in      varChar2,
        v_i_unacpdate           in      varChar2,
        v_i_mdchkmk             in      varChar2,
        v_i_sts                 in      varChar2,
        v_i_typemk              in      varChar2
    ) is
      v_jobno                  MMJOBLOG.JOB_NO%TYPE;
      v_starttime              TIMESTAMP;                          --開始時間
      v_benids                 BAAPPBASE.BENIDS%TYPE;              --受益人社福識別碼
      v_benidnno               BAAPPBASE.BENIDNNO%TYPE;            --受益人身分證號
      v_paykind                BAAPPBASE.PAYKIND%TYPE;             --給付種類
      v_nlwkmk                 BAAPPEXPAND.NLWKMK%TYPE;            --普職註記 空白-預設值、1-普通、2-職災
      v_adwkmk                 BAAPPEXPAND.ADWKMK%TYPE;            --加職註記 空白-預設值、1-一般職災、2-加職職災

      --讀取該案當下處理狀態
      CURSOR c_dataCur_Baapp IS
        Select  A.BENIDS,
                A.BENIDNNO,
                A.PAYKIND,
                B.NLWKMK,
                B.ADWKMK
        From    BAAPPBASE A, BAAPPEXPAND B
         Where  A.BAAPPBASEID = B.BAAPPBASEID
         And    A.APNO  = v_i_apno
         And    A.SEQNO =  v_i_seqno;

--=============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
    PROCEDURE get_jobno
    IS
    BEGIN
       SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_jobno FROM DUAL;

    END get_jobno;
--============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------

    Begin
    --寫入開始LOG
    get_jobno;
    v_starttime := SYSTIMESTAMP;
    BA.SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'sp_BA_insBaunacpdtl',
                    p_step   => '前端新增BAUNACPDTL應收未收檔資料',
                    p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');

    If (Trim(v_i_apno) Is Not Null And length(v_i_apno) = 12)   Then
          For v_CurBaapp In c_dataCur_Baapp Loop
          		v_benids      := v_CurBaapp.BENIDS  ;
          		v_benidnno    := v_CurBaapp.BENIDNNO;
          		v_paykind     := v_CurBaapp.PAYKIND ;
          		v_nlwkmk      := v_CurBaapp.NLWKMK  ;
          		v_adwkmk      := v_CurBaapp.ADWKMK  ;

              sp_BA_insUnacpdtlData(
                            v_i_apno,
                            v_i_seqno,
                            v_i_issuym,
                            v_i_payym,
                            v_i_recamt,
                            v_i_recrem,
                            v_i_unacpdate,
                            v_i_mdchkmk,
                            v_i_sts,
                            v_i_typemk,
                            v_benids,
                            v_benidnno,
                            v_paykind,
                            v_nlwkmk,
                            v_adwkmk,
                            v_jobno
                           );

              BA.SP_BA_RECJOBLOG (p_job_no => v_jobno,
                          p_job_id => 'sp_BA_insBaunacpdtl',
                          p_step   => '前端新增BAUNACPDTL應收未收檔資料',
                          p_memo   => '結束時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');

          End Loop;
    Else
        BA.SP_BA_RECJOBLOG (p_job_no => v_jobno,
                        p_job_id => 'sp_BA_insBaunacpdtl',
                        p_step   => '前端新增BAUNACPDTL應收未收檔資料',
                        p_memo   => '受理編號：['||v_i_apno||']不得為空值或長度不符');
    End If;

    End;
    --Procedure sp_BA_insBaunacpdtl

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_WebUpdStatus.sp_BA_insUnacpdtlData
        PURPOSE:         新增應收未收檔資料

        PARAMETER(IN):   *v_i_apno                 (varChar2)       -- 受理編號
                         *v_i_seqno                (varChar2)       -- 序號
                         *v_i_issuym               (varChar2)       -- 核定年月
                         *v_i_payym                (varChar2)       -- 給付年月
                         *v_i_recamt               (varChar2)       -- 應收總金額
                         *v_i_recrem               (varChar2)       -- 未收總金額
                         *v_i_unacpdate            (varChar2)       -- 應收立帳日期
                         *v_i_mdchkmk              (varChar2)       -- 處理功能 A-新增、C-修改、D-註銷
                         *v_i_sts                  (varChar2)       -- 資料狀態 1-應收未收、2-催收款、3-呆帳
                         *v_i_typemk               (Number)         -- 應收記錄來源 0-應收未收、C-止付、D-退匯收回
                         *v_i_benids               (varChar2)       -- 受益人社福識別碼
                         *v_i_benidnno             (varChar2)       -- 受益人身分證號
                         *v_i_paykind              (varChar2)       -- 給付種類
                         *v_i_nlwkmk               (varChar2)       -- 普職註記 空白-預設值、1-普通、2-職災
                         *v_i_adwkmk               (varChar2)       -- 加職註記 空白-預設值、1-一般職災、2-加職職災
                         *v_i_jobno                (varChar2)       -- JobNumber

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/04/11  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_insUnacpdtlData (
        v_i_apno                in      varChar2,             -- 勞保年金受理編號
        v_i_seqno               in      varChar2,             -- 序號
        v_i_issuym              in      varChar2,             -- 核定年月
        v_i_payym               in      varChar2,             -- 給付年月
        v_i_recamt              in      varChar2,             -- 應收總金額
        v_i_recrem              in      varChar2,             -- 未收總金額
        v_i_unacpdate           in      varChar2,             -- 應收立帳日期
        v_i_mdchkmk             in      varChar2,             -- 處理功能 A-新增、C-修改、D-註銷
        v_i_sts                 in      varChar2,             -- 資料狀態 1-應收未收、2-催收款、3-呆帳
        v_i_typemk              in      varChar2,             -- 應收記錄來源 0-應收未收、C-止付、D-退匯收回
        v_i_benids              in      varChar2,             -- 受益人社福識別碼
        v_i_benidnno            in      varChar2,             -- 受益人身分證號
        v_i_paykind             in      varChar2,             -- 給付種類
        v_i_nlwkmk              in      varChar2,             -- 普職註記 空白-預設值、1-普通、2-職災
        v_i_adwkmk              in      varChar2,             -- 加職註記 空白-預設值、1-一般職災、2-加職職災
        v_i_jobno               in      varChar2              -- JobNumber
    ) is
        v_sql                  varChar2(3000);                --案件類別
        v_errMsg               varChar2(2000);                --錯誤訊息
        v_mdchkmk              varChar2(1) := 'A';            -- 處理功能 A-新增、C-修改、D-註銷
        v_sts                  varChar2(1) := '1';            -- 資料狀態 1-應收未收、2-催收款、3-呆帳
        v_typemk               varChar2(1) := '0';            -- 應收記錄來源 0-應收未收、C-止付、D-退匯收回

--=============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
-------------------------------------------------------------------------------
    Begin

    If (v_i_mdchkmk is not null) then
       v_mdchkmk := v_i_mdchkmk;
    End if ;

    If (v_i_sts is not null) then
       v_sts := v_i_sts;
    End if ;

    If (v_i_typemk is not null) then
       v_typemk := v_i_typemk;
    End if ;

    v_sql := ' INSERT INTO BAUNACPDTL ' ||
             ' (BAUNACPDTLID, APNO, SEQNO, ISSUYM, ' ||
             '  PAYYM, BENIDS, BENIDNNO, PAYKIND, ' ||
             '  RECAMT, RECREM, UNACPDATE, MDCHKMK, ' ||
             '  TXDATE, STS, TYPEMK, NLWKMK, ' ||
             '  ADWKMK) VALUES (' ||
             '  '   || (TO_CHAR(SYSDATE,'YYYYMMDD')||LPAD(TO_CHAR(BA.BAUNACPDTLID.NEXTVAL),12,'0')) || ',' ||
             '  ''' || v_i_apno     || ''',' ||
             '  ''' || v_i_seqno    || ''',' ||
             '  ''' || v_i_issuym   || ''',' ||
             '  ''' || v_i_payym    || ''',' ||
             '  ''' || v_i_benids   || ''',' ||
             '  ''' || v_i_benidnno || ''',' ||
             '  ''' || v_i_paykind  || ''',' ||
             '  ''' || v_i_recamt   || ''',' ||
             '  ''' || v_i_recrem   || ''',' ||
             '  ''' || v_i_unacpdate|| ''',' ||
             '  ''' || v_mdchkmk    || ''',' ||
             '  '   || TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') || ',' ||
             '  ''' || v_sts        || ''',' ||
             '  ''' || v_typemk     || ''',' ||
             '  ''' || v_i_nlwkmk   || ''',' ||
             '  ''' || v_i_adwkmk   || ''')' ;

    BA.SP_BA_RECJOBLOG (p_job_no => v_i_jobno,
                    p_job_id => 'sp_BA_insUnacpdtlData',
                    p_step   => '新增應收未收檔資料',
                    p_memo   => '更新內容：['|| v_sql || ']');

    execute immediate v_sql;

    Exception
            When others
            then
                v_errMsg := SQLErrm;
                BA.SP_BA_RECJOBLOG (p_job_no => v_i_jobno,
                        p_job_id => 'sp_BA_insUnacpdtlData',
                        p_step   => '更新資料庫出現錯誤',
                        p_memo   => '錯誤訊息：[' || v_errMsg ||']');
                rollback;


    End;
    --Procedure sp_BA_insUnacpdtlData
End;
/


spool off
