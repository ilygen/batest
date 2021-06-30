package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BaproceduredataDao;
import tw.gov.bli.ba.domain.Baproceduredata;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 資料庫處理程序明細檔 (<code>BAPROCEDUREDATA</code>)
 * 
 * @author KIYOMI
 */
@DaoTable("BAPROCEDUREDTL")
public class BaproceduredataDaoImpl extends SqlMapClientDaoSupport implements BaproceduredataDao {
    
    /**
     * 批次程式執行作業
     * 
     * @param baJobId
     * @param procedureName
     * @param seqNo
     * @param paramValue
     * @param creUser
     * @param creDatetime
     */
    public void insertProcedureData(Baproceduredata baproceduredata) {
        if (baproceduredata != null)
            getSqlMapClientTemplate().insert("BAPROCEDUREDATA.insertProcedureData", baproceduredata);
    }

    /**
     * 取得 資料庫處理程序資料檔(<code>BAPROCEDUREDATA</code>) 
     * 
     * @param jobId
     * @return 內含 <code>Baproceduredata</code> 物件的 List
     */
    public List<Baproceduredata> getRunProcedureDataBy(String jobId) {
        HashMap<String, String> map = new HashMap<String, String>();
        
        map.put("baJobId", jobId);
        return getSqlMapClientTemplate().queryForList("BAPROCEDUREDATA.selectProcedureDataBy", map);        
    }
       
}
