package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.actions.OldAgeReviewRpt01Action;
import tw.gov.bli.ba.rpt.cases.CiptUtilityCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01AnnuityPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01BenDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01BenPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01Case;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01ChkfileDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01ChkfileDescCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DecideDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DeductAnnuityDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DeductOnceDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DieOncePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DiePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DisableAnnuityPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01DisablePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01InjuryPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01IssueAmtDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01JoblessPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01LoanDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01MonthAvgAmtDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01NotifyDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01NpPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01OnceAvgAmtDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01OncePayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01PayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01SurvivorAnnuityPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01UnitCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.ba.util.ValidateUtility;

/**
 * 勞保老年年金給付受理編審清單
 * 
 * @author Goston 
 * 20131119 KIYOMI 增加若 編審註記代碼 (CHKCODE) 值為 P1 或 P2 或 P3，表頭要顯示「＊預警案件」
 */
public class OldAgeReviewRpt01Report extends ReportBase {
    private static Log log = LogFactory.getLog(OldAgeReviewRpt01Action.class);
    private int nPage; // 每筆資料的頁數

    private String printDate = ""; // 印表日期

    private String line = "-----------------------------------------------------------------------------------------------------------------------------------------------";

    public OldAgeReviewRpt01Report() throws Exception {
        super();
        nPage = 0;
        printDate = DateUtility.formatNowChineseDateString(false);
    }

    class OldAgeReviewSameKind {
    	
    	public OldAgeReviewSameKind() {
    		super();
    		
    	}
    	
        /**
         * 
			// 勞保老年給付	
			// 勞保補償金	
			// 勞保老年差額金
			// 勞保老年年金
         * 
         * 
         * @param caseData
         * @param table
         * @param earlyWarning
         * @throws Exception
         */
        public void execute(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 在塞請領同類給付資料表頭前, 先隨便塞空白行測試是否需換頁
            addEmptyRow(table, 1);

            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                deleteRow(table, 1);
                document.add(table);
                table = addHeader(caseData, false, earlyWarning);
            }
            else {
                deleteRow(table, 1);
            }

            addColumn(table, 60, 1, "請領同類給付資料：", fontCh12, 0, LEFT);

			// 勞保老年給付	
            // 一次給付資料 (有資料再印)
            this.printOncePays(caseData, table, earlyWarning);
            
			// 勞保補償金	
			
            // 勞保老年差額金

            // 勞保老年年金
            // 年金給付資料 (有資料再印)
            this.printAnnuitys(caseData, table, earlyWarning);
            
            
            // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
            addEmptyRow(table, 1);

            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // 換了頁就不印分隔線了
                deleteRow(table, 1);
                document.add(table);
                table = addHeader(caseData, false, earlyWarning);
            }
            else {
                deleteRow(table, 1);
                addLine(table);
            }
    		
    	}

    	/**
    	 * 一次給付資料
    	 * 
    	 * @param caseData
    	 * @param table
    	 * @param earlyWarning
    	 * @throws Exception
    	 */
    	public void printOncePays(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 一次給付資料 (有資料再印)
    		if (caseData.getOncePayList() != null) {
    			List<OldAgeReviewRpt01OncePayDataCase> oncePayList = caseData.getOncePayList();
    			for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++) { // ... [
    				OldAgeReviewRpt01OncePayDataCase oncePayData = oncePayList.get(nOncePayCount);
    				
    				// 印一次給付表頭
    				if (nOncePayCount == 0) {
    					addEmptyRow(table, 1);
    					
    					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
    						deleteRow(table, 1);
    						document.add(table);
    						table = addHeader(caseData, false, earlyWarning);
    					}
    					else {
    						deleteRow(table, 1);
    					}
    					
    					// 一次給付 表頭
    					addColumn(table, 60, 1, "[一次給付]", fontCh12b, 0, LEFT);
    				}
    				
    				// 一次給付資料一筆有六行 (如受理編號第 5 和 6 碼為 46 則會 4 行), 在塞資料前先測試是否需換頁
    				if (StringUtils.equals("46", StringUtils.substring(oncePayData.getBmApNo(), 4, 6)))
    					addEmptyRow(table, 6);
    				else
    					addEmptyRow(table, 4);
    				
    				if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
    					if (StringUtils.equals("46", StringUtils.substring(oncePayData.getBmApNo(), 4, 6)))
    						deleteRow(table, 6);
    					else
    						deleteRow(table, 4);
    					document.add(table);
    					table = addHeader(caseData, false, earlyWarning);
    				}
    				else {
    					if (StringUtils.equals("46", StringUtils.substring(oncePayData.getBmApNo(), 4, 6)))
    						deleteRow(table, 6);
    					else
    						deleteRow(table, 4);
    				}
    				
    				addColumn(table, 7, 1, "事故者姓名", fontCh12b, 0, LEFT);
    				addColumn(table, 7, 1, "受理日期", fontCh12, 0, LEFT);
    				addColumn(table, 12, 1, "受理編號", fontCh12, 0, LEFT);
    				addColumn(table, 7, 1, "事故日期", fontCh12, 0, LEFT);
    				addColumn(table, 7, 1, "保險證號", fontCh12, 0, LEFT);
    				addColumn(table, 6, 1, "核定金額", fontCh12, 0, LEFT);
    				addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
    				addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
    				// ---
    				addColumn(table, 7, 1, oncePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
    				addColumn(table, 7, 1, oncePayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
    				addColumn(table, 12, 1, oncePayData.getBmApNoString(), fontCh12, 0, LEFT); // 受理編號
    				addColumn(table, 7, 1, oncePayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期
    				addColumn(table, 7, 1, oncePayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
    				
    				if (oncePayData.getBmChkAmt() != null && oncePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
    					addColumn(table, 6, 1, formatBigDecimalToInteger(oncePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
    				else
    					addColumn(table, 6, 1, "0", fontCh12, 0, LEFT); // 核定金額
    				
    				addColumn(table, 7, 1, oncePayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
    				addColumn(table, 7, 1, oncePayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
    				// ---
    				addColumn(table, 14, 1, "補件日期/註記", fontCh12, 0, LEFT);
    				addColumn(table, 10, 1, "不給付日期", fontCh12, 0, LEFT);
    				addColumn(table, 10, 1, "補收金額", fontCh12, 0, LEFT);
    				addColumn(table, 26, 1, "　", fontCh12, 0, LEFT);
    				// ---
    				addColumn(table, 14, 1, oncePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(oncePayData.getBmNdocMk())) ? " / " + oncePayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
    				addColumn(table, 10, 1, oncePayData.getBmNopDateString(), fontCh12, 0, LEFT); // 不給付日期
    				
    				if (oncePayData.getBmAdjAmts() != null && oncePayData.getBmAdjAmts().compareTo(new BigDecimal(0)) != 0)
    					addColumn(table, 10, 1, formatBigDecimalToInteger(oncePayData.getBmAdjAmts()), fontCh12, 0, LEFT); // 補收金額
    				else
    					addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 補收金額
    				
    				addColumn(table, 26, 1, "　", fontCh12, 0, LEFT);
    				// ---
    				if (StringUtils.equals("46", StringUtils.substring(oncePayData.getBmApNo(), 4, 6))) {
    					addColumn(table, 11, 1, "事業主管機關", fontCh12, 0, LEFT);
    					addColumn(table, 11, 1, "申請代算單位", fontCh12, 0, LEFT);
    					addColumn(table, 8, 1, "法令依據", fontCh12, 0, LEFT);
    					addColumn(table, 8, 1, "複核日期", fontCh12, 0, LEFT);
    					addColumn(table, 22, 1, "　", fontCh12, 0, LEFT);
    					// ---
    					addColumn(table, 11, 1, oncePayData.getBmOldOrgDpt(), fontCh12, 0, LEFT); // 事業主管機關
    					addColumn(table, 11, 1, oncePayData.getBmOldAplDpt(), fontCh12, 0, LEFT); // 申請代算單位
    					addColumn(table, 8, 1, oncePayData.getBmOldLawNo(), fontCh12, 0, LEFT); // 法令依據
    					addColumn(table, 8, 1, oncePayData.getBmExmDteString(), fontCh12, 0, LEFT); // 複核日期
    					addColumn(table, 22, 1, "　", fontCh12, 0, LEFT);
    				}
    				
    				// 最後一筆印完後空一行 (如果年金給付資料有資料再印)
    				if ((nOncePayCount == oncePayList.size() - 1) && (caseData.getAnnuityPayList() != null && caseData.getAnnuityPayList().size() > 0)) {
    					// 空白行
    					addEmptyRow(table, 1);
    					
    					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
    						// 換了頁就不再塞空白行了
    						deleteRow(table, 1);
    						document.add(table);
    						table = addHeader(caseData, false, earlyWarning);
    					}
    				}
    			} // ] ... end for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++)
    			
    		}
    		
    	}

		/**
		 * 年金給付資料
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public void printAnnuitys(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 年金給付資料 (有資料再印)
			if (caseData.getAnnuityPayList() != null) {
				List<OldAgeReviewRpt01AnnuityPayDataCase> annuityPayList = caseData.getAnnuityPayList();
				for (int nAnnuityPayCount = 0; nAnnuityPayCount < annuityPayList.size(); nAnnuityPayCount++) { // ... [
					OldAgeReviewRpt01AnnuityPayDataCase annuityPayData = annuityPayList.get(nAnnuityPayCount);
					
					// 印年金給付表頭
					if (nAnnuityPayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 年金給付 表頭
						addColumn(table, 60, 1, "[年金給付]", fontCh12b, 0, LEFT);
					}
					
					// 年金給付資料一筆有四行, 在塞資料前先測試是否需換頁
					addEmptyRow(table, 4);
					
					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
						deleteRow(table, 4);
						document.add(table);
						table = addHeader(caseData, false, earlyWarning);
					}
					else {
						deleteRow(table, 4);
					}
					
					addColumn(table, 8, 1, "事故者姓名", fontCh12b, 0, LEFT);
					addColumn(table, 7, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 24, 1, "受理編號/核定年月/給付年月", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "事故日期", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "保險證號", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 8, 1, annuityPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
					addColumn(table, 7, 1, annuityPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 24, 1, annuityPayData.getApNoString() + " / " + StringUtils.defaultString(annuityPayData.getIssuYmString()) + " / " + StringUtils.defaultString(annuityPayData.getPayYmString()), fontCh12, 0, LEFT); // 受理編號/核定年月/給付年月
					addColumn(table, 7, 1, annuityPayData.getEvtJobDateString(), fontCh12, 0, LEFT); // 事故日期
					addColumn(table, 7, 1, annuityPayData.getLsUbno(), fontCh12, 0, LEFT); // 保險證號
					
					if (annuityPayData.getIssueAmt() != null && annuityPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 7, 1, formatBigDecimalToInteger(annuityPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 7, 1, "0", fontCh12, 0, LEFT); // 核定金額
					// ---20130408 更改核定日期、核付日期、 補件日期/註記、 不給付日期 為 案件類別、處理狀態
					addColumn(table, 8, 1, "案件類別", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "處理狀態", fontCh12, 0, LEFT);
					// addColumn(table, 8, 1, "核定日期", fontCh12, 0, LEFT);
					// addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
					// addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
					// addColumn(table, 8, 1, "不給付日期", fontCh12, 0, LEFT);
					addColumn(table, 18, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
					addColumn(table, 25, 1, "補收金額", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 8, 1, annuityPayData.getCaseTypString(), fontCh12, 0, LEFT); // 案件類別
					addColumn(table, 9, 1, annuityPayData.getProcStatString(), fontCh12, 0, LEFT); // 處理狀態
					// addColumn(table, 8, 1, annuityPayData.getChkDateString(), fontCh12, 0, LEFT); // 核定日期
					// addColumn(table, 7, 1, annuityPayData.getAplpayDateString(), fontCh12, 0, LEFT); // 核付日期
					// addColumn(table, 10, 1, annuityPayData.getProdateString() + ((StringUtils.isNotBlank(annuityPayData.getNdomk1())) ? ((StringUtils.isNotBlank(annuityPayData.getProdate())) ? (" / " + annuityPayData.getNdomk1()) : annuityPayData.getNdomk1()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
					// addColumn(table, 8, 1, annuityPayData.getExeDateString(), fontCh12, 0, LEFT); // 不給付日期
					
					addColumn(table, 18, 1, annuityPayData.getCloseDateString() + ((StringUtils.isNotBlank(annuityPayData.getCloseCause())) ? ((StringUtils.isNotBlank(annuityPayData.getCloseDate())) ? (" / " + annuityPayData.getCloseCause()) : "        / " + annuityPayData.getCloseCause()) : ""), fontCh12, 0, LEFT); // 結案日期/結案原因
					
					BigDecimal tempAmt = (annuityPayData.getRecAmt() != null) ? annuityPayData.getRecAmt() : annuityPayData.getSupAmt();
					if (tempAmt != null && tempAmt.compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 25, 1, formatBigDecimalToInteger(tempAmt), fontCh12, 0, LEFT); // 補收金額
					else
						addColumn(table, 25, 1, " ", fontCh12, 0, LEFT); // 補收金額
				} // ] ... end for (int nAnnuityPayCount = 0; nAnnuityPayCount < annuityPayList.size(); nAnnuityPayCount++)
				
			}

		}
    	
    }
    
    class OldAgeReviewOtherKind {
    	
    	public OldAgeReviewOtherKind() {
    		super();
    		
    	}

        /**
         * 
			// 勞保傷病給付
			// 勞保失能給付
			// 勞保失能年金
			// 勞保本人死亡給付	
			// 災保本人死亡給付
			// 勞保家屬死亡給付	
			// 勞保失蹤津貼給付
			// 農保喪葬津貼
			// 勞保遺屬年金	
			// 災保遺屬年金
			// 就保失業給付
			// 國保老年年金	
			// 國保身障年金	
			// 國保喪葬給付	
			// 國保遺屬年金
         * 
         * @param caseData
         * @param table
         * @param earlyWarning
         * @throws Exception
         */
        public void execute(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 在塞請領他類給付資料表頭前, 先隨便塞空白行測試是否需換頁
            addEmptyRow(table, 1);

            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                deleteRow(table, 1);
                document.add(table);
                table = addHeader(caseData, false, earlyWarning);
            }
            else {
                deleteRow(table, 1);
            }

            addColumn(table, 60, 1, "請領他類給付資料：", fontCh12, 0, LEFT);

			// 勞保傷病給付
            // 申請傷病給付記錄資料 (有資料再印)
            this.printInjuryPays(caseData, table, earlyWarning);
            
			// 勞保失能給付
            // 申請失能給付記錄資料 (有資料再印)
            this.printDisablePays(caseData, table, earlyWarning);
            
			// 勞保失能年金
            // 申請失能年金記錄資料 (有資料再印)
            this.printDisableAnnuitys(caseData, table, earlyWarning);
            
			// 勞保本人死亡給付
            // 申請死亡給付記錄資料 (有資料再印)
            this.printDiePays(caseData, table, earlyWarning);
            
			// 災保本人死亡給付
            // 申請死亡給付記錄資料 (有資料再印) 災保 20220421
            this.printDisasterReviewDiePays(caseData, table, earlyWarning);
            
			// 勞保家屬死亡給付
            // 申請遺屬年金記錄資料 (有資料再印)
            this.printSurvivorAnnuitys(caseData, table, earlyWarning);
            
			// 勞保失蹤津貼給付
            
			// 農保喪葬津貼
            
			// 勞保遺屬年金
            
			// 災保遺屬年金
            // 申請遺屬年金記錄資料 (有資料再印) 災保 20220421
            this.printDisasterReviewSurvivorAnnuitys(caseData, table, earlyWarning);
            
			// 就保失業給付
            // 申請失業給付記錄資料 (有資料再印)
            this.printJoblessPays(caseData, table, earlyWarning);
            
			// 國保老年年金
            
			// 國保身障年金
            
			// 國保喪葬給付
            
			// 國保遺屬年金
            

            /** 20220421 excel 無此項目
            // 申請國保給付記錄資料 (有資料再印)
            this.printNpPayList(caseData, table, earlyWarning);
             * 
             */

            // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
            addEmptyRow(table, 1);

            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // 換了頁就不印分隔線了
                deleteRow(table, 1);
                document.add(table);
                table = addHeader(caseData, false, earlyWarning);
            }
            else {
                deleteRow(table, 1);
                addLine(table);
            }
        }
        
		/**
		 * 申請國保給付記錄資料
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public void printNpPays(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 申請國保給付記錄資料 (有資料再印)
			if (caseData.getNpPayList() != null) {
				List<OldAgeReviewRpt01NpPayDataCase> npPayList = caseData.getNpPayList();
				for (int nNpPayCount = 0; nNpPayCount < npPayList.size(); nNpPayCount++) { // ... [
					OldAgeReviewRpt01NpPayDataCase npPayData = npPayList.get(nNpPayCount);
					
					// 印國保給付記錄表頭
					if (nNpPayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 國保給付記錄 表頭
						addColumn(table, 60, 1, "申請國保給付記錄：", fontCh12b, 0, LEFT);
						
					}
					
					// 國保給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					addEmptyRow(table, 4);
					
					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
						deleteRow(table, 4);
						document.add(table);
						table = addHeader(caseData, false, earlyWarning);
					}
					else {
						deleteRow(table, 4);
					}
					
					addColumn(table, 7, 1, "申請日期", fontCh12b, 0, LEFT);
					addColumn(table, 15, 1, "受理編號/給付年月", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "被保險人姓名", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "事故日期", fontCh12, 0, LEFT);
					addColumn(table, 6, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 7, 1, npPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 15, 1, npPayData.getApNo() + ((StringUtils.isNotBlank(npPayData.getPayYm())) ? ((StringUtils.isNotBlank(npPayData.getApNo())) ? (" / " + npPayData.getPayYmString()) : npPayData.getPayYmString()) : ""), fontCh12, 0, LEFT); // 受理編號/給付年月
					addColumn(table, 11, 1, npPayData.getEvteeName(), fontCh12, 0, LEFT); // 被保險人姓名
					addColumn(table, 7, 1, npPayData.getEvtDtString(), fontCh12, 0, LEFT); // 事故日期
					
					if (npPayData.getIssueAmt() != null && npPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 6, 1, formatBigDecimalToInteger(npPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 7, 1, npPayData.getChkDtString(), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 7, 1, npPayData.getAplPayDateString(), fontCh12, 0, LEFT); // 核付日期
					// ---
					addColumn(table, 7, 1, "合格註記", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "人工審核結果", fontCh12, 0, LEFT);
					addColumn(table, 43, 1, "　", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 7, 1, npPayData.getAcceptMark(), fontCh12, 0, LEFT); // 合格註記
					addColumn(table, 10, 1, npPayData.getManChkFlg(), fontCh12, 0, LEFT); // 人工審核結果
					addColumn(table, 43, 1, "　", fontCh12, 0, LEFT);
					
				} // ] ... end for (int nNpPayCount = 0; nNpPayCount < npPayList.size(); nNpPayCount++)
				
			}

		}

		/**
		 * 申請失業給付記錄資料
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public void printJoblessPays(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 申請失業給付記錄資料 (有資料再印)
			if (caseData.getJoblessPayList() != null) {
				List<OldAgeReviewRpt01JoblessPayDataCase> joblessPayList = caseData.getJoblessPayList();
				for (int nJoblessPayCount = 0; nJoblessPayCount < joblessPayList.size(); nJoblessPayCount++) { // ... [
					OldAgeReviewRpt01JoblessPayDataCase joblessPayData = joblessPayList.get(nJoblessPayCount);
					
					// 印失業給付記錄表頭
					if (nJoblessPayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 失業給付記錄 表頭
						addColumn(table, 60, 1, "申請失業給付記錄：", fontCh12b, 0, LEFT);
					}
					
					// 失業給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					addEmptyRow(table, 4);
					
					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
						deleteRow(table, 4);
						document.add(table);
						table = addHeader(caseData, false, earlyWarning);
					}
					else {
						deleteRow(table, 4);
					}
					
					addColumn(table, 7, 1, "受理日期", fontCh12b, 0, LEFT);
					addColumn(table, 12, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "求職日期", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "給付起日-", fontCh12, 0, RIGHT);
					addColumn(table, 6, 1, "給付迄日", fontCh12, 0, LEFT);
					addColumn(table, 6, 1, "核定金額", fontCh12, 0, RIGHT);
					addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);
					addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 7, 1, joblessPayData.getApDteString(), fontCh12b, 0, LEFT); // 受理日期
					addColumn(table, 12, 1, joblessPayData.getApNoString(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 7, 1, joblessPayData.getName(), fontCh12, 0, LEFT); // 事故者姓名
					addColumn(table, 7, 1, joblessPayData.getAprDteString(), fontCh12, 0, LEFT); // 求職日期
					addColumn(table, 7, 1, joblessPayData.getSymDteString() + ((StringUtils.isNotBlank(joblessPayData.getTymDte())) ? "-" : ""), fontCh12, 0, RIGHT); // 給付起日-
					addColumn(table, 6, 1, joblessPayData.getTymDteString(), fontCh12, 0, LEFT); // 給付迄日
					
					if (joblessPayData.getChkAmt() != null && joblessPayData.getChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 6, 1, formatBigDecimalToInteger(joblessPayData.getChkAmt()), fontCh12, 0, RIGHT); // 核定金額
					else
						addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // 核定金額
					addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);
					addColumn(table, 7, 1, joblessPayData.getChkDteString(), fontCh12, 0, LEFT); // 核定日期
					// ---
					addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "補件日期", fontCh12, 0, LEFT);
					addColumn(table, 8, 1, "不給付日期", fontCh12, 0, LEFT);
					addColumn(table, 38, 1, "　", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 7, 1, joblessPayData.getPayDteString(), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 7, 1, joblessPayData.getSavDt1String(), fontCh12, 0, LEFT); // 補件日期
					addColumn(table, 8, 1, joblessPayData.getNpyDteString(), fontCh12, 0, LEFT); // 不給付日期
					addColumn(table, 38, 1, "　", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nJoblessPayCount == joblessPayList.size() - 1) && (caseData.getNpPayList() != null && caseData.getNpPayList().size() > 0)) {
						// 空白行
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
				} // ] ... end for (int nJoblessPayCount = 0; nJoblessPayCount < joblessPayList.size(); nJoblessPayCount++)
				
			}
			
		}

		/**
		 * 申請傷病給付記錄資料
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public void printInjuryPays(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 申請傷病給付記錄資料 (有資料再印)
			if (caseData.getInjuryPayList() != null) {
				List<OldAgeReviewRpt01InjuryPayDataCase> injuryPayList = caseData.getInjuryPayList();
				for (int nInjuryPayCount = 0; nInjuryPayCount < injuryPayList.size(); nInjuryPayCount++) { // ... [
					OldAgeReviewRpt01InjuryPayDataCase injuryPayData = injuryPayList.get(nInjuryPayCount);
					
					// 印傷病給付記錄表頭
					if (nInjuryPayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 傷病給付記錄 表頭
						addColumn(table, 60, 1, "申請傷病給付記錄：", fontCh12b, 0, LEFT);
					}
					
					// 傷病給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					addEmptyRow(table, 4);
					
					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
						deleteRow(table, 4);
						document.add(table);
						table = addHeader(caseData, false, earlyWarning);
					}
					else {
						deleteRow(table, 4);
					}
					
					addColumn(table, 7, 1, "受理日期", fontCh12b, 0, LEFT);
					addColumn(table, 12, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "事故日期", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核定起日-", fontCh12, 0, RIGHT);
					addColumn(table, 7, 1, "迄日", fontCh12, 0, CENTER);
					addColumn(table, 6, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 7, 1, injuryPayData.getBmApDteString(), fontCh12b, 0, LEFT); // 受理日期
					addColumn(table, 12, 1, injuryPayData.getBmApNoString(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 7, 1, injuryPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
					addColumn(table, 7, 1, injuryPayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期
					addColumn(table, 7, 1, injuryPayData.getBmLosFmDteString() + ((StringUtils.isNotBlank(injuryPayData.getBmLosToDte())) ? "-" : ""), fontCh12, 0, RIGHT); // 核定起日-
					addColumn(table, 7, 1, injuryPayData.getBmLosToDteString(), fontCh12, 0, CENTER); // 迄日
					
					if (injuryPayData.getBmChkAmt() != null && injuryPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 6, 1, formatBigDecimalToInteger(injuryPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 7, 1, injuryPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
					// ---
					addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 8, 1, "不給付日期", fontCh12, 0, LEFT);
					addColumn(table, 35, 1, "　", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 7, 1, injuryPayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 10, 1, injuryPayData.getBmNrepDateString() + ((StringUtils.isNotBlank(injuryPayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNrepDateString())) ? (" / " + injuryPayData.getBmNdocMk()) : injuryPayData.getBmNdocMk()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
					addColumn(table, 8, 1, injuryPayData.getBmNopDateString(), fontCh12, 0, LEFT); // 不給付日期
					addColumn(table, 35, 1, "　", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nInjuryPayCount == injuryPayList.size() - 1) && ((caseData.getJoblessPayList() != null && caseData.getJoblessPayList().size() > 0) || (caseData.getNpPayList() != null && caseData.getNpPayList().size() > 0))) {
						// 空白行
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
				} // ] ... end for (int nInjuryPayCount = 0; nInjuryPayCount < injuryPayList.size(); nInjuryPayCount++)
				
			}

		}

		/**
		 * 申請遺屬年金記錄資料 災保
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public void printDisasterReviewSurvivorAnnuitys(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 申請遺屬年金記錄資料 (有資料再印) 災保
			if (caseData.getDisasterSurvivorAnnuityPayList() != null) {
				List<OldAgeReviewRpt01SurvivorAnnuityPayDataCase> survivorAnnuityPayBy9List = caseData.getDisasterSurvivorAnnuityPayList();
				for (int nSurvivorAnnuityPayCount = 0; nSurvivorAnnuityPayCount < survivorAnnuityPayBy9List.size(); nSurvivorAnnuityPayCount++) { // ... [
					OldAgeReviewRpt01SurvivorAnnuityPayDataCase survivorAnnuityPayData = survivorAnnuityPayBy9List.get(nSurvivorAnnuityPayCount);
					
					// 印申請遺屬年金記錄表頭
					if (nSurvivorAnnuityPayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 申請遺屬年金記錄 表頭
						addColumn(table, 60, 1, "災保-申請遺屬年金記錄：", fontCh12b, 0, LEFT);
					}
					
					// 遺屬年金記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					addEmptyRow(table, 4);
					
					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
						deleteRow(table, 4);
						document.add(table);
						table = addHeader(caseData, false, earlyWarning);
					}
					else {
						deleteRow(table, 4);
					}
					
					addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 22, 1, "受理編號/核定年月/給付年月", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "死亡日期", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "案件類別", fontCh12, 0, LEFT);
					
					// ---
					addColumn(table, 8, 1, survivorAnnuityPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
					addColumn(table, 7, 1, DateUtility.formatChineseDateString(survivorAnnuityPayData.getAppDate(), false), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 22, 1, survivorAnnuityPayData.getApNoString() + " / " + StringUtils.defaultString(survivorAnnuityPayData.getIssuYmString()) + " / " + StringUtils.defaultString(survivorAnnuityPayData.getPayYmString()), fontCh12, 0, LEFT); // 受理編號/核定年月/給付年月
					addColumn(table, 7, 1, DateUtility.formatChineseDateString(survivorAnnuityPayData.getEvtJobDate(), false), fontCh12, 0, LEFT); // 死亡日期
					
					if (survivorAnnuityPayData.getIssueAmt() != null && survivorAnnuityPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 7, 1, formatBigDecimalToInteger(survivorAnnuityPayData.getIssueAmt()), fontCh12, 0, CENTER); // 核定金額
					else
						addColumn(table, 7, 1, " ", fontCh12, 0, CENTER); // 核定金額
					
					addColumn(table, 9, 1, survivorAnnuityPayData.getCaseTypString(), fontCh12, 0, LEFT); // 案件類別
					// ---
					addColumn(table, 8, 1, "處理狀態", fontCh12, 0, LEFT);
					addColumn(table, 20, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
					addColumn(table, 32, 1, "　", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 8, 1, survivorAnnuityPayData.getProcStatString(), fontCh12, 0, LEFT); // 處理狀態
					if (StringUtils.isNotBlank(survivorAnnuityPayData.getCloseDate()) || StringUtils.isNotBlank(survivorAnnuityPayData.getCloseCauseStr())) {
						addColumn(table, 20, 1, DateUtility.formatChineseDateString(survivorAnnuityPayData.getCloseDate(), false) + "/" + survivorAnnuityPayData.getCloseCauseStr(), fontCh12, 0, LEFT); // 結案日期/結案原因
					}
					else {
						addColumn(table, 20, 1, "　", fontCh12, 0, LEFT); // 結案日期/結案原因
					}
					addColumn(table, 32, 1, "　", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nSurvivorAnnuityPayCount == survivorAnnuityPayBy9List.size() - 1) && ((caseData.getInjuryPayList() != null && caseData.getInjuryPayList().size() > 0) || (caseData.getJoblessPayList() != null && caseData.getJoblessPayList().size() > 0) || (caseData.getNpPayList() != null && caseData.getNpPayList().size() > 0))) {
						// 空白行
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
				} // ] ... end for (int nSurvivorAnnuityPayCount = 0; nSurvivorAnnuityPayCount < survivorAnnuityPayList.size(); nSurvivorAnnuityPayCount++)
				
			}
            
		}

		public void printSurvivorAnnuitys(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 申請遺屬年金記錄資料 (有資料再印)
			if (caseData.getSurvivorAnnuityPayList() != null) {
				List<OldAgeReviewRpt01SurvivorAnnuityPayDataCase> survivorAnnuityPayList = caseData.getSurvivorAnnuityPayList();
				for (int nSurvivorAnnuityPayCount = 0; nSurvivorAnnuityPayCount < survivorAnnuityPayList.size(); nSurvivorAnnuityPayCount++) { // ... [
					OldAgeReviewRpt01SurvivorAnnuityPayDataCase survivorAnnuityPayData = survivorAnnuityPayList.get(nSurvivorAnnuityPayCount);
					
					// 印申請遺屬年金記錄表頭
					if (nSurvivorAnnuityPayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 申請遺屬年金記錄 表頭
						addColumn(table, 60, 1, "申請遺屬年金記錄：", fontCh12b, 0, LEFT);
					}
					
					// 遺屬年金記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					addEmptyRow(table, 4);
					
					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
						deleteRow(table, 4);
						document.add(table);
						table = addHeader(caseData, false, earlyWarning);
					}
					else {
						deleteRow(table, 4);
					}
					
					addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 22, 1, "受理編號/核定年月/給付年月", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "死亡日期", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "案件類別", fontCh12, 0, LEFT);
					
					// ---
					addColumn(table, 8, 1, survivorAnnuityPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
					addColumn(table, 7, 1, DateUtility.formatChineseDateString(survivorAnnuityPayData.getAppDate(), false), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 22, 1, survivorAnnuityPayData.getApNoString() + " / " + StringUtils.defaultString(survivorAnnuityPayData.getIssuYmString()) + " / " + StringUtils.defaultString(survivorAnnuityPayData.getPayYmString()), fontCh12, 0, LEFT); // 受理編號/核定年月/給付年月
					addColumn(table, 7, 1, DateUtility.formatChineseDateString(survivorAnnuityPayData.getEvtJobDate(), false), fontCh12, 0, LEFT); // 死亡日期
					
					if (survivorAnnuityPayData.getIssueAmt() != null && survivorAnnuityPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 7, 1, formatBigDecimalToInteger(survivorAnnuityPayData.getIssueAmt()), fontCh12, 0, CENTER); // 核定金額
					else
						addColumn(table, 7, 1, " ", fontCh12, 0, CENTER); // 核定金額
					
					addColumn(table, 9, 1, survivorAnnuityPayData.getCaseTypString(), fontCh12, 0, LEFT); // 案件類別
					// ---
					addColumn(table, 8, 1, "處理狀態", fontCh12, 0, LEFT);
					addColumn(table, 20, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
					addColumn(table, 32, 1, "　", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 8, 1, survivorAnnuityPayData.getProcStatString(), fontCh12, 0, LEFT); // 處理狀態
					if (StringUtils.isNotBlank(survivorAnnuityPayData.getCloseDate()) || StringUtils.isNotBlank(survivorAnnuityPayData.getCloseCauseStr())) {
						addColumn(table, 20, 1, DateUtility.formatChineseDateString(survivorAnnuityPayData.getCloseDate(), false) + "/" + survivorAnnuityPayData.getCloseCauseStr(), fontCh12, 0, LEFT); // 結案日期/結案原因
					}
					else {
						addColumn(table, 20, 1, "　", fontCh12, 0, LEFT); // 結案日期/結案原因
					}
					addColumn(table, 32, 1, "　", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nSurvivorAnnuityPayCount == survivorAnnuityPayList.size() - 1) && ((caseData.getInjuryPayList() != null && caseData.getInjuryPayList().size() > 0) || (caseData.getJoblessPayList() != null && caseData.getJoblessPayList().size() > 0) || (caseData.getNpPayList() != null && caseData.getNpPayList().size() > 0))) {
						// 空白行
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
				} // ] ... end for (int nSurvivorAnnuityPayCount = 0; nSurvivorAnnuityPayCount < survivorAnnuityPayList.size(); nSurvivorAnnuityPayCount++)
				
			}
			
		}

		/**
		 * 申請死亡給付記錄資料 災保
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public void printDisasterReviewDiePays(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 申請死亡給付記錄資料 (有資料再印) 災保
			if (caseData.getDisasterInsuranceDiePayList() != null) {
				List<OldAgeReviewRpt01DiePayDataCase> diePayBy9List = caseData.getDisasterInsuranceDiePayList();
				for (int nDiePayCount = 0; nDiePayCount < diePayBy9List.size(); nDiePayCount++) { // ... [
					OldAgeReviewRpt01DiePayDataCase diePayData = diePayBy9List.get(nDiePayCount);
					
					// 印死亡給付記錄表頭
					if (nDiePayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 死亡給付記錄 表頭
						addColumn(table, 60, 1, "災保-申請死亡給付記錄：", fontCh12b, 0, LEFT);
					}
					
					// 死亡給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					addEmptyRow(table, 4);
					
					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
						deleteRow(table, 4);
						document.add(table);
						table = addHeader(caseData, false, earlyWarning);
					}
					else {
						deleteRow(table, 4);
					}
					
					addColumn(table, 7, 1, "申請日期", fontCh12b, 0, LEFT);
					addColumn(table, 18, 1, "受理編號/給付年月", fontCh12, 0, LEFT);
					addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "死亡日期", fontCh12, 0, LEFT);
					addColumn(table, 6, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 7, 1, diePayData.getBmApDteString(), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 18, 1, diePayData.getBmApNoString() + ((StringUtils.isNotBlank(diePayData.getBmPayYm())) ? ((StringUtils.isNotBlank(diePayData.getBmApNo())) ? (" / " + diePayData.getBmPayYmString()) : diePayData.getBmPayYmString()) : ""), fontCh12, 0, LEFT); // 受理編號/給付年月
					addColumn(table, 8, 1, diePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
					addColumn(table, 7, 1, diePayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 死亡日期
					
					if (diePayData.getBmChkAmt() != null && diePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 6, 1, formatBigDecimalToInteger(diePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 7, 1, diePayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 7, 1, diePayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
					// ---
					addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 8, 1, "不給付日期", fontCh12, 0, LEFT);
					addColumn(table, 42, 1, "　", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 10, 1, diePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(diePayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(diePayData.getBmNrepDateString())) ? (" / " + diePayData.getBmNdocMk()) : diePayData.getBmNdocMk()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
					addColumn(table, 8, 1, diePayData.getBmNopDateString(), fontCh12, 0, LEFT); // 不給付日期
					addColumn(table, 42, 1, "　", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nDiePayCount == diePayBy9List.size() - 1) 
							&& ((caseData.getSurvivorAnnuityPayList() != null && caseData.getSurvivorAnnuityPayList().size() > 0) 
									|| (caseData.getInjuryPayList() != null && caseData.getInjuryPayList().size() > 0) 
									|| (caseData.getJoblessPayList() != null && caseData.getJoblessPayList().size() > 0) 
									|| (caseData.getNpPayList() != null && caseData.getNpPayList().size() > 0))) {
						// 空白行
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
				} // ] ... end for (int nDiePayCount = 0; nDiePayCount < diePayList.size(); nDiePayCount++)
				
			}

		}

		/**
		 * 申請死亡給付記錄資料
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public void printDiePays(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 申請死亡給付記錄資料 (有資料再印)
			if (caseData.getDiePayList() != null) {
				List<OldAgeReviewRpt01DiePayDataCase> diePayList = caseData.getDiePayList();
				for (int nDiePayCount = 0; nDiePayCount < diePayList.size(); nDiePayCount++) { // ... [
					OldAgeReviewRpt01DiePayDataCase diePayData = diePayList.get(nDiePayCount);
					
					// 印死亡給付記錄表頭
					if (nDiePayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 死亡給付記錄 表頭
						addColumn(table, 60, 1, "申請死亡給付記錄：", fontCh12b, 0, LEFT);
					}
					
					// 死亡給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					addEmptyRow(table, 4);
					
					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
						deleteRow(table, 4);
						document.add(table);
						table = addHeader(caseData, false, earlyWarning);
					}
					else {
						deleteRow(table, 4);
					}
					
					addColumn(table, 7, 1, "申請日期", fontCh12b, 0, LEFT);
					addColumn(table, 18, 1, "受理編號/給付年月", fontCh12, 0, LEFT);
					addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "死亡日期", fontCh12, 0, LEFT);
					addColumn(table, 6, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 7, 1, diePayData.getBmApDteString(), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 18, 1, diePayData.getBmApNoString() + ((StringUtils.isNotBlank(diePayData.getBmPayYm())) ? ((StringUtils.isNotBlank(diePayData.getBmApNo())) ? (" / " + diePayData.getBmPayYmString()) : diePayData.getBmPayYmString()) : ""), fontCh12, 0, LEFT); // 受理編號/給付年月
					addColumn(table, 8, 1, diePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
					addColumn(table, 7, 1, diePayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 死亡日期
					
					if (diePayData.getBmChkAmt() != null && diePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 6, 1, formatBigDecimalToInteger(diePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 7, 1, diePayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 7, 1, diePayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
					// ---
					addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 8, 1, "不給付日期", fontCh12, 0, LEFT);
					addColumn(table, 42, 1, "　", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 10, 1, diePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(diePayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(diePayData.getBmNrepDateString())) ? (" / " + diePayData.getBmNdocMk()) : diePayData.getBmNdocMk()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
					addColumn(table, 8, 1, diePayData.getBmNopDateString(), fontCh12, 0, LEFT); // 不給付日期
					addColumn(table, 42, 1, "　", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nDiePayCount == diePayList.size() - 1) 
							&& ((caseData.getSurvivorAnnuityPayList() != null && caseData.getSurvivorAnnuityPayList().size() > 0) 
									|| (caseData.getInjuryPayList() != null && caseData.getInjuryPayList().size() > 0) 
									|| (caseData.getJoblessPayList() != null && caseData.getJoblessPayList().size() > 0) 
									|| (caseData.getNpPayList() != null && caseData.getNpPayList().size() > 0))) {
						// 空白行
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
				} // ] ... end for (int nDiePayCount = 0; nDiePayCount < diePayList.size(); nDiePayCount++)
				
			}

			
		}

		/**
		 * 申請失能年金記錄資料
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public void printDisableAnnuitys(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 申請失能年金記錄資料 (有資料再印)
			if (caseData.getDisableAnnuityPayList() != null) {
				List<OldAgeReviewRpt01DisableAnnuityPayDataCase> disableAnnuityPayList = caseData.getDisableAnnuityPayList();
				for (int nDisableAnnuityPayCount = 0; nDisableAnnuityPayCount < disableAnnuityPayList.size(); nDisableAnnuityPayCount++) { // ... [
					OldAgeReviewRpt01DisableAnnuityPayDataCase disableAnnuityPayData = disableAnnuityPayList.get(nDisableAnnuityPayCount);
					
					// 印失能年金記錄表頭
					if (nDisableAnnuityPayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 傷病給付記錄 表頭
						addColumn(table, 60, 1, "申請失能年金記錄：", fontCh12b, 0, LEFT);
					}
					
					// 失能年金記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					addEmptyRow(table, 4);
					
					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
						deleteRow(table, 4);
						document.add(table);
						table = addHeader(caseData, false, earlyWarning);
					}
					else {
						deleteRow(table, 4);
					}
					
					addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 22, 1, "受理編號/核定年月/給付年月", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "診斷失能日期", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "失能等級", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
					
					// ---
					addColumn(table, 8, 1, disableAnnuityPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
					addColumn(table, 7, 1, DateUtility.formatChineseDateString(disableAnnuityPayData.getAppDate(), false), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 22, 1, disableAnnuityPayData.getApNoString() + " / " + StringUtils.defaultString(disableAnnuityPayData.getIssuYmString()) + " / " + StringUtils.defaultString(disableAnnuityPayData.getPayYmString()), fontCh12, 0, LEFT); // 受理編號/核定年月/給付年月
					addColumn(table, 9, 1, DateUtility.formatChineseDateString(disableAnnuityPayData.getEvtJobDate(), false), fontCh12, 0, LEFT); // 診斷失能日期
					addColumn(table, 7, 1, disableAnnuityPayData.getCriInJcl(), fontCh12, 0, LEFT); // 失能等級
					
					if (disableAnnuityPayData.getIssueAmt() != null && disableAnnuityPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 7, 1, formatBigDecimalToInteger(disableAnnuityPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					// ---
					addColumn(table, 8, 1, "案件類別", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "處理狀態", fontCh12, 0, LEFT);
					addColumn(table, 18, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
					addColumn(table, 25, 1, "失能項目", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 8, 1, disableAnnuityPayData.getCaseTypString(), fontCh12, 0, LEFT); // 案件類別
					addColumn(table, 9, 1, disableAnnuityPayData.getProcStatString(), fontCh12, 0, LEFT); // 處理狀態
					if (StringUtils.isNotBlank(disableAnnuityPayData.getCloseDate()) || StringUtils.isNotBlank(disableAnnuityPayData.getCloseCauseStr())) {
						addColumn(table, 18, 1, DateUtility.formatChineseDateString(disableAnnuityPayData.getCloseDate(), false) + "/" + disableAnnuityPayData.getCloseCauseStr(), fontCh12, 0, LEFT); // 結案日期/結案原因
					}
					else {
						addColumn(table, 18, 1, "　", fontCh12, 0, LEFT); // 結案日期/結案原因
					}
					addColumn(table, 25, 1, disableAnnuityPayData.getCriInJdp(), fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nDisableAnnuityPayCount == disableAnnuityPayList.size() - 1) 
							&& ((caseData.getDiePayList() != null && caseData.getDiePayList().size() > 0) 
									|| (caseData.getSurvivorAnnuityPayList() != null && caseData.getSurvivorAnnuityPayList().size() > 0) 
									|| (caseData.getInjuryPayList() != null && caseData.getInjuryPayList().size() > 0) 
									|| (caseData.getJoblessPayList() != null && caseData.getJoblessPayList().size() > 0) 
									|| (caseData.getNpPayList() != null && caseData.getNpPayList().size() > 0))) {
						// 空白行
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
				} // ] ... end for (int nDisableAnnuityPayCount = 0; nDisableAnnuityPayCount < disableAnnuityPayList.size(); nDisableAnnuityPayCount++)
				
			}

		}

		/**
		 * 申請失能給付記錄資料
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public void printDisablePays(OldAgeReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 申請失能給付記錄資料 (有資料再印)
			if (caseData.getDisablePayList() != null) {
				List<OldAgeReviewRpt01DisablePayDataCase> disablePayList = caseData.getDisablePayList();
				for (int nDisablePayCount = 0; nDisablePayCount < disablePayList.size(); nDisablePayCount++) { // ... [
					OldAgeReviewRpt01DisablePayDataCase disablePayData = disablePayList.get(nDisablePayCount);
					
					// 印失能給付記錄表頭
					if (nDisablePayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 失能給付記錄 表頭
						addColumn(table, 60, 1, "申請失能給付記錄：", fontCh12b, 0, LEFT);
					}
					
					// 失能給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					addEmptyRow(table, 4);
					
					if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
						deleteRow(table, 4);
						document.add(table);
						table = addHeader(caseData, false, earlyWarning);
					}
					else {
						deleteRow(table, 4);
					}
					
					addColumn(table, 11, 1, "申請（受理）日期", fontCh12b, 0, LEFT);
					addColumn(table, 18, 1, "受理編號/給付年月", fontCh12, 0, LEFT);
					addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "診斷失能日期", fontCh12, 0, LEFT);
					addColumn(table, 14, 1, "失能項目", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 11, 1, disablePayData.getBmApDteString(), fontCh12, 0, LEFT); // 申請（受理）日期
					addColumn(table, 18, 1, disablePayData.getBmApNoString() + ((StringUtils.isNotBlank(disablePayData.getBmPayYm())) ? ((StringUtils.isNotBlank(disablePayData.getBmApNo())) ? (" / " + disablePayData.getBmPayYmString()) : disablePayData.getBmPayYmString()) : ""), fontCh12, 0, LEFT); // 受理編號/給付年月
					addColumn(table, 8, 1, disablePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
					addColumn(table, 9, 1, disablePayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 診斷失能日期
					addColumn(table, 14, 1, disablePayData.getBmCriInjDp(), fontCh12, 0, LEFT); // 失能項目
					// ---
					addColumn(table, 7, 1, "失能等級", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 8, 1, "不給付日期", fontCh12, 0, LEFT);
					addColumn(table, 14, 1, "　", fontCh12, 0, LEFT);
					// ---
					addColumn(table, 7, 1, disablePayData.getBmCriInjCl(), fontCh12, 0, LEFT); // 失能等級
					
					if (disablePayData.getBmChkAmt() != null && disablePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 7, 1, formatBigDecimalToInteger(disablePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 7, 1, disablePayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 7, 1, disablePayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 10, 1, disablePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(disablePayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(disablePayData.getBmNrepDateString())) ? (" / " + disablePayData.getBmNdocMk()) : disablePayData.getBmNdocMk()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
					addColumn(table, 8, 1, disablePayData.getBmNopDateString(), fontCh12, 0, LEFT); // 不給付日期
					addColumn(table, 14, 1, "　", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nDisablePayCount == disablePayList.size() - 1) 
							&& ((caseData.getDisableAnnuityPayList() != null && caseData.getDisableAnnuityPayList().size() > 0) 
									|| (caseData.getDiePayList() != null && caseData.getDiePayList().size() > 0) 
									|| (caseData.getSurvivorAnnuityPayList() != null && caseData.getSurvivorAnnuityPayList().size() > 0) 
									|| (caseData.getInjuryPayList() != null && caseData.getInjuryPayList().size() > 0) 
									|| (caseData.getJoblessPayList() != null && caseData.getJoblessPayList().size() > 0) 
									|| (caseData.getNpPayList() != null && caseData.getNpPayList().size() > 0))) {
						// 空白行
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
				} // ] ... end for (int nDisablePayCount = 0; nDisablePayCount < disablePayList.size(); nDisablePayCount++)
			}
				
			}
    	
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 10, 4, 20, 20);
        return document;
    }

    /**
     * 加入分隔線
     * 
     * @param table
     * @param icolspan Column Span
     * @throws Exception
     */
    public void addLine(Table table) throws Exception {
        addColumn(table, 60, 1, line, fontCh8, 0, LEFT);
    }

    /**
     * 加入空白行
     * 
     * @param table
     * @param rows 行數
     * @throws Exception
     */
    public void addEmptyRow(Table table, int rows) throws Exception {
        for (int i = 0; i < rows; i++) {
            addColumn(table, 60, 1, "　", fontCh12, 0, LEFT); // 注意: 內容是全形空白
        }
    }

    /**
     * 刪除行
     * 
     * @param table
     * @param rows 行數
     * @throws Exception
     */
    public void deleteRow(Table table, int rows) throws Exception {
        for (int i = 0; i < rows; i++) {
            table.deleteLastRow();
        }
    }

    /**
     * 建立表頭
     * 
     * @param payListSize 事故者 給付資料 筆數
     * @param attached 是否為附表
     * @return
     * @throws Exception
     */
    public Table addHeader(OldAgeReviewRpt01Case caseData, boolean attached, String earlyWarning) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        String earlyWarningDesc = "";
        if (earlyWarning.equals("Y"))
            earlyWarningDesc = "＊預警案件";
        else
            earlyWarningDesc = "";

        if (caseData.getIssueAmtData() != null)
            addColumn(table, 15, 1, "版次：" + StringUtils.defaultString(caseData.getIssueAmtData().getVeriSeq()), fontCh12, 0, LEFT);
        else
            addColumn(table, 15, 1, "版次：", fontCh12, 0, LEFT);
        addColumn(table, 30, 1, "勞保老年年金給付受理/審核清單", fontCh18, 0, CENTER);
        addColumn(table, 15, 1, "頁次：" + StringUtils.leftPad(String.valueOf(writer.getCurrentPageNumber() - nPage), 3, "0"), fontCh12, 0, LEFT);
        // 第 ? 頁 / 共 ? 頁 中的 第 ? 頁 部份
        // [
        // addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
        // createPageText(500, 800, writer.getCurrentPageNumber() - nPage,"頁次：", " / ", 12, 150, 50);
        // ]
        // ---
        addColumn(table, 15, 1, "受理日期：" + caseData.getCrtTimeString(), fontCh12, 0, LEFT);

        // addColumn(table, 30, 1, (caseData.getPayList().size() == 0) ? " " : ((attached) ? "【附表】" : "【總表】"), fontCh12, 0, CENTER);
        if (writer.getCurrentPageNumber() - nPage == 1) {
            if (StringUtils.equalsIgnoreCase(caseData.getCaseTyp(), ConstantKey.BAAPPBASE_CASETYP_1))
                addColumn(table, 30, 1, caseData.getCheckinStr() + "【" + ConstantKey.BAAPPBASE_CASETYP_STR_1 + "】" + earlyWarningDesc, fontCh12, 0, CENTER);
            else if (StringUtils.equalsIgnoreCase(caseData.getCaseTyp(), ConstantKey.BAAPPBASE_CASETYP_3))
                addColumn(table, 30, 1, caseData.getCheckinStr() + "【" + ConstantKey.BAAPPBASE_CASETYP_STR_3 + "】" + earlyWarningDesc, fontCh12, 0, CENTER);
            else if (StringUtils.equalsIgnoreCase(caseData.getCaseTyp(), ConstantKey.BAAPPBASE_CASETYP_6))
                addColumn(table, 30, 1, caseData.getCheckinStr() + "【" + ConstantKey.BAAPPBASE_CASETYP_STR_6 + "】" + earlyWarningDesc, fontCh12, 0, CENTER);
            else if (StringUtils.equalsIgnoreCase(caseData.getCaseTyp(), ConstantKey.BAAPPBASE_CASETYP_2))
                addColumn(table, 30, 1, caseData.getCheckinStr() + "【" + ConstantKey.BAAPPBASE_CASETYP_STR_2 + "】" + earlyWarningDesc, fontCh12, 0, CENTER);
            else if (StringUtils.equalsIgnoreCase(caseData.getCaseTyp(), ConstantKey.BAAPPBASE_CASETYP_4))
                addColumn(table, 30, 1, caseData.getCheckinStr() + "【" + ConstantKey.BAAPPBASE_CASETYP_STR_4 + "】" + earlyWarningDesc, fontCh12, 0, CENTER);
            else if (StringUtils.equalsIgnoreCase(caseData.getCaseTyp(), ConstantKey.BAAPPBASE_CASETYP_5))
                addColumn(table, 30, 1, caseData.getCheckinStr() + "【" + ConstantKey.BAAPPBASE_CASETYP_STR_5 + "】" + earlyWarningDesc, fontCh12, 0, CENTER);
            else
                addColumn(table, 30, 1, caseData.getCheckinStr()+ " " + earlyWarningDesc, fontCh12, 0, CENTER);
        }
        else {
            addColumn(table, 30, 1, " ", fontCh12, 0, CENTER);
        }
        addColumn(table, 15, 1, "印表日期：" + printDate, fontCh12, 0, LEFT);
        // ---
        addColumn(table, 20, 1, "申請日期：" + caseData.getAppDateString(), fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "受理編號：" + caseData.getApNoString(), fontCh12, 0, LEFT);
        addBarcode39NoLabel(table, caseData.getApNo(), 75, 75, -5, 20, 1, 0, LEFT, MIDDLE);
        // ---
        // 首次給付年月為空 用給付年月起日帶入
        if (StringUtils.isNotBlank(caseData.getPayYmsString())) {
            addColumn(table, 20, 1, "首次給付年月：" + caseData.getPayYmsString(), fontCh12, 0, LEFT);
        }
        else if (caseData.getPayList().size() > 0) {
            addColumn(table, 20, 1, "首次給付年月：" + caseData.getPayList().get(0).getPayYmString(), fontCh12, 0, LEFT);
        }
        else {
            addColumn(table, 20, 1, "首次給付年月：", fontCh12, 0, LEFT);
        }

        addColumn(table, 20, 1, "核定年月：" + caseData.getIssuYmString(), fontCh12, 0, LEFT);
        if (caseData.getPayList().size() > 0)
            addColumn(table, 20, 1, "給付年月起迄 " + caseData.getPayList().get(0).getPayYmString() + " - " + caseData.getPayList().get(caseData.getPayList().size() - 1).getPayYmString(), fontCh12, 0, LEFT);
        else
            addColumn(table, 20, 1, "給付年月起迄：       -       ", fontCh12, 0, LEFT);
        // ---
        addLine(table);
        // ---
        addColumn(table, 9, 1, caseData.getEvtName(), fontCh16, 0, LEFT);
        addColumn(table, 4, 1, StringUtils.defaultString(caseData.getEvtAge()) + "歲", fontCh16, 8, 0, LEFT);
        addColumn(table, 3, 1, caseData.getEvtSexString(), fontCh16, 0, LEFT);
        addColumn(table, 21, 1, caseData.getEvtBrDateString(), fontCh16, 0, LEFT);
        addColumn(table, 9, 1, caseData.getEvtIdnNo(), fontCh16, 0, LEFT);
        addColumn(table, 2, 1, ValidateUtility.validatePersonIdNo(caseData.getEvtIdnNo()) ? "" : "W", fontCh16, 0, LEFT);
        addBarcode39NoLabel(table, StringUtils.defaultString(caseData.getEvtIdnNo()), 75, 75, -5, 12, 1, 0, LEFT, MIDDLE);
        // ---
        addLine(table);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<OldAgeReviewRpt01Case> caseList) throws Exception {
        String payKindMsg = "";
        try {
            document.open();

            for (int index = 0; index < caseList.size(); index++) {
                // 頁次處理
                if (index > 0) {
                    nPage = writer.getPageNumber();
                }

                OldAgeReviewRpt01Case caseData = (OldAgeReviewRpt01Case) caseList.get(index);
                payKindMsg = StringUtils.substring(caseData.getApNo(), 0, 1);
                // 表頭
                String earlyWarning = "N";
                List<OldAgeReviewRpt01ChkfileDataCase> chkfileDataList01 = caseData.getChkfileDataList();
                if (chkfileDataList01.size() > 0) {
                    // 取得事故者編審註記資料
                    List<StringBuffer> chkfileStringList = new ArrayList<StringBuffer>();
                    StringBuffer chkfileString = new StringBuffer("");
                    for (OldAgeReviewRpt01ChkfileDataCase chkfileData : chkfileDataList01) {
                        // 若 編審註記代碼 (CHKCODE) 值為 P1 或 P2 或 P3，表頭要顯示「＊預警案件」
                        if (StringUtils.equalsIgnoreCase(chkfileData.getChkCode(), "P1") || StringUtils.equalsIgnoreCase(chkfileData.getChkCode(), "P2") || StringUtils.equalsIgnoreCase(chkfileData.getChkCode(), "P3"))
                            earlyWarning = "Y";
                    }
                }

                List<OldAgeReviewRpt01BenDataCase> benList01 = caseData.getBenList();
                for (int nBenList = 0; nBenList < benList01.size(); nBenList++) {
                    OldAgeReviewRpt01BenDataCase benData = benList01.get(nBenList);
                    List<OldAgeReviewRpt01BenPayDataCase> benPayList = benData.getBenPayList();

                    for (OldAgeReviewRpt01BenPayDataCase benPayData : benPayList) {
                        // 取得受款人編審註記資料
                        // 若 編審註記代碼 (CHKCODE) 值為 P1 或 P2 或 P3，表頭要顯示「＊預警案件」
                        if (benData.getChkfileDataBy(benPayData.getPayYm()).contains("P1") || benData.getChkfileDataBy(benPayData.getPayYm()).contains("P2") || benData.getChkfileDataBy(benPayData.getPayYm()).contains("P3")) // 編審註記
                            earlyWarning = "Y";
                    }
                }

                Table table = addHeader(caseData, false, earlyWarning);

                // 核定總額資料
                // [
                OldAgeReviewRpt01IssueAmtDataCase issueAmtData = caseData.getIssueAmtData();

                addColumn(table, 27, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 11, 1, "審核", fontCh12, 0, CENTER);
                addColumn(table, 11, 1, "覆核", fontCh12, 0, CENTER);
                addColumn(table, 11, 1, "總核", fontCh12, 0, CENTER);
                // ---
                addColumn(table, 7, 1, "投保金額：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, formatBigDecimalToInteger(caseData.getLsInsmAmt()), fontCh12, 0, RIGHT); // 投保金額
                addColumn(table, 47, 1, " ", fontCh12, 0, LEFT);
                // ---
                addColumn(table, 7, 1, "平均薪資：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, formatBigDecimalToInteger(caseData.getDecideData().getInsAvgAmt()), fontCh12, 0, RIGHT); // 平均薪資
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (更正前：) 標題 - 有值才顯示, 此段預留
                addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (更正前：) 的值 - 有值才顯示, 此段預留
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 33, 1, " ", fontCh12, 0, LEFT);
                // ---
                addColumn(table, 7, 1, "核定總額：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, formatBigDecimalToInteger(issueAmtData.getIssueAmt()), fontCh12, 0, RIGHT); // 核定總額
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (扣減：) 標題 - 有值才顯示, 此段預留
                addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (扣減：) 的值 - 有值才顯示, 此段預留
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 33, 1, " ", fontCh12, 0, LEFT);
                // ---
                // 調整總額 - 有值時此欄目才顯示
                if (issueAmtData.getAdjustAmt() != null && issueAmtData.getAdjustAmt().compareTo(new BigDecimal(0)) != 0) {
                    addColumn(table, 7, 1, "調整總額：", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, formatBigDecimalToInteger(issueAmtData.getAdjustAmt()), fontCh12, 0, RIGHT); // 調整總額
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (扣減：) 標題 - 有值才顯示, 此段預留
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (扣減：) 的值 - 有值才顯示, 此段預留
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 33, 1, " ", fontCh12, 0, LEFT);
                }
                // ---
                // 補發總額 - 有值時此欄目才顯示
                if (issueAmtData.getSupAmt() != null && issueAmtData.getSupAmt().compareTo(new BigDecimal(0)) != 0) {
                    addColumn(table, 7, 1, "補發總額：", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, formatBigDecimalToInteger(issueAmtData.getSupAmt()), fontCh12, 0, RIGHT); // 補發總額
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (扣減：) 標題 - 有值才顯示, 此段預留
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (扣減：) 的值 - 有值才顯示, 此段預留
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 33, 1, " ", fontCh12, 0, LEFT);
                }
                // ---
                // 紓困總額 - 有值時此欄目才顯示
                if (issueAmtData.getOffsetAmt() != null && issueAmtData.getOffsetAmt().compareTo(new BigDecimal(0)) != 0) {
                    addColumn(table, 7, 1, "紓困總額：", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, formatBigDecimalToInteger(issueAmtData.getOffsetAmt()), fontCh12, 0, RIGHT); // 補發總額
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (扣減：) 標題 - 有值才顯示, 此段預留
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (扣減：) 的值 - 有值才顯示, 此段預留
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 33, 1, " ", fontCh12, 0, LEFT);
                }
                // ---
                // 扣減總額 - 有值時此欄目才顯示
                if (issueAmtData.getOtherAmt() != null && issueAmtData.getOtherAmt().compareTo(new BigDecimal(0)) != 0) {
                    addColumn(table, 7, 1, "扣減總額：", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, formatBigDecimalToInteger(issueAmtData.getOtherAmt()), fontCh12, 0, RIGHT); // 扣減總額
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (扣減：) 標題 - 有值才顯示, 此段預留
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (扣減：) 的值 - 有值才顯示, 此段預留
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 33, 1, " ", fontCh12, 0, LEFT);
                }
                // ---
                addColumn(table, 7, 1, "實付總額：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, formatBigDecimalToInteger(issueAmtData.getAplpayAmt()), fontCh12, 0, RIGHT); // 實付總額
                addColumn(table, 47, 1, " ", fontCh12, 0, LEFT);
                // 分隔線
                addLine(table);
                // ]
                // 核定總額資料

                // 給付資料
                // [
                List<OldAgeReviewRpt01PayDataCase> payList = caseData.getPayList();

                if (payList.size() > 0) {
                    // 給付資料表頭
                    addColumn(table, 6, 1, "給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定金額", fontCh12, 0, RIGHT);
                    // addColumn(table, 11, 1, "/ 調整前金額", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "應收金額", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "補發金額", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "紓困金額", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "扣減總金額", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "匯款匯費", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "實付金額", fontCh12, 0, RIGHT);
                    addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT);

                    // 給付資料明細
                    for (int nPayList = 0; nPayList < payList.size(); nPayList++) {
                        // 給付資料明細每筆為一行, 在塞資料前先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        OldAgeReviewRpt01PayDataCase payData = payList.get(nPayList);

                        addColumn(table, 6, 1, payData.getPayYmString(), fontCh12, 0, LEFT); // 給付年月

                        if (payData.getIssueAmt() != null && payData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(payData.getIssueAmt()), fontCh12, 0, RIGHT); // 核定
                        else
                            addColumn(table, 7, 1, "　", fontCh12, 0, RIGHT); // 核定

                        // if (payData.getIssueAmt() != null && payData.getIssuCalcAmt() != null && payData.getIssuCalcAmt().compareTo(payData.getIssueAmt()) != 0 && payData.getIssuCalcAmt().compareTo(new BigDecimal(0)) != 0)
                        // addColumn(table, 11, 1, formatBigDecimalToInteger(payData.getIssuCalcAmt()), fontCh12, 0, RIGHT); // / 物價調整金額
                        // else
                        // addColumn(table, 11, 1, " ", fontCh12, 0, RIGHT); // / 物價調整金額

                        if (payData.getRecAmt() != null && payData.getRecAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(payData.getRecAmt()), fontCh12, 0, RIGHT); // 應收金額
                        else
                            addColumn(table, 7, 1, "　", fontCh12, 0, RIGHT); // 應收金額

                        if (payData.getSupAmt() != null && payData.getSupAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(payData.getSupAmt()), fontCh12, 0, RIGHT); // 補發金額
                        else
                            addColumn(table, 7, 1, "　", fontCh12, 0, RIGHT); // 補發金額

                        if (payData.getOffsetAmt() != null && payData.getOffsetAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(payData.getOffsetAmt()), fontCh12, 0, RIGHT); // 紓困金額
                        else
                            addColumn(table, 7, 1, "　", fontCh12, 0, RIGHT); // 紓困金額

                        if (payData.getOtherAmt() != null && payData.getOtherAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(payData.getOtherAmt()), fontCh12, 0, RIGHT); // 事故者扣減總金額
                        else
                            addColumn(table, 7, 1, "　", fontCh12, 0, RIGHT); // 事故者扣減總金額

                        if (payData.getPayRate() != null && payData.getPayRate().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(payData.getPayRate()), fontCh12, 0, RIGHT); // 匯款匯費
                        else
                            addColumn(table, 7, 1, "0", fontCh12, 0, RIGHT); // 匯款匯費

                        if (payData.getAplpayAmt() != null && payData.getAplpayAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(payData.getAplpayAmt()), fontCh12, 0, RIGHT); // 實付金額
                        else
                            addColumn(table, 7, 1, "0", fontCh12, 0, RIGHT); // 實付金額
                        addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT);
                    }

                    // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不印分隔線了
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                        addLine(table);
                    }
                }
                // ]
                // 給付資料

                // 核定資料
                // [
                // 核定資料有四行, 在塞資料前先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 4);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    deleteRow(table, 4);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 4);
                }

                OldAgeReviewRpt01DecideDataCase decideData = caseData.getDecideData();
                
                
                List<OldAgeReviewRpt01OnceAvgAmtDataCase> onceAvgAmtList = caseData.getOnceAvgAmtList();
                for (int nMonth = 0; nMonth < 1; nMonth++) {
                    OldAgeReviewRpt01OnceAvgAmtDataCase onceAvgAmtData = null;
                    if(onceAvgAmtList.size() > 0) {
                        onceAvgAmtData = onceAvgAmtList.get(nMonth);
                    }

                addColumn(table, 7, 1, "申請單位", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "最後單位", fontCh12, 0, LEFT);
                addColumn(table, 4, 1, "計算", fontCh12, 0, CENTER);
                addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);
                addColumn(table, 5, 1, "金額", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "投保", fontCh12, 0, CENTER);
                addColumn(table, 6, 1, "(年-日)", fontCh12, 0, CENTER);
                addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);
                addColumn(table, 6, 1, "實付", fontCh12, 0, CENTER);
                addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);
                addColumn(table, 6, 1, "老年", fontCh12, 0, CENTER);
                addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);
                addColumn(table, 7, 1, "國保年資", fontCh12, 0, CENTER);
                addColumn(table, 3, 1, "　", fontCh12, 0, LEFT);
                // ---
                addColumn(table, 7, 1, caseData.getApUbno(), fontCh12, 0, LEFT); // 申請單位
                addColumn(table, 7, 1, caseData.getLsUbno(), fontCh12, 0, LEFT); // 最後單位
                addColumn(table, 4, 1, decideData.getOldab(), fontCh12, 0, CENTER); // 計算
                addColumn(table, 1, 1, "/", fontCh12, 0, CENTER); // /
                addColumn(table, 5, 1, formatBigDecimalToInteger(((decideData.getOldaAmt() == null) ? new BigDecimal(0) : decideData.getOldaAmt()).max(((decideData.getOldbAmt() == null) ? new BigDecimal(0) : decideData.getOldbAmt()))), fontCh12, 0, RIGHT); // 金額
                addColumn(table, 5, 1, getBigDecimalValue(decideData.getNitrmY()) + "-" + getBigDecimalValue(decideData.getNitrmM()), fontCh12, 0, CENTER); // 投保
                addColumn(table, 6, 1, "(" + getBigDecimalValue(decideData.getItrmY()) + "-" + getBigDecimalValue(decideData.getItrmD()) + ")", fontCh12, 0, CENTER); // (年-日)
                addColumn(table, 1, 1, "/", fontCh12, 0, CENTER); // /
                addColumn(table, 6, 1, getBigDecimalValue(decideData.getAplPaySeniY()) + "-" + getBigDecimalValue(decideData.getAplPaySeniM()), fontCh12, 0, CENTER); // 實付
                addColumn(table, 1, 1, "/", fontCh12, 0, CENTER); // /
                
                
                if (caseData.getOldSeniab().equals("2")) { // 採計為 2
                    if(onceAvgAmtData != null) {
                        addColumn(table, 6, 1, getBigDecimalValue(onceAvgAmtData.getOldTY()) + "-" + getBigDecimalValue(onceAvgAmtData.getOldTD()), fontCh12, 0, CENTER); // 老年
                    } else {
                        addColumn(table, 6, 1, "", fontCh12, 0, CENTER); // 老年
                    }
                }
                else { // 採計為 1 或空值
                    addColumn(table, 6, 1, getBigDecimalValue(decideData.getNoldtY()) + "-" + getBigDecimalValue(decideData.getNoldtM()), fontCh12, 0, CENTER); // 老年
                }
                
                
                addColumn(table, 1, 1, "/", fontCh12, 0, CENTER); // /
                addColumn(table, 7, 1, getBigDecimalValue(decideData.getValseniY()) + "-" + getBigDecimalValue(decideData.getValseniM()), fontCh12, 0, CENTER); // 國保年資
                addColumn(table, 3, 1, "　", fontCh12, 0, LEFT);
                // ---
                addColumn(table, 7, 1, "事故日期", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "退保日期", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "符合日期", fontCh12, 0, CENTER);
                addColumn(table, 7, 1, (StringUtils.isNotBlank(caseData.getEvtDieDate()) ? "死亡日期" : ""), fontCh12, 0, CENTER); // 有值才顯示
                addColumn(table, 7, 1, (StringUtils.isNotBlank(caseData.getEvtMissingDate()) ? "失蹤日期" : ""), fontCh12, 0, CENTER); // 有值才顯示
                // BADAPR.OLDRATE, BADAPR.OLDRATESDATE, BADAPR.OLDRATEEDATE 有值才顯示
                // BAAPPBASE.APITEM = '1' 顯示 展延期間/比率, BAAPPBASE.APITEM = '2' 顯示 減額期間/比率
                boolean showOldRateData = false;
                if (decideData.getOldRate() != null && decideData.getOldRate().compareTo(new BigDecimal(0)) != 0) {
                    addColumn(table, 12, 1, ((StringUtils.equals(caseData.getApItem(), "1")) ? "展延期間/比率" : ((StringUtils.equals(caseData.getApItem(), "2")) ? "減給期間/比率" : "")), fontCh12, 0, CENTER);
                    showOldRateData = true;
                }
                else {
                    addColumn(table, 12, 1, " ", fontCh12, 0, CENTER);
                }
                addColumn(table, 13, 1, "申請項目", fontCh12, 0, CENTER);
                // ---
                addColumn(table, 7, 1, caseData.getEvtJobDateString(), fontCh12, 0, LEFT); // 事故日期
                addColumn(table, 7, 1, caseData.getEvtDateString(), fontCh12, 0, LEFT); // 退保日期
                addColumn(table, 7, 1, caseData.getEvtEligibleDateString(), fontCh12, 0, CENTER); // 符合日期
                addColumn(table, 7, 1, caseData.getEvtDieDateString(), fontCh12, 0, CENTER); // 死亡日期 - 有值才顯示
                addColumn(table, 7, 1, caseData.getEvtMissingDateString(), fontCh12, 0, CENTER); // 失蹤日期 - 有值才顯示
                if (showOldRateData) {
                    String dateBegin = "";
                    String dateEnd = "";
                    String oldRateYears = "";
                    String oldRateMonths = "";
                    if (StringUtils.isNotBlank(decideData.getOldRateSdate()) && StringUtils.isNotBlank(decideData.getOldRateEdate())) {
                        if (Integer.parseInt(decideData.getOldRateEdate()) >= Integer.parseInt(decideData.getOldRateSdate())) {
                            dateBegin = decideData.getOldRateSdate() + "01";
                            dateEnd = decideData.getOldRateEdate() + "01";
                        }
                        else {
                            dateBegin = decideData.getOldRateEdate() + "01";
                            dateEnd = decideData.getOldRateSdate() + "01";
                        }

                        int nOldRateMonths = DateUtility.wholeMonthsBetween(dateBegin, dateEnd);

                        oldRateYears = String.valueOf(nOldRateMonths / 12);
                        oldRateMonths = String.valueOf(nOldRateMonths % 12);
                    }

                    addColumn(table, 12, 1, StringUtils.rightPad(oldRateYears, 2, " ") + "-" + StringUtils.rightPad(oldRateMonths, 2, " ") + "/" + getBigDecimalValue(decideData.getOldRate()) + "%", fontCh12, 0, CENTER); // 展延期間/比率 or 減額期間/比率 - 有值才顯示
                }
                else {
                    addColumn(table, 12, 1, " ", fontCh12, 0, CENTER); // 展延期間/比率 or 減額期間/比率 - 有值才顯示
                }
                // 20090511 改為秀代號就好
                addColumn(table, 13, 1, caseData.getApItem(), fontCh12, 0, CENTER);
                // addColumn(table, 13, 1, caseData.getEncodeApItem(), fontCh12, 0, CENTER);

                // ---
                addColumn(table, 10, 1, "前次核定金額", fontCh12, 0, LEFT);
                addColumn(table, 4, 1, " ", fontCh12, 0, RIGHT);
                addColumn(table, 10, 1, "發放方式", fontCh12, 0, LEFT); // 20121024 Kiyomi
                addColumn(table, 2, 1, " ", fontCh12, 0, RIGHT);
                addColumn(table, 10, 1, "同單位(年日)", fontCh12, 0, LEFT); // 20170809 Kiyomi
                addColumn(table, 24, 1, " ", fontCh12, 0, RIGHT);

                if (caseData.getIssuCalcAmt() != null && caseData.getIssuCalcAmt().compareTo(new BigDecimal(0)) != 0)
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseData.getIssuCalcAmt()), fontCh12, 0, RIGHT); // 前次核定金額
                else
                    addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 前次核定金額

                addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT);

                if (StringUtils.isBlank(caseData.getInterValMonth()) || caseData.getInterValMonth().equals("0") || caseData.getInterValMonth().equals("1")) {
                    addColumn(table, 10, 1, "按月發放", fontCh12, 0, LEFT); // 發放方式
                }
                else {
                    addColumn(table, 10, 1, "按" + caseData.getInterValMonth() + "個月發放", fontCh12, 0, LEFT); // 發放方式
                }

                addColumn(table, 2, 1, " ", fontCh12, 0, RIGHT);
                addColumn(table, 10, 1, caseData.getSamTY().toString() + "-" + caseData.getSamTD().toString(), fontCh12, 0, LEFT); // 同單位年資(年) - 同單位年資(日)
                addColumn(table, 24, 1, " ", fontCh12, 0, RIGHT);

                // ---

                // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                    addLine(table);
                }
                // ]
                // 核定資料

                // 事故者死亡一次給付相關資料
                // [
                // 若事故者死亡，則須查詢事故者一次給付相關資料
                if (StringUtils.isNotBlank(caseData.getEvtDieDate()) || StringUtils.isNotBlank(caseData.getCloseCause())) { // ... [
                    /**
                     * if (StringUtils.equals(caseData.getCloseCause(), "01")) {
                     */
                    // 事故者一次給付相關資料有四行, 在塞資料前先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 4);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 4);
                    }

                    OldAgeReviewRpt01DieOncePayDataCase dieOncePayData = caseData.getDieOncePayData();

                    addColumn(table, 10, 1, "結案原因", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "一次給付符合", fontCh12, 0, CENTER);
                    addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);
//                    addColumn(table, 7, 1, "一次年資", fontCh12, 0, CENTER);
//                    addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);
                    addColumn(table, 10, 1, "平均薪資(36)", fontCh12, 0, CENTER);
                    addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);
                    addColumn(table, 7, 1, "月數", fontCh12, 0, CENTER);
                    addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);
                    addColumn(table, 13, 1, "金額", fontCh12, 0, CENTER);
                    addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);
                    addColumn(table, 5, 1, "採計", fontCh12, 0, CENTER); // 20170809 Kiyomi
                    addColumn(table, 3, 1, "　", fontCh12, 0, CENTER);
                    // ---
                    BigDecimal onceOldAmt = (dieOncePayData.getOnceOldAmt() == null) ? new BigDecimal(0) : dieOncePayData.getOnceOldAmt();
                    BigDecimal annuAmt = (caseData.getAnnuAmt() == null) ? new BigDecimal(0) : caseData.getAnnuAmt();
                    BigDecimal dabAnnuAmt = (caseData.getDabAnnuAmt() == null) ? new BigDecimal(0) : caseData.getDabAnnuAmt();

                    addColumn(table, 10, 1, StringUtils.defaultString(caseData.getCloseCause()), fontCh12, 0, LEFT); // 結案原因
                    addColumn(table, 8, 1, caseData.getOncePayMk(), fontCh12, 0, CENTER); // 一次給付符合
                    addColumn(table, 1, 1, "/", fontCh12, 0, CENTER); // /



                        // addColumn(table, 7, 1, getBigDecimalValue(dieOncePayData.getOldtY()) + "-" + getBigDecimalValue(dieOncePayData.getOldtD()), fontCh12, 0, CENTER); // 一次年資
                        /**
                        if (caseData.getOldSeniab().equals("1")) {
                            addColumn(table, 7, 1, getBigDecimalValue(onceAvgAmtData.getNitrmY()) + "-" + getBigDecimalValue(onceAvgAmtData.getNitrmM()), fontCh12, 0, CENTER); // 一次年資
                        }
                        else {
                            addColumn(table, 7, 1, getBigDecimalValue(onceAvgAmtData.getOldTY()) + "-" + getBigDecimalValue(onceAvgAmtData.getOldTD()), fontCh12, 0, CENTER); // 一次年資
                        }

                        addColumn(table, 1, 1, "/", fontCh12, 0, CENTER); // /
                        */
                        // if (dieOncePayData.getOnceAvgAmt() != null && dieOncePayData.getOnceAvgAmt().compareTo(new BigDecimal(0)) != 0)
                        // addColumn(table, 7, 1, formatBigDecimalToInteger(dieOncePayData.getOnceAvgAmt()), fontCh12, 0, CENTER); // 平均薪資
                    if (onceAvgAmtData != null) {
                        if (onceAvgAmtData.getAvgWg() != null && onceAvgAmtData.getAvgWg().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 10, 1, formatBigDecimalToInteger(onceAvgAmtData.getAvgWg()), fontCh12, 0, CENTER); // 平均薪資
                        else
                            addColumn(table, 10, 1, "　", fontCh12, 0, CENTER); // 平均薪資
                    } else {
                        addColumn(table, 10, 1, "　", fontCh12, 0, CENTER); // 平均薪資
                    }

                    addColumn(table, 1, 1, "/", fontCh12, 0, CENTER); // /
                    // addColumn(table, 3, 1, getBigDecimalValue(dieOncePayData.getOncePayYm()), fontCh12, 0, CENTER); // 月數
                    if (caseData.getOncePayMk().equals("N") || caseData.getOncePayMk().equals("")) {
                        addColumn(table, 7, 1, "0", fontCh12, 0, CENTER); // 月數
                    } else {
                        addColumn(table, 7, 1, getBigDecimalValue(caseData.getOncePayM()), fontCh12, 0, CENTER); // 月數
                    }
                    addColumn(table, 1, 1, "/", fontCh12, 0, CENTER); // /

                    // if (dieOncePayData.getOncePayAmt() != null && dieOncePayData.getOncePayAmt().compareTo(new BigDecimal(0)) != 0)
                    // addColumn(table, 5, 1, formatBigDecimalToInteger(dieOncePayData.getOncePayAmt()), fontCh12, 0, CENTER); // 金額
                    if (caseData.getOncePayMk().equals("N") || caseData.getOncePayMk().equals("")) {
                        addColumn(table, 13, 1, "0", fontCh12, 0, CENTER); // 金額
                    } else {
                    
                        if (caseData.getOnceIssueAmt() != null && caseData.getOnceIssueAmt().compareTo(new BigDecimal(0)) >= 0)
                            addColumn(table, 13, 1, formatBigDecimalToInteger(caseData.getOnceIssueAmt()), fontCh12, 0, CENTER); // 金額
                        else
                            addColumn(table, 13, 1, "　", fontCh12, 0, CENTER); // 金額
                    }

                    addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);
                    // addColumn(table, 10, 1, caseData.getOldSeniabString(), fontCh12, 0, CENTER); // 一次請領之年資採計方式（中文描述）
                    addColumn(table, 5, 1, caseData.getOldSeniab(), fontCh12, 0, CENTER); // 一次請領之年資採計方式
                    addColumn(table, 3, 1, "　", fontCh12, 0, CENTER);
                    // ---
                    addColumn(table, 11, 1, "已領老年年金金額", fontCh12, 0, LEFT);
                    addColumn(table, 13, 1, "失能年金受理編號", fontCh12, 0, RIGHT);
                    addColumn(table, 13, 1, "已領失能年金金額", fontCh12, 0, RIGHT);
                    addColumn(table, 11, 1, "已領年金總額", fontCh12, 0, RIGHT);
                    addColumn(table, 10, 1, "試算老年差額金", fontCh12, 0, RIGHT); // 20170809 Kiyomi
                    addColumn(table, 2, 1, "　", fontCh12, 0, CENTER);

                    // ---
                    if (caseData.getAnnuAmt() != null && caseData.getAnnuAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 11, 1, formatBigDecimalToInteger(caseData.getAnnuAmt()), fontCh12, 0, RIGHT); // 已領老年年金金額
                    else
                        addColumn(table, 11, 1, "　", fontCh12, 0, RIGHT); // 已領老年年金金額
                    addColumn(table, 13, 1, caseData.getDabApNo(), fontCh12, 0, RIGHT); // 失能年金受理編號

                    if (dabAnnuAmt != null && dabAnnuAmt.compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 13, 1, formatBigDecimalToInteger(dabAnnuAmt), fontCh12, 0, RIGHT); // 己領失能年金金額
                    else
                        addColumn(table, 13, 1, "　", fontCh12, 0, RIGHT); // 己領失能年金金額

                    if (dabAnnuAmt.add(annuAmt) != null && dabAnnuAmt.add(annuAmt).compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 11, 1, formatBigDecimalToInteger(dabAnnuAmt.add(annuAmt)), fontCh12, 0, RIGHT); // 已領年金總額
                    else
                        addColumn(table, 11, 1, "　", fontCh12, 0, RIGHT); // 已領年金總額

                    if (caseData.getOncePayMk().equals("N") || caseData.getOncePayMk().equals("")) {
                        addColumn(table, 10, 1, "0", fontCh12, 0, RIGHT); // 老年差額金
                    } else {
                        if (caseData.getMarginAmt().compareTo(new BigDecimal(0)) >= 0) {
                            addColumn(table, 10, 1, formatBigDecimalToInteger(caseData.getMarginAmt()), fontCh12, 0, RIGHT); // 老年差額金
                        }
                        else {
                            addColumn(table, 10, 1, " ", fontCh12, 0, RIGHT); // 老年差額金
                        }
                    }

                    addColumn(table, 2, 1, "　", fontCh12, 0, CENTER);

                    // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不印分隔線了
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                        addLine(table);
                    }
                    /**
                     * } 
                     * else if (StringUtils.equals(caseData.getCloseCause(), "02") || StringUtils.equals(caseData.getCloseCause(), "03")) { 
                     *      // 如果結案原因 (CLOSECAUSE) 欄位值為 02, 只需顯示擇領欄其餘不用顯示 
                     *      addEmptyRow(table, 2); 
                     *      if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數 
                     *          deleteRow(table, 2); 
                     *          document.add(table); 
                     *          table = addHeader(caseData, false, earlyWarning); 
                     *      } 
                     *      else { 
                     *          deleteRow(table, 2); 
                     *      } 
                     *      addColumn(table, 60, 1, "結案原因", fontCh12, 0, LEFT); 
                     *      // --- addColumn(table, 60, 1, caseData.getCloseCause(), fontCh12,0, LEFT); // 擇領 
                     *      // 在塞分隔線前, 先隨便塞空白行測試是否需換頁 
                     *      addEmptyRow(table, 1); 
                     *      if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數 
                     *          // 換了頁就不印分隔線了 
                     *          deleteRow(table, 1); 
                     *          document.add(table); 
                     *          table = addHeader(caseData, false, earlyWarning); 
                     *      } 
                     *      else { 
                     *          deleteRow(table, 1); 
                     *          addLine(table); 
                     *      } 
                     * }
                     */

                } // ] ... end if (StringUtils.isNotBlank(caseData.getEvtDieDate()) && StringUtils.isNotBlank(caseData.getCloseCause()))
                  // ]
                  // 事故者死亡一次給付相關資料
                }
                // 事故者編審註記
                // [
                // 在塞標題前先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                }

                addColumn(table, 60, 1, "事故者編審註記：", fontCh12, 0, LEFT);
                // ---
                List<OldAgeReviewRpt01ChkfileDataCase> chkfileDataList = caseData.getChkfileDataList();
                if (chkfileDataList.size() > 0) {
                    // 取得事故者編審註記資料
                    String previousPayYm = "";
                    List<StringBuffer> chkfileStringList = new ArrayList<StringBuffer>();
                    StringBuffer chkfileString = new StringBuffer("");
                    for (OldAgeReviewRpt01ChkfileDataCase chkfileData : chkfileDataList) {
                        // 若 編審註記代碼 (CHKCODE) 值為 BB 或 LN, 不再事故者編審註記顯示改顯示於受款人編審註記中
                        if (StringUtils.equalsIgnoreCase(chkfileData.getChkCode(), "BB") || StringUtils.equalsIgnoreCase(chkfileData.getChkCode(), "LN"))
                            continue;

                        if (StringUtils.isBlank(previousPayYm) || !StringUtils.equals(chkfileData.getPayYm(), previousPayYm)) {
                            // 先將資料放到 ArrayList
                            if (StringUtils.isNotBlank(previousPayYm))
                                chkfileStringList.add(chkfileString);

                            previousPayYm = chkfileData.getPayYm();
                            chkfileString = new StringBuffer("");
                        }

                        chkfileString.append(((StringUtils.isBlank(chkfileString.toString())) ? (chkfileData.getPayYmString() + "－ " + chkfileData.getChkCode()) : (" " + chkfileData.getChkCode())));
                    }
                    // 如果 chkfileString 不為空白則必須於迴圈後將其加入 ArrayList, 否則資料會少一筆
                    if (StringUtils.isNotBlank(chkfileString.toString()))
                        chkfileStringList.add(chkfileString);

                    // 印出每筆給付年月的事故者編審註記資料
                    for (StringBuffer str : chkfileStringList) {
                        // 在塞事故者編審註記資料前, 先隨便塞空白行測試是否需換頁
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        addColumn(table, 60, 1, str.toString(), fontCh12, 0, LEFT);
                    }
                }

                // 編審註記說明
                List<OldAgeReviewRpt01ChkfileDescCase> chkfileDescList = caseData.getChkfileDescList();
                for (OldAgeReviewRpt01ChkfileDescCase chkfileDesc : chkfileDescList) {
                    // 在編審註記說明前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                    }

                    addColumn(table, 10, 1, "編審註記說明：", fontCh12, 0, LEFT);
                    addColumn(table, 50, 1, StringUtils.defaultString(chkfileDesc.getChkCode()) + " " + StringUtils.defaultString(chkfileDesc.getChkCodePost()) + " " + StringUtils.defaultString(chkfileDesc.getChkResult()), fontCh12, 0, LEFT);
                }

                // 塞入一行空白行
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }

                // 在處理編審結果前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                }

                // 編審結果判斷 Y 合格 N 不合格 空白 待處理
                addColumn(table, 16, 1, "電腦編審結果：" + ((StringUtils.equalsIgnoreCase(caseData.getAcceptMk(), "Y")) ? "合格" : ((StringUtils.equalsIgnoreCase(caseData.getAcceptMk(), "N")) ? "不合格" : "待處理")), fontCh12, 0, LEFT);
                addColumn(table, 16, 1, "人工審核結果：" + ((StringUtils.equalsIgnoreCase(caseData.getManchkMk(), "Y")) ? "合格" : ((StringUtils.equalsIgnoreCase(caseData.getManchkMk(), "N")) ? "不合格" : "待處理")), fontCh12, 0, LEFT);
                addColumn(table, 28, 1, "＊", fontCh12, 0, LEFT);

                // 在處理受理鍵入資料及修改紀錄前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                }

                addColumn(table, 35, 1, "受理鍵入資料及修改紀錄：（鍵入／更正人員代號：" + ((StringUtils.isNotBlank(caseData.getUpdUser())) ? caseData.getUpdUser() : StringUtils.defaultString(caseData.getCrtUser())) + "）", fontCh12, 0, LEFT);
                addColumn(table, 12, 1, ((StringUtils.isNotBlank(caseData.getChgNote())) ? ("更正原因：" + caseData.getChgNote()) : ""), fontCh12, 0, LEFT); // 更正原因 - 有值才顯示
                addColumn(table, 13, 1, ((StringUtils.isNotBlank(caseData.getMexcLvl())) ? ("決行層級：" + caseData.getMexcLvl()) : ""), fontCh12, 0, LEFT); // 決行層級 - 有值才顯示

                // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                    addLine(table);
                }
                // ]
                // 事故者編審註記

                // 核定通知書
                // [
                if (caseData.getNotifyData() != null) {
                    OldAgeReviewRpt01NotifyDataCase notifyData = caseData.getNotifyData();

                    // 受文者
                    StringBuffer receiveName = new StringBuffer("");
                    String sCommZip = "";
                    String sCommAddr = "";
                    
                    ArrayList<String> sNameList = new ArrayList<String>(); // 法 定代理人 list

                    for (int i = 0; i < caseData.getBenList().size(); i++) {
                        if (!caseData.getBenList().get(i).getGrdName().equals("")) {
                            if (sNameList.size() == 0) {
                                sNameList.add(caseData.getBenList().get(i).getGrdName());
                            }
                            else {
                                for (int j = 0; j < sNameList.size(); j++) {
                                    if (!sNameList.get(j).equals(caseData.getBenList().get(i).getGrdName()) && StringUtils.isNotBlank(caseData.getBenList().get(i).getBenName()) && !caseData.getBenList().get(i).getBenEvtRel().equals("F") && !caseData.getBenList().get(i).getBenEvtRel().equals("Z")) {
                                        sNameList.add(caseData.getBenList().get(i).getGrdName());
                                    }
                                }
                            }
                        }
                    }
                    
                    if (StringUtils.isNotBlank(caseData.getBenName()) && !caseData.getNotifyForm().substring(0, 1).equals("D"))
                        receiveName.append(caseData.getBenName());

                    if (caseData.getNotifyForm().substring(0, 1).equals("D")) {

                        String receiveBenName = "";
                        String receiveBenName1 = "";

                        for (int j = 0; j < sNameList.size(); j++) {
                            receiveBenName = "";
                            for (int i = 0; i < caseData.getBenList().size(); i++) {
                                if (StringUtils.isNotBlank(caseData.getBenList().get(i).getBenName()) && !caseData.getBenList().get(i).getBenEvtRel().equals("F") && !caseData.getBenList().get(i).getBenEvtRel().equals("Z")) {

                                    if (sNameList.get(j).equals(caseData.getBenList().get(i).getGrdName())) {
                                        if (i + 1 == caseData.getBenList().size()) {
                                            receiveBenName = receiveBenName + caseData.getBenList().get(i).getBenName();
                                        }
                                        else {
                                            receiveBenName = receiveBenName + caseData.getBenList().get(i).getBenName() + "、";
                                        }
                                    }

                                    if (sCommZip.equals("") && sCommAddr.equals("")) {
                                        sCommZip = caseData.getBenList().get(i).getCommZip();
                                        sCommAddr = caseData.getBenList().get(i).getCommAddr();
                                    }

                                }
                            }

                            int iLocation = receiveBenName.lastIndexOf("、");
                            if (receiveBenName.length() > 0 && iLocation == receiveBenName.length() - 1) {
                                if (iLocation > 0) {
                                    receiveBenName = receiveBenName.substring(0, iLocation);
                                }
                            }

                            // 受文者字串組合
                            if (j + 1 == sNameList.size()) {
                                receiveName.append(sNameList.get(j).toString() + "(" + receiveBenName + "之法定代理人)");
                            }
                            else {
                                receiveName.append(sNameList.get(j).toString() + "(" + receiveBenName + "之法定代理人)" + "、");
                            }

                        }
                        
                        for (int i = 0; i < caseData.getBenList().size(); i++) {
                            receiveBenName1 = "";
                            if (StringUtils.isNotBlank(caseData.getBenList().get(i).getBenName()) && !caseData.getBenList().get(i).getBenEvtRel().equals("F") && !caseData.getBenList().get(i).getBenEvtRel().equals("Z")) {

                                if (caseData.getBenList().get(i).getGrdName().trim() == "") {
                                    if (i + 1 == caseData.getBenList().size()) {
                                        receiveBenName1 = receiveBenName1 + caseData.getBenList().get(i).getBenName();
                                    }
                                    else {
                                        receiveBenName1 = receiveBenName1 + caseData.getBenList().get(i).getBenName() + "、";
                                    }
                                    
                                    if (sCommZip.equals("") && sCommAddr.equals("")) {
                                        sCommZip = caseData.getBenList().get(i).getCommZip();
                                        sCommAddr = caseData.getBenList().get(i).getCommAddr();
                                    }

                                    int iLocation = receiveBenName1.lastIndexOf("、");
                                    if (receiveBenName1.length() > 0 && iLocation == receiveBenName1.length() - 1) {
                                        if (iLocation > 0) {
                                            receiveBenName1 = receiveBenName1.substring(0, iLocation);
                                        }
                                    }
                                    
                                    if (StringUtils.isNotBlank(receiveName.toString()))
                                        receiveName.append("、");
                                    
                                    receiveName.append(receiveBenName1);
                                }
                            }
                        }

                    }
                    else {

                        for (OldAgeReviewRpt01BenDataCase benData : caseData.getBenList()) {
                            if (StringUtils.isNotBlank(benData.getBenName()) && !benData.getBenEvtRel().equals("F") && !benData.getBenEvtRel().equals("Z")) {

                                if (StringUtils.isNotBlank(receiveName.toString()))
                                    receiveName.append("、");

                                receiveName.append(benData.getBenName());

                                if (sCommZip.equals("") && sCommAddr.equals("")) {
                                    sCommZip = benData.getCommZip();
                                    sCommAddr = benData.getCommAddr();
                                }
                            }
                        }
                    }

                    // 先試印核定通知書的 標題 受文者 地址 主旨 及 說明的第一點, 測試是否需換頁
                    // [
                    addColumn(table, 30, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 14, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    // ---
                    addEmptyRow(table, 1);
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "受文者：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 54, 1, receiveName.toString(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "地址：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    if (!caseData.getNotifyForm().substring(0, 1).equals("D")) {
                        addColumnAssignVAlignmentAndLineSpace(table, 55, 1, (StringUtils.defaultString(caseData.getCommZip()) + " " + StringUtils.defaultString(caseData.getCommAddr())).trim(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    } else {
                        addColumnAssignVAlignmentAndLineSpace(table, 55, 1, (StringUtils.defaultString(sCommZip) + " " + StringUtils.defaultString(sCommAddr)).trim(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    }
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "主旨：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 54, 1, notifyData.getSubject(), fontCh12, 8, 0, JUSTIFIED, TOP); // 主旨
                    addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                    // ---
                    if (notifyData.getContent().size() > 0) {
                        addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "說明：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        addColumnAssignVAlignmentAndLineSpace(table, 54, 1, notifyData.getContent().get(0), fontCh12, 8, 0, JUSTIFIED, TOP); // 說明
                        addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                    }

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        if (notifyData.getContent().size() > 0)
                            deleteRow(table, 6);
                        else
                            deleteRow(table, 5);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        if (notifyData.getContent().size() > 0)
                            deleteRow(table, 6);
                        else
                            deleteRow(table, 5);
                    }
                    // ]

                    // 正式印核定通知書
                    addColumn(table, 30, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 14, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    // ---
                    addEmptyRow(table, 1);
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "受文者：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 54, 1, receiveName.toString(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "地址：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    if (!caseData.getNotifyForm().substring(0, 1).equals("D")) {
                        addColumnAssignVAlignmentAndLineSpace(table, 55, 1, (StringUtils.defaultString(caseData.getCommZip()) + " " + StringUtils.defaultString(caseData.getCommAddr())).trim(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    } else {
                        addColumnAssignVAlignmentAndLineSpace(table, 55, 1, (StringUtils.defaultString(sCommZip) + " " + StringUtils.defaultString(sCommAddr)).trim(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    }
                    // ---
                    addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "主旨：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 54, 1, notifyData.getSubject(), fontCh12, 8, 0, JUSTIFIED, TOP); // 主旨
                    addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                    // ---
                    // 說明 的處理
                    for (int nContentCount = 0; nContentCount < notifyData.getContent().size(); nContentCount++) {
                        String content = notifyData.getContent().get(nContentCount);

                        if (nContentCount == 0)
                            addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "說明：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        else
                            addColumnAssignVAlignmentAndLineSpace(table, 5, 1, " ", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        addColumnAssignVAlignmentAndLineSpace(table, 3, 1, content.substring(0, content.indexOf("、") + 1), fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        addColumnAssignVAlignmentAndLineSpace(table, 51, 1, content.substring(content.indexOf("、") + 1, content.length()), fontCh12, 8, 0, JUSTIFIED, TOP); // 說明
                        addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);

                            // 換了頁要再印一次
                            if (nContentCount == 0)
                                addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "說明：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                            else
                                addColumnAssignVAlignmentAndLineSpace(table, 5, 1, " ", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                            addColumnAssignVAlignmentAndLineSpace(table, 3, 1, content.substring(0, content.indexOf("、") + 1), fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                            addColumnAssignVAlignmentAndLineSpace(table, 51, 1, content.substring(content.indexOf("、") + 1, content.length()), fontCh12, 8, 0, JUSTIFIED, TOP); // 說明
                            addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                        }
                    }

                    // 印完核定通知書後強制換頁
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                // 20080218 新增無核定資料要印標題
                else {
                    // 先試印核定通知書的 標題測試是否需換頁
                    // [
                    addColumn(table, 30, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 14, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    // ---
                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                    }

                    // 正式印核定通知書
                    addColumn(table, 30, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 14, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    // 印完核定通知書後強制換頁
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                // ]
                // 核定通知書

                // ---------------------------------------------------------------------------------------------------

                // 請領同類給付資料
                // [
                OldAgeReviewSameKind sameKind = new OldAgeReviewSameKind();
                sameKind.execute(caseData, table, earlyWarning);
                
                if (false) {
                // 在塞請領同類給付資料表頭前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                }

                addColumn(table, 60, 1, "請領同類給付資料：", fontCh12, 0, LEFT);

                // 一次給付資料 (有資料再印)
                List<OldAgeReviewRpt01OncePayDataCase> oncePayList = caseData.getOncePayList();
                for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++) { // ... [
                    OldAgeReviewRpt01OncePayDataCase oncePayData = oncePayList.get(nOncePayCount);

                    // 印一次給付表頭
                    if (nOncePayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 一次給付 表頭
                        addColumn(table, 60, 1, "[一次給付]", fontCh12b, 0, LEFT);
                    }

                    // 一次給付資料一筆有六行 (如受理編號第 5 和 6 碼為 46 則會 4 行), 在塞資料前先測試是否需換頁
                    if (StringUtils.equals("46", StringUtils.substring(oncePayData.getBmApNo(), 4, 6)))
                        addEmptyRow(table, 6);
                    else
                        addEmptyRow(table, 4);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        if (StringUtils.equals("46", StringUtils.substring(oncePayData.getBmApNo(), 4, 6)))
                            deleteRow(table, 6);
                        else
                            deleteRow(table, 4);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        if (StringUtils.equals("46", StringUtils.substring(oncePayData.getBmApNo(), 4, 6)))
                            deleteRow(table, 6);
                        else
                            deleteRow(table, 4);
                    }

                    addColumn(table, 7, 1, "事故者姓名", fontCh12b, 0, LEFT);
                    addColumn(table, 7, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "事故日期", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "保險證號", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 7, 1, oncePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
                    addColumn(table, 7, 1, oncePayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
                    addColumn(table, 12, 1, oncePayData.getBmApNoString(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 7, 1, oncePayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期
                    addColumn(table, 7, 1, oncePayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號

                    if (oncePayData.getBmChkAmt() != null && oncePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 6, 1, formatBigDecimalToInteger(oncePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 6, 1, "0", fontCh12, 0, LEFT); // 核定金額

                    addColumn(table, 7, 1, oncePayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 7, 1, oncePayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
                    // ---
                    addColumn(table, 14, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "不給付日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "補收金額", fontCh12, 0, LEFT);
                    addColumn(table, 26, 1, "　", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 14, 1, oncePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(oncePayData.getBmNdocMk())) ? " / " + oncePayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 10, 1, oncePayData.getBmNopDateString(), fontCh12, 0, LEFT); // 不給付日期

                    if (oncePayData.getBmAdjAmts() != null && oncePayData.getBmAdjAmts().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 10, 1, formatBigDecimalToInteger(oncePayData.getBmAdjAmts()), fontCh12, 0, LEFT); // 補收金額
                    else
                        addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 補收金額

                    addColumn(table, 26, 1, "　", fontCh12, 0, LEFT);
                    // ---
                    if (StringUtils.equals("46", StringUtils.substring(oncePayData.getBmApNo(), 4, 6))) {
                        addColumn(table, 11, 1, "事業主管機關", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "申請代算單位", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "法令依據", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "複核日期", fontCh12, 0, LEFT);
                        addColumn(table, 22, 1, "　", fontCh12, 0, LEFT);
                        // ---
                        addColumn(table, 11, 1, oncePayData.getBmOldOrgDpt(), fontCh12, 0, LEFT); // 事業主管機關
                        addColumn(table, 11, 1, oncePayData.getBmOldAplDpt(), fontCh12, 0, LEFT); // 申請代算單位
                        addColumn(table, 8, 1, oncePayData.getBmOldLawNo(), fontCh12, 0, LEFT); // 法令依據
                        addColumn(table, 8, 1, oncePayData.getBmExmDteString(), fontCh12, 0, LEFT); // 複核日期
                        addColumn(table, 22, 1, "　", fontCh12, 0, LEFT);
                    }

                    // 最後一筆印完後空一行 (如果年金給付資料有資料再印)
                    if ((nOncePayCount == oncePayList.size() - 1) && (caseData.getAnnuityPayList().size() > 0)) {
                        // 空白行
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }
                } // ] ... end for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++)

                // 年金給付資料 (有資料再印)
                List<OldAgeReviewRpt01AnnuityPayDataCase> annuityPayList = caseData.getAnnuityPayList();
                for (int nAnnuityPayCount = 0; nAnnuityPayCount < annuityPayList.size(); nAnnuityPayCount++) { // ... [
                    OldAgeReviewRpt01AnnuityPayDataCase annuityPayData = annuityPayList.get(nAnnuityPayCount);

                    // 印年金給付表頭
                    if (nAnnuityPayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 年金給付 表頭
                        addColumn(table, 60, 1, "[年金給付]", fontCh12b, 0, LEFT);
                    }

                    // 年金給付資料一筆有四行, 在塞資料前先測試是否需換頁
                    addEmptyRow(table, 4);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 4);
                    }

                    addColumn(table, 8, 1, "事故者姓名", fontCh12b, 0, LEFT);
                    addColumn(table, 7, 1, "申請日期", fontCh12, 0, LEFT);
                    addColumn(table, 24, 1, "受理編號/核定年月/給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "事故日期", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "保險證號", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 8, 1, annuityPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
                    addColumn(table, 7, 1, annuityPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
                    addColumn(table, 24, 1, annuityPayData.getApNoString() + " / " + StringUtils.defaultString(annuityPayData.getIssuYmString()) + " / " + StringUtils.defaultString(annuityPayData.getPayYmString()), fontCh12, 0, LEFT); // 受理編號/核定年月/給付年月
                    addColumn(table, 7, 1, annuityPayData.getEvtJobDateString(), fontCh12, 0, LEFT); // 事故日期
                    addColumn(table, 7, 1, annuityPayData.getLsUbno(), fontCh12, 0, LEFT); // 保險證號

                    if (annuityPayData.getIssueAmt() != null && annuityPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 7, 1, formatBigDecimalToInteger(annuityPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 7, 1, "0", fontCh12, 0, LEFT); // 核定金額
                    // ---20130408 更改核定日期、核付日期、 補件日期/註記、 不給付日期 為 案件類別、處理狀態
                    addColumn(table, 8, 1, "案件類別", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "處理狀態", fontCh12, 0, LEFT);
                    // addColumn(table, 8, 1, "核定日期", fontCh12, 0, LEFT);
                    // addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
                    // addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    // addColumn(table, 8, 1, "不給付日期", fontCh12, 0, LEFT);
                    addColumn(table, 18, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
                    addColumn(table, 25, 1, "補收金額", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 8, 1, annuityPayData.getCaseTypString(), fontCh12, 0, LEFT); // 案件類別
                    addColumn(table, 9, 1, annuityPayData.getProcStatString(), fontCh12, 0, LEFT); // 處理狀態
                    // addColumn(table, 8, 1, annuityPayData.getChkDateString(), fontCh12, 0, LEFT); // 核定日期
                    // addColumn(table, 7, 1, annuityPayData.getAplpayDateString(), fontCh12, 0, LEFT); // 核付日期
                    // addColumn(table, 10, 1, annuityPayData.getProdateString() + ((StringUtils.isNotBlank(annuityPayData.getNdomk1())) ? ((StringUtils.isNotBlank(annuityPayData.getProdate())) ? (" / " + annuityPayData.getNdomk1()) : annuityPayData.getNdomk1()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    // addColumn(table, 8, 1, annuityPayData.getExeDateString(), fontCh12, 0, LEFT); // 不給付日期

                    addColumn(table, 18, 1, annuityPayData.getCloseDateString() + ((StringUtils.isNotBlank(annuityPayData.getCloseCause())) ? ((StringUtils.isNotBlank(annuityPayData.getCloseDate())) ? (" / " + annuityPayData.getCloseCause()) : "        / " + annuityPayData.getCloseCause()) : ""), fontCh12, 0, LEFT); // 結案日期/結案原因

                    BigDecimal tempAmt = (annuityPayData.getRecAmt() != null) ? annuityPayData.getRecAmt() : annuityPayData.getSupAmt();
                    if (tempAmt != null && tempAmt.compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 25, 1, formatBigDecimalToInteger(tempAmt), fontCh12, 0, LEFT); // 補收金額
                    else
                        addColumn(table, 25, 1, " ", fontCh12, 0, LEFT); // 補收金額
                } // ] ... end for (int nAnnuityPayCount = 0; nAnnuityPayCount < annuityPayList.size(); nAnnuityPayCount++)

                // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                    addLine(table);
                }
                
                }
                // ]
                // 請領同類給付資料

                // 請領他類給付資料
                // [
                OldAgeReviewOtherKind otherKind = new OldAgeReviewOtherKind();
                otherKind.execute(caseData, table, earlyWarning);
                
                if (false) {
                // 在塞請領他類給付資料表頭前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                }

                addColumn(table, 60, 1, "請領他類給付資料：", fontCh12, 0, LEFT);

                // 申請失能給付記錄資料 (有資料再印)
                List<OldAgeReviewRpt01DisablePayDataCase> disablePayList = caseData.getDisablePayList();
                for (int nDisablePayCount = 0; nDisablePayCount < disablePayList.size(); nDisablePayCount++) { // ... [
                    OldAgeReviewRpt01DisablePayDataCase disablePayData = disablePayList.get(nDisablePayCount);

                    // 印失能給付記錄表頭
                    if (nDisablePayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 失能給付記錄 表頭
                        addColumn(table, 60, 1, "申請失能給付記錄：", fontCh12b, 0, LEFT);
                    }

                    // 失能給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    addEmptyRow(table, 4);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 4);
                    }

                    addColumn(table, 11, 1, "申請（受理）日期", fontCh12b, 0, LEFT);
                    addColumn(table, 18, 1, "受理編號/給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "診斷失能日期", fontCh12, 0, LEFT);
                    addColumn(table, 14, 1, "失能項目", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 11, 1, disablePayData.getBmApDteString(), fontCh12, 0, LEFT); // 申請（受理）日期
                    addColumn(table, 18, 1, disablePayData.getBmApNoString() + ((StringUtils.isNotBlank(disablePayData.getBmPayYm())) ? ((StringUtils.isNotBlank(disablePayData.getBmApNo())) ? (" / " + disablePayData.getBmPayYmString()) : disablePayData.getBmPayYmString()) : ""), fontCh12, 0, LEFT); // 受理編號/給付年月
                    addColumn(table, 8, 1, disablePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
                    addColumn(table, 9, 1, disablePayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 診斷失能日期
                    addColumn(table, 14, 1, disablePayData.getBmCriInjDp(), fontCh12, 0, LEFT); // 失能項目
                    // ---
                    addColumn(table, 7, 1, "失能等級", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "不給付日期", fontCh12, 0, LEFT);
                    addColumn(table, 14, 1, "　", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 7, 1, disablePayData.getBmCriInjCl(), fontCh12, 0, LEFT); // 失能等級

                    if (disablePayData.getBmChkAmt() != null && disablePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 7, 1, formatBigDecimalToInteger(disablePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 核定金額

                    addColumn(table, 7, 1, disablePayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 7, 1, disablePayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 10, 1, disablePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(disablePayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(disablePayData.getBmNrepDateString())) ? (" / " + disablePayData.getBmNdocMk()) : disablePayData.getBmNdocMk()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 8, 1, disablePayData.getBmNopDateString(), fontCh12, 0, LEFT); // 不給付日期
                    addColumn(table, 14, 1, "　", fontCh12, 0, LEFT);

                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nDisablePayCount == disablePayList.size() - 1) && (caseData.getDisableAnnuityPayList().size() > 0 || caseData.getDiePayList().size() > 0 || caseData.getSurvivorAnnuityPayList().size() > 0 || caseData.getInjuryPayList().size() > 0 || caseData.getJoblessPayList().size() > 0 || caseData.getNpPayList().size() > 0)) {
                        // 空白行
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }
                } // ] ... end for (int nDisablePayCount = 0; nDisablePayCount < disablePayList.size(); nDisablePayCount++)

                // 申請失能年金記錄資料 (有資料再印)
                List<OldAgeReviewRpt01DisableAnnuityPayDataCase> disableAnnuityPayList = caseData.getDisableAnnuityPayList();
                for (int nDisableAnnuityPayCount = 0; nDisableAnnuityPayCount < disableAnnuityPayList.size(); nDisableAnnuityPayCount++) { // ... [
                    OldAgeReviewRpt01DisableAnnuityPayDataCase disableAnnuityPayData = disableAnnuityPayList.get(nDisableAnnuityPayCount);

                    // 印失能年金記錄表頭
                    if (nDisableAnnuityPayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 傷病給付記錄 表頭
                        addColumn(table, 60, 1, "申請失能年金記錄：", fontCh12b, 0, LEFT);
                    }

                    // 失能年金記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    addEmptyRow(table, 4);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 4);
                    }

                    addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "申請日期", fontCh12, 0, LEFT);
                    addColumn(table, 22, 1, "受理編號/核定年月/給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "診斷失能日期", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "失能等級", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);

                    // ---
                    addColumn(table, 8, 1, disableAnnuityPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
                    addColumn(table, 7, 1, DateUtility.formatChineseDateString(disableAnnuityPayData.getAppDate(), false), fontCh12, 0, LEFT); // 申請日期
                    addColumn(table, 22, 1, disableAnnuityPayData.getApNoString() + " / " + StringUtils.defaultString(disableAnnuityPayData.getIssuYmString()) + " / " + StringUtils.defaultString(disableAnnuityPayData.getPayYmString()), fontCh12, 0, LEFT); // 受理編號/核定年月/給付年月
                    addColumn(table, 9, 1, DateUtility.formatChineseDateString(disableAnnuityPayData.getEvtJobDate(), false), fontCh12, 0, LEFT); // 診斷失能日期
                    addColumn(table, 7, 1, disableAnnuityPayData.getCriInJcl(), fontCh12, 0, LEFT); // 失能等級

                    if (disableAnnuityPayData.getIssueAmt() != null && disableAnnuityPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 7, 1, formatBigDecimalToInteger(disableAnnuityPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 核定金額

                    // ---
                    addColumn(table, 8, 1, "案件類別", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "處理狀態", fontCh12, 0, LEFT);
                    addColumn(table, 18, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
                    addColumn(table, 25, 1, "失能項目", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 8, 1, disableAnnuityPayData.getCaseTypString(), fontCh12, 0, LEFT); // 案件類別
                    addColumn(table, 9, 1, disableAnnuityPayData.getProcStatString(), fontCh12, 0, LEFT); // 處理狀態
                    if (StringUtils.isNotBlank(disableAnnuityPayData.getCloseDate()) || StringUtils.isNotBlank(disableAnnuityPayData.getCloseCauseStr())) {
                        addColumn(table, 18, 1, DateUtility.formatChineseDateString(disableAnnuityPayData.getCloseDate(), false) + "/" + disableAnnuityPayData.getCloseCauseStr(), fontCh12, 0, LEFT); // 結案日期/結案原因
                    }
                    else {
                        addColumn(table, 18, 1, "　", fontCh12, 0, LEFT); // 結案日期/結案原因
                    }
                    addColumn(table, 25, 1, disableAnnuityPayData.getCriInJdp(), fontCh12, 0, LEFT);

                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nDisableAnnuityPayCount == disableAnnuityPayList.size() - 1) && (caseData.getDiePayList().size() > 0 || caseData.getSurvivorAnnuityPayList().size() > 0 || caseData.getInjuryPayList().size() > 0 || caseData.getJoblessPayList().size() > 0 || caseData.getNpPayList().size() > 0)) {
                        // 空白行
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }
                } // ] ... end for (int nDisableAnnuityPayCount = 0; nDisableAnnuityPayCount < disableAnnuityPayList.size(); nDisableAnnuityPayCount++)

                // 申請死亡給付記錄資料 (有資料再印)
                List<OldAgeReviewRpt01DiePayDataCase> diePayList = caseData.getDiePayList();
                for (int nDiePayCount = 0; nDiePayCount < diePayList.size(); nDiePayCount++) { // ... [
                    OldAgeReviewRpt01DiePayDataCase diePayData = diePayList.get(nDiePayCount);

                    // 印死亡給付記錄表頭
                    if (nDiePayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 死亡給付記錄 表頭
                        addColumn(table, 60, 1, "申請死亡給付記錄：", fontCh12b, 0, LEFT);
                    }

                    // 死亡給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    addEmptyRow(table, 4);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 4);
                    }

                    addColumn(table, 7, 1, "申請日期", fontCh12b, 0, LEFT);
                    addColumn(table, 18, 1, "受理編號/給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "死亡日期", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 7, 1, diePayData.getBmApDteString(), fontCh12, 0, LEFT); // 申請日期
                    addColumn(table, 18, 1, diePayData.getBmApNoString() + ((StringUtils.isNotBlank(diePayData.getBmPayYm())) ? ((StringUtils.isNotBlank(diePayData.getBmApNo())) ? (" / " + diePayData.getBmPayYmString()) : diePayData.getBmPayYmString()) : ""), fontCh12, 0, LEFT); // 受理編號/給付年月
                    addColumn(table, 8, 1, diePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
                    addColumn(table, 7, 1, diePayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 死亡日期

                    if (diePayData.getBmChkAmt() != null && diePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 6, 1, formatBigDecimalToInteger(diePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額

                    addColumn(table, 7, 1, diePayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 7, 1, diePayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
                    // ---
                    addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "不給付日期", fontCh12, 0, LEFT);
                    addColumn(table, 42, 1, "　", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 10, 1, diePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(diePayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(diePayData.getBmNrepDateString())) ? (" / " + diePayData.getBmNdocMk()) : diePayData.getBmNdocMk()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 8, 1, diePayData.getBmNopDateString(), fontCh12, 0, LEFT); // 不給付日期
                    addColumn(table, 42, 1, "　", fontCh12, 0, LEFT);

                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nDiePayCount == diePayList.size() - 1) && (caseData.getSurvivorAnnuityPayList().size() > 0 || caseData.getInjuryPayList().size() > 0 || caseData.getJoblessPayList().size() > 0 || caseData.getNpPayList().size() > 0)) {
                        // 空白行
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }
                } // ] ... end for (int nDiePayCount = 0; nDiePayCount < diePayList.size(); nDiePayCount++)

                // 申請遺屬年金記錄資料 (有資料再印)
                List<OldAgeReviewRpt01SurvivorAnnuityPayDataCase> survivorAnnuityPayList = caseData.getSurvivorAnnuityPayList();
                for (int nSurvivorAnnuityPayCount = 0; nSurvivorAnnuityPayCount < survivorAnnuityPayList.size(); nSurvivorAnnuityPayCount++) { // ... [
                    OldAgeReviewRpt01SurvivorAnnuityPayDataCase survivorAnnuityPayData = survivorAnnuityPayList.get(nSurvivorAnnuityPayCount);

                    // 印申請遺屬年金記錄表頭
                    if (nSurvivorAnnuityPayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 申請遺屬年金記錄 表頭
                        addColumn(table, 60, 1, "申請遺屬年金記錄：", fontCh12b, 0, LEFT);
                    }

                    // 遺屬年金記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    addEmptyRow(table, 4);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 4);
                    }

                    addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "申請日期", fontCh12, 0, LEFT);
                    addColumn(table, 22, 1, "受理編號/核定年月/給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "死亡日期", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "案件類別", fontCh12, 0, LEFT);

                    // ---
                    addColumn(table, 8, 1, survivorAnnuityPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
                    addColumn(table, 7, 1, DateUtility.formatChineseDateString(survivorAnnuityPayData.getAppDate(), false), fontCh12, 0, LEFT); // 申請日期
                    addColumn(table, 22, 1, survivorAnnuityPayData.getApNoString() + " / " + StringUtils.defaultString(survivorAnnuityPayData.getIssuYmString()) + " / " + StringUtils.defaultString(survivorAnnuityPayData.getPayYmString()), fontCh12, 0, LEFT); // 受理編號/核定年月/給付年月
                    addColumn(table, 7, 1, DateUtility.formatChineseDateString(survivorAnnuityPayData.getEvtJobDate(), false), fontCh12, 0, LEFT); // 死亡日期

                    if (survivorAnnuityPayData.getIssueAmt() != null && survivorAnnuityPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 7, 1, formatBigDecimalToInteger(survivorAnnuityPayData.getIssueAmt()), fontCh12, 0, CENTER); // 核定金額
                    else
                        addColumn(table, 7, 1, " ", fontCh12, 0, CENTER); // 核定金額

                    addColumn(table, 9, 1, survivorAnnuityPayData.getCaseTypString(), fontCh12, 0, LEFT); // 案件類別
                    // ---
                    addColumn(table, 8, 1, "處理狀態", fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
                    addColumn(table, 32, 1, "　", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 8, 1, survivorAnnuityPayData.getProcStatString(), fontCh12, 0, LEFT); // 處理狀態
                    if (StringUtils.isNotBlank(survivorAnnuityPayData.getCloseDate()) || StringUtils.isNotBlank(survivorAnnuityPayData.getCloseCauseStr())) {
                        addColumn(table, 20, 1, DateUtility.formatChineseDateString(survivorAnnuityPayData.getCloseDate(), false) + "/" + survivorAnnuityPayData.getCloseCauseStr(), fontCh12, 0, LEFT); // 結案日期/結案原因
                    }
                    else {
                        addColumn(table, 20, 1, "　", fontCh12, 0, LEFT); // 結案日期/結案原因
                    }
                    addColumn(table, 32, 1, "　", fontCh12, 0, LEFT);

                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nSurvivorAnnuityPayCount == survivorAnnuityPayList.size() - 1) && (caseData.getInjuryPayList().size() > 0 || caseData.getJoblessPayList().size() > 0 || caseData.getNpPayList().size() > 0)) {
                        // 空白行
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }
                } // ] ... end for (int nSurvivorAnnuityPayCount = 0; nSurvivorAnnuityPayCount < survivorAnnuityPayList.size(); nSurvivorAnnuityPayCount++)

                // 申請傷病給付記錄資料 (有資料再印)
                List<OldAgeReviewRpt01InjuryPayDataCase> injuryPayList = caseData.getInjuryPayList();
                for (int nInjuryPayCount = 0; nInjuryPayCount < injuryPayList.size(); nInjuryPayCount++) { // ... [
                    OldAgeReviewRpt01InjuryPayDataCase injuryPayData = injuryPayList.get(nInjuryPayCount);

                    // 印傷病給付記錄表頭
                    if (nInjuryPayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 傷病給付記錄 表頭
                        addColumn(table, 60, 1, "申請傷病給付記錄：", fontCh12b, 0, LEFT);
                    }

                    // 傷病給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    addEmptyRow(table, 4);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 4);
                    }

                    addColumn(table, 7, 1, "受理日期", fontCh12b, 0, LEFT);
                    addColumn(table, 12, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "事故日期", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定起日-", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "迄日", fontCh12, 0, CENTER);
                    addColumn(table, 6, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 7, 1, injuryPayData.getBmApDteString(), fontCh12b, 0, LEFT); // 受理日期
                    addColumn(table, 12, 1, injuryPayData.getBmApNoString(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 7, 1, injuryPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
                    addColumn(table, 7, 1, injuryPayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期
                    addColumn(table, 7, 1, injuryPayData.getBmLosFmDteString() + ((StringUtils.isNotBlank(injuryPayData.getBmLosToDte())) ? "-" : ""), fontCh12, 0, RIGHT); // 核定起日-
                    addColumn(table, 7, 1, injuryPayData.getBmLosToDteString(), fontCh12, 0, CENTER); // 迄日

                    if (injuryPayData.getBmChkAmt() != null && injuryPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 6, 1, formatBigDecimalToInteger(injuryPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額

                    addColumn(table, 7, 1, injuryPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
                    // ---
                    addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "不給付日期", fontCh12, 0, LEFT);
                    addColumn(table, 35, 1, "　", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 7, 1, injuryPayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 10, 1, injuryPayData.getBmNrepDateString() + ((StringUtils.isNotBlank(injuryPayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNrepDateString())) ? (" / " + injuryPayData.getBmNdocMk()) : injuryPayData.getBmNdocMk()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 8, 1, injuryPayData.getBmNopDateString(), fontCh12, 0, LEFT); // 不給付日期
                    addColumn(table, 35, 1, "　", fontCh12, 0, LEFT);

                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nInjuryPayCount == injuryPayList.size() - 1) && (caseData.getJoblessPayList().size() > 0 || caseData.getNpPayList().size() > 0)) {
                        // 空白行
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }
                } // ] ... end for (int nInjuryPayCount = 0; nInjuryPayCount < injuryPayList.size(); nInjuryPayCount++)

                // 申請失業給付記錄資料 (有資料再印)
                List<OldAgeReviewRpt01JoblessPayDataCase> joblessPayList = caseData.getJoblessPayList();
                for (int nJoblessPayCount = 0; nJoblessPayCount < joblessPayList.size(); nJoblessPayCount++) { // ... [
                    OldAgeReviewRpt01JoblessPayDataCase joblessPayData = joblessPayList.get(nJoblessPayCount);

                    // 印失業給付記錄表頭
                    if (nJoblessPayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 失業給付記錄 表頭
                        addColumn(table, 60, 1, "申請失業給付記錄：", fontCh12b, 0, LEFT);
                    }

                    // 失業給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    addEmptyRow(table, 4);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 4);
                    }

                    addColumn(table, 7, 1, "受理日期", fontCh12b, 0, LEFT);
                    addColumn(table, 12, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "求職日期", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "給付起日-", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "給付迄日", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "核定金額", fontCh12, 0, RIGHT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 7, 1, joblessPayData.getApDteString(), fontCh12b, 0, LEFT); // 受理日期
                    addColumn(table, 12, 1, joblessPayData.getApNoString(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 7, 1, joblessPayData.getName(), fontCh12, 0, LEFT); // 事故者姓名
                    addColumn(table, 7, 1, joblessPayData.getAprDteString(), fontCh12, 0, LEFT); // 求職日期
                    addColumn(table, 7, 1, joblessPayData.getSymDteString() + ((StringUtils.isNotBlank(joblessPayData.getTymDte())) ? "-" : ""), fontCh12, 0, RIGHT); // 給付起日-
                    addColumn(table, 6, 1, joblessPayData.getTymDteString(), fontCh12, 0, LEFT); // 給付迄日

                    if (joblessPayData.getChkAmt() != null && joblessPayData.getChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 6, 1, formatBigDecimalToInteger(joblessPayData.getChkAmt()), fontCh12, 0, RIGHT); // 核定金額
                    else
                        addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // 核定金額
                    addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, joblessPayData.getChkDteString(), fontCh12, 0, LEFT); // 核定日期
                    // ---
                    addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "補件日期", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "不給付日期", fontCh12, 0, LEFT);
                    addColumn(table, 38, 1, "　", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 7, 1, joblessPayData.getPayDteString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 7, 1, joblessPayData.getSavDt1String(), fontCh12, 0, LEFT); // 補件日期
                    addColumn(table, 8, 1, joblessPayData.getNpyDteString(), fontCh12, 0, LEFT); // 不給付日期
                    addColumn(table, 38, 1, "　", fontCh12, 0, LEFT);

                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nJoblessPayCount == joblessPayList.size() - 1) && (caseData.getNpPayList().size() > 0)) {
                        // 空白行
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }
                } // ] ... end for (int nJoblessPayCount = 0; nJoblessPayCount < joblessPayList.size(); nJoblessPayCount++)

                // 申請國保給付記錄資料 (有資料再印)
                List<OldAgeReviewRpt01NpPayDataCase> npPayList = caseData.getNpPayList();
                for (int nNpPayCount = 0; nNpPayCount < npPayList.size(); nNpPayCount++) { // ... [
                    OldAgeReviewRpt01NpPayDataCase npPayData = npPayList.get(nNpPayCount);

                    // 印國保給付記錄表頭
                    if (nNpPayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 國保給付記錄 表頭
                        addColumn(table, 60, 1, "申請國保給付記錄：", fontCh12b, 0, LEFT);

                    }

                    // 國保給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    addEmptyRow(table, 4);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 4);
                    }

                    addColumn(table, 7, 1, "申請日期", fontCh12b, 0, LEFT);
                    addColumn(table, 15, 1, "受理編號/給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "被保險人姓名", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "事故日期", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 7, 1, npPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
                    addColumn(table, 15, 1, npPayData.getApNo() + ((StringUtils.isNotBlank(npPayData.getPayYm())) ? ((StringUtils.isNotBlank(npPayData.getApNo())) ? (" / " + npPayData.getPayYmString()) : npPayData.getPayYmString()) : ""), fontCh12, 0, LEFT); // 受理編號/給付年月
                    addColumn(table, 11, 1, npPayData.getEvteeName(), fontCh12, 0, LEFT); // 被保險人姓名
                    addColumn(table, 7, 1, npPayData.getEvtDtString(), fontCh12, 0, LEFT); // 事故日期

                    if (npPayData.getIssueAmt() != null && npPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 6, 1, formatBigDecimalToInteger(npPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額

                    addColumn(table, 7, 1, npPayData.getChkDtString(), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 7, 1, npPayData.getAplPayDateString(), fontCh12, 0, LEFT); // 核付日期
                    // ---
                    addColumn(table, 7, 1, "合格註記", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "人工審核結果", fontCh12, 0, LEFT);
                    addColumn(table, 43, 1, "　", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 7, 1, npPayData.getAcceptMark(), fontCh12, 0, LEFT); // 合格註記
                    addColumn(table, 10, 1, npPayData.getManChkFlg(), fontCh12, 0, LEFT); // 人工審核結果
                    addColumn(table, 43, 1, "　", fontCh12, 0, LEFT);

                } // ] ... end for (int nNpPayCount = 0; nNpPayCount < npPayList.size(); nNpPayCount++)

                // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                    addLine(table);
                }
                
                }
                // ]

                // ---------------------------------------------------------------------------------------------------
                // 本次紓困貸款資料
                // [
                OldAgeReviewRpt01LoanDataCase loanData = caseData.getLoanData();
                if (loanData != null && loanData.getLoanAmt() != null && loanData.getLoanAmt().compareTo(new BigDecimal(0)) != 0) {
                    // 先塞三行空白 兩行標題一行資料 測試是否換頁
                    addEmptyRow(table, 3);
                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 3);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 3);
                    }

                    addColumn(table, 60, 1, "本次紓困貸款資料:", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 9, 1, "勞貸貸款金額", fontCh12, 0, RIGHT);
                    addColumn(table, 10, 1, "本金餘額", fontCh12, 0, RIGHT);
                    addColumn(table, 10, 1, "利息", fontCh12, 0, RIGHT);
                    addColumn(table, 10, 1, "本息截止日", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "呆帳金額", fontCh12, 0, RIGHT);
                    addColumn(table, 10, 1, "扣減金額", fontCh12, 0, RIGHT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);
                    // ---
                    if (loanData.getLoanAmt() != null && loanData.getLoanAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 9, 1, formatBigDecimalToInteger(loanData.getLoanAmt()), fontCh12, 0, RIGHT); // 勞貸貸款金額
                    else
                        addColumn(table, 9, 1, "", fontCh12, 0, RIGHT); // 勞貸貸款金額

                    if (loanData.getRecapAmt() != null && loanData.getRecapAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 10, 1, formatBigDecimalToInteger(loanData.getRecapAmt()), fontCh12, 0, RIGHT); // 本金餘額
                    else
                        addColumn(table, 10, 1, "", fontCh12, 0, RIGHT); // 本金餘額

                    if (loanData.getLoaniTrt() != null && loanData.getLoaniTrt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 10, 1, formatBigDecimalToInteger(loanData.getLoaniTrt()), fontCh12, 0, RIGHT); // 利息
                    else
                        addColumn(table, 10, 1, "", fontCh12, 0, RIGHT); // 利息

                    addColumn(table, 10, 1, loanData.getDlineDateString(), fontCh12, 0, LEFT); // 本息截止日

                    if (loanData.getBadDebtAmt() != null && loanData.getBadDebtAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 10, 1, formatBigDecimalToInteger(loanData.getBadDebtAmt()), fontCh12, 0, RIGHT); // 呆帳金額
                    else
                        addColumn(table, 10, 1, "", fontCh12, 0, RIGHT); // 呆帳金額

                    if (loanData.getOffsetAmt() != null && loanData.getOffsetAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 10, 1, formatBigDecimalToInteger(loanData.getOffsetAmt()), fontCh12, 0, RIGHT); // 扣減金額
                    else
                        addColumn(table, 10, 1, "", fontCh12, 0, RIGHT); // 扣減金額
                    addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);

                    // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 1);
                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不印分隔線了
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                        addLine(table);
                    }
                }

                // ]
                // ---------------------------------------------------------------------------------------------------
                // 另按扣減資料
                // [
                List<OldAgeReviewRpt01DeductOnceDataCase> deductOnceList = caseData.getDeductOnceList();
                List<OldAgeReviewRpt01DeductAnnuityDataCase> deductAnnuityList = caseData.getDeductAnnuityList();
                if (deductOnceList.size() != 0 || deductAnnuityList.size() != 0) {
                    // 先塞四行空白 三行標題一行資料 測試是否換頁
                    addEmptyRow(table, 4);
                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 4);
                    }

                    addColumn(table, 60, 1, "另案扣減資料：", fontCh12, 0, LEFT);
                    // ---
                    if (deductOnceList.size() > 0) {
                        addColumn(table, 60, 1, "【一次給付】", fontCh12, 0, LEFT);
                        // ---
                        addColumn(table, 5, 1, "序", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "立帳對象姓名", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "應收未收餘額", fontCh12, 0, RIGHT);
                        addColumn(table, 10, 1, "處理狀況", fontCh12, 0, LEFT);
                        addColumn(table, 15, 1, "年金受理編號", fontCh12, 0, LEFT);
                        // ---
                        // 塞資料
                        for (OldAgeReviewRpt01DeductOnceDataCase deductOnceData : deductOnceList) {
                            addEmptyRow(table, 1);
                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                            else {
                                deleteRow(table, 1);
                            }

                            addColumn(table, 5, 1, (deductOnceData.getRowNum() != null) ? deductOnceData.getRowNum().toPlainString() : "", fontCh12, 0, LEFT); // 序
                            addColumn(table, 10, 1, StringUtils.defaultString(deductOnceData.getRxfName()), fontCh12, 0, LEFT); // 立帳對象姓名
                            addColumn(table, 10, 1, StringUtils.defaultString(deductOnceData.getRxfApNo()), fontCh12, 0, LEFT); // 受理編號
                            if (deductOnceData.getSubAmt() != null && deductOnceData.getSubAmt().compareTo(new BigDecimal(0)) != 0)
                                addColumn(table, 10, 1, formatBigDecimalToInteger(deductOnceData.getSubAmt()), fontCh12, 0, RIGHT); // 應收未收餘額
                            else
                                addColumn(table, 10, 1, "", fontCh12, 0, RIGHT); // 應收未收餘額

                            addColumn(table, 10, 1, StringUtils.defaultString(deductOnceData.getPrSt()), fontCh12, 0, LEFT); // 處理狀況
                            addColumn(table, 15, 1, StringUtils.defaultString(deductOnceData.getApNo()), fontCh12, 0, LEFT); // 年金受理編號
                        }
                    }

                    if (deductAnnuityList.size() > 0) {
                        addEmptyRow(table, 3);
                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 3);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 3);
                        }

                        addColumn(table, 60, 1, "【年金給付】", fontCh12, 0, LEFT);
                        // ---
                        addColumn(table, 5, 1, "序", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "立帳對象姓名", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "核定年月", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "給付年月", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "應收總額", fontCh12, 0, RIGHT);
                        addColumn(table, 10, 1, "未收餘額", fontCh12, 0, RIGHT);
                        addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);
                        // ---
                        // 塞資料
                        for (OldAgeReviewRpt01DeductAnnuityDataCase deductAnnuityData : deductAnnuityList) {
                            addEmptyRow(table, 1);
                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                            else {
                                deleteRow(table, 1);
                            }

                            addColumn(table, 5, 1, (deductAnnuityData.getRowNum() != null) ? deductAnnuityData.getRowNum().toPlainString() : "", fontCh12, 0, LEFT); // 序
                            addColumn(table, 10, 1, StringUtils.defaultString(deductAnnuityData.getBenName()), fontCh12, 0, LEFT); // 立帳對象姓名
                            addColumn(table, 10, 1, StringUtils.defaultString(deductAnnuityData.getApNo()), fontCh12, 0, LEFT); // 受理編號
                            addColumn(table, 7, 1, deductAnnuityData.getIssuYmString(), fontCh12, 0, LEFT); // 核定年月
                            addColumn(table, 7, 1, deductAnnuityData.getPayYmString(), fontCh12, 0, LEFT); // 給付年月

                            if (deductAnnuityData.getRecAmt() != null && deductAnnuityData.getRecAmt().compareTo(new BigDecimal(0)) != 0)
                                addColumn(table, 10, 1, formatBigDecimalToInteger(deductAnnuityData.getRecAmt()), fontCh12, 0, RIGHT); // 應收總額
                            else
                                addColumn(table, 10, 1, "", fontCh12, 0, RIGHT); // 應收總額

                            if (deductAnnuityData.getRecRem() != null && deductAnnuityData.getRecRem().compareTo(new BigDecimal(0)) != 0)
                                addColumn(table, 10, 1, formatBigDecimalToInteger(deductAnnuityData.getRecRem()), fontCh12, 0, RIGHT); // 未收餘額
                            else
                                addColumn(table, 10, 1, "", fontCh12, 0, RIGHT); // 未收餘額

                            addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);
                        }
                    }

                    // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 1);
                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不印分隔線了
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                        addLine(table);
                    }
                }

                // ]

                // ---------------------------------------------------------------------------------------------------
                // 受款人資料
                // [
                // 在塞受款人人數前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                }

                addColumn(table, 5, 1, "受款人", fontCh12l, 0, JUSTIFIEDALL); // 有底線
                addColumn(table, 20, 1, "資料：", fontCh12, 0, LEFT);
                addColumn(table, 35, 1, "受款人數： " + (caseData.getBenList().size() + 1), fontCh12, 0, LEFT); // 因有含事故者本人, 故須加 1
                // ---
                // 空白行
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }

                // ---
                // 事故者於受款人資料的資料
                // [
                // 為求每位受款人的資料能在同一頁, 故先計算每位受款人之資料行數
                // 並預先塞入空白行, 測試是否需換頁
                // 行數 = 固定行數 6 行 + 法定代理人 1 行 (若有值) + 給付資料 n 行 + 給付資料標題 1 行 (若給付資料 > 0 筆)
                List<OldAgeReviewRpt01BenPayDataCase> evtBenPayList = caseData.getEvtBenPayList();

                int nEvtBenPayLine = 6 + evtBenPayList.size();

                // 給付資料標題 1 行 (若給付資料 > 0 筆)
                if (evtBenPayList.size() > 0)
                    nEvtBenPayLine++;

                // 法定代理人 1 行 (若有值)
                if (StringUtils.isNotBlank(caseData.getGrdName()) || StringUtils.isNotBlank(caseData.getGrdIdnNo()) || StringUtils.isNotBlank(caseData.getGrdBrDate()))
                    nEvtBenPayLine++;

                // 法定代理人多一行變 2 行
                if (StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "A") || StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "F") || StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "Z") || StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "N"))
                    nEvtBenPayLine++;

                // 塞事故者於受款人資料的資料前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, nEvtBenPayLine);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, nEvtBenPayLine);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, nEvtBenPayLine);
                }

                addColumn(table, 40, 1, "受款人序：01", fontCh12, 0, LEFT); // 事故者本人固定在第一筆
                addColumn(table, 20, 1, " ", fontCh12, 0, LEFT); // 發給：
                // ---
                addColumn(table, 60, 1, "姓名：" + StringUtils.defaultString(caseData.getBenName()), fontCh12, 0, LEFT);
                // ---
                addColumn(table, 16, 1, "身分證號：" + StringUtils.defaultString(caseData.getBenIdnNo()), fontCh12, 0, LEFT);
                addColumn(table, 16, 1, "出生日期：" + StringUtils.defaultString(caseData.getBenBrDateString()), fontCh12, 0, LEFT);
                addColumn(table, 28, 1, "電話：" + StringUtils.defaultString(caseData.getTel1()) + ((StringUtils.isNotBlank(caseData.getTel2()) && StringUtils.isNotBlank(caseData.getTel1())) ? ("、" + StringUtils.defaultString(caseData.getTel2())) : StringUtils.defaultString(caseData.getTel2())), fontCh12, 0, LEFT);
                // ---
                // 帳號處理
                String strEvtAccountNo = StringUtils.defaultString(caseData.getPayBankId());
                if (StringUtils.isNotBlank(caseData.getBranchId()))
                    if (caseData.getPayTyp().equals("1") && !caseData.getPayBankId().equals("700")) {
                        strEvtAccountNo = strEvtAccountNo + "-" + "0000";
                    }
                    else {
                        strEvtAccountNo = strEvtAccountNo + "-" + caseData.getBranchId();
                    }
                if (StringUtils.isNotBlank(caseData.getPayEeacc()))
                    strEvtAccountNo = strEvtAccountNo + "-" + caseData.getPayEeacc();

                addColumn(table, 7, 1, "關係：" + StringUtils.defaultString(caseData.getBenEvtRel()), fontCh12, 0, LEFT);
                addColumn(table, 9, 1, "給付方式：" + StringUtils.defaultString(caseData.getPayTyp()), fontCh12, 0, LEFT);
                addColumn(table, 25, 1, "帳號：" + strEvtAccountNo + " " + StringUtils.defaultString(caseData.getBankName()), fontCh12, 0, LEFT);
                addColumn(table, 13, 1, "戶名：" + StringUtils.defaultString(caseData.getAccName()), fontCh12, 0, LEFT);
                if (StringUtils.isBlank(caseData.getSpecialAcc())) {
                    addColumn(table, 6, 1, "", fontCh12, 0, LEFT);
                }
                else {
                    addColumn(table, 6, 1, "(專戶)", fontCh12, 0, LEFT);
                }
                // ---
                addColumn(table, 10, 1, "國籍別：" + StringUtils.defaultString(caseData.getBenNationTyp()), fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "性別：" + StringUtils.defaultString(caseData.getBenSex()), fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "國籍：" + StringUtils.defaultString(caseData.getBenNationName()), fontCh12, 0, LEFT);
                addColumn(table, 30, 1, "身分查核年月：" + caseData.getIdnChkYmString(), fontCh12, 0, LEFT);
                // ---
                if (evtBenPayList.size() > 0) {
                    addColumn(table, 8, 1, "給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "可領金額", fontCh12, 0, RIGHT);
                    addColumn(table, 8, 1, "扣減金額", fontCh12, 0, RIGHT);
                    addColumn(table, 8, 1, "紓困金額", fontCh12, 0, RIGHT);
                    addColumn(table, 8, 1, "匯款匯費", fontCh12, 0, RIGHT);
                    addColumn(table, 8, 1, "實付金額", fontCh12, 0, RIGHT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "編審註記", fontCh12, 0, LEFT);
                }
                // ---
                for (OldAgeReviewRpt01BenPayDataCase evtBenPayData : evtBenPayList) {
                    addColumn(table, 8, 1, evtBenPayData.getPayYmString(), fontCh12, 0, LEFT); // 給付年月

                    if (evtBenPayData.getIssueAmt() != null && evtBenPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 8, 1, formatBigDecimalToInteger(evtBenPayData.getIssueAmt()), fontCh12, 0, RIGHT); // 可領金額
                    else
                        addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 可領金額

                    if (evtBenPayData.getOtherAmt() != null && evtBenPayData.getOtherAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 8, 1, formatBigDecimalToInteger(evtBenPayData.getOtherAmt()), fontCh12, 0, RIGHT); // 扣減金額
                    else
                        addColumn(table, 8, 1, " ", fontCh12, 0, RIGHT); // 扣減金額

                    // 20100809 先不秀紓困金額
                    // if (evtBenPayData.getOffsetAmt() != null && evtBenPayData.getOffsetAmt().compareTo(new BigDecimal(0)) != 0)
                    // addColumn(table, 8, 1, formatBigDecimalToInteger(evtBenPayData.getOffsetAmt()), fontCh12, 0, RIGHT); // 紓困金額
                    // else
                    addColumn(table, 8, 1, " ", fontCh12, 0, RIGHT); // 紓困金額

                    if (evtBenPayData.getPayRate() != null && evtBenPayData.getPayRate().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 8, 1, formatBigDecimalToInteger(evtBenPayData.getPayRate()), fontCh12, 0, RIGHT); // 匯款匯費
                    else
                        addColumn(table, 8, 1, " ", fontCh12, 0, RIGHT); // 匯款匯費

                    if (evtBenPayData.getAplpayAmt() != null && evtBenPayData.getAplpayAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 8, 1, formatBigDecimalToInteger(evtBenPayData.getAplpayAmt()), fontCh12, 0, RIGHT); // 實付金額
                    else
                        addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 實付金額

                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, caseData.getChkfileDataBy(evtBenPayData.getPayYm()), fontCh12, 0, LEFT); // 編審註記
                }
                // ---
                addColumn(table, 60, 1, "地址：" + StringUtils.defaultString(caseData.getCommZip()) + " " + StringUtils.defaultString(caseData.getCommAddr()), fontCh12, 0, LEFT);
                // ---
                if (StringUtils.isNotBlank(caseData.getGrdName()) || StringUtils.isNotBlank(caseData.getGrdIdnNo()) || StringUtils.isNotBlank(caseData.getGrdBrDate())) {
                    addColumn(table, 16, 1, "法定代理人姓名：" + StringUtils.defaultString(caseData.getGrdName()), fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, "身分證號：" + StringUtils.defaultString(caseData.getGrdIdnNo()), fontCh12, 0, LEFT);
                    addColumn(table, 28, 1, "出生日期：" + StringUtils.defaultString(caseData.getGrdBrDateString()), fontCh12, 0, LEFT);
                }
                // ---
                BigDecimal unitpayAmt = null;
                BigDecimal returnAmt = null;
                BigDecimal remainAmt = null;
                BigDecimal loanAmt = null;
                BigDecimal compenAmt = null;
                BigDecimal lecomAmt = null;
                BigDecimal recomAmt = null;
                BigDecimal badDebtAmt = null;

                if (evtBenPayList.size() > 0) {
                    OldAgeReviewRpt01BenPayDataCase evtBenPayData = evtBenPayList.get(evtBenPayList.size() - 1);
                    unitpayAmt = evtBenPayData.getUnitpayAmt();
                    returnAmt = evtBenPayData.getReturnAmt();
                    remainAmt = evtBenPayData.getRemainAmt();
                    loanAmt = evtBenPayData.getLoanAmt();
                    lecomAmt = evtBenPayData.getLecomAmt();
                    recomAmt = evtBenPayData.getRecomAmt();
                    badDebtAmt = evtBenPayData.getBadDebtAmt();
                }

                if (StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "A")) {
                    addColumn(table, 20, 1, "墊付金額：" + (StringUtils.equals(formatBigDecimalToInteger(unitpayAmt), "0") ? "" : formatBigDecimalToInteger(unitpayAmt)), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "已歸墊金額：" + (StringUtils.equals(formatBigDecimalToInteger(returnAmt), "0") ? "" : formatBigDecimalToInteger(returnAmt)), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "未歸墊金額：" + (StringUtils.equals(formatBigDecimalToInteger(remainAmt), "0") ? "" : formatBigDecimalToInteger(remainAmt)), fontCh12, 0, LEFT);
                }
                else if (StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "F")) {
                    addColumn(table, 60, 1, "紓困貸款金額：" + (StringUtils.equals(formatBigDecimalToInteger(loanAmt), "0") ? "" : formatBigDecimalToInteger(loanAmt)), fontCh12, 0, LEFT);
                }
                else if (StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "Z")) {
                    addColumn(table, 20, 1, "實際補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(caseData.getCutAmt()), "0") ? "" : formatBigDecimalToInteger(compenAmt)), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "已代扣補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(lecomAmt), "0") ? "" : formatBigDecimalToInteger(lecomAmt)), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "未扣補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(recomAmt), "0") ? "" : formatBigDecimalToInteger(recomAmt)), fontCh12, 0, LEFT);
                }
                else if (StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "N")) {
                    addColumn(table, 60, 1, "紓困呆帳金額：" + formatBigDecimalToInteger(badDebtAmt), fontCh12, 0, LEFT);
                }
                // ]
                // 事故者於受款人資料的資料

                List<OldAgeReviewRpt01BenDataCase> benList = caseData.getBenList();

                for (int nBenList = 0; nBenList < benList.size(); nBenList++) {
                    OldAgeReviewRpt01BenDataCase benData = benList.get(nBenList);

                    // 空白行 (每個受款人間留一行空白)
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不再塞空白行了
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }

                    // 為求每位受款人的資料能在同一頁, 故先計算每位受款人之資料行數
                    // 並預先塞入空白行, 測試是否需換頁
                    // 行數 = 固定行數 6 行 + 法定代理人 1 行 (若有值) + 給付資料 n 行 + 給付資料標題 1 行 (若給付資料 > 0 筆)
                    List<OldAgeReviewRpt01BenPayDataCase> benPayList = benData.getBenPayList();

                    int nBenPayLine = 6 + benPayList.size();

                    // 給付資料標題 1 行 (若給付資料 > 0 筆)
                    if (benPayList.size() > 0)
                        nBenPayLine++;

                    // 法定代理人 1 行 (若有值)
                    if (StringUtils.isNotBlank(benData.getGrdName()) || StringUtils.isNotBlank(benData.getGrdIdnNo()) || StringUtils.isNotBlank(benData.getGrdBrDate()))
                        nBenPayLine++;

                    // 法定代理人多一行變 2 行
                    if (StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "A") || StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "F") || StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "Z") || StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "N"))
                        nBenPayLine++;

                    // 塞受款人資料的資料前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, nBenPayLine);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不再塞空白行了
                        deleteRow(table, nBenPayLine);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, nBenPayLine);
                    }

                    addColumn(table, 40, 1, "受款人序：" + StringUtils.leftPad(String.valueOf(nBenList + 2), 2, "0"), fontCh12, 0, LEFT); // 第一筆是事故者, 故要加 1
                    addColumn(table, 20, 1, " ", fontCh12, 0, LEFT); // 發給：
                    // --- 申請日期 只有自然人才需要秀
                    addColumn(table, 41, 1, "姓名：" + StringUtils.defaultString(benData.getBenName()), fontCh12, 0, LEFT);
                    if (NumberUtils.isNumber(benData.getBenEvtRel())) {
                        addColumn(table, 19, 1, "申請日期：" + StringUtils.defaultString(benData.getAppDateStr()), fontCh12, 0, LEFT);
                    }
                    else {
                        addColumn(table, 19, 1, " ", fontCh12, 0, LEFT);
                    }
                    // ---
                    addColumn(table, 16, 1, "身分證號：" + StringUtils.defaultString(benData.getBenIdnNo()), fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, "出生日期：" + StringUtils.defaultString(benData.getBenBrDateString()), fontCh12, 0, LEFT);
                    addColumn(table, 28, 1, "電話：" + StringUtils.defaultString(benData.getTel1()) + ((StringUtils.isNotBlank(benData.getTel2()) && StringUtils.isNotBlank(benData.getTel1())) ? ("、" + StringUtils.defaultString(benData.getTel2())) : StringUtils.defaultString(benData.getTel2())), fontCh12, 0, LEFT);
                    // ---
                    // 帳號處理
                    String strBenAccountNo = StringUtils.defaultString(benData.getPayBankId());
                    if (StringUtils.isNotBlank(benData.getBranchId()))
                        if (benData.getPayTyp().equals("1") && !benData.getPayBankId().equals("700")) {
                            strBenAccountNo = strBenAccountNo + "-" + "0000";
                        }
                        else {
                            strBenAccountNo = strBenAccountNo + "-" + benData.getBranchId();
                        }
                    if (StringUtils.isNotBlank(benData.getPayEeacc()))
                        strBenAccountNo = strBenAccountNo + "-" + benData.getPayEeacc();

                    addColumn(table, 7, 1, "關係：" + StringUtils.defaultString(benData.getBenEvtRel()), fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "給付方式：" + StringUtils.defaultString(benData.getPayTyp()), fontCh12, 0, LEFT);
                    addColumn(table, 26, 1, "帳號：" + strBenAccountNo + " " + StringUtils.defaultString(benData.getBankName()), fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "戶名：" + StringUtils.defaultString(benData.getAccName()), fontCh12, 0, LEFT);
                    if (StringUtils.isBlank(benData.getSpecialAcc())) {
                        addColumn(table, 6, 1, "", fontCh12, 0, LEFT);
                    }
                    else {
                        addColumn(table, 6, 1, "(專戶)", fontCh12, 0, LEFT);
                    }
                    // --- 受益人要為自然人才需要秀底下資料
                    if (NumberUtils.isNumber(benData.getBenEvtRel())) {
                        addColumn(table, 10, 1, "國籍別：" + StringUtils.defaultString(benData.getBenNationTyp()), fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "性別：" + StringUtils.defaultString(benData.getBenSex()), fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "國籍：" + StringUtils.defaultString(benData.getBenNationName()), fontCh12, 0, LEFT);
                        addColumn(table, 30, 1, " ", fontCh12, 0, LEFT);
                    }
                    // ---
                    if (benPayList.size() > 0) {
                        addColumn(table, 8, 1, "給付年月", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "可領金額", fontCh12, 0, RIGHT);
                        addColumn(table, 8, 1, "扣減金額", fontCh12, 0, RIGHT);
                        addColumn(table, 8, 1, "紓困金額", fontCh12, 0, RIGHT);
                        addColumn(table, 8, 1, "匯款匯費", fontCh12, 0, RIGHT);
                        addColumn(table, 8, 1, "實付金額", fontCh12, 0, RIGHT);
                        addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "編審註記", fontCh12, 0, LEFT);
                    }
                    // ---
                    for (OldAgeReviewRpt01BenPayDataCase benPayData : benPayList) {
                        addColumn(table, 8, 1, benPayData.getPayYmString(), fontCh12, 0, LEFT); // 給付年月

                        if (benPayData.getIssueAmt() != null && benPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getIssueAmt()), fontCh12, 0, RIGHT); // 可領金額
                        else
                            addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 可領金額

                        if (benPayData.getOtherAmt() != null && benPayData.getOtherAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getOtherAmt()), fontCh12, 0, RIGHT); // 扣減金額
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, RIGHT); // 扣減金額

                        // 20100809 先不秀紓困金額
                        // if (benPayData.getOffsetAmt() != null && benPayData.getOffsetAmt().compareTo(new BigDecimal(0)) != 0)
                        // addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getOffsetAmt()), fontCh12, 0, RIGHT); // 紓困金額
                        // else
                        addColumn(table, 8, 1, " ", fontCh12, 0, RIGHT); // 紓困金額

                        if (benPayData.getPayRate() != null && benPayData.getPayRate().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getPayRate()), fontCh12, 0, RIGHT); // 匯款匯費
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, RIGHT); // 匯款匯費

                        if (benPayData.getAplpayAmt() != null && benPayData.getAplpayAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getAplpayAmt()), fontCh12, 0, RIGHT); // 實付金額
                        else
                            addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 實付金額

                        addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, benData.getChkfileDataBy(benPayData.getPayYm()), fontCh12, 0, LEFT); // 編審註記
                    }
                    // ---
                    addColumn(table, 60, 1, "地址：" + StringUtils.defaultString(benData.getCommZip()) + " " + StringUtils.defaultString(benData.getCommAddr()), fontCh12, 0, LEFT);
                    // ---
                    if (StringUtils.isNotBlank(benData.getGrdName()) || StringUtils.isNotBlank(benData.getGrdIdnNo()) || StringUtils.isNotBlank(benData.getGrdBrDate())) {
                        addColumn(table, 16, 1, "法定代理人姓名：" + StringUtils.defaultString(benData.getGrdName()), fontCh12, 0, LEFT);
                        addColumn(table, 16, 1, "身分證號：" + StringUtils.defaultString(benData.getGrdIdnNo()), fontCh12, 0, LEFT);
                        addColumn(table, 28, 1, "出生日期：" + StringUtils.defaultString(benData.getGrdBrDateString()), fontCh12, 0, LEFT);
                    }
                    // ---
                    BigDecimal benUnitpayAmt = null;
                    BigDecimal benReturnAmt = null;
                    BigDecimal benRemainAmt = null;
                    BigDecimal benLoanAmt = null;
                    BigDecimal benCompenAmt = null;
                    BigDecimal benLecomAmt = null;
                    BigDecimal benRecomAmt = null;
                    BigDecimal benBadDebtAmt = null;

                    if (benPayList.size() > 0) {
                        OldAgeReviewRpt01BenPayDataCase benPayData = benPayList.get(benPayList.size() - 1);
                        benUnitpayAmt = benPayData.getUnitpayAmt();
                        benReturnAmt = benPayData.getReturnAmt();
                        benRemainAmt = benPayData.getRemainAmt();
                        benLoanAmt = benPayData.getLoanAmt();
                        benLecomAmt = benPayData.getLecomAmt();
                        benRecomAmt = benPayData.getRecomAmt();
                        benBadDebtAmt = benPayData.getBadDebtAmt();
                    }

                    if (StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "A")) {
                        addColumn(table, 20, 1, "墊付金額：" + (StringUtils.equals(formatBigDecimalToInteger(benUnitpayAmt), "0") ? "" : formatBigDecimalToInteger(benUnitpayAmt)), fontCh12, 0, LEFT);
                        addColumn(table, 20, 1, "已歸墊金額：" + (StringUtils.equals(formatBigDecimalToInteger(benReturnAmt), "0") ? "" : formatBigDecimalToInteger(benReturnAmt)), fontCh12, 0, LEFT);
                        addColumn(table, 20, 1, "未歸墊金額：" + (StringUtils.equals(formatBigDecimalToInteger(benRemainAmt), "0") ? "" : formatBigDecimalToInteger(benRemainAmt)), fontCh12, 0, LEFT);
                    }
                    else if (StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "F")) {
                        addColumn(table, 60, 1, "紓困貸款金額：" + (StringUtils.equals(formatBigDecimalToInteger(benLoanAmt), "0") ? "" : formatBigDecimalToInteger(benLoanAmt)), fontCh12, 0, LEFT);
                    }
                    else if (StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "Z")) {
                        addColumn(table, 20, 1, "實際補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(benData.getCutAmt()), "0") ? "" : formatBigDecimalToInteger(benData.getCutAmt())), fontCh12, 0, LEFT);
                        addColumn(table, 20, 1, "已代扣補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(benLecomAmt), "0") ? "" : formatBigDecimalToInteger(benLecomAmt)), fontCh12, 0, LEFT);
                        addColumn(table, 20, 1, "未扣補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(benRecomAmt), "0") ? "" : formatBigDecimalToInteger(benRecomAmt)), fontCh12, 0, LEFT);
                    }
                    else if (StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "N")) {
                        addColumn(table, 60, 1, "紓困呆帳金額：" + (StringUtils.equals(formatBigDecimalToInteger(benBadDebtAmt), "0") ? "" : formatBigDecimalToInteger(benBadDebtAmt)), fontCh12, 0, LEFT);
                    }
                }

                // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                    addLine(table);
                }
                // ]
                // 受款人資料

                // 承保異動資料 (此區格式待確認)
                // [
                // 計算承保異動資料行數
                // 行數 = 固定行數 1 行 + 承保異動資料 n 行 + 承保異動資料標題 1 行 (若承保異動資料 > 0 筆)
                List<CiptUtilityCase> changeList = caseData.getChangeList();
                int nChangeDataLine = 1 + changeList.size();

                if (changeList.size() > 0)
                    nChangeDataLine++;

                // 塞承保異動資料資料前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, nChangeDataLine);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    deleteRow(table, nChangeDataLine);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, nChangeDataLine);
                }

                addColumn(table, 60, 1, "承保異動資料：", fontCh12, 0, LEFT);
                // ---
                if (changeList.size() > 0) {
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "保險證號", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "異動別", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "投保薪資", fontCh12, 0, RIGHT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    // addColumn(table, 3, 1, "逕調", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "部門", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "生效日期", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "退保日期", fontCh12, 0, LEFT);
                }
                // ---
                for (CiptUtilityCase changeData : changeList) {
                    // 塞承保異動資料資料前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                    }

                    addColumn(table, 2, 1, changeData.getSidMk(), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, changeData.getTxcdType2(), fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, changeData.getUno(), fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, changeData.getTxcd(), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, changeData.getTxcdTypeA(), fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, formatBigDecimalToInteger(changeData.getWage()), fontCh12, 0, RIGHT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    if (StringUtility.replaceSpaceString(changeData.getTsMk()).equals("P2") || StringUtility.replaceSpaceString(changeData.getTsMk()).equals("P4")) {
                        addColumn(table, 3, 1, changeData.getTsMk(), fontCh12, 0, LEFT);
                    } else {
                        addColumn(table, 3, 1, changeData.getTxcdTypeB(), fontCh12, 0, LEFT);
                    }
                    
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, changeData.getDept(), fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, changeData.getEfDteString(), fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, changeData.getExitDteString(), fontCh12, 0, LEFT); // 退保日期
                }
                // ]
                // 承保異動資料

                // 塞入一行空白行
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }

                // 投保單位資料 (有資料再印)
                // [
                // 申請單位
                OldAgeReviewRpt01UnitCase applyUnitData = caseData.getApplyUnitData();
                if (applyUnitData != null) {
                    // 申請單位資料前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                    }

                    addColumn(table, 10, 1, "申請單位", fontCh12, 0, LEFT); // 申請單位
                    addColumn(table, 10, 1, formatBigDecimalToInteger(applyUnitData.getPrsnoB()), fontCh12, 0, LEFT); // 單位人數
                    addColumn(table, 10, 1, applyUnitData.getUbNo(), fontCh12, 0, LEFT); // 保險證號
                    addColumn(table, 20, 1, applyUnitData.getUname(), fontCh12, 0, LEFT); // 投保單位名稱
                    addColumn(table, 10, 1, applyUnitData.getEname(), fontCh12, 0, LEFT); // 負責人
                }

                // 最後單位
                OldAgeReviewRpt01UnitCase lastUnitData = caseData.getLastUnitData();
                if (lastUnitData != null) {
                    // 最後單位資料前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                    }

                    addColumn(table, 10, 1, "最後單位", fontCh12, 0, LEFT); // 申請單位
                    addColumn(table, 10, 1, formatBigDecimalToInteger(lastUnitData.getPrsnoB()), fontCh12, 0, LEFT); // 單位人數
                    addColumn(table, 10, 1, lastUnitData.getUbNo(), fontCh12, 0, LEFT); // 保險證號
                    addColumn(table, 20, 1, lastUnitData.getUname(), fontCh12, 0, LEFT); // 投保單位名稱
                    addColumn(table, 10, 1, lastUnitData.getEname(), fontCh12, 0, LEFT); // 負責人
                }

                // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);
                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                    addLine(table);
                }
                // ]
                // ---------------------------------------------------------------------------------------------------
                // 最高 60 個月平均薪資明細
                // [
                // 最高 60 個月平均薪資明細資料前, 先塞空白行測試是否需換頁（兩行標題，一行資料）
                addEmptyRow(table, 3);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    deleteRow(table, 3);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 3);
                }

                addColumn(table, 40, 1, "最高 " + caseData.getAppMonth() + " 個月平均薪資明細:", fontCh12, 0, LEFT);
                addColumn(table, 20, 1, "實際均薪月數：" + caseData.getRealAvgMon() + "個月", fontCh12, 0, LEFT);
                // ---
                addColumn(table, 5, 1, "年 月", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "薪 資", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "年 月", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "薪 資", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "年 月", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "薪 資", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "年 月", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "薪 資", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "年 月", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "薪 資", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "年 月", fontCh12, 0, RIGHT);
                addColumn(table, 5, 1, "薪 資", fontCh12, 0, RIGHT);
                // ]

                List<OldAgeReviewRpt01MonthAvgAmtDataCase> monthAvgAmtList = caseData.getMonthAvgAmtList();
                for (int nMonth = 0; nMonth < monthAvgAmtList.size(); nMonth++) {
                    OldAgeReviewRpt01MonthAvgAmtDataCase monthAvgAmtData = monthAvgAmtList.get(nMonth);

                    if (nMonth % 6 == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }
                    }

                    if (monthAvgAmtData.getDwMk().equals("2")) {
                        addColumn(table, 5, 1, "*" + monthAvgAmtData.getAvgYmString(), fontCh12, 0, RIGHT); // 年月
                    }
                    else {
                        addColumn(table, 5, 1, " " + monthAvgAmtData.getAvgYmString(), fontCh12, 0, RIGHT); // 年月
                    }

                    if (monthAvgAmtData.getAvgWg() != null && monthAvgAmtData.getAvgWg().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 5, 1, formatBigDecimalToInteger(monthAvgAmtData.getAvgWg()), fontCh12, 0, RIGHT); // 薪資
                    else
                        addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT); // 薪資

                    // 最後一筆要補滿表格
                    if (nMonth == monthAvgAmtList.size() - 1) {
                        for (int nEmpty = 0; nEmpty < 6 - ((nMonth % 6) + 1); nEmpty++) {
                            addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT); // 年月
                            addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT); // 薪資
                        }
                    }
                }

                document.add(table);

                if (StringUtils.isNotBlank(caseData.getCheckin()) && (caseData.getConsent() != null && caseData.getConsent().length > 0)) {
                    if (StringUtils.equals(caseData.getCheckin(), "2")) {
                        PdfReader reader = null;
                        try {
                            reader = new PdfReader(caseData.getConsent());
                            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                                document.newPage();
                                int rotation = reader.getPageRotation(i);
                                PdfImportedPage page = writer.getImportedPage(reader, i);
                                Image img = Image.getInstance(page);
                                if (rotation == 90) {
                                    img.setRotationDegrees(-90);
                                } else if (rotation == 180) {
                                    img.setRotationDegrees(-180);
                                } else if (rotation == 270) {
                                    img.setRotationDegrees(-270);
                                }
                                img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
                                float x = (PageSize.A4.getWidth() - img.getScaledWidth()) / 2;
                                float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 2;
                                img.setAbsolutePosition(x, y);
                                getPdfContetByte().addImage(img);
                            }
                        } catch (Exception e) {
                            // 設定查詢失敗訊息
                            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKindMsg)) {
                                log.error("產製 勞保老年年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
                            } else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKindMsg)) {
                                log.error("產製 勞保失能年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
                            } else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKindMsg)) {
                                log.error("產製 勞保遺屬年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
                            } else {
                                log.error("產製 勞保年金給付受理編審清單 發生錯誤:" + ExceptionUtility.getStackTrace(e));
                            }
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (Exception e) {
                                    log.error(ExceptionUtility.getStackTrace(e));
                                }
                            }
                        }
                    }
                }

                if(writer.getCurrentPageNumber() % 2 != 0) {
                   	document.newPage();
                   	writer.setPageEmpty(false);
                }

            }

            // 第 ? 頁 / 共 ? 頁 中的 共 ? 頁 部份
            // [
            // closePageText(String.valueOf(writer.getPageNumber()) + " ", 12);
            // ]

            document.close();
        }
        finally {
            document.close();
        }

        return bao;
    }
    
}
