package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BabanDao;
import tw.gov.bli.ba.dao.BapaavgmonDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baban;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.domain.Bapaavgmon;
import tw.gov.bli.ba.domain.Bapacut;
import tw.gov.bli.ba.maint.cases.AvgAmtMonMaintCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for  平均薪資月數  (<code>BAPAAVGMON</code>)
 * 
 * @author Noctis
 */

public class BapaavgmonDaoImpl extends SqlMapClientDaoSupport implements BapaavgmonDao{

	 /**
     * 取得  平均薪資月數(<code>BAPAAVGMON</code>)
     * 
     * @return
     */
    public Integer getBapaavgmonCount() {
       
        return (Integer) getSqlMapClientTemplate().queryForObject("BAPAAVGMON.getBapaavgmonCount");
    }
    
    /**
     * 取得  平均薪資月數(<code>BAPAAVGMON</code>)
     * 
     * @return
     */
    public String getBapaavgmonAppMonth(String appDate) {
    	HashMap<String, String> map = new HashMap<String, String>();
        
        map.put("appDate", appDate);
        return (String) getSqlMapClientTemplate().queryForObject("BAPAAVGMON.getBapaavgmonAppMonth", map);
    }
    
    /**
     * 依傳入條件取得  平均薪資檔(<code>BAPAAVGMON</code>) 資料清單
     * 
     * @param effYear 施行年度
     * @return 內含 <code>Bapaavgmon</code> 物件的 List
     */
    @DaoFieldList("EFFYEAR")
    public List<Bapaavgmon> selectBapaavgmonDataListForAvgAmtMonMaintBy(String effYear) {
        HashMap<String, String> map = new HashMap<String, String>();
     
        map.put("effYear", effYear);
      
        return getSqlMapClientTemplate().queryForList("BAPAAVGMON.selectBapaavgmonDataListForAvgAmtMonMaintBy", map);
    }
    
    /**
     * 刪除平均薪資檔 (<code>BAPAAVGMON</code>) 資料
     * 
     * @param effYear 施行年度
     * @return <code>BigDecimal</code>
     */
    public void deleteData(String effYear) {
        getSqlMapClientTemplate().update("BAPAAVGMON.deleteData", effYear);
    }    
    
    /**
     * 新增資料到 平均薪資月數檔 (<code>BAPAAVGMON</code>)<br>
     * 
     * @param data <code>Bapaavgmon</code> 物件
     */
    public void insertData(Bapaavgmon data) {
        if (data != null)
            getSqlMapClientTemplate().insert("BAPAAVGMON.insertData", data);
    }
    
    /**
     * 更新資料到 平均薪資月數檔 (<code>BAPAAVGMON</code>)<br>
     * 
     * @param data <code>Bapaavgmon</code> 物件
     */
    public int updateData(Bapaavgmon data) {
        return getSqlMapClientTemplate().update("BAPAAVGMON.updateData", data);
    }    
    
}
