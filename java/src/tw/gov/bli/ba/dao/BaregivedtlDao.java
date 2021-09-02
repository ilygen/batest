package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Baregivedtl;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.update.cases.BaappbaseDataUpdateCase;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 改匯資料檔 (<code>BAREGIVEDTL</code>)
 * 
 * @author Rickychi
 */
public interface BaregivedtlDao {
    /**
     * 依傳入條件取得 改匯資料檔 (<code>BAREGIVEDTL</code>) 資料 for 給付資料更正
     * 
     * @param apNo 受理編號
     * @param accSeqNo 受款人序號
     * @return 內含 <code>Baregivedtl</code> 物件的 List
     */
    public List<Baregivedtl> selectPayDataForPaymentDataUpdate(String apNo, String accSeqNo);

    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 退匯金額分配資料
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlDeathRepayForQryData_2By(String apNo, String seqNo, String oriIssuYm, String payYm, String procUser,String procDeptId, String procIp, String issuKind);
    
    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 退匯金額分配資料
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlDeathRepayForQryData_4By(String apNo, String seqNo, String issuYm, String payYm, String procUser,String procDeptId, String procIp, String issuKind);
    
    /**
     * 依條件取得 改匯黨資料(<code>BAREGIVEDTL</code>) 
     * For 無勾選時確認是否該筆資料當天有無改匯或取消紀錄
     * @param Baregivedtl 改匯檔
     */
    public String selectCheckAfChkDateForCheckBox(String apNo, String seqNo, String oriIssuYm, String payYm);
    
    /**
     * 依條件取得 【改匯註銷】按鈕的狀態及使用條件(是否開啟的條件) DataCount2 (<code>BAREGIVEDTL</code>) 
     * For Update OldAgeDeathRepay
     */
    public String getDataCount2ForQuery(String apNo, String seqNo);
    
    /**
     * 依條件取得 【改匯註銷】按鈕的狀態及使用條件(是否開啟的條件) DataCount2 (<code>BAREGIVEDTL</code>) 
     * For Update OldAgeDeathRepay
     */
    public String getDataCount3ForQuery(String apNo, String seqNo);
    
    /**
     * 依條件取得 【退匯金額】(<code>BAREGIVEDTL</code>) 
     * For Update OldAgeDeathRepay
     */
    public BigDecimal getRemitAmtDataForCheckAvgNum(String apNo, String seqNo ,String oriIssuYm,String payYm);
    
    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 年金應收收回處理作業-退匯收回
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlForPaymentReceive(Baregivedtl baregivedtl);
    
    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 年金應收收回處理作業-退匯收回
     * 
     * @param Baregivedtl 改匯檔
     */
    public void updateBaregivedtlForCancelReceive(Baregivedtl baregivedtl);
    
    /**
     * 更新 改匯檔(<code>BAREGIVEDTL</code>) 將 AFMK 修改為 D
     * 
     * @param apno
     * @param seqno
     * @return
     */
    public int updateMkAndBrmkAndAfmkByApnoAndSeqno(String apno, String seqno, UserBean userBean);
    
}
