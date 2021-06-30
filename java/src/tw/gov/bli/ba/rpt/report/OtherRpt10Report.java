package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.OtherRpt10Case;
import tw.gov.bli.ba.rpt.cases.OtherRpt10ClassCase;
import tw.gov.bli.ba.rpt.cases.DecisionRpt02Case;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;

/**
 * 穿透案件統計表
 * 
 * @author Kiyomi
 */
public class OtherRpt10Report extends ReportBase {
    private String printDate = ""; // 印表日期
    private static int PAGE_ROW_SIZE = 25;// 每25筆要換頁

    public OtherRpt10Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(false);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        return document;
    }

    /**
     * 增加空白行
     * 
     * @param table
     * @param rowCount 行次序號
     * @throws Exception
     */
    public void addEmptyRow(Table table, int rowCount) throws Exception {
        // addColumnAssignPhraseSizeTextRise(table, 6, 1, Integer.toString(rowCount), fontCh12, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 6, 1, "　", fontCh12, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 12, 1, "　", fontCh12, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 10, 1, "　", fontCh12, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 4, 1, "　", fontCh12, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 6, 1, "　", fontCh12, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 12, 1, "　", fontCh12, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 10, 1, "　", fontCh12, 15, 3f, 1, CENTER);

    }

    /**
     * 建立多行的 Column
     * 
     * @param table : 欲填入欄位的 table 元件
     * @param icolspan : column span 若無填1
     * @param irowspan : row span 若無填1
     * @param str : 欄位內容字串
     * @param font : 字型
     * @param phraseSize : phrase 的大小
     * @param textRise: 文字離底線的距離
     * @param borderWidth : 欄位 border 的寬度
     * @param hAlignment : 水平對齊方式
     * @throws BadElementException
     */
    public void addColumnAssignPhraseSizeTextRise(Table table, int icolspan, int irowspan, String str, Font font, int phraseSize, float testRise, int borderWidth, int hAlignment) throws BadElementException {
        Phrase phrase = new Phrase(phraseSize);
        Chunk chunk = new Chunk(StringUtils.defaultString(str), font);
        chunk.setTextRise(testRise);
        phrase.add(chunk);

        Cell cell = new Cell(phrase);
        cell.setBorderWidth(borderWidth);
        cell.setHorizontalAlignment(hAlignment);

        if (icolspan > 1)
            cell.setColspan(icolspan);

        if (irowspan > 1)
            cell.setRowspan(irowspan);

        table.addCell(cell);
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

    // 取得給付別中文說明
    private String getPayCodeStr(String payCode) {
        if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_L, payCode)) {
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_L;
        }
        else if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_K, payCode)) {
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_K;
        }
        else if (StringUtils.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S, payCode)) {
            return ConstantKey.BAAPPBASE_PAGE_PAYKIND_STR_S;
        }
        else {
            return "";
        }
    }

    /**
     * 建立表頭
     * 
     * @return
     * @throws Exception
     */
    public Table addHeader(String issuYm, String payCode, String chkMan) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(60);

        addColumn(table, 11, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 38, 1, RptTitleUtility.getOrgTitle(DateUtility.getNowWestDate()) + "　" + RptTitleUtility.getGroupsTitle(payCode, DateUtility.getNowWestDate()), fontCh18b, 0, CENTER);
        addColumn(table, 11, 1, "", fontCh10, 0, LEFT);
        // ---
        addColumn(table, 60, 1, "穿透案件統計表", fontCh18b, 0, CENTER);
        // ---
        addColumn(table, 48, 1, "", fontCh13, 0, LEFT);
        addColumn(table, 12, 1, "頁次：" + StringUtility.chtLeftPad(String.valueOf(writer.getPageNumber()), 4, "0"), fontCh13, 0, LEFT);
        // ---
        addColumn(table, 48, 1, "", fontCh13, 0, LEFT);
        addColumn(table, 12, 1, "日期：" + printDate, fontCh13, 0, LEFT);
        // ---
        addColumn(table, 20, 1, "給付別：" + getPayCodeStr(payCode), fontCh13, 0, LEFT);
        addColumn(table, 40, 1, "", fontCh13, 0, LEFT);
        // ---
        addColumn(table, 20, 1, "核定年月：" + DateUtility.formatChineseYearMonthString(issuYm, false), fontCh13, 0, LEFT);
        addColumn(table, 40, 1, "初核人員：" + chkMan, fontCh13, 0, LEFT);
        // ---
        addColumn(table, 60, 1, "", fontCh13, 0, LEFT);
        addColumn(table, 60, 1, "", fontCh13, 0, LEFT);
        // ---
        addColumnAssignPhraseSizeTextRise(table, 6, 1, "序號", fontCh13, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 12, 1, "受理編號", fontCh13, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 10, 1, "種類", fontCh13, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 4, 1, "　", fontCh13, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 6, 1, "序號", fontCh13, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 12, 1, "受理編號", fontCh13, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 10, 1, "種類", fontCh13, 15, 3f, 1, CENTER);

        return table;
    }
    
    /**
     * 建立表尾
     * 
     * @param table
     * @param caseData
     * @return
     * @throws Exception
     */
    public Table addFooter(Table table, Integer totalCount) throws Exception {

        addColumn(table, 60, 1, "本次統計共" + totalCount.toString() + "件", fontCh13, 0, LEFT);
        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<OtherRpt10ClassCase> printList, String issuYm, String payCode, int totalCount) throws Exception {
        try {
            document.open();
            Table table = null;

            for (OtherRpt10ClassCase caseData : printList) {
                int rowCount = 0;// 本頁列印筆數
                table = addHeader(issuYm, payCode, caseData.getChkMan());

                for (int i = 0; i < caseData.getChkManOtherRptDataList().size(); i++) {

                    OtherRpt10Case dataCase = caseData.getChkManOtherRptDataList().get(i);
                    if ((table == null) || (rowCount > 0 && rowCount % PAGE_ROW_SIZE == 0)) {
                        if (table != null) {
                            document.add(table);
                            table = addHeader(issuYm, payCode, caseData.getChkMan());
                        }
                        rowCount = 0;
                    }
                    rowCount++;
                    // addColumnAssignPhraseSizeTextRise(table, 6, 1, Integer.toString(rowCount), fontCh12, 15, 3f, 1, CENTER);
                    addColumnAssignPhraseSizeTextRise(table, 6, 1, dataCase.getSeqNo(), fontCh12, 15, 3f, 1, CENTER);
                    addColumnAssignPhraseSizeTextRise(table, 12, 1, dataCase.getApNo(), fontCh12, 15, 3f, 1, CENTER);
                    addColumnAssignPhraseSizeTextRise(table, 10, 1, dataCase.getCaseTypString(), fontCh12, 15, 3f, 1, CENTER);
                    addColumnAssignPhraseSizeTextRise(table, 4, 1, "　", fontCh13, 15, 3f, 1, CENTER);
                    addColumnAssignPhraseSizeTextRise(table, 6, 1, dataCase.getSeqNo1(), fontCh12, 15, 3f, 1, CENTER);
                    addColumnAssignPhraseSizeTextRise(table, 12, 1, dataCase.getApNo1(), fontCh12, 15, 3f, 1, CENTER);
                    addColumnAssignPhraseSizeTextRise(table, 10, 1, dataCase.getCaseTypString1(), fontCh12, 15, 3f, 1, CENTER);

                }

                // 補空白行補到滿
                while ((rowCount) % PAGE_ROW_SIZE != 0) {
                    rowCount++;
                    addEmptyRow(table, rowCount);
                }

                table = addFooter(table, totalCount);
                document.add(table);

            }
            
//            if (table != null) {
//                table = addFooter(table, totalCount);
//                document.add(table);
//            }
            

            document.close();
        }
        finally {
            document.close();
        }

        return bao;
    }
}
