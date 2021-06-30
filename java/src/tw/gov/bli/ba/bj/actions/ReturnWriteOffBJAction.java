package tw.gov.bli.ba.bj.actions;

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
import tw.gov.bli.ba.bj.cases.ReturnWriteOffBJCase;
import tw.gov.bli.ba.bj.forms.ReturnWriteOffBJForm;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 批次處理 - 收回作業 (BABA0M020X)
 * 
 * @author swim
 */
public class ReturnWriteOffBJAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ReturnWriteOffBJAction.class);

    private BjService bjService;

    /**
     * 批次處理 - 收回作業 - 查詢頁面 (baba0m020x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 收回作業 - 查詢頁面 ReturnWriteOffBJAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ReturnWriteOffBJForm iform = (ReturnWriteOffBJForm) form;

        try {
            String upTimeBeg = DateUtility.changeDateType(iform.getUpTimeBeg());
            String upTimeEnd = DateUtility.changeDateType(iform.getUpTimeEnd());

            List<ReturnWriteOffBJCase> dataList = bjService.getReturnWriteOffBJData(DateUtility.changeDateType(upTimeBeg), DateUtility.changeDateType(upTimeEnd));

            if (dataList.size() > 0) {

                // 將 查詢條件/查詢結果清單 存到 Request Scope
                CaseSessionHelper.setReturnWriteOffBJCase(dataList, request);

                log.debug("執行 批次處理 - 收回作業 - 查詢頁面 ReturnWriteOffBJAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
            else {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("執行 批次處理 - 收回作業 - 查詢頁面 ReturnWriteOffBJAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReturnWriteOffBJAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 收回作業 - 批次處理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 收回作業 - 批次處理 ReturnWriteOffBJAction.doBatch() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ReturnWriteOffBJForm iform = (ReturnWriteOffBJForm) form;

        try {
            // 執行 回壓處理狀態
            String[] baBatchRecId = iform.getIdForConfirm().split(",");
            bjService.doReturnWriteOffBJ(baBatchRecId, userData.getEmpNo());

            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeReturnWriteOffBJForm(request);

            // 設定批次處理成功訊息
            saveMessages(session, DatabaseMessageHelper.getBatchJobSuccessMessage());
            log.debug("執行 批次處理 - 收回作業 - 批次處理 ReturnWriteOffBJAction.doBatch() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);

        }
        catch (Exception e) {
            // 設定批次處理失敗訊息
            log.error("ReturnWriteOffBJAction.doBatch() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getBatchJobFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 批次處理 - 收回作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 收回作業 - 返回 ReturnWriteOffBJAction.doBack() 開始 ... ");

        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeReturnWriteOffBJForm(request);
            CaseSessionHelper.removeReturnWriteOffBJCase(request);

            log.debug("執行 批次處理 - 收回作業 - 返回 ReturnWriteOffBJAction.doBack() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_BACK);

        }
        catch (Exception e) {
            log.error("ReturnWriteOffBJAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }
}
