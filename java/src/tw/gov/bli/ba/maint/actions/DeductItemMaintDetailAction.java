package tw.gov.bli.ba.maint.actions;

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
import tw.gov.bli.ba.maint.cases.DeductItemMaintCase;
import tw.gov.bli.ba.maint.forms.DeductItemMaintDetailForm;
import tw.gov.bli.ba.maint.forms.PreviewConditionMaintDetailForOldAgeAnnuityForm;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 扣減參數維護作業 - (bapa0x051a.jsp)
 * 
 * @author Goston
 */
public class DeductItemMaintDetailAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(DeductItemMaintDetailAction.class);

    private MaintService maintService;

    /**
     * 維護作業 - 扣減參數維護作業 (bapa0x050f.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 扣減參數維護作業 - 老年年金維護作業 DeductItemMaintDetailAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        DeductItemMaintDetailForm detailForm = (DeductItemMaintDetailForm) form;

        try {

            if (detailForm != null) {
                DeductItemMaintCase caseData = new DeductItemMaintCase();
                BeanUtility.copyProperties(caseData, detailForm);

                // 存檔
                maintService.saveDeductItemMaintData(caseData, userData, detailForm);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 維護作業 - 扣減參數維護作業 - 老年年金維護作業 DeductItemMaintDetailAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);
        }
        catch (Exception e) {
            // 設定存檔失敗訊息
            log.error("DeductItemMaintDetailAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getSaveFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }

}
