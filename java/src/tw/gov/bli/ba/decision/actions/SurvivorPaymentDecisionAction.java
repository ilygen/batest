package tw.gov.bli.ba.decision.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.decision.cases.SurvivorPaymentDecisionCase;
import tw.gov.bli.ba.decision.cases.DisabledPaymentDecisionExtCase;
import tw.gov.bli.ba.decision.cases.SurvivorPaymentDecisionCase;
import tw.gov.bli.ba.decision.cases.SurvivorPaymentDecisionExtCase;
import tw.gov.bli.ba.decision.forms.SurvivorPaymentDecisionForm;
import tw.gov.bli.ba.decision.helper.CaseSessionHelper;
import tw.gov.bli.ba.decision.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewCase;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewExtCase;
import tw.gov.bli.ba.review.forms.SurvivorPaymentReviewForm;
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
 * 決行作業 - 遺屬年金給付決行作業 (BARC0D210A)
 * 
 * @author Rickychi
 */
public class SurvivorPaymentDecisionAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(SurvivorPaymentDecisionAction.class);

    private DecisionService decisionService;
    // 決行作業 - 遺屬年金給付決行作業 - 審核清單明細資料 頁面
    private static final String FORWARD_SINGLE_PAYMENT_DETAIL = "singleReviewDetail";
    private static final String FORWARD_SINGLE_PAYMENT_DETAIL_FAIL = "singleReviewDetailFail";

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 查詢頁面 (baco0d210a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 查詢頁面 SurvivorPaymentDecisionAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorPaymentDecisionForm iform = (SurvivorPaymentDecisionForm) form;

        try {
            List<SurvivorPaymentDecisionCase> dataList = new ArrayList<SurvivorPaymentDecisionCase>();
            String apNo = iform.getApNoStr();// 畫面輸入之受理編號

            if (("1").equals(iform.getQryCond()) || ("2").equals(iform.getQryCond())) {
                // 用 受理編號 查詢
                if (("1").equals(iform.getQryCond())) {
                    dataList = decisionService.getDecisionDataListByApNoForSurvivor(apNo, userData.getEmpNo());
                }
                // 用 列印日期 + 頁次 查詢
                else if (("2").equals(iform.getQryCond())) {
                    dataList = decisionService.selectDecisionDataByRptDateForSurvivor(DateUtility.changeDateType(iform.getRptDate()), iform.getPageNo(), iform.getChkMan(), userData.getEmpNo());
                }

                if (dataList.size() <= 0) {
                    // 設定查無資料訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                    log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 查詢頁面 SurvivorPaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else {
                    SurvivorPaymentDecisionCase caseForTitle = new SurvivorPaymentDecisionCase();
                    BeanUtility.copyProperties(caseForTitle, dataList.get(0));

                    // 計算 核定金額合計 & 實付金額合計
                    BigDecimal tissueAmt = new BigDecimal(0);
                    BigDecimal taplPayAmt = new BigDecimal(0);
                    for (int i = 0; i < dataList.size(); i++) {
                        SurvivorPaymentDecisionCase obj = dataList.get(i);
                        tissueAmt = tissueAmt.add(obj.getTissueAmt());
                        taplPayAmt = taplPayAmt.add(obj.getTaplPayAmt());
                    }
                    caseForTitle.setTissueAmt(tissueAmt);
                    caseForTitle.setTaplPayAmt(taplPayAmt);

                    // 將 查詢條件/查詢結果清單 存到 Request Scope
                    SurvivorPaymentDecisionForm qryCondForm = new SurvivorPaymentDecisionForm();
                    BeanUtility.copyProperties(qryCondForm, iform);
                    qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                    FormSessionHelper.setSurvivorPaymentDecisionQueryForm(qryCondForm, request);
                    CaseSessionHelper.setSurvivorPaymentDecisionCaseForTitle(caseForTitle, request);// title預設抓第一筆資料
                    CaseSessionHelper.setSurvivorPaymentDecisionList(dataList, request);

                    log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 查詢頁面 SurvivorPaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
                }
            }
            else {
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorPaymentDecisionAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 個案審核 (baco0d211a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSingleReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 個案審核 SurvivorPaymentDecisionAction.doSingleReview() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorPaymentDecisionForm iform = (SurvivorPaymentDecisionForm) form;

        try {
            String apNo = iform.getApNo();
            List<SurvivorPaymentDecisionCase> dataList = decisionService.selectDetailDecisionDataForSurvivor(apNo);

            if (dataList.size() == 1) {
                SurvivorPaymentDecisionCase caseObj = dataList.get(0);
                apNo = caseObj.getApNo();

                // 取得 XX函註記
                List<SurvivorPaymentDecisionCase> detail1 = decisionService.getLetterTypeMk1ListForSurvivor(apNo);
                List<SurvivorPaymentDecisionCase> detail2 = decisionService.getLetterTypeMk2ListForSurvivor(apNo);
                List<SurvivorPaymentDecisionCase> detail3 = decisionService.getLetterTypeMk3ListForSurvivor(apNo);
                List<SurvivorPaymentDecisionCase> detail4 = decisionService.getLetterTypeMk4ListForSurvivor(apNo);
                List<SurvivorPaymentDecisionCase> detail5 = decisionService.getLetterTypeMk5ListForSurvivor(apNo);
                SurvivorPaymentDecisionCase detail6 = decisionService.getLetterTypeMk6ListForSurvivor(apNo);

                // 取得 失能相關資料
                SurvivorPaymentDecisionCase disabledData = decisionService.selectDisabledDataForSurvivor(apNo);
                // 取得 編審給付資料
                List<SurvivorPaymentDecisionExtCase> payList = decisionService.getPayDataForSurvivor(caseObj.getApNo(), caseObj.getIssuYm());
                // 取得 事故者編審註記資料
                List<SurvivorPaymentDecisionExtCase> evtChkList = decisionService.getSurvivorPaymentDecisionEvtChkList(caseObj.getApNo());
                // 取得 眷屬編審註記資料
                List<SurvivorPaymentDecisionCase> benChkList = decisionService.getSurvivorPaymentDecisionBenChkList(caseObj.getApNo());
                // 取得 符合註記資料
                List<SurvivorPaymentDecisionCase> matchChkList = decisionService.getSurvivorPaymentDecisionOtherChkList(caseObj.getApNo());
                // 取得 事故者/受款人 資料(含事故者核定資料)
                List<SurvivorPaymentDecisionCase> beneficiaryDataList = decisionService.getbeneficiaryDataListForSurvivor(caseObj.getApNo());
                // 取得 所有受款人核定資料(不含事故者)
                List<SurvivorPaymentDecisionExtCase> benIssuDataList = decisionService.getIssuDataListForSurvivor(beneficiaryDataList, caseObj.getApNo(), "0000", "<>");
                // 將受款人核定資料(不含事故者) 轉成頁面顯示用之List 與 session暫存用之map
                List<Object> transDataList = decisionService.transInitBenIssuDataForSurvivor(benIssuDataList);
                // 取得受款人姓名清單
                List<SurvivorPaymentDecisionExtCase> benNameList = decisionService.getBenNameListForSurvivor(apNo, "1");
                // 取得 給付年月下拉選單
                request.getSession().setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_PAYYM_OPTION_LIST, decisionService.getPayYmOptionListForSurvivor(caseObj.getApNo(), caseObj.getIssuYm()));
                // 取得 受款人下拉選單
                request.getSession().setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_SEQNO_OPTION_LIST, decisionService.getSeqNoOptionListForSurvivor(caseObj.getApNo()));

                SurvivorPaymentReviewForm modifyForm = new SurvivorPaymentReviewForm();
                BeanUtility.copyProperties(modifyForm, caseObj);

                // 將 查詢結果 case 存到 Request Scope
                CaseSessionHelper.setSurvivorPaymentDecisionCase(caseObj, request);
                CaseSessionHelper.setSurvivorPaymentDecisionDisabledData(disabledData, request);
                CaseSessionHelper.setSurvivorPayDataList(payList, request);
                CaseSessionHelper.setSurvivorPaymentDecisionEvtChkList(evtChkList, request);
                CaseSessionHelper.setSurvivorPaymentDecisionBenChkList(benChkList, request);
                CaseSessionHelper.setSurvivorPaymentDecisionMatchChkList(matchChkList, request);
                CaseSessionHelper.setSurvivorBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setSurvivorOrigBenIssuDataList(benIssuDataList, request);
                CaseSessionHelper.setSurvivorBenIssuDataList((List<SurvivorPaymentDecisionCase>) transDataList.get(0), request);
                CaseSessionHelper.setSurvivorBenIssuDataMap((Map<BigDecimal, SurvivorPaymentDecisionExtCase>) transDataList.get(1), request);
                CaseSessionHelper.setSurvivorPaymentDecisionBenNameList(benNameList, request);
                CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk6List(detail6, request);

                log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 個案審核 SurvivorPaymentDecisionAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
            }
            else {
                // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 個案審核 SurvivorPaymentDecisionAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorPaymentDecisionAction.doSingleReview() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL_FAIL);
        }
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 受款人資料 (baco0d211q.jsp) 修改確認(多筆)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSaveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 受款人資料 - 修改確認(多筆) PaymentReviewAction.doSaveData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorPaymentDecisionForm iform = (SurvivorPaymentDecisionForm) form;

        try {
            if (!("").equals(iform.getApNoOfConfirm())) {
                decisionService.updateDataForSurvivor(iform.getApNoOfConfirm(), userData);

                // 設定更新成功訊息
                saveMessages(session, CustomMessageHelper.getDecisionSuccessMessage());

                // 重新查詢 受款人資料
                SurvivorPaymentDecisionForm qryCondForm = FormSessionHelper.getSurvivorPaymentDecisionQueryForm(request);
                List<SurvivorPaymentDecisionCase> dataList = new ArrayList<SurvivorPaymentDecisionCase>();

                // 用 受理編號 查詢
                if (("1").equals(iform.getQryCond())) {
                    dataList = decisionService.getDecisionDataListByApNoForSurvivor(qryCondForm.getApNoStr(), userData.getEmpNo());
                }
                // 用 列印日期 + 頁次 查詢
                else if (("2").equals(iform.getQryCond())) {
                    dataList = decisionService.selectDecisionDataByRptDateForSurvivor(DateUtility.changeDateType(qryCondForm.getRptDate()), qryCondForm.getPageNo(), qryCondForm.getChkMan(), userData.getEmpNo());
                }

                if (dataList.size() <= 0) {
                    // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                    FormSessionHelper.removeSurvivorPaymentDecisionForm(request);
                    FormSessionHelper.removeSurvivorPaymentDecisionQueryForm(request);
                    CaseSessionHelper.removeAllSurvivorPaymentDecisionCase(request);

                    log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 查詢頁面 SurvivorPaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else {
                    SurvivorPaymentDecisionCase caseForTitle = new SurvivorPaymentDecisionCase();
                    BeanUtility.copyProperties(caseForTitle, dataList.get(0));

                    // 計算 核定金額合計 & 實付金額合計
                    BigDecimal tissueAmt = new BigDecimal(0);
                    BigDecimal taplPayAmt = new BigDecimal(0);
                    for (int i = 0; i < dataList.size(); i++) {
                        SurvivorPaymentDecisionCase obj = dataList.get(i);
                        tissueAmt = tissueAmt.add(obj.getTissueAmt());
                        taplPayAmt = taplPayAmt.add(obj.getTaplPayAmt());
                    }
                    caseForTitle.setTissueAmt(tissueAmt);
                    caseForTitle.setTaplPayAmt(taplPayAmt);

                    // 將 查詢條件/查詢結果清單 存到 Request Scope
                    // qryCondForm.setResultSize(new BigDecimal(dataList.size()));
                    // FormSessionHelper.setSurvivorPaymentDecisionQueryForm(qryCondForm, request);
                    // CaseSessionHelper.setSurvivorPaymentDecisionCaseForTitle(caseForTitle, request);// title預設抓第一筆資料
                    // CaseSessionHelper.setSurvivorPaymentDecisionList(dataList, request);

                    // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                    FormSessionHelper.removeSurvivorPaymentDecisionForm(request);
                    FormSessionHelper.removeSurvivorPaymentDecisionQueryForm(request);
                    CaseSessionHelper.removeAllSurvivorPaymentDecisionCase(request);

                    log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 受款人資料 - 修改確認(多筆) PaymentReviewAction.doSaveData() 完成 ... ");
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
            log.error("SurvivorPaymentDecisionAction.doSaveData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getDecisionFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 返回清單
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 返回清單 SurvivorPaymentDecisionAction.doBackList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            List<SurvivorPaymentDecisionCase> dataList = new ArrayList<SurvivorPaymentDecisionCase>();
            SurvivorPaymentDecisionForm qryCondForm = FormSessionHelper.getSurvivorPaymentDecisionQueryForm(request);
            String apNo = qryCondForm.getApNoStr();

            if (("1").equals(qryCondForm.getQryCond()) || ("2").equals(qryCondForm.getQryCond())) {
                // 用 受理編號 查詢
                if (("1").equals(qryCondForm.getQryCond())) {
                    dataList = decisionService.getDecisionDataListByApNoForSurvivor(apNo, userData.getEmpNo());
                }
                // 用 列印日期 + 頁次 查詢
                else if (("2").equals(qryCondForm.getQryCond())) {
                    dataList = decisionService.selectDecisionDataByRptDateForSurvivor(DateUtility.changeDateType(qryCondForm.getRptDate()), qryCondForm.getPageNo(), qryCondForm.getChkMan(), userData.getEmpNo());
                }

                if (dataList.size() <= 0) {
                    // 設定查無資料訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                    log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 查詢頁面 SurvivorPaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else {
                    SurvivorPaymentDecisionCase caseForTitle = new SurvivorPaymentDecisionCase();
                    BeanUtility.copyProperties(caseForTitle, dataList.get(0));

                    // 計算 核定金額合計 & 實付金額合計
                    BigDecimal tissueAmt = new BigDecimal(0);
                    BigDecimal taplPayAmt = new BigDecimal(0);
                    for (int i = 0; i < dataList.size(); i++) {
                        SurvivorPaymentDecisionCase obj = dataList.get(i);
                        tissueAmt = tissueAmt.add(obj.getTissueAmt());
                        taplPayAmt = taplPayAmt.add(obj.getTaplPayAmt());
                    }
                    caseForTitle.setTissueAmt(tissueAmt);
                    caseForTitle.setTaplPayAmt(taplPayAmt);

                    // 將 查詢條件/查詢結果清單 存到 Request Scope
                    qryCondForm.setResultSize(new BigDecimal(dataList.size()));
                    FormSessionHelper.setSurvivorPaymentDecisionQueryForm(qryCondForm, request);
                    CaseSessionHelper.setSurvivorPaymentDecisionCaseForTitle(caseForTitle, request);// title預設抓第一筆資料
                    CaseSessionHelper.setSurvivorPaymentDecisionList(dataList, request);

                    log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 查詢頁面 SurvivorPaymentDecisionAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
                }
            }
            else {
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorPaymentDecisionAction.doBackList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        }
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 檢視受理審核清單
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReviewRpt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 檢視受理審核清單 SurvivorPaymentDecisionAction.doReviewRpt() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorPaymentDecisionForm iform = (SurvivorPaymentDecisionForm) form;
        try {
            SurvivorPaymentDecisionCase detailCase = CaseSessionHelper.getSurvivorPaymentDecisionCase(request);
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
            log.error("SurvivorPaymentDecisionAction.doReviewRpt() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_SINGLE_PAYMENT_DETAIL);
        }
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 返回 SurvivorPaymentDecisionAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removeSurvivorPaymentDecisionForm(request);
        FormSessionHelper.removeSurvivorPaymentDecisionQueryForm(request);
        CaseSessionHelper.removeAllSurvivorPaymentDecisionCase(request);

        log.debug("執行 決行作業 - 遺屬年金給付決行作業 - 返回 SurvivorPaymentDecisionAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setDecisionService(DecisionService decisionService) {
        this.decisionService = decisionService;
    }
}
