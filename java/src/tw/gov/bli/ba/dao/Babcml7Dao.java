package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Babcml7;

/**
 * DAO for 勞保複檢費用主檔 (<code>BABCML7</code>)
 * 
 * @author Evelyn Hsu
 */
public interface Babcml7Dao {

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) for 複檢費用審核給付清單
     * 
     * @param apNo 受理編號
     * @param apSeq 申請序號
     * @return
     */
    public List<Babcml7> selecReviewFeeReceiptRpt01ListBy(String apNo, String apSeq);

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) for 複檢費用核定通知書
     * 
     * @param apNo 受理編號
     * @param apSeq 申請序號
     * @return
     */
    public List<Babcml7> selecReviewFeeReceiptRpt02ListBy(String apNo, String apSeq);

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) for 複檢費用核定清單
     * 
     * @param apNo 受理編號
     * @param apSeq 申請序號
     * @return
     */
    public List<Babcml7> selecReviewFeeReceiptRpt03ListBy(String apNo, String apSeq);

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 該筆受理案件的下一筆複檢費用申請序 for 複檢費用受理作業
     * 
     * @param reApNo 複檢費用受理編號
     * @return
     */
    public Integer getReviewFeeReceiptApSeqForInsertBy(String reApNo);

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用受理作業
     * 
     * @param reApNo 複檢費用受理編號
     * @param evtIdnNo 事故者身分證號
     * @return
     */
    public List<Babcml7> getReviewFeeReceiptListForUpdateBy(String reApNo, String evtIdnNo);

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用受理作業 (MMAPLOG 用)
     * 
     * @param reApNo 複檢費用受理編號
     * @param apSeq 複檢費用申請序
     * @return
     */
    public Babcml7 getReviewFeeReceiptDataForUpdateBy(String reApNo, BigDecimal apSeq);

    /**
     * 新增資料至 勞保複檢費用主檔(<code>BABCML7</code>) for 複檢費用受理作業
     * 
     * @param data
     */
    public void insertReviewFeeReceiptData(Babcml7 data);

    /**
     * 更新 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用受理作業
     * 
     * @param data
     */
    public void updateReviewFeeReceiptData(Babcml7 data);

    /**
     * 刪除 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用受理作業
     * 
     * @param reApNo 複檢費用受理編號
     * @param apSeq 複檢費用申請序
     */
    public void deleteReviewFeeReceiptData(String reApNo, BigDecimal apSeq);

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用審核作業
     * 
     * @param reApNo 複檢費用受理編號
     * @return
     */
    public List<Babcml7> getReviewFeeReviewListBy(String reApNo);

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用審核作業
     * 
     * @param reApNo 複檢費用受理編號
     * @param apSeq 複檢費用申請序
     * @return
     */
    public Babcml7 getReviewFeeReviewDataForUpdateBy(String reApNo, BigDecimal apSeq);

    /**
     * 更新 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用審核作業
     * 
     * @param data
     */
    public void updateReviewFeeReviewData(Babcml7 data);

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用決行作業
     * 
     * @param reApNo 複檢費用受理編號
     * @return
     */
    public List<Babcml7> getReviewFeeDecisionListBy(String reApNo);

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用決行作業
     * 
     * @param reApNo 複檢費用受理編號
     * @param apSeq 複檢費用申請序
     * @return
     */
    public Babcml7 getReviewFeeDecisionDataForUpdateBy(String reApNo, BigDecimal apSeq);

    /**
     * 更新 勞保複檢費用主檔(<code>BABCML7</code>) 的資料 for 複檢費用決行作業
     * 
     * @param data
     */
    public void updateReviewFeeDecisionData(Babcml7 data);

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 複檢費用資料 for 失能年金給付查詢
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Babcml7</code> 物件的 List
     */
    public List<Babcml7> selectReFeesMasterDataForDisabledPaymentQuery(String apNo);

    /**
     * 依傳入的條件取得 勞保複檢費用主檔(<code>BABCML7</code>) 複檢費用明細資料 for 失能年金給付查詢
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Babcml7</code> 物件的 List
     */
    public List<Babcml7> selectReFeesDetailDataForDisabledPaymentQuery(String reApNo, BigDecimal apSeq, String apNo);
}
