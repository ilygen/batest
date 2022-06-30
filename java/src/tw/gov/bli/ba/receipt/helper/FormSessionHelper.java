package tw.gov.bli.ba.receipt.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.receipt.forms.OldAgeAnnuityReceiptForm;
import tw.gov.bli.ba.receipt.forms.DisabledAnnuityReceiptForm;
import tw.gov.bli.ba.receipt.forms.SurvivorAnnuityReceiptForm;

/**
 * Form Helper for 受理作業
 * 
 * @author Rickychi
 */
public class FormSessionHelper {
    private static Log log = LogFactory.getLog(FormSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 受理作業 - 老年年金給付受理作業 - OldAgeAnnuityReceipt
    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 老年年金給付受理作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setOldAgeAnnuityReceiptForm(OldAgeAnnuityReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setOldAgeAnnuityReceiptForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLDAGE_ANNUITY_RECEIPT_FORM, form);
    }

    /**
     * 受理作業 - 老年年金給付受理作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOldAgeAnnuityReceiptForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeOldAgeAnnuityReceiptForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.OLDAGE_ANNUITY_RECEIPT_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 老年年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static void setOldAgeAnnuityReceiptQryCondForm(OldAgeAnnuityReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setOldAgeAnnuityReceiptQryCondForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLDAGE_ANNUITY_RECEIPT_QRYCOND_FORM, form);
    }

    /**
     * 受理作業 - 老年年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static OldAgeAnnuityReceiptForm getOldAgeAnnuityReceiptQryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getOldAgeAnnuityReceiptQryCondForm() ...");

        HttpSession session = request.getSession();
        return (OldAgeAnnuityReceiptForm) session.getAttribute(ConstantKey.OLDAGE_ANNUITY_RECEIPT_QRYCOND_FORM);
    }

    /**
     * 受理作業 - 老年年金給付受理作業<br>
     * 將 qryCond Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOldAgeAnnuityReceiptQryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeOldAgeAnnuityReceiptQryCondForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.OLDAGE_ANNUITY_RECEIPT_QRYCOND_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 老年年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static void setOldAgeAnnuityReceiptQryForm(OldAgeAnnuityReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setOldAgeAnnuityReceiptQryCondForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLDAGE_ANNUITY_RECEIPT_QRYCOND_FORM, form);
    }

    /**
     * 受理作業 - 老年年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static OldAgeAnnuityReceiptForm getOldAgeAnnuityReceiptQryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getOldAgeAnnuityReceiptQryForm() ...");

        HttpSession session = request.getSession();
        return (OldAgeAnnuityReceiptForm) session.getAttribute(ConstantKey.OLDAGE_ANNUITY_RECEIPT_QUERY_FORM);
    }

    /**
     * 受理作業 - 老年年金給付受理作業<br>
     * 將 qryCond Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOldAgeAnnuityReceiptQryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeOldAgeAnnuityReceiptQryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.OLDAGE_ANNUITY_RECEIPT_QUERY_FORM);
    }
    
    /**
     * 受理作業 - 失能年金臨櫃受理作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledAnnuityWalkInReceiptQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledAnnuityWalkInReceiptQueryForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_ANNUITY_WALK_IN_RECEIPT_QUERY_FORM);
    }
    

    // ---------------------------------------------------------------------------------------
    // 受理作業 - 失能年金給付受理作業 - DisabledAnnuityReceipt
    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDisabledAnnuityReceiptForm(DisabledAnnuityReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledAnnuityReceiptForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_FORM, form);
    }

    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 從 Session 中 取得 form
     * 
     * @param request
     */
    public static DisabledAnnuityReceiptForm getDisabledAnnuityReceiptForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledAnnuityReceiptForm() ...");

        HttpSession session = request.getSession();
        return (DisabledAnnuityReceiptForm) session.getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_FORM);
    }

    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledAnnuityReceiptForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledAnnuityReceiptForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_FORM);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDisabledAnnuityReceiptFamForm(DisabledAnnuityReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledAnnuityReceiptFamForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_FAM_FORM, form);
    }

    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 從 Session 中 取得 form
     * 
     * @param request
     */
    public static DisabledAnnuityReceiptForm getDisabledAnnuityReceiptFamForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledAnnuityReceiptFamForm() ...");

        HttpSession session = request.getSession();
        return (DisabledAnnuityReceiptForm) session.getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_FAM_FORM);
    }

    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledAnnuityReceiptFamForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledAnnuityReceiptFamForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_FAM_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static void setDisabledAnnuityReceiptQryCondForm(DisabledAnnuityReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledAnnuityReceiptQryCondForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QRYCOND_FORM, form);
    }

    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static DisabledAnnuityReceiptForm getDisabledAnnuityReceiptQryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledAnnuityReceiptQryCondForm() ...");

        HttpSession session = request.getSession();
        return (DisabledAnnuityReceiptForm) session.getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QRYCOND_FORM);
    }

    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將 qryCond Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledAnnuityReceiptQryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledAnnuityReceiptQryCondForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QRYCOND_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static void setDisabledAnnuityReceiptQueryForm(DisabledAnnuityReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledAnnuityReceiptQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QUERY_FORM, form);
    }

    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static DisabledAnnuityReceiptForm getDisabledAnnuityReceiptQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledAnnuityReceiptQueryForm() ...");

        HttpSession session = request.getSession();
        return (DisabledAnnuityReceiptForm) session.getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QUERY_FORM);
    }

    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將 qryCond Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledAnnuityReceiptQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledAnnuityReceiptQueryForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_QUERY_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 受理作業 - 遺屬年金給付受理作業 - SurvivorAnnuityReceipt
    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setSurvivorAnnuityReceiptForm(SurvivorAnnuityReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorAnnuityReceiptForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_FORM, form);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static SurvivorAnnuityReceiptForm getSurvivorAnnuityReceiptForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorAnnuityReceiptForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorAnnuityReceiptForm) session.getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_FORM);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorAnnuityReceiptForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorAnnuityReceiptForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setSurvivorAnnuityReceiptBenForm(SurvivorAnnuityReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorAnnuityReceiptBenForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BEN_FORM, form);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static SurvivorAnnuityReceiptForm getSurvivorAnnuityReceiptBenForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorAnnuityReceiptBenForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorAnnuityReceiptForm) session.getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BEN_FORM);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorAnnuityReceiptBenForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorAnnuityReceiptBenForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BEN_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static void setSurvivorAnnuityReceiptQryForm(SurvivorAnnuityReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorAnnuityReceiptQryCondForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QRYCOND_FORM, form);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static SurvivorAnnuityReceiptForm getSurvivorAnnuityReceiptQryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorAnnuityReceiptQryForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorAnnuityReceiptForm) session.getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QUERY_FORM);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將 qryCond Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorAnnuityReceiptQryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorAnnuityReceiptQryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QUERY_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static void setSurvivorAnnuityReceiptQryCondForm(SurvivorAnnuityReceiptForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorAnnuityReceiptQryCondForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QRYCOND_FORM, form);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將 qryCond 加入 Session 中
     * 
     * @param request
     */
    public static SurvivorAnnuityReceiptForm getSurvivorAnnuityReceiptQryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorAnnuityReceiptQryCondForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorAnnuityReceiptForm) session.getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QRYCOND_FORM);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業<br>
     * 將 qryCond 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorAnnuityReceiptQryCondForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorAnnuityReceiptQryCondForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_QRYCOND_FORM);
    }
    // ---------------------------------------------------------------------------------------
}
