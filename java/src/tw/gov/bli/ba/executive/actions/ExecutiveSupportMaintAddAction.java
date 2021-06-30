package tw.gov.bli.ba.executive.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportMaintCase;
import tw.gov.bli.ba.executive.forms.ExecutiveSupportMaintAddForm;
import tw.gov.bli.ba.executive.helper.CaseSessionHelper;
import tw.gov.bli.ba.executive.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.ExecutiveService;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class ExecutiveSupportMaintAddAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ExecutiveSupportMaintAddAction.class);

    private ExecutiveService executiveService;

    /**
     * 行政支援作業 - 行政支援註記維護 - 新增頁面 (basu0d011a.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援註記維護 - 新增頁面 ExecutiveSupportMaintAddAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        ExecutiveSupportMaintAddForm detailForm = (ExecutiveSupportMaintAddForm) form;

        try {
            ExecutiveSupportMaintCase caseData = CaseSessionHelper.getExecutiveSupportMaintCase(request);
            
            Maadmrec maadmrec = new Maadmrec();
            BeanUtility.copyProperties(maadmrec, detailForm);
            maadmrec.setSlrpNo(detailForm.getSlrpNo1()+detailForm.getSlrpNo2()+detailForm.getSlrpNo3()+detailForm.getSlrpNo4()+detailForm.getSlrpNo5());
            maadmrec.setSlrelate(detailForm.getSlrelate1()+detailForm.getSlrelate2()+detailForm.getSlrelate3()+detailForm.getSlrelate4()+detailForm.getSlrelate5());
            maadmrec.setCloseNo(detailForm.getCloseNo1()+detailForm.getCloseNo2()+detailForm.getCloseNo3()+detailForm.getCloseNo4()+detailForm.getCloseNo5());
            
            // 新增資料
            executiveService.insertMaadmrec(maadmrec, caseData, userData, detailForm.getPayMk());
            // 設定存檔成功訊息
            saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            // 清除Session資料
            CaseSessionHelper.removeExecutiveSupportMaintCase(request);
            FormSessionHelper.removeExecutiveSupportMaintForm(request);
            FormSessionHelper.removeExecutiveSupportMaintAddForm(request);
            
            log.debug("執行 行政支援作業 - 行政支援註記維護 - 新增頁面 ExecutiveSupportMaintAddAction.doSave() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);

        }
        catch (Exception e) {
            // 清除Session資料
            CaseSessionHelper.removeExecutiveSupportMaintCase(request);
            FormSessionHelper.removeExecutiveSupportMaintForm(request);
            FormSessionHelper.removeExecutiveSupportMaintAddForm(request);
            // 設定存檔失敗訊息
            log.error("ExecutiveSupportMaintAddAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 行政支援作業 - 行政支援註記維護 - 新增頁面 (basu0d011a.jsp) <br>
     * 按下「返回」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 行政支援作業 - 行政支援註記維護 - 新增頁面 ExecutiveSupportMaintAddAction.doBack() 開始 ... ");

        HttpSession session = request.getSession();

        try {
            // 清除Session資料
            CaseSessionHelper.removeExecutiveSupportMaintCase(request);
            FormSessionHelper.removeExecutiveSupportMaintForm(request);
            FormSessionHelper.removeExecutiveSupportMaintAddForm(request);
            
            log.debug("執行 行政支援作業 - 行政支援註記維護 - 新增頁面 ExecutiveSupportMaintAddAction.doBack() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_BACK);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecutiveSupportMaintAddAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setExecutiveService(ExecutiveService executiveService) {
        this.executiveService = executiveService;
    }
}
