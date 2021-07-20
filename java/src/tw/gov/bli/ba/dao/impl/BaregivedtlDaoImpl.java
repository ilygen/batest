package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BaregivedtlDao;
import tw.gov.bli.ba.domain.Baregivedtl;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAOImpl for 改匯資料檔 (<code>BAREGIVEDTL</code>)
 * 
 * @author Rickychi
 */
@DaoTable("BAREGIVEDTL")
public class BaregivedtlDaoImpl extends SqlMapClientDaoSupport implements BaregivedtlDao {
    /**
     * 依傳入條件取得 改匯資料檔 (<code>BAREGIVEDTL</code>) 資料 for 給付資料更正
     * 
     * @param apNo 受理編號
     * @param accSeqNo 受款人序號
     * @return 內含 <code>Baregivedtl</code> 物件的 List
     */
    @DaoFieldList("APNO,ACCSEQNO")
    public List<Baregivedtl> selectPayDataForPaymentDataUpdate(String apNo, String accSeqNo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("accSeqNo", accSeqNo);
        return getSqlMapClientTemplate().queryForList("BAREGIVEDTL.selectPayDataForPaymentDataUpdate", map);
    }
    
    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 退匯金額分配資料
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlDeathRepayForQryData_2By(String apNo, String seqNo, String oriIssuYm, String payYm, String procUser,String procDeptId, String procIp, String issuKind) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("oriIssuYm", oriIssuYm);
        map.put("payYm", payYm);
        map.put("procUser", procUser);
        map.put("procDeptId", procDeptId);
        map.put("procIp", procIp);
        map.put("issuKind", issuKind);
        
        getSqlMapClientTemplate().update("BAREGIVEDTL.updateBaregivedtlDeathRepayForQryData_2", map);
    }
    
    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 退匯金額分配資料
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlDeathRepayForQryData_4By(String apNo, String seqNo, String issuYm, String payYm, String procUser,String procDeptId, String procIp, String issuKind) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("issuYm", issuYm);
        map.put("payYm", payYm);
        map.put("procUser", procUser);
        map.put("procDeptId", procDeptId);
        map.put("procIp", procIp);
        map.put("issuKind", issuKind);
        getSqlMapClientTemplate().update("BAREGIVEDTL.updateBaregivedtlDeathRepayForQryData_4", map);
    }
    
    /**
     * 依條件取得 改匯黨資料(<code>BAREGIVEDTL</code>) 
     * For 無勾選時確認是否該筆資料當天有無改匯或取消紀錄
     * @param Baregivedtl 改匯檔
     */
    @DaoFieldList("APNO,SEQNO,ORIISSUYM,PAYYM")
    public String selectCheckAfChkDateForCheckBox(String apNo, String seqNo, String oriIssuYm, String payYm) {
    	HashMap<String, String> map = new HashMap<String,String>();
    	if(StringUtils.isNotBlank(apNo))
    		map.put("apNo",apNo);
    	if(StringUtils.isNotBlank(seqNo))
    		map.put("seqNo",seqNo);
    	if(StringUtils.isNotBlank(oriIssuYm))
    		map.put("oriIssuYm",oriIssuYm);
    	if(StringUtils.isNotBlank(payYm))
    		map.put("payYm",payYm);
        return (String) getSqlMapClientTemplate().queryForObject("BAREGIVEDTL.selectCheckAfChkDateForCheckBox", map);
    }
    
    /**
     * 依條件取得 【改匯註銷】按鈕的狀態及使用條件(是否開啟的條件) DataCount2 (<code>BAREGIVEDTL</code>) 
     * For Update OldAgeDeathRepay
     */
    @DaoFieldList("APNO,SEQNO")
    public String getDataCount2ForQuery(String apNo, String seqNo) {
    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAREGIVEDTL.getDataCount2ForQuery", map);
    }
    
    /**
     * 依條件取得 【改匯註銷】按鈕的狀態及使用條件(是否開啟的條件) DataCount3 (<code>BAREGIVEDTL</code>) 
     * For Update OldAgeDeathRepay
     */
    @DaoFieldList("APNO,SEQNO")
    public String getDataCount3ForQuery(String apNo, String seqNo) {
    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        return (String) getSqlMapClientTemplate().queryForObject("BAREGIVEDTL.getDataCount3ForQuery", map);
    }
    
    /**
     * 依條件取得 【退匯金額】(<code>BAREGIVEDTL</code>) 
     * For Update OldAgeDeathRepay
     */
    @DaoFieldList("APNO,SEQNO")
    public BigDecimal getRemitAmtDataForCheckAvgNum(String apNo, String seqNo,String oriIssuYm,String payYm){
    	HashMap<String, String> map = new HashMap<String, String>();
        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("oriIssuYm", oriIssuYm);
        map.put("payYm", payYm);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("BAREGIVEDTL.getRemitAmtDataForCheckAvgNum", map);
    }
    
    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 年金應收收回處理作業-退匯收回
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlForPaymentReceive(Baregivedtl baregivedtl){
   
        getSqlMapClientTemplate().update("BAREGIVEDTL.updateBaregivedtlForPaymentReceive", baregivedtl);
    }
    
    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 年金應收收回處理作業-退匯收回註銷
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlForCancelReceive(Baregivedtl baregivedtl){
    	
        getSqlMapClientTemplate().update("BAREGIVEDTL.updateBaregivedtlForCancelReceive", baregivedtl);
    }
}
