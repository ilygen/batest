package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 給付延伸主檔 (<code>BAAPPEXPAND</code>)
 * 
 * @author Evelyn Hsu
 */

public interface BastudtermDao {

    /**
     * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Bastudterm</code> 物件
     */
    public Bastudterm selectStudMasterDataForSurvivorPaymentQuery(String apNo, String seqNo);

    /**
     * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料明細 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    public List<Bastudterm> selectStudDetailDataForSurvivorPaymentQuery(String apNo, String seqNo);

    /**
     * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料 for 遺屬年金受款人資料更正
     *
     * @param apNo  受理編號
     * @param seqNo 序號
     * @return <code>Bastudterm</code> 物件
     */
    public List<Bastudterm> selectStudtermListForSurvivorPayeeDataUpdate(String apNo, String seqNo);
    
   
    /**
     * 新增遺屬在學期間資料 (<code>BASTUDTERM</code>) for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    public BigDecimal insertBastudterm(Bastudterm studterm);
    
    /**
     * 刪除遺屬在學期間資料 (<code>BASTUDTERM</code>) for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    public void deleteBastudterm(String apNo, String seqNo);
    
    /**
     * 依傳入條件取得 眷屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料 for 眷屬資料更正
     *
     * @param apNo  受理編號
     * @param seqNo 序號
     * @return <code>Bastudterm</code> 物件
     */
    public List<Bastudterm> selectStudtermListForDependantDataUpdate(String apNo, String seqNo);

    /**
     * 依傳入條件取得 遺屬在學期間檔 (<code>BASTUDTERM</code>) 在學資料明細 for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    public BigDecimal selectMaxTermNoBy(String apNo, String seqNo);

}
