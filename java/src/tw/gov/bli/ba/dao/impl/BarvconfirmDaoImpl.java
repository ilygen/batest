package tw.gov.bli.ba.dao.impl;

/**
 * DAO for 月核案件檢核確認檔 (<code>BARVCONFIRM</code>)
 * 
 * @author Noctis
 */

import java.util.HashMap;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BarvconfirmDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Barvconfirm;
import tw.gov.bli.common.annotation.DaoFieldList;

public class BarvconfirmDaoImpl extends SqlMapClientDaoSupport implements BarvconfirmDao {

	/**
     * 依傳入條件取得 月核案件檢核確認檔(<code>BARVCONFIRM</code>) 資料清單
     */
    public List<Barvconfirm> selectBarvconfirmDataBy(String payCode, String issuYm) {
    	
    	HashMap<String,String> map = new HashMap<String,String>();
    	
    	map.put("payCode", payCode);
        map.put("issuYm", issuYm);
       
        return (List<Barvconfirm>) getSqlMapClientTemplate().queryForList("BARVCONFIRM.selectBarvconfirmDataBy", map);
    }
    
    /**
     * 更新 月核案件檢核確認檔(<code>BARVCONFIRM</code>) 資料
     * 
     * @param barvconfirm 給付主檔
     */
    public void updateBarvconfirmData(Barvconfirm barvconfirm) {
        getSqlMapClientTemplate().update("BARVCONFIRM.updateBarvconfirmData", barvconfirm);
    }
    
    /**
     * 依傳入條件取得 月核案件檢核確認檔(<code>BARVCONFIRM</code>) 資料清單
     */
    public String selectDataCountBy(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        return (String) getSqlMapClientTemplate().queryForObject("BARVCONFIRM.selectDataCountBy", map);
    }

}
