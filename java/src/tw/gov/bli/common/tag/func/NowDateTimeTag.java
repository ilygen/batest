package tw.gov.bli.common.tag.func;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.common.util.DateUtil;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * TagLib - 取得目前系統日期時間字串
 * 
 * @author Goston
 */
public class NowDateTimeTag extends TagSupport {
    private static Log log = LogFactory.getLog(NowDateTimeTag.class);

    private boolean chtType = true;

    public int doStartTag() {
        try {
            JspWriter out = pageContext.getOut();
            out.print(DateUtil.formatNowChineseDateTimeString(chtType));
        }
        catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: NowDateTimeTag 原因: " + ExceptionUtil.getStackTrace(e));
        }

        return SKIP_BODY;
    }

    public void setChtType(boolean chtType) {
        this.chtType = chtType;
    }

}
