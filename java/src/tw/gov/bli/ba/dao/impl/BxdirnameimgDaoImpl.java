package tw.gov.bli.ba.dao.impl;


import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BxdirnameimgDao;
import tw.gov.bli.ba.domain.Bamanager;

/**
 * DAO for 總經理署名圖 (<code>BXDIRNAMEIMG</code>)
 *
 * @author ttlin
 */
public class BxdirnameimgDaoImpl extends SqlMapClientDaoSupport implements BxdirnameimgDao {

    /**
     * 依傳入的條件取得 總經理署名圖(<code>BXDIRNAMEIMG</code>)
     *
     * @param type
     * @param dte
     * @return Bamanager
     */
	@Override
	public Bamanager selectFile(String type, String dte) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("dte", dte);
        return (Bamanager) getSqlMapClientTemplate().queryForObject("BXDIRNAMEIMG.selectFile", map);
	}

}
