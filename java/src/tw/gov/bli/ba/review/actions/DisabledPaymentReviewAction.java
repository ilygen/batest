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
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewCase;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewExtCase;
import tw.gov.bli.ba.review.cases.PaymentReviewCase;
import tw.gov.bli.ba.review.cases.PaymentReviewExtCase;
import tw.gov.bli.ba.review.forms.DisabledPaymentReviewForm;
import tw.gov.bli.ba.review.forms.PaymentReviewForm;
import tw.gov.bli.ba.review.helper.CaseSessionHelper;
import tw.gov.bli.ba.review.helper.FormSessionHelper;
import tw.gov.bli.ba.rpt.actions.OldAgeReviewRpt01Action;
import tw.gov.bli.ba.rpt.forms.OldAgeReviewRpt01Form;
import tw.gov.bli.ba.services.ReviewService;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 審核作業 - 失能年金給付審核作業 (BACO0D110A)
 *
 * @author Rickychi
 */
public class DisabledPaymentReviewAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DisabledPaymentReviewAction.class);

    private ReviewService reviewService;
    private SelectOptionService selectOptionService;

    // 審核作業 - 失能年金給付審核作業 - 審核清單明細資料 頁面
    private static final String FORWARD_SINGLE_REVIEW_DETAIL = "singleReviewDetail";
    private static final String FORWARD_SINGLE_REVIEW_DETAIL_FAIL = "singleReviewDetailFail";

    /**
     * 審核作業 - 失能年金給付審核作業 - 查詢頁面 (baco0d010a.jsp)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 失能年金給付審核作業 - 查詢頁面 DisabledPaymentReviewAction.doQuery() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPaymentReviewForm iform = (DisabledPaymentReviewForm) form;

        try {
            List<DisabledPaymentReviewCase> dataList = new ArrayList<DisabledPaymentReviewCase>();
            String apNo = iform.getApNoStr();// 畫面輸入之受理編號

            dataList = reviewService.selectReviewDataFromBaappbaseForDisabled(apNo);

            if (dataList.size() <= 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 審核作業 - 失能年金給付審核作業 - 查詢頁面 DisabledPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            else if (dataList.size() == 1) {
                DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
                caseObj = dataList.get(0);

                // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
                caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(apNo, caseObj.getIssuYm()));
                caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(apNo, caseObj.getIssuYm()));

                // 取得 XX函註記
                List<DisabledPaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForDisabled(apNo);
                DisabledPaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForDisabled(apNo);

                // 取得 職災相關資料
                DisabledPaymentReviewCase occAccData = reviewService.getOccAccDataForDisabled(apNo);
                // 取得 失能相關資料
                DisabledPaymentReviewCase disabledData = reviewService.selectDisabledDataForDisabled(apNo);
                // 取得 核定資料
                List<DisabledPaymentReviewExtCase> payList = reviewService.getPayDataForDisabled(caseObj.getApNo(), caseObj.getIssuYm());
                BigDecimal issueAmtTotal = BigDecimal.ZERO;
                for (DisabledPaymentReviewExtCase disabledPaymentReviewExtCase : payList) {
                    issueAmtTotal = issueAmtTotal.add(disabledPaymentReviewExtCase.getIssueAmt());
                }
                caseObj.setIssueAmtTotal(issueAmtTotal);
                // 取得 事故者編審註記資料
                List<DisabledPaymentReviewExtCase> evtChkList = reviewService.getDisabledPaymentReviewEvtChkList(caseObj.getApNo());
                // 取得 眷屬編審註記資料
                List<DisabledPaymentReviewCase> benChkList = reviewService.getDisabledPaymentReviewBenChkList(caseObj.getApNo());
                // 取得 符合註記資料
                List<DisabledPaymentReviewCase> matchChkList = reviewService.getDisabledPaymentReviewOtherChkList(caseObj.getApNo());
                // 取得 受款人資料
                List<DisabledPaymentReviewCase> beneficiaryDataList = reviewService.getAllIssuDataForDisabled(caseObj.getApNo());
                // 取得 更正原因 清單
                request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
                // 取得受款人姓名清單
                List<DisabledPaymentReviewExtCase> benNameList = reviewService.getBenNameListForDisabled(apNo, "1");

                DisabledPaymentReviewForm modifyForm = new DisabledPaymentReviewForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, caseObj, ConstantKey.OLD_FIELD_PREFIX);

                DisabledPaymentReviewForm qryCondForm = new DisabledPaymentReviewForm();
                BeanUtility.copyProperties(qryCondForm, iform);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果 case 存到 Request Scope
                CaseSessionHelper.setDisabledPaymentReviewCase(caseObj, request);
                CaseSessionHelper.setDisabledPaymentReviewOccAccData(occAccData, request);
                CaseSessionHelper.setDisabledPaymentReviewDisabledData(disabledData, request);
                CaseSessionHelper.setDisabledPayDataList(payList, request);
                CaseSessionHelper.setDisabledPaymentReviewEvtChkList(evtChkList, request);
                CaseSessionHelper.setDisabledPaymentReviewBenChkList(benChkList, request);
                CaseSessionHelper.setDisabledPaymentReviewMatchChkList(matchChkList, request);
                CaseSessionHelper.setDisabledBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setDisabledPaymentReviewBenNameList(benNameList, request);
                FormSessionHelper.setDisabledPaymentReviewForm(modifyForm, request);
                FormSessionHelper.setDisabledPaymentReviewQueryForm(qryCondForm, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk6List(detail6, request);

                log.debug("執行 審核作業 - 失能年金給付審核作業 - 查詢頁面 DisabledPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
            }
            else {
                DisabledPaymentReviewForm qryCondForm = new DisabledPaymentReviewForm();
                BeanUtility.copyPropertiesForUpdate(qryCondForm, iform, ConstantKey.OLD_FIELD_PREFIX);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setDisabledPaymentReviewCaseForTitle(dataList.get(0), request);// title預設抓第一筆資料
                CaseSessionHelper.setDisabledPaymentReviewList(dataList, request);
                FormSessionHelper.setDisabledPaymentReviewQueryForm(qryCondForm, request);

                log.debug("執行 審核作業 - 失能年金給付審核作業 - 查詢頁面 DisabledPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentReviewAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 個案審核 (baco0d011a.jsp)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSingleReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 失能年金給付審核作業 - 個案審核 DisabledPaymentReviewAction.doSingleReview() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPaymentReviewForm iform = (DisabledPaymentReviewForm) form;

        try {
            String apNo = iform.getApNo();
            List<DisabledPaymentReviewCase> dataList = reviewService.selectReviewDataFromBaappbaseForDisabled(apNo);

            if (dataList.size() == 1) {
                DisabledPaymentReviewCase caseObj = dataList.get(0);
                apNo = caseObj.getApNo();

                // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
                caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(apNo, caseObj.getIssuYm()));
                caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(apNo, caseObj.getIssuYm()));

                // 取得 XX函註記
                List<DisabledPaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForDisabled(apNo);
                DisabledPaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForDisabled(apNo);

                // 取得 職災相關資料
                DisabledPaymentReviewCase occAccData = reviewService.getOccAccDataForDisabled(apNo);
                // 取得 失能相關資料
                DisabledPaymentReviewCase disabledData = reviewService.selectDisabledDataForDisabled(apNo);
                // 取得 編審給付資料
                List<DisabledPaymentReviewExtCase> payList = reviewService.getPayDataForDisabled(caseObj.getApNo(), caseObj.getIssuYm());
                // 取得 事故者編審註記資料
                List<DisabledPaymentReviewExtCase> evtChkList = reviewService.getDisabledPaymentReviewEvtChkList(caseObj.getApNo());
                // 取得 眷屬編審註記資料
                List<DisabledPaymentReviewCase> benChkList = reviewService.getDisabledPaymentReviewOtherChkList(caseObj.getApNo());
                // 取得 符合註記資料
                List<DisabledPaymentReviewCase> matchChkList = reviewService.getDisabledPaymentReviewOtherChkList(caseObj.getApNo());
                // 取得 受款人資料
                List<DisabledPaymentReviewCase> beneficiaryDataList = reviewService.getAllIssuDataForDisabled(caseObj.getApNo());
                // 取得 更正原因 清單
                request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
                // 取得受款人姓名清單
                List<DisabledPaymentReviewExtCase> benNameList = reviewService.getBenNameListForDisabled(apNo, "1");

                DisabledPaymentReviewForm modifyForm = new DisabledPaymentReviewForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, caseObj, ConstantKey.OLD_FIELD_PREFIX);

                DisabledPaymentReviewForm qryCondForm = new DisabledPaymentReviewForm();
                BeanUtility.copyProperties(qryCondForm, iform);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果 case 存到 Request Scope
                CaseSessionHelper.setDisabledPaymentReviewCase(caseObj, request);
                CaseSessionHelper.setDisabledPaymentReviewOccAccData(occAccData, request);
                CaseSessionHelper.setDisabledPaymentReviewDisabledData(disabledData, request);
                CaseSessionHelper.setDisabledPayDataList(payList, request);
                CaseSessionHelper.setDisabledPaymentReviewEvtChkList(evtChkList, request);
                CaseSessionHelper.setDisabledPaymentReviewBenChkList(benChkList, request);
                CaseSessionHelper.setDisabledPaymentReviewMatchChkList(matchChkList, request);
                CaseSessionHelper.setDisabledBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setDisabledPaymentReviewBenNameList(benNameList, request);
                FormSessionHelper.setDisabledPaymentReviewForm(modifyForm, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk6List(detail6, request);

                log.debug("執行 審核作業 - 失能年金給付審核作業 - 個案審核 DisabledPaymentReviewAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
            }
            else {
                // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                log.debug("執行 審核作業 - 失能年金給付審核作業 - 個案審核 DisabledPaymentReviewAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentReviewAction.doSingleReview() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FAIL);
        }
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 受款人資料 (baco0d013q.jsp) 修改確認(單筆)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSaveBeneficiaryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 失能年金給付審核作業 - 受款人資料 - 修改確認(單筆) DisabledPaymentReviewAction.doSaveBeneficiaryData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPaymentReviewForm iform = (DisabledPaymentReviewForm) form;

        try {
            DisabledPaymentReviewCase origCaseObj = CaseSessionHelper.getDisabledPaymentReviewCase(request);

            // 受理編號
            String apNo = origCaseObj.getApNo();
            String issuYm = origCaseObj.getIssuYm();

            List<DisabledPaymentReviewCase> origBeneficiaryDataList = CaseSessionHelper.getDisabledBeneficiaryDataList(request);
            List<BigDecimal> baappbaseIdList = new ArrayList<BigDecimal>();
            List<BigDecimal> baappbaseIdListForBadapr = new ArrayList<BigDecimal>();

            for (int j = 0; j < origBeneficiaryDataList.size(); j++) {
                baappbaseIdList.add((origBeneficiaryDataList.get(j)).getBaappbaseId());
            }

            // 取得需記錄異動LOG的欄位資料
            // 為不影響前端頁面顯示, 故須複製一份 Form
            DisabledPaymentReviewForm tempForm = new DisabledPaymentReviewForm();
            BeanUtility.copyProperties(tempForm, form);
            tempForm.setProcStat("20");
            origCaseObj.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_REVIEW_OLDAGE_FIELD_RESOURCE_PREFIX));

            reviewService.updateSingleBeneficiaryDataForDisabled(origCaseObj, iform.getNotifyForm(), iform.getChgNote(), iform.getManchkMk(), userData);

            // 重新查詢資料
            List<DisabledPaymentReviewCase> dataList = reviewService.selectReviewDataFromBaappbaseForDisabled(apNo);

            if (dataList.size() == 1) {
                DisabledPaymentReviewCase caseObj = dataList.get(0);
                apNo = caseObj.getApNo();
                // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
                caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(apNo, caseObj.getIssuYm()));
                caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(apNo, caseObj.getIssuYm()));
                // 取得 XX函註記
                List<DisabledPaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForDisabled(apNo);
                DisabledPaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForDisabled(apNo);

                // 取得 職災相關資料
                DisabledPaymentReviewCase occAccData = reviewService.getOccAccDataForDisabled(apNo);
                // 取得 失能相關資料
                DisabledPaymentReviewCase disabledData = reviewService.selectDisabledDataForDisabled(apNo);
                // 取得 編審給付資料
                List<DisabledPaymentReviewExtCase> payList = reviewService.getPayDataForDisabled(caseObj.getApNo(), caseObj.getIssuYm());
                // 取得 事故者編審註記資料
                List<DisabledPaymentReviewExtCase> evtChkList = reviewService.getDisabledPaymentReviewEvtChkList(caseObj.getApNo());
                // 取得 眷屬編審註記資料
                List<DisabledPaymentReviewCase> benChkList = reviewService.getDisabledPaymentReviewOtherChkList(caseObj.getApNo());
                // 取得 符合註記資料
                List<DisabledPaymentReviewCase> matchChkList = reviewService.getDisabledPaymentReviewOtherChkList(caseObj.getApNo());
                // 取得 受款人資料
                List<DisabledPaymentReviewCase> beneficiaryDataList = reviewService.getAllIssuDataForDisabled(caseObj.getApNo());
                // 取得 更正原因 清單
                request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
                // 取得受款人姓名清單
                List<DisabledPaymentReviewExtCase> benNameList = reviewService.getBenNameListForDisabled(apNo, "1");

                DisabledPaymentReviewForm modifyForm = new DisabledPaymentReviewForm();
                BeanUtility.copyProperties(modifyForm, caseObj);

                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeDisabledPaymentReviewForm(request);
                FormSessionHelper.removeDisabledPaymentReviewQueryForm(request);
                CaseSessionHelper.removeAllDisabledPaymentReviewCase(request);

                // 設定更新成功訊息
                saveMessages(session, CustomMessageHelper.getReviewSuccessMessage());
                log.debug("執行 審核作業 - 失能年金給付審核作業 - 個案審核 DisabledPaymentReviewAction.doSingleReview() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
            }
            else {
                // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeDisabledPaymentReviewForm(request);
                FormSessionHelper.removeDisabledPaymentReviewQueryForm(request);
                CaseSessionHelper.removeAllDisabledPaymentReviewCase(request);

                log.debug("執行 審核作業 - 失能年金給付審核作業 - 個案審核 DisabledPaymentReviewAction.doSingleReview() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeDisabledPaymentReviewForm(request);
            FormSessionHelper.removeDisabledPaymentReviewQueryForm(request);
            CaseSessionHelper.removeAllDisabledPaymentReviewCase(request);
            // 設定更新失敗訊息
            log.error("DisabledPaymentReviewAction.doSaveBeneficiaryData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReviewFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 受款人資料 (baco0d011q.jsp) 修改確認(多筆)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSaveDataForAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 失能年金給付審核作業 - 受款人資料 - 修改確認(多筆) DisabledPaymentReviewAction.doSaveBeneficiaryDataForAll() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPaymentReviewForm iform = (DisabledPaymentReviewForm) form;

        try {

            if (!("").equals(iform.getApNoOfConfirm())) {
                reviewService.updateAllBeneficiaryDataForDisabled(iform.getApNoOfConfirm(), userData, CaseSessionHelper.getPaymentReviewList(request));

                // 設定更新成功訊息
                saveMessages(session, CustomMessageHelper.getReviewSuccessMessage());

                // 重新查詢 受款人資料
                DisabledPaymentReviewForm qryCondForm = FormSessionHelper.getDisabledPaymentReviewQueryForm(request);
                List<DisabledPaymentReviewCase> dataList = new ArrayList<DisabledPaymentReviewCase>();

                // 用 受理編號 查詢
                dataList = reviewService.selectReviewDataFromBaappbaseForDisabled(qryCondForm.getApNoStr());

                if (dataList.size() <= 0) {
                    // 設定查無資料訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                    log.debug("執行 審核作業 - 失能年金給付審核作業 - 查詢頁面 DisabledPaymentReviewAction.doQuery() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                else if (dataList.size() == 1) {
                    DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
                    caseObj = dataList.get(0);

                    // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
                    caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(qryCondForm.getApNoStr(), caseObj.getIssuYm()));
                    caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(qryCondForm.getApNoStr(), caseObj.getIssuYm()));

                    // 取得 XX函註記
                    List<DisabledPaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForDisabled(qryCondForm.getApNoStr());
                    List<DisabledPaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForDisabled(qryCondForm.getApNoStr());
                    List<DisabledPaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForDisabled(qryCondForm.getApNoStr());
                    List<DisabledPaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForDisabled(qryCondForm.getApNoStr());
                    List<DisabledPaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForDisabled(qryCondForm.getApNoStr());
                    DisabledPaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForDisabled(qryCondForm.getApNoStr());

                    // 取得 職災相關資料
                    DisabledPaymentReviewCase occAccData = reviewService.getOccAccDataForDisabled(qryCondForm.getApNoStr());
                    // 取得 失能相關資料
                    DisabledPaymentReviewCase disabledData = reviewService.selectDisabledDataForDisabled(qryCondForm.getApNoStr());
                    // 取得 編審給付資料
                    List<DisabledPaymentReviewExtCase> payList = reviewService.getPayDataForDisabled(caseObj.getApNo(), caseObj.getIssuYm());
                    // 取得 事故者編審註記資料
                    List<DisabledPaymentReviewExtCase> evtChkList = reviewService.getDisabledPaymentReviewEvtChkList(caseObj.getApNo());
                    // 取得 眷屬編審註記資料
                    List<DisabledPaymentReviewCase> benChkList = reviewService.getDisabledPaymentReviewOtherChkList(caseObj.getApNo());
                    // 取得 符合註記資料
                    List<DisabledPaymentReviewCase> matchChkList = reviewService.getDisabledPaymentReviewOtherChkList(caseObj.getApNo());
                    // 取得 受款人資料
                    List<DisabledPaymentReviewCase> beneficiaryDataList = reviewService.getAllIssuDataForDisabled(caseObj.getApNo());
                    // 取得 更正原因 清單
                    request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
                    // 取得受款人姓名清單
                    List<DisabledPaymentReviewExtCase> benNameList = reviewService.getBenNameListForDisabled(caseObj.getApNo(), "1");

                    DisabledPaymentReviewForm modifyForm = new DisabledPaymentReviewForm();
                    BeanUtility.copyProperties(modifyForm, caseObj);

                    qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                    // 將 查詢結果 case 存到 Request Scope
                    CaseSessionHelper.setDisabledPaymentReviewCase(caseObj, request);
                    CaseSessionHelper.setDisabledPaymentReviewOccAccData(occAccData, request);
                    CaseSessionHelper.setDisabledPaymentReviewDisabledData(disabledData, request);
                    CaseSessionHelper.setDisabledPayDataList(payList, request);
                    CaseSessionHelper.setDisabledPaymentReviewEvtChkList(evtChkList, request);
                    CaseSessionHelper.setDisabledPaymentReviewBenChkList(benChkList, request);
                    CaseSessionHelper.setDisabledPaymentReviewMatchChkList(matchChkList, request);
                    CaseSessionHelper.setDisabledBeneficiaryDataList(beneficiaryDataList, request);
                    CaseSessionHelper.setDisabledPaymentReviewBenNameList(benNameList, request);
                    FormSessionHelper.setDisabledPaymentReviewForm(modifyForm, request);
                    FormSessionHelper.setDisabledPaymentReviewQueryForm(qryCondForm, request);
                    CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk1List(detail1, request);
                    CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk2List(detail2, request);
                    CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk3List(detail3, request);
                    CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk4List(detail4, request);
                    CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk5List(detail5, request);
                    CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk6List(detail6, request);

                    log.debug("執行 審核作業 - 失能年金給付審核作業 - 受款人資料 - 修改確認 DisabledPaymentReviewAction.doSaveBeneficiaryDataForAll() 完成 ... ");
                    return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
                }
                else {
                    qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                    // 將 查詢結果清單 存到 Request Scope
                    CaseSessionHelper.setDisabledPaymentReviewCaseForTitle(dataList.get(0), request);// title預設抓第一筆資料
                    CaseSessionHelper.setDisabledPaymentReviewList(dataList, request);
                    FormSessionHelper.setDisabledPaymentReviewQueryForm(qryCondForm, request);

                    log.debug("執行 審核作業 - 失能年金給付審核作業 - 受款人資料 - 修改確認 DisabledPaymentReviewAction.doSaveBeneficiaryDataForAll() 完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
                }
            }
            else {// 無資料可更新
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeDisabledPaymentReviewForm(request);
                FormSessionHelper.removeDisabledPaymentReviewQueryForm(request);
                CaseSessionHelper.removeAllDisabledPaymentReviewCase(request);

                ActionMessages msgs = new ActionMessages();
                msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.review.noDataToUpdate"));
                saveMessages(session, msgs);

                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);

            }
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("DisabledPaymentReviewAction.doSaveDataForAll() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReviewFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 返回清單
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 失能年金給付審核作業 - 返回清單 DisabledPaymentReviewAction.doBackList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPaymentReviewForm iform = (DisabledPaymentReviewForm) form;

        try {
            List<DisabledPaymentReviewCase> dataList = new ArrayList<DisabledPaymentReviewCase>();
            DisabledPaymentReviewForm qryCondForm = FormSessionHelper.getDisabledPaymentReviewQueryForm(request);
            String apNo = qryCondForm.getApNoStr();// 畫面輸入之受理編號

            dataList = reviewService.selectReviewDataFromBaappbaseForDisabled(apNo);

            if (dataList.size() <= 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 審核作業 - 失能年金給付審核作業 - 查詢頁面 DisabledPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            else if (dataList.size() == 1) {
                DisabledPaymentReviewCase caseObj = new DisabledPaymentReviewCase();
                caseObj = dataList.get(0);

                // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
                caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(apNo, caseObj.getIssuYm()));
                caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(apNo, caseObj.getIssuYm()));

                // 取得 XX函註記
                List<DisabledPaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForDisabled(apNo);
                List<DisabledPaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForDisabled(apNo);
                DisabledPaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForDisabled(apNo);

                // 取得 職災相關資料
                DisabledPaymentReviewCase occAccData = reviewService.getOccAccDataForDisabled(apNo);
                // 取得 失能相關資料
                DisabledPaymentReviewCase disabledData = reviewService.selectDisabledDataForDisabled(apNo);
                // 取得 編審給付資料
                List<DisabledPaymentReviewExtCase> payList = reviewService.getPayDataForDisabled(caseObj.getApNo(), caseObj.getIssuYm());
                // 取得 事故者編審註記資料
                List<DisabledPaymentReviewExtCase> evtChkList = reviewService.getDisabledPaymentReviewEvtChkList(caseObj.getApNo());
                // 取得 眷屬編審註記資料
                List<DisabledPaymentReviewCase> benChkList = reviewService.getDisabledPaymentReviewOtherChkList(caseObj.getApNo());
                // 取得 符合註記資料
                List<DisabledPaymentReviewCase> matchChkList = reviewService.getDisabledPaymentReviewOtherChkList(caseObj.getApNo());
                // 取得 受款人資料
                List<DisabledPaymentReviewCase> beneficiaryDataList = reviewService.getAllIssuDataForDisabled(caseObj.getApNo());
                // 取得 更正原因 清單
                request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
                // 取得受款人姓名清單
                List<DisabledPaymentReviewExtCase> benNameList = reviewService.getBenNameListForDisabled(apNo, "1");

                DisabledPaymentReviewForm modifyForm = new DisabledPaymentReviewForm();
                BeanUtility.copyProperties(modifyForm, caseObj);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果 case 存到 Request Scope
                CaseSessionHelper.setDisabledPaymentReviewCase(caseObj, request);
                CaseSessionHelper.setDisabledPaymentReviewOccAccData(occAccData, request);
                CaseSessionHelper.setDisabledPaymentReviewDisabledData(disabledData, request);
                CaseSessionHelper.setDisabledPayDataList(payList, request);
                CaseSessionHelper.setDisabledPaymentReviewEvtChkList(evtChkList, request);
                CaseSessionHelper.setDisabledPaymentReviewBenChkList(benChkList, request);
                CaseSessionHelper.setDisabledPaymentReviewMatchChkList(matchChkList, request);
                CaseSessionHelper.setDisabledBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setDisabledPaymentReviewBenNameList(benNameList, request);
                FormSessionHelper.setDisabledPaymentReviewForm(modifyForm, request);
                FormSessionHelper.setDisabledPaymentReviewQueryForm(qryCondForm, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk6List(detail6, request);

                log.debug("執行 審核作業 - 失能年金給付審核作業 - 查詢頁面 DisabledPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
            }
            else {
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setDisabledPaymentReviewCaseForTitle(dataList.get(0), request);// title預設抓第一筆資料
                CaseSessionHelper.setDisabledPaymentReviewList(dataList, request);
                FormSessionHelper.setDisabledPaymentReviewQueryForm(qryCondForm, request);

                log.debug("執行 審核作業 - 失能年金給付審核作業 - 查詢頁面 DisabledPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledPaymentReviewAction.doBackList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 返回
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 失能年金給付審核作業 - 返回 DisabledPaymentReviewAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removeDisabledPaymentReviewForm(request);
        FormSessionHelper.removeDisabledPaymentReviewQueryForm(request);
        CaseSessionHelper.removeAllDisabledPaymentReviewCase(request);

        log.debug("執行 審核作業 - 失能年金給付審核作業 - 返回 DisabledPaymentReviewAction.doBack() 完成 ... ");
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
        log.debug("執行 審核作業 - 失能年金給付審核作業 - 檢視受理審核清單 DisabledPaymentReviewAction.doReviewRpt() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        DisabledPaymentReviewForm iform = (DisabledPaymentReviewForm) form;
        try {
            DisabledPaymentReviewCase detailCase = CaseSessionHelper.getDisabledPaymentReviewCase(request);
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
            log.error("DisabledPaymentReviewAction.doReviewRpt() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
