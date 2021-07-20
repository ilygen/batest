package tw.gov.bli.ba.query.actions;

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
import tw.gov.bli.ba.query.cases.UpdateLogQueryCase;
import tw.gov.bli.ba.query.forms.UpdateLogQueryForm;
import tw.gov.bli.ba.query.helper.CaseSessionHelper;
import tw.gov.bli.ba.services.QueryService;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 查詢作業 - 更正日誌查詢 - 查詢頁面 (baiq0d030q.jsp)
 * 
 * @author Goston
 */
public class UpdateLogQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(UpdateLogQueryAction.class);

    private QueryService queryService;

    /**
     * 查詢作業 - 更正日誌查詢 - 查詢頁面 (baiq0d030q.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 更正日誌查詢 - 查詢頁面 UpdateLogQueryAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        UpdateLogQueryForm queryForm = (UpdateLogQueryForm) form;

        try {
            // 將先前的 Case 先清除
            CaseSessionHelper.removeUpdateLogQueryCase(request);

            String apNo = StringUtils.upperCase(StringUtils.trimToEmpty(queryForm.getApNo1())) + StringUtils.trimToEmpty(queryForm.getApNo2()) + StringUtils.trimToEmpty(queryForm.getApNo3()) + StringUtils.trimToEmpty(queryForm.getApNo4());

            // 受理編號長度為 1 則代表其值為畫面中自動代入之 L K S, 故不以其為條件進行查詢
            if (StringUtils.length(apNo) == 1) {
                apNo = null;
            }
            else if (StringUtils.length(apNo) > 1 && StringUtils.length(apNo) != ConstantKey.APNO_LENGTH) { // 受理編號長度不滿 12 碼則不做查詢
                // 設定 無查詢資料 訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }

            // 取得 更正日誌 資料
            UpdateLogQueryCase caseData = queryService.getUpdateLogQueryCaseBy(queryForm.getPayCode(), queryForm.getUpdateDateBegin(), queryForm.getUpdateDateEnd(), apNo, queryForm.getBenIdnNo(), queryForm.getUpdUser());

            // 塞到 Session 中
            CaseSessionHelper.setUpdateLogQueryCase(caseData, request);

            log.debug("執行 查詢作業 - 更正日誌查詢 - 查詢頁面 UpdateLogQueryAction.doQuery() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("UpdateLogQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

}
