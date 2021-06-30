package tw.gov.bli.ba.framework.helper;

import java.util.ResourceBundle;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import tw.gov.bli.ba.Global;

public class CustomMessageHelper {
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(Global.BA_RESOURCE_BUNDLE);

    /**
     * 訊息: 即時編審失敗
     * 
     * @return
     */
    public static ActionMessages getCheckMarkFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.checkMark.fail"));
        return msgs;
    }

    /**
     * 訊息: 銀行帳號前7碼錯誤
     * 
     * @return
     */
    public static ActionMessages getBankIdErrorMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.bankId.error"));
        return msgs;
    }

    /**
     * 訊息: 查詢之檔案已刪除
     * 
     * @return
     */
    public static ActionMessages getFileErrorMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.file.notExist"));
        return msgs;
    }

    /**
     * 訊息: 帳號錯誤, 請重新輸入
     * 
     * @return
     */
    public static ActionMessages getAccountNoErrorMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.accountNo.error"));
        return msgs;
    }

    /**
     * 訊息: 該筆資料為其他資料之具名領取者，不可刪除。
     * 
     * @return
     */
    public static ActionMessages getAccDataCantDeleteMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.accDataCantDelete"));
        return msgs;
    }

    /**
     * 訊息: 報表產製失敗
     * 
     * @return
     */
    public static ActionMessages getReportGenerateFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.reportGenerate.fail"));
        return msgs;
    }

    /**
     * 「{0}」輸入錯誤，請重新確認
     * 
     * @param fieldName 欄位名稱
     * @return
     */
    public static ActionMessages getInvalidFieldValueMessage(ActionMessage fieldName) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.invalidValue", resourceBundle.getString(fieldName.getKey())));
        return msgs;
    }
    
    /**
     * 本受理案件已登錄繼承人(受款人)資料，請先至『失能年金受款人資料更正』刪除繼承人(受款人)資料，始可清空「死亡日期」資料。
     * 
     * @return
     */
    public static ActionMessages getDeleteBenDataBeforeSaveMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.update.disabled.evtDieDate"));
        return msgs;
    }
    
    /**
     * 訊息: 調整指數未達5%或小於-5%，不可調整。
     * 
     * @return
     */
    public static ActionMessages getTotalCpiFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.totalCpi.fail"));
        return msgs;
    }    
    
    /**
     * 訊息: 核定年度必需小於等於系統年度。
     * 
     * @return
     */
    public static ActionMessages getOverYearFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.overYear.fail"));
        return msgs;
    }   
    
    /**
     * 訊息: 指數年度必需小於核定年度。
     * 
     * @return
     */
    public static ActionMessages getOverAdjYearFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.overAdjYear.fail"));
        return msgs;
    }   
    
    /**
     * 訊息: 指數年度不得小於等於已存在之指數年度。
     * 
     * @return
     */
    public static ActionMessages getMaxYearFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.maxYear.fail"));
        return msgs;
    }

    /**
     * 訊息: 審核作業完成
     * 
     * @return
     */
    public static ActionMessages getReviewSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.review.success"));
        return msgs;
    }
    
    /**
     * 訊息: 審核作業失敗
     * 
     * @return
     */
    public static ActionMessages getReviewFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.review.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 決行作業完成
     * 
     * @return
     */
    public static ActionMessages getDecisionSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.decision.success"));
        return msgs;
    }
    
    /**
     * 訊息: 決行作業失敗
     * 
     * @return
     */
    public static ActionMessages getDecisionFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.decision.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 前一筆資料的給付年月迄月為空，不得執行新增作業
     * 
     * @return
     */
    public static ActionMessages getCheckPayYmEFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.checkBeforePayYmE.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 加計金額需高於前一筆資料之加計金額。
     * 
     * @return
     */
    public static ActionMessages getCheckLastBasicAmt() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.checklastbasicamt.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 給付年月迄月必需大於等於給付年月起月。
     * 
     * @return
     */
    public static ActionMessages getCheckPayYmE() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.checkpayYmE.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 成長率不可小於 0。
     * 
     * @return
     */
    public static ActionMessages getGrowThrateError() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.checkGrowThrateError.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 此年度已做過調整，不可再進行調整作業。
     * 
     * @return
     */
    public static ActionMessages getCheckAdjMkFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.checkAdjMk.fail"));
        return msgs;
    }
    
    
    
    /**
     * 訊息: 無當年度物價指數調整明細資料，不可做調整
     * 
     * @return
     */
    public static ActionMessages getCheckCpiDltDataFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.checkCpiDltData.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 無符合資料，受款人死亡日期非空白
     * 
     * @return
     */
    public static ActionMessages getCheckOldAgeDeathRepayDataFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.checkOldAgeDeathRepayData.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 無符合資料，無退匯資料
     * 
     * @return
     */
    public static ActionMessages getOldAgeDeathRepayDataFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.oldAgeDeathRepayData2.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 無符合資料，該受款人無繼承人資料
     * 
     * @return
     */
    public static ActionMessages getHeirSeqNoExistDataFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.oldAgeDeathRepayData1.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 尚未勾選改匯之繼承人資料
     * 
     * @return
     */
    public static ActionMessages getCheckQryData4CheckIndexFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.checkQryData4CheckIndex.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 死亡改匯作業失敗
     * 
     * @return
     */
    public static ActionMessages getOldAgeDeathRepayFailMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.OldAgeDeathRepay.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 死亡改匯處理作業完成
     * 
     * @return
     */
    public static ActionMessages getOldAgeDeathRepaySuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.OldAgeDeathRepay.success"));
        return msgs;
    }
    
    /**
     * 訊息: 因出納系統的條件限制，本案件今日已有改匯或取消改匯的記錄，無法再進行改匯或取消改匯作業，請於明天再進行此作業。
     * 
     * @return
     */
    public static ActionMessages getCheckAfChkDateForCheckBoxMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.checkAfChkDateForCheckBox.fail"));
        return msgs;
    }
    
    /**
     * 訊息: 勾選的繼承人資料與目前已分派的改匯繼承人資料相同，請重新勾選資料。
     * 
     * @return
     */
    public static ActionMessages getCheckRepeatHeirMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("errors.checkRepeatHeir.fail"));
        return msgs;
    }

    /**
     * 訊息: 「受款人序XXXX的退匯金額已改變，請至受款人資料更正作業變更受款人序(改匯註銷再新增)，才可重新分派」。
     * 
     * @return
     */
    public static ActionMessages getRemitAmtDataForCheckAvgNumMessage(String heirSeqNo) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(heirSeqNo,false));
        return msgs;
    }
    
    /**
     * 訊息: "批次處理成功   "+procMsg。getUpdatePaidMarkMessage
     * 
     * @return
     */
    public static ActionMessages getBatchJobMessage(String procMsg) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(procMsg,false));
        return msgs;
    }
    
    /**
     * 訊息: "G1007 回押作業完成   "+procMsg。
     * 
     * @return
     */
    public static ActionMessages getUpdatePaidMarkMessage(String procMsg) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(procMsg,false));
        return msgs;
    }
    
    /**
     * 檔案下載失敗
     * 
     * @return
     */
    public static ActionMessages getDownloadErrorMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.downloadFile.fail"));
        return msgs;
    }
    
    /**
     * 同年月已有完成重新查核紀錄，請確認。
     * 
     * @return
     */
    public static ActionMessages getBareCheckDataCountMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.bareCheckDataCount.fail"));
        return msgs;
    }
    
    /**
     * 同年月已有完成重新查核紀錄，請確認。
     * 
     * @return
     */
    public static ActionMessages getAccountsReceivableDataCaseListMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.accountsReceivableDataCaseList.fail"));
        return msgs;
    }
    
    /**
     * 本日線上月核定作業已執行過。
     * 
     * @return
     */
    public static ActionMessages getMonthPerformedErrorMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.onlineMonthPerformed.fail"));
        return msgs;
    }
    
    /**
     * 本日線上月核定作業已執行過。
     * 
     * @return
     */
    public static ActionMessages getMonthBatchPerformedErrorMessage(String procTypeStr) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.monthPerformed.fail", procTypeStr));
        return msgs;
    }
    
    /**
     * 月核定案件僅能輸入前一核定年月。
     * 
     * @return
     */
    public static ActionMessages getCheckMonthErrorMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.checkmonth.fail"));
        return msgs;
    }
    
    /**
     * 線上月試核作業執行完成。
     * 
     * @return
     */
    public static ActionMessages getMonthCheckSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.monthCheck.success"));
        return msgs;
    }
    
    /**
     * 線上月試核作業執行完成。
     * 
     * @return
     */
    public static ActionMessages getMonthSuccessMessage() {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.month.success"));
        return msgs;
    }
    
    /**
     * 訊息: 受理編號XXXXXXXXXXXX於核定年月XXXXXXXX以核付，不可編審。
     * 
     * @return
     */
    public static ActionMessages getMonthApprovedFailMessage(String apNoMsg) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.monthApproved.fail", apNoMsg));
        return msgs;
    }
    
    /**
     * 訊息: 【XX年金XX核定年月批次月處理作業(月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定))作業排程成功】。
     * 
     * @return
     */
    public static ActionMessages getMonthScheduleBatchSuccessMessage(String scheduleMsg) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg.monthScheduleBatch.success", scheduleMsg));
        return msgs;
    }
    
    /**
     * 訊息: 【XX年金XX核定年月批次月處理作業(月處理類型(2:批次月試核 3:第一次批次月核定 4:第二次批次月核定))作業排程失敗】。
     * 
     * @return
     */
    public static ActionMessages getMonthScheduleBatchFailMessage(String scheduleMsg) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.monthScheduleBatch.fail", scheduleMsg));
        return msgs;
    }
    
    /**
     * 申請年度{0}年資料已存在。
     * 
     * @return
     */
    public static ActionMessages getCpiDtlSaveErrorMessage(String issuYear, String adjYear) {
        ActionMessages msgs = new ActionMessages();
        msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.cpiDtlSave.fail", adjYear, issuYear));
        return msgs;
    }
    
    /**
     * 月核定檢核確認作業尚未確認，不得產製媒體檔。
     * 
     * @return
     */
    public static ActionMessages getMediaBatchCountErrorMessage() {
    	 ActionMessages msgs = new ActionMessages();
         msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.mediaBatchCount.fail"));
         return msgs;
    }
    
}
