package tw.gov.bli.ba.framework.helper;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class DatabaseMessageHelper {

    /**
     * 訊息: 資料查詢成功
     * 
     * @return
     */
    public static ActionMessages getQuerySuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.querySuccess"));
        return msgs;
    }

    /**
     * 訊息: E1004 資料查詢失敗
     * 
     * @return
     */
    public static ActionMessages getQueryFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.queryFail"));
        return msgs;
    }
    /**
     * 訊息: 本金期數與利息期數已修正過，若需取得新的利息資料，請至前頁面修改利息試算資料點選利息計算
     * 
     * @return
     */
    public static ActionMessages getInterestTryMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.interestTryMessage"));
        return msgs;
    }
    /**
     * 訊息: E1004 報表產製失敗
     * 
     * @return
     */
    public static ActionMessages getReportFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.queryFail"));
        return msgs;
    }

    /**
     * 訊息: G0001 資料新增成功
     * 
     * @return
     */
    public static ActionMessages getSaveSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.saveSuccess"));
        return msgs;
    }

    /**
     * 訊息: E1001 資料新增失敗
     * 
     * @return
     */
    public static ActionMessages getSaveFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.saveFail"));
        return msgs;
    }

    /**
     * 訊息: G0002 資料更新成功
     * 
     * @return
     */
    public static ActionMessages getUpdateSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.updateSuccess"));
        return msgs;
    }

    /**
     * 訊息: E1002 資料更新失敗
     * 
     * @return
     */
    public static ActionMessages getUpdateFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.updateFail"));
        return msgs;
    }

    /**
     * 訊息: G0003 資料刪除成功
     * 
     * @return
     */
    public static ActionMessages getDeleteSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.deleteSuccess"));
        return msgs;
    }

    /**
     * 訊息: E1003 資料刪除失敗
     * 
     * @return
     */
    public static ActionMessages getDeleteFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.deleteFail"));
        return msgs;
    }

    /**
     * 訊息: W0040 無查詢資料
     * 
     * @return
     */
    public static ActionMessages getNoResultMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noResult"));
        return msgs;
    }

    /**
     * 訊息: G1001 受理編號：{受理編號}資料新增成功
     * 
     * @return
     */
    public static ActionMessages getReceiptSaveSuccessMessage(String apNo) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.receiptSaveSuccess", apNo));
        return msgs;
    }

    /**
     * 訊息: G1001 受理編號：{受理編號} 資料新增成功(BC：{BC受理編號|錯誤訊息})
     * 
     * @return
     */
    public static ActionMessages getReceiptSaveSuccessMessage(String apNo, String bcApNo) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.receiptSaveSuccessWithBcApNo", apNo, bcApNo));
        return msgs;
    }

    /**
     * 訊息: G1002 受理編號：{受理編號}資料更新成功
     * 
     * @return
     */
    public static ActionMessages getReceiptUpdateSuccessMessage(String apNo) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.receiptUpdateSuccess", apNo));
        return msgs;
    }
    /**
     * 訊息: 查無{年度}對應之利率資料
     * 
     * @return
     */
    public static ActionMessages getNoRateMessage(String chtYy) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.getNoRateMessage", chtYy));
        return msgs;
    }
    /**
     * 訊息: G1003 受理編號：{受理編號}資料刪除成功
     * 
     * @return
     */
    public static ActionMessages getReceiptDeleteSuccessMessage(String apNo) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.receiptDeleteSuccess", apNo));
        return msgs;
    }

    /**
     * 訊息: E0069 該筆資料已存在
     * 
     * @return
     */
    public static ActionMessages getNoResultToExistMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noResultToExist"));
        return msgs;
    }

    /**
     * 訊息: G0017 註銷成功
     * 
     * @return
     */
    public static ActionMessages getCancelSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.cancelSuccess"));
        return msgs;
    }

    /**
     * 訊息: E1005 註銷失敗
     * 
     * @return
     */
    public static ActionMessages getCancelFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.cancelFail"));
        return msgs;
    }

    /**
     * 訊息: W1002 案件狀態已變更，請確認後重新執行
     * 
     * @return
     */
    public static ActionMessages getStatusChangeMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.statusChange"));
        return msgs;
    }

    /**
     * 訊息: G1004 資料更新成功！案件狀態已變更，請重新執行
     * 
     * @return
     */
    public static ActionMessages getSaveSuccessStatusChangeMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.saveSuccessStatusChange"));
        return msgs;
    }

    /**
     * 訊息: W1003 無此受理號碼或尚未產生核定資料！
     * 
     * @return
     */
    public static ActionMessages getNoResultForApNoMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noResultForApNo"));
        return msgs;
    }

    /**
     * 訊息: 批次處理成功
     * 
     * @return
     */
    public static ActionMessages getBatchJobSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.batchJobSuccess"));
        return msgs;
    }

    /**
     * 訊息: 批次處理失敗
     * 
     * @return
     */
    public static ActionMessages getBatchJobFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.batchJobFail"));
        return msgs;
    }

    /**
     * 訊息: 新增失敗，此受理號碼已存在！
     * 
     * @return
     */
    public static ActionMessages getApNoExistMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.ApNoExist"));
        return msgs;
    }

    /**
     * 訊息: 受理編號編碼規則錯誤
     * 
     * @return
     */
    public static ActionMessages getApNoErrorMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.ApNoError"));
        return msgs;
    }

    /**
     * 訊息: 無該筆受理編號
     * 
     * @return
     */
    public static ActionMessages getApNoNonExistErrorMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.ApNoNonExist"));
        return msgs;
    }

    /**
     * 訊息: E1008 受款人資料已存在
     * 
     * @return
     */
    public static ActionMessages getBenNameExistMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.benNameToExist"));
        return msgs;
    }

    /**
     * 訊息: E1009 法定代理人為事故者或受款人
     * 
     * @return
     */
    public static ActionMessages getGrdNameExistMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.grdNameToExist"));
        return msgs;
    }
    
    /**
     * 訊息: E1012 法定代理人為事故者或受款人
     * 
     * @return
     */
    public static ActionMessages getGrdDeadMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.saveFail.grdDead"));
        return msgs;
    }
    

    /**
     * 訊息: G1001 受理編號：{受理編號} SEQ={序號} 資料新增成功
     * 
     * @return
     */
    public static ActionMessages getReceiptSaveSuccessWithSeqNoMessage(String apNo, String seqNo) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.receiptSaveSuccessWithSeqNo", apNo, seqNo));
        return msgs;
    }

    /**
     * 訊息: G1002 受理編號：{受理編號}資料更新成功
     * 
     * @return
     */
    public static ActionMessages getReceiptUpdateSuccessWithSeqNoMessage(String apNo, String seqNo) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.receiptUpdateSuccessWithSeqNo", apNo, seqNo));
        return msgs;
    }

    /**
     * 訊息: G1003 受理編號：{受理編號}資料刪除成功
     * 
     * @return
     */
    public static ActionMessages getReceiptDeleteSuccessWithSeqNoMessage(String apNo, String seqNo) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.receiptDeleteSuccessWithSeqNo", apNo, seqNo));
        return msgs;
    }

    /**
     * 訊息: G1007 回押作業完成
     * 
     * @return
     */
    public static ActionMessages getUpdatePaidMarkSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.updatePaidMarkSuccess"));
        return msgs;
    }

    /**
     * 訊息: W1004 本案已決行！
     * 
     * @return
     */
    public static ActionMessages getAlreadyDecisionMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.alreadyDecision"));
        return msgs;
    }

    /**
     * 訊息: G1006 編審註記程度調整作業完成
     * 
     * @return
     */
    public static ActionMessages getCheckMarkLevelAdjustSaveSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.checkMarkLevelAdjust.saveSuccess"));
        return msgs;
    }

    /**
     * 訊息: E1007 編審註記程度調整作業失敗
     * 
     * @return
     */
    public static ActionMessages getCheckMarkLevelAdjustSaveFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.checkMarkLevelAdjust.saveFail"));
        return msgs;
    }

    /**
     * 訊息: E1010 資料存檔失敗(眷屬/遺屬資料已存在)
     * 
     * @return
     */
    public static ActionMessages getSaveFailCauseFamDataExistMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.saveFail.famDataExist"));
        return msgs;
    }

    /**
     * 訊息: E1011 資料存檔失敗(眷屬/遺屬資料與事故者相同)
     * 
     * @return
     */
    public static ActionMessages getSaveFailCauseDupEvtFamDataMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.saveFail.DupEvtFamData"));
        return msgs;
    }

    /**
     * 訊息: G1010 受理編號：{受理編號}資料更新成功！(但給付資料與出納系統的改匯記錄不符，請確認)
     * 
     * @return
     */
    public static ActionMessages getReceiptUpdateSuccessButDataNotMatchMessage(String apNo) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.receiptUpdateSuccessButDataNotMatch", apNo));
        return msgs;
    }
    
    /**
     * 訊息: G1008 資料更新成功
     * 
     * @return
     */
    public static ActionMessages getPaymentReviewSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.paymentReviewSuccess"));
        return msgs;
    }
    /**
     * 訊息: E0069 該核定年月、核定日期與給付別正在排程中，請稍待
     * 
     * @return
     */
    public static ActionMessages getDuplicateWaiting() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.duplicateWaiting"));
        return msgs;
    }
    
    /**
     * 訊息: E0071 該核定日期與給付別正在排程中，請勿重複排程
     * 
     * @return
     */
    public static ActionMessages getDuplicateMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.duplicateSchedule"));
        return msgs;
    }
    
    /**
     * 訊息: E0073 系統日期介於批次月試核及月核定期間，無法執行此排程動作。
     * 
     * @return
     */
    public static ActionMessages getBetweenChkdateMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.betweenChkdate"));
        return msgs;
    }
    /**
     * 訊息: E0072  該核定日期與給付別已記入排程，請至進度查詢查詢處理狀況
     * 
     * @return
     */
    public static ActionMessages getScheduleSuccssMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.scheduleSuccess"));
        return msgs;
    }
    

    /**
     * 訊息: E0074  該核定日期與給付別已記入排程
     * 
     * @return
     */
    public static ActionMessages getScheduleOperationSuccess(){
    	ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.scheduleOperationSuccess"));
        return msgs;
    }
    
    /**
     * 訊息: E0075 該 Stored Procedure 已記入排程
     * 
     * @return
     */
    public static ActionMessages getSetScheduleSuccess(){
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.setScheduleSuccess"));
        return msgs;
    }    
    
    /**
     * 訊息: 查無資料
     * 
     * @return
     */
    public static ActionMessages getNoDataMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.NoMediaDownloadData"));
        return msgs;
    }
    
    /**
     * 訊息: 查無應收未收檔資料資料
     * 
     * @return
     */
    public static ActionMessages getNoUnacpdtlDataMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.NoUnacpdtlData"));
        return msgs;
    }
    /**
     * 訊息: 無待處理資料
     * 
     * @return
     */
    public static ActionMessages getNoProcDataMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.NoProcDataMessage"));
        return msgs;
    }
    /**
    * 訊息: 轉催資料處理完成
    * 
    * @return
    */
   public static ActionMessages getProcDataMessage() {
       ActionMessages msgs = new ActionMessages();
       msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.ProcDataMessage"));
       return msgs;
   }
   
   /**
    * 訊息: 轉催資料處理失敗
    * 
    * @return
    */
   public static ActionMessages getProcDataErrorMessage() {
       ActionMessages msgs = new ActionMessages();
       msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.ProcDataErrorMessage"));
       return msgs;
   }
   
   /**
    * 訊息: 今日已執行過此功能，若要列印相關報表，請執行補印功能
    * 
    * @return
    */
   public static ActionMessages getProcDulpicate() {
       ActionMessages msgs = new ActionMessages();
       msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.ProcDulpicate"));
       return msgs;
   }
   /**
    * 訊息: 查無繳款單資料，請先點選確認執行繳款單
    * 
    * @return
    */
   public static ActionMessages getSaveDataMessage(){
	   ActionMessages msgs = new ActionMessages();
       msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.saveDataFirst"));
       return msgs;
   }

    /**
     * 訊息：{受理編號} 未結案且無狀態為結案之遺屬
     * 
     * @return
     */
    public static ActionMessages getNoDataWithApNoForCloseStatusAlterationMessage(String apNo) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noDataWithApNoForCloseStatusAlteration", apNo));
        return msgs;
    }

    /**
     * 訊息：{受理編號} 尚未結案或查無該受理編號的結案資料
     * 
     * @return
     */
    public static ActionMessages getNoDataWithApNoForOldAgeAndDisabledCloseStatusAlterationMessage(String apNo) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noDataWithApNoForOldAgeAndDisabledCloseStatusAlteration", apNo));
        return msgs;
    }
    
    /**
     * 訊息：查無該處理年月的勞保年金統計檔資料
     * 
     * @return
     */
    public static ActionMessages getExecStatisticsDataEmpty(String issuYm) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noExecStatisticsDataEmpty", issuYm));
        return msgs;
    }
    
    /**
     * 訊息：查無該勞保年金統計檔明細步驟
     * 
     * @return
     */
    public static ActionMessages getExecStatisticsDtlDataEmpty(String issuYm) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noExecStatisticsDtlDataEmpty", issuYm));
        return msgs;
    }
    
    /**
     * 訊息: 該處理年月已產生勞保年金統計檔資料，不可重複執行勞保年金統計作業
     * 
     * @return
     */
    public static ActionMessages getScheduleOperationDupre(){
    	ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.scheduleOperationDupre"));
        return msgs;
    }
     
    /**
    * 訊息: 該處理日期已記入排程
    * 
    * @return
    */
   public static ActionMessages getScheduleOperation(){
   	ActionMessages msgs = new ActionMessages();
       msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.scheduleOperation"));
       return msgs;
   }

    /**
     * 訊息：今日已逾期或未達可執行補正核付新增之日，不可執行老年年金補正核付新增作業
     * 
     * @return
     */
    public static ActionMessages getnoDataWithPayCodeForOldAgePaymentMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noDataWithPayCodeForOldAgePayment"));
        return msgs;
    }
    
    /**
     * 訊息：本案件已由系統核付，或查無可補正之核付資料，或查無該受理案件資料，不可執行老年年金補正核付新增作業
     * 
     * @return
     */
    public static ActionMessages getnoDataWithApNoForOldAgePaymentMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.getnoDataWithApNoForOldAgePaymentMessage"));
        return msgs;
    }
    
    /**
     * 訊息：本案件已由系統核付或查無該受理案件的人工補正核付資料，不可執行老年年金補正核付修改作業
     * 
     * @return
     */
    public static ActionMessages getnoDataWithApNoForOldAgePaymentModifyMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.getnoDataWithApNoForOldAgePaymentModifyMessage"));
        return msgs;
    }
    
    /**
     * 訊息：老年年金補正核付作業修改成功
     * 
     * @return
     */
    public static ActionMessages successModifyForOldAgePayment() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.successModifyForOldAgePayment"));
        return msgs;
    }
    
    /**
     * 訊息：老年年金補正核付作業新增成功
     * 
     * @return
     */
    public static ActionMessages successInsertForOldAgePayment() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.successInsertForOldAgePayment"));
        return msgs;
    }
    
    /**
     * 訊息：本案件已有系統核付資料，不可執行老年年金補正核付新增作業
     * 
     * @return
     */
    public static ActionMessages noExecuteOldAgePayment() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noExecuteOldAgePayment"));
        return msgs;
    }
    
    /**
     * 訊息：今日已逾期或未達可執行補正核付新增之日，不可執行失能年金補正核付新增作業
     * 
     * @return
     */
    public static ActionMessages getnoDataWithPayCodeForDisabledPaymentMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noDataWithPayCodeForDisabledPayment"));
        return msgs;
    }
    
    /**
     * 訊息：本案件已有系統核付資料，不可執行失能年金補正核付新增作業
     * 
     * @return
     */
    public static ActionMessages noExecuteDisabledPayment() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noExecuteDisabledPayment"));
        return msgs;
    }
    
    /**
     * 訊息：本案件已由系統核付，或查無可補正之核付資料，或查無該受理案件資料，不可執行失能年金補正核付新增作業
     * 
     * @return
     */
    public static ActionMessages getnoDataWithApNoForDisabledPaymentMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.getnoDataWithApNoForDisabledPaymentMessage"));
        return msgs;
    }
    
    /**
     * 訊息：失能年金補正核付作業修改成功
     * 
     * @return
     */
    public static ActionMessages successModifyForDisabledPayment() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.successModifyForDisabledPayment"));
        return msgs;
    }
    
    /**
     * 訊息：失能年金補正核付作業新增成功
     * 
     * @return
     */
    public static ActionMessages successInsertForDisabledPayment() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.successInsertForDisabledPayment"));
        return msgs;
    }
    
    /**
     * 訊息：今日已逾期或未達可執行補正核付新增之日，不可執行遺屬年金補正核付新增作業
     * 
     * @return
     */
    public static ActionMessages getnoDataWithPayCodeForSurvivorPaymentMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noDataWithPayCodeForSurvivorPayment"));
        return msgs;
    }
    
    /**
     * 訊息：本案件已有系統核付資料，不可執行遺屬年金補正核付新增作業
     * 
     * @return
     */
    public static ActionMessages noExecuteSurvivorPayment() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.noExecuteSurvivorPayment"));
        return msgs;
    }
    
    /**
     * 訊息：本案件已由系統核付，或查無可補正之核付資料，或查無該受理案件資料，不可執行遺屬年金補正核付新增作業
     * 
     * @return
     */
    public static ActionMessages getnoDataWithApNoForSurvivorPaymentMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.getnoDataWithApNoForSurvivorPaymentMessage"));
        return msgs;
    }
    
    /**
     * 訊息：遺屬年金補正核付作業修改成功
     * 
     * @return
     */
    public static ActionMessages successModifyForSurvivorPayment() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.successModifyForSurvivorPayment"));
        return msgs;
    }
    
    /**
     * 訊息：遺屬年金補正核付作業新增成功
     * 
     * @return
     */
    public static ActionMessages successInsertForSurvivorPayment() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.successInsertForSurvivorPayment"));
        return msgs;
    }

    /**
     * 訊息：執行檢核作業完成
     *
     * @return
     */
    public static ActionMessages successUpload() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.successUpload"));
        return msgs;
    }

    /**
     * 訊息：執行檢核作業失敗
     *
     * @return
     */
    public static ActionMessages failUpload() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.failUpload"));
        return msgs;
    }

    /**
     * 訊息：傳送作業成功
     *
     */
    public static ActionMessages successUpload2() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.successUpload2"));
        return msgs;
    }

    /**
     * 訊息：傳送作業失敗
     *
     */
    public static ActionMessages failUpload2() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.failUpload2"));
        return msgs;
    }
    
    /**
     * 訊息: E0072  已記入排程，請至進度查詢查詢處理狀況
     * 
     * @return
     */
    public static ActionMessages repReportgeneration() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.db.repReportgeneration"));
        return msgs;
    }
}
