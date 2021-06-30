package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bafamily;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 眷屬檔 (<code>BAFAMILY</code>)
 * 
 * @author Evelyn Hsu
 */

public interface BafamilyDao {
    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 資料
     * 
     * @param bafamilyId 資料列編號
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    public List<Bafamily> selectDataBy(BigDecimal bafamilyId, BigDecimal baappbaseId, String apNo, String seqNo);

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 眷屬資料 for 勞保XX年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param baappbaseId 給付主檔資料列編號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    public List<Bafamily> selectReviewRpt01FamilyListBy(String apNo, BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 眷屬資料 for 失能年金編審註記程度調整 (眷屬資料)
     * 
     * @param apNo 受理編號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    public List<Bafamily> getDisabledCheckMarkLevelAdjustFamListBy(String apNo);

    /**
     * 新增 眷屬檔 (<code>BAFAMILY</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamily 眷屬檔
     */
    public BigDecimal insertDataForDisabledAnnuityReceipt(Bafamily bafamily);

    /**
     * 新增 眷屬檔 (<code>BAFAMILY</code>) 資料(多筆) For 失能年金受理作業
     * 
     * @param bafamily 眷屬檔
     */
    public void insertDataForDisabledAnnuityReceipt(final List<Bafamily> bafamilyList);

    /**
     * 修改 眷屬檔 (<code>BAFAMILY</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamily 眷屬檔
     */
    public void updateDataForDisabledAnnuityReceipt(Bafamily bafamily);

    /**
     * 刪除 眷屬檔 (<code>BAFAMILY</code>) 資料 By bafamilyId
     * 
     * @param bafamilyId 眷屬檔資料列編號
     */
    public void deleteDataByBafamilyId(BigDecimal bafamilyId);

    /**
     * 刪除 眷屬檔 (<code>BAFAMILY</code>) 資料 By baappbaseId
     * 
     * @param baappbaseId 給付主檔資料列編號
     */
    public void deleteDataByBaappbaseId(BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 資料 By baappbaseId
     * 
     * @param baappbaseId 給付主檔資料列編號
     */
    public List<Bafamily> selectDataByBaappbaseIdByLog(BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 已存在之眷屬資料 for 失能/遺屬年金給付受理作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param famIdnNo 遺屬/眷屬身分證號
     * @param famBrDate 遺屬/眷屬出生日期
     * @param seqNo 序號
     * @param eqType SQL EqualType
     * @return 內含 <code>BigDecimal</code> 物件的 List
     */
    public List<BigDecimal> selectExistDataForAnnuityReceiptBy(BigDecimal baappbaseId, String famIdnNo, String famBrDate, String seqNo, String eqType);

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 新序號
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @return <code>String</code> 物件
     */
    public String selectNewSeqNo(BigDecimal baappbaseId, String apNo);

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 眷屬資料清單 for 失能年金受理作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @return <code>String</code> 物件
     */
    public List<Bafamily> selectDisabledFamDataListBy(BigDecimal baappbaseId, String apNo);

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 眷屬資料 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */

    public List<Bafamily> selectDependantDataForList(String apNo);

    /**
     * 取得 眷屬資料筆數
     */
    public Integer getDependantDataCount(String apNo, String famIdnNo, String bafamilyId);

    /**
     * 取得 眷屬資料筆數
     */
    public Integer getEvtDataCount(String apNo, String famIdnNo, String bafamilyId);

    /**
     * 取得 眷屬資料序號
     */
    public Integer getDependantSeqNoDataCount(String apNo);

    /**
     * 新增 眷屬檔(<code>BAFAMILY</code>) 資料
     * 
     * @param bafamily 眷屬檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertData(Bafamily bafamily);

    /**
     * 修改 眷屬檔 (<code>BAFAMILY</code>) 資料
     * 
     * @param bafamily 眷屬檔
     * @return <code>BigDecimal</code>
     */
    public void updateDataForDependant(Bafamily bafamily);

    /**
     * 刪除 眷屬檔 (<code>BAFAMILY</code>) 資料
     * 
     * @param bafamily 眷屬檔
     * @return <code>BigDecimal</code>
     */
    public void deleteDataForDependant(Bafamily bafamily);

    /**
     * 依傳入條件取得 眷屬檔(<code>BAFAMILY</code>) 資料 FOR 眷屬資料更正作業
     * 
     * @param bafamilyId 資料編號
     * @return <code>Bafamily</code> 物件
     */
    public Bafamily getDependantDataUpdateForUpdateCaseBy(String bafamilyId);

    /**
     * 依傳入條件取得 眷屬檔(<code>BAFAMILY</code>) 資料
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Bafamily</code> 物件
     */
    public List<Bafamily> getDisableBafamilyListBy(BigDecimal baappbaseId, String apNo);

    /**
     * 依傳入條件取得 眷屬檔(<code>BAFAMILY</code>) 資料 For 失能年金給付查詢作業
     * 
     * @param apNo 受理編號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    public List<Bafamily> selectFamilyDataForDisabledPaymentQuery(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Boolean getDisableReviewRpt01BafamilyByCount(String apNo);

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 眷屬資料 for 補送在學證明通知函
     * 
     * @param apNo 受理編號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    @DaoFieldList("APNO")
    public List<Bafamily> selectFamNameForMonthlyRpt29By(String apNo, String sA114RptMk);

    /**
     * 依傳入條件取得 受款人數(<code>BAFAMILY</code>)
     * 
     * @param apNo 受理編號
     * @return
     */
    public BigDecimal getPayeeCount(String apNo);

}
