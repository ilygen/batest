package tw.gov.bli.common.tag.acl;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * TagLib - 取得螢幕編號
 * 
 * @author Goston
 */
public class GetProgIdTag extends TagSupport {
    private static Log log = LogFactory.getLog(GetProgIdTag.class);

    public int doStartTag() {
        try {
            String progId = StringUtils.defaultString((String) pageContext.getAttribute("_page_progId"));
            JspWriter out = pageContext.getOut();

            if (progId.equals("")) {
                log.error("Taglib 發生錯誤, Tag: GetProgIdTag 原因: progId 未定義");
                out.print("<font color='red'>Undefined progId</font>");
            }
            else {
                out.print(progId);
            }
        }
        catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: GetProgIdTag 原因: " + ExceptionUtil.getStackTrace(e));
        }
        return SKIP_BODY;
    }
}
