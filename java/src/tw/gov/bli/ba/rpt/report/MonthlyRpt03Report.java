package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt03Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt03DetailCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

/**
 * 月核定差異統計表
 * 
 * @author Goston
 */
public class MonthlyRpt03Report extends ReportBase {
    private String printDate = ""; // 印表日期

    private final int footerLines = 6; // 表尾行數

    public MonthlyRpt03Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(false);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
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
            addColumn(table, 40, 1, "　", fontCh11, 0, LEFT); // 注意: 內容是全形空白, 字型大小為 11
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
    public Table addHeader(MonthlyRpt03Case caseData) throws Exception {
        document.newPage();

        // 建立表格
        Table table = createTable(40);
        table.setAutoFillEmptyCells(true);
        table.setAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.setPadding(3);

        addColumn(table, 10, 1, "報表編號：BALP0D330", fontCh11, 0, LEFT);
        addColumn(table, 20, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate())+"　"+RptTitleUtility.getGroupsTitle(caseData.getPayCode(), DateUtility.getNowWestDate()), fontCh18, 0, CENTER);
        //addColumn(table, 20, 1, "勞工保險局 給 付 處", fontCh18, 0, CENTER);
        addColumn(table, 10, 1, " ", fontCh11, 0, LEFT);
        // ---
        addColumn(table, 40, 1, "月核定差異統計表", fontCh18, 0, CENTER);
        // ---
        addColumn(table, 15, 1, "給付種類：" + StringUtils.defaultString(caseData.getPayCodeName()), fontCh11, 0, LEFT);
        addColumn(table, 18, 1, "單位：件　", fontCh11, 0, RIGHT);
        addColumn(table, 7, 1, "印表日期：" + printDate, fontCh11, 0, LEFT);
        // ---
        addColumn(table, 3, 2, "核定年月", fontCh11, 1, CENTER);
        addColumn(table, 3, 2, "受理件數\r\n（註1）", fontCh11, 1, CENTER);
        addColumn(table, 20, 1, "合格案（註2）", fontCh11, 1, CENTER);
        addColumn(table, 6, 1, "新案（註3~4）", fontCh11, 1, CENTER);
        addColumn(table, 8, 1, "合格舊案狀態改變（註5~6）", fontCh11, 1, CENTER);

        addColumn(table, 3, 1, "新案", fontCh11, 1, CENTER);
        addColumn(table, 3, 1, "續發案", fontCh11, 1, CENTER);
        addColumn(table, 4, 1, "停發後續發案", fontCh11, 1, CENTER);
        addColumn(table, 3, 1, "補發案", fontCh11, 1, CENTER);
        addColumn(table, 4, 1, "停發後補發案", fontCh11, 1, CENTER);
        addColumn(table, 3, 1, "合計", fontCh11, 1, CENTER);
        addColumn(table, 3, 1, "待處理", fontCh11, 1, CENTER);
        addColumn(table, 3, 1, "不合格", fontCh11, 1, CENTER);
        addColumn(table, 4, 1, "改待處理", fontCh11, 1, CENTER);
        addColumn(table, 4, 1, "改不合格", fontCh11, 1, CENTER);
        // ---

        return table;
    }

    /**
     * 建立表尾
     * 
     * @param table
     */
    public void addFooter(Table table) throws Exception {
        addColumnAssignVAlignment(table, 2, 6, "註：", fontCh11, 0, RIGHT, TOP);

        addColumnAssignVAlignment(table, 38, 1, "1.受理件數：核定年月之前一月受理件數。", fontCh11, 0, LEFT, TOP);
        addColumnAssignVAlignment(table, 38, 1, "2.合格案之各案類，定義同撥付總表。", fontCh11, 0, LEFT, TOP);
        addColumnAssignVAlignment(table, 38, 1, "3.新案待處理：核定年月之前一月受理，該核定年月未核付且為待處理狀態之案件數。", fontCh11, 0, LEFT, TOP);
        addColumnAssignVAlignment(table, 38, 1, "4.新案不合格：核定年月之前一月受理，為不合格狀態之案件數。", fontCh11, 0, LEFT, TOP);
        addColumnAssignVAlignment(table, 38, 1, "5.合格改待處理：上一核定年月為合格狀態，本核定年月為待處理狀態之案件數。", fontCh11, 0, LEFT, TOP);
        addColumnAssignVAlignment(table, 38, 1, "6.合格改不合格：上一核定年月為合格狀態，本核定年月為不合格狀態之案件數。", fontCh11, 0, LEFT, TOP);
    }

    public ByteArrayOutputStream doReport(UserBean userData, MonthlyRpt03Case caseData) throws Exception {
        try {
            boolean needPrintFooter = true; // 結束後是否需印表尾

            document.open();

            // 表頭
            Table table = addHeader(caseData);

            // 計算 Loop 要跑幾次 (輸入的核定年月 到 核定年 - 1 的 12 月)
            // caseData 中的年月是西元年月
            int nLoopCount = Integer.parseInt(StringUtils.substring(caseData.getIssuYmEnd(), 4, 6)) + 1;

            for (int i = 0; i < nLoopCount; i++) { // ... [
                String issuYm = "";
                if (i == 0)
                    issuYm = caseData.getIssuYmBegin();
                else
                    issuYm = StringUtils.substring(caseData.getIssuYmEnd(), 0, 4) + StringUtils.leftPad(String.valueOf(i), 2, "0");

                if (!caseData.getDetailMap().containsKey(issuYm)) { // 沒有該核定年月的資料
                    // 塞資料
                    addColumn(table, 3, 1, DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(issuYm), false), fontCh11, 1, CENTER); // 核定年月
                    addColumn(table, 3, 1, " ", fontCh11, 1, CENTER); // 受理件數
                    addColumn(table, 3, 1, " ", fontCh11, 1, CENTER); // 合格案 - 新案
                    addColumn(table, 3, 1, " ", fontCh11, 1, CENTER); // 合格案 - 續發案
                    addColumn(table, 4, 1, " ", fontCh11, 1, CENTER); // 合格案 - 停發後續發案
                    addColumn(table, 3, 1, " ", fontCh11, 1, CENTER); // 合格案 - 補發案
                    addColumn(table, 4, 1, " ", fontCh11, 1, CENTER); // 合格案 - 停發後補發案
                    addColumn(table, 3, 1, " ", fontCh11, 1, CENTER); // 合格案 - 合計
                    addColumn(table, 3, 1, " ", fontCh11, 1, CENTER); // 新案 - 待處理
                    addColumn(table, 3, 1, " ", fontCh11, 1, CENTER); // 新案 - 不合格
                    addColumn(table, 4, 1, " ", fontCh11, 1, CENTER); // 合格舊案狀態改變 - 改待處理
                    addColumn(table, 4, 1, " ", fontCh11, 1, CENTER); // 合格舊案狀態改變 - 改不合格
                }
                else { // 有該核定年月的資料
                    MonthlyRpt03DetailCase detailCaseData = caseData.getDetailMap().get(issuYm);

                    // 計算合格案合計
                    // [
                    BigDecimal qualifyCount = new BigDecimal(0);
                    if (detailCaseData.getQualify1() != null)
                        qualifyCount = qualifyCount.add(detailCaseData.getQualify1());
                    if (detailCaseData.getQualify2() != null)
                        qualifyCount = qualifyCount.add(detailCaseData.getQualify2());
                    if (detailCaseData.getQualify3() != null)
                        qualifyCount = qualifyCount.add(detailCaseData.getQualify3());
                    if (detailCaseData.getQualify4() != null)
                        qualifyCount = qualifyCount.add(detailCaseData.getQualify4());
                    if (detailCaseData.getQualify5() != null)
                        qualifyCount = qualifyCount.add(detailCaseData.getQualify5());
                    // ]

                    // 塞資料
                    addColumn(table, 3, 1, detailCaseData.getIssuYmString(), fontCh11, 1, CENTER); // 核定年月
                    addColumn(table, 3, 1, formatBigDecimalToInteger(detailCaseData.getApCount()), fontCh11, 1, CENTER); // 受理件數
                    addColumn(table, 3, 1, formatBigDecimalToInteger(detailCaseData.getQualify1()), fontCh11, 1, CENTER); // 合格案 - 新案
                    addColumn(table, 3, 1, formatBigDecimalToInteger(detailCaseData.getQualify2()), fontCh11, 1, CENTER); // 合格案 - 續發案
                    addColumn(table, 4, 1, formatBigDecimalToInteger(detailCaseData.getQualify3()), fontCh11, 1, CENTER); // 合格案 - 停發後續發案
                    addColumn(table, 3, 1, formatBigDecimalToInteger(detailCaseData.getQualify4()), fontCh11, 1, CENTER); // 合格案 - 補發案
                    addColumn(table, 4, 1, formatBigDecimalToInteger(detailCaseData.getQualify5()), fontCh11, 1, CENTER); // 合格案 - 停發後補發案
                    addColumn(table, 3, 1, formatBigDecimalToInteger(qualifyCount), fontCh11, 1, CENTER); // 合格案 - 合計
                    addColumn(table, 3, 1, formatBigDecimalToInteger(detailCaseData.getNewCase1()), fontCh11, 1, CENTER); // 新案 - 待處理
                    addColumn(table, 3, 1, formatBigDecimalToInteger(detailCaseData.getNewCase2()), fontCh11, 1, CENTER); // 新案 - 不合格
                    addColumn(table, 4, 1, formatBigDecimalToInteger(detailCaseData.getDisQualify1()), fontCh11, 1, CENTER); // 合格舊案狀態改變 - 改待處理
                    addColumn(table, 4, 1, formatBigDecimalToInteger(detailCaseData.getDisQualify2()), fontCh11, 1, CENTER); // 合格舊案狀態改變 - 改不合格
                }

                // 測試塞下一筆 + 表尾時是否需要換頁
                // [
                if (i != nLoopCount - 1) {
                    addEmptyRow(table, 1); // 下一筆
                }
                addEmptyRow(table, footerLines); // 表尾

                if (!writer.fitsPage(table)) { // 超過一頁所能顯示的行數
                    if (i != nLoopCount - 1) {
                        deleteRow(table, 1);
                    }
                    deleteRow(table, footerLines);

                    addFooter(table); // 加入表尾
                    document.add(table);

                    needPrintFooter = false;

                    if (i != nLoopCount - 1) { // 最後一筆就不用再換頁了
                        table = addHeader(caseData);
                    }
                }
                else {
                    if (i != nLoopCount - 1) {
                        deleteRow(table, 1);
                    }
                    deleteRow(table, footerLines);

                    needPrintFooter = true;
                }
                // ]
            } // ] ... for (int i = 0; i < nLoopCount; i++)

            if (needPrintFooter) {
                addFooter(table); // 加入表尾
                document.add(table);
            }

            document.close();
        }
        finally {
            document.close();
        }

        return bao;
    }
}
