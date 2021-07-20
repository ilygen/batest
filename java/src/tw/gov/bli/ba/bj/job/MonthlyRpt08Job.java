package tw.gov.bli.ba.bj.job;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.owasp.encoder.Encode;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.actions.MonthlyRpt08Action;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt08Case;
import tw.gov.bli.ba.rpt.report.MonthlyRpt08Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK08Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS08Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 批次處理 - 批次月核定合格清冊排程-批次程式
 * 
 * @author ZeHua
 */
public class MonthlyRpt08Job implements Job {
    private UserBean userData;
    private static Log log = LogFactory.getLog(MonthlyRpt08Action.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobId = context.getJobDetail().getName();
        RptService rptService = (RptService) SpringHelper.getBeanById("rptService");

        Babatchjob babatchjob = null;
        try {
            log.info("月核定合格清冊排程作業啟動" + jobId + "...");
            // 更新批次佇列的狀態;從此開始屬於Runing狀態
            rptService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_RUNNING, null);
            // 將目前待處裡的佇列資料讀出來
            babatchjob = rptService.getBaBatchJobData(jobId);
            // 查無目前需處理資料
            if (!StringUtils.equals(babatchjob.getStatus(), "R")) {
                // 該筆排程處裡失敗
                rptService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_ERROR, babatchjob.getProcType());
            }
            else {
                String payCode = babatchjob.getPayCode();
                String chkDate = DateUtility.changeDateType(babatchjob.getChkDate());
                String issuYm = DateUtility.changeWestYearMonthType(babatchjob.getIssuYm());
                String fileNpWithLip = "";
                if (StringUtils.equals(babatchjob.getPaySeqNo(), "2")) {
                    fileNpWithLip = "Y";
                }
                else {
                    fileNpWithLip = "N";
                }
                String paySeqNo = babatchjob.getPaySeqNo();
                // for Log
                // [
                /*
                 * String procuser = userData.getUserId(); String proctime = DateUtility.getNowWestDateTime(true);
                 */
                // ]
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("payCode", payCode);
                map.put("issuYm", issuYm);
                map.put("chkDate", chkDate);
                map.put("npWithLip", fileNpWithLip);

                // for Log
                // [
                int pagePerFile = rptService.getMonthlyRpt08Pages(); // 每個檔案的頁數

                String fileName = StringUtils.trimToEmpty(payCode) + "_" + StringUtils.trimToEmpty(DateUtility.changeChineseYearMonthType(issuYm)) + "_" + StringUtils.trimToEmpty(DateUtility.changeDateType(chkDate)) + "_" + fileNpWithLip
                                + "_MonthlyRpt08_";
                map.put("baseFileName", fileName);
                map.put("pagePerFile", String.valueOf(pagePerFile));
                /*
                 * map.put("logRptName", "BALP0D380L"); map.put("logQryCondition", "PAYCODE=" + StringUtils.trimToEmpty(payCode) + ",ISSUYM=" + StringUtils.trimToEmpty(DateUtility.changeChineseYearMonthType(issuYm)) + ",CHKDATE=" +
                 * StringUtils.trimToEmpty(DateUtility.changeDateType(chkDate)) + ",NPWITHLIP=" + npWithLip); // private String apNo; // 報表中塞 map.put("logIssuYm", StringUtils.trimToEmpty(DateUtility.changeChineseYearMonthType(issuYm))); // private
                 * String payYm; // 報表中塞 map.put("logChkdate", StringUtils.trimToEmpty(DateUtility.changeDateType(chkDate))); // private BigDecimal page; // 報表中塞 // private String filename; // 報表中塞 map.put("logProcuser", procuser);
                 * map.put("logProctime", proctime);
                 */
                // ]
                List caseData = null;
                List caseData1 = null; // 20120406
                caseData = new ArrayList<MonthlyRpt08Case>();
                caseData1 = new ArrayList<MonthlyRpt08Case>(); // 20120406

                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                    // 抓報表資料
                    // List<MonthlyRpt08Case> caseData = rptService.getMonthlyRpt08DataBy(payCode,DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                    caseData = rptService.getMonthlyRpt08DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate));
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                    caseData = rptService.getMonthlyRptK08DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "IS NULL", "=");
                    caseData1 = rptService.getMonthlyRptK08DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), paySeqNo, "IS NOT NULL", "<>");
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                    caseData = rptService.getMonthlyRptS08DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), "IS NULL", "=");
                    caseData1 = rptService.getMonthlyRptS08DataBy(payCode, DateUtility.changeChineseYearMonthType(issuYm), DateUtility.changeDateType(chkDate), "IS NOT NULL", "<>");
                }
                ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                if (caseData.size() > 0 || caseData1.size() > 0) {
                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(payCode)) {// 老年
                        MonthlyRpt08Report report = new MonthlyRpt08Report();
                        baoOutput = report.doReport(userData, caseData, map);
                    }
                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(payCode)) {// 失能
                        MonthlyRptK08Report report = new MonthlyRptK08Report();
                        baoOutput = report.doReport(userData, caseData, caseData1, map);
                    }
                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(payCode)) {// 遺屬
                        MonthlyRptS08Report report = new MonthlyRptS08Report();
                        baoOutput = report.doReport(userData, caseData, caseData1, map);
                    }
                    // PDF 拆檔
                    // [
                    PdfReader reader = new PdfReader(baoOutput.toByteArray());
                    int n = reader.getNumberOfPages();
                    log.info("Number of pages : " + n);

                    int fileSeq = 1;
                    String outFile = null;
                    Document document = null;
                    PdfCopy writer = null;
                    FileOutputStream fos = null;
                    try {
                        for (int i = 1; i <= n; i++) {
                            outFile = PropertyHelper.getProperty("rpt.path") + fileName + String.format("%03d", fileSeq) + ".pdf";

                            if (i == 1) {
                                document = new Document(reader.getPageSizeWithRotation(1));
                                fos = new FileOutputStream(Encode.forJava(outFile));
                                writer = new PdfCopy(document, fos);
                            }

                            if (document != null && writer != null) {
                                document.open();

                                PdfImportedPage page = writer.getImportedPage(reader, i);
                                writer.addPage(page);

                                if (i == n || (i % pagePerFile == 0)) {
                                    fileSeq++;
                                    document.close();
                                    writer.close();
                                    if (i != n) {
                                        ExceptionUtil.safeColse(fos);
                                        outFile = PropertyHelper.getProperty("rpt.path") + fileName + String.format("%03d", fileSeq) + ".pdf";
                                        document = new Document(reader.getPageSizeWithRotation(1));
                                        fos = new FileOutputStream(Encode.forJava(outFile));
                                        writer = new PdfCopy(document, fos);
                                    }
                                }
                            }
                        }
                    }
                    finally {
                        ExceptionUtil.safeColse(fos);
                        ExceptionUtil.safeColse(baoOutput);
                    }
                    // 塞 Log
                    /*
                     * if (caseData != null && caseData.size() > 0) rptService.insertBarptlogData(caseData); if (caseData1 != null && caseData1.size() > 0) rptService.insertBarptlogData(caseData1);
                     */

                    log.info("列印作業 - 月核定處理相關報表 - 月核定合格清冊 - PDF 拆檔完成...");
                    // ]
                    /*
                     * String printDate = DateUtility.getNowChineseDate(); // 設定回傳回 Client 端之檔案大小, 若不設定, // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面 // response.setHeader("Content-disposition", "attachment; filename=\"MonthlyRpt08_" + printDate + ".pdf" + "\"");
                     * response.setHeader("Content-disposition", "attachment; filename=\"" + payCode + "_MonthlyRpt08_" + printDate + ".pdf" + "\""); response.setHeader("Content-Encoding", "pdf"); response.setContentType("application/pdf"); // 設定MIME
                     * response.setHeader("Expires", "0"); response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0"); response.setHeader("Pragma", "public"); response.setContentLength(baoOutput.size()); // 傳回 Client 端
                     * ServletOutputStream sout = null; try { sout = response.getOutputStream(); baoOutput.writeTo(sout); sout.flush(); } catch (Exception e) { } finally { if (baoOutput != null) baoOutput.close(); if (sout != null) sout.close(); }
                     */
                }
                rptService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_END, babatchjob.getProcType());

            }
            log.info("月核定合格清冊排程作業結束" + jobId + "...");
        }
        catch (

        Exception e) {
            rptService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_ERROR, babatchjob.getProcType());
            log.error(ExceptionUtility.getStackTrace(e));
        }
    }
}
