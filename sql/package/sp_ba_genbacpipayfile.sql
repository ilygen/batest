create or replace procedure ba.SP_BA_genBACPIPayFile (
    v_i_issuym         in      varChar2,
    v_i_paycode        in      varChar2,
    v_i_paydate        in      varChar2
) authid definer is
    v_filepath         varChar2(50) := 'EXCHANGE';         --轉出給付媒體檔的存放目錄
    v_mfilename        varChar2(100) := '';
    v_PayNum           Number(8) := 0;
    v_PayAMT           Number(12) := 0;
    v_fileid           UTL_FILE.FILE_TYPE;

    --查詢及計算各媒體檔的首錄及尾錄金額
    Cursor c_dataCur is
        select t1.TATYP                                    --出帳類別
              ,Count(*) as DATACOUNT                       --轉帳總件數
              ,Sum(nvl(t2.CPIADJAMT,0)) as AMT             --轉帳總金額
          from (select (deCode(t11.PAYTYP,'1','BL1'
                                         ,'2','BL1'
                                         ,'3',(deCode(t11.BENEVTREL,'Z',' ','LAB'))
                                         ,'A','BLA',' ')) TATYP
                       ,t11.APNO
                       ,t11.SEQNO
                       ,t11.PAYKIND
                       ,t11.ISSUYM
                       ,t11.PAYYM
                       ,t11.PAYTYP
                       ,t11.BENEVTREL
                  from BACPIAMT t11
                 where t11.APNO like (v_i_paycode||'%')
                   and ((t11.APNO not like 'S%' and t11.SEQNO = '0000')
                     or (t11.APNO like 'S%' and t11.SEQNO <> '0000'))
                   and t11.ISSUYM = v_i_issuym
                   and t11.PAYTYP in ('1','2','A')) t1
               ,(select t21.APNO
                       ,t22.ACCSEQNO
                       ,t21.PAYKIND
                       ,t21.ISSUYM
                       ,t21.PAYYM
                       ,t21.PAYTYP
                       ,Sum(nvl(t21.CPIADJAMT,0)) as CPIADJAMT
                   from BACPIAMT t21,BAAPPBASE t22
                  where t21.APNO = t22.APNO
                    and ((t21.APNO not like 'S%' and t21.SEQNO = '0000')
                      or (t21.APNO like 'S%' and t21.SEQNO <> '0000'))
                    and t21.SEQNO = t22.SEQNO
                    and t21.ISSUYM = v_i_issuym
                    and t21.PAYTYP in ('1','2','A')
                  group by t21.APNO,t22.ACCSEQNO,t21.PAYKIND,t21.ISSUYM,t21.PAYYM,t21.PAYTYP) t2
         where t1.APNO = t2.APNO
           and t1.APNO like (v_i_paycode||'%')
           and ((t1.APNO not like 'S%' and t1.SEQNO = '0000')
             or (t1.APNO like 'S%' and t1.SEQNO <> '0000'))
           and (t1.APNO||t1.SEQNO||t1.PAYTYP) = (t2.APNO||t2.ACCSEQNO||t1.PAYTYP)
           and t1.ISSUYM = t2.ISSUYM
           and t1.PAYYM = t2.PAYYM
           and t1.PAYKIND = t2.PAYKIND
           and t1.PAYTYP = t2.PAYTYP
           and t2.CPIADJAMT > 0
         group by t1.TATYP;

    --依傳入的出帳類別查詢該媒體檔待發的核付資料
    Cursor c_PayDataCur(v_tatyp varChar2) is
        select t1.APNO                                    --受理編號
              ,t1.SEQNO                                   --受款人序
              ,t1.PAYKIND                                 --給付種類
              ,t1.ISSUYM                                  --核定年月
              ,t1.PAYYM                                   --給付年月
              ,t1.BENIDNNO                                --身分證號
              ,t1.BENNAME                                 --受款人姓名
              ,t1.PAYTYP                                  --給付方式
              ,t1.PAYBANKID                               --總行代碼
              ,t1.BRANCHID                                --分行代號
              ,t1.ACCNO                                   --入帳帳號
              ,t2.AMT                                     --交易金額
          from (select t11.APNO
                      ,t11.SEQNO
                      ,t11.PAYKIND
                      ,t11.ISSUYM
                      ,t11.PAYYM
                      ,t11.BENIDNNO
                      ,t11.BENNAME
                      ,t11.PAYTYP
                      ,t11.PAYBANKID
                      ,deCode(v_tatyp,'BLA',(RPAD(nvl(trim(t11.BRANCHID),' '),5,' '))
                                           ,(RPAD(nvl(trim(t11.BRANCHID),' '),5,' '))
                             ) as BRANCHID
                      ,deCode(t11.PAYTYP,'A',(LPAD(nvl(trim(t11.PAYEEACC),' '),14,' '))
                                          ,(LPAD(nvl(trim(t11.PAYEEACC),'0'),14,'0'))
                             ) as ACCNO
                  from BACPIAMT t11
                 where t11.APNO like (v_i_paycode||'%')
                   and ((t11.APNO not like 'S%' and t11.SEQNO = '0000')
                     or (t11.APNO like 'S%' and t11.SEQNO <> '0000'))
                   and t11.ISSUYM = v_i_issuym
                   and t11.PAYTYP in ('1','2','A')
                   and (deCode(t11.PAYTYP,'1','BL1'
                                         ,'2','BL1'
                                         ,'3',(deCode(t11.BENEVTREL,'Z',' ','LAB'))
                                         ,'A','BLA',' ')) = v_tatyp) t1
               ,(select t21.APNO
                       ,t22.ACCSEQNO
                       ,t21.PAYKIND
                       ,t21.ISSUYM
                       ,t21.PAYYM
                       ,t21.PAYTYP
                       ,Sum(nvl(t21.CPIADJAMT,0)) as AMT
                   from BACPIAMT t21,BAAPPBASE t22
                  where t21.APNO = t22.APNO
                    and ((t21.APNO not like 'S%' and t21.SEQNO = '0000')
                      or (t21.APNO like 'S%' and t21.SEQNO <> '0000'))
                    and t21.SEQNO = t22.SEQNO
                    and (deCode(t21.PAYTYP,'1','BL1'
                                          ,'2','BL1'
                                          ,'3',(deCode(t21.BENEVTREL,'Z',' ','LAB'))
                                          ,'A','BLA',' ')) = v_tatyp
                  group by t21.APNO,t22.ACCSEQNO,t21.PAYKIND,t21.ISSUYM,t21.PAYYM,t21.PAYTYP) t2
         where t1.APNO = t2.APNO
           and t1.APNO like (v_i_paycode||'%')
           and ((t1.APNO not like 'S%' and t1.SEQNO = '0000')
             or (t1.APNO like 'S%' and t1.SEQNO <> '0000'))
           and (t1.APNO||t1.SEQNO||t1.PAYTYP) = (t2.APNO||t2.ACCSEQNO||t1.PAYTYP)
           and t1.ISSUYM = t2.ISSUYM
           and t1.PAYYM = t2.PAYYM
           and t1.PAYKIND = t2.PAYKIND
           and t1.PAYTYP = t2.PAYTYP
           and t2.AMT > 0
         order by t1.APNO,to_number(t1.SEQNO),t1.ISSUYM,t1.PAYYM;

begin
    for v_dataCur in c_dataCur Loop
        v_PayNum := 0;
        v_PayAMT := 0;

        v_mfilename := (v_dataCur.TATYP||'005'||fn_BA_transDateValue(v_i_paydate,'1')||substr(to_Char(Sysdate,'YYYYMMDDHH24MISS'),1,12));

        if v_i_paycode = 'L' then
            v_mfilename := v_mfilename||'1';
        elsif v_i_paycode = 'K' then
            v_mfilename := v_mfilename||'2';
        elsif v_i_paycode = 'S' then
            v_mfilename := v_mfilename||'3';
        else
            v_mfilename := v_mfilename||'0';
        end if;

        v_mfilename := v_mfilename||'.txt';

        v_fileid := utl_file.fopen(v_filepath,v_mfilename,'w');

        --組合及產生首錄資料錄
        utl_file.put_line(v_fileid
                         ,'1'
                        ||RPAD('BLI',8,' ')
                        ||RPAD('005',8,' ')
                        ||v_dataCur.TATYP
                        ||fn_BA_transDateValue(v_i_paydate,'1')
                        ||'1'
                        ||RPAD('139862',17,' ')
                        ||LPAD(v_dataCur.AMT,14,'0')
                        ||LPAD(v_dataCur.DATACOUNT,10,'0')
                        ||'1'
                        ||' '
                        ||fn_BA_transDateValue(v_i_paydate,'1')
                        ||RPAD(' ',62,' '));

        for v_PayDataCur in c_PayDataCur(v_dataCur.TATYP) Loop
            v_PayNum := v_PayNum + 1;
            v_PayAMT := v_PayAMT + to_Number(v_PayDataCur.AMT);

            --組合及產生明細錄資料錄
            utl_file.put_line(v_fileid
                             ,'2'
                            ||RPAD('BLI',8,' ')
                            ||RPAD(nvl(trim(v_PayDataCur.PAYBANKID),' '),3,' ')
                            ||v_PayDataCur.BRANCHID
                            ||v_dataCur.TATYP
                            ||fn_BA_transDateValue(v_i_paydate,'1')
                            ||v_PayDataCur.ACCNO
                            ||LPAD(nvl(trim(v_PayDataCur.AMT),'0'),14,'0')
                            ||LPAD(' ',2,' ')
                            ||v_PayDataCur.APNO
                            ||v_PayDataCur.SEQNO
                            ||v_PayDataCur.PAYKIND
                            ||LPAD(' ',2,' ')
                            ||fn_BA_transDateValue(v_PayDataCur.PAYYM,'2')
                            ||RPAD(nvl(trim(v_PayDataCur.BENIDNNO),' '),10,' ')
                            ||convert(substr(RPAD(fn_BA_transCharValue(replace(v_PayDataCur.BENNAME,' ','　'),'1'),30,'　'),1,15)||'1', 'ZHT16BIG5', 'UTF8')
                            ||' '
                            ||LPAD(' ',7,' ')
                            ||fn_BA_transDateValue(v_PayDataCur.ISSUYM,'2')
                            ||LPAD(' ',4,' '));
        end Loop;

        --組合及產生尾錄資料錄
        utl_file.put_line(v_fileid
                         ,'3'
                        ||RPAD('BLI',8,' ')
                        ||RPAD('005',8,' ')
                        ||v_dataCur.TATYP
                        ||fn_BA_transDateValue(v_i_paydate,'1')
                        ||'1'
                        ||RPAD('139862',17,' ')
                        ||fn_BA_transDateValue(v_i_paydate,'1')
                        ||LPAD(v_dataCur.AMT,14,'0')
                        ||LPAD(v_dataCur.DATACOUNT,10,'0')
                        ||LPAD('0',14,'0')
                        ||LPAD('0',10,'0')
                        ||' '
                        ||fn_BA_transDateValue(v_i_paydate,'1')
                        ||LPAD(' ',32,' '));

        utl_file.fclose(v_fileid);

        dbms_output.put_line(RPAD('SP_BA_genBACPIPayFile',40,'-')||'>>'||RPAD(v_mfilename,32,' ')||'    -PayNum:'||RPAD(v_PayNum,8,' ')||'    -PayAMT:'||v_PayAMT);
    end Loop;
exception
    when others
        then
            dbms_output.put_line(RPAD('**Err:SP_BA_genBACPIPayFile',40,'-')||'>>'||SQLErrm);
end SP_BA_genBACPIPayFile;
/

