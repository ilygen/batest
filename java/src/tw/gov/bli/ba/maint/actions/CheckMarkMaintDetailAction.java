package tw.gov.bli.ba.maint.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.SystemMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.domain.Bapadchk;
import tw.gov.bli.ba.maint.cases.AdviceMaintCase;
import tw.gov.bli.ba.maint.cases.CheckMarkMaintCase;
import tw.gov.bli.ba.maint.forms.CheckMarkMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CheckMarkMaintQueryForm;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 編審註記維護作業 - 查詢頁面 (bapa0x011a.jsp)
 * 
 * @author Swim
 */
public class CheckMarkMaintDetailAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(CheckMarkMaintDetailAction.class);

    private MaintService maintService;

    /**
     * 維護作業 - 編審註記維護作業 - 查詢頁面 (bapa0x011a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 編審註記維護作業 - 新增頁面 CheckMarkMaintDetailAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CheckMarkMaintDetailForm detailForm = (CheckMarkMaintDetailForm) form;

        try {
            // 檢查是否有重複資料
            List<CheckMarkMaintCase> checkList = maintService.getCheckMarkMaintQueryCaseBy(detailForm.getChkObj(), detailForm.getChkGroup(), detailForm.getChkCode());

            if (checkList.size() > 0) {
                // 設定該筆資料已存在訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultToExistMessage());

                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }

            if (detailForm != null) {
                Bapadchk detailData = new Bapadchk();
                BeanUtility.copyProperties(detailData, detailForm);

                // 變更日期為西元年
                if (StringUtils.defaultString(detailForm.getValibDate()).trim().length() == 7) {
                    detailData.setValibDate(DateUtility.changeDateType(detailForm.getValibDate().trim()));
                }
                if (StringUtils.defaultString(detailForm.getValieDate()).trim().length() == 7) {
                    detailData.setValieDate(DateUtility.changeDateType(detailForm.getValieDate().trim()));
                }

                // 存檔
                maintService.saveCheckMarkMaintData(detailData, userData);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 維護作業 - 編審註記維護作業 - 新增頁面  CheckMarkMaintDetailAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CheckMarkMaintDetailAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 編審註記維護作業 - 修改頁面 (bapa0x013c.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 編審註記維護作業 - 修改頁面 CheckMarkMaintDetailAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CheckMarkMaintDetailForm detailForm = (CheckMarkMaintDetailForm) form;

        try {
            if (detailForm != null) {
                Bapadchk detailData = new Bapadchk();
                BeanUtility.copyProperties(detailData, detailForm);

                // 變更日期為西元年
                if (StringUtils.defaultString(detailForm.getValibDate()).trim().length() == 7) {
                    detailData.setValibDate(DateUtility.changeDateType(detailForm.getValibDate().trim()));
                }

                if (StringUtils.defaultString(detailForm.getValieDate()).trim().length() == 7) {
                    detailData.setValieDate(DateUtility.changeDateType(detailForm.getValieDate().trim()));
                }

                // 存檔
                maintService.updateCheckMarkMaintData(detailData, userData);

                // 更新清單資料
                CheckMarkMaintQueryForm queryForm = (CheckMarkMaintQueryForm) FormSessionHelper.getCheckMarkMaintQueryForm(request);
                List<CheckMarkMaintCase> applyList = maintService.getCheckMarkMaintQueryCaseBy(queryForm.getChkObj(), queryForm.getChkGroup(), queryForm.getChkCode());
                CaseSessionHelper.setCheckMarkMaintQueryCase(applyList, request);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 維護作業 - 編審註記維護作業 - 修改頁面  CheckMarkMaintDetailAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CheckMarkMaintDetailAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }
}
