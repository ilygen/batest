package tw.gov.bli.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.common.dao.LoginDao;
import tw.gov.bli.common.domain.CasUser;
import tw.gov.bli.common.domain.UserButton;
import tw.gov.bli.common.domain.UserInfo;

public class LoginDaoImpl extends SqlMapClientDaoSupport implements LoginDao {

    /**
     * 檢核使用者是否有登入 Portal
     * 
     * @param userId 使用者代碼
     * @param token 檢查資訊 Token
     * @return 回傳 <code>'Y'</code>，表示可以正常登入; 回傳 <code>'N'</code>，表示尚未登入、登入錯誤或已登出。
     */
    public String selectUserLoginStatus(String userId, String token) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("p_user_id", userId);
        map.put("p_token", token);
        getSqlMapClientTemplate().queryForObject("LOGIN.selectUserLoginStatus", map);
        return map.get("result");
    }
    
    /**
     * CAS 登入時, 取得使用者資料
     * 
     * @param userId 使用者代碼
     * @return <code>CasUser</code> 物件
     */
    /*
    public CasUser selectCasUser(String userId) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        return (CasUser) getSqlMapClientTemplate().queryForObject("LOGIN.selectCasUser", map);
    }
    */

    /**
     * 取得使用者所能執行的系統項目代碼
     * 
     * @param systemId 應用系統代號
     * @param userId 使用者代碼
     * @return 內含 系統項目代碼 (<code>ITEMS.ITEM_ID</code>) 的 List
     */
    public List<String> selectUserItemList(String systemId, String userId) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("systemId", systemId);
        map.put("userId", userId);
        return getSqlMapClientTemplate().queryForList("LOGIN.selectUserItem", map);
    }
    
    /**
     * CAS 登入時, 取得使用者所能執行的系統項目代碼
     * 
     * @param systemId 應用系統代號
     * @param userId 使用者代碼
     * @return 內含 系統項目代碼 (<code>PORTAL_USER.USER_MENU_ITEM</code>) 的 List
     */
    /*
    public List<String> selectCasUserItemList(String systemId, String userId) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("systemId", systemId);
        map.put("userId", userId);
        return getSqlMapClientTemplate().queryForList("LOGIN.selectCasUserItem", map);
    }
    */

    /**
     * 取得使用者於各畫面所能執行的按鍵名稱
     * 
     * @param systemId 應用系統代號
     * @param userId 使用者代碼
     * @return 內含 <code>tw.gov.bli.common.domain.UserButton</code> 物件的 List
     */
    public List<UserButton> selectUserButtonList(String systemId, String userId) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("systemId", systemId);
        map.put("userId", userId);
        return getSqlMapClientTemplate().queryForList("LOGIN.selectUserButton", map);
    }

    /**
     * 登入時, 塞一筆 Logon Log 到 NPPORTAL.LOGON_LOG 中
     * 
     * @param userData 使用者物件
     */
    public void insertNpportalLogonLogData(UserInfo userData) {
        getSqlMapClientTemplate().insert("LOGIN.insertNpportalLogonLogData", userData);
    }
}
