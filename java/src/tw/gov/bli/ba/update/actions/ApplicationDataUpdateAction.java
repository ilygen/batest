package tw.gov.bli.ba.update.actions;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.iisi.SecureToken;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.ApplicationUpdateDupeIdnNoCase;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.BasenimaintCase;
import tw.gov.bli.ba.update.cases.ChkFileCollectionCase;
import tw.gov.bli.ba.update.forms.ApplicationDataUpdateForm;
import tw.gov.bli.ba.update.forms.OnceAmtDataUpdateForm;
import tw.gov.bli.ba.update.forms.OwesDataUpdateForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 案件資料更正 (BAMO0D030C)
 * 
 * @author Rickychi
 */
public class ApplicationDataUpdateAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ApplicationDataUpdateAction.class);

    private UpdateService updateService;
    private SelectOptionService selectOptionService;
    // 更正作業 - 案件資料更正 - 案件資料詳細頁面
    private static final String FORWARD_APPLICATION_DETAIL = "applicationDetail";
    // 更正作業 - 案件資料更正 - 欠費期間維護頁面
    private static final String FORWARD_OWES_DATA_UPDATE = "owesDataUpdate";
    // 更正作業 - 案件資料更正 - 一次給付資料更正頁面
    private static final String FORWARD_ONCEAMT_DATA_UPDATE = "onceAmtDataUpdate";
    // 更正作業 - 案件資料更正 - 身分證號重號資料頁面
    private static final String FORWARD_DUPEIDNNO_DATA = "dupeIdnNoData";

    /**
     * 更正作業 - 案件資料更正 - 查詢頁面 (bamo0d030c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 查詢頁面 ApplicationDataUpdate.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ApplicationDataUpdateForm iform = (ApplicationDataUpdateForm) form;

        try {
            List<BaappbaseDataUpdateCase> dataList = updateService.selectBaappbaseDataList(null, iform.getApNoStr(), "0000", new String[] { "01", "10", "11", "12", "20" }, "in");

            // 資料筆數<=0時，顯示訊息:W0040無查詢資料
            if (dataList.size() <= 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 更正作業 - 案件資料更正 - 查詢頁面 ApplicationDataUpdateAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            // 資料筆數=1時，將系統畫面導至『申請資料更正作業 (BAMO0D031C)』頁面
            else if (dataList.size() == 1) {//
                BaappbaseDataUpdateCase obj = (BaappbaseDataUpdateCase) dataList.get(0);
                BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(obj.getBaappbaseId(), null, null, null);

                // 取得 國籍 清單
                request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                // 取得 申請項目 清單
                request.getSession().setAttribute(ConstantKey.APITEM_OPTION_LIST, selectOptionService.getApItemOldOptionList());
                // 取得 結案原因 清單
                request.getSession().setAttribute(ConstantKey.CLOSECAUSE_OPTION_LIST, selectOptionService.getCloseCauseOptionList(detailData.getApNo().substring(0, 1)));
                // 取得 給付編審 列表
                Map<String, Object> resuleMap = updateService.getChkListForAppUpdateBy(detailData.getBaappbaseId());
                request.getSession().setAttribute(ConstantKey.CHKFILE_OPTION_LIST, (List<ChkFileCollectionCase>) resuleMap.get("ChkFile"));
                // 取得"註銷"按鈕狀態
                detailData.setDelBtnStatus(updateService.getDelBtnForApplicationUpdate(detailData.getApNo()));
                // 取得"事故者姓名"可否修改狀態
                detailData.setEvtNameStatus(updateService.getEvtNameModifyStatusForApplicationUpdate(detailData.getApNo()));
                // 取得"發放方式"可否修改狀態
                detailData.setIsShowInterValMonth(updateService.chkInterValMonthStatus(detailData.getApNo()));

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                ApplicationDataUpdateForm modifyForm = new ApplicationDataUpdateForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
                detailData.setIsShowCvldtlName((String) resuleMap.get("isShowCvldtlName"));
                detailData.setIsShowLoanMk((String) resuleMap.get("isShowLoanMk"));

                // 將FORM中日期欄位西元轉民國
                modifyForm = updateService.transApplicationDataUpdateFormData(modifyForm);
                // 檢查錯誤檔是否有此身分證號
                // String idnoExist = updateService.checkIdnoExist(modifyForm.getEvtIdnNo(),modifyForm.getEvtBrDate());
                // modifyForm.setIdnoExist(idnoExist);

                FormSessionHelper.setApplicationDataUpdateForm(modifyForm, request);
                CaseSessionHelper.setApplicationDataUpdateCase(detailData, request);

                log.debug("執行 更正作業 - 案件資料更正 - 查詢頁面 ApplicationDataUpdateAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
            // 資料筆數>1時，將系統畫面導至『申請資料更正作業(BAMO0D030C)』
            else {
                // 設定訊息: W1002 案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                log.debug("執行 更正作業 - 案件資料更正 - 查詢頁面 ApplicationDataUpdateAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ApplicationDataUpdateAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 修改作業存檔 (bamo0d031c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 修改作業存檔 ApplicationDataUpdateAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ApplicationDataUpdateForm iform = (ApplicationDataUpdateForm) form;
        String forward = ConstantKey.FORWARD_UPDATE_SUCCESS;
        try {
            // if (StringUtils.isNotBlank(iform.getDabApNo1()) && StringUtils.isNotBlank(iform.getDabApNo2()) && StringUtils.isNotBlank(iform.getDabApNo3()) && StringUtils.isNotBlank(iform.getDabApNo4())) {
            // iform.setDabApNo(iform.getDabApNo1().trim() + iform.getDabApNo2().trim() + iform.getDabApNo3().trim() + iform.getDabApNo4().trim());
            // }

            BaappbaseDataUpdateCase caseObj = new BaappbaseDataUpdateCase();
            BeanUtility.copyProperties(caseObj, iform);

            // 將事故者姓名是否可修改狀態帶入
            BaappbaseDataUpdateCase origCase = CaseSessionHelper.getApplicationDataUpdateCase(request);
            caseObj.setEvtNameStatus(origCase.getEvtNameStatus());

            // 依畫面輸入欄位條件轉換 給付主檔部分欄位
            caseObj = updateService.transUpdateData(caseObj, origCase, "application");

            // 取得需記錄異動LOG的欄位資料
            // 為不影響前端頁面顯示, 故須複製一份 Form
            ApplicationDataUpdateForm tempForm = new ApplicationDataUpdateForm();
            BeanUtility.copyProperties(tempForm, form);

            // 給付資料更正主檔 改前改後值 for BAAPPLOG
            caseObj.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_APPLICATION_FIELD_RESOURCE_PREFIX));

            // 根據 baappbaseId 更新主檔
            updateService.updateDataForApplication(userData, caseObj);

            // 呼叫即時編審WebService
            // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
            String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
            log.info("webServiceUrl: " + webServiceUrl);
            SingleCheckMarkServiceHttpBindingStub binding;
            SingleCheckMarkServiceLocator singleCheckMarkServiceLocator = new SingleCheckMarkServiceLocator();
            singleCheckMarkServiceLocator.setSingleCheckMarkServiceHttpPortEndpointAddress(webServiceUrl);
            binding = (SingleCheckMarkServiceHttpBindingStub) singleCheckMarkServiceLocator.getSingleCheckMarkServiceHttpPort();
            String returnCode = binding.doCheckMark(caseObj.getApNo(), SecureToken.getInstance().getToken());
            log.debug("執行 更正作業 - 案件資料更正 - 修改作業存檔 - 呼叫即時編審結果... " + returnCode);

            // 返回明細頁面前先確認該筆案件狀態是否可修改
            BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(caseObj.getBaappbaseId(), new String[] { "01", "10", "11", "12", "20" }, null, "in");
            if (detailData != null) {
                // 該筆案件為可修改
                if (("0000").equals(detailData.getSeqNo())) {
                    // 取得 國籍 清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 取得 申請項目 清單
                    request.getSession().setAttribute(ConstantKey.APITEM_OPTION_LIST, selectOptionService.getApItemOldOptionList());
                    // 取得 結案原因 清單
                    request.getSession().setAttribute(ConstantKey.CLOSECAUSE_OPTION_LIST, selectOptionService.getCloseCauseOptionList(detailData.getApNo().substring(0, 1)));
                    // 取得 給付編審 列表
                    Map<String, Object> resuleMap = updateService.getChkListForAppUpdateBy(detailData.getBaappbaseId());
                    request.getSession().setAttribute(ConstantKey.CHKFILE_OPTION_LIST, (List<ChkFileCollectionCase>) resuleMap.get("ChkFile"));
                    // 取得"註銷"按鈕狀態
                    detailData.setDelBtnStatus(updateService.getDelBtnForApplicationUpdate(detailData.getApNo()));
                    // 取得"事故者姓名"可否修改狀態
                    detailData.setEvtNameStatus(updateService.getEvtNameModifyStatusForApplicationUpdate(detailData.getApNo()));
                    // 取得"發放方式"可否修改狀態
                    detailData.setIsShowInterValMonth(updateService.chkInterValMonthStatus(detailData.getApNo()));

                    // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                    ApplicationDataUpdateForm modifyForm = new ApplicationDataUpdateForm();
                    BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
                    detailData.setIsShowCvldtlName((String) resuleMap.get("isShowCvldtlName"));
                    detailData.setIsShowLoanMk((String) resuleMap.get("isShowLoanMk"));

                    // 將FORM中日期欄位西元轉民國
                    modifyForm = updateService.transApplicationDataUpdateFormData(modifyForm);

                    FormSessionHelper.setApplicationDataUpdateForm(modifyForm, request);
                    CaseSessionHelper.setApplicationDataUpdateCase(detailData, request);
                    forward = FORWARD_APPLICATION_DETAIL;
                }
                // 原修改案件狀態已變更, 返回功能首頁
                else {
                    // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                    FormSessionHelper.removeApplicationDataUpdateForm(request);
                    FormSessionHelper.removeApplicationDataUpdateQueryForm(request);
                    FormSessionHelper.removeOnceAmtDataUpdateForm(request);
                    FormSessionHelper.removeOwesDataUpdateForm(request);
                    CaseSessionHelper.removeApplicationDataUpdateCase(request);
                    CaseSessionHelper.removeBasenimaintCaseList(request);

                    forward = ConstantKey.FORWARD_UPDATE_SUCCESS;
                }
            }

            // 設定更新訊息
            if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                // 設定即時編審失敗訊息
                saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
            }
            else {
                // 設定更新成功訊息
                saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(iform.getApNoStrDisplay()));
            }
            log.debug("執行 更正作業 - 案件資料更正 - 修改作業存檔 ApplicationDataUpdateAction.doUpdate() 完成 ... ");
            return mapping.findForward(forward);

        }
        catch (RemoteException ex) {
            FormSessionHelper.removeApplicationDataUpdateForm(request);
            BaappbaseDataUpdateCase detailData = CaseSessionHelper.getApplicationDataUpdateCase(request);
            // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
            ApplicationDataUpdateForm modifyForm = new ApplicationDataUpdateForm();
            BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
            // detailData.setIsShowCvldtlName((String) resuleMap.get("isShowCvldtlName"));

            // 將FORM中日期欄位西元轉民國
            modifyForm = updateService.transApplicationDataUpdateFormData(modifyForm);
            FormSessionHelper.setApplicationDataUpdateForm(modifyForm, request);

            // 設定及時編審失敗訊息
            log.error("ApplicationDataUpdateAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(ex));
            saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
        catch (Exception e) {
            FormSessionHelper.removeApplicationDataUpdateForm(request);
            BaappbaseDataUpdateCase detailData = CaseSessionHelper.getApplicationDataUpdateCase(request);
            // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
            ApplicationDataUpdateForm modifyForm = new ApplicationDataUpdateForm();
            BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
            // detailData.setIsShowCvldtlName((String) resuleMap.get("isShowCvldtlName"));

            // 將FORM中日期欄位西元轉民國
            modifyForm = updateService.transApplicationDataUpdateFormData(modifyForm);
            FormSessionHelper.setApplicationDataUpdateForm(modifyForm, request);

            // 設定更新失敗訊息
            log.error("ApplicationDataUpdateAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 註銷作業 (bamo0d031c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 註銷作業 ApplicationDataUpdateAction.doCancel() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ApplicationDataUpdateForm iform = (ApplicationDataUpdateForm) form;
        try {
            BaappbaseDataUpdateCase caseObj = new BaappbaseDataUpdateCase();
            BeanUtility.copyProperties(caseObj, iform);

            // 根據 baappbaseId 註銷主檔
            updateService.cancelDataForApplication(userData, caseObj);

            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeApplicationDataUpdateForm(request);
            FormSessionHelper.removeApplicationDataUpdateQueryForm(request);
            FormSessionHelper.removeOnceAmtDataUpdateForm(request);
            FormSessionHelper.removeOwesDataUpdateForm(request);
            CaseSessionHelper.removeApplicationDataUpdateCase(request);
            CaseSessionHelper.removeBasenimaintCaseList(request);

            // 設定更新成功訊息
            saveMessages(session, DatabaseMessageHelper.getCancelSuccessMessage());
            log.debug("執行 更正作業 - 案件資料更正 - 註銷作業 ApplicationDataUpdateAction.doCancel() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);
        }
        catch (Exception e) {
            FormSessionHelper.removeApplicationDataUpdateForm(request);
            BaappbaseDataUpdateCase detailData = CaseSessionHelper.getApplicationDataUpdateCase(request);
            // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
            ApplicationDataUpdateForm modifyForm = new ApplicationDataUpdateForm();
            BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
            // detailData.setIsShowCvldtlName((String) resuleMap.get("isShowCvldtlName"));

            // 將FORM中日期欄位西元轉民國
            modifyForm = updateService.transApplicationDataUpdateFormData(modifyForm);
            FormSessionHelper.setApplicationDataUpdateForm(modifyForm, request);

            // 設定更新失敗訊息
            log.error("ApplicationDataUpdateAction.doCancel() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getCancelFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 給付資料更正 - 返回 ApplicationDataUpdateAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removeApplicationDataUpdateForm(request);
        FormSessionHelper.removeApplicationDataUpdateQueryForm(request);
        FormSessionHelper.removeOnceAmtDataUpdateForm(request);
        FormSessionHelper.removeOwesDataUpdateForm(request);
        CaseSessionHelper.removeApplicationDataUpdateCase(request);
        CaseSessionHelper.removeBasenimaintCaseList(request);

        log.debug("執行 更正作業 - 給付資料更正 - 返回 ApplicationDataUpdateAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 更正作業 - 案件資料更正 - 欠費期間維護
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareOwesDataUpate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 欠費期間維護 ApplicationDataUpdateAction.doOwesDataUpate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ApplicationDataUpdateForm iform = (ApplicationDataUpdateForm) form;
        try {
            // 確認該筆案件資料是否存在
            BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(iform.getBaappbaseId(), new String[] { "01", "10", "11", "12", "20" }, null, "in");

            // 案件資料還存在，系統畫面導至『欠費期間維護作業(BAMO0D022N)』
            if (detailData != null) {
                // 根據APNO 取得 欠費更正資料
                List<BasenimaintCase> basenimaintCaseList = updateService.selectBasenimaintDataList(detailData.getApNo(), null, "0");

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                OwesDataUpdateForm modifyForm = new OwesDataUpdateForm();
                BeanUtility.copyProperties(modifyForm, detailData);
                modifyForm.setSeqNo("");

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                FormSessionHelper.setOwesDataUpdateForm(modifyForm, request);
                CaseSessionHelper.setApplicationDataUpdateCase(detailData, request);
                CaseSessionHelper.setBasenimaintCaseList(basenimaintCaseList, request);

                log.debug("執行 更正作業 - 案件資料更正 - 欠費期間維護 ApplicationDataUpdateAction.doOwesDataUpate() 完成 ... ");
                return mapping.findForward(FORWARD_OWES_DATA_UPDATE);
            }
            // 案件資料不存在, 系統畫面切換回『案件資料更正查詢作業(BAMO0D030C)』
            else {
                // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeApplicationDataUpdateForm(request);
                FormSessionHelper.removeApplicationDataUpdateQueryForm(request);
                FormSessionHelper.removeOnceAmtDataUpdateForm(request);
                FormSessionHelper.removeOwesDataUpdateForm(request);
                CaseSessionHelper.removeApplicationDataUpdateCase(request);
                CaseSessionHelper.removeBasenimaintCaseList(request);

                log.debug("執行 更正作業 - 案件資料更正 - 欠費期間維護 ApplicationDataUpdateAction.doOwesDataUpate() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("ApplicationDataUpdateAction.prepareOwesDataUpate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 一次給付資料更正
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward prepareOnceAmtDataUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 ApplicationDataUpdateAction.doOnceAmtDataUpate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ApplicationDataUpdateForm iform = (ApplicationDataUpdateForm) form;
        try {
            // 確認該筆案件資料是否存在
            BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(iform.getBaappbaseId(), new String[] { "01", "10", "11", "12", "20" }, null, "in");

            // 案件資料還存在，系統畫面導至『一次給付資料更正作業(BAMO0D032C)』
            if (detailData != null) {
                // 2009.01.12 修改
                // '職訓薪資扣除起迄'及下方的Grid資料全都先藏起來。
                // 所以相關步驟通通先註解掉
                // [
                // 根據 APNO 取得 一次給付資料
                // List<BasenimaintCase> basenimaintCaseList = updateService.selectBasenimaintDataList(detailData.getApNo(), null, "1");
                // ]

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                OnceAmtDataUpdateForm modifyForm = new OnceAmtDataUpdateForm();
                BeanUtility.copyProperties(modifyForm, detailData);
                modifyForm.setSeqNo("");
                modifyForm.setOldSeniab(detailData.getOldSeniab());
                modifyForm.setOffInsurDate(detailData.getOffInsurDateStr());
                modifyForm.setLawRetireDate(detailData.getLawRetireDateStr());

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                FormSessionHelper.setOnceAmtDataUpdateForm(modifyForm, request);
                CaseSessionHelper.setApplicationDataUpdateCase(detailData, request);

                // 2009.01.12 修改
                // '職訓薪資扣除起迄'及下方的Grid資料全都先藏起來。
                // 所以相關步驟通通先註解掉
                // 根據 APNO 取得 一次給付資料
                // [
                // CaseSessionHelper.setBasenimaintCaseList(basenimaintCaseList, request);
                // ]

                log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 ApplicationDataUpdateAction.doOnceAmtDataUpate() 完成 ... ");
                return mapping.findForward(FORWARD_ONCEAMT_DATA_UPDATE);
            }
            // 案件資料不存在, 系統畫面切換回『案件資料更正查詢作業(BAMO0D030C)』
            else {
                // 設定顯示訊息：W1002-案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeApplicationDataUpdateForm(request);
                FormSessionHelper.removeApplicationDataUpdateQueryForm(request);
                FormSessionHelper.removeOnceAmtDataUpdateForm(request);
                FormSessionHelper.removeOwesDataUpdateForm(request);
                CaseSessionHelper.removeApplicationDataUpdateCase(request);
                CaseSessionHelper.removeBasenimaintCaseList(request);

                log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 ApplicationDataUpdateAction.doOnceAmtDataUpate() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("ApplicationDataUpdateAction.prepareOnceAmtDataUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 選擇事故者姓名
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward chooseEvtName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 選擇事故者姓名 ApplicationDataUpdateAction.chooseEvtName() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ApplicationDataUpdateForm iform = (ApplicationDataUpdateForm) form;
        String forward = FORWARD_APPLICATION_DETAIL;
        try {
            // 取得 身分證號重號資料
            List<ApplicationUpdateDupeIdnNoCase> list = updateService.getDupeIdnNoData(iform.getApNo());

            if (list.size() == 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                forward = FORWARD_APPLICATION_DETAIL;
            }
            else {
                // 將相關 Case 存到 Request Scope
                CaseSessionHelper.setApplicationUpdateDupeIdnNoCaseList(list, request);
                forward = FORWARD_DUPEIDNNO_DATA;
            }
            log.debug("執行 更正作業 - 案件資料更正 - 選擇事故者姓名 ApplicationDataUpdateAction.chooseEvtName() 結束 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("ApplicationDataUpdateAction.chooseEvtName() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_APPLICATION_DETAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 更新事故者/受款人資料
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdateEvtData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 更新事故者/受款人資料 ApplicationDataUpdateAction.doUpdateEvtData() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ApplicationDataUpdateForm iform = (ApplicationDataUpdateForm) form;
        String forward = FORWARD_DUPEIDNNO_DATA;
        try {
            String[] evtData = iform.getEvtData().split(";");
            String evtName = evtData[0]; // 事故者姓名
            String evtIdnNo = evtData[1];// 事故者身分證號
            String evtBrDate = evtData[2];// 事故者出生日期

            // 由案件明細取得受理編號
            BaappbaseDataUpdateCase caseObj = CaseSessionHelper.getApplicationDataUpdateCase(request);
            String apNo = caseObj.getApNo();

            // 更新 主檔事故者/受款人資料
            updateService.updateEvtBenDataForApplicationUpdate(userData, apNo, evtName, evtIdnNo, evtBrDate);

            // 呼叫即時編審WebService
            // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
            String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
            log.info("webServiceUrl: " + webServiceUrl);
            SingleCheckMarkServiceHttpBindingStub binding;
            SingleCheckMarkServiceLocator singleCheckMarkServiceLocator = new SingleCheckMarkServiceLocator();
            singleCheckMarkServiceLocator.setSingleCheckMarkServiceHttpPortEndpointAddress(webServiceUrl);
            binding = (SingleCheckMarkServiceHttpBindingStub) singleCheckMarkServiceLocator.getSingleCheckMarkServiceHttpPort();
            String returnCode = binding.doCheckMark(caseObj.getApNo(), SecureToken.getInstance().getToken());
            log.debug("執行 更正作業 - 案件資料更正 - 修改作業存檔 - 呼叫即時編審結果... " + returnCode);

            // 返回明細頁面前先確認該筆案件狀態是否可修改
            BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(caseObj.getBaappbaseId(), new String[] { "01", "10", "11", "12", "20" }, null, "in");
            if (detailData != null) {
                // 該筆案件為可修改
                if (("0000").equals(detailData.getSeqNo())) {
                    // 取得 國籍 清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 取得 申請項目 清單
                    request.getSession().setAttribute(ConstantKey.APITEM_OPTION_LIST, selectOptionService.getApItemOldOptionList());
                    // 取得 結案原因 清單
                    request.getSession().setAttribute(ConstantKey.CLOSECAUSE_OPTION_LIST, selectOptionService.getCloseCauseOptionList(detailData.getApNo().substring(0, 1)));
                    // 取得 給付編審 列表
                    Map<String, Object> resuleMap = updateService.getChkListForAppUpdateBy(detailData.getBaappbaseId());
                    request.getSession().setAttribute(ConstantKey.CHKFILE_OPTION_LIST, (List<ChkFileCollectionCase>) resuleMap.get("ChkFile"));
                    // 取得"註銷"按鈕狀態
                    detailData.setDelBtnStatus(updateService.getDelBtnForApplicationUpdate(detailData.getApNo()));
                    // 取得"事故者姓名"可否修改狀態
                    detailData.setEvtNameStatus(updateService.getEvtNameModifyStatusForApplicationUpdate(detailData.getApNo()));

                    // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                    ApplicationDataUpdateForm modifyForm = new ApplicationDataUpdateForm();
                    BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
                    detailData.setIsShowCvldtlName((String) resuleMap.get("isShowCvldtlName"));

                    // 將FORM中日期欄位西元轉民國
                    modifyForm = updateService.transApplicationDataUpdateFormData(modifyForm);

                    FormSessionHelper.setApplicationDataUpdateForm(modifyForm, request);
                    CaseSessionHelper.setApplicationDataUpdateCase(detailData, request);
                    forward = FORWARD_APPLICATION_DETAIL;
                }
                // 原修改案件狀態已變更, 返回功能首頁
                else {
                    // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                    FormSessionHelper.removeApplicationDataUpdateForm(request);
                    FormSessionHelper.removeApplicationDataUpdateQueryForm(request);
                    FormSessionHelper.removeOnceAmtDataUpdateForm(request);
                    FormSessionHelper.removeOwesDataUpdateForm(request);
                    CaseSessionHelper.removeApplicationDataUpdateCase(request);
                    CaseSessionHelper.removeBasenimaintCaseList(request);

                    forward = ConstantKey.FORWARD_UPDATE_SUCCESS;
                }
            }

            // 設定更新訊息
            if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                // 設定即時編審失敗訊息
                saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
            }
            else {
                // 設定更新成功訊息
                saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(iform.getApNoStrDisplay()));
            }
            log.debug("執行 更正作業 - 案件資料更正 - 更新事故者/受款人資料 ApplicationDataUpdateAction.doUpdateEvtData() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("ApplicationDataUpdateAction.doUpdateEvtData() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_DUPEIDNNO_DATA);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 返回資料明細
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 返回資料明細 ApplicationDataUpdateAction.doBackDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ApplicationDataUpdateForm iform = (ApplicationDataUpdateForm) form;
        String forward = ConstantKey.FORWARD_BACK;
        try {
            BaappbaseDataUpdateCase caseObj = CaseSessionHelper.getApplicationDataUpdateCase(request);

            // 返回明細頁面前先確認該筆案件狀態是否可修改
            BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(caseObj.getBaappbaseId(), new String[] { "01", "10", "11", "12", "20" }, null, "in");
            if (detailData != null) {
                // 該筆案件為可修改
                if (("0000").equals(detailData.getSeqNo())) {
                    // 取得 國籍 清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 取得 申請項目 清單
                    request.getSession().setAttribute(ConstantKey.APITEM_OPTION_LIST, selectOptionService.getApItemOldOptionList());
                    // 取得 結案原因 清單
                    request.getSession().setAttribute(ConstantKey.CLOSECAUSE_OPTION_LIST, selectOptionService.getCloseCauseOptionList(detailData.getApNo().substring(0, 1)));
                    // 取得 給付編審 列表
                    Map<String, Object> resuleMap = updateService.getChkListForAppUpdateBy(detailData.getBaappbaseId());
                    request.getSession().setAttribute(ConstantKey.CHKFILE_OPTION_LIST, (List<ChkFileCollectionCase>) resuleMap.get("ChkFile"));
                    // 取得"註銷"按鈕狀態
                    detailData.setDelBtnStatus(updateService.getDelBtnForApplicationUpdate(detailData.getApNo()));
                    // 取得"事故者姓名"可否修改狀態
                    detailData.setEvtNameStatus(updateService.getEvtNameModifyStatusForApplicationUpdate(detailData.getApNo()));

                    // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                    ApplicationDataUpdateForm modifyForm = new ApplicationDataUpdateForm();
                    BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
                    detailData.setIsShowCvldtlName((String) resuleMap.get("isShowCvldtlName"));

                    // 將FORM中日期欄位西元轉民國
                    modifyForm = updateService.transApplicationDataUpdateFormData(modifyForm);

                    FormSessionHelper.setApplicationDataUpdateForm(modifyForm, request);
                    CaseSessionHelper.setApplicationDataUpdateCase(detailData, request);
                    forward = FORWARD_APPLICATION_DETAIL;
                }
                // 原修改案件狀態已變更, 返回功能首頁
                else {
                    // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                    FormSessionHelper.removeApplicationDataUpdateForm(request);
                    FormSessionHelper.removeApplicationDataUpdateQueryForm(request);
                    FormSessionHelper.removeOnceAmtDataUpdateForm(request);
                    FormSessionHelper.removeOwesDataUpdateForm(request);
                    CaseSessionHelper.removeApplicationDataUpdateCase(request);
                    CaseSessionHelper.removeBasenimaintCaseList(request);

                    forward = ConstantKey.FORWARD_BACK;
                }
            }

            log.debug("執行 更正作業 - 案件資料更正 - 返回資料明細 ApplicationDataUpdateAction.doBackDetail() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定失敗訊息
            log.error("ApplicationDataUpdateAction.doBackDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
}
