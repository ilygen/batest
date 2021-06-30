package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BapaissudateDao;
import tw.gov.bli.ba.domain.Bapaissudate;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 月核定日期參數檔 (<code>BAPAISSUDATE</code>)
 * 
 * @author Goston
 */
@DaoTable("BAPAISSUDATE")
public class BapaissudateDaoImpl extends SqlMapClientDaoSupport implements BapaissudateDao {

    /**
     * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 勞工紓困貸款繳納本息情形查詢單
     * 
     * @param issuYm 核定年月
     * @return
     */
    @DaoFieldList("ISSUYM")
    public Bapaissudate getDataUpdateRpt01DataBy(String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);

        return (Bapaissudate) getSqlMapClientTemplate().queryForObject("BAPAISSUDATE.selectDataUpdateRpt01DataBy", map);
    }

    /**
     * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 線上月核定作業
     * 
     * @param payCode 給付別
     * @return
     */
    public String selectMaxIssuYmBy(String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        return (String) getSqlMapClientTemplate().queryForObject("BAPAISSUDATE.selectMaxIssuYmBy", map);
    }

    /**
     * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 線上月核定作業
     * 
     * @param payCode 給付別
     * @return
     */
    public BigDecimal selectCheckCountForBjMonthBy(String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAPAISSUDATE.selectCheckCountForBjMonthBy", map);
    }
    
    /**
	 * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 失能國併勞批次產製媒體作業
	 * 
	 * @param payCode 給付別
	 * @return
	 */
    public BigDecimal selectCheckCountForBjMonthBy2(String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAPAISSUDATE.selectCheckCountForBjMonthBy2", map);
    }

    public BigDecimal selectCheckChkdate(String payCode, String issuYm, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAPAISSUDATE.selectCheckChkdate", map);
    }

    /**
     * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 線上月核定作業
     * 
     * @param payCode 給付別
     * @return
     */
    public String selectIssuDateForOtherRpt10(String payCode, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        return (String) getSqlMapClientTemplate().queryForObject("BAPAISSUDATE.selectIssuDateForOtherRpt10", map);
    }

    /**
     * 依傳入條件取得 (<code>BAPAISSUDATE</code>) 資料 for 補正核付作業
     * 
     * @param payCode 給付別
     * @return
     */
    public String qryChkIssuym(String payCode) {
    	return (String) getSqlMapClientTemplate().queryForObject("BAPAISSUDATE.qryChkIssuym", payCode);
    }
    
}
