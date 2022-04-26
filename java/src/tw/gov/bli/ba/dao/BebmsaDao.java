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
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 一次給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getOldAgeReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請失能給付記錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getOldAgeReviewRpt01DisablePayListBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請死亡給付記錄 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getOldAgeReviewRpt01DiePayListBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getOldAgeReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate, String evtJobDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 資料 for 紓困貸款抵銷情形照會單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getDataUpdateRpt03DataBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 一次給付資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> selectOncePayDataForPaymentBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請失能給付紀錄資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> selectCriPayApplyDataForPaymentBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請死亡給付紀錄資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> selectDiePayApplyDataForPaymentBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付紀錄資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> selectInjPayApplyDataForPaymentBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 申請代算單位 (<code>BEBMSA</code>)下拉選單資料
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @param bmOldAplDpt 已存在的申請代算單位
     * @return
     */
    public List<Bebmsa> getOldAplDptOptionList(String evtBrDate, String evtIdnNo, String bmOldAplDpt);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 一次給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> selectSurvivorReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate, String paytyp);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 老年給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> selectSurvivorReviewRpt01OldAgePayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 失能給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> selectSurvivorReviewRpt01DisPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 職災住院醫療給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> selectSurvivorReviewRpt01HosPayListBy(String evtIdnNo, String evtBrDate);
    
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
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請農保死亡給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getSurvivorReviewRpt01FamDiePayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請農保殘廢給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getDisableReviewRpt01FarmPayListBy(String evtIdnNo, String evtBrDate);
    
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請失能給付記錄資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getDisableReviewRpt01DisablePayListBy(String evtIdnNo, String evtBrDate);
    
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
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請農保死亡給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getDisableReviewRpt01FamDiePayListBy(String evtIdnNo, String evtBrDate);


    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getDisableReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請老年給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> getDisableReviewRpt01OldPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 職災住院醫療給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Bebmsa> selectDisableReviewRpt01HosPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔(<code>BEBMSA</code>) 資料 for 給付查詢MasterDetail
     * 
     * @param evtIdnNo 事故者身份證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>Bebmsa</code> 物件的 List
     */
    public List<Bebmsa> selectDifferenceDetail(String evtIdnNo, String evtBrDate, String sBMPAYKND, String sBMAPNO);

	/**
	 * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 一次給付資料 for 災保失能年金給付受理編審清單
	 * 
	 * @param evtIdnNo 事故者身分證號
	 * @param evtBrDate 事故者出生日期
	 * @param paytyp 給付種類
	 * @return
	 */
	public List<Bebmsa> getDisableReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate, String paytyp);

}
