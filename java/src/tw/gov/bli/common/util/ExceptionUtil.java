package tw.gov.bli.common.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 處理 Exception 的公用程式
 * 
 * @author Goston
 */
public class ExceptionUtil {
    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    /**
     * 安全關閉IO
     * 
     * @param io
     */
    public static void safeColse(Closeable io) {
        if (io != null) {
            try {
                io.close();
            }
            catch (IOException ignore) {
            }
        }

    }
}
