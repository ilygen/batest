create or replace procedure ba.Ba_updatePASSLIST
/******************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            Ba_updatePASSLIST
    PURPOSE:         更新(BA.BAPASSLIST)的月核定合格案件資料

    USAGE:
    I/O        (Type)Object Name
    ---------  ---------------------------------------------------------------
               (Table)BAPASSLIST        勞保年金月核定案件列表
               (Table)BAPASSTEMP        勞保年金月核定暫存檔

    REVISIONS:
    Ver        Date        Author           Description
    ---------  ----------  ---------------  ------------------------------------
    1.0        2009/02/21  ChungYuu        Created this procedure.

    NOTES:

******************************************************************************/
authid definer is
     v_apno       BAPASSTEMP.APNO%type;
     n_apno       NUMBER;
     n_seqno      NUMBER;

     CURSOR cur_pass
     IS
        Select Apno From BAPASSTEMP
        Order BY Apno;

     CURSOR cur_passliss (i_apno VARCHAR2)
         IS
            Select COUNT(Apno) AS COUNT From BAPASSLIST
             Where Apno = i_apno;
begin
    n_seqno := 18636;
    OPEN cur_pass;
    LOOP


         FETCH cur_pass INTO v_apno;
         EXIT WHEN cur_pass%NOTFOUND;
         OPEN cur_passliss(v_apno);
         FETCH cur_passliss INTO n_apno;
         CLOSE cur_passliss;
         IF n_apno > 0
         THEN
             UPDATE BAPASSLIST
                SET MANCHKMK = ' '
              WHERE APNO =  v_apno;
             COMMIT;
         ELSE
             n_seqno := n_seqno+1;

             INSERT INTO BAPASSLIST (
             SEQNUM,
             ISSUYM,
             PAYYM,
             APNO,
             MANCHKMK,
             EXEDATE,
             CHKDATE) VALUES (
             n_seqno+1,
             '200902',
             '200901',
             v_apno,
             ' ',
             '20090220',
             '20090220');
              COMMIT;
         END IF;

    END LOOP;
    CLOSE cur_pass;
    EXCEPTION
    WHEN OTHERS
    THEN
         DBMS_OUTPUT.PUT_LINE(SQLERRM);

end Ba_updatePASSLIST;
/

