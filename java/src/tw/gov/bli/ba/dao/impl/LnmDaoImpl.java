package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.LnmDao;
import tw.gov.bli.ba.domain.Lnm;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 貸款主檔 (<code>LNM</code>) <br>
 * 注意: 此檔的日期格式皆為民國日期
 * 
 * @author Goston
 */
@DaoTable("LNM")
public class LnmDaoImpl extends SqlMapClientDaoSupport implements LnmDao {

    /**
     * 依傳入條件取得 貸款主檔 (<code>LNM</code>) 資料 for 勞工紓困貸款繳納本息情形查詢單
     * 
     * @param idnAply 身份證號
     * @param brDteAply 出生日期 (傳入西元日期, DAO 實作會將其轉為民國日期)
     * @return
     */
    @DaoFieldList("IDNAPLY,BRDTEAPLY")
    public Lnm getDataUpdateRpt01DataBy(String idnAply, String brDteAply) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(idnAply))
            map.put("idnAply", idnAply);
        if (StringUtils.isNotBlank(brDteAply))
            map.put("brDteAply", brDteAply);

        return (Lnm) getSqlMapClientTemplate().queryForObject("LNM.selectDataUpdateRpt01DataBy", map);
    }

    /**
     * 依傳入條件取得 貸款主檔 (<code>LNM</code>) 資料 for 紓困貸款撥款情形查詢清單
     * 
     * @param idnAply 身份證號
     * @param brDteAply 出生日期 (傳入西元日期, DAO 實作會將其轉為民國日期)
     * @return
     */
    @DaoFieldList("IDNAPLY,BRDTEAPLY")
    public Lnm getDataUpdateRpt02DataBy(String idnAply, String brDteAply) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(idnAply))
            map.put("idnAply", idnAply);
        if (StringUtils.isNotBlank(brDteAply))
            map.put("brDteAply", brDteAply);

        return (Lnm) getSqlMapClientTemplate().queryForObject("LNM.selectDataUpdateRpt02DataBy", map);
    }

}
