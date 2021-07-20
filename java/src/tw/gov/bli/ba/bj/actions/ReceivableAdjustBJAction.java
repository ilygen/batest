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
import tw.gov.bli.ba.bj.cases.ReceivableAdjustBJDetailCase;
import tw.gov.bli.ba.bj.cases.ReceivableAdjustBJMasterCase;
import tw.gov.bli.ba.bj.forms.ReceivableAdjustBJForm;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FormSessionHelper;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 批次處理 - 已收調整作業 (BABA0M040X)
 * 
 * @author Rickychi
 */
public class ReceivableAdjustBJAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ReceivableAdjustBJAction.class);

    private BjService bjService;

    // 批次處理 - 已收調整作業 - Master頁面
    private static final String FORWARD_MASTER_LIST = "masterList";
    // 批次處理 - 已收調整作業 - Detail頁面
    private static final String FORWARD_DETAIL_DATA = "detailData";

    /**
     * 批次處理 - 已收調整作業 - 查詢頁面 (baba0m040x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 已收調整作業 - 查詢頁面 ReceivableAdjustBJAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ReceivableAdjustBJForm iform = (ReceivableAdjustBJForm) form;

        try {
            String apNo = iform.getApNoStr();
            String benIdnNo = iform.getBenIdnNo();
            String recKind = iform.getRecKind();
            List<ReceivableAdjustBJMasterCase> dataList = bjService.getReceivableAdjMasterData(apNo, benIdnNo, recKind);

            if (dataList.size() > 0) {
                ReceivableAdjustBJMasterCase caseObj = dataList.get(0);

                // 將 查詢條件/查詢結果清單 存到 Request Scope
                CaseSessionHelper.setReceivableAdjustBJMasterCase(caseObj, request);
                CaseSessionHelper.setReceivableAdjustBJMasterCaseList(dataList, request);
                log.debug("執行 批次處理 - 已收調整作業 - 查詢頁面 ReceivableAdjustBJAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
            else {
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                log.debug("執行 批次處理 - 已收調整作業 - 查詢頁面 ReceivableAdjustBJAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReceivableAdjustBJAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 批次處理 - 已收調整作業 - 批次處理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBatch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 已收調整作業 - 批次處理 ReceivableAdjustBJAction.doBatch() 開始 ... ");
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ReceivableAdjustBJForm iform = (ReceivableAdjustBJForm) form;

        try {
            // 執行 已收調整作業 - 批次處理
            String[] baunacprecId = iform.getIdForConfirm().split(",");
            bjService.doReceivableAdjustMasterBJ(baunacprecId, userData.getEmpNo());

            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeReceivableAdjustBJForm(request);
            FormSessionHelper.removeReceivableAdjustBJCondForm(request);
            CaseSessionHelper.removeAllReceivableAdjustBJCase(request);

            // 設定批次處理成功訊息
            saveMessages(session, DatabaseMessageHelper.getBatchJobSuccessMessage());
            log.debug("執行 批次處理 - 已收調整作業 - 批次處理 ReceivableAdjustBJAction.doBatch() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);

        }
        catch (Exception e) {
            // 設定批次處理失敗訊息
            log.error("ReceivableAdjustBJAction.doBatch() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getBatchJobFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    /**
     * 批次處理 - 已收調整作業 - 明細查詢 (baba0m041x.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 已收調整作業 - 明細查詢 ReceivableAdjustBJAction.doDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ReceivableAdjustBJForm iform = (ReceivableAdjustBJForm) form;

        try {
            ReceivableAdjustBJDetailCase caseObj = bjService.getReceivableAdjDetailData(iform.getBaunacprecId());

            // 將 查詢條件/查詢結果清單 存到 Request Scope
            CaseSessionHelper.setReceivableAdjustBJDetailCase(caseObj, request);
            log.debug("執行 批次處理 - 已收調整作業 - 明細查詢 ReceivableAdjustBJAction.doDetail() 完成 ... ");
            return mapping.findForward(FORWARD_DETAIL_DATA);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReceivableAdjustBJAction.doDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 批次處理 - 已收調整作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 已收調整作業 - 返回 ReceivableAdjustBJAction.doBack() 開始 ... ");

        try {
            // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
            FormSessionHelper.removeReceivableAdjustBJForm(request);
            FormSessionHelper.removeReceivableAdjustBJCondForm(request);
            CaseSessionHelper.removeAllReceivableAdjustBJCase(request);

            log.debug("執行 批次處理 - 已收調整作業 - 返回 ReceivableAdjustBJAction.doBack() 完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_BACK);

        }
        catch (Exception e) {
            log.error("ReceivableAdjustBJAction.doBack() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }

}
