package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.Balp0d340Dao;
import tw.gov.bli.ba.domain.Balp0d340;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 月核定撥付檔 (<code>BALP0D340</code>)
 * 
 * @author swim
 */
@DaoTable("BALP0D340")
public class Balp0d340DaoImpl extends SqlMapClientDaoSupport implements Balp0d340Dao{
    /**
     * 依傳入條件取得 月核定撥付檔 (<code>BALP0D340</code>) for 月核定撥付總表
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM")
    public List<Balp0d340> selectMonthlyRpt04DataBy(String payCode,String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        
        return (List<Balp0d340>) getSqlMapClientTemplate().queryForList("BALP0D340.selectData", map);
    }
}
