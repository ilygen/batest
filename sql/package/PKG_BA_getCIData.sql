CREATE OR REPLACE Package BA.PKG_BA_getCIData
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_getCIData
    PURPOSE:         查詢CI承保系統被保險人基本資料(CIPB)、被保險人異動資料(CIPT)、
                     被保險人投保薪資計算資料(CIPG)

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2012/03/31  ChungYu Lin  Created this Package.

    NOTES:
    1.各 Function 及 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/
authid definer is
    v_g_errMsg               varChar2(4000);

    Procedure sp_BA_genBACIPBData (
        v_i_intyp               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_wagecount           in      number,
        v_i_idnno               in      varChar2,
        v_i_evtdate             in      varChar2
    );

    Procedure sp_BA_genBATEPBData (
        v_i_intyp               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_evtdate             in      varChar2
    );

    Procedure sp_BA_genBACIPTData (
        v_i_intyp               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_idkey               in      varChar2
    );

    Procedure sp_BA_genBACIPGData (
        v_i_intyp               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_ciid                in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_avgtyp              in      varChar2,
        v_i_getavgtyp           in      varChar2,
        v_i_idkey               in      varChar2
    );

    Procedure sp_BA_updBACIPBData (
        v_i_intyp               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_wagecount           in      number,
        v_i_idnno               in      varChar2,
        v_i_evtdate             in      varChar2,
        v_i_updtyp              in      varChar2
    );

    Procedure sp_BA_updBATEPBData (
        v_i_intyp               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_evtdate             in      varChar2,
        v_i_updtyp              in      varChar2
    );

    Procedure sp_BA_Get_CIPB (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_appdate             in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_evtdate             in      varChar2
    );

    Procedure sp_BA_Upd_CIPB (
         v_i_apno                in      varChar2,
         v_i_seqno               in      varChar2,
         v_i_appdate             in      varChar2,
         v_i_idnno               in      varChar2,
         v_i_evtdate             in      varChar2,
         v_i_updtyp              in      varChar2
    );

     Procedure INS_ERR_LOG (
         v_i_idn                 in      varChar2,
         v_i_intyp               in      varChar2,
         v_i_errmsg              in      varChar2
    );


    --  2017/08/01 Add By ChungYu 因計算同單位年資需求
    Procedure sp_BA_Get_SamItrmy (
        v_i_intyp               in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_ciid                in      varChar2,
        v_o_samty               out     varChar2,
        v_o_samtd               out     varChar2
    );

    PROCEDURE Get_AllData_TEST;
    PROCEDURE Get_EvtAFData_TEST;
    PROCEDURE Trans_BliAdmToBA_TEST;
    PROCEDURE Trans_APNOToBA_TEST;    --  2013/08/28 Add By ChungYu 勞保年金CIPB、CIPT、CIPG均增加Apno、 Seqno 兩欄位，並將資料轉入。

    PROCEDURE Update_AllSamty_TEST(
        v_i_intyp               in      varChar2
    );                                --  2017/08/01 Add By ChungYu 勞保老年年金更新被保險人同單位年資

    Procedure sp_BA_updHBEDMK (
        v_i_intyp               in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_brdte               in      varChar2,
        v_i_name                in      varChar2
    );

    PROCEDURE sp_BA_Upd_ADJYM;
    PROCEDURE CALL_Batch_TEST;
    PROCEDURE CALL_CIT01_TEST;
    PROCEDURE CALL_TET01_TEST;

End;
/

CREATE OR REPLACE Package Body BA.PKG_BA_getCIData
is

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_getCIData.sp_BA_genBACIPBData
        PURPOSE:         查詢CI承保系統被保險人基本資料(CIPB)、被保險人異動資料(CIPT)、
                         被保險人投保薪資計算資料(CIPG)。

        PARAMETER(IN):  *v_i_idnno         (varChar2)       --被保險人身分證字號


        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2012/03/31  ChungYu Lin  Created this Package.
        1.1   2024/01/09  William      依據BABAWEB-101，
                                       調修刪除CIPB的順序及參數
        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genBACIPBData (
        v_i_intyp               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_wagecount           in      number,
        v_i_idnno               in      varChar2,
        v_i_evtdate             in      varChar2
    ) is
        v_ciid                CI.CIPB.CIID%TYPE  := '';         -- 勞就保識別碼
        v_idkey               VARCHAR2(12)       := '';         -- 承保年資副程式回傳被保險人識別碼
        v_idnno               VARCHAR2(11)       := '';         -- 被保險人身分證字號
        v_bcout               CI.PL0126M0OUT%ROWTYPE;
        v_BMK                 VARCHAR2(10)       := '';         -- 領取給付註記
        v_POM                 VARCHAR2(2)        := '';         -- 個人欠費註記
        v_LIQDTE              CI.CIPB.LIQDTE%TYPE:= '';         -- 勞保給付年資起算日
        v_evtdate             VARCHAR2(8)        := '';         -- 事故日期
        v_MinAVGMYM           VARCHAR2(6)        := '';
        v_MaxEFDTE            VARCHAR2(8)        := '';
        v_SAMTY               CIPB.Samty%TYPE    := '';         -- 同單位年資-年
        v_SAMTD               CIPB.Samtd%TYPE    := '';         -- 同單位年資-日

     Cursor c_dataCur_CIPB is
              Select FTYP                   --保險類別
                    ,RANGE                  --資料區碼
                    ,CIID                   --勞就保識別碼
                    ,IDN                    --身分證號
                    ,IDNSEQ                 --身分證號序號
                    ,BRDTE                  --出生日期
                    ,NAME                   --姓名
                    ,SEX                    --性別
                    ,FMK                    --外籍註記
                    ,IVMK                   --調查註記
                    ,PRDTE                  --最初加保日期
                    ,LIQDTE                 --勞保給付年資起算日
                    ,EIQDTE                 --就保給付年資起算日
                    ,QMK                    --年資註記
                    ,HMK                    --身心障礙註記
                    ,CBMK                   --關鍵欄位曾變更註記
                    ,HDMK                   --待核註記
                    ,SBMK1                  --領取失能給付註記
                    ,SBMK2                  --領取老年給付註記
                    ,SBMK3                  --領取死亡給付註記
                    ,SBMK4                  --領取失蹤給付註記
                    ,SBMK5                  --領取紓困給付註記
                    ,UINMK                  --管制加保註記
                    ,POM1                   --職業工會個人欠費註記
                    ,POM2                   --漁會個人欠費註記
                    ,POM3                   --育嬰個人欠費註記
                    ,OBTYPE                 --軍公教養老給付
                    ,OBDTE                  --軍公教養老給付日期
                    ,OBMK                   --軍公教養老給付原因
                    ,KMK                    --個人僅加就保
                    ,YRCOMB                 --年資併計註記
                    ,CRE_USER               --建立使用者
                    ,CRE_SYSTP              --建立系統程式
                    ,CRE_DATETIME           --建立時間日期
                    ,UPD_USER               --修改使用者
                    ,UPD_SYSTP              --修改系統程式
                    ,UPD_DATETIME           --修改時間日期
                From CI.CIPB
               Where FTYP = v_i_intyp
                 And IDN = v_i_idnno
               Order By IDNSEQ;

   begin
                 --清除該身分證被保險人資料
                Delete from CIPB Where INTYP = v_i_intyp  And IDN like v_i_idnno ||'%'
                        And APNO = v_i_apno And SEQNO = v_i_seqno;
                --讀出保險別 v_i_intyp 的 CIPB同身分證被保險人資料，並依身分證號序號(IDNSEQ)排列
                for v_CurCIPB in c_dataCur_CIPB Loop
                 Begin
                     v_idnno := Rpad(v_CurCIPB.IDN,10,' ') || v_CurCIPB.IDNSEQ;           -- IDN 不足十碼要補空白至十碼  2012/04/25  遺屬提出
                     v_Ciid  := v_CurCIPB.CIID;
                     v_Idkey := '';

                     If v_i_intyp = 'L' Then
                         --回組原CIPB BMK 註記
                         v_BMK := NVL(v_CurCIPB.SBMK1,' ') || NVL(v_CurCIPB.SBMK2,' ') || NVL(v_CurCIPB.SBMK3,' ') ||
                                  NVL(v_CurCIPB.SBMK4,' ') || '     '|| NVL(v_CurCIPB.SBMK5,' ');

                         --回組原CIPB POM 註記                ** 2012/05/08 POM 空白等同於Null  **
                         IF Trim(v_CurCIPB.POM1) IS NOT NULL
                         AND Trim(v_CurCIPB.POM2) IS NOT NULL
                         THEN
                             v_POM := 'P';
                         ELSIF Trim(v_CurCIPB.POM1) IS NOT NULL
                         THEN
                             v_POM := 'R';
                         ELSIF Trim(v_CurCIPB.POM2) IS NOT NULL
                         THEN
                             v_POM := 'Y';
                         ELSE
                             v_POM := ' ';
                         END IF;
                         v_POM := v_POM || NVL(v_CurCIPB.POM3,' ');
                     End If;
                     --  2012/04/26  已領取失能要呼叫年資副程式，要帶入勞保給付年資起算日(LIQDTE)
                     v_LIQDTE := Null;
                     If (v_CurCIPB.SBMK1 = '2') Then
                         v_LIQDTE := v_CurCIPB.LIQDTE;
                     End If;

                     /* 2012/05/01 若傳入年資截止日大於系統日期，則年資截止日帶空白，老年提前送局提出 */
                     v_evtdate := v_i_evtdate;
                     If (v_i_evtdate > to_Char(Sysdate,'YYYYMMDD')) Then
                         v_evtdate := Null;
                     End if;


                     -- 呼叫平均薪資60個月併計

                     If v_i_intyp = 'L' Then
                        CI.PKG_CI_T01.CI_T01( v_CurCIPB.FTYP                --保險別
                                             ,v_Ciid                        --勞就保識別碼
                                             ,v_i_wagecount                 --計算均薪月數
                                             ,v_LIQDTE                      --年資起算日
                                             ,v_evtdate                     --年資截止日
                                             ,NULL                          --YNB 例外指定
                                             ,NULL                          --SMK 分項別
                                             ,'1'                           --TMK 選取異動註記
                                             ,NULL                          --WMK 老年計算均薪註記
                                          -- ,NULL      2018/02/08 Mark By ChugnYu  --DWCMK 重複加保投保薪資合併計算註記
                                             ,'A'                           --2018/02/08 DWCMK 重複加保投保薪資合併計算註記改為'A'-全併計
                                             ,NULL                          --RINOS 同單位保險證號
                                             ,v_Idkey);
                     Else
                        CI.PKG_CI_T01.CI_T01( v_CurCIPB.FTYP                --保險別
                                              ,v_Ciid                        --勞就保識別碼
                                              ,6                             --計算均薪月數
                                              ,v_LIQDTE                      --年資起算日
                                              ,v_evtdate                   --年資截止日
                                              ,NULL                          --YNB 例外指定
                                              ,NULL                          --SMK 分項別
                                              ,'1'                           --TMK 選取異動註記
                                              ,NULL                          --WMK 老年計算均薪註記
                                              ,'N'                           --DWCMK 重複加保投保薪資合併計算註記
                                              ,NULL                          --RINOS 同單位保險證號
                                              ,v_Idkey);
                     End if;


                     If v_Idkey > 0 then
                        SELECT * INTO v_bcout FROM CI.PL0126M0OUT WHERE IDKEY = v_Idkey;

                        INSERT INTO   CIPB ( INTYP
                                            ,IDN
                                            ,BRDTE
                                            ,NAME
                                            ,SEX
                                            ,FMK
                                            ,NOVMK
                                            ,EMK
                                            ,CMK
                                            ,IVMK
                                            ,PRDTE
                                            ,QMK
                                            ,BSCD
                                            ,CBMK
                                            ,BMK
                                            ,UINMK
                                            ,OFIMK
                                            ,POM
                                            ,KMK
                                            ,CVEPT
                                            ,ITRMY
                                            ,ITRMD
                                            ,DEPTY
                                            ,DEPTD
                                            ,OLDTY
                                            ,OLDTD
                                            ,AVGWG
                                            ,AVGM
                                            ,EFMK
                                            ,NITRMY
                                            ,NITRMM
                                            ,NOLDTY
                                            ,NOLDTM
                                            ,ITRMD_15
                                            ,HAVGWG
                                            ,HAVGM
                                            ,DWCMK
                                            ,MPMK
                                            ,PROCDATE
                                            ,HBEDMK
                                            ,HMK
                                            ,CIID
                                            ,APNO
                                            ,SEQNO
                                            ,HDMK                   --待核註記 2015/07/23 add by ChungYu 因BG註記新增
                                            )
                                   VALUES (  v_CurCIPB.FTYP
                                            ,v_idnno
                                            ,v_CurCIPB.BRDTE
                                            ,v_CurCIPB.NAME
                                            ,v_CurCIPB.SEX
                                            ,v_CurCIPB.FMK
                                            ,'' -- NOVMK
                                            ,'' -- EMK
                                            ,'' -- CMK
                                            ,v_CurCIPB.IVMK
                                            ,v_CurCIPB.LIQDTE
                                            ,v_CurCIPB.QMK
                                            ,'' -- BSCD
                                            ,v_CurCIPB.CBMK
                                            ,v_BMK -- BMK
                                            ,v_CurCIPB.UINMK
                                            ,'' --OFIMK
                                            ,v_POM --POM
                                            ,'' --KMK
                                            ,Decode(v_evtdate,Null,to_Char(Sysdate,'YYYYMMDD'),v_evtdate) --CVEPT
                                            ,NVL(v_BCOut.ITRMY, 0)
                                            ,NVL(v_BCOut.ITRMD, 0)
                                            ,NVL(v_BCOut.DEPTY, 0)
                                            ,NVL(v_BCOut.DEPTD, 0)
                                            ,NVL(v_BCOut.OLDTY, 0)
                                            ,NVL(v_BCOut.OLDTD, 0)
                                            ,NVL(v_BCOut.AVGWG, 0)
                                            ,v_BCOut.AVGM
                                            ,v_BCOut.EFMK
                                            ,NVL(v_BCOut.NITRMY , 0)
                                            ,NVL(v_BCOut.NITRMM , 0)
                                            ,NVL(v_BCOut.NOLDTY , 0)
                                            ,NVL(v_BCOut.NOLDTM , 0)
                                            ,NVL(v_BCOut.ITRMD15, 0)
                                            ,NVL(v_BCOut.HAVGWG , 0)
                                            ,v_BCOut.HAVGW
                                            ,v_BCOut.DWCMK -- DWCMK
                                            ,v_BCOut.MPMK
                                            ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                            ,v_BCOut.HBEDMK
                                            ,v_CurCIPB.HMK
                                            ,v_CurCIPB.CIID
                                            ,v_i_apno
                                            ,v_i_seqno
                                            ,v_CurCIPB.HDMK
                                          );


                           if ( v_i_intyp = 'L') Then
                               sp_BA_genBACIPGData(v_i_intyp,v_i_apno,v_i_seqno,v_CurCIPB.CIID,v_idnno,'1','2',v_idkey);     --產生被保險人投保薪資類別 1 投保薪資資料(CIPG)
                           End If;

                           -- 呼叫平均薪資60個月不併計
                           CI.PKG_CI_T01.CI_T01( v_CurCIPB.FTYP                --保險別
                                                ,v_Ciid                        --勞就保識別碼
                                                ,v_i_wagecount                 --計算均薪月數
                                                ,v_LIQDTE                      --年資起算日
                                                ,v_evtdate                     --年資截止日
                                                ,NULL                          --YNB 例外指定
                                                ,NULL                          --SMK 分項別
                                                ,'1'                           --TMK 選取異動註記
                                                ,NULL                          --WMK 老年計算均薪註記
                                                ,'N'                           --DWCMK 重複加保投保薪資合併計算註記
                                                ,NULL                          --RINOS 同單位保險證號
                                                ,v_Idkey);

                           sp_BA_genBACIPGData(v_i_intyp,v_i_apno,v_i_seqno,v_CurCIPB.CIID,v_idnno,'6','2',v_idkey);     --產生被保險人投保薪資類別 6 投保薪資資料(CIPG)

                           -- 呼叫平均薪資6個月不併計

                           CI.PKG_CI_T01.CI_T01( v_CurCIPB.FTYP                --保險別
                                                ,v_Ciid                        --勞就保識別碼
                                                ,6                             --計算均薪月數
                                                ,v_LIQDTE                      --年資起算日
                                                ,v_evtdate                   --年資截止日
                                                ,NULL                          --YNB 例外指定
                                                ,NULL                          --SMK 分項別
                                                ,'1'                           --TMK 選取異動註記
                                                ,NULL                          --WMK 老年計算均薪註記
                                                ,'N'                           --DWCMK 重複加保投保薪資合併計算註記
                                                ,NULL                          --RINOS 同單位保險證號
                                                ,v_Idkey);

                           sp_BA_genBACIPGData(v_i_intyp,v_i_apno,v_i_seqno,v_CurCIPB.CIID,v_idnno,'5','1',v_idkey);     --產生被保險人投保薪資類別 5 投保薪資資料(CIPG)


                       CI.PKG_CI_T01.CI_T01( v_CurCIPB.FTYP                --保險別
                                            ,v_Ciid                        --勞就保識別碼
                                            ,v_i_wagecount                 --計算均薪月數
                                            ,NULL                          --年資起算日
                                            ,NULL                          --年資截止日
                                            ,NULL                          --YNB 例外指定
                                            ,NULL                          --SMK 分項別
                                            ,'1'                           --TMK 選取異動註記
                                            ,NULL                          --WMK 老年計算均薪註記
                                         -- ,NULL      2018/02/08 Mark By ChugnYu  --DWCMK 重複加保投保薪資合併計算註記
                                            ,'A'                           --2018/02/08 DWCMK 重複加保投保薪資合併計算註記改為'A'-全併計
                                            ,NULL                          --RINOS 同單位保險證號
                                            ,v_Idkey);

                        sp_BA_genBACIPTData(v_i_intyp,v_i_apno,v_i_seqno, v_idnno, v_idkey);       --產生被保險人承保異動資料(CIPT)

                        -- 2012/09/06 CIPB新增「投保薪資調整年月_ADJYM」、「逕調註記_ADJMK」欄位，並於新帶事故者
                        -- CIPB、CIPT、CIPG 最後更新欄位值。
                        --  經測試分段Query 被保險人 CIPT、CIPG 較下SubQuery 整段包一起更新，SQL 效能差很多，
                        --  故將更新勞保年金系統所使用的 CIPB 「投保薪資調整年月_ADJYM」、「逕調註記_ADJMK」SQL，分段執行

                        If (v_i_intyp = 'L') Then
                            v_MinAVGMYM := NULL;
                            v_MaxEFDTE  := NULL;

                            --  讀取被保險人投保薪資類別為1之最小投保年月
                            SELECT NVL(MIN(D.AVGMYM), '000000') INTO v_MinAVGMYM
                              FROM CIPG D
                             WHERE D.INTYP = 'L'
                               AND D.IDN = v_idnno
                               AND D.AVGTYP = '1'
                               AND D.AVGMYM <> '191101'
                               AND ROWNUM = 1;

                            --  讀取被保險人CIPT中，異動別為4、3，離該投保年月最近但小於等於該投保年月之生效日期年月
                            SELECT MAX(C.EFDTE) INTO v_MaxEFDTE
                              FROM CIPT C
                             WHERE C.INTYP = 'L'
                               AND C.IDN = v_idnno
                               AND (C.TXCD = '4' OR C.TXCD = '3')
                               AND SUBSTR(C.EFDTE,1,6) <= v_MinAVGMYM;

                            IF v_MaxEFDTE IS NOT NULL THEN

                               -- 讀取被保險人CIPT中，異動別為4、3，離該投保年月最近但小於等於該投保年月之生效日期年月帶入「投保薪資調整年月」欄
                               -- 讀取該筆CIPT之處理人員代號_ STAFF欄，當該代號前2碼=66者，「逕調註記」欄帶入”Y”，否則為null
                               UPDATE CIPB A
                                  SET (A.ADJYM, A.ADJMK) = (SELECT SUBSTR(B.EFDTE, 1, 6),
                                                                   DECODE(B.STAFF, '00000', 'Y',
                                                                                    DECODE(SUBSTR(B.STAFF, 1, 2), '66', 'Y', NULL)) AS "STAFF"
                                                              FROM CIPT B
                                                             WHERE B.INTYP = 'L'
                                                               AND B.IDN =  v_idnno
                                                               AND B.EFDTE = v_MaxEFDTE
                                                               AND (B.TXCD = '4' OR B.TXCD = '3')
                                                               AND ROWNUM = 1)
                                WHERE A.INTYP = 'L'
                                  AND A.IDN = v_idnno;

                            ELSE
                               UPDATE CIPB A
                                  SET A.ADJYM = NULL,
                                      A.ADJMK = NULL
                                WHERE A.INTYP = 'L'
                                  AND A.IDN = v_idnno;
                            END IF;

                            /* 2017/07/27 因計算老年差額金需求，新增計算同單位年資並更新至BA.CIPB中
                            */
                            If (SUBSTR(v_i_apno,1,1) = 'L') And (NVL(v_BCOut.ITRMY, 0) >= 25) Then
                                v_SAMTY := '';
                                v_SAMTD := '';
                                sp_BA_Get_SamItrmy(v_i_intyp,v_i_idnno,v_Ciid,v_SAMTY,v_SAMTD);

                               UPDATE CIPB S
                                  SET S.Samty = v_SAMTY,
                                      S.Samtd = v_SAMTD
                                WHERE S.INTYP = 'L'
                                  AND S.IDN = v_idnno
                                  And S.Ciid = v_Ciid
                                  And S.APNO = v_i_apno
                                  And S.SEQNO = v_i_seqno;
                              End if;
                            /* 2017/07/27 因計算老年差額金需求，新增計算同單位年資並更新至BA.CIPB中
                            */
                         End if;

                     End if;

                     /*DBMS_OUTPUT.PUT_LINE('NAME =' || v_CurCIPB.NAME || ',CIID=' || v_CurCIPB.CIID);
                     DBMS_OUTPUT.PUT_LINE('v_ciid=' || v_Ciid || ',idkey=' || v_Idkey);*/

                 exception
                       When others then
                            INS_ERR_LOG(v_idnno,v_i_intyp,'Err:sp_BA_genBACIPBData');
                            dbms_output.put_line('**Err:PKG_BA_getCIData-sp_BA_genBACIPBData------->>[ L:'||v_i_idnno||' ]');
                            dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
                       end;
                 end Loop;


            dbms_output.put_line('  PKG_BA_getCIData-sp_BA_genBACIPBData----------->>[ '||v_i_idnno||' ]');
        end;
    --Procedure sp_BA_genBACIPBData

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_getCIData.sp_BA_genBATEPBData
        PURPOSE:         查詢CI承保系統被保險人基本資料(CIPB)、被保險人異動資料(CIPT)、
                         被保險人投保薪資計算資料(CIPG)。

        PARAMETER(IN):  *v_i_idnno         (varChar2)       --被保險人身分證字號


        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
       .0   2012/03/31  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genBATEPBData (
        v_i_intyp               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_evtdate             in      varChar2
    ) is
        v_ciid                TE.TEPB.TEID%TYPE := '';         -- 勞就保識別碼
        v_idkey               VARCHAR2(12)      := '';         -- 承保年資副程式回傳被保險人識別碼
        v_idnno               VARCHAR2(11)      := '';         -- 被保險人身分證字號
        v_bcout               CI.PL0126M0OUT%ROWTYPE;

     Cursor c_dataCur_TEPB is
              Select TEID                   --勞就保識別碼
                    ,IDN                    --身分證號
                    ,IDNSEQ                 --身分證號序號
                    ,BRDTE                  --出生日期
                    ,NAME                   --姓名
                    ,SEX                    --性別
                    ,IVMK                   --調查註記
                    ,PRDTE                  --最初加保日期
                    ,HMK                    --身心障礙註記
                    ,CBMK                   --關鍵欄位曾變更註記
                    ,HDMK                   --待核註記
                    ,SBMK1                  --領取失能給付註記
                    ,SBMK3                  --領取死亡給付註記
                    ,UINMK                  --管制加保註記
                    ,POM                    --個人欠費註記
                    ,CRE_USER               --建立使用者
                    ,CRE_SYSTP              --建立系統程式
                    ,CRE_DATETIME           --建立時間日期
                    ,UPD_USER               --修改使用者
                    ,UPD_SYSTP              --修改系統程式
                    ,UPD_DATETIME           --修改時間日期
                From TE.TEPB
               Where IDN = v_i_idnno
               Order By IDNSEQ;

   begin

           -- begin
                --讀出保險別 v_i_intyp 的 CIPB同身分證被保險人資料，並依身分證號序號(IDNSEQ)排列

                for v_CurTEPB in c_dataCur_TEPB Loop
                 Begin
                     v_idnno := v_CurTEPB.IDN || v_CurTEPB.IDNSEQ;
                     v_Ciid  := v_CurTEPB.TEID;
                     v_Idkey := '';



                     --清除該身分證被保險人資料
                     Delete from CIPB Where INTYP = v_i_intyp  And IDN = v_idnno
                                        And APNO = v_i_apno And SEQNO = v_i_seqno;
                     -- v_Idn   := v_CurCIPB.IDN || v_CurCIPB.IDNSEQ;
                     -- DBMS_OUTPUT.PUT_LINE('v_Idn=' || v_Idn );

                     -- 呼叫平均薪資6個月併計

                     TE.PKG_TE_T01.TE_T01( v_i_intyp                     --保險別
                                          ,v_Ciid                        --勞就保識別碼
                                          ,6                             --計算均薪月數
                                          ,null                          --年資起算日
                                          ,v_i_evtdate                   --年資截止日
                                          ,NULL                          --YNB 例外指定
                                          ,NULL                          --SMK 分項別
                                          ,'1'                           --TMK 選取異動註記
                                          ,NULL                          --WMK 老年計算均薪註記
                                          ,NULL                          --DWCMK 重複加保投保薪資合併計算註記
                                          ,NULL                          --RINOS 同單位保險證號
                                          ,v_Idkey);

                     SELECT * INTO v_bcout FROM CI.PL0126M0OUT WHERE IDKEY = v_Idkey;

                     INSERT INTO   CIPB ( INTYP
                                         ,IDN
                                         ,BRDTE
                                         ,NAME
                                         ,SEX
                                         ,FMK
                                         ,NOVMK
                                         ,EMK
                                         ,CMK
                                         ,IVMK
                                         ,PRDTE
                                         ,QMK
                                         ,BSCD
                                         ,CBMK
                                         ,BMK
                                         ,UINMK
                                         ,OFIMK
                                         ,POM
                                         ,KMK
                                         ,CVEPT
                                         ,ITRMY
                                         ,ITRMD
                                         ,DEPTY
                                         ,DEPTD
                                         ,OLDTY
                                         ,OLDTD
                                         ,AVGWG
                                         ,AVGM
                                         ,EFMK
                                         ,NITRMY
                                         ,NITRMM
                                         ,NOLDTY
                                         ,NOLDTM
                                         ,ITRMD_15
                                         ,HAVGWG
                                         ,HAVGM
                                         ,DWCMK
                                         ,MPMK
                                         ,PROCDATE
                                         ,HBEDMK
                                         ,HMK
                                         ,CIID
                                         ,APNO
                                         ,SEQNO)
                                VALUES (  v_i_intyp
                                         ,v_idnno
                                         ,v_CurTEPB.BRDTE
                                         ,v_CurTEPB.NAME
                                         ,v_CurTEPB.SEX
                                         ,'' --v_CurTEPB.FMK
                                         ,'' -- NOVMK
                                         ,'' -- EMK
                                         ,'' -- CMK
                                         ,v_CurTEPB.IVMK
                                         ,v_CurTEPB.PRDTE
                                         ,'' --v_CurCIPB.QMK
                                         ,'' -- BSCD
                                         ,v_CurTEPB.CBMK
                                         ,''-- BMK
                                         ,v_CurTEPB.UINMK
                                         ,'' --OFIMK
                                         ,v_CurTEPB.POM --POM
                                         ,'' --KMK
                                         ,Decode(v_i_evtdate,Null,to_Char(Sysdate,'YYYYMMDD'),v_i_evtdate) --CVEPT
                                         ,NVL(v_BCOut.ITRMY, 0)
                                         ,NVL(v_BCOut.ITRMD, 0)
                                         ,NVL(v_BCOut.DEPTY, 0)
                                         ,NVL(v_BCOut.DEPTD, 0)
                                         ,NVL(v_BCOut.OLDTY, 0)
                                         ,NVL(v_BCOut.OLDTD, 0)
                                         ,NVL(v_BCOut.AVGWG, 0)
                                         ,v_BCOut.AVGM
                                         ,v_BCOut.EFMK
                                         ,NVL(v_BCOut.NITRMY , 0)
                                         ,NVL(v_BCOut.NITRMM , 0)
                                         ,NVL(v_BCOut.NOLDTY , 0)
                                         ,NVL(v_BCOut.NOLDTM , 0)
                                         ,NVL(v_BCOut.ITRMD15, 0)
                                         ,NVL(v_BCOut.HAVGWG , 0)
                                         ,v_BCOut.HAVGW
                                         ,v_BCOut.DWCMK -- DWCMK
                                         ,v_BCOut.MPMK
                                         ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                         ,v_BCOut.HBEDMK
                                         ,v_CurTEPB.HMK
                                         ,v_CurTEPB.TEID
                                         ,v_i_apno
                                         ,v_i_seqno
                                       );

                     sp_BA_genBACIPTData(v_i_intyp,v_i_apno,v_i_seqno, v_idnno, v_idkey);                 --產生被保險人承保異動資料(CIPT)
                     sp_BA_genBACIPGData(v_i_intyp,v_i_apno,v_i_seqno, v_CurTEPB.TEID, v_idnno,'1','1',v_idkey);          --產生被保險人投保薪資類別 1 投保薪資資料(CIPG)


                     /*DBMS_OUTPUT.PUT_LINE('NAME =' || v_CurCIPB.NAME || ',CIID=' || v_CurCIPB.CIID);
                     DBMS_OUTPUT.PUT_LINE('v_ciid=' || v_Ciid || ',idkey=' || v_Idkey);*/

                 exception
                       When others then
                            INS_ERR_LOG(v_idnno,v_i_intyp,'Err:sp_BA_genBATEPBData');
                            --dbms_output.put_line('**Err:PKG_BA_getCIData-sp_BA_genBATEPBData------->>[ L:'||v_i_idnno||' ]');
                            dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
                       end;
                 end Loop;


            --dbms_output.put_line('  PKG_BA_getCIData-sp_BA_genBATEPBData----------->>[ '||v_i_idnno||' ]');
        end;
    --Procedure sp_BA_genBACIPBData

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_getCIData.sp_BA_genBACIPTData
        PURPOSE:         查詢被保險人異動資料(CIPT)。

        PARAMETER(IN):  *v_i_idnno         (varChar2)       --被保險人身分證字號
                        *v_i_idkey         (varChar2)       --被保險人識別代號


        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
       .0   2012/03/31  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/

    Procedure sp_BA_genBACIPTData (
        v_i_intyp               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_idkey               in      varChar2
    ) is

    v_sidmk2   VARCHAR2(1);
    v_sidmk3   VARCHAR2(1);
    v_sidmk4   VARCHAR2(1);
    v_sidmk    VARCHAR2(10);
    v_dept     VARCHAR2(1);
    v_count    NUMBER;
    v_seqno    NUMBER;
    v_addrow   NUMBER;

     Cursor c_dataCur_CIPT is
              Select SEQNO                  --資料序
                    ,INTYP                  --保險別
                    ,IDNO                   --被保險人身分證號
                    ,LINENO                 --降序行次
                    ,SDTE                   --加保日
                    ,UNO                    --保險證號
                    ,UNOCK                  --保險證號檢查碼
                    ,TXCD                   --異動代號
                    ,EFDTE                  --生效日期
                    ,WAGE                   --投保薪資
                    ,DEPT                   --工作部門註記
                    ,COUID                  --續保身分
                    ,CELMK                  --取消資格
                    ,BSMN                   --計費註記
                    ,STAFF                  --處理人員代號
                    ,OWMK                   --墊償身分註記
                    ,LIMK                   --普保適用註記
                    ,OIMK                   --職保適用註記
                    ,EIMK                   --就保適用註記
                    ,RATEMK                 --負擔比例註記
                    ,SPCMK                  --特殊身分註記
                    ,TSMK                   --異動原因註記
                    ,NPRMK                  --不退費註記
                    ,FILL                   --退保工作部門
                    ,EDATE                  --退保日
                    ,RANGE                  --資料區碼
                    ,ACNO                   --單位個人戶號
                    ,CIID                   --生效註記
                    ,LADTE                  --最近一次加保日期
                    ,LEDTE                  --最近一次退保日期
                    ,BSCD                   --生效註記
                From CI.PL0126M0PT
               Where IDKEY = v_i_idkey
               ORDER BY LINENO DESC;

   begin
        v_count  := 0;
        v_seqno  := 0;
        v_addrow := 0;

         --清除該身分證被保險人CIPT資料
         Delete from CIPT
          Where INTYP = v_i_intyp
            And IDN = v_i_idnno
            And APNO = v_i_apno
            And APSEQNO = v_i_seqno;

         for v_CurCIPT in c_dataCur_CIPT Loop
         Begin
              --原out生效小至大排序,下端年資副程式大至小
              IF c_dataCur_CIPT%ROWCOUNT = 1
              THEN
                 v_count := v_CurCIPT.SEQNO;
              END IF;
              v_seqno := v_count - v_CurCIPT.SEQNO + 1;

              --LIMK,OIMK,EIMK組回SIDMK2
              v_sidmk2 := NULL;
              IF v_CurCIPT.limk IS NOT NULL
              AND v_CurCIPT.oimk IS NOT NULL
              THEN
                v_sidmk2 := ' ';
              ELSIF v_CurCIPT.limk IS NOT NULL
              THEN
                v_sidmk2 := 'C';
              ELSIF v_CurCIPT.oimk IS NOT NULL
              THEN
                v_sidmk2 := 'O';
              ELSIF v_CurCIPT.eimk = 'Y'
              THEN
                v_sidmk2 := 'E';
              END IF;
              --EIMK'Y'=>' '其餘EIMK
              v_sidmk3 := NULL;
              IF v_CurCIPT.eimk = 'Y'
              THEN
                v_sidmk3 := ' ';
              ELSE
                v_sidmk3 := v_CurCIPT.eimk;
              END IF;
              --RATEMK,SPCMK組回SIDMK4
              v_sidmk4 := NULL;
              IF v_CurCIPT.ratemk IS NOT NULL
              THEN
                v_sidmk4 := v_CurCIPT.RATEMK;
              ELSIF v_CurCIPT.spcmk IS NOT NULL
              THEN
                v_sidmk4 := v_CurCIPT.spcmk;
              END IF;
              v_sidmk := NVL(v_CurCIPT.owmk,' ') || NVL(v_sidmk2,' ') || NVL(v_sidmk3,' ') ||
                         NVL(v_sidmk4,' ') || '      ';

              -- CELMK>COUID>T01DEPT組回DEPT
              IF v_CurCIPT.celmk IS NOT NULL
              THEN
                v_dept := v_CurCIPT.celmk;
              ELSIF v_CurCIPT.couid IS NOT NULL
              THEN
                IF v_CurCIPT.couid IN ('4','5')
                THEN
                  v_dept := '4';
                ELSE
                  v_dept := v_CurCIPT.couid;
                END IF;
              ELSE
                v_dept := v_CurCIPT.dept;
              END IF;

             INSERT INTO   CIPT ( INTYP
                                 ,IDN
                                 ,UNO
                                 ,UNOCK
                                 ,SEQNO
                                 ,TXCD
                                 ,EFDTE
                                 ,WAGE
                                 ,DEPT
                                 ,BSMN
                                 ,STAFF
                                 ,SIDMK
                                 ,TSMK
                                 ,NRPMK
                                 ,FILL
                                 ,PRODTE
                                 ,APNO
                                 ,APSEQNO
                                 )
                        VALUES (  v_CurCIPT.INTYP
                                 ,v_i_idnno
                                 ,v_CurCIPT.UNO
                                 ,v_CurCIPT.UNOCK
                                 ,v_seqno+v_addrow
                                 ,v_CurCIPT.TXCD
                                 ,v_CurCIPT.EFDTE
                                 ,v_CurCIPT.WAGE
                                 ,v_dept
                                 ,v_CurCIPT.BSMN
                                 ,v_CurCIPT.STAFF
                                 ,v_sidmk
                                 ,v_CurCIPT.TSMK
                                 ,v_CurCIPT.NPRMK
                                 ,v_CurCIPT.FILL
                                 ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 ,v_i_apno
                                 ,v_i_seqno
                               );

               --  若退保日不為NUll，則新增一筆異動註記(TXCD=2)的異動紀錄
               If Trim(v_CurCIPT.EDATE) Is Not Null Then
                    v_addrow := v_addrow + 1;

                    -- CELMK>COUID>T01DEPT組回DEPT
               -- 2012/04/18 PL0126M0PT  取消資格欄位為(FILL)
                    v_dept := NUll;
                    IF v_CurCIPT.fill IS NOT NULL
                    THEN
                      v_dept := v_CurCIPT.fill;
                    ELSIF v_CurCIPT.couid IS NOT NULL
                    THEN
                      IF v_CurCIPT.couid IN ('4','5')
                      THEN
                        v_dept := '4';
                      ELSE
                        v_dept := v_CurCIPT.couid;
                      END IF;
                    ELSE
                     v_dept := v_CurCIPT.dept;
                    END IF;
               -- 2012/04/18 PL0126M0PT  取消資格欄位為(FILL)

                    INSERT INTO   CIPT ( INTYP
                                         ,IDN
                                         ,UNO
                                         ,UNOCK
                                         ,SEQNO
                                         ,TXCD
                                         ,EFDTE
                                         ,WAGE
                                         ,DEPT
                                         ,BSMN
                                         ,STAFF
                                         ,SIDMK
                                         ,TSMK
                                         ,NRPMK
                                         ,FILL
                                         ,PRODTE
                                         ,APNO
                                         ,APSEQNO
                                         )
                                VALUES (  v_CurCIPT.INTYP
                                         ,v_i_idnno
                                         ,v_CurCIPT.UNO
                                         ,v_CurCIPT.UNOCK
                                         ,v_seqno+v_addrow
                                         ,'2'
                                         ,v_CurCIPT.EDATE
                                         ,v_CurCIPT.WAGE
                                         ,v_dept
                                         ,v_CurCIPT.BSMN
                                         ,v_CurCIPT.STAFF
                                         ,v_sidmk
                                         ,v_CurCIPT.TSMK
                                         ,v_CurCIPT.NPRMK
                                         ,v_CurCIPT.FILL
                                         ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                         ,v_i_apno
                                         ,v_i_seqno
                                       );
               End if;

         exception
               When others then
                    dbms_output.put_line('**Err:PKG_BA_getCIData-sp_BA_genBACIPTData------->>[ '||v_i_idnno||' ]');
                    dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
               end;
         end Loop;
         dbms_output.put_line('  PKG_BA_getCIData-sp_BA_genBACIPTData----------->>[ '||v_i_idnno||' ]');
 end;
    --Procedure sp_BA_genBACIPTData

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_getCIData.sp_BA_genBACIPGData
        PURPOSE:         查詢被保險人異動資料(CIPT)。

        PARAMETER(IN):  *v_i_intyp         (varChar2)       --保險別
                        *v_i_idnno         (varChar2)       --被保險人身分證號
                        *v_i_avgtyp        (varChar2)       --投保薪資類別
                        *v_i_getavgtyp     (varChar2)       --取得年資副程式投保薪資類別(1：年資截止日最近6個月or36個月，
                                                                                         2：被保險人投保最高6個月or60個月)
                        *v_i_idkey         (varChar2)       --被保險人識別代號

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
       .0   2012/03/31  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_genBACIPGData (
        v_i_intyp               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_ciid                in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_avgtyp              in      varChar2,
        v_i_getavgtyp           in      varChar2,
        v_i_idkey               in      varChar2
    ) is

     Cursor c_dataCur_CIPG is
              Select AVGTYP                   --投保薪資類別
                    ,AVGMYM                   --投保年月
                    ,AVGWG                    --月投保薪資
                    ,DWMK                     -- 2018/07/18 Add By ChungYu 新增雙薪註記欄位
                From CI.PL0126M0WAGE
               Where IDKEY = v_i_idkey
                 And AVGTYP = v_i_getavgtyp
               ORDER BY AVGMYM;

   begin

         --清除該身分證被保險人CIPG資料
         Delete from CIPG
          Where INTYP = v_i_intyp
            And IDN = v_i_idnno
            And AVGTYP = v_i_avgtyp
            And APNO = v_i_apno
            And SEQNO = v_i_seqno;

         for v_CurCIPG in c_dataCur_CIPG Loop
         Begin

             INSERT INTO   CIPG ( INTYP
                                 ,IDN
                                 ,AVGTYP
                                 ,AVGMYM
                                 ,AVGWG
                                 ,PRODTE
                                 ,APNO
                                 ,SEQNO
                                 ,CIID
                                 ,DWMK)         -- 2018/07/18 Add By ChungYu 新增雙薪註記欄位
                        VALUES (  v_i_intyp
                                 ,v_i_idnno
                                 ,v_i_avgtyp
                                 ,v_CurCIPG.AVGMYM
                                 ,v_CurCIPG.AVGWG
                                 ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                 ,v_i_apno
                                 ,v_i_seqno
                                 ,v_i_ciid
                                 ,v_CurCIPG.DWMK-- 2018/07/18 Add By ChungYu 新增雙薪註記欄位
                               );

         exception
               When others then
                    dbms_output.put_line('**Err:PKG_BA_getCIData-sp_BA_genBACIPGData------->>[ '||v_i_idnno||' ]');
                    dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
               end;
         end Loop;
         dbms_output.put_line('  PKG_BA_getCIData-sp_BA_genBACIPGData----------->>[ '||v_i_idnno||' ]');
 end;
    --Procedure sp_BA_genBACIPGData

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_getCIData.sp_BA_updBACIPBData
        PURPOSE:         查詢CI承保系統更新被保險人基本資料(CIPB)之領取給付註記(BMK)及欠費註記(POM)、
                         及被保險人異動資料(CIPT)，失能年金使用

        PARAMETER(IN):  *v_i_intyp         (varChar2)       --保險別
                        *v_i_idnno         (varChar2)       --被保險人身分證字號

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
       .0   2012/04/02  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_updBACIPBData (
        v_i_intyp               in      varChar2,
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_wagecount           in      number,
        v_i_idnno               in      varChar2,
        v_i_evtdate             in      varChar2,
        v_i_updtyp              in      varChar2

    ) is
        v_ciid                CIPB.CIID%TYPE    := '';         -- 勞就保識別碼
        v_idkey               VARCHAR2(12)      := '';         -- 承保年資副程式回傳被保險人識別碼
        v_idnno               VARCHAR2(11)      := '';         -- 被保險人身分證字號
--        v_bcout               CI.PL0126M0OUT%ROWTYPE;
        v_IVMK                VARCHAR2(10)      := '';         -- 領取給付註記
        v_POM                 VARCHAR2(2)       := '';         -- 個人欠費註記
        v_BMK                 VARCHAR2(10)      := '';         -- 領取給付註記
        v_UINMK               VARCHAR2(1)       := '';         -- 管制加保註記

    --  讀取  BA CIPB 被保險人基本資料
    --  2012/05/22  給付處詹小姐修改更新 BA CIPB 被保險人基本資料條件
    --  修改為先以給付主檔IDN讀取BA CIPB 取出每一筆CIPB 被保險人資料 CIID
    --  再以CIID 讀取 CI CIPB 被保險人基本資料，更新BA CIPB 相關欄位。
     Cursor c_dataCur_baCIPB is
              Select INTYP                   --保險類別
                    ,CIID                    --勞就保識別碼
                    ,IDN                     --身分證號
                    ,NAME                    --姓名
                From CIPB
               Where INTYP = v_i_intyp
                 And IDN LIKE v_i_idnno || '%'
                 And APNO = v_i_apno
                 And SEQNO = v_i_seqno
               Order By IDN;

     --  讀取  CI CIPB 被保險人基本資料   2012/05/22  給付處詹小姐修改更新 BA CIPB 被保險人基本資料條件
     Cursor c_dataCur_CIPB is
              Select FTYP                   --保險類別
                    ,CIID                   --勞就保識別碼
                    ,IDN                    --身分證號
                    ,IDNSEQ                 --身分證號序號
                    ,NAME                   --姓名
                    ,IVMK                   --調查註記
                    ,SBMK1                  --領取失能給付註記
                    ,SBMK2                  --領取老年給付註記
                    ,SBMK3                  --領取死亡給付註記
                    ,SBMK4                  --領取失蹤給付註記
                    ,SBMK5                  --領取紓困給付註記
                    ,POM1                   --職業工會個人欠費註記
                    ,POM2                   --漁會個人欠費註記
                    ,POM3                   --育嬰個人欠費註記
                    ,UINMK                  --管制加保註記
                    ,HDMK                   --待核註記 2015/07/23 add by ChungYu 因BG註記新增
                From CI.CIPB
               Where FTYP = v_i_intyp
                 And CIID = v_ciid
               Order By IDNSEQ;

   begin

       -- 讀出各保險別 BA CIPB同身分證被保險人資料，並依身分證號(IDN)排列
       For v_CurbaCIPB in c_dataCur_baCIPB Loop
         begin
              v_Ciid  := v_CurbaCIPB.CIID;
              v_idnno := v_CurbaCIPB.IDN;
              v_Idkey := '';

              If trim(v_Ciid) IS Not Null Then
                -- 以CIID讀出各保險別CI CIPB同身分證被保險人資料，並更新BA CIPB 資料
                For v_CurCIPB in c_dataCur_CIPB Loop
                  Begin

                     --回組原CIPB BMK 註記
                     -- 2012/05/18 同步CI CIPB BMK
                     v_BMK := NVL(v_CurCIPB.SBMK1,' ') || NVL(v_CurCIPB.SBMK2,' ') || NVL(v_CurCIPB.SBMK3,' ') ||
                              NVL(v_CurCIPB.SBMK4,' ') || '     '|| NVL(v_CurCIPB.SBMK5,' ');

                     -- 2012/05/18 同步CI CIPB UINMK
                     v_UINMK:= v_CurCIPB.UINMK;
                     v_IVMK := v_CurCIPB.IVMK;

                     --回組原CIPB POM 註記               ** 2012/05/08 POM 空白等同於Null  **
                     IF Trim(v_CurCIPB.POM1) IS NOT NULL
                     AND Trim(v_CurCIPB.POM2) IS NOT NULL
                     THEN
                         v_POM := 'P';
                     ELSIF Trim(v_CurCIPB.POM1) IS NOT NULL
                     THEN
                         v_POM := 'R';
                     ELSIF Trim(v_CurCIPB.POM2) IS NOT NULL
                     THEN
                         v_POM := 'Y';
                     ELSE
                         v_POM := ' ';
                     END IF;
                     v_POM := v_POM || NVL(v_CurCIPB.POM3,' ');

                     -- 更新CIPB領取給付註記(BMK)及欠費註記(POM)
                     Update CIPB
                        Set IVMK = v_IVMK,
                            POM  = v_POM,
                            NAME = v_CurCIPB.NAME,                          -- 2012/04/23  給付處詹小姐新增
                            BMK  = v_BMK,                                   -- 2012/05/18  CI.CIPB.BMK   同步
                            UINMK= v_UINMK,                                 -- 2012/05/18  CI.CIPB.UINMK 同步
                            HDMK = v_CurCIPB.HDMK                           --待核註記 2015/07/23 add by ChungYu 因BG註記新增
                      Where INTYP = v_i_intyp
                        And CIID  = v_ciid
                        And APNO = v_i_apno
                        And SEQNO = v_i_seqno;

                     if v_i_updtyp = '2' then

                        -- 呼叫平均薪資60個月併計
                        CI.PKG_CI_T01.CI_T01( v_CurCIPB.FTYP                --保險別
                                             ,v_Ciid                        --勞就保識別碼
                                             ,v_i_wagecount                 --計算均薪月數
                                             ,null                          --年資起算日
                                             ,v_i_evtdate                   --年資截止日
                                             ,NULL                          --YNB 例外指定
                                             ,NULL                          --SMK 分項別
                                             ,NULL                          --TMK 選取異動註記
                                             ,NULL                          --WMK 老年計算均薪註記
                                          -- ,NULL      2018/02/08 Mark By ChugnYu  --DWCMK 重複加保投保薪資合併計算註記
                                             ,'A'                           --2018/02/08 DWCMK 重複加保投保薪資合併計算註記改為'A'-全併計
                                             ,NULL                          --RINOS 同單位保險證號
                                             ,v_Idkey);

                        sp_BA_genBACIPTData(v_i_intyp, v_i_apno, v_i_seqno,v_idnno, v_idkey);               --產生被保險人承保異動資料(CIPT)
                     end if;

                     exception
                       When others then
                            INS_ERR_LOG(v_i_idnno,v_i_intyp,'Err:sp_BA_updBACIPBData');
                            dbms_output.put_line('**Err:PKG_BA_getCIData-sp_BA_updBACIPBData------->>['||v_i_intyp ||':'||v_i_idnno||' ]');
                            dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
                       end;
                 end Loop;
              end if;
            exception
              When others then
                   INS_ERR_LOG(v_i_idnno,v_i_intyp,'Err:sp_BA_updBACIPBData');
                   dbms_output.put_line('**Err:PKG_BA_getCIData-sp_BA_updBACIPBData------->>['||v_i_intyp ||':'||v_i_idnno||' ]');
                   dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
              end;
       end Loop;
           dbms_output.put_line('  PKG_BA_getCIData-sp_BA_updBACIPBData----------->>[ '||v_i_idnno||' ]');
   end;
   --Procedure sp_BA_updBACIPBData

  /********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            PKG_BA_getCIData.sp_BA_updBATEPBData
      PURPOSE:         查詢CI承保系統更新被保險人基本資料(CIPB)之領取給付註記(BMK)及欠費註記(POM)、
                       及被保險人異動資料(CIPT)，失能年金使用

      PARAMETER(IN):  *v_i_intyp         (varChar2)       --保險別
                      *v_i_idnno         (varChar2)       --被保險人身分證字號

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
     .0   2012/04/02  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/
  Procedure sp_BA_updBATEPBData (
      v_i_intyp               in      varChar2,
      v_i_apno                in      varChar2,
      v_i_seqno               in      varChar2,
      v_i_idnno               in      varChar2,
      v_i_evtdate             in      varChar2,
      v_i_updtyp              in      varChar2

  ) is
      v_ciid                TE.TEPB.TEID%TYPE := '';         -- 勞就保識別碼
      v_idkey               VARCHAR2(12)      := '';         -- 承保年資副程式回傳被保險人識別碼
      v_idnno               VARCHAR2(11)      := '';         -- 被保險人身分證字號

    --  讀取  BA CIPB 被保險人基本資料
    --  2012/05/22  給付處詹小姐修改更新 BA CIPB 被保險人基本資料條件
    --  修改為先以給付主檔IDN讀取BA CIPB 取出每一筆CIPB 被保險人資料 CIID
    --  再以CIID 讀取 CI CIPB 被保險人基本資料，更新BA CIPB 相關欄位。
       Cursor c_dataCur_baCIPB is
              Select INTYP                   --保險類別
                    ,CIID                    --勞就保識別碼
                    ,IDN                     --身分證號
                    ,NAME                    --姓名
                From CIPB
               Where INTYP = 'F'
                 And IDN LIKE v_i_idnno || '%'
                 And APNO = v_i_apno
                 And SEQNO = v_i_seqno
               Order By IDN;

   --  讀取  TE TEPB 被保險人基本資料   2012/05/22  給付處詹小姐修改更新 BA CIPB 被保險人基本資料條件
      Cursor c_dataCur_TEPB is
             Select TEID                   --勞就保識別碼
                   ,IDN                    --身分證號
                   ,IDNSEQ                 --身分證號序號
                   ,SBMK1                  --領取失能給付註記
                   ,SBMK3                  --領取死亡給付註記
                   ,POM                    --個人欠費註記
               From TE.TEPB
              Where TEID = v_ciid;

   begin

      -- 讀出各保險別 BA CIPB同身分證農保被保險人資料，並依身分證號(IDN)排列
      For v_CurbaCIPB in c_dataCur_baCIPB Loop
        begin
          v_Ciid  := v_CurbaCIPB.CIID;
          v_idnno := v_CurbaCIPB.IDN;
          v_Idkey := '';

          If  trim(v_Ciid) IS Not Null Then
             -- 以CIID讀出各保險別TE TEPB同身分證被保險人資料，並更新BA CIPB 資料
               for v_CurTEPB in c_dataCur_TEPB Loop
                 Begin

                      -- 呼叫平均薪資60個月併計

                      TE.PKG_TE_T01.TE_T01( v_i_intyp                --保險別
                                           ,v_Ciid                        --勞就保識別碼
                                           ,6                             --計算均薪月數
                                           ,null                          --年資起算日
                                           ,v_i_evtdate                   --年資截止日
                                           ,NULL                          --YNB 例外指定
                                           ,NULL                          --SMK 分項別
                                           ,NULL                          --TMK 選取異動註記
                                           ,NULL                          --WMK 老年計算均薪註記
                                           ,NULL                          --DWCMK 重複加保投保薪資合併計算註記
                                           ,NULL                          --RINOS 同單位保險證號
                                           ,v_Idkey);

                      sp_BA_genBACIPTData(v_i_intyp, v_i_apno, v_i_seqno, v_idnno,v_idkey);               --產生被保險人承保異動資料(CIPT)

                   exception
                     When others then
                          INS_ERR_LOG(v_i_idnno,v_i_intyp,'Err:sp_BA_updBATEPBData');
                          dbms_output.put_line('**Err:PKG_BA_getCIData-sp_BA_updBATEPBData------->>['||v_i_intyp ||':'||v_i_idnno||' ]');
                          dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
                     end;
               end Loop;
          end if;
        exception
              When others then
                   INS_ERR_LOG(v_i_idnno,v_i_intyp,'Err:sp_BA_updBACIPBData');
                   dbms_output.put_line('**Err:PKG_BA_getCIData-sp_BA_updBACIPBData------->>['||v_i_intyp ||':'||v_i_idnno||' ]');
                   dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
              end;
      end Loop;
          dbms_output.put_line('  PKG_BA_getCIData-sp_BA_updBATEPBData----------->>[ '||v_i_idnno||' ]');
    end;
--Procedure sp_BA_updBATEPBData

 /********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            PKG_BA_getCIData.sp_BA_Get_CIPB
      PURPOSE:         依據身分證號取得被保險人各保險別CIPB、CIPT、CIPG

      PARAMETER(IN):  *v_i_idnno         (varChar2)       --被保險人身分證字號

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
     .0   2012/04/02  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/
  Procedure sp_BA_Get_CIPB (
        v_i_apno                in      varChar2,
        v_i_seqno               in      varChar2,
        v_i_appdate             in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_evtdate             in      varChar2
  ) is

       v_wagecount            number(3)      := 0;         -- 平均薪資採計月份

  begin
           --取得申請案件平均薪資採計月份參數
           v_wagecount := 0;

           Begin
             Select t.APPMONTH Into v_wagecount  -- 採計月數
               From BAPAAVGMON t
              Where t.EFFYEAR = (Select Max(t1.EFFYEAR) From BAPAAVGMON t1
                                  Where t1.EFFYEAR <= Substr(v_i_appdate,1,4));
           Exception
             When NO_DATA_FOUND Then
                  v_wagecount := 60;
           End;

           --產生被保險人勞保資料(L)
             sp_BA_genBACIPBData('L', v_i_apno, v_i_seqno, v_wagecount, v_i_idnno, v_i_evtdate);

           --產生被保險人勞保自願職保資料(V)
             sp_BA_genBACIPBData('V', v_i_apno, v_i_seqno, v_wagecount, v_i_idnno, v_i_evtdate);

           --產生被保險人農保資料(F)
             sp_BA_genBATEPBData('F', v_i_apno, v_i_seqno, v_i_idnno , v_i_evtdate);

          dbms_output.put_line('  PKG_BA_getCIData-sp_BA_Get_CIPB----------->>[ '||v_i_idnno||' ]');
  end;
  --Procedure sp_BA_Get_CIPB



   /********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            PKG_BA_getCIData.sp_BA_Upd_CIPB
      PURPOSE:         依據身分證號更新被保險人勞保(L)、自願職保(V)CIPB、CIPT

      PARAMETER(IN):  *v_i_idnno         (varChar2)       --被保險人身分證字號

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
     .0   2012/04/02  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/
  Procedure sp_BA_Upd_CIPB (
      v_i_apno                in      varChar2,
      v_i_seqno               in      varChar2,
      v_i_appdate             in      varChar2,
      v_i_idnno               in      varChar2,
      v_i_evtdate             in      varChar2,
      v_i_updtyp              in      varChar2
  ) is

  v_wagecount            number(3)      := 0;         -- 平均薪資採計月份

  begin

           --取得申請案件平均薪資採計月份參數
           v_wagecount := 0;

           Begin
             Select t.APPMONTH Into v_wagecount  -- 採計月數
               From BAPAAVGMON t
              Where t.EFFYEAR = (Select Max(t1.EFFYEAR) From BAPAAVGMON t1
                                  Where t1.EFFYEAR <= Substr(v_i_appdate,1,4));
           Exception
             When NO_DATA_FOUND Then
                  v_wagecount := 60;
           End;

           --更新被保險人勞保資料(L)
             sp_BA_updBACIPBData('L', v_i_apno, v_i_seqno, v_wagecount, v_i_idnno, v_i_evtdate,v_i_updtyp);

           --更新被保險人勞保自願職保資料(V)
             sp_BA_updBACIPBData('V', v_i_apno, v_i_seqno, v_wagecount, v_i_idnno, v_i_evtdate,v_i_updtyp );

             --  更新類別為 2 才需更新被保險人農保資料 2012/05/22 給付處詹小姐提出
             if (v_i_updtyp = '2')  then
                --更新被保險人農保資料(F)
                sp_BA_updBATEPBData('F', v_i_apno, v_i_seqno, v_i_idnno, v_i_evtdate, null );
             end if;

          dbms_output.put_line('  PKG_BA_getCIData-sp_BA_Upd_CIPB----------->>[ '||v_i_idnno||' ]');
  end;
  --Procedure sp_BA_Upd_CIPB

  /********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            PKG_BA_getCIData.INS_ERR_LOG
      PURPOSE:         更新被保險人CIPB失敗，將被保險人IDN寫入BATRANSCIERRLOG

      PARAMETER(IN):  *v_i_idnno         (varChar2)       --被保險人身分證字號

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
     .0   2012/04/06  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/

  PROCEDURE INS_ERR_LOG (
         v_i_idn                 in      varChar2,
         v_i_intyp               in      varChar2,
         v_i_errmsg              in      varChar2
  ) is
  begin
         INSERT INTO BATRANSCIERRLOG VALUES (v_i_idn , v_i_intyp, to_Char(Sysdate,'YYYYMMDDHH24MISS'),
                                     v_i_errmsg || '-' ||pkg_ba_error.show_debug_msg );
  end;
  --Procedure INS_ERR_LOG

  /********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            PKG_BA_getCIData.Get_AllData_TEST
      PURPOSE:         將全部勞保年金之事故者、眷屬及遺屬，呼叫年資副程式，重新轉入
                       CIPB、CIPT、CIPG，測試勞保年金系統轉回CIPB、CIPT、CIPG並重新編審。

      PARAMETER(IN):  *v_i_idnno         (varChar2)       --被保險人身分證字號

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
     .0   2012/04/06  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/

  PROCEDURE Get_AllData_TEST
  IS
       v_count   NUMBER    := 0;
       Cursor c_dataCur_CIPB is
          SELECT T1.EVTIDNNO AS IDNNO
               , T1.EVTJOBDATE AS EVTDATE
               , T1.APNO AS APNO
               , T1.SEQNO AS SEQNO
               , T1.APPDATE AS APPDATE
            FROM BAAPPBASE T1
           WHERE T1.SEQNO = '0000'
           UNION
          SELECT T2.BENIDNNO AS IDNNO
               , '' AS EVTDATE
               , T2.APNO AS APNO
               , T2.SEQNO AS SEQNO
               , T2.APPDATE AS APPDATE
            FROM BAAPPBASE T2
           WHERE T2.SEQNO <> '0000'
             AND T2.BENEVTREL IN ('1','2','3','4','5','6','7')
           UNION
          SELECT T3.FAMIDNNO AS IDNNO
               , '' AS EVTDATE
               , T3.APNO AS APNO
               , T3.SEQNO AS SEQNO
               , TO_CHAR(SYSDATE,'YYYYMMDD') AS APPDATE
           FROM BAFAMILY T3;
  BEGIN
        -- dbms_output.enable(NULL);
         dbms_output.disable;
         for v_CurCIPB in c_dataCur_CIPB Loop
             v_count := v_count + 1;
             sp_BA_Get_CIPB( v_CurCIPB.APNO, v_CurCIPB.SEQNO, v_CurCIPB.APPDATE, v_CurCIPB.IDNNO, v_CurCIPB.EVTDATE );
             IF( MOD(v_count, 500) = 0) THEN
                 COMMIT;
             END IF;
         END Loop;
         COMMIT;
  END Get_AllData_TEST;
  --Procedure Get_AllData_TEST

  /********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            PKG_BA_getCIData.Get_EvtAFData_TEST
      PURPOSE:         將全部勞保年金之事故者、眷屬及遺屬，呼叫年資副程式，重新轉入
                       CIPB、CIPT、CIPG，測試勞保年金系統轉回CIPB、CIPT、CIPG並重新編審。

      PARAMETER(IN):  *v_i_idnno         (varChar2)       --被保險人身分證字號

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
     .0   2012/04/06  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/

  PROCEDURE Get_EvtAFData_TEST
  IS
       v_count   NUMBER    := 0;
       Cursor c_dataCur_CIPB is
          SELECT T1.EVTIDNNO AS IDNNO
               , T1.EVTJOBDATE AS EVTDATE
               , T1.APNO AS APNO
               , T1.SEQNO AS SEQNO
               , T1.APPDATE As APPDATE
            FROM BAAPPBASE T1
           WHERE T1.SEQNO = '0000'
             And (T1.Evtidnno like 'A%' or T1.Evtidnno like 'F%')
          UNION
          SELECT T2.BENIDNNO AS IDNNO
               , '' AS EVTDATE
               , T2.APNO AS APNO
               , T2.SEQNO AS SEQNO
               , T2.APPDATE As APPDATE
            FROM BAAPPBASE T2
           WHERE T2.SEQNO <> '0000'
             And (T2.Evtidnno like 'A%' or T2.Evtidnno like 'F%')
             AND T2.BENEVTREL IN ('1', '2', '3', '4', '5', '6', '7')
          UNION
          SELECT T3.FAMIDNNO AS IDNNO
               , '' AS EVTDATE
               , T3.APNO AS APNO
               , T3.SEQNO AS SEQNO
               , TO_CHAR(SYSDATE,'YYYYMMDD') AS APPDATE
            FROM BAFAMILY T3
           where T3.APNO in
                 (SELECT T4.APNO
                    FROM BAAPPBASE T4
                   WHERE T4.Apno Like 'K%'
                     And T4.SEQNO = '0000'
                     And (T4.Evtidnno like 'A%' or T4.Evtidnno like 'F%'));
  BEGIN
        -- dbms_output.enable(NULL);
         dbms_output.disable;
         for v_CurCIPB in c_dataCur_CIPB Loop
             v_count := v_count + 1;
             sp_BA_Get_CIPB(v_CurCIPB.APNO, v_CurCIPB.SEQNO, v_CurCIPB.Appdate, v_CurCIPB.IDNNO, v_CurCIPB.EVTDATE);
             IF( MOD(v_count, 500) = 0) THEN
                 COMMIT;
             END IF;
         END Loop;
         COMMIT;
  END Get_EvtAFData_TEST;
  --Procedure Get_EvtAFData_TEST

  /********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            PKG_BA_getCIData.Get_AllData_TEST
      PURPOSE:         將全部勞保年金之事故者、眷屬及遺屬的CIPB、CIPT、CIPG，重原先的BLIADM
                       轉入至BA 下CIPB、CIPT、CIPG，並呼叫年資副程式更新年金實行前有保險年資(HBEDMK)
                       欄位資料，承保系統上線轉檔。

      PARAMETER(IN):

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
     .0   2012/04/06  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/

  PROCEDURE Trans_BliAdmToBA_TEST
  IS
       Cursor c_dataCur_CIPB is
          /*SELECT INTYP, IDN FROM CIPB
           WHERE INTYP in ('L','V','F')
             AND CIID Is Null
           ORDER BY IDN;*/
          SELECT INTYP, IDN, BRDTE, NAME FROM CIPB
           WHERE INTYP in ('L','V','F')
             AND CIID Is Null
           ORDER BY IDN;

  BEGIN


        /*呼叫年資副程式更新CIPB年金實行前有保險年資(HBEDMK)及勞就保識別碼(CIID)欄位資料，承保系統上線轉檔，
          尚未執行。
        */
         FOR v_CurCIPBIdn IN c_dataCur_CIPB LOOP
         BEGIN
             --sp_BA_updHBEDMK(v_CurCIPBIdn.INTYP ,v_CurCIPBIdn.IDN);
             sp_BA_updHBEDMK(v_CurCIPBIdn.INTYP ,v_CurCIPBIdn.IDN, v_CurCIPBIdn.BRDTE,v_CurCIPBIdn.NAME);
             COMMIT;
         EXCEPTION
             WHEN OTHERS THEN
                INS_ERR_LOG(v_CurCIPBIdn.Idn, v_CurCIPBIdn.Intyp, 'sp_BA_updHBEDMK false.');
             END;
         END LOOP;
         COMMIT;
  END Trans_BliAdmToBA_TEST;
  --Procedure Trans_BliAdmToBA_TEST

  /********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            PKG_BA_getCIData.Trans_APNOToBA_TEST
      PURPOSE:         勞保年金系統下之 CIPB、CIPT、CIPG，均增加APNO 、SEQNO 二欄位，並將資料轉入。

      PARAMETER(IN):

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
      1.0   2013/08/28  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/

  PROCEDURE Trans_APNOToBA_TEST
  IS
        v_APCount  NUMBER      := 0;
        v_count    NUMBER      := 0;
        v_Dupcount NUMBER      := 0;
        v_IDNNO    BA.BAAPPBASE.Evtidnno%Type := '';
        v_APNO     BA.BAAPPBASE.Apno%Type     := '';
        v_SEQNO    BA.BAAPPBASE.SEQNO%Type    := '';

        Cursor c_dataCur_APLIST is
          SELECT '1' AS "SURMK", t.APNO, t.SEQNO, t.EVTIDNNO AS "EVTIDNNO", t.BENIDNNO AS "BENIDNNO"
            FROM BAAPPBASE t
           Where t.Benevtrel in ('1', '2', '3', '4', '5', '6', '7')
          UNION
          SELECT '2' AS "SURMK", a.APNO, a.SEQNO, a.FAMIDNNO AS "EVTIDNNO", a.FAMIDNNO AS "BENIDNNO"
            FROM BAFAMILY a;

        Cursor c_dataCur_CIPBLIST is
          SELECT t.* FROM CIPB t
           Where t.intyp = 'L'
             And t.idn like v_IDNNO||'%'
           Order By t.idn;

        Cursor c_dataCur_CIPBLIST1 is
          SELECT t.* FROM CIPB t
           Where t.intyp = 'L'
             And t.idn like v_IDNNO||'%'
             And t.apno = v_APNO
             And t.seqno = v_SEQNO
           Order By t.idn;

  BEGIN

        --  讀取勞保年金所有的受理案件清單

        FOR v_CurAPLIST IN c_dataCur_APLIST LOOP
         BEGIN
             v_APCount    := 0;
             v_count      := 0;
             v_Dupcount   := 0;
             v_IDNNO      := '';
             v_APNO       := '';
             v_SEQNO      := '';

             -- 讀取受理編號及序號在CIPB的筆數
             Select Count(*) Into v_APCount From CIPB t
              Where t.apno  = v_CurAPLIST.Apno
                And t.seqno = v_CurAPLIST.Seqno;

             -- 受理編號及序號在CIPB的筆數為0
             If ( v_APCount = 0 ) Then
                If (v_CurAPLIST.Seqno = '0000' And v_CurAPLIST.Surmk = '1') then
                   v_IDNNO := v_CurAPLIST.Evtidnno;
                Elsif (v_CurAPLIST.Seqno <> '0000' And v_CurAPLIST.Surmk = '1') then
                   v_IDNNO := v_CurAPLIST.Benidnno;
                Else
                   v_IDNNO := v_CurAPLIST.Evtidnno;
                End If;

                -- 被保險人基本資料尚未轉入受理編號
                Select Count(*) Into v_count From CIPB t
                 Where t.idn Like v_IDNNO||'%'
                   And Trim(t.apno) Is Null;

                -- 被保險人基本資料已轉入受理編號，但為不同受理編號
                Select Count(*) Into v_Dupcount From CIPB t
                 Where t.idn Like v_IDNNO||'%'
                   And t.apno <> v_CurAPLIST.Apno;

                --  被保險人基本資料轉入事故者受理編號
                If (v_count > 0) Then
                   Update CIPB t
                      Set t.Apno = v_CurAPLIST.Apno,
                          t.seqno = v_CurAPLIST.Seqno
                    Where t.idn Like v_IDNNO||'%'
                      And Trim(t.apno) Is Null;

                   Update CIPT t
                      Set t.Apno = v_CurAPLIST.Apno,
                          t.apseqno = v_CurAPLIST.Seqno
                    Where t.idn Like v_IDNNO||'%';

                   FOR v_CurCIPBLIST IN c_dataCur_CIPBLIST LOOP
                   BEGIN
                        Update CIPG t
                           Set t.Apno = v_CurAPLIST.Apno,
                               t.seqno = v_CurAPLIST.Seqno,
                               t.CIID = v_CurCIPBLIST.Ciid
                         Where t.idn = v_CurCIPBLIST.Idn;
                   EXCEPTION
                        WHEN OTHERS THEN
                         INS_ERR_LOG(v_CurAPLIST.Apno, '', 'CIPG false.');
                        END;
                   END LOOP;
                Elsif ( v_Dupcount > 0 ) Then

                   Select Min(APNO) Into v_APNO From CIPB
                    Where idn Like v_IDNNO||'%';

                   Select Min(SEQNO) Into v_SEQNO From CIPB
                    Where idn Like v_IDNNO||'%'
                      And APNO = v_APNO;

                   Insert Into CIPB (intyp, idn, brdte, name, sex,
                                           fmk, novmk, emk, cmk, ivmk,
                                           prdte, qmk, bscd, cbmk, bmk,
                                           uinmk, ofimk, pom, kmk, cvept,
                                           itrmy, itrmd, depty, deptd, oldty,
                                           oldtd, avgwg, avgm, efmk, nitrmy,
                                           nitrmm, noldty, noldtm, itrmd_15, havgwg,
                                           havgm, dwcmk, mpmk, procdate, hbedmk,
                                           hmk, ciid, adjym, adjmk, APNO, SEQNO)
                                  ( Select intyp, idn, brdte, name, sex,
                                           fmk, novmk, emk, cmk, ivmk,
                                           prdte, qmk, bscd, cbmk, bmk,
                                           uinmk, ofimk, pom, kmk, cvept,
                                           itrmy, itrmd, depty, deptd, oldty,
                                           oldtd, avgwg, avgm, efmk, nitrmy,
                                           nitrmm, noldty, noldtm, itrmd_15, havgwg,
                                           havgm, dwcmk, mpmk, procdate, hbedmk,
                                           hmk, ciid, adjym, adjmk, v_CurAPLIST.APNO, v_CurAPLIST.SEQNO
                                      From CIPB t
                                     Where t.idn Like v_IDNNO||'%'
                                       And t.Apno = v_APNO
                                       And t.Seqno = v_SEQNO
                               );

                   Insert Into CIPT (intyp, idn, uno, unock, seqno,
                                           txcd, efdte, wage, dept, bsmn,
                                           staff, sidmk, tsmk, nrpmk, fill,
                                           prodte, apno, apseqno)
                                  ( Select intyp, idn, uno, unock, seqno,
                                           txcd, efdte, wage, dept, bsmn,
                                           staff, sidmk, tsmk, nrpmk, fill,
                                           prodte, v_CurAPLIST.APNO, v_CurAPLIST.SEQNO
                                      From CIPT t
                                     Where t.idn Like v_IDNNO||'%'
                                       And t.Apno = v_APNO
                                       And t.apseqno = v_SEQNO
                                   );

                   FOR v_CurCIPBLIST1 IN c_dataCur_CIPBLIST1 LOOP
                   BEGIN
                        Insert Into CIPG (intyp, idn, avgtyp, avgmym, avgwg,
                                                prodte, apno, seqno, ciid)
                                       ( Select intyp, idn, avgtyp, avgmym, avgwg,
                                                prodte, v_CurAPLIST.APNO, v_CurAPLIST.SEQNO, v_CurCIPBLIST1.Ciid
                                           From CIPG t
                                          Where t.idn = v_CurCIPBLIST1.Idn
                                            And t.Apno = v_APNO
                                            And t.Seqno = v_SEQNO
                                       );
                   EXCEPTION
                        WHEN OTHERS THEN
                         INS_ERR_LOG(v_CurAPLIST.Apno, '', 'CIPG false.');
                        END;
                   END LOOP;
                End If;

             End If;

             COMMIT;
         EXCEPTION
             WHEN OTHERS THEN
                  INS_ERR_LOG(v_CurAPLIST.Apno, '', 'Trans_APNOToBA_TEST false.');
             END;
         END LOOP;
         COMMIT;
  END Trans_APNOToBA_TEST;
  --Procedure Trans_APNOToBA_TEST

/********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            PKG_BA_getCIData.Update_AllSamty_TEST
      PURPOSE:         勞保年金系統下之 CIPB增加SAMTY 、SAMTD 同單位年資二欄位，並將資料轉入。

      PARAMETER(IN):

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
      1.0   2017/08/01  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/

  PROCEDURE Update_AllSamty_TEST(
        v_i_intyp               in      varChar2
  )
   IS
        v_count      NUMBER             := 0;
	    	v_SAMTY      CIPB.Samty%TYPE    := '';         -- 同單位年資-年
        v_SAMTD      CIPB.Samtd%TYPE    := '';         -- 同單位年資-日
        v_jobno      mmjoblog.job_no%TYPE;
        v_starttime  TIMESTAMP;

        Cursor c_dataCur_CIPBLIST is
          SELECT t.* FROM CIPB t
           Where t.intyp = 'L'
             And t.apno like 'L%'
             And t.itrmy >= 25
         --    And t.ciid like '0000002186%'
           Order By t.idn;

  BEGIN

        --  讀取勞保年金所有的受理案件清單

        --  2017/12/08 寫入開始LOG Add By ChungYu
        v_jobno  := REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','');
        v_starttime := SYSTIMESTAMP;

        SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                         p_job_id => 'Update_AllSamty_TEST',
                         p_step   => '更新所有CIPB同單位年資資料',
                         p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||'),');
        --  2017/12/07 寫入開始LOG Add By ChungYu

        FOR v_CurCIPBLIST IN c_dataCur_CIPBLIST LOOP
         BEGIN
            v_count := v_count + 1;

            -- If (NVL(v_CurCIPBLIST.ITRMY, 0) >= 25) Then

                v_SAMTY := '';
                v_SAMTD := '';
                sp_BA_Get_SamItrmy('L',SUBSTR(v_CurCIPBLIST.Idn,1,1),v_CurCIPBLIST.Ciid,v_SAMTY,v_SAMTD);

                UPDATE CIPB S
                   SET S.Samty = v_SAMTY,
                       S.Samtd = v_SAMTD
                 WHERE S.INTYP = 'L'
                   AND S.IDN = v_CurCIPBLIST.Idn
                   And S.Ciid = v_CurCIPBLIST.Ciid
                   And S.APNO = v_CurCIPBLIST.Apno
                   And S.SEQNO = v_CurCIPBLIST.Seqno;
           --  End if;
             IF( MOD(v_count, 100000) = 0) THEN
                 dbms_output.put_line(' Current Update Recode : ' || v_count);
                 --  2017/12/08 寫入開始LOG Add By ChungYu
                  SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                   p_job_id => 'Update_AllSamty_TEST',
                                   p_step   => '更新所有CIPB同單位年資資料',
                                   p_memo   => 'Current Update Recode :('||v_count||'),');
                  --  2017/12/08 寫入開始LOG Add By ChungYu
                 COMMIT;
             END IF;
         EXCEPTION
             WHEN OTHERS THEN
                  INS_ERR_LOG(v_CurCIPBLIST.Apno, '', 'Update_AllSamty_TEST false.');

                  --  2017/12/08 寫入開始LOG Add By ChungYu
                  SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                                   p_job_id => 'Update_AllSamty_TEST',
                                   p_step   => '更新所有CIPB同單位年資資料',
                                   p_memo   => 'Update_AllSamty_TEST false Apno:('||v_CurCIPBLIST.Apno||'),');
                  --  2017/12/08 寫入開始LOG Add By ChungYu

               --   dbms_output.put_line('Update_AllSamty_TEST false:'||v_CurCIPBLIST.Apno||'**'||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
             END;
         END LOOP;
         COMMIT;
         DBMS_OUTPUT.PUT_LINE('****  Update_AllSamty_TEST Final.  ****');
         --  2017/12/08 寫入開始LOG Add By ChungYu
         SP_BA_RECJOBLOG (p_job_no =>  v_jobno,
                          p_job_id => 'Update_AllSamty_TEST',
                          p_step   => '更新所有CIPB同單位年資資料',
                          p_memo   => '****  Update_AllSamty_TEST Final.  ****');
         --  2017/12/08 寫入開始LOG Add By ChungYu
  END Update_AllSamty_TEST;
  --Procedure Update_AllSamty_TEST


  /********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            PKG_BA_getCIData.sp_BA_updHBEDMK
      PURPOSE:         呼叫年資副程式更新CIPB年金實行前有保險年資(HBEDMK)
                       欄位資料。

      PARAMETER(IN):

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
     .0   2012/04/06  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/

  /*Procedure sp_BA_updHBEDMK (
        v_i_intyp               in      varChar2,
        v_i_idnno               in      varChar2
    ) */
  Procedure sp_BA_updHBEDMK (
        v_i_intyp               in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_brdte               in      varChar2,
        v_i_name                in      varChar2
    ) is
        v_ciid                CI.CIPB.CIID%TYPE := '';         -- 勞就保識別碼
        v_idkey               VARCHAR2(12)      := '';         -- 承保年資副程式回傳被保險人識別碼
        v_HBEDMK              VARCHAR2(1)       := '';         --年金實行前有保險年資
        v_rocodeCount         Number            := 0;          --同身分證被保險人資料筆數

     Cursor c_dataCur_CIPB is
              /*  Select CIID                  --勞就保識別碼
                From CI.CIPB
               Where FTYP = v_i_intyp
                 And IDN = SUBSTR(v_i_idnno,1,10);
                 And IDNSEQ = SUBSTR(v_i_idnno,11,1);*/
               Select * From CI.CIPB
               Where FTYP = v_i_intyp
                 And IDN = SUBSTR(v_i_idnno,1,10)
                 And BRDTE = v_i_brdte
                 And NAME  = v_i_name;

   begin

           -- begin
                --  讀出各保險別CIPB同身分證被保險人資料，並依身分證號序號(IDNSEQ)排列，
                --  並更新被保險人CIPB年金實行前有保險年資(HBEDMK)及勞就保識別碼(CIID)。

                /*  2012/04/19 給付處詹小姐決議，若原CIPB同身分證號有多筆的被保險人資料，
                    因新承保系統所對應的被保險人身份，不確定是否為同一人，因此決議不更新
                    被保險人CIPB年金實行前有保險年資(HBEDMK)及勞就保識別碼(CIID)。
                    2012/12/07 給付處詹美娜確定若BA.CIPB CIID為空值,則以IDN+BRDTE+NAME為KEY
                    讀取CI.CIPB取得CIID
                */

                /*Select Count(IDN) Into v_rocodeCount From CI.CIPB
                 Where FTYP = v_i_intyp
                   And IDN Like SUBSTR(v_i_idnno,1,10); */
                Select  Count(IDN) Into v_rocodeCount
                From CI.CIPB
               Where FTYP = v_i_intyp
                 And IDN = SUBSTR(v_i_idnno,1,10)
                 And BRDTE = v_i_brdte
                 And NAME  = v_i_name;

                If v_rocodeCount = 1 Then
                   for v_CurCIPB in c_dataCur_CIPB Loop
                   Begin
                       v_Ciid  := v_CurCIPB.CIID;
                       v_Idkey := '';

                       -- 呼叫平均薪資60個月併計
                       CI.PKG_CI_T01.CI_T01( v_i_intyp                     --保險別
                                            ,v_Ciid                        --勞就保識別碼
                                            ,60                            --計算均薪月數
                                            ,null                          --年資起算日
                                            ,NULL                          --年資截止日
                                            ,NULL                          --YNB 例外指定
                                            ,NULL                          --SMK 分項別
                                            ,NULL                          --TMK 選取異動註記
                                            ,NULL                          --WMK 老年計算均薪註記
                                         -- ,NULL      2018/02/08 Mark By ChugnYu  --DWCMK 重複加保投保薪資合併計算註記
                                            ,'A'                           --2018/02/08 DWCMK 重複加保投保薪資合併計算註記改為'A'-全併計
                                            ,NULL                          --RINOS 同單位保險證號
                                            ,v_Idkey);

                       SELECT HBEDMK INTO v_HBEDMK FROM CI.PL0126M0OUT WHERE IDKEY = v_Idkey;

                        -- 更新CIPB領取給付註記(BMK)及欠費註記(POM)
                       /*Update CIPB
                          Set CIID   = v_Ciid,
                              HBEDMK = v_HBEDMK
                        Where INTYP = v_i_intyp
                          And IDN   = v_i_idnno;*/
                        Update CIPB
                          Set CIID   = v_Ciid,
                              HBEDMK = v_HBEDMK
                        Where INTYP = v_i_intyp
                          And IDN   = v_i_idnno
                          And BRDTE = v_i_brdte
                          And NAME  = v_i_name;

                   exception
                         When others then
                              INS_ERR_LOG(v_i_idnno,v_i_intyp, 'Err:sp_BA_updHBEDMK');
                              dbms_output.put_line('**Err:PKG_BA_getCIData-sp_BA_updHBEDMK------->>['||v_i_intyp ||':'||v_i_idnno||' ]');
                              dbms_output.put_line(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
                         end;
                   end Loop;
                   --dbms_output.put_line('  PKG_BA_getCIData-sp_BA_updHBEDMK----------->>[ '||v_i_idnno||' ]');
                end if;
        end;
    --Procedure sp_BA_updHBEDMK

  /********批次更新勞保年金系統所使用的 CIPB 「投保薪資調整年月_ADJYM」、「逕調註記_ADJMK」 *********/  -- Add By ChungYu 2012/09/10
  PROCEDURE sp_BA_Upd_ADJYM
  IS
       v_count          NUMBER            := 0;
       v_MinAVGMYM      VARCHAR2(6)       := '';
       v_MaxEFDTE       VARCHAR2(8)       := '';

       Cursor c_dataCur_CIPB is
          Select t.Idn From BA.CIPB t
           Where t.intyp = 'L';

  BEGIN
         FOR v_CurCIPB in c_dataCur_CIPB Loop
             v_count := v_count + 1;
              v_MinAVGMYM := NULL;
              v_MaxEFDTE  := NULL;
             --  經測試分段Query 被保險人 CIPT、CIPG 較下SubQuery 整段包一起更新，SQL 效能好很多，
             --  故將更新勞保年金系統所使用的 CIPB 「投保薪資調整年月_ADJYM」、「逕調註記_ADJMK」SQL，分段執行
             --  讀取被保險人投保薪資類別為1之最小投保年月
             SELECT NVL(MIN(D.AVGMYM), '000000') INTO v_MinAVGMYM
               FROM CIPG D
              WHERE D.INTYP = 'L'
                AND D.IDN = v_CurCIPB.IDN
                AND D.AVGTYP = '1'
                AND D.AVGMYM <> '191101'
                AND ROWNUM = 1;

             --  讀取被保險人CIPT中，異動別為4、3，離該投保年月最近但小於等於該投保年月之生效日期年月
             SELECT MAX(C.EFDTE) INTO v_MaxEFDTE
               FROM CIPT C
              WHERE C.INTYP = 'L'
                AND C.IDN = v_CurCIPB.IDN
                AND (C.TXCD = '4' OR C.TXCD = '3')
                AND SUBSTR(C.EFDTE,1,6) <= v_MinAVGMYM;

             IF v_MaxEFDTE IS NOT NULL THEN

                -- 讀取被保險人CIPT中，異動別為4、3，離該投保年月最近但小於等於該投保年月之生效日期年月帶入「投保薪資調整年月」欄
                -- 讀取該筆CIPT之處理人員代號_ STAFF欄，當該代號前2碼=66者，「逕調註記」欄帶入”Y”，否則為null
                UPDATE CIPB A
                   SET (A.ADJYM, A.ADJMK) = (SELECT SUBSTR(B.EFDTE, 1, 6),
                                                    DECODE(B.STAFF, '00000', 'Y',
                                                                     DECODE(SUBSTR(B.STAFF, 1, 2), '66', 'Y', NULL)) AS "STAFF"
                                               FROM CIPT B
                                              WHERE B.INTYP = 'L'
                                                AND B.IDN =  v_CurCIPB.Idn
                                                AND B.EFDTE = v_MaxEFDTE
                                                AND (B.TXCD = '4' OR B.TXCD = '3')
                                                AND ROWNUM = 1)
                 WHERE A.INTYP = 'L'
                   AND A.IDN = v_CurCIPB.Idn;

             ELSE
                UPDATE CIPB A
                   SET A.ADJYM = NULL,
                       A.ADJMK = NULL
                 WHERE A.INTYP = 'L'
                   AND A.IDN = v_CurCIPB.Idn;
             END IF;


             IF( MOD(v_count, 10000) = 0) THEN
                 dbms_output.put_line('** COMMIT ROUND COUNT  ------->> ' || v_count || ' TIME： ' || TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS'));
                 COMMIT;
             END IF;
         END Loop;
         COMMIT;
  END sp_BA_Upd_ADJYM;

  --Procedure sp_BA_Upd_ADJYM

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_getCIData.sp_BA_Get_SamItrmy
        PURPOSE:         計算勞保老年年金被保險人同單位年資。

        PARAMETER(IN):  *v_i_ciid         (varChar2)       --被保險人勞就保識別碼


        PARAMETER(OUT): *v_o_samty        (varChar2)       --被保險人同單位年資-年
		                    *v_o_samtd        (varChar2)       --被保險人同單位年資-日

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2017/08/01  ChungYu Lin  Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    Procedure sp_BA_Get_SamItrmy (
        v_i_intyp               in      varChar2,
        v_i_idnno               in      varChar2,
        v_i_ciid                in      varChar2,
        v_o_samty               out     varChar2,
        v_o_samtd               out     varChar2
    ) is

	  --  2017/07/27 Add By ChungYu 因計算同單位年資需求
      i             NUMBER := 0;
      v_samubno     VARCHAR2(8);
      SAM_RUNOS     VARCHAR2(2700);
      TYPE TYP_REC_CMF17 IS RECORD(RUNO  VARCHAR2(200));
      TYPE TYP_TAB_CMF17 IS TABLE OF TYP_REC_CMF17 INDEX BY BINARY_INTEGER;
      tab_cmf17     TYP_TAB_CMF17;

	  TYPE TYP_REC_SAM IS RECORD(
      RELATIONID    BCCMF16.RELATIONID%TYPE,
      UBNO          BCCMF16.UBNO%TYPE,
      IDKEYS        VARCHAR2(12),
      SAMTY         NUMBER(3),
      SAMTD         NUMBER(3),
      RUNOS         VARCHAR2(2700));

	  TYPE TYP_TAB_SAM IS TABLE OF TYP_REC_SAM INDEX BY BINARY_INTEGER;
      tab_sam       TYP_TAB_SAM;
      v_126m0out    CI.PL0126M0OUT%ROWTYPE;

      CURSOR CUR_UPB(p_ftyp VARCHAR2,p_ciid VARCHAR2) IS
      SELECT DISTINCT UNO FROM CI.CIUPB
       WHERE FTYP = p_ftyp AND CIID = p_ciid
       ORDER BY UNO;

      CURSOR CUR_CMF16 (p_ftyp VARCHAR2,p_ciid VARCHAR2) IS
      SELECT RELATIONID,UBNO FROM BCCMF16 WHERE UBNO IN (
      SELECT DISTINCT UNO FROM CI.CIUPB
       WHERE FTYP = p_ftyp AND CIID = p_ciid)
       ORDER BY RELATIONID;
      --  2017/07/27 Add By ChungYu 因計算同單位年資需求

    Begin
      --1.找曾經投保過單位之投保年資，若有一單位大於等於25年則視為同單位年資
      tab_sam.delete;
      v_126m0out := NULL;
      FOR tmp_upb IN CUR_UPB('L',v_i_ciid)
      LOOP
        tab_sam(CUR_UPB%ROWCOUNT).ubno  := tmp_upb.uno;
        tab_sam(CUR_UPB%ROWCOUNT).runos := tmp_upb.uno;
        CI.PKG_CI_T01.CI_T03(v_i_intyp,                        --保險別
                             v_i_ciid,                         --勞就保識別碼
                             60,                               --計算均薪月數
                             NULL,                             --年資起算日
                             NULL,                             --年資截止日
                             NULL,                             --YNB 例外指定
                             NULL,                             --SMK 分項別
                             '1',                              --TMK 選取異動註記
                             NULL,                             --WMK 老年計算均薪註記
                             NULL,                             --DWCMK 重複加保投保薪資合併計算註記
                             tab_sam(CUR_UPB%ROWCOUNT).runos,  --RINOS 同單位保險證號
                             v_126m0out
                             );
        tab_sam(CUR_UPB%ROWCOUNT).idkeys := v_126m0out.idkey;
        tab_sam(CUR_UPB%ROWCOUNT).samty  := v_126m0out.itrmy;
        tab_sam(CUR_UPB%ROWCOUNT).samtd  := v_126m0out.itrmd;
      END LOOP;
      v_126m0out.itrmy := 0;
      v_126m0out.itrmd := 0;
      FOR i IN 1 .. tab_sam.COUNT
      LOOP
        IF tab_sam(i).samty > v_126m0out.itrmy
        OR (tab_sam(i).samty = v_126m0out.itrmy AND tab_sam(i).samtd > v_126m0out.itrmd)
        THEN
          v_126m0out.idkey := tab_sam(i).idkeys;
          v_126m0out.itrmy := tab_sam(i).samty;
          v_126m0out.itrmd := tab_sam(i).samtd;
        END IF;
      END LOOP;
      --2.無任一單位大於等於25年，查參數bccmf17無再查參數bccmf16
      IF v_126m0out.itrmy < 25
      THEN
        SAM_RUNOS := NULL;
        --tab_sam.delete;   --各單位年資要與同單位年資一起比較,得最大同單位年資
        v_126m0out.itrmy := 0;
        v_126m0out.itrmd := 0;
        v_samubno     := NULL;
        SELECT DECODE(UBNO1,NULL,NULL,UBNO1 || ',') || DECODE(UBNO2,NULL,NULL,UBNO2 || ',') ||
               DECODE(UBNO3,NULL,NULL,UBNO3 || ',') || DECODE(UBNO4,NULL,NULL,UBNO4 || ',') ||
               DECODE(UBNO5,NULL,NULL,UBNO5 || ',') || DECODE(UBNO6,NULL,NULL,UBNO6 || ',') ||
               DECODE(UBNO7,NULL,NULL,UBNO7 || ',') || DECODE(UBNO8,NULL,NULL,UBNO8 || ',') ||
               DECODE(UBNO9,NULL,NULL,UBNO9 || ',') || DECODE(UBNO10,NULL,NULL,UBNO10 || ',') ||
               DECODE(UBNO11,NULL,NULL,UBNO11 || ',') || DECODE(UBNO12,NULL,NULL,UBNO12 || ',') ||
               DECODE(UBNO13,NULL,NULL,UBNO13 || ',') || DECODE(UBNO14,NULL,NULL,UBNO14 || ',') ||
               DECODE(UBNO15,NULL,NULL,UBNO15 || ',')
        BULK COLLECT INTO tab_cmf17
        FROM BCCMF17 WHERE IDNO = v_i_idnno;
        FOR i IN 1 .. tab_cmf17.COUNT
        LOOP
          SAM_RUNOS := SAM_RUNOS || tab_cmf17(i).runo;
        END LOOP;
        IF tab_cmf17.COUNT > 0
        THEN
          CI.PKG_CI_T01.CI_T03(v_i_intyp,                        --保險別
                               v_i_ciid,                         --勞就保識別碼
                               60,                               --計算均薪月數
                               NULL,                             --年資起算日
                               NULL,                             --年資截止日
                               NULL,                             --YNB 例外指定
                               NULL,                             --SMK 分項別
                               '1',                              --TMK 選取異動註記
                               NULL,                             --WMK 老年計算均薪註記
                               NULL,                             --DWCMK 重複加保投保薪資合併計算註記
                               SAM_RUNOS,                        --RINOS 同單位保險證號
                               v_126m0out
                               );
        ELSE
          i := tab_sam.COUNT;
          FOR tmp_cmf16 IN CUR_CMF16('L',v_i_ciid)
          LOOP
            IF CUR_CMF16%ROWCOUNT = 1
            OR tab_sam(tab_sam.COUNT).relationid <> tmp_cmf16.relationid
            THEN
              i := tab_sam.COUNT + 1;
              tab_sam(i).relationid := tmp_cmf16.relationid;
              tab_sam(i).ubno       := tmp_cmf16.ubno;
              tab_sam(i).runos      := tmp_cmf16.ubno || ',';
            ELSE
              tab_sam(i).runos      := tab_sam(i).runos || tmp_cmf16.ubno || ',';
            END IF;
          END LOOP;

          FOR i IN 1 .. tab_sam.COUNT
          LOOP
            CI.PKG_CI_T01.CI_T03(v_i_intyp,                       --保險別
                                 v_i_ciid,                        --勞就保識別碼
                                 60,                              --計算均薪月數
                                 NULL,                            --年資起算日
                                 NULL,                            --年資截止日
                                 NULL,                            --YNB 例外指定
                                 NULL,                            --SMK 分項別
                                 '1',                             --TMK 選取異動註記
                                 NULL,                            --WMK 老年計算均薪註記
                                 NULL,                            --DWCMK 重複加保投保薪資合併計算註記
                                 tab_sam(i).runos,                --RINOS 同單位保險證號
                                 v_126m0out
                                 );
            tab_sam(i).idkeys := v_126m0out.idkey;
            tab_sam(i).samty  := v_126m0out.itrmy;
            tab_sam(i).samtd  := v_126m0out.itrmd;
          END LOOP;
          --找關係企業中最大年資
          v_126m0out.itrmy := 0;
          v_126m0out.itrmd := 0;
          FOR i IN 1 .. tab_sam.COUNT
          LOOP
            IF tab_sam(i).samty > v_126m0out.itrmy
            OR (tab_sam(i).samty = v_126m0out.itrmy AND tab_sam(i).samtd > v_126m0out.itrmd)
            THEN
              v_126m0out.idkey := tab_sam(i).idkeys;
              v_126m0out.itrmy := tab_sam(i).samty;
              v_126m0out.itrmd := tab_sam(i).samtd;
              v_samubno        := SUBSTR(tab_sam(i).ubno,1,8);
            END IF;
          END LOOP;
        END IF;
      END IF;
      v_o_samty := v_126m0out.itrmy;
      v_o_samtd := v_126m0out.itrmd;
	End;
  --Procedure sp_BA_Get_SamItrmy

  /********測試批次呼叫年資副程式並轉回勞保年金系統所使用的CIPB、CIPT、CIPG *********/
  PROCEDURE CALL_Batch_TEST
  IS
       v_count   NUMBER    := 0;
       Cursor c_dataCur_CIPB is
          Select t.Idn From CI.CIPB t
           Where t.idn like 'A%';

  BEGIN
        -- dbms_output.enable(NULL);
         dbms_output.disable;
         for v_CurCIPB in c_dataCur_CIPB Loop
             v_count := v_count + 1;
             --sp_BA_Get_CIPB(v_CurCIPB.Idn, '');
             IF( MOD(v_count, 500) = 0) THEN
                 COMMIT;
             END IF;
         END Loop;
         COMMIT;
  END CALL_Batch_TEST;
  --Procedure CALL_Batch_TEST

  /**********************測試呼叫ci年資副程式***************************/
  PROCEDURE CALL_CIT01_TEST
  IS
    v_intyp  VARCHAR2(1) := 'L';
    v_ciid   VARCHAR2(10) := '0000000691';
    v_idkey  VARCHAR2(100);
  BEGIN
    CI.PKG_CI_T01.CI_T01(v_intyp,v_ciid,60,null,to_char(sysdate,'yyyymmdd'),NULL,NULL,NULL,NULL,'Y',NULL,v_idkey);


    DBMS_OUTPUT.PUT_LINE('v_ciid=' || v_ciid || ',idkey=' || v_idkey);


  END CALL_CIT01_TEST;
  --Procedure CALL_CIT01_TEST

/**********************測試呼叫ci年資副程式***************************/
  PROCEDURE CALL_TET01_TEST
  IS
    v_intyp  VARCHAR2(1) := 'F';
    v_ciid   VARCHAR2(10) := '0001146708';
    v_idkey  VARCHAR2(100);
  BEGIN
    TE.PKG_TE_T01.TE_T01(v_intyp,v_ciid,6,null,to_char(sysdate,'yyyymmdd'),NULL,NULL,NULL,NULL,NULL,NULL,v_idkey);
    DBMS_OUTPUT.PUT_LINE('v_ciid=' || v_ciid || ',idkey=' || v_idkey);
  END CALL_TET01_TEST;
  --Procedure CALL_TET01_TEST

End;
/