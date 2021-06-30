package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.Bbcmf08Dao;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 現金給付分案尾碼原則檔 (<code>BBCMF08</code>)
 * 
 * @author swim
 */
@DaoTable("BBCMF08")
public class Bbcmf08DaoImpl extends SqlMapClientDaoSupport implements Bbcmf08Dao {
    /**
     * 取得 現金給付分案尾碼原則檔(<code>BBCMF08</code>) 承辦人分機號碼資料
     * 
     * @param gvCd1 員工編號
     * @return <code>data2</code> String物件
     */
    @DaoFieldList("GVCD1")
    public String selectBbcmf08DataBy(String gvCd1) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("gvCd1", gvCd1);

        return (String) getSqlMapClientTemplate().queryForObject("BBCMF08.selectBbcmf08DataBy", map);
    }
}
