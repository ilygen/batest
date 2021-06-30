package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.query.cases.PaymentQueryBenDataCase;
import tw.gov.bli.ba.query.forms.PaymentQueryForm;
import tw.gov.bli.ba.query.helper.FormSessionHelper;
import tw.gov.bli.ba.rpt.cases.CiptUtilityCase;
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01UnitCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01BenDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01BenPayDataCase;
import tw.gov.bli.ba.rpt.cases.OldAgeReviewRpt01ChkfileDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorRevewRpt01ExpDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01AnnuityPayDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01BenDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01BenPayDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01Case;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01ChkfileDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01CipgDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01DecideDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01DeductDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01DiePayDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01DisablePayDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01InjuryPayDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01IssueAmtDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01JoblessPayDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01LoanAmtCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01NotifyDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01NpPayDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01OncePayDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01PayDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01PayDeductDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01PayOldDataCase;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01UnitCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ValidateUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

/**
 * 勞保遺屬年金給付受理編審清單
 * 
 * @author Rickychi 
 * 20101124 kiyomi 核定通知書之後的內容取消跳頁判斷 
 * 20101208 kiyomi 版面調整、顯示欄位增加
 * 20131119 KIYOMI 增加若 編審註記代碼 (CHKCODE) 值為 P1 或 P2 或 P3，表頭要顯示「＊預警案件」
 * 20150918 KIYOMI 受理/審核清單「請領他類給付資料」不顯示失業給付
 */

public class SurvivorReviewRpt01Report extends ReportBase {

    private int nPage; // 每筆資料的頁數

    private String printDate = ""; // 印表日期

    private String line = "--------------------------------------------------------------------------------------------------------------------------------------";

    public SurvivorReviewRpt01Report() throws Exception {
        super();
        nPage = 0;
        printDate = DateUtility.formatNowChineseDateString(false);
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
    public Table addHeader(SurvivorReviewRpt01Case caseData, boolean attached, String earlyWarning) throws Exception {
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

        // addColumn(table, 15, 1, " ", fontCh12, 0, LEFT); //20101208 kiyomi
        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT); // 20101208 kiyomi
        addColumn(table, 13, 1, "版次：" + StringUtils.defaultString(caseData.getDecideData().getVeriSeq()), fontCh12, 0, LEFT); // 20101208 kiyomi
        addColumn(table, 30, 1, "勞保遺屬年金給付受理/審核清單", fontCh18, 0, CENTER);
        addColumn(table, 13, 1, "頁次：" + StringUtils.leftPad(String.valueOf(writer.getCurrentPageNumber() - nPage), 3, "0"), fontCh12, 0, LEFT);
        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
        // 第 ? 頁 / 共 ? 頁 中的 第 ? 頁 部份
        // [
        // addColumn(table, 15, 1, " ", fontCh12, 0, LEFT);
        // createPageText(500, 800, writer.getCurrentPageNumber() - nPage,"頁次：", " / ", 12, 150, 50);
        // ]
        // ---
        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
        // addColumn(table, 14, 1, "版次："+StringUtils.defaultString(caseData.getDecideData().getVeriSeq()), fontCh12, 0, LEFT); //20101208 kiyomi
        addColumn(table, 22, 1, "受理日期：" + StringUtils.defaultString(caseData.getCrtTimeString()), fontCh12, 0, LEFT); // 20101208 kiyomi
        // addColumn(table, 28, 1, (caseData.getPayList()!= null) ? " " : ((attached) ? "【附表】" : "【總表】"), fontCh12, 0, CENTER); //20101208 kiyomi
        if (writer.getCurrentPageNumber() - nPage == 1)
            addColumn(table, 20, 1, "【" + StringUtils.defaultString(caseData.getCaseTypString()) + "】" + earlyWarningDesc, fontCh12, 0, LEFT);
        else 
            addColumn(table, 20, 1, " ", fontCh12, 0, CENTER);
        
        addColumn(table, 14, 1, "印表日期：" + printDate, fontCh12, 0, LEFT);
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
        addColumn(table, 2, 1, " ", fontCh16, 0, LEFT);
        addColumn(table, 8, 1, caseData.getEvtName(), fontCh16, 0, LEFT);
        addColumn(table, 4, 1, StringUtils.defaultString(caseData.getEvtAge()) + "歲", fontCh16, 8, 0, LEFT);
        addColumn(table, 2, 1, caseData.getEvtSexString(), fontCh16, 0, CENTER);
        addColumn(table, 20, 1, caseData.getEvtBrDateString(), fontCh16, 0, LEFT);
        addColumn(table, 9, 1, caseData.getEvtIdnNo(), fontCh16, 0, LEFT);
        addColumn(table, 2, 1, ValidateUtility.validatePersonIdNo(caseData.getEvtIdnNo()) ? "" : "W", fontCh16, 0, LEFT);
        addBarcode39NoLabel(table, StringUtils.defaultString(caseData.getEvtIdnNo()), 75, 75, -5, 12, 1, 0, LEFT, MIDDLE);
        addColumn(table, 1, 1, " ", fontCh16, 0, LEFT);
        // ---
        addLine(table);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<SurvivorReviewRpt01Case> caseList) throws Exception {
        try {
            document.open();

            for (int index = 0; index < caseList.size(); index++) {
                // 頁次處理
                if (index > 0) {
                    nPage = writer.getPageNumber();
                }

                SurvivorReviewRpt01Case caseData = (SurvivorReviewRpt01Case) caseList.get(index);

                // 表頭
                String earlyWarning = "N";
                List<SurvivorReviewRpt01ChkfileDataCase> chkfileDataList01 = caseData.getChkfileDataList();
                if (chkfileDataList01.size() > 0) {
                    // 取得事故者編審註記資料                    
                    List<StringBuffer> chkfileStringList = new ArrayList<StringBuffer>();
                    StringBuffer chkfileString = new StringBuffer("");
                    for (SurvivorReviewRpt01ChkfileDataCase chkfileData : chkfileDataList01) {
                        // 若 編審註記代碼 (CHKCODE) 值為 P1 或 P2 或 P3，表頭要顯示「＊預警案件」
                        if (StringUtils.equalsIgnoreCase(chkfileData.getChkCode(), "P1") || StringUtils.equalsIgnoreCase(chkfileData.getChkCode(), "P2") || StringUtils.equalsIgnoreCase(chkfileData.getChkCode(), "P3"))
                            earlyWarning = "Y";
                    }
                }
                
                List<SurvivorReviewRpt01BenDataCase> benList01 = caseData.getBenList();
                for (int nBenList = 0; nBenList < benList01.size(); nBenList++) {
                    SurvivorReviewRpt01BenDataCase benData = benList01.get(nBenList);
                    List<SurvivorReviewRpt01BenPayDataCase> benPayList = benData.getBenPayDataList();
                        List<SurvivorReviewRpt01ChkfileDataCase> benChkfileDataList = benData.getChkfileDataList();
                        if (benChkfileDataList.size() > 0) {

                            // 取得事故者編審註記資料
                            String previousPayYm = "";
                            List<StringBuffer> benChkfileStringList = new ArrayList<StringBuffer>();
                            StringBuffer benChkfileString = new StringBuffer("");
                            for (SurvivorReviewRpt01ChkfileDataCase benChkfileData : benChkfileDataList) {

                                if (StringUtils.isBlank(previousPayYm) || !StringUtils.equals(benChkfileData.getPayYm(), previousPayYm)) {
                                    // 先將資料放到 ArrayList
                                    if (StringUtils.isNotBlank(previousPayYm))
                                        benChkfileStringList.add(benChkfileString);

                                    previousPayYm = benChkfileData.getPayYm();
                                    benChkfileString = new StringBuffer("");
                                }

                                benChkfileString.append(((StringUtils.isBlank(benChkfileString.toString())) ? (benChkfileData.getPayYmString() + "－ " + benChkfileData.getChkCode()) : (" " + benChkfileData.getChkCode())));
                            }
                            // 如果 chkfileString 不為空白則必須於迴圈後將其加入 ArrayList, 否則資料會少一筆
                            if (StringUtils.isNotBlank(benChkfileString.toString()))
                                benChkfileStringList.add(benChkfileString);

                            // 印出每筆給付年月的事故者編審註記資料
                            for (StringBuffer str : benChkfileStringList) {
                                // 取得受款人編審註記資料
                                // 若 編審註記代碼 (CHKCODE) 值為 P1 或 P2 或 P3，表頭要顯示「＊預警案件」
                                if (str.toString().contains("P1") || str.toString().contains("P2") || str.toString().contains("P3")) // 編審註記
                                    earlyWarning = "Y";
                            }
                        }
                }
                
                Table table = addHeader(caseData, false, earlyWarning);

                // 核定總額資料
                // [
                SurvivorReviewRpt01IssueAmtDataCase issueAmtData = caseData.getIssueAmtData();

                addColumn(table, 28, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "審核", fontCh12, 0, CENTER);
                addColumn(table, 10, 1, "覆核", fontCh12, 0, CENTER);
                addColumn(table, 10, 1, "總核", fontCh12, 0, CENTER);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                // ---
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "投保金額：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, formatBigDecimalToInteger(caseData.getLsInsmAmt()), fontCh12, 0, RIGHT); // 投保金額
                addColumn(table, 45, 1, " ", fontCh12, 0, LEFT);
                // ---
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "平均薪資：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, formatBigDecimalToInteger(caseData.getDecideData().getInsAvgAmt()), fontCh12, 0, RIGHT); // 平均薪資
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (更正前：) 標題 - 有值才顯示, 此段預留
                addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (更正前：) 的值 - 有值才顯示, 此段預留
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 31, 1, " ", fontCh12, 0, LEFT);
                // ---
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "核定總額：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, formatBigDecimalToInteger(issueAmtData.getIssueAmt()), fontCh12, 0, RIGHT); // 核定總額
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (扣減：) 標題 - 有值才顯示, 此段預留
                addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (扣減：) 的值 - 有值才顯示, 此段預留
                addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 31, 1, " ", fontCh12, 0, LEFT);
                // 補發總額 - 有值時此欄目才顯示
                if (issueAmtData.getSupAmt() != null && issueAmtData.getSupAmt().compareTo(new BigDecimal(0)) != 0) {
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "補發總額：", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, formatBigDecimalToInteger(issueAmtData.getSupAmt()), fontCh12, 0, RIGHT); // 補發總額
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
                    addColumn(table, 6, 1, formatBigDecimalToInteger(issueAmtData.getOtherAmt()), fontCh12, 0, RIGHT); // 扣減總額
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // (扣減：) 標題 - 有值才顯示, 此段預留
                    addColumn(table, 6, 1, " ", fontCh12, 0, RIGHT); // (扣減：) 的值 - 有值才顯示, 此段預留
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 31, 1, " ", fontCh12, 0, LEFT);
                }
                // ---
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "實付總額：", fontCh12, 0, LEFT);
                addColumn(table, 6, 1, formatBigDecimalToInteger(issueAmtData.getAplpayAmt().add(issueAmtData.getInheritorAmt())) , fontCh12, 0, RIGHT); // 實付總額 + 計息存儲金額
                addColumn(table, 45, 1, " ", fontCh12, 0, LEFT);
                // 分隔線
                addLine(table);
                // ]
                // 核定總額資料

                // 給付資料
                // [
                List<SurvivorReviewRpt01PayDataCase> payList = caseData.getPayList();
                List<SurvivorRevewRpt01ExpDataCase> disableList = caseData.getDisableList();

                if (caseData.getPayList() != null) {
                    // 給付資料表頭
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "核定金額", fontCh12, 0, RIGHT);
                    //addColumn(table, 9, 1, "/調整前金額", fontCh12, 0, RIGHT);
                    addColumn(table, 9, 1, "應收/沖抵金額", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "補發金額", fontCh12, 0, RIGHT);
                    addColumn(table, 11, 1, "事故者/遺屬扣減", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "紓困金額", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "匯款匯費", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "實付/存儲", fontCh12, 0, RIGHT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

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

                        SurvivorReviewRpt01PayDataCase payData = payList.get(nPayList);

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 6, 1, payData.getPayYmString(), fontCh12, 0, LEFT); // 給付年月

                        if (payData.getIssueAmt() != null && payData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(payData.getIssueAmt()), fontCh12, 0, RIGHT); // 核定金額
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 核定

                        // if (payData.getAdjustAmt() != null && payData.getAdjustAmt().compareTo(new BigDecimal(0)) != 0)
                        // addColumn(table, 10, 1, formatBigDecimalToInteger(payData.getAdjustAmt()), fontCh12, 0, RIGHT); // / 物價調整金額
                        // else
                        // addColumn(table, 7, 1, " ", fontCh12, 0, RIGHT); // / 物價調整金額

                        //if (!(payData.getIssuCalcAmt().equals(payData.getIssueAmt()))) {
                            //if (payData.getIssuCalcAmt() != null && payData.getIssuCalcAmt().compareTo(new BigDecimal(0)) != 0)
                                //addColumn(table, 7, 1, formatBigDecimalToInteger(payData.getIssuCalcAmt()), fontCh12, 0, RIGHT); // / 調整前金額
                            //else
                                //addColumn(table, 7, 1, " ", fontCh12, 0, RIGHT); // / 調整前金額
                        //} else {
                            //addColumn(table, 7, 1, " ", fontCh12, 0, RIGHT); // / 調整前金額
                        //}
                        
                        //if (payData.getCpiRate().compareTo(new BigDecimal(0)) > 0)
                        //addColumn(table, 7, 1, formatBigDecimalToInteger(payData.getIssuCalcAmt()), fontCh12, 0, RIGHT); // / 調整前金額
                        //else
                        //addColumn(table, 7, 1, " ", fontCh12, 0, RIGHT); // / 調整前金額

                        if (payData.getRecAmt() != null && payData.getRecAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 5, 1, formatBigDecimalToInteger(payData.getRecAmt()) + "/", fontCh12, 0, RIGHT); // 應收金額
                        else
                            addColumn(table, 5, 1, "0/", fontCh12, 0, RIGHT); // 應收金額

                        if (payData.getPayBanance() != null && payData.getPayBanance().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 4, 1, formatBigDecimalToInteger(payData.getPayBanance()), fontCh12, 0, RIGHT); // 沖抵
                        else
                            addColumn(table, 4, 1, "0", fontCh12, 0, RIGHT); // 沖抵

                        if (payData.getSupAmt() != null && payData.getSupAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(payData.getSupAmt()), fontCh12, 0, RIGHT); // 補發金額
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 補發金額

                        if (payData.getOtherAmtEvt() != null && payData.getOtherAmtEvt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 5, 1, formatBigDecimalToInteger(payData.getOtherAmtEvt()) + "/", fontCh12, 0, RIGHT); // 事故者扣減總金額
                        else
                            addColumn(table, 5, 1, "0/", fontCh12, 0, RIGHT); // 事故者扣減總金額

                        if (payData.getOtherAmtBen() != null && payData.getOtherAmtBen().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 5, 1, formatBigDecimalToInteger(payData.getOtherAmtBen()), fontCh12, 0, RIGHT); // 事故者扣減總金額
                        else
                            addColumn(table, 5, 1, "0", fontCh12, 0, RIGHT); // 事故者扣減總金額

                        addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);

                        if (payData.getOffsetAmt() != null && payData.getOffsetAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 5, 1, formatBigDecimalToInteger(payData.getOffsetAmt()), fontCh12, 0, RIGHT); // 紓困金額
                        else
                            addColumn(table, 5, 1, "0", fontCh12, 0, RIGHT); // 紓困金額

                        addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT); // 紓困金額

                        if (payData.getPayRate() != null && payData.getPayRate().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(payData.getPayRate()), fontCh12, 0, RIGHT); // 匯款匯費
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 匯款匯費
                        
                        if ((payData.getAplpayAmt() != null && payData.getAplpayAmt().compareTo(new BigDecimal(0)) != 0) || (payData.getInheritorAmt() != null && payData.getInheritorAmt().compareTo(new BigDecimal(0)) != 0)) {
                        	if(payData.getInheritorAmt() == null) {
                        		payData.setInheritorAmt(new BigDecimal(0));
                        	}
                        	addColumn(table, 6, 1, formatNumber(payData.getAplpayAmt().intValue() + payData.getInheritorAmt().intValue()), fontCh12, 0, RIGHT); // 實付金額
                        }else {
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 實付金額
                        }


                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                }

                SurvivorReviewRpt01DecideDataCase decideData = caseData.getDecideData();

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "實付年資", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "投保年資", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "申請項目", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "申請單位", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "事故發生單位", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "事故日期", fontCh12, 0, LEFT);
                addColumn(table, 8, 1, "判決日期", fontCh12, 0, LEFT);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                // ---

                
                //20110128 kiyomi - start
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                }
                //20110128 kiyomi - end                
                
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                if (decideData.getAplPaySeniY() != null || decideData.getAplPaySeniM() != null)
                    addColumn(table, 7, 1, decideData.getAplPaySeniY() + "年" + decideData.getAplPaySeniM() + "月", fontCh12, 0, LEFT); // 實付
                else
                    addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 實付

                if (decideData.getNitrmY() != null || decideData.getNitrmM() != null)
                    addColumn(table, 7, 1, decideData.getNitrmY() + "年" + decideData.getNitrmM() + "月", fontCh12, 0, LEFT); // 投保
                else
                    addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 投保

                if (caseData.getApItem() != null)
                    addColumn(table, 7, 1, caseData.getApItem(), fontCh12, 0, LEFT); // 申請項目
                else
                    addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 申請項目

                if (caseData.getApUbno() != null)
                    addColumn(table, 10, 1, caseData.getApUbno(), fontCh12, 0, LEFT); // 申請單位
                else
                    addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 申請單位
                if (caseData.getLsUbno() != null)
                    addColumn(table, 10, 1, caseData.getLsUbno(), fontCh12, 0, LEFT); // 事故發生單位
                else
                    addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 事故發生單位

                if (caseData.getEvtDieDate() != null)
                    addColumn(table, 7, 1, caseData.getEvtJobDateString(), fontCh12, 0, LEFT); // 死亡日期
                else
                    addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 死亡日期

                if (disableList.get(0).getJudgeDate() != null)
                    addColumn(table, 8, 1, disableList.get(0).getJudgeDateString(), fontCh12, 0, LEFT); // 判決日期
                else
                    addColumn(table, 8, 1, " ", fontCh12, 0, LEFT); // 判決日期

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                // ---
                
                //20110128 kiyomi - start
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                }
                //20110128 kiyomi - end                
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "傷病分類", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "先核普通", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "國籍別", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "國籍", fontCh12, 0, LEFT);

                // ---

                if (caseData.getDisableList() != null && disableList.size() > 0) {
                                      
                    for (int disList = 0; disList < disableList.size(); disList++) {

                        SurvivorRevewRpt01ExpDataCase disData = disableList.get(disList);
                        if (disableList.get(0).getFamAppMk() != null && StringUtils.isNotBlank(disableList.get(0).getFamAppMk()))
                            addColumn(table, 17, 1, "符合63條之1第3項 ", fontCh12, 0, LEFT);
                        else
                            addColumn(table, 17, 1, "  ", fontCh12, 0, LEFT);
                        // addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); //20101208 kiyomi
                        addColumn(table, 10, 1, "發放方式", fontCh12, 0, LEFT); // 20120817 frank

                        // ---
                        
                        //20110128 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不再塞空白行了
                            deleteRow(table, 1);
                            document.add(table);
                            table = addHeader(caseData, false, earlyWarning);
                        }
                        else {
                            deleteRow(table, 1);
                        }
                        //20110128 kiyomi - end                          
                        
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        if (disData.getEvTyp() != null)
                            addColumn(table, 7, 1, disData.getEvTyp(), fontCh12, 0, LEFT); // 傷病分類
                        else
                            addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 傷病分類

                        if (disData.getPrType() != null)
                            addColumn(table, 7, 1, disData.getPrType(), fontCh12, 0, LEFT); // 先核普通
                        else
                            addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 先核普通

                        addColumn(table, 7, 1, caseData.getEvtNationTpeString(), fontCh12, 0, LEFT); // 國籍別
                        addColumn(table, 10, 1, caseData.getEvtNationCodeName(), fontCh12, 0, LEFT); // 國籍

                        if (disableList.get(0).getFamAppMk() != null && StringUtils.isNotBlank(disableList.get(0).getFamAppMk()))
                            addColumn(table, 17, 1, disableList.get(0).getFamAppMk(), fontCh12, 0, LEFT); // 符合63條之1第3項
                        else
                            addColumn(table, 17, 1, " ", fontCh12, 0, LEFT); // 符合63條之1第3項
                        
                        if(caseData.getInterValMonth().equals("0") || caseData.getInterValMonth().equals("1") || caseData.getInterValMonth().equals(""))
                            addColumn(table, 10, 1, "按月發放", fontCh12, 0, LEFT); //發放方式
                        else if (!caseData.getInterValMonth().equals("0") || !caseData.getInterValMonth().equals("1") || !caseData.getInterValMonth().equals(""))
                        	addColumn(table, 10, 1, "按"+caseData.getInterValMonth()+"個月發放", fontCh12, 0, LEFT); //發放方式
                    }
                }
                else {                                       

                    addColumn(table, 10, 1, "  ", fontCh12, 0, LEFT);
                    addColumn(table, 17, 1, " ", fontCh12, 0, CENTER);

                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 傷病分類
                    addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 先核普通
                    addColumn(table, 7, 1, " ", fontCh12, 0, CENTER);// 國籍別
                    addColumn(table, 10, 1, " ", fontCh12, 0, RIGHT); // 國籍
                    addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 符合63條之1第3項
                    addColumn(table, 17, 1, " ", fontCh12, 0, CENTER);

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

                }

                // ---

                if (caseData.getDisableList() != null && disableList.size() > 0) {

                    for (int disList = 0; disList < disableList.size(); disList++) {

                        SurvivorRevewRpt01ExpDataCase disData = disableList.get(disList);

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        // if ((caseData.getCutAmt()!= null && !"0".equals(formatBigDecimalToInteger(caseData.getCutAmt()))) || (caseData.getLecomAmt()!= null && !"0".equals(formatBigDecimalToInteger(caseData.getLecomAmt()))) || (caseData.getRecomAmt()!= null && !"0".equals(formatBigDecimalToInteger(caseData.getRecomAmt())))){
                        if ((caseData.getCutAmt() != null) || (caseData.getLecomAmt() != null) || (caseData.getRecomAmt() != null)) {
                            addColumn(table, 8, 1, "應扣失能", fontCh12, 0, LEFT);
                            addColumn(table, 8, 1, "已扣失能", fontCh12, 0, LEFT);
                            addColumn(table, 8, 1, "未扣失能", fontCh12, 0, LEFT);
                        }
                        else {
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);
                        }

                        if (disData.getEvCode() != null && StringUtils.isNotBlank(disData.getEvCode()))
                            addColumn(table, 8, 1, "傷病原因", fontCh12, 0, LEFT);
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);

                        if (disData.getCriInPartStr() != null && StringUtils.isNotBlank(disData.getCriInPartStr()))
                            addColumn(table, 14, 1, "受傷部位", fontCh12, 0, LEFT);
                        else
                            addColumn(table, 14, 1, " ", fontCh12, 0, LEFT);

                        if (disData.getCriMedium() != null && StringUtils.isNotBlank(disData.getCriMedium()))
                            addColumn(table, 10, 1, "媒介物", fontCh12, 0, LEFT);
                        else
                            addColumn(table, 10, 1, " ", fontCh12, 0, LEFT);

                        /*
                         * if (disData.getCriInJnmeStr()!= null && StringUtils.isNotBlank(disData.getCriInJnmeStr())) addColumn(table, 25, 1, "國際疾病代碼", fontCh12, 0, LEFT); else addColumn(table, 25, 1, " ", fontCh12, 0, LEFT);
                         */
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // ---

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        // if ((caseData.getCutAmt()!= null && !"0".equals(formatBigDecimalToInteger(caseData.getCutAmt()))) || (caseData.getLecomAmt()!= null && !"0".equals(formatBigDecimalToInteger(caseData.getLecomAmt()))) || (caseData.getRecomAmt()!= null && !"0".equals(formatBigDecimalToInteger(caseData.getRecomAmt())))){
                        if ((caseData.getCutAmt() != null) || (caseData.getLecomAmt() != null) || (caseData.getRecomAmt() != null)) {
                            addColumn(table, 8, 1, formatBigDecimalToInteger((caseData.getCutAmt() == null) ? new BigDecimal(0) : caseData.getCutAmt()), fontCh12, 0, LEFT); // 應扣失能
                            addColumn(table, 8, 1, formatBigDecimalToInteger((caseData.getLecomAmt() == null) ? new BigDecimal(0) : caseData.getLecomAmt()), fontCh12, 0, LEFT); // 已扣失能
                            addColumn(table, 8, 1, formatBigDecimalToInteger((caseData.getRecomAmt() == null) ? new BigDecimal(0) : caseData.getRecomAmt()), fontCh12, 0, LEFT); // 未扣失能
                        }
                        else {
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);
                        }

                        if (disData.getEvCode() != null && StringUtils.isNotBlank(disData.getEvCode()))
                            addColumn(table, 8, 1, disData.getEvCode(), fontCh12, 0, LEFT); // 傷病原因
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT); // 傷病原因

                        if (disData.getCriInPartStr() != null && StringUtils.isNotBlank(disData.getCriInPartStr()))
                            addColumn(table, 14, 1, disData.getCriInPartStr(), fontCh12, 0, LEFT); // 受傷部位
                        else
                            addColumn(table, 14, 1, " ", fontCh12, 0, LEFT); // 受傷部位

                        if (disData.getCriMedium() != null && StringUtils.isNotBlank(disData.getCriMedium()))
                            addColumn(table, 10, 1, disData.getCriMedium(), fontCh12, 0, LEFT); // 媒介物
                        else
                            addColumn(table, 10, 1, " ", fontCh12, 0, LEFT); // 媒介物
                        /*
                         * if (disData.getCriInJnmeStr()!= null && StringUtils.isNotBlank(disData.getCriInJnmeStr())) addColumn(table, 25, 1, disData.getCriInJnmeStr(), fontCh12, 0, LEFT); // 國際疾病代碼 else addColumn(table, 25, 1, " ", fontCh12, 0, LEFT); // 國際疾病代碼
                         */
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
                        if (disData.getCriInJnmeStr() != null && StringUtils.isNotBlank(disData.getCriInJnmeStr()))
                            addColumn(table, 56, 1, "國際疾病代碼", fontCh12, 0, LEFT);
                        else
                            addColumn(table, 56, 1, " ", fontCh12, 0, LEFT);
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
                        if (disData.getCriInJnmeStr() != null && StringUtils.isNotBlank(disData.getCriInJnmeStr()))
                            addColumn(table, 56, 1, disData.getCriInJnmeStr(), fontCh12, 0, LEFT); // 國際疾病代碼
                        else
                            addColumn(table, 56, 1, " ", fontCh12, 0, LEFT); // 國際疾病代碼
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    }

                }
                // ---屆齡日期 符合日期 展減期間／比率 計算式

                if ((caseData.getEvtExpireDate() != null && StringUtils.isNotBlank(caseData.getEvtExpireDate())) || (caseData.getEvtEligibleDate() != null && StringUtils.isNotBlank(caseData.getEvtEligibleDate())) || ((decideData.getOldRateSdate() != null && StringUtils.isNotBlank(decideData.getOldRateSdate())) || (decideData.getOldRateEdate() != null && StringUtils.isNotBlank(decideData.getOldRateEdate())) || decideData.getOldExtraRate() != null)
                                || (decideData.getOldab() != null && StringUtils.isNotBlank(decideData.getOldab())) || (caseData.getCloseDate() != null && StringUtils.isNotBlank(caseData.getCloseDate())) || (caseData.getCloseCause() != null && StringUtils.isNotBlank(caseData.getCloseCause()))) {
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (caseData.getEvtExpireDate() != null && StringUtils.isNotBlank(caseData.getEvtExpireDate()))
                        addColumn(table, 7, 1, "屆齡日期", fontCh12, 0, LEFT);
                    else
                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
                    if (caseData.getEvtEligibleDate() != null && StringUtils.isNotBlank(caseData.getEvtEligibleDate()))
                        addColumn(table, 7, 1, "符合日期", fontCh12, 0, LEFT);
                    else
                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);

                    if ((decideData.getOldRateSdate() != null && StringUtils.isNotBlank(decideData.getOldRateSdate())) || (decideData.getOldRateEdate() != null && StringUtils.isNotBlank(decideData.getOldRateEdate())) || decideData.getOldExtraRate() != null)
                        addColumn(table, 17, 1, "展減期間／比率", fontCh12, 0, LEFT);
                    else
                        addColumn(table, 17, 1, " ", fontCh12, 0, LEFT);
                    if (decideData.getOldab() != null && StringUtils.isNotBlank(decideData.getOldab()))
                        addColumn(table, 10, 1, "計算式", fontCh12, 0, LEFT);
                    else
                        addColumn(table, 10, 1, " ", fontCh12, 0, LEFT);

                    if (caseData.getCloseDate() != null && StringUtils.isNotBlank(caseData.getCloseDate()))
                        addColumn(table, 7, 1, "結案日期", fontCh12, 0, LEFT);
                    else
                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
                    if (caseData.getCloseCause() != null && StringUtils.isNotBlank(caseData.getCloseCause()))
                        addColumn(table, 8, 1, "結案原因", fontCh12, 0, LEFT);
                    else
                        addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // }else{
                    // addColumn(table, 7, 1, " ", fontCh12, 0, CENTER);
                    // addColumn(table, 8, 1, " ", fontCh12, 0, CENTER);
                    // addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // }
                    // ---

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    if (caseData.getEvtExpireDate() != null && StringUtils.isNotBlank(caseData.getEvtExpireDate()))
                        addColumn(table, 7, 1, caseData.getEvtExpireDateString(), fontCh12, 0, LEFT);
                    else
                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);
                    if (caseData.getEvtEligibleDate() != null && StringUtils.isNotBlank(caseData.getEvtEligibleDate()))
                        addColumn(table, 7, 1, caseData.getEvtEligibleDateString(), fontCh12, 0, LEFT);
                    else
                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT);

                    if ((decideData.getOldRateSdate() != null && StringUtils.isNotBlank(decideData.getOldRateSdate())) || (decideData.getOldRateEdate() != null && StringUtils.isNotBlank(decideData.getOldRateEdate())) || decideData.getOldExtraRate() != null)
                        addColumn(table, 17, 1, decideData.getOldRateSdateString() + "-" + (decideData.getOldRateEdate() != null ? decideData.getOldRateEdateString() : "") + "/" + (decideData.getOldExtraRate() != null ? decideData.getOldExtraRate() : ""), fontCh12, 0, LEFT);
                    else
                        addColumn(table, 17, 1, " ", fontCh12, 0, LEFT);
                    if (decideData.getOldab() != null && StringUtils.isNotBlank(decideData.getOldab()))
                        addColumn(table, 10, 1, decideData.getOldab(), fontCh12, 0, LEFT);
                    else
                        addColumn(table, 10, 1, " ", fontCh12, 0, LEFT);

                    if (caseData.getCloseDate() != null)
                        addColumn(table, 7, 1, caseData.getCloseDateString(), fontCh12, 0, LEFT); // 結案日期
                    else
                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 結案日期

                    if (caseData.getCloseCause() != null)
                        addColumn(table, 8, 1, caseData.getCloseCause(), fontCh12, 0, LEFT); // 結案原因
                    else
                        addColumn(table, 8, 1, " ", fontCh12, 0, LEFT); // 結案原因

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // }else{
                    // addColumn(table, 7, 1, " ", fontCh12, 0, CENTER);//結案日期
                    // addColumn(table, 8, 1, " ", fontCh12, 0, CENTER); //結案原因
                    // addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // }
                }
                // ---

                // SurvivorReviewRpt01DieOncePayDataCase dieOncePayData = caseData.getDieOncePayData();

                // ---

                if (caseData.getDabApNo() != null) {
                    if (StringUtils.isNotBlank(caseData.getDabApNo()) && "K".equals(caseData.getDabApNo().substring(0, 1))) {
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 14, 1, "失能年金受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 14, 1, "失能年金核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 30, 1, " ", fontCh12, 0, LEFT);

                        // ---
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        if (caseData.getDabApNo() != null)
                            addColumn(table, 14, 1, caseData.getDabApNo(), fontCh12, 0, LEFT); // 失能年金受理編號
                        else
                            addColumn(table, 14, 1, " ", fontCh12, 0, LEFT); // 失能年金受理編號

                        if (caseData.getDabAnnuAmt() != null && !"0".equals(formatBigDecimalToInteger(caseData.getDabAnnuAmt())))
                            addColumn(table, 10, 1, formatBigDecimalToInteger((caseData.getDabAnnuAmt() == null) ? new BigDecimal(0) : caseData.getDabAnnuAmt()), fontCh12, 0, RIGHT); // 失能年金月給付金額
                        else
                            addColumn(table, 10, 1, "0", fontCh12, 0, RIGHT); // 失能年金月給付金額
                        addColumn(table, 34, 1, " ", fontCh12, 0, LEFT);
                    }
                    else if (StringUtils.isNotBlank(caseData.getDabApNo()) && "L".equals(caseData.getDabApNo().substring(0, 1))) {
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 14, 1, "老年年金受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 14, 1, "老年年金核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 30, 1, " ", fontCh12, 0, LEFT);
                        // ---
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        if (caseData.getDabApNo() != null)
                            addColumn(table, 14, 1, caseData.getDabApNo(), fontCh12, 0, LEFT); // 老年年金受理編號
                        else
                            addColumn(table, 14, 1, " ", fontCh12, 0, LEFT); // 老年年金受理編號

                        if (caseData.getDabAnnuAmt() != null && !"0".equals(formatBigDecimalToInteger(caseData.getDabAnnuAmt())))
                            addColumn(table, 10, 1, formatBigDecimalToInteger((caseData.getDabAnnuAmt() == null) ? new BigDecimal(0) : caseData.getDabAnnuAmt()), fontCh12, 0, RIGHT); // 老年年金月給付金額
                        else
                            addColumn(table, 10, 1, "0", fontCh12, 0, RIGHT); // 老年年金月給付金額
                        addColumn(table, 34, 1, " ", fontCh12, 0, LEFT);
                    }
                }
                
                // ---// 前次核定金額
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "前次核定金額", fontCh12, 0, LEFT);
                addColumn(table, 48, 1, " ", fontCh12, 0, RIGHT);
                
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                if (caseData.getIssuCalcAmt() != null && caseData.getIssuCalcAmt().compareTo(new BigDecimal(0)) != 0)
                    addColumn(table, 8, 1, formatBigDecimalToInteger(caseData.getIssuCalcAmt()), fontCh12, 0, RIGHT); // 前次核定金額
                else
                    addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 前次核定金額
                
                addColumn(table, 50, 1, " ", fontCh12, 0, RIGHT);
                
                // ---
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

                // ] ... end if (StringUtils.isNotBlank(caseData.getEvtDieDate()) && StringUtils.isNotBlank(caseData.getCloseCause()))
                // ]
                // 事故者死亡一次給付相關資料

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
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 58, 1, "事故者編審註記：", fontCh12, 0, LEFT);
                // ---
                List<SurvivorReviewRpt01ChkfileDataCase> chkfileDataList = caseData.getChkfileDataList();
                if (caseData.getChkfileDataList() != null) {
                    // 取得事故者編審註記資料
                    String previousPayYm = "";
                    List<StringBuffer> chkfileStringList = new ArrayList<StringBuffer>();
                    StringBuffer chkfileString = new StringBuffer("");
                    for (SurvivorReviewRpt01ChkfileDataCase chkfileData : chkfileDataList) {
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
                List<SurvivorReviewRpt01ChkfileDataCase> evtChkfileDescList = caseData.getChkfileDescList();
                if (caseData.getChkfileDescList() != null) {
                    for (SurvivorReviewRpt01ChkfileDataCase evtChkfileDesc : evtChkfileDescList) {
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
                        addColumn(table, 46, 1, StringUtils.defaultString(evtChkfileDesc.getChkCode()) + " " + StringUtils.defaultString(evtChkfileDesc.getChkCodePost()) + " " + StringUtils.defaultString(evtChkfileDesc.getChkResult()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }
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
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 15, 1, "電腦編審結果：" + ((StringUtils.equalsIgnoreCase(caseData.getAcceptMk(), "Y")) ? "合格" : ((StringUtils.equalsIgnoreCase(caseData.getAcceptMk(), "N")) ? "不合格" : "待處理")), fontCh12, 0, LEFT);
                addColumn(table, 15, 1, "人工審核結果：" + ((StringUtils.equalsIgnoreCase(caseData.getManchkMk(), "Y")) ? "合格" : ((StringUtils.equalsIgnoreCase(caseData.getManchkMk(), "N")) ? "不合格" : "待處理")), fontCh12, 0, LEFT);
                addColumn(table, 26, 1, "＊", fontCh12, 0, LEFT);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                // 在處理受理鍵入資料及修改紀錄前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 2);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, 2);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 2);
                    addEmptyRow(table, 1);
                }
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 34, 1, "受理鍵入資料及修改紀錄：（鍵入／更正人員代號：" + ((StringUtils.isNotBlank(caseData.getUpdUser())) ? caseData.getUpdUser() : StringUtils.defaultString(caseData.getCrtUser())) + "）", fontCh12, 0, LEFT);
                addColumn(table, 11, 1, ((StringUtils.isNotBlank(caseData.getChgNote())) ? ("更正原因：" + caseData.getChgNote()) : ""), fontCh12, 0, LEFT); // 更正原因 - 有值才顯示
                addColumn(table, 11, 1, ((StringUtils.isNotBlank(caseData.getMexcLvl())) ? ("決行層級：" + caseData.getMexcLvl()) : ""), fontCh12, 0, LEFT); // 決行層級 - 有值才顯示
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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
                    SurvivorReviewRpt01NotifyDataCase notifyData = caseData.getNotifyData();

                    // 先試印核定通知書的 標題 受文者 地址 主旨 及 說明的第一點, 測試是否需換頁
                    // [
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, (disableList.get(0).getMonNotifyingMk().equals("N") || disableList.get(0).getMonNotifyingMk().equals("")) ? "＊不寄發月通知表" : "", fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 15, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addEmptyRow(table, 1);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "受文者：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 50, 1, caseData.getReceiveName(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "地址：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 50, 1, (StringUtils.defaultString(notifyData.getCommZip()) + " " + StringUtils.defaultString(notifyData.getCommAddr())).trim(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "主旨：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 50, 1, notifyData.getSubject(), fontCh12, 8, 0, JUSTIFIED, TOP); // 主旨
                    addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    if (notifyData.getContent().size() > 0) {
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "說明：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                        addColumnAssignVAlignmentAndLineSpace(table, 49, 1, notifyData.getContent().get(0), fontCh12, 8, 0, JUSTIFIED, TOP); // 說明
                        addColumnAssignVAlignmentAndLineSpace(table, 1, 1, " ", fontCh12, 8, 0, JUSTIFIED, TOP);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, (disableList.get(0).getMonNotifyingMk().equals("N") || disableList.get(0).getMonNotifyingMk().equals("")) ? "＊不寄發月通知表" : "", fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "核 定 通 知 書", fontCh12l, 0, RIGHT); // 有底線
                    addColumn(table, 15, 1, "（稿）", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, ((StringUtils.isNotBlank(caseData.getNotifyForm())) ? ("核定格式：" + caseData.getNotifyForm()) : ""), fontCh12, 0, LEFT); // 核定格式 - 有值才顯示
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addEmptyRow(table, 1);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "受文者：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 50, 1, caseData.getReceiveName(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumnAssignVAlignmentAndLineSpace(table, 6, 1, "地址：", fontCh12, 8, 0, JUSTIFIEDALL, TOP);
                    addColumnAssignVAlignmentAndLineSpace(table, 50, 1, (StringUtils.defaultString(notifyData.getCommZip()) + " " + StringUtils.defaultString(notifyData.getCommAddr())).trim(), fontCh12, 8, 0, JUSTIFIED, TOP); // 受文者
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

                            // 換了頁要再印一次
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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
                    addColumn(table, 11, 1, (disableList.get(0).getMonNotifyingMk().equals("N") || disableList.get(0).getMonNotifyingMk().equals("")) ? "＊不寄發月通知表" : "", fontCh12, 0, LEFT);
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

                // ---------------------------------------------------------------------------------------------------

                // 請領同類給付資料
                // [
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
                List<SurvivorReviewRpt01OncePayDataCase> oncePayList = caseData.getOncePayList();
                for (int nOncePayCount = 0; nOncePayCount < oncePayList.size(); nOncePayCount++) { // ... [
                    SurvivorReviewRpt01OncePayDataCase oncePayData = oncePayList.get(nOncePayCount);

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
                        addColumn(table, 58, 1, "[一次給付（包括勞保本人死亡給付、家屬死亡給付、農保死亡給付）]", fontCh12b, 0, LEFT);
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
                    // addEmptyRow(table, 4);

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // deleteRow(table, 4);
                    // document.add(table);
                    // table = addHeader(caseData, false);
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
                    addColumn(table, 7, 1, "申請者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "保險證號", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "事故日期", fontCh12, 0, LEFT);
                    addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);

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
                    addColumn(table, 7, 1, oncePayData.getBmGvName(), fontCh12, 0, LEFT); // 申請者姓名
                    addColumn(table, 7, 1, oncePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
                    addColumn(table, 6, 1, oncePayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
                    addColumn(table, 7, 1, oncePayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
                    addColumn(table, 10, 1, oncePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 8, 1, oncePayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 死亡日期
                    addColumn(table, 11, 1, oncePayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期

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
                    addColumn(table, 7, 1, "申請項目", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "給付月數", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "核付金額", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 12, 1, "不給付日期/原因", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "補收金額", fontCh12, 0, LEFT);
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
                    addColumn(table, 7, 1, oncePayData.getBmDeaapItem(), fontCh12, 0, LEFT); // 申請項目
                    addColumn(table, 5, 1, formatBigDecimalToInteger(oncePayData.getBmChkDay()), fontCh12, 0, LEFT); // 給付月數

                    if (oncePayData.getBmChkAmt() != null && oncePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 7, 1, formatBigDecimalToInteger(oncePayData.getBmChkAmt()), fontCh12, 0, RIGHT); // 核定金額
                    else
                        addColumn(table, 7, 1, "0", fontCh12, 0, RIGHT); // 核定金額
                    addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT); // 核定金額
                    addColumn(table, 7, 1, oncePayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 10, 1, oncePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(oncePayData.getBmNdocMk())) ? " / " + oncePayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 12, 1, oncePayData.getBmNopDateString() + "/ " + oncePayData.getBmNopMark(), fontCh12, 0, LEFT); // 不給付日期
                    if (oncePayData.getBmAdjAmts() != null && oncePayData.getBmAdjAmts().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 7, 1, formatBigDecimalToInteger(oncePayData.getBmAdjAmts()), fontCh12, 0, RIGHT); // 補收金額
                    else
                        addColumn(table, 7, 1, " ", fontCh12, 0, RIGHT); // 補收金額
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // 最後一筆印完後空一行 (如果年金給付資料有資料再印)
                    if ((nOncePayCount == oncePayList.size() - 1) && (caseData.getOncePayList().size() > 0)) {
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
                List<SurvivorReviewRpt01AnnuityPayDataCase> annuityPayList = caseData.getAnnuityPayList();
                for (int nAnnuityPayCount = 0; nAnnuityPayCount < annuityPayList.size(); nAnnuityPayCount++) { // ... [
                    SurvivorReviewRpt01AnnuityPayDataCase annuityPayData = annuityPayList.get(nAnnuityPayCount);

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
                        addColumn(table, 58, 1, "勞保遺屬年金給付：", fontCh12b, 0, LEFT);
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
                    // table = addHeader(caseData, false);
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
                    addColumn(table, 7, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "申請日期", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "保險證號", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "事故日期", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "傷病分類", fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, "首次給付年月", fontCh12, 0, LEFT);

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
                    addColumn(table, 7, 1, annuityPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
                    addColumn(table, 7, 1, annuityPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
                    addColumn(table, 6, 1, annuityPayData.getLsUbno(), fontCh12, 0, LEFT);// 保險證號
                    addColumn(table, 10, 1, annuityPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 8, 1, annuityPayData.getEvtDateString(), fontCh12, 0, LEFT); // 事故日期
                    addColumn(table, 8, 1, annuityPayData.getEvTyp(), fontCh12, 0, LEFT); // 傷病分類
                    addColumn(table, 10, 1, annuityPayData.getPayYmString(), fontCh12, 0, LEFT); // 首次給付年月

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
                    addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "核付日期", fontCh12, 0, LEFT);

                    addColumn(table, 18, 1, "結案日期/結案原因", fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, " ", fontCh12, 0, LEFT);

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
                        addColumn(table, 5, 1, formatBigDecimalToInteger(annuityPayData.getIssueAmt()), fontCh12, 0, RIGHT); // 核定金額
                    else
                        addColumn(table, 5, 1, "0", fontCh12, 0, RIGHT); // 核定金額

                    addColumn(table, 2, 1, " ", fontCh12, 0, RIGHT);

                    addColumn(table, 7, 1, annuityPayData.getChkDateString(), fontCh12, 0, LEFT); // 核定日期
                    addColumn(table, 6, 1, annuityPayData.getAplpayDateString(), fontCh12, 0, LEFT); // 核付日期

                    addColumn(table, 18, 1, annuityPayData.getCloseDtString() + "/" + annuityPayData.getCloseCause(), fontCh12, 0, LEFT); // 結案日期/結案原因
                    addColumn(table, 20, 1, " ", fontCh12, 0, LEFT);

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

                // 國保喪葬給付記錄資料 (有資料再印)
                List<SurvivorReviewRpt01NpPayDataCase> npSurivorDidePayList = caseData.getNpSurivorDidePayList();
                if (caseData.getNpSurivorPayList() != null) {
                    for (int nNpSurivorDidePayCount = 0; nNpSurivorDidePayCount < npSurivorDidePayList.size(); nNpSurivorDidePayCount++) { // ... [
                        SurvivorReviewRpt01NpPayDataCase npSurivorPayData = npSurivorDidePayList.get(nNpSurivorDidePayCount);

                        // 印國保喪葬給付記錄表頭
                        if (nNpSurivorDidePayCount == 0) {
                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                            else {
                                deleteRow(table, 1);
                            }

                            // 國保喪葬給付記錄 表頭
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 58, 1, "國保喪葬給付記錄：", fontCh12b, 0, LEFT);

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
                        // 國保喪葬給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                        // addEmptyRow(table, 4);

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // deleteRow(table, 4);
                        // document.add(table);
                        // table = addHeader(caseData, false);
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
                        addColumn(table, 7, 1, "申請者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 6, 1, "申請日期", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 13, 1, "事故日期", fontCh12, 0, LEFT);
                        addColumn(table, 13, 1, "核定日期 ", fontCh12, 0, LEFT);
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
                        addColumn(table, 7, 1, " ", fontCh12, 0, LEFT); // 申請者姓名
                        addColumn(table, 7, 1, npSurivorPayData.getEvteeName(), fontCh12, 0, LEFT); // 事故者姓名
                        addColumn(table, 6, 1, npSurivorPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
                        addColumn(table, 10, 1, npSurivorPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 13, 1, npSurivorPayData.getEvtDtString(), fontCh12, 0, LEFT); // 事故日期
                        addColumn(table, 13, 1, npSurivorPayData.getChkDtString(), fontCh12, 0, LEFT); // 核定日期

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
                        addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
                        addColumn(table, 44, 1, " ", fontCh12, 0, LEFT);
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

                        if (npSurivorPayData.getIssueAmt() != null && npSurivorPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 5, 1, formatBigDecimalToInteger(npSurivorPayData.getIssueAmt()), fontCh12, 0, RIGHT); // 核定金額
                        else
                            addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT); // 核定金額
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        addColumn(table, 7, 1, npSurivorPayData.getAplPayDateString(), fontCh12, 0, LEFT); // 核付日期
                        addColumn(table, 44, 1, " ", fontCh12, 0, LEFT);

                        // 最後一筆印完後空一行 (如果年金給付資料有資料再印)
                        if ((nNpSurivorDidePayCount == npSurivorDidePayList.size() - 1) && (caseData.getNpSurivorDidePayList().size() > 0)) {
                            // 空白行
                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                // 換了頁就不再塞空白行了
                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                        }
                    } // ] ... end for (int nNpDidePayCount = 0; nNpDidePayCount < npDidePayList.size(); nNpDidePayCount++)
                }
                // ---

                // 國保遺屬年金給付記錄資料 (有資料再印)
                List<SurvivorReviewRpt01NpPayDataCase> npSurivorPayList = caseData.getNpSurivorPayList();
                if (caseData.getNpSurivorPayList() != null) {
                    for (int nNpSurivorPayCount = 0; nNpSurivorPayCount < npSurivorPayList.size(); nNpSurivorPayCount++) { // ... [
                        SurvivorReviewRpt01NpPayDataCase npSurivorPayData = npSurivorPayList.get(nNpSurivorPayCount);

                        // 印國保遺屬年金給付記錄表頭
                        if (nNpSurivorPayCount == 0) {
                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                            else {
                                deleteRow(table, 1);
                            }

                            // 國保遺屬年金給付記錄 表頭
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 58, 1, "國保遺屬年金給付記錄：", fontCh12b, 0, LEFT);

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
                        // 國保遺屬年金給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
                        // addEmptyRow(table, 4);

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // deleteRow(table, 4);
                        // document.add(table);
                        // table = addHeader(caseData, false);
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
                        addColumn(table, 7, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "申請日期", fontCh12, 0, LEFT);
                        addColumn(table, 16, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 13, 1, "事故日期", fontCh12, 0, LEFT);
                        addColumn(table, 13, 1, "首次給付年月", fontCh12, 0, LEFT);
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

                        addColumn(table, 7, 1, npSurivorPayData.getEvteeName(), fontCh12, 0, LEFT); // 事故者姓名
                        addColumn(table, 7, 1, npSurivorPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
                        addColumn(table, 16, 1, npSurivorPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 13, 1, npSurivorPayData.getEvtDtString(), fontCh12, 0, LEFT); // 事故日期
                        addColumn(table, 13, 1, npSurivorPayData.getPayYmsString() + (npSurivorPayData.getPayYme() != null ? "-" + npSurivorPayData.getPayYmeString() : ""), fontCh12, 0, LEFT); // 首次給付年月

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
                        addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
                        addColumn(table, 6, 1, "核付日期", fontCh12, 0, LEFT);
                        addColumn(table, 36, 1, "結案日期 / 結案原因", fontCh12, 0, LEFT);
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

                        if (npSurivorPayData.getIssueAmt() != null && npSurivorPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 5, 1, formatBigDecimalToInteger(npSurivorPayData.getIssueAmt()), fontCh12, 0, RIGHT); // 核定金額
                        else
                            addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT); // 核定金額
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, npSurivorPayData.getChkDtString(), fontCh12, 0, LEFT); // 核定日期

                        addColumn(table, 6, 1, npSurivorPayData.getAplPayDateString(), fontCh12, 0, LEFT); // 核付日期
                        addColumn(table, 36, 1, npSurivorPayData.getCloseDtString() + (StringUtils.isNotBlank(npSurivorPayData.getCloseReason()) ? "/" + npSurivorPayData.getCloseReason() : ""), fontCh12, 0, LEFT); // 結案日期/原因
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        // 最後一筆印完後空一行 (如果年金給付資料有資料再印)
                        if ((nNpSurivorPayCount == npSurivorPayList.size() - 1) && (caseData.getNpSurivorPayList().size() > 0)) {
                            // 空白行
                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                // 換了頁就不再塞空白行了
                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                        }

                    } // ] ... end for (int nNpSurivorPayCount = 0; nNpSurivorPayCount < npSurivorPayList.size(); nNpSurivorPayCount++)
                }
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
                // 請領同類給付資料

                // 請領他類給付資料
                // [
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

                // 申請老年年金給付記錄資料 (有資料再印)
                List<SurvivorReviewRpt01AnnuityPayDataCase> oldPayList = caseData.getOldPayList();
                if (caseData.getOldPayList() != null) {
                    for (int nOldPayCount = 0; nOldPayCount < oldPayList.size(); nOldPayCount++) { // ... [
                        SurvivorReviewRpt01AnnuityPayDataCase oldPayData = oldPayList.get(nOldPayCount);

                        // 印失能給付記錄表頭
                        if (nOldPayCount == 0) {
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
                        // table = addHeader(caseData, false);
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
                        addColumn(table, 9, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "事故日期", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "首次給付年月", fontCh12, 0, LEFT);
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
                        addColumn(table, 9, 1, oldPayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
                        addColumn(table, 9, 1, oldPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
                        addColumn(table, 9, 1, oldPayData.getLsUbno(), fontCh12, 0, LEFT); // 保險證號
                        addColumn(table, 9, 1, oldPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 9, 1, oldPayData.getEvtDateString(), fontCh12, 0, LEFT); // 事故日期
                        addColumn(table, 11, 1, oldPayData.getPayYmsString() + (oldPayData.getPayYme() != null ? "-" + oldPayData.getPayYmeString() : ""), fontCh12, 0, LEFT); // 首次給付年月
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
                        addColumn(table, 9, 1, "核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核付日期", fontCh12, 0, LEFT);
                        addColumn(table, 29, 1, "結案日期/原因", fontCh12, 0, LEFT);
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
                        if (oldPayData.getIssueAmt() != null && oldPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 9, 1, formatBigDecimalToInteger(oldPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                        else
                            addColumn(table, 9, 1, "0", fontCh12, 0, LEFT); // 核定金額

                        addColumn(table, 9, 1, oldPayData.getChkDateString(), fontCh12, 0, LEFT); // 核定日期
                        addColumn(table, 9, 1, oldPayData.getAplpayDateString(), fontCh12, 0, LEFT); // 核付日期
                        addColumn(table, 29, 1, oldPayData.getCloseDtString() + ((StringUtils.isNotBlank(oldPayData.getCloseCause())) ? ((StringUtils.isNotBlank(oldPayData.getCloseDtString())) ? (" / " + oldPayData.getCloseCause()) : oldPayData.getCloseCause()) : ""), fontCh12, 0, LEFT); // 不給付日期
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                        if ((nOldPayCount == oldPayList.size() - 1) && (caseData.getOldPayList().size() > 0)) {
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
                }// ] ... end for (int nOldPayCount = 0; nOldPayCount < oldPayList.size(); nOldPayCount++)

                // 申請失能給付記錄資料 (有資料再印)
                List<SurvivorReviewRpt01DisablePayDataCase> disablePayList = caseData.getDisablePayList();
                if (caseData.getDisablePayList() != null) {
                    for (int nDisablePayCount = 0; nDisablePayCount < disablePayList.size(); nDisablePayCount++) { // ... [
                        SurvivorReviewRpt01DisablePayDataCase disablePayData = disablePayList.get(nDisablePayCount);

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
                            addColumn(table, 58, 1, "申請失能年金給付記錄：", fontCh12b, 0, LEFT);
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
                        // 失能給付記錄資料一筆有六行, 在塞資料前先測試是否需換頁
                        // addEmptyRow(table, 6);

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // deleteRow(table, 6);
                        // document.add(table);
                        // table = addHeader(caseData, false);
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
                        addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "保險證號", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "申請日期", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "診斷失能日期", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "傷病分類", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "首次給付年月", fontCh12, 0, LEFT);
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
                        addColumn(table, 8, 1, disablePayData.getEvtName(), fontCh12, 0, LEFT); // 事故者姓名
                        addColumn(table, 7, 1, disablePayData.getApUbno(), fontCh12, 0, LEFT); // 保險證號
                        addColumn(table, 8, 1, disablePayData.getAppDteString(), fontCh12, 0, LEFT); // 申請日期
                        addColumn(table, 8, 1, disablePayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 8, 1, disablePayData.getEvtJobDateString(), fontCh12, 0, LEFT); // 診斷失能日期
                        addColumn(table, 8, 1, disablePayData.getEvTyp(), fontCh12, 0, LEFT); // 傷病分類
                        addColumn(table, 9, 1, disablePayData.getPayYmsString() + (disablePayData.getPayYme() != null ? "-" + disablePayData.getPayYmeString() : ""), fontCh12, 0, LEFT); // 首次給付年月
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
                        addColumn(table, 8, 1, "失能等級", fontCh12, 0, LEFT);
                        addColumn(table, 48, 1, "失能項目", fontCh12, 0, LEFT);
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
                        addColumn(table, 8, 1, disablePayData.getCriInJclStr(), fontCh12, 0, LEFT); // 失能等級
                        addColumn(table, 48, 1, disablePayData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
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
                        addColumn(table, 8, 1, "核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "核付日期", fontCh12, 0, LEFT);
                        addColumn(table, 34, 1, "結案日期/原因", fontCh12, 0, LEFT);
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
                            addColumn(table, 8, 1, formatBigDecimalToInteger(disablePayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, LEFT); // 核定金額
                        addColumn(table, 7, 1, disablePayData.getChkDateString(), fontCh12, 0, LEFT); // 核定日期
                        addColumn(table, 7, 1, disablePayData.getAplpayDateString(), fontCh12, 0, LEFT); // 核付日期
                        addColumn(table, 34, 1, disablePayData.getCloseDateString() + ((StringUtils.isNotBlank(disablePayData.getCloseCause())) ? ((StringUtils.isNotBlank(disablePayData.getCloseDateString())) ? (" / " + disablePayData.getCloseCause()) : disablePayData.getCloseCause()) : ""), fontCh12, 0, LEFT); // 結案日期
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                        if ((nDisablePayCount == disablePayList.size() - 1) && (caseData.getDisablePayList().size() > 0)) {
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

                // 申請老年給付記錄
                List<SurvivorReviewRpt01OncePayDataCase> oldAgePayList = caseData.getOldAgePayList();
                if (caseData.getOldAgePayList() != null) {
                    for (int nOldAgePayCount = 0; nOldAgePayCount < oldAgePayList.size(); nOldAgePayCount++) { // ... [
                        SurvivorReviewRpt01OncePayDataCase oldAgePayData = oldAgePayList.get(nOldAgePayCount);

                        // 印年金給付表頭
                        if (nOldAgePayCount == 0) {
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
                            addColumn(table, 58, 1, "申請老年給付記錄:", fontCh12b, 0, LEFT);
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
                        addColumn(table, 7, 1, "受款人姓名", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "保險證號", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "受理日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "事故日期", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "核定日期", fontCh12, 0, LEFT);
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
                        addColumn(table, 7, 1, oldAgePayData.getBmGvName(), fontCh12, 0, LEFT);// 受款人姓名
                        addColumn(table, 8, 1, oldAgePayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
                        addColumn(table, 8, 1, oldAgePayData.getBmUbNo(), fontCh12, 0, LEFT);// 保險證號
                        addColumn(table, 8, 1, oldAgePayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
                        addColumn(table, 9, 1, oldAgePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 8, 1, oldAgePayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期
                        addColumn(table, 8, 1, oldAgePayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
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
                        addColumn(table, 7, 1, "核定月數", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "核付日期", fontCh12, 0, LEFT);
                        addColumn(table, 12, 1, "補件日期/註記", fontCh12, 0, LEFT);
                        addColumn(table, 12, 1, "不給付日期/原因", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "補收金額 ", fontCh12, 0, LEFT);
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
                        addColumn(table, 7, 1, formatBigDecimalToInteger(oldAgePayData.getBmChkDay()), fontCh12, 0, LEFT); // 核定月數
                        if (oldAgePayData.getBmChkAmt() != null && oldAgePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(oldAgePayData.getBmChkAmt()), fontCh12, 0, RIGHT); // 核定金額
                        else
                            addColumn(table, 7, 1, "0", fontCh12, 0, RIGHT); // 核定金額
                        addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 8, 1, oldAgePayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期

                        addColumn(table, 12, 1, oldAgePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(oldAgePayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(oldAgePayData.getBmNrepDateString())) ? (" / " + oldAgePayData.getBmNdocMk()) : oldAgePayData.getBmNdocMk()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
                        addColumn(table, 12, 1, oldAgePayData.getBmNopDateString() + ((StringUtils.isNotBlank(oldAgePayData.getBmNopMark())) ? ((StringUtils.isNotBlank(oldAgePayData.getBmNopDateString())) ? (" / " + oldAgePayData.getBmNopMark()) : oldAgePayData.getBmNopMark()) : ""), fontCh12, 0, LEFT); // 不給付日期

                        if (oldAgePayData.getBmAdjAmts() != null && oldAgePayData.getBmAdjAmts().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 5, 1, formatBigDecimalToInteger(oldAgePayData.getBmAdjAmts()), fontCh12, 0, RIGHT); // 補收金額
                        else
                            addColumn(table, 5, 1, "0", fontCh12, 0, RIGHT); // 補收金額

                        addColumn(table, 4, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                        if ((nOldAgePayCount == oldAgePayList.size() - 1) && (caseData.getOldAgePayList().size() > 0)) {
                            // 空白行
                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                // 換了頁就不再塞空白行了
                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                        }

                    } // ] ... end for (int nOldAgePayCount = 0; nOldAgePayCount < getOldAgePayList.size(); nOldAgePayCount++)
                }

                // 申請失能給付記錄
                List<SurvivorReviewRpt01OncePayDataCase> disPayList = caseData.getDisPayList();
                if (caseData.getDisPayList() != null) {
                    for (int nDisPayCount = 0; nDisPayCount < disPayList.size(); nDisPayCount++) { // ... [
                        SurvivorReviewRpt01OncePayDataCase disPayData = disPayList.get(nDisPayCount);

                        // 印年金給付表頭
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

                            // 年金給付 表頭
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 58, 1, "申請失能給付記錄:", fontCh12b, 0, LEFT);
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
                        // }else {
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
                        addColumn(table, 8, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "保險證號", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "受理日期", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "診斷失能日期", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "傷病分類", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
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
                        addColumn(table, 8, 1, disPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
                        addColumn(table, 7, 1, disPayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
                        addColumn(table, 8, 1, disPayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
                        addColumn(table, 8, 1, disPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 8, 1, disPayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 診斷失能日期
                        addColumn(table, 8, 1, disPayData.getBmEvType(), fontCh12, 0, LEFT); // 傷病分類
                        addColumn(table, 9, 1, disPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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
                        addColumn(table, 8, 1, "失能等級", fontCh12, 0, LEFT);
                        addColumn(table, 48, 1, "失能項目", fontCh12, 0, LEFT);
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
                        addColumn(table, 8, 1, disPayData.getCriInJclStr(), fontCh12, 0, LEFT); // 失能等級
                        addColumn(table, 48, 1, disPayData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
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
                        addColumn(table, 8, 1, "核付日數", fontCh12, 0, LEFT);
                        addColumn(table, 7, 1, "核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "核付日期", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "補件日期/註記", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "不給付日期/原因", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "補收金額", fontCh12, 0, LEFT);
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
                        addColumn(table, 8, 1, formatBigDecimalToInteger(disPayData.getBmChkDay()), fontCh12, 0, LEFT); // 核付日數
                        if (disPayData.getBmChkAmt() != null && disPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(disPayData.getBmChkAmt()), fontCh12, 0, RIGHT); // 核定金額
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 核定金額
                        addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);

                        addColumn(table, 8, 1, disPayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期

                        addColumn(table, 11, 1, disPayData.getBmNrepDateString() + ((StringUtils.isNotBlank(disPayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(disPayData.getBmNrepDateString())) ? (" / " + disPayData.getBmNdocMk()) : disPayData.getBmNdocMk()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
                        addColumn(table, 11, 1, disPayData.getBmNopDateString() + ((StringUtils.isNotBlank(disPayData.getBmNopMark())) ? ((StringUtils.isNotBlank(disPayData.getBmNopDateString())) ? (" / " + disPayData.getBmNopMark()) : disPayData.getBmNopMark()) : ""), fontCh12, 0, LEFT); // 不給付日期

                        if (disPayData.getBmAdjAmts() != null && disPayData.getBmAdjAmts().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(disPayData.getBmAdjAmts()), fontCh12, 0, RIGHT); // 補收金額--已收金額
                        else
                            addColumn(table, 7, 1, "0", fontCh12, 0, RIGHT); // 補收金額--已收金額

                        addColumn(table, 6, 1, " ", fontCh12, 0, LEFT);
                        // ---

                        // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                        if ((nDisPayCount == disPayList.size() - 1) && (caseData.getDisPayList().size() > 0)) {
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
                List<SurvivorReviewRpt01InjuryPayDataCase> injurySurvivorPayList = caseData.getInjurySurvivorPayList();
                if (caseData.getInjurySurvivorPayList() != null) {

                    for (int nInjurySurvivorPayCount = 0; nInjurySurvivorPayCount < injurySurvivorPayList.size(); nInjurySurvivorPayCount++) { // ... [
                        SurvivorReviewRpt01InjuryPayDataCase injurySurvivorPayData = injurySurvivorPayList.get(nInjurySurvivorPayCount);
                        // 印年金給付表頭
                        if (nInjurySurvivorPayCount == 0) {
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
                        addColumn(table, 9, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "事故日期", fontCh12, 0, LEFT);
                        addColumn(table, 10, 1, "核定日期", fontCh12, 0, LEFT);

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
                        addColumn(table, 9, 1, injurySurvivorPayData.getBmEvName(), fontCh12, 0, LEFT); // 事故者姓名
                        addColumn(table, 9, 1, injurySurvivorPayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
                        addColumn(table, 9, 1, injurySurvivorPayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
                        addColumn(table, 10, 1, injurySurvivorPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 9, 1, injurySurvivorPayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期
                        addColumn(table, 10, 1, injurySurvivorPayData.getBmExmDteString(), fontCh12, 0, LEFT); // 核定日期
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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
                        addColumn(table, 9, 1, "傷病分類", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "傷病名稱", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "部位", fontCh12, 0, LEFT);
                        addColumn(table, 29, 1, "給付起日-給付迄日", fontCh12, 0, LEFT);
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
                        addColumn(table, 9, 1, injurySurvivorPayData.getBmEvType(), fontCh12, 0, LEFT); // 傷病分類
                        addColumn(table, 9, 1, injurySurvivorPayData.getBmCriInjNme(), fontCh12, 0, LEFT); // 傷病名稱
                        addColumn(table, 9, 1, injurySurvivorPayData.getBmInjInPart(), fontCh12, 0, LEFT); // 部位
                        addColumn(table, 29, 1, injurySurvivorPayData.getBmInjPfmDteString() + ((StringUtils.isNotBlank(injurySurvivorPayData.getBmInjPtoDte())) ? "-" + injurySurvivorPayData.getBmInjPtoDteString() : ""), fontCh12, 0, LEFT); // 給付起日-迄日
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
                        addColumn(table, 9, 1, "核定日數", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核付日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
                        addColumn(table, 20, 1, "不給付日期/原因", fontCh12, 0, LEFT);

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

                        addColumn(table, 9, 1, formatBigDecimalToInteger(injurySurvivorPayData.getBmChkDay()), fontCh12, 0, LEFT); // 核定日數
                        if (injurySurvivorPayData.getBmChkAmt() != null && injurySurvivorPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 9, 1, formatBigDecimalToInteger(injurySurvivorPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 給付金額
                        else
                            addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 給付金額

                        addColumn(table, 9, 1, injurySurvivorPayData.getBmPayDteString(), fontCh12, 0, LEFT); // 核付日期
                        addColumn(table, 9, 1, injurySurvivorPayData.getBmNrepDateString() + ((StringUtils.isNotBlank(injurySurvivorPayData.getBmNdocMk())) ? ((StringUtils.isNotBlank(injurySurvivorPayData.getBmNrepDateString())) ? (" / " + injurySurvivorPayData.getBmNdocMk()) : injurySurvivorPayData.getBmNdocMk()) : ""), fontCh12, 0, LEFT); // 補件日期/註記
                        addColumn(table, 20, 1, injurySurvivorPayData.getBmNopDateString(), fontCh12, 0, LEFT); // 不給付日期

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                        if ((nInjurySurvivorPayCount == injurySurvivorPayList.size() - 1) && caseData.getInjurySurvivorPayList().size() > 0) {
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

                // 申請職災住院醫療給付記錄
                List<SurvivorReviewRpt01OncePayDataCase> hosPayList = caseData.getHosPayList();
                if (caseData.getHosPayList() != null) {
                    for (int nHosPayCount = 0; nHosPayCount < hosPayList.size(); nHosPayCount++) { // ... [
                        SurvivorReviewRpt01OncePayDataCase hosPayData = hosPayList.get(nHosPayCount);

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
                        addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理編號", fontCh12, 0, LEFT);
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
                        addColumn(table, 9, 1, hosPayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
                        addColumn(table, 9, 1, hosPayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
                        addColumn(table, 9, 1, hosPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 20, 1, hosPayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期

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

                // 申請失蹤給付記錄資料 (有資料再印)
                List<SurvivorReviewRpt01DiePayDataCase> rptDisappearPayList = caseData.getDisappearPayList();
                if (caseData.getDisappearPayList() != null) {
                    // 申請失蹤給付記錄資料 (有資料再印)

                    for (int nDisappearPayCount = 0; nDisappearPayCount < rptDisappearPayList.size(); nDisappearPayCount++) { // ... [
                        SurvivorReviewRpt01DiePayDataCase disappearPayData = rptDisappearPayList.get(nDisappearPayCount);

                        // 印年申請失蹤給付記錄表頭
                        if (nDisappearPayCount == 0) {
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
                            addColumn(table, 58, 1, "申請失蹤給付記錄:", fontCh12b, 0, LEFT);
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
                        addColumn(table, 9, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "事故日期", fontCh12, 0, LEFT);
                        addColumn(table, 11, 1, "核定日期", fontCh12, 0, LEFT);
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
                        addColumn(table, 9, 1, StringUtils.defaultString(disappearPayData.getBmEvName()), fontCh12, 0, LEFT); // 事故者姓名
                        addColumn(table, 9, 1, disappearPayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
                        addColumn(table, 9, 1, StringUtils.defaultString(disappearPayData.getBmApDteString()), fontCh12, 0, LEFT); // 受理日期
                        addColumn(table, 9, 1, disappearPayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 9, 1, StringUtils.defaultString(disappearPayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 事故日期
                        addColumn(table, 11, 1, StringUtils.defaultString(disappearPayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
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
                        addColumn(table, 18, 1, "給付起日-給付迄日", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核付日期", fontCh12, 0, LEFT);
                        addColumn(table, 20, 1, "不給付日期/原因", fontCh12, 0, LEFT);
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
                        addColumn(table, 18, 1, disappearPayData.getBmLosToDteString() + ((StringUtils.isNotBlank(disappearPayData.getBmLosFmDteString())) ? "-" + disappearPayData.getBmLosFmDteString() : ""), fontCh12, 0, LEFT); // 給付起日-給付迄日
                        if (disappearPayData.getBmChkAmt() != null && disappearPayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 9, 1, formatBigDecimalToInteger(disappearPayData.getBmChkAmt()), fontCh12, 0, LEFT); // 給付金額
                        else
                            addColumn(table, 9, 1, "0", fontCh12, 0, LEFT); // 給付金額
                        addColumn(table, 9, 1, StringUtils.defaultString(disappearPayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
                        addColumn(table, 20, 1, StringUtils.defaultString(disappearPayData.getBmNopDateString() + ((StringUtils.isNotBlank(disappearPayData.getBmNdocMk())) ? " / " + disappearPayData.getBmNdocMk() : "")), fontCh12, 0, LEFT); // 不給付日期
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                        // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                        if ((nDisappearPayCount == rptDisappearPayList.size() - 1) && caseData.getDisappearPayList().size() > 0) {
                            // 空白行
                            addEmptyRow(table, 1);

                            if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                                // 換了頁就不再塞空白行了
                                deleteRow(table, 1);
                                document.add(table);
                                table = addHeader(caseData, false, earlyWarning);
                            }
                        }
                    } // ] ... end for (int nDisappearPayCount = 0; nDisappearPayCount < disappearPayList.size(); nDisappearPayCount++)
                }
                
                /**
                 * 20150918 marked by Kiyomi
                // 申請失業給付記錄資料 (有資料再印)
                List<SurvivorReviewRpt01JoblessPayDataCase> joblessPayList = caseData.getJoblessPayList();
                if (caseData.getJoblessPayList() != null) {
                    // 申請失業給付記錄資料 (有資料再印)

                    for (int nJoblessPayCount = 0; nJoblessPayCount < joblessPayList.size(); nJoblessPayCount++) { // ... [
                        SurvivorReviewRpt01JoblessPayDataCase joblessPayData = joblessPayList.get(nJoblessPayCount);

                        // 印年申請失蹤給付記錄表頭
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

                            // 年金給付 表頭
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                            addColumn(table, 58, 1, "申請失業給付記錄:", fontCh12b, 0, LEFT);
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
                        addColumn(table, 9, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "事故日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
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
                        addColumn(table, 9, 1, joblessPayData.getName(), fontCh12, 0, LEFT); // 事故者姓名
                        addColumn(table, 9, 1, joblessPayData.getApDteString(), fontCh12, 0, LEFT); // 受理日期
                        addColumn(table, 9, 1, joblessPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 9, 1, joblessPayData.getBmEvtDteString(), fontCh12, 0, LEFT); // 事故日期
                        addColumn(table, 9, 1, joblessPayData.getChkDteString(), fontCh12, 0, LEFT); // 核定日期
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
                        addColumn(table, 9, 1, "求職日期", fontCh12, 0, LEFT);
                        addColumn(table, 18, 1, "給付起日-迄日", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "給付金額", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核付日期", fontCh12, 0, LEFT);
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
                        addColumn(table, 9, 1, joblessPayData.getAprDteString(), fontCh12, 0, LEFT); // 求職日期
                        addColumn(table, 18, 1, joblessPayData.getSymDteString() + ((StringUtils.isNotBlank(joblessPayData.getTymDte())) ? "-" + joblessPayData.getTymDteString() : ""), fontCh12, 0, LEFT); // 給付起日-

                        if (joblessPayData.getChkAmt() != null && joblessPayData.getChkAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 9, 1, formatBigDecimalToInteger(joblessPayData.getChkAmt()), fontCh12, 0, LEFT); // 給付金額
                        else
                            addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 給付金額

                        addColumn(table, 9, 1, joblessPayData.getPayDteString(), fontCh12, 0, LEFT); // 核付日期
                        addColumn(table, 11, 1, joblessPayData.getNpyDteString(), fontCh12, 0, LEFT); // 不給付日期
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
                }
                */

                // 申請農保身心障礙給付記錄資料 (有資料再印)
                List<SurvivorReviewRpt01OncePayDataCase> famDiePayList = caseData.getFamDiePayList();
                for (int nDiePayCount = 0; nDiePayCount < famDiePayList.size(); nDiePayCount++) { // ... [
                    SurvivorReviewRpt01OncePayDataCase famDiePayData = famDiePayList.get(nDiePayCount);

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
                    // 死亡給付記錄資料一筆有四行, 在塞資料前先測試是否需換頁
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
                    addColumn(table, 9, 1, "事故者姓名", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "受理日期", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "受理編號", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "保險證號", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "審殘日期", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "核定金額", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "核定日期", fontCh12, 0, LEFT);
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
                    addColumn(table, 9, 1, StringUtils.defaultString(famDiePayData.getBmEvName()), fontCh12, 0, LEFT); // 事故者姓名
                    addColumn(table, 9, 1, famDiePayData.getBmApDteString(), fontCh12, 0, LEFT); // 受理日期
                    addColumn(table, 9, 1, famDiePayData.getBmApNo(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 9, 1, famDiePayData.getBmUbNo(), fontCh12, 0, LEFT); // 保險證號
                    addColumn(table, 7, 1, StringUtils.defaultString(famDiePayData.getBmEvtDteString()), fontCh12, 0, LEFT); // 審殘日期
                    if (famDiePayData.getBmChkAmt() != null && famDiePayData.getBmChkAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 6, 1, formatBigDecimalToInteger(famDiePayData.getBmChkAmt()), fontCh12, 0, LEFT); // 核定金額
                    else
                        addColumn(table, 6, 1, " ", fontCh12, 0, LEFT); // 核定金額
                    addColumn(table, 7, 1, StringUtils.defaultString(famDiePayData.getBmExmDteString()), fontCh12, 0, LEFT); // 核定日期
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
                    addColumn(table, 9, 1, "核付日數", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "殘廢等級", fontCh12, 0, LEFT);
                    addColumn(table, 38, 1, "殘廢項目", fontCh12, 0, LEFT);
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
                    addColumn(table, 9, 1, formatBigDecimalToInteger(famDiePayData.getBmChkDay()), fontCh12, 0, LEFT); // 核付日數
                    addColumn(table, 9, 1, famDiePayData.getBmCriInjNme(), fontCh12, 0, LEFT); // 失能等級
                    addColumn(table, 38, 1, famDiePayData.getCriInJdpStr(), fontCh12, 0, LEFT); // 失能項目
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
                    addColumn(table, 9, 1, "核付日期", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "補件日期/註記", fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "不給付日期/原因", fontCh12, 0, LEFT);
                    addColumn(table, 18, 1, "補收金額—已收金額", fontCh12, 0, LEFT);
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

                    addColumn(table, 9, 1, StringUtils.defaultString(famDiePayData.getBmPayDteString()), fontCh12, 0, LEFT); // 核付日期
                    addColumn(table, 9, 1, famDiePayData.getBmNrepDateString() + ((StringUtils.isNotBlank(famDiePayData.getBmNdocMk())) ? " / " + famDiePayData.getBmNdocMk() : ""), fontCh12, 0, LEFT); // 補件日期/註記
                    addColumn(table, 20, 1, StringUtils.defaultString(famDiePayData.getBmNopDateString() + ((StringUtils.isNotBlank(famDiePayData.getBmNdocMk())) ? " / " + famDiePayData.getBmNdocMk() : "")), fontCh12, 0, LEFT); // 不給付日期

                    if (famDiePayData.getBmAdjAmts() != null && famDiePayData.getBmAdjAmts().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 10, 1, formatBigDecimalToInteger(famDiePayData.getBmAdjAmts()), fontCh12, 0, RIGHT); // 補收金額--已收金額
                    else
                        addColumn(table, 10, 1, "0", fontCh12, 0, RIGHT); // 補收金額--已收金額
                    addColumn(table, 10, 1, " ", fontCh12, 0, LEFT);
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

                // 申請國保給付記錄資料 (有資料再印)
                List<SurvivorReviewRpt01NpPayDataCase> npPayList = caseData.getNpPayList();
                if (caseData.getNpPayList() != null) {
                    for (int nNpPayCount = 0; nNpPayCount < npPayList.size(); nNpPayCount++) { // ... [
                        SurvivorReviewRpt01NpPayDataCase npPayData = npPayList.get(nNpPayCount);

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
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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
                        addColumn(table, 9, 1, "事故者姓名", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "申請日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "受理編號", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "首次給付年月", fontCh12, 0, LEFT);
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
                        addColumn(table, 9, 1, npPayData.getEvteeName(), fontCh12, 0, LEFT); // 事故者姓名
                        addColumn(table, 9, 1, npPayData.getAppDateString(), fontCh12, 0, LEFT); // 申請日期
                        addColumn(table, 9, 1, npPayData.getApNo(), fontCh12, 0, LEFT); // 受理編號
                        addColumn(table, 9, 1, npPayData.getPayYmString(), fontCh12, 0, LEFT); // 首次給付年月
                        addColumn(table, 20, 1, npPayData.getEvtDtString(), fontCh12, 0, LEFT); // 事故日期

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
                        addColumn(table, 9, 1, "核定金額", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核定日期", fontCh12, 0, LEFT);
                        addColumn(table, 9, 1, "核付日期", fontCh12, 0, LEFT);
                        addColumn(table, 29, 1, "結案日期 / 結案原因", fontCh12, 0, LEFT);
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
                            addColumn(table, 9, 1, formatBigDecimalToInteger(npPayData.getIssueAmt()), fontCh12, 0, LEFT); // 核定金額
                        else
                            addColumn(table, 9, 1, " ", fontCh12, 0, LEFT); // 核定金額
                        addColumn(table, 9, 1, npPayData.getChkDtString(), fontCh12, 0, LEFT); // 核定日期
                        addColumn(table, 9, 1, npPayData.getAplPayDateString(), fontCh12, 0, LEFT); // 核付日期
                        addColumn(table, 29, 1, npPayData.getCloseDtString(), fontCh12, 0, LEFT); // 結案日期
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        // 最後一筆印完後空一行 (其他給付記錄有資料再印)
                        if ((nNpPayCount == npPayList.size() - 1) && caseData.getNpPayList().size() > 0) {
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
                // 在塞分隔線前, 先隨便塞空白行測試是否需換頁

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

                // -----------------------------------------------------------------------------------------------

                // 本次紓困貸款
                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // deleteRow(table, 1);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }
                // else {
                // deleteRow(table, 1);
                // }
                //           

                // 20101124 kiyomi - start
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                }
                // 20101124 kiyomi - end

                List<SurvivorReviewRpt01LoanAmtCase> loanAmtList = caseData.getLoanAmtData();
                if (caseData.getLoanAmtData() != null && loanAmtList.size() > 0) {
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
                    // addLine(table);
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
                    addColumn(table, 8, 1, "勞貸貸款金額", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "本金餘額", fontCh12, 0, LEFT);
                    addColumn(table, 4, 1, "利息", fontCh12, 0, LEFT);
                    addColumn(table, 4, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "本息截止日", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "呆帳金額", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "其他費用", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "扣減金額", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    for (SurvivorReviewRpt01LoanAmtCase loanAmtData : loanAmtList) {
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
                        addColumn(table, 7, 1, formatBigDecimalToInteger(loanAmtData.getLoanAmt()), fontCh12, 0, RIGHT);// 勞貸貸款金額
                        addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 5, 1, formatBigDecimalToInteger(loanAmtData.getRecapAmt()), fontCh12, 0, RIGHT);// 本金餘額
                        addColumn(table, 3, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 4, 1, formatBigDecimalToInteger(loanAmtData.getLoaniTrt()), fontCh12, 0, RIGHT);// 利息
                        addColumn(table, 4, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 8, 1, loanAmtData.getDlineDateString(), fontCh12, 0, LEFT);// 本息截止日
                        addColumn(table, 5, 1, formatBigDecimalToInteger(loanAmtData.getBadDebtAmt()), fontCh12, 0, RIGHT);// 呆帳金額
                        addColumn(table, 3, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 5, 1, formatBigDecimalToInteger(loanAmtData.getOffsetExp()), fontCh12, 0, RIGHT);// 其他費用
                        addColumn(table, 3, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 5, 1, formatBigDecimalToInteger(loanAmtData.getOffsetAmt()), fontCh12, 0, RIGHT);// 扣減金額
                        addColumn(table, 3, 1, " ", fontCh12, 0, RIGHT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }
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

                // ---------------------------------------------------------------------------------------------------
                // 另案扣減資料

                List<SurvivorReviewRpt01DeductDataCase> deductList = caseData.getDeductList();
                if (caseData.getDeductList() != null && deductList.size() > 0) {
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
                    // addLine(table);
                    // }
                    // 20101124 kiyomi - mark end
                    // 印標題
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
                    addColumn(table, 14, 1, "年金受理編號", fontCh12, 0, LEFT);

                    addColumn(table, 4, 1, " ", fontCh12, 0, LEFT);

                    // 印資料
                    for (int i = 0; i < deductList.size(); i++) {
                        SurvivorReviewRpt01DeductDataCase deductData = deductList.get(i);
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

                        addColumn(table, 4, 1, " ", fontCh12, 0, LEFT);

                    }
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

                List<SurvivorReviewRpt01PayDeductDataCase> deductPayList = caseData.getDeductPayList();
                if (caseData.getDeductPayList() != null && deductPayList.size() > 0) {

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

                    // 印標題
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
                    addColumn(table, 7, 1, "未收餘額", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "", fontCh12, 0, RIGHT);
                    addColumn(table, 5, 1, "", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    // ---

                    // 印資料
                    for (int i = 0; i < deductPayList.size(); i++) {
                        SurvivorReviewRpt01PayDeductDataCase deductPayData = deductPayList.get(i);
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
                        addColumn(table, 7, 1, formatBigDecimalToInteger(deductPayData.getRecRem()), fontCh12, 0, RIGHT);
                        addColumn(table, 7, 1, "", fontCh12, 0, RIGHT);
                        addColumn(table, 5, 1, "", fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }
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

                // ]

                // ---------------------------------------------------------------------------------------------------
                // 遺屬資料
                // [
                // 在塞繼承人人數前, 先隨便塞空白行測試是否需換頁
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

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "遺屬資料：", fontCh12b, 0, JUSTIFIEDALL); // 有底線
                addColumn(table, 48, 1, "遺屬申請人數：" + caseData.getBenList().size(), fontCh12, 0, LEFT);
                addEmptyRow(table, 1);
               
                List<SurvivorReviewRpt01BenDataCase> benList = caseData.getBenList();

                for (int nBenList = 0; nBenList < benList.size(); nBenList++) {
                    SurvivorReviewRpt01BenDataCase benData = benList.get(nBenList);

                    // 空白行 (每個受款人間留一行空白)
                    // addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不再塞空白行了
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }

                    // 為求每位受款人的資料能在同一頁, 故先計算每位受款人之資料行數
                    // 並預先塞入空白行, 測試是否需換頁
                    // 行數 = 固定行數 5 行 + 法定代理人 1 行 (若有值) + 給付資料 n 行 + 給付資料標題 1 行 (若給付資料 > 0 筆)
                    List<SurvivorReviewRpt01BenPayDataCase> benPayList = benData.getBenPayDataList();

                    int nBenPayLine = 5 + benPayList.size();

                    // 20101124 kiyomi - mark start
                    // 給付資料標題 1 行 (若給付資料 > 0 筆)
                    // if (benPayList.size() > 0)
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
                    // 20101124 kiyomi - start
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                    addColumn(table, 16, 1, "遺屬序：" + StringUtils.defaultString(benData.getSeqNo().substring(0, 2)), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "姓名：" + StringUtils.defaultString(benData.getBenName()), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "申請日期：" + StringUtils.defaultString(benData.getAppDateString()), fontCh12, 0, LEFT);
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
                    addColumn(table, 20, 1, "出生日期：" + StringUtils.defaultString(benData.getBenBrDateString()), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "關係：" + StringUtils.defaultString(benData.getBenEvtRel()), fontCh12, 0, LEFT);
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
                    addColumn(table, 16, 1, "國籍別：" + StringUtils.defaultString(benData.getBenNationTpeString()), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "國籍：" + StringUtils.defaultString(benData.getBenNationCodeName()), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "性別：" + StringUtils.defaultString(benData.getBenSexString()), fontCh12, 0, LEFT);
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
                    addColumn(table, 16, 1, "給付方式：" + StringUtils.defaultString(benData.getPayTyp()), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "電話1：" + StringUtils.defaultString(benData.getTel1()), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "電話2：" + StringUtils.defaultString(benData.getTel2()), fontCh12, 0, LEFT);
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
                    addColumn(table, 30, 1, "帳號：" + strBenAccountNo + " " + StringUtils.defaultString(benData.getBankName()), fontCh12, 0, LEFT);
                    addColumn(table, 16, 1, "戶名：" + StringUtils.defaultString(benData.getAccName()), fontCh12, 0, LEFT);
                    if(StringUtils.isBlank(benData.getSpecialAcc())){
                    	addColumn(table, 12, 1, "" , fontCh12, 0, LEFT);
                    }else{
                    	addColumn(table, 12, 1, "(專戶)", fontCh12, 0, LEFT);
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
                    addColumn(table, 56, 1, "地址：" + StringUtils.defaultString(benData.getCommZip()) + "_" + StringUtils.defaultString(benData.getCommAddr()), fontCh12, 0, LEFT);
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
                    addColumn(table, 16, 1, "結婚日期：" + StringUtils.defaultString(benData.getMarryDateString()), fontCh12, 0, LEFT);
                    addColumn(table, 40, 1, "每月工作收入註記 / 收入："
                                    + ((StringUtils.equals(formatBigDecimalToInteger(benData.getMonIncome()), "")) || (StringUtils.equals(formatBigDecimalToInteger(benData.getMonIncome()), "0")) ? StringUtils.defaultString(benData.getMonIncomeMk()) : StringUtils.defaultString(benData.getMonIncomeMk()) + "/" + (!(formatBigDecimalToInteger(benData.getMonIncome()).equals("0") && benData.getMonIncome() != null) ? formatBigDecimalToInteger(benData.getMonIncome()) : "0")),
                                    fontCh12, 0, LEFT);
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
                    addColumn(table, 16, 1, "配偶扶養：" + benData.getRaiseChildMk(), fontCh12, 0, LEFT);
                    addColumn(table, 40, 1, "在學/段數/起月-迄月："
                                    + (benData.getTermCount() == null || StringUtils.equals(formatBigDecimalToInteger(benData.getTermCount()), "0") ? " " : StringUtils.defaultString(benData.getStudMk()) + "/" + StringUtils.defaultString(formatBigDecimalToInteger(benData.getTermCount())) + "/"
                                                    + (!"".equals(benData.getStudSYmString()) && !"".equals(benData.getStudEYmString()) ? StringUtils.defaultString(benData.getStudSYmString()) + "-" + StringUtils.defaultString(benData.getStudEYmString()) : " ")), fontCh12, 0, LEFT);
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
                    addColumn(table, 16, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 40, 1, "學校名稱（代碼）：" + (benData.getSchoolCodeStr() != null ? benData.getSchoolCodeStr() : " ") + benData.getSchoolCodeWithBrackets(), fontCh12, 0, LEFT);                    
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
                    addColumn(table, 16, 1, "婚姻狀況：" + (benData.getBenMarrMk() != null ? benData.getBenMarrMk() : " "), fontCh12, 0, LEFT);
                    addColumn(table, 40, 1, "重殘 / 段數 / 起月 / 迄月：" + (benData.getHandicaptermCount() == null || StringUtils.equals(formatBigDecimalToInteger(benData.getHandicaptermCount()), "0") ? " " : "Y" + "/" + StringUtils.defaultString(formatBigDecimalToInteger(benData.getHandicaptermCount())) + "/"
                                    + (!"".equals(benData.getHandicapSYmString()) && !"".equals(benData.getHandicapEYmString()) ? StringUtils.defaultString(benData.getHandicapSYmString()) + "-" + StringUtils.defaultString(benData.getHandicapEYmString()) : " ")), fontCh12, 0, LEFT);
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
                    addColumn(table, 16, 1, "收養日期：" + StringUtils.defaultString(benData.getAdoPtDateString()), fontCh12, 0, LEFT);
                    addColumn(table, 40, 1, "受禁治產(監護)宣告(起日-迄日)：" + (!"".equals(benData.getInterDictDateString()) || !"".equals(benData.getRepealInterDictDateString()) ? StringUtils.defaultString(benData.getInterDictDateString()) + "-" + StringUtils.defaultString(benData.getRepealInterDictDateString()) : " "), fontCh12, 0, LEFT);
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
                    addColumn(table, 16, 1, "得請領起月：" + StringUtils.defaultString(benData.getAbleApsYmString()), fontCh12, 0, LEFT);
                    addColumn(table, 40, 1, "被保險人扶養：" + StringUtils.defaultString(benData.getRaiseEvtMk()), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    if (!"".equals(benData.getBenDieDateString()) || !"".equals(benData.getPrisSDateString())) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 16, 1, "死亡日期：" + StringUtils.defaultString(benData.getBenDieDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 40, 1, "監管日期（起日-迄日）： " + (!"".equals(benData.getPrisSDateString()) || !"".equals(benData.getPrisEDateString()) ? StringUtils.defaultString(benData.getPrisSDateString()) + "-" + StringUtils.defaultString(benData.getPrisEDateString()) : " "), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                    // ---
                    if (!"".equals(benData.getMisSDateString()) || !"".equals(benData.getDigamyDateString())) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 16, 1, "再婚日期：" + StringUtils.defaultString(benData.getDigamyDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 40, 1, "失蹤日期（起日-迄日）：" + (!"".equals(benData.getMisSDateString()) || !"".equals(benData.getMisEDateString()) ? StringUtils.defaultString(benData.getMisSDateString()) + "-" + StringUtils.defaultString(benData.getMisEDateString()) : " "), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                    // ---
                    if (!"".equals(benData.getAbanYmString()) || !"".equals(benData.getRelChgDateString()) || !"".equals(benData.getIdnChkYmString())) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 16, 1, "身分查核年月：" + StringUtils.defaultString(benData.getIdnChkYmString()), fontCh12, 0, LEFT);
                        addColumn(table, 20, 1, "放棄請領起月： " + StringUtils.defaultString(benData.getAbanYmString()), fontCh12, 0, LEFT);
                        addColumn(table, 20, 1, "親屬關係變動日期：" + StringUtils.defaultString(benData.getRelChgDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                    // ---
                    if (StringUtils.isNotBlank(benData.getSavingMk()) || StringUtils.isNotBlank(benData.getCloseDate()) || StringUtils.isNotBlank(benData.getCloseCause())) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 20, 1, "計息存儲：" + StringUtils.defaultString(benData.getSavingMkStr()), fontCh12, 0, LEFT);
                        addColumn(table, 16, 1, "結案原因：" + StringUtils.defaultString(benData.getCloseCause()), fontCh12, 0, LEFT);
                        addColumn(table, 20, 1, "結案日期：" + StringUtils.defaultString(benData.getCloseDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                    // ---
                    /*
                     * if(!"".equals(benData.getMisSDateString())|| !"".equals(benData.getPrisSDateString())){ //20101124 kiyomi - start addEmptyRow(table, 1); if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數 // 換了頁就不再塞空白行了 deleteRow(table, 1); document.add(table); table = addHeader(caseData, false, earlyWarning); } else { deleteRow(table, 1); } //20101124 kiyomi - end addColumn(table, 2, 1, " ", fontCh12, 0, LEFT); addColumn(table, 28, 1, "失蹤日期（起日-迄日）：" +
                     * (!"".equals(benData.getMisSDateString())||!"".equals(benData.getMisEDateString())?StringUtils.defaultString(benData.getMisSDateString())+"-"+ StringUtils.defaultString(benData.getMisEDateString()):" "), fontCh12, 0, LEFT); addColumn(table, 28, 1, "監管日期（起日-迄日）： " + (!"".equals(benData.getPrisSDateString())||!"".equals(benData.getPrisEDateString())?StringUtils.defaultString(benData.getPrisSDateString())+"-"+ StringUtils.defaultString(benData.getPrisEDateString()):" "),
                     * fontCh12, 0, LEFT); addColumn(table, 2, 1, " ", fontCh12, 0, LEFT); }
                     */
                    // ---
                    /*
                     * //20101208 kiyomi if("Y".equals(benData.getInterDictMk())){ //20101124 kiyomi - start addEmptyRow(table, 1); if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數 // 換了頁就不再塞空白行了 deleteRow(table, 1); document.add(table); table = addHeader(caseData, false, earlyWarning); } else { deleteRow(table, 1); } //20101124 kiyomi - end addColumn(table, 2, 1, " ", fontCh12, 0, LEFT); addColumn(table, 36, 1, "受禁治產(監護)宣告-撤銷日期：" +
                     * (!"".equals(benData.getInterDictDateString())&&!"".equals(benData.getRepealInterDictDateString())?StringUtils.defaultString(benData.getInterDictDateString())+"-"+ StringUtils.defaultString(benData.getRepealInterDictDateString()):" "), fontCh12, 0, LEFT); addColumn(table, 20, 1, "婚姻狀況：" +(benData.getBenMarrMk()!=null?benData.getBenMarrMk():" "), fontCh12, 0, LEFT); addColumn(table, 2, 1, " ", fontCh12, 0, LEFT); }else if("N".equals(benData.getInterDictMk())){ //20101124
                     * kiyomi - start addEmptyRow(table, 1); if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數 // 換了頁就不再塞空白行了 deleteRow(table, 1); document.add(table); table = addHeader(caseData, false, earlyWarning); } else { deleteRow(table, 1); } //20101124 kiyomi - end addColumn(table, 2, 1, " ", fontCh12, 0, LEFT); addColumn(table, 36, 1, "受禁治產(監護)宣告-撤銷日期：" +
                     * (!"".equals(benData.getInterDictDateString())&&!"".equals(benData.getRepealInterDictDateString())?StringUtils.defaultString(benData.getInterDictDateString())+"-"+ StringUtils.defaultString(benData.getRepealInterDictDateString()):" "), fontCh12, 0, LEFT); addColumn(table, 20, 1, "婚姻狀況：" +(benData.getBenMarrMk()!=null?benData.getBenMarrMk():" "), fontCh12, 0, LEFT); addColumn(table, 2, 1, " ", fontCh12, 0, LEFT); }else{ //20101124 kiyomi - start addEmptyRow(table, 1); if
                     * (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數 // 換了頁就不再塞空白行了 deleteRow(table, 1); document.add(table); table = addHeader(caseData, false, earlyWarning); } else { deleteRow(table, 1); } //20101124 kiyomi - end addColumn(table, 2, 1, " ", fontCh12, 0, LEFT); addColumn(table, 56, 1, "婚姻狀況：" +(benData.getBenMarrMk()!=null?benData.getBenMarrMk():" "), fontCh12, 0, LEFT); addColumn(table, 2, 1, " ", fontCh12, 0, LEFT); }
                     */
                    if (StringUtils.isNotBlank(benData.getGrdName())) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 25, 1, "法定代理人姓名：" + StringUtils.defaultString(benData.getGrdName()), fontCh12, 0, LEFT);
                        addColumn(table, 33, 1, " ", fontCh12, 0, LEFT);
                    }

                    if (StringUtils.isNotBlank(benData.getGrdIdnNo()) || StringUtils.isNotBlank(benData.getGrdBrDate())) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 20, 1, "法定代理人出生日期：" + StringUtils.defaultString(benData.getGrdBrDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 36, 1, "法定代理人身分證號：" + StringUtils.defaultString(benData.getGrdIdnNo()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                    if (!"".equals(benData.getAssignName())) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 25, 1, "代辦人姓名：" + StringUtils.defaultString(benData.getAssignName()), fontCh12, 0, LEFT);
                        addColumn(table, 33, 1, " ", fontCh12, 0, LEFT);
                    }

                    if (StringUtils.isNotBlank(benData.getAssignBrDateString()) || StringUtils.isNotBlank(benData.getAssignIdnNo())) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 20, 1, "代辦人出生日期：" + StringUtils.defaultString(benData.getAssignBrDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 36, 1, "代辦人身分證號（統一編號）：" + StringUtils.defaultString(benData.getAssignIdnNo()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                    // ---

                    // 20101124 kiyomi - mark start
                    // addEmptyRow(table, 1);
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
                        addEmptyRow(table, 1);
                    }
                    // 20101124 kiyomi - end

                    // ---
                    if (benPayList.size() > 0) {

                        // 20101124 kiyomi - mark start
                        // 塞受款人資料的資料前, 先隨便塞空白行測試是否需換頁
                        // addEmptyRow(table, benPayList.size()+1);

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不再塞空白行了
                        // deleteRow(table, benPayList.size()+1);
                        // document.add(table);
                        // table = addHeader(caseData, false, earlyWarning);
                        // }
                        // else {
                        // deleteRow(table, benPayList.size()+1);
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
                        addColumn(table, 6, 1, "給付年月", fontCh12, 0, LEFT);
                        addColumn(table, 6, 1, "可領金額", fontCh12, 0, RIGHT);
                        addColumn(table, 6, 1, "應收金額", fontCh12, 0, RIGHT);
                        addColumn(table, 6, 1, "本案沖抵", fontCh12, 0, RIGHT);
                        // addColumn(table, 6, 1, "扣減金額", fontCh12, 0, RIGHT);
                        addColumn(table, 6, 1, "事故者", fontCh12, 0, RIGHT);
                        addColumn(table, 1, 1, "/", fontCh12, 0, RIGHT);
                        addColumn(table, 6, 1, "遺屬扣減", fontCh12, 0, RIGHT);
                        // addColumn(table, 14, 1, "攤紓困/呆　帳", fontCh12, 0, CENTER);
                        addColumn(table, 7, 1, "攤紓困", fontCh12, 0, RIGHT);
                        // addColumn(table, 1, 1, "/", fontCh12, 0, RIGHT);
                        // addColumn(table, 6, 1, "呆帳", fontCh12, 0, LEFT);
                        addColumn(table, 6, 1, "匯款匯費", fontCh12, 0, RIGHT);
                        addColumn(table, 6, 1, "實付/存儲", fontCh12, 0, RIGHT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }
                    // ---

                    for (SurvivorReviewRpt01BenPayDataCase benPayData : benPayList) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 6, 1, benPayData.getPayYmString(), fontCh12, 0, LEFT); // 給付年月

                        if (benPayData.getIssueAmt() != null && benPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(benPayData.getIssueAmt()), fontCh12, 0, RIGHT); // 可領金額
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 可領金額

                        if (benPayData.getRecAmt() != null && benPayData.getRecAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(benPayData.getRecAmt()), fontCh12, 0, RIGHT); // 應收金額
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 應收金額

                        if (benPayData.getPayBanance() != null && benPayData.getPayBanance().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(benPayData.getPayBanance()), fontCh12, 0, RIGHT); // 本案沖抵
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 本案沖抵

                        if (benPayData.getEvtOtherAmt() != null && benPayData.getEvtOtherAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(benPayData.getEvtOtherAmt()), fontCh12, 0, RIGHT); // 事故者扣減
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 事故者扣減
                                                
                        addColumn(table, 1, 1, "/", fontCh12, 0, LEFT);
                        
                        if (benPayData.getOtherAmt() != null && benPayData.getOtherAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(benPayData.getOtherAmt()), fontCh12, 0, RIGHT); // 遺屬扣減金額
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 遺屬扣減金額

                        if (benPayData.getEvtOffsetAmt() != null && benPayData.getEvtOffsetAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 7, 1, formatBigDecimalToInteger(benPayData.getEvtOffsetAmt()), fontCh12, 0, RIGHT); // 攤紓困金額
                        else
                            addColumn(table, 7, 1, "0", fontCh12, 0, RIGHT); // 攤紓困金額

                        

                        //if (benPayData.getBadDebtAmt() != null && benPayData.getBadDebtAmt().compareTo(new BigDecimal(0)) != 0)
                            //addColumn(table, 6, 1, formatBigDecimalToInteger(benPayData.getBadDebtAmt()), fontCh12, 0, RIGHT); // 呆帳金額
                        //else
                            //addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 呆帳金額

                        if (benPayData.getPayRate() != null && benPayData.getPayRate().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 6, 1, formatBigDecimalToInteger(benPayData.getPayRate()), fontCh12, 0, RIGHT); // 匯款匯費
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 匯款匯費

                        if ((benPayData.getAplpayAmt() != null && benPayData.getAplpayAmt().compareTo(new BigDecimal(0)) != 0) || (benPayData.getInheritorAmt() != null && benPayData.getInheritorAmt().compareTo(new BigDecimal(0)) != 0))
                            // addColumn(table, 6, 1, formatBigDecimalToInteger(benPayData.getAplpayAmt()), fontCh12, 0, RIGHT); // 實付金額 
                            addColumn(table, 6, 1, formatNumber(benPayData.getAplpayAmt().intValue() + benPayData.getInheritorAmt().intValue()), fontCh12, 0, RIGHT); // 實付金額 + 計息存儲金額
                        else
                            addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 實付金額 + 計息存儲金額

                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                    // 20101124 kiyomi - mark start
                    // addEmptyRow(table, 2);
                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    // deleteRow(table, 1);
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, 1);
                    // }
                    // 20101124 kiyomi - mark end
                    if (benData.getChkDataList() != null) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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

                        // 20101124 kiyomi - mark start
                        // addEmptyRow(table, 1);
                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不印分隔線了
                        // deleteRow(table, 1);
                        // document.add(table);
                        // table = addHeader(caseData, false, earlyWarning);
                        // }
                        // else {
                        // deleteRow(table, 1);

                        // }
                        // 20101124 kiyomi - mark end

                        List<SurvivorReviewRpt01ChkfileDataCase> chkFileDataList = benData.getChkDataList();
                        if (chkFileDataList.size() > 0) {

                            // 取得事故者編審註記資料
                            String previousPayYm = "";
                            List<StringBuffer> chkfileStringList = new ArrayList<StringBuffer>();
                            StringBuffer chkfileString = new StringBuffer("");
                            for (SurvivorReviewRpt01ChkfileDataCase chkfileData : chkFileDataList) {

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
                        addEmptyRow(table, 1);
                    }

                    if (benData.getChkfileDataList() != null) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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

                        // 20101124 kiyomi - mark start
                        // addEmptyRow(table, 1);
                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不印分隔線了
                        // deleteRow(table, 1);
                        // document.add(table);
                        // table = addHeader(caseData, false, earlyWarning);
                        // }
                        // else {
                        // deleteRow(table, 1);
                        // }
                        // 20101124 kiyomi - mark end

                        List<SurvivorReviewRpt01ChkfileDataCase> benChkfileDataList = benData.getChkfileDataList();
                        if (benChkfileDataList.size() > 0) {

                            // 取得事故者編審註記資料
                            String previousPayYm = "";
                            List<StringBuffer> benChkfileStringList = new ArrayList<StringBuffer>();
                            StringBuffer benChkfileString = new StringBuffer("");
                            for (SurvivorReviewRpt01ChkfileDataCase benChkfileData : benChkfileDataList) {

                                if (StringUtils.isBlank(previousPayYm) || !StringUtils.equals(benChkfileData.getPayYm(), previousPayYm)) {
                                    // 先將資料放到 ArrayList
                                    if (StringUtils.isNotBlank(previousPayYm))
                                        benChkfileStringList.add(benChkfileString);

                                    previousPayYm = benChkfileData.getPayYm();
                                    benChkfileString = new StringBuffer("");
                                }

                                benChkfileString.append(((StringUtils.isBlank(benChkfileString.toString())) ? (benChkfileData.getPayYmString() + "－ " + benChkfileData.getChkCode()) : (" " + benChkfileData.getChkCode())));
                            }
                            // 如果 chkfileString 不為空白則必須於迴圈後將其加入 ArrayList, 否則資料會少一筆
                            if (StringUtils.isNotBlank(benChkfileString.toString()))
                                benChkfileStringList.add(benChkfileString);

                            // 印出每筆給付年月的事故者編審註記資料
                            for (StringBuffer str : benChkfileStringList) {
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

                        addEmptyRow(table, 1);
                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                            // 換了頁就不印分隔線了
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
                        // 換了頁就不印分隔線了
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                    }

                    // 編審結果判斷 Y 合格 N 不合格 空白 待處理
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 15, 1, "電腦編審結果：" + ((StringUtils.equalsIgnoreCase(benData.getAcceptMk(), "Y")) ? "合格" : ((StringUtils.equalsIgnoreCase(benData.getAcceptMk(), "N")) ? "不合格" : "待處理")), fontCh12, 0, LEFT);
                    addColumn(table, 15, 1, "人工審核結果：" + ((StringUtils.equalsIgnoreCase(benData.getManchkMk(), "Y")) ? "合格" : ((StringUtils.equalsIgnoreCase(benData.getManchkMk(), "N")) ? "不合格" : "待處理")), fontCh12, 0, LEFT);
                    addColumn(table, 26, 1, "＊", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

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
                        addEmptyRow(table, 1);
                    }
                }

                if (caseData.getBenChkDescList() != null) {
                    // 20101124 kiyomi - start
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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

                    List<SurvivorReviewRpt01ChkfileDataCase> benChkDataDescList = caseData.getBenChkDescList();
                    if (benChkDataDescList.size() > 0) {
                        // 取得事故者編審註記資料
                        String previousPayYm = "";
                        List<StringBuffer> benChkfileStringList = new ArrayList<StringBuffer>();
                        StringBuffer famChkfileString = new StringBuffer("");
                        for (SurvivorReviewRpt01ChkfileDataCase benChkfileData : benChkDataDescList) {
                            // 在符合註記說明前, 先隨便塞空白行測試是否需換頁
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
                            addColumn(table, 56, 1, StringUtils.defaultString(benChkfileData.getChkCode()) + " " + StringUtils.defaultString(benChkfileData.getChkCodePost()) + " " + StringUtils.defaultString(benChkfileData.getChkResult()), fontCh12, 0, LEFT);
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
                    addEmptyRow(table, 1);
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
                if (caseData.getChkSurvivorDescDataList() != null) {
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 58, 1, "編審註記/說明：" + " ", fontCh12, 0, LEFT);

                    List<SurvivorReviewRpt01ChkfileDataCase> chkDescDataList = caseData.getChkSurvivorDescDataList();
                    if (chkDescDataList.size() > 0) {
                        // 取得事故者編審註記資料
                        String previousPayYm = "";
                        List<StringBuffer> chkfileStringList = new ArrayList<StringBuffer>();
                        StringBuffer chkfileString = new StringBuffer("");
                        for (SurvivorReviewRpt01ChkfileDataCase chkfileData : chkDescDataList) {
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
                            addColumn(table, 56, 1, StringUtils.defaultString(chkfileData.getChkCode()) + " " + StringUtils.defaultString(chkfileData.getChkCodePost()) + " " + StringUtils.defaultString(chkfileData.getChkResult()), fontCh12, 0, LEFT);
                            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                        }

                    }

                }

                // 塞入一行空白行
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                    addEmptyRow(table, 1);
                }

                List<SurvivorReviewRpt01BenDataCase> extList = caseData.getExtList();

                // addEmptyRow(table, 1);
                //             
                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // // 換了頁就不再塞空白行了
                // deleteRow(table, 1);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }

                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 10, 1, "繼承人資料： ", fontCh12l, 0, JUSTIFIEDALL); // 有底線
                addColumn(table, 46, 1, "申請人數：" + caseData.getExtList().size(), fontCh12, 0, LEFT);
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                // 空白行 (每個受款人間留一行空白)
                // addEmptyRow(table, 1);
                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // // 換了頁就不再塞空白行了
                // deleteRow(table, 1);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }
                // else {
                // deleteRow(table, 1);
                // addEmptyRow(table, 1);
                // }

                for (int nExtList = 0; nExtList < extList.size(); nExtList++) {
                    SurvivorReviewRpt01BenDataCase extData = extList.get(nExtList);

                    // 空白行 (每個受款人間留一行空白)
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不再塞空白行了
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                    }

                    // 為求每位受款人的資料能在同一頁, 故先計算每位受款人之資料行數
                    // 並預先塞入空白行, 測試是否需換頁
                    // 行數 = 固定行數 5 行 + 法定代理人 1 行 (若有值) + 給付資料 n 行 + 給付資料標題 1 行 (若給付資料 > 0 筆)
                    List<SurvivorReviewRpt01BenPayDataCase> benPayList = extData.getBenPayList();

                    int nBenPayLine = 5 + benPayList.size();

                    // 20101124 kiyomi - mark start
                    // 給付資料標題 1 行 (若給付資料 > 0 筆)
                    // if (benPayList.size() > 0)
                    // nBenPayLine++;

                    // 法定代理人 1 行 (若有值)
                    // if (StringUtils.isNotBlank(extData.getGrdName()) || StringUtils.isNotBlank(extData.getGrdIdnNo()) || StringUtils.isNotBlank(extData.getGrdBrDate()))
                    // nBenPayLine++;

                    // 法定代理人多一行變 2 行
                    // if (StringUtils.equalsIgnoreCase(extData.getBenEvtRel(), "A") || StringUtils.equalsIgnoreCase(extData.getBenEvtRel(), "F") || StringUtils.equalsIgnoreCase(extData.getBenEvtRel(), "Z")
                    // || StringUtils.equalsIgnoreCase(extData.getBenEvtRel(), "N"))
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
                    addColumn(table, 18, 1, "繼承人序：" + StringUtils.leftPad(String.valueOf(nExtList + 1), 2, "0"), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "姓名：" + StringUtils.defaultString(extData.getBenName()), fontCh12, 0, LEFT);
                    addColumn(table, 18, 1, "申請日期：" + StringUtils.defaultString(extData.getAppDateString()), fontCh12, 0, LEFT);
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
                    addColumn(table, 18, 1, "身分證號：" + StringUtils.defaultString(extData.getBenIdnNo()), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "出生日期：" + StringUtils.defaultString(extData.getBenBrDateString()), fontCh12, 0, LEFT);
                    addColumn(table, 18, 1, "繼承自：" + StringUtils.defaultString(extData.getRefBenName()), fontCh12, 0, LEFT);
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
                    addColumn(table, 18, 1, "國籍別：" + StringUtils.defaultString(extData.getBenNationTyp()), fontCh12, 0, LEFT);
                    addColumn(table, 20, 1, "國籍：" + StringUtils.defaultString(extData.getBenNationCode()), fontCh12, 0, LEFT);
                    addColumn(table, 18, 1, "性別：" + StringUtils.defaultString(extData.getBenSexString()), fontCh12, 0, LEFT);
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
                    addColumn(table, 18, 1, "關係：" + StringUtils.defaultString(extData.getBenEvtRel()), fontCh12, 0, LEFT);
                    /*
                     * //20101208 kiyomi addColumn(table, 18, 1, "電話：" + StringUtils.defaultString(extData.getTel1()) + ((StringUtils.isNotBlank(extData.getTel2()) && StringUtils.isNotBlank(extData.getTel1())) ? ("、" + StringUtils.defaultString(extData.getTel2())) : StringUtils.defaultString(extData.getTel2())), fontCh12, 0, LEFT);
                     */
                    addColumn(table, 20, 1, "電話1：" + StringUtils.defaultString(extData.getTel1()), fontCh12, 0, LEFT);
                    addColumn(table, 18, 1, "電話2：" + StringUtils.defaultString(extData.getTel2()), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 帳號處理
                    // 20101124 kiyomi - start
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不再塞空白行了
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                    }
                    // 20101124 kiyomi - end
                    String strExtAccountNo = StringUtils.defaultString(extData.getPayBankId());
                    if (StringUtils.isNotBlank(extData.getBranchId()))
                        if(extData.getPayTyp().equals("1") && !extData.getPayBankId().equals("700")){
                        	strExtAccountNo = strExtAccountNo + "-" + "0000";
        	            }else{
        	            	strExtAccountNo = strExtAccountNo + "-" + extData.getBranchId();
        	            }
                    if (StringUtils.isNotBlank(extData.getPayEeacc()))
                        strExtAccountNo = strExtAccountNo + "-" + extData.getPayEeacc();
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 18, 1, "給付方式：" + StringUtils.defaultString(extData.getPayTyp()), fontCh12, 0, LEFT);
                    addColumn(table, 25, 1, "帳號：" + strExtAccountNo + " " + StringUtils.defaultString(extData.getBankName()), fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "戶名：" + StringUtils.defaultString(extData.getAccName()), fontCh12, 0, LEFT);
                    if(StringUtils.isBlank(extData.getSpecialAcc())){
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
                    addColumn(table, 56, 1, "地址：" + StringUtils.defaultString(extData.getCommZip()) + "_" + StringUtils.defaultString(extData.getCommAddr()), fontCh12, 0, LEFT);
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
                    addColumn(table, 18, 1, "婚姻狀況：" + StringUtils.defaultString(extData.getBenMarrMk()), fontCh12, 0, LEFT);
                    addColumn(table, 38, 1, "計息存儲：" + StringUtils.defaultString(extData.getSavingMk()), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    if (StringUtils.isNotBlank(extData.getGrdName())) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 56, 1, "法定代理人姓名：" + StringUtils.defaultString(extData.getGrdName()), fontCh12, 0, LEFT);
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    }

                    // ---
                    if (StringUtils.isNotBlank(extData.getGrdIdnNo()) || StringUtils.isNotBlank(extData.getGrdBrDate())) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 22, 1, "法定代理人出生日期：" + StringUtils.defaultString(extData.getGrdBrDateString()), fontCh12, 0, LEFT);
                        addColumn(table, 34, 1, "法定代理人身分證號：" + StringUtils.defaultString(extData.getGrdIdnNo()), fontCh12, 0, LEFT);
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
                    addColumn(table, 56, 1, "代辦人姓名：" + StringUtils.defaultString(extData.getAssignName()), fontCh12, 0, LEFT);
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
                    addColumn(table, 22, 1, "代辦人出生日期：" + StringUtils.defaultString(extData.getAssignBrDateString()), fontCh12, 0, LEFT);
                    addColumn(table, 34, 1, "代辦人身分證號（統一編號）：" + StringUtils.defaultString(extData.getAssignIdnNo()), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                    // ---
                    // 20101124 kiyomi - mark start
                    // addEmptyRow(table, 1);
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
                        addEmptyRow(table, 1);
                    }
                    // 20101124 kiyomi - end

                    if (benPayList.size() > 0) {
                        // 20101124 kiyomi - mark start
                        // 塞受款人資料的資料前, 先隨便塞空白行測試是否需換頁
                        // addEmptyRow(table, benPayList.size());

                        // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不再塞空白行了
                        // deleteRow(table, benPayList.size());
                        // document.add(table);
                        // table = addHeader(caseData, false, earlyWarning);
                        // }
                        // else {
                        // deleteRow(table, benPayList.size());
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
                        addColumn(table, 8, 1, "給付年月", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "可領金額", fontCh12, 0, RIGHT);
                        addColumn(table, 8, 1, "扣減金額", fontCh12, 0, RIGHT);
                        addColumn(table, 8, 1, "呆帳金額", fontCh12, 0, RIGHT);
                        addColumn(table, 8, 1, "匯款匯費", fontCh12, 0, RIGHT);
                        addColumn(table, 8, 1, "實付金額", fontCh12, 0, RIGHT);
                        addColumn(table, 10, 1, " ", fontCh12, 0, LEFT);

                    }
                    // ---

                    for (SurvivorReviewRpt01BenPayDataCase benPayData : benPayList) {
                        // 20101124 kiyomi - start
                        addEmptyRow(table, 1);

                        if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        addColumn(table, 8, 1, benPayData.getPayYmString(), fontCh12, 0, LEFT); // 給付年月

                        if (benPayData.getIssueAmt() != null && benPayData.getIssueAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getIssueAmt()), fontCh12, 0, RIGHT); // 可領金額
                        else
                            addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 可領金額

                        if (benPayData.getOtherAmt() != null && benPayData.getOtherAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getOtherAmt()), fontCh12, 0, RIGHT); // 扣減金額
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, RIGHT); // 扣減金額

                        if (benPayData.getBadDebtAmt() != null && benPayData.getBadDebtAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getBadDebtAmt()), fontCh12, 0, RIGHT); // 呆帳金額
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, RIGHT); // 呆帳金額

                        if (benPayData.getPayRate() != null && benPayData.getPayRate().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getPayRate()), fontCh12, 0, RIGHT); // 匯款匯費
                        else
                            addColumn(table, 8, 1, " ", fontCh12, 0, RIGHT); // 匯款匯費

                        if (benPayData.getAplpayAmt() != null && benPayData.getAplpayAmt().compareTo(new BigDecimal(0)) != 0)
                            addColumn(table, 8, 1, formatBigDecimalToInteger(benPayData.getAplpayAmt()), fontCh12, 0, RIGHT); // 實付金額
                        else
                            addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 實付金額
                        addColumn(table, 10, 1, " ", fontCh12, 0, LEFT);

                    }

                }

                // // 在塞分隔線前, 先隨便塞空白行測試是否需換頁
                addEmptyRow(table, 1);

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不印分隔線了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                    addEmptyRow(table, 1);
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
                // if (changeList.size() > 0)
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
                if (changeList.size() > 0) {

                    // 20101124 kiyomi - start
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                    addColumn(table, 6, 1, "投保薪資", fontCh12, 0, LEFT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, "逕調", fontCh12, 0, LEFT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, "部門", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "生效日期", fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, "退保日期", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, changeData.getSidMk(), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, changeData.getTxcdType2(), fontCh12, 0, LEFT);
                    addColumn(table, 10, 1, changeData.getUno(), fontCh12, 0, LEFT);
                    addColumn(table, 5, 1, changeData.getTxcd(), fontCh12, 0, LEFT);
                    addColumn(table, 2, 1, changeData.getTxcdTypeA(), fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, formatBigDecimalToInteger(changeData.getWage()), fontCh12, 0, RIGHT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 3, 1, changeData.getTxcdTypeB(), fontCh12, 0, LEFT);
                    addColumn(table, 1, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 7, 1, changeData.getDept(), fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, changeData.getEfDteString(), fontCh12, 0, LEFT);
                    addColumn(table, 9, 1, changeData.getExitDteString(), fontCh12, 0, LEFT); // 退保日期
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                }
                // ]
                // 承保異動資料

                addEmptyRow(table, 1);
                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    deleteRow(table, 1);
                    document.add(table);
                    table = addHeader(caseData, false, earlyWarning);
                }
                else {
                    deleteRow(table, 1);
                    addEmptyRow(table, 1);
                }

                // 最後單位
                SurvivorReviewRpt01UnitCase applyUnitData = caseData.getApplyUnitData();
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
                    // addColumn(table, 9, 1, "申請單位", fontCh12, 0, LEFT); // 申請單位
                    addColumn(table, 9, 1, formatBigDecimalToInteger(applyUnitData.getPrsnoB()), fontCh12, 0, LEFT); // 單位人數
                    addColumn(table, 9, 1, applyUnitData.getUbNo(), fontCh12, 0, LEFT); // 保險證號
                    addColumn(table, 24, 1, applyUnitData.getUname(), fontCh12, 0, LEFT); // 投保單位名稱
                    addColumn(table, 14, 1, applyUnitData.getEname(), fontCh12, 0, LEFT); // 負責人
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                }

                // 塞入一行空白行
                List<SurvivorReviewRpt01CipgDataCase> cipgDataList = caseData.getCipgData();
                int nCipgDataListLine = 3;
                // 20101124 kiyomi - mark start
                // if(cipgDataList.size()>0&&cipgDataList.size() % 5 >0)
                // nCipgDataListLine=(cipgDataList.size()/5)+1;
                // else if(cipgDataList.size()>0&&cipgDataList.size() % 5 ==0)
                // nCipgDataListLine=(cipgDataList.size()/5);

                // addEmptyRow(table, nCipgDataListLine);

                // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                // 換了頁就不再塞空白行了
                // deleteRow(table, nCipgDataListLine);
                // document.add(table);
                // table = addHeader(caseData, false, earlyWarning);
                // }else {
                // deleteRow(table, nCipgDataListLine);
                // }
                // 20101124 kiyomi - mark end

                // 最高60個月平均薪資明細
                // [

                // 20101124 kiyomi - start
                addEmptyRow(table, 1);

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
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                addColumn(table, 38, 1, "※最高"+caseData.getAppMonth()+"個月平均薪資明細：", fontCh12, 0, LEFT); // 申請單位
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
                    addColumn(table, 5, 1, "薪 資 ", fontCh12, 0, LEFT); // 薪資

                }
                addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                if (caseData.getCipgData() != null) {

                    // 20101124 kiyomi - start
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
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
                        SurvivorReviewRpt01CipgDataCase cipgDataCase = cipgDataList.get(avgWgRow);
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
                    if (cipgDataList.size() % 5 != 0) {
                        addColumn(table, (5 - (cipgDataList.size() % 5)) * 11, 1, " ", fontCh12, 0, LEFT); // 補空白
                        addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
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
                    addEmptyRow(table, 1);
                }

                // ]

                List<SurvivorReviewRpt01PayOldDataCase> payOldList = caseData.getEvtPayOldList();
                if (payOldList.size() > 0) {

                    // 20101124 kiyomi - mark start
                    // 塞受款人資料的資料前, 先隨便塞空白行測試是否需換頁
                    // addEmptyRow(table, payOldList.size());

                    // if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    // 換了頁就不再塞空白行了
                    // deleteRow(table, payOldList.size());
                    // document.add(table);
                    // table = addHeader(caseData, false, earlyWarning);
                    // }
                    // else {
                    // deleteRow(table, payOldList.size());
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
                    addColumn(table, 6, 1, "給付年月", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, "基本金額/", fontCh12, 0, RIGHT); // 基本月給付金額
                    addColumn(table, 5, 1, "計算式", fontCh12, 0, RIGHT);
                    addColumn(table, 9, 1, "勞保給付金額", fontCh12, 0, RIGHT);
                    addColumn(table, 7, 1, "符合人數", fontCh12, 0, RIGHT); // 遺屬符合人數
                    addColumn(table, 1, 1, "/", fontCh12, 0, RIGHT);
                    addColumn(table, 8, 1, "加計比例", fontCh12, 0, RIGHT);
                    addColumn(table, 8, 1, "當月扣除失能", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "核定金額", fontCh12, 0, RIGHT);
                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);

                }
                // ---

                for (SurvivorReviewRpt01PayOldDataCase payOldData : payOldList) {
                    // 20101124 kiyomi - start
                    addEmptyRow(table, 1);

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        // 換了頁就不再塞空白行了
                        deleteRow(table, 1);
                        document.add(table);
                        table = addHeader(caseData, false, earlyWarning);
                    }
                    else {
                        deleteRow(table, 1);
                    }
                    // 20101124 kiyomi - end

                    BigDecimal oldbAmt = (payOldData.getOldbAmt() == null) ? new BigDecimal(0) : payOldData.getOldbAmt();
                    BigDecimal oldRate = (payOldData.getOldRate() == null) ? new BigDecimal(0) : payOldData.getOldRate();
                    Double recomAmt = (oldbAmt.intValue() * oldRate.intValue() * 0.01);

                    addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
                    addColumn(table, 6, 1, payOldData.getPayYmString(), fontCh12, 0, LEFT); // 給付年月

                    if (payOldData.getOldaAmt() != null && payOldData.getOldaAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 6, 1, formatBigDecimalToInteger(payOldData.getOldaAmt()) + (payOldData.getOldab() != null ? "/" : ""), fontCh12, 0, RIGHT); // 基本月給付金額
                    else
                        addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 基本月給付金額

                    if (payOldData.getOldaAmt() != null && payOldData.getOldaAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 5, 1, formatBigDecimalToInteger(payOldData.getOldab()), fontCh12, 0, RIGHT); // 計算式
                    else
                        addColumn(table, 5, 1, " ", fontCh12, 0, RIGHT); // 計算式

                    if (payOldData.getOldbAmt() != null && payOldData.getOldbAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 9, 1, formatBigDecimalToInteger(payOldData.getOldbAmt()), fontCh12, 0, RIGHT); // 勞保給付金額
                    else
                        addColumn(table, 9, 1, "0", fontCh12, 0, RIGHT); // 勞保給付金額

                    if (payOldData.getQualCount() != null && payOldData.getQualCount().compareTo(new BigDecimal(0)) != 0) {
                        addColumn(table, 7, 1, formatBigDecimalToInteger(payOldData.getQualCount()), fontCh12, 0, RIGHT); // 遺屬符合人數
                        addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);
                    }
                    else {
                        addColumn(table, 7, 1, "0", fontCh12, 0, RIGHT); // 遺屬符合人數
                        addColumn(table, 1, 1, " ", fontCh12, 0, RIGHT);
                    }

                    if (payOldData.getOldRate() != null && payOldData.getOldRate().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 8, 1, formatBigDecimalToInteger(payOldData.getOldRate()) + "%/" + formatNumber(Math.round(recomAmt)), fontCh12, 0, RIGHT); // 加計比例
                    else
                        addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 加計比例

                    if (payOldData.getCompenAmt() != null && payOldData.getCompenAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 8, 1, formatBigDecimalToInteger(payOldData.getCompenAmt()), fontCh12, 0, RIGHT); // 當月扣除失能
                    else
                        addColumn(table, 8, 1, "0", fontCh12, 0, RIGHT); // 當月扣除失能

                    if (payOldData.getOldbAmt() != null && payOldData.getOldbAmt().compareTo(new BigDecimal(0)) != 0)
                        addColumn(table, 6, 1, formatBigDecimalToInteger(payOldData.getIssueAmt()), fontCh12, 0, RIGHT); // 核定金額
                    else
                        addColumn(table, 6, 1, "0", fontCh12, 0, RIGHT); // 核定金額

                    addColumn(table, 2, 1, " ", fontCh12, 0, RIGHT);
                }

                document.add(table);
                
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
