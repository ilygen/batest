package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Bbcmf06;

/**
 * Dao for 資料名稱參數檔 (<code>BBCMF06</code>)
 * 
 * @author Goston
 */
public interface Bbcmf06Dao {
    /**
     * 依傳入的條件取得 資料名稱參數檔 (<code>BBCMF06</code>) 的資料
     * 
     * @param insKd 保險別
     * @param dataKd 資料種類
     * @param dataCd 資料代碼
     * @return
     */
    public List<Bbcmf06> selectListBy(String insKd, String dataKd, String dataCd);

    /**
     * 依傳入的條件取得 資料名稱參數檔 (<code>BBCMF06</code>) 失能項目 (<code>CRIINJDP</code>) 的資料
     * 
     * @param dataCd 資料代碼
     * @return
     */
    public List<Bbcmf06> selectCriInJdpListBy(String dataCd);
}
