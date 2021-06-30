package tw.gov.bli.ba.review.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.review.forms.DisabledPaymentReviewForm;
import tw.gov.bli.ba.review.forms.PaymentReviewForm;
import tw.gov.bli.ba.review.forms.SurvivorPaymentReviewForm;

/**
 * Form Helper for 審核作業
 * 
 * @author Rickychi
 */
public class FormSessionHelper {
    private static Log log = LogFactory.getLog(FormSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 審核作業 - 老年年金給付審核作業 - PaymentReview
    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setPaymentReviewForm(PaymentReviewForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setPaymentReviewForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_FORM, form);
    }

    /**
     * 審核作業 - 給付審核作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentReviewForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePaymentReviewForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 給付審核作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setReviewQueryForm(PaymentReviewForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReviewQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REVIEW_QUERY_FORM, form);
    }

    /**
     * 審核作業 - 給付審核作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static PaymentReviewForm getReviewQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getReviewQueryForm() ...");

        HttpSession session = request.getSession();
        return (PaymentReviewForm) session.getAttribute(ConstantKey.PAYMENT_REVIEW_QUERY_FORM);
    }

    /**
     * 審核作業 - 給付審核作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReviewQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReviewQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.PAYMENT_REVIEW_QUERY_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 審核作業 - 失能年金給付審核作業 - DisabledPaymentReview
    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 失能年金給付審核作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDisabledPaymentReviewForm(DisabledPaymentReviewForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledPaymentReviewForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_FORM, form);
    }

    /**
     * 審核作業 - 失能年金給付審核作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledPaymentReviewForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 失能年金給付審核作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDisabledPaymentReviewQueryForm(DisabledPaymentReviewForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledPaymentReviewQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_QUERY_FORM, form);
    }

    /**
     * 審核作業 - 失能年金給付審核作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static DisabledPaymentReviewForm getDisabledPaymentReviewQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledPaymentReviewQueryForm() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentReviewForm) session.getAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_QUERY_FORM);
    }

    /**
     * 審核作業 - 失能年金給付審核作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentReviewQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledPaymentReviewQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_REVIEW_QUERY_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 審核作業 - 遺屬年金給付審核作業 - SurvivorPaymentReview
    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 遺屬年金給付審核作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setSurvivorPaymentReviewForm(SurvivorPaymentReviewForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorPaymentReviewForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_FORM, form);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorPaymentReviewForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 審核作業 - 遺屬年金給付審核作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setSurvivorPaymentReviewQueryForm(SurvivorPaymentReviewForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorPaymentReviewQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_QUERY_FORM, form);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static SurvivorPaymentReviewForm getSurvivorPaymentReviewQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorPaymentReviewQueryForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentReviewForm) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_QUERY_FORM);
    }

    /**
     * 審核作業 - 遺屬年金給付審核作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentReviewQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorPaymentReviewQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_REVIEW_QUERY_FORM);
    }
    
    /**
     * 列印作業 - 補送在學通知函<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthlyRpt29Form(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthlyRpt29Form() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTHLY_RPT_29_FORM);
    }
    
    /**
     * 列印作業 - 查核失能程度通知函<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthlyRpt30Form(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthlyRpt30Form() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTHLY_RPT_30_FORM);
    }
    
    /**
     * 列印作業 - 老年差額金通知<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthlyRpt31Form(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthlyRpt31Form() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTHLY_RPT_31_FORM);
    }
}
