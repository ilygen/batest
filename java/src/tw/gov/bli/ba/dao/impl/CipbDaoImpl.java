package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.CipbDao;
import tw.gov.bli.ba.domain.Cipb;
import tw.gov.bli.ba.domain.Cipg;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 被保險人基本資料檔 (<code>CIPB</code>)
 * 
 * @author KIYOMI
 */

public class CipbDaoImpl extends SqlMapClientDaoSupport implements CipbDao {

    /**
     * Call StoreProcedure
     * 
     * @param famIdnNo 眷屬身分證號
     * @param evtJobDate 事故日期
     * @return
     */
    @DaoFieldList("APNO,SEQNO,FAMIDNNO,EVTJOBDATE")
    public void doGetCipb(String apNo, String seqNo, String appDate, String famIdnNo, String evtJobDate) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("v_i_apno", apNo);
        map.put("v_i_seqno", seqNo);
        map.put("v_i_appDate", appDate);
        map.put("v_i_famidnno", famIdnNo);
        map.put("v_i_evtjobdate", evtJobDate);

        getSqlMapClientTemplate().queryForObject("CIPB.doCipb", map);

    }

    /**
     * 依傳入條件取得 一次平均薪資 (<code>CIPB</code>)
     * 
     * @param apNo 受理編號
     * @param seqNo
     * @param idnNo
     * @return
     */
    @DaoFieldList("APNO,SEQNO,IDNNO")
    public BigDecimal getAvgWg(String apNo, String seqNo, String idnNo, String type) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        if (StringUtils.isNotBlank(idnNo))
            map.put("idnNo", idnNo);

        if (StringUtils.isNotBlank(type))
            map.put("type", type);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("CIPB.getAvgWg", map);
    }

    /**
     * 依傳入條件取得 被保險人基本資料檔(<code>CIPB</code>) 一次平均薪資 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo
     * @param idn 身分證號
     * @return
     */
    @DaoFieldList("APNO,SEQNO,IDN")
    public List<Cipb> selectOldAgeReviewRpt01OnceAvgAmt(String apNo, String seqNo, String idn) {
        
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("idn", idn);
        
        return getSqlMapClientTemplate().queryForList("CIPB.selectOldAgeReviewRpt01OnceAvgAmt", map);
    }

    /**
     * 依傳入條件取得 被保險人基本資料檔(<code>CIPB</code>) for 年金統計查詢
     * 
     * @return
     */
    public List<Cipb> qryCipbFmkList() {
    	return getSqlMapClientTemplate().queryForList("CIPB.qryCipbFmkList");
    }
}
