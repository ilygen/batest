package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapandomkDao;
import tw.gov.bli.ba.domain.Bapandomk;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOImpl for 處理註記參數檔 (<code>BAPANDOMK</code>)
 * 
 * @author jerry
 */
@DaoTable("BAPANDOMK")
public class BapandomkDaoImpl extends SqlMapClientDaoSupport implements BapandomkDao{

    /**
     * 依傳入的條件取得 處理註記參數檔 (<code>BAPANDOMK</code>) 的資料<br>
     * 
     * @param letterType 函別
     * @param lpaymk 老年給付註記
     * @param kpaymk 失能給付註記
     * @param spaymk 遺屬給付註記
     * @return 內含 <code>BAPANDOMK</code> 物件的 List
     */
    @DaoFieldList("LETTERTYPE,LPAYMK,KPAYMK,SPAYMK")
    public List<Bapandomk> selectListBy(String letterType, String lpaymk, String kpaymk, String spaymk) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(letterType))
            map.put("letterType", letterType);
        if (StringUtils.isNotBlank(lpaymk))
            map.put("lpaymk", lpaymk);
        if (StringUtils.isNotBlank(kpaymk))
            map.put("kpaymk", kpaymk);
        if (StringUtils.isNotBlank(spaymk))
            map.put("spaymk", spaymk);

        return getSqlMapClientTemplate().queryForList("BAPANDOMK.selectDataBy", map);
    }
    
    /**
     * 依傳入的條件取得 處理註記參數檔 (<code>BAPANDOMK</code>) 的資料<br>
     * 
     * @param letterType 函別
     * @param lpaymk 老年給付註記
     * @param kpaymk 失能給付註記
     * @param spaymk 遺屬給付註記
     * @return 內含 <code>BAPANDOMK</code> 物件的 List
     */
    public String selectDataForAuditRpt01By(String letterType,String ndomk1, String lpaymk, String kpaymk, String spaymk) {
    	 HashMap<String, String> map = new HashMap<String, String>();

         if (StringUtils.isNotBlank(letterType))
             map.put("letterType", letterType);
         if (StringUtils.isNotBlank(ndomk1))
             map.put("ndomk1", ndomk1);
         if (StringUtils.isNotBlank(lpaymk))
             map.put("lpaymk", lpaymk);
         if (StringUtils.isNotBlank(kpaymk))
             map.put("kpaymk", kpaymk);
         if (StringUtils.isNotBlank(spaymk))
             map.put("spaymk", spaymk);

         return (String) getSqlMapClientTemplate().queryForObject("BAPANDOMK.selectDataForAuditRpt01By", map);
    }
}
