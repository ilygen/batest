package tw.gov.bli.ba.dao;

import tw.gov.bli.ba.domain.Lnmd;

/**
 * DAO for 紓困貸款呆帳檔 (<code>LNMD</code>) <br>
 * 注意: 此檔的日期格式皆為民國日期
 * 
 * @author swim
 */
public interface LnmdDao {
    /**
     * 依傳入條件取得 紓困貸款呆帳檔 (<code>LNMD</code>) 資料 for 紓困貸款呆帳債務人照會單
     * 
     * @param idnBb 受款人身分證號
     * @param brdteBb 借款人出生日期 (傳入西元日期, DAO 實作會將其轉為民國日期)
     * @return
     */
    public Lnmd getDataUpdateRpt04DataBy(String idn_Bb, String brdte_Bb);
}
