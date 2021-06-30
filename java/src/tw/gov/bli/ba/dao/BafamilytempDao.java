package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import tw.gov.bli.ba.domain.Bafamily;
import tw.gov.bli.ba.domain.Bafamilytemp;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>)
 * 
 * @author Rickychi
 */

public interface BafamilytempDao {
    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) new 資料列編號
     * 
     * @return <code>BigDecimal</code> 物件
     */
    public BigDecimal getSequenceBafamilytempId();

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料
     * 
     * @param apNo 受理編號
     * @param baappbaseId 給付主檔資料列編號
     * @return 內含 <code>Bafamilytemp</code> 物件的 List
     */
    public List<Bafamilytemp> selectDataBy(BigDecimal bafamilytempId, String seqNo);

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 已存在之眷屬資料 for 失能/遺屬年金給付受理作業
     * 
     * @param bafamilytempId 資料列編號
     * @param famIdnNo 遺屬/眷屬身分證號
     * @param famBrDate 遺屬/眷屬出生日期
     * @param seqNo 序號
     * @param eqType SQL EqualType
     * @return 內含 <code>BigDecimal</code> 物件的 List
     */
    public List<BigDecimal> selectExistDataForAnnuityReceiptBy(BigDecimal bafamilytempId, String famIdnNo, String famBrDate, String seqNo, String eqType);

    /**
     * 依傳入條件取得 眷屬檔 (<code>BAFAMILY</code>) 新序號
     * 
     * @param bafamilytempId 資料列編號
     * @return <code>String</code> 物件
     */
    public String selectNewSeqNo(BigDecimal bafamilytempId);

    /**
     * 新增 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void insertDataForDisabledAnnuityReceipt(Bafamilytemp bafamilytemp);

    /**
     * 修改 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void updateDataForDisabledAnnuityReceipt(Bafamilytemp bafamilytemp);
    
    /**
     * 修改 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void updateSeqNoForSurvivorAnnuityReceipt(BigDecimal bafamilytempId, String beforeSeqNo, String afterSeqNo);
    
    /**
     * 修改 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void updateAcccSeqNoForSurvivorAnnuityReceipt(BigDecimal bafamilytempId, String beforeSeqNo, String afterSeqNo);

    /**
     * 刪除 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 失能年金受理作業
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 序號
     */
    public void deleteDataBy(BigDecimal bafamilytempId, String seqNo);
    
    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 序號
     */
    public List<Bafamilytemp> selectDeleteAfterDataBy(BigDecimal bafamilytempId, String seqNo);

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 眷屬資料清單 for 受理作業
     * 
     * @param bafamilytempId 眷屬檔資料列編號
     * @return 內含<code>Bafamilytemp</code> 物件的List
     */
    public List<Bafamilytemp> selectFamDataListForAnnuityReceiptBy(BigDecimal bafamilytempId);

    /**
     * 新增 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 遺屬年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void insertDataForSurvivorAnnuityReceipt(Bafamilytemp bafamilytemp);

    /**
     * 修改 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 資料 For 遺屬年金受理作業
     * 
     * @param bafamilytemp 遺屬眷屬暫存檔
     */
    public void updateDataForSurvivorAnnuityReceipt(Bafamilytemp bafamilytemp);

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔 (<code>BAFAMILYTEMP</code>) 帳號資料
     * 
     * @param bafamilytempId 資料列序號
     * @param apNo 受理編號
     * @return <code>Bafamilytemp</code> 物件
     */
    public Bafamilytemp selectBankDataBy(BigDecimal bafamilytempId, String seqNo);

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔(<code>BAFAMILYTEMP</code>) 具名領取資料
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 序號
     * @return 內含 <code>Bafamilytemp</code> 物件的 List
     */
    public List<Bafamilytemp> selectBenListBy(BigDecimal bafamilytempId, String seqNo);

    /**
     * 依傳入條件取得 遺屬眷屬暫存檔((<code>BAFAMILYTEMP</code>) 被具名領取筆數
     * 
     * @param bafamilytempId 資料列編號
     * @param seqNo 序號
     * @return <code>Integer</code> 物件
     */
    public Integer selectAccSeqNoAmt(BigDecimal bafamilytempId, String seqNo);

    /**
     * 更新 遺屬眷屬暫存檔((<code>BAFAMILYTEMP</code>) 具名領取資料 FOR 遺屬年金給付受理
     * 
     * @param bafamilytemp 給付主檔
     */
    public void updateAccDataForSurvivorAnnuityReceipt(Bafamilytemp bafamilytemp);
}
