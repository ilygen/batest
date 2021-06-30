package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BafamilytempDao;
import tw.gov.bli.ba.domain.Bafamilytemp;
import tw.gov.bli.ba.util.StringUtility;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BAFAMILYTEMP")
public class BafamilytempDaoImpl extends SqlMapClientDaoSupport implements BafamilytempDao {
    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) new 資料列編號
     * 
     * @return <code>BigDecimal</code> 物件
     */
    public BigDecimal getSequenceBafamilytempId() {
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAFAMILYTEMP.getSequenceBafamilytempId", null);
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料
     * 
     * @param apNo 受理編號
     * @param baappbaseId 給付主檔資料列編號
     * @return 內含 <code>Bafamilytemp</code> 物件的 List
     */
    @DaoFieldList("BAFAMILYTEMPID,SEQNO")
    public List<Bafamilytemp> selectDataBy(BigDecimal bafamilytempId, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (bafamilytempId != null)
            map.put("bafamilytempId", bafamilytempId);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BAFAMILYTEMP.selectDataBy", map);
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 已存在之眷屬資料 for 失能/遺屬年金給付受理作業
     * 
     * @param bafamilytempId 資料列編號
     * @param famIdnNo 遺屬/眷屬身分證號
     * @param famBrDate 遺屬/眷屬出生日期
     * @param seqNo 序號
     * @param eqType SQL EqualType
     * @return 內含 <code>BigDecimal</code> 物件的 List
     */
    @DaoFieldList("BAFAMILYTEMPID,FAMIDNNO,FAMBRDATE,SEQNO,EQTYPE")
    public List<BigDecimal> selectExistDataForAnnuityReceiptBy(BigDecimal bafamilytempId, String famIdnNo, String famBrDate, String seqNo, String eqType) {
        // 修改FORTIFY
        eqType = StringUtility.changOperator(eqType);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("bafamilytempId", bafamilytempId);
        map.put("famIdnNo", famIdnNo);
        map.put("famBrDate", famBrDate);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        if (StringUtils.isNotBlank(eqType))
            map.put("eqType", eqType);
        return getSqlMapClientTemplate().queryForList("BAFAMILYTEMP.selectExistDataForAnnuityReceiptBy", map);
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 新序號
     * 
     * @param bafamilytempId 資料列編號
     * @return <code>String</code> 物件
     */
    @DaoFieldList("BAFAMILYTEMPID")
    public String selectNewSeqNo(BigDecimal bafamilytempId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("bafamilytempId", bafamilytempId);
        return (String) getSqlMapClientTemplate().queryForObject("BAFAMILYTEMP.selectNewSeqNo", map);
    }

    /**
     * 新增 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void insertDataForDisabledAnnuityReceipt(Bafamilytemp bafamilytemp) {
        getSqlMapClientTemplate().insert("BAFAMILYTEMP.insertDataForDisabledAnnuityReceipt", bafamilytemp);
    }

    /**
     * 修改 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void updateDataForDisabledAnnuityReceipt(Bafamilytemp bafamilytemp) {
        getSqlMapClientTemplate().update("BAFAMILYTEMP.updateDataForDisabledAnnuityReceipt", bafamilytemp);
    }

    /**
     * 修改 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void updateSeqNoForSurvivorAnnuityReceipt(BigDecimal bafamilytempId, String beforeSeqNo, String afterSeqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("bafamilytempId", bafamilytempId);
        if (StringUtils.isNotBlank(beforeSeqNo))
            map.put("beforeSeqNo", beforeSeqNo);
        if (StringUtils.isNotBlank(afterSeqNo))
            map.put("afterSeqNo", afterSeqNo);

        getSqlMapClientTemplate().update("BAFAMILYTEMP.updateSeqNoForSurvivorAnnuityReceipt", map);
    }

    /**
     * 修改 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void updateAcccSeqNoForSurvivorAnnuityReceipt(BigDecimal bafamilytempId, String beforeSeqNo, String afterSeqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("bafamilytempId", bafamilytempId);
        if (StringUtils.isNotBlank(beforeSeqNo))
            map.put("beforeSeqNo", beforeSeqNo);
        if (StringUtils.isNotBlank(afterSeqNo))
            map.put("afterSeqNo", afterSeqNo);

        getSqlMapClientTemplate().update("BAFAMILYTEMP.updateAcccSeqNoForSurvivorAnnuityReceipt", map);
    }

    /**
     * 刪除 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 序號
     */
    public void deleteDataBy(BigDecimal bafamilytempId, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("bafamilytempId", bafamilytempId);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        getSqlMapClientTemplate().delete("BAFAMILYTEMP.deleteDataBy", map);
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 序號
     */
    public List<Bafamilytemp> selectDeleteAfterDataBy(BigDecimal bafamilytempId, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("bafamilytempId", bafamilytempId);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BAFAMILYTEMP.selectDeleteAfterDataBy", map);
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 眷屬資料清單 for 受理作業
     * 
     * @param bafamilytempId 眷屬檔資料列編號
     * @return 內含<code>Bafamilytemp</code> 物件的List
     */
    @DaoFieldList("BAFAMILYTEMPID")
    public List<Bafamilytemp> selectFamDataListForAnnuityReceiptBy(BigDecimal bafamilytempId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("bafamilytempId", bafamilytempId);
        return getSqlMapClientTemplate().queryForList("BAFAMILYTEMP.selectFamDataListForAnnuityReceiptBy", map);
    }

    /**
     * 新增 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 遺屬年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void insertDataForSurvivorAnnuityReceipt(Bafamilytemp bafamilytemp) {
        getSqlMapClientTemplate().insert("BAFAMILYTEMP.insertDataForSurvivorAnnuityReceipt", bafamilytemp);
    }

    /**
     * 修改 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 遺屬年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void updateDataForSurvivorAnnuityReceipt(Bafamilytemp bafamilytemp) {
        getSqlMapClientTemplate().update("BAFAMILYTEMP.updateDataForSurvivorAnnuityReceipt", bafamilytemp);
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 帳號資料
     * 
     * @param bafamilytempId 資料列序號
     * @param apNo 受理編號
     * @return <code>Bafamilytemp</code> 物件
     */
    @DaoFieldList("BAFAMILYTEMPID,SEQNO")
    public Bafamilytemp selectBankDataBy(BigDecimal bafamilytempId, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("bafamilytempId", bafamilytempId);
        map.put("seqNo", seqNo);
        return (Bafamilytemp) getSqlMapClientTemplate().queryForObject("BAFAMILYTEMP.selectBankDataBy", map);
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔(<code>BAFAMILYTEMP</code>) 具名領取資料
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 序號
     * @return 內含 <code>Bafamilytemp</code> 物件的 List
     */
    @DaoFieldList("BAFAMILYTEMPID,SEQNO")
    public List<Bafamilytemp> selectBenListBy(BigDecimal bafamilytempId, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("bafamilytempId", bafamilytempId);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);
        return getSqlMapClientTemplate().queryForList("BAFAMILYTEMP.selectBenListBy", map);
    }

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔((<code>BAFAMILYTEMP</code>) 被具名領取筆數
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 序號
     * @return <code>Integer</code> 物件
     */
    @DaoFieldList("BAFAMILYTEMPID,SEQNO")
    public Integer selectAccSeqNoAmt(BigDecimal bafamilytempId, String seqNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("bafamilytempId", bafamilytempId);
        map.put("seqNo", seqNo);
        return (Integer) getSqlMapClientTemplate().queryForObject("BAFAMILYTEMP.selectAccSeqNoAmt", map);
    }

    /**
     * 更新 遺屬眷屬暫存檔((<code>BAFAMILYTEMP</code>) 具名領取資料 FOR 遺屬年金給付受理
     * 
     * @param bafamilytemp 給付主檔
     */
    public void updateAccDataForSurvivorAnnuityReceipt(Bafamilytemp bafamilytemp) {
        getSqlMapClientTemplate().update("BAFAMILYTEMP.updateAccDataForSurvivorAnnuityReceipt", bafamilytemp);
    }
}
