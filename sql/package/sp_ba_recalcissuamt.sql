create or replace procedure ba.SP_BA_reCalcIssuamt
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            SP_BA_reCalcIssuamt
    PURPOSE:         產生老年年金基本金額調整，核定金額異動案件資料

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2012/06/16  ChungYu Wu    Created this procedure.

    NOTES:
    1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

********************************************************************************/
authid definer is
--    v_baappabse               BA.BAAPPBASE%ROWTYPE;  -- 勞保年金主檔資料
    v_badapr                  BA.BADAPR%ROWTYPE;       -- 勞保年金核定檔資料
    v_newOldAAmt              Number(6);               -- 重新計算的A式金額
    v_newOldBAmt              Number(6);               -- 重新計算的B式金額
    v_tmpIssuAmt              Number(6);               -- 暫存核定金額
    v_newIssuAmt              Number(6);               -- 新的核定金額
    v_oldAB                   Varchar(1);              -- 重計第一式/第二式
    v_count                   Number(6);               -- 總筆數
    v_countSuc                Number(6);               -- 成功筆數
    v_countErr                Number(6);               -- 錯誤筆數

    --查詢有核發過 2012/01 ~ 2012/06 給付月份的老年年金案件主檔資料
    Cursor c_dataCur is
        Select A.*
          From BAAPPBASE A
         Where A.Apno Like 'L%'
      --     And A.SEQNO = '0000'
           And A.Casetyp In ('2', '4')
           And A.Benevtrel Not In ('F','Z')
           And A.Apno In (Select A1.APNO
                            From BADAPR A1
                           Where A1.Apno Like 'L%'
                             And A1.MTESTMK = 'F'
                             And A1.PAYYM >= '201201'
                             And A1.PAYYM <= '201206'
                             And A1.APLPAYMK = '3'
                             And A1.APLPAYDATE Is Not Null
                             And A1.Apno not in (Select A2.APNO
                                                   From BADAPR A2
                                                   Where A2.APNO = A1.APNO
                                                     And A2.MTESTMK = 'F'
                                                     And A2.PAYKIND = '49'
                                                     And A2.APLPAYMK = '3'
                                                     And A2.APLPAYDATE Is Not Null))
         order by A.Apno, A.SEQNO;

   /* --查詢傳入受理編號最大給付年月的核定檔資料
    Cursor c_dataCurDetail(temAPNO VARCHAR2) is
        Select A.*
          From BADAPR A
         Where A.Apno = temAPNO
           And A.SEQNO = '0000'
           And A.PAYYM = (Select Max(A1.PAYYM)
                            From BADAPR A1
                           Where A1.Apno = temAPNO
                             And A1.MTESTMK = 'F'
                             And A1.PAYYM >= '201201'
                             And A1.PAYYM <= '201206'
                             And A1.APLPAYMK = '3'
                             And A1.APLPAYDATE Is Not Null)
           And Rownum = 1;*/

Begin
     --清除勞保年金基本金額異動案件紀錄檔(BABASICCHG)資料
     Delete From BABASICCHG;
     v_count    := 0;
     v_countSuc := 0;
     v_countErr := 0;

     --查詢有核發過 2012/01 ~ 2012/06 給付月份的老年年金案件主檔資料
     For v_dataCur in c_dataCur Loop
         v_newOldAAmt := 0;
         v_newOldBAmt := 0;
         v_tmpIssuAmt := 0;
         v_newIssuAmt := 0;
         v_oldAB      := null;

         v_count := v_count + 1 ;
         --讀取受理編號最大給付年月的核定檔資料
         Select A.* into v_badapr
           From BADAPR A
          Where A.Apno = v_dataCur.Apno
            And A.SEQNO = '0000'
            And A.PAYYM = (Select Max(A1.PAYYM)
                             From BADAPR A1
                            Where A1.Apno = v_dataCur.Apno
                              And A1.MTESTMK = 'F'
                              And A1.PAYYM >= '201201'
                              And A1.PAYYM <= '201206'
                              And A1.APLPAYMK = '3'
                              And A1.APLPAYDATE Is Not Null)
            And Rownum = 1;

         -- 重新計算老年A式金額
         v_newOldAAmt := round(((v_badapr.aplpayseniy+round(v_badapr.aplpaysenim/12, 2))*v_badapr.INSAVGAMT*0.00775)+3500);

         -- 重新計算老年B式金額
         v_newOldBAmt := round((v_badapr.aplpayseniy+round(v_badapr.aplpaysenim/12, 2))*v_badapr.INSAVGAMT*0.0155);

         -- A > B 取 A ， B > A 取 B
         If v_newOldAAmt > v_newOldBAmt Then
            v_tmpIssuAmt := v_newOldAAmt;
            v_oldAB := '1';
         Else
            v_tmpIssuAmt := v_newOldBAmt;
            v_oldAB := '2';
         End If;

         -- 重新計算核定金額

         v_newIssuAmt := round(v_tmpIssuAmt * ( 1 + (v_badapr.oldrate / 100) ));

         -- 本次重新計算核定金額與最大給付年月的核定金額不同，則將資料新增至勞保年金基本金額異動案件紀錄檔(BABASICCHG)
         If (v_newIssuAmt <> v_badapr.befissueamt ) Then
           Begin
             v_countSuc := v_countSuc + 1;

             --  事故者
             If v_dataCur.Seqno = '0000'  Then
                INSERT INTO BABASICCHG ( APNO, APITEM, CASETYP, PROCSTAT, EVTAPPIDNNO,
                                         EVTNAME, EVTBRDATE, EVTSEX, EVTDIEDATE, LSCHKMK,
                                         BENIDNNO, BENNAME, BENBRDATE, BENSEX, BENEVTREL,
                                         COMMZIP, COMMADDR, PAYTYP, CUTAMT, ISSUYM,
                                         PAYYM, ISSUEAMT, REISSUEAMT, PAYYMS, NOTIFYFORM,
                                         LOANMK, SEQNO, INSAVGAMT, APLPAYSENIY, APLPAYSENIM,
                                         OLDAB, REOLDAB, OLDAAMT, REOLDAAMT, OLDBAMT,
                                         REOLDBAMT, OLDRATE, OLDRATESDATE, OLDRATEEDATE, COMPENAMT,
                                         OFFSETAMT, PROCTIME)
                                VALUES
                                       ( v_dataCur.Apno, v_dataCur.Apitem, v_dataCur.Casetyp, v_dataCur.Procstat, v_dataCur.Evtidnno,
                                         v_dataCur.Evtname, v_dataCur.Evtbrdate, v_dataCur.Evtsex, v_dataCur.Evtdiedate, v_dataCur.Lschkmk,
                                         v_dataCur.Benidnno, v_dataCur.Benname, v_dataCur.Benbrdate, v_dataCur.Bensex, v_dataCur.Benevtrel,
                                         v_dataCur.Commzip, v_dataCur.Commaddr, v_dataCur.Paytyp, v_dataCur.Cutamt, v_badapr.Issuym,
                                         v_badapr.Payym, v_badapr.befissueamt, v_newIssuAmt, v_dataCur.Payyms, v_dataCur.Notifyform,
                                         v_dataCur.Loanmk, v_dataCur.seqno, v_badapr.insavgamt, v_badapr.aplpayseniy, v_badapr.aplpaysenim,
                                         v_badapr.oldab, v_oldAB, v_badapr.oldaamt, v_newOldAAmt, v_badapr.oldbamt,
                                         v_newOldBAmt, v_badapr.oldrate, v_badapr.oldratesdate, v_badapr.oldrateedate, v_badapr.compenamt,
                                         v_badapr.offsetamt, to_Char(Sysdate,'YYYYMMDDHH24MISS'));
             Else   --  受益人
                INSERT INTO BABASICCHG ( APNO, APITEM, CASETYP, PROCSTAT, EVTAPPIDNNO,
                                         EVTNAME, EVTBRDATE, EVTSEX, EVTDIEDATE, LSCHKMK,
                                         BENIDNNO, BENNAME, BENBRDATE, BENSEX, BENEVTREL,
                                         COMMZIP, COMMADDR, PAYTYP, CUTAMT, ISSUYM,
                                         PAYYM, ISSUEAMT, REISSUEAMT, PAYYMS, NOTIFYFORM,
                                         LOANMK, SEQNO, INSAVGAMT, APLPAYSENIY, APLPAYSENIM,
                                         OLDAB, REOLDAB, OLDAAMT, REOLDAAMT, OLDBAMT,
                                         REOLDBAMT, OLDRATE, OLDRATESDATE, OLDRATEEDATE, COMPENAMT,
                                         OFFSETAMT, PROCTIME)
                                VALUES
                                       ( v_dataCur.Apno, v_dataCur.Apitem, v_dataCur.Casetyp, v_dataCur.Procstat, v_dataCur.Evtidnno,
                                         v_dataCur.Evtname, v_dataCur.Evtbrdate, v_dataCur.Evtsex, v_dataCur.Evtdiedate, Null,
                                         v_dataCur.Benidnno, v_dataCur.Benname, v_dataCur.Benbrdate, v_dataCur.Bensex, v_dataCur.Benevtrel,
                                         v_dataCur.Commzip, v_dataCur.Commaddr, v_dataCur.Paytyp, Null, Null,
                                         Null, Null, Null, Null, Null,
                                         Null, v_dataCur.seqno, Null, Null, Null,
                                         Null, Null, Null, Null, Null,
                                         Null, Null, Null, Null, Null,
                                         Null, to_Char(Sysdate,'YYYYMMDDHH24MISS'));
             End if;
           Exception
                When others then
                     v_countErr := v_countErr + 1;
                     dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
                End;
         End if;
     End Loop;

     Commit;

     dbms_output.put_line('              總筆數 ： ' || v_count);
     dbms_output.put_line('  核定金額不一致筆數 ： ' || v_countSuc);
     dbms_output.put_line('            錯誤筆數 ： ' || v_countErr);

End SP_BA_reCalcIssuamt;
/

