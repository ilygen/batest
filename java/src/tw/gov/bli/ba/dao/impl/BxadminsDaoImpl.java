package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.dao.BxadminsDao;
import tw.gov.bli.ba.domain.Bxadmins;
import tw.gov.bli.ba.executive.cases.ExecutiveSupportDataCase;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOImpl for 行政支援記錄檔 (<code>BXADMINS</code>)
 * 
 * @author swim
 */
@DaoTable("BXADMINS")
public class BxadminsDaoImpl extends SqlMapClientDaoSupport implements BxadminsDao {
    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 資料清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param idNo 身份證號
     * @return 內含 <code>BXADMINS</code> 物件的 List
     */
    @DaoFieldList("APNO, IDNO, ISSUYM")
    public List<Bxadmins> selectExecutiveSupportQueryListBy(String apNo, String idNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(idNo))
            map.put("idNo", idNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return getSqlMapClientTemplate().queryForList("BXADMINS.selectExecutiveSupportQueryListBy", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 資料清單且行政支援記錄未銷案
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @param proDate 承辦日期
     * @param seqNo 序號
     * @param letterType 處理函別代碼
     * @return 內含 <code>Bxadmins</code> 物件的 List
     */
    @DaoFieldList("APNO, ISSUYM, PRODATE, SEQNO, LETTERTYPE")
    public List<Bxadmins> selectExecutiveSupportCloseListBy(String apNo, String issuYm, String proDate, String seqNo, String letterType) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(proDate)) {
            map.put("proDate", proDate);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(letterType)) {
            map.put("letterType", letterType);
        }
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectExecutiveSupportCloseListBy", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 序號
     * 
     * @param apNo 受理編號
     * @param issuym 核定年月
     * @param prodate 承辦日期
     * @param lettertype 處理函別代碼
     * @return 內含 <code>序號</code> 的Integer
     */
    @DaoFieldList("APNO, ISSUYM, PRODATE, LETTERTYPE")
    public String selectSeqNoBy(String apNo, String issuYm, String proDate, String letterType) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(proDate))
            map.put("proDate", proDate);
        if (StringUtils.isNotBlank(letterType))
            map.put("letterType", letterType);

        return (String) getSqlMapClientTemplate().queryForObject("BXADMINS.selectSeqNoBy", map);
    }

    /**
     * 新增 行政支援記錄檔(<code>BXADMINS</code>) 資料
     * 
     * @param BXADMINS 行政支援記錄檔
     */
    public BigDecimal insertData(Bxadmins maadmrec) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BXADMINS.insertData", maadmrec);
    }

    /**
     * 更新 行政支援記錄檔(<code>BXADMINS</code>) 資料
     * 
     * @param HashMap
     */
    public void updateData(Bxadmins maadmrec) {
        getSqlMapClientTemplate().update("BXADMINS.updateData", maadmrec);
    }

    /**
     * 更新 行政支援記錄檔(<code>BXADMINS</code>) closeDate資料
     * 
     * @param HashMap
     */
    public void updateDataClosedate(Bxadmins maadmrec) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", maadmrec.getApNo());
        map.put("proDate", DateUtility.changeDateType(maadmrec.getProDate()));
        map.put("issuYm", DateUtility.changeChineseYearMonthType(maadmrec.getIssuYm()));
        map.put("seqNo", maadmrec.getSeqNo());
        map.put("closDate", maadmrec.getClosDate());
        map.put("updUser", maadmrec.getUpdUser());
        map.put("updTime", maadmrec.getUpdTime());
        map.put("letterType", maadmrec.getLetterType());
        getSqlMapClientTemplate().update("BXADMINS.updateDataClosedate", map);
    }

    /**
     * 刪除 行政支援記錄檔(<code>BXADMINS</code>) 資料
     * 
     * @param letterType 處理函別代碼
     * @param proDate 承辦日期
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public void deleteData(ExecutiveSupportDataCase caseData) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", caseData.getApNo());
        map.put("proDate", DateUtility.changeDateType(caseData.getProDate()));
        map.put("letterType", caseData.getLetterType());
        map.put("seqNo", caseData.getSeqNo());
        getSqlMapClientTemplate().delete("BXADMINS.deleteData", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 交查函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk1(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("strA", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_A);
        map.put("strB", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk1", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 不給付函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk2(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk2", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 補件通知函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk3(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("strA", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_C);
        map.put("strB", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        map.put("strC", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_D);
        map.put("strD", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk3", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 照會函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk4(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("strA", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_A);
        map.put("strB", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk4", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 其他簽函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk5(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk5", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk6(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk6", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 交查函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk1K(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("strA", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_A);
        map.put("strB", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk1K", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 不給付函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk2K(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk2K", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 補件通知函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk3K(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("strA", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_C);
        map.put("strB", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        map.put("strC", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_D);
        map.put("strD", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk3K", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 照會函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk4K(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("strA", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_A);
        map.put("strB", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk4K", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 其他簽函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk5K(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk5K", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk6K(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk6K", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 交查函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk1S(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("strA", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_A);
        map.put("strB", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk1S", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 不給付函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk2S(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk2S", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 補件通知函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk3S(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("strA", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_C);
        map.put("strB", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        map.put("strC", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_D);
        map.put("strD", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk3S", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 照會函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk4S(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("strA", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_A);
        map.put("strB", ConstantKey.BXADMINS_LETTER_TYPE_MK_SQL_B);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk4S", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 其他簽函資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk5S(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk5S", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 行政救濟日期,救濟種類,行政救濟辦理情形,救濟事由資料
     * 
     * @param apNo 受理編號
     * @return <code>Bxadmins</code> 物件
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectLetterTypeMk6S(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BXADMINS.selectLetterTypeMk6S", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 救濟種類資料
     * 
     * @param reliefKind 救濟種類
     * @return <code>Bxadmins</code> 物件
     */
    public String selectLetterTypeMk6Kind(String reliefKind) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("reliefKind", reliefKind);
        return (String) getSqlMapClientTemplate().queryForObject("BXADMINS.selectLetterTypeMk6Kind", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 行政救濟辦理情形資料
     * 
     * @param reliefKind 救濟種類
     * @param reliefStat 行政救濟辦理情形
     * @return <code>Bxadmins</code> 物件
     */
    public String selectLetterTypeMk6Stat(String reliefKind, String reliefStat) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("reliefKind", reliefKind);
        map.put("reliefStat", reliefStat);
        return (String) getSqlMapClientTemplate().queryForObject("BXADMINS.selectLetterTypeMk6Stat", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 年金給付資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Bxadmins getOldAgeReviewRpt01AnnuityPayDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Bxadmins) getSqlMapClientTemplate().queryForObject("BXADMINS.getOldAgeReviewRpt01AnnuityPayDataBy", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) Q1 for 給付審核(判斷「人工審核結果」radio button用)
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return <code>Integer</code> 物件
     */
    @DaoFieldList("APNO,ISSUYM")
    public Integer selectQ1ForReview(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (Integer) getSqlMapClientTemplate().queryForObject("BXADMINS.selectQ1ForReview", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) Q2 for 給付審核(判斷「人工審核結果」radio button用)
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return <code>Integer</code> 物件
     */
    @DaoFieldList("APNO,ISSUYM")
    public Integer selectQ2ForReview(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("issuYm", issuYm);
        return (Integer) getSqlMapClientTemplate().queryForObject("BXADMINS.selectQ2ForReview", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Bxadmins selectSurvivorReviewRpt01AnnuityPayDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Bxadmins) getSqlMapClientTemplate().queryForObject("BXADMINS.selectSurvivorReviewRpt01AnnuityPayDataBy", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("APNO,ISSUYM")
    public Bxadmins getDisableReviewRpt01AnnuityPayDataBy(String apNo, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Bxadmins) getSqlMapClientTemplate().queryForObject("BXADMINS.getDisableReviewRpt01AnnuityPayDataBy", map);
    }

    /**
     * 依傳入條件取得 行政支援記錄檔(<code>BXADMINS</code>) 資料 for 審核清單明細資料
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Bxadmins> selectBxadminsDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BXADMINS.selectBxadminsDataBy", map);
    }

    /**
     * 新增 行政支援記錄檔 (<code>BXADMINS</code>) 資料
     * 
     * @param BXADMINS 行政支援記錄檔
     * @return
     */
    public void insertDataForMonthlyRpt31(final List<Bxadmins> maadmrecList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (Bxadmins maadmrec : maadmrecList) {

                    executor.insert("BXADMINS.insertDataForMonthlyRpt31", maadmrec);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 依傳入條件取得 行政支援記錄檔 (<code>BXADMINS</code>) BXADMINSID
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ISSUYM 核 定年月
     * @param LETTERTYPE 函別
     * @param PRODATE 承辦日期
     * @return BigDecimal
     */
    @DaoFieldList("APNO,SEQNO,ISSUYM,LETTERTYPE,PRODATE")
    public BigDecimal selectMaAdmRecIdForMonthlyRpt31(String apNo, String seqNo, String issuYm, String LetterType, String proDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        if (StringUtils.isNotBlank(LetterType))
            map.put("letterType", LetterType);

        if (StringUtils.isNotBlank(proDate))
            map.put("proDate", proDate);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BXADMINS.selectMaAdmRecIdForMonthlyRpt31", map);
    }

    /**
     * 更新 行政支援記錄檔(<code>BXADMINS</code>) closeDate 資料
     * 
     * @param
     */
    public void updateCloseDateForMonthlyRpt31(Bxadmins maadmrec) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", maadmrec.getApNo());
        map.put("proDate", DateUtility.changeDateType(maadmrec.getProDate()));
        map.put("issuYm", DateUtility.changeChineseYearMonthType(maadmrec.getIssuYm()));
        map.put("seqNo", maadmrec.getSeqNo());
        map.put("closDate", maadmrec.getClosDate());
        map.put("updUser", maadmrec.getUpdUser());
        map.put("updTime", maadmrec.getUpdTime());
        map.put("letterType", maadmrec.getLetterType());
        
        getSqlMapClientTemplate().update("BXADMINS.updateCloseDateForMonthlyRpt31", map);
    }

    /**
     * 更新 行政支援記錄檔(<code>BXADMINS</code>) FOR 結案狀態變更作業
     * 
     * @param HashMap
     */
    public void updateDataForCloseStatusAlteration(String apNo, String updUser) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("updUser", updUser);
        getSqlMapClientTemplate().update("BXADMINS.updateDataForCloseStatusAlteration", map);
    }
}
