package tw.gov.bli.common.services;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.owasp.encoder.Encode;

import edu.yale.its.tp.cas.client.filter.CASFilter;
import tw.gov.bli.common.dao.LoginDao;
import tw.gov.bli.common.dao.PortalDao;
import tw.gov.bli.common.dao.PortalLogDao;
import tw.gov.bli.common.domain.CasUser;
import tw.gov.bli.common.domain.PortalLog;
import tw.gov.bli.common.domain.UserButton;
import tw.gov.bli.common.domain.UserInfo;
import tw.gov.bli.common.helper.EnvFacadeHelper;
import tw.gov.bli.common.helper.UserSessionHelper;
import tw.gov.bli.common.util.DateUtil;
import tw.gov.bli.common.util.ExceptionUtil;
import tw.gov.bli.common.util.HttpUtil;

/**
 * 檢核使用者 Portal 登入狀態並取得使用者相關資料及權限
 * 
 * @author Goston
 */
public class LoginService {
    private static Log log = LogFactory.getLog(LoginService.class);

    private boolean developMode = false;
    private LoginDao loginDao;
    private PortalDao portalDao;
    private PortalLogDao portalLogDao;

    /**
     * 檢核使用者 Portal 登入狀態, 並將傳入的使用者物件內容初始化
     * 
     * @param userData 使用者物件 (若要自行延伸功能請繼承 <code>tw.gov.bli.common.domain.UserInfoBean</code>)
     * @param request HttpServletRequest
     * @return 登入成功回傳 <code>true</code>, 失敗回傳 <code>false</code>
     */
    public boolean getUserLoginData(UserInfo userData, HttpServletRequest request) {
        log.info("檢核使用者 Portal 登入狀態及使用者物件初始化 開始...");

        HttpSession session = request.getSession();

        String userId = ""; // 使用者代碼
        String userName = ""; // 使用者名稱
        String deptId = ""; // 部門代碼
        String empNo = ""; // 員工編號
        String loginIP = ""; // 使用者 IP
        String token = ""; // 檢查資訊 Token

        if (EnvFacadeHelper.isEnableCAS()) {
            userId = StringUtils.defaultString((String) session.getAttribute(CASFilter.CAS_FILTER_USER)); // 使用者代碼

            if (StringUtils.isBlank(userId))
                return false;

            CasUser casUser = portalDao.selectCasUser(userId);

            if (casUser == null)
                return false;

            userName = StringUtils.defaultString(casUser.getUserName()); // 使用者名稱
            deptId = StringUtils.defaultString(casUser.getDeptId()); // 部門代碼
            empNo = StringUtils.defaultString(casUser.getEmpId()); // 員工編號
            loginIP = StringUtils.defaultString(HttpUtil.getClientIP(request)); // 使用者 IP
            token = userId + DateUtil.getNowWestDateTime(true); // 檢查資訊 Token
        }
        else {
            // 取得大同 Portal 傳來的 Request Parameter
            userId = StringUtils.defaultString(Encode.forHtmlAttribute(request.getParameter("PERSONID"))); // 使用者代碼
            userName = StringUtils.defaultString(Encode.forHtmlAttribute(request.getParameter("UserName"))); // 使用者名稱
            deptId = StringUtils.defaultString(Encode.forHtmlAttribute(request.getParameter("DEPTID"))); // 部門代碼
            empNo = StringUtils.defaultString(Encode.forHtmlAttribute(request.getParameter("EmpNO"))); // 員工編號
            loginIP = StringUtils.defaultString(Encode.forHtmlAttribute(request.getParameter("USERIP"))); // 使用者 IP
            token = StringUtils.defaultString(Encode.forHtmlAttribute(request.getParameter("TKN"))); // 檢查資訊 Token
        }

        // 沒有 使用者代碼 及 檢查資訊 Token 一切免談
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(token))
            return false;

        log.debug("取得使用者資訊: PERSONID = " + userId + " / UserName = " + userName + " / DEPTID = " + deptId + " / EmpNO = " + empNo + " / USERIP = " + loginIP + " / TKN = " + token);

        // 非開發模式需檢核使用者是否已登入 Portal
        if (!developMode) {
            if (!EnvFacadeHelper.isEnableCAS()) {
                // 'Y' 表示可以正常登入; 'N' 表示 尚未登入 / 登入錯誤 / 已登出
                if (!StringUtils.defaultString(loginDao.selectUserLoginStatus(userId, token)).equals("I")) {
                    log.info("檢核使用者 Portal 登入狀態, 使用者代碼: " + Encode.forJava(userId) + " ... 結果: 使用者 尚未登入 / 登入錯誤 / 已登出 Portal...");
                    return false;
                }
                else {
                    log.info("檢核使用者 Portal 登入狀態, 使用者代碼: " + Encode.forJava(userId) + " ... 結果: 使用者正常登入 Portal...");
                }
            }
        }

        // 設定使用者資料
        userData.setUserId(userId);
        userData.setUserName(userName);
        userData.setDeptId(deptId);
        userData.setEmpNo(empNo);
        userData.setLoginIP(loginIP);
        userData.setToken(token);

        log.debug("設定使用者資料完成, 使用者代碼: " + userId);

        if (EnvFacadeHelper.isEnableCAS()) {
            // 取得使用者可執行之所有系統項目代碼 (ITEMS.ITEM_ID)
            List<String> itemIdList = portalDao.selectCasUserItemList(EnvFacadeHelper.getSystemId(), userId);
            userData.setItemIdList(itemIdList);
        }
        else {
            // 取得使用者可執行之所有系統項目代碼 (ITEMS.ITEM_ID)
            List<String> itemIdList = loginDao.selectUserItemList(EnvFacadeHelper.getSystemId(), userId);
            userData.setItemIdList(itemIdList);
        }

        log.debug("設定使用者可執行之系統項目代碼資料完成, 使用者代碼: " + userId);

        // 取得各頁面使用者所能執行之 Button 清單
        if (EnvFacadeHelper.isButtonControlEnabled()) {
            List<UserButton> userButtonList = loginDao.selectUserButtonList(EnvFacadeHelper.getSystemId(), userId);
            userData.setUserButtonList(userButtonList);
        }

        log.debug("設定使用者各頁面可執行之 Button 清單完成, 使用者代碼: " + userId);

        // 將使用者資料存入 Session
        UserSessionHelper.setUserData(request, userData);

        log.info("檢核使用者 Portal 登入狀態及使用者物件初始化 完成...");

        // 非開發模式於登入成功後須寫入一筆 Logon log 到 NPPORTAL.LOGON_LOG
        if (!developMode) {
            if (!EnvFacadeHelper.isEnableCAS()) {
                try {
                    loginDao.insertNpportalLogonLogData(userData);
                }
                catch (Exception e) {
                    log.error("NPPORTAL.LOGON_LOG 寫入錯誤, 原因:" + ExceptionUtil.getStackTrace(e));
                }
            }
        }

        // Portal Log - Login Log
        // [
        if (EnvFacadeHelper.isNeedLogging(request)) {
            try {
                String path = request.getServletPath();

                Timestamp date = DateUtil.getNowDateTimeAsTimestamp();
                String now = DateUtil.parseTimestampToWestDateTime(date, true);

                PortalLog logData = new PortalLog();
                logData.setLogDateTime(now); // 紀錄時間
                logData.setUserId(userData.getUserId()); // 用戶代號
                logData.setUserIP(userData.getLoginIP()); // 用戶 IP 位址
                logData.setUserAction("Login"); // 用戶執行動作
                logData.setApCode(EnvFacadeHelper.getSystemId()); // 應用系統代號
                logData.setApName(EnvFacadeHelper.getSystemName()); // 應用系統名稱
                logData.setApFunctionCode(EnvFacadeHelper.getItemIdByServletPath(path)); // 應用系統功能代號
                logData.setApFunctionName(EnvFacadeHelper.getItemNameByServletPath(path)); // 應用系統功能名稱
                logData.setApUrl(request.getServletPath()); // 應用系統網址
                logData.setLogDescript("使用者登入系統"); // 說明
                logData.setDateTime(date); // 系統時間
                logData.setToken(userData.getToken()); // 檢查資訊

                BigDecimal sysId = portalLogDao.insertData(logData);
                userData.setSysId(sysId);
                userData.setApFunctionCode(EnvFacadeHelper.getItemIdByServletPath(request.getServletPath()));
                userData.setApFunctionName(EnvFacadeHelper.getItemNameByServletPath(request.getServletPath()));
            }
            catch (Exception e) {
                log.error("Portal Log - Login Log 寫入錯誤, 原因:" + ExceptionUtil.getStackTrace(e));
            }
        }
        // ]

        return true;
    }

    public void setDevelopMode(boolean developMode) {
        this.developMode = developMode;
    }

    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public void setPortalLogDao(PortalLogDao portalLogDao) {
        this.portalLogDao = portalLogDao;
    }

    public void setPortalDao(PortalDao portalDao) {
        this.portalDao = portalDao;
    }
}
