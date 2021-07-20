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
import tw.gov.bli.ba.maint.forms.CheckMarkMaintQueryForm;
import tw.gov.bli.ba.maint.forms.CheckMarkMaintDetailForm;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.cases.CheckMarkMaintCase;
import tw.gov.bli.ba.domain.Bapadchk;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 編審註記維護作業 - 查詢頁面 (bapa0x010f.jsp)
 * 
 * @author Swim
 */
public class CheckMarkMaintQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(CheckMarkMaintQueryAction.class);

    private static final String FORWARD_INSERT_CHECK_MARK = "insertCheckMark"; // 編審註記維護新增作業頁面
    private static final String FORWARD_MODIFY_CHECK_MARK_LIST = "modifyCheckMarkList"; // 編審註記維護修改作業頁面

    private MaintService maintService;
    private SelectOptionService selectOptionService;

    /**
     * 維護作業 - 編審註記維護作業 - 查詢頁面 (bapa0x010f.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 編審註記維護作業 - 查詢頁面 CheckMarkMaintQueryAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CheckMarkMaintQueryForm queryForm = (CheckMarkMaintQueryForm) form;

        try {
            FormSessionHelper.removeCheckMarkMaintQueryForm(request);
            FormSessionHelper.removeCheckMarkMaintDetailForm(request);
            return mapping.findForward(FORWARD_INSERT_CHECK_MARK);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CheckMarkMaintQueryAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 編審註記維護作業 - 查詢頁面 (bapa0x010f.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 編審註記維護作業 - 查詢頁面 CheckMarkMaintQueryAction.doQuery() 開始 ... ");

        String chkGroup = null;
        String chkObj = null;

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CheckMarkMaintQueryForm queryForm = (CheckMarkMaintQueryForm) form;

        try {
            List<CheckMarkMaintCase> applyList = maintService.getCheckMarkMaintQueryCaseBy(queryForm.getChkObj(), queryForm.getChkGroup(), queryForm.getChkCode());

            // 類別
            if (!queryForm.getChkGroup().equals(""))
                chkGroup = selectOptionService.getParamName(ConstantKey.BAPARAM_PARAMGROUP_CHKGROUP, queryForm.getChkGroup());
            else
                chkGroup = "全部";
            // 給付種類
            if (!queryForm.getChkObj().equals(""))
                chkObj = selectOptionService.getParamName(ConstantKey.BAPARAM_PARAMGROUP_PAYCODE, queryForm.getChkObj());

            CaseSessionHelper.setCheckMarkMaintQueryCase(applyList, request);
            FormSessionHelper.setCheckMarkMaintQueryForm(queryForm, chkGroup, chkObj, request);

            return mapping.findForward(FORWARD_MODIFY_CHECK_MARK_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CheckMarkMaintQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
}
