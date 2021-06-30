package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.Bccmf01Dao;
import tw.gov.bli.ba.dao.Bccmf42Dao;
import tw.gov.bli.ba.domain.Bccmf42;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 保密資料檔 (<code>BCCMF01</code>)
 * 
 * @author Noctis
 */
@DaoTable("BCCMF01")
public class Bccmf01DaoImpl extends SqlMapClientDaoSupport implements Bccmf01Dao {
	/**
     * 取得 保密資料檔(<code>BCCMF01</code>) 是否可查看保密資料
     * 
     * @param prpNo 身分證號
     * @return <code>data</code> String物件
     */
    public BigDecimal selectBccmf01CheckCount(String prpNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        
        map.put("prpNo", prpNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BCCMF01.selectBccmf01CheckCount", map);
    }

}
