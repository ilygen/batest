package tw.gov.bli.ba.query.actions;

/**
 * 勞工保險年金給付系統 - 編審註記查詢
 * 查詢條件輸入頁面
 * BAIQ0D040Q.jsp
 */
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
import org.apache.struts.actions.DispatchAction;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bapadchk;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.query.forms.CheckMarkLevelQueryForm;
import tw.gov.bli.ba.query.cases.CheckMarkLevelQueryCase;
import tw.gov.bli.ba.services.QueryService;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.ba.query.helper.CaseSessionHelper;
import tw.gov.bli.ba.query.helper.FormSessionHelper;

public class CheckMarkLevelQueryAction extends DispatchAction {
    private static Log log = LogFactory.getLog(CheckMarkLevelQueryAction.class);

    private QueryService queryService;

    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 勞工保險年金給付系統 - 編審註記查詢 - 查詢條件輸入頁面 查詢開始 CheckMarkLevelQueryAction.doQuery() ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CheckMarkLevelQueryForm queryForm = (CheckMarkLevelQueryForm) form;

        try {
            // 移除所有存在 Session 中與查詢相關的 List 資料
            CaseSessionHelper.removeCheckMarkLevelQueryCase(request);

            List<CheckMarkLevelQueryCase> applyList = queryService.getCheckMarkLevelQueryCaseBy(queryForm.getChkObj(), queryForm.getChkGroup(), queryForm.getChkCode());

            CaseSessionHelper.setCheckMarkLevelQueryCase(applyList, request);
            FormSessionHelper.setCheckMarkLevelQueryForm(queryForm, request);

            log.debug("執行 勞工保險年金給付系統 - 編審註記查詢 - 查詢條件輸入頁面 查詢完成 CheckMarkLevelQueryAction.doQuery() ... ");

            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
        }
        catch (Exception e) {
            log.error("CheckMarkLevelQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(request, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

}
