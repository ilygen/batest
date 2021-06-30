package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import tw.gov.bli.ba.dao.BaacpdtlDao;
import tw.gov.bli.ba.dao.BaarclistDao;
import tw.gov.bli.ba.domain.Baacpdtl;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Pfpccky;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;
import tw.gov.bli.ba.domain.Baarclist;

/**
 * DAOIMPL for (<code>BAACPDTL</code>)
 * 
 * @author Noctis
 */
@DaoTable("BAACPDTL")
public class BaacpdtlDaoImpl extends SqlMapClientDaoSupport implements BaacpdtlDao {
   
	/**
     * 新增(<code>BAACPDTL</code>) 資料 for 1. 	老年年金應收收回處理作業 - 退匯
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForRemittanceReceiveData(Baacpdtl baacpdtl) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAACPDTL.insertDataForRemittanceReceiveData", baacpdtl);
    }
    
    /**
     * 新增(<code>BAACPDTL</code>) 資料 for 1. 	老年年金應收收回處理作業 - 退現
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForCashReceiveData(Baacpdtl baacpdtl) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAACPDTL.insertDataForCashReceiveData", baacpdtl);
    }
    
    /**
     * 依傳入條件取得 退現資料檔 (<code>BAACPDTL</code>)  for 老年年金應收收回註銷處理
     * 
     * @param apNo 受理編號
     * @param payCode 給付別
     * @return
     */
    public List<Baacpdtl> selectCashReceivedDataBy(String apNo,String payCode) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(apNo))
            map.put("payCode", payCode);

        return getSqlMapClientTemplate().queryForList("BAACPDTL.selectCashReceivedDataBy", map);
    }
    
    /**
     * 依傳入條件取得 BAUNACPDTLID for 老年年金應收收回註銷處理
     * 
     * @param apNo
     * @param insKdCash
     * @param bliAccountCode
     * @param bookedBook
     * @param bkAccountDt
     * @param batchNo
     * @param cashAmt
     * @param divSeq
     * @param indexNo
     */
    public List<Baacpdtl> selectBaunacpdtlDataForCashReceivedBy(String apNo, String insKdCash, String bliAccountCode, String bookedBook, String bkAccountDt, String batchNo, String serialNo, BigDecimal cashAmt, String divSeq, String indexNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (apNo != null)
            map.put("apNo", apNo);
        if (insKdCash != null)
            map.put("insKdCash", insKdCash);
        if (bliAccountCode != null)
            map.put("bliAccountCode", bliAccountCode);
        if (bookedBook != null)
            map.put("bookedBook", bookedBook);
        if (bkAccountDt != null)
            map.put("bkAccountDt", bkAccountDt);
        if (batchNo != null)
            map.put("batchNo", batchNo);
        if (serialNo != null)
            map.put("serialNo", serialNo);
        if (cashAmt != null)
            map.put("cashAmt", cashAmt);
        if (divSeq != null)
            map.put("divSeq", divSeq);
        if (indexNo != null)
            map.put("indexNo", indexNo);
        return getSqlMapClientTemplate().queryForList("BAACPDTL.selectBaunacpdtlDataForCashReceivedBy", map);
    }
    
    /**
     * 依傳入條件取得 BAUNACPDTLID for 老年年金應收收回註銷處理
     * 
     * @param apNo
     * @param insKdCash
     * @param bliAccountCode
     * @param bookedBook
     * @param bkAccountDt
     * @param batchNo
     * @param cashAmt
     * @param divSeq
     * @param indexNo
     */
    public List<Baacpdtl> selectBaunacpdtlIdForCashReceivedBy(String apNo, String insKdCash, String bliAccountCode, String bookedBook, String bkAccountDt, String batchNo,String serialNo, BigDecimal cashAmt, String divSeq, String indexNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (apNo != null)
        map.put("apNo", apNo);
        if (insKdCash != null)
        map.put("insKdCash", insKdCash);
        if (bliAccountCode != null)
        map.put("bliAccountCode", bliAccountCode);
        if (bookedBook != null)
        map.put("bookedBook", bookedBook);
        if (bkAccountDt != null)
        map.put("bkAccountDt", bkAccountDt);
        if (batchNo != null)
        map.put("batchNo", batchNo);
        if (serialNo != null)
        map.put("serialNo", serialNo);
        if (cashAmt != null)
        map.put("cashAmt", cashAmt);
        if (divSeq != null)
        map.put("divSeq", divSeq);
        if (indexNo != null)
        map.put("indexNo", indexNo);
        return getSqlMapClientTemplate().queryForList("BAACPDTL.selectBaunacpdtlIdForCashReceivedDataBy", map);
    }
    
    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID<br>
     * 
     * @param transActionId
     * @param transActionSeq
     * @param inskdRegive
     * @return
     */
    public List<Baacpdtl> selectBaunacpdtlDataForRemittanceReceivedBy(String transActionId, String transActionSeq, String insKd) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (transActionId != null)
        map.put("transActionId", transActionId);
        if (transActionSeq != null)
        map.put("transActionSeq", transActionSeq);
        if (insKd != null)
        map.put("insKd", insKd);
        return getSqlMapClientTemplate().queryForList("BAACPDTL.selectBaunacpdtlDataForRemittanceReceivedBy", map);
    }
    
    /**
     * 依傳入條件取得 BAACPDTL'BAUNACPDTLID LIST<br>
     * 
     * @param transActionId
     * @param transActionSeq
     * @param inskdRegive
     * @return
     */
    public List<Baacpdtl> selectBaunacpdtlIdForRemittanceReceivedBy(String transActionId, String transActionSeq, String insKd) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (transActionId != null)
        map.put("transActionId", transActionId);
        if (transActionSeq != null)
        map.put("transActionSeq", transActionSeq);
        if (insKd != null)
        map.put("insKd", insKd);
        return getSqlMapClientTemplate().queryForList("BAACPDTL.selectBaunacpdtlIdForRemittanceReceivedBy", map);
    }
    
    /**
     * 更新 退現資料檔(<code>BAACPDTL</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBaacpdtlForCancelCashReceive(BigDecimal baunacpdtlId, BigDecimal baacpdtlId) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        if (baunacpdtlId != null)
            map.put("baunacpdtlId", baunacpdtlId);

        if (baacpdtlId != null)
            map.put("baacpdtlId", baacpdtlId);

        getSqlMapClientTemplate().update("BAACPDTL.updateBaacpdtlForCancelCashReceive", map);
    }
    
    /**
     * 新增(<code>BAACPDTL</code>) 資料 for 1.  老年年金應收收回處理作業 - 退匯
     * 
     * @param baacpdtl
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Baacpdtl baacpdtl) {
        return (BigDecimal) getSqlMapClientTemplate().insert("BAACPDTL.insertDataForBatchPaymentReceiveData", baacpdtl);
    }
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR 整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */    
    public List<Baacpdtl> selectBaacpdtlDataBy(String apNo,String seqNo, String acpDate) {
        HashMap<String, String> map = new HashMap<String, String>();
       
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(acpDate)) {
            map.put("acpDate", acpDate);
        }        
        
        return getSqlMapClientTemplate().queryForList("BAACPDTL.selectBaacpdtlDataBy", map);
    }
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR 更正作業 - 應收收回處理作業 - 老年年金應收收回處理
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */
    public List<Baacpdtl> selectBaacpdtlDataForPaymentReceiveBy(String apNo, String seqNo, BigDecimal baunacpdtlId, String batchNo, String bkAccountDt, String bli_Account_Code, String bookedBook, BigDecimal cash_Amt, String divSeq, String index_No, String serialNo) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        
        if (baunacpdtlId != null) {
            map.put("baunacpdtlId", baunacpdtlId);
        }

        if (StringUtils.isNotBlank(batchNo)) {
            map.put("batchNo", batchNo);
        }
        
        if (StringUtils.isNotBlank(bkAccountDt)) {
            map.put("bkAccountDt", bkAccountDt);
        }
        
        if (StringUtils.isNotBlank(bli_Account_Code)) {
            map.put("bli_Account_Code", bli_Account_Code);
        }

        if (StringUtils.isNotBlank(bookedBook)) {
            map.put("bookedBook", bookedBook);
        }
        
        if (cash_Amt != null) {
            map.put("cash_Amt", cash_Amt);
        }
        
        if (StringUtils.isNotBlank(divSeq)) {
            map.put("divSeq", divSeq);
        }

        if (StringUtils.isNotBlank(index_No)) {
            map.put("index_No", index_No);
        }
        
        if (StringUtils.isNotBlank(serialNo)) {
            map.put("serialNo", serialNo);
        }

        return getSqlMapClientTemplate().queryForList("BAACPDTL.selectBaacpdtlDataForPaymentReceiveBy", map);
    }
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （失能）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */    
    public List<Baacpdtl> selectKBaacpdtlDataBy(String apNo,String seqNo, String acpDate) {
        HashMap<String, String> map = new HashMap<String, String>();
       
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(acpDate)) {
            map.put("acpDate", acpDate);
        }        
        
        return getSqlMapClientTemplate().queryForList("BAACPDTL.selectKBaacpdtlDataBy", map);
    }
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （遺屬）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */    
    public List<Baacpdtl> selectSBaacpdtlDataBy(String apNo,String seqNo, String acpDate) {
        HashMap<String, String> map = new HashMap<String, String>();
       
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(acpDate)) {
            map.put("acpDate", acpDate);
        }        
        
        return getSqlMapClientTemplate().queryForList("BAACPDTL.selectSBaacpdtlDataBy", map);
    }
    
    /**
     * 更新 退現資料檔(<code>BAACPDTL</code>) 資料
     * 
     * @param baacpdtl 退現資料檔
     */
    public void updateBaacpdtlForPfmPfd(int v_cprnpage, int v_lcnt, BigDecimal baacpdtlId) {
        
        HashMap<String, String> map = new HashMap<String, String>();
        
        if (StringUtils.isNotBlank(String.valueOf(baacpdtlId))) {
            map.put("baacpdtlId", String.valueOf(baacpdtlId));
        }
        
        if (StringUtils.isNotBlank(String.valueOf(v_cprnpage))) {
            map.put("v_cprnpage", String.valueOf(v_cprnpage));
        }
        
        if (StringUtils.isNotBlank(String.valueOf(v_lcnt))) {
            map.put("v_lcnt", String.valueOf(v_lcnt));
        }
        
        getSqlMapClientTemplate().update("BAACPDTL.updateForPfmPfd", map);
    }
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （老年）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */    
    public List<Baacpdtl> getDataForPfdResult(String payCode,String chkDate, String acpDate, String apNo, String apSeqNo, String payTyp, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
       
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        
        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }
        
        if (StringUtils.isNotBlank(acpDate)) {
            map.put("acpDate", acpDate);
        }
        
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        
        if (StringUtils.isNotBlank(apSeqNo)) {
            map.put("apSeqNo", apSeqNo);
        }
        
        if (StringUtils.isNotBlank(payTyp)) {
            map.put("payTyp", payTyp);
        }
        
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }
        
        return getSqlMapClientTemplate().queryForList("BAACPDTL.getDataForPfdResult", map);
    }
    
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （老年）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */    
    public List<Baacpdtl> getDataForPaymentReceivePfdResult(String payCode,String chkDate, String acpDate, String apNo, String apSeqNo, String payTyp, String issuYm) {
        HashMap<String, String> map = new HashMap<String, String>();
       
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        
        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }
        
        if (StringUtils.isNotBlank(acpDate)) {
            map.put("acpDate", acpDate);
        }
        
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        
        if (StringUtils.isNotBlank(apSeqNo)) {
            map.put("apSeqNo", apSeqNo);
        }
        
        if (StringUtils.isNotBlank(payTyp)) {
            map.put("payTyp", payTyp);
        }
        
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }
        
        return getSqlMapClientTemplate().queryForList("BAACPDTL.getDataForPaymentReceivePfdResult", map);
    }
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （失能）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */    
    public List<Baacpdtl> getDataForPfdKResult(String payCode,String chkDate, String acpDate, String apNo, String apSeqNo, String payTyp, String issuYm, String nlWkMk, String adWkMk) {
        HashMap<String, String> map = new HashMap<String, String>();
       
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        
        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }
        
        if (StringUtils.isNotBlank(acpDate)) {
            map.put("acpDate", acpDate);
        }
        
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        
        if (StringUtils.isNotBlank(apSeqNo)) {
            map.put("apSeqNo", apSeqNo);
        }
        
        if (StringUtils.isNotBlank(payTyp)) {
            map.put("payTyp", payTyp);
        }
        
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }
        
        if (StringUtils.isNotBlank(nlWkMk)) {
            map.put("nlWkMk", nlWkMk);
        }
        
        if (StringUtils.isNotBlank(adWkMk)) {
            map.put("adWkMk", adWkMk);
        }
        
        return getSqlMapClientTemplate().queryForList("BAACPDTL.getDataForPfdKResult", map);
    }
    
    /**
     * 依傳入條件取得 已收明細檔 (<code>BAACPDTL</code>) 資料 FOR （遺屬）整批收回核定清單 - 應收已收核定清單
     * 
     * @param APNO 受理編號
     * @param SEQNO 序號
     * @param ACPDATE
     * @return <code>Baacpdtl</code> 物件
     */    
    public List<Baacpdtl> getDataForPfdSResult(String payCode,String chkDate, String acpDate, String apNo, String apSeqNo, String payTyp, String issuYm, String nlWkMk, String adWkMk) {
        HashMap<String, String> map = new HashMap<String, String>();
       
        if (StringUtils.isNotBlank(payCode)) {
            map.put("payCode", payCode);
        }
        
        if (StringUtils.isNotBlank(chkDate)) {
            map.put("chkDate", chkDate);
        }
        
        if (StringUtils.isNotBlank(acpDate)) {
            map.put("acpDate", acpDate);
        }
        
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        
        if (StringUtils.isNotBlank(apSeqNo)) {
            map.put("apSeqNo", apSeqNo);
        }
        
        if (StringUtils.isNotBlank(payTyp)) {
            map.put("payTyp", payTyp);
        }
        
        if (StringUtils.isNotBlank(issuYm)) {
            map.put("issuYm", issuYm);
        }
        
        if (StringUtils.isNotBlank(nlWkMk)) {
            map.put("nlWkMk", nlWkMk);
        }
        
        if (StringUtils.isNotBlank(adWkMk)) {
            map.put("adWkMk", adWkMk);
        }
        
        return getSqlMapClientTemplate().queryForList("BAACPDTL.getDataForPfdSResult", map);
    }

}
