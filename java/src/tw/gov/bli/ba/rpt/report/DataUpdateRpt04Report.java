package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.DataUpdateRpt04Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

public class DataUpdateRpt04Report extends ReportBase {
    
    private RptService rptService;
    
    public DataUpdateRpt04Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        return document;
    }
    
    public Table getPage(UserBean userData,DataUpdateRpt04Case caseData) throws Exception
    {
        Integer sumAplpayAmt = 0;
       
        
        document.newPage();
        // 建立表格
        Table table = createTable(42);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);

        addColumn(table, 34, 1, "報表編號：BALP0D140", fontCh10, 0, LEFT);
        addColumn(table, 8, 1, "", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
          
        addColumn(table, 42, 1, RptTitleUtility.getGroupsTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+RptTitleUtility.getDivisionTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+"　紓困貸款呆帳債務人照會單", fontCh18, 0, CENTER);
        //addColumn(table, 42, 1, "給付處"+caseData.getDeptNameString()+"給付科　紓困貸款呆帳債務人照會單", fontCh18, 0, CENTER);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 8, 1, "紓困貸款借款人：", fontCh10, 0, RIGHT);
        addColumn(table, 6, 1, caseData.getName_Bb(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "身分證號：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getIdn_Bb(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "出生日期：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getBrdteBbStr(), fontCh10, 0, LEFT);
        
        addColumn(table, 8, 1, "紓困貸款收件編號：", fontCh10, 0, RIGHT);
        addColumn(table, 6, 1, caseData.getRcvNo(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "呆帳本金：", fontCh10, 0, RIGHT);
        addColumn(table, 4, 1, formatNumber(caseData.getAmt1().longValue())+"元", fontCh10, 0, RIGHT);
        addColumn(table, 10, 1, "呆帳利息：", fontCh10, 0, RIGHT);
        addColumn(table, 4, 1, formatNumber(caseData.getAmt2().longValue())+"元", fontCh10, 0, RIGHT);
        addColumn(table, 3, 1, "", fontCh10, 0, RIGHT);
        
        addColumn(table, 14, 1, "　", fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "違約金：", fontCh10, 0, RIGHT);
        addColumn(table, 4, 1, formatNumber(caseData.getAmt3().longValue())+"元", fontCh10, 0, RIGHT);
        addColumn(table, 10, 1, "費用：", fontCh10, 0, RIGHT);
        addColumn(table, 4, 1, formatNumber(caseData.getAmt4().longValue())+"元", fontCh10, 0, RIGHT);
        addColumn(table, 3, 1, "", fontCh10, 0, RIGHT);
            
        addColumn(table, 8, 1, "紓困貸款債務人：", fontCh10, 0, RIGHT);
        addColumn(table, 6, 1, caseData.getName_Aa(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "身分證號：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getIdn_Aa(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "出生日期：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getBrdteAaStr(), fontCh10, 0, LEFT);
        
        addColumn(table, 8, 1, "申請給付種類：", fontCh10, 0, RIGHT);
        addColumn(table, 6, 1, caseData.getPayCodeStr()+"給付", fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "受理編號：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getApNoStr(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "申請日期：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getAppDateStr(), fontCh10, 0, LEFT);
            
        addColumn(table, 8, 1, "核定金額：", fontCh10, 0, RIGHT);
        addColumn(table, 34, 1, formatNumber(Long.decode(caseData.getIssueAmt())), fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "　　上列紓困貸款呆帳債務人應償還之紓困貸款金額敬請依相關規定辦理。", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
            
        addColumn(table, 42, 1, "　　此致", fontCh10, 0, LEFT);
        addColumn(table, 42, 1, RptTitleUtility.getCleanDivisionTitle(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
        //addColumn(table, 42, 1, "財務處欠費清理科", fontCh10, 0, LEFT);
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
           
        addColumn(table, 42, 1, "　　　　"+RptTitleUtility.getGroupsTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+RptTitleUtility.getDivisionTitle(caseData.getApNo().substring(0, 1), DateUtility.getNowWestDate())+"　　承辦人：_______________　　　複核：_____________", fontCh10, 0, CENTER);
        //addColumn(table, 42, 1, "　　　　給付處"+caseData.getDeptNameString()+"給付科　　承辦人：_______________　　　複核：_____________", fontCh10, 0, CENTER);
        
        addColumn(table, 42, 1, "日期："+DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), true), fontCh10, 0, RIGHT);
            
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        /**
        addColumn(table, 42, 1, "----------------------------------------------------------------------------------------------------", fontCh10, 0, CENTER);
        
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        
        addColumn(table, 42, 1, "給付處"+caseData.getDeptNameString()+"給付科　紓困貸款呆帳債務人照會單(回執聯)", fontCh18, 0, CENTER);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 8, 1, "申請給付種類：", fontCh10, 0, RIGHT);
        addColumn(table, 6, 1, caseData.getPayCodeStr()+"給付", fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "受理編號：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getApNoStr(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "受理日期：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getAppDateStr(), fontCh10, 0, LEFT);
        
        addColumn(table, 8, 1, "核定金額：", fontCh10, 0, RIGHT);
        addColumn(table, 34, 1, formatNumber(Long.decode(caseData.getIssueAmt())), fontCh10, 0, LEFT);
            
        addColumn(table, 8, 1, "紓困貸款借款人：", fontCh10, 0, RIGHT);
        addColumn(table, 6, 1, caseData.getName_Bb(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "身分證號：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getIdn_Bb(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "出生日期：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getBrdteBbStr(), fontCh10, 0, LEFT);
            
        addColumn(table, 8, 1, "紓困貸款收件編號：", fontCh10, 0, RIGHT);
        addColumn(table, 6, 1, caseData.getRcvNo(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "呆帳本金：", fontCh10, 0, RIGHT);
        addColumn(table, 4, 1, formatNumber(caseData.getAmt1().longValue())+"元", fontCh10, 0, RIGHT);
        addColumn(table, 10, 1, "呆帳利息：", fontCh10, 0, RIGHT);
        addColumn(table, 4, 1, formatNumber(caseData.getAmt2().longValue())+"元", fontCh10, 0, RIGHT);
        addColumn(table, 3, 1, "", fontCh10, 0, RIGHT);
        
        addColumn(table, 14, 1, "", fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "違約金：", fontCh10, 0, RIGHT);
        addColumn(table, 4, 1, formatNumber(caseData.getAmt3().longValue())+"元", fontCh10, 0, RIGHT);
        addColumn(table, 10, 1, "費用：", fontCh10, 0, RIGHT);
        addColumn(table, 4, 1, formatNumber(caseData.getAmt4().longValue())+"元", fontCh10, 0, RIGHT);
        addColumn(table, 3, 1, "", fontCh10, 0, RIGHT);
        
        addColumn(table, 8, 1, "紓困貸款債務人：", fontCh10, 0, RIGHT);
        addColumn(table, 6, 1, caseData.getName_Aa(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "身分證號：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getIdn_Aa(), fontCh10, 0, LEFT);
        addColumn(table, 7, 1, "出生日期：", fontCh10, 0, RIGHT);
        addColumn(table, 7, 1, caseData.getBrdteAaStr(), fontCh10, 0, LEFT);
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        
        addColumn(table, 2, 1, "□", fontCh10, 0, RIGHT);
        addColumn(table, 40, 1, "不同意扣減", fontCh10, 0, LEFT);
            
        addColumn(table, 2, 1, "□", fontCh10, 0, RIGHT);
        addColumn(table, 40, 1, "同意扣減，請於其得請領之給付中扣減本金：　　元，利息：　　元，違約金：　　元，費用：　　元。檢送同意書乙份。", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 42, 1, "　　此致", fontCh10, 0, LEFT);
        addColumn(table, 42, 1, "給付處"+caseData.getDeptNameString()+"給付科", fontCh10, 0, LEFT);
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 42, 1, "　　　　財務處欠費清理科　　承辦人：_______________　　　複核：_____________", fontCh10, 0, CENTER);
        
        addColumn(table, 42, 1, "日期："+DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), true), fontCh10, 0, RIGHT);
**/
        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<DataUpdateRpt04Case> caseData) throws Exception {
        try {
            RptService rptService = (RptService) SpringHelper.getBeanById("rptService");
            
            document.open();

            Table table = null;
            
            // 列印紓困貸款呆帳債務人照會單
            for(DataUpdateRpt04Case dataUpdateRpt04Case : caseData){
                table = getPage(userData,dataUpdateRpt04Case);               
                document.add(table);
            }
            
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
