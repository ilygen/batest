package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import tw.gov.bli.ba.domain.Baban;

/**
 * DAO for 查調病歷檔 (<code>BABAN</code>)
 * 
 * @author Evelyn Hsu
 */

public interface BabanDao {
    
    /**
     * 依傳入條件取得 處理函別(<code>BABAN</code>) 更新條件
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * 
     * @return
     */
    public Integer getBabanCount(String apNo, String issuYm, String proDte, String clinNo);
    
    /**
     * 依傳入條件取得 處理函別(<code>BABAN</code>) 更新條件
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * 
     * @return
     */
    public String getBabanPayMk(String apNo, String issuYm, String proDte, String clinNo);
    
    /**
     * 新增 查調病歷檔(<code>BABAN</code>) 資料
     * 
     * @param BABAN 行政支援記錄檔
     */
    public BigDecimal insertData(Baban baban);
    
    /**
     *更新 查調病歷檔(<code>BABAN</code>) 資料
     * 
     * @param BABAN 行政支援記錄檔
     */
    public void updateData(Baban baban);

}
