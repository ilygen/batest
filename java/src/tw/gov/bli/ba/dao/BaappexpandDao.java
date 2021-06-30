package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 給付延伸主檔 (<code>BAAPPEXPAND</code>)
 * 
 * @author Evelyn Hsu
 */

public interface BaappexpandDao {

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 詳細資料
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappexpand</code> 物件
     */
    public List<Baappexpand> getDisableBaappexpandListBy(BigDecimal baappbaseId, String apNo);
    
    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 詳細資料
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappexpand</code> 物件
     */
    public List<Baappexpand> getSurvivorBaappexpandListBy(BigDecimal baappbaseId, String apNo);
    
    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 詳細資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappexpand</code> 物件
     */
    public List<Baappexpand> getSurvivorBaappexpandListByEvt(String apNo);



    /**
     * 新增 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金受理作業
     * 
     * @param baappexpand 給付延伸主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForDisabledAnnuityReceipt(Baappexpand baappexpand);

    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金受理作業
     * 
     * @param baappexpand 給付延伸主檔
     */
    public void updateDataForDisabledAnnuityReceipt(Baappexpand baappexpand);

    /**
     * 新增 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金受款人更正作業
     * 
     * @param baappexpand 給付延伸主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForDisabledPayeeDataUpdate(Baappexpand baappexpand);

    
    /**
     * 新增 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金受款人更正作業
     * 
     * @param baappexpand 給付延伸主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForSurvivorPayeeDataUpdate(Baappexpand baappexpand);

    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金受款人更正作業
     * 
     * @param baappexpand 給付延伸主檔
     * @return <code>BigDecimal</code>
     */
    public void updateDataForSurvivorPayeeDataUpdate(Baappexpand baappexpand);

    /**
     * 新增 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金受理作業
     * 
     * @param baappexpand 給付延伸主檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForSurvivorAnnuityReceipt(Baappexpand baappexpand);

    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 事故者資料 for 遺屬年金受理作業
     * 
     * @param baappexpand 給付延伸主檔
     */
    public void updateEvtDataForSurvivorAnnuityReceipt(Baappexpand baappexpand);

    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 事故者資料 for 遺屬年金受理作業 (多筆)
     * 
     * @param baappexpandList 給付延伸主檔List
     */
    public void updateEvtDataForSurvivorAnnuityReceipt(final List<Baappexpand> baappexpandList);

    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 遺屬資料 for 遺屬年金受理作業
     * 
     * @param baappexpand 給付延伸主檔
     */
    public void updateBenDataForSurvivorAnnuityReceipt(Baappexpand baappexpand);

    /**
     * 刪除 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 By baappbaseId
     * 
     * @param baappbaseId 給付主檔資料列編號
     */
    public void deleteDataByBaappbaseId(BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金案件資料更正
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public Baappexpand getDisabledApplicationDataUpdateExpandDataBy(BigDecimal baappbaseId, String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金案件資料更正 (須更新的資料列編號)
     * 
     * @param apNo 受理編號
     * @param baappbaseId 給付主檔資料列編號
     * @return <code>BAAPPEXPANDID</code> 資料編號
     */
    public List<BigDecimal> getDisabledApplicationDataUpdateListForUpdateBy(String apNo, BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金案件資料更正 (依指定的資料列編號取得須更新的給付延伸主檔資料)
     * 
     * @param baappexpandId 資料編號
     * @return
     */
    public Baappexpand getDisabledApplicationDataUpdateExpandDataByBaappexpandId(BigDecimal baappexpandId);

    /**
     * 更新 給付延伸主檔(<code>BAAPPEXPAND</code>) 的資料 for 失能年金案件資料更正
     * 
     * @param dataList
     */
    public void updateDisabledApplicationDataUpdateData(final List<Baappexpand> dataList);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金案件資料更正
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public Baappexpand getSurvivorApplicationDataUpdateExpandDataBy(BigDecimal baappbaseId, String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金案件資料更正 (須更新的資料列編號)
     * 
     * @param apNo 受理編號
     * @param baappbaseId 給付主檔資料列編號
     * @return <code>BAAPPEXPANDID</code> 資料編號
     */
    public List<BigDecimal> getSurvivorApplicationDataUpdateListForUpdateBy(String apNo, BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 遺屬年金案件資料更正 (依指定的資料列編號取得須更新的給付延伸主檔資料)
     * 
     * @param baappexpandId 資料編號
     * @return
     */
    public Baappexpand getSurvivorApplicationDataUpdateExpandDataByBaappexpandId(BigDecimal baappexpandId);

    /**
     * 更新 給付延伸主檔(<code>BAAPPEXPAND</code>) 的資料 for 遺屬年金案件資料更正
     * 
     * @param dataList
     */
    public void updateSurvivorApplicationDataUpdateData(final List<Baappexpand> dataList);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 複檢費用受理作業 (Header for Add)
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @return
     */
    public Baappexpand getReviewFeeReceiptHeaderDataForInsertBy(BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 複檢費用受理作業 (Header for Update)
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @return
     */
    public Baappexpand getReviewFeeReceiptHeaderDataForUpdateBy(BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 複檢費用審核作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @return
     */
    public Baappexpand getReviewFeeReviewHeaderDataBy(BigDecimal baappbaseId);
    
    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 複檢費用決行作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @return
     */
    public Baappexpand getReviewFeeDecisionHeaderDataBy(BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 失能相關資料 for 失能 / 遺屬 年金給付審核作業
     * 
     * @param apNo 受理編號
     * @return <code>Baappexpand</code> 物件
     */
    public Baappexpand selectDisabledDataForPaymentReview(String apNo);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 遺屬相關資料 for 遺屬受款人資料更正 作業
     * 
     * @param apNo 受理編號
     * @return <code>Baappexpand</code> 物件
     */
    public Baappexpand selectDataForSurvivorPayeeDataUpdate(BigDecimal baappbaseId);
    
    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 遺屬欄位資料 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Baappexpand</code> 物件
     */
    public Baappexpand selectBenDataForSurvivorPaymentQuery(String apNo, String seqNo);
    
    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 年金給付
     * 
     * @param apNo 受理編號
     * @return <code>Baappexpand</code> 物件
     */
    
    public Baappexpand getSurvivorReviewRpt01AnnuityPayList(String apNo);
    
    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金給付
     * 
     * @param apNo 受理編號
     * @return <code>Baappexpand</code> 物件
     */
    
    public Baappexpand getSurvivorReviewRpt01DisablePayList(String apNo);
    
    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金給付
     * 
     * @param apNo 受理編號
     * @return <code>Baappexpand</code> 物件
     */
    
    public Baappexpand getDisabledReviewRpt01AnnuityPayList(String apNo);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金給付
     * 
     * @param apNo 受理編號
     * @return <code>Baappexpand</code> 物件
     */
    
    public Baappexpand getDisabledReviewRpt01SurvivorPayList(String apNo);
    
    /**
     * 修改 給付延伸主檔(<code>BAAPPEXPAND</code>) 資料 for 失能年金編審註記程度調整
     * 
     * @param baappexpand 給付延伸主檔
     */
    public void updateAbleapsYmForDisabledCheckMarkLevelAdjust(Baappexpand baappexpand);
    
    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) 得請領起始年月 for 失能年金編審註記程度調整
     * 
     * @param apNo 受理編號
     * @param baappbaseId 
     */
    public String getAbleapsYmForDisabledCheckMarkLevelAdjust(String apNo,BigDecimal baappbaseId);
    
    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) COMNPMK
     * 
     * @param baappbaseId
     * @param apNo
     * @param seqNo
     */
    public String selectComnpmkBy(BigDecimal baappbaseId, String apNo, String seqNo);
    
    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) MONNOTIFYINGMK
     * 
     * @param baappbaseId
     * @param apNo
     * @param seqNo
     */
    public String selectMonnotifyingmkBy(BigDecimal baappbaseId, String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付延伸主檔(<code>BAAPPEXPAND</code>) NLWKMK and ADWKMK for 給付查詢
     * 
     * @param baappbaseId
     * @param apNo
     * @return
     */
    public Baappexpand getBaappexpandDataForWkMkBy(BigDecimal baappbaseId, String apNo);

    /**
     * 更新 CPI 基準年月 for 給付延伸主檔(<code>BAAPPEXPAND</code>)
     * 
     * @param apNo
     * @param updUser
     * @param updTime
     */
    public void updateCpiBaseYMForResetPaymentParameter(String apNo, String updUser, String updTime);

    /**
     * 更新計算平均薪資申請年度 for 給付延伸主檔(<code>BAAPPEXPAND</code>)
     * 
     * @param apNo
     * @param updUser
     * @param updTime
     */
    public void updateInsAvgAmtAppYearForResetPaymentParameter(String apNo, String updUser, String updTime);
}
