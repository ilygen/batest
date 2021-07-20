package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Bapandomk;

/**
 * DAO for 處理註記參數檔 (<code>BAPANDOMK</code>)
 * 
 * @author jerry
 */
public interface BapandomkDao {
    /**
     * 依傳入的條件取得 處理註記參數檔 (<code>BAPANDOMK</code>) 的資料<br>
     * 
     * @param letterType 函別
     * @param lpaymk 老年給付註記
     * @param kpaymk 失能給付註記
     * @param spaymk 遺屬給付註記
     * @return 內含 <code>BAPANDOMK</code> 物件的 List
     */
    public List<Bapandomk> selectListBy(String letterType, String lpaymk, String kpaymk, String spaymk);
    
    /**
     * 依傳入的條件取得 處理註記參數檔 (<code>BAPANDOMK</code>) 的資料<br>
     * 
     * @param letterType 函別
     * @param ndomk1 處理註記
     * @param lpaymk 老年給付註記
     * @param kpaymk 失能給付註記
     * @param spaymk 遺屬給付註記
     * @return 內含 <code>BAPANDOMK</code> 物件的 List
     */
    public String selectDataForAuditRpt01By(String letterType,String ndomk1, String lpaymk, String kpaymk, String spaymk);
}
