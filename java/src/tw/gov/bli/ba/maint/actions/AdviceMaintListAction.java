package tw.gov.bli.ba.maint.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.maint.cases.AdviceMaintCase;
import tw.gov.bli.ba.domain.Banotify;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import tw.gov.bli.ba.maint.forms.AdviceMaintDetailForm;
import tw.gov.bli.ba.maint.forms.AdviceMaintListForm;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class AdviceMaintListAction extends DispatchAction {
    private static Log log = LogFactory.getLog(AdviceMaintListAction.class);

    private MaintService maintService;

    private static final String FORWARD_UPDATE_ADVICE = "updateAdvice"; // 核定通知書維護作業修改作業頁面
    private static final String FORWARD_QUERY_ADVICE = "queryAdvice"; // 核定通知書維護作業查詢作業頁面

    /**
     * 維護作業 - 核定通知書維護作業 - 查詢頁面 (bapa0x022c.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 查詢頁面 AdviceMaintListAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintListForm queryForm = (AdviceMaintListForm) form;

        try {
            String payCode = StringUtils.defaultString(request.getParameter("payCode"));
            String authTyp = StringUtils.defaultString(request.getParameter("authTyp"));

            // 主旨
            List<AdviceMaintCase> purposeList = maintService.getAdviceMaintForUpdateCaseBy(payCode, authTyp, "0");
            // 說明
            List<AdviceMaintCase> explainList = maintService.getAdviceMaintForUpdateCaseBy(payCode, authTyp, "1");

            // 主旨
            AdviceMaintCase adviceDataPurpose = (AdviceMaintCase) purposeList.get(0);
            // 說明
            // AdviceMaintCase adviceDataExplain = (AdviceMaintCase) detailData.get(1);

            // adviceDataPurpose.setDataContExplain(adviceDataExplain.getDataContExplain());

            AdviceMaintDetailForm updateForm = new AdviceMaintDetailForm();
            BeanUtility.copyProperties(updateForm, adviceDataPurpose);

            FormSessionHelper.setAdviceMaintDetailForm(updateForm, request);
            CaseSessionHelper.setAdviceMaintDataContCase(explainList, request);

            return mapping.findForward(FORWARD_UPDATE_ADVICE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintListAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
     * 維護作業 - 核定通知書維護作業 - 查詢頁面 (bapa0x022c.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQueryDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 核定通知書維護作業 - 查詢頁面 AdviceMaintListAction.doQueryDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        AdviceMaintListForm queryForm = (AdviceMaintListForm) form;

        try {
            String payCode = StringUtils.defaultString(request.getParameter("payCode"));
            String authTyp = StringUtils.defaultString(request.getParameter("authTyp"));

            // 主旨
            List<AdviceMaintCase> purposeList = maintService.getAdviceMaintForUpdateCaseBy(payCode, authTyp, "0");
            // 說明
            List<AdviceMaintCase> explainList = maintService.getAdviceMaintForUpdateCaseBy(payCode, authTyp, "1");

            // 主旨
            AdviceMaintCase adviceDataPurpose = (AdviceMaintCase) purposeList.get(0);
            // 說明
            // AdviceMaintCase adviceDataExplain = (AdviceMaintCase) detailData.get(1);

            // adviceDataPurpose.setDataContExplain(adviceDataExplain.getDataContExplain());

            AdviceMaintDetailForm updateForm = new AdviceMaintDetailForm();
            BeanUtility.copyProperties(updateForm, adviceDataPurpose);

            FormSessionHelper.setAdviceMaintDetailForm(updateForm, request);
            CaseSessionHelper.setAdviceMaintDataContCase(explainList, request);

            return mapping.findForward(FORWARD_QUERY_ADVICE);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("AdviceMaintListAction.doQueryDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 核定通知書維護作業 (bapa0x022c.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }

}
