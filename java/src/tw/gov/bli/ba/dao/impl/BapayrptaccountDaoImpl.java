package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapayrptaccountDao;
import tw.gov.bli.ba.domain.Bapayrptaccount;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOImpl for 核付報表總表會計科目紀錄檔 (<code>BAPAYRPTACCOUNT</code>)
 * 
 * @author Kiyomi
 */
@DaoTable("BAPAYRPTACCOUNT")
public class BapayrptaccountDaoImpl extends SqlMapClientDaoSupport implements BapayrptaccountDao {

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 月核定撥付總表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptaccount> selectMonthlyRpt04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("benEvtRel", benEvtRel);
        map.put("eqType", eqType);
        map.put("chkDate", chkDate);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectMonthlyRpt04Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 月核定撥付總表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptaccount> selectMonthlyRptK04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
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
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectMonthlyRptK04Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 月核定撥付總表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptaccount> selectMonthlyRptS04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate, String isNullOrNot, String eqOrNot) {
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
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectMonthlyRptS04Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 退匯核定清單
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptaccount> selectMonthlyRpt11Report(String payCode, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectMonthlyRpt11Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 改匯核定清單
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptaccount> selectMonthlyRpt13Report(String payCode, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectMonthlyRpt13Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 退匯應收已收核定清單
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptaccount> selectMonthlyRpt19Report(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);
        map.put("isNullOrNot", isNullOrNot);
        map.put("eqOrNot", eqOrNot);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectMonthlyRpt19Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 應收款立帳核定清單
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptaccount> selectMonthlyRpt20Report(String payCode, String issuYm, String chkDate, String paySeqNo, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);
        map.put("isNull", isNull);
        map.put("isEqual", isEqual);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectMonthlyRpt20Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 退匯/現應收已收核定清單
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRpt25Report(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rptTyp", rptTyp);
        map.put("apNo", apNo);
        map.put("chkDate", chkDate);
        map.put("apSeqNo", seqNo);
        map.put("paySeqNo", paySeqNo);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectMonthlyRpt25Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 退匯/現應收已收註銷核定清單
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptaccount> selectMonthlyRpt27Report(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rptTyp", rptTyp);
        map.put("apNo", apNo);
        map.put("chkDate", chkDate);
        map.put("apSeqNo", seqNo);
        map.put("paySeqNo", paySeqNo);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectMonthlyRpt27Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 保留遺屬年金計息存儲核定清單
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptaccount> selectMonthlyRpt32Report(String payCode, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);
        map.put("isNullOrNot", isNullOrNot);
        map.put("eqOrNot", eqOrNot);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectMonthlyRpt32Report", map);
    }

    public List<Bapayrptaccount> selectOtherRpt06DataBy(String payCode, String procYm, String paySeqNo, String isNull, String isEqual, String cPrnDate) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", procYm);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        map.put("isNull", isNull);
        map.put("isEqual", isEqual);
        map.put("cPrnDate", cPrnDate);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectOtherRpt06DataBy", map);
    }

    public List<Bapayrptaccount> selectOtherRpt08DataBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual, String nowDate) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        map.put("issuYm", deadYy);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        map.put("cPrnDate", nowDate);
        map.put("isNull", isNull);
        map.put("isEqual", isEqual);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectOtherRpt08DataBy", map);
    }

    /**
     * 依傳入的條件取得轉呆帳核定清單 的資料- 補印
     * 
     * @param payCode 給付別
     * @param bDebtDate 轉催收日期
     * @return
     */
    public List<Bapayrptaccount> selectOtherRpt08CompRptBy(String payCode, String bDebtDate, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("chkDate", bDebtDate);
        map.put("isNull", isNull);
        map.put("isEqual", isEqual);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectOtherRpt08CompRptBy", map);
    }

    public List<Bapayrptaccount> selectOtherRpt09DataBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        map.put("issuYm", deadYy);
        map.put("paySeqNo", paySeqNo);
        map.put("isNull", isNull);
        map.put("isEqual", isEqual);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectOtherRpt09DataBy", map);
    }

    public String execAccountRpt16Type(String payCode, String procYm, String paySeqNo, String cprnDt) {
        String flag = null;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("v_i_paycode", payCode);
        map.put("v_i_procYm", procYm);
        map.put("v_i_payseqno", paySeqNo);
        map.put("v_i_cprnDt", cprnDt);
        map.put("v_o_flag", "");
        if (StringUtils.equals(payCode, "L")) {
            getSqlMapClientTemplate().queryForObject("BAPAYRPTACCOUNT.execRpt16LType", map);
        }
        else if (StringUtils.equals(payCode, "K")) {
            getSqlMapClientTemplate().queryForObject("BAPAYRPTACCOUNT.execRpt16KType", map);
        }
        else {
            getSqlMapClientTemplate().queryForObject("BAPAYRPTACCOUNT.execRpt16SType", map);
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
    public List<Bapayrptaccount> selectOtherRpt06CompRptBy(String payCode, String demDate, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("chkDate", demDate);
        map.put("isNull", isNull);
        map.put("isEqual", isEqual);
        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectOtherRpt06CompRptBy", map);
    }

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回核定清單
     * 
     * @param bapayrptaccount
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Bapayrptaccount bapayrptaccount) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTACCOUNT.insertDataForBatchPaymentReceiveData", bapayrptaccount);
    }

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回核定清單
     * 
     * @param bapayrptaccount
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForPaymentReceiveData(Bapayrptaccount bapayrptaccount) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTACCOUNT.insertDataForPaymentReceiveData", bapayrptaccount);
    }

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for （失能）整批收回核定清單
     * 
     * @param bapayrptaccount
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertKDataForBatchPaymentReceiveData(Bapayrptaccount bapayrptaccount) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTACCOUNT.insertKDataForBatchPaymentReceiveData", bapayrptaccount);
    }

    /**
     * 新增(<code>BAPAYRPTACCOUNT</code>) 資料 for （遺屬）整批收回核定清單
     * 
     * @param bapayrptaccount
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertSDataForBatchPaymentReceiveData(Bapayrptaccount bapayrptaccount) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTACCOUNT.insertSDataForBatchPaymentReceiveData", bapayrptaccount);
    }

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param rptTyp
     * @param payCode
     * @param chkDate 已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptaccount> getBatchPaymentReceiveDataBy(String rptTyp, String payCode, String chkDate, String cPrnDate, String isNullOrNot, String eqOrNot) {
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

        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.getBatchPaymentReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param payCode
     * @param chkDate 已收日期
     * @return
     */
    public List<Bapayrptaccount> getRpttypeForPfmPfdResult(String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("chkDate", chkDate);

        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectRpttypeForPfmPfdBy", map);
    }

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param payCode
     * @param chkDate 已收日期
     * @return
     */
    public List<Bapayrptaccount> getRpttypeForPaymentReceivePfmPfdResult(String payCode, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("apNo", apNo);

        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectRpttypeForPaymentReceivePfmPfdBy", map);
    }

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊 (K)
     * 
     * @param payCode
     * @param chkDate 已收日期
     * @return
     */
    public List<Bapayrptaccount> getRpttypeForPfmPfdKResult(String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("chkDate", chkDate);

        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectRpttypeForPfmPfdKBy", map);
    }

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊 (S)
     * 
     * @param payCode
     * @param chkDate 已收日期
     * @return
     */
    public List<Bapayrptaccount> getRpttypeForPfmPfdSResult(String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("chkDate", chkDate);

        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectRpttypeForPfmPfdSBy", map);
    }

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param payCode
     * @param chkDate 已收日期
     * @return
     */
    public List<Bapayrptaccount> getDataForPfmPfdResult(String payCode, String chkDate, String issuTyp, String payTyp, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("chkDate", chkDate);
        map.put("issuTyp", issuTyp);
        map.put("payTyp", payTyp);
        map.put("issuYm", issuYm);

        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectDataForPfmPfdBy", map);
    }

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊 (K)
     * 
     * @param payCode
     * @param chkDate 已收日期
     * @return
     */
    public List<Bapayrptaccount> getDataForPfmPfdKResult(String payCode, String chkDate, String issuTyp, String payTyp, String issuYm, String nlWkMk, String adWkMk) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("chkDate", chkDate);
        map.put("issuTyp", issuTyp);
        map.put("payTyp", payTyp);
        map.put("issuYm", issuYm);
        map.put("nlWkMk", nlWkMk);
        map.put("adWkMk", adWkMk);

        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectDataForPfmPfdKBy", map);
    }

    /**
     * 依傳入條件取得 核付報表總表會計科目紀錄檔(<code>BAPAYRPTACCOUNT</code>) 資料 for 整批收回應收已收清冊 (S)
     * 
     * @param payCode
     * @param chkDate 已收日期
     * @return
     */
    public List<Bapayrptaccount> getDataForPfmPfdSResult(String payCode, String chkDate, String issuTyp, String payTyp, String issuYm, String nlWkMk, String adWkMk) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("chkDate", chkDate);
        map.put("issuTyp", issuTyp);
        map.put("payTyp", payTyp);
        map.put("issuYm", issuYm);
        map.put("nlWkMk", nlWkMk);
        map.put("adWkMk", adWkMk);

        return (List<Bapayrptaccount>) getSqlMapClientTemplate().queryForList("BAPAYRPTACCOUNT.selectDataForPfmPfdSBy", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptaccountForPfmPfd(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode) {

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

        getSqlMapClientTemplate().update("BAPAYRPTACCOUNT.updateForPfmPfd", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptaccountForPfmPfdK(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode, String nlWkMk, String adWkMk) {

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

        getSqlMapClientTemplate().update("BAPAYRPTACCOUNT.updateForPfmPfdK", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBapayrptaccountForPfmPfdS(int v_cprnpage, int v_lcnt, String issuTyp, String issuYm, String chkDate, String payCode, String nlWkMk, String adWkMk) {

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

        getSqlMapClientTemplate().update("BAPAYRPTACCOUNT.updateForPfmPfdS", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateErptpageForPfmPfd(String v_i_cprndt, int v_cprnpage, int v_lcnt, String issuTyp, String chkDate, String payCode, String issuYm) {

        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(v_i_cprndt)) {
            map.put("v_i_cprndt", v_i_cprndt);
        }

        if (StringUtils.isNotBlank(String.valueOf(v_cprnpage))) {
            map.put("v_cprnpage", String.valueOf(v_cprnpage));
        }

        if (StringUtils.isNotBlank(String.valueOf(v_lcnt))) {
            map.put("v_lcnt", String.valueOf(v_lcnt));
        }

        if (StringUtils.isNotBlank(issuTyp)) {
            map.put("issuTyp", issuTyp);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        getSqlMapClientTemplate().update("BAPAYRPTACCOUNT.updateErptpageForPfmPfd", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
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

        getSqlMapClientTemplate().update("BAPAYRPTACCOUNT.updateTempMk", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateErptpageForPfmPfdK(String v_i_cprndt, int v_cprnpage, int v_lcnt, String issuTyp, String chkDate, String payCode, String issuYm, String nlWkMk, String adWkMk) {

        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(v_i_cprndt)) {
            map.put("v_i_cprndt", v_i_cprndt);
        }

        if (StringUtils.isNotBlank(String.valueOf(v_cprnpage))) {
            map.put("v_cprnpage", String.valueOf(v_cprnpage));
        }

        if (StringUtils.isNotBlank(String.valueOf(v_lcnt))) {
            map.put("v_lcnt", String.valueOf(v_lcnt));
        }

        if (StringUtils.isNotBlank(issuTyp)) {
            map.put("issuTyp", issuTyp);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        if (StringUtils.isNotBlank(nlWkMk)) {
            map.put("nlWkMk", nlWkMk);
        }

        if (StringUtils.isNotBlank(adWkMk)) {
            map.put("adWkMk", adWkMk);
        }

        getSqlMapClientTemplate().update("BAPAYRPTACCOUNT.updateErptpageForPfmPfdK", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTACCOUNT</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateErptpageForPfmPfdS(String v_i_cprndt, int v_cprnpage, int v_lcnt, String issuTyp, String chkDate, String payCode, String issuYm, String nlWkMk, String adWkMk) {

        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(v_i_cprndt)) {
            map.put("v_i_cprndt", v_i_cprndt);
        }

        if (StringUtils.isNotBlank(String.valueOf(v_cprnpage))) {
            map.put("v_cprnpage", String.valueOf(v_cprnpage));
        }

        if (StringUtils.isNotBlank(String.valueOf(v_lcnt))) {
            map.put("v_lcnt", String.valueOf(v_lcnt));
        }

        if (StringUtils.isNotBlank(issuTyp)) {
            map.put("issuTyp", issuTyp);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        if (StringUtils.isNotBlank(nlWkMk)) {
            map.put("nlWkMk", nlWkMk);
        }

        if (StringUtils.isNotBlank(adWkMk)) {
            map.put("adWkMk", adWkMk);
        }

        getSqlMapClientTemplate().update("BAPAYRPTACCOUNT.updateErptpageForPfmPfdS", map);
    }

}
