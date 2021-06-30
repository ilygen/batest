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
import tw.gov.bli.ba.rpt.cases.MonthlyRpt07Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt07FooterRptCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt07MiddleRptCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;

/**
 * 月核定給付撥款總額表
 * 
 * @author Goston
 */
public class MonthlyRptK07Report extends ReportBase {
    private String printDate = ""; // 印表日期

    public MonthlyRptK07Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(false);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A3.rotate(), 80, 80, 60, 30);
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
    public Table addHeader(MonthlyRpt07Case caseData) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.setPadding(3);

        addColumn(table, 60, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(caseData.getPayCode(), DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
        // addColumn(table, 60, 1, "勞工保險局", fontCh18, 0, CENTER);
        // ---
        addColumn(table, 60, 1, "勞保" + caseData.getPayCodeString() + "給付撥款總額表", fontCh18, 0, CENTER);
        // ---
        addColumn(table, 45, 1, "撥 款 日：" + DateUtility.formatChineseDateString(caseData.getPayDate2(), false), fontCh12, 0, LEFT);
        addColumn(table, 15, 1, "印表日期：" + printDate, fontCh12, 0, LEFT);
        // ---
        addColumn(table, 45, 1, "出帳帳號：139860-2", fontCh12, 0, LEFT);
        addColumn(table, 15, 1, "頁　　次：" + writer.getCurrentPageNumber(), fontCh12, 0, LEFT);
        // ---
        addColumn(table, 60, 1, "檔案名稱：" + StringUtils.defaultString(caseData.getMfileName()), fontCh12, 0, LEFT);
        // ---
        addEmptyRow(table, 1);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt07Case> caseList, HashMap<String, Object> map) throws Exception {
        try {
            String payCode = (String) map.get("payCode");

            // 2009.6.8 EvelynHsu add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt07_" + Encode.forJava(printDate) + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt07_" + printDate + ".pdf");
            // writer = PdfWriter.getInstance(document, bao);

            document.open();

            for (int i = 0; i < caseList.size(); i++) { // begin ... [
                MonthlyRpt07Case caseData = caseList.get(i);

                // 表頭
                Table table = addHeader(caseData);

                // 新案相關資料 (issuTyp = 1)
                // [
                BigDecimal dataCountIssuTyp1 = new BigDecimal(0);
                BigDecimal amt2IssuTyp1 = new BigDecimal(0);

                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(caseData.getDataCountIssuTyp1())))
                    dataCountIssuTyp1 = new BigDecimal(StringUtils.trimToEmpty(caseData.getDataCountIssuTyp1()));
                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(caseData.getAmt2IssuTyp1())))
                    amt2IssuTyp1 = new BigDecimal(StringUtils.trimToEmpty(caseData.getAmt2IssuTyp1()));
                // ]

                // 續發相關資料 (issuTyp = 2)
                // [
                BigDecimal dataCountIssuTyp2 = new BigDecimal(0);
                BigDecimal amt2IssuTyp2 = new BigDecimal(0);

                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(caseData.getDataCountIssuTyp2())))
                    dataCountIssuTyp2 = new BigDecimal(StringUtils.trimToEmpty(caseData.getDataCountIssuTyp2()));
                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(caseData.getAmt2IssuTyp2())))
                    amt2IssuTyp2 = new BigDecimal(StringUtils.trimToEmpty(caseData.getAmt2IssuTyp2()));
                // ]

                // 結案相關資料 (issuTyp = 4)
                // [
                BigDecimal dataCountIssuTyp4 = new BigDecimal(0);
                BigDecimal amt2IssuTyp4 = new BigDecimal(0);

                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(caseData.getDataCountIssuTyp4())))
                    dataCountIssuTyp4 = new BigDecimal(StringUtils.trimToEmpty(caseData.getDataCountIssuTyp4()));
                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(caseData.getAmt2IssuTyp4())))
                    amt2IssuTyp4 = new BigDecimal(StringUtils.trimToEmpty(caseData.getAmt2IssuTyp4()));
                // ]

                // 補發相關資料 (issuTyp = 5)
                // [
                BigDecimal dataCountIssuTyp5 = new BigDecimal(0);
                BigDecimal amt2IssuTyp5 = new BigDecimal(0);

                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(caseData.getDataCountIssuTyp5())))
                    dataCountIssuTyp5 = new BigDecimal(StringUtils.trimToEmpty(caseData.getDataCountIssuTyp5()));
                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(caseData.getAmt2IssuTyp5())))
                    amt2IssuTyp5 = new BigDecimal(StringUtils.trimToEmpty(caseData.getAmt2IssuTyp5()));
                // ]

                // 改匯相關資料 (issuTyp = A)
                // [
                BigDecimal dataCountIssuTypA = new BigDecimal(0);
                BigDecimal amt2IssuTypA = new BigDecimal(0);

                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(caseData.getDataCountIssuTypA())))
                    dataCountIssuTypA = new BigDecimal(StringUtils.trimToEmpty(caseData.getDataCountIssuTypA()));
                if (StringUtils.isNotBlank(StringUtils.trimToEmpty(caseData.getAmt2IssuTypA())))
                    amt2IssuTypA = new BigDecimal(StringUtils.trimToEmpty(caseData.getAmt2IssuTypA()));
                // ]

                addColumn(table, 9, 1, "輸入資料筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 51, 1, formatBigDecimalToInteger(caseData.getSumDataCount()), fontCh12, 0, LEFT); // 輸入資料筆數
                // ---
                addColumn(table, 9, 1, "總筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseData.getSumDataCount()), fontCh12, 0, LEFT); // 總筆數
                addColumn(table, 5, 1, "總金額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseData.getAmountAmt()), fontCh12, 0, LEFT); // 總金額
                addColumn(table, 7, 1, "實付金額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseData.getAmountAmt()), fontCh12, 0, LEFT); // 實付金額
                addColumn(table, 7, 1, "止付金額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 止付金額
                // ---
                addColumn(table, 9, 1, "核定(新案)筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(dataCountIssuTyp1), fontCh12, 0, LEFT); // 核定(新案)筆數
                addColumn(table, 5, 1, "金　額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(amt2IssuTyp1), fontCh12, 0, LEFT); // 金 額
                addColumn(table, 7, 1, "核定止付筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 核定止付筆數
                addColumn(table, 7, 1, "核定止付金額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 核定止付金額
                // ---
                addColumn(table, 9, 1, "補發筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(dataCountIssuTyp5), fontCh12, 0, LEFT); // 補發筆數
                addColumn(table, 5, 1, "金　額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(amt2IssuTyp5), fontCh12, 0, LEFT); // 金 額
                addColumn(table, 7, 1, "補發止付筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 補發止付筆數
                addColumn(table, 7, 1, "補發止付金額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 補發止付金額
                // ---
                addColumn(table, 9, 1, "續發筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(dataCountIssuTyp2), fontCh12, 0, LEFT); // 續發筆數
                addColumn(table, 5, 1, "金　額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(amt2IssuTyp2), fontCh12, 0, LEFT); // 金 額
                addColumn(table, 7, 1, "續發止付筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 續發止付筆數
                addColumn(table, 7, 1, "續發止付金額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 續發止付金額
                // ---
                addColumn(table, 9, 1, "結案筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(dataCountIssuTyp4), fontCh12, 0, LEFT); // 續發筆數
                addColumn(table, 5, 1, "金　額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(amt2IssuTyp4), fontCh12, 0, LEFT); // 金 額
                addColumn(table, 7, 1, "結案止付筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 續發止付筆數
                addColumn(table, 7, 1, "結案止付金額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 續發止付金額
                // ---
                addColumn(table, 9, 1, "改匯筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(dataCountIssuTypA), fontCh12, 0, LEFT); // 改匯筆數
                addColumn(table, 5, 1, "金　額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(amt2IssuTypA), fontCh12, 0, LEFT); // 金 額
                addColumn(table, 7, 1, "改匯止付筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 改匯止付筆數
                addColumn(table, 7, 1, "改匯止付金額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 改匯止付金額
                // ---
                addColumn(table, 9, 1, "輸出筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, formatBigDecimalToInteger(caseData.getOutputDataCount()), fontCh12, 0, LEFT); // 輸出筆數
                addColumn(table, 5, 1, "　", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "　", fontCh12, 0, LEFT);
                addColumn(table, 7, 1, "止付總筆數：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 止付總筆數
                addColumn(table, 7, 1, "止付總金額：", fontCh12, 0, RIGHT);
                addColumn(table, 8, 1, "0", fontCh12, 0, LEFT); // 止付總金額
                // ---
                addEmptyRow(table, 1);

                // MIDDLE
                addColumn(table, 10, 1, "行庫局", fontCh12, 1, CENTER);
                addColumn(table, 25, 1, "名　　稱", fontCh12, 1, CENTER);
                addColumn(table, 10, 1, "筆　數", fontCh12, 1, CENTER);
                addColumn(table, 15, 1, "金　額", fontCh12, 1, CENTER);
                if (caseData.getMiddleRptData().size() > 0) {
                    for (MonthlyRpt07MiddleRptCase middleRptCase : caseData.getMiddleRptData()) {
                        addColumn(table, 10, 1, middleRptCase.getBankNo(), fontCh12, 1, CENTER); // 行庫局
                        addColumn(table, 25, 1, middleRptCase.getBankName(), fontCh12, 1, LEFT); // 名稱
                        addColumn(table, 10, 1, formatBigDecimalToInteger(middleRptCase.getDataCount()), fontCh12, 1, RIGHT); // 筆數
                        addColumn(table, 15, 1, formatBigDecimalToInteger(middleRptCase.getAmountAmt()), fontCh12, 1, RIGHT); // 金額
                    }
                }
                else {
                    addColumn(table, 10, 1, "　", fontCh12, 1, CENTER); // 行庫局
                    addColumn(table, 25, 1, "　", fontCh12, 1, LEFT); // 名稱
                    addColumn(table, 10, 1, "　", fontCh12, 1, RIGHT); // 筆數
                    addColumn(table, 15, 1, "　", fontCh12, 1, RIGHT); // 金額
                }

                addEmptyRow(table, 1);

                // FOOTER
                addColumn(table, 10, 1, "給付代號", fontCh12, 1, CENTER);
                addColumn(table, 25, 1, "給付名稱", fontCh12, 1, CENTER);
                addColumn(table, 10, 1, "筆　數", fontCh12, 1, CENTER);
                addColumn(table, 15, 1, "金　額", fontCh12, 1, CENTER);
                if (caseData.getFooterRptData().size() > 0) {
                    for (MonthlyRpt07FooterRptCase footerRptCase : caseData.getFooterRptData()) {
                        addColumn(table, 10, 1, footerRptCase.getPayNo(), fontCh12, 1, CENTER); // 給付代號
                        addColumn(table, 25, 1, footerRptCase.getPayName(), fontCh12, 1, LEFT); // 給付名稱
                        addColumn(table, 10, 1, formatBigDecimalToInteger(footerRptCase.getDataCount()), fontCh12, 1, RIGHT); // 筆數
                        addColumn(table, 15, 1, formatBigDecimalToInteger(footerRptCase.getAmountAmt()), fontCh12, 1, RIGHT); // 金額
                    }
                }
                else {
                    addColumn(table, 10, 1, "　", fontCh12, 1, CENTER); // 給付代號
                    addColumn(table, 25, 1, "　", fontCh12, 1, LEFT); // 給付名稱
                    addColumn(table, 10, 1, "　", fontCh12, 1, RIGHT); // 筆數
                    addColumn(table, 15, 1, "　", fontCh12, 1, RIGHT); // 金額
                }

                document.add(table);
            } // ] ... end for (int i = 0; i < caseList.size(); i++)

            document.close();
            // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
            printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRptK07_" + Encode.forJava(printDate) + ".pdf"), bao);
        }
        finally {
            document.close();
        }

        return bao;
    }
}
