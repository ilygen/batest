package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baban;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.domain.Bapaavgmon;
import tw.gov.bli.ba.domain.Bapacut;
import tw.gov.bli.ba.domain.Bapairr;
import tw.gov.bli.ba.maint.cases.AvgAmtMonMaintCase;

/**
 * DAO for  勞保年金所得替代率參數檔  (<code>BAPAIRR</code>)
 * 
 * @author Noctis
 */

public interface BapairrDao {
    
    /**
     * 依傳入條件取得 平均薪資月數檔(<code>BAPAIRR</code>) 資料清單
     * 
     * @param effYm 施行年月
     * @return 內含 <code>Bapairr</code> 物件的 List
     */
    public List<Bapairr> selectBapairrDataForReplacementRatioMaintBy(String effYm, String payCode);
    
    /**
     * 刪除 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>) 資料
     * 
     * @param effYm 施行年月
     * @return <code>BigDecimal</code>
     */
    public void deleteData(String effYm, String payCode);
    
    /**
     * 新增資料到 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br>
     * 
     * @param data <code>Bapairr</code> 物件
     */
    public void insertData(Bapairr data);
    
    /**
     * 新增資料到 勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br> For 失能遺屬
     * 
     * @param data <code>Bapairr</code> 物件
     */
    public void insertDataForDisabledSurvivor(Bapairr data);
    
    /**
     * 更新資料到勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br>
     * 
     * @param data <code>Bapairr</code> 物件
     */
    public int updateData(Bapairr data);   
    
    /**
     * 更新資料到勞保年金所得替代率參數檔 (<code>BAPAIRR</code>)<br> For 失能遺屬
     * 
     * @param data <code>Bapairr</code> 物件
     */
    public int updateDataForDisabledSurvivor(Bapairr data);  
}
