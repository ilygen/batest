package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.owasp.encoder.Encode;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt09Case;
import tw.gov.bli.ba.util.DateUtility;

/**
 * 給付抵銷紓困貸款明細
 * 
 * @author Goston
 */
public class MonthlyRptK09Report extends ReportBase {
    private String printDate = ""; // 印表日期

    public MonthlyRptK09Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(false);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
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
    public Table addHeader(MonthlyRpt09Case caseData) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.setPadding(3);

        addColumn(table, 60, 1, "勞保年金給付抵銷紓困貸款明細資料", fontCh16, 0, CENTER);
        // ---
        addColumn(table, 40, 1, "核付日期：" + DateUtility.formatChineseDateString(caseData.getPayDate(), false), fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "印表日期：" + DateUtility.formatChineseDateString(caseData.getcPrnDateStr(), false), fontCh12, 0, LEFT);
        // ---
        addColumn(table, 40, 1, "給 付 別：" + caseData.getPayCodeString(), fontCh12, 0, LEFT);
        addColumn(table, 20, 1, "印表頁次：第 " + writer.getCurrentPageNumber() + " 頁", fontCh12, 0, LEFT);

        // 表格標題
        addColumn(table, 6, 1, "序號", fontCh12, 1, CENTER);
        addColumn(table, 6, 1, "分行別", fontCh12, 1, CENTER);
        addColumn(table, 14, 1, "放款帳號", fontCh12, 1, CENTER);
        addColumn(table, 8, 1, "姓名", fontCh12, 1, CENTER);
        addColumn(table, 8, 1, "身分證號", fontCh12, 1, CENTER);
        addColumn(table, 8, 1, "本息截止日", fontCh12, 1, CENTER);
        addColumn(table, 10, 1, "抵銷金額", fontCh12, 1, CENTER);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt09Case> caseList, HashMap<String, Object> map) throws Exception {
        try {
            String payCode = (String) map.get("payCode");

            // 2009.6.8 EvelynHsu add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt09_" + Encode.forJava(printDate) + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt09_" + printDate + ".pdf");
            // writer = PdfWriter.getInstance(document, bao);

            document.open();

            BigDecimal pageRec = new BigDecimal(0); // 本頁小計 - 抵銷件數
            BigDecimal pageAmountAmt = new BigDecimal(0); // 本頁小計 - 抵銷金額
            BigDecimal allRec = new BigDecimal(0); // 總計 - 抵銷件數
            BigDecimal allAmountAmt = new BigDecimal(0); // 總計 - 抵銷金額

            Table table = null;

            for (int i = 0; i < caseList.size(); i++) {
                MonthlyRpt09Case caseData = caseList.get(i);

                // 第一筆加入表頭
                if (i == 0) {
                    table = addHeader(caseData);
                }

                // 本頁小計 - 抵銷件數
                pageRec = pageRec.add(new BigDecimal(1));
                // 本頁小計 - 抵銷金額
                pageAmountAmt = pageAmountAmt.add(caseData.getPayAmtNonNull());
                // 總計 - 抵銷件數
                allRec = allRec.add(new BigDecimal(1));
                // 總計 - 抵銷金額
                allAmountAmt = allAmountAmt.add(caseData.getPayAmtNonNull());

                addColumn(table, 6, 1, String.valueOf(i + 1) + ".", fontCh12, 1, LEFT); // 序號
                addColumn(table, 6, 1, caseData.getBranchId(), fontCh12, 1, LEFT); // 分行別
                addColumn(table, 14, 1, caseData.getPayEeacc(), fontCh12, 1, LEFT); // 放款帳號
                addColumn(table, 8, 1, caseData.getBenName(), fontCh12, 1, LEFT); // 姓名
                addColumn(table, 8, 1, caseData.getBenIdn(), fontCh12, 1, CENTER); // 身分證號
                addColumn(table, 8, 1, DateUtility.formatChineseDateString(caseData.getDlineDate(), false), fontCh12, 1, CENTER); // 本息截止日
                addColumn(table, 10, 1, formatBigDecimalToInteger(caseData.getPayAmtNonNull()), fontCh12, 1, RIGHT); // 抵銷金額

                if (i == caseList.size() - 1) { // 表示目前印的是最後一筆, 必須加入表尾
                    // 塞滿空白行, 以讓表格美觀
                    // [
                    while (writer.fitsPage(table)) {
                        addColumn(table, 6, 1, "　", fontCh12, 1, LEFT);
                        addColumn(table, 6, 1, "　", fontCh12, 1, LEFT);
                        addColumn(table, 14, 1, "　", fontCh12, 1, LEFT);
                        addColumn(table, 8, 1, "　", fontCh12, 1, LEFT);
                        addColumn(table, 8, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 8, 1, "　", fontCh12, 1, CENTER);
                        addColumn(table, 10, 1, "　", fontCh12, 1, RIGHT);
                    }
                    // ]

                    deleteRow(table, 4); // 表尾有 3 行 + 前面回圈會多塞 1 行, 故要刪 4 行

                    addEmptyRow(table, 1);
                    // ---
                    addColumn(table, 10, 1, "本頁小計", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "抵銷件數：", fontCh12, 0, LEFT);
                    addColumn(table, 14, 1, formatBigDecimalToInteger(pageRec), fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "抵銷金額：", fontCh12, 0, LEFT);
                    addColumn(table, 14, 1, formatBigDecimalToInteger(pageAmountAmt) + " 元", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "　", fontCh12, 0, LEFT);
                    // ---
                    addColumn(table, 10, 1, "總計", fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "抵銷件數：", fontCh12, 0, LEFT);
                    addColumn(table, 14, 1, formatBigDecimalToInteger(allRec), fontCh12, 0, LEFT);
                    addColumn(table, 8, 1, "抵銷金額：", fontCh12, 0, LEFT);
                    addColumn(table, 14, 1, formatBigDecimalToInteger(allAmountAmt) + " 元", fontCh12, 0, RIGHT);
                    addColumn(table, 6, 1, "　", fontCh12, 0, LEFT);
                }
                else {
                    // 測試塞下一筆 + 表尾時是否需要換頁 (表尾有三行, 分別是 空白行 1 行, 本頁小計 1 行, 總計 1 行)
                    addColumn(table, 6, 1, String.valueOf(i + 2) + ".", fontCh12, 1, LEFT); // 序號
                    addColumn(table, 6, 1, caseList.get(i + 1).getBranchId(), fontCh12, 1, LEFT); // 分行別
                    addColumn(table, 14, 1, caseList.get(i + 1).getPayEeacc(), fontCh12, 1, LEFT); // 放款帳號
                    addColumn(table, 8, 1, caseList.get(i + 1).getBenName(), fontCh12, 1, LEFT); // 姓名
                    addColumn(table, 8, 1, caseList.get(i + 1).getBenIdn(), fontCh12, 1, CENTER); // 身分證號
                    addColumn(table, 8, 1, DateUtility.formatChineseDateString(caseList.get(i + 1).getDlineDate(), false), fontCh12, 1, CENTER); // 本息截止日
                    addColumn(table, 10, 1, formatBigDecimalToInteger(caseList.get(i + 1).getPayAmtNonNull()), fontCh12, 1, RIGHT); // 抵銷金額
                    // ---
                    addEmptyRow(table, 3); // 表尾 3 行

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);

                        addEmptyRow(table, 1);
                        // ---
                        addColumn(table, 10, 1, "本頁小計", fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "抵銷件數：", fontCh12, 0, LEFT);
                        addColumn(table, 14, 1, formatBigDecimalToInteger(pageRec), fontCh12, 0, LEFT);
                        addColumn(table, 8, 1, "抵銷金額：", fontCh12, 0, LEFT);
                        addColumn(table, 14, 1, formatBigDecimalToInteger(pageAmountAmt) + " 元", fontCh12, 0, RIGHT);
                        addColumn(table, 6, 1, "　", fontCh12, 0, LEFT);
                        // ---
                        // 不是最後一頁, 總計這行就不加了, 省得有不預期換頁的麻煩
                        // addColumn(table, 10, 1, " ", fontCh12, 0, LEFT);
                        // addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);
                        // addColumn(table, 14, 1, " ", fontCh12, 0, LEFT);
                        // addColumn(table, 8, 1, " ", fontCh12, 0, LEFT);
                        // addColumn(table, 14, 1, " ", fontCh12, 0, RIGHT);
                        // addColumn(table, 6, 1, " ", fontCh12, 0, LEFT);

                        document.add(table);

                        // 本頁小計歸零
                        pageRec = new BigDecimal(0); // 本頁小計 - 抵銷件數
                        pageAmountAmt = new BigDecimal(0); // 本頁小計 - 抵銷金額

                        table = addHeader(caseList.get(i + 1)); // 下一筆
                    }
                    else {
                        deleteRow(table, 4);
                    }
                }
            }

            document.add(table);

            document.close();
            // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
            printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRptK09_" + Encode.forJava(printDate) + ".pdf"), bao);
        }
        finally {
            document.close();
        }

        return bao;
    }
}
