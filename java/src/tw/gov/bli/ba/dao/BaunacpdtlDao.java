package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.domain.Barxf;
import tw.gov.bli.ba.domain.Baunacpdtl;

/**
 * DAO for 應收帳務明細檔 (<code>BAUNACPDTL</code>)
 * 
 * @author Rickychi
 */
public interface BaunacpdtlDao {
    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 應收查詢
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param unacpDate 應收立帳日期
     * @param issuYm 核定年月
     * @return 內含 <code>Baunacpdtl</code> 物件的 List
     */
    public List<Baunacpdtl> selectReceivableDetailBy(String apNo, String evtIdnNo, String unacpDate, String issuYm);

    /**
     * 更新 應收帳務明細檔(<code>BAUNACPDTL</code>) 收回種類資料
     * 
     * @param Baunacpdtl 應收帳務明細檔
     */
    public void updateRecKind(Baunacpdtl baunacpdtl);

    /**
     * 更新 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 已收調整作業
     * 
     * @param Baunacprec 應收帳務記錄檔
     */
    public void updateForReceivableAdj(Baunacpdtl baunacpdtl);

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 另案扣減資料 - 年金給付 for 勞保老年年金給付受理編審清單
     * 
     * @param benIdnNo 受益人身分證號
     * @return
     */
    public List<Baunacpdtl> selectOldAgeReviewRpt01DeductAnnuityListBy(String benIdnNo);

    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BAUNACPDTL</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param BENIDNNO 受益人身分證號
     * @return
     */
    public List<Baunacpdtl> selectDisableReviewRpt01PayDeductListBy(String benIdnNo);

    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BAUNACPDTL</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param BENIDNNO 受益人身分證號
     * @return
     */
    public List<Baunacpdtl> selectSurvivorReviewRpt01PayDeductListBy(String benIdnNo);
    
    /**
     * 依傳入條件取得 應收資料(<code>BAUNACPDTL</code>) 資料 for 應收收回處理作業
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<Baunacpdtl> selectAccountsReceivableDataListBy(String apNo, String seqNo, String issuYm, String payYm);
    
    /**
     * 更新 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 應收收回處理作業
     * 
     * @param recRem   未收總金額
     * @param actEndMk  應收結案註記
     * @param baunacpdtlId  資料列編號(應收帳務明細編號)
     * @param Baunacpdtl 應收帳務明細檔
     */
    public void updateBaunacpdtlForPaymentReceive(BigDecimal recRem,String actEndMk,BigDecimal baunacpdtlId);
    
    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 已退匯或退回現金尚未沖轉收回案件清單
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return BigDecimal
     */
    public BigDecimal selectOtherRpt02RecRemDataBy(String payCode,String apNo,String seqNo);
    
    /**
     * 依傳入條件取得 應收資料(<code>BAUNACPDTL</code>) 資料 for 已退匯或退回現金尚未沖轉收回案件清單
     * 
     * @param apNo  受理編號
     * @return
     */
    public List<Baunacpdtl> selectOtherRpt02SourceDataListBy(String apNo);
    
    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 已退匯或退回現金尚未沖轉收回案件清單 - 退現
     * 
     * @param payCode  給付別
     * @return
     */
    public List<Baunacpdtl> selectOtherRpt02CashDataListBy(String payCode);
    
    /**
     * 更新 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料
     * 
     * @param baunacpdtl 退現資料檔
     */
    public void updateBaunacpdtlForCancelCashReceive(Baunacpdtl baunacpdtl);
    
    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for for 應收收回處理作業 - 退現收回註銷
     * 
     * @param baunacpdtlId 
     * @return
     */
    public Baunacpdtl selectDataForPaymentReceiveBy(BigDecimal baunacpdtlId);
    /**
     * 依傳入條件取得 應收未收資料
     * 
     * @param apno 受理編號
     * @return
     */
    public List<Baunacpdtl> selectUnacpdtlDataList(String apno);
    public String execRptOverdue(String payCode, String procYm, String paySeqNo, String cprnDt,String rptTyp, String apNo, String deadYy);
    
    public List<Baunacpdtl> getBadDebtDtlDataBy(String payCode, String apNo,String isNull,String isEqual);
    
    public void updateDataForBadDebt(Baunacpdtl baunacpdtl);
}
