package tw.gov.bli.ba.dao;

import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Biref;

/**
 * DAO for 就保給付檔 (<code>BIREF</code>)
 * 
 * @author Rickychi
 */
public interface BirefDao {
    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 申請失業給付紀錄資料
     * 
     * @param idNo 身分證號
     * @param brth 出生日期
     * @return 內含 <code>Biref</code> 物件的 List
     */
    public List<Biref> selectUnEmpDataBy(String idNo, String brth);

    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 申請失業給付記錄 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Biref> getOldAgeReviewRpt01JoblessPayListBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 申請失業給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Biref> getDisableReviewRpt01JoblessPayListBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 申請職業訓練生活津貼記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Biref> getDisableReviewRpt01VocationalTrainingLivingAllowanceListBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 申請失業給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Biref> getSurvivorReviewRpt01JoblessPayListBy(String evtIdnNo, String evtBrDate);

}
