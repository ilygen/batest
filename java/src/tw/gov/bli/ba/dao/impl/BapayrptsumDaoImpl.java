package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapayrptsumDao;
import tw.gov.bli.ba.domain.Bapayrptsum;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOImpl for 核付報表總表紀錄檔 (<code>BAPAYRPTSUM</code>)
 * 
 * @author Kiyomi
 */
@DaoTable("BAPAYRPTSUM")
public class BapayrptsumDaoImpl extends SqlMapClientDaoSupport implements BapayrptsumDao {

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 月核定撥付總表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptsum> selectMonthlyRpt04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("benEvtRel", benEvtRel);
        map.put("eqType", eqType);
        map.put("chkDate", chkDate);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectMonthlyRpt04Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 月核定撥付總表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptsum> selectMonthlyRptK04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("benEvtRel", benEvtRel);
        map.put("eqType", eqType);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);
        map.put("isNullOrNot", isNullOrNot);
        map.put("eqOrNot", eqOrNot);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectMonthlyRptK04Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 月核定撥付總表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptsum> selectMonthlyRptS04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("benEvtRel", benEvtRel);
        map.put("eqType", eqType);
        map.put("chkDate", chkDate);
        map.put("isNullOrNot", isNullOrNot);
        map.put("eqOrNot", eqOrNot);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectMonthlyRptS04Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯核定清單
     * 
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptsum> selectMonthlyRpt11Report(String payCode, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectMonthlyRpt11Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯核定清單
     * 
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptsum> selectMonthlyRpt13Report(String payCode, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectMonthlyRpt13Report", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 應收款立帳核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptsum> selectMonthlyRpt20DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);
        map.put("isNull", isNull);
        map.put("isEqual", isEqual);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectMonthlyRpt20DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bapayrptsum</code>) 資料 for 退匯應收已收核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptsum> selectMonthlyRpt19DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);
        map.put("isNullOrNot", isNullOrNot);
        map.put("eqOrNot", eqOrNot);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectMonthlyRpt19DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bapayrptsum</code>) 資料 for 退匯/現應收已收核定清單
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRpt25DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rptTyp", rptTyp);
        map.put("apNo", apNo);
        map.put("chkDate", chkDate);
        map.put("apSeqNo", seqNo);
        map.put("paySeqNo", paySeqNo);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectMonthlyRpt25DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bapayrptsum</code>) 資料 for 退匯/現應收已收註銷核定清單
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptsum> selectMonthlyRpt27DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rptTyp", rptTyp);
        map.put("apNo", apNo);
        map.put("chkDate", chkDate);
        map.put("apSeqNo", seqNo);
        map.put("paySeqNo", paySeqNo);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectMonthlyRpt27DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Bapayrptsum</code>) 資料 for 保留遺屬年金計息存儲核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptsum> selectMonthlyRpt32DataBy(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);
        map.put("isNullOrNot", isNullOrNot);
        map.put("eqOrNot", eqOrNot);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectMonthlyRpt32DataBy", map);
    }

    /**
     * 依傳入條件取得轉催核定清單 資料
     * 
     * @return
     */
    public List<Bapayrptsum> selectOtherRpt06DataBy(String payCode, String procYm, String paySeqNo, String isNull, String isEqual, String nowDate) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", procYm);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        map.put("isNull", isNull);
        map.put("isEqual", isEqual);
        map.put("cPrnDate", nowDate);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectOtherRpt06DataBy", map);
    }

    /**
     * 依傳入條件取得轉催核定清單 資料
     * 
     * @return
     */
    public List<Bapayrptsum> selectOtherRpt08DataBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual, String nowDate) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        map.put("issuYm", deadYy);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        map.put("cPrnDate", nowDate);
        map.put("isNull", isNull);
        map.put("isEqual", isEqual);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectOtherRpt08DataBy", map);
    }

    public String execSumRpt16Type(String payCode, String procYm, String paySeqNo, String cprnDt) {
        String flag = null;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("v_i_paycode", payCode);
        map.put("v_i_procYm", procYm);
        map.put("v_i_payseqno", paySeqNo);
        map.put("v_i_cprnDt", cprnDt);
        map.put("v_o_flag", "");
        if (StringUtils.equals(payCode, "L")) {
            getSqlMapClientTemplate().queryForObject("BAPAYRPTSUM.execRpt16LType", map);
        }
        else if (StringUtils.equals(payCode, "K")) {
            getSqlMapClientTemplate().queryForObject("BAPAYRPTSUM.execRpt16KType", map);
        }
        else {
            getSqlMapClientTemplate().queryForObject("BAPAYRPTSUM.execRpt16SType", map);
        }
        flag = (String) map.get("v_o_flag");

        return flag;
    }

    /**
     * 依傳入的條件取得轉催收核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param demDate 轉催收日期
     * @return
     */
    public List<Bapayrptsum> selectOtherRpt06CompRptBy(String payCode, String demDate, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("chkDate", demDate);
        map.put("isNull", isNull);
        map.put("isEqual", isEqual);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectOtherRpt06CompRptBy", map);
    }

    /**
     * 依傳入的條件取得轉呆帳核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param demDate 轉催收日期
     * @return
     */
    public List<Bapayrptsum> selectOtherRpt08CompRptBy(String payCode, String bDebtDate, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("chkDate", bDebtDate);
        map.put("isNull", isNull);
        map.put("isEqual", isEqual);
        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectOtherRpt08CompRptBy", map);
    }

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for 整批收回核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Bapayrptsum bapayrptsum) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTSUM.insertDataForBatchPaymentReceiveData", bapayrptsum);
    }

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for 整批收回核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForPaymentReceiveData(Bapayrptsum bapayrptsum) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTSUM.insertDataForPaymentReceiveData", bapayrptsum);
    }

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for （失能）整批收回核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertKDataForBatchPaymentReceiveData(Bapayrptsum bapayrptsum) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTSUM.insertKDataForBatchPaymentReceiveData", bapayrptsum);
    }

    /**
     * 新增(<code>BAPAYRPTSUM</code>) 資料 for （遺屬）整批收回核定清單
     * 
     * @param bapayrptsum
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertSDataForBatchPaymentReceiveData(Bapayrptsum bapayrptsum) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTSUM.insertSDataForBatchPaymentReceiveData", bapayrptsum);
    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTSUM</code>) 資料 FOR 整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 給付別
     * @param CHKDATE
     * @return <code>Bapayrptsum</code> 物件
     */
    public List<Bapayrptsum> selectBapayrptsumDataForRptaccountBy(String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        return getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectBapayrptsumDataForRptaccountBy", map);
    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTSUM</code>) 資料 FOR 整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 給付別
     * @param CHKDATE
     * @return <code>Bapayrptsum</code> 物件
     */
    public List<Bapayrptsum> selectBapayrptsumDataToRptaccountForPaymentReceiveBy(String payCode, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }

        return getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectBapayrptsumDataToRptaccountForPaymentReceiveBy", map);
    }

    /**
     * 依傳入條件取得 核付報表總表紀錄檔(<code>BAPAYRPTSUM</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param rptTyp
     * @param payCode
     * @param chkDate 已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptsum> getBatchPaymentReceiveDataBy(String rptTyp, String payCode, String chkDate, String cPrnDate, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("rptTyp", rptTyp);
        map.put("payCode", payCode);
        map.put("chkDate", chkDate);
        map.put("cPrnDate", cPrnDate);
        map.put("isNullOrNot", isNullOrNot);
        map.put("eqOrNot", eqOrNot);

        return (List<Bapayrptsum>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.getBatchPaymentReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTSUM</code>) 資料 FOR （遺屬）整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 給付別
     * @param CHKDATE
     * @return <code>Bapayrptsum</code> 物件
     */
    public List<Bapayrptsum> selectSBapayrptsumDataForRptaccountBy(String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        return getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectSBapayrptsumDataForRptaccountBy", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTSUM</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptsumForPfmPfd(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode) {

        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(String.valueOf(v_cprnpage))) {
            map.put("v_cprnpage", String.valueOf(v_cprnpage));
        }

        if (StringUtils.isNotBlank(String.valueOf(v_lcnt))) {
            map.put("v_lcnt", String.valueOf(v_lcnt));
        }

        if (StringUtils.isNotBlank(issuTyp)) {
            map.put("issuTyp", issuTyp);
        }

        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        getSqlMapClientTemplate().update("BAPAYRPTSUM.updateForPfmPfd", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTSUM</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptsumForPfmPfdK(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode, String nlWkMk, String adWkMk) {

        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(String.valueOf(v_cprnpage))) {
            map.put("v_cprnpage", String.valueOf(v_cprnpage));
        }

        if (StringUtils.isNotBlank(String.valueOf(v_lcnt))) {
            map.put("v_lcnt", String.valueOf(v_lcnt));
        }

        if (StringUtils.isNotBlank(issuTyp)) {
            map.put("issuTyp", issuTyp);
        }

        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(nlWkMk)) {
            map.put("nlWkMk", nlWkMk);
        }

        if (StringUtils.isNotBlank(adWkMk)) {
            map.put("adWkMk", adWkMk);
        }

        getSqlMapClientTemplate().update("BAPAYRPTSUM.updateForPfmPfdK", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTSUM</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptsumForPfmPfdS(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode, String nlWkMk, String adWkMk) {

        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(String.valueOf(v_cprnpage))) {
            map.put("v_cprnpage", String.valueOf(v_cprnpage));
        }

        if (StringUtils.isNotBlank(String.valueOf(v_lcnt))) {
            map.put("v_lcnt", String.valueOf(v_lcnt));
        }

        if (StringUtils.isNotBlank(issuTyp)) {
            map.put("issuTyp", issuTyp);
        }

        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(nlWkMk)) {
            map.put("nlWkMk", nlWkMk);
        }

        if (StringUtils.isNotBlank(adWkMk)) {
            map.put("adWkMk", adWkMk);
        }

        getSqlMapClientTemplate().update("BAPAYRPTSUM.updateForPfmPfdS", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTSUM</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateTempMk(String payCode, String apNo) {

        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }

        getSqlMapClientTemplate().update("BAPAYRPTSUM.updateTempMk", map);
    }

}
