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
import tw.gov.bli.ba.decision.cases.DisabledPaymentDecisionCase;
import tw.gov.bli.ba.decision.cases.DisabledPaymentDecisionExtCase;
import tw.gov.bli.ba.decision.forms.DisabledPaymentDecisionForm;
import tw.gov.bli.ba.decision.helper.CaseSessionHelper;
import tw.gov.bli.ba.decision.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewCase;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewExtCase;
import tw.gov.bli.ba.review.forms.DisabledPaymentReviewForm;
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
 * 決行作業 - 失能年金給付決行作業 (BARC0D110A)
 * 
 * @author Rickychi
 */
public class DisabledPaymentDecisionAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DisabledPaymentDecisionAction.class);

    private DecisionService decisionService;
    // 決行作業 - 失能年金給付決行作業 - 審核清單明細資料 頁面
    private static final String FORWARD_SINGLE_PAYMENT_DETAIL = "singleReviewDetail";
    private static final String FORWARD_SINGLE_PAYMENT_DETAIL_FAIL = "singleReviewDetailFail";

    /**
     * 決行作業 - 失能年金給付決行作業 - 查詢頁面 (baco0d110a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 失能年金給付決行作業 - 查詢頁面 DisabledPaymentDecisionAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPaymentDecisionForm iform = (DisabledPaymentDecisionForm) form;

        try {
            List<DisabledPaymentDecisionCase> dataList = new ArrayList<DisabledPaymentDecisionCase>();
            String apNo = iform.getApNoStr();// 畫面輸入之受理編號

            if (("1").equals(iform.getQryCond()) || ("2").equals(iform.getQryCond())) {
                // 用 受理編號 查詢
                if (("1").equals(iform.getQryCond())) {
                    dataList = decisionService.getDecisionDataListByApNoForDisabled(apNo, userData.getEmpNo());
                }
                // 用 列印日期 + 頁次 查詢
                else if (("2").equals(iform.getQryCond())) {
                    dataList = decisionService.selectDecisionDataByRptDateForDisabled(DateUtility.changeDateType(iform.getRptDate()), iform.getPageNo(), iform.getChkMan(), userData.getEmpNo());
                }

                if (dataList.size() <= 0) {
                    // 設定查無資料訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                    log.debug("執行 決行作業 - 失能年金給付決行作業 - 查詢頁面 DisabledPaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else {
                    DisabledPaymentDecisionCase caseForTitle = new DisabledPaymentDecisionCase();
                    BeanUtility.copyProperties(caseForTitle, dataList.get(0));

                    // 計算 核定金額合計 & 實付金額合計
                    BigDecimal tissueAmt = new BigDecimal(0);
                    BigDecimal taplPayAmt = new BigDecimal(0);
                    for (int i = 0; i < dataList.size(); i++) {
                        DisabledPaymentDecisionCase obj = dataList.get(i);
                        tissueAmt = tissueAmt.add(obj.getTissueAmt());
                        taplPayAmt = taplPayAmt.add(obj.getTaplPayAmt());
                    }
                    caseForTitle.setTissueAmt(tissueAmt);
                    caseForTitle.setTaplPayAmt(taplPayAmt);

                    // 將 查詢條件/查詢結果清單 存到 Request Scope
                    DisabledPaymentDecisionForm qryCondForm = new DisabledPaymentDecisionForm();
                    BeanUtility.copyProperties(qryCondForm, iform);
                    qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                    FormSessionHelper.setDisabledPaymentDecisionQueryForm(qryCondForm, request);
                    CaseSessionHelper.setDisabledPaymentDecisionCaseForTitle(caseForTitle, request);// title預設抓第一筆資料
                    CaseSessionHelper.setDisabledPaymentDecisionList(dataList, request);

                    log.debug("執行 決行作業 - 失能年金給付決行作業 - 查詢頁面 DisabledPaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
                }
            }
            else {
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentDecisionAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 個案審核 (baco0d111a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSingleReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 失能年金給付決行作業 - 個案審核 DisabledPaymentDecisionAction.doSingleReview() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPaymentDecisionForm iform = (DisabledPaymentDecisionForm) form;

        try {
            String apNo = iform.getApNo();
            List<DisabledPaymentDecisionCase> dataList = decisionService.selectDetailDecisionDataForDisabled(apNo);

            if (dataList.size() == 1) {
                DisabledPaymentDecisionCase caseObj = dataList.get(0);
                apNo = caseObj.getApNo();

                // 取得 XX函註記
                List<DisabledPaymentDecisionCase> detail1 = decisionService.getLetterTypeMk1ListForDisabled(apNo);
                List<DisabledPaymentDecisionCase> detail2 = decisionService.getLetterTypeMk2ListForDisabled(apNo);
                List<DisabledPaymentDecisionCase> detail3 = decisionService.getLetterTypeMk3ListForDisabled(apNo);
                List<DisabledPaymentDecisionCase> detail4 = decisionService.getLetterTypeMk4ListForDisabled(apNo);
                List<DisabledPaymentDecisionCase> detail5 = decisionService.getLetterTypeMk5ListForDisabled(apNo);
                DisabledPaymentDecisionCase detail6 = decisionService.getLetterTypeMk6ListForDisabled(apNo);

                // 取得 職災相關資料
                DisabledPaymentDecisionCase occAccData = decisionService.getOccAccDataForDisabled(apNo);
                // 取得 失能相關資料
                DisabledPaymentDecisionCase disabledData = decisionService.selectDisabledDataForDisabled(apNo);
                // 取得 核定資料
                List<DisabledPaymentDecisionExtCase> payList = decisionService.getPayDataForDisabled(caseObj.getApNo(), caseObj.getIssuYm());
                // 取得 事故者編審註記資料
                List<DisabledPaymentDecisionExtCase> evtChkList = decisionService.getDisabledPaymentDecisionEvtChkList(caseObj.getApNo());
                // 取得 眷屬編審註記資料
                List<DisabledPaymentDecisionCase> benChkList = decisionService.getDisabledPaymentDecisionBenChkList(caseObj.getApNo());
                // 取得 符合註記資料
                List<DisabledPaymentDecisionCase> matchChkList = decisionService.getDisabledPaymentDecisionOtherChkList(caseObj.getApNo());
                // 取得 受款人資料
                List<DisabledPaymentDecisionCase> beneficiaryDataList = decisionService.getAllIssuDataForDisabled(caseObj.getApNo());
                // 取得受款人姓名清單
                List<DisabledPaymentDecisionExtCase> benNameList = decisionService.getBenNameListForDisabled(apNo, "1");

                DisabledPaymentDecisionForm modifyForm = new DisabledPaymentDecisionForm();
                BeanUtility.copyProperties(modifyForm, caseObj);
                DisabledPaymentDecisionForm qryCondForm = FormSessionHelper.getDisabledPaymentDecisionQueryForm(request);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果 case 存到 Request Scope
                FormSessionHelper.setDisabledPaymentDecisionForm(modifyForm, request);
                FormSessionHelper.setDisabledPaymentDecisionQueryForm(qryCondForm, request);

                CaseSessionHelper.setDisabledPaymentDecisionCase(caseObj, request);
                CaseSessionHelper.setDisabledPaymentDecisionOccAccData(occAccData, request);
                CaseSessionHelper.setDisabledPaymentDecisionDisabledData(disabledData, request);
                CaseSessionHelper.setDisabledPayDataList(payList, request);
                CaseSessionHelper.setDisabledPaymentDecisionEvtChkList(evtChkList, request);
                CaseSessionHelper.setDisabledPaymentDecisionBenChkList(benChkList, request);
                CaseSessionHelper.setDisabledPaymentDecisionMatchChkList(matchChkList, request);
                CaseSessionHelper.setDisabledBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setDisabledPaymentDecisionBenNameList(benNameList, request);
                CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk6List(detail6, request);

                log.debug("執行 決行作業 - 失能年金給付決行作業 - 個案審核 DisabledPaymentDecisionAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
            }
            else {
                // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                log.debug("執行 決行作業 - 失能年金給付決行作業 - 個案審核 DisabledPaymentDecisionAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentDecisionAction.doSingleReview() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL_FAIL);
        }
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 受款人資料 (baco0d111q.jsp) 修改確認(多筆)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSaveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 失能年金給付決行作業 - 受款人資料 - 修改確認(多筆) PaymentReviewAction.doSaveData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPaymentDecisionForm iform = (DisabledPaymentDecisionForm) form;

        try {
            if (!("").equals(iform.getApNoOfConfirm())) {
                decisionService.updateDataForDisabled(iform.getApNoOfConfirm(), userData);

                // 設定更新成功訊息
                saveMessages(session, CustomMessageHelper.getDecisionSuccessMessage());

                // 重新查詢 受款人資料
                DisabledPaymentDecisionForm qryCondForm = FormSessionHelper.getDisabledPaymentDecisionQueryForm(request);
                List<DisabledPaymentDecisionCase> dataList = new ArrayList<DisabledPaymentDecisionCase>();

                // 用 受理編號 查詢
                if (("1").equals(iform.getQryCond())) {
                    dataList = decisionService.getDecisionDataListByApNoForDisabled(qryCondForm.getApNoStr(), userData.getEmpNo());
                }
                // 用 列印日期 + 頁次 查詢
                else if (("2").equals(iform.getQryCond())) {
                    dataList = decisionService.selectDecisionDataByRptDateForDisabled(DateUtility.changeDateType(qryCondForm.getRptDate()), qryCondForm.getPageNo(), qryCondForm.getChkMan(), userData.getEmpNo());
                }

                if (dataList.size() <= 0) {
                    // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                    FormSessionHelper.removeDisabledPaymentDecisionForm(request);
                    FormSessionHelper.removeDisabledPaymentDecisionQueryForm(request);
                    CaseSessionHelper.removeAllDisabledPaymentDecisionCase(request);

                    log.debug("執行 決行作業 - 失能年金給付決行作業 - 查詢頁面 DisabledPaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else {
                    DisabledPaymentDecisionCase caseForTitle = new DisabledPaymentDecisionCase();
                    BeanUtility.copyProperties(caseForTitle, dataList.get(0));

                    // 計算 核定金額合計 & 實付金額合計
                    BigDecimal tissueAmt = new BigDecimal(0);
                    BigDecimal taplPayAmt = new BigDecimal(0);
                    for (int i = 0; i < dataList.size(); i++) {
                        DisabledPaymentDecisionCase obj = dataList.get(i);
                        tissueAmt = tissueAmt.add(obj.getTissueAmt());
                        taplPayAmt = taplPayAmt.add(obj.getTaplPayAmt());
                    }
                    caseForTitle.setTissueAmt(tissueAmt);
                    caseForTitle.setTaplPayAmt(taplPayAmt);

                    // 將 查詢條件/查詢結果清單 存到 Request Scope
                    // qryCondForm.setResultSize(new BigDecimal(dataList.size()));
                    // FormSessionHelper.setDisabledPaymentDecisionQueryForm(qryCondForm, request);
                    // CaseSessionHelper.setDisabledPaymentDecisionCaseForTitle(caseForTitle, request);// title預設抓第一筆資料
                    // CaseSessionHelper.setDisabledPaymentDecisionList(dataList, request);

                    // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                    FormSessionHelper.removeDisabledPaymentDecisionForm(request);
                    FormSessionHelper.removeDisabledPaymentDecisionQueryForm(request);
                    CaseSessionHelper.removeAllDisabledPaymentDecisionCase(request);

                    log.debug("執行 決行作業 - 失能年金給付決行作業 - 受款人資料 - 修改確認(多筆) PaymentReviewAction.doSaveData() 完成 ... ");
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
            log.error("DisabledPaymentDecisionAction.doSaveData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getDecisionFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 返回清單
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 失能年金給付決行作業 - 返回清單 DisabledPaymentDecisionAction.doBackList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            List<DisabledPaymentDecisionCase> dataList = new ArrayList<DisabledPaymentDecisionCase>();
            DisabledPaymentDecisionForm qryCondForm = FormSessionHelper.getDisabledPaymentDecisionQueryForm(request);
            String apNo = qryCondForm.getApNoStr();

            if (("1").equals(qryCondForm.getQryCond()) || ("2").equals(qryCondForm.getQryCond())) {
                // 用 受理編號 查詢
                if (("1").equals(qryCondForm.getQryCond())) {
                    dataList = decisionService.getDecisionDataListByApNoForDisabled(apNo, userData.getEmpNo());
                }
                // 用 列印日期 + 頁次 查詢
                else if (("2").equals(qryCondForm.getQryCond())) {
                    dataList = decisionService.selectDecisionDataByRptDateForDisabled(DateUtility.changeDateType(qryCondForm.getRptDate()), qryCondForm.getPageNo(), qryCondForm.getChkMan(), userData.getEmpNo());
                }

                if (dataList.size() <= 0) {
                    // 設定查無資料訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                    log.debug("執行 決行作業 - 失能年金給付決行作業 - 查詢頁面 DisabledPaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else {
                    DisabledPaymentDecisionCase caseForTitle = new DisabledPaymentDecisionCase();
                    BeanUtility.copyProperties(caseForTitle, dataList.get(0));

                    // 計算 核定金額合計 & 實付金額合計
                    BigDecimal tissueAmt = new BigDecimal(0);
                    BigDecimal taplPayAmt = new BigDecimal(0);
                    for (int i = 0; i < dataList.size(); i++) {
                        DisabledPaymentDecisionCase obj = dataList.get(i);
                        tissueAmt = tissueAmt.add(obj.getTissueAmt());
                        taplPayAmt = taplPayAmt.add(obj.getTaplPayAmt());
                    }
                    caseForTitle.setTissueAmt(tissueAmt);
                    caseForTitle.setTaplPayAmt(taplPayAmt);

                    // 將 查詢條件/查詢結果清單 存到 Request Scope
                    qryCondForm.setResultSize(new BigDecimal(dataList.size()));
                    FormSessionHelper.setDisabledPaymentDecisionQueryForm(qryCondForm, request);
                    CaseSessionHelper.setDisabledPaymentDecisionCaseForTitle(caseForTitle, request);// title預設抓第一筆資料
                    CaseSessionHelper.setDisabledPaymentDecisionList(dataList, request);

                    log.debug("執行 決行作業 - 失能年金給付決行作業 - 查詢頁面 DisabledPaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
                }
            }
            else {
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentDecisionAction.doBackList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        }
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 失能年金給付決行作業 - 返回 DisabledPaymentDecisionAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removeDisabledPaymentDecisionForm(request);
        FormSessionHelper.removeDisabledPaymentDecisionQueryForm(request);
        CaseSessionHelper.removeAllDisabledPaymentDecisionCase(request);

        log.debug("執行 決行作業 - 失能年金給付決行作業 - 返回 DisabledPaymentDecisionAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 檢視受理審核清單
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReviewRpt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 失能年金給付決行作業 - 檢視受理審核清單 DisabledPaymentDecisionAction.doReviewRpt() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPaymentDecisionForm iform = (DisabledPaymentDecisionForm) form;
        try {
            DisabledPaymentDecisionCase detailCase = CaseSessionHelper.getDisabledPaymentDecisionCase(request);
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
            log.error("DisabledPaymentDecisionAction.doReviewRpt() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
        }
    }

    public void setDecisionService(DecisionService decisionService) {
        this.decisionService = decisionService;
    }
}
