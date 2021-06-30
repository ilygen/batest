package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BaproceduredtlDao;
import tw.gov.bli.ba.domain.Baproceduredtl;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 資料庫處理程序明細檔 (<code>BAPROCEDUREDTL</code>)
 * 
 * @author KIYOMI
 */
@DaoTable("BAPROCEDUREDTL")
public class BaproceduredtlDaoImpl extends SqlMapClientDaoSupport implements BaproceduredtlDao {
    
    /**
     * 取得 資料庫處理程序明細檔(<code>BAPROCEDUREDTL</code>) 
     * 
     * @param procName
     * @return 內含 <code>Baproceduredtl</code> 物件的 List
     */
    public List<Baproceduredtl> selectListBy(String procName) {
        HashMap<String, String> map = new HashMap<String, String>();
        
        map.put("procName", procName);
        return getSqlMapClientTemplate().queryForList("BAPROCEDUREDTL.selectParametersBy", map);        
    }
    
    /**
     * 取得 資料庫處理程序明細檔(<code>BAPROCEDUREDTL</code>) 
     * 
     * @param procName
     * @return 內含 <code>Baproceduredtl</code> 物件的 List
     */
    public List<Baproceduredtl> selectParametersNameBy(String procName) {
        HashMap<String, String> map = new HashMap<String, String>();
        
        map.put("procName", procName);
        return getSqlMapClientTemplate().queryForList("BAPROCEDUREDTL.selectParametersNameBy", map);        
    }    

   
}
