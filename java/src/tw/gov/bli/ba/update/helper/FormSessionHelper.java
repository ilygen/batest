package tw.gov.bli.ba.update.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.rpt.forms.BatchPaymentReceiveForm;
import tw.gov.bli.ba.update.forms.ApplicationDataUpdateForm;
import tw.gov.bli.ba.update.forms.CheckMarkLevelAdjustForm;
import tw.gov.bli.ba.update.forms.CommunicationDataUpdateForm;
import tw.gov.bli.ba.update.forms.DependantDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.DependantDataUpdateQueryForm;
import tw.gov.bli.ba.update.forms.DisabledApplicationDataUpdateForm;
import tw.gov.bli.ba.update.forms.DisabledCheckMarkLevelAdjustForm;
import tw.gov.bli.ba.update.forms.DisabledPayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.DisabledPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.forms.DisabledPaymentReceiveForm;
import tw.gov.bli.ba.update.forms.InheritorRegisterForm;
import tw.gov.bli.ba.update.forms.OldAgeDeathRepayForm;
import tw.gov.bli.ba.update.forms.OldAgePaymentReceiveForm;
import tw.gov.bli.ba.update.forms.OnceAmtDataUpdateForm;
import tw.gov.bli.ba.update.forms.OwesDataUpdateForm;
import tw.gov.bli.ba.update.forms.PayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.PayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.forms.PaymentDataUpdateForm;
import tw.gov.bli.ba.update.forms.StopPaymentProcessDetailForm;
import tw.gov.bli.ba.update.forms.StopPaymentProcessQueryForm;
import tw.gov.bli.ba.update.forms.SurvivorApplicationDataUpdateForm;
import tw.gov.bli.ba.update.forms.SurvivorCheckMarkLevelAdjustForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateCompelDataForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateDetailForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateHandicapTermForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateStudTermForm;
import tw.gov.bli.ba.update.forms.SurvivorPaymentReceiveForm;

/**
 * Form Helper for 更正作業
 * 
 * @author Rickychi
 */
public class FormSessionHelper {
    private static Log log = LogFactory.getLog(FormSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 通訊資料更正 - CommunicationDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 通訊資料更正<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCommunicationDataUpdateQueryForm(CommunicationDataUpdateForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setCommunicationDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.COMMUNICATION_DATA_UPDATE_QUERY_FORM, form);
    }

    /**
     * 更正作業 - 通訊資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCommunicationDataUpdateQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCommunicationDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.COMMUNICATION_DATA_UPDATE_QUERY_FORM);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 更正作業 - 通訊資料更正<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCommunicationDataUpdateForm(CommunicationDataUpdateForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setCommunicationDataUpdateForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.COMMUNICATION_DATA_UPDATE_FORM, form);
    }

    /**
     * 更正作業 - 通訊資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCommunicationDataUpdateForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCommunicationDataUpdateForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.COMMUNICATION_DATA_UPDATE_FORM);
    }

    // ---------------------------------------------------------------------------------------

    // 更正作業 - 給付資料更正 - PaymentDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 給付資料更正<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setPaymentDataUpdateQueryForm(PaymentDataUpdateForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setPaymentDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DATA_UPDATE_QUERY_FORM, form);
    }

    /**
     * 更正作業 - 給付資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDataUpdateQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePaymentDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.PAYMENT_DATA_UPDATE_QUERY_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 給付資料更正<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setPaymentDataUpdateForm(PaymentDataUpdateForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setPaymentDataUpdateForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DATA_UPDATE_FORM, form);
    }

    /**
     * 更正作業 - 給付資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDataUpdateForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePaymentDataUpdateForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.PAYMENT_DATA_UPDATE_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 案件資料更正 - ApplicationDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 案件資料更正<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setApplicationDataUpdateForm(ApplicationDataUpdateForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setApplicationDataUpdateForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.APPLICATION_DATA_UPDATE_FORM, form);
    }

    /**
     * 更正作業 - 給付資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeApplicationDataUpdateForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeApplicationDataUpdateForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.APPLICATION_DATA_UPDATE_FORM);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 案件資料更正<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setApplicationDataUpdateQueryForm(ApplicationDataUpdateForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setApplicationDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.APPLICATION_DATA_UPDATE_QUERY_FORM, form);
    }

    /**
     * 更正作業 - 給付資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeApplicationDataUpdateQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeApplicationDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.APPLICATION_DATA_UPDATE_QUERY_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 案件資料更正 - 欠費期間維護 - OwesDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 案件資料更正 - 欠費期間維護<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setOwesDataUpdateForm(OwesDataUpdateForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setOwesDataUpdateForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OWES_DATA_UPDATE_FORM, form);
    }

    /**
     * 更正作業 - 給付資料更正 - 欠費期間維護<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOwesDataUpdateForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeOwesDataUpdateForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.OWES_DATA_UPDATE_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 案件資料更正 - 一次給付資料更正 - OnceAmtDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 案件資料更正 - 一次給付資料更正<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setOnceAmtDataUpdateForm(OnceAmtDataUpdateForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setOnceAmtDataUpdateForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.ONCEAMT_DATA_UPDATE_FORM, form);
    }

    /**
     * 更正作業 - 給付資料更正 - 一次給付資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOnceAmtDataUpdateForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeOnceAmtDataUpdateForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.ONCEAMT_DATA_UPDATE_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 債務繼承人資料登錄作業 - InheritorRegister
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 債務繼承人資料登錄作業<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setInheritorRegisterForm(InheritorRegisterForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setInheritorRegisterForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.INHERITOR_REGISTER_FORM, form);
    }

    /**
     * 更正作業 - 債務繼承人資料登錄作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeInheritorRegisterForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeInheritorRegisterForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.INHERITOR_REGISTER_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 受款人資料更正 - PayeeDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 受款人資料更正 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static PayeeDataUpdateQueryForm getPayeeDataUpdateQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getPayeeDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        return (PayeeDataUpdateQueryForm) session.getAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_FORM);
    }

    /**
     * 更正作業 - 受款人資料更正<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setPayeeDataUpdateQueryForm(PayeeDataUpdateQueryForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setPayeeDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_FORM, form);
    }

    /**
     * 更正作業 - 受款人資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removePayeeDataUpdateQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePayeeDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_FORM);
    }

    /**
     * 更正作業 - 受款人資料更正 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setPayeeDataUpdateDetailForm(PayeeDataUpdateDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setPayeeDataUpdateDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYEE_DATA_UPDATE_DETAIL_FORM, form);
    }

    /**
     * 更正作業 - 受款人資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removePayeeDataUpdateDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePayeeDataUpdateDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.PAYEE_DATA_UPDATE_DETAIL_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 失能年金受款人資料更正 - DisabledPayeeDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 失能年金受款人資料更正 <br>
     * 將 Form 加入 Session 中
     * @param request
     */
    public static DisabledPayeeDataUpdateQueryForm getDisabledPayeeDataUpdateQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledPayeeDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        return (DisabledPayeeDataUpdateQueryForm) session.getAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_FORM);
    }

    /**
     * 更正作業 - 失能年金受款人資料更正<br>
     * 將 Form 加入 Session 中
     * @param request
     */
    public static void setDisabledPayeeDataUpdateQueryForm(DisabledPayeeDataUpdateQueryForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledPayeeDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_FORM, form);
    }
    
    /**
     * 更正作業 - 失能年金受款人資料更正 <br>
     * 將 Form 加入 Session 中

     * 
     * @param request
     */
    public static void setDisabledPayeeDataUpdateDetailForm(DisabledPayeeDataUpdateDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledPayeeDataUpdateDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_DETAIL_FORM, form);
    }

    /**
     * 更正作業 - 失能年金受款人資料更正<br>
     * 將相關 Form 自 Session 中移除
     * @param request
     */
    public static void removeDisabledPayeeDataUpdateQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledPayeeDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_FORM);
    }
    
    /**
     * 更正作業 - 失能年金受款人資料更正<br>
     * 將相關 Form 自 Session 中移除

     * 
     * @param request
     */
    public static void removeDisabledPayeeDataUpdateDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledPayeeDataUpdateDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_DETAIL_FORM);
    }
    
    // ---------------------------------------------------------------------------------------
    // 更正作業 - 遺屬年金受款人資料更正 - SurvivorPayeeDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 遺屬年金受款人資料更正 <br>
     * 將 Form 由 Session 中取出
     * @param request
     */
    public static SurvivorPayeeDataUpdateQueryForm getSurvivorPayeeDataUpdateQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorPayeeDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorPayeeDataUpdateQueryForm) session.getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_FORM);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正<br>
     * 將 Form 加入 Session 中
     * @param request
     */
    public static void setSurvivorPayeeDataUpdateQueryForm(SurvivorPayeeDataUpdateQueryForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorPayeeDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_FORM, form);
    }
    
    /**
     * 更正作業 - 遺屬年金受款人資料更正 <br>
     * 將 Form 加入 Session 中
     * @param request
     */
    public static void setSurvivorPayeeDataUpdateDetailForm(SurvivorPayeeDataUpdateDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorPayeeDataUpdateDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_FORM, form);
    }
    
    /**
     * 更正作業 - 遺屬年金受款人資料更正 <br>
     * 將 Form 由 Session 中取出
     * @param request
     */
    public static SurvivorPayeeDataUpdateDetailForm getSurvivorPayeeDataUpdateDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorPayeeDataUpdateDetailForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorPayeeDataUpdateDetailForm)session.getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_FORM);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正<br>
     * 將相關 Form 自 Session 中移除
     * @param request
     */
    public static void removeSurvivorPayeeDataUpdateQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorPayeeDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_FORM);
    }
    
    /**
     * 更正作業 - 遺屬年金受款人資料更正<br>
     * 將相關 Form 自 Session 中移除
     * @param request
     */
    public static void removeSurvivorPayeeDataUpdateDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorPayeeDataUpdateDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_FORM);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正<br>
     * 將 Form 加入 Session 中
     * @param request
     */
    public static void setSurvivorPayeeDataUpdateStudTermForm(SurvivorPayeeDataUpdateStudTermForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorPayeeDataUpdateStudTermForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_STUDTERM_FORM, form);
    }

    /**
         * 更正作業 - 遺屬年金受款人資料更正 <br>
         * 將 Form 由 Session 中取出
         * @param request
         */
    public static SurvivorPayeeDataUpdateStudTermForm getSurvivorPayeeDataUpdateStudTermForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorPayeeDataUpdateDetailForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorPayeeDataUpdateStudTermForm) session.getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_STUDTERM_FORM);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正<br>
     * 將相關 Form 自 Session 中移除
     * @param request
     */
    public static void removeSurvivorPayeeDataUpdateStudTermForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorPayeeDataUpdateStudTermForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_STUDTERM_FORM);
    }
    
    /**
     * 更正作業 - 遺屬年金受款人資料更正<br>
     * 將 Form 加入 Session 中
     * @param request
     */
    public static void setSurvivorPayeeDataUpdateCompelDataForm(SurvivorPayeeDataUpdateCompelDataForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorPayeeDataUpdateCompelDataForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_COMPEL_DATA_FORM, form);
    }

    /**
         * 更正作業 - 遺屬年金受款人資料更正 <br>
         * 將 Form 由 Session 中取出
         * @param request
         */
    public static SurvivorPayeeDataUpdateCompelDataForm getSurvivorPayeeDataUpdateCompelDataForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorPayeeDataUpdateCompelDataForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorPayeeDataUpdateCompelDataForm) session.getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_COMPEL_DATA_FORM);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正<br>
     * 將相關 Form 自 Session 中移除
     * @param request
     */
    public static void removeSurvivorPayeeDataUpdateCompelDataForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorPayeeDataUpdateCompelDataForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_COMPEL_DATA_FORM);
    }
    
    /**
     * 更正作業 - 遺屬年金受款人資料更正<br>
     * 將 Form 加入 Session 中
     * @param request
     */
    public static void setSurvivorPayeeDataUpdateHandicapTermForm(SurvivorPayeeDataUpdateHandicapTermForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorPayeeDataUpdateHandicapTermForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_HANDICAPTERM_FORM, form);
    }
    
    /**
         * 更正作業 - 遺屬年金受款人資料更正 <br>
         * 將 Form 由 Session 中取出
         * @param request
         */
    public static SurvivorPayeeDataUpdateHandicapTermForm getSurvivorPayeeDataUpdateHandicapTermForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.SurvivorPayeeDataUpdateHandicapTermForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorPayeeDataUpdateHandicapTermForm) session.getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_HANDICAPTERM_FORM);
    }
    
    /**
     * 更正作業 - 遺屬年金受款人資料更正<br>
     * 將相關 Form 自 Session 中移除
     * @param request
     */
    public static void removeSurvivorPayeeDataUpdateHandicapTermForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorPayeeDataUpdateHandicapTermForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_HANDICAPTERM_FORM);
    }
    
    // ---------------------------------------------------------------------------------------
    // 更正作業 - 眷屬資料更正 - DependantDataUpdate
    // ---------------------------------------------------------------------------------------

    /**
     * 更正作業 - 眷屬資料更正 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static DependantDataUpdateQueryForm getDependantDataUpdateQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDependantDataUpdateQueryFromm() ...");

        HttpSession session = request.getSession();
        return (DependantDataUpdateQueryForm) session.getAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_QUERY_FORM);
    }

    /**
     * 更正作業 - 眷屬資料更正<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDependantDataUpdateQueryForm(DependantDataUpdateQueryForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDependantDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_QUERY_FORM, form);
    }

    /**
     * 更正作業 - 眷屬資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDependantDataUpdateQueryFrom(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removePayeeDataUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_QUERY_FORM);
    }

    /**
     * 更正作業 - 眷屬資料更正 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setDependantDataUpdateDetailForm(DependantDataUpdateDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDependantDataUpdateDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_DETAIL_FORM, form);
    }
    
    /**
     * 更正作業 - 眷屬資料更正 <br>
     * 將 Form 由 Session 中取出
     * @param request
     */
    public static DependantDataUpdateDetailForm getDependantDataUpdateDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDependantDataUpdateDetailForm() ...");

        HttpSession session = request.getSession();
        return (DependantDataUpdateDetailForm)session.getAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_DETAIL_FORM);
    }

    /**
     * 更正作業 - 眷屬資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDependantDataUpdateDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDependantDataUpdateDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_DETAIL_FORM);
    }
    
    /**
     * 更正作業 - 眷屬資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDependantDataUpdateQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDependantUpdateQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_QUERY_FORM);
    }
    
    /**
     * 更正作業 - 眷屬資料更正<br>
     * 將相關 Form 自 Session 中移除
     * @param request
     */
    public static void removeDependantDataUpdateStudTermForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDependantDataUpdateStudTermForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_STUDTERM_FORM);
    }
    
    /**
     * 更正作業 - 眷屬資料更正<br>
     * 將相關 Form 自 Session 中移除
     * @param request
     */
    public static void removeDependantDataUpdateCompelDataForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDependantDataUpdateCompelDataForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_COMPEL_DATA_FORM);
    }    

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 止付處理 - StopPaymentProcess
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 止付處理 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static StopPaymentProcessQueryForm getStopPaymentProcessQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getStopPaymentProcessQueryForm() ...");

        HttpSession session = request.getSession();
        return (StopPaymentProcessQueryForm) session.getAttribute(ConstantKey.STOP_PAYMENT_PROCESS_QUERY_FORM);
    }

    /**
     * 更正作業 - 止付處理<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setStopPaymentProcessQueryForm(StopPaymentProcessQueryForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setStopPaymentProcessQueryForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.STOP_PAYMENT_PROCESS_QUERY_FORM, form);
    }

    /**
     * 更正作業 - 止付處理<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeStopPaymentProcessQueryForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeStopPaymentProcessQueryForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.STOP_PAYMENT_PROCESS_QUERY_FORM);
    }

    /**
     * 更正作業 - 止付處理 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setStopPaymentProcessDetailForm(StopPaymentProcessDetailForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setStopPaymentProcessDetailForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.STOP_PAYMENT_PROCESS_DETAIL_FORM, form);
    }

    /**
     * 更正作業 - 止付處理<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeStopPaymentProcessDetailForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeStopPaymentProcessDetailForm() ...");

        HttpSession session = request.getSession();
        // 查詢頁面
        session.removeAttribute(ConstantKey.STOP_PAYMENT_PROCESS_DETAIL_FORM);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 編審註記程度調整 - CheckMarkLevelAdjust
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 編審註記程度調整 <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static CheckMarkLevelAdjustForm getCheckMarkLevelAdjustForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.CheckMarkLevelAdjustForm() ...");

        HttpSession session = request.getSession();
        return (CheckMarkLevelAdjustForm) session.getAttribute(ConstantKey.CHECK_MARK_LEVEL_ADJUST_FORM);
    }

    /**
     * 更正作業 - 編審註記程度調整<br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     */
    public static void setCheckMarkLevelAdjustForm(CheckMarkLevelAdjustForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setCheckMarkLevelAdjustForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CHECK_MARK_LEVEL_ADJUST_FORM, form);
    }

    /**
     * 更正作業 - 編審註記程度調<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCheckMarkLevelAdjustFormm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCheckMarkLevelAdjustFormm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CHECK_MARK_LEVEL_ADJUST_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 (bamo0d160c.jsp bamo0d161c.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */
    public static DisabledCheckMarkLevelAdjustForm getDisabledCheckMarkLevelAdjustForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledCheckMarkLevelAdjustForm() ...");

        HttpSession session = request.getSession();
        return (DisabledCheckMarkLevelAdjustForm) session.getAttribute(ConstantKey.DISABLED_CHECK_MARK_LEVEL_ADJUST_FORM);
    }

    /**
     * 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 (bamo0d160c.jsp bamo0d161c.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param form
     * @param request
     */
    public static void setDisabledCheckMarkLevelAdjustForm(DisabledCheckMarkLevelAdjustForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledCheckMarkLevelAdjustForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_CHECK_MARK_LEVEL_ADJUST_FORM, form);
    }

    /**
     * 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 (bamo0d160c.jsp bamo0d161c.jsp) <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledCheckMarkLevelAdjustForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledCheckMarkLevelAdjustForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_CHECK_MARK_LEVEL_ADJUST_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整 (bamo0d260c.jsp bamo0d261c.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */
    public static SurvivorCheckMarkLevelAdjustForm getSurvivorCheckMarkLevelAdjustForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorCheckMarkLevelAdjustForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorCheckMarkLevelAdjustForm) session.getAttribute(ConstantKey.SURVIVOR_CHECK_MARK_LEVEL_ADJUST_FORM);
    }

    /**
     * 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整 (bamo0d260c.jsp bamo0d261c.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param form
     * @param request
     */
    public static void setSurvivorCheckMarkLevelAdjustForm(SurvivorCheckMarkLevelAdjustForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorCheckMarkLevelAdjustForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_CHECK_MARK_LEVEL_ADJUST_FORM, form);
    }

    /**
     * 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整 (bamo0d260c.jsp bamo0d261c.jsp) <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorCheckMarkLevelAdjustForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorCheckMarkLevelAdjustForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_CHECK_MARK_LEVEL_ADJUST_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 案件資料更正 - 失能年金案件資料更正
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */
    public static DisabledApplicationDataUpdateForm getDisabledApplicationDataUpdateForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledApplicationDataUpdateForm() ...");

        HttpSession session = request.getSession();
        return (DisabledApplicationDataUpdateForm) session.getAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_FORM);
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param form
     * @param request
     */
    public static void setDisabledApplicationDataUpdateForm(DisabledApplicationDataUpdateForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledApplicationDataUpdateForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_FORM, form);
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp) <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledApplicationDataUpdateForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledApplicationDataUpdateForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_FORM);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 案件資料更正 - 遺屬年金案件資料更正
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */
    public static SurvivorApplicationDataUpdateForm getSurvivorApplicationDataUpdateForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorApplicationDataUpdateForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorApplicationDataUpdateForm) session.getAttribute(ConstantKey.SURVIVOR_APPLICATION_DATA_UPDATE_FORM);
    }

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp) <br>
     * 將 Form 加入 Session 中
     * 
     * @param form
     * @param request
     */
    public static void setSurvivorApplicationDataUpdateForm(SurvivorApplicationDataUpdateForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorApplicationDataUpdateForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_APPLICATION_DATA_UPDATE_FORM, form);
    }

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp) <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorApplicationDataUpdateForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorApplicationDataUpdateForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_APPLICATION_DATA_UPDATE_FORM);
    }
    
    // ---------------------------------------------------------------------------------------
    // 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)
    // ---------------------------------------------------------------------------------------
    
    /**
     * 更正作業 - 死亡改匯處理作業  - 老年年金受款人死亡改匯處理(BAMO0D090C,BAMO0D091C) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */

    public static void setOldAgeDeathRepayForm( OldAgeDeathRepayForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setsetOldAgeDeathRepayForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLDAGE_DEATH_REPAY_FORM, form);
    }
    
    /**
     * 更正作業 - 死亡改匯處理作業  - 老年年金受款人死亡改匯處理(BAMO0D090C,BAMO0D091C) <br>
     * 取得 Session Form 
     * 
     * @param request
     * @return
     */
    public static OldAgeDeathRepayForm getOldAgeDeathRepayForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getOldAgeDeathRepayForm() ...");

        HttpSession session = request.getSession();
        return (OldAgeDeathRepayForm) session.getAttribute(ConstantKey.OLDAGE_DEATH_REPAY_FORM);
    }

    /**
     * 更正作業 - 死亡改匯處理作業  - 老年年金受款人死亡改匯處理(BAMO0D090C,BAMO0D091C) <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     * @return
     */
    public static void removeOldAgeDeathRepayForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeOldAgeDeathRepayForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.OLDAGE_DEATH_REPAY_FORM);
    }
    
    // ---------------------------------------------------------------------------------------
    // 更正作業 - 應收收回處理作業 - 老年年金應收收回處理作業 (BAMO0D100C)
    // ---------------------------------------------------------------------------------------
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理作業 (BAMO0D100C) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */

    public static void setOldAgePaymentReceiveForm( OldAgePaymentReceiveForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setOldAgePaymentReceiveForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLDAGE_PAYMENT_RECEIVE_FORM, form);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理作業 (BAMO0D100C) <br>
     * 取得 Session Form 
     * 
     * @param request
     * @return
     */
    public static OldAgePaymentReceiveForm getOldAgePaymentReceiveForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getOldAgePaymentReceiveForm() ...");

        HttpSession session = request.getSession();
        return (OldAgePaymentReceiveForm) session.getAttribute(ConstantKey.OLDAGE_PAYMENT_RECEIVE_FORM);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理作業 (BAMO0D100C) <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     * @return
     */
    public static void removeOldAgePaymentReceiveForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeOldAgePaymentReceiveForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.OLDAGE_PAYMENT_RECEIVE_FORM);
    }
    
 // ---------------------------------------------------------------------------------------
    // 更正作業 - 應收收回處理作業 - 失能年金應收收回處理作業 (BAMO0D200C)
    // ---------------------------------------------------------------------------------------
    
    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理作業 (BAMO0D200C) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */

    public static void setDisabledPaymentReceiveForm( DisabledPaymentReceiveForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setDisabledPaymentReceiveForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_RECEIVE_FORM, form);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理作業 (BAMO0D200C) <br>
     * 取得 Session Form 
     * 
     * @param request
     * @return
     */
    public static DisabledPaymentReceiveForm getDisabledPaymentReceiveForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getDisabledPaymentReceiveForm() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentReceiveForm) session.getAttribute(ConstantKey.DISABLED_PAYMENT_RECEIVE_FORM);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理作業 (BAMO0D200C) <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     * @return
     */
    public static void removeDisabledPaymentReceiveForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeDisabledPaymentReceiveForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_RECEIVE_FORM);
    }
    
 // ---------------------------------------------------------------------------------------
    // 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理作業 (BAMO0D300C)
    // ---------------------------------------------------------------------------------------
    
    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理作業 (BAMO0D300C) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */

    public static void setSurvivorPaymentReceiveForm( SurvivorPaymentReceiveForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setSurvivorPaymentReceiveForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_RECEIVE_FORM, form);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理作業 (BAMO0D300C) <br>
     * 取得 Session Form 
     * 
     * @param request
     * @return
     */
    public static SurvivorPaymentReceiveForm getSurvivorPaymentReceiveForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.getSurvivorPaymentReceiveForm() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentReceiveForm) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_RECEIVE_FORM);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理作業 (BAMO0D300C) <br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     * @return
     */
    public static void removeSurvivorPaymentReceiveForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeSurvivorPaymentReceiveForm() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_RECEIVE_FORM);
    }
    
    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 (BALP0D690l) <br>
     * 將 Form 加入 Session 中
     * 
     * @param request
     * @return
     */

    public static void setBatchPaymentReceiveForm(BatchPaymentReceiveForm form, HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.setBatchPaymentReceiveForm() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.BATCH_PAYMENT_RECEIVE_FORM, form);
    }
    
    /**
     * 更正作業 - 給付資料更正<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeBatchPaymentReceiveForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeBatchPaymentReceiveForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.BATCH_PAYMENT_RECEIVE_FORM);
    }

    /**
     * 更正作業 - 結案狀態變更作業<br>
     * 將相關 Form 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCloseStatusAlterationForm(HttpServletRequest request) {
        log.debug("執行 FormSessionHelper.removeCloseStatusAlterationForm() ...");

        HttpSession session = request.getSession();
        // 各 頁面
        session.removeAttribute(ConstantKey.CLOSE_STATUS_ALTERATION_FORM);
    }
}
