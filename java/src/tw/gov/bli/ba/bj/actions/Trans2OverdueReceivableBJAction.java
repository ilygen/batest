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
import tw.gov.bli.ba.bj.cases.Trans2OverdueReceivableBJDetailCase;
import tw.gov.bli.ba.bj.cases.Trans2OverdueReceivableBJMasterCase;
import tw.gov.bli.ba.bj.forms.Trans2OverdueReceivableBJForm;
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
 * 批次處理 - 轉催收作業 (BABA0M030X)
 * 
 * @author Rickychi
 */
public class Trans2OverdueReceivableBJAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(Trans2OverdueReceivableBJAction.class);

    private BjService bjService;

    /**
     * 批次處理 - 轉催收作業 - 查詢頁面 (baba0m030x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 轉催收作業 - 查詢頁面 Trans2OverdueReceivableBJAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        Trans2OverdueReceivableBJForm iform = (Trans2OverdueReceivableBJForm) form;

        try {
            String unacpSdate = DateUtility.changeDateType(iform.getUnacpSdate());
            String unacpEdate = DateUtility.changeDateType(iform.getUnacpEdate());
            List<Trans2OverdueReceivableBJDetailCase> dataList = bjService.getTrans2OverdueReceivableData(unacpSdate, unacpEdate, "01");

            if (dataList.size() > 0) {
                Trans2OverdueReceivableBJMasterCase master = new Trans2OverdueReceivableBJMasterCase();
                master.setUnacpSdate(unacpSdate);
                master.setUnacpEdate(unacpEdate);
                master.setTotalAmt(bjService.getBaunacprecTotalAmt(unacpSdate, unacpEdate, "01"));
                master.setSumRecAmt(bjService.getBaunacprecSumRecAmt(unacpSdate, unacpEdate, "01"));
                // 將 查詢條件/查詢結果清單 存到 Request Scope
                CaseSessionHelper.setTrans2OverdueReceivableBJMasterCase(master, request);
                CaseSessionHelper.setTrans2OverdueReceivableBJDetailCase(dataList, request);
                log.debug("執行 批次處理 - 轉催收作業 - 查詢頁面 Trans2OverdueReceivableBJAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
            else {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("執行 批次處理 - 轉催收作業 - 查詢頁面 Trans2OverdueReceivableBJAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("Trans2OverdueReceivableBJAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 轉催收作業 - 批次處理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 轉催收作業 - 批次處理 Trans2OverdueReceivableBJAction.doBatch() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        try {
            // 執行 轉催收作業 - 批次處理
            List<Trans2OverdueReceivableBJDetailCase> caseList = CaseSessionHelper.getTrans2OverdueReceivableBJDetailCase(request);
            bjService.doTrans2OverdueReceivableBJ(caseList);

            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeTrans2OverdueReceivableBJForm(request);
            FormSessionHelper.removeTrans2OverdueReceivableBJCondForm(request);
            CaseSessionHelper.removeAllTrans2OverdueReceivableBJCase(request);

            // 設定批次處理成功訊息
            saveMessages(session, DatabaseMessageHelper.getBatchJobSuccessMessage());
            log.debug("執行 批次處理 - 轉催收作業 - 批次處理 Trans2OverdueReceivableBJAction.doBatch() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);

        }
        catch (Exception e) {
            // 設定批次處理失敗訊息
            log.error("Trans2OverdueReceivableBJAction.doBatch() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getBatchJobFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 批次處理 - 轉催收作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 轉催收作業 - 返回 Trans2OverdueReceivableBJAction.doBack() 開始 ... ");

        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeTrans2OverdueReceivableBJForm(request);
            FormSessionHelper.removeTrans2OverdueReceivableBJCondForm(request);
            CaseSessionHelper.removeAllTrans2OverdueReceivableBJCase(request);
            log.debug("執行 批次處理 - 轉催收作業 - 返回 Trans2OverdueReceivableBJAction.doBack() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_BACK);

        }
        catch (Exception e) {
            log.error("Trans2OverdueReceivableBJAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }

}
