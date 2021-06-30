create or replace procedure ba.sp_BA_genBAOverDueFile
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            sp_BA_genBAOverDueFile
    PURPOSE:         產生轉催/轉呆報表資料

    PARAMETER(IN):  *v_i_paycode       (varChar2)       --給付別
                     v_i_procYm        (varChar2)       --處理年月
                     v_i_payseqno      (varChar2)
                     v_i_cprnDt        (varChar2)       --處理日期

    PARAMETER(OUT):
                     v_o_flag          (varChar2)       --傳出值 0-成功；1-無資料 空值:失敗
    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2015/07/15  ZeHua Chen    Created this procedure.

    NOTES:
    1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。
    2.v_i_golivedate=核付案件申請期限日期。當審核、決行上線後,此日期不需再傳入。

********************************************************************************/

(
    v_i_paycode             in      varChar2,
    v_i_procYm              in      varChar2,
    v_i_cprnDt              in      varChar2,
    v_i_rpttyp              in      varChar2,
    v_i_apno                in      varChar2,
    v_i_deadYy              in      varChar2,
    v_o_flag                out     varChar2
) authid definer is
    v_ProgName               varChar2(200) := 'sp_BA_genBAOverDueFile';
    v_errMsg                 varChar2(2000);
    v_g_flag                 varchar2(1);
begin
  v_errMsg := ' ';
  v_g_flag := '0';


  if  v_i_rpttyp = '16' then
        if v_i_paycode = 'S' then
             PKG_BA_genPayRPT.sp_BA_genPayRPT_16_S(v_i_paycode,v_i_procYm,v_i_cprnDt,v_g_flag);
             if v_g_flag='1' then
                PKG_BA_genPayRPTSUMAccount.sp_BA_genPaySUMRPT_16_S(v_i_paycode,v_i_procYm,v_i_cprnDt,v_g_flag);
                PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_16_S(v_i_paycode,v_i_procYm,v_i_cprnDt,v_g_flag);
             end if;
        elsif v_i_paycode = 'L' then
             PKG_BA_genPayRPT.sp_BA_genPayRPT_16(v_i_paycode,v_i_procYm,v_i_cprnDt ,v_g_flag);
             if v_g_flag='1' then
                PKG_BA_genPayRPTSUMAccount.sp_BA_genPaySUMRPT_16_L(v_i_paycode,v_i_procYm,v_i_cprnDt,v_g_flag);
                PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_16_L(v_i_paycode,v_i_procYm,v_i_cprnDt,v_g_flag);
             end if;
        elsif v_i_paycode = 'K' then
             PKG_BA_genPayRPT.sp_BA_genPayRPT_16(v_i_paycode,v_i_procYm,v_i_cprnDt,v_g_flag);
             if v_g_flag='1' then
                PKG_BA_genPayRPTSUMAccount.sp_BA_genPaySUMRPT_16_K(v_i_paycode,v_i_procYm,v_i_cprnDt,v_g_flag);
                PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_16_K(v_i_paycode,v_i_procYm,v_i_cprnDt,v_g_flag);
             end if;
        end if;

  elsif v_i_rpttyp = '17' then
      if v_i_paycode = 'S' then
           PKG_BA_genPayRPT.sp_BA_genPayRPT_17_S(v_i_paycode,v_i_cprndt,v_i_deadyy,v_i_apno, v_g_flag);
           if v_g_flag='1' then
               PKG_BA_genPayRPTSUMAccount.sp_BA_genPaySUMRPT_17_S(v_i_paycode,v_i_cprndt,v_i_deadyy, v_g_flag);
               PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_17_S(v_i_paycode,v_i_cprndt,v_i_deadyy, v_g_flag);
           end if;
      elsif v_i_paycode = 'L' then
           PKG_BA_genPayRPT.sp_BA_genPayRPT_17(v_i_paycode,v_i_cprndt,v_i_deadyy,v_i_apno, v_g_flag);
           if v_g_flag='1' then
              PKG_BA_genPayRPTSUMAccount.sp_BA_genPaySUMRPT_17_L(v_i_paycode,v_i_cprndt,v_i_deadyy, v_g_flag);
              PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_17_L(v_i_paycode,v_i_cprndt,v_i_deadyy, v_g_flag);
           end if;
      elsif v_i_paycode = 'K' then
           PKG_BA_genPayRPT.sp_BA_genPayRPT_17(v_i_paycode,v_i_cprndt,v_i_deadyy,v_i_apno, v_g_flag);
           dbms_output.put_line(v_g_flag);
           if v_g_flag='1' then
              PKG_BA_genPayRPTSUMAccount.sp_BA_genPaySUMRPT_17_K(v_i_paycode,v_i_cprndt,v_i_deadyy, v_g_flag);
              PKG_BA_genPayRPTSUMAccount.sp_BA_genPayACCOUNTRPT_17_K(v_i_paycode,v_i_cprndt,v_i_deadyy, v_g_flag);
           end if;
      end if;
  end if;
  v_o_flag := v_g_flag;
exception
    when others
        then
            v_errMsg := SQLErrm;
            v_g_flag := '';
            v_o_flag := v_g_flag;
            dbms_output.put_line(RPAD('**Err:'||v_ProgName,85,'-')||v_errMsg||'>>');
end sp_BA_genBAOverDueFile;
/

