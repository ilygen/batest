package tw.gov.bli.common.dao;

import java.util.List;
import tw.gov.bli.common.domain.CasUser;
import tw.gov.bli.common.domain.UserButton;
import tw.gov.bli.common.domain.UserInfo;

/**
 * 處理使用者登入及使用者物件初始化之 DAO,<br>
 * 此 Dao 由 Framework 實作, 各系統不需實作<br>
 * 
 * @author Goston
 */
public interface LoginDao {
    /**
     * 檢核使用者是否有登入 Portal
     * 
     * @param userId 使用者代碼
     * @param token 檢查資訊 Token
     * @return 回傳 <code>'Y'</code>，表示可以正常登入; 回傳 <code>'N'</code>，表示尚未登入、登入錯誤或已登出。
     */
    public String selectUserLoginStatus(String userId, String token);

    /**
     * CAS 登入時, 取得使用者資料
     * 
     * @param userId 使用者代碼
     * @return <code>CasUser</code> 物件
     */
    // public CasUser selectCasUser(String userId);
    
    /**
     * 取得使用者所能執行的系統項目代碼
     * 
     * @param systemId 應用系統代號
     * @param userId 使用者代碼
     * @return 內含 系統項目代碼 (<code>ITEMS.ITEM_ID</code>) 的 List
     */
    public List<String> selectUserItemList(String systemId, String userId);
    
    /**
     * CAS 登入時, 取得使用者所能執行的系統項目代碼
     * 
     * @param systemId 應用系統代號
     * @param userId 使用者代碼
     * @return 內含 系統項目代碼 (<code>PORTAL_USER.USER_MENU_ITEM</code>) 的 List
     */
    // public List<String> selectCasUserItemList(String systemId, String userId);

    /**
     * 取得使用者於各畫面所能執行的按鍵名稱
     * 
     * @param systemId 應用系統代號
     * @param userId 使用者代碼
     * @return 內含 <code>tw.gov.bli.common.domain.UserButton</code> 物件的 List
     */
    public List<UserButton> selectUserButtonList(String systemId, String userId);

    /**
     * 登入時, 塞一筆 Logon Log 到 NPPORTAL.LOGON_LOG 中
     * 
     * @param userData 使用者物件
     */
    public void insertNpportalLogonLogData(UserInfo userData);
}
