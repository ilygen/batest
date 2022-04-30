/**
 * 
 */
package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BbgovnpesDao;
import tw.gov.bli.ba.domain.Bbgovnpes;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * @author kn0561
 *
 */
@DaoTable("BBGOVNPES")
public class BbgovnpesDaoImpl extends SqlMapClientDaoSupport implements BbgovnpesDao {

	/**
	 * 
	 */
	public BbgovnpesDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	@DaoFieldList("EVTIDNNO,EVTBRDATE,PAYKINDG")
    public List<Bbgovnpes> getCivilServantReviewRpt01PayListBy(String evtIdnNo, String evtBrDate, String payKindG) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(evtIdnNo))
            map.put("evtIdnNo", evtIdnNo);
        if (StringUtils.isNotBlank(evtBrDate))
            map.put("evtBrDate", evtBrDate);
        if (StringUtils.isNotBlank(payKindG))
            map.put("payKindG", payKindG);

        return getSqlMapClientTemplate().queryForList("BBGOVNPES.getCivilServantReviewRpt01PayListBy", map);
        
    }

}
