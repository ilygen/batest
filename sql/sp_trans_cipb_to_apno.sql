create or replace procedure ba.SP_Trans_CIPB_TO_APNO
  /********************************************************************************
      PROJECT:         BLI-BaWeb 勞保年金給付系統
      NAME:            SP_Trans_CIPB_TO_APNO
      PURPOSE:         新建 CIPB_TEMP、CIPT_TEMP、CIPG_TEMP 整批將增加Apno、 Seqno 兩欄位的勞保年金CIPB、CIPT、CIPG 資料轉入。

      PARAMETER(IN):

      PARAMETER(OUT):

      USAGE:
      I/O   (Type)Object Name
      ----  -----------------------------------------------------------------------

      REVISIONS:
      Ver   Date        Author       Description
      ----  ----------  -----------  ----------------------------------------------
      1.0   2014/02/18  ChungYu Lin  Created this Package.

      NOTES:
      1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

  ********************************************************************************/
authid definer is

        v_count    NUMBER      := 0;
        v_IDNNO    ba.BAFAMILY.FAMIDNNO%Type := '';


        Cursor c_dataCur_APLIST is
         SELECT a.APNO,
                a.SEQNO,
                a.FAMIDNNO
           FROM BAFAMILY a
          Where a.FAMIDNNO in (Select Distinct SUBSTR(t1.IDN,1,10) From CIPB t1
                                where t1.procdate = '20140218000000'
                               );

        Cursor c_dataCur_CIPBLIST is
          SELECT t.* FROM CIPB t
           Where t.idn like v_IDNNO||'%'
             and t.procdate <> '20140218000000'
           Order By t.intyp, t.idn, t.apno, t.seqno;

begin

        v_count      := 0;
        --  讀取勞保年金所有的受理案件清單

        FOR v_CurAPLIST IN c_dataCur_APLIST LOOP
        BEGIN
             v_IDNNO      := '';
             v_IDNNO := v_CurAPLIST.FAMIDNNO;

          FOR v_CurCIPBLIST IN c_dataCur_CIPBLIST LOOP
          BEGIN

             -- 讀取受理編號及序號在CIPB的筆數
             Select Count(*) Into v_count From CIPT t
              Where t.intyp = v_CurCIPBLIST.Intyp
                And SUBSTR(t.IDN,1,10)  = v_CurAPLIST.FAMIDNNO
                And t.apno  = v_CurAPLIST.Apno
                And t.apseqno = v_CurAPLIST.Seqno
                and rownum = 1;


             If v_count = 0 then

                   Insert Into CIPT       (intyp, idn, uno, unock, seqno,
                                           txcd, efdte, wage, dept, bsmn,
                                           staff, sidmk, tsmk, nrpmk, fill,
                                           prodte, apno, apseqno)
                                  ( Select intyp, idn, uno, unock, seqno,
                                           txcd, efdte, wage, dept, bsmn,
                                           staff, sidmk, tsmk, nrpmk, fill,
                                           '20140218000000', v_CurAPLIST.APNO, v_CurAPLIST.SEQNO
                                      From CIPT t
                                     Where t.intyp = v_CurCIPBLIST.Intyp
                                       And t.idn like v_IDNNO || '%'
                                       And t.apno  = v_CurCIPBLIST.Apno
                                       And t.apseqno = v_CurCIPBLIST.Seqno
                                   );

                    COMMIT;
             end if;
             EXCEPTION

             WHEN OTHERS THEN
                  dbms_output.put_line( v_CurCIPBLIST.Idn ||'false.');
             END;
             END LOOP;
         EXCEPTION
             WHEN OTHERS THEN
                  dbms_output.put_line(' SP_Trans_CIPB_TO_APNO false.');
             END;
         END LOOP;
         COMMIT;

end SP_Trans_CIPB_TO_APNO;
/

