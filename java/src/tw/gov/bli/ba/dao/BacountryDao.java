package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Bacountry;

/**
 * DAO for 國家代碼檔 (<code>BACOUNTRY</code>)
 * 
 * @author Rickychi
 */
public interface BacountryDao {
    /**
     * 取得 國家代碼檔(<code>BACOUNTRY</code>) 資料
     * 
     * @param countryId 國家代碼
     * @param cname 國家名稱
     * @return 內含 <code>Bacountry</code> 物件的 List
     */
    public List<Bacountry> selectDataBy(String countryId, String cname);
    
    /**
     * 取得 國家代碼檔(<code>BACOUNTRY</code>) 資料
     * 
     * @param countryId 國家代碼
     * @param cname 國家名稱
     * @return 內含 <code>Bacountry</code> 物件的 List
     */
    public String selectCNameData(String countryId);
}
