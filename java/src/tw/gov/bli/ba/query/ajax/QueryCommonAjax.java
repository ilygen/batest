package tw.gov.bli.ba.query.ajax;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bccmf42;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.query.cases.PaymentQueryIssuPayDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryMasterCase;
import tw.gov.bli.ba.query.cases.PaymentQueryNpIssuDataCase;
import tw.gov.bli.ba.query.helper.CaseSessionHelper;
import tw.gov.bli.ba.services.QueryAjaxService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.helper.HttpHelper;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * Ajax Service for 給付審核
 * 
 * @author Rickychi
 */
public class QueryCommonAjax {
    private static Log log = LogFactory.getLog(QueryCommonAjax.class);

    private QueryAjaxService queryAjaxService;

    /**
     * 依傳入條件 檢查核定格式<br>
     * 
     * @param apNo 受理編號
     * @param optionYm 篩選下拉選單年月
     * @param seqNo 受款人序號
     * @param qryCond 查詢條件
     * @return 內含<code>PaymentQueryIssuPayDataCase</code>物件的List
     */
    public PaymentQueryIssuPayDataCase[] flashIssuDataList(String apNo, String optionYm, String seqNo, String qryCond) {
        log.debug("執行 queryAjaxService.flashIssuDataList 開始 ...");

        return queryAjaxService.flashIssuDataList(apNo, optionYm, seqNo, qryCond);
    }

    /**
     * 依傳入條件 篩選核定資料<br>
     * 
     * @param optionYm 篩選下拉選單年月
     * @param qryCond 查詢條件
     * @return 內含<code>PaymentQueryNpIssuDataCase</code>物件的List
     */
    public PaymentQueryNpIssuDataCase[] flashNpIssuDataList(String optionYm, String qryCond) {
        log.debug("執行 queryAjaxService.flashNpIssuDataList 開始 ...");

        return queryAjaxService.flashNpIssuDataList(optionYm, qryCond);
    }
    
    /**
     * 依傳入條件 檢查保密檔<br>
     * 
     * @param apNo 受理編號
     * @param 
     * @return 
     */
    public String checkSecrecy(String apNo, String benIdnNo, String benName, String benBrDate, String qryCond, String startYm, String endYm) {
    	log.debug("執行 QueryCommonAjax.checkSecrecy 開始 ...");

        HttpServletRequest request = HttpHelper.getHttpRequest();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        
        int checkPrpNo = queryAjaxService.selectBccmf01CheckCount(userData.getEmpNo()).intValue();
        
        String message = ""; // 是否要show保密警告視窗
        
        List<PaymentQueryMasterCase> queryList = queryAjaxService.selectPaymentQueryMasterData(apNo, benIdnNo, benName, DateUtility.changeDateType(benBrDate), qryCond, DateUtility.changeChineseYearMonthType(startYm), DateUtility.changeChineseYearMonthType(endYm));

        if(queryList.size() <= 0){
        	if(StringUtils.isNotBlank(benIdnNo)){
        		int checkCount = queryAjaxService.checkIdnNoExist(benIdnNo).intValue();
        		
        		if(checkCount > 0){
        			message = "Y";
        		}
        	}
        }else{
        	
        	for(PaymentQueryMasterCase caseData : queryList){
        		
        		List<Baappbase> idnNoList = queryAjaxService.selectIdnNoDataBy(caseData.getApNo());
        		
        		for(Baappbase idnNoData : idnNoList){
        			
        			Bccmf42 bccmf42Data = queryAjaxService.selectPayTypBy(idnNoData.getIdnNo());
        			
        			if(bccmf42Data != null){

        			    if(StringUtils.isBlank(bccmf42Data.getPayTyp())){
        				
        			    	caseData.setSecrecy("Y");
        			    	message = "Y";
        				
        			    }else{
        				
        			    	if(StringUtils.equals(caseData.getPagePayKind(), "L")){
        			    		if(StringUtils.indexOf(bccmf42Data.getPayTyp(), "41") >= 0){
        			    			caseData.setSecrecy("Y");
        				    		message = "Y";
        				    	}
        			    	}else if(StringUtils.equals(caseData.getPagePayKind(), "K")){
        				    	if(StringUtils.indexOf(bccmf42Data.getPayTyp(), "31") >= 0){
        				    		caseData.setSecrecy("Y");
        				    		message = "Y";
        				    	}
        				    }else if(StringUtils.equals(caseData.getPagePayKind(), "S")){
        				    	if(StringUtils.indexOf(bccmf42Data.getPayTyp(), "51") >= 0){
        				    		caseData.setSecrecy("Y");
        				    		message = "Y";
        			    		}
        			    	}
        				
        			    }
        			
        		    }
        		}
        		
        		if(checkPrpNo <= 0 && StringUtils.equals(caseData.getSecrecy(), "Y")){
        			caseData.setDetailLock("Y");
        		}
        		
        	}
        	
        }
        
        CaseSessionHelper.setPaymentQueryMasterCaseList(queryList, request);

        log.debug("執行 QueryCommonAjax.checkSecrecy 結束 ...");
        
        return message;
    }    

    public void setQueryAjaxService(QueryAjaxService queryAjaxService) {
        this.queryAjaxService = queryAjaxService;
    }

}
