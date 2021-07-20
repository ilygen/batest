package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Baacpdtl;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baban;
import tw.gov.bli.ba.domain.Pfpccky;

/**
 * DAO for (<code>BAACPDTL</code>)
 * 
 * @author Noctis
 */

public interface BaacpdtlDao {
    
	/**
     * 新增(<code>BAACPDTL</code>) 資料 for 1. 	老年年金應收收回處理作業 - 退匯
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForRemittanceReceiveData(Baacpdtl baacpdtl);
    
    /**
     * 新增(<code>BAACPDTL</code>) 資料 for 1. 	老年年金應收收回處理作業 - 退現
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForCashReceiveData(Baacpdtl baacpdtl);
    
    /**
     * 依傳入條件取得 退現資料檔 (<code>BAACPDTL</code>)  for 老年年金應收收回註銷處理
     * 
     * @param apNo  受理編號
     * @param apNo  給付別
     * @return
     */
    public List<Baacpdtl> selectCashReceivedDataBy(String apNo,String payCode);

    /**
     * 依傳入條件取得 BAUNACPDTLID for 老年年金應收收回註銷處理
     * 
     * @param apNo
     * @param insKdCash
     * @param bliAccountCode
     * @param bookedBook
     * @param bkAccountDt
     * @param batchNo
     * @param cashAmt
     * @param divSeq
     * @param indexNo
     */
    public List<Baacpdtl> selectBaunacpdtlDataForCashReceivedBy(String apNo, String insKdCash, String bliAccountCode, String bookedBook, String bkAccountDt, String batchNo,String serialNo, BigDecimal cashAmt, String divSeq, String indexNo);
    
    /**
     * 依傳入條件取得 BAUNACPDTLID for 老年年金應收收回註銷處理
     * 
     * @param apNo
     * @param insKdCash
     * @param bliAccountCode
     * @param bookedBook
     * @param bkAccountDt
     * @param batchNo
     * @param cashAmt
     * @param divSeq
     * @param indexNo
     */
    public List<Baacpdtl> selectBaunacpdtlIdForCashReceivedBy(String apNo, String insKdCash, String bliAccountCode, String bookedBook, String bkAccountDt, String batchNo,String serialNo, BigDecimal cashAmt, String divSeq, String indexNo);

    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID<br>
     * 
     * @param transActionId
     * @param transActionSeq
     * @param inskdRegive
     * @return
     */
    public List<Baacpdtl> selectBaunacpdtlDataForRemittanceReceivedBy(String transActionId, String transActionSeq, String insKd);
    
    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID LIST<br>
     * 
     * @param transActionId
     * @param transActionSeq
     * @param inskdRegive
     * @return
     */
    public List<Baacpdtl> selectBaunacpdtlIdForRemittanceReceivedBy(String transActionId, String transActionSeq, String insKd);
    
    /**
     * 更新 退現資料檔(<code>BAACPDTL</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBaacpdtlForCancelCashReceive(BigDecimal baunacpdtlId, BigDecimal baacpdtlId);
    
    /**
     * 新增(<code>BAACPDTL</code>) 資料 for 1.  老年年金應收收回處理作業 - 退匯
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Baacpdtl baacpdtl);
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR 整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */
    public List<Baacpdtl> selectBaacpdtlDataBy(String apNo ,String seqNo, String acpDate);
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR 更正作業 - 應收收回處理作業 - 老年年金應收收回處理
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */
    public List<Baacpdtl> selectBaacpdtlDataForPaymentReceiveBy(String apNo, String seqNo, BigDecimal baunacpdtlId, String batchNo, String bkAccountDt, String bli_Account_Code, String bookedBook, BigDecimal cash_Amt, String divSeq, String index_No, String serialNo);
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （失能）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */
    public List<Baacpdtl> selectKBaacpdtlDataBy(String apNo ,String seqNo, String acpDate);
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （遺屬）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */
    public List<Baacpdtl> selectSBaacpdtlDataBy(String apNo ,String seqNo, String acpDate);
    
    /**
     * 更新 退現資料檔(<code>BAACPDTL</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBaacpdtlForPfmPfd(int v_cprnpage, int v_lcnt, BigDecimal baacpdtlId);
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （老年）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */
    public List<Baacpdtl> getDataForPfdResult(String payCode ,String chkDate, String acpDate, String apNo, String apSeqNo, String payTyp, String issuYm);
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （老年）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */
    public List<Baacpdtl> getDataForPaymentReceivePfdResult(String payCode ,String chkDate, String acpDate, String apNo, String apSeqNo, String payTyp, String issuYm);
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （失能）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */
    public List<Baacpdtl> getDataForPfdKResult(String payCode ,String chkDate, String acpDate, String apNo, String apSeqNo, String payTyp, String issuYm, String nlWkMk, String adWkMk);
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （遺屬）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */
    public List<Baacpdtl> getDataForPfdSResult(String payCode ,String chkDate, String acpDate, String apNo, String apSeqNo, String payTyp, String issuYm, String nlWkMk, String adWkMk);
}
