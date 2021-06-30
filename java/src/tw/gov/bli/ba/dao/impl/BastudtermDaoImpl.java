package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BastudtermDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOImpl for 遺屬在學期間檔 (<code>BASTUDTERM</code>)
 * 
 * @author Rickychi
 */

@DaoTable("BASTUDTERM")
public class BastudtermDaoImpl extends SqlMapClientDaoSupport implements BastudtermDao {

    /**
     * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Bastudterm</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Bastudterm selectStudMasterDataForSurvivorPaymentQuery(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (Bastudterm) getSqlMapClientTemplate().queryForObject("BASTUDTERM.selectStudMasterDataForSurvivorPaymentQuery", map);
    }

    /**
     * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料明細 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bastudterm> selectStudDetailDataForSurvivorPaymentQuery(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BASTUDTERM.selectStudDetailDataForSurvivorPaymentQuery", map);
    }

    /**
     * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料明細 for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bastudterm> selectStudtermListForSurvivorPayeeDataUpdate(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BASTUDTERM.selectStudtermListForSurvivorPayeeDataUpdate", map);
    }
    
    /**
     * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料明細 for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    public BigDecimal selectMaxTermNoBy(String apNo, String seqNo) {
    	HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BASTUDTERM.selectMaxTermNoBy", map);
    }

    /**
     * 新增遺屬在學期間資料 (<code>BASTUDTERM</code>) for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    public BigDecimal insertBastudterm(Bastudterm studterm) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BASTUDTERM.insertBastudterm", studterm);
    }
    
    /**
     * 刪除遺屬在學期間資料 (<code>BASTUDTERM</code>) for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    public void deleteBastudterm(String apNo, String seqNo){
    	HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        getSqlMapClientTemplate().update("BASTUDTERM.deleteBastudterm", map);
    }
    
    /**
     * 依傳入條件取得 眷屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料明細 for 眷屬資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bastudterm> selectStudtermListForDependantDataUpdate(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BASTUDTERM.selectStudtermListForDependantDataUpdate", map);
    }

    
}
