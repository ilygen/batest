package tw.gov.bli.ba.update.actions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import com.iisi.SecureToken;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.SurvivorReviewRpt01Case;
import tw.gov.bli.ba.rpt.report.SurvivorReviewRpt01Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.SurvivorApplicationDataUpdateCase;
import tw.gov.bli.ba.update.cases.SurvivorApplicationDataUpdateDupeIdnCase;
import tw.gov.bli.ba.update.forms.SurvivorApplicationDataUpdateForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp bamo0d231c.jsp bamo0d233c.jsp)
 * 
 * @author Goston
 */
public class SurvivorApplicationDataUpdateAction extends DispatchAction {
    private static Log log = LogFactory.getLog(SurvivorApplicationDataUpdateAction.class);

    private static final String FORWARD_DUPE_LIST_PAGE = "dupeListPage";
    private static final String FORWARD_DETAIL_PAGE = "detailPage";

    private UpdateService updateService;
    private SelectOptionService selectOptionService;
    private RptService rptService;

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d230c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 查詢頁面 SurvivorApplicationDataUpdateAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        SurvivorApplicationDataUpdateForm queryForm = (SurvivorApplicationDataUpdateForm) form;

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeSurvivorApplicationDataUpdateCase(request);

            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(queryForm.getApNo1())) + StringUtils.trimToEmpty(queryForm.getApNo2()) + StringUtils.trimToEmpty(queryForm.getApNo3()) + StringUtils.trimToEmpty(queryForm.getApNo4());

            if (StringUtils.length(apNo) != ConstantKey.APNO_LENGTH) { // 受理編號長度不滿 12 碼則不做查詢
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 取得 案件 資料
            SurvivorApplicationDataUpdateCase caseData = updateService.getSurvivorApplicationDataUpdateData(apNo);

            // 無查詢資料
            if (caseData == null) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 取得"發放方式"可否修改狀態
            caseData.setIsShowInterValMonth(updateService.chkInterValMonthStatus(caseData.getApNo()));

            // 將 CaseData 塞到 Session 中
            CaseSessionHelper.setSurvivorApplicationDataUpdateCase(caseData, request);

            // Form 值重置
            queryForm.clearFields();

            // Form 下拉式選單初始化
            // [
            queryForm.setCountryOptionList(selectOptionService.getCountry());// 國籍下拉選單
            queryForm.setApItemOptionList(selectOptionService.getSurvivorApplicationDataUpdateApItemList()); // 申請項目下拉選單
            queryForm.setEvTypOptionList(selectOptionService.getEvTypOptionList()); // 傷病分類下拉選單
            queryForm.setScloseCauseOptionList(selectOptionService.getScloseCauseOptionList()); // 結案原因下拉選單
            // ]

            // 取得 可輸入的 核定通知書格式 值塞入 Form 中 (用於頁面檢核)
            // [
            List<String> notifyFormList = selectOptionService.selectNotifyFormBy(apNo);
            StringBuffer validNotifyFormValue = new StringBuffer("");
            for (String notifyFormValue : notifyFormList) {
                validNotifyFormValue.append("," + notifyFormValue);
            }
            if (StringUtils.length(validNotifyFormValue.toString()) > 0)
                queryForm.setValidNotifyFormValue(StringUtils.substring(validNotifyFormValue.toString(), 1, validNotifyFormValue.toString().length()));
            // ]

            // 將 CaseData 的資料塞到 Form 中
            // [
            BeanUtility.copyPropertiesForUpdate(queryForm, caseData, ConstantKey.OLD_FIELD_PREFIX);

            // 西元日期轉民國日期
            queryForm.setEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
            queryForm.setEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
            queryForm.setAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
            queryForm.setChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月
            queryForm.setJudgeDate(DateUtility.changeDateType(caseData.getJudgeDate())); // 判決日期
            queryForm.setOldEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
            queryForm.setOldEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
            queryForm.setOldAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
            queryForm.setOldChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月
            queryForm.setOldJudgeDate(DateUtility.changeDateType(caseData.getJudgeDate())); // 判決日期
            queryForm.setTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
            queryForm.setOldTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
            queryForm.setMonNotifyingMk(caseData.getMonNotifyingMk());  // 寄發月通知表
            queryForm.setOldMonNotifyingMk(caseData.getMonNotifyingMk());  // 寄發月通知表

            // ]

            log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 查詢頁面 SurvivorApplicationDataUpdateAction.doQuery() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("SurvivorApplicationDataUpdateAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d231c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 明細頁面 SurvivorApplicationDataUpdateAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        SurvivorApplicationDataUpdateForm updateForm = (SurvivorApplicationDataUpdateForm) form;

        try {
            // 自 Session 中取出 Case 資料
            SurvivorApplicationDataUpdateCase caseData = CaseSessionHelper.getSurvivorApplicationDataUpdateCase(request);

            if (caseData == null) {
                // 設定更新失敗訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            // 傷病原因 檢核
            if ((updateForm.getEvTyp().equals("1") || updateForm.getEvTyp().equals("2")) && (updateForm.getEvAppTyp().equals("1") || updateForm.getEvAppTyp().equals("2"))
                            || (updateForm.getEvTyp().equals("3") || updateForm.getEvTyp().equals("4")) && (updateForm.getEvAppTyp().equals("3") || updateForm.getEvAppTyp().equals("4"))
                            || (updateForm.getEvTyp().equals("1") || updateForm.getEvTyp().equals("2")) && (updateForm.getEvAppTyp().equals("3") || updateForm.getEvAppTyp().equals("4"))){
                if (!updateService.isValidEvCode(updateForm.getEvTyp(), updateForm.getEvCode())) {
                    // 設定「傷病原因」輸入錯誤 訊息
                    saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.survivor.evCode")));
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
                }
            } else if ((updateForm.getEvTyp().equals("3") || updateForm.getEvTyp().equals("4")) && (updateForm.getEvAppTyp().equals("1") || updateForm.getEvAppTyp().equals("2"))){
                if (!updateService.isValidEvCode2(updateForm.getEvAppTyp(), updateForm.getEvCode())) {
                    // 設定「傷病原因」輸入錯誤 訊息
                    saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.survivor.evCode")));
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
                }                
            }
            // 受傷部位 檢核
            if (!updateService.isValidCriInPart(updateForm.getCriInPart1())) {
                // 設定「受傷部位1」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.survivor.criInPart1")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInPart(updateForm.getCriInPart2())) {
                // 設定「受傷部位2」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.survivor.criInPart2")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInPart(updateForm.getCriInPart3())) {
                // 設定「受傷部位3」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.survivor.criInPart3")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            // 媒介物 檢核
            if (!updateService.isValidCriMedium(updateForm.getCriMedium())) {
                // 設定「媒介物」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.survivor.criMedium")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            // 國際疾病代碼 檢核
            if (!updateService.isValidCriInJnme(updateForm.getCriInJnme1())) {
                // 設定「國際疾病代碼1」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.survivor.criInJnme1")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJnme(updateForm.getCriInJnme2())) {
                // 設定「國際疾病代碼2」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.survivor.criInJnme2")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJnme(updateForm.getCriInJnme3())) {
                // 設定「國際疾病代碼3」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.survivor.criInJnme3")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJnme(updateForm.getCriInJnme4())) {
                // 設定「國際疾病代碼4」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.survivor.criInJnme4")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            String apNo = caseData.getApNo(); // 受理編號 - 供後續使用
            String apNoString = updateForm.getApNoString(); // 受理編號 - 供後續使用

            // 將 Form 的值設定到 Case
            // [
            // BAAPPBASE
            caseData.setEvtNationTpe(updateForm.getEvtNationTpe()); // 事故者國籍別

            if (StringUtils.equals(updateForm.getEvtNationTpe(), "1"))
                caseData.setEvtNationCode("000"); // 事故者國籍
            else
                caseData.setEvtNationCode(updateForm.getEvtNationCode()); // 事故者國籍

            if (StringUtils.equals(updateForm.getEvtNationTpe(), "1"))
                caseData.setEvtSex(StringUtils.substring(updateForm.getEvtIdnNo(), 1, 2)); // 事故者性別
            else
                caseData.setEvtSex(updateForm.getEvtSex()); // 事故者性別

            caseData.setEvtName(updateForm.getEvtName()); // 事故者姓名
            caseData.setEvtIdnNo(updateForm.getEvtIdnNo()); // 事故者身分證號
            caseData.setEvtBrDate((StringUtils.length(updateForm.getEvtBrDate()) == 7 || StringUtils.equals(StringUtils.substring(updateForm.getEvtBrDate(), 0, 1), "-")) ? DateUtility.changeDateType(updateForm.getEvtBrDate()) : updateForm
                            .getEvtBrDate()); // 事故者出生日期
            caseData.setEvtDieDate((StringUtils.length(updateForm.getEvtDieDate()) == 7) ? DateUtility.changeDateType(updateForm.getEvtDieDate()) : updateForm.getEvtDieDate()); // 事故者死亡日期
            caseData.setAppDate((StringUtils.length(updateForm.getAppDate()) == 7) ? DateUtility.changeDateType(updateForm.getAppDate()) : updateForm.getAppDate()); // 申請日期
            caseData.setApUbno(updateForm.getApUbno()); // 申請單位保險證號
            caseData.setApubnock(updateForm.getApubnock()); // 申請單位保險證號檢查碼
            caseData.setLsUbno(updateForm.getLsUbno()); // 最後單位保險證號 - 事故發生單位保險證號
            caseData.setLsUbnoCk(updateForm.getLsUbnoCk()); // 最後單位保險證號檢查碼
            caseData.setNotifyForm(updateForm.getNotifyForm()); // 核定通知書格式

            if (StringUtils.equals(updateForm.getLoanMk(), "1"))
                caseData.setLoanMk("1"); // 不須抵銷紓困貸款註記
            else
                caseData.setLoanMk("0"); // 不須抵銷紓困貸款註記

            caseData.setCloseCause(updateForm.getCloseCause()); // 結案原因
            caseData.setChoiceYm((StringUtils.length(updateForm.getChoiceYm()) == 5) ? DateUtility.changeChineseYearMonthType(updateForm.getChoiceYm()) : updateForm.getChoiceYm()); // 擇領起月
            caseData.setApItem(updateForm.getApItem()); // 申請項目
            caseData.setCutAmt(updateForm.getCutAmt());// 應扣失能金額
            caseData.setInterValMonth(updateForm.getInterValMonth());// 發放方式

            // BAAPPEXPAND
            caseData.setJudgeDate((StringUtils.length(updateForm.getJudgeDate()) == 7) ? DateUtility.changeDateType(updateForm.getJudgeDate()) : updateForm.getJudgeDate()); // 判決日期
            caseData.setEvAppTyp(updateForm.getEvAppTyp()); // 申請傷病分類
            caseData.setEvTyp(updateForm.getEvTyp()); // 傷病分類
            caseData.setEvCode(updateForm.getEvCode()); // 傷病原因
            caseData.setCriInPart1(updateForm.getCriInPart1()); // 受傷部位1
            caseData.setCriInPart2(updateForm.getCriInPart2()); // 受傷部位2
            caseData.setCriInPart3(updateForm.getCriInPart3()); // 受傷部位3
            caseData.setCriMedium(updateForm.getCriMedium()); // 媒介物
            caseData.setCriInJnme1(updateForm.getCriInJnme1()); // 國際疾病代碼1
            caseData.setCriInJnme2(updateForm.getCriInJnme2()); // 國際疾病代碼2
            caseData.setCriInJnme3(updateForm.getCriInJnme3()); // 國際疾病代碼3
            caseData.setCriInJnme4(updateForm.getCriInJnme4()); // 國際疾病代碼4
            caseData.setPrType(updateForm.getPrType()); // 先核普通
            caseData.setFamAppMk(updateForm.getFamAppMk());// 符合第 63 條之 1 第 3 項註記
            
            if (StringUtils.equals(updateForm.getMonNotifyingMk(), "Y"))
                caseData.setMonNotifyingMk("Y"); // 寄發月通知表
            else
                caseData.setMonNotifyingMk("N"); // 寄發月通知表
            
            if (updateForm.getCaseTyp().equals("2") && StringUtils.equals(updateForm.getIssuNotifyingMk(), "Y"))
                caseData.setIssuNotifyingMk("Y"); // 寄發核定通知書
            else
                caseData.setIssuNotifyingMk("N"); // 寄發核定通知書
                      
            // ]

            // 存檔
            updateService.updateSurvivorApplicationDataUpdateData(userData, caseData, updateForm);

            // 呼叫即時編審 WebService
            // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
            String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
            log.info("webServiceUrl: "+ webServiceUrl);
            String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
            try {
                SingleCheckMarkServiceHttpBindingStub binding;
                binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator().getSingleCheckMarkServiceHttpPort();
                returnCode = binding.doCheckMark(apNo,SecureToken.getInstance().getToken());
            }
            catch (Exception e) {
                log.error("SurvivorApplicationDataUpdateAction.doSave() 即時編審發生錯誤:" + ExceptionUtility.getStackTrace(e));
            }

            // 設定更新訊息
            if (StringUtils.equals(ConstantKey.DO_CHECK_MARK_FAIL, returnCode))
                saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage()); // 設定即時編審失敗訊息
            else
                saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(apNoString)); // 設定更新成功訊息

            // 將 CaseData 自 Session 移除
            CaseSessionHelper.removeSurvivorApplicationDataUpdateCase(request);

            // Form 值重置
            updateForm.clearFields();

            // 重新取得更新後的案件資料
            caseData = updateService.getSurvivorApplicationDataUpdateData(apNo);

            if (caseData != null) { // caseData 不為 null 表示案件可以繼續修改, 則頁面導回 Detail 頁面, 否則則導回 Query 頁面
                // 取得"發放方式"可否修改狀態
                caseData.setIsShowInterValMonth(updateService.chkInterValMonthStatus(caseData.getApNo()));

                // 將 CaseData 塞到 Session 中
                CaseSessionHelper.setSurvivorApplicationDataUpdateCase(caseData, request);

                // Form 值重置
                updateForm.clearFields();

                // Form 下拉式選單初始化
                // [
                updateForm.setCountryOptionList(selectOptionService.getCountry());// 國籍下拉選單
                updateForm.setApItemOptionList(selectOptionService.getSurvivorApplicationDataUpdateApItemList()); // 申請項目下拉選單
                updateForm.setEvTypOptionList(selectOptionService.getEvTypOptionList()); // 傷病分類下拉選單
                updateForm.setScloseCauseOptionList(selectOptionService.getScloseCauseOptionList()); // 結案原因下拉選單
                // ]

                // 取得 可輸入的 核定通知書格式 值塞入 Form 中 (用於頁面檢核)
                // [
                List<String> notifyFormList = selectOptionService.selectNotifyFormBy(apNo);
                StringBuffer validNotifyFormValue = new StringBuffer("");
                for (String notifyFormValue : notifyFormList) {
                    validNotifyFormValue.append("," + notifyFormValue);
                }
                if (StringUtils.length(validNotifyFormValue.toString()) > 0)
                    updateForm.setValidNotifyFormValue(StringUtils.substring(validNotifyFormValue.toString(), 1, validNotifyFormValue.toString().length()));
                // ]

                // 將 CaseData 的資料塞到 Form 中
                // [
                BeanUtility.copyPropertiesForUpdate(updateForm, caseData, ConstantKey.OLD_FIELD_PREFIX);

                // 西元日期轉民國日期
                updateForm.setEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
                updateForm.setEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
                updateForm.setAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
                updateForm.setChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月
                updateForm.setJudgeDate(DateUtility.changeDateType(caseData.getJudgeDate())); // 判決日期

                updateForm.setOldEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
                updateForm.setOldEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
                updateForm.setOldAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
                updateForm.setOldChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月
                updateForm.setOldJudgeDate(DateUtility.changeDateType(caseData.getJudgeDate())); // 判決日期

                updateForm.setTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
                updateForm.setOldTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
                // ]

                log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 明細頁面 SurvivorApplicationDataUpdateAction.doSave() 完成 ... ");
                return mapping.findForward(FORWARD_DETAIL_PAGE);
            }
            else {
                log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 明細頁面 SurvivorApplicationDataUpdateAction.doSave() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
            }
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("SurvivorApplicationDataUpdateAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d231c.jsp) <br>
     * 按下「註銷」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 明細頁面 SurvivorApplicationDataUpdateAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        SurvivorApplicationDataUpdateForm deleteForm = (SurvivorApplicationDataUpdateForm) form;

        try {
            // 自 Session 中取出 Case 資料
            SurvivorApplicationDataUpdateCase caseData = CaseSessionHelper.getSurvivorApplicationDataUpdateCase(request);

            if (caseData == null) {
                // 設定註銷失敗訊息
                saveMessages(session, DatabaseMessageHelper.getCancelFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
            }

            // 註銷
            updateService.deleteSurvivorApplicationDataUpdateData(userData, caseData.getApNo());

            // 設定註銷成功訊息
            saveMessages(session, DatabaseMessageHelper.getCancelSuccessMessage());

            // 將 CaseData 自 Session 移除
            CaseSessionHelper.removeSurvivorApplicationDataUpdateCase(request);

            // Form 值重置
            deleteForm.clearFields();

            log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 明細頁面 SurvivorApplicationDataUpdateAction.doDelete() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);
        }
        catch (Exception e) {
            // 設定註銷失敗訊息
            log.error("SurvivorApplicationDataUpdateAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getCancelFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d231c.jsp) <br>
     * 按下「選擇事故者姓名」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSelectEvtName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return mapping.findForward(FORWARD_DUPE_LIST_PAGE);
    }

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d233c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSelectDupe(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 身分證重號頁面 SurvivorApplicationDataUpdateAction.doSelectDupe() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        SurvivorApplicationDataUpdateForm dupeForm = (SurvivorApplicationDataUpdateForm) form;

        try {
            // 自 Session 中取出 Case 資料
            SurvivorApplicationDataUpdateCase caseData = CaseSessionHelper.getSurvivorApplicationDataUpdateCase(request);

            if (caseData == null) {
                // 設定更新失敗訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            int index = dupeForm.getRadioDupeIndex();

            if (index >= 0 && index < caseData.getDupeIdnList().size()) {
                SurvivorApplicationDataUpdateDupeIdnCase dupeIdnCase = caseData.getDupeIdnList().get(index);

                if (dupeIdnCase != null) {
                    String apNo = caseData.getApNo(); // 受理編號 - 供後續使用
                    String apNoString = caseData.getApNoString(); // 受理編號 - 供後續使用

                    if(StringUtils.isNotBlank(dupeIdnCase.getIdnNo()) && dupeIdnCase.getIdnNo().length() > 10){
                    dupeIdnCase.setIdnNoTrim(dupeIdnCase.getIdnNo().substring(0, 10));
                    }
                    // 存檔
                    updateService.updateSurvivorApplicationDataUpdateDupeIdnData(userData, dupeIdnCase);

                    // 呼叫即時編審 WebService
                    // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
                    String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
                    log.info("webServiceUrl: "+ webServiceUrl);
                    String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
                    try {
                        SingleCheckMarkServiceHttpBindingStub binding;
                        binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator().getSingleCheckMarkServiceHttpPort();
                        returnCode = binding.doCheckMark(apNo,SecureToken.getInstance().getToken());
                    }
                    catch (Exception e) {
                        log.error("SurvivorApplicationDataUpdateAction.doSave() 即時編審發生錯誤:" + ExceptionUtility.getStackTrace(e));
                    }

                    // 設定更新訊息
                    if (StringUtils.equals(ConstantKey.DO_CHECK_MARK_FAIL, returnCode))
                        saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage()); // 設定即時編審失敗訊息
                    else
                        saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(apNoString)); // 設定更新成功訊息

                    // 將 CaseData 自 Session 移除
                    CaseSessionHelper.removeSurvivorApplicationDataUpdateCase(request);

                    // Form 值重置
                    dupeForm.clearFields();

                    // 重新取得更新後的案件資料
                    caseData = updateService.getSurvivorApplicationDataUpdateData(apNo);

                    if (caseData != null) { // caseData 不為 null 表示案件可以繼續修改, 則頁面導回 Detail 頁面, 否則則導回 Query 頁面
                        // 將 CaseData 塞到 Session 中
                        CaseSessionHelper.setSurvivorApplicationDataUpdateCase(caseData, request);

                        // Form 值重置
                        dupeForm.clearFields();

                        // Form 下拉式選單初始化
                        // [
                        dupeForm.setCountryOptionList(selectOptionService.getCountry());// 國籍下拉選單
                        dupeForm.setApItemOptionList(selectOptionService.getSurvivorApplicationDataUpdateApItemList()); // 申請項目下拉選單
                        dupeForm.setEvTypOptionList(selectOptionService.getEvTypOptionList()); // 傷病分類下拉選單
                        dupeForm.setScloseCauseOptionList(selectOptionService.getScloseCauseOptionList()); // 結案原因下拉選單
                        // ]

                        // 取得 可輸入的 核定通知書格式 值塞入 Form 中 (用於頁面檢核)
                        // [
                        List<String> notifyFormList = selectOptionService.selectNotifyFormBy(apNo);
                        StringBuffer validNotifyFormValue = new StringBuffer("");
                        for (String notifyFormValue : notifyFormList) {
                            validNotifyFormValue.append("," + notifyFormValue);
                        }
                        if (StringUtils.length(validNotifyFormValue.toString()) > 0)
                            dupeForm.setValidNotifyFormValue(StringUtils.substring(validNotifyFormValue.toString(), 1, validNotifyFormValue.toString().length()));
                        // ]

                        // 將 CaseData 的資料塞到 Form 中
                        // [
                        BeanUtility.copyPropertiesForUpdate(dupeForm, caseData, ConstantKey.OLD_FIELD_PREFIX);

                        // 西元日期轉民國日期
                        dupeForm.setEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
                        dupeForm.setEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
                        dupeForm.setAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
                        dupeForm.setChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月
                        dupeForm.setJudgeDate(DateUtility.changeDateType(caseData.getJudgeDate())); // 判決日期

                        dupeForm.setOldEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
                        dupeForm.setOldEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
                        dupeForm.setOldAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
                        dupeForm.setOldChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月
                        dupeForm.setOldJudgeDate(DateUtility.changeDateType(caseData.getJudgeDate())); // 判決日期

                        dupeForm.setTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
                        dupeForm.setOldTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
                        // ]

                        log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 身分證重號頁面 SurvivorApplicationDataUpdateAction.doSelectDupe() 完成 ... ");
                        return mapping.findForward(FORWARD_DETAIL_PAGE);
                    }
                    else {
                        log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 身分證重號頁面 SurvivorApplicationDataUpdateAction.doSelectDupe() 完成 ... ");
                        return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
                    }
                }
            }

            log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 身分證重號頁面 SurvivorApplicationDataUpdateAction.doSelectDupe() 完成 ... ");

            return mapping.findForward(FORWARD_DETAIL_PAGE);
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("SurvivorApplicationDataUpdateAction.doSelectDupe() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d233c.jsp) <br>
     * 按下「檢視受理/審核清單」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 明細頁面 SurvivorApplicationDataUpdateAction.doPrint() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 自 Session 中取出 Case 資料
            SurvivorApplicationDataUpdateCase caseData = CaseSessionHelper.getSurvivorApplicationDataUpdateCase(request);

            if (caseData == null) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            List<SurvivorReviewRpt01Case> caseList = new ArrayList<SurvivorReviewRpt01Case>();
            caseList = rptService.getSurvivorReviewRpt01DataBy(caseData.getApNo(), caseData.getApNo());

            if (caseList == null || caseList.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            SurvivorReviewRpt01Report report = new SurvivorReviewRpt01Report();
            baoOutput = report.doReport(userData, caseList);

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            response.setHeader("Content-disposition", "attachment; filename=\"SurvivorReviewRpt01_" + printDate + ".pdf" + "\"");
            response.setHeader("Content-Encoding", "pdf");
            response.setContentType("application/pdf"); // 設定MIME
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentLength(baoOutput.size());

            // 傳回 Client 端
            ServletOutputStream sout = null;
            try {
                sout = response.getOutputStream();
                baoOutput.writeTo(sout);
                sout.flush();
            }
            catch (Exception e) {
            }
            finally {
                if (baoOutput != null)
                    baoOutput.close();
                if (sout != null)
                    sout.close();
            }

            log.debug("執行 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 - 明細頁面 SurvivorApplicationDataUpdateAction.doPrint() 完成 ... ");

            return null;
        }
        catch (Exception e) {
            // 設定報表產製失敗訊息
            log.error("SurvivorApplicationDataUpdateAction.doPrint() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            return mapping.findForward(FORWARD_DETAIL_PAGE);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 遺屬年金案件資料更正 (bamo0d233c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return mapping.findForward(FORWARD_DETAIL_PAGE);
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
