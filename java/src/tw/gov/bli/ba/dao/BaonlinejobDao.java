package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Baonlinejob;

/**
 * DAO for 勞保年金線上作業檔 (<code>BAONLINEJOB</code>)
 * 
 * @author Noctis
 */
public interface BaonlinejobDao {
	
	 /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 「查詢線上月核定作業於系統日期當日是否已經執行過。 for 線上月核定作業
     * 
     * @param payCode 給付別
     * @return <code>BigDecimal</code>
     */
    public BigDecimal selectCountCheckForBjMonthBy(String payCode);
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 「查詢線上月試核作業於系統日期當日是否已經執行過。 for 線上月試核作業
     * 
     * @param payCode 給付別
     * @return <code>BigDecimal</code>
     */
    public BigDecimal selectCountCheckForBjMonthCheckBy(String apNo);
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baonlinejob</code> 物件的 List
     */
    public List<Baonlinejob> selectMonthDataListBy(String payCode, String issuYm);
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baonlinejob</code> 物件的 List
     */
    public List<Baonlinejob> selectMonthQueryDataListBy(String payCode, String issuYm, String chkDate);
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 編審後 資料清單
     * 
     * @param payCode 給付別
     * @return 內含 <code>Baonlinejob</code> 物件的 List
     */
    public List<Baonlinejob> selectMonthApprovedDataListBy(String apNo, String issuYm);
    
    /**
     * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業
     * 
     * @param Baonlinejob 給付主檔
     */
    public void updateBaonlinejobDataForMonth(Baonlinejob baonlinejob);
    
    /**
     * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月試核作業
     * 
     * @param Baonlinejob 給付主檔
     */
    public void updateBaonlinejobDataForMonthCheck(Baonlinejob baonlinejob);
    
    /**
     * 更新 勞保年金線上作業檔(<code>BAONLINEJOB</code>) for 線上月核定作業 查詢頁面 刪除
     * 
     * @param Baonlinejob 給付主檔
     */
    public void updateBaonlinejobDataForMonthDelete(Baonlinejob baonlinejob);
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 月試核 資料清單
     * 
     * @param payCode 給付別
     * @return 內含 <code>Baonlinejob</code> 物件的 List
     */
    public List<Baonlinejob> selectMonthCheckQueryDataListBy(String payCode, String issuYm);
    
    /**
     * 依傳入條件取得 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 月試核 編審後 資料清單
     * 
     * @param payCode 給付別
     * @return 內含 <code>Baonlinejob</code> 物件的 List
     */
    public List<Baonlinejob> selectMonthCheckApprovedDataListBy(String apNo, String issuYm);
    
    /**
     * 新增 勞保年金線上作業檔(<code>BAONLINEJOB</code>) 資料
     * 
     * @param baonlinejob 給付主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertBaonlinejobDataForMonthCheck(Baonlinejob baonlinejob);
}
