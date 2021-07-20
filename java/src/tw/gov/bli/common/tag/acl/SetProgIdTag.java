package tw.gov.bli.common.tag.acl;

import javax.servlet.http.HttpServletRequest;
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
 * TagLib - 設定螢幕編號
 * 
 * @author Goston
 */
public class SetProgIdTag extends TagSupport {
    private static Log log = LogFactory.getLog(SetProgIdTag.class);

    private String progId;

    public int doStartTag() {
        try {
            if (!EnvFacadeHelper.isValidProgId(progId) && EnvFacadeHelper.isUndefineUrlControl()) {
                // 頁面程式代號 - 螢幕編號 未定義 且 控管未定義之 Url
                log.error("Taglib 發生錯誤, Tag: SetProgIdTag 原因: progId 錯誤...");
            }
            else {
                pageContext.setAttribute("_page_progId", StringUtils.upperCase(progId));

                // 更新使用者物件中目前執行的功能的 頁面程式代號 - 螢幕編號
                HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
                if (request != null) {
                    UserInfo userData = UserSessionHelper.getUserData(request);
                    if (userData != null) {
                        userData.setProgId(StringUtils.upperCase(progId));
                    }
                }
            }
        }
        catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: SetProgIdTag 原因: " + ExceptionUtil.getStackTrace(e));
        }

        return SKIP_BODY;
    }

    public void setProgId(String progId) {
        this.progId = progId;
    }

}
