package tw.gov.bli.ba.query.actions;

import java.math.BigDecimal;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.SystemMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.query.helper.CaseSessionHelper;
import tw.gov.bli.ba.query.helper.FormSessionHelper;
import tw.gov.bli.ba.query.cases.ExecutiveSupportQueryCase;
import tw.gov.bli.ba.query.forms.ExecutiveSupportQueryForm;
import tw.gov.bli.ba.services.QueryService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.ba.util.DateUtility;

/**
 * 查詢作業 - 行政支援查詢 - 查詢頁面 (baiq0d050q.jsp)
 * 
 * @author jerry
 */
public class ExecutiveSupportQueryAction extends DispatchAction {
    private static Log log = LogFactory.getLog(ExecutiveSupportQueryAction.class);
    private static final String FORWARD_QUERY_LIST = "queryList"; // 行政支援查詢結果頁面
    private QueryService queryService;

    /**
     * 查詢作業 - 行政支援查詢 - 查詢頁面 (baiq0d050q.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 行政支援查詢 - 查詢頁面 ExecutiveSupportQueryAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();

        ExecutiveSupportQueryForm queryForm = (ExecutiveSupportQueryForm) form;

        try {
            String qryFromWhere = queryForm.getQryFromWhere();
            String apNo = "";
            if (("BAIQ0D050Q").equals(qryFromWhere)) {
                apNo = queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4();
            }
            else if (("BAIQ0D012Q").equals(qryFromWhere) || ("BAIQ0D062Q").equals(qryFromWhere) || ("BAIQ0D112Q").equals(qryFromWhere) || ("BAIQ0D162Q").equals(qryFromWhere) || ("BAIQ0D212Q").equals(qryFromWhere)
                            || ("BAIQ0D262Q").equals(qryFromWhere)) {// 從給付查詢連過來
                apNo = queryForm.getApNo();
            }

            String issuYm = DateUtility.changeChineseYearMonthType(queryForm.getIssuYm());

            ExecutiveSupportQueryCase caseData = queryService.getExecutiveSupportQueryBy(apNo);
            // 檢查是否有符合條件之資料
            if (caseData == null) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }

            ExecutiveSupportQueryForm condFrom = new ExecutiveSupportQueryForm();
            BeanUtility.copyProperties(condFrom, queryForm);
            CaseSessionHelper.setExecutiveSupportQueryCase(caseData, request);
            CaseSessionHelper.setExecutiveSupportQueryCaseList(queryService.getExecutiveSupportQueryListBy(apNo, queryForm.getIdNo(), issuYm), request);
            FormSessionHelper.setExecutiveSupportQueryCondForm(condFrom, request);
            log.debug("執行 查詢作業 - 行政支援查詢 - 查詢頁面 ExecutiveSupportQueryAction.doQuery() 完成 ... ");
            return mapping.findForward(FORWARD_QUERY_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

}
