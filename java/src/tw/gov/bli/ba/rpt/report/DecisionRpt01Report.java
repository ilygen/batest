package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baarclist;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.DecisionRpt01Case;
import tw.gov.bli.ba.util.DateUtility;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;

public class DecisionRpt01Report extends ReportBase {
    private String printDate = ""; // 印表日期
    private int page = 0;// 頁次
    private int arcPgS = 900;// 頁次
    private String decisionSdateStr;// 決行日期起
    private String decisionEdateStr;// 決行日期迄
    private String payCode;// 給付別
    private static int PAGE_ROW_SIZE = 20;// 每20筆要換頁

    public DecisionRpt01Report(String payCode, String decisionSdate, String decisionEdate, BigDecimal arcPg, BigDecimal arcPg1) throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(true); // 中文年月日
        if (StringUtils.length(decisionSdate) == 8) {
            decisionSdateStr = DateUtility.formatChineseDateString(DateUtility.changeDateType(decisionSdate), true);
        }
        else if (StringUtils.length(decisionSdate) == 7) {
            decisionSdateStr = DateUtility.formatChineseDateString(decisionSdate, true);
        }

        if (StringUtils.length(decisionEdate) == 8) {
            decisionEdateStr = DateUtility.formatChineseDateString(DateUtility.changeDateType(decisionEdate), true);
        }
        else if (StringUtils.length(decisionEdate) == 7) {
            decisionEdateStr = DateUtility.formatChineseDateString(decisionEdate, true);
        }
        this.payCode = payCode;
        this.page = arcPg.intValue();
        
        if (arcPg1.intValue() != 0)
            this.arcPgS = arcPg1.intValue();
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 10, 10, 30, 30);
        return document;
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
     * 增加空白行
     * 
     * @param table
     * @param rowCount 行次序號
     * @throws Exception
     */
    public void addEmptyRow(Table table, int rowCount) throws Exception {
        addColumnAssignPhraseSizeTextRise(table, 3, 1, Integer.toString(rowCount), fontCh12, 24, 8f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 15, 1, "", fontCh12, 24, 8f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 14, 1, "", fontCh12, 24, 8f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 7, 1, "", fontCh12, 24, 8f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 7, 1, "", fontCh12, 24, 8f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 14, 1, "", fontCh12, 24, 8f, 1, CENTER);
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
    public void addColumnAssignPhraseSizeTextRise(Table table, int icolspan, int irowspan, String str, Font font, int phraseSize, float textRise, int borderWidth, int hAlignment) throws BadElementException {
        Phrase phrase = new Phrase(phraseSize);
        Chunk chunk = new Chunk(StringUtils.defaultString(str), font);
        chunk.setTextRise(textRise);
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

    // 取得標題案件狀態中文說明
    private String getCaseTypStr(String caseTyp) {
        if (StringUtils.equals("1", caseTyp) || StringUtils.equals("2", caseTyp)) {
            return "給付案";
        }
        else if (StringUtils.equals("3", caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_3;
        }
        else if (StringUtils.equals("4", caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_4;
        }
        else if (StringUtils.equals("6", caseTyp)) {
            return ConstantKey.BAAPPBASE_CASETYP_STR_6;
        }
        else {
            return "";
        }
    }

    // 取得左上角報表序號
    private String getRptSeqno(String caseTyp) {
        if (StringUtils.equals("1", caseTyp) || StringUtils.equals("2", caseTyp)) {
            return "表一";
        }
        else if (StringUtils.equals("3", caseTyp)) {
            return "表二";
        }
        else if (StringUtils.equals("4", caseTyp)) {
            return "表三";
        }
        else if (StringUtils.equals("6", caseTyp)) {
            return "表四";
        }
        else {
            return "";
        }
    }

    // 取得給付別中文說明
    private String getPayCodeStr() {
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
     * 建立表尾
     * 
     * @param table
     * @param caseData
     * @return
     * @throws Exception
     */
    public Table addFooter(Table table, String casetyp) throws Exception {
        if (StringUtils.equals("3", casetyp)) {
            addColumn(table, 60, 1, "註：帶入行政支援記錄函別21之承辦日期。", fontCh13, 0, LEFT);
        }
        else if (StringUtils.equals("6", casetyp)) {
            addColumn(table, 60, 1, "註：帶入行政支援記錄函別21，處理註記02之承辦日期。", fontCh13, 0, LEFT);
        }
        return table;
    }

    /**
     * 建立表頭
     * 
     * @return
     * @throws Exception
     */
    public Table addHeader(DecisionRpt01Case caseData) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(60);
        // ---
        addColumn(table, 60, 1, "【" + getRptSeqno(caseData.getCaseTyp()) + "】", fontCh11, 0, LEFT);
        // ---
        addColumn(table, 60, 1, "勞保年金給付歸檔清單", fontCh18b, 0, CENTER);
        // ---
        if (StringUtils.equals("1", caseData.getCaseTyp()) || StringUtils.equals("2", caseData.getCaseTyp())) {
            addColumn(table, 26, 1, "給付別：" + getPayCodeStr(), fontCh13, 0, LEFT);
            addColumn(table, 34, 1, "【" + getCaseTypStr(caseData.getCaseTyp()) + "】", fontCh13, 0, LEFT);
        }
        else if (StringUtils.equals("3", caseData.getCaseTyp())) {
            addColumn(table, 26, 1, "給付別：" + getPayCodeStr(), fontCh13, 0, LEFT);
            addColumn(table, 34, 1, "【" + getCaseTypStr(caseData.getCaseTyp()) + "】", fontCh13, 0, LEFT);
        }
        if (StringUtils.equals("4", caseData.getCaseTyp())) {
            addColumn(table, 27, 1, "給付別：" + getPayCodeStr(), fontCh13, 0, LEFT);
            addColumn(table, 33, 1, "【" + getCaseTypStr(caseData.getCaseTyp()) + "】", fontCh13, 0, LEFT);
        }
        if (StringUtils.equals("6", caseData.getCaseTyp())) {
            addColumn(table, 25, 1, "給付別：" + getPayCodeStr(), fontCh13, 0, LEFT);
            addColumn(table, 35, 1, "【" + getCaseTypStr(caseData.getCaseTyp()) + "】", fontCh13, 0, LEFT);
        }
        // ---
        addColumn(table, 30, 1, "核定年月：" + caseData.getIssuYmString(), fontCh13, 0, LEFT);
        addColumn(table, 10, 1, "", fontCh13, 0, LEFT);
        addColumn(table, 20, 1, "歸檔頁次：第" + StringUtils.leftPad(Integer.toString(page), 3, "0") + "頁", fontCh13, 0, LEFT);
        // ---
        addColumn(table, 35, 1, "決行日期：" + decisionSdateStr + "～" + decisionEdateStr, fontCh13, 0, LEFT);
        addColumn(table, 5, 1, "", fontCh13, 0, LEFT);
        addColumn(table, 20, 1, "印表日期：" + printDate, fontCh13, 0, LEFT);
        // ---
        addColumnAssignPhraseSizeTextRise(table, 3, 1, "行\r\n次", fontCh13, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 15, 1, "受理編號", fontCh13, 15, -6f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 14, 1, "事故者姓名", fontCh13, 15, -6f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 7, 1, "初核\r\n人員", fontCh13, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 7, 1, "決行\r\n人員", fontCh13, 15, 3f, 1, CENTER);
        addColumnAssignPhraseSizeTextRise(table, 14, 1, "備註", fontCh13, 15, -6f, 1, CENTER);

        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<DecisionRpt01Case> caseList) throws Exception {
        try {
            document.open();
            Table table = null;
            int rowCount = 0;// 本頁列印筆數
            String footerCasetyp = "";
            String sFlag = "N";
            for (int i = 0; i < caseList.size(); i++) {
                DecisionRpt01Case dataCase = caseList.get(i);
                
                if (StringUtils.substring(dataCase.getApNo().trim(), 0, 1).equals("S") && dataCase.getCaseTyp().equals("3") && sFlag.equals("N")){
                    page = arcPgS;
                    sFlag = "Y";
                } else if (StringUtils.substring(dataCase.getApNo().trim(), 0, 1).equals("S") && !dataCase.getCaseTyp().equals("3") && sFlag.equals("Y")){
                    sFlag = "N";
                }
                
                
                if ((table == null) || (rowCount > 0 && rowCount % PAGE_ROW_SIZE == 0)) {
                    if (table != null) {
                        addFooter(table, footerCasetyp);
                        document.add(table);
                    }

                    rowCount = 0;
                    page++;
                    table = addHeader(dataCase);
                    footerCasetyp = dataCase.getCaseTyp();
                }
                rowCount++;
                addColumnAssignPhraseSizeTextRise(table, 3, 1, Integer.toString(rowCount), fontCh12, 22, 6f, 1, CENTER);
                addColumnAssignPhraseSizeTextRise(table, 15, 1, dataCase.getApNoString(), fontCh12, 22, 6f, 1, CENTER);
                addColumnAssignPhraseSizeTextRise(table, 14, 1, dataCase.getEvtName(), fontCh12, 22, 6f, 1, CENTER);
                addColumnAssignPhraseSizeTextRise(table, 7, 1, dataCase.getChkMan(), fontCh12, 22, 6f, 1, CENTER);
                addColumnAssignPhraseSizeTextRise(table, 7, 1, dataCase.getExeMan(), fontCh12, 22, 6f, 1, CENTER);
                addColumnAssignPhraseSizeTextRise(table, 14, 1, dataCase.getNote(), fontCh12, 22, 6f, 1, LEFT);
                dataCase.setArcPg(BigDecimal.valueOf(page));
                if (i == caseList.size() - 1) {
                    // 補空白行補到滿
                    while ((rowCount) % PAGE_ROW_SIZE != 0) {
                        rowCount++;
                        addEmptyRow(table, rowCount);
                    }
                    addFooter(table, footerCasetyp);
                    document.add(table);
                }
                // 不同的CASETYP 需要換頁
                else {
                    DecisionRpt01Case nextDataCase = caseList.get(i + 1);
                    if (isNewPageByCaseTyp(dataCase, nextDataCase)) {
                        
                        if (StringUtils.substring(nextDataCase.getApNo().trim(), 0, 1).equals("S") && nextDataCase.getCaseTyp().equals("3") && sFlag.equals("N")){
                            page = arcPgS;
                            sFlag = "Y";
                        } else if (StringUtils.substring(nextDataCase.getApNo().trim(), 0, 1).equals("S") && !nextDataCase.getCaseTyp().equals("3") && sFlag.equals("Y")){
                            sFlag = "N";
                        }                        
                        
                        // 補空白行補到滿
                        while ((rowCount) % PAGE_ROW_SIZE != 0) {
                            rowCount++;
                            addEmptyRow(table, rowCount);
                        }
                        if (table != null) {
                            addFooter(table, footerCasetyp);
                            document.add(table);
                        }
                        rowCount = 0;
                        page++;
                        table = addHeader(nextDataCase);
                        footerCasetyp = nextDataCase.getCaseTyp();
                    }
                }
            }
            document.close();
        }
        finally {
            document.close();
        }

        return bao;
    }

    /**
     * 不同的CASETYP 需要換頁 CASETYP 1,2 不須換頁
     * 
     * @param dataCase
     * @param naxtDataCase
     */
    private boolean isNewPageByCaseTyp(DecisionRpt01Case dataCase, DecisionRpt01Case nextDataCase) {
        if (StringUtils.equals("1", dataCase.getCaseTyp()) && StringUtils.equals("2", nextDataCase.getCaseTyp())) {
            return false;
        }
        else if (StringUtils.equals("2", dataCase.getCaseTyp()) && StringUtils.equals("1", nextDataCase.getCaseTyp())) {
            return false;
        }
        else if (StringUtils.equals(dataCase.getCaseTyp(), nextDataCase.getCaseTyp())) {
            return false;
        }
        else {
            return true;
        }

    }
        
}
