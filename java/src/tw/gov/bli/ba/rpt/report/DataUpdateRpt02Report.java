package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.DataUpdateRpt02Case;
import tw.gov.bli.ba.util.DateUtility;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

/**
 * 勞保被保險人紓困貸款撥款情形查詢清單
 * 
 * @author Goston
 */
public class DataUpdateRpt02Report extends ReportBase {
    private String printDate = ""; // 印表日期

    public DataUpdateRpt02Report() throws Exception {
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
    
    public ByteArrayOutputStream doReport(UserBean userData, DataUpdateRpt02Case caseData) throws Exception {
        try {
            document.open();

            document.newPage();

            // 建立表格
            Table table = createTable(40);
            table.setAutoFillEmptyCells(true);
            table.setAlignment(Element.ALIGN_CENTER);
            table.setPadding(3);
            
            addColumn(table, 40, 1, "報表編號：BALP0D120", fontCh12, 0, LEFT);
            // ---
            addEmptyRow(table, 1); // 空白行
            // ---
            addColumn(table, 40, 1, "勞保被保險人紓困貸款撥款情形查詢清單", fontCh20b, 0, CENTER);
            // ---
            addColumn(table, 40, 1, "製表日期： " + printDate, fontCh12, 0, RIGHT);
            // ---
            StringBuffer strContent = new StringBuffer("");
            strContent.append("經查本局核定貸款合格戶");
            strContent.append(" " + StringUtils.defaultString(caseData.getNameAply()) + " ");
            strContent.append("（身分證統一編號： " + StringUtils.defaultString(caseData.getIdnAply()) + " ，出生日期： " + caseData.getBrDteAplyString() + "），");
            strContent.append("再申請老年或死亡或終身不能從事工作之失能給付，");
            strContent.append("截至 " + printDate + "本局電腦顯示貸款尚未撥款，");
            strContent.append("請　貴行配合查詢並依目前實際撥款情形勾選已撥款或尚未撥款。");
            strContent.append("已撥款者，請填寫撥款日期及金額，尚未撥款者，請即配合停止撥款。");
            addColumnAssignVAlignmentAndLineSpace(table, 5, 1, "說明：", fontCh16b, 5f, 0, RIGHT, TOP); // 有指定垂直對齊方式
            addColumnAssignVAlignmentAndLineSpace(table, 35, 1, strContent.toString() , fontCh16b, 5f, 0, JUSTIFIED, TOP); // 有指定垂直對齊方式
            // ---
            addColumnAssignVAlignment(table, 12, 3, "貸款案收件編號\r\n給付案-受理編號", fontCh16b, 1, CENTER, MIDDLE); // 有指定垂直對齊方式
            addColumnAssignVAlignment(table, 10, 3, "貸款核定金額\r\n貸款核定日期", fontCh16b, 1, CENTER, MIDDLE); // 有指定垂直對齊方式
            addColumnAssignVAlignment(table, 7, 3, "查詢時間", fontCh16b, 1, CENTER, MIDDLE); // 有指定垂直對齊方式
            addColumnAssignVAlignment(table, 7, 1, "已撥款", fontCh16b, 1, CENTER, MIDDLE); // 有指定垂直對齊方式
            addColumnAssignVAlignment(table, 4, 3, "尚未\r\n撥款", fontCh16b, 1, CENTER, MIDDLE); // 有指定垂直對齊方式
            
            addColumnAssignVAlignment(table, 7, 2, "撥款日期\r\n撥款金額", fontCh16b, 1, CENTER, MIDDLE); // 有指定垂直對齊方式
            // ---
            addColumnAssignVAlignment(table, 12, 3, caseData.getRcvNoString() + "\r\n" + caseData.getApNoString(), fontCh16b, 1, CENTER, MIDDLE); // 貸款案收件編號 及 給付案-受理編號
            addColumnAssignVAlignment(table, 10, 3, formatBigDecimalToInteger(caseData.getMoneyExm()) + "\r\n" + caseData.getDateExmString(), fontCh16b, 1, CENTER, MIDDLE); // 貸款核定金額 及 貸款核定日期
            addColumnAssignVAlignment(table, 7, 3, " ", fontCh16b, 1, CENTER, MIDDLE); // 查詢時間
            addColumnAssignVAlignment(table, 7, 3, " ", fontCh16b, 1, CENTER, MIDDLE); // 已撥款 撥款日期 撥款金額
            addColumnAssignVAlignment(table, 4, 3, " ", fontCh16b, 1, CENTER, MIDDLE); // 尚未撥款           
            // ---
            addColumnAssignVAlignment(table, 4, 1, "註：", fontCh16b, 0, RIGHT, TOP);
            addColumnAssignVAlignment(table, 36, 1, "接獲本停止撥款清單，請即處理，並填妥處理結果傳真個人金融部\r\n傳真號碼（02）2375-7022", fontCh16b, 0, LEFT, TOP);
            
            document.add(table);
            
            document.close();
        }
        finally {
            document.close();
        }

        return bao;
    }
}
