package tw.gov.bli.ba.query.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.query.forms.CheckMarkLevelQueryForm;
import tw.gov.bli.ba.query.forms.ExecutiveSupportQueryForm;
import tw.gov.bli.ba.query.forms.PaymentQueryForm;
import tw.gov.bli.ba.query.forms.ReceivableQueryForm;
import tw.gov.bli.ba.rpt.forms.OtherRpt08Form;

/**
 * Form Helper for 查詢作業
 * 
 * @author jerry
 */
public class FormSessionHelper {
    private static Log log = LogFactory.getLog(FormSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 查詢作業 - 更正日誌查詢 - UpdateLogQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 更正日誌查詢<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUpdateLogQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeUpdateLogQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.UPDATE_LOG_QUERY_FORM);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 查詢作業 - 編審註記查詢- CheckMarkLevelQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 編審註記查詢<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCheckMarkLevelQueryForm(CheckMarkLevelQueryForm queryData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCheckMarkLevelQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CHECK_MARK_LEVEL_QUERY_FORM, queryData);
    }

    /**
     * 查詢作業 - 編審註記查詢<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCheckMarkLevelQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCheckMarkLevelQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.CHECK_MARK_LEVEL_QUERY_FORM);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 查詢作業 - 行政支援查詢- ExecutiveSupportQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 行政支援查詢<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setExecutiveSupportQueryForm(ExecutiveSupportQueryForm from, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setExecutiveSupportQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_FORM, from);

    }

    /**
     * 查詢作業 - 行政支援查詢<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeExecutiveSupportQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_FORM);

    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 行政支援查詢<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setExecutiveSupportQueryCondForm(ExecutiveSupportQueryForm from, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setExecutiveSupportQueryCondForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_COND_FORM, from);

    }

    /**
     * 查詢作業 - 行政支援查詢<br>
     * 從 Session 中 取得 查詢條件Form
     * 
     * @param request
     */
    public static ExecutiveSupportQueryForm getExecutiveSupportQueryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getExecutiveSupportQueryCondForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        return (ExecutiveSupportQueryForm) session.getAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_COND_FORM);
    }

    /**
     * 查詢作業 - 行政支援查詢<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportQueryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeExecutiveSupportQueryCondForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_QUERY_COND_FORM);

    }

    // ---------------------------------------------------------------------------------------
    // 查詢作業 - 給付查詢- PaymentQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setPaymentQueryForm(PaymentQueryForm from, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setPaymentQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.setAttribute(ConstantKey.PAYMENT_QUERY_FORM, from);

    }

    /**
     * 查詢作業 - 給付查詢<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePaymentQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_FORM);

    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 給付查詢<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setPaymentQueryCondForm(PaymentQueryForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setPaymentQueryCondForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_QUERY_COND_FORM, form);
    }

    /**
     * 查詢作業 - 給付查詢<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static PaymentQueryForm getPaymentQueryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getPaymentQueryCondForm() ...");

        HttpSession session = request.getSession();
        return (PaymentQueryForm) session.getAttribute(ConstantKey.PAYMENT_QUERY_COND_FORM);
    }

    /**
     * 查詢作業 - 給付查詢<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentQueryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePaymentQueryCondForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.PAYMENT_QUERY_COND_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 查詢作業 - 應收查詢- ReceivableQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 應收查詢<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setReceivableQueryForm(ReceivableQueryForm from, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReceivableQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.setAttribute(ConstantKey.RECEIVABLE_QUERY_FORM, from);

    }

    /**
     * 查詢作業 - 應收查詢<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReceivableQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReceivableQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.RECEIVABLE_QUERY_FORM);

    }

    // ---------------------------------------------------------------------------------------
    /**
     * 查詢作業 - 應收查詢<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setReceivableQueryCondForm(ReceivableQueryForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReceivableQueryCondForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RECEIVABLE_QUERY_COND_FORM, form);
    }

    /**
     * 查詢作業 - 應收查詢<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static ReceivableQueryForm getReceivableQueryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getReceivableQueryCondForm() ...");

        HttpSession session = request.getSession();
        return (ReceivableQueryForm) session.getAttribute(ConstantKey.RECEIVABLE_QUERY_COND_FORM);
    }

    /**
     * 查詢作業 - 應收查詢<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReceivableQueryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReceivableQueryCondForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.RECEIVABLE_QUERY_COND_FORM);
    }
    // ---------------------------------------------------------------------------------------
    /**
     *<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setOtherRpt08Form(OtherRpt08Form form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setOtherRpt08Form() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OTHER_RPT08_FORM, form);
    }

    /**
     * <br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static OtherRpt08Form getOtherRpt08Form(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getOtherRpt08Form() ...");

        HttpSession session = request.getSession();
        return (OtherRpt08Form) session.getAttribute(ConstantKey.OTHER_RPT08_FORM);
    }

    /**
     * <br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOtherRpt08Form(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeOtherRpt08Form() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.OTHER_RPT08_FORM);
    }
    /**
     *<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setOtherRpt08DetailForm(OtherRpt08Form form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setOtherRpt08DetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OTHER_RPT08_DETAIL_FORM, form);
    }

    /**
     * <br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static OtherRpt08Form getOtherRpt08DetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getOtherRpt08DetailForm() ...");

        HttpSession session = request.getSession();
        return (OtherRpt08Form) session.getAttribute(ConstantKey.OTHER_RPT08_DETAIL_FORM);
    }

    /**
     * <br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOtherRpt08DetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeOtherRpt08DetailForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.OTHER_RPT08_DETAIL_FORM);
    }
    
    /**
     * <br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAnnuityStatisticsForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeAnnuityStatisticsForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute("AnnuityStatisticsForm");
    }
}

