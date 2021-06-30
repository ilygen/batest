package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;

import org.apache.commons.lang.StringUtils;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.OtherRpt01Case;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;

/**
 * 調卷/還卷單
 * 
 * @author Goston
 */
public class OtherRpt01Report extends ReportBase {

    private String printDate = ""; // 印表日期

    private String line = "-----------------------------------------------------------------------------------------------------------------------------------";

    public OtherRpt01Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(false);
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 30, 30, 60, 60);
        return document;
    }

    /**
     * 加入分隔線
     * 
     * @param table
     * @param icolspan Column Span
     * @throws Exception
     */
    public void addLine(Table table) throws Exception {
        addColumn(table, 40, 1, line, fontCh8, 0, CENTER);
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
            addColumn(table, 40, 1, "　", fontCh12, 0, LEFT); // 注意: 內容是全形空白
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

    public ByteArrayOutputStream doReport(UserBean userData, OtherRpt01Case caseData) throws Exception {
        try {
            document.open();

            document.newPage();

            // 建立表格
            Table table = createTable(40);
            table.setAutoFillEmptyCells(true);
            table.setAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            addColumn(table, 40, 1, "報表編號：BALP0D510", fontCh12, 0, LEFT);

            // 調卷單
            // [
            addColumn(table, 40, 1, RptTitleUtility.getGroupsTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+RptTitleUtility.getDivisionTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+"　調卷單", fontCh22, 0, CENTER);
            //addColumn(table, 40, 1, "給付處" + caseData.getDeptNameString() + "給付科　調卷單", fontCh22, 0, CENTER);
            // ---
            addColumn(table, 31, 1, " ", fontCh12, 0, LEFT);
            addColumn(table, 9, 1, "日期：" + printDate, fontCh12, 0, LEFT); // 調卷單的日期代系統日期
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 13, 1, "調卷給付種類：" + caseData.getPayKindString() + "年金給付", fontCh12, 0, LEFT);
            addColumn(table, 13, 1, "案件類別：" + caseData.getCaseTypStr() , fontCh12, 0, LEFT);
            addColumn(table, 14, 1, " " , fontCh12, 0, LEFT);
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 13, 1, "受理號碼：" + caseData.getApNoString(), fontCh12, 0, LEFT);
            addColumn(table, 13, 1, "核定年月：" + caseData.getIssuYmString(), fontCh12, 0, LEFT);
            if(StringUtils.isNotBlank(caseData.getProDate())){
                addColumn(table, 14, 1, "不給付日期：" + DateUtility.formatChineseDateString(caseData.getProDate(),false), fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 14, 1, "不給付日期：" , fontCh12, 0, LEFT);
            }
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 13, 1, "事故者姓名：" + StringUtils.defaultString(caseData.getEvtName()), fontCh12, 0, LEFT);
            addColumn(table, 13, 1, "身分證字號：" + StringUtils.defaultString(caseData.getEvtIdnNo()), fontCh12, 0, LEFT);
            addColumn(table, 14, 1, "出生日期：" + caseData.getEvtBrDateString(), fontCh12, 0, LEFT);
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 13, 1, "歸檔頁次：" + StringUtils.defaultString(caseData.getArcPg()), fontCh12, 0, LEFT);
            addColumn(table, 13, 1, "印表日期：" + caseData.getArcDateString(), fontCh12, 0, LEFT);
            addColumn(table, 14, 1, " ", fontCh12, 0, LEFT);
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 40, 1, "上列調卷案，敬請儘速調卷，並交給調卷人簽收。", fontCh12, 0, LEFT);
            // ---
            addColumn(table, 40, 1, "調卷人　"+RptTitleUtility.getGroupsTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+RptTitleUtility.getDivisionTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+"　員工編號：　　　　姓名：　　　　　　　　分機：　　　", fontCh12, 0, LEFT); // 空白都是全形
            //addColumn(table, 40, 1, "調卷人　給付處" + caseData.getDeptNameString() + "給付科　員工編號：　　　　姓名：　　　　　　　　分機：　　　", fontCh12, 0, LEFT); // 空白都是全形
            // ---
            addEmptyRow(table, 2); // 空白行 2 行
            // ---
            addColumn(table, 40, 1, "調卷人簽收章________________　調卷簽收日期________________", fontCh12, 0, CENTER);
            // ]
            // 調卷單

            // 分隔線
            addLine(table);
            // ---
            addEmptyRow(table, 1); // 空白行

            // 還卷單
            // [
            addColumn(table, 40, 1, RptTitleUtility.getGroupsTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+RptTitleUtility.getDivisionTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+"　還卷單", fontCh22, 0, CENTER);
            //addColumn(table, 40, 1, "給付處" + caseData.getDeptNameString() + "給付科　還卷單", fontCh22, 0, CENTER);
            // ---
            addColumn(table, 31, 1, " ", fontCh12, 0, LEFT);
            addColumn(table, 9, 1, "日期：", fontCh12, 0, LEFT); // 還卷單的日期由使用者自行填
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 13, 1, "調卷給付種類：" + caseData.getPayKindString() + "年金給付", fontCh12, 0, LEFT);
            addColumn(table, 13, 1, "案件類別：" + caseData.getCaseTypStr() , fontCh12, 0, LEFT);
            addColumn(table, 14, 1, " " , fontCh12, 0, LEFT);
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 13, 1, "受理號碼：" + caseData.getApNoString(), fontCh12, 0, LEFT);
            addColumn(table, 13, 1, "核定年月：" + caseData.getIssuYmString(), fontCh12, 0, LEFT);
            if(StringUtils.isNotBlank(caseData.getProDate())){
                addColumn(table, 14, 1, "不給付日期：" + DateUtility.formatChineseDateString(caseData.getProDate(),false), fontCh12, 0, LEFT);
            }else{
            	addColumn(table, 14, 1, "不給付日期：" , fontCh12, 0, LEFT);
            }
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 13, 1, "事故者姓名：" + StringUtils.defaultString(caseData.getEvtName()), fontCh12, 0, LEFT);
            addColumn(table, 13, 1, "身分證字號：" + StringUtils.defaultString(caseData.getEvtIdnNo()), fontCh12, 0, LEFT);
            addColumn(table, 14, 1, "出生日期：" + caseData.getEvtBrDateString(), fontCh12, 0, LEFT);
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 13, 1, "歸檔頁次：" + StringUtils.defaultString(caseData.getArcPg()), fontCh12, 0, LEFT);
            addColumn(table, 13, 1, "印表日期：" + caseData.getArcDateString(), fontCh12, 0, LEFT);
            addColumn(table, 14, 1, " ", fontCh12, 0, LEFT);
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 40, 1, "上列還卷案，敬請儘速歸檔，並請歸檔人歸檔後簽收回覆。", fontCh12, 0, LEFT);
            // ---
            addColumn(table, 40, 1, "還卷者　"+RptTitleUtility.getGroupsTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+RptTitleUtility.getDivisionTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+"　員工編號：　　　　姓名：　　　　　　　　分機：　　　", fontCh12, 0, LEFT); // 空白都是全形
            //addColumn(table, 40, 1, "還卷者　給付處" + caseData.getDeptNameString() + "給付科　員工編號：　　　　姓名：　　　　　　　　分機：　　　", fontCh12, 0, LEFT); // 空白都是全形
            // ---
            addEmptyRow(table, 2); // 空白行 2 行
            // ---
            addColumn(table, 40, 1, "歸檔人簽收章________________　歸檔簽收日期________________", fontCh12, 0, CENTER);
            // ]
            // 還卷單
            
            document.add(table);

            document.close();
        }
        finally {
            document.close();
        }

        return bao;
    }

}
