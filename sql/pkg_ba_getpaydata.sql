spool PKG_BA_GETPAYDATA.log


CREATE OR REPLACE Package BA.PKG_BA_getPayData
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_getPayData
    PURPOSE:         查詢核付相關資料之共用Package

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2009/07/10  Angela Wu    Created this Package.

    NOTES:
    1.各 Function 及 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/
authid definer is
    v_g_rowCount             Number;
    v_g_errMsg               varChar2(4000);
    v_g_rtnData              varChar2(100);
    v_g_ProgName             varChar2(200);
    v_g_flag                 varchar2(1);
    v_g_payFlag              varchar2(1);
    v_g_payDate              varChar2(100);
    v_g_dateCount            Number;
    function fn_BA_getChkDate (
        v_i_issutyp            in      varChar2,
        v_i_bankkind           in      varChar2,
        v_i_paycode            in      varChar2,
        v_i_issuym             in      varChar2,
        v_i_toChineseDate      in      varChar2,
        v_i_chkdate            in      varchar2
    ) return varChar2;

    function fn_BA_getPayDate (
        v_i_issutyp            in      varChar2,
        v_i_bankkind           in      varChar2,
        v_i_paycode            in      varChar2,
        v_i_issuym             in      varChar2,
        v_i_toChineseDate      in      varChar2,
        v_i_chkdate            in      varchar2
    ) return varChar2;

    function fn_BA_getProcEmpInfo (
        v_i_getParamCode        in      varChar2
    ) return varChar2;

    Procedure sp_BA_genBAIssuData (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_mtestmk             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
        --v_i_GoLiveDate          in      varChar2
    );

End;
/

prompt
prompt Creating package body PKG_BA_GETPAYDATA
prompt ==================================================
prompt
CREATE OR REPLACE Package Body BA.PKG_BA_getPayData
is
    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_getPayData.fn_BA_getChkDate
        PURPOSE:         依傳入之核付處理類別、給付銀行別、核定年月、給付別查詢月核定日期。

        PARAMETER(IN):  *v_i_issutyp       (varChar2)       --核付處理類別
                        *v_i_bankkind      (varChar2)       --給付銀行別
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_issuym        (varChar2)       --核定年月
                        *v_i_toChineseDate (varChar2)       --是否轉換成民國年格式
                        *v_i_payseqno       (varChar2)      --核付次數(1_第一次核付：一般年金(老年、失能、遺屬)；
                                                                       2_第二次核付：失能(國併勞))
                        *v_i_chkdate       (varChar2)       --核定日期

        PARAMETER(OUT):  v_g_rtnData       (varChar2)       --月核定日期

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/10  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    function fn_BA_getChkDate (
        v_i_issutyp            in      varChar2,
        v_i_bankkind           in      varChar2,
        v_i_paycode            in      varChar2,
        v_i_issuym             in      varChar2,
        v_i_toChineseDate      in      varChar2,
        v_i_chkdate            in      varchar2
    ) return varChar2 is
        begin
            v_g_rtnData := '';
            v_g_rowCount := 0;

                  select Count(*)
                    into v_g_rowCount
                    from BAPAISSUDATE t
                   where t.PAYCODE = UPPER(v_i_paycode)
                     and t.ISSUYM = v_i_issuym
                     and t.BANKKIND = v_i_bankkind
                     and t.ISSUTYP = v_i_issutyp
                     and t.issudate = v_i_chkdate;
                  --大批量月核
                  if v_g_rowCount > 0 then
                    select Max(t.ISSUDATE)
                      into v_g_rtnData
                      from BAPAISSUDATE t
                     where t.PAYCODE = UPPER(v_i_paycode)
                       and t.ISSUYM = v_i_issuym
                       and t.BANKKIND = v_i_bankkind
                       and t.ISSUTYP = v_i_issutyp
                       and t.issudate = v_i_chkdate;

                    if UPPER(v_i_toChineseDate) = 'Y' then
                      v_g_rtnData := fn_BA_transDateValue(v_g_rtnData, '1');
                    end if;
                  --小批量月核
                  else
                    v_g_rtnData := v_i_chkdate;
                  end if;
            return v_g_rtnData;
        end;
    --function fn_BA_getChkDate

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_getPayData.fn_BA_getPayDate
        PURPOSE:         依傳入之核付處理類別、給付銀行別、核定年月、給付別查詢核付日期。

        PARAMETER(IN):  *v_i_issutyp       (varChar2)       --核付處理類別
                        *v_i_bankkind      (varChar2)       --給付銀行別
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_issuym        (varChar2)       --核定年月
                        *v_i_toChineseDate (varChar2)       --是否轉換成民國年格式
                        *v_i_chkdate       (varChar2)       --核定日期
        PARAMETER(OUT):  v_g_rtnData       (varChar2)       --核付日期

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/10  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    function fn_BA_getPayDate (
        v_i_issutyp            in      varChar2,
        v_i_bankkind           in      varChar2,
        v_i_paycode            in      varChar2,
        v_i_issuym             in      varChar2,
        v_i_toChineseDate      in      varChar2,
        v_i_chkdate            in      varChar2
    ) return varChar2 is
        begin
            v_g_rtnData := ' ';
            v_g_rowCount := 0;
            v_g_dateCount := 0;
            v_g_payDate := '';
            v_g_payFlag := 'N';--flag for 工作檔
                select Count(*) into v_g_rowCount
                  from BAPAISSUDATE t
                 where t.PAYCODE = UPPER(v_i_paycode)
                   and t.ISSUYM = v_i_issuym
                   and t.BANKKIND = v_i_bankkind
                   and t.ISSUTYP = v_i_issutyp
                   and t.issudate = v_i_chkdate;
                --大批量月核
                if v_g_rowCount>0 then
                    select Max(t.APLPAYDATE) into v_g_rtnData
                      from BAPAISSUDATE t
                     where t.PAYCODE = UPPER(v_i_paycode)
                       and t.ISSUYM = v_i_issuym
                       and t.BANKKIND = v_i_bankkind
                       and t.ISSUTYP = v_i_issutyp
                       and t.issudate = v_i_chkdate;
                    if UPPER(v_i_toChineseDate) = 'Y' then
                        v_g_rtnData := fn_BA_transDateValue(v_g_rtnData,'1');
                    end if;
                    return v_g_rtnData;
                --小批量月核(paydate 改以核定日期後下一個工作日)
                else
                    v_g_payDate := v_i_chkdate;
                    while v_g_payFlag = 'N' loop
                         v_g_payDate := TO_CHAR(TO_DATE(v_g_payDate,'YYYYMMDD')+1,'YYYYMMDD');
                         select COUNT(T.HDT) into v_g_dateCount
                         from SDHOLIDAY t
                         where TO_CHAR(T.HDT,'YYYYMMDD') = v_g_payDate;
                         if v_g_dateCount = 0 then
                            v_g_payFlag := 'Y';
                         end if;
                    end loop;
                    return v_g_payDate;
                end if;

        end;
    --function fn_BA_getPayDate

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_getPayData.fn_BA_getProcEmpInfo
        PURPOSE:         依傳入之查詢資料類別查詢系統參數檔預設的處理人員相關資訊。

        PARAMETER(IN):  *v_i_getParamCode  (varChar2)       --查詢資料類別

        PARAMETER(OUT):  v_g_rtnData       (varChar2)       --處理人員相關資訊

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/10  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    function fn_BA_getProcEmpInfo (
        v_i_getParamCode       in      varChar2
    ) return varChar2 is
        begin
            v_g_rtnData := '';
            v_g_rowCount := 0;

            select Count(*) into v_g_rowCount
              from BAPARAM t
             where t.PARAMGROUP = 'BLIPROC'
               and t.PARAMCODE = UPPER(v_i_getParamCode)
               and t.PARAMUSEMK = 'Y';

            if v_g_rowCount>0 then
                select PARAMNAME into v_g_rtnData
                  from BAPARAM t
                 where t.PARAMGROUP = 'BLIPROC'
                   and t.PARAMCODE = UPPER(v_i_getParamCode)
                   and t.PARAMUSEMK = 'Y';
            else
                v_g_rtnData := ' ';
            end if;

            return v_g_rtnData;
        end;
    --function fn_BA_getProcEmpInfo

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_getPayData.sp_BA_genBAIssuData
        PURPOSE:         產生月處理資料暫存紀錄檔資料。

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月
                        *v_i_paycode       (varChar2)       --給付別
                        *v_i_chkdate       (varChar2)       --月核定日期
                        *v_i_mtestmk       (varChar2)       --處理註記

                        *v_i_payseqno      (varChar2)       --欄位值：1為第一次出媒體
                                                                    ：2為第二次出媒體(僅出失能國並勞36案部份)
                                                            -2011/12/08 Add by ChungYu

                        *v_i_bajobid       (varChar2)       --線上批次id 2014/10/16 Add by Zehua
                        *v_i_GoLiveDate    (varChar2)       --核付案件申請期限日期

        PARAMETER(OUT):
                        *v_o_flag    (varChar2)             --傳出值 0:成功；1:失敗 2014/10/16 Add by Zehua
        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/10  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genBAIssuData (
        v_i_issuym              in      varChar2,
        v_i_paycode             in      varChar2,
        v_i_chkdate             in      varChar2,
        v_i_mtestmk             in      varChar2,
        v_i_payseqno            in      varChar2,
        v_i_bajobid             in      varchar2,
        v_o_flag                out     varchar2
       -- v_i_GoLiveDate          in      varChar2
    ) is
      v_Apno                BA.BAAPPBASE.APNO%TYPE      := '';      -- 受理編號
      v_Seqno               BA.BAAPPBASE.SEQNO%TYPE     := '';      -- 序號
      v_EvtEVTIDNNO         BA.BAAPPBASE.EVTIDNNO%TYPE  := '';      -- 2012/08/24 Add By ChungYu 事故者身分證號(遺屬事故者並無受款人身分證號，
                                                                    -- 當產製紓困貸款清單時需將事故者身分證號帶入受款人身分證號)
                                                                    -- 否則無法讀取到事故者紓困貸款資料。
      v_BenISSUEAMT         BA.BADAPR.ISSUEAMT%TYPE     := 0;       -- 2012/09/26 Add By ChungYu 因為遺屬普植互改案件，於不補不收以及有扣除事故者扣項時，
                                                                    -- 於產製合格清冊抓取核定金額互相衝突並且無條件可供判斷，因此於最前端抓取資料時區分開來。

        --2011/12/08 Modify by ChungYu
        --查詢待發的核付資料，將給付主檔及核定檔Join的方式，拆開分兩段SQL分別撈主檔及核定檔資料。
        --第1段:將主檔所有本次核定年月待發的案件包含受款人讀出(老年紓困受款人亦會讀出，但是後端出紓困媒體檔以不從紓困受款人抓資料)。
        --第2段:依序將主檔受款人資料，到核定檔撈本次核定年月的核付資料


     Cursor c_dataCur_Final is
            select APNO                                                 --受理編號
                  ,SEQNO                                                --受款人序號
                  ,PAYKIND                                              --給付種類
                  ,IMK                                                  --保險別
                  ,EVTIDNNO As EVTIDN                                   --事故者身分證號      2012/08/24 Add By ChungYu
                  ,BENIDS                                               --受益人社福識別碼
                  ,BENIDNNO As BENIDN                                   --受益人身分證號
                  ,BENNAME                                              --受益人姓名
                  ,CASETYP                                              --案件類別
                  ,BENEVTREL                                            --受益人與事故者關係
                  ,COMMZIP                                              --郵遞區號
                  ,COMMADDR                                             --地址
                  ,TEL1 As COMMTEL                                      --電話
                  ,ACCIDN                                               --戶名IDN
                  ,ACCNAME                                              --戶名
                  ,deCode(ACCREL,'1',(deCode((nvl(trim(ACCSEQNO),SEQNO)),SEQNO,SEQNO,'Err'))
                           ,'3',(deCode((nvl(trim(ACCSEQNO),' ')),' ','Err',SEQNO,'Err',ACCSEQNO))
                           ,'Err') as ACCSEQNO                          --被共同具領之受款人員序號
                  ,deCode(LSCHKMK,'5','1','0') as DLINEMK               --勞貸戶結清註記
              from BAAPPBASE
             where APNO like (v_i_paycode||'%')
               and (PROCSTAT = '50' or PROCSTAT = '90')
               --and   ((v_i_payseqno = '1' And PAYKIND <> '36' And PAYKIND <> '38')    -- v_i_payseqno = '1' 為第一次出媒體(不出36及38案) 38案須等勞併國補檔完在上  modify by Chungyu 2014/02/12
               and   ((v_i_payseqno = '1' And PAYKIND <> '36')                        -- v_i_payseqno = '1' 為第一次出媒體(不出36案) 38案勞併國已經補檔完    modify by JustinUu 2015/11/22
                   or (v_i_payseqno = '2' And PAYKIND = '36'))                        -- v_i_payseqno = '2' 為第二次出媒體(僅出36案)     modify by Chungyu 2014/02/12
               and issuym = v_i_issuym
               and trim(MANCHKMK) Is Not Null                            -- modify by Chungyu 2012/02/10
               --and (ACCEPTMK = 'Y' or nvl(trim(ACCEPTMK),' ') = ' ')  //modify by Chungyu 2012/02/10
               and (CHKDATE is not null and nvl(trim(CHKDATE),' ')<>' ')
               and (EXEDATE is not null and nvl(trim(EXEDATE),' ')<>' ');

      --第2段:依序將主檔受款人資料，到核定檔撈本次核定年月的核付資料
     Cursor c_dataCur_Badapr is
            select  t2.APNO
                   ,t2.SEQNO
                   ,t2.ISSUYM
                   ,t2.PAYYM
                   ,t2.PAYKIND
                   ,t2.MCHKTYP as ISSUTYP
                   ,t2.PAYTYP as SOURCEPAYTYP
                   ,deCode(t2.PAYTYP,'1','1','2','1',t2.PAYTYP) as PAYTYP
                   ,t2.ACCIDN
                   ,t2.ACCNAME
                   ,t2.CHKDATE
                   ,t2.APLPAYDATE
                   ,deCode(t2.PAYTYP,'1',LPAD(nvl(trim(t2.PAYBANKID),'0'),3,'0')
                   ,'2',LPAD(nvl(trim(t2.PAYBANKID),'0'),3,'0')
                            ,nvl(trim(t2.PAYBANKID),' ')) as PAYBANKID
                   ,deCode(t2.PAYTYP,'1',LPAD(nvl(trim(t2.BRANCHID),'0'),4,'0')
                                    ,'2',LPAD(nvl(trim(t2.BRANCHID),'0'),4,'0')
                                    ,nvl(trim(t2.BRANCHID),' ')) as BRANCHID
                   ,deCode(t2.PAYTYP,'1',LPAD(nvl(trim(t2.PAYEEACC),'0'),14,'0')
                                    ,'2',LPAD(nvl(trim(t2.PAYEEACC),'0'),14,'0')
                                    ,nvl(trim(t2.PAYEEACC),' ')) as PAYEEACC
                   ,deCode(t2.MCHKTYP,'2',deCode(t2.SUPRECMK,'C',deCode(t2.NACHGMK,NULL,t2.SUPAMT
                                                                                  ,t2.BEFISSUEAMT)
                                                            ,'D',deCode(t2.NACHGMK,NULL,'0'
                                                                                  ,t2.BEFISSUEAMT)          --  失能普職互改 定義不同 // 2012/06/28 Add By ChungYu
                                                            ,t2.BEFISSUEAMT)
                                     ,'5',deCode(t2.SUPRECMK,'C',deCode(t2.NACHGMK,NULL,t2.SUPAMT
                                                                                  ,t2.BEFISSUEAMT)
                                                            ,'D',deCode(t2.NACHGMK,NULL,'0'
                                                                                  ,t2.BEFISSUEAMT))         --  失能普職互改 定義不同 // 2012/06/28 Add By ChungYu
                                     ,'4',deCode(t2.SUPRECMK,'C',deCode(t2.NACHGMK,NULL,t2.SUPAMT
                                                                                  ,t2.BEFISSUEAMT)
                                                            ,'D',deCode(t2.NACHGMK,NULL,'0'
                                                                                  ,t2.BEFISSUEAMT)
                                                            ,deCode(t2.SEQNO,'0000', deCode(SUBSTR(t2.APNO,1,1),'L',(nvl(t2.COMPENAMT,0) + nvl(t2.OFFSETAMT,0) + nvl(t2.OTHERAMT,0) + nvl(t2.APLPAYAMT,0))    --死亡後結案仍有可能是給被保險人錢
                                                                                                                   ,t2.ISSUEAMT)
                                                                                                                   ,t2.ISSUEAMT))  -- 死亡結案核定金額L：補償金 + 紓困 + 另案扣減，失能及遺屬：紓困 + 另案扣減。  2012/06/22  Add By ChungYu
                                     ,t2.BEFISSUEAMT) as ISSUEAMT
                   ,nvl(t2.APLPAYAMT,0) as APLPAYAMT
                   ,nvl(t2.OTHERAAMT,0) as OTHERAAMT
                   ,nvl(t2.OTHERBAMT,0) as OTHERBAMT
                   ,nvl(t2.OFFSETAMT,0) as OFFSETAMT
                   ,nvl(t2.COMPENAMT,0) as COMPENAMT
                   ,nvl(t2.PAYRATE,0) as PAYRATE
                   ,nvl(t2.RECAMT,0) as RECAMT
                   ,nvl(t2.INHERITORAMT,0) as INHERITORAMT   --  2017/09/07 Modify By ChungYu 因遺屬計息存儲需求核定檔新增計息存儲金額欄位
                   ,0 as ITRTTAX
                   ,0 as OTHERAMT
                   ,t2.CPRNPAGE
                   ,t2.CPRNDATE
                   ,t2.DLINEDATE
                   ,t2.MANCHKMK
                   ,t2.NLWKMK
                   ,t2.ADWKMK
                   ,t2.NACHGMK
                   ,t2.EVTOTHERAAMT
                   ,t2.EVTOTHERBAMT
                   ,t2.EVTOFFSETAMT
                   ,t2.ISSUEAMT AS ORIISSUEAMT
              from BADAPR t2
             where t2.APNO = v_Apno
               and t2.SEQNO = v_Seqno
               and t2.ISSUYM = v_i_issuym
               and t2.MTESTMK = v_i_mtestmk
               -- and t2.MANCHKMK = 'Y'                                       mark by Chungyu 2012/02/10
               -- and (t2.ACCEPTMK = 'Y' or nvl(trim(t2.ACCEPTMK),' ') = ' ') mark by Chungyu 2012/02/10
               and t2.MANCHKMK Is Not Null                                    -- modify By Chungyu 2012/02/10
               and t2.CHKDATE = v_i_chkdate;

        begin
            v_g_ProgName := 'PKG_BA_getPayData.sp_BA_genBAIssuData';
            v_g_flag := '0';
            --清除該給付別所有核付暫存資料
            --Modify by Anglea 20190320
            /**
            delete from BAISSUDATATEMP
             where PAYCODE = v_i_paycode
               and PAYSEQNO = v_i_payseqno;
            **/
            execute immediate 'truncate table BAISSUDATATEMP';
            
            v_g_rowCount := 0;

            begin
                 --第1段:將主檔所有本次核定年月待發的案件包含受款人讀出
                 for v_CurFinal in c_dataCur_Final
                 Loop
                 Begin
                     v_Apno := '';
                     v_Seqno := '';

                     -- 2012/08/24 Add By ChungYu
                     v_EvtEVTIDNNO := Null;
                     -- 2012/08/24 Add By ChungYu

                     v_g_rowCount := v_g_rowCount + 1;
                     --如果ACCSEQNO Error整個跳出結束
                     if v_CurFinal.ACCSEQNO <> 'Err' then
                        v_Apno := v_CurFinal.APNO;
                        v_Seqno := v_CurFinal.SEQNO;

                        --  2012/08/24 Add By ChungYu 將遺屬事故者身分證號取出，後續帶入受益人身分證號(紓困貸款)
                        v_EvtEVTIDNNO := Null;
                        if ((SUBSTR(v_Apno,1,1) = 'S') And (v_Seqno = '0000')) Then
                             v_EvtEVTIDNNO := v_CurFinal.EVTIDN;
                        end if;
                        --  2012/08/24 Add By ChungYu

                        --第2段:依序將主檔受款人資料，到核定檔撈本次核定年月的核付資料

                        for v_CurFinalBadapr in c_dataCur_Badapr
                        Loop
                        Begin
                            -- 2012/09/26 Add By ChungYu 因為遺屬普植互改案件，於不補不收以及有扣除事故者扣項時，
                            -- 於產製合格清冊抓取核定金額互相衝突並且無條件可供判斷，因此於最前端抓取資料時區分開來。


                            v_BenISSUEAMT := 0;
                            if ((SUBSTR(v_Apno,1,1) = 'S')
                                 And (v_Seqno <> '0000')
                                 And (v_CurFinalBadapr.NACHGMK is not null)
                                 And (NVL(v_CurFinalBadapr.ORIISSUEAMT,0) >0 )) Then
                                      v_BenISSUEAMT := NVL(v_CurFinalBadapr.ISSUEAMT,0)
                                                       - NVL(v_CurFinalBadapr.EVTOTHERAAMT,0)
                                                       - NVL(v_CurFinalBadapr.EVTOTHERBAMT,0)
                                                       - NVL(v_CurFinalBadapr.EVTOFFSETAMT,0);
                            else
                                v_BenISSUEAMT := v_CurFinalBadapr.ISSUEAMT;
                            end if;


                            -- 2012/09/26 Add By ChungYu

                            insert into BAISSUDATATEMP (APNO
                                                       ,SEQNO
                                                       ,PAYCODE
                                                       ,INSKD
                                                       ,PAYKIND
                                                       ,ISSUTYP
                                                       ,CASETYP
                                                       ,ISSUYM
                                                       ,PAYYM
                                                       ,SOURCEPAYTYP
                                                       ,PAYTYP
                                                       ,BENIDS
                                                       ,BENIDN
                                                       ,BENNAME
                                                       ,BENEVTREL
                                                       ,COMMZIP
                                                       ,COMMADDR
                                                       ,COMMTEL
                                                       ,ACCIDN
                                                       ,ACCNAME
                                                       ,ACCSEQNO
                                                       ,PAYBANKID
                                                       ,BRANCHID
                                                       ,PAYEEACC
                                                       ,ISSUEAMT
                                                       ,APLPAYAMT
                                                       ,PAYRATE
                                                       ,OTHERAAMT
                                                       ,OTHERBAMT
                                                       ,OFFSETAMT
                                                       ,COMPENAMT
                                                       ,CHKDATE
                                                       ,DLINEDATE
                                                       ,DLINEMK
                                                       ,APLPAYDATE
                                                       ,PROCTIME
                                                       ,PAYSEQNO
                                                       ,MANCHKMK
                                                       ,NLWKMK
                                                       ,ADWKMK
                                                       ,NACHGMK
                                                       ,UNACPAMT                                  -- 2012/05/16  Add By ChungYu
                                                       ,INHERITORAMT                              -- 2017/09/07  Add By ChungYu
                                                      ) values (
                                                        v_CurFinal.APNO
                                                       ,v_CurFinal.SEQNO
                                                       ,v_i_paycode
                                                       ,v_CurFinal.IMK
                                                       ,v_CurFinalBadapr.PAYKIND                  -- 2012/06/06  v_CurFinal.PAYKIND -->  _CurFinalBadapr.PAYKIND  因為失能會同時有35、37
                                                       ,v_CurFinalBadapr.ISSUTYP
                                                       ,v_CurFinal.CASETYP
                                                       ,v_CurFinalBadapr.ISSUYM
                                                       ,v_CurFinalBadapr.PAYYM
                                                       ,v_CurFinalBadapr.SOURCEPAYTYP
                                                       ,v_CurFinalBadapr.PAYTYP
                                                       ,v_CurFinal.BENIDS
                                                       ,NVL(v_EvtEVTIDNNO,v_CurFinal.BENIDN)      -- 2012/08/24  Modify By ChungYu   遺屬事故者身分證號帶入受益人身分證號(紓困貸款明細清單)
                                                       ,v_CurFinal.BENNAME
                                                       ,v_CurFinal.BENEVTREL
                                                       ,v_CurFinal.COMMZIP
                                                       ,v_CurFinal.COMMADDR
                                                       ,v_CurFinal.COMMTEL
                                                       ,v_CurFinal.ACCIDN
                                                       ,v_CurFinal.ACCNAME
                                                       ,v_CurFinal.ACCSEQNO
                                                       ,v_CurFinalBadapr.PAYBANKID
                                                       ,v_CurFinalBadapr.BRANCHID
                                                       ,v_CurFinalBadapr.PAYEEACC
                                                       ,v_BenISSUEAMT                              -- 2012/09/26 Mark by ChungYu v_CurFinalBadapr.ISSUEAMT
                                                       ,v_CurFinalBadapr.APLPAYAMT
                                                       ,v_CurFinalBadapr.PAYRATE
                                                       ,v_CurFinalBadapr.OTHERAAMT
                                                       ,v_CurFinalBadapr.OTHERBAMT
                                                       ,v_CurFinalBadapr.OFFSETAMT
                                                       ,v_CurFinalBadapr.COMPENAMT
                                                       ,v_CurFinalBadapr.CHKDATE
                                                       ,v_CurFinalBadapr.DLINEDATE
                                                       ,v_CurFinal.DLINEMK
                                                       ,v_CurFinalBadapr.APLPAYDATE
                                                       ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                       ,v_i_payseqno
                                                       ,v_CurFinalBadapr.MANCHKMK
                                                       ,v_CurFinalBadapr.NLWKMK
                                                       ,v_CurFinalBadapr.ADWKMK
                                                       ,v_CurFinalBadapr.NACHGMK
                                                       ,v_CurFinalBadapr.RECAMT              -- 2012/05/16  Add By ChungYu
                                                       ,v_CurFinalBadapr.INHERITORAMT        -- 2017/09/07  Add By ChungYu
                                                       );
                         exception
                                  when others then
                                  v_g_errMsg := SQLErrm;
                                  dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>(SQLErr)['||v_i_paycode||'] '||v_g_errMsg);
                                  
                                  --修改log作法 by TseHua 20180419
                                  sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid,'1',
                                     RPAD('**Err:'||v_g_ProgName,85,'-')||'>>(SQLErr)['||v_i_paycode||'] '||v_g_errMsg ,
                                     replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                                     v_g_flag := '1'; 
                                     v_o_flag := v_g_flag;
                          end;
                        end loop;


                     else
                           dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>(ACCSEQNO資料錯誤)'||v_CurFinal.APNO||'-'||v_CurFinal.SEQNO);
                           rollback;
                           --Modify by Anglea 20190320
                           /**
                           Delete from BAISSUDATATEMP
                            Where PAYCODE = v_i_paycode
                              and PAYSEQNO = v_i_payseqno;
                           commit;
                           **/
                           execute immediate 'truncate table BAISSUDATATEMP'; 
                           v_g_rowCount := 0;
                           
                           --修改log作法 by TseHua 20180419
                            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                              RPAD('**Err:' || v_g_ProgName, 85, '-') || '>>(ACCSEQNO資料錯誤)' || v_CurFinal.APNO || '-' || v_CurFinal.SEQNO,replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                          
                             v_g_flag := '1';
                             v_o_flag := v_g_flag;
                           exit;
                     end if;
                 exception
                       When others then
                            dbms_output.put_line(RPAD('**Err:'||v_g_ProgName,85,'-')||'>>['||v_i_paycode||']');
                            dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
                            
 	                         	--修改log作法 by TseHua 20180419
                            sp_ba_recbatchjobdtl(BAJOBDTLID.NEXTVAL, v_i_bajobid, '1',
                                '產生月處理資料暫存紀錄檔資料發生錯誤-' ||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE,
                                replace(to_char(systimestamp, 'yyyyMMddHH24missxff'),'.',''));
                             v_g_flag := '1';
                             v_o_flag := v_g_flag;
                       end;
                 end Loop;
                 v_o_flag := v_g_flag;
            end;
            dbms_output.put_line(RPAD(v_g_ProgName,85,'-')||'>>['||v_i_paycode||']:'||v_g_rowCount);
        end;
    --Procedure sp_BA_genBAIssuData
End;
/

 
spool off
