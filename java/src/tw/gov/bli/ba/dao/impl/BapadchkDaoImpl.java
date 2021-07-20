package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapadchkDao;
import tw.gov.bli.ba.domain.Bacountry;
import tw.gov.bli.ba.domain.Bapadchk;
import tw.gov.bli.ba.maint.cases.CheckMarkMaintCase;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

import java.util.*;

/**
 * DAO for 編審註記參數檔 (<code>BAPADCHK</code>)
 * 
 * @author swim
 */
@DaoTable("BAPADCHK")
public class BapadchkDaoImpl extends SqlMapClientDaoSupport implements BapadchkDao {
    /**
     * 依傳入的條件取得 編審註記參數檔 (<code>BAPADCHK</code>) 的資料<br>
     * 
     * @param chkObj 給付對象
     * @param chkGroup 編審註記類別
     * @param chkCode 編審註記代號
     * 
     * @return <code>List<AdviceMaintCase></code> 物件
     */
    @DaoFieldList("CHKOBJ,CHKGROUP,CHKCODE")
    public List<Bapadchk> selectData(String chkObj, String chkGroup, String chkCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(chkObj))
            map.put("chkObj", chkObj);
        if (StringUtils.isNotBlank(chkGroup))
            map.put("chkGroup", chkGroup);
        if (StringUtils.isNotBlank(chkCode))
            map.put("chkCode", chkCode);

        return (List<Bapadchk>) getSqlMapClientTemplate().queryForList("BAPADCHK.selectData", map);
    }

    /**
     * 新增資料到 編審註記參數檔 (<code>BAPADCHK</code>)<br>
     * 
     * @param data <code>Bapadchk</code> 物件
     */
    public BigDecimal insertData(Bapadchk data) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPADCHK.insertData", data);
    }

    /**
     * 更新資料到 編審註記參數檔 (<code>BAPADCHK</code>)<br>
     * 
     * @param data <code>Bapadchk</code> 物件
     */
    public int updateData(Bapadchk data) {
        return getSqlMapClientTemplate().update("BAPADCHK.updateData", data);
    }

    /**
     * 依傳入的條件取得 編審註記參數檔 (<code>BAPADCHK</code>) 的資料<br>
     * 
     * @param chkTyp 編審註記種類
     * @param chkObj 給付對象
     * @param chkGroup 編審註記類別
     * @param chkCode 資料區分
     * @param valibDate 資料區分
     * 
     * @return <code>Bapadchk</code> 物件
     */
    @DaoFieldList("CHKTYP,CHKOBJ,CHKGROUP,CHKCODE,VALIBDATE")
    public Bapadchk selectSingleForUpdateData(String chkTyp, String chkObj, String chkGroup, String chkCode, String valibDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(chkTyp))
            map.put("chkTyp", chkTyp);
        if (StringUtils.isNotBlank(chkObj))
            map.put("chkObj", chkObj);
        if (StringUtils.isNotBlank(chkGroup))
            map.put("chkGroup", chkGroup);
        if (StringUtils.isNotBlank(chkCode))
            map.put("chkCode", chkCode);
        if (StringUtils.isNotBlank(valibDate))
            map.put("valibDate", valibDate);

        return (Bapadchk) getSqlMapClientTemplate().queryForObject("BAPADCHK.selectSingleForUpdateData", map);
    }
}
