package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.CvldtlDao;
import tw.gov.bli.ba.dao.NbexcepDao;
import tw.gov.bli.ba.domain.Cvldtl;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for (<code>NBEXCEP</code>)
 * 
 * @author Noctis
 */

public class NbexcepDaoImpl extends SqlMapClientDaoSupport implements NbexcepDao {
	 
	 /**
     * 依傳入條件取得(<code>NBEXCEP</code>)
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    public Integer selectCountForNbexcep(String evtIds) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("evtIds", evtIds);

        return (Integer) getSqlMapClientTemplate().queryForObject("NBEXCEP.selectCountForNbexcep", map);
    }
    
    /**
     * 依傳入條件取得(<code>NBEXCEP</code>)
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    public String selectCtrlBdtBy(String evtIds) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("evtIds", evtIds);
        
        return (String) getSqlMapClientTemplate().queryForObject("NBEXCEP.selectCtrlBdtBy", map);
    }
}
