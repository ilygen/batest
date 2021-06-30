package tw.gov.bli.ba.decision.helper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.decision.cases.DisabledPaymentDecisionCase;
import tw.gov.bli.ba.decision.cases.DisabledPaymentDecisionExtCase;
import tw.gov.bli.ba.decision.cases.PaymentDecisionCase;
import tw.gov.bli.ba.decision.cases.PaymentDecisionExtCase;
import tw.gov.bli.ba.decision.cases.SurvivorPaymentDecisionCase;
import tw.gov.bli.ba.decision.cases.SurvivorPaymentDecisionExtCase;

/**
 * Case Helper for 決行作業
 * 
 * @author Rickychi
 */
public class CaseSessionHelper {
    private static Log log = LogFactory.getLog(CaseSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 決行作業 - 給付決行作業 - PaymentDecision
    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業 - 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentDecisionList(List<PaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_LIST, list);
    }

    /**
     * 決行作業 - 給付決行作業 - 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionCase> getPaymentDecisionList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_LIST);
    }

    /**
     * 決行作業 - 給付決行作業- 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業 - 受理編號 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentDecisionCase(PaymentDecisionCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_CASE, obj);
    }

    /**
     * 決行作業 - 給付決行作業 - 受理編號 查詢結果<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentDecisionCase getPaymentDecisionCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionCase() ...");

        HttpSession session = request.getSession();
        return (PaymentDecisionCase) session.getAttribute(ConstantKey.PAYMENT_DECISION_CASE);
    }

    /**
     * 決行作業 - 給付決行作業 - 受理編號 查詢結果<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_CASE);
    }

    /**
     * 決行作業 - 給付決行作業 - 查詢結果 TITLE<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentDecisionCaseForTitle(PaymentDecisionCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_CASE_FOR_TITLE, obj);
    }

    /**
     * 決行作業 - 給付決行作業 - 查詢結果 TITLE<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentDecisionCase getPaymentDecisionCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionCaseForTitle() ...");

        HttpSession session = request.getSession();
        return (PaymentDecisionCase) session.getAttribute(ConstantKey.PAYMENT_DECISION_CASE_FOR_TITLE);
    }

    /**
     * 決行作業 - 給付決行作業 - 查詢結果 TITLE<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_CASE_FOR_TITLE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentDecisionChkList(List<PaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_CHK_LIST, list);
    }

    /**
     * 決行作業 - 給付決行作業 - 事故者編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionExtCase> getPaymentDecisionChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionChkList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionExtCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_CHK_LIST);
    }

    /**
     * 決行作業 - 給付決行作業- 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------
    
    /**
     * 決行作業 - 給付決行作業 - 受款人編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentDecisionBenChkList(List<PaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionBenChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_BEN_CHK_LIST, list);
    }

    /**
     * 決行作業 - 給付決行作業 - 受款人編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionCase> getPaymentDecisionBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionBenChkList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_BEN_CHK_LIST);
    }

    /**
     * 決行作業 - 給付決行作業- 受款人編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionBenChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_BEN_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 受款人 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPaymentDecisionBenNameList(List<PaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionBenNameList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_BENNAME_LIST, list);
    }

    /**
     * 決行作業 - 給付決行作業 - 受款人 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionExtCase> getPaymentDecisionBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionBenNameList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionExtCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_BENNAME_LIST);
    }

    /**
     * 決行作業 - 給付決行作業- 受款人 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionBenNameList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_BENNAME_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 欠費期間資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSenimaintDataList(List<PaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSenimaintDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_SENIMAINT_DATA_LIST, list);
    }

    /**
     * 決行作業 - 給付決行作業 - 欠費期間資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionExtCase> getSenimaintDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSenimaintDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionExtCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_SENIMAINT_DATA_LIST);
    }

    /**
     * 決行作業 - 給付決行作業- 欠費期間資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSenimaintDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSenimaintDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_SENIMAINT_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 承保異動資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setCiptDataList(List<PaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCiptDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_CIPT_DATA_LIST, list);
    }

    /**
     * 決行作業 - 給付決行作業 - 承保異動資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionExtCase> getCiptDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getCiptDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionExtCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_CIPT_DATA_LIST);
    }

    /**
     * 決行作業 - 給付決行作業- 承保異動資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCiptDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCiptDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_CIPT_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setPayDataList(List<PaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPayDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_PAY_DATA_LIST, list);
    }

    /**
     * 決行作業 - 給付決行作業 - 編審給付資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionExtCase> getPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPayDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionExtCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_PAY_DATA_LIST);
    }

    /**
     * 決行作業 - 給付決行作業- 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removePayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePayDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_PAY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 一次給付資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setOncePayDataCase(PaymentDecisionExtCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setOncePayDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_ONCEPAY_DATA_CASE, caseObj);
    }

    /**
     * 決行作業 - 給付決行作業 - 一次給付資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static PaymentDecisionExtCase getOncePayDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getOncePayDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentDecisionExtCase) session.getAttribute(ConstantKey.PAYMENT_DECISION_ONCEPAY_DATA_CASE);
    }

    /**
     * 決行作業 - 給付決行作業- 一次給付資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOncePayDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeOncePayDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_ONCEPAY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 一次給付更正資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setOncePayModifyDataList(List<PaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setOncePayModifyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_ONCEPAYMODIFY_DATA_LIST, list);
    }

    /**
     * 決行作業 - 給付決行作業 - 一次給付更正資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionExtCase> getOncePayModifyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getOncePayModifyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionExtCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_ONCEPAYMODIFY_DATA_LIST);
    }

    /**
     * 決行作業 - 給付決行作業- 一次給付更正資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOncePayModifyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeOncePayModifyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_ONCEPAYMODIFY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 受款人核定資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setBenIssueDataList(List<PaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setBenIssueDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_BENISSUE_DATA_LIST, list);
    }

    /**
     * 決行作業 - 給付決行作業 - 受款人核定資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionExtCase> getBenIssueDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getBenIssueDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionExtCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_BENISSUE_DATA_LIST);
    }

    /**
     * 決行作業 - 給付決行作業- 受款人核定資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeBenIssueDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeBenIssueDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_BENISSUE_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 受款人資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setBeneficiaryDataList(List<PaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_BENEFICIARY_DATA_LIST, list);
    }

    /**
     * 決行作業 - 給付決行作業 - 受款人資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionCase> getBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_BENEFICIARY_DATA_LIST);
    }

    /**
     * 決行作業 - 給付決行作業- 受款人資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_BENEFICIARY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業 - 核定通知書資料 查詢結果<br>
     * 將 查詢結果 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setNotifyData(PaymentDecisionExtCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setNotifyData() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_NOTIFY_DATA_CASE, caseObj);
    }

    /**
     * 決行作業 - 給付決行作業 - 核定通知書資料 查詢結果<br>
     * 自 Session 中取得 查詢結果
     * 
     * @param request
     * @return
     */
    public static PaymentDecisionExtCase getNotifyData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getNotifyData() ...");

        HttpSession session = request.getSession();
        return (PaymentDecisionExtCase) session.getAttribute(ConstantKey.PAYMENT_DECISION_NOTIFY_DATA_CASE);
    }

    /**
     * 決行作業 - 給付決行作業- 核定通知書資料 查詢結果<br>
     * 將 查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeNotifyData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeNotifyData() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_NOTIFY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setOncePayDetailDataCase(PaymentDecisionExtCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setOncePayDetailDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_ONCEPAY_DETAIL_DATA_CASE, caseObj);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static PaymentDecisionExtCase getOncePayDetailDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getBenIssueDataList() ...");

        HttpSession session = request.getSession();
        return (PaymentDecisionExtCase) session.getAttribute(ConstantKey.PAYMENT_DECISION_ONCEPAY_DETAIL_DATA_CASE);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 一次給付資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOncePayDetailDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeBenIssueDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_ONCEPAY_DETAIL_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setAnnuityPayDataList(List<PaymentDecisionExtCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setAnnuityPayDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_ANNUITYPAY_DATA_CASE_LIST, caseList);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionExtCase> getAnnuityPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getAnnuityPayDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionExtCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_ANNUITYPAY_DATA_CASE_LIST);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 年金給付資料 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAnnuityPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAnnuityPayDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_ANNUITYPAY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setCriPayApplyDataList(List<PaymentDecisionExtCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setCriPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_CRIPAY_APPLY_DATA_CASE_LIST, caseList);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionExtCase> getCriPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getCriPayApplyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionExtCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_CRIPAY_APPLY_DATA_CASE_LIST);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請失能給付紀錄 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeCriPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeCriPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_CRIPAY_APPLY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDiePayApplyDataCase(PaymentDecisionExtCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDiePayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_DIEPAY_APPLY_DATA_CASE, caseObj);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static PaymentDecisionExtCase getDiePayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDiePayApplyDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentDecisionExtCase) session.getAttribute(ConstantKey.PAYMENT_DECISION_DIEPAY_APPLY_DATA_CASE);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請死亡給付紀錄 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDiePayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDiePayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_DIEPAY_APPLY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setInjPayApplyDataList(List<PaymentDecisionExtCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setInjPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_INJPAY_APPLY_DATA_CASE_LIST, caseList);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionExtCase> getInjPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getInjPayApplyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionExtCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_INJPAY_APPLY_DATA_CASE_LIST);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請傷病給付紀錄 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeInjPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeInjPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_INJPAY_APPLY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄資料 查詢結果<br>
     * 將 caseList 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setUnEmpPayApplyDataList(List<PaymentDecisionExtCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setUnEmpPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_UNEMPPAY_APPLY_DATA_CASE_LIST, caseList);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄資料 查詢結果<br>
     * 自 Session 中取得 caseList
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionExtCase> getUnEmpPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getUnEmpPayApplyDataList() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionExtCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_UNEMPPAY_APPLY_DATA_CASE_LIST);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請失業給付紀錄資料 查詢結果<br>
     * 將 caseList 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUnEmpPayApplyDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeUnEmpPayApplyDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_UNEMPPAY_APPLY_DATA_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄資料 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setNpPayApplyDataCase(PaymentDecisionExtCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setNpPayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_NPPAY_APPLY_DATA_CASE, caseObj);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄資料 查詢結果<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentDecisionExtCase getNpPayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getNpPayApplyDataCase() ...");

        HttpSession session = request.getSession();
        return (PaymentDecisionExtCase) session.getAttribute(ConstantKey.PAYMENT_DECISION_NPPAY_APPLY_DATA_CASE);
    }

    /**
     * 決行作業 - 給付決行作業 - 請領同類／他類／另案扣減 - 申請國保給付紀錄資料 查詢結果<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeNpPayApplyDataCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeNpPayApplyDataCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_NPPAY_APPLY_DATA_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業 - 交查函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentDecisionLetterTypeMk1List(List<PaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_1_LIST, caseList);
    }

    /**
     * 決行作業 - 給付決行作業 - 交查函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionCase> getPaymentDecisionLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_1_LIST);
    }

    /**
     * 決行作業 - 給付決行作業 - 交查函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_1_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業 - 不給付函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentDecisionLetterTypeMk2List(List<PaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_2_LIST, caseList);
    }

    /**
     * 決行作業 - 給付決行作業 - 不給付函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionCase> getPaymentDecisionLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_2_LIST);
    }

    /**
     * 決行作業 - 給付決行作業 - 不給付函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_2_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業 - 補件通知函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentDecisionLetterTypeMk3List(List<PaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_3_LIST, caseList);
    }

    /**
     * 決行作業 - 給付決行作業 - 補件通知函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionCase> getPaymentDecisionLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_3_LIST);
    }

    /**
     * 決行作業 - 給付決行作業 - 補件通知函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_3_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業 - 照會函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentDecisionLetterTypeMk4List(List<PaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_4_LIST, caseList);
    }

    /**
     * 決行作業 - 給付決行作業 - 照會函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionCase> getPaymentDecisionLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_4_LIST);
    }

    /**
     * 決行作業 - 給付決行作業 - 照會函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_4_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業 - 其他簽函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentDecisionLetterTypeMk5List(List<PaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_5_LIST, caseList);
    }

    /**
     * 決行作業 - 給付決行作業 - 其他簽函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<PaymentDecisionCase> getPaymentDecisionLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        return (List<PaymentDecisionCase>) session.getAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_5_LIST);
    }

    /**
     * 決行作業 - 給付決行作業 - 其他簽函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_5_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setPaymentDecisionLetterTypeMk6List(PaymentDecisionCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setPaymentDecisionLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_6_LIST, caseObj);
    }

    /**
     * 決行作業 - 給付決行作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static PaymentDecisionCase getPaymentDecisionLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPaymentDecisionLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        return (PaymentDecisionCase) session.getAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_6_LIST);
    }

    /**
     * 決行作業 - 給付決行作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removePaymentDecisionLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removePaymentDecisionLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.PAYMENT_DECISION_LETTER_TYPE_6_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 給付決行作業<br>
     * 將 所有查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllPaymentDecisionCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllcase() ...");

        HttpSession session = request.getSession();
        removeBeneficiaryDataList(request);
        removeBenIssueDataList(request);
        removeCiptDataList(request);
        removeOncePayDataCase(request);
        removeOncePayModifyDataList(request);
        removePayDataList(request);
        removePaymentDecisionBenNameList(request);
        removePaymentDecisionCase(request);
        removePaymentDecisionCaseForTitle(request);
        removePaymentDecisionChkList(request);
        removePaymentDecisionBenChkList(request);
        removePaymentDecisionList(request);
        removeSenimaintDataList(request);
        removeOncePayDetailDataCase(request);
        removeCriPayApplyDataList(request);
        removeDiePayApplyDataCase(request);
        removeInjPayApplyDataList(request);
        removeUnEmpPayApplyDataList(request);
        removeAnnuityPayDataList(request);
        removeNpPayApplyDataCase(request);
        removeNotifyData(request);
        removePaymentDecisionLetterTypeMk1List(request);
        removePaymentDecisionLetterTypeMk2List(request);
        removePaymentDecisionLetterTypeMk3List(request);
        removePaymentDecisionLetterTypeMk4List(request);
        removePaymentDecisionLetterTypeMk5List(request);
        removePaymentDecisionLetterTypeMk6List(request);
    }

    // ---------------------------------------------------------------------------------------
    // 決行作業 - 失能年金給付決行作業 - DisabledPaymentDecision
    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 失能年金給付決行作業 - 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentDecisionList(List<DisabledPaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LIST, list);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionCase> getDisabledPaymentDecisionList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPDisabledaymentDecisionList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業- 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 失能年金給付決行作業 - 受理編號 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentDecisionCase(DisabledPaymentDecisionCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_CASE, obj);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 受理編號 查詢結果<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static DisabledPaymentDecisionCase getDisabledPaymentDecisionCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionCase() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentDecisionCase) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_CASE);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 受理編號 查詢結果<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 失能年金給付決行作業 - 查詢結果 TITLE<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentDecisionCaseForTitle(DisabledPaymentDecisionCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_CASE_FOR_TITLE, obj);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 查詢結果 TITLE<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static DisabledPaymentDecisionCase getDisabledPaymentDecisionCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionCaseForTitle() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentDecisionCase) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_CASE_FOR_TITLE);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 查詢結果 TITLE<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_CASE_FOR_TITLE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 失能年金給付決行作業 - 職災相關資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentDecisionOccAccData(DisabledPaymentDecisionCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionOccAccData() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_OCCACC_DATA, obj);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 職災相關資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static DisabledPaymentDecisionCase getDisabledPaymentDecisionOccAccData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionOccAccData() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentDecisionCase) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_OCCACC_DATA);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 職災相關資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionOccAccData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionOccAccData() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_OCCACC_DATA);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 失能年金給付決行作業 - 失能相關資料<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentDecisionDisabledData(DisabledPaymentDecisionCase list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionDisabledData() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_DISABLED_DATA, list);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 失能相關資料<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static DisabledPaymentDecisionCase getDisabledPaymentDecisionDisabledData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionDisabledData() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentDecisionCase) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_DISABLED_DATA);
    }

    /**
     * 決行作業 - 失能年金給付決行作業- 失能相關資料<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionDisabledData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionDisabledData() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_DISABLED_DATA);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 失能年金給付決行作業 - 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPayDataList(List<DisabledPaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPayDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_PAY_DATA_LIST, list);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 編審給付資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionExtCase> getDisabledPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPayDataList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionExtCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_PAY_DATA_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業- 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPayDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_PAY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 失能年金給付決行作業 - 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentDecisionEvtChkList(List<DisabledPaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionEvtChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_EVT_CHK_LIST, list);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 事故者編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionExtCase> getDisabledPaymentDecisionEvtChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionEvtChkList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionExtCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_EVT_CHK_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業- 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionEvtChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionEvtChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_EVT_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 失能年金給付決行作業 - 眷屬編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentDecisionBenChkList(List<DisabledPaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionBenChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_BEN_CHK_LIST, list);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 眷屬編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionCase> getDisabledPaymentDecisionBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionBenChkList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_BEN_CHK_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業- 眷屬編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionBenChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_BEN_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 失能年金給付決行作業 - 符合註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentDecisionMatchChkList(List<DisabledPaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionMatchChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_MATCH_CHK_LIST, list);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 符合註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionCase> getDisabledPaymentDecisionMatchChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionMatchChkList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_MATCH_CHK_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業- 符合註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionMatchChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionMatchChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_MATCH_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 失能年金給付決行作業 - 受款人資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledBeneficiaryDataList(List<DisabledPaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_BENEFICIARY_DATA_LIST, list);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 受款人資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionCase> getDisabledBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_BENEFICIARY_DATA_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業- 受款人資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_BENEFICIARY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 失能年金給付決行作業 - 受款人 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledPaymentDecisionBenNameList(List<DisabledPaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionBenNameList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_BENNAME_LIST, list);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 受款人 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionExtCase> getDisabledPaymentDecisionBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionBenNameList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionExtCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_BENNAME_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業- 受款人 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionBenNameList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_BENNAME_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 失能年金給付決行作業 - 交查函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentDecisionLetterTypeMk1List(List<DisabledPaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_1_LIST, caseList);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 交查函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionCase> getDisabledPaymentDecisionLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_1_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 交查函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_1_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 失能年金給付決行作業 - 不給付函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentDecisionLetterTypeMk2List(List<DisabledPaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_2_LIST, caseList);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 不給付函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionCase> getDisabledPaymentDecisionLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_2_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 不給付函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_2_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 失能年金給付決行作業 - 補件通知函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentDecisionLetterTypeMk3List(List<DisabledPaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_3_LIST, caseList);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 補件通知函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionCase> getDisabledPaymentDecisionLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_3_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 補件通知函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_3_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 失能年金給付決行作業 - 照會函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentDecisionLetterTypeMk4List(List<DisabledPaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_4_LIST, caseList);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 照會函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionCase> getDisabledPaymentDecisionLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_4_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 照會函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_4_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 失能年金給付決行作業 - 其他簽函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentDecisionLetterTypeMk5List(List<DisabledPaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_5_LIST, caseList);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 其他簽函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<DisabledPaymentDecisionCase> getDisabledPaymentDecisionLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        return (List<DisabledPaymentDecisionCase>) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_5_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 其他簽函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_5_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 失能年金給付決行作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setDisabledPaymentDecisionLetterTypeMk6List(DisabledPaymentDecisionCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledPaymentDecisionLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_6_LIST, caseObj);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static DisabledPaymentDecisionCase getDisabledPaymentDecisionLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPaymentDecisionLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        return (DisabledPaymentDecisionCase) session.getAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_6_LIST);
    }

    /**
     * 決行作業 - 失能年金給付決行作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledPaymentDecisionLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledPaymentDecisionLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_PAYMENT_DECISION_LETTER_TYPE_6_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 失能年金給付決行作業<br>
     * 將 所有查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllDisabledPaymentDecisionCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllcase() ...");

        HttpSession session = request.getSession();
        removeDisabledPaymentDecisionList(request);
        removeDisabledPaymentDecisionCase(request);
        removeDisabledPaymentDecisionCaseForTitle(request);
        removeDisabledPaymentDecisionOccAccData(request);
        removeDisabledPaymentDecisionDisabledData(request);
        removeDisabledPayDataList(request);
        removeDisabledPaymentDecisionEvtChkList(request);
        removeDisabledPaymentDecisionBenChkList(request);
        removeDisabledPaymentDecisionMatchChkList(request);
        removeDisabledBeneficiaryDataList(request);
        removeDisabledPaymentDecisionBenNameList(request);
        removeDisabledPaymentDecisionLetterTypeMk1List(request);
        removeDisabledPaymentDecisionLetterTypeMk2List(request);
        removeDisabledPaymentDecisionLetterTypeMk3List(request);
        removeDisabledPaymentDecisionLetterTypeMk4List(request);
        removeDisabledPaymentDecisionLetterTypeMk5List(request);
        removeDisabledPaymentDecisionLetterTypeMk6List(request);
    }

    // ---------------------------------------------------------------------------------------
    // 決行作業 - 遺屬年金給付決行作業 - SurvivorPaymentDecision
    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 遺屬年金給付決行作業 - 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentDecisionList(List<SurvivorPaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LIST, list);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionCase> getSurvivorPaymentDecisionList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getPDisabledaymentDecisionList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業- 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 遺屬年金給付決行作業 - 受理編號 查詢結果<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentDecisionCase(SurvivorPaymentDecisionCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_CASE, obj);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 受理編號 查詢結果<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static SurvivorPaymentDecisionCase getSurvivorPaymentDecisionCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionCase() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentDecisionCase) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_CASE);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 受理編號 查詢結果<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_CASE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 查詢結果 TITLE<br>
     * 將 case 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentDecisionCaseForTitle(SurvivorPaymentDecisionCase obj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_CASE_FOR_TITLE, obj);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 查詢結果 TITLE<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static SurvivorPaymentDecisionCase getSurvivorPaymentDecisionCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionCaseForTitle() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentDecisionCase) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_CASE_FOR_TITLE);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 查詢結果 TITLE<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionCaseForTitle(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionCaseForTitle() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_CASE_FOR_TITLE);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 失能相關資料<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentDecisionDisabledData(SurvivorPaymentDecisionCase list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionDisabledData() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_DISABLED_DATA, list);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 失能相關資料<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static SurvivorPaymentDecisionCase getSurvivorPaymentDecisionDisabledData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionDisabledData() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentDecisionCase) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_DISABLED_DATA);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業- 失能相關資料<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionDisabledData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionDisabledData() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_DISABLED_DATA);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPayDataList(List<SurvivorPaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPayDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_PAY_DATA_LIST, list);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 編審給付資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionExtCase> getSurvivorPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledPayDataList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionExtCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_PAY_DATA_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業- 編審給付資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPayDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPayDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_PAY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentDecisionEvtChkList(List<SurvivorPaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionEvtChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_EVT_CHK_LIST, list);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 事故者編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionExtCase> getSurvivorPaymentDecisionEvtChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionEvtChkList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionExtCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_EVT_CHK_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業- 事故者編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionEvtChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionEvtChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_EVT_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 眷屬編審註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentDecisionBenChkList(List<SurvivorPaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionBenChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BEN_CHK_LIST, list);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 眷屬編審註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionCase> getSurvivorPaymentDecisionBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionBenChkList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BEN_CHK_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業- 眷屬編審註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionBenChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionBenChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BEN_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 符合註記 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentDecisionMatchChkList(List<SurvivorPaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionMatchChkList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_MATCH_CHK_LIST, list);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 符合註記 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionCase> getSurvivorPaymentDecisionMatchChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionMatchChkList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_MATCH_CHK_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業- 符合註記 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionMatchChkList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionMatchChkList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_MATCH_CHK_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 受款人資料 查詢結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorBeneficiaryDataList(List<SurvivorPaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BENEFICIARY_DATA_LIST, list);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 受款人資料 查詢結果<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionCase> getSurvivorBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BENEFICIARY_DATA_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業- 受款人資料 查詢結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorBeneficiaryDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorBeneficiaryDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BENEFICIARY_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付決行作業 - 受款人核定資料 原始查詢結果List<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorOrigBenIssuDataList(List<SurvivorPaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorOrigBenIssuDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_ORIG_BEN_ISSU_DATA_LIST, list);
    }

    /**
     * 審核作業 - 遺屬年金給付決行作業 - 受款人核定資料 原始查詢結果List<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionExtCase> getSurvivorOrigBenIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorOrigBenIssuDataList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionExtCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_ORIG_BEN_ISSU_DATA_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付決行作業- 受款人核定資料 原始查詢結果List<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorOrigBenIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorOrigBenIssuDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_ORIG_BEN_ISSU_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付決行作業 - 受款人核定資料 查詢結果List<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorBenIssuDataList(List<SurvivorPaymentDecisionCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorBenIssuDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BEN_ISSU_DATA_LIST, list);
    }

    /**
     * 審核作業 - 遺屬年金給付決行作業 - 受款人核定資料 查詢結果List<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionCase> getSurvivorBenIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorBenIssuDataList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BEN_ISSU_DATA_LIST);
    }

    /**
     * 審核作業 - 遺屬年金給付決行作業- 受款人核定資料 查詢結果List<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorBenIssuDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorBenIssuDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BEN_ISSU_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 審核作業 - 遺屬年金給付決行作業 - 受款人核定資料 查詢結果Map<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorBenIssuDataMap(Map<BigDecimal, SurvivorPaymentDecisionExtCase> map, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorBenIssuDataMap() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BEN_ISSU_DATA_MAP, map);
    }

    /**
     * 審核作業 - 遺屬年金給付決行作業 - 受款人核定資料 查詢結果Map<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static Map<BigDecimal, SurvivorPaymentDecisionExtCase> getSurvivorBenIssuDataMap(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorBenIssuDataMap() ...");

        HttpSession session = request.getSession();
        return (Map<BigDecimal, SurvivorPaymentDecisionExtCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BEN_ISSU_DATA_MAP);
    }

    /**
     * 審核作業 - 遺屬年金給付決行作業- 受款人核定資料 查詢結果Map<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorBenIssuDataMap(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorBenIssuDataMap() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BEN_ISSU_DATA_MAP);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 受款人 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorPaymentDecisionBenNameList(List<SurvivorPaymentDecisionExtCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionBenNameList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BENNAME_LIST, list);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 受款人 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionExtCase> getSurvivorPaymentDecisionBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionBenNameList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionExtCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BENNAME_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業- 受款人 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionBenNameList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionBenNameList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_BENNAME_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 交查函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentDecisionLetterTypeMk1List(List<SurvivorPaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_1_LIST, caseList);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 交查函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionCase> getSurvivorPaymentDecisionLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_1_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 交查函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionLetterTypeMk1List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionLetterTypeMk1List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_1_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 遺屬年金給付決行作業 - 不給付函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentDecisionLetterTypeMk2List(List<SurvivorPaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_2_LIST, caseList);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 不給付函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionCase> getSurvivorPaymentDecisionLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_2_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 不給付函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionLetterTypeMk2List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionLetterTypeMk2List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_2_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 遺屬年金給付決行作業 - 補件通知函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentDecisionLetterTypeMk3List(List<SurvivorPaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_3_LIST, caseList);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 補件通知函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionCase> getSurvivorPaymentDecisionLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_3_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 補件通知函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionLetterTypeMk3List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionLetterTypeMk3List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_3_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 遺屬年金給付決行作業 - 照會函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentDecisionLetterTypeMk4List(List<SurvivorPaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_4_LIST, caseList);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 照會函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionCase> getSurvivorPaymentDecisionLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_4_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 照會函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionLetterTypeMk4List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionLetterTypeMk4List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_4_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 遺屬年金給付決行作業 - 其他簽函資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentDecisionLetterTypeMk5List(List<SurvivorPaymentDecisionCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_5_LIST, caseList);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 其他簽函資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static List<SurvivorPaymentDecisionCase> getSurvivorPaymentDecisionLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorPaymentDecisionCase>) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_5_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 其他簽函資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionLetterTypeMk5List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionLetterTypeMk5List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_5_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 遺屬年金給付決行作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 存到 Session 中
     * 
     * @param caseObj
     * @param request
     */
    public static void setSurvivorPaymentDecisionLetterTypeMk6List(SurvivorPaymentDecisionCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorPaymentDecisionLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_6_LIST, caseObj);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 自 Session 中取得 case
     * 
     * @param request
     * @return
     */
    public static SurvivorPaymentDecisionCase getSurvivorPaymentDecisionLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorPaymentDecisionLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        return (SurvivorPaymentDecisionCase) session.getAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_6_LIST);
    }

    /**
     * 決行作業 - 遺屬年金給付決行作業 - 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料<br>
     * 將 case 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorPaymentDecisionLetterTypeMk6List(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorPaymentDecisionLetterTypeMk6List() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_PAYMENT_DECISION_LETTER_TYPE_6_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 決行作業 - 遺屬年金給付決行作業<br>
     * 將 所有查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllSurvivorPaymentDecisionCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllcase() ...");

        HttpSession session = request.getSession();
        removeSurvivorPaymentDecisionList(request);
        removeSurvivorPaymentDecisionCase(request);
        removeSurvivorPaymentDecisionCaseForTitle(request);
        removeSurvivorPaymentDecisionDisabledData(request);
        removeDisabledPayDataList(request);
        removeSurvivorPaymentDecisionEvtChkList(request);
        removeSurvivorPaymentDecisionBenChkList(request);
        removeSurvivorPaymentDecisionMatchChkList(request);
        removeSurvivorBeneficiaryDataList(request);
        removeSurvivorOrigBenIssuDataList(request);
        removeSurvivorBenIssuDataList(request);
        removeSurvivorBenIssuDataMap(request);
        removeSurvivorPaymentDecisionBenNameList(request);
        removeSurvivorPaymentDecisionLetterTypeMk1List(request);
        removeSurvivorPaymentDecisionLetterTypeMk2List(request);
        removeSurvivorPaymentDecisionLetterTypeMk3List(request);
        removeSurvivorPaymentDecisionLetterTypeMk4List(request);
        removeSurvivorPaymentDecisionLetterTypeMk5List(request);
        removeSurvivorPaymentDecisionLetterTypeMk6List(request);
    }
}
