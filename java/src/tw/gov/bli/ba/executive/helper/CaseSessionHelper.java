package tw.gov.bli.ba.executive.helper;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportCloseCase;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportDataCase;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportMaintCase;
import tw.gov.bli.ba.query.cases.ExecutiveSupportQueryCase;

/**
 * Case Helper for 行政支援記錄作業
 * 
 * @author jerry
 */
public class CaseSessionHelper {
    private static Log log = LogFactory.getLog(CaseSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 行政支援作業 - 行政支援記錄維護 - ExecutiveSupporeMaint
    // ---------------------------------------------------------------------------------------
    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setExecutiveSupportMaintCase(ExecutiveSupportMaintCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportMaintCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_CASE, caseData);
    }

    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 從 Session 中取得相關 Case
     * 
     * @return ExecutiveSupporeMaintCase
     */
    public static ExecutiveSupportMaintCase getExecutiveSupportMaintCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getExecutiveSupportMaintCase() ...");

        HttpSession session = request.getSession();
        return (ExecutiveSupportMaintCase) session.getAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_CASE);
    }

    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportMaintCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportMaintCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_CASE);
    }
    
    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setExecutiveSupportMaintListCase(List<ExecutiveSupportDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportMaintListCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_LIST_CASE, caseList);
    }

    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 從 Session 中取得相關 Case
     * 
     * @return ExecutiveSupporeMaintCase
     */
    public static List<ExecutiveSupportDataCase> getExecutiveSupportMaintListCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getExecutiveSupportMaintListCase() ...");

        HttpSession session = request.getSession();
        return (List<ExecutiveSupportDataCase>) session.getAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_LIST_CASE);
    }

    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportMaintListCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportMaintListCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_LIST_CASE);
    }
    
    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setExecutiveSupportMaintDetailCase(ExecutiveSupportDataCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportMaintDetailCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_DETAIL_CASE, caseData);
    }

    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 從 Session 中取得相關 Case
     * 
     * @return ExecutiveSupporeMaintCase
     */
    public static ExecutiveSupportDataCase getExecutiveSupportMaintDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getExecutiveSupportMaintDetailCase() ...");

        HttpSession session = request.getSession();
        return (ExecutiveSupportDataCase) session.getAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_DETAIL_CASE);
    }

    /**
     * 行政支援作業 - 行政支援記錄維護<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportMaintDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportMaintDetailCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_MAINT_DETAIL_CASE);
    }

    // ---------------------------------------------------------------------------------------
    // 行政支援作業 - 行政支援記錄銷案 - ExecutiveSupportClose
    // ---------------------------------------------------------------------------------------
    /**
     * 行政支援作業 - 行政支援記錄銷案<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setExecutiveSupportCloseCase(ExecutiveSupportCloseCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportCloseCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_CLOSE_CASE, caseData);
    }

    /**
     * 行政支援作業 - 行政支援記錄銷案<br>
     * 從 Session 中取得相關 Case
     * 
     * @return ExecutiveSupporeMaintCase
     */
    public static ExecutiveSupportCloseCase getExecutiveSupportCloseCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getExecutiveSupportCloseCase() ...");

        HttpSession session = request.getSession();
        return (ExecutiveSupportCloseCase) session.getAttribute(ConstantKey.EXECUTIVE_SUPPORT_CLOSE_CASE);
    }

    /**
     * 行政支援作業 - 行政支援記錄銷案<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportCloseCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportCloseCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_CLOSE_CASE);
    }
    
    /**
     * 行政支援作業 - 行政支援記錄銷案<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setExecutiveSupportCloseCase(ExecutiveSupportQueryCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportCloseCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_CLOSE_CASE, caseData);
    }
    
    
    /**
     * 行政支援作業 - 行政支援記錄銷案<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setExecutiveSupportCloseListCase(List<ExecutiveSupportDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setExecutiveSupportCloseListCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.EXECUTIVE_SUPPORT_CLOSE_LIST_CASE, caseList);
    }

    /**
     * 行政支援作業 - 行政支援記錄銷案<br>
     * 從 Session 中取得相關 Case
     * 
     * @return ExecutiveSupporeMaintCase
     */
    public static List<ExecutiveSupportDataCase> getExecutiveSupportCloseListCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getExecutiveSupportCloseListCase() ...");

        HttpSession session = request.getSession();
        return (List<ExecutiveSupportDataCase>) session.getAttribute(ConstantKey.EXECUTIVE_SUPPORT_CLOSE_LIST_CASE);
    }

    /**
     * 行政支援作業 - 行政支援記錄銷案<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecutiveSupportCloseListCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeExecutiveSupportCloseListCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.EXECUTIVE_SUPPORT_CLOSE_LIST_CASE);
    }

    // ---------------------------------------------------------------------------------------
}
