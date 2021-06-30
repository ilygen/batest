package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Baprocedure;

/**
 * DAO for 資料庫處理程序主檔 (<code>BAPROCEDURE</code>)
 * 
 * @author KIYOMI
 */
public interface BaprocedureDao {
    
    /**
     * 取得 資料庫處理程序主檔(<code>BAPROCEDURE</code>) 
     * 
     * @param 
     * @return 內含 <code>Baprocedure</code> 物件的 List
     */
    public List<Baprocedure> selectListBy();

   
}
