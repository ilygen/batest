package tw.gov.bli.ba.dao;

import tw.gov.bli.ba.domain.Bamanager;

/**
 * DAO for 總經理署名圖 (<code>BXDIRNAMEIMG</code>)
 *
 * @author ttlin
 */
public interface BxdirnameimgDao {

    /**
     * 依傳入的條件取得 總經理署名圖(<code>BXDIRNAMEIMG</code>)
     *
     * @param type
     * @param dte
     * @return
     */
    public Bamanager selectFile(String type, String dte);
}
