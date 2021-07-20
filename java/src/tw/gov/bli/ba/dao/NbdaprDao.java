package tw.gov.bli.ba.dao;

import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Nbappbase;
import tw.gov.bli.ba.domain.Nbdapr;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 國保給付核定檔 (<code>NBDAPR</code>)
 * 
 * @author Rickychi
 */
public interface NbdaprDao {
    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 申請國保給付記錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    public Nbdapr getOldAgeReviewRpt01NpPayDetailDataBy(String apNo, String issuYm, String payYm);

    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 申請國保給付記錄資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    public Nbdapr selectSurvivorReviewRpt01NpPayDetailDataBy(String apNo, String issuYm, String payYm);

    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 申請國保給付記錄資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    public Nbdapr getDisableReviewRpt01NpPayDetailDataBy(String apNo, String issuYm, String payYm);

    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 國保核定資料 FOR 失能年金給付查詢
     * 
     * @param apNo 受理編號
     * @param qryCond 查詢條件
     * @param startYm 開始年月
     * @param endYm 結束年月
     * @return
     */
    public List<Nbdapr> selectPaymentQueryDisabledNpIssuDataBy(String apNo, String qryCond, String startYm, String endYm);
    
    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 國保核定資料 FOR 失能年金給付受理/審核清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Nbdapr> selectDisabledReviewRpt01NpDataList36By(String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 國保給付核定檔 (<code>NBDAPR</code>) 國保核定資料 FOR 失能年金給付受理/審核清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Nbdapr> selectDisabledReviewRpt01NpDataList38By(String mapNo, String apNo, String issuYm);

}
