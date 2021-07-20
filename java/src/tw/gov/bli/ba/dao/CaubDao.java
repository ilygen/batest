package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Caub;

/**
 * DAO for 投保單位檔 (<code>CAUB</code>)
 * 
 * @author jerry
 */
public interface CaubDao {

    /**
     * 依傳入條件取得 投保單位檔(<code>CAUB</code>) 資料清單
     * 
     * @param ubNo 保險證號
     */
    public String selectCaubName(String ubNo);

    /**
     * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 投保單位資料 for 勞保老年年金給付受理編審清單
     * 
     * @param ubNo 保險證號
     * @return
     */
    public Caub getOldAgeReviewRpt01UnitDataBy(String ubNo);
    
    /**
     * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 資料清單
     * 
     * @param ubNo 保險證號
     */
    public List<Caub> selectPayeeDataUpdateCaubData(String ubNo);
    
    /**
     * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 投保單位資料 for 勞保失能年金給付受理編審清單
     * 
     * @param ubNo 保險證號
     * @return
     */
    public Caub getDisableReviewRpt01UnitDataBy(String ubNo);
    
    /**
     * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 投保單位資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param ubNo 保險證號
     * @return
     */
    public Caub getSurvivorReviewRpt01UnitDataBy(String ubNo);    
    
    /**
     * 依傳入條件取得 投保單位檔 (<code>CAUB</code>) 單位類別資料 for 年金統計查詢
     * 
     * @param ubType 單位類別
     * @return
     */
    public List<Caub> qryUbTypeList();  
}
