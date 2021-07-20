package tw.gov.bli.ba.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 處理 Exception 的公用程式
 * 
 * @author Goston
 */
public class ExceptionUtility {
    public static String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
