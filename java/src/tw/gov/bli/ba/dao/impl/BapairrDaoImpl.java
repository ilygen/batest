package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BabanDao;
import tw.gov.bli.ba.dao.BapairrDao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baban;
import tw.gov.bli.ba.domain.Bapacut;
import tw.gov.bli.ba.domain.Bapairr;
import tw.gov.bli.ba.maint.cases.AvgAmtMonMaintCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for  勞保年金所得替代率參數檔  (<code>BAPAIRR</code>)
 * 
 * @author Noctis
 */

public class BapairrDaoImpl extends SqlMapClientDaoSupport implements BapairrDao{

    
    /**
     * 依傳入條件取得  勞保年金所得替代率參數檔(<code>BAPAIRR</code>) 資料清單
     * 
     * @param effYear 施行年度
     * @return 內含 <code>Bapairr</code> 物件的 List
     */
    @DaoFieldList("EFFYM,PAYCODE")
    public List<Bapairr> selectBapairrDataForReplacementRatioMaintBy(String effYm, String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();
     
        map.put("effYm", effYm);
        map.put("payCode", payCode);
      
        return getSqlMapClientTemplate().queryForList("BAPAIRR.selectBapairrDataForReplacementRatioMaintBy", map);
    }
    
    /**
     * 刪除 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>) 資料
     * 
     * @param effYm 施行年月
     * @return <code>BigDecimal</code>
     */
    public void deleteData(String effYm, String payCode) {
    	HashMap<String, String> map = new HashMap<String, String>();
        
        map.put("effYm", effYm);
        map.put("payCode", payCode);
        getSqlMapClientTemplate().update("BAPAIRR.deleteData", map);
    }    
    
    /**
     * 新增資料到 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br>
     * 
     * @param data <code>Bapairr</code> 物件
     */
    public void insertData(Bapairr data) {
        if (data != null)
            getSqlMapClientTemplate().insert("BAPAIRR.insertData", data);
    }
    
    /**
     * 新增資料到 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br> For 失能遺屬
     * 
     * @param data <code>Bapairr</code> 物件
     */
    public void insertDataForDisabledSurvivor(Bapairr data) {
        if (data != null)
            getSqlMapClientTemplate().insert("BAPAIRR.insertDataForDisabledSurvivor", data);
    }
    
    /**
     * 更新資料到 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br>
     * 
     * @param data <code>Bapairr</code> 物件
     */
    public int updateData(Bapairr data) {
        return getSqlMapClientTemplate().update("BAPAIRR.updateData", data);
    }    
    
    /**
     * 更新資料到 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br>  For 失能遺屬
     * 
     * @param data <code>Bapairr</code> 物件
     */
    public int updateDataForDisabledSurvivor(Bapairr data) {
        return getSqlMapClientTemplate().update("BAPAIRR.updateDataForDisabledSurvivor", data);
    }    
    
}
