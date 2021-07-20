package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.Bbcmf09Dao;
import tw.gov.bli.ba.dao.Bbcmf29Dao;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 出生日期錯誤參數檔 (<code>BBCMF29</code>)
 * 
 * @author Noctis
 */
@DaoTable("BBCMF29")
public class Bbcmf29DaoImpl extends SqlMapClientDaoSupport implements Bbcmf29Dao {
	/**
     * 取得 現金給付分案尾碼原則檔(<code>BBCMF09</code>) 承辦人分機號碼資料
     * 
     * @param prpNo 員工編號
     * @param apNo 受理編號
     * @return <code>data2</code> String物件
     */
    public String checkIdnoExist(String idNo,String brDte) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("idNo", idNo);
        map.put("brDte", brDte);
        return (String) getSqlMapClientTemplate().queryForObject("BBCMF29.checkIdnoExist", map);
    }

   
}
