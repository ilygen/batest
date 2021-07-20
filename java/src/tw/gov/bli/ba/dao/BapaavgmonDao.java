package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baban;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.domain.Bapaavgmon;
import tw.gov.bli.ba.domain.Bapacut;
import tw.gov.bli.ba.maint.cases.AvgAmtMonMaintCase;

/**
 * DAO for  平均薪資月數  (<code>BAPAAVGMON</code>)
 * 
 * @author Noctis
 */

public interface BapaavgmonDao {
    
    /**
     * 取得  平均薪資月數(<code>BAPAAVGMON</code>)
     * 
     * @return
     */
    public Integer getBapaavgmonCount();
    
    /**
     * 取得  平均薪資月數(<code>BAPAAVGMON</code>)
     * 
     * @return
     */
    public String getBapaavgmonAppMonth(String appDate);
    
    /**
     * 依傳入條件取得 平均薪資月數檔(<code>BAPAAVGMON</code>) 資料清單
     * 
     * @param effYear 施行年度
     * @return 內含 <code>Bapaavgmon</code> 物件的 List
     */
    public List<Bapaavgmon> selectBapaavgmonDataListForAvgAmtMonMaintBy(String effYear);
    
    /**
     * 刪除 平均薪資檔 (<code>BAPAAVGMON</code>) 資料
     * 
     * @param effYear 施行年度
     * @return <code>BigDecimal</code>
     */
    public void deleteData(String effYear);
    
    /**
     * 新增資料到 平均薪資月數檔 (<code>BAPAAVGMON</code>)<br>
     * 
     * @param data <code>Bapaavgmon</code> 物件
     */
    public void insertData(Bapaavgmon data);
    
    /**
     * 更新資料到 平均薪資月數檔 (<code>BAPAAVGMON</code>)<br>
     * 
     * @param data <code>Bapaavgmon</code> 物件
     */
    public int updateData(Bapaavgmon data);   
}
