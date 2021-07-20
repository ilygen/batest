package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.CipgDao;
import tw.gov.bli.ba.domain.Cipg;
import tw.gov.bli.ba.domain.Cipt;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 被保險人投保薪資檔 (<code>CIPG</code>)
 * 
 * @author Evelyn Hsu
 */

public class CipgDaoImpl extends SqlMapClientDaoSupport implements CipgDao {

    /**
     * 依傳入的條件取得 被保險人投保薪資檔(<code>CIPG</code>) for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分証號
     * @return
     */
    @DaoFieldList("evtIdnNo")
    public List<Cipg> selectDisableReviewRpt01CipgData(String apNo, String seqNo, String evtIdnNo, String evTyp, String inTyp, String prType, String appMonth) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);

        if (StringUtils.isNotBlank(evTyp) && ("3".equals(evTyp) || "4".equals(evTyp)) && (prType == null || !"Y".equals(prType)))
            map.put("avgTyp", "1");
        else
            map.put("avgTyp", "6");

        if (StringUtils.isNotBlank(inTyp))
            map.put("inTyp", inTyp);

        if (StringUtils.isNotBlank(appMonth))
            map.put("appMonth", appMonth);

        return getSqlMapClientTemplate().queryForList("CIPG.selectDisableReviewRpt01AvgWg", map);

    }

    /**
     * 依傳入的條件取得 被保險人投保薪資檔(<code>CIPG</code>) for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param evtIdnNo 事故者身分証號
     * @param evTyp 傷病分類
     * @param inTyp
     * @param prType 先核普通
     * @param appMonth
     * @return
     */
    @DaoFieldList("evtIdnNo")
    public List<Cipg> selectSurvivorReviewRpt01CipgData(String apNo, String seqNo, String evtIdnNo, String evTyp, String inTyp, String prType, String appMonth) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);

        if (StringUtils.isNotBlank(evTyp) && ("3".equals(evTyp) || "4".equals(evTyp)) && (prType == null || StringUtils.isBlank(prType) || "Y".equals(prType)))
            map.put("avgTyp", "1");
        else
            map.put("avgTyp", "6");

        if (StringUtils.isNotBlank(inTyp))
            map.put("inTyp", inTyp);

        if (StringUtils.isNotBlank(appMonth))
            map.put("appMonth", appMonth);

        return getSqlMapClientTemplate().queryForList("CIPG.selectSurvivorReviewRpt01AvgWg", map);

    }

    /**
     * 依傳入條件取得 被保險人投保薪資檔(<code>CIPG</code>) 最高60個月平均薪資明細 for 給付查詢
     * 
     * @param idn 身分證號
     * @param avgTyp 投保薪資類別
     * @return
     */
    @DaoFieldList("APNO,SEQNO,IDN,ANGTYP,APPMONTH")
    public List<Cipg> selectPaymentQuerySixtyMonAvgAmtDataBy(String apNo, String seqNo, String idn, String avgTyp, String appMonth) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("idn", idn);
        map.put("avgTyp", avgTyp);
        map.put("appMonth", appMonth);
        return getSqlMapClientTemplate().queryForList("CIPG.selectPaymentQuerySixtyMonAvgAmtDataBy", map);
    }

    /**
     * 依傳入條件取得 被保險人投保薪資檔(<code>CIPG</code>) 最高60個月平均薪資明細 for 給付查詢 (老年年金)
     * 
     * @param idn 身分證號
     * @param avgTyp 投保薪資類別
     * @return
     */
    @DaoFieldList("APNO,SEQNO,IDN,APPMONTH")
    public List<Cipg> selectPaymentQuerySixtyMonAvgAmtDataForOldAgeBy(String apNo, String seqNo, String idn, String appMonth) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("idn", idn);
        map.put("appMonth", appMonth);
        return getSqlMapClientTemplate().queryForList("CIPG.selectPaymentQuerySixtyMonAvgAmtDataForOldAgeBy", map);
    }

    /**
     * 依傳入條件取得 被保險人投保薪資檔(<code>CIPG</code>) 最高60個月平均薪資明細 for 勞保老年年金給付受理編審清單
     * 
     * @param idn 身分證號
     * @return
     */
    @DaoFieldList("APNO,SEQNO,IDN,APPMONTH")
    public List<Cipg> selectOldAgeReviewRpt01SixtyMonthAvgAmtList(String apNo, String seqNo, String idn, String appMonth) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("idn", idn);
        map.put("appMonth", appMonth);
        return getSqlMapClientTemplate().queryForList("CIPG.selectOldAgeReviewRpt01SixtyMonthAvgAmtList", map);
    }

    /**
     * 依傳入條件取得 實際均薪月數(<code>CIPG</code>) For 老年年金
     * 
     * @param idn 身分證號
     * @return
     */
    @DaoFieldList("APNO,SEQNO,IDN")
    public String getRealAvgMonForOldAge(String apNo, String seqNo, String idn) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("idn", idn);

        return (String) getSqlMapClientTemplate().queryForObject("CIPG.getRealAvgMonForOldAge", map);
    }

    /**
     * 依傳入條件取得 實際均薪月數(<code>CIPG</code>) For 失能遺屬
     * 
     * @param idn 身分證號
     * @return
     */
    @DaoFieldList("APNO,SEQNO,IDN,ANGTYP")
    public String getRealAvgMonBy(String apNo, String seqNo, String idn, String avgTyp) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("idn", idn);
        map.put("avgTyp", avgTyp);

        return (String) getSqlMapClientTemplate().queryForObject("CIPG.getRealAvgMonBy", map);
    }

    /**
     * 依傳入條件取得 實際均薪月數(<code>CIPG</code>) For 失能年金Rpt01
     * 
     * @param idn 身分證號
     * @return
     */
    public String getRealAvgMonForDisableReviewRpt01By(String apNo, String seqNo, String idn, String inTyp, String avgTyp) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("idn", idn);
        map.put("inTyp", inTyp);
        map.put("avgTyp", avgTyp);

        return (String) getSqlMapClientTemplate().queryForObject("CIPG.getRealAvgMonForDisableReviewRpt01By", map);
    }

    /**
     * 依傳入條件取得 實際均薪月數(<code>CIPG</code>) For 遺屬年金Rpt01
     * 
     * @param idn 身分證號
     * @return
     */
    public String getRealAvgMonForSurvivorReviewRpt01By(String apNo, String seqNo, String idn, String inTyp, String avgTyp) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("idn", idn);
        map.put("inTyp", inTyp);
        map.put("avgTyp", avgTyp);

        return (String) getSqlMapClientTemplate().queryForObject("CIPG.getRealAvgMonForSurvivorReviewRpt01By", map);
    }

    /**
     * 依傳入條件取得 平均薪資(<code>CIPG</code>) for 失能、遺屬年金
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param idn 身分證號
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public BigDecimal getAvgWageForKS(String apNo, String seqNo, String idn) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(idn))
            map.put("idn", idn);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("CIPG.getAvgWageForKS", map);
    }

}
