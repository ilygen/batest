package tw.gov.bli.ba.decision.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.decision.cases.PaymentDecisionCase;
import tw.gov.bli.ba.decision.cases.PaymentDecisionExtCase;
import tw.gov.bli.ba.decision.forms.PaymentDecisionForm;
import tw.gov.bli.ba.decision.helper.CaseSessionHelper;
import tw.gov.bli.ba.decision.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.review.cases.PaymentReviewCase;
import tw.gov.bli.ba.review.forms.PaymentReviewForm;
import tw.gov.bli.ba.rpt.actions.OldAgeReviewRpt01Action;
import tw.gov.bli.ba.rpt.forms.OldAgeReviewRpt01Form;
import tw.gov.bli.ba.services.DecisionService;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 決行作業 - 給付決行作業 (BARC0D010A)
 * 
 * @author Rickychi
 */
public class PaymentDecisionAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(PaymentDecisionAction.class);

    private DecisionService decisionService;

    // 決行作業 - 給付決行作業 - 個案審核詳細頁面
    private static final String FORWARD_SINGLE_REVIEW = "singleReview";
    private static final String FORWARD_SINGLE_REVIEW_FAIL = "singleReviewFail";
    // 決行作業 - 給付決行作業 - 審核清單明細資料 頁面
    private static final String FORWARD_SINGLE_PAYMENT_DETAIL = "singleReviewDetail";
    private static final String FORWARD_SINGLE_PAYMENT_DETAIL_FAIL = "singleReviewDetailFail";
    // 決行作業 - 給付決行作業 - 審核清單明細資料 - 總表資料 頁面
    private static final String FORWARD_MASTER_DATA = "masterData";
    // 決行作業 - 給付決行作業 - 審核清單明細資料 - 請領同類/他類資料 頁面
    private static final String FORWARD_APPLY_DATA = "applyData";
    // 決行作業 - 給付決行作業 - 審核清單明細資料 - 事故者/受款人資料 頁面
    private static final String FORWARD_BENEFICIARY_DATA = "beneficiaryData";
    // 決行作業 - 給付決行作業 - 審核清單明細資料 - 核定資料 頁面
    private static final String FORWARD_ISSU_DATA = "issuData";
    // 決行作業 - 給付決行作業 - 審核清單明細資料 - 年資資料 頁面
    private static final String FORWARD_SENIORITY_DATA = "seniorityData";

    /**
     * 決行作業 - 給付決行作業 - 查詢頁面 (baco0d010a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 給付決行作業 - 查詢頁面 PaymentDecisionAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentDecisionForm iform = (PaymentDecisionForm) form;

        try {
            List<PaymentDecisionCase> dataList = new ArrayList<PaymentDecisionCase>();
            String apNo = iform.getApNoStr();// 畫面輸入之受理編號

            if (("1").equals(iform.getQryCond()) || ("2").equals(iform.getQryCond())) {
                // 用 受理編號 查詢
                if (("1").equals(iform.getQryCond())) {
                    dataList = decisionService.getDecisionDataListByApNo(apNo, userData.getEmpNo());
                }
                // 用 列印日期 + 頁次 查詢
                else if (("2").equals(iform.getQryCond())) {
                    dataList = decisionService.selectDecisionDataByRptDate(DateUtility.changeDateType(iform.getRptDate()), iform.getPageNo(), iform.getChkMan(), userData.getEmpNo());
                }

                if (dataList.size() <= 0) {
                    // 設定查無資料訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                    log.debug("執行 決行作業 - 給付決行作業 - 查詢頁面 PaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else {
                    PaymentDecisionCase caseForTitle = new PaymentDecisionCase();
                    BeanUtility.copyProperties(caseForTitle, dataList.get(0));

                    // 計算 核定金額合計 & 實付金額合計
                    BigDecimal tissueAmt = new BigDecimal(0);
                    BigDecimal taplPayAmt = new BigDecimal(0);
                    for (int i = 0; i < dataList.size(); i++) {
                        PaymentDecisionCase obj = dataList.get(i);
                        tissueAmt = tissueAmt.add(obj.getTissueAmt());
                        taplPayAmt = taplPayAmt.add(obj.getTaplPayAmt());
                    }
                    caseForTitle.setTissueAmt(tissueAmt);
                    caseForTitle.setTaplPayAmt(taplPayAmt);

                    // 將 查詢條件/查詢結果清單 存到 Request Scope
                    PaymentDecisionForm qryCondForm = new PaymentDecisionForm();
                    BeanUtility.copyProperties(qryCondForm, iform);
                    qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                    FormSessionHelper.setDecisionQueryForm(qryCondForm, request);
                    CaseSessionHelper.setPaymentDecisionCaseForTitle(caseForTitle, request);// title預設抓第一筆資料
                    CaseSessionHelper.setPaymentDecisionList(dataList, request);

                    log.debug("執行 決行作業 - 給付決行作業 - 查詢頁面 PaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
                }
            }
            else {
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentDecisionAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 決行作業 - 給付決行作業 - 個案審核 (baco0d011a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSingleReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 給付決行作業 - 個案審核 PaymentDecisionAction.doSingleReview() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentDecisionForm iform = (PaymentDecisionForm) form;

        try {
            String apNo = iform.getApNo();
            List<PaymentDecisionCase> dataList = decisionService.selectDetailDecisionData(apNo);

            if (dataList.size() == 1) {
                PaymentDecisionCase caseObj = dataList.get(0);
                apNo = caseObj.getApNo();

                // 取得 XX函註記
                List<PaymentDecisionCase> detail1 = decisionService.getLetterTypeMk1List(apNo);
                List<PaymentDecisionCase> detail2 = decisionService.getLetterTypeMk2List(apNo);
                List<PaymentDecisionCase> detail3 = decisionService.getLetterTypeMk3List(apNo);
                List<PaymentDecisionCase> detail4 = decisionService.getLetterTypeMk4List(apNo);
                List<PaymentDecisionCase> detail5 = decisionService.getLetterTypeMk5List(apNo);
                PaymentDecisionCase detail6 = decisionService.getLetterTypeMk6List(apNo);

                // 取得 核定資料
                List<PaymentDecisionExtCase> payList = decisionService.getPayData(caseObj.getApNo(), caseObj.getIssuYm());
                // 取得事故者編審註記資料
                List<PaymentDecisionExtCase> chkList = decisionService.getOldAgePaymentDecisionEvtChkList(caseObj.getApNo());
                // 取得 受款人編審註記資料
                List<PaymentDecisionCase> benChkList = decisionService.getOldAgePaymentDecisionBenChkList(caseObj.getApNo());
                // 取得 受款人資料
                List<PaymentDecisionCase> beneficiaryDataList = decisionService.getAllIssuData(caseObj.getApNo());
                // 取得受款人姓名清單
                List<PaymentDecisionExtCase> benNameList = decisionService.getBenNameList(apNo, "1");

                PaymentDecisionForm modifyForm = new PaymentDecisionForm();
                BeanUtility.copyProperties(modifyForm, caseObj);

                PaymentDecisionForm qryCondForm = new PaymentDecisionForm();
                BeanUtility.copyProperties(qryCondForm, iform);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果 case 存到 Request Scope
                CaseSessionHelper.setPaymentDecisionCase(caseObj, request);
                CaseSessionHelper.setPayDataList(payList, request);
                CaseSessionHelper.setPaymentDecisionChkList(chkList, request);
                CaseSessionHelper.setPaymentDecisionBenChkList(benChkList, request);
                CaseSessionHelper.setBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setPaymentDecisionBenNameList(benNameList, request);
                FormSessionHelper.setPaymentDecisionForm(modifyForm, request);
                FormSessionHelper.setDecisionQueryForm(qryCondForm, request);
                CaseSessionHelper.setPaymentDecisionLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setPaymentDecisionLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setPaymentDecisionLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setPaymentDecisionLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setPaymentDecisionLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setPaymentDecisionLetterTypeMk6List(detail6, request);

                log.debug("執行 決行作業 - 給付決行作業 - 個案審核 PaymentDecisionAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
            }
            else {
                // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                log.debug("執行 決行作業 - 給付決行作業 - 個案審核 PaymentDecisionAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentDecisionAction.doSingleReview() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SINGLE_REVIEW_FAIL);
        }
    }

    // 20090311 目前的版本用不到, 全部註解掉
    /*
     * ================================================Start================================================
     */
    // /**
    // * 決行作業 - 給付決行作業 - 審核清單明細資料 (baco0d012a.jsp)
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward doSingleReviewDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 決行作業 - 給付決行作業 - 審核清單明細資料 PaymentDecisionAction.doSingleReviewDetail() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // PaymentDecisionForm iform = (PaymentDecisionForm) form;
    //
    // try {
    // PaymentDecisionCase caseObj = CaseSessionHelper.getPaymentDecisionCase(request);
    //
    // // 取得 編審給付資料
    // List<PaymentDecisionExtCase> payList = decisionService.getPayData(caseObj.getApNo(), caseObj.getIssuYm());
    // // 取得 一次給付資料
    // PaymentDecisionExtCase oncePayCase = decisionService.getOncePayData(caseObj.getApNo(), caseObj.getIssuYm());
    // // 取得 一次給付更正資料
    // List<PaymentDecisionExtCase> oncePayModifyList = decisionService.getOncePayModifyData(caseObj.getApNo());
    // // 取得 核定通知書資料
    // PaymentDecisionExtCase notifyData = decisionService.getNotifyData(caseObj);
    // // 取得編審註記資料
    // List<PaymentDecisionExtCase> chkList = decisionService.getPaymentReviewChkList(caseObj.getApNo(), "0000");
    //
    // // 將 相關查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setPayDataList(payList, request);
    // CaseSessionHelper.setOncePayDataCase(oncePayCase, request);
    // CaseSessionHelper.setOncePayModifyDataList(oncePayModifyList, request);
    // CaseSessionHelper.setNotifyData(notifyData, request);
    // CaseSessionHelper.setPaymentDecisionChkList(chkList, request);
    //
    // log.debug("執行 決行作業 - 給付決行作業 - 審核清單明細資料 PaymentDecisionAction.doSingleReviewDetail() 完成 ... ");
    // return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentDecisionAction.doSingleReviewDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL_FAIL);
    // }
    // }
    //
    // /**
    // * 決行作業 - 給付決行作業 - 總表資料 (baco0d013q.jsp)
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward reviewMasterData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 決行作業 - 給付決行作業 - 總表資料 PaymentDecisionAction.reviewMasterData() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // PaymentDecisionForm iform = (PaymentDecisionForm) form;
    //
    // try {
    // PaymentDecisionCase caseObj = CaseSessionHelper.getPaymentDecisionCase(request);
    //
    // // 取得 編審給付資料
    // List<PaymentDecisionExtCase> payList = decisionService.getPayData(caseObj.getApNo(), caseObj.getIssuYm());
    // // 取得 一次給付資料
    // PaymentDecisionExtCase oncePayCase = decisionService.getOncePayData(caseObj.getApNo(), caseObj.getIssuYm());
    // // 取得 一次給付更正資料
    // List<PaymentDecisionExtCase> oncePayModifyList = decisionService.getOncePayModifyData(caseObj.getApNo());
    // // 取得 核定通知書資料
    // PaymentDecisionExtCase notifyData = decisionService.getNotifyData(caseObj);
    // // 取得編審註記資料
    // List<PaymentDecisionExtCase> chkList = decisionService.getPaymentReviewChkList(caseObj.getApNo(), "0000");
    //
    // // 將 相關查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setPayDataList(payList, request);
    // CaseSessionHelper.setOncePayDataCase(oncePayCase, request);
    // CaseSessionHelper.setOncePayModifyDataList(oncePayModifyList, request);
    // CaseSessionHelper.setNotifyData(notifyData, request);
    // CaseSessionHelper.setPaymentDecisionChkList(chkList, request);
    //
    // log.debug("執行 決行作業 - 給付決行作業 - 總表資料 PaymentDecisionAction.reviewMasterData() 完成 ... ");
    // return mapping.findForward(FORWARD_MASTER_DATA);
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentDecisionAction.reviewMasterData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
    // }
    // }
    //
    // /**
    // * 決行作業 - 給付決行作業 - 請領同類/他類資料 (baco0d013q01.jsp)
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward reviewApplyData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 決行作業 - 給付決行作業 - 請領同類/他類資料 PaymentDecisionAction.reviewApplyData() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // PaymentDecisionForm iform = (PaymentDecisionForm) form;
    //
    // try {
    // PaymentDecisionCase caseObj = CaseSessionHelper.getPaymentDecisionCase(request);
    // String apNo = caseObj.getApNo();
    // String evtIdnNo = caseObj.getEvtIdnNo();
    // String evtBrDate = caseObj.getEvtBrDate();
    // String evtBrDateStr = caseObj.getEvtBrDateStr();// 就保給付檔(BIREF)裡的日期存民國
    // String evtIds = caseObj.getEvtIds();
    //
    // // 一次給付資料
    // PaymentDecisionExtCase oncePayData = decisionService.getOncePayDetailData(evtIdnNo, evtBrDate);
    // // 年金給付資料
    // List<PaymentDecisionExtCase> annuityPayDataList = decisionService.getAnnuityPayData(apNo, evtIdnNo, evtBrDate);
    // // 申請失能給付紀錄資料
    // List<PaymentDecisionExtCase> criPayData = decisionService.getCriPayApplyData(evtIdnNo, evtBrDate);
    // // 申請死亡給付紀錄資料
    // PaymentDecisionExtCase diePayData = decisionService.getDiePayApplyData(evtIdnNo, evtBrDate);
    // // 申請傷病給付紀錄資料
    // List<PaymentDecisionExtCase> injPayData = decisionService.getInjPayApplyData(evtIdnNo, evtBrDate);
    // // 申請失業給付紀錄資料
    // List<PaymentDecisionExtCase> unEmpPayDataList = decisionService.getUnEmpPayApplyData(evtIdnNo, evtBrDateStr);
    // // 申請國保給付紀錄資料
    // PaymentDecisionExtCase npPayData = decisionService.getNpPayApplyData(evtIds);
    //
    // // 將 查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setOncePayDetailDataCase(oncePayData, request);
    // CaseSessionHelper.setAnnuityPayDataList(annuityPayDataList, request);
    // CaseSessionHelper.setCriPayApplyDataList(criPayData, request);
    // CaseSessionHelper.setDiePayApplyDataCase(diePayData, request);
    // CaseSessionHelper.setInjPayApplyDataList(injPayData, request);
    // CaseSessionHelper.setUnEmpPayApplyDataList(unEmpPayDataList, request);
    // CaseSessionHelper.setNpPayApplyDataCase(npPayData, request);
    //
    // log.debug("執行 決行作業 - 給付決行作業 - 請領同類/他類資料 PaymentDecisionAction.reviewApplyData() 完成 ... ");
    // return mapping.findForward(FORWARD_APPLY_DATA);
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentDecisionAction.reviewApplyData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
    // }
    // }
    //
    // /**
    // * 決行作業 - 給付決行作業 - 事故者/受款人資料 (baco0d013q02.jsp)
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward reviewBeneficiaryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 決行作業 - 給付決行作業 - 受款人資料 PaymentDecisionAction.reviewBeneficiaryData() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // PaymentDecisionForm iform = (PaymentDecisionForm) form;
    //
    // try {
    // PaymentDecisionCase caseObj = CaseSessionHelper.getPaymentDecisionCase(request);
    //
    // // 取得 受款人資料
    // List<PaymentDecisionCase> beneficiaryDataList = decisionService.getAllBenData(caseObj.getApNo());
    //
    // // 將 相關查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setBeneficiaryDataList(beneficiaryDataList, request);
    //
    // log.debug("執行 決行作業 - 給付決行作業 - 受款人資料 PaymentDecisionAction.reviewBeneficiaryData() 完成 ... ");
    // return mapping.findForward(FORWARD_BENEFICIARY_DATA);
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentDecisionAction.reviewBeneficiaryData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
    // }
    // }
    //
    // /**
    // * 決行作業 - 給付決行作業 - 核定資料 (baco0d013q04.jsp)
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward reviewIssuData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 決行作業 - 給付決行作業 - 核定資料 PaymentDecisionAction.reviewIssuData() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // PaymentDecisionForm iform = (PaymentDecisionForm) form;
    //
    // try {
    // PaymentDecisionCase caseObj = CaseSessionHelper.getPaymentDecisionCase(request);
    //
    // // 取得 受款人資料
    // List<PaymentDecisionCase> beneficiaryDataList = decisionService.getAllIssuData(caseObj.getApNo());
    //
    // // 將 相關查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setBeneficiaryDataList(beneficiaryDataList, request);
    //
    // log.debug("執行 決行作業 - 給付決行作業 - 核定資料 PaymentDecisionAction.reviewIssuData() 完成 ... ");
    // return mapping.findForward(FORWARD_ISSU_DATA);
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentDecisionAction.reviewIssuData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
    // }
    // }
    //
    // /**
    // * 決行作業 - 給付決行作業 - 年資資料 (baco0d013q03.jsp)
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward reviewSeniorityData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 決行作業 - 給付決行作業 - 年資資料 PaymentDecisionAction.reviewSeniorityData() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // PaymentDecisionForm iform = (PaymentDecisionForm) form;
    //
    // try {
    // PaymentDecisionCase caseObj = CaseSessionHelper.getPaymentDecisionCase(request);
    // // 取得 欠費期間資料
    // List<PaymentDecisionExtCase> seniList = decisionService.getSenimaintData(caseObj.getApNo(), "0");
    // // 取得 承保異動資料
    // List<PaymentDecisionExtCase> ciptList = decisionService.getCiptData(caseObj.getEvtIdnNo(), "L");
    //
    // // 將 相關查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setSenimaintDataList(seniList, request);
    // CaseSessionHelper.setCiptDataList(ciptList, request);
    //
    // log.debug("執行 決行作業 - 給付決行作業 - 年資資料 PaymentDecisionAction.reviewSeniorityData() 完成 ... ");
    // return mapping.findForward(FORWARD_SENIORITY_DATA);
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentDecisionAction.reviewSeniorityData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
    // }
    // }
    //
    // /**
    // * 決行作業 - 給付決行作業 - 返回
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward doBackMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 決行作業 - 給付決行作業 - 返回 PaymentDecisionAction.doBackMaster() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    //
    // try {
    // PaymentDecisionCase tempCaseObj = CaseSessionHelper.getPaymentDecisionCase(request);
    // String apNo = tempCaseObj.getApNo();
    // List<PaymentDecisionCase> dataList = decisionService.selectDetailDecisionData(apNo);
    //
    // if (dataList.size() == 1) {
    // PaymentDecisionCase caseObj = dataList.get(0);
    // // XX函註記
    // caseObj = decisionService.getLetterTypeMk(caseObj);
    //
    // // 2009.02.07 拿掉審核管制條件 by Rickychi
    // // [
    // // Baappbase condObj = reviewService.getReviewCond(apNo);// 審核管制條件
    // // caseObj.setIsShowCond1(condObj.getIsShowCond1());// 審核管制條件1
    // // caseObj.setIsShowCond2(condObj.getIsShowCond2());// 審核管制條件2
    // // caseObj.setIsShowCond3(condObj.getIsShowCond3());// 審核管制條件3
    // // caseObj.setIsShowCond4(condObj.getIsShowCond4());// 審核管制條件4
    // // caseObj.setIsShowCond5(condObj.getIsShowCond5());// 審核管制條件5
    // // caseObj.setIsShowCond6(condObj.getIsShowCond6());// 審核管制條件6
    // // caseObj.setIsShowCond7(condObj.getIsShowCond7());// 審核管制條件7
    // // caseObj.setIsShowCond8(condObj.getIsShowCond8());// 審核管制條件8
    // // caseObj.setIsShowCond9(condObj.getIsShowCond9());// 審核管制條件9
    // // ]
    //
    // // 取得編審註記資料
    // List<PaymentDecisionExtCase> chkList = decisionService.getPaymentReviewChkList(apNo, "0000");
    //
    // // 取得受款人姓名清單
    // List<PaymentDecisionExtCase> benNameList = decisionService.getBenNameList(apNo, "1");
    //
    // // 將 查詢結果 case 存到 Request Scope
    // CaseSessionHelper.setPaymentDecisionCase(caseObj, request);
    // CaseSessionHelper.setPaymentDecisionChkList(chkList, request);
    // CaseSessionHelper.setPaymentDecisionBenNameList(benNameList, request);
    //
    // log.debug("執行 決行作業 - 給付決行作業 - 返回 PaymentDecisionAction.doBackMaster() 完成 ... ");
    // return mapping.findForward(FORWARD_SINGLE_REVIEW);
    // }
    // else {
    // // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
    // saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());
    // log.debug("執行 決行作業 - 給付決行作業 - 返回 PaymentDecisionAction.doBackMaster() 完成 ... ");
    // return mapping.findForward(ConstantKey.FORWARD_BACK);
    // }
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentDecisionAction.doBackMaster() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(ConstantKey.FORWARD_BACK);
    // }
    // }
    /*
     * ================================================End================================================
     */

    /**
     * 決行作業 - 給付決行作業 - 受款人資料 (baco0d011q.jsp) 修改確認(多筆)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSaveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 給付決行作業 - 受款人資料 - 修改確認(多筆) PaymentReviewAction.doSaveData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentDecisionForm iform = (PaymentDecisionForm) form;

        try {
            if (!("").equals(iform.getApNoOfConfirm())) {
                decisionService.updateData(iform.getApNoOfConfirm(), userData);

                // 設定更新成功訊息
                saveMessages(session, CustomMessageHelper.getDecisionSuccessMessage());

                // 重新查詢 受款人資料
                PaymentDecisionForm qryCondForm = FormSessionHelper.getDecisionQueryForm(request);
                List<PaymentDecisionCase> dataList = new ArrayList<PaymentDecisionCase>();

                // 用 受理編號 查詢
                if (("1").equals(iform.getQryCond())) {
                    dataList = decisionService.getDecisionDataListByApNo(qryCondForm.getApNoStr(), userData.getEmpNo());
                }
                // 用 列印日期 + 頁次 查詢
                else if (("2").equals(iform.getQryCond())) {
                    dataList = decisionService.selectDecisionDataByRptDate(DateUtility.changeDateType(qryCondForm.getRptDate()), qryCondForm.getPageNo(), qryCondForm.getChkMan(), userData.getEmpNo());
                }

                if (dataList.size() <= 0) {
                    // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                    FormSessionHelper.removePaymentDecisionForm(request);
                    FormSessionHelper.removeDecisionQueryForm(request);
                    CaseSessionHelper.removeAllPaymentDecisionCase(request);

                    log.debug("執行 決行作業 - 給付決行作業 - 查詢頁面 PaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else {
                    PaymentDecisionCase caseForTitle = new PaymentDecisionCase();
                    BeanUtility.copyProperties(caseForTitle, dataList.get(0));

                    // 計算 核定金額合計 & 實付金額合計
                    BigDecimal tissueAmt = new BigDecimal(0);
                    BigDecimal taplPayAmt = new BigDecimal(0);
                    for (int i = 0; i < dataList.size(); i++) {
                        PaymentDecisionCase obj = dataList.get(i);
                        tissueAmt = tissueAmt.add(obj.getTissueAmt());
                        taplPayAmt = taplPayAmt.add(obj.getTaplPayAmt());
                    }
                    caseForTitle.setTissueAmt(tissueAmt);
                    caseForTitle.setTaplPayAmt(taplPayAmt);

                    // 將 查詢條件/查詢結果清單 存到 Request Scope
                    // qryCondForm.setResultSize(new BigDecimal(dataList.size()));
                    // FormSessionHelper.setDecisionQueryForm(qryCondForm, request);
                    // CaseSessionHelper.setPaymentDecisionCaseForTitle(caseForTitle, request);// title預設抓第一筆資料
                    // CaseSessionHelper.setPaymentDecisionList(dataList, request);

                    // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                    FormSessionHelper.removePaymentDecisionForm(request);
                    FormSessionHelper.removeDecisionQueryForm(request);
                    CaseSessionHelper.removeAllPaymentDecisionCase(request);

                    log.debug("執行 決行作業 - 給付決行作業 - 受款人資料 - 修改確認(多筆) PaymentReviewAction.doSaveData() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
                }
            }
            else {// 無資料可更新
                ActionMessages msgs = new ActionMessages();
                msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.decision.noDataToUpdate"));
                saveMessages(session, msgs);
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("PaymentDecisionAction.doSaveData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getDecisionFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 決行作業 - 給付決行作業 - 返回清單
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 給付決行作業 - 返回清單 PaymentDecisionAction.doBackList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            List<PaymentDecisionCase> dataList = new ArrayList<PaymentDecisionCase>();
            PaymentDecisionForm qryCondForm = FormSessionHelper.getDecisionQueryForm(request);
            String apNo = qryCondForm.getApNoStr();

            if (("1").equals(qryCondForm.getQryCond()) || ("2").equals(qryCondForm.getQryCond())) {
                // 用 受理編號 查詢
                if (("1").equals(qryCondForm.getQryCond())) {
                    dataList = decisionService.getDecisionDataListByApNo(apNo, userData.getEmpNo());
                }
                // 用 列印日期 + 頁次 查詢
                else if (("2").equals(qryCondForm.getQryCond())) {
                    dataList = decisionService.selectDecisionDataByRptDate(DateUtility.changeDateType(qryCondForm.getRptDate()), qryCondForm.getPageNo(), qryCondForm.getChkMan(), userData.getEmpNo());
                }

                if (dataList.size() <= 0) {
                    // 設定查無資料訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                    log.debug("執行 決行作業 - 給付決行作業 - 查詢頁面 PaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else {
                    PaymentDecisionCase caseForTitle = new PaymentDecisionCase();
                    BeanUtility.copyProperties(caseForTitle, dataList.get(0));

                    // 計算 核定金額合計 & 實付金額合計
                    BigDecimal tissueAmt = new BigDecimal(0);
                    BigDecimal taplPayAmt = new BigDecimal(0);
                    for (int i = 0; i < dataList.size(); i++) {
                        PaymentDecisionCase obj = dataList.get(i);
                        tissueAmt = tissueAmt.add(obj.getTissueAmt());
                        taplPayAmt = taplPayAmt.add(obj.getTaplPayAmt());
                    }
                    caseForTitle.setTissueAmt(tissueAmt);
                    caseForTitle.setTaplPayAmt(taplPayAmt);

                    // 將 查詢條件/查詢結果清單 存到 Request Scope
                    qryCondForm.setResultSize(new BigDecimal(dataList.size()));
                    FormSessionHelper.setDecisionQueryForm(qryCondForm, request);
                    CaseSessionHelper.setPaymentDecisionCaseForTitle(caseForTitle, request);// title預設抓第一筆資料
                    CaseSessionHelper.setPaymentDecisionList(dataList, request);

                    log.debug("執行 決行作業 - 給付決行作業 - 查詢頁面 PaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
                }
            }
            else {
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentDecisionAction.doBackList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        }
    }

    /**
     * 決行作業 - 給付決行作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 給付決行作業 - 返回 PaymentDecisionAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removePaymentDecisionForm(request);
        FormSessionHelper.removeDecisionQueryForm(request);
        CaseSessionHelper.removeAllPaymentDecisionCase(request);

        log.debug("執行 決行作業 - 給付決行作業 - 返回 PaymentDecisionAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 決行作業 - 給付決行作業 - 檢視受理審核清單
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReviewRpt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 給付決行作業 - 檢視受理審核清單 PaymentDecisionAction.doReviewRpt() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentDecisionForm iform = (PaymentDecisionForm) form;
        try {
            PaymentDecisionCase detailCase = CaseSessionHelper.getPaymentDecisionCase(request);
            ;
            OldAgeReviewRpt01Action printOldAgeReviewRpt01 = new OldAgeReviewRpt01Action();
            printOldAgeReviewRpt01.setRptService((RptService) SpringHelper.getBeanById("rptService"));
            OldAgeReviewRpt01Form rptForm = new OldAgeReviewRpt01Form();
            rptForm.setApNo1Begin(detailCase.getApNo1());
            rptForm.setApNo2Begin(detailCase.getApNo2());
            rptForm.setApNo3Begin(detailCase.getApNo3());
            rptForm.setApNo4Begin(detailCase.getApNo4());
            rptForm.setApNo1End(detailCase.getApNo1());
            rptForm.setApNo2End(detailCase.getApNo2());
            rptForm.setApNo3End(detailCase.getApNo3());
            rptForm.setApNo4End(detailCase.getApNo4());
            printOldAgeReviewRpt01.doReport(mapping, rptForm, request, response);
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentDecisionAction.doReviewRpt() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
        }
    }

    public void setDecisionService(DecisionService decisionService) {
        this.decisionService = decisionService;
    }
}
