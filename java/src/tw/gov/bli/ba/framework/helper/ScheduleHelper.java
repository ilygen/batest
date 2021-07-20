package tw.gov.bli.ba.framework.helper;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.domain.UserInfo;
import tw.gov.bli.common.helper.SpringHelper;

public class ScheduleHelper {
	/**
     * 立即排程
     * 
     * @param jobClass 排程的 Job （ex: Job1.class）
     * @param dataMap JobDataMap （沒有則傳入 null）
     * @param userData 使用者物件
     * @return 排程成功回傳 Job ID（此 ID 需紀錄在排程紀錄檔），失敗則回傳 null
     */
    public static String scheduleNow(Class<? extends Job> jobClass, Map<String, String> dataMap, UserInfo userData) {
    	try {
            String now = DateUtility.getNowWestDateTimeMs();
            String jobId = StringUtils.defaultString(userData.getUserId()) + now + "J";
            String triggerId = StringUtils.defaultString(userData.getUserId()) + now + "T";
            String groupId = jobClass.getSimpleName();

            long startTime = System.currentTimeMillis() + 10000L;

            JobDetail jobDetail = new JobDetail(jobId, groupId, jobClass);

            if (dataMap != null) {
                JobDataMap jobDataMap = jobDetail.getJobDataMap();

                for (String key : dataMap.keySet()) {
                    jobDataMap.put(key, dataMap.get(key));
                }
            }

            SimpleTrigger trigger = new SimpleTrigger(triggerId, groupId, new Date(startTime), null, 0, 0L);

            Scheduler quartzScheduler = (Scheduler) SpringHelper.getBeanById("quartzScheduler");

            quartzScheduler.scheduleJob(jobDetail, trigger);
            
            return jobId;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 立即排程
     * 
     * @param jobClass 排程的 Job （ex: Job1.class）
     * @param dataMap JobDataMap （沒有則傳入 null）
     * @param userData 使用者物件
     * @return 排程成功回傳 Job ID（此 ID 需紀錄在排程紀錄檔），失敗則回傳 null
     */
    public static String scheduleAtSettingTime(Class<? extends Job> jobClass, Map<String, String> dataMap, UserInfo userData, String sRunDateBegin) {
        try {
            String now = DateUtility.getNowWestDateTimeMs();
            String jobId = StringUtils.defaultString(userData.getUserId()) + now + "J";
            String triggerId = StringUtils.defaultString(userData.getUserId()) + now + "T";
            String groupId = jobClass.getSimpleName();

            /**
            long startTime;
            Date uDate;

            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                uDate = dateFormat.parse(sRunDateBegin);
           
                Calendar cal = Calendar.getInstance(Locale.TAIWAN);
                cal.setTime(uDate);
                startTime = cal.getTimeInMillis();
                
            } catch (Exception e) {
                return null;
            }*/
            
            long startTime = System.currentTimeMillis() + 10000L;
                        
                        
            JobDetail jobDetail = new JobDetail(jobId, groupId, jobClass);

            if (dataMap != null) {
                JobDataMap jobDataMap = jobDetail.getJobDataMap();

                for (String key : dataMap.keySet()) {
                    jobDataMap.put(key, dataMap.get(key));
                }
            }

            SimpleTrigger trigger = new SimpleTrigger(triggerId, groupId, new Date(startTime), null, 0, 0L);                  

            Scheduler quartzScheduler = (Scheduler) SpringHelper.getBeanById("quartzScheduler");

            quartzScheduler.scheduleJob(jobDetail, trigger);
            
            return jobId;
        }
        catch (Exception e) {
            return null;
        }
    }

    
    
}
