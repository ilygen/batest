package tw.gov.bli.ba.maint.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.domain.Bapadcslvl;
import tw.gov.bli.ba.maint.forms.DecisionLevelMaintQueryForm;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class DecisionLevelMaintQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DecisionLevelMaintQueryAction.class);

    private static final String FORWARD_INSERT_DECISION_LEVEL = "insertDecisionLevel"; // 決行層級維護新增作業頁面
    private static final String FORWARD_MODIFY_DECISION_LEVEL_LIST = "modifyDecisionLevelList"; // 決行層級維護修改作業頁面

    private MaintService maintService;
    private SelectOptionService selectOptionService;

    /**
     * 維護作業 - 決行層級維護作業 - 查詢頁面 (bapa0x030f.jsp) <br>
     * 按下「新增」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doInsert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 決行層級維護作業 - 新增頁面 DecisionLevelMaintAction.doInsert() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DecisionLevelMaintQueryForm queryForm = (DecisionLevelMaintQueryForm) form;

        try {
            // 清除Session資料
            FormSessionHelper.removeDecisionLevelMaintQueryForm(request);
            FormSessionHelper.removeDecisionLevelMaintDetailForm(request);

            log.debug("執行 維護作業 - 決行層級維護作業 - 新增頁面  DecisionLevelMaintQueryAction.doInsert() 完成 ... ");

            return mapping.findForward(FORWARD_INSERT_DECISION_LEVEL);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DecisionLevelMaintQueryAction.doInsert() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 決行層級維護作業 - 查詢頁面 (bapa0x030f.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doModify(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 決行層級維護作業 - 修改頁面 DecisionLevelMaintAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DecisionLevelMaintQueryForm queryForm = (DecisionLevelMaintQueryForm) form;

        try {
            // 取得修改資料清單
            List<Bapadcslvl> applyList = maintService.getDecisionLevelMaintQueryCaseBy(queryForm.getPayCode(), null, null);

            CaseSessionHelper.setDecisionLevelMaintQueryCase(applyList, request);
            FormSessionHelper.setDecisionLevelMaintQueryForm(queryForm, request);

            log.debug("執行 維護作業 - 決行層級維護作業 - 修改頁面  DecisionLevelMaintQueryAction.doModify() 完成 ... ");

            return mapping.findForward(FORWARD_MODIFY_DECISION_LEVEL_LIST);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DecisionLevelMaintQueryAction.doModify() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
