package tw.gov.bli.ba.executive.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportCloseForm;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportMaintForm;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportMaintModifyForm;

/**
 * Form Helper for 行政支援記錄作業
 * 
 * @author jerry
 */
public class FormSessionHelper {
    private static Log log = LogFactory.getLog(FormSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 行政支援作業 - 行政支援記錄維護 - ExecutiveSupportMaint
    // ---------------------------------------------------------------------------------------
    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Form 增加到 Session 中
     * 
     * @param request
     */
    public static void setExecutiveSupportMaintForm(ExecutiveSupportMaintForm form, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportMaintForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_FORM, form);
    }
    
    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 自 Session 中取得 ExecutiveSupportMaintForm
     * 
     * @param request
     */
    public static ExecutiveSupportMaintForm getExecutiveSupportMaintForm(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getExecutiveSupportMaintForm() ...");

        HttpSession session = request.getSession();
        return (ExecutiveSupportMaintForm) session.getAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_FORM);
    }
    
    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportMaintForm(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportMaintForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_FORM);
    }

    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportMaintAddForm(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportMaintAddForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_ADD_FORM);
    }

    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportMaintListForm(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportMaintListForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_LIST_FORM);
    }
    
    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Form 增加到 Session 中
     * 
     * @param request
     */
    public static void setExecutiveSupportMaintModifyForm(ExecutiveSupportMaintModifyForm form, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportMaintModifyForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_MODIFY_FORM, form);
    }

    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportMaintModifyForm(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportMaintModifyForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_MODIFY_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 行政支援作業 - 行政支援記錄銷案 - ExecutiveSupportClose
    // ---------------------------------------------------------------------------------------
    /**
     * 行政支援作業 - 行政支援記錄銷案 - 查詢條件<br>
     * 將 qryCondForm 加入 Session 中
     * 
     * @param qryCondForm
     * @param request
     */
    public static void setExecutiveSupportCloseQueryForm(ExecutiveSupportCloseForm qryCondForm, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportCloseQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_CLOSE_QUERY_FORM, qryCondForm);
    }

    /**
     * 行政支援作業 - 行政支援記錄銷案 - 查詢條件<br>
     * 自 Session 中取得 qryCondForm
     * 
     * @param request
     */
    public static ExecutiveSupportCloseForm getExecutiveSupportCloseQueryForm(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getExecutiveSupportCloseQueryForm() ...");

        HttpSession session = request.getSession();
        return (ExecutiveSupportCloseForm) session.getAttribute(ConstantKey.EXECUTIVE_SUPPORT_CLOSE_QUERY_FORM);
    }

    /**
     * 行政支援作業 - 行政支援記錄銷案 - 查詢條件<br>
     * 將 qryCondForm 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportCloseQueryForm(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportCloseQueryForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_CLOSE_QUERY_FORM);
    }
    
    /**
     * 行政支援作業 - 行政支援記錄銷案 - 查詢條件<br>
     * 將 qryCondForm 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportCloseForm(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportCloseForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_CLOSE_FORM);
    }

}
