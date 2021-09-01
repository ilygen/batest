package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import tw.gov.bli.ba.domain.Badapr;
import tw.gov.bli.ba.domain.Bapflbac;
import tw.gov.bli.ba.domain.Baunacpdtl;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 退匯資料檔 (<code>BAPFLBAC</code>)
 * 
 * @author Goston
 */
public interface BapflbacDao {
    /**
     * 依傳入條件取得 退匯資料檔(<code>BAPFLBAC</code>) 資料 for 退匯通知書
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param apNo 受理編號
     * @param benIdn 受款人身分證號
     * @return
     */
    public List<Bapflbac> getMonthlyRpt16DataBy(String payCode, String issuYm, String apNo, String benIdn, String chkDate);
    
    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 退匯金額分配資料
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacDeathRepayForQryData_2By(String apNo, String seqNo, String oriIssuYm, String payYm, String issuKind);
    
    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 退匯金額分配資料
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacDeathRepayForQryData_4By(String apNo, String seqNo, String issuYm, String payYm);

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
    public String doExpDeathRepayRec(String apNo, String seqNo, String oriIssuYm, String payYm, String afPayDate, String issuKind); 
    
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
    public String doDeathRepayRefundment(String apNo, String seqNo, String oriIssuYm, String payYm, String brChkDate, String issuKind); 
    
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
    public String doExpSingleRec(String apNo, String seqNo, String issuYm, String payYm, String payDate); 
    
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
    public String doExpCancelSingleRec(String apNo, String seqNo, String oriIssuYm, String payYm, String brChkDate); 
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param ACPDATE 收回日期
     * @return 
     */
    public String doRtnCashRpt(String apNo, String seqNo, String acpDate); 
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param ACPDATE 收回日期
     * @return 
     */
    public String doRtnRefundmentRpt(String apNo, String seqNo, String acpDate); 
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param CANCELDATE(註銷日期)
     * @return 
     */
    public String doCancelRtnCashRpt(String apNo, String seqNo, String cancelDate); 
    
    /**
     * Call StoreProcedure
     * 
     * @param APNO(受理編號)
     * @param SEQNO(受款人序)
     * @param CANCELDATE(註銷日期)
     * @return 
     */
    public String doCancelRtnRefundmentRpt(String apNo, String seqNo, String cancelDate); 

    /**
     * 新增 退匯檔(<code>BAPFLBAC</code>) 資料 for 死亡改匯處理作業
     * 
     * @param Bapflbac 退匯檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertBapflbacDataForDeathRepayBy(Bapflbac data);
    
    /**
     * 新增 退匯檔(<code>BAPFLBAC</code>) 資料 for 死亡改匯處理作業
     * 
     * @param Bapflbac 退匯檔
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertBapflbacDataForDeathRepay2By(Bapflbac data);
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 老年年金應收收回處理
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<Bapflbac> selectRemittanceReceiveDataListBy(String apNo,String seqNo);
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 失能年金應收收回處理
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<Bapflbac> selectDisabledRemittanceReceiveDataListBy(String apNo,String seqNo);
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 遺屬年金應收收回處理
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<Bapflbac> selectSurvivorRemittanceReceiveDataListBy(String apNo,String seqNo);
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 老年年金應收收回處理 註銷
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<Bapflbac> selectRemittanceReceivedDataListBy(String apNo,String seqNo);
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 老年年金應收收回處理 註銷
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<Bapflbac> selectDisabledRemittanceReceivedDataListBy(String apNo,String seqNo);
    
    /**
     * 依傳入條件取得 退匯資料(<code>BAPFLBAC</code>) 資料 for 老年年金應收收回處理 註銷
     * 
     * @param apNo  受理編號
     * @param seqNo 受款人序
     * @return
     */
    public List<Bapflbac> selectSurvivorRemittanceReceivedDataListBy(String apNo,String seqNo);
    
    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 年金應收收回處理作業-退匯收回註銷
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacForPaymentReceive(Bapflbac bapflbac);
    
    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 年金應收收回處理作業-退匯收回
     * 
     * @param Bapflbac 退匯檔
     */
    public void updateBapflbacForCancelReceive(Bapflbac bapflbac);
    
    /**
     * 依傳入條件取得 退匯檔(<code>BAPFLBAC</code>) 資料 for 已退匯止付尚未應收款立帳案件清單
     * 
     * @param payCode 給付別
     * @return 內含 <code>Bapflbac</code> 物件的 List
     */
    public List<Bapflbac> selectOtherRpt03DataListBy(String payCode);
    
    /**
     * 依傳入條件取得 退匯檔(<code>BAPFLBAC</code>) 資料 for 已退匯或退回現金尚未沖轉收回案件清單
     * 
     * @param payCode 給付別
     * @return 內含 <code>Bapflbac</code> 物件的 List
     */
    public List<Bapflbac> selectOtherRpt02DataListBy(String payCode);
    
    /**
     * 依受理編號及受款人序查詢資料筆數
     * @param apno 受理編號
     * @param seqno 受款人序
     * @return
     */
    public Integer selectDataCountByApnoAndSeqno(String apno, String seqno);
    
    /**
     * 更新退匯檔(<code>BAPFLBAC</code>) 將 AFMK 修改為 D
     * 
     * @param apno
     * @param seqno
     * @return
     */
    public int updateAfmkByApnoAndSeqno(String apno, String seqno, UserBean userBean);
}
