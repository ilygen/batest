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
import tw.gov.bli.ba.rpt.cases.DisableReviewRpt01Case;
import tw.gov.bli.ba.rpt.report.DisableReviewRpt01Kind36Report;
import tw.gov.bli.ba.rpt.report.DisableReviewRpt01Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateBareCheckCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateCase;
import tw.gov.bli.ba.update.cases.DisabledApplicationDataUpdateDupeIdnCase;
import tw.gov.bli.ba.update.forms.DisabledApplicationDataUpdateForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp bamo0d131c.jsp bamo0d133c.jsp)
 * 
 * @author Goston
 */
public class DisabledApplicationDataUpdateAction extends DispatchAction {
    private static Log log = LogFactory.getLog(DisabledApplicationDataUpdateAction.class);

    private static final String FORWARD_DUPE_LIST_PAGE = "dupeListPage";
    private static final String FORWARD_DETAIL_PAGE = "detailPage";
    private static final String FORWARD_DETAIL_PAGE_PAYKIND36 = "detailPagePayKind36";
    private static final String REHC_LIST_PAGE = "rehcListPage";
    private static final String REHC_DETAIL_PAGE = "rehcDetailPage";

    private UpdateService updateService;
    private SelectOptionService selectOptionService;
    private RptService rptService;

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d130c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 查詢頁面 DisabledApplicationDataUpdateAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledApplicationDataUpdateForm queryForm = (DisabledApplicationDataUpdateForm) form;

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeDisabledApplicationDataUpdateCase(request);

            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(queryForm.getApNo1())) + StringUtils.trimToEmpty(queryForm.getApNo2()) + StringUtils.trimToEmpty(queryForm.getApNo3()) + StringUtils.trimToEmpty(queryForm.getApNo4());

            if (StringUtils.length(apNo) != ConstantKey.APNO_LENGTH) { // 受理編號長度不滿 12 碼則不做查詢
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 取得 案件 資料
            DisabledApplicationDataUpdateCase caseData = updateService.getDisabledApplicationDataUpdateData(apNo);

            // 無查詢資料
            if (caseData == null) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            
            //重新審核失能程度 是否開啟
            if(StringUtils.isNotBlank(caseData.getRehcMk()) && StringUtils.isNotBlank(caseData.getRehcYm())){
            	caseData.setRehcOpen("Y");
            }

            // 取得"發放方式"可否修改狀態
            caseData.setIsShowInterValMonth(updateService.chkInterValMonthStatus(caseData.getApNo()));
            
            //20140325加入 國保受理編號
            queryForm.setOldMapNo(caseData.getMapNo());

            // 將 CaseData 塞到 Session 中
            CaseSessionHelper.setDisabledApplicationDataUpdateCase(caseData, request);

            // Form 值重置
            queryForm.clearFields();

            // Form 下拉式選單初始化
            // [
            queryForm.setCountryOptionList(selectOptionService.getCountry());// 國籍下拉選單
            queryForm.setEvTypOptionList(selectOptionService.getEvTypOptionList()); // 傷病分類下拉選單
            queryForm.setDisQualMkOptionList(selectOptionService.getDisQualMkOptionList()); // 年金請領資格下拉選單
            queryForm.setKcloseCauseOptionList(selectOptionService.getKcloseCauseOptionList()); // 結案原因下拉選單
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

            /*
             * if (StringUtils.isNotBlank(caseData.getRehcMk())) { queryForm.setCbxRehcMk("Y"); // 重新查核失能程度註記 (input - Checkbox) queryForm.setOldCbxRehcMk("Y"); }
             */

            if (StringUtils.isNotBlank(caseData.getRehcMk()))
                queryForm.setRehcMk(caseData.getRehcMk()); // 重新查核失能程度註記
            else
                queryForm.setRehcMk("0"); // 重新查核失能程度註記

            // 西元日期轉民國日期
            queryForm.setEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
            queryForm.setEvtJobDate(DateUtility.changeDateType(caseData.getEvtJobDate())); // 事故者離職日期 - 診斷失能日期
            queryForm.setEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
            queryForm.setAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
            queryForm.setChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月
            queryForm.setRehcYm(DateUtility.changeWestYearMonthType(caseData.getRehcYm())); // 重新查核失能程度註記年月

            queryForm.setOldEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
            queryForm.setOldEvtJobDate(DateUtility.changeDateType(caseData.getEvtJobDate())); // 事故者離職日期 - 診斷失能日期
            queryForm.setOldEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
            queryForm.setOldAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
            queryForm.setOldChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月

            if (StringUtils.equals(caseData.getRehcMk(), "2") && StringUtils.length(caseData.getRehcYm()) == 6) {
                queryForm.setRehcYear(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 0, 3)); // 重新查核失能程度註記 - 年 (input - Text)
                queryForm.setRehcMonth(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 3, 5)); // 重新查核失能程度註記 - 月 (input - Text)
                queryForm.setOldRehcYear(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 0, 3)); // 重新查核失能程度註記 - 年 (input - Text)
                queryForm.setOldRehcMonth(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 3, 5)); // 重新查核失能程度註記 - 月 (input - Text)
            }

            queryForm.setTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
            queryForm.setOldTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
            queryForm.setMonNotifyingMk(caseData.getMonNotifyingMk());  // 寄發月通知表

            // ]

            log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 查詢頁面 DisabledApplicationDataUpdateAction.doQuery() 完成 ... ");
            if (StringUtils.equals("36", caseData.getPayKind())) {
                return mapping.findForward(FORWARD_DETAIL_PAGE_PAYKIND36); //bamo0d135c
            }
            else {
                return mapping.findForward(FORWARD_DETAIL_PAGE); //bamo0d131c
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledApplicationDataUpdateAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d131c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 明細頁面 DisabledApplicationDataUpdateAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledApplicationDataUpdateForm updateForm = (DisabledApplicationDataUpdateForm) form;
        String payKind = "";

        try {
            // 自 Session 中取出 Case 資料
            DisabledApplicationDataUpdateCase caseData = CaseSessionHelper.getDisabledApplicationDataUpdateCase(request);
            payKind = caseData.getPayKind();

            if (caseData == null) {
                // 設定更新失敗訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            // 已登錄其他非本人的受款人, 但其他受款人資料未月核
            if (caseData.getBenCount().intValue() != 0 && caseData.getBenCountAfterCheck().intValue() == 0) {
                if (StringUtils.isBlank(updateForm.getEvtDieDate())) {
                    // 設定「本受理案件已登錄繼承人(受款人)資料，請先至『失能年金受款人資料更正』刪除繼承人(受款人)資料，始可清空「死亡日期」資料。」錯誤訊息
                    saveErrors(session, CustomMessageHelper.getDeleteBenDataBeforeSaveMessage());
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
                }
            }

            // 傷病原因 檢核
            if ((updateForm.getEvTyp().equals("1") || updateForm.getEvTyp().equals("2")) && (updateForm.getEvAppTyp().equals("1") || updateForm.getEvAppTyp().equals("2")) || (updateForm.getEvTyp().equals("3") || updateForm.getEvTyp().equals("4"))
                            && (updateForm.getEvAppTyp().equals("3") || updateForm.getEvAppTyp().equals("4")) || (updateForm.getEvTyp().equals("1") || updateForm.getEvTyp().equals("2"))
                            && (updateForm.getEvAppTyp().equals("3") || updateForm.getEvAppTyp().equals("4"))) {
                if (!updateService.isValidEvCode(updateForm.getEvTyp(), updateForm.getEvCode())) {
                    // 設定「傷病原因」輸入錯誤 訊息
                    saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.evCode")));
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
                }
            }
            else if ((updateForm.getEvTyp().equals("3") || updateForm.getEvTyp().equals("4")) && (updateForm.getEvAppTyp().equals("1") || updateForm.getEvAppTyp().equals("2"))) {
                if (!updateService.isValidEvCode2(updateForm.getEvAppTyp(), updateForm.getEvCode())) {
                    // 設定「傷病原因」輸入錯誤 訊息
                    saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.survivor.evCode")));
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
                }
            }

            // 受傷部位 檢核
            if (!updateService.isValidCriInPart(updateForm.getCriInPart1())) {
                // 設定「受傷部位1」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInPart1")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInPart(updateForm.getCriInPart2())) {
                // 設定「受傷部位2」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInPart2")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInPart(updateForm.getCriInPart3())) {
                // 設定「受傷部位3」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInPart3")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            // 媒介物 檢核
            if (!updateService.isValidCriMedium(updateForm.getCriMedium())) {
                // 設定「媒介物」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criMedium")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            // 失能項目 檢核
            if (!updateService.isValidCriInJdp(updateForm.getCriInJdp1())) {
                // 設定「失能項目1」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJdp1")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJdp(updateForm.getCriInJdp2())) {
                // 設定「失能項目2」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJdp2")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJdp(updateForm.getCriInJdp3())) {
                // 設定「失能項目3」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJdp3")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJdp(updateForm.getCriInJdp4())) {
                // 設定「失能項目4」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJdp4")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJdp(updateForm.getCriInJdp5())) {
                // 設定「失能項目5」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJdp5")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJdp(updateForm.getCriInJdp6())) {
                // 設定「失能項目6」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJdp6")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJdp(updateForm.getCriInJdp7())) {
                // 設定「失能項目7」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJdp7")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJdp(updateForm.getCriInJdp8())) {
                // 設定「失能項目8」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJdp8")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJdp(updateForm.getCriInJdp9())) {
                // 設定「失能項目9」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJdp9")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJdp(updateForm.getCriInJdp10())) {
                // 設定「失能項目10」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJdp10")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            // 國際疾病代碼 檢核
            if (!updateService.isValidCriInJnme(updateForm.getCriInJnme1())) {
                // 設定「國際疾病代碼1」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJnme1")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJnme(updateForm.getCriInJnme2())) {
                // 設定「國際疾病代碼2」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJnme2")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJnme(updateForm.getCriInJnme3())) {
                // 設定「國際疾病代碼3」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJnme3")));
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            if (!updateService.isValidCriInJnme(updateForm.getCriInJnme4())) {
                // 設定「國際疾病代碼4」輸入錯誤 訊息
                saveErrors(session, CustomMessageHelper.getInvalidFieldValueMessage(new ActionMessage("label.update.disabled.criInJnme4")));
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
            caseData.setEvtJobDate((StringUtils.length(updateForm.getEvtJobDate()) == 7) ? DateUtility.changeDateType(updateForm.getEvtJobDate()) : updateForm.getEvtJobDate()); // 事故者離職日期 - 診斷失能日期
            caseData.setEvtDieDate((StringUtils.length(updateForm.getEvtDieDate()) == 7) ? DateUtility.changeDateType(updateForm.getEvtDieDate()) : updateForm.getEvtDieDate()); // 事故者死亡日期
            caseData.setAppDate((StringUtils.length(updateForm.getAppDate()) == 7) ? DateUtility.changeDateType(updateForm.getAppDate()) : updateForm.getAppDate()); // 申請日期
            caseData.setApUbno(updateForm.getApUbno()); // 申請單位保險證號
            caseData.setApubnock(updateForm.getApubnock()); // 申請單位保險證號檢查碼
            caseData.setLsUbno(updateForm.getLsUbno()); // 最後單位保險證號 - 事故發生單位保險證號
            caseData.setLsUbnoCk(updateForm.getLsUbnoCk()); // 最後單位保險證號檢查碼
            caseData.setNotifyForm(updateForm.getNotifyForm()); // 核定通知書格式
            caseData.setCutAmt(updateForm.getCutAmt()); // 扣減 / 補償總金額 - 年金應扣金額
            //20140325加入國保受裡面號 如有3M註記 mapNo給修改後的值 如無3M註記 給原值不更新
            if(caseData.isContainCheckMark3M() == true){
            	caseData.setMapNo(updateForm.getMapNo());
            }

            if (StringUtils.equals(updateForm.getLoanMk(), "1"))
                caseData.setLoanMk("1"); // 不須抵銷紓困貸款註記
            else
                caseData.setLoanMk("0"); // 不須抵銷紓困貸款註記

            caseData.setCloseCause(updateForm.getCloseCause()); // 結案原因
            caseData.setChoiceYm((StringUtils.length(updateForm.getChoiceYm()) == 5) ? DateUtility.changeChineseYearMonthType(updateForm.getChoiceYm()) : updateForm.getChoiceYm()); // 擇領起月
            caseData.setInterValMonth(updateForm.getInterValMonth()); // 發放方式
            caseData.setComSeniority(updateForm.getComSeniority()); //

            // BAAPPEXPAND
            caseData.setEvAppTyp(updateForm.getEvAppTyp());// 申請傷病分類
            caseData.setEvTyp(updateForm.getEvTyp()); // 傷病分類
            caseData.setDisQualMk(updateForm.getDisQualMk()); // 年金請領資格
            caseData.setEvCode(updateForm.getEvCode()); // 傷病原因
            caseData.setCriInPart1(updateForm.getCriInPart1()); // 受傷部位1
            caseData.setCriInPart2(updateForm.getCriInPart2()); // 受傷部位2
            caseData.setCriInPart3(updateForm.getCriInPart3()); // 受傷部位3
            caseData.setCriMedium(updateForm.getCriMedium()); // 媒介物
            caseData.setCriInIssul(updateForm.getCriInIssul()); // 核定等級
            caseData.setCriInJcl1(updateForm.getCriInJcl1()); // 失能等級1
            caseData.setCriInJcl2(updateForm.getCriInJcl2()); // 失能等級2
            caseData.setCriInJcl3(updateForm.getCriInJcl3()); // 失能等級3
            caseData.setCriInJdp1(updateForm.getCriInJdp1()); // 失能項目1
            caseData.setCriInJdp2(updateForm.getCriInJdp2()); // 失能項目2
            caseData.setCriInJdp3(updateForm.getCriInJdp3()); // 失能項目3
            caseData.setCriInJdp4(updateForm.getCriInJdp4()); // 失能項目4
            caseData.setCriInJdp5(updateForm.getCriInJdp5()); // 失能項目5
            caseData.setCriInJdp6(updateForm.getCriInJdp6()); // 失能項目6
            caseData.setCriInJdp7(updateForm.getCriInJdp7()); // 失能項目7
            caseData.setCriInJdp8(updateForm.getCriInJdp8()); // 失能項目8
            caseData.setCriInJdp9(updateForm.getCriInJdp9()); // 失能項目9
            caseData.setCriInJdp10(updateForm.getCriInJdp10()); // 失能項目10
            caseData.setHosId(updateForm.getHosId()); // 醫療院所代碼
            caseData.setDoctorName1(updateForm.getDoctorName1()); // 醫師姓名1
            caseData.setDoctorName2(updateForm.getDoctorName2()); // 醫師姓名2
            caseData.setOcAccHosId(updateForm.getOcAccHosId()); // 職病醫療院所代碼
            caseData.setOcAccDoctorName1(updateForm.getOcAccDoctorName1()); // 職病醫師姓名1
            caseData.setOcAccDoctorName2(updateForm.getOcAccDoctorName2()); // 職病醫師姓名2            
            caseData.setCriInJnme1(updateForm.getCriInJnme1()); // 國際疾病代碼1
            caseData.setCriInJnme2(updateForm.getCriInJnme2()); // 國際疾病代碼2
            caseData.setCriInJnme3(updateForm.getCriInJnme3()); // 國際疾病代碼3
            caseData.setCriInJnme4(updateForm.getCriInJnme4()); // 國際疾病代碼4
            
            if (StringUtils.equals(updateForm.getMonNotifyingMk(), "Y"))
                caseData.setMonNotifyingMk("Y"); // 寄發月通知表
            else
                caseData.setMonNotifyingMk("N"); // 寄發月通知表            

            /*
             * if (StringUtils.isNotBlank(updateForm.getCbxRehcMk())) caseData.setRehcMk(updateForm.getRehcMk()); // 重新查核失能程度註記 else caseData.setRehcMk("0"); // 重新查核失能程度註記
             */

            if (StringUtils.isNotBlank(updateForm.getRehcMk())) {
                caseData.setRehcMk(updateForm.getRehcMk()); // 重新查核失能程度註記
                if (StringUtils.equals(updateForm.getRehcMk(), "1")) {
                    caseData.setRehcYm(DateUtility.calMonth(DateUtility.getNowWestDate(), 61).substring(0, 6)); // 重新查核失能程度年月
                }
                else if (StringUtils.equals(updateForm.getRehcMk(), "2")) {
                    caseData.setRehcYm(DateUtility.changeChineseYearMonthType(StringUtils.defaultString(updateForm.getRehcYear()) + StringUtils.defaultString(updateForm.getRehcMonth()))); // 重新查核失能程度年月
                }
            }

            caseData.setOcaccIdentMk(updateForm.getOcaccIdentMk()); // 符合第20條之1註記
            caseData.setPrType(updateForm.getPrType()); // 先核普通
            caseData.setOcAccaddAmt(updateForm.getOcAccaddAmt()); // 己領職災增給金額
            caseData.setDeductDay(updateForm.getDeductDay()); // 扣除天數
            // ]

            // 存檔
            updateService.updateDisabledApplicationDataUpdateData(userData, caseData, updateForm);

            // 呼叫即時編審 WebService
            // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
            String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
            log.info("webServiceUrl: " + webServiceUrl);
            String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
            try {
                SingleCheckMarkServiceHttpBindingStub binding;
                binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator().getSingleCheckMarkServiceHttpPort();
                returnCode = binding.doCheckMark(apNo,SecureToken.getInstance().getToken());
            }
            catch (Exception e) {
                log.error("DisabledApplicationDataUpdateAction.doSave() 即時編審發生錯誤:" + ExceptionUtility.getStackTrace(e));
            }

            // 設定更新訊息
            if (StringUtils.equals(ConstantKey.DO_CHECK_MARK_FAIL, returnCode))
                saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage()); // 設定即時編審失敗訊息
            else
                saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(apNoString)); // 設定更新成功訊息

            // 將 CaseData 自 Session 移除
            CaseSessionHelper.removeDisabledApplicationDataUpdateCase(request);

            // Form 值重置
            updateForm.clearFields();

            // 重新取得更新後的案件資料
            caseData = updateService.getDisabledApplicationDataUpdateData(apNo);

            if (caseData != null) { // caseData 不為 null 表示案件可以繼續修改, 則頁面導回 Detail 頁面, 否則則導回 Query 頁面
                // 取得"發放方式"可否修改狀態
                caseData.setIsShowInterValMonth(updateService.chkInterValMonthStatus(caseData.getApNo()));
                
                //重新審核失能程度 是否開啟
                if(StringUtils.isNotBlank(caseData.getRehcMk()) && StringUtils.isNotBlank(caseData.getRehcYm())){
                	caseData.setRehcOpen("Y");
                }

                // 將 CaseData 塞到 Session 中
                CaseSessionHelper.setDisabledApplicationDataUpdateCase(caseData, request);

                // Form 下拉式選單初始化
                // [
                updateForm.setCountryOptionList(selectOptionService.getCountry());// 國籍下拉選單
                updateForm.setEvTypOptionList(selectOptionService.getEvTypOptionList()); // 傷病分類下拉選單
                updateForm.setDisQualMkOptionList(selectOptionService.getDisQualMkOptionList()); // 年金請領資格下拉選單
                updateForm.setKcloseCauseOptionList(selectOptionService.getKcloseCauseOptionList()); // 結案原因下拉選單
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

                /*
                 * if (StringUtils.isNotBlank(caseData.getRehcMk())) { updateForm.setCbxRehcMk("Y"); // 重新查核失能程度註記 (input - Checkbox) updateForm.setOldCbxRehcMk("Y"); }
                 */

                if (StringUtils.isNotBlank(caseData.getRehcMk()))
                    updateForm.setRehcMk(caseData.getRehcMk()); // 重新查核失能程度註記
                else
                    updateForm.setRehcMk("0"); // 重新查核失能程度註記

                // 西元日期轉民國日期
                updateForm.setEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
                updateForm.setEvtJobDate(DateUtility.changeDateType(caseData.getEvtJobDate())); // 事故者離職日期 - 診斷失能日期
                updateForm.setEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
                updateForm.setAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
                updateForm.setChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月
                updateForm.setRehcYm(DateUtility.changeWestYearMonthType(caseData.getRehcYm())); // 重新查核失能程度註記年月

                updateForm.setOldEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
                updateForm.setOldEvtJobDate(DateUtility.changeDateType(caseData.getEvtJobDate())); // 事故者離職日期 - 診斷失能日期
                updateForm.setOldEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
                updateForm.setOldAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
                updateForm.setOldChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月

                if (StringUtils.equals(caseData.getRehcMk(), "2") && StringUtils.length(caseData.getRehcYm()) == 6) {
                    updateForm.setRehcYear(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 0, 3)); // 重新查核失能程度註記 - 年 (input - Text)
                    updateForm.setRehcMonth(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 3, 5)); // 重新查核失能程度註記 - 月 (input - Text)
                    updateForm.setOldRehcYear(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 0, 3)); // 重新查核失能程度註記 - 年 (input - Text)
                    updateForm.setOldRehcMonth(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 3, 5)); // 重新查核失能程度註記 - 月 (input - Text)
                }

                updateForm.setTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
                updateForm.setOldTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
                // ]

                log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 明細頁面 DisabledApplicationDataUpdateAction.doSave() 完成 ... ");
                if (StringUtils.equals("36", caseData.getPayKind())) {
                    return mapping.findForward(FORWARD_DETAIL_PAGE_PAYKIND36);
                }
                else {
                    return mapping.findForward(FORWARD_DETAIL_PAGE);
                }
            }
            else {
                log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 明細頁面 DisabledApplicationDataUpdateAction.doSave() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
            }
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("DisabledApplicationDataUpdateAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());

            if (StringUtils.equals("36", payKind)) {
                return mapping.findForward(FORWARD_DETAIL_PAGE_PAYKIND36);
            }
            else {
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d131c.jsp) <br>
     * 按下「註銷」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 明細頁面 DisabledApplicationDataUpdateAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledApplicationDataUpdateForm deleteForm = (DisabledApplicationDataUpdateForm) form;
        String payKind = "";
        
        try {
            // 自 Session 中取出 Case 資料
            DisabledApplicationDataUpdateCase caseData = CaseSessionHelper.getDisabledApplicationDataUpdateCase(request);
            payKind = caseData.getPayKind();
            
            if (caseData == null) {
                // 設定註銷失敗訊息
                saveMessages(session, DatabaseMessageHelper.getCancelFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
            }

            // 註銷
            updateService.deleteDisabledApplicationDataUpdateData(userData, caseData.getApNo());

            // 設定註銷成功訊息
            saveMessages(session, DatabaseMessageHelper.getCancelSuccessMessage());

            // 將 CaseData 自 Session 移除
            CaseSessionHelper.removeDisabledApplicationDataUpdateCase(request);

            // Form 值重置
            deleteForm.clearFields();

            log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 明細頁面 DisabledApplicationDataUpdateAction.doDelete() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);
        }
        catch (Exception e) {
            // 設定註銷失敗訊息
            log.error("DisabledApplicationDataUpdateAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getCancelFailMessage());
            if (StringUtils.equals("36", payKind)) {
                return mapping.findForward(FORWARD_DETAIL_PAGE_PAYKIND36);
            }
            else {
                return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
            }
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d131c.jsp) <br>
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
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d133c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSelectDupe(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 身分證重號頁面 DisabledApplicationDataUpdateAction.doSelectDupe() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledApplicationDataUpdateForm dupeForm = (DisabledApplicationDataUpdateForm) form;

        try {
            // 自 Session 中取出 Case 資料
            DisabledApplicationDataUpdateCase caseData = CaseSessionHelper.getDisabledApplicationDataUpdateCase(request);

            if (caseData == null) {
                // 設定更新失敗訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            int index = dupeForm.getRadioDupeIndex();

            if (index >= 0 && index < caseData.getDupeIdnList().size()) {
                DisabledApplicationDataUpdateDupeIdnCase dupeIdnCase = caseData.getDupeIdnList().get(index);

                if (dupeIdnCase != null) {
                    String apNo = caseData.getApNo(); // 受理編號 - 供後續使用
                    String apNoString = caseData.getApNoString(); // 受理編號 - 供後續使用
                    
                    if(StringUtils.isNotBlank(dupeIdnCase.getIdnNo()) && dupeIdnCase.getIdnNo().length() > 10){
                    dupeIdnCase.setIdnNoTrim(dupeIdnCase.getIdnNo().substring(0, 10));
                    }

                    // 存檔
                    updateService.updateDisabledApplicationDataUpdateDupeIdnData(userData, dupeIdnCase);

                    // 呼叫即時編審 WebService
                    // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
                    String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
                    log.info("webServiceUrl: " + webServiceUrl);
                    String returnCode = ConstantKey.DO_CHECK_MARK_FAIL;
                    try {
                        SingleCheckMarkServiceHttpBindingStub binding;
                        binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator().getSingleCheckMarkServiceHttpPort();
                        returnCode = binding.doCheckMark(apNo,SecureToken.getInstance().getToken());
                    }
                    catch (Exception e) {
                        log.error("DisabledApplicationDataUpdateAction.doSelectDupe() 即時編審發生錯誤:" + ExceptionUtility.getStackTrace(e));
                    }

                    // 設定更新訊息
                    if (StringUtils.equals(ConstantKey.DO_CHECK_MARK_FAIL, returnCode))
                        saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage()); // 設定即時編審失敗訊息
                    else
                        saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(apNoString)); // 設定更新成功訊息

                    // 將 CaseData 自 Session 移除
                    CaseSessionHelper.removeDisabledApplicationDataUpdateCase(request);

                    // Form 值重置
                    dupeForm.clearFields();

                    // 重新取得更新後的案件資料
                    caseData = updateService.getDisabledApplicationDataUpdateData(apNo);

                    if (caseData != null) { // caseData 不為 null 表示案件可以繼續修改, 則頁面導回 Detail 頁面, 否則則導回 Query 頁面
                        // 將 CaseData 塞到 Session 中
                        CaseSessionHelper.setDisabledApplicationDataUpdateCase(caseData, request);

                        // Form 下拉式選單初始化
                        // [
                        dupeForm.setCountryOptionList(selectOptionService.getCountry());// 國籍下拉選單
                        dupeForm.setEvTypOptionList(selectOptionService.getEvTypOptionList()); // 傷病分類下拉選單
                        dupeForm.setKcloseCauseOptionList(selectOptionService.getKcloseCauseOptionList()); // 結案原因下拉選單
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

                        /*
                         * if (StringUtils.isNotBlank(caseData.getRehcMk())) { dupeForm.setCbxRehcMk("Y"); // 重新查核失能程度註記 (input - Checkbox) dupeForm.setOldCbxRehcMk("Y"); }
                         */

                        if (StringUtils.isNotBlank(caseData.getRehcMk()))
                            dupeForm.setRehcMk(caseData.getRehcMk()); // 重新查核失能程度註記
                        else
                            dupeForm.setRehcMk("0"); // 重新查核失能程度註記

                        // 西元日期轉民國日期
                        dupeForm.setEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
                        dupeForm.setEvtJobDate(DateUtility.changeDateType(caseData.getEvtJobDate())); // 事故者離職日期 - 診斷失能日期
                        dupeForm.setEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
                        dupeForm.setAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
                        dupeForm.setChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月

                        dupeForm.setOldEvtBrDate(DateUtility.changeDateType(caseData.getEvtBrDate())); // 事故者出生日期
                        dupeForm.setOldEvtJobDate(DateUtility.changeDateType(caseData.getEvtJobDate())); // 事故者離職日期 - 診斷失能日期
                        dupeForm.setOldEvtDieDate(DateUtility.changeDateType(caseData.getEvtDieDate())); // 事故者死亡日期
                        dupeForm.setOldAppDate(DateUtility.changeDateType(caseData.getAppDate())); // 申請日期
                        dupeForm.setOldChoiceYm(DateUtility.changeWestYearMonthType(caseData.getChoiceYm())); // 擇領起月

                        if (StringUtils.equals(caseData.getRehcMk(), "2") && StringUtils.length(caseData.getRehcYm()) == 6) {
                            dupeForm.setRehcYear(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 0, 3)); // 重新查核失能程度註記 - 年 (input - Text)
                            dupeForm.setRehcMonth(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 3, 5)); // 重新查核失能程度註記 - 月 (input - Text)
                            dupeForm.setOldRehcYear(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 0, 3)); // 重新查核失能程度註記 - 年 (input - Text)
                            dupeForm.setOldRehcMonth(StringUtils.substring(DateUtility.changeWestYearMonthType(caseData.getRehcYm()), 3, 5)); // 重新查核失能程度註記 - 月 (input - Text)
                        }

                        dupeForm.setTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
                        dupeForm.setOldTmpEvtNationCode(caseData.getEvtNationCode()); // 事故者國籍代碼 (input - 下拉選單)
                        // ]

                        log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 身分證重號頁面 DisabledApplicationDataUpdateAction.doSelectDupe() 完成 ... ");
                        if (StringUtils.equals("36", caseData.getPayKind())) {
                            return mapping.findForward(FORWARD_DETAIL_PAGE_PAYKIND36);
                        }
                        else {
                            return mapping.findForward(FORWARD_DETAIL_PAGE);
                        }
                    }
                    else {
                        log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 身分證重號頁面 DisabledApplicationDataUpdateAction.doSelectDupe() 完成 ... ");
                        return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
                    }
                }
            }

            log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 身分證重號頁面 DisabledApplicationDataUpdateAction.doSelectDupe() 完成 ... ");

            return mapping.findForward(FORWARD_DETAIL_PAGE);
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("DisabledApplicationDataUpdateAction.doSelectDupe() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d133c.jsp) <br>
     * 按下「檢視受理/審核清單」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 明細頁面 DisabledApplicationDataUpdateAction.doPrint() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        String payKind = "";
        try {
            // 自 Session 中取出 Case 資料
            DisabledApplicationDataUpdateCase caseData = CaseSessionHelper.getDisabledApplicationDataUpdateCase(request);

            if (caseData == null) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            List<DisableReviewRpt01Case> caseList = new ArrayList<DisableReviewRpt01Case>();
            caseList = rptService.getDisableReviewRpt01DataBy(caseData.getApNo(), caseData.getApNo());
            payKind = caseData.getPayKind();

            if (caseList == null || caseList.size() == 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            if (caseList != null) {
                for (int i = 0; i < caseList.size(); i++) {
                    // 放入得請領起始年月formatChineseYearMonthString
                    DisableReviewRpt01Case caseDataCase = (DisableReviewRpt01Case) caseList.get(i);
                    // 處理日期前
                    String ableapsYmBefor = rptService.getAbleapsYmForDisabledCheckMarkLevelAdjust(caseDataCase.getApNo(), caseDataCase.getBaappbaseId());

                    if (StringUtils.isNotBlank(ableapsYmBefor)) {
                        // 處理日期後 放入list內各筆資料之AbleapsYm 得請領起始年月
                        String ableapsYmAfter = DateUtility.formatChineseYearMonthString(DateUtility.changeWestYearMonthType(ableapsYmBefor), false);
                        caseDataCase.setAbleapsYm(ableapsYmAfter);
                    }
                }
            }

            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

            DisableReviewRpt01Case caseObj = new DisableReviewRpt01Case();
            caseObj = (DisableReviewRpt01Case)caseList.get(0);                    
            if("36".equals(caseObj.getPayKind())){
                DisableReviewRpt01Kind36Report report = new DisableReviewRpt01Kind36Report();
                baoOutput = report.doReport(userData, caseList); 
            } else {
                DisableReviewRpt01Report report = new DisableReviewRpt01Report();
                baoOutput = report.doReport(userData, caseList);                        
            }
            //DisableReviewRpt01Report report = new DisableReviewRpt01Report(); 20130806修改掉
            //baoOutput = report.doReport(userData, caseList);

            String printDate = DateUtility.getNowChineseDate();

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            response.setHeader("Content-disposition", "attachment; filename=\"DisableReviewRpt01_" + printDate + ".pdf" + "\"");
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

            log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 明細頁面 DisabledApplicationDataUpdateAction.doPrint() 完成 ... ");

            return null;
        }
        catch (Exception e) {
            // 設定報表產製失敗訊息
            log.error("DisabledApplicationDataUpdateAction.doPrint() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getReportGenerateFailMessage());
            if (StringUtils.equals("36", payKind)) {
                return mapping.findForward(FORWARD_DETAIL_PAGE_PAYKIND36);
            }
            else {
                return mapping.findForward(FORWARD_DETAIL_PAGE);
            }
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d133c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        // 自 Session 中取出 Case 資料
        DisabledApplicationDataUpdateCase caseData = CaseSessionHelper.getDisabledApplicationDataUpdateCase(request);
        if (StringUtils.equals("36", caseData.getPayKind())) {
            return mapping.findForward(FORWARD_DETAIL_PAGE_PAYKIND36);
        }
        else {
            return mapping.findForward(FORWARD_DETAIL_PAGE);
        }
    }
    
    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d131c.jsp) <br>
     * 按下「重新審核失能程度」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doRehc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 修改頁面 DisabledApplicationDataUpdateAction.doRehc() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledApplicationDataUpdateForm queryForm = (DisabledApplicationDataUpdateForm) form;

        try {
            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(queryForm.getApNo1())) + StringUtils.trimToEmpty(queryForm.getApNo2()) + StringUtils.trimToEmpty(queryForm.getApNo3()) + StringUtils.trimToEmpty(queryForm.getApNo4());

            // 自 Session 中取出 Case 資料
            DisabledApplicationDataUpdateCase caseData = CaseSessionHelper.getDisabledApplicationDataUpdateCase(request);
            
            List<DisabledApplicationDataUpdateBareCheckCase> bareCheckDataList = updateService.selectBareCheckDataBy(caseData.getApNo(), caseData.getSeqNo());

            // 無查詢資料
            if (bareCheckDataList.size() <= 0) {
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }

            CaseSessionHelper.setDisabledApplicationDataUpdateBareCheckCaseList(bareCheckDataList, request);


            log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 修改頁面 DisabledApplicationDataUpdateAction.doRehc() 完成 ... ");
        
                return mapping.findForward(REHC_LIST_PAGE); //bamo0d136c

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledApplicationDataUpdateAction.doRehc() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }
    
    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d136c.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doRehcDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 重新審核失能程度 修改頁面 DisabledApplicationDataUpdateAction.doRehcDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledApplicationDataUpdateForm queryForm = (DisabledApplicationDataUpdateForm) form;

        try {
            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(queryForm.getApNo1())) + StringUtils.trimToEmpty(queryForm.getApNo2()) + StringUtils.trimToEmpty(queryForm.getApNo3()) + StringUtils.trimToEmpty(queryForm.getApNo4());

            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));
            // 自 Session 中取出 Case 資料
            List<DisabledApplicationDataUpdateBareCheckCase> bareCheckDataList = CaseSessionHelper.getDisabledApplicationDataUpdateBareCheckCaseList(request);
 
            DisabledApplicationDataUpdateBareCheckCase caseDate = bareCheckDataList.get(Integer.parseInt(rowNum) - 1);
            
            queryForm.setReChkStatusOptionList(selectOptionService.getReChkStatusOptionList());// 國籍下拉選單
            queryForm.setReChkResult1OptionList(selectOptionService.getReChkResult1OptionList());// 傷病分類下拉選單
            queryForm.setReChkResult2OptionList(selectOptionService.getReChkResult2OptionList());// 傷病分類下拉選單
            queryForm.setReChkYm(caseDate.getReChkYm()); //重新查核失能程度年月
            queryForm.setIsreChk(caseDate.getIsreChk()); //是否複檢
            queryForm.setReChkStatus(caseDate.getReChkStatus()); //重新查核狀態
            if(caseDate.getReChkStatus().equals("1")){
            	queryForm.setReChkResult1(caseDate.getReChkResult()); //重新查核結果1
            	queryForm.setOldReChkResult1(caseDate.getReChkResult());
            }else if(caseDate.getReChkStatus().equals("2")){
            	queryForm.setReChkResult2(caseDate.getReChkResult()); //重新查核結果2
            	queryForm.setOldReChkResult2(caseDate.getReChkResult());
            }
            if(StringUtils.isNotBlank(caseDate.getComReChkYm())){
                queryForm.setComReChkYm(caseDate.getComReChkYm()); //完成重新查核年月
                queryForm.setOldComReChkYm(caseDate.getComReChkYm()); //完成重新查核年月 原值
            }else{
            	queryForm.setComReChkYm(DateUtility.getNowChineseDate().substring(0, 5)); //完成重新查核年月
            }
            queryForm.setOldIsreChk(caseDate.getIsreChk());
            queryForm.setOldReChkStatus(caseDate.getReChkStatus());

            log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 重新審核失能程度 修改頁面 DisabledApplicationDataUpdateAction.doRehcDetail() 完成 ... ");
        
            return mapping.findForward(REHC_DETAIL_PAGE); //bamo0d136c

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledApplicationDataUpdateAction.doRehcDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(REHC_DETAIL_PAGE);
        }
    }
    
    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d137c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doRehcUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 重新審核失能程度 修改頁面 DisabledApplicationDataUpdateAction.doRehcUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledApplicationDataUpdateForm updateForm = (DisabledApplicationDataUpdateForm) form;

        try {
            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(updateForm.getApNo1())) + StringUtils.trimToEmpty(updateForm.getApNo2()) + StringUtils.trimToEmpty(updateForm.getApNo3()) + StringUtils.trimToEmpty(updateForm.getApNo4());
            
            //Boolean test = updateService.selectBareCheckDataCountBy(apNo,updateForm.getSeqNo(),DateUtility.changeChineseYearMonthType(updateForm.getComReChkYm()));
            
            if(!updateForm.getComReChkYm().equals(updateForm.getOldComReChkYm())){
            
            if(updateService.selectBareCheckDataCountBy(apNo,updateForm.getSeqNo(),DateUtility.changeChineseYearMonthType(updateForm.getComReChkYm()))){
                 // 設定更新失敗訊息
                 saveMessages(session, CustomMessageHelper.getBareCheckDataCountMessage());
                 return mapping.findForward(REHC_DETAIL_PAGE);
            }
            
            }
            
            DisabledApplicationDataUpdateBareCheckCase caseData = new DisabledApplicationDataUpdateBareCheckCase();

            caseData.setApNo(updateForm.getApNo());
            caseData.setSeqNo(updateForm.getSeqNo());
            caseData.setReChkYm(DateUtility.changeChineseYearMonthType(updateForm.getReChkYm())); //重新查核失能程度年月
            caseData.setIsreChk(updateForm.getIsreChk()); //是否複檢
            caseData.setReChkStatus(updateForm.getReChkStatus()); //重新查核狀態
            if(updateForm.getReChkStatus().equals("1")){
            	caseData.setReChkResult(updateForm.getReChkResult1()); //重新查核結果1
            }else if(updateForm.getReChkStatus().equals("2")){
            	caseData.setReChkResult(updateForm.getReChkResult2()); //重新查核結果2
            }
            caseData.setComReChkYm(DateUtility.changeChineseYearMonthType(updateForm.getComReChkYm())); //完成重新查核年月
            caseData.setUpdUser(userData.getEmpNo());
         
            //更新 重新查核失能程度檔
            updateService.updateBareCheckData(caseData);
            
            //更新頁面List資料
            List<DisabledApplicationDataUpdateBareCheckCase> bareCheckDataList = updateService.selectBareCheckDataBy(caseData.getApNo(), caseData.getSeqNo());
            
            CaseSessionHelper.setDisabledApplicationDataUpdateBareCheckCaseList(bareCheckDataList, request);
            
            // 設定存檔成功訊息
            saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            
            
            log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 重新審核失能程度 修改頁面 DisabledApplicationDataUpdateAction.doRehcUpdate() 完成 ... ");
        
            return mapping.findForward(REHC_LIST_PAGE); //bamo0d136c

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledApplicationDataUpdateAction.doRehcUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(REHC_DETAIL_PAGE);
        }
    }
    
    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d136c.jsp) <br>
     * 按下「刪除」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doRehcDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 重新審核失能程度 修改頁面 DisabledApplicationDataUpdateAction.doRehcUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DisabledApplicationDataUpdateForm updateForm = (DisabledApplicationDataUpdateForm) form;

        try {
            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(updateForm.getApNo1())) + StringUtils.trimToEmpty(updateForm.getApNo2()) + StringUtils.trimToEmpty(updateForm.getApNo3()) + StringUtils.trimToEmpty(updateForm.getApNo4());
            
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));
            // 自 Session 中取出 Case 資料
            List<DisabledApplicationDataUpdateBareCheckCase> bareCheckDataList = CaseSessionHelper.getDisabledApplicationDataUpdateBareCheckCaseList(request);
 
            DisabledApplicationDataUpdateBareCheckCase caseDate = bareCheckDataList.get(Integer.parseInt(rowNum) - 1);

         
            //更新 重新查核失能程度檔
            updateService.deleteDataForBareCheck(updateForm.getApNo(),updateForm.getSeqNo(),DateUtility.changeChineseYearMonthType(caseDate.getReChkYm()));
            
            //更新頁面List資料
            List<DisabledApplicationDataUpdateBareCheckCase> bareCheckDataListAfter = updateService.selectBareCheckDataBy(updateForm.getApNo(), updateForm.getSeqNo());
            
            CaseSessionHelper.setDisabledApplicationDataUpdateBareCheckCaseList(bareCheckDataListAfter, request);
            
            //設定刪除成功訊息                        
            saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());                 
            
            
            log.debug("執行 更正作業 - 案件資料更正 - 失能年金案件資料更正 - 重新審核失能程度 修改頁面 DisabledApplicationDataUpdateAction.doRehcUpdate() 完成 ... ");
        
            return mapping.findForward(REHC_LIST_PAGE); //bamo0d136c

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DisabledApplicationDataUpdateAction.doRehcUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(REHC_DETAIL_PAGE);
        }
    }
    
    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d136c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        
        return mapping.findForward(FORWARD_DETAIL_PAGE);
    }
    
    /**
     * 更正作業 - 案件資料更正 - 失能年金案件資料更正 (bamo0d137c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackToRechUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        
    	DisabledApplicationDataUpdateForm updateForm = (DisabledApplicationDataUpdateForm) form;
    	
    	updateForm.setReChkResult1("");
    	updateForm.setReChkResult2("");
    	
        return mapping.findForward(REHC_LIST_PAGE);
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
