package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BabanDao;
import tw.gov.bli.ba.domain.Baban;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAOImpl for 查調病歷檔 (<code>BABAN</code>)
 * 
 * @author Evelyn Hsu
 */

public class BabanDaoImpl extends SqlMapClientDaoSupport implements BabanDao{

    /**
     * 依傳入條件取得 處理函別(<code>BABAN</code>) 更新條件
     * 
     * @param apNo 受理編號
     * @param proDte 承辦日期
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Integer getBabanCount(String apNo, String issuYm, String proDte, String clinNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        map.put("proDte", proDte);
        map.put("clinNo", clinNo);
        return (Integer) getSqlMapClientTemplate().queryForObject("BABAN.getBabanCount", map);
    }
    
    /**
     * 依傳入條件取得 處理函別(<code>BABAN</code>) 更新條件
     * 
     * @param apNo 受理編號
     * @param proDte 承辦日期
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public String getBabanPayMk(String apNo, String issuYm, String proDte, String clinNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        map.put("proDte", proDte);
        map.put("clinNo", clinNo);
        
        return (String) getSqlMapClientTemplate().queryForObject("BABAN.getBabanPayMk", map);
    }
    
    /**
     * 新增 查調病歷檔(<code>BABAN</code>) 資料
     * 
     * @param baban <code>Baban</code> 物件
     */
    public BigDecimal insertData(Baban baban) {
        return (BigDecimal)getSqlMapClientTemplate().insert("BABAN.insertData", baban);
    }
    
    /**
     * 更新 查調病歷檔(<code>BABAN</code>) 資料
     * 
     * @param baban <code>Baban</code> 物件
     */
    public void updateData(Baban baban) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", baban.getApNo());
        map.put("proDate", DateUtility.changeDateType(baban.getProDte()));
        map.put("clinNo", baban.getClinNo());
       getSqlMapClientTemplate().update("BABAN.updateData", baban);
    }

}
