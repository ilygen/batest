package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.ReviewFeeReceiptNotifyDataCase;
import tw.gov.bli.ba.rpt.cases.ReviewFeeReceiptRpt02DataCase;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;

/**
 * 複檢費用核定通知書
 * 
 * @author Evelyn Hsu
 */


public class ReviewFeeReceiptRpt02Report extends ReportBase {
    
    private RptService rptService;
    private int seqNo = 1;

    public ReviewFeeReceiptRpt02Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Rectangle rectangle = new Rectangle(576, 792);
        Document document = new Document(rectangle, 10, 10, 0, 10);
        return document;
    }
    
    public Table printPage(ReviewFeeReceiptRpt02DataCase caseData) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(60);
        // table.setAutoFillEmptyCells(true);

        // addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);

        addColumnAssignVAlignment(table, 60, 1, StringUtils.leftPad(String.valueOf(seqNo), 5, "0"), fontCh10, 0, RIGHT, BOTTOM);
        // addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);

        addColumn(table, 16, 1, " ", fontCh20, 0, RIGHT);
        addColumnAssignLineSpace(table, 25, 1, RptTitleUtility.getPeopleTitle(DateUtility.getNowWestDate())+"核定通知書", fontCh22b, 0, 0, JUSTIFIEDALL);
        //addColumnAssignLineSpace(table, 25, 1, "勞工保險局核定通知書", fontCh22b, 0, 0, JUSTIFIEDALL);
        addColumnAssignVAlignment(table, 19, 1," ", fontCh14, 0, RIGHT, BOTTOM);

        addColumn(table, 37, 1, " ", fontCh11, 0, CENTER);
        addColumn(table, 23, 1, "機關地址：10013台北市羅斯福路1段4號    " + "發文日期：" + StringUtils.rightPad(caseData.getWordDate(), (StringUtils.isBlank(caseData.getWordDate())) ? 28 : 26, " ") + "發文字號：保給字第" + caseData.getWordNo() + "號" + "      " + "承辦單位："
                        + StringUtils.rightPad(caseData.getUnit(), 18, " ") + "聯絡電話：" + caseData.getComTel(), fontCh11, 0, LEFT);

        addColumnAssignLineSpace(table, 16, 1, "地  址：", fontCh12, 10, 0, RIGHT);
        addColumnAssignLineSpace(table, 44, 1, StringUtils.trimToEmpty(caseData.getCommZip()), fontCh12, 10, 0, LEFT);
        addColumnAssignLineSpace(table, 16, 1, " ", fontCh12, 10, 0, LEFT);
        addColumnAssignLineSpace(table, 20, 1, StringUtils.trimToEmpty(caseData.getCommAddr()), fontCh12, 10, 0, LEFT);
        addColumnAssignLineSpace(table, 24, 1, " ", fontCh12, 10, 0, LEFT);
        addColumnAssignLineSpace(table, 16, 1, "受文者：", fontCh16, 10, 0, RIGHT);
        addColumnAssignLineSpace(table, 44, 1, caseData.getName(), fontCh16, 10, 0, LEFT);

        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);
        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);
        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);
        addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);
        
        if (caseData.getNotifyData() != null) {
        
            ReviewFeeReceiptNotifyDataCase notifyData = caseData.getNotifyData();
    
            addColumnAssignLineSpace(table, 5, 1, "主旨：", fontCh12, 8, 0, LEFT);
            addColumnAssignLineSpace(table, 55, 1, notifyData.getSubject(), fontCh12, 8, 0, JUSTIFIED);
    
            addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);
            addColumn(table, 60, 1, " ", fontCh8, 0, CENTER);
            
            if (notifyData.getContent().size() > 0) {
              addColumn(table, 6, 1, "說明：", fontCh12, 0, LEFT);
              addColumn(table, 54, 1, notifyData.getContent().get(0), fontCh12, 0, LEFT); // 說明
              
            }

            
        }
        
        addColumnAssignLineSpace(table, 5, 1, "正本：", fontCh12, 8, 0, LEFT);
        addColumnAssignLineSpace(table, 55, 1, caseData.getProgenitor(), fontCh12, 8, 0, LEFT);

        while (writer.fitsPage(table)) {
            addColumnAssignLineSpace(table, 60, 1, "　", fontCh12, 8, 0, RIGHT);
        }
        table.deleteLastRow();
        table.deleteLastRow();
        
        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<ReviewFeeReceiptRpt02DataCase> caseList) throws Exception {

        try {

            document.open();
            Table table = null;

            for (ReviewFeeReceiptRpt02DataCase caseData : caseList) {
                table = printPage(caseData);
                document.add(table);
            }
            document.close();
        }
        finally {
            document.close();
        }
        return bao;
    }

    private String getNameMsg(List<String> nameList) {
        String msg = "";
        if (nameList != null) {
            for (String name : nameList) {
                if (StringUtils.isNotBlank(msg)) {
                    msg = msg + "、" + name;
                }
                else {
                    msg = name;
                }
            }
        }
        return msg;
    }


}
