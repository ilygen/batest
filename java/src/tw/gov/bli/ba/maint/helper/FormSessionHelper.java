package tw.gov.bli.ba.maint.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.maint.cases.DeductItemMaintCase;
import tw.gov.bli.ba.maint.forms.AvgAmtMonMaintDetailForm;
import tw.gov.bli.ba.maint.forms.AvgAmtMonMaintListForm;
import tw.gov.bli.ba.maint.forms.AvgAmtMonMaintQueryForm;
import tw.gov.bli.ba.maint.forms.BasicAmtMaintDetailForm;
import tw.gov.bli.ba.maint.forms.BasicAmtMaintQueryForm;
import tw.gov.bli.ba.maint.forms.DeductItemMaintDetailForm;
import tw.gov.bli.ba.maint.forms.DisabledReplacementRatioMaintDetailForm;
import tw.gov.bli.ba.maint.forms.DisabledReplacementRatioMaintListForm;
import tw.gov.bli.ba.maint.forms.DisabledReplacementRatioMaintQueryForm;
import tw.gov.bli.ba.maint.forms.PreviewConditionMaintDetailForOldAgeAnnuityForm;
import tw.gov.bli.ba.maint.forms.CheckMarkMaintQueryForm;
import tw.gov.bli.ba.maint.forms.CheckMarkMaintDetailForm;
import tw.gov.bli.ba.maint.forms.AdviceMaintQueryForm;
import tw.gov.bli.ba.maint.forms.AdviceMaintDetailForm;
import tw.gov.bli.ba.maint.forms.DecisionLevelMaintQueryForm;
import tw.gov.bli.ba.maint.forms.DecisionLevelMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintQueryForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CpiRecMaintQueryForm;
import tw.gov.bli.ba.maint.forms.CpiRecMaintDetailForm;
import tw.gov.bli.ba.maint.forms.ReplacementRatioMaintDetailForm;
import tw.gov.bli.ba.maint.forms.ReplacementRatioMaintListForm;
import tw.gov.bli.ba.maint.forms.ReplacementRatioMaintQueryForm;
import tw.gov.bli.ba.maint.forms.SurvivorReplacementRatioMaintDetailForm;
import tw.gov.bli.ba.maint.forms.SurvivorReplacementRatioMaintListForm;
import tw.gov.bli.ba.maint.forms.SurvivorReplacementRatioMaintQueryForm;

/**
 * Form Helper for 維護作業
 * 
 * @author Goston
 */
public class FormSessionHelper {
    private static Log log = LogFactory.getLog(FormSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 維護作業 - 先抽對象維護作業 - PreviewConditionMaint
    // ---------------------------------------------------------------------------------------
    /**
     * 維護作業 - 先抽對象維護作業 - 老年年金維護作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setPreviewConditionMaintDetailForOldAgeAnnuityForm(PreviewConditionMaintDetailForOldAgeAnnuityForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setPreviewConditionMaintDetailForOldAgeAnnuityForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PREVIEW_CONDITION_MAINT_DETAIL_FOR_OLD_AGE_ANNUITY_FORM, form);
    }

    /**
     * 維護作業 - 先抽對象維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removePreviewConditionMaintForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePreviewConditionMaintForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.PREVIEW_CONDITION_MAINT_QUERY_FORM);
        // 各 明細頁面
        session.removeAttribute(ConstantKey.PREVIEW_CONDITION_MAINT_DETAIL_FOR_OLD_AGE_ANNUITY_FORM);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 維護作業 - 編審註記維護作業 - CheckMarkMaint
    // ---------------------------------------------------------------------------------------
    /**
     * 維護作業 - 編審註記維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static CheckMarkMaintQueryForm getCheckMarkMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getCheckMarkMaintQueryForm() ...");

        HttpSession session = request.getSession();
        return (CheckMarkMaintQueryForm) session.getAttribute(ConstantKey.CHECK_MARK_MAINT_QUERY_FORM);
    }

    /**
     * 維護作業 - 編審註記維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCheckMarkMaintQueryForm(CheckMarkMaintQueryForm form, String chkGroup, String chkObj, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setCheckMarkMaintQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.BAPARAM_PARAMGROUP_CHKGROUP, chkGroup);
        session.setAttribute(ConstantKey.BAPARAM_PARAMGROUP_PAYCODE, chkObj);
        session.setAttribute(ConstantKey.CHECK_MARK_MAINT_QUERY_FORM, form);
    }

    /**
     * 維護作業 - 編審註記維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCheckMarkMaintDetailForm(CheckMarkMaintDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setCheckMarkMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CHECK_MARK_MAINT_DETAIL_FORM, form);
    }

    /**
     * 維護作業 - 編審註記維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCheckMarkMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCheckMarkMaintQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.CHECK_MARK_MAINT_QUERY_FORM);
    }

    /**
     * 維護作業 - 編審註記維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCheckMarkMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCheckMarkMaintDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.CHECK_MARK_MAINT_DETAIL_FORM);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 維護作業 - 核定通知書維護作業 - AdviceMaint
    // ---------------------------------------------------------------------------------------
    /**
     * 維護作業 - 核定通知書維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static AdviceMaintQueryForm getAdviceMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getAdviceMaintQueryForm() ...");

        HttpSession session = request.getSession();
        return (AdviceMaintQueryForm) session.getAttribute(ConstantKey.ADVICE_MAINT_QUERY_FORM);
    }

    /**
     * 維護作業 - 核定通知書維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setAdviceMaintQueryForm(AdviceMaintQueryForm form, String chkObj, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setAdviceMaintQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.BAPARAM_PARAMGROUP_PAYCODE, chkObj);
        session.setAttribute(ConstantKey.ADVICE_MAINT_QUERY_FORM, form);
    }

    /**
     * 維護作業 - 核定通知書維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setAdviceMaintDetailForm(AdviceMaintDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setAdviceMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.ADVICE_MAINT_DETAIL_FORM, form);
    }

    /**
     * 維護作業 - 核定通知書維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAdviceMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeAdviceMaintQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.ADVICE_MAINT_QUERY_FORM);
    }

    /**
     * 維護作業 - 核定通知書維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAdviceMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeAdviceMaintDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.ADVICE_MAINT_DETAIL_FORM);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 維護作業 - 決行層級維護作業 - DecisionLevelMaint
    // ---------------------------------------------------------------------------------------
    /**
     * 維護作業 - 決行層級維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static DecisionLevelMaintQueryForm getDecisionLevelMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDecisionLevelMaintQueryForm() ...");

        HttpSession session = request.getSession();
        return (DecisionLevelMaintQueryForm) session.getAttribute(ConstantKey.DECISION_LEVEL_MAINT_QUERY_FORM);
    }

    /**
     * 維護作業 - 決行層級維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDecisionLevelMaintQueryForm(DecisionLevelMaintQueryForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDecisionLevelMaintQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DECISION_LEVEL_MAINT_QUERY_FORM, form);
    }

    /**
     * 維護作業 - 決行層級維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDecisionLevelMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDecisionLevelMaintQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.DECISION_LEVEL_MAINT_QUERY_FORM);
    }

    /**
     * 維護作業 - 決行層級維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDecisionLevelMaintDetailForm(DecisionLevelMaintDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDecisionLevelMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DECISION_LEVEL_MAINT_DETAIL_FORM, form);
    }

    /**
     * 維護作業 - 決行層級維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDecisionLevelMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDecisionLevelMaintDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.DECISION_LEVEL_MAINT_DETAIL_FORM);
    }

    /*
     * 維護作業 - 扣減參數維護作業<br> 將 Form 加入 Session 中 @param request
     */
    public static void setDeductItemMaintDetailForm(List<DeductItemMaintCase> list, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDeductItemMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DEDUCT_ITEM_MAINT_DETAIL_FORM, list);
    }

    /*
     * 維護作業 - 扣減參數維護作業<br> 將 Form 加入 Session 中 @param request
     */
    public static void setDeductItemMaintDetailForm(DeductItemMaintDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDeductItemMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DEDUCT_ITEM_MAINT_DETAIL_FORM, form);
    }

    /*
     * 維護作業 - 扣減參數維護作業<br> 將相關 Form 自 Session 中移除 @param request
     */
    public static void removeDeductItemMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDeductItemMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DEDUCT_ITEM_MAINT_DETAIL_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 物價指數調整明細維護作業 - CpiDtlMaint
    // ---------------------------------------------------------------------------------------    
    /**
     * 維護作業 - 物價指數調整明細維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCpiDtlMaintQueryForm(CpiDtlMaintQueryForm form, String issuYear, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setCpiDtlMaintQueryForm() ...");

        HttpSession session = request.getSession();
        //session.setAttribute(ConstantKey.BAPARAM_PARAMGROUP_CHKGROUP, cpiYear);
        session.setAttribute(ConstantKey.CPI_DTL_MAINT_QUERY_FORM, form);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static CpiDtlMaintQueryForm getCpiDtlMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getCpiDtlMaintQueryForm() ...");

        HttpSession session = request.getSession();
        return (CpiDtlMaintQueryForm) session.getAttribute(ConstantKey.CPI_DTL_MAINT_QUERY_FORM);
    } 
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCpiDtlMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCpiDtlMaintQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.CPI_DTL_MAINT_QUERY_FORM);
    } 
    
    /**
     * 維護作業 - 物價指數調整明細維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCpiDtlMaintDetailForm(CpiDtlMaintDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setCpiDtlMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CPI_DTL_MAINT_DETAIL_FORM, form);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static CpiDtlMaintDetailForm getCpiDtlMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getCpiDtlMaintDetailForm() ...");

        HttpSession session = request.getSession();
        return (CpiDtlMaintDetailForm) session.getAttribute(ConstantKey.CPI_DTL_MAINT_DETAIL_FORM);
    } 
    
    /**
     * 維護作業 - 物價指數調整明細維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCpiDtlMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCpiDtlMaintDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.CPI_DTL_MAINT_DETAIL_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 物價指數調整紀錄維護作業 - CpiRecMaint
    // ---------------------------------------------------------------------------------------    
    /**
     * 維護作業 - 物價指數調整紀錄維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCpiRecMaintQueryForm(CpiRecMaintQueryForm form, String issuYear, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setCpiRecMaintQueryForm() ...");

        HttpSession session = request.getSession();
        //session.setAttribute(ConstantKey.BAPARAM_PARAMGROUP_CHKGROUP, cpiYear);
        session.setAttribute(ConstantKey.CPI_REC_MAINT_QUERY_FORM, form);
    }    
    
    /**
     * 維護作業 - 物價指數調整紀錄維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCpiRecMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCpiRecMaintQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.CPI_REC_MAINT_QUERY_FORM);
    } 
    
    /**
     * 維護作業 - 物價指數調整紀錄維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCpiRecMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCpiRecMaintDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.CPI_REC_MAINT_QUERY_FORM);
    }
    
    /**
     * 維護作業 - 物價指數調整紀錄維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCpiRecMaintDetailForm(CpiRecMaintDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setCpiDtlMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CPI_REC_MAINT_DETAIL_FORM, form);
    }    
    
    /**
     * 維護作業 - 物價指數調整明細維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static CpiRecMaintQueryForm getCpiRecMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getCpiDtlMaintQueryForm() ...");

        HttpSession session = request.getSession();
        return (CpiRecMaintQueryForm) session.getAttribute(ConstantKey.CPI_REC_MAINT_QUERY_FORM);
    }    
    
 // ---------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 老年年金加計金額調整作業 - BasicAmtMaint
    // ---------------------------------------------------------------------------------------    
    /**
     * 維護作業 - 老年年金加計金額調整作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setBasicAmtMaintQueryForm(BasicAmtMaintQueryForm form,String payYmb,HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setBasicAmtMaintListForm() ...");

        HttpSession session = request.getSession();
        
        session.setAttribute(ConstantKey.BASIC_AMT_MAINT_QUERYL_FORM, form);
    }    
    
    /**
     * 維護作業 - 老年年金加計金額調整作業 <br>
     * 從Session中 取Form
     * 
     * @param request
     */
    public static BasicAmtMaintQueryForm getBasicAmtMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getBasicAmtMaintQueryForm() ...");

        HttpSession session = request.getSession();
        return (BasicAmtMaintQueryForm) session.getAttribute(ConstantKey.BASIC_AMT_MAINT_QUERYL_FORM);
    }    
    
    /**
     * 維護作業 - 老年年金加計金額調整作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeBasicAmtMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCpiRecMaintQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.BASIC_AMT_MAINT_QUERYL_FORM);
    }
    
    /**
     * 維護作業 - 老年年金加計金額調整作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setBasicAmtMaintDetailForm(BasicAmtMaintDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setBasicAmtMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.BASIC_AMT_MAINT_DETAIL_FORM, form);
    }    
    
    /**
     * 維護作業 - 老年年金加計金額調整作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeBasicAmtMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCpiRecMaintDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.BASIC_AMT_MAINT_DETAIL_FORM);
    }
    
 // ---------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 勞保年金平均薪資月數參數維護修改作業 - AvgAmtMonMaint
    // ---------------------------------------------------------------------------------------    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static AvgAmtMonMaintQueryForm getAvgAmtMonMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getAvgAmtMonMaintQueryForm() ...");

        HttpSession session = request.getSession();
        return (AvgAmtMonMaintQueryForm) session.getAttribute(ConstantKey.AVG_AMT_MON_MAINT_QUERY_FORM);
    } 
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setAvgAmtMonMaintQueryForm(AvgAmtMonMaintQueryForm form, String effYear, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setAvgAmtMonMaintQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.AVG_AMT_MON_MAINT_QUERY_FORM, form);
    }    
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAvgAmtMonMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeAvgAmtMonMaintQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.AVG_AMT_MON_MAINT_QUERY_FORM);
    } 
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static AvgAmtMonMaintListForm getAvgAmtMonMaintListForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getAvgAmtMonMaintListForm() ...");

        HttpSession session = request.getSession();
        return (AvgAmtMonMaintListForm) session.getAttribute(ConstantKey.AVG_AMT_MON_MAINT_LIST_FORM);
    } 
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setAvgAmtMonMaintListForm(AvgAmtMonMaintListForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setAvgAmtMonMaintListForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.AVG_AMT_MON_MAINT_LIST_FORM, form);
    }    
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAvgAmtMonMaintListForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeAvgAmtMonMaintListForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.AVG_AMT_MON_MAINT_LIST_FORM);
    } 
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static AvgAmtMonMaintDetailForm getAvgAmtMonMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getAvgAmtMonMaintDetailForm() ...");

        HttpSession session = request.getSession();
        return (AvgAmtMonMaintDetailForm) session.getAttribute(ConstantKey.AVG_AMT_MON_MAINT_DETAIL_FORM);
    } 
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setAvgAmtMonMaintDetailForm(AvgAmtMonMaintDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setAvgAmtMonMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.AVG_AMT_MON_MAINT_DETAIL_FORM, form);
    }    
    
    /**
     * 維護作業 - 勞保年金平均薪資月數參數維護修改作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAvgAmtMonMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeAvgAmtMonMaintDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.AVG_AMT_MON_MAINT_DETAIL_FORM);
    }
    
    // ---------------------------------------------------------------------------------------
    
    
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 老年年金所得替代率參數維護作業 - ReplacementRatioMaint
    // ---------------------------------------------------------------------------------------    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static ReplacementRatioMaintQueryForm getReplacementRatioMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getReplacementRatioMaintQueryForm() ...");

        HttpSession session = request.getSession();
        return (ReplacementRatioMaintQueryForm) session.getAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_QUERY_FORM);
    } 
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setReplacementRatioMaintQueryForm(ReplacementRatioMaintQueryForm form, String effYm, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReplacementRatioMaintQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_QUERY_FORM, form);
    }    
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReplacementRatioMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReplacementRatioMaintQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_QUERY_FORM);
    } 
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static ReplacementRatioMaintListForm getReplacementRatioMaintListForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getReplacementRatioMaintListForm() ...");

        HttpSession session = request.getSession();
        return (ReplacementRatioMaintListForm) session.getAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_LIST_FORM);
    } 
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setReplacementRatioMaintListForm(ReplacementRatioMaintListForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReplacementRatioMaintListForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_LIST_FORM, form);
    }    
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReplacementRatioMaintListForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReplacementRatioMaintListForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_LIST_FORM);
    } 
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static ReplacementRatioMaintDetailForm getReplacementRatioMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getReplacementRatioMaintDetailForm() ...");

        HttpSession session = request.getSession();
        return (ReplacementRatioMaintDetailForm) session.getAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_DETAIL_FORM);
    } 
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setReplacementRatioMaintDetailForm(ReplacementRatioMaintDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReplacementRatioMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_DETAIL_FORM, form);
    }    
    
    /**
     * 維護作業 - 老年年金所得替代率參數維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReplacementRatioMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReplacementRatioMaintDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.REPLACEMENT_RATIO_MAINT_DETAIL_FORM);
    }
    
    // ---------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 失能年金所得替代率參數維護作業 - DisabledReplacementRatioMaint
    // ---------------------------------------------------------------------------------------    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static DisabledReplacementRatioMaintQueryForm getDisabledReplacementRatioMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledReplacementRatioMaintQueryForm() ...");

        HttpSession session = request.getSession();
        return (DisabledReplacementRatioMaintQueryForm) session.getAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_FORM);
    } 
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDisabledReplacementRatioMaintQueryForm(DisabledReplacementRatioMaintQueryForm form, String effYm, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledReplacementRatioMaintQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_FORM, form);
    }    
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledReplacementRatioMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledReplacementRatioMaintQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_QUERY_FORM);
    } 
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static DisabledReplacementRatioMaintListForm getDisabledReplacementRatioMaintListForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledReplacementRatioMaintListForm() ...");

        HttpSession session = request.getSession();
        return (DisabledReplacementRatioMaintListForm) session.getAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_LIST_FORM);
    } 
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDisabledReplacementRatioMaintListForm(DisabledReplacementRatioMaintListForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledReplacementRatioMaintListForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_LIST_FORM, form);
    }    
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledReplacementRatioMaintListForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReplacementRatioMaintListForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_LIST_FORM);
    } 
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static DisabledReplacementRatioMaintDetailForm getDisabledReplacementRatioMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledReplacementRatioMaintDetailForm() ...");

        HttpSession session = request.getSession();
        return (DisabledReplacementRatioMaintDetailForm) session.getAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_DETAIL_FORM);
    } 
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDisabledReplacementRatioMaintDetailForm(DisabledReplacementRatioMaintDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledReplacementRatioMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_DETAIL_FORM, form);
    }    
    
    /**
     * 維護作業 - 失能年金所得替代率參數維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledReplacementRatioMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledReplacementRatioMaintDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.DISABLED_REPLACEMENT_RATIO_MAINT_DETAIL_FORM);
    }
    
    // ---------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------
    // 維護作業 - 遺屬年金所得替代率參數維護作業 - ReplacementRatioMaint
    // ---------------------------------------------------------------------------------------    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static SurvivorReplacementRatioMaintQueryForm getSurvivorReplacementRatioMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorReplacementRatioMaintQueryForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorReplacementRatioMaintQueryForm) session.getAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_FORM);
    } 
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setSurvivorReplacementRatioMaintQueryForm(SurvivorReplacementRatioMaintQueryForm form, String effYm, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorReplacementRatioMaintQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_FORM, form);
    }    
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorReplacementRatioMaintQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorReplacementRatioMaintQueryForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_QUERY_FORM);
    } 
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static SurvivorReplacementRatioMaintListForm getSurvivorReplacementRatioMaintListForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorReplacementRatioMaintListForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorReplacementRatioMaintListForm) session.getAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_LIST_FORM);
    } 
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setSurvivorReplacementRatioMaintListForm(SurvivorReplacementRatioMaintListForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorReplacementRatioMaintListForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_LIST_FORM, form);
    }    
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorReplacementRatioMaintListForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorReplacementRatioMaintListForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_LIST_FORM);
    } 
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static SurvivorReplacementRatioMaintDetailForm getSurvivorReplacementRatioMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorReplacementRatioMaintDetailForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorReplacementRatioMaintDetailForm) session.getAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_DETAIL_FORM);
    } 
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setSurvivorReplacementRatioMaintDetailForm(SurvivorReplacementRatioMaintDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorReplacementRatioMaintDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_DETAIL_FORM, form);
    }    
    
    /**
     * 維護作業 - 遺屬年金所得替代率參數維護作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorReplacementRatioMaintDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorReplacementRatioMaintDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.SURVIVOR_REPLACEMENT_RATIO_MAINT_DETAIL_FORM);
    }
    
    // ---------------------------------------------------------------------------------------
    
    /**
     * 維護作業 - 重設案件給付參數資料<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeResetPaymentParameterForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeResetPaymentParameterForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.RESET_PAYMENT_PARAMETER_FORM);
    }
}
