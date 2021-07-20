CREATE OR REPLACE PACKAGE BA.PKG_BA_SYNCPBBMSA
authid definer IS
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            SP_BA_SYNCPBBMSA
    PURPOSE:         將大同資料庫PBBMSA當日異動回寫至NEC資料庫PBBMSA

    PARAMETER(IN):
    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2011/03/08  A70348       Created this procedure.

    NOTES:
    1.
********************************************************************************/


   /*PROCEDURE ba_synctable_log (startTime   IN Date,
                                endTime     IN Date,
                                deleteCount IN Number,
                                insertCount IN Number
                                tableName   IN Varchar2);*/


   PROCEDURE ba_sync_main;

END ;
/

CREATE OR REPLACE PACKAGE BODY BA.PKG_BA_SYNCPBBMSA is
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_SYNCPBBMSA
    PURPOSE:         將大同資料庫PBBMSA當日異動回寫至NEC資料庫PBBMSA

    PARAMETER(IN):
    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2011/03/08  A70348       Created this procedure.

    NOTES:
    1.20110714 刪除NEC資料庫pbbmsa時修改不加updte的條件以避免資料未刪掉的問題
********************************************************************************/

    /*寫入LOG Table*/
    procedure ba_synctable_log(
          v_i_startTime    in Date,
          v_i_endTime      in Date,
          v_i_deleteCount  in Number,
          v_i_insertCount  in Number,
          v_i_tableName    in Varchar2)
        is
          v_sql         VarChar2(200);
          v_rowCount    Number :=0;
          v_i_errorMessage VarChar2(1020) default sqlerrm;
          pragma autonomous_transaction;
        begin
          v_sql := 'select count(*) from ba.basynctablelog where starttime = :y and tablename = :z';
          execute immediate v_sql into v_rowCount using v_i_startTime, v_i_tableName ;
          /*select count(*) into v_rowCount from ba.basynctablelog where starttime = v_i_startTime and tablename = v_i_tableName;*/
          if (v_rowCount = 0) then
             v_sql := 'insert into ba.basynctablelog values (:u, :v, :w, :x, :y, :z)';
             execute immediate v_sql using  v_i_deleteCount, v_i_insertCount, v_i_startTime, v_i_endTime, v_i_tableName, v_i_errorMessage;
            /*insert into ba.basynctablelog values
                   (v_i_deleteCount, v_i_insertCount,v_i_startTime, v_i_endTime, v_i_tableName, v_i_errorMessage);*/
          else
             v_sql := 'update ba.basynctablelog set deletecount = :u, insertcount = :v, endtime = :w, errormessage = :x where starttime = :y and tablename = :z';
             execute immediate v_sql using  v_i_deleteCount, v_i_insertCount, v_i_endTime, v_i_errorMessage, v_i_startTime, v_i_tableName;
             /*update ba.basynctablelog set starttime = v_i_startTime,
                   endtime = v_i_endTime, deletecount = v_i_deleteCount,
                   insertcount = v_i_insertCount , errormessage = v_i_errorMessage
                   where starttime = v_i_startTime and tablename = v_i_tableName;*/
          end if;
          commit;
    end;
    --procedure ba_synctable_log End

    /*取得NEC資料庫PBBMSA最大異動日*/
    function ba_pbbmsa_maxUpdate return varChar2 is
          v_sql         Varchar(200);
          v_maxUpdte    Varchar(8);
        begin
          v_maxUpdte := ' ';
          v_sql := 'select max(updte) from pbbmsa';
          execute immediate v_sql into v_maxUpdte;
          dbms_output.put_line('SP_BA_SyncPbbmsa-sql=[ '||v_sql||
                                ' ], updte=['|| v_maxUpdte||']');
        return v_maxUpdte;
    end;
    --function ba_pbbmsa_maxUpdate End


    procedure ba_sync_pbbmsa (
          v_i_maxUpdte  in Varchar2)
          is
          v_errMsg      Varchar2(2000);
          v_startTime   Date;
          v_endTime     Date;
          v_deleteCount Number;
          v_insertCount Number;
          v_rowCount    Number;
          v_sql         Varchar(200);
          v_maxUpdte    Varchar(8);
        begin
          v_errMsg := ' ';
          v_startTime := sysdate;
          v_deleteCount := 0;
          v_insertCount := 0;
          v_maxUpdte := v_i_maxUpdte;

          --1.開始執行轉檔,記錄起始時間,寫入LOG TABLE
          ba_synctable_log(v_startTime,v_endTime,v_deleteCount,v_insertCount,'pbbmsa');
          --2.超過三十萬筆資料就不要執行,改請DBA全量轉檔
          v_sql := 'select count(*) from bliadm.pbbmsa@blidb ' ||
                 'where updte >= :x';
          dbms_output.put_line('SP_BA_SyncPbbmsa-sql=[ '||v_sql||' ]');
          execute immediate v_sql into v_rowCount using v_maxUpdte;
          --execute immediate v_sql into v_rowCount using '20110506';
          if v_rowCount <= 300000 then
            --3.刪除NEC資料庫PBBMSA與大同資料庫PBBMSA重複的資料
            v_sql := 'delete from pbbmsa ' ||
                  'where bmapno in ( ' ||
                  'select bmapno from bliadm.pbbmsa@blidb ' ||
                  'where updte >= :x )';
                  --'where bmapno like ''100041000%'') ';
            execute immediate v_sql using v_maxUpdte;
            v_deleteCount := SQL%RowCount;
            dbms_output.put_line('SP_BA_SyncPbbmsa-(刪除筆數:'||v_deleteCount||')筆 sql=[ '||v_sql||' ]');
            --4.記錄刪除筆數,寫入LOG TABLE
            ba_synctable_log(v_startTime,v_endTime,v_deleteCount,v_insertCount,'pbbmsa');
            --5.新增至NEC資料庫PBBMSA的資料
            v_sql := 'insert into pbbmsa ' ||
                  '(select * from bliadm.pbbmsa@blidb ' ||
                  'where updte >= :x )';
                  --'where bmapno like ''100041000%'') ';
            execute immediate v_sql using v_maxUpdte;
            v_insertCount := SQL%RowCount;
            dbms_output.put_line('SP_BA_SyncPbbmsa-(新增筆數:'||v_insertCount||')筆 sql=[ '||v_sql||' ]');
            --6.執行完成,將新增筆數與完成時間寫入LOG TABLE
            ba_synctable_log(v_startTime,sysdate,v_deleteCount,v_insertCount,'pbbmsa');
            commit;
            dbms_output.put_line('SP_BA_SyncPbbmsa-FINAL');

          else
               --7.超過筆數不執行,寫入LOG TABLE
               v_errMsg := '超過三十萬筆,請執行全量轉檔';
               ba_synctable_log(v_startTime,sysdate,v_deleteCount,v_insertCount,'pbbmsa');
               dbms_output.put_line('SP_BA_SyncPbbmsa-超過三十萬筆,請執行全量轉檔');
          end if;

          exception
            when others
            then
                v_errMsg := SQLErrm;
                dbms_output.put_line('**Err:SP_BA_SyncPbbmsa-----'||v_errMsg);
                ba_synctable_log(v_startTime,sysdate,v_deleteCount,v_insertCount,'pbbmsa');
                rollback;
    end;
    --procedure ba_sync_pbbmsa End

    procedure ba_sync_pbbmsadelmk(
          v_i_maxUpdte  in Varchar2)
          is
          v_errMsg      Varchar2(2000);
          v_startTime   Date;
          v_endTime     Date;
          v_deleteCount Number;
          v_insertCount Number;
          v_sql         Varchar(300);
          pragma autonomous_transaction;
        begin
          v_errMsg := ' ';
          v_startTime := sysdate;
          v_deleteCount := 0;
          v_insertCount := 0;

          --1.開始執行轉檔,記錄起始時間,寫入LOG TABLE
          ba_synctable_log(v_startTime,v_endTime,v_deleteCount,v_insertCount,'pbbmsadelmk');

          --2.將大同資料庫現金給付主檔(BCPMA)本案邏輯註銷之案件(DELMK='Y')
          --  從NEC資料庫中的PBBMSA搬移至PBBMSADELMK
          v_sql := 'insert into pbbmsadelmk ' ||
                '(select a.*, to_char(sysdate,''YYYYMMDDHH24MISS'') ' ||
                'from bliadm.pbbmsa a,   ' ||
                '(select apno from bliadm.bcpma@blidb ' ||
                'where length(apno) = 12 and delmk = ''Y'' and uptime >= :x ) b ' ||
                'where a.bmapno = b.apno )';
                --'where length(apno) = 12 and delmk = ''Y'' ) b ' ||
                --'where a.bmapno = b.apno )';
          dbms_output.put_line('SP_BA_SyncPbbmsadelmk-sql=[ '||v_sql||' ]');
          execute immediate v_sql using v_i_maxUpdte;
          --execute immediate v_sql;
          v_insertCount := SQL%RowCount;
          dbms_output.put_line('SP_BA_SyncPbbmsadelmk-(新增筆數:'||v_insertCount||')筆 ');
          --3.記錄新增筆數,寫入LOG TABLE
          ba_synctable_log(v_startTime,v_endTime,v_deleteCount,v_insertCount,'pbbmsadelmk');

          --4.將NEC資料庫中已搬移至PBBMSADELMK之案件從PBBMSA刪除
          v_sql := 'delete from pbbmsa ' ||
                'where bmapno in ( ' ||
                'select bmapno from pbbmsadelmk )' ;
                --'select bmapno from pbbmsadelmk where updte >= :x )';    --20110714
          dbms_output.put_line('SP_BA_SyncPbbmsadelmk-sql=[ '||v_sql||' ]');
          execute immediate v_sql;
          --execute immediate v_sql using v_i_maxUpdte;    --20110714
          v_deleteCount := SQL%RowCount;
          dbms_output.put_line('SP_BA_SyncPbbmsadelmk-(刪除筆數:'||v_deleteCount||')筆 ');
          --5.執行完成,將新增筆數與完成時間寫入LOG TABLE
          ba_synctable_log(v_startTime,sysdate,v_deleteCount,v_insertCount,'pbbmsadelmk');

          commit;
          dbms_output.put_line('SP_BA_SyncPbbmsadelmk-FINAL');

          exception
            when others
            then
                v_errMsg := SQLErrm;
                dbms_output.put_line('**Err:SP_BA_SyncPbbmsadelmk-----'||v_errMsg);
                ba_synctable_log(v_startTime,sysdate,v_deleteCount,v_insertCount,'pbbmsadelmk');
                rollback;
    end;
    --procedure ba_sync_pbbmsadelmk End

     procedure ba_sync_main is
     v_maxUpdte    Varchar(8);
        begin
          --1.執行ba_pbbmsa_maxUpdate
          v_maxUpdte := ba_pbbmsa_maxUpdate;

          --2.執行ba_sync_pbbmsa
          ba_sync_pbbmsa(v_maxUpdte);

          --3.執行ba_sync_pbbmsadelmk
          ba_sync_pbbmsadelmk(v_maxUpdte);
    end;
    --procedure ba_sync_main End
end;
/

