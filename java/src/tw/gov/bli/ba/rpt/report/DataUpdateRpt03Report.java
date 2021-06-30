package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.DataUpdateRpt03Case;
import tw.gov.bli.ba.rpt.cases.DataUpdateRpt03DetailCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;

/**
 * 紓困貸款抵銷情形照會單
 * 
 * @author Goston
 */
public class DataUpdateRpt03Report extends ReportBase {
    private String printDate = ""; // 印表日期
    
    private String line = "-----------------------------------------------------------------------------------------------------------------------------------";

    public DataUpdateRpt03Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(true); // 中文民國年月日字串
    }

    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
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

    public ByteArrayOutputStream doReport(UserBean userData, DataUpdateRpt03Case caseData) throws Exception {
        try {
            document.open();

            List<DataUpdateRpt03DetailCase> detailList = caseData.getDetailList();

            // 一筆明細一頁
            for (DataUpdateRpt03DetailCase detailCaseData : detailList) {
                document.newPage();

                // 建立表格
                Table table = createTable(40);
                table.setAutoFillEmptyCells(true);
                table.setAlignment(Element.ALIGN_CENTER);
                table.setPadding(5);

                // 報表上半部
                // [
                addColumn(table, 40, 1, "報表編號：BALP0D130", fontCh11, 0, LEFT);
                // ---
                addColumn(table, 40, 1, "紓困貸款抵銷情形照會單", fontCh16b, 0, CENTER);
                // ---
                addColumn(table, 40, 1, "請查明受理號碼：" + detailCaseData.getBmApNoString() + " 目前紓困貸款相關辦理情形，並填具「紓", fontCh14, 0, LEFT);
                // ---
                addColumn(table, 40, 1, "困貸款抵銷情形（回執聯）」，送還承辦人員以憑辦理抵銷紓困貸款相關事宜。", fontCh14, 0, LEFT);
                // ---
                addEmptyRow(table, 1); // 空白行
                // ---
                addColumn(table, 40, 1, "　　此 致", fontCh14, 0, LEFT); // 開頭空白為全形空白
                // ---
                //addColumn(table, 40, 1, StringUtils.leftPad(detailCaseData.getBmApNoDeptString(), 2, "　") + "給付科", fontCh14, 0, LEFT); // 空白為全形空白
                addColumn(table, 40, 1, RptTitleUtility.getDivisionTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate()), fontCh14, 0, LEFT); // 空白為全形空白
                // ---
                //addColumn(table, 40, 1, "　　　　" + StringUtils.leftPad(caseData.getDeptNameString(), 2, "　") + "給付科　　承辦人：____________ 複核：____________", fontCh14, 0, LEFT); // 空白為全形空白
                addColumn(table, 40, 1, "　　　　" + RptTitleUtility.getDivisionTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+"　　承辦人：____________ 複核：____________", fontCh14, 0, LEFT); // 空白為全形空白
                // ---
                addEmptyRow(table, 1); // 空白行
                // ---
                addColumn(table, 40, 1, "日期：" + printDate, fontCh14, 0, RIGHT);
                // ---
                addEmptyRow(table, 1); // 空白行
                // ]
                
                // 分隔線
                addLine(table);
                addEmptyRow(table, 1); // 空白行
                
                // 報表下半部
                // [
                addColumn(table, 40, 1, "紓困貸款抵銷情形（回執聯）", fontCh16b, 0, CENTER);
                // ---
                addColumn(table, 40, 1, "經查明，受理號碼：" + detailCaseData.getBmApNoString() + " 目前辦理情形如下，還請卓辦：", fontCh14, 0, LEFT);
                // ---
                addColumn(table, 40, 1, "□已核付：抵銷前之勞工紓困貸款本金_____________元。", fontCh14, 0, LEFT);
                // ---
                addColumn(table, 40, 1, "　　　　　　　　　　　　　　　利息_____________元。", fontCh14, 0, LEFT); // 空白為全形空白
                // ---
                addColumn(table, 40, 1, "　　　　　　　　　　　　　　　抵銷_____________元。", fontCh14, 0, LEFT); // 空白為全形空白
                // ---
                addColumn(table, 40, 1, "□尚未核付：　1.□尚有待辦事項，目前無法核定。", fontCh14, 0, LEFT); // 空白為全形空白
                // ---
                addColumn(table, 40, 1, "　　　　　　　2.□書件齊備符合規定，可於近日內核定，本案應抵銷金額", fontCh14, 0, LEFT); // 空白為全形空白
                // ---
                addColumn(table, 40, 1, "　　　　　　　　　元。", fontCh14, 0, LEFT); // 空白為全形空白
                // ---
                addColumn(table, 40, 1, "　　此 致", fontCh14, 0, LEFT); // 開頭空白為全形空白
                // ---
                //addColumn(table, 40, 1, StringUtils.leftPad(caseData.getDeptNameString(), 2, "　") + "給付科", fontCh14, 0, LEFT); // 空白為全形空白
                addColumn(table, 40, 1, RptTitleUtility.getDivisionTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate()), fontCh14, 0, LEFT); // 空白為全形空白
                // ---
                //addColumn(table, 40, 1, "　　　　" + StringUtils.leftPad(detailCaseData.getBmApNoDeptString(), 2, "　") + "給付科　　承辦人：____________ 複核：____________", fontCh14, 0, LEFT); // 空白為全形空白
                addColumn(table, 40, 1, "　　　　" + RptTitleUtility.getDivisionTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+"　　承辦人：____________ 複核：____________", fontCh14, 0, LEFT); // 空白為全形空白
                // ---
                addColumn(table, 40, 1, "日期：　　年　　月　　日", fontCh14, 0, RIGHT);
                // ]
                
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
