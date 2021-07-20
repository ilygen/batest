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
import tw.gov.bli.ba.rpt.cases.BatchPaymentReceiveDataCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;

/**
 * 整批收回失敗清單
 * 
 * @author Kiyomi
 */
public class BatchPaymentReceiveFailureReport extends ReportBase {
    private String printDate = ""; // 印表日期

    public BatchPaymentReceiveFailureReport() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(true);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A3.rotate(), 30, 30, 30, 30);
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
    public Table addHeader(BatchPaymentReceiveDataCase caseData, HashMap<String, Object> map) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.setPadding(3);

        addColumn(table, 20, 1, "報表編號：BALP0D690L", fontCh16, 0, LEFT);
        addColumn(table, 25, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle((String) map.get("payCode"), DateUtility.getNowWestDate()), fontCh22, 0, CENTER);
        // addColumn(table, 25, 1, "勞工保險局", fontCh18, 0, CENTER);
        addColumn(table, 15, 1, "　", fontCh12, 0, LEFT);
        // ---

        addColumn(table, 20, 1, "印表日期：" + DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(), false), fontCh16, 0, LEFT);
        addColumn(table, 25, 1, "整批收回失敗清單", fontCh22, 0, CENTER);
        addColumn(table, 15, 1, "印表頁次：第 " + writer.getCurrentPageNumber() + " 頁", fontCh16, 0, RIGHT);
        // ---

        addColumn(table, 60, 1, "核收日期：" + DateUtility.formatChineseDateString(StringUtils.defaultString((String) map.get("chkDate")), false), fontCh16, 0, LEFT);

        addEmptyRow(table, 1);

        // 表格標題
        addColumn(table, 6, 1, "序號", fontCh18, 0, CENTER);
        addColumn(table, 8, 1, "受理編號", fontCh18, 0, LEFT);
        addColumn(table, 5, 1, "分割序號", fontCh18, 0, LEFT);
        addColumn(table, 8, 1, "給付金額", fontCh18, 0, RIGHT);
        addColumn(table, 33, 1, "　", fontCh18, 0, LEFT);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<BatchPaymentReceiveDataCase> caseList, HashMap<String, Object> map) throws Exception {
        try {
            String payCode = (String) map.get("payCode");
            String errorMessage = (String) map.get("errorMessage");

            // 2020.10.15 Kiyomi add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_BatchPaymentReceiveFailure_" + Encode.forJava(printDate) + ".pdf");
            // FileOutputStream bao = new FileOutputStream("E:\\"+"MonthlyRpt24_" + printDate + ".pdf");
            // writer = PdfWriter.getInstance(document, bao);

            document.open();

            BigDecimal pageRec = new BigDecimal(0); // 本頁小計 - 筆數
            BigDecimal allRec = new BigDecimal(0); // 總筆數

            Table table = null;

            int recNo = 0; // 用來暫存 編號

            for (int i = 0; i < caseList.size(); i++) { // ... [
                BatchPaymentReceiveDataCase caseData = caseList.get(i);

                // 第一筆加入表頭
                if (i == 0) {
                    table = addHeader(caseData, map);
                }

                // 本頁小計 - 筆數
                pageRec = pageRec.add(new BigDecimal(1));

                // 總筆數
                allRec = allRec.add(new BigDecimal(1));

                addColumn(table, 6, 1, String.valueOf(recNo + 1), fontCh16, 0, CENTER); // 序號
                addColumn(table, 8, 1, caseData.getTempHandleNo(), fontCh16, 0, LEFT); // 受理編號
                addColumn(table, 5, 1, caseData.getDivSeq(), fontCh16, 0, CENTER); // 分割序號
                addColumn(table, 8, 1, formatBigDecimalToFloat(((caseData.getAvailable_Money() == null) ? new BigDecimal(0) : caseData.getAvailable_Money())), fontCh16, 0, RIGHT); // 交易金額
                addColumn(table, 33, 1, "　", fontCh16, 0, LEFT); // 申請代算單位

                // 判段是否為最後一筆, 或達成換頁條件 (受益人與事故者關係 + 相關單位名稱1)
                if (i == caseList.size() - 1) {
                    // 塞滿空白行, 以讓表格美觀
                    // [
                    while (writer.fitsPage(table)) {
                        addEmptyRow(table, 1);
                    }

                    deleteRow(table, 4); // 表尾有 3 行 + 前面回圈會多塞 1 行, 故要刪 4 行

                    addEmptyRow(table, 1);
                    // ---
                    if (i == caseList.size() - 1) {

                        addColumn(table, 60, 1, "　　　總筆數：" + formatBigDecimalToInteger(allRec) + "／" + "本頁小計筆數：" + formatBigDecimalToInteger(pageRec), fontCh16, 0, LEFT);

                    }
                    else {
                        // ---
                        addColumn(table, 30, 1, "　", fontCh12, 0, LEFT);
                        addColumn(table, 30, 1, "本頁小計筆數：" + formatBigDecimalToInteger(pageRec), fontCh16, 0, LEFT);

                    }
                    document.add(table);

                    // 歸零
                    recNo = 0; // 編號
                    pageRec = new BigDecimal(0); // 本頁小計 - 筆數
                    /**
                     * if (!StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "Z")) { allRec = new BigDecimal(0); // 總筆數 }
                     */
                    if (i != caseList.size() - 1)
                        table = addHeader(caseList.get(i + 1), map); // 下一筆
                }
                else {
                    // 測試塞下一筆 + 表尾時是否需要換頁
                    addColumn(table, 6, 1, String.valueOf(recNo + 2), fontCh16, 0, CENTER); // 序號
                    addColumn(table, 8, 1, caseList.get(i + 1).getTempHandleNo(), fontCh16, 0, LEFT); // 受理編號
                    addColumn(table, 8, 1, formatBigDecimalToFloat(((caseList.get(i + 1).getCash_Amt() == null) ? new BigDecimal(0) : caseList.get(i + 1).getCash_Amt())), fontCh16, 0, RIGHT); // 交易金額
                    addColumn(table, 38, 1, "　", fontCh12, 0, LEFT); // 申請代算單位
                    // ---
                    addEmptyRow(table, 3); // 表尾 3 行

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);

                        // 表尾之本頁小計
                        addEmptyRow(table, 1);
                        // ---
                        addColumn(table, 30, 1, "　", fontCh12, 0, LEFT);
                        addColumn(table, 30, 1, "本頁小計筆數：" + formatBigDecimalToInteger(pageRec), fontCh16, 0, LEFT);

                        document.add(table);

                        // 編號 + 1
                        recNo++;

                        // 本頁小計歸零
                        pageRec = new BigDecimal(0); // 本頁小計 - 筆數

                        table = addHeader(caseList.get(i + 1), map); // 下一筆
                    }
                    else {
                        // 編號 + 1
                        recNo++;

                        deleteRow(table, 4);
                    }
                }
            } // ] ... end for (int i = 0; i < caseList.size(); i++)

            document.close();
            // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
            printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_BatchPaymentReceiveFailure_" + Encode.forJava(printDate) + ".pdf"), bao);
        }
        finally {
            document.close();
        }

        return bao;
    }
}
