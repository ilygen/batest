package tw.gov.bli.ba.dao;

import tw.gov.bli.ba.domain.Bacriinj;

/**
 * Dao for 國際疾病代碼參數檔 (<code>BACRIINJ</code>)
 * 
 * @author Goston
 */
public interface BacriinjDao {
    /**
     * 依傳入的條件取得 國際疾病代碼參數檔 (<code>BACRIINJ</code>) 的資料
     * 
     * @param criInJMk 國際疾病代碼
     * @return
     */
    public Bacriinj selectDataBy(String criInJMk);
}
