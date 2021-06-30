package tw.gov.bli.ba.maint.helper;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.maint.cases.AvgAmtMonMaintCase;
import tw.gov.bli.ba.maint.cases.BaletterCodeCase;
import tw.gov.bli.ba.maint.cases.BasicAmtMaintCase;
import tw.gov.bli.ba.maint.cases.DisabledReplacementRatioMaintCase;
import tw.gov.bli.ba.maint.cases.PreviewConditionMaintCase;
import tw.gov.bli.ba.maint.cases.CheckMarkMaintCase;
import tw.gov.bli.ba.maint.cases.AdviceMaintCase;
import tw.gov.bli.ba.maint.cases.CpiDtlMaintCase;
import tw.gov.bli.ba.maint.cases.CpiRecMaintCase;
import tw.gov.bli.ba.maint.cases.ReplacementRatioMaintCase;
import tw.gov.bli.ba.maint.cases.ResetPaymentParameterCase;
import tw.gov.bli.ba.maint.cases.SurvivorReplacementRatioMaintCase;
import tw.gov.bli.ba.domain.Bapadcslvl;
import tw.gov.bli.ba.domain.Banotify;

public class CaseSessionHelper {
    private static Log log = LogFactory.getLog(CaseSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 維護作業 - 先抽對象維護作業 - PreviewConditionMaint
    // ---------------------------------------------------------------------------------------
    /**
     * 維護作業 - 先抽對象維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPreviewConditionMaintCase(PreviewConditionMaintCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPreviewConditionMaintCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PREVIEW_CONDITION_MAINT_CASE, caseData);
    }

    /**
     * 維護作業 - 先抽對象維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePreviewConditionMaintCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePreviewConditionMaintCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PREVIEW_CONDITION_MAINT_CASE);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 維護作業 - 編審註記維護作業 - CheckMarkMaintQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 維護作業 - 編審註記維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCheckMarkMaintQueryCase(List<CheckMarkMaintCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCheckMarkMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CHECK_MARK_MAINT_QUERY_CASE, caseData);
    }

    /**
     * 維護作業 - 編審註記維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<CheckMarkMaintCase> getCheckMarkMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.CheckMarkMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<CheckMarkMaintCase>) session.getAttribute(ConstantKey.CHECK_MARK_MAINT_QUERY_CASE);
    }

    /**
     * 維護作業 - 編審註記維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCheckMarkMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCheckMarkMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CHECK_MARK_MAINT_QUERY_CASE);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 維護作業 - 核定通知書維護作業 - AdviceMaintQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 維護作業 - 核定通知書維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setAdviceMaintQueryCase(List<AdviceMaintCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setAdviceMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.ADVICE_MAINT_QUERY_CASE, caseData);
    }

    /**
     * 維護作業 - 核定通知書維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAdviceMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAdviceMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.ADVICE_MAINT_QUERY_CASE);
    }

    /**
     * 維護作業 - 核定通知書維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setAdviceMaintDataContCase(List<AdviceMaintCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setAdviceMaintDataContCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.ADVICE_MAINT_DATA_CONT_CASE, caseData);
    }

    /**
     * 維護作業 - 核定通知書維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<AdviceMaintCase> getAdviceMaintDataContCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getAdviceMaintDataContCase() ...");

        HttpSession session = request.getSession();
        return (List<AdviceMaintCase>) session.getAttribute(ConstantKey.ADVICE_MAINT_DATA_CONT_CASE);
    }

    /**
     * 維護作業 - 核定通知書維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAdviceMaintDataContCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAdviceMaintDataContCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.ADVICE_MAINT_DATA_CONT_CASE);
    }
    
    /**
     * 維護作業 - 核定通知書維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setBaletterCodeCaseList(List<BaletterCodeCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setBaletterCodeCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.BALETTER_CODE_CASE_LIST, caseData);
    }

    /**
     * 維護作業 - 核定通知書維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<BaletterCodeCase> getBaletterCodeCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getBaletterCodeCaseList() ...");

        HttpSession session = request.getSession();
        return (List<BaletterCodeCase>) session.getAttribute(ConstantKey.BALETTER_CODE_CASE_LIST);
    }

    /**
     * 維護作業 - 核定通知書維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeBaletterCodeCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeBaletterCodeCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.BALETTER_CODE_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 維護作業 - 決行層級維護作業 - DecisionLevelMaintQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 維護作業 - 決行層級維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDecisionLevelMaintQueryCase(List<Bapadcslvl> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDecisionLevelMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DECISION_LEVEL_MAINT_QUERY_CASE, caseData);
    }

    /**
     * 維護作業 - 決行層級維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDecisionLevelMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDecisionLevelMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DECISION_LEVEL_MAINT_QUERY_CASE);
    }
    // ---------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 物價指數調整明細維護作業 - CpiDtlMaintQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCpiDtlMaintQueryCase(List<CpiDtlMaintCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCpiDtlMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CPI_DTL_MAINT_QUERY_CASE, caseData);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<CpiDtlMaintCase> getCpiDtlMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.CpiDtlMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<CpiDtlMaintCase>) session.getAttribute(ConstantKey.CPI_DTL_MAINT_QUERY_CASE);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCpiDtlMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCpiDtlMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CPI_DTL_MAINT_QUERY_CASE);
    }
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setAdjYearAmountCaseList(List<CpiDtlMaintCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setAdjYearAmountCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CPI_DTL_ADJYEAR_AMOUNT_CASE_LIST, caseList);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<CpiDtlMaintCase> getAdjYearAmountCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getAdjYearAmountCaseList() ...");

        HttpSession session = request.getSession();
        return (List<CpiDtlMaintCase>) session.getAttribute(ConstantKey.CPI_DTL_ADJYEAR_AMOUNT_CASE_LIST);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAdjYearAmountCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAdjYearAmountCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CPI_DTL_ADJYEAR_AMOUNT_CASE_LIST);
    }
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setAdjYearCaseList(List<CpiDtlMaintCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setAdjYearCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CPI_DTL_ADJYEAR_CASE_LIST, caseList);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<CpiDtlMaintCase> getAdjYearCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getAdjYearCaseList() ...");

        HttpSession session = request.getSession();
        return (List<CpiDtlMaintCase>) session.getAttribute(ConstantKey.CPI_DTL_ADJYEAR_CASE_LIST);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAdjYearCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAdjYearCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CPI_DTL_ADJYEAR_CASE_LIST);
    }
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setOldAdjYearCase(CpiDtlMaintCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setOldAdjYearCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CPI_DTL_OLD_ADJYEAR_CASE, caseData);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static CpiDtlMaintCase getOldAdjYearCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getOldAdjYearCase() ...");

        HttpSession session = request.getSession();
        return (CpiDtlMaintCase) session.getAttribute(ConstantKey.CPI_DTL_OLD_ADJYEAR_CASE);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOldAdjYearCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeOldAdjYearCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CPI_DTL_OLD_ADJYEAR_CASE);
    }
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setUserDetailCaseList(List<CpiDtlMaintCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setUserDetailCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CPI_DTL_USER_DETAIL_CASE_LIST, caseList);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<CpiDtlMaintCase> getUserDetailCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getUserDetailCaseList() ...");

        HttpSession session = request.getSession();
        return (List<CpiDtlMaintCase>) session.getAttribute(ConstantKey.CPI_DTL_USER_DETAIL_CASE_LIST);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUserDetailCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeUserDetailCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CPI_DTL_USER_DETAIL_CASE_LIST);
    }
    
    // ---------------------------------------------------------------------------------------    
    
    
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 物價指數調整紀錄維護作業 - CpiRecMaintQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 維護作業 - 物價指數調整紀錄維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCpiRecMaintQueryCase(List<CpiRecMaintCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCpiRecMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CPI_REC_MAINT_QUERY_CASE, caseData);
    }    
    
    /**
     * 維護作業 - 物價指數調整紀錄維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<CpiRecMaintCase> getCpiRecMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.CpiRecMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<CpiRecMaintCase>) session.getAttribute(ConstantKey.CPI_REC_MAINT_QUERY_CASE);
    }    
    
    /**
     * 維護作業 - 物價指數調整紀錄維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCpiRecMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCpiRecMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CPI_REC_MAINT_QUERY_CASE);
    }
    
    /**
     * 維護作業 - 物價指數調整紀錄維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCpiRecMaintDetailCaseList(List<CpiRecMaintCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCpiRecMaintDetailCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CPI_REC_MAINT_DETAIL_CASE_LIST, caseList);
    }    
    
    /**
     * 維護作業 - 物價指數調整紀錄維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<CpiRecMaintCase> getCpiRecMaintDetailCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getCpiRecMaintDetailCaseList() ...");

        HttpSession session = request.getSession();
        return (List<CpiRecMaintCase>) session.getAttribute(ConstantKey.CPI_REC_MAINT_DETAIL_CASE_LIST);
    }    
    
    /**
     * 維護作業 - 物價指數調整紀錄維護作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCpiRecMaintDetailCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCpiRecMaintDetailCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CPI_REC_MAINT_DETAIL_CASE_LIST);
    }
    
    // ---------------------------------------------------------------------------------------    
    
 // ---------------------------------------------------------------------------------------
    // 維護作業 - 老年年金加計金額調整作業- BasicAmtMaintList
    // ---------------------------------------------------------------------------------------
    /**
     * 維護作業 - 老年年金加計金額調整作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setBasicAmtMaintQueryCase(List<BasicAmtMaintCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setBasicAmtMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.BASIC_AMT_MAINT_QUERY_CASE, caseData);
    }    
    
    /**
     * 維護作業 - 老年年金加計金額調整作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<BasicAmtMaintCase> getBasicAmtMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.BasicAmtMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<BasicAmtMaintCase>) session.getAttribute(ConstantKey.BASIC_AMT_MAINT_QUERY_CASE);
    }    
    
    // ---------------------------------------------------------------------------------------   
    
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 勞保年金平均薪資月數參數維護修改作業 - AvgAmtMonMaintQuery
    // ---------------------------------------------------------------------------------------
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setAvgAmtMonMaintQueryCase(AvgAmtMonMaintCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setAvgAmtMonMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.AVG_AMT_MON_MAINT_QUERY_CASE, caseData);
    }    
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static AvgAmtMonMaintCase getAvgAmtMonMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getAvgAmtMonMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (AvgAmtMonMaintCase) session.getAttribute(ConstantKey.AVG_AMT_MON_MAINT_QUERY_CASE);
    }    
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業<br>
     * 取得相關 Case 從 Session 中移除
     * 
     * @param caseData
     * @param request
     */
    public static void removeAvgAmtMonMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAvgAmtMonMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.AVG_AMT_MON_MAINT_QUERY_CASE);
    }
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setAvgAmtMonMaintQueryCaseList(List<AvgAmtMonMaintCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setAvgAmtMonMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.AVG_AMT_MON_MAINT_QUERY_CASE_LIST, caseData);
    }    
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<AvgAmtMonMaintCase> getAvgAmtMonMaintQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getAvgAmtMonMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<AvgAmtMonMaintCase>) session.getAttribute(ConstantKey.AVG_AMT_MON_MAINT_QUERY_CASE_LIST);
    }    
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業<br>
     * 取得相關 Case 從 Session 中移除
     * 
     * @param caseData
     * @param request
     */
    public static void removeAvgAmtMonMaintQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAvgAmtMonMaintQueryCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.AVG_AMT_MON_MAINT_QUERY_CASE_LIST);
    }
    // ---------------------------------------------------------------------------------------    
    
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 老年年金所得替代率參數維護作業 - ReplacementRatioMaint
    // ---------------------------------------------------------------------------------------
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReplacementRatioMaintQueryCase(ReplacementRatioMaintCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_QUERY_CASE, caseData);
    }    
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static ReplacementRatioMaintCase getReplacementRatioMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (ReplacementRatioMaintCase) session.getAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_QUERY_CASE);
    }    
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中移除
     * 
     * @param caseData
     * @param request
     */
    public static void removeReplacementRatioMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_QUERY_CASE);
    }
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReplacementRatioMaintQueryCaseList(List<ReplacementRatioMaintCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setReplacementRatioMaintQueryCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST, caseData);
    }    
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<ReplacementRatioMaintCase> getReplacementRatioMaintQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<ReplacementRatioMaintCase>) session.getAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST);
    }    
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中移除
     * 
     * @param caseData
     * @param request
     */
    public static void removeReplacementRatioMaintQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReplacementRatioMaintQueryCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST);
    }
    // ---------------------------------------------------------------------------------------  
    
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 失能年金所得替代率參數維護作業 - DisabledReplacementRatioMaint
    // ---------------------------------------------------------------------------------------
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledReplacementRatioMaintQueryCase(DisabledReplacementRatioMaintCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_CASE, caseData);
    }    
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static DisabledReplacementRatioMaintCase getDisabledReplacementRatioMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (DisabledReplacementRatioMaintCase) session.getAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_CASE);
    }    
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中移除
     * 
     * @param caseData
     * @param request
     */
    public static void removeDisabledReplacementRatioMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_CASE);
    }
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledReplacementRatioMaintQueryCaseList(List<DisabledReplacementRatioMaintCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledReplacementRatioMaintQueryCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST, caseData);
    }    
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<DisabledReplacementRatioMaintCase> getDisabledReplacementRatioMaintQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<DisabledReplacementRatioMaintCase>) session.getAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST);
    }    
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中移除
     * 
     * @param caseData
     * @param request
     */
    public static void removeDisabledReplacementRatioMaintQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledReplacementRatioMaintQueryCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST);
    }
    // ---------------------------------------------------------------------------------------  

    // ---------------------------------------------------------------------------------------
    // 維護作業 - 遺屬年金所得替代率參數維護作業 - SurvivorReplacementRatioMaint
    // ---------------------------------------------------------------------------------------
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorReplacementRatioMaintQueryCase(SurvivorReplacementRatioMaintCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_CASE, caseData);
    }    
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static SurvivorReplacementRatioMaintCase getSurvivorReplacementRatioMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (SurvivorReplacementRatioMaintCase) session.getAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_CASE);
    }    
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中移除
     * 
     * @param caseData
     * @param request
     */
    public static void removeSurvivorReplacementRatioMaintQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_CASE);
    }
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorReplacementRatioMaintQueryCaseList(List<SurvivorReplacementRatioMaintCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorReplacementRatioMaintQueryCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST, caseData);
    }    
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<SurvivorReplacementRatioMaintCase> getSurvivorReplacementRatioMaintQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorReplacementRatioMaintQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorReplacementRatioMaintCase>) session.getAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST);
    }    
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業<br>
     * 取得相關 Case 從 Session 中移除
     * 
     * @param caseData
     * @param request
     */
    public static void removeSurvivorReplacementRatioMaintQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorReplacementRatioMaintQueryCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_CASE_LIST);
    }
    // ---------------------------------------------------------------------------------------

    /**
     * 維護作業 - 重設案件給付參數資料<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setResetPaymentParameterCase(ResetPaymentParameterCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setResetPaymentParameterCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RESET_PAYMENT_PARAMETER_CASE, caseObj);
    }
    
    /**
     * 維護作業 - 重設案件給付參數資料<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeResetPaymentParameterCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.ResetPaymentParameterCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.RESET_PAYMENT_PARAMETER_CASE);
    }
}
