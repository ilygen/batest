create or replace procedure ba.SP_BA_CALL_BA_LOG2ADM(v_i_tranbegindate in varChar2,
                                                          v_i_tranenddate   in varChar2) authid definer is

  Cursor c_mquerylog_1 is
    select substr(acstime,1,8)as acstime  from mmaccesslg_bli
    where substr(acstime,1,8) >= v_i_tranbegindate
    and   substr(acstime,1,8) <= v_i_tranenddate
	group by substr(acstime,1,8)
	order by substr(acstime,1,8);

begin
  begin

    for v_mqueryCur1 in c_mquerylog_1 Loop
     dbms_output.put_line('SP_BA_CALL_BA_LOG2ADM---[' || v_mqueryCur1.acstime || ' ]');
     ba_log2adm(v_mqueryCur1.acstime);
    end loop;
  end;
end SP_BA_CALL_BA_LOG2ADM;
/

