package tw.gov.bli.ba.maint.actions;

import java.util.ArrayList;
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
import tw.gov.bli.ba.maint.forms.AdviceMaintQueryForm;
import tw.gov.bli.ba.maint.forms.AdviceMaintDetailForm;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.cases.AdviceMaintCase;
import tw.gov.bli.ba.domain.Banotify;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 核定通知書維護作業 - 查詢頁面 (bapa0x020f.jsp)
 * 
 * @author Swim
 */
public class AdviceMaintQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(AdviceMaintQueryAction.class);

    private static final String FORWARD_INSERT_ADVICE = "insertAdvice"; // 核定通知書維護新增作業頁面
    private static final String FORWARD_MODIFY_ADVICE_LIST = "modifyAdviceList"; // 核定通知書維護修改作業頁面
    private static final String FORWARD_QUERY_ADVICE_LIST = "queryAdviceList"; // 核定通知書維護修改作業頁面

    private MaintService maintService;
    private SelectOptionService selectOptionService;

    /**
     * 維護作業 - 核定通知書維護作業 - 查詢頁面 (bapa0x020f.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 查詢頁面 AdviceMaintQueryAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintQueryForm queryForm = (AdviceMaintQueryForm) form;

        try {
            // 新增眷屬資料
            ArrayList<AdviceMaintCase> banotifyList = new ArrayList<AdviceMaintCase>();

            // 存放眷屬資料於session
            CaseSessionHelper.setAdviceMaintDataContCase(banotifyList, request);

            FormSessionHelper.removeAdviceMaintQueryForm(request);
            FormSessionHelper.removeAdviceMaintDetailForm(request);
            return mapping.findForward(FORWARD_INSERT_ADVICE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintQueryAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 核定通知書維護作業 - 查詢頁面 (bapa0x020f.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 查詢頁面 AdviceMaintQueryAction.doQuery() 開始 ... ");

        String payCode = null;

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintQueryForm queryForm = (AdviceMaintQueryForm) form;

        try {
            List<AdviceMaintCase> applyList = maintService.getAdviceMaintQueryCaseBy(queryForm.getPayCode(), queryForm.getAuthTyp());

            // 給付別
            if (!queryForm.getPayCode().equals("")) {
                payCode = selectOptionService.getParamName(ConstantKey.BAPARAM_PARAMGROUP_PAYCODE, queryForm.getPayCode());
            }

            CaseSessionHelper.setAdviceMaintQueryCase(applyList, request);
            FormSessionHelper.setAdviceMaintQueryForm(queryForm, payCode, request);

            return mapping.findForward(FORWARD_MODIFY_ADVICE_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 維護作業 - 核定通知書維護作業 - 查詢頁面 (bapa0x020f.jsp) <br>
     * 按下「查詢」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 查詢頁面 AdviceMaintQueryAction.doQueryList() 開始 ... ");

        String payCode = null;

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintQueryForm queryForm = (AdviceMaintQueryForm) form;

        try {
            List<AdviceMaintCase> applyList = maintService.getAdviceMaintQueryCaseBy(queryForm.getPayCode(), queryForm.getAuthTyp());

            // 給付別
            if (!queryForm.getPayCode().equals("")) {
                payCode = selectOptionService.getParamName(ConstantKey.BAPARAM_PARAMGROUP_PAYCODE, queryForm.getPayCode());
            }

            CaseSessionHelper.setAdviceMaintQueryCase(applyList, request);
            FormSessionHelper.setAdviceMaintQueryForm(queryForm, payCode, request);

            return mapping.findForward(FORWARD_QUERY_ADVICE_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintQueryAction.doQueryList() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
