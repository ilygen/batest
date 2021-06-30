package tw.gov.bli.ba.bj.helper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BatchHelper {
    private static Log log = LogFactory.getLog(BatchHelper.class);

    private static final String JOB_KEY = "baBatchJob";
    private static final String JOB_VALUE = "execute";
    
    /**
     * 檢核排程是否執行，須先設定 Java Option<br>
     * ex: <code>-DbaBatchJob=execute</code>
     * 
     * @return
     */
    public static boolean needExecuteBatch() {
        String value = System.getProperty(JOB_KEY);

        if (StringUtils.equalsIgnoreCase(value, JOB_VALUE))
            return true;
        else
            return false;
    }
}
