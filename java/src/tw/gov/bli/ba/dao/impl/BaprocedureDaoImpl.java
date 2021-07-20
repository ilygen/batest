package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BaprocedureDao;
import tw.gov.bli.ba.domain.Baprocedure;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 資料庫處理程序主檔 (<code>BAPROCEDURE</code>)
 * 
 * @author KIYOMI
 */
@DaoTable("BAPROCEDURE")
public class BaprocedureDaoImpl extends SqlMapClientDaoSupport implements BaprocedureDao {
    
    /**
     * 取得 資料庫處理程序主檔(<code>BAPROCEDURE</code>) 
     * 
     * @param 
     * @return 內含 <code>Baprocedure</code> 物件的 List
     */
    public List<Baprocedure> selectListBy() {
        HashMap<String, String> map = new HashMap<String, String>();
        
        //map.put("idnNo", idnNo);
        return getSqlMapClientTemplate().queryForList("BAPROCEDURE.selectProcedureNameBy", map);        
    }

   
}
