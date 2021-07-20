package tw.gov.bli.ba.dao;

import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baunacpdtl;
import tw.gov.bli.ba.domain.Pbbmsa;
import tw.gov.bli.ba.domain.Pfpccky;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 退現資料檔 (<code>PFPCCKY</code>) <br>
 * 
 * @author Noctis
 */
public interface LocalPfpcckyDao {

    /**
     * 依傳入條件取得 退現資料檔 (<code>PFPCCKY</code>)  for 老年年金應收收回處理
     * 
     * @param apNo  受理編號
     * @return
     */
    public List<Pfpccky> selectCashReceiveDataListBy(String apNo,String payCode);
    
    /**
     * 依傳入條件取得 退現資料檔(<code>PFPCCKY</code>) 資料 for 已退匯或退回現金尚未沖轉收回案件清單 - 退現
     * 
     * @param apNo  受理編號
     * @return
     */
    public List<Pfpccky> selectOtherRpt02CashSourceDataListBy(String apNo);

    /**
     * 依傳入條件取得 退現資料檔 (<code>PFPCCKY</code>)  for 整批收回核定清單
     * 
     * @param payCode  給付別
     * @param chkDate  核收日期
     * @return
     */
    public List<Pfpccky> selectBatchPaymentReceiveDataListBy(String payCode, String chkDate);
    
    /**
     * 依傳入條件取得 退現資料檔 (<code>PFPCCKY</code>)  for 整批收回核定清單
     * 
     * @param payCode  給付別
     * @param chkDate  核收日期
     * @return
     */
    public List<Pfpccky> selectBatchPaymentReceiveDataDetailListBy(String payCode, String chkDate, String apNo);    
    
    /**
     * 依傳入條件取得 退現資料檔 (<code>PFPCCKY</code>)  for 整批收回核定清單
     * 
     * @param payCode  給付別
     * @param chkDate  核收日期
     * @return
     */
    public List<Pfpccky> selectBatchPaymentReceiveDataMasterListBy(String payCode, String chkDate);      
    
}
