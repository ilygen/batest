package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.Bbcmf09Dao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bbcmf09;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 現金給付分案尾碼原則檔 (<code>BBCMF09</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BBCMF09")
public class Bbcmf09DaoImpl extends SqlMapClientDaoSupport implements Bbcmf09Dao {
    /**
     * 取得 現金給付分案尾碼原則檔(<code>BBCMF09</code>) 承辦人分機號碼資料
     * 
     * @param prpNo 員工編號
     * @param apNo 受理編號
     * @return <code>data2</code> String物件
     */
    @DaoFieldList("APNO")
    public String selectData2By(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (String) getSqlMapClientTemplate().queryForObject("BBCMF09.selectData2By", map);
    }

    /**
     * 取得 現金給付分案尾碼原則檔(<code>BBCMF09</code>) 員工編號
     * 
     * @param apNo 受理編號
     * @return <code>prpNo</code> String物件
     */
    @DaoFieldList("APNO")
    public String selectPrpNoBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (String) getSqlMapClientTemplate().queryForObject("BBCMF09.selectPrpNoBy", map);
    }
    
    /**
     * 取得 現金給付分案尾碼原則檔(<code>BBCMF09</code>) 詳細資料
     * 
     * @param apNo 受理編號
     * @return <code>prpNo</code> String物件
     */
    @DaoFieldList("APNO")
    public Bbcmf09 selectComTelForMonthlyRpt05By(String apNo) {
    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (Bbcmf09) getSqlMapClientTemplate().queryForObject("BBCMF09.selectComTelForMonthlyRpt05By", map);
    }
}
