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
import tw.gov.bli.ba.bj.forms.RunProcedureForm;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.ScheduleHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.helper.FormSessionHelper;
import tw.gov.bli.ba.bj.job.RunProcedureJob;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 批次處理 - 批次排程作業 - 批次程式執行作業 - 查詢頁面  (baba0m230x.jsp)
 * 
 * @author KIYOMI
 */
public class RunProcedureAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(RunProcedureAction.class);

    private BjService bjService;
    private static final String PROCTYPE_PROCEDURE= "9";    
    
    /**
     * 批次處理 - 批次排程作業 - 批次程式執行作業 - 查詢頁面  (baba0m230x.jsp) 按下 確認
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doRun(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 批次排程作業 - 批次程式執行作業 - 查詢頁面  RunProcedureAction.doRun() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        RunProcedureForm queryForm = (RunProcedureForm) form;

        try {
            String sRunDateBegin = queryForm.getRunDateBegin();
            String sProcedureName = queryForm.getProcedureName();
            String[] sParamsList = queryForm.getParamsList().split(",", queryForm.getParamsList().length());                                                                     
            

            String jobId = ScheduleHelper.scheduleAtSettingTime(RunProcedureJob.class, null, userData, sRunDateBegin);
            //String jobId = ScheduleHelper.scheduleNow(RunProcedureJob.class, null, userData);
            bjService.saveProcedureData(jobId, sProcedureName, sParamsList, userData.getEmpNo(), DateUtility.getNowWestDateTime(true));
            bjService.doScheduleBatchJob(jobId, null, null, null, userData.getEmpNo(), userData.getDeptId(), userData.getLoginIP(), DateUtility.getNowWestDateTime(true), PROCTYPE_PROCEDURE, ConstantKey.STATUS_WAITING, "0");
                
            // 已記入排程佇列
            saveMessages(session, DatabaseMessageHelper.getSetScheduleSuccess());
            log.debug("批次處理 - 批次程式執行排程作業 runProcedureAction.doRun() 計入排程完成 ... ");
            return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);

        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("RunProcedureAction.doRun() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public void setBjService(BjService bjService) {
        this.bjService = bjService;
    }   
   
}
