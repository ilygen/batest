package tw.gov.bli.ba.bj.job;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.owasp.encoder.Encode;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.ba.domain.Babatchjobdtl;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt05Case;
import tw.gov.bli.ba.rpt.report.MonthlyRpt05Report;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.services.RptService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.common.util.ExceptionUtil;

public class OtherRpt05Job implements Job {
	
	private UserBean userData;
    private static Log log = LogFactory.getLog(OtherRpt05Job.class);
   
	
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	String jobId = context.getJobDetail().getName();
        RptService rptService = (RptService) SpringHelper.getBeanById("rptService");
        BjService bjService = (BjService) SpringHelper.getBeanById("bjService");
        String strinfo = "";
        int PageNO = 1;
        Babatchjob babatchjob = null;
        
        OutputStream outputStream = null;
        ByteArrayOutputStream baoOutput = null;
        try {
        	log.info("批次核定函排程作業啟動" + jobId + "...");
            // 更新批次佇列的狀態;從此開始屬於Runing狀態
            rptService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_RUNNING, null);
            // 將目前待處裡的佇列資料讀出來
            babatchjob = rptService.getBaBatchJobData(jobId);
            // 查無目前需處理資料
            if (!StringUtils.equals(babatchjob.getStatus(), "R")) {
                // 該筆排程處裡失敗
                rptService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_ERROR, babatchjob.getProcType());
            } else {
        	String payCode = babatchjob.getPayCode();
        	String issuYm = DateUtility.changeWestYearMonthType(babatchjob.getIssuYm());
        	String fileName = babatchjob.getFileName();
            String rptKind = fileName.substring(20, 21);
            
            if(userData == null){
            	userData= new UserBean();
            	userData.setEmpNo(babatchjob.getProcEmpNo());
            }
            
            int sizePerFile = rptService.getOtherRpt05Size(); // 幾筆apNo產一個檔

            List<String> apNoList = new ArrayList<String>();

            // 抓報表資料 apNo
            if (payCode.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_L)) { // 老年
                apNoList = rptService.selectForPrintNotify(payCode, DateUtility.changeChineseYearMonthType(issuYm), rptKind);
            }
            else if (payCode.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S)) { // 遺屬
                apNoList = rptService.selectSurvivorForPrintNotify(payCode, DateUtility.changeChineseYearMonthType(issuYm));
            }
            else { // 失能
                apNoList = rptService.selectDisabledForPrintNotify(payCode, DateUtility.changeChineseYearMonthType(issuYm));
            }

//            apNoList.add("L00000100011");
            
            // 所有apNo資料集合 n個檔
            List<List<MonthlyRpt05Case>> dataList = new ArrayList<List<MonthlyRpt05Case>>();

            if(apNoList != null && apNoList.size() > 0) {
                // 只有單檔 單檔列印
                if (apNoList.size() < sizePerFile + 1) {
                    // 一個pdf檔的資料
                    List<MonthlyRpt05Case> caseList = new ArrayList<MonthlyRpt05Case>();

                    for (String apNo : apNoList) {
                    	try {
                        	List<MonthlyRpt05Case> singleCaseList = new ArrayList<MonthlyRpt05Case>();

                        	if (payCode.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S)) {
                                singleCaseList = rptService.getMonthlyRpt05SurvivorDataBy(userData, apNo, DateUtility.changeChineseYearMonthType(issuYm));
                            }
                            else {
                                singleCaseList = rptService.getMonthlyRpt05DataBy(apNo, DateUtility.changeChineseYearMonthType(issuYm));
                            }
                        
                        // 單筆受編號案件資料
                        for (MonthlyRpt05Case caseData : singleCaseList) {
                            caseData.setPayCode(payCode);
                            caseList.add(caseData);
                        }
                     
                    	}catch (Exception e) {
                        	//log 產檔失敗資訊與筆數
                    		strinfo = "受理編號:" + apNo + "，讀取核定函資料失敗-" + e;
                    		log.error("受理編號:" + apNo + "，讀取核定函資料失敗-" );
                    		log.error(ExceptionUtility.getStackTrace(e));
                    		if (strinfo.length() > 150)
                    			strinfo = strinfo.substring(0, 150);
                			savebabatchjobdtl(jobId, "1", strinfo, bjService);
                        }
                    }

                    dataList.add(caseList);
                 
                }
                else {    // 多檔

                    // 檔案數量
                    int files = 0;

                    if (apNoList.size() % sizePerFile == 0) {
                        files = apNoList.size() / sizePerFile;
                    }
                    else {
                        files = apNoList.size() / sizePerFile + 1;
                    }

                    // apNo計數
                    int apNoNum = 0;

                    for (int i = 0; i < files; i++) {

                        // 一個pdf檔的資料
                        List<MonthlyRpt05Case> caseList = new ArrayList<MonthlyRpt05Case>();

                        for (int n = 0; n < sizePerFile; n++) {

                            List<MonthlyRpt05Case> singleCaseList = new ArrayList<MonthlyRpt05Case>();

                            if (apNoNum + 1 <= apNoList.size()) {

                            	try {
                            		if (payCode.equals(ConstantKey.BAAPPBASE_PAGE_PAYKIND_S)) {
                                        singleCaseList = rptService.getMonthlyRpt05SurvivorDataBy(userData, apNoList.get(apNoNum), DateUtility.changeChineseYearMonthType(issuYm));
                                    }
                                    else {
                                        singleCaseList = rptService.getMonthlyRpt05DataBy(apNoList.get(apNoNum), DateUtility.changeChineseYearMonthType(issuYm));
                                    }
                            	

                            		// 單筆受編號案件資料
                            		for (MonthlyRpt05Case caseData : singleCaseList) {
                            			caseData.setPayCode(payCode);
                            			caseList.add(caseData);
                            		}
                            	}catch (Exception e) {
                            		//log 產檔失敗資訊與筆數
                            		strinfo = "受理編號:" + apNoList.get(apNoNum) + "，讀取核定函資料失敗-" + e;

                            		log.error("受理編號:" +  apNoList.get(apNoNum) + "，讀取核定函資料失敗-" );
                            		log.error(ExceptionUtility.getStackTrace(e));
                            		if (strinfo.length() > 150)
                            			strinfo = strinfo.substring(0, 150);
                        			savebabatchjobdtl(jobId, "1", strinfo, bjService);
                            	}

                                apNoNum++;
                            }

                        }
                        dataList.add(caseList);
                    }
                }

                for (int i = 0; i < dataList.size(); i++) {

                    baoOutput = new ByteArrayOutputStream();

                    MonthlyRpt05Report report = new MonthlyRpt05Report();
                    baoOutput = report.doReport(userData, dataList.get(i), payCode, jobId, bjService, DateUtility.changeChineseYearMonthType(issuYm), PageNO);

                    PageNO = report.PageNo;
                    
                    // 寫入local檔案
                    File file = new File(PropertyHelper.getProperty("rpt.path") + Encode.forJava(fileName) + "_" +StringUtils.leftPad(String.valueOf((i + 1)), 3, "0")  + ".pdf");
                    outputStream = new FileOutputStream(file);
                    baoOutput.writeTo(outputStream);
                    ExceptionUtil.safeColse(outputStream);
                }
            }
            rptService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_END, babatchjob.getProcType());
            log.info("批次核定函排程作業結束" + jobId + "...");
         }
        }catch (Exception e) {
        	rptService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_ERROR, babatchjob.getProcType());
            log.error(ExceptionUtility.getStackTrace(e));
        }finally {
			ExceptionUtil.safeColse(outputStream);
			ExceptionUtil.safeColse(baoOutput);
		}
    }
    
  //將執行結果訊息存入[線上批次啟動作業紀錄明細檔 BABATCHJOBDTL]
  public void savebabatchjobdtl(String jobDataid, String flag, String strinfo, BjService bjService){
  		try {
  			Babatchjobdtl babatchjobdtl = new Babatchjobdtl();
  			babatchjobdtl.setBaJobId(jobDataid);
  			babatchjobdtl.setFlag(flag);
  			babatchjobdtl.setFlagMsg(strinfo);
  			babatchjobdtl.setFlagTime(DateUtility.getNowWestDateTime(true));
  			bjService.inserBabatchjobdtlData(babatchjobdtl);
  		}
  		catch(Exception exp) {
  			log.error("新增babatchjobdtl失敗-" + exp);
  		}
  	}
}
        
