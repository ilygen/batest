package tw.gov.bli.ba.framework.struts.plugin;

import org.apache.struts.action.PlugIn;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import javax.servlet.ServletException;

public class ConvertPlugIn implements PlugIn {
    public void destroy() {
        ConvertUtils.deregister();
    }

    public void init(ActionServlet arg0, ModuleConfig arg1) throws ServletException {
        ConvertUtils.register(new BigDecimalConverter(), java.math.BigDecimal.class);
    }
}
