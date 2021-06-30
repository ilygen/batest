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
import com.lowagie.text.Table;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt17Case;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.StringUtility;

/**
 * 退回現金清冊
 * 
 * @author Goston
 */
public class MonthlyRptS17Report extends ReportBase {
    private String printDate = ""; // 印表日期

    public MonthlyRptS17Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(false);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4.rotate(), 15, 15, 15, 15);
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
    public Table addHeader(MonthlyRpt17Case caseData, String payCode) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.setPadding(2.7f);

        addColumn(table, 60, 1, "勞保年金退回現金至" + StringUtils.trimToEmpty(caseData.getDataKd()) + "清冊", fontCh16, 0, CENTER);
        // ---
        addColumn(table, 38, 1, "　", fontCh12, 0, LEFT);
        addColumn(table, 7, 1, "頁次：" + writer.getCurrentPageNumber(), fontCh12, 0, LEFT);
        addColumn(table, 15, 1, "印表日期：" + DateUtility.formatChineseDateString(caseData.getcPrnDateStr(), false), fontCh12, 0, LEFT);
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

        // addColumn(table, 25, 1, "給付別：" + caseData.getPayCodeString(), fontCh12, 0, LEFT);
        addColumn(table, 25, 1, "給付別：" + payCodeStr, fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "類別：" + caseData.getIssuTypString(), fontCh12, 0, LEFT);
        addColumn(table, 15, 1, "給付方式：" + caseData.getPayTypString(), fontCh12, 0, LEFT);

        // 表格標題
        addColumn(table, 3, 1, "序\r\n號", fontCh12, 1, CENTER);
        addColumn(table, 8, 1, "年金受\r\n理編號", fontCh12, 1, CENTER);
        addColumn(table, 4, 1, "年金給\r\n付年月", fontCh12, 1, CENTER);
        addColumn(table, 4, 1, "憑證\r\n日期", fontCh12, 1, CENTER);
        addColumnAssignVAlignment(table, 6, 1, "受款人姓名", fontCh12, 1, CENTER, MIDDLE);
        addColumnAssignVAlignment(table, 5, 1, "金額", fontCh12, 1, CENTER, MIDDLE);
        addColumn(table, 3, 1, "本局\r\n帳戶", fontCh12, 1, CENTER);
        addColumn(table, 2, 1, "種\r\n類", fontCh12, 1, CENTER);
        addColumnAssignVAlignment(table, 5, 1, "身分證號", fontCh12, 1, CENTER, MIDDLE);
        addColumnAssignVAlignment(table, 6, 1, "聯絡電話", fontCh12, 1, CENTER, MIDDLE);
        addColumnAssignVAlignment(table, 8, 1, "受理編號", fontCh12, 1, CENTER, MIDDLE);
        addColumn(table, 3, 1, "受款\r\n人序", fontCh12, 1, CENTER);
        addColumn(table, 3, 1, "移至\r\n註記", fontCh12, 1, CENTER);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt17Case> caseList, HashMap<String, Object> map) throws Exception {
        try {
            String payCode = (String) map.get("payCode");

            // 2009.6.8 EvelynHsu add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt17_" + Encode.forJava(printDate) + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt17_" + printDate + ".pdf");
            // writer = PdfWriter.getInstance(document, bao);

            document.open();

            BigDecimal pageAmountAmt = new BigDecimal(0); // 本頁小計 - 金額
            BigDecimal allAmountAmt = new BigDecimal(0); // 總計 - 金額

            int recNo = 0; // 用來暫存 編號

            Table table = null;

            for (int i = 0; i < caseList.size(); i++) { // ... [
                MonthlyRpt17Case caseData = caseList.get(i);

                // 目前這筆資料的 資料類別 + 案件類別 + 給付方式 (用於分頁)
                String currentPageCondition = StringUtils.trimToEmpty(caseData.getDataKd()) + caseData.getIssuTypString() + caseData.getPayTypString();

                // 下一筆資料的 資料類別 + 案件類別 + 給付方式 (用於分頁)
                String nextPageCondition = "";
                if (i != caseList.size() - 1) {
                    nextPageCondition = StringUtils.trimToEmpty(caseList.get(i + 1).getDataKd()) + caseList.get(i + 1).getIssuTypString() + caseList.get(i + 1).getPayTypString();
                }

                // 第一筆加入表頭
                if (i == 0) {
                    table = addHeader(caseData, payCode);
                }

                // 計算金額
                if (caseData.getIssueAmt() != null) {
                    pageAmountAmt = pageAmountAmt.add(caseData.getIssueAmt()); // 本頁小計 - 金額
                    allAmountAmt = allAmountAmt.add(caseData.getIssueAmt()); // 總計 - 金額
                }

                // 列印資料
                // [
                addColumn(table, 3, 1, String.valueOf(recNo + 1), fontCh12, 1, CENTER); // 序號
                addColumn(table, 8, 1, caseData.getApNoString(), fontCh12, 1, LEFT); // 年金受理編號
                addColumn(table, 4, 1, StringUtils.defaultString(caseData.getPayYmString()), fontCh12, 1, CENTER); // 年金給付年月
                addColumn(table, 4, 1, caseData.getPayDateString(), fontCh12, 1, CENTER); // 憑證日期
                addColumn(table, 6, 1, StringUtility.replaceSpaceString(caseData.getBenName()), fontCh12, 1, LEFT); // 受款人姓名
                addColumn(table, 5, 1, formatBigDecimalToInteger(caseData.getIssueAmt()), fontCh12, 1, RIGHT); // 金額
                addColumn(table, 3, 1, StringUtils.defaultString(caseData.getBliPayeeAcc()), fontCh12, 1, CENTER); // 本局帳戶
                addColumn(table, 2, 1, StringUtils.defaultString(caseData.getDataTyp()), fontCh12, 1, CENTER); // 種類
                addColumn(table, 5, 1, StringUtils.trimToEmpty(caseData.getBenIdn()), fontCh12, 1, LEFT); // 身分證號
                addColumn(table, 6, 1, StringUtils.trimToEmpty(caseData.getCommTel()), fontCh12, 1, LEFT); // 聯絡電話
                addColumn(table, 8, 1, caseData.getOtherApNo1String(), fontCh12, 1, LEFT); // 受理編號
                addColumn(table, 3, 1, StringUtils.defaultString(caseData.getOtherSeqNo1()), fontCh12, 1, CENTER); // 受款人序
                addColumn(table, 3, 1, StringUtils.defaultString(caseData.getHjMk()), fontCh12, 1, CENTER); // 移至註記
                // ]

                // 最後一筆或是達到換頁條件, 則要塞滿空白行再換頁
                if (i == caseList.size() - 1 || !StringUtils.equals(currentPageCondition, nextPageCondition)) {
                    // 塞滿空白行, 以讓表格美觀
                    // [
                    while (writer.fitsPage(table)) {
                        addColumn(table, 3, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 8, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 4, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 4, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 6, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 5, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 3, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 2, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 5, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 6, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 8, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 3, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 3, 1, "　", fontCh12, 1, CENTER);
                    }
                    // ]

                    // 加入表尾
                    deleteRow(table, 4); // 表尾有 3 行 + 前面回圈會多塞 1 筆, 故要刪 4 行

                    addColumn(table, 25, 1, "本　　　　　　頁　　　　　　小　　　　　　計", fontCh12, 1, CENTER);
                    addColumn(table, 5, 1, formatBigDecimalToInteger(pageAmountAmt), fontCh12, 1, RIGHT);
                    addColumn(table, 30, 1, "　", fontCh12, 0, CENTER);
                    // ---
                    addColumn(table, 25, 1, "總　　　　　　　　　　　　　　　　　　　　計", fontCh12, 1, CENTER);
                    addColumn(table, 5, 1, formatBigDecimalToInteger(allAmountAmt), fontCh12, 1, RIGHT);
                    addColumn(table, 30, 1, "　", fontCh12, 0, CENTER);
                    // ---
                    addColumn(table, 60, 1, "註：種類１現金、２醫療", fontCh12, 0, LEFT);

                    // 歸零
                    recNo = 0; // 編號
                    pageAmountAmt = new BigDecimal(0); // 本頁小計 - 金額
                    allAmountAmt = new BigDecimal(0); // 總計 - 金額

                    document.add(table);

                    if (i != caseList.size() - 1)
                        table = addHeader(caseList.get(i + 1), payCode); // 下一筆
                }
                else {
                    // 測試塞下一筆 + 表尾時是否需要換頁
                    addColumn(table, 3, 1, String.valueOf(recNo + 2), fontCh12, 1, CENTER); // 序號
                    addColumn(table, 8, 1, caseList.get(i + 1).getApNoString(), fontCh12, 1, LEFT); // 年金受理編號
                    addColumn(table, 4, 1, StringUtils.defaultString(caseList.get(i + 1).getPayYmString()), fontCh12, 1, CENTER); // 年金給付年月
                    addColumn(table, 4, 1, caseList.get(i + 1).getPayDateString(), fontCh12, 1, CENTER); // 憑證日期
                    addColumn(table, 6, 1, StringUtility.replaceSpaceString(caseList.get(i + 1).getBenName()), fontCh12, 1, LEFT); // 受款人姓名
                    addColumn(table, 5, 1, formatBigDecimalToInteger(caseList.get(i + 1).getIssueAmt()), fontCh12, 1, RIGHT); // 金額
                    addColumn(table, 3, 1, StringUtils.defaultString(caseList.get(i + 1).getBliPayeeAcc()), fontCh12, 1, CENTER); // 本局帳戶
                    addColumn(table, 2, 1, StringUtils.defaultString(caseList.get(i + 1).getDataTyp()), fontCh12, 1, CENTER); // 種類
                    addColumn(table, 5, 1, StringUtils.trimToEmpty(caseList.get(i + 1).getBenIdn()), fontCh12, 1, LEFT); // 身分證號
                    addColumn(table, 6, 1, StringUtils.trimToEmpty(caseList.get(i + 1).getCommTel()), fontCh12, 1, LEFT); // 聯絡電話
                    addColumn(table, 8, 1, caseList.get(i + 1).getOtherApNo1String(), fontCh12, 1, LEFT); // 受理編號
                    addColumn(table, 3, 1, StringUtils.defaultString(caseList.get(i + 1).getOtherSeqNo1()), fontCh12, 1, CENTER); // 受款人序
                    addColumn(table, 3, 1, StringUtils.defaultString(caseList.get(i + 1).getHjMk()), fontCh12, 1, CENTER); // 移至註記
                    // ---
                    addEmptyRow(table, 3); // 表尾 3 行

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);

                        // 加入表尾
                        addColumn(table, 25, 1, "本　　　　　　頁　　　　　　小　　　　　　計", fontCh12, 1, CENTER);
                        addColumn(table, 5, 1, formatBigDecimalToInteger(pageAmountAmt), fontCh12, 1, RIGHT);
                        addColumn(table, 30, 1, "　", fontCh12, 0, CENTER);
                        // ---
                        addColumn(table, 25, 1, "總　　　　　　　　　　　　　　　　　　　　計", fontCh12, 1, CENTER);
                        addColumn(table, 5, 1, formatBigDecimalToInteger(allAmountAmt), fontCh12, 1, RIGHT);
                        addColumn(table, 30, 1, "　", fontCh12, 0, CENTER);
                        // ---
                        addColumn(table, 60, 1, "註：種類１現金、２醫療", fontCh12, 0, LEFT);

                        // 編號 + 1
                        recNo++;

                        // 歸零
                        pageAmountAmt = new BigDecimal(0); // 本頁小計 - 金額

                        document.add(table);

                        table = addHeader(caseList.get(i + 1), payCode); // 下一筆
                    }
                    else {
                        // 編號 + 1
                        recNo++;

                        deleteRow(table, 4);
                    }
                }
            } // ] ... for (int i = 0; i < caseList.size(); i++) {

            document.close();
            // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
            printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRptS17_" + Encode.forJava(printDate) + ".pdf"), bao);
        }
        finally {
            document.close();
        }

        return bao;
    }
}
