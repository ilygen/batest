create or replace procedure ba.SP_BA_MMACCESSLG_LOGTRANBATCH(v_i_date in varChar2) authid definer is
/* ============================================================================
   程式目的：依據輸入日期將BA.MMACCESSLG, BA.MMAPLOG, BA.MMQUERYLOG複製到BA.MMACCESSLG_BLI
   執行方式：EXEC SP_BA_MMACCESSLG_LOGTRANBATCH(資料日期)
   傳入參數：v_i_date   1.空值-取系統日前一天, 2.ALL-取全量資料, 3.日期-取傳入之日期
   傳出參數：無
   相關表格：
   維護人員        日期    說明
   -----------   --------  ----------------------------------------------------
   Justin Yu     20130322  V1.0
   ChugnYu       20141003  V2.0  DBA 於2014/10/01 提出本支procedure執行時間過久，耗用資源過多，
                           因此調整Query SQL，將Join Table 拆開改分段執行。
   ChugnYu       20141006  V2.1  調整 MMAPLOG 轉檔查詢條件。                        
   ============================================================================*/ 
   
  v_g_genFlag  boolean := false;
  v_g_rowCount Number := 0;
  v_g_Count    Number := 0;
  v_seq_no     Number := 0;
  v_in_count   Number default 0;
  v_out_count  Number default 0;
  v_proc_count Number default 0;
  v_err_count  Number default 0;
  v_step       varChar2(1000) default null;
  v_memo       varChar2(1000) default null;
  v_table_name varChar2(1000) default null;
  v_i_jobno    MMJOBLOG.JOB_NO%type;
  v_job_kind   MMJOBLOG.JOB_KIND%type := '';
  v_job_name   MMJOBLOG.JOB_NAME%type := '';
  v_job_no     varChar2(1000);
  v_job_id     varChar2(1000);
  v_sqlcode    varChar2(1000);
  v_sqlerrm    varChar2(1000);
  v_date       varChar2(8) := '';
  v_chkflg     boolean     := true;
  v_SCRNO      MMACCESSLG.SCRNO%Type;         -- 2014/10/03  Add By ChungYu
  v_BAAPPBASE  BAAPPBASE%ROWTYPE;             -- 2014/10/03  Add By ChungYu

  Cursor c_mquerylog_1 is
    select to_Char(to_Number(substr(a.QYTIME, 0, 3)) + 1911 ||
                   substr(a.QYTIME, 4, 10)) as QYTIME,
           a.QUERYMAN,
           a.DEPTID,
           a.TERMED,
           a.QYCONDITION,
           a.SNO,
           (select extractValue(value(x), '//Value/text()') as ITEMVALUE
              from table(XmlSequence(EXTRACT((select XmlType(m.QYCONDITION)
                                               from MMQUERYLOG m
                                              where m.SNO = a.SNO
                                                and m.TABLENAME =
                                                    'BAAPPBASE'),
                                             '//Condition'))) X
             where extractValue(value(x), '//ColumnName/text()') =
                   'BENIDNNO') as EVTIDNNO,
           (select extractValue(value(x), '//Value/text()') as ITEMVALUE
              from table(XmlSequence(EXTRACT((select XmlType(m.QYCONDITION)
                                               from MMQUERYLOG m
                                              where m.SNO = a.SNO
                                                and m.TABLENAME =
                                                    'BAAPPBASE'),
                                             '//Condition'))) X
             where extractValue(value(x), '//ColumnName/text()') = 'BENNAME') as EVTNAME,
           (select extractValue(value(x), '//Value/text()') as ITEMVALUE
              from table(XmlSequence(EXTRACT((select XmlType(m.QYCONDITION)
                                               from MMQUERYLOG m
                                              where m.SNO = a.SNO
                                                and m.TABLENAME =
                                                    'BAAPPBASE'),
                                             '//Condition'))) X
             where extractValue(value(x), '//ColumnName/text()') = 'APNO') as APNO,
           (select extractValue(value(x), '//Value/text()') as ITEMVALUE
              from table(XmlSequence(EXTRACT((select XmlType(m.QYCONDITION)
                                               from MMQUERYLOG m
                                              where m.SNO = a.SNO
                                                and m.TABLENAME =
                                                    'BAAPPBASE'),
                                             '//Condition'))) X
             where extractValue(value(x), '//ColumnName/text()') =
                   'BENBRDATE') as EVTBRDATE
      from MMQUERYLOG a
     where a.TABLENAME = 'BAAPPBASE'
       and a.QYTIME like Decode(v_date,Null,'1',fn_BA_transDateValue(v_date,'1')) || '%'   -- 2014/10/06 Modify By ChungYu 修改v_i_date傳入ALL時為空白，無法查詢出任何資料，改轉民國1XX年資料。
       and (select count(extractValue(value(x), '//Value/text()')) as ITEMVALUE
              from table(XmlSequence(EXTRACT((select XmlType(m.QYCONDITION)
                                               from MMQUERYLOG m
                                              where m.TABLENAME =
                                                    'BAAPPBASE'
                                                and m.SNO = a.SNO),
                                             '//Condition'))) X
             where extractValue(value(x), '//ColumnName/text()') =
                   'BENBRDATE'
                or (extractValue(value(x), '//ColumnName/text()') = 'APNO' and
                    length(extractValue(value(x), '//Value/text()')) = 12)
                or extractValue(value(x), '//ColumnName/text()') = 'BENNAME'
                or extractValue(value(x), '//ColumnName/text()') =
                   'BENIDNNO') > 0
       and (select lengthb(extractValue(value(x), '//Value/text()')) as ITEMLENGTH -- 20130423 added by Kiyomi 排除APNO的值超出欄位設定最大長度的資料
              from table(XmlSequence(EXTRACT((select XmlType(m.QYCONDITION)
                                               from MMQUERYLOG m
                                              where m.SNO = a.SNO
                                                and m.TABLENAME =
                                                    'BAAPPBASE'),
                                             '//Condition'))) X
             where extractValue(value(x), '//ColumnName/text()') = 'APNO') <= 14;

  Cursor c_mupdatelog_2 is
    select to_Char(to_Number(substr(a.CHGTIME, 0, 3)) + 1911 ||
                   substr(a.CHGTIME, 4, 10)) as CHGTIME,
           a.MODIFYMAN,
           a.DEPTID,
           a.TERMED,
           substr(a.PKFIELD, 13) as PKFIELD,
           a.CHGCODE,
           a.SNO
      from MMAPLOG a
     where TABLENAME = 'BAAPPBASE'
       And a.CHGCODE in ('U', 'D')
       And a.PGMCODE not like 'BAAA%'
       and a.CHGTIME like  Decode(v_date,Null,'1',fn_BA_transDateValue(v_date,'1')) || '%';    -- 2014/10/06 Modify By ChungYu 修改v_i_date傳入ALL時為空白，無法查詢出任何資料

begin
  begin
    v_chkflg := TRUE;
    if v_i_date IS NULL THEN
       --當沒有輸入日期參數時
       --直接以系統日期前一天取代
       v_date := TO_CHAR(SYSDATE - 1,'YYYYMMDD');
    elsif v_i_date = 'ALL' THEN
       --當輸入'ALL'字串時,取得所有資料
       v_date := '';
    else
       --若有輸入日期參數時
       --須先檢查日期是否符合萬年曆規則
       --符合則以輸入的日期取代
       --不符合則直接跳開不執行
       begin
         v_date := TO_CHAR(TO_DATE(v_i_date,'YYYYMMDD'),'YYYYMMDD');
       EXCEPTION
         WHEN OTHERS THEN
           v_chkflg := FALSE;
       end;
    end if;

    v_g_rowCount := 0;
    v_g_Count    := 0;
    v_i_jobno    := to_Char(Sysdate, 'YYYYMMDDHH24MISS');

    for v_mqueryCur1 in c_mquerylog_1 Loop
      v_g_genFlag  := true;
      v_g_rowCount := v_g_rowCount + 1;
      v_g_Count    := v_g_Count + 1;
      v_SCRNO      := '';               -- 2014/10/03  Add By ChungYu

      --  2014/10/03  Add By ChungYu

      Select t.SCRNO Into v_SCRNO From MMACCESSLG t
       Where t.SNO = v_mqueryCur1.SNO;
      
      --  2014/10/03  Add By ChungYu

      insert into MMACCESSLG_BLI
        (ACSTIME,
         APNAME,
         PSNO,
         DEPID,
         TRNSID,
         TERMID,
         STYPE,
         UNO,
         YM,
         IDNO,
         PROPOSER,
         PROC,
         APNO,
         EVBRTH,
         QMK,
         ACCTYPE,
         SOURCE,
         NPIDS)
      values
        (v_mqueryCur1.QYTIME,
         '',
         v_mqueryCur1.QUERYMAN,
         v_mqueryCur1.DEPTID,
         v_SCRNO,
         v_mqueryCur1.TERMED,
         'BA',
         '',
         '',
         v_mqueryCur1.EVTIDNNO,
         v_mqueryCur1.EVTNAME,
         '',
         v_mqueryCur1.APNO,
         v_mqueryCur1.EVTBRDATE,
         'SNO=' || v_mqueryCur1.SNO,
         'Q',
         '',
         '');

      if mod(v_g_Count, 500) = 0 then
        commit;
      end if;
    end loop;
    commit;

    if v_g_genFlag = true then
      dbms_output.put_line('SP_BA_MMACCESSLG_LOGTRANBATCH--稽核軌跡紀錄檔(for MMQUERYLOG)-[ ' ||
                           v_g_rowCount || ' ]');
    else
      dbms_output.put_line('SP_BA_MMACCESSLG_LOGTRANBATCH--稽核軌跡紀錄檔(for MMQUERYLOG)-[ ' ||
                           v_g_rowCount || ' ]-NoData');
    end if;
  end;

  begin
    v_g_rowCount := 0;
    for v_mupdateCur1 in c_mupdatelog_2 Loop
      v_g_genFlag  := true;
      v_g_rowCount := v_g_rowCount + 1;
      v_g_Count    := v_g_Count + 1;
      v_SCRNO      := '';               -- 2014/10/03  Add By ChungYu
      
      --  2014/10/03  Add By ChungYu

      Select t.SCRNO Into v_SCRNO From MMACCESSLG t
       Where t.SNO = v_mupdateCur1.SNO;
      
      If (v_SCRNO Is Not Null) Then
      Begin  
         Select c.* Into v_BAAPPBASE From BAAPPBASE c
          Where c.BAAPPBASEID =v_mupdateCur1.PKFIELD
            and (c.LSUBNO is not null or c.EVTIDNNO is not null 
              or c.EVTNAME is not null or c.APNO is not null 
              or c.EVTBRDATE is not null or c.EVTIDS is not null);
      --  2014/10/03  Add By ChungYu

         insert into MMACCESSLG_BLI
           (ACSTIME,
            APNAME,
            PSNO,
            DEPID,
            TRNSID,
            TERMID,
            STYPE,
            UNO,
            YM,
            IDNO,
            PROPOSER,
            PROC,
            APNO,
            EVBRTH,
            QMK,
            ACCTYPE,
            SOURCE,
            NPIDS)
         values
           (v_mupdateCur1.CHGTIME,
            '',
            v_mupdateCur1.MODIFYMAN,
            v_mupdateCur1.DEPTID,
            v_SCRNO,
            v_mupdateCur1.TERMED,
            'BA',
            v_BAAPPBASE.LSUBNO,
            '',
            v_BAAPPBASE.EVTIDNNO,
            v_BAAPPBASE.EVTNAME,
            '',
            v_BAAPPBASE.APNO,
            v_BAAPPBASE.EVTBRDATE,
            'SNO=' || v_mupdateCur1.SNO,
            v_mupdateCur1.CHGCODE,
            '',
            v_BAAPPBASE.EVTIDS);
      Exception
         when NO_DATA_FOUND then
             dbms_output.put_line(' BAAPPBASE NO_DATA_FOUND'||substr(v_mupdateCur1.PKFIELD, 13));
         End;  
      End If;


      if mod(v_g_Count, 500) = 0 then
        commit;
      end if;
    end loop;
    commit;

    if v_g_genFlag = true then
      dbms_output.put_line('SP_BA_MMACCESSLG_LOGTRANBATCH--稽核軌跡紀錄檔(for MMACCESSLG)-[ ' ||
                           v_g_rowCount || ' ]');
    else
      dbms_output.put_line('SP_BA_MMACCESSLG_LOGTRANBATCH--稽核軌跡紀錄檔(for MMACCESSLG)-[ ' ||
                           v_g_rowCount || ' ]-NoData');
    end if;
  end;

  begin
    --寫入log
    /*SP_JOBLOG (v_job_no     => v_i_jobno,
                 v_job_id     => 'SP_BA_MMACCESSLG_LOGTRANBATCH',
                 v_in_count   => v_g_Count,
                 v_out_count  => v_g_Count,
                 v_proc_count => v_g_Count,
                 v_memo       => '批次作業成功');
    */

    v_job_no     := v_i_jobno;
    v_job_id     := 'SP_BA_MMACCESSLG_LOGTRANBATCH';
    v_job_name   := 'Ba.MMACCESSLG_BLI轉檔程式';
    v_in_count   := v_g_Count;
    v_out_count  := v_g_Count;
    v_proc_count := v_g_Count;
    v_memo       := '批次作業成功';

    select max(to_number(SEQ_NO)) + 1
      into v_seq_no
      from ba.MMJOBLOG
     where job_no = v_job_no;

    v_seq_no := NVL(v_seq_no, 0);

    insert into MMJOBLOG
      (job_no,
       seq_no,
       job_kind,
       job_id,
       job_name,
       job_step,
       table_name,
       execute_tmstmp,
       in_count,
       out_count,
       proc_count,
       err_count,
       memo)
    values
      (v_job_no,
       v_seq_no,
       v_job_kind,
       v_job_id,
       v_job_name,
       v_step,
       v_table_name,
       SYSTIMESTAMP,
       v_in_count,
       v_out_count,
       v_proc_count,
       v_err_count,
       v_memo);

    commit;
  exception
    when others then
      v_sqlcode := sqlcode;
      v_sqlerrm := sqlerrm;

      delete from MMJOBLOG
       where job_no = v_job_no
         and seq_no = -911;

      insert into MMJOBLOG
        (job_no,
         seq_no,
         job_kind,
         job_id,
         job_name,
         job_step,
         table_name,
         execute_tmstmp,
         in_count,
         out_count,
         proc_count,
         err_count,
         memo)
      values
        (v_job_no,
         -911,
         '',
         v_sqlcode,
         '',
         'RECORD_JOB_LOG Error!',
         v_table_name,
         SYSTIMESTAMP,
         0,
         0,
         0,
         0,
         v_sqlerrm);
      commit;
  end;
exception
  when others then
    rollback;
    /*SP_JOBLOG(v_job_no     => v_i_jobno,
                v_job_id     => 'SP_BA_MMACCESSLG_LOGTRANBATCH',
                v_step       => 'EXCEPTION',
                v_memo       => '錯誤:'||SQLERRM|| DBMS_UTILITY.FORMAT_ERROR_BACKTRACE );
    */
    begin
      v_job_no     := v_i_jobno;
      v_job_id     := 'SP_BA_MMACCESSLG_LOGTRANBATCH';
      v_job_name   := 'Ba.MMACCESSLG_BLI轉檔程式';
      v_in_count   := v_g_Count;
      v_out_count  := v_g_Count;
      v_proc_count := v_g_Count;
      v_memo       := '錯誤:' || sqlerrm ||
                      DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;

      select max(to_number(seq_no)) + 1
        into v_seq_no
        from ba.MMJOBLOG
       where job_no = v_job_no;

      v_seq_no := NVL(v_seq_no, 0);

      insert into MMJOBLOG
        (job_no,
         seq_no,
         job_kind,
         job_id,
         job_name,
         job_step,
         table_name,
         execute_tmstmp,
         in_count,
         out_count,
         proc_count,
         err_count,
         memo)
      values
        (v_job_no,
         v_seq_no,
         v_job_kind,
         v_job_id,
         v_job_name,
         v_step,
         v_table_name,
         SYSTIMESTAMP,
         v_in_count,
         v_out_count,
         v_proc_count,
         v_err_count,
         v_memo);
      commit;
    exception
      when others then
        v_sqlcode := sqlcode;
        v_sqlerrm := sqlerrm;

        delete from MMJOBLOG
         where job_no = v_job_no
           and seq_no = -911;

        insert into MMJOBLOG
          (job_no,
           seq_no,
           job_kind,
           job_id,
           job_name,
           job_step,
           table_name,
           execute_tmstmp,
           in_count,
           out_count,
           proc_count,
           err_count,
           memo)
        values
          (v_job_no,
           -911,
           '',
           v_sqlcode,
           '',
           'RECORD_JOB_LOG Error!',
           v_table_name,
           SYSTIMESTAMP,
           0,
           0,
           0,
           0,
           v_sqlerrm);

        commit;
    end;
    dbms_output.put_line('Err:SP_BA_MMACCESSLG_LOGTRANBATCH--------------->>' ||
                         sqlerrm);

end SP_BA_MMACCESSLG_LOGTRANBATCH;
/

