package tw.gov.bli.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.util.FrameworkLogUtil;

/**
 * Framework Logging Filter<br>
 * <br>
 * 欲使用 Framework 之相關 Logging 機制, 須於 <code>web.xml</code> 定義此 Filter<br>
 * 若不定義, 則須自行實作 Logging 機制並呼叫各 Log Table 之 DAO 紀錄 Log<br>
 * <code><pre>
 *  &lt;filter&gt;
 *      &lt;filter-name&gt;LoggingFilter&lt;/filter-name&gt;
 *      &lt;filter-class&gt;tw.gov.bli.common.filter.LoggingFilter&lt;/filter-class&gt;
 *      &lt;init-param&gt;
 *          &lt;param-name&gt;undefineUrlLogging&lt;/param-name&gt;
 *          &lt;param-value&gt;true&lt;/param-value&gt;
 *      &lt;/init-param&gt;
 *  &lt;/filter&gt;
 * 
 *  &lt;filter-mapping&gt;
 *      &lt;filter-name&gt;LoggingFilter&lt;/filter-name&gt;
 *      &lt;url-pattern&gt;*.do&lt;/url-pattern&gt;
 *  &lt;/filter-mapping&gt;
 * 
 *  &lt;filter-mapping&gt;
 *      &lt;filter-name&gt;LoggingFilter&lt;/filter-name&gt;
 *      &lt;url-pattern&gt;*.jsp&lt;/url-pattern&gt;
 *  &lt;/filter-mapping&gt;
 * </pre></code> <code>&lt;param-name&gt;undefineUrlLogging&lt;/param-name&gt;</code> 用來設定是否紀錄未定義之 URL
 * 
 * @author Goston
 */
public class LoggingFilter implements Filter {
    private static Log log = LogFactory.getLog(LoggingFilter.class);

    private static final String UNDEFINE_URL_LOGGING = "undefineUrlLogging"; // 是否 Log 未定義之 URL 的操作 - <init-param> 之
                                                                             // <param-name>

    private boolean undefineUrlLogging = true; // 是否 Log 未定義之 URL 的操作

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 紀錄 Portal Log, 至於是否紀錄在 doPortalLog 會判斷
        FrameworkLogUtil.doPortalLog(req);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // 有啟用本 Filter 代表 Framework Logging 機制已啟用
        EnvFacadeHelper.setLoggingEnabled(true);

        // 是否 Log 未定義之 URL 的操作
        // [
        String sUndefineUrlLogging = filterConfig.getInitParameter(UNDEFINE_URL_LOGGING);
        if (StringUtils.equalsIgnoreCase(sUndefineUrlLogging, "false")) {
            undefineUrlLogging = false;
            EnvFacadeHelper.setUndefineUrlLogging(false);
        } else {
            undefineUrlLogging = true;
            EnvFacadeHelper.setUndefineUrlLogging(true);
        }
        // ]
    }

    public void destroy() {
    }

}
