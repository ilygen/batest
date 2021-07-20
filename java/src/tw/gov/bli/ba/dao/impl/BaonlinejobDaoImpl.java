package tw.gov.bli.ba.dao.impl;

/**
 * DAO for 勞保年金線上作業檔 (<code>BAONLINEJOB</code>)
 * 
 * @author Noctis
 */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BaonlinejobDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baonlinejob;
import tw.gov.bli.common.annotation.DaoFieldList;


public class BaonlinejobDaoImpl extends SqlMapClientDaoSupport implements BaonlinejobDao {

	/**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 「查詢線上月核定作業於系統日期當日是否已經執行過。 for 線上月核定作業
     * 
     * @param payCode 給付別
     * @return <code>BigDecimal</code>
     */
    public BigDecimal selectCountCheckForBjMonthBy(String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAONLINEJOB.selectCountCheckForBjMonthBy", map);
    }
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 「查詢線上月試核作業於系統日期當日是否已經執行過。 for 線上月試核作業
     * 
     * @param payCode 給付別
     * @return <code>BigDecimal</code>
     */
    public BigDecimal selectCountCheckForBjMonthCheckBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAONLINEJOB.selectCountCheckForBjMonthCheckBy", map);
    }
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baonlinejob</code> 物件的 List
     */
    public List<Baonlinejob> selectMonthDataListBy(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }
      
        return getSqlMapClientTemplate().queryForList("BAONLINEJOB.selectMonthDataListBy", map);
    }
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @return 內含 <code>Baonlinejob</code> 物件的 List
     */
    public List<Baonlinejob> selectMonthQueryDataListBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }
        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }
      
        return getSqlMapClientTemplate().queryForList("BAONLINEJOB.selectMonthQueryDataListBy", map);
    }
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 編審後 資料清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baonlinejob</code> 物件的 List
     */
    public List<Baonlinejob> selectMonthApprovedDataListBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        return getSqlMapClientTemplate().queryForList("BAONLINEJOB.selectMonthApprovedDataListBy", map);
    }
    
    /**
     * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業
     * 
     * @param Baonlinejob 給付主檔
     */
    public void updateBaonlinejobDataForMonth(Baonlinejob baonlinejob) {
        getSqlMapClientTemplate().update("BAONLINEJOB.updateBaonlinejobDataForMonth", baonlinejob);
    }
    
    /**
     * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月試核作業
     * 
     * @param Baonlinejob 給付主檔
     */
    public void updateBaonlinejobDataForMonthCheck(Baonlinejob baonlinejob) {
        getSqlMapClientTemplate().update("BAONLINEJOB.updateBaonlinejobDataForMonthCheck", baonlinejob);
    }
    
    /**
     * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業 查詢頁面 刪除
     * 
     * @param Baonlinejob 給付主檔
     */
    public void updateBaonlinejobDataForMonthDelete(Baonlinejob baonlinejob) {
        getSqlMapClientTemplate().update("BAONLINEJOB.updateBaonlinejobDataForMonthDelete", baonlinejob);
    }
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 月試核 資料清單
     * 
     * @param payCode 給付別
     * @return 內含 <code>Baonlinejob</code> 物件的 List
     */
    public List<Baonlinejob> selectMonthCheckQueryDataListBy(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        return getSqlMapClientTemplate().queryForList("BAONLINEJOB.selectMonthCheckQueryDataListBy", map);
    }
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 月試核 編審後 資料清單
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Baonlinejob</code> 物件的 List
     */
    public List<Baonlinejob> selectMonthCheckApprovedDataListBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        return getSqlMapClientTemplate().queryForList("BAONLINEJOB.selectMonthCheckApprovedDataListBy", map);
    }
    
    /**
     * 新增 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料
     * 
     * @param baonlinejob 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertBaonlinejobDataForMonthCheck(Baonlinejob baonlinejob) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAONLINEJOB.insertBaonlinejobDataForMonthCheck", baonlinejob);
    }

}
