package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BacountryDao;
import tw.gov.bli.ba.domain.Bacountry;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 國家代碼檔 (<code>BACOUNTRY</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BACOUNTRY")
public class BacountryDaoImpl extends SqlMapClientDaoSupport implements BacountryDao {
    /**
     * 取得 國家代碼檔(<code>BACOUNTRY</code>) 資料
     * 
     * @param countryId 國家代碼
     * @param cname 國家名稱
     * @return 內含 <code>Bacountry</code> 物件的 List
     */
    @DaoFieldList("COUNTRY_ID,CNAME")
    public List<Bacountry> selectDataBy(String countryId, String cname) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(countryId))
            map.put("countryId", countryId);
        if (StringUtils.isNotBlank(cname))
            map.put("cname", cname);
        return getSqlMapClientTemplate().queryForList("BACOUNTRY.selectData", map);
    }
    
    /**
     * 取得 國家代碼檔(<code>BACOUNTRY</code>) 資料
     * 
     * @param countryId 國家代碼
     * @param cname 國家名稱
     * @return 內含 <code>Bacountry</code> 物件的 List
     */
    @DaoFieldList("COUNTRY_ID,CNAME")
    public String selectCNameData(String countryId) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(countryId))
            map.put("countryId", countryId);
        return (String) getSqlMapClientTemplate().queryForObject("BACOUNTRY.selectCNameData", map);
    }
}
