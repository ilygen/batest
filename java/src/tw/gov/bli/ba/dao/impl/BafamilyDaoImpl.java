package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import tw.gov.bli.ba.dao.BafamilyDao;
import tw.gov.bli.ba.domain.Bafamily;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 眷屬檔 (<code>BAFAMILY</code>)
 * 
 * @author Evelyn Hsu
 */
@DaoTable("BAFAMILY")
public class BafamilyDaoImpl extends SqlMapClientDaoSupport implements BafamilyDao {
    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 資料
     * 
     * @param bafamilyId 資料列編號
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    @DaoFieldList("BAFAMILYID,BAAPPBASEID,APNO,SEQNO")
    public List<Bafamily> selectDataBy(BigDecimal bafamilyId, BigDecimal baappbaseId, String apNo, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (bafamilyId != null)
            map.put("bafamilyId", bafamilyId);
        if (baappbaseId != null)
            map.put("baappbaseId", baappbaseId);
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BAFAMILY.selectDataBy", map);
    }

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 眷屬資料 for 勞保XX年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param baappbaseId 給付主檔資料列編號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    @DaoFieldList("APNO,BAAPPBASEID")
    public List<Bafamily> selectReviewRpt01FamilyListBy(String apNo, BigDecimal baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("baappbaseId", baappbaseId);
        return getSqlMapClientTemplate().queryForList("BAFAMILY.selectReviewRpt01FamilyListBy", map);
    }

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 眷屬資料 for 失能年金編審註記程度調整 (眷屬資料)
     * 
     * @param apNo 受理編號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    @DaoFieldList("APNO")
    public List<Bafamily> getDisabledCheckMarkLevelAdjustFamListBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);

        return getSqlMapClientTemplate().queryForList("BAFAMILY.getDisabledCheckMarkLevelAdjustFamListBy", map);
    }

    /**
     * 新增 眷屬檔 (<code>BAFAMILY</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamily 眷屬檔
     */
    public BigDecimal insertDataForDisabledAnnuityReceipt(Bafamily bafamily) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAFAMILY.insertDataForDisabledAnnuityReceipt", bafamily);
    }

    /**
     * 新增 眷屬檔 (<code>BAFAMILY</code>) 資料(多筆) For 失能年金受理作業
     * 
     * @param bafamily 眷屬檔
     */
    public void insertDataForDisabledAnnuityReceipt(final List<Bafamily> bafamilyList) {
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (int i = 0; i < bafamilyList.size(); i++) {
                    Bafamily bafamily = bafamilyList.get(i);
                    executor.insert("BAFAMILY.insertDataForDisabledAnnuityReceipt", bafamily);
                }
                int rowsaffected = executor.executeBatch();

                return new Integer(rowsaffected);
            }
        });
    }

    /**
     * 修改 眷屬檔 (<code>BAFAMILY</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamily 眷屬檔
     */
    public void updateDataForDisabledAnnuityReceipt(Bafamily bafamily) {
        getSqlMapClientTemplate().update("BAFAMILY.updateDataForDisabledAnnuityReceipt", bafamily);
    }

    /**
     * 刪除 眷屬檔 (<code>BAFAMILY</code>) 資料 By bafamilyId
     * 
     * @param bafamilyId 眷屬檔資料列編號
     */
    public void deleteDataByBafamilyId(BigDecimal bafamilyId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("bafamilyId", bafamilyId);
        getSqlMapClientTemplate().delete("BAFAMILY.deleteDataByBafamilyId", map);
    }

    /**
     * 刪除 眷屬檔 (<code>BAFAMILY</code>) 資料 By baappbaseId
     * 
     * @param baappbaseId 給付主檔資料列編號
     */
    public void deleteDataByBaappbaseId(BigDecimal baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        getSqlMapClientTemplate().delete("BAFAMILY.deleteDataByBaappbaseId", map);
    }

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 資料 By baappbaseId
     * 
     * @param baappbaseId 給付主檔資料列編號
     */
    public List<Bafamily> selectDataByBaappbaseIdByLog(BigDecimal baappbaseId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        return getSqlMapClientTemplate().queryForList("BAFAMILY.selectDataByBaappbaseIdByLog", map);
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 已存在之眷屬資料 for 失能/遺屬年金給付受理作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param famIdnNo 遺屬/眷屬身分證號
     * @param famBrDate 遺屬/眷屬出生日期
     * @param seqNo 序號
     * @param eqType SQL EqualType
     * @return 內含 <code>BigDecimal</code> 物件的 List
     */
    @DaoFieldList("BAAPPBASEID,FAMIDNNO,FAMBRDATE,SEQNO,EQTYPE")
    public List<BigDecimal> selectExistDataForAnnuityReceiptBy(BigDecimal baappbaseId, String famIdnNo, String famBrDate, String seqNo, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("famIdnNo", famIdnNo);
        map.put("famBrDate", famBrDate);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(eqType))
            map.put("eqType", eqType);
        return getSqlMapClientTemplate().queryForList("BAFAMILY.selectExistDataForAnnuityReceiptBy", map);
    }

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 新序號
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @return <code>String</code> 物件
     */
    @DaoFieldList("BAAPPBASEID,APNO")
    public String selectNewSeqNo(BigDecimal baappbaseId, String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("apNo", apNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAFAMILY.selectNewSeqNo", map);
    }

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 眷屬資料清單 for 失能年金受理作業
     * 
     * @param baappbaseId 給付主檔資料列編號
     * @param apNo 受理編號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    @DaoFieldList("BAAPPBASEID,APNO")
    public List<Bafamily> selectDisabledFamDataListBy(BigDecimal baappbaseId, String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAFAMILY.selectDisabledFamDataListBy", map);
    }

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 眷屬資料 for 眷屬資料更正作業
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<Bafamily> selectDependantDataForList(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAFAMILY.selectDependantDataListBy", map);
    }

    /**
     * 取得 眷屬資料筆數
     */
    @DaoFieldList("APNO,FAMIDNNO,FAMBRDATE")
    public Integer getDependantDataCount(String apNo, String famIdnNo, String bafamilyId) {

        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(famIdnNo))
            map.put("famIdnNo", famIdnNo);
        if (StringUtils.isNotBlank(bafamilyId))
            map.put("bafamilyId", bafamilyId);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAFAMILY.getDependantDataCount", map);
    }

    /**
     * 取得 眷屬資料筆數
     */
    @DaoFieldList("APNO,FAMIDNNO,FAMBRDATE")
    public Integer getEvtDataCount(String apNo, String famIdnNo, String bafamilyId) {

        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(famIdnNo))
            map.put("famIdnNo", famIdnNo);
        if (StringUtils.isNotBlank(bafamilyId))
            map.put("bafamilyId", bafamilyId);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAFAMILY.getEvtDataCount", map);
    }

    /**
     * 取得 眷屬資料序號
     */
    @DaoFieldList("APNO")
    public Integer getDependantSeqNoDataCount(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAFAMILY.getDependantSeqNoCount", map);
    }

    /**
     * 新增 眷屬檔(<code>BAFAMILY</code>) 資料
     * 
     * @param bafamily 資料編號
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertData(Bafamily bafamily) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAFAMILY.insertData", bafamily);
    }

    /**
     * 修改 眷屬檔 (<code>BAFAMILY</code>) 資料
     * 
     * @param bafamily 眷屬檔
     * @return <code>BigDecimal</code>
     */
    public void updateDataForDependant(Bafamily bafamily) {
        getSqlMapClientTemplate().update("BAFAMILY.updateDataForDependant", bafamily);
    }

    /**
     * 刪除 眷屬檔 (<code>BAFAMILY</code>) 資料
     * 
     * @param bafamily 眷屬檔
     * @return <code>BigDecimal</code>
     */
    public void deleteDataForDependant(Bafamily bafamily) {
        getSqlMapClientTemplate().update("BAFAMILY.deleteDataForDependant", bafamily);
    }

    /**
     * 依傳入條件取得 眷屬檔(<code>BAFAMILY</code>) 詳細資料
     * 
     * @param bafamilyId 資料編號
     * @return <code>Bafamily</code> 物件
     */
    @DaoFieldList("BAFAMILYID")
    public Bafamily getDependantDataUpdateForUpdateCaseBy(String bafamilyId) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(bafamilyId))
            map.put("bafamilyId", bafamilyId);

        return (Bafamily) getSqlMapClientTemplate().queryForObject("BAFAMILY.selectDependantDataUpdateForUpdateDataBy", map);
    }

    /**
     * 依傳入條件取得 眷屬檔(<code>BAFAMILY</code>) 資料
     * 
     * @param baappbaseId 資料列編號
     * @param apNo 受理編號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    @DaoFieldList("BAAPPBASE,APNO")
    public List<Bafamily> getDisableBafamilyListBy(BigDecimal baappbaseId, String apNo) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("baappbaseId", baappbaseId);
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        return getSqlMapClientTemplate().queryForList("BAFAMILY.selectDisableReviewRptListBy", map);

    }

    /**
     * 依傳入條件取得 眷屬檔(<code>BAFAMILY</code>) 資料 For 失能年金給付查詢作業
     * 
     * @param apNo 受理編號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    @DaoFieldList("APNO")
    public List<Bafamily> selectFamilyDataForDisabledPaymentQuery(String apNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        return getSqlMapClientTemplate().queryForList("BAFAMILY.selectFamilyDataForDisabledPaymentQuery", map);
    }

    /**
     * 依傳入條件取得 給付核定檔(<code>BADAPR</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public Boolean getDisableReviewRpt01BafamilyByCount(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (getSqlMapClientTemplate().queryForObject("BAFAMILY.getDisableReviewRpt01BafamilyByCount", map) != null && (Integer) getSqlMapClientTemplate().queryForObject("BAFAMILY.getDisableReviewRpt01BafamilyByCount", map) > 0)
            return true;
        else
            return false;
    }

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 眷屬資料 for 補送在學證明通知函
     * 
     * @param apNo 受理編號
     * @return 內含<code>Bafamily</code> 物件的List
     */
    @DaoFieldList("APNO")
    public List<Bafamily> selectFamNameForMonthlyRpt29By(String apNo, String sA114RptMk) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("apNo", apNo);
        map.put("sA114RptMk", sA114RptMk);
        return getSqlMapClientTemplate().queryForList("BAFAMILY.selectFamNameForMonthlyRpt29By", map);
    }

    /**
     * 依傳入條件取得 受款人數(<code>BAFAMILY</code>)
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public BigDecimal getPayeeCount(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);

        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAFAMILY.getPayeeCount", map);
    }

}
