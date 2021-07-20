package tw.gov.bli.ba.dao;

import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Nbappbase;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 國保給付主檔 (<code>NBAPPBASE</code>)
 * 
 * @author Rickychi
 */
public interface NbappbaseDao {
    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付紀錄資料 for 給付審核
     * 
     * @param evtIds 事故者社福識別碼
     * @return 內含 <code>Nbappbase</code> 物件的 List
     */
    public List<Nbappbase> selectNpPayDataBy(String evtIds);

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付記錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    public List<Nbappbase> getOldAgeReviewRpt01NpPayListBy(String evtIds);

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付記錄資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    public List<Nbappbase> selectSurvivorReviewRpt01NpPayListBy(String evtIds);

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保喪葬給付記錄資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    public List<Nbappbase> selectSurvivorReviewRpt01NpDidePayListBy(String evtIds, String apNoTyp);

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付記錄資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    public List<Nbappbase> getDisableReviewRpt01NpPayListBy(String evtIds);

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保身障給付記錄資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    public List<Nbappbase> getDisableReviewRpt01NpDisPayListBy(String evtIds);

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) LABMERGE
     * 
     * @param apNo
     * @return
     */
    public String selectLabmergeByApNo(String apNo);

    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 國保資料 FOR 失能年金給付查詢
     * 
     * @param apNo
     * @return
     */
    public List<Nbappbase> selectPaymentQueryDisabledNpDataBy(String apNo);
    
    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 國保資料 FOR 失能年金給付受理/審核清單 國保資料 payKind=38
     * 
     * @param apNo
     * @return
     */
    public Nbappbase selectDisabledReviewRpt01NpData38By(String apNo);
    
    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 國保資料 FOR 失能年金給付受理/審核清單 國保資料 payKind=36
     * 
     * @param apNo
     * @return
     */
    public Nbappbase selectDisabledReviewRpt01NpData36By(String apNo);
    
    /**
     * 依傳入條件取得 國保給付主檔(<code>NBAPPBASE</code>) 申請國保給付紀錄資料 for 失能年金給付受理
     * 
     * @param apNo 國寶受理編號
     * @return 內含 <code>Nbappbase</code> 物件的 List
     */
    public List<Nbappbase> selectDataBy(String apNo,String seqNo);
}
