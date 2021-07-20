package tw.gov.bli.ba.bj.actions;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.ExecStatisticReportCase;
import tw.gov.bli.ba.bj.cases.ExecStatisticsDtlCase;
import tw.gov.bli.ba.bj.forms.ExecStatisticsForm;
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.job.ExecStatisticsJob;
import tw.gov.bli.ba.bj.report.ExecStatisticsReport;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.ScheduleHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

public class ExecStatisticsAction extends BaseDispatchAction {
 	private static Log log = LogFactory.getLog(ReceivableAdjustBJAction.class);
 	private static final String EMPTY_COLUMN = "";
 	private static final String PROCTYPE_EXEC_STATISTICS = "12";
 	private BjService bjService;
 	/**
     * 批次處理 -年金統計執行作業 - 執行頁面-(BAST0M270S)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doExec(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 -年金統計執行作業 - 執行頁面  ExecStatisticsAction.doExec() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        ExecStatisticsForm queryForm = (ExecStatisticsForm) form;
        try {
        	 String issuYm = DateUtility.changeChineseYearMonthType(queryForm.getIssuYm());
        	 //String qrySussess = "1";
        	 //List<Babatchjob> jobStatus = bjService.queryBatchJobStatus(issuYm,qrySussess);

             //if (jobStatus == null) {
             String jobId = ScheduleHelper.scheduleNow(ExecStatisticsJob.class, null, userData);
             bjService.doScheduleBatchJob(jobId, issuYm, EMPTY_COLUMN, EMPTY_COLUMN, userData.getEmpNo(), userData.getDeptId(),
                             userData.getLoginIP(), DateUtility.getNowWestDateTime(true), PROCTYPE_EXEC_STATISTICS, ConstantKey.STATUS_WAITING, "0");

             //訊息:年金統計執行作業計入排程
             saveMessages(session, DatabaseMessageHelper.getScheduleOperation());
             log.debug("批次處理 -年金統計執行作業 doExec 計入排程完成 ... ");
             return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
             //} else {
             //	 //訊息:該處理年月已產生勞保年金統計檔資料，不可重複執行勞保年金統計作業。
             //	 saveMessages(session, DatabaseMessageHelper.getScheduleOperationDupre());
             //	 log.debug("批次處理 -年金統計執行作業 doExec 排程中已有相同資料");
             //	 return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
             //}
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecStatisticsAction.doExec() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
	 * 批次處理 - 年金統計執行作業  - 進度查詢 -(BAST0M270S)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
    public ActionForward doProgress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 -年金統計執行作業 - 進度查詢  ExecStatisticsAction.doProgress() 開始 ... ");

        HttpSession session = request.getSession();
        ExecStatisticsForm queryForm = (ExecStatisticsForm) form;
        try {
        	 String issuYm = DateUtility.changeChineseYearMonthType(queryForm.getIssuYm());
        	 List<Babatchjob> babatchjob = bjService.queryBatchJobStatus(issuYm);

             if (babatchjob == null) {
                 saveMessages(session, DatabaseMessageHelper.getExecStatisticsDataEmpty(queryForm.getIssuYm()));
                 log.debug("批次處理 -年金統計執行作業 doProgress:查無該處理年月的勞保年金統計檔資料 ... ");
                 return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
             } else {
            	 CaseSessionHelper.setExecStatisticsDataList(babatchjob, request);
            	 log.debug("批次處理 -年金統計執行作業 doProgress 查詢完成");
            	 return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL);
             }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecStatisticsAction.doProgress() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }
    
    /**
	 * 批次處理 - 年金統計執行作業處理狀況查詢  - 狀態明細 -(BAST0M271Q)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
    public ActionForward doQryProgressDtl(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 年金統計執行作業處理狀況查詢 - 進度查詢  ExecStatisticsAction.doProgress() 開始 ... ");
        HttpSession session = request.getSession();
        ExecStatisticsForm queryForm = (ExecStatisticsForm) form;
        try {
        	 String baJobid = queryForm.getBaJobId();
        	 List<ExecStatisticsDtlCase> progressSteps = bjService.getStatisticsProgressStepDtl(baJobid);
             if (progressSteps == null) {
                 saveMessages(session, DatabaseMessageHelper.getExecStatisticsDtlDataEmpty(queryForm.getIssuYm()));
                 log.debug("批次處理 -年金統計執行作業 doQryProgressDtl:查無該勞保年金統計檔明細步驟 ... ");
                 return mapping.findForward(ConstantKey.FORWARD_QUERY_DTL_EMPTY);
             } else {
            	 CaseSessionHelper.setExecStatisticsDataDtlList(progressSteps, request);
            	 log.debug("批次處理 -年金統計執行作業 doQryProgressDtl 查詢完成");
            	 return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL_SUCCESS);
             }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecStatisticsAction.doQryProgressDtl() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL_FAIL);
        }
    }
    
    /**
	 * 批次處理 - 年金統計執行作業處理狀況查詢  - 檢核報表 -(BAST0M271Q)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 -年金統計執行作業處理狀況查詢 - 檢核報表  ExecStatisticsAction.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        ExecStatisticsForm queryForm = (ExecStatisticsForm) form;
        try {
        	List<Babatchjob> babatchjob = CaseSessionHelper.getExecStatisticsDataList(request);
        	String baJobid = queryForm.getBaJobId();
        	String issuYm = "";
        	for(Babatchjob jobData : babatchjob) {
        		if(StringUtils.equals(baJobid, jobData.getBaJobId())) {
        			issuYm = DateUtility.changeChineseYearMonthType(jobData.getIssuYm());
        			break;
        		}
        	}
        	List<ExecStatisticReportCase> rptList = bjService.getStatisticsReportData(issuYm);

            if (rptList == null) {
                saveMessages(session, DatabaseMessageHelper.getExecStatisticsDataEmpty(issuYm));
                log.debug("批次處理 -年金統計執行作業 doReport:查無該勞保年金統計檔明細步驟 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_DTL_EMPTY);
            }
            
            // 產生EXCEL報表
            ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
            
            ExecStatisticsReport report = new ExecStatisticsReport();
            baoOutput = report.doReport(rptList);

            // 設定回傳回 Client 端之檔案大小, 若不設定,
            // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
            response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava("ExecStatisticsReport"+"_"+issuYm) + ".xls" + "\"");
            response.setContentType("application/vnd.ms-excel"); // 設定MIME
            response.setHeader("Pargma", "no-cache");
            response.setHeader("Cache-Control", "max-age=1");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentLength(baoOutput.size());

            // 傳回 Client 端
            ServletOutputStream sout = null;
            try {
                sout = response.getOutputStream();
                baoOutput.writeTo(sout);
                sout.flush();
                log.debug("批次處理 -年金統計執行作業 doReport 產製報表完成");
            }
            catch (Exception e) {
            }
            finally {
                if (baoOutput != null)
                    baoOutput.close();
                if (sout != null)
                    sout.close();
            }

            return null;
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("ExecStatisticsAction.doReport() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL_FAIL);
        }
    }
    /**
   	 * 批次處理 - 年金統計執行作業處理狀況查詢   - 返回 BAST0M270S
   	 * 
   	 * @param mapping
   	 * @param form
   	 * @param request
   	 * @param response
   	 * @return
   	 */
    public ActionForward doBack270(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 年金統計執行作業處理狀況查詢  - 返回  ExecStatisticsAction.doBack270() 開始 ... ");
        try {
			return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
		} catch (Exception e) {
			return mapping.findForward(ConstantKey.FORWARD_QUERY_SUCCESS);
		}
    }
    /**
   	 * 批次處理 - 年金統計執行作業處理狀況查詢   - 返回 BAST0M271Q
   	 * 
   	 * @param mapping
   	 * @param form
   	 * @param request
   	 * @param response
   	 * @return
   	 */
    public ActionForward doBack271(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 批次處理 - 年金統計執行作業處理狀況查詢  - 返回  ExecStatisticsAction.doBack271() 開始 ... ");
        try {
			return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL);
		} catch (Exception e) {
			return mapping.findForward(ConstantKey.FORWARD_QUERY_DETAIL);
		}
    }
    
	public static void setLog(Log log) {
		ExecStatisticsAction.log = log;
	}
	
	public void setBjService(BjService bjService) {
		this.bjService = bjService;
	}
}
