package tw.gov.bli.ba.bj.job;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import com.lowagie.text.pdf.PdfReader;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.actions.MonthlyRpt10Action;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type1PayAmtCase;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt10Type1RptCase;
import tw.gov.bli.ba.rpt.report.MonthlyRpt10Type2Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptK10Type2Report;
import tw.gov.bli.ba.rpt.report.MonthlyRptS10Type2Report;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * 批次處理 - 批次勞保年金核付明細表排程-批次程式
 * 
 * @author ZeHua
 */
public class MonthlyRpt10Job implements Job {
    private UserBean userData;
    private static Log log = LogFactory.getLog(MonthlyRpt10Action.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobId = context.getJobDetail().getName();
        RptService rptService = (RptService) SpringHelper.getBeanById("rptService");

        Babatchjob babatchjob = null;
        try {

            log.info("勞保年金核付明細表排程啟動" + jobId + "...");
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
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("payCode", payCode);
                map.put("chkDate", chkDate);
                List caseList = null;
                HashMap payAmtMap = null;
                List subCaseList = null;
                caseList = new ArrayList<MonthlyRpt10Type1RptCase>();
                payAmtMap = new HashMap<String, MonthlyRpt10Type1PayAmtCase>();
                subCaseList = new ArrayList<MonthlyRpt10Type1RptCase>();
                if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(babatchjob.getPayCode())) {// 老年
                    // 抓報表資料
                    caseList = rptService.getMonthlyRpt10RptType2DataBy(babatchjob.getPayCode(), babatchjob.getIssuYm(), chkDate, new String[] { "F", "N", "Z" }, "NOT IN", new String[] { "A.APNO", "A.SEQNO", "A.ISSUYM", "A.PAYYM" });
                    // 代扣補償金
                    subCaseList = rptService.getMonthlyRpt10RptType2DataBy(babatchjob.getPayCode(), babatchjob.getIssuYm(), chkDate, new String[] { "Z" }, "IN",
                                    new String[] { "A.BENEVTREL", "A.COMPNAME1", "A.PAYBANKID", "A.BRANCHID", "A.PAYEEACC" });
                    payAmtMap = rptService.getMonthlyRpt10RptType2PayAmtDataBy(babatchjob.getPayCode(), babatchjob.getIssuYm(), chkDate);
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(babatchjob.getPayCode())) {// 失能
                    if (StringUtils.equals(babatchjob.getPaySeqNo(), "2")) {
                        map.put("paySeqNo", "2");
                    }
                    else {
                        map.put("paySeqNo", "1");
                    }
                    caseList = rptService.getMonthlyRptK10RptType2DataBy(babatchjob.getPayCode(), babatchjob.getIssuYm(), chkDate, babatchjob.getPaySeqNo());
                }
                else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(babatchjob.getPayCode())) {// 遺屬
                    caseList = rptService.getMonthlyRptS10RptType2DataBy(babatchjob.getPayCode(), babatchjob.getIssuYm(), chkDate);
                }
                String fileName = "";
                if (caseList.size() > 0) {
                    ByteArrayOutputStream baoOutput = new ByteArrayOutputStream();
                    log.info("報表產製開始......");
                    if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_L).equals(babatchjob.getPayCode())) {// 老年
                        MonthlyRpt10Type2Report report = new MonthlyRpt10Type2Report();
                        baoOutput = report.doReport(userData, caseList, payAmtMap, subCaseList, map);
                    }
                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_K).equals(babatchjob.getPayCode())) {// 失能
                        MonthlyRptK10Type2Report report = new MonthlyRptK10Type2Report();
                        baoOutput = report.doReport(userData, caseList, map);
                    }
                    else if ((ConstantKey.BAAPPBASE_PAGE_PAYKIND_S).equals(babatchjob.getPayCode())) {// 遺屬
                        MonthlyRptS10Type2Report report = new MonthlyRptS10Type2Report();
                        baoOutput = report.doReport(userData, caseList, map);
                    }
                    log.info("報表產製完成......");
                    PdfReader reader = new PdfReader(baoOutput.toByteArray());

                    String outFile = null;
                    Document document = null;
                    PdfCopy writer = null;
                    String printDate = DateUtility.getNowChineseDate();

                    
                    if (StringUtils.equals(babatchjob.getPaySeqNo(), "2")) {
                        fileName = babatchjob.getPayCode() + "_" + chkDate + "_MonthlyRpt10Type2_36_" + printDate;
                    }
                    else {
                        fileName = babatchjob.getPayCode() + "_" + chkDate + "_MonthlyRpt10Type2_" + printDate;
                    }
                    log.info("報表寫入開始......");
                    outFile = PropertyHelper.getProperty("rpt.path") + fileName + ".pdf";
                    int n = reader.getNumberOfPages();
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(new File(Encode.forJava(outFile)));
                        baoOutput.writeTo(fos);
                    }
                    finally {
                        ExceptionUtil.safeColse(fos);
                    }
                    log.info("報表寫入完成......file Name: "+ fileName);
                    /*
                     * for (int i = 1; i <= n; i++) { if (i == 1) { document = new Document(reader.getPageSizeWithRotation(1)); writer = new PdfCopy(document, new FileOutputStream(outFile)); } document.open(); PdfImportedPage page =
                     * writer.getImportedPage(reader, i); writer.addPage(page); if (i == n) { document.close(); writer.close(); } if (baoOutput != null) baoOutput.close(); }
                     */

                }
                rptService.updateBaBatchJobStatusAfter(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_END, babatchjob.getProcType(), fileName);
            }
            log.info("勞保年金核付明細表排程結束" + jobId + "...");
        }
        catch (Exception e) {
            rptService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_ERROR, babatchjob.getProcType());
            log.error(ExceptionUtility.getStackTrace(e));
        }
    }

}
