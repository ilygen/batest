package tw.gov.bli.ba.query.actions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.encoder.Encode;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.domain.Cvldtl;
import tw.gov.bli.ba.domain.Nbdapr;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.query.cases.PaymentQueryArclistDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryAvgAmtDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryBenDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryCpiDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryDisabledDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryFamilyDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryIssuPayDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryLetterTypeMkCase;
import tw.gov.bli.ba.query.cases.PaymentQueryLoanDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryMasterCase;
import tw.gov.bli.ba.query.cases.PaymentQueryNpDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryNpIssuDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryOccAccDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryReFeesDetailDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryReFeesMasterDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryUnqualifiedNoticeDataCase;
import tw.gov.bli.ba.query.cases.UpdateLogQueryCase;
import tw.gov.bli.ba.query.cases.UpdateLogQueryDetailCase;
import tw.gov.bli.ba.query.forms.PaymentQueryForm;
import tw.gov.bli.ba.query.helper.CaseSessionHelper;
import tw.gov.bli.ba.query.helper.FormSessionHelper;
import tw.gov.bli.ba.rpt.actions.OldAgeReviewRpt01Action;
import tw.gov.bli.ba.rpt.forms.OldAgeReviewRpt01Form;
import tw.gov.bli.ba.rpt.report.DisabledPaymentRpt01Report;
import tw.gov.bli.ba.rpt.report.DisabledPaymentRpt02Report;
import tw.gov.bli.ba.rpt.report.OldAgePaymentRpt01Report;
import tw.gov.bli.ba.rpt.report.OldAgePaymentRpt02Report;
import tw.gov.bli.ba.rpt.report.SimplifyDisabledPaymentRpt01Report;
import tw.gov.bli.ba.rpt.report.SimplifyDisabledPaymentRpt02Report;
import tw.gov.bli.ba.rpt.report.SimplifyOldAgePaymentRpt01Report;
import tw.gov.bli.ba.rpt.report.SimplifyOldAgePaymentRpt02Report;
import tw.gov.bli.ba.rpt.report.SimplifySurvivorPaymentRpt01Report;
import tw.gov.bli.ba.rpt.report.SimplifySurvivorPaymentRpt02Report;
import tw.gov.bli.ba.rpt.report.SurvivorPaymentRpt01Report;
import tw.gov.bli.ba.rpt.report.SurvivorPaymentRpt02Report;
import tw.gov.bli.ba.services.QueryService;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateBareCheckCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCase;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.DateUtil;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 查詢作業 - 給付查詢作業 (BAIQ0D010Q)
 * 
 * @author Rickychi
 */
public class PaymentQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(PaymentQueryAction.class);

    private QueryService queryService;

    // 查詢作業 - 給付查詢作業 - 明細清單
    private static final String FORWARD_MASTER_LIST = "masterList";
    // 查詢作業 - 給付查詢作業 - 老年年金明細查詢
    private static final String FORWARD_OLDAGE_DETAIL_DATA = "oldAgeDetailData";
    // 查詢作業 - 給付查詢作業 - 老年年金案件資料
    private static final String FORWARD_OLDAGE_APPLICATION_DATA = "oldAgeApplicationData";
    // 查詢作業 - 給付查詢作業 - 老年年金事故者/受款人資料
    private static final String FORWARD_OLDAGE_BEN_DATA = "oldAgeBenData";
    // 查詢作業 - 給付查詢作業 - 老年年金紓困貸款
    private static final String FORWARD_OLDAGE_LOAN_DATA = "oldAgeLoanData";
    // 查詢作業 - 給付查詢作業 - 老年年金平均薪資
    private static final String FORWARD_OLDAGE_AVGAMT_DATA = "oldAgeAvgAmtData";
    // 查詢作業 - 給付查詢作業 - 老年年金物價指數調整
    private static final String FORWARD_OLDAGE_CPI_DATA = "oldAgeCpiData";
    // 查詢作業 - 給付查詢作業 - 老年年金物價指數調整記錄資料
    private static final String FORWARD_OLDAGE_CPIREC_DATA = "oldAgeCpiRecData";
    // 查詢作業 - 給付查詢作業 - 老年年金歸檔記錄資料
    private static final String FORWARD_OLDAGE_ARCLIST_DATA = "oldAgeArclistData";
    // 查詢作業 - 給付查詢作業 - 老年年金更正日誌資料
    private static final String FORWARD_OLDAGE_UPDATE_LOG_QUERY_DATA = "oldAgeUpdateLogQueryData";

    // 查詢作業 - 給付查詢作業 - 失能年金明細查詢
    private static final String FORWARD_DISABLED_DETAIL_DATA = "disabledDetailData";
    // 查詢作業 - 給付查詢作業 - 失能年金案件資料
    private static final String FORWARD_DISABLED_APPLICATION_DATA = "disabledApplicationData";
    // 查詢作業 - 給付查詢作業 - 失能年金事故者/受款人資料
    private static final String FORWARD_DISABLED_BEN_DATA = "disabledBenData";
    // 查詢作業 - 給付查詢作業 - 失能年金眷屬資料
    private static final String FORWARD_DISABLED_FAMILY_DATA = "disabledFamilyData";
    // 查詢作業 - 給付查詢作業 - 失能年金複檢費用資料
    private static final String FORWARD_DISABLED_REFEES_MASTER_DATA_LIST = "disabledReFeesMasterDataList";
    // 查詢作業 - 給付查詢作業 - 失能年金複檢費用明細資料
    private static final String FORWARD_DISABLED_REFEES_DETAIL_DATA = "disabledReFeesDetailData";
    // 查詢作業 - 給付查詢作業 - 失能年金在學資料
    private static final String FORWARD_DISABLED_STUD_DETAIL_DATA = "disabledStudDetailData";
    // 查詢作業 - 給付查詢作業 - 失能年金紓困貸款
    private static final String FORWARD_DISABLED_LOAN_DATA = "disabledLoanData";
    // 查詢作業 - 給付查詢作業 - 失能年金平均薪資
    private static final String FORWARD_DISABLED_AVGAMT_DATA = "disabledAvgAmtData";
    // 查詢作業 - 給付查詢作業 - 失能年金物價指數調整
    private static final String FORWARD_DISABLED_CPI_DATA = "disabledCpiData";
    // 查詢作業 - 給付查詢作業 - 失能年金物價指數調整記錄資料
    private static final String FORWARD_DISABLED_CPIREC_DATA = "disabledCpiRecData";
    // 查詢作業 - 給付查詢作業 - 失能年金重新查核失能程度資料
    private static final String FORWARD_DISABLED_REHC_DATA = "disabledRehcData";
    // 查詢作業 - 給付查詢作業 - 失能年金歸檔記錄資料
    private static final String FORWARD_DISABLED_ARCLIST_DATA = "disabledArclistData";
    // 查詢作業 - 給付查詢作業 - 失能年金更正日誌資料
    private static final String FORWARD_DISABLED_UPDATE_LOG_QUERY_DATA = "disabledUpdateLogQueryData";
    // 查詢作業 - 給付查詢作業 - 失能年金國保資料
    private static final String FORWARD_DISABLED_NP_DATA = "disabledNpData";

    // 查詢作業 - 給付查詢作業 - 遺屬年金明細查詢
    private static final String FORWARD_SURVIVOR_DETAIL_DATA = "survivorDetailData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金案件資料
    private static final String FORWARD_SURVIVOR_APPLICATION_DATA = "survivorApplicationData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金事故者/受款人資料
    private static final String FORWARD_SURVIVOR_BEN_DATA = "survivorBenData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金在學資料
    private static final String FORWARD_SURVIVOR_STUD_DETAIL_DATA = "survivorStudDetailData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金重殘資料
    private static final String FORWARD_SURVIVOR_HANDICAP_DETAIL_DATA = "survivorHandicapDetailData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金紓困貸款
    private static final String FORWARD_SURVIVOR_LOAN_DATA = "survivorLoanData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金平均薪資
    private static final String FORWARD_SURVIVOR_AVGAMT_DATA = "survivorAvgAmtData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金物價指數調整
    private static final String FORWARD_SURVIVOR_CPI_DATA = "survivorCpiData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金物價指數調整記錄資料
    private static final String FORWARD_SURVIVOR_CPIREC_DATA = "survivorCpiRecData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金強迫不合格資料
    private static final String FORWARD_SURVIVOR_COMPEL_NOPAY_DETAIL_DATA = "survivorCompelNopayDetailData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金歸檔記錄資料
    private static final String FORWARD_SURVIVOR_ARCLIST_DATA = "survivorArclistData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金更正日誌資料
    private static final String FORWARD_SURVIVOR_UPDATE_LOG_QUERY_DATA = "survivorUpdateLogQueryData";
    // 查詢作業 - 給付查詢作業 - 遺屬年金核定通知記錄資料
    private static final String FORWARD_SURVIVOR_BAUNQUALIFIED_NOTICE_DATA = "survivorUnqualifiedNoticeData";

    // // 查詢作業 - 給付查詢作業 - 請領同類/他類資料 頁面
    // private static final String FORWARD_APPLY_DATA = "applyData";
    // // 查詢作業 - 給付查詢作業 - 核定資料
    // private static final String FORWARD_ISSU_DATA = "issuData";
    // // 查詢作業 - 給付查詢作業 - 年資資料
    // private static final String FORWARD_SENI_DATA = "seniData";
    // // 查詢作業 - 給付查詢作業 - 一次給付資料
    // private static final String FORWARD_ONCEPAY_DATA = "oncePayData";
    // // 查詢作業 - 給付查詢作業 - 核定/給付資料明細
    // private static final String FORWARD_ISSU_PAY_DETAIL = "issuPayDetail";

    /**
     * 查詢作業 - 給付查詢作業 - 查詢頁面(baiq0d010q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 查詢頁面 PaymentQueryAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            String apNo = iform.getApNoStr();
            String benName = iform.getName();
            String benIdnNo = iform.getIdn();
            String benBrDate = iform.getBrDateStr();
            String qryCond = iform.getQryCond();
            String startYm = iform.getStartYmStr();
            String endYm = iform.getEndYmStr();

            // List<PaymentQueryMasterCase> queryList = queryService.selectPaymentQueryMasterData(apNo, benIdnNo, benName, benBrDate, qryCond, startYm, endYm);
            List<PaymentQueryMasterCase> queryList = CaseSessionHelper.getPaymentQueryMasterCaseList(request);

            if (queryList.size() == 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 查詢作業 - 給付查詢作業 - 查詢頁面 PaymentQueryAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            else {
                // 將 查詢條件/查詢結果清單 存到 Request Scope
                PaymentQueryForm qryForm = new PaymentQueryForm();
                BeanUtility.copyProperties(qryForm, iform);
                FormSessionHelper.setPaymentQueryCondForm(qryForm, request);
                CaseSessionHelper.setPaymentQueryMasterCaseList(queryList, request);

                log.debug("執行 查詢作業 - 給付查詢作業 - 查詢頁面 PaymentQueryAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    // ---------------------------------------- 老年年金 ----------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 明細查詢(案件資料)(baiq0d011q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doOldAgeDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金明細查詢(案件資料) PaymentQueryAction.doOldAgeDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            String apNo = iform.getApNo();
            // PaymentQueryDetailDataCase detail = queryService.getPaymentQueryDetail(apNo, null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);
            List<PaymentQueryMasterCase> caseObj = CaseSessionHelper.getPaymentQueryMasterCaseList(request);

            PaymentQueryDetailDataCase detail = new PaymentQueryDetailDataCase();

            for (PaymentQueryMasterCase caseData : caseObj) {
                if (caseData.getApNo().equals(apNo)) {
                    detail = queryService.getPaymentQueryDetail(apNo, null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);
                    detail.setSecrecy(caseData.getSecrecy());
                }
            }

            if (detail == null) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 查詢作業 - 老年年金明細查詢(案件資料) PaymentQueryAction.doOldAgeDetail() 完成 ... ");
                return mapping.findForward(FORWARD_MASTER_LIST);
            }
            else {
                // 起始查詢條件
                PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
                String qryCond = qryForm.getQryCond();
                String startYm = qryForm.getStartYmStr();
                String endYm = qryForm.getEndYmStr();
                String evtName = qryForm.getName();
                String evtIdnNo = qryForm.getIdn();
                String evtBrDate = qryForm.getBrDateStr();

                // 累計已領年金金額 = dabAnnuAmt + annuAmt
                detail.setAnnuAmt(detail.getDabAnnuAmt().add(detail.getAnnuAmt()));

                // 取得 承辦人分機號碼
                detail.setEmpExt(queryService.getEmpExtData(detail.getApNo()));

                // 取得原住民羅馬拼音
                Cvldtl cvldtl = queryService.selectHaddrBy(detail.getEvtIds());
                if (cvldtl != null) {
                    detail.setRmp_Name(cvldtl.getRmp_Name());
                }

                // 取得 XX函註記
                // detail = queryService.getLetterTypeMk(detail);
                List<PaymentQueryLetterTypeMkCase> detail1 = queryService.getLetterTypeMk1List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail2 = queryService.getLetterTypeMk2List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail3 = queryService.getLetterTypeMk3List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail4 = queryService.getLetterTypeMk4List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail5 = queryService.getLetterTypeMk5List(detail.getApNo());
                PaymentQueryLetterTypeMkCase detail6 = queryService.getLetterTypeMk6List(detail.getApNo());

                // 取得 核定年月資料
                List<Object> mixDataList = queryService.getPaymentQueryIssuPayDataList(apNo, startYm, endYm, qryCond);
                List<Badapr> origIssuPayDataList = (List<Badapr>) mixDataList.get(0);
                List<PaymentQueryIssuPayDataCase> issuPayDataList = (List<PaymentQueryIssuPayDataCase>) mixDataList.get(1);

                // 取得 事故者編審註記資料
                List<PaymentQueryDetailDataCase> chkFileDataList = queryService.getPaymentQueryEvtChkList(detail.getApNo(), "0000");

                // 取得 核定年月下拉選單
                if (StringUtils.equals("ISSUYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_ISSUYM_OPTION_LIST, queryService.getIssuYmOptionList(origIssuPayDataList));
                }
                // 取得 給付年月下拉選單
                if (StringUtils.equals("PAYYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_PAYYM_OPTION_LIST, queryService.getPayYmOptionList(origIssuPayDataList));
                }
                // 取得 受款人下拉選單
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_SEQNO_OPTION_LIST, queryService.getSeqNoOptionList(origIssuPayDataList));

                // 將 查詢條件/查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentQueryDetailDataCase(detail, request);
                CaseSessionHelper.setPaymentQueryIssuPayDataCaseList(issuPayDataList, request);
                CaseSessionHelper.setPaymentQueryOrigIssuPayDataCaseList(origIssuPayDataList, request);
                CaseSessionHelper.setPaymentQueryEvtChkFileDataCase(chkFileDataList, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk6List(detail6, request);

                log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金明細查詢(案件資料) PaymentQueryAction.doOldAgeDetail() 完成 ... ");
                return mapping.findForward(FORWARD_OLDAGE_DETAIL_DATA);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doOldAgeDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 老年年金案件資料(baiq0d012q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doOldAgeApplicationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金案件資料 PaymentQueryAction.doOldAgeApplicationData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            String apNo = iform.getApNo();

            List<PaymentQueryMasterCase> caseObj = CaseSessionHelper.getPaymentQueryMasterCaseList(request);

            PaymentQueryDetailDataCase detail = new PaymentQueryDetailDataCase();

            for (PaymentQueryMasterCase caseData : caseObj) {
                if (caseData.getApNo().equals(apNo)) {
                    detail = queryService.getPaymentQueryDetail(apNo, null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);
                    detail.setSecrecy(caseData.getSecrecy());
                }
            }
            // 案件資料
            // PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 取得 案件資料
            // PaymentQueryDetailDataCase detail = queryService.getPaymentQueryDetail(caseObj.getApNo(), null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_L);

            if (detail == null) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金案件資料 PaymentQueryAction.doOldAgeApplicationData() 完成 ... ");
                return mapping.findForward(FORWARD_MASTER_LIST);
            }
            else {
                // 起始查詢條件
                PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
                String qryCond = qryForm.getQryCond();
                String startYm = qryForm.getStartYmStr();
                String endYm = qryForm.getEndYmStr();
                String evtName = qryForm.getName();
                String evtIdnNo = qryForm.getIdn();
                String evtBrDate = qryForm.getBrDateStr();

                // 累計已領年金金額 = dabAnnuAmt + annuAmt
                detail.setAnnuAmt(detail.getDabAnnuAmt().add(detail.getAnnuAmt()));

                // 取得 承辦人分機號碼
                detail.setEmpExt(queryService.getEmpExtData(detail.getApNo()));

                // 取得 XX函註記
                // detail = queryService.getLetterTypeMk(detail);
                List<PaymentQueryLetterTypeMkCase> detail1 = queryService.getLetterTypeMk1List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail2 = queryService.getLetterTypeMk2List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail3 = queryService.getLetterTypeMk3List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail4 = queryService.getLetterTypeMk4List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail5 = queryService.getLetterTypeMk5List(detail.getApNo());
                PaymentQueryLetterTypeMkCase detail6 = queryService.getLetterTypeMk6List(detail.getApNo());

                // 取得 核定年月資料
                List<Object> mixDataList = queryService.getPaymentQueryIssuPayDataList(detail.getApNo(), startYm, endYm, qryCond);
                List<Badapr> origIssuPayDataList = (List<Badapr>) mixDataList.get(0);
                List<PaymentQueryIssuPayDataCase> issuPayDataList = (List<PaymentQueryIssuPayDataCase>) mixDataList.get(1);
                // 取得 事故者編審註記資料
                List<PaymentQueryDetailDataCase> chkFileDataList = queryService.getPaymentQueryEvtChkList(detail.getApNo(), "0000");

                // 取得 核定年月下拉選單
                if (StringUtils.equals("ISSUYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_ISSUYM_OPTION_LIST, queryService.getIssuYmOptionList(origIssuPayDataList));
                }
                // 取得 給付年月下拉選單
                if (StringUtils.equals("PAYYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_PAYYM_OPTION_LIST, queryService.getPayYmOptionList(origIssuPayDataList));
                }
                // 取得 受款人下拉選單
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_SEQNO_OPTION_LIST, queryService.getSeqNoOptionList(origIssuPayDataList));

                // 將 查詢條件/查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentQueryDetailDataCase(detail, request);
                CaseSessionHelper.setPaymentQueryIssuPayDataCaseList(issuPayDataList, request);
                CaseSessionHelper.setPaymentQueryOrigIssuPayDataCaseList(origIssuPayDataList, request);
                CaseSessionHelper.setPaymentQueryEvtChkFileDataCase(chkFileDataList, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk6List(detail6, request);

                log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金案件資料 PaymentQueryAction.doOldAgeApplicationData() 完成 ... ");
                return mapping.findForward(FORWARD_OLDAGE_DETAIL_DATA);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doOldAgeApplicationData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 老年年金受款人資料(含核定/編審資料) (baiq0d012q01.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doOldAgeBenData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金受款人資料 PaymentQueryAction.doOldAgeBenData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 起始查詢條件
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 取得 受款人資料
            List<PaymentQueryBenDataCase> benDataList = queryService.getBeneficiaryData(caseObj.getApNo(), qryCond, startYm, endYm);

            //Added By LouisChange 20200311 begin
            //添加受款人資料之原住民羅馬拼音
            //ba_1090122544 需求  : 以事故者身分證及生日去搜尋當戶政全戶檔, 若只有一筆資料且該筆資料原住名身分代碼(OlivCode)為1或2時, 原住民羅馬拼音(Rmp_Name)有值則顯示
            List<Cvldtl> cvldtls = queryService.selectRmpNameBy(caseObj.getEvtIdnNo(), caseObj.getEvtBrDate());
            if (CollectionUtils.isNotEmpty(benDataList) && CollectionUtils.isNotEmpty(cvldtls) && cvldtls.size() == 1) {
            	Cvldtl cvldtl = cvldtls.get(0);
            	if (cvldtl != null && ("1".equals(cvldtl.getOlivCode()) || "2".equals(cvldtl.getOlivCode()))) {
            		String rmpName = cvldtl.getRmp_Name();
            		if (StringUtils.isNotBlank(rmpName)) {
            			for (PaymentQueryBenDataCase benData : benDataList) {
            				benData.setRmp_Name(rmpName);
            			}
            		}
            	}
            }
            //Added By LouisChange 20200311 end
            
            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryBenDataCaseList(benDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金受款人資料 PaymentQueryAction.doOldAgeBenData() 完成 ... ");
            return mapping.findForward(FORWARD_OLDAGE_BEN_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doOldAgeBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_OLDAGE_DETAIL_DATA);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 老年年金紓困貸款(baiq0d012q06.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doOldAgeLoanData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金紓困貸款 PaymentQueryAction.doOldAgeLoanData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 起始查詢條件
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 最新紓困貸款資料
            /**
             * 20180411 當給付主檔（BAAPPBASE）之勞貸戶註記（LSCHKMK）= 4-最後一次扣減紓困貸款土銀已回覆時， 
             * 改讀取紓困貸款回覆記錄檔（BALASTREPLY）相對應欄位資料顯示。
             */
            PaymentQueryLoanDataCase loanMaster = new PaymentQueryLoanDataCase();
            if (caseObj.getLsChkMk().equals("4")) {
                loanMaster = queryService.getPaymentQueryLoanMasterDataForLsChkMk4(caseObj.getApNo(), null);
            }
            else {
                loanMaster = queryService.getPaymentQueryLoanMasterData(caseObj.getApNo(), null);
            }

            // 紓困貸款 核定資料
            List<PaymentQueryLoanDataCase> loanDetailList = queryService.getPaymentQueryLoanDetailData(caseObj.getApNo(), qryCond, startYm, endYm, null);

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryLoanMasterDataCase(loanMaster, request);
            CaseSessionHelper.setPaymentQueryLoanDetailDataCaseList(loanDetailList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金紓困貸款 PaymentQueryAction.doOldAgeLoanData() 完成 ... ");
            return mapping.findForward(FORWARD_OLDAGE_LOAN_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doOldAgeLoanData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 老年年金平均薪資(baiq0d012q02.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doOldAgeAvgAmtData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金平均薪資 PaymentQueryAction.doOldAgeAvgAmtData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;
        // 清除 月平均薪資資料
        CaseSessionHelper.removePaymentQuerySixtyMonAvgAmtDataCaseList(request);

        try {
            // 起始查詢條件
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            // 身分證重號處理
            String evtIdnNo = queryService.getDupeIdnNoMkIdn(caseObj.getApNo(), caseObj.getDupeIdnNoMk(), caseObj.getEvtIdnNo());

            // 年資資料
            PaymentQueryAvgAmtDataCase avgAmtSeniData = queryService.getPaymentQueryAvgAmtSeniDataForOldAge(caseObj.getApNo());
            // 取得 查詢條件 平均薪資月數
            String appMonth = queryService.getBapaavgmonAppMonth(caseObj.getAppDate());
            // 60個月平均薪資資料
            if (StringUtils.isNotBlank(avgAmtSeniData.getEvtIdnNo())) {
                List<PaymentQueryAvgAmtDataCase> sixtyMonAvgAmtDataList = queryService.getPaymentQuerySixtyMonAvgAmtDataForOldAge(caseObj.getApNo(), "0000", evtIdnNo, appMonth);

                // 取得 畫面上顯示實際均薪月數：XXX個月
                String realAvgMon = queryService.getRealAvgMonForOldAge(caseObj.getApNo(), "0000", evtIdnNo);
                avgAmtSeniData.setRealAvgMon(realAvgMon); // 放入 實際均薪月數

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentQuerySixtyMonAvgAmtDataCaseList(sixtyMonAvgAmtDataList, request);
            }
            avgAmtSeniData.setAppMonth(appMonth); // 放入 平均薪資月數
            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryAvgAmtSeniDataCase(avgAmtSeniData, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金平均薪資 PaymentQueryAction.doOldAgeAvgAmtData() 完成 ... ");
            return mapping.findForward(FORWARD_OLDAGE_AVGAMT_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doOldAgeAvgAmtData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 老年年金物價指數調整(baiq0d012q07.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doOldAgeCpiData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金物價指數調整 PaymentQueryAction.doOldAgeCpiData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            String payCode = caseObj.getApNo1();
            // 物價指數調整資料
            List<PaymentQueryCpiDataCase> cpiDataList = queryService.getCpiDataForPaymentQuery(caseObj.getApNo(), payCode);

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryCpiDataCaseList(cpiDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金物價指數調整 PaymentQueryAction.doOldAgeCpiData() 完成 ... ");
            return mapping.findForward(FORWARD_OLDAGE_CPI_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doOldAgeCpiData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 老年年金物價指數調整記錄資料(baiq0d012q08.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doOldAgeCpiRecData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金物價指數調整記錄資料 PaymentQueryAction.doOldAgeCpiRecData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 物價指數調整記錄資料
            List<PaymentQueryCpiDataCase> cpiRecDataList = queryService.getCpiRecForPaymentQuery();

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryCpiRecDataCaseList(cpiRecDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金物價指數調整記錄資料 PaymentQueryAction.doOldAgeCpiRecData() 完成 ... ");
            return mapping.findForward(FORWARD_OLDAGE_CPIREC_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doOldAgeCpiRecData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 老年年金歸檔記錄資料(baiq0d012q09.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doOldAgeArclistData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 歸檔記錄資料 PaymentQueryAction.doOldAgeArclistData() 開始 ... ");
        HttpSession session = request.getSession();
        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 歸檔記錄資料
            PaymentQueryArclistDataCase arclistCase = queryService.getArclistDataForPaymentQuery(caseObj.getApNo());

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryArclistDataCase(arclistCase, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 歸檔記錄資料 PaymentQueryAction.doOldAgeArclistData() 完成 ... ");
            return mapping.findForward(FORWARD_OLDAGE_ARCLIST_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doOldAgeArclistData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 老年年金更正日誌資料(baiq0d012q10.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */

    public ActionForward doOldAgeUpdateLogQueryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金更正日誌資料 PaymentQueryAction.doOldAgeUpdateLogQueryData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeUpdateLogQueryCase(request);

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            String updateDateBegin = "20090101";
            String updateDateEnd = DateUtil.getNowWestDate();
            String apNo = caseObj.getApNo();
            String payCode = caseObj.getApNo1();
            // 取得 更正日誌 資料
            UpdateLogQueryCase caseData = queryService.getUpdateLogQueryCaseBy(payCode, updateDateBegin, updateDateEnd, apNo, null, null);

            // 塞到 Session 中
            CaseSessionHelper.setUpdateLogQueryCase(caseData, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金更正日誌資料 PaymentQueryAction.doOldAgeUpdateLogQueryData() 完成 ... ");

            return mapping.findForward(FORWARD_OLDAGE_UPDATE_LOG_QUERY_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doOldAgeUpdateLogQueryData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業(外單位用) - 老年年金更正日誌資料(baiq0d062q10.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */

    public ActionForward doOldAgeSimplifyUpdateLogQueryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業(外單位用) - 老年年金更正日誌資料 PaymentQueryAction.doOldAgeSimplifyUpdateLogQueryData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeSimplifyUpdateLogQueryCase(request);

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            String updateDateBegin = "20090101";
            String updateDateEnd = DateUtil.getNowWestDate();
            String apNo = caseObj.getApNo();
            String payCode = caseObj.getApNo1();
            // 取得 更正日誌 資料
            UpdateLogQueryCase caseData = queryService.getUpdateLogQueryCaseBy(payCode, updateDateBegin, updateDateEnd, apNo, null, null);

            List<UpdateLogQueryDetailCase> updateLogList = caseData.getUpdateLogList();

            // 隱藏隱私資料
            for (UpdateLogQueryDetailCase LogList : updateLogList) {

                if (!StringUtils.isBlank(LogList.getUpdUser())) {
                    LogList.setUpdUser("*****");
                }
                if (!StringUtils.isBlank(LogList.getUpCol())) {
                    if (LogList.getUpCol().substring(0, 2).equals("帳號") || LogList.getUpCol().substring(0, 2).equals("電話") || LogList.getUpCol().equals("地址") || LogList.getUpCol().equals("受款人身分證號")) {
                        LogList.setAvalue("********");
                        LogList.setBvalue("********");
                    }
                }
            }

            // 塞到 Session 中
            CaseSessionHelper.setSimplifyUpdateLogQueryCase(caseData, request);

            log.debug("執行 查詢作業 - 給付查詢作業(外單位用) - 老年年金更正日誌資料 PaymentQueryAction.doOldAgeSimplifyUpdateLogQueryData() 完成 ... ");

            return mapping.findForward(FORWARD_OLDAGE_UPDATE_LOG_QUERY_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doOldAgeSimplifyUpdateLogQueryData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    // ---------------------------------------- 老年年金 ----------------------------------------

    // ---------------------------------------- 失能年金 ----------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 失能年金明細查詢(案件資料)(baiq0d011q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金明細查詢(案件資料) PaymentQueryAction.doDisabledDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            String apNo = iform.getApNo();
            List<PaymentQueryMasterCase> caseObj = CaseSessionHelper.getPaymentQueryMasterCaseList(request);
            // PaymentQueryDetailDataCase detail = queryService.getPaymentQueryDetail(apNo, "35", ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
            // 20120510 失能有些資料的payKind除了35還有37，會造成sql抓到多筆資料，改成以主檔中的payKind為主傳值進去。
            // PaymentQueryDetailDataCase detail = queryService.getPaymentQueryDetail(apNo, null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);

            PaymentQueryDetailDataCase detail = new PaymentQueryDetailDataCase();
            // PaymentQueryDetailDataCase detail = queryService.getPaymentQueryDetail(apNo, caseObj.get(0).getPayKind(), ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
            for (PaymentQueryMasterCase caseData : caseObj) {
                if (caseData.getApNo().equals(apNo)) {
                    detail = queryService.getPaymentQueryDetail(apNo, caseData.getPayKind(), ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
                    detail.setSecrecy(caseData.getSecrecy());
                }
            }
            if (detail == null) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 查詢作業 - 失能年金明細查詢(案件資料) PaymentQueryAction.doDisabledDetail() 完成 ... ");
                return mapping.findForward(FORWARD_MASTER_LIST);
            }
            else {
                // 起始查詢條件
                PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
                String qryCond = qryForm.getQryCond();
                String startYm = qryForm.getStartYmStr();
                String endYm = qryForm.getEndYmStr();
                String evtName = qryForm.getName();
                String evtIdnNo = qryForm.getIdn();
                String evtBrDate = qryForm.getBrDateStr();

                // 取得 38案加發眷屬補助金額
                if (StringUtils.equals(detail.getPayKind(), "38")) {
                    detail.setBasicAmt(queryService.selectBasicAmtForPaymentQuery(detail.getApNo().substring(0, 1), detail.getPayYm()));
                }

                // 取得 承辦人分機號碼
                detail.setEmpExt(queryService.getEmpExtData(detail.getApNo()));

                // 取得原住民羅馬拼音
                Cvldtl cvldtl = queryService.selectHaddrBy(detail.getEvtIds());
                if (cvldtl != null) {
                    detail.setRmp_Name(cvldtl.getRmp_Name());
                }

                // 取得 XX函註記
                // detail = queryService.getLetterTypeMk(detail);
                List<PaymentQueryLetterTypeMkCase> detail1 = queryService.getLetterTypeMk1ListK(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail2 = queryService.getLetterTypeMk2ListK(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail3 = queryService.getLetterTypeMk3ListK(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail4 = queryService.getLetterTypeMk4ListK(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail5 = queryService.getLetterTypeMk5ListK(detail.getApNo());
                PaymentQueryLetterTypeMkCase detail6 = queryService.getLetterTypeMk6ListK(detail.getApNo());

                // 取得 核定年月資料
                List<Object> mixDataList = queryService.getPaymentQueryIssuPayDataList(apNo, startYm, endYm, qryCond);
                List<Badapr> origIssuPayDataList = (List<Badapr>) mixDataList.get(0);
                List<PaymentQueryIssuPayDataCase> issuPayDataList = (List<PaymentQueryIssuPayDataCase>) mixDataList.get(1);
                // 取得 事故者編審註記資料
                List<PaymentQueryDetailDataCase> chkFileDataList = queryService.getPaymentQueryEvtChkList(detail.getApNo(), "0000");
                // 取得 眷屬編審註記資料
                List<PaymentQueryDetailDataCase> benChkList = queryService.getPaymentQueryOtherChkList(detail.getApNo(), "0000", "B");
                // 取得 符合註記資料
                List<PaymentQueryDetailDataCase> matchChkList = queryService.getPaymentQueryOtherChkList(detail.getApNo(), "0000", "C");
                // 取得 職災相關資料
                PaymentQueryOccAccDataCase occAccData = queryService.getOccAccDataForDisabled(apNo);
                // 取得 失能相關資料
                PaymentQueryDisabledDataCase disabledData = queryService.selectDisabledDataForDisabled(apNo);

                // 取得 核定年月下拉選單
                if (StringUtils.equals("ISSUYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_ISSUYM_OPTION_LIST, queryService.getIssuYmOptionList(origIssuPayDataList));
                }
                // 取得 給付年月下拉選單
                if (StringUtils.equals("PAYYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_PAYYM_OPTION_LIST, queryService.getPayYmOptionList(origIssuPayDataList));
                }
                // 取得 受款人下拉選單
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_SEQNO_OPTION_LIST, queryService.getSeqNoOptionList(origIssuPayDataList));

                // 將 查詢條件/查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentQueryDetailDataCase(detail, request);
                CaseSessionHelper.setPaymentQueryIssuPayDataCaseList(issuPayDataList, request);
                CaseSessionHelper.setPaymentQueryOrigIssuPayDataCaseList(origIssuPayDataList, request);
                CaseSessionHelper.setPaymentQueryEvtChkFileDataCase(chkFileDataList, request);
                CaseSessionHelper.setPaymentQueryBenChkFileDataCase(benChkList, request);
                CaseSessionHelper.setPaymentQueryMatchChkFileDataCase(matchChkList, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk6List(detail6, request);
                CaseSessionHelper.setPaymentQueryOccAccDataCase(occAccData, request);
                CaseSessionHelper.setPaymentQueryDisabledCase(disabledData, request);

                log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金明細查詢(案件資料) PaymentQueryAction.doDisabledDetail() 完成 ... ");
                return mapping.findForward(FORWARD_DISABLED_DETAIL_DATA);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能年金案件資料(baiq0d112q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledApplicationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金案件資料 PaymentQueryAction.doDisabledApplicationData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {

            String apNo = iform.getApNo();
            List<PaymentQueryMasterCase> caseObj = CaseSessionHelper.getPaymentQueryMasterCaseList(request);
            // PaymentQueryDetailDataCase detail = queryService.getPaymentQueryDetail(apNo, "35", ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
            // 20120510 失能有些資料的payKind除了35還有37，會造成sql抓到多筆資料，改成以主檔中的payKind為主傳值進去。
            // PaymentQueryDetailDataCase detail = queryService.getPaymentQueryDetail(apNo, null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
            PaymentQueryDetailDataCase detail = new PaymentQueryDetailDataCase();
            for (PaymentQueryMasterCase caseData : caseObj) {
                if (caseData.getApNo().equals(apNo)) {
                    detail = queryService.getPaymentQueryDetail(apNo, caseData.getPayKind(), ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);
                    detail.setSecrecy(caseData.getSecrecy());
                }
            }
            // 案件資料
            // PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 取得 案件資料
            // PaymentQueryDetailDataCase detail = queryService.getPaymentQueryDetail(caseObj.getApNo(), "35", ConstantKey.BAAPPBASE_PAGE_PAYKIND_K);

            if (detail == null) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 查詢作業 - 給付查詢作業 - 老年年金案件資料 PaymentQueryAction.doDisabledApplicationData() 完成 ... ");
                return mapping.findForward(FORWARD_MASTER_LIST);
            }
            else {
                // 起始查詢條件
                PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
                String qryCond = qryForm.getQryCond();
                String startYm = qryForm.getStartYmStr();
                String endYm = qryForm.getEndYmStr();
                String evtName = qryForm.getName();
                String evtIdnNo = qryForm.getIdn();
                String evtBrDate = qryForm.getBrDateStr();

                // 取得 承辦人分機號碼
                detail.setEmpExt(queryService.getEmpExtData(detail.getApNo()));

                // 取得 XX函註記
                // detail = queryService.getLetterTypeMk(detail);
                List<PaymentQueryLetterTypeMkCase> detail1 = queryService.getLetterTypeMk1List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail2 = queryService.getLetterTypeMk2List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail3 = queryService.getLetterTypeMk3List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail4 = queryService.getLetterTypeMk4List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail5 = queryService.getLetterTypeMk5List(detail.getApNo());
                PaymentQueryLetterTypeMkCase detail6 = queryService.getLetterTypeMk6List(detail.getApNo());

                // 取得 核定年月資料
                List<Object> mixDataList = queryService.getPaymentQueryIssuPayDataList(detail.getApNo(), startYm, endYm, qryCond);
                List<Badapr> origIssuPayDataList = (List<Badapr>) mixDataList.get(0);
                List<PaymentQueryIssuPayDataCase> issuPayDataList = (List<PaymentQueryIssuPayDataCase>) mixDataList.get(1);

                // 取得 事故者編審註記資料
                List<PaymentQueryDetailDataCase> chkFileDataList = queryService.getPaymentQueryEvtChkList(detail.getApNo(), "0000");
                // 取得 眷屬編審註記資料
                List<PaymentQueryDetailDataCase> benChkList = queryService.getPaymentQueryOtherChkList(detail.getApNo(), "0000", "B");
                // 取得 符合註記資料
                List<PaymentQueryDetailDataCase> matchChkList = queryService.getPaymentQueryOtherChkList(detail.getApNo(), "0000", "C");
                // 取得 職災相關資料
                PaymentQueryOccAccDataCase occAccData = queryService.getOccAccDataForDisabled(detail.getApNo());
                // 取得 失能相關資料
                PaymentQueryDisabledDataCase disabledData = queryService.selectDisabledDataForDisabled(detail.getApNo());

                // 取得 核定年月下拉選單
                if (StringUtils.equals("ISSUYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_ISSUYM_OPTION_LIST, queryService.getIssuYmOptionList(origIssuPayDataList));
                }
                // 取得 給付年月下拉選單
                if (StringUtils.equals("PAYYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_PAYYM_OPTION_LIST, queryService.getPayYmOptionList(origIssuPayDataList));
                }
                // 取得 受款人下拉選單
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_SEQNO_OPTION_LIST, queryService.getSeqNoOptionList(origIssuPayDataList));

                // 將 查詢條件/查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentQueryDetailDataCase(detail, request);
                CaseSessionHelper.setPaymentQueryIssuPayDataCaseList(issuPayDataList, request);
                CaseSessionHelper.setPaymentQueryOrigIssuPayDataCaseList(origIssuPayDataList, request);
                CaseSessionHelper.setPaymentQueryEvtChkFileDataCase(chkFileDataList, request);
                CaseSessionHelper.setPaymentQueryBenChkFileDataCase(benChkList, request);
                CaseSessionHelper.setPaymentQueryMatchChkFileDataCase(matchChkList, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk6List(detail6, request);
                CaseSessionHelper.setPaymentQueryOccAccDataCase(occAccData, request);
                CaseSessionHelper.setPaymentQueryDisabledCase(disabledData, request);

                log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金案件資料 PaymentQueryAction.doDisabledApplicationData() 完成 ... ");
                return mapping.findForward(FORWARD_DISABLED_DETAIL_DATA);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledApplicationData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能年金受款人資料(含核定/編審資料) (baiq0d112q01.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledBenData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金受款人資料 PaymentQueryAction.doDisabledBenData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 起始查詢條件
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 取得 受款人資料
            List<PaymentQueryBenDataCase> benDataList = queryService.getBeneficiaryData(caseObj.getApNo(), qryCond, startYm, endYm);
            
            //Added By LouisChange 20200311 begin
            //添加受款人資料之原住民羅馬拼音
            //ba_1090122544 需求  : 以事故者身分證及生日去搜尋當戶政全戶檔, 若只有一筆資料且該筆資料原住名身分代碼(OlivCode)為1或2時, 原住民羅馬拼音(Rmp_Name)有值則顯示
            if (CollectionUtils.isNotEmpty(benDataList)) {
            	for (PaymentQueryBenDataCase benData : benDataList) {
	            	List<Cvldtl> cvldtls = queryService.selectRmpNameBy(benData.getBenIdnNo(), benData.getBenBrDate());
	            	if (CollectionUtils.isNotEmpty(cvldtls) && cvldtls.size() == 1) {
	            		Cvldtl cvldtl = cvldtls.get(0);
	            		if (cvldtl != null && ("1".equals(cvldtl.getOlivCode()) || "2".equals(cvldtl.getOlivCode()))) {
	            			benData.setRmp_Name(cvldtl.getRmp_Name());
	            		}
	            	}
            	}
            }
            //Added By LouisChange 20200311 end

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryBenDataCaseList(benDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金受款人資料 PaymentQueryAction.doDisabledBenData() 完成 ... ");
            return mapping.findForward(FORWARD_DISABLED_BEN_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能年金眷屬資料(baiq0d112q02.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledFamilyData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金眷屬資料 PaymentQueryAction.doDisabledFamilyData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 起始查詢條件
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 取得 眷屬資料
            List<PaymentQueryFamilyDataCase> familyList = queryService.getFamilyDataForDisabled(caseObj.getApNo());

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryFamilyDataList(familyList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金眷屬資料 PaymentQueryAction.doDisabledFamilyData() 完成 ... ");
            return mapping.findForward(FORWARD_DISABLED_FAMILY_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledFamilyData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 複檢費用資料(baiq0d112q03.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledReFeesMasterDataList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 複檢費用資料 PaymentQueryAction.doDisabledReFeesMasterDataList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            // 取得 複檢費用資料
            List<PaymentQueryReFeesMasterDataCase> refeesMasterList = queryService.getReFeesMasterDataForDisabled(caseObj.getApNo());
            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryReFeesMasterDataList(refeesMasterList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 複檢費用資料 PaymentQueryAction.doDisabledReFeesMasterDataList() 完成 ... ");
            return mapping.findForward(FORWARD_DISABLED_REFEES_MASTER_DATA_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledReFeesMasterDataList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 複檢費用明細資料(baiq0d112q05.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledReFeesDetailData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 複檢費用明細資料 PaymentQueryAction.doDisabledReFeesDetailData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 取得 複檢費用明細資料
            PaymentQueryReFeesDetailDataCase refeesDetail = queryService.getReFeesDetailDataForDisabled(iform.getReApNo(), iform.getApSeq(), iform.getApNo());
            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryReFeesDetailDataCase(refeesDetail, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 複檢費用明細資料 PaymentQueryAction.doDisabledReFeesDetailData() 完成 ... ");
            return mapping.findForward(FORWARD_DISABLED_REFEES_DETAIL_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledReFeesDetailData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能年金受款人在學資料 (baiq0d212q06.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledStudDetailData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金受款人在學資料 PaymentQueryAction.doDisabledStudDetailData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 取得 受款人在學資料
            List<PaymentQueryFamilyDataCase> studDetailDataList = queryService.getStudDetailDataForDisabled(iform.getApNo(), iform.getSeqNo());

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryDisabledStudDetailDataCaseList(studDetailDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金受款人在學資料 PaymentQueryAction.doDisabledStudDetailData() 完成 ... ");
            return mapping.findForward(FORWARD_DISABLED_STUD_DETAIL_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledStudDetailData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_DISABLED_FAMILY_DATA);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能年金紓困貸款(baiq0d112q07.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledLoanData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金紓困貸款 PaymentQueryAction.doDisabledLoanData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 起始查詢條件
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 最新紓困貸款資料
            /**
             * 20180411 當給付主檔（BAAPPBASE）之勞貸戶註記（LSCHKMK）= 4-最後一次扣減紓困貸款土銀已回覆時， 
             * 改讀取紓困貸款回覆記錄檔（BALASTREPLY）相對應欄位資料顯示。
             */
            PaymentQueryLoanDataCase loanMaster = new PaymentQueryLoanDataCase();
            if (caseObj.getLsChkMk().equals("4")) {
                loanMaster = queryService.getPaymentQueryLoanMasterDataForLsChkMk4(caseObj.getApNo(), "35,36,38");
            }
            else {
                loanMaster = queryService.getPaymentQueryLoanMasterData(caseObj.getApNo(), "35,36,38");
            }

            // 紓困貸款 核定資料
            List<PaymentQueryLoanDataCase> loanDetailList = queryService.getPaymentQueryLoanDetailData(caseObj.getApNo(), qryCond, startYm, endYm, "35,36,38");

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryLoanMasterDataCase(loanMaster, request);
            CaseSessionHelper.setPaymentQueryLoanDetailDataCaseList(loanDetailList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金紓困貸款 PaymentQueryAction.doDisabledLoanData() 完成 ... ");
            return mapping.findForward(FORWARD_DISABLED_LOAN_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledLoanData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能年金平均薪資(baiq0d112q08.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledAvgAmtData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金平均薪資 PaymentQueryAction.doDisabledAvgAmtData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;
        // 清除 60個月平均薪資資料
        CaseSessionHelper.removePaymentQuerySixtyMonAvgAmtDataCaseList(request);
        try {
            // 起始查詢條件
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 年資資料
            PaymentQueryAvgAmtDataCase avgAmtSeniData = queryService.getPaymentQueryAvgAmtSeniData(caseObj.getApNo(), "35,36,38");

            // 身分證重號處理
            String evtIdnNo = queryService.getDupeIdnNoMkIdn(caseObj.getApNo(), caseObj.getDupeIdnNoMk(), caseObj.getEvtIdnNo());
            // 取得 查詢條件 平均薪資月數
            String appMonth = queryService.getBapaavgmonAppMonth(caseObj.getAppDate());
            // 60個月平均薪資資料
            String realAvgMon = "";
            List<PaymentQueryAvgAmtDataCase> sixtyMonAvgAmtDataList = new ArrayList<PaymentQueryAvgAmtDataCase>();

            if (StringUtils.isNotBlank(avgAmtSeniData.getEvtIdnNo())) {

                if ((StringUtils.equals(ConstantKey.BAAPPEXPAND_EVTYP_3, avgAmtSeniData.getEvTyp()) || StringUtils.equals(ConstantKey.BAAPPEXPAND_EVTYP_4, avgAmtSeniData.getEvTyp())) && !StringUtils.equals("Y", avgAmtSeniData.getPrType())
                                || StringUtils.isBlank(avgAmtSeniData.getPrType())) {
                    sixtyMonAvgAmtDataList = queryService.getPaymentQuerySixtyMonAvgAmtData(caseObj.getApNo(), "0000", evtIdnNo, "1", appMonth);
                    realAvgMon = queryService.getRealAvgMonBy(caseObj.getApNo(), "0000", evtIdnNo, "1");
                }
                else {
                    sixtyMonAvgAmtDataList = queryService.getPaymentQuerySixtyMonAvgAmtData(caseObj.getApNo(), "0000", evtIdnNo, "6", appMonth);
                    realAvgMon = queryService.getRealAvgMonBy(caseObj.getApNo(), "0000", evtIdnNo, "6");
                }
                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentQuerySixtyMonAvgAmtDataCaseList(sixtyMonAvgAmtDataList, request);
                avgAmtSeniData.setRealAvgMon(realAvgMon); // 放入 實際均薪月數
            }
            // 取得 畫面上顯示實際均薪月數：XXX個月
            avgAmtSeniData.setAppMonth(appMonth); // 放入 平均薪資月數

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryAvgAmtSeniDataCase(avgAmtSeniData, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金平均薪資 PaymentQueryAction.doDisabledAvgAmtData() 完成 ... ");
            return mapping.findForward(FORWARD_DISABLED_AVGAMT_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledLoanData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能年金物價指數調整(baiq0d112q09.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledCpiData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金物價指數調整 PaymentQueryAction.doDisabledCpiData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            String payCode = caseObj.getApNo1();
            // 物價指數調整資料
            List<PaymentQueryCpiDataCase> cpiDataList = queryService.getCpiDataForPaymentQuery(caseObj.getApNo(), payCode);

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryCpiDataCaseList(cpiDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金物價指數調整 PaymentQueryAction.doDisabledCpiData() 完成 ... ");
            return mapping.findForward(FORWARD_DISABLED_CPI_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledCpiData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能年金物價指數調整記錄資料(baiq0d112q10.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledCpiRecData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金物價指數調整記錄資料 PaymentQueryAction.doDisabledCpiRecData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 物價指數調整記錄資料
            List<PaymentQueryCpiDataCase> cpiRecDataList = queryService.getCpiRecForPaymentQuery();

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryCpiRecDataCaseList(cpiRecDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金物價指數調整記錄資料 PaymentQueryAction.doDisabledCpiRecData() 完成 ... ");
            return mapping.findForward(FORWARD_DISABLED_CPIREC_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledCpiRecData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能年金 重新查核失能程度資料(baiq0d112q13.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledRehcData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金 重新查核失能程度資料資料 PaymentQueryAction.doDisabledRehcData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            DisabledApplicationDataUpdateCase caseDate = queryService.getDisabledApplicationDataUpdateMasterDataForRehcBy(caseObj.getApNo());

            // 更新頁面List資料
            List<DisabledApplicationDataUpdateBareCheckCase> bareCheckDataListAfter = queryService.selectBareCheckDataBy(caseObj.getApNo(), caseObj.getSeqNo());

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setDisabledApplicationDataUpdateCase(caseDate, request);
            CaseSessionHelper.setDisabledApplicationDataUpdateBareCheckCaseList(bareCheckDataListAfter, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金 重新查核失能程度資料資料 PaymentQueryAction.doDisabledRehcData() 完成 ... ");
            return mapping.findForward(FORWARD_DISABLED_REHC_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledRehcData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能年金歸檔記錄資料(baiq0d012q11.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledArclistData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 歸檔記錄資料 PaymentQueryAction.doDiabledArclistData() 開始 ... ");
        HttpSession session = request.getSession();
        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 歸檔記錄資料
            PaymentQueryArclistDataCase arclistCase = queryService.getArclistDataForPaymentQuery(caseObj.getApNo());

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryArclistDataCase(arclistCase, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 歸檔記錄資料 PaymentQueryAction.doDisabledArclistData() 完成 ... ");
            return mapping.findForward(FORWARD_DISABLED_ARCLIST_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledArclistData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能年金更正日誌資料(baiq0d112q12.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */

    public ActionForward doDisabledUpdateLogQueryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金更正日誌資料 PaymentQueryAction.doDisabledUpdateLogQueryData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeUpdateLogQueryCase(request);

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            String updateDateBegin = "20090101";
            String updateDateEnd = DateUtil.getNowWestDate();
            String apNo = caseObj.getApNo();
            String payCode = caseObj.getApNo1();
            // 取得 更正日誌 資料
            UpdateLogQueryCase caseData = queryService.getUpdateLogQueryCaseBy(payCode, updateDateBegin, updateDateEnd, apNo, null, null);

            // 塞到 Session 中
            CaseSessionHelper.setUpdateLogQueryCase(caseData, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金更正日誌資料 PaymentQueryAction.doDisabledUpdateLogQueryData() 完成 ... ");

            return mapping.findForward(FORWARD_DISABLED_UPDATE_LOG_QUERY_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledUpdateLogQueryData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業(外單位用) - 失能年金更正日誌資料(baiq0d162q12.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */

    public ActionForward doDisabledSimplifyUpdateLogQueryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業(外單位用) - 失能年金更正日誌資料 PaymentQueryAction.doDisabledSimplifyUpdateLogQueryData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeSimplifyUpdateLogQueryCase(request);

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            String updateDateBegin = "20090101";
            String updateDateEnd = DateUtil.getNowWestDate();
            String apNo = caseObj.getApNo();
            String payCode = caseObj.getApNo1();
            // 取得 更正日誌 資料
            UpdateLogQueryCase caseData = queryService.getUpdateLogQueryCaseBy(payCode, updateDateBegin, updateDateEnd, apNo, null, null);

            // 隱藏隱私資料
            List<UpdateLogQueryDetailCase> updateLogList = caseData.getUpdateLogList();

            for (UpdateLogQueryDetailCase LogList : updateLogList) {

                if (!StringUtils.isBlank(LogList.getUpdUser())) {
                    LogList.setUpdUser("*****");
                }
                if (!StringUtils.isBlank(LogList.getUpCol())) {
                    if (LogList.getUpCol().substring(0, 2).equals("帳號") || LogList.getUpCol().substring(0, 2).equals("電話") || LogList.getUpCol().equals("地址") || LogList.getUpCol().equals("受款人身分證號")) {
                        LogList.setAvalue("********");
                        LogList.setBvalue("********");
                    }
                }
            }

            // 塞到 Session 中
            CaseSessionHelper.setSimplifyUpdateLogQueryCase(caseData, request);

            log.debug("執行 查詢作業 - 給付查詢作業(外單位用) - 失能年金更正日誌資料 PaymentQueryAction.doDisabledSimplifyUpdateLogQueryData() 完成 ... ");

            return mapping.findForward(FORWARD_DISABLED_UPDATE_LOG_QUERY_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledSimplifyUpdateLogQueryData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 國保資料(baiq0d112q04.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDisabledNpData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金國保資料 PaymentQueryAction.doDisabledNpData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removePaymentQueryNpDataCase(request);

            // 國保資料
            PaymentQueryDetailDataCase detail = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            PaymentQueryNpDataCase npDataCase = queryService.getPaymentQueryDisabledNpData(detail.getMapNo());

            // 無mapNo 頁面訊息 並無關聯國保資料的受理編號。
            if (StringUtils.isBlank(detail.getMapNo())) {
                npDataCase.setMapNoStr("");
            }
            // 國保年資 如為0 不帶入
            if (npDataCase.getValSeni() != null && npDataCase.getValSenid() != null) {
                if (npDataCase.getValSeni().compareTo(new BigDecimal("0")) != 0 && npDataCase.getValSenid().compareTo(new BigDecimal("0")) != 0) {
                    npDataCase.setValSeniSrt(npDataCase.getValSeni() + "月" + npDataCase.getValSenid() + "日");
                }
                else if (npDataCase.getValSeni().compareTo(new BigDecimal("0")) != 0 && npDataCase.getValSenid().compareTo(new BigDecimal("0")) == 0) {
                    npDataCase.setValSeniSrt(npDataCase.getValSeni() + "月");
                }
                else if (npDataCase.getValSeni().compareTo(new BigDecimal("0")) == 0 && npDataCase.getValSenid().compareTo(new BigDecimal("0")) != 0) {
                    npDataCase.setValSeniSrt(npDataCase.getValSenid() + "日");
                }
                else {
                    npDataCase.setValSeniSrt("");
                }
            }

            // 轉換國保年資 20140402 修改直接放月日欄位
            // if(npDataCase.getValSeni() != null){
            // int valSeni = npDataCase.getValSeni().intValue();
            // String nYear = "";
            // String nMonth = "";
            // if (valSeni > 0 && valSeni <= 12){
            // nMonth = String.valueOf(valSeni);
            // npDataCase.setValSeniSrt(nMonth+"月");
            // }else if(valSeni % 12 == 0){
            // nYear = String.valueOf(valSeni/12);
            // npDataCase.setValSeniSrt(nYear+"年");
            // }else if(valSeni % 12 != 0){
            // nYear = String.valueOf(valSeni/12);
            // nMonth = String.valueOf(valSeni % 12);
            // npDataCase.setValSeniSrt(nYear+"年"+nMonth+"月");
            // }
            // }

            List<Nbdapr> origIssuDataList = new ArrayList<Nbdapr>();
            List<PaymentQueryNpIssuDataCase> issuDataList = new ArrayList<PaymentQueryNpIssuDataCase>();
            if (npDataCase != null) {
                List<Object> list = queryService.getPaymentQueryDisabledNpIssuData(detail.getMapNo(), qryCond, startYm, endYm);

                origIssuDataList = (List<Nbdapr>) list.get(0);
                issuDataList = (List<PaymentQueryNpIssuDataCase>) list.get(1);

                // 取得 核定年月下拉選單
                if (StringUtils.equals("ISSUYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_NPDATA_ISSUYM_OPTION_LIST, queryService.getNpDataIssuPayYmOptionList(qryCond, issuDataList));
                }
                // 取得 給付年月下拉選單
                if (StringUtils.equals("PAYYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_NPDATA_PAYYM_OPTION_LIST, queryService.getNpDataIssuPayYmOptionList(qryCond, issuDataList));
                }
            }

            // 塞到 Session 中
            CaseSessionHelper.setPaymentQueryNpDataCase(npDataCase, request);
            CaseSessionHelper.setPaymentQueryOrigNpIssuDataList(origIssuDataList, request);
            CaseSessionHelper.setPaymentQueryNpIssuDataList(issuDataList, request);

            List<PaymentQueryNpIssuDataCase> tmpList = CaseSessionHelper.getPaymentQueryNpIssuDataList(request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 失能年金國保資料 PaymentQueryAction.doDisabledNpData() 完成 ... ");

            return mapping.findForward(FORWARD_DISABLED_NP_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledNpData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    // ---------------------------------------- 失能年金 ----------------------------------------

    // ---------------------------------------- 遺屬年金 ----------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金明細查詢(案件資料)(baiq0d011q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金明細查詢(案件資料) PaymentQueryAction.doSurvivorDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            String apNo = iform.getApNo();
            // PaymentQueryDetailDataCase detail = queryService.getPaymentQueryDetail(apNo, null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);
            List<PaymentQueryMasterCase> caseObj = CaseSessionHelper.getPaymentQueryMasterCaseList(request);

            PaymentQueryDetailDataCase detail = new PaymentQueryDetailDataCase();

            for (PaymentQueryMasterCase caseData : caseObj) {
                if (caseData.getApNo().equals(apNo)) {
                    detail = queryService.getPaymentQueryDetail(apNo, null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);
                    detail.setSecrecy(caseData.getSecrecy());
                }
            }

            if (detail == null) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 查詢作業 - 遺屬年金明細查詢(案件資料) PaymentQueryAction.doSurvivorDetail() 完成 ... ");
                return mapping.findForward(FORWARD_MASTER_LIST);
            }
            else {
                // 起始查詢條件
                PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
                String qryCond = qryForm.getQryCond();
                String startYm = qryForm.getStartYmStr();
                String endYm = qryForm.getEndYmStr();
                String evtName = qryForm.getName();
                String evtIdnNo = qryForm.getIdn();
                String evtBrDate = qryForm.getBrDateStr();

                // 取得 承辦人分機號碼
                detail.setEmpExt(queryService.getEmpExtData(detail.getApNo()));

                // 取得原住民羅馬拼音
                Cvldtl cvldtl = queryService.selectHaddrBy(detail.getEvtIds());
                if (cvldtl != null) {
                    detail.setRmp_Name(cvldtl.getRmp_Name());
                }

                // 取得 XX函註記
                // detail = queryService.getLetterTypeMk(detail);
                List<PaymentQueryLetterTypeMkCase> detail1 = queryService.getLetterTypeMk1ListS(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail2 = queryService.getLetterTypeMk2ListS(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail3 = queryService.getLetterTypeMk3ListS(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail4 = queryService.getLetterTypeMk4ListS(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail5 = queryService.getLetterTypeMk5ListS(detail.getApNo());
                PaymentQueryLetterTypeMkCase detail6 = queryService.getLetterTypeMk6ListS(detail.getApNo());

                // 取得 核定年月資料
                List<Object> mixDataList = queryService.getPaymentQueryIssuPayDataList(apNo, startYm, endYm, qryCond);
                List<Badapr> origIssuPayDataList = (List<Badapr>) mixDataList.get(0);
                List<PaymentQueryIssuPayDataCase> issuPayDataList = (List<PaymentQueryIssuPayDataCase>) mixDataList.get(1);
                // 取得 事故者編審註記資料
                List<PaymentQueryDetailDataCase> chkFileDataList = queryService.getPaymentQueryEvtChkListForSurvivor(detail.getApNo(), "0000");
                // 取得 眷屬編審註記資料
                List<PaymentQueryDetailDataCase> benChkList = queryService.getPaymentQueryOtherChkListForSurvivor(detail.getApNo(), "B");
                // 取得 符合註記資料
                List<PaymentQueryDetailDataCase> matchChkList = queryService.getPaymentQueryOtherChkListForSurvivor(detail.getApNo(), "C");
                // 取得 失能相關資料
                PaymentQueryDisabledDataCase disabledData = queryService.selectDisabledDataForDisabled(detail.getApNo());

                // 取得 核定年月下拉選單
                if (StringUtils.equals("ISSUYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_ISSUYM_OPTION_LIST, queryService.getIssuYmOptionList(origIssuPayDataList));
                }
                // 取得 給付年月下拉選單
                if (StringUtils.equals("PAYYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_PAYYM_OPTION_LIST, queryService.getPayYmOptionList(origIssuPayDataList));
                }
                // 取得 受款人下拉選單
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_SEQNO_OPTION_LIST, queryService.getSeqNoOptionList(origIssuPayDataList));

                // 將 查詢條件/查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentQueryDetailDataCase(detail, request);
                CaseSessionHelper.setPaymentQueryIssuPayDataCaseList(issuPayDataList, request);
                CaseSessionHelper.setPaymentQueryOrigIssuPayDataCaseList(origIssuPayDataList, request);
                CaseSessionHelper.setPaymentQueryEvtChkFileDataCase(chkFileDataList, request);
                CaseSessionHelper.setPaymentQueryBenChkFileDataCase(benChkList, request);
                CaseSessionHelper.setPaymentQueryMatchChkFileDataCase(matchChkList, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk6List(detail6, request);
                CaseSessionHelper.setPaymentQueryDisabledCase(disabledData, request);

                log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金明細查詢(案件資料) PaymentQueryAction.doSurvivorDetail() 完成 ... ");
                return mapping.findForward(FORWARD_SURVIVOR_DETAIL_DATA);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金案件資料(baiq0d112q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorApplicationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金案件資料 PaymentQueryAction.doSurvivorApplicationData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            String apNo = iform.getApNo();

            List<PaymentQueryMasterCase> caseObj = CaseSessionHelper.getPaymentQueryMasterCaseList(request);

            PaymentQueryDetailDataCase detail = new PaymentQueryDetailDataCase();

            for (PaymentQueryMasterCase caseData : caseObj) {
                if (caseData.getApNo().equals(apNo)) {
                    detail = queryService.getPaymentQueryDetail(apNo, null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);
                    detail.setSecrecy(caseData.getSecrecy());
                }
            }
            // 案件資料
            // PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 取得 案件資料
            // PaymentQueryDetailDataCase detail = queryService.getPaymentQueryDetail(caseObj.getApNo(), null, ConstantKey.BAAPPBASE_PAGE_PAYKIND_S);

            if (detail == null) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金案件資料 PaymentQueryAction.doSurvivorApplicationData() 完成 ... ");
                return mapping.findForward(FORWARD_MASTER_LIST);
            }
            else {
                // 起始查詢條件
                PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
                String qryCond = qryForm.getQryCond();
                String startYm = qryForm.getStartYmStr();
                String endYm = qryForm.getEndYmStr();
                String evtName = qryForm.getName();
                String evtIdnNo = qryForm.getIdn();
                String evtBrDate = qryForm.getBrDateStr();

                // 取得 承辦人分機號碼
                detail.setEmpExt(queryService.getEmpExtData(detail.getApNo()));

                // 取得 XX函註記
                // detail = queryService.getLetterTypeMk(detail);
                List<PaymentQueryLetterTypeMkCase> detail1 = queryService.getLetterTypeMk1List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail2 = queryService.getLetterTypeMk2List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail3 = queryService.getLetterTypeMk3List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail4 = queryService.getLetterTypeMk4List(detail.getApNo());
                List<PaymentQueryLetterTypeMkCase> detail5 = queryService.getLetterTypeMk5List(detail.getApNo());
                PaymentQueryLetterTypeMkCase detail6 = queryService.getLetterTypeMk6List(detail.getApNo());

                // 取得 核定年月資料
                List<Object> mixDataList = queryService.getPaymentQueryIssuPayDataList(detail.getApNo(), startYm, endYm, qryCond);
                List<Badapr> origIssuPayDataList = (List<Badapr>) mixDataList.get(0);
                List<PaymentQueryIssuPayDataCase> issuPayDataList = (List<PaymentQueryIssuPayDataCase>) mixDataList.get(1);
                // 取得 事故者編審註記資料
                List<PaymentQueryDetailDataCase> chkFileDataList = queryService.getPaymentQueryEvtChkListForSurvivor(detail.getApNo(), "0000");
                // 取得 眷屬編審註記資料
                List<PaymentQueryDetailDataCase> benChkList = queryService.getPaymentQueryOtherChkListForSurvivor(detail.getApNo(), "B");
                // 取得 符合註記資料
                List<PaymentQueryDetailDataCase> matchChkList = queryService.getPaymentQueryOtherChkListForSurvivor(detail.getApNo(), "C");
                // 取得 失能相關資料
                PaymentQueryDisabledDataCase disabledData = queryService.selectDisabledDataForDisabled(detail.getApNo());

                // 取得 核定年月下拉選單
                if (StringUtils.equals("ISSUYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_ISSUYM_OPTION_LIST, queryService.getIssuYmOptionList(origIssuPayDataList));
                }
                // 取得 給付年月下拉選單
                if (StringUtils.equals("PAYYM", qryCond)) {
                    request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_PAYYM_OPTION_LIST, queryService.getPayYmOptionList(origIssuPayDataList));
                }
                // 取得 受款人下拉選單
                request.getSession().setAttribute(ConstantKey.PAYMENT_QUERY_SEQNO_OPTION_LIST, queryService.getSeqNoOptionList(origIssuPayDataList));

                // 將 查詢條件/查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentQueryDetailDataCase(detail, request);
                CaseSessionHelper.setPaymentQueryIssuPayDataCaseList(issuPayDataList, request);
                CaseSessionHelper.setPaymentQueryOrigIssuPayDataCaseList(origIssuPayDataList, request);
                CaseSessionHelper.setPaymentQueryEvtChkFileDataCase(chkFileDataList, request);
                CaseSessionHelper.setPaymentQueryBenChkFileDataCase(benChkList, request);
                CaseSessionHelper.setPaymentQueryMatchChkFileDataCase(matchChkList, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk1List(detail1, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk2List(detail2, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk3List(detail3, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk4List(detail4, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk5List(detail5, request);
                CaseSessionHelper.setPaymentQueryLetterTypeMk6List(detail6, request);
                CaseSessionHelper.setPaymentQueryDisabledCase(disabledData, request);

                log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金案件資料 PaymentQueryAction.doSurvivorApplicationData() 完成 ... ");
                return mapping.findForward(FORWARD_SURVIVOR_DETAIL_DATA);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doSurvivorApplicationData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金受款人資料(含核定/編審資料) (baiq0d212q01.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorBenData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金受款人資料 PaymentQueryAction.doSurvivorBenData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 起始查詢條件
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            // 取得 受款人資料
            List<PaymentQueryBenDataCase> benDataList = queryService.getBeneficiaryDataForSurvivor(caseObj.getApNo(), qryCond, startYm, endYm);
            
            //Added By LouisChange 20200311 begin
            //添加受款人資料之原住民羅馬拼音
            //ba_1090122544 需求  : 以事故者身分證及生日去搜尋當戶政全戶檔, 若只有一筆資料且該筆資料原住名身分代碼(OlivCode)為1或2時, 原住民羅馬拼音(Rmp_Name)有值則顯示
            if (CollectionUtils.isNotEmpty(benDataList)) {
            	for (PaymentQueryBenDataCase benData : benDataList) {
            		if (!"0000".equals(benData.getSeqNo())) {            			
            			List<Cvldtl> cvldtls = queryService.selectRmpNameBy(benData.getBenIdnNo(), benData.getBenBrDate());
            			if (CollectionUtils.isNotEmpty(cvldtls) && cvldtls.size() == 1) {
            				Cvldtl cvldtl = cvldtls.get(0);
            				if (cvldtl != null && ("1".equals(cvldtl.getOlivCode()) || "2".equals(cvldtl.getOlivCode()))) {
            					benData.setRmp_Name(cvldtl.getRmp_Name());
            				}
            			}
            		}
            	}
            }
            //Added By LouisChange 20200311 end

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryBenDataCaseList(benDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金受款人資料 PaymentQueryAction.doSurvivorBenData() 完成 ... ");
            return mapping.findForward(FORWARD_SURVIVOR_BEN_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doSurvivorBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SURVIVOR_DETAIL_DATA);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金受款人在學資料 (baiq0d212q02.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorStudDetailData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金受款人在學資料 PaymentQueryAction.doSurvivorStudDetailData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 取得 受款人在學資料
            List<PaymentQueryBenDataCase> studDetailDataList = queryService.getStudDetailDataForSurvivor(iform.getApNo(), iform.getSeqNo());

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryStudDetailDataCaseList(studDetailDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金受款人在學資料 PaymentQueryAction.doSurvivorStudDetailData() 完成 ... ");
            return mapping.findForward(FORWARD_SURVIVOR_STUD_DETAIL_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doSurvivorBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SURVIVOR_BEN_DATA);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金受款人重殘資料 (baiq0d212q11.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorHandicapDetailData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金受款人重殘資料 PaymentQueryAction.doSurvivorHandicapDetailData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 取得 受款人重殘資料
            List<PaymentQueryBenDataCase> handicapDetailDataList = queryService.getHandicapDetailDataForSurvivor(iform.getApNo(), iform.getSeqNo());

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryHandicapDetailDataCaseList(handicapDetailDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金受款人重殘資料 PaymentQueryAction.doSurvivorHandicapDetailData() 完成 ... ");
            return mapping.findForward(FORWARD_SURVIVOR_HANDICAP_DETAIL_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doSurvivorHandicapDetailData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SURVIVOR_BEN_DATA);
        }
    }
    
    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金紓困貸款(baiq0d212q04.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorLoanData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金紓困貸款 PaymentQueryAction.doSurvivorLoanData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 起始查詢條件
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 最新紓困貸款資料
            /**
             * 20180411 當給付主檔（BAAPPBASE）之勞貸戶註記（LSCHKMK）= 4-最後一次扣減紓困貸款土銀已回覆時， 
             * 改讀取紓困貸款回覆記錄檔（BALASTREPLY）相對應欄位資料顯示。
             */
            PaymentQueryLoanDataCase loanMaster = new PaymentQueryLoanDataCase();
            if (caseObj.getLsChkMk().equals("4")) {
                loanMaster = queryService.getPaymentQueryLoanMasterDataForLsChkMk4(caseObj.getApNo(), null);
            }
            else {
                loanMaster = queryService.getPaymentQueryLoanMasterData(caseObj.getApNo(), null);
            }

            // 紓困貸款 核定資料
            List<PaymentQueryLoanDataCase> loanDetailList = queryService.getPaymentQueryLoanDetailData(caseObj.getApNo(), qryCond, startYm, endYm, null);

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryLoanMasterDataCase(loanMaster, request);
            CaseSessionHelper.setPaymentQueryLoanDetailDataCaseList(loanDetailList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金紓困貸款 PaymentQueryAction.doSurvivorLoanData() 完成 ... ");
            return mapping.findForward(FORWARD_SURVIVOR_LOAN_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledLoanData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金平均薪資(baiq0d212q03.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorAvgAmtData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金平均薪資 PaymentQueryAction.doSurvivorAvgAmtData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;
        CaseSessionHelper.removePaymentQuerySixtyMonAvgAmtDataCaseList(request);
        try {
            // 起始查詢條件
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 年資資料
            PaymentQueryAvgAmtDataCase avgAmtSeniData = queryService.getPaymentQueryAvgAmtSeniData(caseObj.getApNo(), null);
            // 取得 查詢條件 平均薪資月數
            String appMonth = "";
            // 他案受理編號 身分證號
            String dabapNo = "";
            String evtIdnNo = "";

            //
            if (caseObj.getApItem().equals("7") || caseObj.getApItem().equals("8")) {
                PaymentQueryDetailDataCase detail = queryService.selectDataForSurvivorAvgAmtDetail(caseObj.getDabApNo());
                if (detail != null) {
                    appMonth = queryService.getBapaavgmonAppMonth(detail.getAppDate());
                    dabapNo = detail.getApNo();
                    // 身分證重號處理
                    evtIdnNo = queryService.getDupeIdnNoMkIdn(dabapNo, detail.getDupeIdnNoMk(), detail.getEvtIdnNo());
                }
            }
            else {
                appMonth = queryService.getBapaavgmonAppMonth(caseObj.getAppDate());
                // 身分證重號處理
                evtIdnNo = queryService.getDupeIdnNoMkIdn(caseObj.getApNo(), caseObj.getDupeIdnNoMk(), caseObj.getEvtIdnNo());
            }
            // 60個月平均薪資資料
            String realAvgMon = "";
            List<PaymentQueryAvgAmtDataCase> sixtyMonAvgAmtDataList = new ArrayList<PaymentQueryAvgAmtDataCase>();

            if (StringUtils.isNotBlank(avgAmtSeniData.getEvtIdnNo()) || StringUtils.isNotBlank(evtIdnNo)) {

                if ((StringUtils.equals(ConstantKey.BAAPPEXPAND_EVTYP_3, avgAmtSeniData.getEvTyp()) || StringUtils.equals(ConstantKey.BAAPPEXPAND_EVTYP_4, avgAmtSeniData.getEvTyp())) && StringUtils.equals("Y", avgAmtSeniData.getPrType())) {
                    if (caseObj.getApItem().equals("7")) {
                        // 他案 失能
                        PaymentQueryAvgAmtDataCase dabapNoAvgAmtSeniData = queryService.getPaymentQueryAvgAmtSeniData(dabapNo, null);
                        if ((StringUtils.equals(ConstantKey.BAAPPEXPAND_EVTYP_3, dabapNoAvgAmtSeniData.getEvTyp()) || StringUtils.equals(ConstantKey.BAAPPEXPAND_EVTYP_4, dabapNoAvgAmtSeniData.getEvTyp()))
                                        && !StringUtils.equals("Y", dabapNoAvgAmtSeniData.getPrType()) || StringUtils.isBlank(dabapNoAvgAmtSeniData.getPrType())) {
                            sixtyMonAvgAmtDataList = queryService.getPaymentQuerySixtyMonAvgAmtData(dabapNo, "0000", evtIdnNo, "1", appMonth);
                            realAvgMon = queryService.getRealAvgMonBy(dabapNo, "0000", evtIdnNo, "1");
                        }
                        else {
                            sixtyMonAvgAmtDataList = queryService.getPaymentQuerySixtyMonAvgAmtData(dabapNo, "0000", evtIdnNo, "6", appMonth);
                            realAvgMon = queryService.getRealAvgMonBy(dabapNo, "0000", evtIdnNo, "6");
                        }
                    }
                    else if (caseObj.getApItem().equals("8")) {
                        // 他案 老年
                        sixtyMonAvgAmtDataList = queryService.getPaymentQuerySixtyMonAvgAmtDataForOldAge(dabapNo, "0000", evtIdnNo, appMonth);
                        // 取得 畫面上顯示實際均薪月數：XXX個月
                        realAvgMon = queryService.getRealAvgMonForOldAge(dabapNo, "0000", evtIdnNo);
                    }
                    else {
                        sixtyMonAvgAmtDataList = queryService.getPaymentQuerySixtyMonAvgAmtData(caseObj.getApNo(), "0000", avgAmtSeniData.getEvtIdnNo(), "1", appMonth);
                        realAvgMon = queryService.getRealAvgMonBy(caseObj.getApNo(), "0000", avgAmtSeniData.getEvtIdnNo(), "1");
                    }
                }
                else {
                    if (caseObj.getApItem().equals("7")) {
                        // 他案 失能
                        PaymentQueryAvgAmtDataCase dabapNoAvgAmtSeniData = queryService.getPaymentQueryAvgAmtSeniData(dabapNo, null);
                        if ((StringUtils.equals(ConstantKey.BAAPPEXPAND_EVTYP_3, dabapNoAvgAmtSeniData.getEvTyp()) || StringUtils.equals(ConstantKey.BAAPPEXPAND_EVTYP_4, dabapNoAvgAmtSeniData.getEvTyp()))
                                        && !StringUtils.equals("Y", dabapNoAvgAmtSeniData.getPrType()) || StringUtils.isBlank(dabapNoAvgAmtSeniData.getPrType())) {
                            sixtyMonAvgAmtDataList = queryService.getPaymentQuerySixtyMonAvgAmtData(dabapNo, "0000", evtIdnNo, "1", appMonth);
                            realAvgMon = queryService.getRealAvgMonBy(dabapNo, "0000", evtIdnNo, "1");
                        }
                        else {
                            sixtyMonAvgAmtDataList = queryService.getPaymentQuerySixtyMonAvgAmtData(dabapNo, "0000", evtIdnNo, "6", appMonth);
                            realAvgMon = queryService.getRealAvgMonBy(dabapNo, "0000", evtIdnNo, "6");
                        }
                    }
                    else if (caseObj.getApItem().equals("8")) {
                        // 他案 老年
                        sixtyMonAvgAmtDataList = queryService.getPaymentQuerySixtyMonAvgAmtDataForOldAge(dabapNo, "0000", evtIdnNo, appMonth);
                        // 取得 畫面上顯示實際均薪月數：XXX個月
                        realAvgMon = queryService.getRealAvgMonForOldAge(dabapNo, "0000", evtIdnNo);
                    }
                    else {
                        sixtyMonAvgAmtDataList = queryService.getPaymentQuerySixtyMonAvgAmtData(caseObj.getApNo(), "0000", avgAmtSeniData.getEvtIdnNo(), "6", appMonth);
                        realAvgMon = queryService.getRealAvgMonBy(caseObj.getApNo(), "0000", avgAmtSeniData.getEvtIdnNo(), "6");
                    }
                }

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentQuerySixtyMonAvgAmtDataCaseList(sixtyMonAvgAmtDataList, request);
                // 取得 畫面上顯示實際均薪月數：XXX個月
                avgAmtSeniData.setRealAvgMon(realAvgMon); // 放入 實際均薪月數
            }
            avgAmtSeniData.setAppMonth(appMonth); // 放入 平均薪資月數

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryAvgAmtSeniDataCase(avgAmtSeniData, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金平均薪資 PaymentQueryAction.doSurvivorAvgAmtData() 完成 ... ");
            return mapping.findForward(FORWARD_SURVIVOR_AVGAMT_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doDisabledLoanData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金物價指數調整(baiq0d212q05.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorCpiData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金物價指數調整 PaymentQueryAction.doSurvivorCpiData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            String payCode = caseObj.getApNo1();
            // 物價指數調整資料
            List<PaymentQueryCpiDataCase> cpiDataList = queryService.getCpiDataForPaymentQuery(caseObj.getApNo(), payCode);

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryCpiDataCaseList(cpiDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金物價指數調整 PaymentQueryAction.doSurvivorCpiData() 完成 ... ");
            return mapping.findForward(FORWARD_SURVIVOR_CPI_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doSurvivorCpiData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金物價指數調整記錄資料(baiq0d212q06.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorCpiRecData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金物價指數調整記錄資料 PaymentQueryAction.doSurvivorCpiRecData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 物價指數調整記錄資料
            List<PaymentQueryCpiDataCase> cpiRecDataList = queryService.getCpiRecForPaymentQuery();

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryCpiRecDataCaseList(cpiRecDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金物價指數調整記錄資料 PaymentQueryAction.doSurvivorCpiRecData() 完成 ... ");
            return mapping.findForward(FORWARD_SURVIVOR_CPIREC_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doSurvivorCpiRecData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金受款人強迫不合格資料 (baiq0d212q07.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorCompelNopayDetailData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金受款人強迫不合格資料 PaymentQueryAction.doSurvivorCompelNopayDetailData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 取得 受款人不合格資料
            // List<PaymentQueryBenDataCase> studDetailDataList = queryService.getStudDetailDataForSurvivor(iform.getApNo(), iform.getSeqNo());
            List<PaymentQueryBenDataCase> compelNopayDetailDataList = queryService.getCompelNopayDetailDataForSurvivor(iform.getApNo(), iform.getSeqNo());

            // 將 查詢結果清單 存到 Request Scope
            // CaseSessionHelper.setPaymentQueryStudDetailDataCaseList(studDetailDataList, request);
            CaseSessionHelper.setPaymentQueryCompelNopayDetailDataCaseList(compelNopayDetailDataList, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金受款人強迫不合格資料 PaymentQueryAction.doSurvivorCompelNopayDetailData() 完成 ... ");
            return mapping.findForward(FORWARD_SURVIVOR_COMPEL_NOPAY_DETAIL_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doSurvivorBenData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_SURVIVOR_BEN_DATA);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金歸檔記錄資料(baiq0d012q11.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorArclistData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 歸檔記錄資料 PaymentQueryAction.doSurvivorArclistData() 開始 ... ");
        HttpSession session = request.getSession();
        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 歸檔記錄資料
            PaymentQueryArclistDataCase arclistCase = queryService.getArclistDataForPaymentQuery(caseObj.getApNo());

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryArclistDataCase(arclistCase, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 歸檔記錄資料 PaymentQueryAction.doSurvivorArclistData() 完成 ... ");
            return mapping.findForward(FORWARD_SURVIVOR_ARCLIST_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doSurvivorArclistData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金更正日誌資料(baiq0d212q09.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */

    public ActionForward doSurvivorUpdateLogQueryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金更正日誌資料 PaymentQueryAction.doSurvivorUpdateLogQueryData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeUpdateLogQueryCase(request);

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            String updateDateBegin = "20090101";
            String updateDateEnd = DateUtil.getNowWestDate();
            String apNo = caseObj.getApNo();
            String payCode = caseObj.getApNo1();
            // 取得 更正日誌 資料
            UpdateLogQueryCase caseData = queryService.getUpdateLogQueryCaseBy(payCode, updateDateBegin, updateDateEnd, apNo, null, null);

            // 塞到 Session 中
            CaseSessionHelper.setUpdateLogQueryCase(caseData, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 遺屬年金更正日誌資料 PaymentQueryAction.doSurvivorUpdateLogQueryData() 完成 ... ");

            return mapping.findForward(FORWARD_SURVIVOR_UPDATE_LOG_QUERY_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doSurvivorUpdateLogQueryData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業(外單位用) - 遺屬年金更正日誌資料(baiq0d262q09.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */

    public ActionForward doSurvivorSimplifyUpdateLogQueryData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業(外單位用) - 遺屬年金更正日誌資料 PaymentQueryAction.doSurvivorSimplifyUpdateLogQueryData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeSimplifyUpdateLogQueryCase(request);

            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            String updateDateBegin = "20090101";
            String updateDateEnd = DateUtil.getNowWestDate();
            String apNo = caseObj.getApNo();
            String payCode = caseObj.getApNo1();
            // 取得 更正日誌 資料
            UpdateLogQueryCase caseData = queryService.getUpdateLogQueryCaseBy(payCode, updateDateBegin, updateDateEnd, apNo, null, null);

            // 隱藏隱私資料
            List<UpdateLogQueryDetailCase> updateLogList = caseData.getUpdateLogList();

            for (UpdateLogQueryDetailCase LogList : updateLogList) {

                if (!StringUtils.isBlank(LogList.getUpdUser())) {
                    LogList.setUpdUser("*****");
                }
                if (!StringUtils.isBlank(LogList.getUpCol())) {
                    if (LogList.getUpCol().substring(0, 2).equals("帳號") || LogList.getUpCol().substring(0, 2).equals("電話") || LogList.getUpCol().equals("地址") || LogList.getUpCol().equals("受款人身分證號")) {
                        LogList.setAvalue("********");
                        LogList.setBvalue("********");
                    }
                }
            }

            // 塞到 Session 中
            CaseSessionHelper.setSimplifyUpdateLogQueryCase(caseData, request);

            log.debug("執行 查詢作業 - 給付查詢作業(外單位用) - 遺屬年金更正日誌資料 PaymentQueryAction.doSurvivorSimplifyUpdateLogQueryData() 完成 ... ");

            return mapping.findForward(FORWARD_SURVIVOR_UPDATE_LOG_QUERY_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doSurvivorSimplifyUpdateLogQueryData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 遺屬年金核定通知記錄資料(baiq0d212q10.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSurvivorUnqualifiedNoticeData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 核定通知記錄資料 PaymentQueryAction.doSurvivorUnqualifiedNoticeData() 開始 ... ");
        HttpSession session = request.getSession();
        try {
            // 案件資料
            PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryDetailDataCase(request);

            // 核定清單記錄資料
            PaymentQueryUnqualifiedNoticeDataCase arclistCase = queryService.getUnqualifiedNoticeDataForPaymentQuery(caseObj.getApNo());

            // 將 查詢結果清單 存到 Request Scope
            CaseSessionHelper.setPaymentQueryUnqualifiedNoticeDataCase(arclistCase, request);

            log.debug("執行 查詢作業 - 給付查詢作業 - 核定通知記錄資料 PaymentQueryAction.doSurvivorUnqualifiedNoticeData() 完成 ... ");
            return mapping.findForward(FORWARD_SURVIVOR_BAUNQUALIFIED_NOTICE_DATA);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doSurvivorUnqualifiedNoticeData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    // ---------------------------------------- 遺屬年金 ----------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 返回明細首頁
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackMaster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 返回資料清單 PaymentQueryAction.doBackMaster() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String apNo = qryForm.getApNoStr();
            String benName = qryForm.getName();
            String benIdnNo = qryForm.getIdn();
            String benBrDate = qryForm.getBrDateStr();
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // List<PaymentQueryMasterCase> queryList = queryService.selectPaymentQueryMasterData(apNo, benIdnNo, benName, benBrDate, qryCond, startYm, endYm);
            List<PaymentQueryMasterCase> queryList = CaseSessionHelper.getPaymentQueryMasterCaseList(request);

            if (queryList.size() == 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 查詢作業 - 給付查詢作業 - 返回資料清單 PaymentQueryAction.doBackMaster() 結束 ... ");
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }
            else {
                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setPaymentQueryMasterCaseList(queryList, request);

                log.debug("執行 查詢作業 - 給付查詢作業 - 返回資料清單 PaymentQueryAction.doBackMaster() 結束 ... ");
                return mapping.findForward(FORWARD_MASTER_LIST);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doBackMaster() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 返回 PaymentQueryAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removePaymentQueryForm(request);
        FormSessionHelper.removePaymentQueryCondForm(request);
        CaseSessionHelper.removeAllPaymentQueryCase(request);

        log.debug("執行 查詢作業 - 給付查詢作業 - 返回 PaymentQueryAction.doBack() 完成 ... ");
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
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 檢視受理審核清單 PaymentQueryAction.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;
        PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
        String forward = FORWARD_MASTER_LIST;
        if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(detailCase.getPagePayKind())) {
            forward = FORWARD_OLDAGE_APPLICATION_DATA;
        }
        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(detailCase.getPagePayKind())) {
            forward = FORWARD_DISABLED_APPLICATION_DATA;
        }
        else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(detailCase.getPagePayKind())) {
            forward = FORWARD_SURVIVOR_APPLICATION_DATA;
        }
        try {
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            String apNo = qryForm.getApNoStr();
            String benName = qryForm.getName();
            String benIdnNo = qryForm.getIdn();
            String benBrDate = qryForm.getBrDateStr();
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            List<PaymentQueryMasterCase> queryList = queryService.selectPaymentQueryMasterData(apNo, benIdnNo, benName, benBrDate, qryCond, startYm, endYm);

            String fileUrl = PropertyHelper.getProperty("rpt.path.final.rpt") + Encode.forJava(iform.getApNo()) + "_" + Encode.forJava(iform.getIssuYm()) + "_F.pdf";
            File file = new File(fileUrl);
            // File file = new File("c:\\ubn.pdf");

            if (file.exists()) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                    byte[] buffer = new byte[128];
                    int iLength = 0;
                    while ((iLength = fis.read(buffer)) != -1) {
                        baoOutput.write(buffer, 0, iLength);
                    }

                    // String printDate = DateUtility.getNowChineseDate();

                    // 設定回傳回 Client 端之檔案大小, 若不設定,
                    // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                    response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(iform.getApNo()) + "_" + DateUtility.changeWestYearMonthType(Encode.forJava(iform.getIssuYm())) + "_F.pdf" + "\"");
                    response.setHeader("Content-Encoding", "pdf");
                    response.setContentType("application/pdf"); // 設定MIME
                    response.setHeader("Expires", "0");
                    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                    response.setHeader("Pragma", "public");
                    response.setContentLength(baoOutput.size());

                    // 傳回 Client 端

                    ServletOutputStream sout = response.getOutputStream();
                    baoOutput.writeTo(sout);
                    sout.flush();
                    baoOutput.close();
                    sout.close();

                }
                finally {
                    ExceptionUtil.safeColse(fis);
                }
                return null;
            }
            else {
                // 設定查詢失敗訊息
                // saveMessages(session, CustomMessageHelper.getFileErrorMessage());
                return mapping.findForward(forward);
            }
        }
        catch (

        Exception e) {
            // 設定查詢失敗訊息
            log.error("PaymentQueryAction.doReport() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(forward);
        }
    }

    // /**
    // * 詢作業 - 給付查詢作業 - 請領同類/他類資料 (baco0d013q01.jsp)
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward doApplyData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 詢作業 - 給付查詢作業 - 請領同類/他類資料 PaymentQueryAction.doApplyData() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // PaymentQueryForm iform = (PaymentQueryForm) form;
    //
    // try {
    // // 案件資料
    // PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryMasterDetailCase(request);
    // String apNo = caseObj.getApNo();
    // String evtIdnNo = caseObj.getEvtIdnNo();
    // String evtBrDate = caseObj.getEvtBrDate();
    // String evtBrDateStr = caseObj.getEvtBrDateStr();// 就保給付檔(BIREF)裡的日期存民國
    // String evtIds = caseObj.getEvtIds();
    //
    // // 一次給付資料
    // PaymentQueryApplyDataCase oncePayData = queryService.getOncePayDetailData(evtIdnNo, evtBrDate);
    // // 年金給付資料
    // List<PaymentQueryApplyDataCase> annuityPayDataList = queryService.getAnnuityPayData(apNo, evtIdnNo, evtBrDate);
    // // 申請失能給付紀錄資料
    // List<PaymentQueryApplyDataCase> criPayDataList = queryService.getCriPayApplyData(evtIdnNo, evtBrDate);
    // // 申請死亡給付紀錄資料
    // PaymentQueryApplyDataCase diePayData = queryService.getDiePayApplyData(evtIdnNo, evtBrDate);
    // // 申請傷病給付紀錄資料
    // List<PaymentQueryApplyDataCase> injPayDataList = queryService.getInjPayApplyData(evtIdnNo, evtBrDate);
    // // 申請失業給付紀錄資料
    // List<PaymentQueryApplyDataCase> unEmpPayDataList = queryService.getUnEmpPayApplyData(evtIdnNo, evtBrDateStr);
    // // 申請國保給付紀錄資料
    // PaymentQueryApplyDataCase npPayData = queryService.getNpPayApplyData(evtIds);
    //
    // // 將 查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setOncePayDetailDataCase(oncePayData, request);
    // CaseSessionHelper.setAnnuityPayDataList(annuityPayDataList, request);
    // CaseSessionHelper.setCriPayApplyDataList(criPayDataList, request);
    // CaseSessionHelper.setDiePayApplyDataCase(diePayData, request);
    // CaseSessionHelper.setInjPayApplyDataList(injPayDataList, request);
    // CaseSessionHelper.setUnEmpPayApplyDataList(unEmpPayDataList, request);
    // CaseSessionHelper.setNpPayApplyDataCase(npPayData, request);
    //
    // log.debug("執行 詢作業 - 給付查詢作業 - 請領同類/他類資料 PaymentQueryAction.doApplyData() 完成 ... ");
    // return mapping.findForward(FORWARD_APPLY_DATA);
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentQueryAction.doApplyData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(FORWARD_DETAIL_DATA);
    // }
    // }

    // /**
    // * 查詢作業 - 給付查詢作業 - 核定資料 (baiq0d012q04.jsp)
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward doIssuData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 查詢作業 - 給付查詢作業 - 核定資料 PaymentQueryAction.doIssuData() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // PaymentQueryForm iform = (PaymentQueryForm) form;
    //
    // try {
    // // 起始查詢條件
    // PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
    // String qryCond = qryForm.getQryCond();
    // String startYm = qryForm.getStartYmStr();
    // String endYm = qryForm.getEndYmStr();
    //
    // // 案件資料
    // PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryMasterDetailCase(request);
    //
    // // 取得 核定資料
    // List<PaymentQueryBenDataCase> benDataList = queryService.getIssuData(caseObj.getApNo(), qryCond, startYm, endYm);
    //
    // // 將 查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setPaymentQueryBenDataCaseList(benDataList, request);
    //
    // log.debug("執行 查詢作業 - 給付查詢作業 - 核定資料 PaymentQueryAction.doIssuData() 完成 ... ");
    // return mapping.findForward(FORWARD_ISSU_DATA);
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentQueryAction.doIssuData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(FORWARD_DETAIL_DATA);
    // }
    // }

    // /**
    // * 查詢作業 - 給付查詢作業 - 年資資料(含欠費期間/承保異動資料) (baiq0d012q02.jsp)
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward doSeniData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 查詢作業 - 給付查詢作業 - 年資資料(含欠費期間/承保異動資料) PaymentQueryAction.doSeniData() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // PaymentQueryForm iform = (PaymentQueryForm) form;
    //
    // try {
    // // 案件資料
    // PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryMasterDetailCase(request);
    //
    // // 取得 年資資料
    // PaymentQuerySeniDataCase seniData = queryService.getSeniData(caseObj.getApNo(), caseObj.getEvtIdnNo());
    //
    // // 將 查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setPaymentQuerySeniDataCase(seniData, request);
    //
    // log.debug("執行 查詢作業 - 給付查詢作業 - 年資資料(含欠費期間/承保異動資料) PaymentQueryAction.doSeniData() 完成 ... ");
    // return mapping.findForward(FORWARD_SENI_DATA);
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentQueryAction.doSeniData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(FORWARD_DETAIL_DATA);
    // }
    // }

    // /**
    // * 查詢作業 - 給付查詢作業 - 一次給付資料 (baiq0d012q03.jsp)
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward doOncePayData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 查詢作業 - 給付查詢作業 - 一次給付資料 PaymentQueryAction.doOncePayData() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // PaymentQueryForm iform = (PaymentQueryForm) form;
    //
    // try {
    // // 起始查詢條件
    // PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
    // String qryCond = qryForm.getQryCond();
    // String startYm = qryForm.getStartYmStr();
    // String endYm = qryForm.getEndYmStr();
    //
    // // 案件資料
    // PaymentQueryDetailDataCase caseObj = CaseSessionHelper.getPaymentQueryMasterDetailCase(request);
    //
    // // 取得 一次給付資料
    // PaymentQueryOncePayDataCase oncePayCase = queryService.getOncePayData(caseObj.getApNo(), caseObj.getIssuYm());
    // // 取得 一次給付更正資料
    // List<PaymentQueryOncePayDataCase> oncePayModifyList = queryService.getOncePayModifyData(caseObj.getApNo());
    //
    // // 將 查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setPaymentQueryOncePayDataCase(oncePayCase, request);
    // CaseSessionHelper.setPaymentQueryOncePayModifyDataCaseList(oncePayModifyList, request);
    //
    // log.debug("執行 查詢作業 - 給付查詢作業 - 一次給付資料 PaymentQueryAction.doOncePayData() 完成 ... ");
    // return mapping.findForward(FORWARD_ONCEPAY_DATA);
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentQueryAction.doOncePayData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(FORWARD_DETAIL_DATA);
    // }
    // }

    // /**
    // * 查詢作業 - 給付查詢作業 - 給付明細查詢 - 核定/給付資料明細 (baiq0d015q.jsp)
    // *
    // * @param mapping
    // * @param form
    // * @param request
    // * @param response
    // * @return
    // */
    // public ActionForward doIssuPayDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    // log.debug("執行 查詢作業 - 給付查詢作業 - 給付明細查詢 - 核定/給付資料明細 PaymentQueryAction.doIssuPayDetail() 開始 ... ");
    //
    // HttpSession session = request.getSession();
    // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
    // PaymentQueryForm iform = (PaymentQueryForm) form;
    //
    // try {
    // // 取得 核定/給付資料明細
    // PaymentQueryIssuPayDataCase issuPayDetail = queryService.getPaymentQueryIssuPayDetail(iform.getApNo(), iform.getIssuYm(), iform.getPayYm());
    // // 取得 應收已收資料
    // List<PaymentQueryAlreadyReceiveCase> alreadyReceiveDataList = queryService.getPaymentQueryAlreadyReceiveData(iform.getApNo(), iform.getIssuYm());
    //
    // // 將 查詢條件/查詢結果清單 存到 Request Scope
    // CaseSessionHelper.setPaymentQueryIssuPayDetailCase(issuPayDetail, request);
    // CaseSessionHelper.setPaymentQueryAlreadyReceiveCaseList(alreadyReceiveDataList, request);
    //
    // log.debug("執行 查詢作業 - 給付查詢作業 - 給付明細查詢 - 核定/給付資料明細 PaymentQueryAction.doIssuPayDetail() 完成 ... ");
    // return mapping.findForward(FORWARD_ISSU_PAY_DETAIL);
    // }
    // catch (Exception e) {
    // // 設定查詢失敗訊息
    // log.error("PaymentQueryAction.doIssuPayDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
    // saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
    // return mapping.findForward(FORWARD_DETAIL_DATA);
    // }
    // }

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
        log.debug("執行 查詢作業 - 給付查詢作業 - 檢視受理審核清單 PaymentQueryAction.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;
        
        try {
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
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
            log.error("PaymentQueryAction.doReport() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    // 以下區塊為 給付查詢 給付查詢(外單位用) 列印 之Action START

    // ---------老年年金 給付查詢 給付查詢(外單位專用) 列印---------

    /**
     * 查詢作業 - 給付查詢作業 - 案件資料 按下 [列印] BAIQ0D012Q
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportApplicationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 案件資料列印 PaymentQueryAction.doReportApplicationData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            List<PaymentQueryIssuPayDataCase> issuPayDataList = CaseSessionHelper.getPaymentQueryIssuPayDataCaseList(request);
            List<Badapr> origIssuPayDataLis = CaseSessionHelper.getPaymentQueryOrigIssuPayDataCaseList(request);
            List<PaymentQueryDetailDataCase> chkFileDataList = CaseSessionHelper.getPaymentQueryEvtChkFileDataCase(request);
            List<PaymentQueryLetterTypeMkCase> detail1 = CaseSessionHelper.getPaymentQueryLetterTypeMk1List(request);
            List<PaymentQueryLetterTypeMkCase> detail2 = CaseSessionHelper.getPaymentQueryLetterTypeMk2List(request);
            List<PaymentQueryLetterTypeMkCase> detail3 = CaseSessionHelper.getPaymentQueryLetterTypeMk3List(request);
            List<PaymentQueryLetterTypeMkCase> detail4 = CaseSessionHelper.getPaymentQueryLetterTypeMk4List(request);
            List<PaymentQueryLetterTypeMkCase> detail5 = CaseSessionHelper.getPaymentQueryLetterTypeMk5List(request);
            PaymentQueryLetterTypeMkCase detail6 = CaseSessionHelper.getPaymentQueryLetterTypeMk6List(request);

            // 取得 受款人資料
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();
            List<PaymentQueryBenDataCase> benDataList = queryService.getBeneficiaryData(detailCase.getApNo(), qryCond, startYm, endYm);

            // 將 查詢結果清單 存到 Request Scope
            // CaseSessionHelper.setPaymentQueryBenDataCaseList(benDataList, request);
            // List<PaymentQueryBenDataCase> benDataList = CaseSessionHelper.getPaymentQueryBenDataCaseList(request);

            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
                OldAgePaymentRpt01Report report = new OldAgePaymentRpt01Report();
                baoOutput = report.doReport(userData, detailCase, issuPayDataList, origIssuPayDataLis, chkFileDataList, detail1, detail2, detail3, detail4, detail5, detail6, qryForm, benDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
                response.setHeader("Content-disposition", "attachment; filename=\"OldAgePaymentRpt01Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢 案件資料 PaymentQueryAction.doReportApplicationData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 (外單位專用)- 案件資料 按下 [列印] BAIQ0D062Q
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportSimplifyApplicationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 案件資料列印 PaymentQuerySimplifyAction.doReportSimplifyApplicationData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            List<PaymentQueryIssuPayDataCase> issuPayDataList = CaseSessionHelper.getPaymentQueryIssuPayDataCaseList(request);
            List<Badapr> origIssuPayDataLis = CaseSessionHelper.getPaymentQueryOrigIssuPayDataCaseList(request);
            List<PaymentQueryDetailDataCase> chkFileDataList = CaseSessionHelper.getPaymentQueryEvtChkFileDataCase(request);
            List<PaymentQueryLetterTypeMkCase> detail1 = CaseSessionHelper.getPaymentQueryLetterTypeMk1List(request);
            List<PaymentQueryLetterTypeMkCase> detail2 = CaseSessionHelper.getPaymentQueryLetterTypeMk2List(request);
            List<PaymentQueryLetterTypeMkCase> detail3 = CaseSessionHelper.getPaymentQueryLetterTypeMk3List(request);
            List<PaymentQueryLetterTypeMkCase> detail4 = CaseSessionHelper.getPaymentQueryLetterTypeMk4List(request);
            List<PaymentQueryLetterTypeMkCase> detail5 = CaseSessionHelper.getPaymentQueryLetterTypeMk5List(request);
            PaymentQueryLetterTypeMkCase detail6 = CaseSessionHelper.getPaymentQueryLetterTypeMk6List(request);

            // 取得 受款人資料
            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();
            List<PaymentQueryBenDataCase> benDataList = queryService.getBeneficiaryData(detailCase.getApNo(), qryCond, startYm, endYm);

            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 取得原住民羅馬拼音
            Cvldtl cvldtl = queryService.selectHaddrBy(detailCase.getEvtIds());
            if (cvldtl != null) {
                detailCase.setRmp_Name(cvldtl.getRmp_Name());
            }

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
                SimplifyOldAgePaymentRpt01Report report = new SimplifyOldAgePaymentRpt01Report();
                baoOutput = report.doReport(userData, detailCase, issuPayDataList, origIssuPayDataLis, chkFileDataList, detail1, detail2, detail3, detail4, detail5, detail6, qryForm, benDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
                response.setHeader("Content-disposition", "attachment; filename=\"SimplifyOldAgePaymentRpt01Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢 案件資料 PaymentQuerySimplifyAction.doReportSimplifyApplicationData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    // ---------失能年金 給付查詢 給付查詢(外單位專用) 列印---------

    /**
     * 查詢作業 - 給付查詢作業 - 案件資料 按下 [列印] BAIQ0D112Q
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportDisabledApplicationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 案件資料列印 PaymentQueryAction.doReportDisabledApplicationData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            List<PaymentQueryIssuPayDataCase> issuPayDataList = CaseSessionHelper.getPaymentQueryIssuPayDataCaseList(request);
            List<Badapr> origIssuPayDataLis = CaseSessionHelper.getPaymentQueryOrigIssuPayDataCaseList(request);
            List<PaymentQueryDetailDataCase> chkFileDataList = CaseSessionHelper.getPaymentQueryEvtChkFileDataCase(request);
            List<PaymentQueryLetterTypeMkCase> detail1 = CaseSessionHelper.getPaymentQueryLetterTypeMk1List(request);
            List<PaymentQueryLetterTypeMkCase> detail2 = CaseSessionHelper.getPaymentQueryLetterTypeMk2List(request);
            List<PaymentQueryLetterTypeMkCase> detail3 = CaseSessionHelper.getPaymentQueryLetterTypeMk3List(request);
            List<PaymentQueryLetterTypeMkCase> detail4 = CaseSessionHelper.getPaymentQueryLetterTypeMk4List(request);
            List<PaymentQueryLetterTypeMkCase> detail5 = CaseSessionHelper.getPaymentQueryLetterTypeMk5List(request);
            PaymentQueryLetterTypeMkCase detail6 = CaseSessionHelper.getPaymentQueryLetterTypeMk6List(request);

            List<PaymentQueryDetailDataCase> benChkList = CaseSessionHelper.getPaymentQueryBenChkFileDataCase(request);
            List<PaymentQueryDetailDataCase> matchChkList = CaseSessionHelper.getPaymentQueryMatchChkFileDataCase(request);
            PaymentQueryOccAccDataCase occAccData = CaseSessionHelper.getPaymentQueryOccAccDataCase(request);
            PaymentQueryDisabledDataCase disabledData = CaseSessionHelper.getPaymentQueryDisabledCase(request);

            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 取得 受款人資料
            List<PaymentQueryBenDataCase> benDataList = queryService.getBeneficiaryData(detailCase.getApNo(), qryCond, startYm, endYm);
            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 取得原住民羅馬拼音
            Cvldtl cvldtl = queryService.selectHaddrBy(detailCase.getEvtIds());
            if (cvldtl != null) {
                detailCase.setRmp_Name(cvldtl.getRmp_Name());
            }

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {
                DisabledPaymentRpt01Report report = new DisabledPaymentRpt01Report();
                baoOutput = report.doReport(userData, detailCase, issuPayDataList, origIssuPayDataLis, chkFileDataList, detail1, detail2, detail3, detail4, detail5, detail6, qryForm, benChkList, matchChkList, occAccData, disabledData, benDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {
                response.setHeader("Content-disposition", "attachment; filename=\"DisabledPaymentRpt01Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢 案件資料 PaymentQueryAction.doReportDisabledApplicationData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業(外單位專用) - 案件資料 按下 [列印] BAIQ0D162Q
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportSimplifyDisabledApplicationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業(外單位專用) - 案件資料列印 PaymentQueryAction.doReportSimplifyDisabledApplicationData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            List<PaymentQueryIssuPayDataCase> issuPayDataList = CaseSessionHelper.getPaymentQueryIssuPayDataCaseList(request);
            List<Badapr> origIssuPayDataLis = CaseSessionHelper.getPaymentQueryOrigIssuPayDataCaseList(request);
            List<PaymentQueryDetailDataCase> chkFileDataList = CaseSessionHelper.getPaymentQueryEvtChkFileDataCase(request);
            List<PaymentQueryLetterTypeMkCase> detail1 = CaseSessionHelper.getPaymentQueryLetterTypeMk1List(request);
            List<PaymentQueryLetterTypeMkCase> detail2 = CaseSessionHelper.getPaymentQueryLetterTypeMk2List(request);
            List<PaymentQueryLetterTypeMkCase> detail3 = CaseSessionHelper.getPaymentQueryLetterTypeMk3List(request);
            List<PaymentQueryLetterTypeMkCase> detail4 = CaseSessionHelper.getPaymentQueryLetterTypeMk4List(request);
            List<PaymentQueryLetterTypeMkCase> detail5 = CaseSessionHelper.getPaymentQueryLetterTypeMk5List(request);
            PaymentQueryLetterTypeMkCase detail6 = CaseSessionHelper.getPaymentQueryLetterTypeMk6List(request);

            List<PaymentQueryDetailDataCase> benChkList = CaseSessionHelper.getPaymentQueryBenChkFileDataCase(request);
            List<PaymentQueryDetailDataCase> matchChkList = CaseSessionHelper.getPaymentQueryMatchChkFileDataCase(request);
            PaymentQueryOccAccDataCase occAccData = CaseSessionHelper.getPaymentQueryOccAccDataCase(request);
            PaymentQueryDisabledDataCase disabledData = CaseSessionHelper.getPaymentQueryDisabledCase(request);

            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 取得 受款人資料
            List<PaymentQueryBenDataCase> benDataList = queryService.getBeneficiaryData(detailCase.getApNo(), qryCond, startYm, endYm);
            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 取得原住民羅馬拼音
            Cvldtl cvldtl = queryService.selectHaddrBy(detailCase.getEvtIds());
            if (cvldtl != null) {
                detailCase.setRmp_Name(cvldtl.getRmp_Name());
            }

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 老年
                SimplifyDisabledPaymentRpt01Report report = new SimplifyDisabledPaymentRpt01Report();
                baoOutput = report.doReport(userData, detailCase, issuPayDataList, origIssuPayDataLis, chkFileDataList, detail1, detail2, detail3, detail4, detail5, detail6, qryForm, benChkList, matchChkList, occAccData, disabledData, benDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 老年
                response.setHeader("Content-disposition", "attachment; filename=\"SimplifyDisabledPaymentRpt01Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢 案件資料(外單位專用) PaymentQueryAction.doReportSimplifyDisabledApplicationData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    // ---------遺屬年金 給付查詢 給付查詢(外單位專用) 列印---------
    /**
     * 查詢作業 - 給付查詢作業 - 案件資料 按下 [列印] BAIQ0D212Q
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportSurvivorApplicationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 案件資料列印 PaymentQueryAction.doReportSurvivorApplicationData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            List<PaymentQueryIssuPayDataCase> issuPayDataList = CaseSessionHelper.getPaymentQueryIssuPayDataCaseList(request);
            List<Badapr> origIssuPayDataLis = CaseSessionHelper.getPaymentQueryOrigIssuPayDataCaseList(request);
            List<PaymentQueryDetailDataCase> chkFileDataList = CaseSessionHelper.getPaymentQueryEvtChkFileDataCase(request);
            List<PaymentQueryLetterTypeMkCase> detail1 = CaseSessionHelper.getPaymentQueryLetterTypeMk1List(request);
            List<PaymentQueryLetterTypeMkCase> detail2 = CaseSessionHelper.getPaymentQueryLetterTypeMk2List(request);
            List<PaymentQueryLetterTypeMkCase> detail3 = CaseSessionHelper.getPaymentQueryLetterTypeMk3List(request);
            List<PaymentQueryLetterTypeMkCase> detail4 = CaseSessionHelper.getPaymentQueryLetterTypeMk4List(request);
            List<PaymentQueryLetterTypeMkCase> detail5 = CaseSessionHelper.getPaymentQueryLetterTypeMk5List(request);
            PaymentQueryLetterTypeMkCase detail6 = CaseSessionHelper.getPaymentQueryLetterTypeMk6List(request);

            List<PaymentQueryDetailDataCase> benChkList = CaseSessionHelper.getPaymentQueryBenChkFileDataCase(request);
            List<PaymentQueryDetailDataCase> matchChkList = CaseSessionHelper.getPaymentQueryMatchChkFileDataCase(request);
            PaymentQueryDisabledDataCase disabledData = CaseSessionHelper.getPaymentQueryDisabledCase(request);

            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 取得 受款人資料
            List<PaymentQueryBenDataCase> benDataList = queryService.getBeneficiaryDataForSurvivor(detailCase.getApNo(), qryCond, startYm, endYm);

            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 取得原住民羅馬拼音
            Cvldtl cvldtl = queryService.selectHaddrBy(detailCase.getEvtIds());
            if (cvldtl != null) {
                detailCase.setRmp_Name(cvldtl.getRmp_Name());
            }

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬
                SurvivorPaymentRpt01Report report = new SurvivorPaymentRpt01Report();
                baoOutput = report.doReport(userData, detailCase, issuPayDataList, origIssuPayDataLis, chkFileDataList, detail1, detail2, detail3, detail4, detail5, detail6, qryForm, benChkList, matchChkList, disabledData, benDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 老年
                response.setHeader("Content-disposition", "attachment; filename=\"SurvivorPaymentRpt01Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢(外單位專用) 案件資料 PaymentQueryAction.doReportSurvivorApplicationData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業(萬單位專用) - 案件資料 按下 [列印] BAIQ0D262Q
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportSimplifySurvivorApplicationData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業(萬單位專用) - 案件資料列印 PaymentQueryAction.doReportSimplifySurvivorApplicationData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            List<PaymentQueryIssuPayDataCase> issuPayDataList = CaseSessionHelper.getPaymentQueryIssuPayDataCaseList(request);
            List<Badapr> origIssuPayDataLis = CaseSessionHelper.getPaymentQueryOrigIssuPayDataCaseList(request);
            List<PaymentQueryDetailDataCase> chkFileDataList = CaseSessionHelper.getPaymentQueryEvtChkFileDataCase(request);
            List<PaymentQueryLetterTypeMkCase> detail1 = CaseSessionHelper.getPaymentQueryLetterTypeMk1List(request);
            List<PaymentQueryLetterTypeMkCase> detail2 = CaseSessionHelper.getPaymentQueryLetterTypeMk2List(request);
            List<PaymentQueryLetterTypeMkCase> detail3 = CaseSessionHelper.getPaymentQueryLetterTypeMk3List(request);
            List<PaymentQueryLetterTypeMkCase> detail4 = CaseSessionHelper.getPaymentQueryLetterTypeMk4List(request);
            List<PaymentQueryLetterTypeMkCase> detail5 = CaseSessionHelper.getPaymentQueryLetterTypeMk5List(request);
            PaymentQueryLetterTypeMkCase detail6 = CaseSessionHelper.getPaymentQueryLetterTypeMk6List(request);

            List<PaymentQueryDetailDataCase> benChkList = CaseSessionHelper.getPaymentQueryBenChkFileDataCase(request);
            List<PaymentQueryDetailDataCase> matchChkList = CaseSessionHelper.getPaymentQueryMatchChkFileDataCase(request);
            PaymentQueryDisabledDataCase disabledData = CaseSessionHelper.getPaymentQueryDisabledCase(request);

            String qryCond = qryForm.getQryCond();
            String startYm = qryForm.getStartYmStr();
            String endYm = qryForm.getEndYmStr();

            // 取得 受款人資料
            List<PaymentQueryBenDataCase> benDataList = queryService.getBeneficiaryDataForSurvivor(detailCase.getApNo(), qryCond, startYm, endYm);

            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 取得原住民羅馬拼音
            Cvldtl cvldtl = queryService.selectHaddrBy(detailCase.getEvtIds());
            if (cvldtl != null) {
                detailCase.setRmp_Name(cvldtl.getRmp_Name());
            }

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬
                SimplifySurvivorPaymentRpt01Report report = new SimplifySurvivorPaymentRpt01Report();
                baoOutput = report.doReport(userData, detailCase, issuPayDataList, origIssuPayDataLis, chkFileDataList, detail1, detail2, detail3, detail4, detail5, detail6, qryForm, benChkList, matchChkList, disabledData, benDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 老年
                response.setHeader("Content-disposition", "attachment; filename=\"SimplifySurvivorPaymentRpt01Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢(外單位專用) 案件資料 PaymentQueryAction.doReportSimplifySurvivorApplicationData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 歸檔記錄 按下 [列印] BAIQ0D012Q09
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportFileList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 歸檔記錄列印 PaymentQueryAction.doReportFileList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            PaymentQueryArclistDataCase arclistDetailDataList = CaseSessionHelper.getPaymentQueryArclistDetailDataCaseList(request);

            // 將 查詢結果清單 存到 Request Scope
            // CaseSessionHelper.setPaymentQueryBenDataCaseList(benDataList, request);
            // List<PaymentQueryBenDataCase> benDataList = CaseSessionHelper.getPaymentQueryBenDataCaseList(request);

            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
                OldAgePaymentRpt02Report report = new OldAgePaymentRpt02Report();
                baoOutput = report.doReport(userData, detailCase, qryForm, arclistDetailDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
                response.setHeader("Content-disposition", "attachment; filename=\"OldAgePaymentRpt02Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢 歸檔記錄 PaymentQueryAction.doReportFileList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業（外單位專用） - 歸檔記錄 按下 [列印] BAIQ0D062Q09
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportSimplifyFileList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業（外單位專用） - 給付查詢作業 - 歸檔記錄列印 PaymentQueryAction.doReportSimplifyFileList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            PaymentQueryArclistDataCase arclistDetailDataList = CaseSessionHelper.getPaymentQueryArclistDetailDataCaseList(request);

            // 將 查詢結果清單 存到 Request Scope
            // CaseSessionHelper.setPaymentQueryBenDataCaseList(benDataList, request);
            // List<PaymentQueryBenDataCase> benDataList = CaseSessionHelper.getPaymentQueryBenDataCaseList(request);

            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
                SimplifyOldAgePaymentRpt02Report report = new SimplifyOldAgePaymentRpt02Report();
                baoOutput = report.doReport(userData, detailCase, qryForm, arclistDetailDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payKind)) {// 老年
                response.setHeader("Content-disposition", "attachment; filename=\"SimplifyOldAgePaymentRpt02Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢（外單位專用） 歸檔記錄 PaymentQueryAction.doReportSimplifyFileList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 歸檔記錄 按下 [列印] BAIQ0D112Q11
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportDisabledFileList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 歸檔記錄列印 PaymentQueryAction.doReportDisabledFileList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            PaymentQueryArclistDataCase arclistDetailDataList = CaseSessionHelper.getPaymentQueryArclistDetailDataCaseList(request);

            // 將 查詢結果清單 存到 Request Scope
            // CaseSessionHelper.setPaymentQueryBenDataCaseList(benDataList, request);
            // List<PaymentQueryBenDataCase> benDataList = CaseSessionHelper.getPaymentQueryBenDataCaseList(request);

            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 失能
                DisabledPaymentRpt02Report report = new DisabledPaymentRpt02Report();
                baoOutput = report.doReport(userData, detailCase, qryForm, arclistDetailDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 失能
                response.setHeader("Content-disposition", "attachment; filename=\"DisabledPaymentRpt02Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢 歸檔記錄 PaymentQueryAction.doReportDisabledFileList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業（外單位專用） - 歸檔記錄 按下 [列印] BAIQ0D162Q11
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportSimplifyDisabledFileList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業（外單位專用） - 給付查詢作業 - 歸檔記錄列印 PaymentQueryAction.doReportSimplifyDisabledFileList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            PaymentQueryArclistDataCase arclistDetailDataList = CaseSessionHelper.getPaymentQueryArclistDetailDataCaseList(request);

            // 將 查詢結果清單 存到 Request Scope
            // CaseSessionHelper.setPaymentQueryBenDataCaseList(benDataList, request);
            // List<PaymentQueryBenDataCase> benDataList = CaseSessionHelper.getPaymentQueryBenDataCaseList(request);

            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 失能
                SimplifyDisabledPaymentRpt02Report report = new SimplifyDisabledPaymentRpt02Report();
                baoOutput = report.doReport(userData, detailCase, qryForm, arclistDetailDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payKind)) {// 失能
                response.setHeader("Content-disposition", "attachment; filename=\"SimplifyDisabledPaymentRpt02Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢（外單位專用） 歸檔記錄 PaymentQueryAction.doReportSimplifyDisabledFileList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業 - 歸檔記錄 按下 [列印] BAIQ0D212Q08
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportSurvivorFileList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 給付查詢作業 - 歸檔記錄列印 PaymentQueryAction.doReportSurvivorFileList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            PaymentQueryArclistDataCase arclistDetailDataList = CaseSessionHelper.getPaymentQueryArclistDetailDataCaseList(request);

            // 將 查詢結果清單 存到 Request Scope
            // CaseSessionHelper.setPaymentQueryBenDataCaseList(benDataList, request);
            // List<PaymentQueryBenDataCase> benDataList = CaseSessionHelper.getPaymentQueryBenDataCaseList(request);

            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬
                SurvivorPaymentRpt02Report report = new SurvivorPaymentRpt02Report();
                baoOutput = report.doReport(userData, detailCase, qryForm, arclistDetailDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬
                response.setHeader("Content-disposition", "attachment; filename=\"SurvivorPaymentRpt02Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢 歸檔記錄 PaymentQueryAction.doReportSurvivorFileList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 給付查詢作業（外單位專用） - 歸檔記錄 按下 [列印] BAIQ0D262Q08
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReportSimplifySurvivorFileList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業（外單位專用） - 給付查詢作業 - 歸檔記錄列印 PaymentQueryAction.doReportSimplifySurvivorFileList() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        PaymentQueryForm iform = (PaymentQueryForm) form;

        try {
            // 抓報表資料
            PaymentQueryForm qryForm = FormSessionHelper.getPaymentQueryCondForm(request);
            PaymentQueryDetailDataCase detailCase = CaseSessionHelper.getPaymentQueryDetailDataCase(request);
            PaymentQueryArclistDataCase arclistDetailDataList = CaseSessionHelper.getPaymentQueryArclistDetailDataCaseList(request);

            // 將 查詢結果清單 存到 Request Scope
            // CaseSessionHelper.setPaymentQueryBenDataCaseList(benDataList, request);
            // List<PaymentQueryBenDataCase> benDataList = CaseSessionHelper.getPaymentQueryBenDataCaseList(request);

            // 承辦人分機號碼
            detailCase.setEmpExt(queryService.getEmpExtData(detailCase.getApNo()));

            // 給付種類
            String payKind = detailCase.getPagePayKind();

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬屬
                SimplifySurvivorPaymentRpt02Report report = new SimplifySurvivorPaymentRpt02Report();
                baoOutput = report.doReport(userData, detailCase, qryForm, arclistDetailDataList);
            }

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payKind)) {// 遺屬
                response.setHeader("Content-disposition", "attachment; filename=\"SimplifySurvivorPaymentRpt02Report_" + printDate + ".pdf" + "\"");
            }
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
            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 給付查詢（外單位專用） 歸檔記錄 PaymentQueryAction.doReportSimplifySurvivorFileList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    // 以上區塊為 給付查詢 給付查詢(外單位用) 列印 之Action END

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }
}
