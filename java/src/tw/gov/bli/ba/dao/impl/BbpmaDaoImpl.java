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
 * @author Rickychi
 */
@DaoTable("BBPMA")
public class BbpmaDaoImpl extends SqlMapClientDaoSupport implements BbpmaDao {

    /**
     * 依傳入的條件取得 給付主檔(<code>BAAPPBASE</code>) 遺屬年金紀錄資料 for 勞保老年年金給付受理編審清單
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

}
