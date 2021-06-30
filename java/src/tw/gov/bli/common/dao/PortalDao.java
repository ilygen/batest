package tw.gov.bli.common.dao;

import java.util.List;
import tw.gov.bli.common.domain.CasUser;
import tw.gov.bli.common.domain.SystemFunction;

public interface PortalDao {
    /**
     * 依應用系統代號取得系統功能清單 for CAS
     * 
     * @param systemId 應用系統代號
     * @return 內含 <code>tw.gov.bli.common.domain.SystemFunction</code> 物件的 List
     */   
    public List<SystemFunction> selectCasSysFuncByItemId(String itemId);
    
   /**
    * CAS 登入時, 取得使用者所能執行的系統項目代碼
    * 
    * @param systemId 應用系統代號
    * @param userId 使用者代碼
    * @return 內含 系統項目代碼 (<code>PORTAL_USER.USER_MENU_ITEM</code>) 的 List
    */
   public List<String> selectCasUserItemList(String systemId, String userId);
   
   /**
    * CAS 登入時, 取得使用者資料
    * 
    * @param userId 使用者代碼
    * @return <code>CasUser</code> 物件
    */
   public CasUser selectCasUser(String userId);
}
