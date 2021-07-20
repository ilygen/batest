package tw.gov.bli.common.listener;

import java.sql.Timestamp;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.common.ConstantKey;
import tw.gov.bli.common.domain.PortalLog;
import tw.gov.bli.common.domain.UserInfo;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.common.services.LoggingService;
import tw.gov.bli.common.util.DateUtil;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * Framework HttpSession Listener For Logout Log
 * 
 * @author Goston
 */
public class FrameworkHttpSessionListener implements HttpSessionListener {
    private static Log log = LogFactory.getLog(FrameworkHttpSessionListener.class);

    public void sessionCreated(HttpSessionEvent se) {
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        UserInfo userData = (UserInfo) se.getSession().getAttribute(ConstantKey.USER_INFO);
        if (userData != null) {
            // Portal Log - Logout Log
            // [
            if (EnvFacadeHelper.isLoggingEnabled()) {
                try {
                    LoggingService loggingService = (LoggingService) SpringHelper.getBeanById(ConstantKey.LOGGING_SERVICE_ID);

                    Timestamp date = DateUtil.getNowDateTimeAsTimestamp();
                    String now = DateUtil.parseTimestampToWestDateTime(date, true);

                    PortalLog logData = new PortalLog();
                    logData.setLogDateTime(now); // 紀錄時間
                    logData.setUserId(userData.getUserId()); // 用戶代號
                    logData.setUserIP(userData.getLoginIP()); // 用戶 IP 位址
                    logData.setUserAction("Logout"); // 用戶執行動作
                    logData.setApCode(EnvFacadeHelper.getSystemId()); // 應用系統代號
                    logData.setApName(EnvFacadeHelper.getSystemName()); // 應用系統名稱
                    logData.setApFunctionCode(""); // 應用系統功能代號
                    logData.setApFunctionName(""); // 應用系統功能名稱
                    logData.setApUrl(""); // 應用系統網址
                    logData.setLogDescript("使用者登出系統"); // 說明
                    logData.setDateTime(date); // 系統時間
                    logData.setToken(userData.getToken()); // 檢查資訊

                    loggingService.loggingPortalLog(logData);
                }
                catch (Exception e) {
                    log.error("Portal Log - Logout Log 寫入錯誤, 原因:" + ExceptionUtil.getStackTrace(e));
                }
            }
            // ]
        }
    }

}
