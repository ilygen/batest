package tw.gov.bli.ba.bj.job;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.RunprocedurejobDao;
import tw.gov.bli.ba.domain.Babatchjob;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.bj.cases.RunProcedureCase;
import tw.gov.bli.ba.services.BjService;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.SpringHelper;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
/**
 * 批次處理 - 批次程式執行作業
 * 
 * @author KIYOMI
 */
public class RunProcedureJob implements Job{
	private UserBean userData;
	private static Log log = LogFactory.getLog(RunProcedureJob.class);
		
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String jobId = context.getJobDetail().getName();

		BjService bjService = (BjService) SpringHelper.getBeanById("bjService");

		Babatchjob babatchjob = null;
		try {
			log.info("批次程式執行排程作業啟動" + jobId + "...");
			// 更新批次佇列的狀態;從此開始屬於Runing狀態			
			bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_RUNNING, "9");

			// 將目前待處裡的佇列資料讀出來
			babatchjob = bjService.getBaBatchJobData(jobId);
			
			// 查無目前需處理資料
			if (!StringUtils.equals(babatchjob.getStatus(), "R")) {
			    
				// 該筆排程處裡失敗
			    bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_ERROR, "9");

			} else {

                List caseData = new ArrayList<RunProcedureCase>();                                
                caseData = bjService.getRunProcedureDataBy(jobId); 
                

                Map<String,String> map = bjService.runProcedure(caseData);
                String sReturnResult = "";
                
                for (Object key : map.keySet()) {
                    sReturnResult = sReturnResult + key + "=" + map.get(key) + "　";
                }
                
                bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_END, "9");
                bjService.insertBaBatchJobDtl(jobId, DateUtility.getNowWestDateTime(true), sReturnResult);
	        }
			 	             
		} catch (Exception e) {
		    bjService.updateBaBatchJobStatus(jobId, DateUtility.getNowWestDateTime(true), ConstantKey.STATUS_ERROR, "9"); 
			log.error(ExceptionUtility.getStackTrace(e));
		}
	}
}
