package tw.gov.bli.ba.payment.actions;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
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
import tw.gov.bli.ba.payment.cases.PaymentReprintCase;
import tw.gov.bli.ba.payment.cases.PaymentStageDtlCase;
import tw.gov.bli.ba.payment.cases.PaymentUpdateCase;
import tw.gov.bli.ba.payment.forms.PaymentProcessQueryForm;
import tw.gov.bli.ba.payment.forms.PaymentReprintForm;
import tw.gov.bli.ba.payment.rpt.PaymentReport;
import tw.gov.bli.ba.services.PaymentService;
import tw.gov.bli.ba.services.QueryService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 繳款單作業 - 繳款單列印作業
 * 
 * @author  Zehua
 */
public class PaymentReprintAction  extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PaymentReprintAction.class);
    
    private PaymentService paymentService;
    private QueryService queryService;
    /**
     * 繳款單作業 - 繳款單列印作業 (bapm0d020l.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentReprintForm queryForm = (PaymentReprintForm) form;
        try {
        	String prtDt = queryForm.getPrintDt();//show:表示補印，已經有日期;空:表示列印，未有日期
        	String sequenceNo = "";  
        	if(StringUtils.isNotBlank(queryForm.getSequenceNo())){
        		sequenceNo = StringUtility.chtLeftPad(queryForm.getSequenceNo(), 5, "0");
        	}
        	List<PaymentReprintCase> queryList = paymentService.getPaymentReprintList(queryForm.getIdn(),queryForm.getPaymentNo(),queryForm.getCaseNo(),prtDt,  sequenceNo, DateUtility.changeDateType(queryForm.getPaymentDate()));
        	if(queryList==null || queryList.size()<=0){
        		saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
    	        return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
				
        	}
        	String benName = paymentService.getPaymentBenName(queryList.get(0).getIdn());
        	queryForm.setIdn(queryList.get(0).getIdn());//身分證號
			queryForm.setPaymentName(benName);//姓名
			// 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentReprintList(queryList, request);
            FormSessionHelper.setPaymentReprintForm(queryForm, request);
            log.debug("繳款單作業 - 繳款單列印作業 - 查詢頁面  PaymentReprintAction.doPrint() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_QUERY_LIST);        	
        }catch (Exception e) {
	        // 設定查詢失敗訊息
	        log.error("PaymentProcessAction.doPrint() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
	        saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
	        return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    /**
     * 繳款單作業 - 繳款單補印作業 (bapm0d021l.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCompList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentReprintForm queryForm = (PaymentReprintForm) form;
        try {
        	String prtDt = queryForm.getPrintDt();//show:表示補印，已經有日期;空:表示列印，未有日期
        	String sequenceNo = "";  
        	if(StringUtils.isNotBlank(queryForm.getSequenceNo())){
        		sequenceNo = StringUtility.chtLeftPad(queryForm.getSequenceNo(), 5, "0");
        	}
        	List<PaymentReprintCase> queryList = paymentService.getPaymentReprintList(queryForm.getIdn(),queryForm.getPaymentNo(),queryForm.getCaseNo(),prtDt,  sequenceNo, DateUtility.changeDateType(queryForm.getPaymentDate()));
        	if(queryList==null || queryList.size()<=0){
        		saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
    	        return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
				
        	}
      	    String[] idForConfirm = queryForm.getIdForConfirm().split(",");//選擇補印的繳款單號
	      	PaymentReprintCase paymentCase = queryList.get(Integer.parseInt(idForConfirm[0]));//單選，因此取第0個即可(選擇某張繳款單)
	      	List<PaymentReprintCase> dtlList = paymentService.getPaymentReprintdtlList(paymentCase);
	      	if(dtlList==null || dtlList.size()<=0){
	      		log.debug("繳款單作業 - 繳款單列印作業 - 查詢頁面  PaymentReprintAction.doCompList() 完成 ...查無資料 ");
		        saveMessages(request, DatabaseMessageHelper.getNoDataMessage());
				return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
        	}
	      
			// 將 查詢結果清單 存到 Request Scope
	        CaseSessionHelper.setPaymentReprintList(dtlList, request);
	        FormSessionHelper.setPaymentReprintForm(queryForm, request);
	        log.debug("繳款單作業 - 繳款單列印作業 - 查詢頁面  PaymentReprintAction.doPrint() 完成 ... ");
	        return mapping.findForward(ConstantKey.FORWARD_PAYMENT_COMP_PRINT);
        }catch (Exception e) {
	        // 設定查詢失敗訊息
	        log.error("PaymentReprintAction.doCompList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
	        saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
	        return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    	
    }
    /**
     * 繳款單作業 - 繳款單列印作業 (bapm0d021m.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrintFirst(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
 	    HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentReprintForm queryForm = (PaymentReprintForm) form;
        try {
     	   List<PaymentReprintCase> caseList = CaseSessionHelper.getPaymentReprintList(request);
     	   String[] idForConfirm = queryForm.getIdForConfirm().split(",");//列印作業改為只能單選所以idForConfirm.length只會有一個
  	 	   for(int i=0; i<idForConfirm.length; i++){
  	 		   String paymentNo = caseList.get(Integer.parseInt(idForConfirm[i])).getPaymentNo();//選取的繳款單號
  	 		   PaymentUpdateCase qryData =  paymentService.getPaymentProcessQueryList(paymentNo);//繳款單主檔資料
  	 		   PaymentProcessQueryForm rptForm = new PaymentProcessQueryForm();
    		     
  	 		   if(qryData != null){
  	    		   BeanUtility.copyProperties(rptForm, qryData);
  	    		   FormSessionHelper.setPaymentProcessQueryForm(rptForm, request);
  	    	   }else{
  	    		   // 設定查詢失敗訊息
  	    	       saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
  	    	       return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
  	    	   }
  	    	   List<PaymentProcessCase> qryDataUnacpdtl = paymentService.getPaymentProcessDetailList(paymentNo);//應收未收資料
  	    	   if(qryDataUnacpdtl == null && qryDataUnacpdtl.size()<=0){
  	    		   // 設定查詢失敗訊息
  	    	       saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
  	    	       return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
  	    	   }else{
  	    		   CaseSessionHelper.setPaymentProcessDetailList(qryDataUnacpdtl, request);
  	    	   }
  	    	   List<PaymentStageDtlCase> prsInterest = paymentService.getPaymentInterestDetailListForDb(paymentNo);//試算利息資料
  	    	   if(prsInterest!=null && prsInterest.size()>0){
  	    		   CaseSessionHelper.setPaymentInterestDetailListForDb(prsInterest, request);
  	    	   }
  	    	   List<PaymentStageDtlCase> assPayment = paymentService.getPaymentAssignDetailListForDb(paymentNo);//本金分配明細
  	    	   if(assPayment!=null && assPayment.size()>0){
  	    		   CaseSessionHelper.setPaymentAssignLastList(assPayment, request);
  	    	   }
  	    	   
  	    	   List<PaymentStageDtlCase> confirmPayment = paymentService.getPaymentAssignDetailList(paymentNo,0);//利息及執行費的分配明細
  	    	   if(confirmPayment!=null && confirmPayment.size()>0){
  	    		   CaseSessionHelper.setPaymentAssignDetailList(confirmPayment, request);
  	    	   }
  	    	   //印報表
  	    	   List<PaymentStageDtlCase> paymentList =  CaseSessionHelper.getPaymentAssignDetailList(request);
  	    	   List<PaymentProcessCase> apnoList = CaseSessionHelper.getPaymentProcessDetailList(request);
  	    	   String printDate = DateUtility.getNowChineseDate();
  	    	   ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
  	    	   String mApno = "";
  	    	   for (int j = 0; j < apnoList.size(); j++) {
  	    		   if (StringUtils.equals(apnoList.get(j).getMapnoMk(), "1")) {
  	    			   mApno = apnoList.get(j).getApno();
  	    		   }
  	    	   }
  	    	   String appName = "";
  	    	   String mPayKind = "";
  	    	   String paymentSex = "";
  	    	   PaymentProcessCase paymentData = paymentService.getPaymentBaseData(mApno, queryForm.getIdn());
  	    	   if (paymentData != null) {
  	    		   appName = paymentData.getBenName();
  	    		   mPayKind = paymentData.getPayKind();
  	    		   paymentSex = paymentData.getPaymentSex();
  	    	   }
  	    	   String evtName = paymentService.getEvtNameFromBaappbaseByIdnAndApno(queryForm.getIdn(), mApno);
  	    	   String empExt = queryService.getEmpExtData(mApno);// 分機
  	    	   
  	    	   int execAmt = 0;
  	    	   int interestAmt = 0;
  	    	   BigDecimal execAmtInterestAmt = BigDecimal.ZERO;
  	    	   for(int j=0; j<paymentList.size(); j++){//計算繳款單會用到的金額合計
  	    		   execAmt = execAmt + paymentList.get(j).getExecAmt().intValue();//總執行費
  	    		   interestAmt = interestAmt + paymentList.get(j).getPayInterest().intValue();//總利息費
  	    		   execAmtInterestAmt = paymentList.get(j).getExecAmt().add(paymentList.get(j).getPayInterest());//應繳總額
  	    	   }
  	    	   rptForm.setTotExec(BigDecimal.valueOf(execAmt));//總執行費
  	    	   rptForm.setTotInterst(BigDecimal.valueOf(interestAmt));//總利息
  	    	   rptForm.setTotAmt(rptForm.getPayTotAmt());//應繳總額(含利息與執行費)
  	    	   rptForm.setPayTotAmt(rptForm.getPayTotAmt().subtract(execAmtInterestAmt));//應繳總額(扣除利息與執行費)
  	    	   rptForm.setPrtDate(DateUtility.getNowChineseDate());//列印日期
  	    	   FormSessionHelper.setPaymentProcessQueryForm(rptForm, request);
  	    	   PaymentReport report = new PaymentReport();
  	           baoOutput = report.doReport(null, mApno,evtName, appName, mPayKind, empExt, paymentList, apnoList, rptForm, paymentSex);  
  	           // 設定回傳回 Client 端之檔案大小, 若不設定,
  	           // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
  	           response.setHeader("Content-disposition", "attachment; filename=\"" + "PaymentReport_" + printDate + ".pdf" + "\"");
  	           response.setHeader("Content-Encoding", "pdf");
  	           response.setContentType("application/pdf"); // 設定MIME
  	           response.setHeader("Expires", "0");
  	           response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
  	           response.setHeader("Pragma", "public");
  	           response.setContentLength(baoOutput.size());
  	           paymentService.updatePrintDate(DateUtility.getNowWestDate(),paymentNo, paymentList);//更新列印日期及barcode，更新後只能補印
  	           // 傳回 Client 端
  	           ServletOutputStream sout = null;
  	           try {
  	               sout = response.getOutputStream();
  	               baoOutput.writeTo(sout);
  	               sout.flush();                   
  	           }
  	           catch (Exception e) {
  	           }
  	           finally {
  	               if (baoOutput != null)
  	                   baoOutput.close();
  	               if (sout != null)
  	                   sout.close();
  	           }
  	 	   }
        }catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentReprintAction.doPrintFirst() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
        return null;
    }
    /**
     * 繳款單作業 - 繳款單補印作業 (bapm0d022m.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCompPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentReprintForm queryForm = (PaymentReprintForm) form;
        try {
	     	   List<PaymentReprintCase> caseList = CaseSessionHelper.getPaymentReprintList(request);
	     	   String[] idForConfirm = queryForm.getIdForConfirmDtl().split(",");
	     	   List<String> paymentNoDtlList = new ArrayList<String>();
	     	   //0~13
	  	 	   String paymentNo="";
	  	 	   String paymentDate = "";
	     	   for(int i=0; i<idForConfirm.length; i++){//補印作業可以選擇列印的期別(多選)
	  	 		   String paymentNoDtl = caseList.get(Integer.parseInt(idForConfirm[i])).getPaymentNo();
	  	 		   paymentDate = caseList.get(Integer.parseInt(idForConfirm[i])).getPrtDate();//以列印的日期為主
	  	 		   paymentNoDtlList.add(paymentNoDtl);//補印的單號(有帶期別的明細)
	  	 		   paymentNo = paymentNoDtl.substring(0,13)+"00";//繳款單號
	  	 	   }	   
  	 		   PaymentUpdateCase qryData =  paymentService.getPaymentProcessQueryList(paymentNo);//繳款單主檔資料
  	 		   PaymentProcessQueryForm rptForm = new PaymentProcessQueryForm();
    		     
  	 		   if(qryData != null){
  	    		   BeanUtility.copyProperties(rptForm, qryData);
  	    		   FormSessionHelper.setPaymentProcessQueryForm(rptForm, request);
  	    	   }else{
  	    		   // 設定查詢失敗訊息
  	    	       saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
  	    	       return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
  	    	   }
  	    	   List<PaymentProcessCase> qryDataUnacpdtl = paymentService.getPaymentProcessDetailList(paymentNo);//應收未收資料
  	    	   if(qryDataUnacpdtl == null && qryDataUnacpdtl.size()<=0){
  	    		   // 設定查詢失敗訊息
  	    	       saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
  	    	       return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
  	    	   }else{
  	    		   CaseSessionHelper.setPaymentProcessDetailList(qryDataUnacpdtl, request);
  	    	   }
  	    	   List<PaymentStageDtlCase> prsInterest = paymentService.getPaymentInterestDetailListForDb(paymentNo);//試算利息資料
  	    	   if(prsInterest!=null && prsInterest.size()>0){
  	    		   CaseSessionHelper.setPaymentInterestDetailListForDb(prsInterest, request);
  	    	   }
  	    	   List<PaymentStageDtlCase> assPayment = paymentService.getPaymentAssignDetailListForDb(paymentNo);//本金分配明細
  	    	   if(assPayment!=null && assPayment.size()>0){
  	    		   CaseSessionHelper.setPaymentAssignLastList(assPayment, request);
  	    	   }
  	    	   
  	    	   List<PaymentStageDtlCase> confirmPayment = paymentService.getPaymentAssignDetailList(paymentNo,0);//利息及執行費的分配明細
  	    	   if(confirmPayment!=null && confirmPayment.size()>0){
  	    		   CaseSessionHelper.setPaymentAssignDetailList(confirmPayment, request);
  	    	   }
  	    	   //印報表
  	    	   List<PaymentStageDtlCase> paymentList =  CaseSessionHelper.getPaymentAssignDetailList(request);
  	    	   List<PaymentProcessCase> apnoList = CaseSessionHelper.getPaymentProcessDetailList(request);
  	    	   String printDate = DateUtility.getNowChineseDate();
  	    	   ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
  	    	   String mApno = "";
	    	   for (int j = 0; j < apnoList.size(); j++) {
	    		   if (StringUtils.equals(apnoList.get(j).getMapnoMk(), "1")) {
	    			   mApno = apnoList.get(j).getApno();
	    		   }
	    	   }
	    	   String appName = "";
	    	   String mPayKind = "";
	    	   String paymentSex = "";
	    	   PaymentProcessCase paymentData = paymentService.getPaymentBaseData(mApno, queryForm.getIdn());
	    	   if (paymentData != null) {
	    		   appName = paymentData.getBenName();
	    		   mPayKind = paymentData.getPayKind();
	    		   paymentSex = paymentData.getPaymentSex();
	    	   }
	    	   String evtName = paymentService.getEvtNameFromBaappbase(queryForm.getIdn());
	    	   String empExt = queryService.getEmpExtData(mApno);// 分機
  	    	   int execAmt = 0;
  	    	   int interestAmt = 0;
  	    	   for(int j=0; j<paymentList.size(); j++){//計算繳款單會用到的金額合計
  	    		   execAmt = execAmt + paymentList.get(j).getExecAmt().intValue();//總執行費
  	    		   interestAmt = interestAmt + paymentList.get(j).getPayInterest().intValue();//總利息費
  	    		   
  	    	   }
  	    	   rptForm.setTotExec(BigDecimal.valueOf(execAmt));//總執行費
  	    	   rptForm.setTotInterst(BigDecimal.valueOf(interestAmt));//總利息
  	    	   rptForm.setTotAmt(rptForm.getPayTotAmt());//應繳總額(含利息與執行費)
  	    	   //rptForm.setPrtDate(DateUtility.getNowChineseDate());
  	    	   FormSessionHelper.setPaymentProcessQueryForm(rptForm, request);
  	    	   PaymentReport report = new PaymentReport();
  	           baoOutput = report.doReportComp(paymentDate, paymentNoDtlList, null,evtName,  mApno, appName, mPayKind, empExt, paymentList, apnoList, rptForm, paymentSex);  
  	           //(InputStream inFile,String mApno, String appName, String mPayKind, String empExt, List<PaymentStageDtlCase> caseList, List<PaymentProcessQueryCase> apnoList, PaymentProcessQueryForm queryForm) 
  	           // 設定回傳回 Client 端之檔案大小, 若不設定,
  	           // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
  	           //response.setHeader("Content-disposition", "attachment; filename=\"MonthlyRpt23_" + printDate + ".pdf" + "\"");
  	           response.setHeader("Content-disposition", "attachment; filename=\"" + "PaymentReport_" + printDate + ".pdf" + "\"");
  	           response.setHeader("Content-Encoding", "pdf");
  	           response.setContentType("application/pdf"); // 設定MIME
  	           response.setHeader("Expires", "0");
  	           response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
  	           response.setHeader("Pragma", "public");
  	           response.setContentLength(baoOutput.size());
  	          
  	           // 傳回 Client 端
  	           ServletOutputStream sout = null;
  	           try {
  	               sout = response.getOutputStream();
  	               baoOutput.writeTo(sout);
  	               sout.flush();                   
  	           }
  	           catch (Exception e) {
  	           }
  	           finally {
  	               if (baoOutput != null)
  	                   baoOutput.close();
  	               if (sout != null)
  	                   sout.close();
  	           }
        }catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentReprintAction.doCompPrint() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
        return null;
  	   
    }
   public static void setLog(Log log) {
	   PaymentReprintAction.log = log;
   }

   public void setPaymentService(PaymentService paymentService) {
	   this.paymentService = paymentService;
   }
	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}
}
