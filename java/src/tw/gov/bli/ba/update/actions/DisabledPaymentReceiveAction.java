package tw.gov.bli.ba.update.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.LocalPfpcckyuserrecDao;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.DisabledAccountsReceivableDataCase;
import tw.gov.bli.ba.update.cases.DisabledCashReceiveDataCase;
import tw.gov.bli.ba.update.cases.DisabledPaymentReceiveDataCase;
import tw.gov.bli.ba.update.cases.DisabledRemittanceReceiveDataCase;
import tw.gov.bli.ba.update.forms.DisabledPaymentReceiveForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.pf.model.service.Pfxx0w040Service;
import tw.gov.bli.pf.model.vo.PfpcckyVO;
import tw.gov.bli.pf.model.vo.PfpcckyuserrecVO;

/**
 * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理
 * 
 * @author Noctis
 */
public class DisabledPaymentReceiveAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DisabledPaymentReceiveAction.class);
    private UpdateService updateService;
    private Pfxx0w040Service pfxx0w040Service;
    private LocalPfpcckyuserrecDao localpfpcckyuserrecDao;

    // 更正作業 - 應收收回處理作業 - 年金應收收回處理 - 現金收回
    private static final String CASH_PAGE = "cashPage";
    // 更正作業 - 應收收回處理作業 - 年金應收收回處理 - 退匯收回
    private static final String REMITTANCE_PAGE = "remittancePage";
    // 更正作業 - 應收收回處理作業 - 年金應收收回處理 - 註銷退匯收回
    private static final String CANCEL_CASH_PAGE = "cancelCashPage";
    // 更正作業 - 應收收回處理作業 - 年金應收收回處理 - 註銷退匯收回
    private static final String CANCEL_REMITTANCE_PAGE = "cancelRemittancePage";

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 查詢頁面 (bamo0d100c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 查詢頁面 DisabledPaymentReceiveAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledPaymentReceiveForm queryForm = (DisabledPaymentReceiveForm) form;

        try {
            // 受理編號
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();

            // 現金收回
            if (queryForm.getReceiveType().equals("1")) {
                // 查詢 收回狀況
                DisabledPaymentReceiveDataCase caseData = updateService.selectDisabledCashReceiveDataBy(apno, queryForm.getApNo1());

                if (caseData == null) {
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }

                // 查詢 應收資料
                List<DisabledAccountsReceivableDataCase> accountsReceivableDataCaseList = updateService.selectDisabledAccountsReceivableDataListBy(apno, queryForm.getSeqNo(), "", "");
                // 查詢退現資料
                List<DisabledCashReceiveDataCase> cashReceiveDataCaseList = updateService.selectDisabledCashReceiveDataListBy(apno, queryForm.getApNo1());

                // if(checkOldAgeDeathRepayDataCaseList.size() <= 0 ){
                // saveMessages(session, CustomMessageHelper.getCheckOldAgeDeathRepayDataFailMessage());
                // return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                // }

                // 測試資料--------------------------------------------------
                // DisabledPaymentReceiveDataCase testCaseData = new DisabledPaymentReceiveDataCase();
                // testCaseData.setApNo("K20000000001"); // 受理編號
                // testCaseData.setSeqNo("0000"); // 受款人序
                // testCaseData.setEvtName("測試人"); // 事故者姓名
                // testCaseData.setEvtIdnNo("A123456789"); // 事故者身分證號
                // testCaseData.setEvtBrDate("19800205"); // 事故者出生日期
                // testCaseData.setEvtJobDate("19850205") ;// 事故日期
                //
                // List<DisabledAccountsReceivableDataCase> testAccountsCaseDataList = new ArrayList<DisabledAccountsReceivableDataCase>();
                // for(int i = 0 ; i<5 ; i++){
                // DisabledAccountsReceivableDataCase accountsCaseData = new DisabledAccountsReceivableDataCase();
                // accountsCaseData.setApNo("K20000000001");
                // accountsCaseData.setIssuYm("0980708");
                // accountsCaseData.setPayYm("0980101");
                // accountsCaseData.setRecAmt(new BigDecimal(5000));
                // accountsCaseData.setRecRem(new BigDecimal(10000));
                // accountsCaseData.setSeqNo("0000");
                // accountsCaseData.setSts("2");
                // accountsCaseData.setUnacpDate("19900101");
                // testAccountsCaseDataList.add(accountsCaseData);
                // }
                //
                // List<DisabledCashReceiveDataCase> testCashReceiveDataCaseList = new ArrayList<DisabledCashReceiveDataCase>();
                // for(int i = 0 ; i<5 ; i++){
                // DisabledCashReceiveDataCase cashReceiveDataCaseData = new DisabledCashReceiveDataCase();
                // cashReceiveDataCaseData.setInsKd("K");
                // cashReceiveDataCaseData.setBli_Account_Code("888");
                // cashReceiveDataCaseData.setBookedBook("入帳方式");
                // cashReceiveDataCaseData.setBkAccountDt("19900101");
                // cashReceiveDataCaseData.setBatchNo("234567");
                // cashReceiveDataCaseData.setSerialNo("0000");
                // cashReceiveDataCaseData.setCash_Amt(new BigDecimal(10000));
                // cashReceiveDataCaseData.setDivSeq("19900101");
                // cashReceiveDataCaseData.setIndex_No("19900101");
                // cashReceiveDataCaseData.setMoveToCode("19900101");
                // cashReceiveDataCaseData.setTempHandleNo("19900101");
                // testCashReceiveDataCaseList.add(cashReceiveDataCaseData);
                // }

                // 測試資料--------------------------------------------------

                FormSessionHelper.setDisabledPaymentReceiveForm(queryForm, request);

                // 測試用
                // CaseSessionHelper.setDisabledPaymentReceiveDataCase(testCaseData, request);
                // CaseSessionHelper.setDisabledAccountsReceivableDataCaseList(testAccountsCaseDataList, request);
                // CaseSessionHelper.setDisabledCashReceiveDataCaseList(testCashReceiveDataCaseList, request);

                CaseSessionHelper.setDisabledPaymentReceiveDataCase(caseData, request);
                CaseSessionHelper.setDisabledAccountsReceivableDataCaseList(accountsReceivableDataCaseList, request);
                CaseSessionHelper.setDisabledCashReceiveDataCaseList(cashReceiveDataCaseList, request);

                log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 查詢頁面 OldAgePaymentReceiveAction.doQuery() 完成 ... ");

                return mapping.findForward(CASH_PAGE);

                // 退匯收回
            }
            else {
                // 查詢 收回狀況
                DisabledPaymentReceiveDataCase caseData = updateService.selectDisabledRemittanceReceiveDataBy(apno, queryForm.getSeqNo(), queryForm.getApNo1());

                if (caseData == null) {
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }

                // 查詢 應收資料
                List<DisabledAccountsReceivableDataCase> accountsReceivableDataCaseList = updateService.selectDisabledAccountsReceivableDataListBy(apno, queryForm.getSeqNo(), "", "");
                // 查詢退匯資料
                List<DisabledRemittanceReceiveDataCase> remittanceReceiveDataCaseList = updateService.selectDisabledRemittanceReceiveDataListBy(apno, queryForm.getSeqNo());

                // 測試資料--------------------------------------------------
                // DisabledPaymentReceiveDataCase testCaseData = new DisabledPaymentReceiveDataCase();
                // testCaseData.setApNo("K20000000001"); // 受理編號
                // testCaseData.setSeqNo("0000"); // 受款人序
                // testCaseData.setEvtName("測試人"); // 事故者姓名
                // testCaseData.setEvtIdnNo("A123456789"); // 事故者身分證號
                // testCaseData.setEvtBrDate("19800205"); // 事故者出生日期
                // testCaseData.setEvtJobDate("19850205") ;// 事故日期
                //
                // List<DisabledAccountsReceivableDataCase> testAccountsCaseDataList = new ArrayList<DisabledAccountsReceivableDataCase>();
                // for(int i = 0 ; i<5 ; i++){
                // DisabledAccountsReceivableDataCase accountsCaseData = new DisabledAccountsReceivableDataCase();
                // accountsCaseData.setApNo("K20000000001");
                // accountsCaseData.setIssuYm("0980708");
                // accountsCaseData.setPayYm("0980101");
                // accountsCaseData.setRecAmt(new BigDecimal(55555));
                // accountsCaseData.setRecRem(new BigDecimal(5000));
                // accountsCaseData.setSeqNo("0000");
                // accountsCaseData.setSts("2");
                // accountsCaseData.setUnacpDate("19900101");
                // testAccountsCaseDataList.add(accountsCaseData);
                // }
                //
                // List<DisabledRemittanceReceiveDataCase> testRemittanceReceiveDataCaseList = new ArrayList<DisabledRemittanceReceiveDataCase>();
                // for(int i = 0 ; i<5 ; i++){
                // DisabledRemittanceReceiveDataCase remittanceReceiveDataCaseData = new DisabledRemittanceReceiveDataCase();
                // remittanceReceiveDataCaseData.setApNo("K");
                // remittanceReceiveDataCaseData.setSeqNo("888");
                // remittanceReceiveDataCaseData.setPayYm("19900101");
                // remittanceReceiveDataCaseData.setOriIssuYm("19900101");
                // remittanceReceiveDataCaseData.setBrChkDate("19800101");
                // remittanceReceiveDataCaseData.setRemitAmt(new BigDecimal(13000));
                // remittanceReceiveDataCaseData.setIssuKind("11111");
                // remittanceReceiveDataCaseData.setBrNote("19900101");
                // testRemittanceReceiveDataCaseList.add(remittanceReceiveDataCaseData);
                // }

                // 測試資料--------------------------------------------------

                FormSessionHelper.setDisabledPaymentReceiveForm(queryForm, request);

                // 測試用
                // CaseSessionHelper.setDisabledPaymentReceiveDataCase(testCaseData, request);
                // CaseSessionHelper.setDisabledAccountsReceivableDataCaseList(testAccountsCaseDataList, request);
                // CaseSessionHelper.setDisabledRemittanceReceiveDataCaseList(testRemittanceReceiveDataCaseList, request);

                CaseSessionHelper.setDisabledPaymentReceiveDataCase(caseData, request);
                CaseSessionHelper.setDisabledAccountsReceivableDataCaseList(accountsReceivableDataCaseList, request);
                CaseSessionHelper.setDisabledRemittanceReceiveDataCaseList(remittanceReceiveDataCaseList, request);

                log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 查詢頁面 DisabledPaymentReceiveAction.doQuery() 完成 ... ");

                return mapping.findForward(REMITTANCE_PAGE);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentReceiveAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 查詢頁面 (bamo0d200c.jsp) <br>
     * 按下「註銷」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 查詢頁面 DisabledPaymentReceiveAction.doCancel() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledPaymentReceiveForm queryForm = (DisabledPaymentReceiveForm) form;

        try {
            // 受理編號
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();

            // 現金收回註銷
            if (queryForm.getReceiveType().equals("1")) {
                // 查詢 收回狀況
                DisabledPaymentReceiveDataCase caseData = updateService.selectChkCancelDisabledCashReceiveDataBy(apno, queryForm.getApNo1());

                if (caseData == null) {
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }

                // 查詢 退現已收資料
                List<DisabledCashReceiveDataCase> cashReceivedDataCaseList = updateService.selectDisabledCashReceivedDataListBy(apno, queryForm.getApNo1());

                if (cashReceivedDataCaseList.size() <= 0) {
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }

                // 測試資料--------------------------------------------------
                // DisabledPaymentReceiveDataCase testCaseData = new DisabledPaymentReceiveDataCase();
                // testCaseData.setApNo("L20000000001"); // 受理編號
                // testCaseData.setSeqNo("0000"); // 受款人序
                // testCaseData.setEvtName("測試人"); // 事故者姓名
                // testCaseData.setEvtIdnNo("A123456789"); // 事故者身分證號
                // testCaseData.setEvtBrDate("19800205"); // 事故者出生日期
                // testCaseData.setEvtJobDate("19850205") ;// 事故日期
                //
                // List<DisabledCashReceiveDataCase> testCashReceiveDataCaseList = new ArrayList<DisabledCashReceiveDataCase>();
                // for(int i = 0 ; i<5 ; i++){
                // DisabledCashReceiveDataCase cashReceiveDataCaseData = new DisabledCashReceiveDataCase();
                // cashReceiveDataCaseData.setInsKdCash("L");
                // cashReceiveDataCaseData.setBliAccountCode("888");
                // cashReceiveDataCaseData.setBookedBook("入帳方式");
                // cashReceiveDataCaseData.setBkAccountDt("19900101");
                // cashReceiveDataCaseData.setBatchNo("234567");
                // cashReceiveDataCaseData.setSerialNo("0000");
                // cashReceiveDataCaseData.setCashAmt(new BigDecimal(10000));
                // cashReceiveDataCaseData.setDivSeq("19900101");
                // cashReceiveDataCaseData.setIndexNo("888");
                // testCashReceiveDataCaseList.add(cashReceiveDataCaseData);
                // }

                // 測試資料--------------------------------------------------

                FormSessionHelper.setDisabledPaymentReceiveForm(queryForm, request);

                // 測試用
                // CaseSessionHelper.setDisabledPaymentReceiveDataCase(testCaseData, request);
                // CaseSessionHelper.setDisabledCashReceiveDataCaseList(testCashReceiveDataCaseList, request);

                CaseSessionHelper.setDisabledPaymentReceiveDataCase(caseData, request);
                CaseSessionHelper.setDisabledCashReceiveDataCaseList(cashReceivedDataCaseList, request);

                log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 查詢頁面 DisabledPaymentReceiveAction.doCancel() 完成 ... ");

                return mapping.findForward(CANCEL_CASH_PAGE);

                // 退匯收回註銷
            }
            else {
                // 查詢 收回狀況
                DisabledPaymentReceiveDataCase caseData = updateService.selectChkCancelDisabledRemittanceReceiveDataBy(apno, queryForm.getSeqNo(), queryForm.getApNo1());

                if (caseData == null) {
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }

                // 查詢退匯已收資料
                List<DisabledRemittanceReceiveDataCase> remittanceReceivedDataCaseList = updateService.selectDisabledRemittanceReceivedDataListBy(apno, queryForm.getSeqNo());

                // 測試資料--------------------------------------------------
                // DisabledPaymentReceiveDataCase testCaseData = new DisabledPaymentReceiveDataCase();
                // testCaseData.setApNo("L20000000001"); // 受理編號
                // testCaseData.setSeqNo("0000"); // 受款人序
                // testCaseData.setEvtName("測試人"); // 事故者姓名
                // testCaseData.setEvtIdnNo("A123456789"); // 事故者身分證號
                // testCaseData.setEvtBrDate("19800205"); // 事故者出生日期
                // testCaseData.setEvtJobDate("19850205") ;// 事故日期
                //
                // List<DisabledRemittanceReceiveDataCase> testRemittanceReceiveDataCaseList = new ArrayList<DisabledRemittanceReceiveDataCase>();
                // for(int i = 0 ; i<5 ; i++){
                // DisabledRemittanceReceiveDataCase remittanceReceiveDataCaseData = new DisabledRemittanceReceiveDataCase();
                // remittanceReceiveDataCaseData.setApNo("L");
                // remittanceReceiveDataCaseData.setSeqNo("888");
                // remittanceReceiveDataCaseData.setPayYm("19900101");
                // remittanceReceiveDataCaseData.setOriIssuYm("19900101");
                // remittanceReceiveDataCaseData.setBrChkDate("19800101");
                // remittanceReceiveDataCaseData.setRemitAmt(new BigDecimal(13000));
                // remittanceReceiveDataCaseData.setIssuKind("11111");
                // remittanceReceiveDataCaseData.setBrNote("19900101");
                // testRemittanceReceiveDataCaseList.add(remittanceReceiveDataCaseData);
                // }

                // 測試資料--------------------------------------------------

                FormSessionHelper.setDisabledPaymentReceiveForm(queryForm, request);

                // 測試用
                // CaseSessionHelper.setDisabledPaymentReceiveDataCase(testCaseData, request);
                // CaseSessionHelper.setDisabledRemittanceReceiveDataCaseList(testRemittanceReceiveDataCaseList, request);

                CaseSessionHelper.setDisabledPaymentReceiveDataCase(caseData, request);
                CaseSessionHelper.setDisabledRemittanceReceiveDataCaseList(remittanceReceivedDataCaseList, request);

                log.debug("執行 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 - 查詢頁面 DisabledPaymentReceiveAction.doCancel() 完成 ... ");

                return mapping.findForward(CANCEL_REMITTANCE_PAGE);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentReceiveAction.doCancel() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 退匯頁面 (bamo0d101c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirmRemittanceReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 退匯頁面 DisabledPaymentReceiveAction.doConfirmRemittanceReceive() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledPaymentReceiveForm queryForm = (DisabledPaymentReceiveForm) form;

        try {
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();

            DisabledPaymentReceiveDataCase caseData = CaseSessionHelper.getDisabledPaymentReceiveDataCase(request);
            // 應收資料
            List<DisabledAccountsReceivableDataCase> accountsReceivableDataCaseList = CaseSessionHelper.getDisabledAccountsReceivableDataCaseList(request);
            // 退匯資料
            List<DisabledRemittanceReceiveDataCase> remittanceReceiveDataCaseList = CaseSessionHelper.getDisabledRemittanceReceiveDataCaseList(request);

            // 照順序扣除 應收未收餘額
            DisabledRemittanceReceiveDataCase remittanceReceiveData = remittanceReceiveDataCaseList.get(queryForm.getIndex());
            int remitAmt = remittanceReceiveData.getRemitAmt().intValue();

            for (DisabledAccountsReceivableDataCase accountsReceivableData : accountsReceivableDataCaseList) {
                int recRem = accountsReceivableData.getRecRem().intValue();
                if (remitAmt - recRem >= 0) {
                    remitAmt = remitAmt - recRem;
                    accountsReceivableData.setRecRem(new BigDecimal(0));
                    remittanceReceiveData.setRemitAmt(new BigDecimal(remitAmt));
                    // 更新 應收帳務明細檔 //" "未收訖 1部分收回 2以收訖
                    updateService.updateBaunacpdtlForPaymentReceive(accountsReceivableData.getRecRem(), "2", accountsReceivableData.getBaunacpdtlId());
                    // 新增 BAACPDTL
                    updateService.insertDataForDisabledRemittanceReceiveData(userData, caseData, accountsReceivableData, remittanceReceiveData);

                    // (remitAmt - recRem < 0)
                }
                else {
                    int lastRecRem = recRem - remitAmt;
                    // 現金以後除為0 停止繼續
                    if (lastRecRem == recRem) {

                        break;

                    }
                    else {
                        accountsReceivableData.setRecRem(new BigDecimal(lastRecRem));
                        remitAmt = 0;
                        remittanceReceiveData.setRemitAmt(new BigDecimal(remitAmt));
                        // 更新 應收帳務明細檔
                        updateService.updateBaunacpdtlForPaymentReceive(accountsReceivableData.getRecRem(), "1", accountsReceivableData.getBaunacpdtlId());
                        // 新增 BAACPDTL
                        updateService.insertDataForDisabledRemittanceReceiveData(userData, caseData, accountsReceivableData, remittanceReceiveData);

                        // 退匯金額扣除完畢 已剩金額為0 call sp傳送資料至出納
                        updateService.doExpSingleRec(remittanceReceiveData.getApNo(), remittanceReceiveData.getSeqNo(), DateUtility.getNowWestDate().substring(0, 6), remittanceReceiveData.getPayYm(), DateUtility.getNowWestDate());

                        updateService.doRtnRefundmentRpt(remittanceReceiveData.getApNo(), remittanceReceiveData.getSeqNo(), DateUtility.getNowWestDate());

                        break;
                    }
                }
            }

            // 更新改退匯檔
            updateService.updateBaregivedtlForPaymentReceive(userData, apno, queryForm.getSeqNo(), remittanceReceiveData.getIssuKind(), remittanceReceiveData.getOriIssuYm(), remittanceReceiveData.getPayYm());
            updateService.updateBapflbacForPaymentReceive(userData, apno, queryForm.getSeqNo(), remittanceReceiveData.getIssuKind(), remittanceReceiveData.getOriIssuYm(), remittanceReceiveData.getPayYm(), remittanceReceiveData.getRemitAmt());

            // 更新畫面資料
            // 查詢 應收資料
            List<DisabledAccountsReceivableDataCase> accountsReceivableDataCaseListNew = updateService.selectDisabledAccountsReceivableDataListBy(apno, queryForm.getSeqNo(), "", "");
            // 查詢退匯資料
            List<DisabledRemittanceReceiveDataCase> remittanceReceiveDataCaseListNew = updateService.selectDisabledRemittanceReceiveDataListBy(apno, queryForm.getSeqNo());

            CaseSessionHelper.setDisabledAccountsReceivableDataCaseList(accountsReceivableDataCaseListNew, request);
            CaseSessionHelper.setDisabledRemittanceReceiveDataCaseList(remittanceReceiveDataCaseListNew, request);

            log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 退匯頁面 DisabledPaymentReceiveAction.doConfirmRemittanceReceive() 完成 ... ");

            return mapping.findForward(REMITTANCE_PAGE);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentReceiveAction.doConfirmRemittanceReceive() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 退現頁面 (bamo0d102c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirmCashReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 退現頁面 DisabledPaymentReceiveAction.doConfirmCashReceive() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledPaymentReceiveForm queryForm = (DisabledPaymentReceiveForm) form;

        try {
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();

            List caseData2 = new ArrayList();
            int iAffairreCount = 0;
            PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();

            DisabledPaymentReceiveDataCase caseData = CaseSessionHelper.getDisabledPaymentReceiveDataCase(request);

            // 應收資料
            List<DisabledAccountsReceivableDataCase> accountsReceivableDataCaseList = CaseSessionHelper.getDisabledAccountsReceivableDataCaseList(request);

            // 退現資料
            List<DisabledCashReceiveDataCase> cashReceiveDataCaseList = CaseSessionHelper.getDisabledCashReceiveDataCaseList(request);

            // 照順序扣除 應收未收餘額
            DisabledCashReceiveDataCase cashReceiveData = cashReceiveDataCaseList.get(queryForm.getIndex());
            // long cash_Amt = cashReceiveData.getCash_Amt().intValue();

            long cash_Amt = cashReceiveData.getAvailable_Money().intValue();
            long cash_AmtT = cashReceiveData.getCash_Amt().intValue();

            // String apno = cashReceiveData.getTempHandleNo().toString();
            // String issuym = cashReceiveData.getIssuYm().toString();
            // String payym = cashReceiveData.getPayYm().toString();
            // String seqno = cashReceiveData.getSeqNo().toString();

            for (DisabledAccountsReceivableDataCase accountsReceivableData : accountsReceivableDataCaseList) {
                long recRem = accountsReceivableData.getRecRem().intValue();
                cashReceiveData.setRecm(accountsReceivableData.getIssuYm());
                cashReceiveData.setApNo(accountsReceivableData.getApNo());
                cashReceiveData.setIssuYm(accountsReceivableData.getIssuYm());
                cashReceiveData.setPayKind(accountsReceivableData.getPayKind());

                if (cash_Amt - recRem >= 0) {
                    // 現金金額 此set可能不需要
                    cashReceiveData.setCash_Amt(new BigDecimal(cash_AmtT));

                    cash_Amt = cash_Amt - recRem;
                    accountsReceivableData.setRecRem(new BigDecimal(0));

                    // 可用餘額
                    cashReceiveData.setAvailable_Money(new BigDecimal(cash_Amt));

                    // 收回金額
                    cashReceiveData.setRetrieveMoney(recRem);
                    cashReceiveData.setRecAmt(new BigDecimal(recRem));

                    // 更新 應收帳務明細檔
                    // " "：未收訖；1：部分收回；9：已收訖
                    updateService.updateBaunacpdtlForPaymentReceive(accountsReceivableData.getRecRem(), "9", accountsReceivableData.getBaunacpdtlId());

                    // 新增 BAACPDTL
                    updateService.insertDataForDisabledCashReceiveData(userData, caseData, accountsReceivableData, cashReceiveData);

                    // call 年金元件service
                    iAffairreCount = iAffairreCount + 1;

                    log.info("(UpdateService.doBatchPaymentReceive: [cash_Amt - recRem >= 0]) pcckyuserrec vlue:");
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
                    pcckyuserrec.setPersonno(userData.getUserId());
                    log.info("pcckyuserrec.setPersonno:" + pcckyuserrec.getPersonno());
                    pcckyuserrec.setPnodept(userData.getDeptId());
                    log.info("pcckyuserrec.setPnodept:" + pcckyuserrec.getPnodept());

                    pcckyuserrec.setPnodate(DateUtility.changeDateType(userData.getLoginDate()));
                    log.info("pcckyuserrec.setPnodate:" + pcckyuserrec.getPnodate());
                    pcckyuserrec.setCamfield(cashReceiveData.getTempHandleNo());
                    log.info("pcckyuserrec.setCamfield:" + Encode.forJava(cashReceiveData.getTempHandleNo()));
                    caseData2.add(pcckyuserrec);

                    // doPfxx0w040Service(userData,cashReceiveData,caseData);

                    // (remitAmt - recRem < 0)
                }
                else {
                    long lastRecRem = recRem - cash_Amt;
                    // 現金以後除為0 停止繼續
                    if (lastRecRem == recRem) {

                        break;

                    }
                    else {
                        // accountsReceivableData.setRecRem(new BigDecimal(lastRecRem));

                        // 收回金額
                        cashReceiveData.setRetrieveMoney(cash_Amt);
                        // cash_Amt = 0;

                        // 現金金額 此set可能不需要
                        cashReceiveData.setCash_Amt(new BigDecimal(cash_AmtT));

                        cashReceiveData.setRecAmt(new BigDecimal(cash_Amt));
                        cash_Amt = cash_Amt - recRem;

                        accountsReceivableData.setRecRem(new BigDecimal(lastRecRem));

                        // 可用餘額
                        cashReceiveData.setAvailable_Money(new BigDecimal(lastRecRem));

                        // 更新 應收帳務明細檔
                        updateService.updateBaunacpdtlForPaymentReceive(accountsReceivableData.getRecRem(), "1", accountsReceivableData.getBaunacpdtlId());

                        // 新增 BAACPDTL
                        updateService.insertDataForDisabledCashReceiveData(userData, caseData, accountsReceivableData, cashReceiveData);

                        // call 年金元件service

                        iAffairreCount = iAffairreCount + 1;

                        // PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();
                        log.info("(UpdateService.doBatchPaymentReceive: [cash_Amt - recRem < 0]) pcckyuserrec vlue:");
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
                        pcckyuserrec.setPersonno(userData.getUserId());
                        log.info("pcckyuserrec.setPersonno:" + pcckyuserrec.getPersonno());
                        pcckyuserrec.setPnodept(userData.getDeptId());
                        log.info("pcckyuserrec.setPnodept:" + pcckyuserrec.getPnodept());

                        pcckyuserrec.setPnodate(DateUtility.changeDateType(userData.getLoginDate()));
                        log.info("pcckyuserrec.setPnodate:" + pcckyuserrec.getPnodate());
                        pcckyuserrec.setCamfield(cashReceiveData.getTempHandleNo());
                        log.info("pcckyuserrec.setCamfield:" + Encode.forJava(cashReceiveData.getTempHandleNo()));
                        caseData2.add(pcckyuserrec);

                        // doPfxx0w040Service(userData,cashReceiveData,caseData);

                        // 退現金額扣除完畢 已剩金額為0 call sp傳送資料至出納
                        // updateService.doRtnCashRpt(cashReceiveData.getApNo(),cashReceiveData.getSeqNo(),DateUtility.getNowWestDate());
                        break;
                    }
                } // if (cash_Amt - recRem >= 0) {

                // 退現金額扣除完畢 已剩金額為0 call sp傳送資料至出納
                updateService.doRtnCashRpt(cashReceiveData.getApNo(), queryForm.getSeqNo() /** cashReceiveData.getSeqNo() */
                                , DateUtility.getNowWestDate());

            } // for (AccountsReceivableDataCase accountsReceivableData : accountsReceivableDataCaseList) {

            cashReceiveData.setAffairreCount(new BigDecimal(iAffairreCount));
            doPfxx0w040Service(userData, cashReceiveData, caseData2);

            // 更新畫面資料
            // 查詢 應收資料
            List<DisabledAccountsReceivableDataCase> accountsReceivableDataCaseListNew = updateService.selectDisabledAccountsReceivableDataListBy(apno, queryForm.getSeqNo(), "", "");
            // 查詢退匯資料
            List<DisabledCashReceiveDataCase> cashReceiveDataListNew = updateService.selectDisabledCashReceiveDataListBy(apno, queryForm.getApNo1());

            CaseSessionHelper.setDisabledAccountsReceivableDataCaseList(accountsReceivableDataCaseListNew, request);
            CaseSessionHelper.setDisabledCashReceiveDataCaseList(cashReceiveDataListNew, request);

            log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 退現頁面 DisabledPaymentReceiveAction.doConfirmCashReceive() 完成 ... ");

            return mapping.findForward(CASH_PAGE);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentReceiveAction.doConfirmCashReceive() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回註銷處理 - 退匯註銷頁面 (bamo0d103c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCancelRemittanceReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回註銷處理 - 註銷頁面DisabledPaymentReceiveAction.doCancelRemittanceReceive() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledPaymentReceiveForm queryForm = (DisabledPaymentReceiveForm) form;

        try {
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();

            DisabledPaymentReceiveDataCase caseData = CaseSessionHelper.getDisabledPaymentReceiveDataCase(request);
            // 退匯收回註銷
            List<DisabledRemittanceReceiveDataCase> remittanceReceiveDataCaseList = CaseSessionHelper.getDisabledRemittanceReceiveDataCaseList(request);
            // 取得頁面勾選資料
            DisabledRemittanceReceiveDataCase remittanceReceiveData = remittanceReceiveDataCaseList.get(queryForm.getIndex());

            // Qry01 從BAACPDTL取得BAUNACPDTLID & RECAMT 多筆
            List<DisabledRemittanceReceiveDataCase> baacpdtlDataList = updateService.selectBaunacpdtlDataForDisabledRemittanceReceivedBy(remittanceReceiveData.getTransActionId(), remittanceReceiveData.getTransActionSeq(),
                            remittanceReceiveData.getInsKd());

            // 加回RECAMT 總額 更新改退匯檔 使用
            BigDecimal remitAmt = remittanceReceiveData.getRemitAmt();

            // 更新 退現資料檔 BAACPDTL
            for (DisabledRemittanceReceiveDataCase baacpdtlData : baacpdtlDataList) {

                remitAmt.add(baacpdtlData.getRecAmt());
                updateService.updateBaacpdtlForCancelCashReceive(baacpdtlData.getBaunacpdtlId(), baacpdtlData.getBaacpdtlId());
            }

            // Qry02 從BAACPDTL取得BAUNACPDTLID 多筆
            List<DisabledRemittanceReceiveDataCase> baacpdtlIdDataList = updateService.selectBaunacpdtlIdForDisabledRemittanceReceivedBy(remittanceReceiveData.getTransActionId(), remittanceReceiveData.getTransActionSeq(),
                            remittanceReceiveData.getInsKd());

            // 取得 應收帳務明細檔 BAUNACPDTL 原 RECREM 資料
            for (DisabledRemittanceReceiveDataCase baacpdtlIdData : baacpdtlIdDataList) {

                DisabledRemittanceReceiveDataCase oldBaunacpdtlData = updateService.selectDataForDisabledRemittanceReceiveBy(baacpdtlIdData.getBaunacpdtlId());

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
            List<DisabledRemittanceReceiveDataCase> remittanceReceivedDataCaseListNew = updateService.selectDisabledRemittanceReceivedDataListBy(apno, queryForm.getSeqNo());

            CaseSessionHelper.setDisabledRemittanceReceiveDataCaseList(remittanceReceivedDataCaseListNew, request);

            log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回註銷處理 - 註銷頁面 DisabledPaymentReceiveAction.doCancelRemittanceReceive() 完成 ... ");

            return mapping.findForward(CANCEL_REMITTANCE_PAGE);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentReceiveAction.doCancelRemittanceReceive() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回註銷處理 - 退現註銷頁面 (bamo0d104c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCancelCashReceive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回註銷處理 - 註銷頁面 DisabledPaymentReceiveAction.doCancelCashReceive() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledPaymentReceiveForm queryForm = (DisabledPaymentReceiveForm) form;
        PfpcckyuserrecVO pcckyuserrec = new PfpcckyuserrecVO();
        List caseData2 = new ArrayList();

        try {
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();

            DisabledPaymentReceiveDataCase caseData = CaseSessionHelper.getDisabledPaymentReceiveDataCase(request);

            // 現金收回註銷
            List<DisabledCashReceiveDataCase> cashReceiveDataCaseList = CaseSessionHelper.getDisabledCashReceiveDataCaseList(request);

            // 取得頁面勾選資料
            DisabledCashReceiveDataCase cashReceiveData = cashReceiveDataCaseList.get(queryForm.getIndex());

            // Qry01 從BAACPDTL取得BAUNACPDTLID & RECAMT 多筆
            List<DisabledCashReceiveDataCase> baacpdtlDataList = updateService.selectBaunacpdtlDataForDisabledCashReceivedBy(apno, cashReceiveData.getInsKdCash(), cashReceiveData.getBliAccountCode(), cashReceiveData.getBookedBook(),
                            cashReceiveData.getBkAccountDt(), cashReceiveData.getBatchNo(), cashReceiveData.getSerialNo(), cashReceiveData.getCashAmt(), cashReceiveData.getDivSeq(), cashReceiveData.getIndexNo());

            // 更新 退現資料檔 BAACPDTL
            for (DisabledCashReceiveDataCase baacpdtlData : baacpdtlDataList) {

                updateService.updateBaacpdtlForCancelCashReceive(baacpdtlData.getBaunacpdtlId(), baacpdtlData.getBaacpdtlId());

                /**
                 * 處理要填入 pfxx0w040Service.writeUpdatePfxx0w040n 的資料
                 */
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
            List<DisabledCashReceiveDataCase> baacpdtlIdDataList = updateService.selectBaunacpdtlIdForDisabledCashReceivedBy(apno, cashReceiveData.getInsKdCash(), cashReceiveData.getBliAccountCode(), cashReceiveData.getBookedBook(),
                            cashReceiveData.getBkAccountDt(), cashReceiveData.getBatchNo(), cashReceiveData.getSerialNo(), cashReceiveData.getCashAmt(), cashReceiveData.getDivSeq(), cashReceiveData.getIndexNo());

            // 取得 應收帳務明細檔 BAUNACPDTL 原 RECREM 資料
            for (DisabledCashReceiveDataCase baacpdtlIdData : baacpdtlIdDataList) {

                DisabledCashReceiveDataCase oldBaunacpdtlData = updateService.selectDataForDisabledCashReceiveBy(baacpdtlIdData.getBaunacpdtlId());

                BigDecimal recRem = oldBaunacpdtlData.getRecRem().add(baacpdtlIdData.getRecAmt());
                // 更新 應收帳務明細檔 BAUNACPDTL
                updateService.updateBaunacpdtlForCancelCashReceive(baacpdtlIdData.getBaunacpdtlId(), recRem);

            }

            // 利用writeUpdatePfxx0w040n回傳沖抵資料給出納
            doPfxx0w040UpdateService(userData, cashReceiveData, caseData2);

            // 傳送此筆註銷資料至出納做取消 call sp傳送資料至出納
            updateService.doCancelRtnCashRpt(cashReceiveData.getApNo(), cashReceiveData.getSeqNo(), DateUtility.getNowWestDate());

            // 查詢退匯資料 更新頁面資料
            List<DisabledCashReceiveDataCase> cashReceivedDataListNew = updateService.selectDisabledCashReceivedDataListBy(apno, queryForm.getApNo1());

            CaseSessionHelper.setDisabledCashReceiveDataCaseList(cashReceivedDataListNew, request);

            log.debug("執行 更正作業 - 應收收回處理作業 - 失能年金應收收回註銷處理 - 註銷頁面 DisabledPaymentReceiveAction.doCancelCashReceive() 完成 ... ");

            return mapping.findForward(CANCEL_CASH_PAGE);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentReceiveAction.doCancelCashReceive() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 - 退現、退匯頁面 (bamo0d101c.jsp,bamo0d102c.jsp) <br>
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
        CaseSessionHelper.removeDisabledPaymentReceiveDataCase(request);
        CaseSessionHelper.removeDisabledAccountsReceivableDataCaseList(request);
        CaseSessionHelper.removeDisabledRemittanceReceiveDataCaseList(request);
        CaseSessionHelper.removeDisabledCashReceiveDataCaseList(request);
        // 清除明細資料畫面
        FormSessionHelper.removeDisabledPaymentReceiveForm(request);

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
    public void doPfxx0w040Service(UserBean userData, DisabledCashReceiveDataCase cashReceiveData, List<PfpcckyuserrecVO> caseData) {

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

        Integer iExpid = selectExpid(pfpcckyvo.getInskd(), pfpcckyvo.getBliAccountCode(), pfpcckyvo.getBookedbook(), pfpcckyvo.getBkaccountdt(), pfpcckyvo.getBatchno(), pfpcckyvo.getSerialno(), pfpcckyvo.getCashAmt(), pfpcckyvo.getDivseq(),
                        pfpcckyvo.getIndexNo());
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
            log.info("pcckyuserrec.setRecm:" + Encode.forJava(cashReceiveData.getRecm()));

            pcckyuserrec.setPersonno(userData.getUserId());
            log.info("pcckyuserrec.setPersonno:" + pcckyuserrec.getPersonno());

            pcckyuserrec.setPnodept(userData.getDeptId());
            log.info("pcckyuserrec.setPnodept:" + pcckyuserrec.getPnodept());

            pcckyuserrec.setPnodate(DateUtility.changeDateType(userData.getLoginDate()));
            log.info("pcckyuserrec.setPnodate:" + pcckyuserrec.getPnodate());

            pcckyuserrec.setCamfield(cashReceiveData.getTempHandleNo());
            log.info("pcckyuserrec.setCamfield:" + Encode.forJava(cashReceiveData.getTempHandleNo()));

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
     * 執行現金收回取消作業時，存取 PFPCCKY 與 PFPCCKYUSERREC 資料表的介面服務
     * 
     * @param userData
     * @param cashReceiveData
     * @param caseData
     */
    // public void doPfxx0w040UpdateService(UserBean userData, CashReceiveDataCase cashReceiveData, OldAgePaymentReceiveDataCase caseData) {
    public void doPfxx0w040UpdateService(UserBean userData, DisabledCashReceiveDataCase cashReceiveData, List<PfpcckyuserrecVO> caseData) {

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

        Integer iExpid = selectExpid(pfpcckyvo.getInskd(), pfpcckyvo.getBliAccountCode(), pfpcckyvo.getBookedbook(), pfpcckyvo.getBkaccountdt(), pfpcckyvo.getBatchno(), pfpcckyvo.getSerialno(), pfpcckyvo.getCashAmt(), pfpcckyvo.getDivseq(),
                        pfpcckyvo.getIndexNo());
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
            log.info("pcckyuserrec.setRecm:" + Encode.forJava(cashReceiveData.getRecm()));

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
