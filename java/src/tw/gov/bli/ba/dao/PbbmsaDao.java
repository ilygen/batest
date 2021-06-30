package tw.gov.bli.ba.dao;

import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Pbbmsa;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 現金給付參考檔 (<code>PBBMSA</code>) <br>
 * 
 * @author Goston
 */
public interface PbbmsaDao {

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 一次給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getOldAgeReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請失能給付記錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getOldAgeReviewRpt01DisablePayListBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請死亡給付記錄 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getOldAgeReviewRpt01DiePayListBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請傷病給付記錄 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getOldAgeReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate, String evtJobDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 資料 for 紓困貸款抵銷情形照會單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getDataUpdateRpt03DataBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 一次給付資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> selectOncePayDataForPaymentBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請失能給付紀錄資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> selectCriPayApplyDataForPaymentBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請死亡給付紀錄資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> selectDiePayApplyDataForPaymentBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請傷病給付紀錄資料 for 給付審核/決行
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> selectInjPayApplyDataForPaymentBy(String evtIdnNo, String evtBrDate);

    /**
     * 依傳入條件取得 申請代算單位 (<code>PBBMSA</code>)下拉選單資料
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @param bmOldAplDpt 已存在的申請代算單位
     * @return
     */
    public List<Pbbmsa> getOldAplDptOptionList(String evtBrDate, String evtIdnNo, String bmOldAplDpt);

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 一次給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> selectSurvivorReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 老年給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> selectSurvivorReviewRpt01OldAgePayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 失能給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> selectSurvivorReviewRpt01DisPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 職災住院醫療給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> selectSurvivorReviewRpt01HosPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請傷病給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param bmEvidNo 身分證號
     * @param bmEvBrth 出生日期
     * @return
     */
    public List<Pbbmsa> selectSurvivorReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate, String apNo);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請失蹤給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getSurvivorReviewRpt01DisappearPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請農保死亡給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getSurvivorReviewRpt01FamDiePayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 一次給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getDisableReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請農保殘廢給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getDisableReviewRpt01FarmPayListBy(String evtIdnNo, String evtBrDate);
    
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請失能給付記錄資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getDisableReviewRpt01DisablePayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請死亡給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getDisableReviewRpt01DiePayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請失蹤給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getDisableReviewRpt01DisPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請農保死亡給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getDisableReviewRpt01FamDiePayListBy(String evtIdnNo, String evtBrDate);


    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請傷病給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getDisableReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 申請老年給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> getDisableReviewRpt01OldPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 職災住院醫療給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    public List<Pbbmsa> selectDisableReviewRpt01HosPayListBy(String evtIdnNo, String evtBrDate);
    
    /**
     * 依傳入條件取得 現金給付參考檔(<code>PBBMSA</code>) 資料 for 給付查詢MasterDetail
     * 
     * @param evtIdnNo 事故者身份證號
     * @param evtBrDate 事故者出生日期
     * @return 內含 <code>Pbbmsa</code> 物件的 List
     */
    public List<Pbbmsa> selectDifferenceDetail(String evtIdnNo, String evtBrDate, String sBMPAYKND, String sBMAPNO);    
    
}
