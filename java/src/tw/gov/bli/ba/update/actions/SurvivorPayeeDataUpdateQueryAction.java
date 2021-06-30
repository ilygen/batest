package tw.gov.bli.ba.update.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;
import tw.gov.bli.ba.update.forms.SurvivorPayeeDataUpdateQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.ExceptionUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 更正作業 - 遺屬年金受款人資料更正 - 查詢功能
 * @author Azuritul
 *
 */
public class SurvivorPayeeDataUpdateQueryAction extends SurvivorPayeeDataUpdateAction{

    private static Log log = LogFactory.getLog(SurvivorPayeeDataUpdateQueryAction.class);
    private static final String FORWARD_MODIFY_SURVIVOR_PAYEE_DATA_LIST = "modifySurvivorPayeeDataList";

    private static final String LOG_INFO_DOQUERY_START = "執行 遺屬年金受款人資料更正 - 查詢頁面 SurvivorPayeeDataUpdateQueryAction.doQuery() 開始 ... ";
    private static final String LOG_INFO_DOQUERY_ERROR = "執行 遺屬年金受款人資料更正 - 查詢頁面 SurvivorPayeeDataUpdateQueryAction.doQuery() 發生錯誤:";

    /**
     * 更正作業 - 遺屬年金受款人資料更正 - 查詢頁面 (bamo0d280c.jsp)
     * 按下確認後執行的method
     * @param mapping  ActionMapping
     * @param form     ActionForm
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug(LOG_INFO_DOQUERY_START);
        SurvivorPayeeDataUpdateQueryForm queryForm = (SurvivorPayeeDataUpdateQueryForm) form;
        cleanDataInSession(request);
        try {
            List<SurvivorPayeeDataUpdateCase> survivorEvtPayeeList = refetchSurvivorPayeeDataUpdateCaseList(queryForm.getApNo(), null);
            List<SurvivorPayeeDataUpdateCase> survivorPayeeList = refetchSurvivorPayeeDataUpdateCaseList(queryForm.getApNo(), "0000");
            if (survivorEvtPayeeList.size() <= 0) {
                saveMessages(request.getSession(), DatabaseMessageHelper.getNoResultForApNoMessage());
                return mapping.findForward(ConstantKey.FORWARD_BACK);
            }
            FormSessionHelper.setSurvivorPayeeDataUpdateQueryForm(queryForm, request);
            CaseSessionHelper.setSurvivorPayeeDataUpdateQueryCase(survivorPayeeList, request);
            CaseSessionHelper.setSurvivorPayeeEvtDataUpdateQueryCase(survivorEvtPayeeList, request);
            return mapping.findForward(FORWARD_MODIFY_SURVIVOR_PAYEE_DATA_LIST);
        }
        catch (Exception e) {
            log.error(LOG_INFO_DOQUERY_ERROR + ExceptionUtility.getStackTrace(e));
            saveMessages(request.getSession(), DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
}
