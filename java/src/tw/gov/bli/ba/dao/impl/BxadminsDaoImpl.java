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
}
