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
 * 年金統計執行作業
 * @author TseHua
 *
 */
public class ExecStatisticsJob implements Job {
	private static Log log = LogFactory.getLog(OldMediaOnlineAction.class);
	private static final String OUTFLAG_FAIL = "N";
	private static final String OUTFLAG_END = "E";
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String jobId = context.getJobDetail().getName();
		BjService bjService = (BjService) SpringHelper.getBeanById("bjService");
		Babatchjob babatchjob = null;
		try {
			log.info("年金統計執行作業排程啟動，"+jobId+"...");
			// 將目前待處裡的佇列資料讀出來
			bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_RUNNING, "12");
			babatchjob = bjService.getBaBatchJobData(jobId);
			//更新批次佇列的狀態;從此開始屬於Runing狀態
			
			//查無目前需處理資料
			if (!StringUtils.equals(babatchjob.getStatus(), "R")) {
				//該筆排程處理失敗
				bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true),  ConstantKey.STATUS_ERROR, babatchjob.getProcType());
			} else {
				// call storeprocedure
				String outFlag = bjService.callSpBansf(jobId, babatchjob.getIssuYm());
				if (StringUtils.equals(outFlag, OUTFLAG_FAIL)) {
					// 失敗
					bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_ERROR, babatchjob.getProcType());
				} else if (StringUtils.equals(outFlag, OUTFLAG_END)){
					// 成功 
					bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_END, babatchjob.getProcType());
				} else {
					// 未知結果
					bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), outFlag, babatchjob.getProcType());
				}
			}
			log.info("年金統計執行作業排程結束，"+jobId+"...");
		} catch (Exception e) {
			bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_ERROR, babatchjob.getProcType());
			log.error(ExceptionUtility.getStackTrace(e));
		}
		
	}

}
