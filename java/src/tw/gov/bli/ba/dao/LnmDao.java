package tw.gov.bli.ba.dao;

import tw.gov.bli.ba.domain.Lnm;

/**
 * DAO for 貸款主檔 (<code>LNM</code>) <br>
 * 注意: 此檔的日期格式皆為民國日期
 * 
 * @author Goston
 */
public interface LnmDao {

    /**
     * 依傳入條件取得 貸款主檔 (<code>LNM</code>) 資料 for 勞工紓困貸款繳納本息情形查詢單
     * 
     * @param idnAply 身份證號
     * @param brDteAply 出生日期 (傳入西元日期, DAO 實作會將其轉為民國日期)
     * @return
     */
    public Lnm getDataUpdateRpt01DataBy(String idnAply, String brDteAply);
    
    /**
     * 依傳入條件取得 貸款主檔 (<code>LNM</code>) 資料 for 紓困貸款撥款情形查詢清單
     * 
     * @param idnAply 身份證號
     * @param brDteAply 出生日期 (傳入西元日期, DAO 實作會將其轉為民國日期)
     * @return
     */
    public Lnm getDataUpdateRpt02DataBy(String idnAply, String brDteAply);
}
