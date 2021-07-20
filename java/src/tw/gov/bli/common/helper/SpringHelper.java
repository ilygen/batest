package tw.gov.bli.common.helper;

import javax.servlet.ServletContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 用於無法使用 Spring IOC 注入 Bean Instance 時, 取得定義於 Spring Config 檔中的 Bean Instance
 * 
 * @author Goston
 */
public class SpringHelper {
    private static Log log = LogFactory.getLog(SpringHelper.class);

    /**
     * ServletContext 將由 <code>tw.gov.bli.common.listener.SystemInitialListener</code> 進行初始化
     */
    private static ServletContext ctx = null;

    /**
     * 取得定義於 Spring Config 檔中的 Bean Instance
     * 
     * @param id 於 Spring Config 定義的 Bean ID
     * @return 取得的 Bean Instance
     */
    public static Object getBeanById(String id) {
        return WebApplicationContextUtils.getWebApplicationContext(ctx).getBean(id);
    }

    /**
     * 設定 ServletContext <br>
     * 將由 <code>tw.gov.bli.common.listener.SystemInitialListener</code> 進行初始化
     * 
     * @param ctx ServletContext
     */
    public static void setCtx(ServletContext ctx) {
        if (SpringHelper.ctx == null)
            SpringHelper.ctx = ctx;
    }

}
