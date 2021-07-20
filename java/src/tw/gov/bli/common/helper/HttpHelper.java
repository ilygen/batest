package tw.gov.bli.common.helper;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * HTTP Helper
 * 
 * @author Goston
 */
public class HttpHelper {
    private static Log log = LogFactory.getLog(HttpHelper.class);

    private static final ThreadLocal<HttpServletRequest> threadLocalHttpRequest = new ThreadLocal<HttpServletRequest>();

    /**
     * 設定當前的 HttpServletRequest 到 ThreadLocal 中
     * 
     * @param request 由 Filter 所傳入, 當前執行緒 (Thread) 的 HttpServletRequest 物件
     */
    public static void setHttpRequest(HttpServletRequest request) {
        threadLocalHttpRequest.set(request);
    }

    /**
     * 取得 ThreadLocal 中的 HttpServletRequest
     * 
     * @return 當前執行緒 (Thread) 的 HttpServletRequest 物件
     */
    public static HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) threadLocalHttpRequest.get();
    }

}
