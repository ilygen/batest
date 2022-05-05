package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BbdaprDao;
import tw.gov.bli.ba.domain.Bbdapr;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 給付核定檔 (<code>BBDAPR</code>)
 * 
 * @author swim
 */
@DaoTable("BBDAPR")
public class BbdaprDaoImpl extends SqlMapClientDaoSupport implements BbdaprDao {

    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public Bbdapr getDisableReviewRpt01AnnuityPayDataBy(String apNo, String issuYm, String payYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.getDisableReviewRpt01AnnuityPayDataBy", map);
    }

    @DaoFieldList("APNO,ISSUYM,PAYYM")
    public Bbdapr selectSurvivorReviewRpt01DateDataBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (Bbdapr) getSqlMapClientTemplate().queryForObject("BBDAPR.selectSurvivorReviewRpt01DateDataBy", map);
    }
}
