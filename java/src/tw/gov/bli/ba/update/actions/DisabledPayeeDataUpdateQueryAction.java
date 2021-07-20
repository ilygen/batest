package tw.gov.bli.ba.update.actions;

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
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.update.forms.DisabledPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 更正作業 - 失能年金受款人資料更正 - 查詢頁面 (bamo0d180c.jsp)
 * 按下確認後執行的Action
 * @author Azuritul
 *
 */
public class DisabledPayeeDataUpdateQueryAction extends DisabledPayeeDataUpdateAction{

    private static Log log = LogFactory.getLog(DisabledPayeeDataUpdateQueryAction.class);
    // 更正作業 - 受款人資料更正 - 受款人資料清單頁面
    private static final String FORWARD_MODIFY_DISABLED_PAYEE_DATA_LIST = "modifyDisabledPayeeDataList";

    private static final String LOG_INFO_DOQUERY_START = "執行 失能年金受款人資料更正 - 查詢頁面 DisabledPayeeDataUpdateQueryAction.doQuery() 開始 ... ";
    private static final String LOG_INFO_DOQUERY_ERROR = "執行 失能年金受款人資料更正 - 查詢頁面 DisabledPayeeDataUpdateQueryAction.doQuery() 發生錯誤:";

    /**
     * 更正作業 - 失能年金受款人資料更正 - 查詢頁面 (bamo0d180c.jsp)
     * 按下確認後執行的method
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOQUERY_START);

        DisabledPayeeDataUpdateQueryForm queryForm = (DisabledPayeeDataUpdateQueryForm) form;
        cleanDataInSession(request);

        try {
            List<BaappbaseDataUpdateCase> applyList = fetchDisabledPayeeDataUpdateCaseList(queryForm.getApNo());
            if (applyList.isEmpty()) {
                saveMessages(request.getSession(), DatabaseMessageHelper.getNoResultForApNoMessage());
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }

            FormSessionHelper.setDisabledPayeeDataUpdateQueryForm(queryForm, request);
            CaseSessionHelper.setDisabledPayeeDataUpdateQueryCase(applyList, request);

            return mapping.findForward(FORWARD_MODIFY_DISABLED_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOQUERY_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

}
