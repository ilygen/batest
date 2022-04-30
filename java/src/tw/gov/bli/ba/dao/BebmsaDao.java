package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Bebmsa;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 現金給付參考檔 (<code>BEBMSA</code>) <br>
 * 
 * @author Goston
 */
public interface BebmsaDao {

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請死亡給付記錄 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getOldAgeReviewRpt01DiePayListBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 一次給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> selectSurvivorReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate, String paytyp);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param bmEvidNo 身分證號
     * @param bmEvBrth 出生日期
     * @return
     */
    public List<Bebmsa> selectSurvivorReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate, String apNo);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請失蹤給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getSurvivorReviewRpt01DisappearPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請死亡給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getDisableReviewRpt01DiePayListBy(String evtIdnNo, String evtBrDate, String paytyp);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請失蹤給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getDisableReviewRpt01DisPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getDisableReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate);
    
	/**
	 * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 一次給付資料 for 災保失能年金給付受理編審清單
	 * 
	 * @param evtIdnNo 事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @param paytyp 給付種類
	 * @return
	 */
	public List<Bebmsa> getDisableReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate, String paytyp);

	/**
	 * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 失能給付資料 for 勞保遺屬年金給付受理編審清單
	 * 
	 * @param evtIdnNo 事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return
	 */
	List<Bebmsa> selectSurvivorReviewRpt01DisPayListUsingPayTypBy(String evtIdnNo, String evtBrDate, String payTyp);

	/**
	 * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保失能年金給付受理編審清單
	 * 
	 * @param evtIdnNo 事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return
	 */
	List<Bebmsa> getDisasterReviewRpt01InjuryCarePayListBy(String evtIdnNo, String evtBrDate);

	/**
	 * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保遺屬年金給付受理編審清單
	 * 
	 * @param bmEvidNo 身分證號
	 * @param bmEvBrth 出生日期
	 * @return
	 */
	List<Bebmsa> selectDisasterReviewRpt01InjuryCarePayListBy(String evtIdnNo, String evtBrDate, String apNo);

	/**
	 * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 職災住院醫療給付資料 for 勞保失能年金給付受理編審清單
	 * 
	 * @param evtIdnNo 事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @return
	 */
	List<Bebmsa> selectDisableReviewRpt01HosPayListBy(String evtIdnNo, String evtBrDate);

}
