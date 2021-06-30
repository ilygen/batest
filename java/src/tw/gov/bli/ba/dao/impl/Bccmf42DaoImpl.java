package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.Bccmf42Dao;
import tw.gov.bli.ba.domain.Bccmf42;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 保密資料檔 (<code>BCCMF42</code>)
 * 
 * @author Noctis
 */
@DaoTable("BCCMF42")
public class Bccmf42DaoImpl extends SqlMapClientDaoSupport implements Bccmf42Dao {
	/**
     * 取得 保密資料檔(<code>BCCMF42</code>) 是否有保密資料
     * 
     * @param idnNo 身分證號
     * @return <code>data</code> String物件
     */
    public BigDecimal checkIdnNoExist(String idnNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        
        map.put("idnNo", idnNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BCCMF42.checkIdnNoExist", map);
    }
    
    /**
     * 取得 保密資料檔(<code>BCCMF42</code>) payTyp
     * 
     * @param idnNo 身分證號
     * @return <code>data</code> String物件
     */
    public Bccmf42 selectPayTypBy(String idnNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        
        map.put("idnNo", idnNo);

        return (Bccmf42) getSqlMapClientTemplate().queryForObject("BCCMF42.selectPayTypBy", map);
    }

   
}
