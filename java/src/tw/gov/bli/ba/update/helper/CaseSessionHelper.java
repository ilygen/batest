package tw.gov.bli.ba.update.helper;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.UpdatePaidMarkBJCase;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Bahandicapterm;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.ba.query.cases.PaymentQueryDetailDataCase;
import tw.gov.bli.ba.rpt.cases.BatchPaymentReceiveDataCase;
import tw.gov.bli.ba.update.cases.AccountsReceivableDataCase;
import tw.gov.bli.ba.update.cases.ApplicationUpdateDupeIdnNoCase;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.BadaprDataCase;
import tw.gov.bli.ba.update.cases.BasenimaintCase;
import tw.gov.bli.ba.update.cases.CashReceiveDataCase;
import tw.gov.bli.ba.update.cases.CheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.cases.CloseStatusAlterationCase;
import tw.gov.bli.ba.update.cases.CloseStatusAlterationCasePayeeList;
import tw.gov.bli.ba.update.cases.DependantDataUpdateCase;
import tw.gov.bli.ba.update.cases.DependantEvtDataUpdateCase;
import tw.gov.bli.ba.update.cases.DisabledAccountsReceivableDataCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateBareCheckCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCase;
import tw.gov.bli.ba.update.cases.DisabledCashReceiveDataCase;
import tw.gov.bli.ba.update.cases.DisabledCheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.cases.DisabledPaymentReceiveDataCase;
import tw.gov.bli.ba.update.cases.DisabledRemittanceReceiveDataCase;
import tw.gov.bli.ba.update.cases.InheritorRegisterCase;
import tw.gov.bli.ba.update.cases.OldAgePaymentReceiveDataCase;
import tw.gov.bli.ba.update.cases.RemittanceReceiveDataCase;
import tw.gov.bli.ba.update.cases.StopPaymentProcessCase;
import tw.gov.bli.ba.update.cases.SurvivorAccountsReceivableDataCase;
import tw.gov.bli.ba.update.cases.SurvivorApplicationDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorCashReceiveDataCase;
import tw.gov.bli.ba.update.cases.SurvivorCheckMarkLevelAdjustCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCompelDataCase;
import tw.gov.bli.ba.update.cases.SurvivorPaymentReceiveDataCase;
import tw.gov.bli.ba.update.cases.SurvivorRemittanceReceiveDataCase;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.cases.DependantDataUpdateCompelDataCase;

/**
 * Case Helper for 更正作業
 * 
 * @author Goston
 */
public class CaseSessionHelper {
    private static Log log = LogFactory.getLog(CaseSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 通訊資料更正 - CommunicationDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 通訊資料更正 - 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCommunicationDataUpdateList(List<BaappbaseDataUpdateCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCommunicationDataUpdateList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.COMMUNICATION_DATA_UPDATE_LIST, list);
    }

    /**
     * 更正作業 - 通訊資料更正 - 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<BaappbaseDataUpdateCase> getCommunicationDataUpdateList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getCommunicationDataUpdateList() ...");

        HttpSession session = request.getSession();
        return (List<BaappbaseDataUpdateCase>) session.getAttribute(ConstantKey.COMMUNICATION_DATA_UPDATE_LIST);
    }

    /**
     * 更正作業 - 通訊資料更正 - 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCommunicationDataUpdateList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCommunicationDataUpdateList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.COMMUNICATION_DATA_UPDATE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 更正作業 - 通訊資料更正<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCommunicationDataUpdateCase(BaappbaseDataUpdateCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCommunicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.BAAPPBASE_DATA_UPDATE_CASE, obj);
    }

    /**
     * 更正作業 - 通訊資料更正<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static List<BaappbaseDataUpdateCase> getCommunicationDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getCommunicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        return (List<BaappbaseDataUpdateCase>) session.getAttribute(ConstantKey.BAAPPBASE_DATA_UPDATE_CASE);
    }

    /**
     * 更正作業 - 通訊資料更正<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCommunicationDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCommunicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.BAAPPBASE_DATA_UPDATE_CASE);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 給付資料更正 - PaymentDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 給付資料更正 - 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentDataUpdateList(List<BaappbaseDataUpdateCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDataUpdateList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DATA_UPDATE_LIST, list);
    }

    /**
     * 更正作業 - 給付資料更正 - 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<BaappbaseDataUpdateCase> getPaymentDataUpdateList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDataUpdateList() ...");

        HttpSession session = request.getSession();
        return (List<BaappbaseDataUpdateCase>) session.getAttribute(ConstantKey.PAYMENT_DATA_UPDATE_LIST);
    }

    /**
     * 更正作業 - 給付資料更正 - 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDataUpdateList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDataUpdateList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DATA_UPDATE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 更正作業 - 給付資料更正<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentDataUpdateCase(BaappbaseDataUpdateCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.BAAPPBASE_DATA_UPDATE_CASE, obj);
    }

    /**
     * 更正作業 - 給付資料更正<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static BaappbaseDataUpdateCase getPaymentDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDataUpdateCase() ...");

        HttpSession session = request.getSession();
        return (BaappbaseDataUpdateCase) session.getAttribute(ConstantKey.BAAPPBASE_DATA_UPDATE_CASE);
    }

    /**
     * 更正作業 - 給付資料更正<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.BAAPPBASE_DATA_UPDATE_CASE);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 案件資料更正 - ApplicationDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 案件資料更正<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setApplicationDataUpdateCase(BaappbaseDataUpdateCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.BAAPPBASE_DATA_UPDATE_CASE, obj);
    }

    /**
     * 更正作業 - 案件資料更正<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static BaappbaseDataUpdateCase getApplicationDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        return (BaappbaseDataUpdateCase) session.getAttribute(ConstantKey.BAAPPBASE_DATA_UPDATE_CASE);
    }

    /**
     * 更正作業 - 案件資料更正<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeApplicationDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.BAAPPBASE_DATA_UPDATE_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 更正作業 - 案件資料更正 - 欠費更正資料<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setBasenimaintCaseList(List<BasenimaintCase> obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setBasenimaintCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.BASENIMAINTCASE_LIST, obj);
    }

    /**
     * 更正作業 - 案件資料更正 - 欠費更正資料<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<BasenimaintCase> getBasenimaintCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getBasenimaintCaseList() ...");

        HttpSession session = request.getSession();
        return (List<BasenimaintCase>) session.getAttribute(ConstantKey.BASENIMAINTCASE_LIST);
    }

    /**
     * 更正作業 - 案件資料更正 - 欠費更正資料<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeBasenimaintCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeBasenimaintCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.BASENIMAINTCASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 更正作業 - 案件資料更正 - 身分證號重號資料<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setApplicationUpdateDupeIdnNoCaseList(List<ApplicationUpdateDupeIdnNoCase> obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setApplicationUpdateDupeIdnNoCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.APPLICATION_UPDATE_DUPEIDNNO_CASE_LIST, obj);
    }

    /**
     * 更正作業 - 案件資料更正 - 身分證號重號資料<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<ApplicationUpdateDupeIdnNoCase> getApplicationUpdateDupeIdnNoCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getApplicationUpdateDupeIdnNoCaseList() ...");

        HttpSession session = request.getSession();
        return (List<ApplicationUpdateDupeIdnNoCase>) session.getAttribute(ConstantKey.APPLICATION_UPDATE_DUPEIDNNO_CASE_LIST);
    }

    /**
     * 更正作業 - 案件資料更正 - 身分證號重號資料<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeApplicationUpdateDupeIdnNoCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeApplicationUpdateDupeIdnNoCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.APPLICATION_UPDATE_DUPEIDNNO_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 債務繼承人資料更正 - inheritorRegister
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 債務繼承人資料更正<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setInheritorRegisterCase(InheritorRegisterCase Case, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setInheritorRegisterCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.INHERITOR_REGISTER_CASE, Case);
    }

    /**
     * 更正作業 - 債務繼承人資料更正<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static InheritorRegisterCase getInheritorRegisterCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getInheritorRegisterCase() ...");

        HttpSession session = request.getSession();
        return (InheritorRegisterCase) session.getAttribute(ConstantKey.INHERITOR_REGISTER_CASE);
    }

    /**
     * 更正作業 - 債務繼承人資料更正<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeInheritorRegisterCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeInheritorRegisterCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.INHERITOR_REGISTER_CASE);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 受款人資料更正 - PayeeDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 受款人資料更正 - 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePayeeDataUpdateList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePayeeDataUpdateList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYEE_DATA_UPDATE_LIST);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static List<BaappbaseDataUpdateCase> getPayeeDataUpdateQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<BaappbaseDataUpdateCase>) session.getAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPayeeDataUpdateQueryCase(List<BaappbaseDataUpdateCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE, caseData);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePayeeDataUpdateQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE);
    }
    
    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static List<BaappbaseDataUpdateCase> getPayeeDataUpdateQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<BaappbaseDataUpdateCase>) session.getAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE_LIST);
    }
    
    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPayeeDataUpdateQueryCaseList(List<BaappbaseDataUpdateCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE_LIST, caseData);
    }
    
    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePayeeDataUpdateQueryCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE_LIST);
    }


    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static Baappbase getPayeeDataUpdateDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        return (Baappbase) session.getAttribute(ConstantKey.PAYEE_DATA_UPDATE_DETAIL_CASE);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request List<Badapr>
     */
    public static void setPayeeDataUpdateDetailCase(Baappbase caseData, Integer Q1, Integer Q2, String accSeqNoControl, Integer relationQ2, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE_Q1, Q1);
        session.setAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE_Q2, Q2);
        session.setAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE_ACCSEQNO_CONTROL, accSeqNoControl);
        session.setAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE_RELATIONQ2, Q2);
        session.setAttribute(ConstantKey.PAYEE_DATA_UPDATE_DETAIL_CASE, caseData);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePayeeDataUpdateDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE_Q1);
        session.removeAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE_Q2);
        session.removeAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE_ACCSEQNO_CONTROL);
        session.removeAttribute(ConstantKey.PAYEE_DATA_UPDATE_QUERY_CASE_RELATIONQ2);
        session.removeAttribute(ConstantKey.PAYEE_DATA_UPDATE_DETAIL_CASE);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPayeeDataForBadaprCase(BadaprDataCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPayeeDataForBadaprCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYEE_DATA_UPDATE_BADAPR_CASE, caseData);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static BadaprDataCase getPayeeDataForBadaprCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPayeeDataForBadaprCase() ...");

        HttpSession session = request.getSession();
        return (BadaprDataCase) session.getAttribute(ConstantKey.PAYEE_DATA_UPDATE_BADAPR_CASE);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePayeeDataForBadaprCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePayeeDataForBadaprCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYEE_DATA_UPDATE_BADAPR_CASE);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 受款人資料更正 - 失能年金受款人資料更正 DisabledPayeeDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 失能年金受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPayeeDataUpdateQueryCase(List<BaappbaseDataUpdateCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE, caseData);
    }

    /**
     * 更正作業 - 失能年金受款人資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static List<BaappbaseDataUpdateCase> getDisabledPayeeDataUpdateQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<BaappbaseDataUpdateCase>) session.getAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE);
    }

    /**
     * 更正作業 - 失能年金受款人資料更正作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPayeeDataUpdateQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE);
    }

    /**
     * 更正作業 - 失能年金受款人資料更正作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPayeeDataUpdateDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_Q1);
        session.removeAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_Q2);
        session.removeAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_ACCSEQNO_CONTROL);
        session.removeAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_RELATIONQ2);
        session.removeAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_DETAIL_CASE);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request List<Badapr>
     */
    public static void setDisabledPayeeDataUpdateDetailCase(Baappbase caseData, Integer Q1, Integer Q2, String accSeqNoControl, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_Q1, Q1);
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_Q2, Q2);
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_ACCSEQNO_CONTROL, accSeqNoControl);
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_DETAIL_CASE, caseData);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request List<Badapr>
     */
    public static void setDisabledPayeeDataUpdateDetailCase(Baappbase caseData, boolean displayIdnChkYm, boolean enableCoreceive, String accSeqNoControl, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        // 身分查核年月顯示與否
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_Q1, displayIdnChkYm);
        // 具名領取Enable與否
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_Q2, enableCoreceive);
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_QUERY_CASE_ACCSEQNO_CONTROL, accSeqNoControl);
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_DETAIL_CASE, caseData);
    }

    /**
     * 更正作業 - 失能年金受款人資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static Baappbase getDisabledPayeeDataUpdateDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDiasbledPayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        return (Baappbase) session.getAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_DETAIL_CASE);
    }

    /**
     * 更正作業 - 失能年金受款人資料更正 - 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPayeeDataUpdateList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPayeeDataUpdateList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_LIST);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPayeeDataForBadaprCase(BadaprDataCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPayeeDataForBadaprCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYEE_DATA_UPDATE_BADAPR_CASE, caseData);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 受款人資料更正 - 遺屬年金受款人資料更正 SurvivorPayeeDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPayeeDataUpdateQueryCase(List<SurvivorPayeeDataUpdateCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE, caseData);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPayeeEvtDataUpdateQueryCase(List<SurvivorPayeeDataUpdateCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_EVT_DATA_UPDATE_QUERY_CASE, caseData);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPayeeDataUpdateCase> getSurvivorPayeeDataUpdateQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPayeeDataUpdateCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPayeeDataUpdateCase> getSurvivorPayeeEvtDataUpdateQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPayeeDataUpdateCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYEE_EVT_DATA_UPDATE_QUERY_CASE);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPayeeDataUpdateQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPayeeDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPayeeDataUpdateDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_Q1);
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_Q2);
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_ACCSEQNO_CONTROL);
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_RELATIONQ2);
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_CASE);
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_STUDTERM_LIST_CASE);
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_HANDICAPTERM_LIST_CASE);
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_COMPELDATA_LIST_CASE);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request List<Badapr>
     */
    public static void setSurvivorPayeeDataUpdateDetailCase(Baappbase caseData, Baappexpand expandData, Integer Q1, Integer Q2, String accSeqNoControl, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_Q1, Q1);
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_Q2, Q2);
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_ACCSEQNO_CONTROL, accSeqNoControl);
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_CASE, caseData);
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_BAAPPEXPAND_CASE, expandData);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request List<Badapr>
     */
    public static void setSurvivorPayeeDataUpdateDetailCase(Baappbase caseData, Baappexpand expandData, boolean displayIdnChkYm, boolean enableCoreceive, String accSeqNoControl, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        // 身分查核年月顯示與否
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_Q1, displayIdnChkYm);
        // 具名領取Enable與否
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_Q2, enableCoreceive);
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_QUERY_CASE_ACCSEQNO_CONTROL, accSeqNoControl);
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_CASE, caseData);
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_BAAPPEXPAND_CASE, expandData);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static Baappbase getSurvivorPayeeDataUpdateDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        return (Baappbase) session.getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_CASE);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param request
     * @return
     */
    public static void setSurvivorPayeeDataUpdateDetailBaappexpandCase(HttpServletRequest request, Baappexpand baappexpand) {
        log.debug("執行 CaseSessionHelper.setSurvivorPayeeDataUpdateDetailBaappexpandCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_BAAPPEXPAND_CASE, baappexpand);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static Baappexpand getSurvivorPayeeDataUpdateDetailBaappexpandCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPayeeDataUpdateDetailBaappexpandCase() ...");

        HttpSession session = request.getSession();
        return (Baappexpand) session.getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_DETAIL_BAAPPEXPAND_CASE);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正 - 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPayeeDataUpdateList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPayeeDataUpdateList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_LIST);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPayeeDataForBadaprCase(BadaprDataCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPayeeDataForBadaprCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_BADAPR_CASE, caseData);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPayeeDataListForBadaprCase(List<Map> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPayeeDataForBadaprCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_BADAPR_LIST_CASE, caseData);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPayeeDataForBadaprCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPayeeDataForBadaprCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_BADAPR_CASE);
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_BADAPR_LIST_CASE);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將 在學期間資料 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPayeeDataUpdateStudtermList(List<Bastudterm> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPayeeDataUpdateStudtermList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_STUDTERM_LIST_CASE, caseData);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將 重殘期間資料 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPayeeDataUpdateHandicaptermList(List<Bahandicapterm> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPayeeDataUpdateHandicaptermList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_HANDICAPTERM_LIST_CASE, caseData);
    }
    
    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 自 Session 中取得 在學期間資料 Case
     * 
     * @param request
     * @return
     */
    public static List<Bastudterm> getSurvivorPayeeDataUpdateStudtermList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPayeeDataUpdateStudtermList() ...");

        HttpSession session = request.getSession();
        return (List<Bastudterm>) session.getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_STUDTERM_LIST_CASE);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 自 Session 中取得 重殘期間資料 Case
     * 
     * @param request
     * @return
     */
    public static List<Bahandicapterm> getSurvivorPayeeDataUpdateHandicaptermList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPayeeDataUpdateHandicaptermList() ...");

        HttpSession session = request.getSession();
        return (List<Bahandicapterm>) session.getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_HANDICAPTERM_LIST_CASE);
    }
    
    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將在學期間資料 Case 從 Session 中移除
     * 
     * @param caseData
     * @param request
     */
    public static void removeSurvivorPayeeDataUpdateStudtermList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPayeeDataUpdateStudtermList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_STUDTERM_LIST_CASE);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將重殘期間資料 Case 從 Session 中移除
     * 
     * @param caseData
     * @param request
     */
    public static void removeSurvivorPayeeDataUpdateHandicaptermList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPayeeDataUpdateHandicaptermList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_HANDICAPTERM_LIST_CASE);
    }
    // ---------------------------------------------------------------------------------------

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將 在學期間資料 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPayeeDataUpdateCompelDataList(List<SurvivorPayeeDataUpdateCompelDataCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPayeeDataUpdateCompelDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_COMPELDATA_LIST_CASE, caseData);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 自 Session 中取得 在學期間資料 Case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPayeeDataUpdateCompelDataCase> getSurvivorPayeeDataUpdateCompelDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPayeeDataUpdateCompelDataList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPayeeDataUpdateCompelDataCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_COMPELDATA_LIST_CASE);
    }

    /**
     * 更正作業 - 遺屬年金受款人資料更正作業<br>
     * 將在學期間資料 Case 從 Session 中移除
     * 
     * @param caseData
     * @param request
     */
    public static void removeSurvivorPayeeDataUpdateCompelDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPayeeDataUpdateCompelDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYEE_DATA_UPDATE_COMPELDATA_LIST_CASE);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 眷屬資料更正 - DependantDataUpdate
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 -眷屬資料更正 - 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDependantDataUpdateList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDependantDataUpdateList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_LIST);
    }

    /**
     * 更正作業 - 眷屬資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static List<DependantDataUpdateCase> getDependantDataUpdateQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDependantDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<DependantDataUpdateCase>) session.getAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_QUERY_CASE);
    }

    /**
     * 更正作業 - 眷屬資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static List<DependantEvtDataUpdateCase> getDependantEvtDataUpdateQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDependantEvtDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        return (List<DependantEvtDataUpdateCase>) session.getAttribute(ConstantKey.DEPENDANT_EVT_DATA_UPDATE_QUERY_CASE);
    }

    /**
     * 更正作業 - 眷屬資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDependantDataUpdateQueryCase(List<DependantDataUpdateCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDeependantDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_QUERY_CASE, caseData);
    }

    /**
     * 更正作業 - 眷屬資料更正作業 - 事故者資料<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDependantEvtDataUpdateQueryCase(List<DependantEvtDataUpdateCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDeependantEvtDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DEPENDANT_EVT_DATA_UPDATE_QUERY_CASE, caseData);
    }

    /**
     * 更正作業 - 眷屬資料更正作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDependantDataUpdateQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDependantDataUpdateQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_QUERY_CASE);
    }

    /**
     * 更正作業 - 受款人資料更正作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDependantDataUpdateDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePayeeDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_DETAIL_CASE);
        session.removeAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_STUDTERM_LIST_CASE);
    }

    /**
     * 更正作業 - 眷屬資料更正作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */

    public static DependantDataUpdateCase getDependantDataUpdateDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDependantDataUpdateDetailCase() ...");

        HttpSession session = request.getSession();
        return (DependantDataUpdateCase) session.getAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_DETAIL_CASE);
    }

    /**
     * 更正作業 - 眷屬資料更正作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param request
     * @return
     */

    public static void setDependantDataUpdateDetailCase(DependantDataUpdateCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDependantDataUpdateDetailCase() ...");
        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_DETAIL_CASE, caseData);

    }

    /**
     * 更正作業 - 眷屬資料更正作業 - 眷屬編審註記<br>
     * 將 MasterDetail Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDependantQueryBenChkFileDataCase(List<PaymentQueryDetailDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDependantQueryBenChkFileDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DEPENDANT_QUERY_BEN_CHKFILE_DATA_CASE, caseList);
    }

    /**
     * 更正作業 - 眷屬資料更正作業 - 符合註記<br>
     * 將 MasterDetail Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDependantQueryMatchChkFileDataCase(List<PaymentQueryDetailDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDependantQueryMatchChkFileDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DEPENDANT_QUERY_MATCH_CHKFILE_DATA_CASE, caseList);
    }

    /**
     * 更正作業 - 眷屬資料更正作業<br>
     * 將 在學期間資料 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDependantDataUpdateStudtermList(List<Bastudterm> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDependantDataUpdateStudtermList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_STUDTERM_LIST_CASE, caseData);
    }

    /**
     * 更正作業 - 眷屬資料更正作業<br>
     * 自 Session 中取得 在學期間資料 Case
     * 
     * @param request
     * @return
     */
    public static List<Bastudterm> getDependantDataUpdateStudtermList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDependantDataUpdateStudtermList() ...");

        HttpSession session = request.getSession();
        return (List<Bastudterm>) session.getAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_STUDTERM_LIST_CASE);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 止付處理作業 - StopPaymentProcessQuery
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 止付處理作業<br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setStopPaymentProcessQueryCase(StopPaymentProcessCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setStopPaymentProcessQueryCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.STOP_PAYMENT_PROCESS_QUERY_CASE, caseData);
    }

    /**
     * 更正作業 - 止付處理作業<br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static StopPaymentProcessCase getStopPaymentProcessQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getStopPaymentProcessQueryCase() ...");

        HttpSession session = request.getSession();
        return (StopPaymentProcessCase) session.getAttribute(ConstantKey.STOP_PAYMENT_PROCESS_QUERY_CASE);
    }

    /**
     * 更正作業 - 止付處理作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeStopPaymentProcessQueryCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeStopPaymentProcessQueryCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.STOP_PAYMENT_PROCESS_QUERY_CASE);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 編審註記程度調整 - checkMarkLevelAdjust
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 編審註記程度調整 <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCheckMarkLevelAdjustCase(CheckMarkLevelAdjustCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCheckMarkLevelAdjustCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CHECK_MARK_LEVEL_ADJUST_CASE, caseData);
    }

    /**
     * 更正作業 - 編審註記程度調整 <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static CheckMarkLevelAdjustCase getCheckMarkLevelAdjustCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getCheckMarkLevelAdjustCase() ...");

        HttpSession session = request.getSession();
        return (CheckMarkLevelAdjustCase) session.getAttribute(ConstantKey.CHECK_MARK_LEVEL_ADJUST_CASE);
    }

    /**
     * 更正作業 - 編審註記程度調整 <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCheckMarkLevelAdjustCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCheckMarkLevelAdjustCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CHECK_MARK_LEVEL_ADJUST_CASE);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 (bamo0d160c.jsp bamo0d161c.jsp) <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledCheckMarkLevelAdjustCase(DisabledCheckMarkLevelAdjustCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledCheckMarkLevelAdjustCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_CHECK_MARK_LEVEL_ADJUST_CASE, caseData);
    }

    /**
     * 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 (bamo0d160c.jsp bamo0d161c.jsp) <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static DisabledCheckMarkLevelAdjustCase getDisabledCheckMarkLevelAdjustCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledCheckMarkLevelAdjustCase() ...");

        HttpSession session = request.getSession();
        return (DisabledCheckMarkLevelAdjustCase) session.getAttribute(ConstantKey.DISABLED_CHECK_MARK_LEVEL_ADJUST_CASE);
    }

    /**
     * 更正作業 - 編審註記程度調整 - 失能年金編審註記程度調整 (bamo0d160c.jsp bamo0d161c.jsp) <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledCheckMarkLevelAdjustCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledCheckMarkLevelAdjustCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_CHECK_MARK_LEVEL_ADJUST_CASE);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整 (bamo0d260c.jsp bamo0d261c.jsp) <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorCheckMarkLevelAdjustCase(SurvivorCheckMarkLevelAdjustCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorCheckMarkLevelAdjustCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_CHECK_MARK_LEVEL_ADJUST_CASE, caseData);
    }

    /**
     * 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整 (bamo0d260c.jsp bamo0d261c.jsp) <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static SurvivorCheckMarkLevelAdjustCase getSurvivorCheckMarkLevelAdjustCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorCheckMarkLevelAdjustCase() ...");

        HttpSession session = request.getSession();
        return (SurvivorCheckMarkLevelAdjustCase) session.getAttribute(ConstantKey.SURVIVOR_CHECK_MARK_LEVEL_ADJUST_CASE);
    }

    /**
     * 更正作業 - 編審註記程度調整 - 遺屬年金編審註記程度調整 (bamo0d260c.jsp bamo0d261c.jsp) <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorCheckMarkLevelAdjustCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorCheckMarkLevelAdjustCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_CHECK_MARK_LEVEL_ADJUST_CASE);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 案件資料更正 - 失能年金案件資料更正
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp) <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledApplicationDataUpdateCase(DisabledApplicationDataUpdateCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_CASE, caseData);
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp) <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static DisabledApplicationDataUpdateCase getDisabledApplicationDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        return (DisabledApplicationDataUpdateCase) session.getAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_CASE);
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp) <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledApplicationDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_CASE);
    }
    
    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 重新審核失能程度 (bamo0d131c.jsp bamo0d136c.jsp) <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledApplicationDataUpdateBareCheckCaseList(List<DisabledApplicationDataUpdateBareCheckCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledApplicationDataUpdateBareCheckCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_BARE_CHECK_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 重新審核失能程度 (bamo0d131c.jsp bamo0d136c.jsp) <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static List<DisabledApplicationDataUpdateBareCheckCase> getDisabledApplicationDataUpdateBareCheckCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledApplicationDataUpdateBareCheckCaseList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledApplicationDataUpdateBareCheckCase>) session.getAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_BARE_CHECK_CASE_LIST);
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 重新審核失能程度 (bamo0d131c.jsp bamo0d136c.jsp) <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledApplicationDataUpdateBareCheckCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledApplicationDataUpdateBareCheckCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_APPLICATION_DATA_UPDATE_BARE_CHECK_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------
    // 更正作業 - 案件資料更正 - 遺屬年金案件資料更正
    // ---------------------------------------------------------------------------------------
    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp) <br>
     * 將相關 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorApplicationDataUpdateCase(SurvivorApplicationDataUpdateCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_APPLICATION_DATA_UPDATE_CASE, caseData);
    }

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp) <br>
     * 自 Session 中取得 相關 Case
     * 
     * @param request
     * @return
     */
    public static SurvivorApplicationDataUpdateCase getSurvivorApplicationDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        return (SurvivorApplicationDataUpdateCase) session.getAttribute(ConstantKey.SURVIVOR_APPLICATION_DATA_UPDATE_CASE);
    }

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp) <br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorApplicationDataUpdateCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorApplicationDataUpdateCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_APPLICATION_DATA_UPDATE_CASE);
    }
    
    // ---------------------------------------------------------------------------------------
    // 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)
    // ---------------------------------------------------------------------------------------
    
    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_1)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCheckOldAgeDeathRepayDataCase(BaappbaseDataUpdateCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCheckOldAgeDeathRepayDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CHECK_OLDAGE_DEATH_REPAY_DATA_CASE, caseData);
    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_1)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param request
     * @return
     */
    public static BaappbaseDataUpdateCase getCheckOldAgeDeathRepayDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getCheckOldAgeDeathRepayDataCase() ...");

        HttpSession session = request.getSession();
        return (BaappbaseDataUpdateCase) session.getAttribute(ConstantKey.CHECK_OLDAGE_DEATH_REPAY_DATA_CASE);
    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_1)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param request
     */
    public static void removeCheckOldAgeDeathRepayDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCheckOldAgeDeathRepayDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CHECK_OLDAGE_DEATH_REPAY_DATA_CASE);
    }
    
    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_2)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setOldAgeDeathRepayDataCaseList(List<BaappbaseDataUpdateCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setOldAgeDeathRepayDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLDAGE_DEATH_REPAY_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_2)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @return
     */
    public static List<BaappbaseDataUpdateCase> getOldAgeDeathRepayDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getOldAgeDeathRepayDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<BaappbaseDataUpdateCase>) session.getAttribute(ConstantKey.OLDAGE_DEATH_REPAY_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_2)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param request
     */
    public static void removeOldAgeDeathRepayDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeOldAgeDeathRepayDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.OLDAGE_DEATH_REPAY_DATA_CASE_LIST);
    }
    
    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_3)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setHeirSeqNoExistDataCaseList(List<BaappbaseDataUpdateCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setHeirSeqNoExistDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.HEIR_SEQNO_EXIST_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_3)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param return
     */
    public static List<BaappbaseDataUpdateCase> getHeirSeqNoExistDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getHeirSeqNoExistDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<BaappbaseDataUpdateCase>) session.getAttribute(ConstantKey.HEIR_SEQNO_EXIST_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_3)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param request
     */
    public static void removeHeirSeqNoExistDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeHeirSeqNoExistDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.HEIR_SEQNO_EXIST_DATA_CASE_LIST);
    }
    
    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_4)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setRepayIssueAmtDataCaseList(List<BaappbaseDataUpdateCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setRepayIssueAmtDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REPAY_ISSUEAMT_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_4)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param return
     */
    public static List<BaappbaseDataUpdateCase> getRepayIssueAmtDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getRepayIssueAmtDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<BaappbaseDataUpdateCase>) session.getAttribute(ConstantKey.REPAY_ISSUEAMT_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 死亡改匯處理作業 - 老年年金受款人死亡改匯處理(BAMO0D090C)(QryData_4)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param request
     */
    public static void removeRepayIssueAmtDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeRepayIssueAmtDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.REPAY_ISSUEAMT_DATA_CASE_LIST);
    }
    
    // ---------------------------------------------------------------------------------------
    // 更正作業 - 應收收回處理作業 - 老年年金應收收回處理作業 (BAMO0D100C)
    // ---------------------------------------------------------------------------------------
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 收回狀況資料(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setOldAgePaymentReceiveDataCase(OldAgePaymentReceiveDataCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setOldAgePaymentReceiveDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLDAGE_PAYMENT_RECEIVE_DATA_CASE, caseData);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 收回狀況資料(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static OldAgePaymentReceiveDataCase getOldAgePaymentReceiveDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getOldAgePaymentReceiveDataCase() ...");

        HttpSession session = request.getSession();
        return (OldAgePaymentReceiveDataCase) session.getAttribute(ConstantKey.OLDAGE_PAYMENT_RECEIVE_DATA_CASE);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 收回狀況資料(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeOldAgePaymentReceiveDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeOldAgePaymentReceiveDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.OLDAGE_PAYMENT_RECEIVE_DATA_CASE);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 應收資料List(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setAccountsReceivableDataCaseList(List<AccountsReceivableDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setAccountsReceivableDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.ACCOUNTS_RECEIVABLE_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 應收資料List(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<AccountsReceivableDataCase> getAccountsReceivableDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getAccountsReceivableDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<AccountsReceivableDataCase>) session.getAttribute(ConstantKey.ACCOUNTS_RECEIVABLE_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 應收資料List(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeAccountsReceivableDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAccountsReceivableDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.ACCOUNTS_RECEIVABLE_DATA_CASE_LIST);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理退現資料(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCashReceiveDataCaseList(List<CashReceiveDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCashReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CASH_RECEIVE_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 退現資料(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<CashReceiveDataCase> getCashReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getCashReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<CashReceiveDataCase>) session.getAttribute(ConstantKey.CASH_RECEIVE_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 退現資料(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeCashReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCashReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CASH_RECEIVE_DATA_CASE_LIST);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 退匯資料(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setRemittanceReceiveDataCaseList(List<RemittanceReceiveDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setRemittanceReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.REMITTANCE_RECEIVE_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 退匯資料(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<RemittanceReceiveDataCase> getRemittanceReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getRemittanceReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<RemittanceReceiveDataCase>) session.getAttribute(ConstantKey.REMITTANCE_RECEIVE_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 老年年金應收收回處理 退匯資料(BAMO0D100C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeRemittanceReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeRemittanceReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.REMITTANCE_RECEIVE_DATA_CASE_LIST);
    }
    
    
    // ---------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------
    // 更正作業 - 應收收回處理作業 - 失能年金應收收回處理作業 (BAMO0D200C)
    // ---------------------------------------------------------------------------------------
    
    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 收回狀況資料(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentReceiveDataCase(DisabledPaymentReceiveDataCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.seDisabledPaymentReceiveDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_RECEIVE_DATA_CASE, caseData);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 收回狀況資料(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static DisabledPaymentReceiveDataCase getDisabledPaymentReceiveDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentReceiveDataCase() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentReceiveDataCase) session.getAttribute(ConstantKey.DISABLED_PAYMENT_RECEIVE_DATA_CASE);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 收回狀況資料(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeDisabledPaymentReceiveDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentReceiveDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_RECEIVE_DATA_CASE);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 應收資料List(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledAccountsReceivableDataCaseList(List<DisabledAccountsReceivableDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledAccountsReceivableDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_ACCOUNTS_RECEIVABLE_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 應收資料List(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<DisabledAccountsReceivableDataCase> getDisabledAccountsReceivableDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledAccountsReceivableDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledAccountsReceivableDataCase>) session.getAttribute(ConstantKey.DISABLED_ACCOUNTS_RECEIVABLE_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 應收資料List(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeDisabledAccountsReceivableDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledAccountsReceivableDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_ACCOUNTS_RECEIVABLE_DATA_CASE_LIST);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理退現資料(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledCashReceiveDataCaseList(List<DisabledCashReceiveDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledCashReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_CASH_RECEIVE_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理退現資料(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<DisabledCashReceiveDataCase> getDisabledCashReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledCashReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledCashReceiveDataCase>) session.getAttribute(ConstantKey.DISABLED_CASH_RECEIVE_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理退現資料(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeDisabledCashReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledCashReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_CASH_RECEIVE_DATA_CASE_LIST);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 退匯資料(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledRemittanceReceiveDataCaseList(List<DisabledRemittanceReceiveDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledRemittanceReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_REMITTANCE_RECEIVE_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 退匯資料(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<DisabledRemittanceReceiveDataCase> getDisabledRemittanceReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledRemittanceReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledRemittanceReceiveDataCase>) session.getAttribute(ConstantKey.DISABLED_REMITTANCE_RECEIVE_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 失能年金應收收回處理 退匯資料(BAMO0D200C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeDisabledRemittanceReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledRemittanceReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_REMITTANCE_RECEIVE_DATA_CASE_LIST);
    }
    
    
    // ---------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------
    // 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理作業 (BAMO0D300C)
    // ---------------------------------------------------------------------------------------
    
    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理 收回狀況資料(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentReceiveDataCase(SurvivorPaymentReceiveDataCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentReceiveDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_RECEIVE_DATA_CASE, caseData);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理 收回狀況資料(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static SurvivorPaymentReceiveDataCase getSurvivorPaymentReceiveDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentReceiveDataCase() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentReceiveDataCase) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_RECEIVE_DATA_CASE);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理 收回狀況資料(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeSurvivorPaymentReceiveDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentReceiveDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_RECEIVE_DATA_CASE);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理 應收資料List(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorAccountsReceivableDataCaseList(List<SurvivorAccountsReceivableDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorAccountsReceivableDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_ACCOUNTS_RECEIVABLE_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理 應收資料List(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<SurvivorAccountsReceivableDataCase> getSurvivorAccountsReceivableDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getAccountsReceivableDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorAccountsReceivableDataCase>) session.getAttribute(ConstantKey.SURVIVOR_ACCOUNTS_RECEIVABLE_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理 應收資料List(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeSurvivorAccountsReceivableDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorAccountsReceivableDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_ACCOUNTS_RECEIVABLE_DATA_CASE_LIST);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理退現資料(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorCashReceiveDataCaseList(List<SurvivorCashReceiveDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorCashReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_CASH_RECEIVE_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理退現資料(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<SurvivorCashReceiveDataCase> getSurvivorCashReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorCashReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorCashReceiveDataCase>) session.getAttribute(ConstantKey.SURVIVOR_CASH_RECEIVE_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理退現資料(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeSurvivorCashReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorCashReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_CASH_RECEIVE_DATA_CASE_LIST);
    }
    
    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理 退匯資料(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorRemittanceReceiveDataCaseList(List<SurvivorRemittanceReceiveDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorRemittanceReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_REMITTANCE_RECEIVE_DATA_CASE_LIST, caseList);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理 退匯資料(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<SurvivorRemittanceReceiveDataCase> getSurvivorRemittanceReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorRemittanceReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorRemittanceReceiveDataCase>) session.getAttribute(ConstantKey.SURVIVOR_REMITTANCE_RECEIVE_DATA_CASE_LIST);
    }

    /**
     * 更正作業 - 應收收回處理作業 - 遺屬年金應收收回處理 退匯資料(BAMO0D300C)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void removeSurvivorRemittanceReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorRemittanceReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_REMITTANCE_RECEIVE_DATA_CASE_LIST);
    }
    
    
    // ---------------------------------------------------------------------------------------
    
    
    /**
     * 更正作業 - 眷屬資料更正作業<br>
     * 自 Session 中取得 不合格年月資料 Case
     * 
     * @param request
     * @return
     */
    public static List<DependantDataUpdateCompelDataCase> getDependantDataUpdateCompelDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDependantDataUpdateCompelDataList() ...");

        HttpSession session = request.getSession();
        return (List<DependantDataUpdateCompelDataCase>) session.getAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_COMPELDATA_LIST_CASE);
    }
    
    /**
     * 更正作業 - 眷屬資料更正作業<br>
     * 將 不合格年月資料 Case 加入 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDependantDataUpdateCompelDataList(List<DependantDataUpdateCompelDataCase> caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDependantDataUpdateCompelDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DEPENDANT_DATA_UPDATE_COMPELDATA_LIST_CASE, caseData);
    }
    
    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 (BALP0D690L)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setBatchPaymentReceiveDataCaseList(List<BatchPaymentReceiveDataCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setBatchPaymentReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.BATCH_PAYMENT_RECEIVE_DATA_CASE_LIST, caseList);
    }
    
    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 收回狀況資料(BALP0D690L)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setBatchPaymentReceiveDataCase(BatchPaymentReceiveDataCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setBatchPaymentReceiveDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLDAGE_PAYMENT_RECEIVE_DATA_CASE, caseData);
    }
    
    /**
     * 列印作業 - 應收收回相關報表 - 整批收回核定清單 收回狀況資料(BALP0D690L)<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static List<BatchPaymentReceiveDataCase> getBatchPaymentReceiveDataCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getBatchPaymentReceiveDataCaseList() ...");

        HttpSession session = request.getSession();
        return (List<BatchPaymentReceiveDataCase>) session.getAttribute(ConstantKey.BATCH_PAYMENT_RECEIVE_DATA_CASE_LIST);
    }

    /**
     * 更止作業 - 結案狀態變更作業<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCloseStatusAlterationCase(List<CloseStatusAlterationCase> caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCloseStatusAlterationCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CLOSE_STATUS_ALTERATION_CASE, caseObj);
    }

    /**
     * 更止作業 - 結案狀態變更作業<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCloseStatusAlterationCasePayeeList(List<CloseStatusAlterationCasePayeeList> caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCloseStatusAlterationCasePayeeList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.CLOSE_STATUS_ALTERATION_CASE_PAYEE_LIST, caseObj);
    }

    /**
     * 更正作業 - 結案狀態變更作業<br>
     * 將相關 Case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCloseStatusAlterationCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCloseStatusAlterationCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.CLOSE_STATUS_ALTERATION_CASE);
        session.removeAttribute(ConstantKey.CLOSE_STATUS_ALTERATION_CASE_PAYEE_LIST);
    }
}
