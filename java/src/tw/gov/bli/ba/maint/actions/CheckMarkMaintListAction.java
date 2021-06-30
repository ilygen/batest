package tw.gov.bli.ba.maint.actions;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Bapadchk;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;
import tw.gov.bli.ba.maint.cases.CheckMarkMaintCase;
import tw.gov.bli.ba.maint.forms.CheckMarkMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CheckMarkMaintListForm;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class CheckMarkMaintListAction extends DispatchAction {
    private static Log log = LogFactory.getLog(CheckMarkMaintListAction.class);

    private MaintService maintService;

    private static final String FORWARD_UPDATE_CHECK_MARK = "updateCheckMark"; // 編審註記維護修改作業頁面

    /**
     * 維護作業 - 編審註記維護作業 - 查詢頁面 (bapa0x012c.jsp) <br>
     * 按下「修改」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 編審註記維護作業 - 查詢頁面 CheckMarkMaintQueryAction.doUpdate() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CheckMarkMaintListForm queryForm = (CheckMarkMaintListForm) form;

        try {
            String rowNum = StringUtils.defaultString(request.getParameter("rowNum"));

            List<CheckMarkMaintCase> applyList = CaseSessionHelper.getCheckMarkMaintQueryCase(request);
            CheckMarkMaintCase caseData = applyList.get(Integer.parseInt(rowNum) - 1);

            // 取得編審資料
            Bapadchk detailData = maintService.getCheckMarkMaintForUpdateCaseBy(caseData.getChkTyp(), caseData.getChkObj(), caseData.getChkGroup(), caseData.getChkCode(), caseData.getValibDate());

            CheckMarkMaintDetailForm updateForm = new CheckMarkMaintDetailForm();
            BeanUtility.copyProperties(updateForm, detailData);

            // 變更日期為民國年
            if (StringUtils.defaultString(updateForm.getValibDate()).trim().length() == 8) {
                updateForm.setValibDate(DateUtility.changeDateType(updateForm.getValibDate().trim()));
            }
            if (StringUtils.defaultString(updateForm.getValieDate()).trim().length() == 8) {
                updateForm.setValieDate(DateUtility.changeDateType(updateForm.getValieDate().trim()));
            }

            FormSessionHelper.setCheckMarkMaintDetailForm(updateForm, request);

            return mapping.findForward(FORWARD_UPDATE_CHECK_MARK);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CheckMarkMaintListAction.doUpdate() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 維護作業 - 編審註記維護作業 (bapa0x012c.jsp) <br>
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
