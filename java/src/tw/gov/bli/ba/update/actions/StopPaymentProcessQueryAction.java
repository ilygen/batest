package tw.gov.bli.ba.update.actions;

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
import tw.gov.bli.ba.services.UpdateService;
import tw.gov.bli.ba.update.cases.StopPaymentProcessCase;
import tw.gov.bli.ba.update.forms.StopPaymentProcessDetailForm;
import tw.gov.bli.ba.update.forms.StopPaymentProcessQueryForm;
import tw.gov.bli.ba.update.helper.CaseSessionHelper;
import tw.gov.bli.ba.update.helper.FormSessionHelper;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.DateUtil;

/**
 * 更正作業 - 止付處理 (BAMO0D040N)
 * 
 * @author swim
 */
public class StopPaymentProcessQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(StopPaymentProcessQueryAction.class);

    private UpdateService updateService;

    // 更正作業 - 止付處理 - 止付處理明細頁面
    private static final String FORWARD_STOP_PAYMENT_PROCESS_DETAIL = "stopPaymentProcessDetail";

    /**
     * 更正作業 - 止付處理 - 查詢頁面 (bamo0d040n.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 更正作業 - 止付處理 - 查詢頁面 StopPaymentProcessQueryAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        StopPaymentProcessQueryForm queryForm = (StopPaymentProcessQueryForm) form;
        
        // 清除Session資料
        CaseSessionHelper.removeStopPaymentProcessQueryCase(request);
        FormSessionHelper.removeStopPaymentProcessQueryForm(request);
        FormSessionHelper.removeStopPaymentProcessDetailForm(request);

        try {
            // 取得查詢資料
            StopPaymentProcessCase caseData = updateService.getStopPaymentProcessCaseBy(queryForm.getApNo1() + queryForm.getApNo2() + queryForm.getApNo3() + queryForm.getApNo4(), DateUtil.changeChineseYearMonthType(queryForm.getIssuYm()));

            // 檢查是否有資料 無資料則回查詢頁面
            if (caseData == null) {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            
            
            StopPaymentProcessDetailForm updateForm = new StopPaymentProcessDetailForm();
            BeanUtility.copyProperties(updateForm, caseData);

            CaseSessionHelper.setStopPaymentProcessQueryCase(caseData, request);
            FormSessionHelper.setStopPaymentProcessQueryForm(queryForm, request);
            FormSessionHelper.setStopPaymentProcessDetailForm(updateForm, request);

            return mapping.findForward(FORWARD_STOP_PAYMENT_PROCESS_DETAIL);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("StopPaymentProcessQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }
}
