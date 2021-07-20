package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bachkfile;
import tw.gov.bli.ba.domain.Barecheck;
import tw.gov.bli.ba.update.cases.ChkFileCase;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 重新查核失能程度檔 (<code>BARECHECK</code>)
 * 
 * @author Noctis
 */
public interface BarecheckDao {
	
	 /**
     * 依傳入條件取得 重新查核失能程度檔(<code>BARECHECK</code>) 資料 FOR 失能年金受款人資料更正 重新查核失能程度
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return <code>List<Bachkfile></code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Barecheck> selectBareCheckDataBy(String apNo, String seqNo);
    
    /**
     * 更新 重新查核失能程度檔(<code>BARECHECK</code>) 資料
     * 
     * @param barecheck 重新查核失能程度檔
     */
    public void updateBareCheckData(Barecheck barecheck);
    
    /**
     * 刪除 重新查核失能程度檔(<code>BARECHECK</code>) 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param reChkYm 重新查核失能程度年月
     */
    public void deleteDataForBareCheck(String apNo, String seqNo, String reChkYm);
    
    /**
     * 依傳入條件取得 重新查核失能程度檔(<code>BARECHECK</code>) 資料 FOR  受理審核清單 重新查核失能程度 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return <code>List<Bachkfile></code> 物件
     */
    @DaoFieldList("APNO,SEQNO")
    public List<Barecheck> selectBareCheckDataForRpt01By(String apNo, String seqNo);
    
    /**
     * 依傳入條件取得 重新查核失能程度檔(<code>BARECHECK</code>) 資料 FOR  受理審核清單 重新查核失能程度 資料 資料庫是否已有同月份資料
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @param comReChkYm 完成重新查核年月
     * @return <code>Boolean</code>
     */
    public Boolean selectBareCheckDataCountBy(String apNo, String seqNo, String comReChkYm);
    
    /**
     * 依傳入條件取得 重新查核失能程度檔(<code>BARECHECK</code>) 資料 FOR  受理審核清單 重新查核失能程度 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 受款人序
     * @return <code>Boolean</code>
     */
    public String selectMaxReChkYmBy(String apNo, String seqNo);
}
