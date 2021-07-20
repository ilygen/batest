package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BanotifyDao;
import tw.gov.bli.ba.domain.Banotify;
import tw.gov.bli.ba.maint.cases.AdviceMaintCase;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 核定通知書格式檔 (<code>BANOTIFY</code>)
 * 
 * @author swim
 */
@DaoTable("BANOTIFY")
public class BanotifyDaoImpl extends SqlMapClientDaoSupport implements BanotifyDao {
    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @param dataSeqNo 資料序號
     * @return <code>List<Banotify></code> 物件
     */
    @DaoFieldList("PAYCODE,AUTHTYP,DATATYP,DATASEQNO")
    public List<Banotify> selectDataBy(String payCode, String authTyp, String dataTyp, String dataSeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(authTyp))
            map.put("authTyp", authTyp);
        if (StringUtils.isNotBlank(dataTyp))
            map.put("dataTyp", dataTyp);
        if (StringUtils.isNotBlank(dataSeqNo))
            map.put("dataSeqNo", dataSeqNo);

        return (List<Banotify>) getSqlMapClientTemplate().queryForList("BANOTIFY.selectDataBy", map);
    }
    
    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @param dataSeqNo 資料序號
     * @return <code>List<AdviceMaintCase></code> 物件
     */
    @DaoFieldList("PAYCODE,AUTHTYP,DATATYP,DATASEQNO")
    public List<AdviceMaintCase> selectData(String payCode, String authTyp, String dataTyp, String dataSeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(authTyp))
            map.put("authTyp", authTyp);
        if (StringUtils.isNotBlank(dataTyp))
            map.put("dataTyp", dataTyp);
        if (StringUtils.isNotBlank(dataSeqNo))
            map.put("dataSeqNo", dataSeqNo);

        return (List<AdviceMaintCase>) getSqlMapClientTemplate().queryForList("BANOTIFY.selectData", map);
    }

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @param dataSeqNo 資料序號
     * @return <code>AdviceMaintCase</code> 物件
     */
    @DaoFieldList("PAYCODE,AUTHTYP,DATATYP,DATASEQNO")
    public AdviceMaintCase selectDataContExplain(String payCode, String authTyp, String dataTyp, String dataSeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(authTyp))
            map.put("authTyp", authTyp);
        if (StringUtils.isNotBlank(dataTyp))
            map.put("dataTyp", dataTyp);
        if (StringUtils.isNotBlank(dataSeqNo))
            map.put("dataSeqNo", dataSeqNo);

        return (AdviceMaintCase) getSqlMapClientTemplate().queryForObject("BANOTIFY.selectData", map);
    }

    /**
     * 新增資料到 核定通知書格式檔 (<code>BANOTIFY</code>)<br>
     * 
     * @param data <code>AdviceMaintCase</code> 物件
     */
    public BigDecimal insertData(AdviceMaintCase data) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BANOTIFY.insertData", data);
    }

    /**
     * 更新資料到 核定通知書格式檔 (<code>BANOTIFY</code>)<br>
     * 
     * @param data <code>AdviceMaintCase</code> 物件
     */
    public int updateData(AdviceMaintCase data) {
        return getSqlMapClientTemplate().update("BANOTIFY.updateData", data);
    }

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return <code>AdviceMaintCase</code> 物件
     */
    @DaoFieldList("PAYCODE,AUTHTYP,DATATYP")
    public List<AdviceMaintCase> selectSingleForUpdateData(String payCode, String authTyp, String dataTyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(authTyp))
            map.put("authTyp", authTyp);
        if (StringUtils.isNotBlank(dataTyp))
            map.put("dataTyp", dataTyp);

        return (List<AdviceMaintCase>) getSqlMapClientTemplate().queryForList("BANOTIFY.selectSingleForUpdateData", map);
    }

    /**
     * 刪除 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料<br>
     * 
     * @param AdviceMaintCase
     */
    public void deleteData(AdviceMaintCase data) {
        getSqlMapClientTemplate().delete("BANOTIFY.deleteData", data);
    }

    /**
     * 更正作業報表 給付函核定通知書
     */
    @DaoFieldList("PAYCODE,AUTHTYP")
    public List<Banotify> selectMonthlyRpt05Report(String payCode, String authTyp) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(authTyp))
            map.put("authTyp", authTyp);
        return (List<Banotify>) getSqlMapClientTemplate().queryForList("BANOTIFY.selectMonthlyRpt05Report", map);
    }

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料 for 勞保老年年金給付受理編審清單
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return
     */
    @DaoFieldList("PAYCODE,AUTHTYP,DATATYP")
    public List<Banotify> getOldAgeReviewRpt01NotifyListBy(String payCode, String authTyp, String dataTyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(authTyp))
            map.put("authTyp", authTyp);
        if (StringUtils.isNotBlank(dataTyp))
            map.put("dataTyp", dataTyp);

        return getSqlMapClientTemplate().queryForList("BANOTIFY.getOldAgeReviewRpt01NotifyListBy", map);
    }

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料 for 給付審核/決行
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return
     */
    @DaoFieldList("PAYCODE,AUTHTYP,DATATYP")
    public List<Banotify> getNotifyListForReviewBy(String payCode, String authTyp, String dataTyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(authTyp))
            map.put("authTyp", authTyp);
        if (StringUtils.isNotBlank(dataTyp))
            map.put("dataTyp", dataTyp);

        return getSqlMapClientTemplate().queryForList("BANOTIFY.getNotifyListForReviewBy", map);
    }
    
    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 核定通知書格式資料
     * 
     * @param apNo 受理編號
     * @return
     */
    @DaoFieldList("APNO")
    public List<String> selectNotifyFormBy(String apNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);        
        return getSqlMapClientTemplate().queryForList("BANOTIFY.selectNotifyFormBy", map);
    }
    
    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料 for 勞保失能年金給付受理編審清單
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return
     */
    @DaoFieldList("PAYCODE,AUTHTYP,DATATYP")
    public List<Banotify> getDisableReviewRpt01NotifyListBy(String payCode, String authTyp, String dataTyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(authTyp))
            map.put("authTyp", authTyp);
        if (StringUtils.isNotBlank(dataTyp))
            map.put("dataTyp", dataTyp);

        return getSqlMapClientTemplate().queryForList("BANOTIFY.getOldAgeReviewRpt01NotifyListBy", map);
    }
    
    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return
     */
    @DaoFieldList("PAYCODE,AUTHTYP,DATATYP")
    public List<Banotify> getSurvivorReviewRpt01NotifyListBy(String payCode, String authTyp, String dataTyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(authTyp))
            map.put("authTyp", authTyp);
        if (StringUtils.isNotBlank(dataTyp))
            map.put("dataTyp", dataTyp);

        return getSqlMapClientTemplate().queryForList("BANOTIFY.getOldAgeReviewRpt01NotifyListBy", map);
    }
    
    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料 for 複檢費用
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return
     */
    @DaoFieldList("PAYCODE,AUTHTYP,DATATYP")
    public List<Banotify> getReviewFeeReceiptwRpt01NotifyListBy(String payCode, String authTyp, String dataTyp) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        if (StringUtils.isNotBlank(authTyp))
            map.put("authTyp", authTyp);
        if (StringUtils.isNotBlank(dataTyp))
            map.put("dataTyp", dataTyp);

        return getSqlMapClientTemplate().queryForList("BANOTIFY.getOldAgeReviewRpt01NotifyListBy", map);
    }
}
