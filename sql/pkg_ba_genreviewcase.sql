CREATE OR REPLACE Package BA.PKG_BA_GENREVIEWCASE
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PRO_PKG_BA_GENREVIEWCASE
    PURPOSE:         第一次月核定後，各項年金檢核案件產生作業

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2015/07/13  ChungYu      Created this Package.

    NOTES:
    1.各 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/
authid definer is
    v_g_errMsg               varChar2(4000);

    procedure sp_BA_GenReviewCaseL (
        v_i_issuym           in      varChar2
    );

    procedure sp_BA_GenReviewCaseK (
        v_i_issuym           in      varChar2
    );

    procedure sp_BA_GenReviewCaseS (
        v_i_issuym           in      varChar2
    );

End;
/

CREATE OR REPLACE Package Body BA.PKG_BA_GENREVIEWCASE
is
    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PRO_PKG_BA_GENREVIEWCASE.sp_BA_GenReviewCaseL
        PURPOSE:         老年年金檢核案件產生作業

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2015/07/13  ChungYu      Created this procedure.
        1.1   2015/10/01  ChungYu      修正失能年金產生檢核案件，排除37案.
        1.2   2015/10/02  ChungYu      修正老年年金產生隨機抽查檢核案件，檢核案件件數問題.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_GenReviewCaseL (
        v_i_issuym           in      varChar2
    ) is
        v_issuym             varChar2(6);
        v_confirmCount       Number;
        v_RowCount           Number(20);
        v_MaxCount           Number(20);
        v_Loop               BOOLEAN;
        v_BADAPRID           Number(20);
        v_BAAAPPPID          Number(20);
        v_roundCount         Number;

        --查詢第一次月核定後,多給付年月的案件資料
        Cursor c_dataCur is
               SELECT T.APNO               -- 受理編號
                     ,T.MCHKTYP            -- 月核案件類別
                     ,T.ISSUYM             -- 核定年月
                     ,T.PAYYM              -- 給付年月
                     ,T.OLDRATE            -- 展延/減額比率
                     ,T.BEFISSUEAMT        -- 總核定金額
                 FROM BADAPR T
                WHERE T.APNO LIKE 'L%'
                  AND T.SEQNO = '0000'
                  AND T.ISSUYM = v_issuym
                  AND T.MTESTMK = 'F'
                  AND T.PAYKIND <> '44'
                  AND T.APNO IN (Select T1.APNO From BAAPPBASE T1
                                  Where SUBSTR(T1.APNO,1,1) = 'L'
                                    And T1.SEQNO = '0000'
                                    AND T1.CASETYP = '2'
                                    AND T1.PROCSTAT = '50')
                  AND T.APNO IN ( SELECT T2.APNO FROM BADAPR T2
                                   WHERE SUBSTR(T2.APNO,1,1) = 'L'
                                     AND T2.SEQNO = '0000'
                                     AND T2.ISSUYM = v_issuym
                                     AND T2.MTESTMK = 'F'
                                   GROUP BY T2.APNO
                                  HAVING COUNT( Distinct T2.PAYYM)>1
                                 )
               ORDER BY T.MCHKTYP, T.APNO;

        begin
            v_g_errMsg := '';
            v_issuym       := to_Char(Sysdate,'YYYYMM');
            v_confirmCount := 0;
            v_MaxCount     := 0;
            v_RowCount     := 0;
            v_Loop         := FALSE;

            -- 若有傳入"核定年月"時,則以傳入的值為條件;無傳入時,則預設為系統年月
            if nvl(trim(v_i_issuym),' ')<>' ' then
               v_issuym := v_i_issuym;
            end if;

            -- 查詢檢核案件是否經過業務單位確認
            Select Count(*) into v_confirmCount From BARVCONFIRM
             Where PAYCODE = 'L'
               And ISSUYM  = v_issuym
               And CONFIRMMK = 'Y';

            --  尚未經過確認可重新產生檢核案件
            if (v_confirmCount = 0) then

                Delete BARVCASE
                 Where PAYCODE = 'L'
                   And ISSUYM  = v_issuym;

                Delete BARVCONFIRM
                 Where PAYCODE = 'L'
                   And ISSUYM  = v_issuym;

                /*
                   產生多給付年月案件檢核
                */
                for v_dataCur in c_dataCur Loop
                    --新增月核案件檢核記錄檔資料
                    insert into BARVCASE (PAYCODE
                                         ,RVTYPE
                                         ,APNO
                                         ,MCHKTYP
                                         ,ISSUYM
                                         ,PAYYM
                                         ,OLDRATE
                                         ,BEFISSUEAMT
                                         ,PROCTIME
                                        ) values (
                                            'L'
                                           ,'1'
                                           ,v_dataCur.APNO
                                           ,v_dataCur.MCHKTYP
                                           ,v_dataCur.ISSUYM
                                           ,v_dataCur.PAYYM
                                           ,v_dataCur.OLDRATE
                                           ,v_dataCur.BEFISSUEAMT
                                           ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                   );
                end Loop;

                -- 查詢檢核案件是否經過業務單位確認
                Select MAX(BAAPPBASEID) into v_MaxCount From BAAPPBASE;

                /*
                   產生隨機抽查案件檢核,展延/減額比率大於0 , 1000筆案件資料。
                   總抽查次數為30000次。
                */
                v_RowCount   := 0;
                v_roundCount := 0;
                v_Loop       := FALSE;
                While Not v_Loop Loop
                   v_BADAPRID := 0;
                   BEGIN
                        /*SELECT round(dbms_random.value(1,v_MaxCount)) Into v_BAAAPPPID
                          From Dual;*/

                        SELECT T.BAAPPBASEID Into v_BAAAPPPID FROM BAAPPBASE T
                         WHERE SUBSTR(T.APNO,1,1) = 'L'
                           AND T.SEQNO = '0000'
                           AND T.CASETYP = '2'
                           AND T.PROCSTAT = '50'
                           AND BAAPPBASEID = round(dbms_random.value(1,v_MaxCount));


                        SELECT NVL(T.BADAPRID,0) into v_BADAPRID
                          FROM BADAPR T
                         WHERE T.APNO LIKE 'L%'
                           AND T.SEQNO = '0000'
                           AND T.ISSUYM = v_issuym
                           AND T.MTESTMK = 'F'
                           AND T.PAYKIND <> '44'
                           AND T.OLDRATE > 0
                           AND T.BAAPPBASEID = round(dbms_random.value(1,v_MaxCount))
                           AND ROWNUM = 1;

                    Exception
                         When NO_DATA_FOUND Then
                           v_roundCount := v_roundCount + 1;
                    End;

                    If v_BADAPRID <> 0 then
                       insert into BARVCASE (PAYCODE
                                            ,RVTYPE
                                            ,APNO
                                            ,MCHKTYP
                                            ,ISSUYM
                                            ,PAYYM
                                            ,OLDRATE
                                            ,BEFISSUEAMT
                                            ,PROCTIME
                                          )
                                     Select 'L'
                                            ,'2'
                                            ,APNO
                                            ,MCHKTYP
                                            ,ISSUYM
                                            ,PAYYM
                                            ,OLDRATE
                                            ,BEFISSUEAMT
                                            ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                       From BADAPR
                                      Where BADAPRID = v_BADAPRID;
                    end if;

                    If (v_RowCount <= 1000) And (v_BADAPRID <> 0) then
                       v_RowCount := v_RowCount +1;
                       if (v_RowCount >= 1000) then
                           v_Loop := TRUE;
                       end if;
                    elsif (v_roundCount >= 30000) then
                           v_Loop := TRUE;
                    end if;
                end Loop;


                /*
                   產生隨機抽查案件檢核,展延/減額比率小於0 , 1000筆案件資料。
                   總抽查次數為30000次。
                */
                v_RowCount   := 0;
                v_roundCount := 0;
                v_Loop       := FALSE;
                While Not v_Loop Loop
                   v_BADAPRID := 0;
                   BEGIN
                        /*SELECT round(dbms_random.value(1,v_MaxCount)) Into v_BAAAPPPID
                          From Dual;*/

                        SELECT T.BAAPPBASEID Into v_BAAAPPPID FROM BAAPPBASE T
                         WHERE SUBSTR(T.APNO,1,1) = 'L'
                           AND T.SEQNO = '0000'
                           AND T.CASETYP = '2'
                           AND T.PROCSTAT = '50'
                           AND BAAPPBASEID = round(dbms_random.value(1,v_MaxCount));

                        SELECT NVL(T.BADAPRID,0) into v_BADAPRID
                          FROM BADAPR T
                         WHERE T.APNO LIKE 'L%'
                           AND T.SEQNO = '0000'
                           AND T.ISSUYM = v_issuym
                           AND T.MTESTMK = 'F'
                           AND T.PAYKIND <> '44'
                           AND T.OLDRATE < 0
                           AND T.BAAPPBASEID = round(dbms_random.value(1,v_MaxCount))
                           AND ROWNUM = 1;

                    Exception
                         When NO_DATA_FOUND Then
                           v_roundCount := v_roundCount + 1;
                    End;

                    If v_BADAPRID <> 0 then
                       insert into BARVCASE (PAYCODE
                                            ,RVTYPE
                                            ,APNO
                                            ,MCHKTYP
                                            ,ISSUYM
                                            ,PAYYM
                                            ,OLDRATE
                                            ,BEFISSUEAMT
                                            ,PROCTIME
                                          )
                                     Select 'L'
                                            ,'2'
                                            ,APNO
                                            ,MCHKTYP
                                            ,ISSUYM
                                            ,PAYYM
                                            ,OLDRATE
                                            ,BEFISSUEAMT
                                            ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                       From BADAPR
                                      Where BADAPRID = v_BADAPRID;
                    end if;

                    If (v_RowCount <= 1000) And (v_BADAPRID <> 0) then
                       v_RowCount := v_RowCount +1;
                       if (v_RowCount >= 1000) then
                           v_Loop := TRUE;
                       end if;
                    elsif (v_roundCount >= 30000) then
                           v_Loop := TRUE;
                    end if;

                end Loop;

               --  新增月核案件檢核確認檔
                   insert into BARVCONFIRM (PAYCODE
                                           ,ISSUYM
                                           ,CONFIRMMK
                                          ) values (
                                           'L'
                                          ,v_issuym
                                          ,NULL
                                          );

             end if;

           dbms_output.put_line('新增老年月核案件檢核記錄檔資料完成');
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line('**Err:'||v_g_errMsg);
        end;
    --procedure sp_BA_GenReviewCaseL End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PRO_PKG_BA_GENREVIEWCASE.sp_BA_GenReviewCaseK
        PURPOSE:         失能年金檢核案件產生作業

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2015/07/15  ChungYu      Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_GenReviewCaseK (
        v_i_issuym           in      varChar2
    ) is
        v_issuym             varChar2(6);
        v_confirmCount       Number;

        begin
            v_issuym       := to_Char(Sysdate,'YYYYMM');

            -- 若有傳入"核定年月"時,則以傳入的值為條件;無傳入時,則預設為系統年月
            if nvl(trim(v_i_issuym),' ')<>' ' then
               v_issuym := v_i_issuym;
            end if;

            -- 查詢檢核案件是否經過業務單位確認
            Select Count(*) into v_confirmCount From BARVCONFIRM
             Where PAYCODE = 'K'
               And ISSUYM  = v_issuym
               And CONFIRMMK = 'Y';

            --  尚未經過確認可重新產生檢核案件
            if (v_confirmCount = 0) then

                Delete BARVCASE
                 Where PAYCODE = 'K'
                   And ISSUYM  = v_issuym;

                Delete BARVCONFIRM
                 Where PAYCODE = 'K'
                   And ISSUYM  = v_issuym;

                /*
                   產生月試核未異動的續發案但給付月數大於1個月的案件資料。
                */

                       insert into BARVCASE (PAYCODE
                                            ,RVTYPE
                                            ,APNO
                                            ,MCHKTYP
                                            ,ISSUYM
                                            ,PAYYM
                                            ,OLDRATE
                                            ,BEFISSUEAMT
                                            ,PROCTIME
                                            ,PAYKIND
                                            ,BENNAME
                                          )
                                      SELECT 'K'
                                            ,'1'
                                            ,T.APNO
                                            ,T.MCHKTYP
                                            ,T.ISSUYM
                                            ,T.PAYYM
                                            ,''
                                            ,T.BEFISSUEAMT
                                            ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                            ,T.PAYKIND
                                            ,(Select A.BENNAME From BAAPPBASE A
                                               Where A.APNO = T.APNO
                                                 And A.SEQNO = '0000')
                                        FROM BADAPR T
                                       WHERE T.APNO LIKE 'K%'
                                         AND T.SEQNO = '0000'
                                         AND T.ISSUYM = v_issuym
                                         AND T.MTESTMK = 'F'
                                         AND T.PAYKIND Not In ('34','37')
                                         AND T.APNO IN (Select T1.APNO From BAAPPBASE T1
                                                         Where SUBSTR(T1.APNO,1,1) = 'K'
                                                           AND T1.SEQNO = '0000'
                                                           AND T1.CASETYP = '2'
                                                           AND T1.PROCSTAT = '50')
                                         AND T.APNO IN ( SELECT DISTINCT APNO FROM BADAPR T2
                                                          WHERE SUBSTR(T2.APNO,1,1) = 'K'
                                                            AND T2.SEQNO = '0000'
                                                            AND T2.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym , 'YYYYMM'), -1),'YYYYMM')
                                                            AND T2.MTESTMK = 'F'
                                                            AND T2.APLPAYMK = '3'
                                                            AND T2.APLPAYDATE IS NOT NULL
                                                        )
                                         AND T.APNO NOT IN ( SELECT DISTINCT(T3.APNO)
                                                               FROM BAEXALIST T3
                                                              WHERE SUBSTR(T3.APNO,1,1) = 'K'
                                                                AND T3.ISSUYM = v_issuym
                                                                AND T3.MTESTMK = 'M'
                                                            )
                                         AND T.APNO IN (SELECT T4.APNO FROM BADAPR T4
                                                         WHERE T4.APNO = T.APNO
                                                           AND T4.SEQNO = '0000'
                                                           AND T4.ISSUYM = v_issuym
                                                           AND T4.MTESTMK = 'F'
                                                           AND T4.PAYKIND Not In ('34','37')
                                                         GROUP BY T4.APNO
                                                        HAVING COUNT(T4.APNO)>1
                                                        )
                                         AND T.CPIRATE = ( SELECT T7.CPIRATE FROM BADAPR T7
                                                            WHERE T7.APNO = T.APNO
                                                               AND T7.SEQNO = '0000'
                                                               AND T7.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym, 'YYYYMM'), -1),'YYYYMM')
                                                               AND T7.PAYYM = (SELECT MAX(T71.PAYYM) FROM BADAPR T71
                                                                                WHERE T71.APNO = T7.APNO
                                                                                  AND T71.SEQNO = '0000'
                                                                                  AND T71.ISSUYM = T7.ISSUYM
                                                                                  AND T71.MTESTMK = 'F'
                                                                                  AND T71.APLPAYMK = '3'
                                                                                  AND T71.APLPAYDATE IS NOT NULL
                                                                               )
                                                               AND T7.MTESTMK = 'F'
                                                               AND T7.PAYKIND Not In ('34','37')
                                                               AND T7.APLPAYMK = '3'
                                                               AND T7.APLPAYDATE IS NOT NULL
                                                          )
                                       ORDER BY T.APNO;
               /*
                   產生月試核未異動的續發案但核定金額與上個月不同的案件資料。
                */

                       insert into BARVCASE (PAYCODE
                                            ,RVTYPE
                                            ,APNO
                                            ,MCHKTYP
                                            ,ISSUYM
                                            ,PAYYM
                                            ,OLDRATE
                                            ,BEFISSUEAMT
                                            ,PROCTIME
                                            ,PAYKIND
                                            ,BENNAME
                                            ,LASTISSUEAMT
                                          )
                                      SELECT 'K'
                                            ,'2'
                                            ,T.APNO
                                            ,T.MCHKTYP
                                            ,T.ISSUYM
                                            ,T.PAYYM
                                            ,''
                                            ,T.BEFISSUEAMT
                                            ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                            ,T.PAYKIND
                                            ,(Select A.BENNAME From BAAPPBASE A
                                               Where A.APNO = T.APNO
                                                 And A.SEQNO = '0000')
                                            ,(SELECT T6.BEFISSUEAMT FROM BADAPR T6
                                               WHERE T6.APNO = T.APNO
                                                 AND T6.SEQNO = '0000'
                                                 AND T6.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym, 'YYYYMM'), -1),'YYYYMM')
                                                 AND T6.PAYYM = (SELECT MAX(T61.PAYYM) FROM BADAPR T61
                                                                  WHERE T61.APNO = T6.APNO
                                                                    AND T61.SEQNO = '0000'
                                                                    AND T61.ISSUYM = T6.ISSUYM
                                                                    AND T61.MTESTMK = 'F'
                                                                    AND T61.APLPAYMK = '3'
                                                                    AND T61.APLPAYDATE IS NOT NULL
                                                                 )
                                                 AND T6.MTESTMK = 'F'
                                                 AND T6.APLPAYMK = '3'
                                                 AND T6.APLPAYDATE IS NOT NULL) AS BMONTHISSUEAMT
                                        FROM BADAPR T
                                       WHERE T.APNO LIKE 'K%'
                                         AND T.SEQNO = '0000'
                                         AND T.ISSUYM = v_issuym
                                         AND T.MTESTMK = 'F'
                                         AND T.PAYKIND Not In ('34','37')
                                         AND T.APNO IN (Select T1.APNO From BAAPPBASE T1
                                                         Where SUBSTR(T1.APNO,1,1) = 'K'
                                                           And T1.SEQNO = '0000'
                                                           AND T1.CASETYP = '2'
                                                           AND T1.PROCSTAT = '50')
                                         AND T.APNO IN ( SELECT DISTINCT APNO FROM BADAPR T2
                                                          WHERE SUBSTR(T2.APNO,1,1) = 'K'
                                                            AND T2.SEQNO = '0000'
                                                            AND T2.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym, 'YYYYMM'), -1),'YYYYMM')
                                                            AND T2.MTESTMK = 'F'
                                                            AND T2.APLPAYMK = '3'
                                                            AND T2.APLPAYDATE IS NOT NULL
                                                       )
                                         AND T.APNO NOT IN ( SELECT DISTINCT(T3.APNO)
                                                               FROM BAEXALIST T3
                                                              WHERE SUBSTR(T3.APNO,1,1) = 'K'
                                                                AND T3.ISSUYM = v_issuym
                                                                AND T3.MTESTMK = 'M'
                                                            )
                                         AND T.APNO NOT IN (SELECT T4.APNO FROM BADAPR T4
                                                             WHERE T4.APNO = T.APNO
                                                               AND T4.SEQNO = '0000'
                                                               AND T4.ISSUYM = v_issuym
                                                               AND T4.MTESTMK = 'F'
                                                               AND T4.PAYKIND Not In ('34','37')
                                                             GROUP BY T4.APNO
                                                             HAVING COUNT(T4.APNO)>1
                                                           )
                                          AND T.BEFISSUEAMT <> ( SELECT T5.BEFISSUEAMT FROM BADAPR T5
                                                                  WHERE T5.APNO = T.APNO
                                                                    AND T5.SEQNO = '0000'
                                                                    AND T5.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym, 'YYYYMM'), -1),'YYYYMM')
                                                                    AND T5.PAYYM = (SELECT MAX(T51.PAYYM) FROM BADAPR T51
                                                                                     WHERE T51.APNO = T5.APNO
                                                                                       AND T51.SEQNO = '0000'
                                                                                       AND T51.ISSUYM = T5.ISSUYM
                                                                                       AND T51.MTESTMK = 'F'
                                                                                       AND T51.APLPAYMK = '3'
                                                                                       AND T51.APLPAYDATE IS NOT NULL
                                                                                    )
                                                                    AND T5.MTESTMK = 'F'
                                                                    AND T5.PAYKIND Not In ('34','37')
                                                                    AND T5.APLPAYMK = '3'
                                                                    AND T5.APLPAYDATE IS NOT NULL
                                                               )
                                          AND T.CPIRATE = ( SELECT T7.CPIRATE FROM BADAPR T7
                                                             WHERE T7.APNO = T.APNO
                                                               AND T7.SEQNO = '0000'
                                                               AND T7.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym, 'YYYYMM'), -1),'YYYYMM')
                                                               AND T7.PAYYM = (SELECT MAX(T71.PAYYM) FROM BADAPR T71
                                                                                WHERE T71.APNO = T7.APNO
                                                                                  AND T71.SEQNO = '0000'
                                                                                  AND T71.ISSUYM = T7.ISSUYM
                                                                                  AND T71.MTESTMK = 'F'
                                                                                  AND T71.APLPAYMK = '3'
                                                                                  AND T71.APLPAYDATE IS NOT NULL
                                                                               )
                                                               AND T7.MTESTMK = 'F'
                                                               AND T7.PAYKIND Not In ('34','37')
                                                               AND T7.APLPAYMK = '3'
                                                               AND T7.APLPAYDATE IS NOT NULL
                                                          )
                                      ORDER BY T.APNO;


               --  新增月核案件檢核確認檔
                   insert into BARVCONFIRM (PAYCODE
                                           ,ISSUYM
                                           ,CONFIRMMK
                                          ) values (
                                           'K'
                                          ,v_issuym
                                          ,NULL
                                          );

             end if;

           dbms_output.put_line('新增失能月核案件檢核記錄檔資料完成');
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line('**Err:'||v_g_errMsg);
        end;
    --procedure sp_BA_GenReviewCaseK End

/********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PRO_PKG_BA_GENREVIEWCASE.sp_BA_GenReviewCaseS
        PURPOSE:         遺屬年金檢核案件產生作業

        PARAMETER(IN):  *v_i_issuym        (varChar2)       --核定年月

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2015/07/15  ChungYu      Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_GenReviewCaseS (
        v_i_issuym           in      varChar2
    ) is
        v_issuym             varChar2(6);
        v_confirmCount       Number;

        begin
            v_issuym       := to_Char(Sysdate,'YYYYMM');

            -- 若有傳入"核定年月"時,則以傳入的值為條件;無傳入時,則預設為系統年月
            if nvl(trim(v_i_issuym),' ')<>' ' then
               v_issuym := v_i_issuym;
            end if;

            -- 查詢檢核案件是否經過業務單位確認
            Select Count(*) into v_confirmCount From BARVCONFIRM
             Where PAYCODE = 'S'
               And ISSUYM  = v_issuym
               And CONFIRMMK = 'Y';

            --  尚未經過確認可重新產生檢核案件
            if (v_confirmCount = 0) then

                Delete BARVCASE
                 Where PAYCODE = 'S'
                   And ISSUYM  = v_issuym;

                Delete BARVCONFIRM
                 Where PAYCODE = 'S'
                   And ISSUYM  = v_issuym;

                /*
                   產生月試核未異動的續發案但給付月數大於1個月的案件資料。
                */

                       insert into BARVCASE (PAYCODE
                                            ,RVTYPE
                                            ,APNO
                                            ,MCHKTYP
                                            ,ISSUYM
                                            ,PAYYM
                                            ,OLDRATE
                                            ,BEFISSUEAMT
                                            ,PROCTIME
                                            ,PAYKIND
                                            ,BENNAME
                                          )
                                      SELECT 'S'
                                            ,'1'
                                            ,T.APNO
                                            ,T.MCHKTYP
                                            ,T.ISSUYM
                                            ,T.PAYYM
                                            ,''
                                            ,T.BEFISSUEAMT
                                            ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                            ,T.PAYKIND
                                            ,(Select A.BENNAME From BAAPPBASE A
                                               Where A.APNO = T.APNO
                                                 And A.SEQNO = '0000')
                                        FROM BADAPR T
                                       WHERE T.APNO LIKE 'S%'
                                         AND T.SEQNO = '0000'
                                         AND T.ISSUYM = v_issuym
                                         AND T.MTESTMK = 'F'
                                         AND T.PAYKIND <> '54'
                                         AND T.APNO IN (Select T1.APNO From BAAPPBASE T1
                                                         Where SUBSTR(T1.APNO,1,1) = 'S'
                                                           AND T1.SEQNO = '0000'
                                                           AND T1.CASETYP = '2'
                                                           AND T1.PROCSTAT = '50')
                                         AND T.APNO IN ( SELECT DISTINCT APNO FROM BADAPR T2
                                                          WHERE SUBSTR(T2.APNO,1,1) = 'S'
                                                            AND T2.SEQNO = '0000'
                                                            AND T2.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym , 'YYYYMM'), -1),'YYYYMM')
                                                            AND T2.MTESTMK = 'F'
                                                            AND T2.APLPAYMK = '3'
                                                            AND T2.APLPAYDATE IS NOT NULL
                                                        )
                                         AND T.APNO NOT IN ( SELECT DISTINCT(T3.APNO)
                                                               FROM BAEXALIST T3
                                                              WHERE SUBSTR(T3.APNO,1,1) = 'S'
                                                                AND T3.ISSUYM = v_issuym
                                                                AND T3.MTESTMK = 'M'
                                                            )
                                         AND T.APNO IN (SELECT T4.APNO FROM BADAPR T4
                                                         WHERE T4.APNO = T.APNO
                                                           AND T4.SEQNO = '0000'
                                                           AND T4.ISSUYM = v_issuym
                                                           AND T4.MTESTMK = 'F'
                                                           AND T4.PAYKIND <> '54'
                                                         GROUP BY T4.APNO
                                                        HAVING COUNT(T4.APNO)>1
                                                        )
                                         AND T.CPIRATE = ( SELECT T7.CPIRATE FROM BADAPR T7
                                                            WHERE T7.APNO = T.APNO
                                                               AND T7.SEQNO = '0000'
                                                               AND T7.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym, 'YYYYMM'), -1),'YYYYMM')
                                                               AND T7.PAYYM = (SELECT MAX(T71.PAYYM) FROM BADAPR T71
                                                                                WHERE T71.APNO = T7.APNO
                                                                                  AND T71.SEQNO = '0000'
                                                                                  AND T71.ISSUYM = T7.ISSUYM
                                                                                  AND T71.MTESTMK = 'F'
                                                                                  AND T71.APLPAYMK = '3'
                                                                                  AND T71.APLPAYDATE IS NOT NULL
                                                                               )
                                                               AND T7.MTESTMK = 'F'
                                                               AND T7.PAYKIND <> '54'
                                                               AND T7.APLPAYMK = '3'
                                                               AND T7.APLPAYDATE IS NOT NULL
                                                          )
                                       ORDER BY T.APNO;
               /*
                   產生月試核未異動的續發案但核定金額與上個月不同的案件資料。
                */

                       insert into BARVCASE (PAYCODE
                                            ,RVTYPE
                                            ,APNO
                                            ,MCHKTYP
                                            ,ISSUYM
                                            ,PAYYM
                                            ,OLDRATE
                                            ,BEFISSUEAMT
                                            ,PROCTIME
                                            ,PAYKIND
                                            ,BENNAME
                                            ,LASTISSUEAMT
                                          )
                                      SELECT 'S'
                                            ,'2'
                                            ,T.APNO
                                            ,T.MCHKTYP
                                            ,T.ISSUYM
                                            ,T.PAYYM
                                            ,''
                                            ,T.BEFISSUEAMT
                                            ,to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                            ,T.PAYKIND
                                            ,(Select A.BENNAME From BAAPPBASE A
                                               Where A.APNO = T.APNO
                                                 And A.SEQNO = '0000')
                                            ,(SELECT T6.BEFISSUEAMT FROM BADAPR T6
                                               WHERE T6.APNO = T.APNO
                                                 AND T6.SEQNO = '0000'
                                                 AND T6.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym, 'YYYYMM'), -1),'YYYYMM')
                                                 AND T6.PAYYM = (SELECT MAX(T61.PAYYM) FROM BADAPR T61
                                                                  WHERE T61.APNO = T6.APNO
                                                                    AND T61.SEQNO = '0000'
                                                                    AND T61.ISSUYM = T6.ISSUYM
                                                                    AND T61.MTESTMK = 'F'
                                                                    AND T61.APLPAYMK = '3'
                                                                    AND T61.APLPAYDATE IS NOT NULL
                                                                 )
                                                 AND T6.MTESTMK = 'F'
                                                 AND T6.APLPAYMK = '3'
                                                 AND T6.APLPAYDATE IS NOT NULL) AS BMONTHISSUEAMT
                                        FROM BADAPR T
                                       WHERE T.APNO LIKE 'S%'
                                         AND T.SEQNO = '0000'
                                         AND T.ISSUYM = v_issuym
                                         AND T.MTESTMK = 'F'
                                         AND T.PAYKIND <> '54'
                                         AND T.APNO IN (Select T1.APNO From BAAPPBASE T1
                                                         Where SUBSTR(T1.APNO,1,1) = 'S'
                                                           And T1.SEQNO = '0000'
                                                           AND T1.CASETYP = '2'
                                                           AND T1.PROCSTAT = '50')
                                         AND T.APNO IN ( SELECT DISTINCT APNO FROM BADAPR T2
                                                          WHERE SUBSTR(T2.APNO,1,1) = 'S'
                                                            AND T2.SEQNO = '0000'
                                                            AND T2.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym, 'YYYYMM'), -1),'YYYYMM')
                                                            AND T2.MTESTMK = 'F'
                                                            AND T2.APLPAYMK = '3'
                                                            AND T2.APLPAYDATE IS NOT NULL
                                                       )
                                         AND T.APNO NOT IN ( SELECT DISTINCT(T3.APNO)
                                                               FROM BAEXALIST T3
                                                              WHERE SUBSTR(T3.APNO,1,1) = 'S'
                                                                AND T3.ISSUYM = v_issuym
                                                                AND T3.MTESTMK = 'M'
                                                            )
                                         AND T.APNO NOT IN (SELECT T4.APNO FROM BADAPR T4
                                                             WHERE T4.APNO = T.APNO
                                                               AND T4.SEQNO = '0000'
                                                               AND T4.ISSUYM = v_issuym
                                                               AND T4.MTESTMK = 'F'
                                                               AND T4.PAYKIND <> '54'
                                                             GROUP BY T4.APNO
                                                             HAVING COUNT(T4.APNO)>1
                                                           )
                                          AND T.BEFISSUEAMT <> ( SELECT T5.BEFISSUEAMT FROM BADAPR T5
                                                                  WHERE T5.APNO = T.APNO
                                                                    AND T5.SEQNO = '0000'
                                                                    AND T5.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym, 'YYYYMM'), -1),'YYYYMM')
                                                                    AND T5.PAYYM = (SELECT MAX(T51.PAYYM) FROM BADAPR T51
                                                                                     WHERE T51.APNO = T5.APNO
                                                                                       AND T51.SEQNO = '0000'
                                                                                       AND T51.ISSUYM = T5.ISSUYM
                                                                                       AND T51.MTESTMK = 'F'
                                                                                       AND T51.APLPAYMK = '3'
                                                                                       AND T51.APLPAYDATE IS NOT NULL
                                                                                    )
                                                                    AND T5.MTESTMK = 'F'
                                                                    AND T5.PAYKIND <> '34'
                                                                    AND T5.APLPAYMK = '3'
                                                                    AND T5.APLPAYDATE IS NOT NULL
                                                               )
                                          AND T.CPIRATE = ( SELECT T7.CPIRATE FROM BADAPR T7
                                                             WHERE T7.APNO = T.APNO
                                                               AND T7.SEQNO = '0000'
                                                               AND T7.ISSUYM = TO_CHAR(ADD_MONTHS(TO_DATE( v_issuym, 'YYYYMM'), -1),'YYYYMM')
                                                               AND T7.PAYYM = (SELECT MAX(T71.PAYYM) FROM BADAPR T71
                                                                                WHERE T71.APNO = T7.APNO
                                                                                  AND T71.SEQNO = '0000'
                                                                                  AND T71.ISSUYM = T7.ISSUYM
                                                                                  AND T71.MTESTMK = 'F'
                                                                                  AND T71.APLPAYMK = '3'
                                                                                  AND T71.APLPAYDATE IS NOT NULL
                                                                               )
                                                               AND T7.MTESTMK = 'F'
                                                               AND T7.PAYKIND <> '54'
                                                               AND T7.APLPAYMK = '3'
                                                               AND T7.APLPAYDATE IS NOT NULL
                                                          )
                                      ORDER BY T.APNO;


               --  新增月核案件檢核確認檔
                   insert into BARVCONFIRM (PAYCODE
                                           ,ISSUYM
                                           ,CONFIRMMK
                                          ) values (
                                           'S'
                                          ,v_issuym
                                          ,NULL
                                          );

             end if;

           dbms_output.put_line('新增遺屬月核案件檢核記錄檔資料完成');
        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line('**Err:'||v_g_errMsg);
        end;
    --procedure sp_BA_GenReviewCaseS End

End;
/

