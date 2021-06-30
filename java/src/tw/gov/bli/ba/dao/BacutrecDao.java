package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bacutrec;

/**
 * DAO for 另案扣減紀錄檔 (<code>BACUTREC</code>)
 * 
 * @author Rickychi
 */
public interface BacutrecDao {
    /**
     * 依傳入條件取得 另案扣減紀錄檔(<code>BAAPPBASE</code>) 資料
     * 
     * @param bacutrecId 資料列編號
     * @param baappbaseId給付主檔資料編號
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param procStat 處理狀態
     * @param eqType SQL EqualType
     * @return 內含 <code>Bacutrec</code> 物件的 List
     */
    public List<Bacutrec> selectDataBy(BigDecimal bacutrecId, BigDecimal baappbaseId, String apNo, String seqNo, String[] procStat, String eqType);
}
