package tw.gov.bli.ba.query.helper;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.domain.Nbdapr;
import tw.gov.bli.ba.query.cases.AnnuityStatisticsCase;
import tw.gov.bli.ba.query.cases.CheckMarkLevelQueryCase;
import tw.gov.bli.ba.query.cases.ExecutiveSupportDataCase;
import tw.gov.bli.ba.query.cases.ExecutiveSupportQueryCase;
import tw.gov.bli.ba.query.cases.PaymentQueryAlreadyReceiveCase;
import tw.gov.bli.ba.query.cases.PaymentQueryApplyDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryArclistDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryArclistDetailDataCase;
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
import tw.gov.bli.ba.query.cases.PaymentQueryOncePayDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryReFeesDetailDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryReFeesMasterDataCase;
import tw.gov.bli.ba.query.cases.PaymentQuerySeniDataCase;
import tw.gov.bli.ba.query.cases.PaymentQueryUnqualifiedNoticeDataCase;
import tw.gov.bli.ba.query.cases.ReceivableQueryDetailCase;
import tw.gov.bli.ba.query.cases.ReceivableQueryMasterCase;
import tw.gov.bli.ba.query.cases.UpdateLogQueryCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateBareCheckCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCase;

public class CaseSessionHelper {
    private static Log log = LogFactory.getLog(CaseSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 查詢作業 - 更正日誌查詢 - UpdateLogQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 更正日誌查詢<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setUpdateLogQueryCase(UpdateLogQueryCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setUpdateLogQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.UPDATE_LOG_QUERY_CASE, caseData);
    }

    /**
     * 查詢作業 - 更正日誌查詢<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUpdateLogQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeUpdateLogQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.UPDATE_LOG_QUERY_CASE);
    }

    /**
     * 查詢作業 - 更正日誌查詢(外單位用)<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSimplifyUpdateLogQueryCase(UpdateLogQueryCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSimplifyUpdateLogQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SIMPLIFY_UPDATE_LOG_QUERY_CASE, caseData);
    }

    /**
     * 查詢作業 - 更正日誌查詢(外單位用)<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSimplifyUpdateLogQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSimplifyUpdateLogQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SIMPLIFY_UPDATE_LOG_QUERY_CASE);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 查詢作業 - 行政支援查詢 - ExecutiveSupportQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 行政支援查詢<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setExecutiveSupportQueryCaseList(List<ExecutiveSupportDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportQueryCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 行政支援查詢<br>
     * 從 Session 中取得相關 Case
     * 
     * @return List<ExecutiveSupportDataCase>
     */
    public static List<ExecutiveSupportDataCase> getExecutiveSupportQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getExecutiveSupportQueryCaseList() ...");

        HttpSession session = request.getSession();
        return (List<ExecutiveSupportDataCase>) session.getAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_CASE_LIST);
    }

    /**
     * 查詢作業 - 行政支援查詢<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportQueryCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_CASE_LIST);
    }

    /**
     * 查詢作業 - 行政支援查詢<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setExecutiveSupportQueryCase(ExecutiveSupportQueryCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_CASE, caseData);
    }

    /**
     * 查詢作業 - 行政支援查詢<br>
     * 從 Session 中取得相關 Case
     * 
     * @return List<ExecutiveSupportDataCase>
     */
    public static ExecutiveSupportQueryCase getExecutiveSupportQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getExecutiveSupportQueryCase() ...");

        HttpSession session = request.getSession();
        return (ExecutiveSupportQueryCase) session.getAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_CASE);
    }

    /**
     * 查詢作業 - 行政支援查詢<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_CASE);
    }

    /**
     * 查詢作業 - 行政支援查詢<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setExecutiveSupportQueryDataCase(ExecutiveSupportDataCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportQueryDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_DATA_CASE, caseData);
    }

    /**
     * 查詢作業 - 行政支援查詢<br>
     * 從 Session 中取得相關 Case
     * 
     * @return ExecutiveSupportDataCase
     */
    public static ExecutiveSupportDataCase getExecutiveSupportQueryDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getExecutiveSupportQueryDataCase() ...");

        HttpSession session = request.getSession();
        return (ExecutiveSupportDataCase) session.getAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_DATA_CASE);
    }

    /**
     * 查詢作業 - 行政支援查詢<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportQueryDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportQueryDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 查詢作業 - 編審註記查詢作業 - CheckMarkLevelQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 編審註記查詢作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCheckMarkLevelQueryCase(List<CheckMarkLevelQueryCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCheckMarkLevelQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CHECK_MARK_LEVEL_QUERY_CASE, caseData);
    }

    /**
     * 查詢作業 - 編審註記查詢作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCheckMarkLevelQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCheckMarkLevelQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CHECK_MARK_LEVEL_QUERY_CASE);
    }

    // ---------------------------------------------------------------------------------------
    // 查詢作業 - 給付查詢作業- PaymentQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 資料清單<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryMasterCaseList(List<PaymentQueryMasterCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryMasterCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_MASTER_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 資料清單<br>
     * 從 Session 中取得相關 Case
     * 
     * @return List<PaymentQueryMasterCase>
     */
    public static List<PaymentQueryMasterCase> getPaymentQueryMasterCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryMasterCaseList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryMasterCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_MASTER_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 資料清單<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryMasterCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryMasterCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_MASTER_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 明細資料<br>
     * 將 MasterDetail Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryDetailDataCase(PaymentQueryDetailDataCase caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryDetailDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_DETAIL_DATA_CASE, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 明細資料<br>
     * 從 Session 中取得 MasterDetail Case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static PaymentQueryDetailDataCase getPaymentQueryDetailDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.PaymentQueryDetailDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryDetailDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_DETAIL_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 明細資料<br>
     * 將 MasterDetail Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryDetailDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryDetailDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_DETAIL_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 核定/給付資料<br>
     * 將 issuPayData Case List 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryIssuPayDataCaseList(List<PaymentQueryIssuPayDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryIssuPayDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_ISSU_PAY_DATA_CASE, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 核定/給付資料<br>
     * 從 Session 中取得 issuPayData Case List
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryIssuPayDataCase> getPaymentQueryIssuPayDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryIssuPayDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryIssuPayDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_ISSU_PAY_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 核定/給付資料<br>
     * 將 issuPayData Case List 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryIssuPayDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryIssuPayDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_ISSU_PAY_DATA_CASE);
    }
    
    /**
     * 查詢作業 - 給付查詢作業 - 歸檔記錄資料<br>
     * 從 Session 中取得 arclistDetailDataList Case List
     * 
     * @return PaymentQueryArclistDetailDataCase
     */
    public static PaymentQueryArclistDataCase getPaymentQueryArclistDetailDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryArclistDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryArclistDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_ARCLIST_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 歸檔記錄資料<br>
     * 將 arclistDetailDataList Case List 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryArclistDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryArclistDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_ARCLIST_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 核定/給付資料 查詢結果原始資料<br>
     * 將 issuPayData Case List 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryOrigIssuPayDataCaseList(List<Badapr> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryOrigIssuPayDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_ORIG_ISSU_PAY_DATA_CASE, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 核定/給付資料 查詢結果原始資料<br>
     * 從 Session 中取得 issuPayData Case List
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<Badapr> getPaymentQueryOrigIssuPayDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryOrigIssuPayDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<Badapr>) session.getAttribute(ConstantKey.PAYMENT_QUERY_ORIG_ISSU_PAY_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 核定/給付資料 查詢結果原始資料<br>
     * 將 issuPayData Case List 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryOrigIssuPayDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryOrigIssuPayDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_ORIG_ISSU_PAY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 核定/給付資料明細<br>
     * 將 issuPayDetail Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryIssuPayDetailCase(PaymentQueryIssuPayDataCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryIssuPayDetailCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_ISSU_PAY_DETAIL_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 核定/給付資料明細<br>
     * 從 Session 中取得 issuPayDetail Case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static PaymentQueryIssuPayDataCase getPaymentQueryIssuPayDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryIssuPayDetailCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryIssuPayDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_ISSU_PAY_DETAIL_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 核定/給付資料明細<br>
     * 將 issuPayDetail Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryIssuPayDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryIssuPayDetailCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_ISSU_PAY_DETAIL_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 應收已收資料<br>
     * 將 alreadyReceive Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryAlreadyReceiveCaseList(List<PaymentQueryAlreadyReceiveCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryAlreadyReceiveCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_ALREADY_RECEIVE_DATA_CASE, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 應收已收資料<br>
     * 從 Session 中取得 alreadyReceive Case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryAlreadyReceiveCase> getPaymentQueryAlreadyReceiveCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryAlreadyReceiveCaseList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryAlreadyReceiveCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_ALREADY_RECEIVE_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 應收已收資料<br>
     * 將 alreadyReceive Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryAlreadyReceiveCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryAlreadyReceiveCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_ALREADY_RECEIVE_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 受款人資料(含核定/編審資料)<br>
     * 將 benData Case List 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryBenDataCaseList(List<PaymentQueryBenDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryBenDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_BEN_DATA_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 受款人資料(含核定/編審資料)<br>
     * 從 Session 中取得 benData Case List
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryBenDataCase> getPaymentQueryBenDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryBenDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryBenDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_BEN_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 受款人資料(含核定/編審資料)<br>
     * 將 benData Case List 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryBenDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryBenDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_BEN_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 年資資料(含欠費期間/承保異動資料)<br>
     * 將 seniData Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQuerySeniDataCase(PaymentQuerySeniDataCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQuerySeniDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_SENI_DATA_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 年資資料(含欠費期間/承保異動資料)<br>
     * 從 Session 中取得 seniData Case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static PaymentQuerySeniDataCase getPaymentQuerySeniDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQuerySeniDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQuerySeniDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_SENI_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 年資資料(含欠費期間/承保異動資料)<br>
     * 將 seniData Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQuerySeniDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQuerySeniDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_SENI_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 一次給付資料<br>
     * 將 oncePayData Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryOncePayDataCase(PaymentQueryOncePayDataCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryOncePayDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_ONCEPAY_DATA_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 一次給付資料<br>
     * 從 Session 中取得 oncePayData Case
     * 
     * @return PaymentQueryOncePayDataCase
     */
    public static PaymentQueryOncePayDataCase getPaymentQueryOncePayDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryOncePayDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryOncePayDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_ONCEPAY_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 -一次給付資料<br>
     * 將 oncePayData Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryOncePayDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryOncePayDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_ONCEPAY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 一次給付更正資料<br>
     * 將 oncePayData Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryOncePayModifyDataCaseList(List<PaymentQueryOncePayDataCase> caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryOncePayModifyDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_ONCEPAY_MODIFY_DATA_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 一次給付更正資料<br>
     * 從 Session 中取得 oncePayData Case
     * 
     * @return PaymentQueryOncePayDataCase
     */
    public static List<PaymentQueryOncePayDataCase> getPaymentQueryOncePayModifyDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryOncePayModifyDataCase() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryOncePayDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_ONCEPAY_MODIFY_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 一次給付更正資料<br>
     * 將 oncePayData Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryOncePayModifyDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryOncePayModifyDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_ONCEPAY_MODIFY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setOncePayDetailDataCase(PaymentQueryApplyDataCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setOncePayDetailDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_ONCEPAY_DETAIL_DATA_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static PaymentQueryApplyDataCase getOncePayDetailDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getBenIssueDataList() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryApplyDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_ONCEPAY_DETAIL_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOncePayDetailDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeBenIssueDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_ONCEPAY_DETAIL_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setAnnuityPayDataList(List<PaymentQueryApplyDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setAnnuityPayDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_ANNUITYPAY_DATA_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentQueryApplyDataCase> getAnnuityPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getAnnuityPayDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryApplyDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_ANNUITYPAY_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAnnuityPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAnnuityPayDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_ANNUITYPAY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setCriPayApplyDataList(List<PaymentQueryApplyDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCriPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_CRIPAY_APPLY_DATA_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentQueryApplyDataCase> getCriPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getCriPayApplyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryApplyDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_CRIPAY_APPLY_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCriPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCriPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_CRIPAY_APPLY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDiePayApplyDataCase(PaymentQueryApplyDataCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDiePayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_DIEPAY_APPLY_DATA_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static PaymentQueryApplyDataCase getDiePayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDiePayApplyDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryApplyDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_DIEPAY_APPLY_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDiePayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDiePayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_DIEPAY_APPLY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setInjPayApplyDataList(List<PaymentQueryApplyDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setInjPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_INJPAY_APPLY_DATA_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentQueryApplyDataCase> getInjPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getInjPayApplyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryApplyDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_INJPAY_APPLY_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeInjPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeInjPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_INJPAY_APPLY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄資料 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setUnEmpPayApplyDataList(List<PaymentQueryApplyDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setUnEmpPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_UNEMPPAY_APPLY_DATA_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄資料 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentQueryApplyDataCase> getUnEmpPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getUnEmpPayApplyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryApplyDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_UNEMPPAY_APPLY_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄資料 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUnEmpPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeUnEmpPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_UNEMPPAY_APPLY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄資料 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setNpPayApplyDataCase(PaymentQueryApplyDataCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setNpPayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_NPPAY_APPLY_DATA_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄資料 查詢結果<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentQueryApplyDataCase getNpPayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getNpPayApplyDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryApplyDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_NPPAY_APPLY_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄資料 查詢結果<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeNpPayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeNpPayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_NPPAY_APPLY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 事故者編審註記<br>
     * 將 MasterDetail Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryEvtChkFileDataCase(List<PaymentQueryDetailDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryEvtChkFileDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_EVT_CHKFILE_DATA_CASE, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 事故者編審註記<br>
     * 從 Session 中取得 MasterDetail Case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryDetailDataCase> getPaymentQueryEvtChkFileDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryEvtChkFileDataCase() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryDetailDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_EVT_CHKFILE_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 事故者編審註記<br>
     * 將 MasterDetail Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryEvtChkFileDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryEvtChkFileDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_EVT_CHKFILE_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 受款人編審註記<br>
     * 將 MasterDetail Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryBenChkFileDataCase(List<PaymentQueryDetailDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryBenChkFileDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_BEN_CHKFILE_DATA_CASE, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 受款人編審註記<br>
     * 從 Session 中取得 MasterDetail Case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryDetailDataCase> getPaymentQueryBenChkFileDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryBenChkFileDataCase() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryDetailDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_BEN_CHKFILE_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 受款人編審註記<br>
     * 將 MasterDetail Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryBenChkFileDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryBenChkFileDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_BEN_CHKFILE_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 符合註記<br>
     * 將 MasterDetail Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryMatchChkFileDataCase(List<PaymentQueryDetailDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryMatchChkFileDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_MATCH_CHKFILE_DATA_CASE, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 符合註記<br>
     * 從 Session 中取得 MasterDetail Case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryDetailDataCase> getPaymentQueryMatchChkFileDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryMatchChkFileDataCase() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryDetailDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_MATCH_CHKFILE_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 符合註記<br>
     * 將 MasterDetail Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryMatchChkFileDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryMatchChkFileDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_MATCH_CHKFILE_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 交查函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentQueryLetterTypeMk1List(List<PaymentQueryLetterTypeMkCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_1_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 交查函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentQueryLetterTypeMkCase> getPaymentQueryLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryLetterTypeMkCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_1_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 交查函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_1_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 不給付函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentQueryLetterTypeMk2List(List<PaymentQueryLetterTypeMkCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_2_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 不給付函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentQueryLetterTypeMkCase> getPaymentQueryLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryLetterTypeMkCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_2_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 不給付函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_2_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 補件通知函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentQueryLetterTypeMk3List(List<PaymentQueryLetterTypeMkCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_3_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 補件通知函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentQueryLetterTypeMkCase> getPaymentQueryLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryLetterTypeMkCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_3_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 補件通知函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_3_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 照會函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentQueryLetterTypeMk4List(List<PaymentQueryLetterTypeMkCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_4_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 照會函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentQueryLetterTypeMkCase> getPaymentQueryLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryLetterTypeMkCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_4_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 照會函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_4_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 其他簽函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentQueryLetterTypeMk5List(List<PaymentQueryLetterTypeMkCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_5_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 其他簽函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentQueryLetterTypeMkCase> getPaymentQueryLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryLetterTypeMkCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_5_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 其他簽函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_5_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentQueryLetterTypeMk6List(PaymentQueryLetterTypeMkCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_6_LIST, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentQueryLetterTypeMkCase getPaymentQueryLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryLetterTypeMkCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_6_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_LETTER_TYPE_6_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 職災相關資料<br>
     * 將 case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryOccAccDataCase(PaymentQueryOccAccDataCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryOccAccDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_OCCACC_DATA_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 職災相關資料<br>
     * 從 Session 中取得 case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static PaymentQueryOccAccDataCase getPaymentQueryOccAccDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryOccAccDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryOccAccDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_OCCACC_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 職災相關資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryOccAccDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryOccAccDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_OCCACC_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 失能相關資料<br>
     * 將 case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryDisabledCase(PaymentQueryDisabledDataCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryDisabledCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_DISABLED_DATA_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能相關資料<br>
     * 從 Session 中取得 case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static PaymentQueryDisabledDataCase getPaymentQueryDisabledCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryDisabledCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryDisabledDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_DISABLED_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 失能相關資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryDisabledCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryDisabledCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_DISABLED_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 眷屬資料<br>
     * 將 case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryFamilyDataList(List<PaymentQueryFamilyDataCase> caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryFamilyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_FAMILY_DATA_LIST, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 眷屬資料<br>
     * 從 Session 中取得 case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryFamilyDataCase> getPaymentQueryFamilyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryFamilyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryFamilyDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_FAMILY_DATA_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 眷屬資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryFamilyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryFamilyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_FAMILY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 複檢費用資料<br>
     * 將 case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryReFeesMasterDataList(List<PaymentQueryReFeesMasterDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryReFeesMasterDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_REFEES_MASTER_DATA_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 複檢費用資料<br>
     * 從 Session 中取得 case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryReFeesMasterDataCase> getPaymentQueryReFeesMasterDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryReFeesMasterDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryReFeesMasterDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_REFEES_MASTER_DATA_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 複檢費用資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryReFeesMasterDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryReFeesMasterDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_REFEES_MASTER_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 複檢費用明細資料<br>
     * 將 case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryReFeesDetailDataCase(PaymentQueryReFeesDetailDataCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryReFeesDetailData() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_REFEES_DETAIL_DATA_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 複檢費用明細資料<br>
     * 從 Session 中取得 case
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static PaymentQueryReFeesDetailDataCase getPaymentQueryReFeesDetailDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryReFeesDetailData() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryReFeesDetailDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_REFEES_DETAIL_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 複檢費用明細資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryReFeesDetailDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryReFeesDetailData() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_REFEES_DETAIL_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 受款人在學資料<br>
     * 將 benData Case List 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryStudDetailDataCaseList(List<PaymentQueryBenDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryStudDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_STUD_DETAIL_DATA_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 受款人在學資料<br>
     * 從 Session 中取得 benData Case List
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryBenDataCase> getPaymentQueryStudDetailDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryStudDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryBenDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_STUD_DETAIL_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 受款人在學資料<br>
     * 將 benData Case List 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryStudDetailDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryStudDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_STUD_DETAIL_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 受款人強迫不合格資料<br>
     * 將 benData Case List 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryCompelNopayDetailDataCaseList(List<PaymentQueryBenDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 setPaymentQueryCompelNopayDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_COMPEL_NOPAY_DETAIL_DATA_CASE_LIST, caseList);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 受款人重殘資料<br>
     * 將 benData Case List 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryHandicapDetailDataCaseList(List<PaymentQueryBenDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryHandicapDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_HANDICAP_DETAIL_DATA_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 受款人重殘資料<br>
     * 從 Session 中取得 benData Case List
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryBenDataCase> getPaymentQueryHandicapDetailDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryHandicapDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryBenDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_HANDICAP_DETAIL_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 受款人重殘資料<br>
     * 將 benData Case List 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryHandicapDetailDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryHandicapDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_HANDICAP_DETAIL_DATA_CASE_LIST);
    }
    
    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 受款人在學資料 for 失能年金<br>
     * 將 benData Case List 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryDisabledStudDetailDataCaseList(List<PaymentQueryFamilyDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryDisabledStudDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_DISABLED_STUD_DETAIL_DATA_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 受款人在學資料 for 失能年金<br>
     * 從 Session 中取得 benData Case List
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryFamilyDataCase> getPaymentQueryDisabledStudDetailDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryDisabledStudDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryFamilyDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_DISABLED_STUD_DETAIL_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 受款人在學資料 for 失能年金<br>
     * 將 benData Case List 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryDisabledStudDetailDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryDisabledStudDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_DISABLED_STUD_DETAIL_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 最新紓困貸款資料<br>
     * 將 LoanMasterDataCase 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryLoanMasterDataCase(PaymentQueryLoanDataCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryLoanMasterDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_LOAN_MASTER_DATA_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 最新紓困貸款資料<br>
     * 從 Session 中取得 LoanMasterDataCase
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static PaymentQueryLoanDataCase getPaymentQueryLoanMasterDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryLoanMasterDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryLoanDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_LOAN_MASTER_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 最新紓困貸款資料<br>
     * 將 LoanMasterDataCase 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryLoanMasterDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryLoanMasterDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_LOAN_MASTER_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 紓困貸款 核定資料<br>
     * 將 LoanMasterDataCase 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryLoanDetailDataCaseList(List<PaymentQueryLoanDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryLoanDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_LOAN_DETAIL_DATA_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 紓困貸款 核定資料<br>
     * 從 Session 中取得 LoanMasterDataCase
     * 
     * @return PaymentQueryIssuPayDataCase
     */
    public static List<PaymentQueryLoanDataCase> getPaymentQueryLoanDetailDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryLoanDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryLoanDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_LOAN_DETAIL_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 紓困貸款 核定資料<br>
     * 將 LoanMasterDataCase 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryLoanDetailDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryLoanDetailDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_LOAN_DETAIL_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 平均薪資 年資資料<br>
     * 將 avgAmtSeniDataCase 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryAvgAmtSeniDataCase(PaymentQueryAvgAmtDataCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryAvgAmtSeniDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_AVGAMT_SENI_DATA_CASE, caseObj);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 平均薪資 年資資料<br>
     * 從 Session 中取得 avgAmtSeniDataCase
     * 
     * @return PaymentQueryAvgAmtDataCase
     */
    public static PaymentQueryAvgAmtDataCase getPaymentQueryAvgAmtSeniDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryAvgAmtSeniDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryAvgAmtDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_AVGAMT_SENI_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 平均薪資 年資資料<br>
     * 將 avgAmtSeniDataCase 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryAvgAmtSeniDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryAvgAmtSeniDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_AVGAMT_SENI_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 平均薪資 最高60個月平均薪資明細<br>
     * 將 sixtyMonAvgAmtDataList 加入 Session 中
     * 
     * @param caseList
     * @param request
     */
    public static void setPaymentQuerySixtyMonAvgAmtDataCaseList(List<PaymentQueryAvgAmtDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQuerySixtyMonAvgAmtDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_SIXTY_MON_AVGAMT_DATA_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 平均薪資 最高60個月平均薪資明細<br>
     * 從 Session 中取得 sixtyMonAvgAmtDataList
     * 
     * @return List<PaymentQueryAvgAmtDataCase>
     */
    public static List<PaymentQueryAvgAmtDataCase> getPaymentQuerySixtyMonAvgAmtDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQuerySixtyMonAvgAmtDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryAvgAmtDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_SIXTY_MON_AVGAMT_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 平均薪資 最高60個月平均薪資明細<br>
     * 將 sixtyMonAvgAmtDataList 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQuerySixtyMonAvgAmtDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQuerySixtyMonAvgAmtDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_SIXTY_MON_AVGAMT_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 物價指數調整資料<br>
     * 將 cpiDataCaseList 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryCpiDataCaseList(List<PaymentQueryCpiDataCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryCpiDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_CPI_DATA_CASE_LIST, list);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 物價指數調整資料<br>
     * 從 Session 中取得 cpiDataCaseList
     * 
     * @return PaymentQueryAvgAmtDataCase
     */
    public static List<PaymentQueryCpiDataCase> getPaymentQueryCpiDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryCpiDataCase() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryCpiDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_CPI_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 物價指數調整資料<br>
     * 將 cpiDataCaseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryCpiDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryCpiDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_CPI_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 物價指數調整記錄資料<br>
     * 將 cpiRecDataCaseList 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryCpiRecDataCaseList(List<PaymentQueryCpiDataCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryCpiRecDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_CPIREC_DATA_CASE_LIST, list);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 物價指數調整記錄資料<br>
     * 從 Session 中取得 cpiRecDataCaseList
     * 
     * @return PaymentQueryAvgAmtDataCase
     */
    public static List<PaymentQueryCpiDataCase> getPaymentQueryCpiRecDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryCpiRecDataCase() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryCpiDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_CPIREC_DATA_CASE_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 物價指數調整記錄資料<br>
     * 將 cpiRecDataCaseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryCpiRecDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryCpiRecDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_CPIREC_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢作業 - 歸檔記錄資料<br>
     * 將 ArclistDataCase 加入 Session 中
     * 
     * @param dataCase
     * @param request
     */
    public static void setPaymentQueryArclistDataCase(PaymentQueryArclistDataCase dataCase, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryArclistDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_ARCLIST_CASE, dataCase);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 歸檔記錄資料<br>
     * 從 Session 中取得 ArclistDataCase
     * 
     * @return PaymentQueryArclistDataCase
     */
    public static PaymentQueryArclistDataCase getPaymentQueryArclistDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryArclistDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryArclistDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_ARCLIST_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 歸檔記錄資料<br>
     * 將 PaymentQueryArclistDataCase 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryArclistDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryArclistDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_ARCLIST_CASE);
    }

    // ---------------------------------------------------------------------------------------
    
    /**
     * 查詢作業 - 給付查詢作業 - 核定通知記錄資料<br>
     * 將 PaymentQueryUnqualifiedNoticeDataCase 加入 Session 中
     * 
     * @param dataCase
     * @param request
     */
    public static void setPaymentQueryUnqualifiedNoticeDataCase(PaymentQueryUnqualifiedNoticeDataCase dataCase, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryUnqualifiedNoticeDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_UNQUALIFIED_NOTICE_CASE, dataCase);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 核定通知記錄資料<br>
     * 從 Session 中取得 PaymentQueryUnqualifiedNoticeDataCase
     * 
     * @return PaymentQueryUnqualifiedNoticeDataCase
     */
    public static PaymentQueryUnqualifiedNoticeDataCase getPaymentQueryUnqualifiedNoticeDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryArclistDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryUnqualifiedNoticeDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_UNQUALIFIED_NOTICE_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 核定通知記錄資料<br>
     * 將 PaymentQueryUnqualifiedNoticeDataCase 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryUnqualifiedNoticeDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryUnqualifiedNoticeDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_UNQUALIFIED_NOTICE_CASE);
    }

    // ---------------------------------------------------------------------------------------
    
    /**
     * 查詢作業 - 給付查詢作業<br>
     * 將所有 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllPaymentQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllPaymentQueryCase() ...");

        HttpSession session = request.getSession();
        removePaymentQueryMasterCaseList(request);
        removePaymentQueryDetailDataCase(request);
        removePaymentQueryIssuPayDataCaseList(request);
        removePaymentQueryOrigIssuPayDataCaseList(request);
        removePaymentQueryIssuPayDetailCase(request);
        removePaymentQueryAlreadyReceiveCaseList(request);
        removePaymentQueryBenDataCaseList(request);
        removePaymentQuerySeniDataCase(request);
        removePaymentQueryOncePayDataCase(request);
        removePaymentQueryOncePayModifyDataCaseList(request);
        removeOncePayDetailDataCase(request);
        removeAnnuityPayDataList(request);
        removeCriPayApplyDataList(request);
        removeDiePayApplyDataCase(request);
        removeInjPayApplyDataList(request);
        removeUnEmpPayApplyDataList(request);
        removeNpPayApplyDataCase(request);
        removePaymentQueryEvtChkFileDataCase(request);
        removePaymentQueryBenChkFileDataCase(request);
        removePaymentQueryMatchChkFileDataCase(request);
        removePaymentQueryLetterTypeMk1List(request);
        removePaymentQueryLetterTypeMk2List(request);
        removePaymentQueryLetterTypeMk3List(request);
        removePaymentQueryLetterTypeMk4List(request);
        removePaymentQueryLetterTypeMk5List(request);
        removePaymentQueryLetterTypeMk6List(request);
        removePaymentQueryOccAccDataCase(request);
        removePaymentQueryDisabledCase(request);
        removePaymentQueryFamilyDataList(request);
        removePaymentQueryReFeesMasterDataList(request);
        removePaymentQueryReFeesDetailDataCase(request);
        removePaymentQueryStudDetailDataCaseList(request);
        removePaymentQueryHandicapDetailDataCaseList(request);
        removePaymentQueryDisabledStudDetailDataCaseList(request);
        removePaymentQueryLoanMasterDataCase(request);
        removePaymentQueryLoanDetailDataCaseList(request);
        removePaymentQueryAvgAmtSeniDataCase(request);
        removePaymentQuerySixtyMonAvgAmtDataCaseList(request);
        removePaymentQueryCpiDataCase(request);
        removePaymentQueryCpiRecDataCase(request);
        removePaymentQueryArclistDataCase(request);
        removePaymentQueryNpDataCase(request);
    }

    // ---------------------------------------------------------------------------------------
    // 查詢作業 - 應收查詢作業- ReceivableQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 應收查詢作業 - Master資料<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReceivableQueryMasterCase(ReceivableQueryMasterCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setReceivableQueryMasterCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RECEIVABLE_QUERY_MASTER_CASE, caseObj);
    }

    /**
     * 查詢作業 - 應收查詢作業 - Master資料<br>
     * 從 Session 中取得相關 Case
     * 
     * @return List<PaymentQueryMasterCase>
     */
    public static ReceivableQueryMasterCase getReceivableQueryMasterCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReceivableQueryMasterCase() ...");

        HttpSession session = request.getSession();
        return (ReceivableQueryMasterCase) session.getAttribute(ConstantKey.RECEIVABLE_QUERY_MASTER_CASE);
    }

    /**
     * 查詢作業 - 應收查詢作業 - Master資料<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReceivableQueryMasterCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReceivableQueryMasterCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.RECEIVABLE_QUERY_MASTER_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 應收查詢作業 - Master資料清單<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReceivableQueryMasterCaseList(List<ReceivableQueryMasterCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryMasterCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RECEIVABLE_QUERY_MASTER_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 應收查詢作業 - Master資料清單<br>
     * 從 Session 中取得相關 Case
     * 
     * @return List<PaymentQueryMasterCase>
     */
    public static List<ReceivableQueryMasterCase> getReceivableQueryMasterCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReceivableQueryMasterCaseList() ...");

        HttpSession session = request.getSession();
        return (List<ReceivableQueryMasterCase>) session.getAttribute(ConstantKey.RECEIVABLE_QUERY_MASTER_CASE_LIST);
    }

    /**
     * 查詢作業 - 應收查詢作業 - Master資料清單<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReceivableQueryMasterCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReceivableQueryMasterCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.RECEIVABLE_QUERY_MASTER_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 應收查詢作業 - Detail資料清單<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReceivableQueryDetailCaseList(List<ReceivableQueryDetailCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setReceivableQueryDetailCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RECEIVABLE_QUERY_DETAIL_CASE_LIST, caseList);
    }

    /**
     * 查詢作業 - 應收查詢作業 - Detail資料清單<br>
     * 從 Session 中取得相關 Case
     * 
     * @return List<PaymentQueryMasterCase>
     */
    public static List<ReceivableQueryDetailCase> getReceivableQueryDetailCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReceivableQueryDetailCaseList() ...");

        HttpSession session = request.getSession();
        return (List<ReceivableQueryDetailCase>) session.getAttribute(ConstantKey.RECEIVABLE_QUERY_DETAIL_CASE_LIST);
    }

    /**
     * 查詢作業 - 應收查詢作業 - Master資料清單<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReceivableQueryDetailCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReceivableQueryDetailCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.RECEIVABLE_QUERY_DETAIL_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 應收查詢作業<br>
     * 將所有 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllReceivableQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllReceivableQueryCase() ...");

        HttpSession session = request.getSession();
        removeReceivableQueryMasterCaseList(request);
        removeReceivableQueryDetailCaseList(request);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 國保資料<br>
     * 將 PaymentQueryNpDataCase 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentQueryNpDataCase(PaymentQueryNpDataCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryNpDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_NP_DATA_CASE, caseData);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 國保資料<br>
     * 從 Session 中取得 PaymentQueryNpDataCase
     * 
     * @return PaymentQueryNpDataCase
     */
    public static PaymentQueryNpDataCase getPaymentQueryNpDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryNpDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryNpDataCase) session.getAttribute(ConstantKey.PAYMENT_QUERY_NP_DATA_CASE);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 國保資料<br>
     * 將 PaymentQueryNpDataCase 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryNpDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryNpDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_NP_DATA_CASE);
    }

 // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 國保核定資料<br>
     * 將 List<PaymentQueryNpIssuDataCase> 加入 Session 中
     * 
     * @param caseList
     * @param request
     */
    public static void setPaymentQueryNpIssuDataList(List<PaymentQueryNpIssuDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryNpIssuDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_NP_ISSU_DATA_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 國保核定資料<br>
     * 從 Session 中取得 List<PaymentQueryNpIssuDataCase>
     * 
     * @return List<PaymentQueryNpIssuDataCase>
     */
    public static List<PaymentQueryNpIssuDataCase> getPaymentQueryNpIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryNpIssuDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentQueryNpIssuDataCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_NP_ISSU_DATA_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 國保核定資料<br>
     * 將 List<PaymentQueryNpIssuDataCase> 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryNpIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryNpIssuDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_NP_ISSU_DATA_LIST);
    }

 // ---------------------------------------------------------------------------------------

    /**
     * 查詢作業 - 給付查詢作業 - 國保核定資料(原始)<br>
     * 將 List<Nbdapr> 加入 Session 中
     * 
     * @param caseList
     * @param request
     */
    public static void setPaymentQueryOrigNpIssuDataList(List<Nbdapr> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryOrigNpIssuDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_ORIG_NP_ISSU_DATA_LIST, caseList);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 國保核定資料(原始)<br>
     * 從 Session 中取得 List<PaymentQueryNpIssuDataCase>
     * 
     * @return List<Nbdapr>
     */
    public static List<Nbdapr> getPaymentQueryOrigNpIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentQueryOrigNpIssuDataList() ...");

        HttpSession session = request.getSession();
        return (List<Nbdapr>) session.getAttribute(ConstantKey.PAYMENT_QUERY_ORIG_NP_ISSU_DATA_LIST);
    }

    /**
     * 查詢作業 - 給付查詢作業 - 國保核定資料(原始)<br>
     * 將 List<PaymentQueryNpIssuDataCase> 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryOrigNpIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentQueryOrigNpIssuDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_ORIG_NP_ISSU_DATA_LIST);
    }
    
    /**
     * 給付查詢作業 - 重新審核失能程度  <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledApplicationDataUpdateBareCheckCaseList(List<DisabledApplicationDataUpdateBareCheckCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledApplicationDataUpdateBareCheckCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_BARE_CHECK_CASE_LIST, caseList);
    }

    /**
     * 給付查詢作業 - 案件資料更正 - 重新審核失能程度 <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static List<DisabledApplicationDataUpdateBareCheckCase> getDisabledApplicationDataUpdateBareCheckCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledApplicationDataUpdateBareCheckCaseList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledApplicationDataUpdateBareCheckCase>) session.getAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_BARE_CHECK_CASE_LIST);
    }

    /**
     * 給付查詢作業 - 重新審核失能程度 <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledApplicationDataUpdateBareCheckCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledApplicationDataUpdateBareCheckCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_BARE_CHECK_CASE_LIST);
    }
    
    // ---------------------------------------------------------------------------------------
    // 給付查詢 - 重新審核失能程度
    // ---------------------------------------------------------------------------------------
    /**
     * 給付查詢 - 重新審核失能程度 <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledApplicationDataUpdateCase(DisabledApplicationDataUpdateCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_CASE, caseData);
    }

    /**
     * 給付查詢 - 重新審核失能程度 <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static DisabledApplicationDataUpdateCase getDisabledApplicationDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        return (DisabledApplicationDataUpdateCase) session.getAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_CASE);
    }

    /**
     * 給付查詢 - 重新審核失能程度 <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledApplicationDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_CASE);
    }
    
    

    // ---------------------------------------------------------------------------------------

    
    // ---------------------------------------------------------------------------------------
    // 查詢作業 - 年金統計查詢
    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 年金統計查詢 <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setAnnuityStatisticsCase(AnnuityStatisticsCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setAnnuityStatisticsCaseCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute("AnnuityStatisticsCase", caseData);
    }

    /**
     * 查詢作業 - 年金統計查詢 <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static AnnuityStatisticsCase getAnnuityStatisticsCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getAnnuityStatisticsCase() ...");

        HttpSession session = request.getSession();
        return (AnnuityStatisticsCase) session.getAttribute("AnnuityStatisticsCase");
    }

    /**
     * 查詢作業 - 年金統計查詢 <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAnnuityStatisticsCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAnnuityStatisticsCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute("AnnuityStatisticsCase");
    }
    
    

    // ---------------------------------------------------------------------------------------
}
