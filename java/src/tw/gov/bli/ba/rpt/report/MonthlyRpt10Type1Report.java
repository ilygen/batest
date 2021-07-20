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

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type1PayAmtCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type1RptCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;

/**
 * 核付清單
 * 
 * @author Goston
 */
public class MonthlyRpt10Type1Report extends ReportBase {
    private String printDate = ""; // 印表日期

    private HashMap<String, MonthlyRpt10Type1PayAmtCase> payAmtMap; // 代扣補償金

    public MonthlyRpt10Type1Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(true);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 20, 20, 30, 30);
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
            addColumn(table, 60, 1, "　", fontCh10, 0, LEFT); // 注意: 內容是全形空白
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
    public Table addHeader(MonthlyRpt10Type1RptCase caseData) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(60);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.setPadding(1.57f);

        addColumn(table, 15, 1, "報表編號：BALP0D3A0L-1", fontCh10, 0, LEFT);
        addColumn(table, 30, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(caseData.getPayCode(), DateUtility.getNowWestDate()), fontCh16, 0, CENTER);
        // addColumn(table, 30, 1, "勞工保險局給付處", fontCh16, 0, CENTER);
        addColumn(table, 15, 1, "　", fontCh10, 0, LEFT);
        // ---
        // 給付方式 為 匯票郵寄申請人 則顯示郵局帳戶
        if (StringUtils.equalsIgnoreCase(caseData.getPayTyp(), ConstantKey.BAAPPBASE_PAYTYP_4))
            addColumn(table, 18, 1, "帳　　號：郵局50093111號帳戶", fontCh10, 0, LEFT);
        else
            addColumn(table, 18, 1, "帳　　號：土銀台北139862帳戶", fontCh10, 0, LEFT);
        addColumn(table, 24, 1, StringUtils.defaultString(caseData.getPayTypName()) + "　核付清單", fontCh16, 0, CENTER);
        addColumn(table, 18, 1, "印表頁次：第 " + writer.getCurrentPageNumber() + " 頁", fontCh10, 0, LEFT);
        // ---
        // 受益人與事故者關係 為 'Z' 則顯示 支付代扣補償金
        if (StringUtils.equalsIgnoreCase(caseData.getBenEvtRel(), "Z")) {
            BigDecimal payAmt = new BigDecimal(0);
            if (payAmtMap != null) {
                MonthlyRpt10Type1PayAmtCase payAmtCase = payAmtMap.get(StringUtils.trimToEmpty(caseData.getCompName1()));
                if (payAmtCase != null && payAmtCase.getPayAmt() != null)
                    payAmt = payAmtCase.getPayAmt();
            }

            addColumn(table, 42, 1, "支付" + StringUtility.replaceSpaceString(caseData.getCompName1()) + "代扣補償金：" + formatBigDecimalToInteger(payAmt), fontCh10, 0, LEFT); // 支付代扣補償金
        }
        else {
            addColumn(table, 42, 1, "　", fontCh10, 0, LEFT); // 支付代扣補償金
        }

        addColumn(table, 18, 1, "印表日期：" + DateUtility.formatChineseDateString(caseData.getcPrnDateStr(), false), fontCh10, 0, LEFT);
        // ---
        addColumn(table, 60, 1, "開票日期：" + StringUtils.defaultString(caseData.getPayDateString()), fontCh10, 0, LEFT);

        // 表格標題
        // [
        addColumn(table, 4, 2, "編號", fontCh10, 0, CENTER);
        addColumn(table, 13, 1, "受理編號", fontCh10, 0, CENTER);
        addColumn(table, 11, 1, "核定年月　給付年月", fontCh10, 0, CENTER);
        addColumn(table, 10, 2, "受款人抬頭", fontCh10, 0, CENTER);
        addColumn(table, 7, 1, "金額", fontCh10, 0, CENTER);
        addColumn(table, 6, 1, "核定日期", fontCh10, 0, CENTER);
        addColumn(table, 9, 1, "支票號碼", fontCh10, 0, CENTER);
        // ---
        addColumn(table, 13, 1, "帳　　號", fontCh10, 0, CENTER);
        addColumn(table, 11, 1, "身分證號", fontCh10, 0, CENTER);
        addColumn(table, 7, 1, "新台幣：元", fontCh10, 0, CENTER);
        addColumn(table, 6, 1, "頁次行次", fontCh10, 0, CENTER);
        addColumn(table, 9, 1, "備　　註", fontCh10, 0, CENTER);
        // ]

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<MonthlyRpt10Type1RptCase> caseList, HashMap<String, MonthlyRpt10Type1PayAmtCase> payAmtMap, List<MonthlyRpt10Type1RptCase> subCaseList, HashMap<String, Object> map) throws Exception {
        try {
            String payCode = (String) map.get("payCode");

            // 2009.2.20 EvelynHsu add 輸出報表檔至主機上
            String printDate = DateUtility.getNowChineseDate();
            // FileOutputStream bao = new FileOutputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt10Type1_" + Encode.forJava(printDate) + ".pdf");
            // FileOutputStream bao = new FileOutputStream("F:\\"+"MonthlyRpt10Type1_" + printDate + ".pdf");
            // writer = PdfWriter.getInstance(document, bao);
            document.open();

            // 設定 代扣補償金 HashMap
            this.payAmtMap = payAmtMap;

            // 定義 Border 樣式
            int nLine1BorderStyle = Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT;
            int nLine2BorderStyle = Rectangle.BOTTOM | Rectangle.LEFT | Rectangle.RIGHT;

            BigDecimal pageRec = new BigDecimal(0); // 本頁小計 - 件數
            BigDecimal pageAmountAmt = new BigDecimal(0); // 本頁小計 - 金額
            BigDecimal allRec = new BigDecimal(0); // 總計 - 件數
            BigDecimal allAmountAmt = new BigDecimal(0); // 總計 - 金額

            int recNo = 0; // 用來暫存 編號

            Table table = null;

            for (int i = 0; i < caseList.size(); i++) { // ... [
                MonthlyRpt10Type1RptCase caseData = caseList.get(i);

                // 第一筆加入表頭
                if (i == 0) {
                    table = addHeader(caseData);
                }

                // 本頁小計 - 件數
                pageRec = pageRec.add(new BigDecimal(1));
                // 本頁小計 - 金額
                if (caseData.getPayAmt() != null)
                    pageAmountAmt = pageAmountAmt.add(caseData.getPayAmt());
                // 總計 - 件數
                allRec = allRec.add(new BigDecimal(1));
                // 總計 - 金額
                if (caseData.getPayAmt() != null)
                    allAmountAmt = allAmountAmt.add(caseData.getPayAmt());

                // 列印資料
                // [
                addColumnAssignVAlignment(table, 4, 2, String.valueOf(recNo + 1), fontCh10, 1, CENTER, MIDDLE); // 編號
                addColumn(table, 13, 1, caseData.getApNoString(), fontCh10, 1, nLine1BorderStyle, LEFT); // 受理編號
                addColumn(table, 11, 1, StringUtils.rightPad(caseData.getIssuYmString(), 5, " ") + "　" + StringUtils.rightPad(caseData.getPayYmString(), 5, " "), fontCh10, 1, nLine1BorderStyle, LEFT); // 核定年月 給付年月
                addColumn(table, 10, 2, caseData.getBenName(), fontCh10, 1, LEFT); // 受款人抬頭
                addColumn(table, 7, 1, formatBigDecimalToInteger(((caseData.getPayAmt() == null) ? new BigDecimal(0) : caseData.getPayAmt())), fontCh10, 1, nLine1BorderStyle, RIGHT); // 金額
                addColumn(table, 6, 1, caseData.getChkDateString(), fontCh10, 1, nLine1BorderStyle, RIGHT); // 核定日期
                addColumn(table, 9, 1, "　", fontCh10, 1, nLine1BorderStyle, LEFT); // 支票號碼
                // ---
                addColumn(table, 13, 1, caseData.getBankString(), fontCh10, 1, nLine2BorderStyle, LEFT); // 帳 號
                addColumn(table, 11, 1, caseData.getBenIdn(), fontCh10, 1, nLine2BorderStyle, LEFT); // 身分證號
                addColumn(table, 7, 1, "　", fontCh10, 1, nLine2BorderStyle, RIGHT); // 新台幣：元
                addColumn(table, 6, 1, (caseData.getRptPage() == null) ? "" : caseData.getRptPage().toPlainString(), fontCh10, 1, nLine2BorderStyle, RIGHT); // 頁次行次
                addColumn(table, 9, 1, caseData.getCompName2(), fontCh10, 1, nLine2BorderStyle, LEFT); // 備 註
                // ]

                // 判段是否為最後一筆, 或達成換頁條件 (給付方式 + 受益人與事故者關係 + 相關單位名稱1)
                if (i == caseList.size() - 1 || !StringUtils.equals(StringUtils.trimToEmpty(caseData.getPayTyp()), StringUtils.trimToEmpty(caseList.get(i + 1).getPayTyp()))) {
                    // 塞滿空白行, 以讓表格美觀
                    // [
                    while (writer.fitsPage(table)) {
                        // [
                        addColumn(table, 4, 2, "　", fontCh10, 1, CENTER);
                        addColumn(table, 13, 1, "　", fontCh10, 1, nLine1BorderStyle, LEFT);
                        addColumn(table, 11, 1, "　", fontCh10, 1, nLine1BorderStyle, LEFT);
                        addColumn(table, 10, 2, "　", fontCh10, 1, LEFT);
                        addColumn(table, 7, 1, "　", fontCh10, 1, nLine1BorderStyle, RIGHT);
                        addColumn(table, 6, 1, "　", fontCh10, 1, nLine1BorderStyle, RIGHT);
                        addColumn(table, 9, 1, "　", fontCh10, 1, nLine1BorderStyle, LEFT);
                        // ---
                        addColumn(table, 13, 1, "　", fontCh10, 1, nLine2BorderStyle, LEFT);
                        addColumn(table, 11, 1, "　", fontCh10, 1, nLine2BorderStyle, LEFT);
                        addColumn(table, 7, 1, "　", fontCh10, 1, nLine2BorderStyle, RIGHT);
                        addColumn(table, 6, 1, "　", fontCh10, 1, nLine2BorderStyle, RIGHT);
                        addColumn(table, 9, 1, "　", fontCh10, 1, nLine2BorderStyle, LEFT);
                        // ]
                    }
                    // ]
                    deleteRow(table, 8); // 表尾有 5 行 + 前面回圈會多塞 1 筆, 故要刪 6 行

                    addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT, LEFT);
                    addColumn(table, 8, 1, "本頁小計", fontCh10, 0, LEFT);
                    addColumn(table, 6, 1, "件數：", fontCh10, 0, RIGHT);
                    addColumn(table, 10, 1, formatBigDecimalToInteger(pageRec), fontCh10, 0, LEFT);
                    addColumn(table, 8, 1, "金額：", fontCh10, 0, RIGHT);
                    addColumn(table, 18, 1, formatBigDecimalToInteger(pageAmountAmt), fontCh10, 1, Rectangle.RIGHT, LEFT);
                    // ---
                    addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT | Rectangle.BOTTOM, LEFT);
                    addColumn(table, 8, 1, "總計", fontCh10, 1, Rectangle.BOTTOM, LEFT);
                    addColumn(table, 6, 1, "件數：", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                    addColumn(table, 10, 1, formatBigDecimalToInteger(allRec), fontCh10, 1, Rectangle.BOTTOM, LEFT);
                    addColumn(table, 8, 1, "金額：", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                    addColumn(table, 18, 1, formatBigDecimalToInteger(allAmountAmt), fontCh10, 1, Rectangle.RIGHT | Rectangle.BOTTOM, LEFT);
                    // --
                    addEmptyRow(table, 1);
                    // --
                    addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                    addColumn(table, 20, 1, printDate, fontCh10, 0, LEFT);
                    // --
                    // 給付方式 為 匯票郵寄申請人 則顯示郵局
                    if (StringUtils.equalsIgnoreCase(caseData.getPayTyp(), ConstantKey.BAAPPBASE_PAYTYP_4)) {
                        addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 20, 1, "郵政儲金匯業局\r\n　儲匯管理處　", fontCh10, 0, LEFT);
                    }
                    else {
                        addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 20, 1, "土銀台北分行", fontCh10, 0, LEFT);
                    }

                    // 歸零
                    recNo = 0; // 編號
                    pageRec = new BigDecimal(0); // 本頁小計 - 件數
                    pageAmountAmt = new BigDecimal(0); // 本頁小計 - 金額
                    allRec = new BigDecimal(0); // 總計 - 件數
                    allAmountAmt = new BigDecimal(0); // 總計 - 金額

                    document.add(table);

                    if (i != caseList.size() - 1)
                        table = addHeader(caseList.get(i + 1)); // 下一筆
                }
                else {
                    // 測試塞下一筆 + 表尾時是否需要換頁
                    addColumnAssignVAlignment(table, 4, 2, String.valueOf(recNo + 2), fontCh10, 1, CENTER, MIDDLE); // 編號
                    addColumn(table, 13, 1, caseList.get(i + 1).getApNoString(), fontCh10, 1, nLine1BorderStyle, LEFT); // 受理編號
                    addColumn(table, 11, 1, StringUtils.rightPad(caseList.get(i + 1).getIssuYmString(), 5, " ") + "　" + StringUtils.rightPad(caseList.get(i + 1).getPayYmString(), 5, " "), fontCh10, 1, nLine1BorderStyle, LEFT); // 核定年月 給付年月
                    addColumn(table, 10, 2, caseList.get(i + 1).getBenName(), fontCh10, 1, LEFT); // 受款人抬頭
                    addColumn(table, 7, 1, formatBigDecimalToInteger(((caseList.get(i + 1).getPayAmt() == null) ? new BigDecimal(0) : caseList.get(i + 1).getPayAmt())), fontCh10, 1, nLine1BorderStyle, RIGHT); // 金額
                    addColumn(table, 6, 1, caseList.get(i + 1).getChkDateString(), fontCh10, 1, nLine1BorderStyle, RIGHT); // 核定日期
                    addColumn(table, 9, 1, "　", fontCh10, 1, nLine1BorderStyle, LEFT); // 支票號碼
                    // ---
                    addColumn(table, 13, 1, caseList.get(i + 1).getAccountNo(), fontCh10, 1, nLine2BorderStyle, LEFT); // 帳 號
                    addColumn(table, 11, 1, caseList.get(i + 1).getBenIdn(), fontCh10, 1, nLine2BorderStyle, LEFT); // 身分證號
                    addColumn(table, 7, 1, "　", fontCh10, 1, nLine2BorderStyle, RIGHT); // 新台幣：元
                    addColumn(table, 6, 1, (caseList.get(i + 1).getRptPage() == null) ? "" : caseList.get(i + 1).getRptPage().toPlainString(), fontCh10, 1, nLine2BorderStyle, RIGHT); // 頁次行次
                    addColumn(table, 9, 1, caseData.getCompName2(), fontCh10, 1, nLine2BorderStyle, LEFT); // 備 註
                    // ---
                    // 表尾
                    // ---
                    addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT, LEFT);
                    addColumn(table, 8, 1, "本頁小計", fontCh10, 0, LEFT);
                    addColumn(table, 6, 1, "件數：", fontCh10, 0, RIGHT);
                    addColumn(table, 10, 1, formatBigDecimalToInteger(pageRec), fontCh10, 0, LEFT);
                    addColumn(table, 8, 1, "金額：", fontCh10, 0, RIGHT);
                    addColumn(table, 18, 1, formatBigDecimalToInteger(pageAmountAmt), fontCh10, 1, Rectangle.RIGHT, LEFT);
                    // ---
                    addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT | Rectangle.BOTTOM, LEFT);
                    addColumn(table, 8, 1, "總計", fontCh10, 1, Rectangle.BOTTOM, LEFT);
                    addColumn(table, 6, 1, "件數：", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                    addColumn(table, 10, 1, formatBigDecimalToInteger(allRec), fontCh10, 1, Rectangle.BOTTOM, LEFT);
                    addColumn(table, 8, 1, "金額：", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                    addColumn(table, 18, 1, formatBigDecimalToInteger(allAmountAmt), fontCh10, 1, Rectangle.RIGHT | Rectangle.BOTTOM, LEFT);
                    // --
                    addEmptyRow(table, 1);
                    // --
                    addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                    addColumn(table, 20, 1, printDate, fontCh10, 0, LEFT);
                    // --
                    // 給付方式 為 匯票郵寄申請人 則顯示郵局
                    if (StringUtils.equalsIgnoreCase(caseData.getPayTyp(), ConstantKey.BAAPPBASE_PAYTYP_4)) {
                        addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 20, 1, "郵政儲金匯業局\r\n　儲匯管理處　", fontCh10, 0, LEFT);
                    }
                    else {
                        addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 20, 1, "土銀台北分行", fontCh10, 0, LEFT);
                    }

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 7);

                        addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT, LEFT);
                        addColumn(table, 8, 1, "本頁小計", fontCh10, 0, LEFT);
                        addColumn(table, 6, 1, "件數：", fontCh10, 0, RIGHT);
                        addColumn(table, 10, 1, formatBigDecimalToInteger(pageRec), fontCh10, 0, LEFT);
                        addColumn(table, 8, 1, "金額：", fontCh10, 0, RIGHT);
                        addColumn(table, 18, 1, formatBigDecimalToInteger(pageAmountAmt), fontCh10, 1, Rectangle.RIGHT, LEFT);
                        // ---
                        addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT | Rectangle.BOTTOM, LEFT);
                        addColumn(table, 8, 1, "　", fontCh10, 1, Rectangle.BOTTOM, LEFT);
                        addColumn(table, 6, 1, "　", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                        addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.BOTTOM, LEFT);
                        addColumn(table, 8, 1, "　", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                        addColumn(table, 18, 1, "　", fontCh10, 1, Rectangle.RIGHT | Rectangle.BOTTOM, LEFT);
                        // --
                        addEmptyRow(table, 1);
                        // --
                        addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 20, 1, printDate, fontCh10, 0, LEFT);
                        // --
                        // 給付方式 為 匯票郵寄申請人 則顯示郵局
                        if (StringUtils.equalsIgnoreCase(caseData.getPayTyp(), ConstantKey.BAAPPBASE_PAYTYP_4)) {
                            addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                            addColumn(table, 20, 1, "郵政儲金匯業局\r\n　儲匯管理處　", fontCh10, 0, LEFT);
                        }
                        else {
                            addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                            addColumn(table, 20, 1, "土銀台北分行", fontCh10, 0, LEFT);
                        }

                        document.add(table);

                        // 編號 + 1
                        recNo++;

                        // 本頁小計歸零
                        pageRec = new BigDecimal(0); // 本頁小計 - 件數
                        pageAmountAmt = new BigDecimal(0); // 本頁小計 - 金額

                        table = addHeader(caseList.get(i + 1)); // 下一筆
                    }
                    else {
                        // 編號 + 1
                        recNo++;

                        deleteRow(table, 7);
                    }
                }
            } // ] ... for (int i = 0; i < caseList.size(); i++)

            // 代扣補償金
            for (int i = 0; i < subCaseList.size(); i++) { // ... [
                MonthlyRpt10Type1RptCase caseData = subCaseList.get(i);

                // 第一筆加入表頭
                if (i == 0) {
                    table = addHeader(caseData);
                }

                // 本頁小計 - 件數
                pageRec = pageRec.add(new BigDecimal(1));
                // 本頁小計 - 金額
                if (caseData.getPayAmt() != null)
                    pageAmountAmt = pageAmountAmt.add(caseData.getPayAmt());
                // 總計 - 件數
                allRec = allRec.add(new BigDecimal(1));
                // 總計 - 金額
                if (caseData.getPayAmt() != null)
                    allAmountAmt = allAmountAmt.add(caseData.getPayAmt());

                // 列印資料
                // [
                addColumnAssignVAlignment(table, 4, 2, String.valueOf(recNo + 1), fontCh10, 1, CENTER, MIDDLE); // 編號
                addColumn(table, 13, 1, caseData.getApNoString(), fontCh10, 1, nLine1BorderStyle, LEFT); // 受理編號
                addColumn(table, 11, 1, StringUtils.rightPad(caseData.getIssuYmString(), 5, " ") + "　" + StringUtils.rightPad(caseData.getPayYmString(), 5, " "), fontCh10, 1, nLine1BorderStyle, LEFT); // 核定年月 給付年月
                addColumn(table, 10, 2, caseData.getBenName(), fontCh10, 1, LEFT); // 受款人抬頭
                addColumn(table, 7, 1, formatBigDecimalToInteger(((caseData.getPayAmt() == null) ? new BigDecimal(0) : caseData.getPayAmt())), fontCh10, 1, nLine1BorderStyle, RIGHT); // 金額
                addColumn(table, 6, 1, caseData.getChkDateString(), fontCh10, 1, nLine1BorderStyle, RIGHT); // 核定日期
                addColumn(table, 9, 1, "　", fontCh10, 1, nLine1BorderStyle, LEFT); // 支票號碼
                // ---
                addColumn(table, 13, 1, caseData.getBankString(), fontCh10, 1, nLine2BorderStyle, LEFT); // 帳 號
                addColumn(table, 11, 1, caseData.getBenIdn(), fontCh10, 1, nLine2BorderStyle, LEFT); // 身分證號
                addColumn(table, 7, 1, "　", fontCh10, 1, nLine2BorderStyle, RIGHT); // 新台幣：元
                addColumn(table, 6, 1, (caseData.getRptPage() == null) ? "" : caseData.getRptPage().toPlainString(), fontCh10, 1, nLine2BorderStyle, RIGHT); // 頁次行次
                addColumn(table, 9, 1, caseData.getCompName2(), fontCh10, 1, nLine2BorderStyle, LEFT); // 備 註
                // ]

                // 判段是否為最後一筆, 或達成換頁條件 (給付方式 + 受益人與事故者關係 + 相關單位名稱1)
                if (i == subCaseList.size() - 1 || !StringUtils.equals(StringUtils.trimToEmpty(caseData.getPayTyp()) + StringUtils.trimToEmpty(caseData.getBenEvtRel()) + StringUtils.trimToEmpty(caseData.getCompName1()),
                                StringUtils.trimToEmpty(subCaseList.get(i + 1).getPayTyp()) + StringUtils.trimToEmpty(subCaseList.get(i + 1).getBenEvtRel() + StringUtils.trimToEmpty(subCaseList.get(i + 1).getCompName1())))) {
                    // 塞滿空白行, 以讓表格美觀
                    // [
                    while (writer.fitsPage(table)) {
                        // [
                        addColumn(table, 4, 2, "　", fontCh10, 1, CENTER);
                        addColumn(table, 13, 1, "　", fontCh10, 1, nLine1BorderStyle, LEFT);
                        addColumn(table, 11, 1, "　", fontCh10, 1, nLine1BorderStyle, LEFT);
                        addColumn(table, 10, 2, "　", fontCh10, 1, LEFT);
                        addColumn(table, 7, 1, "　", fontCh10, 1, nLine1BorderStyle, RIGHT);
                        addColumn(table, 6, 1, "　", fontCh10, 1, nLine1BorderStyle, RIGHT);
                        addColumn(table, 9, 1, "　", fontCh10, 1, nLine1BorderStyle, LEFT);
                        // ---
                        addColumn(table, 13, 1, "　", fontCh10, 1, nLine2BorderStyle, LEFT);
                        addColumn(table, 11, 1, "　", fontCh10, 1, nLine2BorderStyle, LEFT);
                        addColumn(table, 7, 1, "　", fontCh10, 1, nLine2BorderStyle, RIGHT);
                        addColumn(table, 6, 1, "　", fontCh10, 1, nLine2BorderStyle, RIGHT);
                        addColumn(table, 9, 1, "　", fontCh10, 1, nLine2BorderStyle, LEFT);
                        // ]
                    }
                    // ]
                    deleteRow(table, 8); // 表尾有 5 行 + 前面回圈會多塞 1 筆, 故要刪 6 行

                    addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT, LEFT);
                    addColumn(table, 8, 1, "本頁小計", fontCh10, 0, LEFT);
                    addColumn(table, 6, 1, "件數：", fontCh10, 0, RIGHT);
                    addColumn(table, 10, 1, formatBigDecimalToInteger(pageRec), fontCh10, 0, LEFT);
                    addColumn(table, 8, 1, "金額：", fontCh10, 0, RIGHT);
                    addColumn(table, 18, 1, formatBigDecimalToInteger(pageAmountAmt), fontCh10, 1, Rectangle.RIGHT, LEFT);
                    // ---
                    addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT | Rectangle.BOTTOM, LEFT);
                    addColumn(table, 8, 1, "總計", fontCh10, 1, Rectangle.BOTTOM, LEFT);
                    addColumn(table, 6, 1, "件數：", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                    addColumn(table, 10, 1, formatBigDecimalToInteger(allRec), fontCh10, 1, Rectangle.BOTTOM, LEFT);
                    addColumn(table, 8, 1, "金額：", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                    addColumn(table, 18, 1, formatBigDecimalToInteger(allAmountAmt), fontCh10, 1, Rectangle.RIGHT | Rectangle.BOTTOM, LEFT);
                    // --
                    addEmptyRow(table, 1);
                    // --
                    addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                    addColumn(table, 20, 1, printDate, fontCh10, 0, LEFT);
                    // --
                    // 給付方式 為 匯票郵寄申請人 則顯示郵局
                    if (StringUtils.equalsIgnoreCase(caseData.getPayTyp(), ConstantKey.BAAPPBASE_PAYTYP_4)) {
                        addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 20, 1, "郵政儲金匯業局\r\n　儲匯管理處　", fontCh10, 0, LEFT);
                    }
                    else {
                        addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 20, 1, "土銀台北分行", fontCh10, 0, LEFT);
                    }

                    // 歸零
                    recNo = 0; // 編號
                    pageRec = new BigDecimal(0); // 本頁小計 - 件數
                    pageAmountAmt = new BigDecimal(0); // 本頁小計 - 金額
                    allRec = new BigDecimal(0); // 總計 - 件數
                    allAmountAmt = new BigDecimal(0); // 總計 - 金額

                    document.add(table);

                    if (i != subCaseList.size() - 1)
                        table = addHeader(subCaseList.get(i + 1)); // 下一筆
                }
                else {
                    // 測試塞下一筆 + 表尾時是否需要換頁
                    addColumnAssignVAlignment(table, 4, 2, String.valueOf(recNo + 2), fontCh10, 1, CENTER, MIDDLE); // 編號
                    addColumn(table, 13, 1, subCaseList.get(i + 1).getApNoString(), fontCh10, 1, nLine1BorderStyle, LEFT); // 受理編號
                    addColumn(table, 11, 1, StringUtils.rightPad(subCaseList.get(i + 1).getIssuYmString(), 5, " ") + "　" + StringUtils.rightPad(subCaseList.get(i + 1).getPayYmString(), 5, " "), fontCh10, 1, nLine1BorderStyle, LEFT); // 核定年月 給付年月
                    addColumn(table, 10, 2, subCaseList.get(i + 1).getBenName(), fontCh10, 1, LEFT); // 受款人抬頭
                    addColumn(table, 7, 1, formatBigDecimalToInteger(((subCaseList.get(i + 1).getPayAmt() == null) ? new BigDecimal(0) : subCaseList.get(i + 1).getPayAmt())), fontCh10, 1, nLine1BorderStyle, RIGHT); // 金額
                    addColumn(table, 6, 1, subCaseList.get(i + 1).getChkDateString(), fontCh10, 1, nLine1BorderStyle, RIGHT); // 核定日期
                    addColumn(table, 9, 1, "　", fontCh10, 1, nLine1BorderStyle, LEFT); // 支票號碼
                    // ---
                    addColumn(table, 13, 1, subCaseList.get(i + 1).getAccountNo(), fontCh10, 1, nLine2BorderStyle, LEFT); // 帳 號
                    addColumn(table, 11, 1, subCaseList.get(i + 1).getBenIdn(), fontCh10, 1, nLine2BorderStyle, LEFT); // 身分證號
                    addColumn(table, 7, 1, "　", fontCh10, 1, nLine2BorderStyle, RIGHT); // 新台幣：元
                    addColumn(table, 6, 1, (subCaseList.get(i + 1).getRptPage() == null) ? "" : subCaseList.get(i + 1).getRptPage().toPlainString(), fontCh10, 1, nLine2BorderStyle, RIGHT); // 頁次行次
                    addColumn(table, 9, 1, caseData.getCompName2(), fontCh10, 1, nLine2BorderStyle, LEFT); // 備 註
                    // ---
                    // 表尾
                    // ---
                    addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT, LEFT);
                    addColumn(table, 8, 1, "本頁小計", fontCh10, 0, LEFT);
                    addColumn(table, 6, 1, "件數：", fontCh10, 0, RIGHT);
                    addColumn(table, 10, 1, formatBigDecimalToInteger(pageRec), fontCh10, 0, LEFT);
                    addColumn(table, 8, 1, "金額：", fontCh10, 0, RIGHT);
                    addColumn(table, 18, 1, formatBigDecimalToInteger(pageAmountAmt), fontCh10, 1, Rectangle.RIGHT, LEFT);
                    // ---
                    addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT | Rectangle.BOTTOM, LEFT);
                    addColumn(table, 8, 1, "總計", fontCh10, 1, Rectangle.BOTTOM, LEFT);
                    addColumn(table, 6, 1, "件數：", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                    addColumn(table, 10, 1, formatBigDecimalToInteger(allRec), fontCh10, 1, Rectangle.BOTTOM, LEFT);
                    addColumn(table, 8, 1, "金額：", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                    addColumn(table, 18, 1, formatBigDecimalToInteger(allAmountAmt), fontCh10, 1, Rectangle.RIGHT | Rectangle.BOTTOM, LEFT);
                    // --
                    addEmptyRow(table, 1);
                    // --
                    addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                    addColumn(table, 20, 1, printDate, fontCh10, 0, LEFT);
                    // --
                    // 給付方式 為 匯票郵寄申請人 則顯示郵局
                    if (StringUtils.equalsIgnoreCase(caseData.getPayTyp(), ConstantKey.BAAPPBASE_PAYTYP_4)) {
                        addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 20, 1, "郵政儲金匯業局\r\n　儲匯管理處　", fontCh10, 0, LEFT);
                    }
                    else {
                        addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 20, 1, "土銀台北分行", fontCh10, 0, LEFT);
                    }

                    if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                        deleteRow(table, 7);

                        addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT, LEFT);
                        addColumn(table, 8, 1, "本頁小計", fontCh10, 0, LEFT);
                        addColumn(table, 6, 1, "件數：", fontCh10, 0, RIGHT);
                        addColumn(table, 10, 1, formatBigDecimalToInteger(pageRec), fontCh10, 0, LEFT);
                        addColumn(table, 8, 1, "金額：", fontCh10, 0, RIGHT);
                        addColumn(table, 18, 1, formatBigDecimalToInteger(pageAmountAmt), fontCh10, 1, Rectangle.RIGHT, LEFT);
                        // ---
                        addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.LEFT | Rectangle.BOTTOM, LEFT);
                        addColumn(table, 8, 1, "　", fontCh10, 1, Rectangle.BOTTOM, LEFT);
                        addColumn(table, 6, 1, "　", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                        addColumn(table, 10, 1, "　", fontCh10, 1, Rectangle.BOTTOM, LEFT);
                        addColumn(table, 8, 1, "　", fontCh10, 1, Rectangle.BOTTOM, RIGHT);
                        addColumn(table, 18, 1, "　", fontCh10, 1, Rectangle.RIGHT | Rectangle.BOTTOM, LEFT);
                        // --
                        addEmptyRow(table, 1);
                        // --
                        addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                        addColumn(table, 20, 1, printDate, fontCh10, 0, LEFT);
                        // --
                        // 給付方式 為 匯票郵寄申請人 則顯示郵局
                        if (StringUtils.equalsIgnoreCase(caseData.getPayTyp(), ConstantKey.BAAPPBASE_PAYTYP_4)) {
                            addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                            addColumn(table, 20, 1, "郵政儲金匯業局\r\n　儲匯管理處　", fontCh10, 0, LEFT);
                        }
                        else {
                            addColumn(table, 40, 1, "　", fontCh10, 0, LEFT);
                            addColumn(table, 20, 1, "土銀台北分行", fontCh10, 0, LEFT);
                        }

                        document.add(table);

                        // 編號 + 1
                        recNo++;

                        // 本頁小計歸零
                        pageRec = new BigDecimal(0); // 本頁小計 - 件數
                        pageAmountAmt = new BigDecimal(0); // 本頁小計 - 金額

                        table = addHeader(subCaseList.get(i + 1)); // 下一筆
                    }
                    else {
                        // 編號 + 1
                        recNo++;

                        deleteRow(table, 7);
                    }
                }
            } // ] ... for (int i = 0; i < subCaseList.size(); i++)

            document.close();
            // 20180103 輸出報表檔至主機上 因為FORTIY 修改寫法
            printToDisk((PropertyHelper.getProperty("rpt.path") + Encode.forJava(payCode) + "_MonthlyRpt10Type1_" + Encode.forJava(printDate) + ".pdf"), bao);
        }
        finally {
            document.close();
        }

        return bao;
    }
}
