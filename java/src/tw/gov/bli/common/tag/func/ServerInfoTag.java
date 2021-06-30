package tw.gov.bli.common.tag.func;

import java.lang.management.ManagementFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ServerInfoTag extends TagSupport {
    public int doStartTag() {
        try {
            JspWriter out = pageContext.getOut();
            out.print("PID: " + ManagementFactory.getRuntimeMXBean().getName());
        }
        catch (Exception e) {

        }

        return SKIP_BODY;
    }
}
