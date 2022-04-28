package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.CompPaymentCase;
import tw.gov.bli.ba.dao.BbdaprDao;
import tw.gov.bli.ba.domain.Bbdapr;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt01Case;
import tw.gov.bli.ba.rpt.cases.MonthlyRpt06DetailCase;
import tw.gov.bli.ba.update.cases.StopPaymentProcessCase;
import tw.gov.bli.ba.update.cases.StopPaymentProcessDetailCase;
import tw.gov.bli.ba.update.cases.SurvivorPayeeDataUpdateCase;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 給付核定檔 (<code>BBDAPR</code>)
 * 
 * @author swim
 */
@DaoTable("BBDAPR")
public class BbdaprDaoImpl extends SqlMapClientDaoSupport implements BbdaprDao {
    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>StopPaymentProcessCase</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM")
    public StopPaymentProcessCase selectData(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        List<StopPaymentProcessCase> returnList = getSqlMapClientTemplate().queryForList("BBDAPR.selectData", map);

        if (returnList != null && returnList.size() > 0)
            return returnList.get(0);
        else
            return null;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<StopPaymentProcessDetailCase> selectStopPaymentDetailListData(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (List<StopPaymentProcessDetailCase>) getSqlMapClientTemplate().queryForList("BBDAPR.selectStopPaymentDetailData", map);
    }

    /**
     * 更新 BBDAPR 的資料
     */
    public int updateData(Bbdapr data) {
        return getSqlMapClientTemplate().update("BBDAPR.updateData", data);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Bbdapr</code> 物件
     */
    @DaoFieldList("APNO")
    public Bbdapr selectPayeeDataForBbdapr(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.selectPayeeDataForBbdapr", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Bbdapr</code> 物件
     */
    @DaoFieldList("APNO")
    public Bbdapr selectDisabledPayeeDataForBbdapr(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.selectDisabledPayeeDataForBbdapr", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Bbdapr</code> 物件
     */
    @DaoFieldList("APNO")
    public Bbdapr selectSurvivorPayeeDataHeaderForBbdapr(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.selectSurvivorPayeeDataHeaderForBbdapr", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Bbdapr</code> 物件
     */
    @DaoFieldList("APNO")
    public List<SurvivorPayeeDataUpdateCase> selectSurvivorPayeeDataListForBbdapr(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectSurvivorPayeeDataListForBbdapr", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @param issuym 核定年月
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bbdapr> selectPayDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 人工編審註記筆數
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param qryTyp 查詢方式 1:(MANCHKMK IS NULL OR MANCHKMK=' ') ; 2:MANCHKMK = 'N'
     * @return 內含 <code>BigDecimal</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM,QRYTYP")
    public BigDecimal selectManChkMkBy(String apNo, String issuYm, String qryTyp) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        map.put("qryTyp", qryTyp);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BBDAPR.selectManChkMkBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 前次核定金額
     * 
     * @param apNo 受理編號
     * @param issuCalcAmt 前次核定金額
     * @return 內含 <code>BigDecimal</code> 物件
     */
    @DaoFieldList("APNO")
    public Bbdapr selectOnceIssuCalcAmtBy(String apNo) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.selectOnceIssuCalcAmtBy", map);
    }

    /**
     * 更新 給付核定檔(<code>BBDAPR</code>) 人工編審註記資料
     * 
     * @param badapr 給付核定檔
     */
    public void updateDataForReview(Bbdapr badapr) {
        getSqlMapClientTemplate().update("BBDAPR.updateDataForReview", badapr);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Bbdapr getOldAgeReviewRpt01IssueAmtDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.getOldAgeReviewRpt01IssueAmtDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bbdapr> getOldAgeReviewRpt01PayListBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.getOldAgeReviewRpt01PayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Bbdapr getOldAgeReviewRpt01LoanDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.getOldAgeReviewRpt01LoanDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Bbdapr getOldAgeReviewRpt01DecideDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.getOldAgeReviewRpt01DecideDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 年金給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public Bbdapr getOldAgeReviewRpt01AnnuityPayDataBy(String apNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(payYm))
            map.put("payYm", payYm);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.getOldAgeReviewRpt01AnnuityPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bbdapr> getOldAgeReviewRpt01BenPayListBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.getOldAgeReviewRpt01BenPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 給付查詢作業 明細資料表頭-核定/給付年月資料
     * 
     * @param apNo 受理編號
     * @param startYm 查詢年月起
     * @param endYm 查詢年月迄
     * @return
     */
    @DaoFieldList("APNO,STARTYM,ENDYM")
    public List<Bbdapr> selectPaymentQueryIssuPayData(String apNo, String startYm, String endYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(startYm))
            map.put("startYm", startYm);
        if (StringUtils.isNotBlank(endYm))
            map.put("endYm", endYm);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectPaymentQueryIssuPayData", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 給付查詢作業 明細資料表頭-核定/給付年月資料
     * 
     * @param apNo 受理編號
     * @param startYm 查詢年月起
     * @param endYm 查詢年月迄
     * @param qryCond 查詢條件
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    @DaoFieldList("APNO,STARTYM,ENDYM,QRYCOND")
    public List<Bbdapr> selectPaymentQueryIssuPayDataBy(String apNo, String startYm, String endYm, String qryCond) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(startYm))
            map.put("startYm", startYm);
        if (StringUtils.isNotBlank(endYm))
            map.put("endYm", endYm);
        // if (StringUtils.isNotBlank(evtIdnNo))
        // map.put("evtIdnNo", evtIdnNo);
        // if (StringUtils.isNotBlank(evtBrDate))
        // map.put("evtBrDate", evtBrDate);
        // if (StringUtils.isNotBlank(evtName))
        // map.put("evtName", evtName);
        if (StringUtils.isNotBlank(qryCond))
            map.put("qryCond", qryCond);

        map.put("varIn", ConstantKey.BBDAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_IN);
        map.put("varOut", ConstantKey.BBDAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_OUT);
        map.put("varMod", ConstantKey.BBDAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_MOD);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectPaymentQueryIssuPayDataBy", map);
    }

    // /**
    // * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 給付查詢作業 明細資料表頭-核定/給付年月資料
    // *
    // * @param apNo 受理編號
    // * @param startYm 查詢年月起
    // * @param endYm 查詢年月迄
    // * @param qryCond 查詢條件
    // * @return 內含 <code>Bbdapr</code> 物件的 List
    // */
    // @DaoFieldList("APNO,STARTYM,ENDYM,QRYCOND")
    // public List<Bbdapr> selectPaymentQueryIssuPayDataBy(String apNo, String startYm, String endYm, String qryCond) {
    // HashMap<String, String> map = new HashMap<String, String>();
    // if (StringUtils.isNotBlank(apNo))
    // map.put("apNo", apNo);
    // if (StringUtils.isNotBlank(startYm))
    // map.put("startYm", startYm);
    // if (StringUtils.isNotBlank(endYm))
    // map.put("endYm", endYm);
    // if (StringUtils.isNotBlank(qryCond))
    // map.put("qryCond", qryCond);
    // return getSqlMapClientTemplate().queryForList("BBDAPR.selectPaymentQueryIssuPayDataBy", map);
    // }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 給付查詢作業 給付明細查詢 - 核定/給付資料明細
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public List<Bbdapr> selectPaymentQueryIssuPayDetailBy(String apNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(payYm))
            map.put("payYm", payYm);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectPaymentQueryIssuPayDetailBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 給付查詢作業 給付明細查詢 - 年資資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<Bbdapr> selectSeniDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectSeniDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 止付單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bbdapr> selectDataUpdateRpt05ForBbdapr(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (List<Bbdapr>) getSqlMapClientTemplate().queryForList("BBDAPR.selectDataUpdateRpt05ForBbdapr", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>MonthlyRpt01Case</code>) 資料 for 月處理核定合格清冊
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<MonthlyRpt01Case> getMonthlyRpt01ListBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectMonthlyRpt01Report", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>MonthlyRpt01Case</code>) 資料 for 一次給付資料
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bbdapr> selectOncePayDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectOncePayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 給付函核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bbdapr> getMonthlyRpt05ListBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectMonthlyRpt05Report", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for BaReportReplaceUtility
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> selectDataForReportReplace(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectDataForReportReplace", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for BaReportReplaceUtility
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> selectDataForReportReplaceA140(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectDataForReportReplaceA140", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public HashMap<String, Object> selectReportReplaceDataTotal(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BBDAPR.selectReportReplaceDataTotal", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 受理審核清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataTotalForBalp010(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BBDAPR.selectReportReplaceDataTotalForBalp010", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataTotalForA118(String apNo, String issuYm, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(eqType))
            map.put("eqType", eqType);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BBDAPR.selectReportReplaceDataTotalForA118", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataTotalForA122(String apNo, String issuYm, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(eqType))
            map.put("eqType", eqType);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BBDAPR.selectReportReplaceDataTotalForA122", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 受理審核清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataA139ForBalp010(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BBDAPR.selectReportReplaceDataA139ForBalp010", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public HashMap<String, Object> selectReportReplaceDataForA139(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BBDAPR.selectReportReplaceDataForA139", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> selectReportReplaceDataForA133(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectReportReplaceDataForA133", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件個受款人的核定總額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param seqNo 序號
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,SEQNO")
    public List<Bbdapr> selectReportReplaceDataPersonal(String apNo, String issuYm, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(issuYm))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectReportReplaceDataPersonal", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件個受款人的核定總額資料 for report replace 受理審核清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param seqNo 序號
     * @return
     */
    public List<Bbdapr> selectReportReplaceDataPersonalForBalp010(String apNo, String issuYm, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(issuYm))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectReportReplaceDataPersonalForBalp010", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 止付單
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public String getDataUpdateRpt05ForAplpayAmt(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (String) getSqlMapClientTemplate().queryForObject("BBDAPR.getDataUpdateRpt05ForAplpayAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 核定清單
     * 
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public MonthlyRpt06DetailCase getMonthlyRpt06DataBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (MonthlyRpt06DetailCase) getSqlMapClientTemplate().queryForObject("BBDAPR.selectMonthlyRpt06Report", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 最新1筆的核付日期
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public String selectMaxAplPayDate(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        return (String) getSqlMapClientTemplate().queryForObject("BBDAPR.selectMaxAplPayDate", map);
    }

    /**
     * 更新合格註記 for 給付核定檔(<code>Bbdapr</code>)
     * 
     * @param apNo 受理編號
     * @param procTime 異動日期時間
     */
    public void updateManchkMk(String apNo, String procTime) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("procTime", procTime);
        getSqlMapClientTemplate().update("BBDAPR.updateManchkMk", map);
    }

    /**
     * 刪除 給付核定檔 (<code>Bbdapr</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public void deleteBbdaprData(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        getSqlMapClientTemplate().delete("BBDAPR.deleteBbdaprData", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 月核定撥付總表
     * 
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bbdapr> selectMonthlyRpt04Report(String payCode, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        return (List<Bbdapr>) getSqlMapClientTemplate().queryForList("BBDAPR.selectMonthlyRpt04Report", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Bbdapr getDisableReviewRpt01IssueAmtDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.getDisableReviewRpt01IssueAmtDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bbdapr> getDisableReviewRpt01BenPayListBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.getDisableReviewRpt01BenPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bbdapr> getDisableReviewRpt01BenByPayListBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.getDisableReviewRpt01BenByPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保失能年金給付受理編審清單(已扣失能)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getDisableReviewRpt01BenPayDataByLecomAmt(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BBDAPR.getDisableReviewRpt01BenPayDataByLecomAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單(已扣失能)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getSurvivorReviewRpt01BenPayDataByLecomAmt(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BBDAPR.getSurvivorReviewRpt01BenPayDataByLecomAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單(未扣失能)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getSurvivorReviewRpt01BenPayDataByRecomAmt(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BBDAPR.getSurvivorReviewRpt01BenPayDataByRecomAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bbdapr> getDisableReviewRpt01BenListBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.getDisableReviewRpt01BenDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bbdapr> getDisableReviewRpt01BenListFor38By(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.getDisableReviewRpt01BenListFor38By", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Bbdapr getDisableReviewRpt01DecideDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.getDisableReviewRpt01DecideDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public Bbdapr getDisableReviewRpt01AnnuityPayDataBy(String apNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.getDisableReviewRpt01AnnuityPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bbdapr> getDisableReviewRpt01PayListBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.getDisableReviewRpt01PayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bbdapr> getDisableReviewRpt01LoanAmt(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectDisableRevieewRpt01LoanAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 核定總額資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Bbdapr selectSurvivorReviewRpt01IssueAmtDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.selectSurvivorReviewRpt01IssueAmtDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Bbdapr getSurvivorReviewRpt01DecideDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.getSurvivorReviewRpt01DecideDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bbdapr> getSurvivorReviewRpt01BenPayListBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.getSurvivorReviewRpt01BenPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bbdapr> selectSurvivorReviewRpt01PayDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectSurvivorReviewRpt01PayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 年金給付資料/請領同類給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public Bbdapr selectSurvivorReviewRpt01DateDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.selectSurvivorReviewRpt01DateDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bbdapr> getSurvivorReviewRpt01OldPayListBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectSurvivorReviewRpt01OldPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 受款人給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Bbdapr> selectSurvivorReviewRpt01BenPayDataBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectSurvivorReviewRpt01BenPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bbdapr> getSurvivorReviewRpt01LoanAmt(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectSurvivorRevieewRpt01LoanAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 職災相關資料 for 失能年金給付審核作業
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Bbdapr selectOccAccDataForDisabledPaymentReview(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.selectOccAccDataForDisabledPaymentReview", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 給付年月下拉選單 FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @param ISSUYM 核定年月
     * @return 內含<code>Bbdapr</code> 物件的List
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bbdapr> selectPayYmListForSurvivorPaymentReview(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectPayYmListForSurvivorPaymentReview", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 非本人的受款人筆數 for 失能年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Integer getDisabledApplicationDataUpdateBenCountBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BBDAPR.getDisabledApplicationDataUpdateBenCountBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 受款人筆數 for 止付單
     * 
     * @param apNo 受理編號
     * @param issuYm 受理編號
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Integer getRpt05PeopleCountBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (Integer) getSqlMapClientTemplate().queryForObject("BBDAPR.getRpt05PeopleCountBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 受款人筆數 for 止付單
     * 
     * @param apNo 受理編號
     * @param issuYm 受理編號
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Integer getRpt05PeopleCountForSBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (Integer) getSqlMapClientTemplate().queryForObject("BBDAPR.getRpt05PeopleCountForSBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 月核後受款人筆數 for 失能年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Integer getDisabledApplicationDataUpdateBenCountAfterCheckBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BBDAPR.getDisabledApplicationDataUpdateBenCountAfterCheckBy", map);
    }

    /**
     * 更新 給付核定檔(<code>BBDAPR</code>) 事故者核定資料 FOR 遺屬年金給付審核
     * 
     * @param badapr 給付主擋
     */
    public void updateEvtDataForSurvivorPaymentReview(Bbdapr badapr) {
        getSqlMapClientTemplate().update("BBDAPR.updateEvtDataForSurvivorPaymentReview", badapr);
    }

    /**
     * 更新 給付核定檔(<code>BBDAPR</code>) 受款人核定資料 FOR 遺屬年金給付審核
     * 
     * @param badapr 給付主擋
     */
    public void updateBenDataForSurvivorPaymentReview(Bbdapr badapr) {
        getSqlMapClientTemplate().update("BBDAPR.updateBenDataForSurvivorPaymentReview", badapr);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public Boolean getDisableReviewRpt01BenListBySum(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        if (getSqlMapClientTemplate().queryForObject("BBDAPR.getDisableReviewRpt01BenDataBySum", map) != null && (Integer) getSqlMapClientTemplate().queryForObject("BBDAPR.getDisableReviewRpt01BenDataBySum", map) > 0)
            return true;
        else
            return false;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getSurvivorReviewRpt01BenPayDataByOldbAmt(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BBDAPR.getSurvivorReviewRpt01BenPayDataByOldbAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 給付查詢 最新紓困貸款資料
     * 
     * @param apNo 受理編號
     * @param payKind 給付種類
     * @return
     */
    @DaoFieldList("APNO,PAYKIND")
    public List<Bbdapr> selectPaymentQueryLoanMasterDataBy(String apNo, String payKind353638) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        if (StringUtils.isNotBlank(payKind353638))
            map.put("payKind353638", payKind353638);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectPaymentQueryLoanMasterDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 給付查詢 最新紓困貸款資料 (BAAPPBASE.LSCHKMK = 4)
     * 
     * @param apNo 受理編號
     * @param payKind 給付種類
     * @return
     */
    @DaoFieldList("APNO,PAYKIND")
    public List<Bbdapr> selectPaymentQueryLoanMasterDataForLsChkMk4By(String apNo, String payKind353638) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        if (StringUtils.isNotBlank(payKind353638))
            map.put("payKind353638", payKind353638);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectPaymentQueryLoanMasterDataForLsChkMk4By", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 給付查詢 紓困貸款 核定資料
     * 
     * @param apNo 受理編號
     * @param qryCond 查詢條件
     * @param startYm 查詢年月起
     * @param endYm 查詢年月迄
     * @param payKind 給付種類
     * @return
     */
    @DaoFieldList("APNO,QRYCOND,STARTYM,ENDYM,PAYKIND")
    public List<Bbdapr> selectPaymentQueryLoanDetailDataBy(String apNo, String qryCond, String startYm, String endYm, String payKind353638) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("qryCond", qryCond);
        if (StringUtils.isNotBlank(startYm))
            map.put("startYm", startYm);
        if (StringUtils.isNotBlank(apNo))
            map.put("endYm", endYm);
        if (StringUtils.isNotBlank(payKind353638))
            map.put("payKind353638", payKind353638);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectPaymentQueryLoanDetailDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 給付查詢 平均薪資 - 年資資料
     * 
     * @param apNo 受理編號
     * @param payKind 給付種類
     * @return
     */
    @DaoFieldList("APNO,PAYKIND")
    public List<Bbdapr> selectPaymentQuerySeniDataBy(String apNo, String payKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        if (StringUtils.isNotBlank(payKind))
            map.put("payKind", payKind);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectPaymentQuerySeniDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 給付查詢 平均薪資 - 年資資料 (老年年金)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Bbdapr> selectPaymentQuerySeniDataForOldAge(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectPaymentQuerySeniDataForOldAge", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 刪除鈕狀態 for 眷屬資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getDependantDeleteStatus(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BBDAPR.getDependantDeleteStatus", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 核定金額 for 給付查詢
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal selectBefissueAmtForPaymentQuery(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BBDAPR.selectBefissueAmtForPaymentQuery", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 物價指數調整資料 for 給付查詢
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO,PAYCODE")
    public List<Bbdapr> selectCpiDataForPaymentQuery(String apNo, String paycode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("paycode", paycode);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectCpiDataForPaymentQueryNew", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 職災相關資料 for 失能年金給付審核清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Bbdapr selectOccAccDataForDisabledReviewRpt01(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.selectOccAccDataForDisabledReviewRpt01", map);
    }

    /**
     * 更新 給付核定檔(<code>BBDAPR</code>) 退匯金額分配資料
     * 
     * @param badapr 給付核定檔
     */
    public void updateRepayIssueAmtDataForDeathRepay(String apNo, String seqNo, String oriIssuYm, String payYm, String issuKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("oriIssuYm", oriIssuYm);
        map.put("payYm", payYm);
        map.put("issuKind", issuKind);
        getSqlMapClientTemplate().update("BBDAPR.updateRepayIssueAmtDataForDeathRepay", map);
    }

    /**
     * 刪除 給付核定檔 (<code>Bbdapr</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param oriIssuYm 核定年月
     * @param payYm 給付年月
     */
    public void deleteRepayIssueAmtDataForDeathRepay(String apNo, String seqNo, String oriIssuYm, String payYm, String issuKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("oriIssuYm", oriIssuYm);
        map.put("payYm", payYm);
        map.put("issuKind", issuKind);
        
        getSqlMapClientTemplate().delete("BBDAPR.deleteRepayIssueAmtDataForDeathRepay", map);
    }

    /**
     * 新增 給付核定檔(<code>Bbdapr</code>) 資料 for 死亡改匯處理作業
     * 
     * @param Bbdapr 給付核定檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForDeathRepay(Bbdapr data) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BBDAPR.insertDataForDeathRepay", data);
    }

    /**
     * 更新 給付核定檔(<code>BBDAPR</code>) 退匯金額分配資料
     * 
     * @param badapr 給付核定檔
     */
    public void updateOldAgeDeathRepayDataForDeathRepay(BigDecimal remitAmt, String brChkDate, String apNo, String seqNo, String oriIssuYm, String payYm, String issuKind) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("remitAmt", remitAmt);
        map.put("brChkDate", brChkDate);
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("oriIssuYm", oriIssuYm);
        map.put("payYm", payYm);
        map.put("issuKind", issuKind);
        getSqlMapClientTemplate().update("BBDAPR.updateOldAgeDeathRepayDataForDeathRepay", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件個受款人的原核定金額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataForBbdaprBefAmt(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BBDAPR.selectReportReplaceDataForBbdaprBefAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 同一核定年月補發部分金額之給付年月補發金額加總 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataForSupData(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BBDAPR.selectReportReplaceDataForSupData", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件個受款人的核定年月總核付金額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataForToalIssuAmt(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BBDAPR.selectReportReplaceDataForToalIssuAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 線上月核定作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>BigDecimal</code> 物件
     */
    public BigDecimal selectCountForMonthBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BBDAPR.selectCountForMonthBy", map);
    }

    /**
     * 依傳入的條件計算 核定檔 (<code>BBDAPR</code>) 已領其他年金金額資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Bbdapr> selectDabAnnuDataBy(String apNo, String evtJobYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("evtJobYm", evtJobYm);
        return getSqlMapClientTemplate().queryForList("BBDAPR.selectDabAnnuDataBy", map);
    }

    /**
     * 依傳入條件取得 核定檔(<code>BBDAPR</code>) 資料清單 For 穿透案件統計表
     * 
     * @param issuYm 核定年月
     * @param issuDate
     * @param preIssuDate
     * @return 內含 <code>Bbdapr</code> 物件的 List
     */
    public List<Bbdapr> selectOtherRpt10DataBy(String issuYm, String issuDate, String preIssuDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("issuYm", issuYm);
        map.put("issuDate", issuDate);
        map.put("preIssuDate", preIssuDate);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectOtherRpt10DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 資料 for 止付單
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public BigDecimal getSumAplpayAmtForAccRel3(String apNo, List<String> seqNoList, String issuYm, String payYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (seqNoList != null)
            map.put("seqNoList", seqNoList);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        if (StringUtils.isNotBlank(payYm))
            map.put("payYm", payYm);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BBDAPR.getSumAplpayAmtForAccRel3", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件個受款人的核定總額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bbdapr> selectReportReplaceDataForA045S(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BBDAPR.selectReportReplaceDataForA045S", map);
    }

    /**
     * 依傳入條件取得 補發金額(<code>BBDAPR</code>)
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public BigDecimal selectReportReplaceDataForBbdaprSupAmt(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BBDAPR.getSupAmtForA141", map);
    }

    /**
     * 依傳入條件取得 本月核定合格及不合格人數 (A143) (<code>BBDAPR</code>)
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public BigDecimal selectReportReplaceDataForPeopleNumberA143(String apNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(payYm))
            map.put("payYm", payYm);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BBDAPR.getPeopleNumberForA143", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 老年差額金通知
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public String selectRptReplaceForMaxPayYmL021(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (String) getSqlMapClientTemplate().queryForObject("BBDAPR.getMaxPayYmForL021", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 老年差額金通知
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectRptReplaceForPayYmL012(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BBDAPR.selectRptReplaceForPayYmL012", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bbdapr</code>) 受理案件的核定總額資料 for report replace 遺屬年金部分不合格核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectRptReplaceForPayYmA164(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BBDAPR.selectRptReplaceForPayYmA164", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 逾期未決行案件管制表
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public String selectChkListForAuditRpt01(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (String) getSqlMapClientTemplate().queryForObject("BBDAPR.getChkListForAuditRpt01", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 逾期未決行案件管制表
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public String selectPreChkListForAuditRpt01(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (String) getSqlMapClientTemplate().queryForObject("BBDAPR.getPreChkListForAuditRpt01", map);
    }
    
    /**
     * BABA0M241A.jsp BABA0M251A.jsp 更新 BBDAPR 的資料
     */
    public int updManPayDataList(UserBean userData, CompPaymentCase compPaymentCase) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("chkDate", compPaymentCase.getChkDate());
    	map.put("aplpayDate", compPaymentCase.getAplPayDate());
    	map.put("remitDate", compPaymentCase.getRemitDate());
    	map.put("apNo", compPaymentCase.getApNo());
    	map.put("procTime", compPaymentCase.getProcTime());
    	map.put("issuYm", compPaymentCase.getIssuYm());
    	map.put("manPayChgMan", userData.getEmpNo());
    	
    	return getSqlMapClientTemplate().update("BBDAPR.updManPayDataList", map);
    }
    
    /**
     * BABA0M241A.jsp BABA0M251A.jsp 新增 BBDAPR 的資料
     */
    public int addManPayDataList(UserBean userData, CompPaymentCase compPaymentCase) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("chkDate", compPaymentCase.getChkDate());
    	map.put("aplpayDate", compPaymentCase.getAplPayDate());
    	map.put("remitDate", compPaymentCase.getRemitDate());
    	map.put("apNo", compPaymentCase.getApNo());
    	map.put("issuYm", compPaymentCase.getIssuYm());
    	map.put("manPayChgMan", userData.getEmpNo());
    	
    	return getSqlMapClientTemplate().update("BBDAPR.addManPayDataList", map);
    }
    
    /**
     * BABA0M242C.jsp BABA0M252C.jsp 更新 BBDAPR 的資料
     */
    public int updManPayDataList2(UserBean userData, CompPaymentCase compPaymentCase) {
       	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("chkDate", compPaymentCase.getChkDate());
    	map.put("aplpayDate", compPaymentCase.getAplPayDate());
    	map.put("remitDate", compPaymentCase.getRemitDate());
    	map.put("apNo", compPaymentCase.getApNo());
    	map.put("procTime", compPaymentCase.getProcTime());
    	map.put("issuYm", compPaymentCase.getIssuYm());
    	map.put("manPayChgMan", userData.getEmpNo());
    	
    	return getSqlMapClientTemplate().update("BBDAPR.updManPayDataList2", map);
    }
    
    /**
     * 依傳入條件取得 給付核定檔(<code>BBDAPR</code>) 資料 for 補正核付作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Bbdapr> qryChkManPay(String apNo, String issuYm) {
       	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("apNo", apNo);
    	map.put("issuYm", issuYm);
    	return (List<Bbdapr>) getSqlMapClientTemplate().queryForList("BBDAPR.qryChkManPay", map);
    }
    
    /**
     * BABA0M261A.jsp 更新 BBDAPR 的資料
     */
    public int updManPayDataListForSurvivor(UserBean userData, CompPaymentCase compPaymentCase) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("chkDate", compPaymentCase.getChkDate());
    	map.put("aplpayDate", compPaymentCase.getAplPayDate());
    	map.put("remitDate", compPaymentCase.getRemitDate());
    	map.put("apNo", compPaymentCase.getApNo());
    	map.put("procTime", compPaymentCase.getProcTime());
    	map.put("issuYm", compPaymentCase.getIssuYm());
    	map.put("manPayChgMan", userData.getEmpNo());
    	
    	return getSqlMapClientTemplate().update("BBDAPR.updManPayDataListForSurvivor", map);
    }
    
    /**
     * BABA0M261A.jsp 新增 BBDAPR 的資料
     */
    public int addManPayDataListForSurvivor(UserBean userData, CompPaymentCase compPaymentCase) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("chkDate", compPaymentCase.getChkDate());
    	map.put("aplpayDate", compPaymentCase.getAplPayDate());
    	map.put("remitDate", compPaymentCase.getRemitDate());
    	map.put("apNo", compPaymentCase.getApNo());
    	map.put("issuYm", compPaymentCase.getIssuYm());
    	map.put("manPayChgMan", userData.getEmpNo());
    	
    	return getSqlMapClientTemplate().update("BBDAPR.addManPayDataListForSurvivor", map);
    }
    
    /**
     * BABA0M262C.jsp 更新 BBDAPR 的資料
     */
    public int updManPayDataListForSurvivor2(UserBean userData, CompPaymentCase compPaymentCase) {
       	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("chkDate", compPaymentCase.getChkDate());
    	map.put("aplpayDate", compPaymentCase.getAplPayDate());
    	map.put("remitDate", compPaymentCase.getRemitDate());
    	map.put("apNo", compPaymentCase.getApNo());
    	map.put("procTime", compPaymentCase.getProcTime());
    	map.put("issuYm", compPaymentCase.getIssuYm());
    	map.put("manPayChgMan", userData.getEmpNo());
    	
    	return getSqlMapClientTemplate().update("BBDAPR.updManPayDataListForSurvivor2", map);
    }
}
