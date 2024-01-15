package tw.gov.bli.ba.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BacpirecDao;
import tw.gov.bli.ba.domain.Bacpirec;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;
import java.math.BigDecimal;
import java.util.*;

/**
 * Dao for 物價指數調整紀錄檔 (<code>BACPIREC</code>)
 *
 * @author Kiyomi
 */
@DaoTable("BACPIREC")
public class BacpirecDaoImpl extends SqlMapClientDaoSupport implements BacpirecDao {

    /**
     * 依傳入的條件取得 物價指數調整紀錄檔 (<code>BACPIREC</code>) 的資料
     *
     * @param adjYear 指數年度
     * @return <code>List<CpiRecMaintCase></code> 物件
     */
    @DaoFieldList("ISSUYEAR")
    public List<Bacpirec> selectData(String issuYear) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(issuYear))
            map.put("issuYear", issuYear);

        return (List<Bacpirec>) getSqlMapClientTemplate().queryForList("BACPIREC.selectDataBy", map);
    }

    /**
     * 依傳入的條件取得 物價指數調整紀錄檔 (<code>BACPIREC</code>) 的資料
     *
     * @param adjYear 指數年度
     * @return <code>List<CpiRecMaintCase></code> 物件
     */
    @DaoFieldList("ISSUYEAR")
    public List<Bacpirec> checkData(String issuYear) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(issuYear))
            map.put("issuYear", DateUtility.changeChineseYearType(issuYear));

        return (List<Bacpirec>) getSqlMapClientTemplate().queryForList("BACPIREC.checkDataBy", map);
    }

    /**
     * 新增資料到 物價指數調整明細檔 (<code>BACPIREC</code>)<br>
     *
     * @param data <code>Bacpirec</code> 物件
     */
    public BigDecimal insertData(Bacpirec data) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BACPIREC.insertData", data);
    }


    /**
     * 更新資料到 物價指數調整紀錄檔 (<code>BACPIREC</code>)<br>
     *
     * @param data <code>Bacpirec</code> 物件
     */
    public int updateData(Bacpirec data) {
        return getSqlMapClientTemplate().update("BACPIREC.updateData", data);
    }

    /**
     * 刪除 物價指數調整明細檔 (<code>BACPIREC</code>) 資料
     *
     * @param adjYear 指數年度
     * @return <code>BigDecimal</code>
     */
    public void deleteData(String issuYear) {
        getSqlMapClientTemplate().update("BACPIREC.deleteData", issuYear);
    }


    /**
     * 依傳入的條件取得 物價指數調整紀錄檔 (<code>BACPIREC</code>) 的資料<br>
     *
     * @param cpiYear      指數年度
     * @param cpIndex      物價指數成長率
     * @param reqRpno      報請核定文號
     * @param issuRpno     核定文號
     * @param issuDesc     核定結果
     * @param updUser      員工編號
     * @param updTime      系統日期時間
     *
     * @return <code>Bacpirec</code> 物件
     */
    @DaoFieldList("ISSUYEAR")
    public List<Bacpirec> selectSingleForUpdateData(String issuYear) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(issuYear))
            map.put("issuYear", issuYear);

        return (List<Bacpirec>) getSqlMapClientTemplate().queryForList("BACPIREC.selectSingleForUpdateData", map);
    }
    
    /**
     * 依傳入條件取得 物價指數調整紀錄檔 (<code>BACPIREC</code>) 物價指數調整記錄資料 for 給付查詢
     * 
     * @return
     */
    public List<Bacpirec> selectCpipRecForPaymentQuery() {
        return getSqlMapClientTemplate().queryForList("BACPIREC.selectCpipRecForPaymentQuery", null);
    }

	@Override
	public List<BigDecimal> selectCpiRateByAppDateAndEvtDieDate(String appDate, String evtDieDate,String payDate) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotBlank(appDate)) {
			map.put("appYm", StringUtils.substring(appDate, 0, 6));
			if (StringUtils.isNotBlank(payDate)) {			
				 if ( Integer.parseInt(StringUtils.substring(appDate, 0, 4))>Integer.parseInt(StringUtils.substring(payDate, 0, 4))  )  //取大的年
					 map.put("appYear", StringUtils.substring(appDate, 0, 4)); //申請年 > 首次核定年
				 else
					 map.put("appYear", StringUtils.substring(payDate, 0, 4));		
			}
			else 
			  map.put("appYear", StringUtils.substring(appDate, 0, 4));
		}
		if (StringUtils.isNotBlank(evtDieDate)) {
			map.put("evtDieYm", StringUtils.substring(evtDieDate, 0, 6));
		}

		return getSqlMapClientTemplate().queryForList("BACPIREC.selectCpiRateByAppDateAndEvtDieDate", map);
	}
}
