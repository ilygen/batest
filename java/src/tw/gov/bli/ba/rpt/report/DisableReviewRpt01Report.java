package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.CiptUtilityCase;
import tw.gov.bli.ba.rpt.cases.CivilServantReviewRpt01DeadOncePayCase;
import tw.gov.bli.ba.rpt.cases.CivilServantReviewRpt01DisablePayCase;
import tw.gov.bli.ba.rpt.cases.CivilServantReviewRpt01RetirementAnnuityPayCase;
import tw.gov.bli.ba.rpt.cases.DisableQueryOccAccDataCase;
import tw.gov.bli.ba.rpt.cases.DisableRevewRpt01ExpDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01AnnuityPayDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01BenDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01BenPayDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01Case;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01ChkfileDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01ChkfileDescCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01CipgDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01DecideDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01DeductDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01DiePayDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01FamChkfileDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01FamilyDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01InjuryPayDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01IssueAmtDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01JoblessPayDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01LoanAmtCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01NotifyDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01NpData38Case;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01NpPayDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01OldAgePayDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01OldPayDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01OncePayDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01PayDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01PayDeductDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01SurvivorPayDataCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01UnitCase;
import tw.gov.bli.ba.rpt.cases.SoldierReviewRpt01DeadPayCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ValidateUtility;

/**
 * 勞保失能年金給付受理編審清單
 * 
 * @author Evelyn Hsu 
 * 20101124 kiyomi 核定通知書之後的內容取消跳頁判斷
 * 20131119 KIYOMI 增加若 編審註記代碼 (CHKCODE) 值為 P1 及（ P2 或 P3），表頭要顯示「＊預警案件」
 */

public class DisableReviewRpt01Report extends ReportBase {

    private int nPage; // 每筆資料的頁數

    private String printDate = ""; // 印表日期

    private String line = "--------------------------------------------------------------------------------------------------------------------------------------";

    public DisableReviewRpt01Report() throws Exception {
        super();
        nPage = 0;
        printDate = DateUtility.formatNowChineseDateString(false);
    }

	class DisableReviewSameKind {
		
		public DisableReviewSameKind() {
			super();
			
		}
		
		/**
		 * 
			// 勞保失能給付
			// 災保失能給付
			// 退保後職業病失能津貼
			// 未加保失能補助
			// 勞保失能差額金
			// 災保失能差額金
			// 勞保失能年金
			// 災保失能年金
			// 災保失能照護補助
			// 農保殘廢給付
			// 國保身障年金
			// 公保失能給付
			// 軍保身心障礙給付
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public Table execute(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
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
	        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	        addColumn(table, 58, 1, "請領同類給付資料：", fontCh12b, 0, LEFT);

			// 勞保失能給付
	        // 一次給付資料 (有資料再印)
	        table = this.printDisableReviewOncePays(caseData, caseData.getOncePayList(), table, earlyWarning, "[一次給付]");
	        
	        // 災保失能給付
	        // 一次給付資料 (有資料再印) 災保 災保失能給付 20220421
	        table = this.printDisasterOncePays(caseData, caseData.getDisasterOncePayList(), table, earlyWarning, "申請災保失能給付記錄：");
	        
			// 退保後職業病失能津貼
	        // 一次給付資料 (有資料再印) 災保 退保後職業病失能津貼 20220421
	        table = this.printDisasterOncePays(caseData, caseData.getDisasterOncePay3TList(), table, earlyWarning, "申請退保後職業病失能津貼記錄：");
	        
			// 未加保失能補助
	        // 一次給付資料 (有資料再印) 災保 未加保失能補助 20220421
	        table = this.printDisasterOncePays(caseData, caseData.getDisasterOncePay3NList(), table, earlyWarning, "申請未加保失能補助記錄：");
	        
			// 災保失能差額金
	        // 一次給付資料 (有資料再印) 災保 災保失能差額金 20220421
	        table = this.printDisasterOncePays(caseData, caseData.getDisasterOncePay39List(), table, earlyWarning, "申請災保失能差額金記錄：");
	        
			// 勞保失能年金
	        // 年金給付資料 (有資料再印)
	        table = this.printAnnuitys(caseData, table, earlyWarning);
	        
			// 災保失能年金
	        // 年金給付資料 (有資料再印) 災保 20220421
	        table = this.printDisasterReviewAnnuitys(caseData, table, earlyWarning);
	        
			// 災保失能照護補助
	        table = this.printDisasterOncePays(caseData, caseData.getDisasterOncePay3CList(), table, earlyWarning, "申請災保失能照護補助記錄：");
	        
			// 農保殘廢給付
	        // 申請農保殘廢給付記錄 (有資料再印)
	        table = this.printFarmOncePays(caseData, table, earlyWarning);
	        
	        // 申請國保給付記錄資料 (有資料再印) excel 無此項目
	        table = this.printNbDisPays(caseData, table, earlyWarning);
	        
			// 公保失能給付
	        table = this.printCivilServantDisablePayList(caseData, caseData.getCivilServantDisablePayList(), table, earlyWarning, "申請公保失能給付記錄：");
	        
			// 軍保身心障礙給付
	        table = this.printSoldierDisablePayList(caseData, caseData.getSoldierDisablePayList(), table, earlyWarning, "申請軍保身心障礙給付記錄：");
	        
	        
	        


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
			
	        return table;
		}

		public Table printSoldierDisablePayList(DisableReviewRpt01Case caseData,
				List<SoldierReviewRpt01DeadPayCase> oncePayList, Table table, String earlyWarning,
				String title) throws Exception {
            // 一次給付資料 (有資料再印)
			if (oncePayList != null) {
				for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++) { // ... [
					SoldierReviewRpt01DeadPayCase oncePayData = oncePayList.get(nOncePayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, title, fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// addEmptyRow(table, 8);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 8);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 8);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					
					//申請人姓名-APPNAME、申請日期-APPDATE、確定永久失能日-EVTRETDATE、失能等級-DISQUALMK、失能編號-CRIINJDP、核付日期-APPISSUEDATE、核付金額-APPISSUEAMT、 結案日期-CLOSEDATE

					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "申請人姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "致身心障礙日", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "身障等級", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "身障編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, oncePayData.getAppName(), fontCh12, 0, LEFT); // 申請人姓名
					addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getAppDate()), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 10, 1, oncePayData.getEvtRetDate(), fontCh12, 0, LEFT); // 確定永久失能日
					addColumn(table, 9, 1, oncePayData.getDisQualMk(), fontCh12, 0, LEFT); // 身障等級
					addColumn(table, 9, 1, oncePayData.getDisEvtCode(), fontCh12, 0, LEFT); // 身障編號
					addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getAppIssueDate()), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "結案日期", fontCh12, 0, LEFT);
					addColumn(table, 37, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, oncePayData.getAppIssueAmt(), fontCh12, 0, LEFT); // 核付金額
					addColumn(table, 9, 1, oncePayData.getCloseDate(), fontCh12, 0, LEFT); // 結案日期
					addColumn(table, 37, 1, " ", fontCh12, 0, LEFT); 
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
    				// ---
    				// 20101124 kiyomi - start
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
    				// 20101124 kiyomi - end
					// 最後一筆印完後空一行 (如果年金給付資料有資料再印)
					if ((nOncePayCount == oncePayList.size() - 1)) {
						
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
			
			return table;
		}

		public Table printCivilServantDisablePayList(DisableReviewRpt01Case caseData,
				List<CivilServantReviewRpt01DisablePayCase> oncePayList, Table table,
				String earlyWarning, String title) throws Exception {
            // 一次給付資料 (有資料再印)
			if (oncePayList != null) {
				for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++) { // ... [
					CivilServantReviewRpt01DisablePayCase oncePayData = oncePayList.get(nOncePayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, title, fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// addEmptyRow(table, 8);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 8);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 8);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					
					//申請人姓名-APPNAME、申請日期-APPDATE、確定永久失能日-EVTRETDATE、失能等級-DISQUALMK、失能編號-CRIINJDP、核付日期-APPISSUEDATE、核付金額-APPISSUEAMT、 結案日期-CLOSEDATE

					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "申請人姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "確定永久失能日", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "失能等級", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "失能編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, oncePayData.getAppName(), fontCh12, 0, LEFT); // 申請人姓名
					addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getAppDate()), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 10, 1, oncePayData.getEvtRetDate(), fontCh12, 0, LEFT); // 確定永久失能日
					addColumn(table, 9, 1, oncePayData.getDisQualMk(), fontCh12, 0, LEFT); // 失能等級
					addColumn(table, 9, 1, oncePayData.getCriinjdp(), fontCh12, 0, LEFT); // 失能編號
					addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getAppIssueDate()), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "結案日期", fontCh12, 0, LEFT);
					addColumn(table, 37, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, oncePayData.getAppIssueAmt(), fontCh12, 0, LEFT); // 核付金額
					addColumn(table, 9, 1, oncePayData.getCloseDate(), fontCh12, 0, LEFT); // 結案日期
					addColumn(table, 37, 1, " ", fontCh12, 0, LEFT); 
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
    				// ---
    				// 20101124 kiyomi - start
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
    				// 20101124 kiyomi - end
					// 最後一筆印完後空一行 (如果年金給付資料有資料再印)
					if ((nOncePayCount == oncePayList.size() - 1) 
							&& (caseData.getSoldierDisablePayList().size() > 0)) {
						
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
			
			return table;
		}

		
		/**
		 * 申請國保給付記錄資料
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public Table printNbDisPays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 申請國保身障給付記錄資料 (有資料再印)
			if (caseData.getNbDisPayList() != null) {
				List<DisableReviewRpt01NpPayDataCase> npDisPayList = caseData.getNbDisPayList();
				for (int nNpDisPayCount = 0; nNpDisPayCount < npDisPayList.size(); nNpDisPayCount++) { // ... [
					DisableReviewRpt01NpPayDataCase npDisPayData = npDisPayList.get(nNpDisPayCount);
					
					// 印國保給付記錄表頭
					
					if (nNpDisPayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						// 國保給付記錄 表頭
						addColumn(table, 58, 1, "申請國保身障年金給付記錄：", fontCh12b, 0, LEFT);
						
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// 國保給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					// addEmptyRow(table, 4);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 4);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 4);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "首次給付年月", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "手冊鑑定日期", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, npDisPayData.getEvteeName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 11, 1, npDisPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 11, 1, npDisPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 11, 1, StringUtils.defaultString(npDisPayData.getPayYmsString()) + ((StringUtils.isNotBlank(npDisPayData.getPayYmsString())) ? "-" + StringUtils.defaultString(npDisPayData.getPayYmeString()) : ""), fontCh12, 0,
							LEFT); // 首次給付年月
					addColumn(table, 11, 1, StringUtils.defaultString(npDisPayData.getHandIcApDateString()), fontCh12, 0, LEFT); // 手冊鑑定日期
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (npDisPayData.getIssueAmt() != null && npDisPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 6, 1, formatBigDecimalToInteger(npDisPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額
					addColumn(table, 6, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, npDisPayData.getChkDtString(), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 11, 1, npDisPayData.getAplPayDateString(), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 10, 1, StringUtils.defaultString(npDisPayData.getCloseDtString() + ((StringUtils.isNotBlank(npDisPayData.getCloseReason())) ? " / " + npDisPayData.getCloseReason() : "")), fontCh12, 0, LEFT); // 結案日期/結案原因
					addColumn(table, 12, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					
					// ---
					// 最後一筆印完後空一行 (如果年金給付資料有資料再印)
					if ((nNpDisPayCount == npDisPayList.size() - 1) 
							&& (caseData.getNbDisPayList() != null && caseData.getNbDisPayList().size() > 0)) {
						// 空白行
						
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
					
				} // ] ... end for (int nNpPayCount = 0; nNpPayCount < npPayList.size(); nNpPayCount++)
				
			}
			
			return table;
		}

		/**
		 * 申請農保殘廢給付記錄
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @throws Exception
		 */
		public Table printFarmOncePays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
            // 申請農保殘廢給付記錄 (有資料再印)
			if (caseData.getFarmPayList() != null) {
				List<DisableReviewRpt01OncePayDataCase> farmPayList = caseData.getFarmPayList();
				for (int nFarmPayCount = 0; nFarmPayCount < farmPayList.size(); nFarmPayCount++) { // ... [
					DisableReviewRpt01OncePayDataCase farmPayData = farmPayList.get(nFarmPayCount);
					
					// 印申請農保殘廢給付記錄表頭
					
					if (nFarmPayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 申請農保殘廢給付記錄 表 頭
						
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請農保身心障礙給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// addEmptyRow(table, 8);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 8);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 8);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "審殘日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, farmPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 9, 1, StringUtils.defaultString(farmPayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
					addColumn(table, 10, 1, farmPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 9, 1, farmPayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
					addColumn(table, 9, 1, StringUtils.defaultString(farmPayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 審殘日期
					addColumn(table, 11, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付日數", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "殘廢等級", fontCh12, 0, LEFT);
					addColumn(table, 37, 1, "殘廢項目", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, formatBigDecimalToInteger(farmPayData.getBmChkDay()), fontCh12, 0, LEFT); // 核付日數
					addColumn(table, 9, 1, farmPayData.getBmCriInjNme(), fontCh12, 0, LEFT); // 失能等級
					addColumn(table, 37, 1, farmPayData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 19, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (farmPayData.getBmChkAmt() != null && farmPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 9, 1, formatBigDecimalToInteger(farmPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 9, 1, "0", fontCh12, 0, LEFT); // 核定金額
					addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
					
					addColumn(table, 9, 1, StringUtils.defaultString(farmPayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 10, 1, StringUtils.defaultString(farmPayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 10, 1, farmPayData.getBmNrepDateString() + ((StringUtils.isNotBlank(farmPayData.getBmNdocMk())) ? " / " + farmPayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
					addColumn(table, 19, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 19, 1, "不給付日期/原因", fontCh12, 0, LEFT);
					addColumn(table, 20, 1, "補收金額—已收金額", fontCh12, 0, LEFT);
					addColumn(table, 19, 1, " ", fontCh12, 0, LEFT);
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 19, 1, StringUtils.defaultString(farmPayData.getBmNopDateString()) + ((StringUtils.isNotBlank(farmPayData.getBmNopMark())) ? " / " + farmPayData.getBmNopMark() : ""), fontCh12, 0, LEFT); // 不給付日期/原因
					if (farmPayData.getBmAdjAmts() != null && farmPayData.getBmAdjAmts().compareTo(new BigDecimal(0)) != 0) {
						addColumn(table, 10, 1, formatBigDecimalToInteger(farmPayData.getBmAdjAmts()), fontCh12, 0, LEFT); // 補收金額
						addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
					}
					else {
						addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 補收金額
					}
					addColumn(table, 28, 1, " ", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (如果年金給付資料有資料再印)
					if ((nFarmPayCount == farmPayList.size() - 1) 
							&& (caseData.getCivilServantDisablePayList().size() > 0
				            		|| caseData.getSoldierDisablePayList().size() > 0)) {
						// 空白行
						
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
				}
				
			}
			
			return table;
		}

		/**
		 * 年金給付資料
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @return 
		 * @throws Exception
		 */
		public Table printAnnuitys(DisableReviewRpt01Case caseData,
				Table table, String earlyWarning) throws Exception {
            // 年金給付資料 (有資料再印)
			if (caseData.getAnnuityPayList() != null) {
				List<DisableReviewRpt01AnnuityPayDataCase> annuityPayList = caseData.getAnnuityPayList();
				for (int nAnnuityPayCount = 0; nAnnuityPayCount < annuityPayList.size(); nAnnuityPayCount++) { // ... [
					DisableReviewRpt01AnnuityPayDataCase annuityPayData = annuityPayList.get(nAnnuityPayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "[年金給付]", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// 年金給付資料一筆有四行, 在塞資料前先測試是否需換頁
					// addEmptyRow(table, 6);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 6);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 6);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "首次給付年月", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "診斷失能日", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, annuityPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getAppDateString()), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 10, 1, annuityPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 9, 1, annuityPayData.getApUbno(), fontCh12, 0, LEFT); // 保險證號
					addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getPayYmsString()) + ((StringUtils.isNotBlank(annuityPayData.getPayYmsString())) ? "-" + StringUtils.defaultString(annuityPayData.getPayYmString()) : ""), fontCh12,
							0, LEFT); // 首次給付年月
					addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getEvtJobDateString()), fontCh12, 0, LEFT); // 診斷失能日
					
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "傷病分類", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "失能等級", fontCh12, 0, LEFT);
					addColumn(table, 37, 1, "失能項目", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, annuityPayData.getEvTyp(), fontCh12, 0, LEFT); // 傷病分類
					addColumn(table, 9, 1, annuityPayData.getCriInJclStr(), fontCh12, 0, LEFT); // 失能等級
					addColumn(table, 37, 1, annuityPayData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 27, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (annuityPayData.getIssueAmt() != null && annuityPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 9, 1, formatBigDecimalToInteger(annuityPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 9, 1, "0", fontCh12, 0, LEFT); // 核定金額
					addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getChkDateString()), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 10, 1, StringUtils.defaultString(annuityPayData.getAplpayDateString()), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 27, 1, StringUtils.defaultString(annuityPayData.getCloseDateString()) + ((StringUtils.isNotBlank(annuityPayData.getCloseCause())) ? (" / " + annuityPayData.getCloseCause()) : ""), fontCh12, 0, LEFT); // 結案日期/原因
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 最後一筆印完後空一行 (如果年金給付資料有資料再印)
					if ((nAnnuityPayCount == annuityPayList.size() - 1) && (caseData.getDisasterAnnuityPayList().size() > 0
		            		|| caseData.getDisasterOncePay3CList().size() > 0
		            		|| caseData.getFarmPayList().size() > 0
		            		|| caseData.getCivilServantDisablePayList().size() > 0
		            		|| caseData.getSoldierDisablePayList().size() > 0)) {
						// 空白行
						
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
					
				} // ] ... end for (int nAnnuityPayCount = 0; nAnnuityPayCount < annuityPayList.size(); nAnnuityPayCount++)
				
			}

			return table;
			
		}

		/**
		 * 年金給付資料
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @return 
		 * @throws Exception
		 */
		public Table printDisasterReviewAnnuitys(DisableReviewRpt01Case caseData,
				Table table, String earlyWarning) throws Exception {
            // 年金給付資料 (有資料再印)
            List<DisableReviewRpt01AnnuityPayDataCase> annuityPayList = caseData.getDisasterAnnuityPayList();
            for (int nAnnuityPayCount = 0; nAnnuityPayCount < annuityPayList.size(); nAnnuityPayCount++) { // ... [
                DisableReviewRpt01AnnuityPayDataCase annuityPayData = annuityPayList.get(nAnnuityPayCount);

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
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 58, 1, "[年金給付]", fontCh12b, 0, LEFT);
                }
                else {
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                        addEmptyRow(table, 1);
                    }
                }

                // 20101124 kiyomi - mark start
                // 年金給付資料一筆有四行, 在塞資料前先測試是否需換頁
                // addEmptyRow(table, 6);

                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // deleteRow(table, 6);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }
                // else {
                // deleteRow(table, 6);
                // }
                // 20101124 kiyomi - mark end
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, "首次給付年月", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, "診斷失能日", fontCh12, 0, LEFT);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                // ---
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, annuityPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名

                addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getAppDateString()), fontCh12, 0, LEFT); // 申請日期
                addColumn(table, 10, 1, annuityPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                addColumn(table, 9, 1, annuityPayData.getApUbno(), fontCh12, 0, LEFT); // 保險證號
                addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getPayYmsString()) + ((StringUtils.isNotBlank(annuityPayData.getPayYmsString())) ? "-" + StringUtils.defaultString(annuityPayData.getPayYmString()) : ""), fontCh12,
                                0, LEFT); // 首次給付年月
                addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getEvtJobDateString()), fontCh12, 0, LEFT); // 診斷失能日

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                // ---

                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "傷病分類", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, "失能等級", fontCh12, 0, LEFT);
                addColumn(table, 37, 1, "失能項目", fontCh12, 0, LEFT);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                // ---
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, annuityPayData.getEvTyp(), fontCh12, 0, LEFT); // 傷病分類
                addColumn(table, 9, 1, annuityPayData.getCriInJclStr(), fontCh12, 0, LEFT); // 失能等級
                addColumn(table, 37, 1, annuityPayData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                // ---

                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
                addColumn(table, 27, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                // ---

                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                if (annuityPayData.getIssueAmt() != null && annuityPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                    addColumn(table, 9, 1, formatBigDecimalToInteger(annuityPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                else
                    addColumn(table, 9, 1, "0", fontCh12, 0, LEFT); // 核定金額
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getChkDateString()), fontCh12, 0, LEFT); // 核定日期
                addColumn(table, 10, 1, StringUtils.defaultString(annuityPayData.getAplpayDateString()), fontCh12, 0, LEFT); // 核付日期
                addColumn(table, 27, 1, StringUtils.defaultString(annuityPayData.getCloseDateString()) + ((StringUtils.isNotBlank(annuityPayData.getCloseCause())) ? (" / " + annuityPayData.getCloseCause()) : ""), fontCh12, 0, LEFT); // 結案日期/原因
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                // ---
                // 最後一筆印完後空一行 (如果年金給付資料有資料再印)
                if ((nAnnuityPayCount == annuityPayList.size() - 1) && (caseData.getDisasterOncePay3CList().size() > 0
	            		|| caseData.getFarmPayList().size() > 0
	            		|| caseData.getCivilServantDisablePayList().size() > 0
	            		|| caseData.getSoldierDisablePayList().size() > 0)) {
                    // 空白行

                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不再塞空白行了

                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                }

            } // ] ... end for (int nAnnuityPayCount = 0; nAnnuityPayCount < annuityPayList.size(); nAnnuityPayCount++)

            return table;
			
		}

		public Table printDisasterOncePays(DisableReviewRpt01Case caseData, List<DisableReviewRpt01OncePayDataCase> oncePayList, Table table, String earlyWarning, String title) throws Exception {
	        for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++) { // ... [
	        	DisableReviewRpt01OncePayDataCase oncePayData = oncePayList.get(nOncePayCount);

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
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 58, 1, title, fontCh12b, 0, LEFT);
	            }
	            else {
	                addEmptyRow(table, 1);

	                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                    deleteRow(table, 1);
	                    document.add(table);
	                    table = addHeader(caseData, false, earlyWarning);
	                }
	                else {
	                    deleteRow(table, 1);
	                    addEmptyRow(table, 1);
	                }
	            }

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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "診斷失能日", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, oncePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名

	            addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
	            addColumn(table, 10, 1, oncePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
	            addColumn(table, 9, 1, oncePayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
	            addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 診斷失能日

	            addColumn(table, 9, 1, oncePayData.getBmEvType(), fontCh12, 0, LEFT); // 診斷失能日

	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            // ---

	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "核付日數", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "失能等級", fontCh12, 0, LEFT);
	            addColumn(table, 37, 1, "失能項目", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

	            // ---

	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, formatBigDecimalToInteger(oncePayData.getBmChkDay()), fontCh12, 0, LEFT); // 核定日數
	            addColumn(table, 9, 1, oncePayData.getBmCriInjNme(), fontCh12, 0, LEFT); // 失能等級
	            addColumn(table, 37, 1, oncePayData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
	            addColumn(table, 18, 1, "補件日期/註記", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "", fontCh12, 0, LEFT);
	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            if (oncePayData.getBmChkAmt() != null && oncePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
	                addColumn(table, 9, 1, formatBigDecimalToInteger(oncePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
	            else
	                addColumn(table, 9, 1, "0", fontCh12, 0, LEFT); // 核定金額
	            addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
	            addColumn(table, 10, 1, StringUtils.defaultString(oncePayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
	            addColumn(table, 18, 1, oncePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(oncePayData.getBmNdocMk())) ? " / " + oncePayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
	            addColumn(table, 11, 1, "", fontCh12, 0, LEFT);

	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 19, 1, "不給付日期/原因", fontCh12, 0, LEFT);
	            addColumn(table, 20, 1, "補收金額—已收金額", fontCh12, 0, LEFT);
	            addColumn(table, 19, 1, " ", fontCh12, 0, LEFT);
	            // ---

	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 19, 1, StringUtils.defaultString(oncePayData.getBmNopDateString()) + ((StringUtils.isNotBlank(oncePayData.getBmNopMark())) ? " / " + oncePayData.getBmNopMark() : ""), fontCh12, 0, LEFT); // 不給付日期/原因
	            if (oncePayData.getBmAdjAmts() != null && oncePayData.getBmAdjAmts().compareTo(new BigDecimal(0)) != 0) {
	                addColumn(table, 10, 1, formatBigDecimalToInteger(oncePayData.getBmAdjAmts()), fontCh12, 0, LEFT); // 補收金額
	                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
	            }
	            else {
	                addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 補收金額
	            }
	            addColumn(table, 28, 1, " ", fontCh12, 0, LEFT);

	            // 最後一筆印完後空一行 (如果年金給付資料有資料再印)
	            if ((nOncePayCount == oncePayList.size() - 1) && (caseData.getFarmPayList().size() > 0
	            		|| caseData.getCivilServantDisablePayList().size() > 0
	            		|| caseData.getSoldierDisablePayList().size() > 0
	            		)) {
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

	        return table;
			
		}

		public Table printDisableReviewOncePays(DisableReviewRpt01Case caseData, List<DisableReviewRpt01OncePayDataCase> oncePayList, Table table, String earlyWarning, String title) throws Exception {
	        for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++) { // ... [
	        	DisableReviewRpt01OncePayDataCase oncePayData = oncePayList.get(nOncePayCount);

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
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 58, 1, title, fontCh12b, 0, LEFT);
	            }
	            else {
	                addEmptyRow(table, 1);

	                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                    deleteRow(table, 1);
	                    document.add(table);
	                    table = addHeader(caseData, false, earlyWarning);
	                }
	                else {
	                    deleteRow(table, 1);
	                    addEmptyRow(table, 1);
	                }
	            }

	            // 20101124 kiyomi - mark start
	            // addEmptyRow(table, 8);

	            // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	            // deleteRow(table, 8);
	            // document.add(table);
	            // table = addHeader(caseData, false, earlyWarning);
	            // }
	            // else {
	            // deleteRow(table, 8);
	            // }
	            // 20101124 kiyomi - mark end
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "診斷失能日", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, oncePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名

	            addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
	            addColumn(table, 10, 1, oncePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
	            addColumn(table, 9, 1, oncePayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
	            addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 診斷失能日

	            addColumn(table, 9, 1, oncePayData.getBmEvType(), fontCh12, 0, LEFT); // 診斷失能日

	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            // ---

	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "核付日數", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "失能等級", fontCh12, 0, LEFT);
	            addColumn(table, 37, 1, "失能項目", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

	            // ---

	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, formatBigDecimalToInteger(oncePayData.getBmChkDay()), fontCh12, 0, LEFT); // 核定日數
	            addColumn(table, 9, 1, oncePayData.getBmCriInjNme(), fontCh12, 0, LEFT); // 失能等級
	            addColumn(table, 37, 1, oncePayData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
	            addColumn(table, 18, 1, "補件日期/註記", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "", fontCh12, 0, LEFT);
	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            if (oncePayData.getBmChkAmt() != null && oncePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
	                addColumn(table, 9, 1, formatBigDecimalToInteger(oncePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
	            else
	                addColumn(table, 9, 1, "0", fontCh12, 0, LEFT); // 核定金額
	            addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
	            addColumn(table, 10, 1, StringUtils.defaultString(oncePayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
	            addColumn(table, 18, 1, oncePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(oncePayData.getBmNdocMk())) ? " / " + oncePayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
	            addColumn(table, 11, 1, "", fontCh12, 0, LEFT);

	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 19, 1, "不給付日期/原因", fontCh12, 0, LEFT);
	            addColumn(table, 20, 1, "補收金額—已收金額", fontCh12, 0, LEFT);
	            addColumn(table, 19, 1, " ", fontCh12, 0, LEFT);
	            // ---

	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 19, 1, StringUtils.defaultString(oncePayData.getBmNopDateString()) + ((StringUtils.isNotBlank(oncePayData.getBmNopMark())) ? " / " + oncePayData.getBmNopMark() : ""), fontCh12, 0, LEFT); // 不給付日期/原因
	            if (oncePayData.getBmAdjAmts() != null && oncePayData.getBmAdjAmts().compareTo(new BigDecimal(0)) != 0) {
	                addColumn(table, 10, 1, formatBigDecimalToInteger(oncePayData.getBmAdjAmts()), fontCh12, 0, LEFT); // 補收金額
	                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
	            }
	            else {
	                addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 補收金額
	            }
	            addColumn(table, 28, 1, " ", fontCh12, 0, LEFT);

	            // 最後一筆印完後空一行 (如果年金給付資料有資料再印)
	            if ((nOncePayCount == oncePayList.size() - 1) && (caseData.getDisasterOncePayList().size() > 0 
	            		|| caseData.getDisasterOncePay3TList().size() > 0
	            		|| caseData.getDisasterOncePay3NList().size() > 0
	            		|| caseData.getDisasterOncePay39List().size() > 0
	            		|| caseData.getAnnuityPayList().size() > 0
	            		|| caseData.getDisasterAnnuityPayList().size() > 0
	            		|| caseData.getDisasterOncePay3CList().size() > 0
	            		|| caseData.getFarmPayList().size() > 0
	            		|| caseData.getCivilServantDisablePayList().size() > 0
	            		|| caseData.getSoldierDisablePayList().size() > 0
	            		)) {
	            	
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
			
	        return table;
		}


	}
	
	class DisableReviewOtherKind {
		
		public DisableReviewOtherKind() {
			super();
			
		}
		
		/**
		 * 
			// 職災住院醫療給付
			// 勞保傷病給付
			// 災保傷病給付
			// 莫拉克傷病給付
			// 傷病照護補助
			// 勞保老年給付
			// 勞保補償金
			// 勞保老年差額金
			// 勞保本人死亡給付
			// 災保本人死亡給付
			// 退保後職業病死亡津貼
			// 未加保死亡補助
			// 勞保家屬死亡給付
			// 勞保失蹤津貼給付
			// 災保失蹤津貼給付
			// 農保喪葬津貼
			// 就保失業給付
			// 就保職訓津貼
			// 就保育嬰津貼
			// 勞保老年年金
			// 勞保遺屬年金
			// 災保遺屬年金
			// 國保老年年金
			// 國保喪葬給付
			// 國保遺屬年金
			// 公保養老年金給付
			// 公保養老遺屬年金給付
			// 公保死亡遺屬年金給付
		 * 
		 * @param caseData
		 * @param table
		 * @param earlyWarning
		 * @return 
		 * @throws Exception
		 */
		public Table execute(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
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

	        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	        addColumn(table, 58, 1, "請領他類給付資料：", fontCh12b, 0, LEFT);

			// 職災住院醫療給付
	        // 申請職災住院醫療給付記錄
	        table = this.printOncePays(caseData, table, earlyWarning);
	        
			// 勞保傷病給付
	        // 申請傷病給付記錄資料 (有資料再印)
	        table = this.printInjuryPays(caseData, table, earlyWarning);
	        
			// 災保傷病給付
	        table = this.printDisasterReviewInjuryPays(caseData, table, earlyWarning);
	        
			// 傷病照護補助
	        table = this.printDisasterReviewInjuryCarePays(caseData, table, earlyWarning);
	        
			// 勞保老年給付
	        // 申請老年給付記錄 (有資料再印)
	        table = this.printOldAgePays(caseData, table, earlyWarning);
	        
			// 勞保本人死亡給付
	        // 申請死亡給付記錄資料 (有資料再印)
	        table = this.printDiePays(caseData, table, earlyWarning);
	        
			// 災保本人死亡給付
	        table = this.printDisasterDiePays(caseData, table, earlyWarning);
	        
			// 退保後職業病死亡津貼
	        table = this.printDisasterDieForDiseaseAfterQuitPays(caseData, table, earlyWarning);
	        
			// 未加保死亡補助
	        table = this.printDisasterDieWithoutPays(caseData, table, earlyWarning);
	        
			// 勞保家屬死亡給付
	        // 申請遺屬年金給付(有資料再印)
	        table = this.printSurvivorPays(caseData, table, earlyWarning);
	        
			// 勞保失蹤津貼給付
	        // 申請失蹤給付記錄資料 (有資料再印)
	        table = this.printLostPays(caseData, table, earlyWarning);
	        
			// 災保失蹤津貼給付
	        table = this.printDisasterLostPays(caseData, table, earlyWarning);
	        
			// 農保喪葬津貼
	        // 申請農保死亡給付記錄資料 (有資料再印)
	        table = this.printFarmDiePays(caseData, table, earlyWarning);
	        
			// 就保失業給付
	        // 申請失業給付記錄資料 (有資料再印)
	        table = this.printJoblessPays(caseData, table, earlyWarning);
	        
			// 就保職訓津貼
	        // 申請職業訓練生活津貼記錄資料 (有資料再印)
	        table = this.printJobTrainingPays(caseData, table, earlyWarning);
	        
			// 勞保老年年金
	        // 申請老年年金給付(有資料再印)
	        table = this.printOldAgeAnnuitys(caseData, table, earlyWarning);
	        
			// 災保遺屬年金
	        table = this.printDisasterReviewSurvivorPays(caseData, table, earlyWarning);
	        
	        // 申請國保給付記錄資料 (有資料再印)
	        table = this.printNpPays(caseData, table, earlyWarning);

			// 公保養老年金給付
	        table = this.printCivilServantRetiredAnnuityPayList(caseData, table, earlyWarning);
	        
			// 公保養老遺屬年金給付
	        table = this.printCivilServantRetiredSurvivorAnnuityPayList(caseData, table, earlyWarning);
	        
			// 公保死亡遺屬年金給付
	        table = this.printCivilServantDeadSurvivorAnnuityPayList(caseData, table, earlyWarning);
	        
	        

	        // 20101124 kiyomi - mark start
	        // addLine(table);
	        // 20101124 kiyomi - mark end
	        // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
	        addEmptyRow(table, 1);
	        //
	        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	        // // 換了頁就不印分隔線了
	        // deleteRow(table, 1);
	        // document.add(table);
	        // table = addHeader(caseData, false, earlyWarning);
	        // }
	        // else {
	        // deleteRow(table, 1);
	        // addLine(table);
	        // }
	        // ]

	        // 20101124 kiyomi - start
	        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	            // 換了頁就不再塞空白行了

	            deleteRow(table, 1);
	            document.add(table);
	            table = addHeader(caseData, false, earlyWarning);
	        }
	        else {
	            deleteRow(table, 1);
	            addLine(table);
	        }
	        // 20101124 kiyomi - end

	        return table;
		}

		public Table printCivilServantDeadSurvivorAnnuityPayList(DisableReviewRpt01Case caseData, Table table,
				String earlyWarning) throws Exception {
			List<CivilServantReviewRpt01DeadOncePayCase> oncePayList = caseData.getCivilServantDeadSurvivorAnnuityPayList();
			if (oncePayList != null) {
				for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++) { // ... [
					CivilServantReviewRpt01DeadOncePayCase oncePayData = oncePayList.get(nOncePayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請公保養老遺屬年金給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// addEmptyRow(table, 8);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 8);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 8);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					
					//申請人姓名-APPNAME、申請日期-APPDATE、確定永久失能日-EVTRETDATE、失能等級-DISQUALMK、失能編號-CRIINJDP、核付日期-APPISSUEDATE、核付金額-APPISSUEAMT、 結案日期-CLOSEDATE

					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "申請人姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "死亡日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "失能等級", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "失能編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, oncePayData.getAppName(), fontCh12, 0, LEFT); // 申請人姓名
					addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getAppDate()), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 10, 1, oncePayData.getEvtRetDate(), fontCh12, 0, LEFT); // 死亡日期
					addColumn(table, 9, 1, oncePayData.getDisQualMk(), fontCh12, 0, LEFT); // 失能等級
					addColumn(table, 9, 1, oncePayData.getCriinjdp(), fontCh12, 0, LEFT); // 失能編號
					addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getIssueDate()), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "結案日期", fontCh12, 0, LEFT);
					addColumn(table, 37, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, oncePayData.getAppIssueAmt(), fontCh12, 0, LEFT); // 核付金額
					addColumn(table, 9, 1, oncePayData.getCloseDate(), fontCh12, 0, LEFT); // 結案日期
					addColumn(table, 37, 1, " ", fontCh12, 0, LEFT); 
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
    				// ---
    				// 20101124 kiyomi - start
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
    				// 20101124 kiyomi - end
					// 最後一筆印完後空一行 (如果年金給付資料有資料再印)
					if ((nOncePayCount == oncePayList.size() - 1)) {
						
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
			return table;
			
		}

		public Table printCivilServantRetiredSurvivorAnnuityPayList(DisableReviewRpt01Case caseData, Table table,
				String earlyWarning) throws Exception {
			// 公保養老遺屬年金給付 (有資料再印)
			List<CivilServantReviewRpt01DeadOncePayCase> oncePayList = caseData.getCivilServantRetiredSurvivorAnnuityPayList();
			if (oncePayList != null) {
				for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++) { // ... [
					CivilServantReviewRpt01DeadOncePayCase oncePayData = oncePayList.get(nOncePayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請公保養老遺屬年金給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// addEmptyRow(table, 8);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 8);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 8);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					
					//申請人姓名-APPNAME、申請日期-APPDATE、確定永久失能日-EVTRETDATE、失能等級-DISQUALMK、失能編號-CRIINJDP、核付日期-APPISSUEDATE、核付金額-APPISSUEAMT、 結案日期-CLOSEDATE

					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "申請人姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "死亡日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "失能等級", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "失能編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, oncePayData.getAppName(), fontCh12, 0, LEFT); // 申請人姓名
					addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getAppDate()), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 10, 1, oncePayData.getEvtRetDate(), fontCh12, 0, LEFT); // 死亡日期
					addColumn(table, 9, 1, oncePayData.getDisQualMk(), fontCh12, 0, LEFT); // 失能等級
					addColumn(table, 9, 1, oncePayData.getCriinjdp(), fontCh12, 0, LEFT); // 失能編號
					addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getIssueDate()), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "結案日期", fontCh12, 0, LEFT);
					addColumn(table, 37, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, oncePayData.getAppIssueAmt(), fontCh12, 0, LEFT); // 核付金額
					addColumn(table, 9, 1, oncePayData.getCloseDate(), fontCh12, 0, LEFT); // 結案日期
					addColumn(table, 37, 1, " ", fontCh12, 0, LEFT); 
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
    				// ---
    				// 20101124 kiyomi - start
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
    				// 20101124 kiyomi - end
					// 最後一筆印完後空一行 (如果年金給付資料有資料再印)
					if ((nOncePayCount == oncePayList.size() - 1) 
							&& (caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
						
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
			return table;
			
		}

		public Table printCivilServantRetiredAnnuityPayList(DisableReviewRpt01Case caseData, Table table,
				String earlyWarning) throws Exception {
			// 公保養老年金給付
			// 一次給付資料 (有資料再印)
			List<CivilServantReviewRpt01RetirementAnnuityPayCase> oncePayList = caseData.getCivilServantRetiredAnnuityPayList();
			if (oncePayList != null) {
				for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++) { // ... [
					CivilServantReviewRpt01RetirementAnnuityPayCase oncePayData = oncePayList.get(nOncePayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請公保養老年金給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// addEmptyRow(table, 8);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 8);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 8);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					
					//申請人姓名-APPNAME、申請日期-APPDATE、確定永久失能日-EVTRETDATE、失能等級-DISQUALMK、失能編號-CRIINJDP、核付日期-APPISSUEDATE、核付金額-APPISSUEAMT、 結案日期-CLOSEDATE

					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "申請人姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "退休日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "年金起始日", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "首次給付年月", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, oncePayData.getAppName(), fontCh12, 0, LEFT); // 申請人姓名
					addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getAppDate()), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 10, 1, oncePayData.getEvtRetDate(), fontCh12, 0, LEFT); // 退休日期
					addColumn(table, 9, 1, oncePayData.getPensDate(), fontCh12, 0, LEFT); // 年金起始日
					addColumn(table, 9, 1, oncePayData.getPayYm(), fontCh12, 0, LEFT); // 首次給付年月
					addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getIssueDate()), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "結案日期", fontCh12, 0, LEFT);
					addColumn(table, 37, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, oncePayData.getAppIssueAmt(), fontCh12, 0, LEFT); // 核付金額
					addColumn(table, 9, 1, oncePayData.getCloseDate(), fontCh12, 0, LEFT); // 結案日期
					addColumn(table, 37, 1, " ", fontCh12, 0, LEFT); 
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
    				// ---
    				// 20101124 kiyomi - start
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
    				// 20101124 kiyomi - end
					// 最後一筆印完後空一行 (如果年金給付資料有資料再印)
					if ((nOncePayCount == oncePayList.size() - 1) 
							&& (caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
									|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
						
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
			return table;
						
		}


		public Table printNpPays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請國保給付記錄資料 (有資料再印)
			if (caseData.getNpPayList() != null) {
				List<DisableReviewRpt01NpPayDataCase> npPayList = caseData.getNpPayList();
				for (int nNpPayCount = 0; nNpPayCount < npPayList.size(); nNpPayCount++) { // ... [
					DisableReviewRpt01NpPayDataCase npPayData = npPayList.get(nNpPayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						// 國保給付記錄 表頭
						addColumn(table, 58, 1, "申請國保給付記錄：", fontCh12b, 0, LEFT);
						
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// 國保給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					// addEmptyRow(table, 4);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 4);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 4);
					// }
					// 20101124 kiyomi - mark end
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "申請日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "首次給付年月", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "事故日期", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, npPayData.getEvteeName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 11, 1, npPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
					addColumn(table, 11, 1, npPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 11, 1, npPayData.getPayYmString(), fontCh12, 0, LEFT); // 首次給付年月
					addColumn(table, 11, 1, npPayData.getEvtDtString(), fontCh12, 0, LEFT); // 事故日期
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (npPayData.getIssueAmt() != null && npPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 6, 1, formatBigDecimalToInteger(npPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額
					addColumn(table, 6, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, npPayData.getChkDtString(), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 11, 1, npPayData.getAplPayDateString(), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 11, 1, npPayData.getAplPayDateString(), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 11, 1, StringUtils.defaultString(npPayData.getCloseDtString() + ((StringUtils.isNotBlank(npPayData.getCloseReason())) ? " / " + npPayData.getCloseReason() : "")), fontCh12, 0, LEFT); // 不給付日期
					
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// addColumn(table, 7, 1, "合格註記", fontCh12, 0, LEFT);
					// addColumn(table, 10, 1, "人工審核結果", fontCh12, 0, LEFT);
					// addColumn(table, 41, 1, "　", fontCh12, 0, LEFT);
					// // ---
					// addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// addColumn(table, 7, 1, npPayData.getAcceptMark(), fontCh12, 0, LEFT); // 合格註記
					// addColumn(table, 10, 1, npPayData.getManChkFlg(), fontCh12, 0, LEFT); // 人工審核結果
					// addColumn(table, 41, 1, "　", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nNpPayCount == npPayList.size() - 1) && (caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
						// 空白行
						
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
					
				} // ] ... end for (int nNpPayCount = 0; nNpPayCount < npPayList.size(); nNpPayCount++)
				
			}
			return table;

		}

		public Table printJobTrainingPays(DisableReviewRpt01Case caseData, Table table,
				String earlyWarning) throws Exception {
	        // 申請職業訓練生活津貼記錄資料 (有資料再印)
			if (caseData.getVocationalTrainingLivingAllowanceList() != null) {
				List<DisableReviewRpt01JoblessPayDataCase> vocationalTrainingLivingAllowanceList = caseData.getVocationalTrainingLivingAllowanceList();
				for (int nCount = 0; nCount < vocationalTrainingLivingAllowanceList.size(); nCount++) { // ... [
					DisableReviewRpt01JoblessPayDataCase vocationalTrainingLivingAllowanceData = vocationalTrainingLivingAllowanceList.get(nCount);
					
					// 印職業訓練生活津貼記錄表頭
					
					if (nCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 職業訓練生活津貼記錄 表頭
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請職業訓練生活津貼記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// 職業訓練生活津貼記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					// addEmptyRow(table, 4);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 4);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 4);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "受理日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "離職日期", fontCh12, 0, LEFT);
					addColumn(table, 14, 1, "給付起日-迄日", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, vocationalTrainingLivingAllowanceData.getName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 11, 1, vocationalTrainingLivingAllowanceData.getApDteString(), fontCh12, 0, LEFT); // 受理日期
					addColumn(table, 11, 1, vocationalTrainingLivingAllowanceData.getApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 9, 1, vocationalTrainingLivingAllowanceData.getTdteString(), fontCh12, 0, LEFT); // 離職日期
					addColumn(table, 14, 1, vocationalTrainingLivingAllowanceData.getSymDteString() + ((StringUtils.isNotBlank(vocationalTrainingLivingAllowanceData.getTymDte())) ? "-" + vocationalTrainingLivingAllowanceData.getTymDteString() : ""), fontCh12, 0, LEFT); // 給付起日-
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 14, 1, "不給付日期", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (vocationalTrainingLivingAllowanceData.getChkAmt() != null && vocationalTrainingLivingAllowanceData.getChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 11, 1, formatBigDecimalToInteger(vocationalTrainingLivingAllowanceData.getChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 11, 1, vocationalTrainingLivingAllowanceData.getChkDteString(), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 11, 1, vocationalTrainingLivingAllowanceData.getPayDteString(), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 9, 1, vocationalTrainingLivingAllowanceData.getSavDt1String() + ((StringUtils.isNotBlank(vocationalTrainingLivingAllowanceData.getNdcMrk())) ? " / " + vocationalTrainingLivingAllowanceData.getNdcMrk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
					addColumn(table, 14, 1, vocationalTrainingLivingAllowanceData.getNpyDteString(), fontCh12, 0, LEFT); // 不給付日期
					
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nCount == vocationalTrainingLivingAllowanceList.size() - 1) && (caseData.getOldAgePayList().size() > 0
							|| caseData.getDisasterSurvivorPayList().size() > 0
							|| caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
						// 空白行
						
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							// 換了頁就不再塞空白行了
							
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
					}
				} // ] ... end for (int nCount = 0; nCount < vocationalTrainingLivingAllowanceList.size(); nCount++)
				
			}

			return table;

		}

		public Table printJoblessPays(DisableReviewRpt01Case caseData, Table table,
				String earlyWarning) throws Exception {
	        // 申請失業給付記錄資料 (有資料再印)
			if (caseData.getJoblessPayList() != null) {
				List<DisableReviewRpt01JoblessPayDataCase> joblessPayList = caseData.getJoblessPayList();
				for (int nJoblessPayCount = 0; nJoblessPayCount < joblessPayList.size(); nJoblessPayCount++) { // ... [
					DisableReviewRpt01JoblessPayDataCase joblessPayData = joblessPayList.get(nJoblessPayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請失業給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// 失業給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					// addEmptyRow(table, 4);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 4);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 4);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "受理日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "求職日期", fontCh12, 0, LEFT);
					addColumn(table, 14, 1, "給付起日-迄日", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, joblessPayData.getName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 11, 1, joblessPayData.getApDteString(), fontCh12, 0, LEFT); // 受理日期
					addColumn(table, 11, 1, joblessPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 9, 1, joblessPayData.getAprDteString(), fontCh12, 0, LEFT); // 求職日期
					addColumn(table, 14, 1, joblessPayData.getSymDteString() + ((StringUtils.isNotBlank(joblessPayData.getTymDte())) ? "-" + joblessPayData.getTymDteString() : ""), fontCh12, 0, LEFT); // 給付起日-
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 14, 1, "不給付日期", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (joblessPayData.getChkAmt() != null && joblessPayData.getChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 11, 1, formatBigDecimalToInteger(joblessPayData.getChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 11, 1, joblessPayData.getChkDteString(), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 11, 1, joblessPayData.getPayDteString(), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 9, 1, joblessPayData.getSavDt1String() + ((StringUtils.isNotBlank(joblessPayData.getNdcMrk())) ? " / " + joblessPayData.getNdcMrk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
					addColumn(table, 14, 1, joblessPayData.getNpyDteString(), fontCh12, 0, LEFT); // 不給付日期
					
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nJoblessPayCount == joblessPayList.size() - 1) && (caseData.getVocationalTrainingLivingAllowanceList().size() > 0
							|| caseData.getOldAgePayList().size() > 0
							|| caseData.getDisasterSurvivorPayList().size() > 0
							|| caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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

			return table;

		}

		public Table printFarmDiePays(DisableReviewRpt01Case caseData, Table table,
				String earlyWarning) throws Exception {
	        // 申請農保死亡給付記錄資料 (有資料再印)
	        List<DisableReviewRpt01DiePayDataCase> famDiePayList = caseData.getFamDiePayList();
	        for (int nDiePayCount = 0; nDiePayCount < famDiePayList.size(); nDiePayCount++) { // ... [
	            DisableReviewRpt01DiePayDataCase famDiePayData = famDiePayList.get(nDiePayCount);

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
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 58, 1, "申請農保死亡給付記錄：", fontCh12b, 0, LEFT);
	            }
	            else {
	                addEmptyRow(table, 1);

	                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                    deleteRow(table, 1);
	                    document.add(table);
	                    table = addHeader(caseData, false, earlyWarning);
	                }
	                else {
	                    deleteRow(table, 1);
	                    addEmptyRow(table, 1);
	                }
	            }

	            // 20101124 kiyomi - mark start
	            // 死亡給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
	            // addEmptyRow(table, 4);

	            // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	            // deleteRow(table, 4);
	            // document.add(table);
	            // table = addHeader(caseData, false, earlyWarning);
	            // }
	            // else {
	            // deleteRow(table, 4);
	            // }
	            // 20101124 kiyomi - mark end
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "受理日期", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "死亡日期", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 12, 1, StringUtils.defaultString(famDiePayData.getBmEvName()), fontCh12, 0, LEFT); // 事故者姓名

	            addColumn(table, 11, 1, famDiePayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
	            addColumn(table, 11, 1, famDiePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
	            addColumn(table, 11, 1, StringUtils.defaultString(famDiePayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 死亡日期

	            addColumn(table,
	                            11,
	                            1,
	                            StringUtils.defaultString(famDiePayData.getBmLosFmDteString()
	                                            + ((StringUtils.isNotBlank(famDiePayData.getBmLosToDteString())) ? ((StringUtils.isNotBlank(famDiePayData.getBmLosToDteString())) ? (" - " + famDiePayData.getBmLosToDteString()) : famDiePayData
	                                                            .getBmLosToDteString()) : "")), fontCh12, 0, LEFT); // 申請起日
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 12, 1, "核定金額", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            if (famDiePayData.getBmChkAmt() != null && famDiePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
	                addColumn(table, 12, 1, formatBigDecimalToInteger(famDiePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
	            else
	                addColumn(table, 12, 1, " ", fontCh12, 0, LEFT); // 核定金額

	            addColumn(table, 11, 1, StringUtils.defaultString(famDiePayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
	            addColumn(table, 11, 1, StringUtils.defaultString(famDiePayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
	            addColumn(table, 11, 1, famDiePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(famDiePayData.getBmNdocMk())) ? " / " + famDiePayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
	            addColumn(table, 11, 1, StringUtils.defaultString(famDiePayData.getBmNopDateString() + ((StringUtils.isNotBlank(famDiePayData.getBmNdocMk())) ? " / " + famDiePayData.getBmNdocMk() : "")), fontCh12, 0, LEFT); // 不給付日期

	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            // 最後一筆印完後空一行 (其他給付記錄有資料再印)
	            if ((nDiePayCount == famDiePayList.size() - 1) && (caseData.getJoblessPayList().size() > 0
						|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
						|| caseData.getOldAgePayList().size() > 0
						|| caseData.getDisasterSurvivorPayList().size() > 0
						|| caseData.getNpPayList().size() > 0
						|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
						|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
						|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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

			return table;

		}

		public Table printLostPays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請失蹤給付記錄資料 (有資料再印)
			if (caseData.getDisPayList() != null) {
				List<DisableReviewRpt01DiePayDataCase> rptDisPayList = caseData.getDisPayList();
				for (int nDisPayCount = 0; nDisPayCount < rptDisPayList.size(); nDisPayCount++) { // ... [
					DisableReviewRpt01DiePayDataCase disPayData = rptDisPayList.get(nDisPayCount);
					
					// 印失蹤給付記錄表頭
					
					if (nDisPayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 失蹤給付記錄 表頭
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請失蹤給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// 失蹤給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					// addEmptyRow(table, 4);
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 4);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 4);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "受理日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "事故日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "申請起日—迄日", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, StringUtils.defaultString(disPayData.getBmEvName()), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
					addColumn(table, 11, 1, disPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 事故日期
					
					addColumn(table,
							11,
							1,
							StringUtils.defaultString(disPayData.getBmLosFmDteString()
									+ ((StringUtils.isNotBlank(disPayData.getBmLosToDteString())) ? ((StringUtils.isNotBlank(disPayData.getBmLosToDteString())) ? (" - " + disPayData.getBmLosToDteString()) : disPayData
											.getBmLosToDteString()) : "")), fontCh12, 0, LEFT); // 申請起日
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (disPayData.getBmChkAmt() != null && disPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 12, 1, formatBigDecimalToInteger(disPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 12, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 11, 1, disPayData.getBmNrepDateString() + ((StringUtils.isNotBlank(disPayData.getBmNdocMk())) ? " / " + disPayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
					addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmNopDateString() + ((StringUtils.isNotBlank(disPayData.getBmNdocMk())) ? " / " + disPayData.getBmNdocMk() : "")), fontCh12, 0, LEFT); // 不給付日期
					
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nDisPayCount == rptDisPayList.size() - 1) && (caseData.getDisasterLostPayList().size() > 0
							|| caseData.getFamDiePayList().size() > 0
							|| caseData.getJoblessPayList().size() > 0
							|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
							|| caseData.getOldAgePayList().size() > 0
							|| caseData.getDisasterSurvivorPayList().size() > 0
							|| caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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

			return table;
		}

		public Table printDisasterLostPays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請失蹤給付記錄資料 (有資料再印) 災保
			if (caseData.getDisasterLostPayList() != null) {
				List<DisableReviewRpt01DiePayDataCase> rptDisPayList = caseData.getDisasterLostPayList();
				for (int nDisPayCount = 0; nDisPayCount < rptDisPayList.size(); nDisPayCount++) { // ... [
					DisableReviewRpt01DiePayDataCase disPayData = rptDisPayList.get(nDisPayCount);
					
					// 印失蹤給付記錄表頭
					
					if (nDisPayCount == 0) {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
						}
						
						// 失蹤給付記錄 表頭
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請失蹤給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// 失蹤給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					// addEmptyRow(table, 4);
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 4);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 4);
					// }
					// 20101124 kiyomi - mark end
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "受理日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "事故日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "申請起日—迄日", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, StringUtils.defaultString(disPayData.getBmEvName()), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
					addColumn(table, 11, 1, disPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 事故日期
					
					addColumn(table,
							11,
							1,
							StringUtils.defaultString(disPayData.getBmLosFmDteString()
									+ ((StringUtils.isNotBlank(disPayData.getBmLosToDteString())) ? ((StringUtils.isNotBlank(disPayData.getBmLosToDteString())) ? (" - " + disPayData.getBmLosToDteString()) : disPayData
											.getBmLosToDteString()) : "")), fontCh12, 0, LEFT); // 申請起日
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (disPayData.getBmChkAmt() != null && disPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 12, 1, formatBigDecimalToInteger(disPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 12, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 11, 1, disPayData.getBmNrepDateString() + ((StringUtils.isNotBlank(disPayData.getBmNdocMk())) ? " / " + disPayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
					addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmNopDateString() + ((StringUtils.isNotBlank(disPayData.getBmNdocMk())) ? " / " + disPayData.getBmNdocMk() : "")), fontCh12, 0, LEFT); // 不給付日期
					
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nDisPayCount == rptDisPayList.size() - 1) && (caseData.getFamDiePayList().size() > 0
							|| caseData.getJoblessPayList().size() > 0
							|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
							|| caseData.getOldAgePayList().size() > 0
							|| caseData.getDisasterSurvivorPayList().size() > 0
							|| caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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

			return table;

		}

		public Table printDiePays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請死亡給付記錄資料 (有資料再印)
			if (caseData.getDiePayList() != null) {
				List<DisableReviewRpt01DiePayDataCase> diePayList = caseData.getDiePayList();
				for (int nDiePayCount = 0; nDiePayCount < diePayList.size(); nDiePayCount++) { // ... [
					DisableReviewRpt01DiePayDataCase diePayData = diePayList.get(nDiePayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請死亡給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - mark start
					// 死亡給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
					// addEmptyRow(table, 4);
					
					// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
					// deleteRow(table, 4);
					// document.add(table);
					// table = addHeader(caseData, false, earlyWarning);
					// }
					// else {
					// deleteRow(table, 4);
					// }
					// 20101124 kiyomi - mark end
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "死亡日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "申請項目", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, diePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
					addColumn(table, 10, 1, diePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 死亡日期
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmEvType()), fontCh12, 0, LEFT); // 傷病分類
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmDeaapItem()), fontCh12, 0, LEFT); // 申請項目
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核定金額 ", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核定日期 ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
					addColumn(table, 5, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (diePayData.getBmChkAmt() != null && diePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 10, 1, formatBigDecimalToInteger(diePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 10, 1, StringUtils.defaultString(diePayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
					
					addColumn(table, 11, 1,
							diePayData.getBmNrepDateString()
							+ ((StringUtils.isNotBlank(diePayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(diePayData.getBmNrepDateString())) ? (" / " + diePayData.getBmNdocMk()) : diePayData.getBmNdocMk()) : ""), fontCh12,
							0, LEFT); // 補件日期/註記
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmNopDateString()), fontCh12, 0, LEFT); // 不給付日期
					
					addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nDiePayCount == diePayList.size() - 1) && (caseData.getDisasterDiePayList().size() > 0
							|| caseData.getDisasterDieForDiseaseAfterQuitPayList().size() > 0
							|| caseData.getDisasterDieWithoutPayList().size() > 0
							|| caseData.getSurvivorPayList().size() > 0
							|| caseData.getDisPayList().size() > 0
							|| caseData.getDisasterLostPayList().size() > 0
							|| caseData.getFamDiePayList().size() > 0
							|| caseData.getJoblessPayList().size() > 0
							|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
							|| caseData.getOldAgePayList().size() > 0
							|| caseData.getDisasterSurvivorPayList().size() > 0
							|| caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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
			return table;

		}

		public Table printDisasterDiePays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請死亡給付記錄資料 (有資料再印) 災保
			if (caseData.getDisasterDiePayList() != null) {
				List<DisableReviewRpt01DiePayDataCase> diePayList = caseData.getDisasterDiePayList();
				for (int nDiePayCount = 0; nDiePayCount < diePayList.size(); nDiePayCount++) { // ... [
					DisableReviewRpt01DiePayDataCase diePayData = diePayList.get(nDiePayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請災保本人死亡給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "死亡日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "申請項目", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, diePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
					addColumn(table, 10, 1, diePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 死亡日期
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmEvType()), fontCh12, 0, LEFT); // 傷病分類
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmDeaapItem()), fontCh12, 0, LEFT); // 申請項目
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核定金額 ", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核定日期 ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
					addColumn(table, 5, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (diePayData.getBmChkAmt() != null && diePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 10, 1, formatBigDecimalToInteger(diePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 10, 1, StringUtils.defaultString(diePayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
					
					addColumn(table, 11, 1,
							diePayData.getBmNrepDateString()
							+ ((StringUtils.isNotBlank(diePayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(diePayData.getBmNrepDateString())) ? (" / " + diePayData.getBmNdocMk()) : diePayData.getBmNdocMk()) : ""), fontCh12,
							0, LEFT); // 補件日期/註記
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmNopDateString()), fontCh12, 0, LEFT); // 不給付日期
					
					addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nDiePayCount == diePayList.size() - 1) && (caseData.getDisasterDieForDiseaseAfterQuitPayList().size() > 0
							|| caseData.getDisasterDieWithoutPayList().size() > 0
							|| caseData.getSurvivorPayList().size() > 0
							|| caseData.getDisPayList().size() > 0
							|| caseData.getDisasterLostPayList().size() > 0
							|| caseData.getFamDiePayList().size() > 0
							|| caseData.getJoblessPayList().size() > 0
							|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
							|| caseData.getOldAgePayList().size() > 0
							|| caseData.getDisasterSurvivorPayList().size() > 0
							|| caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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

			return table;

		}

		public Table printDisasterDieForDiseaseAfterQuitPays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請退保後職業病死亡津貼 (有資料再印) 災保
	        List<DisableReviewRpt01DiePayDataCase> diePayList = caseData.getDisasterDieForDiseaseAfterQuitPayList();
	        for (int nDiePayCount = 0; nDiePayCount < diePayList.size(); nDiePayCount++) { // ... [
	        	DisableReviewRpt01DiePayDataCase diePayData = diePayList.get(nDiePayCount);

	            // 印退保後職業病死亡津貼記錄表頭

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

	                // 退保後職業病死亡津貼記錄 表頭
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 58, 1, "申請災保退保後職業病死亡津貼記錄：", fontCh12b, 0, LEFT);
	            }
	            else {
	                addEmptyRow(table, 1);

	                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                    deleteRow(table, 1);
	                    document.add(table);
	                    table = addHeader(caseData, false, earlyWarning);
	                }
	                else {
	                    deleteRow(table, 1);
	                    addEmptyRow(table, 1);
	                }
	            }

	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "死亡日期", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "申請項目", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, diePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名

	            addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
	            addColumn(table, 10, 1, diePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
	            addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 死亡日期
	            addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmEvType()), fontCh12, 0, LEFT); // 傷病分類
	            addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmDeaapItem()), fontCh12, 0, LEFT); // 申請項目
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "核定金額 ", fontCh12, 0, LEFT);
	            addColumn(table, 9, 1, "核定日期 ", fontCh12, 0, LEFT);
	            addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
	            addColumn(table, 5, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            if (diePayData.getBmChkAmt() != null && diePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
	                addColumn(table, 10, 1, formatBigDecimalToInteger(diePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
	            else
	                addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額

	            addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
	            addColumn(table, 10, 1, StringUtils.defaultString(diePayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期

	            addColumn(table, 11, 1,
	                            diePayData.getBmNrepDateString()
	                                            + ((StringUtils.isNotBlank(diePayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(diePayData.getBmNrepDateString())) ? (" / " + diePayData.getBmNdocMk()) : diePayData.getBmNdocMk()) : ""), fontCh12,
	                            0, LEFT); // 補件日期/註記
	            addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmNopDateString()), fontCh12, 0, LEFT); // 不給付日期

	            addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

	            // 最後一筆印完後空一行 (其他給付記錄有資料再印)
	            if ((nDiePayCount == diePayList.size() - 1) && (caseData.getDisasterDieWithoutPayList().size() > 0
						|| caseData.getSurvivorPayList().size() > 0
						|| caseData.getDisPayList().size() > 0
						|| caseData.getDisasterLostPayList().size() > 0
						|| caseData.getFamDiePayList().size() > 0
						|| caseData.getJoblessPayList().size() > 0
						|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
						|| caseData.getOldAgePayList().size() > 0
						|| caseData.getDisasterSurvivorPayList().size() > 0
						|| caseData.getNpPayList().size() > 0
						|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
						|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
						|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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

			return table;

		}

		public Table printDisasterDieWithoutPays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請未加保死亡補助 (有資料再印) 災保
			if (caseData.getDisasterDieWithoutPayList() != null) {
				List<DisableReviewRpt01DiePayDataCase> diePayList = caseData.getDisasterDieWithoutPayList();
				for (int nDiePayCount = 0; nDiePayCount < diePayList.size(); nDiePayCount++) { // ... [
					DisableReviewRpt01DiePayDataCase diePayData = diePayList.get(nDiePayCount);
					
					// 印未加保死亡補助記錄表頭
					
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
						
						// 未加保死亡補助記錄 表頭
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請災保未加保死亡補助記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "死亡日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "申請項目", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, diePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
					addColumn(table, 10, 1, diePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 死亡日期
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmEvType()), fontCh12, 0, LEFT); // 傷病分類
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmDeaapItem()), fontCh12, 0, LEFT); // 申請項目
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核定金額 ", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核定日期 ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
					addColumn(table, 5, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (diePayData.getBmChkAmt() != null && diePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 10, 1, formatBigDecimalToInteger(diePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
					addColumn(table, 10, 1, StringUtils.defaultString(diePayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
					
					addColumn(table, 11, 1,
							diePayData.getBmNrepDateString()
							+ ((StringUtils.isNotBlank(diePayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(diePayData.getBmNrepDateString())) ? (" / " + diePayData.getBmNdocMk()) : diePayData.getBmNdocMk()) : ""), fontCh12,
							0, LEFT); // 補件日期/註記
					addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmNopDateString()), fontCh12, 0, LEFT); // 不給付日期
					
					addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nDiePayCount == diePayList.size() - 1) && (caseData.getSurvivorPayList().size() > 0
							|| caseData.getDisPayList().size() > 0
							|| caseData.getDisasterLostPayList().size() > 0
							|| caseData.getFamDiePayList().size() > 0
							|| caseData.getJoblessPayList().size() > 0
							|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
							|| caseData.getOldAgePayList().size() > 0
							|| caseData.getDisasterSurvivorPayList().size() > 0
							|| caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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

			return table;

		}

		public Table printOldAgePays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請老年給付記錄 (有資料再印)
	        List<DisableReviewRpt01OldPayDataCase> oldAgePayList = caseData.getOldPayList();
	        for (int nInjuryPayCount = 0; nInjuryPayCount < oldAgePayList.size(); nInjuryPayCount++) { // ... [
	            DisableReviewRpt01OldPayDataCase injuryPayData = oldAgePayList.get(nInjuryPayCount);

	            // 印老年給付記錄表頭
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
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 58, 1, "申請老年給付記錄：", fontCh12b, 0, LEFT);
	            }
	            else {
	                addEmptyRow(table, 1);

	                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                    deleteRow(table, 1);
	                    document.add(table);
	                    table = addHeader(caseData, false, earlyWarning);
	                }
	                else {
	                    deleteRow(table, 1);
	                    addEmptyRow(table, 1);
	                }
	            }

	            // 20101124 kiyomi - mark start
	            // 老年給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
	            // addEmptyRow(table, 4);

	            // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	            // deleteRow(table, 4);
	            // document.add(table);
	            // table = addHeader(caseData, false, earlyWarning);
	            // }
	            // else {
	            // deleteRow(table, 4);
	            // }
	            // 20101124 kiyomi - mark end
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "受理日期", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "事故日期 ", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "核定金額 ", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 12, 1, injuryPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名

	            addColumn(table, 11, 1, injuryPayData.getBmApDteString(), fontCh12, 0, LEFT); // 申請日期
	            addColumn(table, 11, 1, injuryPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
	            addColumn(table, 11, 1, StringUtils.defaultString(injuryPayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 事故日期
	            if (injuryPayData.getBmChkAmt() != null && injuryPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
	                addColumn(table, 11, 1, formatBigDecimalToInteger(injuryPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
	            else
	                addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 核定金額
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 12, 1, "核定日期 ", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
	            addColumn(table, 11, 1, "　", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            // ---
	            // 20101124 kiyomi - start
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
	            // 20101124 kiyomi - end
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	            addColumn(table, 12, 1, injuryPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核付日期
	            addColumn(table, 11, 1, injuryPayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
	            addColumn(table, 11, 1, injuryPayData.getBmNrepDateString()
	                            + ((StringUtils.isNotBlank(injuryPayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNrepDateString())) ? (" / " + injuryPayData.getBmNdocMk()) : injuryPayData.getBmNdocMk()) : ""), fontCh12, 0,
	                            LEFT); // 補件日期/註記
	            addColumn(table, 11, 1, injuryPayData.getBmNopDateString()
	                            + ((StringUtils.isNotBlank(injuryPayData.getBmNopMark())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNopMark())) ? (" / " + injuryPayData.getBmNopMark()) : injuryPayData.getBmNopMark()) : ""), fontCh12, 0, LEFT); // 不給付日期

	            addColumn(table, 11, 1, "　", fontCh12, 0, LEFT);
	            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

	            // 最後一筆印完後空一行 (其他給付記錄有資料再印)
	            if ((nInjuryPayCount == oldAgePayList.size() - 1) && (caseData.getDiePayList().size() > 0
						|| caseData.getDisasterDiePayList().size() > 0
						|| caseData.getDisasterDieForDiseaseAfterQuitPayList().size() > 0
						|| caseData.getDisasterDieWithoutPayList().size() > 0
						|| caseData.getSurvivorPayList().size() > 0
						|| caseData.getDisPayList().size() > 0
						|| caseData.getDisasterLostPayList().size() > 0
						|| caseData.getFamDiePayList().size() > 0
						|| caseData.getJoblessPayList().size() > 0
						|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
						|| caseData.getOldAgePayList().size() > 0
						|| caseData.getDisasterSurvivorPayList().size() > 0
						|| caseData.getNpPayList().size() > 0
						|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
						|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
						|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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

			return table;
			
		}

		public Table printSurvivorPays(DisableReviewRpt01Case caseData, Table table,
				String earlyWarning) throws Exception {
	        // 申請遺屬年金給付(有資料再印)
			if (caseData.getSurvivorPayList() != null) {
				List<DisableReviewRpt01SurvivorPayDataCase> survivorPayCaseList = caseData.getSurvivorPayList();
				if (caseData.getSurvivorPayList() != null) {
					for (int nDisablePayCount = 0; nDisablePayCount < survivorPayCaseList.size(); nDisablePayCount++) { // ... [
						
						DisableReviewRpt01SurvivorPayDataCase disablePayData = survivorPayCaseList.get(nDisablePayCount);
						
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
							addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
							addColumn(table, 58, 1, "申請遺屬年金給付記錄：", fontCh12b, 0, LEFT);
						}
						else {
							addEmptyRow(table, 1);
							
							if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
								deleteRow(table, 1);
								document.add(table);
								table = addHeader(caseData, false, earlyWarning);
							}
							else {
								deleteRow(table, 1);
								addEmptyRow(table, 1);
							}
						}
						
						// 20101124 kiyomi - mark start
						// 失能給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
						// addEmptyRow(table, 4);
						
						// if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
						// deleteRow(table, 4);
						// document.add(table);
						// table = addHeader(caseData, false, earlyWarning);
						// }
						// else {
						// deleteRow(table, 4);
						// }
						// 20101124 kiyomi - mark end
						// 20101124 kiyomi - start
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
						// 20101124 kiyomi - end
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
						addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
						addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
						addColumn(table, 9, 1, "首次給付年月", fontCh12, 0, LEFT);
						addColumn(table, 9, 1, "死亡日期 ", fontCh12, 0, LEFT);
						addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						// ---
						// 20101124 kiyomi - start
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
						// 20101124 kiyomi - end
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 10, 1, disablePayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
						
						addColumn(table, 9, 1, disablePayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
						addColumn(table, 10, 1, disablePayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
						addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getPayYmsString()) + ((StringUtils.isNotBlank(disablePayData.getPayYmsString())) ? "-" + StringUtils.defaultString(disablePayData.getPayYmString()) : ""),
								fontCh12, 0, LEFT); // 首次給付年月
						addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getEvtJobDateString()), fontCh12, 0, LEFT); // 死亡日期
						addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getEvTyp()), fontCh12, 0, LEFT); // 傷病分類
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						// ---
						// 20101124 kiyomi - start
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
						// 20101124 kiyomi - end
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
						addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
						addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
						addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
						addColumn(table, 11, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
						addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						// ---
						
						// 20101124 kiyomi - start
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
						// 20101124 kiyomi - end
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						if (disablePayData.getIssueAmt() != null && disablePayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
							addColumn(table, 10, 1, formatBigDecimalToInteger(disablePayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
						else
							addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額
						
						addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getChkDateString()), fontCh12, 0, LEFT); // 核定日期
						addColumn(table, 10, 1, StringUtils.defaultString(disablePayData.getAplpayDateString()), fontCh12, 0, LEFT); // 核付日期
						addColumn(table,
								9,
								1,
								disablePayData.getProdateString()
								+ ((StringUtils.isNotBlank(disablePayData.getNdomk1())) ? ((StringUtils.isNotBlank(disablePayData.getNdomk1())) ? (" / " + disablePayData.getNdomk1()) : disablePayData.getNdomk1()) : ""),
								fontCh12, 0, LEFT); // 補件日期/註記
						addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getCloseDateString()) + ((StringUtils.isNotBlank(disablePayData.getCloseCause())) ? " / " + disablePayData.getCloseCause() : ""), fontCh12, 0, LEFT); // 結案日期/原因
						addColumn(table, 9, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						// 最後一筆印完後空一行 (其他給付記錄有資料再印)
						if ((nDisablePayCount == survivorPayCaseList.size() - 1) && (caseData.getDisPayList().size() > 0
								|| caseData.getDisasterLostPayList().size() > 0
								|| caseData.getFamDiePayList().size() > 0
								|| caseData.getJoblessPayList().size() > 0
								|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
								|| caseData.getOldAgePayList().size() > 0
								|| caseData.getDisasterSurvivorPayList().size() > 0
								|| caseData.getNpPayList().size() > 0
								|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
								|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
								|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
							// 空白行
							
							addEmptyRow(table, 1);
							
							if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
								// 換了頁就不再塞空白行了
								
								deleteRow(table, 1);
								document.add(table);
								table = addHeader(caseData, false, earlyWarning);
							}
						}
					}
				}// ] ... end for (int nDisablePayCount = 0; nDisablePayCount < disablePayList.size(); nDisablePayCount++)
				
			}

			return table;

		}

		public Table printDisasterReviewSurvivorPays(DisableReviewRpt01Case caseData, Table table,
				String earlyWarning) throws Exception {
	        // 申請遺屬年金給付(有資料再印)
	        List<DisableReviewRpt01SurvivorPayDataCase> survivorPayCaseList = caseData.getDisasterSurvivorPayList();
	        if (caseData.getSurvivorPayList() != null) {
	            for (int nDisablePayCount = 0; nDisablePayCount < survivorPayCaseList.size(); nDisablePayCount++) { // ... [

	                DisableReviewRpt01SurvivorPayDataCase disablePayData = survivorPayCaseList.get(nDisablePayCount);

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
	                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                    addColumn(table, 58, 1, "申請遺屬年金給付記錄：", fontCh12b, 0, LEFT);
	                }
	                else {
	                    addEmptyRow(table, 1);

	                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                        deleteRow(table, 1);
	                        document.add(table);
	                        table = addHeader(caseData, false, earlyWarning);
	                    }
	                    else {
	                        deleteRow(table, 1);
	                        addEmptyRow(table, 1);
	                    }
	                }

	                // 20101124 kiyomi - mark start
	                // 失能給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
	                // addEmptyRow(table, 4);

	                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                // deleteRow(table, 4);
	                // document.add(table);
	                // table = addHeader(caseData, false, earlyWarning);
	                // }
	                // else {
	                // deleteRow(table, 4);
	                // }
	                // 20101124 kiyomi - mark end
	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
	                addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "首次給付年月", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "死亡日期 ", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                // ---
	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 10, 1, disablePayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名

	                addColumn(table, 9, 1, disablePayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
	                addColumn(table, 10, 1, disablePayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
	                addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getPayYmsString()) + ((StringUtils.isNotBlank(disablePayData.getPayYmsString())) ? "-" + StringUtils.defaultString(disablePayData.getPayYmString()) : ""),
	                                fontCh12, 0, LEFT); // 首次給付年月
	                addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getEvtJobDateString()), fontCh12, 0, LEFT); // 死亡日期
	                addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getEvTyp()), fontCh12, 0, LEFT); // 傷病分類
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                // ---
	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
	                addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
	                addColumn(table, 11, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
	                addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                // ---

	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                if (disablePayData.getIssueAmt() != null && disablePayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
	                    addColumn(table, 10, 1, formatBigDecimalToInteger(disablePayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
	                else
	                    addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額

	                addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getChkDateString()), fontCh12, 0, LEFT); // 核定日期
	                addColumn(table, 10, 1, StringUtils.defaultString(disablePayData.getAplpayDateString()), fontCh12, 0, LEFT); // 核付日期
	                addColumn(table,
	                                9,
	                                1,
	                                disablePayData.getProdateString()
	                                                + ((StringUtils.isNotBlank(disablePayData.getNdomk1())) ? ((StringUtils.isNotBlank(disablePayData.getNdomk1())) ? (" / " + disablePayData.getNdomk1()) : disablePayData.getNdomk1()) : ""),
	                                fontCh12, 0, LEFT); // 補件日期/註記
	                addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getCloseDateString()) + ((StringUtils.isNotBlank(disablePayData.getCloseCause())) ? " / " + disablePayData.getCloseCause() : ""), fontCh12, 0, LEFT); // 結案日期/原因
	                addColumn(table, 9, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                // 最後一筆印完後空一行 (其他給付記錄有資料再印)
	                if ((nDisablePayCount == survivorPayCaseList.size() - 1) && (caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
	                    // 空白行

	                    addEmptyRow(table, 1);

	                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                        // 換了頁就不再塞空白行了

	                        deleteRow(table, 1);
	                        document.add(table);
	                        table = addHeader(caseData, false, earlyWarning);
	                    }
	                }
	            }
	        }// ] ... end for (int nDisablePayCount = 0; nDisablePayCount < disablePayList.size(); nDisablePayCount++)

			return table;

		}

		public Table printOldAgeAnnuitys(DisableReviewRpt01Case caseData, Table table,
				String earlyWarning) throws Exception {
	        // 申請老年年金給付(有資料再印)
	        List<DisableReviewRpt01OldAgePayDataCase> oldAgePayCaseList = caseData.getOldAgePayList();
	        if (caseData.getOldAgePayList() != null) {
	            for (int nDisablePayCount = 0; nDisablePayCount < oldAgePayCaseList.size(); nDisablePayCount++) { // ... [

	                DisableReviewRpt01OldAgePayDataCase disablePayData = oldAgePayCaseList.get(nDisablePayCount);

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
	                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                    addColumn(table, 58, 1, "申請老年年金給付記錄：", fontCh12b, 0, LEFT);
	                }
	                else {
	                    addEmptyRow(table, 1);

	                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                        deleteRow(table, 1);
	                        document.add(table);
	                        table = addHeader(caseData, false, earlyWarning);
	                    }
	                    else {
	                        deleteRow(table, 1);
	                        addEmptyRow(table, 1);
	                    }
	                }

	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
	                addColumn(table, 11, 1, "申請日期", fontCh12, 0, LEFT);
	                addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
	                addColumn(table, 11, 1, "首次給付年月", fontCh12, 0, LEFT);
	                addColumn(table, 11, 1, "事故日期", fontCh12, 0, LEFT);
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                // ---
	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 12, 1, disablePayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名

	                addColumn(table, 11, 1, disablePayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
	                addColumn(table, 11, 1, disablePayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
	                addColumn(table, 11, 1, StringUtils.defaultString(disablePayData.getPayYmsString()) + ((StringUtils.isNotBlank(disablePayData.getPayYmsString())) ? "-" + StringUtils.defaultString(disablePayData.getPayYmString()) : ""),
	                                fontCh12, 0, LEFT); // 首次給付年月
	                addColumn(table, 11, 1, StringUtils.defaultString(disablePayData.getEvtJobDateString()), fontCh12, 0, LEFT); // 事故日期
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                // ---
	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 12, 1, "核定金額", fontCh12, 0, LEFT);
	                addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
	                addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
	                addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
	                addColumn(table, 11, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                // ---

	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                if (disablePayData.getIssueAmt() != null && disablePayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
	                    addColumn(table, 12, 1, formatBigDecimalToInteger(disablePayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
	                else
	                    addColumn(table, 12, 1, " ", fontCh12, 0, LEFT); // 核定金額

	                addColumn(table, 11, 1, StringUtils.defaultString(disablePayData.getChkDateString()), fontCh12, 0, LEFT); // 核定日期
	                addColumn(table, 11, 1, StringUtils.defaultString(disablePayData.getAplpayDateString()), fontCh12, 0, LEFT); // 核付日期
	                addColumn(table,
	                                11,
	                                1,
	                                disablePayData.getProdateString()
	                                                + ((StringUtils.isNotBlank(disablePayData.getNdomk1())) ? ((StringUtils.isNotBlank(disablePayData.getNdomk1())) ? (" / " + disablePayData.getNdomk1()) : disablePayData.getNdomk1()) : ""),
	                                fontCh12, 0, LEFT); // 補件日期/註記
	                addColumn(table, 11, 1, StringUtils.defaultString(disablePayData.getCloseDateString()) + ((StringUtils.isNotBlank(disablePayData.getCloseCause())) ? " / " + disablePayData.getCloseCause() : ""), fontCh12, 0, LEFT); // 結案日期/原因

	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                // 最後一筆印完後空一行 (其他給付記錄有資料再印)
	                if ((nDisablePayCount == oldAgePayCaseList.size() - 1) && (caseData.getDisasterSurvivorPayList().size() > 0
							|| caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
	                    // 空白行

	                    addEmptyRow(table, 1);

	                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                        // 換了頁就不再塞空白行了

	                        deleteRow(table, 1);
	                        document.add(table);
	                        table = addHeader(caseData, false, earlyWarning);
	                    }
	                }
	            }
	        }// ] ... end for (int nDisablePayCount = 0; nDisablePayCount < disablePayList.size(); nDisablePayCount++)
			
			return table;

		}

		public Table printInjuryPays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請傷病給付記錄資料 (有資料再印)
			if (caseData.getInjuryPayList() != null) {
				List<DisableReviewRpt01InjuryPayDataCase> injuryPayList = caseData.getInjuryPayList();
				for (int nInjuryPayCount = 0; nInjuryPayCount < injuryPayList.size(); nInjuryPayCount++) { // ... [
					DisableReviewRpt01InjuryPayDataCase injuryPayData = injuryPayList.get(nInjuryPayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請傷病給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "事故日期", fontCh12, 0, LEFT);
					addColumn(table, 6, 1, "傷病分類", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, "給付起日-迄日", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh8, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, injuryPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 9, 1, injuryPayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
					addColumn(table, 10, 1, injuryPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 9, 1, injuryPayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期
					addColumn(table, 6, 1, injuryPayData.getBmEvType(), fontCh12, 0, LEFT); // 傷病分類
					// addColumn(table, 12, 1, injuryPayData.getBmInjPfmDteString() + ((StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) ? "-" +injuryPayData.getBmInjPtoDteString(): ""), fontCh12, 0, LEFT); // 給付起日-迄日
					addColumn(table, 12, 1, injuryPayData.getBmInjPfmDteString(), fontCh12, 0, LEFT); // 給付起日-迄日
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20111012 by Kiyomi 將起日-迄日中的迄日另外獨立一行列印
					
					if (StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) {
						// 20101124 kiyomi - start
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
						// 20101124 kiyomi - end
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 事故者姓名
						
						addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 受理日期
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 受理編號
						addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 事故日期
						addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 傷病分類
						addColumn(table, 12, 1, ((StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) ? "-" + injuryPayData.getBmInjPtoDteString() : ""), fontCh12, 0, LEFT); // 給付起日-迄日
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					}
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (injuryPayData.getBmChkAmt() != null && injuryPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 10, 1, formatBigDecimalToInteger(injuryPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 9, 1, injuryPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
					
					addColumn(table, 10, 1, injuryPayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 9, 1, injuryPayData.getBmNrepDateString()
							+ ((StringUtils.isNotBlank(injuryPayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNrepDateString())) ? (" / " + injuryPayData.getBmNdocMk()) : injuryPayData.getBmNdocMk()) : ""), fontCh12, 0,
							LEFT); // 補件日期/註記
					addColumn(table, 9, 1, injuryPayData.getBmNopDateString()
							+ ((StringUtils.isNotBlank(injuryPayData.getBmNopMark())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNopDateString())) ? (" / " + injuryPayData.getBmNopMark()) : injuryPayData.getBmNopMark()) : ""), fontCh12,
							0, LEFT); // 不給付日期
					
					addColumn(table, 9, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nInjuryPayCount == injuryPayList.size() - 1) && (caseData.getDisasterInjuryPayList().size() > 0
							|| caseData.getDisasterInjuryCarePayList().size() > 0
							|| caseData.getOldPayList().size() > 0
							|| caseData.getDiePayList().size() > 0
							|| caseData.getDisasterDiePayList().size() > 0
							|| caseData.getDisasterDieForDiseaseAfterQuitPayList().size() > 0
							|| caseData.getDisasterDieWithoutPayList().size() > 0
							|| caseData.getSurvivorPayList().size() > 0
							|| caseData.getDisPayList().size() > 0
							|| caseData.getDisasterLostPayList().size() > 0
							|| caseData.getFamDiePayList().size() > 0
							|| caseData.getJoblessPayList().size() > 0
							|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
							|| caseData.getOldAgePayList().size() > 0
							|| caseData.getDisasterSurvivorPayList().size() > 0
							|| caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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

			return table;
			
		}

		public Table printDisasterReviewInjuryPays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請傷病給付記錄資料 (有資料再印) 災保
			if (caseData.getDisasterInjuryPayList() != null) {
				List<DisableReviewRpt01InjuryPayDataCase> injuryPayList = caseData.getDisasterInjuryPayList();
				for (int nInjuryPayCount = 0; nInjuryPayCount < injuryPayList.size(); nInjuryPayCount++) { // ... [
					DisableReviewRpt01InjuryPayDataCase injuryPayData = injuryPayList.get(nInjuryPayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請災保傷病給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "事故日期", fontCh12, 0, LEFT);
					addColumn(table, 6, 1, "傷病分類", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, "給付起日-迄日", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh8, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, injuryPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 9, 1, injuryPayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
					addColumn(table, 10, 1, injuryPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 9, 1, injuryPayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期
					addColumn(table, 6, 1, injuryPayData.getBmEvType(), fontCh12, 0, LEFT); // 傷病分類
					// addColumn(table, 12, 1, injuryPayData.getBmInjPfmDteString() + ((StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) ? "-" +injuryPayData.getBmInjPtoDteString(): ""), fontCh12, 0, LEFT); // 給付起日-迄日
					addColumn(table, 12, 1, injuryPayData.getBmInjPfmDteString(), fontCh12, 0, LEFT); // 給付起日-迄日
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20111012 by Kiyomi 將起日-迄日中的迄日另外獨立一行列印
					
					if (StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) {
						// 20101124 kiyomi - start
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
						// 20101124 kiyomi - end
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 事故者姓名
						
						addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 受理日期
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 受理編號
						addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 事故日期
						addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 傷病分類
						addColumn(table, 12, 1, ((StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) ? "-" + injuryPayData.getBmInjPtoDteString() : ""), fontCh12, 0, LEFT); // 給付起日-迄日
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					}
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (injuryPayData.getBmChkAmt() != null && injuryPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 10, 1, formatBigDecimalToInteger(injuryPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 9, 1, injuryPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
					
					addColumn(table, 10, 1, injuryPayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 9, 1, injuryPayData.getBmNrepDateString()
							+ ((StringUtils.isNotBlank(injuryPayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNrepDateString())) ? (" / " + injuryPayData.getBmNdocMk()) : injuryPayData.getBmNdocMk()) : ""), fontCh12, 0,
							LEFT); // 補件日期/註記
					addColumn(table, 9, 1, injuryPayData.getBmNopDateString()
							+ ((StringUtils.isNotBlank(injuryPayData.getBmNopMark())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNopDateString())) ? (" / " + injuryPayData.getBmNopMark()) : injuryPayData.getBmNopMark()) : ""), fontCh12,
							0, LEFT); // 不給付日期
					
					addColumn(table, 9, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nInjuryPayCount == injuryPayList.size() - 1) && (caseData.getDisasterInjuryCarePayList().size() > 0
							|| caseData.getOldPayList().size() > 0
							|| caseData.getDiePayList().size() > 0
							|| caseData.getDisasterDiePayList().size() > 0
							|| caseData.getDisasterDieForDiseaseAfterQuitPayList().size() > 0
							|| caseData.getDisasterDieWithoutPayList().size() > 0
							|| caseData.getSurvivorPayList().size() > 0
							|| caseData.getDisPayList().size() > 0
							|| caseData.getDisasterLostPayList().size() > 0
							|| caseData.getFamDiePayList().size() > 0
							|| caseData.getJoblessPayList().size() > 0
							|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
							|| caseData.getOldAgePayList().size() > 0
							|| caseData.getDisasterSurvivorPayList().size() > 0
							|| caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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

			return table;
			
		}

		public Table printDisasterReviewInjuryCarePays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請傷病給付記錄資料 (有資料再印) 災保
			if (caseData.getDisasterInjuryCarePayList() != null) {
				List<DisableReviewRpt01InjuryPayDataCase> injuryPayList = caseData.getDisasterInjuryCarePayList();
				for (int nInjuryPayCount = 0; nInjuryPayCount < injuryPayList.size(); nInjuryPayCount++) { // ... [
					DisableReviewRpt01InjuryPayDataCase injuryPayData = injuryPayList.get(nInjuryPayCount);
					
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
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 58, 1, "申請災保傷病給付記錄：", fontCh12b, 0, LEFT);
					}
					else {
						addEmptyRow(table, 1);
						
						if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
							deleteRow(table, 1);
							document.add(table);
							table = addHeader(caseData, false, earlyWarning);
						}
						else {
							deleteRow(table, 1);
							addEmptyRow(table, 1);
						}
					}
					
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "事故日期", fontCh12, 0, LEFT);
					addColumn(table, 6, 1, "傷病分類", fontCh12, 0, LEFT);
					addColumn(table, 12, 1, "給付起日-迄日", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh8, 0, LEFT);
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, injuryPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
					
					addColumn(table, 9, 1, injuryPayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
					addColumn(table, 10, 1, injuryPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
					addColumn(table, 9, 1, injuryPayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期
					addColumn(table, 6, 1, injuryPayData.getBmEvType(), fontCh12, 0, LEFT); // 傷病分類
					// addColumn(table, 12, 1, injuryPayData.getBmInjPfmDteString() + ((StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) ? "-" +injuryPayData.getBmInjPtoDteString(): ""), fontCh12, 0, LEFT); // 給付起日-迄日
					addColumn(table, 12, 1, injuryPayData.getBmInjPfmDteString(), fontCh12, 0, LEFT); // 給付起日-迄日
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					
					// ---
					// 20111012 by Kiyomi 將起日-迄日中的迄日另外獨立一行列印
					
					if (StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) {
						// 20101124 kiyomi - start
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
						// 20101124 kiyomi - end
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 事故者姓名
						
						addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 受理日期
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 受理編號
						addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 事故日期
						addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 傷病分類
						addColumn(table, 12, 1, ((StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) ? "-" + injuryPayData.getBmInjPtoDteString() : ""), fontCh12, 0, LEFT); // 給付起日-迄日
						addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					}
					
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
					addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
					addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
					addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
					addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// ---
					// 20101124 kiyomi - start
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
					// 20101124 kiyomi - end
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					if (injuryPayData.getBmChkAmt() != null && injuryPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
						addColumn(table, 10, 1, formatBigDecimalToInteger(injuryPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
					else
						addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額
					
					addColumn(table, 9, 1, injuryPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
					
					addColumn(table, 10, 1, injuryPayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
					addColumn(table, 9, 1, injuryPayData.getBmNrepDateString()
							+ ((StringUtils.isNotBlank(injuryPayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNrepDateString())) ? (" / " + injuryPayData.getBmNdocMk()) : injuryPayData.getBmNdocMk()) : ""), fontCh12, 0,
							LEFT); // 補件日期/註記
					addColumn(table, 9, 1, injuryPayData.getBmNopDateString()
							+ ((StringUtils.isNotBlank(injuryPayData.getBmNopMark())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNopDateString())) ? (" / " + injuryPayData.getBmNopMark()) : injuryPayData.getBmNopMark()) : ""), fontCh12,
							0, LEFT); // 不給付日期
					
					addColumn(table, 9, 1, " ", fontCh12, 0, LEFT);
					addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
					// 最後一筆印完後空一行 (其他給付記錄有資料再印)
					if ((nInjuryPayCount == injuryPayList.size() - 1) && (caseData.getOldPayList().size() > 0
							|| caseData.getDiePayList().size() > 0
							|| caseData.getDisasterDiePayList().size() > 0
							|| caseData.getDisasterDieForDiseaseAfterQuitPayList().size() > 0
							|| caseData.getDisasterDieWithoutPayList().size() > 0
							|| caseData.getSurvivorPayList().size() > 0
							|| caseData.getDisPayList().size() > 0
							|| caseData.getDisasterLostPayList().size() > 0
							|| caseData.getFamDiePayList().size() > 0
							|| caseData.getJoblessPayList().size() > 0
							|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
							|| caseData.getOldAgePayList().size() > 0
							|| caseData.getDisasterSurvivorPayList().size() > 0
							|| caseData.getNpPayList().size() > 0
							|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
							|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
							|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
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
			
			return table;
			
		}

		public Table printOncePays(DisableReviewRpt01Case caseData, Table table, String earlyWarning) throws Exception {
	        // 申請職災住院醫療給付記錄
	        if (caseData.getHosPayList() != null) {
	        	List<DisableReviewRpt01OncePayDataCase> hosPayList = caseData.getHosPayList();
	            for (int nHosPayCount = 0; nHosPayCount < hosPayList.size(); nHosPayCount++) { // ... [
	            	DisableReviewRpt01OncePayDataCase hosPayData = hosPayList.get(nHosPayCount);

	                // 印年金給付表頭

	                if (nHosPayCount == 0) {
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
	                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                    addColumn(table, 58, 1, "申請職災住院醫療給付記錄:", fontCh12b, 0, LEFT);
	                }
	                else {
	                    addEmptyRow(table, 1);

	                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                        deleteRow(table, 1);
	                        document.add(table);
	                        table = addHeader(caseData, false, earlyWarning);
	                    }
	                    else {
	                        deleteRow(table, 1);
	                        addEmptyRow(table, 1);
	                    }
	                }

	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "事故者姓名", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "受理編號", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
	                addColumn(table, 20, 1, "事故日期", fontCh12, 0, LEFT);

	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                // ---
	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, hosPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名

	                addColumn(table, 9, 1, hosPayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
	                addColumn(table, 9, 1, hosPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
	                addColumn(table, 9, 1, hosPayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
	                addColumn(table, 20, 1, hosPayData.getBmEvtDte(), fontCh12, 0, LEFT); // 事故日期

	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                // ---
	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "住院始日", fontCh12, 0, LEFT);
	                ;
	                addColumn(table, 9, 1, "傷病原因", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "受傷部位", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
	                addColumn(table, 22, 1, " ", fontCh12, 0, LEFT);
	                // ---
	                // 20101124 kiyomi - start
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
	                // 20101124 kiyomi - end
	                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
	                addColumn(table, 9, 1, hosPayData.getBmInjPfmDteString(), fontCh12, 0, LEFT); // 住院始日
	                addColumn(table, 9, 1, hosPayData.getBmEvCode(), fontCh12, 0, LEFT); // 傷病原因
	                addColumn(table, 9, 1, hosPayData.getBmInjInPart(), fontCh12, 0, LEFT); // 受傷部位
	                addColumn(table, 9, 1, hosPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
	                addColumn(table, 22, 1, " ", fontCh12, 0, LEFT);

	                // 最後一筆印完後空一行 (其他給付記錄有資料再印)
	                if ((nHosPayCount == hosPayList.size() - 1) && (caseData.getInjuryPayList().size() > 0
	                		|| caseData.getDisasterInjuryPayList().size() > 0
	                		|| caseData.getDisasterInjuryCarePayList().size() > 0
	                		|| caseData.getOldPayList().size() > 0
	                		|| caseData.getDiePayList().size() > 0
	                		|| caseData.getDisasterDiePayList().size() > 0
	                		|| caseData.getDisasterDieForDiseaseAfterQuitPayList().size() > 0
	                		|| caseData.getDisasterDieWithoutPayList().size() > 0
	                		|| caseData.getSurvivorPayList().size() > 0
	                		|| caseData.getDisPayList().size() > 0
	                		|| caseData.getDisasterLostPayList().size() > 0
	                		|| caseData.getFamDiePayList().size() > 0
	                		|| caseData.getJoblessPayList().size() > 0
	                		|| caseData.getVocationalTrainingLivingAllowanceList().size() > 0
	                		|| caseData.getOldAgePayList().size() > 0
	                		|| caseData.getDisasterSurvivorPayList().size() > 0
	                		|| caseData.getNpPayList().size() > 0
	                		|| caseData.getCivilServantRetiredAnnuityPayList().size() > 0
	                		|| caseData.getCivilServantRetiredSurvivorAnnuityPayList().size() > 0
	                		|| caseData.getCivilServantDeadSurvivorAnnuityPayList().size() > 0)) {
	                    // 空白行

	                    addEmptyRow(table, 1);

	                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
	                        // 換了頁就不再塞空白行了

	                        deleteRow(table, 1);
	                        document.add(table);
	                        table = addHeader(caseData, false, earlyWarning);
	                    }
	                }

	            } // ] ... end for (int nDisPayCount = 0; nDisPayCount < getDisPayList.size(); nDisPayCount++)
	        }

			return table;
			
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
        addColumn(table, 2, 1, " ", fontCh8, 0, LEFT);
        addColumn(table, 56, 1, line, fontCh8, 0, LEFT);
        addColumn(table, 2, 1, " ", fontCh8, 0, LEFT);
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

    public Table addHeader(DisableReviewRpt01Case caseData, boolean attached, String earlyWarning) throws Exception {
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

        if ("38".equals(caseData.getPayKind())) {
            addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
            addColumn(table, 30, 1, "勞保失能年金給付受理/審核清單", fontCh18, 0, RIGHT);
            addColumn(table, 8, 1, "【勞併國】", fontCh12, 0, LEFT);
        }
        else {
            addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
            addColumn(table, 30, 1, "勞保失能年金給付受理/審核清單", fontCh18, 0, CENTER);
        }

        addColumn(table, 13, 1, "頁次：" + StringUtils.leftPad(String.valueOf(writer.getCurrentPageNumber() - nPage), 3, "0"), fontCh12, 0, LEFT);
        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
        // 第 ? 頁 / 共 ? 頁 中的 第 ? 頁 部份
        // [
        // addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
        // createPageText(500, 800, writer.getCurrentPageNumber() - nPage,"頁次：", " / ", 12, 150, 50);
        // ]
        // ---
        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
        addColumn(table, 15, 1, "版次：" + StringUtils.defaultString(caseData.getDecideData().getVeriSeq()), fontCh12, 0, LEFT);
        // addColumn(table, 28, 1, (caseData.getPayList() != null && caseData.getPayList().size() == 0) ? " " : ((attached) ? "【附表】" : "【總表】"), fontCh12, 0, CENTER);
        if (writer.getCurrentPageNumber() - nPage == 1)        
            addColumn(table, 28, 1, "【" + caseData.getCaseTypString() + "】" + earlyWarningDesc, fontCh12, 0, CENTER);
        else 
            addColumn(table, 28, 1, " ", fontCh12, 0, CENTER);
        
        addColumn(table, 13, 1, "印表日期：" + printDate, fontCh12, 0, LEFT);
        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
        // ---
        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
        addColumn(table, 19, 1, "申請日期：" + caseData.getAppDateString(), fontCh12, 0, LEFT);
        addColumn(table, 19, 1, "受理編號：" + caseData.getApNoString(), fontCh12, 0, LEFT);
        addBarcode39NoLabel(table, caseData.getApNo(), 75, 75, -5, 20, 1, 0, LEFT, MIDDLE);
        // ---
        // 首次給付年月為空 用給付年月起日帶入

        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
        if (StringUtils.isNotBlank(caseData.getPayYmsString())) {
            // if ((caseData.getCaseTyp()).equals("1")) {
            addColumn(table, 19, 1, "首次給付年月：" + caseData.getPayYmsString(), fontCh12, 0, LEFT);
        }
        else {
            addColumn(table, 19, 1, "首次給付年月：" + ((caseData.getPayList() != null) ? " " : caseData.getPayList().get(0).getPayYmString()), fontCh12, 0, LEFT);
        }
        addColumn(table, 18, 1, "核定年月：" + caseData.getIssuYmString(), fontCh12, 0, LEFT);
        if (caseData.getPayList() != null && caseData.getPayList().size() > 0)
            addColumn(table, 19, 1, "給付年月起迄 " + caseData.getPayList().get(0).getPayYmString() + " - " + caseData.getPayList().get(caseData.getPayList().size() - 1).getPayYmString(), fontCh12, 0, LEFT);
        else
            addColumn(table, 19, 1, "給付年月起迄：       -       ", fontCh12, 0, LEFT);
        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
        // ---
        addLine(table);
        // ---
        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
        addColumn(table, 8, 1, caseData.getEvtName(), fontCh16, 0, LEFT);
        addColumn(table, 4, 1, StringUtils.defaultString(caseData.getEvtAge()) + "歲", fontCh16, 8, 0, LEFT);
        addColumn(table, 2, 1, caseData.getEvtSexString(), fontCh16, 0, LEFT);
        addColumn(table, 20, 1, caseData.getEvtBrDateString(), fontCh16, 0, LEFT);
        addColumn(table, 9, 1, caseData.getEvtIdnNo(), fontCh16, 0, LEFT);
        addColumn(table, 2, 1, ValidateUtility.validatePersonIdNo(caseData.getEvtIdnNo()) ? "" : "W", fontCh16, 0, LEFT);
        addBarcode39NoLabel(table, StringUtils.defaultString(caseData.getEvtIdnNo()), 75, 75, -5, 12, 1, 0, LEFT, MIDDLE);
        addColumn(table, 1, 1, " ", fontCh16, 0, LEFT);
        // ---
        addLine(table);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<DisableReviewRpt01Case> caseList) throws Exception {

        try {
            document.open();

            for (int index = 0; index < caseList.size(); index++) {

                // 頁次處理
                if (index > 0) {
                    nPage = writer.getPageNumber();
                }

                DisableReviewRpt01Case caseData = (DisableReviewRpt01Case) caseList.get(index);

                // 表頭
                String earlyWarningP1 = "N";
                String earlyWarningP2 = "N";
                String earlyWarningP3 = "N";
                String earlyWarning = "N";
                List<DisableReviewRpt01ChkfileDataCase> chkfileDataList01 = caseData.getChkfileDataList();
                if (chkfileDataList01.size() > 0) {
                    // 取得事故者編審註記資料                    
                    List<StringBuffer> chkfileStringList = new ArrayList<StringBuffer>();
                    StringBuffer chkfileString = new StringBuffer("");
                    for (DisableReviewRpt01ChkfileDataCase chkfileData : chkfileDataList01) {
                        // 若 編審註記代碼 (CHKCODE) 值為 P1 及（ P2 或 P3），表頭要顯示「＊預警案件」
                        if (StringUtils.equalsIgnoreCase(chkfileData.getChkCode(), "P1"))
                            earlyWarningP1 = "Y";
                        else if (StringUtils.equalsIgnoreCase(chkfileData.getChkCode(), "P2"))
                            earlyWarningP2 = "Y";
                        else if (StringUtils.equalsIgnoreCase(chkfileData.getChkCode(), "P3"))
                            earlyWarningP3 = "Y";                                                        
                    }
                }
                
                List<DisableReviewRpt01BenDataCase> benList01 = caseData.getBenList();
                if (benList01.size() > 0) {                
                    for (int nBenList = 0; nBenList < benList01.size(); nBenList++) {
                        DisableReviewRpt01BenDataCase benData = benList01.get(nBenList);
                        List<DisableReviewRpt01BenPayDataCase> benPayList = benData.getBenPayList();

                        for (DisableReviewRpt01BenPayDataCase benPayData : benPayList) {
                            // 若 編審註記代碼 (CHKCODE) 值為 P1 及（ P2 或 P3），表頭要顯示「＊預警案件」
                            if (benData.getChkfileDataBy(benPayData.getPayYm()).contains("P1"))
                                earlyWarningP1 = "Y";
                            else if (benData.getChkfileDataBy(benPayData.getPayYm()).contains("P2"))
                                earlyWarningP2 = "Y";
                            else if (benData.getChkfileDataBy(benPayData.getPayYm()).contains("P3"))
                                earlyWarningP3 = "Y";  
                        }                   
                    }
                }
                
                if (earlyWarningP1.equals("Y") && (earlyWarningP2.equals("Y") || earlyWarningP3.equals("Y")))
                    earlyWarning = "Y";                 
                
                Table table = addHeader(caseData, false, earlyWarning);

                // 核定總額資料
                // [
                DisableReviewRpt01IssueAmtDataCase issueAmtData = caseData.getIssueAmtData();

                addColumn(table, 28, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "審核", fontCh12, 0, CENTER);
                addColumn(table, 10, 1, "覆核", fontCh12, 0, CENTER);
                addColumn(table, 10, 1, "總核", fontCh12, 0, CENTER);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                // ---
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "投保薪資：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, "".equals(formatBigDecimalToInteger(caseData.getLsInsmAmt())) ? "0" : formatBigDecimalToInteger(caseData.getLsInsmAmt()), fontCh12, 0, RIGHT); // 投保金額
                addColumn(table, 45, 1, " ", fontCh12, 0, LEFT);
                // ---
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "平均薪資：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, "".equals(formatBigDecimalToInteger(caseData.getDecideData().getInsAvgAmt())) ? "0" : formatBigDecimalToInteger(caseData.getDecideData().getInsAvgAmt()), fontCh12, 0, RIGHT); // 平均薪資
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (更正前：) 標題 - 有值才顯示, 此段預留
                addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (更正前：) 的值 - 有值才顯示, 此段預留
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 31, 1, " ", fontCh12, 0, LEFT);
                // ---
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "核定總額：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, "".equals(formatBigDecimalToInteger(issueAmtData.getIssueAmt())) ? "0" : formatBigDecimalToInteger(issueAmtData.getIssueAmt()), fontCh12, 0, RIGHT); // 核定總額
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (扣減：) 標題 - 有值才顯示, 此段預留
                addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (扣減：) 的值 - 有值才顯示, 此段預留
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 31, 1, " ", fontCh12, 0, LEFT);
                // ---
                // 補發總額 - 有值時此欄目才顯示
                if (issueAmtData.getSupAmt() != null && issueAmtData.getSupAmt().compareTo(new BigDecimal(0)) != 0) {
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "補發總額：", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "".equals(formatBigDecimalToInteger(issueAmtData.getSupAmt())) ? "0" : formatBigDecimalToInteger(issueAmtData.getSupAmt()), fontCh12, 0, RIGHT); // 補發總額
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (扣減：) 標題 - 有值才顯示, 此段預留
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (扣減：) 的值 - 有值才顯示, 此段預留
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 31, 1, " ", fontCh12, 0, LEFT);
                }
                // ---
                // 紓困總額 - 有值時此欄目才顯示
                if (issueAmtData.getOffsetAmt() != null && issueAmtData.getOffsetAmt().compareTo(new BigDecimal(0)) != 0) {
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "紓困總額：", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "".equals(formatBigDecimalToInteger(issueAmtData.getOffsetAmt())) ? "0" : formatBigDecimalToInteger(issueAmtData.getOffsetAmt()), fontCh12, 0, RIGHT); // 紓困總額
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (扣減：) 標題 - 有值才顯示, 此段預留
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (扣減：) 的值 - 有值才顯示, 此段預留
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 31, 1, " ", fontCh12, 0, LEFT);
                }
                // ---
                // 扣減總額 - 有值時此欄目才顯示
                if (issueAmtData.getOtherAmt() != null && issueAmtData.getOtherAmt().compareTo(new BigDecimal(0)) != 0) {
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "扣減總額：", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "".equals(formatBigDecimalToInteger(issueAmtData.getOtherAmt())) ? "0" : formatBigDecimalToInteger(issueAmtData.getOtherAmt()), fontCh12, 0, RIGHT); // 扣減總額
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (扣減：) 標題 - 有值才顯示, 此段預留
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (扣減：) 的值 - 有值才顯示, 此段預留
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 31, 1, " ", fontCh12, 0, LEFT);
                }
                // ---
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "實付總額：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, "".equals(formatBigDecimalToInteger(issueAmtData.getAplpayAmt())) ? "0" : formatBigDecimalToInteger(issueAmtData.getAplpayAmt()), fontCh12, 0, RIGHT); // 實付總額
                addColumn(table, 45, 1, " ", fontCh12, 0, LEFT);
                // 分隔線

                addLine(table);
                // ]
                // 核定總額資料

                // 給付資料
                // [
                List<DisableReviewRpt01PayDataCase> payList = caseData.getPayList();

                if (caseData.getPayList() != null) {

                    // 給付資料表頭
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "核定金額", fontCh12, 0, RIGHT);
                    // addColumn(table, 10, 1, "/ 調整前金額", fontCh12, 0, RIGHT);
                    addColumn(table, 10, 1, "應收/沖抵金額", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "補發金額", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "扣減總金額", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "紓困金額", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "匯款匯費", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "實付金額", fontCh12, 0, RIGHT);
                    addColumn(table, 5, 1, "　", fontCh12, 0, LEFT);

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

                        DisableReviewRpt01PayDataCase payData = payList.get(nPayList);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 6, 1, payData.getPayYmString(), fontCh12, 0, LEFT); // 給付年月

                        if (payData.getIssueAmt() != null && payData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(payData.getIssueAmt()), fontCh12, 0, RIGHT); // 核定
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 核定金額

                        // if (payData.getAdjustAmt() != null && payData.getAdjustAmt().compareTo(new BigDecimal(0)) != 0)
                        // addColumn(table, 10, 1, formatBigDecimalToInteger(payData.getAdjustAmt()), fontCh12, 0, RIGHT); // / 物價調整金額
                        // addColumn(table, 10, 1, " ", fontCh12, 0, RIGHT); // / 物價調整金額
                        // else
                        // addColumn(table, 10, 1, "0", fontCh12, 0, RIGHT); // / 物價調整金額
                        // addColumn(table, 10, 1, " ", fontCh12, 0, RIGHT); // / 物價調整金額

                        // if (payData.getIssuCalcAmt() != null){
                        // if (!(payData.getIssuCalcAmt().equals(payData.getIssueAmt()))) {
                        // //if (payData.getIssuCalcAmt() != null && payData.getIssuCalcAmt().compareTo(new BigDecimal(0)) != 0)
                        // if (payData.getIssuCalcAmt().compareTo(new BigDecimal(0)) != 0)
                        // addColumn(table, 10, 1, formatBigDecimalToInteger(payData.getIssuCalcAmt()), fontCh12, 0, RIGHT); // / 調整前金額

                        // else
                        // addColumn(table, 10, 1, " ", fontCh12, 0, RIGHT); // / 調整前金額

                        // } else {
                        // addColumn(table, 10, 1, " ", fontCh12, 0, RIGHT); // / 調整前金額

                        // }
                        // }else{
                        // addColumn(table, 10, 1, " ", fontCh12, 0, RIGHT); // / 調整前金額

                        // }

                        if (payData.getRecAmt() != null && payData.getRecAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 5, 1, formatBigDecimalToInteger(payData.getRecAmt()) + "/", fontCh12, 0, RIGHT); // 應收金額
                        else
                            addColumn(table, 5, 1, "0/", fontCh12, 0, RIGHT); // 應收金額

                        if (payData.getPayBanance() != null && payData.getPayBanance().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 5, 1, formatBigDecimalToInteger(payData.getPayBanance()), fontCh12, 0, RIGHT); // 沖抵
                        else
                            addColumn(table, 5, 1, "0", fontCh12, 0, RIGHT); // 沖抵

                        if (payData.getSupAmt() != null && payData.getSupAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(payData.getSupAmt()), fontCh12, 0, RIGHT); // 補發金額
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 補發金額

                        if (payData.getOtherAmt() != null && payData.getOtherAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(payData.getOtherAmt()), fontCh12, 0, RIGHT); // 扣減總金額

                        else
                            addColumn(table, 7, 1, "0", fontCh12, 0, RIGHT); // 扣減總金額

                        if (payData.getOffsetAmt() != null && payData.getOffsetAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(payData.getOffsetAmt()), fontCh12, 0, RIGHT); // 紓困金額
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 紓困金額

                        if (payData.getPayRate() != null && payData.getPayRate().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(payData.getPayRate()), fontCh12, 0, RIGHT); // 匯款匯費
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 匯款匯費

                        if (payData.getAplpayAmt() != null && payData.getAplpayAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(payData.getAplpayAmt()), fontCh12, 0, RIGHT); // 實付金額
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 實付金額

                        addColumn(table, 5, 1, "　", fontCh12, 0, LEFT);
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

                // 20110128 kiyomi - mark start
                // 核定資料有四行, 在塞資料前先隨便塞空白行測試是否需換頁
                // addEmptyRow(table, 4);

                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // deleteRow(table, 4);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }
                // else {
                // deleteRow(table, 4);
                // }
                // 20110128 kiyomi - mark end

                // 20110128 kiyomi - start
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
                // 20110128 kiyomi - end

                DisableReviewRpt01DecideDataCase decideData = caseData.getDecideData();
                List<DisableRevewRpt01ExpDataCase> disableList = caseData.getDisableList();

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, "失能等級", fontCh12, 0, LEFT);
                addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                addColumn(table, 8, 1, "核定等級", fontCh12, 0, LEFT);
                addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, "失能項目", fontCh12, 0, LEFT);
                addColumn(table, 27, 1, "　", fontCh12, 0, LEFT);

                if (caseData.getDisableList() != null && disableList.size() > 0) {

                    // 20110128 kiyomi - start
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
                    // 20110128 kiyomi - end

                    for (int disList = 0; disList < disableList.size(); disList++) {

                        DisableRevewRpt01ExpDataCase disData = disableList.get(disList);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        if (disData.getCriInJclStr() != null)
                            addColumn(table, 6, 1, disData.getCriInJclStr(), fontCh12, 0, LEFT); // 失能等級
                        else
                            addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 失能等級

                        addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);

                        if (disData.getCriInIssul() != null)
                            addColumn(table, 8, 1, disData.getCriInIssul(), fontCh12, 0, LEFT); // 核定等級
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT); // 核定等級

                        addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);

                        if (disData.getCriInJdpStr() != null)
                            addColumn(table, 36, 1, disData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
                        else
                            addColumn(table, 36, 1, " ", fontCh12, 0, LEFT); // 失能項目
                    }

                }
                else {
                    // 20110128 kiyomi - start
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
                    // 20110128 kiyomi - end

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 失能等級
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, " ", fontCh12, 0, LEFT); // 核定等級
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 失能項目
                    addColumn(table, 27, 1, "　", fontCh12, 0, LEFT);
                }

                // 20110128 kiyomi - start
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
                // 20110128 kiyomi - end

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, "申請單位", fontCh12, 0, LEFT);
                addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                addColumn(table, 8, 1, "事故發生單位", fontCh12, 0, LEFT);
                addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, "診斷失能日期", fontCh12, 0, LEFT);
                addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                addColumn(table, 8, 1, "國際疾病代碼", fontCh12, 0, LEFT);
                addColumn(table, 15, 1, "　", fontCh12, 0, LEFT);

                if (caseData.getDisableList() != null && disableList.size() > 0) {

                    // 20110128 kiyomi - start
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
                    // 20110128 kiyomi - end

                    for (int disList = 0; disList < disableList.size(); disList++) {

                        DisableRevewRpt01ExpDataCase disData = disableList.get(disList);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        if (caseData.getApUbno() != null)
                            addColumn(table, 6, 1, caseData.getApUbno(), fontCh12, 0, LEFT); // 申請單位
                        else
                            addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 申請單位

                        addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);

                        if (caseData.getLsUbno() != null)
                            addColumn(table, 8, 1, caseData.getLsUbno(), fontCh12, 0, LEFT); // 事故發生單位
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT); // 事故發生單位

                        addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);

                        if (caseData.getEvtJobDate() != null)
                            addColumn(table, 9, 1, StringUtils.defaultString(caseData.getEvtJobDateDateString()), fontCh12, 0, LEFT); // 診斷失能日期
                        else
                            addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 診斷失能日期

                        addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);

                        if (disData.getCriInJnmeStr() != null)
                            addColumn(table, 21, 1, disData.getCriInJnmeStr(), fontCh12, 0, LEFT); // 國際疾病代碼
                        else
                            addColumn(table, 21, 1, " ", fontCh12, 0, CENTER); // 國際疾病代碼

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                }
                else {

                    // 20110128 kiyomi - start
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
                    // 20110128 kiyomi - end

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 申請單位
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 事故發生單位
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 診斷失能日期
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 國際疾病代碼
                    addColumn(table, 12, 1, "　", fontCh12, 0, LEFT);
                }

                // 20110128 kiyomi - start
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
                // 20110128 kiyomi - end

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, "傷病分類", fontCh12, 0, LEFT);
                addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                addColumn(table, 8, 1, "傷病原因", fontCh12, 0, LEFT);
                addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                addColumn(table, 9, 1, "受傷部位", fontCh12, 0, LEFT);
                addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                addColumn(table, 8, 1, "媒介物", fontCh12, 0, LEFT);
                addColumn(table, 15, 1, "　", fontCh12, 0, LEFT);

                if (caseData.getDisableList() != null && disableList.size() > 0) {

                    // 20110128 kiyomi - start
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
                    // 20110128 kiyomi - end

                    for (int disList = 0; disList < disableList.size(); disList++) {
                        DisableRevewRpt01ExpDataCase disData = disableList.get(disList);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        if (disData.getEvTyp() != null)
                            addColumn(table, 6, 1, disData.getEvTyp(), fontCh12, 0, LEFT); // 傷病分類
                        else
                            addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 傷病分類

                        addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);

                        if (disData.getEvCode() != null)
                            addColumn(table, 9, 1, disData.getEvCode(), fontCh12, 0, LEFT); // 傷病原因
                        else
                            addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 傷病原因

                        addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);

                        if (disData.getCriInPartStr() != null)
                            addColumn(table, 8, 1, disData.getCriInPartStr(), fontCh12, 0, LEFT); // 受傷部位
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT); // 受傷部位

                        addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);

                        if (disData.getCriMedium() != null)
                            addColumn(table, 8, 1, disData.getCriMedium(), fontCh12, 0, LEFT); // 媒介物

                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT); // 媒介物

                        addColumn(table, 15, 1, "　", fontCh12, 0, LEFT);

                    }

                }
                else {

                    // 20110128 kiyomi - start
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
                    // 20110128 kiyomi - end

                    addColumn(table, 9, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, " ", fontCh12, 0, CENTER);
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, " ", fontCh12, 0, CENTER);
                    addColumn(table, 14, 1, "　", fontCh12, 0, LEFT);

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 傷病分類
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 傷病原因
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, " ", fontCh12, 0, CENTER); // 受傷部位
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, " ", fontCh12, 0, CENTER); // 媒介物

                    addColumn(table, 15, 1, "　", fontCh12, 0, LEFT);

                }

                // ---// 前次核定金額
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "前次核定金額", fontCh12, 0, LEFT);
                addColumn(table, 12, 1, "得請領起月", fontCh12, 0, LEFT); // 20121024 Kiyomi
                addColumn(table, 13, 1, "發放方式", fontCh12, 0, LEFT); // 20121024 Kiyomi
                addColumn(table, 13, 1, "年金請領資格", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, " ", fontCh12, 0, RIGHT);

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                if (caseData.getIssuCalcAmt() != null && caseData.getIssuCalcAmt().compareTo(new BigDecimal(0)) != 0)
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseData.getIssuCalcAmt()), fontCh12, 0, RIGHT); // 前次核定金額
                else
                    addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 前次核定金額

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 12, 1, caseData.getAbleapsYm(), fontCh12, 0, LEFT);

                if (caseData.getInterValMonth().equals("0") || caseData.getInterValMonth().equals("1") || caseData.getInterValMonth().equals(""))
                    addColumn(table, 13, 1, "按月發放", fontCh12, 0, LEFT); // 發放方式
                else if (!caseData.getInterValMonth().equals("0") || !caseData.getInterValMonth().equals("1") || !caseData.getInterValMonth().equals(""))
                    addColumn(table, 13, 1, "按" + caseData.getInterValMonth() + "個月發放", fontCh12, 0, LEFT); // 發放方式

                if(disableList.size() > 0){
                    addColumn(table, 13, 1, disableList.get(0).getDisQualMkStr(), fontCh12, 0, LEFT);
                }else{
                	addColumn(table, 13, 1, " ", fontCh12, 0, LEFT);
                }
                addColumn(table, 10, 1, " ", fontCh12, 0, RIGHT);
                
                // ---重新查核失能程度資料
                if(caseData.getBareCheckList().size() > 0 && caseData.getPayKind().equals("35") || caseData.getPayKind().equals("38")){
                
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 15, 1, "重新查核失能程度年月", fontCh12, 0, LEFT);
                addColumn(table, 43, 1, "重新查核狀態 / 結果 / 完成年月", fontCh12, 0, LEFT); // 20121024 Kiyomi 	
                	
                for(int i = 0 ; i < caseData.getBareCheckList().size() ; i++){
                
                  //最後一筆只放入最大年月
                  if(i == caseData.getBareCheckList().size() - 1){
                	  addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                      addColumn(table, 15, 1, caseData.getBareCheckList().get(i).getReChkYm(), fontCh12, 0, LEFT);
                      addColumn(table, 43, 1, " ", fontCh12, 0, LEFT); // 20121024 Kiyomi
                  }else{
                	  addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                      addColumn(table, 15, 1, caseData.getBareCheckList().get(i).getReChkYm(), fontCh12, 0, LEFT);
                      addColumn(table, 43, 1, caseData.getBareCheckList().get(i).getReChkStatusStr()+" / "+caseData.getBareCheckList().get(i).getReChkResultStr()+" / "+caseData.getBareCheckList().get(i).getComReChkYm(), fontCh12, 0, LEFT); // 20121024 Kiyomi
                  }

                }
                }
                // ---
                
                // 分隔線

                addLine(table);

                if (caseData.getDisableList() != null && disableList.size() > 0) {

                    for (int disList = 0; disList < disableList.size(); disList++) {

                        DisableRevewRpt01ExpDataCase disData = disableList.get(disList);

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "醫院代碼：", fontCh12, 0, LEFT);
                        if (disData.getHosId() != null)
                            addColumn(table, 10, 1, disData.getHosId(), fontCh12, 0, LEFT);
                        else
                            addColumn(table, 10, 1, " ", fontCh12, 0, LEFT);

                        addColumn(table, 2, 1, "　", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "醫師1：", fontCh12, 0, LEFT);

                        if (disData.getDoctorName1() != null)
                            addColumn(table, 8, 1, disData.getDoctorName1(), fontCh12, 0, LEFT);
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);

                        addColumn(table, 2, 1, "　", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "醫師2：", fontCh12, 0, LEFT);

                        if (disData.getDoctorName2() != null)
                            addColumn(table, 8, 1, disData.getDoctorName2(), fontCh12, 0, LEFT);
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        // ---
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "醫院名稱：", fontCh12, 0, LEFT);
                        if (disData.getHosId() != null)
                            addColumn(table, 40, 1, disData.getHpName(), fontCh12, 0, LEFT);
                        else
                            addColumn(table, 40, 1, "　", fontCh12, 0, LEFT);

                        addColumn(table, 10, 1, "　", fontCh12, 0, LEFT);
                        
                        // ---
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "職病醫院代碼：", fontCh12, 0, LEFT);
                        if (disData.getOcAccHosId() != null)
                            addColumn(table, 8, 1, disData.getOcAccHosId(), fontCh12, 0, LEFT);
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);

                        addColumn(table, 2, 1, "　", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "職病醫師1：", fontCh12, 0, LEFT);

                        if (disData.getOcAccDoctorName1() != null)
                            addColumn(table, 8, 1, disData.getOcAccDoctorName1(), fontCh12, 0, LEFT);
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);

                        addColumn(table, 2, 1, "　", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "職病醫師2：", fontCh12, 0, LEFT);

                        if (disData.getOcAccDoctorName2() != null)
                            addColumn(table, 8, 1, disData.getOcAccDoctorName2(), fontCh12, 0, LEFT);
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        // ---
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "職病醫院名稱：", fontCh12, 0, LEFT);
                        if (disData.getOcAccHosId() != null)
                            addColumn(table, 40, 1, disData.getOcAccHpName(), fontCh12, 0, LEFT);
                        else
                            addColumn(table, 40, 1, "　", fontCh12, 0, LEFT);

                        addColumn(table, 8, 1, "　", fontCh12, 0, LEFT);                        
                        
                    }

                }
                else {
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "醫院代碼：", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "醫師1：", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "醫師2：", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "醫院名稱：", fontCh12, 0, LEFT);
                    addColumn(table, 40, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "　", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "職病醫院代碼：", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "職病醫師1：", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "職病醫師2：", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);                    
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "職病醫院名稱：", fontCh12, 0, LEFT);
                    addColumn(table, 40, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "　", fontCh12, 0, LEFT);                    
                }

                // 分隔線

                addLine(table);

                addEmptyRow(table, 10);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    deleteRow(table, 10);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 10);
                }

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, "勞保投保", fontCh12, 0, LEFT);
                addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "實發年資", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, "勞保計算", fontCh12, 0, LEFT);
                addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "給付金額", fontCh12, 0, LEFT);
                addColumn(table, 24, 1, "", fontCh12, 0, LEFT);

                List<DisableReviewRpt01BenPayDataCase> disPayList = caseData.getEvtBenPayList();

                if (caseData.getEvtBenPayList() != null && disPayList.size() > 0) {

                    DisableReviewRpt01BenPayDataCase payData = disPayList.get(0);

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (payData.getNitrmY() != null || payData.getNitrmM() != null)
                        addColumn(table, 6, 1, payData.getNitrmY() + "年" + payData.getNitrmM() + "月", fontCh12, 0, LEFT); // 勞保投保
                    else
                        addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 勞保投保

                    addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);

                    if (payData.getAplPaySeniY() != null || payData.getAplPaySeniM() != null)
                        addColumn(table, 7, 1, payData.getAplPaySeniY() + "年" + payData.getAplPaySeniM() + "月", fontCh12, 0, LEFT); // 實發年資
                    else
                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 實發年資

                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT);

                    if (payData.getOldaAmt() != null)
                        addColumn(table, 6, 1, formatBigDecimalToInteger(payData.getOldaAmt()), fontCh12, 0, RIGHT); // 勞保計算
                    else
                        addColumn(table, 6, 1, "0", fontCh12, 0, LEFT); // 勞保計算

                    addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);

                    if (payData.getOldbAmt() != null)
                        addColumn(table, 5, 1, formatBigDecimalToInteger(payData.getOldbAmt()), fontCh12, 0, RIGHT); // 給付金額
                    else
                        addColumn(table, 5, 1, "0", fontCh12, 0, RIGHT); // 給付金額
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 24, 1, " ", fontCh12, 0, LEFT);

                }
                else {
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 勞保投保
                    addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 實發年資
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 勞保計算
                    addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, " ", fontCh12, 0, RIGHT); // 給付金額
                    addColumn(table, 24, 1, " ", fontCh12, 0, LEFT);

                }

                // ---
                // 分隔線

                addLine(table);

                addEmptyRow(table, 10);
                Boolean lineStatus = false;

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    deleteRow(table, 10);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 10);
                }
                if (caseData.getEvtBenPayDataStatus() || caseData.getEvtBafamilyStatus()) {
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "眷屬符合人數", fontCh12, 0, LEFT);
                    addColumn(table, 4, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "加發眷屬補助", fontCh12, 0, LEFT);
                    addColumn(table, 4, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "每月扣除失能", fontCh12, 0, LEFT);
                    addColumn(table, 4, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                }

                List<DisableReviewRpt01BenPayDataCase> disBenPayList = caseData.getEvtBenPayDataList();

                if ((caseData.getEvtBenPayDataList() != null && disBenPayList.size() > 0 && caseData.getEvtBenPayDataStatus()) || caseData.getEvtBafamilyStatus()) {
                    for (DisableReviewRpt01BenPayDataCase benPayData : disBenPayList) {
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        if (benPayData.getPayYm() != null)
                            addColumn(table, 7, 1, StringUtils.defaultString(benPayData.getPayYmString()), fontCh12, 0, LEFT); // 給付年月
                        else
                            addColumn(table, 7, 1, " ", fontCh12, 0, RIGHT); // 給付年月

                        addColumn(table, 6, 1, benPayData.getPayKind(), fontCh12, 0, LEFT);

                        if (benPayData.getQualCount() != null)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getQualCount()), fontCh12, 0, RIGHT); // 眷屬符合人數
                        else
                            addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 眷屬符合人數

                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);

                        if (benPayData.getOldRate() != null && !"0".equals(formatBigDecimalToInteger(benPayData.getOldRate())))
                        	if(StringUtils.equals(caseData.getPayKind(), "38")){
                                addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getOldRate()) + "% / " + formatBigDecimalToInteger(benPayData.getOldRateAmt38()), fontCh12, 0, RIGHT); // 加發眷屬補助
                        	}else{
                        		addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getOldRate()) + "% / " + formatBigDecimalToInteger(benPayData.getOldRateAmt()), fontCh12, 0, RIGHT); // 加發眷屬補助
                        	}
                        else
                            addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 加發眷屬補助

                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);

                        if (benPayData.getOldRate() != null)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(benPayData.getCompenAmt()), fontCh12, 0, RIGHT); // 每月扣除金額
                        else
                            addColumn(table, 7, 1, "0", fontCh12, 0, RIGHT); // 每月扣除金額

                        addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);
                        
                        // 判斷是否換頁
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
                    }

                }

                if (caseData.getEvtBenPayDataStatus() || caseData.getEvtBafamilyStatus()) {

                    // ---
                    // 分隔線

                    addLine(table);

                    addEmptyRow(table, 10);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 10);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 10);
                    }
                }

                if (caseData.getOccAcc() != null) {

                    DisableQueryOccAccDataCase occAcccData = caseData.getOccAcc();

                    if ((occAcccData.getBefIssueAmt() != null && occAcccData.getBefIssueAmt().compareTo(new BigDecimal(0)) > 0) || (occAcccData.getInsAvgAmt() != null && occAcccData.getOldbAmt().compareTo(new BigDecimal(0)) > 0)
                                    || (occAcccData.getOcAccaddAmt() != null && occAcccData.getOcAccaddAmt().compareTo(new BigDecimal(0)) > 0) || (occAcccData.getAplpayAmt() != null && occAcccData.getAplpayAmt().compareTo(new BigDecimal(0)) > 0)
                                    || formatBigDecimalToInteger(disableList.get(0).getDeductDay()) != "") {

                        lineStatus = true;

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "職災補償1次金", fontCh12, 0, LEFT);
                        addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);
                        addColumn(table, 10, 1, "6個月平均薪資", fontCh12, 0, LEFT);
                        addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "已領職災增給金額", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "實發職災1次金", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "扣除日數", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        Integer befIssueAmt = (occAcccData.getBefIssueAmt() == null) ? new Integer(0) : occAcccData.getBefIssueAmt().intValue();
                        Integer ocAccaddAmt = (occAcccData.getOcAccaddAmt() == null) ? new Integer(0) : occAcccData.getOcAccaddAmt().intValue();
                        Integer aplpayAmt = (befIssueAmt - ocAccaddAmt);

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        if (occAcccData.getOldbAmt() != null)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(occAcccData.getOldbAmt()), fontCh12, 0, RIGHT); // 職災補償1次金
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 職災補償1次金
                        addColumn(table, 4, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 1, 1, "/", fontCh12, 0, CENTER);

                        if (occAcccData.getInsAvgAmt() != null)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(occAcccData.getInsAvgAmt()), fontCh12, 0, RIGHT); // 6個月平均薪資
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 6個月平均薪資
                        addColumn(table, 4, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);

                        if (caseData.getDisableList() != null && disableList.size() > 0 && disableList.get(0).getOcAccaddAmt() != null)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(occAcccData.getOcAccaddAmt()), fontCh12, 0, RIGHT); // 已領職災增給金額
                        else
                            addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 已領職災增給金額

                        addColumn(table, 5, 1, " ", fontCh12, 0, LEFT);

                        if (occAcccData.getAplpayAmt() != null)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(occAcccData.getAplpayAmt()) != "" ? formatBigDecimalToInteger(occAcccData.getAplpayAmt()) : "0", fontCh12, 0, RIGHT); // 職災實發1次金
                        else
                            addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 職災實發1次金

                        addColumn(table, 5, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 4, 1, formatBigDecimalToInteger(disableList.get(0).getDeductDay()) != "" ? formatBigDecimalToInteger(disableList.get(0).getDeductDay()) : "0", fontCh12, 0, RIGHT); // 扣除天數
                        addColumn(table, 6, 1, " ", fontCh12, 0, LEFT);
                    }
                }

                if ((caseData.getCutAmt() != null && caseData.getCutAmt().compareTo(new BigDecimal(0)) != 0) || (disPayList.get(0).getLecomAmt() != null && disPayList.get(0).getLecomAmt().compareTo(new BigDecimal(0)) != 0)
                                || (disPayList.get(0).getRecomAmt() != null && disPayList.get(0).getRecomAmt().compareTo(new BigDecimal(0)) != 0) || (disableList.get(0).getPrType() != null && !"".equals(disableList.get(0).getPrType()))
                                || (disableList.get(0).getOcaccIdentMk() != null && !"".equals(disableList.get(0).getOcaccIdentMk()))) {
                    lineStatus = true;

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "應扣失能", fontCh12, 0, LEFT);
                    addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "已扣失能", fontCh12, 0, LEFT);
                    addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "未扣失能", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, CENTER);
                    addColumn(table, 11, 1, "符合第20條之1", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, CENTER);
                    addColumn(table, 8, 1, "先核普通", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, CENTER);

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (caseData.getCutAmt() != null && caseData.getCutAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 6, 1, (!"".equals(formatBigDecimalToInteger(caseData.getCutAmt()))) ? formatBigDecimalToInteger(caseData.getCutAmt()) : "0", fontCh12, 0, RIGHT); // 應扣失能
                    else
                        addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 應扣失能
                    addColumn(table, 4, 1, " ", fontCh12, 0, RIGHT);
                    addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);

                    if (caseData.getEvtBenPayList() != null && disPayList.size() > 0) {

                        DisableReviewRpt01BenPayDataCase payData = disPayList.get(0);

                        Integer cutAmt = (caseData.getCutAmt() == null) ? new Integer(0) : caseData.getCutAmt().intValue();
                        Integer lecomAmt = (payData.getLecomAmt() == null || "".equals(payData.getLecomAmt().toString())) ? new Integer(0) : payData.getLecomAmt().intValue();
                        Integer recomAmt = (cutAmt - lecomAmt);

                        if (payData.getLecomAmt() != null)
                            addColumn(table, 6, 1, ("".equals(formatBigDecimalToInteger(payData.getLecomAmt()))) ? "0" : formatBigDecimalToInteger(payData.getLecomAmt()), fontCh12, 0, RIGHT); // 已扣失能
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 已扣失能

                        addColumn(table, 4, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);

                        if (caseData.getCutAmt() != null)
                            addColumn(table, 6, 1, ("".equals(formatNumber(recomAmt)) ? "0" : formatNumber(recomAmt)), fontCh12, 0, RIGHT); // 未扣失能
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 未扣失能
                        addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    }
                    else {

                        addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 已扣失能
                        addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 未扣失能
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    }

                    if (caseData.getDisableList() != null && disableList.size() > 0) {

                        for (int disList = 0; disList < disableList.size(); disList++) {
                            DisableRevewRpt01ExpDataCase disData = disableList.get(disList);
                            if (disData.getPrType() != null)
                                addColumn(table, 11, 1, disData.getOcaccIdentMk(), fontCh12, 0, LEFT); // 符合第20條之1
                            else
                                addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 符合第20條之1

                            addColumn(table, 2, 1, " ", fontCh12, 0, CENTER);

                            if (disData.getPrType() != null)
                                addColumn(table, 8, 1, disData.getPrType(), fontCh12, 0, LEFT); // 先核普通

                            else
                                addColumn(table, 8, 1, " ", fontCh12, 0, LEFT); // 先核普通

                        }
                    }
                    else {
                        addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 符合第20條之1
                        addColumn(table, 2, 1, " ", fontCh12, 0, CENTER);
                        addColumn(table, 8, 1, " ", fontCh12, 0, LEFT); // 先核普通

                    }

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                }

                DisableReviewRpt01OncePayDataCase dieOncePayData = caseData.getDieOncePayData();

                BigDecimal annuAmt = (caseData.getAnnuAmt() == null) ? new BigDecimal(0) : caseData.getAnnuAmt();
                BigDecimal oldAnnuAmt = (caseData.getDabAnnuAmt() == null) ? new BigDecimal(0) : caseData.getDabAnnuAmt();

                if ((annuAmt != null && annuAmt.compareTo(new BigDecimal(0)) != 0) || (oldAnnuAmt != null && oldAnnuAmt.compareTo(new BigDecimal(0)) != 0) || (caseData.getDabApNo() != null && !"".equals(caseData.getDabApNo()))) {
                    lineStatus = true;

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "已領失能年金金額", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "老年年金受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "已領老年年金金額", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "已領年金給付總額", fontCh12, 0, LEFT);
                    addColumn(table, 4, 1, " ", fontCh12, 0, LEFT);

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (annuAmt != null && annuAmt.compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 10, 1, formatBigDecimalToInteger(annuAmt), fontCh12, 0, RIGHT); // 失能年金已領金額
                    else
                        addColumn(table, 10, 1, "0", fontCh12, 0, RIGHT); // 失能年金已領金額

                    addColumn(table, 5, 1, " ", fontCh12, 0, LEFT);

                    if (caseData.getDabApNo() != null) {
                        addColumn(table, 11, 1, caseData.getDabApNo(), fontCh12, 0, LEFT); // 老年年金受理編號
                    }
                    else {
                        addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 老年年金受理編號
                    }

                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);

                    if (oldAnnuAmt != null && oldAnnuAmt.compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 11, 1, formatBigDecimalToInteger(oldAnnuAmt), fontCh12, 0, RIGHT); // 老年年金已領金額
                    else
                        addColumn(table, 11, 1, "　", fontCh12, 0, RIGHT); // 老年年金已領金額

                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);

                    Integer recomAmt = (oldAnnuAmt.intValue() + annuAmt.intValue());

                    if (oldAnnuAmt != null && oldAnnuAmt.compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 11, 1, formatNumber(recomAmt), fontCh12, 0, RIGHT); // 已領年金給付總額
                    else
                        addColumn(table, 11, 1, "0", fontCh12, 0, RIGHT); // 已領年金給付總額
                    addColumn(table, 4, 1, " ", fontCh12, 0, LEFT);
                }

                if (!"".equals(caseData.getEvtDieDate()) || !"".equals(caseData.getEvtMissingDate()) || !"".equals(caseData.getCloseDate()) || !"".equals(caseData.getCloseCause())) {
                    lineStatus = true;

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "死亡日期", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "失蹤日期", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "結案日期", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "結案原因 ", fontCh12, 0, LEFT);
                    addColumn(table, 4, 1, " ", fontCh12, 0, LEFT);

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (caseData.getEvtDieDate() != null)
                        addColumn(table, 12, 1, StringUtils.defaultString(caseData.getEvtDieDateString()), fontCh12, 0, LEFT); // 死亡日期
                    else
                        addColumn(table, 12, 1, " ", fontCh12, 0, LEFT); // 死亡日期

                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                    if (caseData.getEvtMissingDate() != null)
                        addColumn(table, 11, 1, StringUtils.defaultString(caseData.getEvtMissDateString()), fontCh12, 0, LEFT); // 失蹤日期
                    else
                        addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 失蹤日期
                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);

                    if (caseData.getCloseDate() != null)
                        addColumn(table, 11, 1, StringUtils.defaultString(caseData.getCloseDateString()), fontCh12, 0, LEFT); // 結案日期
                    else
                        addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 結案日期

                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);

                    if (caseData.getCloseCause() != null)
                        addColumn(table, 11, 1, caseData.getCloseCause(), fontCh12, 0, LEFT); // 結案原因
                    else
                        addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 結案原因

                    addColumn(table, 4, 1, " ", fontCh12, 0, LEFT);

                }
                // 在塞事故者編審註記資料前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);
                if (lineStatus) {
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

                //if ("38".equals(caseData.getPayKind())) {
                if (caseData.isContainCheckMark3M()) {
                    DisableReviewRpt01NpData38Case npIssuData = caseData.getNpData38();
                    List<DisableReviewRpt01NpData38Case> npData38List = caseData.getNpData38List();

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "國保受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "國保年資", fontCh12, 0, LEFT);
                    addColumn(table, 1, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "併計國保年資", fontCh12, 0, RIGHT);
                    addColumn(table, 23, 1, "　", fontCh12, 0, LEFT);

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, StringUtils.defaultString(npIssuData.getApNoStrDisplay()), fontCh12, 0, LEFT); // 國保受理編號
                    addColumn(table, 4, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, npIssuData.getValSeniDisplay(), fontCh12, 0, LEFT); // 國保年資
                    addColumn(table, 1, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, StringUtils.defaultString(npIssuData.getComnpMkStr()), fontCh12, 0, RIGHT); // 併計國保年資
                    addColumn(table, 23, 1, "　", fontCh12, 0, LEFT);

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "國保金額", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "在途保費", fontCh12, 0, RIGHT);
                    addColumn(table, 5, 1, "利息", fontCh12, 0, RIGHT);
                    addColumn(table, 5, 1, "應收", fontCh12, 0, RIGHT);
                    addColumn(table, 5, 1, "補發", fontCh12, 0, RIGHT);
                    addColumn(table, 5, 1, "減領", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "另案扣減", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "實付金額", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "審核結果", fontCh12, 0, RIGHT);
                    addColumn(table, 2, 1, "　", fontCh12, 0, LEFT);
                    
                    
                    if(npData38List.size() > 0){
                    for(DisableReviewRpt01NpData38Case npData38 : npData38List){

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, StringUtils.defaultString(npData38.getPayYmStr()), fontCh12, 0, LEFT); // 給付年月
                    addColumn(table, 6, 1, formatBigDecimalToInteger(npData38.getIssueAmt()), fontCh12, 0, RIGHT); // 國保金額
                    addColumn(table, 6, 1, formatBigDecimalToInteger(npData38.getSagtotAmt()), fontCh12, 0, RIGHT); // 在途保費
                    addColumn(table, 5, 1, formatBigDecimalToInteger(npData38.getItrtAmt()), fontCh12, 0, RIGHT); // 利息
                    addColumn(table, 5, 1, formatBigDecimalToInteger(npData38.getRecAmt()), fontCh12, 0, RIGHT); // 應收
                    addColumn(table, 5, 1, formatBigDecimalToInteger(npData38.getSupAmt()), fontCh12, 0, RIGHT); // 補發
                    addColumn(table, 5, 1, formatBigDecimalToInteger(npData38.getCutAmt()), fontCh12, 0, RIGHT); // 減領
                    addColumn(table, 6, 1, formatBigDecimalToInteger(npData38.getOtherAmt()), fontCh12, 0, RIGHT); // 另案扣減
                    addColumn(table, 6, 1, formatBigDecimalToInteger(npData38.getAplPayAmt()), fontCh12, 0, RIGHT); // 實付金額
                    addColumn(table, 6, 1, StringUtils.defaultString(npData38.getManChkFlg()), fontCh12, 0, RIGHT); // 審核結果
                    addColumn(table, 2, 1, "　", fontCh12, 0, LEFT);

                    // 在塞事故者編審註記資料前, 先隨便塞空白行測試是否需換頁
                    addEmptyRow(table, 1);
                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不印分隔線了
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                    }
                    }
                }else{
                	addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 給付年月
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // 國保金額
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // 在途保費
                    addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT); // 利息
                    addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT); // 應收
                    addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT); // 補發
                    addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT); // 減領
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // 另案扣減
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // 實付金額
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // 審核結果
                    addColumn(table, 2, 1, "　", fontCh12, 0, LEFT);
                }
                }
                //}

                // 事故者編審註記

                // [

                addColumn(table, 2, 1, " ", fontCh8, 0, LEFT);
                addColumn(table, 58, 1, "事故者編審註記：", fontCh12, 0, LEFT);
                // ---
                List<DisableReviewRpt01ChkfileDataCase> chkfileDataList = caseData.getChkfileDataList();
                if (chkfileDataList.size() > 0) {
                    // 取得事故者編審註記資料

                    String previousPayYm = "";
                    List<StringBuffer> chkfileStringList = new ArrayList<StringBuffer>();
                    StringBuffer chkfileString = new StringBuffer("");
                    for (DisableReviewRpt01ChkfileDataCase chkfileData : chkfileDataList) {
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
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 56, 1, str.toString(), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }
                }

                // 編審註記說明
                List<DisableReviewRpt01ChkfileDescCase> chkfileDescList = caseData.getChkfileDescList();
                for (DisableReviewRpt01ChkfileDescCase chkfileDesc : chkfileDescList) {
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
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "編審註記說明：", fontCh12, 0, LEFT);
                    addColumn(table, 46, 1, StringUtils.defaultString(chkfileDesc.getChkCode()) + " " + StringUtils.defaultString(chkfileDesc.getChkCodePost()) + " " + StringUtils.defaultString(chkfileDesc.getChkResult()), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 15, 1, "電腦編審結果：" + ((StringUtils.equalsIgnoreCase(caseData.getAcceptMk(), "Y")) ? "合格" : ((StringUtils.equalsIgnoreCase(caseData.getAcceptMk(), "N")) ? "不合格" : "待處理")), fontCh12, 0, LEFT);
                addColumn(table, 15, 1, "人工審核結果：" + ((StringUtils.equalsIgnoreCase(caseData.getManchkMk(), "Y")) ? "合格" : ((StringUtils.equalsIgnoreCase(caseData.getManchkMk(), "N")) ? "不合格" : "待處理")), fontCh12, 0, LEFT);
                addColumn(table, 26, 1, "＊", fontCh12, 0, LEFT);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

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
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 34, 1, "受理鍵入資料及修改紀錄：（鍵入／更正人員代號：" + ((StringUtils.isNotBlank(caseData.getUpdUser())) ? caseData.getUpdUser() : StringUtils.defaultString(caseData.getCrtUser())) + "）", fontCh12, 0, LEFT);
                addColumn(table, 12, 1, ((StringUtils.isNotBlank(caseData.getChgNote())) ? ("更正原因：" + caseData.getChgNote()) : ""), fontCh12, 0, LEFT); // 更正原因 - 有值才顯示
                addColumn(table, 12, 1, ((StringUtils.isNotBlank(caseData.getMexcLvl())) ? ("決行層級：" + caseData.getMexcLvl()) : ""), fontCh12, 0, LEFT); // 決行層級 - 有值才顯示
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
                    DisableReviewRpt01NotifyDataCase notifyData = caseData.getNotifyData();

                    // 受文者

                    StringBuffer receiveName = new StringBuffer("");
                    if (StringUtils.isNotBlank(caseData.getBenName()))
                        receiveName.append(caseData.getBenName());
                    if (caseData.getBenList() != null) {
                        for (DisableReviewRpt01BenDataCase benData : caseData.getBenList()) {
                            if (StringUtils.isNotBlank(benData.getBenName())) {
                                if (StringUtils.isNotBlank(receiveName.toString()))
                                    receiveName.append("、");
                                receiveName.append(benData.getBenName());
                            }
                        }
                    }

                    // 先試印核定通知書的 標題 受文者 地址 主旨 及 說明的第一點, 測試是否需換頁
                    // [
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, (disableList.get(0).getMonNotifyingMk().equals("N") || disableList.get(0).getMonNotifyingMk().equals("")) ? "＊不寄發月通知表" : "" , fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 15, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addEmptyRow(table, 1);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "受文者：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 50, 1, receiveName.toString(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "地址：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 50, 1, (StringUtils.defaultString(caseData.getCommZip()) + " " + StringUtils.defaultString(caseData.getCommAddr())).trim(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "主旨：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 49, 1, notifyData.getSubject(), fontCh12, 8, 0, JUSTIFIED, TOP); // 主旨
                    addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    if (notifyData.getContent() != null) {
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "說明：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        addColumnAssignVAlignmentAndLineSpace(table, 49, 1, notifyData.getContent().get(0), fontCh12, 8, 0, JUSTIFIED, TOP); // 說明
                        addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        if (notifyData.getContent() != null)
                            deleteRow(table, 6);
                        else
                            deleteRow(table, 5);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        if (notifyData.getContent() != null)
                            deleteRow(table, 6);
                        else
                            deleteRow(table, 5);
                    }
                    // ]

                    // 正式印核定通知書

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, (disableList.get(0).getMonNotifyingMk().equals("N") || disableList.get(0).getMonNotifyingMk().equals("")) ? "＊不寄發月通知表" : "" , fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 15, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addEmptyRow(table, 1);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "受文者：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 50, 1, receiveName.toString(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "地址：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 50, 1, (StringUtils.defaultString(caseData.getCommZip()) + " " + StringUtils.defaultString(caseData.getCommAddr())).trim(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "主旨：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 49, 1, notifyData.getSubject(), fontCh12, 8, 0, JUSTIFIED, TOP); // 主旨
                    addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 說明 的處理

                    for (int nContentCount = 0; nContentCount < notifyData.getContent().size(); nContentCount++) {
                        String content = notifyData.getContent().get(nContentCount);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        if (nContentCount == 0)
                            addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "說明：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        else
                            addColumnAssignVAlignmentAndLineSpace(table, 6, 1, " ", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        addColumnAssignVAlignmentAndLineSpace(table, 4, 1, content.substring(0, content.indexOf("、") + 1), fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        addColumnAssignVAlignmentAndLineSpace(table, 45, 1, content.substring(content.indexOf("、") + 1, content.length()), fontCh12, 8, 0, JUSTIFIED, TOP); // 說明
                        addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            // 換了頁要再印一次

                            if (nContentCount == 0)
                                addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "說明：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                            else
                                addColumnAssignVAlignmentAndLineSpace(table, 6, 1, " ", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                            addColumnAssignVAlignmentAndLineSpace(table, 4, 1, content.substring(0, content.indexOf("、") + 1), fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                            addColumnAssignVAlignmentAndLineSpace(table, 45, 1, content.substring(content.indexOf("、") + 1, content.length()), fontCh12, 8, 0, JUSTIFIED, TOP); // 說明
                            addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, (disableList.get(0).getMonNotifyingMk().equals("N") || disableList.get(0).getMonNotifyingMk().equals("")) ? "＊不寄發月通知表" : "" , fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 15, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, (disableList.get(0).getMonNotifyingMk().equals("N") || disableList.get(0).getMonNotifyingMk().equals("")) ? "＊不寄發月通知表" : "" , fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 15, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // 印完核定通知書後強制換頁
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                // ]
                // 核定通知書

                // 請領同類給付資料
                // [
    	        DisableReviewSameKind sameKind = new DisableReviewSameKind();
                table = sameKind.execute(caseData, table, earlyWarning);
                sameKind = null;

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
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 58, 1, "請領同類給付資料：", fontCh12b, 0, LEFT);

                // 一次給付資料 (有資料再印)
                List<DisableReviewRpt01OncePayDataCase> oncePayList = caseData.getOncePayList();
                for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++) { // ... [
                    DisableReviewRpt01OncePayDataCase oncePayData = oncePayList.get(nOncePayCount);

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
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "[一次給付]", fontCh12b, 0, LEFT);
                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // addEmptyRow(table, 8);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 8);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 8);
                    // }
                    // 20101124 kiyomi - mark end
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "診斷失能日", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, oncePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
                    addColumn(table, 10, 1, oncePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 9, 1, oncePayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
                    addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 診斷失能日

                    addColumn(table, 9, 1, oncePayData.getBmEvType(), fontCh12, 0, LEFT); // 診斷失能日

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核付日數", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "失能等級", fontCh12, 0, LEFT);
                    addColumn(table, 37, 1, "失能項目", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, formatBigDecimalToInteger(oncePayData.getBmChkDay()), fontCh12, 0, LEFT); // 核定日數
                    addColumn(table, 9, 1, oncePayData.getBmCriInjNme(), fontCh12, 0, LEFT); // 失能等級
                    addColumn(table, 37, 1, oncePayData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 18, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (oncePayData.getBmChkAmt() != null && oncePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 9, 1, formatBigDecimalToInteger(oncePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 9, 1, "0", fontCh12, 0, LEFT); // 核定金額
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, StringUtils.defaultString(oncePayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 10, 1, StringUtils.defaultString(oncePayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 18, 1, oncePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(oncePayData.getBmNdocMk())) ? " / " + oncePayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 11, 1, "", fontCh12, 0, LEFT);

                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 19, 1, "不給付日期/原因", fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "補收金額—已收金額", fontCh12, 0, LEFT);
                    addColumn(table, 19, 1, " ", fontCh12, 0, LEFT);
                    // ---

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 19, 1, StringUtils.defaultString(oncePayData.getBmNopDateString()) + ((StringUtils.isNotBlank(oncePayData.getBmNopMark())) ? " / " + oncePayData.getBmNopMark() : ""), fontCh12, 0, LEFT); // 不給付日期/原因
                    if (oncePayData.getBmAdjAmts() != null && oncePayData.getBmAdjAmts().compareTo(new BigDecimal(0)) != 0) {
                        addColumn(table, 10, 1, formatBigDecimalToInteger(oncePayData.getBmAdjAmts()), fontCh12, 0, LEFT); // 補收金額
                        addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    }
                    else {
                        addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 補收金額
                    }
                    addColumn(table, 28, 1, " ", fontCh12, 0, LEFT);

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

                // 申請農保殘廢給付記錄 (有資料再印)
                List<DisableReviewRpt01OncePayDataCase> farmPayList = caseData.getFarmPayList();
                for (int nFarmPayCount = 0; nFarmPayCount < farmPayList.size(); nFarmPayCount++) { // ... [
                    DisableReviewRpt01OncePayDataCase farmPayData = farmPayList.get(nFarmPayCount);

                    // 印申請農保殘廢給付記錄表頭

                    if (nFarmPayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 申請農保殘廢給付記錄 表 頭

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "申請農保身心障礙給付記錄：", fontCh12b, 0, LEFT);
                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // addEmptyRow(table, 8);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 8);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 8);
                    // }
                    // 20101124 kiyomi - mark end
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "審殘日期", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, farmPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 9, 1, StringUtils.defaultString(farmPayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
                    addColumn(table, 10, 1, farmPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 9, 1, farmPayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
                    addColumn(table, 9, 1, StringUtils.defaultString(farmPayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 審殘日期
                    addColumn(table, 11, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核付日數", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "殘廢等級", fontCh12, 0, LEFT);
                    addColumn(table, 37, 1, "殘廢項目", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, formatBigDecimalToInteger(farmPayData.getBmChkDay()), fontCh12, 0, LEFT); // 核付日數
                    addColumn(table, 9, 1, farmPayData.getBmCriInjNme(), fontCh12, 0, LEFT); // 失能等級
                    addColumn(table, 37, 1, farmPayData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 19, 1, " ", fontCh12, 0, LEFT);

                    // ---

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (farmPayData.getBmChkAmt() != null && farmPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 9, 1, formatBigDecimalToInteger(farmPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 9, 1, "0", fontCh12, 0, LEFT); // 核定金額
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);

                    addColumn(table, 9, 1, StringUtils.defaultString(farmPayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 10, 1, StringUtils.defaultString(farmPayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 10, 1, farmPayData.getBmNrepDateString() + ((StringUtils.isNotBlank(farmPayData.getBmNdocMk())) ? " / " + farmPayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 19, 1, " ", fontCh12, 0, LEFT);

                    // ---

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 19, 1, "不給付日期/原因", fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "補收金額—已收金額", fontCh12, 0, LEFT);
                    addColumn(table, 19, 1, " ", fontCh12, 0, LEFT);
                    // ---

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 19, 1, StringUtils.defaultString(farmPayData.getBmNopDateString()) + ((StringUtils.isNotBlank(farmPayData.getBmNopMark())) ? " / " + farmPayData.getBmNopMark() : ""), fontCh12, 0, LEFT); // 不給付日期/原因
                    if (farmPayData.getBmAdjAmts() != null && farmPayData.getBmAdjAmts().compareTo(new BigDecimal(0)) != 0) {
                        addColumn(table, 10, 1, formatBigDecimalToInteger(farmPayData.getBmAdjAmts()), fontCh12, 0, LEFT); // 補收金額
                        addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    }
                    else {
                        addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 補收金額
                    }
                    addColumn(table, 28, 1, " ", fontCh12, 0, LEFT);

                    // 最後一筆印完後空一行 (如果年金給付資料有資料再印)
                    if ((nFarmPayCount == farmPayList.size() - 1) && (caseData.getFarmPayList().size() > 0)) {
                        // 空白行

                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了

                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }
                }

                // 年金給付資料 (有資料再印)
                List<DisableReviewRpt01AnnuityPayDataCase> annuityPayList = caseData.getAnnuityPayList();
                for (int nAnnuityPayCount = 0; nAnnuityPayCount < annuityPayList.size(); nAnnuityPayCount++) { // ... [
                    DisableReviewRpt01AnnuityPayDataCase annuityPayData = annuityPayList.get(nAnnuityPayCount);

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
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "[年金給付]", fontCh12b, 0, LEFT);
                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // 年金給付資料一筆有四行, 在塞資料前先測試是否需換頁
                    // addEmptyRow(table, 6);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 6);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 6);
                    // }
                    // 20101124 kiyomi - mark end
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "首次給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "診斷失能日", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, annuityPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getAppDateString()), fontCh12, 0, LEFT); // 申請日期
                    addColumn(table, 10, 1, annuityPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 9, 1, annuityPayData.getApUbno(), fontCh12, 0, LEFT); // 保險證號
                    addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getPayYmsString()) + ((StringUtils.isNotBlank(annuityPayData.getPayYmsString())) ? "-" + StringUtils.defaultString(annuityPayData.getPayYmString()) : ""), fontCh12,
                                    0, LEFT); // 首次給付年月
                    addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getEvtJobDateString()), fontCh12, 0, LEFT); // 診斷失能日

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "傷病分類", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "失能等級", fontCh12, 0, LEFT);
                    addColumn(table, 37, 1, "失能項目", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, annuityPayData.getEvTyp(), fontCh12, 0, LEFT); // 傷病分類
                    addColumn(table, 9, 1, annuityPayData.getCriInJclStr(), fontCh12, 0, LEFT); // 失能等級
                    addColumn(table, 37, 1, annuityPayData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 27, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (annuityPayData.getIssueAmt() != null && annuityPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 9, 1, formatBigDecimalToInteger(annuityPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 9, 1, "0", fontCh12, 0, LEFT); // 核定金額
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, StringUtils.defaultString(annuityPayData.getChkDateString()), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 10, 1, StringUtils.defaultString(annuityPayData.getAplpayDateString()), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 27, 1, StringUtils.defaultString(annuityPayData.getCloseDateString()) + ((StringUtils.isNotBlank(annuityPayData.getCloseCause())) ? (" / " + annuityPayData.getCloseCause()) : ""), fontCh12, 0, LEFT); // 結案日期/原因
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 最後一筆印完後空一行 (如果年金給付資料有資料再印)
                    if ((nAnnuityPayCount == annuityPayList.size() - 1) && (caseData.getAnnuityPayList().size() > 0)) {
                        // 空白行

                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了

                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }

                } // ] ... end for (int nAnnuityPayCount = 0; nAnnuityPayCount < annuityPayList.size(); nAnnuityPayCount++)

                // 申請國保給付記錄資料 (有資料再印)
                List<DisableReviewRpt01NpPayDataCase> npDisPayList = caseData.getNbDisPayList();
                for (int nNpDisPayCount = 0; nNpDisPayCount < npDisPayList.size(); nNpDisPayCount++) { // ... [
                    DisableReviewRpt01NpPayDataCase npDisPayData = npDisPayList.get(nNpDisPayCount);

                    // 印國保給付記錄表頭

                    if (nNpDisPayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // 國保給付記錄 表頭
                        addColumn(table, 58, 1, "申請國保身障年金給付記錄：", fontCh12b, 0, LEFT);

                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // 國保給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    // addEmptyRow(table, 4);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 4);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 4);
                    // }
                    // 20101124 kiyomi - mark end
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "申請日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "首次給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "手冊鑑定日期", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, npDisPayData.getEvteeName(), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 11, 1, npDisPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
                    addColumn(table, 11, 1, npDisPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 11, 1, StringUtils.defaultString(npDisPayData.getPayYmsString()) + ((StringUtils.isNotBlank(npDisPayData.getPayYmsString())) ? "-" + StringUtils.defaultString(npDisPayData.getPayYmeString()) : ""), fontCh12, 0,
                                    LEFT); // 首次給付年月
                    addColumn(table, 11, 1, StringUtils.defaultString(npDisPayData.getHandIcApDateString()), fontCh12, 0, LEFT); // 手冊鑑定日期
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (npDisPayData.getIssueAmt() != null && npDisPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 6, 1, formatBigDecimalToInteger(npDisPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, npDisPayData.getChkDtString(), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 11, 1, npDisPayData.getAplPayDateString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 10, 1, StringUtils.defaultString(npDisPayData.getCloseDtString() + ((StringUtils.isNotBlank(npDisPayData.getCloseReason())) ? " / " + npDisPayData.getCloseReason() : "")), fontCh12, 0, LEFT); // 結案日期/結案原因
                    addColumn(table, 12, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---

                    // ---
                    // 最後一筆印完後空一行 (如果年金給付資料有資料再印)
                    if ((nNpDisPayCount == npDisPayList.size() - 1) && (caseData.getNbDisPayList().size() > 0)) {
                        // 空白行

                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了

                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }

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
                // 請領同類給付資料

                // 請領他類給付資料
                // [
    	        DisableReviewOtherKind otherKind = new DisableReviewOtherKind();
                table = otherKind.execute(caseData, table, earlyWarning);
                otherKind = null;
                
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

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 58, 1, "請領他類給付資料：", fontCh12b, 0, LEFT);

                // 申請老年年金給付(有資料再印)
                List<DisableReviewRpt01OldAgePayDataCase> oldAgePayCaseList = caseData.getOldAgePayList();
                if (caseData.getOldAgePayList() != null) {
                    for (int nDisablePayCount = 0; nDisablePayCount < oldAgePayCaseList.size(); nDisablePayCount++) { // ... [

                        DisableReviewRpt01OldAgePayDataCase disablePayData = oldAgePayCaseList.get(nDisablePayCount);

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
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 58, 1, "申請老年年金給付記錄：", fontCh12b, 0, LEFT);
                        }
                        else {
                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                            else {
                                deleteRow(table, 1);
                                addEmptyRow(table, 1);
                            }
                        }

                        // 20101124 kiyomi - mark start
                        // 失能給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                        // addEmptyRow(table, 4);

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // deleteRow(table, 4);
                        // document.add(table);
                        // table = addHeader(caseData, false, earlyWarning);
                        // }
                        // else {
                        // deleteRow(table, 4);
                        // }
                        // 20101124 kiyomi - mark end
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "申請日期", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "首次給付年月", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "事故日期", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 12, 1, disablePayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名

                        addColumn(table, 11, 1, disablePayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
                        addColumn(table, 11, 1, disablePayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 11, 1, StringUtils.defaultString(disablePayData.getPayYmsString()) + ((StringUtils.isNotBlank(disablePayData.getPayYmsString())) ? "-" + StringUtils.defaultString(disablePayData.getPayYmString()) : ""),
                                        fontCh12, 0, LEFT); // 首次給付年月
                        addColumn(table, 11, 1, StringUtils.defaultString(disablePayData.getEvtJobDateString()), fontCh12, 0, LEFT); // 事故日期
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 12, 1, "核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---

                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        if (disablePayData.getIssueAmt() != null && disablePayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 12, 1, formatBigDecimalToInteger(disablePayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                        else
                            addColumn(table, 12, 1, " ", fontCh12, 0, LEFT); // 核定金額

                        addColumn(table, 11, 1, StringUtils.defaultString(disablePayData.getChkDateString()), fontCh12, 0, LEFT); // 核定日期
                        addColumn(table, 11, 1, StringUtils.defaultString(disablePayData.getAplpayDateString()), fontCh12, 0, LEFT); // 核付日期
                        addColumn(table,
                                        11,
                                        1,
                                        disablePayData.getProdateString()
                                                        + ((StringUtils.isNotBlank(disablePayData.getNdomk1())) ? ((StringUtils.isNotBlank(disablePayData.getNdomk1())) ? (" / " + disablePayData.getNdomk1()) : disablePayData.getNdomk1()) : ""),
                                        fontCh12, 0, LEFT); // 補件日期/註記
                        addColumn(table, 11, 1, StringUtils.defaultString(disablePayData.getCloseDateString()) + ((StringUtils.isNotBlank(disablePayData.getCloseCause())) ? " / " + disablePayData.getCloseCause() : ""), fontCh12, 0, LEFT); // 結案日期/原因

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                        if ((nDisablePayCount == oldAgePayCaseList.size() - 1) && caseData.getOldAgePayList().size() > 0) {
                            // 空白行

                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                // 換了頁就不再塞空白行了

                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                        }
                    }
                }// ] ... end for (int nDisablePayCount = 0; nDisablePayCount < disablePayList.size(); nDisablePayCount++)

                // 申請遺屬年金給付(有資料再印)
                List<DisableReviewRpt01SurvivorPayDataCase> survivorPayCaseList = caseData.getSurvivorPayList();
                if (caseData.getSurvivorPayList() != null) {
                    for (int nDisablePayCount = 0; nDisablePayCount < survivorPayCaseList.size(); nDisablePayCount++) { // ... [

                        DisableReviewRpt01SurvivorPayDataCase disablePayData = survivorPayCaseList.get(nDisablePayCount);

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
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 58, 1, "申請遺屬年金給付記錄：", fontCh12b, 0, LEFT);
                        }
                        else {
                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                            else {
                                deleteRow(table, 1);
                                addEmptyRow(table, 1);
                            }
                        }

                        // 20101124 kiyomi - mark start
                        // 失能給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                        // addEmptyRow(table, 4);

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // deleteRow(table, 4);
                        // document.add(table);
                        // table = addHeader(caseData, false, earlyWarning);
                        // }
                        // else {
                        // deleteRow(table, 4);
                        // }
                        // 20101124 kiyomi - mark end
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "首次給付年月", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "死亡日期 ", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, disablePayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名

                        addColumn(table, 9, 1, disablePayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
                        addColumn(table, 10, 1, disablePayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getPayYmsString()) + ((StringUtils.isNotBlank(disablePayData.getPayYmsString())) ? "-" + StringUtils.defaultString(disablePayData.getPayYmString()) : ""),
                                        fontCh12, 0, LEFT); // 首次給付年月
                        addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getEvtJobDateString()), fontCh12, 0, LEFT); // 死亡日期
                        addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getEvTyp()), fontCh12, 0, LEFT); // 傷病分類
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---

                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        if (disablePayData.getIssueAmt() != null && disablePayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 10, 1, formatBigDecimalToInteger(disablePayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                        else
                            addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額

                        addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getChkDateString()), fontCh12, 0, LEFT); // 核定日期
                        addColumn(table, 10, 1, StringUtils.defaultString(disablePayData.getAplpayDateString()), fontCh12, 0, LEFT); // 核付日期
                        addColumn(table,
                                        9,
                                        1,
                                        disablePayData.getProdateString()
                                                        + ((StringUtils.isNotBlank(disablePayData.getNdomk1())) ? ((StringUtils.isNotBlank(disablePayData.getNdomk1())) ? (" / " + disablePayData.getNdomk1()) : disablePayData.getNdomk1()) : ""),
                                        fontCh12, 0, LEFT); // 補件日期/註記
                        addColumn(table, 9, 1, StringUtils.defaultString(disablePayData.getCloseDateString()) + ((StringUtils.isNotBlank(disablePayData.getCloseCause())) ? " / " + disablePayData.getCloseCause() : ""), fontCh12, 0, LEFT); // 結案日期/原因
                        addColumn(table, 9, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                        if ((nDisablePayCount == survivorPayCaseList.size() - 1) && caseData.getSurvivorPayList().size() > 0) {
                            // 空白行

                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                // 換了頁就不再塞空白行了

                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                        }
                    }
                }// ] ... end for (int nDisablePayCount = 0; nDisablePayCount < disablePayList.size(); nDisablePayCount++)

                // 申請職災住院醫療給付記錄
                List<DisableReviewRpt01OncePayDataCase> hosPayList = caseData.getHosPayList();
                if (caseData.getHosPayList() != null) {
                    for (int nHosPayCount = 0; nHosPayCount < hosPayList.size(); nHosPayCount++) { // ... [
                        DisableReviewRpt01OncePayDataCase hosPayData = hosPayList.get(nHosPayCount);

                        // 印年金給付表頭

                        if (nHosPayCount == 0) {
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
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 58, 1, "申請職災住院醫療給付記錄:", fontCh12b, 0, LEFT);
                        }
                        else {
                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                            else {
                                deleteRow(table, 1);
                                addEmptyRow(table, 1);
                            }
                        }

                        // 20101124 kiyomi - mark start
                        // 年金給付資料一筆有四行, 在塞資料前先測試是否需換頁
                        // addEmptyRow(table, 4);

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // deleteRow(table, 4);
                        // document.add(table);
                        // table = addHeader(caseData, false, earlyWarning);
                        // }else {
                        // deleteRow(table, 4);
                        // }
                        // 20101124 kiyomi - mark end

                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
                        addColumn(table, 20, 1, "事故日期", fontCh12, 0, LEFT);

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, hosPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名

                        addColumn(table, 9, 1, hosPayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
                        addColumn(table, 9, 1, hosPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 9, 1, hosPayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
                        addColumn(table, 20, 1, hosPayData.getBmEvtDte(), fontCh12, 0, LEFT); // 事故日期

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "住院始日", fontCh12, 0, LEFT);
                        ;
                        addColumn(table, 9, 1, "傷病原因", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受傷部位", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
                        addColumn(table, 22, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, hosPayData.getBmInjPfmDteString(), fontCh12, 0, LEFT); // 住院始日
                        addColumn(table, 9, 1, hosPayData.getBmEvCode(), fontCh12, 0, LEFT); // 傷病原因
                        addColumn(table, 9, 1, hosPayData.getBmInjInPart(), fontCh12, 0, LEFT); // 受傷部位
                        addColumn(table, 9, 1, hosPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
                        addColumn(table, 22, 1, " ", fontCh12, 0, LEFT);

                        // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                        if ((nHosPayCount == hosPayList.size() - 1) && (caseData.getHosPayList().size() > 0)) {
                            // 空白行

                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                // 換了頁就不再塞空白行了

                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                        }

                    } // ] ... end for (int nDisPayCount = 0; nDisPayCount < getDisPayList.size(); nDisPayCount++)
                }

                // 申請傷病給付記錄資料 (有資料再印)
                List<DisableReviewRpt01InjuryPayDataCase> injuryPayList = caseData.getInjuryPayList();
                for (int nInjuryPayCount = 0; nInjuryPayCount < injuryPayList.size(); nInjuryPayCount++) { // ... [
                    DisableReviewRpt01InjuryPayDataCase injuryPayData = injuryPayList.get(nInjuryPayCount);

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
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "申請傷病給付記錄：", fontCh12b, 0, LEFT);
                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // 傷病給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    // addEmptyRow(table, 4);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 4);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 4);
                    // }
                    // 20101124 kiyomi - mark end

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "事故日期", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "傷病分類", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "給付起日-迄日", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh8, 0, LEFT);

                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, injuryPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 9, 1, injuryPayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
                    addColumn(table, 10, 1, injuryPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 9, 1, injuryPayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期
                    addColumn(table, 6, 1, injuryPayData.getBmEvType(), fontCh12, 0, LEFT); // 傷病分類
                    // addColumn(table, 12, 1, injuryPayData.getBmInjPfmDteString() + ((StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) ? "-" +injuryPayData.getBmInjPtoDteString(): ""), fontCh12, 0, LEFT); // 給付起日-迄日
                    addColumn(table, 12, 1, injuryPayData.getBmInjPfmDteString(), fontCh12, 0, LEFT); // 給付起日-迄日
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 20111012 by Kiyomi 將起日-迄日中的迄日另外獨立一行列印

                    if (StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) {
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 事故者姓名

                        addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 受理日期
                        addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 事故日期
                        addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 傷病分類
                        addColumn(table, 12, 1, ((StringUtils.isNotBlank(injuryPayData.getBmInjPtoDte())) ? "-" + injuryPayData.getBmInjPtoDteString() : ""), fontCh12, 0, LEFT); // 給付起日-迄日
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (injuryPayData.getBmChkAmt() != null && injuryPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 10, 1, formatBigDecimalToInteger(injuryPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額

                    addColumn(table, 9, 1, injuryPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期

                    addColumn(table, 10, 1, injuryPayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 9, 1, injuryPayData.getBmNrepDateString()
                                    + ((StringUtils.isNotBlank(injuryPayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNrepDateString())) ? (" / " + injuryPayData.getBmNdocMk()) : injuryPayData.getBmNdocMk()) : ""), fontCh12, 0,
                                    LEFT); // 補件日期/註記
                    addColumn(table, 9, 1, injuryPayData.getBmNopDateString()
                                    + ((StringUtils.isNotBlank(injuryPayData.getBmNopMark())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNopDateString())) ? (" / " + injuryPayData.getBmNopMark()) : injuryPayData.getBmNopMark()) : ""), fontCh12,
                                    0, LEFT); // 不給付日期

                    addColumn(table, 9, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nInjuryPayCount == injuryPayList.size() - 1) && caseData.getInjuryPayList().size() > 0) {
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

                // 申請老年給付記錄 (有資料再印)
                List<DisableReviewRpt01OldPayDataCase> oldAgePayList = caseData.getOldPayList();
                for (int nInjuryPayCount = 0; nInjuryPayCount < oldAgePayList.size(); nInjuryPayCount++) { // ... [
                    DisableReviewRpt01OldPayDataCase injuryPayData = oldAgePayList.get(nInjuryPayCount);

                    // 印老年給付記錄表頭
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
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "申請老年給付記錄：", fontCh12b, 0, LEFT);
                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // 老年給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    // addEmptyRow(table, 4);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 4);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 4);
                    // }
                    // 20101124 kiyomi - mark end
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "事故日期 ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核定金額 ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, injuryPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 11, 1, injuryPayData.getBmApDteString(), fontCh12, 0, LEFT); // 申請日期
                    addColumn(table, 11, 1, injuryPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 11, 1, StringUtils.defaultString(injuryPayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 事故日期
                    if (injuryPayData.getBmChkAmt() != null && injuryPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 11, 1, formatBigDecimalToInteger(injuryPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 核定金額
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "核定日期 ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, injuryPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 11, 1, injuryPayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 11, 1, injuryPayData.getBmNrepDateString()
                                    + ((StringUtils.isNotBlank(injuryPayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNrepDateString())) ? (" / " + injuryPayData.getBmNdocMk()) : injuryPayData.getBmNdocMk()) : ""), fontCh12, 0,
                                    LEFT); // 補件日期/註記
                    addColumn(table, 11, 1, injuryPayData.getBmNopDateString()
                                    + ((StringUtils.isNotBlank(injuryPayData.getBmNopMark())) ? ((StringUtils.isNotBlank(injuryPayData.getBmNopMark())) ? (" / " + injuryPayData.getBmNopMark()) : injuryPayData.getBmNopMark()) : ""), fontCh12, 0, LEFT); // 不給付日期

                    addColumn(table, 11, 1, "　", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nInjuryPayCount == oldAgePayList.size() - 1) && caseData.getOldPayList().size() > 0) {
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

                // 申請死亡給付記錄資料 (有資料再印)
                List<DisableReviewRpt01DiePayDataCase> diePayList = caseData.getDiePayList();
                for (int nDiePayCount = 0; nDiePayCount < diePayList.size(); nDiePayCount++) { // ... [
                    DisableReviewRpt01DiePayDataCase diePayData = diePayList.get(nDiePayCount);

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
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "申請死亡給付記錄：", fontCh12b, 0, LEFT);
                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // 死亡給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    // addEmptyRow(table, 4);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 4);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 4);
                    // }
                    // 20101124 kiyomi - mark end

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "死亡日期", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "申請項目", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, diePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
                    addColumn(table, 10, 1, diePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 死亡日期
                    addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmEvType()), fontCh12, 0, LEFT); // 傷病分類
                    addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmDeaapItem()), fontCh12, 0, LEFT); // 申請項目
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核定金額 ", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "核定日期 ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
                    addColumn(table, 5, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (diePayData.getBmChkAmt() != null && diePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 10, 1, formatBigDecimalToInteger(diePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 核定金額

                    addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 10, 1, StringUtils.defaultString(diePayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期

                    addColumn(table, 11, 1,
                                    diePayData.getBmNrepDateString()
                                                    + ((StringUtils.isNotBlank(diePayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(diePayData.getBmNrepDateString())) ? (" / " + diePayData.getBmNdocMk()) : diePayData.getBmNdocMk()) : ""), fontCh12,
                                    0, LEFT); // 補件日期/註記
                    addColumn(table, 9, 1, StringUtils.defaultString(diePayData.getBmNopDateString()), fontCh12, 0, LEFT); // 不給付日期

                    addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nDiePayCount == diePayList.size() - 1) && caseData.getDiePayList().size() > 0) {
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

                // 申請失蹤給付記錄資料 (有資料再印)
                List<DisableReviewRpt01DiePayDataCase> rptDisPayList = caseData.getDisPayList();
                for (int nDisPayCount = 0; nDisPayCount < rptDisPayList.size(); nDisPayCount++) { // ... [
                    DisableReviewRpt01DiePayDataCase disPayData = rptDisPayList.get(nDisPayCount);

                    // 印失蹤給付記錄表頭

                    if (nDisPayCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 失蹤給付記錄 表頭
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "申請失蹤給付記錄：", fontCh12b, 0, LEFT);
                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // 失蹤給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    // addEmptyRow(table, 4);
                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 4);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 4);
                    // }
                    // 20101124 kiyomi - mark end
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "事故日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "申請起日—迄日", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, StringUtils.defaultString(disPayData.getBmEvName()), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
                    addColumn(table, 11, 1, disPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 事故日期

                    addColumn(table,
                                    11,
                                    1,
                                    StringUtils.defaultString(disPayData.getBmLosFmDteString()
                                                    + ((StringUtils.isNotBlank(disPayData.getBmLosToDteString())) ? ((StringUtils.isNotBlank(disPayData.getBmLosToDteString())) ? (" - " + disPayData.getBmLosToDteString()) : disPayData
                                                                    .getBmLosToDteString()) : "")), fontCh12, 0, LEFT); // 申請起日
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (disPayData.getBmChkAmt() != null && disPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 12, 1, formatBigDecimalToInteger(disPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 12, 1, " ", fontCh12, 0, LEFT); // 核定金額

                    addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 11, 1, disPayData.getBmNrepDateString() + ((StringUtils.isNotBlank(disPayData.getBmNdocMk())) ? " / " + disPayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 11, 1, StringUtils.defaultString(disPayData.getBmNopDateString() + ((StringUtils.isNotBlank(disPayData.getBmNdocMk())) ? " / " + disPayData.getBmNdocMk() : "")), fontCh12, 0, LEFT); // 不給付日期

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nDisPayCount == rptDisPayList.size() - 1) && caseData.getDisPayList().size() > 0) {
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

                // 申請農保死亡給付記錄資料 (有資料再印)
                List<DisableReviewRpt01DiePayDataCase> famDiePayList = caseData.getFamDiePayList();
                for (int nDiePayCount = 0; nDiePayCount < famDiePayList.size(); nDiePayCount++) { // ... [
                    DisableReviewRpt01DiePayDataCase famDiePayData = famDiePayList.get(nDiePayCount);

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
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "申請農保死亡給付記錄：", fontCh12b, 0, LEFT);
                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // 死亡給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    // addEmptyRow(table, 4);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 4);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 4);
                    // }
                    // 20101124 kiyomi - mark end
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "死亡日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, StringUtils.defaultString(famDiePayData.getBmEvName()), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 11, 1, famDiePayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
                    addColumn(table, 11, 1, famDiePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 11, 1, StringUtils.defaultString(famDiePayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 死亡日期

                    addColumn(table,
                                    11,
                                    1,
                                    StringUtils.defaultString(famDiePayData.getBmLosFmDteString()
                                                    + ((StringUtils.isNotBlank(famDiePayData.getBmLosToDteString())) ? ((StringUtils.isNotBlank(famDiePayData.getBmLosToDteString())) ? (" - " + famDiePayData.getBmLosToDteString()) : famDiePayData
                                                                    .getBmLosToDteString()) : "")), fontCh12, 0, LEFT); // 申請起日
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (famDiePayData.getBmChkAmt() != null && famDiePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 12, 1, formatBigDecimalToInteger(famDiePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 12, 1, " ", fontCh12, 0, LEFT); // 核定金額

                    addColumn(table, 11, 1, StringUtils.defaultString(famDiePayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 11, 1, StringUtils.defaultString(famDiePayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 11, 1, famDiePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(famDiePayData.getBmNdocMk())) ? " / " + famDiePayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 11, 1, StringUtils.defaultString(famDiePayData.getBmNopDateString() + ((StringUtils.isNotBlank(famDiePayData.getBmNdocMk())) ? " / " + famDiePayData.getBmNdocMk() : "")), fontCh12, 0, LEFT); // 不給付日期

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nDiePayCount == famDiePayList.size() - 1) && caseData.getFamDiePayList().size() > 0) {
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

                // 申請失業給付記錄資料 (有資料再印)
                List<DisableReviewRpt01JoblessPayDataCase> joblessPayList = caseData.getJoblessPayList();
                for (int nJoblessPayCount = 0; nJoblessPayCount < joblessPayList.size(); nJoblessPayCount++) { // ... [
                    DisableReviewRpt01JoblessPayDataCase joblessPayData = joblessPayList.get(nJoblessPayCount);

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
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "申請失業給付記錄：", fontCh12b, 0, LEFT);
                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // 失業給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    // addEmptyRow(table, 4);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 4);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 4);
                    // }
                    // 20101124 kiyomi - mark end
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "求職日期", fontCh12, 0, LEFT);
                    addColumn(table, 14, 1, "給付起日-迄日", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, joblessPayData.getName(), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 11, 1, joblessPayData.getApDteString(), fontCh12, 0, LEFT); // 受理日期
                    addColumn(table, 11, 1, joblessPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 9, 1, joblessPayData.getAprDteString(), fontCh12, 0, LEFT); // 求職日期
                    addColumn(table, 14, 1, joblessPayData.getSymDteString() + ((StringUtils.isNotBlank(joblessPayData.getTymDte())) ? "-" + joblessPayData.getTymDteString() : ""), fontCh12, 0, LEFT); // 給付起日-
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 14, 1, "不給付日期", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (joblessPayData.getChkAmt() != null && joblessPayData.getChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 11, 1, formatBigDecimalToInteger(joblessPayData.getChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 核定金額

                    addColumn(table, 11, 1, joblessPayData.getChkDteString(), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 11, 1, joblessPayData.getPayDteString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 9, 1, joblessPayData.getSavDt1String() + ((StringUtils.isNotBlank(joblessPayData.getNdcMrk())) ? " / " + joblessPayData.getNdcMrk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 14, 1, joblessPayData.getNpyDteString(), fontCh12, 0, LEFT); // 不給付日期

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nJoblessPayCount == joblessPayList.size() - 1) && (caseData.getJoblessPayList().size() > 0)) {
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

                // 申請職業訓練生活津貼記錄資料 (有資料再印)
                List<DisableReviewRpt01JoblessPayDataCase> vocationalTrainingLivingAllowanceList = caseData.getVocationalTrainingLivingAllowanceList();
                for (int nCount = 0; nCount < vocationalTrainingLivingAllowanceList.size(); nCount++) { // ... [
                    DisableReviewRpt01JoblessPayDataCase vocationalTrainingLivingAllowanceData = vocationalTrainingLivingAllowanceList.get(nCount);

                    // 印職業訓練生活津貼記錄表頭

                    if (nCount == 0) {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }

                        // 職業訓練生活津貼記錄 表頭
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "申請職業訓練生活津貼記錄：", fontCh12b, 0, LEFT);
                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // 職業訓練生活津貼記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    // addEmptyRow(table, 4);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 4);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 4);
                    // }
                    // 20101124 kiyomi - mark end
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "離職日期", fontCh12, 0, LEFT);
                    addColumn(table, 14, 1, "給付起日-迄日", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, vocationalTrainingLivingAllowanceData.getName(), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 11, 1, vocationalTrainingLivingAllowanceData.getApDteString(), fontCh12, 0, LEFT); // 受理日期
                    addColumn(table, 11, 1, vocationalTrainingLivingAllowanceData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 9, 1, vocationalTrainingLivingAllowanceData.getTdteString(), fontCh12, 0, LEFT); // 離職日期
                    addColumn(table, 14, 1, vocationalTrainingLivingAllowanceData.getSymDteString() + ((StringUtils.isNotBlank(vocationalTrainingLivingAllowanceData.getTymDte())) ? "-" + vocationalTrainingLivingAllowanceData.getTymDteString() : ""), fontCh12, 0, LEFT); // 給付起日-
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 14, 1, "不給付日期", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (vocationalTrainingLivingAllowanceData.getChkAmt() != null && vocationalTrainingLivingAllowanceData.getChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 11, 1, formatBigDecimalToInteger(vocationalTrainingLivingAllowanceData.getChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 11, 1, " ", fontCh12, 0, LEFT); // 核定金額

                    addColumn(table, 11, 1, vocationalTrainingLivingAllowanceData.getChkDteString(), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 11, 1, vocationalTrainingLivingAllowanceData.getPayDteString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 9, 1, vocationalTrainingLivingAllowanceData.getSavDt1String() + ((StringUtils.isNotBlank(vocationalTrainingLivingAllowanceData.getNdcMrk())) ? " / " + vocationalTrainingLivingAllowanceData.getNdcMrk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 14, 1, vocationalTrainingLivingAllowanceData.getNpyDteString(), fontCh12, 0, LEFT); // 不給付日期

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nCount == vocationalTrainingLivingAllowanceList.size() - 1) && (caseData.getJoblessPayList().size() > 0)) {
                        // 空白行

                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了

                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }
                } // ] ... end for (int nCount = 0; nCount < vocationalTrainingLivingAllowanceList.size(); nCount++)

                // 申請國保給付記錄資料 (有資料再印)
                List<DisableReviewRpt01NpPayDataCase> npPayList = caseData.getNpPayList();
                for (int nNpPayCount = 0; nNpPayCount < npPayList.size(); nNpPayCount++) { // ... [
                    DisableReviewRpt01NpPayDataCase npPayData = npPayList.get(nNpPayCount);

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
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // 國保給付記錄 表頭
                        addColumn(table, 58, 1, "申請國保給付記錄：", fontCh12b, 0, LEFT);

                    }
                    else {
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                            addEmptyRow(table, 1);
                        }
                    }

                    // 20101124 kiyomi - mark start
                    // 國保給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                    // addEmptyRow(table, 4);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 4);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 4);
                    // }
                    // 20101124 kiyomi - mark end

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "申請日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "首次給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "事故日期", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, npPayData.getEvteeName(), fontCh12, 0, LEFT); // 事故者姓名

                    addColumn(table, 11, 1, npPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
                    addColumn(table, 11, 1, npPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 11, 1, npPayData.getPayYmString(), fontCh12, 0, LEFT); // 首次給付年月
                    addColumn(table, 11, 1, npPayData.getEvtDtString(), fontCh12, 0, LEFT); // 事故日期
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (npPayData.getIssueAmt() != null && npPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 6, 1, formatBigDecimalToInteger(npPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, npPayData.getChkDtString(), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 11, 1, npPayData.getAplPayDateString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 11, 1, npPayData.getAplPayDateString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 11, 1, StringUtils.defaultString(npPayData.getCloseDtString() + ((StringUtils.isNotBlank(npPayData.getCloseReason())) ? " / " + npPayData.getCloseReason() : "")), fontCh12, 0, LEFT); // 不給付日期

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    // addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // addColumn(table, 7, 1, "合格註記", fontCh12, 0, LEFT);
                    // addColumn(table, 10, 1, "人工審核結果", fontCh12, 0, LEFT);
                    // addColumn(table, 41, 1, "　", fontCh12, 0, LEFT);
                    // // ---
                    // addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // addColumn(table, 7, 1, npPayData.getAcceptMark(), fontCh12, 0, LEFT); // 合格註記
                    // addColumn(table, 10, 1, npPayData.getManChkFlg(), fontCh12, 0, LEFT); // 人工審核結果
                    // addColumn(table, 41, 1, "　", fontCh12, 0, LEFT);

                    // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                    if ((nNpPayCount == npPayList.size() - 1) && (caseData.getNpPayList().size() > 0)) {
                        // 空白行

                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了

                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                    }

                } // ] ... end for (int nNpPayCount = 0; nNpPayCount < npPayList.size(); nNpPayCount++)

                // 20101124 kiyomi - mark start
                // addLine(table);
                // 20101124 kiyomi - mark end
                // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);
                //
                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // // 換了頁就不印分隔線了
                // deleteRow(table, 1);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }
                // else {
                // deleteRow(table, 1);
                // addLine(table);
                // }
                }
                // ]

                // 20101124 kiyomi - start
                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了

                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                    addLine(table);
                }
                // 20101124 kiyomi - end

                // -----------------------------------------------------------------------------------------------

                // 本次紓困貸款

                // 先塞三行空白 兩行標題一行資料 測試是否換頁
                // addEmptyRow(table, 3);
                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // deleteRow(table, 3);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }
                // else {
                // deleteRow(table, 3);
                // addLine(table);
                // }

                List<DisableReviewRpt01LoanAmtCase> loanAmtList = caseData.getLoanAmtData();
                if (caseData.getLoanAmtData() != null && caseData.getLoanAmtData().size() > 0 && loanAmtList.get(0).getLoanAmt().compareTo(new BigDecimal(0)) != 0) {
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 58, 1, "本次紓困貸款資料：", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "勞貸貸款金額", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "本金餘額", fontCh12, 0, LEFT);
                    addColumn(table, 4, 1, "利息", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "本息截止日", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "呆帳金額", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "其他費用", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "抵銷金額", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    for (DisableReviewRpt01LoanAmtCase loanAmtData : loanAmtList) {
                        // 先測試是否換頁

                        addEmptyRow(table, 1);
                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }
                        if (loanAmtData.getLoanAmt().compareTo(new BigDecimal(0)) != 0) {
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 7, 1, formatBigDecimalToInteger(loanAmtData.getLoanAmt()), fontCh12, 0, RIGHT);
                            addColumn(table, 2, 1, " ", fontCh12, 0, RIGHT);
                            addColumn(table, 5, 1, formatBigDecimalToInteger(loanAmtData.getRecapAmt()), fontCh12, 0, RIGHT);
                            addColumn(table, 3, 1, " ", fontCh12, 0, RIGHT);
                            addColumn(table, 4, 1, formatBigDecimalToInteger(loanAmtData.getLoaniTrt()), fontCh12, 0, RIGHT);
                            addColumn(table, 3, 1, " ", fontCh12, 0, RIGHT);
                            addColumn(table, 8, 1, loanAmtData.getDlineDateString(), fontCh12, 0, LEFT);
                            addColumn(table, 5, 1, formatBigDecimalToInteger(loanAmtData.getBadDebtAmt()), fontCh12, 0, RIGHT);
                            addColumn(table, 3, 1, " ", fontCh12, 0, RIGHT);
                            addColumn(table, 5, 1, formatBigDecimalToInteger(loanAmtData.getOffsetExp()), fontCh12, 0, RIGHT);
                            addColumn(table, 3, 1, " ", fontCh12, 0, RIGHT);
                            addColumn(table, 5, 1, formatBigDecimalToInteger(loanAmtData.getOffsetAmt()), fontCh12, 0, RIGHT);
                            addColumn(table, 3, 1, " ", fontCh12, 0, RIGHT);
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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

                // ---------------------------------------------------------------------------------------------------
                // 另案扣減資料
                // [
                // 20101124 kiyomi - mark start
                // 先塞三行空白 兩行標題一行資料 測試是否換頁
                // addEmptyRow(table, 3);
                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // deleteRow(table, 3);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }
                // else {
                // deleteRow(table, 3);
                // }
                // 20101124 kiyomi - mark end

                // 另案扣減

                List<DisableReviewRpt01DeductDataCase> deductList = caseData.getDeductList();
                if (caseData.getDeductList() != null && deductList.size() > 0) {
                    // 印標題

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 58, 1, "另案扣減資料：", fontCh12, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 58, 1, "【一次給付】：", fontCh12b, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, "序", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "立帳對象姓名", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "應收未收餘額", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "處理狀況", fontCh12, 0, RIGHT);
                    addColumn(table, 3, 1, "", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "年金受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "", fontCh12, 0, RIGHT);
                    addColumn(table, 4, 1, "", fontCh12, 0, LEFT);

                    // 印資料

                    for (int i = 0; i < deductList.size(); i++) {
                        DisableReviewRpt01DeductDataCase deductData = deductList.get(i);
                        // 先測試是否換頁

                        addEmptyRow(table, 1);
                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 3, 1, String.valueOf(i + 1), fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, deductData.getRxfName(), fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, deductData.getRxfApNo(), fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, formatBigDecimalToInteger(deductData.getSubAmt()), fontCh12, 0, RIGHT);
                        addColumn(table, 8, 1, deductData.getPrSt(), fontCh12, 0, RIGHT);
                        addColumn(table, 3, 1, "", fontCh12, 0, LEFT);
                        addColumn(table, 14, 1, deductData.getApNoString(), fontCh12, 0, LEFT);
                        addColumn(table, 4, 1, "", fontCh12, 0, LEFT);

                    }
                    addEmptyRow(table, 1);
                }
                List<DisableReviewRpt01PayDeductDataCase> deductPayList = caseData.getDeductPayList();
                if (caseData.getDeductPayList() != null && deductPayList.size() > 0) {
                    // 印標題

                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 58, 1, "【年金給付】：", fontCh12b, 0, LEFT);
                    // ---
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, "序", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "立帳對象姓名", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "應收總額", fontCh12, 0, RIGHT);
                    addColumn(table, 3, 1, "", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "未收餘額", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "", fontCh12, 0, RIGHT);
                    addColumn(table, 4, 1, "", fontCh12, 0, LEFT);

                    // 印資料

                    for (int i = 0; i < deductPayList.size(); i++) {
                        DisableReviewRpt01PayDeductDataCase deductPayData = deductPayList.get(i);
                        // 先測試是否換頁

                        addEmptyRow(table, 1);
                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 3, 1, String.valueOf(i + 1), fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, deductPayData.getBenName(), fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, deductPayData.getApNo(), fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, deductPayData.getPayYm(), fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, formatBigDecimalToInteger(deductPayData.getRecAmt()), fontCh12, 0, RIGHT);
                        addColumn(table, 3, 1, "", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, formatBigDecimalToInteger(deductPayData.getRecRem()), fontCh12, 0, LEFT);
                        addColumn(table, 6, 1, "", fontCh12, 0, RIGHT);
                        addColumn(table, 4, 1, "", fontCh12, 0, LEFT);
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
                }

                // ---------------------------------------------------------------------------------------------------
                // 受款人資料

                // [

                // ---
                // 事故者於受款人資料的資料
                // [
                // 為求每位受款人的資料能在同一頁, 故先計算每位受款人之資料行數
                // 並預先塞入空白行, 測試是否需換頁
                // 行數 = 固定行數 7 行 + 法定代理人 1 行 (若有值) + 給付資料 n 行 + 給付資料標題 1 行 (若給付資料 > 0 筆)
                List<DisableReviewRpt01BenPayDataCase> evtBenPayList = caseData.getBenByPayList();

                int nEvtBenPayLine = 8 + evtBenPayList.size();

                // 20101124 kiyomi - mark start
                // 給付資料標題 1 行 (若給付資料 > 0 筆)
                // if (caseData.getEvtBenPayList()!=null)
                // nEvtBenPayLine++;

                // 法定代理人 1 行 (若有值)
                // if (StringUtils.isNotBlank(caseData.getGrdName()) || StringUtils.isNotBlank(caseData.getGrdIdnNo()) || StringUtils.isNotBlank(caseData.getGrdBrDate()))
                // nEvtBenPayLine++;

                // 法定代理人多一行變 2 行

                // if (StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "A") || StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "F") || StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "Z")
                // || StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "N"))
                // nEvtBenPayLine++;

                // 塞事故者於受款人資料的資料前, 先隨便塞空白行測試是否需換頁
                // addEmptyRow(table, nEvtBenPayLine);

                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // 換了頁就不再塞空白行了

                // deleteRow(table, nEvtBenPayLine);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }
                // else {
                // deleteRow(table, nEvtBenPayLine);
                // }
                // 20101124 kiyomi - mark end
                // 20101124 kiyomi - start
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了

                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                // 20101124 kiyomi - end
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 5, 1, "受款人", fontCh12l, 0, JUSTIFIEDALL); // 有底線

                addColumn(table, 18, 1, "資料：", fontCh12, 0, LEFT);
                if (caseData.getBenList() != null) {
                    addColumn(table, 33, 1, "受款人數： " + (caseData.getBenList().size() + 1), fontCh12, 0, LEFT); // 因有含事故者本人, 故須加 1
                }
                else {
                    addColumn(table, 33, 1, "受款人數： " + (0 + 1), fontCh12, 0, LEFT); // 因有含事故者本人, 故須加 1
                }
                addColumn(table, 2, 1, " ", fontCh8, 0, LEFT);
                // ---
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh8, 0, LEFT);
                addColumn(table, 40, 1, "受款人序：01", fontCh12, 0, LEFT); // 事故者本人固定在第一筆

                addColumn(table, 18, 1, " ", fontCh12, 0, LEFT); // 發給：

                // ---
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 56, 1, "姓名：" + StringUtils.defaultString(caseData.getBenName()), fontCh12, 0, LEFT);
                addColumn(table, 2, 1, " ", fontCh8, 0, LEFT);
                // ---
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 15, 1, "身分證號：" + StringUtils.defaultString(caseData.getBenIdnNo()), fontCh12, 0, LEFT);
                addColumn(table, 15, 1, "出生日期：" + StringUtils.defaultString(caseData.getBenBrDateString()), fontCh12, 0, LEFT);
                addColumn(table, 26, 1, "電話：" + StringUtils.defaultString(caseData.getTel1())
                                + ((StringUtils.isNotBlank(caseData.getTel2()) && StringUtils.isNotBlank(caseData.getTel1())) ? ("、" + StringUtils.defaultString(caseData.getTel2())) : StringUtils.defaultString(caseData.getTel2())), fontCh12, 0, LEFT);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                // ---
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                // 帳號處理
                String strEvtAccountNo = StringUtils.defaultString(caseData.getPayBankId());
                if (StringUtils.isNotBlank(caseData.getBranchId()))
                    if(caseData.getPayTyp().equals("1") && !caseData.getPayBankId().equals("700")){
                    	strEvtAccountNo = strEvtAccountNo + "-" + "0000";
                	}else{
                		strEvtAccountNo = strEvtAccountNo + "-" + caseData.getBranchId();
                	}
                if (StringUtils.isNotBlank(caseData.getPayEeacc()))
                    strEvtAccountNo = strEvtAccountNo + "-" + caseData.getPayEeacc();
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, "關係：" + StringUtils.defaultString(caseData.getBenEvtRel()), fontCh12, 0, LEFT);
                addColumn(table, 8, 1, "給付方式：" + StringUtils.defaultString(caseData.getPayTyp()), fontCh12, 0, LEFT);
                addColumn(table, 25, 1, "帳號：" + strEvtAccountNo + " " + StringUtils.defaultString(caseData.getBankName()), fontCh12, 0, LEFT);
                addColumn(table, 13, 1, "戶名：" + StringUtils.defaultString(caseData.getAccName()), fontCh12, 0, LEFT);
                if(StringUtils.isBlank(caseData.getSpecialAcc())){
                	addColumn(table, 6, 1, "" , fontCh12, 0, LEFT);
                }else{
                	addColumn(table, 6, 1, "(專戶)", fontCh12, 0, LEFT);
                }
                // ---
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, "國籍別：" + caseData.getEvtNationTpe(), fontCh12, 0, LEFT);
                addColumn(table, 8, 1, "性別：" + caseData.getEvtSex(), fontCh12, 0, LEFT);
                addColumn(table, 12, 1, "國籍：" + caseData.getEvtNationCode(), fontCh12, 0, LEFT);
                addColumn(table, 13, 1, "婚姻狀況：" + caseData.getBenMarrMk(), fontCh12, 0, LEFT);
                addColumn(table, 17, 1, "身分查核年月：" + caseData.getIdnChkYmString(), fontCh12, 0, LEFT);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                // ---
                if (caseData.getBenByPayList() != null) {
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "可領金額", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "扣減金額", fontCh12, 0, RIGHT);
                    addColumn(table, 8, 1, "紓困呆帳金額", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "匯款匯費", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "實付金額", fontCh12, 0, RIGHT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "編審註記", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                }
                // ---
                if (caseData.getBenByPayList() != null) {
                    for (DisableReviewRpt01BenPayDataCase evtBenPayData : evtBenPayList) {
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, evtBenPayData.getPayYmString(), fontCh12, 0, LEFT); // 給付年月

                        if (evtBenPayData.getIssueAmt() != null && evtBenPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(evtBenPayData.getIssueAmt()), fontCh12, 0, RIGHT); // 可領金額
                        else
                            addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 可領金額

                        if (evtBenPayData.getOtherAmt() != null && evtBenPayData.getOtherAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(evtBenPayData.getOtherAmt()), fontCh12, 0, RIGHT); // 扣減金額
                        else
                            addColumn(table, 7, 1, " ", fontCh12, 0, RIGHT); // 扣減金額

                        if (evtBenPayData.getBadDebtAmt() != null && evtBenPayData.getBadDebtAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(evtBenPayData.getBadDebtAmt()), fontCh12, 0, RIGHT); // 呆帳金額
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, RIGHT); // 呆帳金額

                        if (evtBenPayData.getPayRate() != null && evtBenPayData.getPayRate().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(evtBenPayData.getPayRate()), fontCh12, 0, RIGHT); // 匯款匯費
                        else
                            addColumn(table, 7, 1, " ", fontCh12, 0, RIGHT); // 匯款匯費

                        if (evtBenPayData.getAplpayAmt() != null && evtBenPayData.getAplpayAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(evtBenPayData.getAplpayAmt()), fontCh12, 0, RIGHT); // 實付金額
                        else
                            addColumn(table, 7, 1, "0", fontCh12, 0, RIGHT); // 實付金額

                        addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, caseData.getChkfileDataBy(evtBenPayData.getPayYm()), fontCh12, 0, LEFT); // 編審註記
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }
                }
                // ---
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 56, 1, "地址：" + StringUtils.defaultString(caseData.getCommZip()) + " " + StringUtils.defaultString(caseData.getCommAddr()), fontCh12, 0, LEFT);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                // ---
                if (StringUtils.isNotBlank(caseData.getGrdName()) || StringUtils.isNotBlank(caseData.getGrdIdnNo()) || StringUtils.isNotBlank(caseData.getGrdBrDate())) {
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, "法定代理人姓名：" + StringUtils.defaultString(caseData.getGrdName()), fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, "身分證號：" + StringUtils.defaultString(caseData.getGrdIdnNo()), fontCh12, 0, LEFT);
                    addColumn(table, 24, 1, "出生日期：" + StringUtils.defaultString(caseData.getGrdBrDateString()), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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

                if (caseData.getBenByPayList() != null && evtBenPayList.size() > 0) {
                    DisableReviewRpt01BenPayDataCase evtBenPayData = evtBenPayList.get(evtBenPayList.size() - 1);

                    unitpayAmt = evtBenPayData.getUnitpayAmt();
                    returnAmt = evtBenPayData.getReturnAmt();
                    remainAmt = evtBenPayData.getRemainAmt();
                    loanAmt = evtBenPayData.getLoanAmt();
                    lecomAmt = evtBenPayData.getLecomAmt();
                    recomAmt = evtBenPayData.getRecomAmt();
                    badDebtAmt = evtBenPayData.getBadDebtAmt();
                }

                if (StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "A")) {
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 18, 1, "墊付金額：" + (StringUtils.equals(formatBigDecimalToInteger(unitpayAmt), "0") ? "" : formatBigDecimalToInteger(unitpayAmt)), fontCh12, 0, LEFT);
                    addColumn(table, 19, 1, "已歸墊金額：" + (StringUtils.equals(formatBigDecimalToInteger(returnAmt), "0") ? "" : formatBigDecimalToInteger(returnAmt)), fontCh12, 0, LEFT);
                    addColumn(table, 19, 1, "未歸墊金額：" + (StringUtils.equals(formatBigDecimalToInteger(remainAmt), "0") ? "" : formatBigDecimalToInteger(remainAmt)), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                }
                else if (StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "F")) {
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 56, 1, "紓困貸款金額：" + (StringUtils.equals(formatBigDecimalToInteger(loanAmt), "0") ? "" : formatBigDecimalToInteger(loanAmt)), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                }
                else if (StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "Z")) {
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 18, 1, "實際補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(caseData.getCutAmt()), "0") ? "" : formatBigDecimalToInteger(compenAmt)), fontCh12, 0, LEFT);
                    addColumn(table, 19, 1, "已代扣補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(lecomAmt), "0") ? "" : formatBigDecimalToInteger(lecomAmt)), fontCh12, 0, LEFT);
                    addColumn(table, 19, 1, "未扣補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(recomAmt), "0") ? "" : formatBigDecimalToInteger(recomAmt)), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                }
                else if (StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "N")) {
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 56, 1, "紓困呆帳金額：" + formatBigDecimalToInteger(badDebtAmt), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                }
                // ]
                // 事故者於受款人資料的資料

                List<DisableReviewRpt01BenDataCase> benList = caseData.getBenList();
                if (caseData.getBenList() != null && benList.size() > 0) {
                    for (int nBenList = 0; nBenList < benList.size(); nBenList++) {
                        DisableReviewRpt01BenDataCase benData = benList.get(nBenList);

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
                        // 行數 = 固定行數 5 行 + 法定代理人 1 行 (若有值) + 給付資料 n 行 + 給付資料標題 1 行 (若給付資料 > 0 筆)
                        List<DisableReviewRpt01BenPayDataCase> benPayList = benData.getBenPayList();

                        int nBenPayLine = 5 + benPayList.size();

                        // 20101124 kiyomi - mark start
                        // 給付資料標題 1 行 (若給付資料 > 0 筆)
                        // if (benData.getBenPayList()!=null)
                        // nBenPayLine++;

                        // 法定代理人 1 行 (若有值)
                        // if (StringUtils.isNotBlank(benData.getGrdName()) || StringUtils.isNotBlank(benData.getGrdIdnNo()) || StringUtils.isNotBlank(benData.getGrdBrDate()))
                        // nBenPayLine++;

                        // 法定代理人多一行變 2 行

                        // if (StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "A") || StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "F") || StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "Z")
                        // || StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "N"))
                        // nBenPayLine++;

                        // 塞受款人資料的資料前, 先隨便塞空白行測試是否需換頁
                        // addEmptyRow(table, nBenPayLine);

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不再塞空白行了

                        // deleteRow(table, nBenPayLine);
                        // document.add(table);
                        // table = addHeader(caseData, false, earlyWarning);
                        // }
                        // else {
                        // deleteRow(table, nBenPayLine);
                        // }
                        // 20101124 kiyomi - mark end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 40, 1, "受款人序：" + StringUtils.leftPad(String.valueOf(nBenList + 2), 2, "0"), fontCh12, 0, LEFT); // 第一筆是事故者, 故要加 1
                        addColumn(table, 18, 1, " ", fontCh12, 0, LEFT); // 發給：

                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 30, 1, "姓名：" + StringUtils.defaultString(benData.getBenName()), fontCh12, 0, LEFT);
                        addColumn(table, 26, 1, "繼承人申請日期：" + StringUtils.defaultString(benData.getAppDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 16, 1, "身分證號：" + StringUtils.defaultString(benData.getBenIdnNo()), fontCh12, 0, LEFT);
                        addColumn(table, 16, 1, "出生日期：" + StringUtils.defaultString(benData.getBenBrDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 24, 1, "電話：" + StringUtils.defaultString(benData.getTel1())
                                        + ((StringUtils.isNotBlank(benData.getTel2()) && StringUtils.isNotBlank(benData.getTel1())) ? ("、" + StringUtils.defaultString(benData.getTel2())) : StringUtils.defaultString(benData.getTel2())), fontCh12, 0,
                                        LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        // 帳號處理
                        String strBenAccountNo = StringUtils.defaultString(benData.getPayBankId());
                        if (StringUtils.isNotBlank(benData.getBranchId()))
                            if(benData.getPayTyp().equals("1") && !benData.getPayBankId().equals("700")){
                            	strBenAccountNo = strBenAccountNo + "-" + "0000";
                	        }else{
                	        	strBenAccountNo = strBenAccountNo + "-" + benData.getBranchId();
                	        }
                        if (StringUtils.isNotBlank(benData.getPayEeacc()))
                            strBenAccountNo = strBenAccountNo + "-" + benData.getPayEeacc();

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 6, 1, "關係：" + StringUtils.defaultString(benData.getBenEvtRel()), fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "給付方式：" + StringUtils.defaultString(benData.getPayTyp()), fontCh12, 0, LEFT);
                        addColumn(table, 30, 1, "帳號：" + strBenAccountNo + " " + StringUtils.defaultString(benData.getBankName()), fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "戶名：" + StringUtils.defaultString(benData.getAccName()), fontCh12, 0, LEFT);
                        if(StringUtils.isBlank(benData.getSpecialAcc())){
                        	addColumn(table, 7, 1, "" , fontCh12, 0, LEFT);
                        }else{
                        	addColumn(table, 7, 1, "(專戶)", fontCh12, 0, LEFT);
                        }
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 6, 1, "國籍別：" + benData.getEvtNationTpe(), fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "性別：" + benData.getEvtSex(), fontCh12, 0, LEFT);
                        addColumn(table, 12, 1, "國籍：" + benData.getEvtNationCode(), fontCh12, 0, LEFT);
                        addColumn(table, 13, 1, "婚姻狀況：" + benData.getBenMarrMk(), fontCh12, 0, LEFT);
                        addColumn(table, 17, 1, "身分查核年月：" + benData.getIdnChkYmString(), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        if (benData.getBenPayList() != null && benPayList.size() > 0) {
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 7, 1, "給付年月", fontCh12, 0, LEFT);
                            addColumn(table, 8, 1, "可領金額", fontCh12, 0, RIGHT);
                            addColumn(table, 7, 1, "扣減金額", fontCh12, 0, RIGHT);
                            addColumn(table, 8, 1, "紓困呆帳金額", fontCh12, 0, RIGHT);
                            addColumn(table, 7, 1, "匯款匯費", fontCh12, 0, RIGHT);
                            addColumn(table, 7, 1, "實付金額", fontCh12, 0, RIGHT);
                            addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 11, 1, "編審註記", fontCh12, 0, LEFT);
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        }
                        // ---
                        if (benData.getBenPayList() != null && benPayList.size() > 0) {
                            for (DisableReviewRpt01BenPayDataCase benPayData : benPayList) {
                                // 20101124 kiyomi - start
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
                                // 20101124 kiyomi - end
                                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                                addColumn(table, 7, 1, benPayData.getPayYmString(), fontCh12, 0, LEFT); // 給付年月

                                if (benPayData.getIssueAmt() != null && benPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                                    addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getIssueAmt()), fontCh12, 0, RIGHT); // 可領金額
                                else
                                    addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 可領金額

                                if (benPayData.getOtherAmt() != null && benPayData.getOtherAmt().compareTo(new BigDecimal(0)) != 0)
                                    addColumn(table, 7, 1, formatBigDecimalToInteger(benPayData.getOtherAmt()), fontCh12, 0, RIGHT); // 扣減金額
                                else
                                    addColumn(table, 7, 1, " ", fontCh12, 0, RIGHT); // 扣減金額

                                if (benPayData.getBadDebtAmt() != null && benPayData.getBadDebtAmt().compareTo(new BigDecimal(0)) != 0)
                                    addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getBadDebtAmt()), fontCh12, 0, RIGHT); // 呆帳金額
                                else
                                    addColumn(table, 8, 1, " ", fontCh12, 0, RIGHT); // 呆帳金額

                                if (benPayData.getPayRate() != null && benPayData.getPayRate().compareTo(new BigDecimal(0)) != 0)
                                    addColumn(table, 7, 1, formatBigDecimalToInteger(benPayData.getPayRate()), fontCh12, 0, RIGHT); // 匯款匯費
                                else
                                    addColumn(table, 7, 1, " ", fontCh12, 0, RIGHT); // 匯款匯費

                                if (benPayData.getAplpayAmt() != null && benPayData.getAplpayAmt().compareTo(new BigDecimal(0)) != 0)
                                    addColumn(table, 7, 1, formatBigDecimalToInteger(benPayData.getAplpayAmt()), fontCh12, 0, RIGHT); // 實付金額
                                else
                                    addColumn(table, 7, 1, "0", fontCh12, 0, RIGHT); // 實付金額

                                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                                addColumn(table, 11, 1, benData.getChkfileDataBy(benPayData.getPayYm()), fontCh12, 0, LEFT); // 編審註記
                                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            }
                        }
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 56, 1, "地址：" + StringUtils.defaultString(benData.getCommZip()) + " " + StringUtils.defaultString(benData.getCommAddr()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        if (StringUtils.isNotBlank(benData.getGrdName()) || StringUtils.isNotBlank(benData.getGrdIdnNo()) || StringUtils.isNotBlank(benData.getGrdBrDate())) {
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 16, 1, "法定代理人姓名：" + StringUtils.defaultString(benData.getGrdName()), fontCh12, 0, LEFT);
                            addColumn(table, 16, 1, "身分證號：" + StringUtils.defaultString(benData.getGrdIdnNo()), fontCh12, 0, LEFT);
                            addColumn(table, 24, 1, "出生日期：" + StringUtils.defaultString(benData.getGrdBrDateString()), fontCh12, 0, LEFT);
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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

                        if (benData.getBenPayList() != null && benPayList.size() > 0) {
                            DisableReviewRpt01BenPayDataCase benPayData = benPayList.get(benPayList.size() - 1);
                            benUnitpayAmt = benPayData.getUnitpayAmt();
                            benReturnAmt = benPayData.getReturnAmt();
                            benRemainAmt = benPayData.getRemainAmt();
                            benLoanAmt = benPayData.getLoanAmt();
                            benLecomAmt = benPayData.getLecomAmt();
                            benRecomAmt = benPayData.getRecomAmt();
                            benBadDebtAmt = benPayData.getBadDebtAmt();
                        }

                        if (StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "A")) {
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 18, 1, "墊付金額：" + (StringUtils.equals(formatBigDecimalToInteger(benUnitpayAmt), "0") ? "" : formatBigDecimalToInteger(benUnitpayAmt)), fontCh12, 0, LEFT);
                            addColumn(table, 19, 1, "已歸墊金額：" + (StringUtils.equals(formatBigDecimalToInteger(benReturnAmt), "0") ? "" : formatBigDecimalToInteger(benReturnAmt)), fontCh12, 0, LEFT);
                            addColumn(table, 19, 1, "未歸墊金額：" + (StringUtils.equals(formatBigDecimalToInteger(benRemainAmt), "0") ? "" : formatBigDecimalToInteger(benRemainAmt)), fontCh12, 0, LEFT);
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        }
                        else if (StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "F")) {
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 56, 1, "紓困貸款金額：" + (StringUtils.equals(formatBigDecimalToInteger(benLoanAmt), "0") ? "" : formatBigDecimalToInteger(benLoanAmt)), fontCh12, 0, LEFT);
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        }
                        else if (StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "Z")) {
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 18, 1, "實際補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(benData.getCutAmt()), "0") ? "" : formatBigDecimalToInteger(benData.getCutAmt())), fontCh12, 0, LEFT);
                            addColumn(table, 19, 1, "已代扣補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(benLecomAmt), "0") ? "" : formatBigDecimalToInteger(benLecomAmt)), fontCh12, 0, LEFT);
                            addColumn(table, 19, 1, "未扣補償金額：" + (StringUtils.equals(formatBigDecimalToInteger(benRecomAmt), "0") ? "" : formatBigDecimalToInteger(benRecomAmt)), fontCh12, 0, LEFT);
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        }
                        else if (StringUtils.equalsIgnoreCase(benData.getBenEvtRel(), "N")) {
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 56, 1, "紓困呆帳金額：" + (StringUtils.equals(formatBigDecimalToInteger(benBadDebtAmt), "0") ? "" : formatBigDecimalToInteger(benBadDebtAmt)), fontCh12, 0, LEFT);
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        }
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

                // 20101124 kiyomi - mark start
                // if (caseData.getChangeList()!=null)
                // nChangeDataLine++;

                // 塞承保異動資料資料前, 先隨便塞空白行測試是否需換頁
                // addEmptyRow(table, nChangeDataLine);

                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // deleteRow(table, nChangeDataLine);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }
                // else {
                // deleteRow(table, nChangeDataLine);
                // }
                // 20101124 kiyomi - mark end
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 58, 1, "承保異動資料：", fontCh12, 0, LEFT);
                // ---
                if (caseData.getChangeList() != null) {
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "保險證號", fontCh12, 0, LEFT);
                    addColumn(table, 5, 1, "異動別", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "投保薪資", fontCh12, 0, RIGHT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, "逕調", fontCh12, 0, LEFT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "部門", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "生效日期", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "退保日期", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                }
                // ---
                if (caseData.getChangeList() != null) {
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
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, changeData.getSidMk(), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, changeData.getTxcdType2(), fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, changeData.getUno(), fontCh12, 0, LEFT);
                        addColumn(table, 5, 1, changeData.getTxcd(), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, changeData.getTxcdTypeA(), fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, formatBigDecimalToInteger(changeData.getWage()), fontCh12, 0, RIGHT);
                        addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 3, 1, changeData.getTxcdTypeB(), fontCh12, 0, LEFT);
                        addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, changeData.getDept(), fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, changeData.getEfDteString(), fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, changeData.getExitDteString(), fontCh12, 0, LEFT); // 退保日期

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }
                }
                // ]
                // 承保異動資料

                // 最後單位

                DisableReviewRpt01UnitCase applyUnitData = caseData.getApplyUnitData();
                if (caseData.getApplyUnitData() != null) {
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
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "申請單位", fontCh12, 0, LEFT); // 申請單位
                    addColumn(table, 9, 1, formatBigDecimalToInteger(applyUnitData.getPrsnoB()), fontCh12, 0, LEFT); // 單位人數
                    addColumn(table, 9, 1, applyUnitData.getUbNo(), fontCh12, 0, LEFT); // 保險證號
                    addColumn(table, 19, 1, applyUnitData.getUname(), fontCh12, 0, LEFT); // 投保單位名稱
                    addColumn(table, 10, 1, applyUnitData.getEname(), fontCh12, 0, LEFT); // 負責人

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                }
                // addLine(table);
                addEmptyRow(table, 1);
                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                    addLine(table);
                }
                // ]

                // 最高60個月平均薪資明細
                // [

                List<DisableReviewRpt01CipgDataCase> cipgDataList = caseData.getCipgData();

                // 20101124 kiyomi - mark start
                // if(caseData.getCipgData()!=null && cipgDataList.size()>0){
                // addEmptyRow(table, 15);
                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // deleteRow(table, 15);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }
                // else {
                // deleteRow(table, 15);
                // }
                // }
                // 20101124 kiyomi - mark end
                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 38, 1, "※最高 "+caseData.getAppMonth()+"個月平均薪資明細：", fontCh12, 0, LEFT); // 申請單位
                addColumn(table, 20, 1, "實際均薪月數："+caseData.getRealAvgMon()+"個月", fontCh12, 0, LEFT);

                // 20101124 kiyomi - start
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
                // 20101124 kiyomi - end
                addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                for (int avgWgRow = 0; avgWgRow < 5; avgWgRow++) {

                    addColumn(table, 6, 1, "年 月 ", fontCh12, 0, CENTER); // 年月
                    addColumn(table, 5, 1, "薪 資 ", fontCh12, 0, CENTER); // 薪資

                }
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                if (caseData.getCipgData() != null) {
                    // 塞受款人資料的資料前, 先隨便塞空白行測試是否需換頁

                    // addEmptyRow(table, 13);
                    //
                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // // 換了頁就不再塞空白行了

                    // deleteRow(table, 13);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }else{
                    // deleteRow(table, 13);
                    //
                    // }
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    for (int avgWgRow = 0; avgWgRow < cipgDataList.size(); avgWgRow++) {

                        DisableReviewRpt01CipgDataCase cipgDataCase = cipgDataList.get(avgWgRow);
                        if (avgWgRow == 0) {
                            addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                            if (cipgDataCase.getDwMk().equals("2")) {
                                addColumn(table, 6, 1, "*" + cipgDataCase.getAvgYmString(), fontCh12, 0, CENTER); // 年 月
                            }
                            else {
                                addColumn(table, 6, 1, " " + cipgDataCase.getAvgYmString(), fontCh12, 0, CENTER); // 年 月
                            }

                            addColumn(table, 5, 1, (!(formatBigDecimalToInteger(cipgDataCase.getAvgWg()).equals("0") && cipgDataCase.getAvgWg() != null) ? formatBigDecimalToInteger(cipgDataCase.getAvgWg()) : "0"), fontCh12, 0, RIGHT); // 薪 資

                        }
                        else if (avgWgRow > 4 && avgWgRow % 5 == 0) {
                            addColumn(table, 3, 1, " ", fontCh12, 0, LEFT);
                            if (cipgDataCase.getDwMk().equals("2")) {
                                addColumn(table, 6, 1, "*" + cipgDataCase.getAvgYmString(), fontCh12, 0, CENTER); // 年 月
                            }
                            else {
                                addColumn(table, 6, 1, " " + cipgDataCase.getAvgYmString(), fontCh12, 0, CENTER); // 年 月
                            }

                            addColumn(table, 5, 1, (!(formatBigDecimalToInteger(cipgDataCase.getAvgWg()).equals("0") && cipgDataCase.getAvgWg() != null) ? formatBigDecimalToInteger(cipgDataCase.getAvgWg()) : "0"), fontCh12, 0, RIGHT); // 薪 資

                        }
                        else if (avgWgRow % 5 == 4) {
                            if (cipgDataCase.getDwMk().equals("2")) {
                                addColumn(table, 6, 1, "*" + cipgDataCase.getAvgYmString(), fontCh12, 0, CENTER); // 年 月
                            }
                            else {
                                addColumn(table, 6, 1, " " + cipgDataCase.getAvgYmString(), fontCh12, 0, CENTER); // 年 月
                            }

                            addColumn(table, 5, 1, (!(formatBigDecimalToInteger(cipgDataCase.getAvgWg()).equals("0") && cipgDataCase.getAvgWg() != null) ? formatBigDecimalToInteger(cipgDataCase.getAvgWg()) : "0"), fontCh12, 0, RIGHT); // 薪 資

                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        }
                        else {
                            if (cipgDataCase.getDwMk().equals("2")) {
                                addColumn(table, 6, 1, "*" + cipgDataCase.getAvgYmString(), fontCh12, 0, CENTER); // 年 月
                            }
                            else {
                                addColumn(table, 6, 1, " " + cipgDataCase.getAvgYmString(), fontCh12, 0, CENTER); // 年 月
                            }

                            addColumn(table, 5, 1, (!(formatBigDecimalToInteger(cipgDataCase.getAvgWg()).equals("0") && cipgDataCase.getAvgWg() != null) ? formatBigDecimalToInteger(cipgDataCase.getAvgWg()) : "0"), fontCh12, 0, RIGHT); // 薪 資

                        }

                    }
                    //cipgDataList.size() < Integer.parseInt(caseData.getAppMonth())+1 && 
                    if (cipgDataList.size() % 5 != 0) {
                        addColumn(table, (5 - (cipgDataList.size() % 5)) * 11, 1, " ", fontCh12, 0, LEFT); // 補空白

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                }
                addLine(table);

                // ]

                // 事故者於眷屬資料的資料

                List<DisableReviewRpt01FamilyDataCase> famList = caseData.getFamDataCaseList();

                if (caseData.getFamDataCaseList() != null && famList.size() > 0) {
                    // 20101124 kiyomi - mark end
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // 20101124 kiyomi - mark end
                    // 20101124 kiyomi - start
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
                    // 20101124 kiyomi - end
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "眷屬資料：", fontCh12b, 0, LEFT);

                    if (caseData.getFamDataCaseList() != null && famList.size() > 0) {
                        addColumn(table, 20, 1, "眷屬申請人數：" + (caseData.getFamDataCaseList().size()), fontCh12, 0, LEFT); // 因有含事故者本人, 故須加 1
                    }
                    else {
                        addColumn(table, 20, 1, " ", fontCh12, 0, LEFT); // 因有含事故者本人, 故須加 1
                    }

                    addColumn(table, 28, 1, " ", fontCh12b, 0, LEFT);

                    for (int nFamList = 0; nFamList < famList.size(); nFamList++) {
                        DisableReviewRpt01FamilyDataCase famData = famList.get(nFamList);

                        // 空白行 (每個受款人間留一行空白)
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了

                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }

                        // 為求每位眷屬的資料能在同一頁, 故先計算每位眷屬之資料行數

                        // 並預先塞入空白行, 測試是否需換頁

                        List<DisableReviewRpt01FamChkfileDataCase> famChkFile = famData.getFamChkfileDataList();

                        int nFamPayLine = 10;

                        // 20101124 kiyomi - mark start
                        // 給付資料標題 1 行 (若給付資料 > 0 筆)
                        // if (famData.getFamChkfileDataList()!=null)
                        // nFamPayLine=nFamPayLine+2;

                        // 塞受款人資料的資料前, 先隨便塞空白行測試是否需換頁
                        // addEmptyRow(table, nFamPayLine);

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不再塞空白行了

                        // deleteRow(table, nFamPayLine);
                        // document.add(table);
                        // table = addHeader(caseData, false, earlyWarning);
                        // }
                        // else {
                        // deleteRow(table, nFamPayLine);
                        // }
                        // 20101124 kiyomi - mark end

                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "眷屬序：" + StringUtils.leftPad(String.valueOf(nFamList + 1), 1, "0"), fontCh12, 0, LEFT); // 第一筆是事故者, 故要加 1
                        addColumn(table, 26, 1, "姓名：" + StringUtils.defaultString(famData.getFamName()), fontCh12, 0, LEFT);
                        addColumn(table, 20, 1, "申請日期：" + StringUtils.defaultString(famData.getFamAppDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 14, 1, "身分證號：" + StringUtils.defaultString(famData.getFamIdnNo()), fontCh12, 0, LEFT);
                        addColumn(table, 15, 1, "出生日期：" + StringUtils.defaultString(famData.getFamBrDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 27, 1, "關係：" + StringUtils.defaultString(famData.getFamEvtRel()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---

                        // ---
                        // addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // addColumn(table, 14, 1, "國籍別：" + StringUtils.defaultString(famData.getFamNationTyp()), fontCh12, 0, LEFT);
                        // addColumn(table, 15, 1, "性別：" + StringUtils.defaultString(famData.getFamSex()), fontCh12, 0, LEFT);
                        // addColumn(table, 27, 1, "國籍："+ StringUtils.defaultString(famData.getFamNationCode()), fontCh12, 0, LEFT);
                        // addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---

                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 14, 1, "結婚日期：" + StringUtils.defaultString(famData.getMarryDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 15, 1, "離婚日期：" + StringUtils.defaultString(famData.getDivorceDateString()), fontCh12, 0, LEFT);
                        addColumn(table,
                                        27,
                                        1,
                                        "每月工作收入註記 / 收入："
                                                        + ((StringUtils.equals(formatBigDecimalToInteger(famData.getMonIncome()), "")) || (StringUtils.equals(formatBigDecimalToInteger(famData.getMonIncome()), "0")) ? StringUtils
                                                                        .defaultString(famData.getMonIncomeMk()) : StringUtils.defaultString(famData.getMonIncomeMk()) + "/"
                                                                        + (!(formatBigDecimalToInteger(famData.getMonIncome()).equals("0") && famData.getMonIncome() != null) ? formatBigDecimalToInteger(famData.getMonIncome()) : "0")), fontCh12, 0,
                                        LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---

                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table,
                                        29,
                                        1,
                                        "在學/段數/起月-迄月："
                                                        + (StringUtils.equals(formatBigDecimalToInteger(famData.getStudDataCount()), "0") ? " " : StringUtils.defaultString(famData.getStudMk())
                                                                        + "/"
                                                                        + StringUtils.defaultString(formatBigDecimalToInteger(famData.getStudDataCount()))
                                                                        + "/"
                                                                        + (!"".equals(famData.getStudSYmString()) && !"".equals(famData.getStudEYmString()) ? StringUtils.defaultString(famData.getStudSYmString()) + "-"
                                                                                        + StringUtils.defaultString(famData.getStudEYmString()) : " ")), fontCh12, 0, LEFT);
                        addColumn(table, 27, 1, "配偶扶養：" + StringUtils.defaultString(famData.getRaiseChildMk()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        
                        // ---
                        
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 40, 1, "學校名稱（代碼）：" + (famData.getSchoolCodeStr() != null ? famData.getSchoolCodeStr() : " ") + famData.getSchoolCodeWithBrackets(), fontCh12, 0, LEFT);
                        addColumn(table, 16, 1, " ", fontCh12, 0, LEFT);                                            
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);                        
                        
                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 29, 1, "領有重度以上身心障礙手冊或證明：" + StringUtils.defaultString(famData.getHandIcapMk()), fontCh12, 0, LEFT);
                        addColumn(table, 27, 1, "收養日期：" + StringUtils.defaultString(famData.getAdoPtDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 29, 1, "放棄請領（起月）：" + StringUtils.defaultString(famData.getAbanYmString()), fontCh12, 0, LEFT);
                        addColumn(table, 27, 1, "親屬關係變動日期：" + StringUtils.defaultString(famData.getRelChgDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---

                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table,
                                        29,
                                        1,
                                        "失蹤日期（起日-迄日）："
                                                        + (!"".equals(famData.getMisSDateString()) || !"".equals(famData.getMisEDateString()) ? StringUtils.defaultString(famData.getMisSDateString()) + "-"
                                                                        + StringUtils.defaultString(famData.getMisEDateString()) : " "), fontCh12, 0, LEFT);
                        addColumn(table,
                                        27,
                                        1,
                                        "監管日期（起日-迄日）："
                                                        + (!"".equals(famData.getPrisSDateString()) || !"".equals(famData.getPrisEDateString()) ? StringUtils.defaultString(famData.getPrisSDateString()) + "-"
                                                                        + StringUtils.defaultString(famData.getPrisEDateString()) : " "), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---

                        if ("Y".equals(famData.getInterDictMk())) {
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 32,1,"受禁治產(監護)宣告-撤銷日期："+ (!"".equals(famData.getInterDictDateString()) || !"".equals(famData.getRepealInterDictDateString()) ? StringUtils.defaultString(famData.getInterDictDateString()) + "-"+ StringUtils.defaultString(famData.getRepealInterDictDateString()) : " "), fontCh12, 0, LEFT);
                            addColumn(table, 24, 1, "死亡日期：" + StringUtils.defaultString(famData.getFamDieDateString()), fontCh12, 0, LEFT);                            
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end                            
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 32, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 24, 1, "得請領起月：" + StringUtils.defaultString(famData.getAbleApsYmStr()), fontCh12, 0, LEFT);                                                                     
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        }
                        else if ("N".equals(famData.getInterDictMk())) {
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 32, 1, "撒銷禁治產宣告日期：" + StringUtils.defaultString(famData.getInterDictDateString()), fontCh12, 0, LEFT);
                            addColumn(table, 24, 1, "死亡日期：" + StringUtils.defaultString(famData.getFamDieDateString()), fontCh12, 0, LEFT);                            
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end                            
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 32, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 24, 1, "得請領起月：" + StringUtils.defaultString(famData.getAbleApsYmStr()), fontCh12, 0, LEFT);                                                                     
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);                            
                        }
                        else {
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 32, 1, "死亡日期：" + StringUtils.defaultString(famData.getFamDieDateString()), fontCh12, 0, LEFT);
                            addColumn(table, 24, 1, "得請領起月：" + StringUtils.defaultString(famData.getAbleApsYmStr()), fontCh12, 0, LEFT);
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        }
                        // ---

                        // ---
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 14, 1, "身分查核年月：" + StringUtils.defaultString(famData.getIdnChkYmString()), fontCh12, 0, LEFT);
                        addColumn(table, 18, 1, "結案日期：" + StringUtils.defaultString(famData.getCloseDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 24, 1, "結案原因：" + famData.getCloseCause(), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---

                        if (famData.getFamChkfileDescList() != null) {
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 58, 1, "符合註記：" + " ", fontCh12, 0, LEFT);

                            List<DisableReviewRpt01FamChkfileDataCase> famChkfileDescList = famData.getFamChkfileDescList();
                            if (famChkfileDescList.size() > 0) {
                                // 取得事故者編審註記資料

                                String previousPayYm = "";
                                List<StringBuffer> famChkfileStringList = new ArrayList<StringBuffer>();
                                StringBuffer famChkfileString = new StringBuffer("");
                                for (DisableReviewRpt01FamChkfileDataCase famChkfileData : famChkfileDescList) {

                                    if (StringUtils.isBlank(previousPayYm) || !StringUtils.equals(famChkfileData.getPayYm(), previousPayYm)) {
                                        // 先將資料放到 ArrayList
                                        if (StringUtils.isNotBlank(previousPayYm))
                                            famChkfileStringList.add(famChkfileString);

                                        previousPayYm = famChkfileData.getPayYm();
                                        famChkfileString = new StringBuffer("");
                                    }

                                    famChkfileString.append(((StringUtils.isBlank(famChkfileString.toString())) ? (famChkfileData.getPayYmString() + "－ " + famChkfileData.getChkCode()) : (" " + famChkfileData.getChkCode())));
                                }
                                // 如果 chkfileString 不為空白則必須於迴圈後將其加入 ArrayList, 否則資料會少一筆

                                if (StringUtils.isNotBlank(famChkfileString.toString()))
                                    famChkfileStringList.add(famChkfileString);

                                // 印出每筆給付年月的事故者編審註記資料

                                for (StringBuffer str : famChkfileStringList) {
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
                                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                                    addColumn(table, 56, 1, str.toString(), fontCh12, 0, LEFT);
                                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                                }
                            }
                        }

                        if (famData.getFamChkfileDataList() != null) {
                            // 20101124 kiyomi - start
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
                            // 20101124 kiyomi - end
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 58, 1, "編審註記：" + " ", fontCh12, 0, LEFT);
                            List<DisableReviewRpt01FamChkfileDataCase> famChkfileDataList = famData.getFamChkfileDataList();
                            if (famChkfileDataList.size() > 0) {
                                // 取得事故者編審註記資料

                                String previousPayYm = "";
                                List<StringBuffer> famChkfileStringList = new ArrayList<StringBuffer>();
                                StringBuffer famChkfileString = new StringBuffer("");
                                for (DisableReviewRpt01FamChkfileDataCase famChkfileData : famChkfileDataList) {

                                    if (StringUtils.isBlank(previousPayYm) || !StringUtils.equals(famChkfileData.getPayYm(), previousPayYm)) {
                                        // 先將資料放到 ArrayList
                                        if (StringUtils.isNotBlank(previousPayYm))
                                            famChkfileStringList.add(famChkfileString);

                                        previousPayYm = famChkfileData.getPayYm();
                                        famChkfileString = new StringBuffer("");
                                    }

                                    famChkfileString.append(((StringUtils.isBlank(famChkfileString.toString())) ? (famChkfileData.getPayYmString() + "－ " + famChkfileData.getChkCode()) : (" " + famChkfileData.getChkCode())));
                                }
                                // 如果 chkfileString 不為空白則必須於迴圈後將其加入 ArrayList, 否則資料會少一筆

                                if (StringUtils.isNotBlank(famChkfileString.toString()))
                                    famChkfileStringList.add(famChkfileString);

                                // 印出每筆給付年月的事故者編審註記資料

                                for (StringBuffer str : famChkfileStringList) {
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
                                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                                    addColumn(table, 56, 1, str.toString(), fontCh12, 0, LEFT);
                                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                                }
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

                    if (caseData.getFamChkDescList() != null) {
                        List<DisableReviewRpt01FamChkfileDataCase> famChkDataDescList = caseData.getFamChkDescList();
                        // 20101124 kiyomi - mark start
                        // 在符合註記說明前, 先隨便塞空白行測試是否需換頁
                        // addEmptyRow(table, famChkDataDescList.size()+1);

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // deleteRow(table, famChkDataDescList.size()+1);
                        // document.add(table);
                        // table = addHeader(caseData, false, earlyWarning);
                        // }
                        // else {
                        // deleteRow(table, famChkDataDescList.size()+1);
                        // }
                        // 20101124 kiyomi - mark end
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "符合註記/說明：" + " ", fontCh12, 0, LEFT);

                        if (famChkDataDescList.size() > 0) {
                            // 取得事故者編審註記資料

                            String previousPayYm = "";
                            List<StringBuffer> famChkfileStringList = new ArrayList<StringBuffer>();
                            StringBuffer famChkfileString = new StringBuffer("");
                            for (DisableReviewRpt01FamChkfileDataCase famChkfileData : famChkDataDescList) {
                                // 20101124 kiyomi - start
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
                                // 20101124 kiyomi - end
                                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                                addColumn(table, 56, 1, StringUtils.defaultString(famChkfileData.getChkCode()) + " " + StringUtils.defaultString(famChkfileData.getChkCodePre()) + " " + StringUtils.defaultString(famChkfileData.getChkResult()),
                                                fontCh12, 0, LEFT);
                                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                            }

                        }
                    }

                    if (caseData.getFamChkDescDataList() != null) {
                        List<DisableReviewRpt01FamChkfileDataCase> famChkDataList = caseData.getFamChkDescDataList();

                        // 20101124 kiyomi - mark start
                        // 在編審註記說明前, 先隨便塞空白行測試是否需換頁
                        // addEmptyRow(table, famChkDataList.size()+1);

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // deleteRow(table, famChkDataList.size()+1);
                        // document.add(table);
                        // table = addHeader(caseData, false, earlyWarning);
                        // }
                        // else {
                        // deleteRow(table, famChkDataList.size()+1);
                        // }
                        // 20101124 kiyomi - mark end
                        // 20101124 kiyomi - start
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
                        // 20101124 kiyomi - end
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 58, 1, "編審註記/說明：" + " ", fontCh12, 0, LEFT);

                        if (famChkDataList.size() > 0) {
                            // 取得事故者編審註記資料

                            String previousPayYm = "";
                            List<StringBuffer> famChkfileStringList = new ArrayList<StringBuffer>();
                            StringBuffer famChkfileString = new StringBuffer("");
                            for (DisableReviewRpt01FamChkfileDataCase famChkfileData : famChkDataList) {
                                // 20101124 kiyomi - start
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
                                // 20101124 kiyomi - end
                                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                                addColumn(table, 56, 1, StringUtils.defaultString(famChkfileData.getChkCode()) + " " + StringUtils.defaultString(famChkfileData.getChkCodePre()) + " " + StringUtils.defaultString(famChkfileData.getChkResult()),
                                                fontCh12, 0, LEFT);
                                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                            }

                        }

                    }

                }
                // ]
                // 受款人資料

                document.add(table);
                
                if(writer.getCurrentPageNumber() % 2 != 0) {  
                   	document.newPage();  
                   	writer.setPageEmpty(false);  
                   }  
            }
            document.close();

        }
        finally {
            document.close();
        }

        return bao;
    }

    
}
