package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.KcafDao;
import tw.gov.bli.ba.domain.Kcaf;


public class KcafDaoImpl extends SqlMapClientDaoSupport implements KcafDao{
    /**
     * 依傳入的條件查詢 被保險人關鍵欄位變更檔 (<code>KCAF</code>) 的資料 FOR 註記BC
     * 
     * @param aIdNo  改後身分證號 
     * @param aBrDte 改後出生日期
     * @return
     */
    public List<Kcaf> selectByAIdnAndABrDte(String aIdn,String aBrDte){
        HashMap<String,String> map =new HashMap<String,String>();
        map.put("aIdn", aIdn);
        map.put("aBrDte", aBrDte);
        return (List<Kcaf>) getSqlMapClientTemplate().queryForList("KCAF.selectByBIdnoAndBBrDte", map);
    }
}
