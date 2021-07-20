package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.CvldtlDao;
import tw.gov.bli.ba.domain.Cvldtl;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 戶政全戶檔 (<code>CVLDTL</code>)
 * 
 * @author Rickychi
 */

@DaoTable("CVLDTL")
public class CvldtlDaoImpl extends SqlMapClientDaoSupport implements CvldtlDao {
    /**
     * 依傳入條件取得 戶政全戶檔(<code>CVLDTL</code>) 資料清單
     * 
     * @param idn 事故者身分證號
     * @param ebDate 出生日期
     * @return 內含 <code>Cvldtl</code> 物件的 List
     */
    @DaoFieldList("IDN, EBDATE")
    public List<Cvldtl> selectDataBy(String idn, String ebDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(idn)) {
            map.put("idn", idn);
        }
        else {
            map.put("idn", "");
        }
        if (StringUtils.isNotBlank(ebDate)) {
            map.put("ebDate", ebDate);
        }
        else {
            map.put("ebDate", "");
        }
        return getSqlMapClientTemplate().queryForList("CVLDTL.selectDataBy", map);
    }
    
    /**
     * 依傳入條件取得 戶政全戶檔(<code>CVLDTL</code>) 資料清單
     * 
     * @param Evtids 
     * @return String
     */
    public Cvldtl selectHaddrBy(String evtIds) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("evtIds", evtIds);
        
        return (Cvldtl) getSqlMapClientTemplate().queryForObject("CVLDTL.selectHaddrBy", map);
    }
    
    /**
     * Call StoreProcedure
     * 
     * @param NPIDS = Evtids
     * @param addrdiff
     * @return 
     */
    public Cvldtl selectInfoBy(String evtids, String addrdiff) {
        
    	Cvldtl cvldtlData = new Cvldtl();
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("p_npids",  evtids);
        map.put("p_addrdiff",  addrdiff);
        //return (Cvldtl)getSqlMapClientTemplate().queryForObject("CVLDTL.selectInfoBy", map);
        getSqlMapClientTemplate().queryForObject("CVLDTL.selectInfoBy", map);
        cvldtlData.setV_commaddr(map.get("p_addr"));
        cvldtlData.setV_commzip(map.get("p_zipcode"));
        return cvldtlData;
    }  
    /**
     * 依傳入條件取得 郵遞區號
     * 
     * @param apNo 受理編號
     * @return
     */
    public String selectZipCodeData(String addr){
    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("addr", addr);
        
        return (String) getSqlMapClientTemplate().queryForObject("CVLDTL.selectZipCodeData", map);
    }
    
    //Added By LouisChange 20200311 begin
    /**
     * 依傳入條件取得 戶政全戶檔(<code>CVLDTL</code>) 資料清單
     * 
     * @param idn 事故者身分證號
     * @param ebDate 出生日期
     * @return 內含 <code>Cvldtl</code> 物件的 List
     */
    @DaoFieldList("IDN, EBDATE")
    public List<Cvldtl> selectRmpNameBy(String idn, String ebDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(idn)) {
            map.put("idn", idn);
        }
        else {
            map.put("idn", "");
        }
        if (StringUtils.isNotBlank(ebDate)) {
            map.put("ebDate", ebDate);
        }
        else {
            map.put("ebDate", "");
        }
        return getSqlMapClientTemplate().queryForList("CVLDTL.selectRmpNameBy", map);
    }
    //Added By LouisChange 20200311 end
}
