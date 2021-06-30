package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.lowagie.text.Element;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.Image;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.DecisionRpt03Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt01Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt05Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.common.helper.SpringHelper;

public class DecisionRpt03Report extends ReportBase {

    private RptService rptService;

    public DecisionRpt03Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 10, 10, 30, 30);
        return document;
    }

    public Table printPage(List<DecisionRpt03Case> caseList, String decisionDate, BigDecimal totalCount) throws Exception {
        document.newPage();
        // 建立表格
        Table table = createTable(60);

        addColumn(table, 20, 1, " ", fontCh20, 0, RIGHT);
        addColumnAssignLineSpace(table, 27, 1, "歸檔清單點交清冊" , fontCh22b, 0, 0, LEFT);
        addColumnAssignVAlignment(table, 13, 1, "印表日期："+DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(),false), fontCh12, 0, RIGHT, RIGHT);
        
        addColumnAssignVAlignment(table, 60, 1, "歸檔日期："+DateUtility.formatChineseDateString(decisionDate, false), fontCh12, 0, RIGHT, RIGHT);

        addColumnAssignVAlignment(table, 60, 1, "　" , fontCh11, 0, RIGHT, RIGHT);
        addColumnAssignVAlignment(table, 60, 1, "　" , fontCh11, 0, RIGHT, RIGHT);
        
        addColumnAssignVAlignment(table, 20, 1, "給付別" , fontCh16, 0, CENTER, RIGHT);
        addColumnAssignVAlignment(table, 20, 1, "起迄頁次" , fontCh16, 0, CENTER, RIGHT);
        addColumnAssignVAlignment(table, 20, 1, "件數" , fontCh16, 0, CENTER, RIGHT);
        
        for(DecisionRpt03Case caseData : caseList){
        	addColumnAssignVAlignment(table, 20, 1, caseData.getPayCodeString() , fontCh14, 0, CENTER, RIGHT);
            addColumnAssignVAlignment(table, 20, 1, caseData.getMinArcPg()+"-"+caseData.getMaxArcPg() , fontCh14, 0, CENTER, RIGHT);
            addColumnAssignVAlignment(table, 20, 1, caseData.getCountNumber().toString() , fontCh14, 0, CENTER, RIGHT);
            addColumnAssignVAlignment(table, 60, 1, "　" , fontCh14, 0, RIGHT, RIGHT);
            addColumnAssignVAlignment(table, 60, 1, "　" , fontCh14, 0, RIGHT, RIGHT);
        }
        
        while (writer.fitsPage(table)) {
            addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT);
        }

        for (int i = 0; i < 10; i++)
            table.deleteLastRow();

     
        addColumnAssignVAlignment(table, 20, 1, "合計：" , fontCh14, 0, CENTER, RIGHT);
        addColumnAssignVAlignment(table, 20, 1, "" , fontCh14, 0, CENTER, RIGHT);
        addColumnAssignVAlignment(table, 20, 1,  totalCount.toString() , fontCh14, 0, CENTER, RIGHT);
       
        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<DecisionRpt03Case> caseList, String decisionDate) throws Exception {

        try {

            document.open();
            Table table = null;
            
            BigDecimal totalCount = new BigDecimal(0);
            
            for(DecisionRpt03Case caseData : caseList){
            	if(caseData.getCountNumber() != null){
            		totalCount = totalCount.add(caseData.getCountNumber());
            	}
            }

            table = printPage(caseList, decisionDate, totalCount);
            
            document.add(table);
            document.close();
        }
        finally {
            document.close();
        }
        return bao;
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}

