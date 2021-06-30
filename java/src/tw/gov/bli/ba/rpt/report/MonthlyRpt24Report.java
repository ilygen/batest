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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt24Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt24PayAmtCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;

/**
 * 代扣補償金清冊
 * 
 * @author Kiyomi
 */
public class MonthlyRpt24Report extends ReportBase {
    private String printDate = ""; // 印表日期

    private HashMap<String, MonthlyRpt24PayAmtCase> payAmtMap; // 代扣補償金

    public MonthlyRpt24Report() throws Exception {
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
    public Table addHeader(MonthlyRpt24Case caseData) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.setPadding(3);

        addColumn(table, 20, 1, "報表編號：BALP0D3O0L", fontCh12, 0, LEFT);
        addColumn(table, 25, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(caseData.getPayCode(), DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
        // addColumn(table, 25, 1, "勞工保險局", fontCh18, 0, CENTER);
        addColumn(table, 15, 1, "　", fontCh12, 0, LEFT);
        // ---
        BigDecimal payAmt = new BigDecimal(0);
        if (payAmtMap != null) {
            MonthlyRpt24PayAmtCase payAmtCase = payAmtMap.get(StringUtils.trimToEmpty(caseData.getPayTyp()) + StringUtils.trimToEmpty(caseData.getCompName1()) + StringUtils.trimToEmpty(caseData.getPayBankId())
                            + StringUtils.trimToEmpty(caseData.getBranchId()) + StringUtils.trimToEmpty(caseData.getPayEeacc()));
            if (payAmtCase != null && payAmtCase.getPayAmt() != null)
                payAmt = payAmtCase.getPayAmt();
        }

        addColumn(table, 20, 1, "支付" + StringUtility.replaceSpaceString(caseData.getCompName1()) + "代扣補償金：" + formatBigDecimalToInteger(payAmt), fontCh12, 0, LEFT); // 支付代扣補償金

        addColumn(table, 25, 1, "勞保年金(" + StringUtils.defaultString(caseData.getPayCodeString()) + ")　代扣補償金清冊", fontCh18, 0, CENTER);
        addColumn(table, 15, 1, "印表頁次：第 " + writer.getCurrentPageNumber() + " 頁", fontCh12, 0, LEFT);
        // ---
        if (StringUtils.equalsIgnoreCase(caseData.getPayTyp(), "3")) {

            addColumn(table, 20, 1, "支付方式：支票", fontCh12, 0, LEFT);
        }
        else {
            // addColumn(table, 20, 1, "入帳銀行及帳號：" + StringUtils.defaultString(caseData.getPayBankId()) + StringUtils.defaultString(caseData.getBranchId()) + "-" + StringUtils.defaultString(caseData.getPayEeacc()), fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "入帳銀行及帳號：" + caseData.getPayBankIdStr(), fontCh12, 0, LEFT);
        }

        addColumn(table, 25, 1, "　", fontCh18, 0, CENTER);
        addColumn(table, 15, 1, "印表日期：" + DateUtility.formatChineseDateString(caseData.getcPrnDateStr(), false), fontCh12, 0, LEFT);
        // --

        addColumn(table, 60, 1, "核付日期：" + StringUtils.defaultString(caseData.getPayDateString()), fontCh18, 0, LEFT);

        addEmptyRow(table, 1);

        // 表格標題
        addColumn(table, 6, 1, "序號", fontCh18, 0, CENTER);
        addColumn(table, 8, 1, "受理編號", fontCh18, 0, LEFT);
        addColumn(table, 8, 1, "姓名", fontCh18, 0, LEFT);
        addColumn(table, 8, 1, "身分證號碼", fontCh18, 0, LEFT);
        addColumn(table, 8, 1, "給付金額", fontCh18, 0, RIGHT);
        addColumn(table, 6, 1, "核定年月", fontCh18, 0, CENTER);
        addColumn(table, 6, 1, "給付年月", fontCh18, 0, CENTER);

        addColumn(table, 10, 1, "申請代算單位", fontCh18, 0, LEFT);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt24Case> caseList, HashMap<String, MonthlyRpt24PayAmtCase> payAmtMap, HashMap<String, Object> map) throws Exception {
        try {
            String payCode = (String) map.get("payCode");

            // 2020.10.15 Kiyomi add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt24_" + Encode.forJava(printDate) + ".pdf");
            // FileOutputStream bao = new FileOutputStream("E:\\"+"MonthlyRpt24_" + printDate + ".pdf");
            // writer = PdfWriter.getInstance(document, bao);

            document.open();

            // 設定 代扣補償金 HashMap
            this.payAmtMap = payAmtMap;

            BigDecimal pageRec = new BigDecimal(0); // 本頁小計 - 筆數
            BigDecimal pageAmountAmt = new BigDecimal(0); // 本頁小計 - 金額
            BigDecimal allRec = new BigDecimal(0); // 總筆數
            BigDecimal allAmountAmt = new BigDecimal(0); // 總金額

            Table table = null;

            int recNo = 0; // 用來暫存 編號

            for (int i = 0; i < caseList.size(); i++) { // ... [
                MonthlyRpt24Case caseData = caseList.get(i);

                // 第一筆加入表頭
                if (i == 0) {
                    table = addHeader(caseData);
                }

                // 本頁小計 - 筆數
                pageRec = pageRec.add(new BigDecimal(1));
                // 本頁小計 - 金額
                if (caseData.getPayAmt() != null)
                    pageAmountAmt = pageAmountAmt.add(caseData.getPayAmt());

                // 總筆數
                allRec = allRec.add(new BigDecimal(1));
                // 總金額
                if (caseData.getPayAmt() != null)
                    allAmountAmt = allAmountAmt.add(caseData.getPayAmt());

                addColumn(table, 6, 1, String.valueOf(recNo + 1), fontCh12, 0, CENTER); // 序號
                addColumn(table, 8, 1, caseData.getApNoString(), fontCh12, 0, LEFT); // 受理編號
                addColumn(table, 8, 1, caseData.getBenName(), fontCh12, 0, LEFT); // 姓名
                addColumn(table, 8, 1, caseData.getBenIdn(), fontCh12, 0, LEFT); // 身分證號碼
                addColumn(table, 8, 1, formatBigDecimalToFloat(((caseData.getPayAmt() == null) ? new BigDecimal(0) : caseData.getPayAmt())), fontCh12, 0, RIGHT); // 交易金額
                addColumn(table, 6, 1, caseData.getIssuYmString(), fontCh12, 0, CENTER); // 核定年月
                addColumn(table, 6, 1, caseData.getPayYmString(), fontCh12, 0, CENTER); // 給付年月

                addColumn(table, 10, 1, caseData.getCompName2(), fontCh12, 0, LEFT); // 申請代算單位

                // 判段是否為最後一筆, 或達成換頁條件 (受益人與事故者關係 + 相關單位名稱1)
                if (i == caseList.size() - 1 || !StringUtils.equals(
                                StringUtils.trimToEmpty(caseData.getPayTyp()) + StringUtils.trimToEmpty(caseData.getCompName1()) + StringUtils.trimToEmpty(caseData.getPayBankId()) + StringUtils.trimToEmpty(caseData.getBranchId())
                                                + StringUtils.trimToEmpty(caseData.getPayEeacc()),
                                StringUtils.trimToEmpty(caseList.get(i + 1).getPayTyp()) + StringUtils.trimToEmpty(caseList.get(i + 1).getCompName1()) + StringUtils.trimToEmpty(caseList.get(i + 1).getPayBankId())
                                                + StringUtils.trimToEmpty(caseList.get(i + 1).getBranchId()) + StringUtils.trimToEmpty(caseList.get(i + 1).getPayEeacc()))) {
                    // 塞滿空白行, 以讓表格美觀
                    // [
                    while (writer.fitsPage(table)) {
                        addEmptyRow(table, 1);
                    }

                    deleteRow(table, 4); // 表尾有 3 行 + 前面回圈會多塞 1 行, 故要刪 4 行

                    addEmptyRow(table, 1);
                    // ---
                    if (!StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "Z") || i == caseList.size() - 1) {

                        addColumn(table, 30, 1, "　　　總筆數：" + formatBigDecimalToInteger(allRec) + "／" + "本頁小計筆數：" + formatBigDecimalToInteger(pageRec), fontCh12, 0, LEFT);
                        addColumn(table, 30, 1, "本頁小計金額：" + formatBigDecimalToFloat(pageAmountAmt), fontCh12, 0, LEFT);
                    }
                    else {
                        // ---
                        addColumn(table, 15, 1, "　", fontCh12, 0, LEFT);
                        addColumn(table, 15, 1, "本頁小計筆數：" + formatBigDecimalToInteger(pageRec), fontCh12, 0, LEFT);
                        addColumn(table, 30, 1, "本頁小計金額：" + formatBigDecimalToFloat(pageAmountAmt), fontCh12, 0, LEFT);

                    }
                    document.add(table);

                    // 歸零
                    recNo = 0; // 編號
                    pageRec = new BigDecimal(0); // 本頁小計 - 筆數
                    pageAmountAmt = new BigDecimal(0); // 本頁小計 - 金額

                    if (!StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "Z")) {
                        allRec = new BigDecimal(0); // 總筆數
                        allAmountAmt = new BigDecimal(0); // 總金額
                    }

                    if (i != caseList.size() - 1)
                        table = addHeader(caseList.get(i + 1)); // 下一筆
                }
                else {
                    // 測試塞下一筆 + 表尾時是否需要換頁
                    addColumn(table, 6, 1, String.valueOf(recNo + 2), fontCh12, 0, CENTER); // 序號
                    addColumn(table, 8, 1, caseList.get(i + 1).getApNoString(), fontCh12, 0, LEFT); // 受理編號
                    addColumn(table, 8, 1, caseList.get(i + 1).getBenName(), fontCh12, 0, LEFT); // 姓名
                    addColumn(table, 8, 1, caseList.get(i + 1).getBenIdn(), fontCh12, 0, LEFT); // 身分證號碼
                    addColumn(table, 8, 1, formatBigDecimalToFloat(((caseList.get(i + 1).getPayAmt() == null) ? new BigDecimal(0) : caseList.get(i + 1).getPayAmt())), fontCh12, 0, RIGHT); // 交易金額
                    addColumn(table, 6, 1, caseList.get(i + 1).getIssuYmString(), fontCh12, 0, CENTER); // 核定年月
                    addColumn(table, 6, 1, caseList.get(i + 1).getPayYmString(), fontCh12, 0, CENTER); // 給付年月

                    addColumn(table, 10, 1, caseList.get(i + 1).getCompName2(), fontCh12, 0, LEFT); // 申請代算單位
                    // ---
                    addEmptyRow(table, 3); // 表尾 3 行

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 4);

                        // 表尾之本頁小計
                        addEmptyRow(table, 1);
                        // ---
                        addColumn(table, 15, 1, "　", fontCh12, 0, LEFT);
                        addColumn(table, 15, 1, "本頁小計筆數：" + formatBigDecimalToInteger(pageRec), fontCh12, 0, LEFT);
                        addColumn(table, 30, 1, "本頁小計金額：" + formatBigDecimalToFloat(pageAmountAmt), fontCh12, 0, LEFT);

                        document.add(table);

                        // 編號 + 1
                        recNo++;

                        // 本頁小計歸零
                        pageRec = new BigDecimal(0); // 本頁小計 - 筆數
                        pageAmountAmt = new BigDecimal(0); // 本頁小計 - 金額

                        table = addHeader(caseList.get(i + 1)); // 下一筆
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
            printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt24_" + Encode.forJava(printDate) + ".pdf"), bao);
        }
        finally {
            document.close();
        }

        return bao;
    }
}
