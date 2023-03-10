CREATE OR REPLACE Package BA.PKG_BA_updCIStatus
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_updCIStatus
    PURPOSE:         更新CI.CIPB 被保險人領取給付註記、管制加保註記、被保險人逕退檔
                     資料

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2012/04/11  ChungYu Lin  Created this Package.

    NOTES:
    1.各 Function 及 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/
authid definer is
    v_g_errMsg               varChar2(4000);

    Procedure sp_BA_updSBMK (
        v_i_paycode             in      varChar2,
        v_i_intyp               in      varChar2,
        v_i_ciid                in      varChar2,
        v_i_user                in      varChar2,
        v_i_befsbmk             in      varChar2,
        v_i_sbmk                in      varChar2,
        v_i_apno                in      varChar2,
        v_i_issuym              in      varChar2,
        v_i_casetyp             in      varChar2,
        v_i_systp               in      varChar2,
        v_o_return              out     varChar2,
        v_o_returnCode          out     varChar2
    );

    Procedure sp_BA_updUINMK (
        v_i_intyp               in      varChar2,
        v_i_ciid                in      varChar2,
        v_i_user                in      varChar2,
        v_i_befuinmk            in      varChar2,
        v_i_uinmk               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_issuym              in      varChar2,
        v_i_casetyp             in      varChar2,
        v_i_systp               in      varChar2,
        v_o_return              out     varChar2,
        v_o_returnCode          out     varChar2
    );

    Procedure sp_BA_updCheckMonth (
        v_i_issuym              in      varChar2,
        v_i_user                in      varChar2
    );

    -- 2015/04/01 Mark By ChungYu 老年年金取消月初逕退改至sp_BA_OldAgeDaily
    /*Procedure sp_BA_updLastMonth (
        v_i_paramym             in      varChar2,
        v_i_user                in      varChar2
    );*/
    -- 2015/04/01 Mark By ChungYu 老年年金取消月初逕退改至sp_BA_OldAgeDaily

    Procedure sp_BA_OldAgeDaily (
        v_i_user                in      varChar2
    );

    Procedure sp_BA_updCase3SBMK (
        v_i_user                in      varChar2
    );

    Procedure sp_BA_updDaily (
        v_i_user                in      varChar2
    );

    Procedure sp_BA_updBatchSBMK(
        v_i_user                in      varChar2
    );

    Procedure sp_BA_singleUpdate(
        v_i_apno                in      varChar2,
        v_i_sbmk                in      varChar2,
        v_i_uinmk               in      varChar2,
        v_i_withdraw            in      varChar2,
        v_i_user                in      varChar2
    );

    Procedure sp_BA_singleUpdateBatch;

    Procedure sp_saveMsg(
        v_i_msgtyp              IN      VARCHAR2,
        v_i_apno                IN      VARCHAR2,
        v_i_issuym              IN      VARCHAR2,
        v_i_ciid                IN      VARCHAR2,
        v_i_intyp               IN      VARCHAR2,
        v_i_userid              IN      VARCHAR2,
        v_i_message             IN      VARCHAR2
    );

End;
/

CREATE OR REPLACE Package Body BA.PKG_BA_updCIStatus
is

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_updCIStatus.sp_BA_updSBMK
        PURPOSE:         更新 CI.CIPB 被保險人領取老年給付註記 (SBMK2)、失能給付註記 (SBMK1)、
                         遺屬給付註記 (SBMK3)。

        PARAMETER(IN):   *v_i_paycode       (varChar2)       -- 給付種類 L：老年(SIBMK2)
                                                                         K：失能(SIBMK1)
                                                                         S：遺屬(SIBMK3)
                         *v_i_intyp         (varChar2)       -- 保險類別
                         *v_i_ciid          (varChar2)       -- 勞就保識別碼
                         *v_i_user          (varChar2)       -- 修改使用者
                         *v_i_sbmk          (varChar2)       -- 領取失能給付註記
                         *v_i_apno          (varChar2)       -- 勞保年金受理編號

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/04/11  ChungYu Lin  Created this Package.
        1.1   2023/03/08  William      依據babaweb-69修改，增加log

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_updSBMK (
        v_i_paycode             in      varChar2,
        v_i_intyp               in      varChar2,
        v_i_ciid                in      varChar2,
        v_i_user                in      varChar2,
        v_i_befsbmk             in      varChar2,
        v_i_sbmk                in      varChar2,
        v_i_apno                in      varChar2,
        v_i_issuym              in      varChar2,
        v_i_casetyp             in      varChar2,
        v_i_systp               in      varChar2,
        v_o_return              out     varChar2,
        v_o_returnCode          out     varChar2
    ) is
    v_result          VARCHAR2(1)         := NULL;
    v_resultCode      VARCHAR2(300)       := NULL;
    v_sbmk            VARCHAR2(10)        := NULL;
    v_rec_plog plog%ROWTYPE;

    Begin
          /*
             2012/12/18 Modify By ChungYu 不給付案要清除領取給付註記 ，所以會傳Null 進來
          */
          If (Trim(v_i_paycode) Is Not Null) And (Trim(v_i_intyp) Is Not Null) And
             (Trim(v_i_ciid) Is Not Null)    /*And (Trim(v_i_sbmk) Is Not Null)*/ And
             (Trim(v_i_apno) Is Not Null) Then
              Case
                --   更新被保險人領取老年給付註記 (SBMK2)
                When v_i_paycode = 'L' Then
                     v_sbmk := 'SBMK2';
                     CI.CIPB_API_SBMK2( v_i_ciid,       --  勞就保識別碼
                                        v_i_intyp,      --  保險類別
                                        v_i_user,       --  修改使用者
                                        v_i_systp,      --  修改系統程式
                                        v_i_sbmk,       --  領取失能給付註記
                                        v_result,       --  return 0,1,2
                                        v_resultCode    --  return [CIPB_API_SBMK1比對CIPB查無資料],[CIPB_API_SBMK1更新成功],[CIPB_API_SBMK1發生錯誤:SQLERRM]
                                       );
                --   更新被保險人領取失能給付註記 (SBMK1)
                When v_i_paycode = 'K' Then
                     v_sbmk := 'SBMK1';
                     CI.CIPB_API_SBMK1( v_i_ciid,       --  勞就保識別碼
                                        v_i_intyp,      --  保險類別
                                        v_i_user,       --  修改使用者
                                        v_i_systp,      --  修改系統程式
                                        v_i_sbmk,       --  領取失能給付註記
                                        v_result,       --  return 0,1,2
                                        v_resultCode    --  return [CIPB_API_SBMK1比對CIPB查無資料],[CIPB_API_SBMK1更新成功],[CIPB_API_SBMK1發生錯誤:SQLERRM]
                                       );
                --   更新被保險人領取遺屬給付註記 (SBMK3)
                When v_i_paycode = 'S' Then
                     v_sbmk := 'SBMK3';
                     CI.CIPB_API_SBMK3( v_i_ciid,       --  勞就保識別碼
                                        v_i_intyp,      --  保險類別
                                        v_i_user,       --  修改使用者
                                        v_i_systp,      --  修改系統程式
                                        v_i_sbmk,       --  領取失能給付註記
                                        v_result,       --  return 0,1,2
                                        v_resultCode    --  return [CIPB_API_SBMK1比對CIPB查無資料],[CIPB_API_SBMK1更新成功],[CIPB_API_SBMK1發生錯誤:SQLERRM]
                                       );
                End Case;

                Insert Into BAUPDCIDBLOG (APNO, ISSUYM, CASETYP, CIID, INTYP, USERID, SYSTP, COLNAME, COLVALUEB,COLVALUEE,RESULTNO, RESULTCODE, PROCDATE)
                                  Values (v_i_apno,
                                          v_i_issuym,
                                          v_i_casetyp,
                                          v_i_ciid,
                                          v_i_intyp,
                                          v_i_user,
                                          v_i_systp,
                                          v_sbmk,
                                          v_i_befsbmk,
                                          v_i_sbmk,
                                          v_result,
                                          v_resultCode,
                                          to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                         );
                if v_result <> '1' then
                    v_rec_plog.userid    := v_i_user;
                    v_rec_plog.jobid     := TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISSSSS');
                    v_rec_plog.starttime := SYSDATE;
                    v_rec_plog.typemk    := '1';
                    v_rec_plog.levelmk   := '1'; --INFO
                    v_rec_plog.pseq      := '1';
                    v_rec_plog.proctime  := SYSDATE;
                    v_rec_plog.procname  := 'sp_BA_updSBMK';
                    v_rec_plog.msg1      := 'Update Ci.Cipb SBMK Error ：APNO='||v_i_apno||','|| v_result || '：' || v_resultCode;
                    pkg_plog.sp_ins_log(v_rec_plog);
                end if;

          End If;
          v_o_return     := v_result;
          v_o_returnCode := 'Update Ci.Cipb SBMK Error ： '|| v_result || '：' || v_resultCode;
    Exception when others then
          v_rec_plog.userid    := v_i_user;
          v_rec_plog.jobid     := TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISSSSS');
          v_rec_plog.starttime := SYSDATE;
          v_rec_plog.typemk    := '1';
          v_rec_plog.levelmk   := '3'; 
          v_rec_plog.pseq      := '1';
          v_rec_plog.proctime  := SYSDATE;
          v_rec_plog.procname  := 'sp_BA_updSBMK';
          v_rec_plog.msg1      := 'Update Ci.Cipb SBMK Error ：：APNO='||v_i_apno||','||SQLCODE || SQLERRM;
          v_rec_plog.msg2      := dbms_utility.format_error_backtrace;
          pkg_plog.sp_ins_log(v_rec_plog);
    End;
    --Procedure sp_BA_updSBMK

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_updCIStatus.sp_BA_updUINMK
        PURPOSE:         更新CI.CIPB 被保險人管制加保註記 (UINMK)

        PARAMETER(IN):   *v_i_intyp         (varChar2)       -- 保險類別
                         *v_i_ciid          (varChar2)       -- 勞就保識別碼
                         *v_i_user          (varChar2)       -- 修改使用者
                         *v_i_uinmk         (varChar2)       -- 管制加保註記
                         *v_i_apno          (varChar2)       -- 勞保年金受理編號


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
    Procedure sp_BA_updUINMK (
        v_i_intyp               in      varChar2,
        v_i_ciid                in      varChar2,
        v_i_user                in      varChar2,
        v_i_befuinmk            in      varChar2,
        v_i_uinmk               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_issuym              in      varChar2,
        v_i_casetyp             in      varChar2,
        v_i_systp               in      varChar2,
        v_o_return              out     varChar2,
        v_o_returnCode          out     varChar2
    ) is

    v_result          VARCHAR2(1)         := NULL;
    v_resultCode      VARCHAR2(300)       := NULL;
    v_rec_plog plog%ROWTYPE;

    Begin
          /*
             2012/12/18 Modify By ChungYu 不給付案要清除管制加保註記 ，所以會傳Null 進來
          */
          If (Trim(v_i_intyp) Is Not Null) And (Trim(v_i_ciid) Is Not Null) And
             /*(Trim(v_i_uinmk) Is Not Null) And*/ (Trim(v_i_apno) Is Not Null) Then

              CI.CIPB_API_UINMK( v_i_ciid,       --  勞就保識別碼
                                 v_i_intyp,      --  保險類別
                                 v_i_user,       --  修改使用者
                                 v_i_systp,      --  修改系統程式
                                 v_i_uinmk,      --  領取失能給付註記
                                 v_result,       --  return 0,1,2
                                 v_resultCode    --  return [CIPB_API_SBMK1比對CIPB查無資料],[CIPB_API_SBMK1更新成功],[CIPB_API_SBMK1發生錯誤:SQLERRM]
                                );

              Insert Into BAUPDCIDBLOG (APNO, ISSUYM, CASETYP, CIID, INTYP, USERID, SYSTP, COLNAME, COLVALUEB, COLVALUEE, RESULTNO, RESULTCODE, PROCDATE)
                                Values (v_i_apno,
                                        v_i_issuym,
                                        v_i_casetyp,
                                        v_i_ciid,
                                        v_i_intyp,
                                        v_i_user,
                                        v_i_systp,
                                        'UINMK',
                                        v_i_befuinmk,
                                        v_i_uinmk,
                                        v_result,
                                        v_resultCode,
                                        to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                        );

               if v_result <> '1' then
                    v_rec_plog.userid    := v_i_user;
                    v_rec_plog.jobid     := TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISSSSS');
                    v_rec_plog.starttime := SYSDATE;
                    v_rec_plog.typemk    := '1';
                    v_rec_plog.levelmk   := '1'; --INFO
                    v_rec_plog.pseq      := '1';
                    v_rec_plog.proctime  := SYSDATE;
                    v_rec_plog.procname  := 'sp_BA_updUINMK';
                    v_rec_plog.msg1      := 'Update Ci.Cipb UINMK Error ：APNO='||v_i_apno||','|| v_result || '：' || v_resultCode;
                    pkg_plog.sp_ins_log(v_rec_plog);
                end if;
          End If;
          v_o_return     := v_result;
          v_o_returnCode := 'Update Ci.Cipb UINMK Error ： '|| v_result || '：' || v_resultCode;
    Exception when others then
          v_rec_plog.userid    := v_i_user;
          v_rec_plog.jobid     := TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISSSSS');
          v_rec_plog.starttime := SYSDATE;
          v_rec_plog.typemk    := '1';
          v_rec_plog.levelmk   := '3'; 
          v_rec_plog.pseq      := '1';
          v_rec_plog.proctime  := SYSDATE;
          v_rec_plog.procname  := 'sp_BA_updUINMK';
          v_rec_plog.msg1      := 'Update Ci.Cipb UINMK Error ：：APNO='||v_i_apno||','||SQLCODE || SQLERRM;
          v_rec_plog.msg2      := dbms_utility.format_error_backtrace;
          pkg_plog.sp_ins_log(v_rec_plog);
    End;
    --Procedure sp_BA_updUINMK

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_updCIStatus.sp_BA_updCheckMonth
        PURPOSE:         勞保年金月核定後，將本次核定年月所核發之新案事故者，
                         更新被保險人[永久註記]及[管制加保註記(UINMK)]。

        PARAMETER(IN):   *v_i_issuym        (varChar2)       -- 核定年月
                         *v_i_user          (varChar2)       -- 使用者帳號

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/04/18  ChungYu Lin  Created this Package.
        1.1   2014/05/07  ChungYu Lin  因為暫緩給付案於核定檔並不會押上帳務註記及日期，因此修改c_dataCur_BAAPP cursor.
        1.2   2015/04/01  ChungYu Lin  因老年年金清除臨時、永久註記及逕退，移到sp_BA_OldAgeDaily執行，因此sp_BA_updCheckMonth
                                       取消老年年金清除臨時、永久註記及逕退條件。
        1.3   2015/10/30  ChungYu Lin  老年年金恢復於月核定後，清除臨時、永久註記及逕退。
        1.4   2015/11/05  ChungYu Lin  修改老年年金於月核定後，上臨時、永久註記及逕退的判斷條件。

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_updCheckMonth (
        v_i_issuym             in      varChar2,
        v_i_user               in      varChar2
    ) is

      v_intyp                  VARCHAR2(1)             := '';         -- 被保險人勞保資料(L) 勞保自願職保資料(V)
      v_evtidn                 Baappbase.Evtidnno%TYPE := '';         -- 事故者身分證號
      v_payCode                VARCHAR2(1)             := '';         -- 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
      v_SBMK                   VARCHAR2(1)             := '';         -- 給付註記
      v_tempSBMK               VARCHAR2(1)             := '';         -- 給付註記
      v_UINMK                  VARCHAR2(1)             := '';         -- 管制加保註記
      v_tempUINMK              VARCHAR2(1)             := '';         -- 管制加保註記
      v_CIPB                   CIPB%ROWTYPE;
      v_result                 VARCHAR2(1)             := NULL;
      v_resultCode             VARCHAR2(300)           := NULL;
      v_systp                  VARCHAR2(20)            := 'BaSPMonth';
      v_upcode                 VARCHAR2(1)             := NULL;
      v_count                  NUMBER                  := 0;
      v_countDRAW              NUMBER                  := 0;          --受理編號存在逕退檔筆數
      v_countALL               NUMBER                  := 0;          --總筆數
      v_countPOC               NUMBER                  := 0;          --處理總筆數
      v_countSUC               NUMBER                  := 0;          --更新領取註記成功筆數
      v_countCase1G            NUMBER                  := 0;          --給付案G總筆數
      v_countCase3G            NUMBER                  := 0;          --不給付G案總筆數
      v_countCase1A            NUMBER                  := 0;          --給付案A總筆數
      v_countCase3A            NUMBER                  := 0;          --不給付A案總筆數
      v_countCase1Z            NUMBER                  := 0;          --給付案A總筆數
      v_countCase3Z            NUMBER                  := 0;          --不給付A案總筆數
      v_countToDRAW            NUMBER                  := 0;          --逕退總筆數
      v_countToDRAWSUC         NUMBER                  := 0;          --逕退成功筆數
      v_countToDRAWErr         NUMBER                  := 0;          --逕退失敗筆數
      v_countErr               NUMBER                  := 0;          --更新領取註記失敗筆數
      v_countBI                NUMBER                  := 0;          --查無事故者 BI CIPB筆數
      v_DupCount               NUMBER                  := 0;          --事故者CIPB暫存筆數
      v_countDupIdn            NUMBER                  := 0;          --事故者CIPB筆數大於一筆未經身分證重號檔選擇
      v_updBACIBMK             CIPB.BMK%TYPE           := '';         --更新BA CIPB BMK
      v_TXCD2                  BAWITHDRAW.TXCD2%TYPE   := '';         --逕退檔註記
      v_ciCIPB                 CI.CIPB%ROWTYPE;                       --承保系統被保險人基本資料 2013/03/07 Add By ChungYu
      v_ubno                   Baappbase.Lsubno%TYPE   := '';         --單位保險證號  2013/06/14 Add By ChungYu
      v_jobno                  mmjoblog.job_no%TYPE;
      v_starttime              TIMESTAMP;                             --開始時間

      --核定年月當月所核付的新案、不給付案、暫緩給付案清單
      CURSOR c_dataCur_BAAPP IS
        Select A.APNO,                                             -- 受理編號
               A.SEQNO,                                            -- 序號 2014/04/08 Add By ChungYu 回押 Ba.CIPB 要增加受理編號+序號
               A.APPDATE,                                          -- 受理日期
               A.PAYKIND,                                          -- 給付種類
               Decode(D.IDNNO,Null,A.EvtIdnno,D.IDNNO) As EVTIDNO, -- 事故者身分證號
               A.EVTBRDATE,                                        -- 事故者生日
               A.EVTNAME,                                          -- 事故者姓名
               A.EVTJOBDATE,                                       -- 事故日期
               A.APUBNO,                                           -- 申請單位保險證號  2013/06/14 老年逕退改用最後單位保險證號
               A.LSUBNO,                                           -- 最後單位保險證號  2013/06/14 老年逕退改用最後單位保險證號
               B.ADWKMK,                                           -- 加職註記
               C.MCHKTYP AS CASETYP,                               -- 案件類別
               C.APLPAYDATE,                                       -- 帳務日期
               E.NOTEMK,                                           -- 行政支援註記
               (Select COUNT(F1.Apno)
                  From BAAPPBASE F1
                 Where F1.APNO <> A.apno
                   And SUBSTR(F1.APNO,1,1) = SUBSTR(A.APNO,1,1)
                   And F1.Seqno = '0000'
                   And F1.CASETYP In ('2', '4')
                   And F1.EVTIDNNO = A.EVTIDNNO
                   And F1.EVTNAME = A.EVTNAME
                  And F1.EVTBRDATE = A.EVTBRDATE
                ) AS APCOUNT                                       -- 同給付別非同受理編號筆數
          From (Select A1.* From Baappbase A1
                 Where A1.APNO in (Select Distinct A2.Apno From BADAPR A2
                                    Where A2.Issuym = v_i_issuym
                                      And A2.seqno = '0000'
                                      And A2.MCHKTYP In ('1', '3', '6')     -- 2015/10/30 Modify By ChungYu 恢復老年年金清除臨時、永久註記及逕退條件。
                                      And A2.MTESTMK = 'F'
                                      And ((A2.Aplpaymk = '3' And  Trim(A2.Aplpaydate) Is Not Null)         -- 2014/05/07 Modify By  ChungYu 修改暫緩給付案讀取條件
                                        Or (A2.Aplpaymk Is Null And Trim(A2.Aplpaydate) Is Null And A1.Procstat = '80'))
                                      And Trim(A2.MANCHKMK) Is Not Null
                                  )
                   And A1.SEQNO = '0000') A,
               (Select APNO, SEQNO, ADWKMK From BAAPPEXPAND Where SEQNO = '0000') B,
               (Select Distinct C1.Apno, C1.MCHKTYP, C1.APLPAYDATE
                  From BaDapr C1
                 Where C1.Seqno = '0000'
                   And C1.MCHKTYP In ('1', '3', '6')                        -- 2015/10/30 Modify By ChungYu 恢復老年年金清除臨時、永久註記及逕退條件。
                   And C1.ISSUYM = v_i_issuym
                   And C1.MTESTMK = 'F'
                   And ((C1.Aplpaymk = '3' And Trim(C1.Aplpaydate) Is Not Null)              -- 2014/05/07 Modify By  ChungYu 修改暫緩給付案讀取條件
                     Or (C1.Aplpaymk Is Null And C1.Aplpaydate Is Null And C1.MCHKTYP = '3'))
                   And Trim(C1.MANCHKMK) Is Not Null) C,
               (Select D1.APNO, D1.IDNNO From BADUPEIDN D1 Where SELMK = '2' ) D,
               (Select E1.APNO, 'Y' As NOTEMK From MAADMREC E1
                 Where E1.LETTERTYPE = '21'
                   And (NVL(E1.DELMK, ' ') <> 'D')
                   And ((NVL(E1.NDOMK1, ' ') = '55') Or
                        (NVL(E1.NDOMK2, ' ') = '55')) )E
                 Where A.SEQNO = '0000'
                   And A.APNO = B.APNO(+)
                   And A.APNO = C.APNO
                   And A.APNO = D.APNO(+)
                   And A.APNO = E.APNO(+)
                 Order by A.APNO, C.MCHKTYP;
-- ============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
    PROCEDURE get_jobno
    IS
    BEGIN
       SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_jobno FROM DUAL;

    END get_jobno;
-- ============================================================================

--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------

    Begin
    --寫入開始LOG --(S)
    get_jobno;
    v_starttime := SYSTIMESTAMP;
    SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'sp_BA_updCheckMonth',
                    p_step   => '勞保年金月核定後，將本次核定年月所核發之新案事故者，更新被保險人及管制加保註記',
                    p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno);
    --寫入開始LOG --(E)

     If (Trim(v_i_issuym) Is Not Null) And (Trim(v_i_user) Is Not Null) Then
         For v_CurBAAPP In c_dataCur_BAAPP Loop

             v_countALL:= v_countALL + 1;
             v_evtidn  := v_CurBAAPP.Evtidno;
             v_payCode := Substr(v_CurBAAPP.Apno,1,1);
             v_TXCD2   := '';


             -- 受理案件是否已寫入逕退檔
             SELECT COUNT(*) INTO v_count FROM BAWITHDRAW
              WHERE apno = v_CurBAAPP.Apno;

             -- 2012/10/17 讀取逕退檔註記
             If v_count > 0 Then
                SELECT Trim(T.TXCD2) INTO v_TXCD2 FROM BAWITHDRAW T
                 WHERE T.APNO = v_CurBAAPP.Apno
                   AND Rownum = 1;
             End If;

              -- 2015/11/05 Modify By ChungYu 因老年於日編審時會去逕退，失能及遺屬無每日逕退，因此修改判斷條件，
              --                              老年若逕退後仍可進入上領取給付註記及管制加保註記。

              If (v_count > 0) and (v_TXCD2 = 'Y') and (v_payCode <> 'L') Then
                 v_countDRAW := v_countDRAW + 1;
              Else

                 --  事故者加職註記為2時，改讀取加職段資料
                 If v_CurBAAPP.Adwkmk = '2' Then
                    v_intyp := 'V';
                 Else
                    v_intyp := 'L';
                 End If;

                 If (v_evtidn Is Not Null) Then
                   Begin

                     Select Count(IDN) Into v_DupCount From CIPB
                      Where INTYP = v_intyp
                        And IDN Like v_evtidn||'%'
                        And APNO = v_CurBAAPP.APNO
                        And SEQNO = v_CurBAAPP.SEQNO;

                     If v_DupCount = 1 Then

                         Select * Into v_CIPB From CIPB
                          Where INTYP = v_intyp
                            And IDN Like v_evtidn||'%'
                            And APNO = v_CurBAAPP.APNO
                            And SEQNO = v_CurBAAPP.SEQNO;

                         -- 2013/03/07 比對被保險人註記狀態改讀取承保CIPB
                         If v_CIPB.Ciid Is Not Null Then
                            Select * Into v_ciCIPB From CI.CIPB
                             Where FTYP = v_intyp
                               And CIID = v_CIPB.Ciid;
                         End if;
                         -- 2013/03/07 比對被保險人註記狀態改讀取承保CIPB

                         If (v_CIPB.Ciid Is Not Null) And (v_ciCIPB.Ciid Is Not Null) Then
                            v_countPOC := v_countPOC +1 ;
                            v_upcode := null;
                            /*  永久註記：

                                老年年金：
                                於每月勞保老年年金月核定作業後，讀取該月核付之新案，將對應之「領取老年給付註記_SBMK2」
                                欄位值更新為3-已核付。

                                失能年金：
                                於每月勞保失能年金月核定作業後，讀取該月核付之新案，於對應之「領取失能給付註記_SBMK1」
                                欄寫入4-已核付。

                                遺屬年金：
                                於每月勞保遺屬年金月核定作業後，讀取該月核付之新案，於對應之「領取死亡給付註記_SBMK3」
                                欄寫入3-已核付。
                            */
                            If v_CurBAAPP.CASETYP = '1' Then
                               If (v_payCode = 'L') Or (v_payCode = 'S') Then

                                  -- 取得老年及遺屬當前的給付註記
                                  If (v_payCode = 'L') Then
                                      v_tempSBMK  := v_ciCIPB.SBmk2;
                                      v_tempUINMK := v_ciCIPB.Uinmk;
                                  Elsif (v_payCode = 'S') Then
                                      v_tempSBMK  := v_ciCIPB.SBmk3;
                                      v_tempUINMK := v_ciCIPB.Uinmk;
                                  End If;

                                  v_SBMK := 3;
                                  v_UINMK := 'Y';
                                  v_upcode := 'G';       -- G：符合更新已領給付條件更新成功

                                  If (NVL(v_tempSBMK,' ') = NVL(v_SBMK,' ')) then
                                      v_upcode := 'A';   -- A：現存已領給付註記與欲更新相同
                                  Else
                                     if (v_tempSBMK = '1') Or (trim(v_tempSBMK) Is Null) Then

                                         sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                        v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                        v_CIPB.Ciid,             --  勞就保識別碼
                                                        v_i_user,                --  UserID
                                                        v_tempSBMK,              --  Befort SIDMK Value
                                                        v_SBMK,                  --  SIDMK Value
                                                        v_CurBAAPP.Apno,         --  受理編號
                                                        v_i_issuym,              --  核定年月
                                                        v_CurBAAPP.casetyp,      --  案件類別
                                                        v_systp,
                                                        v_result,
                                                        v_resultCode
                                                      );

                                         -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                         If (v_result = '1') Then
                                             If (v_payCode = 'L')  Then
                                                 v_updBACIBMK := Substr(v_CIPB.Bmk,1,1)||v_SBMK||Substr(v_CIPB.Bmk,3,8);
                                             Elsif (v_payCode = 'S')  Then
                                                 v_updBACIBMK := Substr(v_CIPB.Bmk,1,2)||v_SBMK||Substr(v_CIPB.Bmk,4,7);
                                             End if;

                                             Update CIPB
                                                Set BMK = v_updBACIBMK
                                              Where INTYP = v_CIPB.Intyp
                                                And IDN   = v_CIPB.Idn
                                                And APNO  = v_CurBAAPP.APNO
                                                And SEQNO = v_CurBAAPP.SEQNO;

                                         End if;

                                         -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                      else
                                         v_upcode := 'Z';             -- Z:現存已領給付註記不符可更新條件
                                     end if;
                                  end If;
                                  -- 更新失敗寫LOG，並將v_upcode設為N
                                  if v_result <> '1' Then
                                     v_upcode := 'N';                 -- N:表示失敗
                                     sp_saveMsg( '0',
                                                 v_CurBAAPP.Apno,
                                                 v_i_issuym,
                                                 v_CIPB.CIID,
                                                 v_intyp,
                                                 v_i_user,
                                                 v_resultCode
                                               );
                                  End if;
                                  --  若更新SBMK 成功，繼續更新UNIMK
                                  /*
                                      老年、失能及遺屬年金核付案件：於寫入前項「領取給付註記」之永久註記同時，
                                      於本欄位寫入管制註記”Y”。
                                  */
                                  if (v_result = '1') And (v_upcode = 'G') And
                                     (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                                        sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                        v_CIPB.Ciid,             --  勞就保識別碼
                                                        v_i_user,                --  UserID
                                                        v_tempUINMK,             --  Befort UINMK Value
                                                        v_UINMK,                 --  UINMK Value
                                                        v_CurBAAPP.Apno,         --  受理編號
                                                        v_i_issuym,              --  核定年月
                                                        v_CurBAAPP.casetyp,      --  案件類別
                                                        v_systp,
                                                        v_result,
                                                        v_resultCode
                                                       );

                                         -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                         If (v_result = '1') Then

                                             Update CIPB
                                                Set UINMK = v_UINMK
                                              Where INTYP = v_CIPB.Intyp
                                                And IDN   = v_CIPB.Idn
                                                And APNO  = v_CurBAAPP.APNO
                                                And SEQNO = v_CurBAAPP.SEQNO;

                                         End if;
                                         -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位

                                  End if;
                                  -- 更新失敗寫LOGMsg
                                  if v_result <> '1' Then
                                     sp_saveMsg( '0',
                                                 v_CurBAAPP.Apno,
                                                 v_i_issuym,
                                                 v_CIPB.CIID,
                                                 v_intyp,
                                                 v_i_user,
                                                 v_resultCode
                                               );
                                  End if;
                               /*
                                  失能年金：
                                  於每月勞保失能年金月核定作業後，讀取該月核付之新案，於對應之「領取失能給付註記_SBMK1」
                                  欄寫入4-已核付。
                               */
                               Elsif v_payCode = 'K' Then
                                  v_tempSBMK  := v_ciCIPB.SBmk1;
                                  v_tempUINMK := v_ciCIPB.Uinmk;
                                  v_SBMK := 4;
                                  v_UINMK := 'Y';
                                  v_upcode := 'G';        -- G：符合更新已領給付條件更新成功
                                  If (NVL(v_tempSBMK,' ') = NVL(v_SBMK,' ')) then
                                      v_upcode := 'A';    -- A：現存已領給付註記與欲更新相同
                                  Else
                                      if (v_tempSBMK = '1') Or (trim(v_tempSBMK) Is Null) Then
                                          sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                         v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                         v_CIPB.Ciid,             --  勞就保識別碼
                                                         v_i_user,                --  UserID
                                                         v_tempSBMK,              --  Befort SIDMK Value
                                                         v_SBMK,                  --  SIDMK Value
                                                         v_CurBAAPP.Apno,         --  受理編號
                                                         v_i_issuym,              --  核定年月
                                                         v_CurBAAPP.casetyp,      --  案件類別
                                                         v_systp,
                                                         v_result,
                                                         v_resultCode
                                                       );

                                         -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                         If (v_result = '1') Then
                                             v_updBACIBMK := v_SBMK||Substr(v_CIPB.Bmk,2,9);

                                             Update CIPB
                                                Set BMK = v_updBACIBMK
                                              Where INTYP = v_CIPB.Intyp
                                                And IDN   = v_CIPB.Idn
                                                And APNO  = v_CurBAAPP.APNO
                                                And SEQNO = v_CurBAAPP.SEQNO;

                                         End if;

                                         -- 2012/05/18 新增同時同步BA CIPB BMK 欄位

                                      else
                                         v_upcode := 'Z';           -- Z:現存已領給付註記不符可更新條件
                                     end if;
                                  end If;
                                  -- 更新失敗寫LOG，並將v_upcode設為N
                                  if v_result <> '1' Then
                                     v_upcode := 'N';               -- N:表示失敗
                                     sp_saveMsg( '0',
                                                 v_CurBAAPP.Apno,
                                                 v_i_issuym,
                                                 v_CIPB.CIID,
                                                 v_intyp,
                                                 v_i_user,
                                                 v_resultCode
                                               );
                                  End if;
                                  --  若更新SBMK 成功，繼續更新UNIMK
                                  if (v_result = '1') And (v_upcode = 'G') And
                                     (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                                          sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                          v_CIPB.Ciid,             --  勞就保識別碼
                                                          v_i_user,                --  UserID
                                                          v_tempUINMK,             --  Befort UINMK Value
                                                          v_UINMK,                 --  UINMK Value
                                                          v_CurBAAPP.Apno,         --  受理編號
                                                          v_i_issuym,              --  核定年月
                                                          v_CurBAAPP.casetyp,      --  案件類別
                                                          v_systp,
                                                          v_result,
                                                          v_resultCode
                                                         );

                                          -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                          If (v_result = '1') Then

                                              Update CIPB
                                                 Set UINMK = v_UINMK
                                               Where INTYP = v_CIPB.Intyp
                                                 And IDN   = v_CIPB.Idn
                                                 And APNO  = v_CurBAAPP.APNO
                                                 And SEQNO = v_CurBAAPP.SEQNO;

                                          End if;
                                          -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                  End if;
                                  -- 更新失敗寫LOG
                                  if v_result <> '1' Then
                                     sp_saveMsg( '0',
                                                 v_CurBAAPP.Apno,
                                                 v_i_issuym,
                                                 v_CIPB.CIID,
                                                 v_intyp,
                                                 v_i_user,
                                                 v_resultCode
                                               );
                                  End if;
                               End if;

                               if v_upcode = 'G' Then
                                  v_countCase1G := v_countCase1G + 1;
                               elsif v_upcode = 'A' Then
                                  v_countCase1A := v_countCase1A + 1;
                               elsif v_upcode = 'Z' Then
                                  v_countCase1Z := v_countCase1Z + 1;
                               end if;
                            --  不給付案及暫緩給付案，清除給付註記
                            /*  遺屬年金：
                                於每月勞保遺屬年金月核定作業後，讀取該月案件類別為不給付案及暫緩給付案，
                                且無同給付別、同IDN、出生及姓名之續發案或結案者，將對應之「領取死亡給付註記」1-已受理、3-已核付或空值，
                                更新為9-不給付案（遺屬年金），其他欄位值不予更新。
                            */
                            Elsif (v_CurBAAPP.CASETYP = '3' Or v_CurBAAPP.CASETYP = '6') Then
                               v_upcode := 'G';
                               If (v_payCode = 'S') Then
                                   If (v_CurBAAPP.APCOUNT = 0) And ((v_ciCIPB.SBmk3 = '1') Or
                                      (v_ciCIPB.SBmk3 = '3') Or (Trim(v_ciCIPB.SBmk3) Is Null)) Then
                                        v_tempSBMK  := v_ciCIPB.SBmk3;
                                        v_SBMK := 9;
                                        If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                            sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                           v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                           v_CIPB.Ciid,             --  勞就保識別碼
                                                           v_i_user,                --  UserID
                                                           v_tempSBMK,              --  Befort SIDMK Value
                                                           v_SBMK,                  --  SIDMK Value
                                                           v_CurBAAPP.Apno,         --  受理編號
                                                           v_i_issuym,              --  核定年月
                                                           v_CurBAAPP.casetyp,      --  案件類別
                                                           v_systp,
                                                           v_result,
                                                           v_resultCode
                                                          );
                                            If v_result <> '1' Then
                                               v_upcode := 'N';
                                               sp_saveMsg( '0',
                                                           v_CurBAAPP.Apno,
                                                           v_i_issuym,
                                                           v_CIPB.CIID,
                                                           v_intyp,
                                                           v_i_user,
                                                           v_resultCode
                                                         );
                                            Else
                                                 v_updBACIBMK := Substr(v_CIPB.Bmk,1,2)||v_SBMK||Substr(v_CIPB.Bmk,4,7);
                                                 Update CIPB
                                                    Set BMK = v_updBACIBMK
                                                  Where INTYP = v_CIPB.Intyp
                                                    And IDN   = v_CIPB.Idn
                                                    And APNO  = v_CurBAAPP.APNO
                                                    And SEQNO = v_CurBAAPP.SEQNO;
                                            End if;
                                        Else
                                            v_upcode := 'A';
                                        End if;
                                   Else
                                       v_upcode := 'Z';
                                   End if;

                               -- 2015/10/30 恢復老年年金清除臨時、永久註記及逕退條件。
                               /* 老年年金清除臨時及永久註記之條件：
                                  （1）「領取老年給付註記CIPB_API_SBMK2」：於每月勞保老年年金月核定作業後，
                                        讀取該月案件類別為不給付及暫緩給付案，且無同給付別、同IDN、出生及姓名之續發案或結案者，
                                        將本欄之1-已受理、3-已核付（老年年金）清為空白，  其他欄位值不予清除。
                               */
                               Elsif (v_payCode = 'L') Then
                                   If (v_CurBAAPP.APCOUNT = 0) And ((v_ciCIPB.SBmk2 = '1') Or
                                      (v_ciCIPB.SBmk2 = '3')) Then
                                       v_tempSBMK  := v_ciCIPB.SBmk2;
                                       v_SBMK := Null;
                                       If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                           sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                          v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                          v_CIPB.Ciid,             --  勞就保識別碼
                                                          v_i_user,                --  UserID
                                                          v_tempSBMK,              --  Befort SIDMK Value
                                                          v_SBMK,                  --  SIDMK Value
                                                          v_CurBAAPP.Apno,         --  受理編號
                                                          v_i_issuym,              --  核定年月
                                                          v_CurBAAPP.casetyp,      --  案件類別
                                                          v_systp,
                                                          v_result,
                                                          v_resultCode
                                                         );
                                           If v_result <> '1' Then
                                              v_upcode := 'N';
                                               sp_saveMsg( '0',
                                                           v_CurBAAPP.Apno,
                                                           v_i_issuym,
                                                           v_CIPB.CIID,
                                                           v_intyp,
                                                           v_i_user,
                                                           v_resultCode
                                                         );
                                           Else
                                                 v_updBACIBMK := Substr(v_CIPB.Bmk,1,1)||NVL(v_SBMK,' ')||Substr(v_CIPB.Bmk,3,8);
                                                 Update CIPB
                                                    Set BMK = v_updBACIBMK
                                                  Where INTYP = v_CIPB.Intyp
                                                    And IDN   = v_CIPB.Idn
                                                    And APNO  = v_CurBAAPP.APNO
                                                    And SEQNO = v_CurBAAPP.SEQNO;
                                           End if;
                                      Else
                                           v_upcode := 'A';
                                      End if;
                                   Else
                                       v_upcode := 'Z';
                                   End if;
                                   --  清除管制加保註記
                                   /*
                                      老年及失能年金不給付及暫緩給付案件：於完成清除前述「領取給付註記」之永久註記同時，
                                      如老年、失能、死亡及失蹤之領取給付註記為空值或＜2，且「管制加保註記」之欄位值為Y時，
                                      清為空白；未清除永久註記者，本欄位不須清為空白。
                                   */
                                 If (v_result = '1') And ((v_upcode <> 'Z') Or ((v_upcode <> 'N'))) Then
                                      If (NVL(trim(v_ciCIPB.SBmk1),'1') < '2' ) And
                                         (NVL(trim(v_ciCIPB.SBmk2),'1') < '2' ) And
                                         (NVL(trim(v_ciCIPB.SBmk3),'1') < '2' ) And
                                         (NVL(trim(v_ciCIPB.SBmk4),'1') < '2' ) And
                                         (NVL(v_ciCIPB.UINMK,' ') = 'Y') Then
                                            v_tempUINMK := v_ciCIPB.UINMK;
                                            v_UINMK := Null;
                                          If (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                                                sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                                v_CIPB.Ciid,             --  勞就保識別碼
                                                                v_i_user,                --  UserID
                                                                v_tempSBMK,              --  Befort SIDMK Value
                                                                v_UINMK,                 --  SIDMK Value
                                                                v_CurBAAPP.Apno,         --  受理編號
                                                                v_i_issuym,              --  核定年月
                                                                v_CurBAAPP.casetyp,      --  案件類別
                                                                v_systp,
                                                                v_result,
                                                                v_resultCode
                                                               );
                                              If v_result <> '1' Then
                                                   sp_saveMsg( '0',
                                                               v_CurBAAPP.Apno,
                                                               v_i_issuym,
                                                               v_CIPB.CIID,
                                                               v_intyp,
                                                               v_i_user,
                                                               v_resultCode
                                                             );
                                              Else
                                                   -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                                   Update CIPB
                                                      Set UINMK = v_UINMK
                                                    Where INTYP = v_CIPB.Intyp
                                                      And IDN   = v_CIPB.Idn
                                                      And APNO  = v_CurBAAPP.APNO
                                                      And SEQNO = v_CurBAAPP.SEQNO;
                                                   -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                              End if;
                                          End if;
                                   End if;
                               End if;



                               /* 失能年金清除臨時及永久註記之條件：
                                  （1）「領取失能給付註記CIPB_API_SBMK1」」：於每月勞保失能年金月核定作業後，
                                        讀取該月案件類別為不給付及暫緩給付案，且無同給付別、同IDN、出生及姓名之續發案或結案者，
                                        將本欄之1-已受理、4-已核付（失能年金）清為空白，其他欄位值不予清除。
                               */
                               Elsif (v_payCode = 'K') Then
                                      v_upcode := 'G';
                                     If (v_CurBAAPP.APCOUNT = 0) And
                                        ((v_ciCIPB.SBmk1 = '1') Or (v_ciCIPB.SBmk1 = '4')) Then
                                          v_tempSBMK  := v_ciCIPB.SBmk1;
                                          v_SBMK := Null;
                                          If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                              sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                             v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                             v_CIPB.Ciid,             --  勞就保識別碼
                                                             v_i_user,                --  UserID
                                                             v_tempSBMK,              --  Befort SIDMK Value
                                                             v_SBMK,                  --  SIDMK Value
                                                             v_CurBAAPP.Apno,         --  受理編號
                                                             v_i_issuym,              --  核定年月
                                                             v_CurBAAPP.casetyp,      --  案件類別
                                                             v_systp,
                                                             v_result,
                                                             v_resultCode
                                                            );
                                               If v_result <> '1' Then
                                                  v_upcode := 'N';
                                                  sp_saveMsg( '0',
                                                              v_CurBAAPP.Apno,
                                                              v_i_issuym,
                                                              v_CIPB.CIID,
                                                              v_intyp,
                                                              v_i_user,
                                                              v_resultCode
                                                            );
                                               Else
                                                   -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                                   v_updBACIBMK := NVL(v_SBMK,' ') || Substr(v_CIPB.Bmk,2,9);
                                                   Update CIPB
                                                      Set BMK = v_updBACIBMK
                                                    Where INTYP = v_CIPB.Intyp
                                                      And IDN   = v_CIPB.Idn
                                                      And APNO  = v_CurBAAPP.APNO
                                                      And SEQNO = v_CurBAAPP.SEQNO;
                                                   -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                               End if;
                                          else
                                              v_upcode := 'A';
                                          end if;
                                     Else
                                         v_upcode := 'Z';
                                     End if;
                                     --  清除管制加保註記
                                   /*
                                      老年及失能年金不給付及暫緩給付案件：於完成清除前述「領取給付註記」之永久註記同時，
                                      如失能之領取給付註記不為空值，老年、死亡及失蹤之領取給付註記為空值或＜2，且「管制加保註記」之欄位值為Y時，
                                      清為空白；未清除永久註記者，本欄位不須清為空白。
                                   */
                                   If (v_result = '1') And ((v_upcode <> 'Z') Or ((v_upcode <> 'N'))) Then
                                      If (Trim(v_ciCIPB.SBmk1) Is Not Null ) And
                                         (NVL(v_ciCIPB.SBmk2,'1') < '2' ) And
                                         (NVL(v_ciCIPB.SBmk3,'1') < '2' ) And
                                         (NVL(v_ciCIPB.SBmk4,'1') < '2' ) And
                                         (NVL(v_ciCIPB.UINMK,' ') = 'Y') Then
                                            v_tempUINMK := v_ciCIPB.UINMK;
                                            v_UINMK := Null;
                                            If (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                                                sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                                v_CIPB.Ciid,             --  勞就保識別碼
                                                                v_i_user,                --  UserID
                                                                v_tempSBMK,              --  Befort SIDMK Value
                                                                v_UINMK,                 --  SIDMK Value
                                                                v_CurBAAPP.Apno,         --  受理編號
                                                                v_i_issuym,              --  核定年月
                                                                v_CurBAAPP.casetyp,      --  案件類別
                                                                v_systp,
                                                                v_result,
                                                                v_resultCode
                                                               );
                                                If v_result <> '1' Then
                                                   sp_saveMsg( '0',
                                                               v_CurBAAPP.Apno,
                                                               v_i_issuym,
                                                               v_CIPB.CIID,
                                                               v_intyp,
                                                               v_i_user,
                                                               v_resultCode
                                                             );
                                                Else
                                                  -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                                   Update CIPB
                                                      Set UINMK = v_UINMK
                                                    Where INTYP = v_CIPB.Intyp
                                                      And IDN   = v_CIPB.Idn
                                                      And APNO  = v_CurBAAPP.APNO
                                                      And SEQNO = v_CurBAAPP.SEQNO;
                                                   -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                                End if;
                                             End if;
                                      End if;
                                   End if;
                               End if;

                               if v_upcode = 'G' Then
                                  v_countCase3G := v_countCase3G + 1;
                               elsif v_upcode = 'A' Then
                                  v_countCase3A := v_countCase3A + 1;
                               elsif v_upcode = 'Z' Then
                                  v_countCase3Z := v_countCase3Z + 1;
                               end if;

                            End if;
                            -- 寫入逕退檔
                            If (v_result = '1') and ((Trim(v_TXCD2) <> 'Y') Or (Trim(v_TXCD2) Is Null)) Then  -- 2015/12/11 Modify By ChungYu 避免老年重複逕退，以及失能遺屬月核後未逕退到。
                                v_countSUC := v_countSUC + 1;
                                If (v_count = 0) Then   -- 2012/10/17 Add By ChungYu

                                    --2013/07/11 Modify By ChungYu 老年新案逕退改用最後單位
                                    If ((v_payCode = 'L') And (v_CurBAAPP.CASETYP = '1')) Then
                                        v_ubno := v_CurBAAPP.LSUBNO;
                                    Else
                                        v_ubno := v_CurBAAPP.APUBNO;
                                    End If;
                                    --2013/07/11 老年新案逕退改用最後單位

                                    Insert Into BAWITHDRAW (APNO, ISSUYM, CASETYP, INTYP, PAYKIND,
                                                            UBNO, EVTIDNNO, EVTBRDATE, EVTNAME, EVTJOBDATE,
                                                            APLPAYDATE, APPDATE, DEPT, VMK, UPCODE,
                                                            PBSTAT, PBSTAT2, PBUINMK, PBUINMK2, TXCD2,
                                                            SPCH, CIID, NDOMK, HCODE,  PROCTIME)
                                                    VALUES (v_CurBAAPP.APNO, v_i_issuym, v_CurBAAPP.CASETYP, v_CIPB.Intyp, v_CurBAAPP.PAYKIND,
                                                            v_ubno, v_CurBAAPP.EVTIDNO, v_CurBAAPP.EVTBRDATE, v_CurBAAPP.EVTNAME, v_CurBAAPP.EVTJOBDATE,
                                                            v_CurBAAPP.APLPAYDATE, v_CurBAAPP.APPDATE, '', Decode(v_intyp,'V','V',Null),v_upcode,
                                                            v_tempSBMK, v_SBMK,v_tempUINMK, v_UINMK,'',
                                                            '', v_CIPB.CIID, v_CurBAAPP.NOTEMK, '', to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                            );
                                Else
                                    Update BAWITHDRAW Set APLPAYDATE = v_CurBAAPP.APLPAYDATE,
                                                          UPCODE     = v_upcode,
                                                          PBSTAT     = v_tempSBMK,
                                                          PBSTAT2    = v_SBMK,
                                                          PBUINMK    = v_tempUINMK,
                                                          PBUINMK2   = v_UINMK,
                                                          NDOMK      = v_CurBAAPP.NOTEMK,
                                                          PROCTIME   = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                    Where APNO = v_CurBAAPP.APNO;
                                End If;
                               /* 老年年金：
                                  -- 2015/10/30 恢復老年年金清除臨時、永久註記及逕退條件。

                                  1、於每月勞保老年年金月核定作業後，讀取該月核付，且給付主檔之申請單位保險證號非空白之新案，
                                     以事故日期為退保日期產生逕退檔。
                                  2、於每月勞保老年年金月核定作業後，讀取該月不給付及暫緩給付案，當給付主檔之申請單位保險證
                                     號非空白，且行政支援記錄檔中函別21之處理註記(一)及處理註記（二）非55者，以事故日期為退
                                     保日期產生逕退檔。
                               */
                                If ( v_payCode = 'L' ) Then
                                     If ((v_CurBAAPP.CASETYP = '1') And (Trim(v_CurBAAPP.LSUBNO) Is Not Null)) Or
                                          (((v_CurBAAPP.CASETYP = '3') Or (v_CurBAAPP.CASETYP = '6')) And (NVL(v_CurBAAPP.NOTEMK,' ') <> 'Y'))  Then
                                             v_countToDRAW := v_countToDRAW + 1;

                                             --  2013/07/10 月核逕退的新案核付案件用最後單位來逕退,不給付案則為申請單位
                                                 v_ubno := '';
                                                 If (v_CurBAAPP.CASETYP = '1') Then
                                                     v_ubno := v_CurBAAPP.LSUBNO;
                                                 Else
                                                     v_ubno := v_CurBAAPP.APUBNO;
                                                 End If;
                                             --  2013/07/10

                                             CI.CI040M020( v_intyp,                        --  保險別    L、V、F
                                                           v_CurBAAPP.PAYKIND,             --  核付種類  45 or 35 ...
                                                           v_CurBAAPP.APLPAYDATE,          --  核付日期
                                                           v_ubno,                         --  保險證號
                                                           SUBSTR(v_CurBAAPP.EVTIDNO,1,10),--  身分證號   2012/10/04 ChungYu Modify
                                                           v_CurBAAPP.EVTBRDATE,           --  出生日期
                                                           v_CurBAAPP.EVTNAME,             --  姓名
                                                           v_CurBAAPP.APNO,                --  受理號碼
                                                           v_CurBAAPP.EVTJOBDATE,          --  事故日期
                                                           v_CurBAAPP.APPDATE,             --  受理日期
                                                           to_Char(Sysdate,'YYYYMMDD'),    --  處理日期
                                                           to_Char(Sysdate,'HH24MISS'),    --  處理時間
                                                           '',                             --  工作部門
                                                           '',                             --  輕重殘註記   only K = '1'
                                                           '',                             --  育嬰續保起日
                                                           '',                             --  育嬰續保迄日
                                                           '',                             --  育嬰續保申請日
                                                           v_result,
                                                           v_resultCode);
                                     End if;
                                /* 失能年金：
                                   於每月勞保失能年金月核定作業後，讀取該月核付之新案，以事故日期為退保日期產生逕退檔。
                                */
                                Elsif ( v_payCode = 'K' ) Then
                                     If (v_CurBAAPP.CASETYP = '1') Then
                                             v_countToDRAW := v_countToDRAW + 1;
                                             CI.CI040M020( v_intyp,                        --  保險別    L、V、F
                                                           v_CurBAAPP.PAYKIND,             --  核付種類  45 or 35 ...
                                                           v_CurBAAPP.APLPAYDATE,          --  核付日期
                                                           v_CurBAAPP.APUBNO,              --  申請保險證號
                                                           SUBSTR(v_CurBAAPP.EVTIDNO,1,10),--  身分證號   2012/10/04 ChungYu Modify
                                                           v_CurBAAPP.EVTBRDATE,           --  出生日期
                                                           v_CurBAAPP.EVTNAME,             --  姓名
                                                           v_CurBAAPP.APNO,                --  受理號碼
                                                           v_CurBAAPP.EVTJOBDATE,          --  事故日期
                                                           v_CurBAAPP.APPDATE,             --  受理日期
                                                           to_Char(Sysdate,'YYYYMMDD'),    --  處理日期
                                                           to_Char(Sysdate,'HH24MISS'),    --  處理時間
                                                           '',                             --  工作部門
                                                           '1',                            --  輕重殘註記   only K = '1'
                                                           '',                             --  育嬰續保起日
                                                           '',                             --  育嬰續保迄日
                                                           '',                             --  育嬰續保申請日
                                                           v_result,
                                                           v_resultCode);
                                     End if;
                                /* 遺屬年金：
                                   於每月勞保遺屬年金月核定作業後，讀取該月核付之新案、不給付及暫緩給付案，以事故日期為退保日期產生逕退檔。
                                */
                                Elsif ( v_payCode = 'S' ) Then
                                            v_countToDRAW := v_countToDRAW + 1;
                                            CI.CI040M020( v_intyp,                        --  保險別    L、V、F
                                                          v_CurBAAPP.PAYKIND,             --  核付種類  45 or 35 ...
                                                          v_CurBAAPP.APLPAYDATE,          --  核付日期
                                                          v_CurBAAPP.APUBNO,              --  申請保險證號
                                                          SUBSTR(v_CurBAAPP.EVTIDNO,1,10),--  身分證號   2012/10/04 ChungYu Modify
                                                          v_CurBAAPP.EVTBRDATE,           --  出生日期
                                                          v_CurBAAPP.EVTNAME,             --  姓名
                                                          v_CurBAAPP.APNO,                --  受理號碼
                                                          v_CurBAAPP.EVTJOBDATE,          --  事故日期
                                                          v_CurBAAPP.APPDATE,             --  受理日期
                                                          to_Char(Sysdate,'YYYYMMDD'),    --  處理日期
                                                          to_Char(Sysdate,'HH24MISS'),    --  處理時間
                                                          '',                             --  工作部門
                                                          '',                             --  輕重殘註記   only K = '1'
                                                          '',                             --  育嬰續保起日
                                                          '',                             --  育嬰續保迄日
                                                          '',                             --  育嬰續保申請日
                                                          v_result,
                                                          v_resultCode);
                                End if;
                                -- 更新逕退檔
                                IF NVL(v_result,'1') = '1' Then
                                   v_countToDRAWSUC := v_countToDRAWSUC + 1;
                                     Update BAWITHDRAW
                                        Set TXCD2 = 'Y'
                                      Where APNO = v_CurBAAPP.APNO;
                                Else
                                   v_countToDRAWErr := v_countToDRAWErr + 1;
                                End if;
                            Else
                               If (v_TXCD2 = 'Y') then   --2015/11/05 Modify By ChungYu
                                   v_countDRAW := v_countDRAW + 1;
                               Else
                                   v_countErr := v_countErr + 1;
                               End if;
                            End if;
                         End If;
                     Else
                       v_countDupIdn := v_countDupIdn + 1;
                     End If;
                  Exception
                    when NO_DATA_FOUND then
                         sp_saveMsg( '0',
                                     v_CurBAAPP.Apno,
                                     '',
                                     '',
                                     v_intyp,
                                     v_i_user,
                                     '查無事故者 BA.CIPB 資料'
                                   );
                         v_countBI := v_countBI + 1;
                  End;

                 End If;
             -- dbms_output.put_line(' PKG_BA_updCIStatus-sp_BA_updCheckMonth----------->>[ Insert Error Log ]');
          End If;

      End Loop;
         sp_saveMsg( '1','','','','',v_i_user,' 總筆數                 = ' || v_countALL);
         sp_saveMsg( '1','','','','',v_i_user,' 受理編號存在逕退檔筆數 = ' || v_countDRAW);
         sp_saveMsg( '1','','','','',v_i_user,' 處理總筆數             = ' || v_countPOC);
         sp_saveMsg( '1','','','','',v_i_user,' 更新領取註記成功筆數   = ' || v_countSUC);
         sp_saveMsg( '1','','','','',v_i_user,' 給付案G總筆數          = ' || v_countCase1G);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付G案總筆數        = ' || v_countCase3G);
         sp_saveMsg( '1','','','','',v_i_user,' 給付案A總筆數          = ' || v_countCase1A);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付A案總筆數        = ' || v_countCase3A);
         sp_saveMsg( '1','','','','',v_i_user,' 給付案Z總筆數          = ' || v_countCase1Z);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付Z案總筆數        = ' || v_countCase3Z);
         sp_saveMsg( '1','','','','',v_i_user,' 更新領取註記失敗筆數   = ' || v_countErr);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退總筆數             = ' || v_countToDRAW);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退成功筆數           = ' || v_countToDRAWSUC);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退失敗筆數           = ' || v_countToDRAWErr);
         sp_saveMsg( '1','','','','',v_i_user,' 查無事故者 BA.CIPB 資料= ' || v_countBI);
         sp_saveMsg( '1','','','','',v_i_user,' 未經身分證重號檔選擇   = ' || v_countDupIdn);
         Commit;
     Else
         dbms_output.put_line(' PKG_BA_updCIStatus-sp_BA_updCheckMonth----------->>[ 核定年月或使用者帳號不可為空白 ]');
     End If;
     SP_BA_RECJOBLOG (p_job_no => v_jobno,
                      p_job_id => 'sp_BA_updCheckMonth',
                      p_step   => '勞保年金月核定後，將本次核定年月所核發之新案事故者，更新被保險人及管制加保註記',
                      p_memo   => '結束時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')'||CHR(10)||
                                                  ' 總筆數                 = (' || v_countALL ||')'||CHR(10)||
                                                  ' 受理編號存在逕退檔筆數 = (' || v_countDRAW ||')'||CHR(10)||
                                                  ' 處理總筆數             = (' || v_countPOC ||')'||CHR(10)||
                                                  ' 更新領取註記成功筆數   = (' || v_countSUC ||')'||CHR(10)||
                                                  ' 給付案G總筆數          = (' || v_countCase1G ||')'||CHR(10)||
                                                  ' 不給付G案總筆數        = (' || v_countCase3G ||')'||CHR(10)||
                                                  ' 給付案A總筆數          = (' || v_countCase1A ||')'||CHR(10)||
                                                  ' 不給付A案總筆數        = (' || v_countCase3A ||')'||CHR(10)||
                                                  ' 給付案Z總筆數          = (' || v_countCase1Z ||')'||CHR(10)||
                                                  ' 不給付Z案總筆數        = (' || v_countCase3Z ||')'||CHR(10)||
                                                  ' 更新領取註記失敗筆數   = (' || v_countErr ||')'||CHR(10)||
                                                  ' 逕退總筆數             = (' || v_countToDRAW ||')'||CHR(10)||
                                                  ' 逕退成功筆數           = (' || v_countToDRAWSUC ||')'||CHR(10)||
                                                  ' 逕退失敗筆數           = (' || v_countToDRAWErr ||')'||CHR(10)||
                                                  ' 查無事故者 BA.CIPB 資料= (' || v_countBI ||')'||CHR(10)||
                                                  ' 未經身分證重號檔選擇   = (' || v_countDupIdn ||')'||CHR(10)
                        );
    End;
    --Procedure sp_BA_updCheckMonth

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_updCIStatus.sp_BA_updLastMonth
        PURPOSE:         依照輸入的參數年月，將老年年金受理案件申請日期為輸入參數年月前一個月，且
                         處理狀態為<=40，申請單位非空白，事故日期<輸入參數年月的第一天，無
                         處理函別為21、處理註記為"55"之行政支援紀錄，之受理案件產生逕退檔，另如為
                         不給付案件已複核決行，且無其他已申請或已核付之老年給付紀錄者（含給付種類
                         41、45、48、49），請同時註銷老註記與管制加保註記。

        PARAMETER(IN):   *v_i_paramym        (varChar2)       -- 參數年月
                         *v_i_user          (varChar2)       -- 使用者帳號

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/08/13  ChungYu Lin  Created this Package.
        1.1   2014/03/26  ChungYu Lin  修改老年年金月初逕退條件，改讀取處理狀態<= 40
        1.2   2015/04/01  ChungYu Lin  因老年年金清除臨時、永久註記及逕退，移到sp_BA_OldAgeDaily執行，
                                       因此刪除 sp_BA_updLastMonth。

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
/*    Procedure sp_BA_updLastMonth (
        v_i_paramym            in      varChar2,                      -- 參數年月
        v_i_user               in      varChar2
    ) is
      v_intyp                  VARCHAR2(1)             := '';         -- 被保險人勞保資料(L) 勞保自願職保資料(V)
      v_evtidn                 Baappbase.Evtidnno%TYPE := '';         -- 事故者身分證號
      v_payCode                VARCHAR2(1)             := '';         -- 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
      v_SBMK                   VARCHAR2(1)             := '';         -- 給付註記
      v_tempSBMK               VARCHAR2(1)             := '';         -- 給付註記
      v_UINMK                  VARCHAR2(1)             := '';         -- 管制加保註記
      v_tempUINMK              VARCHAR2(1)             := '';         -- 管制加保註記
      v_CIPB                   CIPB%ROWTYPE;
      v_result                 VARCHAR2(1)             := NULL;
      v_resultCode             VARCHAR2(300)           := NULL;
      v_systp                  VARCHAR2(20)            := 'BaSPLastM';
      v_upcode                 VARCHAR2(1)             := NULL;
      v_count                  NUMBER                  := 0;
      v_countDRAW              NUMBER                  := 0;          --受理編號存在逕退檔筆數
      v_countALL               NUMBER                  := 0;          --總筆數
      v_countPOC               NUMBER                  := 0;          --處理總筆數
      v_countCase1G            NUMBER                  := 0;          --月初逕退G總筆數
      v_countCase3G            NUMBER                  := 0;          --不給付G案總筆數
      v_countCase1A            NUMBER                  := 0;          --月初逕退A總筆數
      v_countCase3A            NUMBER                  := 0;          --不給付A案總筆數
      v_countCase1Z            NUMBER                  := 0;          --月初逕退A總筆數
      v_countCase3Z            NUMBER                  := 0;          --不給付A案總筆數
      v_countToDRAW            NUMBER                  := 0;          --逕退總筆數
      v_countToDRAWSUC         NUMBER                  := 0;          --逕退成功筆數
      v_countToDRAWErr         NUMBER                  := 0;          --逕退失敗筆數
      v_countErr               NUMBER                  := 0;          --更新領取註記失敗筆數
      v_countBI                NUMBER                  := 0;          --查無事故者 BI CIPB筆數
      v_DupCount               NUMBER                  := 0;          --事故者CIPB暫存筆數
      v_countDupIdn            NUMBER                  := 0;          --事故者CIPB筆數大於一筆未經身分證重號檔選擇
      v_updBACIBMK             CIPB.BMK%TYPE           := '';         --更新BA CIPB BMK
      v_APCount                NUMBER                  := 0;          --同給付別非同受理編號筆數
      v_ciCIPB                 CI.CIPB%ROWTYPE;                       --承保系統被保險人基本資料 2013/03/07 Add By ChungYu

      \*老年年金月初逕退條件
        就符合下列條件之老年年金給付案件產生逕退檔（排除已核付案件）。
        另如為不給付案件已複核決行，且無其他已申請或已核付之老年給付紀錄者（含給付種類41、45、48、49），
        請同時註銷老註記與管制加保註記。

        一.申請日期為輸入參數年月前一個月。
        二.處理狀態為00、01、10。               2014/03/26 Modify By ChungYu 改讀取處理狀態<= 40
        三.申請單位非空白。
        四.事故日期<輸入參數年月的第一天。
        五.無處理函別為21、處理註記為"55"之行政支援紀錄（如有多筆紀錄，以最末筆為準）。
      *\
      CURSOR c_dataCur_BAAPP IS
        Select A.APNO,                                             -- 受理編號
               A.SEQNO,                                            -- 序號  2014/04/08 Add By ChungYu 回押 Ba.CIPB 要加受理編號+序號
               A.APPDATE,                                          -- 受理日期
               A.PAYKIND,                                          -- 給付種類
               Decode(D.IDNNO,Null,A.EvtIdnno,D.IDNNO) As EVTIDNO, -- 事故者身分證號
               A.EVTBRDATE,                                        -- 事故者生日
               A.EVTNAME,                                          -- 事故者姓名
               A.EVTJOBDATE,                                       -- 事故日期
               A.APUBNO,                                           -- 申請單位保險證號  2013/06/14 老年逕退改用最後單位保險證號
               --A.LSUBNO,                                         -- 最後單位保險證號  2013/06/14 老年逕退改用最後單位保險證號
                                                                   -- 月初逕退改回用申請單位 2013/08/06
               A.CASETYP AS CASETYP,                               -- 案件類別
               '' AS APLPAYDATE,                                   -- 帳務日期
               E.NOTEMK                                            -- 行政支援註記
          From (Select A1.* From Baappbase A1
                 Where A1.APNO Like ('L%')
                   And A1.SEQNO = '0000'
                   And SUBSTR(A1.APPDATE,1,6) = SUBSTR(TO_CHAR(ADD_MONTHS(TO_DATE(v_i_paramym||'01','YYYYMMDD'),-1),'YYYYMMDD'),1,6)
                   And A1.EVTJOBDATE < v_i_paramym || '01'
                   --And ( A1.CASETYP = '1' And A1.PROCSTAT In ('00','01','10','11','12')  And A1.LSUBNO Is Not Null)  -- 月初逕退改回用申請單位 2013/08/06
                   --And ( A1.CASETYP = '1' And A1.PROCSTAT In ('00','01','10','11','12')  And A1.APUBNO Is Not Null)  -- 2014/03/26 月初逕退改讀取處理狀態<= 40
                   And ( A1.CASETYP = '1' And A1.PROCSTAT <= '40' And A1.APUBNO Is Not Null)
                      Or (A1.CASETYP = '3' And A1.PROCSTAT = '90' )) A,
               (Select D1.APNO, D1.IDNNO From BADUPEIDN D1 Where SELMK = '2' ) D,
               (Select E1.APNO, 'Y' As NOTEMK From MAADMREC E1
                 Where E1.LETTERTYPE = '21'
                   And (NVL(E1.DELMK, ' ') <> 'D')
                   And ((NVL(E1.NDOMK1, ' ') = '55') Or
                        (NVL(E1.NDOMK2, ' ') = '55')) )E
                 Where A.SEQNO = '0000'
                   And A.APNO = D.APNO(+)
                   And A.APNO = E.APNO(+)
                 Order by A.APNO, A.CASETYP;


    Begin
     If (Trim(v_i_paramym) Is Not Null) And (Trim(v_i_user) Is Not Null) Then
         v_intyp := 'L';

         For v_CurBAAPP In c_dataCur_BAAPP Loop

             v_countALL:= v_countALL + 1;
             v_evtidn  := v_CurBAAPP.Evtidno;
             v_payCode := Substr(v_CurBAAPP.Apno,1,1);

             SELECT COUNT(*) INTO v_count FROM BAWITHDRAW
              WHERE apno = v_CurBAAPP.Apno;

             -- 2012/12/11 Modify By ChungYu  因為不給付案已存在於逕退檔中，若加入以下條件，則不給付案即不會清除永久註記，
             --                               故將本條件移到，處理新案逕退前。
   --           If v_count > 0 Then
   --              v_countDRAW := v_countDRAW + 1;
   --           Else

                 If (v_evtidn Is Not Null) Then
                   Begin

                     Select Count(IDN) Into v_DupCount From CIPB
                      Where INTYP = 'L'
                        And IDN Like v_evtidn||'%'
                        And APNO  = v_CurBAAPP.APNO
                        And SEQNO = v_CurBAAPP.SEQNO;

                     If v_DupCount = 1 Then

                         Select * Into v_CIPB From CIPB
                          Where INTYP = 'L'
                            And IDN Like v_evtidn||'%'
                            And APNO  = v_CurBAAPP.APNO
                            And SEQNO = v_CurBAAPP.SEQNO;

                         -- 2013/03/07 比對被保險人註記狀態改讀取承保CIPB
                         If v_CIPB.Ciid Is Not Null Then
                            Select * Into v_ciCIPB From CI.CIPB
                             Where FTYP = v_intyp
                               And CIID = v_CIPB.Ciid;
                         End if;
                         -- 2013/03/07 比對被保險人註記狀態改讀取承保CIPB

                         -- 同給付別非同受理編號筆數
                         Select COUNT(Apno) Into v_APCount
                           From (Select F2.Apno From BAAPPBASE F2
                                  Where F2.APNO <> v_CurBAAPP.APNO
                                    And SUBSTR(F2.APNO,1,1) = 'L'
                                    And F2.Seqno = '0000'
                                    And F2.CASEMK Is Null
                                    And F2.EVTIDNNO = substr(v_CurBAAPP.EVTIDNO,1,10)
                                    And F2.EVTNAME = v_CurBAAPP.EVTNAME
                                    And F2.EVTBRDATE = v_CurBAAPP.EVTBRDATE
                                  Union
                                 Select F3.BMEVIDNO From PBBMSA F3
                                  Where F3.BMPAYKND = '4'
                                    And F3.BMEVIDNO = substr(v_CurBAAPP.EVTIDNO,1,10)
                                    And SUBSTR(F3.BMAPNO,5,2) = '41');

                         If (v_CIPB.Ciid Is Not Null) And (v_ciCIPB.Ciid Is Not Null) Then
                            v_countPOC := v_countPOC +1 ;
                            v_upcode := null;

                               --  不給付案，清除給付註記
                               \* 老年年金清除臨時及永久註記之條件：
                                  （1）「領取老年給付註記CIPB_API_SBMK2」：於每月勞保老年年金月核定作業後，
                                        讀取該月案件類別為不給付及暫緩給付案，且無同給付別、同IDN、出生及姓名之續發案或結案者，
                                        將本欄之1-已受理、3-已核付（老年年金）清為空白，  其他欄位值不予清除。
                               *\
                            if (v_CurBAAPP.CASETYP = '3') Then
                                v_upcode := 'G';
                                If (v_APCount = 0) And ((v_ciCIPB.SBmk2 = '1') Or
                                   (v_ciCIPB.SBmk2 = '3')) Then
                                    v_tempSBMK  := v_ciCIPB.SBmk2;
                                    v_SBMK := null;
                                    If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                        sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                       v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                       v_CIPB.Ciid,             --  勞就保識別碼
                                                       v_i_user,                --  UserID
                                                       v_tempSBMK,              --  Befort SIDMK Value
                                                       v_SBMK,                  --  SIDMK Value
                                                       v_CurBAAPP.Apno,         --  受理編號
                                                       v_i_paramym,             --  參數年月
                                                       v_CurBAAPP.casetyp,      --  案件類別
                                                       v_systp,
                                                       v_result,
                                                       v_resultCode
                                                     );
                                        If v_result <> '1' Then
                                           v_upcode := 'N';
                                           sp_saveMsg( '0',
                                                       v_CurBAAPP.Apno,
                                                       v_i_paramym,
                                                       v_CIPB.CIID,
                                                       v_intyp,
                                                       v_i_user,
                                                       v_resultCode
                                                     );
                                        Else
                                           v_updBACIBMK := Substr(v_CIPB.Bmk,1,1)||NVL(v_SBMK,' ')||Substr(v_CIPB.Bmk,3,8);
                                           Update CIPB
                                              Set BMK = v_updBACIBMK
                                            Where INTYP = v_CIPB.Intyp
                                              And IDN   = v_CIPB.Idn
                                              And APNO  = v_CurBAAPP.APNO
                                              And SEQNO = v_CurBAAPP.SEQNO;
                                        End if;
                                Else
                                   v_upcode := 'A';
                                End if;
                            Else
                                v_upcode := 'Z';
                            End if;
                            --  不給付案，清除管制加保註記
                            \*
                                老年年金不給付案件：於完成清除前述「領取給付註記」之永久註記同時，
                                如老年、失能、死亡及失蹤之領取給付註記為空值或＜2，且「管制加保註記」之欄位值為Y時，
                                清為空白；未清除永久註記者，本欄位不須清為空白。
                            *\
                            If (v_result = '1') And ((v_upcode <> 'Z') Or ((v_upcode <> 'N'))) Then
                                If (NVL(v_ciCIPB.SBmk1,'1') < '2' ) And
                                   (NVL(v_ciCIPB.SBmk2,'1') < '2' ) And
                                   (NVL(v_ciCIPB.SBmk3,'1') < '2' ) And
                                   (NVL(v_ciCIPB.SBmk4,'1') < '2' ) And
                                   (NVL(v_ciCIPB.UINMK,' ') = 'Y') Then
                                    v_tempUINMK := v_ciCIPB.UINMK;
                                    v_UINMK := null;
                                    If (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                                        sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                        v_CIPB.Ciid,             --  勞就保識別碼
                                                        v_i_user,                --  UserID
                                                        v_tempSBMK,              --  Befort SIDMK Value
                                                        v_UINMK,                 --  SIDMK Value
                                                        v_CurBAAPP.Apno,         --  受理編號
                                                        v_i_paramym,             --  核定年月
                                                        v_CurBAAPP.casetyp,      --  案件類別
                                                        v_systp,
                                                        v_result,
                                                        v_resultCode
                                                      );
                                        If v_result <> '1' Then
                                           sp_saveMsg( '0',
                                                       v_CurBAAPP.Apno,
                                                       v_i_paramym,
                                                       v_CIPB.CIID,
                                                       v_intyp,
                                                       v_i_user,
                                                       v_resultCode
                                                      );
                                        Else
                                          -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                          Update CIPB
                                             Set UINMK = v_UINMK
                                           Where INTYP = v_CIPB.Intyp
                                             And IDN   = v_CIPB.Idn
                                             And APNO  = v_CurBAAPP.APNO
                                             And SEQNO = v_CurBAAPP.SEQNO;
                                          -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                        End if;
                                    End if;
                                End if;
                            End if;

                               if v_upcode = 'G' Then
                                  v_countCase3G := v_countCase3G + 1;
                               elsif v_upcode = 'A' Then
                                  v_countCase3A := v_countCase3A + 1;
                               elsif v_upcode = 'Z' Then
                                  v_countCase3Z := v_countCase3Z + 1;
                               end if;

                            End if;

                         --  2012/12/11  Modify By ChungYu  因為不給付案已存在於逕退檔中，調整存在逕退檔判斷位置。
                         If v_count > 0 Then
                            v_countDRAW := v_countDRAW + 1;
                         Else
                            -- 寫入逕退檔
                            If (v_result = '1' and v_CurBAAPP.CASETYP = '3') or (v_CurBAAPP.CASETYP <> '3')  Then
                                Insert Into BAWITHDRAW (APNO, ISSUYM, CASETYP, INTYP, PAYKIND,
                                                        UBNO, EVTIDNNO, EVTBRDATE, EVTNAME, EVTJOBDATE,
                                                        APLPAYDATE, APPDATE, DEPT, VMK, UPCODE,
                                                        PBSTAT, PBSTAT2, PBUINMK, PBUINMK2, TXCD2,
                                                        SPCH, CIID, NDOMK, HCODE,  PROCTIME)
                                                VALUES (v_CurBAAPP.APNO, v_i_paramym, v_CurBAAPP.CASETYP, v_CIPB.Intyp, v_CurBAAPP.PAYKIND,
                                                        --v_CurBAAPP.LSUBNO, v_CurBAAPP.EVTIDNO, v_CurBAAPP.EVTBRDATE, v_CurBAAPP.EVTNAME, v_CurBAAPP.EVTJOBDATE,  -- 月初逕退改回用申請單位 2013/08/06
                                                        v_CurBAAPP.APUBNO, v_CurBAAPP.EVTIDNO, v_CurBAAPP.EVTBRDATE, v_CurBAAPP.EVTNAME, v_CurBAAPP.EVTJOBDATE,
                                                        v_CurBAAPP.APLPAYDATE, v_CurBAAPP.APPDATE, '', Decode(v_intyp,'V','V',Null),v_upcode,
                                                        v_tempSBMK, v_SBMK,v_tempUINMK, v_UINMK,'',
                                                        '', v_CIPB.CIID, v_CurBAAPP.NOTEMK, '', to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                        );
                               \* 老年年金：
                                  1、於每月勞保老年年金月核定作業後，讀取該月核付，且給付主檔之申請單位保險證號非空白之新案，
                                     以事故日期為退保日期產生逕退檔。
                                  2、於每月勞保老年年金月核定作業後，讀取該月不給付及暫緩給付案，當給付主檔之申請單位保險證
                                     號非空白，且行政支援記錄檔中函別21之處理註記(一)及處理註記（二）非55者，以事故日期為退
                                     保日期產生逕退檔。
                               *\
                                If ( v_payCode = 'L' ) Then
                                     --If ((v_CurBAAPP.CASETYP = '1') And (Trim(v_CurBAAPP.LSUBNO) Is Not Null)) Or
                                     If ((v_CurBAAPP.CASETYP = '1') And (Trim(v_CurBAAPP.APUBNO) Is Not Null)) Or
                                          ((v_CurBAAPP.CASETYP = '3') And (NVL(v_CurBAAPP.NOTEMK,' ') <> 'Y'))  Then
                                             v_countToDRAW := v_countToDRAW + 1;
                                             CI.CI040M020( v_intyp,                        --  保險別    L、V、F
                                                           v_CurBAAPP.PAYKIND,             --  核付種類  45 or 35 ...
                                                           v_CurBAAPP.APLPAYDATE,          --  核付日期
                                                           --v_CurBAAPP.LSUBNO,              --  保險證號
                                                           v_CurBAAPP.APUBNO,              --  保險證號
                                                           SUBSTR(v_CurBAAPP.EVTIDNO,1,10),--  身分證號   2012/10/04 ChungYu Modify
                                                           v_CurBAAPP.EVTBRDATE,           --  出生日期
                                                           v_CurBAAPP.EVTNAME,             --  姓名
                                                           v_CurBAAPP.APNO,                --  受理號碼
                                                           v_CurBAAPP.EVTJOBDATE,          --  事故日期
                                                           v_CurBAAPP.APPDATE,             --  受理日期
                                                           to_Char(Sysdate,'YYYYMMDD'),    --  處理日期
                                                           to_Char(Sysdate,'HH24MISS'),    --  處理時間
                                                           '',                             --  工作部門
                                                           '',                             --  輕重殘註記   only K = '1'
                                                           '',                             --  育嬰續保起日
                                                           '',                             --  育嬰續保迄日
                                                           '',                             --  育嬰續保申請日
                                                           v_result,
                                                           v_resultCode);
                                     End if;
                                End if;
                                -- 更新逕退檔
                                IF NVL(v_result,'1') = '1' Then
                                   v_countToDRAWSUC := v_countToDRAWSUC + 1;

                                     -- 2012/10/17 mark by ChungYu 月初逕退不需要上逕退檔註記，待月核付後上永久註記一起上
                                     \*Update BAWITHDRAW
                                        Set TXCD2 = 'Y'
                                      Where APNO = v_CurBAAPP.APNO;*\
                                Else
                                   v_countToDRAWErr := v_countToDRAWErr + 1;
                                End if;
                            Else
                               v_countErr := v_countErr + 1;
                            End if;
                          End If;
                         End If;
                     Else
                       v_countDupIdn := v_countDupIdn + 1;
                     End If;
                  Exception
                    when NO_DATA_FOUND then
                         sp_saveMsg( '0',
                                     v_CurBAAPP.Apno,
                                     '',
                                     '',
                                     v_intyp,
                                     v_i_user,
                                     '查無事故者 BA.CIPB 資料'
                                   );
                         v_countBI := v_countBI + 1;
                  End;

                 End If;
             -- dbms_output.put_line(' PKG_BA_updCIStatus-sp_BA_updLastMonth----------->>[ Insert Error Log ]');
        --  End If;
      End Loop;
         sp_saveMsg( '1','','','','',v_i_user,' 總筆數                 = ' || v_countALL);
         sp_saveMsg( '1','','','','',v_i_user,' 受理編號存在逕退檔筆數 = ' || v_countDRAW);
         sp_saveMsg( '1','','','','',v_i_user,' 處理總筆數             = ' || v_countPOC);
         sp_saveMsg( '1','','','','',v_i_user,' 給付案G總筆數          = ' || v_countCase1G);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付G案總筆數        = ' || v_countCase3G);
         sp_saveMsg( '1','','','','',v_i_user,' 給付案A總筆數          = ' || v_countCase1A);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付A案總筆數        = ' || v_countCase3A);
         sp_saveMsg( '1','','','','',v_i_user,' 給付案Z總筆數          = ' || v_countCase1Z);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付Z案總筆數        = ' || v_countCase3Z);
         sp_saveMsg( '1','','','','',v_i_user,' 更新領取註記失敗筆數   = ' || v_countErr);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退總筆數             = ' || v_countToDRAW);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退成功筆數           = ' || v_countToDRAWSUC);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退失敗筆數           = ' || v_countToDRAWErr);
         sp_saveMsg( '1','','','','',v_i_user,' 查無事故者 BA.CIPB 資料= ' || v_countBI);
         sp_saveMsg( '1','','','','',v_i_user,' 未經身分證重號檔選擇   = ' || v_countDupIdn);
         Commit;

         dbms_output.put_line(' 總筆數                 = ' || v_countALL);
         dbms_output.put_line(' 受理編號存在逕退檔筆數 = ' || v_countDRAW);
         dbms_output.put_line(' 處理總筆數             = ' || v_countPOC);
         dbms_output.put_line(' 給付案G總筆數          = ' || v_countCase1G);
         dbms_output.put_line(' 不給付G案總筆數        = ' || v_countCase3G);
         dbms_output.put_line(' 給付案A總筆數          = ' || v_countCase1A);
         dbms_output.put_line(' 不給付A案總筆數        = ' || v_countCase3A);
         dbms_output.put_line(' 給付案Z總筆數          = ' || v_countCase1Z);
         dbms_output.put_line(' 不給付Z案總筆數        = ' || v_countCase3Z);
         dbms_output.put_line(' 更新領取註記失敗筆數   = ' || v_countErr);
         dbms_output.put_line(' 逕退總筆數             = ' || v_countToDRAW);
         dbms_output.put_line(' 逕退成功筆數           = ' || v_countToDRAWSUC);
         dbms_output.put_line(' 逕退失敗筆數           = ' || v_countToDRAWErr);
         dbms_output.put_line(' 查無事故者 BA.CIPB 資料= ' || v_countBI);
         dbms_output.put_line(' 未經身分證重號檔選擇   = ' || v_countDupIdn);
     Else
         dbms_output.put_line(' PKG_BA_updCIStatus-sp_BA_updLastMonth----------->>參數年月或使用者帳號不可為空白 ]');
     End If;
    End;*/
    --Procedure sp_BA_updLastMonth

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_updCIStatus.sp_BA_OldAgeDaily
        PURPOSE:         1.老年年金每日於日批次編審後，將前一日決行案件類別為不給付及暫緩給付案案件，
                           清除臨時及永久註記。
                         2.修改被保險人逕退檔條件，將前一日決行案件類別為新案案件，以事故日期為退保日
                           期及最後單位保險證號為保險證號產生逕退檔。
                         3.修改被保險人逕退檔條件，將前一日決行案件類別為不給付及暫緩給付案案件，當給
                           付主檔之申請單位保險證號非空白，且行政支援記錄檔中函別21之處理註記(一)及處
                           理註記（二）非55者，以事故日期為退保日期產生逕退檔。
                         -- 以下2018/09/03 新增需求
                         4.以身分證號+比對勞保老年年金給付逕退檔：
                           無符合資料：以給付主檔最後單位保險證號為保險證號，以事故日期為退保日期產生逕退檔。
                           有符合資料：
                           (1).該逕退檔之保險證號及退保日與給付主檔之最後單位保險證號及事故日期均相符者，不須產生逕退檔。
                           (2).該逕退檔之保險證號或退保日期與給付主檔之最後單位保險證號或事故日期不符者，以最後單位保險證
                               號為保險證號，以事故日期為退保日期產生逕退檔。
                         -- 以上2018/09/03 新增需求
                         
        PARAMETER(IN):   *v_i_user          (varChar2)       -- 使用者帳號

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2015/03/30  ChungYu Lin  Created this Package.
        1.1   2015/10/12  ChungYu Lin  修改為小於前一日所有決行案件.
        1.2   2016/04/11  ChungYu Lin  因老年不給付案發生SBMK2 = 2，但UINMK=Y 卻被清空的情形，經推測清除UINMK的條件，
                                       有可能因前一筆的案件處理狀態未清除，導致進入清除程序中，因此新增每次處理案件均
                                       將v_result、v_upcode 清空.
        1.3   2018/09/25  ChungYu Lin  若逕退檔之保險證號或退保日期與給付主檔之最後單位保險證號或事故日期不符，以最後
                                       單位保險證號為保險證號，以事故日期為退保日期重新產生逕退檔。
        1.4   2018/10/19  ChungYu Lin  讀取同給付別非同受理編號筆數時，加入新案。
         
        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_OldAgeDaily (
        v_i_user               in      varChar2
    ) is

      v_intyp                  VARCHAR2(1)             := '';         -- 被保險人勞保資料(L) 勞保自願職保資料(V)
      v_evtidn                 Baappbase.Evtidnno%TYPE := '';         -- 事故者身分證號
      v_payCode                VARCHAR2(1)             := '';         -- 勞保年金給付別L：老年年金
      v_SBMK                   VARCHAR2(1)             := '';         -- 給付註記
      v_tempSBMK               VARCHAR2(1)             := '';         -- 給付註記
      v_UINMK                  VARCHAR2(1)             := '';         -- 管制加保註記
      v_tempUINMK              VARCHAR2(1)             := '';         -- 管制加保註記
      v_CIPB                   CIPB%ROWTYPE;
      v_result                 VARCHAR2(1)             := NULL;
      v_resultCode             VARCHAR2(300)           := NULL;
      v_systp                  VARCHAR2(20)            := 'BaOADay';
      v_upcode                 VARCHAR2(1)             := NULL;
      v_count                  NUMBER                  := 0;
      v_countDRAW              NUMBER                  := 0;          --受理編號存在逕退檔筆數
      v_countALL               NUMBER                  := 0;          --總筆數
      v_countPOC               NUMBER                  := 0;          --處理總筆數
      v_countSUC               NUMBER                  := 0;          --更新領取註記成功筆數
      v_countCase3G            NUMBER                  := 0;          --不給付G案總筆數
      v_countCase3A            NUMBER                  := 0;          --不給付A案總筆數
      v_countCase3Z            NUMBER                  := 0;          --不給付A案總筆數
      v_countToDRAW            NUMBER                  := 0;          --逕退總筆數
      v_countToDRAWSUC         NUMBER                  := 0;          --逕退成功筆數
      v_countToDRAWErr         NUMBER                  := 0;          --逕退失敗筆數
      v_countErr               NUMBER                  := 0;          --更新領取註記失敗筆數
      v_countBI                NUMBER                  := 0;          --查無事故者 BI CIPB筆數
      v_DupCount               NUMBER                  := 0;          --事故者CIPB暫存筆數
      v_countDupIdn            NUMBER                  := 0;          --事故者CIPB筆數大於一筆未經身分證重號檔選擇
      v_updBACIBMK             CIPB.BMK%TYPE           := '';         --更新BA CIPB BMK
      v_TXCD2                  BAWITHDRAW.TXCD2%TYPE   := '';         --逕退檔註記
      v_ciCIPB                 CI.CIPB%ROWTYPE;                       --承保系統被保險人基本資料
      v_ubno                   Baappbase.Lsubno%TYPE   := '';         --單位保險證號
      v_baWITHDRAW             BAWITHDRAW%ROWTYPE;                    --勞保年金逕退檔資料

      --讀取系統日期前一日決行的新案、不給付案、暫緩給付案清單
      CURSOR c_dataCur_BAAPP IS
        Select A.APNO,                                             -- 受理編號
               A.SEQNO,                                            -- 序號  回押 Ba.CIPB 要增加受理編號+序號
               A.APPDATE,                                          -- 受理日期
               A.PAYKIND,                                          -- 給付種類
               Decode(D.IDNNO,Null,A.EvtIdnno,D.IDNNO) As EVTIDNO, -- 事故者身分證號
               A.EVTBRDATE,                                        -- 事故者生日
               A.EVTNAME,                                          -- 事故者姓名
               A.EVTJOBDATE,                                       -- 事故日期
               A.APUBNO,                                           -- 申請單位保險證號  2013/06/14 老年逕退改用最後單位保險證號
               A.LSUBNO,                                           -- 最後單位保險證號  2013/06/14 老年逕退改用最後單位保險證號
               A.CASETYP,                                          -- 案件類別
               E.NOTEMK,                                           -- 行政支援註記
               A.ISSUYM,                                           -- 核定年月
               A.EXEDATE,                                          -- 決行日期
               (Select COUNT(F1.Apno)
                  From BAAPPBASE F1
                 Where F1.APNO <> A.apno
                   And SUBSTR(F1.APNO,1,1) = SUBSTR(A.APNO,1,1)
                   And F1.Seqno = '0000'
                   And F1.CASETYP In ('1', '2', '4')              -- 2018/10/19 Modify By ChugnYu
                   And F1.EVTIDNNO = A.EVTIDNNO
                   And F1.EVTNAME = A.EVTNAME
                   And F1.EVTBRDATE = A.EVTBRDATE
                ) AS APCOUNT                                       -- 同給付別非同受理編號筆數
          From (Select A1.* From Baappbase A1
                 Where SUBSTR(A1.APNO,1,1) = 'L'
                   And A1.SEQNO = '0000'
                   And A1.CASETYP In ('1', '3', '6')
                   And A1.PROCSTAT = '40'
                   And A1.EXEDATE <= to_Char(SYSDATE-1,'YYYYMMDD')
                   And A1.MANCHKMK Is Not Null
                   And A1.SEQNO = '0000') A,
               (Select D1.APNO, D1.IDNNO From BADUPEIDN D1 Where SELMK = '2' ) D,
               (Select E1.APNO, 'Y' As NOTEMK From MAADMREC E1
                 Where E1.LETTERTYPE = '21'
                   And (NVL(E1.DELMK, ' ') <> 'D')
                   And ((NVL(E1.NDOMK1, ' ') = '55') Or
                        (NVL(E1.NDOMK2, ' ') = '55')) )E
                 Where A.SEQNO = '0000'
                   And A.APNO = D.APNO(+)
                   And A.APNO = E.APNO(+)
                 Order by A.APNO, A.CASETYP;

    Begin
     If (Trim(v_i_user) Is Not Null) Then
         v_intyp := 'L';

         For v_CurBAAPP In c_dataCur_BAAPP Loop

             v_countALL:= v_countALL + 1;
             v_evtidn  := v_CurBAAPP.Evtidno;
             v_payCode := Substr(v_CurBAAPP.Apno,1,1);
             v_result      := Null;                     -- 2016/04/11 Add By ChungYu
             v_upcode      := Null;                     -- 2016/04/11 Add By ChungYu
             v_resultCode  := Null;                     -- 2016/04/11 Add By ChungYu

             If (v_evtidn Is Not Null) Then
               Begin

                 Select Count(IDN) Into v_DupCount From CIPB
                  Where INTYP = v_intyp
                    And IDN Like v_evtidn||'%'
                    And APNO = v_CurBAAPP.APNO
                    And SEQNO = v_CurBAAPP.SEQNO;

                 If v_DupCount = 1 Then

                    Select * Into v_CIPB From CIPB
                     Where INTYP = v_intyp
                       And IDN Like v_evtidn||'%'
                       And APNO = v_CurBAAPP.APNO
                       And SEQNO = v_CurBAAPP.SEQNO;

                    -- 2013/03/07 比對被保險人註記狀態改讀取承保CIPB
                    If v_CIPB.Ciid Is Not Null Then
                       Select * Into v_ciCIPB From CI.CIPB
                        Where FTYP = v_intyp
                          And CIID = v_CIPB.Ciid;
                    End if;
                    -- 2013/03/07 比對被保險人註記狀態改讀取承保CIPB

                    If (v_CIPB.Ciid Is Not Null) And (v_ciCIPB.Ciid Is Not Null) Then
                        v_countPOC := v_countPOC +1 ;
                        v_upcode := null;

                        --  不給付案及暫緩給付案，清除給付註記

                        if (v_CurBAAPP.CASETYP = '3' Or v_CurBAAPP.CASETYP = '6') Then
                            v_upcode := 'G';

                        /* 老年年金清除臨時及永久註記之條件：
                          （1）「領取老年給付註記CIPB_API_SBMK2」：於每日勞保老年年金日批次編審作業後，
                                 讀取前一日決行且案件類別為不給付及暫緩給付案，且無同給付別、同IDN、出生及姓名之續發案或結案者，
                                 將本欄之1-已受理、3-已核付（老年年金）清為空白，  其他欄位值不予清除。
                        */
                           if (v_payCode = 'L') Then
                               If (v_CurBAAPP.APCOUNT = 0) And ((v_ciCIPB.SBmk2 = '1') Or
                                  (v_ciCIPB.SBmk2 = '3')) Then
                                   v_tempSBMK  := v_ciCIPB.SBmk2;
                                   v_SBMK := Null;
                                   If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                       sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                      v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                      v_CIPB.Ciid,             --  勞就保識別碼
                                                      v_i_user,                --  UserID
                                                      v_tempSBMK,              --  Befort SIDMK Value
                                                      v_SBMK,                  --  SIDMK Value
                                                      v_CurBAAPP.Apno,         --  受理編號
                                                      v_CurBAAPP.Issuym,       --  核定年月
                                                      v_CurBAAPP.casetyp,      --  案件類別
                                                      v_systp,
                                                      v_result,
                                                      v_resultCode
                                                    );
                                       If v_result <> '1' Then
                                          v_upcode := 'N';
                                          sp_saveMsg( '0',
                                                      v_CurBAAPP.Apno,
                                                      v_CurBAAPP.Issuym,
                                                      v_CIPB.CIID,
                                                      v_intyp,
                                                      v_i_user,
                                                      v_resultCode
                                                     );
                                       Else
                                          v_updBACIBMK := Substr(v_CIPB.Bmk,1,1)||NVL(v_SBMK,' ')||Substr(v_CIPB.Bmk,3,8);
                                          Update CIPB
                                             Set BMK = v_updBACIBMK
                                           Where INTYP = v_CIPB.Intyp
                                             And IDN   = v_CIPB.Idn
                                             And APNO  = v_CurBAAPP.APNO
                                             And SEQNO = v_CurBAAPP.SEQNO;
                                       End if;
                                    Else
                                       v_upcode := 'A';
                                    End if;
                               Else
                                    v_upcode := 'Z';
                               End if;
                               --  清除管制加保註記
                               /*
                                  老年年金不給付及暫緩給付案件：於完成清除前述「領取給付註記」之永久註記同時，
                                  如老年之領取給付註記不為空值，失能、死亡及失蹤之領取給付註記為空值或＜2，且「管制加保註記」之欄位值為Y時，
                                  清為空白；未清除永久註記者，本欄位不須清為空白。
                               */
                               If (v_result = '1') And ((v_upcode <> 'Z') Or ((v_upcode <> 'N'))) Then
                                   If (NVL(trim(v_ciCIPB.SBmk1),'1') < '2' ) And
                                      (trim(v_ciCIPB.SBmk2) is Not Null)     And
                                      (NVL(trim(v_ciCIPB.SBmk3),'1') < '2' ) And
                                      (NVL(trim(v_ciCIPB.SBmk4),'1') < '2' ) And
                                      (NVL(v_ciCIPB.UINMK,' ') = 'Y') Then
                                          v_tempUINMK := v_ciCIPB.UINMK;
                                          v_UINMK := Null;
                                          If (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                                              sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                              v_CIPB.Ciid,             --  勞就保識別碼
                                                              v_i_user,                --  UserID
                                                              v_tempUINMK,             --  Befort UINMK Value   2016/04/11 Modify By ChungYu
                                                              v_UINMK,                 --  UINMK Value
                                                              v_CurBAAPP.Apno,         --  受理編號
                                                              v_CurBAAPP.Issuym,       --  核定年月
                                                              v_CurBAAPP.casetyp,      --  案件類別
                                                              v_systp,
                                                              v_result,
                                                              v_resultCode
                                                             );
                                              If v_result <> '1' Then
                                                 sp_saveMsg( '0',
                                                             v_CurBAAPP.Apno,
                                                             v_CurBAAPP.Issuym,
                                                             v_CIPB.CIID,
                                                             v_intyp,
                                                             v_i_user,
                                                             v_resultCode
                                                           );
                                              Else
                                              -- 同步BA CIPB UINMK 欄位
                                                 Update CIPB
                                                    Set UINMK = v_UINMK
                                                  Where INTYP = v_CIPB.Intyp
                                                    And IDN   = v_CIPB.Idn
                                                    And APNO  = v_CurBAAPP.APNO
                                                    And SEQNO = v_CurBAAPP.SEQNO;
                                                 -- 2同步BA CIPB UINMK 欄位
                                              End if;
                                          End if;
                                   End if;
                               End if;
                           End if;

                               if v_upcode = 'G' Then
                                  v_countCase3G := v_countCase3G + 1;
                               elsif v_upcode = 'A' Then
                                  v_countCase3A := v_countCase3A + 1;
                               elsif v_upcode = 'Z' Then
                                  v_countCase3Z := v_countCase3Z + 1;
                               end if;

                               --  Modify By ChungYu 調整不給付案及暫緩給付案，清除給付註記記錄位置
                               If (v_result = '1') Then
                                   v_countSUC := v_countSUC + 1;
                               Else
                                   v_countErr := v_countErr + 1;
                               End if;

                        End if;

                        -- 2017/11/16 Modify By ChungYu 調整判斷逕退，因為清除臨時註記與管制加保註記不需要先行判斷是否有上逕退
                        -- 受理案件是否已寫入逕退檔
                        SELECT COUNT(*) INTO v_count FROM BAWITHDRAW
                         WHERE apno = v_CurBAAPP.Apno;

                        -- 2012/10/17 讀取逕退檔註記
                        -- 2018/09/25 當逕退檔之保險證號或退保日期改變須重新逕退
                        v_baWITHDRAW:= Null; 
                        If v_count > 0 Then
                           -- 2018/09/25 當逕退檔之保險證號或退保日期改變須重新逕退
                           SELECT * INTO v_baWITHDRAW FROM BAWITHDRAW T
                            WHERE T.APNO = v_CurBAAPP.Apno
                              AND Rownum = 1;
                              
                           v_TXCD2 := Trim(v_baWITHDRAW.TXCD2);
                            -- 2018/09/25 當逕退檔之保險證號或退保日期改變須重新逕退  
                           /* 2018/09/25 Mark by ChungYu 
                           
                           SELECT Trim(T.TXCD2) INTO v_TXCD2 FROM BAWITHDRAW T
                            WHERE T.APNO = v_CurBAAPP.Apno
                              AND Rownum = 1;*/
                        End If;

                        If (v_count > 0) and (v_TXCD2 = 'Y') Then   -- 2012/10/17 Modify By ChungYu
                            v_countDRAW := v_countDRAW + 1;
                        Else
                        -- 2017/11/16 Modify By ChungYu 調整判斷逕退，因為清除臨時註記與管制加保註記不需要先行判斷是否有上逕退

                           -- 寫入逕退檔
                           If (v_count = 0) Then   -- 2012/10/17 Add By ChungYu

                               -- 2013/07/11 Modify By ChungYu 老年新案逕退改用最後單位
                              -- 2018/09/25 Mark by Chngyu 老年新案逕退改用最後單位
                              -- If ((v_payCode = 'L') And (v_CurBAAPP.CASETYP = '1')) Then
                                    v_ubno := v_CurBAAPP.LSUBNO;
                               /*Else
                                    v_ubno := v_CurBAAPP.APUBNO;
                               End If;*/
                               -- 2013/07/11 老年新案逕退改用最後單位
                               Insert Into BAWITHDRAW (APNO, ISSUYM, CASETYP, INTYP, PAYKIND,
                                                       UBNO, EVTIDNNO, EVTBRDATE, EVTNAME, EVTJOBDATE,
                                                       APLPAYDATE, APPDATE, DEPT, VMK, UPCODE,
                                                       PBSTAT, PBSTAT2, PBUINMK, PBUINMK2, TXCD2,
                                                       SPCH, CIID, NDOMK, HCODE,  PROCTIME)
                                               VALUES (v_CurBAAPP.APNO, v_CurBAAPP.Issuym, v_CurBAAPP.CASETYP, v_CIPB.Intyp, v_CurBAAPP.PAYKIND,
                                                       v_ubno, v_CurBAAPP.EVTIDNO, v_CurBAAPP.EVTBRDATE, v_CurBAAPP.EVTNAME, v_CurBAAPP.EVTJOBDATE,
                                                       v_CurBAAPP.EXEDATE, v_CurBAAPP.APPDATE, '', Decode(v_intyp,'V','V',Null),v_upcode,
                                                       v_tempSBMK, v_SBMK,v_tempUINMK, v_UINMK,'',
                                                       '', v_CIPB.CIID, v_CurBAAPP.NOTEMK, '', to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                       );
                           Else
                               Update BAWITHDRAW Set APLPAYDATE = v_CurBAAPP.EXEDATE,
                                                     UPCODE     = v_upcode,
                                                     PBSTAT     = v_tempSBMK,
                                                     PBSTAT2    = v_SBMK,
                                                     PBUINMK    = v_tempUINMK,
                                                     PBUINMK2   = v_UINMK,
                                                     NDOMK      = v_CurBAAPP.NOTEMK,
                                                     UBNO       = v_ubno,                  --  2018/09/25 Add By ChungYu 
                                                     EVTJOBDATE = v_CurBAAPP.EVTJOBDATE,   --  2018/09/25 Add By ChungYu 
                                                     PROCTIME   = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                               Where APNO = v_CurBAAPP.APNO;
                           End If;
                           /* 老年年金：
                              1、每日讀取案件類別為新案，處理狀態為40-決行，決行日期為前一日（如遇例假日應含前一工作日及
                                 假日）者，以事故日期為退保日期，以最後單位保險證號為保險證號產生逕退檔。
                              2、每日讀取案件類別為不給付及暫緩給付案，處理狀態為40-決行，決行日期為前一日（如遇例假日應
                                 含前一工作日及假日）之案件，當給付主檔之申請單位保險證號非空白，且行政支援記錄檔中函別21
                                 之處理註記(一)及處理註記（二）非55者，以事故日期為退保日期產生逕退檔。
                              3.若逕退檔之保險證號或退保日期與給付主檔之最後單位保險證號或事故日期不符，以最後
                                單位保險證號為保險證號，以事故日期為退保日期重新產生逕退檔。  2018/09/25 Add
                           */
                           If ( v_payCode = 'L' ) Then
                                If (((v_CurBAAPP.CASETYP = '1') And (Trim(v_CurBAAPP.LSUBNO) Is Not Null)) or 
                                    ((v_CurBAAPP.LSUBNO <> v_baWITHDRAW.Ubno) or (v_CurBAAPP.EVTJOBDATE <> v_baWITHDRAW.EVTJOBDATE))) Or
                                    (((v_CurBAAPP.CASETYP = '3') Or (v_CurBAAPP.CASETYP = '6')) And (NVL(v_CurBAAPP.NOTEMK,' ') <> 'Y'))  Then
                                       v_countToDRAW := v_countToDRAW + 1;

                                       --  新案案件用最後單位來逕退,不給付案則為申請單位
                                       v_ubno := '';
                                       If (v_CurBAAPP.CASETYP = '1') Then
                                           v_ubno := v_CurBAAPP.LSUBNO;
                                       Else
                                           v_ubno := v_CurBAAPP.APUBNO;
                                       End If;

                                       CI.CI040M020( v_intyp,                        --  保險別    L、V、F
                                                     v_CurBAAPP.PAYKIND,             --  核付種類  45 or 35 ...
                                                     v_CurBAAPP.EXEDATE,             --  決行日期
                                                     v_ubno,                         --  保險證號
                                                     SUBSTR(v_CurBAAPP.EVTIDNO,1,10),--  身分證號   2012/10/04 ChungYu Modify
                                                     v_CurBAAPP.EVTBRDATE,           --  出生日期
                                                     v_CurBAAPP.EVTNAME,             --  姓名
                                                     v_CurBAAPP.APNO,                --  受理號碼
                                                     v_CurBAAPP.EVTJOBDATE,          --  事故日期
                                                     v_CurBAAPP.APPDATE,             --  受理日期
                                                     to_Char(Sysdate,'YYYYMMDD'),    --  處理日期
                                                     to_Char(Sysdate,'HH24MISS'),    --  處理時間
                                                     '',                             --  工作部門
                                                     '',                             --  輕重殘註記   only K = '1'
                                                     '',                             --  育嬰續保起日
                                                     '',                             --  育嬰續保迄日
                                                     '',                             --  育嬰續保申請日
                                                     v_result,
                                                     v_resultCode);
                                End if;
                           End if;
                           -- 更新逕退檔
                           IF NVL(v_result,'1') = '1' Then
                              v_countToDRAWSUC := v_countToDRAWSUC + 1;
                              Update BAWITHDRAW
                                 Set TXCD2 = 'Y'
                               Where APNO = v_CurBAAPP.APNO;
                           Else
                               v_countToDRAWErr := v_countToDRAWErr + 1;
                           End if;
                        End if;
                    End If;
                 Else
                    v_countDupIdn := v_countDupIdn + 1;
                 End If;
               Exception
                when NO_DATA_FOUND then
                     sp_saveMsg( '0',
                                  v_CurBAAPP.Apno,
                                 '',
                                 '',
                                 v_intyp,
                                 v_i_user,
                                 '查無事故者 BA.CIPB 資料'
                               );
                     v_countBI := v_countBI + 1;
               End;
             End If;
             -- dbms_output.put_line(' PKG_BA_updCIStatus-sp_BA_updCheckMonth----------->>[ Insert Error Log ]');
      End Loop;
         sp_saveMsg( '1','','','','',v_i_user,' 總筆數                 = ' || v_countALL);
         sp_saveMsg( '1','','','','',v_i_user,' 受理編號存在逕退檔筆數 = ' || v_countDRAW);
         sp_saveMsg( '1','','','','',v_i_user,' 處理總筆數             = ' || v_countPOC);
         sp_saveMsg( '1','','','','',v_i_user,' 更新領取註記成功筆數   = ' || v_countSUC);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付G案總筆數        = ' || v_countCase3G);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付A案總筆數        = ' || v_countCase3A);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付Z案總筆數        = ' || v_countCase3Z);
         sp_saveMsg( '1','','','','',v_i_user,' 更新領取註記失敗筆數   = ' || v_countErr);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退總筆數             = ' || v_countToDRAW);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退成功筆數           = ' || v_countToDRAWSUC);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退失敗筆數           = ' || v_countToDRAWErr);
         sp_saveMsg( '1','','','','',v_i_user,' 查無事故者 BA.CIPB 資料= ' || v_countBI);
         sp_saveMsg( '1','','','','',v_i_user,' 未經身分證重號檔選擇   = ' || v_countDupIdn);
         Commit;

         dbms_output.put_line(' 總筆數                 = ' || v_countALL);
         dbms_output.put_line(' 受理編號存在逕退檔筆數 = ' || v_countDRAW);
         dbms_output.put_line(' 處理總筆數             = ' || v_countPOC);
         dbms_output.put_line(' 更新領取註記成功筆數   = ' || v_countSUC);
         dbms_output.put_line(' 不給付G案總筆數        = ' || v_countCase3G);
         dbms_output.put_line(' 不給付A案總筆數        = ' || v_countCase3A);
         dbms_output.put_line(' 不給付Z案總筆數        = ' || v_countCase3Z);
         dbms_output.put_line(' 更新領取註記失敗筆數   = ' || v_countErr);
         dbms_output.put_line(' 逕退總筆數             = ' || v_countToDRAW);
         dbms_output.put_line(' 逕退成功筆數           = ' || v_countToDRAWSUC);
         dbms_output.put_line(' 逕退失敗筆數           = ' || v_countToDRAWErr);
         dbms_output.put_line(' 查無事故者 BA.CIPB 資料= ' || v_countBI);
         dbms_output.put_line(' 未經身分證重號檔選擇   = ' || v_countDupIdn);
     Else
         dbms_output.put_line(' PKG_BA_updCIStatus-sp_BA_OldAgeDaily----------->>[ 使用者帳號不可為空白 ]');
     End If;
    End;
    --Procedure sp_BA_OldAgeDaily


    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_updCIStatus.sp_BA_updCase3SBMK
        PURPOSE:         將全部的不給付案件，且無其他已申請或已核付之老年給付紀錄者（含給付種類
                         41、45、48、49），請同時註銷老註記與管制加保註記。

        PARAMETER(IN):   *v_i_user          (varChar2)       -- 使用者帳號

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/12/17  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_updCase3SBMK (
        v_i_user               in      varChar2
    ) is
      v_intyp                  VARCHAR2(1)             := '';         -- 被保險人勞保資料(L) 勞保自願職保資料(V)
      v_evtidn                 Baappbase.Evtidnno%TYPE := '';         -- 事故者身分證號
      v_payCode                VARCHAR2(1)             := '';         -- 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
      v_SBMK                   VARCHAR2(1)             := '';         -- 給付註記
      v_tempSBMK               VARCHAR2(1)             := '';         -- 給付註記
      v_UINMK                  VARCHAR2(1)             := '';         -- 管制加保註記
      v_tempUINMK              VARCHAR2(1)             := '';         -- 管制加保註記
      v_CIPB                   CIPB%ROWTYPE;
      v_result                 VARCHAR2(1)             := NULL;
      v_resultCode             VARCHAR2(300)           := NULL;
      v_systp                  VARCHAR2(20)            := 'BaSPCase3';
      v_upcode                 VARCHAR2(1)             := NULL;
      --v_count                  NUMBER                  := 0;
      v_countDRAW              NUMBER                  := 0;          --受理編號存在逕退檔筆數
      v_countALL               NUMBER                  := 0;          --總筆數
      v_countPOC               NUMBER                  := 0;          --處理總筆數
      v_countCase3G            NUMBER                  := 0;          --不給付G案總筆數
      v_countCase3A            NUMBER                  := 0;          --不給付A案總筆數
      v_countCase3Z            NUMBER                  := 0;          --不給付A案總筆數
      v_countErr               NUMBER                  := 0;          --更新領取註記失敗筆數
      v_countBI                NUMBER                  := 0;          --查無事故者 BI CIPB筆數
      v_DupCount               NUMBER                  := 0;          --事故者CIPB暫存筆數
      v_countDupIdn            NUMBER                  := 0;          --事故者CIPB筆數大於一筆未經身分證重號檔選擇
      v_updBACIBMK             CIPB.BMK%TYPE           := '';         --更新BA CIPB BMK
      v_APCount                NUMBER                  := 0;          --同給付別非同受理編號筆數
      v_ciCIPB                 CI.CIPB%ROWTYPE;                       --承保系統被保險人基本資料 2013/03/07 Add By ChungYu

      /*不給付案件，且無其他已申請或已核付之老年給付紀錄者（含給付種類41、45、48、49），
        同時註銷老註記與管制加保註記。
      */
      CURSOR c_dataCur_BAAPP IS
        Select A.APNO,                                             -- 受理編號
               A.SEQNO,                                            -- 序號2014/04/08 Add By ChungYu 回押Ba.Cipb 要加受理編號+序號
               A.APPDATE,                                          -- 受理日期
               A.PAYKIND,                                          -- 給付種類
               Decode(D.IDNNO,Null,A.EvtIdnno,D.IDNNO) As EVTIDNO, -- 事故者身分證號
               A.EVTBRDATE,                                        -- 事故者生日
               A.EVTNAME,                                          -- 事故者姓名
               A.EVTJOBDATE,                                       -- 事故日期
               A.APUBNO,                                           -- 申請單位保險證號  2013/06/14 老年逕退改用最後單位保險證號
               A.LSUBNO,                                           -- 最後單位保險證號  2013/06/14 老年逕退改用最後單位保險證號
               A.CASETYP AS CASETYP,                               -- 案件類別
               '' AS APLPAYDATE                                    -- 帳務日期
          From (Select A1.* From Baappbase A1
                 Where A1.APNO Like ('L%')
                   And A1.SEQNO = '0000'
                   And (A1.CASETYP = '3' And A1.PROCSTAT = '90' )
                ) A,
               (Select D1.APNO, D1.IDNNO From BADUPEIDN D1 Where SELMK = '2' ) D
                 Where A.SEQNO = '0000'
                   And A.APNO = D.APNO(+)
                   --And A.APNO IN( 'L20000238550','L20000313443','L20000301324','L20000299286')                    --單筆處理不給付案在此輸入APNO
                 Order by A.APNO, A.CASETYP;


    Begin
     If (Trim(v_i_user) Is Not Null) Then
         v_intyp := 'L';

         For v_CurBAAPP In c_dataCur_BAAPP Loop

             v_countALL:= v_countALL + 1;
             v_evtidn  := v_CurBAAPP.Evtidno;
             v_payCode := Substr(v_CurBAAPP.Apno,1,1);

             /*SELECT COUNT(*) INTO v_count FROM BAWITHDRAW
              WHERE apno = v_CurBAAPP.Apno;*/

                 If (v_evtidn Is Not Null) Then
                   Begin

                     Select Count(IDN) Into v_DupCount From CIPB
                      Where INTYP = 'L'
                        And IDN Like v_evtidn||'%';

                     If v_DupCount = 1 Then

                         Select * Into v_CIPB From CIPB
                          Where INTYP = 'L'
                            And IDN Like v_evtidn||'%';

                         -- 2013/03/07 比對被保險人註記狀態改讀取承保CIPB
                         If v_CIPB.Ciid Is Not Null Then
                            Select * Into v_ciCIPB From CI.CIPB
                             Where FTYP = v_intyp
                               And CIID = v_CIPB.Ciid;
                         End if;
                         -- 2013/03/07 比對被保險人註記狀態改讀取承保CIPB

                         -- 同給付別非同受理編號筆數
                         Select COUNT(Apno) Into v_APCount
                           From (Select F2.Apno From BAAPPBASE F2
                                  Where F2.APNO <> v_CurBAAPP.APNO
                                    And SUBSTR(F2.APNO,1,1) = 'L'
                                    And F2.Seqno = '0000'
                                    And F2.CASEMK Is Null
                                    And F2.EVTIDNNO = v_CurBAAPP.EVTIDNO
                                    And F2.EVTNAME = v_CurBAAPP.EVTNAME
                                    And F2.EVTBRDATE = v_CurBAAPP.EVTBRDATE
                                  Union
                                 Select F3.BMEVIDNO From PBBMSA F3
                                  Where F3.BMPAYKND = '4'
                                    And F3.BMEVIDNO = v_CurBAAPP.EVTIDNO
                                    And SUBSTR(F3.BMAPNO,5,2) = '41');

                         If (v_CIPB.Ciid Is Not Null) And (v_ciCIPB.Ciid Is Not Null) Then
                            v_countPOC := v_countPOC +1 ;
                            v_upcode := null;

                               --  不給付案，清除給付註記
                               /* 老年年金清除臨時及永久註記之條件：
                                  （1）「領取老年給付註記CIPB_API_SBMK2」：於每月勞保老年年金月核定作業後，
                                        讀取該月案件類別為不給付及暫緩給付案，且無同給付別、同IDN、出生及姓名之續發案或結案者，
                                        將本欄之1-已受理、3-已核付（老年年金）清為空白，  其他欄位值不予清除。
                               */
                            if (v_CurBAAPP.CASETYP = '3') Then
                                v_upcode := 'G';
                                If (v_APCount = 0) And ((v_ciCIPB.SBmk2 = '1') Or
                                   (v_ciCIPB.SBmk2 = '3')) Then
                                    v_tempSBMK  := v_ciCIPB.SBmk2;
                                    v_SBMK := null;
                                    If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                        sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                       v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                       v_CIPB.Ciid,             --  勞就保識別碼
                                                       v_i_user,                --  UserID
                                                       v_tempSBMK,              --  Befort SIDMK Value
                                                       v_SBMK,                  --  SIDMK Value
                                                       v_CurBAAPP.Apno,         --  受理編號
                                                       '',             --  參數年月
                                                       v_CurBAAPP.casetyp,      --  案件類別
                                                       v_systp,
                                                       v_result,
                                                       v_resultCode
                                                     );
                                        If v_result <> '1' Then
                                           v_upcode := 'N';
                                           sp_saveMsg( '0',
                                                       v_CurBAAPP.Apno,
                                                       '',
                                                       v_CIPB.CIID,
                                                       v_intyp,
                                                       v_i_user,
                                                       v_resultCode
                                                     );
                                        Else
                                           v_updBACIBMK := Substr(v_CIPB.Bmk,1,1)||NVL(v_SBMK,' ')||Substr(v_CIPB.Bmk,3,8);
                                           Update CIPB
                                              Set BMK = v_updBACIBMK
                                            Where INTYP = v_CIPB.Intyp
                                              And IDN   = v_CIPB.Idn
                                              And APNO  = v_CurBAAPP.APNO
                                              And SEQNO = v_CurBAAPP.SEQNO;
                                        End if;
                                Else
                                   v_upcode := 'A';
                                End if;
                            Else
                                v_upcode := 'Z';
                            End if;
                            --  不給付案，清除管制加保註記
                            /*
                                老年年金不給付案件：於完成清除前述「領取給付註記」之永久註記同時，
                                如老年、失能、死亡及失蹤之領取給付註記為空值或＜2，且「管制加保註記」之欄位值為Y時，
                                清為空白；未清除永久註記者，本欄位不須清為空白。
                            */
                            If (v_result = '1') And ((v_upcode <> 'Z') Or ((v_upcode <> 'N'))) Then
                                If (NVL(v_ciCIPB.SBmk1,'1') < '2' ) And
                                   (NVL(v_ciCIPB.SBmk2,'1') < '2' ) And
                                   (NVL(v_ciCIPB.SBmk3,'1') < '2' ) And
                                   (NVL(v_ciCIPB.SBmk4,'1') < '2' ) And
                                   (NVL(v_ciCIPB.UINMK,' ') = 'Y') Then
                                    v_tempUINMK := v_ciCIPB.UINMK;
                                    v_UINMK := null;
                                    If (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                                        sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                        v_CIPB.Ciid,             --  勞就保識別碼
                                                        v_i_user,                --  UserID
                                                        v_tempSBMK,              --  Befort SIDMK Value
                                                        v_UINMK,                 --  SIDMK Value
                                                        v_CurBAAPP.Apno,         --  受理編號
                                                        '',             --  核定年月
                                                        v_CurBAAPP.casetyp,      --  案件類別
                                                        v_systp,
                                                        v_result,
                                                        v_resultCode
                                                      );
                                        If v_result <> '1' Then
                                           sp_saveMsg( '0',
                                                       v_CurBAAPP.Apno,
                                                       '',
                                                       v_CIPB.CIID,
                                                       v_intyp,
                                                       v_i_user,
                                                       v_resultCode
                                                      );
                                        Else
                                          -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                          Update CIPB
                                             Set UINMK = v_UINMK
                                           Where INTYP = v_CIPB.Intyp
                                             And IDN   =  v_CIPB.Idn
                                             And APNO  = v_CurBAAPP.APNO
                                             And SEQNO = v_CurBAAPP.SEQNO;
                                          -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                        End if;
                                    End if;
                                End if;
                            End if;

                               if v_upcode = 'G' Then
                                  v_countCase3G := v_countCase3G + 1;
                               elsif v_upcode = 'A' Then
                                  v_countCase3A := v_countCase3A + 1;
                               elsif v_upcode = 'Z' Then
                                  v_countCase3Z := v_countCase3Z + 1;
                               end if;

                            End if;


                         End If;
                     Else
                       v_countDupIdn := v_countDupIdn + 1;
                     End If;
                  Exception
                    when NO_DATA_FOUND then
                         sp_saveMsg( '0',
                                     v_CurBAAPP.Apno,
                                     '',
                                     '',
                                     v_intyp,
                                     v_i_user,
                                     '查無事故者 BA.CIPB 資料'
                                   );
                         v_countBI := v_countBI + 1;
                  End;

                 End If;
             -- dbms_output.put_line(' PKG_BA_updCIStatus-sp_BA_updCase3SBMK----------->>[ Insert Error Log ]');
        --  End If;
      End Loop;
         sp_saveMsg( '1','','','','',v_i_user,' 總筆數                 = ' || v_countALL);
         sp_saveMsg( '1','','','','',v_i_user,' 受理編號存在逕退檔筆數 = ' || v_countDRAW);
         sp_saveMsg( '1','','','','',v_i_user,' 處理總筆數             = ' || v_countPOC);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付G案總筆數        = ' || v_countCase3G);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付A案總筆數        = ' || v_countCase3A);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付Z案總筆數        = ' || v_countCase3Z);
         sp_saveMsg( '1','','','','',v_i_user,' 更新領取註記失敗筆數   = ' || v_countErr);
         sp_saveMsg( '1','','','','',v_i_user,' 查無事故者 BA.CIPB 資料= ' || v_countBI);
         sp_saveMsg( '1','','','','',v_i_user,' 未經身分證重號檔選擇   = ' || v_countDupIdn);
         Commit;

         dbms_output.put_line(' 總筆數                 = ' || v_countALL);
         dbms_output.put_line(' 受理編號存在逕退檔筆數 = ' || v_countDRAW);
         dbms_output.put_line(' 處理總筆數             = ' || v_countPOC);
         dbms_output.put_line(' 不給付G案總筆數        = ' || v_countCase3G);
         dbms_output.put_line(' 不給付A案總筆數        = ' || v_countCase3A);
         dbms_output.put_line(' 不給付Z案總筆數        = ' || v_countCase3Z);
         dbms_output.put_line(' 更新領取註記失敗筆數   = ' || v_countErr);
         dbms_output.put_line(' 查無事故者 BA.CIPB 資料= ' || v_countBI);
         dbms_output.put_line(' 未經身分證重號檔選擇   = ' || v_countDupIdn);
     Else
         dbms_output.put_line(' PKG_BA_updCIStatus-sp_BA_updCase3SBMK----------->>參數年月或使用者帳號不可為空白 ]');
     End If;
    End;
    --Procedure sp_BA_updCase3SBMK

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_updCIStatus.sp_BA_updDaily
        PURPOSE:         勞保年金新案日編審後，更新新案事故者，
                         被保險人[臨時註記]。

        PARAMETER(IN):

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/04/18  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_updDaily (
        v_i_user               in      varChar2
    ) is

      v_intyp                  VARCHAR2(1)             := '';         -- 被保險人勞保資料(L) 勞保自願職保資料(V)
      v_ciCount                Number                  := 0;          -- 被保險人基本資料筆數
      v_evtidn                 Baappbase.Evtidnno%TYPE := '';         -- 事故者身分證號
      v_payCode                VARCHAR2(1)             := '';         -- 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
      v_SBMK                   VARCHAR2(1)             := '';         -- 給付註記
      v_tempSBMK               VARCHAR2(1)             := '';         -- 給付註記
      v_result                 VARCHAR2(1)             := NULL;
      v_resultCode             VARCHAR2(300)           := NULL;
      v_CIPB                   CIPB%ROWTYPE;
      v_tempCIPB               CI.CIPB%ROWTYPE;
      v_systp                  VARCHAR2(20)            := 'BaSPDaily';
      v_tmpCount               Number                  := 0;
      v_countALL               NUMBER                  := 0;          --總筆數
      v_countSUC               NUMBER                  := 0;          --成功筆數
      v_countNQ                NUMBER                  := 0;          --不符更新筆數
      v_countBI                NUMBER                  := 0;          --事故者IDN OR CIID 為 NULL
      v_countBK                NUMBER                  := 0;          --事故者 CIPB 資料為多筆
      v_countNOCICipb          NUMBER                  := 0;          --查無事故者 CI.CIPB 資料
      v_countNOBACipb          NUMBER                  := 0;          --查無事故者 BA.CIPB 資料

      --新案
      CURSOR c_dataCur_BAAPP IS
        Select A.Apno, A.SEQNO, A.EvtIdnno, B.ADWKMK
          From Baappbase A,
               (Select APNO, ADWKMK From BAAPPEXPAND Where SEQNO = '0000') B
         Where A.SEQNO = '0000'
           And A.APNO = B.APNO (+)
           And A.CASETYP = '1'
           And (A.CASEMK Is Null Or (A.CASEMK <> 'D') And (A.PROCSTAT <> '99'))
         Order by A.Apno;

    Begin
     If Trim(v_i_user) Is Not Null Then
       For v_CurBAAPP In c_dataCur_BAAPP Loop
           v_countALL := v_countALL + 1;

           v_evtidn  := Null;
           v_payCode := Substr(v_CurBAAPP.Apno,1,1);

           --  事故者加職註記為2時，改讀取加職段資料
           If v_CurBAAPP.Adwkmk = '2' Then
              v_intyp := 'V';
           Else
              v_intyp := 'L';
           End If;

           --  取得 BA 被保險人基本資料筆數
           Select Count(IDN) Into v_ciCount From CIPB
            Where INTYP = v_intyp
              And IDN Like v_CurBAAPP.Evtidnno||'%'
              And APNO  = v_CurBAAPP.APNO
              And SEQNO = v_CurBAAPP.SEQNO;

           --  被保險人基本資料筆數 = 1(排除BK註記)
           If (v_ciCount = 1) Then
               v_evtidn := v_CurBAAPP.Evtidnno;
           Elsif (v_ciCount > 1) Then
               Select Count(*) into v_tmpCount From BADUPEIDN
                Where APNO = v_CurBAAPP.APNO
                  And SELMK = '2';

               If (v_tmpCount = 1) Then
                    Select IDNNO Into v_evtidn From BADUPEIDN
                     Where APNO = v_CurBAAPP.APNO
                       And SELMK = '2';
               Else
                    v_countBK := v_countBK + 1;
                    v_evtidn := Null;
               end if;
           Else
               v_countBK := v_countBK + 1;
               v_evtidn := Null;
           End If;

           v_CIPB := null;
           If (v_evtidn Is Not Null) Then
             Begin
               Select * Into v_CIPB From CIPB
                Where INTYP = v_intyp
                  And IDN Like v_evtidn||'%'
                  And APNO  = v_CurBAAPP.APNO
                  And SEQNO = v_CurBAAPP.SEQNO;

               If (v_CIPB.Idn Is Not Null) And (v_CIPB.Ciid Is Not Null ) Then
                 Begin
                  v_SBMK := '1';
                  Select * Into v_tempCIPB From CI.CIPB
                   Where FTYP = v_intyp
                     And CIID = v_CIPB.Ciid;

                  If ((v_payCode = 'L' And NVL(v_tempCIPB.SBMK2,'0') < '1') or
                     (v_payCode = 'K' And NVL(v_tempCIPB.SBMK1,'0') < '1')  or
                     (v_payCode = 'S' And NVL(v_tempCIPB.SBMK3,'0') < '1')) And
                     (NVL(v_tempCIPB.UINMK,' ') <> 'Y') Then

                     If v_payCode = 'L' Then
                        v_tempSBMK := SubStr(v_CIPB.BMK,2,1);
                     Elsif v_payCode = 'K' Then
                        v_tempSBMK := SubStr(v_CIPB.BMK,1,1);
                     Elsif v_payCode = 'S' Then
                        v_tempSBMK := SubStr(v_CIPB.BMK,3,1);
                     End If;
                     -- Update CIPB SDMK
                     sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                    v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                    v_CIPB.CIID,             --  勞就保識別碼
                                    v_i_user,                --  UserID
                                    v_tempSBMK,
                                    v_SBMK,                  --  SDMK Value
                                    v_CurBAAPP.Apno,         --  受理編號
                                    '',                      --  核定年月
                                    '1',                     --  案件類別
                                    v_systp,                 --  作業名稱
                                    v_result,
                                    v_resultCode
                                   );
                     if v_result = '1' then
                       v_countSUC := v_countSUC + 1;
                     End If;
                  Else
                      sp_saveMsg( '0',
                                  v_CurBAAPP.Apno,
                                  '',
                                  v_CIPB.CIID,
                                  v_intyp,
                                  v_i_user,
                                 '不符更新SBMK'
                                );
                       v_countNQ := v_countNQ + 1;
                  End If;
                 Exception
                    when NO_DATA_FOUND then
                         sp_saveMsg( '0',
                                     v_CurBAAPP.Apno,
                                     '',
                                     '',
                                     v_intyp,
                                     v_i_user,
                                     '查無事故者 CI.CIPB 資料'
                                   );
                         v_countNOCICipb := v_countNOCICipb + 1;
                  End;
               Else
                  sp_saveMsg( '0',
                              v_CurBAAPP.Apno,
                              '',
                              v_CIPB.CIID,
                              v_intyp,
                              v_i_user,
                              '事故者IDN OR CIID 為 NULL'
                            );
                  v_countBI := v_countBI + 1;
               End If;
             Exception
                    when NO_DATA_FOUND then
                         sp_saveMsg( '0',
                                     v_CurBAAPP.Apno,
                                     '',
                                     '',
                                     v_intyp,
                                     v_i_user,
                                     '查無事故者 BA.CIPB 資料'
                                   );
                         v_countNOBACipb := v_countNOBACipb + 1;
                  End;
           End If;
       -- dbms_output.put_line(' PKG_BA_updCIStatus-sp_BA_updDaily----------->>[ Insert Error Log ]');
       End Loop;
       Commit;
     Else
          dbms_output.put_line(' User ID 不可為空白! ');
     End If;

         dbms_output.put_line('          處理臨時註記筆數 = ' || v_countALL);
         dbms_output.put_line('                  成功筆數 = ' || v_countSUC);
         dbms_output.put_line('              不符更新筆數 = ' || v_countNQ);
         dbms_output.put_line(' 事故者IDN OR CIID 為 NULL = ' || v_countBI);
         dbms_output.put_line(' 事故者 BA.CIPB 資料為多筆 = ' || v_countBK);
         dbms_output.put_line('   查無事故者 CI.CIPB 資料 = ' || v_countNOCICipb);
         dbms_output.put_line('   查無事故者 BA.CIPB 資料 = ' || v_countNOBACipb);
  End;
    --Procedure sp_BA_updDaily

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_updCIStatus.sp_BA_updBatchSBMK
        PURPOSE:         將勞保年金已核付案件，年金類別分別更新：老年被保險人領取老年給付註記為3，
                         失能被保險人領取失能給付註記為4，遺屬被保險人領取死亡給付註記為3。

        PARAMETER(IN):

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/04/18  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_updBatchSBMK (
        v_i_user               in      varChar2
    ) is

      v_intyp                  VARCHAR2(1)             := '';         -- 被保險人勞保資料(L) 勞保自願職保資料(V)
      v_ciid                   CIPB.CIID%TYPE          := '';         -- 勞就保識別碼
      v_ciCount                Number                  := 0;          -- Ci被保險人基本資料筆數
      v_baCount                Number                  := 0;          -- Ba被保險人基本資料筆數
      v_evtidn                 Baappbase.Evtidnno%TYPE := '';         -- 事故者身分證號
      v_payCode                VARCHAR2(1)             := '';         -- 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
      v_SBMK                   VARCHAR2(1)             := '';         -- 給付註記
      v_tempSBMK               VARCHAR2(1)             := '';         -- 給付註記改前值
      v_CICIPB                 CI.CIPB%ROWTYPE;
      v_result                 VARCHAR2(1)             := NULL;
      v_resultCode             VARCHAR2(300)           := NULL;
      v_TotalCount             Number                  := 0;          -- 批次更新總筆數
      v_CiCount0               Number                  := 0;          -- 承保查無資料筆數
      v_CiCount1               Number                  := 0;          -- 承保更新成功筆數
      v_CiCount2               Number                  := 0;          -- 承保更新失敗筆數
      v_BaCount0               Number                  := 0;          -- CIPB SBMK 不為2筆數
      v_BaCount1               Number                  := 0;          -- 事故者Cipb為多筆不予更新SBMK筆數
      v_BaCount2               Number                  := 0;          -- 查無事故者 CIPB CIID資料筆數
      v_BaCount3               Number                  := 0;          -- 查無事故者Ci.CIPB 資料
      v_BaCount4               Number                  := 0;          -- BA CIID查無事故者Ci.CIPB 資料
      v_BaCount5               Number                  := 0;          -- BA 事故者Cipb為多筆不予更新SBMK筆數
      v_systp                  VARCHAR2(20)            := 'BaSPBatch';
      --勞保年金已核付案件
      CURSOR c_dataCur_BAAPP IS
        Select A.Apno, A.Seqno, A.EvtIdnno, A.CASETYP, B.ADWKMK, A.Evtbrdate
          From Baappbase A,
               (Select APNO, ADWKMK From BAAPPEXPAND Where SEQNO = '0000') B
         Where (A.APNO Like 'S%' Or A.APNO Like 'L%')
           And A.SEQNO = '0000'
           And A.APNO = B.APNO (+)
           And A.APNO in  (Select Distinct C1.Apno From BaDapr C1
                            Where (C1.APNO Like 'S%' Or C1.APNO Like 'L%')
                              And C1.Seqno = '0000'
                              And C1.MCHKTYP = '1'
                              And C1.ISSUYM <= '201203'
                              And C1.MTESTMK = 'F'
                              And C1.Aplpaymk = '3'
                              And Trim(C1.Aplpaydate) Is Not Null
                              And Trim(C1.MANCHKMK) Is Not Null)
           And A.CASETYP In ('2','4')
           And (A.CASEMK Is Null Or (A.CASEMK <> 'D') And (A.PROCSTAT <> '99'))
         Order by A.Apno;

    Begin
    if trim(v_i_user) Is Not Null Then
       For v_CurBAAPP In c_dataCur_BAAPP Loop

           v_TotalCount := v_TotalCount +1;
           v_evtidn  := Null;
           v_payCode := Substr(v_CurBAAPP.Apno,1,1);

           --  事故者加職註記為2時，改讀取加職段資料
           If v_CurBAAPP.Adwkmk = '2' Then
              v_intyp := 'V';
           Else
              v_intyp := 'L';
           End If;

           --  取得被保險人基本資料筆數
           If ( v_payCode = 'L' ) Then
                -- 取得CI被保險人基本資料筆數
                Select Count(IDN) Into v_ciCount From CI.CIPB
                 Where FTYP = v_intyp
                   And IDN Like v_CurBAAPP.Evtidnno||'%'
                   And SBMK2 = '2';

                -- 取得BA被保險人基本資料筆數
                Select Count(IDN) Into v_baCount From BA.CIPB
                 Where INTYP = v_intyp
                   And IDN Like v_CurBAAPP.Evtidnno||'%'
                   And APNO  = v_CurBAAPP.APNO
                   And SEQNO = v_CurBAAPP.SEQNO;

           elsif ( v_payCode = 'S' ) Then
                -- 取得CI被保險人基本資料筆數
                Select Count(IDN) Into v_ciCount From CI.CIPB
                 Where FTYP = v_intyp
                   And IDN Like v_CurBAAPP.Evtidnno||'%'
                   And SBMK3 = '2';

                -- 取得BA被保險人基本資料筆數
                Select Count(IDN) Into v_baCount From BA.CIPB
                 Where INTYP = v_intyp
                   And IDN Like v_CurBAAPP.Evtidnno||'%'
                   And APNO  = v_CurBAAPP.APNO
                   And SEQNO = v_CurBAAPP.SEQNO;
           end if;

           --  CI and BA 被保險人基本資料筆數 = 1(排除BK註記)
           If (v_ciCount = 1) And (v_baCount = 1) And (v_CurBAAPP.Evtidnno Is Not Null) Then
               v_evtidn := v_CurBAAPP.Evtidnno;
             Begin

               Select Ciid Into v_ciid From CIPB
                Where INTYP = v_intyp
                  And IDN Like v_evtidn||'%'
                  And BRDTE = v_CurBAAPP.Evtbrdate
                  And APNO  = v_CurBAAPP.APNO
                  And SEQNO = v_CurBAAPP.SEQNO;

               If (v_ciid Is Not Null) Then
                 Begin

                  Select * Into v_CICIPB From CI.CIPB
                   Where CIID = v_ciid
                     And FTYP = v_intyp;

                  v_SBMK := '3';
                  If ( v_payCode = 'L' ) Then
                       v_tempSBMK := v_CICIPB.SBMK2;
                  Elsif ( v_payCode = 'S' ) Then
                       v_tempSBMK := v_CICIPB.SBMK3;
                  End If;

                 -- Update CIPB SIDMK
                  If (v_tempSBMK = '2') Then

                      sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                     v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                     v_ciid,                  --  勞就保識別碼
                                     v_i_user,                --  UserID
                                     v_tempSBMK,
                                     v_SBMK,                  --  SDMK Value
                                     v_CurBAAPP.Apno,         --  受理編號
                                     '',                      --  核定年月
                                     v_CurBAAPP.CASETYP,      --  案件類別
                                     v_systp,                 --  作業名稱
                                     v_result,
                                     v_resultCode
                                    );

                      sp_saveMsg( '0',
                                  v_CurBAAPP.Apno,
                                  '',
                                  v_CICIPB.CIID,
                                  v_intyp,
                                  v_i_user,
                                  v_resultCode
                                 );

                      if v_result = '0' then
                         v_CiCount0 := v_CiCount0 + 1;
                      elsif v_result = '1' then
                         v_CiCount1 := v_CiCount1 + 1;
                      elsif v_result = '2' then
                         v_CiCount1 := v_CiCount2 + 1;
                      end if;
                  Else
                      sp_saveMsg( '0',
                                  v_CurBAAPP.Apno,
                                  '',
                                  v_CICIPB.CIID,
                                  v_intyp,
                                  v_i_user,
                                  'CIPB SBMK 不為2'
                                );
                      v_BaCount0 := v_BaCount0 + 1;
                  End If;
                 Exception
                   when NO_DATA_FOUND then
                        sp_saveMsg( '0',
                                    v_CurBAAPP.Apno,
                                    '',
                                    v_ciid,
                                    v_intyp,
                                    v_i_user,
                                    'BA CIID查無事故者CI.CIPB 資料'
                                  );
                        v_BaCount4 := v_BaCount4 + 1;
                 End;
               Else
                      sp_saveMsg( '0',
                                  v_CurBAAPP.Apno,
                                  '',
                                  v_CICIPB.CIID,
                                  v_intyp,
                                  v_i_user,
                                  '查無事故者CIPB CIID資料'
                                 );
                      v_BaCount2 := v_BaCount2 + 1;
               End If;
             Exception
                   when NO_DATA_FOUND then
                        sp_saveMsg( '0',
                                    v_CurBAAPP.Apno,
                                    '',
                                    v_ciid,
                                    v_intyp,
                                    v_i_user,
                                    '查無事故者BA.CIPB 資料'
                                  );
                        v_BaCount3 := v_BaCount3 + 1;
                 End;
           Elsif (v_ciCount > 1) Then
               sp_saveMsg( '0',
                           v_CurBAAPP.Apno,
                           '',
                           v_CICIPB.CIID,
                           v_intyp,
                           v_i_user,
                           '事故者Ci Cipb為多筆不予更新SBMK'
                          );
               v_BaCount1 := v_BaCount1 + 1;
           Elsif (v_baCount > 1) Then
               sp_saveMsg( '0',
                           v_CurBAAPP.Apno,
                           '',
                           v_CICIPB.CIID,
                           v_intyp,
                           v_i_user,
                           '事故者Ba Cipb為多筆不予更新SBMK'
                          );
               v_BaCount1 := v_BaCount1 + 1;
           Else
               sp_saveMsg( '0',
                           v_CurBAAPP.Apno,
                           '',
                           v_CICIPB.CIID,
                           v_intyp,
                           v_i_user,
                           '查無事故者BA.CIPB 資料'
                         );
                      v_BaCount3 := v_BaCount3 + 1;
           End If;
       End Loop;
       else
           dbms_output.put_line(' User ID 不可為空白! ');
       end if;

           sp_saveMsg( '1','','','','',v_i_user,'批次更新永久註記總筆數              ： '||v_TotalCount);
           sp_saveMsg( '1','','','','',v_i_user,'承保查無資料筆數                    ： '||v_CiCount0);
           sp_saveMsg( '1','','','','',v_i_user,'承保更新成功筆數                    ： '||v_CiCount1);
           sp_saveMsg( '1','','','','',v_i_user,'承保更新失敗筆數                    ： '||v_CiCount2);
           sp_saveMsg( '1','','','','',v_i_user,'CIPB SBMK 不為2筆數                 ： '||v_BaCount0);
           sp_saveMsg( '1','','','','',v_i_user,'事故者Ci Cipb為多筆不予更新SBMK筆數 ： '||v_BaCount1);
           sp_saveMsg( '1','','','','',v_i_user,'查無事故者CIPB CIID資料             ： '||v_BaCount2);
           sp_saveMsg( '1','','','','',v_i_user,'查無事故者CI.CIPB 資料              ： '||v_BaCount3);
           sp_saveMsg( '1','','','','',v_i_user,'BA CIID查無事故者CI.CIPB 資料       ： '||v_BaCount4);
           sp_saveMsg( '1','','','','',v_i_user,'事故者Ba Cipb為多筆不予更新SBMK筆數 ： '||v_BaCount5);

           dbms_output.put_line('批次更新永久註記總筆數              ： '||v_TotalCount);
           dbms_output.put_line('承保查無資料筆數                    ： '||v_CiCount0);
           dbms_output.put_line('承保更新成功筆數                    ： '||v_CiCount1);
           dbms_output.put_line('承保更新失敗筆數                    ： '||v_CiCount2);
           dbms_output.put_line('CIPB SBMK 不為2筆數                 ： '||v_BaCount0);
           dbms_output.put_line('事故者Ci Cipb為多筆不予更新SBMK筆數 ： '||v_BaCount1);
           dbms_output.put_line('查無事故者CIPB CIID資料             ： '||v_BaCount2);
           dbms_output.put_line('查無事故者CI.CIPB 資料              ： '||v_BaCount3);
           dbms_output.put_line('BA CIID查無事故者CI.CIPB 資料       ： '||v_BaCount4);
           dbms_output.put_line('事故者Ba Cipb為多筆不予更新SBMK筆數 ： '||v_BaCount5);
           Commit;
    End;
    --Procedure sp_BA_updBatchSBMK

/********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_updCIStatus.sp_BA_singleUpdate
        PURPOSE:         單筆更新被保險人[永久註記(SBMK)]及[管制加保註記(UINMK)]與逕退。

        PARAMETER(IN):   *v_i_apno          (varChar2)       -- 受理編號
					     *v_i_sbmk          (varChar2)       -- 永久註記    ：'Y' = 新增永久註記,     'N' = 註銷永久註記,     null = 不更新
					     *v_i_uinmk         (varChar2)       -- 管制加保註記：'Y' = 新增管制加保註記, 'N' = 註銷管制加保註記, null = 不更新
					     *v_i_withdraw      (varChar2)       -- 逕退        ：'Y' = 新增逕退,         'N' = 註銷逕退,         null = 不更新
					     *v_i_user          (varChar2)       -- 使用者帳號

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/11/06  Justin Yu    Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_singleUpdate (
        v_i_apno               in      varChar2,
        v_i_sbmk               in      varChar2,                      -- 領取註記     Y:新增  N:註銷  空白:不處理
        v_i_uinmk              in      varChar2,                      -- 管制加保註記 Y:新增  N:註銷  空白:不處理
        v_i_withdraw           in      varChar2,                      -- 逕退         Y:新增          空白:不處理
        v_i_user               in      varChar2                       -- 員工編號
    ) is

      v_intyp                  VARCHAR2(1)             := '';         -- 被保險人勞保資料(L) 勞保自願職保資料(V)
      v_evtidn                 Baappbase.Evtidnno%TYPE := '';         -- 事故者身分證號
      v_payCode                VARCHAR2(1)             := '';         -- 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
      v_SBMK                   VARCHAR2(1)             := '';         -- 給付註記
      v_tempSBMK               VARCHAR2(1)             := '';         -- 給付註記
      v_UINMK                  VARCHAR2(1)             := '';         -- 管制加保註記
      v_tempUINMK              VARCHAR2(1)             := '';         -- 管制加保註記
      v_CIPB                   CIPB%ROWTYPE;
      v_result                 VARCHAR2(1)             := NULL;
      v_resultCode             VARCHAR2(300)           := NULL;
      v_systp                  VARCHAR2(20)            := 'BaSPSingle';
      v_upcode                 VARCHAR2(1)             := NULL;
      v_count                  NUMBER                  := 0;
      v_countDRAW              NUMBER                  := 0;          --受理編號存在逕退檔筆數
      v_countALL               NUMBER                  := 0;          --總筆數
      v_countPOC               NUMBER                  := 0;          --處理總筆數
      v_countSUC               NUMBER                  := 0;          --更新領取註記成功筆數
      v_countCase1G            NUMBER                  := 0;          --給付案G總筆數
      v_countCase3G            NUMBER                  := 0;          --不給付G案總筆數
      v_countCase1A            NUMBER                  := 0;          --給付案A總筆數
      v_countCase3A            NUMBER                  := 0;          --不給付A案總筆數
      v_countCase1Z            NUMBER                  := 0;          --給付案A總筆數
      v_countCase3Z            NUMBER                  := 0;          --不給付A案總筆數
      v_countToDRAW            NUMBER                  := 0;          --逕退總筆數
      v_countToDRAWSUC         NUMBER                  := 0;          --逕退成功筆數
      v_countToDRAWErr         NUMBER                  := 0;          --逕退失敗筆數
      v_countErr               NUMBER                  := 0;          --更新領取註記失敗筆數
      v_countBI                NUMBER                  := 0;          --查無事故者 BI CIPB筆數
      v_DupCount               NUMBER                  := 0;          --事故者CIPB暫存筆數
      v_countDupIdn            NUMBER                  := 0;          --事故者CIPB筆數大於一筆未經身分證重號檔選擇
      v_updBACIBMK             CIPB.BMK%TYPE           := '';         --更新BA CIPB BMK
      v_TXCD2                  BAWITHDRAW.TXCD2%TYPE   := '';         --逕退檔註記
      v_ciCIPB                 CI.CIPB%ROWTYPE;                       --承保系統被保險人基本資料 2013/03/07 Add By ChungYu
      v_ubno                   Baappbase.Lsubno%TYPE   := '';         --單位保險證號  2013/06/14 Add By ChungYu
      v_jobno                  mmjoblog.job_no%TYPE;
      v_starttime              TIMESTAMP;                             --開始時間

      --核定年月當月所核付的新案、不給付案、暫緩給付案清單
      CURSOR c_dataCur_BAAPP IS
        Select A.APNO,                                             -- 受理編號
               A.SEQNO,                                            -- 受款人序
               A.APPDATE,                                          -- 受理日期
               A.PAYKIND,                                          -- 給付種類
               Decode(D.IDNNO,Null,A.EvtIdnno,D.IDNNO) As EVTIDNO, -- 事故者身分證號
               A.EVTBRDATE,                                        -- 事故者生日
               A.EVTNAME,                                          -- 事故者姓名
               A.EVTJOBDATE,                                       -- 事故日期
               A.APUBNO,                                           -- 申請單位保險證號  2013/06/14 老年逕退改用最後單位保險證號
               A.LSUBNO,                                           -- 最後單位保險證號  2013/06/14 老年逕退改用最後單位保險證號
               B.ADWKMK,                                           -- 加職註記
               C.MCHKTYP AS CASETYP,                               -- 案件類別
               C.APLPAYDATE,                                       -- 帳務日期
               C.ISSUYM,                                           -- 最小核定年月
               E.NOTEMK,                                           -- 行政支援註記
               '0' AS APCOUNT                                      -- 同給付別非同受理編號筆數
          From (Select A1.* From Baappbase A1
                 Where A1.APNO = v_i_apno
                   And A1.SEQNO = '0000') A,
               (Select APNO, SEQNO, ADWKMK From BAAPPEXPAND Where SEQNO = '0000') B,
               (Select Distinct C1.Apno, C1.MCHKTYP, C1.APLPAYDATE, C1.ISSUYM
                  From BaDapr C1
                 Where C1.Seqno = '0000'
                   --And C1.MCHKTYP In ('1', '3', '6')
                   And C1.ISSUYM = (Select Distinct min(C2.issuym) From BADAPR C2
                   					Where C2.APNO = v_i_apno
                   					  And C2.SEQNO = '0000'
                   					  And C2.MTESTMK = 'F'
                   					  And ((C2.Aplpaymk = '3' And Trim(C2.Aplpaydate) Is Not Null)              -- 2014/05/07 Modify By  ChungYu 修改暫緩給付案讀取條件
                     				    Or (C2.Aplpaymk Is Null And C2.Aplpaydate Is Null And C2.MCHKTYP = '3'))
                              And Trim(C2.MANCHKMK) Is Not Null
                   					)
                   And C1.MTESTMK = 'F'
                   And ((C1.Aplpaymk = '3' And Trim(C1.Aplpaydate) Is Not Null)              -- 2014/05/07 Modify By  ChungYu 修改暫緩給付案讀取條件
                     Or (C1.Aplpaymk Is Null And C1.Aplpaydate Is Null And C1.MCHKTYP = '3'))
                   And Trim(C1.MANCHKMK) Is Not Null
                   ) C,
               (Select D1.APNO, D1.IDNNO From BADUPEIDN D1 Where SELMK = '2' ) D,
               (Select E1.APNO, 'Y' As NOTEMK From MAADMREC E1
                 Where E1.LETTERTYPE = '21'
                   And (NVL(E1.DELMK, ' ') <> 'D')
                   And ((NVL(E1.NDOMK1, ' ') = '55') Or
                        (NVL(E1.NDOMK2, ' ') = '55')) )E
                 Where A.SEQNO = '0000'
                   And A.APNO = B.APNO(+)
                   And A.APNO = C.APNO
                   And A.APNO = D.APNO(+)
                   And A.APNO = E.APNO(+)
                 Order by A.APNO, C.MCHKTYP;
-- ============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
    PROCEDURE get_jobno
    IS
    BEGIN
       SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_jobno FROM DUAL;

    END get_jobno;
-- ============================================================================

--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------

    Begin
    --寫入開始LOG --(S)
    get_jobno;
    v_starttime := SYSTIMESTAMP;
    SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'sp_BA_singleUpdate',
                    p_step   => '單筆更新被保險人永久註記及管制加保註記與逕退',
                    p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||'),'||
                                '受理編號：('||v_i_apno||'),'||CHR(10)||
                                '是否更新領取給付註記：('||v_i_sbmk||'),'||CHR(10)||
                                '是否更新管制加保註記：('||v_i_uinmk||'),'||CHR(10)||
                                '是否逕退：('||v_i_withdraw||'),');
    --寫入開始LOG --(E)

     If (Trim(v_i_apno) Is Not Null) And (Trim(v_i_user) Is Not Null) Then
         For v_CurBAAPP In c_dataCur_BAAPP Loop

             v_countALL:= v_countALL + 1;
             v_evtidn  := v_CurBAAPP.Evtidno;
             v_payCode := Substr(v_CurBAAPP.Apno,1,1);

             /*
             -- 受理案件是否已寫入逕退檔
             SELECT COUNT(*) INTO v_count FROM BAWITHDRAW
              WHERE apno = v_CurBAAPP.Apno;

             -- 2012/10/17 讀取逕退檔註記
             If v_count > 0 Then
                SELECT Trim(T.TXCD2) INTO v_TXCD2 FROM BAWITHDRAW T
                 WHERE T.APNO = v_CurBAAPP.Apno
                   AND Rownum = 1;
             End If;

             If (v_count > 0) and (v_TXCD2 = 'Y') Then   -- 2012/10/17 Modify By ChungYu
                v_countDRAW := v_countDRAW + 1;
             Else
             */
                 --  事故者加職註記為2時，改讀取加職段資料
                 If v_CurBAAPP.Adwkmk = '2' Then
                    v_intyp := 'V';
                 Else
                    v_intyp := 'L';
                 End If;

                 If (v_evtidn Is Not Null) Then
                   Begin

                     Select Count(IDN) Into v_DupCount From CIPB
                      Where INTYP = v_intyp
                        And IDN Like v_evtidn||'%'
                        And APNO = v_CurBAAPP.Apno
                        And SEQNO = v_CurBAAPP.Seqno;

                     If v_DupCount = 1 Then

                         Select * Into v_CIPB From CIPB
                          Where INTYP = v_intyp
                            And IDN Like v_evtidn||'%'
                            And APNO = v_CurBAAPP.Apno
                            And SEQNO = v_CurBAAPP.Seqno;

                         -- 2013/03/07 比對被保險人註記狀態改讀取承保CIPB
                         If v_CIPB.Ciid Is Not Null Then
                            Select * Into v_ciCIPB From CI.CIPB
                             Where FTYP = v_intyp
                               And CIID = v_CIPB.Ciid;
                         End if;
                         -- 2013/03/07 比對被保險人註記狀態改讀取承保CIPB

                         If (v_CIPB.Ciid Is Not Null) And (v_ciCIPB.Ciid Is Not Null) Then
                            v_countPOC := v_countPOC +1 ;
                            v_upcode := null;

                            /*---------------------------------------------------------------------------------------
                                新增永久註記：
                                老年年金：
                                於每月勞保老年年金月核定作業後，讀取該月核付之新案，將對應之「領取老年給付註記_SBMK2」
                                欄位值更新為3-已核付。

                                失能年金：
                                於每月勞保失能年金月核定作業後，讀取該月核付之新案，於對應之「領取失能給付註記_SBMK1」
                                欄寫入4-已核付。

                                遺屬年金：
                                於每月勞保遺屬年金月核定作業後，讀取該月核付之新案，於對應之「領取死亡給付註記_SBMK3」
                                欄寫入3-已核付。
                              ----------------------------------------------------------------------------------------
                            */
                            If NVL(v_i_sbmk, ' ') = 'Y' Then
                               If (v_payCode = 'L') Or (v_payCode = 'S') Then

                                  -- 取得老年及遺屬當前的給付註記
                                  If (v_payCode = 'L') Then
                                      v_tempSBMK  := v_ciCIPB.SBmk2;
                                      v_tempUINMK := v_ciCIPB.Uinmk;
                                  Elsif (v_payCode = 'S') Then
                                      v_tempSBMK  := v_ciCIPB.SBmk3;
                                      v_tempUINMK := v_ciCIPB.Uinmk;
                                  End If;

                                  v_SBMK := 3;
                                  v_UINMK := 'Y';
                                  v_upcode := 'G';       -- G：符合更新已領給付條件更新成功

                                  If (NVL(v_tempSBMK,' ') = NVL(v_SBMK,' ')) then
                                      v_upcode := 'A';   -- A：現存已領給付註記與欲更新相同
                                  Else
                                     if (v_tempSBMK = '1') Or (trim(v_tempSBMK) Is Null) Then

                                         sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                        v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                        v_CIPB.Ciid,             --  勞就保識別碼
                                                        v_i_user,                --  UserID
                                                        v_tempSBMK,              --  Befort SIDMK Value
                                                        v_SBMK,                  --  SIDMK Value
                                                        v_CurBAAPP.Apno,         --  受理編號
                                                        v_CurBAAPP.Issuym,       --  核定年月
                                                        v_CurBAAPP.casetyp,      --  案件類別
                                                        v_systp,
                                                        v_result,
                                                        v_resultCode
                                                      );

                                         -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                         If (v_result = '1') Then
                                             If (v_payCode = 'L')  Then
                                                 v_updBACIBMK := Substr(v_CIPB.Bmk,1,1)||v_SBMK||Substr(v_CIPB.Bmk,3,8);
                                             Elsif (v_payCode = 'S')  Then
                                                 v_updBACIBMK := Substr(v_CIPB.Bmk,1,2)||v_SBMK||Substr(v_CIPB.Bmk,4,7);
                                             End if;

                                             Update CIPB
                                                Set BMK = v_updBACIBMK
                                              Where INTYP = v_CIPB.Intyp
                                                And IDN   = v_CIPB.Idn
                                                And APNO  = v_CurBAAPP.Apno
                                                And SEQNO = v_CurBAAPP.Seqno;

                                         End if;

                                         -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                      else
                                         v_upcode := 'Z';             -- Z:現存已領給付註記不符可更新條件
                                     end if;
                                  end If;
                                  -- 更新失敗寫LOG，並將v_upcode設為N
                                  if v_result <> '1' Then
                                     v_upcode := 'N';                 -- N:表示失敗
                                     sp_saveMsg( '0',
                                                 v_CurBAAPP.Apno,
                                                 v_CurBAAPP.Issuym,
                                                 v_CIPB.CIID,
                                                 v_intyp,
                                                 v_i_user,
                                                 v_resultCode
                                               );
                                  End if;

                               /*
                                  失能年金：
                                  於每月勞保失能年金月核定作業後，讀取該月核付之新案，於對應之「領取失能給付註記_SBMK1」
                                  欄寫入4-已核付。
                               */
                               Elsif v_payCode = 'K' Then
                                  v_tempSBMK  := v_ciCIPB.SBmk1;
                                  v_tempUINMK := v_ciCIPB.Uinmk;
                                  v_SBMK := 4;
                                  v_UINMK := 'Y';
                                  v_upcode := 'G';        -- G：符合更新已領給付條件更新成功
                                  If (NVL(v_tempSBMK,' ') = NVL(v_SBMK,' ')) then
                                      v_upcode := 'A';    -- A：現存已領給付註記與欲更新相同
                                  Else
                                      if (v_tempSBMK = '1') Or (trim(v_tempSBMK) Is Null) Then
                                          sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                         v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                         v_CIPB.Ciid,             --  勞就保識別碼
                                                         v_i_user,                --  UserID
                                                         v_tempSBMK,              --  Befort SIDMK Value
                                                         v_SBMK,                  --  SIDMK Value
                                                         v_CurBAAPP.Apno,         --  受理編號
                                                         v_CurBAAPP.Issuym,       --  核定年月
                                                         v_CurBAAPP.casetyp,      --  案件類別
                                                         v_systp,
                                                         v_result,
                                                         v_resultCode
                                                       );

                                         -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                         If (v_result = '1') Then
                                             v_updBACIBMK := v_SBMK||Substr(v_CIPB.Bmk,2,9);

                                             Update CIPB
                                                Set BMK = v_updBACIBMK
                                              Where INTYP = v_CIPB.Intyp
                                                And IDN   = v_CIPB.Idn
                                                And APNO  = v_CurBAAPP.Apno
                                                And SEQNO = v_CurBAAPP.Seqno;

                                         End if;

                                         -- 2012/05/18 新增同時同步BA CIPB BMK 欄位

                                      else
                                         v_upcode := 'Z';           -- Z:現存已領給付註記不符可更新條件
                                     end if;
                                  end If;
                                  -- 更新失敗寫LOG，並將v_upcode設為N
                                  if v_result <> '1' Then
                                     v_upcode := 'N';               -- N:表示失敗
                                     sp_saveMsg( '0',
                                                 v_CurBAAPP.Apno,
                                                 v_CurBAAPP.Issuym,
                                                 v_CIPB.CIID,
                                                 v_intyp,
                                                 v_i_user,
                                                 v_resultCode
                                               );
                                  End if;
                                End if;

                              if v_upcode = 'G' Then
                                 v_countCase1G := v_countCase1G + 1;
                              elsif v_upcode = 'A' Then
                                 v_countCase1A := v_countCase1A + 1;
                              elsif v_upcode = 'Z' Then
                                 v_countCase1Z := v_countCase1Z + 1;
                              end if;
                            End if;    ---------------------新增永久註記 end-------------------------

                            /*---------------------------------------------------------------------------------------
                                新增管制加保註記
                              ---------------------------------------------------------------------------------------
                            */
                            IF (NVL(v_i_uinmk,' ') = 'Y') Then
                                      v_tempUINMK := v_ciCIPB.Uinmk;
                                      v_UINMK := 'Y';
                            /*IF (NVL(v_i_uinmk,' ') = 'Y') And
                                 (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                            */
                                      sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                      v_CIPB.Ciid,             --  勞就保識別碼
                                                      v_i_user,                --  UserID
                                                      v_tempUINMK,             --  Befort UINMK Value
                                                      v_UINMK,                 --  UINMK Value
                                                      v_CurBAAPP.Apno,         --  受理編號
                                                      v_CurBAAPP.Issuym,       --  核定年月
                                                      v_CurBAAPP.casetyp,      --  案件類別
                                                      v_systp,
                                                      v_result,
                                                      v_resultCode
                                                     );

                                      -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                      If (v_result = '1') Then

                                          Update CIPB
                                             Set UINMK = v_UINMK
                                           Where INTYP = v_CIPB.Intyp
                                             And IDN   = v_CIPB.Idn
                                             And APNO  = v_CurBAAPP.Apno
                                             And SEQNO = v_CurBAAPP.Seqno;

                                      End if;
                                      -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                      -- 更新失敗寫LOG
                                      if v_result <> '1' Then
                                         sp_saveMsg( '0',
                                                     v_CurBAAPP.Apno,
                                                     v_CurBAAPP.Issuym,
                                                     v_CIPB.CIID,
                                                     v_intyp,
                                                     v_i_user,
                                                     v_resultCode
                                                   );
                                      End if;
                            End if;    ---------------------新增管制加保註記 end-------------------------

                            /*---------------------------------------------------------------------------------------
                                註銷永久註記
                                遺屬年金：
                                於每月勞保遺屬年金月核定作業後，讀取該月案件類別為不給付案及暫緩給付案，
                                且無同給付別、同IDN、出生及姓名之續發案或結案者，將對應之「領取死亡給付註記」1-已受理、3-已核付或空值，
                                更新為9-不給付案（遺屬年金），其他欄位值不予更新。
                              ---------------------------------------------------------------------------------------
                            */
                            IF NVL(v_i_sbmk, ' ') = 'N' THEN
                               v_upcode := 'G';
                               If (v_payCode = 'S') Then
                                   If (v_CurBAAPP.APCOUNT = 0) And ((v_ciCIPB.SBmk3 = '1') Or
                                      (v_ciCIPB.SBmk3 = '3') Or (Trim(v_ciCIPB.SBmk3) Is Null)) Then
                                        v_tempSBMK  := v_ciCIPB.SBmk3;
                                        v_SBMK := 9;
                                        If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                            sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                           v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                           v_CIPB.Ciid,             --  勞就保識別碼
                                                           v_i_user,                --  UserID
                                                           v_tempSBMK,              --  Befort SIDMK Value
                                                           v_SBMK,                  --  SIDMK Value
                                                           v_CurBAAPP.Apno,         --  受理編號
                                                           v_CurBAAPP.Issuym,       --  核定年月
                                                           v_CurBAAPP.casetyp,      --  案件類別
                                                           v_systp,
                                                           v_result,
                                                           v_resultCode
                                                          );
                                            If v_result <> '1' Then
                                               v_upcode := 'N';
                                               sp_saveMsg( '0',
                                                           v_CurBAAPP.Apno,
                                                           v_CurBAAPP.Issuym,
                                                           v_CIPB.CIID,
                                                           v_intyp,
                                                           v_i_user,
                                                           v_resultCode
                                                         );
                                            Else
                                                 v_updBACIBMK := Substr(v_CIPB.Bmk,1,2)||v_SBMK||Substr(v_CIPB.Bmk,4,7);
                                                 Update CIPB
                                                    Set BMK = v_updBACIBMK
                                                  Where INTYP = v_CIPB.Intyp
                                                    And IDN   = v_CIPB.Idn
                                                    And APNO  = v_CurBAAPP.Apno
                                                    And SEQNO = v_CurBAAPP.Seqno;
                                            End if;
                                        Else
                                            v_upcode := 'A';
                                        End if;
                                   Else
                                       v_upcode := 'Z';
                                   End if;

                               /* 老年年金清除臨時及永久註記之條件：
                                  （1）「領取老年給付註記CIPB_API_SBMK2」：於每月勞保老年年金月核定作業後，
                                        讀取該月案件類別為不給付及暫緩給付案，且無同給付別、同IDN、出生及姓名之續發案或結案者，
                                        將本欄之1-已受理、3-已核付（老年年金）清為空白，  其他欄位值不予清除。
                               */
                               Elsif (v_payCode = 'L') Then
                                     If (v_CurBAAPP.APCOUNT = 0) And ((v_ciCIPB.SBmk2 = '1') Or
                                        (v_ciCIPB.SBmk2 = '3')) Then
                                         v_tempSBMK  := v_ciCIPB.SBmk2;
                                         v_SBMK := Null;
                                         If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                             sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                            v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                            v_CIPB.Ciid,             --  勞就保識別碼
                                                            v_i_user,                --  UserID
                                                            v_tempSBMK,              --  Befort SIDMK Value
                                                            v_SBMK,                  --  SIDMK Value
                                                            v_CurBAAPP.Apno,         --  受理編號
                                                            v_CurBAAPP.Issuym,       --  核定年月
                                                            v_CurBAAPP.casetyp,      --  案件類別
                                                            v_systp,
                                                            v_result,
                                                            v_resultCode
                                                           );
                                             If v_result <> '1' Then
                                               v_upcode := 'N';
                                               sp_saveMsg( '0',
                                                           v_CurBAAPP.Apno,
                                                           v_CurBAAPP.Issuym,
                                                           v_CIPB.CIID,
                                                           v_intyp,
                                                           v_i_user,
                                                           v_resultCode
                                                         );
                                             Else
                                                 v_updBACIBMK := Substr(v_CIPB.Bmk,1,1)||NVL(v_SBMK,' ')||Substr(v_CIPB.Bmk,3,8);
                                                 Update CIPB
                                                    Set BMK = v_updBACIBMK
                                                  Where INTYP = v_CIPB.Intyp
                                                    And IDN   = v_CIPB.Idn
                                                    And APNO  = v_CurBAAPP.Apno
                                                    And SEQNO = v_CurBAAPP.Seqno;
                                             End if;
                                        Else
                                            v_upcode := 'A';
                                        End if;
                                   Else
                                       v_upcode := 'Z';
                                   End if;

                               /* 失能年金清除臨時及永久註記之條件：
                                  （1）「領取失能給付註記CIPB_API_SBMK1」」：於每月勞保失能年金月核定作業後，
                                        讀取該月案件類別為不給付及暫緩給付案，且無同給付別、同IDN、出生及姓名之續發案或結案者，
                                        將本欄之1-已受理、4-已核付（失能年金）清為空白，其他欄位值不予清除。
                               */
                               Elsif (v_payCode = 'K') Then
                                      v_upcode := 'G';
                                     If (v_CurBAAPP.APCOUNT = 0) And
                                        ((v_ciCIPB.SBmk1 = '1') Or (v_ciCIPB.SBmk1 = '4')) Then
                                          v_tempSBMK  := v_ciCIPB.SBmk1;
                                          v_SBMK := Null;
                                          If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                              sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                             v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                             v_CIPB.Ciid,             --  勞就保識別碼
                                                             v_i_user,                --  UserID
                                                             v_tempSBMK,              --  Befort SIDMK Value
                                                             v_SBMK,                  --  SIDMK Value
                                                             v_CurBAAPP.Apno,         --  受理編號
                                                             v_CurBAAPP.Issuym,       --  核定年月
                                                             v_CurBAAPP.casetyp,      --  案件類別
                                                             v_systp,
                                                             v_result,
                                                             v_resultCode
                                                            );
                                               If v_result <> '1' Then
                                                  v_upcode := 'N';
                                                  sp_saveMsg( '0',
                                                              v_CurBAAPP.Apno,
                                                              v_CurBAAPP.Issuym,
                                                              v_CIPB.CIID,
                                                              v_intyp,
                                                              v_i_user,
                                                              v_resultCode
                                                            );
                                               Else
                                                   -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                                   v_updBACIBMK := NVL(v_SBMK,' ') || Substr(v_CIPB.Bmk,2,9);
                                                   Update CIPB
                                                      Set BMK = v_updBACIBMK
                                                    Where INTYP = v_CIPB.Intyp
                                                      And IDN   = v_CIPB.Idn
                                                      And APNO  = v_CurBAAPP.Apno
                                                      And SEQNO = v_CurBAAPP.Seqno;
                                                   -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                               End if;
                                          else
                                              v_upcode := 'A';
                                          end if;
                                     Else
                                         v_upcode := 'Z';
                                     End if;
                               End if;

                               if v_upcode = 'G' Then
                                  v_countCase3G := v_countCase3G + 1;
                               elsif v_upcode = 'A' Then
                                  v_countCase3A := v_countCase3A + 1;
                               elsif v_upcode = 'Z' Then
                                  v_countCase3Z := v_countCase3Z + 1;
                               end if;

                            End if;    -----------------------註銷永久註記 end--------------------------------------

                            /*---------------------------------------------------------------------------------------
                                註銷管制加保註記
                                老年及失能年金不給付及暫緩給付案件：於完成清除前述「領取給付註記」之永久註記同時，
                                如老年、失能、死亡及失蹤之領取給付註記為空值或＜2，且「管制加保註記」之欄位值為Y時，
                                清為空白；未清除永久註記者，本欄位不須清為空白。
                              ---------------------------------------------------------------------------------------
                            */
                            If (nvl(v_i_uinmk, ' ')) = 'N' Then
                               If (NVL(v_ciCIPB.SBmk1,'1') < '2' ) And
                                  (NVL(v_ciCIPB.SBmk2,'1') < '2' ) And
                                  (NVL(v_ciCIPB.SBmk3,'1') < '2' ) And
                                  (NVL(v_ciCIPB.SBmk4,'1') < '2' ) And
                                  (NVL(v_ciCIPB.UINMK,' ') = 'Y') Then
                                     v_tempUINMK := v_ciCIPB.UINMK;
                                     v_UINMK := Null;
                                     If (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                                         sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                         v_CIPB.Ciid,             --  勞就保識別碼
                                                         v_i_user,                --  UserID
                                                         v_tempSBMK,              --  Befort SIDMK Value
                                                         v_UINMK,                 --  SIDMK Value
                                                         v_CurBAAPP.Apno,         --  受理編號
                                                         v_CurBAAPP.Issuym,       --  核定年月
                                                         v_CurBAAPP.casetyp,      --  案件類別
                                                         v_systp,
                                                         v_result,
                                                         v_resultCode
                                                        );
                                         If v_result <> '1' Then
                                            sp_saveMsg( '0',
                                                        v_CurBAAPP.Apno,
                                                        v_CurBAAPP.Issuym,
                                                        v_CIPB.CIID,
                                                        v_intyp,
                                                        v_i_user,
                                                        v_resultCode
                                                      );
                                         Else
                                           -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                            Update CIPB
                                               Set UINMK = v_UINMK
                                             Where INTYP = v_CIPB.Intyp
                                               And IDN   =  v_CIPB.Idn
                                               And APNO  = v_CurBAAPP.Apno
                                               And SEQNO = v_CurBAAPP.Seqno;
                                            -- 2012/05/18 新增同時同步BA CIPB UINMK 欄位
                                         End if;
                                      End if;
                               End if;
                            End if;    -----------------------註銷管制加保註記 end--------------------------------------

                            /*---------------------------------------------------------------------------------------
                                寫入逕退檔
                              ---------------------------------------------------------------------------------------
                            */
                            If (NVL(v_i_withdraw,' ') = 'Y') Then

                                v_countSUC := v_countSUC + 1;
                                If (v_count = 0) Then

                                    --2013/07/11 Modify By ChungYu 老年新案逕退改用最後單位
                                    If ((v_payCode = 'L') And (v_CurBAAPP.CASETYP = '1')) Then
                                        v_ubno := v_CurBAAPP.LSUBNO;
                                    Else
                                        v_ubno := v_CurBAAPP.APUBNO;
                                    End If;
                                    --2013/07/11 老年新案逕退改用最後單位

                                    Insert Into BAWITHDRAW (APNO, ISSUYM, CASETYP, INTYP, PAYKIND,
                                                            UBNO, EVTIDNNO, EVTBRDATE, EVTNAME, EVTJOBDATE,
                                                            APLPAYDATE, APPDATE, DEPT, VMK, UPCODE,
                                                            PBSTAT, PBSTAT2, PBUINMK, PBUINMK2, TXCD2,
                                                            SPCH, CIID, NDOMK, HCODE,  PROCTIME)
                                                    VALUES (v_CurBAAPP.APNO, v_CurBAAPP.Issuym, v_CurBAAPP.CASETYP, v_CIPB.Intyp, v_CurBAAPP.PAYKIND,
                                                            v_ubno, v_CurBAAPP.EVTIDNO, v_CurBAAPP.EVTBRDATE, v_CurBAAPP.EVTNAME, v_CurBAAPP.EVTJOBDATE,
                                                            v_CurBAAPP.APLPAYDATE, v_CurBAAPP.APPDATE, '', Decode(v_intyp,'V','V',Null),v_upcode,
                                                            v_tempSBMK, v_SBMK,v_tempUINMK, v_UINMK,'',
                                                            '', v_CIPB.CIID, v_CurBAAPP.NOTEMK, '', to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                            );
                                Else
                                    Update BAWITHDRAW Set APLPAYDATE = v_CurBAAPP.APLPAYDATE,
                                                          UPCODE     = v_upcode,
                                                          PBSTAT     = v_tempSBMK,
                                                          PBSTAT2    = v_SBMK,
                                                          PBUINMK    = v_tempUINMK,
                                                          PBUINMK2   = v_UINMK,
                                                          NDOMK      = v_CurBAAPP.NOTEMK,
                                                          PROCTIME   = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                    Where APNO = v_CurBAAPP.APNO;
                                End If;
                               /* 老年年金：
                                  1、於每月勞保老年年金月核定作業後，讀取該月核付，且給付主檔之申請單位保險證號非空白之新案，
                                     以事故日期為退保日期產生逕退檔。
                                  2、於每月勞保老年年金月核定作業後，讀取該月不給付及暫緩給付案，當給付主檔之申請單位保險證
                                     號非空白，且行政支援記錄檔中函別21之處理註記(一)及處理註記（二）非55者，以事故日期為退
                                     保日期產生逕退檔。
                               */
                                If ( v_payCode = 'L' ) Then
                                     If ((v_CurBAAPP.CASETYP = '1') And (Trim(v_CurBAAPP.LSUBNO) Is Not Null)) Or
                                          (((v_CurBAAPP.CASETYP = '3') Or (v_CurBAAPP.CASETYP = '6')) And (NVL(v_CurBAAPP.NOTEMK,' ') <> 'Y'))  Then
                                             v_countToDRAW := v_countToDRAW + 1;

                                             --  2013/07/10 月核逕退的新案核付案件用最後單位來逕退,不給付案則為申請單位
                                                 v_ubno := '';
                                                 If (v_CurBAAPP.CASETYP = '1') Then
                                                     v_ubno := v_CurBAAPP.LSUBNO;
                                                 Else
                                                     v_ubno := v_CurBAAPP.APUBNO;
                                                 End If;
                                             --  2013/07/10

                                             CI.CI040M020( v_intyp,                        --  保險別    L、V、F
                                                           v_CurBAAPP.PAYKIND,             --  核付種類  45 or 35 ...
                                                           v_CurBAAPP.APLPAYDATE,          --  核付日期
                                                           v_ubno,                         --  保險證號
                                                           SUBSTR(v_CurBAAPP.EVTIDNO,1,10),--  身分證號   2012/10/04 ChungYu Modify
                                                           v_CurBAAPP.EVTBRDATE,           --  出生日期
                                                           v_CurBAAPP.EVTNAME,             --  姓名
                                                           v_CurBAAPP.APNO,                --  受理號碼
                                                           v_CurBAAPP.EVTJOBDATE,          --  事故日期
                                                           v_CurBAAPP.APPDATE,             --  受理日期
                                                           to_Char(Sysdate,'YYYYMMDD'),    --  處理日期
                                                           to_Char(Sysdate,'HH24MISS'),    --  處理時間
                                                           '',                             --  工作部門
                                                           '',                             --  輕重殘註記   only K = '1'
                                                           '',                             --  育嬰續保起日
                                                           '',                             --  育嬰續保迄日
                                                           '',                             --  育嬰續保申請日
                                                           v_result,
                                                           v_resultCode);
                                     End if;
                                /* 失能年金：
                                   於每月勞保失能年金月核定作業後，讀取該月核付之新案，以事故日期為退保日期產生逕退檔。
                                */
                                Elsif ( v_payCode = 'K' ) Then
                                     If (v_CurBAAPP.CASETYP = '1') Then
                                             v_countToDRAW := v_countToDRAW + 1;
                                             CI.CI040M020( v_intyp,                        --  保險別    L、V、F
                                                           v_CurBAAPP.PAYKIND,             --  核付種類  45 or 35 ...
                                                           v_CurBAAPP.APLPAYDATE,          --  核付日期
                                                           v_CurBAAPP.APUBNO,              --  申請保險證號
                                                           SUBSTR(v_CurBAAPP.EVTIDNO,1,10),--  身分證號   2012/10/04 ChungYu Modify
                                                           v_CurBAAPP.EVTBRDATE,           --  出生日期
                                                           v_CurBAAPP.EVTNAME,             --  姓名
                                                           v_CurBAAPP.APNO,                --  受理號碼
                                                           v_CurBAAPP.EVTJOBDATE,          --  事故日期
                                                           v_CurBAAPP.APPDATE,             --  受理日期
                                                           to_Char(Sysdate,'YYYYMMDD'),    --  處理日期
                                                           to_Char(Sysdate,'HH24MISS'),    --  處理時間
                                                           '',                             --  工作部門
                                                           '1',                            --  輕重殘註記   only K = '1'
                                                           '',                             --  育嬰續保起日
                                                           '',                             --  育嬰續保迄日
                                                           '',                             --  育嬰續保申請日
                                                           v_result,
                                                           v_resultCode);
                                     End if;
                                /* 遺屬年金：
                                   於每月勞保遺屬年金月核定作業後，讀取該月核付之新案、不給付及暫緩給付案，以事故日期為退保日期產生逕退檔。
                                */
                                Elsif ( v_payCode = 'S' ) Then
                                            v_countToDRAW := v_countToDRAW + 1;
                                            CI.CI040M020( v_intyp,                        --  保險別    L、V、F
                                                          v_CurBAAPP.PAYKIND,             --  核付種類  45 or 35 ...
                                                          v_CurBAAPP.APLPAYDATE,          --  核付日期
                                                          v_CurBAAPP.APUBNO,              --  申請保險證號
                                                          SUBSTR(v_CurBAAPP.EVTIDNO,1,10),--  身分證號   2012/10/04 ChungYu Modify
                                                          v_CurBAAPP.EVTBRDATE,           --  出生日期
                                                          v_CurBAAPP.EVTNAME,             --  姓名
                                                          v_CurBAAPP.APNO,                --  受理號碼
                                                          v_CurBAAPP.EVTJOBDATE,          --  事故日期
                                                          v_CurBAAPP.APPDATE,             --  受理日期
                                                          to_Char(Sysdate,'YYYYMMDD'),    --  處理日期
                                                          to_Char(Sysdate,'HH24MISS'),    --  處理時間
                                                          '',                             --  工作部門
                                                          '',                             --  輕重殘註記   only K = '1'
                                                          '',                             --  育嬰續保起日
                                                          '',                             --  育嬰續保迄日
                                                          '',                             --  育嬰續保申請日
                                                          v_result,
                                                          v_resultCode);
                                End if;
                                -- 更新逕退檔
                                IF NVL(v_result,'1') = '1' Then
                                   v_countToDRAWSUC := v_countToDRAWSUC + 1;
                                     Update BAWITHDRAW
                                        Set TXCD2 = 'Y'
                                      Where APNO = v_CurBAAPP.APNO;
                                Else
                                   v_countToDRAWErr := v_countToDRAWErr + 1;
                                End if;
                            --Else
                            --   v_countErr := v_countErr + 1;
                            End if;-----------------------寫入逕退檔 end--------------------------------------
                         End If;
                     Else
                       v_countDupIdn := v_countDupIdn + 1;
                     End If;
                     SP_BA_RECJOBLOG (p_job_no => v_jobno,
                                      p_job_id => 'sp_BA_singleUpdate',
                                      p_step   => '單筆更新被保險人永久註記及管制加保註記與逕退',
                                      p_memo   => '結束時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')'||CHR(10)||
                                                  ' 總筆數                 = (' || v_countALL ||')'||CHR(10)||
                                                  ' 受理編號存在逕退檔筆數 = (' || v_countDRAW ||')'||CHR(10)||
                                                  ' 處理總筆數             = (' || v_countPOC ||')'||CHR(10)||
                                                  ' 更新領取註記成功筆數   = (' || v_countSUC ||')'||CHR(10)||
                                                  ' 給付案G總筆數          = (' || v_countCase1G ||')'||CHR(10)||
                                                  ' 不給付G案總筆數        = (' || v_countCase3G ||')'||CHR(10)||
                                                  ' 給付案A總筆數          = (' || v_countCase1A ||')'||CHR(10)||
                                                  ' 不給付A案總筆數        = (' || v_countCase3A ||')'||CHR(10)||
                                                  ' 給付案Z總筆數          = (' || v_countCase1Z ||')'||CHR(10)||
                                                  ' 不給付Z案總筆數        = (' || v_countCase3Z ||')'||CHR(10)||
                                                  ' 更新領取註記失敗筆數   = (' || v_countErr ||')'||CHR(10)||
                                                  ' 逕退總筆數             = (' || v_countToDRAW ||')'||CHR(10)||
                                                  ' 逕退成功筆數           = (' || v_countToDRAWSUC ||')'||CHR(10)||
                                                  ' 逕退失敗筆數           = (' || v_countToDRAWErr ||')'||CHR(10)||
                                                  ' 查無事故者 BA.CIPB 資料= (' || v_countBI ||')'||CHR(10)||
                                                  ' 未經身分證重號檔選擇   = (' || v_countDupIdn ||')'||CHR(10)
                                                  );
                  Exception
                    when NO_DATA_FOUND then
                         sp_saveMsg( '0',
                                     v_CurBAAPP.Apno,
                                     '',
                                     '',
                                     v_intyp,
                                     v_i_user,
                                     '查無事故者 BA.CIPB 資料'
                                   );
                         v_countBI := v_countBI + 1;
                  End;

                 End If;
             -- dbms_output.put_line(' PKG_BA_updCIStatus-sp_BA_singleUpdate----------->>[ Insert Error Log ]');
             /*End If;
             */
         End Loop;
         sp_saveMsg( '1','','','','',v_i_user,' 總筆數                 = ' || v_countALL);
         sp_saveMsg( '1','','','','',v_i_user,' 受理編號存在逕退檔筆數 = ' || v_countDRAW);
         sp_saveMsg( '1','','','','',v_i_user,' 處理總筆數             = ' || v_countPOC);
         sp_saveMsg( '1','','','','',v_i_user,' 更新領取註記成功筆數   = ' || v_countSUC);
         sp_saveMsg( '1','','','','',v_i_user,' 給付案G總筆數          = ' || v_countCase1G);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付G案總筆數        = ' || v_countCase3G);
         sp_saveMsg( '1','','','','',v_i_user,' 給付案A總筆數          = ' || v_countCase1A);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付A案總筆數        = ' || v_countCase3A);
         sp_saveMsg( '1','','','','',v_i_user,' 給付案Z總筆數          = ' || v_countCase1Z);
         sp_saveMsg( '1','','','','',v_i_user,' 不給付Z案總筆數        = ' || v_countCase3Z);
         sp_saveMsg( '1','','','','',v_i_user,' 更新領取註記失敗筆數   = ' || v_countErr);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退總筆數             = ' || v_countToDRAW);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退成功筆數           = ' || v_countToDRAWSUC);
         sp_saveMsg( '1','','','','',v_i_user,' 逕退失敗筆數           = ' || v_countToDRAWErr);
         sp_saveMsg( '1','','','','',v_i_user,' 查無事故者 BA.CIPB 資料= ' || v_countBI);
         sp_saveMsg( '1','','','','',v_i_user,' 未經身分證重號檔選擇   = ' || v_countDupIdn);
         Commit;

         dbms_output.put_line(' 總筆數                 = ' || v_countALL);
         dbms_output.put_line(' 受理編號存在逕退檔筆數 = ' || v_countDRAW);
         dbms_output.put_line(' 處理總筆數             = ' || v_countPOC);
         dbms_output.put_line(' 更新領取註記成功筆數   = ' || v_countSUC);
         dbms_output.put_line(' 給付案G總筆數          = ' || v_countCase1G);
         dbms_output.put_line(' 不給付G案總筆數        = ' || v_countCase3G);
         dbms_output.put_line(' 給付案A總筆數          = ' || v_countCase1A);
         dbms_output.put_line(' 不給付A案總筆數        = ' || v_countCase3A);
         dbms_output.put_line(' 給付案Z總筆數          = ' || v_countCase1Z);
         dbms_output.put_line(' 不給付Z案總筆數        = ' || v_countCase3Z);
         dbms_output.put_line(' 更新領取註記失敗筆數   = ' || v_countErr);
         dbms_output.put_line(' 逕退總筆數             = ' || v_countToDRAW);
         dbms_output.put_line(' 逕退成功筆數           = ' || v_countToDRAWSUC);
         dbms_output.put_line(' 逕退失敗筆數           = ' || v_countToDRAWErr);
         dbms_output.put_line(' 查無事故者 BA.CIPB 資料= ' || v_countBI);
         dbms_output.put_line(' 未經身分證重號檔選擇   = ' || v_countDupIdn);
     Else
         dbms_output.put_line(' PKG_BA_updCIStatus-sp_BA_singleUpdate----------->>[ 核定年月或使用者帳號不可為空白 ]');
     End If;
    End;
    --Procedure sp_BA_singleUpdate

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_updCIStatus.sp_BA_singleUpdatBatch
        PURPOSE:         批次更新被保險人[永久註記(SBMK)]及[管制加保註記(UINMK)]與逕退。

        PARAMETER(IN):

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2014/05/09  Justin Yu    Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_singleUpdateBatch  is
       v_count   NUMBER    := 0;
       Cursor c_dataCur_BAAPP is
          SELECT Distinct T.APNO FROM BAAPPBASE T
           WHERE T.APNO IN  ('K00000002165');
                           /*(select distinct a.apno
                             from ba.baappbase a , ci.cipb b
                             where a.APNO in (select apno from ba.BAUPDCIDBLOG
                                              where casetyp = '3'
                                              and colname = 'UINMK'
                                              and systp = 'BaOADay')
                             and b.ftyp = 'L'
                             and b.idn like a.evtidnno || '%'
                             and a.evtbrdate = b.brdte
                             and a.seqno = '0000'
                             and b.sbmk2 in ('2','3')
                             and trim(uinmk) is null);--補上管制加保註記被誤刪之SQL
                             */
                            /*(SELECT APNO FROM
                                 (SELECT APNO FROM BAWITHDRAW
                                 WHERE APNO LIKE 'L%'
                                 AND TXCD2 = 'Y'
                                 AND ISSUYM = '201509'
                                 AND CASETYP = '1')
                                 MINUS
                                 (SELECT APNO FROM CIBCSR)
                             );*/

       Begin
         for v_CurBAAPP in c_dataCur_BAAPP Loop
             v_count := v_count + 1;
             sp_BA_singleUpdate(v_CurBAAPP.APNO,'','N','','70348');
             --dbms_output.put_line(v_count || ' APNO = ' || v_CurBAAPP.APNO);
         End Loop;
    End sp_BA_singleUpdateBatch;
    --Procedure sp_BA_singleUpdateBatch

  Procedure sp_saveMsg(v_i_msgtyp    IN  VARCHAR2,
                       v_i_apno      IN  VARCHAR2,
                       v_i_issuym    IN  VARCHAR2,
                       v_i_ciid      IN  VARCHAR2,
                       v_i_intyp     IN  VARCHAR2,
                       v_i_userid    IN  VARCHAR2,
                       v_i_message   IN  VARCHAR2)
  IS
    PRAGMA AUTONOMOUS_TRANSACTION; --獨立的TRANSACTION不受呼叫端影響
  BEGIN

     Insert Into BAUPDCIDBMSG ( MSGTYP, APNO, ISSUYM, CIID, INTYP, USERID, Message, PROCDATE)
                       Values ( v_i_msgtyp,
                                v_i_apno,
                                v_i_issuym,
                                v_i_ciid,
                                v_i_intyp,
                                v_i_userid,
                                v_i_message,
                                to_Char(Sysdate,'YYYYMMDDHH24MISS')
                              );
      COMMIT;

  EXCEPTION
      WHEN OTHERS THEN
        NULL;
  END sp_saveMsg;

End;
/
