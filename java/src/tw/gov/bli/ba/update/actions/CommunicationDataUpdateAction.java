package tw.gov.bli.ba.update.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.forms.CommunicationDataUpdateForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 通訊資料更正 (BAM0D010C)
 * 
 * @author Rickychi
 */
public class CommunicationDataUpdateAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(CommunicationDataUpdateAction.class);
    public static final String CSRF_TOKEN = "csrfToken";
    private UpdateService updateService;
    // 更正作業 - 通訊資料更正 - 通訊資料詳細頁面
    private static final String FORWARD_COMMUN_DETAIL = "communDetail";
    // 更正作業 - 通訊資料更正 - 通訊資料清單頁面
    private static final String FORWARD_COMMUN_LIST = "communList";

    /**
     * 更正作業 - 通訊資料更正 - 查詢頁面 (bamo0d010c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 通訊資料更正 - 查詢頁面 CommunicationDataUpdateAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        CommunicationDataUpdateForm iform = (CommunicationDataUpdateForm) form;
        String forward = ConstantKey.FORWARD_QUERY_FAIL;
        
        try {
        	//csrffail
        	if(!validateCsrfToken(request))
        		return mapping.findForward(ConstantKey.CSRF_FAIL);
            List<BaappbaseDataUpdateCase> dataList = updateService.selectBaappbaseDataList(null, iform.getApNoStr(), null, new String[] { "90", "99" }, "not in");

            // 資料筆數<=0時，於下方訊息區顯示訊息(MSG：W0040無查詢資料)。
            if (dataList.size() <= 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                forward = ConstantKey.FORWARD_QUERY_FAIL;
            }
            // 資料筆數=1時，查詢該筆明細資料，將系統畫面導至『通訊資料更正作業(BAMO0D011C)』
            // else if (dataList.size() == 1) {//
            // BaappbaseDataUpdateCase obj = (BaappbaseDataUpdateCase) dataList.get(0);
            // BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(obj.getBaappbaseId(), "('90', '99')", null, "not in");
            //
            // if (detailData != null) {
            // if (("2").equals(detailData.getCommTyp())) {
            // // 根據 事故者身分證號, 出生日期 取得戶政資料
            // Cvldtl cvldtlData = updateService.selectCvldtlNameBy(detailData.getBenIdnNo(), detailData.getBenBrDate());
            // detailData.setCvlDtlCommZip(cvldtlData.getHzip());
            // detailData.setCvlDtlCommAddr(cvldtlData.getHaddr());
            // }else{
            // detailData.setCvlDtlCommZip(detailData.getCommZip());
            // detailData.setCvlDtlCommAddr(detailData.getCommAddr());
            // }
            //
            // // 將欲修改的資料帶到 Form 中, 並將 Form 及相關 List Case / Detail Case 存到 Request Scope
            // CommunicationDataUpdateForm modifyForm = new CommunicationDataUpdateForm();
            // BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);
            // FormSessionHelper.setCommunicationDataUpdateForm(modifyForm, request);
            // CaseSessionHelper.setCommunicationDataUpdateCase(detailData, request);
            // forward = FORWARD_COMMUN_DETAIL;
            // }
            // else {
            // // 設定查無資料訊息
            // saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
            // forward = ConstantKey.FORWARD_QUERY_FAIL;
            // }
            // }
            // 資料筆數=>1時，將系統畫面導至『通訊資料更正查詢列表作業(BAMO0D012C)』
            else {
                // 排除遺屬年金事故者資料
                dataList = updateService.excludeEvtDataForSurvivor(dataList);

                BaappbaseDataUpdateCase obj = (BaappbaseDataUpdateCase) dataList.get(0);

                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setCommunicationDataUpdateList(dataList, request);
                CaseSessionHelper.setCommunicationDataUpdateCase(obj, request);

                forward = ConstantKey.FORWARD_QUERY_SUCCESS;
            }
            log.debug("執行 更正作業 - 通訊資料更正 - 查詢頁面 CommunicationDataUpdateAction.doQuery() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CommunicationDataUpdateAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 更正作業 - 通訊資料更正 - 詳細資料頁面 (bamo0d012c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doModifyDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 通訊資料更正 - 詳細資料頁面 CommunicationDataUpdateAction.doModifyDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        CommunicationDataUpdateForm iform = (CommunicationDataUpdateForm) form;
        String forward = FORWARD_COMMUN_LIST;

        try {
        	//csrffail
        	if(!validateCsrfToken(request))
        		return mapping.findForward(ConstantKey.CSRF_FAIL);
            // 取得給付主檔詳細資料
            BaappbaseDataUpdateCase detailData = updateService.getBaappbaseDetail(iform.getBaappbaseId(), new String[] { "90", "99" }, null, "not in");

            if (detailData != null) {
                // 將欲修改的資料帶到 Form 中, 並將 Form 存到 Request Scope
                CommunicationDataUpdateForm modifyForm = new CommunicationDataUpdateForm();
                BeanUtility.copyPropertiesForUpdate(modifyForm, detailData, ConstantKey.OLD_FIELD_PREFIX);

                FormSessionHelper.setCommunicationDataUpdateForm(modifyForm, request);
                CaseSessionHelper.setCommunicationDataUpdateCase(detailData, request);
                forward = FORWARD_COMMUN_DETAIL;
            }
            else {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                forward = FORWARD_COMMUN_LIST;
            }
            log.debug("執行 更正作業 - 通訊資料更正 - 詳細資料頁面 CommunicationDataUpdateAction.doModifyDetail() 完成 ... ");
            return mapping.findForward(forward);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CommunicationDataUpdateAction.doModifyDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_COMMUN_LIST);
        }
    }

    /**
     * 更正作業 - 通訊資料更正 - 修改作業存檔 (bamo0d011c.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 通訊資料更正 - 修改作業存檔 CommunicationDataUpdateAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CommunicationDataUpdateForm iform = (CommunicationDataUpdateForm) form;
        try {
        	//csrffail
        	if(!validateCsrfToken(request))
        		return mapping.findForward(ConstantKey.CSRF_FAIL);
            BaappbaseDataUpdateCase caseObj = new BaappbaseDataUpdateCase();
            BeanUtility.copyProperties(caseObj, iform);

            // 依畫面輸入欄位條件轉換 給付主檔部分欄位
            caseObj = updateService.transUpdateData(caseObj, null, "communication");

            // 取得需記錄異動LOG的欄位資料
            // 為不影響前端頁面顯示, 故須複製一份 Form
            CommunicationDataUpdateForm tempForm = new CommunicationDataUpdateForm();
            BeanUtility.copyProperties(tempForm, form);
            tempForm.setCommZip(caseObj.getCommZip());
            tempForm.setCommAddr(caseObj.getCommAddr());
            caseObj.setBaapplogList(LoggingHelper.getBaapplogList(tempForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_COMM_FIELD_RESOURCE_PREFIX));

            // 根據 baappbaseId 更新主檔
            updateService.updateDataForCommunication(userData, caseObj);

            List<BaappbaseDataUpdateCase> dataList = updateService.selectBaappbaseDataList(null, iform.getApNo(), null, new String[] { "90", "99" }, "not in");

            // 資料筆數<=0時，將系統畫面導至『通訊資料更正查詢作業(BAMO0D010C)』
            if (dataList.size() <= 0) {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeCommunicationDataUpdateQueryForm(request);
                FormSessionHelper.removeCommunicationDataUpdateForm(request);
                CaseSessionHelper.removeCommunicationDataUpdateList(request);

                // 設定更新成功訊息
                saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(iform.getApNoStrDisplay()));
                log.debug("執行 更正作業 - 通訊資料更正 - 修改作業存檔 CommunicationDataUpdateAction.doUpdate() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
            }
            // 資料筆數>1時，將系統畫面導至『通訊資料更正查詢列表作業(BAMO0D012C)』
            else {
                // 排除遺屬年金事故者資料
                dataList = updateService.excludeEvtDataForSurvivor(dataList);
                // 將 查詢結果清單 存到 Request Scope
                CaseSessionHelper.setCommunicationDataUpdateList(dataList, request);
                // 設定更新成功訊息
                saveMessages(session, DatabaseMessageHelper.getReceiptUpdateSuccessMessage(iform.getApNoStrDisplay()));
                log.debug("執行 更正作業 - 通訊資料更正 - 修改作業存檔 CommunicationDataUpdateAction.doUpdate() 完成 ... ");
                return mapping.findForward(FORWARD_COMMUN_LIST);
            }
        }
        catch (Exception e) {
            // 設定更新失敗訊息
            log.error("CommunicationDataUpdateAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 通訊資料更正 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 通訊資料更正 - 返回 CommunicationDataUpdateAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removeCommunicationDataUpdateQueryForm(request);
        FormSessionHelper.removeCommunicationDataUpdateForm(request);
        CaseSessionHelper.removeCommunicationDataUpdateList(request);

        log.debug("執行 更正作業 - 通訊資料更正 - 返回 CommunicationDataUpdateAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    /**
     * 更正作業 - 通訊資料更正 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doModifyBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 通訊資料更正 - 返回 CommunicationDataUpdateAction.doModifyBack() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        CommunicationDataUpdateForm iform = (CommunicationDataUpdateForm) form;

        try {
            String apNo = iform.getApNo();// 受理編號
            List<BaappbaseDataUpdateCase> dataList = updateService.selectBaappbaseDataList(null, apNo, null, new String[] { "90", "99" }, "not in");

            // 資料筆數<=0時，將系統畫面導至『通訊資料更正查詢作業(BAMO0D010C)』
            if (dataList.size() <= 0) {
                // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
                FormSessionHelper.removeCommunicationDataUpdateQueryForm(request);
                FormSessionHelper.removeCommunicationDataUpdateForm(request);
                CaseSessionHelper.removeCommunicationDataUpdateList(request);

                log.debug("執行 更正作業 - 通訊資料更正 - 返回 CommunicationDataUpdateAction.doModifyBack() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }
            // 資料筆數>=1時，將系統畫面導至『通訊資料更正查詢列表作業(BAMO0D012C)』
            else {
                // 排除遺屬年金事故者資料
                dataList = updateService.excludeEvtDataForSurvivor(dataList);

                BaappbaseDataUpdateCase obj = (BaappbaseDataUpdateCase) dataList.get(0);

                // 將 查詢結果清單 及相關 List Case / Detail Case 存到 Request Scope
                CaseSessionHelper.setCommunicationDataUpdateList(dataList, request);
                CaseSessionHelper.setCommunicationDataUpdateCase(obj, request);

                log.debug("執行 更正作業 - 通訊資料更正 - 返回 CommunicationDataUpdateAction.doModifyBack() 完成 ... ");
                return mapping.findForward(FORWARD_COMMUN_LIST);
            }
        }
        catch (Exception e) {
            log.error("CommunicationDataUpdateAction.doModifyBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        }
    }
    /**
     * csrf validate
     * @param request
     * @return
     * @throws Exception
     */
    private boolean validateCsrfToken(HttpServletRequest request) throws Exception {
    	HttpSession session = request.getSession();
        Object tokenId = session.getAttribute(CSRF_TOKEN);
		if(tokenId!=null && StringUtils.equals(String.valueOf(tokenId), request.getParameter(CSRF_TOKEN))){
			return true;
		}
		return false;
	}
    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }
}
