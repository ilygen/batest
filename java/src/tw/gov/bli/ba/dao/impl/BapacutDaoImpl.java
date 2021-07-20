package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapacutDao;
import tw.gov.bli.ba.domain.Bapacut;
import tw.gov.bli.ba.domain.Bapainspect;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

import java.util.*;
/**
 * DAO for 先抽對象條件參數檔 (<code>BAPACUT</code>)
 * 
 * @author Ocean Cheng
 */
@DaoTable("BAPACUT")
public class BapacutDaoImpl extends SqlMapClientDaoSupport implements BapacutDao{
	/**
     * 依傳入的條件取得 扣減參數條件參數檔 (<code>BAPACUT</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @return <code>Bapacut</code> 物件
     */
	@DaoFieldList("PAYCODE")
    public List<Bapacut> selectData(String payCode) {
    	HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        return  (List<Bapacut>) getSqlMapClientTemplate().queryForList("BAPACUT.selectDataBy", map);
    }
    
	/**
     * 新增資料到 扣減參數條件參數檔 (<code>BAPACUT</code>)<br>
     * 
     * @param data <code>Bapacut</code> 物件
     */
    public void insertData(Bapacut data) {
        if (data != null)
            getSqlMapClientTemplate().insert("BAPACUT.insertData", data);
    }
    
    /**
     * 刪除 先抽對象條件參數檔 (<code>BAPACUT</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     */
    public void deleteData(String payCode) {
        getSqlMapClientTemplate().delete("BAPACUT.deleteData", payCode);
    }
    
}
