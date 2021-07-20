package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Baproceduredata;

/**
 * DAO for 資料庫處理程序資料檔 (<code>BAPROCEDUREDATA</code>)
 * 
 * @author KIYOMI
 */
public interface BaproceduredataDao {
    
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
    public void insertProcedureData(Baproceduredata baproceduredata);

    /**
     * 取得 資料庫處理程序資料檔(<code>BAPROCEDUREDATA</code>) 
     * 
     * @param jobId
     * @return 內含 <code>Baproceduredata</code> 物件的 List
     */
    public List<Baproceduredata> getRunProcedureDataBy(String jobId);
            
}
