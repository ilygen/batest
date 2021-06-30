package tw.gov.bli.common.helper;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tw.gov.bli.common.domain.SystemFunction;
import tw.gov.bli.common.domain.UserInfo;

/**
 * 用來存放及取得系統環境參數之 Helper
 * 
 * @author Goston
 */
public class EnvFacadeHelper {
    private static Log log = LogFactory.getLog(EnvFacadeHelper.class);

    private static String systemId = null; // 應用系統代號
    private static String systemName = null; // 應用系統名稱

    // 權限控管相關
    // [
    private static boolean enableCAS = false; // 是否啟用 CAS Login
    private static boolean undefineUrlControl = true; // 是否管控未被定義於系統功能清單之功能
    private static boolean buttonControlEnabled = true; // 是否控管 Button
    private static boolean buttonHide = true; // 啟動 Button 控管時, 對於沒有權限的 Button 是否隱藏, 設為 false 則 Button 會 Disable
    // ]

    // Log 相關
    // [
    private static boolean loggingEnabled = false; // Framework Logging 機制是否啟用
    private static boolean undefineUrlLogging = true; // 是否 Log 未定義之 URL 的操作
    // ]

    private static HashMap<String, SystemFunction> systemFunctionMap; // 系統功能清單

    /**
     * 檢核使用者所執行的功能是否未被定義
     * 
     * @param path 使用者執行的功能的 ServletPath
     * @return 使用者執行的是未定義的功能回傳 <code>true</code>, 為已定義的功能回傳 <code>false</code>, 傳入之 Path 為 <code>null</code> 或空白回傳 <code>true</code>
     */
    public static boolean isUndefineUrlByServletPath(String path) {
        if (StringUtils.isBlank(path))
            return true;

        if (!systemFunctionMap.containsKey(parseUrlByServletPath(path))) // 未定義
            return true;
        else
            // 有定義
            return false;
    }

    /**
     * 傳入的 ServletPath 若為 <code>.jsp</code> / <code>.html</code> / <code>.htm</code> 等網頁頁面<br>
     * 則取出檔名的部份 (不含副檔名) 轉成大寫傳回 (即畫面編號),<br>
     * 若為 .do 之類則不進行轉換<br>
     * 
     * @param path 使用者執行的功能的 ServletPath
     * @return 轉換後的字串, 如傳入 <code>"/abc.jsp"</code> 則傳回 <code>"ABC"</code>; 如傳入 <code>"/abc.do"</code> 則傳回 <code>"/abc.do"</code>
     */
    public static String parseUrlByServletPath(String path) {
        if (StringUtils.endsWithIgnoreCase(path, ".jsp") || StringUtils.endsWithIgnoreCase(path, ".htm") || StringUtils.endsWithIgnoreCase(path, ".html"))
            return StringUtils.upperCase(path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")));
        else
            return path;
    }

    /**
     * 依傳入的 ServletPath 取得 系統項目代碼 (<code>ITEMS.ITEM_ID</code>)<br>
     * 
     * @param path 使用者執行的功能的 ServletPath
     * @return 系統項目代碼, 若該路徑未定義回傳 <code>null</code>
     */
    public static String getItemIdByServletPath(String path) {
        if (isUndefineUrlByServletPath(path)) // 路徑未定義
            return null;

        String url = parseUrlByServletPath(path);

        if (!systemFunctionMap.containsKey(url)) // 功能未定義
            return null;
        else
            // 功能有定義
            return ((SystemFunction) systemFunctionMap.get(url)).getItemId();
    }

    /**
     * 依傳入的 ServletPath 取得 系統項目名稱 (<code>ITEMS.ITEM_NAME</code>)<br>
     * 
     * @param path 使用者執行的功能的 ServletPath
     * @return 系統項目名稱, 若該路徑未定義回傳 <code>null</code>
     */
    public static String getItemNameByServletPath(String path) {
        if (isUndefineUrlByServletPath(path)) // 路徑未定義
            return null;

        String url = parseUrlByServletPath(path);

        if (!systemFunctionMap.containsKey(url)) // 功能未定義
            return null;
        else
            // 功能有定義
            return ((SystemFunction) systemFunctionMap.get(url)).getItemName();
    }

    /**
     * 檢核傳入的 頁面程式代號 - 螢幕編號 是否有定義
     * 
     * @param progId 頁面程式代號 - 螢幕編號
     * @return 有定義回傳 <code>true</code>; 未定義回傳 <code>false</code>
     */
    public static boolean isValidProgId(String progId) {
        if (StringUtils.isBlank(progId) || !systemFunctionMap.containsKey(StringUtils.upperCase(progId)))
            return false;
        else
            return true;
    }

    /**
     * 檢核目前使用者執行的動作是否需要紀錄 Log
     * 
     * @return 需要回傳 <code>true</code>; 不需要回傳 <code>false</code>
     */
    public static boolean isNeedLogging(HttpServletRequest request) {
        if (request == null)
            return false;

        UserInfo userData = UserSessionHelper.getUserData(request);

        if (loggingEnabled && userData != null) {
            log.debug("檢核路徑是否需要紀錄 Log, 路徑: " + request.getServletPath());

            if (isUndefineUrlByServletPath(request.getServletPath())) {
                log.debug(((undefineUrlLogging) ? "" : "不") + "需紀錄 Log, 路徑: " + request.getServletPath());
                return undefineUrlLogging;
            }
            else {
                log.debug("需紀錄 Log, 路徑: " + request.getServletPath());
                return true;
            }
        }
        else {
            return false;
        }
    }

    /**
     * 儲存系統功能清單, 用於權限檢核及各項 Log
     * 
     * @param list 內含 <code>SystemFunction</code> 物件的 List
     */
    public static void setSystemFunctionMap(List<SystemFunction> list) {
        log.debug("開始設定 系統功能清單 ...");

        systemFunctionMap = new HashMap<String, SystemFunction>();
        for (SystemFunction systemFunction : list) {
            log.debug("設定 系統功能清單 項目: " + systemFunction.getItemId() + " / " + systemFunction.getItemName() + " / " + systemFunction.getUrl());
            systemFunctionMap.put(systemFunction.getUrl(), systemFunction);
        }

        log.debug("系統功能清單 設定完成!");
    }

    /**
     * 取得 應用系統代號
     * 
     * @return 應用系統代號
     */
    public static String getSystemId() {
        return systemId;
    }

    /**
     * 設定 應用系統代號
     * 
     * @param systemId 應用系統代號
     */
    public static void setSystemId(String systemId) {
        // 應用系統代號 僅可指定一次
        if (EnvFacadeHelper.systemId == null)
            EnvFacadeHelper.systemId = StringUtils.upperCase(systemId);
    }

    /**
     * 取得 應用系統名稱
     * 
     * @return 應用系統名稱
     */
    public static String getSystemName() {
        return systemName;
    }

    /**
     * 設定 應用系統名稱
     * 
     * @param systemName 應用系統名稱
     */
    public static void setSystemName(String systemName) {
        // 應用系統名稱 僅可指定一次
        if (EnvFacadeHelper.systemName == null)
            EnvFacadeHelper.systemName = systemName;
    }

    /**
     * 取得 是否管控未被定義於系統功能清單之功能
     * 
     * @return
     */
    public static boolean isUndefineUrlControl() {
        return undefineUrlControl;
    }

    /**
     * 設定 是否管控未被定義於系統功能清單之功能
     * 
     * @param undefineUrlControl
     */
    public static void setUndefineUrlControl(boolean undefineUrlControl) {
        EnvFacadeHelper.undefineUrlControl = undefineUrlControl;
    }

    /**
     * 取得 Framework Logging 機制是否啟用
     * 
     * @return
     */
    public static boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    /**
     * 設定 Framework Logging 機制是否啟用
     * 
     * @param loggingEnabled
     */
    public static void setLoggingEnabled(boolean loggingEnabled) {
        EnvFacadeHelper.loggingEnabled = loggingEnabled;
    }

    /**
     * 取得 是否 Log 未定義之 URL 的操作
     * 
     * @return
     */
    public static boolean isUndefineUrlLogging() {
        return undefineUrlLogging;
    }

    /**
     * 設定 是否 Log 未定義之 URL 的操作
     * 
     * @param undefineUrlLogging
     */
    public static void setUndefineUrlLogging(boolean undefineUrlLogging) {
        EnvFacadeHelper.undefineUrlLogging = undefineUrlLogging;
    }

    /**
     * 取得 是否控管 Button
     * 
     * @return
     */
    public static boolean isButtonControlEnabled() {
        return buttonControlEnabled;
    }

    /**
     * 設定 是否控管 Button
     * 
     * @param buttonControlEnabled
     */
    public static void setButtonControlEnabled(boolean buttonControlEnabled) {
        EnvFacadeHelper.buttonControlEnabled = buttonControlEnabled;
    }

    /**
     * 取得 啟動 Button 控管時, 對於沒有權限的 Button 是否隱藏
     * 
     * @return
     */
    public static boolean isButtonHide() {
        return buttonHide;
    }

    /**
     * 設定 啟動 Button 控管時, 對於沒有權限的 Button 是否隱藏
     * 
     * @param buttonHide
     */
    public static void setButtonHide(boolean buttonHide) {
        EnvFacadeHelper.buttonHide = buttonHide;
    }

    /**
     * 取得 是否啟用 CAS Login
     * @return
     */
    public static boolean isEnableCAS() {
        return enableCAS;
    }

    /**
     * 設定 是否啟用 CAS Login
     * @param enableCAS
     */
    public static void setEnableCAS(boolean enableCAS) {
        EnvFacadeHelper.enableCAS = enableCAS;
    }
    
    
}
