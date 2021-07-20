package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BirefDao;
import tw.gov.bli.ba.domain.Biref;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

@DaoTable("BIREF")
public class BirefDaoImpl extends SqlMapClientDaoSupport implements BirefDao {
    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 申請失業給付紀錄資料
     * 
     * @param idNo 身分證號
     * @param brth 出生日期
     * @return 內含 <code>Biref</code> 物件的 List
     */
    @DaoFieldList("IDNO,BRTH")
    public List<Biref> selectUnEmpDataBy(String idNo, String brth) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("idNo", idNo);
        map.put("brth", brth);
        return getSqlMapClientTemplate().queryForList("BIREF.selectUnEmpDataBy", map);
    }

    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 申請失業給付記錄 for 勞保老年年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Biref> getOldAgeReviewRpt01JoblessPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate)) {
            if (StringUtils.length(evtBrDate) == 8)
                map.put("evtBrDate", DateUtility.changeDateType(evtBrDate));
            else
                map.put("evtBrDate", evtBrDate);
        }

        return getSqlMapClientTemplate().queryForList("BIREF.getOldAgeReviewRpt01JoblessPayListBy", map);
    }

    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 申請失業給付記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Biref> getDisableReviewRpt01JoblessPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BIREF.getDisableReviewRpt01JoblessPayListBy", map);
    }

    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 申請職業訓練生活津貼記錄 for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Biref> getDisableReviewRpt01VocationalTrainingLivingAllowanceListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);

        return getSqlMapClientTemplate().queryForList("BIREF.getDisableReviewRpt01VocationalTrainingLivingAllowanceListBy", map);
    }

    /**
     * 依傳入條件取得 就保給付檔(<code>BIREF</code>) 申請失業給付記錄 for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分證號
     * @param evtBrDate 事故者出生日期
     * @return
     */
    @DaoFieldList("EVTIDNNO,EVTBRDATE")
    public List<Biref> getSurvivorReviewRpt01JoblessPayListBy(String evtIdnNo, String evtBrDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate)) {
            if (StringUtils.length(evtBrDate) == 8)
                map.put("evtBrDate", DateUtility.changeDateType(evtBrDate));
            else
                map.put("evtBrDate", evtBrDate);
        }

        return getSqlMapClientTemplate().queryForList("BIREF.getDisableReviewRpt01JoblessPayListBy", map);
    }
}
