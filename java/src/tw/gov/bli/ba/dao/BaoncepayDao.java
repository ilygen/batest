package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Baoncepay;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * Dao for 一次請領給付資料檔 (<code>BAONCEPAY</code>)
 * 
 * @author Rickychi
 */
public interface BaoncepayDao {
    /**
     * 依傳入條件取得 一次請領給付資料檔(<code>BADAPR</code>) 一次給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>StopPaymentProcessCase</code> 物件的 List
     */
    public List<Baoncepay> selectOncePayDataBy(String apNo);

    /**
     * 依傳入條件取得 一次請領給付資料檔(<code>BADAPR</code>) 一次給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return <code>Baoncepay</code> 物件
     */
    public Baoncepay getOldAgeReviewRpt01DieOncePayDataBy(String apNo);
    
}
