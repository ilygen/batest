package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.bj.cases.CompPaymentCase;
import tw.gov.bli.ba.dao.BadaprDao;
import tw.gov.bli.ba.domain.Badapr;
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
 * DAO for 給付核定檔 (<code>BADAPR</code>)
 * 
 * @author swim
 */
@DaoTable("BADAPR")
public class BadaprDaoImpl extends SqlMapClientDaoSupport implements BadaprDao {
    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
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

        List<StopPaymentProcessCase> returnList = getSqlMapClientTemplate().queryForList("BADAPR.selectData", map);

        if (returnList != null && returnList.size() > 0)
            return returnList.get(0);
        else
            return null;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>Badapr</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<StopPaymentProcessDetailCase> selectStopPaymentDetailListData(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (List<StopPaymentProcessDetailCase>) getSqlMapClientTemplate().queryForList("BADAPR.selectStopPaymentDetailData", map);
    }

    /**
     * 更新 BADAPR 的資料
     */
    public int updateData(Badapr data) {
        return getSqlMapClientTemplate().update("BADAPR.updateData", data);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Badapr</code> 物件
     */
    @DaoFieldList("APNO")
    public Badapr selectPayeeDataForBadapr(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.selectPayeeDataForBadapr", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Badapr</code> 物件
     */
    @DaoFieldList("APNO")
    public Badapr selectDisabledPayeeDataForBadapr(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.selectDisabledPayeeDataForBadapr", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Badapr</code> 物件
     */
    @DaoFieldList("APNO")
    public Badapr selectSurvivorPayeeDataHeaderForBadapr(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.selectSurvivorPayeeDataHeaderForBadapr", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Badapr</code> 物件
     */
    @DaoFieldList("APNO")
    public List<SurvivorPayeeDataUpdateCase> selectSurvivorPayeeDataListForBadapr(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectSurvivorPayeeDataListForBadapr", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料
     * 
     * @param apNo 受理編號
     * @param issuym 核定年月
     * @return 內含 <code>Badapr</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Badapr> selectPayDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 人工編審註記筆數
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
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BADAPR.selectManChkMkBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 前次核定金額
     * 
     * @param apNo 受理編號
     * @param issuCalcAmt 前次核定金額
     * @return 內含 <code>BigDecimal</code> 物件
     */
    @DaoFieldList("APNO")
    public Badapr selectOnceIssuCalcAmtBy(String apNo) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.selectOnceIssuCalcAmtBy", map);
    }

    /**
     * 更新 給付核定檔(<code>BADAPR</code>) 人工編審註記資料
     * 
     * @param badapr 給付核定檔
     */
    public void updateDataForReview(Badapr badapr) {
        getSqlMapClientTemplate().update("BADAPR.updateDataForReview", badapr);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Badapr getOldAgeReviewRpt01IssueAmtDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.getOldAgeReviewRpt01IssueAmtDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Badapr> getOldAgeReviewRpt01PayListBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.getOldAgeReviewRpt01PayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Badapr getOldAgeReviewRpt01LoanDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.getOldAgeReviewRpt01LoanDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Badapr getOldAgeReviewRpt01DecideDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.getOldAgeReviewRpt01DecideDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 年金給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public Badapr getOldAgeReviewRpt01AnnuityPayDataBy(String apNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(payYm))
            map.put("payYm", payYm);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.getOldAgeReviewRpt01AnnuityPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Badapr> getOldAgeReviewRpt01BenPayListBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.getOldAgeReviewRpt01BenPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 明細資料表頭-核定/給付年月資料
     * 
     * @param apNo 受理編號
     * @param startYm 查詢年月起
     * @param endYm 查詢年月迄
     * @return
     */
    @DaoFieldList("APNO,STARTYM,ENDYM")
    public List<Badapr> selectPaymentQueryIssuPayData(String apNo, String startYm, String endYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(startYm))
            map.put("startYm", startYm);
        if (StringUtils.isNotBlank(endYm))
            map.put("endYm", endYm);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectPaymentQueryIssuPayData", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 明細資料表頭-核定/給付年月資料
     * 
     * @param apNo 受理編號
     * @param startYm 查詢年月起
     * @param endYm 查詢年月迄
     * @param qryCond 查詢條件
     * @return 內含 <code>Badapr</code> 物件的 List
     */
    @DaoFieldList("APNO,STARTYM,ENDYM,QRYCOND")
    public List<Badapr> selectPaymentQueryIssuPayDataBy(String apNo, String startYm, String endYm, String qryCond) {
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

        map.put("varIn", ConstantKey.BADAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_IN);
        map.put("varOut", ConstantKey.BADAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_OUT);
        map.put("varMod", ConstantKey.BADAPR_PAYMENT_QUERY_ISSUPAY_DATA_SQL_VAR_MOD);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectPaymentQueryIssuPayDataBy", map);
    }

    // /**
    // * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 明細資料表頭-核定/給付年月資料
    // *
    // * @param apNo 受理編號
    // * @param startYm 查詢年月起
    // * @param endYm 查詢年月迄
    // * @param qryCond 查詢條件
    // * @return 內含 <code>Badapr</code> 物件的 List
    // */
    // @DaoFieldList("APNO,STARTYM,ENDYM,QRYCOND")
    // public List<Badapr> selectPaymentQueryIssuPayDataBy(String apNo, String startYm, String endYm, String qryCond) {
    // HashMap<String, String> map = new HashMap<String, String>();
    // if (StringUtils.isNotBlank(apNo))
    // map.put("apNo", apNo);
    // if (StringUtils.isNotBlank(startYm))
    // map.put("startYm", startYm);
    // if (StringUtils.isNotBlank(endYm))
    // map.put("endYm", endYm);
    // if (StringUtils.isNotBlank(qryCond))
    // map.put("qryCond", qryCond);
    // return getSqlMapClientTemplate().queryForList("BADAPR.selectPaymentQueryIssuPayDataBy", map);
    // }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 給付明細查詢 - 核定/給付資料明細
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return 內含 <code>Badapr</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public List<Badapr> selectPaymentQueryIssuPayDetailBy(String apNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(payYm))
            map.put("payYm", payYm);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectPaymentQueryIssuPayDetailBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢作業 給付明細查詢 - 年資資料
     * 
     * @param apNo 受理編號
     * @return 內含 <code>Badapr</code> 物件的 List
     */
    @DaoFieldList("APNO")
    public List<Badapr> selectSeniDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectSeniDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 止付單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param issuYm 核定年月
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Badapr> selectDataUpdateRpt05ForBadapr(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (List<Badapr>) getSqlMapClientTemplate().queryForList("BADAPR.selectDataUpdateRpt05ForBadapr", map);
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

        return getSqlMapClientTemplate().queryForList("BADAPR.selectMonthlyRpt01Report", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>MonthlyRpt01Case</code>) 資料 for 一次給付資料
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>Badapr</code> 物件的 List
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Badapr> selectOncePayDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectOncePayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 給付函核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Badapr> getMonthlyRpt05ListBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectMonthlyRpt05Report", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for BaReportReplaceUtility
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Badapr> selectDataForReportReplace(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectDataForReportReplace", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for BaReportReplaceUtility
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Badapr> selectDataForReportReplaceA140(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectDataForReportReplaceA140", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件的核定總額資料 for report replace
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

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BADAPR.selectReportReplaceDataTotal", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件的核定總額資料 for report replace 受理審核清單
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

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BADAPR.selectReportReplaceDataTotalForBalp010", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件的核定總額資料 for report replace 核定通知書
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

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BADAPR.selectReportReplaceDataTotalForA118", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件的核定總額資料 for report replace 核定通知書
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

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BADAPR.selectReportReplaceDataTotalForA122", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件的核定總額資料 for report replace 受理審核清單
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

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BADAPR.selectReportReplaceDataA139ForBalp010", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件的核定總額資料 for report replace
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

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BADAPR.selectReportReplaceDataForA139", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件的核定總額資料 for report replace 核定通知書
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Badapr> selectReportReplaceDataForA133(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectReportReplaceDataForA133", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件個受款人的核定總額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param seqNo 序號
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,SEQNO")
    public List<Badapr> selectReportReplaceDataPersonal(String apNo, String issuYm, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(issuYm))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectReportReplaceDataPersonal", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件個受款人的核定總額資料 for report replace 受理審核清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param seqNo 序號
     * @return
     */
    public List<Badapr> selectReportReplaceDataPersonalForBalp010(String apNo, String issuYm, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(issuYm))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectReportReplaceDataPersonalForBalp010", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 止付單
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

        return (String) getSqlMapClientTemplate().queryForObject("BADAPR.getDataUpdateRpt05ForAplpayAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核定清單
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

        return (MonthlyRpt06DetailCase) getSqlMapClientTemplate().queryForObject("BADAPR.selectMonthlyRpt06Report", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 最新1筆的核付日期
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public String selectMaxAplPayDate(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        return (String) getSqlMapClientTemplate().queryForObject("BADAPR.selectMaxAplPayDate", map);
    }

    /**
     * 更新合格註記 for 給付核定檔(<code>Badapr</code>)
     * 
     * @param apNo 受理編號
     * @param procTime 異動日期時間
     */
    public void updateManchkMk(String apNo, String procTime) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("procTime", procTime);
        getSqlMapClientTemplate().update("BADAPR.updateManchkMk", map);
    }

    /**
     * 刪除 給付核定檔 (<code>Badapr</code>) 的資料<br>
     * for 受款人資料更正
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public void deleteBadaprData(String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);

        getSqlMapClientTemplate().delete("BADAPR.deleteBadaprData", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 月核定撥付總表
     * 
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Badapr> selectMonthlyRpt04Report(String payCode, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        return (List<Badapr>) getSqlMapClientTemplate().queryForList("BADAPR.selectMonthlyRpt04Report", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Badapr getDisableReviewRpt01IssueAmtDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.getDisableReviewRpt01IssueAmtDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Badapr> getDisableReviewRpt01BenPayListBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.getDisableReviewRpt01BenPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Badapr> getDisableReviewRpt01BenByPayListBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.getDisableReviewRpt01BenByPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單(已扣失能)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getDisableReviewRpt01BenPayDataByLecomAmt(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BADAPR.getDisableReviewRpt01BenPayDataByLecomAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單(已扣失能)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getSurvivorReviewRpt01BenPayDataByLecomAmt(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BADAPR.getSurvivorReviewRpt01BenPayDataByLecomAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單(未扣失能)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getSurvivorReviewRpt01BenPayDataByRecomAmt(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BADAPR.getSurvivorReviewRpt01BenPayDataByRecomAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Badapr> getDisableReviewRpt01BenListBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.getDisableReviewRpt01BenDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Badapr> getDisableReviewRpt01BenListFor38By(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.getDisableReviewRpt01BenListFor38By", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Badapr getDisableReviewRpt01DecideDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.getDisableReviewRpt01DecideDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public Badapr getDisableReviewRpt01AnnuityPayDataBy(String apNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.getDisableReviewRpt01AnnuityPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Badapr> getDisableReviewRpt01PayListBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.getDisableReviewRpt01PayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Badapr> getDisableReviewRpt01LoanAmt(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectDisableRevieewRpt01LoanAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 核定總額資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Badapr selectSurvivorReviewRpt01IssueAmtDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.selectSurvivorReviewRpt01IssueAmtDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Badapr getSurvivorReviewRpt01DecideDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.getSurvivorReviewRpt01DecideDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Badapr> getSurvivorReviewRpt01BenPayListBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.getSurvivorReviewRpt01BenPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Badapr> selectSurvivorReviewRpt01PayDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectSurvivorReviewRpt01PayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 年金給付資料/請領同類給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public Badapr selectSurvivorReviewRpt01DateDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.selectSurvivorReviewRpt01DateDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Badapr> getSurvivorReviewRpt01OldPayListBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectSurvivorReviewRpt01OldPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 受款人給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param payYm 給付年月
     * @return
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM")
    public List<Badapr> selectSurvivorReviewRpt01BenPayDataBy(String apNo, String seqNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectSurvivorReviewRpt01BenPayDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Badapr> getSurvivorReviewRpt01LoanAmt(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectSurvivorRevieewRpt01LoanAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 職災相關資料 for 失能年金給付審核作業
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Badapr selectOccAccDataForDisabledPaymentReview(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.selectOccAccDataForDisabledPaymentReview", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 給付年月下拉選單 FOR 遺屬年金給付審核
     * 
     * @param APNO 受理編號
     * @param ISSUYM 核定年月
     * @return 內含<code>Badapr</code> 物件的List
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Badapr> selectPayYmListForSurvivorPaymentReview(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectPayYmListForSurvivorPaymentReview", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 非本人的受款人筆數 for 失能年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Integer getDisabledApplicationDataUpdateBenCountBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BADAPR.getDisabledApplicationDataUpdateBenCountBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 受款人筆數 for 止付單
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
        return (Integer) getSqlMapClientTemplate().queryForObject("BADAPR.getRpt05PeopleCountBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 受款人筆數 for 止付單
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
        return (Integer) getSqlMapClientTemplate().queryForObject("BADAPR.getRpt05PeopleCountForSBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 月核後受款人筆數 for 失能年金案件資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Integer getDisabledApplicationDataUpdateBenCountAfterCheckBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (Integer) getSqlMapClientTemplate().queryForObject("BADAPR.getDisabledApplicationDataUpdateBenCountAfterCheckBy", map);
    }

    /**
     * 更新 給付核定檔(<code>BADAPR</code>) 事故者核定資料 FOR 遺屬年金給付審核
     * 
     * @param badapr 給付主擋
     */
    public void updateEvtDataForSurvivorPaymentReview(Badapr badapr) {
        getSqlMapClientTemplate().update("BADAPR.updateEvtDataForSurvivorPaymentReview", badapr);
    }

    /**
     * 更新 給付核定檔(<code>BADAPR</code>) 受款人核定資料 FOR 遺屬年金給付審核
     * 
     * @param badapr 給付主擋
     */
    public void updateBenDataForSurvivorPaymentReview(Badapr badapr) {
        getSqlMapClientTemplate().update("BADAPR.updateBenDataForSurvivorPaymentReview", badapr);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
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

        if (getSqlMapClientTemplate().queryForObject("BADAPR.getDisableReviewRpt01BenDataBySum", map) != null && (Integer) getSqlMapClientTemplate().queryForObject("BADAPR.getDisableReviewRpt01BenDataBySum", map) > 0)
            return true;
        else
            return false;
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getSurvivorReviewRpt01BenPayDataByOldbAmt(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BADAPR.getSurvivorReviewRpt01BenPayDataByOldbAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 最新紓困貸款資料
     * 
     * @param apNo 受理編號
     * @param payKind 給付種類
     * @return
     */
    @DaoFieldList("APNO,PAYKIND")
    public List<Badapr> selectPaymentQueryLoanMasterDataBy(String apNo, String payKind353638) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        if (StringUtils.isNotBlank(payKind353638))
            map.put("payKind353638", payKind353638);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectPaymentQueryLoanMasterDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 最新紓困貸款資料 (BAAPPBASE.LSCHKMK = 4)
     * 
     * @param apNo 受理編號
     * @param payKind 給付種類
     * @return
     */
    @DaoFieldList("APNO,PAYKIND")
    public List<Badapr> selectPaymentQueryLoanMasterDataForLsChkMk4By(String apNo, String payKind353638) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        if (StringUtils.isNotBlank(payKind353638))
            map.put("payKind353638", payKind353638);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectPaymentQueryLoanMasterDataForLsChkMk4By", map);
    }

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
    @DaoFieldList("APNO,QRYCOND,STARTYM,ENDYM,PAYKIND")
    public List<Badapr> selectPaymentQueryLoanDetailDataBy(String apNo, String qryCond, String startYm, String endYm, String payKind353638) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("qryCond", qryCond);
        if (StringUtils.isNotBlank(startYm))
            map.put("startYm", startYm);
        if (StringUtils.isNotBlank(apNo))
            map.put("endYm", endYm);
        if (StringUtils.isNotBlank(payKind353638))
            map.put("payKind353638", payKind353638);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectPaymentQueryLoanDetailDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 平均薪資 - 年資資料
     * 
     * @param apNo 受理編號
     * @param payKind 給付種類
     * @return
     */
    @DaoFieldList("APNO,PAYKIND")
    public List<Badapr> selectPaymentQuerySeniDataBy(String apNo, String payKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        if (StringUtils.isNotBlank(payKind))
            map.put("payKind", payKind);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectPaymentQuerySeniDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 給付查詢 平均薪資 - 年資資料 (老年年金)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Badapr> selectPaymentQuerySeniDataForOldAge(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectPaymentQuerySeniDataForOldAge", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 刪除鈕狀態 for 眷屬資料更正
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getDependantDeleteStatus(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BADAPR.getDependantDeleteStatus", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 核定金額 for 給付查詢
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal selectBefissueAmtForPaymentQuery(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BADAPR.selectBefissueAmtForPaymentQuery", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 物價指數調整資料 for 給付查詢
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO,PAYCODE")
    public List<Badapr> selectCpiDataForPaymentQuery(String apNo, String paycode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("paycode", paycode);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectCpiDataForPaymentQueryNew", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 職災相關資料 for 失能年金給付審核清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Badapr selectOccAccDataForDisabledReviewRpt01(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (Badapr) getSqlMapClientTemplate().queryForObject("BADAPR.selectOccAccDataForDisabledReviewRpt01", map);
    }

    /**
     * 更新 給付核定檔(<code>BADAPR</code>) 退匯金額分配資料
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
        getSqlMapClientTemplate().update("BADAPR.updateRepayIssueAmtDataForDeathRepay", map);
    }

    /**
     * 刪除 給付核定檔 (<code>Badapr</code>) 的資料<br>
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
        
        getSqlMapClientTemplate().delete("BADAPR.deleteRepayIssueAmtDataForDeathRepay", map);
    }

    /**
     * 新增 給付核定檔(<code>Badapr</code>) 資料 for 死亡改匯處理作業
     * 
     * @param Badapr 給付核定檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForDeathRepay(Badapr data) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BADAPR.insertDataForDeathRepay", data);
    }

    /**
     * 更新 給付核定檔(<code>BADAPR</code>) 退匯金額分配資料
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
        getSqlMapClientTemplate().update("BADAPR.updateOldAgeDeathRepayDataForDeathRepay", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件個受款人的原核定金額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public HashMap<String, Object> selectReportReplaceDataForBadaprBefAmt(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BADAPR.selectReportReplaceDataForBadaprBefAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 同一核定年月補發部分金額之給付年月補發金額加總 for report replace
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

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BADAPR.selectReportReplaceDataForSupData", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件個受款人的核定年月總核付金額資料 for report replace
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

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BADAPR.selectReportReplaceDataForToalIssuAmt", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 線上月核定作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return 內含 <code>BigDecimal</code> 物件
     */
    public BigDecimal selectCountForMonthBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BADAPR.selectCountForMonthBy", map);
    }

    /**
     * 依傳入的條件計算 核定檔 (<code>BADAPR</code>) 已領其他年金金額資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Badapr> selectDabAnnuDataBy(String apNo, String evtJobYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("evtJobYm", evtJobYm);
        return getSqlMapClientTemplate().queryForList("BADAPR.selectDabAnnuDataBy", map);
    }

    /**
     * 依傳入條件取得 核定檔(<code>BADAPR</code>) 資料清單 For 穿透案件統計表
     * 
     * @param issuYm 核定年月
     * @param issuDate
     * @param preIssuDate
     * @return 內含 <code>Badapr</code> 物件的 List
     */
    public List<Badapr> selectOtherRpt10DataBy(String issuYm, String issuDate, String preIssuDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("issuYm", issuYm);
        map.put("issuDate", issuDate);
        map.put("preIssuDate", preIssuDate);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectOtherRpt10DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 止付單
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

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BADAPR.getSumAplpayAmtForAccRel3", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件個受款人的核定總額資料 for report replace
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Badapr> selectReportReplaceDataForA045S(String apNo, String issuYm) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BADAPR.selectReportReplaceDataForA045S", map);
    }

    /**
     * 依傳入條件取得 補發金額(<code>BADAPR</code>)
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public BigDecimal selectReportReplaceDataForBadaprSupAmt(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BADAPR.getSupAmtForA141", map);
    }

    /**
     * 依傳入條件取得 本月核定合格及不合格人數 (A143) (<code>BADAPR</code>)
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

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BADAPR.getPeopleNumberForA143", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 老年差額金通知
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
        return (String) getSqlMapClientTemplate().queryForObject("BADAPR.getMaxPayYmForL021", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件的核定總額資料 for report replace 老年差額金通知
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

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BADAPR.selectRptReplaceForPayYmL012", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 受理案件的核定總額資料 for report replace 遺屬年金部分不合格核定通知書
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

        return (HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("BADAPR.selectRptReplaceForPayYmA164", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 逾期未決行案件管制表
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
        return (String) getSqlMapClientTemplate().queryForObject("BADAPR.getChkListForAuditRpt01", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 逾期未決行案件管制表
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
        return (String) getSqlMapClientTemplate().queryForObject("BADAPR.getPreChkListForAuditRpt01", map);
    }
    
    /**
     * BABA0M241A.jsp BABA0M251A.jsp 更新 BADAPR 的資料
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
    	
    	return getSqlMapClientTemplate().update("BADAPR.updManPayDataList", map);
    }
    
    /**
     * BABA0M241A.jsp BABA0M251A.jsp 新增 BADAPR 的資料
     */
    public int addManPayDataList(UserBean userData, CompPaymentCase compPaymentCase) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("chkDate", compPaymentCase.getChkDate());
    	map.put("aplpayDate", compPaymentCase.getAplPayDate());
    	map.put("remitDate", compPaymentCase.getRemitDate());
    	map.put("apNo", compPaymentCase.getApNo());
    	map.put("issuYm", compPaymentCase.getIssuYm());
    	map.put("manPayChgMan", userData.getEmpNo());
    	
    	return getSqlMapClientTemplate().update("BADAPR.addManPayDataList", map);
    }
    
    /**
     * BABA0M242C.jsp BABA0M252C.jsp 更新 BADAPR 的資料
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
    	
    	return getSqlMapClientTemplate().update("BADAPR.updManPayDataList2", map);
    }
    
    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 補正核付作業
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    public List<Badapr> qryChkManPay(String apNo, String issuYm) {
       	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("apNo", apNo);
    	map.put("issuYm", issuYm);
    	return (List<Badapr>) getSqlMapClientTemplate().queryForList("BADAPR.qryChkManPay", map);
    }
    
    /**
     * BABA0M261A.jsp 更新 BADAPR 的資料
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
    	
    	return getSqlMapClientTemplate().update("BADAPR.updManPayDataListForSurvivor", map);
    }
    
    /**
     * BABA0M261A.jsp 新增 BADAPR 的資料
     */
    public int addManPayDataListForSurvivor(UserBean userData, CompPaymentCase compPaymentCase) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("chkDate", compPaymentCase.getChkDate());
    	map.put("aplpayDate", compPaymentCase.getAplPayDate());
    	map.put("remitDate", compPaymentCase.getRemitDate());
    	map.put("apNo", compPaymentCase.getApNo());
    	map.put("issuYm", compPaymentCase.getIssuYm());
    	map.put("manPayChgMan", userData.getEmpNo());
    	
    	return getSqlMapClientTemplate().update("BADAPR.addManPayDataListForSurvivor", map);
    }
    
    /**
     * BABA0M262C.jsp 更新 BADAPR 的資料
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
    	
    	return getSqlMapClientTemplate().update("BADAPR.updManPayDataListForSurvivor2", map);
    }
}
