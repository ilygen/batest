package tw.gov.bli.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * Domain Object for 使用者<br>
 * Framework 須仰賴此類別實作之功能, 因此若各系統欲擴充使用者物件之功能需繼承此類別<br>
 * 
 * @author Goston
 */
public class UserInfoBean implements UserInfo, Serializable {
    /**
     * 使用者代碼
     */
    private String userId;

    /**
     * 使用者名稱
     */
    private String userName;

    /**
     * 部門代碼
     */
    private String deptId;

    /**
     * 員工編號
     */
    private String empNo;

    /**
     * 使用者 IP
     */
    private String loginIP;

    /**
     * 檢查資訊 Token
     */
    private String token;

    /**
     * 使用者可執行之所有系統項目代碼 (<code>ITEMS.ITEM_ID</code>)
     */
    private List<String> itemIdList;

    /**
     * 各頁面使用者所能執行之 Button 清單
     */
    private List<UserButton> userButtonList;

    /**
     * 各頁面使用者所能執行之 Button Map
     */
    private HashMap<String, List<String>> userButtonMap;

    /**
     * for Framework Logging, 代表目前使用者作業之 Protal Log Id
     */
    private BigDecimal sysId;

    /**
     * for Framework Logging, 使用者目前執行的功能的 應用系統功能代號
     */
    private String apFunctionCode;

    /**
     * for Framework Logging, 使用者目前執行的功能的 應用系統功能名稱
     */
    private String apFunctionName;

    /**
     * for Framework Logging, 使用者目前執行的功能的 頁面程式代號 - 螢幕編號
     */
    private String progId;

    /**
     * 使用者是否有執行某項功能之權限
     * 
     * @param itemId 系統項目代碼
     * @return 有回傳 <code>true</code>; 沒有回傳 <code>false</code>
     */
    public boolean hasPrivilege(String itemId) {
        if (StringUtils.defaultString(itemId).equals(""))
            return false;

        if (itemIdList.contains(itemId))
            return true;
        else
            return false;
    }

    /**
     * 檢核使用者是否有執行傳入之某按鍵的權限
     * 
     * @param progId 頁面程式代號 - 螢幕編號
     * @param tagId 按鍵編號 - 按鍵名稱
     * @return 有回傳 <code>true</code>; 沒有回傳 <code>false</code>
     */
    public boolean isValidUserButton(String progId, String tagId) {
        if (StringUtils.defaultString(progId).equals("") || StringUtils.defaultString(tagId).equals("") || !userButtonMap.containsKey(progId))
            return false;

        List<String> btnList = (List<String>) userButtonMap.get(progId);
        if (btnList.contains(tagId))
            return true;
        else
            return false;
    }

    public UserInfoBean() {
        itemIdList = new ArrayList<String>();
        userButtonMap = new HashMap<String, List<String>>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getItemIdList() {
        return itemIdList;
    }

    public void setItemIdList(List<String> itemIdList) {
        this.itemIdList = itemIdList;
    }

    public List<UserButton> getUserButtonList() {
        return userButtonList;
    }

    public void setUserButtonList(List<UserButton> userButtonList) {
        this.userButtonList = userButtonList;

        // 將傳入之 Button 清單轉換為 Map
        userButtonMap = new HashMap<String, List<String>>();

        String previousProgId = "";
        for (UserButton userButton : userButtonList) {
            if (!StringUtils.equals(userButton.getProgId(), previousProgId)) {
                List<String> btnList = new ArrayList<String>();
                btnList.add(userButton.getTagId());
                userButtonMap.put(userButton.getProgId(), btnList);
                previousProgId = userButton.getProgId();
            }
            else {
                List<String> btnList = (List<String>) userButtonMap.get(userButton.getProgId());
                btnList.add(userButton.getTagId());
            }
        }
    }

    public BigDecimal getSysId() {
        return sysId;
    }

    public void setSysId(BigDecimal sysId) {
        this.sysId = sysId;
    }

    public String getApFunctionCode() {
        return apFunctionCode;
    }

    public void setApFunctionCode(String apFunctionCode) {
        this.apFunctionCode = apFunctionCode;
    }

    public String getApFunctionName() {
        return apFunctionName;
    }

    public void setApFunctionName(String apFunctionName) {
        this.apFunctionName = apFunctionName;
    }

    public String getProgId() {
        return progId;
    }

    public void setProgId(String progId) {
        this.progId = progId;
    }

}
