package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.owasp.encoder.Encode;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt18Case;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;

/**
 * 退回現金轉暫收及待結轉清單
 * 
 * @author Goston
 */
public class MonthlyRpt18Report extends ReportBase {
    private String printDate = ""; // 印表日期

    public MonthlyRpt18Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(false);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 25, 25, 25, 25);
        return document;
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
     * @return
     * @throws Exception
     */
    public Table addHeader(MonthlyRpt18Case caseData, String payCode) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.setPadding(3.5f);

        addColumn(table, 60, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(caseData.getPayCode(), DateUtility.getNowWestDate()) + "－勞保", fontCh14, 0, CENTER);
        // addColumn(table, 60, 1, "勞工保險局給付處－勞保", fontCh14, 0, CENTER);
        // ---
        addColumn(table, 60, 1, "退回現金轉暫收及待結轉清單", fontCh14, 0, CENTER);
        //
        addColumn(table, 60, 1, "中華民國 " + caseData.getPayDateChineseString(), fontCh14, 0, CENTER);
        // ---
        addColumn(table, 20, 1, "職醫", fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "頁次：" + writer.getCurrentPageNumber(), fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "印表日期：" + DateUtility.formatChineseDateString(caseData.getcPrnDateStr(), false), fontCh12, 0, LEFT);
        // ---
        String payCodeStr = "";
        String isNaChgMkStr = "";

        if ("Y".equals(caseData.getIsNaChgMk())) {
            if ("12".equals(caseData.getNaChgMk())) {
                isNaChgMkStr = " - 普改職";
            }
            else if ("13".equals(caseData.getNaChgMk())) {
                isNaChgMkStr = " - 普改加職";
            }
            else if ("21".equals(caseData.getNaChgMk())) {
                isNaChgMkStr = " - 職改普";
            }
            else if ("23".equals(caseData.getNaChgMk())) {
                isNaChgMkStr = " - 職改加職";
            }
            else if ("31".equals(caseData.getNaChgMk())) {
                isNaChgMkStr = " - 加職改普";
            }
            else if ("32".equals(caseData.getNaChgMk())) {
                isNaChgMkStr = " - 加職改職";
            }
        }
        else if ("N".equals(caseData.getIsNaChgMk())) {
            isNaChgMkStr = "";
        }

        if (("L").equals(payCode)) {
            payCodeStr = "普通-老年給付(年金)";
        }
        else if (("K").equals(payCode)) {
            // payCodeStr = "○○-失能給付(年金)";
            if (("1").equals(caseData.getNlWkMk())) {
                payCodeStr = "普通-失能給付(年金)" + isNaChgMkStr;
            }
            else if (("2").equals(caseData.getNlWkMk()) && ("1").equals(caseData.getAdWkMk())) {
                payCodeStr = "職災-失能給付(年金)" + isNaChgMkStr;
            }
            else if (("2").equals(caseData.getNlWkMk()) && ("2").equals(caseData.getAdWkMk())) {
                payCodeStr = "加職-失能給付(年金)" + isNaChgMkStr;
            }
            else {
                payCodeStr = "普通-失能給付(年金)";
            }

        }
        else if (("S").equals(payCode)) {
            // payCodeStr = "○○-遺屬給付(年金)";
            if (("1").equals(caseData.getNlWkMk())) {
                payCodeStr = "普通-遺屬給付(年金)";
            }
            else if (("2").equals(caseData.getNlWkMk()) && ("1").equals(caseData.getAdWkMk())) {
                payCodeStr = "職災-遺屬給付(年金)";
            }
            else if (("2").equals(caseData.getNlWkMk()) && ("2").equals(caseData.getAdWkMk())) {
                payCodeStr = "加職-遺屬給付(年金)";
            }
            else {
                payCodeStr = "普通-遺屬給付(年金)";
            }
        }

        addColumn(table, 20, 1, "給付別：" + payCodeStr, fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "類別：" + caseData.getIssuTypString(), fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "給付方式：" + caseData.getPayTypString(), fontCh12, 0, LEFT);

        // 表格標題
        addColumn(table, 5, 1, "序號", fontCh12, 1, CENTER);
        addColumn(table, 16, 1, "受理編號", fontCh12, 1, CENTER);
        addColumn(table, 22, 1, "受款人姓名", fontCh12, 1, CENTER);
        addColumn(table, 10, 1, "金額", fontCh12, 1, CENTER);
        addColumn(table, 7, 1, "退回日期", fontCh12, 1, CENTER);

        return table;
    }

    /**
     * 加入表尾
     * 
     * @param table
     * @param lendAccountingTitleAmt
     * @param loanAccountingTitleAmt
     * @throws Exception
     */
    public void addFooter(Table table, BigDecimal lendAccountingTitleAmt, BigDecimal loanAccountingTitleAmt) throws Exception {
        int nBorderLeftStyle = Rectangle.LEFT;
        int nBorderRightStyle = Rectangle.RIGHT;
        int nLineBorderStyle = Rectangle.LEFT | Rectangle.RIGHT;
        int nEndBorderStyle = Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM;

        addColumn(table, 48, 1, "借：111212  00510  1銀行存款－土銀台北分行１０１６３－６帳戶", fontCh12, 1, nBorderLeftStyle, LEFT);
        addColumn(table, 12, 1, formatBigDecimalToInteger(lendAccountingTitleAmt), fontCh12, 1, nBorderRightStyle, RIGHT);
        // ---
        addColumn(table, 48, 1, "　　貸：236X122 29   A暫收及待結轉帳項－醫─暫收款", fontCh12, 1, nBorderLeftStyle, LEFT);
        addColumn(table, 12, 1, formatBigDecimalToInteger(loanAccountingTitleAmt), fontCh12, 1, nBorderRightStyle, RIGHT);
        // ---
        addColumn(table, 60, 1, "　", fontCh12, 1, nLineBorderStyle, LEFT);
        // ---
        addColumn(table, 60, 1, "　", fontCh12, 1, nLineBorderStyle, LEFT);
        // ---
        addColumn(table, 60, 1, "　", fontCh12, 1, nLineBorderStyle, LEFT);
        // ---
        addColumn(table, 60, 1, "　", fontCh12, 1, nEndBorderStyle, LEFT);
        // ---
        addColumn(table, 30, 1, "經辦", fontCh12, 0, LEFT);
        addColumn(table, 30, 1, "出納", fontCh12, 0, LEFT);
        // ---
        addColumn(table, 30, 1, "人員", fontCh12, 0, LEFT);
        addColumn(table, 30, 1, "科長", fontCh12, 0, LEFT);
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt18Case> caseList, HashMap<String, Object> map) throws Exception {
        try {
            String payCode = (String) map.get("payCode");

            // 2009.6.8 EvelynHsu add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt18_" + Encode.forJava(printDate) + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt18_" + printDate + ".pdf");
            // writer = PdfWriter.getInstance(document, bao);

            document.open();

            BigDecimal lendAccountingTitleAmt = new BigDecimal(0); // 會計科目: 借：110212 0051 4銀行存款－土銀台北分行１０１６３－６帳戶
            BigDecimal loanAccountingTitleAmt = new BigDecimal(0); // 會計科目: 貸：2825122 29 4暫收及待結轉帳項－醫─暫收款

            int recNo = 0; // 用來暫存 編號

            Table table = null;

            for (int i = 0; i < caseList.size(); i++) { // ... [
                MonthlyRpt18Case caseData = caseList.get(i);

                // 目前這筆資料的 類別 + 給付方式 (用於分頁)
                String currentPageCondition = caseData.getIssuTypString() + caseData.getPayTypString();

                // 下一筆資料的 類別 + 給付方式 (用於分頁)
                String nextPageCondition = "";
                if (i != caseList.size() - 1) {
                    nextPageCondition = caseList.get(i + 1).getIssuTypString() + caseList.get(i + 1).getPayTypString();
                }

                // 第一筆加入表頭
                if (i == 0) {
                    table = addHeader(caseData, payCode);
                }

                // 計算會計科目金額
                if (caseData.getIssueAmt() != null) {
                    lendAccountingTitleAmt = lendAccountingTitleAmt.add(caseData.getIssueAmt()); // 會計科目: 借：110212 0051 4銀行存款－土銀台北分行１０１６３－６帳戶
                    loanAccountingTitleAmt = loanAccountingTitleAmt.add(caseData.getIssueAmt()); // 會計科目: 貸：2825122 29 4暫收及待結轉帳項－醫─暫收款
                }

                // 列印資料
                // [
                addColumn(table, 5, 1, String.valueOf(recNo + 1), fontCh12, 1, CENTER); // 序號
                addColumn(table, 16, 1, caseData.getOtherApNo1String(), fontCh12, 1, LEFT); // 受理編號
                addColumn(table, 22, 1, StringUtility.replaceSpaceString(caseData.getBenName()), fontCh12, 1, LEFT); // 受款人姓名
                addColumn(table, 10, 1, formatBigDecimalToInteger(caseData.getIssueAmt()), fontCh12, 1, RIGHT); // 金額
                addColumn(table, 7, 1, caseData.getPayDateString(), fontCh12, 1, CENTER); // 退回日期
                // ]

                // 最後一筆或是達到換頁條件, 則要塞滿空白行再換頁
                if (i == caseList.size() - 1 || !StringUtils.equals(currentPageCondition, nextPageCondition)) {
                    // 塞滿空白行, 以讓表格美觀
                    // [
                    while (writer.fitsPage(table)) {
                        addColumn(table, 5, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 16, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 22, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 10, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 7, 1, "　", fontCh12, 1, CENTER);
                    }
                    // ]

                    // 加入表尾
                    deleteRow(table, 9); // 表尾有 8 行 + 前面回圈會多塞 1 筆, 故要刪 9 行

                    addFooter(table, lendAccountingTitleAmt, loanAccountingTitleAmt);

                    // 歸零
                    recNo = 0; // 編號
                    lendAccountingTitleAmt = new BigDecimal(0); // 會計科目: 借：110212 0051 4銀行存款－土銀台北分行１０１６３－６帳戶
                    loanAccountingTitleAmt = new BigDecimal(0); // 會計科目: 貸：2825122 29 4暫收及待結轉帳項－醫─暫收款

                    document.add(table);

                    if (i != caseList.size() - 1)
                        table = addHeader(caseList.get(i + 1), payCode); // 下一筆
                }
                else {
                    // 測試塞下一筆 + 表尾時是否需要換頁
                    addColumn(table, 5, 1, String.valueOf(recNo + 2), fontCh12, 1, CENTER); // 序號
                    addColumn(table, 16, 1, caseList.get(i + 1).getOtherApNo1String(), fontCh12, 1, LEFT); // 受理編號
                    addColumn(table, 22, 1, StringUtility.replaceSpaceString(caseList.get(i + 1).getBenName()), fontCh12, 1, LEFT); // 受款人姓名
                    addColumn(table, 10, 1, formatBigDecimalToInteger(caseList.get(i + 1).getIssueAmt()), fontCh12, 1, RIGHT); // 金額
                    addColumn(table, 7, 1, caseList.get(i + 1).getPayDateString(), fontCh12, 1, CENTER); // 退回日期

                    addFooter(table, lendAccountingTitleAmt, loanAccountingTitleAmt); // 表尾

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 9);

                        // 加入表尾
                        addFooter(table, lendAccountingTitleAmt, loanAccountingTitleAmt);

                        // 編號 + 1
                        recNo++;

                        // 歸零
                        lendAccountingTitleAmt = new BigDecimal(0); // 會計科目: 借：110212 0051 4銀行存款－土銀台北分行１０１６３－６帳戶
                        loanAccountingTitleAmt = new BigDecimal(0); // 會計科目: 貸：2825122 29 4暫收及待結轉帳項－醫─暫收款

                        document.add(table);

                        table = addHeader(caseList.get(i + 1), payCode); // 下一筆
                    }
                    else {
                        // 編號 + 1
                        recNo++;

                        deleteRow(table, 9);
                    }
                }
            } // ] ... for (int i = 0; i < caseList.size(); i++) {

            document.close();

            // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
            printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt18_" + Encode.forJava(printDate) + ".pdf"), bao);
        }
        finally {
            document.close();
        }

        return bao;
    }
}
