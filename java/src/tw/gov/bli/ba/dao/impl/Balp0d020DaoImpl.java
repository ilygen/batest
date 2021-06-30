package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.Balp0d020Dao;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Balp0d020;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * DAO for 審核給付清單紀錄檔 (<code>BALP0D020</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BALP0D020")
public class Balp0d020DaoImpl extends SqlMapClientDaoSupport implements Balp0d020Dao {

    /**
     * 依傳入條件取得 審核給付清單紀錄檔 (<code>BALP0D020</code>) 已執行"打包"列印最大的頁次
     * 
     * @param payCode 給付別
     * @param chkMan 審核人員
     * @param rptDate 報表日期
     * @return
     */
    @DaoFieldList("PAYCODE,CHKMAN,RPTDATE")
    public BigDecimal selectMaxPageNoBy(String payCode, String chkMan, String rptDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("chkMan", chkMan);
        map.put("rptDate", rptDate);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BALP0D020.selectMaxPageNoBy", map);
    }

    /**
     * 依傳入條件取得 審核給付清單紀錄檔 (<code>BALP0D020</code>) 已執行"打包"列印最大的頁次
     * 
     * @param payCode 給付別
     * @param chkMan 審核人員
     * @param chkDate 審核日期
     * @return
     */
    @DaoFieldList("PAYCODE,CHKMAN,RPTDATE")
    public List<Balp0d020> selectNonPackDataBy(String payCode, String chkMan, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("payCode", payCode);
        map.put("chkMan", chkMan);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);
        return getSqlMapClientTemplate().queryForList("BALP0D020.selectNonPackDataBy", map);
    }

    /**
     * 依傳入條件取得 審核給付清單紀錄檔 (<code>BALP0D020</code>) 報表資料 For 審核給付清單
     * 
     * @param payCode 給付別
     * @param chkMan 審核人員
     * @param rptDate 報表日期
     * @param prtTyp 列印項目
     * @param spageNo 起始列印頁次
     * @param epageNo 結束列印頁次
     * @param pageNo 列印頁次
     * @return
     */
    @DaoFieldList("PAYCODE,CHKMAN,RPTDATE,PRTTYP,SPAGENO,EPAGENO,PAGENO")
    public List<Balp0d020> selectOldAgeReviewRpt02DataBy(String payCode, String chkMan, String rptDate, String prtTyp, BigDecimal spageNo, BigDecimal epageNo, BigDecimal pageNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("payCode", payCode);
        map.put("chkMan", chkMan);
        map.put("rptDate", rptDate);
        if (StringUtils.isNotBlank(prtTyp))
            map.put("prtTyp", prtTyp);
        if (spageNo != null)
            map.put("spageNo", spageNo);
        if (epageNo != null)
            map.put("epageNo", epageNo);
        if (pageNo != null)
            map.put("pageNo", pageNo);
        return getSqlMapClientTemplate().queryForList("BALP0D020.selectOldAgeReviewRpt02DataBy", map);
    }

    /**
     * 依傳入條件取得 審核給付清單紀錄檔 (<code>BALP0D020</code>) 已執行"打包"列印最大的頁次
     * 
     * @param balp0d020 審核給付清單紀錄檔
     * @return
     */
    public void updateDataForOldAgeReviewRpt02(Balp0d020 balp0d020) {
        getSqlMapClientTemplate().update("BALP0D020.updateDataForOldAgeReviewRpt02", balp0d020);
    }

    /**
     * 依傳入條件取得 審核給付清單紀錄檔 (<code>BALP0D020</code>) 已執行"打包"列印最大的頁次
     * 
     * @param list 審核給付清單紀錄檔List
     * @return
     */
    public void updateDataForOldAgeReviewRpt02(final List<Balp0d020> list) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (Balp0d020 balp0d020 : list) {
                    executor.insert("BALP0D020.updateDataForOldAgeReviewRpt02", balp0d020);
                }

                int rowsaffected = executor.executeBatch();
                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 更新 審核給付清單紀錄檔 (<code>BALP0D020</code>) For 給付審核
     * 
     * @param balp0d020 審核給付清單紀錄檔
     * @return
     */
    public void updateDataForReview(Balp0d020 balp0d020) {
        getSqlMapClientTemplate().update("BALP0D020.updateDataForReview", balp0d020);
    }

    /**
     * 新增 審核給付清單紀錄檔 (<code>BALP0D020</code>) For 給付審核
     * 
     * @param balp0d020 審核給付清單紀錄檔
     * @return
     */
    public void insertDataForReview(Balp0d020 balp0d020) {
        getSqlMapClientTemplate().insert("BALP0D020.insertDataForReview", balp0d020);
    }

    /**
     * 新增 審核給付清單紀錄檔 (<code>BALP0D020</code>) For 遺屬年金給付審核
     * 
     * @param balp0d020 審核給付清單紀錄檔
     * @return
     */
    public void insertDataForSurvivorPaymentReview(Balp0d020 balp0d020) {
        getSqlMapClientTemplate().insert("BALP0D020.insertDataForSurvivorPaymentReview", balp0d020);
    }

    /**
     * 更新 給付主檔(<code>BAAPPBASE</code>) for 給付決行
     * 
     * @param baappbase 給付主檔
     */
    public void updateDataForDecision(Balp0d020 balp0d020) {
        getSqlMapClientTemplate().update("BALP0D020.updateDataForDecision", balp0d020);
    }

    /**
     * 更新 審核給付清單紀錄檔 (<code>BALP0D020</code>) for 審核給付清單
     */
    public void updateProcmkForOldAgeReviewRpt02() {
        getSqlMapClientTemplate().update("BALP0D020.updateProcmkForOldAgeReviewRpt02", null);
    }

    /**
     * 更新 審核給付清單紀錄檔 (<code>BALP0D020</code>) 
     */
    public void updateProcMkByApNo(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        getSqlMapClientTemplate().update("BALP0D020.updateProcMkByApNo", map);
    }
}
