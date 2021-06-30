package tw.gov.bli.ba.executive.actions;

import java.math.BigDecimal;
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

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportCloseCase;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportDataCase;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportCloseForm;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportCloseListForm;
import tw.gov.bli.ba.executive.helper.CaseSessionHelper;
import tw.gov.bli.ba.executive.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewCase;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewExtCase;
import tw.gov.bli.ba.review.cases.PaymentReviewCase;
import tw.gov.bli.ba.review.cases.PaymentReviewExtCase;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewCase;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewExtCase;
import tw.gov.bli.ba.review.forms.DisabledPaymentReviewForm;
import tw.gov.bli.ba.review.forms.PaymentReviewForm;
import tw.gov.bli.ba.review.forms.SurvivorPaymentReviewForm;
import tw.gov.bli.ba.services.ExecutiveService;
import tw.gov.bli.ba.services.ReviewService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class ExecutiveSupportCloseListAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ExecutiveSupportCloseListAction.class);

    private ExecutiveService executiveService;
    private ReviewService reviewService;
    private SelectOptionService selectOptionService;

    // 審核作業 - 給付審核作業 - 審核清單明細資料 頁面
    private static final String FORWARD_SINGLE_REVIEW = "singleReview";
    private static final String FORWARD_SINGLE_REVIEW_FAIL = "singleReviewFail";
    private static final String FORWARD_SINGLE_REVIEW_DETAIL_FOR_OLDAGE = "singleReviewDetailForOldAge";
    private static final String FORWARD_SINGLE_REVIEW_DETAIL_FOR_DISABLED = "singleReviewDetailForDisabled";
    private static final String FORWARD_SINGLE_REVIEW_DETAIL_FOR_SURVIVOR = "singleReviewDetailForSurvivor";
    private static final String FORWARD_SINGLE_REVIEW_DETAIL_FAIL_FOR_OLDAGE = "singleReviewDetailFailForOldAge";
    private static final String FORWARD_SINGLE_REVIEW_DETAIL_FAIL_FOR_DISABLED = "singleReviewDetailFailForDisabled";
    private static final String FORWARD_SINGLE_REVIEW_DETAIL_FAIL_FOR_SURVIVOR = "singleReviewDetailFailForSurvivor";

    /**
     * 行政支援作業 - 行政支援記錄銷案 - 清單頁面 (basu0d0120d.jsp) <br>
     * 按下「確定」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doClose(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 清單頁面 ExecutiveSupportCloseListAction.doClose() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ExecutiveSupportCloseListForm listForm = (ExecutiveSupportCloseListForm) form;

        try {
            ExecutiveSupportCloseForm queryForm = FormSessionHelper.getExecutiveSupportCloseQueryForm(request);
            List<ExecutiveSupportDataCase> caseList = CaseSessionHelper.getExecutiveSupportCloseListCase(request);

            // 未更新之主檔資料
            ExecutiveSupportCloseCase oldCaseData = CaseSessionHelper.getExecutiveSupportCloseCase(request);

            // 根據頁面的CHECKBOX 勾選的清單作銷案
            executiveService.updateMaadmrecCloseDate(listForm.getCheckBox(), caseList, userData);
            // 清除頁面heckbox資料
            listForm.reset(mapping, request);

            // 重新查詢清單資料
            // List<Maadmrec> dataList = executiveService.getExecutiveSupportCloseListBy(queryForm.getApNo(), DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()));
            // CaseSessionHelper.setExecutiveSupportCloseListCase(dataList, request);

            // 查詢主檔是否有符合資料oeb xue ~~

            ExecutiveSupportCloseCase caseData = executiveService.getExecutiveSupportCloseBy(oldCaseData.getApNo(), DateUtility.changeChineseYearMonthType(oldCaseData.getIssuYm()));

            if (caseData == null) {
                CaseSessionHelper.removeExecutiveSupportCloseListCase(request);
                CaseSessionHelper.removeExecutiveSupportCloseCase(request);
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            // 查詢Maadmrec資料
            caseList = executiveService.getExecutiveSupportCloseListBy(oldCaseData.getApNo(), DateUtility.changeChineseYearMonthType(oldCaseData.getIssuYm()));

            // 行政支援記錄銷案查詢結果列表中的 CheckBox 及【確認】鍵狀態

            Maadmrec maadmrec = null;
            Integer q1 = executiveService.getUpdateLetterTypeOptionList(oldCaseData.getApNo(), DateUtility.changeChineseYearMonthType(oldCaseData.getIssuYm()));
            Integer q2 = executiveService.getButtonStatus(oldCaseData.getApNo());
            Integer q3 = 0;
            for (ExecutiveSupportDataCase caseListData : caseList) {
                if ((!"21".equals(caseListData.getLetterType()) && !"22".equals(caseListData.getLetterType()) && !"23".equals(caseListData.getLetterType()) && !"24".equals(caseListData.getLetterType()) && !"30".equals(caseListData.getLetterType())
                                && !"31".equals(caseListData.getLetterType()) && !"40".equals(caseListData.getLetterType()) && !"Y".equals(caseListData.getPayMk()))
                                || caseListData.getClosDate() == null) {
                    q3 += 1;

                }
            }

            if (caseList.size() != 0) {

                if (q2 > 0) {
                    session.setAttribute("buttonStatus", "true");
                }
                else if (q2 <= 0) {
                    session.setAttribute("buttonStatus", "false");
                    session.setAttribute("q1", q1);
                    if (q3 > 0) {
                        session.setAttribute("buttonStatusTemp", "false");
                    }
                    else {
                        session.setAttribute("buttonStatusTemp", "true");
                    }
                }
                else {
                    session.setAttribute("buttonStatus", "false");
                }
            }

            CaseSessionHelper.setExecutiveSupportCloseCase(caseData, request);
            CaseSessionHelper.setExecutiveSupportCloseListCase(caseList, request);
            FormSessionHelper.setExecutiveSupportCloseQueryForm(queryForm, request);

            saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 清單頁面 ExecutiveSupportCloseListAction.doClose() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);

        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("ExecutiveSupportCloseListAction.doClose() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 行政支援作業 - 行政支援記錄銷案 - 清單頁面 (basu0d0120d.jsp) 按下「返回」的動作 For 老年年金給付受理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackForOldAgePaymentReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 清單頁面 - 返回 ExecutiveSupportCloseListAction.doBackForOldAgePaymentReview() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ExecutiveSupportCloseListForm queryForm = (ExecutiveSupportCloseListForm) form;
        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            CaseSessionHelper.removeExecutiveSupportCloseListCase(request);
            FormSessionHelper.removeExecutiveSupportCloseQueryForm(request);
            FormSessionHelper.removeExecutiveSupportCloseForm(request);

            PaymentReviewCase caseObj = tw.gov.bli.ba.review.helper.CaseSessionHelper.getPaymentReviewCase(request);
            String apNo = caseObj.getApNo();

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

            // 將 查詢結果 case 存到 Request Scope
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setPaymentReviewCase(caseObj, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setPayDataList(payList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setPaymentReviewChkList(chkList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setPaymentReviewBenChkList(benChkList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setBeneficiaryDataList(beneficiaryDataList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setPaymentReviewBenNameList(benNameList, request);
            tw.gov.bli.ba.review.helper.FormSessionHelper.setPaymentReviewForm(modifyForm, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setPaymentReviewLetterTypeMk1List(detail1, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setPaymentReviewLetterTypeMk2List(detail2, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setPaymentReviewLetterTypeMk3List(detail3, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setPaymentReviewLetterTypeMk4List(detail4, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setPaymentReviewLetterTypeMk5List(detail5, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setPaymentReviewLetterTypeMk6List(detail6, request);

            log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 清單頁面 - 返回 ExecutiveSupportCloseListAction.doBackForOldAgePaymentReview() 完成 ... ");
            return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FOR_OLDAGE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportCloseListAction.doBackForOldAgePaymentReview() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FAIL_FOR_OLDAGE);
        }
    }

    /**
     * 行政支援作業 - 行政支援記錄銷案 - 清單頁面 (basu0d0120d.jsp) 按下「返回」的動作 For 失能年金給付受理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackForDisabledPaymentReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 清單頁面 - 返回 ExecutiveSupportCloseListAction.doBackForDisabledPaymentReview() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ExecutiveSupportCloseListForm queryForm = (ExecutiveSupportCloseListForm) form;
        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            CaseSessionHelper.removeExecutiveSupportCloseListCase(request);
            FormSessionHelper.removeExecutiveSupportCloseQueryForm(request);
            FormSessionHelper.removeExecutiveSupportCloseForm(request);

            DisabledPaymentReviewCase caseObj = tw.gov.bli.ba.review.helper.CaseSessionHelper.getDisabledPaymentReviewCase(request);
            String apNo = caseObj.getApNo();

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

            // 將 查詢結果 case 存到 Request Scope
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewCase(caseObj, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewOccAccData(occAccData, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewDisabledData(disabledData, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPayDataList(payList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewEvtChkList(evtChkList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewBenChkList(benChkList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewMatchChkList(matchChkList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledBeneficiaryDataList(beneficiaryDataList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewBenNameList(benNameList, request);
            tw.gov.bli.ba.review.helper.FormSessionHelper.setDisabledPaymentReviewForm(modifyForm, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk1List(detail1, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk2List(detail2, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk3List(detail3, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk4List(detail4, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk5List(detail5, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk6List(detail6, request);
            log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 清單頁面 - 返回 ExecutiveSupportCloseListAction.doBackForDisabledPaymentReview() 完成 ... ");
            return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FOR_DISABLED);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportCloseListAction.doBackForDisabledPaymentReview() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FAIL_FOR_DISABLED);
        }
    }

    /**
     * 行政支援作業 - 行政支援記錄銷案 - 清單頁面 (basu0d0120d.jsp) 按下「返回」的動作 For 失能年金給付受理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackForSurvivorPaymentReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 清單頁面 - 返回 ExecutiveSupportCloseListAction.doBackForSurvivorPaymentReview() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ExecutiveSupportCloseListForm queryForm = (ExecutiveSupportCloseListForm) form;
        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            CaseSessionHelper.removeExecutiveSupportCloseListCase(request);
            FormSessionHelper.removeExecutiveSupportCloseQueryForm(request);
            FormSessionHelper.removeExecutiveSupportCloseForm(request);

            SurvivorPaymentReviewCase caseObj = tw.gov.bli.ba.review.helper.CaseSessionHelper.getSurvivorPaymentReviewCase(request);
            String apNo = caseObj.getApNo();

            // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
            caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(apNo, caseObj.getIssuYm()));
            caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(apNo, caseObj.getIssuYm()));

            // 取得 XX函註記
            List<SurvivorPaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForSurvivor(apNo);
            List<SurvivorPaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForSurvivor(apNo);
            List<SurvivorPaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForSurvivor(apNo);
            List<SurvivorPaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForSurvivor(apNo);
            List<SurvivorPaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForSurvivor(apNo);
            SurvivorPaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForSurvivor(apNo);

            // 取得 失能相關資料
            SurvivorPaymentReviewCase disabledData = reviewService.selectDisabledDataForSurvivor(apNo);
            // 取得 編審給付資料
            List<SurvivorPaymentReviewExtCase> payList = reviewService.getPayDataForSurvivor(caseObj.getApNo(), caseObj.getIssuYm());
         // 取得 事故者編審註記資料
            List<SurvivorPaymentReviewExtCase> evtChkList = reviewService.getSurvivorPaymentReviewEvtChkList(caseObj.getApNo());
            // 取得 眷屬編審註記資料
            List<SurvivorPaymentReviewCase> benChkList = reviewService.getSurvivorPaymentReviewBenChkList(caseObj.getApNo());
            // 取得 符合註記資料
            List<SurvivorPaymentReviewCase> matchChkList = reviewService.getSurvivorPaymentReviewOtherChkList(caseObj.getApNo());
            // 取得 事故者/受款人 資料(含事故者核定資料)
            List<SurvivorPaymentReviewCase> beneficiaryDataList = reviewService.getbeneficiaryDataListForSurvivor(caseObj.getApNo(), caseObj.getQ1(), caseObj.getQ2());
            // 取得 所有受款人核定資料(不含事故者)
            List<SurvivorPaymentReviewExtCase> benIssuDataList = reviewService.getIssuDataListForSurvivor(beneficiaryDataList, caseObj.getApNo(), "0000", "<>");
            // 將受款人核定資料(不含事故者) 轉成頁面顯示用之List 與 session暫存用之map
            List<Object> transDataList = reviewService.transInitBenIssuDataForSurvivor(benIssuDataList);
            // 取得 更正原因 清單
            request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
            // 取得受款人姓名清單
            List<SurvivorPaymentReviewExtCase> benNameList = reviewService.getBenNameListForSurvivor(apNo, "1");
            // 取得 給付年月下拉選單
            request.getSession().setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_PAYYM_OPTION_LIST, reviewService.getPayYmOptionListForSurvivor(caseObj.getApNo(), caseObj.getIssuYm()));
            // 取得 受款人下拉選單
            request.getSession().setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_SEQNO_OPTION_LIST, reviewService.getSeqNoOptionListForSurvivor(caseObj.getApNo()));

            SurvivorPaymentReviewForm modifyForm = new SurvivorPaymentReviewForm();
            BeanUtility.copyProperties(modifyForm, caseObj);

            // 將 查詢結果 case 存到 Request Scope
            tw.gov.bli.ba.review.helper.FormSessionHelper.setSurvivorPaymentReviewForm(modifyForm, request);

            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewCase(caseObj, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewDisabledData(disabledData, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPayDataList(payList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewEvtChkList(evtChkList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewBenChkList(benChkList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewMatchChkList(matchChkList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorBeneficiaryDataList(beneficiaryDataList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorOrigBenIssuDataList(benIssuDataList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorBenIssuDataList((List<SurvivorPaymentReviewCase>) transDataList.get(0), request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorBenIssuDataMap((Map<BigDecimal, SurvivorPaymentReviewExtCase>) transDataList.get(1), request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewBenNameList(benNameList, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk1List(detail1, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk2List(detail2, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk3List(detail3, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk4List(detail4, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk5List(detail5, request);
            tw.gov.bli.ba.review.helper.CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk6List(detail6, request);
            log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 清單頁面 - 返回 ExecutiveSupportCloseListAction.doBackForSurvivorPaymentReview() 完成 ... ");
            return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FOR_SURVIVOR);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportCloseListAction.doBackForSurvivorPaymentReview() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FAIL_FOR_SURVIVOR);
        }
    }

    public ActionForward doBackClean(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 查詢頁面 - 返回 ExecutiveSupportCloseListAction.doBack() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ExecutiveSupportCloseListForm queryForm = (ExecutiveSupportCloseListForm) form;

        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            CaseSessionHelper.removeExecutiveSupportCloseListCase(request);
            FormSessionHelper.removeExecutiveSupportCloseQueryForm(request);
            FormSessionHelper.removeExecutiveSupportCloseForm(request);

            log.debug("執行 行政支援作業 - 行政支援記錄銷案 - 查詢頁面 - 返回 ExecutiveSupportCloseListAction.doBackClean() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportCloseListAction.doBackClean() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        }
    }

    public void setExecutiveService(ExecutiveService executiveService) {
        this.executiveService = executiveService;
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
}
