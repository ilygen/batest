package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.CaubDao;
import tw.gov.bli.ba.domain.Caub;
import tw.gov.bli.ba.domain.Cvldtl;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOImpl for 投保單位檔 (<code>CAUB</code>)
 * 
 * @author jerry
 */
@DaoTable("CAUB")
public class CaubDaoImpl extends SqlMapClientDaoSupport implements CaubDao {

    /**
     * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 資料清單
     * 
     * @param ubNo 保險證號
     */
    @DaoFieldList("UBNO")
    public String selectCaubName(String ubNo) {
        return (String) getSqlMapClientTemplate().queryForObject("CAUB.selectCaubName", ubNo);
    }

    /**
     * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 投保單位資料 for 勞保老年年金給付受理編審清單
     * 
     * @param ubNo 保險證號
     * @return
     */
    @DaoFieldList("UBNO")
    public Caub getOldAgeReviewRpt01UnitDataBy(String ubNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("ubNo", ubNo);

        return (Caub) getSqlMapClientTemplate().queryForObject("CAUB.getOldAgeReviewRpt01UnitDataBy", map);
    }
    
    /**
     * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 資料清單
     * 
     * @param ubNo 保險證號
     */
    @DaoFieldList("UBNO")
    public List<Caub> selectPayeeDataUpdateCaubData(String ubNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("ubNo", ubNo);
        
        return getSqlMapClientTemplate().queryForList("CAUB.getPayeeDataUpdateCaubDataBy", map);
    }
    
    /**
     * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 投保單位資料 for 勞保失能年金給付受理編審清單
     * 
     * @param ubNo 保險證號
     * @return
     */
    @DaoFieldList("UBNO")
    public Caub getDisableReviewRpt01UnitDataBy(String ubNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("ubNo", ubNo);

        return (Caub) getSqlMapClientTemplate().queryForObject("CAUB.getDisableReviewRpt01UnitDataBy", map);
    }
    
    /**
     * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 投保單位資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param ubNo 保險證號
     * @return
     */
    @DaoFieldList("UBNO")
    public Caub getSurvivorReviewRpt01UnitDataBy(String ubNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("ubNo", ubNo);

        return (Caub) getSqlMapClientTemplate().queryForObject("CAUB.getSurvivorReviewRpt01UnitDataBy", map);
    }    
    
    /**
     * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 單位類別資料 for 年金統計查詢
     * 
     * @param ubType 單位類別
     * @return
     */
    public List<Caub> qryUbTypeList() {
    	return getSqlMapClientTemplate().queryForList("CAUB.qryUbTypeList");
    }
}
