package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BacpidtlDao;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * Dao for 物價指數調整明細檔 (<code>BACPIDTL</code>)
 * 
 * @author Kiyomi
 */
@DaoTable("BACPIDTL")
public class BacpidtlDaoImpl extends SqlMapClientDaoSupport implements BacpidtlDao {

    /**
     * 依傳入的條件取得物價指數調整明細檔 (<code>BACPIDTL</code>) 的資料
     * 
     * @param issuYear 核定年度
     * @return <code>List<CpiDtlMaintCase></code> 物件
     */
    @DaoFieldList("ISSUYEAR")
    public List<Bacpidtl> selectData(String issuYear) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(issuYear))
            map.put("issuYear", issuYear);

        return (List<Bacpidtl>) getSqlMapClientTemplate().queryForList("BACPIDTL.selectDataBy", map);
    }

    /**
     * 依傳入的條件取得 物價指數調整明細檔 (<code>BACPIDTL</code>) 的資料<br>
     * 
     * @param ISSUYEAR
     * @return <code>Bacpidtl</code> 物件
     */
    @DaoFieldList("ISSUYEAR")
    public List<Bacpidtl> selectListForForCpiRec(String issuYear) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(issuYear))
            map.put("issuYear", issuYear);

        return (List<Bacpidtl>) getSqlMapClientTemplate().queryForList("BACPIDTL.selectListForForCpiRec", map);
    }

    /**
     * 依傳入的條件取得 物價指數調整明細檔 (<code>BACPIDTL</code>) 的資料<br>
     * 
     * @param ADJYEAR
     * @return <code>Bacpidtl</code> 物件
     */
    public Bacpidtl selectSingleForCheckSaveDataForDtl(String issuYear, String adjYear) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(issuYear))
            map.put("issuYear", issuYear);
        if (StringUtils.isNotBlank(adjYear))
            map.put("adjYear", adjYear);

        return (Bacpidtl) getSqlMapClientTemplate().queryForObject("BACPIDTL.selectSingleForCheckSaveDataForDtl", map);
    }

    /**
     * 取得 物價指數調整明細檔 (<code>BACPIDTL</code>) 的資料<br>
     * For 物價指數調整紀錄
     * 
     * @return <code>Bacpidtl</code> 物件
     */

    public List<Bacpidtl> selectSingleForCpiDltForCpiRecData() {

        return (List<Bacpidtl>) getSqlMapClientTemplate().queryForList("BACPIDTL.selectSingleForCpiDltForCpiRecData", null);
    }

    /**
     * 取得 物價指數調整明細檔 (<code>BACPIDTL</code>) 的資料<br>
     * For 物價指數調整紀錄
     * 
     * @return <code>Bacpidtl</code> 物件
     */
    public List<Bacpidtl> selectInsertDataForCpiRec(String issuYear) {

        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(issuYear))
            map.put("issuYear", issuYear);

        return (List<Bacpidtl>) getSqlMapClientTemplate().queryForList("BACPIDTL.selectInsertDataForCpiRec", map);
    }

    /**
     * 新增資料到 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * 
     * @param data <code>Bacpidtl</code> 物件
     */
    public BigDecimal insertData(Bacpidtl data) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BACPIDTL.insertData", data);
    }

    /**
     * 更新資料到 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * 
     * @param data <code>Bacpidtl</code> 物件
     */
    public int updateData(Bacpidtl data) {
        return getSqlMapClientTemplate().update("BACPIDTL.updateData", data);
    }

    /**
     * 刪除 物價指數調整明細檔 (<code>BACPIDTL</code>) 資料
     * 
     * @param cpiYear 指數年度
     * @return <code>BigDecimal</code>
     */
    public void deleteData(String issuYear, String eqType, String[] adjYear, String cpiYear, String cpiYearE, BigDecimal cpiB, BigDecimal cpiE, BigDecimal cpIndex) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("issuYear", issuYear);
        map.put("eqType", eqType);
        map.put("adjYear", adjYear);
        map.put("cpiYear", cpiYear);
        map.put("cpiYearE", cpiYearE);
        map.put("cpiB", cpiB);
        map.put("cpiE", cpiE);
        map.put("cpIndex", cpIndex);

        getSqlMapClientTemplate().update("BACPIDTL.deleteData", map);
    }

    /**
     * 更新資料到 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * 
     * @param data <code>Bacpidtl</code> 物件
     */
    public int updateAdjMk(Bacpidtl data) {
        return getSqlMapClientTemplate().update("BACPIDTL.updateAdjMk", data);
    }

    /**
     * 依傳入條件取得 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * SEQNO
     * 
     * @return <code>BigDecimal</code> 物件
     */
    public BigDecimal selectSeqNoForCpiDtl(Bacpidtl data) {
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BACPIDTL.selectSeqNoForCpiDtl", data);
    }

    /**
     * 依傳入條件取得 物價指數調整明細檔 (<code>BACPIDTL</code>)
     * 
     * @return <code>List<Bacpidtl></code> 物件
     */
    public List<Bacpidtl> selectAdjYearDataBy(Bacpidtl data) {
        return (List<Bacpidtl>) getSqlMapClientTemplate().queryForList("BACPIDTL.selectAdjYearDataBy", data);
    }

    /**
     * 依傳入條件取得 物價指數調整明細檔 (<code>BACPIDTL</code>)
     * 
     * @return <code>List<Bacpidtl></code> 物件
     */
    public List<Bacpidtl> selectUpdDelDataBy(Bacpidtl data) {
        return (List<Bacpidtl>) getSqlMapClientTemplate().queryForList("BACPIDTL.selectUpdDelDataBy", data);
    }

    /**
     * 依傳入條件取得 物價指數調整明細檔 (<code>BACPIDTL</code>)
     * 
     * @return <code>List<Bacpidtl></code> 物件
     */
    public List<Bacpidtl> selectDetailUserDataBy(Bacpidtl data) {
        return (List<Bacpidtl>) getSqlMapClientTemplate().queryForList("BACPIDTL.selectDetailUserDataBy", data);
    }

}
