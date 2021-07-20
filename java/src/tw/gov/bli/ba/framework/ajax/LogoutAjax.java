package tw.gov.bli.ba.framework.ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import tw.gov.bli.common.helper.UserSessionHelper;

public class LogoutAjax {
    
    /**
     * 使用者登出
     * @return
     */
    public String doLogout() {
        WebContext ctx = WebContextFactory.get();
        HttpServletRequest request = ctx.getHttpServletRequest();

        HttpSession session = request.getSession();
        UserSessionHelper.removeUserData(request);

        return "";
    }
}
