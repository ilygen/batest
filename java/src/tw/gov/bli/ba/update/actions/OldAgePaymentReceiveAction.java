package tw.gov.bli.ba.update.actions;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.LocalPfpcckyuserrecDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.query.cases.UpdateLogQueryCase;
import tw.gov.bli.ba.rpt.cases.BatchPaymentReceiveDataCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt08Case;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.AccountsReceivableDataCase;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.CashReceiveDataCase;
import tw.gov.bli.ba.update.cases.CheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.cases.OldAgePaymentReceiveDataCase;
import tw.gov.bli.ba.update.cases.RemittanceReceiveDataCase;
import tw.gov.bli.ba.update.forms.ApplicationDataUpdateForm;
import tw.gov.bli.ba.update.forms.CheckMarkLevelAdjustForm;
import tw.gov.bli.ba.update.forms.OldAgePaymentReceiveForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.DateUtil;
import tw.gov.bli.pf.model.service.Pfxx0w040Service;
import tw.gov.bli.pf.model.vo.PfpcckyVO;
import tw.gov.bli.pf.model.vo.PfpcckyuserrecVO;

/**
 * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理
 * @author Noctis
 *
 */
public class OldAgePaymentReceiveAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OldAgePaymentReceiveAction.class);
    private UpdateService updateService;
    private Pfxx0w040Service pfxx0w040Service;
    private LocalPfpcckyuserrecDao localpfpcckyuserrecDao;
    
    // 更正作業 - 應收收回處理作業 - 年金應收收回處理 - 現金收回
    private static final String CASH_PAGE = "cashPage";
    private static final String RECEIVABLES_PAGE = "receivablesPage";
    // 更正作業 - 應收收回處理作業 - 年金應收收回處理 - 退匯收回
    private static final String REMITTANCE_PAGE = "remittancePage";
    // 更正作業 - 應收收回處理作業 - 年金應收收回處理 - 註銷退匯收回
    private static final String CANCEL_CASH_PAGE = "cancelCashPage";
    // 更正作業 - 應收收回處理作業 - 年金應收收回處理 - 註銷退匯收回
    private static final String CANCEL_REMITTANCE_PAGE = "cancelRemittancePage";

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 (bamo0d100c.jsp) <br>
     * 按下「收回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 OldAgePaymentReceiveAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgePaymentReceiveForm queryForm = (OldAgePaymentReceiveForm) form;

        try {
        	//受理編號
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            
            //現金收回
            if(queryForm.getReceiveType().equals("1")){
            	//查詢 收回狀況
                OldAgePaymentReceiveDataCase caseData = updateService.selectCashReceiveDataBy(apno,queryForm.getApNo1());
            	
            	if(caseData == null ){
            		saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            	}
            	
            	//查詢 應收資料
/**            	List<AccountsReceivableDataCase> accountsReceivableDataCaseList = updateService.selectAccountsReceivableDataListBy(apno,queryForm.getSeqNo(),"","");*/
            	//查詢退現資料
            	List<CashReceiveDataCase> cashReceiveDataCaseList = updateService.selectCashReceiveDataListBy(apno,queryForm.getApNo1());

            	//測試資料--------------------------------------------------
//            	OldAgePaymentReceiveDataCase testCaseData = new OldAgePaymentReceiveDataCase();
//            	testCaseData.setApNo("L20000000001"); // 受理編號
//            	testCaseData.setSeqNo("0000"); // 受款人序
//            	testCaseData.setEvtName("測試人"); // 事故者姓名
//            	testCaseData.setEvtIdnNo("A123456789"); // 事故者身分證號
//            	testCaseData.setEvtBrDate("19800205"); // 事故者出生日期
//            	testCaseData.setEvtJobDate("19850205") ;// 事故日期
//            	
//            	List<AccountsReceivableDataCase> testAccountsCaseDataList = new ArrayList<AccountsReceivableDataCase>();
//            	for(int i = 0 ; i<5 ; i++){
//            		AccountsReceivableDataCase accountsCaseData = new AccountsReceivableDataCase();
//            		accountsCaseData.setApNo("L20000000001");
//            		accountsCaseData.setIssuYm("0980708");
//            		accountsCaseData.setPayYm("0980101");
//            		accountsCaseData.setRecAmt(new BigDecimal(5000));
//            		accountsCaseData.setRecRem(new BigDecimal(10000));
//            		accountsCaseData.setSeqNo("0000");
//            		accountsCaseData.setSts("2");
//            		accountsCaseData.setUnacpDate("19900101");
//            		testAccountsCaseDataList.add(accountsCaseData);
//            	}
//            	
//            	List<CashReceiveDataCase> testCashReceiveDataCaseList = new ArrayList<CashReceiveDataCase>();
//            	for(int i = 0 ; i<5 ; i++){
//            		CashReceiveDataCase cashReceiveDataCaseData = new CashReceiveDataCase();
//            		cashReceiveDataCaseData.setInsKd("L");
//            		cashReceiveDataCaseData.setBli_Account_Code("888");
//            		cashReceiveDataCaseData.setBookedBook("入帳方式");
//            		cashReceiveDataCaseData.setBkAccountDt("19900101");
//            		cashReceiveDataCaseData.setBatchNo("234567");
//            		cashReceiveDataCaseData.setSerialNo("0000");
//            		cashReceiveDataCaseData.setCash_Amt(new BigDecimal(10000));
//            		cashReceiveDataCaseData.setDivSeq("19900101");
//            		cashReceiveDataCaseData.setIndex_No("19900101");
//            		cashReceiveDataCaseData.setMoveToCode("19900101");
//            		cashReceiveDataCaseData.setTempHandleNo("19900101");
//            		testCashReceiveDataCaseList.add(cashReceiveDataCaseData);
//            	}
//            	
            	//測試資料--------------------------------------------------
            	
            	FormSessionHelper.setOldAgePaymentReceiveForm(queryForm,request);
            	
            	//測試用
//            	CaseSessionHelper.setOldAgePaymentReceiveDataCase(testCaseData, request);
//            	CaseSessionHelper.setAccountsReceivableDataCaseList(testAccountsCaseDataList, request);
//            	CaseSessionHelper.setCashReceiveDataCaseList(testCashReceiveDataCaseList, request);
            	
            	CaseSessionHelper.setOldAgePaymentReceiveDataCase(caseData, request);
/**            	CaseSessionHelper.setAccountsReceivableDataCaseList(accountsReceivableDataCaseList, request);*/
            	CaseSessionHelper.setCashReceiveDataCaseList(cashReceiveDataCaseList, request);
            	
            	log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 OldAgePaymentReceiveAction.doQuery() 完成 ... ");

                return mapping.findForward(CASH_PAGE);
                
            //退匯收回    
            }else{
            	//查詢 收回狀況
            	OldAgePaymentReceiveDataCase caseData = updateService.selectRemittanceReceiveDataBy(apno,queryForm.getSeqNo(),queryForm.getApNo1());
            	
            	if(caseData == null ){
            		saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            	}
            	
            	//查詢 應收資料
            	List<AccountsReceivableDataCase> accountsReceivableDataCaseList = updateService.selectAccountsReceivableDataListBy(apno,queryForm.getSeqNo(),"","");
            	//查詢退匯資料
            	List<RemittanceReceiveDataCase> remittanceReceiveDataCaseList = updateService.selectRemittanceReceiveDataListBy(apno,queryForm.getSeqNo());
            	
            	//測試資料--------------------------------------------------
//            	OldAgePaymentReceiveDataCase testCaseData = new OldAgePaymentReceiveDataCase();
//            	testCaseData.setApNo("L20000000001"); // 受理編號
//            	testCaseData.setSeqNo("0000"); // 受款人序
//            	testCaseData.setEvtName("測試人"); // 事故者姓名
//            	testCaseData.setEvtIdnNo("A123456789"); // 事故者身分證號
//            	testCaseData.setEvtBrDate("19800205"); // 事故者出生日期
//            	testCaseData.setEvtJobDate("19850205") ;// 事故日期
//            	
//            	List<AccountsReceivableDataCase> testAccountsCaseDataList = new ArrayList<AccountsReceivableDataCase>();
//            	for(int i = 0 ; i<5 ; i++){
//            		AccountsReceivableDataCase accountsCaseData = new AccountsReceivableDataCase();
//            		accountsCaseData.setApNo("L20000000001");
//            		accountsCaseData.setIssuYm("0980708");
//            		accountsCaseData.setPayYm("0980101");
//            		accountsCaseData.setRecAmt(new BigDecimal(55555));
//            		accountsCaseData.setRecRem(new BigDecimal(5000));
//            		accountsCaseData.setSeqNo("0000");
//            		accountsCaseData.setSts("2");
//            		accountsCaseData.setUnacpDate("19900101");
//            		testAccountsCaseDataList.add(accountsCaseData);
//            	}
//            	
//            	List<RemittanceReceiveDataCase> testRemittanceReceiveDataCaseList = new ArrayList<RemittanceReceiveDataCase>();
//            	for(int i = 0 ; i<5 ; i++){
//            		RemittanceReceiveDataCase remittanceReceiveDataCaseData = new RemittanceReceiveDataCase();
//            		remittanceReceiveDataCaseData.setApNo("L");
//            		remittanceReceiveDataCaseData.setSeqNo("888");
//            		remittanceReceiveDataCaseData.setPayYm("19900101");
//            		remittanceReceiveDataCaseData.setOriIssuYm("19900101");
//            		remittanceReceiveDataCaseData.setBrChkDate("19800101");
//            		remittanceReceiveDataCaseData.setRemitAmt(new BigDecimal(13000));
//            		remittanceReceiveDataCaseData.setIssuKind("11111");
//            		remittanceReceiveDataCaseData.setBrNote("19900101");
//            		testRemittanceReceiveDataCaseList.add(remittanceReceiveDataCaseData);
//            	}
            	
            	//測試資料--------------------------------------------------
            	
            	FormSessionHelper.setOldAgePaymentReceiveForm(queryForm,request);
            	
            	//測試用
            	//CaseSessionHelper.setOldAgePaymentReceiveDataCase(testCaseData, request);
            	//CaseSessionHelper.setAccountsReceivableDataCaseList(testAccountsCaseDataList, request);
            	//CaseSessionHelper.setRemittanceReceiveDataCaseList(testRemittanceReceiveDataCaseList, request);
            	
            	CaseSessionHelper.setOldAgePaymentReceiveDataCase(caseData, request);
            	CaseSessionHelper.setAccountsReceivableDataCaseList(accountsReceivableDataCaseList, request);
            	CaseSessionHelper.setRemittanceReceiveDataCaseList(remittanceReceiveDataCaseList, request);
            	
            	log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 OldAgePaymentReceiveAction.doQuery() 完成 ... ");

                return mapping.findForward(REMITTANCE_PAGE);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldAgePaymentReceiveAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    
    
    
    
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 退現頁面 (bamo0d105c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */

    public ActionForward doOldAgeReceivablesData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 退現頁面 OldAgePaymentReceiveAction.doOldAgeReceivablesData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeAccountsReceivableDataCaseList(request);

            // 案件資料
            OldAgePaymentReceiveDataCase caseObj = CaseSessionHelper.getOldAgePaymentReceiveDataCase(request);

            // 查詢 應收資料
            List<AccountsReceivableDataCase> accountsReceivableDataCaseList = updateService.selectAccountsReceivableDataListBy(caseObj.getApNo(), caseObj.getSeqNo(), "", "");

            // 塞到 Session 中
            CaseSessionHelper.setAccountsReceivableDataCaseList(accountsReceivableDataCaseList, request);

            log.debug("執行 執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 退現頁面 OldAgePaymentReceiveAction.doOldAgeReceivablesData() 完成 ... ");

            return mapping.findForward(RECEIVABLES_PAGE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldAgePaymentReceiveAction.doOldAgeReceivablesData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    
    
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 (bamo0d100c.jsp) <br>
     * 按下「註銷」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 OldAgePaymentReceiveAction.doCancel() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgePaymentReceiveForm queryForm = (OldAgePaymentReceiveForm) form;

        try {
        	//受理編號
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            
            //現金收回註銷
            if(queryForm.getReceiveType().equals("1")){
            	//查詢 收回狀況
            	OldAgePaymentReceiveDataCase caseData = updateService.selectChkCancelCashReceiveDataBy(apno,queryForm.getApNo1());
            	
            	if(caseData == null ){
            		saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            	}
            	
            	//查詢 退現已收資料
            	List<CashReceiveDataCase> cashReceivedDataCaseList = updateService.selectCashReceivedDataListBy(apno,queryForm.getApNo1());
            	
            	if(cashReceivedDataCaseList.size() <= 0 ){
            		saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            	}

            	//測試資料--------------------------------------------------
//            	OldAgePaymentReceiveDataCase testCaseData = new OldAgePaymentReceiveDataCase();
//            	testCaseData.setApNo("L20000000001"); // 受理編號
//            	testCaseData.setSeqNo("0000"); // 受款人序
//            	testCaseData.setEvtName("測試人"); // 事故者姓名
//            	testCaseData.setEvtIdnNo("A123456789"); // 事故者身分證號
//            	testCaseData.setEvtBrDate("19800205"); // 事故者出生日期
//            	testCaseData.setEvtJobDate("19850205") ;// 事故日期
//
//            	List<CashReceiveDataCase> testCashReceiveDataCaseList = new ArrayList<CashReceiveDataCase>();
//            	for(int i = 0 ; i<5 ; i++){
//            		CashReceiveDataCase cashReceiveDataCaseData = new CashReceiveDataCase();
//            		cashReceiveDataCaseData.setInsKdCash("L");
//            		cashReceiveDataCaseData.setBliAccountCode("888");
//            		cashReceiveDataCaseData.setBookedBook("入帳方式");
//            		cashReceiveDataCaseData.setBkAccountDt("19900101");
//            		cashReceiveDataCaseData.setBatchNo("234567");
//            		cashReceiveDataCaseData.setSerialNo("0000");
//            		cashReceiveDataCaseData.setCashAmt(new BigDecimal(10000));
//            		cashReceiveDataCaseData.setDivSeq("19900101");
//            		cashReceiveDataCaseData.setIndexNo("888");
//            		testCashReceiveDataCaseList.add(cashReceiveDataCaseData);
//            	}
            	
            	//測試資料--------------------------------------------------
            	
            	FormSessionHelper.setOldAgePaymentReceiveForm(queryForm,request);
            	
            	//測試用
//            	CaseSessionHelper.setOldAgePaymentReceiveDataCase(testCaseData, request);
//            	CaseSessionHelper.setCashReceiveDataCaseList(testCashReceiveDataCaseList, request);
            	
            	CaseSessionHelper.setOldAgePaymentReceiveDataCase(caseData, request);
            	CaseSessionHelper.setCashReceiveDataCaseList(cashReceivedDataCaseList, request);
            	
            	log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 OldAgePaymentReceiveAction.doCancel() 完成 ... ");

                return mapping.findForward(CANCEL_CASH_PAGE);
                
            //退匯收回註銷   
            }else{
            	//查詢 收回狀況
            	OldAgePaymentReceiveDataCase caseData = updateService.selectChkCancelRemittanceReceiveDataBy(apno,queryForm.getSeqNo(),queryForm.getApNo1());
            	
            	if(caseData == null ){
            		saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            	}
            	
            	//查詢 退匯已收資料
            	List<RemittanceReceiveDataCase> remittanceReceivedDataCaseList = updateService.selectRemittanceReceivedDataListBy(apno,queryForm.getSeqNo());
            	
            	//測試資料--------------------------------------------------
//            	OldAgePaymentReceiveDataCase testCaseData = new OldAgePaymentReceiveDataCase();
//            	testCaseData.setApNo("L20000000001"); // 受理編號
//            	testCaseData.setSeqNo("0000"); // 受款人序
//            	testCaseData.setEvtName("測試人"); // 事故者姓名
//            	testCaseData.setEvtIdnNo("A123456789"); // 事故者身分證號
//            	testCaseData.setEvtBrDate("19800205"); // 事故者出生日期
//            	testCaseData.setEvtJobDate("19850205") ;// 事故日期
//         	
//            	List<RemittanceReceiveDataCase> testRemittanceReceiveDataCaseList = new ArrayList<RemittanceReceiveDataCase>();
//            	for(int i = 0 ; i<5 ; i++){
//            		RemittanceReceiveDataCase remittanceReceiveDataCaseData = new RemittanceReceiveDataCase();
//            		remittanceReceiveDataCaseData.setApNo("L");
//            		remittanceReceiveDataCaseData.setSeqNo("888");
//            		remittanceReceiveDataCaseData.setPayYm("19900101");
//            		remittanceReceiveDataCaseData.setOriIssuYm("19900101");
//            		remittanceReceiveDataCaseData.setBrChkDate("19800101");
//            		remittanceReceiveDataCaseData.setRemitAmt(new BigDecimal(13000));
//            		remittanceReceiveDataCaseData.setIssuKind("11111");
//            		remittanceReceiveDataCaseData.setBrNote("19900101");
//            		testRemittanceReceiveDataCaseList.add(remittanceReceiveDataCaseData);
//            	}
//            	
            	//測試資料--------------------------------------------------
            	
            	FormSessionHelper.setOldAgePaymentReceiveForm(queryForm,request);
            	
            	//測試用
//            	CaseSessionHelper.setOldAgePaymentReceiveDataCase(testCaseData, request);
//            	CaseSessionHelper.setRemittanceReceiveDataCaseList(testRemittanceReceiveDataCaseList, request);
            	
            	CaseSessionHelper.setOldAgePaymentReceiveDataCase(caseData, request);
            	CaseSessionHelper.setRemittanceReceiveDataCaseList(remittanceReceivedDataCaseList, request);
            	
            	log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 OldAgePaymentReceiveAction.doCancel() 完成 ... ");

                return mapping.findForward(CANCEL_REMITTANCE_PAGE);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldAgePaymentReceiveAction.doCancel() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 退匯頁面 (bamo0d101c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirmRemittanceReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 OldAgePaymentReceiveAction.doConfirmRemittanceReceive() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgePaymentReceiveForm queryForm = (OldAgePaymentReceiveForm) form;

        try {/**
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            
            OldAgePaymentReceiveDataCase caseData = CaseSessionHelper.getOldAgePaymentReceiveDataCase(request);
            //應收資料
            List<AccountsReceivableDataCase> accountsReceivableDataCaseList = CaseSessionHelper.getAccountsReceivableDataCaseList(request);
            //退匯資料
            List<RemittanceReceiveDataCase> remittanceReceiveDataCaseList = CaseSessionHelper.getRemittanceReceiveDataCaseList(request);
            
            //照順序扣除 應收未收餘額
            RemittanceReceiveDataCase remittanceReceiveData = remittanceReceiveDataCaseList.get(queryForm.getIndex());
            int remitAmt = remittanceReceiveData.getRemitAmt().intValue(); 
            
            for(AccountsReceivableDataCase accountsReceivableData : accountsReceivableDataCaseList){
            	int recRem = accountsReceivableData.getRecRem().intValue();
            	if(remitAmt - recRem >= 0){
            		remitAmt = remitAmt - recRem;
            		accountsReceivableData.setRecRem(new BigDecimal(0));
            		remittanceReceiveData.setRemitAmt(new BigDecimal(remitAmt));
            		//更新 應收帳務明細檔  //" "未收訖 1部分收回 2以收訖
            		updateService.updateBaunacpdtlForPaymentReceive(accountsReceivableData.getRecRem(),"2",accountsReceivableData.getBaunacpdtlId());
            		//新增 BAACPDTL
            		updateService.insertDataForRemittanceReceiveData( userData, caseData,accountsReceivableData,remittanceReceiveData);
            		
                //(remitAmt - recRem < 0)
            	}else{
            		int lastRecRem = recRem - remitAmt;
            		//現金以後除為0 停止繼續
            		if(lastRecRem == recRem){
            			
            			break;
            			
            		}else{
            			accountsReceivableData.setRecRem(new BigDecimal(lastRecRem));
                		remitAmt = 0;
                		remittanceReceiveData.setRemitAmt(new BigDecimal(remitAmt));
                		//更新 應收帳務明細檔
                		updateService.updateBaunacpdtlForPaymentReceive(accountsReceivableData.getRecRem(),"1",accountsReceivableData.getBaunacpdtlId());
                		//新增 BAACPDTL
                		updateService.insertDataForRemittanceReceiveData( userData, caseData,accountsReceivableData,remittanceReceiveData);
                		
                		//退匯金額扣除完畢 已剩金額為0 call sp傳送資料至出納
                        updateService.doExpSingleRec(remittanceReceiveData.getApNo(),remittanceReceiveData.getSeqNo(),DateUtility.getNowWestDate().substring(0, 6),remittanceReceiveData.getPayYm(),DateUtility.getNowWestDate());
                        
                        updateService.doRtnRefundmentRpt(remittanceReceiveData.getApNo(),remittanceReceiveData.getSeqNo(), DateUtility.getNowWestDate());
                        
                		break;
                		}
            	}
            }
            
            //更新改退匯檔
            updateService.updateBaregivedtlForPaymentReceive(userData ,apno, queryForm.getSeqNo(), remittanceReceiveData.getIssuKind(), remittanceReceiveData.getOriIssuYm(), remittanceReceiveData.getPayYm());
            updateService.updateBapflbacForPaymentReceive(userData,apno, queryForm.getSeqNo(), remittanceReceiveData.getIssuKind(), remittanceReceiveData.getOriIssuYm(), remittanceReceiveData.getPayYm(), remittanceReceiveData.getRemitAmt());

            //更新畫面資料
            //查詢 應收資料
        	List<AccountsReceivableDataCase> accountsReceivableDataCaseListNew = updateService.selectAccountsReceivableDataListBy(apno,queryForm.getSeqNo(),"","");
        	//查詢退匯資料
        	List<RemittanceReceiveDataCase> remittanceReceiveDataCaseListNew = updateService.selectRemittanceReceiveDataListBy(apno,queryForm.getSeqNo());
            
        	CaseSessionHelper.setAccountsReceivableDataCaseList(accountsReceivableDataCaseListNew, request);
        	CaseSessionHelper.setRemittanceReceiveDataCaseList(remittanceReceiveDataCaseListNew, request);

            log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 OldAgePaymentReceiveAction.doConfirmRemittanceReceive() 完成 ... ");
*/
            return mapping.findForward(REMITTANCE_PAGE);
            
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldAgePaymentReceiveAction.doConfirmRemittanceReceive() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 退現頁面 (bamo0d102c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirmCashReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 OldAgePaymentReceiveAction.doConfirmCashReceive() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgePaymentReceiveForm queryForm = (OldAgePaymentReceiveForm) form;
        List<OldAgePaymentReceiveDataCase> tempCashReceiveList = new ArrayList<OldAgePaymentReceiveDataCase>();

        // 案件資料
        OldAgePaymentReceiveDataCase caseObj = CaseSessionHelper.getOldAgePaymentReceiveDataCase(request);

        try {
            // 受理編號
            String sApNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            // 查詢退現資料
            List<CashReceiveDataCase> cashReceiveDataCaseList = updateService.selectCashReceiveDataListBy(sApNo, queryForm.getApNo1());
            // 勾選資料 List
            List<CashReceiveDataCase> cashReceiveDataList = new ArrayList<CashReceiveDataCase>();

            // 取得勾選資料 index
            int checkIndex[] = queryForm.getIndex();

            for (int i = 0; i < checkIndex.length; i++) {
                int Index = checkIndex[i];
                CashReceiveDataCase cashReceiveData = cashReceiveDataCaseList.get(Index);
                cashReceiveDataList.add(cashReceiveData);

            }

            // 建立資料Map
            // Map的Key 為 issuYm+payYm
            // Value則是同為該 INSKD, BLI_ACCOUNT_CODE, BOOKEDBOOK, BKACCOUNTDT, BATCHNO, SERIALNO, CASH_AMT, INDEX_NO 下的所有資料
            Map<String, List<CashReceiveDataCase>> dataMap = new TreeMap<String, List<CashReceiveDataCase>>();

            for (int i = 0; i < cashReceiveDataList.size(); i++) {
                CashReceiveDataCase obj = cashReceiveDataList.get(i);
                dataMap.put(obj.getInsKd() + obj.getBli_Account_Code() + obj.getBookedBook() + obj.getBkAccountDt() + obj.getBatchNo() + obj.getSerialNo() + obj.getCash_Amt() + obj.getIndex_No(), new ArrayList<CashReceiveDataCase>());
            }

            for (int i = 0; i < cashReceiveDataList.size(); i++) {
                CashReceiveDataCase obj = cashReceiveDataList.get(i);
                (dataMap.get(obj.getInsKd() + obj.getBli_Account_Code() + obj.getBookedBook() + obj.getBkAccountDt() + obj.getBatchNo() + obj.getSerialNo() + obj.getCash_Amt() + obj.getIndex_No())).add(obj);
            }

            for (Iterator it = dataMap.keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();
                tempCashReceiveList = updateService.doOldAgePaymentReceive(mapping, form, request, response, userData, queryForm, dataMap, key);

                // 產生報表檔資料 及 PFM & PFD 資料
                if ((tempCashReceiveList != null || tempCashReceiveList.size() > 0)) {
                    try {
                        updateService.createReportDataForPaymentReceive(mapping, form, request, response, userData, queryForm, tempCashReceiveList);
                    }
                    catch (Exception e) {

                        // 設定查詢失敗訊息
                        log.error("BatchPaymentReceiveAction.createReportData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
                        saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
                        return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                    }
                }
            }

            // 更新畫面資料
            // 查詢 應收資料
            List<AccountsReceivableDataCase> accountsReceivableDataCaseListNew = updateService.selectAccountsReceivableDataListBy(sApNo, queryForm.getSeqNo(), "", "");
            // 查詢退匯資料
            List<CashReceiveDataCase> cashReceiveDataListNew = updateService.selectCashReceiveDataListBy(sApNo, queryForm.getApNo1());

            CaseSessionHelper.setAccountsReceivableDataCaseList(accountsReceivableDataCaseListNew, request);
            CaseSessionHelper.setCashReceiveDataCaseList(cashReceiveDataListNew, request);

            log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 OldAgePaymentReceiveAction.doConfirmCashReceive() 完成 ... ");

            if (accountsReceivableDataCaseListNew.size() == 0) {
                saveMessages(session, CustomMessageHelper.getAccountsReceivableDataCaseListMessage());
            }

            saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            return mapping.findForward(CASH_PAGE);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldAgePaymentReceiveAction.doConfirmCashReceive() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回註銷處理 - 退匯註銷頁面 (bamo0d103c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCancelRemittanceReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回註銷處理 - 註銷頁面 OldAgePaymentReceiveAction.doCancelRemittanceReceive() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgePaymentReceiveForm queryForm = (OldAgePaymentReceiveForm) form;

        try {/**
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();

            OldAgePaymentReceiveDataCase caseData = CaseSessionHelper.getOldAgePaymentReceiveDataCase(request);
            // 退匯收回註銷
            List<RemittanceReceiveDataCase> remittanceReceiveDataCaseList = CaseSessionHelper.getRemittanceReceiveDataCaseList(request);
            // 取得頁面勾選資料
            RemittanceReceiveDataCase remittanceReceiveData = remittanceReceiveDataCaseList.get(queryForm.getIndex());

            // Qry01 從BAACPDTL取得BAUNACPDTLID & RECAMT 多筆
            List<RemittanceReceiveDataCase> baacpdtlDataList = updateService.selectBaunacpdtlDataForRemittanceReceivedBy(remittanceReceiveData.getTransActionId(), remittanceReceiveData.getTransActionSeq(), remittanceReceiveData.getInsKd());

            // 加回RECAMT 總額 更新改退匯檔 使用
            BigDecimal remitAmt = remittanceReceiveData.getRemitAmt();

            // 更新 退現資料檔 BAACPDTL
            for (RemittanceReceiveDataCase baacpdtlData : baacpdtlDataList) {

                remitAmt.add(baacpdtlData.getRecAmt());
                updateService.updateBaacpdtlForCancelCashReceive(baacpdtlData.getBaunacpdtlId(), baacpdtlData.getBaacpdtlId());
            }

            // Qry02 從BAACPDTL取得BAUNACPDTLID 多筆
            List<RemittanceReceiveDataCase> baacpdtlIdDataList = updateService.selectBaunacpdtlIdForRemittanceReceivedBy(remittanceReceiveData.getTransActionId(), remittanceReceiveData.getTransActionSeq(), remittanceReceiveData.getInsKd());

            // 取得 應收帳務明細檔 BAUNACPDTL 原 RECREM 資料
            for (RemittanceReceiveDataCase baacpdtlIdData : baacpdtlIdDataList) {

                RemittanceReceiveDataCase oldBaunacpdtlData = updateService.selectDataForRemittanceReceiveBy(baacpdtlIdData.getBaunacpdtlId());

                BigDecimal recRem = oldBaunacpdtlData.getRecRem().add(baacpdtlIdData.getRecAmt());
                // 更新 應收帳務明細檔 BAUNACPDTL 與退現使用同樣SQL
                updateService.updateBaunacpdtlForCancelCashReceive(baacpdtlIdData.getBaunacpdtlId(), recRem);

            }

            // 更新改退匯檔
            updateService.updateBaregivedtlForCancelReceive(userData, apno, queryForm.getSeqNo(), remittanceReceiveData.getOriIssuYm(), remittanceReceiveData.getPayYm());
            updateService.updateBapflbacForCancelReceive(userData, apno, queryForm.getSeqNo(), remittanceReceiveData.getOriIssuYm(), remittanceReceiveData.getPayYm(), remitAmt);

            // 傳送此筆註銷資料至出納做取消 call sp傳送資料至出納
            updateService.doExpCancelSingleRec(remittanceReceiveData.getApNo(), remittanceReceiveData.getSeqNo(), remittanceReceiveData.getOriIssuYm(), remittanceReceiveData.getPayYm(), remittanceReceiveData.getBrChkDate());

            updateService.doCancelRtnRefundmentRpt(remittanceReceiveData.getApNo(), remittanceReceiveData.getSeqNo(), DateUtility.getNowWestDate());

            // 查詢退匯資料 更新頁面資料
            List<RemittanceReceiveDataCase> remittanceReceivedDataCaseListNew = updateService.selectRemittanceReceivedDataListBy(apno, queryForm.getSeqNo());

            CaseSessionHelper.setRemittanceReceiveDataCaseList(remittanceReceivedDataCaseListNew, request);

            log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回註銷處理 - 註銷頁面 OldAgePaymentReceiveAction.doCancelRemittanceReceive() 完成 ... ");
*/
            return mapping.findForward(CANCEL_REMITTANCE_PAGE);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldAgePaymentReceiveAction.doCancelRemittanceReceive() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回註銷處理 - 退現註銷頁面 (bamo0d104c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCancelCashReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回註銷處理 - 註銷頁面 OldAgePaymentReceiveAction.doCancelCashReceive() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OldAgePaymentReceiveForm queryForm = (OldAgePaymentReceiveForm) form;
        PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();
        List caseData2 = new ArrayList();

        try {/**
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();

            OldAgePaymentReceiveDataCase caseData = CaseSessionHelper.getOldAgePaymentReceiveDataCase(request);

            // 現金收回註銷
            List<CashReceiveDataCase> cashReceiveDataCaseList = CaseSessionHelper.getCashReceiveDataCaseList(request);

            // 取得頁面勾選資料
            CashReceiveDataCase cashReceiveData = cashReceiveDataCaseList.get(queryForm.getIndex());

            // Qry01 從BAACPDTL取得BAUNACPDTLID & RECAMT 多筆
            List<CashReceiveDataCase> baacpdtlDataList = updateService.selectBaunacpdtlDataForCashReceivedBy(apno, cashReceiveData.getInsKdCash(), cashReceiveData.getBliAccountCode(), cashReceiveData.getBookedBook(), cashReceiveData.getBkAccountDt(), cashReceiveData.getBatchNo(), cashReceiveData.getSerialNo(), cashReceiveData.getCashAmt(), cashReceiveData.getDivSeq(), cashReceiveData.getIndexNo());

            // 更新 退現資料檔 BAACPDTL
            for (CashReceiveDataCase baacpdtlData : baacpdtlDataList) {

                updateService.updateBaacpdtlForCancelCashReceive(baacpdtlData.getBaunacpdtlId(), baacpdtlData.getBaacpdtlId());

                /**
                 * 處理要填入 pfxx0w040Service.writeUpdatePfxx0w040n 的資料
                 *//**
                pcckyuserrec.setInskd(cashReceiveData.getInsKd());
                log.info("pcckyuserrec.setInskd:" + pcckyuserrec.getInskd());

                pcckyuserrec.setBliAccountCode(cashReceiveData.getBli_Account_Code());
                log.info("pcckyuserrec.setBliAccountCode:" + pcckyuserrec.getBliAccountCode());

                pcckyuserrec.setBookedbook(cashReceiveData.getBookedBook());
                log.info("pcckyuserrec.setBookedbook:" + pcckyuserrec.getBookedbook());

                pcckyuserrec.setBkaccountdt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
                log.info("pcckyuserrec.setBkaccountdt:" + pcckyuserrec.getBkaccountdt());

                pcckyuserrec.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
                log.info("pcckyuserrec.setBatchno:" + pcckyuserrec.getBatchno());

                pcckyuserrec.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
                log.info("pcckyuserrec.setSerialno:" + pcckyuserrec.getSerialno());

                if (cashReceiveData.getCash_Amt() == null) {
                    pcckyuserrec.setCashAmt((long) 0);
                }
                else {
                    pcckyuserrec.setCashAmt(cashReceiveData.getCash_Amt().longValue());
                }
                log.info("pcckyuserrec.setCashAmt:" + pcckyuserrec.getCashAmt());

                pcckyuserrec.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
                log.info("pcckyuserrec.setDivseq:" + pcckyuserrec.getDivseq());

                pcckyuserrec.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
                log.info("pcckyuserrec.setIndexNo:" + pcckyuserrec.getIndexNo());

                // pcckyuserrec.setExpid(1l);

                pcckyuserrec.setRetrievemoney(cashReceiveData.getRetrieveMoney());// 收回金額
                log.info("pcckyuserrec.setRetrievemoney:" + pcckyuserrec.getRetrievemoney());

                pcckyuserrec.setApno(cashReceiveData.getTempHandleNo());
                log.info("pcckyuserrec.setApno:" + pcckyuserrec.getApno());

                pcckyuserrec.setRecm(cashReceiveData.getRecm());
                log.info("pcckyuserrec.setRecm:" + pcckyuserrec.getRecm());

                pcckyuserrec.setCancelpno(userData.getUserId());
                log.info("pcckyuserrec.setCancelpno:" + pcckyuserrec.getCancelpno());

                pcckyuserrec.setCancelpnodept(userData.getDeptId());
                log.info("pcckyuserrec.setCancelpnodept:" + pcckyuserrec.getCancelpnodept());

                pcckyuserrec.setCanceldate(DateUtility.changeDateType(userData.getLoginDate()));
                log.info("pcckyuserrec.setCanceldate:" + pcckyuserrec.getCanceldate());

                caseData2.add(pcckyuserrec);

            }

            // Qry02 從BAACPDTL取得BAUNACPDTLID 多筆
            List<CashReceiveDataCase> baacpdtlIdDataList = updateService.selectBaunacpdtlIdForCashReceivedBy(apno, cashReceiveData.getInsKdCash(), cashReceiveData.getBliAccountCode(), cashReceiveData.getBookedBook(), cashReceiveData.getBkAccountDt(), cashReceiveData.getBatchNo(), cashReceiveData.getSerialNo(), cashReceiveData.getCashAmt(), cashReceiveData.getDivSeq(), cashReceiveData.getIndexNo());

            // 取得 應收帳務明細檔 BAUNACPDTL 原 RECREM 資料
            for (CashReceiveDataCase baacpdtlIdData : baacpdtlIdDataList) {

                CashReceiveDataCase oldBaunacpdtlData = updateService.selectDataForCashReceiveBy(baacpdtlIdData.getBaunacpdtlId());

                BigDecimal recRem = oldBaunacpdtlData.getRecRem().add(baacpdtlIdData.getRecAmt());

                // 更新 應收帳務明細檔 BAUNACPDTL
                updateService.updateBaunacpdtlForCancelCashReceive(baacpdtlIdData.getBaunacpdtlId(), recRem);

            }

            // 利用writeUpdatePfxx0w040n回傳沖抵資料給出納
            doPfxx0w040UpdateService(userData, cashReceiveData, caseData2);

            // 傳送此筆註銷資料至出納做取消 call sp傳送資料至出納
            updateService.doCancelRtnCashRpt(cashReceiveData.getApNo(), cashReceiveData.getSeqNo(), DateUtility.getNowWestDate());

            // 查詢退匯資料 更新頁面資料
            List<CashReceiveDataCase> cashReceivedDataListNew = updateService.selectCashReceivedDataListBy(apno, queryForm.getApNo1());

            CaseSessionHelper.setCashReceiveDataCaseList(cashReceivedDataListNew, request);

            log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回註銷處理 - 註銷頁面 OldAgePaymentReceiveAction.doCancelCashReceive() 完成 ... ");
*/
            return mapping.findForward(CANCEL_CASH_PAGE);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OldAgePaymentReceiveAction.doCancelCashReceive() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 退現、退匯頁面 (bamo0d101c.jsp,bamo0d102c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        // 清除 Session
        CaseSessionHelper.removeOldAgePaymentReceiveDataCase(request);
        CaseSessionHelper.removeAccountsReceivableDataCaseList(request);
        CaseSessionHelper.removeRemittanceReceiveDataCaseList(request);
        CaseSessionHelper.removeCashReceiveDataCaseList(request);
        // 清除明細資料畫面
        FormSessionHelper.removeOldAgePaymentReceiveForm(request);

        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 執行現金收回作業時，存取 PFPCCKY 與 PFPCCKYUSERREC 資料表的介面服務
     * 
     * @param userData
     * @param cashReceiveData
     * @param caseData
     */
    // public void doPfxx0w040Service(UserBean userData, CashReceiveDataCase cashReceiveData, OldAgePaymentReceiveDataCase caseData) {
    public void doPfxx0w040Service(UserBean userData, CashReceiveDataCase cashReceiveData, List<PfpcckyuserrecVO> caseData) {

        // call 年金元件 service
        List list = new ArrayList();
        Map map = new HashMap();

        map.put("personid", userData.getUserId());
        map.put("deptid", userData.getDeptId());
        map.put("ip", userData.getLoginIP());

        log.info("(doPfxx0w040Service) pfpcckyvo vlue:");
        PfpcckyVO pfpcckyvo = new PfpcckyVO();

        pfpcckyvo.setInskd(cashReceiveData.getInsKd());
        log.info("pfpcckyvo.setInskd:" + pfpcckyvo.getInskd());

        pfpcckyvo.setBliAccountCode(cashReceiveData.getBli_Account_Code());
        log.info("pfpcckyvo.setBliAccountCode:" + pfpcckyvo.getBliAccountCode());

        pfpcckyvo.setBookedbook(cashReceiveData.getBookedBook());
        log.info("pfpcckyvo.setBookedbook:" + pfpcckyvo.getBookedbook());

        pfpcckyvo.setBkaccountdt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
        log.info("pfpcckyvo.setBkaccountdt:" + pfpcckyvo.getBkaccountdt());

        pfpcckyvo.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
        log.info("pfpcckyvo.setBatchno:" + pfpcckyvo.getBatchno());

        pfpcckyvo.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
        log.info("pfpcckyvo.setSerialno:" + pfpcckyvo.getSerialno());

        pfpcckyvo.setCashAmt(cashReceiveData.getCash_Amt().longValue());
        log.info("pfpcckyvo.setCashAmt:" + pfpcckyvo.getCashAmt());

        pfpcckyvo.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
        log.info("pfpcckyvo.setDivseq:" + pfpcckyvo.getDivseq());

        pfpcckyvo.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
        log.info("pfpcckyvo.setIndexNo:" + pfpcckyvo.getIndexNo());
        // ------------------------------------------------------------//
        pfpcckyvo.setMovetocode(cashReceiveData.getMoveToCode());
        log.info("pfpcckyvo.setMovetocode:" + pfpcckyvo.getMovetocode());

        pfpcckyvo.setTemphandleno(cashReceiveData.getTempHandleNo());
        log.info("pfpcckyvo.setTemphandleno:" + pfpcckyvo.getTemphandleno());

        pfpcckyvo.setAffairreprno(userData.getUserId());
        log.info("pfpcckyvo.setAffairreprno:" + pfpcckyvo.getAffairreprno());

        pfpcckyvo.setAffairredept(userData.getDeptId());
        log.info("pfpcckyvo.setAffairredept:" + pfpcckyvo.getAffairredept());

        pfpcckyvo.setAffairredate(DateUtility.getNowWestDate());
        log.info("pfpcckyvo.setAffairredate:" + DateUtility.getNowWestDate());

        if (cashReceiveData.getAvailable_Money() == null) {
            pfpcckyvo.setAvailableMoney((long) 0);// 可用餘額
        }
        else {
            pfpcckyvo.setAvailableMoney(cashReceiveData.getAvailable_Money().longValue());// 可用餘額
        }
        log.info("pfpcckyvo.setAvailableMoney:" + pfpcckyvo.getAvailableMoney());

        if (cashReceiveData.getAffairreCount() == null) {
            pfpcckyvo.setAffairrecount((long) 0);
        }
        else {
            pfpcckyvo.setAffairrecount(cashReceiveData.getAffairreCount().longValue());
        }
        log.info("pfpcckyvo.setAffairrecount:" + pfpcckyvo.getAffairrecount());

        map.put("pcckyvo", pfpcckyvo);

        // ------------------------------------------------------------//
        // ------------------------------------------------------------//

        Integer iExpid = selectExpid(pfpcckyvo.getInskd(), pfpcckyvo.getBliAccountCode(), pfpcckyvo.getBookedbook(), pfpcckyvo.getBkaccountdt(), pfpcckyvo.getBatchno(), pfpcckyvo.getSerialno(), pfpcckyvo.getCashAmt(), pfpcckyvo.getDivseq(), pfpcckyvo.getIndexNo());
        if (iExpid == null) {
            iExpid = 0;
        }
        log.info("(doPfxx0w040Service) [pfpcckyvo] iExpid vlue:" + iExpid);

        List detailList = new ArrayList();

        for (int j = 0; j < caseData.size(); j++) {
            PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();
            log.info("(doPfxx0w040Service) pcckyuserrec vlue:");

            pcckyuserrec.setInskd(cashReceiveData.getInsKd());
            log.info("pcckyuserrec.setInskd:" + pcckyuserrec.getInskd());

            pcckyuserrec.setBliAccountCode(cashReceiveData.getBli_Account_Code());
            log.info("pcckyuserrec.setBliAccountCode:" + pcckyuserrec.getBliAccountCode());

            pcckyuserrec.setBookedbook(cashReceiveData.getBookedBook());
            log.info("pcckyuserrec.setBookedbook:" + pcckyuserrec.getBookedbook());

            pcckyuserrec.setBkaccountdt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
            log.info("pcckyuserrec.setBkaccountdt:" + pcckyuserrec.getBkaccountdt());

            pcckyuserrec.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
            log.info("pcckyuserrec.setBatchno:" + pcckyuserrec.getBatchno());

            pcckyuserrec.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
            log.info("pcckyuserrec.setSerialno:" + pcckyuserrec.getSerialno());

            if (cashReceiveData.getCash_Amt() == null) {
                pcckyuserrec.setCashAmt((long) 0);
            }
            else {
                pcckyuserrec.setCashAmt(cashReceiveData.getCash_Amt().longValue());
            }
            log.info("pcckyuserrec.setCashAmt:" + pcckyuserrec.getCashAmt());

            pcckyuserrec.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
            log.info("pcckyuserrec.setDivseq:" + pcckyuserrec.getDivseq());

            pcckyuserrec.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
            log.info("pcckyuserrec.setIndexNo:" + pcckyuserrec.getIndexNo());
            // ------------------------------------------------------------//

            iExpid = iExpid.intValue() + 1;

            pcckyuserrec.setExpid(Long.valueOf(iExpid));
            log.info("pcckyuserrec.setExpid:" + pcckyuserrec.getExpid());

            pcckyuserrec.setRetrievemoney(cashReceiveData.getRetrieveMoney());// 收回金額
            log.info("pcckyuserrec.setRetrievemoney:" + pcckyuserrec.getRetrievemoney());

            pcckyuserrec.setApno(caseData.get(j).getApno());
            log.info("pcckyuserrec.setApno:" + pcckyuserrec.getApno());

            pcckyuserrec.setRecm(cashReceiveData.getRecm());
            log.info("pcckyuserrec.setRecm:" + cashReceiveData.getRecm());

            pcckyuserrec.setPersonno(userData.getUserId());
            log.info("pcckyuserrec.setPersonno:" + pcckyuserrec.getPersonno());

            pcckyuserrec.setPnodept(userData.getDeptId());
            log.info("pcckyuserrec.setPnodept:" + pcckyuserrec.getPnodept());

            pcckyuserrec.setPnodate(DateUtility.changeDateType(userData.getLoginDate()));
            log.info("pcckyuserrec.setPnodate:" + pcckyuserrec.getPnodate());

            pcckyuserrec.setCamfield(cashReceiveData.getTempHandleNo());
            log.info("pcckyuserrec.setCamfield:" + cashReceiveData.getTempHandleNo());

            detailList.add(pcckyuserrec);
        }
        map.put("pcckylist", detailList);

        // map.put("pcckylist", pcckyuserrec);

        list.add(map);

        if (list != null) {
            log.info("list is not null ");
            log.info(list.size());
        }

        if (list.isEmpty())
            log.info("list is empty");

        if (pfxx0w040Service == null)
            log.info("pfxx0w040Service is null");

        log.info(pfxx0w040Service.writeSavePfxx0w040n(list));
        String deBugStop = "";
    }

    /**
     * 依傳入條件取得 退回現金業務單位使用紀錄檔(<code>PFPCCKYUSERREC</code>) EXPID
     * 
     * @param idn 身分證號
     * @return
     */
    public Integer selectExpid(String sInskd, String sBliAccountCode, String sBookedbook, String sBkaccountdt, Long lBatchno, Long lSerialno, Long lCashAmt, Long lDivseq, Long lIndexNo) {
        Integer iExpid = localpfpcckyuserrecDao.selectExpid(sInskd, sBliAccountCode, sBookedbook, sBkaccountdt, lBatchno, lSerialno, lCashAmt, lDivseq, lIndexNo);
        return iExpid;
    }

    /**
     * 執行現金收回取消作業時，存取 PFPCCKY 與 PFPCCKYUSERREC 資料表的介面服務
     * 
     * @param userData
     * @param cashReceiveData
     * @param caseData
     */
    // public void doPfxx0w040UpdateService(UserBean userData, CashReceiveDataCase cashReceiveData, OldAgePaymentReceiveDataCase caseData) {
    public void doPfxx0w040UpdateService(UserBean userData, CashReceiveDataCase cashReceiveData, List<PfpcckyuserrecVO> caseData) {

        // call 年金元件 service
        List list = new ArrayList();
        Map map = new HashMap();

        map.put("personid", userData.getEmpNo());
        map.put("deptid", userData.getDeptId());
        map.put("ip", userData.getLoginIP());

        log.info("(doPfxx0w040UpdateService) pfpcckyvo vlue:");
        PfpcckyVO pfpcckyvo = new PfpcckyVO();

        pfpcckyvo.setInskd(cashReceiveData.getInsKd());
        log.info("pfpcckyvo.setInskd:" + pfpcckyvo.getInskd());

        pfpcckyvo.setBliAccountCode(cashReceiveData.getBli_Account_Code());
        log.info("pfpcckyvo.setBliAccountCode:" + pfpcckyvo.getBliAccountCode());

        pfpcckyvo.setBookedbook(cashReceiveData.getBookedBook());
        log.info("pfpcckyvo.setBookedbook:" + pfpcckyvo.getBookedbook());

        pfpcckyvo.setBkaccountdt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
        log.info("pfpcckyvo.setBkaccountdt:" + pfpcckyvo.getBkaccountdt());

        pfpcckyvo.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
        log.info("pfpcckyvo.setBatchno:" + pfpcckyvo.getBatchno());

        pfpcckyvo.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
        log.info("pfpcckyvo.setSerialno:" + pfpcckyvo.getSerialno());

        pfpcckyvo.setCashAmt(cashReceiveData.getCash_Amt().longValue());
        log.info("pfpcckyvo.setCashAmt:" + pfpcckyvo.getCashAmt());

        pfpcckyvo.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
        log.info("pfpcckyvo.setDivseq:" + pfpcckyvo.getDivseq());

        pfpcckyvo.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
        log.info("pfpcckyvo.setIndexNo:" + pfpcckyvo.getIndexNo());
        // ------------------------------------------------------------//

        pfpcckyvo.setTemphandleno(cashReceiveData.getTempHandleNo());
        log.info("pfpcckyvo.setTemphandleno:" + pfpcckyvo.getTemphandleno());

        if (cashReceiveData.getAvailable_Money() == null) {
            pfpcckyvo.setAvailableMoney((long) 0);// 可用餘額
        }
        else {
            pfpcckyvo.setAvailableMoney(cashReceiveData.getAvailable_Money().longValue());// 可用餘額
        }
        log.info("pfpcckyvo.setAvailableMoney:" + pfpcckyvo.getAvailableMoney());

        map.put("pcckyvo", pfpcckyvo);

        // ------------------------------------------------------------//
        // ------------------------------------------------------------//

        Integer iExpid = selectExpid(pfpcckyvo.getInskd(), pfpcckyvo.getBliAccountCode(), pfpcckyvo.getBookedbook(), pfpcckyvo.getBkaccountdt(), pfpcckyvo.getBatchno(), pfpcckyvo.getSerialno(), pfpcckyvo.getCashAmt(), pfpcckyvo.getDivseq(), pfpcckyvo.getIndexNo());
        if (iExpid == null) {
            iExpid = 0;
        }
        log.info("(writeUpdatePfxx0w040n) [pfpcckyvo] iExpid vlue:" + iExpid);

        List detailList = new ArrayList();

        for (int j = 0; j < caseData.size(); j++) {
            PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();
            log.info("(writeUpdatePfxx0w040n) pcckyuserrec vlue:");

            pcckyuserrec.setInskd(cashReceiveData.getInsKd());
            log.info("pcckyuserrec.setInskd:" + pcckyuserrec.getInskd());

            pcckyuserrec.setBliAccountCode(cashReceiveData.getBli_Account_Code());
            log.info("pcckyuserrec.setBliAccountCode:" + pcckyuserrec.getBliAccountCode());

            pcckyuserrec.setBookedbook(cashReceiveData.getBookedBook());
            log.info("pcckyuserrec.setBookedbook:" + pcckyuserrec.getBookedbook());

            pcckyuserrec.setBkaccountdt(DateUtility.changeDateType(cashReceiveData.getBkAccountDt()));
            log.info("pcckyuserrec.setBkaccountdt:" + pcckyuserrec.getBkaccountdt());

            pcckyuserrec.setBatchno(Long.parseLong(cashReceiveData.getBatchNo()));
            log.info("pcckyuserrec.setBatchno:" + pcckyuserrec.getBatchno());

            pcckyuserrec.setSerialno(Long.parseLong(cashReceiveData.getSerialNo()));
            log.info("pcckyuserrec.setSerialno:" + pcckyuserrec.getSerialno());

            if (cashReceiveData.getCash_Amt() == null) {
                pcckyuserrec.setCashAmt((long) 0);
            }
            else {
                pcckyuserrec.setCashAmt(cashReceiveData.getCash_Amt().longValue());
            }
            log.info("pcckyuserrec.setCashAmt:" + pcckyuserrec.getCashAmt());

            pcckyuserrec.setDivseq(Long.parseLong(cashReceiveData.getDivSeq()));
            log.info("pcckyuserrec.setDivseq:" + pcckyuserrec.getDivseq());

            pcckyuserrec.setIndexNo(Long.parseLong(cashReceiveData.getIndex_No()));
            log.info("pcckyuserrec.setIndexNo:" + pcckyuserrec.getIndexNo());
            // ------------------------------------------------------------//

            iExpid = iExpid.intValue() + 1;

            pcckyuserrec.setExpid(Long.valueOf(iExpid));
            log.info("pcckyuserrec.setExpid:" + pcckyuserrec.getExpid());

            pcckyuserrec.setRetrievemoney(cashReceiveData.getRetrieveMoney());// 收回金額
            log.info("pcckyuserrec.setRetrievemoney:" + pcckyuserrec.getRetrievemoney());

            pcckyuserrec.setApno(caseData.get(j).getApno());
            log.info("pcckyuserrec.setApno:" + pcckyuserrec.getApno());

            pcckyuserrec.setRecm(cashReceiveData.getRecm());
            log.info("pcckyuserrec.setRecm:" + cashReceiveData.getRecm());

            pcckyuserrec.setPersonno(userData.getUserId());
            log.info("pcckyuserrec.setPersonno:" + pcckyuserrec.getPersonno());

            pcckyuserrec.setPnodept(userData.getDeptId());
            log.info("pcckyuserrec.setPnodept:" + pcckyuserrec.getPnodept());

            pcckyuserrec.setPnodate(DateUtility.changeDateType(userData.getLoginDate()));
            log.info("pcckyuserrec.setPnodate:" + pcckyuserrec.getPnodate());

            detailList.add(pcckyuserrec);
        }
        map.put("pcckylist", detailList);

        // map.put("pcckylist", pcckyuserrec);

        list.add(map);

        if (list != null) {
            log.info("list is not null ");
            log.info(list.size());
        }

        if (list.isEmpty())
            log.info("list is empty");

        if (pfxx0w040Service == null)
            log.info("pfxx0w040Service is null");

        log.info(pfxx0w040Service.writeUpdatePfxx0w040n(list));
        String deBugStop = "";
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setPfxx0w040Service(Pfxx0w040Service pfxx0w040Service) {
        this.pfxx0w040Service = pfxx0w040Service;
    }

    public void setLocalPfpcckyuserrecDao(LocalPfpcckyuserrecDao localpfpcckyuserrecDao) {
        this.localpfpcckyuserrecDao = localpfpcckyuserrecDao;
    }

}
