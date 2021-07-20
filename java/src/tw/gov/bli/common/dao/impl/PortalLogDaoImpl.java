package tw.gov.bli.common.dao.impl;

import java.math.BigDecimal;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.common.dao.PortalLogDao;
import tw.gov.bli.common.domain.PortalLog;
import tw.gov.bli.common.util.StrUtil;

public class PortalLogDaoImpl extends SqlMapClientDaoSupport implements PortalLogDao {

    /**
     * 紀錄 Portal Log
     * 
     * @param logData <code>PortalLog</code> 物件
     * @return <code>PORTAL_LOG.SYS_ID</code>
     */
    public BigDecimal insertData(PortalLog logData) {
        // 檢核各欄位長度
        // [
        if (StrUtil.stringRealLength(logData.getApUrl()) > 200) // 應用系統網址
            logData.setApUrl(StringUtils.defaultString(StrUtil.chtStrSubstring(logData.getApUrl(), 0, 200)));

        if (StrUtil.stringRealLength(logData.getLogDescript()) > 100) // 說明
            logData.setLogDescript(StringUtils.defaultString(StrUtil.chtStrSubstring(logData.getLogDescript(), 0, 100)));
        // ]

        return (BigDecimal) getSqlMapClientTemplate().insert("PORTAL_LOG.insertData", logData);
    }

}
