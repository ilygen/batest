package tw.gov.bli.ba.review.helper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.MonthCheckKCase;
import tw.gov.bli.ba.bj.cases.MonthCheckLCase;
import tw.gov.bli.ba.bj.cases.MonthCheckSCase;
import tw.gov.bli.ba.bj.cases.MonthKCase;
import tw.gov.bli.ba.bj.cases.MonthLCase;
import tw.gov.bli.ba.bj.cases.MonthQueryCase;
import tw.gov.bli.ba.bj.cases.MonthSCase;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewCase;
import tw.gov.bli.ba.review.cases.DisabledPaymentReviewExtCase;
import tw.gov.bli.ba.review.cases.PaymentReviewCase;
import tw.gov.bli.ba.review.cases.PaymentReviewExtCase;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewCase;
import tw.gov.bli.ba.review.cases.SurvivorPaymentReviewExtCase;

/**
 * Case Helper for 審核作業
 * 
 * @author Rickychi
 */
public class CaseSessionHelper {
    private static Log log = LogFactory.getLog(CaseSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 審核作業 - 給付審核作業 - PaymentReview
    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業 - 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentReviewList(List<PaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_LIST, list);
    }

    /**
     * 審核作業 - 給付審核作業 - 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewCase> getPaymentReviewList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_LIST);
    }

    /**
     * 審核作業 - 給付審核作業- 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業 - 受理編號 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentReviewCase(PaymentReviewCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_CASE, obj);
    }

    /**
     * 審核作業 - 給付審核作業 - 受理編號 查詢結果<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentReviewCase getPaymentReviewCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewCase() ...");

        HttpSession session = request.getSession();
        return (PaymentReviewCase) session.getAttribute(ConstantKey.PAYMENT_REVIEW_CASE);
    }

    /**
     * 審核作業 - 給付審核作業 - 受理編號 查詢結果<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_CASE);
    }

    /**
     * 審核作業 - 給付審核作業 - 查詢結果 TITLE<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentReviewCaseForTitle(PaymentReviewCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_CASE_FOR_TITLE, obj);
    }

    /**
     * 審核作業 - 給付審核作業 - 查詢結果 TITLE<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentReviewCase getPaymentReviewCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewCaseForTitle() ...");

        HttpSession session = request.getSession();
        return (PaymentReviewCase) session.getAttribute(ConstantKey.PAYMENT_REVIEW_CASE_FOR_TITLE);
    }

    /**
     * 審核作業 - 給付審核作業 - 查詢結果 TITLE<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_CASE_FOR_TITLE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentReviewChkList(List<PaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_CHK_LIST, list);
    }

    /**
     * 審核作業 - 給付審核作業 - 事故者編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewExtCase> getPaymentReviewChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewChkList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewExtCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_CHK_LIST);
    }

    /**
     * 審核作業 - 給付審核作業- 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_CHK_LIST);
    }
    
 // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 受款人編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentReviewBenChkList(List<PaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewBenChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_BEN_CHK_LIST, list);
    }

    /**
     * 審核作業 - 給付審核作業 - 受款人編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewCase> getPaymentReviewBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewBenChkList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_BEN_CHK_LIST);
    }

    /**
     * 審核作業 - 給付審核作業- 受款人編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewBenChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_BEN_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 受款人 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentReviewBenNameList(List<PaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewBenNameList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_BENNAME_LIST, list);
    }

    /**
     * 審核作業 - 給付審核作業 - 受款人 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewExtCase> getPaymentReviewBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewBenNameList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewExtCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_BENNAME_LIST);
    }

    /**
     * 審核作業 - 給付審核作業- 受款人 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewBenNameList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_BENNAME_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 欠費期間資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSenimaintDataList(List<PaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSenimaintDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_SENIMAINT_DATA_LIST, list);
    }

    /**
     * 審核作業 - 給付審核作業 - 欠費期間資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewExtCase> getSenimaintDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSenimaintDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewExtCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_SENIMAINT_DATA_LIST);
    }

    /**
     * 審核作業 - 給付審核作業- 欠費期間資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSenimaintDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSenimaintDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_SENIMAINT_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 承保異動資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCiptDataList(List<PaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCiptDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_CIPT_DATA_LIST, list);
    }

    /**
     * 審核作業 - 給付審核作業 - 承保異動資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewExtCase> getCiptDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getCiptDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewExtCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_CIPT_DATA_LIST);
    }

    /**
     * 審核作業 - 給付審核作業- 承保異動資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCiptDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCiptDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_CIPT_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPayDataList(List<PaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPayDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_PAY_DATA_LIST, list);
    }

    /**
     * 審核作業 - 給付審核作業 - 編審給付資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewExtCase> getPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPayDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewExtCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_PAY_DATA_LIST);
    }

    /**
     * 審核作業 - 給付審核作業- 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePayDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_PAY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 一次給付資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setOncePayDataCase(PaymentReviewExtCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setOncePayDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_ONCEPAY_DATA_CASE, caseObj);
    }

    /**
     * 審核作業 - 給付審核作業 - 一次給付資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static PaymentReviewExtCase getOncePayDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getOncePayDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentReviewExtCase) session.getAttribute(ConstantKey.PAYMENT_REVIEW_ONCEPAY_DATA_CASE);
    }

    /**
     * 審核作業 - 給付審核作業- 一次給付資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOncePayDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeOncePayDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_ONCEPAY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 一次給付更正資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setOncePayModifyDataList(List<PaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setOncePayModifyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_ONCEPAYMODIFY_DATA_LIST, list);
    }

    /**
     * 審核作業 - 給付審核作業 - 一次給付更正資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewExtCase> getOncePayModifyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getOncePayModifyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewExtCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_ONCEPAYMODIFY_DATA_LIST);
    }

    /**
     * 審核作業 - 給付審核作業- 一次給付更正資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOncePayModifyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeOncePayModifyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_ONCEPAYMODIFY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 核定通知書資料 查詢結果<br>
     * 將 查詢結果 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setNotifyData(PaymentReviewExtCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setNotifyData() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_NOTIFY_DATA_CASE, caseObj);
    }

    /**
     * 審核作業 - 給付審核作業 - 核定通知書資料 查詢結果<br>
     * 自 Session 中取得 查詢結果
     * 
     * @param request
     * @return
     */
    public static PaymentReviewExtCase getNotifyData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getNotifyData() ...");

        HttpSession session = request.getSession();
        return (PaymentReviewExtCase) session.getAttribute(ConstantKey.PAYMENT_REVIEW_NOTIFY_DATA_CASE);
    }

    /**
     * 審核作業 - 給付審核作業- 核定通知書資料 查詢結果<br>
     * 將 查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeNotifyData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeNotifyData() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_NOTIFY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 受款人核定資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setBenIssueDataList(List<PaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setBenIssueDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_BENISSUE_DATA_LIST, list);
    }

    /**
     * 審核作業 - 給付審核作業 - 受款人核定資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewExtCase> getBenIssueDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getBenIssueDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewExtCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_BENISSUE_DATA_LIST);
    }

    /**
     * 審核作業 - 給付審核作業- 受款人核定資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeBenIssueDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeBenIssueDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_BENISSUE_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 受款人資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setBeneficiaryDataList(List<PaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_BENEFICIARY_DATA_LIST, list);
    }

    /**
     * 審核作業 - 給付審核作業 - 受款人資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewCase> getBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_BENEFICIARY_DATA_LIST);
    }

    /**
     * 審核作業 - 給付審核作業- 受款人資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_BENEFICIARY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setOncePayDetailDataCase(PaymentReviewExtCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setOncePayDetailDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_ONCEPAY_DETAIL_DATA_CASE, caseObj);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static PaymentReviewExtCase getOncePayDetailDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getOncePayDetailDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentReviewExtCase) session.getAttribute(ConstantKey.PAYMENT_REVIEW_ONCEPAY_DETAIL_DATA_CASE);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOncePayDetailDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeOncePayDetailDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_ONCEPAY_DETAIL_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setAnnuityPayDataList(List<PaymentReviewExtCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setAnnuityPayDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_ANNUITYPAY_DATA_CASE_LIST, caseList);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewExtCase> getAnnuityPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getAnnuityPayDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewExtCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_ANNUITYPAY_DATA_CASE_LIST);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAnnuityPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAnnuityPayDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_ANNUITYPAY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setCriPayApplyDataList(List<PaymentReviewExtCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCriPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_CRIPAY_APPLY_DATA_CASE_LIST, caseList);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewExtCase> getCriPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getCriPayApplyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewExtCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_CRIPAY_APPLY_DATA_CASE_LIST);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCriPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCriPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_CRIPAY_APPLY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDiePayApplyDataCase(PaymentReviewExtCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDiePayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_DIEPAY_APPLY_DATA_CASE, caseObj);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static PaymentReviewExtCase getDiePayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDiePayApplyDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentReviewExtCase) session.getAttribute(ConstantKey.PAYMENT_REVIEW_DIEPAY_APPLY_DATA_CASE);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDiePayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDiePayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_DIEPAY_APPLY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setInjPayApplyDataList(List<PaymentReviewExtCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setInjPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_INJPAY_APPLY_DATA_CASE_LIST, caseList);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewExtCase> getInjPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getInjPayApplyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewExtCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_INJPAY_APPLY_DATA_CASE_LIST);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeInjPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeInjPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_INJPAY_APPLY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄資料 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setUnEmpPayApplyDataList(List<PaymentReviewExtCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setUnEmpPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_UNEMPPAY_APPLY_DATA_CASE_LIST, caseList);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄資料 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewExtCase> getUnEmpPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getUnEmpPayApplyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewExtCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_UNEMPPAY_APPLY_DATA_CASE_LIST);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄資料 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUnEmpPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeUnEmpPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_UNEMPPAY_APPLY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄資料 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setNpPayApplyDataCase(PaymentReviewExtCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setNpPayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_NPPAY_APPLY_DATA_CASE, caseObj);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄資料 查詢結果<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentReviewExtCase getNpPayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getNpPayApplyDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentReviewExtCase) session.getAttribute(ConstantKey.PAYMENT_REVIEW_NPPAY_APPLY_DATA_CASE);
    }

    /**
     * 審核作業 - 給付審核作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄資料 查詢結果<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeNpPayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeNpPayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_NPPAY_APPLY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業 - 交查函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentReviewLetterTypeMk1List(List<PaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_1_LIST, caseList);
    }

    /**
     * 審核作業 - 給付審核作業 - 交查函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewCase> getPaymentReviewLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_1_LIST);
    }

    /**
     * 審核作業 - 給付審核作業 - 交查函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_1_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業 - 不給付函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentReviewLetterTypeMk2List(List<PaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_2_LIST, caseList);
    }

    /**
     * 審核作業 - 給付審核作業 - 不給付函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewCase> getPaymentReviewLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_2_LIST);
    }

    /**
     * 審核作業 - 給付審核作業 - 不給付函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_2_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業 - 補件通知函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentReviewLetterTypeMk3List(List<PaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_3_LIST, caseList);
    }

    /**
     * 審核作業 - 給付審核作業 - 補件通知函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewCase> getPaymentReviewLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_3_LIST);
    }

    /**
     * 審核作業 - 給付審核作業 - 補件通知函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_3_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業 - 照會函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentReviewLetterTypeMk4List(List<PaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_4_LIST, caseList);
    }

    /**
     * 審核作業 - 給付審核作業 - 照會函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewCase> getPaymentReviewLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_4_LIST);
    }

    /**
     * 審核作業 - 給付審核作業 - 照會函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_4_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業 - 其他簽函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentReviewLetterTypeMk5List(List<PaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_5_LIST, caseList);
    }

    /**
     * 審核作業 - 給付審核作業 - 其他簽函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentReviewCase> getPaymentReviewLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentReviewCase>) session.getAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_5_LIST);
    }

    /**
     * 審核作業 - 給付審核作業 - 其他簽函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_5_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentReviewLetterTypeMk6List(PaymentReviewCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentReviewLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_6_LIST, caseObj);
    }

    /**
     * 審核作業 - 給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentReviewCase getPaymentReviewLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentReviewLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        return (PaymentReviewCase) session.getAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_6_LIST);
    }

    /**
     * 審核作業 - 給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReviewLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_LETTER_TYPE_6_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業<br>
     * 將 所有查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllPaymentReviewCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllPaymentReviewCase() ...");

        HttpSession session = request.getSession();
        removeBeneficiaryDataList(request);
        removeBenIssueDataList(request);
        removeCiptDataList(request);
        removeOncePayDataCase(request);
        removeOncePayModifyDataList(request);
        removePayDataList(request);
        removePaymentReviewBenNameList(request);
        removePaymentReviewCase(request);
        removePaymentReviewCaseForTitle(request);
        removePaymentReviewChkList(request);
        removePaymentReviewBenChkList(request);
        removePaymentReviewList(request);
        removeSenimaintDataList(request);
        removeOncePayDetailDataCase(request);
        removeCriPayApplyDataList(request);
        removeDiePayApplyDataCase(request);
        removeInjPayApplyDataList(request);
        removeUnEmpPayApplyDataList(request);
        removeAnnuityPayDataList(request);
        removeNpPayApplyDataCase(request);
        removeNotifyData(request);
        removePaymentReviewLetterTypeMk1List(request);
        removePaymentReviewLetterTypeMk2List(request);
        removePaymentReviewLetterTypeMk3List(request);
        removePaymentReviewLetterTypeMk4List(request);
        removePaymentReviewLetterTypeMk5List(request);
        removePaymentReviewLetterTypeMk6List(request);
    }

    // ---------------------------------------------------------------------------------------
    // 審核作業 - 失能年金給付審核作業 - DisabledPaymentReview
    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 失能年金給付審核作業 - 受理編號 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentReviewCase(DisabledPaymentReviewCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_CASE, obj);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 受理編號 查詢結果<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static DisabledPaymentReviewCase getDisabledPaymentReviewCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewCase() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentReviewCase) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_CASE);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 受理編號 查詢結果<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 失能年金給付審核作業 - 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentReviewList(List<DisabledPaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LIST, list);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewCase> getDisabledPaymentReviewList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業- 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 失能年金給付審核作業 - 查詢結果 TITLE<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentReviewCaseForTitle(DisabledPaymentReviewCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_CASE_FOR_TITLE, obj);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 查詢結果 TITLE<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentReviewCase getDisabledPaymentReviewCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewCaseForTitle() ...");

        HttpSession session = request.getSession();
        return (PaymentReviewCase) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_CASE_FOR_TITLE);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 查詢結果 TITLE<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_CASE_FOR_TITLE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 失能年金給付審核作業 - 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPayDataList(List<DisabledPaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPayDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_PAY_DATA_LIST, list);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 編審給付資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewExtCase> getDisabledPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPayDataList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewExtCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_PAY_DATA_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業- 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPayDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_PAY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 失能年金給付審核作業 - 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentReviewEvtChkList(List<DisabledPaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewEvtChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_EVT_CHK_LIST, list);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 事故者編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewExtCase> getDisabledPaymentReviewEvtChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewEvtChkList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewExtCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_EVT_CHK_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業- 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewEvtChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewEvtChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_EVT_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 失能年金給付審核作業 - 眷屬編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentReviewBenChkList(List<DisabledPaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewBenChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_BEN_CHK_LIST, list);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 眷屬編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewCase> getDisabledPaymentReviewBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewBenChkList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_BEN_CHK_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業- 眷屬編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewBenChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_BEN_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 失能年金給付審核作業 - 符合註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentReviewMatchChkList(List<DisabledPaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewMatchChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_MATCH_CHK_LIST, list);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 符合註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewCase> getDisabledPaymentReviewMatchChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewMatchChkList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_MATCH_CHK_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業- 符合註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewMatchChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewMatchChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_MATCH_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 失能年金給付審核作業 - 受款人資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledBeneficiaryDataList(List<DisabledPaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_BENEFICIARY_DATA_LIST, list);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 受款人資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewCase> getDisabledBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_BENEFICIARY_DATA_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業- 受款人資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_BENEFICIARY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 失能年金給付審核作業 - 受款人 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentReviewBenNameList(List<DisabledPaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewBenNameList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_BENNAME_LIST, list);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 受款人 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewExtCase> getDisabledPaymentReviewBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewBenNameList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewExtCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_BENNAME_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業- 受款人 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewBenNameList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_BENNAME_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 失能年金給付審核作業 - 交查函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentReviewLetterTypeMk1List(List<DisabledPaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_1_LIST, caseList);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 交查函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewCase> getDisabledPaymentReviewLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_1_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 交查函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_1_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 失能年金給付審核作業 - 不給付函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentReviewLetterTypeMk2List(List<DisabledPaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_2_LIST, caseList);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 不給付函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewCase> getDisabledPaymentReviewLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_2_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 不給付函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_2_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 失能年金給付審核作業 - 補件通知函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentReviewLetterTypeMk3List(List<DisabledPaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_3_LIST, caseList);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 補件通知函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewCase> getDisabledPaymentReviewLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_3_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 補件通知函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_3_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 失能年金給付審核作業 - 照會函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentReviewLetterTypeMk4List(List<DisabledPaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_4_LIST, caseList);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 照會函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewCase> getDisabledPaymentReviewLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_4_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 照會函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_4_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 失能年金給付審核作業 - 其他簽函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentReviewLetterTypeMk5List(List<DisabledPaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_5_LIST, caseList);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 其他簽函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentReviewCase> getDisabledPaymentReviewLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentReviewCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_5_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 其他簽函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_5_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 失能年金給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentReviewLetterTypeMk6List(DisabledPaymentReviewCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_6_LIST, caseObj);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static DisabledPaymentReviewCase getDisabledPaymentReviewLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentReviewCase) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_6_LIST);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_LETTER_TYPE_6_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 失能年金給付審核作業 - 職災相關資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentReviewOccAccData(DisabledPaymentReviewCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewOccAccData() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_OCCACC_DATA, obj);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 職災相關資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static DisabledPaymentReviewCase getDisabledPaymentReviewOccAccData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewOccAccData() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentReviewCase) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_OCCACC_DATA);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 職災相關資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewOccAccData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewOccAccData() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_OCCACC_DATA);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 失能年金給付審核作業 - 失能相關資料<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentReviewDisabledData(DisabledPaymentReviewCase list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentReviewDisabledData() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_DISABLED_DATA, list);
    }

    /**
     * 審核作業 - 失能年金給付審核作業 - 失能相關資料<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static DisabledPaymentReviewCase getDisabledPaymentReviewDisabledData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReviewDisabledData() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentReviewCase) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_DISABLED_DATA);
    }

    /**
     * 審核作業 - 失能年金給付審核作業- 失能相關資料<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewDisabledData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReviewDisabledData() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_DISABLED_DATA);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 失能年金給付審核作業<br>
     * 將 所有查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllDisabledPaymentReviewCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllDisabledPaymentReviewCase() ...");

        HttpSession session = request.getSession();
        removeDisabledPaymentReviewList(request);
        removeDisabledPayDataList(request);
        removeDisabledPaymentReviewEvtChkList(request);
        removeDisabledPaymentReviewBenChkList(request);
        removeDisabledPaymentReviewMatchChkList(request);
        removeDisabledBeneficiaryDataList(request);
        removeDisabledPaymentReviewBenNameList(request);
        removeDisabledPaymentReviewLetterTypeMk1List(request);
        removeDisabledPaymentReviewLetterTypeMk2List(request);
        removeDisabledPaymentReviewLetterTypeMk3List(request);
        removeDisabledPaymentReviewLetterTypeMk4List(request);
        removeDisabledPaymentReviewLetterTypeMk5List(request);
        removeDisabledPaymentReviewLetterTypeMk6List(request);
        removeDisabledPaymentReviewOccAccData(request);
        removeDisabledPaymentReviewDisabledData(request);
    }

    // ---------------------------------------------------------------------------------------
    // 審核作業 - 遺屬年金給付審核作業 - SurvivorPaymentReview
    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受理編號 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentReviewCase(SurvivorPaymentReviewCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_CASE, obj);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受理編號 查詢結果<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static SurvivorPaymentReviewCase getSurvivorPaymentReviewCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewCase() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentReviewCase) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_CASE);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受理編號 查詢結果<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentReviewList(List<SurvivorPaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LIST, list);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewCase> getSurvivorPaymentReviewList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業- 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 查詢結果 TITLE<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentReviewCaseForTitle(SurvivorPaymentReviewCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_CASE_FOR_TITLE, obj);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 查詢結果 TITLE<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentReviewCase getSurvivorPaymentReviewCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewCaseForTitle() ...");

        HttpSession session = request.getSession();
        return (PaymentReviewCase) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_CASE_FOR_TITLE);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 查詢結果 TITLE<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_CASE_FOR_TITLE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 遺屬年金給付審核作業 - 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPayDataList(List<SurvivorPaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPayDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_PAY_DATA_LIST, list);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 編審給付資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewExtCase> getSurvivorPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPayDataList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewExtCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_PAY_DATA_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業- 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPayDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_PAY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentReviewEvtChkList(List<SurvivorPaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewEvtChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_EVT_CHK_LIST, list);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 事故者編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewExtCase> getSurvivorPaymentReviewEvtChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewEvtChkList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewExtCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_EVT_CHK_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業- 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewEvtChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewEvtChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_EVT_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 眷屬編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentReviewBenChkList(List<SurvivorPaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewBenChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_CHK_LIST, list);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 眷屬編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewCase> getSurvivorPaymentReviewBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewBenChkList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_CHK_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業- 眷屬編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewBenChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 符合註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentReviewMatchChkList(List<SurvivorPaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewMatchChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_MATCH_CHK_LIST, list);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 符合註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewCase> getSurvivorPaymentReviewMatchChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewMatchChkList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_MATCH_CHK_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業- 符合註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewMatchChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewMatchChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_MATCH_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受款人資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorBeneficiaryDataList(List<SurvivorPaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BENEFICIARY_DATA_LIST, list);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受款人資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewCase> getSurvivorBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BENEFICIARY_DATA_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業- 受款人資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BENEFICIARY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受款人核定資料 原始查詢結果List<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorOrigBenIssuDataList(List<SurvivorPaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorOrigBenIssuDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_ORIG_BEN_ISSU_DATA_LIST, list);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受款人核定資料 原始查詢結果List<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewExtCase> getSurvivorOrigBenIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorOrigBenIssuDataList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewExtCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_ORIG_BEN_ISSU_DATA_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業- 受款人核定資料 原始查詢結果List<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorOrigBenIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorOrigBenIssuDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_ORIG_BEN_ISSU_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受款人核定資料 查詢結果List<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorBenIssuDataList(List<SurvivorPaymentReviewCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorBenIssuDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_ISSU_DATA_LIST, list);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受款人核定資料 查詢結果List<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewCase> getSurvivorBenIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorBenIssuDataList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_ISSU_DATA_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業- 受款人核定資料 查詢結果List<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorBenIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorBenIssuDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_ISSU_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受款人核定資料 查詢結果Map<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorBenIssuDataMap(Map<BigDecimal, SurvivorPaymentReviewExtCase> map, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorBenIssuDataMap() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_ISSU_DATA_MAP, map);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受款人核定資料 查詢結果Map<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static Map<BigDecimal, SurvivorPaymentReviewExtCase> getSurvivorBenIssuDataMap(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorBenIssuDataMap() ...");

        HttpSession session = request.getSession();
        return (Map<BigDecimal, SurvivorPaymentReviewExtCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_ISSU_DATA_MAP);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業- 受款人核定資料 查詢結果Map<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorBenIssuDataMap(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorBenIssuDataMap() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BEN_ISSU_DATA_MAP);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受款人 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentReviewBenNameList(List<SurvivorPaymentReviewExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewBenNameList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BENNAME_LIST, list);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 受款人 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewExtCase> getSurvivorPaymentReviewBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewBenNameList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewExtCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BENNAME_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業- 受款人 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewBenNameList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_BENNAME_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 遺屬年金給付審核作業 - 交查函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentReviewLetterTypeMk1List(List<SurvivorPaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_1_LIST, caseList);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 交查函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewCase> getSurvivorPaymentReviewLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_1_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 交查函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_1_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 遺屬年金給付審核作業 - 不給付函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentReviewLetterTypeMk2List(List<SurvivorPaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_2_LIST, caseList);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 不給付函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewCase> getSurvivorPaymentReviewLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_2_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 不給付函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_2_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 遺屬年金給付審核作業 - 補件通知函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentReviewLetterTypeMk3List(List<SurvivorPaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_3_LIST, caseList);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 補件通知函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewCase> getSurvivorPaymentReviewLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_3_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 補件通知函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_3_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 遺屬年金給付審核作業 - 照會函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentReviewLetterTypeMk4List(List<SurvivorPaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_4_LIST, caseList);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 照會函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewCase> getSurvivorPaymentReviewLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_4_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 照會函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_4_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 遺屬年金給付審核作業 - 其他簽函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentReviewLetterTypeMk5List(List<SurvivorPaymentReviewCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_5_LIST, caseList);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 其他簽函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentReviewCase> getSurvivorPaymentReviewLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentReviewCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_5_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 其他簽函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_5_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 遺屬年金給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentReviewLetterTypeMk6List(SurvivorPaymentReviewCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_6_LIST, caseObj);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static SurvivorPaymentReviewCase getSurvivorPaymentReviewLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentReviewCase) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_6_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_LETTER_TYPE_6_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 遺屬年金給付審核作業 - 職災相關資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentReviewOccAccData(SurvivorPaymentReviewCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewOccAccData() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_OCCACC_DATA, obj);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 職災相關資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static SurvivorPaymentReviewCase getSurvivorPaymentReviewOccAccData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewOccAccData() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentReviewCase) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_OCCACC_DATA);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 職災相關資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewOccAccData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewOccAccData() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_OCCACC_DATA);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 遺屬相關資料<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentReviewDisabledData(SurvivorPaymentReviewCase list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReviewSurvivorData() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_DISABLED_DATA, list);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業 - 遺屬相關資料<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static SurvivorPaymentReviewCase getSurvivorPaymentReviewDisabledData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReviewSurvivorData() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentReviewCase) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_DISABLED_DATA);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業- 遺屬相關資料<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewDisabledData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReviewDisabledData() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_DISABLED_DATA);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付審核作業<br>
     * 將 所有查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllSurvivorPaymentReviewCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllSurvivorPaymentReviewCase() ...");

        HttpSession session = request.getSession();
        removeSurvivorPaymentReviewList(request);
        removeSurvivorPayDataList(request);
        removeSurvivorPaymentReviewEvtChkList(request);
        removeSurvivorPaymentReviewBenChkList(request);
        removeSurvivorPaymentReviewMatchChkList(request);
        removeSurvivorBeneficiaryDataList(request);
        removeSurvivorPaymentReviewBenNameList(request);
        removeSurvivorPaymentReviewLetterTypeMk1List(request);
        removeSurvivorPaymentReviewLetterTypeMk2List(request);
        removeSurvivorPaymentReviewLetterTypeMk3List(request);
        removeSurvivorPaymentReviewLetterTypeMk4List(request);
        removeSurvivorPaymentReviewLetterTypeMk5List(request);
        removeSurvivorPaymentReviewLetterTypeMk6List(request);
        removeSurvivorPaymentReviewOccAccData(request);
        removeSurvivorPaymentReviewDisabledData(request);
        removeSurvivorOrigBenIssuDataList(request);
        removeSurvivorBenIssuDataList(request);
        removeSurvivorBenIssuDataMap(request);
    }

}
