package tw.gov.bli.ba.framework.struts.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.struts.DelegatingRequestProcessor;
import tw.gov.bli.ba.Global;
import tw.gov.bli.common.domain.UserInfo;
import tw.gov.bli.common.helper.UserSessionHelper;

public class FrameworkController extends DelegatingRequestProcessor {

    private static Log log = LogFactory.getLog(FrameworkController.class);

    protected boolean processRoles(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping) throws IOException, ServletException {
        String roles[] = mapping.getRoleNames();

        // 檢查是否為任何人皆可以存取的頁面
        if ((roles != null) && (roles.length == 1)) {
            if (StringUtils.equals(Global.EVERYONE_ROLE, roles[0])) {
                return true;
            }
        }

        // 不是任何人皆可以存取的頁面的話，都需要有登入系統才可使用
        UserInfo userData = UserSessionHelper.getUserData(request);
        if (userData != null) {
            return true;
        }
        else {
            // 設定 Response 為沒有通過授權,
            // 頁面會導到 web.xml 中 error-code = 401 所指定的頁面
            log.debug("使用者沒有通過授權");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, super.getInternal().getMessage("error.notAuthorized", mapping.getPath()));
            return false;
        }
    }
}
