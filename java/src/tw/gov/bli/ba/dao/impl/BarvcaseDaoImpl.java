package tw.gov.bli.ba.dao.impl;

/**
 * DAO for 月核案件檢核檔 (<code>BARVCASE</code>)
 * 
 * @author Noctis
 */

import java.util.HashMap;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BarvcaseDao;
import tw.gov.bli.ba.dao.BarvconfirmDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Barvcase;
import tw.gov.bli.ba.domain.Barvconfirm;

public class BarvcaseDaoImpl extends SqlMapClientDaoSupport implements BarvcaseDao {

	/**
     * 依傳入條件取得 月核案件檢核確認檔(<code>BARVCONFIRM</code>) 資料清單
     */
    public List<Barvcase> selectBarvcaseDataBy(String payCode, String issuYm, String rvType) {
    	
    	HashMap<String,String> map = new HashMap<String,String>();
    	
    	map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("rvType", rvType);
       
        return (List<Barvcase>) getSqlMapClientTemplate().queryForList("BARVCASE.selectBarvcaseDataBy", map);
    }

}
