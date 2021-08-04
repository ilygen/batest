CREATE OR REPLACE Procedure BA.sp_BA_updDisableCase

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            sp_BA_updDisableCase
        PURPOSE:         將失能年金四月、五月已核付案件更新，更新被保險人[永久註記]及[管制加保註記(UINMK)]。

        PARAMETER(IN):   *v_i_user          (varChar2)       -- 使用者帳號

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/06/19  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/

(
    v_i_user               in      varChar2
)
authid definer Is

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
      v_issuym                 VARCHAR2(6)             := '';         --核定年月


      --核定年月當月所核付的新案、不給付案、暫緩給付案清單
      CURSOR c_dataCur_BAAPP IS
        Select A.APNO,                                             -- 受理編號
               '0000' AS SEQNO,                                    -- 序號 2014/04/08 Add By ChungYu 回押 Ba.CIPB 要增加受理編號+序號
               A.APPDATE,                                          -- 受理日期
               A.PAYKIND,                                          -- 給付種類
               Decode(D.IDNNO,Null,A.EvtIdnno,D.IDNNO) As EVTIDNO, -- 事故者身分證號
               A.EVTBRDATE,                                        -- 事故者生日
               A.EVTNAME,                                          -- 事故者姓名
               A.EVTJOBDATE,                                       -- 事故日期
               A.APUBNO,                                           -- 申請單位保險證號
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
                 Where A1.APNO in (Select Distinct A2.Apno From BADISABLECASE A2
                                    Where Trim(A2.PROCMK) is null
                                  )
                   And A1.SEQNO = '0000') A,
               (Select APNO, SEQNO, ADWKMK From BAAPPEXPAND Where SEQNO = '0000') B,
               (Select C1.Apno, '1' As "MCHKTYP" , C1.APLPAYDATE
                  From BADISABLECASE C1
                 Where Trim(C1.PROCMK) is null
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

Begin
     If Trim(v_i_user) Is Not Null Then

         For v_CurBAAPP In c_dataCur_BAAPP Loop

             v_countALL:= v_countALL + 1;
             v_evtidn  := v_CurBAAPP.Evtidno;
             v_payCode := Substr(v_CurBAAPP.Apno,1,1);
             v_issuym  := Substr(v_CurBAAPP.Appdate,1,6);

             SELECT COUNT(*) INTO v_count FROM BAWITHDRAW
              WHERE apno = v_CurBAAPP.Apno;

              If v_count > 0 Then
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

                         If v_CIPB.Ciid Is Not Null Then
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
                                      v_tempSBMK  := Substr(v_CIPB.Bmk,2,1);
                                      v_tempUINMK := v_CIPB.Uinmk;
                                  Elsif (v_payCode = 'S') Then
                                      v_tempSBMK :=  Substr(v_CIPB.Bmk,3,1);
                                      v_tempUINMK := v_CIPB.Uinmk;
                                  End If;

                                  v_SBMK := 3;
                                  v_UINMK := 'Y';
                                  v_upcode := 'G';       -- G：符合更新已領給付條件更新成功

                                  If (NVL(v_tempSBMK,' ') = NVL(v_SBMK,' ')) then
                                      v_upcode := 'A';   -- A：現存已領給付註記與欲更新相同
                                  Else
                                     if (v_tempSBMK = '1') Or (trim(v_tempSBMK) Is Null) Then

                                         PKG_BA_updCIStatus.sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                                           v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                                           v_CIPB.Ciid,             --  勞就保識別碼
                                                                           v_i_user,                --  UserID
                                                                           v_tempSBMK,              --  Befort SIDMK Value
                                                                           v_SBMK,                  --  SIDMK Value
                                                                           v_CurBAAPP.Apno,         --  受理編號
                                                                           v_issuym,                --  核定年月
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
                                                And IDN   =  v_CIPB.Idn
                                                And APNO = v_CurBAAPP.APNO
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
                                     PKG_BA_updCIStatus.sp_saveMsg( '0',
                                                                    v_CurBAAPP.Apno,
                                                                    v_issuym,
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
                                        PKG_BA_updCIStatus.sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                                           v_CIPB.Ciid,             --  勞就保識別碼
                                                                           v_i_user,                --  UserID
                                                                           v_tempUINMK,             --  Befort UINMK Value
                                                                           v_UINMK,                 --  UINMK Value
                                                                           v_CurBAAPP.Apno,         --  受理編號
                                                                           v_issuym,              --  核定年月
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
                                                And IDN   =  v_CIPB.Idn
                                                And APNO = v_CurBAAPP.APNO
                                                And SEQNO = v_CurBAAPP.SEQNO;

                                         End if;
                                         -- 2012/05/18 新增同時同步BA CIPB BMK 欄位

                                  End if;
                                  -- 更新失敗寫LOGMsg
                                  if v_result <> '1' Then
                                     PKG_BA_updCIStatus.sp_saveMsg( '0',
                                                                    v_CurBAAPP.Apno,
                                                                    v_issuym,
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
                                  v_tempSBMK  := Substr(v_CIPB.Bmk,1,1);
                                  v_tempUINMK := v_CIPB.Uinmk;
                                  v_SBMK := 4;
                                  v_UINMK := 'Y';
                                  v_upcode := 'G';        -- G：符合更新已領給付條件更新成功
                                  If (NVL(v_tempSBMK,' ') = NVL(v_SBMK,' ')) then
                                      v_upcode := 'A';    -- A：現存已領給付註記與欲更新相同
                                  Else
                                      if (v_tempSBMK = '1') Or (trim(v_tempSBMK) Is Null) Then
                                          PKG_BA_updCIStatus.sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                                            v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                                            v_CIPB.Ciid,             --  勞就保識別碼
                                                                            v_i_user,                --  UserID
                                                                            v_tempSBMK,              --  Befort SIDMK Value
                                                                            v_SBMK,                  --  SIDMK Value
                                                                            v_CurBAAPP.Apno,         --  受理編號
                                                                            v_issuym,              --  核定年月
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
                                                And IDN   =  v_CIPB.Idn
                                                And APNO = v_CurBAAPP.APNO
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
                                     PKG_BA_updCIStatus.sp_saveMsg( '0',
                                                                    v_CurBAAPP.Apno,
                                                                    v_issuym,
                                                                    v_CIPB.CIID,
                                                                    v_intyp,
                                                                    v_i_user,
                                                                    v_resultCode
                                                                  );
                                  End if;
                                  --  若更新SBMK 成功，繼續更新UNIMK
                                  if (v_result = '1') And (v_upcode = 'G') And
                                     (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                                          PKG_BA_updCIStatus.sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                                             v_CIPB.Ciid,             --  勞就保識別碼
                                                                             v_i_user,                --  UserID
                                                                             v_tempUINMK,             --  Befort UINMK Value
                                                                             v_UINMK,                 --  UINMK Value
                                                                             v_CurBAAPP.Apno,         --  受理編號
                                                                             v_issuym,              --  核定年月
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
                                                 And IDN   =  v_CIPB.Idn
                                                 And APNO = v_CurBAAPP.APNO
                                                 And SEQNO = v_CurBAAPP.SEQNO;      

                                          End if;
                                          -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                  End if;
                                  -- 更新失敗寫LOG
                                  if v_result <> '1' Then
                                     PKG_BA_updCIStatus.sp_saveMsg( '0',
                                                                    v_CurBAAPP.Apno,
                                                                    v_issuym,
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
                                   If (v_CurBAAPP.APCOUNT = 0) And ((Substr(v_CIPB.Bmk,3,1) = '1') Or
                                      (Substr(v_CIPB.Bmk,3,1) = '3') Or (Trim(Substr(v_CIPB.Bmk,3,1)) Is Null)) Then
                                        v_tempSBMK  := Substr(v_CIPB.Bmk,3,1);
                                        v_SBMK := 9;
                                        If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                            PKG_BA_updCIStatus.sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                                              v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                                              v_CIPB.Ciid,             --  勞就保識別碼
                                                                              v_i_user,                --  UserID
                                                                              v_tempSBMK,              --  Befort SIDMK Value
                                                                              v_SBMK,                  --  SIDMK Value
                                                                              v_CurBAAPP.Apno,         --  受理編號
                                                                              v_issuym,                --  核定年月
                                                                              v_CurBAAPP.casetyp,      --  案件類別
                                                                              v_systp,
                                                                              v_result,
                                                                              v_resultCode
                                                                             );
                                            If v_result <> '1' Then
                                               v_upcode := 'N';
                                               PKG_BA_updCIStatus.sp_saveMsg( '0',
                                                                              v_CurBAAPP.Apno,
                                                                              v_issuym,
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
                                                    And IDN   =  v_CIPB.Idn
                                                    And APNO = v_CurBAAPP.APNO
                                                    And SEQNO = v_CurBAAPP.SEQNO;
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
                                     If (v_CurBAAPP.APCOUNT = 0) And ((Substr(v_CIPB.Bmk,2,1) = '1') Or
                                        (Substr(v_CIPB.Bmk,2,1) = '3')) Then
                                         v_tempSBMK  := Substr(v_CIPB.Bmk,2,1);
                                         v_SBMK := Null;
                                         If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                             PKG_BA_updCIStatus.sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                                               v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                                               v_CIPB.Ciid,             --  勞就保識別碼
                                                                               v_i_user,                --  UserID
                                                                               v_tempSBMK,              --  Befort SIDMK Value
                                                                               v_SBMK,                  --  SIDMK Value
                                                                               v_CurBAAPP.Apno,         --  受理編號
                                                                               v_issuym,                --  核定年月
                                                                               v_CurBAAPP.casetyp,      --  案件類別
                                                                               v_systp,
                                                                               v_result,
                                                                               v_resultCode
                                                                              );
                                             If v_result <> '1' Then
                                               v_upcode := 'N';
                                               PKG_BA_updCIStatus.sp_saveMsg( '0',
                                                                              v_CurBAAPP.Apno,
                                                                              v_issuym,
                                                                              v_CIPB.CIID,
                                                                              v_intyp,
                                                                              v_i_user,
                                                                              v_resultCode
                                                                            );
                                             Else
                                                 v_updBACIBMK := Substr(v_CIPB.Bmk,1,1)||v_SBMK||Substr(v_CIPB.Bmk,3,8);
                                                 Update CIPB
                                                    Set BMK = v_updBACIBMK
                                                  Where INTYP = v_CIPB.Intyp
                                                    And IDN   =  v_CIPB.Idn
                                                    And APNO = v_CurBAAPP.APNO
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
                                      If (NVL(Substr(v_CIPB.Bmk,1,1),'1') < 2 ) And
                                         (NVL(Substr(v_CIPB.Bmk,2,1),'1') < 2 ) And
                                         (NVL(Substr(v_CIPB.Bmk,3,1),'1') < 2 ) And
                                         (NVL(Substr(v_CIPB.Bmk,4,1),'1') < 2 ) And
                                         (NVL(v_CIPB.UINMK,' ') = 'Y') Then
                                            v_tempUINMK := v_CIPB.UINMK;
                                            v_UINMK := Null;
                                            If (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                                                PKG_BA_updCIStatus.sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                                                   v_CIPB.Ciid,             --  勞就保識別碼
                                                                                   v_i_user,                --  UserID
                                                                                   v_tempSBMK,              --  Befort SIDMK Value
                                                                                   v_UINMK,                 --  SIDMK Value
                                                                                   v_CurBAAPP.Apno,         --  受理編號
                                                                                  v_issuym,              --  核定年月
                                                                                   v_CurBAAPP.casetyp,      --  案件類別
                                                                                   v_systp,
                                                                                   v_result,
                                                                                   v_resultCode
                                                                                  );
                                                If v_result <> '1' Then
                                                   PKG_BA_updCIStatus.sp_saveMsg( '0',
                                                                                  v_CurBAAPP.Apno,
                                                                                  v_issuym,
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
                                                      And APNO = v_CurBAAPP.APNO
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
                                        ((Substr(v_CIPB.Bmk,1,1) = '1') Or (Substr(v_CIPB.Bmk,1,1) = '4')) Then
                                          v_tempSBMK  := Substr(v_CIPB.Bmk,1,1);
                                          v_SBMK := Null;
                                          If (NVL(v_tempSBMK,' ') <> NVL(v_SBMK,' ')) Then
                                              PKG_BA_updCIStatus.sp_BA_updSBMK( v_payCode,               --  v_i_paycode 勞保年金給付別L：老年年金、K：失能年金、S：遺屬年金
                                                             v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                             v_CIPB.Ciid,             --  勞就保識別碼
                                                             v_i_user,                --  UserID
                                                             v_tempSBMK,              --  Befort SIDMK Value
                                                             v_SBMK,                  --  SIDMK Value
                                                             v_CurBAAPP.Apno,         --  受理編號
                                                             v_issuym,              --  核定年月
                                                             v_CurBAAPP.casetyp,      --  案件類別
                                                             v_systp,
                                                             v_result,
                                                             v_resultCode
                                                            );
                                               If v_result <> '1' Then
                                                  v_upcode := 'N';
                                                  PKG_BA_updCIStatus.sp_saveMsg( '0',
                                                              v_CurBAAPP.Apno,
                                                              v_issuym,
                                                              v_CIPB.CIID,
                                                              v_intyp,
                                                              v_i_user,
                                                              v_resultCode
                                                            );
                                               Else
                                                   -- 2012/05/18 新增同時同步BA CIPB BMK 欄位
                                                   v_updBACIBMK := v_SBMK || Substr(v_CIPB.Bmk,2,9);
                                                   Update CIPB
                                                      Set BMK = v_updBACIBMK
                                                    Where INTYP = v_CIPB.Intyp
                                                      And IDN   =  v_CIPB.Idn
                                                      And APNO = v_CurBAAPP.APNO
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
                                      如老年、失能、死亡及失蹤之領取給付註記為空值或＜2，且「管制加保註記」之欄位值為Y時，
                                      清為空白；未清除永久註記者，本欄位不須清為空白。
                                   */
                                   If (v_result = '1') And ((v_upcode <> 'Z') Or ((v_upcode <> 'N'))) Then
                                      If (NVL(Substr(v_CIPB.Bmk,1,1),'1') < 2 ) And
                                         (NVL(Substr(v_CIPB.Bmk,2,1),'1') < 2 ) And
                                         (NVL(Substr(v_CIPB.Bmk,3,1),'1') < 2 ) And
                                         (NVL(Substr(v_CIPB.Bmk,4,1),'1') < 2 ) And
                                         (NVL(v_CIPB.UINMK,' ') = 'Y') Then
                                            v_tempUINMK := v_CIPB.UINMK;
                                            v_UINMK := Null;
                                            If (NVL(v_tempUINMK,' ') <> NVL(v_UINMK,' ')) Then
                                                PKG_BA_updCIStatus.sp_BA_updUINMK( v_intyp,                 --  被保險人勞保資料(L) 勞保自願職保資料(V)
                                                                                   v_CIPB.Ciid,             --  勞就保識別碼
                                                                                   v_i_user,                --  UserID
                                                                                   v_tempSBMK,              --  Befort SIDMK Value
                                                                                   v_UINMK,                 --  SIDMK Value
                                                                                   v_CurBAAPP.Apno,         --  受理編號
                                                                                   v_issuym,                --  核定年月
                                                                                   v_CurBAAPP.casetyp,      --  案件類別
                                                                                   v_systp,
                                                                                   v_result,
                                                                                   v_resultCode
                                                               );
                                                If v_result <> '1' Then
                                                   PKG_BA_updCIStatus.sp_saveMsg( '0',
                                                                                  v_CurBAAPP.Apno,
                                                                                  v_issuym,
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
                                                      And APNO = v_CurBAAPP.APNO
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
                            If (v_result = '1') Then
                                v_countSUC := v_countSUC + 1;
                                Insert Into BAWITHDRAW (APNO, ISSUYM, CASETYP, INTYP, PAYKIND,
                                                        UBNO, EVTIDNNO, EVTBRDATE, EVTNAME, EVTJOBDATE,
                                                        APLPAYDATE, APPDATE, DEPT, VMK, UPCODE,
                                                        PBSTAT, PBSTAT2, PBUINMK, PBUINMK2, TXCD2,
                                                        SPCH, CIID, NDOMK, HCODE,  PROCTIME)
                                                VALUES (v_CurBAAPP.APNO, v_issuym, v_CurBAAPP.CASETYP, v_CIPB.Intyp, v_CurBAAPP.PAYKIND,
                                                        v_CurBAAPP.APUBNO, v_CurBAAPP.EVTIDNO, v_CurBAAPP.EVTBRDATE, v_CurBAAPP.EVTNAME, v_CurBAAPP.EVTJOBDATE,
                                                        v_CurBAAPP.APLPAYDATE, v_CurBAAPP.APPDATE, '', Decode(v_intyp,'V','V',Null),v_upcode,
                                                        v_tempSBMK, v_SBMK,v_tempUINMK, v_UINMK,'',
                                                        '', v_CIPB.CIID, v_CurBAAPP.NOTEMK, '', to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                        );
                               /* 老年年金：
                                  1、於每月勞保老年年金月核定作業後，讀取該月核付，且給付主檔之申請單位保險證號非空白之新案，
                                     以事故日期為退保日期產生逕退檔。
                                  2、於每月勞保老年年金月核定作業後，讀取該月不給付及暫緩給付案，當給付主檔之申請單位保險證
                                     號非空白，且行政支援記錄檔中函別21之處理註記(一)及處理註記（二）非55者，以事故日期為退
                                     保日期產生逕退檔。
                               */
                                If ( v_payCode = 'L' ) Then
                                     If ((v_CurBAAPP.CASETYP = '1') And (Trim(v_CurBAAPP.APUBNO) Is Not Null)) Or
                                          (((v_CurBAAPP.CASETYP = '3') Or (v_CurBAAPP.CASETYP = '6')) And (NVL(v_CurBAAPP.NOTEMK,' ') <> 'Y'))  Then
                                             v_countToDRAW := v_countToDRAW + 1;
                                             CI.CI040M020( v_intyp,                        --  保險別    L、V、F
                                                           v_CurBAAPP.PAYKIND,             --  核付種類  45 or 35 ...
                                                           v_CurBAAPP.APLPAYDATE,          --  核付日期
                                                           v_CurBAAPP.APUBNO,              --  保險證號
                                                           v_CurBAAPP.EVTIDNO,             --  身分證號
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
                                                           v_CurBAAPP.APUBNO,              --  保險證號
                                                           v_CurBAAPP.EVTIDNO,             --  身分證號
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
                                                          v_CurBAAPP.APUBNO,              --  保險證號
                                                          v_CurBAAPP.EVTIDNO,             --  身分證號
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

                                     -- Update BAWITHDRAW
                                     Update BAWITHDRAW
                                        Set TXCD2 = 'Y'
                                      Where APNO = v_CurBAAPP.APNO;

                                     -- Update BADISABLECASE
                                     Update BADISABLECASE
                                        Set PROCMK = 'Y',
                                            PROCTIME = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                      Where APNO = v_CurBAAPP.APNO;
                                Else
                                   v_countToDRAWErr := v_countToDRAWErr + 1;
                                End if;
                            Else
                               v_countErr := v_countErr + 1;
                            End if;
                         End If;
                     Else
                       v_countDupIdn := v_countDupIdn + 1;
                     End If;
                  Exception
                    when NO_DATA_FOUND then
                         PKG_BA_updCIStatus.sp_saveMsg( '0',
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
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 總筆數                 = ' || v_countALL);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 受理編號存在逕退檔筆數 = ' || v_countDRAW);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 處理總筆數             = ' || v_countPOC);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 更新領取註記成功筆數   = ' || v_countSUC);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 給付案G總筆數          = ' || v_countCase1G);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 不給付G案總筆數        = ' || v_countCase3G);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 給付案A總筆數          = ' || v_countCase1A);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 不給付A案總筆數        = ' || v_countCase3A);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 給付案Z總筆數          = ' || v_countCase1Z);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 不給付Z案總筆數        = ' || v_countCase3Z);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 更新領取註記失敗筆數   = ' || v_countErr);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 逕退總筆數             = ' || v_countToDRAW);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 逕退成功筆數           = ' || v_countToDRAWSUC);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 逕退失敗筆數           = ' || v_countToDRAWErr);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 查無事故者 BA.CIPB 資料= ' || v_countBI);
         PKG_BA_updCIStatus.sp_saveMsg( '1','','','','',v_i_user,' 未經身分證重號檔選擇   = ' || v_countDupIdn);
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
         dbms_output.put_line(' sp_BA_updDisableCase----------->>[ 使用者帳號不可為空白 ]');
     End If;

End sp_BA_updDisableCase;
/

