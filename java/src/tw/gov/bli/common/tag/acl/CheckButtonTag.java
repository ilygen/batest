package tw.gov.bli.common.tag.acl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.common.domain.UserInfo;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.ExceptionUtil;

/**
 * TagLib - Button 權限控管
 * 
 * @author Goston
 */
public class CheckButtonTag extends TagSupport {
    private static Log log = LogFactory.getLog(CheckButtonTag.class);

    private String buttonName;

    public int doStartTag() {
        try {
            String progId = StringUtils.defaultString((String) pageContext.getAttribute("_page_progId"));
            JspWriter out = pageContext.getOut();

            if (!EnvFacadeHelper.isButtonControlEnabled()) { // 不啟動 Button 的權限管控
                out.print("<span>");
                return EVAL_BODY_INCLUDE;
            }

            if (progId.equals("") || StringUtils.defaultString(buttonName).trim().equals("")) {
                log.error("Taglib 發生錯誤, Tag: CheckButtonTag 原因: progId 或 buttonName 未定義");
                out.print("<span>");
                out.print("<font color='red'>Undefined progId or buttonName</font>");
                return SKIP_BODY;
            }
            else {
                UserInfo userData = UserSessionHelper.getUserData((HttpServletRequest) pageContext.getRequest());
                if (!EnvFacadeHelper.isUndefineUrlControl() || (userData != null && userData.isValidUserButton(progId, buttonName))) {
                    // 不控管未定義之 Url 或 該使用者有權存取 Button
                    out.print("<span>");
                    return EVAL_BODY_INCLUDE;
                }
                else {
                    if (EnvFacadeHelper.isButtonHide()) {
                        out.print("<span>");
                        return SKIP_BODY;
                    }
                    else {
                        out.print("<span disabled onmouseover='for(i = 0; i < this.childNodes.length; i++){var obj=this.childNodes[i]; if(obj.type == \"button\" || obj.type == \"submit\" || obj.type == \"reset\") obj.disabled=true;}'>");
                        return EVAL_BODY_INCLUDE;
                    }
                }
            }

        }
        catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: CheckButtonTag 原因: " + ExceptionUtil.getStackTrace(e));
            return SKIP_BODY;
        }
    }

    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("</span>");
            return EVAL_PAGE;
        }
        catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: CheckButtonTag 原因: " + ExceptionUtil.getStackTrace(e));
            return EVAL_PAGE;
        }
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
}
