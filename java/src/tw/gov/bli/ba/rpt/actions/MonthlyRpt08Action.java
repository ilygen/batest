package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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
import tw.gov.bli.ba.bj.helper.CaseSessionHelper;
import tw.gov.bli.ba.bj.job.MonthlyRpt08Job;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.ScheduleHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt08ProgressCase;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt08Form;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.ReportUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 列印作業 - 月核定處理相關報表 - 月核定合格清冊 - 查詢頁面 (balp0d380l.jsp)
 * 
 * @author swim
 */
public class MonthlyRpt08Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt08Action.class);

    private static final String FORWARD_LIST_PAGE = "listPage";
    private static final String PROCTYPE_BATCH_RPT08_N = "7";// 月核定合格清冊
    private RptService rptService;

    public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.info("執行 列印作業 - 月核定處理相關報表 - 月核定合格清冊 - 下載檔案 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt08Form queryForm = (MonthlyRpt08Form) form;
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
            log.error("下載 月核定合格清冊 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getDownloadErrorMessage());
            return mapping.findForward(FORWARD_LIST_PAGE);
        }
        finally {
            ExceptionUtil.safeColse(baoOutput);
            // ExceptionUtil.safeColse(sout);
            ExceptionUtil.safeColse(fs);
        }

    }

    /**
     * 列印作業 - 月核定處理相關報表 - 月核定合格清冊 - 查詢頁面 (balp0d380l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.info("執行 列印作業 - 月核定處理相關報表 - 月核定合格清冊 - 查詢頁面 MonthlyRpt08Action.doReport() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt08Form queryForm = (MonthlyRpt08Form) form;

        try {
            String npWithLip = queryForm.getNpWithLip();// 國併勞
            String payCode = queryForm.getPayCode();
            String fileNpWithLip = "N";
            if (StringUtils.isNotBlank(npWithLip))
                fileNpWithLip = npWithLip;
            Babatchjob batchJob = new Babatchjob();
            String paySeqNo = null;
            if (StringUtils.equals(payCode, "K")) {
                if (StringUtils.equals(fileNpWithLip, "Y")) {
                    paySeqNo = "2";
                    session.setAttribute("seqNo", "2");
                }
                else {
                    paySeqNo = "1";
                    session.setAttribute("seqNo", "1");
                }
            }
            else {
                paySeqNo = " ";
                session.setAttribute("seqNo", " ");
            }

            batchJob = rptService.doScheduleBatchJobStatus(DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()), DateUtility.changeDateType(queryForm.getChkDate()), queryForm.getPayCode(), PROCTYPE_BATCH_RPT08_N, paySeqNo);

            if (batchJob == null) {
                String jobId = ScheduleHelper.scheduleNow(MonthlyRpt08Job.class, null, userData);
                rptService.doScheduleBatchJob(jobId, DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()), DateUtility.changeDateType(queryForm.getChkDate(), false), queryForm.getPayCode(), userData.getEmpNo(), userData.getDeptId(),
                                userData.getLoginIP(), DateUtility.getNowWestDateTime(true), PROCTYPE_BATCH_RPT08_N, ConstantKey.STATUS_WAITING, paySeqNo);

                // 設定相同核定年月與核定日期已記入排程佇列
                saveMessages(session, DatabaseMessageHelper.getScheduleOperationSuccess());
                log.debug("月核定處理相關報表 - 月核定合格清冊排程作業 MonthlyRpt08Action.doReport() 計入排程完成 ... ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                if (StringUtils.equals(batchJob.getStatus(), "W") || StringUtils.equals(batchJob.getStatus(), "R")) {
                    saveMessages(session, DatabaseMessageHelper.getDuplicateWaiting());
                    log.debug("月核定處理相關報表 - 月核定合格清冊排程作業 MonthlyRpt08Action.doReport() 計入排程完成 .. ...(排程中已有相同資料) ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }
                else {
                    String printDate = DateUtility.getNowChineseDate();
                    String fileName = StringUtils.trimToEmpty(queryForm.getPayCode()) + "_" + StringUtils.trimToEmpty(DateUtility.changeChineseYearMonthType(queryForm.getIssuYm())) + "_"
                                    + StringUtils.trimToEmpty(DateUtility.changeDateType(queryForm.getChkDate())) + "_" + fileNpWithLip + "_MonthlyRpt08_";
                    // 檢查報表檔是否已產生過
                    File[] fileList = ReportUtility.listPDFFile(fileName);
                    if (fileList != null && fileList.length > 0) {
                        List<String> files = new ArrayList<String>();
                        for (File file : fileList) {
                            files.add(file.getName());
                        }
                        session.setAttribute(ConstantKey.MONTHLY_RPT_08_FILE_LIST, files);
                        return mapping.findForward(FORWARD_LIST_PAGE);
                    }
                    else {
                        saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
                        log.debug("月核定處理相關報表 - 月核定合格清冊排程作業 MonthlyRpt08Action.doReport()查無資料");
                        return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                    }
                }
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 月核定合格清冊 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 合格清冊產製報表進度查詢
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doProgress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        MonthlyRpt08Form queryForm = (MonthlyRpt08Form) form;
        try {
            String paySeqNo = null;
            if (StringUtils.equals(queryForm.getPayCode(), "K")) {
                if (StringUtils.equals(queryForm.getNpWithLip(), "Y")) {
                    paySeqNo = "2";
                    session.setAttribute("seqNo", "2");
                }
                else {
                    paySeqNo = "1";
                    session.setAttribute("seqNo", "1");
                }
            }
            else {
                paySeqNo = " ";
            }
            // 查詢目前進度
            List<MonthlyRpt08ProgressCase> caseData = rptService.getRpt08ProgressStep(PROCTYPE_BATCH_RPT08_N, DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()), DateUtility.changeDateType(queryForm.getChkDate()), queryForm.getPayCode(),
                            paySeqNo);
            if (caseData == null) {
                saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
                log.debug("列印作業 - 月核定處理相關報表  - 合格清冊產製報表進度查詢-進度查詢 - MonthlyRpt08Action.doProgress() 完成 ...(查無資料) ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                CaseSessionHelper.setMonthlyRpt08ProgressList(caseData, request);
                log.debug("列印作業 - 月核定處理相關報表  - 合格清冊產製報表進度查詢-進度查詢  - MonthlyRpt08Action.doProgress() 完成 ...");
                return mapping.findForward(ConstantKey.FOWARD_QRY_PROGRESS);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthlyRpt08Action.doProgress() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    /**
     * 批次處理 - 老年年金線上批次產製媒體檔作業-返回首頁-(BABA0M170Q)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doBackFirst(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
        }
        catch (Exception e) {
            return null;
        }

    }

    public void setRptService(RptService rptService) {
        this.rptService = rptService;
    }
}
