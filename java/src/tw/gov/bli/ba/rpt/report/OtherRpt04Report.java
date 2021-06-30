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
import tw.gov.bli.ba.rpt.cases.OtherRpt04Case;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.RptTitleUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.helper.SpringHelper;

public class OtherRpt04Report extends ReportBase {
	private static int PAGE_ROW_SIZE = 20;// 每20筆要換頁
	private static int PAGE_TOTAL = 1;// 頁次

    public OtherRpt04Report() throws Exception {
        super();
    }

    public Document createDocument() {
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        return document;
    }
    
    /**
     * 建立表頭 page1
     * 
     * @param payListSize 事故者 給付資料 筆數
     * @param attached 是否為附表
     * @return
     * @throws Exception
     */
    
    public Table addHeader(String crtDate) throws Exception {
    	document.newPage();
        // 建立表格
        Table table = createTable(60);

        addColumn(table, 20, 1, " ", fontCh20, 0, RIGHT);
        addColumnAssignVAlignment(table, 25, 1, "受理案件統計表" , fontCh22b, 0, 0, LEFT);
        addColumnAssignVAlignment(table, 15, 1, "", fontCh12, 0, LEFT, RIGHT);
        
        addColumnAssignVAlignment(table, 45, 1, "　" , fontCh12, 0, RIGHT, RIGHT);
        addColumnAssignVAlignment(table, 15, 1, "印表日期："+DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(),false), fontCh12, 0, LEFT, RIGHT);
        
        addColumnAssignVAlignment(table, 45, 1, "　" , fontCh12, 0, RIGHT, RIGHT);
        addColumnAssignVAlignment(table, 15, 1, "印表頁次："+StringUtility.chtLeftPad(String.valueOf(writer.getPageNumber()),4,"0")+"/"+StringUtility.chtLeftPad(String.valueOf(PAGE_TOTAL),4,"0"), fontCh12, 0, LEFT, RIGHT);
        
        addColumnAssignVAlignment(table, 45, 1, "　" , fontCh12, 0, RIGHT, RIGHT);
        addColumnAssignVAlignment(table, 15, 1, "受理日期："+DateUtility.formatChineseDateString(crtDate, false), fontCh12, 0, LEFT, RIGHT);
        
        return table;
    }
    
    /**
     * 建立表頭 page2
     * 
     * @param payListSize 事故者 給付資料 筆數
     * @param attached 是否為附表
     * @return
     * @throws Exception
     */
    
    public Table addHeaderPage2(String crtDate) throws Exception {
    	document.newPage();
        // 建立表格
        Table table = createTable(60);

        addColumn(table, 20, 1, " ", fontCh20, 0, RIGHT);
        addColumnAssignVAlignment(table, 25, 1, "受理案件統計表" , fontCh22b, 0, 0, LEFT);
        addColumnAssignVAlignment(table, 15, 1, "", fontCh12, 0, LEFT, RIGHT);
        
        addColumnAssignVAlignment(table, 45, 1, "　" , fontCh12, 0, RIGHT, RIGHT);
        addColumnAssignVAlignment(table, 15, 1, "印表日期："+DateUtility.formatChineseDateString(DateUtility.getNowChineseDate(),false), fontCh12, 0, LEFT, RIGHT);
        
        addColumnAssignVAlignment(table, 45, 1, "　" , fontCh12, 0, RIGHT, RIGHT);
        addColumnAssignVAlignment(table, 15, 1, "印表頁次："+StringUtility.chtLeftPad(String.valueOf(writer.getPageNumber()),4,"0")+"/"+StringUtility.chtLeftPad(String.valueOf(PAGE_TOTAL),4,"0"), fontCh12, 0, LEFT, RIGHT);
        
        addColumnAssignVAlignment(table, 45, 1, "　" , fontCh12, 0, RIGHT, RIGHT);
        addColumnAssignVAlignment(table, 15, 1, "受理日期："+DateUtility.formatChineseDateString(crtDate, false), fontCh12, 0, LEFT, RIGHT);
        
        addColumnAssignVAlignment(table, 60, 1, "　" , fontCh12, 0, RIGHT, RIGHT);
        
        addColumnAssignVAlignment(table, 8, 1, "給付別" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 5, 1, "" , fontCh12, 0, CENTER, RIGHT);
        addColumnAssignVAlignment(table, 8, 1, "K(一般)" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1, "K(國併勞)" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1, "L(一般)" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1, "L(網路)" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1, "S" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 7, 1, "合計" , fontCh12, 0, LEFT, RIGHT);
        
        addColumnAssignVAlignment(table, 8, 1, "員工編號" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 52, 1, "" , fontCh12, 0, LEFT, RIGHT);
        
        return table;
    }

    public void printPage1(List<OtherRpt04Case> page1List, String crtDate, BigDecimal totalCountD, BigDecimal totalCountM) throws Exception {
    	
    	document.open();
    	
        Table table = null;
        
        table = addHeader(crtDate);

        addColumnAssignVAlignment(table, 60, 1, "　" , fontCh12, 0, RIGHT, RIGHT);
        
        addColumnAssignVAlignment(table, 17, 1, "給付別" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 7, 1, "" , fontCh12, 0, CENTER, RIGHT);
        addColumnAssignVAlignment(table, 12, 1, "受理起號" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 12, 1, "受理迄號" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 12, 1, "受理件數" , fontCh12, 0, RIGHT, RIGHT);
        
        for(int i = 0 ; i < page1List.size() ; i++){
        	
        	if((i+1)%2 == 0){
        		addColumnAssignVAlignment(table, 17, 1, "" , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 7, 1, page1List.get(i).getDmStr() , fontCh12, 0, CENTER, RIGHT);
                addColumnAssignVAlignment(table, 12, 1, page1List.get(i).getMinApNo() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 12, 1, page1List.get(i).getMaxApNo() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 12, 1, page1List.get(i).getRecCordCount().toString() , fontCh12, 0, RIGHT, RIGHT);
                addColumnAssignVAlignment(table, 60, 1, "　" , fontCh12, 0, RIGHT, RIGHT);
        	}else{
        		addColumnAssignVAlignment(table, 17, 1, page1List.get(i).getPayCodeStr() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 7, 1, page1List.get(i).getDmStr() , fontCh12, 0, CENTER, RIGHT);
                addColumnAssignVAlignment(table, 12, 1, page1List.get(i).getMinApNo() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 12, 1, page1List.get(i).getMaxApNo() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 12, 1, page1List.get(i).getRecCordCount().toString() , fontCh12, 0, RIGHT, RIGHT);
        	}
        	
        }
        
        while (writer.fitsPage(table)) {
            addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT);
        }

        for (int i = 0; i < 8; i++)
            table.deleteLastRow();

     
        addColumnAssignVAlignment(table, 17, 1, "合計：" , fontCh14, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 7, 1, "本日" , fontCh14, 0, CENTER, RIGHT);
        addColumnAssignVAlignment(table, 24, 1,  "" , fontCh14, 0, RIGHT, RIGHT);
        addColumnAssignVAlignment(table, 12, 1,  totalCountD.toString() , fontCh14, 0, RIGHT, RIGHT);
        
        addColumnAssignVAlignment(table, 17, 1, "" , fontCh14, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 7, 1, "本月" , fontCh14, 0, CENTER, RIGHT);
        addColumnAssignVAlignment(table, 24, 1,  "" , fontCh14, 0, RIGHT, RIGHT);
        addColumnAssignVAlignment(table, 12, 1,  totalCountM.toString() , fontCh14, 0, RIGHT, RIGHT);
       
        document.add(table);
        //return table;
    }
    
    public void printPage2(List<OtherRpt04Case> page2List, String crtDate) throws Exception {
    	
    	//document.open(); printPage1 has open
        Table table = null;
        
        int rowCount = 0;// 本頁列印筆數
        //合計
        BigDecimal payK1TotalD = BigDecimal.ZERO;
        BigDecimal payK1TotalM = BigDecimal.ZERO;
        BigDecimal payK2TotalD = BigDecimal.ZERO;
        BigDecimal payK2TotalM = BigDecimal.ZERO;
        BigDecimal payL1TotalD = BigDecimal.ZERO;
        BigDecimal payL1TotalM = BigDecimal.ZERO;
        BigDecimal payL2TotalD = BigDecimal.ZERO;
        BigDecimal payL2TotalM = BigDecimal.ZERO;
        BigDecimal payS1TotalD = BigDecimal.ZERO;
        BigDecimal payS1TotalM = BigDecimal.ZERO;
        BigDecimal singleDTotal = BigDecimal.ZERO;
        BigDecimal singleMTotal = BigDecimal.ZERO;
        
        table = addHeaderPage2(crtDate);

        for(int i = 0 ; i < page2List.size() ; i++){
        	
        	if ((table == null) || (rowCount > 0 && rowCount % PAGE_ROW_SIZE == 0)) {
                if (table != null) {
                	  while (writer.fitsPage(table)) {
                          addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT);
                      }

                      for (int x = 0; x < 8; x++)
                          table.deleteLastRow();
                	//頁尾
                    addColumnAssignVAlignment(table, 8, 1, "合計：" , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 5, 1, "本日" , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 8, 1,  payK1TotalD.toString() , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 8, 1,  payK2TotalD.toString() , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 8, 1,  payL1TotalD.toString() , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 8, 1,  payL2TotalD.toString() , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 8, 1,  payS1TotalD.toString() , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 7, 1,  singleDTotal.toString() , fontCh12, 0, LEFT, RIGHT);
                    
                    addColumnAssignVAlignment(table, 8, 1, "" , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 5, 1, "本月" , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 8, 1,  payK1TotalM.toString() , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 8, 1,  payK2TotalM.toString() , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 8, 1,  payL1TotalM.toString() , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 8, 1,  payL2TotalM.toString() , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 8, 1,  payS1TotalM.toString() , fontCh12, 0, LEFT, RIGHT);
                    addColumnAssignVAlignment(table, 7, 1,  singleMTotal.toString() , fontCh12, 0, LEFT, RIGHT);
                    document.add(table);
                    table = addHeaderPage2(crtDate);
                }
                rowCount = 0;
                payK1TotalD = BigDecimal.ZERO;
                payK1TotalM = BigDecimal.ZERO;
                payK2TotalD = BigDecimal.ZERO;
                payK2TotalM = BigDecimal.ZERO;
                payL1TotalD = BigDecimal.ZERO;
                payL1TotalM = BigDecimal.ZERO;
                payL2TotalD = BigDecimal.ZERO;
                payL2TotalM = BigDecimal.ZERO;
                payS1TotalD = BigDecimal.ZERO;
                payS1TotalM = BigDecimal.ZERO;
                singleDTotal = BigDecimal.ZERO;
                singleMTotal = BigDecimal.ZERO;
            }
        	//計數
        	rowCount++;
            if(page2List.get(i).getDm().equals("D")){
            	payK1TotalD = payK1TotalD.add(page2List.get(i).getPayK1());
            	payK2TotalD = payK2TotalD.add(page2List.get(i).getPayK2());
            	payL1TotalD = payL1TotalD.add(page2List.get(i).getPayL1());
            	payL2TotalD = payL2TotalD.add(page2List.get(i).getPayL2());
            	payS1TotalD = payS1TotalD.add(page2List.get(i).getPayS1());
            	singleDTotal = singleDTotal.add(page2List.get(i).getSingleDTotal());
            }else if(page2List.get(i).getDm().equals("M")){
            	payK1TotalM = payK1TotalM.add(page2List.get(i).getPayK1());
            	payK2TotalM = payK2TotalM.add(page2List.get(i).getPayK2());
            	payL1TotalM = payL1TotalM.add(page2List.get(i).getPayL1());
            	payL2TotalM = payL2TotalM.add(page2List.get(i).getPayL2());
            	payS1TotalM = payS1TotalM.add(page2List.get(i).getPayS1());
            	singleMTotal = singleMTotal.add(page2List.get(i).getSingleMTotal());
            }
        	if((i+1)%2 == 0){
        		addColumnAssignVAlignment(table, 8, 1, "" , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 5, 1, page2List.get(i).getDmStr() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 8, 1, page2List.get(i).getPayK1().toString() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 8, 1, page2List.get(i).getPayK2().toString() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 8, 1, page2List.get(i).getPayL1().toString() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 8, 1, page2List.get(i).getPayL2().toString() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 8, 1, page2List.get(i).getPayS1().toString() , fontCh12, 0, LEFT, RIGHT);
                if(page2List.get(i).getDm().equals("D")){
                	addColumnAssignVAlignment(table, 7, 1, page2List.get(i).getSingleDTotal().toString() , fontCh12, 0, LEFT, RIGHT);
                }else{
                	addColumnAssignVAlignment(table, 7, 1, page2List.get(i).getSingleMTotal().toString() , fontCh12, 0, LEFT, RIGHT);
                }
                addColumnAssignVAlignment(table, 60, 1, "　" , fontCh12, 0, RIGHT, RIGHT);
        	}else{
        		addColumnAssignVAlignment(table, 8, 1, page2List.get(i).getCrtUser() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 5, 1, page2List.get(i).getDmStr() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 8, 1, page2List.get(i).getPayK1().toString() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 8, 1, page2List.get(i).getPayK2().toString() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 8, 1, page2List.get(i).getPayL1().toString() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 8, 1, page2List.get(i).getPayL2().toString() , fontCh12, 0, LEFT, RIGHT);
                addColumnAssignVAlignment(table, 8, 1, page2List.get(i).getPayS1().toString() , fontCh12, 0, LEFT, RIGHT);
                if(page2List.get(i).getDm().equals("D")){
                	addColumnAssignVAlignment(table, 7, 1, page2List.get(i).getSingleDTotal().toString() , fontCh12, 0, LEFT, RIGHT);
                }else{
                	addColumnAssignVAlignment(table, 7, 1, page2List.get(i).getSingleMTotal().toString() , fontCh12, 0, LEFT, RIGHT);
                }
        	}
        	
        }
        
        while (writer.fitsPage(table)) {
            addColumnAssignLineSpace(table, 60, 1, " ", fontCh1, 0, 0, RIGHT);
        }

        for (int x = 0; x < 8; x++)
            table.deleteLastRow();
        
        //頁尾
        addColumnAssignVAlignment(table, 8, 1, "合計：" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 5, 1, "本日" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1,  payK1TotalD.toString() , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1,  payK2TotalD.toString() , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1,  payL1TotalD.toString() , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1,  payL2TotalD.toString() , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1,  payS1TotalD.toString() , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 7, 1,  singleDTotal.toString() , fontCh12, 0, LEFT, RIGHT);
        
        addColumnAssignVAlignment(table, 8, 1, "" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 5, 1, "本月" , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1,  payK1TotalM.toString() , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1,  payK2TotalM.toString() , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1,  payL1TotalM.toString() , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1,  payL2TotalM.toString() , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 8, 1,  payS1TotalM.toString() , fontCh12, 0, LEFT, RIGHT);
        addColumnAssignVAlignment(table, 7, 1,  singleMTotal.toString() , fontCh12, 0, LEFT, RIGHT);

        document.add(table);
        //return table;
    }

    public ByteArrayOutputStream doReport(UserBean userData, List<OtherRpt04Case> page1List, List<OtherRpt04Case> page2List, String crtDate) throws Exception {

        try {
            
            //page1 合計計算
            BigDecimal totalCountD = new BigDecimal(0);
            BigDecimal totalCountM = new BigDecimal(0);
            
            for(OtherRpt04Case caseData : page1List){
            	if(caseData.getRecCordCount() != null){
            		if(caseData.getDm().equals("D")){
            			totalCountD = totalCountD.add(caseData.getRecCordCount());
            		}else if(caseData.getDm().equals("M")){
            			totalCountM = totalCountM.add(caseData.getRecCordCount());
            		}
            	}
            }
            
            //計算總頁次 pag1固定為1頁
            if(page2List.size() > 0){
            	if(page2List.size() > PAGE_ROW_SIZE){
            		if(page2List.size()%PAGE_ROW_SIZE == 0){
            			PAGE_TOTAL = 1+(page2List.size()/PAGE_ROW_SIZE);
                	}else{
                		PAGE_TOTAL = 2+(page2List.size()/PAGE_ROW_SIZE);
                	}
            	}else{
            		PAGE_TOTAL = 2;
            	}
            	
            }

            printPage1(page1List, crtDate, totalCountD, totalCountM);
            printPage2(page2List, crtDate);

            document.close();
        }
        finally {
            document.close();
        }
        return bao;
    }

}

