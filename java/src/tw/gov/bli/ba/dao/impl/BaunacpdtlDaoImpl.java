package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BaunacpdtlDao;
import tw.gov.bli.ba.domain.Baunacpdtl;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 應收帳務明細檔 (<code>BAUNACPDTL</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BAUNACPDTL")
public class BaunacpdtlDaoImpl extends SqlMapClientDaoSupport implements BaunacpdtlDao {

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 應收查詢
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param unacpDate 應收立帳日期
     * @param issuYm 核定年月
     * @return 內含 <code>Baunacpdtl</code> 物件的 List
     */
    @DaoFieldList("APNO,EVTIDNNO,UNACPDATE,ISSUYM")
    public List<Baunacpdtl> selectReceivableDetailBy(String apNo, String evtIdnNo, String unacpDate, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(unacpDate))
            map.put("unacpDate", unacpDate);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        return getSqlMapClientTemplate().queryForList("BAUNACPDTL.selectReceivableDetailBy", map);
    }

    /**
     * 更新 應收帳務明細檔(<code>BAUNACPDTL</code>) 收回種類資料
     * 
     * @param Baunacpdtl 應收帳務明細檔
     */
    public void updateRecKind(Baunacpdtl baunacpdtl) {
        getSqlMapClientTemplate().update("BAUNACPDTL.updateRecKind", baunacpdtl);
    }

    /**
     * 更新 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 已收調整作業
     * 
     * @param Baunacprec 應收帳務記錄檔
     */
    public void updateForReceivableAdj(Baunacpdtl baunacpdtl) {
        getSqlMapClientTemplate().update("BAUNACPDTL.updateForReceivableAdj", baunacpdtl);
    }

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 另案扣減資料 - 年金給付 for 勞保老年年金給付受理編審清單
     * 
     * @param benIdnNo 受益人身分證號
     * @return
     */
    @DaoFieldList("BENIDNNO")
    public List<Baunacpdtl> selectOldAgeReviewRpt01DeductAnnuityListBy(String benIdnNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(benIdnNo))
            map.put("benIdnNo", benIdnNo);

        return getSqlMapClientTemplate().queryForList("BAUNACPDTL.selectOldAgeReviewRpt01DeductAnnuityListBy", map);
    }

    /**
     * 依傳入條件取得 另案扣減(<code>BAUNACPDTL</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param BENIDNNO 受益人身分證號
     * @return
     */
    public List<Baunacpdtl> selectDisableReviewRpt01PayDeductListBy(String benIdnNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("benIdnNo", benIdnNo);
        return (List<Baunacpdtl>) getSqlMapClientTemplate().queryForList("BAUNACPDTL.selectDisableReviewRpt01PayDeductListBy", map);
    }

    /**
     * 依傳入條件取得 另案扣減(<code>BAUNACPDTL</code>) 資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param BENIDNNO 受益人身分證號
     * @return
     */
    public List<Baunacpdtl> selectSurvivorReviewRpt01PayDeductListBy(String benIdnNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("benIdnNo", benIdnNo);
        return (List<Baunacpdtl>) getSqlMapClientTemplate().queryForList("BAUNACPDTL.selectDisableReviewRpt01PayDeductListBy", map);
    }

    /**
     * 依傳入條件取得 應收資料(<code>BAUNACPDTL</code>) 資料 for 老年年金應收收回處理
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Baunacpdtl> selectAccountsReceivableDataListBy(String apNo, String seqNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        if (StringUtils.isNotBlank(payYm))
            map.put("payYm", payYm);

        return getSqlMapClientTemplate().queryForList("BAUNACPDTL.selectAccountsReceivableDataListBy", map);
    }

    /**
     * 更新 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 應收收回處理作業
     * 
     * @param recRem 未收總金額
     * @param actEndMk 應收結案註記
     * @param baunacpdtlId 資料列編號(應收帳務明細編號)
     * @param Baunacpdtl 應收帳務明細檔
     */
    public void updateBaunacpdtlForPaymentReceive(BigDecimal recRem, String actEndMk, BigDecimal baunacpdtlId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("recRem", recRem);
        map.put("actEndMk", actEndMk);
        map.put("baunacpdtlId", baunacpdtlId);
        getSqlMapClientTemplate().update("BAUNACPDTL.updateBaunacpdtlForPaymentReceive", map);
    }

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 已退匯或退回現金尚未沖轉收回案件清單
     * 
     * @param payCode 給付別
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return BigDecimal
     */
    public BigDecimal selectOtherRpt02RecRemDataBy(String payCode, String apNo, String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAUNACPDTL.selectOtherRpt02RecRemDataBy", map);
    }

    /**
     * 依傳入條件取得 應收資料(<code>BAUNACPDTL</code>) 資料 for 應收收回處理作業
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Baunacpdtl> selectOtherRpt02SourceDataListBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAUNACPDTL.selectOtherRpt02SourceDataListBy", map);
    }

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for 應收收回處理作業 - 退現
     * 
     * @param payCode 給付別
     * @return
     */
    public List<Baunacpdtl> selectOtherRpt02CashDataListBy(String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("apNo", payCode);

        return getSqlMapClientTemplate().queryForList("BAUNACPDTL.selectOtherRpt02CashDataListBy", map);
    }

    /**
     * 更新 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料
     * 
     * @param baunacpdtl 退現資料檔
     */
    public void updateBaunacpdtlForCancelCashReceive(Baunacpdtl baunacpdtl) {

        getSqlMapClientTemplate().update("BAUNACPDTL.updateBaunacpdtlForCancelCashReceive", baunacpdtl);
    }

    /**
     * 依傳入條件取得 應收帳務明細檔(<code>BAUNACPDTL</code>) 資料 for for 應收收回處理作業 - 退現收回註銷
     * 
     * @param baunacpdtlId
     * @return
     */
    public Baunacpdtl selectDataForPaymentReceiveBy(BigDecimal baunacpdtlId) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (baunacpdtlId != null)
            map.put("baunacpdtlId", baunacpdtlId);

        return (Baunacpdtl) getSqlMapClientTemplate().queryForObject("BAUNACPDTL.selectDataForPaymentReceiveBy", map);
    }

    public String execRptOverdue(String payCode, String procYm, String paySeqNo, String cprnDt, String rptTyp, String apNo, String deadYy) {
        String rowCount = null;
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(payCode))
            map.put("v_i_paycode", payCode);
        if (StringUtils.isNotBlank(procYm))
            map.put("v_i_procYm", procYm);
        if (StringUtils.isNotBlank(cprnDt))
            map.put("v_i_cprnDt", cprnDt);
        if (StringUtils.isNotBlank(rptTyp))
            map.put("v_i_rpttyp", rptTyp);
        if (StringUtils.isNotBlank(apNo))
            map.put("v_i_apno", apNo);
        if (StringUtils.isNotBlank(deadYy))
            map.put("v_i_deadYy", deadYy);

        map.put("v_o_flag", "");

        getSqlMapClientTemplate().queryForObject("BAUNACPDTL.execRptOverdue", map);
        rowCount = (String) map.get("v_o_flag");

        return rowCount;
    }

    /**
     * 依傳入條件取得 應收未收資料
     * 
     * @param apno 受理編號
     * @return
     */
    public List<Baunacpdtl> selectUnacpdtlDataList(String apno) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apno)) {
            map.put("apno", apno);
        }
        return (List<Baunacpdtl>) getSqlMapClientTemplate().queryForList("BAUNACPDTL.selectUnacpdtlDataList", map);
    }

    public List<Baunacpdtl> getBadDebtDtlDataBy(String payCode, String apNo, String isNull, String isEqual) {
        // 修改FORTIFY
        isNull = StringUtility.changOperator(isNull);
        isEqual = StringUtility.changOperator(isEqual);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(isNull))
            map.put("isNull", isNull);
        if (StringUtils.isNotBlank(isEqual))
            map.put("isEqual", isEqual);
        return (List<Baunacpdtl>) getSqlMapClientTemplate().queryForList("BAUNACPDTL.selectBadDebtDtlDataBy", map);
    }

    public void updateDataForBadDebt(Baunacpdtl baunacpdtl) {

        getSqlMapClientTemplate().update("BAUNACPDTL.updateDataForBadDebt", baunacpdtl);
    }
}
