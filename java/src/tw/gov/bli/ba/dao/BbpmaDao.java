package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Bbpma;

/**
 * DAO for 給付主檔 (<code>BAAPPBASE</code>)
 * 
 * @author Rickychi
 */
public interface BbpmaDao {

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬年金紀錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @return
     */
    public List<Bbpma> getOldAgeReviewRpt01SurvivorAnnuityPayListBy(String evtIdnNo);

}
