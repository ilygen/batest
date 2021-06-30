package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.LnmdDao;
import tw.gov.bli.ba.domain.Lnmd;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 紓困貸款呆帳檔 (<code>LNMD</code>) <br>
 * 注意: 此檔的日期格式皆為民國日期
 * 
 * @author swim
 */
@DaoTable("LNMD")
public class LnmdDaoImpl extends SqlMapClientDaoSupport implements LnmdDao{
    /**
     * 依傳入條件取得 紓困貸款呆帳檔 (<code>LNMD</code>) 資料 for 紓困貸款呆帳債務人照會單
     * 
     * @param idnBb 受款人身分證號
     * @param brdteBb 借款人出生日期 (傳入西元日期, DAO 實作會將其轉為民國日期)
     * @return
     */
    @DaoFieldList("IDN_BB,BRDTE_BB")
    public Lnmd getDataUpdateRpt04DataBy(String idn_Bb, String brdte_Bb) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(idn_Bb))
            map.put("idn_Bb", idn_Bb);
        if (StringUtils.isNotBlank(brdte_Bb)) 
            map.put("brdte_Bb", brdte_Bb);

        return (Lnmd) getSqlMapClientTemplate().queryForObject("LNMD.selectDataUpdateRpt04DataBy", map);
    }
}
