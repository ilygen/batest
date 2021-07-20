package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapadcslvlDao;
import tw.gov.bli.ba.domain.Bapadchk;
import tw.gov.bli.ba.domain.Bapadcslvl;
import tw.gov.bli.ba.maint.cases.CheckMarkMaintCase;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 決行層級參數檔 (<code>BAPADCSLVL</code>)
 * 
 * @author swim
 */
@DaoTable("BAPADCSLVL")
public class BapadcslvlDaoImpl extends SqlMapClientDaoSupport implements BapadcslvlDao{

    /**
     * 依傳入的條件取得 決行層級參數檔 (<code>BAPADCSLVL</code>) 的資料<br>
     * 
     * @param payCode 給付別
     * @param payKind 給付種類
     * @param rechklvl 決行層級代碼
     * 
     * @return <code>List<Bapadcslvl></code> 物件
     */
    @DaoFieldList("PAYCODE,PAYKIND,RECHKLVL")
    public List<Bapadcslvl> selectData(String payCode,String payKind,String rechklvl) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(payKind))
            map.put("payKind", payKind);
        if (StringUtils.isNotBlank(rechklvl))
            map.put("rechklvl", rechklvl);
        
        return (List<Bapadcslvl>) getSqlMapClientTemplate().queryForList("BAPADCSLVL.selectData", map);
    }
    
    /**
     * 新增資料到 決行層級參數檔 (<code>BAPADCSLVL</code>)<br>
     * 
     * @param data <code>Bapadcslvl</code> 物件
     */
    public BigDecimal insertData(Bapadcslvl data) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPADCSLVL.insertData", data);
    }
    
    /**
     * 更新資料到 決行層級參數檔 (<code>BAPADCSLVL</code>)<br>
     * 
     * @param data <code>Bapadcslvl</code> 物件
     */
    public int updateData(Bapadcslvl data) {
        return getSqlMapClientTemplate().update("BAPADCSLVL.updateData", data);
    }
    
    /**
     * 依傳入的條件取得 決行層級參數檔 (<code>BAPADCSLVL</code>) 的資料<br>
     * 
     * @param payCode 給付別
     * @param payKind 給付種類
     * @param rechklvl 決行層級代碼
     * 
     * @return <code>Bapadcslvl</code> 物件
     */
    @DaoFieldList("PAYCODE,PAYKIND,RECHKLVL")
    public Bapadcslvl selectSingleForUpdateData(String payCode,String payKind,String rechklvl) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(payKind))
            map.put("payKind", payKind);
        if (StringUtils.isNotBlank(rechklvl))
            map.put("rechklvl", rechklvl);
        
        return (Bapadcslvl) getSqlMapClientTemplate().queryForObject("BAPADCSLVL.selectData", map);
    }
    
    /**
     * 刪除 決行層級參數檔 (<code>BAPADCSLVL</code>) 的資料<br>
     * 
     * @param <code>Bapadcslvl</code> 物件
     */
    public void deleteData(Bapadcslvl detailData) {
        getSqlMapClientTemplate().delete("BAPADCSLVL.deleteData", detailData);
    }
}
