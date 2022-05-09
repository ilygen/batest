package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BebmsaDao;
import tw.gov.bli.ba.domain.Bebmsa;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 現金給付參考檔 (<code>BEBMSA</code>) <br>
 * 
 * @author Goston
 */
@DaoTable("BEBMSA")
public class BebmsaDaoImpl extends SqlMapClientDaoSupport implements BebmsaDao {

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請死亡給付記錄 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
	@Override
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getOldAgeReviewRpt01DiePayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getOldAgeReviewRpt01DiePayListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param bmEvidNo 身分證號
     * @param bmEvBrth 出生日期
     * @return
     */
	@Override
    @DaoFieldList("BMEVIDNO,BMEVBRTH,APNO")
    public List<Bebmsa> selectSurvivorReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BEBMSA.selectSurvivorReviewRpt01InjuryPayListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param bmEvidNo 身分證號
     * @param bmEvBrth 出生日期
     * @return
     */
    @Override
	@DaoFieldList("BMEVIDNO,BMEVBRTH,APNO")
    public List<Bebmsa> selectDisasterReviewRpt01InjuryCarePayListBy(String evtIdnNo, String evtBrDate, String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BEBMSA.selectDisasterReviewRpt01InjuryCarePayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請失蹤給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
	@Override
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getSurvivorReviewRpt01DisappearPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getSurvivorReviewRpt01DisappearPayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 一次給付資料 for 災保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
	 * @param paytyp 給付種類
     * @return
     */
    @Override
	@DaoFieldList("EVTIDNNO,EVTBRDATE,PAYTYP")
    public List<Bebmsa> getDisableReviewRpt01OncePayListBy(String evtIdnNo, String evtBrDate, String paytyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        if (StringUtils.isNotBlank(paytyp))
            map.put("paytyp", paytyp);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01OncePayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請死亡給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
	@Override
    @DaoFieldList("EVTIDNNO,EVTBRDATE,PAYTYP")
    public List<Bebmsa> getDisableReviewRpt01DiePayListBy(String evtIdnNo, String evtBrDate, String paytyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        if (StringUtils.isNotBlank(paytyp))
            map.put("paytyp", paytyp);

        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01DiePayListBy", map);
    }
    
    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
	@Override
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getDisableReviewRpt01InjuryPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
       
        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisableReviewRpt01InjuryPayListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>BEBMSA</code>) 申請傷病給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @Override
	@DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> getDisasterReviewRpt01InjuryCarePayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
       
        return getSqlMapClientTemplate().queryForList("BEBMSA.getDisasterReviewRpt01InjuryCarePayListBy", map);
    }

    /**
     * 依傳入條件取得 現金給付參考檔 (<code>PBBMSA</code>) 職災住院醫療給付資料 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @Override
	@DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Bebmsa> selectDisableReviewRpt01HosPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BEBMSA.selectDisableReviewRpt01HosPayListBy", map);
    }

}
