package tw.gov.bli.ba.receipt.helper;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptCase;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptEvtCase;
import tw.gov.bli.ba.receipt.cases.DisabledAnnuityReceiptFamCase;
import tw.gov.bli.ba.receipt.cases.OldAgeAnnuityReceiptCase;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptBenCase;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptCase;
import tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptEvtCase;

/**
 * Case Helper for 受理作業
 * 
 * @author Rickychi
 */
public class CaseSessionHelper {
    private static Log log = LogFactory.getLog(CaseSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 受理作業 - 老年年金給付受理作業 - OldAgeAnnuityReceipt
    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 老年年金給付受理作業 - 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setOldAgeAnnuityReceiptList(List<OldAgeAnnuityReceiptCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setOldAgeAnnuityReceiptList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLDAGE_ANNUITY_RECEIPT_LIST, list);
    }

    /**
     * 受理作業 - 老年年金給付受理作業 - 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<OldAgeAnnuityReceiptCase> getOldAgeAnnuityReceiptList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getOldAgeAnnuityReceiptList() ...");

        HttpSession session = request.getSession();
        return (List<OldAgeAnnuityReceiptCase>) session.getAttribute(ConstantKey.OLDAGE_ANNUITY_RECEIPT_LIST);
    }

    /**
     * 受理作業 - 老年年金給付受理作業- 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeOldAgeAnnuityReceiptList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeOldAgeAnnuityReceiptList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.OLDAGE_ANNUITY_RECEIPT_LIST);
    }

    // ---------------------------------------------------------------------------------------
    // 受理作業 - 失能年金給付受理作業 - DisabledAnnuityReceipt
    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 失能年金給付受理作業 - 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledAnnuityReceiptList(List<DisabledAnnuityReceiptCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledAnnuityReceiptList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_LIST, list);
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledAnnuityReceiptCase> getDisabledAnnuityReceiptList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledAnnuityReceiptList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledAnnuityReceiptCase>) session.getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_LIST);
    }

    /**
     * 受理作業 - 失能年金給付受理作業- 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledAnnuityReceiptList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledAnnuityReceiptList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 失能年金給付受理作業 - 眷屬資料清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledAnnuityReceiptFamDataList(List<DisabledAnnuityReceiptFamCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledAnnuityReceiptFamDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_FAM_DATA_LIST, list);
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 眷屬資料清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<DisabledAnnuityReceiptFamCase> getDisabledAnnuityReceiptFamDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledAnnuityReceiptFamDataList() ...");

        HttpSession session = request.getSession();
        return (List<DisabledAnnuityReceiptFamCase>) session.getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_FAM_DATA_LIST);
    }

    /**
     * 受理作業 - 失能年金給付受理作業- 眷屬資料清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledAnnuityReceiptFamDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledAnnuityReceiptFamDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_FAM_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------
    
    /**
     * 受理作業 - 失能年金給付受理作業 - 登錄國保受理新增作業<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledAnnuityReceiptEvtCase(DisabledAnnuityReceiptEvtCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledAnnuityReceiptEvtCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_EVT_DATA_CASE, caseData);
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 登錄國保受理新增作業<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static DisabledAnnuityReceiptEvtCase getDisabledAnnuityReceiptEvtCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledAnnuityReceiptEvtCase() ...");

        HttpSession session = request.getSession();
        return (DisabledAnnuityReceiptEvtCase) session.getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_EVT_DATA_CASE);
    }

    /**
     * 受理作業 - 失能年金給付受理作業- 登錄國保受理新增作業<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledAnnuityReceiptEvtCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledAnnuityReceiptEvtCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_EVT_DATA_CASE);
    }
    
    // ---------------------------------------------------------------------------------------

    /**
     * 受理作業 - 失能年金給付受理作業 - 遺屬眷屬暫存檔資料列編號<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setDisabledAnnuityReceiptBafamilytempId(BigDecimal bafamilytempId, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setDisabledAnnuityReceiptBafamilytempId() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_BAFAMILYTEMPID, bafamilytempId);
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 遺屬眷屬暫存檔資料列編號<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static BigDecimal getDisabledAnnuityReceiptBafamilytempId(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledAnnuityReceiptBafamilytempId() ...");

        HttpSession session = request.getSession();
        return (BigDecimal) session.getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_BAFAMILYTEMPID);
    }

    /**
     * 受理作業 - 失能年金給付受理作業 - 遺屬眷屬暫存檔資料列編號<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static String getDisabledAnnuityReceiptBafamilytempIdStr(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getDisabledAnnuityReceiptBafamilytempIdStr() ...");

        HttpSession session = request.getSession();
        return ((BigDecimal) session.getAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_BAFAMILYTEMPID)).toString();
    }

    /**
     * 受理作業 - 失能年金給付受理作業- 遺屬眷屬暫存檔資料列編號<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeDisabledAnnuityReceiptBafamilytempId(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeDisabledAnnuityReceiptBafamilytempId() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_ANNUITY_RECEIPT_BAFAMILYTEMPID);
    }

    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將 所有查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllDisabledAnnuityReceiptCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllDisabledAnnuityReceiptCase() ...");

        removeDisabledAnnuityReceiptBafamilytempId(request);
        removeDisabledAnnuityReceiptFamDataList(request);
        removeDisabledAnnuityReceiptList(request);
    }

    // ---------------------------------------------------------------------------------------
    // 受理作業 - 遺屬年金給付受理作業 - SurvivorAnnuityReceipt
    // ---------------------------------------------------------------------------------------
    /**
     * 受理作業 - 遺屬年金給付受理作業 - 查詢結果清單<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorAnnuityReceiptList(List<SurvivorAnnuityReceiptCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorAnnuityReceiptList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_LIST, list);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 查詢結果清單<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorAnnuityReceiptCase> getSurvivorAnnuityReceiptList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorAnnuityReceiptList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorAnnuityReceiptCase>) session.getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_LIST);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業- 查詢結果清單<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorAnnuityReceiptList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorAnnuityReceiptList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 事故者資料<br>
     * 將 遺屬資料清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorAnnuityReceiptEvtData(SurvivorAnnuityReceiptEvtCase evtCase, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorAnnuityReceiptEvtData() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_EVT_DATA, evtCase);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 事故者資料<br>
     * 自 Session 中取得 遺屬資料清單
     * 
     * @param request
     * @return
     */
    public static SurvivorAnnuityReceiptEvtCase getSurvivorAnnuityReceiptEvtData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorAnnuityReceiptEvtData ...");

        HttpSession session = request.getSession();
        return (SurvivorAnnuityReceiptEvtCase) session.getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_EVT_DATA);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業- 事故者資料<br>
     * 將 遺屬資料清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorAnnuityReceiptEvtData(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorAnnuityReceiptEvtData() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_EVT_DATA);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 遺屬資料清單<br>
     * 將 遺屬資料清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorAnnuityReceiptBenDataList(List<SurvivorAnnuityReceiptBenCase> list, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorAnnuityReceiptBenDataList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BEN_DATA_LIST, list);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 遺屬資料清單<br>
     * 自 Session 中取得 遺屬資料清單
     * 
     * @param request
     * @return
     */
    public static List<SurvivorAnnuityReceiptBenCase> getSurvivorAnnuityReceiptBenDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorAnnuityReceiptBenDataList() ...");

        HttpSession session = request.getSession();
        return (List<SurvivorAnnuityReceiptBenCase>) session.getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BEN_DATA_LIST);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業- 遺屬資料清單<br>
     * 將 遺屬資料清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorAnnuityReceiptBenDataList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorAnnuityReceiptBenDataList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BEN_DATA_LIST);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 遺屬眷屬暫存檔資料列編號<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setSurvivorAnnuityReceiptBafamilytempId(BigDecimal bafamilytempId, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setSurvivorAnnuityReceiptBafamilytempId() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BAFAMILYTEMPID, bafamilytempId);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 遺屬眷屬暫存檔資料列編號<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static BigDecimal getSurvivorAnnuityReceiptBafamilytempId(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorAnnuityReceiptBafamilytempId() ...");

        HttpSession session = request.getSession();
        return (BigDecimal) session.getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BAFAMILYTEMPID);
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業 - 遺屬眷屬暫存檔資料列編號<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static String getSurvivorAnnuityReceiptBafamilytempIdStr(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getSurvivorAnnuityReceiptBafamilytempIdStr() ...");

        HttpSession session = request.getSession();
        return ((BigDecimal) session.getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BAFAMILYTEMPID)).toString();
    }

    /**
     * 受理作業 - 遺屬年金給付受理作業- 遺屬眷屬暫存檔資料列編號<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeSurvivorAnnuityReceiptBafamilytempId(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeSurvivorAnnuityReceiptBafamilytempId() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BAFAMILYTEMPID);
    }

    // ---------------------------------------------------------------------------------------

    /**
     * 受理作業 - 失能年金給付受理作業<br>
     * 將 所有查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllSurvivorAnnuityReceiptCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllSurvivorAnnuityReceiptCase() ...");
        removeSurvivorAnnuityReceiptList(request);
        removeSurvivorAnnuityReceiptBenDataList(request);
        removeSurvivorAnnuityReceiptBafamilytempId(request);
    }
}
