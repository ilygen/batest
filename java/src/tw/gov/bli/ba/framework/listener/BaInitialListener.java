package tw.gov.bli.ba.framework.listener;

import java.util.Properties;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.ba.framework.helper.EnvHelper;
import tw.gov.bli.ba.helper.PropertyHelper;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.SpringHelper;
import com.iisi.SecureToken;

public class BaInitialListener implements ServletContextListener {
    private static Log log = LogFactory.getLog(BaInitialListener.class);

    public void contextInitialized(ServletContextEvent event) {
        ServletContext ctx = event.getServletContext();

        // 設定 DB 參數檔 Helper
        Properties properties = (Properties) SpringHelper.getBeanById("properties");
        PropertyHelper.initailProperties(properties);

        // ResourceBundle resourceBondule = ResourceBundle.getBundle("resource");

        String basePath = PropertyHelper.getProperty("font.path");
        String sungFilename = PropertyHelper.getProperty("font.Sung.filename");
        String kaiFilename = PropertyHelper.getProperty("font.Kai.filename");

        String resourcePath = PropertyHelper.getProperty("resource.path");
        // 設定路徑
        EnvHelper.setSungPath(basePath + sungFilename);
        EnvHelper.setKaiPath(basePath + kaiFilename);

        EnvHelper.setResourcePath(resourcePath);

        // 設定呼叫 web service 時的 SecureToken
        try {
            InitialContext env = new InitialContext();
            DataSource ds = (DataSource) env.lookup("java:comp/env/jdbc/baDS");
            SecureToken.initail(ds, "BA");
        }
        catch (Exception e) {
            log.error("SecureToken initail fail.....");
            log.error(ExceptionUtility.getStackTrace(e));
        }

    }

    public void contextDestroyed(ServletContextEvent event) {

    }

}
