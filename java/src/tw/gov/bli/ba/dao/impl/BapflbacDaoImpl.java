package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.BapflbacDao;
import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.domain.Bapflbac;
import tw.gov.bli.ba.domain.Baunacpdtl;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 退匯資料檔 (<code>BAPFLBAC</code>)
 * 
 * @author Goston
 */
@DaoTable("BAPFLBAC")
public class BapflbacDaoImpl extends SqlMapClientDaoSupport implements BapflbacDao {

    /**
     * 依傳入條件取得 退匯資料檔(<code>BAPFLBAC</code>) 資料 for 退匯通知書
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param apNo 受理編號
     * @param benIdn 受款人身分證號
     * @return
     */
    @DaoFieldList("PAYCODE,ISSUYM,APNO,BENIDN")
    public List<Bapflbac> getMonthlyRpt16DataBy(String payCode, String issuYm, String apNo, String benIdn, String chkDate) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("payCode", payCode);
        map.put("issuYm", issuYm);
        map.put("chkDate", chkDate);

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(benIdn))
            map.put("benIdn", benIdn);
        if (StringUtils.isNotBlank(chkDate))
            map.put("chkDate", chkDate);

        return getSqlMapClientTemplate().queryForList("BAPFLBAC.getMonthlyRpt16DataBy", map);
    }

    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 退匯金額分配資料
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacDeathRepayForQryData_2By(String apNo, String seqNo, String oriIssuYm, String payYm, String issuKind) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("oriIssuYm", oriIssuYm);
        map.put("payYm", payYm);
        map.put("issuKind", issuKind);
        getSqlMapClientTemplate().update("BAPFLBAC.updateBapflbacDeathRepayForQryData_2", map);
    }
    
    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 退匯金額分配資料
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacDeathRepayForQryData_4By(String apNo, String seqNo, String issuYm, String payYm) {
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("issuYm", issuYm);
        map.put("payYm", payYm);
        getSqlMapClientTemplate().update("BAPFLBAC.updateBapflbacDeathRepayForQryData_4", map);
    }
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param ORIISSUYM(原核定年月)
     * @param PAYYM(給付年月)
     * @param AFPAYDATE(改匯初付日期)
     * @return 
     */
    @DaoFieldList("APNO,SEQNO,ORIISSUYM,PAYYM,AFPAYDATE")
    public String doExpDeathRepayRec(String apNo, String seqNo, String oriIssuYm, String payYm, String afPayDate, String issuKind) {
    	String procMsgCode = null;
        String procMsg = null;
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("v_i_apNo",  apNo);
        map.put("v_i_seqNo",  seqNo);
        map.put("v_i_oriIssuYm",  oriIssuYm);
        map.put("v_i_payYm",  payYm);
        map.put("v_i_afPayDate",  afPayDate);
        map.put("v_i_issuKind", issuKind);
        
        getSqlMapClientTemplate().queryForObject("BAPFLBAC.UpdateDeathRepayParam", map);
		
        procMsgCode=(String)map.get("v_o_procMsgCode");
        procMsg = (String)map.get("v_o_procMsg");
        
        return procMsgCode+procMsg;

    } 
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param ORIISSUYM(原核定年月)
     * @param PAYYM(給付年月)
     * @param BRCHKDATE(改匯初付日期)
     * @return 
     */
    @DaoFieldList("APNO,SEQNO,ORIISSUYM,PAYYM,BRCHKDATE")
    public String doDeathRepayRefundment(String apNo, String seqNo, String oriIssuYm, String payYm, String brChkDate, String issuKind) {
    	String procMsgCode = null;
        String procMsg = null;
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("v_i_apNo",  apNo);
        map.put("v_i_seqNo",  seqNo);
        map.put("v_i_oriIssuYm",  oriIssuYm);
        map.put("v_i_payYm",  payYm);
        map.put("v_i_brChkDate",  brChkDate);
        map.put("v_i_issuKind", issuKind);

        getSqlMapClientTemplate().queryForObject("BAPFLBAC.UpdateDeathRepayRefundment", map);
		
        procMsgCode=(String)map.get("v_o_procMsgCode");
        procMsg = (String)map.get("v_o_procMsg");
        
        return procMsgCode+procMsg;

    } 
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param ISSUYM(原核定年月)
     * @param PAYYM(給付年月)
     * @param PAYDATE
     * @return 
     */
    @DaoFieldList("APNO,SEQNO,ORIISSUYM,PAYYM,BRCHKDATE")
    public String doExpSingleRec(String apNo, String seqNo, String issuYm, String payYm, String payDate) {
    	String procMsgCode = null;
        String procMsg = null;
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("v_i_apNo",  apNo);
        map.put("v_i_seqNo",  seqNo);
        map.put("v_i_issuYm",  issuYm);
        map.put("v_i_payYm",  payYm);
        map.put("v_i_payDate",  payDate);

        getSqlMapClientTemplate().queryForObject("BAPFLBAC.doExpSingleRec", map);
		
        procMsgCode=(String)map.get("v_o_procMsgCode");
        procMsg = (String)map.get("v_o_procMsg");
        
        return procMsgCode+procMsg;

    } 
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param ORIISSUYM(原核定年月)
     * @param PAYYM(給付年月)
     * @param BRCHKDATE(改匯初付日期)
     * @return 
     */
    public String doExpCancelSingleRec(String apNo, String seqNo, String oriIssuYm, String payYm, String brChkDate){
    	String procMsgCode = null;
        String procMsg = null;
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("v_i_apNo",  apNo);
        map.put("v_i_seqNo",  seqNo);
        map.put("v_i_oriIssuYm",  oriIssuYm);
        map.put("v_i_payYm",  payYm);
        map.put("v_i_brChkDate",  brChkDate);

        getSqlMapClientTemplate().queryForObject("BAPFLBAC.doExpCancelSingleRec", map);
		
        procMsgCode=(String)map.get("v_o_procMsgCode");
        procMsg = (String)map.get("v_o_procMsg");
        
        return procMsgCode+procMsg;

    } 
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param ACPDATE 收回日期
     * @return 
     */
    public String doRtnCashRpt(String apNo, String seqNo, String acpDate){
    	String procMsgCode = null;
        String procMsg = null;
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("v_i_apNo",  apNo);
        map.put("v_i_seqNo",  seqNo);
        map.put("v_i_acpDate",  acpDate);

        getSqlMapClientTemplate().queryForObject("BAPFLBAC.doRtnCashRpt", map);
		
        procMsgCode=(String)map.get("v_o_procMsgCode");
        procMsg = (String)map.get("v_o_procMsg");
        
        return procMsgCode+procMsg;

    } 
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param ACPDATE 收回日期
     * @return 
     */
    public String doRtnRefundmentRpt(String apNo, String seqNo, String acpDate){
    	String procMsgCode = null;
        String procMsg = null;
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("v_i_apNo",  apNo);
        map.put("v_i_seqNo",  seqNo);
        map.put("v_i_acpDate",  acpDate);

        getSqlMapClientTemplate().queryForObject("BAPFLBAC.doRtnRefundmentRpt", map);
		
        procMsgCode=(String)map.get("v_o_procMsgCode");
        procMsg = (String)map.get("v_o_procMsg");
        
        return procMsgCode+procMsg;

    } 
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param CANCELDATE(註銷日期)
     * @return 
     */
    public String doCancelRtnCashRpt(String apNo, String seqNo, String cancelDate){
    	String procMsgCode = null;
        String procMsg = null;
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("v_i_apNo",  apNo);
        map.put("v_i_seqNo",  seqNo);
        map.put("v_i_cancelDate",  cancelDate);

        getSqlMapClientTemplate().queryForObject("BAPFLBAC.doCancelRtnCashRpt", map);
		
        procMsgCode=(String)map.get("v_o_procMsgCode");
        procMsg = (String)map.get("v_o_procMsg");
        
        return procMsgCode+procMsg;

    } 
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param CANCELDATE(註銷日期)
     * @return 
     */
    public String doCancelRtnRefundmentRpt(String apNo, String seqNo, String cancelDate){
    	String procMsgCode = null;
        String procMsg = null;
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("v_i_apNo",  apNo);
        map.put("v_i_seqNo",  seqNo);
        map.put("v_i_cancelDate",  cancelDate);

        getSqlMapClientTemplate().queryForObject("BAPFLBAC.doCancelRtnRefundmentRpt", map);
		
        procMsgCode=(String)map.get("v_o_procMsgCode");
        procMsg = (String)map.get("v_o_procMsg");
        
        return procMsgCode+procMsg;

    } 
    
    
    /**
     * 新增 退匯檔(<code>BAPFLBAC</code>) 資料 for 死亡改匯處理作業
     * 
     * @param Bapflbac 退匯檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertBapflbacDataForDeathRepayBy(Bapflbac data) {
    	return (BigDecimal) getSqlMapClientTemplate().insert("BAPFLBAC.insertBapflbacDataForDeathRepay", data);
    }
    
    /**
     * 新增 退匯檔(<code>BAPFLBAC</code>) 資料 for 死亡改匯處理作業
     * 
     * @param Bapflbac 退匯檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertBapflbacDataForDeathRepay2By(Bapflbac data) {
    	return (BigDecimal) getSqlMapClientTemplate().insert("BAPFLBAC.insertBapflbacDataForDeathRepay2", data);
    }
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 老年年金應收收回處理
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bapflbac> selectRemittanceReceiveDataListBy(String apNo,String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BAPFLBAC.selectRemittanceReceiveDataListBy", map);
    }
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 失能年金應收收回處理
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bapflbac> selectDisabledRemittanceReceiveDataListBy(String apNo,String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BAPFLBAC.selectDisabledRemittanceReceiveDataListBy", map);
    }
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 遺屬年金應收收回處理
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bapflbac> selectSurvivorRemittanceReceiveDataListBy(String apNo,String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BAPFLBAC.selectSurvivorRemittanceReceiveDataListBy", map);
    }
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 老年年金應收收回處理 註銷
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bapflbac> selectRemittanceReceivedDataListBy(String apNo,String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BAPFLBAC.selectRemittanceReceivedDataListBy", map);
    }
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 失能年金應收收回處理 註銷
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bapflbac> selectDisabledRemittanceReceivedDataListBy(String apNo,String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BAPFLBAC.selectDisabledRemittanceReceivedDataListBy", map);
    }
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 遺屬年金應收收回處理 註銷
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Bapflbac> selectSurvivorRemittanceReceivedDataListBy(String apNo,String seqNo) {
        HashMap<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotBlank(apNo))
            map.put("apNo", apNo);
        if (StringUtils.isNotBlank(seqNo))
            map.put("seqNo", seqNo);

        return getSqlMapClientTemplate().queryForList("BAPFLBAC.selectSurvivorRemittanceReceivedDataListBy", map);
    }
    
    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 年金應收收回處理作業-退匯收回
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacForPaymentReceive(Bapflbac bapflbac){

        getSqlMapClientTemplate().update("BAPFLBAC.updateBapflbacForPaymentReceive", bapflbac);
    }
    
    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 年金應收收回處理作業-退匯收回註銷
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacForCancelReceive(Bapflbac bapflbac){

        getSqlMapClientTemplate().update("BAPFLBAC.updateBapflbacForCancelReceive", bapflbac);
    }
    
    /**
     * 依傳入條件取得 退匯檔(<code>BAPFLBAC</code>) 資料 for 已退匯止付尚未應收款立帳案件清單
     * 
     * @param payCode 給付別
     * @return 內含 <code>Bapflbac</code> 物件的 List
     */
    public List<Bapflbac> selectOtherRpt03DataListBy(String payCode){
    	HashMap<String, String> map = new HashMap<String, String>();
        
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        
        return getSqlMapClientTemplate().queryForList("BAPFLBAC.selectOtherRpt03DataListBy", map);
    }
    
    /**
     * 依傳入條件取得 退匯檔(<code>BAPFLBAC</code>) 資料 for 已退匯或退回現金尚未沖轉收回案件清單
     * 
     * @param payCode 給付別
     * @return 內含 <code>Bapflbac</code> 物件的 List
     */
    public List<Bapflbac> selectOtherRpt02DataListBy(String payCode){
    	HashMap<String, String> map = new HashMap<String, String>();
        
        if (StringUtils.isNotBlank(payCode))
            map.put("payCode", payCode);
        
        return getSqlMapClientTemplate().queryForList("BAPFLBAC.selectOtherRpt02DataListBy", map);
    }
}
