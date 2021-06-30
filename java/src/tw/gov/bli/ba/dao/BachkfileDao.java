package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Bachkfile;
import tw.gov.bli.ba.update.cases.ChkFileCase;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 給付編審檔 (<code>BACHKFILE</code>)
 * 
 * @author Rickychi
 */
public interface BachkfileDao {
    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 FOR 案件資料更正
     * 
     * @param baappbaseId 資料列編號
     * @return <code>ChkFileCase</code> 物件
     */
    public List<ChkFileCase> selectChkListBy(BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 FOR 編審註記程度調整
     * 
     * @param apNo 受理編號
     * @param orderBy 排序方式
     * @return <code>ChkFileCase</code> 物件
     */
    public List<Bachkfile> selectChkListForCheckMarkLevelAdjust(String apNo, String orderBy);

    /**
     * 更新 給付編審檔(<code>BACHKFILE</code>) 資料
     * 
     * @param baChkFileId
     * @param chkCodePost
     */
    public void updateChkCodePost(String baChkFileId, String chkCodePost);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料
     * 
     * @param baChkFileId
     * @param chkCodePost
     */
    public Bachkfile selectChkCodePostByLog(String baChkFileId);

    /**
     * 計算 給付編審檔(<code>BACHKFILE</code>) CHKCODEPOST 資料
     * 
     * @param apNo 受理編號
     */
    public BigDecimal countForCheckMarkLevelAdjust(String apNo);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審註記資料 FOR 給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> selectReviewEvtChkListForOldAge(String apNo);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 受款人編審註記資料 FOR 老年年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> selectReviewBenChkListForOldAge(String apNo);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審註記資料 FOR 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> selectReviewEvtChkListForDisabled(String apNo);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 眷屬編審註記資料 FOR 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> selectReviewBenChkListForDisabled(String apNo);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 眷屬符合註記資料 FOR 失能年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> selectReviewOtherChkListForDisabled(String apNo);
    
    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 事故者編審註記資料 FOR 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> selectReviewEvtChkListForSurvivor(String apNo);
    
    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 遺屬編審註記資料 FOR 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> selectReviewBenChkListForSurvivor(String apNo);
    
    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 遺屬符合註記資料 FOR 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> selectReviewOtherChkListForSurvivor(String apNo);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 FOR 給付審核
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param chkTyp 編審註記種類
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> selectReviewChkListBy(String apNo, String seqNo, String chkTyp, String familySeqNo);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 受款人編審註記資料 FOR 給付審核
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> selectBenChkDataBy(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getOldAgeReviewRpt01ChkfileDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getOldAgeReviewRpt01ChkfileDescBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getOldAgeReviewRpt01BenChkfileDataBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 受款人編審註記資料 FOR 給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param qryCond 查詢條件
     * @param startYm 查詢年月起
     * @param endYm 查詢年月迄
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> selectBenChkDataForPaymentQueryBy(String apNo, String seqNo, String qryCond, String startYm, String endYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 給付函核定通知書
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public String selectForRptReplace(String apNo, String seqNo, String issuYm);
    
    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 給付函核定通知書
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public String selectForRptReplaceA137(String apNo, String seqNo, String issuYm);    

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public Integer selectForPayeeDataUpdateQ1(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔 For 失能年金受款人資料更正作業是否顯示身分查核年月 檢查傳入的受款人是否有W1,WH,WJ註記
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public boolean displayIdnChkYearMonth(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔 For 遺屬年金受款人資料更正作業是否再婚日期 檢查傳入的受款人是否有5G註記
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public boolean displayDigamyDate(String apNo, String seqNo, String issuYm);

    /**
     * 刪除 給付編審檔 (<code>BACHKFILE</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public void deleteBachkfileData(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付編審檔 (<code>BACHKFILE</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public List<Bachkfile> selectBachkfileDataByLog(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getDisableReviewRpt01BenChkfileDataBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getDisableReviewRpt01ChkfileDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getDisableReviewRpt01ChkfileDescBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> selectSurvivorReviewRpt01ChkfileDataBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 失能年金編審註記程度調整 (受款人編審註記資料)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public List<Bachkfile> getDisabledCheckMarkLevelAdjustBenDetailDataBy(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getSurvivorReviewRpt01ChkfileDescBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 失能年金編審註記程度調整 (眷屬編審註記資料)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public List<Bachkfile> getDisabledCheckMarkLevelAdjustFamDetailDataBy(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 遺屬年金編審註記程度調整 (受款人編審註記資料)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return
     */
    public List<Bachkfile> getSurvivorCheckMarkLevelAdjustBenDetailDataBy(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 失能年金案件資料更正 (編審註記資料)
     * 
     * @param baappbaseId 主檔資料列編號
     * @return
     */
    public List<Bachkfile> getDisabledApplicationDataUpdateCheckFileListBy(BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 遺屬年金案件資料更正 (編審註記資料)
     * 
     * @param baappbaseId 主檔資料列編號
     * @return
     */
    public List<Bachkfile> getSurvivorApplicationDataUpdateCheckFileListBy(BigDecimal baappbaseId);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單(眷屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getDisableReviewRpt01FamChkfileDataBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單(眷屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getDisableReviewRpt01FamChkfileDescBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @param chkTyp 編審註記種類
     * @param familySeqNo 眷屬資料序號
     * @return <code>Bachkfile</code> 物件 List
     */

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單(眷屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getDisableReviewRpt01FamChkfileDescDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單(眷屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getDisableReviewRpt01FamChkfileDescData(String apNo, String issuYm);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 遺屬年金給付審核
     * 
     * @param apNo 受理編號
     * @param chkTyp 編審註記種類
     * @param familySeqNo 眷屬資料序號
     * @return <code>Bachkfile</code> 物件 List
     */

    public List<Bachkfile> selectChkFileForSurvivorPaymentReview(String apNo, String chkTyp, String familySeqNo);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 遺屬年金受款人資料更正
     * 
     * @param apNo 受理編號
     * @param chkTyp 編審註記種類
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> getSurvivorPayeeQualifyCheckMark(String apNo, String seqNo);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 遺屬年金受款人資料更正
     * 
     * @param apNo 受理編號
     * @param chkTyp 編審註記種類
     * @return <code>Bachkfile</code> 物件 List
     */
    public List<Bachkfile> getSurvivorPayeeCheckMark(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保遺屬年金給付受理編審清單(遺屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getSurvivorReviewRpt01ChkfileDataBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保失能年金給付受理編審清單(眷屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getSurvivorReviewRpt01BenChkfileBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保遺屬年金給付受理編審清單(遺屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getSurvivorReviewRpt01ChkfileDescDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保遺屬年金給付受理編審清單(遺屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bachkfile> getSurvivorReviewRpt01BenChkfileDescData(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 勞保遺屬年金給付受理編審清單(遺屬)
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public Integer selectDataCount2ForDependant(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 編審註記參數檔(<code>BACHKFILE</code>) 資料 FOR 補送在學證明案件明細資料
     * 
     * @param apNo 受理編號
     * @param seqNo 排序方式
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return <code>BACHKFILE</code> 物件
     */
    public List<Bachkfile> selectChkCodeForRpt29By(String apNo, String seqNo, String issuYm, String payYm);
    
    /**
     * 依傳入條件取得 給付編審檔(<code>BACHKFILE</code>) 資料 for 給付函核定通知書
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     * @return
     */
    public String selectInsuranceSalaryForRptReplace(String apNo, String seqNo, String issuYm);
}
