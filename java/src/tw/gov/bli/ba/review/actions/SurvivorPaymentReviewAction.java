package tw.gov.bli.ba.review.actions;

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
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewCase;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewCase;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewExtCase;
import tw.gov.bli.ba.review.forms.DisabledPaymentReviewForm;
import tw.gov.bli.ba.review.forms.PaymentReviewForm;
import tw.gov.bli.ba.review.forms.SurvivorPaymentReviewForm;
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
 * 審核作業 - 遺屬年金給付審核作業 (BACO0D210A)
 * 
 * @author Rickychi
 */
public class SurvivorPaymentReviewAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(SurvivorPaymentReviewAction.class);

    private ReviewService reviewService;
    private SelectOptionService selectOptionService;

    // 審核作業 - 遺屬年金給付審核作業 - 審核清單明細資料 頁面
    private static final String FORWARD_SINGLE_REVIEW_DETAIL = "singleReviewDetail";
    private static final String FORWARD_SINGLE_REVIEW_DETAIL_FAIL = "singleReviewDetailFail";

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 (baco0d210a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 SurvivorPaymentReviewAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorPaymentReviewForm iform = (SurvivorPaymentReviewForm) form;

        try {
            List<SurvivorPaymentReviewCase> dataList = new ArrayList<SurvivorPaymentReviewCase>();
            String apNo = iform.getApNoStr();// 畫面輸入之受理編號

            // if (("1").equals(iform.getQryCond()) || ("2").equals(iform.getQryCond())) {
            // 用 受理編號 查詢
            // if (("1").equals(iform.getQryCond())) {
            dataList = reviewService.selectReviewDataFromBaappbaseForSurvivor(apNo);
            // }
            // 用 列印日期 + 頁次 查詢
            // else if (("2").equals(iform.getQryCond())) {
            // dataList = reviewService.selectReviewDataFromExalistForSurvivor(DateUtility.changeDateType(iform.getRptDate()), iform.getPageNo());
            // }
            if (dataList.size() <= 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 SurvivorPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            else if (dataList.size() == 1) {
                SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
                // if (("1").equals(iform.getQryCond())) {
                caseObj = dataList.get(0);
                // }
                // else if (("2").equals(iform.getQryCond())) {
                // SurvivorPaymentReviewCase tempCase = dataList.get(0);
                // List<SurvivorPaymentReviewCase> caseList = reviewService.selectReviewDataFromBaappbaseForSurvivor(tempCase.getApNo());
                //
                // if (caseList.size() != 1) {
                // // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                // saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());
                // log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 SurvivorPaymentReviewAction.doQuery() 完成 ... ");
                // return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                // }
                // else {
                // caseObj = caseList.get(0);
                // }
                // }
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
                BeanUtility.copyPropertiesForUpdate(modifyForm, caseObj, ConstantKey.OLD_FIELD_PREFIX);

                SurvivorPaymentReviewForm qryCondForm = new SurvivorPaymentReviewForm();
                BeanUtility.copyProperties(qryCondForm, iform);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果 case 存到 Request Scope
                FormSessionHelper.setSurvivorPaymentReviewForm(modifyForm, request);
                FormSessionHelper.setSurvivorPaymentReviewQueryForm(qryCondForm, request);

                CaseSessionHelper.setSurvivorPaymentReviewCase(caseObj, request);
                CaseSessionHelper.setSurvivorPaymentReviewDisabledData(disabledData, request);
                CaseSessionHelper.setSurvivorPayDataList(payList, request);
                CaseSessionHelper.setSurvivorPaymentReviewEvtChkList(evtChkList, request);
                CaseSessionHelper.setSurvivorPaymentReviewBenChkList(benChkList, request);
                CaseSessionHelper.setSurvivorPaymentReviewMatchChkList(matchChkList, request);
                CaseSessionHelper.setSurvivorBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setSurvivorOrigBenIssuDataList(benIssuDataList, request);
                CaseSessionHelper.setSurvivorBenIssuDataList((List<SurvivorPaymentReviewCase>) transDataList.get(0), request);
                CaseSessionHelper.setSurvivorBenIssuDataMap((Map<BigDecimal, SurvivorPaymentReviewExtCase>) transDataList.get(1), request);
                CaseSessionHelper.setSurvivorPaymentReviewBenNameList(benNameList, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk6List(detail6, request);

                log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 SurvivorPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
            }
            else {
                SurvivorPaymentReviewForm qryCondForm = new SurvivorPaymentReviewForm();
                BeanUtility.copyPropertiesForUpdate(qryCondForm, iform, ConstantKey.OLD_FIELD_PREFIX);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setSurvivorPaymentReviewCaseForTitle(dataList.get(0), request);// title預設抓第一筆資料
                CaseSessionHelper.setSurvivorPaymentReviewList(dataList, request);
                FormSessionHelper.setSurvivorPaymentReviewQueryForm(qryCondForm, request);

                log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 SurvivorPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
            // }
            // else {
            // return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            // }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorPaymentReviewAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 個案審核 (baco0d211a.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSingleReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 個案審核 SurvivorPaymentReviewAction.doSingleReview() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorPaymentReviewForm iform = (SurvivorPaymentReviewForm) form;

        try {
            // String apNo = iform.getApNo();
            // List<SurvivorPaymentReviewCase> dataList = reviewService.selectReviewDataFromBaappbaseForSurvivor(apNo);
            String apNo = iform.getApNo();
            List<SurvivorPaymentReviewCase> dataList = CaseSessionHelper.getSurvivorPaymentReviewList(request);
            SurvivorPaymentReviewCase caseObj = dataList.get(iform.getDataIndex());

            // if (dataList.size() == 1) {
            // SurvivorPaymentReviewCase caseObj = dataList.get(0);
            if (caseObj != null) {
                apNo = caseObj.getApNo();

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
                BeanUtility.copyPropertiesForUpdate(modifyForm, caseObj, ConstantKey.OLD_FIELD_PREFIX);

                // 將 查詢結果 case 存到 Request Scope
                FormSessionHelper.setSurvivorPaymentReviewForm(modifyForm, request);

                CaseSessionHelper.setSurvivorPaymentReviewCase(caseObj, request);
                CaseSessionHelper.setSurvivorPaymentReviewDisabledData(disabledData, request);
                CaseSessionHelper.setSurvivorPayDataList(payList, request);
                CaseSessionHelper.setSurvivorPaymentReviewEvtChkList(evtChkList, request);
                CaseSessionHelper.setSurvivorPaymentReviewBenChkList(benChkList, request);
                CaseSessionHelper.setSurvivorPaymentReviewMatchChkList(matchChkList, request);
                CaseSessionHelper.setSurvivorBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setSurvivorOrigBenIssuDataList(benIssuDataList, request);
                CaseSessionHelper.setSurvivorBenIssuDataList((List<SurvivorPaymentReviewCase>) transDataList.get(0), request);
                CaseSessionHelper.setSurvivorBenIssuDataMap((Map<BigDecimal, SurvivorPaymentReviewExtCase>) transDataList.get(1), request);
                CaseSessionHelper.setSurvivorPaymentReviewBenNameList(benNameList, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk6List(detail6, request);

                log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 個案審核 SurvivorPaymentReviewAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
            }
            else {
                // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 個案審核 SurvivorPaymentReviewAction.doSingleReview() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorPaymentReviewAction.doSingleReview() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL_FAIL);
        }
    }

    // /**
    // * 審核作業 - 遺屬年金給付審核作業 - 受款人資料 (baco0d213q.jsp) 修改確認(單筆) var.1.00
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward doSaveBeneficiaryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 受款人資料 - 修改確認(單筆) SurvivorPaymentReviewAction.doSaveBeneficiaryData() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // SurvivorPaymentReviewForm iform = (SurvivorPaymentReviewForm) form;
    //
    // try {
    // SurvivorPaymentReviewCase origCaseObj = CaseSessionHelper.getSurvivorPaymentReviewCase(request);
    //
    // // 受理編號
    // String apNo = origCaseObj.getApNo();
    // // 核定年月
    // String issuYm = origCaseObj.getIssuYm();
    // // 受款人核定資料
    // Map<BigDecimal, SurvivorPaymentReviewExtCase> benIssuDataMap = CaseSessionHelper.getSurvivorBenIssuDataMap(request);
    //
    // // 更新資料
    // reviewService.updateSingleBeneficiaryDataForSurvivor(origCaseObj, iform.getNotifyForm(), iform.getChgNote(), iform.getManchkMk(), benIssuDataMap, userData);
    //
    // // 重新查詢資料
    // List<SurvivorPaymentReviewCase> dataList = reviewService.selectReviewDataFromBaappbaseForSurvivor(apNo);
    //
    // if (dataList.size() == 1) {
    // SurvivorPaymentReviewCase caseObj = dataList.get(0);
    // apNo = caseObj.getApNo();
    // // 取得 Q1,Q2 for 判斷「人工審核結果」radio button用
    // caseObj.setQ1(reviewService.getQ1ForManchkMkRadio(apNo, caseObj.getIssuYm()));
    // caseObj.setQ2(reviewService.getQ2ForManchkMkRadio(apNo, caseObj.getIssuYm()));
    //
    // // 取得 XX函註記
    // List<SurvivorPaymentReviewCase> detail1 = reviewService.getLetterTypeMk1ListForSurvivor(apNo);
    // List<SurvivorPaymentReviewCase> detail2 = reviewService.getLetterTypeMk2ListForSurvivor(apNo);
    // List<SurvivorPaymentReviewCase> detail3 = reviewService.getLetterTypeMk3ListForSurvivor(apNo);
    // List<SurvivorPaymentReviewCase> detail4 = reviewService.getLetterTypeMk4ListForSurvivor(apNo);
    // List<SurvivorPaymentReviewCase> detail5 = reviewService.getLetterTypeMk5ListForSurvivor(apNo);
    // SurvivorPaymentReviewCase detail6 = reviewService.getLetterTypeMk6ListForSurvivor(apNo);
    //
    // // 取得 失能相關資料
    // SurvivorPaymentReviewCase disabledData = reviewService.selectDisabledDataForSurvivor(apNo);
    // // 取得 編審給付資料
    // List<SurvivorPaymentReviewExtCase> payList = reviewService.getPayDataForSurvivor(caseObj.getApNo(), caseObj.getIssuYm());
    // // 取得 事故者編審註記資料
    // List<SurvivorPaymentReviewExtCase> evtChkList = reviewService.getSurvivorPaymentReviewEvtChkList(caseObj.getApNo());
    // // 取得 眷屬編審註記資料
    // List<SurvivorPaymentReviewCase> benChkList = reviewService.getSurvivorPaymentReviewBenChkList(caseObj.getApNo());
    // // 取得 符合註記資料
    // List<SurvivorPaymentReviewCase> matchChkList = reviewService.getSurvivorPaymentReviewOtherChkList(caseObj.getApNo());
    // // 取得 事故者/受款人 資料(含事故者核定資料)
    // List<SurvivorPaymentReviewCase> beneficiaryDataList = reviewService.getbeneficiaryDataListForSurvivor(caseObj.getApNo(), caseObj.getQ1(), caseObj.getQ2());
    // // 取得 所有受款人核定資料(不含事故者)
    // List<SurvivorPaymentReviewExtCase> benIssuDataList = reviewService.getIssuDataListForSurvivor(beneficiaryDataList, caseObj.getApNo(), "0000", "<>");
    // // 將受款人核定資料(不含事故者) 轉成頁面顯示用之List 與 session暫存用之map
    // List<Object> transDataList = reviewService.transInitBenIssuDataForSurvivor(benIssuDataList);
    // // 取得 更正原因 清單
    // request.getSession().setAttribute(ConstantKey.CHGNOTE_OPTION_LIST, selectOptionService.getChgNoteOptionList());
    // // 取得受款人姓名清單
    // List<SurvivorPaymentReviewExtCase> benNameList = reviewService.getBenNameListForSurvivor(apNo, "1");
    // // 取得 給付年月下拉選單
    // request.getSession().setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_PAYYM_OPTION_LIST, reviewService.getPayYmOptionListForSurvivor(caseObj.getApNo(), caseObj.getIssuYm()));
    // // 取得 受款人下拉選單
    // request.getSession().setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_SEQNO_OPTION_LIST, reviewService.getSeqNoOptionListForSurvivor(caseObj.getApNo()));
    //
    // SurvivorPaymentReviewForm modifyForm = new SurvivorPaymentReviewForm();
    // BeanUtility.copyProperties(modifyForm, caseObj);
    //
    // // 將 查詢結果 case 存到 Request Scope
    // FormSessionHelper.setSurvivorPaymentReviewForm(modifyForm, request);
    //
    // CaseSessionHelper.setSurvivorPaymentReviewCase(caseObj, request);
    // CaseSessionHelper.setSurvivorPaymentReviewDisabledData(disabledData, request);
    // CaseSessionHelper.setSurvivorPayDataList(payList, request);
    // CaseSessionHelper.setSurvivorPaymentReviewEvtChkList(evtChkList, request);
    // CaseSessionHelper.setSurvivorPaymentReviewBenChkList(benChkList, request);
    // CaseSessionHelper.setSurvivorPaymentReviewMatchChkList(matchChkList, request);
    // CaseSessionHelper.setSurvivorBeneficiaryDataList(beneficiaryDataList, request);
    // CaseSessionHelper.setSurvivorOrigBenIssuDataList(benIssuDataList, request);
    // CaseSessionHelper.setSurvivorBenIssuDataList((List<SurvivorPaymentReviewCase>) transDataList.get(0), request);
    // CaseSessionHelper.setSurvivorBenIssuDataMap((Map<BigDecimal, SurvivorPaymentReviewExtCase>) transDataList.get(1), request);
    // CaseSessionHelper.setSurvivorPaymentReviewBenNameList(benNameList, request);
    // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk1List(detail1, request);
    // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk2List(detail2, request);
    // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk3List(detail3, request);
    // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk4List(detail4, request);
    // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk5List(detail5, request);
    // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk6List(detail6, request);
    //
    // // 設定更新成功訊息
    // saveMessages(session, DatabaseMessageHelper.getPaymentReviewSuccessMessage());
    // log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 個案審核 SurvivorPaymentReviewAction.doSingleReview() 完成 ... ");
    // return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
    // }
    // // else {
    // // // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
    // // saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());
    // //
    // // // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
    // // FormSessionHelper.removeSurvivorPaymentReviewForm(request);
    // // FormSessionHelper.removeSurvivorPaymentReviewQueryForm(request);
    // // CaseSessionHelper.removeAllSurvivorPaymentReviewCase(request);
    // //
    // // log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 個案審核 SurvivorPaymentReviewAction.doSingleReview() 完成 ... ");
    // // return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
    // // }
    // else {
    // SurvivorPaymentReviewForm qryCondForm = FormSessionHelper.getSurvivorPaymentReviewQueryForm(request);
    // qryCondForm.setResultSize(new BigDecimal(dataList.size()));
    //
    // // 將 查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setSurvivorPaymentReviewCaseForTitle(dataList.get(0), request);// title預設抓第一筆資料
    // CaseSessionHelper.setSurvivorPaymentReviewList(dataList, request);
    // FormSessionHelper.setSurvivorPaymentReviewQueryForm(qryCondForm, request);
    //
    // log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 SurvivorPaymentReviewAction.doQuery() 完成 ... ");
    // return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
    // }
    // }
    // catch (Exception e) {
    // // 設定更新失敗訊息
    // log.error("SurvivorPaymentReviewAction.doSaveBeneficiaryData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
    // return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
    // }
    // }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受款人資料 (baco0d213q.jsp) 修改確認(單筆) var.2.00
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSaveBeneficiaryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 受款人資料 - 修改確認(單筆) SurvivorPaymentReviewAction.doSaveBeneficiaryData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorPaymentReviewForm iform = (SurvivorPaymentReviewForm) form;

        try {
            SurvivorPaymentReviewCase origCaseObj = CaseSessionHelper.getSurvivorPaymentReviewCase(request);

            // 受理編號
            String apNo = origCaseObj.getApNo();
            // 核定年月
            String issuYm = origCaseObj.getIssuYm();
            // 受款人核定資料
            Map<BigDecimal, SurvivorPaymentReviewExtCase> benIssuDataMap = CaseSessionHelper.getSurvivorBenIssuDataMap(request);
            // 取得 事故者/受款人 資料(含事故者核定資料)
            List<SurvivorPaymentReviewCase> beneficiaryDataList = CaseSessionHelper.getSurvivorBeneficiaryDataList(request);
            // 取得 所有受款人核定資料(不含事故者)
            List<SurvivorPaymentReviewExtCase> benIssuDataList = CaseSessionHelper.getSurvivorOrigBenIssuDataList(request);
            
            // 取得需記錄異動LOG的欄位資料
            // 為不影響前端頁面顯示, 故須複製一份 Form
            SurvivorPaymentReviewForm tempForm = new SurvivorPaymentReviewForm();
            BeanUtility.copyProperties(tempForm, form);
            tempForm.setProcStat("20");
            origCaseObj.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_REVIEW_OLDAGE_FIELD_RESOURCE_PREFIX));
            
            // 更新資料
            reviewService.updateSingleBeneficiaryDataForSurvivor(origCaseObj, iform.getNotifyForm(), iform.getChgNote(), iform.getManchkMk(), beneficiaryDataList, benIssuDataList, userData);

            // 重新查詢資料
            List<SurvivorPaymentReviewCase> dataList = reviewService.selectReviewDataFromBaappbaseForSurvivor(apNo);

            if (dataList.size() == 1) {
                SurvivorPaymentReviewCase caseObj = dataList.get(0);
                apNo = caseObj.getApNo();
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
                beneficiaryDataList = reviewService.getbeneficiaryDataListForSurvivor(caseObj.getApNo(), caseObj.getQ1(), caseObj.getQ2());
                // 取得 所有受款人核定資料(不含事故者)
                benIssuDataList = reviewService.getIssuDataListForSurvivor(beneficiaryDataList, caseObj.getApNo(), "0000", "<>");
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
                // FormSessionHelper.setSurvivorPaymentReviewForm(modifyForm, request);
                //
                // CaseSessionHelper.setSurvivorPaymentReviewCase(caseObj, request);
                // CaseSessionHelper.setSurvivorPaymentReviewDisabledData(disabledData, request);
                // CaseSessionHelper.setSurvivorPayDataList(payList, request);
                // CaseSessionHelper.setSurvivorPaymentReviewEvtChkList(evtChkList, request);
                // CaseSessionHelper.setSurvivorPaymentReviewBenChkList(benChkList, request);
                // CaseSessionHelper.setSurvivorPaymentReviewMatchChkList(matchChkList, request);
                // CaseSessionHelper.setSurvivorBeneficiaryDataList(beneficiaryDataList, request);
                // CaseSessionHelper.setSurvivorOrigBenIssuDataList(benIssuDataList, request);
                // CaseSessionHelper.setSurvivorBenIssuDataList((List<SurvivorPaymentReviewCase>) transDataList.get(0), request);
                // CaseSessionHelper.setSurvivorBenIssuDataMap((Map<BigDecimal, SurvivorPaymentReviewExtCase>) transDataList.get(1), request);
                // CaseSessionHelper.setSurvivorPaymentReviewBenNameList(benNameList, request);
                // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk1List(detail1, request);
                // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk2List(detail2, request);
                // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk3List(detail3, request);
                // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk4List(detail4, request);
                // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk5List(detail5, request);
                // CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk6List(detail6, request);

                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeSurvivorPaymentReviewForm(request);
                FormSessionHelper.removeSurvivorPaymentReviewQueryForm(request);
                CaseSessionHelper.removeAllSurvivorPaymentReviewCase(request);

                // 設定更新成功訊息
                saveMessages(session, CustomMessageHelper.getReviewSuccessMessage());
                log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 個案審核 SurvivorPaymentReviewAction.doSingleReview() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
            }
            else {
                SurvivorPaymentReviewForm qryCondForm = FormSessionHelper.getSurvivorPaymentReviewQueryForm(request);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setSurvivorPaymentReviewCaseForTitle(dataList.get(0), request);// title預設抓第一筆資料
                CaseSessionHelper.setSurvivorPaymentReviewList(dataList, request);
                FormSessionHelper.setSurvivorPaymentReviewQueryForm(qryCondForm, request);

                log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 SurvivorPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
        }
        catch (Exception e) {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeSurvivorPaymentReviewForm(request);
            FormSessionHelper.removeSurvivorPaymentReviewQueryForm(request);
            CaseSessionHelper.removeAllSurvivorPaymentReviewCase(request);
            // 設定更新失敗訊息
            log.error("SurvivorPaymentReviewAction.doSaveBeneficiaryData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReviewFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 返回清單
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 返回清單 SurvivorPaymentReviewAction.doBackList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorPaymentReviewForm iform = (SurvivorPaymentReviewForm) form;

        try {
            List<SurvivorPaymentReviewCase> dataList = new ArrayList<SurvivorPaymentReviewCase>();
            SurvivorPaymentReviewForm qryCondForm = FormSessionHelper.getSurvivorPaymentReviewQueryForm(request);
            String apNo = qryCondForm.getApNoStr();// 畫面輸入之受理編號

            // if (("1").equals(qryCondForm.getQryCond()) || ("2").equals(qryCondForm.getQryCond())) {
            // 用 受理編號 查詢
            // if (("1").equals(qryCondForm.getQryCond())) {
            dataList = reviewService.selectReviewDataFromBaappbaseForSurvivor(apNo);
            // }
            // 用 列印日期 + 頁次 查詢
            // else if (("2").equals(qryCondForm.getQryCond())) {
            // dataList = reviewService.selectReviewDataFromExalistForSurvivor(DateUtility.changeDateType(qryCondForm.getRptDate()), qryCondForm.getPageNo());
            // }

            if (dataList.size() <= 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 SurvivorPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            else if (dataList.size() == 1) {
                SurvivorPaymentReviewCase caseObj = new SurvivorPaymentReviewCase();
                // if (("1").equals(iform.getQryCond())) {
                caseObj = dataList.get(0);
                // }
                // else if (("2").equals(iform.getQryCond())) {
                // SurvivorPaymentReviewCase tempCase = dataList.get(0);
                // List<SurvivorPaymentReviewCase> caseList = reviewService.selectReviewDataFromBaappbaseForSurvivor(tempCase.getApNo());
                // if (caseList.size() != 1) {
                // // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                // saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());
                // log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 SurvivorPaymentReviewAction.doQuery() 完成 ... ");
                // return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                // }
                // else {
                // caseObj = caseList.get(0);
                // }
                // }

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

                SurvivorPaymentReviewForm modifyForm = new SurvivorPaymentReviewForm();
                BeanUtility.copyProperties(modifyForm, caseObj);
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果 case 存到 Request Scope
                CaseSessionHelper.setSurvivorPaymentReviewCase(caseObj, request);
                CaseSessionHelper.setSurvivorPaymentReviewDisabledData(disabledData, request);
                CaseSessionHelper.setSurvivorPayDataList(payList, request);
                CaseSessionHelper.setSurvivorPaymentReviewEvtChkList(evtChkList, request);
                CaseSessionHelper.setSurvivorPaymentReviewBenChkList(benChkList, request);
                CaseSessionHelper.setSurvivorPaymentReviewMatchChkList(matchChkList, request);
                CaseSessionHelper.setSurvivorBeneficiaryDataList(beneficiaryDataList, request);
                CaseSessionHelper.setSurvivorOrigBenIssuDataList(benIssuDataList, request);
                CaseSessionHelper.setSurvivorBenIssuDataList((List<SurvivorPaymentReviewCase>) transDataList.get(0), request);
                CaseSessionHelper.setSurvivorBenIssuDataMap((Map<BigDecimal, SurvivorPaymentReviewExtCase>) transDataList.get(1), request);
                CaseSessionHelper.setSurvivorPaymentReviewBenNameList(benNameList, request);
                FormSessionHelper.setSurvivorPaymentReviewForm(modifyForm, request);
                FormSessionHelper.setSurvivorPaymentReviewQueryForm(qryCondForm, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk6List(detail6, request);

                log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 SurvivorPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(FORWARD_SINGLE_REVIEW_DETAIL);
            }
            else {
                qryCondForm.setResultSize(new BigDecimal(dataList.size()));

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setSurvivorPaymentReviewCaseForTitle(dataList.get(0), request);// title預設抓第一筆資料
                CaseSessionHelper.setSurvivorPaymentReviewList(dataList, request);
                FormSessionHelper.setSurvivorPaymentReviewQueryForm(qryCondForm, request);

                log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 查詢頁面 SurvivorPaymentReviewAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
            // }
            // else {
            // return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            // }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorPaymentReviewAction.doBackList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 返回 SurvivorPaymentReviewAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removeSurvivorPaymentReviewForm(request);
        FormSessionHelper.removeSurvivorPaymentReviewQueryForm(request);
        CaseSessionHelper.removeAllSurvivorPaymentReviewCase(request);

        log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 返回 SurvivorPaymentReviewAction.doBack() 完成 ... ");
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
        log.debug("執行 審核作業 - 遺屬年金給付審核作業 - 檢視受理審核清單 SurvivorPaymentReviewAction.doReviewRpt() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        SurvivorPaymentReviewForm iform = (SurvivorPaymentReviewForm) form;
        try {
            SurvivorPaymentReviewCase detailCase = CaseSessionHelper.getSurvivorPaymentReviewCase(request);
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
            log.error("SurvivorPaymentReviewAction.doReviewRpt() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
