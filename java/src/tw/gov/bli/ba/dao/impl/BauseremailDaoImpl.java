package tw.gov.bli.ba.dao.impl;

/**
 * DAO for 承辦人電子郵件檔 (<code>BAUSEREMAIL</code>)
 * 
 * @author Noctis
 */

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BauseremailDao;
import tw.gov.bli.ba.domain.Bauseremail;


public class BauseremailDaoImpl extends SqlMapClientDaoSupport implements BauseremailDao {

	/**
     * 依傳入條件取得 承辦人電子郵件檔(<code>BAUSEREMAIL</code>) 資料清單
     */
    public List<Bauseremail> selectBauseremailDataListBy() {
      
        return getSqlMapClientTemplate().queryForList("BAUSEREMAIL.selectBauseremailDataListBy");
    }

	 
	public List<Bauseremail> selectBaMediaUserEmailDataListBy(String payCode) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("payCode", payCode);
		return getSqlMapClientTemplate().queryForList("BAUSEREMAIL.selectBaMediaUserEmailDataListBy", map);
	}

}
