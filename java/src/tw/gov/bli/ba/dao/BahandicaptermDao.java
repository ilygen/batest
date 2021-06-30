package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Bahandicapterm;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 給付延伸主檔 (<code>BAAPPEXPAND</code>)
 * 
 * 
 */

public interface BahandicaptermDao {

    /**
     * 依傳入條件取得 遺屬重殘期間檔 (<code>BASHANDICAPTERM</code>) 重殘資料 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Bahandicapterm</code> 物件
     */
    public Bahandicapterm selectHandicapMasterDataForSurvivorPaymentQuery(String apNo, String seqNo);

    /**
     * 依傳入條件取得 遺屬重殘期間檔 (<code>BASHANDICAPTERM</code>) 重殘資料明細 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bahandicapterm</code> 物件的List
     */
    public List<Bahandicapterm> selectHandicapDetailDataForSurvivorPaymentQuery(String apNo, String seqNo);

    /**
     * 依傳入條件取得 遺屬眷屬重殘期間檔 (<code>BASHANDICAPTERM</code>) 重殘期間資料 for 遺屬年金受款人資料更正
     *
     * @param apNo  受理編號
     * @param seqNo 序號
     * @return <code>Bahandicapterm</code> 物件
     */
    public List<Bahandicapterm> selectHandicaptermListForSurvivorPayeeDataUpdate(String apNo, String seqNo);
    
   
    /**
     * 新增遺屬重殘起訖年度資料 (<code>BASHANDICAPTERM</code>) for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bahandicapterm</code> 物件的List
     */
    public BigDecimal insertBahandicapterm(Bahandicapterm handicapterm);
    
    /**
     * 刪除遺屬重殘期間資料 (<code>BASHANDICAPTERM</code>) for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bahandicapterm</code> 物件的List
     */
    public void deleteBahandicapterm(String apNo, String seqNo);
    
    /**
     * 依傳入條件取得 眷屬重殘期間檔 (<code>BASHANDICAPTERM</code>) 重殘資料 for 眷屬資料更正
     *
     * @param apNo  受理編號
     * @param seqNo 序號
     * @return <code>Bahandicapterm</code> 物件
     */
    public List<Bahandicapterm> selectHandicaptermListForDependantDataUpdate(String apNo, String seqNo);

    /**
     * 依傳入條件取得 遺屬重殘期間檔 (<code>BASHANDICAPTERM</code>) 重殘資料明細 for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bahandicapterm</code> 物件的List
     */
    public BigDecimal selectMaxTermNoBy(String apNo, String seqNo);

}
