package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapayrptrecordDao;
import tw.gov.bli.ba.domain.Bapayrptrecord;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOImpl for 核付後報表清單紀錄檔 (<code>BAPAYRPTRECORD</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BAPAYRPTRECORD")
public class BapayrptrecordDaoImpl extends SqlMapClientDaoSupport implements BapayrptrecordDao {

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 給付抵銷紓困貸款明細（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt09DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        return getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt09DataBy", map);
    }

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 給付抵銷紓困貸款明細（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptK09DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);
        return getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptK09DataBy", map);
    }

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 給付抵銷紓困貸款明細（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptS09DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        return getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptS09DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt10RptType1DataBy(String payCode, String issuYm, String chkDate, String[] benEvtRel, String eqType, String[] orderBy) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("benEvtRel", benEvtRel);
        map.put("eqType", eqType);
        map.put("orderBy", orderBy);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt10RptType1DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptK10RptType1DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptK10RptType1DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptS10RptType1DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptS10RptType1DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單(郵政匯票) （老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt10RptType3DataBy(String payCode, String issuYm, String chkDate, String[] benEvtRel, String eqType, String[] orderBy) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("benEvtRel", benEvtRel);
        map.put("eqType", eqType);
        map.put("orderBy", orderBy);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt10RptType3DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單(郵政匯票) （失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptK10RptType3DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptK10RptType3DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單(郵政匯票) （遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptS10RptType3DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptS10RptType3DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt10RptType1PayAmtDataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt10RptType1PayAmtDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付明細表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt10RptType2DataBy(String payCode, String issuYm, String chkDate, String[] benEvtRel, String eqType, String[] orderBy) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("benEvtRel", benEvtRel);
        map.put("eqType", eqType);
        map.put("orderBy", orderBy);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt10RptType2DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付明細表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptK10RptType2DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptK10RptType2DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付明細表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptS10RptType2DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptS10RptType2DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt10RptType2PayAmtDataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt10RptType2PayAmtDataBy", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定撥付總表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> selectMonthlyRpt04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("benEvtRel", benEvtRel);
        map.put("eqType", eqType);
        map.put("chkDate", chkDate);
        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt04Report", map);
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
    public List<Bapayrptrecord> selectMonthlyRptK04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("benEvtRel", benEvtRel);
        map.put("eqType", eqType);
        map.put("chkDate", chkDate);
        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTSUM.selectMonthlyRptK04Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定撥付總表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param benEvtRel 關係
     * @param eqType SQL EqualType
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> selectMonthlyRptS04Report(String payCode, String issuYm, String[] benEvtRel, String eqType, String chkDate) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("benEvtRel", benEvtRel);
        map.put("eqType", eqType);
        map.put("chkDate", chkDate);
        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRptS04Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定撥付總表 代扣補償金資料
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param payTyp 給付方式
     * @param benEvtRel 關係
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM,PAYTYP")
    public List<Bapayrptrecord> selectMonthlyRpt04ReportCompenAmtDetail(String payCode, String issuYm, String payTyp, String benEvtRel) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("payTyp", payTyp);
        map.put("benEvtRel", benEvtRel);
        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt04ReportCompenAmtDetail", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯核定清單
     * 
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> selectMonthlyRpt11Report(String payCode, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt11Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 改匯核定清單
     * 
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> selectMonthlyRpt13Report(String payCode, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt13Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定合格清冊（老年）
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt08DataBy(String apNo, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt08Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定合格清冊（失能）
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptK08DataBy(String apNo, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        if (StringUtils.isNotBlank(isNullOrNot))
            map.put("isNullOrNot", isNullOrNot);
        if (StringUtils.isNotBlank(eqOrNot))
            map.put("eqOrNot", eqOrNot);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRptK08Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月核定合格清冊（遺屬）
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptS08DataBy(String apNo, String issuYm, String chkDate, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        if (StringUtils.isNotBlank(isNullOrNot))
            map.put("isNullOrNot", isNullOrNot);
        if (StringUtils.isNotBlank(eqOrNot))
            map.put("eqOrNot", eqOrNot);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRptS08Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯清冊
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt12DataBy(String apNo, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt12Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 改匯清冊
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt14DataBy(String apNo, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt14Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯應收已收清冊
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt15DataBy(String apNo, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        if (StringUtils.isNotBlank(isNullOrNot))
            map.put("isNullOrNot", isNullOrNot);
        if (StringUtils.isNotBlank(eqOrNot))
            map.put("eqOrNot", eqOrNot);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt15Report", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金清冊（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt17DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt17DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金清冊（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptK17DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptK17DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金清冊（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptS17DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptS17DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金轉暫收及待結轉清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt18DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt18DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金轉暫收及待結轉清單（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptK18DataBy(String payCode, String issuYm, String chkDate, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        map.put("paySeqNo", paySeqNo);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptK18DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退回現金轉暫收及待結轉清單（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptS18DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRptS18DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 退匯應收已收核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> selectMonthlyRpt19DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt19DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 應收款立帳核定清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> selectMonthlyRpt20DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt20DataBy", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 應收款立帳清冊
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt21DataBy(String apNo, String issuYm, String chkDate, String paySeqNo, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        if (StringUtils.isNotBlank(isNull))
            map.put("isNull", isNull);
        if (StringUtils.isNotBlank(isEqual))
            map.put("isEqual", isEqual);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt21Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月處理核定報表彙整
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt22DataBy(String apNo, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt22Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 月處理核定報表彙整
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Integer getMonthlyRpt22DataByPaytyp(String apNo, String issuYm, String chkDate, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        if (StringUtils.isNotBlank(eqType))
            map.put("eqType", eqType);

        return (Integer) getSqlMapClientTemplate().queryForObject("BAPAYRPTRECORD.selectMonthlyRpt22ReportPaytyp", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 郵政匯票通知／入戶匯款證明（老年）
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt23DataBy(String apNo, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt23Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 郵政匯票通知／入戶匯款證明（失能）
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptK23DataBy(String apNo, String issuYm, String chkDate, String paySeqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRptK23Report", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 郵政匯票通知／入戶匯款證明（遺屬）
     * 
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRptS23DataBy(String apNo, String issuYm, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRptS23Report", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付明細表
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt24DataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt24DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>Badapr</code>) 資料 for 核付清單
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Bapayrptrecord> getMonthlyRpt24PayAmtDataBy(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt24PayAmtDataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯/現應收已收清冊
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt26DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rptTyp", rptTyp);
        map.put("apNo", apNo);
        map.put("chkDate", chkDate);
        map.put("seqNo", seqNo);
        map.put("paySeqNo", paySeqNo);
        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt26DataBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BAPAYRPTRECORD</code>) 資料 for 退匯/現應收已收註銷清冊
     * 
     * @param rptTyp
     * @param apNo 受理編號
     * @param chkDate 已收日期/註銷已收日期
     * @param apSeqNo 受款人序
     * @return
     */
    public List<Bapayrptrecord> getMonthlyRpt28DataBy(String rptTyp, String apNo, String chkDate, String seqNo, String paySeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("rptTyp", rptTyp);
        map.put("apNo", apNo);
        map.put("chkDate", chkDate);
        map.put("seqNo", seqNo);
        map.put("paySeqNo", paySeqNo);
        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getMonthlyRpt28DataBy", map);
    }

    /**
     * 依傳入條件取得 核付後報表清單紀錄檔(<code>BAPAYRPTRECORD</code>) 資料 for 保留遺屬年金計息存儲清冊
     * 
     * @param apNo 受理編號
     * @param issuYm
     * @param chkDate
     * @param paySeqNo
     * @param isNullOrNot
     * @param eqOrNot
     * @return
     */
    @DaoFieldList("APNO,ISSUYM,CHKDATE,PAYSEQNO,ISNULLORNOT,EQORNOT")
    public List<Bapayrptrecord> getMonthlyRpt33DataBy(String apNo, String issuYm, String chkDate, String paySeqNo, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        if (StringUtils.isNotBlank(isNullOrNot))
            map.put("isNullOrNot", isNullOrNot);
        if (StringUtils.isNotBlank(eqOrNot))
            map.put("eqOrNot", eqOrNot);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectMonthlyRpt33Report", map);
    }

    /**
     * 依傳入的條件取得 轉催收核定清冊 的資料
     * 
     * @return
     */
    @DaoFieldList("")
    public List<Bapayrptrecord> getOtherRpt07DataBy(String payCode, String procYm, String paySeqNo, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(procYm))
            map.put("procYm", procYm);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        if (StringUtils.isNotBlank(isNull))
            map.put("isNull", isNull);
        if (StringUtils.isNotBlank(isEqual))
            map.put("isEqual", isEqual);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectOtherRpt07DataBy", map);
    }

    @DaoFieldList("")
    public void updateOtherRpt07DataBy(String payCode, String procYm, String paySeqNo, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(procYm))
            map.put("procYm", procYm);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        if (StringUtils.isNotBlank(isNull))
            map.put("isNull", isNull);
        if (StringUtils.isNotBlank(isEqual))
            map.put("isEqual", isEqual);

        getSqlMapClientTemplate().update("BAPAYRPTRECORD.updateOtherRpt07DataBy", map);
    }

    /**
     * 依傳入的條件取得 轉呆帳核定清冊 的資料-補印
     * 
     * @return
     */
    public List<Bapayrptrecord> getOtherRptComp07DataBy(String payCode, String demDate, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(isNull))
            map.put("isNull", isNull);
        if (StringUtils.isNotBlank(isEqual))
            map.put("isEqual", isEqual);
        if (StringUtils.isNotBlank(demDate))
            map.put("demDate", demDate);
        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectOtherRptComp07DataBy", map);
    }

    /**
     * 依傳入的條件取得 轉呆帳核定清冊 的資料
     * 
     * @return
     */
    @DaoFieldList("")
    public List<Bapayrptrecord> getOtherRpt09DataBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(deadYy))
            map.put("deadYy", deadYy);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        if (StringUtils.isNotBlank(isNull))
            map.put("isNull", isNull);
        if (StringUtils.isNotBlank(isEqual))
            map.put("isEqual", isEqual);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectOtherRpt09DataBy", map);
    }

    @DaoFieldList("")
    public void updateOtherRpt09DataBy(String payCode, String apNo, String deadYy, String paySeqNo, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(deadYy))
            map.put("deadYy", deadYy);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(paySeqNo))
            map.put("paySeqNo", paySeqNo);
        if (StringUtils.isNotBlank(isNull))
            map.put("isNull", isNull);
        if (StringUtils.isNotBlank(isEqual))
            map.put("isEqual", isEqual);

        getSqlMapClientTemplate().update("BAPAYRPTRECORD.updateOtherRpt09DataBy", map);
    }

    /**
     * 依傳入的條件取得 轉呆帳核定清冊 的資料-補印
     * 
     * @return
     */
    public List<Bapayrptrecord> getOtherRptComp09DataBy(String payCode, String bDebtDate, String isNull, String isEqual) {
        // 修改FORTIFY
        isEqual = StringUtility.changOperator(isEqual);
        isNull = StringUtility.changOperator(isNull);
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(isNull))
            map.put("isNull", isNull);
        if (StringUtils.isNotBlank(isEqual))
            map.put("isEqual", isEqual);
        if (StringUtils.isNotBlank(bDebtDate))
            map.put("bDebtDate", bDebtDate);
        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectOtherRptComp09DataBy", map);
    }

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for 整批收回核定清單
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Bapayrptrecord bapayrptrecord) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTRECORD.insertDataForBatchPaymentReceiveData", bapayrptrecord);
    }

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for 更正作業 - 應收收回處理作業 - 老年年金應收收回處理
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForPaymentReceiveData(Bapayrptrecord bapayrptrecord) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTRECORD.insertDataForPaymentReceiveData", bapayrptrecord);
    }

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for （失能）整批收回核定清單
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertKDataForBatchPaymentReceiveData(Bapayrptrecord bapayrptrecord) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTRECORD.insertKDataForBatchPaymentReceiveData", bapayrptrecord);
    }

    /**
     * 新增(<code>BAPAYRPTRECORD</code>) 資料 for （遺屬）整批收回核定清單
     * 
     * @param bapayrptrecord
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertSDataForBatchPaymentReceiveData(Bapayrptrecord bapayrptrecord) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAPAYRPTRECORD.insertSDataForBatchPaymentReceiveData", bapayrptrecord);
    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR 整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 給付別
     * @param chkDate
     * @return <code>bapayrptrecord</code> 物件
     */
    public List<Bapayrptrecord> selectBapayrptrecordDataForRptsumBy(String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        return getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectBapayrptrecordDataForRptsumBy", map);
    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR 整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 給付別
     * @param chkDate
     * @return <code>bapayrptrecord</code> 物件
     */
    public List<Bapayrptrecord> selectBapayrptrecordDataToRptsumForPaymentReceiveBy(String payCode, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }

        return getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectBapayrptrecordDataToRptsumForPaymentReceiveBy", map);
    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR （失能）整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 給付別
     * @param chkDate
     * @return <code>bapayrptrecord</code> 物件
     */
    public List<Bapayrptrecord> selectKBapayrptrecordDataForRptsumBy(String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        return getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectKBapayrptrecordDataForRptsumBy", map);
    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR （遺屬）整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 給付別
     * @param chkDate
     * @return <code>Bapayrptrecord</code> 物件
     */
    public List<Bapayrptrecord> selectSBapayrptrecordDataForRptsumBy(String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        return getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectSBapayrptrecordDataForRptsumBy", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BAPAYRPTRECORD</code>) 資料 for 整批收回應收已收清冊
     * 
     * @param rptTyp
     * @param payCode
     * @param chkDate 已收日期
     * @param cPrnDate
     * @return
     */
    public List<Bapayrptrecord> getBatchPaymentReceiveDataBy(String rptTyp, String payCode, String chkDate, String cPrnDate, String isNullOrNot, String eqOrNot) {
        // 修改FORTIFY
        eqOrNot = StringUtility.changOperator(eqOrNot);
        isNullOrNot = StringUtility.changOperator(isNullOrNot);
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("rptTyp", rptTyp);
        map.put("payCode", payCode);
        map.put("chkDate", chkDate);
        map.put("cPrnDate", cPrnDate);
        if (StringUtils.isNotBlank(isNullOrNot))
            map.put("isNullOrNot", isNullOrNot);
        if (StringUtils.isNotBlank(eqOrNot))
            map.put("eqOrNot", eqOrNot);

        return (List<Bapayrptrecord>) getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.getBatchPaymentReceiveDataBy", map);
    }

    /**
     * 依傳入條件取得 已收明細檔 (<code>BAPAYRPTRECORD</code>) 資料 FOR （失能）整批收回核定清單-應收已收核定清單
     * 
     * @param PAYCODE 給付別
     * @param CHKDATE
     * @return <code>Bapayrptrecord</code> 物件
     */
    public List<Bapayrptrecord> selectKBapayrptrecordDataForRptaccountBy(String payCode, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        return getSqlMapClientTemplate().queryForList("BAPAYRPTRECORD.selectKBapayrptrecordDataForRptaccountBy", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTRECORD</code>) 資料
     * 
     * @param bapayrptrecord 退現資料檔
     */
    public void updateBapayrptrecordForPfmPfd(int v_cprnpage, int v_lcnt, String issuTyp, String apNo, String seqNo, String issuYm, String payYm, String chkDate) {

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

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }

        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        if (StringUtils.isNotBlank(payYm)) {
            map.put("payYm", payYm);
        }

        getSqlMapClientTemplate().update("BAPAYRPTRECORD.updateForPfmPfd", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTRECORD</code>) 資料
     * 
     * @param bapayrptrecord 退現資料檔
     */
    public void updateBapayrptrecordForPfmPfdK(int v_cprnpage, int v_lcnt, String issuTyp, String apNo, String seqNo, String issuYm, String payYm, String chkDate, String nlWkMk, String adWkMk) {

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

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }

        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        if (StringUtils.isNotBlank(payYm)) {
            map.put("payYm", payYm);
        }

        if (StringUtils.isNotBlank(nlWkMk)) {
            map.put("nlWkMk", nlWkMk);
        }

        if (StringUtils.isNotBlank(adWkMk)) {
            map.put("adWkMk", adWkMk);
        }

        getSqlMapClientTemplate().update("BAPAYRPTRECORD.updateForPfmPfdK", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTRECORD</code>) 資料
     * 
     * @param bapayrptrecord 退現資料檔
     */
    public void updateBapayrptrecordForPfmPfdS(int v_cprnpage, int v_lcnt, String issuTyp, String apNo, String seqNo, String issuYm, String payYm, String chkDate, String nlWkMk, String adWkMk) {

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

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }

        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }

        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }

        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }

        if (StringUtils.isNotBlank(payYm)) {
            map.put("payYm", payYm);
        }

        if (StringUtils.isNotBlank(nlWkMk)) {
            map.put("nlWkMk", nlWkMk);
        }

        if (StringUtils.isNotBlank(adWkMk)) {
            map.put("adWkMk", adWkMk);
        }

        getSqlMapClientTemplate().update("BAPAYRPTRECORD.updateForPfmPfdS", map);
    }

    /**
     * 更新 退現資料檔(<code>BAPAYRPTRECORD</code>) 資料
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

        getSqlMapClientTemplate().update("BAPAYRPTRECORD.updateTempMk", map);
    }

}
