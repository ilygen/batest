package tw.gov.bli.ba.review.actions;

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
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.review.cases.PaymentReviewCase;
import tw.gov.bli.ba.review.cases.PaymentReviewExtCase;
import tw.gov.bli.ba.review.forms.PaymentReviewForm;
import tw.gov.bli.ba.review.helper.CaseSessionHelper;
import tw.gov.bli.ba.review.helper.FormSessionHelper;
import tw.gov.bli.ba.rpt.actions.OldAgeReviewRpt01Action;
import tw.gov.bli.ba.rpt.forms.OldAgeReviewRpt01Form;
import tw.gov.bli.ba.services.ReviewService;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 審核作業 - 給付審核作業 (BACO0D010A)
 *
 * @author Rickychi
 */
public class PaymentReviewAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(PaymentReviewAction.class);

    private ReviewService reviewService;
    private SelectOptionService selectOptionService;

    // 審核作業 - 給付審核作業 - 審核清單明細資料 頁面
    private static final String FORWARD_SINGLE_REVIEW_DETAIL = "singleReviewDetail";
    private static final String FORWARD_SINGLE_REVIEW_DETAIL_FAIL = "singleReviewDetailFail";

    /**
     * 審核作業 - 給付審核作業 - 查詢頁面 (baco0d010a.jsp)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 給付審核作業 - 查詢頁面 PaymentReviewAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentReviewForm iform = (PaymentReviewForm) form;

        try {
            List<PaymentReviewCase> dataList = new ArrayList<PaymentReviewCase>();
            String apNo = iform.getApNoStr();// 畫面輸入之受理編號

            dataList = reviewService.selectReviewDataFromBaappbase(apNo);

            if (dataList.size() <= 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 審核作業 - 給付審核作業 - 查詢頁面 PaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            else if (dataList.size() == 1) {
                PaymentReviewCase caseObj = new PaymentReviewCase();
                caseObj = dataList.get(0);

                // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
                caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(apNo, caseObj.getIssuYm()));
                caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(apNo, caseObj.getIssuYm()));

                // 取得 XX函註記
                List<PaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForOldAge(apNo);
                List<PaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForOldAge(apNo);
                List<PaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForOldAge(apNo);
                List<PaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForOldAge(apNo);
                List<PaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForOldAge(apNo);
                PaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForOldAge(apNo);

                // 取得 核定資料
                List<PaymentReviewExtCase> payList = reviewService.getPayData(caseObj.getApNo(), caseObj.getIssuYm());
                BigDecimal issueAmtTotal = BigDecimal.ZERO;
                for (PaymentReviewExtCase paymentReviewExtCase : payList) {
                    issueAmtTotal = issueAmtTotal.add(paymentReviewExtCase.getIssueAmt());
                }
                caseObj.setIssueAmtTotal(issueAmtTotal);
                // 取得 事故者編審註記資料
                List<PaymentReviewExtCase> chkList = reviewService.getOldAgePaymentReviewEvtChkList(apNo);
                // 取得 受款人編審註記資料
                List<PaymentReviewCase> benChkList = reviewService.getOldAgePaymentReviewBenChkList(apNo);
                // 取得 受款人資料
                List<PaymentReviewCase> beneficiaryDataList = reviewService.getAllIssuData(caseObj.getApNo());

                // 取得 更正原因 清單
                request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
                // 取得受款人姓名清單
                List<PaymentReviewExtCase> benNameList = reviewService.getBenNameList(apNo, "1");

                PaymentReviewForm modifyForm = new PaymentReviewForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, caseObj, ConstantKey.OLD_FIELD_PREFIX);

                PaymentReviewForm qryCondForm = new PaymentReviewForm();
                BeanUtility.copyProperties(qryCondForm, iform);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果 case 存到 Request Scope
                CaseSessionHelper.setPaymentReviewCase(caseObj, request);
                CaseSessionHelper.setPayDataList(payList, request);
                CaseSessionHelper.setPaymentReviewChkList(chkList, request);
                CaseSessionHelper.setPaymentReviewBenChkList(benChkList, request);
                CaseSessionHelper.setBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setPaymentReviewBenNameList(benNameList, request);
                FormSessionHelper.setPaymentReviewForm(modifyForm, request);
                FormSessionHelper.setReviewQueryForm(qryCondForm, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk6List(detail6, request);

                log.debug("執行 審核作業 - 給付審核作業 - 查詢頁面 PaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
            }
            else {
                PaymentReviewForm qryCondForm = new PaymentReviewForm();
                BeanUtility.copyPropertiesForUpdate(qryCondForm, iform, ConstantKey.OLD_FIELD_PREFIX);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentReviewCaseForTitle(dataList.get(0), request);// title預設抓第一筆資料
                CaseSessionHelper.setPaymentReviewList(dataList, request);
                FormSessionHelper.setReviewQueryForm(qryCondForm, request);

                log.debug("執行 審核作業 - 給付審核作業 - 查詢頁面 PaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentReviewAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 審核作業 - 給付審核作業 - 個案審核 (baco0d011a.jsp)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSingleReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 給付審核作業 - 個案審核 PaymentReviewAction.doSingleReview() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentReviewForm iform = (PaymentReviewForm) form;

        try {
            String apNo = iform.getApNo();
            List<PaymentReviewCase> dataList = reviewService.selectReviewDataFromBaappbase(apNo);

            if (dataList.size() == 1) {
                PaymentReviewCase caseObj = dataList.get(0);
                apNo = caseObj.getApNo();

                // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
                caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(apNo, caseObj.getIssuYm()));
                caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(apNo, caseObj.getIssuYm()));

                // 取得 XX函註記
                List<PaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForOldAge(apNo);
                List<PaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForOldAge(apNo);
                List<PaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForOldAge(apNo);
                List<PaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForOldAge(apNo);
                List<PaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForOldAge(apNo);
                PaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForOldAge(apNo);

                // 取得 編審給付資料
                List<PaymentReviewExtCase> payList = reviewService.getPayData(caseObj.getApNo(), caseObj.getIssuYm());
                // 取得 事故者編審註記資料
                // List<PaymentReviewExtCase> chkList = reviewService.getPaymentReviewChkList(caseObj.getApNo(), "0000");
                List<PaymentReviewExtCase> chkList = reviewService.getOldAgePaymentReviewEvtChkList(apNo);
                // 取得 受款人編審註記資料
                List<PaymentReviewCase> benChkList = reviewService.getOldAgePaymentReviewBenChkList(apNo);
                // 取得 受款人資料
                List<PaymentReviewCase> beneficiaryDataList = reviewService.getAllIssuData(caseObj.getApNo());

                // 取得 更正原因 清單
                request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
                // 取得受款人姓名清單
                List<PaymentReviewExtCase> benNameList = reviewService.getBenNameList(apNo, "1");

                PaymentReviewForm modifyForm = new PaymentReviewForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, caseObj, ConstantKey.OLD_FIELD_PREFIX);

                PaymentReviewForm qryCondForm = new PaymentReviewForm();
                BeanUtility.copyProperties(qryCondForm, iform);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果 case 存到 Request Scope
                CaseSessionHelper.setPaymentReviewCase(caseObj, request);
                CaseSessionHelper.setPayDataList(payList, request);
                CaseSessionHelper.setPaymentReviewChkList(chkList, request);
                CaseSessionHelper.setPaymentReviewBenChkList(benChkList, request);
                CaseSessionHelper.setBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setPaymentReviewBenNameList(benNameList, request);
                FormSessionHelper.setPaymentReviewForm(modifyForm, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk6List(detail6, request);

                log.debug("執行 審核作業 - 給付審核作業 - 個案審核 PaymentReviewAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
            }
            else {
                // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                log.debug("執行 審核作業 - 給付審核作業 - 個案審核 PaymentReviewAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentReviewAction.doSingleReview() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FAIL);
        }
    }

    /**
     * 審核作業 - 給付審核作業 - 受款人資料 (baco0d013q.jsp) 修改確認(單筆)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSaveBeneficiaryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 給付審核作業 - 受款人資料 - 修改確認(單筆) PaymentReviewAction.doSaveBeneficiaryData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentReviewForm iform = (PaymentReviewForm) form;

        try {
            PaymentReviewCase origCaseObj = CaseSessionHelper.getPaymentReviewCase(request);

            // 受理編號
            String apNo = origCaseObj.getApNo();
            String issuYm = origCaseObj.getIssuYm();

            List<PaymentReviewCase> origBeneficiaryDataList = CaseSessionHelper.getBeneficiaryDataList(request);
            List<BigDecimal> baappbaseIdList = new ArrayList<BigDecimal>();
            List<BigDecimal> baappbaseIdListForBadapr = new ArrayList<BigDecimal>();

            for (int j = 0; j < origBeneficiaryDataList.size(); j++) {
                baappbaseIdList.add((origBeneficiaryDataList.get(j)).getBaappbaseId());
            }

            // 取得需記錄異動LOG的欄位資料
            // 為不影響前端頁面顯示, 故須複製一份 Form
            PaymentReviewForm tempForm = new PaymentReviewForm();
            BeanUtility.copyProperties(tempForm, form);
            tempForm.setProcStat("20");
            origCaseObj.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_REVIEW_OLDAGE_FIELD_RESOURCE_PREFIX));

            // PaymentReviewCase masterData = CaseSessionHelper.getPaymentReviewCase(request);
            reviewService.updateSingleBeneficiaryData(origCaseObj, iform.getNotifyForm(), iform.getChgNote(), iform.getManchkMk(), userData);

            // 重新查詢資料
            List<PaymentReviewCase> dataList = reviewService.selectReviewDataFromBaappbase(apNo);

            if (dataList.size() == 1) {
                PaymentReviewCase caseObj = dataList.get(0);
                apNo = caseObj.getApNo();
                // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
                caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(apNo, caseObj.getIssuYm()));
                caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(apNo, caseObj.getIssuYm()));
                // 取得 XX函註記
                List<PaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForOldAge(apNo);
                List<PaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForOldAge(apNo);
                List<PaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForOldAge(apNo);
                List<PaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForOldAge(apNo);
                List<PaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForOldAge(apNo);
                PaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForOldAge(apNo);

                // 取得 編審給付資料
                List<PaymentReviewExtCase> payList = reviewService.getPayData(caseObj.getApNo(), caseObj.getIssuYm());
                // 取得 事故者編審註記資料
                // List<PaymentReviewExtCase> chkList = reviewService.getPaymentReviewChkList(caseObj.getApNo(), "0000");
                List<PaymentReviewExtCase> chkList = reviewService.getOldAgePaymentReviewEvtChkList(apNo);
                // 取得 受款人編審註記資料
                List<PaymentReviewCase> benChkList = reviewService.getOldAgePaymentReviewBenChkList(apNo);
                // 取得 受款人資料
                List<PaymentReviewCase> beneficiaryDataList = reviewService.getAllIssuData(caseObj.getApNo());

                // 取得 更正原因 清單
                request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
                // 取得受款人姓名清單
                List<PaymentReviewExtCase> benNameList = reviewService.getBenNameList(apNo, "1");

                PaymentReviewForm modifyForm = new PaymentReviewForm();
                BeanUtility.copyProperties(modifyForm, caseObj);

                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removePaymentReviewForm(request);
                FormSessionHelper.removeReviewQueryForm(request);
                CaseSessionHelper.removeAllPaymentReviewCase(request);

                // 設定更新成功訊息
                saveMessages(session, CustomMessageHelper.getReviewSuccessMessage());
                log.debug("執行 審核作業 - 給付審核作業 - 個案審核 PaymentReviewAction.doSingleReview() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
            }
            else {
                // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removePaymentReviewForm(request);
                FormSessionHelper.removeReviewQueryForm(request);
                CaseSessionHelper.removeAllPaymentReviewCase(request);

                log.debug("執行 審核作業 - 給付審核作業 - 個案審核 PaymentReviewAction.doSingleReview() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removePaymentReviewForm(request);
            FormSessionHelper.removeReviewQueryForm(request);
            CaseSessionHelper.removeAllPaymentReviewCase(request);
            // 設定更新失敗訊息
            log.error("PaymentReviewAction.doSaveBeneficiaryData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReviewFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 審核作業 - 給付審核作業 - 受款人資料 (baco0d011q.jsp) 修改確認(多筆)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSaveDataForAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 給付審核作業 - 受款人資料 - 修改確認(多筆) PaymentReviewAction.doSaveBeneficiaryDataForAll() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentReviewForm iform = (PaymentReviewForm) form;

        try {

            if (!("").equals(iform.getApNoOfConfirm())) {
                reviewService.updateAllBeneficiaryData(iform.getApNoOfConfirm(), userData, CaseSessionHelper.getPaymentReviewList(request));

                // 設定更新成功訊息
                saveMessages(session, CustomMessageHelper.getReviewSuccessMessage());

                // 重新查詢 受款人資料
                PaymentReviewForm qryCondForm = FormSessionHelper.getReviewQueryForm(request);
                List<PaymentReviewCase> dataList = new ArrayList<PaymentReviewCase>();

                dataList = reviewService.selectReviewDataFromBaappbase(qryCondForm.getApNoStr());

                if (dataList.size() <= 0) {
                    // 設定查無資料訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                    log.debug("執行 審核作業 - 給付審核作業 - 查詢頁面 PaymentReviewAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else if (dataList.size() == 1) {
                    PaymentReviewCase caseObj = new PaymentReviewCase();
                    caseObj = dataList.get(0);

                    // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
                    caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(qryCondForm.getApNoStr(), caseObj.getIssuYm()));
                    caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(qryCondForm.getApNoStr(), caseObj.getIssuYm()));

                    // 取得 XX函註記
                    List<PaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForOldAge(qryCondForm.getApNoStr());
                    List<PaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForOldAge(qryCondForm.getApNoStr());
                    List<PaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForOldAge(qryCondForm.getApNoStr());
                    List<PaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForOldAge(qryCondForm.getApNoStr());
                    List<PaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForOldAge(qryCondForm.getApNoStr());
                    PaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForOldAge(qryCondForm.getApNoStr());

                    // 取得 編審給付資料
                    List<PaymentReviewExtCase> payList = reviewService.getPayData(caseObj.getApNo(), caseObj.getIssuYm());
                    // 取得 事故者編審註記資料
                    // List<PaymentReviewExtCase> chkList = reviewService.getPaymentReviewChkList(caseObj.getApNo(), "0000");
                    List<PaymentReviewExtCase> chkList = reviewService.getOldAgePaymentReviewEvtChkList(caseObj.getApNo());
                    // 取得 受款人編審註記資料
                    List<PaymentReviewCase> benChkList = reviewService.getOldAgePaymentReviewBenChkList(caseObj.getApNo());
                    // 取得 受款人資料
                    List<PaymentReviewCase> beneficiaryDataList = reviewService.getAllIssuData(caseObj.getApNo());

                    // 取得 更正原因 清單
                    request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
                    // 取得受款人姓名清單
                    List<PaymentReviewExtCase> benNameList = reviewService.getBenNameList(caseObj.getApNo(), "1");

                    PaymentReviewForm modifyForm = new PaymentReviewForm();
                    BeanUtility.copyProperties(modifyForm, caseObj);

                    qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                    // 將 查詢結果 case 存到 Request Scope
                    CaseSessionHelper.setPaymentReviewCase(caseObj, request);
                    CaseSessionHelper.setPayDataList(payList, request);
                    CaseSessionHelper.setPaymentReviewChkList(chkList, request);
                    CaseSessionHelper.setPaymentReviewBenChkList(benChkList, request);
                    CaseSessionHelper.setBeneficiaryDataList(beneficiaryDataList, request);
                    CaseSessionHelper.setPaymentReviewBenNameList(benNameList, request);
                    FormSessionHelper.setPaymentReviewForm(modifyForm, request);
                    FormSessionHelper.setReviewQueryForm(qryCondForm, request);
                    CaseSessionHelper.setPaymentReviewLetterTypeMk1List(detail1, request);
                    CaseSessionHelper.setPaymentReviewLetterTypeMk2List(detail2, request);
                    CaseSessionHelper.setPaymentReviewLetterTypeMk3List(detail3, request);
                    CaseSessionHelper.setPaymentReviewLetterTypeMk4List(detail4, request);
                    CaseSessionHelper.setPaymentReviewLetterTypeMk5List(detail5, request);
                    CaseSessionHelper.setPaymentReviewLetterTypeMk6List(detail6, request);

                    log.debug("執行 審核作業 - 給付審核作業 - 受款人資料 - 修改確認 PaymentReviewAction.doSaveBeneficiaryDataForAll() 完成 ... ");
                    return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
                }
                else {
                    qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                    // 將 查詢結果清單 存到 Request Scope
                    CaseSessionHelper.setPaymentReviewCaseForTitle(dataList.get(0), request);// title預設抓第一筆資料
                    CaseSessionHelper.setPaymentReviewList(dataList, request);
                    FormSessionHelper.setReviewQueryForm(qryCondForm, request);

                    log.debug("執行 審核作業 - 給付審核作業 - 受款人資料 - 修改確認 PaymentReviewAction.doSaveBeneficiaryDataForAll() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
                }
            }
            else {// 無資料可更新
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removePaymentReviewForm(request);
                FormSessionHelper.removeReviewQueryForm(request);
                CaseSessionHelper.removeAllPaymentReviewCase(request);

                ActionMessages msgs = new ActionMessages();
                msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.review.noDataToUpdate"));
                saveMessages(session, msgs);

                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("PaymentReviewAction.doSaveDataForAll() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReviewFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 審核作業 - 給付審核作業 - 返回清單
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 給付審核作業 - 返回清單 PaymentReviewAction.doBackList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentReviewForm iform = (PaymentReviewForm) form;

        try {
            List<PaymentReviewCase> dataList = new ArrayList<PaymentReviewCase>();
            PaymentReviewForm qryCondForm = FormSessionHelper.getReviewQueryForm(request);
            String apNo = qryCondForm.getApNoStr();// 畫面輸入之受理編號

            dataList = reviewService.selectReviewDataFromBaappbase(apNo);

            if (dataList.size() <= 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 審核作業 - 給付審核作業 - 查詢頁面 PaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            else if (dataList.size() == 1) {
                PaymentReviewCase caseObj = new PaymentReviewCase();
                caseObj = dataList.get(0);

                // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
                caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(apNo, caseObj.getIssuYm()));
                caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(apNo, caseObj.getIssuYm()));

                // 取得 XX函註記
                List<PaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForOldAge(apNo);
                List<PaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForOldAge(apNo);
                List<PaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForOldAge(apNo);
                List<PaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForOldAge(apNo);
                List<PaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForOldAge(apNo);
                PaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForOldAge(apNo);

                // 取得 編審給付資料
                List<PaymentReviewExtCase> payList = reviewService.getPayData(caseObj.getApNo(), caseObj.getIssuYm());
                // 取得 事故者編審註記資料
                List<PaymentReviewExtCase> chkList = reviewService.getOldAgePaymentReviewEvtChkList(caseObj.getApNo());
                // 取得 受款人編審註記資料
                List<PaymentReviewCase> benChkList = reviewService.getOldAgePaymentReviewBenChkList(caseObj.getApNo());
                // 取得 受款人資料
                List<PaymentReviewCase> beneficiaryDataList = reviewService.getAllIssuData(caseObj.getApNo());

                // 取得 更正原因 清單
                request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
                // 取得受款人姓名清單
                List<PaymentReviewExtCase> benNameList = reviewService.getBenNameList(apNo, "1");

                PaymentReviewForm modifyForm = new PaymentReviewForm();
                BeanUtility.copyProperties(modifyForm, caseObj);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果 case 存到 Request Scope
                CaseSessionHelper.setPaymentReviewCase(caseObj, request);
                CaseSessionHelper.setPayDataList(payList, request);
                CaseSessionHelper.setPaymentReviewChkList(chkList, request);
                CaseSessionHelper.setPaymentReviewBenChkList(benChkList, request);
                CaseSessionHelper.setBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setPaymentReviewBenNameList(benNameList, request);
                FormSessionHelper.setPaymentReviewForm(modifyForm, request);
                FormSessionHelper.setReviewQueryForm(qryCondForm, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setPaymentReviewLetterTypeMk6List(detail6, request);

                log.debug("執行 審核作業 - 給付審核作業 - 查詢頁面 PaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
            }
            else {
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentReviewCaseForTitle(dataList.get(0), request);// title預設抓第一筆資料
                CaseSessionHelper.setPaymentReviewList(dataList, request);
                FormSessionHelper.setReviewQueryForm(qryCondForm, request);

                log.debug("執行 審核作業 - 給付審核作業 - 查詢頁面 PaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentReviewAction.doBackList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 審核作業 - 給付審核作業 - 返回
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 給付審核作業 - 返回 PaymentReviewAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removePaymentReviewForm(request);
        FormSessionHelper.removeReviewQueryForm(request);
        CaseSessionHelper.removeAllPaymentReviewCase(request);

        log.debug("執行 審核作業 - 給付審核作業 - 返回 PaymentReviewAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 檢視受理審核清單
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReviewRpt(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付審核作業 - 檢視受理審核清單 PaymentReviewAction.doReviewRpt() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentReviewForm iform = (PaymentReviewForm) form;
        try {
            PaymentReviewCase detailCase = CaseSessionHelper.getPaymentReviewCase(request);
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
            log.error("PaymentReviewAction.doReviewRpt() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
        }
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
}
