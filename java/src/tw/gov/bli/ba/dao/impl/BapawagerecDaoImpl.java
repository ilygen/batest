package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BapawagerecDao;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 勞保最高最低投保薪資紀錄檔 (<code>BAPAWAGEREC</code>)
 * 
 * @author KIYOMI
 */

public class BapawagerecDaoImpl extends SqlMapClientDaoSupport implements BapawagerecDao {

    /**
     * 依傳入條件取得 投保薪資分級表第一級 (<code>BAPAWAGEREC</code>)
     * 
     * @param maxPayYm 受理編號
     * @return
     */
    @DaoFieldList("MAXPAYYM")
    public BigDecimal getMinWage(String maxPayYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(maxPayYm))
            map.put("maxPayYm", maxPayYm);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAPAWAGEREC.getMinWage", map);
    }

}
