package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import tw.gov.bli.ba.bj.cases.CompPaymentCase;
import tw.gov.bli.ba.domain.Bbdapr;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt01Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt06DetailCase;
import tw.gov.bli.ba.update.cases.StopPaymentProcessCase;
import tw.gov.bli.ba.update.cases.StopPaymentProcessDetailCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 給付核定檔 (<code>BADAPR</code>)
 *
 * @author swim
 *
 */
public interface BbdaprDao {
    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>StopPaymentProcessCase</code> 物件的 List
     */
    public StopPaymentProcessCase selectData(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    public List<StopPaymentProcessDetailCase> selectStopPaymentDetailListData(String apNo, String issuYm);

    /**
     * 更新 BADAPR 的資料
     */
    public int updateData(Bbdapr data);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Bbdapr</code> 物件
     */
    public Bbdapr selectPayeeDataForBbdapr(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Bbdapr</code> 物件
     */
    public Bbdapr selectDisabledPayeeDataForBbdapr(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Bbdapr</code> 物件
     */
    public Bbdapr selectSurvivorPayeeDataHeaderForBbdapr(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Bbdapr</code> 物件
     */
    public List<SurvivorPayeeDataUpdateCase> selectSurvivorPayeeDataListForBbdapr(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @param issuym 核定年月
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    public List<Bbdapr> selectPayDataBy(String apNo, String issuym);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 人工編審註記筆數
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param qryTyp 查詢方式 1:(MANCHKMK IS NULL OR MANCHKMK=' ') ; 2:MANCHKMK = 'N'
     * @return 內含 <code>BigDecimal</code> 物件的 List
     */
    public BigDecimal selectManChkMkBy(String apNo, String issuYm, String qryTyp);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 人工編審註記筆數
     * 
     * @param apNo 受理編號
     * @param issuCalcAmt 前次核定金額
     * @return 內含 <code>BigDecimal</code> 物件
     */
    public Bbdapr selectOnceIssuCalcAmtBy(String apNo);
    
    /**
     * 更新 給付核定檔(<code>BADAPR</code>) 人工編審註記資料
     * 
     * @param badapr 給付核定檔
     */
    public void updateDataForReview(Bbdapr badapr);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Bbdapr getOldAgeReviewRpt01IssueAmtDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getOldAgeReviewRpt01PayListBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Bbdapr getOldAgeReviewRpt01LoanDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Bbdapr getOldAgeReviewRpt01DecideDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 年金給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    public Bbdapr getOldAgeReviewRpt01AnnuityPayDataBy(String apNo, String issuYm, String payYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getOldAgeReviewRpt01BenPayListBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 明細資料表頭-核定/給付年月資料
     * 
     * @param apNo 受理編號
     * @param startYm 查詢年月起
     * @param endYm 查詢年月迄
     * @param qryCond 查詢條件
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    public List<Bbdapr> selectPaymentQueryIssuPayDataBy(String apNo, String startYm, String endYm, String qryCond);

    // /**
    // * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 明細資料表頭-核定/給付年月資料
    // *
    // * @param apNo 受理編號
    // * @param startYm 查詢年月起
    // * @param endYm 查詢年月迄
    // * @param qryCond 查詢條件
    // * @return 內含 <code>Bbdapr</code> 物件的 List
    // */
    // public List<Bbdapr> selectPaymentQueryIssuPayDataBy(String apNo, String startYm, String endYm, String qryCond);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 給付明細查詢 - 核定/給付資料明細
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    public List<Bbdapr> selectPaymentQueryIssuPayDetailBy(String apNo, String issuYm, String payYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 給付明細查詢 - 年資資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    public List<Bbdapr> selectSeniDataBy(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 止付單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     */
    public List<Bbdapr> selectDataUpdateRpt05ForBbdapr(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>MonthlyRpt01Case</code>) 資料 for 月處理核定合格清冊
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<MonthlyRpt01Case> getMonthlyRpt01ListBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>MonthlyRpt01Case</code>) 資料 for 一次給付資料
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    public List<Bbdapr> selectOncePayDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 給付函核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getMonthlyRpt05ListBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for BaReportReplaceUtility
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> selectDataForReportReplace(String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for BaReportReplaceUtility
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> selectDataForReportReplaceA140(String apNo, String issuYm);    

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataTotal(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 受理審核清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataTotalForBalp010(String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataTotalForA118(String apNo, String issuYm, String eqType);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataTotalForA122(String apNo, String issuYm, String eqType);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 受理審核清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataA139ForBalp010(String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataForA139(String apNo, String issuYm);    
    
    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> selectReportReplaceDataForA133(String apNo, String issuYm);    

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件個受款人的核定總額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param seqNo 序號
     * @return
     */
    public List<Bbdapr> selectReportReplaceDataPersonal(String apNo, String issuYm, String seqNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件個受款人的核定總額資料 for report replace 受理審核清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param seqNo 序號
     * @return
     */
    public List<Bbdapr> selectReportReplaceDataPersonalForBalp010(String apNo, String issuYm, String seqNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 止付單
     * 
     * @return
     */
    public String getDataUpdateRpt05ForAplpayAmt(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 核定清單
     * 
     * @return
     */
    public MonthlyRpt06DetailCase getMonthlyRpt06DataBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 最新1筆的核付日期
     * 
     * @param apNo 受理編號
     * @return
     */
    public String selectMaxAplPayDate(String apNo);

    /**
     * 更新合格註記 for 給付核定檔(<code>Bbdapr</code>)
     * 
     * @param apNo 受理編號
     * @param updUser 異動者代號
     * @param updTime 異動日期時間
     */
    public void updateManchkMk(String apNo, String updTime);

    /**
     * 刪除 給付核定檔 (<code>Bbdapr</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public void deleteBbdaprData(String apNo, String seqNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 月核定撥付總表
     * 
     * @return
     */
    public List<Bbdapr> selectMonthlyRpt04Report(String payCode, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getDisableReviewRpt01BenPayListBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getDisableReviewRpt01BenByPayListBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單(已扣失能)
     * 
     * @param apNo 受理編號
     * @return
     */
    public BigDecimal getDisableReviewRpt01BenPayDataByLecomAmt(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單(已扣失能)
     * 
     * @param apNo 受理編號
     * @return
     */
    public BigDecimal getSurvivorReviewRpt01BenPayDataByLecomAmt(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單(未扣失能)
     * 
     * @param apNo 受理編號
     * @return
     */
    public BigDecimal getSurvivorReviewRpt01BenPayDataByRecomAmt(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單(已扣失能)
     * 
     * @param apNo 受理編號
     * @return
     */
    public BigDecimal getSurvivorReviewRpt01BenPayDataByOldbAmt(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getDisableReviewRpt01BenListBy(String apNo, String seqNo, String issuYm);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getDisableReviewRpt01BenListFor38By(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Bbdapr getDisableReviewRpt01IssueAmtDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Bbdapr getDisableReviewRpt01DecideDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    public Bbdapr getDisableReviewRpt01AnnuityPayDataBy(String apNo, String issuYm, String payYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getDisableReviewRpt01PayListBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getDisableReviewRpt01LoanAmt(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 核定總額資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Bbdapr selectSurvivorReviewRpt01IssueAmtDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public Bbdapr getSurvivorReviewRpt01DecideDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getSurvivorReviewRpt01BenPayListBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getSurvivorReviewRpt01OldPayListBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> selectSurvivorReviewRpt01PayDataBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 年金給付資料/請領同類給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    public Bbdapr selectSurvivorReviewRpt01DateDataBy(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 受款人給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    public List<Bbdapr> selectSurvivorReviewRpt01BenPayDataBy(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> getSurvivorReviewRpt01LoanAmt(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 職災相關資料 for 失能年金給付審核作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public Bbdapr selectOccAccDataForDisabledPaymentReview(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付年月下拉選單 FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @param ISSUYM 核定年月
     * @return 內含<code>Bbdapr</code> 物件的List
     */
    public List<Bbdapr> selectPayYmListForSurvivorPaymentReview(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 非本人的受款人筆數 for 失能年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    public Integer getDisabledApplicationDataUpdateBenCountBy(String apNo);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 受款人筆數 for 止付單
     * 
     * @param apNo 受理編號
     * @param issuYm 受理編號
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Integer getRpt05PeopleCountBy(String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 受款人筆數 for 止付單
     * 
     * @param apNo 受理編號
     * @param issuYm 受理編號
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Integer getRpt05PeopleCountForSBy(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 月核後受款人筆數 for 失能年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    public Integer getDisabledApplicationDataUpdateBenCountAfterCheckBy(String apNo);

    /**
     * 更新 給付核定檔(<code>BADAPR</code>) 事故者核定資料 FOR 遺屬年金給付審核
     * 
     * @param badapr 給付主擋
     */
    public void updateEvtDataForSurvivorPaymentReview(Bbdapr badapr);

    /**
     * 更新 給付核定檔(<code>BADAPR</code>) 受款人核定資料 FOR 遺屬年金給付審核
     * 
     * @param badapr 給付主擋
     */
    public void updateBenDataForSurvivorPaymentReview(Bbdapr badapr);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    public Boolean getDisableReviewRpt01BenListBySum(String apNo, String seqNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 刪除鈕狀態 for 眷屬資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    public BigDecimal getDependantDeleteStatus(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 最新紓困貸款資料
     * 
     * @param apNo 受理編號
     * @param payKind 給付種類
     * @return
     */
    public List<Bbdapr> selectPaymentQueryLoanMasterDataBy(String apNo, String payKind353638);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 最新紓困貸款資料 (BAAPPBASE.LSCHKMK = 4)
     * 
     * @param apNo 受理編號
     * @param payKind 給付種類
     * @return
     */
    public List<Bbdapr> selectPaymentQueryLoanMasterDataForLsChkMk4By(String apNo, String payKind353638);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 紓困貸款 核定資料
     * 
     * @param apNo 受理編號
     * @param qryCond 查詢條件
     * @param startYm 查詢年月起
     * @param endYm 查詢年月迄
     * @param payKind 給付種類
     * @return
     */
    public List<Bbdapr> selectPaymentQueryLoanDetailDataBy(String apNo, String qryCond, String startYm, String endYm, String payKind353638);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 平均薪資 - 年資資料
     * 
     * @param apNo 受理編號
     * @param payKind 給付種類
     * @return
     */
    public List<Bbdapr> selectPaymentQuerySeniDataBy(String apNo, String payKind);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 平均薪資 - 年資資料 (老年年金)
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Bbdapr> selectPaymentQuerySeniDataForOldAge(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 核定金額 for 給付查詢
     * 
     * @param apNo 受理編號
     * @return
     */
    public BigDecimal selectBefissueAmtForPaymentQuery(String apNo);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 物價指數調整資料 for 給付查詢
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Bbdapr> selectCpiDataForPaymentQuery(String apNo,String paycode);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 職災相關資料 for 失能年金給付審核清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Bbdapr selectOccAccDataForDisabledReviewRpt01(String apNo, String issuYm);

    /**
     * 更新 給付核定檔(<code>BADAPR</code>) 退匯金額分配資料
     * 
     * @param badapr 給付核定檔
     */
    public void updateRepayIssueAmtDataForDeathRepay(String apNo, String seqNo, String oriIssuYm, String payYm, String issuKind);
    
    /**
     * 刪除 給付核定檔 (<code>Bbdapr</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param oriIssuYm 核定年月 
     * @param payYm 給付年月
     */
    public void deleteRepayIssueAmtDataForDeathRepay(String apNo, String seqNo, String oriIssuYm, String payYm, String issuKind);
    
    /**
     * 新增 給付主檔(<code>BADAPR</code>) 資料 for 死亡改匯處理作業
     * 
     * @param Bbdapr 核定檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForDeathRepay(Bbdapr data);
    
    /**
     * 更新 給付核定檔(<code>BADAPR</code>) 退匯金額分配資料
     * 
     * @param badapr 給付核定檔
     */
    public void updateOldAgeDeathRepayDataForDeathRepay(BigDecimal remitAmt, String brChkDate, String apNo, String seqNo, String oriIssuYm, String payYm, String issuKind);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件個受款人的原核定金額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataForBbdaprBefAmt(String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 同一核定年月補發部分金額之給付年月補發金額加總 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataForSupData(String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件個受款人的核定年月總核付金額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataForToalIssuAmt(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 線上月核定作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>BigDecimal</code> 物件
     */
    public BigDecimal selectCountForMonthBy(String apNo, String issuYm);
    
    /**
     * 依傳入的條件計算 核定檔 (<code>BADAPR</code>) 已領其他年金金額資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Bbdapr> selectDabAnnuDataBy(String apNo, String evtJobYm);
    
    /**
     * 依傳入條件取得 核定檔(<code>BADAPR</code>) 資料清單 For 穿透案件統計表
     *  
     * @param issuYm 核定年月
     * @param issuDate
     * @param preIssuDate
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    public List<Bbdapr> selectOtherRpt10DataBy(String issuYm, String issuDate, String preIssuDate);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 止付單
     * 
     * @return
     */
    public BigDecimal getSumAplpayAmtForAccRel3(String apNo, List<String> seqNoList, String issuYm, String payYm);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件個受款人的核定總額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> selectReportReplaceDataForA045S(String apNo, String issuYm);

    /**
     * 依傳入條件取得 補發金額(<code>BADAPR</code>)
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public BigDecimal selectReportReplaceDataForBbdaprSupAmt(String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 本月核定合格及不合格人數 (A143) (<code>BADAPR</code>)
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    public BigDecimal selectReportReplaceDataForPeopleNumberA143(String apNo, String issuYm, String payYm);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 老年差額金通知 
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public String selectRptReplaceForMaxPayYmL021(String apNo, String issuYm);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 老年差額金通知
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectRptReplaceForPayYmL012(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 遺屬年金部分不合格核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectRptReplaceForPayYmA164(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 逾期未決行案件管制表
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public String selectChkListForAuditRpt01(String apNo, String issuYm);

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 逾期未決行案件管制表
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public String selectPreChkListForAuditRpt01(String apNo, String issuYm);
    
    /**
     * BABA0M241A.jsp BABA0M251A.jsp 更新 BADAPR 的資料
     */
    public int updManPayDataList(UserBean userData, CompPaymentCase compPaymentCase);
    
    /**
     * BABA0M241A.jsp BABA0M251A.jsp 新增 BADAPR 的資料
     */
    public int addManPayDataList(UserBean userData, CompPaymentCase compPaymentCase);
    
    /**
     * BABA0M242C.jsp BABA0M252C.jsp 更新 BADAPR 的資料
     */
    public int updManPayDataList2(UserBean userData, CompPaymentCase compPaymentCase);
    
    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 補正核付作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> qryChkManPay(String apNo, String issuYm);
    
    /**
     * BABA0M261A.jsp 更新 BADAPR 的資料
     */
    public int updManPayDataListForSurvivor(UserBean userData, CompPaymentCase compPaymentCase);
    
    /**
     * BABA0M261A.jsp 新增 BADAPR 的資料
     */
    public int addManPayDataListForSurvivor(UserBean userData, CompPaymentCase compPaymentCase);
    
    /**
     * BABA0M262C.jsp 更新 BADAPR 的資料
     */
    public int updManPayDataListForSurvivor2(UserBean userData, CompPaymentCase compPaymentCase);
    
}
