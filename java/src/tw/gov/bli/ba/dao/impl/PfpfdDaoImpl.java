package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.PfpfdDao;
import tw.gov.bli.ba.domain.Pfpfd;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 戶政全戶檔 (<code>PFPFD</code>)
 * 
 * @author KIYOMI
 */

@DaoTable("PFPFD")
public class PfpfdDaoImpl extends SqlMapClientDaoSupport implements PfpfdDao {

    /**
     * 新增(<code>PFPFD</code>) 資料 for 整批收回核定清單
     * 
     * @param pfpfd
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Pfpfd pfpfd) {
        return (BigDecimal) getSqlMapClientTemplate().insert("PFPFD.insertDataForBatchPaymentReceiveData", pfpfd);
    }
}
