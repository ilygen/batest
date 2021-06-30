create or replace procedure ba.SP_BA_TRANBATCH(
       v_i_trandate in varChar,
       v_i_jobno      IN  MMJOBLOG.Job_No%TYPE
)authid definer is

   v_g_errMsg               varChar2(4000);

    v_g_genFlag              Number;
    v_g_rptPage              Number;
    v_g_rowCount             Number;


    Cursor c_mquerylog_1 is

            select to_Char(to_Number(substr(a.Qytime,0,3))+1911||substr(a.Qytime, 4, 10))as Qytime  --Qytime
                 ,a.Queryman
                 ,a.Deptid
                 ,a.Termed
                 ,a.Qycondition
                 ,a.Sno
                 ,lower(b.scrno)as Scrno
                 ,(SELECT extractValue(value(x), '//Value/text()')as ItemValue
                             FROM TABLE(XmlSequence(EXTRACT((select XmlType(m.qycondition)
                                                             from MMQUERYLOG m where m.sno=a.Sno
                                                             and m.tablename='BAAPPBASE'), '//Condition'))) X
                             where extractValue(value(x), '//ColumnName/text()')='EVTIDNNO')as EVTIDNNO
                  ,(SELECT extractValue(value(x), '//Value/text()')as ItemValue
                             FROM TABLE(XmlSequence(EXTRACT((select XmlType(m.qycondition)
                                                             from MMQUERYLOG m where m.sno=a.Sno
                                                             and m.tablename='BAAPPBASE'), '//Condition'))) X
                             where extractValue(value(x), '//ColumnName/text()')='EVTNAME')as EVTNAME
                   ,(SELECT extractValue(value(x), '//Value/text()')as ItemValue
                             FROM TABLE(XmlSequence(EXTRACT((select XmlType(m.qycondition)
                                                             from MMQUERYLOG m where m.sno=a.Sno
                                                             and m.tablename='BAAPPBASE'), '//Condition'))) X
                             where extractValue(value(x), '//ColumnName/text()')='APNO')as APNO
                    ,(SELECT extractValue(value(x), '//Value/text()')as ItemValue
                             FROM TABLE(XmlSequence(EXTRACT((select XmlType(m.qycondition)
                                                             from MMQUERYLOG m where m.sno=a.Sno
                                                             and m.tablename='BAAPPBASE'), '//Condition'))) X
                             where extractValue(value(x), '//ColumnName/text()')='EVTBRDATE')as EVTBRDATE

            from MMQUERYLOG a, MMACCESSLG b
            where a.tablename='BAAPPBASE'
            and substr(a.qytime, 1, 7)=v_i_trandate
            and a.Sno=b.Sno
            and (SELECT count(extractValue(value(x), '//Value/text()'))as ItemValue
                             FROM TABLE(XmlSequence(EXTRACT((select XmlType(m.qycondition)
                                                             from MMQUERYLOG m where m.tablename='BAAPPBASE' and m.sno=a.Sno), '//Condition'))) X
                                                             where extractValue(value(x), '//ColumnName/text()')='EVTBRDATE'
                                                             or    extractValue(value(x), '//ColumnName/text()')='APNO'
                                                             or    extractValue(value(x), '//ColumnName/text()')='EVTNAME'
                                                             or    extractValue(value(x), '//ColumnName/text()')='EVTIDNNO')>0;

    Cursor c_mupdatelog_2 is
          select to_Char(to_Number(substr(a.Chgtime,0,3))+1911||substr(a.Chgtime, 4, 10))as Chgtime  --Chgtime
                 ,a.Modifyman
                 ,a.Deptid
                 ,a.Termed
                 ,substr(a.Pkfield,13)as Pkfield
                 ,a.Chgcode
                 ,a.Sno
                 ,lower(b.scrno)as Scrno
                 ,c.lsubno as Lsubno
                 ,c.evtidnno as EvtIdnNo
                 ,c.evtname as EvtName
                 ,c.apno as Apno
                 ,c.evtbrdate as EvtBrDate
                 ,c.evtids as EvtIds
            from MMAPLOG a, MMACCESSLG b, Baappbase c
            where tablename='BAAPPBASE'
            And a.chgcode in ('U','D')
            And a.pgmcode not like 'BAAA%'
            and substr(a.Chgtime, 1, 7)=v_i_trandate
            and a.Sno=b.Sno
            and c.baappbaseid = substr(a.Pkfield,13)
            and (c.lsubno is not null
            or  c.evtidnno is not null
            or  c.evtname is not null
            or  c.apno is not null
            or  c.evtbrdate is not null
            or  c.evtids is not null);


  begin
          begin
            v_g_rowCount := 0;


            for v_mqueryCur1 in c_mquerylog_1 Loop
                v_g_genFlag := 1;
                v_g_rowCount := v_g_rowCount + 1;

                if (v_g_rowCount MOD 20)= 0 then
                    v_g_rptPage := v_g_rptPage +1;
                end if;

                insert into MMACCESSLG_BLI(
                            ACSTIME
                           ,APNAME
                           ,PSNO
                           ,DEPID
                           ,TRNSID
                           ,TERMID
                           ,STYPE
                           ,UNO
                           ,YM
                           ,IDNO
                           ,PROPOSER
                           ,PROC
                           ,APNO
                           ,EVBRTH
                           ,QMK
                           ,ACCTYPE
                           ,SOURCE
                           ,NPIDS )
                        values(
                            v_mqueryCur1.Qytime
                           ,''
                           ,v_mqueryCur1.Queryman
                           ,v_mqueryCur1.Deptid
                           ,v_mqueryCur1.Scrno
                           ,v_mqueryCur1.Termed
                           ,'BA'
                           ,''
                           ,''
                           ,v_mqueryCur1.EVTIDNNO
                           ,v_mqueryCur1.EVTNAME
                           ,''
                           ,v_mqueryCur1.APNO
                           ,v_mqueryCur1.EVTBRDATE
                            ,''
                            ,'Q'
                            ,''
                            ,''
                        );

             end loop;
             COMMIT;
             if v_g_genFlag>0 then
                dbms_output.put_line('SP_BA_TRANBATCH--------------->>稽核軌跡紀錄檔-[ '||v_g_rowCount||' ]');
             else
                dbms_output.put_line('SP_BA_TRANBATCH--------------->>稽核軌跡紀錄檔-[ '||v_g_rowCount||' ]-NoData');
             end if;
        end;

        begin
              v_g_rowCount := 0;


            for v_mupdateCur1 in c_mupdatelog_2 Loop
                v_g_genFlag := 1;
                v_g_rowCount := v_g_rowCount + 1;

                if (v_g_rowCount MOD 20)= 0 then
                    v_g_rptPage := v_g_rptPage +1;
                end if;

                insert into MMACCESSLG_BLI(
                            ACSTIME
                           ,APNAME
                           ,PSNO
                           ,DEPID
                           ,TRNSID
                           ,TERMID
                           ,STYPE
                           ,UNO
                           ,YM
                           ,IDNO
                           ,PROPOSER
                           ,PROC
                           ,APNO
                           ,EVBRTH
                           ,QMK
                           ,ACCTYPE
                           ,SOURCE
                           ,NPIDS )
                        values(
                            v_mupdateCur1.Chgtime
                           ,''
                           ,v_mupdateCur1.Modifyman
                           ,v_mupdateCur1.Deptid
                           ,v_mupdateCur1.Scrno
                           ,v_mupdateCur1.Termed
                           ,'BA'
                           ,v_mupdateCur1.Lsubno
                           ,''
                           ,v_mupdateCur1.EvtIdnNo
                           ,v_mupdateCur1.EvtName
                           ,''
                           ,v_mupdateCur1.Apno
                           ,v_mupdateCur1.EvtBrDate
                           ,''
                           ,v_mupdateCur1.Chgcode
                           ,''
                           ,v_mupdateCur1.EvtIds
                        );
             end loop;
             COMMIT;
             if v_g_genFlag>0 then
                dbms_output.put_line('SP_BA_TRANBATCH--------------->>稽核軌跡紀錄檔-[ '||v_g_rowCount||' ]');
             else
                dbms_output.put_line('SP_BA_TRANBATCH--------------->>稽核軌跡紀錄檔-[ '||v_g_rowCount||' ]-NoData');
             end if;

        end;

        --寫入log
        SP_JOBLOG (v_job_no  => v_i_jobno,
                   v_job_id  => 'SP_BA_TRANBATCH',
                   v_step    => 'END',
                   v_memo    => '批次作業成功'||TO_CHAR(SYSDATE, 'YYYY/MM/DD HH24:MI:SS'));

  exception
      when others
          then
              rollback;
              SP_JOBLOG( v_job_no     => v_i_jobno,
                         v_job_id     => 'SP_BA_TRANBATCH',
                         v_step       => 'EXCEPTION',
                         v_memo       => '錯誤:'||SQLERRM|| DBMS_UTILITY.FORMAT_ERROR_BACKTRACE );

              v_g_errMsg := SQLErrm;
              begin
                  dbms_session.close_database_link('BLIDB');
                  dbms_output.put_line('SP_BA_TRANBATCH--------------->>Close Database Link');
              exception
                  when others
                      then
                          dbms_output.put_line('SP_BA_TRANBATCH--------------->>Close Database Link');
              end;
              dbms_output.put_line('Err:SP_BA_TRANBATCH--------------->>'||v_g_errMsg);


end SP_BA_TRANBATCH;
/

