package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BbpmakDao;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Bbpmak;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 給付延伸主檔 (<code>BBPMAK</code>)
 * 
 * @author 
 */

@DaoTable("BBPMAK")
public class BbpmakDaoImpl extends SqlMapClientDaoSupport implements BbpmakDao {

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BBPMAK</code>) 資料 for 失能年金給付
     * 
     * @param apNo 受理編號
     * @return
     */
    @Override
	@DaoFieldList("APNO")
    public Bbpmak selectDisasterReviewRpt01AnnuityPayList(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("apNo", apNo);

        return (Bbpmak) getSqlMapClientTemplate().queryForObject("BBPMAK.selectDisasterReviewRpt01AnnuityPayList", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BBPMAK</code>) 資料 for 失能年金給付
     * 
     * @param apNo 受理編號
     * @return
     */
    @Override
	@DaoFieldList("APNO")
    public Bbpmak selectDisasterReviewRpt01DisablePayList(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("apNo", apNo);

        return (Bbpmak) getSqlMapClientTemplate().queryForObject("BBPMAK.selectDisasterReviewRpt01DisablePayList", map);
    }

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BBPMAK</code>) 資料 for 失能年金給付
     * 
     * @param apNo 受理編號
     * @return
     */
    @Override
	@DaoFieldList("APNO")
    public Bbpmak selectDisasterReviewRpt01SurvivorPayList(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("apNo", apNo);

        return (Bbpmak) getSqlMapClientTemplate().queryForObject("BBPMAK.selectDisasterReviewRpt01SurvivorPayList", map);
    }
	
}
