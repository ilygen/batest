package tw.gov.bli.ba.bj.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.forms.CheckJobForm;
import tw.gov.bli.ba.bj.forms.CheckJobRptForm;
import tw.gov.bli.ba.bj.forms.QryProcedureForm;
import tw.gov.bli.ba.bj.forms.ReceivableAdjustBJForm;
import tw.gov.bli.ba.bj.forms.RunProcedureForm;
import tw.gov.bli.ba.bj.forms.Trans2OverdueReceivableBJForm;
import tw.gov.bli.ba.bj.forms.ReturnWriteOffBJForm;
import tw.gov.bli.ba.bj.forms.UpdatePaidMarkBJForm;
import tw.gov.bli.ba.bj.forms.UpdatePaidMarkBJDetailForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintDetailForm;
import tw.gov.bli.ba.payment.forms.PaymentProcessQueryForm;
import tw.gov.bli.ba.payment.forms.PaymentProgressQueryForm;
import tw.gov.bli.ba.payment.forms.PaymentReprintForm;

/**
 * Form Helper for 批次處理
 * 
 * @author Rickychi
 */
public class FormSessionHelper {
    private static Log log = LogFactory.getLog(FormSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 批次處理 - 給付媒體回押註記作業 - UpdatePaidMarkBJ
    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 給付媒體回押註記作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setUpdatePaidMarkBJForm(UpdatePaidMarkBJForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setUpdatePaidMarkBJForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_FORM, form);
    }
    
    /**
     * 批次處理 - 給付媒體回押註記作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static UpdatePaidMarkBJForm getUpdatePaidMarkBJForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getUpdatePaidMarkBJForm() ...");

        HttpSession session = request.getSession();
        return (UpdatePaidMarkBJForm) session.getAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_FORM);
    }

    /**
     * 批次處理 - 給付媒體回押註記作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUpdatePaidMarkBJForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeUpdatePaidMarkBJForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_FORM);
    }

    /**
     * 批次處理 - 給付媒體回押註記作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setUpdatePaidMarkBJDetailForm(UpdatePaidMarkBJDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setUpdatePaidMarkBJDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_DETAIL_FORM, form);
    }

    /**
     * 批次處理 - 給付媒體回押註記作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUpdatePaidMarkBJDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeUpdatePaidMarkBJDetailForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_DETAIL_FORM);
    }
    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 批次處理 - 收回作業 - ReturnWriteOffBJ
    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 收回作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setReturnWriteOffBJForm(ReturnWriteOffBJForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReturnWriteOffBJForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RETURN_WRITE_OFF_BJ_FORM, form);
    }

    /**
     * 批次處理 - 收回作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReturnWriteOffBJForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReturnWriteOffBJForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.RETURN_WRITE_OFF_BJ_FORM);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 批次處理 - 轉催收作業 - Trans2OverdueReceivableBJ
    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 轉催收作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setTrans2OverdueReceivableBJForm(Trans2OverdueReceivableBJForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setTrans2OverdueReceivableBJForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_FORM, form);
    }

    /**
     * 批次處理 - 轉催收作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeTrans2OverdueReceivableBJForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeTrans2OverdueReceivableBJForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_FORM);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 批次處理 - 轉催收作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setTrans2OverdueReceivableBJCondForm(Trans2OverdueReceivableBJForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setTrans2OverdueReceivableBJCondForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_COND_FORM, form);
    }

    /**
     * 批次處理 - 轉催收作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static Trans2OverdueReceivableBJForm getTrans2OverdueReceivableBJCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getTrans2OverdueReceivableBJCondForm() ...");

        HttpSession session = request.getSession();
        return (Trans2OverdueReceivableBJForm) session.getAttribute(ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_COND_FORM);
    }

    /**
     * 批次處理 - 轉催收作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeTrans2OverdueReceivableBJCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeTrans2OverdueReceivableBJCondForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_COND_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 批次處理 - 已收調整作業 - ReceivableAdjustBJ
    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 已收調整作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setReceivableAdjustBJForm(ReceivableAdjustBJForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReceivableAdjustBJForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_FORM, form);
    }
    
    /**
     * 批次處理 - 線上產製媒體檔作業- 老年年金線上批次產製媒體檔作業- 執行頁面 <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOldMediaOnlineForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeOldMediaOnlineForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.OLD_MEDIA_ONLINE_FORM);
    }
    /**
     * 批次處理 - 線上產製媒體檔作業- 失能年金線上批次產製媒體檔作業 (BABA0M180X.jsp)<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledMediaOnlineForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledMediaOnlineForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_FORM);
    }
    /**
     * 批次處理 - 線上產製媒體檔作業- 遺屬年金線上批次產製媒體檔作業 (BABA0M190X.jsp)<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorMediaOnlineForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorMediaOnlineForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_FORM);
    }
    /**
     * 批次處理 - 線上產製媒體檔作業- 老年年金線上批次產製媒體檔作業- 執行頁面 <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOldMediaBatchForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeOldMediaBatchForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.OLD_MEDIA_BATCH_FORM);
    }
    /**
     * 批次處理 - 線上產製媒體檔作業- 失能年金線上批次產製媒體檔作業 (BABA0M180X.jsp)<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledMediaBatchForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledMediaBatchForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.DISABLED_MEDIA_BATCH_FORM);
    }
    /**
     * 批次處理 - 線上產製媒體檔作業- 遺屬年金線上批次產製媒體檔作業 (BABA0M190X.jsp)<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorMediaBatchForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorMediaBatchForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.SURVIVOR_MEDIA_BATCH_FORM);
    }
    
    /**
     * 批次處理 - 已收調整作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReceivableAdjustBJForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReceivableAdjustBJForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_FORM);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 批次處理 - 已收調整作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setReceivableAdjustBJCondForm(ReceivableAdjustBJForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setReceivableAdjustBJCondForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_COND_FORM, form);
    }

    /**
     * 批次處理 - 已收調整作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static ReceivableAdjustBJForm getReceivableAdjustBJCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getReceivableAdjustBJCondForm() ...");

        HttpSession session = request.getSession();
        return (ReceivableAdjustBJForm) session.getAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_COND_FORM);
    }

    /**
     * 批次處理 - 已收調整作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReceivableAdjustBJCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeReceivableAdjustBJCondForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_COND_FORM);
    }
    
    /**
     * 批次處理 - 老年年金線上月試核作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthCheckLForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthCheckLForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTH_CHECK_L_FORM);
    }
    
    /**
     * 批次處理 - 失能年金線上月試核作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthCheckKForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthCheckKForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTH_CHECK_K_FORM);
    }
    
    /**
     * 批次處理 - 遺屬年金線上月試核作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthCheckSForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthCheckSForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTH_CHECK_S_FORM);
    }
    
    /**
     * 批次處理 - 老年年金線上月核定作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthLForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthLForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTH_L_FORM);
    }
    
    /**
     * 批次處理 - 失能年金線上月核定作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthKForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthKForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTH_K_FORM);
    }
    
    /**
     * 批次處理 - 遺屬年金線上月核定作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthSForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthSForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTH_S_FORM);
    }
    
    /**
     * 批次處理 - 老年年金批次月處理作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthBatchLForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthBatchLForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTH_BATCH_L_FORM);
    }
    
    /**
     * 批次處理 - 失能年金批次月處理作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthBatchKForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthBatchKForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTH_BATCH_K_FORM);
    }
    
    /**
     * 批次處理 - 遺屬年金批次月處理作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeMonthBatchSForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeMonthBatchSForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.MONTH_BATCH_S_FORM);
    }
    // ---------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 檢核確認作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCheckJobForm(CheckJobForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setCheckJobForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CHECK_JOB_FORM, form);
    }

    /**
     * 批次處理 - 檢核確認作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static CheckJobForm getCheckJobForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getCheckJobForm() ...");

        HttpSession session = request.getSession();
        return (CheckJobForm) session.getAttribute(ConstantKey.CHECK_JOB_FORM);
    }

    /**
     * 批次處理 - 檢核確認作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCheckJobForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCheckJobForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.CHECK_JOB_FORM);
    }

    // ---------------------------------------------------------------------------------------
    
 // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 產生檢核案件作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCheckJobRptForm(CheckJobRptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setCheckJobRptForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CHECK_JOB_RPT_FORM, form);
    }

    /**
     * 批次處理 - 1. 	產生檢核案件作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static CheckJobRptForm getCheckJobRptForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getCheckJobRptForm() ...");

        HttpSession session = request.getSession();
        return (CheckJobRptForm) session.getAttribute(ConstantKey.CHECK_JOB_RPT_FORM);
    }

    /**
     * 批次處理 - 1. 	產生檢核案件作業<br>
     * 將 查詢條件Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCheckJobRptForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCheckJobRptForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.CHECK_JOB_RPT_FORM);
    }

    // ---------------------------------------------------------------------------------------
    
    /**
     * 批次作業 - 批次執行作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setRunProcedureForm(RunProcedureForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setRunProcedureForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RUN_PROCEDURE_FORM, form);
    }
    
    /**
     * 批次作業 - 批次執行作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeRunProcedureForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeRunProcedureForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.RUN_PROCEDURE_FORM);
    }
    

    /**
     * 批次作業 - 批次查詢作業 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setQryProcedureForm(RunProcedureForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setQryProcedureForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.QRY_PROCEDURE_FORM, form);
    }    
    
    /**
     * 批次處理 - 批次查詢作業<br>
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     */
    public static QryProcedureForm getQryProcedureForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.QryProcedureForm() ...");

        HttpSession session = request.getSession();
        return (QryProcedureForm) session.getAttribute(ConstantKey.QRY_PROCEDURE_FORM);
    }

    /**
     * 批次處理 - 批次查詢作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeQryProcedureForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeQryProcedureForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.QRY_PROCEDURE_FORM);
    }    
    
    /**
     * 繳款單作業 - 開單維護作業 
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     * @return
     */
    public static PaymentProcessQueryForm getPaymentProcessQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getPaymentProcessQueryForm() ...");

        HttpSession session = request.getSession();
        return (PaymentProcessQueryForm) session.getAttribute(ConstantKey.PAYMENT_PROCESS_QUERY_FORM);
    }
    
    /**
     * 繳款單作業 - 開單維護作業 
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     * @return
     */
    public static void removePaymentProcessQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePaymentProcessQueryForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_PROCESS_QUERY_FORM);
    }
    
    /**
     * 繳款單作業 - 開單維護作業 
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param dataList
     * @param request
     */
    public static void setPaymentProcessQueryForm(PaymentProcessQueryForm form, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCheckJobCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_PROCESS_QUERY_FORM, form);
    }
    /**
     * 繳款單作業 - 開單維護作業 
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     * @return
     */
    public static PaymentReprintForm getPaymentReprintForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getPaymentReprintForm() ...");

        HttpSession session = request.getSession();
        return (PaymentReprintForm) session.getAttribute(ConstantKey.PAYMENT_REPRINT_FORM);
    }
    
    /**
     * 繳款單作業 - 開單維護作業 
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     * @return
     */
    public static void removePaymentReprintForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePaymentReprintForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_REPRINT_FORM);
    }
    
    /**
     * 繳款單作業 - 開單維護作業 
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param dataList
     * @param request
     */
    public static void setPaymentReprintForm(PaymentReprintForm form, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentReprintForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_REPRINT_FORM, form);
    }
    
    
    /**
     * 繳款單作業 - 開單維護作業 
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param request
     * @return
     */
    public static PaymentProgressQueryForm getPaymentQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getPaymentQueryForm() ...");

        HttpSession session = request.getSession();
        return (PaymentProgressQueryForm) session.getAttribute(ConstantKey.PAYMENT_PROGRESS_QUERY_FORM);
    }
    
    /**
     * 繳款單作業 - 開單維護作業 
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     * @return
     */
    public static void removePaymentQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePaymentQueryForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_PROGRESS_QUERY_FORM);
    }
    
    /**
     * 繳款單作業 - 開單維護作業 
     * 將 查詢條件Form 加入 Session 中
     * 
     * @param dataList
     * @param request
     */
    public static void setPaymentQueryForm(PaymentProgressQueryForm form, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_PROGRESS_QUERY_FORM, form);
    }
    
    /**
     * 批次作業 - 補正核付資料作業 - 老年年金補正核付作業 -新增頁面和修改頁面<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCompPaymentForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCompPaymentPaymentForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.COMP_PAYMENT_FORM);
    }
    /**
     * 批次作業 - 年金統計執行作業<br>
     * 將 qryCond 自 Session 中移除
     * 
     * @param request
     */
    public static void removeExecStatisticsForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeExecStatisticsForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.EXEC_STATISTICS_FORM);
    }
    
}
