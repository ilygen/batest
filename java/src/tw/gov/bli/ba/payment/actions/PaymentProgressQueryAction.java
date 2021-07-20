package tw.gov.bli.ba.payment.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.payment.cases.PaymentProcessCase;
import tw.gov.bli.ba.payment.cases.PaymentProgressQueryCase;
import tw.gov.bli.ba.payment.cases.PaymentStageDtlCase;
import tw.gov.bli.ba.payment.cases.PaymentUpdateCase;
import tw.gov.bli.ba.payment.forms.PaymentProcessQueryForm;
import tw.gov.bli.ba.payment.forms.PaymentProgressQueryForm;
import tw.gov.bli.ba.services.PaymentService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.helper.UserSessionHelper;


public class PaymentProgressQueryAction  extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PaymentProgressQueryAction.class);
    
    private PaymentService paymentService;
    
    
    /**
     * 繳款單作業 - 繳款單查詢作業 (bapm0d030l.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentProgressQueryForm queryForm = (PaymentProgressQueryForm) form;
        try {
        	String sequenceNo = "";  
        	if(StringUtils.isNotBlank(queryForm.getSequenceNo())){
        		sequenceNo = StringUtility.chtLeftPad(queryForm.getSequenceNo(), 5, "0");
        	}
        	List<PaymentProgressQueryCase> caseList = paymentService.getPaymentQueryData(queryForm.getIdn(), queryForm.getPaymentNo(), queryForm.getCaseNo(), sequenceNo, DateUtility.changeDateType(queryForm.getPaymentDate()));
        	if(caseList==null || caseList.size()<=0){
        		log.debug("繳款單作業 - 開單維護作業 - 查詢頁面  PaymentProcessAction.doQuery() 完成 ..查無資料. ");
				saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
	            return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
        	}
        	String benName = paymentService.getPaymentBenName(caseList.get(0).getIdn());
        	queryForm.setIdn(caseList.get(0).getIdn());//身分證號
    		queryForm.setPaymentName(benName);//姓名
    		CaseSessionHelper.setPaymentQueryList(caseList, request);
            FormSessionHelper.setPaymentQueryForm(queryForm, request);
            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
        	
        }catch (Exception e) {
	        // 設定查詢失敗訊息
	        log.error("PaymentProgressQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
	        saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
	        return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    /**
     * 繳款單作業 - 繳款單查詢作業 (bapm0d031m.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
    }
    
    /**
     * 繳款單作業 - 繳款單查詢作業 (bapm0d031m.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackFirstPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
    }
    /**
     * 繳款單作業 - 繳款單查詢作業 (bapm0d031m.jsp) <br>
     * 按下「明細」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
   public ActionForward doQueryDtl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   HttpSession session = request.getSession();
       UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
       PaymentProgressQueryForm queryForm = (PaymentProgressQueryForm) form;
       try {
    	   //按下明細查詢後，先把一些之前查的session刪除，以避免有些資料，某幾筆繳款單沒有，卻出現前一個session的資料
    	   CaseSessionHelper.removePaymentProcessQueryList(request);
    	   CaseSessionHelper.removePaymentProcessDetailList(request);
    	   CaseSessionHelper.removePaymentAssignLastList(request);
    	   CaseSessionHelper.removePaymentAssignDetailList(request);
    	   CaseSessionHelper.removePaymentInterestDetailList(request);
    	   CaseSessionHelper.removePaymentReprintList(request);
    	   CaseSessionHelper.removePaymentInterestDetailListForDb(request);
    	   int rowNum = Integer.parseInt(StringUtils.defaultString(request.getParameter("rowNum")));
    	   List<PaymentProgressQueryCase> caseList =  CaseSessionHelper.getPaymentQueryList(request);
    	   PaymentProgressQueryCase caseData = caseList.get(rowNum-1);
    	   if(StringUtils.isNotBlank(caseData.getPaymentNo())){
    		   PaymentProcessQueryForm nextForm = new PaymentProcessQueryForm();
    		   PaymentUpdateCase qryData =  paymentService.getPaymentProcessQueryList(caseData.getPaymentNo());//Form會用到的
        	   if(qryData != null){
        		   BeanUtility.copyProperties(nextForm, qryData);
        		   
        	   }else{
        		   // 設定查詢失敗訊息
        	       saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
        	       return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        	   }
        	   List<PaymentProcessCase> qryDataUnacpdtl = paymentService.getPaymentQueryDetailList(caseData.getPaymentNo());//BAPM0D012M
        	   if(qryDataUnacpdtl == null && qryDataUnacpdtl.size()<=0){
        		   // 設定查詢失敗訊息
        	       saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
        	       return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        	   }else{
        		   CaseSessionHelper.setPaymentProcessDetailList(qryDataUnacpdtl, request);
        	   }
        	   List<PaymentStageDtlCase> prsInterest = new ArrayList<PaymentStageDtlCase>();
        	   List<PaymentStageDtlCase> listData = new ArrayList<PaymentStageDtlCase>();
        	   prsInterest = paymentService.getPaymentInterestDetailListForDb(caseData.getPaymentNo());//BAPM0D013M
        	   if(prsInterest!=null && prsInterest.size()>0){
        		   CaseSessionHelper.setPaymentInterestDetailListForDb(prsInterest, request);
        		   listData = paymentService.getTryStageDataForQuery(prsInterest);
        		   CaseSessionHelper.setPaymentInterestDetailList(listData, request);
        	   }else{
        		   CaseSessionHelper.setPaymentInterestDetailListForDb(prsInterest, request);
        		   CaseSessionHelper.setPaymentInterestDetailList(listData, request);
        	   }
        	   List<PaymentStageDtlCase> assPayment = paymentService.getPaymentAssignDetailListForDb(caseData.getPaymentNo());//BAPM0D014,BAPM0D015
        	   if(assPayment!=null && assPayment.size()>0){
        		   CaseSessionHelper.setPaymentAssignLastList(assPayment, request);
        	   }
        	   
        	   List<PaymentStageDtlCase> confirmPayment = paymentService.getPaymentAssignDetailList(caseData.getPaymentNo(),0);//BAPM0D014,BAPM0D015
        	   if(confirmPayment!=null && confirmPayment.size()>0){
        		   CaseSessionHelper.setPaymentAssignDetailList(confirmPayment, request);
        	   }
        	   BigDecimal totExecAmt = BigDecimal.ZERO;
        	   BigDecimal totInterest = BigDecimal.ZERO;
        	   BigDecimal totAmt = BigDecimal.ZERO;
        	   BigDecimal tmpInterestExec = BigDecimal.ZERO;
        	   for(int i=0; i<confirmPayment.size();i++){
        		   totExecAmt = totExecAmt.add(confirmPayment.get(i).getExecAmt());
        		   totInterest = totInterest.add(confirmPayment.get(i).getPayInterest());
        		   totAmt = totAmt.add(confirmPayment.get(i).getPayAmt());
        		   tmpInterestExec = tmpInterestExec.add(confirmPayment.get(i).getExecAmt()).add(confirmPayment.get(i).getPayInterest());
            	   
        	   }
        	   nextForm.setPayTotAmt(totAmt);
        	   nextForm.setTotExec(totExecAmt);
        	   nextForm.setTotInterst(totInterest);
        	   nextForm.setTotStage(caseData.getNowStage());
        	   nextForm.setTotAmt(totExecAmt.add(totInterest).add(totAmt));
        	   FormSessionHelper.setPaymentProcessQueryForm(nextForm, request);
        	   FormSessionHelper.setPaymentQueryForm(queryForm, request);
        	   return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL_03);
    	   }else{
    		    log.error("PaymentProcessAction.doQuery() 發生錯誤");
   	        	saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
   	        	return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
    	   }
       	
       }catch (Exception e) {
	        // 設定查詢失敗訊息
	        log.error("PaymentProcessAction.doQueryDtl() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
	        saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
	        return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
       } 
   }
   public ActionForward doApnoData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   HttpSession session = request.getSession();
       UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
       PaymentProgressQueryForm queryForm = (PaymentProgressQueryForm) form;
       try {
    	   return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL);
    	   
	   }catch (Exception e) {
	       // 設定查詢失敗訊息
	       log.error("PaymentProcessAction.doQueryDtl() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
	       saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
	       return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
	  } 
   }	
   public ActionForward doInterestData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   HttpSession session = request.getSession();
       UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
       PaymentProgressQueryForm queryForm = (PaymentProgressQueryForm) form;
       try {
    	   return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL_02);
    	   
	   }catch (Exception e) {
	       // 設定查詢失敗訊息
	       log.error("PaymentProcessAction.doQueryDtl() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
	       saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
	       return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
	  } 
   }	
   public ActionForward doAssignData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   HttpSession session = request.getSession();
       UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
       PaymentProgressQueryForm queryForm = (PaymentProgressQueryForm) form;
       try {
    	   List<PaymentStageDtlCase> returnList = CaseSessionHelper.getPaymentAssignLastList(request);
    	   for(int i=0; i<returnList.size();i++){
	   			if(i==0){
	   				returnList.get(i).setNowStagForShow(String.valueOf(returnList.get(i).getNowStage()));
	   			}else{
	   				if(returnList.get(i).getNowStage() != returnList.get(i-1).getNowStage()){
	   					returnList.get(i).setNowStagForShow(String.valueOf(returnList.get(i).getNowStage()));
	   				}
	   			}
	   		}
    	   CaseSessionHelper.setPaymentAssignLastList(returnList, request);
    	   return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL_04);
    	   
	   }catch (Exception e) {
	       // 設定查詢失敗訊息
	       log.error("PaymentProcessAction.doQueryDtl() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
	       saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
	       return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
	  } 
   }
   
   public ActionForward doConfirmData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	   HttpSession session = request.getSession();
       UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
       PaymentProgressQueryForm queryForm = (PaymentProgressQueryForm) form;
       try {
    	   return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL_03);
    	   
	   }catch (Exception e) {
	       // 設定查詢失敗訊息
	       log.error("PaymentProcessAction.doQueryDtl() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
	       saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
	       return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
	  } 
   }	
   public static void setLog(Log log) {
	   PaymentProgressQueryAction.log = log;
   }

   public void setPaymentService(PaymentService paymentService) {
	   this.paymentService = paymentService;
   }
}
