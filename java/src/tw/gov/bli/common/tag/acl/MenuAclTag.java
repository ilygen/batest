package tw.gov.bli.common.tag.acl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.common.domain.UserInfo;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.ExceptionUtil;
import tw.gov.bli.common.util.StrUtil;

/**
 * TagLib - 選單權限控管
 * 
 * @author Goston
 */
public class MenuAclTag extends TagSupport {
    private static Log log = LogFactory.getLog(MenuAclTag.class);

    private String itemId;

    public int doStartTag() {
        try {
            UserInfo userData = UserSessionHelper.getUserData((HttpServletRequest) pageContext.getRequest());

            if (StringUtils.isBlank(itemId) || userData == null) {
                return SKIP_BODY;
            }

            String[] itemIds = StrUtil.splitTrim(StringUtils.upperCase(itemId), ",");

            for (String id : itemIds) {
                if (userData.hasPrivilege(id))
                    return EVAL_BODY_INCLUDE;
            }

            return SKIP_BODY;

        }
        catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: MenuAclTag 原因: " + ExceptionUtil.getStackTrace(e));
            return SKIP_BODY;
        }
    }

    public int doEndTag() throws JspException {
        try {
            return EVAL_PAGE;
        }
        catch (Exception e) {
            log.error("Taglib 發生錯誤, Tag: MenuAclTag 原因: " + ExceptionUtil.getStackTrace(e));
            return EVAL_PAGE;
        }
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}
