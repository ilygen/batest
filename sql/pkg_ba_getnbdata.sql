CREATE OR REPLACE Package BA.PKG_BA_getNBData
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_getNBData
    PURPOSE:         取得失能年金給付種類(36)國併勞案件資料，查詢國民年金主檔案件資料
                     回寫至勞保年金主檔。

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2013/07/04  ChungYu Lin  Created this Package.

    NOTES:
    1.各 Function 及 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/
authid definer is
    v_g_errMsg               varChar2(4000);

    Procedure sp_BA_genNBAPPBASEData (
        v_i_nbapno              in      varChar2
    );

    PROCEDURE Get_AllNBData;

End;
/

prompt
prompt Creating package body PKG_BA_GETNBDATA
prompt ======================================
prompt
CREATE OR REPLACE Package Body BA.PKG_BA_getNBData
is

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_getNBData.sp_BA_genNBAPPBASEData
        PURPOSE:         查詢國民年金NB給付主檔國併勞(36)案件資料，回寫至勞保年金主檔。

        PARAMETER(IN):  *v_i_nbapno   (varChar2)       --國民年金受理編號


        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2013/07/04  ChungYu Lin  Created this Package.
        1.1   2014/03/25  ChungYu Lin  修改國保勞保給付方式對應
        1.2   2014/05/06  ChungYu Lin  修改若國保的勞保受理編號為空，則回讀勞保的受理編號
        1.3   2014/11/05  Kiyomi       修改國保資料存入時，電話欄位中的「-」皆不帶入
        1.4   2015/03/20  ChungYu Lin  修改國保資料存入時，案件類別及處理狀態必須與事故者同步

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genNBAPPBASEData (
        v_i_nbapno               in      varChar2
    ) is

        v_rowCount            Number             := 0;
        v_rowNBEXCEPCount     Number             := 0;
        v_tempEvtDate         Varchar2(8)        := '';
        v_tempBAAPPBASEID     Number             := 0;
        v_tempCOMMADDR        BAAPPBASE.COMMADDR%TYPE  := '';
        v_npids               NBAPPBASE.EVTIDS%TYPE    := '';
        v_addrdiff            NBAPPBASE.COMMTYPE%TYPE  := '';
        v_commzip             NBAPPBASE.COMMZIP%TYPE   := '';
        v_hsncode             CVLDTL.HAREA%TYPE        := '';
        v_tempAPNO            Varchar2(12)             := '';
        v_tempSEQNO           Varchar2(4)              := '';
        v_tempPAYTYPE         NBAPPBASE.PAYTYPE%TYPE    := '';
        v_tempPAYBANKID       NBAPPBASE.PAYBANKID%TYPE := '';
        v_tempBRANCHID        NBAPPBASE.BRANCHID%TYPE  := '';
        v_tempPAYEEACC        NBAPPBASE.PAYEEACC%TYPE  := '';
        v_tempACCIDN          NBAPPBASE.ACCIDN%TYPE    := '';
        v_tempACCNAME         NBAPPBASE.ACCNAME%TYPE   := '';
        v_tempNPAPP           NBAPPBASE%ROWTYPE;
        v_tempACCSEQNO        Varchar2(4)              := '';

     Cursor c_dataCur_NBAPP is
              Select t.*
                From NPS.NBAPPBASE t
               Where t.APNO = v_i_nbapno
               Order By t.SEQNO;

     Begin

            If trim(v_i_nbapno) Is Not Null Then
               v_tempAPNO    := '';

               --依據傳入的受理編號，取出該受理編號的國保資料

                For v_dataCur_NBAPP in c_dataCur_NBAPP Loop
                 Begin
                     v_rowCount := 0;
                     v_tempEvtDate := '';
                     v_tempSEQNO   := '';

                     --  序號  0000 的資料存勞保受理編號，非0000要轉序號  2014/03/18 Modify By Chung Yu

                     If v_dataCur_NBAPP.Seqno = '0000' then
                        v_tempAPNO  := v_dataCur_NBAPP.LABAPNO;
                        v_tempSEQNO := v_dataCur_NBAPP.SEQNO;
                        If Trim(v_tempAPNO) Is Null Then               --  若國保的勞保受理編號為空，則回讀勞保的受理編號 2014/05/06 Add By ChungYu
                           Begin
                             Select APNO Into v_tempAPNO From BAAPPBASE
                              Where MAPNO = v_i_nbapno
                                And SEQNO = '0000';
                           Exception
                             When NO_DATA_FOUND
                             Then
                                  v_tempAPNO := null;
                           End;
                        End If;
                     Else
                        v_tempSEQNO := '0'|| Substr(v_dataCur_NBAPP.SEQNO,3,2)||'0';
                     End If;
                     
                     if length(trim(v_tempAPNO)) = 12 then    --20191017 add by Angela 增修勞保年金受理編號長度的檢核
                         Select Count(*) into v_rowCount From BAAPPBASE
                          Where APNO = v_tempAPNO
                            And SEQNO = v_tempSEQNO;

                         -- 2013/11/15 Modify By ChungYu 國保地址欄位Commtype = 1(同戶籍地) or 2(同繳款單) 要將地址資料帶入
                         If v_dataCur_NBAPP.Commtype = '1' Then
                           Begin
                            Select HADDR Into v_tempCOMMADDR From CVLDTL
                             Where NPIDS = UPPER(v_dataCur_NBAPP.Evtids);
                           Exception
                           When NO_DATA_FOUND
                           Then
                                v_tempCOMMADDR := null;
                           End;
                         Elsif v_dataCur_NBAPP.Commtype = '2' Then
                            v_npids := v_dataCur_NBAPP.Evtids;
                            get_curr_addr(v_npids, v_addrdiff, v_commzip, v_hsncode, v_tempCOMMADDR);
                         Else
                            v_tempCOMMADDR := v_dataCur_NBAPP.COMMADDR;
                         End If;

                         -- 2013/08/09 Modify By ChungYu 失能李小姐(1304)提出「年資管制日期CTRLBDT」如有日期，則直接帶入該日期。
                         -- 若為空則將「身障手冊鑑定日期HANDICAPDT」、「首次評估無工作能力日DIADAB2DT」兩者日期較大者寫入「診斷失能日期」欄。

                         Select Count(*) into v_rowNBEXCEPCount From NPS.NBEXCEP
                          Where BENIDS = v_dataCur_NBAPP.EVTIDS
                            And EXCEPTYPE = 'G';

                         If (v_rowNBEXCEPCount>0) Then
                             Select CTRLBDT into v_tempEvtDate From NPS.NBEXCEP
                              Where BENIDS = v_dataCur_NBAPP.EVTIDS
                                And EXCEPTYPE = 'G';
                         End If;

                         If (Trim(v_tempEvtDate) Is NULL) Then
                            If NVL(v_dataCur_NBAPP.HANDICAPDT,'0') > NVL(v_dataCur_NBAPP.DIADAB2DT,'0') Then
                               v_tempEvtDate := v_dataCur_NBAPP.HANDICAPDT;
                            Else
                               v_tempEvtDate := v_dataCur_NBAPP.DIADAB2DT;
                            End If;
                         End If;

                         --  2014/03/24  國保共同具領亦要轉到勞保
                         If v_dataCur_NBAPP.PAYTYPE = '6' Then
                           BEGIN
                            Select * INTO v_tempNPAPP From NBAPPBASE
                             Where APNO = v_dataCur_NBAPP.Apno
                               And ACCIDN = v_dataCur_NBAPP.ACCIDN
                               And ACCREL <> '3'
                               And PAYTYPE <> '6'
                               And ROWNUM = 1;

                             v_tempPAYTYPE   := v_tempNPAPP.PAYTYPE;
                             v_tempPAYBANKID := v_tempNPAPP.PAYBANKID;
                             v_tempBRANCHID  := v_tempNPAPP.BRANCHID;
                             v_tempPAYEEACC  := v_tempNPAPP.PAYEEACC;
                             v_tempACCIDN    := v_tempNPAPP.ACCIDN;
                             v_tempACCNAME   := v_tempNPAPP.ACCNAME;
                             v_tempACCSEQNO  := '0'|| Substr(v_tempNPAPP.SEQNO,3,2)||'0';
                           Exception
                           When NO_DATA_FOUND
                           Then
                                v_tempPAYTYPE   := '';
                                v_tempPAYBANKID := '';
                                v_tempBRANCHID  := '';
                                v_tempPAYEEACC  := '';
                                v_tempACCIDN    := '';
                                v_tempACCNAME   := '';
                                v_tempACCSEQNO  := '';
                           End;
                         Else
                             v_tempPAYTYPE   := v_dataCur_NBAPP.PAYTYPE;
                             v_tempPAYBANKID := v_dataCur_NBAPP.PAYBANKID;
                             v_tempBRANCHID  := v_dataCur_NBAPP.BRANCHID;
                             v_tempPAYEEACC  := v_dataCur_NBAPP.PAYEEACC;
                             v_tempACCIDN    := v_dataCur_NBAPP.ACCIDN;
                             v_tempACCNAME   := v_dataCur_NBAPP.ACCNAME;
                             v_tempACCSEQNO  := '';
                         End If;

                         If v_rowCount = 0 Then
                           -- DBMS_OUTPUT.PUT_LINE('Insert');

                            -- 新增失能年金給付主檔資料
                            INSERT INTO BAAPPBASE ( BAAPPBASEID         -- 資料列編號
                                                   ,APNO                -- 受理編號
                                                   ,SEQNO               -- 序號
                                                   ,IMK                 -- 保險別
                                                   ,APITEM              -- 申請項目
                                                   ,PAYKIND             -- 給付種類
                                                   ,APPDATE             -- 申請日期
                                                   ,CASETYP             -- 案件類別
                                                   ,MAPNO               -- 相關受理編號
                                                   ,COMBAPMARK          -- 國勞合併申請註記
                                                   ,PROCSTAT            -- 處理狀態
                                                   ,EVTIDS              -- 事故者社福識別碼
                                                   ,EVTIDNNO            -- 事故者身分證號
                                                   ,EVTBRDATE           -- 事故者出生日期
                                                   ,EVTNAME             -- 事故者姓名
                                                   ,EVTSEX              -- 事故者性別
                                                   ,EVTNATIONTPE        -- 事故者國籍別
                                                   ,EVTNATIONCODE       -- 事故者國籍
                                                   ,EVTIDENT            -- 事故者身分別
                                                   ,EVTJOBDATE          -- 事故日期
                                                   ,EVTDIEDATE          -- 事故者死亡日期
                                                   ,BENIDS              -- 受益人社福識別碼
                                                   ,BENIDNNO            -- 受益人身分證號
                                                   ,BENNAME             -- 受益人姓名
                                                   ,BENBRDATE           -- 受益人出生日期
                                                   ,BENSEX              -- 受益人性別
                                                   ,BENEVTREL           -- 受益人與事故者關係
                                                   ,BENNATIONTYP        -- 受益人國籍別
                                                   ,BENNATIONCODE       -- 受益人國籍
                                                   ,TEL1                -- 電話1
                                                   ,TEL2                -- 電話2
                                                   ,COMMTYP             -- 通訊地址別
                                                   ,COMMZIP             -- 通訊郵遞區號
                                                   ,COMMADDR            -- 通訊地址
                                                   ,PAYTYP              -- 給付方式
                                                   ,PAYBANKID           -- 金融機構總代號
                                                   ,BRANCHID            -- 分支代號
                                                   ,PAYEEACC            -- 銀行帳號
                                                   ,ACCIDN              -- 戶名IDN
                                                   ,ACCNAME             -- 戶名
                                                   ,ACCREL              -- 戶名與受益人關係
                                                   ,ACCSEQNO            -- 被共同具領之受款人員序號
                                                   ,GRDIDNNO            -- 法定代理人身分證號
                                                   ,GRDNAME             -- 法定代理人姓名
                                                   ,GRDBRDATE           -- 法定代理人出生日期
                                                   ,CLOSEDATE           -- 結案日期
                                                   ,NOTIFYFORM          -- 核定通知書格式
                                                   ,CRTUSER
                                                   ,CRTTIME
                                                   ,SPECIALACC          -- 專戶註記  2014/04/23 Add By ChungYu
                                                   ,SPEACCDATE          -- 專戶日期  2014/04/23 Add By ChungYu
                                                   )
                                           VALUES ( BAAPPBASEID.NEXTVAL       -- 資料列編號
                                                   ,v_tempAPNO                -- 受理編號
                                                   ,v_tempSEQNO               -- 序號
                                                   ,'1'                       -- 保險別
                                                   ,'0'                       -- 申請項目
                                                   ,'36'                      -- 給付種類
                                                   ,v_dataCur_NBAPP.APPDATE   -- 申請日期
                                                   ,'1'                       -- 案件類別
                                                   ,v_dataCur_NBAPP.APNO      -- 相關受理編號
                                                   ,'Y'                       -- 國勞合併申請註記(待釐清)
                                                   ,'00'                      -- 處理狀態
                                                   ,v_dataCur_NBAPP.EVTIDS    -- 事故者社福識別碼
                                                   ,v_dataCur_NBAPP.EVTIDNNO  -- 事故者身分證號
                                                   ,v_dataCur_NBAPP.EVTEEBIRT -- 事故者出生日期
                                                   ,v_dataCur_NBAPP.EVTEENAME -- 事故者姓名
                                                   ,v_dataCur_NBAPP.EVTSEX    -- 事故者性別
                                                   ,'1'                       -- 事故者國籍別
                                                   ,'000'                     -- 事故者國籍
                                                   ,v_dataCur_NBAPP.EVTIDENT  -- 事故者身分別
                                                   ,v_tempEvtDate             -- 事故日期
                                                   ,v_dataCur_NBAPP.DIEDATE   -- 事故者死亡日期
                                                   ,v_dataCur_NBAPP.BENIDS    -- 受益人社福識別碼
                                                   ,v_dataCur_NBAPP.BENIDNNO  -- 受益人身分證號
                                                   ,v_dataCur_NBAPP.BENEENAME -- 受益人姓名
                                                   ,v_dataCur_NBAPP.BENEEBIRT -- 受益人出生日期
                                                   ,v_dataCur_NBAPP.BENSEX    -- 受益人性別
                                                   ,v_dataCur_NBAPP.BENEVTREL -- 受益人與事故者關係
                                                   ,Decode(v_dataCur_NBAPP.NATIONTYPE,'0','1','1','2','1') -- 受益人國籍別 2014/05/01 Modify By ChungYu
                                                   ,'999'                     -- 受益人國籍
                                                   ,Replace(v_dataCur_NBAPP.COMMTEL,'-','')        -- 電話1 2014/11/05 Modify By Kiyomi
                                                   ,Replace(v_dataCur_NBAPP.MOBILE,'-','')         -- 電話2 2014/11/05 Modify By Kiyomi
                                                   ,Decode(v_dataCur_NBAPP.COMMTYPE,'1','1','2')   -- 通訊地址別
                                                   ,v_dataCur_NBAPP.COMMZIP   -- 通訊郵遞區號
                                                   ,v_tempCOMMADDR            -- 通訊地址
                                                   ,Decode(v_dataCur_NBAPP.PAYTYPE,'1','1','2','2','4','4','5','A','6',v_tempPAYTYPE,'')
                                                                              -- 給付方式 勞保給付方式：1-匯入銀行帳戶、2-匯入郵局帳號、3-來局領取、4-匯票郵寄申請人、5-國外匯款、6-大陸地區匯款、A-扣押戶
                                                                              --          國保給付方式：1.匯入銀行帳戶、2.匯入郵局帳號、3.未勾填、4.匯票郵寄申請人、5.支票、6.共同具領、7.其他
                                                                              --          國保給付方式轉勞保1、2、4互轉，5->A，其餘轉為空白。  2014/03/21 Modify By ChungYu
                                                   ,v_tempPAYBANKID           -- 金融機構總代號
                                                   ,v_tempBRANCHID  -- 分支代號
                                                   ,v_tempPAYEEACC  -- 銀行帳號
                                                   ,v_tempACCIDN    -- 戶名IDN
                                                   ,v_tempACCNAME   -- 戶名
                                                   ,NVL(v_dataCur_NBAPP.ACCREL, v_dataCur_NBAPP.BENEVTREL)    -- 戶名與受益人關係，20140224 MODIFIED BY KIYOMI 本欄位不可為null，但國保資料可允NULL，故增加若為NULL則帶入BENEVTREL
                                                   ,v_tempACCSEQNO            -- 被共同具領之受款人員序號
                                                   ,v_dataCur_NBAPP.GRDIDNNO  -- 法定代理人身分證號
                                                   ,v_dataCur_NBAPP.GRDNAME   -- 法定代理人姓名
                                                   ,v_dataCur_NBAPP.GRDBIRTH  -- 法定代理人出生日期
                                                   ,''   -- 結案日期
                                                   ,'999'
                                                   ,'BaCm'
                                                   ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                   ,Decode(v_dataCur_NBAPP.Specialacc,'Y','Y',Null)  -- 專戶註記  2014/04/23 Add By ChungYu
                                                   ,to_Char(Sysdate,'YYYYMMDD')                      -- 專戶日期  2014/04/23 Add By ChungYu
                                                  );

                            -- 新增失能年金延伸主檔資料
                            Select t.BAAPPBASEID Into v_tempBAAPPBASEID From Baappbase t
                             Where t.Apno = v_tempAPNO
                               And t.Seqno = v_tempSEQNO;

                            INSERT INTO BAAPPEXPAND ( BAAPPEXPANDID         -- 資料列編號
                                                     ,BAAPPBASEID
                                                     ,APNO
                                                     ,SEQNO
                                                     ,EVTYP
                                                     ,CRTUSER
                                                     ,CRTTIME
                                                     )
                                             VALUES ( BAAPPEXPANDID.NEXTVAL
                                                     ,v_tempBAAPPBASEID
                                                     ,v_tempAPNO
                                                     ,v_tempSEQNO
                                                     ,'4'
                                                     ,'BaCm'
                                                     ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                    );
                         Else

                            -- 更新失能年金給付主檔資料
                            UPDATE BAAPPBASE SET  IMK           = '1'                       -- 保險別
                                                 ,APITEM        = '0'                       -- 申請項目
                                                 ,PAYKIND       = '36'                      -- 給付種類
                                                 ,APPDATE       = v_dataCur_NBAPP.APPDATE   -- 申請日期
                                           --      ,CASETYP       = '1'                       -- 案件類別
                                                 ,MAPNO         = v_dataCur_NBAPP.APNO      -- 相關受理編號
                                                 ,COMBAPMARK    = 'Y'                       -- 國勞合併申請註記(待釐清)
                                           --      ,PROCSTAT      = '00'                      -- 處理狀態
                                                 ,EVTIDS        = v_dataCur_NBAPP.EVTIDS    -- 事故者社福識別碼
                                                 ,EVTIDNNO      = v_dataCur_NBAPP.EVTIDNNO  -- 事故者身分證號
                                                 ,EVTBRDATE     = v_dataCur_NBAPP.EVTEEBIRT -- 事故者出生日期
                                                 ,EVTNAME       = v_dataCur_NBAPP.EVTEENAME -- 事故者姓名
                                                 ,EVTSEX        = v_dataCur_NBAPP.EVTSEX    -- 事故者性別
                                                 ,EVTNATIONTPE  = '1'                       -- 事故者國籍別
                                                 ,EVTNATIONCODE = '000'                     -- 事故者國籍
                                                 ,EVTIDENT      = v_dataCur_NBAPP.EVTIDENT  -- 事故者身分別
                                                 ,EVTJOBDATE    = v_tempEvtDate             -- 事故日期
                                                 ,EVTDIEDATE    = v_dataCur_NBAPP.DIEDATE   -- 事故者死亡日期
                                                 ,BENIDS        = v_dataCur_NBAPP.BENIDS    -- 受益人社福識別碼
                                                 ,BENIDNNO      = v_dataCur_NBAPP.BENIDNNO  -- 受益人身分證號
                                                 ,BENNAME       = v_dataCur_NBAPP.BENEENAME -- 受益人姓名
                                                 ,BENBRDATE     = v_dataCur_NBAPP.BENEEBIRT -- 受益人出生日期
                                                 ,BENSEX        = v_dataCur_NBAPP.BENSEX    -- 受益人性別
                                                 ,BENEVTREL     = v_dataCur_NBAPP.BENEVTREL -- 受益人與事故者關係
                                                 ,BENNATIONTYP  = Decode(v_dataCur_NBAPP.NATIONTYPE,'0','1','1','2','1') -- 受益人國籍別
                                                 ,BENNATIONCODE = '999'                     -- 受益人國籍
                                                 ,TEL1          = Replace(v_dataCur_NBAPP.COMMTEL,'-','')       -- 電話1 2014/11/05 Modify By Kiyomi
                                                 ,TEL2          = Replace(v_dataCur_NBAPP.MOBILE,'-','')        -- 電話2 2014/11/05 Modify By Kiyomi
                                                 ,COMMTYP       = Decode(v_dataCur_NBAPP.COMMTYPE,'1','1','2')  -- 通訊地址別
                                                 ,COMMZIP       = v_dataCur_NBAPP.COMMZIP   -- 通訊郵遞區號
                                                 ,COMMADDR      = v_tempCOMMADDR            -- 通訊地址
                                                 --,PAYTYP        = Decode(v_dataCur_NBAPP.PAYTYPE,'1','1','2','2','4','4','5','A','6',v_tempPAYTYPE,'')
                                                                              -- 給付方式 勞保給付方式：1-匯入銀行帳戶、2-匯入郵局帳號、3-來局領取、4-匯票郵寄申請人、5-國外匯款、6-大陸地區匯款、A-扣押戶
                                                                              --          國保給付方式：1.匯入銀行帳戶、2.匯入郵局帳號、3.未勾填、4.匯票郵寄申請人、5.支票、6.共同具領、7.其他
                                                                              --          國保給付方式轉勞保1、2、4互轉，5->A，其餘轉為空白。  2014/03/21 Modify By ChungYu
                                                                              --  Mark By ChungYu 2014/04/23
                                                 --,PAYBANKID     = v_tempPAYBANKID -- 金融機構總代號   --  Mark By ChungYu 2014/04/23
                                                 --,BRANCHID      = v_tempBRANCHID  -- 分支代號         --  Mark By ChungYu 2014/04/23
                                                 --,PAYEEACC      = v_tempPAYEEACC  -- 銀行帳號         --  Mark By ChungYu 2014/04/23
                                                 --,ACCIDN        = v_tempACCIDN    -- 戶名IDN          --  Mark By ChungYu 2014/04/23
                                                 --,ACCNAME       = v_tempACCNAME   -- 戶名             --  Mark By ChungYu 2014/04/23
                                                 --,ACCREL        = NVL(v_dataCur_NBAPP.ACCREL, v_dataCur_NBAPP.BENEVTREL)    -- 戶名與受益人關係  --  Mark By ChungYu 2014/04/23
                                                 --,ACCSEQNO      = v_tempACCSEQNO            -- 被共同具領之受款人員序號   --  Mark By ChungYu 2014/04/23
                                                 ,GRDIDNNO      = v_dataCur_NBAPP.GRDIDNNO  -- 法定代理人身分證號
                                                 ,GRDNAME       = v_dataCur_NBAPP.GRDNAME   -- 法定代理人姓名
                                                 ,GRDBRDATE     = v_dataCur_NBAPP.GRDBIRTH  -- 法法定代理人出生日期
                                      --           ,CLOSEDATE     = ''                      -- 結案日期
                                                 ,UPDUSER       = 'BaCm'
                                                 ,UPDTIME       = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                 ,SPECIALACC    = Decode(v_dataCur_NBAPP.Specialacc,'Y','Y',Null)  --  專戶註記
                                                 ,SPEACCDATE    = NVL(SPEACCDATE,decode(v_dataCur_NBAPP.Specialacc,'Y',to_Char(Sysdate,'YYYYMMDD'),Null))  --  專戶日期
                                           Where APNO = v_tempAPNO
                                             And SEQNO = v_tempSEQNO;

                            -- 更新失能年金給付主檔資料
                            UPDATE BAAPPEXPAND SET  UPDUSER       = 'BaCm'
                                                   ,UPDTIME       = to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           Where APNO = v_tempAPNO
                                             And SEQNO = v_tempSEQNO;
                         End If;

                         -- Add By ChungYu 2015/03/20
                         UPDATE BAAPPBASE
                            SET (CASETYP, PROCSTAT) = (Select T.CASETYP, T.PROCSTAT
                                                         From BAAPPBASE T
                                                        Where T.APNO = v_tempAPNO
                                                          And T.SEQNO = '0000')
                          Where APNO = v_tempAPNO
                            And SEQNO <> '0000';
                         -- Add By ChungYu 2015/03/20
                         
                         dbms_output.put_line('  PKG_BA_getNBData-sp_BA_genNBAPPBASEData Success--->>[ '||v_i_nbapno||' ]:'||v_dataCur_NBAPP.Seqno);
                     else
                         PKG_BA_getCIData.INS_ERR_LOG(v_i_nbapno,'N','Err:sp_BA_genNBAPPBASEData');
                         Dbms_output.put_line('**Err:PKG_BA_getNBData-sp_BA_genNBAPPBASEData：傳入之(勞保)受理編號有誤，(BA)APNO='||v_dataCur_NBAPP.Labapno||'；(NB)APNO='||v_i_nbapno||'；SEQNO='||v_dataCur_NBAPP.SEQNO);
                     end if;
                 Exception
                       When others then
                            PKG_BA_getCIData.INS_ERR_LOG(v_i_nbapno,'N','Err:sp_BA_genNBAPPBASEData');
                            Dbms_output.put_line('**Err:PKG_BA_getNBData-sp_BA_genNBAPPBASEData------->>['||v_dataCur_NBAPP.LABAPNO||' ]:'||v_dataCur_NBAPP.SEQNO);
                            Dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
                       End;
                 End Loop;
            Else
                 dbms_output.put_line('  PKG_BA_getNBData-sp_BA_genNBAPPBASEData----------->>[NB APNO Is Null]');
            End If;
     End;
    --Procedure sp_BA_genNBAPPBASEData

  /********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            PKG_BA_getCIData.Get_AllNBData
      PURPOSE:         將全部國並勞(36)之案件資料重新轉入勞保主檔及延伸主檔。

      PARAMETER(IN):

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
      1.0   2013/07/10  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/

  PROCEDURE Get_AllNBData
  IS
       v_count   NUMBER    := 0;
       Cursor c_dataCur_NBAPP is
          SELECT Distinct T.APNO FROM NPS.NBAPPBASE T
           WHERE T.LABAPNO like 'K2%';

  BEGIN

         dbms_output.disable;
         for v_CurNBAPP in c_dataCur_NBAPP Loop
             v_count := v_count + 1;
             sp_BA_genNBAPPBASEData(v_CurNBAPP.APNO);
             IF( MOD(v_count, 500) = 0) THEN
                 COMMIT;
             END IF;
         END Loop;
         COMMIT;
  END Get_AllNBData;
  --Procedure Get_AllNBData

End;
/