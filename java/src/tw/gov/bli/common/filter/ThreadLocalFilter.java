package tw.gov.bli.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.common.helper.HttpHelper;

/**
 * Framework ThreadLocal Filter for <code>tw.gov.bli.common.helper.HttpHelper</code><br>
 * <br>
 * 欲使用本 Filter, 請於 <code>web.xml</code> 進行定義, 範例如下: <br>
 * <code><pre>
 *  &lt;filter&gt;
 *      &lt;filter-name&gt;FrameworkThreadLocalFilter&lt;/filter-name&gt;
 *      &lt;filter-class&gt;tw.gov.bli.common.filter.ThreadLocalFilter&lt;/filter-class&gt;
 *  &lt;/filter&gt;
 * 
 *  &lt;filter-mapping&gt;
 *      &lt;filter-name&gt;FrameworkThreadLocalFilter&lt;/filter-name&gt;
 *      &lt;url-pattern&gt;*.do&lt;/url-pattern&gt;
 *  &lt;/filter-mapping&gt;
 * </pre></code>
 * 
 * @author Goston
 */
public class ThreadLocalFilter implements Filter {
    private static Log log = LogFactory.getLog(ThreadLocalFilter.class);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("設定 HttpServletRequest ThreadLocal, 路徑: " + ((HttpServletRequest) request).getServletPath());
        HttpHelper.setHttpRequest((HttpServletRequest) request);

        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

}
