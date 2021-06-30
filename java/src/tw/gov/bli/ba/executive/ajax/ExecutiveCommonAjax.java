package tw.gov.bli.ba.executive.ajax;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.domain.Bapandomk;
import tw.gov.bli.ba.domain.Baparam;
import tw.gov.bli.ba.services.ExecutiveAjaxService;
import tw.gov.bli.ba.util.DateUtility;


public class ExecutiveCommonAjax {
    private static Log log = LogFactory.getLog(ExecutiveCommonAjax.class);
    
    private ExecutiveAjaxService executiveAjaxService;
    
    
    /**
     * 自 處理註記參數檔 (<code>BAPANDOMK</code>) 取得 處理註記 下拉式選單的資料
     * 
     * @return
     */
    public List<Bapandomk> getNdomkOptionList(String letterType, String lpaymk, String kpaymk, String spaymk) {
        return executiveAjaxService.getNdomkOptionList( letterType, lpaymk, kpaymk, spaymk);
    }
    
    /**
     * 自 處理註記參數檔 (<code>BAPANDOMK</code>) 取得 行政救濟辦理情形 下拉式選單的資料
     * 
     * @return
     */
    public List<Baparam> getReliefOptionList(String reliefKind) {
        return executiveAjaxService.getReliefOptionList(reliefKind);
    }
    
    /**
     * 依傳入的條件取得 是否為已註銷 (<code>BAAPPBASE</code>) 的案件資料<br>
     * 
     * @param apNo 受理編號
     * @return 
     */
    public Integer getProcastatStatus(String apNo){
        Integer procastatStatus = executiveAjaxService.getProcastatStatus(apNo);
        return procastatStatus; 
    }
    
    public Integer getBabanCount(String apNo, String issuYm, String proDte, String clinNo){
        
        Integer babanCount = executiveAjaxService.getBabanCount(apNo, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(proDte), clinNo);

        return babanCount;
    }
    
    public String getBabanPayMk(String apNo, String issuYm, String proDte, String clinNo){
        
        String payMk = executiveAjaxService.getBabanPayMk(apNo, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(proDte), clinNo);
        
        return payMk;
    }
    
    public String[] getHpTitleStr(String hpNo){
    	String hpTitleStr = "";
  
        hpTitleStr = executiveAjaxService.selectHpSnamBy(hpNo);
        
        if(StringUtils.isBlank(hpTitleStr)){
    		hpTitleStr = "";
    	}
        
        //檢查是否為列管醫院
    	int bbcmf13Count = executiveAjaxService.selectBbcmf13CountBy(hpNo,hpTitleStr);
    	
    	//if(bbcmf13Count > 0){
    	//	hpTitleStr = hpTitleStr+" (此為列管醫院)";
    	//}
    	String[] hpTitle = new String[2];
    	hpTitle[0] = hpTitleStr;
    	
    	if(bbcmf13Count > 0){
    		hpTitle[1] = "Y";
    	}else{
    		hpTitle[1] = "N";
    	}
        
        return hpTitle;
    }

    public void setExecutiveAjaxService(ExecutiveAjaxService executiveAjaxService) {
        this.executiveAjaxService = executiveAjaxService;
    }
    
    

}
