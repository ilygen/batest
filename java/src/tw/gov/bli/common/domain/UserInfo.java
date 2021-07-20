package tw.gov.bli.common.domain;

import java.math.BigDecimal;
import java.util.List;

public interface UserInfo {

    /**
     * 使用者是否有執行某項功能之權限
     * 
     * @param itemId 系統項目代碼
     * @return 有回傳 <code>true</code>; 沒有回傳 <code>false</code>
     */
    public boolean hasPrivilege(String itemId);

    /**
     * 檢核使用者是否有執行傳入之某按鍵的權限
     * 
     * @param progId 頁面程式代號 - 螢幕編號
     * @param tagId 按鍵編號 - 按鍵名稱
     * @return 有回傳 <code>true</code>; 沒有回傳 <code>false</code>
     */
    public boolean isValidUserButton(String progId, String tagId);

    public String getUserId();

    public void setUserId(String userId);

    public String getUserName();

    public void setUserName(String userName);

    public String getDeptId();

    public void setDeptId(String deptId);

    public String getEmpNo();

    public void setEmpNo(String empNo);

    public String getLoginIP();

    public void setLoginIP(String loginIP);

    public String getToken();

    public void setToken(String token);

    public List<String> getItemIdList();

    public void setItemIdList(List<String> itemIdList);

    public List<UserButton> getUserButtonList();

    public void setUserButtonList(List<UserButton> userButtonList);

    // for Framework Logging
    // [
    // 目前使用者作業之 Protal Log Id
    public BigDecimal getSysId();

    public void setSysId(BigDecimal sysId);

    // 目前執行的功能的 應用系統功能代號
    public String getApFunctionCode();

    public void setApFunctionCode(String apFunctionCode);

    // 目前執行的功能的 應用系統功能名稱
    public String getApFunctionName();

    public void setApFunctionName(String apFunctionName);

    // 目前執行的功能的 頁面程式代號 - 螢幕編號
    public String getProgId();

    public void setProgId(String progId);
    // ]
}
