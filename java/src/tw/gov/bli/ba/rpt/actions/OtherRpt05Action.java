package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.owasp.encoder.Encode;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.job.OtherRpt05Job;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.ScheduleHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.forms.OtherRpt05Form;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.ReportUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 列印作業 - 其他類報表 - 批次核定函 - 查詢頁面 (balp0d3q0l.jsp)
 * 
 * @author Noctis
 */
public class OtherRpt05Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(OtherRpt05Action.class);

    private static final String FORWARD_LIST_PAGE = "listPage";

    private RptService rptService;

    /**
     * 列印作業 - 其他類報表 - 批次核定函 - 查詢頁面 (balp0d3q0l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 其他類報表  - 批次核定函 - 查詢頁面 OtherRpt05Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        OtherRpt05Form queryForm = (OtherRpt05Form) form;

        try {

            String payCode = queryForm.getPayCode();
            String issuYm = queryForm.getIssuYm();
            String rptKind = queryForm.getRptKind();
            
            Babatchjob batchJob = new Babatchjob();

            String fileName = "";
            if (payCode.equals("L")) {
                fileName = StringUtils.trimToEmpty(payCode) + "_" + StringUtils.trimToEmpty(DateUtility.changeChineseYearMonthType(issuYm)) + "_OtherRpt05_" + rptKind + "_" + DateUtility.getNowWestDate();
            }
            else {
                fileName = StringUtils.trimToEmpty(payCode) + "_" + StringUtils.trimToEmpty(DateUtility.changeChineseYearMonthType(issuYm)) + "_OtherRpt05_" + DateUtility.getNowWestDate();
            }

            batchJob = rptService.doQueryBatchJobStatusforOtherRpt05Action(DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()),payCode, fileName);
            
            if (batchJob == null) {
                String jobId = ScheduleHelper.scheduleNow(OtherRpt05Job.class, null, userData);
                rptService.doScheduleBatchJobforOtherRpt05Action(jobId, DateUtility.changeChineseYearMonthType(issuYm), fileName, ConstantKey.STATUS_RUNNING, payCode, userData);
               
                // 設定相同核定年月與核定日期已記入排程佇列
                saveMessages(session, DatabaseMessageHelper.repReportgeneration());
                log.debug("其他類報表  - 批次核定函 OtherRpt05Action.doReport() 計入排程完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                if (StringUtils.equals(batchJob.getStatus(), "W") || StringUtils.equals(batchJob.getStatus(), "R")) {
                    saveMessages(session, DatabaseMessageHelper.repReportgeneration());
                    log.debug("其他類報表  - 批次核定函 OtherRpt05Action.doReport() 計入排程完成 .. ...(排程中已有相同資料) ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }
                else {
		            // 檢查報表檔是否已產生過
		            File[] fileList = ReportUtility.listPDFFile(fileName);
		            if (fileList != null && fileList.length > 0) {
		                List<String> files = new ArrayList<String>();
		                for (File file : fileList) {
		                    files.add(file.getName());
		                }
		                Collections.sort(files);
		                
		                session.setAttribute(ConstantKey.OTHER_RPT_05_FILE_LIST, files);
		                return mapping.findForward(FORWARD_LIST_PAGE);
		            } else {
		            	saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
		                log.debug("其他類報表  - 批次核定函 OtherRpt05Action.doReport()查無資料");
		                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
		            }
                }
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 批次核定函 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.info("執行 列印作業 - 其他類報表  - 批次核定函 - 下載檔案 ... ");

        HttpSession session = request.getSession();
        OtherRpt05Form queryForm = (OtherRpt05Form) form;
        FileInputStream fs = null;
        ServletOutputStream sout = null;
        ByteArrayOutputStream baoOutput = null;
        try {
            String fileName = queryForm.getDownloadFileName();

            if (StringUtils.isNotBlank(fileName)) {
                fs = new FileInputStream(PropertyHelper.getProperty("rpt.path") + Encode.forJava(fileName));
                byte[] content = IOUtils.toByteArray(fs);

                // 設定回傳回 Client 端之檔案大小, 若不設定,
                // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(fileName) + "\"");
                response.setHeader("Content-Encoding", "pdf");
                response.setContentType("application/pdf"); // 設定MIME
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");
                response.setContentLength(content.length);

                baoOutput = new ByteArrayOutputStream();
                baoOutput.write(content);
                // 傳回 Client 端
                sout = null;
                sout = response.getOutputStream();
                baoOutput.writeTo(sout);
                sout.flush();

                return null;
            }
            else {
                saveMessages(session, CustomMessageHelper.getDownloadErrorMessage());
                return mapping.findForward(FORWARD_LIST_PAGE);
            }

        }
        catch (Exception e) {
            log.error("下載 批次核定函 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getDownloadErrorMessage());
            return mapping.findForward(FORWARD_LIST_PAGE);
        }
        finally {
            ExceptionUtil.safeColse(baoOutput);
            // ExceptionUtil.safeColse(sout);
            ExceptionUtil.safeColse(fs);
        }
    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
