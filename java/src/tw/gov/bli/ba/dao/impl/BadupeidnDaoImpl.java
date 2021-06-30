package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BadupeidnDao;
import tw.gov.bli.ba.domain.Badupeidn;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>)
 * 
 * @author Rickychi
 */
public class BadupeidnDaoImpl extends SqlMapClientDaoSupport implements BadupeidnDao {
    /**
     * 依傳入條件取得 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param idnNo 身分證號
     * @param brDate 姓名
     * @return
     */
    @DaoFieldList("APNO,SEQNO,IDNNO,BRDATE")
    public List<Badupeidn> selectDataBy(String apNo, String seqNo, String idnNo, String brDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(idnNo))
            map.put("idnNo", idnNo);
        if (StringUtils.isNotBlank(brDate))
            map.put("brDate", brDate);
        return getSqlMapClientTemplate().queryForList("BADUPEIDN.selectDataBy", map);
    }

    /**
     * 更新資料到 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>)
     * 
     * @param badupeidn <code>Badupeidn</code> 物件
     */
    public int updateData(Badupeidn badupeidn) {
        return getSqlMapClientTemplate().update("BADUPEIDN.updateData", badupeidn);
    }

    /**
     * 取得 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Badupeidn getOldAgeReviewRpt01DupeIdnDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Badupeidn) getSqlMapClientTemplate().queryForObject("BADUPEIDN.getOldAgeReviewRpt01DupeIdnDataBy", map);
    }

    /**
     * 取得 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Badupeidn selectSurvivorReviewRpt01DupeIdnDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        return (Badupeidn) getSqlMapClientTemplate().queryForObject("BADUPEIDN.selectSurvivorReviewRpt01DupeIdnDataBy", map);
    }

    /**
     * 依傳入條件取得 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 資料 for 失能年金案件資料更正 (身分證重號資料)
     * 
     * @param apNo 受理編號
     * @param idnNo 身分證號
     * @return
     */
    @DaoFieldList("APNO,IDNNO")
    public List<Badupeidn> getDisabledApplicationDataUpdateDupeIdnListBy(String apNo, String idnNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(idnNo))
            map.put("idnNo", idnNo);

        return getSqlMapClientTemplate().queryForList("BADUPEIDN.getDisabledApplicationDataUpdateDupeIdnListBy", map);
    }

    /**
     * 重置 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 失能年金案件資料更正 (身分證重號資料)
     * 
     * @param data
     */
    public void updateDisabledApplicationDataUpdateResetDupeIdnData(Badupeidn data) {
        if (data != null)
            getSqlMapClientTemplate().update("BADUPEIDN.updateDisabledApplicationDataUpdateResetDupeIdnData", data);
    }

    /**
     * 更新 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 失能年金案件資料更正 (身分證重號資料)
     * 
     * @param data
     */
    public void updateDisabledApplicationDataUpdateDupeIdnData(Badupeidn data) {
        if (data != null)
            getSqlMapClientTemplate().update("BADUPEIDN.updateDisabledApplicationDataUpdateDupeIdnData", data);
    }

    /**
     * 依傳入條件取得 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 資料 for 遺屬年金案件資料更正 (身分證重號資料)
     * 
     * @param apNo 受理編號
     * @param idnNo 身分證號
     * @return
     */
    @DaoFieldList("APNO,IDNNO")
    public List<Badupeidn> getSurvivorApplicationDataUpdateDupeIdnListBy(String apNo, String idnNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(idnNo))
            map.put("idnNo", idnNo);

        return getSqlMapClientTemplate().queryForList("BADUPEIDN.getSurvivorApplicationDataUpdateDupeIdnListBy", map);
    }
    
    /**
     * 重置 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 遺屬年金案件資料更正 (身分證重號資料)
     * 
     * @param data
     */
    public void updateSurvivorApplicationDataUpdateResetDupeIdnData(Badupeidn data) {
        if (data != null)
            getSqlMapClientTemplate().update("BADUPEIDN.updateSurvivorApplicationDataUpdateResetDupeIdnData", data);
    }

    /**
     * 更新 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 遺屬年金案件資料更正 (身分證重號資料)
     * 
     * @param data
     */
    public void updateSurvivorApplicationDataUpdateDupeIdnData(Badupeidn data) {
        if (data != null)
            getSqlMapClientTemplate().update("BADUPEIDN.updateSurvivorApplicationDataUpdateDupeIdnData", data);
    }
    
    /**
     * 依傳入條件取得 被保險人承保身分證號重號檔(<code>BADUPEIDN</code>) 資料 for 老年差額金通知
     * 
     * @param apNo 受理編號
     * @param seqNo
     * @param idnNo
     * @return
     */
    @DaoFieldList("APNO,SEQNO,IDNNO")
    public String selectRptReplaceForNewIdnNoL018(String apNo, String seqNo, String idnNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("idnNo", idnNo);
        return (String) getSqlMapClientTemplate().queryForObject("BADUPEIDN.getNewIdnNoL018", map);
    }
}
