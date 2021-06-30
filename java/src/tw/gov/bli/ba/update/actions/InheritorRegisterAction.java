package tw.gov.bli.ba.update.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.LoggingHelper;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.InheritorRegisterCase;
import tw.gov.bli.ba.update.forms.InheritorRegisterForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.DateUtil;

/**
 * 更正作業 - 債務繼承人資料登錄作業 - 查詢頁面 (bamo0d050a.jsp)
 * 
 * @author jerry
 */
public class InheritorRegisterAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(InheritorRegisterAction.class);
    private static final String FORWARD_ADD = "queryAdd"; // 債務繼承人資料登錄作業新增
    private static final String FORWARD_ADD_FAIL = "queryAddFail"; // 債務繼承人資料登錄作業新增失敗
    private static final String FORWARD_QUERY_MODIFY = "queryModify"; // 債務繼承人資料登錄作業修改清單
    private static final String FORWARD_MODIFY = "modify"; // 債務繼承人資料登錄作業修改清單
    private static final String FORWARD_BACK_LIST = "backList"; // 返回債務繼承人資料登錄作業修改清單
    private static final String FORWARD_BACK = "back"; // 返回債務繼承人資料登錄作業
    private UpdateService updateService;

    /**
     * 更正作業 - 債務繼承人資料登錄作業 - 查詢頁面 (bamo0d050a.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 債務繼承人資料登錄作業 - 查詢頁面 InheritorRegisterAction.doAdd() 開始 ... ");

        HttpSession session = request.getSession();
        // UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        InheritorRegisterForm queryForm = (InheritorRegisterForm) form;

        try {
            // String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            InheritorRegisterCase caseObj = CaseSessionHelper.getInheritorRegisterCase(request);
            String apno = caseObj.getApNo();
            InheritorRegisterCase querycase = new InheritorRegisterCase();

            // 查詢資料
            List<Baappbase> dataList = updateService.selectInheritorRsgisterBy(apno);
            if (dataList == null) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            querycase.setFormatApNo(queryForm.getApNo1() + "-" + queryForm.getApNo2() + "-" + queryForm.getApNo3() + "-" + queryForm.getApNo4());
            BeanUtility.copyProperties(querycase, dataList.get(0));
            queryForm.setBaappbaseId(querycase.getBaappbaseId());
            // 依照編號的開頭判別是哪種年金
            if ("L".equals(queryForm.getApNo1())) {
                querycase.setKind("老年年金");
            }
            else if ("K".equals(queryForm.getApNo1())) {
                querycase.setKind("失能年金");
            }
            else if ("S".equals(queryForm.getApNo1())) {
                querycase.setKind("遺屬年金");
            }

            CaseSessionHelper.setInheritorRegisterCase(querycase, request);
            log.debug("執行 更正作業 - 債務繼承人資料登錄作業 - 查詢頁面 InheritorRegisterAction.doAdd() 完成 ... ");

            return mapping.findForward(FORWARD_ADD);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("InheritorRegisterAction.doAdd() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_ADD_FAIL);
        }
    }

    /**
     * 更正作業 - 債務繼承人資料登錄作業 - 新增頁面 (bamo0d051a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(" 更正作業 - 債務繼承人資料登錄作業 - 新增頁面 InheritorRegisterAction.doInsert() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        InheritorRegisterForm queryForm = (InheritorRegisterForm) form;
        String forward = ConstantKey.FORWARD_SAVE_SUCCESS;
        try {
            // 新增繼承人資料
            InheritorRegisterCase Case = CaseSessionHelper.getInheritorRegisterCase(request);
            String benName = queryForm.getBenName();
            String benIdnNo = queryForm.getBenIdnNo();
            String benBrDate = "";
            if (StringUtils.isNotBlank(queryForm.getBenBrDate())) {
                benBrDate = DateUtility.changeDateType(queryForm.getBenBrDate());
            }
            // 先檢查欲新增的資料是否存在
            List<Baappbase> dataList = updateService.selectInheritorRsgisterForModify(Case.getApNo(), benName, benIdnNo, benBrDate);
            if (dataList.size() != 0) {
                saveMessages(session, DatabaseMessageHelper.getNoResultToExistMessage());
                forward = ConstantKey.FORWARD_SAVE_FAIL;
            }
            else {
                // 複製頁面資料到case中
                if (queryForm.getBenBrDate() != null && queryForm.getBenBrDate().length() == 7) {
                    queryForm.setBenBrDate(DateUtility.changeDateType(queryForm.getBenBrDate()));
                }
                BeanUtility.copyProperties(Case, queryForm);
                updateService.insertDataForInheritorregister(Case, userData);
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());

                // 清除SESSION中的資料
                CaseSessionHelper.removeInheritorRegisterCase(request);
                FormSessionHelper.removeInheritorRegisterForm(request);
                InheritorRegisterCase querycase = new InheritorRegisterCase();

                // 重新查詢資料
                List<Baappbase> resultList = updateService.selectInheritorRsgisterForModify(Case.getApNo(), null, null, null);
                if (resultList == null || resultList.isEmpty()) {
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
                }
                // 頁面LIST 繼承人出生日期 改成民國
                for (int i = 0; i < resultList.size(); i++) {
                    Baappbase obj = resultList.get(i);
                    obj.setBenBrDate(DateUtil.changeDateType((String) ObjectUtils.defaultIfNull(obj.getBenBrDate(), "")));
                }
                querycase.setDetailList(resultList);
                querycase.setFormatApNo(queryForm.getApNo1() + "-" + queryForm.getApNo2() + "-" + queryForm.getApNo3() + "-" + queryForm.getApNo4());
                BeanUtility.copyProperties(querycase, resultList.get(0));

                // 依照編號的開頭判別是哪種年金
                if ("L".equals(queryForm.getApNo1())) {
                    querycase.setKind("老年年金");
                }
                else if ("K".equals(queryForm.getApNo1())) {
                    querycase.setKind("失能年金");
                }
                else if ("S".equals(queryForm.getApNo1())) {
                    querycase.setKind("遺屬年金");
                }

                CaseSessionHelper.setInheritorRegisterCase(querycase, request);
                forward = ConstantKey.FORWARD_SAVE_SUCCESS;
            }
            log.debug("執行 更正作業 - 債務繼承人資料登錄作業 - 新增頁面 InheritorRegisterAction.doInsert() 完成 ... ");
            return mapping.findForward(forward);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("InheritorRegisterAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            forward = ConstantKey.FORWARD_SAVE_FAIL;
            return mapping.findForward(forward);
        }

    }

    /**
     * 更正作業 - 債務繼承人資料登錄作業 - 查詢頁面 (bamo0d050a.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(" 更正作業 - 債務繼承人資料登錄作業 - 查詢頁面 InheritorRegisterAction.doQueryList() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        InheritorRegisterForm queryForm = (InheritorRegisterForm) form;
        try {
            String apno = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            InheritorRegisterCase querycase = new InheritorRegisterCase();

            // 查詢資料
            List<Baappbase> dataList = updateService.selectInheritorRsgisterForModify(apno, null, null, null);
            if (dataList == null || dataList.isEmpty()) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            // 頁面LIST 繼承人出生日期 改成民國
            for (int i = 0; i < dataList.size(); i++) {
                Baappbase obj = dataList.get(i);
                obj.setBenBrDate(DateUtil.changeDateType((String) ObjectUtils.defaultIfNull(obj.getBenBrDate(), "")));
            }
            querycase.setDetailList(dataList);

            querycase.setFormatApNo(queryForm.getApNo1() + "-" + queryForm.getApNo2() + "-" + queryForm.getApNo3() + "-" + queryForm.getApNo4());
            BeanUtility.copyProperties(querycase, dataList.get(0));
            // 依照編號的開頭判別是哪種年金
            if ("L".equals(queryForm.getApNo1())) {
                querycase.setKind("老年年金");
            }
            else if ("K".equals(queryForm.getApNo1())) {
                querycase.setKind("失能年金");
            }
            else if ("S".equals(queryForm.getApNo1())) {
                querycase.setKind("遺屬年金");
            }

            CaseSessionHelper.setInheritorRegisterCase(querycase, request);
            log.debug("執行 更正作業 - 債務繼承人資料登錄作業 - 查詢頁面 InheritorRegisterAction.doQueryList() 完成 ... ");
            return mapping.findForward(FORWARD_QUERY_MODIFY);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("InheritorRegisterAction.doQueryList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 債務繼承人資料登錄作業 - 清單頁面 (bamo0d052c.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doModify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(" 更正作業 - 債務繼承人資料登錄作業 - 修改頁面 InheritorRegisterAction.doModify() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        InheritorRegisterForm queryForm = (InheritorRegisterForm) form;
        try {
            Baappbase obj = updateService.selectInheritorRsgisterDetailBy(queryForm.getBaappbaseId());
            InheritorRegisterForm modifyForm = new InheritorRegisterForm();
            BeanUtility.copyProperties(modifyForm, obj);
            InheritorRegisterCase querycase = CaseSessionHelper.getInheritorRegisterCase(request);
            BeanUtility.copyProperties(querycase, obj);
            modifyForm.setBenNationCodeOption(obj.getBenNationCode());
   
            // 出生日期 改成民國
            obj.setBenBrDate(DateUtil.changeDateType((String) ObjectUtils.defaultIfNull(obj.getBenBrDate(), "")));
        
            
            
            BeanUtility.copyPropertiesForUpdate(modifyForm, obj, ConstantKey.OLD_FIELD_PREFIX);
            // 將要修改的資料帶到下個頁面中
            FormSessionHelper.setInheritorRegisterForm(modifyForm, request);
            CaseSessionHelper.setInheritorRegisterCase(querycase, request);

            log.debug("執行 更正作業 - 債務繼承人資料登錄作業 - 修改頁面InheritorRegisterAction.doModify() 完成 ... ");
            return mapping.findForward(FORWARD_MODIFY);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("InheritorRegisterAction.doModify() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 債務繼承人資料登錄作業 - 修改頁面 (bamo0d053c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(" 更正作業 - 債務繼承人資料登錄作業 - 修改頁面 InheritorRegisterAction.doModify() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        InheritorRegisterForm queryForm = (InheritorRegisterForm) form;
        try {
            // 新增繼承人資料
            InheritorRegisterCase Case = CaseSessionHelper.getInheritorRegisterCase(request);
            // 複製頁面資料到case中
            if (queryForm.getBenBrDate() != null && queryForm.getBenBrDate().length() == 7) {
                queryForm.setBenBrDate(DateUtility.changeDateType(queryForm.getBenBrDate()));
            }
            BeanUtility.copyProperties(Case, queryForm);

            Case.setBaapplogList(LoggingHelper.getBaapplogList(queryForm, ConstantKey.OLD_FIELD_PREFIX, ConstantKey.BAAPPLOG_UPDATE_INHERITOR_FIELD_RESOURCE_PREFIX));
            updateService.updateDataForInheritorRegister(Case, userData);

            // 重新撈取清單資料
            // 查詢資料
            List<Baappbase> dataList = updateService.selectInheritorRsgisterForModify(Case.getApNo(), null, null, null);
            if (dataList == null) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            // 頁面LIST 繼承人出生日期 改成民國
            for (int i = 0; i < dataList.size(); i++) {
                Baappbase obj = dataList.get(i);
                obj.setBenBrDate(DateUtil.changeDateType((String) ObjectUtils.defaultIfNull(obj.getBenBrDate(), "")));
            }
            Case.setDetailList(dataList);
            saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            log.debug("執行 更正作業 - 債務繼承人資料登錄作業 - 修改頁面InheritorRegisterAction.doModify() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("InheritorRegisterAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 更正作業 - 債務繼承人資料登錄作業 - 修改頁面 (bamo0d053c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(" 更正作業 - 債務繼承人資料登錄作業 - 修改頁面 InheritorRegisterAction.doBackList() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        InheritorRegisterForm queryForm = (InheritorRegisterForm) form;
        try {
            InheritorRegisterCase Case = CaseSessionHelper.getInheritorRegisterCase(request);
            // 重新撈取清單資料
            // 查詢資料
            List<Baappbase> dataList = updateService.selectInheritorRsgisterForModify(Case.getApNo(), null, null, null);
            if (dataList == null) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            // 頁面LIST 繼承人出生日期 改成民國
            for (int i = 0; i < dataList.size(); i++) {
                Baappbase obj = dataList.get(i);
                obj.setBenBrDate(DateUtil.changeDateType((String) ObjectUtils.defaultIfNull(obj.getBenBrDate(), "")));
            }
            Case.setDetailList(dataList);

            FormSessionHelper.removeInheritorRegisterForm(request);
            log.debug("執行 更正作業 - 債務繼承人資料登錄作業 - 修改頁面InheritorRegisterAction.doBackList() 完成 ... ");
            return mapping.findForward(FORWARD_BACK_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("InheritorRegisterAction.doBackList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 債務繼承人資料登錄作業 - 修改頁面 (bamo0d052c.jsp,bamo0d051a.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(" 更正作業 - 債務繼承人資料登錄作業 - 修改頁面 InheritorRegisterAction.doBackList() 開始 ... ");
        HttpSession session = request.getSession();

        try {
            CaseSessionHelper.removeInheritorRegisterCase(request);
            FormSessionHelper.removeInheritorRegisterForm(request);
            log.debug("執行 更正作業 - 債務繼承人資料登錄作業 - 修改頁面InheritorRegisterAction.doBackList() 完成 ... ");
            return mapping.findForward(FORWARD_BACK);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("InheritorRegisterAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 更正作業 - 債務繼承人資料登錄作業 - 刪除債務繼承人資料 (bamo0d053c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(" 更正作業 - 債務繼承人資料登錄作業 - 刪除債務繼承人資料 InheritorRegisterAction.doDelete() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        InheritorRegisterForm queryForm = (InheritorRegisterForm) form;
        try {
            InheritorRegisterCase caseObj = CaseSessionHelper.getInheritorRegisterCase(request);
            updateService.deleteForInheritorRegister(caseObj.getApNo(), caseObj.getSeqNo(), caseObj.getBaappbaseId(), userData);

            // 重新撈取清單資料
            // 查詢資料
            List<Baappbase> dataList = updateService.selectInheritorRsgisterForModify(caseObj.getApNo(), null, null, null);
            if (dataList == null) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            // 頁面LIST 繼承人出生日期 改成民國
            for (int i = 0; i < dataList.size(); i++) {
                Baappbase obj = dataList.get(i);
                obj.setBenBrDate(DateUtil.changeDateType((String) ObjectUtils.defaultIfNull(obj.getBenBrDate(), "")));
            }
            caseObj.setDetailList(dataList);
            saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            log.debug("執行 更正作業 - 債務繼承人資料登錄作業 - 刪除債務繼承人資料 InheritorRegisterAction.doDelete() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_DELETE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("InheritorRegisterAction.doDelete() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getUpdateFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_DELETE_FAIL);
        }
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

}
