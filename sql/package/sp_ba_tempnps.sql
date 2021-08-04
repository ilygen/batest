CREATE OR REPLACE Procedure BA.SP_BA_TEMPNPS

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            SP_BA_TEMPNPS
        PURPOSE:         國保遺屬年金案件勾稽勞保資料。

        PARAMETER(IN):

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2015/01/27  Justin Yu    Created this Package.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/

authid definer Is

    v_apno             VARCHAR2(13 BYTE)             := '';        --受理編號
    v_appdate          VARCHAR2(8 BYTE)              := '';        --申請日期
    v_evtdt            VARCHAR2(8 BYTE)              := '';        --死亡日期
    v_evtidnno         VARCHAR2(20 BYTE)             := '';        --被保險人身分證號
    v_evteebirt        VARCHAR2(8 BYTE)              := '';        --出生日期
    v_evteename        VARCHAR2(50 CHAR)             := '';        --姓名
    v_paycount         VARCHAR2(2 BYTE)              := '';        --符合人數
    v_payaddrate       NUMBER(6,2)                   := 0;         --加計比例
    v_issuefamt        NUMBER(6)                     := 0;         --核定金額
    v_addissueamt      NUMBER(6)                     := 0;         --加計後勞保年金金額
    v_nitrmy           VARCHAR2(8 BYTE)              := '';        --勞保年資
    v_havgwg           NUMBER(6)                     := 0;         --平均薪資
    v_laboramt         NUMBER(6)                     := 0;         --勞保年金金額
    v_sbmk             VARCHAR2(1 BYTE)              := '';        --勞保領取給付註記
    v_CIPB             CI.CIPB%ROWTYPE;
    v_PL0126M0OUT      CI.PL0126M0OUT%ROWTYPE;
    v_ciid             CI.CIPB.CIID%TYPE             := '';        --CIPB.CIID
    v_countALL         NUMBER                        := 0;         --總筆數

--國保遺屬年金案件
CURSOR c_dataCur_TEMPNPS IS
        select b.apno, 
               b.appdate, 
               b.evtdt, 
               b.evtidnno, 
               b.evteebirt, 
               b.evteename,
               (select count(c.apno)
                from nbdapr c
                where c.mapno = b.apno and c.seqno <> '0000' and c.seqno like '%00'
                  and c.issuym = '201412' and c.payym = '201411' and c.mtestflg = 'F'
                  and c.issuefamt > 0 and c.acceptmark = 'Y' and c.manchkflg = 'Y') as paycount,
               round(a.issuefamt/3500-1,2) as payaddrate, 
               a.issuefamt--,
               --round(issuefamt*(1+(round(a.issuefamt/3500-1,2))),0) as addissueamt
        from nps.nbdapr a, nps.nbappbase b
        where a.apno like 'D%' and a.apno = a.mapno and a.seqno = '0000'
          and a.issuym = '201412' and a.payym = '201411' and a.mtestflg = 'F'
          and a.issuefamt > 0 and a.acceptmark = 'Y' and a.manchkflg = 'Y'
          and b.apno = a.apno and b.seqno = a.seqno;
      
        CURSOR get_CIPB_DATA(i_idn IN VARCHAR2,i_brdte IN VARCHAR2) IS
          SELECT *  FROM CI.CIPB WHERE FTYP = 'L' AND IDN LIKE i_idn || '%' AND BRDTE = i_brdte;

Begin
         execute immediate 'truncate table TEMPNPS';
         For v_CurTEMPNPS In c_dataCur_TEMPNPS Loop
             v_countALL:= v_countALL + 1;
             v_havgwg      := 0;
             v_nitrmy      := '';
             v_laboramt    := 0;      
             v_addissueamt := 0;       
             v_sbmk        := '';   
             v_ciid        := '';      
                  
             FOR r_cipb IN get_CIPB_DATA(v_CurTEMPNPS.evtidnno ,v_CurTEMPNPS.evteebirt) LOOP
                  v_ciid := r_cipb.ciid;
                  if(r_cipb.sbmk1 in ('2','4') or r_cipb.sbmk2 in ('2','3')) Then
                      v_sbmk := 'Y';
                  End if;          
             END LOOP;
             CI.PKG_CI_T01.CI_T03('L',
                                  v_ciid,  --ciid
                                  60,      --avgm
                                  '',      --p_cvspt
                                  '',      --p_cvept
                                  '',      --p_ynb
                                  '',      --p_smk
                                  '',      --p_tmk
                                  '',      --p_wmk
                                  '',      --p_dwcmk
                                  '',      --p_runos
                                  v_PL0126M0OUT );
             /*CI.PKG_CI_T01.CI_T03('L',
                                  v_CurTEMPNPS.evtidnno,
                                  v_CurTEMPNPS.evteebirt,
                                  v_CurTEMPNPS.evteename,
                                  '',      --p_cvspt
                                  '',      --p_cvept
                                  '',      --p_ynb
                                  '',      --p_smk
                                  '',      --p_tmk
                                  '',      --p_wmk
                                  '',      --p_dwcmk
                                  '',      --p_runos
                                  v_PL0126M0OUT );*/
              
              If (v_PL0126M0OUT.Idkey is not null) Then
                 v_havgwg      := v_PL0126M0OUT.havgwg;
                 v_nitrmy      := round((nvl(v_PL0126M0OUT.nitrmy,0) + nvl(v_PL0126M0OUT.nitrmm,0)/12),2);
                 v_laboramt    := round(v_nitrmy*v_havgwg*0.0155,0);
                 v_addissueamt := round(v_laboramt*(1 + v_CurTEMPNPS.payaddrate),0);
              End if; 
              
              Insert Into TEMPNPS VALUES (
                                  v_CurTEMPNPS.apno,
                                  v_CurTEMPNPS.appdate,
                                  v_CurTEMPNPS.evtdt,
                                  v_CurTEMPNPS.evtidnno,
                                  v_CurTEMPNPS.evteebirt,
                                  v_CurTEMPNPS.evteename,
                                  v_CurTEMPNPS.paycount,
                                  v_CurTEMPNPS.payaddrate,
                                  v_CurTEMPNPS.issuefamt,
                                  v_addissueamt,
                                  v_nitrmy,
                                  v_havgwg,
                                  v_laboramt,
                                  v_sbmk); 
         End Loop;
         commit;
         dbms_output.put_line(' 總筆數                 = ' || v_countALL);

End SP_BA_TEMPNPS;
/

