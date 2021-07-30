CREATE OR REPLACE PACKAGE BA.PG_BANSF_01 authid definer is
	/******************************************************************************
       NAME:       PG_BANSF_01
      -- Purpose : 勞保年金核定檔及勞保年金統計檔轉入主程序
    
       REVISIONS:
       Ver        Date        Author           Description
       ---------  ----------  ---------------  ------------------------------------
       1.0        2012/11/07  Rose Lee         1. Created this package.
    ******************************************************************************/

	PROCEDURE SP_BANSF(S_Insuym       in  varChar2, --處理年月 
                       v_i_bajobid    in  varchar2,
                       p_return_code2 out varChar2, --回傳訊息OK:成功 or NO:失敗
					   v_o_flag       out varChar2
    );

    PROCEDURE SP_BANSF_01(
        P_Payym       In Varchar2, --處理年月
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    );
    
    PROCEDURE SP_BANSF_02(
        P_Insuym      In Varchar2, --處理年月
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    );
    
    PROCEDURE SP_BANSF_04(
        P_Insuym      In Varchar2, --處理年月
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    );
    
    PROCEDURE SP_BANSF_05(
        P_Insuym      In Varchar2, --處理年月
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    );
    
    PROCEDURE UPDATE_NULL(
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    );
                                        
    PROCEDURE SP_UPDATE_BANSF_FROMTMP(
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    );
    
    PROCEDURE SP_UPDATE_PWAGE(
        P_Payym1      In Varchar2, --起始處理年月
        P_Payym2      In Varchar2, --結束處理年月
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    );
    
    PROCEDURE SP_BA_BANSF_GENDATA(
        v_i_issuym    in      varChar2,     
        v_i_mmJobNO   in      varChar2, 
        v_i_bajobid   in      varChar2, 
		v_o_flag      out     varChar2
    );
    
    PROCEDURE SP_BA_BANSF_SUPPLEMENT(
        v_i_issuym    in      varChar2,     
        v_i_mmJobNO   in      varChar2, 
        v_i_bajobid   in      varChar2, 
		v_o_flag      out     varChar2
    ); 
    
    FUNCTION FN_BA_GETFIRSTPAYMK(
        v_i_apno        in      varChar2,
        v_i_aplpaydate  in      varChar2
    )return varChar2;
                                                                                            
END PG_BANSF_01;
/

prompt
prompt Creating package body PG_BANSF_01
prompt =================================
prompt
CREATE OR REPLACE PACKAGE BODY BA.PG_BANSF_01 is
	Procedure SP_BANSF(S_Insuym       in  varChar2, --處理年月 
                       v_i_bajobid    in  varchar2,
                       p_return_code2 out varChar2, --回傳訊息OK:成功 or NO:失敗
					   v_o_flag       out varChar2) is
	BEGIN
		-- ============================================================================
		-- 程式目的:勞保年金核定檔及勞保年金統計檔轉入主程序
		-- 撰寫人員:Rose Lee
		-- 執行方式:由人工啟動
		-- 傳入參數:處理年月      varchar2
		-- 傳出參數:None
		-- 相關 TABLE: None
		-------------------------------------------------------------------------------
		-------------------------------------------------------------------------------
		-- 修改紀錄：
		-- 維護人員        日期    說明
		-- -----------   --------  ----------------------------------------------------
		-- Rose           20121107  v1.0
        -- Angela         20190827  
		-- ============================================================================
		--                          <<<<<<<<< 變數區 >>>>>>>>>>
		-------------------------------------------------------------------------------
		Declare
			P_Return_Code Varchar2(32767 char); --傳回訊息
			--V_Format_Date Date; --DATE格式欄位
			P_Insuym      VARCHAR2(6) := S_Insuym; 
            v_flag        varChar2(1) := '0';
            v_g_mmJobNO   mmjoblog.job_no%type := '';
            v_rowCount    Number := 0 ;

        -- ============================================================================
        --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
        -------------------------------------------------------------------------------
		Begin
            --20190827 Add by Angela RecLog Start
            v_o_flag := 'E';
            begin
                select replace(to_Char(SYSTIMESTAMP, 'YYYYMMDDHH24MISSXFF'),'.','') into v_g_mmJobNO from DUAL;
            exception when others then
                v_o_flag := 'N'; 
                v_g_mmJobNO := '';
            end;

            if v_o_flag = 'E' then
                SP_BA_RecJobLog(p_job_no => v_g_mmJobNO,
                                p_job_id => 'PG_BANSF_01.SP_BANSF',
                                p_step   => '產生勞保年金統計檔資料作業(PG_BANSF_01.SP_BANSF)',
                                p_memo   => '開始時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_g_mmJobNO||')');
                        
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '0',
                                     '(DB)PG_BANSF_01.SP_BANSF：產生勞保年金統計檔資料作業開始',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));       
            end if; 
            --20190827 Add by Angela RecLog End
                    
            /*Begin
				-- 檢查參數內容
				-- 資料年--判斷是否為民國年
				If Length(S_Insuym) <> 5 Then
					RAISE_APPLICATION_ERROR(-20001, '請輸入3碼民國年+2碼月份！');
				End If;
				P_Insuym      := to_Char(S_Insuym + 191100);
				--V_Format_Date := to_Date(P_Insuym || '01', 'YYYYMMDD'); --檢查年月格式是否正確,若不正確會EXCEPTION
				--DBMS_OUTPUT.PUT_LINE(P_Insuym);
			EXCEPTION
				When Others THEN
					v_o_flag := 'N';
                    p_return_code  := 'NO'; --回傳訊息
                    DBMS_OUTPUT.PUT_LINE('輸入參數資料錯誤EXCEPTION！');
                    DBMS_OUTPUT.PUT_LINE('初期處理失敗：錯誤代碼=' || SQLCODE ||'，' || '錯誤訊息=' || SQLERRM || ',' ||Dbms_Utility.Format_Error_Backtrace);
					p_return_code2 := p_return_code2 ||'輸入參數資料錯誤EXCEPTION！';
                    p_return_code2 := p_return_code2 ||'初期處理失敗：錯誤代碼=' || SQLCODE || ' ， ' || '錯誤訊息=' || SQLERRM || ',' || Dbms_Utility.Format_Error_Backtrace || ',';
					Return;
			END;*/ 
            
			case
				when P_Insuym = '201208' then
					execute immediate 'truncate table BADAPR_REF';
					execute immediate 'truncate table BADAPR_REF_ERRL';
					execute immediate 'truncate table BANSF';
					commit;
				when P_Insuym <> '201208' and P_Insuym < '201304' then
					v_o_flag := 'N';
                    p_return_code  := 'NO'; --回傳訊息
					DBMS_OUTPUT.PUT_LINE(P_Insuym||'輸入參數資料錯誤EXCEPTION！，核定年月必須為10108或大於等於10203');
					DBMS_OUTPUT.PUT_LINE('初期處理失敗：錯誤代碼=' || SQLCODE || ' ， ' || '錯誤訊息=' || SQLERRM || ',' ||Dbms_Utility.Format_Error_Backtrace);
					p_return_code2 := p_return_code2 ||'輸入參數資料錯誤EXCEPTION！，核定年月必須為10108或大於等於10203,';
					p_return_code2 := p_return_code2 || '初期處理失敗：錯誤代碼=' ||SQLCODE || ' ， ' || '錯誤訊息=' ||SQLERRM || ',' ||Dbms_Utility.Format_Error_Backtrace || ',';
					Return;
				else
					DBMS_OUTPUT.PUT_LINE('勞保年金核定檔及勞保年金統計檔轉入主程序開始');
					DBMS_OUTPUT.PUT_LINE('1.刪除給付年月資料');
                    p_return_code2 := p_return_code2 ||'勞保年金核定檔及勞保年金統計檔轉入主程序開始,';
					p_return_code2 := p_return_code2 || '1.刪除給付年月資料,';
					/*Delete From bansf where issuym = P_Insuym;
					Delete From badapr_ref where issuym = P_Insuym;*/
					commit;                                        
            end case;
            
            if v_o_flag = 'E' then
                delete from BANSF t where t.ISSUYM = P_Insuym;
                commit;

                execute immediate 'truncate table BADAPR_REF';   
                execute immediate 'truncate table BADAPR_REF_ERRL';
                execute immediate 'truncate table BANSF_3X';
                execute immediate 'truncate table BANSF_REF'; 
                execute immediate 'truncate table BANSF_TEMP'; 
                
                --36案上線後再打開此註解，並下方的 Update 語法註解掉
                --execute immediate 'truncate table BANSF_REF_36';
                --execute immediate 'truncate table BANSF_CPI';
                update BANSF_REF_36 t
                   set t.ISSUYM = deCode(Length(t.ISSUYM),5,to_Char(to_Number(t.ISSUYM)+191100),t.ISSUYM)
                      ,t.PAYYM = deCode(Length(t.PAYYM),5,to_Char(to_Number(t.PAYYM)+191100),t.PAYYM)
                      ,t.APNO = trim(t.APNO);
                update BANSF_CPI t
                   set t.ISSUYM = deCode(Length(t.ISSUYM),5,to_Char(to_Number(t.ISSUYM)+191100),t.ISSUYM)
                      ,t.PAYYM = deCode(Length(t.PAYYM),5,to_Char(to_Number(t.PAYYM)+191100),t.PAYYM)
                      ,t.APNO = trim(t.APNO);
                commit;
                
                begin
                    select count(*) into v_rowCount 
                      from ALL_INDEXES t 
                     where t.OWNER = 'BA' 
                       and t.INDEX_NAME ='BADAPR_IDX_OTHER';
                exception when others then
                    v_rowCount := 0;
                end;
                
                if v_rowCount = 0 then
                    execute immediate 'create index BADAPR_IDX_OTHER on BADAPR (APNO, MTESTMK, SEQNO, APLPAYMK, PAYYM, APLPAYDATE)';
                    commit;                                          
                end if;
                
                SP_BA_BANSF_GENDATA(P_Insuym,v_g_mmJobNO,v_i_bajobid,v_o_flag);
            end if;                                                            
            
            --Dbms_Output.Put_Line ('勞保年金核定檔及勞保年金統計檔資料轉入:'||to_Char(Sysdate,'YYYY/MM/DD HH24:MI:SS'));
			if v_o_flag = 'E' then
                P_Return_Code := 'NO'; --回傳訊息
                PG_BANSF_01.SP_BANSF_01(P_Insuym,v_g_mmJobNO,v_i_bajobid, p_return_code,v_o_flag);
                p_return_code2 := p_return_code2 || P_Return_Code || ',';
    			
                if v_o_flag = 'E' then
                    --DBMS_OUTPUT.put_line ('勞保給付種類4開頭之統計檔轉入:'||to_Char(SYSDATE,'YYYY/MM/DD HH24:MI:SS'));
                    P_Return_Code := 'NO'; --回傳訊息
                    PG_BANSF_01.SP_BANSF_02(P_INSUYM,v_g_mmJobNO,v_i_bajobid, P_RETURN_CODE,v_o_flag);
                    p_return_code2 := p_return_code2 || P_Return_Code || ',';
        			
                    if v_o_flag = 'E' then
                        --DBMS_OUTPUT.put_line ('限制一次金寫入:'||to_Char(SYSDATE,'YYYY/MM/DD HH24:MI:SS'));
                        P_Return_Code := 'NO'; --回傳訊息
                        PG_BANSF_01.SP_BANSF_04(P_INSUYM,v_g_mmJobNO,v_i_bajobid, P_RETURN_CODE,v_o_flag);
                        p_return_code2 := p_return_code2 || P_Return_Code || ',';
            			
                        if v_o_flag = 'E' then
                            --DBMS_OUTPUT.put_line ('後續請領轉檔:'||to_Char(SYSDATE,'YYYY/MM/DD HH24:MI:SS'));
                            P_Return_Code := 'NO'; --回傳訊息
                            PG_BANSF_01.SP_BANSF_05(P_INSUYM,v_g_mmJobNO,v_i_bajobid, P_RETURN_CODE,v_o_flag);
                            p_return_code2 := p_return_code2 || P_Return_Code || ',';
                		    
                            if v_o_flag = 'E' then
                                --Update_Null change payno
                                PG_BANSF_01.UPDATE_NULL(v_g_mmJobNO,v_i_bajobid,P_RETURN_CODE,v_o_flag);
                                p_return_code2 := p_return_code2 || P_Return_Code || ',';
                    		    
                                if v_o_flag = 'E' then
                                    --amy call for 精算 fileds
                                    P_Return_Code := 'NO'; --回傳訊息
                                    PG_BANSF_01.SP_UPDATE_BANSF_FROMTMP(v_g_mmJobNO,v_i_bajobid,P_RETURN_CODE,v_o_flag);
                                    p_return_code2 := p_return_code2 || P_Return_Code || ',';
                        		    
                                    if v_o_flag = 'E' then
                                        --更新平均薪資相關資料
                                        PG_BANSF_01.SP_UPDATE_PWAGE('200902',P_Insuym,v_g_mmJobNO,v_i_bajobid, P_RETURN_CODE,v_o_flag);
                                        p_return_code2 := p_return_code2 || P_RETURN_CODE || ',';
                                        
                                        if v_o_flag = 'E' then
                                            commit;
                                            
                                            --勞保年金統計檔補充資料更新(結案原因、死亡日期、結束請領年月、36案性別欄位、外籍別、大業別)
                                            PG_BANSF_01.SP_BA_BANSF_SUPPLEMENT(P_Insuym,v_g_mmJobNO,v_i_bajobid,v_o_flag);
                                            if v_o_flag = 'E' then
                                                DBMS_OUTPUT.PUT_LINE('勞保年金核定檔及勞保年金統計檔轉入主程序完成！');
                                                p_return_code2 := p_return_code2 || '勞保年金核定檔及勞保年金統計檔轉入主程序完成！,';
                                            end if;
                                        end if; 
                                    end if; 
                                end if; 
                            end if;    
                        end if;    
                    end if;
                end if;
            end if;
            
            --20190827 Add by Angela RecLog Start
            begin
                v_rowCount :=0;
                
                select count(*) into v_rowCount 
                  from ALL_INDEXES t 
                 where t.OWNER = 'BA' 
                   and t.INDEX_NAME ='BADAPR_IDX_OTHER';
            exception when others then
                v_rowCount := 0;
            end;
            
            if v_rowCount > 1 then
                execute immediate 'drop index BADAPR_IDX_OTHER';
                commit;
            end if;
            
            if v_o_flag <> 'E' then
                rollback;
                v_flag := '1';
            end if;   
            
            SP_BA_RecJobLog(p_job_no => v_g_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(PG_BANSF_01.SP_BANSF)',
                            p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_g_mmJobNO||')'||CHR(10)||'執行結果:('||v_flag||')');
                                 
            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 v_flag,
                                 '(DB)PG_BANSF_01.SP_BANSF：產生勞保年金統計檔資料作業結束',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            --20190827 Add by Angela RecLog End

		EXCEPTION
			WHEN OTHERS THEN
				rollback;
                
                DBMS_OUTPUT.PUT_LINE('勞保年金核定檔及勞保年金統計檔轉入主程序失敗！' || SQLCODE ||' - ' || SQLERRM);
				p_return_code2 := p_return_code2 || '勞保年金核定檔及勞保年金統計檔轉入主程序失敗！' || SQLCODE ||' - ' || SQLERRM || ',';
		        
                --20190827 Add by Angela RecLog Start
                begin
                    v_rowCount :=0;
                    
                    select count(*) into v_rowCount 
                      from ALL_INDEXES t 
                     where t.OWNER = 'BA' 
                       and t.INDEX_NAME ='BADAPR_IDX_OTHER';
                exception when others then
                    v_rowCount := 0;
                end;
                
                if v_rowCount > 1 then
                    execute immediate 'drop index BADAPR_IDX_OTHER';
                    commit;
                end if;
                
                SP_BA_RecJobLog(p_job_no => v_g_mmJobNO,
                                p_job_id => 'PG_BANSF_01.SP_BANSF',
                                p_step   => '產生勞保年金統計檔資料作業(PG_BANSF_01.SP_BANSF)',
                                p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_g_mmJobNO||')'||CHR(10)||'執行結果:(1)');

                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '1',
                                     '(DB)PG_BANSF_01.SP_BANSF：產生勞保年金統計檔資料作業結束',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                v_o_flag := 'N';
                --20190827 Add by Angela RecLog End
        End;
	End Sp_BANSF;
    
    PROCEDURE SP_BANSF_01(
        P_Payym       In Varchar2, --處理年月
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    ) Is
    Begin
        -- 修改紀錄：
        -- 維護人員        日期       說明
        -- -----------   --------  ----------------------------------------------------
        -- rose          20120906   v1.0
        -- 供莉瑛執行時所需更動設定如下:
        -- BA. -->ba.
        -- CAUB-->caub
        -- bacipb-->ba.cipb
        -- ============================================================================
        --                          <<<<<<<<< 變數區 >>>>>>>>>>
        -------------------------------------------------------------------------------
        DECLARE
            R_NBAPPBASE_Rec        NBAPPBASE%Rowtype; --NBAPPBASE RECORD
            R_Badapr_Rec           Badapr_Ref%Rowtype; --badapr_ref RECORD
            R_Baappbase_Rec        Baappbase%Rowtype; --BAAPPBASE RECORD
            R_Baappexpand_Rec      Baappexpand%Rowtype; --BAAPPEXPAND RECORD
            R_CIPB_Rec             ba.cipb%Rowtype; --BAAPPEXPAND RECORD
            R_Source_Badapr_Rec    Badapr%Rowtype; --badapr record
            R_Source_Baappbase_Rec Baappbase%Rowtype; --BAAPPBASE RECORD
            R_Caub_Rec             CAUB%Rowtype; --STAT RECORDc
            -- ============================================================================
            sw_found        BOOLEAN := TRUE; --找到資料
            sw_found_badapr BOOLEAN := TRUE; --找到資料
            sw_found_CIPB   BOOLEAN := TRUE;
            -- ============================================================================
            -------------------------------------------------------------------------------
            --V_Bansf_Ref_In_Cnt  Number(10) := 0; --讀取bansf_ref筆數
            --V_Badapr_Ref_In_Cnt Number(10) := 0; --讀取badapr_ref筆數
            V_Out_temp_Cnt      Number(10) := 0; --輸出新增筆數
            V_Out_Cnt           Number(10) := 0; --輸出新增筆數
            v_del_cnt           NUMBER(10) := 0; --刪除筆數
            V_Key_Ubno          Bansf.Ubno%Type;
            V_Key_Baappbaseid   Number(20); --給付主檔資料編號
            V_KEY_APNO          BANSF.APNO%TYPE; --受理編號
            V_KEY_NBAPNO        BANSF.APNO%TYPE; --國保受理編號
            V_Key_Idn           Varchar2(11 Byte); --身份證號
            V_Key_Intyp         varchar(1 byte); --保險別
            v_key_name          Bansf.Evtname%type; --姓名
            v_key_brdate        Varchar2(8 Byte); --生日
            V_Exception_Cnt     Integer := 0; --EXCEPTION錯誤筆數
            --v_format_date       DATE; --DATE格式欄位
            V_Str               Varchar2(3 Byte); --狀態變數
            V_Str2              Varchar2(2 Byte); --狀態變數
            V_Str3              Varchar2(2 Byte); --狀態變數
            v_key_seqno         Varchar2(4 Byte); --序號
            V_BDATE             varchar2(6 byte);
            V_Edate             Varchar2(6 Byte);
            --v_injdp             BCA_D_INJD.INJDP%TYPE;
            V_Age               NUMBER(3);
            V_Payym             Varchar(6 Byte);
            v_iisuym            varchar(6 byte);
            V_Apno              Bansf.Apno%Type;
            V_Samt              Number(12, 2);
            V_Cat               Varchar(1);
            V_Evtdiedate        Baappbase.Evtdiedate%Type;
            v_notfound_cipb_cnt NUMBER(10) := 0; --找不到CIPB筆數
            V_TOOMANY_CIPB_CNT  NUMBER(10) := 0; --找到太多CIPB筆數
            v_other_cipb_cnt    NUMBER(10) := 0; --找到ERR CIPB筆數

            -- ============================================================================
            -- CURSOR C_BANSF_REF
            -------------------------------------------------------------------------------
            --處理年月=201208,則全量資料取入,否則,取回給付年月payym=處理年月P_Payym
            --因excel檔未變造,故先行變造Substr(Ubno,1,3)||Substr(Ubno,7,2)||Substr(Ubno,4,3)
            --Select To_Char(Issuym+191100) As Issuym,To_Char(Payym+191100) As Payym,Apno,Payno,Substr(Ubno,1,3)||Substr(Ubno,7,2)||Substr(Ubno,4,3) As Ubno,Evtbrdate,Evtidnno,Edate,Sex,Evtype,Adwkmk,Case When Firstpay Is Null Then '0' Else Firstpay End As Firstpay,Pamts,Otheramt As Deduct,Paycnt,Evcode,Injno,Injcl,Payday,Case When Suprecmk Is Null Then '1' Else Suprecmk End As Suprecmk,Nachgmk,'0' As Pwage,Null As Appdate,Null As Nitrmy,Null Nitrmm
            --SELECT TO_CHAR(ISSUYM+191100) AS ISSUYM,TO_CHAR(PAYYM+191100) AS PAYYM,APNO,PAYNO,UBNO AS UBNO,EVTBRDATE,EVTIDNNO,EDATE,SEX,EVTYPE,ADWKMK,CASE WHEN FIRSTPAY IS NULL THEN '0' ELSE FIRSTPAY END AS FIRSTPAY,PAMTS,OTHERAMT AS DEDUCT,PAYCNT,EVCODE,INJNO,INJCL,PAYDAY,CASE WHEN SUPRECMK IS NULL THEN '1' ELSE SUPRECMK END AS SUPRECMK,NACHGMK,'0' AS PWAGE,NULL AS APPDATE,NULL AS NITRMY,NULL NITRMM

            CURSOR C_BANSF_REF IS
                select *
                  from (SELECT ISSUYM AS ISSUYM,
                               PAYYM AS PAYYM,
                               NULL AS NBAPNO,
                               UPPER(APNO) AS APNO,
                               PAYNO,
                               UBNO AS UBNO,
                               EVTBRDATE,
                               EVTIDNNO,
                               EDATE,
                               SEX,
                               EVTYPE,
                               ADWKMK,
                               CASE
                                   WHEN FIRSTPAY IS NULL THEN
                                    '0'
                                   ELSE
                                    FIRSTPAY
                               END AS FIRSTPAY,
                               PAMTS,
                               OTHERAMT AS DEDUCT,
                               PAYCNT,
                               EVCODE,
                               INJNO,
                               INJCL,
                               PAYDAY,
                               CASE
                                   WHEN SUPRECMK IS NULL THEN
                                    '1'
                                   ELSE
                                    SUPRECMK
                               END AS SUPRECMK,
                               NACHGMK,
                               '0' AS PWAGE,
                               NULL AS APPDATE,
                               NULL AS NITRMY,
                               NULL NITRMM
                          From Bansf_Ref
                         Where P_Payym = '201208'
                           AND ISSUYM < '201312'
                        UNION ALL
                        SELECT ISSUYM AS ISSUYM,
                               PAYYM AS PAYYM,
                               NULL AS NBAPNO,
                               UPPER(APNO) AS APNO,
                               PAYNO,
                               UBNO AS UBNO,
                               EVTBRDATE,
                               EVTIDNNO,
                               EDATE,
                               SEX,
                               EVTYPE,
                               ADWKMK,
                               CASE
                                   WHEN FIRSTPAY IS NULL THEN
                                    '0'
                                   ELSE
                                    FIRSTPAY
                               END AS FIRSTPAY,
                               PAMTS,
                               OTHERAMT AS DEDUCT,
                               PAYCNT,
                               EVCODE,
                               INJNO,
                               INJCL,
                               PAYDAY,
                               CASE
                                   WHEN SUPRECMK IS NULL THEN
                                    '1'
                                   ELSE
                                    SUPRECMK
                               END AS SUPRECMK,
                               NACHGMK,
                               '0' AS PWAGE,
                               NULL AS APPDATE,
                               NULL AS NITRMY,
                               NULL NITRMM
                          From Bansf_Ref
                         Where P_Payym <> '201208'
                           AND ISSUYM = P_PAYYM
                        UNION ALL
                        Select Issuym As Issuym,
                               Payym As Payym,
                               NBAPNO AS NBAPNO,
                               UPPER(APNO) AS APNO,
                               Payno,
                               Null As Ubno,
                               Evtbrdate,
                               Evtidnno,
                               Null As Edate,
                               SEX As Sex,
                               '3' As Evtype,
                               '1' As Adwkmk,
                               To_Char(Firstpay) As Firstpay,
                               Pamts,
                               Null As Deduct,
                               Null As Paycnt,
                               Null As Evcode,
                               Null As Injno,
                               Null As Injcl,
                               0 As Payday,
                               CASE
                                   WHEN SUPRECMK IS NULL THEN
                                    '1'
                                   ELSE
                                    SUPRECMK
                               END AS SUPRECMK,
                               Null As Nachgmk,
                               Pwage,
                               Appdate,
                               To_Char(Year_Rang) As Nitrmy,
                               To_Char(Mon_Rang) As Nitrmm
                          From Bansf_Ref_36
                         Where P_Payym = '201208'
                           AND ISSUYM < '201312'
                        UNION ALL
                        Select Issuym As Issuym,
                               Payym As Payym,
                               NBAPNO AS NBAPNO,
                               UPPER(APNO) AS APNO,
                               Payno,
                               Null As Ubno,
                               Evtbrdate,
                               Evtidnno,
                               Null As Edate,
                               SEX As Sex,
                               '3' As Evtype,
                               '1' As Adwkmk,
                               To_Char(Firstpay) As Firstpay,
                               Pamts,
                               Null As Deduct,
                               Null As Paycnt,
                               Null As Evcode,
                               Null As Injno,
                               Null As Injcl,
                               0 As Payday,
                               CASE
                                   WHEN SUPRECMK IS NULL THEN
                                    '1'
                                   ELSE
                                    SUPRECMK
                               END AS SUPRECMK,
                               Null As Nachgmk,
                               Pwage,
                               Appdate,
                               To_Char(Year_Rang) As Nitrmy,
                               To_Char(Mon_Rang) As Nitrmm
                          From Bansf_Ref_36
                         Where P_Payym <> '201208'
                           AND ISSUYM = P_PAYYM) A
                 Order By (case
                              when payno = '37' then
                               '1'
                              when payno = '36' then
                               '2'
                              when payno in ('55', '56', '58', '59') then
                               '3'
                              when payno in ('35', '38') then
                               '4'
                              else
                               '5'
                          end),
                          (Case
                              When (Suprecmk is Null or suprecmk = '1') then
                               '0'
                              when Suprecmk = 'C' Then
                               '1'
                              Else
                               '2'
                          End),
                          (case
                              when Nachgmk in ('A', 'B') then
                               '1'
                              else
                               '0'
                          end),
                          Apno,
                          issuym,
                          Payym;

            /*        SELECT TO_CHAR(ISSUYM+191100) AS ISSUYM,TO_CHAR(PAYYM+191100) AS PAYYM,NULL AS NBAPNO,UPPER(APNO) AS APNO,PAYNO,UBNO AS UBNO,EVTBRDATE,EVTIDNNO,EDATE,SEX,EVTYPE,ADWKMK,CASE WHEN FIRSTPAY IS NULL THEN '0' ELSE FIRSTPAY END AS FIRSTPAY,PAMTS,OTHERAMT AS DEDUCT,PAYCNT,EVCODE,INJNO,INJCL,PAYDAY,CASE WHEN SUPRECMK IS NULL THEN '1' ELSE SUPRECMK END AS SUPRECMK,NACHGMK,'0' AS PWAGE,NULL AS APPDATE,NULL AS NITRMY,NULL NITRMM
                   From Bansf_Ref  Where (P_Payym='201208' Or Payym=Lpad(Substr(P_Payym,1,4)-1911,3,'0')||Substr(P_Payym,5,2)) --and apno='S00000000626' --AND payno like '3%' AND ROWNUM<20 -- and (apno='K00000000772' or apno='S00000003837') -- and payno='55' --and issuym='09805'and nachgmk is null and apno='S00000006652' --nachgmk<>'A' --and apno in ('S00000000185','S00000006652') --and payym in ('09807','09808')  -- and (apno='K00000000772' or apno='S00000003837') -- AND payym='10102'or  APNO='K00000000373' --and payno='35' AND APNO='K00000000677'; --and payno='55' -- And Apno='S00000000122' --'K00000000290' --S00000000011' --and ubno='01000273'-- -- AND  (Nachgmk='A' Or Suprecmk In ('C','D'))
                   UNION ALL
                   Select To_Char(Issuym+191100) As Issuym,To_Char(Payym+191100) As Payym,NBAPNO AS NBAPNO,UPPER(APNO) AS APNO,Payno,Null As Ubno,Evtbrdate,Evtidnno,Null As Edate,NULL As Sex,'3' As Evtype,'1' As Adwkmk,To_Char(Firstpay) As Firstpay,Pamts,Null As Deduct,Null As Paycnt,Null As Evcode,Null As Injno,Null As Injcl,0 As Payday,Null As Suprecmk,Null As Nachgmk,Pwage,Appdate,To_Char(Year_Rang) As Nitrmy,To_Char(Mon_Rang) As Nitrmm
                   From Bansf_Ref_36  Where (P_Payym='201208' Or Payym=Lpad(Substr(P_Payym,1,4)-1911,3,'0')||Substr(P_Payym,5,2)) --AND APNO='K20000000001' --And Apno='S00000000046' --'K00000000290' --S00000000011' --and ubno='01000273' --  -- AND  (Nachgmk='A' Or Suprecmk In ('C','D'))
                   Order By Apno,issuym,Payym,Suprecmk ;   --S00000001353
            */
            --依受理編號Apno、給付年月Payym及處理註記Mtestmk='F'等,讀取Badapr資料
            CURSOR C_BADAPR_SUPRECMK(S_KEY_APNO IN VARCHAR2,
                                     S_PAYYM    IN VARCHAR2,
                                     S_SUPRECMK IN VARCHAR2,
                                     S_PAYNO    IN VARCHAR2) IS
                select *
                  from (SELECT *
                          FROM BADAPR
                         WHERE APNO = S_KEY_APNO
                           AND PAYYM = S_PAYYM
                           And Mtestmk = 'F'
                           And Aplpaymk = '3'
                           AND SEQNO = '0000'
                           and ((S_SUPRECMK is null and Suprecmk is null) or
                               Suprecmk =
                               decode(S_SUPRECMK, 'K', 'D', S_SUPRECMK))
                           and paykind = S_PAYNO
                         Order By (Case
                                      When 'C' Is Not Null And Suprecmk = 'C' Then
                                       '1'
                                      Else
                                       '2'
                                  End),
                                  Apno,
                                  issuym desc,
                                  Payym,
                                  Seqno) A
                 where rownum < 2;

            --依受理編號Apno、給付年月Payym,baappbase
            Cursor C_baappbase(s_key_apno In Varchar2) Is
                SELECT *
                  From Baappbase
                 Where Apno = S_Key_Apno
                   AND SEQNO = '0000'
                 Order By Apno, Payym, Seqno;

            -- ============================================================================
            -- procedure
            -------------------------------------------------------------------------------
            -- 輸出結果
            PROCEDURE P_OUTPUT_MESSAGE IS
            BEGIN
                Dbms_Output.Put_Line('異常cipb資料=' ||
                                     to_char(v_notfound_cipb_cnt +
                                             v_toomany_cipb_cnt +
                                             v_other_cipb_cnt));
                p_return_code := p_return_code || '異常cipb資料=' ||
                                 to_char(v_notfound_cipb_cnt +
                                         v_toomany_cipb_cnt + v_other_cipb_cnt) || ',';
                Dbms_Output.Put_Line('新增bansf筆數=' || V_Out_Cnt);
                p_return_code := p_return_code || '新增bansf筆數=' || V_Out_Cnt || ',';
            End;
            -------------------------------------------------------------------------------
            -- 刪除計費年月資料
            -------------------------------------------------------------------------------
            PROCEDURE p_delete_data IS
            Begin
                --刪除BANSF、BADAPR_REF、BC_CMST、BC_CDTL處理當月資料
                Delete From bansf
                 WHERE (P_Payym = '201208' or payym = P_Payym)
                   and substr(payno, 1, 1) <> '4';
                Delete From Badapr_Ref
                 WHERE (P_PAYYM = '201208' OR PAYYM = P_PAYYM)
                   AND SUBSTR(PAYNO, 1, 1) <> '4';
                commit;
                V_Del_Cnt := 0; --刪除筆數
                v_del_cnt := v_del_cnt + SQL%ROWCOUNT; --刪除筆數
            EXCEPTION
                WHEN OTHERS THEN
                    v_exception_cnt := v_exception_cnt + 1;
                    DBMS_OUTPUT.put_line('p_delete_data發生錯誤,錯誤代碼=' || sqlcode || ',' ||
                                         '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code || 'p_delete_data發生錯誤,錯誤代碼=' ||
                                     sqlcode || ',' || '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_01：刪除勞保統計檔(BANSF、BADAPR_REF)(以處理年月為條件)發生錯誤***(E01)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;
            -------------------------------------------------------
            --f_typeno
            FUNCTION f_typeno(s_ubtype CAUB.ubtype%TYPE)
                RETURN cia_d_ubty.typeno%TYPE IS
                RESULT cia_d_ubty.typeno%TYPE;
            BEGIN
                SELECT distinct no
                  INTO RESULT
                  From Cia_D_Ubty X
                 where typeno = s_ubtype
                   and rownum < 2;
                RETURN RESULT;
            EXCEPTION
                WHEN no_data_found THEN
                    RETURN '09';
                WHEN OTHERS THEN
                    RETURN '09';
            End;

            --依受理編號Apno、給付年月ISSUYM,Payym及處理註記Mtestmk='F'等,讀取Badapr資料
            FUNCTION C_BADAPR_SUPRECMK_PAYDATE(S_KEY_APNO IN VARCHAR2,
                                               S_ISSUYM   IN VARCHAR2,
                                               S_PAYYM    IN VARCHAR2,
                                               S_SUPRECMK IN VARCHAR2,
                                               S_PAYNO    IN VARCHAR2,
                                               S_PAYDATE  in varchar2)
                RETURN BADAPR.aplpaydate%TYPE IS
                RESULT BADAPR.aplpaydate%TYPE;
            BEGIN
                if substr(S_KEY_APNO,1,1) = 'S' then
                    select aplpaydate
                      INTO RESULT
                      from (SELECT aplpaydate
                              FROM BADAPR
                             WHERE APNO = S_KEY_APNO
                               AND PAYYM = S_PAYYM
                               AND ISSUYM = S_ISSUYM
                               And Mtestmk = 'F'
                               And Aplpaymk = '3'
                               and ((S_SUPRECMK is null and Suprecmk is null) or
                                   Suprecmk =
                                   decode(S_SUPRECMK, 'K', 'D', S_SUPRECMK))
                               and paykind = S_PAYNO
                             Order By (Case
                                          When 'C' Is Not Null And Suprecmk = 'C' Then
                                           '1'
                                          Else
                                           '2'
                                      End),
                                      Apno,
                                      issuym desc,
                                      Payym,
                                      Seqno) A
                     where rownum < 2;
                else
                    select aplpaydate
                      INTO RESULT
                      from (SELECT aplpaydate
                              FROM BADAPR
                             WHERE APNO = S_KEY_APNO
                               AND PAYYM = S_PAYYM
                               AND ISSUYM = S_ISSUYM
                               And Mtestmk = 'F'
                               And Aplpaymk = '3'
                               AND SEQNO = '0000'
                               and ((S_SUPRECMK is null and Suprecmk is null) or
                                   Suprecmk =
                                   decode(S_SUPRECMK, 'K', 'D', S_SUPRECMK))
                               and paykind = S_PAYNO
                             Order By (Case
                                          When 'C' Is Not Null And Suprecmk = 'C' Then
                                           '1'
                                          Else
                                           '2'
                                      End),
                                      Apno,
                                      issuym desc,
                                      Payym,
                                      Seqno) A
                     where rownum < 2;
                end if;
                RETURN RESULT;
            EXCEPTION
                WHEN no_data_found THEN
                    RETURN S_PAYDATE;
                WHEN OTHERS THEN
                    RETURN S_PAYDATE;
            End;

            ------------------------------------------------------
            --f_typeno
            FUNCTION f_bansfcount(s_apno     bansf.apno%TYPE,
                                  s_payym    bansf.payym%TYPE,
                                  s_suprecmk bansf.code%type) RETURN number IS
                RESULT number;
            BEGIN

                SELECT count(*)
                  INTO RESULT
                  From bansf X
                 where apno = s_apno
                   and payym = s_payym
                   and (s_suprecmk is null or code = s_suprecmk)
                   and rownum < 2;
                -- DBMS_OUTPUT.put_line('1:'||s_apno||';'||RESULT);

                RETURN RESULT;
            EXCEPTION
                WHEN no_data_found THEN
                    RETURN '0';
                WHEN OTHERS THEN
                    RETURN '0';
            End;
            -------------------------------------------------------
            --取回C_BADAPR_Suprecmk總數
            Function F_Count(S_Key_Apno In Varchar2, S_Payym In Varchar2)
                Return INT Is
                RESULT INT;
            Begin
                SELECT COUNT(*)
                  Into Result
                  From Badapr
                 Where Apno = S_Key_Apno
                   And Payym = S_Payym
                   and Mtestmk = 'F'
                   AND Aplpaymk = '3';
                RETURN RESULT;
            EXCEPTION
                When No_Data_Found Then
                    RETURN 0;
                When Others Then
                    RETURN 0;
            End;
            ------------------------------------------------------------
            --取回INJDP障礙(失能部位)
            function F_injdp(S_key_INJNO In Varchar2) Return BCA_D_INJD.INJDP%TYPE Is
                RESULT BCA_D_INJD.INJDP%TYPE;
            Begin
                /*條件:INSKD  保險別='1' and INJNO  障礙項目(分左右)代碼=INJNO
                payym給付年月介於EFDTE生效日及失效日之間*/
                SELECT INJDP
                  Into Result
                  From Bca_D_Injd
                 Where INSKD = '1'
                   and INJNO = S_key_INJNO
                   And P_Payym || '01' >= EFDTE
                   and P_Payym || '01' <= EXDTE
                   AND ROWNUM < 2;
                RETURN RESULT;
            EXCEPTION
                When No_Data_Found Then
                    RETURN 0;
                When Others Then
                    RETURN 0;
            End;
            ------------------------------------------------------------
            --判斷同APNO及給付年月,無存在普職註記之資料
            function f_data(S_Key_Apno In Varchar2, S_Payym In Varchar2) Return INT Is
                RESULT INT;
            Begin
                SELECT COUNT(*)
                  Into Result
                  From bansf_ref
                 Where Apno = S_Key_Apno
                   And Payym = S_Payym
                   and Suprecmk is null;
                --   Dbms_Output.Put_Line ('found:'||S_Key_Apno||';'||S_Payym||';'||RESULT);
                RETURN RESULT;
            EXCEPTION
                When No_Data_Found Then
                    RETURN 0;
                When Others Then
                    RETURN 0;
            End;
            -------------------------------------------------------
            --取回v_bdate開始請領日期
            Function F_bdate(S_Key_Apno In Varchar2,
                             s_seqno    in varchar2,
                             S_Payym    In Varchar2,
                             s_payno    in varchar2) Return BADAPR.PAYYM%type Is
                Result Badapr.Payym%Type;
            Begin
                If S_Payno = '36' Then
                    Select substr(Min(C.Payym), 1, 6)
                      Into Result
                      From Bansf_Ref_36 C
                     Where C.Apno = S_Key_Apno
                       And Firstpay = '1' 
                       And C.Payym <= S_Payym;
                       /*And C.Payym <= Lpad(Substr(S_Payym, 1, 4) - 1911, 3, '0') ||
                           Substr(S_Payym, 5, 2);*/
                           
                    /*If Result Is Not Null Then
                        Result := Substr(Result, 1, 3) + 1911 ||
                                  Substr(Result, 4, 2);
                    end if;*/
                ELSE
                    Select substr(Min(C.Payym), 1, 6)
                      INTO RESULT
                      FROM BADAPR C
                     WHERE C.APNO = S_Key_Apno
                       AND C.PAYYM <= S_Payym
                       And C.Mtestmk = 'F'
                       And C.Aplpaymk = '3'
                       And Trim(C.Aplpaydate) Is Not Null
                       AND C.SEQNO = s_seqno;
                End If;
                --Dbms_Output.Put_Line(S_Key_Apno||';'||S_Payym||';'||s_payno||';'||Result);

                return Result;
            EXCEPTION
                When No_Data_Found Then
                    RETURN null;
                When Others Then
                    RETURN null;
            End;
            -------------------------------------------------------
            --取回v_edate結束日期
            Function F_edate(S_Key_Apno In Varchar2,/*
                             s_seqno    in varchar2,
                             S_Payym    In Varchar2,*/
                             s_payno    in varchar2) Return BADAPR.PAYYM%type Is
                Result Badapr.Payym%Type;
            Begin
                If S_Payno = '36' Then
                    Select substr(max(C.Payym), 1, 6)
                      Into Result
                      From Bansf_Ref_36 C
                     Where C.Apno = S_Key_Apno;
                     
                    /*If Result Is Not Null Then
                        Result := Substr(Result, 1, 3) + 1911 ||
                                  Substr(Result, 4, 2);
                    end if;*/
                ELSE
                    Select substr(max(C.Payym), 1, 6)
                      INTO RESULT
                      FROM BADAPR C
                     WHERE C.APNO = S_Key_Apno
                       And C.Mtestmk = 'F'
                       And C.Aplpaymk = '3'
                       And Trim(C.Aplpaydate) Is Not Null
                       AND c.suprecmk is null
                    /*AND C.seqno='0000'*/
                    ;
                End If;
                --Dbms_Output.Put_Line(S_Key_Apno||';'||S_Payym||';'||s_payno||';'||Result);

                return Result;
            EXCEPTION
                When No_Data_Found Then
                    RETURN null;
                When Others Then
                    RETURN null;
            End;
            -------------------------------------------------------
            --取回月份相減
            Function F_Year(S_Ym_B In Varchar2, S_Ym_E In Varchar2) Return Number Is
            Begin
                If S_Ym_B Is Not Null And S_Ym_E Is Not Null Then
                    If Substr(S_Ym_E, 5, 2) > Substr(S_Ym_B, 5, 2) Then
                        Return to_number(Substr(S_Ym_b, 1, 4) -
                                         Substr(S_Ym_E, 1, 4) - 1);
                    Else
                        Return To_Number(Substr(S_Ym_B, 1, 4) -
                                         Substr(S_Ym_E, 1, 4));
                    end if;
                Else
                    return 0;
                End If;
            EXCEPTION
                When No_Data_Found Then
                    RETURN 0;
                When Others Then
                    Return 0;
            End;
            -------------------------------------------------------------------------------
            --讀取CAUB
            PROCEDURE p_read_caub IS
            BEGIN
                Sw_Found := True;
                Select *
                  Into R_Caub_Rec
                  From CAUB
                 Where Ubno = V_Key_Ubno
                   And Rownum < 2;
                --保險證號
            Exception
                WHEN NO_DATA_FOUND THEN
                    r_caub_rec := null;
                    sw_found   := FALSE;
                WHEN OTHERS THEN
                    Sw_Found := False;
                    Dbms_Output.Put_Line('p_read_caub發生錯誤,UBNO=' || V_Key_Ubno ||
                                         ',錯誤代碼=' || Sqlcode || ',' || '錯誤訊息=' ||
                                         Sqlerrm || ',' ||
                                         DBMS_UTILITY.format_error_backtrace);
                    p_return_code := p_return_code || 'p_read_caub發生錯誤,UBNO=' ||
                                     V_Key_Ubno || ',錯誤代碼=' || Sqlcode || ',' ||
                                     '錯誤訊息=' || Sqlerrm || ',' ||
                                     DBMS_UTILITY.format_error_backtrace || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_01：讀取CAUB發生錯誤，UBNO='||V_Key_Ubno||'***(E02)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            End;
            -------------------------------------------------------------------------------
            -- 顯示LOG內容
            PROCEDURE log_msg(p_msg IN VARCHAR2) IS
            BEGIN
                IF v_exception_cnt > 100 THEN
                    DBMS_OUTPUT.put_line('EXCEPTION錯誤超過100個！');
                    p_return_code := p_return_code || 'EXCEPTION錯誤超過100個！' || ',';
                    raise_application_error(-20001, 'EXCEPTION錯誤超過100個！');
                END IF;

                DBMS_OUTPUT.put_line(p_msg);
                p_return_code := p_return_code || p_msg || ',';
            End;
            -------------------------------------------------------------------------------
            -- 依身份證號Idno讀取CIPB資料
            /*   PROCEDURE p_read_cipb
              is
              Begin
                 Select *
                 Into R_Cipb_Rec
                 From   dss.CIPB
                 WHERE  intyp=v_key_intyp and idn like v_key_idn||'%' and brdte=v_key_brdate and name= v_key_name  and rownum<2;
              Exception
                When No_Data_Found
                Then
                  R_Cipb_Rec:=Null;
                  Sw_Found:=False;
                When Others Then
                  R_Cipb_Rec:=Null;
                  Sw_Found:=False;
                  log_msg('p_read_cipb發生錯誤,idn='||v_key_idn||',錯誤代碼='||sqlcode||','||'錯誤訊息='||SQLERRM);
              End ;
            */

            PROCEDURE p_read_cipb is
            Begin
                SELECT *
                  INTO r_cipb_rec
                  FROM ba.cipb B
                 WHERE B.intyp = V_KEY_INTYP
                   AND B.IDN LIKE V_KEY_IDN || '%'
                   AND B.BRDTE = v_key_brdate;
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    V_NOTFOUND_CIPB_CNT := V_NOTFOUND_CIPB_CNT + 1;
                    R_CIPB_REC          := NULL;
                    SW_FOUND_CIPB       := FALSE;
                WHEN TOO_MANY_ROWS THEN
                    --以 身分證,出生,姓名 讀CIPB取CIID
                    BEGIN
                        SELECT *
                          INTO R_CIPB_REC
                          FROM ba.cipb B
                         WHERE B.intyp = v_key_intyp
                           AND B.IDN like v_key_idn || '%'
                           AND B.BRDTE = v_key_brdate
                           AND B.NAME = v_key_name;
                    EXCEPTION
                        WHEN NO_DATA_FOUND THEN
                            v_notfound_cipb_cnt := v_notfound_cipb_cnt + 1;
                        WHEN TOO_MANY_ROWS THEN
                            v_toomany_cipb_cnt := v_toomany_cipb_cnt + 1;
                        WHEN OTHERS THEN
                            v_other_cipb_cnt := v_other_cipb_cnt + 1;
                    END;
                WHEN OTHERS THEN
                    V_OTHER_CIPB_CNT := V_OTHER_CIPB_CNT + 1;
                    R_Cipb_Rec       := Null;
                    Sw_Found_CIPB    := False;
                    LOG_MSG('p_read_cipb發生錯誤,idn=' || V_KEY_IDN || ',錯誤代碼=' ||
                            SQLCODE || ',' || '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code || 'p_read_cipb發生錯誤,idn=' ||
                                     V_KEY_IDN || ',錯誤代碼=' || SQLCODE || ',' ||
                                     '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_01：讀取BA.CIPB發生錯誤，IDN='||V_KEY_IDN||'***(E03)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;
            -------------------------------------------------------------------------------
            -- 依受理編號Apno及序號Seqno取得BAAPPBASE資料
            PROCEDURE p_read_Nbaappbase IS
            BEGIN
                sw_found := TRUE;
                
                SELECT T2.*
                  INTO R_NBAPPBASE_REC
                  FROM BAAPPBASE T1, NBAPPBASE T2
                 WHERE T1.APNO = v_key_apno
                   AND T1.MAPNO = T2.APNO
                   AND T1.PAYKIND = '36'
                   AND T1.EVTIDNNO = v_key_idn
                   AND T2.SEQNO = '0000'
                   AND T2.PAYDT IS NOT NULL
                   AND T2.APNO IN (SELECT T3.APNO 
                                     FROM NBDAPR T3 
                                    WHERE T2.APNO = T3.APNO
                                      AND T3.CHKLIST LIKE '%C4%');
                   
                /*SELECT DISTINCT *
                  INTO R_NBAPPBASE_REC
                  FROM NBAPPBASE A
                 WHERE APNO = V_KEY_NBAPNO
                   AND SEQNO = '0000'
                   AND PAYDT IS NOT NULL;*/
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    sw_found := FALSE;
                WHEN OTHERS THEN
                    SW_FOUND := FALSE;
                    log_msg('p_read_nbaappbase發生錯誤,APNO=' || v_key_apno ||
                            ',錯誤代碼=' || sqlcode || ',' || '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code ||
                                     'p_read_nbaappbase發生錯誤,APNO=' ||
                                     v_key_apno || ',錯誤代碼=' || sqlcode || ',' ||
                                     '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_01：讀取國保給付主檔(NBAPPBASE)發生錯誤，BAAPPBASE.APNO='||v_key_apno||'**(E04)*',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;

            -------------------------------------------------------------------------------
            -- 依受理編號Apno及序號Seqno取得BAAPPBASE資料
            PROCEDURE p_read_baappbase IS
            BEGIN
                sw_found := TRUE;

                   --DBMS_OUTPUT.put_line('2_1:'||v_key_apno||';');
                --SELECT distinct *
                SELECT *
                  INTO r_baappbase_rec
                  FROM BAAPPBASE A
                 WHERE apno = v_key_apno
                   and seqno = v_key_seqno;
                  --DBMS_OUTPUT.put_line('2:'||v_key_apno||';');

            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    sw_found := FALSE;
                WHEN OTHERS THEN
                    sw_found := FALSE;
                    --  log_msg('p_read_baappbase發生錯誤,APNO='||v_key_apno||',錯誤代碼='||sqlcode||','||'錯誤訊息='||SQLERRM);

                --20190827 Add by Angela RecLog Start
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '1',
                                     '(DB)SP_BANSF_01：讀取勞保給付主檔(BAAPPBASE)發生錯誤，APNO='||v_key_apno||'，受款人序：'||v_key_seqno||'***(E04)',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                --20190827 Add by Angela RecLog End;
            End;

            -- 依給付主檔資料編號Baappbaseid取得BAAPPBASE資料
            PROCEDURE p_read_baappbase_D IS
            BEGIN
                sw_found := TRUE;

                --SELECT distinct *
                SELECT *
                  INTO r_baappbase_rec
                  FROM BAAPPBASE A
                 WHERE A.baappbaseid = v_key_baappbaseid;
                --apno = v_key_apno and seqno='0000';
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    sw_found := FALSE;
                WHEN OTHERS THEN
                    sw_found        := FALSE;
                    v_exception_cnt := v_exception_cnt + 1;
                    log_msg('p_read_baappbase發生錯誤,APNO=' || v_key_apno ||
                            ',錯誤代碼=' || sqlcode || ',' || '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code ||
                                     'p_read_baappbase發生錯誤,APNO=' || v_key_apno ||
                                     ',錯誤代碼=' || sqlcode || ',' || '錯誤訊息=' ||
                                     SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_01：讀取勞保給付主檔(BAAPPBASE)發生錯誤，APNO='||v_key_apno||'，受款人序='||v_key_seqno||'***(E05)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            End;
            -------------------------------------------------------------------------------
            -- 依給付主檔資料編號Baappbaseid取得BAAPPEXPAND資料
            PROCEDURE p_read_baappexpand IS
            BEGIN
                sw_found := TRUE;

                --SELECT distinct *
                SELECT *
                  INTO r_baappexpand_rec
                  FROM baappexpand
                 WHERE BAAPPBASEID = v_key_baappbaseid; --給付主檔資料編號
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    sw_found := FALSE;
                WHEN OTHERS THEN
                    sw_found        := FALSE;
                    v_exception_cnt := v_exception_cnt + 1;
                    log_msg('p_read_baappexpand發生錯誤,BAAPPBASEID=' ||
                            v_key_baappbaseid || ',錯誤代碼=' || sqlcode || ',' ||
                            '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code ||
                                     'p_read_baappexpand發生錯誤,BAAPPBASEID=' ||
                                     v_key_baappbaseid || ',錯誤代碼=' || sqlcode || ',' ||
                                     '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_01：讀取勞保給付延伸主檔(BAAPPEXPAND)發生錯誤，BAAPPBASEID='||v_key_baappbaseid||'***(E06)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            End;
            -------------------------------------------------------------------------------
            --設定年齡組別
            Function F_Agetype(S_Age In Number) Return bansf.agetype%Type Is
                RESULT bansf.agetype%TYPE;
            Begin
                Case
                    When S_Age < 15 Then
                        Result := '01';
                    When S_Age >= 15 And S_Age <= 19 Then
                        Result := '02';
                    When S_Age >= 20 And S_Age <= 24 Then
                        Result := '03';
                    When S_Age >= 25 And S_Age <= 29 Then
                        Result := '04';
                    When S_Age >= 30 And S_Age <= 34 Then
                        Result := '05';
                    When S_Age >= 35 And S_Age <= 39 Then
                        Result := '06';
                    When S_Age >= 40 And S_Age <= 44 Then
                        Result := '07';
                    When S_Age >= 45 And S_Age <= 49 Then
                        Result := '08';
                    When S_Age >= 50 And S_Age <= 54 Then
                        Result := '09';
                    When S_Age >= 55 And S_Age <= 59 Then
                        Result := '10';
                    When S_Age >= 60 And S_Age <= 64 Then
                        Result := '11';
                    When S_Age >= 65 And S_Age <= 69 Then
                        Result := '12';
                    When S_Age >= 70 And S_Age <= 74 Then
                        Result := '13';
                    When S_Age >= 75 And S_Age <= 79 Then
                        Result := '14';
                    When S_Age >= 80 And S_Age <= 84 Then
                        Result := '15';
                    When S_Age >= 85 And S_Age <= 89 Then
                        Result := '16';
                    Else
                        Result := '17';
                end case;
                Return Result;
            End;
            -------------------------------------------------------------------------------
            PROCEDURE update_data IS
            Begin
                --取得主檔資料
                R_Badapr_Rec.Evtidnno  := R_Baappbase_Rec.Evtidnno; --'事故者身份證號';
                R_Badapr_Rec.Evtbrdate := R_Baappbase_Rec.Evtbrdate; --'事故者出生日期';
                R_Badapr_Rec.Evtname   := R_Baappbase_Rec.Evtname; --'事故者姓名';
                R_Badapr_Rec.Appdate   := R_Baappbase_Rec.Appdate; --'申請日期';
                R_Badapr_Rec.Edate     := R_Baappbase_Rec.Evtjobdate; --事故日期;
                /*BAAPPBASE.APITEM(申請項目)(payno='5X')
                4-遺屬年金加喪葬津貼=>'1'
                5-遺屬年金=>'1'
                7,8-領取失能年金期間死亡之遺屬年金=>'2'
                9-年資15年且符合老年一次給付之遺屬年金=>'1'*/
                Case
                    When R_Baappbase_Rec.Apitem = '4' Then
                        V_Str3 := '1';
                    When R_Baappbase_Rec.Apitem = '5' Then
                        V_Str3 := '1';
                    When R_Baappbase_Rec.Apitem = '7' Then
                        v_str3 := '2';
                    When R_Baappbase_Rec.Apitem = '8' Then
                        V_Str3 := '3';
                    When R_Baappbase_Rec.Apitem = '9' Then
                        V_Str3 := '4';
                    Else
                        V_Str3 := Null;
                end case;

                If R_Badapr_Rec.Paykind <> 'S' Then
                    R_Badapr_Rec.Age := R_Baappbase_Rec.Evtage; --'申請年月-事故者出生年月=申請年齡';     20130320
                    if (R_Badapr_Rec.Age is null or R_Badapr_Rec.Age = 0) then
                        R_Badapr_Rec.Age := F_Year(substr(R_Baappbase_Rec.appdate,
                                                          1,
                                                          6),
                                                   substr(R_Baappbase_Rec.Evtbrdate,
                                                          1,
                                                          6));
                    end if;
                end if;
                If R_Badapr_Rec.Paykind = 'S' Then
                    --遺屬年金：計算至被保險人死亡年月，以死亡年月-出生年月,取年   20130320
                    V_Age            := F_Year(substr(R_Baappbase_Rec.Evtdiedate,
                                                      1,
                                                      6),
                                               substr(R_Baappbase_Rec.Evtbrdate,
                                                      1,
                                                      6)); --'核付年齡';
                    R_Badapr_Rec.Age := V_Age;  
                Else
                    --失能及老年年金：計算至核付年月，以核付年月－出生年月,取年
                    V_Age := F_Year(Substr(R_Badapr_Rec.Paydate, 1, 6),
                                    Substr(R_Baappbase_Rec.Evtbrdate, 1, 6)); --'核付年齡';
                 
                End If;
                V_Str2       := F_Agetype(V_Age); --年齡組別
                v_Evtdiedate := R_Baappbase_Rec.Evtdiedate;

                V_Bdate := F_Bdate(R_Badapr_Rec.Apno,
                                   R_Badapr_Rec.Seqno,
                                   R_Badapr_Rec.Payym,
                                   R_Badapr_Rec.Payno);

                --20131206精算遺屬需求
                IF R_Baappbase_Rec.Closedate IS NOT NULL THEN
                    v_edate := SUBSTR(R_Baappbase_Rec.Closedate, 1, 6);

                ELSE
                    --20150616 add casetype in ('3','6')
                    If (R_Baappbase_Rec.Casetyp = '4') or
                       ((R_Baappbase_Rec.Casetyp = '3' or
                       R_Baappbase_Rec.Casetyp = '6') and
                       R_Baappbase_Rec.Closedate is not null) Then
                        v_edate := F_edate(R_Badapr_Rec.Apno,/*
                                           R_Badapr_Rec.Seqno,
                                           R_Badapr_Rec.Payym,*/
                                           R_Badapr_Rec.Payno); --2012/11/30 修改原R_Badapr_Rec.Payym;
                    else
                        V_Edate := '00000';
                    End If;
                END IF;   
                
                --'被保險人國籍別';
                If R_Baappbase_Rec.Evtnationtpe = '2' Then
                    R_Badapr_Rec.Evtnationtpe := 'Y';
                Else
                    R_Badapr_Rec.Evtnationtpe := 'C';
                End If;
                --'受益人國籍別';
                If R_Baappbase_Rec.BENNATIONTYP = '2' Then
                    R_Badapr_Rec.BENNATIONTYP := 'Y';
                Else
                    R_Badapr_Rec.BENNATIONTYP := 'C';
                End If;
                R_Badapr_Rec.Apitem     := R_Baappbase_Rec.Apitem; --'申請項目';
                R_Badapr_Rec.Wage       := nvl(R_Baappbase_Rec.Lsinsmamt, 0); --'投保薪資';
                R_Badapr_Rec.Closecause := R_Baappbase_Rec.Closecause; --'結案原因';
                R_Badapr_Rec.Benids     := R_Baappbase_Rec.Benids; --'受益人社福識別碼';
                If R_Baappbase_Rec.APPdate Is Null or
                   R_Baappbase_Rec.Benbrdate Is Null Then
                    R_Badapr_Rec.Benage := 0;
                Else
                    R_Badapr_Rec.Benage := f_year(substr(R_Baappbase_Rec.APPdate,
                                                         1,
                                                         6),
                                                  substr(R_Baappbase_Rec.BENBRDATE,
                                                         1,
                                                         6)); --'申請年月-遺屬出生年月=受益人單齡';
                END IF;
                R_Badapr_Rec.Bensex       := R_Baappbase_Rec.Bensex; --'受益人性別        ';
                R_Badapr_Rec.Bennationtyp := R_Baappbase_Rec.Bennationtyp; --'受益人國籍別      ';
                R_Badapr_Rec.Benevtrel    := R_Baappbase_Rec.Benevtrel; --'受益人與事故者關係';
                /*
                1.失能年金：給付主檔之申請單位保險證號
                2.老年年金：給付主檔之最後單位保險證號
                3.遺屬年金：
                (1)申請項目4、5：給付主檔之申請單位保險證號
                (2)申請項目7、8、9：給付主檔之最後單位保險證號
                */
                Case
                    When R_Badapr_Rec.Paykind = 'S' Then
                        If R_Badapr_Rec.Apitem In ('4', '5') Then
                            R_Badapr_Rec.Ubno := R_Baappbase_Rec.APUBNO;
                        Else
                            R_BADAPR_REC.UBNO := R_BAAPPBASE_REC.LSUBNO;
                        END IF;
                    When R_Badapr_Rec.Paykind = 'L' Then
                        R_Badapr_Rec.Ubno := R_Baappbase_Rec.Lsubno;
                    Else
                        R_Badapr_Rec.Ubno := R_Baappbase_Rec.Apubno;
                END CASE;
                IF R_BADAPR_REC.UBNO IS NULL THEN
                    R_BADAPR_REC.UBNO := R_BAAPPBASE_REC.LSUBNO;
                END IF;
                V_Key_Ubno := R_Badapr_Rec.Ubno;
                --取得CAUB
                P_Read_Caub;
                R_Badapr_Rec.UBTYPE := nvl(R_Caub_Rec.UBTYPE, 'Z9'); --'單位類別';
                R_Badapr_Rec.Inds   := R_Caub_Rec.Inds; --'小業別';
                R_Badapr_Rec.HINCD  := SUBSTR(R_Caub_Rec.HINCD, 1, 2); --'職災代號';
                R_Badapr_Rec.IDSTA  := R_Caub_Rec.IDSTA; --'大業別';
                R_Badapr_Rec.AREA   := R_Caub_Rec.AREA; --'地區別';
                R_Badapr_Rec.CLSQTY := R_Caub_Rec.PRSNO_B; --'月末人數';
                --取得baappexpand
                P_Read_Baappexpand;
                /*
                --抓取狀態不一情形
                If R_Badapr_Rec.Evtype<>R_Baappexpand_Rec.Evtyp Then
                   Log_Msg(R_Badapr_Rec.Apno||';'||R_Badapr_Rec.Evtype||';'||R_Baappexpand_Rec.Evtyp);
                End if;
                */
                if R_Badapr_Rec.Evtype is null then
                    R_Badapr_Rec.Evtype := R_Baappexpand_Rec.Evtyp; --  2013/03/11
                end if;
                if R_Badapr_Rec.Evtype is null then
                    R_Badapr_Rec.Evtype := '3';
                end if;
                R_Badapr_Rec.Evcode  := r_baappexpand_rec.Evcode; --'事故原因';  BAAPPEXPAND.EVCODE
                R_Badapr_Rec.Injname := R_Baappexpand_Rec.Criinjnme1; --'傷病名稱(國際疾病代碼)';
                R_Badapr_Rec.Injno   := R_Baappexpand_Rec.Criinjdp1; --'障礙(失能)項目';
                R_Badapr_Rec.INJdp   := F_injdp(R_Badapr_Rec.Injno); --'障礙(失能部位)';
                If R_Baappexpand_Rec.Criinissul IS NOT NULL Then
                    R_Badapr_Rec.Injcl := R_Baappexpand_Rec.Criinissul; --'失能等級 (身心障礙等級)';  BAAPPEXPAND.CRIINISSUL
                ELSE
                    R_Badapr_Rec.Injcl := R_Baappexpand_Rec.CRIINJCL1; --'失能等級 (身心障礙等級)';  BAAPPEXPAND.CRIINISSUL
                END IF;
                R_Badapr_Rec.Injpart   := TRIM(UPPER(r_baappexpand_rec.criinpart1)); --'受傷部位';
                R_Badapr_Rec.Medium    := R_Baappexpand_Rec.Crimedium; --'媒介物';
                R_Badapr_Rec.DedUCtday := R_Baappexpand_Rec.dedUCtday; --S '扣減日數';
                --If (R_Source_Badapr_Rec.Nachgmk Is Null And R_Bappexpand_Rec.Adwkmk='2') or ( R_Source_Badapr_Rec.Nachgmk In ('13','23')) Then
                --     R_Badapr_Rec.Adwkmk:='+';
                --Else    ---因年金與一次金不同故,先暫存為NULL,待SA確認
                --   R_Badapr_Rec.Adwkmk:=nvl(R_Baappexpand_Rec.adwkmk,'1');

                --END IF;
                --符合離職退保後職災殘廢給付職業病種類(Y/N)
                If R_Baappexpand_Rec.Ocaccidentmk = 'Y' Then
                    R_Badapr_Rec.CHKKIND := 'Y';
                Else
                    R_Badapr_Rec.CHKKIND := 'N';
                End If;
                --20130312
                /*             DBMS_OUTPUT.put_line ('1'||';'||R_Badapr_Rec.ISSUYM||';'||R_Badapr_Rec.payym||';'||R_Badapr_Rec.apno||';'||R_Badapr_Rec.Suprecmk||';'||R_BADAPR_REC.OLDEXTRARATE);

                IF SUBSTR(R_Badapr_Rec.APNO,1,1) IN ('K','S') THEN
                    R_Badapr_Rec.oldrate     :=R_BADAPR_REC.OLDEXTRARATE;
                ELSE
                    R_Badapr_Rec.oldrate     :=0;
                END IF;*/
                /*2014/02/18 L-老年=>OLDRATE一律給0%
                K-失能=>0人0%，1人25%，>=2人50%
                S-遺屬=>0或1人0%，2人25%，>=3人50%
                   */
                if SUBSTR(R_Badapr_Rec.APNO, 1, 1) = 'S' then
                    IF R_Badapr_Rec.qualcount = 0 OR
                       R_Badapr_Rec.qualcount is null or
                       R_Badapr_Rec.Qualcount = 1 THEN
                        R_Badapr_Rec.oldrate := '0'; --加計比率
                    Else
                        If R_Badapr_Rec.Qualcount = 2 Then
                            R_Badapr_Rec.oldrate := '25'; --加計比率
                        ELSE
                            R_Badapr_Rec.oldrate := '50'; --加計比率
                        End If;
                    End If;
                else
                    IF R_Badapr_Rec.qualcount = 0 OR
                       R_Badapr_Rec.qualcount is null THEN
                        R_Badapr_Rec.oldrate := '0'; --加計比率
                    Else
                        If R_Badapr_Rec.Qualcount = 1 Then
                            R_Badapr_Rec.oldrate := '25'; --加計比率
                        ELSE
                            R_Badapr_Rec.oldrate := '50'; --加計比率
                        End If;
                    End If;
                end if; --2014/02/18
                V_Key_Brdate := R_Baappbase_Rec.Evtbrdate;
                V_Key_Name   := R_Baappbase_Rec.Evtname;

            EXCEPTION
                When No_Data_Found THEN
                    sw_found := FALSE;
                WHEN OTHERS THEN
                    sw_found := FALSE;
                    Log_Msg('p_read_baappbase發生錯誤,APNO=' || V_Key_Apno ||
                            ',錯誤代碼=' || Sqlcode || ',' || '錯誤訊息=' || Sqlerrm);
                    p_return_code := p_return_code ||
                                     'p_read_baappbase發生錯誤,APNO=' || V_Key_Apno ||
                                     ',錯誤代碼=' || Sqlcode || ',' || '錯誤訊息=' ||
                                     Sqlerrm || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_01：讀取勞保給付主檔(BAAPPBASE)、給付核定檔(BADAPR)發生錯誤，APNO='||V_Key_Apno||'***(E07)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            End;
            ---------------------------------------------------------------------------------
            -- 新資料轉入
            -------------------------------------------------------------------------------
            PROCEDURE p_badapr_proc Is
            Begin
                --EXECUTE IMMEDIATE 'truncate TABLE BANSF_TEMP';
                DELETE BANSF_TEMP;
                COMMIT;
                V_Payym      := Null;
                V_Iisuym     := Null;
                V_Samt       := 0;
                V_Str3       := Null;
                v_Evtdiedate := null;
                For r_bansf_rec_rec In C_BANSF_REF Loop
                    v_cat                := null;
                    R_Badapr_Rec         := Null;
                    R_Badapr_Rec.Paydate := r_bansf_rec_rec.ISSUYM || '01'; --'核付日期';
                    R_Badapr_Rec.ISSUYM  := r_bansf_rec_rec.ISSUYM; --'核定年月';
                    R_Badapr_Rec.Payym   := R_Bansf_Rec_Rec.Payym; --'給付年月';
                    R_Badapr_Rec.Apno    := R_Bansf_Rec_Rec.Apno; --'受理編號';
                    R_Badapr_Rec.Payno   := R_Bansf_Rec_Rec.Payno; --'給付種類';           BAAPPBASE        PAYKIND
                    R_Badapr_Rec.Paykind := Substr(R_Badapr_Rec.Apno, 1, 1); --'給付別';
                    R_Badapr_Rec.Seqno   := '0000'; --'序號';
                    R_Badapr_Rec.Edate   := Nvl(R_Bansf_Rec_Rec.Edate, Null);
                    --    DBMS_OUTPUT.put_line(R_Badapr_Rec.Apno);
                    if R_Badapr_Rec.Edate is null or nvl(R_Badapr_Rec.Edate,' ') = ' ' then
                        R_Badapr_Rec.Edate := R_Badapr_Rec.Edate;
                    else
                        Begin
                            R_Badapr_Rec.Edate := R_Badapr_Rec.Edate;
                        exception
                            WHEN OTHERS THEN
                                R_Badapr_Rec.Edate := null;
                        End;
                    End If;
                    R_Badapr_Rec.Evtidnno  := R_Bansf_Rec_Rec.Evtidnno; --'事故者身份證號';
                    V_Key_Idn              := R_Bansf_Rec_Rec.Evtidnno;
                    R_BADAPR_REC.EVTBRDATE := NULL;
                    IF R_BANSF_REC_REC.EVTBRDATE IS NOT NULL and
                       length(R_BANSF_REC_REC.EVTBRDATE) < 8 THEN
                        Begin
                            R_Badapr_Rec.Evtbrdate := R_Bansf_Rec_Rec.Evtbrdate; --事故者出生日期
                        EXCEPTION
                            When Others Then
                                INSERT INTO BADAPR_REF_ERRL
                                    SELECT R_BADAPR_REC.APNO,
                                           R_BADAPR_REC.PAYYM,
                                           V_STR,
                                           R_BADAPR_REC.PAMTS,
                                           'excel事故者出生日期資料錯誤'
                                      FROM DUAL;
                        End;
                    END IF;

                    V_Key_Brdate := R_Badapr_Rec.Evtbrdate;
                    V_Key_Name   := R_Badapr_Rec.evtname;
                    V_Key_Intyp  := 'L';
                    v_key_seqno  := '0000';

                    R_Badapr_Rec.Sex := nvl(R_Bansf_Rec_Rec.Sex, 'Z'); --'事故者性別';   base.EVTSEX

                    R_Badapr_Rec.EVTYPE    := r_bansf_rec_rec.EVTYPE; --'傷病分類';  BAAPPEXPAND.EVAPPTYP
                    R_Badapr_Rec.Evcode    := R_Bansf_Rec_Rec.Evcode; --'事故原因';  BAAPPEXPAND.EVCODE
                    R_Badapr_Rec.Injno     := R_Bansf_Rec_Rec.Injno; --'障礙(失能)項目';      BAAPPEXPAND.CRIINJDP1
                    R_Badapr_Rec.Injdp     := F_Injdp(R_Badapr_Rec.Injno); --'障礙(失能部位)';
                    R_Badapr_Rec.Pamts     := R_Bansf_Rec_Rec.Pamts; --'核付金額';
                    R_Badapr_Rec.Age       := 0; --'申請時事故者年齡';
                    R_Badapr_Rec.Firstpay  := R_Bansf_Rec_Rec.Firstpay; --'首發註記';
                    R_Badapr_Rec.Deduct    := R_Bansf_Rec_Rec.Deduct; --S '扣減金額';
                    R_Badapr_Rec.deductday := 0; --S '扣減日數';
                    R_Badapr_Rec.PAYDAY    := NVL(R_Bansf_Rec_Rec.PAYDAY, 0); --'給付日數';
                    R_Badapr_Rec.Nachgmk   := R_Bansf_Rec_Rec.Nachgmk; --'普職互改註記';
                    --'補發收回註記';
                    If R_Bansf_Rec_Rec.Suprecmk = '1' Then
                        R_Badapr_Rec.Suprecmk := Null;
                    Else
                        R_Badapr_Rec.Suprecmk := R_Bansf_Rec_Rec.Suprecmk;
                    End If;
                    /*1.給付種類<>37
                    (1)補收註記為C或D不計件數。
                    (2)補收註記不為C且不為D，同一核定年月同一受理編號，只計1件(同一受理編號只看同一核定年月，不看其他核定年月，不看給付種類37)
                    2.給付種類=37
                    (1)補收註記為C，加總給付種類=37，核定年月<本次核定年月之同一受理編號核付金額：
                    A.加總金額＞0，不計件數。
                    B.加總金額=0，計1件。
                    舉例：
                      給付種類  核定年月  APNO              給付金額    補發收回註記  件數
                        37      09805     K00000000016      1,000,000                   1
                        37      09905     K00000000016     -1,000,000        D         -1
                        37      10105     K00000000016        200,000        C          1 不含本次加總金額=100萬 > 0，件數=0,否則,加總=0,件數為1 else 0件
                    (2)補收註記為D，加總給付種類=37，核定年月≦本次核定年月之同一受理編號核付金額：
                    A.加總金額＞0，不計件數。
                    B.加總金額=0，計-1件。
                    舉例：
                      給付種類  核定年月  APNO              給付金額    補發收回註記  件數
                        37      09805     K00000000016      1,000,000                   1
                        37      10105     K00000000016       -200,000        D          0  含本次加總金額=80萬 > 0，件數=0
                    (3)補收註記不為C且不為D，核付金額>0，計1件。
                             */
                    If R_Badapr_Rec.Payno = '37' And
                       R_Badapr_Rec.Suprecmk = 'D' THEN
                        V_Samt := V_Samt + R_Badapr_Rec.Pamts;
                    END IF;
                    Case
                        When R_Badapr_Rec.Payno = '37' And
                             R_Badapr_Rec.Suprecmk = 'K' Then
                            R_Badapr_Rec.Paycnt := -1;
                        When R_Badapr_Rec.Suprecmk = 'K' Then
                            R_Badapr_Rec.Paycnt := 0;
                        When R_Badapr_Rec.Payno = '37' And
                             R_Badapr_Rec.Suprecmk = 'C' AND V_Samt = 0 Then
                            R_Badapr_Rec.Paycnt := 1;
                        When R_Badapr_Rec.Payno = '37' And
                             R_Badapr_Rec.Suprecmk = 'C' And V_Samt <> 0 Then
                            R_Badapr_Rec.Paycnt := 0;
                        When R_Badapr_Rec.Payno = '37' And
                             R_Badapr_Rec.Suprecmk = 'D' AND V_Samt = 0 Then
                            R_Badapr_Rec.Paycnt := -1;
                        When R_Badapr_Rec.Payno = '37' And
                             R_Badapr_Rec.Suprecmk = 'D' And V_Samt <> 0 Then
                            R_Badapr_Rec.Paycnt := 0;
                        When R_Badapr_Rec.Payno = '37' And
                             R_Badapr_Rec.Pamts > 0 Then
                            R_Badapr_Rec.Paycnt := 1;
                        When R_Badapr_Rec.Payno = '37' And
                             R_Badapr_Rec.Pamts <= 0 Then
                            R_Badapr_Rec.Paycnt := 0;
                        When R_Badapr_Rec.Suprecmk = 'C' Or
                             R_Badapr_Rec.Suprecmk = 'D' Then
                            R_Badapr_Rec.Paycnt := 0;
                        When V_Apno = R_Badapr_Rec.Apno And
                             V_Iisuym = R_Badapr_Rec.Issuym Then
                            R_Badapr_Rec.Paycnt := 0;
                        When R_Badapr_Rec.Nachgmk IN ('A', 'B') THEN
                            --2013/2/19 --(Nachgmk NOT IN (A,B) AND (Suprecmk IN (d,c) OR PAMTS<=0)) THEN PAYCNT=0
                            R_Badapr_Rec.Paycnt := 0;
                        When R_Badapr_Rec.Pamts > 0 THEN
                            --2013/2/19 --(Nachgmk NOT IN (A,B) AND (Suprecmk IN (d,c) OR PAMTS<=0)) THEN PAYCNT=0
                            R_Badapr_Rec.Paycnt := 1;
                        Else
                            R_Badapr_Rec.Paycnt := 0;
                    End Case;
                    If NOT (R_Badapr_Rec.Payno = '37' And
                        R_Badapr_Rec.Suprecmk = 'D') THEN
                        V_Samt := V_Samt + R_Badapr_Rec.Pamts;
                    END IF;

                    case
                        when r_bansf_rec_rec.adwkmk = '+' then
                            R_Badapr_Rec.Adwkmk := '2';
                        when r_bansf_rec_rec.adwkmk is null then
                            R_Badapr_Rec.Adwkmk := '1';
                        else
                            R_Badapr_Rec.Adwkmk := r_bansf_rec_rec.adwkmk;
                    end case;

                    If r_bansf_rec_rec.Adwkmk = '2' Then
                        V_Key_Intyp := 'V';
                    Else
                        V_Key_Intyp := 'L';
                    end if;

                    V_Key_Apno := R_Badapr_Rec.Apno;

                    --取得BADAPR or baappbase資料
                    V_Str               := 'N';
                    R_Source_Badapr_Rec := Null;
                    --給付種類5開頭及35,37,存取核定檔,否則存取base
                    sw_found_badapr := false;
                    IF Substr(R_Badapr_Rec.Payno, 1, 1) = '5' Or
                       R_Badapr_Rec.Payno In ('35', '37', '38') Then
                        --          DBMS_OUTPUT.put_line ('1'||';'||R_Badapr_Rec.ISSUYM||';'||R_Badapr_Rec.payym||';'||R_Badapr_Rec.apno||';'||R_Badapr_Rec.Suprecmk);
                        For R_Source_Badapr_Rec In C_BAdapr_Suprecmk(R_Badapr_Rec.Apno,
                                                                     R_Badapr_Rec.Payym,
                                                                     R_Badapr_Rec.Suprecmk,
                                                                     R_Badapr_Rec.Payno) Loop
                            sw_found_badapr := true;
                            V_Str           := 'N';
                            If R_Source_Badapr_Rec.Aplpaydate Is Not Null Then
                                R_Badapr_Rec.Paydate := C_BADAPR_SUPRECMK_PAYDATE(R_Badapr_Rec.Apno,
                                                                                  R_Badapr_Rec.Issuym,
                                                                                  R_Badapr_Rec.Payym,
                                                                                  R_Badapr_Rec.Suprecmk,
                                                                                  R_Badapr_Rec.Payno,
                                                                                  R_Badapr_Rec.Paydate);
                                --R_Badapr_Rec.Paydate       :=R_Source_Badapr_Rec.Aplpaydate;     --'核付日期';
                            end if;
                            R_Badapr_Rec.NITRMY  := R_Source_Badapr_Rec.NITRMY; --'勞保投保年資(年-年金制)';
                            R_Badapr_Rec.Nitrmm  := R_Source_Badapr_Rec.Nitrmm; --'勞保投保年資(月-年金制)';
                            R_Badapr_Rec.Pwage   := R_Source_Badapr_Rec.Insavgamt; --'平均薪資';
                            R_Badapr_Rec.Mchktyp := R_Source_Badapr_Rec.Mchktyp;
                            Case
                                When r_bansf_rec_rec.Firstpay = '1' Then
                                    R_Badapr_Rec.Mchktyp := '1'; --'月核案件類別';
                                When R_Source_Badapr_Rec.Mchktyp = '3' Then
                                    R_Badapr_Rec.Mchktyp := '3'; --'月核案件類別';
                                When R_Badapr_Rec.Suprecmk = 'C' Then
                                    R_Badapr_Rec.Mchktyp := '5'; --'月核案件類別';
                                Else
                                    R_Badapr_Rec.Mchktyp := '2'; --'月核案件類別';
                            End Case;
                            R_Badapr_Rec.Oldab        := R_Source_Badapr_Rec.Oldab; --'第一式/第二式';
                            R_Badapr_Rec.Oldaamt      := R_Source_Badapr_Rec.Oldaamt; --'第一式金額(勞保給付金額)';
                            R_Badapr_Rec.Oldbamt      := R_Source_Badapr_Rec.Oldbamt; --'第二式金額(勞保給付金額)';
                            R_Badapr_Rec.Qualcount    := R_Source_Badapr_Rec.Qualcount; --'符合眷屬(遺屬)人數';
                            R_Badapr_Rec.Annuamt      := Nvl(R_Source_Badapr_Rec.Annuamt,
                                                             0); --'累計已領年金金額';
                            R_Badapr_Rec.CUTAMT       := nvl(R_Source_Badapr_Rec.REMAINAMT,
                                                             0); --'應扣失能金額';
                            R_Badapr_Rec.Lecomamt     := nvl(R_Source_Badapr_Rec.Lecomamt,
                                                             0); --'己扣失能金額';
                            R_Badapr_Rec.Oldextrarate := R_Source_Badapr_Rec.Oldextrarate; --(老年、遺屬)展延/減額比率
                            V_Key_Baappbaseid         := R_Source_Badapr_Rec.Baappbaseid; --給付主檔資料編號

                            If R_Source_Badapr_Rec.Seqno = '0000' Then
                                V_Str           := 'Y';
                                V_Out_Temp_Cnt  := 0;
                                R_Baappbase_Rec := Null;
                                V_Key_Seqno     := R_Source_Badapr_Rec.Seqno;
                                P_Read_Baappbase;
                                If Sw_Found = False Then
                                    P_Read_Baappbase_D;
                                End If;
                                R_Badapr_Rec.Seqno := R_Source_Badapr_Rec.Seqno; --'序號';
                                Update_Data;
                            Else
                                -- 新增眷屬資料
                                V_Out_temp_Cnt     := V_Out_temp_Cnt + 1;
                                R_Badapr_Rec.Seqno := R_Source_Badapr_Rec.Seqno;
                                R_Baappbase_Rec    := Null;
                                V_Key_Seqno        := R_Badapr_Rec.Seqno;
                                P_Read_Baappbase;
                                If Sw_Found = False Then
                                    P_Read_Baappbase_D;
                                End If;
                                Update_Data;
                                If V_Out_Temp_Cnt <> 1 Then
                                    R_Badapr_Rec.pamts := 0; --僅第一筆眷屬寫入全數金額
                                end if;
                            End If;
                            --取得cipb
                            P_Read_Cipb;
                            R_Badapr_Rec.Hbedmk := R_Cipb_Rec.Hbedmk; --'年金施行前有無保險年資';
                            --If V_Str='Y' And R_Source_Badapr_Rec.Aplpaymk = '3' Then   未來將加入判別
                            -- DBMS_OUTPUT.put_line (V_Str||';'||R_Badapr_Rec.ISSUYM||';'||R_Badapr_Rec.payym||';'||R_Badapr_Rec.apno);
                            If V_Str = 'Y' Then
                                --有存在seqno=0000才寫入眷屬
                                Case
                                    When R_Badapr_Rec.Evtype = '1' Then
                                        --變數設定EVTYPE
                                        V_Str := '3';
                                    When R_Badapr_Rec.Evtype = '2' Then
                                        V_Str := '4';
                                    When R_Badapr_Rec.Evtype = '3' Then
                                        V_Str := '1';
                                    Else
                                        V_Str := '2';
                                End Case;
                                /*不分給付種類(36除外) 皆用BAAPPBASE.APITEM申請項目判斷
                                項目=7,8  退休條件給"2"-領取失能年金及老年年金中途死亡者
                                項目=9    退休條件給"1"-符合退休條件者
                                其餘      退休條件給"0"-尚未符合退休條件者
                                但給付種類=36 給 " "-空白*/
                                Case
                                    When R_Badapr_Rec.Payno = '36' Then
                                        V_Cat := Null;
                                    When R_Badapr_Rec.Apitem In ('7', '8') Then
                                        V_Cat := '2';
                                    When R_Badapr_Rec.Apitem = '9' Then
                                        V_Cat := '1';
                                    Else
                                        V_Cat := '0';
                                END CASE;

                                IF SW_FOUND = TRUE THEN
                                    IF V_Out_Temp_Cnt = 0 Then
                                        --僅seqno=0000才新增至年金統計檔
                                        V_Out_Cnt := V_Out_Cnt + 1;

                                        Insert Into bansf
                                            (Select R_Badapr_Rec.Paydate,
                                                    R_Badapr_Rec.Appdate,
                                                    R_Badapr_Rec.Issuym,
                                                    R_Badapr_Rec.Payym,
                                                    R_Badapr_Rec.Apno,
                                                    '0000',
                                                    R_Badapr_Rec.Edate,
                                                    R_Badapr_Rec.Ubno,
                                                    R_Baappbase_Rec.LSUbno,
                                                    R_Badapr_Rec.Ubtype,
                                                    R_Badapr_Rec.Inds,
                                                    R_Badapr_Rec.Hincd,
                                                    R_Badapr_Rec.Idsta,
                                                    R_Badapr_Rec.Area,
                                                    R_Badapr_Rec.Clsqty,
                                                    R_Badapr_Rec.Sex,
                                                    V_Age,
                                                    R_Badapr_Rec.Age,
                                                    V_Str2,
                                                    R_Badapr_Rec.Evtnationtpe,
                                                    R_Badapr_Rec.Payno,
                                                    R_Badapr_Rec.Nitrmy,
                                                    R_Badapr_Rec.Nitrmm,
                                                    R_Badapr_Rec.Evtype,
                                                    R_Badapr_Rec.Evcode,
                                                    R_Badapr_Rec.Injname,
                                                    R_Badapr_Rec.Injno,
                                                    R_Badapr_Rec.Injdp,
                                                    R_Badapr_Rec.Injpart,
                                                    R_Badapr_Rec.Medium,
                                                    R_Badapr_Rec.Injcl,
                                                    R_Badapr_Rec.Apitem,
                                                    R_Badapr_Rec.Chkkind,
                                                    R_Badapr_Rec.Pwage,
                                                    R_Badapr_Rec.Wage,
                                                    R_Badapr_Rec.Paycnt,
                                                    R_Badapr_Rec.Suprecmk,
                                                    R_Badapr_Rec.Pamts,
                                                    R_Badapr_Rec.Adwkmk,
                                                    R_Badapr_Rec.Mchktyp,
                                                    R_Badapr_Rec.Oldab,
                                                    R_Badapr_Rec.Oldaamt,
                                                    R_Badapr_Rec.Oldbamt,
                                                    R_Badapr_Rec.Oldextrarate,
                                                    R_Badapr_Rec.Qualcount,
                                                    R_Badapr_Rec.Oldrate,
                                                    R_Badapr_Rec.Closecause,
                                                    R_Badapr_Rec.Annuamt,
                                                    R_Badapr_Rec.Hbedmk,
                                                    R_Badapr_Rec.Nachgmk,
                                                    R_Badapr_Rec.Cutamt,
                                                    R_Badapr_Rec.Lecomamt,
                                                    R_Badapr_Rec.Paykind,
                                                    DECODE(LENGTH(R_Badapr_Rec.Evtidnno),
                                                           11,
                                                           SUBSTR(R_Badapr_Rec.Evtidnno,
                                                                  1,
                                                                  10),
                                                           R_Badapr_Rec.Evtidnno),
                                                    DECODE(LENGTH(R_Badapr_Rec.Evtidnno),
                                                           11,
                                                           SUBSTR(R_Badapr_Rec.Evtidnno,
                                                                  10,
                                                                  1),
                                                           NULL),
                                                    R_Badapr_Rec.Evtbrdate,
                                                    R_Badapr_Rec.Evtname,
                                                    V_Edate,
                                                    V_Bdate,
                                                    R_Baappbase_Rec.Evtdiedate,
                                                    R_Cipb_Rec.Adjym,
                                                    DECODE(R_Cipb_Rec.Adjmk,
                                                           NULL,
                                                           'N',
                                                           R_Cipb_Rec.Adjmk),
                                                    V_STR3,
                                                    R_Badapr_Rec.firstpay,
                                                    R_Cipb_Rec.CIID,
                                                    0,
                                                    V_Cat,
                                                    0,
                                                    0,
                                                    null,
                                                    '',
                                                    to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                               From Dual);
                                        IF R_Badapr_Rec.Nachgmk In ('A', 'B') Then
                                            --當存在普職互改註記時,則新增一筆負值資料,並變更EVTYPE
                                            V_Out_Cnt := V_Out_Cnt + 1;
                                            INSERT INTO bansf
                                                (SELECT R_BADAPR_REC.PAYDATE,
                                                        R_BADAPR_REC.APPDATE,
                                                        R_BADAPR_REC.ISSUYM,
                                                        R_BADAPR_REC.PAYYM,
                                                        R_BADAPR_REC.APNO,
                                                        '0000',
                                                        R_BADAPR_REC.EDATE,
                                                        R_BADAPR_REC.UBNO,
                                                        R_BAAPPBASE_REC.LSUBNO,
                                                        R_BADAPR_REC.UBTYPE,
                                                        R_BADAPR_REC.INDS,
                                                        R_BADAPR_REC.HINCD,
                                                        R_BADAPR_REC.IDSTA,
                                                        R_BADAPR_REC.AREA,
                                                        R_BADAPR_REC.CLSQTY,
                                                        R_BADAPR_REC.SEX,
                                                        V_AGE,
                                                        R_BADAPR_REC.AGE,
                                                        V_STR2,
                                                        R_BADAPR_REC.EVTNATIONTPE,
                                                        R_BADAPR_REC.PAYNO,
                                                        R_BADAPR_REC.NITRMY,
                                                        R_BADAPR_REC.NITRMM,
                                                        V_STR,
                                                        R_BADAPR_REC.EVCODE,
                                                        R_BADAPR_REC.INJNAME,
                                                        R_BADAPR_REC.INJNO,
                                                        R_BADAPR_REC.INJDP,
                                                        R_BADAPR_REC.INJPART,
                                                        R_BADAPR_REC.MEDIUM,
                                                        R_BADAPR_REC.INJCL,
                                                        R_BADAPR_REC.APITEM,
                                                        R_BADAPR_REC.CHKKIND,
                                                        R_BADAPR_REC.PWAGE,
                                                        R_BADAPR_REC.WAGE,
                                                        R_BADAPR_REC.PAYCNT * (-1),
                                                        R_BADAPR_REC.SUPRECMK,
                                                        R_BADAPR_REC.PAMTS * (-1),
                                                        R_BADAPR_REC.ADWKMK,
                                                        R_BADAPR_REC.MCHKTYP,
                                                        R_BADAPR_REC.OLDAB,
                                                        R_BADAPR_REC.OLDAAMT,
                                                        R_BADAPR_REC.OLDBAMT,
                                                        R_BADAPR_REC.OLDEXTRARATE,
                                                        R_BADAPR_REC.QUALCOUNT,
                                                        R_BADAPR_REC.OLDRATE,
                                                        R_BADAPR_REC.CLOSECAUSE,
                                                        R_BADAPR_REC.ANNUAMT,
                                                        R_BADAPR_REC.HBEDMK,
                                                        R_BADAPR_REC.NACHGMK,
                                                        R_BADAPR_REC.CUTAMT,
                                                        R_BADAPR_REC.LECOMAMT,
                                                        R_BADAPR_REC.PAYKIND,
                                                        DECODE(LENGTH(R_BADAPR_REC.EVTIDNNO),
                                                               11,
                                                               SUBSTR(R_BADAPR_REC.EVTIDNNO,
                                                                      1,
                                                                      10),
                                                               R_BADAPR_REC.EVTIDNNO),
                                                        DECODE(LENGTH(R_BADAPR_REC.EVTIDNNO),
                                                               11,
                                                               SUBSTR(R_BADAPR_REC.EVTIDNNO,
                                                                      10,
                                                                      1),
                                                               NULL),
                                                        R_BADAPR_REC.EVTBRDATE,
                                                        R_BADAPR_REC.EVTNAME,
                                                        V_EDATE,
                                                        V_BDATE,
                                                        R_BAAPPBASE_REC.EVTDIEDATE,
                                                        R_CIPB_REC.ADJYM,
                                                        DECODE(R_CIPB_REC.ADJMK,
                                                               NULL,
                                                               'N',
                                                               R_CIPB_REC.ADJMK),
                                                        V_STR3,
                                                        R_BADAPR_REC.FIRSTPAY,
                                                        R_CIPB_REC.CIID,
                                                        0,
                                                        V_CAT,
                                                        0,
                                                        0,
                                                        null,
                                                        '',
                                                        to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                                   FROM DUAL);
                                        END IF;
                                        --2014/04/28
                                        IF R_Source_Badapr_Rec.Seqno = '0000' AND
                                           Substr(R_Badapr_Rec.Payno, 1, 1) = '3' AND
                                           R_Baappbase_Rec.Evtdiedate IS NOT NULL THEN
                                            Insert Into bansf_TEMP
                                                (Select R_Badapr_Rec.Paydate,
                                                        R_Badapr_Rec.Appdate,
                                                        R_Badapr_Rec.Issuym,
                                                        R_Badapr_Rec.Payym,
                                                        R_Badapr_Rec.Apno,
                                                        '0000',
                                                        R_Badapr_Rec.Edate,
                                                        R_Badapr_Rec.Ubno,
                                                        R_Baappbase_Rec.LSUbno,
                                                        R_Badapr_Rec.Ubtype,
                                                        R_Badapr_Rec.Inds,
                                                        R_Badapr_Rec.Hincd,
                                                        R_Badapr_Rec.Idsta,
                                                        R_Badapr_Rec.Area,
                                                        R_Badapr_Rec.Clsqty,
                                                        R_Badapr_Rec.Sex,
                                                        V_Age,
                                                        R_Badapr_Rec.Age,
                                                        V_Str2,
                                                        R_Badapr_Rec.Evtnationtpe,
                                                        R_Badapr_Rec.Payno,
                                                        R_Badapr_Rec.Nitrmy,
                                                        R_Badapr_Rec.Nitrmm,
                                                        R_Badapr_Rec.Evtype,
                                                        R_Badapr_Rec.Evcode,
                                                        R_Badapr_Rec.Injname,
                                                        R_Badapr_Rec.Injno,
                                                        R_Badapr_Rec.Injdp,
                                                        R_Badapr_Rec.Injpart,
                                                        R_Badapr_Rec.Medium,
                                                        R_Badapr_Rec.Injcl,
                                                        R_Badapr_Rec.Apitem,
                                                        R_Badapr_Rec.Chkkind,
                                                        R_Badapr_Rec.Pwage,
                                                        R_Badapr_Rec.Wage,
                                                        R_Badapr_Rec.Paycnt,
                                                        R_Badapr_Rec.Suprecmk,
                                                        R_Badapr_Rec.Pamts,
                                                        R_Badapr_Rec.Adwkmk,
                                                        R_Badapr_Rec.Mchktyp,
                                                        R_Badapr_Rec.Oldab,
                                                        R_Badapr_Rec.Oldaamt,
                                                        R_Badapr_Rec.Oldbamt,
                                                        R_Badapr_Rec.Oldextrarate,
                                                        R_Badapr_Rec.Qualcount,
                                                        R_Badapr_Rec.Oldrate,
                                                        R_Badapr_Rec.Closecause,
                                                        R_Badapr_Rec.Annuamt,
                                                        R_Badapr_Rec.Hbedmk,
                                                        R_Badapr_Rec.Nachgmk,
                                                        R_Badapr_Rec.Cutamt,
                                                        R_Badapr_Rec.Lecomamt,
                                                        R_Badapr_Rec.Paykind,
                                                        DECODE(LENGTH(R_Badapr_Rec.Evtidnno),
                                                               11,
                                                               SUBSTR(R_Badapr_Rec.Evtidnno,
                                                                      1,
                                                                      10),
                                                               R_Badapr_Rec.Evtidnno),
                                                        DECODE(LENGTH(R_Badapr_Rec.Evtidnno),
                                                               11,
                                                               SUBSTR(R_Badapr_Rec.Evtidnno,
                                                                      10,
                                                                      1),
                                                               NULL),
                                                        R_Badapr_Rec.Evtbrdate,
                                                        R_Badapr_Rec.Evtname,
                                                        V_Edate,
                                                        V_Bdate,
                                                        R_Baappbase_Rec.Evtdiedate,
                                                        R_Cipb_Rec.Adjym,
                                                        DECODE(R_Cipb_Rec.Adjmk,
                                                               NULL,
                                                               'N',
                                                               R_Cipb_Rec.Adjmk),
                                                        V_STR3,
                                                        R_Badapr_Rec.firstpay,
                                                        R_Cipb_Rec.CIID,
                                                        0,
                                                        V_Cat,
                                                        0,
                                                        0,
                                                        null
                                                   From Dual);
                                        END IF;
                                    END IF;
                                    --異常資料建入badapr_ref_errl
                                    If R_Badapr_Rec.Nachgmk In ('A', 'B') and
                                       f_data(R_Badapr_Rec.Apno,
                                              R_Badapr_Rec.Payym) > 0 Then
                                        --當存在普職互改註記時,
                                        --找尋同給付月份,且Nachgmk為NULL的資料,當資料不存在,則異常資料建入badapr_ref_errl
                                        INSERT INTO BADAPR_REF_ERRL
                                            SELECT R_BADAPR_REC.APNO,
                                                   R_BADAPR_REC.PAYYM,
                                                   V_STR,
                                                   R_BADAPR_REC.PAMTS,
                                                   '為普職註記,但找不到正常本月資料'
                                              FROM DUAL;
                                    END IF;
                                    INSERT INTO BADAPR_REF VALUES R_BADAPR_REC;
                                    IF R_BADAPR_REC.NACHGMK IN ('A', 'B') THEN
                                        --當存在普職互改註記時,則新增一筆負值資料,並變更EVTYPE
                                        R_Badapr_Rec.PAYcnt := R_Badapr_Rec.PAYcnt * -1;
                                        R_BADAPR_REC.PAMTS  := R_BADAPR_REC.PAMTS * -1;
                                        R_BADAPR_REC.EVTYPE := V_STR;
                                        INSERT INTO BADAPR_REF
                                        VALUES R_BADAPR_REC;
                                    END IF;
                                end if;
                                V_Str := 'Y'; --中間有更動,回寫為正常值
                            Else
                                V_Str := 'N';
                                /*  未來將加入判別
                                If V_Str='Y' Then
                                      --找尋不到原資料,寫入異常
                                    Insert Into Badapr_Ref_Errl Select R_Badapr_Rec.Apno,R_Badapr_Rec.Payym,R_Badapr_Rec.Evtype,R_Badapr_Rec.Pamts,'在BADAPR帳務註記不為3資料' From Dual;
                                 End If;  */
                            End If;
                        End Loop;
                        If V_Str = 'N' Then
                            --找尋不到原資料,寫入異常
                            Insert Into Badapr_Ref_Errl
                                Select R_Badapr_Rec.Apno,
                                       R_Badapr_Rec.Payym,
                                       R_Badapr_Rec.Evtype,
                                       R_Badapr_Rec.Pamts,
                                       '在BADAPR找不到資料'
                                  From Dual;
                        END IF;
                    end if;
                    if sw_found_badapr = false then
                        --Dbms_Output.Put_Line(R_Badapr_Rec.Apno||','||R_Badapr_Rec.Payym);
                        R_Baappbase_Rec := Null;
                        --For R_Source_Baappbase_Rec In C_Baappbase(R_Badapr_Rec.Apno,R_Badapr_Rec.Payym)
                        For R_Source_Baappbase_Rec In C_Baappbase(R_Badapr_Rec.Apno) Loop
                            -- If R_Source_Baappbase_Rec.Paydate Is Not Null Then
                            --     R_Badapr_Rec.Paydate       :=R_Source_Baappbase_Rec.Paydate;     --'核付日期';
                            -- end if;
                            R_Badapr_Rec.Pwage := R_Bansf_Rec_Rec.PWAGE; --'平均薪資';
                            Case
                                When r_bansf_rec_rec.Firstpay = '1' Then
                                    R_Badapr_Rec.Mchktyp := '1'; --'月核案件類別';
                                When R_Source_Baappbase_Rec.CASETYP = '3' Then
                                    R_Badapr_Rec.Mchktyp := '3'; --'月核案件類別';
                                When R_Source_Baappbase_Rec.CASETYP = '5' Then
                                    R_Badapr_Rec.Mchktyp := '5'; --'月核案件類別';
                                Else
                                    R_Badapr_Rec.Mchktyp := '2'; --'月核案件類別';
                            End Case;
                            R_Badapr_Rec.Oldab        := NULL; --'第一式/第二式';
                            R_Badapr_Rec.Oldaamt      := 0; --'第一式金額(勞保給付金額)';
                            R_Badapr_Rec.Oldbamt      := 0; --'第二式金額(勞保給付金額)';
                            R_Badapr_Rec.Qualcount    := 0; --'符合眷屬(遺屬)人數';
                            R_Badapr_Rec.Annuamt      := nvl(R_Source_Baappbase_Rec.Annuamt,
                                                             0); --'累計已領年金金額';
                            R_Badapr_Rec.Lecomamt     := 0; --'己扣失能金額';
                            R_Badapr_Rec.Oldextrarate := 0; --(老年、遺屬)展延/減額比率
                            V_Key_Baappbaseid         := R_Source_Baappbase_Rec.Baappbaseid; --給付主檔資料編號

                            If R_Source_Baappbase_Rec.Seqno = '0000' Then
                                V_Str              := 'Y';
                                V_Out_Temp_Cnt     := 0;
                                R_Badapr_Rec.Seqno := R_Source_Baappbase_Rec.Seqno;
                                R_Baappbase_Rec    := R_Source_Baappbase_Rec; --供update_data使用
                                Update_Data;
                            Else
                                -- 新增眷屬資料
                                V_Out_Temp_Cnt     := V_Out_Temp_Cnt + 1;
                                R_Badapr_Rec.Seqno := R_Source_Baappbase_Rec.Seqno;
                                R_Baappbase_Rec    := R_Source_Baappbase_Rec; --供update_data使用
                                Update_Data;
                                If V_Out_Temp_Cnt <> 1 Then
                                    R_Badapr_Rec.pamts := 0; --僅第一筆眷屬寫入全數金額
                                end if;
                            End If;
                            --取得cipb;
                            V_Key_Brdate := R_Baappbase_Rec.Evtbrdate;
                            v_key_name   := R_Baappbase_Rec.evtname;
                            /*If  r_bansf_rec_rec.Adwkmk='2' Then
                                V_Key_Intyp:= 'V';
                            Else
                                V_Key_Intyp:= 'L';
                            END IF;   */

                            P_READ_CIPB;
                            R_BADAPR_REC.HBEDMK := R_Cipb_Rec.Hbedmk; --'年金施行前有無保險年資';
                            R_BADAPR_REC.NITRMY := R_CIPB_REC.NITRMY; --'勞保投保年資(年-年金制)';
                            R_BADAPR_REC.NITRMM := R_CIPB_REC.NITRMM; --'勞保投保年資(月-年金制)';

                            -- Dbms_Output.Put_Line('2.');
                            If V_Str = 'Y' Then
                                --有存在seqno=0000才寫入眷屬
                                Case
                                    When R_Badapr_Rec.Evtype = '1' Then
                                        --變數設定EVTYPE
                                        V_Str := '3';
                                    When R_Badapr_Rec.Evtype = '2' Then
                                        V_Str := '4';
                                    When R_Badapr_Rec.Evtype = '3' Then
                                        V_Str := '1';
                                    Else
                                        V_Str := '2';
                                End Case;
                                /*不分給付種類(36除外) 皆用BAAPPBASE.APITEM申請項目判斷
                                項目=7,8  退休條件給"2"-領取失能年金及老年年金中途死亡者
                                項目=9    退休條件給"1"-符合退休條件者
                                其餘      退休條件給"0"-尚未符合退休條件者
                                但給付種類=36 給 " "-空白*/
                                Case
                                    When R_Badapr_Rec.Payno = '36' Then
                                        V_Cat := Null;
                                    ELSE
                                        V_Cat := '0';
                                END CASE;

                                If V_Out_Temp_Cnt = 0 Then
                                    --僅seqno=0000才新增至年金統計檔
                                    V_Out_Cnt := V_Out_Cnt + 1;
                                    Insert Into bansf
                                        (Select R_Badapr_Rec.Paydate,
                                                R_Badapr_Rec.Appdate,
                                                R_Badapr_Rec.Issuym,
                                                R_Badapr_Rec.Payym,
                                                R_Badapr_Rec.Apno,
                                                '0000',
                                                R_Badapr_Rec.Edate,
                                                R_Badapr_Rec.Ubno,
                                                R_Source_Baappbase_Rec.LSUbno,
                                                R_Badapr_Rec.Ubtype,
                                                R_Badapr_Rec.Inds,
                                                R_Badapr_Rec.Hincd,
                                                R_Badapr_Rec.Idsta,
                                                R_Badapr_Rec.Area,
                                                R_Badapr_Rec.Clsqty,
                                                R_Badapr_Rec.Sex,
                                                V_Age,
                                                R_Badapr_Rec.Age,
                                                V_Str2,
                                                R_Badapr_Rec.Evtnationtpe,
                                                R_Badapr_Rec.Payno,
                                                R_Badapr_Rec.Nitrmy,
                                                R_Badapr_Rec.Nitrmm,
                                                R_Badapr_Rec.Evtype,
                                                R_Badapr_Rec.Evcode,
                                                R_Badapr_Rec.Injname,
                                                R_Badapr_Rec.Injno,
                                                R_Badapr_Rec.Injdp,
                                                R_Badapr_Rec.Injpart,
                                                R_Badapr_Rec.Medium,
                                                R_Badapr_Rec.Injcl,
                                                R_Badapr_Rec.Apitem,
                                                R_Badapr_Rec.Chkkind,
                                                R_Badapr_Rec.Pwage,
                                                R_Badapr_Rec.Wage,
                                                R_Badapr_Rec.Paycnt,
                                                R_Badapr_Rec.Suprecmk,
                                                R_Badapr_Rec.Pamts,
                                                R_Badapr_Rec.Adwkmk,
                                                R_Badapr_Rec.Mchktyp,
                                                R_Badapr_Rec.Oldab,
                                                R_Badapr_Rec.Oldaamt,
                                                R_Badapr_Rec.Oldbamt,
                                                R_Badapr_Rec.Oldextrarate,
                                                R_Badapr_Rec.Qualcount,
                                                R_Badapr_Rec.Oldrate,
                                                R_Badapr_Rec.Closecause,
                                                R_Badapr_Rec.Annuamt,
                                                R_Badapr_Rec.Hbedmk,
                                                R_Badapr_Rec.Nachgmk,
                                                R_Badapr_Rec.Cutamt,
                                                R_Badapr_Rec.Lecomamt,
                                                R_Badapr_Rec.Paykind,
                                                DECODE(LENGTH(R_Badapr_Rec.Evtidnno),
                                                       11,
                                                       SUBSTR(R_Badapr_Rec.Evtidnno,
                                                              1,
                                                              10),
                                                       R_Badapr_Rec.Evtidnno),
                                                DECODE(LENGTH(R_Badapr_Rec.Evtidnno),
                                                       11,
                                                       SUBSTR(R_Badapr_Rec.Evtidnno,
                                                              10,
                                                              1),
                                                       NULL),
                                                R_Baappbase_Rec.Evtbrdate,
                                                R_Badapr_Rec.Evtname,
                                                V_Edate,
                                                V_Bdate,
                                                R_Source_Baappbase_Rec.Evtdiedate,
                                                R_Cipb_Rec.Adjym,
                                                R_Cipb_Rec.Adjmk,
                                                v_str3,
                                                R_Badapr_Rec.firstpay,
                                                R_Cipb_Rec.CIID,
                                                0,
                                                V_Cat,
                                                0,
                                                0,
                                                null,
                                                '',
                                                to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                           From Dual);
                                    --2014/04/28 SEQNO=0000
                                    IF R_Source_Baappbase_Rec.Evtdiedate IS NOT NULL AND
                                       Substr(R_Badapr_Rec.Payno, 1, 1) = '3' THEN
                                        Insert Into bansf_TEMP
                                            (Select R_Badapr_Rec.Paydate,
                                                    R_Badapr_Rec.Appdate,
                                                    R_Badapr_Rec.Issuym,
                                                    R_Badapr_Rec.Payym,
                                                    R_Badapr_Rec.Apno,
                                                    '0000',
                                                    R_Badapr_Rec.Edate,
                                                    R_Badapr_Rec.Ubno,
                                                    R_Source_Baappbase_Rec.LSUbno,
                                                    R_Badapr_Rec.Ubtype,
                                                    R_Badapr_Rec.Inds,
                                                    R_Badapr_Rec.Hincd,
                                                    R_Badapr_Rec.Idsta,
                                                    R_Badapr_Rec.Area,
                                                    R_Badapr_Rec.Clsqty,
                                                    R_Badapr_Rec.Sex,
                                                    V_Age,
                                                    R_Badapr_Rec.Age,
                                                    V_Str2,
                                                    R_Badapr_Rec.Evtnationtpe,
                                                    R_Badapr_Rec.Payno,
                                                    R_Badapr_Rec.Nitrmy,
                                                    R_Badapr_Rec.Nitrmm,
                                                    R_Badapr_Rec.Evtype,
                                                    R_Badapr_Rec.Evcode,
                                                    R_Badapr_Rec.Injname,
                                                    R_Badapr_Rec.Injno,
                                                    R_Badapr_Rec.Injdp,
                                                    R_Badapr_Rec.Injpart,
                                                    R_Badapr_Rec.Medium,
                                                    R_Badapr_Rec.Injcl,
                                                    R_Badapr_Rec.Apitem,
                                                    R_Badapr_Rec.Chkkind,
                                                    R_Badapr_Rec.Pwage,
                                                    R_Badapr_Rec.Wage,
                                                    R_Badapr_Rec.Paycnt,
                                                    R_Badapr_Rec.Suprecmk,
                                                    R_Badapr_Rec.Pamts,
                                                    R_Badapr_Rec.Adwkmk,
                                                    R_Badapr_Rec.Mchktyp,
                                                    R_Badapr_Rec.Oldab,
                                                    R_Badapr_Rec.Oldaamt,
                                                    R_Badapr_Rec.Oldbamt,
                                                    R_Badapr_Rec.Oldextrarate,
                                                    R_Badapr_Rec.Qualcount,
                                                    R_Badapr_Rec.Oldrate,
                                                    R_Badapr_Rec.Closecause,
                                                    R_Badapr_Rec.Annuamt,
                                                    R_Badapr_Rec.Hbedmk,
                                                    R_Badapr_Rec.Nachgmk,
                                                    R_Badapr_Rec.Cutamt,
                                                    R_Badapr_Rec.Lecomamt,
                                                    R_Badapr_Rec.Paykind,
                                                    DECODE(LENGTH(R_Badapr_Rec.Evtidnno),
                                                           11,
                                                           SUBSTR(R_Badapr_Rec.Evtidnno,
                                                                  1,
                                                                  10),
                                                           R_Badapr_Rec.Evtidnno),
                                                    DECODE(LENGTH(R_Badapr_Rec.Evtidnno),
                                                           11,
                                                           SUBSTR(R_Badapr_Rec.Evtidnno,
                                                                  10,
                                                                  1),
                                                           NULL),
                                                    R_Baappbase_Rec.Evtbrdate,
                                                    R_Badapr_Rec.Evtname,
                                                    V_Edate,
                                                    V_Bdate,
                                                    R_Source_Baappbase_Rec.Evtdiedate,
                                                    R_Cipb_Rec.Adjym,
                                                    R_Cipb_Rec.Adjmk,
                                                    v_str3,
                                                    R_Badapr_Rec.firstpay,
                                                    R_Cipb_Rec.CIID,
                                                    0,
                                                    V_Cat,
                                                    0,
                                                    0,
                                                    null
                                               From Dual);
                                    END IF;
                                    IF R_Badapr_Rec.Nachgmk In ('A', 'B') Then
                                        --當存在普職互改註記時,則新增一筆負值資料,並變更EVTYPE
                                        V_Out_Cnt := V_Out_Cnt + 1;
                                        Insert Into bansf
                                            (Select R_Badapr_Rec.Paydate,
                                                    R_Badapr_Rec.Appdate,
                                                    R_Badapr_Rec.Issuym,
                                                    R_Badapr_Rec.Payym,
                                                    R_Badapr_Rec.Apno,
                                                    '0000',
                                                    R_Badapr_Rec.Edate,
                                                    R_Badapr_Rec.Ubno,
                                                    R_Source_Baappbase_Rec.LSUBNO,
                                                    R_Badapr_Rec.Ubtype,
                                                    R_Badapr_Rec.Inds,
                                                    R_Badapr_Rec.Hincd,
                                                    R_Badapr_Rec.Idsta,
                                                    R_Badapr_Rec.Area,
                                                    R_Badapr_Rec.Clsqty,
                                                    R_Badapr_Rec.Sex,
                                                    V_AGE,
                                                    R_Badapr_Rec.Age,
                                                    V_Str2,
                                                    R_Badapr_Rec.Evtnationtpe,
                                                    R_Badapr_Rec.Payno,
                                                    R_Badapr_Rec.Nitrmy,
                                                    R_Badapr_Rec.Nitrmm,
                                                    V_Str,
                                                    R_Badapr_Rec.Evcode,
                                                    R_Badapr_Rec.Injname,
                                                    R_Badapr_Rec.Injno,
                                                    R_Badapr_Rec.INJDP,
                                                    R_Badapr_Rec.Injpart,
                                                    R_Badapr_Rec.Medium,
                                                    R_Badapr_Rec.Injcl,
                                                    R_Badapr_Rec.Apitem,
                                                    R_Badapr_Rec.Chkkind,
                                                    R_Badapr_Rec.Pwage,
                                                    R_Badapr_Rec.Wage,
                                                    R_Badapr_Rec.Paycnt * (-1),
                                                    R_Badapr_Rec.Suprecmk,
                                                    R_Badapr_Rec.Pamts * (-1),
                                                    R_Badapr_Rec.Adwkmk,
                                                    R_Badapr_Rec.Mchktyp,
                                                    R_Badapr_Rec.Oldab,
                                                    R_Badapr_Rec.Oldaamt,
                                                    R_Badapr_Rec.Oldbamt,
                                                    R_Badapr_Rec.Oldextrarate,
                                                    R_Badapr_Rec.Qualcount,
                                                    R_Badapr_Rec.Oldrate,
                                                    R_Badapr_Rec.Closecause,
                                                    R_Badapr_Rec.Annuamt,
                                                    R_Badapr_Rec.Hbedmk,
                                                    R_Badapr_Rec.Nachgmk,
                                                    R_Badapr_Rec.Cutamt,
                                                    R_Badapr_Rec.Lecomamt,
                                                    R_Badapr_Rec.Paykind,
                                                    DECODE(LENGTH(R_Badapr_Rec.Evtidnno),
                                                           11,
                                                           SUBSTR(R_Badapr_Rec.Evtidnno,
                                                                  1,
                                                                  10),
                                                           R_Badapr_Rec.Evtidnno),
                                                    DECODE(LENGTH(R_Badapr_Rec.Evtidnno),
                                                           11,
                                                           SUBSTR(R_Badapr_Rec.Evtidnno,
                                                                  10,
                                                                  1),
                                                           NULL),
                                                    R_Baappbase_Rec.Evtbrdate,
                                                    R_Badapr_Rec.Evtname,
                                                    v_edate,
                                                    v_bdate,
                                                    R_Source_Baappbase_Rec.Evtdiedate,
                                                    R_Cipb_Rec.Adjym,
                                                    R_Cipb_Rec.Adjmk,
                                                    v_str3,
                                                    R_Badapr_Rec.firstpay,
                                                    R_Cipb_Rec.CIID,
                                                    0,
                                                    V_Cat,
                                                    0,
                                                    0,
                                                    null,
                                                    '',
                                                    to_Char(Sysdate,'YYYYMMDDHH24MISS')
                                               From Dual);
                                    END IF;
                                End If;
                                --異常資料建入badapr_ref_errl
                                If R_Badapr_Rec.Nachgmk In ('A', 'B') And
                                   F_Data(R_Badapr_Rec.Apno, R_Badapr_Rec.Payym) > 0 Then
                                    --當存在普職互改註記時,
                                    --找尋同給付月份,且Nachgmk為NULL的資料,當資料不存在,則異常資料建入badapr_ref_errl
                                    Insert Into Badapr_Ref_Errl
                                        Select R_Badapr_Rec.Apno,
                                               R_Badapr_Rec.Payym,
                                               V_Str,
                                               R_Badapr_Rec.Pamts,
                                               '為普職註記,但找不到正常本月資料'
                                          From Dual;
                                End If;
                                Insert Into Badapr_Ref Values R_Badapr_Rec;
                                If R_Badapr_Rec.Nachgmk In ('A', 'B') Then
                                    --當存在普職互改註記時,則新增一筆負值資料,並變更EVTYPE
                                    --   Dbms_Output.Put_Line('3.2'||';'||R_Badapr_Rec.Payym||';'||R_Badapr_Rec.Seqno);
                                    R_Badapr_Rec.Paycnt := R_Badapr_Rec.Paycnt * -1;
                                    R_Badapr_Rec.Pamts  := R_Badapr_Rec.Pamts * -1;
                                    R_Badapr_Rec.Evtype := V_Str;
                                    INSERT INTO BADAPR_REF VALUES R_BADAPR_REC;
                                End If;
                            END IF;
                            V_STR := 'Y'; --中間有更動,回寫為正常值
                        End Loop;
                        If V_Str = 'N' Then
                            --找尋不到原資料,寫入異常
                            Insert Into Badapr_Ref_Errl
                                Select R_Badapr_Rec.Apno,
                                       R_Badapr_Rec.Payym,
                                       R_Badapr_Rec.Evtype,
                                       R_Badapr_Rec.Pamts,
                                       '在BAAPPBASE找不到資料'
                                  From Dual;
                        End If;
                    End If;
                    IF V_STR = 'N' THEN
                        --資料不存在badapr,baappbase,55,37,38,39重找baappbase
                        R_Badapr_Rec.UBNO    := r_bansf_rec_rec.UBNO; --保險證號
                        R_Badapr_Rec.Injcl   := R_Bansf_Rec_Rec.Injcl; --失能等級 (身心障礙等級)
                        R_Badapr_Rec.Seqno   := '0000'; --序號
                        R_Badapr_Rec.Age     := F_Year(Substr(R_Badapr_Rec.Appdate,
                                                              1,
                                                              6),
                                                       Substr(R_Badapr_Rec.Evtbrdate,
                                                              1,
                                                              6)); --'申請年月-事故者出生年月=年齡';
                        R_Badapr_Rec.PWAGE   := nvl(r_bansf_rec_rec.PWAGE, 0); --平均薪資
                        R_Badapr_Rec.APPDATE := r_bansf_rec_rec.APPDATE; --申請日期
                        --原excel不存在的欄位如下:
                        R_Badapr_Rec.Paydate := R_Bansf_Rec_Rec.Issuym || '01'; --核付日期
                        If R_Badapr_Rec.Ubno Is Not Null Then
                            V_Key_Ubno := R_Badapr_Rec.Ubno;
                            P_Read_Caub;
                            R_Badapr_Rec.Ubtype := R_Caub_Rec.Ubtype; --'單位類別';
                            R_Badapr_Rec.Inds   := R_Caub_Rec.Inds; --'小業別';
                            R_Badapr_Rec.Hincd  := Substr(R_Caub_Rec.Hincd,
                                                          1,
                                                          2); --'職災代號';
                            R_Badapr_Rec.Idsta  := R_Caub_Rec.Idsta; --'大業別';
                            R_Badapr_Rec.Area   := R_Caub_Rec.Area; --'地區別';
                            R_Badapr_Rec.Clsqty := R_Caub_Rec.Prsno_B; --'月末人數';
                        End If;
                        R_Badapr_Rec.EVTNATIONTPE := 'C'; --被保險人國籍別
                        R_Badapr_Rec.INJNAME      := 'Z999999'; --傷病名稱(國際疾病代碼)
                        R_Badapr_Rec.INJDP        := 'Z99'; --障礙(失能部位)
                        R_BADAPR_REC.INJPART      := 'Z99'; --受傷部位
                        R_Badapr_Rec.MEDIUM       := 'Z99'; --媒介物
                        R_BADAPR_REC.APITEM       := 'Z'; --申請項目
                        R_Badapr_Rec.CHKKIND      := 'N'; --"符合離職退保後職災殘廢給付職業病種類(Y/N)"                                                 ;
                        R_Badapr_Rec.Wage         := 0; --投保薪資
                        R_Badapr_Rec.Adwkmk       := nvl(r_bansf_rec_rec.adwkmk,
                                                         '1'); --加職註記
                        R_Badapr_Rec.Evtname      := Null; --事故者姓名
                        --R_Badapr_Rec.MCHKTYP       :='Z'                           ;  --月核案件類別
                        Case
                            When R_Badapr_Rec.FIRSTPAY = '1' Then
                                R_Badapr_Rec.Mchktyp := '1'; --'月核案件類別';
                            When R_Badapr_Rec.FIRSTPAY = '0' Then
                                ---36的EXCEL下續發案給0
                                R_Badapr_Rec.Mchktyp := '2'; --'月核案件類別';
                            When R_Bansf_Rec_Rec.Suprecmk = 'C' Then
                                R_Badapr_Rec.Mchktyp := '5'; --'月核案件類別';
                            Else
                                R_Badapr_Rec.Mchktyp := '2'; --'月核案件類別' 無抓到核定檔.月核案件類別3,故3無法判別;
                        End Case;
                        V_Str2 := Null; --年齡組別
                        Case
                            WHEN v_Evtdiedate Is Null And
                                 R_Bansf_Rec_Rec.EDATE Is Not Null and
                                 R_Badapr_Rec.Paykind = 'S' THEN
                                Begin
                                    If Length(R_Bansf_Rec_Rec.EDATE) < 8 Then
                                        v_Evtdiedate := R_Bansf_Rec_Rec.EDATE; --事故者死亡日期
                                    Else
                                        v_Evtdiedate := R_Bansf_Rec_Rec.EDATE; --事故者死亡日期
                                    end if;
                                    --遺屬年金：計算至被保險人死亡年月，以死亡年月-事故者出生日期,取年
                                    V_Age            := F_Year(Substr(v_Evtdiedate,
                                                                      1,
                                                                      6),
                                                               Substr(R_Bansf_Rec_Rec.Evtbrdate,
                                                                      1,
                                                                      6)); --'核付年齡';
                                    R_Badapr_Rec.Age := V_Age;
                                Exception
                                    When Others Then
                                        v_Evtdiedate := R_Bansf_Rec_Rec.EDATE; --事故者死亡日期
                                        Insert Into Badapr_Ref_Errl
                                            Select R_Badapr_Rec.Apno,
                                                   R_Badapr_Rec.Payym,
                                                   V_Str,
                                                   R_Badapr_Rec.Pamts,
                                                   '事故者死亡日期資料錯誤'
                                              From Dual;
                                End;
                            WHEN R_Badapr_Rec.Evtbrdate Is Null And
                                 R_Bansf_Rec_Rec.Evtbrdate Is Not Null and
                                 R_Badapr_Rec.Paykind <> 'S' THEN
                                Begin
                                    If Length(R_Bansf_Rec_Rec.Evtbrdate) < 8 Then
                                        R_Badapr_Rec.Evtbrdate := R_Bansf_Rec_Rec.Evtbrdate; --事故者出生日期
                                    Else
                                        R_Badapr_Rec.Evtbrdate := R_Bansf_Rec_Rec.Evtbrdate; --事故者出生日期
                                    end if;
                                    --失能及老年年金：計算至核付年月，以核付年月－出生年月,取年
                                    V_Age := F_Year(Substr(R_Badapr_Rec.paydate,
                                                           1,
                                                           6),
                                                    Substr(R_Badapr_Rec.Evtbrdate,
                                                           1,
                                                           6)); --'核付年齡';
                                EXCEPTION
                                    When Others Then
                                        R_Badapr_Rec.Evtbrdate := R_Bansf_Rec_Rec.Evtbrdate; --事故者出生日期
                                        Insert Into Badapr_Ref_Errl
                                            Select R_Badapr_Rec.Apno,
                                                   R_Badapr_Rec.Payym,
                                                   V_Str,
                                                   R_Badapr_Rec.Pamts,
                                                   '事故者出生日期資料錯誤'
                                              From Dual;
                                End;
                            else
                                v_age := 0;
                        END CASE;

                        V_Str2     := F_Agetype(V_Age); --年齡組別
                        R_CIPB_REC := NULL;
                        IF R_BADAPR_REC.PAYNO = '36' THEN
                            R_BADAPR_REC.NITRMY := r_bansf_rec_rec.NITRMY; --勞保投保年資(年-年金制)
                            R_BADAPR_REC.NITRMM := R_BANSF_REC_REC.NITRMM; --勞保投保年資(月-年金制)
                            V_KEY_NBAPNO        := R_BANSF_REC_REC.NBAPNO;
                            R_BADAPR_REC.APITEM := '0'; --申請項目
                            P_READ_NBAAPPBASE;
                            IF sw_found = TRUE THEN
                                R_BADAPR_REC.EDATE     := R_NBAPPBASE_REC.EVTDT; --事故日期;
                                R_BADAPR_REc.EVTBRDATE := R_NBAPPBASE_REC.EVTEEBIRT; --生日;
                                R_BADAPR_REC.EVTIDNNO  := R_NBAPPBASE_REC.EVTIDNNO; -- 身份證號;
                                R_Badapr_Rec.Evtname   := R_NBAPPBASE_REC.Evteename; --'事故者姓名';
                            ELSE
                                R_BADAPR_REc.EVTBRDATE := r_bansf_rec_rec.EVTBRDATE; --生日;
                                R_BADAPR_REC.EVTIDNNO  := r_bansf_rec_rec.EVTIDNNO; -- 身份證號;
                                --     R_Badapr_Rec.Evtname       :=r_bansf_rec_rec.Evtname;     --'事故者姓名';
                            END IF;
                            --36  20130320
                            V_Age := F_Year(Substr(R_Badapr_Rec.paydate, 1, 6),
                                            Substr(R_Badapr_Rec.Evtbrdate, 1, 6)); --'核付年齡';

                            CASE
                                WHEN SUBSTR(R_BADAPR_REC.EVTIDNNO, 2, 1) = '1' THEN
                                    R_BADAPR_REC.SEX := '1';
                                WHEN SUBSTR(R_BADAPR_REC.EVTIDNNO, 2, 1) = '2' THEN
                                    R_BADAPR_REC.SEX := '2';
                                ELSE
                                    R_BADAPR_REC.SEX := 'Z';
                            END CASE;
                            V_Key_Idn    := R_BADAPR_REC.EVTIDNNO;
                            v_key_name   := R_Badapr_Rec.Evtname;
                            V_Key_Intyp  := 'L';
                            v_key_brdate := R_BADAPR_REc.EVTBRDATE;

                            P_READ_CIPB;
                            --36國併勞：計算申請日-生日       20130320
                            R_Badapr_Rec.AGE    := F_YEAR(SUBSTR(R_BADAPR_REC.appdate,
                                                                 1,
                                                                 6),
                                                          SUBSTR(R_BADAPR_REc.EVTBRDATE,
                                                                 1,
                                                                 6)); --'年齡';
                            R_BADAPR_REC.HBEDMK := R_CIPB_REC.HBEDMK; --'年金施行前有無保險年資';
                            R_BADAPR_REC.NITRMY := R_CIPB_REC.NITRMY; --勞保投保年資(年-年金制)
                            R_BADAPR_REC.NITRMM := R_CIPB_REC.NITRMM; --勞保投保年資(月-年金制)
                        ELSE
                            P_READ_CIPB;
                            R_BADAPR_REC.HBEDMK := R_CIPB_REC.HBEDMK; --'年金施行前有無保險年資';
                            R_BADAPR_REC.NITRMY := R_CIPB_REC.NITRMY; --勞保投保年資(年-年金制)
                            R_BADAPR_REC.NITRMM := R_CIPB_REC.NITRMM; --勞保投保年資(月-年金制)
                        END IF;
                        --R_Badapr_Rec.Adjym        :=R_Cipb_Rec.Adjym;     --'投保薪資調整年月';
                        --R_Badapr_Rec.Adjmk        :=R_Cipb_Rec.Adjmk;     --'逕調註記';
                        --R_Badapr_Rec.Ciid        :=R_Cipb_Rec.CIID;     --'勞就保識別碼';
                        R_Badapr_Rec.OLDAB         := NULL; --第一式/第二式
                        R_Badapr_Rec.OLDAAMT       := 0; --第一式金額(勞保給付金額)
                        R_Badapr_Rec.OLDBAMT       := 0; --第二式金額(勞保給付金額)
                        R_Badapr_Rec.OLDEXTRARATE  := 0; --(老年、遺屬)展延/減額比率
                        R_Badapr_Rec.QUALCOUNT     := 0; --符合眷屬(遺屬)人數
                        R_Badapr_Rec.Oldrate       := 0; --加計比率
                        R_Badapr_Rec.CLOSECAUSE    := 'Z9'; --結案原因
                        R_BADAPR_REC.ANNUAMT       := 0; --累計已領年金金額
                        R_Badapr_Rec.Cutamt        := 0; --應扣失能金額
                        R_Badapr_Rec.LECOMAMT      := 0; --己扣失能金額
                        R_Badapr_Rec.Benids        := Null; --受益人社福識別碼
                        R_Badapr_Rec.BENAGE        := 0; --受益人單齡
                        R_Badapr_Rec.BENSEX        := nvl(R_Bansf_Rec_Rec.Sex, 'Z'); --受益人性別
                        R_Badapr_Rec.Bennationtyp  := 'C'; --受益人國籍別
                        R_Badapr_Rec.Bennationcode := null; --受益人國籍
                        R_Badapr_Rec.Benevtrel     := Null; --受益人與事故者關係
                        V_Bdate                    := F_Bdate(R_Badapr_Rec.Apno,
                                                              R_Badapr_Rec.Seqno,
                                                              R_Badapr_Rec.Payym,
                                                              R_Badapr_Rec.Payno);
                        V_EDATE                    := '00000';

                        V_OUT_CNT := V_OUT_CNT + 1;

                        Insert Into bansf
                            (Select R_Badapr_Rec.Paydate,
                                    R_Badapr_Rec.Appdate,
                                    R_Badapr_Rec.Issuym,
                                    R_Badapr_Rec.Payym,
                                    R_Badapr_Rec.Apno,
                                    '0000',
                                    R_Badapr_Rec.Edate,
                                    R_Badapr_Rec.Ubno,
                                    R_Bansf_Rec_Rec.Ubno,
                                    R_Badapr_Rec.Ubtype,
                                    R_Badapr_Rec.Inds,
                                    R_Badapr_Rec.Hincd,
                                    R_Badapr_Rec.Idsta,
                                    R_Badapr_Rec.Area,
                                    R_Badapr_Rec.Clsqty,
                                    R_Badapr_Rec.Sex,
                                    V_Age,
                                    R_Badapr_Rec.Age,
                                    V_Str2,
                                    R_Badapr_Rec.Evtnationtpe,
                                    R_Badapr_Rec.Payno,
                                    decode(R_Badapr_Rec.Nitrmy,
                                           null,
                                           R_BANSF_REC_REC.NITRMY),
                                    decode(R_Badapr_Rec.Nitrmm,
                                           null,
                                           R_BANSF_REC_REC.NITRMm),
                                    R_Badapr_Rec.Evtype,
                                    R_Badapr_Rec.Evcode,
                                    R_Badapr_Rec.Injname,
                                    R_Badapr_Rec.Injno,
                                    R_Badapr_Rec.Injdp,
                                    R_Badapr_Rec.Injpart,
                                    R_Badapr_Rec.Medium,
                                    R_Badapr_Rec.Injcl,
                                    R_Badapr_Rec.Apitem,
                                    R_Badapr_Rec.Chkkind,
                                    R_Badapr_Rec.Pwage,
                                    R_Badapr_Rec.Wage,
                                    R_Badapr_Rec.Paycnt,
                                    R_Badapr_Rec.Suprecmk,
                                    R_Badapr_Rec.Pamts,
                                    R_Badapr_Rec.Adwkmk,
                                    R_Badapr_Rec.Mchktyp,
                                    R_Badapr_Rec.Oldab,
                                    R_Badapr_Rec.Oldaamt,
                                    R_Badapr_Rec.Oldbamt,
                                    R_Badapr_Rec.Oldextrarate,
                                    R_Badapr_Rec.Qualcount,
                                    R_Badapr_Rec.Oldrate,
                                    R_Badapr_Rec.Closecause,
                                    R_Badapr_Rec.Annuamt,
                                    R_Badapr_Rec.Hbedmk,
                                    R_Badapr_Rec.Nachgmk,
                                    R_Badapr_Rec.Cutamt,
                                    R_Badapr_Rec.Lecomamt,
                                    R_Badapr_Rec.Paykind,
                                    Decode(Length(R_Badapr_Rec.Evtidnno),
                                           11,
                                           Substr(R_Badapr_Rec.Evtidnno, 1, 10),
                                           R_Badapr_Rec.Evtidnno),
                                    Decode(Length(R_Badapr_Rec.Evtidnno),
                                           11,
                                           Substr(R_Badapr_Rec.Evtidnno, 10, 1),
                                           Null),
                                    R_Badapr_Rec.Evtbrdate,
                                    R_Badapr_Rec.Evtname,
                                    V_Edate,
                                    V_Bdate,
                                    v_Evtdiedate,
                                    r_cipb_rec.ADJYM,
                                    r_cipb_rec.ADJMK,
                                    V_Str3,
                                    R_Badapr_Rec.Firstpay,
                                    null,
                                    0,
                                    V_Cat,
                                    0,
                                    0,
                                    null,
                                    '',
                                    to_Char(Sysdate,'YYYYMMDDHH24MISS')
                               From Dual);
                        --2014/04/28  DEFAULT SEQNO=0000
                        IF SUBSTR(R_Badapr_Rec.Payno, 1, 1) = '3' AND nvl(v_Evtdiedate,' ') <> ' ' THEN
                            Insert Into bansf_TEMP
                                (Select R_Badapr_Rec.Paydate,
                                        R_Badapr_Rec.Appdate,
                                        R_Badapr_Rec.Issuym,
                                        R_Badapr_Rec.Payym,
                                        R_Badapr_Rec.Apno,
                                        '0000',
                                        R_Badapr_Rec.Edate,
                                        R_Badapr_Rec.Ubno,
                                        R_Bansf_Rec_Rec.Ubno,
                                        R_Badapr_Rec.Ubtype,
                                        R_Badapr_Rec.Inds,
                                        R_Badapr_Rec.Hincd,
                                        R_Badapr_Rec.Idsta,
                                        R_Badapr_Rec.Area,
                                        R_Badapr_Rec.Clsqty,
                                        R_Badapr_Rec.Sex,
                                        V_Age,
                                        R_Badapr_Rec.Age,
                                        V_Str2,
                                        R_Badapr_Rec.Evtnationtpe,
                                        R_Badapr_Rec.Payno,
                                        decode(R_Badapr_Rec.Nitrmy,
                                               null,
                                               R_BANSF_REC_REC.NITRMY),
                                        decode(R_Badapr_Rec.Nitrmm,
                                               null,
                                               R_BANSF_REC_REC.NITRMm),
                                        R_Badapr_Rec.Evtype,
                                        R_Badapr_Rec.Evcode,
                                        R_Badapr_Rec.Injname,
                                        R_Badapr_Rec.Injno,
                                        R_Badapr_Rec.Injdp,
                                        R_Badapr_Rec.Injpart,
                                        R_Badapr_Rec.Medium,
                                        R_Badapr_Rec.Injcl,
                                        R_Badapr_Rec.Apitem,
                                        R_Badapr_Rec.Chkkind,
                                        R_Badapr_Rec.Pwage,
                                        R_Badapr_Rec.Wage,
                                        R_Badapr_Rec.Paycnt,
                                        R_Badapr_Rec.Suprecmk,
                                        R_Badapr_Rec.Pamts,
                                        R_Badapr_Rec.Adwkmk,
                                        R_Badapr_Rec.Mchktyp,
                                        R_Badapr_Rec.Oldab,
                                        R_Badapr_Rec.Oldaamt,
                                        R_Badapr_Rec.Oldbamt,
                                        R_Badapr_Rec.Oldextrarate,
                                        R_Badapr_Rec.Qualcount,
                                        R_Badapr_Rec.Oldrate,
                                        R_Badapr_Rec.Closecause,
                                        R_Badapr_Rec.Annuamt,
                                        R_Badapr_Rec.Hbedmk,
                                        R_Badapr_Rec.Nachgmk,
                                        R_Badapr_Rec.Cutamt,
                                        R_Badapr_Rec.Lecomamt,
                                        R_Badapr_Rec.Paykind,
                                        Decode(Length(R_Badapr_Rec.Evtidnno),
                                               11,
                                               Substr(R_Badapr_Rec.Evtidnno,
                                                      1,
                                                      10),
                                               R_Badapr_Rec.Evtidnno),
                                        Decode(Length(R_Badapr_Rec.Evtidnno),
                                               11,
                                               Substr(R_Badapr_Rec.Evtidnno,
                                                      10,
                                                      1),
                                               Null),
                                        R_Badapr_Rec.Evtbrdate,
                                        R_Badapr_Rec.Evtname,
                                        V_Edate,
                                        V_Bdate,
                                        v_Evtdiedate,
                                        r_cipb_rec.ADJYM,
                                        r_cipb_rec.ADJMK,
                                        V_Str3,
                                        R_Badapr_Rec.Firstpay,
                                        null,
                                        0,
                                        V_Cat,
                                        0,
                                        0,
                                        null
                                   From Dual);
                        END IF;
                        Insert Into Badapr_Ref Values R_Badapr_Rec;

                        IF (Substr(R_Badapr_Rec.Payno, 1, 1) = '5' Or
                           R_Badapr_Rec.Payno In ('35', '37', '38')) and
                           f_bansfcount(r_badapr_rec.Apno,
                                        r_badapr_rec.Payym,
                                        R_Badapr_Rec.Suprecmk) > 0 Then
                            --PAYDATE=CASE WHEN R_SOURCE_BAAPPBASE_REC.PAYDATE IS NULL THEN R_SOURCE_BAAPPBASE_REC.PAYDATE ELSE PAYDATE END,
                            p_read_baappbase;
                            IF sw_found = true THEN
                                R_Badapr_Rec.Seqno := '0000'; --'序號';
                                Update_Data;

                                UPDATE BANSF
                                   SET Evtidnno     = R_Baappbase_Rec.Evtidnno, --'事故者身份證號';
                                       EVTBRDATE    = R_Baappbase_Rec.EVTBRDATE, --'事故者出生日期';
                                       Evtname      = R_Baappbase_Rec.Evtname, --'事故者姓名';
                                       Appdate      = R_Baappbase_Rec.Appdate, --'申請日期';
                                       EVDATE       = R_Baappbase_Rec.EVTDATE, --事故日期;
                                       LSUBNO       = R_Baappbase_Rec.LSUBNO,
                                       PAYAGE       = V_AGE,
                                       AGE          = R_Badapr_Rec.AGE,
                                       EVTNATIONTPE = R_Baappbase_Rec.EVTNATIONTPE, --'事故者國籍別';
                                       EVCODE       = R_Badapr_Rec.EVCODE,
                                       INJNAME      = R_Badapr_Rec.INJNAME,
                                       INJNO        = R_Badapr_Rec.INJNO,
                                       INJDP        = R_Badapr_Rec.INJDP,
                                       INJPART      = R_Badapr_Rec.INJPART,
                                       MEDIUM       = R_Badapr_Rec.MEDIUM,
                                       INJCL        = R_Badapr_Rec.INJCL,
                                       APITEM       = R_Baappbase_Rec.APITEM,
                                       CHKKIND      = R_Badapr_Rec.CHKKIND,
                                       WAGE         = nvl(R_Baappbase_Rec.Lsinsmamt,
                                                          0),
                                       CLOSECAUSE   = R_Baappbase_Rec.CLOSECAUSE,
                                       CUTAMT       = R_Baappbase_Rec.CUTAMT,
                                       LECOMAMT     = R_Badapr_Rec.LECOMAMT,
                                       EVTDIEDATE   = R_Baappbase_Rec.EVTDIEDATE,
                                       adjym        = R_Cipb_Rec.ADJYM,
                                       adjmk        = R_Cipb_Rec.ADJMK,
                                       ciid         = R_Cipb_Rec.CIID
                                 Where Apno = r_badapr_rec.Apno
                                   And Payym = r_badapr_rec.Payym
                                   and ((R_Badapr_Rec.Suprecmk is null and
                                       code is null) or
                                       code = R_Badapr_Rec.Suprecmk);

                                --2014/04/28
                                IF R_Baappbase_Rec.EVTDIEDATE is NOT NULL THEN
                                    UPDATE BANSF_TEMP
                                       SET EVTDIEDATE = R_Baappbase_Rec.EVTDIEDATE
                                     Where Apno = r_badapr_rec.Apno
                                       And Payym = r_badapr_rec.Payym
                                       and ((R_Badapr_Rec.Suprecmk is null and
                                           code is null) or
                                           code = R_Badapr_Rec.Suprecmk);
                                END IF;
                            end if;
                        end if;
                    END IF;

                    V_Payym  := R_Badapr_Rec.Payym;
                    V_Iisuym := R_Badapr_Rec.Issuym;
                    V_APNO   := R_Badapr_Rec.APNO;
                    If Mod(V_Out_Cnt, 50000) = 0 Then
                        Commit;
                    END IF;
                End Loop;
            EXCEPTION
                WHEN OTHERS Then
                    DBMS_OUTPUT.put_line(v_key_APNO);
                    p_return_code   := p_return_code || v_key_APNO || ',';
                    V_Exception_Cnt := V_Exception_Cnt + 1;
                    DBMS_OUTPUT.put_line('p_dwamf_old_proc發生錯誤,錯誤代碼=' ||
                                         sqlcode || ',' || '錯誤訊息=' || SQLERRM);
                    DBMS_OUTPUT.put_line('fn_execute:EXCEPTION==>');
                    DBMS_OUTPUT.put_line('error' || SQLERRM);
                    DBMS_OUTPUT.put_line('error==>' ||
                                         DBMS_UTILITY.format_error_backtrace);
                    p_return_code := p_return_code ||
                                     'p_dwamf_old_proc發生錯誤,錯誤代碼=' || sqlcode || ',' ||
                                     '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_01：統計資料轉入發生錯誤，APNO='||v_key_APNO||'***(E08)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;
            -- ============================================================================
            --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
            -------------------------------------------------------------------------------
        Begin
            DBMS_OUTPUT.put_line('勞保年金統計核定檔資料轉入');
            p_return_code := p_return_code || '勞保年金統計核定檔資料轉入' || ',';
            --   p_return_code := 'OK';  --回傳訊息
            --初期處理------------------------------------------------------
            BEGIN
                -- 檢查參數內容
                -- 處理年月
                Dbms_Output.Put_Line('處理年月=' || P_Payym);
                p_return_code := p_return_code || '處理年月=' || P_Payym || ',';
                --v_format_date := TO_DATE(P_Payym || '01', 'YYYYMMDD');
            EXCEPTION
                WHEN OTHERS THEN
                    DBMS_OUTPUT.put_line('輸入參數資料錯誤EXCEPTION！');
                    p_return_code := p_return_code || '輸入參數資料錯誤EXCEPTION！' || ',';
                    --        p_return_code := 'NO';  --回傳訊息
                    Dbms_Output.Put_Line('初期處理失敗：錯誤代碼=' || Sqlcode || ' ， ' ||
                                         '錯誤訊息=' || Sqlerrm);
                    p_return_code := p_return_code || '初期處理失敗：錯誤代碼=' || Sqlcode ||
                                     ' ， ' || '錯誤訊息=' || Sqlerrm || ',';
                    COMMIT;
                    Return;
            END;
            --主程序處理=========================================================================

            --20190827 Add by Angela RecLog Start
            v_o_flag := '0';
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_01)：勞保年金統計核定檔及勞保年金統計檔轉入(失能、遺屬)',
                            p_memo   => '開始時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 '0',
                                 '(DB)SP_BANSF_01：勞保年金統計核定檔及勞保年金統計檔轉入(失能、遺屬)(開始)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            --20190827 Add by Angela RecLog End

            --1.刪除給付年月資料
            DBMS_OUTPUT.put_line('1.刪除給付年月資料');
            p_return_code := p_return_code || '1.刪除給付年月資料' || ',';
            If P_Payym = '201208' Then
                -- p_delete_data;
                COMMIT;
            end IF;

            --2.勞保年金統計核定檔BADAPR_REF及勞保年金統計檔BANSF轉入
            Dbms_Output.Put_Line('2.勞保年金統計核定檔及勞保年金統計檔轉入' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
            p_return_code := p_return_code || '2.勞保年金統計核定檔及勞保年金統計檔轉入' ||
                             To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            p_badapr_proc;

            If V_Exception_Cnt > 0 Then
                raise_application_error(-20001,'勞保年金統計核定檔及勞保年金統計檔處理失敗！');
                v_o_flag := '1';
            ELSE
                COMMIT;
            END IF;

            --==========================================================================================

            --結束處理
            --DBMS_OUTPUT.put_line('EXCEPTION錯誤筆數＝'||v_exception_cnt);
            IF v_exception_cnt > 0 THEN
                Rollback;
                --      P_Return_Code := 'NO';  --回傳訊息
                DBMS_OUTPUT.put_line('產生勞保年金統計核定檔及勞保年金統計檔失敗！' ||
                                     To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || '產生勞保年金統計核定檔及勞保年金統計檔失敗！' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
                v_o_flag := '1';
            Else
                DBMS_OUTPUT.put_line('產生勞保年金統計核定檔及勞保年金統計檔成功！' ||
                                     To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || '產生勞保年金統計核定檔及勞保年金統計檔成功！' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            END IF;
            COMMIT;

            --20190827 Add by Angela RecLog Start
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_01)：勞保年金統計核定檔及勞保年金統計檔轉入(失能、遺屬)',
                            p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:('||v_o_flag||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 v_o_flag,
                                 '(DB)SP_BANSF_01：勞保年金統計核定檔及勞保年金統計檔轉入(失能、遺屬)(結束)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

            if v_o_flag <> '0' then
                v_o_flag := 'N';
            else
                v_o_flag := 'E';
            end if;
            --20190827 Add by Angela RecLog End;

            p_output_message; --輸出結果
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                DBMS_OUTPUT.put_line('勞保年金統計核定檔失敗！' || SQLCODE || ' - ' ||
                                     SQLERRM);
                p_return_code := p_return_code || '勞保年金統計核定檔失敗！' || SQLCODE ||
                                 ' - ' || SQLERRM || ',';
                --      p_return_code := 'NO';  --回傳訊息
                p_output_message; --輸出結果
                COMMIT;

                --20190827 Add by Angela RecLog Start
                SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                                p_job_id => 'PG_BANSF_01.SP_BANSF',
                                p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_01)：勞保年金統計核定檔及勞保年金統計檔轉入(失能、遺屬)',
                                p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:(1)');

                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '1',
                                     '(DB)SP_BANSF_01：勞保年金統計核定檔及勞保年金統計檔轉入(失能、遺屬)(結束)',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

                v_o_flag := 'N';
                --20190827 Add by Angela RecLog End;
        End;
    End SP_BANSF_01;
                 
    Procedure SP_BANSF_02(
        P_Insuym      In Varchar2, --處理年月
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    ) Is
    Begin
        -- 修改紀錄：
        -- 維護人員        日期       說明
        -- -----------   --------  ----------------------------------------------------
        -- rose          20120906   v1.0
        -- ============================================================================
        --                          <<<<<<<<< 變數區 >>>>>>>>>>
        -------------------------------------------------------------------------------
        Declare
            R_Bansf_Rec       Bansf%Rowtype; --badapr_ref RECORD
            R_Baappbase_Rec   Baappbase%Rowtype; --BAAPPBASE RECORD   --2013/06/04
            --R_Baappexpand_Rec Baappexpand%Rowtype; --BAAPPEXPAND RECORD
            R_CIPB_Rec        ba.cipb%Rowtype; --BAAPPEXPAND RECORD
            R_ciCIPB_Rec      ci.cipb%Rowtype; --BAAPPEXPAND RECORD  --    ci.cipb
            R_Caub_Rec        caub%Rowtype; --STAT RECORDc  --dss.caub
            -- ============================================================================
            sw_found BOOLEAN := TRUE; --找到資料
            -- ============================================================================
            -------------------------------------------------------------------------------
            V_Out_Cnt           Number(10) := 0; --輸出新增筆數
            v_del_cnt           NUMBER(10) := 0; --刪除筆數
            V_Key_Ubno          Bansf.Ubno%Type;
            V_Key_Baappbaseid   Number(20); --給付主檔資料編號
            V_Key_Apno          Bansf.Apno%Type; --受理編號
            V_Key_SEQNO         BADAPR.SEQNO%Type; --序號
            V_Key_Idn           Varchar2(11 Byte); --身份證號
            V_Key_Intyp         varchar(1 byte); --保險別
            V_Key_Name          Bansf.Evtname%Type; --姓名
            v_key_brdate        Varchar2(8 Byte); --生日
            V_Exception_Cnt     Integer := 0; --EXCEPTION錯誤筆數
            --V_Format_Date       Date; --DATE格式欄位
            v_iisuym            varchar(6 byte);
            v_notfound_cipb_cnt NUMBER(10) := 0; --找不到CIPB筆數
            V_TOOMANY_CIPB_CNT  NUMBER(10) := 0; --找到太多CIPB筆數
            V_OTHER_CIPB_CNT    NUMBER(10) := 0; --找到ERR CIPB筆數
            V_Edate             Varchar2(6 Byte);

            -- ============================================================================
            -- CURSOR C_BANSF_REF
            -------------------------------------------------------------------------------
            --處理年月=201208,則全量資料取入,否則,取回給付年月payym=處理年月P_Payym
            CURSOR C_BADAPR_REF IS
                select A.*,
                       B.APNO as APNO_BA,
                       B.Appdate,
                       B.Evtjobdate,
                       b.apubno,
                       b.LSUBNO,
                       b.EVTSEX,
                       b.Evtage,
                       b.Evtnationtpe,
                       b.Closecause,
                       case
                           when A.REMAINAMT is null then
                            0
                           else
                            A.REMAINAMT
                       end Cutamt,
                       B.Evtidnno,
                       B.Evtbrdate,
                       b.Evtname,
                       B.Evtdiedate,
                       b.Casetyp,
                       b.Apitem,
                       B.Lsinsmamt,
                       B.Paydate,
                       C.Evtyp,
                       C.Evcode,
                       C.Criinjnme1,
                       C.Criinjdp1,
                       C.criinpart1,
                       C.Crimedium,
                       C.Criinissul,
                       C.CRIINJCL1,
                       C.CRIINJCL2,
                       c.CRIINJCL3,
                       C.Ocaccidentmk,
                       case
                           when C.adwkmk is null then
                            '1'
                           else
                            c.adwkmk
                       end as adwkmk_ex
                  from (Select *
                          From Badapr
                         Where issuym < '201312'
                           and PAYKIND IN ('45', '48')
                           AND mtestmk = 'F'
                           AND Aplpaymk = '3'
                           and P_Insuym = '201208'
                           and Benevtrel <> 'F'
                           and Benevtrel <> 'N'
                           and Benevtrel <> 'Z'
                        union all
                        Select *
                          From Badapr
                         Where P_Insuym <> '201208'
                           and substr(Aplpaydate, 1, 6) = P_Insuym
                           and PAYKIND IN ('45', '48')
                           AND mtestmk = 'F'
                           AND Aplpaymk = '3'
                           and Benevtrel <> 'F'
                           and Benevtrel <> 'N'
                           and Benevtrel <> 'Z') A,
                       baappbase B,
                       baappexpand C
                 where A.BAAPPBASEID = B.Baappbaseid(+)
                   and A.BAAPPBASEID = C.Baappbaseid(+)
                   and A.BAAPPBASEID = C.Baappbaseid(+)
                 Order By (Case
                              When Suprecmk is Null then
                               '0'
                              when Suprecmk = 'C' Then
                               '1'
                              Else
                               '2'
                          End),
                          A.Apno,
                          substr(Aplpaydate, 1, 6),
                          A.Payym,
                          NVL(BEFISSUEAMT, 0) desc;

            Cursor C_Bansf Is
                Select *
                  From Bansf
                 Where (P_Insuym = '201208' Or Issuym = P_Insuym)
                   And (Bdate Is Null Or Edate Is Null or ciid is null);

            -- ============================================================================
            -- procedure
            -------------------------------------------------------------------------------
            -- 輸出結果
            PROCEDURE p_output_message IS
            BEGIN
                DBMS_OUTPUT.PUT_LINE('異常cipb資料=' ||
                                     TO_CHAR(V_NOTFOUND_CIPB_CNT +
                                             V_TOOMANY_CIPB_CNT +
                                             V_OTHER_CIPB_CNT));
                p_return_code := p_return_code || '異常cipb資料=' ||
                                 TO_CHAR(V_NOTFOUND_CIPB_CNT +
                                         V_TOOMANY_CIPB_CNT + V_OTHER_CIPB_CNT) || ',';
                Dbms_Output.Put_Line('新增bansf筆數=' || V_Out_Cnt);
                p_return_code := p_return_code || '新增bansf筆數=' || V_Out_Cnt || ',';
            End;
            -------------------------------------------------------------------------------
            -- 刪除計費年月資料
            -------------------------------------------------------------------------------
            PROCEDURE p_delete_data IS
            Begin
                --刪除BANSF、BADAPR_REF處理當月資料
                Delete From Bansf
                 WHERE (P_Insuym = '201208' or payym = P_Insuym)
                   and substr(payno, 1, 1) = '4'; --and APNO in ('L10000000181','L20000000077','L20000000613');
                Delete From Badapr_Ref
                 WHERE (P_Insuym = '201208' or payym = P_Insuym)
                   and substr(payno, 1, 1) = '4'; --and APNO in ('L10000000181','L20000000077','L20000000613');
                V_Del_Cnt := 0; --刪除筆數
                v_del_cnt := v_del_cnt + SQL%ROWCOUNT; --刪除筆數
            EXCEPTION
                WHEN OTHERS THEN
                    v_exception_cnt := v_exception_cnt + 1;
                    DBMS_OUTPUT.put_line('p_delete_data發生錯誤,錯誤代碼=' || sqlcode || ',' ||
                                         '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code || 'p_delete_data發生錯誤,錯誤代碼=' ||
                                     sqlcode || ',' || '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_02：刪除勞保統計檔(BANSF、BADAPR_REF)(以處理年月為條件)發生錯誤***(E09)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;
            ------------------------------------------------------------
            --取回INJDP障礙(失能部位)
            function F_injdp(S_key_INJNO In Varchar2) Return BCA_D_INJD.INJDP%TYPE Is
                RESULT BCA_D_INJD.INJDP%TYPE;
            Begin
                /*條件:INSKD  保險別='1' and INJNO  障礙項目(分左右)代碼=INJNO
                payym給付年月介於EFDTE生效日及失效日之間*/
                SELECT INJDP
                  Into Result
                  From Bca_D_Injd
                 Where INSKD = '1'
                   and INJNO = S_key_INJNO
                   And P_Insuym || '01' >= EFDTE
                   and P_Insuym || '01' <= EXDTE
                   AND ROWNUM < 2;
                RETURN RESULT;
            EXCEPTION
                When No_Data_Found Then
                    RETURN 0;
                When Others Then
                    RETURN 0;
            End;
            -------------------------------------------------------
            --取回v_bdate開始請領日期
            Function F_bdate(S_Key_Apno In Varchar2,
                             s_seqno    in varchar2,
                             S_Payym    In Varchar2/*,
                             s_payno    in varchar2*/) Return BADAPR.PAYYM%type Is
                Result Badapr.Payym%Type;
            BEGIN
                Select Min(C.Payym)
                  INTO RESULT
                  FROM BADAPR C
                 WHERE C.APNO = S_Key_Apno
                   AND C.PAYYM <= S_Payym
                   And C.Mtestmk = 'F'
                   And C.Aplpaymk = '3'
                   And Trim(C.Aplpaydate) Is Not Null
                   AND C.SEQNO = s_seqno;
                return Result;
            EXCEPTION
                When No_Data_Found Then
                    RETURN null;
                When Others Then
                    RETURN null;
            End;
            ----------------------------------------------------------------
            -- 取回被保險人pwage 60個月平均薪資
            ----------------------------------------------------------------
            Function F_pwage(S_Key_Apno In Varchar2,
                             S_Payym    In Varchar2/*,
                             s_payno    in varchar2*/) Return BADAPR.PAYYM%type Is
                Result Badapr.Payym%Type;
            Begin
                Select nvl(Insavgamt, 0) as pwage
                  INTO RESULT
                  FROM BADAPR C
                 WHERE C.APNO = S_Key_Apno
                   And C.Mtestmk = 'F'
                   And C.Aplpaymk = '3'
                   And Trim(C.Aplpaydate) Is Not Null
                   AND c.suprecmk is null
                   AND C.SEQNO = '0000'
                   And C.payym = S_Payym;
                --取整案之最大給付年月
                --Dbms_Output.Put_Line(S_Key_Apno||';'||S_Payym||';'||s_payno||';'||Result);
                return Result;
            EXCEPTION
                When No_Data_Found Then
                    RETURN null;
                When Others Then
                    RETURN null;
            End;
            -------------------------------------------------------
            --取回v_edate結束日期
            -------------------------------------------------------
            Function F_edate(S_Key_Apno In Varchar2/*,
                             s_seqno    in varchar2,
                             S_Payym    In Varchar2,
                             s_payno    in varchar2*/) Return BADAPR.PAYYM%type Is
                Result Badapr.Payym%Type;
            Begin
                Select substr(max(C.Payym), 1, 6)
                  INTO RESULT
                  FROM BADAPR C
                 WHERE C.APNO = S_Key_Apno
                   And C.Mtestmk = 'F'
                   And C.Aplpaymk = '3'
                   And Trim(C.Aplpaydate) Is Not Null
                   AND c.suprecmk is null
                /*AND C.SEQNO='0000'*/
                ;
                --取整案之最大給付年月
                --Dbms_Output.Put_Line(S_Key_Apno||';'||S_Payym||';'||s_payno||';'||Result);

                return Result;
            EXCEPTION
                When No_Data_Found Then
                    RETURN null;
                When Others Then
                    RETURN null;
            End;
            -------------------------------------------------------------------------
            --取回月份相減
            Function F_Year(S_Ym_B In Varchar2, S_Ym_E In Varchar2) Return Number Is
            Begin
                If S_Ym_B Is Not Null And S_Ym_E Is Not Null Then
                    If Substr(S_Ym_E, 5, 2) > Substr(S_Ym_b, 5, 2) Then
                        Return to_number(Substr(S_Ym_b, 1, 4) -
                                         Substr(S_Ym_E, 1, 4) - 1);
                    Else
                        Return to_number(Substr(S_Ym_b, 1, 4) -
                                         Substr(S_Ym_E, 1, 4));
                    end if;
                Else
                    return 0;
                End If;
            EXCEPTION
                When No_Data_Found Then
                    RETURN 0;
                When Others Then
                    Return 0;
            End;
            -------------------------------------------------------------------------------
            --讀取CAUB
            PROCEDURE p_read_caub IS
            BEGIN
                Sw_Found := True;
                Select *
                  Into R_Caub_Rec
                  From caub --2013/06/04 dss.caub
                 Where Ubno = V_Key_Ubno
                   And Rownum < 2;
                --保險證號
            Exception
                WHEN NO_DATA_FOUND THEN
                    r_caub_rec := null;
                    sw_found   := FALSE;
                WHEN OTHERS THEN
                    Sw_Found := False;
                    Dbms_Output.Put_Line('p_read_caub發生錯誤,UBNO=' || V_Key_Ubno ||
                                         ',錯誤代碼=' || Sqlcode || ',' || '錯誤訊息=' ||
                                         Sqlerrm || ',' ||
                                         DBMS_UTILITY.format_error_backtrace);
                    p_return_code := p_return_code || 'p_read_caub發生錯誤,UBNO=' ||
                                     V_Key_Ubno || ',錯誤代碼=' || Sqlcode || ',' ||
                                     '錯誤訊息=' || Sqlerrm || ',' ||
                                     DBMS_UTILITY.format_error_backtrace || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_02：讀取CAUB發生錯誤，UBNO='||V_Key_Ubno||'***(E10)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            End;
            -------------------------------------------------------
            --取回v_bdate開始請領日期
            Function F_bdate_bansf(S_Key_Apno In Varchar2, S_Payym In Varchar2)
                Return BADAPR.PAYYM%type Is
                RESULT BADAPR.PAYYM%type;
            Begin
                --Select Min(C.Payym)
                SELECT CASE
                           WHEN MIN(C.BDATE) < MIN(C.PAYYM) THEN
                            MIN(C.BDATE)
                           ELSE
                            MIN(C.PAYYM)
                       END
                  Into Result
                  From Bansf C
                 Where C.Apno = S_Key_Apno
                   AND C.PAYYM <= S_Payym;
                return Result;
            EXCEPTION
                When No_Data_Found Then
                    RETURN null;
                When Others Then
                    Return Null;
            End;
            -------------------------------------------------------------------------------
            -- 顯示LOG內容
            PROCEDURE log_msg(p_msg IN VARCHAR2) IS
            BEGIN
                IF v_exception_cnt > 100 THEN
                    DBMS_OUTPUT.put_line('EXCEPTION錯誤超過100個！');
                    p_return_code := p_return_code || 'EXCEPTION錯誤超過100個！' || ',';
                    raise_application_error(-20001, 'EXCEPTION錯誤超過100個！');
                END IF;

                DBMS_OUTPUT.put_line(p_msg);
                p_return_code := p_return_code || p_msg || ',';
            End;
            -------------------------------------------------------------------------------
            -- 依身份證號Idno讀取CIPB資料
            PROCEDURE p_read_cipb is
            Begin
                SELECT *
                  INTO r_cipb_rec
                  FROM ba.cipb B --2013/06/04 dss.CIPB
                 WHERE B.Intyp = V_KEY_INTYP
                   AND B.IDN LIKE V_KEY_IDN || '%'
                   AND B.BRDTE = v_key_brdate;
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    V_NOTFOUND_CIPB_CNT := V_NOTFOUND_CIPB_CNT + 1;
                    R_CIPB_REC          := NULL;
                    SW_FOUND            := FALSE;
                WHEN TOO_MANY_ROWS THEN
                    --以 身分證,出生,姓名 讀CIPB取CIID
                    BEGIN
                        SELECT *
                          INTO R_CIPB_REC
                          FROM ba.cipb B
                         WHERE B.intyp = v_key_intyp
                           AND B.IDN like v_key_idn || '%'
                           AND B.BRDTE = v_key_brdate
                           AND B.NAME = v_key_name;
                    EXCEPTION
                        WHEN NO_DATA_FOUND THEN
                            v_notfound_cipb_cnt := v_notfound_cipb_cnt + 1;
                        WHEN TOO_MANY_ROWS THEN
                            v_toomany_cipb_cnt := v_toomany_cipb_cnt + 1;
                        WHEN OTHERS THEN
                            v_other_cipb_cnt := v_other_cipb_cnt + 1;
                    END;
                WHEN OTHERS THEN
                    V_OTHER_CIPB_CNT := V_OTHER_CIPB_CNT + 1;
                    R_Cipb_Rec       := Null;
                    Sw_Found         := False;
                    LOG_MSG('p_read_cipb發生錯誤,idn=' || V_KEY_IDN || ',錯誤代碼=' ||
                            SQLCODE || ',' || '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code || 'p_read_cipb發生錯誤,idn=' ||
                                     V_KEY_IDN || ',錯誤代碼=' || SQLCODE || ',' ||
                                     '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_02：讀取BA.CIPB發生錯誤，IDN='||V_KEY_IDN||'***(E11)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;
            -------------------------------------------------------------------------------
            -- 依身份證號Idno讀取CIPB資料
            PROCEDURE p_read_cicipb is
            Begin
                SELECT *
                  INTO r_cicipb_rec
                  FROM ci.cipb B --2013/06/04 ci.CIPB
                 WHERE B.ftyp = V_KEY_INTYP
                   AND B.IDN LIKE V_KEY_IDN || '%'
                   AND B.BRDTE = v_key_brdate;
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    V_NOTFOUND_CIPB_CNT := V_NOTFOUND_CIPB_CNT + 1;
                    R_ciCIPB_REC        := NULL;
                    SW_FOUND            := FALSE;
                WHEN TOO_MANY_ROWS THEN
                    --以 身分證,出生,姓名 讀CIPB取CIID
                    BEGIN
                        SELECT *
                          INTO R_ciCIPB_REC
                          FROM ci.cipb B --2013/06/04 ci.cipb
                         WHERE B.ftyp = v_key_intyp
                           AND B.IDN like v_key_idn || '%'
                           AND B.BRDTE = v_key_brdate
                           AND B.NAME = v_key_name;
                    EXCEPTION
                        WHEN NO_DATA_FOUND THEN
                            v_notfound_cipb_cnt := v_notfound_cipb_cnt + 1;
                        WHEN TOO_MANY_ROWS THEN
                            v_toomany_cipb_cnt := v_toomany_cipb_cnt + 1;
                        WHEN OTHERS THEN
                            v_other_cipb_cnt := v_other_cipb_cnt + 1;
                    END;
                WHEN OTHERS THEN
                    V_OTHER_CIPB_CNT := V_OTHER_CIPB_CNT + 1;
                    R_ciCipb_Rec     := Null;
                    Sw_Found         := False;
                    LOG_MSG('p_read_cipb發生錯誤,idn=' || V_KEY_IDN || ',錯誤代碼=' ||
                            SQLCODE || ',' || '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code || 'p_read_cipb發生錯誤,idn=' ||
                                     V_KEY_IDN || ',錯誤代碼=' || SQLCODE || ',' ||
                                     '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_02：讀取CI.CIPB發生錯誤，IDN='||V_KEY_IDN||'***(E12)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;
            -------------------------------------------------------------------------------
            -- 依給付主檔資料編號Baappbaseid取得BAAPPBASE資料
            PROCEDURE p_read_baappbase_D IS
            BEGIN
                sw_found := TRUE;

                --SELECT distinct *
                SELECT *
                  INTO r_baappbase_rec
                  FROM BAAPPBASE A
                 WHERE A.baappbaseid = v_key_baappbaseid;
                --apno = v_key_apno and seqno='0000';
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    sw_found := FALSE;
                WHEN OTHERS THEN
                    sw_found        := FALSE;
                    v_exception_cnt := v_exception_cnt + 1;
                    log_msg('p_read_baappbase發生錯誤,APNO=' || v_key_apno ||
                            ',錯誤代碼=' || sqlcode || ',' || '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code ||
                                     'p_read_baappbase發生錯誤,APNO=' || v_key_apno ||
                                     ',錯誤代碼=' || sqlcode || ',' || '錯誤訊息=' ||
                                     SQLERRM || ',';

                --20190827 Add by Angela RecLog Start
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '1',
                                     '(DB)SP_BANSF_01：讀取勞保給付主檔一(BAAPPBASE)發生錯誤，APNO='||v_key_apno||'***(E13)',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                --20190827 Add by Angela RecLog End;
            End;
            ------------------------------------------------------------------------------
            --設定年齡組別
            Function F_Agetype(S_Age In Number) Return bansf.agetype%Type Is
                RESULT bansf.agetype%TYPE;
            Begin
                Case
                    When S_Age < 15 Then
                        Result := '01';
                    When S_Age >= 15 And S_Age <= 19 Then
                        Result := '02';
                    When S_Age >= 20 And S_Age <= 24 Then
                        Result := '03';
                    When S_Age >= 25 And S_Age <= 29 Then
                        Result := '04';
                    When S_Age >= 30 And S_Age <= 34 Then
                        Result := '05';
                    When S_Age >= 35 And S_Age <= 39 Then
                        Result := '06';
                    When S_Age >= 40 And S_Age <= 44 Then
                        Result := '07';
                    When S_Age >= 45 And S_Age <= 49 Then
                        Result := '08';
                    When S_Age >= 50 And S_Age <= 54 Then
                        Result := '09';
                    When S_Age >= 55 And S_Age <= 59 Then
                        Result := '10';
                    When S_Age >= 60 And S_Age <= 64 Then
                        Result := '11';
                    When S_Age >= 65 And S_Age <= 69 Then
                        Result := '12';
                    When S_Age >= 70 And S_Age <= 74 Then
                        Result := '13';
                    When S_Age >= 75 And S_Age <= 79 Then
                        Result := '14';
                    When S_Age >= 80 And S_Age <= 84 Then
                        Result := '15';
                    When S_Age >= 85 And S_Age <= 89 Then
                        Result := '16';
                    Else
                        Result := '17';
                end case;
                Return Result;
            End;
            -------------------------------------------------------------------------------
            Procedure P_Result_Proc Is
            Begin
                For C_Bansf_Ref In C_Bansf Loop
                    C_Bansf_Ref.Bdate := F_Bdate_Bansf(C_Bansf_Ref.Apno,
                                                       C_Bansf_Ref.Payym);
                    --結束請領年月
                    /*If C_Bansf_Ref.Edate='00000' Then
                      C_Bansf_Ref.EDATE:=C_Bansf_Ref.EDATE;
                    Else
                      C_Bansf_Ref.Edate:= C_Bansf_Ref.Bdate;
                    End If;*/
                    if C_Bansf_Ref.ciid is null then
                        r_cicipb_rec := null;
                        If C_Bansf_Ref.Adwkmk = '2' Then
                            V_Key_Intyp := 'V';
                        Else
                            V_Key_Intyp := 'L';
                        end if;
                        v_key_idn    := C_Bansf_Ref.Evtidnno;
                        v_key_brdate := C_Bansf_Ref.Evtbrdate;
                        v_key_name   := C_Bansf_Ref.Evtname;
                        p_read_cicipb;
                    end if;

                    Update Bansf
                       Set Bdate = C_Bansf_Ref.Bdate /*,Edate=C_Bansf_Ref.Edate*/,
                           ciid  = decode(ciid, null, r_cicipb_rec.ciid, ciid)
                     Where Apno = C_Bansf_Ref.Apno
                       And Payym = C_Bansf_Ref.Payym
                       and ((C_bansf_ref.Code is null and
                           C_BANSF_REF.CODE is null) or
                           code = C_bansf_ref.Code);

                    Commit;
                    --Dbms_Output.Put_Line('成功');
                End Loop;
            End;
            -------------------------------------------------------------------------------
            PROCEDURE update_data IS
            Begin
                --取得主檔資料
                R_Bansf_Rec.Appdate := R_Baappbase_Rec.Appdate; --'申請日期';
                R_Bansf_Rec.Evdate  := R_Baappbase_Rec.Evtjobdate; --事故日期;
                R_Bansf_Rec.Ubno    := R_Baappbase_Rec.apubno;
                R_BANSF_REC.LSUBNO  := R_BAAPPBASE_REC.LSUBNO;
                IF R_BANSF_REC.UBNO IS NULL THEN
                    R_BANSF_REC.Ubno := R_BAAPPBASE_REC.LSUBNO;
                END IF;
                R_Bansf_Rec.sex := R_Baappbase_Rec.EVTSEX; --'事故者性別';

                --20130320 失能及老年年金：計算至核付年月，以申請年月-出生,取年
                R_Bansf_rec.payage := F_Year(substr(R_Bansf_rec.paydate, 1, 6),
                                             Substr(R_Baappbase_Rec.Evtbrdate,
                                                    1,
                                                    6)); --'核付年齡';

                --20130320 失能及老年年金：計算至核付年月，以申請年月-出生,取年
                R_Bansf_Rec.Age := R_Baappbase_Rec.Evtage; --'申請年月-事故者出生年月=申請年齡';
                IF (R_Bansf_Rec.Age IS NULL or R_Bansf_Rec.Age <= 0) AND
                   R_Baappbase_Rec.APPDATE IS NOT NULL AND
                   R_Baappbase_Rec.EVTBRDATE IS NOT NULL THEN
                    R_Bansf_Rec.Age := F_Year(Substr(R_Baappbase_Rec.APPDATE,
                                                     1,
                                                     6),
                                              Substr(R_Baappbase_Rec.EVTBRDATE,
                                                     1,
                                                     6)); --'申請年月-事故者出生年月=申請年齡';
                END IF;
                R_Bansf_rec.agetype := F_Agetype(R_Bansf_rec.payage); --年齡組別
                --'被保險人國籍別';
                If R_Baappbase_Rec.Evtnationtpe = '2' Then
                    R_Bansf_Rec.Evtnationtpe := 'Y';
                Else
                    R_Bansf_Rec.Evtnationtpe := 'C';
                End If;
                --R_Bansf_Rec.Payno         :=R_Baappbase_Rec.Paykind;     --'給付種類';
                R_Bansf_rec.Closecause := R_Baappbase_Rec.Closecause; --'結案原因';
                R_Bansf_Rec.Evtidnno   := R_Baappbase_Rec.Evtidnno; --'事故者身份證號';
                R_Bansf_rec.Evtbrdate  := R_Baappbase_Rec.Evtbrdate; --'事故者出生日期';
                R_Bansf_Rec.Evtname    := R_Baappbase_Rec.Evtname; --'事故者姓名';
                R_Bansf_Rec.Evtdiedate := R_Baappbase_Rec.Evtdiedate; --'死亡日期';
                R_Bansf_Rec.Bdate      := F_Bdate(R_Bansf_Rec.Apno,
                                                  '0000',
                                                  R_Bansf_Rec.Payym/*,
                                                  R_Bansf_Rec.Payno*/); --'開始請領日期';
                v_edate                := F_edate(R_Bansf_Rec.Apno/*,
                                                  '0000',
                                                  R_Bansf_Rec.Payym,
                                                  R_Bansf_Rec.Payno*/); --'結束日期';
                --'結束請領日期';
                --20150616 add casetype in ('3','6')
                If (R_Baappbase_Rec.Casetyp = '4') or
                   ((R_Baappbase_Rec.Casetyp = '3' or
                   R_Baappbase_Rec.Casetyp = '6') and
                   R_Baappbase_Rec.Closedate is not null) Then
                    R_Bansf_Rec.edate := v_Edate;
                else
                    R_Bansf_Rec.Edate := '00000';
                End If;

                R_Bansf_Rec.Apitem := R_Baappbase_Rec.Apitem; --'申請項目';
                R_Bansf_Rec.Wage   := Nvl(R_Baappbase_Rec.Lsinsmamt, 0); --'投保薪資';

                /*         Evtjobdate(事故日期) >= Appdate(申請日期)   請領類別='1'
                Evtjobdate(事故日期) < Appdate(申請日期)    請領類別='2'*/
                If R_Bansf_Rec.Evdate >= R_Bansf_Rec.Appdate Then
                    R_Bansf_Rec.Gettype := '1';
                Else
                    R_Bansf_Rec.Gettype := '2';
                end if;

                --'首發註記';  R_badapr_rec.Aplpaydate前六碼與主檔paydate前六碼一致即視為首發
                --If R_Bansf_Rec.Mchktyp ='1' Then
                if substr(R_Bansf_rec.Paydate, 1, 6) =
                   substr(R_Baappbase_Rec.Paydate, 1, 6) then
                    R_Bansf_Rec.Firstpay := '1';
                Else
                    R_Bansf_Rec.Firstpay := '0';
                End If;

                V_Key_Ubno := R_Bansf_rec.Ubno;

                --取得caub
                P_Read_Caub;
                R_Bansf_rec.UBTYPE := nvl(R_Caub_Rec.UBTYPE, 'Z9'); --'單位類別';
                R_Bansf_rec.Inds   := R_Caub_Rec.Inds; --'小業別';
                R_Bansf_rec.HINCD  := SUBSTR(R_Caub_Rec.HINCD, 1, 2); --'職災代號';
                R_Bansf_rec.IDSTA  := R_Caub_Rec.IDSTA; --'大業別';
                R_Bansf_rec.AREA   := R_Caub_Rec.AREA; --'地區別';
                R_Bansf_rec.CLSQTY := R_Caub_Rec.PRSNO_B; --'月末人數';

                V_Key_Idn    := R_Bansf_Rec.Evtidnno;
                V_Key_Brdate := R_Bansf_Rec.Evtbrdate;
                V_Key_Name   := R_Bansf_Rec.Evtname;
                If R_Bansf_Rec.Adwkmk = '2' Then
                    V_Key_Intyp := 'V';
                Else
                    V_Key_Intyp := 'L';
                end if;

                --取得cipb
                P_Read_Cipb;
                R_Bansf_rec.Hbedmk := R_Cipb_Rec.Hbedmk; --'年金施行前有無保險年資';
                R_Bansf_Rec.Adjym  := R_Cipb_Rec.Adjym; --'投保薪資調整年月';
                R_Bansf_Rec.Adjmk  := R_Cipb_Rec.Adjmk; --'逕調註記';
                R_Bansf_Rec.Ciid   := R_Cipb_Rec.CIID; --'勞就保識別碼';

                /*
                       老年年金 4X
                先判斷有無舊年資=N,無舊年資則現制一次失能金額=0,有舊年資則計算金額(且為勞保者)
                計算方式：
                依CIID讀取dss.CIPB
                計算年資基數(算至小數第2位)：
                   CIPB.NOLDTY >=15 ,年資基數=15+((CIPB.NOLDTY-15)*2)+((CIPB.NOLDTM/12)*2)
                   CIPB.NOLDTY  <15 ,年資基數=(CIPB.NOLDTY*1)+((CIP.NOLDTM/12)*1)
                現制一次老年金額=CIPB.AVGWG平均薪資*年資基數
                       */

                if substr(R_Bansf_Rec.Payno, 1, 1) = '4' then
                    CASE
                        WHEN R_Cipb_Rec.Hbedmk = 'Y' AND R_Cipb_Rec.INTYP = 'L' AND
                             R_Cipb_Rec.Noldty >= 15 THEN
                            R_Bansf_Rec.LUMSUM := R_Cipb_Rec.AVGWG *
                                                  (15 +
                                                  ((R_Cipb_Rec.NOLDTY - 15) * 2) +
                                                  ((R_Cipb_Rec.NOLDTM / 12) * 2));
                        WHEN R_Cipb_Rec.Hbedmk = 'Y' AND R_Cipb_Rec.INTYP = 'L' AND
                             R_Cipb_Rec.Noldty < 15 THEN
                            R_Bansf_Rec.LUMSUM := R_Cipb_Rec.AVGWG *
                                                  ((R_Cipb_Rec.NOLDTY * 1) +
                                                  ((R_Cipb_Rec.NOLDTM / 12) * 1));
                        ELSE
                            R_Bansf_Rec.LUMSUM := 0;
                    END CASE;
                else
                    R_Bansf_Rec.LUMSUM := 0;
                end if;

            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    sw_found := FALSE;
                WHEN OTHERS THEN
                    sw_found := FALSE;
                    Log_Msg('p_read_baappbase發生錯誤,APNO=' || V_Key_Apno ||
                            ',錯誤代碼=' || Sqlcode || ',' || '錯誤訊息=' || Sqlerrm);
                    p_return_code := p_return_code ||
                                     'p_read_baappbase發生錯誤,APNO=' || V_Key_Apno ||
                                     ',錯誤代碼=' || Sqlcode || ',' || '錯誤訊息=' ||
                                     Sqlerrm || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_01：讀取勞保給付主檔二(BAAPPBASE)發生錯誤，APNO='||V_Key_Apno||'***(E14)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            End;
            ---------------------------------------------------------------------------------
            -- 新資料轉入
            -------------------------------------------------------------------------------
            PROCEDURE p_bansf_proc Is
            Begin
                V_Iisuym := Null;
                For r_badapr_rec In C_Badapr_REF Loop
                    R_Bansf_rec := Null;
                    -- if length(r_badapr_rec.APLPAYDATE)='7' then
                    --    R_Bansf_rec.Paydate       :=r_badapr_rec.APLPAYDATE+'19110000';    --'核付日期';
                    -- else
                    R_Bansf_rec.Paydate := r_badapr_rec.APLPAYDATE; --'核付日期';
                    -- end if;
                    R_Bansf_rec.ISSUYM := r_badapr_rec.ISSUYM; --'核定年月';
                    R_Bansf_Rec.Payym  := R_Badapr_Rec.Payym; --'給付年月';
                    v_edate            := R_Bansf_Rec.Payym;

                    R_BANSF_REC.APNO      := R_BADAPR_REC.APNO; --'受理編號';
                    R_BANSF_REC.SEQNO     := R_BADAPR_REC.SEQNO; --'序號';
                    R_Bansf_Rec.Paykind   := Substr(R_Bansf_Rec.Apno, 1, 1); --'給付別';
                    R_Bansf_rec.NITRMY    := r_badapr_rec.NITRMY; --'勞保投保年資(年-年金制)';
                    R_Bansf_Rec.Nitrmm    := R_Badapr_Rec.Nitrmm; --'勞保投保年資(月-年金制)';
                    R_Bansf_Rec.Code      := R_Badapr_Rec.Suprecmk; --'補收註記';
                    R_Bansf_Rec.Mchktyp   := R_Badapr_Rec.Mchktyp; --'月核案件類別';
                    R_Bansf_Rec.Oldab     := R_Badapr_Rec.Oldab; --'第一式/第二式';
                    R_Bansf_Rec.Oldaamt   := Nvl(R_Badapr_Rec.Oldaamt, 0); --'第一式金額(勞保給付金額)';
                    R_Bansf_Rec.Oldbamt   := Nvl(R_Badapr_Rec.Oldbamt, 0); --'第二式金額(勞保給付金額)';
                    R_Bansf_Rec.Qualcount := R_Badapr_Rec.Qualcount; --'符合眷屬(遺屬)人數';
                    R_Bansf_Rec.Payno     := R_Badapr_Rec.Paykind; --'給付種類';
                    R_Bansf_Rec.Cutamt    := Nvl(r_badapr_rec.Cutamt, 0); --'應扣失能金額';
                    /*2014/02/18
                            L-老年=>OLDRATE一律給0%
                            K-失能=>0人0%，1人25%，>=2人50%
                            S-遺屬=>0或1人0%，2人25%，>=3人50%
                    */
                    if SUBSTR(R_Badapr_Rec.APNO, 1, 1) = 'S' then
                        IF R_Badapr_Rec.qualcount = 0 OR
                           R_Badapr_Rec.qualcount is null or
                           R_Badapr_Rec.Qualcount = 1 THEN
                            R_Bansf_rec.oldrate := 0; --加計比率
                        Else
                            If R_Badapr_Rec.Qualcount = 2 Then
                                R_Bansf_rec.oldrate := 25; --加計比率
                            ELSE
                                R_Bansf_rec.oldrate := 50; --加計比率
                            End If;
                        End If;
                    else
                        IF R_Badapr_Rec.qualcount = 0 OR
                           R_Badapr_Rec.qualcount is null THEN
                            R_Bansf_rec.oldrate := 0; --加計比率
                        Else
                            If R_Badapr_Rec.Qualcount = 1 Then
                                R_Bansf_rec.oldrate := 25; --加計比率
                            ELSE
                                R_Bansf_rec.oldrate := 50; --加計比率
                            End If;
                        End If;
                    end if; --2014/02/18
                    if SUBSTR(R_Badapr_Rec.APNO, 1, 1) = 'L' then
                        R_Bansf_rec.oldrate := 0; --加計比率
                    end if;

                    /*         IF r_badapr_rec.qualcount is null or r_badapr_rec.qualcount=0 THEN
                       R_Bansf_rec.oldrate     :=0 ; --加計比率
                    Else
                       If R_Bansf_rec.Qualcount=1 Then
                          R_Bansf_rec.oldrate     :=25 ; --加計比率
                       ELSE
                          R_Bansf_rec.oldrate     :=50 ; --加計比率
                       End If;
                    End If;*/

                    R_Bansf_rec.Annuamt  := Nvl(r_badapr_rec.Annuamt, 0); --'累計已領年金金額';
                    R_Bansf_rec.Nachgmk  := r_badapr_rec.Nachgmk; --'普職互改註記';
                    R_Bansf_rec.Lecomamt := nvl(r_badapr_rec.Lecomamt, 0); --'己扣失能金額';
                    V_Key_Baappbaseid    := R_Badapr_Rec.Baappbaseid; --給付主檔資料編號

                    /*1.給付種類<>37
                    (1)補收註記為C或D不計件數。
                    (2)補收註記不為C且不為D，同一核定年月同一受理編號，只計1件(同一受理編號只看同一核定年月，不看其他核定年月，不看給付種類37)
                             */
                    Case
                        When R_Bansf_Rec.Code = 'C' Then
                            R_Bansf_Rec.Paycnt := 0;
                            R_Bansf_Rec.Pamts  := R_Badapr_Rec.Supamt;
                        When R_Bansf_Rec.Code = 'D' Then
                            R_Bansf_Rec.Paycnt := 0;
                            R_Bansf_Rec.Pamts  := 0 - R_Badapr_Rec.Recamt;
                        When V_Key_Apno = R_Bansf_rec.Apno And
                             V_Iisuym = substr(R_badapr_rec.Aplpaydate, 1, 6) Then
                            R_Bansf_Rec.Paycnt := 0;
                            R_Bansf_Rec.Pamts  := R_Badapr_Rec.BEFISSUEAMT;
                        When R_Badapr_Rec.BEFISSUEAMT > 0 Then
                            --20130219 UPDATDE
                            R_Bansf_rec.Paycnt := 1;
                            R_Bansf_Rec.Pamts  := R_Badapr_Rec.BEFISSUEAMT;
                        Else
                            R_Bansf_rec.Paycnt := 0;
                            R_Bansf_Rec.Pamts  := R_Badapr_Rec.BEFISSUEAMT;
                    End Case;
                    --    DBMS_OUTPUT.PUT_LINE( V_Key_Apno||';'||R_BANSF_REC.APNO||';'||R_Bansf_Rec.Code||';'||V_Key_Apno||';'||V_Iisuym||';'||R_Bansf_rec.Issuym||';'||to_char(R_Badapr_Rec.BEFISSUEAMT)||';'||R_Bansf_rec.Paycnt);

                    R_Bansf_rec.Evtype := R_Badapr_Rec.Evtyp;
                    if R_Bansf_rec.Evtype is null then
                        R_Bansf_rec.Evtype := '3';
                    end if;
                    R_Bansf_rec.Evcode  := R_Badapr_Rec.Evcode; --'事故原因';
                    R_Bansf_rec.Injname := R_Badapr_Rec.Criinjnme1; --'傷病名稱(國際疾病代碼)';
                    R_Bansf_rec.Injno   := R_Badapr_Rec.Criinjdp1; --'障礙(失能)項目';
                    R_Bansf_rec.INJdp   := F_injdp(R_Bansf_rec.Injno); --'障礙(失能部位)';
                    R_Bansf_rec.Injpart := TRIM(UPPER(R_Badapr_Rec.criinpart1)); --'受傷部位';
                    R_Bansf_Rec.Medium  := R_Badapr_Rec.Crimedium; --'媒介物';
                    --20130320
                    CASE
                        WHEN R_Badapr_Rec.Criinissul IS NOT NULL Then
                            R_Bansf_rec.Injcl := R_Badapr_Rec.Criinissul; --'失能等級 (身心障礙等級)';  BAAPPEXPAND.CRIINISSUL
                        WHEN R_Badapr_Rec.CRIINJCL1 IS NOT NULL Then
                            R_Bansf_rec.Injcl := R_Badapr_Rec.CRIINJCL1; --'失能等級 (身心障礙等級)';  BAAPPEXPAND.CRIINISSUL
                        WHEN R_Badapr_Rec.CRIINJCL2 IS NOT NULL Then
                            R_Bansf_rec.Injcl := R_Badapr_Rec.CRIINJCL2; --'失能等級 (身心障礙等級)';  BAAPPEXPAND.CRIINISSUL
                        WHEN R_Badapr_Rec.CRIINJCL3 IS NOT NULL Then
                            R_Bansf_rec.Injcl := R_Badapr_Rec.CRIINJCL3; --'失能等級 (身心障礙等級)';  BAAPPEXPAND.CRIINISSUL
                        ELSE
                            R_Bansf_rec.Injcl := NULL;
                    END CASE;
                    --符合離職退保後職災殘廢給付職業病種類(Y/N)
                    If R_Badapr_Rec.Ocaccidentmk = 'Y' Then
                        R_Bansf_rec.CHKKIND := 'Y';
                    Else
                        R_Bansf_rec.CHKKIND := 'N';
                    End If;

                    R_Bansf_rec.Adwkmk := R_Badapr_Rec.adwkmk_ex;
                    R_Bansf_Rec.Cat    := '1';

                    V_KEY_APNO  := R_BANSF_REC.APNO;
                    V_KEY_SEQNO := r_badapr_rec.SEQNO;
                    V_Iisuym    := substr(R_badapr_rec.Aplpaydate, 1, 6);

                    R_Baappbase_Rec := Null;
                    If r_badapr_rec.apno_ba is null Then
                        P_Read_Baappbase_D;
                    else
                        R_Baappbase_Rec.Baappbaseid  := r_badapr_rec.baappbaseid;
                        R_Baappbase_Rec.apno         := r_badapr_rec.APNO_BA;
                        R_Baappbase_Rec.Appdate      := r_badapr_rec.Appdate;
                        R_Baappbase_Rec.Evtjobdate   := r_badapr_rec.Evtjobdate;
                        R_Baappbase_Rec.LSUBNO       := r_badapr_rec.LSUBNO;
                        R_Baappbase_Rec.EVTSEX       := r_badapr_rec.EVTSEX;
                        R_Baappbase_Rec.Evtage       := r_badapr_rec.Evtage;
                        R_Baappbase_Rec.Evtnationtpe := r_badapr_rec.Evtnationtpe;
                        R_Baappbase_Rec.Closecause   := r_badapr_rec.Closecause;
                        R_Baappbase_Rec.Evtidnno     := r_badapr_rec.Evtidnno;
                        R_Baappbase_Rec.Evtbrdate    := r_badapr_rec.Evtbrdate;
                        R_Baappbase_Rec.Evtname      := r_badapr_rec.Evtname;
                        R_Baappbase_Rec.Evtdiedate   := r_badapr_rec.Evtdiedate;
                        R_Baappbase_Rec.Casetyp      := r_badapr_rec.Casetyp;
                        R_Baappbase_Rec.Apitem       := r_badapr_rec.Apitem;
                        R_Baappbase_Rec.Lsinsmamt    := r_badapr_rec.Lsinsmamt;
                        R_Baappbase_Rec.Paydate      := r_badapr_rec.Paydate;
                    End If;
                    --取得主檔、延伸檔、單位類別等資料
                    Update_Data;
                    --20131206 非被保險人則取被保險人之Insavgamt
                    if r_badapr_rec.seqno <> '0000' and
                       r_badapr_rec.seqno is not null then
                        R_Bansf_Rec.Pwage := F_pwage(R_Bansf_Rec.Apno,
                                                     v_edate/*,
                                                     R_Bansf_Rec.Payno*/); --'平均薪資';
                    else
                        R_Bansf_Rec.Pwage := R_Badapr_Rec.Insavgamt; --'平均薪資';
                    end if;

                    If R_Bansf_Rec.Payno in ('45', '48') Then
                        R_Bansf_Rec.Oldextrarate := R_Badapr_Rec.Oldrate; --(老年、遺屬)展延/減額比率
                    Else
                        R_Bansf_Rec.Oldextrarate := R_Badapr_Rec.Oldextrarate; --(老年、遺屬)展延/減額比率
                    End If;

                    If R_Bansf_Rec.Payno <> '49' Then
                        Insert Into Bansf
                            (Select R_Bansf_Rec.Paydate,
                                    R_Bansf_Rec.Appdate,
                                    R_Bansf_Rec.Issuym,
                                    R_Bansf_Rec.Payym,
                                    R_Bansf_Rec.Apno,
                                    R_Bansf_Rec.SEQNO,
                                    R_Bansf_Rec.Evdate,
                                    R_Bansf_Rec.Ubno,
                                    R_Baappbase_Rec.Lsubno,
                                    R_Bansf_Rec.Ubtype,
                                    R_Bansf_Rec.Inds,
                                    R_Bansf_Rec.Hincd,
                                    R_Bansf_Rec.Idsta,
                                    R_Bansf_Rec.Area,
                                    R_Bansf_Rec.Clsqty,
                                    R_Bansf_Rec.Sex,
                                    R_Bansf_Rec.Payage,
                                    R_Bansf_Rec.Age,
                                    R_Bansf_Rec.Agetype,
                                    R_Bansf_Rec.Evtnationtpe,
                                    R_Bansf_Rec.Payno,
                                    R_Bansf_Rec.Nitrmy,
                                    R_Bansf_Rec.Nitrmm,
                                    R_Bansf_Rec.Evtype,
                                    R_Bansf_Rec.Evcode,
                                    R_Bansf_Rec.Injname,
                                    R_Bansf_Rec.Injno,
                                    R_Bansf_Rec.Injdp,
                                    R_Bansf_Rec.Injpart,
                                    R_Bansf_Rec.Medium,
                                    R_Bansf_Rec.Injcl,
                                    R_Bansf_Rec.Apitem,
                                    R_Bansf_Rec.Chkkind,
                                    R_Bansf_Rec.Pwage,
                                    R_Bansf_Rec.Wage,
                                    R_Bansf_Rec.Paycnt,
                                    R_Bansf_Rec.Code,
                                    R_Bansf_Rec.Pamts,
                                    R_Bansf_Rec.Adwkmk,
                                    R_Bansf_Rec.Mchktyp,
                                    R_Bansf_Rec.Oldab,
                                    R_Bansf_Rec.Oldaamt,
                                    R_Bansf_Rec.Oldbamt,
                                    R_Bansf_Rec.Oldextrarate,
                                    R_Bansf_Rec.Qualcount,
                                    R_Bansf_Rec.Oldrate,
                                    R_Bansf_Rec.Closecause,
                                    R_Bansf_Rec.Annuamt,
                                    R_Bansf_Rec.Hbedmk,
                                    R_Badapr_Rec.Nachgmk,
                                    R_Bansf_Rec.Cutamt,
                                    R_Bansf_Rec.Lecomamt,
                                    R_Bansf_Rec.Paykind,
                                    Decode(Length(R_Bansf_Rec.Evtidnno),
                                           11,
                                           Substr(R_Bansf_Rec.Evtidnno, 1, 10),
                                           R_Bansf_Rec.Evtidnno),
                                    Decode(Length(R_Bansf_Rec.Evtidnno),
                                           11,
                                           Substr(R_Bansf_Rec.Evtidnno, 10, 1),
                                           Null),
                                    R_Bansf_Rec.Evtbrdate,
                                    R_Bansf_Rec.Evtname,
                                    R_Bansf_Rec.edate,
                                    R_Bansf_Rec.bdate,
                                    R_Bansf_Rec.Evtdiedate,
                                    R_Bansf_Rec.Adjym,
                                    DECODE(R_Bansf_Rec.Adjmk,
                                           NULL,
                                           'N',
                                           R_Bansf_Rec.Adjmk),
                                    R_Bansf_Rec.Gettype,
                                    R_Bansf_Rec.Firstpay,
                                    R_Bansf_Rec.Ciid,
                                    R_Bansf_Rec.Lumsum,
                                    R_Bansf_Rec.Cat,
                                    0,
                                    0,
                                    null,
                                    '',
                                    to_Char(Sysdate,'YYYYMMDDHH24MISS')
                               From Dual);
                        --2014/04/28增加 DEFAULT PAYNO=3x,4x
                        IF r_badapr_rec.seqno = '0000' AND
                           R_Bansf_Rec.Evtdiedate IS NOT NULL THEN
                            Insert Into Bansf_TEMP
                                (Select R_Bansf_Rec.Paydate,
                                        R_Bansf_Rec.Appdate,
                                        R_Bansf_Rec.Issuym,
                                        R_Bansf_Rec.Payym,
                                        R_Bansf_Rec.Apno,
                                        R_Bansf_Rec.SEQNO,
                                        R_Bansf_Rec.Evdate,
                                        R_Bansf_Rec.Ubno,
                                        R_Baappbase_Rec.Lsubno,
                                        R_Bansf_Rec.Ubtype,
                                        R_Bansf_Rec.Inds,
                                        R_Bansf_Rec.Hincd,
                                        R_Bansf_Rec.Idsta,
                                        R_Bansf_Rec.Area,
                                        R_Bansf_Rec.Clsqty,
                                        R_Bansf_Rec.Sex,
                                        R_Bansf_Rec.Payage,
                                        R_Bansf_Rec.Age,
                                        R_Bansf_Rec.Agetype,
                                        R_Bansf_Rec.Evtnationtpe,
                                        R_Bansf_Rec.Payno,
                                        R_Bansf_Rec.Nitrmy,
                                        R_Bansf_Rec.Nitrmm,
                                        R_Bansf_Rec.Evtype,
                                        R_Bansf_Rec.Evcode,
                                        R_Bansf_Rec.Injname,
                                        R_Bansf_Rec.Injno,
                                        R_Bansf_Rec.Injdp,
                                        R_Bansf_Rec.Injpart,
                                        R_Bansf_Rec.Medium,
                                        R_Bansf_Rec.Injcl,
                                        R_Bansf_Rec.Apitem,
                                        R_Bansf_Rec.Chkkind,
                                        R_Bansf_Rec.Pwage,
                                        R_Bansf_Rec.Wage,
                                        R_Bansf_Rec.Paycnt,
                                        R_Bansf_Rec.Code,
                                        R_Bansf_Rec.Pamts,
                                        R_Bansf_Rec.Adwkmk,
                                        R_Bansf_Rec.Mchktyp,
                                        R_Bansf_Rec.Oldab,
                                        R_Bansf_Rec.Oldaamt,
                                        R_Bansf_Rec.Oldbamt,
                                        R_Bansf_Rec.Oldextrarate,
                                        R_Bansf_Rec.Qualcount,
                                        R_Bansf_Rec.Oldrate,
                                        R_Bansf_Rec.Closecause,
                                        R_Bansf_Rec.Annuamt,
                                        R_Bansf_Rec.Hbedmk,
                                        R_Badapr_Rec.Nachgmk,
                                        R_Bansf_Rec.Cutamt,
                                        R_Bansf_Rec.Lecomamt,
                                        R_Bansf_Rec.Paykind,
                                        Decode(Length(R_Bansf_Rec.Evtidnno),
                                               11,
                                               Substr(R_Bansf_Rec.Evtidnno,
                                                      1,
                                                      10),
                                               R_Bansf_Rec.Evtidnno),
                                        Decode(Length(R_Bansf_Rec.Evtidnno),
                                               11,
                                               Substr(R_Bansf_Rec.Evtidnno,
                                                      10,
                                                      1),
                                               Null),
                                        R_Bansf_Rec.Evtbrdate,
                                        R_Bansf_Rec.Evtname,
                                        R_Bansf_Rec.edate,
                                        R_Bansf_Rec.bdate,
                                        R_Bansf_Rec.Evtdiedate,
                                        R_Bansf_Rec.Adjym,
                                        DECODE(R_Bansf_Rec.Adjmk,
                                               NULL,
                                               'N',
                                               R_Bansf_Rec.Adjmk),
                                        R_Bansf_Rec.Gettype,
                                        R_Bansf_Rec.Firstpay,
                                        R_Bansf_Rec.Ciid,
                                        R_Bansf_Rec.Lumsum,
                                        R_Bansf_Rec.Cat,
                                        0,
                                        0,
                                        null
                                   From Dual);
                        END IF;
                        V_Out_Cnt := V_Out_Cnt + 1;
                    end if;

                    --  V_Iisuym:=substr(R_badapr_rec.Aplpaydate,1,6);
                    If Mod(V_Out_Cnt, 50000) = 0 Then
                        Commit;
                    END IF;
                END LOOP;
                --2014/04/28
                DELETE BANSF_TEMP WHERE Evtdiedate IS NULL OR nvl(Evtdiedate,' ') = ' ';
                COMMIT;

                UPDATE BANSF
                   SET Evtdiedate =
                       (SELECT DISTINCT Evtdiedate
                          FROM BANSF_TEMP
                         WHERE Evtdiedate IS NOT NULL
                           AND APNO = BANSF.APNO
                           AND ROWNUM < 2)
                 WHERE APNO IN (SELECT DISTINCT APNO
                                  FROM BANSF_TEMP
                                 WHERE Evtdiedate IS NOT NULL)
                   AND SEQNO = '0000'
                   AND SUBSTR(PAYNO, 1, 1) <> '5';
                commit;

                UPDATE BANSF
                   SET UBTYPE = CASE
                                    WHEN UBTYPE IS NULL OR TRIM(UBTYPE) = '' THEN
                                     'Z9'
                                    ELSE
                                     UBTYPE
                                END,
                       INDS = CASE
                                  WHEN INDS IS NULL OR TRIM(INDS) = '' THEN
                                   'Z999'
                                  ELSE
                                   INDS
                              END,
                       AREA = CASE
                                  WHEN AREA IS NULL OR TRIM(AREA) = '' THEN
                                   'Z999'
                                  ELSE
                                   AREA
                              END,
                       CLSQTY = CASE
                                    WHEN CLSQTY IS NULL THEN
                                     0
                                    ELSE
                                     CLSQTY
                                END,
                       Evtnationtpe = decode(Evtnationtpe,
                                             null,
                                             'C',
                                             'N',
                                             'C',
                                             'F',
                                             'Y',
                                             'Y',
                                             'Y',
                                             '1',
                                             '1',
                                             '2',
                                             '2',
                                             'Z'), --國藉
                       NITRMY = CASE
                                    WHEN NITRMY IS NULL THEN
                                     0
                                    ELSE
                                     NITRMY
                                END,
                       NITRMM = CASE
                                    WHEN NITRMM IS NULL THEN
                                     0
                                    ELSE
                                     NITRMM
                                END,
                       EVCODE = CASE
                                    WHEN EVCODE IS NULL OR TRIM(EVCODE) = '' THEN
                                     'Z9'
                                    ELSE
                                     EVCODE
                                END,
                       INJNAME = Case
                                     When INJNAME Is Null Or Trim(INJNAME) = '' Then
                                      'Z999999'
                                     Else
                                      INJNAME
                                 End,
                       INJNO = CASE
                                   WHEN INJNO IS NULL OR TRIM(INJNO) = '' THEN
                                    'Z9999999'
                                   ELSE
                                    INJNO
                               END,
                       INJPART = CASE
                                     WHEN INJPART IS NULL OR TRIM(INJPART) = '' THEN
                                      'Z99'
                                     ELSE
                                      INJPART
                                 END,
                       MEDIUM = CASE
                                    WHEN MEDIUM IS NULL OR TRIM(MEDIUM) = '' THEN
                                     'Z99'
                                    ELSE
                                     MEDIUM
                                END,
                       INJCL = CASE
                                   WHEN INJCL IS NULL OR TRIM(INJCL) = '' THEN
                                    'Z9'
                                   WHEN LENGTH(INJCL) = 1 THEN
                                    '0' || INJCL
                                   ELSE
                                    INJCL
                               END,
                       APITEM = CASE
                                    WHEN APITEM IS NULL OR
                                         TRIM(APITEM) = '' AND
                                         SUBSTR(PAYNO, 1, 1) = '3' THEN
                                     '0'
                                    when APITEM IS NULL OR TRIM(APITEM) = '' then
                                     'Z'
                                    Else
                                     APITEM
                                End,
                       pwage = Case
                                   When pwage Is Null Then
                                    0
                                   Else
                                    pwage
                               End,
                       WAGE = CASE
                                  WHEN WAGE IS NULL THEN
                                   0
                                  ELSE
                                   WAGE
                              END,
                       PAMTS = CASE
                                   WHEN PAMTS IS NULL THEN
                                    0
                                   ELSE
                                    PAMTS
                               END,
                       LUMSUM = CASE
                                    WHEN LUMSUM IS NULL THEN
                                     0
                                    ELSE
                                     LUMSUM
                                END,
                       MCHKTYP = CASE
                                     WHEN MCHKTYP IS NULL OR TRIM(MCHKTYP) = '' THEN
                                      'Z'
                                     ELSE
                                      MCHKTYP
                                 END,
                       OLDEXTRARATE = CASE
                                          WHEN OLDEXTRARATE IS NULL THEN
                                           0
                                          ELSE
                                           OLDEXTRARATE
                                      END,
                       QUALCOUNT = CASE
                                       WHEN QUALCOUNT IS NULL THEN
                                        0
                                       ELSE
                                        QUALCOUNT
                                   END,
                       OLDRATE = CASE
                                     WHEN OLDRATE IS NULL THEN
                                      0
                                     ELSE
                                      OLDRATE
                                 END,
                       CLOSECAUSE = CASE
                                        WHEN CLOSECAUSE IS NULL OR
                                             TRIM(CLOSECAUSE) = '' THEN
                                         'Z9'
                                        ELSE
                                         CLOSECAUSE
                                    END,
                       adwkmk = CASE
                                    WHEN adwkmk IS NULL OR TRIM(adwkmk) = '' THEN
                                     '1'
                                    ELSE
                                     adwkmk
                                END,
                       SEX = CASE
                                 WHEN SEX IS NULL OR TRIM(SEX) = '' OR SEX = 'C' THEN
                                  'Z'
                                 ELSE
                                  SEX
                             END,
                       PAYAGE = CASE
                                    WHEN (PAYAGE IS NULL or payage <= 0) AND
                                         substr(apno, 1, 1) = 'S' and
                                         Substr(Evtdiedate, 5, 2) >
                                         Substr(EVTBRDATE, 5, 2) THEN
                                     to_number(Substr(Evtdiedate, 1, 4) -
                                               Substr(EVTBRDATE, 1, 4) - 1)
                                    WHEN (PAYAGE IS NULL or payage <= 0) AND
                                         substr(apno, 1, 1) = 'S' and
                                         Substr(Evtdiedate, 5, 2) <=
                                         Substr(EVTBRDATE, 5, 2) THEN
                                     to_number(Substr(Evtdiedate, 1, 4) -
                                               Substr(EVTBRDATE, 1, 4))
                                    WHEN (PAYAGE IS NULL or payage <= 0) AND
                                         substr(apno, 1, 1) <> 'S' and
                                         Substr(PAYYM, 5, 2) >
                                         Substr(EVTBRDATE, 5, 2) THEN
                                     to_number(Substr(PAYYM, 1, 4) -
                                               Substr(EVTBRDATE, 1, 4) - 1)
                                    WHEN (PAYAGE IS NULL or payage <= 0) AND
                                         substr(apno, 1, 1) <> 'S' and
                                         Substr(PAYYM, 5, 2) <=
                                         Substr(EVTBRDATE, 5, 2) THEN
                                     to_number(Substr(PAYYM, 1, 4) -
                                               Substr(EVTBRDATE, 1, 4))
                                    ELSE
                                     to_number(PAYAGE)
                                END,
                       AGE = CASE
                                 WHEN PAYNO = '37' AND Substr(EVTBRDATE, 5, 2) >
                                      Substr(Evdate, 5, 2) THEN
                                  to_number(Substr(Evdate, 1, 4) -
                                            Substr(EVTBRDATE, 1, 4) - 1)
                                 WHEN PAYNO = '37' AND Substr(EVTBRDATE, 5, 2) <=
                                      Substr(Evdate, 5, 2) THEN
                                  to_number(Substr(Evdate, 1, 4) -
                                            Substr(EVTBRDATE, 1, 4))
                                 WHEN PAYAGE IS NULL THEN
                                  0
                                 ELSE
                                  AGE
                             END,
                       EVTYPE = CASE
                                    WHEN EVTYPE IS NULL THEN
                                     '3'
                                    ELSE
                                     EVTYPE
                                END,
                       FIRSTPAY = CASE
                                      WHEN FIRSTPAY IS NULL THEN
                                       '0'
                                      ELSE
                                       FIRSTPAY
                                  END,
                       CHKKIND = CASE
                                     WHEN CHKKIND IS NULL OR TRIM(CHKKIND) = '' THEN
                                      'N'
                                     ELSE
                                      CHKKIND
                                 END,
                       ADJMK = CASE
                                   WHEN ADJMK IS NULL OR TRIM(ADJMK) = '' THEN
                                    'N'
                                   ELSE
                                    ADJMK
                               END,
                       HBEDMK = CASE
                                    WHEN HBEDMK IS NULL OR TRIM(HBEDMK) = '' THEN
                                     'Y'
                                    ELSE
                                     HBEDMK
                                END,
                       code = CASE
                                  WHEN code = 'K' THEN
                                   'D'
                                  ELSE
                                   code
                              END
                 WHERE (ISSUYM = P_INSUYM OR P_INSUYM = '201208');
                commit;

            EXCEPTION
                WHEN OTHERS Then
                    DBMS_OUTPUT.put_line(v_key_APNO);
                    p_return_code   := p_return_code || v_key_APNO || ',';
                    V_Exception_Cnt := V_Exception_Cnt + 1;
                    DBMS_OUTPUT.put_line('p_dwamf_old_proc發生錯誤,錯誤代碼=' ||
                                         sqlcode || ',' || '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code ||
                                     'p_dwamf_old_proc發生錯誤,錯誤代碼=' || sqlcode || ',' ||
                                     '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_01：統計資料轉入發生錯誤，APNO='||v_key_APNO||'***(E15)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;
            -- ============================================================================
            --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
            -------------------------------------------------------------------------------
        Begin
            DBMS_OUTPUT.put_line('勞保年金統計檔資料轉入');
            p_return_code := p_return_code || '勞保年金統計檔資料轉入' || ',';
            --   p_return_code := 'OK';  --回傳訊息
            --初期處理------------------------------------------------------
            BEGIN
                -- 檢查參數內容
                -- 處理年月
                Dbms_Output.Put_Line('處理年月=' || P_Insuym);
                p_return_code := p_return_code || '處理年月=' || P_Insuym || ',';
                --v_format_date := TO_DATE(P_Insuym || '01', 'YYYYMMDD');
            EXCEPTION
                WHEN OTHERS THEN
                    DBMS_OUTPUT.put_line('輸入參數資料錯誤EXCEPTION！');
                    p_return_code := p_return_code || '輸入參數資料錯誤EXCEPTION！' || ',';
                    --        p_return_code := 'NO';  --回傳訊息
                    Dbms_Output.Put_Line('初期處理失敗：錯誤代碼=' || Sqlcode || ' ， ' ||
                                         '錯誤訊息=' || Sqlerrm);
                    p_return_code := p_return_code || '初期處理失敗：錯誤代碼=' || Sqlcode ||
                                     ' ， ' || '錯誤訊息=' || Sqlerrm || ',';
                    COMMIT;
                    Return;
            END;
            --主程序處理=========================================================================

            --20190827 Add by Angela RecLog Start
            v_o_flag := '0';
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_02)：勞保年金統計核定檔及勞保年金統計檔轉入(老年)',
                            p_memo   => '開始時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 '0',
                                 '(DB)SP_BANSF_02：勞保年金統計核定檔及勞保年金統計檔轉入(老年)(開始)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            --20190827 Add by Angela RecLog End

            --1.刪除給付年月資料
            DBMS_OUTPUT.put_line('1.刪除給付年月資料');
            p_return_code := p_return_code || '1.刪除給付年月資料' || ',';
            If P_Insuym = '201208' Then
                p_delete_data;
                COMMIT;
            end IF;

            --2.勞保年金統計核定檔BADAPR_REF及勞保年金統計檔BANSF轉入
            Dbms_Output.Put_Line('2.勞保年金統計檔轉入' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
            p_return_code := p_return_code || '2.勞保年金統計檔轉入' ||
                             To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            P_Bansf_Proc;
            p_result_proc;

            If V_Exception_Cnt > 0 Then
                raise_application_error(-20001, '勞保年金統計檔處理失敗！');
                v_o_flag := '1';
            ELSE
                COMMIT;
            END IF;

            --==========================================================================================

            --結束處理
            --DBMS_OUTPUT.put_line('EXCEPTION錯誤筆數＝'||v_exception_cnt);
            IF v_exception_cnt > 0 THEN
                Rollback;
                --      P_Return_Code := 'NO';  --回傳訊息
                DBMS_OUTPUT.put_line('產生勞保年金統計檔失敗！' ||
                                     To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || '產生勞保年金統計檔失敗！' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
                v_o_flag := '1';
            Else
                DBMS_OUTPUT.put_line('產生勞保年金統計檔成功！' ||
                                     To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || '產生勞保年金統計檔成功！' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            END IF;

            p_output_message; --輸出結果
            COMMIT;

            --20190827 Add by Angela RecLog Start
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_02)：勞保年金統計核定檔及勞保年金統計檔轉入(老年)',
                            p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:('||v_o_flag||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 v_o_flag,
                                 '(DB)SP_BANSF_02：勞保年金統計核定檔及勞保年金統計檔轉入(老年)(結束)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

            if v_o_flag <> '0' then
                v_o_flag := 'N';
            else
                v_o_flag := 'E';
            end if;
            --20190827 Add by Angela RecLog End;
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                DBMS_OUTPUT.put_line('勞保年金統計檔失敗！' || SQLCODE || ' - ' || SQLERRM);
                p_return_code := p_return_code || '勞保年金統計檔失敗！' || SQLCODE || ' - ' || SQLERRM || ',';
                --      p_return_code := 'NO';  --回傳訊息
                p_output_message; --輸出結果
                COMMIT;

                --20190827 Add by Angela RecLog Start
                SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                                p_job_id => 'PG_BANSF_01.SP_BANSF',
                                p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_02)：勞保年金統計核定檔及勞保年金統計檔轉入(老年)',
                                p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:(1)');

                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '1',
                                     '(DB)SP_BANSF_02：勞保年金統計核定檔及勞保年金統計檔轉入(老年)(結束)',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

                v_o_flag := 'N';
                --20190827 Add by Angela RecLog End;
        End;
    End SP_BANSF_02;

    Procedure SP_BANSF_04(
        P_Insuym      In Varchar2, --處理年月
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    ) Is
    Begin
        -- 修改紀錄：
        -- 維護人員        日期       說明
        -- -----------   --------  ----------------------------------------------------
        -- rose          20130909   v1.0
        -- ============================================================================
        --                          <<<<<<<<< 變數區 >>>>>>>>>>
        -------------------------------------------------------------------------------
        Declare
            R_Bansf_Rec Bansf%Rowtype; --badapr_ref RECORD
            R_CIPB_Rec  ba.cipb%Rowtype; --BAAPPEXPAND RECORD
            -- ============================================================================
            sw_found BOOLEAN := TRUE; --找到資料
            -- ============================================================================
            s_key_dwcmk varchar(2);
            V_KEY_CIID  bansf.ciid%type; --勞就保識別碼
            V_Out_Cnt   Number(10) := 0; --輸出新增筆數
            v_126out    CI.PL0126M0OUT%ROWTYPE;
            v_NUMOFDATE BCA_D_INJC.NUMOFDATE1%type;

            V_Key_Apno          Bansf.Apno%Type; --受理編號
            V_Exception_Cnt     Integer := 0; --EXCEPTION錯誤筆數
            --V_Format_Date       Date; --DATE格式欄位
            v_notfound_cipb_cnt NUMBER(10) := 0; --找不到CIPB筆數
            V_TOOMANY_CIPB_CNT  NUMBER(10) := 0; --找到太多CIPB筆數
            V_OTHER_CIPB_CNT    NUMBER(10) := 0; --找到ERR CIPB筆數
            v_LUMSUM            number(12, 2);

            -- ============================================================================
            -- CURSOR C_BANSF_REF
            -------------------------------------------------------------------------------
            --處理年月=201208,則全量資料取入,否則,取回給付年月payym=處理年月P_Payym
            CURSOR C_BANSF IS
                select A.APNO,
                       A.evtidnno,
                       A.HBEDMK,
                       A.SEQNO,
                       A.ISSUYM,
                       A.PAYYM,
                       A.CODE,
                       A.EVTYPE,
                       A.injcl,
                       A.ciid,
                       A.EVDATE,
                       C.Evtyp,
                       C.Evcode,
                       C.deductday,
                       C.Criinjnme1,
                       C.Criinjdp1,
                       C.criinpart1,
                       C.Crimedium,
                       C.Criinissul,
                       C.CRIINJCL1,
                       C.CRIINJCL2,
                       c.CRIINJCL3,
                       C.Ocaccidentmk,
                       case
                           when C.adwkmk is null then
                            '1'
                           else
                            c.adwkmk
                       end as adwkmk_ex,
                       D.Lumsum Lumsum_3x
                  from (Select *
                          From bansf
                         Where issuym < '201312'
                           and payno in ('35', '37', '38')
                           and P_Insuym = '201208'
                        union all
                        Select *
                          From bansf
                         Where P_Insuym <> '201208'
                           and issuym = P_Insuym
                           and payno in ('35', '37', '38')) A,
                       (select * from baappexpand where seqno = '0000') C,
                       bansf_3x D
                 where A.APNO = C.APNO(+)
                   and A.apno = D.apno(+)
                   and a.evtidnno = D.Evtidnno(+)
                /*and A.apno='K00000000183' and A.evtidnno='G100551723'*/
                ;
            -- ============================================================================
            -- procedure
            -------------------------------------------------------------------------------
            -- 輸出結果
            PROCEDURE p_output_message IS
            BEGIN
                DBMS_OUTPUT.PUT_LINE('異常cipb資料=' ||
                                     TO_CHAR(V_NOTFOUND_CIPB_CNT +
                                             V_TOOMANY_CIPB_CNT +
                                             V_OTHER_CIPB_CNT));
                p_return_code := p_return_code || '異常cipb資料=' ||
                                 TO_CHAR(V_NOTFOUND_CIPB_CNT +
                                         V_TOOMANY_CIPB_CNT + V_OTHER_CIPB_CNT) || ',';
                Dbms_Output.Put_Line('新增bansf筆數=' || V_Out_Cnt);
                p_return_code := p_return_code || '新增bansf筆數=' || V_Out_Cnt || ',';
            End;
            ------------------------------------------------------------
            --取回INJDP障礙(失能部位)
            function F_injc(S_key_INJC In Varchar2, S_key_EVTYPE in varchar2)
                Return BCA_D_INJC.NUMOFDATE1%TYPE Is
                RESULT BCA_D_INJC.NUMOFDATE1%TYPE;
            Begin
                /*條件:INJCL失能代碼=INJC*/
                SELECT CASE
                           WHEN S_key_EVTYPE IN ('1', '2') THEN
                            NUMOFDATE1
                           WHEN S_key_EVTYPE IN ('3', '4') THEN
                            NUMOFDATE2
                           ELSE
                            0
                       END
                  Into Result
                  From Bca_D_InjC
                 Where Injcl = S_key_INJC;
                RETURN RESULT;
            EXCEPTION
                When No_Data_Found Then
                    RETURN 0;
                When Others Then
                    RETURN 0;
            End;
            --判別bansf_3x是否存在
            function F_bansf_3x(S_apno in varchar2, S_evtidnno in varchar2)
                return bansf_3x.avgwg%type is
                result bansf_3x.avgwg%type;
            begin
                select count(*)
                  into result
                  from bansf_3x
                 where apno = S_apno
                   and evtidnno = S_evtidnno;
                return result;
            end;
            -------------------------------------------------------------------------------
            -- 顯示LOG內容
            PROCEDURE log_msg(p_msg IN VARCHAR2) IS
            BEGIN
                IF v_exception_cnt > 100 THEN
                    DBMS_OUTPUT.put_line('EXCEPTION錯誤超過100個！');
                    p_return_code := p_return_code || 'EXCEPTION錯誤超過100個！' || ',';
                    raise_application_error(-20001, 'EXCEPTION錯誤超過100個！');
                END IF;

                DBMS_OUTPUT.put_line(p_msg);
                p_return_code := p_return_code || p_msg || ',';
            End;
            -------------------------------------------------------------------------------
            -- 依身份證號Idno讀取CIPB資料
            PROCEDURE p_read_cipb is
            Begin
                Sw_Found := True;
                SELECT *
                  INTO r_cipb_rec
                  FROM ba.cipb B --2013/09/10 ba.CIPB
                 WHERE B.CIID = V_KEY_CIID
                   AND ROWNUM < 2;
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    V_NOTFOUND_CIPB_CNT := V_NOTFOUND_CIPB_CNT + 1;
                    R_CIPB_REC          := NULL;
                    SW_FOUND            := FALSE;
                WHEN OTHERS THEN
                    V_OTHER_CIPB_CNT := V_OTHER_CIPB_CNT + 1;
                    R_Cipb_Rec       := Null;
                    Sw_Found         := False;
                    LOG_MSG('p_read_cipb發生錯誤,CIID=' || V_KEY_CIID || ',錯誤代碼=' ||
                            SQLCODE || ',' || '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code || 'p_read_cipb發生錯誤,CIID=' ||
                                     V_KEY_CIID || ',錯誤代碼=' || SQLCODE || ',' ||
                                     '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_01：讀取BA.CIPB發生錯誤，CIID='||V_KEY_CIID||'***(E16)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;
            ---------------------------------------------------------------------------------
            -- 新資料轉入
            /*1.老年年金
              先判斷bansf.HBEDMK_有無舊年資,(HBEDMK='N')無舊年資則現制一次老年金額=0,
              (HBEDMK='Y')有舊年資則計算現制一次老年金額
              計算方式：
              依CIID讀取BACIPB
              計算年資基數(算至小數第2位,第三位四捨五入)：
                 BACIPB.NOLDTY >=15 ,年資基數=15+((BACIPB.NOLDTY-15)*2)+(round(BACIPB.NOLDTM/12)*2,2)
                 BACIPB.NOLDTY  <15 ,年資基數=(BACIPB.NOLDTY*1)+(round(BACIP.NOLDTM/12,2)*1)
              現制一次老年金額=BACIPB.AVGWG平均薪資(36個月)*年資基數

            2.失能年金
              先判斷bansf.HBEDMK_有無舊年資,(HBEDMK='N')無舊年資則現制一次失能金額=0,
              (HBEDMK='Y')有舊年資則計算現制一次失能金額
              計算方式：
              設定平均日薪=(6個月平均薪資/30,算至小數一位,第二位四捨五入)

              取6個月平均薪資：
              CALL  PKG_CI_T01.CI_T03(p_ftyp,p_ciid,6,
                                NULL,p_cvept,NULL,
                                NULL,NULL,NULL,v_dwcmk,
                                NULL,v_126out);
              取回傳值v_126out.avgwg(6個月平均薪資).
              參數說明：
              p_ftyp='L'  --保險別'L'、'V'
              p_ciid=bansf.ciid
              p_AVGM=6    --計算均薪月數
              p_cvept=bansf.EVDATE  --年資截止日(事故日期)
              p_dwcmk：投保薪資合併計算註記
              傷病分類 IN ('1','2')p_dwcmk='N',傷病分類 IN ('3','4')p_dwcmk=null


              依APNO讀取延伸主檔,設定失能等級(先取核定等級_CRIINISSUL,無核定等級才取失能等級1)
              再用失能等級讀取BCA_D_INJC取出給付日數(EVTYPE_傷病分類 IN ('1','2')取職災日數;
                                                     EVTYPE_傷病分類 IN ('3','4')取普通日數)
              (BCA_D_INJC要先修改加普通日數，職災日數欄位)

              設定扣除天數=BAAPPEXPAND.DEDUCTDAY

              計算現制一次失能金額=(給付日數 - 扣除天數)* 平均日薪 算至整數一位,第一位四捨五入
              ----------------------------------------------------------------------------
              ROUND((p_chkday - p_deduct) * ROUND( p_pwage / 30 , 1) , 0);*/
            -------------------------------------------------------------------------------
            PROCEDURE p_bansf_proc Is
            Begin
                --1.老年年金
                V_Out_Cnt := 0;
                For r_bansf_rec In (select *
                                      from bansf
                                     where (ISSUYM = P_INSUYM OR
                                           P_INSUYM = '201208')
                                       and payno like '4%') Loop
                    v_LUMSUM   := 0;
                    v_key_APNO := r_bansf_rec.apno;
                    v_key_CIID := r_bansf_rec.ciid;
                    p_read_cipb;
                    IF sw_found = TRUE THEN
                        IF r_bansf_rec.HBEDMK = 'N' THEN
                            v_LUMSUM := 0;
                        ELSE
                            IF r_cipb_rec.NOLDTY >= 15 THEN
                                V_LUMSUM := ROUND(r_cipb_rec.avgwg *
                                                  round(15 +
                                                        ((r_CIPB_rec.NOLDTY - 15) * 2) +
                                                        round(r_CIPB_rec.NOLDTM / 12,
                                                              2) * 2,
                                                        2),
                                                  0);
                            ELSE
                                V_LUMSUM := ROUND(r_cipb_rec.avgwg *
                                                  round((r_CIPB_rec.NOLDTY * 1) +
                                                        round(r_CIPB_rec.NOLDTM / 12,
                                                              2) * 1,
                                                        2),
                                                  0);
                            END IF;
                        END IF;
                        UPDATE BANSF
                           SET LUMSUM = V_LUMSUM
                         WHERE APNO = r_bansf_rec.apno
                           and issuym = r_bansf_rec.issuym
                           and payym = r_bansf_rec.payym
                           and (code = r_bansf_rec.code OR
                               (r_bansf_rec.code IS NULL AND CODE IS NULL));
                        V_Out_Cnt := V_Out_Cnt + 1;
                    END IF;
                    If Mod(V_Out_Cnt, 50000) = 0 Then
                        Commit;
                    END IF;
                END LOOP;

                --2.失能年金
                For r_bansf_rec_3 In C_bansf Loop
                    v_key_APNO := r_bansf_rec_3.apno;
                    if r_bansf_rec_3.evtype in ('1', '2') then
                        s_key_dwcmk := 'N';
                    else
                        s_key_dwcmk := null;
                    end if;
                    --當資料已存在bansf_3x下，則修改存入lumsum_3x
                    if r_bansf_rec_3.lumsum_3x > 0 then
                        UPDATE BANSF
                           SET LUMSUM = r_bansf_rec_3.lumsum_3x
                         WHERE APNO = r_bansf_rec_3.apno
                           and issuym = r_bansf_rec_3.issuym
                           and payym = r_bansf_rec_3.payym
                           and (code = r_bansf_rec_3.code or
                               (r_bansf_rec_3.code is null and code is null));
                        V_Out_Cnt := V_Out_Cnt + 1;
                    else
                        CI.PKG_CI_T02.CI_T03('L',
                                             r_bansf_rec_3.ciid,
                                             6,
                                             null,
                                             r_bansf_rec_3.evdate,
                                             null,
                                             null,
                                             null,
                                             null,
                                             s_key_dwcmk,
                                             null,
                                             v_126out);

                        v_LUMSUM := 0;
                        IF v_126out.avgwg is not null and v_126out.avgwg <> 0 THEN
                            v_NUMOFDATE := F_INJC(r_bansf_rec_3.injcl,
                                                  r_bansf_rec_3.EVTYPE);
                            if r_bansf_rec_3.HBEDMK = 'N' THEN
                                v_LUMSUM := 0;
                            else
                                if r_bansf_rec_3.deductday is null then
                                    v_LUMSUM := ROUND(v_NUMOFDATE *
                                                      round(v_126out.avgwg / 30,
                                                            2),
                                                      0);
                                else
                                    v_LUMSUM := ROUND((v_NUMOFDATE -
                                                      r_bansf_rec_3.deductday) *
                                                      round(v_126out.avgwg / 30,
                                                            2),
                                                      0);
                                end if;
                            end if;

                            UPDATE BANSF
                               SET LUMSUM = v_LUMSUM
                             WHERE APNO = r_bansf_rec_3.apno
                               and issuym = r_bansf_rec_3.issuym
                               and payym = r_bansf_rec_3.payym
                               and (code = r_bansf_rec_3.code or (r_bansf_rec_3.code is null and
                                   code is null));
                            if F_bansf_3x(r_bansf_rec_3.apno,
                                          r_bansf_rec_3.evtidnno) = 0 then
                                insert into bansf_3x
                                    select r_bansf_rec_3.apno,
                                           r_bansf_rec_3.evtidnno,
                                           r_bansf_rec_3.Injcl,
                                           r_bansf_rec_3.EVTYPE,
                                           r_bansf_rec_3.HBEDMK,
                                           v_NUMOFDATE,
                                           r_bansf_rec_3.deductday,
                                           v_126out.avgwg,
                                           v_LUMSUM
                                      from dual;
                                commit;
                            end if;
                            V_Out_Cnt := V_Out_Cnt + 1;
                        end if;
                    end if;
                    If Mod(V_Out_Cnt, 50000) = 0 Then
                        Commit;
                    END IF;
                END Loop;

                --2.失能年金 38
                For r_T_ACTUARY_REF In (SELECT * FROM T_ACTUARY_REF) Loop
                    UPDATE BANSF
                       SET OLDBAMT  = r_T_ACTUARY_REF.AMOUNT,
                           CUTAMT   = r_T_ACTUARY_REF.LUMPSUM,
                           LECOMAMT = 0,
                           LUMSUM   = r_T_ACTUARY_REF.LUMSUM
                     WHERE APNO = r_T_ACTUARY_REF.apno
                       and EVTIDNNO = r_T_ACTUARY_REF.EVTIDNNO;
                    V_Out_Cnt := V_Out_Cnt + 1;
                    If Mod(V_Out_Cnt, 50000) = 0 Then
                        Commit;
                    END IF;
                END Loop;
                --2014/5/6 update 普職互改金額修正
                -- update bansf set pamts=0 where issuym='201401' and nachgmk in ('12','21','A','B')
                --and paycnt=0 and pamts<0 and payym=(select min(payym) from bansf A where issuym='201401' and nachgmk in ('12','21','A','B')
                --and paycnt=0 and pamts<0 and apno='bansf.apno)
                -- commit;
            EXCEPTION
                WHEN OTHERS Then
                    DBMS_OUTPUT.put_line(v_key_APNO);
                    p_return_code   := p_return_code || v_key_APNO || ',';
                    V_Exception_Cnt := V_Exception_Cnt + 1;
                    DBMS_OUTPUT.put_line('update Data發生錯誤,錯誤代碼=' || sqlcode || ',' ||
                                         '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code || 'update Data發生錯誤,錯誤代碼=' ||
                                     sqlcode || ',' || '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_04：勞保年金統計檔現制一次給付金額計算資料修改發生錯誤***(E17)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;
            -- ============================================================================
            --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
            -------------------------------------------------------------------------------
        Begin
            DBMS_OUTPUT.put_line('4.勞保年金統計檔現制一次給付金額計算資料修改' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
            p_return_code := p_return_code || '4.勞保年金統計檔現制一次給付金額計算資料修改' ||
                             To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            --   p_return_code := 'OK';  --回傳訊息
            --初期處理------------------------------------------------------
            BEGIN
                -- 檢查參數內容
                -- 處理年月
                Dbms_Output.Put_Line('處理年月=' || P_Insuym);
                p_return_code := p_return_code || '處理年月=' || P_Insuym || ',';
                --v_format_date := TO_DATE(P_Insuym || '01', 'YYYYMMDD');
            EXCEPTION
                WHEN OTHERS THEN
                    DBMS_OUTPUT.put_line('輸入參數資料錯誤EXCEPTION！');
                    p_return_code := p_return_code || '輸入參數資料錯誤EXCEPTION！' || ',';
                    --        p_return_code := 'NO';  --回傳訊息
                    Dbms_Output.Put_Line('初期處理失敗：錯誤代碼=' || Sqlcode || ' ， ' ||
                                         '錯誤訊息=' || Sqlerrm);
                    p_return_code := p_return_code || '初期處理失敗：錯誤代碼=' || Sqlcode ||
                                     ' ， ' || '錯誤訊息=' || Sqlerrm || ',';
                    COMMIT;
                    Return;
            END;
            --主程序處理=========================================================================

            --20190827 Add by Angela RecLog Start
            v_o_flag := '0';
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_04)：勞保年金統計檔現制一次給付金額計算資料修改',
                            p_memo   => '開始時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 '0',
                                 '(DB)SP_BANSF_04：勞保年金統計檔現制一次給付金額計算資料修改(開始)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            --20190827 Add by Angela RecLog End

            --1.現制一次給付金額計算資料修改
            P_Bansf_Proc;

            If V_Exception_Cnt > 0 Then
                raise_application_error(-20001, '勞保年金統計檔現制一次給付金額計算資料修改處理失敗！');
                v_o_flag := '1';
            ELSE
                COMMIT;
            END IF;

            --==========================================================================================

            --結束處理
            --DBMS_OUTPUT.put_line('EXCEPTION錯誤筆數＝'||v_exception_cnt);
            IF v_exception_cnt > 0 THEN
                Rollback;
                --      P_Return_Code := 'NO';  --回傳訊息
                DBMS_OUTPUT.put_line('修改勞保年金統計檔現制一次給付金額失敗！' ||
                                     To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || '修改勞保年金統計檔現制一次給付金額失敗！' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
                v_o_flag := '1';
            Else
                DBMS_OUTPUT.put_line('修改勞保年金統計檔現制一次給付金額成功！' ||
                                     To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || '修改勞保年金統計檔現制一次給付金額成功！' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            END IF;

            p_output_message; --輸出結果
            COMMIT;

            --20190827 Add by Angela RecLog Start
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_04)：勞保年金統計檔現制一次給付金額計算資料修改',
                            p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:('||v_o_flag||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 v_o_flag,
                                 '(DB)SP_BANSF_04：勞保年金統計檔現制一次給付金額計算資料修改(結束)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

            if v_o_flag <> '0' then
                v_o_flag := 'N';
            else
                v_o_flag := 'E';
            end if;
            --20190827 Add by Angela RecLog End;
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                DBMS_OUTPUT.put_line('勞保年金統計檔現制一次給付金額計算資料修改處理失敗！' || SQLCODE ||
                                     ' - ' || SQLERRM);
                p_return_code := p_return_code || '勞保年金統計檔現制一次給付金額計算資料修改處理失敗！' ||
                                 SQLCODE || ' - ' || SQLERRM || ',';
                --      p_return_code := 'NO';  --回傳訊息
                p_output_message; --輸出結果
                COMMIT;

                --20190827 Add by Angela RecLog Start
                SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                                p_job_id => 'PG_BANSF_01.SP_BANSF',
                                p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_04)：勞保年金統計檔現制一次給付金額計算資料修改',
                                p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:(1)');

                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '1',
                                     '(DB)SP_BANSF_04：勞保年金統計檔現制一次給付金額計算資料修改(結束)',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

                v_o_flag := 'N';
                --20190827 Add by Angela RecLog End;
        End;
    End SP_BANSF_04;

    Procedure SP_BANSF_05(
        P_Insuym      In Varchar2, --處理年月
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    ) Is
    Begin
        -- 修改紀錄：
        -- 維護人員        日期       說明
        -- -----------   --------  ----------------------------------------------------
        -- rose          20150904   v1.0
        -- ============================================================================
        --                          <<<<<<<<< 變數區 >>>>>>>>>>
        -------------------------------------------------------------------------------
        Declare
            -- ============================================================================
            --sw_found BOOLEAN := TRUE; --找到資料
            -- ============================================================================
            V_Out_Cnt       Number(10) := 0; --輸出新增筆數
            V_Exception_Cnt Integer := 0; --EXCEPTION錯誤筆數
            --_Format_Date   Date; --DATE格式欄位

            -- ============================================================================
            -- CURSOR C_BANSF_REF
            -------------------------------------------------------------------------------
            --家屬後續請領情形修改 年月為191101代表全部修改)
            CURSOR C_BANSF IS
                select M.apno,
                       m.seqno,
                       M.issuym,
                       M.payym,
                       M.EVTIDNNO,
                       case
                           when BMAPNO = '49' and BMPAYDTE is not null and
                                PBB.BMCHKAMT + PBB.BMADJAMTS > 0 then
                            '1'
                           when BMAPNO = '49' and BMPAYDTE is null and
                                PBB.BMNOPDATE is null Then
                            '5'
                           when BAAPNO = 'S' and BAA.PAYYMS is not null then
                            '2'
                           when BAAPNO = 'S' and BAA.PAYYMS is null and
                                BAA.CASETYP <> '3' then
                            '6'
                           when BAAPNO = 'L' and BAA.CLOSECAUSE = '05' then
                            '3'
                           when (M.LUMSUM - M.ANNUAMT) <= 0 or
                                M.EVDATE < '20070101' or M.HBEDMK = 'N' or
                                (baa.paykind in ('45', '48') and
                                baa.CLOSECAUSE = '03') Then
                            '4'
                           else
                            '7'
                       end as OPTION_MK
                  from bansf M
                  left join (select distinct '49' BMAPNO,
                                             substr(BMEVIDNO, 1, 10) BMEVIDNO,
                                             BMPAYDTE,
                                             BMCHKAMT,
                                             BMADJAMTS,
                                             BMNOPDATE
                               from PBBMSA
                              where substr(BMAPNO, 5, 2) = '49') PBB
                    on PBB.BMEVIDNO = m.EVTIDNNO
                  left join (select substr(apno, 1, 1) as BAAPNO,
                                    substr(EVTIDNNO, 1, 10) as EVTIDNNO,
                                    PAYYMS,
                                    CASETYP,
                                    CLOSECAUSE,
                                    seqno,
                                    paykind
                               from BAAPPBASE BAA
                              where (APITEM = '8' and substr(apno, 1, 1) = 'S' and
                                    seqno = '0000')
                                 OR (substr(apno, 1, 1) = 'L' and seqno = '0000')) BAA
                    on BAA.EVTIDNNO = m.EVTIDNNO
                 where M.payno in ('45', '48')
                   And M.seqno = '0000'
                   And M.EVTDIEDATE is not null
                      --And (M.issuym=P_Insuym or P_Insuym='191101')
                   And (M.issuym = P_Insuym)
                 order by M.apno,
                          m.seqno,
                          M.issuym,
                          M.payym,
                          (case
                              when OPTION_MK = '1' then
                               '7'
                              when OPTION_MK = '5' then
                               '6'
                              when OPTION_MK = '2' then
                               '5'
                              when OPTION_MK = '6' then
                               '4'
                              when OPTION_MK = '4' then
                               '3'
                              when OPTION_MK = '3' then
                               '2'
                              when OPTION_MK = '7' then
                               '1'
                          end);

            CURSOR D_BANSF IS
                select M.apno,
                       m.seqno,
                       M.issuym,
                       M.payym,
                       M.EVTIDNNO,
                       case
                           when BMAPNO = '49' and BMPAYDTE is not null and
                                PBB.BMCHKAMT + PBB.BMADJAMTS > 0 then
                            '1'
                           when BMAPNO = '49' and BMPAYDTE is null and
                                PBB.BMNOPDATE is null Then
                            '5'
                           when BAAPNO = 'S' and BAA.PAYYMS is not null then
                            '2'
                           when BAAPNO = 'S' and BAA.PAYYMS is null and
                                BAA.CASETYP <> '3' then
                            '6'
                           when BAAPNO = 'L' and BAA.CLOSECAUSE = '05' then
                            '3'
                           when (M.LUMSUM - M.ANNUAMT) <= 0 or
                                M.EVDATE < '20070101' or M.HBEDMK = 'N' or
                                (baa.paykind in ('45', '48') and
                                baa.CLOSECAUSE = '03') Then
                            '4'
                           else
                            '7'
                       end as OPTION_MK
                  from bansf M
                  left join (select distinct '49' BMAPNO,
                                             substr(BMEVIDNO, 1, 10) BMEVIDNO,
                                             BMPAYDTE,
                                             BMCHKAMT,
                                             BMADJAMTS,
                                             BMNOPDATE
                               from PBBMSA
                              where substr(BMAPNO, 5, 2) = '49') PBB
                    on PBB.BMEVIDNO = m.EVTIDNNO
                  left join (select substr(apno, 1, 1) as BAAPNO,
                                    substr(EVTIDNNO, 1, 10) as EVTIDNNO,
                                    PAYYMS,
                                    CASETYP,
                                    CLOSECAUSE,
                                    seqno,
                                    paykind
                               from BAAPPBASE BAA
                              where (APITEM = '8' and substr(apno, 1, 1) = 'S' and
                                    seqno = '0000')
                                 OR (substr(apno, 1, 1) = 'L' and seqno = '0000')) BAA
                    on BAA.EVTIDNNO = m.EVTIDNNO
                 where M.payno in ('45', '48')
                   And M.seqno = '0000'
                   And M.EVTDIEDATE is not null
                      --   And (M.issuym=P_Insuym or P_Insuym='191101')
                      --   And (M.issuym='191101')
                   And (M.issuym <> P_Insuym)
                 order by M.apno,
                          m.seqno,
                          M.issuym,
                          M.payym,
                          (case
                              when OPTION_MK = '1' then
                               '7'
                              when OPTION_MK = '5' then
                               '6'
                              when OPTION_MK = '2' then
                               '5'
                              when OPTION_MK = '6' then
                               '4'
                              when OPTION_MK = '4' then
                               '3'
                              when OPTION_MK = '3' then
                               '2'
                              when OPTION_MK = '7' then
                               '1'
                          end);
            --'201312'
            ---    /*and A.apno='K00000000183' and A.evtidnno='G100551723'*/;
            -- ============================================================================
            -- procedure
            -------------------------------------------------------------------------------
            -- 輸出結果
            PROCEDURE p_output_message IS
            BEGIN
                Dbms_Output.Put_Line('修改bansf筆數=' || V_Out_Cnt);
                p_return_code := p_return_code || '修改bansf筆數=' || V_Out_Cnt || ',';
            End;

            -------------------------------------------------------------------------------
            -- 顯示LOG內容
            PROCEDURE log_msg(p_msg IN VARCHAR2) IS
            BEGIN
                IF v_exception_cnt > 100 THEN
                    DBMS_OUTPUT.put_line('EXCEPTION錯誤超過100個！');
                    p_return_code := p_return_code || 'EXCEPTION錯誤超過100個！' || ',';
                    raise_application_error(-20001, 'EXCEPTION錯誤超過100個！');
                END IF;

                DBMS_OUTPUT.put_line(p_msg);
                p_return_code := p_return_code || p_msg || ',';
            End;

            PROCEDURE p_bansf_proc Is
            Begin

                V_Out_Cnt := 0;
                For r_bansf_rec In C_BANSF Loop
                    UPDATE BANSF
                       SET OPTION_MK = r_bansf_rec.OPTION_MK
                     WHERE APNO = r_bansf_rec.apno
                       and issuym = r_bansf_rec.issuym
                       and payym = r_bansf_rec.payym
                       and EVTIDNNO = r_bansf_rec.EVTIDNNO;
                    V_Out_Cnt := V_Out_Cnt + 1;
                    If Mod(V_Out_Cnt, 50000) = 0 Then
                        Commit;
                    END IF;
                END LOOP;
                For r_bansf_rec In D_BANSF Loop
                    UPDATE BANSF
                       SET OPTION_MK = r_bansf_rec.OPTION_MK
                     WHERE APNO = r_bansf_rec.apno
                       and issuym = r_bansf_rec.issuym
                       and payym = r_bansf_rec.payym
                       and EVTIDNNO = r_bansf_rec.EVTIDNNO;
                    V_Out_Cnt := V_Out_Cnt + 1;
                    If Mod(V_Out_Cnt, 50000) = 0 Then
                        Commit;
                    END IF;
                END LOOP;
            EXCEPTION
                WHEN OTHERS Then
                    V_Exception_Cnt := V_Exception_Cnt + 1;
                    DBMS_OUTPUT.put_line('update Data發生錯誤,錯誤代碼=' || sqlcode || ',' ||
                                         '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code || 'update Data發生錯誤,錯誤代碼=' ||
                                     sqlcode || ',' || '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BANSF_05：更新後續請領註記(BANSF.OPTION_MK)發生錯誤***(E18)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;
            -- ============================================================================
            --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
            -------------------------------------------------------------------------------
        Begin
            DBMS_OUTPUT.put_line('5.勞保年金統計檔家屬後續請領情形修改' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
            p_return_code := p_return_code || '5.勞保年金統計檔家屬後續請領情形修改' ||
                             To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            --   p_return_code := 'OK';  --回傳訊息
            --初期處理------------------------------------------------------
            BEGIN
                -- 檢查參數內容
                -- 處理年月
                Dbms_Output.Put_Line('處理年月=' || P_Insuym);
                p_return_code := p_return_code || '處理年月=' || P_Insuym || ',';
                --v_format_date := TO_DATE(P_Insuym || '01', 'YYYYMMDD');
            EXCEPTION
                WHEN OTHERS THEN
                    DBMS_OUTPUT.put_line('輸入參數資料錯誤EXCEPTION！');
                    p_return_code := p_return_code || '輸入參數資料錯誤EXCEPTION！' || ',';
                    --        p_return_code := 'NO';  --回傳訊息
                    Dbms_Output.Put_Line('初期處理失敗：錯誤代碼=' || Sqlcode || ' ， ' ||
                                         '錯誤訊息=' || Sqlerrm);
                    p_return_code := p_return_code || '初期處理失敗：錯誤代碼=' || Sqlcode ||
                                     ' ， ' || '錯誤訊息=' || Sqlerrm || ',';
                    COMMIT;
                    Return;
            END;
            --主程序處理=========================================================================

            --20190827 Add by Angela RecLog Start
            v_o_flag := '0';
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_05)：勞保年金統計檔家屬後續請領情形修改',
                            p_memo   => '開始時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 '0',
                                 '(DB)SP_BANSF_05：勞保年金統計檔家屬後續請領情形修改(開始)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            --20190827 Add by Angela RecLog End

            --1.現制一次給付金額計算資料修改
            P_Bansf_Proc;

            If V_Exception_Cnt > 0 Then
                raise_application_error(-20001,
                                        '勞保年金統計檔家屬後續請領情形修改處理失敗！');
                v_o_flag := '1';
            ELSE
                COMMIT;
            END IF;

            --==========================================================================================

            --結束處理
            --DBMS_OUTPUT.put_line('EXCEPTION錯誤筆數＝'||v_exception_cnt);
            IF v_exception_cnt > 0 THEN
                Rollback;
                --      P_Return_Code := 'NO';  --回傳訊息
                DBMS_OUTPUT.put_line('修改勞保年金統計檔家屬後續請領情形失敗！' ||
                                     To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || '修改勞保年金統計檔家屬後續請領情形失敗！' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
                v_o_flag := '1';
            Else
                DBMS_OUTPUT.put_line('修改勞保年金統計檔家屬後續請領情形成功！' ||
                                     To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || '修改勞保年金統計檔家屬後續請領情形成功！' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            END IF;

            p_output_message; --輸出結果
            COMMIT;

            --20190827 Add by Angela RecLog Start
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_05)：勞保年金統計檔家屬後續請領情形修改',
                            p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:('||v_o_flag||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 v_o_flag,
                                 '(DB)SP_BANSF_05：勞保年金統計檔家屬後續請領情形修改(結束)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

            if v_o_flag <> '0' then
                v_o_flag := 'N';
            else
                v_o_flag := 'E';
            end if;
            --20190827 Add by Angela RecLog End;
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                DBMS_OUTPUT.put_line('勞保年金統計檔家屬後續請領情形修改處理失敗！' || SQLCODE ||
                                     ' - ' || SQLERRM);
                p_return_code := p_return_code || '勞保年金統計檔家屬後續請領情形修改處理失敗！' ||
                                 SQLCODE || ' - ' || SQLERRM || ',';
                --      p_return_code := 'NO';  --回傳訊息
                p_output_message; --輸出結果
                COMMIT;

                --20190827 Add by Angela RecLog Start
                SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                                p_job_id => 'PG_BANSF_01.SP_BANSF',
                                p_step   => '產生勞保年金統計檔資料作業(SP_BANSF_05)：修改勞保年金統計檔家屬後續請領情形失敗',
                                p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:(1)');

                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '1',
                                     '(DB)SP_BANSF_05：修改勞保年金統計檔家屬後續請領情形失敗(結束)',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

                v_o_flag := 'N';
                --20190827 Add by Angela RecLog End;
        End;
    End SP_BANSF_05;

    Procedure UPDATE_NULL(
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    ) Is
    Begin
        declare
            v_apno    bansf.apno%type;
            v_issuym  bansf.issuym%type;
            v_payym   bansf.payym%type;
            v_ubno    bansf.ubno%type;
            v_lsubno  bansf.lsubno%type;
            v_ubtype  bansf.ubtype%type;
            v_inds    bansf.inds%type;
            v_hincd   bansf.hincd%type;
            v_idsta   bansf.idsta%type;
            v_area    bansf.area%type;
            v_clsqty  bansf.clsqty%type;
            v_paykind bansf.paykind%type;
            v_payno   bansf.payno%type;
            v_count   integer;

            CURSOR C_ms_onlycd IS
                select *
                  from bansf
                 where ubno is null
                   and payno in ('56', '59');

        begin
            --20190827 Add by Angela RecLog Start
            v_o_flag := '0';
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(UPDATE_NULL)：更新給付種類(BANSF.PAYNO)',
                            p_memo   => '開始時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 '0',
                                 '(DB)UPDATE_NULL：更新給付種類(BANSF.PAYNO)(開始)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            --20190827 Add by Angela RecLog End

            update bansf A
               set payno =
                   (select distinct case
                                        when b.Apitem = '7' then
                                         '59'
                                        when B.Apitem = '8' then
                                         '56'
                                        else
                                         '55'
                                    end
                      from bansf
                      left join ba.baappbase B
                        on B.APNO = bansf.apno
                       and B.seqno = bansf.seqno
                     where bansf.ubno is null
                       and bansf.payno = '55'
                       and Bansf.APNO = rtrim(A.apno)
                       and bansf.seqno = rtrim(A.seqno)
                       and bansf.issuym = rtrim(A.issuym)
                       and bansf.payym = rtrim(A.payym))
             where A.ubno is null
               and A.payno = '55';

            --1.取出異常資料
            v_count := 0;
            For r_out_rec In C_ms_onlycd Loop
                v_apno  := r_out_rec.apno;
                v_payno := r_out_rec.payno;
                if v_payno = '56' then
                    v_paykind := 'L';
                else
                    v_paykind := 'K';
                end if;
                v_issuym := r_out_rec.issuym;
                v_payym  := r_out_rec.payym;
                --2.異常資料至field，改後比對，取回須修改資料
                for r_data_rec in (select distinct A.issuym,
                                                   A.payym,
                                                   A.ubno,
                                                   A.lsubno,
                                                   A.ubtype,
                                                   A.inds,
                                                   A.hincd,
                                                   A.idsta,
                                                   A.area,
                                                   A.clsqty
                                     from bansf A
                                     left join bansf B
                                       on B.issuym >= A.issuym
                                      and B.payym >= A.payym
                                      and B.evtidnno = A.evtidnno
                                    where A.paykind = v_paykind
                                      and B.payno = v_payno
                                      and B.ubno is null
                                      and B.apno = v_apno
                                      and rownum < 2
                                    order by A.issuym desc, A.payym desc) Loop
                    --3.update data
                    v_ubno   := r_data_rec.ubno;
                    v_lsubno := r_data_rec.lsubno;
                    v_ubtype := r_data_rec.ubtype;
                    v_inds   := r_data_rec.inds;
                    v_hincd  := r_data_rec.hincd;
                    v_idsta  := r_data_rec.idsta;
                    v_area   := r_data_rec.area;
                    v_clsqty := r_data_rec.clsqty;

                    --取得更改前inskd

                    update bansf
                       set ubno   = v_ubno,
                           lsubno = v_lsubno,
                           ubtype = v_ubtype,
                           inds   = v_inds,
                           hincd  = v_hincd,
                           idsta  = v_idsta,
                           area   = v_area,
                           clsqty = v_clsqty
                     where issuym = v_issuym
                       and payym = v_payym
                       and apno = v_apno
                       and ubno is null;
                    commit;

                    v_count := v_count + 1;

                End Loop;
            End Loop;

            DBMS_OUTPUT.put_line('改完筆數:');
            DBMS_OUTPUT.put_line(v_count);
            p_return_code :=  p_return_code || '改完筆數:' || v_count ||',';

            --20190827 Add by Angela RecLog Start
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(UPDATE_NULL)：更新給付種類(BANSF.PAYNO)',
                            p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:('||v_o_flag||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 v_o_flag,
                                 '(DB)UPDATE_NULL：更新給付種類(BANSF.PAYNO)(結束)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            v_o_flag := 'E';
            --20190827 Add by Angela RecLog End;
        end;
    end UPDATE_NULL;

    PROCEDURE SP_UPDATE_BANSF_FROMTMP(
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    ) Is
    Begin
        -- ============================================================================
        --                          <<<<<<<<< 變數區 >>>>>>>>>>
        -------------------------------------------------------------------------------
        DECLARE
            --R_BANSF_Rec  bansf%Rowtype; --bansf  RECORD
            R_Source_Rec Badapr%Rowtype; --badapr record
            -- ============================================================================
            --sw_found BOOLEAN := TRUE; --找到資料
            -- ============================================================================

            V_TEMP_In_Cnt       Number(10) := 0; --讀取 TEMP筆數
            V_Bansf_Update_Cnt  Number(10) := 0; --輸出Bansf Updat 筆數
            V_TEMP_NoUpdate_Cnt Number(10) := 0; --輸出Bansf Updat 筆數
            v_Update_Cnt        Number(10) := 0; --輸出Bansf Updat 筆數
            V_Exception_Cnt     Number(10) := 0; --

            v_key_apno   BANSF.APNO%TYPE;
            v_key_seqno  BANSF.SEQNO%TYPE;
            v_key_issuym BANSF.ISSUYM%TYPE;
            v_key_payym  BANSF.PAYYM%TYPE;
            v_key_payno  BANSF.PAYNO%TYPE;

            v_Nitrmy       BANSF.NITRMY%TYPE; --'年資-年';
            v_Nitrmm       BANSF.NITRMM%TYPE; --'年資-月';
            v_Pwage        BANSF.PWAGE%TYPE; --'平均薪資';
            v_Oldaamt      BANSF.OLDAAMT%TYPE; --'第一式金額(勞保給付金額)';
            v_Oldbamt      BANSF.OLDBAMT%TYPE; --'第二式金額(勞保給付金額)';
            v_Oldextrarate BANSF.OLDEXTRARATE%TYPE; --(老年、遺屬)展延/減額比率
            v_Qualcount    BANSF.QUALCOUNT%TYPE; --'符合眷屬(遺屬)人數';
            v_Oldrate      BANSF.OLDRATE%TYPE;
            v_Cutamt       BANSF.CUTAMT%TYPE; --'應扣失能金額';
            v_Lecomamt     BANSF.LECOMAMT%TYPE; --'己扣失能金額';

            -- ============================================================================
            -- CURSOR C_BANSF
            -------------------------------------------------------------------------------
            /*
            CURSOR C_BANSF(S_KEY_APNO   IN VARCHAR2,
                           S_KEY_SEQNO  IN VARCHAR2,
                           S_KEY_ISSUYM IN VARCHAR2,
                           S_KEY_PAYYM  IN VARCHAR2,
                           S_KEY_PAYNO  IN VARCHAR2) IS
                SELECT *
                  FROM BANSF A
                 WHERE A.PAYNO NOT IN ('36', '39', '49')
                   AND A.APNO = TRIM(S_KEY_APNO)
                   AND A.SEQNO = TRIM(S_KEY_SEQNO)
                   AND A.ISSUYM = TRIM(S_KEY_ISSUYM)
                   AND A.PAYYM = TRIM(S_KEY_PAYYM)
                   AND A.PAYKIND = TRIM(S_KEY_PAYNO);
            */
            ------------------------------------------------------------------------------
            CURSOR C_TEMP IS
                SELECT t1.APNO         AS APNO,
                       t1.SEQNO        AS SEQNO,
                       t1.ISSUYM       AS ISSUYM,
                       t1.PAYYM        AS PAYYM,
                       t1.PAYKIND      AS PAYKIND,
                       t1.MTESTMK      AS MTESTMK,
                       t1.BENIDN       AS BENIDN,
                       t1.ISSUEAMT     AS ISSUEAMT,
                       t1.APLPAYAMT    AS APLPAYAMT,
                       t1.APLPAYMK     AS APLPAYMK,
                       t1.APLPAYDATE   AS APLPAYDATE,
                       t1.MANCHKMK     AS MANCHKMK,
                       t1.PROCTIME     AS PROCTIME,
                       t2.INSAVGAMT    AS INSAVGAMT,
                       t2.ITRMY        AS ITRMY,
                       t2.ITRMD        AS ITRMD,
                       t2.NITRMY       AS NITRMY,
                       t2.NITRMM       AS NITRMM,
                       t2.NOLDTY       AS NOLDTY,
                       t2.NOLDTM       AS NOLDTM,
                       t2.VALSENIY     AS VALSENIY,
                       t2.VALSENIM     AS VALSENIM,
                       t2.APLPAYSENIY  AS APLPAYSENIY,
                       t2.APLPAYSENIM  AS APLPAYSENIM,
                       t2.OLDEXTRARATE AS OLDEXTRARATE,
                       t2.OLDRATE      AS OLDRATE,
                       t2.OLDAAMT      AS OLDAAMT,
                       t2.OLDBAMT      AS OLDBAMT,
                       t2.QUALCOUNT    AS QUALCOUNT,
                       t2.REMAINAMT    AS REMAINAMT,
                       t2.LECOMAMT     AS LECOMAMT
                  FROM (SELECT T1.APNO,
                               T1.SEQNO,
                               T1.ISSUYM,
                               T1.PAYYM,
                               T1.PAYKIND,
                               T1.MTESTMK,
                               T1.BENIDN,
                               T1.ISSUEAMT,
                               T1.APLPAYAMT,
                               T1.APLPAYMK,
                               T1.APLPAYDATE,
                               T1.MANCHKMK,
                               T1.PROCTIME
                          FROM ba.BADAPR T1
                         WHERE T1.APNO IN
                               (SELECT DISTINCT A.APNO
                                  FROM ba.BAAPPBASE A
                                 WHERE (A.APNO LIKE 'L%' OR A.APNO LIKE 'K%')
                                   AND A.CASETYP = '4'
                                   AND A.PROCSTAT = '90')
                           AND T1.SEQNO <> '0000'
                           AND T1.BENEVTREL NOT IN ('F', 'Z')
                           AND T1.MTESTMK = 'F'
                           AND T1.APLPAYMK = '3'
                           AND T1.APLPAYDATE IS NOT NULL
                           AND T1.ISSUEAMT > 0
                           AND T1.PAYKIND <> '49') t1,
                       (SELECT T2.APNO,
                               T2.SEQNO,
                               T2.INSAVGAMT,
                               T2.ITRMY,
                               T2.ITRMD,
                               T2.NITRMY,
                               T2.NITRMM,
                               T2.NOLDTY,
                               T2.NOLDTM,
                               T2.VALSENIY,
                               T2.VALSENIM,
                               T2.APLPAYSENIY,
                               T2.APLPAYSENIM,
                               T2.OLDEXTRARATE,
                               T2.OLDRATE,
                               T2.OLDAAMT,
                               T2.OLDBAMT,
                               T2.QUALCOUNT,
                               T2.REMAINAMT,
                               T2.LECOMAMT
                          FROM ba.BADAPR T2
                         WHERE T2.APNO IN
                               (SELECT DISTINCT B1.APNO
                                  FROM ba.BAAPPBASE B1
                                 WHERE (B1.APNO LIKE 'L%' OR B1.APNO LIKE 'K%')
                                   AND B1.CASETYP = '4'
                                   AND B1.PROCSTAT = '90')
                           AND T2.SEQNO = '0000'
                           AND T2.MTESTMK = 'F'
                           AND T2.APLPAYMK = '3'
                           AND T2.APLPAYDATE IS NOT NULL
                           AND T2.ISSUYM =
                               (SELECT MAX(B2.ISSUYM)
                                  FROM ba.BADAPR B2
                                 WHERE B2.APNO = T2.APNO
                                   AND B2.SEQNO = '0000'
                                   AND B2.MTESTMK = 'F'
                                   AND B2.APLPAYMK = '3'
                                   AND B2.APLPAYDATE IS NOT NULL
                                   AND B2.ISSUEAMT > 0
                                   AND B2.INSAVGAMT > 0
                                   AND B2.PAYKIND <> '49')
                           AND T2.PAYYM = (SELECT MAX(B3.PAYYM)
                                             FROM ba.BADAPR B3
                                            WHERE B3.APNO = T2.APNO
                                              AND B3.SEQNO = '0000'
                                              AND B3.ISSUYM = T2.ISSUYM
                                              AND B3.MTESTMK = 'F'
                                              AND B3.APLPAYMK = '3'
                                              AND B3.APLPAYDATE IS NOT NULL
                                              AND B3.ISSUEAMT > 0
                                              AND B3.INSAVGAMT > 0
                                              AND B3.PAYKIND <> '49')
                           AND T2.PAYKIND <> '49') t2
                 WHERE t1.APNO = t2.APNO;
            -------------------------------------------------------------------------------
            -- procedure
            -------------------------------------------------------------------------------
            -- 輸出結果
            PROCEDURE P_OUTPUT_MESSAGE IS
            BEGIN
                Dbms_Output.Put_Line('select   TEMP筆數=' || V_TEMP_In_Cnt);
                p_return_code := p_return_code || 'select   TEMP筆數=' || V_TEMP_In_Cnt || ',';
                Dbms_Output.Put_Line('noupdate bansf筆數=' || V_TEMP_NoUpdate_Cnt);
                p_return_code := p_return_code || 'noupdate bansf筆數=' ||  V_TEMP_NoUpdate_Cnt || ',';
                Dbms_Output.Put_Line('update   bansf筆數=' || V_Bansf_Update_Cnt);
                p_return_code := p_return_code || 'update   bansf筆數=' || V_Bansf_Update_Cnt || ',';
            End;
            -------------------------------------------------------------------------------

            ---------------------------------------------------------------------------------
            --  bansf update
            -------------------------------------------------------------------------------
            PROCEDURE p_update_bansf Is
            Begin
                For R_Source_Rec In C_TEMP Loop
                    V_TEMP_In_Cnt := V_TEMP_In_Cnt + 1;
                    -- KEY
                    v_key_apno   := TRIM(R_Source_Rec.APNO); --'受理編號';
                    v_key_seqno  := TRIM(R_Source_Rec.SEQNO); --'序號';
                    v_key_issuym := TRIM(R_Source_Rec.ISSUYM); --'核定年月';
                    v_key_payym  := TRIM(R_Source_Rec.Payym); --'給付年月';
                    v_key_payno  := TRIM(R_Source_Rec.PAYKIND); --'給付種類';

                    -- UPDATE VALUE
                    v_Nitrmy       := nvl(R_Source_Rec.Nitrmy, 0); --'年資-年
                    v_Nitrmm       := nvl(R_Source_Rec.Nitrmm, 0); --'年資-月
                    v_Pwage        := nvl(R_Source_Rec.INSAVGAMT, 0); --'平均薪資';
                    v_Oldaamt      := nvl(R_Source_Rec.Oldaamt, 0); --'第一式金額(勞保給付金額)';
                    v_Oldbamt      := nvl(R_Source_Rec.Oldbamt, 0); --'第二式金額(勞保給付金額)';
                    v_Oldextrarate := nvl(R_Source_Rec.OLDEXTRARATE, 0); --(老年、遺屬)展延/減額比率 ;
                    v_Qualcount    := nvl(R_Source_Rec.Qualcount, 0); --'符合眷屬(遺屬)人數';
                    v_Oldrate      := nvl(R_Source_Rec.oldrate, 0);
                    v_Cutamt       := nvl(R_Source_Rec.REMAINAMT, 0); --'應扣失能金額';
                    v_Lecomamt     := nvl(R_Source_Rec.Lecomamt, 0); --'己扣失能金額';

                    Update bansf
                       set Nitrmy       = v_Nitrmy,
                           Nitrmm       = v_Nitrmm,
                           pwage        = v_Pwage,
                           oldaamt      = v_Oldaamt,
                           oldbamt      = v_Oldbamt,
                           oldextrarate = v_Oldextrarate,
                           qualcount    = v_Qualcount,
                           oldrate      = v_Oldrate,
                           cutamt       = v_Cutamt,
                           lecomamt     = v_Lecomamt
                     WHERE bansf.apno = v_key_apno
                       AND bansf.seqno = v_key_seqno
                       AND bansf.issuym = v_key_issuym
                       AND bansf.payym = v_key_payym
                       AND bansf.payno = v_key_payno
                       AND BANSF.PAYNO NOT IN ('36', '39', '49');

                    v_Update_Cnt := SQL%ROWCOUNT;

                    IF v_Update_Cnt = 0 THEN
                        V_TEMP_NoUpdate_Cnt := V_TEMP_NoUpdate_Cnt +
                                               v_Update_Cnt;
                    ELSE
                        V_Bansf_Update_Cnt := V_Bansf_Update_Cnt + v_Update_Cnt;
                    END IF;
                End Loop;
            EXCEPTION
                WHEN OTHERS Then
                    DBMS_OUTPUT.put_line(v_key_APNO);
                    p_return_code   := p_return_code || v_key_APNO || ',';
                    V_Exception_Cnt := V_Exception_Cnt + 1;
                    DBMS_OUTPUT.put_line('p_update_bansf 發生錯誤,錯誤代碼=' || sqlcode || ',' || '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code || 'p_update_bansf 發生錯誤,錯誤代碼=' || sqlcode || ',' || '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_UPDATE_BANSF_FROMTMP：更新年資、平均薪資、金額發生錯誤，APNO='||v_key_APNO||'***(E19)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;

            -- ============================================================================
            --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
            -------------------------------------------------------------------------------
        Begin
            --20190827 Add by Angela RecLog Start
            v_o_flag := '0';
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_UPDATE_BANSF_FROMTMP)：更新年資、平均薪資、金額',
                            p_memo   => '開始時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 '0',
                                 '(DB)SP_UPDATE_BANSF_FROMTMP：更新年資、平均薪資、金額(開始)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            --20190827 Add by Angela RecLog End

            DBMS_OUTPUT.put_line('----- update bansf 35 38 ------');
            p_return_code := p_return_code || '----- update bansf 35 38 ------' || ',';
            --   p_return_code := 'OK';  --回傳訊息
            --主程序處理=========================================================================
            --1.update bansf
            Dbms_Output.Put_Line('update bansf ' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
            p_return_code := p_return_code || 'update bansf ' ||
                             To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            p_update_bansf;
            --==========================================================================================
            --結束處理
            DBMS_OUTPUT.put_line('EXCEPTION 錯誤筆數＝' || v_exception_cnt);
            p_return_code := p_return_code || 'EXCEPTION 錯誤筆數＝' ||
                             v_exception_cnt || ',';
            IF v_exception_cnt > 0 THEN
                Rollback;
                --      P_Return_Code := 'NO';  --回傳訊息
                DBMS_OUTPUT.put_line('update bansf  失敗！' ||
                                     To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || 'update bansf  失敗！' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
                v_o_flag := '1';
            Else
                DBMS_OUTPUT.put_line('update bansf  成功！' ||
                                     To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || 'update bansf  成功！' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            END IF;
            COMMIT;

            --20190827 Add by Angela RecLog Start
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_UPDATE_BANSF_FROMTMP)：更新年資、平均薪資、金額',
                            p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:('||v_o_flag||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 v_o_flag,
                                 '(DB)SP_UPDATE_BANSF_FROMTMP：更新年資、平均薪資、金額(結束)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

            if v_o_flag <> '0' then
                v_o_flag := 'N';
            else
                v_o_flag := 'E';
            end if;
            --20190827 Add by Angela RecLog End;

            p_output_message; --輸出結果
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                DBMS_OUTPUT.put_line('update bansf  失敗！' || SQLCODE || ' - ' ||
                                     SQLERRM);
                p_return_code := p_return_code || 'update bansf  失敗！' ||
                                 SQLCODE || ' - ' || SQLERRM || ',';
                --      p_return_code := 'NO';  --回傳訊息
                p_output_message; --輸出結果
                COMMIT;

                --20190827 Add by Angela RecLog Start
                SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                                p_job_id => 'PG_BANSF_01.SP_BANSF',
                                p_step   => '產生勞保年金統計檔資料作業(SP_UPDATE_BANSF_FROMTMP)：更新年資、平均薪資、金額',
                                p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:(1)');

                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '1',
                                     '(DB)SP_UPDATE_BANSF_FROMTMP：更新年資、平均薪資、金額(結束)',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

                v_o_flag := 'N';
                --20190827 Add by Angela RecLog End;
        End;
    End SP_UPDATE_BANSF_FROMTMP;

    PROCEDURE SP_UPDATE_PWAGE(
        P_Payym1      In Varchar2, --起始處理年月
        P_Payym2      In Varchar2, --結束處理年月
        v_i_mmJobNO   in varChar2,
        v_i_bajobid   in varChar2,
        p_return_code OUT VARCHAR2, --回傳訊息OK:成功 or NO:失敗
        v_o_flag      out varChar2
    ) Is
    Begin
        -- ============================================================================
        --                          <<<<<<<<< 變數區 >>>>>>>>>>
        -------------------------------------------------------------------------------
        DECLARE
            --R_Badapr_Rec        Badapr%Rowtype; --badapr_ref RECORD
            R_Source_Badapr_Rec Badapr%Rowtype; --badapr record
            -- ============================================================================
            --sw_found        BOOLEAN := TRUE; --找到資料
            sw_found_badapr BOOLEAN := TRUE; --找到資料
            -- ============================================================================
            -------------------------------------------------------------------------------
            V_Bansf_In_Cnt       Number(10) := 0; --讀取bansf筆數
            V_Bansf_NoUpdate_Cnt Number(10) := 0; --輸出Bansf Updat 筆數
            V_Bansf_Update_Cnt   Number(10) := 0; --輸出Bansf Updat 筆數
            V_Exception_Cnt      Number(10) := 0; --

            v_key_apno   BANSF.APNO%TYPE;
            v_key_seqno  BANSF.SEQNO%TYPE;
            v_key_payno  BANSF.PAYNO%TYPE;
            v_key_issuym BANSF.ISSUYM%TYPE;
            v_key_payym  BANSF.PAYYM%TYPE;
            v_key_code   BANSF.CODE%TYPE;

            v_Pwage        BANSF.PWAGE%TYPE; --'平均薪資';
            v_Oldaamt      BANSF.OLDAAMT%TYPE; --'第一式金額(勞保給付金額)';
            v_Oldbamt      BANSF.OLDBAMT%TYPE; --'第二式金額(勞保給付金額)';
            v_Oldextrarate BANSF.OLDEXTRARATE%TYPE; --(老年、遺屬)展延/減額比率
            v_Qualcount    BANSF.QUALCOUNT%TYPE; --'符合眷屬(遺屬)人數';
            v_Oldrate      BANSF.OLDRATE%TYPE;
            v_Cutamt       BANSF.CUTAMT%TYPE; --'應扣失能金額';
            v_Lecomamt     BANSF.LECOMAMT%TYPE; --'己扣失能金額';

            -- ============================================================================
            -- CURSOR C_BANSF
            -------------------------------------------------------------------------------
            CURSOR C_BANSF IS
                select *
                  from bansf
                 where bansf.seqno = '0000'
                   and bansf.payno in ('35', '37', '38')
                   --and bansf.payym between P_Payym1 and P_Payym2
                   and bansf.issuym between P_Payym1 and P_Payym2
                   and nvl(bansf.pwage, 0) = 0
                 order by apno, payym, payno;
            -------------------------------------------------------------------------------
            -- 依受理編號Apno及序號Seqno
            CURSOR C_BADAPR_SUPRECMK(S_KEY_APNO IN VARCHAR2,
                                     --S_PAYYM    IN VARCHAR2,
                                     S_PAYNO    IN VARCHAR2,
                                     S_SUPRECMK IN VARCHAR2) IS

                select *
                  from (SELECT *
                          FROM BADAPR
                         WHERE APNO = S_KEY_APNO
                           AND PAYKIND = S_PAYNO
                           And Mtestmk = 'F'
                           And Aplpaymk = '3'
                           AND SEQNO = '0000'
                           AND ((S_SUPRECMK is null and Suprecmk is null) or
                               Suprecmk =
                               decode(S_SUPRECMK, 'K', 'D', S_SUPRECMK))
                         Order By PAYYM DESC) A
                 where rownum < 2;
            -------------------------------------------------------------------------------
            -- procedure
            -------------------------------------------------------------------------------
            -- 輸出結果
            PROCEDURE P_OUTPUT_MESSAGE IS
            BEGIN
                Dbms_Output.Put_Line('select   bansf筆數=' || V_Bansf_In_Cnt);
                p_return_code := p_return_code || 'select   bansf筆數=' ||
                                 V_Bansf_In_Cnt || ',';
                Dbms_Output.Put_Line('update   bansf筆數=' || V_Bansf_Update_Cnt);
                p_return_code := p_return_code || 'update   bansf筆數=' ||
                                 V_Bansf_Update_Cnt || ',';
                Dbms_Output.Put_Line('noupdate bansf筆數=' ||
                                     V_Bansf_NoUpdate_Cnt);
                p_return_code := p_return_code || 'noupdate bansf筆數=' ||
                                 V_Bansf_NoUpdate_Cnt || ',';
            End;
            -------------------------------------------------------------------------------

            ---------------------------------------------------------------------------------
            --  bansf update
            -------------------------------------------------------------------------------
            PROCEDURE p_update_bansf Is
            Begin
                For r_bansf_rec In C_BANSF Loop
                    V_Bansf_In_Cnt := V_Bansf_In_Cnt + 1;
                    v_key_apno     := r_bansf_rec.APNO; --'受理編號';
                    v_key_seqno    := '0000'; --'序號';
                    v_key_payno    := r_bansf_rec.PAYNO; --'給付種類';
                    v_key_issuym   := r_bansf_rec.ISSUYM; --'核定年月';
                    v_key_payym    := r_bansf_rec.Payym; --'給付年月';
                    v_key_code     := r_bansf_rec.CODE;

                    --給付種類35,37,38存取核定檔
                    R_Source_Badapr_Rec := Null;
                    sw_found_badapr     := false;

                    For R_Source_Badapr_Rec In C_BAdapr_Suprecmk(v_key_apno,
                                                                 --v_key_payym,
                                                                 v_key_payno,
                                                                 v_key_code) Loop
                        sw_found_badapr := true;

                        v_Pwage        := nvl(R_Source_Badapr_Rec.Insavgamt, 0); --'平均薪資';
                        v_Oldaamt      := nvl(R_Source_Badapr_Rec.Oldaamt, 0); --'第一式金額(勞保給付金額)';
                        v_Oldbamt      := nvl(R_Source_Badapr_Rec.Oldbamt, 0); --'第二式金額(勞保給付金額)';
                        v_Oldextrarate := nvl(R_Source_Badapr_Rec.Oldextrarate, 0); --(老年、遺屬)展延/減額比率
                        v_Qualcount    := nvl(R_Source_Badapr_Rec.Qualcount, 0); --'符合眷屬(遺屬)人數';
                        v_Oldrate      := nvl(R_Source_Badapr_Rec.oldrate, 0);
                        v_Cutamt       := nvl(R_Source_Badapr_Rec.REMAINAMT, 0); --'應扣失能金額';
                        v_Lecomamt     := nvl(R_Source_Badapr_Rec.Lecomamt, 0); --'己扣失能金額';

                        V_Bansf_Update_Cnt := V_Bansf_Update_Cnt + 1;
                        Update bansf
                           set pwage        = v_Pwage,
                               oldaamt      = v_Oldaamt,
                               oldbamt      = v_Oldbamt,
                               oldextrarate = v_Oldextrarate,
                               qualcount    = v_Qualcount,
                               oldrate      = v_Oldrate,
                               cutamt       = v_Cutamt,
                               lecomamt     = v_Lecomamt
                         WHERE bansf.apno = v_key_apno
                           and bansf.seqno = '0000'
                           and bansf.payno = v_key_payno
                           and bansf.payym = v_key_payym
                           and nvl(bansf.pwage, 0) = 0;
                    End Loop;

                    If sw_found_badapr = false Then
                        --找尋不到badapr資料
                        V_Bansf_NoUpdate_Cnt := V_Bansf_NoUpdate_Cnt + 1;
                        --DBMS_OUTPUT.put_line('找不到 DAPADR APNO='||v_key_apno||' SEQNO='||v_key_seqno||' PAYKIND='||v_key_payno||' PAYYM='||v_key_payym||' ISSUYM='||v_key_issuym ||' CODE='||v_key_code);
                    END IF;
                End Loop;
            EXCEPTION
                WHEN OTHERS Then
                    DBMS_OUTPUT.put_line(v_key_APNO);
                    p_return_code   := p_return_code || v_key_APNO || ',';
                    V_Exception_Cnt := V_Exception_Cnt + 1;
                    DBMS_OUTPUT.put_line('p_update_bansf 發生錯誤,錯誤代碼=' ||
                                         sqlcode || ',' || '錯誤訊息=' || SQLERRM);
                    p_return_code := p_return_code ||
                                     'p_update_bansf 發生錯誤,錯誤代碼=' || sqlcode || ',' ||
                                     '錯誤訊息=' || SQLERRM || ',';

                    --20190827 Add by Angela RecLog Start
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_UPDATE_PWAGE：更新年資、平均薪資、金額發生錯誤，APNO='||v_key_APNO||'***(E20)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    --20190827 Add by Angela RecLog End;
            END;
            -- ============================================================================
            --                      <<<<<<<<< MAIN procedure >>>>>>>>>>
            -------------------------------------------------------------------------------
        Begin
            DBMS_OUTPUT.put_line('----- update bansf ------');
            p_return_code := p_return_code || '----- update bansf ------' || ',';
            --   p_return_code := 'OK';  --回傳訊息
            --初期處理------------------------------------------------------
            BEGIN
                -- 檢查參數內容
                -- 處理年月
                Dbms_Output.Put_Line('處理年月=' || P_Payym1 || '處理年月=' ||
                                     P_Payym2);
                p_return_code := p_return_code || '處理年月=' || P_Payym1 ||
                                 '處理年月=' || P_Payym2 || ',';
            EXCEPTION
                WHEN OTHERS THEN
                    DBMS_OUTPUT.put_line('輸入參數資料錯誤EXCEPTION！');
                    p_return_code := p_return_code || '輸入參數資料錯誤EXCEPTION！' || ',';
                    --        p_return_code := 'NO';  --回傳訊息
                    Dbms_Output.Put_Line('初期處理失敗：錯誤代碼=' || Sqlcode || ' ， ' ||
                                         '錯誤訊息=' || Sqlerrm);
                    p_return_code := p_return_code || '初期處理失敗：錯誤代碼=' || Sqlcode ||
                                     ' ， ' || '錯誤訊息=' || Sqlerrm || ',';
                    COMMIT;
                    Return;
            END;
            --主程序處理=========================================================================

            --20190827 Add by Angela RecLog Start
            v_o_flag := '0';
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_UPDATE_PWAGE)：更新年資、平均薪資、金額',
                            p_memo   => '開始時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 '0',
                                 '(DB)SP_UPDATE_PWAGE：更新年資、平均薪資、金額(開始)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            --20190827 Add by Angela RecLog End

            --1.update bansf
            Dbms_Output.Put_Line('update bansf' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
            p_return_code := p_return_code || 'update bansf' ||
                             To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            p_update_bansf;
            --==========================================================================================
            --結束處理
            DBMS_OUTPUT.put_line('EXCEPTION錯誤筆數＝' || v_exception_cnt);
            p_return_code := p_return_code || 'EXCEPTION錯誤筆數＝' ||
                             v_exception_cnt || ',';
            IF v_exception_cnt > 0 THEN
                Rollback;
                --      P_Return_Code := 'NO';  --回傳訊息
                DBMS_OUTPUT.put_line('update bansf 失敗！' || To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || 'update bansf 失敗！' || To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
                v_o_flag := '1';
            Else
                DBMS_OUTPUT.put_line('update bansf 成功！' ||
                                     To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS'));
                p_return_code := p_return_code || 'update bansf 成功！' ||
                                 To_Char(Sysdate, 'YYYY/MM/DD HH24:MI:SS') || ',';
            END IF;
            COMMIT;
            p_output_message; --輸出結果

            --20190827 Add by Angela RecLog Start
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_UPDATE_PWAGE)：更新年資、平均薪資、金額',
                            p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:('||v_o_flag||')');

            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 v_o_flag,
                                 '(DB)SP_UPDATE_PWAGE：更新年資、平均薪資、金額(結束)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

            if v_o_flag <> '0' then
                v_o_flag := 'N';
            else
                v_o_flag := 'E';
            end if;
            --20190827 Add by Angela RecLog End;
        EXCEPTION
            WHEN OTHERS THEN
                ROLLBACK;
                DBMS_OUTPUT.put_line('勞保年金統計核定檔失敗！' || SQLCODE || ' - ' ||
                                     SQLERRM);
                p_return_code := p_return_code || '勞保年金統計核定檔失敗！' || SQLCODE ||
                                 ' - ' || SQLERRM || ',';
                --      p_return_code := 'NO';  --回傳訊息
                p_output_message; --輸出結果
                COMMIT;

                --20190827 Add by Angela RecLog Start
                SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                                p_job_id => 'PG_BANSF_01.SP_BANSF',
                                p_step   => '產生勞保年金統計檔資料作業(SP_UPDATE_PWAGE)：更新年資、平均薪資、金額',
                                p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:(1)');

                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '1',
                                     '(DB)SP_UPDATE_PWAGE：更新年資、平均薪資、金額(結束)',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));

                v_o_flag := 'N';
                --20190827 Add by Angela RecLog End;
        End;
    End SP_UPDATE_PWAGE;
    
    
    PROCEDURE SP_BA_BANSF_GENDATA(
        v_i_issuym    in      varChar2,     
        v_i_mmJobNO   in      varChar2, 
        v_i_bajobid   in      varChar2, 
		v_o_flag      out     varChar2
    ) is 
        
        v_SUPRECMK    varChar2(1)  := '';
        v_37_PAMTS    Number       := 0;
        
        --失能年金案暫存檔
        Cursor c_CurKData is
            select APNO
                  ,ISSUYM
                  ,PAYYM
                  ,PAYNO 
                  ,PAMTS
                  ,SUPRECMK
                  ,APLPAYDATE
                  ,EVTIDNNO
                  ,EVTBRDATE
                  ,EVTNAME
                  ,APPDATE  
                  ,UBNO
                  ,EDATE 
                  ,SEX
                  ,EVTYPE
                  ,ADWKMK 
                  ,OTHERAMT 
                  ,EVCODE
                  ,INJNO
                  ,INJCL 
                  ,NACHGMK
              from (select t1.APNO as APNO
                          ,t2.ISSUYM as ISSUYM
                          ,t2.PAYYM as PAYYM
                          ,t2.PAYKIND as PAYNO 
                          ,deCode(trim(t2.SUPRECMK),'C',t2.SUPAMT,'D',(-t2.RECAMT),t2.BEFISSUEAMT) as PAMTS
                          ,t2.SUPRECMK as SUPRECMK
                          ,t2.APLPAYDATE
                          ,t1.EVTIDNNO as EVTIDNNO
                          ,t1.EVTBRDATE as EVTBRDATE
                          ,t1.EVTNAME as EVTNAME
                          ,t1.APPDATE as APPDATE  
                          ,t1.APUBNO as UBNO
                          ,t1.EVTJOBDATE as EDATE 
                          ,t1.EVTSEX as SEX
                          ,t3.EVTYP as EVTYPE
                          ,t2.ADWKMK as ADWKMK 
                          ,t2.OTHERAMT as OTHERAMT 
                          ,t3.EVCODE as EVCODE
                          ,t3.CRIINJDP1 as INJNO
                          ,t3.CRIINJCL1 as INJCL 
                          ,deCode(trim(t2.NACHGMK),'12'
                                                  ,deCode(nvl(trim(t2.ISSUEAMT),0),0,'A','')
                                                  ,'21'
                                                  ,deCode(nvl(trim(t2.ISSUEAMT),0),0,'B','')
                                                  ,t2.NACHGMK) as NACHGMK
                      from BAAPPBASE t1,BADAPR t2,BAAPPEXPAND t3
                     where t1.PAYKIND in ('35','37','38','39') 
                       and t2.PAYKIND in ('35','37','38','39')
                       and t1.APNO = t2.APNO
                       and t1.APNO = t3.APNO
                       and t1.SEQNO = t2.SEQNO
                       and t1.SEQNO = t3.SEQNO
                       and t1.SEQNO = '0000'
                       and t2.ISSUYM = v_i_issuym
                       and t2.MTESTMK = 'F'
                       and t2.APLPAYMK = '3'
                       and substr(t2.APLPAYDATE,1,6) = v_i_issuym) x
             where ((trim(x.SUPRECMK) is null or nvl(trim(x.SUPRECMK),' ') = ' ') and x.PAMTS > 0)
                or (x.SUPRECMK = 'C' and x.PAMTS > 0)
                or (x.SUPRECMK = 'D' and x.PAMTS < 0)
             order by x.APNO,x.PAYYM;
             
        --36案上線後再打開此註解
        --失能年金案(國併勞)暫存檔     
        /*
        Cursor c_CurK36Data is
            select APNO
                  ,ISSUYM
                  ,PAYYM
                  ,PAYNO 
                  ,PAMTS
                  ,SUPRECMK
                  ,APLPAYDATE
                  ,EVTIDNNO
                  ,EVTBRDATE
                  ,EVTNAME
                  ,APPDATE  
                  ,PWAGE  
                  ,YEAR_RANG
                  ,MON_RANG
                  ,SEX
              from (select t1.APNO as APNO
                          ,t2.ISSUYM as ISSUYM
                          ,t2.PAYYM as PAYYM
                          ,t2.PAYKIND as PAYNO 
                          ,deCode(trim(t2.SUPRECMK),'C',t2.SUPAMT,'D',(-t2.RECAMT),t2.BEFISSUEAMT) as PAMTS
                          ,t2.SUPRECMK as SUPRECMK
                          ,t2.APLPAYDATE
                          ,t1.EVTIDNNO as EVTIDNNO
                          ,t1.EVTBRDATE as EVTBRDATE
                          ,t1.EVTNAME as EVTNAME
                          ,t1.APPDATE as APPDATE  
                          ,t2.INSAVGAMT as PWAGE  
                          ,t2.NITRMY as YEAR_RANG
                          ,t2.NITRMM as MON_RANG
                          ,t1.EVTSEX as SEX
                      from BAAPPBASE t1,BADAPR t2,BAAPPEXPAND t3
                     where t1.PAYKIND = '36'
                       and t1.APNO = t2.APNO
                       and t1.APNO = t3.APNO
                       and t1.SEQNO = t2.SEQNO
                       and t1.SEQNO = t3.SEQNO
                       and t1.SEQNO = '0000'
                       and t2.ISSUYM = v_i_issuym
                       and t2.MTESTMK = 'F'
                       and t2.APLPAYMK = '3'
                       and substr(t2.APLPAYDATE,1,6) = v_i_issuym) x
             where ((trim(x.SUPRECMK) is null or nvl(trim(x.SUPRECMK),' ') = ' ') and x.PAMTS > 0)
                or (x.SUPRECMK = 'C' and x.PAMTS > 0)
                or (x.SUPRECMK = 'D' and x.PAMTS < 0)
             order by x.APNO,x.PAYYM;
        */
        
        --遺屬年金案暫存檔
        Cursor c_CurSData is
            select APNO
                  ,ISSUYM
                  ,PAYYM
                  ,PAYNO 
                  ,PAMTS
                  ,SUPRECMK
                  ,APLPAYDATE
                  ,EVTIDNNO
                  ,EVTBRDATE
                  ,EVTNAME
                  ,APPDATE  
                  ,UBNO
                  ,EDATE 
                  ,SEX
                  ,EVTYPE
                  ,ADWKMK 
                  ,OTHERAMT 
                  ,EVCODE
                  ,INJNO
                  ,INJCL 
                  ,NACHGMK
              from (select t1.APNO as APNO
                          ,t2.ISSUYM as ISSUYM
                          ,t2.PAYYM as PAYYM
                          ,t2.PAYKIND as PAYNO 
                          ,(select nvl(sum(ta.BEFISSUEAMT),0)
                              from BADAPR ta
                             where ta.APNO = t2.APNO
                               and ta.ISSUYM = t2.ISSUYM
                               and ta.PAYYM = t2.PAYYM 
                               and ta.PAYKIND = t2.PAYKIND
                               and ta.SEQNO <> '0000'
                               and ta.MTESTMK = 'F'
                               and ta.APLPAYMK = '3'
                               and ta.APLPAYDATE = t2.APLPAYDATE
                               and (trim(ta.SUPRECMK) is null or nvl(trim(ta.SUPRECMK),' ') = ' ')) PAMTS
                          ,'' as SUPRECMK
                          ,t2.APLPAYDATE
                          ,t1.EVTIDNNO as EVTIDNNO
                          ,t1.EVTBRDATE as EVTBRDATE
                          ,t1.EVTNAME as EVTNAME
                          ,t1.APPDATE as APPDATE  
                          ,t1.APUBNO as UBNO
                          ,t1.EVTJOBDATE as EDATE 
                          ,t1.EVTSEX as SEX
                          ,t3.EVTYP as EVTYPE
                          ,t2.ADWKMK as ADWKMK 
                          ,t2.OTHERAMT as OTHERAMT 
                          ,t3.EVCODE as EVCODE
                          ,t3.CRIINJDP1 as INJNO
                          ,t3.CRIINJCL1 as INJCL 
                          ,deCode(trim(t2.NACHGMK),'12'
                                                  ,deCode(nvl(trim(t2.ISSUEAMT),0),0,'A','')
                                                  ,'21'
                                                  ,deCode(nvl(trim(t2.ISSUEAMT),0),0,'B','')
                                                  ,t2.NACHGMK) as NACHGMK
                      from BAAPPBASE t1,BADAPR t2,BAAPPEXPAND t3
                     where t1.PAYKIND in ('55','56','59')    
                       and t2.PAYKIND in ('55','56','59')
                       and t1.APNO = t2.APNO
                       and t1.APNO = t3.APNO
                       and t1.SEQNO = t2.SEQNO
                       and t1.SEQNO = t3.SEQNO
                       and t1.SEQNO = '0000'
                       and t2.ISSUYM = v_i_issuym
                       and t2.MTESTMK = 'F'
                       and t2.APLPAYMK = '3'
                       and substr(t2.APLPAYDATE,1,6) = v_i_issuym
                     union
                    select APNO
                          ,ISSUYM
                          ,PAYYM
                          ,PAYNO 
                          ,nvl(PAMTS,0) PAMTS
                          ,deCode(nvl(trim(SUPRECMK),''),'','',deCode(sign(nvl(PAMTS,0)),-1,'D',1,'C','')) as SUPRECMK
                          ,APLPAYDATE
                          ,EVTIDNNO
                          ,EVTBRDATE
                          ,EVTNAME
                          ,APPDATE  
                          ,UBNO
                          ,EDATE 
                          ,SEX
                          ,EVTYPE
                          ,ADWKMK 
                          ,OTHERAMT 
                          ,EVCODE
                          ,INJNO
                          ,INJCL 
                          ,NACHGMK
                     from (select t1.APNO as APNO
                                 ,t2.ISSUYM as ISSUYM
                                 ,t2.PAYYM as PAYYM
                                 ,t2.PAYKIND as PAYNO
                                 ,(nvl(t4.RECAMT,0)+nvl(t4.SUPAMT,0)) PAMTS
                                 ,deCode(sign(nvl((nvl(t4.RECAMT,0)+nvl(t4.SUPAMT,0)),0)),-1,'D',1,'C','') as SUPRECMK
                                 ,t2.APLPAYDATE
                                 ,t1.EVTIDNNO as EVTIDNNO
                                 ,t1.EVTBRDATE as EVTBRDATE
                                 ,t1.EVTNAME as EVTNAME
                                 ,t1.APPDATE as APPDATE  
                                 ,t1.APUBNO as UBNO
                                 ,t1.EVTJOBDATE as EDATE 
                                 ,t1.EVTSEX as SEX
                                 ,t3.EVTYP as EVTYPE
                                 ,t2.ADWKMK as ADWKMK 
                                 ,t2.OTHERAMT as OTHERAMT 
                                 ,t3.EVCODE as EVCODE
                                 ,t3.CRIINJDP1 as INJNO
                                 ,t3.CRIINJCL1 as INJCL 
                                 ,deCode(trim(t2.NACHGMK),'12'
                                                         ,deCode(nvl(trim(t2.ISSUEAMT),0),0,'A','')
                                                         ,'21'
                                                         ,deCode(nvl(trim(t2.ISSUEAMT),0),0,'B','')
                                                         ,t2.NACHGMK) as NACHGMK
                             from BAAPPBASE t1
                                 ,BADAPR t2
                                 ,BAAPPEXPAND t3
                                 ,(select ta.APNO
                                         ,ta.PAYYM
                                         ,ta.PAYKIND
                                         ,nvl(sum(ta.SUPAMT),0) SUPAMT 
                                         ,(-(nvl(sum(ta.RECAMT),0))) RECAMT
                                     from BADAPR ta
                                    where ta.SEQNO <> '0000'
                                      and ta.ISSUYM = v_i_issuym
                                      and ta.MTESTMK = 'F'
                                      and ta.APLPAYMK = '3'
                                      and substr(ta.APLPAYDATE,1,6) = v_i_issuym
                                      and ta.SUPRECMK in ('C','D')
                                    group by ta.APNO,ta.PAYYM,ta.PAYKIND) t4
                            where t1.PAYKIND in ('55','56','59')    
                              and t2.PAYKIND in ('55','56','59')
                              and t4.PAYKIND in ('55','56','59')
                              and t1.APNO = t2.APNO
                              and t1.APNO = t3.APNO
                              and t1.APNO = t4.APNO
                              and t1.SEQNO = t2.SEQNO
                              and t1.SEQNO = t3.SEQNO
                              and t1.PAYKIND = t2.PAYKIND
                              and t1.PAYKIND = t4.PAYKIND
                              and t2.PAYYM = t4.PAYYM
                              and t1.SEQNO = '0000'
                              and t2.ISSUYM = v_i_issuym
                              and t2.MTESTMK = 'F'
                              and t2.APLPAYMK = '3'
                              and substr(t2.APLPAYDATE,1,6) = v_i_issuym
                            union
                           select t1.APNO as APNO
                                 ,t2.ISSUYM as ISSUYM
                                 ,t2.PAYYM as PAYYM
                                 ,t2.PAYKIND as PAYNO
                                 ,(nvl(t2.PREISSUEAMT,0)) PAMTS
                                 ,'' as SUPRECMK
                                 ,t2.APLPAYDATE
                                 ,t1.EVTIDNNO as EVTIDNNO
                                 ,t1.EVTBRDATE as EVTBRDATE
                                 ,t1.EVTNAME as EVTNAME
                                 ,t1.APPDATE as APPDATE  
                                 ,t1.APUBNO as UBNO
                                 ,t1.EVTJOBDATE as EDATE 
                                 ,t1.EVTSEX as SEX
                                 ,t3.EVTYP as EVTYPE
                                 ,t2.ADWKMK as ADWKMK 
                                 ,t2.OTHERAMT as OTHERAMT 
                                 ,t3.EVCODE as EVCODE
                                 ,t3.CRIINJDP1 as INJNO
                                 ,t3.CRIINJCL1 as INJCL 
                                 ,deCode(trim(t2.NACHGMK),'12'
                                                         ,'A'
                                                         ,'21'
                                                         ,'B'
                                                         ,t2.NACHGMK) as NACHGMK
                             from BAAPPBASE t1,BADAPR t2,BAAPPEXPAND t3
                            where t1.PAYKIND in ('55','56','59')    
                              and t2.PAYKIND in ('55','56','59')
                              and t1.APNO = t2.APNO
                              and t1.APNO = t3.APNO
                              and t1.SEQNO = t2.SEQNO
                              and t1.SEQNO = t3.SEQNO
                              and t1.PAYKIND = t2.PAYKIND
                              and t1.SEQNO = '0000'
                              and t2.ISSUYM = v_i_issuym
                              and t2.MTESTMK = 'F'
                              and t2.APLPAYMK = '3'
                              and substr(t2.APLPAYDATE,1,6) = v_i_issuym
                              and t2.SUPRECMK in ('C','D')
                              and (trim(t2.NACHGMK) is not null and nvl(trim(t2.NACHGMK),' ') <> ' ')
                           )
                    ) x
             where ((trim(x.SUPRECMK) is null or nvl(trim(x.SUPRECMK),' ') = ' ') and x.PAMTS > 0)
                or (x.SUPRECMK = 'C' and x.PAMTS > 0)
                or (x.SUPRECMK = 'D' and x.PAMTS < 0)
             order by x.APNO,x.PAYYM; 

    begin
        v_o_flag := 'E';
        SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                        p_job_id => 'PG_BANSF_01.SP_BANSF',
                        p_step   => '產生勞保年金統計檔資料作業(SP_BA_BANSF_GENDATA)：產生勞保年金統計暫存來源檔',
                        p_memo   => '開始時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')');

        SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                             v_i_bajobid,
                             '0',
                             '(DB)SP_BA_BANSF_GENDATA：產生勞保年金統計暫存來源檔(開始)',
                             replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
        
        begin
            for v_CurKData in c_CurKData Loop   
                v_SUPRECMK := ''; 
                v_37_PAMTS := 0;
                
                if v_CurKData.PAYNO = '37' and v_CurKData.SUPRECMK = 'D' and to_Number(nvl(v_CurKData.PAMTS,0)) <0 then
                    begin
                        select nvl(tk.BEFISSUEAMT,0) into v_37_PAMTS
                          from BADAPR tk
                         where tk.APNO = v_CurKData.APNO 
                           and tk.SEQNO = '0000'
                           and tk.PAYKIND = '37'
                           and tk.MTESTMK = 'F'
                           and tk.APLPAYMK = '3'
                           and tk.MANCHKMK = 'Y';
                    exception
                        when others then
                            v_SUPRECMK := v_CurKData.SUPRECMK;    
                    end;
                    
                    if v_37_PAMTS>0 then
                        if to_Number(nvl(v_37_PAMTS,0))-to_Number(nvl(v_CurKData.PAMTS,0)) = 0 then
                            v_SUPRECMK := 'K';
                        else   
                            v_SUPRECMK := 'D';
                        end if;    
                    end if;
                else
                    v_SUPRECMK := v_CurKData.SUPRECMK;
                end if;
                
                insert into BANSF_REF (ISSUYM
                                      ,PAYYM
                                      ,APNO
                                      ,PAYNO
                                      ,UBNO 
                                      ,EVTBRDATE
                                      ,EVTIDNNO
                                      ,EDATE
                                      ,SEX
                                      ,EVTYPE 
                                      ,ADWKMK
                                      ,FIRSTPAY
                                      ,PAMTS
                                      ,OTHERAMT
                                      ,EVCODE
                                      ,INJNO
                                      ,INJCL
                                      ,SUPRECMK 
                                      ,NACHGMK
                                     ) values (
                                       v_CurKData.ISSUYM
                                      ,v_CurKData.PAYYM
                                      ,v_CurKData.APNO
                                      ,v_CurKData.PAYNO
                                      ,v_CurKData.UBNO 
                                      ,v_CurKData.EVTBRDATE
                                      ,v_CurKData.EVTIDNNO
                                      ,v_CurKData.EDATE
                                      ,v_CurKData.SEX
                                      ,v_CurKData.EVTYPE 
                                      ,v_CurKData.ADWKMK
                                      ,FN_BA_GETFIRSTPAYMK(v_CurKData.APNO,v_CurKData.APLPAYDATE)
                                      ,v_CurKData.PAMTS
                                      ,v_CurKData.OTHERAMT
                                      ,v_CurKData.EVCODE
                                      ,v_CurKData.INJNO
                                      ,v_CurKData.INJCL
                                      ,v_SUPRECMK
                                      ,v_CurKData.NACHGMK
                );
            end Loop;  
            
            commit;
            
            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 '0',
                                 '(DB)SP_BA_BANSF_GENDATA：產生勞保年金統計暫存來源檔-失能年金案暫存檔產製完成',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.','')); 
        exception
            when others then
                 rollback;
                 v_o_flag := 'N';
                 SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                      v_i_bajobid,
                                      '1',
                                      '(DB)SP_BA_BANSF_GENDATA：產生勞保年金統計暫存來源檔-失能年金案暫存檔產製失敗***(E21)',
                                      replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
        end; 
        /*
        if v_o_flag = 'E' then
            begin
                for v_CurK36Data in c_CurK36Data Loop
                    insert into BANSF_REF_36 (ISSUYM
                                             ,PAYYM
                                             ,APNO
                                             ,PAYNO 
                                             ,APPDATE 
                                             ,EVTIDNNO
                                             ,EVTBRDATE
                                             ,EVTNAME
                                             ,YEAR_RANG
                                             ,MON_RANG 
                                             ,PWAGE
                                             ,FIRSTPAY
                                             ,PAMTS
                                             ,SUPRECMK
                                             ,SEX
                                            ) values (
                                              v_CurK36Data.ISSUYM
                                             ,v_CurK36Data.PAYYM
                                             ,v_CurK36Data.APNO
                                             ,v_CurK36Data.PAYNO 
                                             ,v_CurK36Data.APPDATE 
                                             ,v_CurK36Data.EVTIDNNO
                                             ,v_CurK36Data.EVTBRDATE
                                             ,v_CurK36Data.EVTNAME
                                             ,v_CurK36Data.YEAR_RANG
                                             ,v_CurK36Data.MON_RANG 
                                             ,v_CurK36Data.PWAGE
                                             ,FN_BA_GETFIRSTPAYMK(v_CurK36Data.APNO,v_CurK36Data.APLPAYDATE)
                                             --,v_CurK36Data.FIRSTPAY
                                             ,v_CurK36Data.PAMTS  
                                             ,v_CurK36Data.SUPRECMK
                                             ,v_CurK36Data.SEX 
                    );
                end Loop;
                
                commit;
                
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '0',
                                     '(DB)SP_BA_BANSF_GENDATA：產生勞保年金統計暫存來源檔-失能年金案(36案)暫存檔產製完成',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.','')); 
            exception
                when others then
                     rollback;
                     v_o_flag := 'N';
                     SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                          v_i_bajobid,
                                          '1',
                                          '(DB)SP_BA_BANSF_GENDATA：產生勞保年金統計暫存來源檔-失能年金案(36案)暫存檔產製失敗***(E22)',
                                          replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            end; 
        end if;
        */
        if v_o_flag = 'E' then   
            begin
                for v_CurSData in c_CurSData Loop
                    insert into BANSF_REF (ISSUYM
                                          ,PAYYM
                                          ,APNO
                                          ,PAYNO
                                          ,UBNO 
                                          ,EVTBRDATE
                                          ,EVTIDNNO
                                          ,EDATE
                                          ,SEX
                                          ,EVTYPE 
                                          ,ADWKMK
                                          ,FIRSTPAY
                                          ,PAMTS
                                          ,OTHERAMT
                                          ,EVCODE
                                          ,INJNO
                                          ,INJCL
                                          ,SUPRECMK 
                                          ,NACHGMK
                                         ) values (
                                           v_CurSData.ISSUYM
                                          ,v_CurSData.PAYYM
                                          ,v_CurSData.APNO
                                          ,v_CurSData.PAYNO
                                          ,v_CurSData.UBNO 
                                          ,v_CurSData.EVTBRDATE
                                          ,v_CurSData.EVTIDNNO
                                          ,v_CurSData.EDATE
                                          ,v_CurSData.SEX
                                          ,v_CurSData.EVTYPE 
                                          ,v_CurSData.ADWKMK
                                          ,FN_BA_GETFIRSTPAYMK(v_CurSData.APNO,v_CurSData.APLPAYDATE)
                                          ,v_CurSData.PAMTS
                                          ,v_CurSData.OTHERAMT
                                          ,v_CurSData.EVCODE
                                          ,v_CurSData.INJNO
                                          ,v_CurSData.INJCL
                                          ,v_CurSData.SUPRECMK 
                                          ,v_CurSData.NACHGMK
                    );
                end Loop; 
                
                commit;
                
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '0',
                                     '(DB)SP_BA_BANSF_GENDATA：產生勞保年金統計暫存來源檔-遺屬年金案暫存檔產製完成',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.','')); 
            exception
                when others then
                     rollback;
                     v_o_flag := 'N';
                     SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                          v_i_bajobid,
                                          '1',
                                          '(DB)SP_BA_BANSF_GENDATA：產生勞保年金統計暫存來源檔-遺屬年金案暫存檔產製失敗***(E23)',
                                          replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            end; 
        end if;
        
        if v_o_flag = 'E' then
            v_o_flag := '0';
        else
            v_o_flag := '1';
        end if;   
        
        SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                        p_job_id => 'PG_BANSF_01.SP_BANSF',
                        p_step   => '產生勞保年金統計檔資料作業(SP_BA_BANSF_GENDATA)：產生勞保年金統計檔之來源檔',
                        p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:('||v_o_flag||')');
                            
        SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                             v_i_bajobid,
                             v_o_flag,
                             '(DB)SP_BA_BANSF_GENDATA：產生勞保年金統計檔之來源檔(結束)',
                             replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
        
        if v_o_flag = '0' then
            v_o_flag := 'E';
        else
            v_o_flag := 'N';
        end if;
    exception
        when others
            then
                SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                                p_job_id => 'PG_BANSF_01.SP_BANSF',
                                p_step   => '產生勞保年金統計檔資料作業(SP_BA_BANSF_GENDATA)：產生勞保年金統計檔之來源檔',
                                p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:(1)');
                                    
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     '1',
                                     '(DB)SP_BA_BANSF_GENDATA：產生勞保年金統計檔之來源檔(結束)',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    
                v_o_flag := 'N';
    end SP_BA_BANSF_GENDATA;
    --Procedure SP_BA_BANSF_GENDATA End
    
    PROCEDURE SP_BA_BANSF_SUPPLEMENT(
        v_i_issuym    in      varChar2,     
        v_i_mmJobNO   in      varChar2, 
        v_i_bajobid   in      varChar2,
		v_o_flag      out     varChar2
    ) is
        --查詢CPI物價指數相關資料
        Cursor c_CurCPIData is
            select t.APNO
                  ,t.SEQNO
                  ,t.ISSUYM
                  ,t.PAYYM 
                  ,nvl(t.ISSUEAMT,0) as CPIAMT 
                  ,nvl(t.CPIRATE,0.0) as CPIRATE
              from BADAPR t
             where t.ISSUYM = v_i_issuym
               and t.PAYKIND in ('34','44','54')
               and t.APLPAYMK = '3'
               and trim(t.APLPAYDATE) is not null
             union
            select t.APNO
                  ,t.SEQNO
                  ,t.ISSUYM
                  ,t.PAYYM 
                  ,nvl(t.CPIAMT,0) as CPIAMT 
                  ,nvl(t.CPIRATE,0.0) as CPIRATE
              from BANSF_CPI t
             where t.ISSUYM = v_i_issuym
               and t.PAYKIND = '34';
               
        begin
            v_o_flag := '0';
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_BA_BANSF_SUPPLEMENT)：勞保年金統計檔補充資料更新',
                            p_memo   => '開始時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')');
                            
            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 '0',
                                 '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新(開始)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));       
                                 
            --結案原因
            begin
                UPDATE BANSF T
                   SET T.CLOSECAUSE = (SELECT T1.CLOSECAUSE 
                                         FROM BAAPPBASE T1
                                        WHERE T1.APNO = T.APNO 
                                          AND T1.SEQNO = '0000')  
                 WHERE T.CLOSECAUSE = 'Z9'                 
                   AND T.APNO IN (SELECT T2.APNO
                                    FROM BAAPPBASE T2
                                   WHERE T2.APNO = T.APNO 
                                     AND T2.SEQNO = '0000'
                                     AND T2.CASETYP = '4'
                                     AND T2.PROCSTAT = '90'
                                     AND TRIM(T2.CLOSECAUSE) IS NOT NULL);       
                
                commit;
                                                     
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     v_o_flag,
                                     '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-結案原因更新完成',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            exception
                when others
                    then
                        rollback;
                        
                        v_o_flag := '1';
                        SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                             v_i_bajobid,
                                             v_o_flag,
                                             '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-結案原因更新發生錯誤***(E24)',
                                             replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            end;
            
            --死亡日期
            begin
                UPDATE BANSF T
                   SET T.EVTDIEDATE = (SELECT T1.EVTDIEDATE 
                                         FROM BAAPPBASE T1
                                        WHERE T1.APNO = T.APNO 
                                          AND T1.SEQNO = '0000')  
                 WHERE TRIM(T.EVTDIEDATE) IS NULL                
                   AND T.APNO IN (SELECT T2.APNO 
                                    FROM BAAPPBASE T2
                                   WHERE T2.APNO = T.APNO 
                                     AND T2.SEQNO = '0000'
                                     AND TRIM(T2.EVTDIEDATE) IS NOT NULL);
                                     
                commit;
                
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     v_o_flag,
                                     '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-死亡日期更新完成',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            exception
                when others
                    then
                        rollback;
                        
                        v_o_flag := '1';
                        SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                             v_i_bajobid,
                                             v_o_flag,
                                             '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-死亡日期更新發生錯誤***(E25)',
                                             replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            end;
            
            --結束請領年月
            begin
                UPDATE BANSF T
                   SET T.EDATE = (SELECT MAX(T1.PAYYM) 
                                    FROM BADAPR T1
                                   WHERE T1.APNO = T.APNO 
                                     AND T1.SEQNO = '0000'
                                     AND T1.MTESTMK = 'F'
                                     AND T1.APLPAYMK = '3'
                                     AND TRIM(T1.APLPAYDATE) IS NOT NULL
                                     AND T1.BEFISSUEAMT > 0)  
                 WHERE T.EDATE = '00000'                
                   AND T.APNO IN (SELECT T1.APNO 
                                    FROM BAAPPBASE T1
                                   WHERE T1.APNO = T.APNO 
                                     AND T1.SEQNO = '0000'
                                     AND T1.CASETYP = '4'
                                     AND T1.PROCSTAT = '90'
                                     AND TRIM(T1.CLOSECAUSE) IS NOT NULL);
                
                commit;
                                     
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     v_o_flag,
                                     '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-結束請領年月更新完成',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            exception
                when others
                    then
                        rollback;
                        
                        v_o_flag := '1';
                        SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                             v_i_bajobid,
                                             v_o_flag,
                                             '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-結束請領年月更新發生錯誤***(E26)',
                                             replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            end;
            
            --36案性別
            begin
                UPDATE BANSF T
                   SET T.SEX = (SELECT T1.EVTSEX 
                                  FROM BAAPPBASE T1
                                 WHERE T1.APNO = T.APNO 
                                   AND T1.SEQNO = '0000')  
                 WHERE T.APNO IN (SELECT T2.APNO 
                                    FROM BAAPPBASE T2
                                   WHERE T2.APNO LIKE 'K%'
                                     AND T2.SEQNO = '0000'
                                     AND T2.PAYKIND = '36')
                   And T.PAYNO = '36';
                
                commit;
                                     
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     v_o_flag,
                                     '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-36案性別更新完成',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            exception
                when others
                    then
                        rollback;
                        
                        v_o_flag := '1';
                        SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                             v_i_bajobid,
                                             v_o_flag,
                                             '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-36案性別發生錯誤***(E27)',
                                             replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            end;
            
            --外籍別
            begin
                UPDATE BANSF T
                   SET T.CIPBFMK = (SELECT C.FMK 
                                      FROM CI.CIPB C
                                     WHERE C.CIID = T.CIID
                                       AND ROWNUM = 1)  
                 WHERE TRIM(T.CIPBFMK) IS NULL;
                
                commit;
                                     
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     v_o_flag,
                                     '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-外籍別更新完成',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            exception
                when others
                    then
                        rollback;
                        
                        v_o_flag := '1';
                        SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                             v_i_bajobid,
                                             v_o_flag,
                                             '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-外籍別發生錯誤***(E28)',
                                             replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            end;
            
	        --大業別
            begin
                UPDATE BANSF T
                   SET T.IDSTA = (SELECT T2.IDSTA 
                                    FROM BANSFIDSTA T2
                                   WHERE T2.APNO = T.APNO
                                     AND T2.PAYKIND = T.PAYNO
                                     AND TRIM(T2.USEMK) IS NULL)  
                      ,T.INDS = (SELECT T3.INDS 
                                   FROM BANSFIDSTA T3
                                  WHERE T3.APNO = T.APNO
                                    AND T3.PAYKIND = T.PAYNO
                                    AND TRIM(T3.USEMK) IS NULL)
                 WHERE T.APNO IN (SELECT T1.APNO
                                    FROM BANSFIDSTA T1
                                   WHERE TRIM(T1.USEMK) IS NULL);
                
                commit;
                                    
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     v_o_flag,
                                     '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-大業別更新完成',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            exception
                when others
                    then 
                        rollback;
                        
                        v_o_flag := '1';
                        SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                             v_i_bajobid,
                                             v_o_flag,
                                             '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-大業別發生錯誤***(E29)',
                                             replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            end;
            
            --CPI物價調整指數金額及指數
            begin
                for v_CurCPIData in c_CurCPIData Loop
                    UPDATE BANSF T
                       SET T.PAMTS = (T.PAMTS+v_CurCPIData.CPIAMT)
                          ,T.CPIAMT = v_CurCPIData.CPIAMT
                          ,T.CPIRATE = v_CurCPIData.CPIRATE
                     WHERE T.APNO = v_CurCPIData.APNO
                       AND T.SEQNO = v_CurCPIData.SEQNO
                       AND T.ISSUYM = v_CurCPIData.ISSUYM
                       AND T.PAYYM = v_CurCPIData.PAYYM
                       AND T.PAYNO NOT IN ('34','37','44','54');
                end Loop;
                
                commit;
                
                SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                     v_i_bajobid,
                                     v_o_flag,
                                     '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-CPI物價調整指數金額及指數更新完成',
                                     replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            exception
                when others
                    then
                        rollback;
                        
                        v_o_flag := '1';
                        SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                             v_i_bajobid,
                                             v_o_flag,
                                             '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新-CPI物價調整指數金額及指數發生錯誤***(E30)',
                                             replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            end;
            
            SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                            p_job_id => 'PG_BANSF_01.SP_BANSF',
                            p_step   => '產生勞保年金統計檔資料作業(SP_BA_BANSF_SUPPLEMENT)：勞保年金統計檔補充資料更新',
                            p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:('||v_o_flag||')');
                            
            SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                 v_i_bajobid,
                                 v_o_flag,
                                 '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新(結束)',
                                 replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
            
            if v_o_flag <> '0' then
                v_o_flag := 'N';
            else
                v_o_flag := 'E';
            end if;  
        exception
            when others
                then
                    rollback;
                    
                    SP_BA_RecJobLog(p_job_no => v_i_mmJobNO,
                                    p_job_id => 'PG_BANSF_01.SP_BANSF',
                                    p_step   => '產生勞保年金統計檔資料作業(SP_BA_BANSF_SUPPLEMENT)：勞保年金統計檔補充資料更新',
                                    p_memo   => '結束時間：'||to_Char(SYSTIMESTAMP,'YYYY/MM/DD HH24:MI:SS')||CHR(10)||'('||v_i_mmJobNO||')'||CHR(10)||'執行結果:(1)');
                                    
                    SP_BA_RecBatchJobDtl(BAJOBDTLID.NEXTVAL,
                                         v_i_bajobid,
                                         '1',
                                         '(DB)SP_BA_BANSF_SUPPLEMENT：勞保年金統計檔補充資料更新(結束)',
                                         replace(to_Char(SYSTIMESTAMP, 'yyyyMMddHH24missxff'),'.',''));
                    
                    v_o_flag := 'N';
        end SP_BA_BANSF_SUPPLEMENT;
    --Procedure SP_BA_BANSF_SUPPLEMENT End
    
    FUNCTION FN_BA_GETFIRSTPAYMK (
        v_i_apno             in      varChar2,
        v_i_aplpaydate       in      varChar2
    ) return varChar2 is
        v_o_rtnfirstpaymk    varChar2(1) := '0';
        v_aplpaydate         varChar2(8) := '';

        begin
            begin
                select min(t.APLPAYDATE) 
                  into v_aplpaydate
                  from BADAPR t
                 where t.APNO = v_i_apno
                   and t.MTESTMK = 'F'
                   and t.APLPAYMK = '3'
                   and (trim(t.APLPAYDATE) is not null and nvl(trim(t.APLPAYDATE),' ') <> ' ');
            exception when others then
                v_aplpaydate := '';
                v_o_rtnfirstpaymk := '0';
            end;                   
            
            if trim(v_aplpaydate) is not null and nvl(trim(v_aplpaydate),' ') <> ' ' then
               if substr(v_i_aplpaydate,1,6) = substr(v_aplpaydate,1,6) then
                    v_o_rtnfirstpaymk := '1';
               end if;    
            end if;
            
            return v_o_rtnfirstpaymk;
        end;
    --function fn_BA_getRECHKDEPT
END;
/