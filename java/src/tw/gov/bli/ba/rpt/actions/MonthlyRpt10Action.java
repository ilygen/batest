package tw.gov.bli.ba.rpt.actions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import tw.gov.bli.ba.bj.job.MonthlyRpt10Job;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.ScheduleHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10ProgressCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type1PayAmtCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type1RptCase;
import tw.gov.bli.ba.rpt.forms.MonthlyRpt10Form;
import tw.gov.bli.ba.rpt.report.MonthlyRpt10Type1Report;
import tw.gov.bli.ba.rpt.report.MonthlyRpt10Type3Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK10Type1Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK10Type3Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS10Type1Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS10Type3Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.ba.util.ReportUtility;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 列印作業 - 月核定處理相關報表 - 核付清單及核付明細表 - 查詢頁面 (balp0d3a0l.jsp)
 * 
 * @author Goston
 * @version    V1.1   20221019  add fileName
 */
public class MonthlyRpt10Action extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(MonthlyRpt10Action.class);
    private static String PROCTYPE_BATCH_RPT10 = "6";
    private RptService rptService;

    /**
     * 列印作業 - 月核定處理相關報表 - 核付清單及核付明細表 - 查詢頁面 (balp0d3a0l.jsp) <br>
     * 按下「列印」的動作
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 列印作業 - 月核定處理相關報表 - 核付清單及核付明細表 - 查詢頁面 MonthlyRpt10Action.doReport() 開始 ... ");
        String paySeqNo = "";
        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt10Form queryForm = (MonthlyRpt10Form) form;

        try {
            String payCode = queryForm.getPayCode();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("payCode", payCode);

            if (queryForm.getNpWithLip().equals("Y") && queryForm.getPayCode().equals("K")) {
                paySeqNo = "2";
                session.setAttribute("seqNo", "2");
            }
            else {
                paySeqNo = "1";
                session.setAttribute("seqNo", "1");
            }

            if (StringUtils.equalsIgnoreCase(queryForm.getReportType(), "1")) { // 核付清單

                List caseList = null;
                HashMap payAmtMap = null;
                List subCaseList = null;
                caseList = new ArrayList<MonthlyRpt10Type1RptCase>();
                payAmtMap = new HashMap<String, MonthlyRpt10Type1PayAmtCase>();
                subCaseList = new ArrayList<MonthlyRpt10Type1RptCase>();
                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(queryForm.getPayCode())) {// 老年
                    // 抓報表資料
                    // List<MonthlyRpt10Type1RptCase> caseList = rptService.getMonthlyRpt10RptType1DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());
                    // HashMap<String, MonthlyRpt10Type1PayAmtCase> payAmtMap = rptService.getMonthlyRpt10RptType1PayAmtDataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());
                    caseList = rptService.getMonthlyRpt10RptType1DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate(), new String[] { "F", "N", "Z" }, "NOT IN",
                                    new String[] { "A.PAYTYP", "A.APNO", "A.SEQNO", "A.ISSUYM", "A.PAYYM" });
                    // 代扣補償金
                    subCaseList = rptService.getMonthlyRpt10RptType1DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate(), new String[] { "Z" }, "IN", new String[] { "A.BENEVTREL", "A.PAYTYP", "A.COMPNAME1", "A.APNO" });
                    payAmtMap = rptService.getMonthlyRpt10RptType1PayAmtDataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(queryForm.getPayCode())) {// 失能
                    caseList = rptService.getMonthlyRptK10RptType1DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate(), paySeqNo);
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(queryForm.getPayCode())) {// 遺屬
                    caseList = rptService.getMonthlyRptS10RptType1DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());
                }

                if (caseList.size() == 0) {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }
                else {
                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(queryForm.getPayCode())) {// 老年
                        MonthlyRpt10Type1Report report = new MonthlyRpt10Type1Report();
                        baoOutput = report.doReport(userData, caseList, payAmtMap, subCaseList, map);
                    }
                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(queryForm.getPayCode())) {// 失能
                        MonthlyRptK10Type1Report report = new MonthlyRptK10Type1Report();
                        baoOutput = report.doReport(userData, caseList, map);
                    }
                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(queryForm.getPayCode())) {// 遺屬
                        MonthlyRptS10Type1Report report = new MonthlyRptS10Type1Report();
                        baoOutput = report.doReport(userData, caseList, map);
                    }

                    String printDate = DateUtility.getNowChineseDate();

                    // 設定回傳回 Client 端之檔案大小, 若不設定,
                    // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                    // response.setHeader("Content-disposition", "attachment; filename=\"MonthlyRpt10_List_" + printDate + ".pdf" + "\"");
                    response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(queryForm.getPayCode()) + "_MonthlyRpt10Type1_" + Encode.forJava(printDate) + ".pdf" + "\"");
                    response.setHeader("Content-Encoding", "pdf");
                    response.setContentType("application/pdf"); // 設定MIME
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
            }
            else if (StringUtils.equalsIgnoreCase(queryForm.getReportType(), "3")) { // 核付清單(郵政匯票)

                List caseList = null;
                HashMap payAmtMap = null;
                List subCaseList = null;
                caseList = new ArrayList<MonthlyRpt10Type1RptCase>();
                payAmtMap = new HashMap<String, MonthlyRpt10Type1PayAmtCase>();
                subCaseList = new ArrayList<MonthlyRpt10Type1RptCase>();
                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(queryForm.getPayCode())) {// 老年
                    // 抓報表資料
                    // List<MonthlyRpt10Type3RptCase> caseList = rptService.getMonthlyRpt10RptType3DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());
                    // HashMap<String, MonthlyRpt10Type1PayAmtCase> payAmtMap = rptService.getMonthlyRpt10RptType1PayAmtDataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());
                    caseList = rptService.getMonthlyRpt10RptType3DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate(), new String[] { "F", "N", "Z" }, "NOT IN",
                                    new String[] { "A.PAYTYP", "A.APNO", "A.SEQNO", "A.ISSUYM", "A.PAYYM" });
                    // 代扣補償金
                    subCaseList = rptService.getMonthlyRpt10RptType3DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate(), new String[] { "Z" }, "IN", new String[] { "A.BENEVTREL", "A.PAYTYP", "A.COMPNAME1", "A.APNO" });
                    payAmtMap = rptService.getMonthlyRpt10RptType1PayAmtDataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(queryForm.getPayCode())) {// 失能
                    caseList = rptService.getMonthlyRptK10RptType3DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate(), paySeqNo);
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(queryForm.getPayCode())) {// 遺屬
                    caseList = rptService.getMonthlyRptS10RptType3DataBy(queryForm.getPayCode(), queryForm.getIssuYm(), queryForm.getChkDate());
                }

                if (caseList.size() == 0) {
                    // 設定 無查詢資料 訊息
                    saveMessages(session, DatabaseMessageHelper.getNoResultMessage());
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }
                else {
                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();

                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(queryForm.getPayCode())) {// 老年
                        MonthlyRpt10Type3Report report = new MonthlyRpt10Type3Report();
                        baoOutput = report.doReport(userData, caseList, payAmtMap, subCaseList, map);
                    }
                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(queryForm.getPayCode())) {// 失能
                        MonthlyRptK10Type3Report report = new MonthlyRptK10Type3Report();
                        baoOutput = report.doReport(userData, caseList, map);
                    }
                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(queryForm.getPayCode())) {// 遺屬
                        MonthlyRptS10Type3Report report = new MonthlyRptS10Type3Report();
                        baoOutput = report.doReport(userData, caseList, map);
                    }

                    String printDate = DateUtility.getNowChineseDate();

                    // 設定回傳回 Client 端之檔案大小, 若不設定,
                    // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
                    // response.setHeader("Content-disposition", "attachment; filename=\"MonthlyRpt10_Post_" + printDate + ".pdf" + "\"");
                    response.setHeader("Content-disposition", "attachment; filename=\"" + Encode.forJava(queryForm.getPayCode()) + "_MonthlyRpt10Type3_" + Encode.forJava(printDate) + ".pdf" + "\"");
                    response.setHeader("Content-Encoding", "pdf");
                    response.setContentType("application/pdf"); // 設定MIME
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
            }
            else {// 核付明細表
                Babatchjob batchJob = rptService.doScheduleBatchJobStatusRpt10(DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()), DateUtility.changeDateType(queryForm.getChkDate()), queryForm.getPayCode(), PROCTYPE_BATCH_RPT10, paySeqNo);
                if (batchJob == null) {
                    String jobId = ScheduleHelper.scheduleNow(MonthlyRpt10Job.class, null, userData);
                    rptService.doScheduleBatchJob(jobId, DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()), DateUtility.changeDateType(queryForm.getChkDate(), false), queryForm.getPayCode(), userData.getEmpNo(), userData.getDeptId(),
                                    userData.getLoginIP(), DateUtility.getNowWestDateTime(true), PROCTYPE_BATCH_RPT10, ConstantKey.STATUS_WAITING, paySeqNo);
                    // 設定相同核定年月與核定日期已記入排程佇列
                    saveMessages(session, DatabaseMessageHelper.getScheduleOperationSuccess());
                    log.debug("月核定處理相關報表 - 核付明細表排程作業 MonthlyRpt10Action.doReport() 計入排程完成 ... ");
                    return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                }
                else {
                    if (StringUtils.equals(batchJob.getStatus(), "W") || StringUtils.equals(batchJob.getStatus(), "R")) {
                        saveMessages(session, DatabaseMessageHelper.getDuplicateWaiting());
                        log.debug("月核定處理相關報表 - 核付明細表排程作業 MonthlyRpt10Action.doReport() 計入排程完成 .. ...(排程中已有相同資料) ");
                        return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                    }
                    else {
                        String printDate = DateUtility.getNowChineseDate();
                        String chkDate = queryForm.getChkDate();
                        String fileName = "";
                        
                      //V1.1 add 
                        if (StringUtils.isNotBlank(paySeqNo)) {
                        	 fileName = batchJob.getFileName();
                        } else {
                        	if (StringUtils.equals(paySeqNo, "2")) {
                                fileName = queryForm.getPayCode() + "_" + chkDate + "_MonthlyRpt10Type2_36_" + printDate;
                            }
                            else {
                                fileName = queryForm.getPayCode() + "_" + chkDate + "_MonthlyRpt10Type2_" + printDate;
                            }
                        }
                        /*
	                        if (StringUtils.equals(paySeqNo, "2")) {
	                            fileName = queryForm.getPayCode() + "_" + chkDate + "_MonthlyRpt10Type2_36_" + printDate;
	                        }
	                        else {
	                            fileName = queryForm.getPayCode() + "_" + chkDate + "_MonthlyRpt10Type2_" + printDate;
	                        } V1.1 End 
                        */

                        File[] fileList = ReportUtility.listPDFFile(fileName);
                        if (fileList != null && fileList.length > 0) {
                            List<String> files = new ArrayList<String>();
                            for (File file : fileList) {
                                files.add(file.getName());
                            }
                            session.setAttribute(ConstantKey.MONTHLY_RPT_10_FILE_LIST, files);
                            return mapping.findForward(ConstantKey.FORWARD_QUERY_LIST);
                        }
                        else {
                            saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
                            log.debug("月核定處理相關報表 - 核付明細表排程作業 MonthlyRpt10Action.doReport()查無資料");
                            return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("產製 核付清單及核付明細表 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_FAIL);
        }
    }

    public ActionForward downloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.info("執行 列印作業 - 月核定處理相關報表 - 核付清單及核付明細表 - 下載檔案 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        MonthlyRpt10Form queryForm = (MonthlyRpt10Form) form;
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
                try {
                    sout = response.getOutputStream();
                    baoOutput.writeTo(sout);
                    sout.flush();
                }
                catch (Exception e) {
                }
                finally {
                    ExceptionUtil.safeColse(baoOutput);
                    ExceptionUtil.safeColse(sout);
                    ExceptionUtil.safeColse(fs);
                }

                return null;
            }
            else {
                saveMessages(session, CustomMessageHelper.getDownloadErrorMessage());
                return mapping.findForward(ConstantKey.FORWARD_QUERY_LIST);
            }

        }
        catch (Exception e) {
            log.error("下載  核付清單及核付明細表發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, CustomMessageHelper.getDownloadErrorMessage());
            return mapping.findForward(ConstantKey.FORWARD_QUERY_LIST);
        }
        finally {
            ExceptionUtil.safeColse(baoOutput);
            ExceptionUtil.safeColse(sout);
            ExceptionUtil.safeColse(fs);
        }
    }

    /**
     * 列印作業 - 月核定處理相關報表 - 核付明細表產製報表進度查詢
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doProgress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        MonthlyRpt10Form queryForm = (MonthlyRpt10Form) form;
        try {
            String paySeqNo = null;
            if (queryForm.getNpWithLip().equals("Y") && queryForm.getPayCode().equals("K")) {
                paySeqNo = "2";
                session.setAttribute("seqNo", "2");
            }
            else {
                paySeqNo = "1";
                session.setAttribute("seqNo", "1");
            }
            // 查詢目前進度
            List<MonthlyRpt10ProgressCase> caseData = rptService.getRpt10ProgressStep(PROCTYPE_BATCH_RPT10, DateUtility.changeChineseYearMonthType(queryForm.getIssuYm()), DateUtility.changeDateType(queryForm.getChkDate()), queryForm.getPayCode(),
                            paySeqNo);
            if (caseData == null) {
                saveMessages(session, DatabaseMessageHelper.getNoDataMessage());
                log.debug("列印作業 - 月核定處理相關報表  - 核付清單及核付明細表-進度查詢 - MonthlyRpt10Action.doProgress() 完成 ...(查無資料) ");
                return mapping.findForward(ConstantKey.FORWARD_QUERY_EMPTY);
            }
            else {
                CaseSessionHelper.setMonthlyRpt10ProgressList(caseData, request);
                log.debug("列印作業 - 月核定處理相關報表  -核付清單及核付明細表-進度查詢  - MonthlyRpt10Action.doProgress() 完成 ...");
                return mapping.findForward(ConstantKey.FOWARD_QRY_PROGRESS);
            }
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("MonthlyRpt10Action.doProgress() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
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
