package tw.gov.bli.common.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.gov.bli.common.ConstantKey;
import tw.gov.bli.common.domain.UserInfo;
import tw.gov.bli.common.domain.UserInfoBean;

/**
 * 用來將使用者物件存放到 Session 及自 Session 中取出之 Helper Class,<br>
 * 使用者物件需實做 <code>tw.gov.bli.common.domain.UserInfo<code> Interface
 * 
 * @author Goston
 */
public class UserSessionHelper {
    private static Log log = LogFactory.getLog(UserSessionHelper.class);

    /**
     * 將使用者資料存到 Session 中
     * 
     * @param request
     * @param userData
     */
    public static void setUserData(HttpServletRequest request, UserInfo userData) {
        HttpSession session = request.getSession();
        session.setAttribute(ConstantKey.USER_INFO, (UserInfoBean) userData);
    }

    /**
     * 從 Session 中取得使用者資料
     * 
     * @param request
     * @return
     */
    public static UserInfo getUserData(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserInfo userData = (UserInfo) session.getAttribute(ConstantKey.USER_INFO);
        return userData;
    }

    /**
     * 將使用者資料從 Session 中移除
     * 
     * @param request
     */
    public static void removeUserData(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            // Marked by Goston - 2008.10.23
            // session.removeAttribute(ConstantKey.USER_INFO);
            session.invalidate();
        }
    }
}
