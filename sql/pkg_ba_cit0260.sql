create or replace package ba.PKG_BA_CIT0260 authid definer is

-- ============================================================================
-- 程式目的：依據CIT0260資料重算勞保年金年資金額資料
-- 撰寫人員：Justin Yu
-- 執行方式：
-- 傳入參數：分流數目      NUMBER
--           批次處理編號  VARCHAR2(30)
-- 傳出參數：
-- 相關TABLE：
--     R/W      表格代號   表格中文名稱
-- ----------- ----------  ----------------------------------------------------
--
-- 修改紀錄：
-- 維護人員        日期    說明
-- -----------   --------  ----------------------------------------------------
-- Justin Yu     20160505  v1.0 傳入參數加入p_instno指定DB SERVER
-- ============================================================================


  TYPE ANNUITY_TABLE_TYPE IS RECORD(
        SENIORITY   CIT0260R.SENIORITY%TYPE,
        OLDRATE     CIT0260R.OLDRATE%TYPE,
        OLDAB       CIT0260R.OLDAB%TYPE,
        OLDAAMT     CIT0260R.OLDAAMT%TYPE,
        OLDBAMT     CIT0260R.OLDBAMT%TYPE,
        ISSUEAMT    CIT0260R.ISSUEAMT%TYPE,
        PAYAMT      CIT0260R.PAYAMT%TYPE,
        PAYMONTH    CIT0260R.PAYMONTH%TYPE
  );
  TYPE ANNUITY_COLUMNS IS TABLE OF ANNUITY_TABLE_TYPE INDEX BY BINARY_INTEGER;

  PROCEDURE SP_BA_CIT0260_BATCH;

  PROCEDURE SP_BA_CIT0260R(P_RANGE IN VARCHAR2);
  PROCEDURE SP_BA_CIT0260RUPDATE;

  PROCEDURE SP_BA_CALCULATE (P_JOBNO  IN VARCHAR2,
                           P_APPLYDATE  IN VARCHAR2,
                           P_CVEPT      IN VARCHAR2,
                           P_BRDTE      IN VARCHAR2,
                           P_ITRMD15    IN VARCHAR2,
                           P_PAYRATE    IN VARCHAR2,
                           P_AVGWG      IN VARCHAR2,
                           P_HAVGWG     IN VARCHAR2,
                           P_OLDTY      IN VARCHAR2,
                           P_OLDTD      IN VARCHAR2,
                           P_NITRMY     IN VARCHAR2,
                           P_NITRMM     IN VARCHAR2,
                           P_NOLDTY     IN VARCHAR2,
                           P_NOLDTM     IN VARCHAR2,
                           P_OUT     OUT ANNUITY_TABLE_TYPE );

  PROCEDURE SP_BA_CALCULATEANNUITY(p_havgwg   IN VARCHAR2,
                                  p_seniority IN VARCHAR2,
                                  p_rate      IN NUMBER,
                                  p_payrate   IN NUMBER,
                                  p_out       OUT ANNUITY_TABLE_TYPE);

  FUNCTION FN_BA_CALCULATERATE(p_fitmonth    IN VARCHAR2,
                               p_applydate IN VARCHAR2) RETURN NUMBER;

  FUNCTION FN_BA_FITAGE(p_brdte IN VARCHAR2) RETURN NUMBER ;

  FUNCTION FN_BA_FITMONTH(p_brdte   IN VARCHAR2,
                         p_itrmd15 IN VARCHAR2) RETURN VARCHAR2;

  FUNCTION FN_BA_CALCULATESENIORITY(p_nitrmy IN NUMBER,
                                    p_nitrmm IN NUMBER) RETURN NUMBER ;

  FUNCTION FN_BA_CALCULATEPAYMONTH(p_oldty  IN VARCHAR2, p_oldtd  IN VARCHAR2,
                                   p_noldty IN VARCHAR2, p_noldtm IN VARCHAR2,
                                   p_brdte  IN VARCHAR2, p_cvept  IN VARCHAR2) RETURN NUMBER ;

  FUNCTION FN_BA_PAYMONTHS(p_noldty  IN NUMBER,   p_noldtm  IN NUMBER,
                           p_brdte   IN VARCHAR2, p_cvept   IN VARCHAR2) RETURN NUMBER ;

END PKG_BA_CIT0260;
/

create or replace package body ba.PKG_BA_CIT0260 AS

--annuity_out  ANNUITY_TABLE_TYPE;
v_performdate  varchar2(08) := '20090101';  --勞保年金修法實行日期

PROCEDURE SP_BA_CIT0260G(pDate    in VARCHAR2,
                   p_range1 in VARCHAR2,
                   p_range2 in VARCHAR2) IS
BEGIN
-- ============================================================================
-- 程式目的：原余科長CIT0260G程式產出CIT0260 table
-- 撰寫人員：余科長
-- 執行方式：
-- 傳入參數：
-- 傳出參數：
-- 相關TABLE：讀取CIPB CIUPB BCCMF16, 產出CIT0260
--     R/W      表格代號   表格中文名稱
-- ----------- ----------  ----------------------------------------------------
--
-- 修改紀錄：
-- 維護人員        日期    說明
-- -----------   --------  ----------------------------------------------------
-- Justin Yu     20160506
-- ============================================================================
--                          <<<<<<<<< 變數區 >>>>>>>>>>
-------------------------------------------------------------------------------
DECLARE
  TYPE TMP_RUNO_TABLE IS TABLE OF VARCHAR2(10240) INDEX BY BINARY_INTEGER;
  tmp_runo TMP_RUNO_TABLE;
  TYPE t_acctime_type IS VARRAY(30) OF NUMBER(15,6);
  t_acctime t_acctime_type := t_acctime_type(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
  ttime     TIMESTAMP;
  v_ctime   NUMBER(15,6) := 0;
  v_message varchar2(1024);
  v_rng12   varchar2(10);
--  轉檔人員  系統名稱  時間
  sys_time       ci.cipb.upd_datetime       %type := to_char(systimestamp,'YYYYMMDDHH24MISS');
  sys_prodte     ci.cipt.efdte              %type := substr(sys_time,1,8);
  v_job_no       mmjoblog.job_no         %TYPE;
  v_date         ci.cipt.efdte              %type := to_char(to_date(sys_prodte,'yyyymmdd')-1,'yyyymmdd');
  v_proym        varchar2(6) := to_char(add_months(to_date(sys_prodte,'yyyymmdd'),-1),'yyyymm');
  v_pb_max       pls_integer := 200000; --cursor處理上限
  sw_exit        boolean := true;
  p_ciid         ci.cipb.ciid%type := ' ';
--  過程計數器  暫存變數
  sys_counter    pls_integer;
  sys_i          pls_integer;
  v_in_cnt       pls_integer := 0; --cipb讀取筆數
  v_skip_cnt     pls_integer := 0; --年齡不滿50歲年資不滿25年不處理
  v_out_cnt      pls_integer := 0; --輸出cit0260筆數
  v_err_cnt      pls_integer := 0; --處理異常筆數
  v_cvldtl_cnt   pls_integer := 0; --檢查戶政檔次數
  v_cvldtl_err   pls_integer := 0; --讀不到戶政檔筆數
  v_cvept        ci.cipt.efdte   %type;
  v_ftyp         ci.cipb.ftyp    %type := 'L';
  v_ciid         ci.cipb.ciid    %type;
  v_idn          ci.cipb.idn     %type;
  v_brdte        ci.cipb.brdte   %type;
  v_avgm         ci.pl0126m0out.avgm%type := 36;
  v_oldty        ci.pl0126m0out.itrmy%type;
  v_sex          cit0260.sex%type;
  v_age          number(3,0) := 0;
--  年資副程式回傳PKG_CI_T01.T03
  v_relationid   bliadm.bccmf16.relationid%type;
  v_runo         varchar2(10240);
  v_p126out      ci.pl0126m0out%rowtype;
  v_out2         a70348.pkg_ci_t02.tmp_out2_type;
  t_p126out      ci.pl0126m0out%rowtype;
  v_cit0260      cit0260%rowtype;
  v_cvldtl       bliadm.cvldtl%rowtype;

--游標
  cursor cur_cipb is
    select
      ftyp,
      range,
      ciid,
      idn,
      idnseq,
      brdte,
      name,
      sex,
      qmk,
      '' as bscd,
      0 as age,
      0 as wage,
      0 as noldty,
      0 as noldtm,
      0 as itrmy,
      0 as itrmd,
      '' as marka,
      '' as markb,
      '' as markc,
      '' as markd,
      '' as proym,
      '' as upd_datetime,
      0 as ccnt,
      '' as speccode,
      '' as runo,
      '' as uno,
      '' as tp
    from ci.cipb a
    where ftyp=v_ftyp and brdte<=v_brdte
      and trim(sbmk1) is null and trim(sbmk2) is null
      and trim(sbmk3) is null and trim(sbmk4) is null
      and idn>=p_range1 and idn<p_range2
      and a.ciid>p_ciid
    order by a.ciid;

  cursor cur_bccmf16 is
    select b.uno, c.relationid
      from ci.ciupb b, bliadm.bccmf16 c
     where b.ftyp=v_ftyp and b.ciid=v_ciid and c.ubno(+)=b.uno
     order by c.relationid, b.uno;

-- ============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
  PROCEDURE get_jobno
  IS
  BEGIN
     SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_job_no FROM DUAL;
  END get_jobno;

  PROCEDURE calc_time(idx pls_integer)
  IS
    ctime     TIMESTAMP;
    t_ctime   NUMBER(15,6) := 0;
  BEGIN
    ctime := systimestamp;
    select extract(HOUR FROM ctime)*3600+extract(MINUTE FROM ctime)*60+
           extract(SECOND FROM ctime) into t_ctime from dual;
    IF (t_ctime >= v_ctime) THEN
      t_acctime(idx) := t_acctime(idx) + t_ctime - v_ctime;
    ELSE --跨日
      t_acctime(idx) := t_acctime(idx) + t_ctime + 86400 - v_ctime;
    END IF;
    v_ctime := t_ctime;
  END;

  PROCEDURE writeLog(p_step in varchar2, p_memo in varchar2, p_msg in varchar2)
  IS
    v_memo varchar2(1024) := p_memo;
  BEGIN
    IF (p_msg = 'Y') THEN
      v_message := to_char(t_acctime(1),'000000.000')||'/'||to_char(t_acctime(2),'000000.000')||'/'
                || to_char(t_acctime(3),'000000.000')||'/'||to_char(t_acctime(4),'000000.000')||'/'
                || to_char(t_acctime(5),'000000.000')||'/'||to_char(t_acctime(6),'000000.000')||'/'
                || to_char(t_acctime(7),'000000.000')||'/'||to_char(t_acctime(8),'000000.000')||';';
      v_memo := v_memo || '(' || v_message || ')';
    END IF;
    SP_BA_RECJOBLOG ( p_job_no       => v_job_no,
                     p_job_id       => 'CIT0260G',
                     p_step         => p_step,
                     p_in_count     => v_in_cnt,
                     p_out_count    => v_out_cnt,
                     p_proc_count   => v_skip_cnt,
                     p_err_count    => v_err_cnt,
                     p_memo         => v_memo,
                     p_table_name   => v_cvldtl_cnt || ' - ' || v_cvldtl_err);
  END;
------------------------------------------------------------------------------
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------
begin
--  初始化
  if (trim(pDate) is not null) then
    v_date := pDate;
    v_proym := substr(pDate,1,6);
  end if;
  v_cvept := v_date;
  v_brdte := to_char(to_number(substr(v_cvept,1,6)) - 4000) || '99'; --年齡算到月
  v_rng12 := ' ['||p_range1||'-'||p_range2||'] ';
  get_jobno;
  writeLog ('Start CIT0260G',
            '年資截止日 (' || v_date || ' - ' || v_brdte || ') 處理範圍'||v_rng12,
            'N');

  select max(ciid) into p_ciid
    from cit0260
   where idn>=p_range1 and idn<p_range2;
  p_ciid := nvl(trim(p_ciid),' ');

  ttime := systimestamp;
  select extract(HOUR FROM ttime)*3600+extract(MINUTE FROM ttime)*60+
         extract(SECOND FROM ttime) into v_ctime from dual;

  v_ftyp := 'L';
LOOP
  sw_exit := true;
  FOR tin IN cur_cipb LOOP
    BEGIN
      calc_time(1);

      v_in_cnt := v_in_cnt + 1;
      v_cit0260 := tin;
      v_ciid := tin.ciid;
      v_idn := tin.idn;

      --if (substr(v_idn,2,1) = '1') then
      IF (tin.sex = 'M') THEN
        v_sex := '1';
      ELSE
        v_sex := '2';
      END IF;

      /*
      PKG_CI_T02.CI_T03(v_ftyp, v_ciid,     v_avgm,
                        null,   v_cvept,    null,
                        null,   null,       null, null,
                        null,   v_p126out);
      */
      a70348.PKG_CI_T02.CI_T03(v_ftyp, v_ciid,     v_avgm,
                        null,   v_cvept,    null,
                        null,   v_out2,     v_p126out);
      calc_time(2);

      v_age := substr(v_proym,1,4) - substr(v_cit0260.brdte,1,4);
      IF (substr(v_cit0260.brdte,5,2) > substr(v_proym,5,2)) THEN
        v_age := v_age - 1;
      END IF;
      --v_oldty := v_p126out.itrmy;
      v_oldty := v_p126out.noldty;
      v_cit0260.bscd := v_p126out.efmk;
      v_cit0260.age := v_age;
      v_cit0260.wage := v_p126out.avgwg;
      v_cit0260.noldty := v_p126out.noldty;
      v_cit0260.noldtm := v_p126out.noldtm;
      v_cit0260.itrmy := v_p126out.itrmy;
      v_cit0260.itrmd := v_p126out.itrmd;
      v_cit0260.proym := v_proym;
      v_cit0260.markA := 'X';
      v_cit0260.markB := 'X';
      v_cit0260.markC := 'X';
      v_cit0260.markD := 'X';
      v_cit0260.upd_datetime := sys_time;
      v_cit0260.ccnt := 1;
      v_cit0260.uno := v_out2.uno;
      v_cit0260.tp := v_out2.tp;
      if (v_age >= 50) then
        if (v_oldty >= 25) then
          v_cit0260.markA := 'A'; --年齡滿50歲年資滿25年
        end if;
        if ((v_sex='1' and v_age>=60) or (v_sex='2' and v_age>=55)) then
          if (v_oldty >= 1) then
            v_cit0260.markB := '1'; --年齡滿[60/55]歲年資滿1年
          end if;
        elsif (v_sex='1' and v_age>=55) then
          if (v_oldty >= 15) then
            v_cit0260.markB := '2'; --年齡滿55歲年資滿15年
          end if;
        elsif (v_oldty >= 25) then
          v_cit0260.markB := '3'; --年齡滿50歲年資滿25年
        end if;
        calc_time(3);
        goto chk_cvldtl;
      end if;
      calc_time(3);
      if (v_oldty < 25) then
        v_skip_cnt := v_skip_cnt + 1; --年齡不滿50歲年資不滿25年不處理
        goto nxt_proc;
      end if;

      v_cit0260.markA := 'B'; --年齡不滿50歲年資滿25年
      tmp_runo.delete;
      v_relationid := ' ';
      sys_i := 0;
      for tcmf16 in cur_bccmf16
      loop
        if (tcmf16.relationid is null or tcmf16.relationid<>v_relationid) then
          sys_i := sys_i + 1;
          tmp_runo(sys_i) := tcmf16.uno;
          v_relationid := tcmf16.relationid;
        else
          tmp_runo(sys_i) := tmp_runo(sys_i) || ',' || tcmf16.uno;
        end if;
      end loop;
      calc_time(4);

      --t_p126out.itrmy := 0;
      --t_p126out.itrmd := 0;
      t_p126out.noldty := 0;
      t_p126out.noldtm := 0;
      v_runo := '';
      for sys_i in 1..tmp_runo.count
      loop
        /*
        PKG_CI_T02.CI_T03(v_ftyp, v_ciid,     v_avgm,
                          null,   v_cvept,    null,
                          null,   null,       null, null,
                          tmp_runo(sys_i),    v_p126out);
        */
        a70348.PKG_CI_T02.CI_T03(v_ftyp,          v_ciid,  v_avgm,
                          null,            v_cvept, null,
                          tmp_runo(sys_i), v_out2,  v_p126out);
        --if (v_p126out.itrmy > t_p126out.itrmy or
        --  (v_p126out.itrmy = t_p126out.itrmy and v_p126out.itrmd > t_p126out.itrmd)) then
        if (v_p126out.noldty > t_p126out.noldty or
          (v_p126out.noldty = t_p126out.noldty and v_p126out.noldtm > t_p126out.noldtm)) then
          t_p126out := v_p126out;
          v_runo := tmp_runo(sys_i);
        end if;
      end loop;
      calc_time(5);

      v_cit0260.noldty := t_p126out.noldty;
      v_cit0260.noldtm := t_p126out.noldtm;
      v_cit0260.runo := v_runo;
      --v_oldty := t_p126out.itrmy;
      v_oldty := t_p126out.noldty;
      if (v_oldty < 25) then --不滿50歲且同一單位年資不滿25年
        v_cit0260.ccnt := 0; --年資不足, 不列入預估值
      else
        v_cit0260.markB := '4'; --年齡不滿50歲同一單位年資滿25年
      end if;
      calc_time(6);

      <<chk_cvldtl>>
      BEGIN
        v_cvldtl_cnt := v_cvldtl_cnt + 1;
        select * into v_cvldtl from bliadm.cvldtl a where a.idn=v_idn;
        v_cit0260.speccode := v_cvldtl.speccode;
        v_cit0260.markC := 'Y'; --戶政存此身分證號
        if (v_cvldtl.ebdate = tin.brdte) then
          if (v_cvldtl.name = tin.name) then
            v_cit0260.markD := '0'; --出生姓名均相同
          else
            v_cit0260.markD := '2'; --出生相同姓名不同
          end if;
        else
          if (v_cvldtl.name = tin.name) then
            v_cit0260.markD := '1'; --出生不同姓名相同
          else
            v_cit0260.markD := '3'; --出生姓名均不同
          end if;
        end if;
      EXCEPTION
        WHEN OTHERS THEN
          v_cvldtl_err := v_cvldtl_err + 1;
          v_cit0260.markC := 'N'; --戶政不存此身分證號
      END;
      calc_time(7);

      insert into cit0260 values v_cit0260;
      --commit;
      v_out_cnt := v_out_cnt + 1;
      calc_time(8);
    EXCEPTION
      WHEN OTHERS THEN
        rollback;
        v_err_cnt := v_err_cnt + 1;
        writeLog ('執行中'||v_rng12||'(' || v_ftyp || ')(' || v_ciid || ')(' || v_idn || ')',
                  '錯誤:['||SQLERRM||']['||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE||'] ',
                  'Y');
    END;
    <<nxt_proc>>
    if (mod(v_in_cnt,10000) = 0) then
      commit;
      writeLog ('執行中'||v_rng12, '執行中 ... ', 'Y');
        if (mod(v_in_cnt,v_pb_max) = 0) then --每v_pb_max筆數,離開迴圈,再重取cursor
          p_ciid := v_ciid;
          --p_idn := v_idn;
          sw_exit := false;
          exit;
        end if;
    END if;

    ttime := systimestamp;
    select extract(HOUR FROM ttime)*3600+extract(MINUTE FROM ttime)*60+
           extract(SECOND FROM ttime) into v_ctime from dual;
  END loop;
  commit;
  exit when sw_exit=true;
  p_ciid := v_ciid;
  select count(*) into sys_counter from baappbase; --延遲
  commit;
END LOOP;
  writeLog ('End CIT0260G'||v_rng12, 'End CIT0260G ', 'Y');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    writeLog (v_rng12||'(' || v_ftyp || ')(' || v_ciid || ')(' || v_idn || ')',
              '錯誤:['||SQLERRM||']['||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE||'] ',
              'Y');
END;
END SP_BA_CIT0260G;


PROCEDURE SP_BA_CIT0260_BATCH
IS
BEGIN
-- ============================================================================
-- 程式目的：處理分流:依據CIT0260資料重算勞保年金年資金額資料
-- 撰寫人員：Justin Yu
-- 執行方式：
-- 傳入參數：
-- 傳出參數：
-- 相關TABLE：
--     R/W      表格代號   表格中文名稱
-- ----------- ----------  ----------------------------------------------------
--
-- 修改紀錄：
-- 維護人員        日期    說明
-- -----------   --------  ----------------------------------------------------
-- Justin Yu     20160505
-- ============================================================================
--                          <<<<<<<<< 變數區 >>>>>>>>>>
-------------------------------------------------------------------------------
  DECLARE
    -- 宣告變數
    v_jobno        mmjoblog.job_no%TYPE;
    v_starttime    TIMESTAMP;             --開始時間
    v_job          INTEGER := 1;
    v_interval     VARCHAR(100) := null;
    v_no_parse     BOOLEAN := false;
    v_instance1    BINARY_INTEGER := 1;    --測試區是1 2 3 4
    v_instance3    BINARY_INTEGER := 2;    --上線區是5 6 7 8(建議跑6 8,線上程式會跑5 7)
    v_instance2    BINARY_INTEGER := 3;
    v_instance4    BINARY_INTEGER := 4;
    v_force        BOOLEAN := false;
    v_d_time       INTEGER := 1;



-- ============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
    PROCEDURE get_jobno
    IS
    BEGIN
       SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_jobno FROM DUAL;
    END get_jobno;
-- ============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------
  BEGIN
    --寫入開始LOG --(S)
    get_jobno;
    v_starttime := SYSTIMESTAMP;
    SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'SP_BA_CIT0260_BATCH',
                    p_step   => '開始分流產製CIT0260資料重算勞保年金年資金額資料',
                    p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');
    --寫入開始LOG --(E)


    --開始分流 --(S)
    --RANGE='A'
    v_d_time := 1;

    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''A'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance1,v_force);
    commit;

    --RANGE='B'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''B'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance2,v_force);
    commit;

    --RANGE='C'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''C'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance3,v_force);
    commit;

    --RANGE='D'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''D'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance4,v_force);
    commit;

    --RANGE='E'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''E'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance1,v_force);
    commit;

    --RANGE='F'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''F'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance2,v_force);
    commit;

    --RANGE='G'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''G'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance3,v_force);
    commit;

    --RANGE='H'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''H'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance4,v_force);
    commit;

    --RANGE='I'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''I'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance1,v_force);
    commit;

    --RANGE='J'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''J'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance2,v_force);
    commit;

    --RANGE='K'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''K'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance2,v_force);
    commit;

    --RANGE='L'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''L'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance3,v_force);
    commit;

    --RANGE='M'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''M'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance4,v_force);
    commit;

    --RANGE='N'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''N'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance1,v_force);
    commit;

    --RANGE='O'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''O'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance2,v_force);
    commit;

    --RANGE='P'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''P'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance3,v_force);
    commit;

    --RANGE='Q'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''Q'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance4,v_force);
    commit;

    --RANGE='R'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''R'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance1,v_force);
    commit;

    --RANGE='S'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''S'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance2,v_force);
    commit;

    --RANGE='T'
    v_d_time := v_d_time + 3;
    DBMS_JOB.SUBMIT(v_job,
                    'PKG_BA_CIT0260.SP_BA_CIT0260R(''T'');',
                    SYSDATE+v_d_time/(24*60*60),
                    v_interval,v_no_parse,v_instance3,v_force);
    commit;
      --開始分流 --(E)

      SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'SP_BA_CIT0260_BATCH',
                    p_step   => '結束分流產製CIT0260資料重算勞保年金年資金額資料',
                    p_memo   => '結束時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');
  EXCEPTION
    WHEN OTHERS THEN
      SP_BA_RECJOBLOG(p_job_no => v_jobno,
                     p_job_id => 'SP_BA_CIT0260_BATCH',
                     p_step   => '處理分流發生錯誤',
                     p_memo   => '錯誤代碼：'||SQLCODE||CHR(10)||
                                 '錯誤訊息：'||SQLERRM||CHR(10)||
                                 '錯誤軌跡：'||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
  END;
END SP_BA_CIT0260_BATCH;

PROCEDURE SP_BA_CIT0260R(p_range IN VARCHAR2) is
-- ============================================================================
-- 程式目的：依據CIT0260資料,以民國48年3月31日以前出生者,
--           重算勞保年金年資金額產出CIT0260R資料
-- 撰寫人員：Justin Yu
-- 執行方式：
-- 傳入參數：range
-- 傳出參數：
-- 相關TABLE：讀取CIT0260,產出CIT0260R
--     R/W      表格代號   表格中文名稱
-- ----------- ----------  ----------------------------------------------------
--
-- 修改紀錄：
-- 維護人員        日期    說明
-- -----------   --------  ----------------------------------------------------
-- Justin Yu     20160505
-- ============================================================================
    -- 宣告變數
    v_brdte       varchar2(08) := '19590401';--以民國48年3月31日以前出生者CIT0260R資料
    v_cvept       varchar2(08) := '20160331';
    v_applydate   varchar2(08) := '20160401';
    v_payrate     cit0260payrate.payrate%type := '0.0155';
    TYPE AVGM_TABLE IS VARRAY(30) OF NUMBER(5);
    avgm_array AVGM_TABLE := AVGM_TABLE(72,84,96,108,132,144,168);

    v_starttime   TIMESTAMP;             --開始時間
    v_jobno       mmjoblog.job_no%TYPE;
    v_pb_max      pls_integer := 200000; --cursor處理上限
    sw_exit       boolean := true;
    v_in_cnt      pls_integer := 0; --輸入cit0260筆數
    v_out_cnt     pls_integer := 0; --輸出cit0260筆數
    v_err_cnt     pls_integer := 0; --處理異常筆數
    v_sqlerrm     varchar2(1024);
    v_range       cit0260.range%type := ' ';
    p_ciid        cit0260.ciid%type := ' ';
    v_ciid        cit0260.ciid%type := ' ';
    v_avgm        NUMBER(5) := 60;
    v_ftyp        cit0260.ftyp%type;
    v_p126out     ci.pl0126m0out%rowtype;
    v_idkey       ci.pl0126m0out.idkey%type:= ' ';
    p_idkey       ci.pl0126m0out.idkey%type:= ' ';
    v_cit0260r    cit0260r%rowtype;
    v_out3        a70348.PKG_CI_T02.tmp_out3_type;
    avgm_i        pls_integer;
    sys_time      ci.cipb.upd_datetime%type := to_char(systimestamp,'YYYYMMDDHH24MISS');
    annuity_out   ANNUITY_TABLE_TYPE;
    --v_out2        ci.pkg_ci_t02.tmp_out2_type;
    --t_p126out     pl0126m0out%rowtype;
    --v_relationid  bliadm.bccmf16.relationid%type;
    --v_runo        varchar2(10240);
    --sys_i         pls_integer;
    --TYPE TMP_RUNO_TABLE IS TABLE OF VARCHAR2(10240) INDEX BY BINARY_INTEGER;
    --tmp_runo TMP_RUNO_TABLE;

    CURSOR cur_cit0260case is
      select ftyp, range,  ciid, idn, idnseq, brdte, name, sex, qmk, bscd,
           age, wage, noldty, noldtm, itrmy, itrmd, marka, markb, markc,
           markd, proym, upd_datetime, ccnt, speccode, runo, uno, tp
      from cit0260 a
      where a.range = p_range
      and a.brdte < v_brdte
      /*and ciid in ('0000012312','0000045192','0014145715','0014145748','0000606647',
                   '0014145811','0014145858','0014145748','0000505424','0000568590')*/
      --and a.ciid > p_ciid
      order by a.ciid;
    t_cit0260case   cur_cit0260case%rowTYPE;

    CURSOR cur_pl0126m0 is
      select itrmy, itrmd, oldty, oldtd, nitrmy, nitrmm, noldty, noldtm,
             itrmd15, efmk, itrml, txcd, efdte, avgwg, havgwg
      from pl0126m0out a, pl0126m0pt b
      where a.idkey = v_idkey
      and a.idkey = b.idkey
      and a.itrml = b.lineno;
    t_pl0126m0   cur_pl0126m0%rowTYPE;

    /**************************************************
		 *取得新舊制的老年年資與投保年資(含關係企業)
     *目前不需要處理
		 ***************************************************/

    /*CURSOR cur_bccmf16 is
      select b.uno, c.relationid
      from ciupb b, bliadm.bccmf16 c
      where b.ftyp=v_ftyp and b.ciid=v_ciid and c.ubno(+)=b.uno
      order by c.relationid, b.uno;*/

-- ============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
    PROCEDURE get_jobno
    IS
    BEGIN
       SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_jobno FROM DUAL;
    END get_jobno;
-- ============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------
  BEGIN
  --寫入開始LOG --(S)
    get_jobno;
    v_starttime := SYSTIMESTAMP;
    SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'SP_BA_CIT0260R',
                    p_step   => '開始產製CIT0260R資料',
                    p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');
  --寫入開始LOG --(E)

	--初始化--
	/*select max(ciid) into p_ciid
    from cit0260r a
   	where a.range = p_range
    and a.brdte < v_brdte;

  p_ciid := nvl(trim(p_ciid),' ');

	LOOP
  sw_exit := true;*/
  FOR t_cit0260case IN cur_cit0260case
    LOOP
	    BEGIN
		/**************************************************
		 *處理X個月的月平均薪資
		 *v_out3已內含有最高120個月與最高180個月的月平均薪資
		 *參數第3位avgm帶入的X,可算出最高X個月的月平均薪資
		 *v_pl0126out.havgwg則是該X個月的月平均薪資
     *此處不會處理最高60個月的月平均薪資
		 ***************************************************/
		v_in_cnt := v_in_cnt + 1;
    v_ciid := t_cit0260case.ciid;
    FOR avgm_i in 1..avgm_array.count
		LOOP
			  	a70348.PKG_CI_T02.CI_T06(t_cit0260case.ftyp,   t_cit0260case.ciid,  avgm_array(avgm_i),
			                            null, v_cvept,    null,
			                            null, v_out3,  v_p126out);
			    IF( avgm_array(avgm_i) = 72 ) THEN
		      			  v_cit0260r.avgwg72   :=   v_p126out.havgwg;
				    ELSIF avgm_array(avgm_i) = 84  THEN
		      			  v_cit0260r.avgwg84   :=   v_p126out.havgwg;
		      	ELSIF avgm_array(avgm_i) = 96  THEN
		      			  v_cit0260r.avgwg96   :=   v_p126out.havgwg;
		      	ELSIF avgm_array(avgm_i) = 108 THEN
		      			  v_cit0260r.avgwg108  :=   v_p126out.havgwg;
		      	ELSIF avgm_array(avgm_i) = 132 THEN
		      			  v_cit0260r.avgwg132  :=   v_p126out.havgwg;
		      	ELSIF avgm_array(avgm_i) = 144  THEN
		      			  v_cit0260r.avgwg144  :=   v_p126out.havgwg;
		      	ELSIF avgm_array(avgm_i) = 168 THEN
		      			  v_cit0260r.avgwg168  :=   v_p126out.havgwg;
		        END IF;
	    END LOOP;

	    v_cit0260r.avgwg120   :=   v_out3.havgwg2;
	    v_cit0260r.avgwg180   :=   v_out3.havgwg3;

     /**************************************************
		 *取得新舊制的老年年資,投保年資與年資滿15年日期
		 ***************************************************/
      v_avgm := '60';
      /*CI.PKG_CI_T02.CI_T03(t_cit0260case.ftyp, t_cit0260case.ciid,     v_avgm,
                        null,   v_cvept,    null,
                        null,   v_out2,     v_p126out); */
      /*
      *為了取得退保日期,改呼叫CI.PKG_CI_T02.CI_T01,其條件如下:
      *若被保險人退保:生效註記不等於Y
		  *且最後一筆異動的異動別等於2(退保),將退保日期改放最後一筆異動日期
      */

      CI.PKG_CI_T01.CI_T01(t_cit0260case.ftyp,
                           t_cit0260case.ciid,
                           v_avgm,
                           null,
                           v_cvept,
                           null,
                           null,
                           null,
                           null,
                           null,
                           null,
                           p_idkey);
      v_idkey := p_idkey;

      FOR t_pl0126m0 IN cur_pl0126m0
      LOOP
        IF (t_pl0126m0.efmk <> 'Y' and t_pl0126m0.txcd = '2') THEN
          v_cvept := t_pl0126m0.efdte;
        END IF;
        --dbms_output.put_line('idkey:('||v_idkey||')');

        /*取得該平均薪資所對應的所得替代率*/
        /*select payrate into v_payrate from cit0260payrate a
        where t_pl0126m0.havgwg between a.minavgwg and a.maxavgwg;*/

        v_cit0260r.cvept   := v_cvept;
        v_cit0260r.avgwg36 := t_pl0126m0.avgwg;
        v_cit0260r.avgwg60 := t_pl0126m0.havgwg;
        v_cit0260r.itrmy   := t_pl0126m0.itrmy;
        v_cit0260r.itrmd   := t_pl0126m0.itrmd;
        v_cit0260r.oldty   := t_pl0126m0.oldty;
        v_cit0260r.oldtd   := t_pl0126m0.oldtd;
        v_cit0260r.nitrmy  := t_pl0126m0.nitrmy;
        v_cit0260r.nitrmm  := t_pl0126m0.nitrmm;
        v_cit0260r.noldty  := t_pl0126m0.noldty;
        v_cit0260r.noldtm  := t_pl0126m0.noldtm;
        v_cit0260r.itrmd15 := t_pl0126m0.itrmd15;
      END LOOP;

      /**************************************************
		 *取得新舊制的老年年資與投保年資(含關係企業)
     *目前不需要處理
		 ***************************************************/
      --處理含關係企業start--
      /*tmp_runo.delete;
      v_relationid := ' ';
      sys_i := 0;
      v_ftyp  := t_cit0260case.ftyp;
      v_ciid  := t_cit0260case.ciid;
      FOR tcmf16 IN cur_bccmf16
      LOOP
        IF (tcmf16.relationid is null OR
            tcmf16.relationid<>v_relationid) THEN
          sys_i := sys_i + 1;
          tmp_runo(sys_i) := tcmf16.uno;
          v_relationid := tcmf16.relationid;
        ELSE
          tmp_runo(sys_i) := tmp_runo(sys_i) || ',' || tcmf16.uno;
        END IF;
      END LOOP;

      t_p126out.itrmy  := 0;
      t_p126out.itrmd  := 0;
      t_p126out.oldty  := 0;
      t_p126out.oldtd  := 0;
      t_p126out.nitrmy := 0;
      t_p126out.nitrmm := 0;
      t_p126out.noldty := 0;
      t_p126out.noldtm := 0;

      v_runo := '';
      FOR sys_i IN 1..tmp_runo.count
      LOOP
        CI.PKG_CI_T02.CI_T03(t_cit0260case.ftyp, t_cit0260case.ciid,  v_avgm,
                          null,            v_cvept, null,
                          tmp_runo(sys_i), v_out2,  v_p126out);
        IF (v_p126out.noldty > t_p126out.noldty OR
           (v_p126out.noldty = t_p126out.noldty AND
            v_p126out.noldtm > t_p126out.noldtm)) THEN
          t_p126out := v_p126out;
          v_runo := tmp_runo(sys_i);
        END IF;
      END LOOP;

      v_cit0260r.itrmy  := t_p126out.itrmy;
      v_cit0260r.itrmd  := t_p126out.itrmd;
      v_cit0260r.oldty  := t_p126out.oldty;
      v_cit0260r.oldtd  := t_p126out.oldtd;
      v_cit0260r.nitrmy := t_p126out.nitrmy;
      v_cit0260r.nitrmm := t_p126out.nitrmm;
      v_cit0260r.noldty := t_p126out.noldty;
      v_cit0260r.noldtm := t_p126out.noldtm;*/
      --處理含關係企業end--

      v_cit0260r.ftyp    := t_cit0260case.ftyp;
      v_cit0260r.range   := t_cit0260case.range;
      v_cit0260r.ciid    := t_cit0260case.ciid;
      v_cit0260r.idn     := t_cit0260case.idn;
      v_cit0260r.idnseq  := t_cit0260case.idnseq;
      v_cit0260r.brdte   := t_cit0260case.brdte;
      v_cit0260r.sex     := t_cit0260case.sex;
      v_cit0260r.bscd    := t_cit0260case.bscd;
      v_cit0260r.age     := t_cit0260case.age;
      v_cit0260r.uno     := t_cit0260case.uno;
      v_cit0260r.upd_datetime := sys_time;

      /**************************************************
		  *呼叫試算勞保老年年金與老年一次給付程式
		  ***************************************************/
      SP_BA_CALCULATE (v_jobno,
                       v_applydate,
                       v_cit0260r.cvept,
                       v_cit0260r.brdte,
                       v_cit0260r.itrmd15,
                       v_payrate,
                       v_cit0260r.avgwg36,
                       v_cit0260r.avgwg60,
                       v_cit0260r.oldty,
                       v_cit0260r.oldtd,
                       v_cit0260r.nitrmy,
                       v_cit0260r.nitrmm,
                       v_cit0260r.noldty,
                       v_cit0260r.noldtm,
                       annuity_out );

      v_cit0260r.seniority := annuity_out.seniority;
      v_cit0260r.oldrate   := annuity_out.oldrate;
      v_cit0260r.oldaamt   := annuity_out.oldaamt;
      v_cit0260r.oldbamt   := annuity_out.oldbamt;
      v_cit0260r.oldab     := annuity_out.oldab;
      v_cit0260r.issueamt  := annuity_out.issueamt;
      v_cit0260r.paymonth  := annuity_out.paymonth;
      v_cit0260r.payamt    := annuity_out.payamt;

	    insert into cit0260r values v_cit0260r;

		  v_out_cnt := v_out_cnt + 1;

	    EXCEPTION
	      WHEN OTHERS THEN
	        v_sqlerrm := SQLERRM;
	        v_err_cnt := v_err_cnt + 1;
	        ROLLBACK;
	        SP_BA_RECJOBLOG(p_job_no => v_jobno,
	                        p_job_id => 'SP_BA_CIT0260R',
	                        p_step   => '執行中'||'(' || v_ftyp || ')(' || v_ciid || ')',
                          p_memo   => '錯誤代碼：'||SQLCODE||CHR(10)||
                                      '錯誤訊息：'||SQLERRM||CHR(10)||
                                      '錯誤軌跡：'||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
	    END;

	    IF (mod(v_in_cnt,10000) = 0) THEN
	      COMMIT;
	      SP_BA_RECJOBLOG(p_job_no => v_jobno,
	                        p_job_id => 'SP_BA_CIT0260R',
	                        p_step   => '執行中'||  v_range ,
	                        p_memo   => '執行中 ... ' );
	        /*IF (mod(v_in_cnt,v_pb_max) = 0) THEN --每v_pb_max筆數,離開迴圈,再重取cursor
	          p_ciid := v_ciid;
	          sw_exit := false;
	          exit;
	        END IF;*/
	    END IF;

	END LOOP;
  COMMIT;
  /*SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'SP_BA_CIT0260R',
                    p_step   => '關閉CURSOR',
                    p_memo   => '關閉CURSOR時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');
  EXIT WHEN sw_exit=true;
  p_ciid := v_ciid;
  COMMIT;
END LOOP;*/
--寫入結束LOG --(S)
    v_starttime := SYSTIMESTAMP;
    SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'SP_BA_CIT0260R',
                    p_step   => '結束產製CIT0260R資料',
                    p_memo   => '結束時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');
--寫入結束LOG --(E)

EXCEPTION
  WHEN OTHERS THEN
  ROLLBACK;
  SP_BA_RECJOBLOG(p_job_no => v_jobno,
                        p_job_id => 'SP_BA_CIT0260R',
                        p_step   => '執行中'||'(' || v_ftyp || ')(' || v_ciid || ')',
                        p_memo   => '錯誤代碼：'||SQLCODE||CHR(10)||
                                    '錯誤訊息：'||SQLERRM||CHR(10)||
                                    '錯誤軌跡：'||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);

END SP_BA_CIT0260R;

PROCEDURE SP_BA_CIT0260RUPDATE
AS
BEGIN
-- ============================================================================
-- 程式目的：更新CIT0260R勞保老年年金與老年一次給付相關欄位
-- 撰寫人員：Justin Yu
-- 執行方式：
-- 傳入參數：
-- 傳出參數：
-- 相關TABLE：
--     R/W      表格代號   表格中文名稱
-- ----------- ----------  ----------------------------------------------------
--
-- 修改紀錄：
-- 維護人員        日期    說明
-- -----------   --------  ----------------------------------------------------
-- Justin Yu     20160505
-- ============================================================================
--                          <<<<<<<<< 變數區 >>>>>>>>>>
-------------------------------------------------------------------------------
  DECLARE
    -- 宣告變數
    v_ciid        cit0260r.ciid%TYPE;
    v_applydate   varchar2(08) := '20160401';
    v_payrate     cit0260payrate.payrate%type := '0.0155';
    annuity_out   ANNUITY_TABLE_TYPE;

    v_starttime   TIMESTAMP;             --開始時間
    v_jobno       mmjoblog.job_no%TYPE;
    v_in_cnt      pls_integer := 0; --輸入cit0260r筆數
    v_out_cnt     pls_integer := 0; --輸出cit0260r筆數
    v_err_cnt     pls_integer := 0; --處理異常筆數
    v_sqlerrm     varchar2(1024);


    CURSOR cur_cit0260case is
      select ciid, cvept, brdte, itrmd15, avgwg36, avgwg60,
             oldty, oldtd, nitrmy, nitrmm, noldty, noldtm
      from cit0260r
      where issueamt is null;

-- ============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------
    PROCEDURE get_jobno
    IS
    BEGIN
       SELECT REPLACE(TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_jobno FROM DUAL;
    END get_jobno;
-- ============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------
  BEGIN
  --寫入開始LOG --(S)
    get_jobno;
    v_starttime := SYSTIMESTAMP;
    SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'SP_BA_CIT0260RUPDATE',
                    p_step   => '開始更新CIT0260R資料',
                    p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');
  --寫入開始LOG --(E)

  FOR t_cit0260case IN cur_cit0260case
    LOOP
	    BEGIN

		v_in_cnt := v_in_cnt + 1;
    v_ciid := t_cit0260case.ciid;

    /**************************************************
		*呼叫試算勞保老年年金與老年一次給付程式
		***************************************************/
    SP_BA_CALCULATE (v_jobno,
                     v_applydate,
                     t_cit0260case.cvept,
                     t_cit0260case.brdte,
                     t_cit0260case.itrmd15,
                     v_payrate,
                     t_cit0260case.avgwg36,
                     t_cit0260case.avgwg60,
                     t_cit0260case.oldty,
                     t_cit0260case.oldtd,
                     t_cit0260case.nitrmy,
                     t_cit0260case.nitrmm,
                     t_cit0260case.noldty,
                     t_cit0260case.noldtm,
                     annuity_out );

	  update cit0260r set seniority = annuity_out.seniority,
                        oldrate   = annuity_out.oldrate,
                        oldaamt   = annuity_out.oldaamt,
                        oldbamt   = annuity_out.oldbamt,
                        oldab     = annuity_out.oldab,
                        issueamt  = annuity_out.issueamt,
                        paymonth  = annuity_out.paymonth,
                        payamt    = annuity_out.payamt
    where ciid = v_ciid
    and issueamt is null;

		v_out_cnt := v_out_cnt + 1;

	    EXCEPTION
	      WHEN OTHERS THEN
	        v_sqlerrm := SQLERRM;
	        v_err_cnt := v_err_cnt + 1;
	        ROLLBACK;
	        SP_BA_RECJOBLOG(p_job_no => v_jobno,
	                        p_job_id => 'SP_BA_CIT0260RUPDATE',
	                        p_step   => '執行中'||'(' || v_ciid || ')',
                          p_memo   => '錯誤代碼：'||SQLCODE||CHR(10)||
                                      '錯誤訊息：'||SQLERRM||CHR(10)||
                                      '錯誤軌跡：'||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
	      END;
	END LOOP;
  COMMIT;

  SP_BA_RECJOBLOG (p_job_no => v_jobno,
                    p_job_id => 'SP_BA_CIT0260RUPDATE',
                    p_step   => '結束開始更新CIT0260R資料',
                    p_memo   => '結束時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_jobno||')');

  EXCEPTION
    WHEN OTHERS THEN
      SP_BA_RECJOBLOG(p_job_no => v_jobno,
                     p_job_id => 'SP_BA_CIT0260RUPDATE',
                     p_step   => '更新CIT0260R資料錯誤',
                     p_memo   => '錯誤代碼：'||SQLCODE||CHR(10)||
                                 '錯誤訊息：'||SQLERRM||CHR(10)||
                                 '錯誤軌跡：'||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
  END;
END SP_BA_CIT0260RUPDATE;

PROCEDURE SP_BA_CALCULATE (p_jobno      IN VARCHAR2,
                           p_applydate  IN VARCHAR2,
                           p_cvept      IN VARCHAR2,
                           p_brdte      IN VARCHAR2,
                           p_itrmd15    IN VARCHAR2,
                           p_payrate    IN VARCHAR2,
                           p_avgwg      IN VARCHAR2,
                           p_havgwg     IN VARCHAR2,
                           p_oldty      IN VARCHAR2,
                           p_oldtd      IN VARCHAR2,
                           p_nitrmy     IN VARCHAR2,
                           p_nitrmm     IN VARCHAR2,
                           p_noldty     IN VARCHAR2,
                           p_noldtm     IN VARCHAR2,
                           p_out        OUT ANNUITY_TABLE_TYPE )
IS
BEGIN
-- ============================================================================
-- 程式目的：試算勞保老年年金與老年一次給付
-- 撰寫人員：Justin Yu
-- 執行方式：
-- 傳入參數：
-- 傳出參數：
-- 相關TABLE：
--     R/W      表格代號   表格中文名稱
-- ----------- ----------  ----------------------------------------------------
--
-- 修改紀錄：
-- 維護人員        日期    說明
-- -----------   --------  ----------------------------------------------------
-- Justin Yu     20160505
-- ============================================================================
--                          <<<<<<<<< 變數區 >>>>>>>>>>
-------------------------------------------------------------------------------
  DECLARE
    -- 宣告變數
    --v_starttime    TIMESTAMP;             --開始時間
    v_oldrate      cit0260r.oldrate%TYPE;
    v_seniority    NUMBER(5,2) := 0;
    p_out2         ANNUITY_TABLE_TYPE;



-- ============================================================================
-- PROCEDURE & FUNCTION AREA
-------------------------------------------------------------------------------

-- ============================================================================
--                      <<<<<<<<< MAIN procedure >>>>>>>>>>
------------------------------------------------------------------------------
  BEGIN
    --寫入開始LOG --(S)
    /*SP_BA_RECJOBLOG (p_job_no => p_jobno,
                    p_job_id => 'SP_BA_CALCULATE',
                    p_step   => '計算勞保年金與一次給付',
                    p_memo   => '開始時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||p_jobno||')');*/
    --寫入開始LOG --(E)
    p_out := null;

   /**計算展減比例
    *1.從出生年月日取得屆齡年齡(可申請年齡)
    *2.可申請日期=出生年月日+屆齡年齡(可申請年齡)
    *3.若年資滿15年日期>可申請日期,則可申請日期=年資滿15年日期
    *4.(申請日期-可申請日期)來計算展減比例
    */
    v_oldrate := FN_BA_CALCULATERATE(FN_BA_FITMONTH(p_brdte,p_itrmd15), p_applydate);
    p_out.oldrate := v_oldrate;

    v_seniority := FN_BA_CALCULATESENIORITY(p_nitrmy,p_nitrmm);
    p_out.seniority := v_seniority;

   /** 計算老年年金。
    * p_havgwg     平均月投保薪資
    * v_seniority  老年年金年資
    * v_oldrate    展延/減額比率
    * p_payrate    所得替代率
    * @return 計算出兩種老年年金的結果
    * 公式1 (平均月投保薪資*老年年金年資*0.775%+3000)*(1+展延(or遞延)比率)
    * 公式2 (平均月投保薪資*老年年金年資*1.55%)*(1+展延(or遞延)比率)
    */
    SP_BA_CALCULATEANNUITY(p_havgwg, v_seniority, v_oldrate, p_payrate,p_out2);
    p_out.oldaamt := p_out2.oldaamt;
    p_out.oldbamt := p_out2.oldbamt;
    if(p_out.oldaamt > p_out.oldbamt) THEN
        p_out.oldab := '1';
        p_out.issueamt := p_out2.oldaamt;
    ELSE
        p_out.oldab := '2';
        p_out.issueamt := p_out2.oldbamt;
    END IF;

    /*計算一次給付金額:平均薪資*給付月數*/
    p_out.paymonth := FN_BA_CALCULATEPAYMONTH(p_oldty, p_oldtd, p_noldty, p_noldtm, p_brdte, p_cvept);
    p_out.payamt := p_avgwg * p_out.paymonth;

    /*SP_BA_RECJOBLOG (p_job_no => p_jobno,
                    p_job_id => 'SP_BA_CALCULATE',
                    p_step   => '結束計算勞保年金與一次給付',
                    p_memo   => '結束時間：'||TO_CHAR(v_starttime,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||p_jobno||')');*/

  EXCEPTION
    WHEN OTHERS THEN
      SP_BA_RECJOBLOG(p_job_no => p_jobno,
                     p_job_id => 'SP_BA_CALCULATE',
                     p_step   => '計算勞保年金與一次給付錯誤',
                     p_memo   => '錯誤代碼：'||SQLCODE||CHR(10)||
                                 '錯誤訊息：'||SQLERRM||CHR(10)||
                                 '錯誤軌跡：'||DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
  END;
END SP_BA_CALCULATE;

/**
	* 計算兩種老年年金。
	* @param havgwg 平均月投保薪資
	* @param seniority  老年年金年資
	* @param rate  展延/減額比率
	* @return 計算出兩種老年年金的結果
	* 公式1 (平均月投保薪資*老年年金年資*0.775%+3000)*(1+展延(or遞延)比率)
	* 公式2 (平均月投保薪資*老年年金年資*1.55%)*(1+展延(or遞延)比率)
	*/
  PROCEDURE SP_BA_CALCULATEANNUITY(p_havgwg    IN VARCHAR2,
                                  p_seniority IN VARCHAR2,
                                  p_rate      IN NUMBER,
                                  p_payrate   IN NUMBER,
                                  p_out       OUT ANNUITY_TABLE_TYPE) IS
  v_havgwg     NUMBER(7) := 0;
  v_seniority  NUMBER(5,2) := 0;
  v_oldaamt    NUMBER(7) := 0;
  v_oldbamt    NUMBER(7) := 0;

  BEGIN
    p_out := null;
    v_havgwg    := to_number(p_havgwg);
    v_seniority := to_number(p_seniority);
    v_oldaamt := round(round(((v_havgwg*v_seniority*p_payrate/2)+3000),0)*(1+p_rate),0);
    v_oldbamt := round(round(((v_havgwg*v_seniority*p_payrate)),0)*(1+p_rate),0);
    p_out.oldaamt := v_oldaamt;
    p_out.oldbamt := v_oldbamt;

  END SP_BA_CALCULATEANNUITY;

/**
	* 計算展延/減額比率。
	* @param p_fitdate    屆齡日期
  * @param p_applaydate 預計申請日期差距之年月
	* @return 計算展延/減額比率的結果
	* 公式  4%*(年+月/12) 最多不超過20%
	* 兩日期差距精算到月數
	*/
  FUNCTION FN_BA_CALCULATERATE(p_fitmonth    IN VARCHAR2,
                               p_applydate IN VARCHAR2) RETURN NUMBER IS
  v_rate            NUMBER(5,4) := 0;
  v_monthsdiff      NUMBER(5,2) := 0;

  BEGIN
    v_monthsdiff := MONTHS_BETWEEN(TO_DATE(substr(p_applydate,1,6),'YYYYMM'),
                                   TO_DATE(p_fitmonth,'YYYYMM'));
    v_rate := round(round(v_monthsdiff /12,2)*0.04,4);
    IF (v_rate > 0.2) THEN
        v_rate := 0.2;
    ELSIF (v_rate < -0.2) THEN
          v_rate := -0.2;

    END IF;

    --DBMS_OUTPUT.PUT_LINE('v_rate : ' || v_rate);
  RETURN v_rate;
  END FN_BA_CALCULATERATE;

  /**
		* 以出生年月日計算符合請領年齡。
		* @p_brdte 出生年月日字串日期(19630620)
		* @return  符合請領年齡
    * 得請領年齡 |       60       |     61    |     62    |     63    |     64    |         65          |
    * 民國(年)   |      106       | 107 | 108 | 109 | 110 | 111 | 112 | 113 | 114 | 115 |      116      |
    * 出生年次   |46年(含)以前出生|     |  47 |     |  48 |     |  49 |     |  50 |     |  51年(含)以後 |
		*/
  FUNCTION FN_BA_FITAGE(p_brdte IN VARCHAR2) RETURN NUMBER IS
  v_age       NUMBER(2) := '65';
  BEGIN
    IF (p_brdte < '19580101') THEN
       v_age := '60';
      ELSIF (p_brdte >= '19580101' and p_brdte < '19590101') THEN
       v_age := '61';
      ELSIF (p_brdte >= '19590101' and p_brdte < '19600101') THEN
       v_age := '62';
      ELSIF (p_brdte >= '19600101' and p_brdte < '19610101') THEN
       v_age := '63';
      ELSIF (p_brdte >= '19610101' and p_brdte < '19620101') THEN
       v_age := '64';
    ELSE
        v_age := '65';
    END IF;

    --DBMS_OUTPUT.PUT_LINE('v_age : ' || v_age);
  RETURN v_age;

  END FN_BA_FITAGE;

  /**
		* 可申請之年月 = 當年度符合請領年齡 + 出生年月。
    * 可申請之年月 < 年資滿15年之日期 ,則須以年資滿15年之日期為可申請之年月
		* @param p_brdte   出生年月日
		* @param p_itrmd15 年資滿15年之日期
		* @return 可申請之年月
		*/
  FUNCTION FN_BA_FITMONTH(p_brdte   IN VARCHAR2, p_itrmd15   IN VARCHAR2) RETURN VARCHAR2 IS
  v_fitage      NUMBER(2)   := 0;
  v_fitmonth    VARCHAR2(6) := '0';
  v_itrmd15     VARCHAR2(6) := '0';

  BEGIN
    v_fitage   := FN_BA_FITAGE(p_brdte);
    v_fitmonth := to_char(ADD_MONTHS(to_date(substr(p_brdte,1,6),'YYYYMM'),v_fitage*12),'YYYYMM');

    /*SP_BA_RECJOBLOG (p_job_no => p_jobno,
                    p_job_id => 'SP_BA_CALCULATE',
                    p_step   => '計算勞保年金與一次給付',
                    p_memo   => 'v_fitdate：('||v_fitdate||')');*/

    v_itrmd15 := substr(p_itrmd15,1,6);
    IF(v_itrmd15 > v_fitmonth) THEN
        v_fitmonth := v_itrmd15;
    END IF;


    --DBMS_OUTPUT.PUT_LINE('v_fitdate : ' || v_fitdate);
    RETURN v_fitmonth;
  END FN_BA_FITMONTH;

  /**
	 * 計算老年年金年資,精算到月後,四捨五入至小數第二位。
	 * @param v_nitrmy 老年年金年資的年數
	 * @param v_nitrmm 老年年金年資的月數
	 * @return 計算出年資四捨五入至小數第二位後的結果
	 */
  FUNCTION FN_BA_CALCULATESENIORITY(p_nitrmy IN NUMBER,
                              p_nitrmm IN NUMBER) RETURN NUMBER IS
  v_seniority       NUMBER(5,2) := 0;
  v_nitrmy          NUMBER(3)   := 0;
  v_nitrmm          NUMBER(2)   := 0;

  BEGIN
    v_nitrmy := p_nitrmy;
    v_nitrmm := p_nitrmm;
    v_seniority := v_nitrmy + round(v_nitrmm/12,2);

    --DBMS_OUTPUT.PUT_LINE('v_seniority : ' || v_seniority);
  RETURN v_seniority;
  END FN_BA_CALCULATESENIORITY;

  /**
		* 一次請領老年給付,給付月數計算。
		* @param yy 舊制年資年數(012)
		* @param dd 舊制年資日數(182)
		* @param nyy 新制年資年數(012)
		* @param nmm 新制年資月數(07)
		* @param birthdte 被保險人出生年月日
		* @param cvept 退職(保)日
		* @return 給付月數
		* 0981105 修改 退職日(CVEPT)在0960101與0980101之間者,超過183天以一年計,未滿183天按月依比例計算
		*              退職日(CVEPT)在0960101前者,超過183天以一年計,未滿183天不計算(舊制)
	  */

  FUNCTION FN_BA_CALCULATEPAYMONTH(p_oldty  IN VARCHAR2, p_oldtd  IN VARCHAR2,
                             p_noldty IN VARCHAR2, p_noldtm IN VARCHAR2,
                             p_brdte  IN VARCHAR2, p_cvept  IN VARCHAR2) RETURN NUMBER IS
  v_daydiff         VARCHAR2(2) := '0';
  v_paymonths       NUMBER(5,2) := 0;
  v_oldty           NUMBER(3)   := 0;
  v_oldtd           NUMBER(3)   := 0;
  v_noldty          NUMBER(3)   := 0;
  v_noldtm          NUMBER(2)   := 0;

  BEGIN
    v_oldty   := to_number(p_oldty);
    v_oldtd   := to_number(p_oldtd);
    v_noldty  := to_number(p_noldty);
    v_noldtm  := to_number(p_noldtm);
    v_daydiff    := substr(v_performdate,1,4) - substr(p_cvept,1,4);

    --DBMS_OUTPUT.PUT_LINE('('||v_performdate||'-'||p_cvept||') = ' || v_daydiff);
    IF (v_daydiff > 0) THEN
        --20090101以前為舊制
        IF (v_daydiff <= 2) THEN
            IF (v_oldtd >= 183) THEN
                --舊制在20070101~20090101間,超過183天,以一年計
                v_oldty := v_oldty + 1;
                v_paymonths := FN_BA_PAYMONTHS(v_oldty, 0, p_brdte, p_cvept);
            ELSE
                --舊制在20070101~20090101間,未滿183天,按月依比例計算
                v_paymonths := FN_BA_PAYMONTHS(v_noldty, v_noldtm, p_brdte, p_cvept);
            END IF;
        ELSE
            IF (v_oldtd >= 183) THEN
                --舊制在20070101以前,超過183天,以一年計,未滿183天不計算
                v_oldty := v_oldty + 1;
            END IF;
                v_paymonths := FN_BA_PAYMONTHS(v_oldty, 0, p_brdte, p_cvept);
        END IF;
    ELSE
				--20090101以後為新制,按月依比例計算
        v_paymonths := FN_BA_PAYMONTHS(v_noldty, v_noldtm, p_brdte, p_cvept);
    END IF;

    --DBMS_OUTPUT.PUT_LINE('v_paymonths : ' || v_paymonths);
  RETURN v_paymonths;
  END FN_BA_CALCULATEPAYMONTH;

 	/**
		* 給付月數計算。
		* @param p_noldty 新制年資年數(012)
		* @param p_noldtm 新制年資月數(07)
		* @param p_brdte  被保險人出生年月日
		* @param p_cvept  退職(保)日
		* @return 給付月數
		* 給付月數= 年資超過15年的部份*2 + 年資15年*1
		* 被保險人年齡>60歲者(以退職日當日之年齡計),給付月數最高不得大於50個月
		* 被保險人年齡<=60歲者(以退職日當日之年齡計),給付月數最高不得大於45個月
		*/
  FUNCTION FN_BA_PAYMONTHS(p_noldty  IN NUMBER,   p_noldtm  IN NUMBER,
                     p_brdte   IN VARCHAR2, p_cvept   IN VARCHAR2) RETURN NUMBER IS
  v_paymonths       NUMBER(5,2) := 0;
  v_age             VARCHAR2(3) := '0';

  BEGIN
    v_paymonths := FN_BA_CALCULATESENIORITY(p_noldty,p_noldtm);

    IF(v_paymonths > 15) THEN
        v_paymonths := ((p_noldty-15) + round(p_noldtm/12,2))*2 + 15;
    END IF;

    v_age := substr(p_cvept,1,4) - substr(p_brdte,1,4);
    IF(substr(p_brdte,5,2) > substr(p_brdte,5,2)) THEN
        v_age := v_age - 1;
    END IF;

    IF(v_age > 60) THEN
         IF(v_paymonths > 50) THEN
             v_paymonths := 50;
         END IF;
    ELSE
         IF(v_paymonths > 45) THEN
             v_paymonths := 45;
         END IF;

    END IF;
  RETURN v_paymonths;
  END FN_BA_PAYMONTHS;

END PKG_BA_CIT0260;
/

