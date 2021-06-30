package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BahandicaptermDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bahandicapterm;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOImpl for 遺屬眷屬重殘期間檔 (<code>BAHANDITERM</code>)
 * 
 * 
 */

@DaoTable("BAHANDICAPTERM")
public class BahandicaptermDaoImpl extends SqlMapClientDaoSupport implements BahandicaptermDao {

    /**
     * 依傳入條件取得 遺屬眷屬重殘期間檔 (<code>BAHANDICAPTERM</code>) 重殘期間資料 for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bahandicapterm</code> 物件的List
     */
    public List<Bahandicapterm> selectHandicaptermListForSurvivorPayeeDataUpdate(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BAHANDICAPTERM.selectBahandicaptermListForSurvivorPayeeDataUpdate", map);
    }

    /**
     * 新增遺屬重殘期間資料 (<code>BAHANDICAPTERM</code>) for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bahandicapterm</code> 物件的List
     */
    public BigDecimal insertBahandicapterm(Bahandicapterm handicapterm) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAHANDICAPTERM.insertBahandicapterm", handicapterm);
    }

    /**
     * 刪除遺屬重殘起訖年度資料 (<code>BAHANDICAPTERM</code>) for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bahandicapterm</code> 物件的List
     */
    public void deleteBahandicapterm(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        getSqlMapClientTemplate().update("BAHANDICAPTERM.deleteBahandicapterm", map);
    }

    /**
     * 依傳入條件取得 遺屬重殘期間檔 (<code>BAHANDICAPTERM</code>) 重殘資料 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Bahandicapterm</code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public Bahandicapterm selectHandicapMasterDataForSurvivorPaymentQuery(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (Bahandicapterm) getSqlMapClientTemplate().queryForObject("BAHANDICAPTERM.selectBahandicapMasterDataForSurvivorPaymentQuery", map);
    }

    /**
     * 依傳入條件取得 遺屬重殘期間檔 (<code>BAHANDICAPTERM</code>) 重殘資料明細 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bahandicapterm</code> 物件的List
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bahandicapterm> selectHandicapDetailDataForSurvivorPaymentQuery(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BAHANDICAPTERM.selectBahandicapDetailDataForSurvivorPaymentQuery", map);
    
    }

    /**
     * 依傳入條件取得 遺屬重殘期間檔 (<code>BAHANDICAPTERM</code>) 重殘資料明細 for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bahandicapterm</code> 物件的List
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bahandicapterm> selectHandicaptermListForDependantDataUpdate(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BAHANDICAPTERM.selectBahandicaptermListForSurvivorPayeeDataUpdate", map);
    }

    /**
     * 依傳入條件取得 遺屬重殘期間檔 (<code>BAHANDICAPTERM</code>) 重殘資料明細 for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bahandicapterm</code> 物件的List
     */
    public BigDecimal selectMaxTermNoBy(String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAHANDICAPTERM.selectMaxTermNoBy", map);
    }

}
