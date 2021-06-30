package tw.gov.bli.ba.update.actions;

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
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Basenimaint;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.cases.BasenimaintCase;
import tw.gov.bli.ba.update.cases.ChkFileCollectionCase;
import tw.gov.bli.ba.update.forms.ApplicationDataUpdateForm;
import tw.gov.bli.ba.update.forms.OnceAmtDataUpdateForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceHttpBindingStub;
import tw.gov.bli.ba.webservices.SingleCheckMarkServiceLocator;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 案件資料更正 - 一次給付資料更正(BAMO0D032C)
 * 
 * @author Rickychi
 */
public class OnceAmtDataUpdateAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OnceAmtDataUpdateAction.class);

    private UpdateService updateService;
    private SelectOptionService selectOptionService;
    // 更正作業 - 案件資料更正 - 查詢頁面
    private static final String FORWARD_APPLICATION_DATA_UPDATE = "applicationDateUpdate";
    // 更正作業 - 案件資料更正 - 確認作業成功
    private static final String FORWARD_ONCEAMT_CONFIRM_SUCCESS = "onceAmtConfirmSuccess";
    // 更正作業 - 案件資料更正 - 確認作業失敗
    private static final String FORWARD_ONCEAMT_CONFIRM_FALI = "onceAmtConfirmFail";

    /**
     * 更正作業 - 案件資料更正 - 一次給付資料更正 新增作業 (bamo0d032c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 新增作業 OnceAmtDataUpdateAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OnceAmtDataUpdateForm iform = (OnceAmtDataUpdateForm) form;
        try {
            // 將 給付主檔／form 資料 複製到 年資維護介接檔/給付主檔
            Basenimaint basenimaint = new Basenimaint();
            BaappbaseDataUpdateCase caseObj = CaseSessionHelper.getApplicationDataUpdateCase(request);
            BeanUtility.copyProperties(basenimaint, caseObj);
            basenimaint.setSeniTyp("1");
            basenimaint.setUbno(caseObj.getLsUbno());
            basenimaint.setUbnoCk(caseObj.getLsUbnoCk());
            basenimaint.setOwesMk(iform.getOwesMk());
            basenimaint.setOldSeniab(iform.getOldSeniab());
            basenimaint.setOwesBdate(DateUtility.changeDateType(iform.getOwesBdate()));
            basenimaint.setOwesEdate(DateUtility.changeDateType(iform.getOwesEdate()));

            // 轉公保資料
            Baappbase baappbase = new Baappbase();
            baappbase.setApNo(caseObj.getApNo());
            baappbase.setReSeniMk(iform.getReSeniMk());
            baappbase.setOffInsurDate(DateUtility.changeDateType(iform.getOffInsurDate()));
            baappbase.setLawRetireDate(DateUtility.changeDateType(iform.getLawRetireDate()));

            // 修改轉公保資料/新增年資維護介接檔
            updateService.insertBasenimaintData(userData, baappbase, basenimaint, "onceAmt");

            // 確認該筆案件資料是否存在
            BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(caseObj.getBaappbaseId(), new String[] { "01", "10", "11", "12", "20", "30" }, null, "in");
            // 案件資料還存在，系統畫面導至『一次給付資料更正作業(BAMO0D032C)』
            if (detailData != null) {
                // 根據APNO 取得 欠費更正資料
                List<BasenimaintCase> basenimaintCaseList = updateService.selectBasenimaintDataList(detailData.getApNo(), null, "1");

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                OnceAmtDataUpdateForm modifyForm = new OnceAmtDataUpdateForm();
                BeanUtility.copyProperties(modifyForm, detailData);
                modifyForm.setSeqNo("");
                modifyForm.setOffInsurDate(detailData.getOffInsurDateStr());
                modifyForm.setLawRetireDate(detailData.getLawRetireDateStr());

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                FormSessionHelper.setOnceAmtDataUpdateForm(modifyForm, request);
                CaseSessionHelper.setApplicationDataUpdateCase(detailData, request);
                CaseSessionHelper.setBasenimaintCaseList(basenimaintCaseList, request);

                // 設定顯示訊息：G0001 資料新增成功
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());

                log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 新增作業 OnceAmtDataUpdateAction.doInsert() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
            }
            // 案件資料不存在, 系統畫面切換回『案件資料更正查詢作業(BAMO0D030C)』
            else {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeApplicationDataUpdateForm(request);
                FormSessionHelper.removeOnceAmtDataUpdateForm(request);
                FormSessionHelper.removeOwesDataUpdateForm(request);
                CaseSessionHelper.removeApplicationDataUpdateCase(request);
                CaseSessionHelper.removeBasenimaintCaseList(request);

                // 設定顯示訊息：G1004 資料更新成功！案件狀態已變更，請重新執行
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessStatusChangeMessage());

                log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 新增作業 OnceAmtDataUpdateAction.doInsert() 完成 ... ");
                return mapping.findForward(FORWARD_APPLICATION_DATA_UPDATE);
            }
        }
        catch (Exception e) {
            // 設定失敗訊息：E1001 資料新增失敗
            log.error("OnceAmtDataUpdateAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 一次給付資料更正 修改作業 (bamo0d032c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 修改作業 OnceAmtDataUpdateAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OnceAmtDataUpdateForm iform = (OnceAmtDataUpdateForm) form;
        try {
            // 將 給付主檔／form 資料 複製到 年資維護介接檔
            Basenimaint basenimaint = new Basenimaint();
            Basenimaint basenimaintData = new Basenimaint();
            BaappbaseDataUpdateCase caseObj = CaseSessionHelper.getApplicationDataUpdateCase(request);
            BeanUtility.copyProperties(basenimaint, caseObj);
            BeanUtility.copyProperties(basenimaintData, caseObj);
            basenimaint.setSeqNo(iform.getSeqNo());
            basenimaint.setSeniTyp("1");
            basenimaint.setUbno(caseObj.getLsUbno());
            basenimaint.setUbnoCk(caseObj.getLsUbnoCk());
            basenimaint.setOwesMk(iform.getOwesMk());
            basenimaint.setOldSeniab(iform.getOldSeniab());
            basenimaint.setOwesBdate(DateUtility.changeDateType(iform.getOwesBdate()));
            basenimaint.setOwesEdate(DateUtility.changeDateType(iform.getOwesEdate()));

            // 轉公保資料
            Baappbase baappbase = new Baappbase();
            baappbase.setApNo(caseObj.getApNo());
            baappbase.setReSeniMk(iform.getReSeniMk());
            baappbase.setOffInsurDate(DateUtility.changeDateType(iform.getOffInsurDate()));
            baappbase.setLawRetireDate(DateUtility.changeDateType(iform.getLawRetireDate()));

            // 修改轉公保資料/年資維護介接檔
            updateService.updateBasenimaintData(userData, baappbase, basenimaint, "onceAmt", basenimaintData);

            // 確認該筆案件資料是否存在
            BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(caseObj.getBaappbaseId(), new String[] { "01", "10", "11", "12", "20", "30" }, null, "in");
            // 案件資料還存在，系統畫面導至『一次給付資料更正作業(BAMO0D032C)』
            if (detailData != null) {
                // 根據APNO 取得 欠費更正資料
                List<BasenimaintCase> basenimaintCaseList = updateService.selectBasenimaintDataList(detailData.getApNo(), null, "1");

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                OnceAmtDataUpdateForm modifyForm = new OnceAmtDataUpdateForm();
                BeanUtility.copyProperties(modifyForm, detailData);
                modifyForm.setSeqNo("");
                modifyForm.setOffInsurDate(detailData.getOffInsurDateStr());
                modifyForm.setLawRetireDate(detailData.getLawRetireDateStr());

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                FormSessionHelper.setOnceAmtDataUpdateForm(modifyForm, request);
                CaseSessionHelper.setApplicationDataUpdateCase(detailData, request);
                CaseSessionHelper.setBasenimaintCaseList(basenimaintCaseList, request);

                // 設定顯示訊息：G0002 資料更新成功
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());

                log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 修改作業 OnceAmtDataUpdateAction.doUpdate() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
            }
            // 案件資料不存在, 系統畫面切換回『案件資料更正查詢作業(BAMO0D030C)』
            else {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeApplicationDataUpdateForm(request);
                FormSessionHelper.removeOnceAmtDataUpdateForm(request);
                FormSessionHelper.removeOwesDataUpdateForm(request);
                CaseSessionHelper.removeApplicationDataUpdateCase(request);
                CaseSessionHelper.removeBasenimaintCaseList(request);

                // 設定顯示訊息：G1004 資料更新成功！案件狀態已變更，請重新執行
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessStatusChangeMessage());
                log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 修改作業 OnceAmtDataUpdateAction.doUpdate() 完成 ... ");
                return mapping.findForward(FORWARD_APPLICATION_DATA_UPDATE);
            }
        }
        catch (Exception e) {
            // 設定失敗訊息：E1002 資料更新失敗
            log.error("OnceAmtDataUpdateAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 一次給付資料更正 刪除作業 (bamo0d032c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正刪除作業 OnceAmtDataUpdateAction.doDelete() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OnceAmtDataUpdateForm iform = (OnceAmtDataUpdateForm) form;
        try {
            // 將 給付主檔／form 資料 複製到 年資維護介接檔
            Basenimaint basenimaint = new Basenimaint();
            BaappbaseDataUpdateCase caseObj = CaseSessionHelper.getApplicationDataUpdateCase(request);
            BeanUtility.copyProperties(basenimaint, caseObj);
            basenimaint.setSeqNo(iform.getSeqNo());
            basenimaint.setSeniTyp("1");
            basenimaint.setUbno(caseObj.getLsUbno());
            basenimaint.setUbnoCk(caseObj.getLsUbnoCk());
            basenimaint.setOwesMk(iform.getOwesMk());
            basenimaint.setOldSeniab(iform.getOldSeniab());
            basenimaint.setOwesBdate(DateUtility.changeDateType(iform.getOwesBdate()));
            basenimaint.setOwesEdate(DateUtility.changeDateType(iform.getOwesEdate()));

            // 修改 年資維護介接檔
            updateService.daleteBasenimaintData(userData, basenimaint);

            // 確認該筆案件資料是否存在
            BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(caseObj.getBaappbaseId(), new String[] { "01", "10", "11", "12", "20", "30" }, null, "in");
            // 案件資料還存在，系統畫面導至『一次給付資料更正作業(BAMO0D032C)』
            if (detailData != null) {
                // 根據APNO 取得 欠費更正資料
                List<BasenimaintCase> basenimaintCaseList = updateService.selectBasenimaintDataList(detailData.getApNo(), null, "1");

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                OnceAmtDataUpdateForm modifyForm = new OnceAmtDataUpdateForm();
                BeanUtility.copyProperties(modifyForm, detailData);
                modifyForm.setSeqNo("");
                modifyForm.setOffInsurDate(detailData.getOffInsurDateStr());
                modifyForm.setLawRetireDate(detailData.getLawRetireDateStr());

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                FormSessionHelper.setOnceAmtDataUpdateForm(modifyForm, request);
                CaseSessionHelper.setApplicationDataUpdateCase(detailData, request);
                CaseSessionHelper.setBasenimaintCaseList(basenimaintCaseList, request);

                // 設定顯示訊息：G0003 資料刪除成功
                saveMessages(session, DatabaseMessageHelper.getDeleteSuccessMessage());

                log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 刪除作業 OnceAmtDataUpdateAction.doDelete() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);
            }
            // 案件資料不存在, 系統畫面切換回『案件資料更正查詢作業(BAMO0D030C)』
            else {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeApplicationDataUpdateForm(request);
                FormSessionHelper.removeOnceAmtDataUpdateForm(request);
                FormSessionHelper.removeOwesDataUpdateForm(request);
                CaseSessionHelper.removeApplicationDataUpdateCase(request);
                CaseSessionHelper.removeBasenimaintCaseList(request);

                // 設定顯示訊息：G1004 資料更新成功！案件狀態已變更，請重新執行
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessStatusChangeMessage());
                log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 刪除作業 OnceAmtDataUpdateAction.doDelete() 完成 ... ");
                return mapping.findForward(FORWARD_APPLICATION_DATA_UPDATE);
            }
        }
        catch (Exception e) {
            // 設定失敗訊息：E1003 資料刪除失敗
            log.error("OnceAmtDataUpdateAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getDeleteFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 一次給付資料更正 確認作業 (bamo0d032c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 確認作業 OnceAmtDataUpdateAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        OnceAmtDataUpdateForm iform = (OnceAmtDataUpdateForm) form;
        String forward = FORWARD_ONCEAMT_CONFIRM_SUCCESS;

        try {
            // 將 給付主檔／form 資料 複製到 年資維護介接檔/給付主檔
            // Basenimaint basenimaint = new Basenimaint();
            BaappbaseDataUpdateCase caseObj = CaseSessionHelper.getApplicationDataUpdateCase(request);
            // BeanUtility.copyProperties(basenimaint, caseObj);
            // basenimaint.setSeniTyp("1");
            // basenimaint.setUbno(caseObj.getLsUbno());
            // basenimaint.setUbnoCk(caseObj.getLsUbnoCk());
            // basenimaint.setOwesMk(iform.getOwesMk());
            // basenimaint.setOldSeniab(iform.getOldSeniab());
            // basenimaint.setOwesBdate(DateUtility.changeDateType(iform.getOwesBdate()));
            // basenimaint.setOwesEdate(DateUtility.changeDateType(iform.getOwesEdate()));

            // 轉公保資料
            Baappbase baappbase = new Baappbase();
            baappbase.setApNo(caseObj.getApNo());
            baappbase.setReSeniMk(iform.getReSeniMk());
            baappbase.setOldSeniab(iform.getOldSeniab());
            baappbase.setOffInsurDate(DateUtility.changeDateType(iform.getOffInsurDate()));
            baappbase.setLawRetireDate(DateUtility.changeDateType(iform.getLawRetireDate()));
            baappbase.setDabApNo(iform.getDabApNo());

            // 修改轉公保資料
            updateService.updateBaappbaseDataForOnceAmtUpdate(userData, baappbase);

            // 呼叫即時編審WebService
            // String webServiceUrl = (ResourceBundle.getBundle("webServiceUrl")).getString("checkMarkWebServicesUrlForOldage");
            String webServiceUrl = PropertyHelper.getProperty("checkMarkWebServicesUrlForOldage");
            log.info("webServiceUrl: " + webServiceUrl);
            SingleCheckMarkServiceHttpBindingStub binding;
            binding = (SingleCheckMarkServiceHttpBindingStub) new SingleCheckMarkServiceLocator().getSingleCheckMarkServiceHttpPort();
            String returnCode = binding.doCheckMark(caseObj.getApNo(), SecureToken.getInstance().getToken());
            log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 確認作業 - 呼叫即時編審結果... " + returnCode);

            // 返回明細頁面前先確認該筆案件狀態是否可修改
            BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(caseObj.getBaappbaseId(), new String[] { "01", "10", "11", "12", "20", "30" }, null, "in");
            if (detailData != null) {
                // 該筆案件為可修改
                if (("0000").equals(detailData.getSeqNo())) {
                    // 取得 國籍 清單
                    request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                    // 取得 申請項目 清單
                    request.getSession().setAttribute(ConstantKey.APITEM_OPTION_LIST, selectOptionService.getApItemOptionList());
                    // 取得 結案原因 清單
                    request.getSession().setAttribute(ConstantKey.CLOSECAUSE_OPTION_LIST, selectOptionService.getCloseCauseOptionList(detailData.getApNo().substring(0, 1)));
                    // 取得 給付編審 列表
                    Map<String, Object> resuleMap = updateService.getChkListForAppUpdateBy(detailData.getBaappbaseId());
                    request.getSession().setAttribute(ConstantKey.CHKFILE_OPTION_LIST, (List<ChkFileCollectionCase>) resuleMap.get("ChkFile"));

                    // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                    ApplicationDataUpdateForm modifyForm = new ApplicationDataUpdateForm();
                    BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
                    detailData.setIsShowCvldtlName((String) resuleMap.get("isShowCvldtlName"));

                    // 將FORM中日期欄位西元轉民國
                    modifyForm = updateService.transApplicationDataUpdateFormData(modifyForm);

                    FormSessionHelper.setApplicationDataUpdateForm(modifyForm, request);
                    CaseSessionHelper.setApplicationDataUpdateCase(detailData, request);
                    forward = FORWARD_ONCEAMT_CONFIRM_SUCCESS;
                }
                // 原修改案件狀態已變更, 返回功能首頁
                else {
                    // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                    FormSessionHelper.removeApplicationDataUpdateForm(request);
                    FormSessionHelper.removeOnceAmtDataUpdateForm(request);
                    FormSessionHelper.removeOwesDataUpdateForm(request);
                    CaseSessionHelper.removeApplicationDataUpdateCase(request);
                    CaseSessionHelper.removeBasenimaintCaseList(request);

                    forward = FORWARD_APPLICATION_DATA_UPDATE;
                }
            }

            // 設定更新訊息
            if ((ConstantKey.DO_CHECK_MARK_FAIL).equals(returnCode)) {
                // 設定即時編審失敗訊息
                saveMessages(session, CustomMessageHelper.getCheckMarkFailMessage());
            }
            else {
                // 設定更新成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }
            log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 確認作業 OnceAmtDataUpdateAction.doConfirm() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定失敗訊息：E1002 資料更新失敗
            log.error("OnceAmtDataUpdateAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(FORWARD_ONCEAMT_CONFIRM_FALI);
        }
    }

    /**
     * 更正作業 - 案件資料更正 - 一次給付資料更正 - 返回(bamo0d032c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 - 返回 OnceAmtDataUpdateAction.doBack() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        OnceAmtDataUpdateForm iform = (OnceAmtDataUpdateForm) form;

        try {
            List<BaappbaseDataUpdateCase> dataList = updateService.selectBaappbaseDataList(null, iform.getApNo(), "0000", new String[] { "01", "10", "11", "12", "20", "30" }, "in");

            if (dataList.size() == 1) {//
                BaappbaseDataUpdateCase obj = (BaappbaseDataUpdateCase) dataList.get(0);
                BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(obj.getBaappbaseId(), null, null, null);

                // 取得 國籍 清單
                request.getSession().setAttribute(ConstantKey.COUNTRYID_OPTION_LIST, selectOptionService.getCountry());
                // 取得 申請項目 清單
                request.getSession().setAttribute(ConstantKey.APITEM_OPTION_LIST, selectOptionService.getApItemOptionList());
                // 取得 結案原因 清單
                request.getSession().setAttribute(ConstantKey.CLOSECAUSE_OPTION_LIST, selectOptionService.getCloseCauseOptionList(detailData.getApNo().substring(0, 1)));
                // 取得 給付編審 列表
                Map<String, Object> resuleMap = updateService.getChkListForAppUpdateBy(detailData.getBaappbaseId());
                request.getSession().setAttribute(ConstantKey.CHKFILE_OPTION_LIST, (List<ChkFileCollectionCase>) resuleMap.get("ChkFile"));

                // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
                ApplicationDataUpdateForm modifyForm = new ApplicationDataUpdateForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
                detailData.setIsShowCvldtlName((String) resuleMap.get("isShowCvldtlName"));

                // 將FORM中日期欄位西元轉民國
                modifyForm = updateService.transApplicationDataUpdateFormData(modifyForm);

                FormSessionHelper.setApplicationDataUpdateForm(modifyForm, request);
                CaseSessionHelper.setApplicationDataUpdateCase(detailData, request);

                log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 - 返回 OnceAmtDataUpdateAction.doBack() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
            // 受理案件資料筆數<=0或>1時，系統畫面導回『案件資料更正查詢作業(BAMO0D030C)』
            else {
                // 設定訊息: W1002 案件狀態已變更，請確認後重新執行
                saveMessages(session, DatabaseMessageHelper.getStatusChangeMessage());

                log.debug("執行 更正作業 - 案件資料更正 - 一次給付資料更正 - 返回 OnceAmtDataUpdateAction.doBack() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("OnceAmtDataUpdateAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
