package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.Balp0d330Dao;
import tw.gov.bli.ba.domain.Balp0d330;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 月核定差異統計檔 (<code>BALP0D330</code>)
 * 
 * @author Goston
 */
@DaoTable("BALP0D330")
public class Balp0d330DaoImpl extends SqlMapClientDaoSupport implements Balp0d330Dao {

    /**
     * 依傳入條件取得 月核定差異統計檔 (<code>BALP0D330</code>) for 月核定差異統計表
     * 
     * @param payCode 給付別
     * @param issuYmBegin 核定年月 (起)
     * @param issuYmEnd 核定年月 (迄)
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM_BEGIN,ISSUYM_END")
    public List<Balp0d330> getMonthlyRpt03ListBy(String payCode, String issuYmBegin, String issuYmEnd) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(issuYmBegin))
            map.put("issuYmBegin", issuYmBegin);
        if (StringUtils.isNotBlank(issuYmEnd))
            map.put("issuYmEnd", issuYmEnd);

        return getSqlMapClientTemplate().queryForList("BALP0D330.selectMonthlyRpt03ListBy", map);
    }

}
