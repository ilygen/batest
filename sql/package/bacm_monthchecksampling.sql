create or replace procedure ba.BaCm_MONTHCHECKSAMPLING(p_payType IN varchar2,
                                                    p_issuYm  IN varchar2,
                                                    p_resulte OUT varchar2) authid definer is
  -- ============================================================================
  -- 程式名稱 : 月核定抽樣查核對象
  -- 程式目的 : 月核定後抽樣查核對象並將抽出來的資料新增到 月核定抽樣查核檔(BAINSPECTLIST)
  -- 輸入參數 :
  --             p_payType                       給付別
  --             p_issuYm                      核定年月
  --
  -- 輸出參數 : p_resulte                     是否抽查
  -- 相關表格：
  --     I/O      表格代號         表格中文名稱
  -- ----------- ---------------   ----------------------------------------------------
  --              BAPAINSPECT      先抽對象條件參數檔
  --              BAINSPECTLIST    月核定抽樣查核檔
  --
  -- 程式紀錄
  --     人員       日期          備註說明
  -- ----------------------------------------
  --     jerry     2009/05/06   v1.0 初版
begin
  -- ============================================================================
  --                          <<<<<<<<< 變數區 >>>>>>>>>>
  -------------------------------------------------------------------------------
  DECLARE
    v_sqlStmt         varChar2(32767); --動態SQL 抓取資料用
    v_andor           varChar2(5);-- 動態SQL 存放 AND,OR
    v_totalcount      number;-- 資料總筆數
    v_sampleTime      number;-- 抽樣次數
    v_rageS           number;--抽樣範圍起
    v_rageE           number;--抽樣範圍迄
    v_sampeNumber     number;--抽樣件數
    v_sampleNumberTotal number;--本次總抽樣件數
    type type_dataCur is ref cursor;
    v_dataCur         type_dataCur;--游標變數
    v_apno            Varchar2(400);
    --       BAPAINSPECT 先抽對象條件參數檔 資料變數
    v_binsurseni   BAPAINSPECT.Binsurseni%TYPE; --投保年資起
    v_einsurseni   BAPAINSPECT.Einsurseni%TYPE; --投保年資迄
    v_bissueamt    BAPAINSPECT.Bissueamt%TYPE; --核定金額區間起
    v_eissueamt    BAPAINSPECT.Eissueamt%TYPE; --核定金額區間迄
    v_bcondition   BAPAINSPECT.Bcondition%TYPE; --區間條件起(老年年金：記錄抽樣年齡起始值)
    v_econdition   BAPAINSPECT.Econdition%TYPE; --區間條件迄(記錄抽樣年齡終止值)
    v_condition1   BAPAINSPECT.Condition1%TYPE; --條件一(老年年金：記錄抽樣申請項目1.老年年金給付、2.減額老年年金給付、3.危險堅強體力。欄位值內容：全選(1   BAPAINSPECT%TYPE;2   BAPAINSPECT%TYPE;3)、選1及2(1   BAPAINSPECT%TYPE;2)
    v_condition2   BAPAINSPECT.Condition2%TYPE; --條件二(老年年金：不使用)
    v_condition3   BAPAINSPECT.Condition3%TYPE; --條件三(老年年金：不使用)
    v_condition4   BAPAINSPECT.Condition4%TYPE; --條件四(老年年金：不使用)
    v_andortyp     BAPAINSPECT.Andortyp%TYPE; --AndOr條件選項(0：AND選項 1：OR選項)
    v_range        BAPAINSPECT.Range%TYPE; --抽樣間隔數
    v_samplevolume BAPAINSPECT.Samplevolume%TYPE; --抽樣件數(每個抽樣區間抽樣數)
    v_limitamount  BAPAINSPECT.Limitamount%TYPE; --每月限制抽樣件數(總抽查數)
    v_printtyp     BAPAINSPECT.Printtyp%TYPE; --列印方式(0：不按分案原則1：按分案原則)
    v_enable       BAPAINSPECT.Enable%TYPE; --是否抽樣(0：執行抽樣1：不執行抽樣)

    -- ----------------------------------------------------------------------------
    -- FUNCTION AREA
    -------------------------------------------------------------------------------
    -- 抽樣件數之指定：
    -- 依照「間隔數_RANGE」、「抽樣件數_SAMPLEVOLUME」及「每月限制件數_LIMITAMOUNT」。
    -- 例如，「間隔數」指定100，「抽樣件數」指定1，「每月限制件數」指定50，代表自每100件合格案中，
    -- 符合抽樣條件者抽取1件，每月抽樣件數不超過50件。
    PROCEDURE p_sampling is
    begin
    --計算資料總筆數  用來計算抽樣次數
     v_totalcount:=0;
    v_sqlStmt := 'select apno from BaCm_Sampling ';
      open v_dataCur for v_sqlStmt;
        loop
          fetch v_dataCur
            into v_apno;
          exit when v_dataCur%notFound;

       v_totalcount := v_dataCur%rowcount;
        end loop;
      close v_dataCur;

      --計算抽樣次數 根據抽樣區間計算要抽樣幾次  例如: 抽樣區間=3 資料筆數=7 則要抽樣三次
       if((v_totalcount mod v_range)=0) then
         v_sampleTime := (v_totalcount / v_range);
       else
         v_sampleTime := floor(v_totalcount / v_range) + 1;
       end if;

      --執行抽樣
      v_rageS :=1;--設定初始抽樣範圍起
      v_rageE := v_range;--設定初始抽樣範圍迄
      v_sampeNumber := to_number(v_samplevolume);--設定初始抽樣個數
      v_sampleNumberTotal := 0;--設定初始抽樣總數
      loop
        exit when v_sampleTime = 0;
        v_sampleTime := v_sampleTime - 1;
        --計算抽樣件數  不可超過 每月限制抽樣件數(總抽查數)
        if( (to_number(v_limitamount) - (v_sampeNumber + v_sampleNumberTotal) ) < 0 ) then
          v_sampeNumber := (to_number(v_limitamount) - v_sampleNumberTotal);
        end if;
        exit when v_sampeNumber = 0;
        --SQL 將暫存檔資料搬到 月核定抽樣查核檔  先用抽樣範圍 抓取暫存檔資料  再用亂數排序  再取前面抽樣個數筆資料 在INSERT到 月核定抽樣查核檔
        v_sqlStmt := ' insert into BAINSPECTLIST (inspectdate,paycode,issuym,apno,appdate,casetyp,evtname,' ||
                      ' paybankid,branchid,payeeacc,payyms,payyme,aplpayamt,issueamt)' ||
                      ' select * from ('||
                      ' select ''' || to_Char(SYSDate,'YYYYMMDD') || ''' as inspectdate , '''|| p_payType || ''' ,' ||
                      ' issuym,apno,appdate,casetyp,evtname,paybankid,' ||
                      ' branchid,payeeacc,payyms,payyme,aplpayamt,issueamt' ||
                      ' from (select RowNum Row_Count,t.* from BaCm_Sampling t) ' ||
                      ' where Row_Count >= ' || v_rageS ||
                      ' and Row_Count <= ' || v_rageE ||
                      ' order by apno desc )' ||
                      ' where rownum <= ' || v_sampeNumber ||
                      ' order by dbms_random.random';
         dbms_output.put_line(v_sqlStmt);
         execute immediate v_sqlStmt;

         v_rageS := v_rageS + v_range;--根據抽樣間隔數 計算抽樣範圍起
         v_rageE := v_rageE + v_range;--根據抽樣間隔數 計算抽樣範圍迄
         v_sampleNumberTotal := v_sampleNumberTotal + v_sampeNumber;--計算抽樣總件數
         dbms_output.put_line(v_sampleTime);
      end loop;
       commit;
    end p_sampling;

   -- ----------------------------------------------------------------------------
    -- FUNCTION AREA
    -------------------------------------------------------------------------------
    --DROP暫存檔
    PROCEDURE p_dropTempTable is
    begin
     v_sqlStmt := ' truncate table BaCm_Sampling ';
     execute immediate v_sqlStmt ;
     v_sqlStmt := ' drop table BaCm_Sampling ';
     execute immediate v_sqlStmt;
    end p_dropTempTable;
    -- ----------------------------------------------------------------------------
    -- FUNCTION AREA
    -------------------------------------------------------------------------------
    --根據先抽對象條件參數檔建立暫存檔
    PROCEDURE p_createTempTable is
    begin
      if(v_andortyp = '0')then
      v_andor := 'AND';
      elsif(v_andortyp = '1')then
      v_andor := 'OR';
      end if;

      v_sqlStmt := ' create global temporary table BaCm_Sampling on commit preserve rows as ' ||
             ' Select A.APPDATE,' ||
             ' A.APNO ,' ||
             ' A.CASETYP,' ||
             ' A.EVTNAME,' ||
             ' A.PAYBANKID,' ||
             ' A.BRANCHID,' ||
             ' A.PAYEEACC,' ||
             ' B.ISSUYM,' ||
             ' B.PAYYMS,' ||
             ' B.PAYYME,' ||
             ' B.APLPAYAMT ,' ||
             ' B.SUMBEFISSUEAMT as issueamt ' ||
        ' From (Select APPDATE,' ||
                     ' APNO,' ||
                     ' CASETYP,' ||
                     ' EVTNAME,' ||
                     ' PAYBANKID,' ||
                     ' BRANCHID,' ||
                     ' PAYEEACC,' ||
                     ' EVTAGE,' ||
                     ' APITEM' ||
                ' From BAAPPBASE' ||
               ' Where APNO like ''' || p_payType || ''' || ''%''' ||
                 ' And SEQNO = ''0000''' ||
                 ' And PROCSTAT = ''50''' ||
                 ' And MANCHKMK = ''Y''' ||
                 ' And (CASEMK <> ''D'' Or CASEMK Is Null)) A,' ||
             ' (Select APNO,' ||
                     ' ISSUYM,' ||
                     ' NITRMY,' ||
                     ' BEFISSUEAMT,' ||
                     ' MIN(PAYYM) PAYYMS,' ||
                     ' MAX(PAYYM) PAYYME,' ||
                     ' SUM(APLPAYAMT) APLPAYAMT,' ||
                     ' SUM(BEFISSUEAMT) SUMBEFISSUEAMT' ||
                ' From BADAPR' ||
               ' Where APNO like ''' || p_payType || ''' || ''%''' ||
                 ' And SEQNO = ''0000''' ||
                 ' And ISSUYM = '''|| p_issuYm || '''' ||
                 ' And MTESTMK = ''F''' ||
                 ' And MANCHKMK = ''Y''' ||
                 ' And APLPAYMK = ''3''' ||
                 ' And Trim(APLPAYDATE) Is Not Null' ||
               ' Group By APNO, ISSUYM, NITRMY, BEFISSUEAMT) B ' ||
       ' Where A.APNO = B.APNO ' ||
         ' And B.ISSUYM = ''' || p_issuYm || '''' ||
         ' and ( (''' || v_binsurseni ||
                   ''' <= B.NITRMY and B.NITRMY <= ''' || v_einsurseni || ''')' ||
                   v_andor || '(''' || v_bissueamt ||
                   ''' <= B.BEFISSUEAMT and B.BEFISSUEAMT <= ''' || v_eissueamt || ''' )' ||
                   v_andor || '( ''' || v_bcondition ||
                   ''' <= EVTAGE and EVTAGE <= ''' || v_econdition || ''')';
      if (trim(v_condition1) is not null) then
        v_sqlStmt := v_sqlStmt || v_andor || ' A.APITEM in (' ||
                     v_condition1 || ')';
      end if;
      v_sqlStmt := v_sqlStmt || ' ) order by apno desc';

      execute immediate v_sqlStmt ;
    end p_createTempTable;
    -- ----------------------------------------------------------------------------
    -- FUNCTION AREA
    -------------------------------------------------------------------------------
    -- 根據輸入的給付別 查詢先抽對象條件參數檔
    PROCEDURE p_Bapainspect is
    begin
      select Binsurseni,
             Einsurseni,
             Bissueamt,
             Eissueamt,
             Bcondition,
             Econdition,
             Condition1,
             Condition2,
             Condition3,
             Condition4,
             Andortyp,
             Range,
             Samplevolume,
             Limitamount,
             Printtyp,
             Enable
        into v_binsurseni,
             v_einsurseni,
             v_bissueamt,
             v_eissueamt,
             v_bcondition,
             v_econdition,
             v_condition1,
             v_condition2,
             v_condition3,
             v_condition4,
             v_andortyp,
             v_range,
             v_samplevolume,
             v_limitamount,
             v_printtyp,
             v_enable
        From BAPAINSPECT
       Where paycode = p_payType;
    end p_Bapainspect;

    -- ============================================================================
    --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
    -------------------------------------------------------------------------------
    begin
    --取得先抽對象條件參數檔資料
      p_Bapainspect;
      if (v_enable = '0') then
        p_resulte := 'Y'; --設定抽查
        p_createTempTable; --建立暫存檔
        p_sampling;--執行抽樣
        p_dropTempTable;--DROP暫存檔
      else
        p_resulte := 'N'; --設定不抽查
      end if;
    exception when others then
      p_resulte := 'N'; --設定不抽查
      --p_dropTempTable;--DROP暫存檔
      dbms_output.put_line(sqlerrm);--顯示錯誤訊息
    end;
    end BaCm_MONTHCHECKSAMPLING;
/

