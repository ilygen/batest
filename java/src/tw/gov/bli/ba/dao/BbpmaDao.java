package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bbpma;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 給付主檔 (<code>BBPMA</code>)
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

	/**
	 * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
	 * 
	 * @param apNo 受理編號
	 * @param evtIdnNo 事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return
	 */
	List<Bbpma> selectSurvivorReviewRpt01DisablePayListBy(String apNo, String evtIdnNo, String evtBrDate);

	/**
	 * 依傳入的條件取得 給付主檔(<code>BBPMA</code>) 年金給付資料 for 災保失能年金給付受理編審清單
	 * 
	 * @param apNo 受理編號
	 * @param evtIdnNo 事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return
	 */
	List<Bbpma> getDisasterReviewRpt01AnnuityPayListBy(String apNo, String evtIdnNo, String evtBrDate);

	/**
	 * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬年金年金給付資料 for 勞保失能年金給付受理編審清單
	 * 
	 * @param apNo 受理編號
	 * @param evtIdnNo 事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return
	 */
	List<Bbpma> getDisasterReviewRpt01SurvivorPayListBy(String apNo, String evtIdnNo, String evtBrDate);

	/**
	 * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
	 * 
	 * @param apNo 受理編號
	 * @param evtIdnNo 事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return
	 */
	List<Bbpma> selectDisasterReviewRpt01AnnuityPayListBy(String apNo, String evtIdnNo, String evtBrDate);

}
