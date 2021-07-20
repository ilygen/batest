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
import tw.gov.bli.ba.maint.forms.CpiRecMaintQueryForm;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.cases.CpiRecMaintCase;
import tw.gov.bli.ba.domain.Bapadchk;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.services.SelectOptionService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 物價指數調整紀錄維護作業 - 查詢頁面 (bapa0x070f.jsp)
 *
 * @author Kiyomi
 */
public class CpiRecMaintQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(CpiRecMaintQueryAction.class);

    private static final String FORWARD_MODIFY_CPI_REC_LIST = "modifyCpiRec"; // 物價指數調整紀錄修改作業頁面
    private static final String FORWARD_QUERY_CPI_REC_LIST = "querySuccess"; // 物價指數調整紀錄查詢作業頁面

    private MaintService maintService;
    private SelectOptionService selectOptionService;

    /**
     * 維護作業 - 物價指數調整紀錄維護作業 - 查詢頁面 (bapa0x070f.jsp) <br>
     * 按下「修改」的動作
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 物價指數調整紀錄維護作業 - 查詢頁面CpiRecMaintQueryAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        CpiRecMaintQueryForm queryForm = (CpiRecMaintQueryForm) form;
       
        try {
            List<CpiRecMaintCase> applyList = maintService.getCpiRecMaintQueryCaseBy(DateUtility.changeChineseYearType(queryForm.getIssuYear()));


            CaseSessionHelper.setCpiRecMaintQueryCase(applyList, request);
            FormSessionHelper.setCpiRecMaintQueryForm(queryForm, queryForm.getIssuYear(), request);

            return mapping.findForward(FORWARD_QUERY_CPI_REC_LIST);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("CpiRecMaintQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }

    public void setSelectOptionService(SelectOptionService selectOptionService) {
        this.selectOptionService = selectOptionService;
    }
}
