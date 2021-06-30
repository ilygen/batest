package tw.gov.bli.ba.query.actions;

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
import tw.gov.bli.ba.domain.Maadmrec;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.query.cases.ReceivableQueryDetailCase;
import tw.gov.bli.ba.query.cases.ReceivableQueryMasterCase;
import tw.gov.bli.ba.query.forms.ReceivableQueryForm;
import tw.gov.bli.ba.query.helper.CaseSessionHelper;
import tw.gov.bli.ba.query.helper.FormSessionHelper;
import tw.gov.bli.ba.services.QueryService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 查詢作業 - 應收查詢作業 (BAIQ0D020Q)
 * 
 * @author Rickychi
 */
public class ReceivableQueryAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(ReceivableQueryAction.class);

    private QueryService queryService;

    // 查詢作業 - 給付查詢作業 - 清單
    private static final String FORWARD_MASTER_LIST = "masterList";
    // 查詢作業 - 給付查詢作業 - 明細查詢
    private static final String FORWARD_DETAIL_DATA = "detailData";

    /**
     * 查詢作業 - 應收查詢作業 - 查詢頁面(baiq0d020q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 應收查詢作業 - 查詢頁面 ReceivableQueryAction.doQuery() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ReceivableQueryForm iform = (ReceivableQueryForm) form;

        try {
            String apNo = iform.getApNoStr();
            String evtIdnNo = iform.getEvtIdnNo();
            String unacpSdate = DateUtility.changeDateType(iform.getUnacpSdate());
            String unacpEdate = DateUtility.changeDateType(iform.getUnacpEdate());

            List<ReceivableQueryMasterCase> masterList = queryService.getReceivableMasterList(apNo, evtIdnNo, unacpSdate, unacpEdate);

            if (masterList.size() == 0) {
                // 設定查無資料訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultMessage());

                log.debug("執行 查詢作業 - 應收查詢作業 - 查詢頁面 ReceivableQueryAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
            }
            else {
                ReceivableQueryMasterCase caseObj = masterList.get(0);
                if (StringUtils.isNotBlank(caseObj.getApNo()) && caseObj.getApNo().length() > 1) {
                    caseObj.setPagePayKind(caseObj.getApNo().substring(0, 1));
                }
                caseObj.setUnacpSdate(unacpSdate);
                caseObj.setUnacpEdate(unacpEdate);

                // 將 查詢條件/查詢結果清單 存到 Request Scope
                ReceivableQueryForm qryForm = new ReceivableQueryForm();
                BeanUtility.copyProperties(qryForm, iform);
                FormSessionHelper.setReceivableQueryCondForm(qryForm, request);
                CaseSessionHelper.setReceivableQueryMasterCase(caseObj, request);
                CaseSessionHelper.setReceivableQueryMasterCaseList(masterList, request);

                log.debug("執行 查詢作業 - 應收查詢作業 - 查詢頁面 ReceivableQueryAction.doQuery() 完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReceivableQueryAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_FAIL);
        }
    }

    /**
     * 查詢作業 - 應收查詢作業 - 明細查詢(baiq0d021q.jsp)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 應收查詢作業 - 明細查詢 ReceivableQueryAction.doDetail() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ReceivableQueryForm iform = (ReceivableQueryForm) form;

        try {
            String apNo = iform.getApNo();
            String evtIdnNo = iform.getEvtIdnNo();
            String unacpDate = iform.getUnacpDate();

            List<ReceivableQueryDetailCase> detailList = queryService.getReceivableDetailList(apNo, evtIdnNo, unacpDate);

            // if (detailList.size() == 0) {
            // // 設定查無資料訊息
            // saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
            //
            // log.debug("執行 查詢作業 - 應收查詢作業 - 明細查詢 ReceivableQueryAction.doDetail() 完成 ... ");
            // return mapping.findForward(FORWARD_MASTER_LIST);
            // }
            // else {
            // 將 查詢條件/查詢結果清單 存到 Request Scope
            
            CaseSessionHelper.setReceivableQueryDetailCaseList(detailList, request);
            
            log.debug("執行 查詢作業 - 應收查詢作業 - 查詢頁面 ReceivableQueryAction.doDetail() 完成 ... ");
            return mapping.findForward(FORWARD_DETAIL_DATA);
            // }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ReceivableQueryAction.doDetail() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(FORWARD_MASTER_LIST);
        }
    }

    /**
     * 查詢作業 - 應收查詢作業 - 返回
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBack(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 查詢作業 - 應收查詢作業 - 返回 ReceivableQueryAction.doBack() 開始 ... ");

        // 將之前的 Form 及相關 List Case / Detail Case 自 Session 中移除
        FormSessionHelper.removeReceivableQueryForm(request);
        FormSessionHelper.removeReceivableQueryCondForm(request);
        CaseSessionHelper.removeAllReceivableQueryCase(request);

        log.debug("執行 查詢作業 - 給付查詢作業 - 返回 ReceivableQueryAction.doBack() 完成 ... ");
        return mapping.findForward(ConstantKey.FORWARD_BACK);
    }

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }
}
