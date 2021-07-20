package tw.gov.bli.ba.decision.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.decision.forms.DisabledPaymentDecisionForm;
import tw.gov.bli.ba.decision.forms.PaymentDecisionForm;
import tw.gov.bli.ba.decision.forms.SurvivorPaymentDecisionForm;

/**
 * Form Helper for 決行作業
 * 
 * @author Rickychi
 */
public class FormSessionHelper {
    private static Log log = LogFactory.getLog(FormSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 決行作業 - 老年年金給付決行作業 - PaymentDecision
    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setPaymentDecisionForm(PaymentDecisionForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setPaymentDecisionForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_FORM, form);
    }

    /**
     * 決行作業 - 給付決行作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePaymentDecisionForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_FORM);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDecisionQueryForm(PaymentDecisionForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDecisionQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_QUERY_FORM, form);
    }

    /**
     * 決行作業 - 給付決行作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static PaymentDecisionForm getDecisionQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDecisionQueryForm() ...");

        HttpSession session = request.getSession();
        return (PaymentDecisionForm) session.getAttribute(ConstantKey.PAYMENT_DECISION_QUERY_FORM);
    }

    /**
     * 決行作業 - 給付決行作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDecisionQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDecisionQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_QUERY_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 決行作業 - 失能年金給付決行作業 - DisabledPaymentDecision
    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 失能年金給付決行作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDisabledPaymentDecisionForm(DisabledPaymentDecisionForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledPaymentDecisionForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_FORM, form);
    }

    /**
     * 決行作業 - 失能年金給付決行作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledPaymentDecisionForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_FORM);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 失能年金給付決行作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDisabledPaymentDecisionQueryForm(DisabledPaymentDecisionForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledPaymentDecisionQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_QUERY_FORM, form);
    }

    /**
     * 決行作業 - 失能年金給付決行作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static DisabledPaymentDecisionForm getDisabledPaymentDecisionQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledPaymentDecisionQueryForm() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentDecisionForm) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_QUERY_FORM);
    }

    /**
     * 決行作業 - 失能年金給付決行作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledPaymentDecisionQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_QUERY_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 決行作業 - 遺屬年金給付決行作業 - SurvivorPaymentDecision
    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 遺屬年金給付決行作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setSurvivorPaymentDecisionForm(SurvivorPaymentDecisionForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorPaymentDecisionForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_FORM, form);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorPaymentDecisionForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_FORM);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 遺屬年金給付決行作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setSurvivorPaymentDecisionQueryForm(SurvivorPaymentDecisionForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorPaymentDecisionQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_QUERY_FORM, form);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static SurvivorPaymentDecisionForm getSurvivorPaymentDecisionQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorPaymentDecisionQueryForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentDecisionForm) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_QUERY_FORM);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorPaymentDecisionQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_QUERY_FORM);
    }
}
