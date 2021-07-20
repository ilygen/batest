package tw.gov.bli.ba.bj.actions;

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
import tw.gov.bli.ba.bj.cases.QryProcedureCase;
import tw.gov.bli.ba.bj.forms.QryProcedureForm;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FormSessionHelper;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 批次處理 - 批次排程作業 - 批次查詢作業 - 查詢頁面  (baba0m240x.jsp)
 * 
 * @author KIYOMI
 */
public class QryProcedureAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(QryProcedureAction.class);

    private BjService bjService;
    
    // session key 	
 	private static final String PROCTYPE_PROCEDURE= "9";

 	
    /**
     * 批次處理 - 批次程式查詢作業 - 進度查詢 - (BABA0M241Q)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        QryProcedureForm queryForm = (QryProcedureForm) form;
        try {
            String startDate = queryForm.getUpdateDateBegin();
            String endDate = queryForm.getUpdateDateEnd();
            
            //查詢目前進度
            List<QryProcedureCase> caseData = bjService.getProcedureProgressStep(PROCTYPE_PROCEDURE, startDate, endDate);
            if(caseData == null) {
                saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
                log.debug("批次處理 - 批次程式查詢作業-進度查詢 - QryProcedureAction.doQuery() 完成 ...(查無資料) ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            } else {
                CaseSessionHelper.setQryProcedureProgressList(caseData, request);
                log.debug("批次處理 - 批次程式查詢作業-進度查詢 - QryProcedureAction.doQuery() 完成 ...");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_LIST);
            }
        } catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("QryProcedureAction.doQuery() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
        
    }
 	
    
    /**
     * 批次處理 - 批次程式查詢作業 - 返回首頁-(BABA0M241Q)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackFirst(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            return mapping.findForward(ConstantKey.FORWARD_BACK);
        } catch (Exception e) {
            return null;
        }
        
    }    
 	 	
    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }
   
   
}
