package tw.gov.bli.common.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.common.ConstantKey;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.SpringHelper;
import tw.gov.bli.common.services.SysfuncService;

/**
 * Framework 環境初始化<br>
 * <br>
 * 欲使用 Framework 提供的各項 Logging 及權限控管功能, 必須於 <code>web.xml</code> 定義此 Listener:<br>
 * <code><pre>
 *  &lt;!-- Define System ID for Framework --&gt;
 *  &lt;context-param&gt;
 *      &lt;param-name&gt;systemId&lt;/param-name&gt;
 *      &lt;param-value&gt;BA&lt;/param-value&gt;
 *  &lt;/context-param&gt;
 * 
 *  &lt;!-- Define System Name for Framework --&gt;
 *  &lt;context-param&gt;
 *      &lt;param-name&gt;systemName&lt;/param-name&gt;
 *      &lt;param-value&gt;勞保年金給付系統&lt;/param-value&gt;
 *  &lt;/context-param&gt;
 * 
 *  &lt;!-- Initial Framework Environment --&gt;
 *  &lt;listener&gt;
 *      &lt;listener-class&gt;tw.gov.bli.common.listener.FrameworkInitialListener&lt;/listener-class&gt;
 *  &lt;/listener&gt;
 * </pre></code>
 * 
 * @author Goston
 */
public class FrameworkInitialListener implements ServletContextListener {
    private static Log log = LogFactory.getLog(FrameworkInitialListener.class);

    public void contextInitialized(ServletContextEvent event) {
        log.info("===== Framework 環境 初始化開始 =====");

        ServletContext ctx = event.getServletContext();

        // 設定 應用系統代號
        EnvFacadeHelper.setSystemId(ctx.getInitParameter(ConstantKey.SYSTEM_ID));
        log.info("設定 應用系統代號 完成");

        // 設定 應用系統名稱
        EnvFacadeHelper.setSystemName(ctx.getInitParameter(ConstantKey.SYSTEM_NAME));
        log.info("設定 應用系統名稱 完成");
        
        // 設定 是否啟用 CAS
        if (StringUtils.equalsIgnoreCase(ctx.getInitParameter(ConstantKey.ENABLE_CAS), "true"))
            EnvFacadeHelper.setEnableCAS(true);
        else
            EnvFacadeHelper.setEnableCAS(false);
        
        log.info("設定 是否啟用 CAS 完成");

        // 設定 SpringHelper 中的 ServletContext
        SpringHelper.setCtx(ctx);
        log.info("設定 SpringHelper 完成");

        // 設定 系統功能清單
        // 注意: 須在 應用系統代號 及 SpringHelper 設定後執行
        SysfuncService sysfuncService = (SysfuncService) SpringHelper.getBeanById(ConstantKey.SYSTEM_FUNCTION_SERVICE_ID);
        if (EnvFacadeHelper.isEnableCAS())
            EnvFacadeHelper.setSystemFunctionMap(sysfuncService.selectCasSysFuncBySystemId(EnvFacadeHelper.getSystemId()));
        else
            EnvFacadeHelper.setSystemFunctionMap(sysfuncService.selectSysFuncBySystemId(EnvFacadeHelper.getSystemId()));
        log.info("設定 系統功能清單 完成");

        log.info("===== Framework 環境 初始化完成! =====");
    }

    public void contextDestroyed(ServletContextEvent event) {

    }
}
