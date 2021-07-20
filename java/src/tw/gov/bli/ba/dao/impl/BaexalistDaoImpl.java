package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BaexalistDao;
import tw.gov.bli.ba.domain.Baexalist;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * DAO for 審核決行清單紀錄檔 (<code>BAEXALIST</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BAEXALIST")
public class BaexalistDaoImpl extends SqlMapClientDaoSupport implements BaexalistDao {
    /**
     * 依傳入條件取得 審核決行清單紀錄檔(<code>BAEXALIST</code>) 資料清單
     * 
     * @param rptDate 列印日期
     * @param pageNo 頁次
     * @param payTyp 給付別
     * @return 內含 <code>Baexalist</code> 物件的 List
     */
    @DaoFieldList("RPTDATE,PAGENO,PAYTYP")
    public List<Baexalist> selectReviewDataBy(String rptDate, BigDecimal pageNo, String payTyp) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rptDate", rptDate);
        map.put("pageNo", pageNo);
        map.put("payTyp", payTyp);
        return getSqlMapClientTemplate().queryForList("BAEXALIST.selectReviewDataBy", map);
    }

    // /**
    // * 依入條件取得 審核決行清單紀錄檔(<code>BAAPPBASE</code>) 給付決行資料
    // *
    // * @param rptDate 列印日期
    // * @param pageNo 頁次
    // * @param chkMan 審核人員
    // * @param empId 員工編號
    // * @return 內含 <code>Baexalist</code> 物件的 List
    // */
    // @DaoFieldList("RPTDATE,PAGENO,CHKMAN,EMPID")
    // public List<Baexalist> selectDecisionDataBy(String rptDate, BigDecimal pageNo, String chkMan, String empId) {
    // HashMap<String, Object> map = new HashMap<String, Object>();
    // map.put("rptDate", rptDate);
    // map.put("pageNo", pageNo);
    // map.put("empId", empId);
    // if (StringUtils.isNotBlank(chkMan)) {
    // map.put("chkMan", chkMan);
    // }
    // return getSqlMapClientTemplate().queryForList("BAEXALIST.selectDecisionDataBy", map);
    // }

    /**
     * 紀錄 Baexalist (多筆)
     * 
     * @param BaexalistList 內含 <code>Baexalist</code> 物件的 List
     */
    public void insertData(final List<Baexalist> BaexalistList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();

                for (Baexalist baexalistData : BaexalistList) {

                    executor.insert("BAEXALIST.insertData", baexalistData);
                }

                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 依傳入的條件計算 (<code>BigDecimal</code>)
     * 
     * @param rptTyp
     * @param rptDate
     * @return
     */
    @DaoFieldList("RPTTYP,RPTDATE")
    public String selectMaxVersion(String rptTyp, String rptDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(rptTyp))
            map.put("rptTyp", rptTyp);
        if (StringUtils.isNotBlank(rptDate))
            map.put("rptDate", rptDate);

        return (String) getSqlMapClientTemplate().queryForObject("BAEXALIST.selectMaxVersion", map);
    }

    /**
     * 依入條件取得 審核決行清單紀錄檔(<code>BAEXALIST</code>) 給付決行資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baexalist</code> 物件的 List
     */
    @DaoFieldList("PAYTYP,ISSUYM,MTESTMK")
    public List<Baexalist> selectMonthlyRpt02DataBy(String payTyp, String issuYm, String mtestMk) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payTyp))
            map.put("payTyp", payTyp);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(mtestMk))
            map.put("mtestMk", mtestMk);

        return getSqlMapClientTemplate().queryForList("BAEXALIST.selectMonthlyRpt02DataBy", map);
    }
    
    /**
     * 依入條件取得 審核決行清單紀錄檔(<code>BAEXALIST</code>) 月處理異動清單
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param mtestMk 月核定別
     * @param chkDate 核定日期
     * @return 內含 <code>Baexalist</code> 物件的 List
     */
    @DaoFieldList("PAYTYP,ISSUYM,MTESTMK,CHKDATE")
    public List<Baexalist> selectMonthlyBatchRptDataBy(String payTyp, String issuYm, String mtestMk, String rptDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(payTyp))
            map.put("payTyp", payTyp);
        if (StringUtils.isNotBlank(issuYm))
            map.put("issuYm", issuYm);
        if (StringUtils.isNotBlank(mtestMk))
            map.put("mtestMk", mtestMk);
        if (StringUtils.isNotBlank(rptDate))
            map.put("rptDate", rptDate);

        return getSqlMapClientTemplate().queryForList("BAEXALIST.selectMonthlyBatchRptDataBy", map);
    }

    /**
     * 依入條件取得 審核決行清單紀錄檔(<code>BAEXALIST</code>) for 審核給付清單
     * 
     * @param chkMan 審核人員
     * @param chkDate 審核日期
     * @return 內含 <code>Baexalist</code> 物件的 List
     */
    @DaoFieldList("CHKMAN,CHKDATE,PAYTYP")
    public List<Baexalist> selectOldAgeReviewRpt02DataBy(String payTyp, String chkMan, String chkDate) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(chkMan))
            map.put("chkMan", chkMan);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        if (StringUtils.isNotBlank(payTyp))
            map.put("payTyp", payTyp);

        return getSqlMapClientTemplate().queryForList("BAEXALIST.selectOldAgeReviewRpt02DataBy", map);
    }
}
