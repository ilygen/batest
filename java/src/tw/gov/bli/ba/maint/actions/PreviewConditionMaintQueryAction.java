package tw.gov.bli.ba.maint.actions;

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
import tw.gov.bli.ba.maint.cases.PreviewConditionMaintCase;
import tw.gov.bli.ba.maint.forms.PreviewConditionMaintDetailForOldAgeAnnuityForm;
import tw.gov.bli.ba.maint.forms.PreviewConditionMaintQueryForm;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 先抽對象維護作業 - 查詢頁面 (bapa0x040f.jsp)
 * 
 * @author Goston
 */
public class PreviewConditionMaintQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(PreviewConditionMaintQueryAction.class);

    private static final String FORWARD_OLD_AGE_ANNUITY = "oldAgeAnnuity"; // 老年年金維護作業頁面

    private MaintService maintService;

    /**
     * 維護作業 - 先抽對象維護作業 - 查詢頁面 (bapa0x040f.jsp) <br>
     * 按下「確認」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 先抽對象維護作業 - 查詢頁面 PreviewConditionMaintQueryAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        PreviewConditionMaintQueryForm queryForm = (PreviewConditionMaintQueryForm) form;

        try {
            if (StringUtils.equalsIgnoreCase(queryForm.getPayKind(), "L")) { // 老年年金維護作業
                // Initial Detail Form
                PreviewConditionMaintDetailForOldAgeAnnuityForm detailForm = new PreviewConditionMaintDetailForOldAgeAnnuityForm();
                detailForm.resetForm();
                detailForm.setPayCode(queryForm.getPayKind());

                // 取得 先抽對象條件參數檔 Case
                PreviewConditionMaintCase caseData = maintService.getPreviewConditionMaintCaseBy(queryForm.getPayKind());
                if (caseData != null)
                    BeanUtility.copyProperties(detailForm, caseData);

                // 將 Detail Form 加入 Session 中
                FormSessionHelper.setPreviewConditionMaintDetailForOldAgeAnnuityForm(detailForm, request);

                log.debug("執行 維護作業 - 先抽對象維護作業 - 查詢頁面 PreviewConditionMaintQueryAction.doQuery() 完成 ... ");

                return mapping.findForward(FORWARD_OLD_AGE_ANNUITY);
            }
            else {
                // 如果 給付種類 不是 老年年金 則顯示功能未開放之訊息 (現階段未開放)
                saveMessages(session, SystemMessageHelper.getFunctionUnavailableMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("PreviewConditionMaintQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }
}
