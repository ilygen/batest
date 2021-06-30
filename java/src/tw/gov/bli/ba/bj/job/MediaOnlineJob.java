package tw.gov.bli.ba.bj.job;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.actions.OldMediaOnlineAction;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.SpringHelper;
/**
 * 批次處理 - 老年年金線上批次產製媒體檔作業-批次程式
 * 
 * @author ZeHua
 */
public class MediaOnlineJob implements Job {
	private static Log log = LogFactory.getLog(OldMediaOnlineAction.class);
	//private static final String ISSUTYP = "1";
	private static final String OUTFLAG_FAIL = "1";
	private static final String GEN_PROC_TYPE ="5";
	//private static final String OUTFLAG_SUCCESS = "0";
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String jobId = context.getJobDetail().getName();
		BjService bjService = (BjService) SpringHelper.getBeanById("bjService");
		
		Babatchjob babatchjob = null;
		try {
			log.info("線上產製媒體檔排程啟動，"+jobId+"...");
			//更新批次佇列的狀態;從此開始屬於Runing狀態
			bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_RUNNING, null);
			// 將目前待處裡的佇列資料讀出來
			babatchjob = bjService.getBaBatchJobData(jobId);
			//查無目前需處理資料
			if (!StringUtils.equals(babatchjob.getStatus(), "R")) {
				//該筆排程處裡失敗
				bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true),  ConstantKey.STATUS_ERROR, babatchjob.getProcType());
			} else {
				String issuTyp = "";
				if(StringUtils.equals(babatchjob.getProcType(), "1")||StringUtils.equals(babatchjob.getProcType(), "5")){
					issuTyp = "1";
				}else if(StringUtils.equals(babatchjob.getProcType(), "10")){
					issuTyp = "2";
				}else if(StringUtils.equals(babatchjob.getProcType(), "11")){
					issuTyp = "3";
				}
				
				// call storeprocedure
				String outFlag = bjService.genBaPayFile(jobId, issuTyp, babatchjob.getIssuYm(), babatchjob.getChkDate(), babatchjob.getPayCode(), "F", babatchjob.getProcEmpNo(), babatchjob.getProcDeptId(), babatchjob.getProcIp(), babatchjob.getPaySeqNo(), GEN_PROC_TYPE);
				if (StringUtils.equals(outFlag, OUTFLAG_FAIL)) {
					// 失敗
					bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_ERROR, babatchjob.getProcType());
				} else {
					// 成功 
					bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_END, babatchjob.getProcType());
					//發送MAIL
					bjService.sendBatchMediaProduce(babatchjob.getPayCode(), babatchjob.getChkDate(), babatchjob.getIssuYm());
				}
			}
			log.info("線上產製媒體檔排程結束，"+jobId+"...");
		} catch (Exception e) {
			bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_ERROR, babatchjob.getProcType());
			log.error(ExceptionUtility.getStackTrace(e));
		}
	}

}
