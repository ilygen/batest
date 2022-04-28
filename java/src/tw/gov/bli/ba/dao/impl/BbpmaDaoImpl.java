package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BbpmaDao;
import tw.gov.bli.ba.domain.Bbpma;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 給付主檔 (<code>BBPMA</code>)
 * 
 * @author 
 */
@DaoTable("BBPMA")
public class BbpmaDaoImpl extends SqlMapClientDaoSupport implements BbpmaDao {

    /**
     * 依傳入的條件取得 給付主檔(<code>BBPMA</code>) 遺屬年金紀錄資料 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @return
     */
    @DaoFieldList("EVTIDNNO")
    public List<Bbpma> getOldAgeReviewRpt01SurvivorAnnuityPayListBy(String evtIdnNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);

        return getSqlMapClientTemplate().queryForList("BBPMA.getOldAgeReviewRpt01SurvivorAnnuityPayDataBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BBPMA</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @Override
	@DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Bbpma> selectSurvivorReviewRpt01DisablePayListBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        
        return getSqlMapClientTemplate().queryForList("BBPMA.selectSurvivorReviewRpt01DisablePayListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BBPMA</code>) 年金給付資料 for 災保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @Override
	@DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Bbpma> getDisasterReviewRpt01AnnuityPayListBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BBPMA.getDisasterReviewRpt01AnnuityPayListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BBPMA</code>) 遺屬年金年金給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @Override
	@DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Bbpma> getDisasterReviewRpt01SurvivorPayListBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BBPMA.getDisasterReviewRpt01SurvivorPayListBy", map);
    }

    /**
     * 依傳入的條件取得 給付主檔(<code>BBPMA</code>) 年金給付資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @Override
	@DaoFieldList("APNO,EVTIDNNO,EVTBRDATE")
    public List<Bbpma> selectDisasterReviewRpt01AnnuityPayListBy(String apNo, String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        return getSqlMapClientTemplate().queryForList("BBPMA.selectDisasterReviewRpt01AnnuityPayListBy", map);
    }


}
