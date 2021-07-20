package tw.gov.bli.common.dao;

import java.math.BigDecimal;
import tw.gov.bli.common.domain.PortalLog;

public interface PortalLogDao {
    /**
     * 紀錄 Portal Log
     * 
     * @param logData <code>PortalLog</code> 物件
     * @return <code>PORTAL_LOG.SYS_ID</code>
     */
    public BigDecimal insertData(PortalLog logData);
}
