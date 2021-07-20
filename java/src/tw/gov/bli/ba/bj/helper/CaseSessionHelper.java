package tw.gov.bli.ba.bj.helper;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.CheckJobCase;
import tw.gov.bli.ba.bj.cases.ExecStatisticsDtlCase;
import tw.gov.bli.ba.bj.cases.CompPaymentCase;
import tw.gov.bli.ba.bj.cases.MediaOnlineDownloadCase;
import tw.gov.bli.ba.bj.cases.MediaOnlineProgressCase;
import tw.gov.bli.ba.bj.cases.MediaOnlineProgressDtlCase;
import tw.gov.bli.ba.bj.cases.MonthBatchDtlCase;
import tw.gov.bli.ba.bj.cases.MonthBatchKCase;
import tw.gov.bli.ba.bj.cases.MonthBatchLCase;
import tw.gov.bli.ba.bj.cases.MonthBatchSCase;
import tw.gov.bli.ba.bj.cases.MonthCheckKCase;
import tw.gov.bli.ba.bj.cases.MonthCheckLCase;
import tw.gov.bli.ba.bj.cases.MonthCheckSCase;
import tw.gov.bli.ba.bj.cases.MonthKCase;
import tw.gov.bli.ba.bj.cases.MonthLCase;
import tw.gov.bli.ba.bj.cases.MonthQueryCase;
import tw.gov.bli.ba.bj.cases.MonthSCase;
import tw.gov.bli.ba.bj.cases.QryProcedureCase;
import tw.gov.bli.ba.bj.cases.UpdatePaidMarkBJCase;
import tw.gov.bli.ba.bj.cases.ReturnWriteOffBJCase;
import tw.gov.bli.ba.bj.cases.ReceivableAdjustBJDetailCase;
import tw.gov.bli.ba.bj.cases.ReceivableAdjustBJMasterCase;
import tw.gov.bli.ba.bj.cases.Trans2OverdueReceivableBJDetailCase;
import tw.gov.bli.ba.bj.cases.Trans2OverdueReceivableBJMasterCase;
import tw.gov.bli.ba.bj.cases.BagivedtlCase;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.ba.domain.Bagivedtl;
import tw.gov.bli.ba.payment.cases.PaymentProcessCase;
import tw.gov.bli.ba.payment.cases.PaymentProcessQueryCase;
import tw.gov.bli.ba.payment.cases.PaymentProgressQueryCase;
import tw.gov.bli.ba.payment.cases.PaymentReprintCase;
import tw.gov.bli.ba.payment.cases.PaymentStageDtlCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt08ProgressCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10ProgressCase;
import tw.gov.bli.ba.rpt.cases.OtherRpt08Case;

/**
 * Case Helper for 批次處理
 * 
 * @author Rickychi
 */
public class CaseSessionHelper {
    private static Log log = LogFactory.getLog(CaseSessionHelper.class);

    // ---------------------------------------------------------------------------------------
    // 批次處理 - 給付媒體回押註記作業 - UpdatePaidMarkBJ
    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 給付媒體回押註記作業 - 查詢結果Master<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setUpdatePaidMarkBJCase(List<UpdatePaidMarkBJCase> caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setUpdatePaidMarkBJCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_CASE, caseObj);
    }

    /**
     * 批次處理 - 給付媒體回押註記作業 - 查詢結果Master<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<UpdatePaidMarkBJCase> getUpdatePaidMarkBJCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getUpdatePaidMarkBJCase() ...");

        HttpSession session = request.getSession();
        return (List<UpdatePaidMarkBJCase>) session.getAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_CASE);
    }

    /**
     * 批次處理 - 給付媒體回押註記作業 - 查詢結果Master<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUpdatePaidMarkBJCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeUpdatePaidMarkBJCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_CASE);
    }
    
    /**
     * 批次處理 - 給付媒體回押註記作業 - 查詢結果Master<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setUpdatePaidMarkBJDetailCase(UpdatePaidMarkBJCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setUpdatePaidMarkBJDetailCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_DETAIL_CASE, caseObj);
    }

    /**
     * 批次處理 - 給付媒體回押註記作業 - 查詢結果Master<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUpdatePaidMarkBJDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeUpdatePaidMarkBJDetailCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_DETAIL_CASE);
    }
    
    /**
     * 批次處理 - 給付媒體回押註記作業 - 給付媒體檔案資料比對結果<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setUpdatePaidMarkBJDetailCase2(BagivedtlCase caseData, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setUpdatePaidMarkBJDetailCase2() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_DETAIL_CASE_2, caseData);
    }

    /**
     * 批次處理 - 給付媒體回押註記作業 - 給付媒體檔案資料比對結果<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUpdatePaidMarkBJDetailCase2(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeUpdatePaidMarkBJDetailCase2() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_DETAIL_CASE_2);
    }
    
    /**
     * 批次處理 - 給付媒體回押註記作業 - 給付媒體檔案轉入資訊<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setUpdatePaidMarkBJDetailCase3(List<BagivedtlCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setUpdatePaidMarkBJDetailCase3() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_DETAIL_CASE_3, caseList);
    }

    /**
     * 批次處理 - 給付媒體回押註記作業 - 給付媒體檔案轉入資訊<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeUpdatePaidMarkBJDetailCase3(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeUpdatePaidMarkBJDetailCase3() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.UPDATE_PAID_MARK_BJ_DETAIL_CASE_3);
    }
    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 批次處理 - 收回作業- ReturnWriteOffBJ
    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 收回作業 - 查詢結果Master<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReturnWriteOffBJCase(List<ReturnWriteOffBJCase> caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setReturnWriteOffBJCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RETURN_WRITE_OFF_BJ_CASE, caseObj);
    }

    /**
     * 批次處理 - 收回作業 - 查詢結果Master<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<ReturnWriteOffBJCase> getReturnWriteOffBJCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReturnWriteOffBJCase() ...");

        HttpSession session = request.getSession();
        return (List<ReturnWriteOffBJCase>) session.getAttribute(ConstantKey.RETURN_WRITE_OFF_BJ_CASE);
    }

    /**
     * 批次處理 - 收回作業 - 查詢結果Master<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReturnWriteOffBJCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReturnWriteOffBJCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.RETURN_WRITE_OFF_BJ_CASE);
    }

    // ---------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------
    // 批次處理 - 轉催收作業 - Trans2OverdueReceivableBJ
    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 轉催收作業 - 查詢結果Master<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setTrans2OverdueReceivableBJMasterCase(Trans2OverdueReceivableBJMasterCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setTrans2OverdueReceivableBJMasterCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_MASTER_CASE, caseObj);
    }

    /**
     * 批次處理 - 轉催收作業 - 查詢結果Master<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static Trans2OverdueReceivableBJMasterCase getTrans2OverdueReceivableBJMasterCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getTrans2OverdueReceivableBJMasterCase() ...");

        HttpSession session = request.getSession();
        return (Trans2OverdueReceivableBJMasterCase) session.getAttribute(ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_MASTER_CASE);
    }

    /**
     * 批次處理 - 轉催收作業- 查詢結果Master<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeTrans2OverdueReceivableBJMasterCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeTrans2OverdueReceivableBJMasterCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_MASTER_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 轉催收作業 - 查詢結果Master<br>
     * 將 查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setTrans2OverdueReceivableBJDetailCase(List<Trans2OverdueReceivableBJDetailCase> caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setTrans2OverdueReceivableBJDetailCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_DETAIL_CASE, caseObj);
    }

    /**
     * 批次處理 - 轉催收作業 - 查詢結果Master<br>
     * 自 Session 中取得 查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<Trans2OverdueReceivableBJDetailCase> getTrans2OverdueReceivableBJDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getTrans2OverdueReceivableBJDetailCase() ...");

        HttpSession session = request.getSession();
        return (List<Trans2OverdueReceivableBJDetailCase>) session.getAttribute(ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_DETAIL_CASE);
    }

    /**
     * 批次處理 - 轉催收作業- 查詢結果Master<br>
     * 將 查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeTrans2OverdueReceivableBJDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeTrans2OverdueReceivableBJDetailCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.TRANS_2_OVERDUE_RECEIVABLE_BJ_DETAIL_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 轉催收作業<br>
     * 將 所有查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllTrans2OverdueReceivableBJCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllTrans2OverdueReceivableBJCase() ...");

        HttpSession session = request.getSession();
        removeTrans2OverdueReceivableBJMasterCase(request);
        removeTrans2OverdueReceivableBJDetailCase(request);
    }

    // ---------------------------------------------------------------------------------------
    // 批次處理 - 已收調整作業 - ReceivableAdjustBJ
    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 已收調整作業 - Master查詢結果<br>
     * 將 Master查詢結果 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReceivableAdjustBJMasterCase(ReceivableAdjustBJMasterCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setReceivableAdjustBJMasterCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_MASTER_CASE, caseObj);
    }

    /**
     * 批次處理 - 已收調整作業 - Master查詢結果<br>
     * 自 Session 中取得 Master查詢結果
     * 
     * @param request
     * @return
     */
    public static ReceivableAdjustBJMasterCase getReceivableAdjustBJMasterCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReceivableAdjustBJMasterCase() ...");

        HttpSession session = request.getSession();
        return (ReceivableAdjustBJMasterCase) session.getAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_MASTER_CASE);
    }

    /**
     * 批次處理 - 已收調整作業 - Master查詢結果<br>
     * 將 Master查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReceivableAdjustBJMasterCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReceivableAdjustBJMasterCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_MASTER_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 已收調整作業 - Master查詢結果清單<br>
     * 將 Master查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReceivableAdjustBJMasterCaseList(List<ReceivableAdjustBJMasterCase> caseList, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setReceivableAdjustBJMasterCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_MASTER_CASE_LIST, caseList);
    }

    /**
     * 批次處理 - 已收調整作業 - Master查詢結果清單<br>
     * 自 Session 中取得 Master查詢結果清單
     * 
     * @param request
     * @return
     */
    public static List<ReceivableAdjustBJMasterCase> getReceivableAdjustBJMasterCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReceivableAdjustBJMasterCaseList() ...");

        HttpSession session = request.getSession();
        return (List<ReceivableAdjustBJMasterCase>) session.getAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_MASTER_CASE_LIST);
    }

    /**
     * 批次處理 - 已收調整作業 - Master查詢結果清單<br>
     * 將 Master查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReceivableAdjustBJMasterCaseList(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReceivableAdjustBJMasterCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_MASTER_CASE_LIST);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 已收調整作業 - Detail查詢結果清單<br>
     * 將 Detail查詢結果清單 存到 Session 中
     * 
     * @param caseData
     * @param request
     */
    public static void setReceivableAdjustBJDetailCase(ReceivableAdjustBJDetailCase caseObj, HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.setReceivableAdjustBJDetailCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_DETAIL_CASE, caseObj);
    }

    /**
     * 批次處理 - 已收調整作業 - Detail查詢結果清單<br>
     * 自 Session 中取得 Detail查詢結果清單
     * 
     * @param request
     * @return
     */
    public static ReceivableAdjustBJDetailCase getReceivableAdjustBJDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.getReceivableAdjustBJMasterCase() ...");

        HttpSession session = request.getSession();
        return (ReceivableAdjustBJDetailCase) session.getAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_DETAIL_CASE);
    }

    /**
     * 批次處理 - 已收調整作業 - Detail查詢結果清單<br>
     * 將 Detail查詢結果清單 自 Session 中移除
     * 
     * @param request
     */
    public static void removeReceivableAdjustBJDetailCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeReceivableAdjustBJMasterCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.RECEIVABLE_ADJUST_BJ_DETAIL_CASE);
    }

    // ---------------------------------------------------------------------------------------
    /**
     * 批次處理 - 已收調整作業<br>
     * 將 所有查詢結果 自 Session 中移除
     * 
     * @param request
     */
    public static void removeAllReceivableAdjustBJCase(HttpServletRequest request) {
        log.debug("執行 CaseSessionHelper.removeAllReceivableAdjustBJCase() ...");

        HttpSession session = request.getSession();
        removeReceivableAdjustBJMasterCase(request);
        removeReceivableAdjustBJMasterCaseList(request);
        removeReceivableAdjustBJDetailCase(request);
    }
    
    public static void setOldMedaOnlineCase(MediaOnlineDownloadCase caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setOldMediaOnlineCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLD_MEDIA_ONLINE_CASE, caseObj);
    }
    public static MediaOnlineDownloadCase getOldMedaOnlineCase(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getOldMediaOnlineCase() ...");

        HttpSession session = request.getSession();
        return (MediaOnlineDownloadCase) session.getAttribute(ConstantKey.OLD_MEDIA_ONLINE_CASE);
    }
    public static void removeOldMedaOnlineCase(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeOldMediaOnlineCase() ...");
    	
        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.OLD_MEDIA_ONLINE_CASE);
    }
    public static void setOldMediaOnlineCaseList(List<MediaOnlineDownloadCase> caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setOldMediaOnlineCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLD_MEDIA_ONLINE_CASE_LIST, caseObj);
    }
    public static List<MediaOnlineDownloadCase> getOldMediaOnlineCaseList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getOldMediaOnlineCaseList() ...");

        HttpSession session = request.getSession();
        return (List<MediaOnlineDownloadCase>) session.getAttribute(ConstantKey.OLD_MEDIA_ONLINE_CASE_LIST);
    }
    public void removeOldMediaOnlineCaseList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeOldMediaOnlineCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.OLD_MEDIA_ONLINE_CASE_LIST);
    }
    
    public static void setSurvivorMediaOnlineCase(MediaOnlineDownloadCase caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setSurvivorMediaOnlineCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_CASE, caseObj);
    }
    public static MediaOnlineDownloadCase getSurvivorMediaOnlineCase(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getSurvivorMediaOnlineCase() ...");

        HttpSession session = request.getSession();
        return (MediaOnlineDownloadCase) session.getAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_CASE);
    }
    public static void removeSurvivorMediaOnlineCase(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeSurvivorMediaOnlineCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_CASE);
    }
    public static void setSurvivorMediaOnlineCaseList(List<MediaOnlineDownloadCase> caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setSurvivorMediaOnlineCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_CASE_LIST, caseObj);
    }
  
    public static List<MediaOnlineDownloadCase> getSurvivorMediaOnlineCaseList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getSurvivorMediaOnlineCaseList() ...");

        HttpSession session = request.getSession();
        return (List<MediaOnlineDownloadCase>) session.getAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_CASE_LIST);
    }
    
    public static void removeSurvivorMediaOnlineCaseList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeSurvivorMediaOnlineCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_CASE_LIST);
    }
    
    public static void setDisabledMediaOnlineCase(MediaOnlineDownloadCase caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setDisabledMediaOnlineCase() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_CASE, caseObj);
    }
    
    public static MediaOnlineDownloadCase getDisabledMediaOnlineCase(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getDisabledMediaOnlineCase() ...");

        HttpSession session = request.getSession();
        return (MediaOnlineDownloadCase) session.getAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_CASE);
    }

    public static void removeDisabledMediaOnlineCase(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeDisabledMediaOnlineCase() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_CASE);
    }
    public static void setDisabledMediaOnlineCaseList(List<MediaOnlineDownloadCase> caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setDisabledMediaOnlineCaseList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_CASE_LIST, caseObj);
    }
    public static List<MediaOnlineDownloadCase> getDisabledMediaOnlineCaseList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getDisabledMediaOnlineCaseList() ...");

        HttpSession session = request.getSession();
        return (List<MediaOnlineDownloadCase>) session.getAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_CASE_LIST);
    }
    public static void removeDisabledMediaOnlineCaseList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeDisabledMediaOnlineCaseList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_CASE_LIST);
    }
    public static void setOldMediaOnlineProgressList(List<MediaOnlineProgressCase> caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setOldMediaOnlineProgressList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLD_MEDIA_ONLINE_PROGRESS_CASE_LIST, caseObj);
    }
    public static List<MediaOnlineProgressCase> getOldMediaOnlineProgressList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getOldMediaOnlineProgressList() ...");

        HttpSession session = request.getSession();
        return (List<MediaOnlineProgressCase>) session.getAttribute(ConstantKey.OLD_MEDIA_ONLINE_PROGRESS_CASE_LIST);
    }
    public static void removeOldMediaOnlineProgressList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeOldMediaOnlineProgressList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.OLD_MEDIA_ONLINE_PROGRESS_CASE_LIST);
    }
    
    public static void setOldMediaOnlineProgressDtlList(List<MediaOnlineProgressDtlCase> caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setOldMediaOnlineProgressDtlList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.OLD_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST, caseObj);
    }
    public static List<MediaOnlineProgressDtlCase> getOldMediaOnlineProgressDtlList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getOldMediaOnlineProgressDtlList() ...");

        HttpSession session = request.getSession();
        return (List<MediaOnlineProgressDtlCase>) session.getAttribute(ConstantKey.OLD_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST);
    }
    public static void removeOldMediaOnlineProgressDtlList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeOldMediaOnlineProgressDtlList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.OLD_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST);
    }
    public static void setDisabledMediaOnlineProgressList(List<MediaOnlineProgressCase> caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setDisabledMediaOnlineProgressList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_PROGRESS_CASE_LIST, caseObj);
    }
    public static List<MediaOnlineProgressCase> getDisabledMediaOnlineProgressList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getDisabledMediaOnlineProgressList() ...");

        HttpSession session = request.getSession();
        return (List<MediaOnlineProgressCase>) session.getAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_PROGRESS_CASE_LIST);
    }
    public static void removeDisabledMediaOnlineProgressList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeDisabledMediaOnlineProgressList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_PROGRESS_CASE_LIST);
    }
    
    public static void setDisabledMediaOnlineProgressDtlList(List<MediaOnlineProgressDtlCase> caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setDisabledMediaOnlineProgressDtlList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST, caseObj);
    }
    public static List<MediaOnlineProgressDtlCase> getDisabledMediaOnlineProgressDtlList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getDisabledMediaOnlineProgressDtlList() ...");

        HttpSession session = request.getSession();
        return (List<MediaOnlineProgressDtlCase>) session.getAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST);
    }
    public static void removeDisabledMediaOnlineProgressDtlList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeDisabledMediaOnlineProgressDtlList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.DISABLED_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST);
    }
    public static void setSurvivorMediaOnlineProgressList(List<MediaOnlineProgressCase> caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setSurvivorMediaOnlineProgressList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_PROGRESS_CASE_LIST, caseObj);
    }
    public static List<MediaOnlineProgressCase> getSurvivorMediaOnlineProgressList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getSurvivorMediaOnlineProgressList() ...");

        HttpSession session = request.getSession();
        return (List<MediaOnlineProgressCase>) session.getAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_PROGRESS_CASE_LIST);
    }
    public static void removeSurvivorMediaOnlineProgressList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeSurvivorMediaOnlineProgressList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_PROGRESS_CASE_LIST);
    }
    public static void setSurvivorMediaOnlineProgressDtlList(List<MediaOnlineProgressDtlCase> caseObj, HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.setSurvivorMediaOnlineProgressDtlList() ...");

        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST, caseObj);
    }
    public static List<MediaOnlineProgressDtlCase> getSurvivorMediaOnlineProgressDtlList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.getSurvivorMediaOnlineProgressDtlList() ...");

        HttpSession session = request.getSession();
        return (List<MediaOnlineProgressDtlCase>) session.getAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST);
    }
    public static void removeSurvivorMediaOnlineProgressDtlList(HttpServletRequest request){
    	log.debug("執行 CaseSessionHelper.removeSurvivorMediaOnlineProgressDtlList() ...");

        HttpSession session = request.getSession();
        session.removeAttribute(ConstantKey.SURVIVOR_MEDIA_ONLINE_PROGRESS_DTL_CASE_LIST);
    }
    public static void removeAllOldMediaOnlineCaseList(HttpServletRequest request){
    	removeOldMedaOnlineCase(request);
    	removeOldMediaOnlineProgressDtlList(request);
    	removeOldMediaOnlineProgressList(request);
    }
    public static void removeAllSurvivorMediaOnlineCaseList(HttpServletRequest request){
    	removeSurvivorMediaOnlineCase(request);
    	removeSurvivorMediaOnlineCaseList(request);
    	removeSurvivorMediaOnlineProgressDtlList(request);
    	removeSurvivorMediaOnlineProgressList(request);
    	
    }
    public static void removeAllDisabledMediaOnlineCaseList(HttpServletRequest request){
    	removeDisabledMediaOnlineCase(request);
    	removeDisabledMediaOnlineCaseList(request);
    	removeDisabledMediaOnlineProgressDtlList(request);
    	removeDisabledMediaOnlineProgressList(request);
    	
    }
    public static void removeAllPaymentCaseList(HttpServletRequest request){
    	removePaymentQueryList(request);
    	removePaymentProcessQueryList(request);
    	removePaymentProcessDetailList(request);
    	removePaymentAssignLastList(request);
    	removePaymentAssignDetailList(request);
    	removePaymentInterestDetailList(request);
    	removePaymentReprintList(request);
    	removePaymentInterestDetailListForDb(request);
    	
    }
    
    // ---------------------------------------------------------------------------------------
       
       /**
        * 批次作業 - 線上月核作業 - 查詢結果 TITLE<br>
        * 將 case 存到 Session 中
        * 
        * @param caseData
        * @param request
        */
       public static void setMonthQueryCaseForTitle(MonthQueryCase obj, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthQueryCaseForTitle() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_QUERY_CASE_FOR_TITLE, obj);
       }

       /**
        * 批次作業 - 線上月核作業 - 查詢結果 TITLE<br>
        * 自 Session 中取得 case
        * 
        * @param request
        * @return
        */
       public static MonthQueryCase getMonthQueryCaseForTitle(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthQueryCaseForTitle() ...");

           HttpSession session = request.getSession();
           return (MonthQueryCase) session.getAttribute(ConstantKey.MONTH_QUERY_CASE_FOR_TITLE);
       }

       
       /**
        * 批次作業 - 線上月核作業 - 查詢結果 TITLE<br>
        * 將 case 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthQueryCaseForTitle(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthQueryCaseForTitle() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_QUERY_CASE_FOR_TITLE);
       }
       
       /**
        * 批次作業 - 線上月核作業 - 查詢結果清單<br>
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param caseData
        * @param request
        */
       public static void setMonthLCaseList(List<MonthLCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthLCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_L_CASE_LIST, list);
       }

       /**
        * 批次作業 - 線上月核作業 - 查詢結果清單<br>
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthLCase> getMonthLCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthLCaseList() ...");

           HttpSession session = request.getSession();
           return (List<MonthLCase>) session.getAttribute(ConstantKey.MONTH_L_CASE_LIST);
       }

       /**
        * 批次作業 - 線上月核作業- 查詢結果清單<br>
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthLCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthLCaseList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_L_CASE_LIST);
       }
       
       /**
        * 批次作業 - 線上月核作業 - 查詢結果清單<br>
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param caseData
        * @param request
        */
       public static void setMonthKCaseList(List<MonthKCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthKCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_K_CASE_LIST, list);
       }

       /**
        * 批次作業 - 線上月核作業 - 查詢結果清單<br>
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthKCase> getMonthKCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthKCaseList() ...");

           HttpSession session = request.getSession();
           return (List<MonthKCase>) session.getAttribute(ConstantKey.MONTH_K_CASE_LIST);
       }

       /**
        * 批次作業 - 線上月核作業- 查詢結果清單<br>
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthKCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthKCaseList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_K_CASE_LIST);
       }
       
       /**
        * 批次作業 - 線上月核作業 - 查詢結果清單<br>
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param caseData
        * @param request
        */
       public static void setMonthSCaseList(List<MonthSCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthSCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_S_CASE_LIST, list);
       }

       /**
        * 批次作業 - 線上月核作業 - 查詢結果清單<br>
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthSCase> getMonthSCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthSCaseList() ...");

           HttpSession session = request.getSession();
           return (List<MonthSCase>) session.getAttribute(ConstantKey.MONTH_S_CASE_LIST);
       }

       /**
        * 批次作業 - 線上月核作業- 查詢結果清單<br>
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthSCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthSCaseList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_S_CASE_LIST);
       }
       
       /**
        * 批次作業 - 線上月試核作業 - 查詢結果清單<br> 老年
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param caseData
        * @param request
        */
       public static void setMonthCheckLCaseList(List<MonthCheckLCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthCheckLCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_CHECK_L_CASE_LIST, list);
       }

       /**
        * 批次作業 - 線上月試核作業 - 查詢結果清單<br> 老年
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthCheckLCase> getMonthCheckLCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthCheckLCaseList() ...");

           HttpSession session = request.getSession();
           return (List<MonthCheckLCase>) session.getAttribute(ConstantKey.MONTH_CHECK_L_CASE_LIST);
       }

       /**
        * 批次作業 - 線上月試核作業- 查詢結果清單<br> 老年
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthCheckLCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthCheckLCaseList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_CHECK_L_CASE_LIST);
       }
       
       /**
        * 批次作業 - 線上月試核作業 - 查詢結果清單<br> 失能
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param caseData
        * @param request
        */
       public static void setMonthCheckKCaseList(List<MonthCheckKCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthCheckKCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_CHECK_K_CASE_LIST, list);
       }

       /**
        * 批次作業 - 線上月試核作業 - 查詢結果清單<br> 失能
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthCheckKCase> getMonthCheckKCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthCheckKCaseList() ...");

           HttpSession session = request.getSession();
           return (List<MonthCheckKCase>) session.getAttribute(ConstantKey.MONTH_CHECK_K_CASE_LIST);
       }

       /**
        * 批次作業 - 線上月試核作業- 查詢結果清單<br> 失能
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthCheckKCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthCheckKCaseList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_CHECK_K_CASE_LIST);
       }
       
       /**
        * 批次作業 - 線上月試核作業 - 查詢結果清單<br> 遺屬
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param caseData
        * @param request
        */
       public static void setMonthCheckSCaseList(List<MonthCheckSCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthCheckSCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_CHECK_S_CASE_LIST, list);
       }

       /**
        * 批次作業 - 線上月試核作業 - 查詢結果清單<br> 遺屬
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthCheckSCase> getMonthCheckSCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthCheckSCaseList() ...");

           HttpSession session = request.getSession();
           return (List<MonthCheckSCase>) session.getAttribute(ConstantKey.MONTH_CHECK_S_CASE_LIST);
       }

       /**
        * 批次作業 - 線上月試核作業- 查詢結果清單<br> 遺屬
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthCheckSCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthCheckSCaseList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_CHECK_S_CASE_LIST);
       }

       /**
        * 批次處理 - 線上月核定作業 - 查詢系統日期是否介於批次月試核及月核定期間，若是則[確認]鍵disable。<br>
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param caseData
        * @param request
        */
       public static void setMonthLCheckCountStr(String checkCount, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthLCheckCountStr() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_Y, checkCount);
       }

       /**
        * 批次處理 - 線上月核定作業 - 查詢系統日期是否介於批次月試核及月核定期間，若是則[確認]鍵disable。<br>
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static String getMonthLCheckCountStr(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthLCheckCountStr() ...");

           HttpSession session = request.getSession();
           return ((String) session.getAttribute(ConstantKey.MONTH_Y));
       }

       /**
        * 批次處理 - 線上月核定作業 - 查詢系統日期是否介於批次月試核及月核定期間，若是則[確認]鍵disable。<br>
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthLCheckCountStr(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthLCheckCountStr() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_Y);
       }
       
       /**
        * 批次處理 - 線上月核定作業 - 預設頁面核定年月。<br>
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param caseData
        * @param request
        */
       public static void setMonthQueryIssuYmStr(String issuYm, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthQueryIssuYmStr() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_QUERY_ISSUYM, issuYm);
       }

       /**
        * 批次處理 - 線上月核定作業 - 預設頁面核定年月。<br>
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static String getMonthQueryIssuYmStr(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthQueryIssuYmStr() ...");

           HttpSession session = request.getSession();
           return ((String) session.getAttribute(ConstantKey.MONTH_QUERY_ISSUYM));
       }

       /**
        * 批次處理 - 線上月核定作業 - 預設頁面核定年月。<br>
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthQueryIssuYmStr(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthQueryIssuYmStr() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_QUERY_ISSUYM);
       }
       
       
       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 老年
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setMonthBatchLCaseList(List<MonthBatchLCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthBatchLCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_BATCH_L_CASE_LIST, list);
       }

       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 老年
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthBatchLCase> getMonthBatchLCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthBatchLCaseList() ...");

           HttpSession session = request.getSession();
           return (List<MonthBatchLCase>) session.getAttribute(ConstantKey.MONTH_BATCH_L_CASE_LIST);
       }

       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 老年
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthBatchLCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthBatchLCaseList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_BATCH_L_CASE_LIST);
       }
       
       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 失能
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setMonthBatchKCaseList(List<MonthBatchKCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthBatchKCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_BATCH_K_CASE_LIST, list);
       }

       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 失能
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthBatchKCase> getMonthBatchKCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthBatchKCaseList() ...");

           HttpSession session = request.getSession();
           return (List<MonthBatchKCase>) session.getAttribute(ConstantKey.MONTH_BATCH_K_CASE_LIST);
       }

       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 失能
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthBatchKCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthBatchKCaseList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_BATCH_K_CASE_LIST);
       }
       
       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 遺屬
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setMonthBatchSCaseList(List<MonthBatchSCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthBatchSCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_BATCH_S_CASE_LIST, list);
       }

       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 遺屬
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthBatchSCase> getMonthBatchSCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthBatchSCaseList() ...");

           HttpSession session = request.getSession();
           return (List<MonthBatchSCase>) session.getAttribute(ConstantKey.MONTH_BATCH_S_CASE_LIST);
       }

       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 遺屬
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthBatchSCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthBatchSCaseList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_BATCH_S_CASE_LIST);
       }
       
       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 批次作業明細檔頁面
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setMonthBatchDtlCaseList(List<MonthBatchDtlCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setMonthBatchDtlCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTH_BATCH_DTL_CASE_LIST, list);
       }

       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 批次作業明細檔頁面
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthBatchDtlCase> getMonthBatchDtlCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthBatchDtlCaseList() ...");

           HttpSession session = request.getSession();
           return (List<MonthBatchDtlCase>) session.getAttribute(ConstantKey.MONTH_BATCH_DTL_CASE_LIST);
       }

       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 批次作業明細檔頁面
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthBatchDtlCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthBatchDtlCaseList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTH_BATCH_DTL_CASE_LIST);
       }
       
       /**
        * 月核定處理相關報表 - 合格清冊產製報表進度查詢
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param request
        */
       public static void setMonthlyRpt08ProgressList(List<MonthlyRpt08ProgressCase> list, HttpServletRequest request){
    	  
    	   log.debug("執行 CaseSessionHelper.setMonthlyRpt08ProgressList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTHLY_RPT08_PROGRESS_CASE_LIST, list);
       }
       /**
        * 月核定處理相關報表 - 合格清冊產製報表進度查詢
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthlyRpt08ProgressCase> getMonthlyRpt08ProgressList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthlyRpt08ProgressList() ...");

           HttpSession session = request.getSession();
           return (List<MonthlyRpt08ProgressCase>) session.getAttribute(ConstantKey.MONTHLY_RPT08_PROGRESS_CASE_LIST);
       }
       
       /**
        * 月核定處理相關報表 - 合格清冊產製報表進度查詢
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthlyRpt08ProgressList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthlyRpt08ProgressList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTHLY_RPT08_PROGRESS_CASE_LIST);
       }
       
       /**
        * 月核定處理相關報表 - 核付明細表產製報表進度查詢
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param request
        */
       public static void setMonthlyRpt10ProgressList(List<MonthlyRpt10ProgressCase> list, HttpServletRequest request){
    	  
    	   log.debug("執行 CaseSessionHelper.setMonthlyRpt10ProgressList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.MONTHLY_RPT10_PROGRESS_CASE_LIST, list);
       }
       /**
        * 月核定處理相關報表 - 核付明細表產製報表進度查詢
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<MonthlyRpt08ProgressCase> getMonthlyRpt10ProgressList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getMonthlyRpt10ProgressList() ...");

           HttpSession session = request.getSession();
           return (List<MonthlyRpt08ProgressCase>) session.getAttribute(ConstantKey.MONTHLY_RPT10_PROGRESS_CASE_LIST);
       }
       
       /**
        * 月核定處理相關報表 - 核付明細表產製報表進度查詢
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeMonthlyRpt10ProgressList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeMonthlyRpt10ProgressList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.MONTHLY_RPT10_PROGRESS_CASE_LIST);
       }
       
       /**
        * 批次作業 - 檢核確認作業 - 查詢結果清單<br> 檢核確認作業明細檔頁面
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setCheckJobCaseList(List<CheckJobCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setCheckJobCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.CHECK_JOB_CASE_LIST, list);
       }

       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 批次作業明細檔頁面
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<CheckJobCase> getCheckJobCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getCheckJobCaseList() ...");

           HttpSession session = request.getSession();
           return (List<CheckJobCase>) session.getAttribute(ConstantKey.CHECK_JOB_CASE_LIST);
       }

       /**
        * 批次作業 - 批次月處理作業 - 查詢結果清單<br> 批次作業明細檔頁面
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeCheckJobCaseList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeCheckJobCaseList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.CHECK_JOB_CASE_LIST);
       }
       
       /**
        * 列印作業- 轉呆帳核定清單 - 查詢結果清單
        * 將 查詢結果清單 存到 Session 中
        * @param request
        */
       public static void setOtherRpt08DetailList(List<OtherRpt08Case> list, HttpServletRequest request){
    	  
    	   log.debug("執行 CaseSessionHelper.setOtherRpt08DetailList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.OTHER_RPT08_DETAIL_CASE_LIST, list);
       }
       
       /**
        * 列印作業- 轉呆帳核定清單 - 查詢結果清單
        * 自 Session 中取得 查詢結果清單
        * @param request
        */
       public static List<OtherRpt08Case> getOtherRpt08DetailList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getOtherRpt08DetailList() ...");

           HttpSession session = request.getSession();
           return (List<OtherRpt08Case>) session.getAttribute(ConstantKey.OTHER_RPT08_DETAIL_CASE_LIST);
       }
       
       /**
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeOtherRpt08DetailList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeOtherRpt08DetailList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.OTHER_RPT08_DETAIL_CASE_LIST);
       }
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<PaymentStageDtlCase> getPaymentInterestDetailListForDb(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getPaymentInterestDetailListForDb() ...");

           HttpSession session = request.getSession();
           return (List<PaymentStageDtlCase>) session.getAttribute(ConstantKey.PAYMENT_INTEREST_DETAIL_CASE_LIST_DB);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中刪除查詢結果清單
        * 
        * @param request
        * @return
        */
       public static void removePaymentInterestDetailListForDb(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getPaymentInterestDetailListForDb() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.PAYMENT_INTEREST_DETAIL_CASE_LIST_DB);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setPaymentInterestDetailListForDb(List<PaymentStageDtlCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getPaymentInterestDetailListForDb() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.PAYMENT_INTEREST_DETAIL_CASE_LIST_DB, list);
       }
       
       /**
        * 繳款單作業 - 列印作業 
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<PaymentReprintCase> getPaymentReprintList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getPaymentReprintList() ...");

           HttpSession session = request.getSession();
           return (List<PaymentReprintCase>) session.getAttribute(ConstantKey.PAYMENT_REPRINT_LIST);
       }
       
       /**
        * 繳款單作業 - 列印作業
        * 自 Session 中刪除查詢結果清單
        * 
        * @param request
        * @return
        */
       public static void removePaymentReprintList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removePaymentReprintList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.PAYMENT_REPRINT_LIST);
       }
       
       /**
        * 繳款單作業 - 列印作業
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setPaymentReprintList(List<PaymentReprintCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getPaymentInterestDetailListForDb() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.PAYMENT_REPRINT_LIST, list);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<PaymentStageDtlCase> getPaymentInterestDetailList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getPaymentInterestDetailList() ...");

           HttpSession session = request.getSession();
           return (List<PaymentStageDtlCase>) session.getAttribute(ConstantKey.PAYMENT_INTEREST_DETAIL_CASE_LIST);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中刪除查詢結果清單
        * 
        * @param request
        * @return
        */
       public static void removePaymentInterestDetailList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removePaymentInterestDetailList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.PAYMENT_INTEREST_DETAIL_CASE_LIST);
       }
       

       /**
        * 繳款單作業 - 開單維護作業 
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setPaymentInterestDetailList(List<PaymentStageDtlCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setPaymentInterestDetailList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.PAYMENT_INTEREST_DETAIL_CASE_LIST, list);
       }
       
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setPaymentAssignDetailList(List<PaymentStageDtlCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setPaymentAssignDetailList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.PAYMENT_ASSIGN_DETAIL_CASE_LIST, list);
       }
       
       
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<PaymentStageDtlCase> getPaymentAssignDetailList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getPaymentAssignDetailList() ...");

           HttpSession session = request.getSession();
           return (List<PaymentStageDtlCase>) session.getAttribute(ConstantKey.PAYMENT_ASSIGN_DETAIL_CASE_LIST);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中刪除查詢結果清單
        * 
        * @param request
        * @return
        */
       public static void removePaymentAssignDetailList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removePaymentAssignDetailList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.PAYMENT_ASSIGN_DETAIL_CASE_LIST);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setPaymentAssignLastList(List<PaymentStageDtlCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setPaymentAssignLastList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.PAYMENT_ASSIGN_LAST_CASE_LIST, list);
       }
       
       
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<PaymentStageDtlCase> getPaymentAssignLastList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getPaymentAssignLastList() ...");

           HttpSession session = request.getSession();
           return (List<PaymentStageDtlCase>) session.getAttribute(ConstantKey.PAYMENT_ASSIGN_LAST_CASE_LIST);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中刪除查詢結果清單
        * 
        * @param request
        * @return
        */
       public static void removePaymentAssignLastList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removePaymentAssignLastList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.PAYMENT_ASSIGN_LAST_CASE_LIST);
       }
       
       
       
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<PaymentProcessCase> getPaymentProcessDetailList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getPaymentProcessDetailList() ...");

           HttpSession session = request.getSession();
           return (List<PaymentProcessCase>) session.getAttribute(ConstantKey.PAYMENT_PROCESS_DETAIL_CASE_LIST);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中刪除查詢結果清單
        * 
        * @param request
        * @return
        */
       public static void removePaymentProcessDetailList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removePaymentProcessDetailList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.PAYMENT_PROCESS_DETAIL_CASE_LIST);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setPaymentProcessDetailList(List<PaymentProcessCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setPaymentProcessDetailList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.PAYMENT_PROCESS_DETAIL_CASE_LIST, list);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<PaymentProcessQueryCase> getPaymentProcessQueryList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getPaymentProcessQueryList() ...");

           HttpSession session = request.getSession();
           return (List<PaymentProcessQueryCase>) session.getAttribute(ConstantKey.PAYMENT_PROCESS_QUERY_CASE_LIST);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中刪除查詢結果清單
        * 
        * @param request
        * @return
        */
       public static void removePaymentProcessQueryList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removePaymentProcessQueryList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.PAYMENT_PROCESS_QUERY_CASE_LIST);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setPaymentProcessQueryList(List<PaymentProcessQueryCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setCheckJobCaseList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.PAYMENT_PROCESS_QUERY_CASE_LIST, list);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<PaymentProgressQueryCase> getPaymentQueryList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getPaymentQueryList() ...");

           HttpSession session = request.getSession();
           return (List<PaymentProgressQueryCase>) session.getAttribute(ConstantKey.PAYMENT_QUERY_LIST);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 自 Session 中刪除查詢結果清單
        * 
        * @param request
        * @return
        */
       public static void removePaymentQueryList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removePaymentQueryList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.PAYMENT_QUERY_LIST);
       }
       
       /**
        * 繳款單作業 - 開單維護作業 
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param dataList
        * @param request
        */
       public static void setPaymentQueryList(List<PaymentProgressQueryCase> list, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setPaymentQueryList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.PAYMENT_QUERY_LIST, list);
       }
      //----------------------------------------------------------------------------------------

       public static void setQryProcedureProgressList(List<QryProcedureCase> caseObj, HttpServletRequest request){
           log.debug("執行 CaseSessionHelper.setProcedureProgressList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.QRY_PROCEDURE_PROGRESS_CASE_LIST, caseObj);
       }    
       /**
        * 批次作業-年金統計執行作業 
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param request
        * @return
        */
       public static void setExecStatisticsDataList(List<Babatchjob> caseObj, HttpServletRequest request){
           log.debug("執行 CaseSessionHelper.setExecStatisticsDataList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.QRY_EXEC_STATISTICS_DATA_LIST, caseObj);
       }    
       /**
        * 批次作業-年金統計執行作業  
        * 自session中取得查詢結果
        * 
        * @param request
        * @return
        */
       public static List<Babatchjob> getExecStatisticsDataList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getExecStatisticsDataList() ...");

           HttpSession session = request.getSession();
           return (List<Babatchjob>) session.getAttribute(ConstantKey.QRY_EXEC_STATISTICS_DATA_LIST);
       }
       /**
        * 批次作業-年金統計執行作業  
        * 自 Session 中刪除查詢結果清單
        * 
        * @param request
        * @return
        */
       public static void removeExecStatisticsDataList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeExecStatisticsDataList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.QRY_EXEC_STATISTICS_DATA_LIST);
       }
       
       /**
        * 批次作業 - 補正核付資料作業 - 老年年金補正核付作業 <br>
        * 將相關 Case 加入 Session 中
        * 
        * @param caseData
        * @param request
        */
       public static void setCompPaymentCase(List<CompPaymentCase> caseData, HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.setCompPaymentCase() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.COMP_PAYMENT_CASE, caseData);
       }
       
       /**
        * 批次作業 - 補正核付資料作業 - 老年年金補正核付作業
        * 自 Session 中取得 查詢結果清單
        * 
        * @param request
        * @return
        */
       public static List<CompPaymentCase> getCompPaymentCase(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getCompPaymentCase() ...");

           HttpSession session = request.getSession();
           return (List<CompPaymentCase>) session.getAttribute(ConstantKey.COMP_PAYMENT_CASE);
       }
       
       /**
        * 批次作業 - 補正核付資料作業 - 老年年金補正核付作業
        * 將 查詢結果清單 自 Session 中移除
        * 
        * @param request
        */
       public static void removeCompPaymentCase(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeCompPaymentCase() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.COMP_PAYMENT_CASE);
       }
       /**
        * 批次作業-年金統計執行作業 
        * 將 查詢結果清單 存到 Session 中
        * 
        * @param request
        * @return
        */
       public static void setExecStatisticsDataDtlList(List<ExecStatisticsDtlCase> caseObj, HttpServletRequest request){
           log.debug("執行 CaseSessionHelper.setExecStatisticsDataDtlList() ...");

           HttpSession session = request.getSession();
           session.setAttribute(ConstantKey.QRY_EXEC_STATISTICS_DATA_DTL_LIST, caseObj);
       }    
       /**
        * 批次作業-年金統計執行作業  
        * 自session中取得查詢結果
        * 
        * @param request
        * @return
        */
       public static List<ExecStatisticsDtlCase> getExecStatisticsDataDtlList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.getExecStatisticsDataDtlList() ...");

           HttpSession session = request.getSession();
           return (List<ExecStatisticsDtlCase>) session.getAttribute(ConstantKey.QRY_EXEC_STATISTICS_DATA_DTL_LIST);
       }
       /**
        * 批次作業-年金統計執行作業  
        * 自 Session 中刪除查詢結果清單
        * 
        * @param request
        * @return
        */
       public static void removeExecStatisticsDataDtlList(HttpServletRequest request) {
           log.debug("執行 CaseSessionHelper.removeExecStatisticsDataDtlList() ...");

           HttpSession session = request.getSession();
           session.removeAttribute(ConstantKey.QRY_EXEC_STATISTICS_DATA_DTL_LIST);
       }
}
