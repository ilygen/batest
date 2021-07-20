package tw.gov.bli.ba.rpt.report;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.DataUpdateRpt05Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;

public class DataUpdateRpt05Report extends ReportBase {
    
    private RptService rptService;
    
    public DataUpdateRpt05Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        return document;
    }
    
    public Table getPageOne(UserBean userData,List<DataUpdateRpt05Case> caseList) throws Exception
    {
        document.newPage();
        // 建立表格
        Table table = createTable(42);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);

        addColumn(table, 34, 1, "報表編號：BALP0D150", fontCh10, 0, LEFT);
        addColumn(table, 8, 1, "", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 8, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 26, 1, "年金止付(照會)單", fontCh18, 0, CENTER);
        addColumn(table, 8, 1, "", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 20, 1, "", fontCh18, 0, CENTER);
        addColumn(table, 8, 1, "印表日期："+DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), false), fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 21, 1, RptTitleUtility.getGroupsTitle(caseList.get(0).getApNo().substring(0, 1), DateUtility.getNowWestDate())+"止付日期："+caseList.get(0).getStexpndDateString(), fontCh10, 0, LEFT);
        //addColumn(table, 21, 1, "給付科止付日期："+caseList.get(0).getStexpndDateString(), fontCh10, 0, LEFT);
        addColumn(table, 21, 1, "止付原因："+caseList.get(0).getStexpndReasonStr(), fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 42, 1, "給付種類："+caseList.get(0).getPayCodeStr()+"給付", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 14, 1, "受理編號："+caseList.get(0).getApNoStr(), fontCh10, 0, LEFT);
        addColumn(table, 14, 1, "核定年月："+caseList.get(0).getIssuYmStr(), fontCh10, 0, LEFT);
        addColumn(table, 14, 1, "事故者姓名："+caseList.get(0).getEvtName(), fontCh10, 0, LEFT);
        
        addColumn(table, 14, 1, "受款人姓名："+caseList.get(0).getBenName(), fontCh10, 0, LEFT);
        addColumn(table, 14, 1, "" , fontCh10, 0, LEFT);        
        addColumn(table, 14, 1, "核付金額："+caseList.get(0).getSumAplpayAmt(), fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, "等"+caseList.get(0).getPeopleCount()+"人", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
            
        addColumn(table, 42, 1, "檢送支付款項止付通知單計"+caseList.size()+"份，敬請儘速止付並回覆。", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
            
        addColumn(table, 42, 1, "　　此致", fontCh10, 0, LEFT);
        addColumn(table, 42, 1, RptTitleUtility.getSafetyPaymentTitle1(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
        //addColumn(table, 42, 1, "給付出納科", fontCh10, 0, LEFT);    
        addColumn(table, 42, 1, RptTitleUtility.getDivisionTitle(caseList.get(0).getApNo().substring(0, 1), DateUtility.getNowWestDate())+"承辦人：【"+userData.getEmpNo()+"】　簽章：__________", fontCh10, 0, RIGHT);
        //addColumn(table, 42, 1, caseList.get(0).getDeptNameString()+"給付科承辦人：【"+userData.getEmpNo()+"】　簽章：__________", fontCh10, 0, RIGHT);    
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "------------------------------------------------------------------------------------------------------", fontCh10, 0, CENTER);
        
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        
        addColumn(table, 8, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 26, 1, "年金止付(回執聯)單", fontCh18, 0, CENTER);
        addColumn(table, 8, 1, "", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 20, 1, "", fontCh18, 0, CENTER);
        addColumn(table, 8, 1, "印表日期："+DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), false), fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 21, 1, RptTitleUtility.getGroupsTitle(caseList.get(0).getApNo().substring(0, 1), DateUtility.getNowWestDate())+"止付日期："+caseList.get(0).getStexpndDateString(), fontCh10, 0, LEFT);
        //addColumn(table, 21, 1, "給付科止付日期："+caseList.get(0).getStexpndDateString(), fontCh10, 0, LEFT);
        addColumn(table, 21, 1, "止付原因："+caseList.get(0).getStexpndReasonStr(), fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 42, 1, "給付種類："+caseList.get(0).getPayCodeStr()+"給付", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
            
        addColumn(table, 14, 1, "受理編號："+caseList.get(0).getApNoStr(), fontCh10, 0, LEFT);
        addColumn(table, 14, 1, "核定年月："+caseList.get(0).getIssuYmStr(), fontCh10, 0, LEFT);
        addColumn(table, 14, 1, "事故者姓名："+caseList.get(0).getEvtName(), fontCh10, 0, LEFT);
            
        addColumn(table, 14, 1, "受款人姓名："+caseList.get(0).getBenName(), fontCh10, 0, LEFT);
        addColumn(table, 14, 1, "" , fontCh10, 0, LEFT);
        addColumn(table, 14, 1, "核付金額："+caseList.get(0).getSumAplpayAmt(), fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, "等"+caseList.get(0).getPeopleCount()+"人", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
           
        addColumn(table, 42, 1, "所送支付款項止付通知單計"+caseList.size()+"份，本科已收訖。", fontCh10, 0, LEFT);
            
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
            
        addColumn(table, 42, 1, "　　此致", fontCh10, 0, LEFT);
        addColumn(table, 42, 1, RptTitleUtility.getDivisionTitle(caseList.get(0).getApNo().substring(0, 1), DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
        //addColumn(table, 42, 1, caseList.get(0).getDeptNameString()+"給付科", fontCh10, 0, LEFT);    
        addColumn(table, 42, 1, RptTitleUtility.getSafetyPaymentTitle1(DateUtility.getNowWestDate())+"承辦人：【　　　】　簽章：__________", fontCh10, 0, RIGHT);
        //addColumn(table, 42, 1, "給付出納科承辦人：【　　　】　簽章：__________", fontCh10, 0, RIGHT);
        return table;
    }
    
    public Table addBank(UserBean userData,DataUpdateRpt05Case caseData) throws Exception
    {  
        document.newPage();
        
        // 建立表格
        Table table = createTable(42);
        table.setAutoFillEmptyCells(true);

        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);
        
        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh8, 0, CENTER);
        
        addColumn(table, 8, 1, "", fontCh10, 0, CENTER);        
//        if(caseData.getBenEvtRel().equals("4")){
//            addColumn(table, 26, 1, "委託郵局寄發郵政特種匯票止付通知單", fontCh18, 0, CENTER);
//        } 
//        else {
        addColumn(table, 26, 1, "委託金融機構支付款項止付通知單", fontCh18, 0, CENTER);
//        }
        addColumn(table, 8, 1, "", fontCh10, 0, LEFT);
        
        addColumn(table, 14, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 14, 1, "", fontCh18, 0, CENTER);
        addColumn(table, 14, 1, "製表日期："+DateUtility.formatChineseDateTimeString(DateUtility.getNowChineseDate(), false), fontCh10, 0, LEFT);
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        
        addColumn(table, 12, 1, "", fontCh10, 0, CENTER);
//        if(caseData.getBenEvtRel().equals("4")){
//            addColumn(table, 30, 1, "出帳帳戶：郵政劃撥儲金"+caseData.getBureauAcc()+"號帳戶", fontCh12, 0, LEFT);
//        }
//        else {
        addColumn(table, 30, 1, "出帳帳戶：土地銀行台北分行13986-2帳戶", fontCh12, 0, LEFT);
//        }
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        
        addColumn(table, 12, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 30, 1, "核付日期："+caseData.getAplpayDateString(), fontCh10, 0, LEFT);
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        
        addColumn(table, 12, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 30, 1, "核定年月："+caseData.getIssuYmString(), fontCh10, 0, LEFT);
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        
        addColumn(table, 12, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 30, 1, "給付年月："+caseData.getPayYmString(), fontCh10, 0, LEFT);
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        
        addColumn(table, 12, 1, "", fontCh10, 0, CENTER);        
//        if(caseData.getBenEvtRel().equals("4")){
//            addColumn(table, 30, 1, "匯票號碼："+caseData.getPayBankId()+caseData.getBranchId()+"-"+caseData.getPayEeacc(), fontCh10, 0, LEFT);
//        }
        if(caseData.getBenEvtRel().equals("F") || caseData.getfStyleForKS().equals("Y")){
            addColumn(table, 30, 1, "放款帳號："+caseData.getAccount_Pay(), fontCh10, 0, LEFT);
        }
        else{
	        if(!StringUtils.equals(caseData.getPayTyp(), "A")){
	        	if(caseData.getPayTyp().equals("1") && !caseData.getPayBankId().equals("700")){
	        		addColumn(table, 30, 1, "轉帳號碼："+caseData.getPayBankId()+"0000"+"-"+caseData.getPayEeacc(), fontCh10, 0, LEFT);
	        	}else{
	        		addColumn(table, 30, 1, "轉帳號碼："+caseData.getPayBankId()+caseData.getBranchId()+"-"+caseData.getPayEeacc(), fontCh10, 0, LEFT);
	        	}
	        } else {
	        	addColumn(table, 30, 1, "扣押戶："+"--", fontCh10, 0, LEFT);
	        }
        }
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        
        addColumn(table, 12, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 30, 1, "核付金額："+caseData.getAplpayAmt(), fontCh10, 0, LEFT);
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        
        addColumn(table, 12, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 30, 1, "受理編號："+caseData.getApNoStr(), fontCh10, 0, LEFT);
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        
        addColumn(table, 12, 1, "", fontCh10, 0, CENTER);
        if(caseData.getBenEvtRel().equals("F") || caseData.getfStyleForKS().equals("Y")){
            addColumn(table, 30, 1, "貸款人："+caseData.getName_Aply(), fontCh10, 0, LEFT);
        }
        else {
        	addColumn(table, 30, 1, "受款人："+caseData.getBenName(), fontCh10, 0, LEFT);
        }        
        
        addColumn(table, 42, 1, " ", fontCh10, 0, CENTER);
        
        addColumn(table, 12, 1, "", fontCh10, 0, CENTER);
        if(caseData.getBenEvtRel().equals("F") || caseData.getfStyleForKS().equals("Y")){
            addColumn(table, 30, 1, "貸款人身份證號："+caseData.getIdn_Aply(), fontCh10, 0, LEFT);
        }
        else {
        	addColumn(table, 30, 1, "受款人身份證號："+caseData.getBenIdnNo(), fontCh10, 0, LEFT);
        }

        
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        
        addColumn(table, 12, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 30, 1, "委託單位："+RptTitleUtility.getPeopleTitle(DateUtility.getNowWestDate()), fontCh10, 0, LEFT);
        //addColumn(table, 30, 1, "委託單位：勞工保險局", fontCh10, 0, LEFT);
        addColumn(table, 42, 1, "", fontCh10, 0, CENTER);
        
        addColumn(table, 12, 1, "", fontCh10, 0, CENTER);
        addColumn(table, 30, 1, RptTitleUtility.getSafetyPaymentTitle1(DateUtility.getNowWestDate())+"經辦人：____________主管：_____________", fontCh10, 0, LEFT);
        //addColumn(table, 30, 1, "給付出納科經辦人：____________主管：_____________", fontCh10, 0, LEFT);
        return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<DataUpdateRpt05Case> caseData) throws Exception {
        try {
            RptService rptService = (RptService) SpringHelper.getBeanById("rptService");
            
            document.open();

            Table pageOne = null;
            Table pageTwo = null;
            
            // 本人只印第一頁
            pageOne = getPageOne(userData,caseData);   
            
            document.add(pageOne);
            
            // 其他受款人每個人都要印
            for(DataUpdateRpt05Case dataUpdateRpt05Case : caseData){
                pageTwo = addBank(userData,dataUpdateRpt05Case); 
                document.add(pageTwo);
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
