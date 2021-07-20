package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Baproceduredtl;

/**
 * DAO for 資料庫處理程序明細檔 (<code>BAPROCEDUREDTL</code>)
 * 
 * @author KIYOMI
 */
public interface BaproceduredtlDao {
    
    /**
     * 取得 資料庫處理程序主檔(<code>BAPROCEDUREDTL</code>) 
     * 
     * @param procName
     * @return 內含 <code>Baproceduredtl</code> 物件的 List
     */
    public List<Baproceduredtl> selectListBy(String procName);
    
    /**
     * 取得 資料庫處理程序主檔(<code>BAPROCEDUREDTL</code>) 
     * 
     * @param procName
     * @return 內含 <code>Baproceduredtl</code> 物件的 List
     */
    public List<Baproceduredtl> selectParametersNameBy(String procName);    

   
}
