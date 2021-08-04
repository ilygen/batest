create or replace procedure ba.Ba_InsertBAEXALIST
/******************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            Ba_InsertBAEXALIST
    PURPOSE:         將200911核定年月月試核定核定檔異動項回寫至(BA.BAEXALIST)的月編審異動清單記錄檔資料

    USAGE:
    I/O        (Type)Object Name
    ---------  ---------------------------------------------------------------
               (Table)BADAPR           勞保年金給付核定檔
               (Table)BAEXALIST        月編審異動清單記錄檔

    REVISIONS:
    Ver        Date        Author           Description
    ---------  ----------  ---------------  ------------------------------------
    1.0        2009/11/17  ChungYu          Created this procedure.

    NOTES:

******************************************************************************/
authid definer is
     v_apno       BADAPR.APNO%type;             --受理編號
     v_chglist    BADAPR.Chglist%type;          --異動註記列表
     v_evtname    BAAPPBASE.EVTNAME%type;       --事故者姓名
     v_casetyp    BAAPPBASE.CASETYP%type;       --案件類別
     v_chkman     BAAPPBASE.Chkman%type;        --審核人員
     v_upcasecode BAEXALIST.UPCAUSECODE%type;   --異動代碼
     v_minpayym   BADAPR.Payym%type;            --最小給付年月
     v_maxpayym   BADAPR.Payym%type;            --最大給付年月
     n_count      Number;                       --異動代碼數目
     n_chgcount   Number;                       --異動代碼項目
     n_pageno     Number;                       --頁次
     n_seqno      Number;                       --序號

     CURSOR cur_ExaApnoList
     IS
        Select Apno,Chglist from BADAPR t
         Where issuym = '200911'
           And mtestmk = 'M'
           And chglist is not null
        Order By Apno;

     CURSOR cur_ApData (i_apno VARCHAR2)
         IS
            Select EVTNAME,
                   CASETYP,
                   Chkman
              From BAAPPBASE
             Where Apno = i_apno
               And Seqno = '0000';

     CURSOR cur_PayYm (p_apno VARCHAR2)
         IS
            SELECT min(PAYYM), max(PAYYM)
               FROM BADAPR
              Where issuym = '200911'
                And mtestmk = 'M'
                And Apno = p_apno
              Group by Apno ;
begin
    n_pageno   := 1;
    n_seqno    := 1;
    OPEN cur_ExaApnoList;
    LOOP
         --取得200911月試核核定檔異動項次清單及受理編號
         FETCH cur_ExaApnoList INTO v_apno,v_chglist;
         EXIT WHEN cur_ExaApnoList%NOTFOUND;

         --取得受理編號案件：事故者姓名、案件類別、審核人員
         OPEN cur_ApData(v_apno);
         FETCH cur_ApData INTO v_evtname,v_casetyp,v_chkman;
         CLOSE cur_ApData;
         n_chgcount := 1;

         WHILE n_chgcount < length(v_chglist)
         LOOP
             v_upcasecode := '';
             v_upcasecode := SUBSTR(v_chglist,n_chgcount,2);

             --查詢受理案件異動代碼是否已回寫至異動清單檔
             SELECT COUNT(*) COUNT INTO n_count
               FROM BAEXALIST
              where APNO = v_apno
                AND ISSUYM = '200911'
                AND MTESTMK = 'M'
                AND UPCAUSECODE = v_upcasecode;

             v_minpayym := '';
             v_maxpayym := '';

             --取得本次月試核編審的最小給付年月、最大給付年月
             OPEN cur_PayYm(v_apno);
             FETCH cur_PayYm INTO v_minpayym,v_maxpayym;
             CLOSE cur_PayYm;

             IF n_count = 0
             THEN
                --新增受理案件異動代碼至異動清單檔
                Insert Into BAEXALIST (RPTTYP,RPTDATE,PAGENO,SEQNO,PAYTYP,MTESTMK,APNO,EVTNAME,CASETYP,ISSUYM,PAYSYM,PAYEYM,CHKMAN,UPCAUSECODE)
                               VALUES ('001','20091117',n_pageno,n_seqno,'L','M',v_apno,v_evtname,v_casetyp,'200911',v_minpayym,v_maxpayym,v_chkman,v_upcasecode);
                n_seqno := n_seqno +1;

                IF MOD(n_seqno,22) = 0
                THEN
                    n_pageno := n_pageno +1;
                END IF;
             END IF;
             n_chgcount := n_chgcount +3;
         END LOOP;

    END LOOP;
    CLOSE cur_ExaApnoList;
    EXCEPTION
    WHEN OTHERS
    THEN
         DBMS_OUTPUT.PUT_LINE(SQLERRM);
end Ba_InsertBAEXALIST;
/

