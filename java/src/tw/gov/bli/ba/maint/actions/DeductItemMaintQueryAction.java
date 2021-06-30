package tw.gov.bli.ba.maint.actions;

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
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.SystemMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.maint.cases.DeductItemMaintCase;
import tw.gov.bli.ba.maint.forms.DeductItemMaintDetailForm;
import tw.gov.bli.ba.maint.forms.DeductItemMaintQueryForm;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 扣減參數維護作業 - 查詢頁面 (bapa0x050f.jsp)
 * 
 * @author Ocean Cheng
 */
public class DeductItemMaintQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DeductItemMaintQueryAction.class);

    private MaintService maintService;

    /**
     * 維護作業 - 扣減參數維護作業 - 查詢頁面 (bapa0x050f.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 扣減參數維護作業 - 查詢頁面 PreviewConditionMaintQueryAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DeductItemMaintQueryForm queryForm = (DeductItemMaintQueryForm) form;

        try {
            // 取得 扣減參數維護作業 Case
            List<DeductItemMaintCase> list = maintService.getDeductItemMaintCaseBy(queryForm.getPayKind());
            DeductItemMaintDetailForm detailForm = new DeductItemMaintDetailForm();
            detailForm.setPayCode(queryForm.getPayKind());
            detailForm.setData(list);
            // 將 Detail Form 加入 Session 中
            FormSessionHelper.setDeductItemMaintDetailForm(detailForm, request);

            log.debug("執行 維護作業 - 扣減參數維護作業 - 查詢頁面 DeductItemMaintQueryAction.doQuery() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("DeductItemMaintQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }
}
