package tw.gov.bli.ba.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BabasicamtDao;
import tw.gov.bli.ba.domain.Babasicamt;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.domain.Bacpirec;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Dao for 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>)
 *
 * @author frank
 */

public class BabasicamtDaoImpl extends SqlMapClientDaoSupport implements BabasicamtDao {

    /**
     * 取得 老年年金加計金額調整紀錄檔(<code>BABASICAMT</code>) 的資料
     *
     * @param 
     * @return <code>List<CpiRecMaintCase></code> 物件
     */
	@DaoFieldList("PAYYMB,PAYCODE")
    public List<Babasicamt> selectData(String payYmB,String payCode) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	
    	map.put("payYmB", payYmB);
    	map.put("payCode", payCode);
    	
        return getSqlMapClientTemplate().queryForList("BABASICAMT.selectData", map);
    }
    
    /**
     * 取得 老年年金加計金額調整紀錄檔 最後一筆資料 取得payYmE判斷是否有值(<code>BABASICAMT</code>) 的資料
     *
     * @param 
     * @return <code>List<BasicAmtMaintCase></code> 物件
     */
    public Babasicamt checkPayYmEValue(String payCode) {
    	
        HashMap<String, String> map = new HashMap<String, String>();
    	
    	
    	map.put("payCode", payCode);

        return (Babasicamt) getSqlMapClientTemplate().queryForObject("BABASICAMT.checkPayYmEValue", map);
    }

    /**
     * 新增資料到 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>)<br>
     *
     * @param data <code>Babasicamt</code> 物件
     */
    public BigDecimal insertData(Babasicamt data) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BABASICAMT.insertData", data);
    }

    /**
     * 刪除 老年年金加計金額調整紀錄檔  (<code>BABASICAMT</code>) 資料
     * 
     * @param payYmB 指數年度
     * @return <code>BigDecimal</code>
     */
    public void deleteData(String payCode,String cpiYear1,String cpiYear2) {
    	HashMap<String, String> map = new HashMap<String, String>();

    	if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
    	if (StringUtils.isNotBlank(cpiYear1))
            map.put("cpiYear1", cpiYear1);
    	if (StringUtils.isNotBlank(cpiYear2))
            map.put("cpiYear2", cpiYear2);
    	
        getSqlMapClientTemplate().update("BABASICAMT.deleteData", map);
    }    
    
    /**
     * 依傳入的條件取得老年年金加計金額調整紀錄檔 (<code>BACPIDTL</code>) 的資料
     * 
     * @param payYmB 給付年月起月
     * @return <code>List<CpiDtlMaintCase></code> 物件
     */
    @DaoFieldList("PAYYMB")
    public Babasicamt checkData(String payYmB) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payYmB))
            map.put("payYmB", payYmB);

        return (Babasicamt) getSqlMapClientTemplate().queryForObject("BABASICAMT.checkDataBy", map);
    }    
    
    /**
     * 依傳入的條件取得 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>) 的資料<br>
     *
     * @param PAYYMB
     *
     * @return <code>Babasicamt</code> 物件
     */
    @DaoFieldList("PAYYMB")
    public Babasicamt selectSingleForUpdateData(String payYmB,String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payYmB))
            map.put("payYmB",payYmB);
            map.put("payCode", payCode);
        

        return (Babasicamt) getSqlMapClientTemplate().queryForObject("BABASICAMT.selectSingleForUpdateData", map);
    }
    
    /**
     * 更新資料到 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>)<br>
     * 
     * @param data <code>Babasicamt</code> 物件
     */
    public int updateData(Babasicamt data) {
        return getSqlMapClientTemplate().update("BABASICAMT.updateData", data);
    }    
    
    /**
     * 依傳入的條件取得 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>) 的資料<br>
     *
     *@param  PAYCODE
     *@param  CPIYEAR1
     *@param  CPIYEAR2
     * @return <code>Babasicamt</code> 物件
     */
    @DaoFieldList("PAYCODE,CPIYEAR1,CPIYEAR2")
    public Babasicamt selectSingleForCheckSaveData(String payCode,String cpiYear1,String cpiYear2) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(cpiYear1))
            map.put("cpiYear1",cpiYear1);
        if (StringUtils.isNotBlank(cpiYear2))
            map.put("cpiYear2",cpiYear2);
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode",payCode);
        

        return (Babasicamt) getSqlMapClientTemplate().queryForObject("BABASICAMT.selectSingleForCheckSaveData", map);
    }
    
    /**
     * 取得給付年月對應之基本金額
     * @param payCode
     * @return
     */
    public List<Babasicamt> selectBasicAmtBy(String payCode) {
        return getSqlMapClientTemplate().queryForList("BABASICAMT.selectBasicAmtBy", payCode);
    }
    
    /**
     * 依傳入的條件取得 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>) 的資料<br>
     * 
     * @param payCode 給付別
     * @param payYm 給付年月
     * @return <code>BigDecimal</code> 物件
     */
    public BigDecimal selectBasicAmtForPaymentQuery(String payCode, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("payYm", payYm);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BABASICAMT.selectBasicAmtForPaymentQuery", map);
    }
}
