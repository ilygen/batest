package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.DataUpdateRpt01Case;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

/**
 * 紓困繳納查詢單
 * 
 * @author Goston
 */
public class DataUpdateRpt01Report extends ReportBase {
    private String printDate = ""; // 印表日期

    public DataUpdateRpt01Report() throws Exception {
        super();
        printDate = DateUtility.formatNowChineseDateString(true); // 中文民國年月日字串
    }
    
    public Document createDocument() {
        // 參數說明: Document(頁面大小, 左邊空白, 右邊空白, 上面空白, 下面空白)
        Document document = new Document(PageSize.A4, 30, 30, 60, 60);
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
            addColumn(table, 40, 1, "　", fontCh12, 0, LEFT); // 注意: 內容是全形空白
        }
    }
    
    public ByteArrayOutputStream doReport(UserBean userData, DataUpdateRpt01Case caseData) throws Exception {
        try {
            document.open();

            document.newPage();

            // 建立表格
            Table table = createTable(40);
            table.setAutoFillEmptyCells(true);
            table.setAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.setPadding(5);
            
            addColumn(table, 40, 1, "報表編號：BALP0D110", fontCh12, 0, LEFT);
            // ---
            addColumn(table, 40, 1, "勞工紓困貸款繳納本息情形查詢單", fontCh18, 0, CENTER);
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 4, 1, "姓名", fontCh12, 1, CENTER);
            addColumn(table, 8, 1, caseData.getNameAply(), fontCh12, 1, CENTER); // 姓名
            addColumn(table, 6, 1, "身分證號", fontCh12, 1, CENTER);
            addColumn(table, 8, 1, caseData.getIdnAply(), fontCh12, 1, CENTER); // 身分證號
            addColumn(table, 4, 1, "出生", fontCh12, 1, CENTER);
            addColumn(table, 10, 1, caseData.getBrDteAplyString(), fontCh12, 1, CENTER); // 出生
            // ---
            addColumn(table, 20, 1, "貸款收件編號：" + caseData.getRcvNoString(), fontCh12, 1, LEFT);
            addColumn(table, 20, 1, "撥款日期：" + caseData.getDatePayString(), fontCh12, 1, LEFT);
            // ---
            addColumn(table, 22, 1, "受理編號：" + caseData.getApNoString(), fontCh12, 1, LEFT);
            addColumn(table, 18, 1, "放款帳號：" + StringUtils.defaultString(caseData.getAccountPay()), fontCh12, 1, LEFT);
            // ---
            StringBuffer strContent = new StringBuffer("");
            strContent.append("請查明截至 " + caseData.getIssuDateString() + "止，尚欠本金、利息及必要費用合計金額為若干？\r\n");
            strContent.append(" \r\n");
            strContent.append("並請檢附明細表一份，俾憑辦理本息抵銷事宜。\r\n");
            strContent.append(" \r\n");
            strContent.append("　　此致\r\n"); // 空白為全形空白
            strContent.append(" \r\n");
            strContent.append("土地銀行\r\n");
            strContent.append(" \r\n");
            strContent.append(" \r\n");
            strContent.append("抵銷　　　　　　　　　元　　　　　　　　　　　　"+RptTitleUtility.getPeopleTitle(DateUtility.getNowWestDate())+"　"+RptTitleUtility.getGroupsTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+"　　　　　　啟\r\n");
            //strContent.append("抵銷　　　　　　　　　元　　　　　　　　　　　　勞工保險局給付處　　　　　　啟\r\n");
            strContent.append(" \r\n");
            addColumn(table, 40, 1, strContent.toString(), fontCh12, 1, LEFT);
            // ---
            addColumn(table, 2, 1, " ", fontCh12, 0, LEFT);
            addColumn(table, 18, 1, "經辦人：", fontCh12, 0, LEFT);
            addColumn(table, 20, 1, "單位主管：", fontCh12, 0, LEFT);
            
            document.add(table);

            document.close();
        }
        finally {
            document.close();
        }

        return bao;
    }
}
