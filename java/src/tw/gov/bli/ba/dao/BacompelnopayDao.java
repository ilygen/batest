package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Bacompelnopay;
import tw.gov.bli.ba.domain.Bastudterm;

/**
 * DAO for 遺屬強迫不合格記錄檔 (<code>BACOMPELNOPAY</code>)
 * 
 * @author Rickychi
 */
public interface BacompelnopayDao {
    /**
     * 依傳入條件取得 遺屬強迫不合格記錄檔 (<code>BACOMPELNOPAY</code>)(<code>BACOMPELNOPAY</code>)
     * 
     * @param data <code>Bacolumnrec</code> 物件
     */
    public List<Bacompelnopay> selectDataBy(String apNo, String seqNo);

    /**
     * 新增 遺屬強迫不合格記錄檔(<code>BACOMPELNOPAY</code>)
     * 
     * @param data <code>Bacolumnrec</code> 物件
     */
    public void insertData(Bacompelnopay bacompelnopay);

    /**
     * 修改 遺屬強迫不合格記錄檔(<code>BACOMPELNOPAY</code>) 資料
     * 
     * @param bacolumnrecList
     */
    public void updateData(Bacompelnopay bacompelnopay);

    /**
     * 刪除 遺屬強迫不合格記錄檔(<code>BACOMPELNOPAY</code>) 資料
     * 
     * @param bacolumnrecList
     */
    public void deleteData(String apNo, String seqNo);
    
    /**
     * 依傳入條件取得 遺屬ＸＸ期間檔 (<code>BACOMPELNOPAY</code>) 強迫不合格資料 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return <code>Bacompelnopay</code> 物件
     */
    public Bacompelnopay selectCompelNopayDataForSurvivorPaymentQuery(String apNo, String seqNo); 
    
    /**
     * 依傳入條件取得 遺屬ＸＸ期間檔 (<code>BACOMPELNOPAY</code>) 強迫不合格資料明細 for 遺屬年金給付查詢
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bacompelnopay</code> 物件的List
     */
    public List<Bacompelnopay> selectCompelNopayDetailDataForSurvivorPaymentQuery(String apNo, String seqNo);    
}
