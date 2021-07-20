package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Pfpfd;
import tw.gov.bli.ba.domain.Pfpfm;

/**
 * DAO for 戶政全戶檔 (<code>PFPFM</code>)
 * 
 * @author Kiyomi
 */
public interface PfpfmDao {

    /**
     * 依傳入條件取得 郵遞區號
     * 
     * @param cPrnDt
     * @return Integer
     */
    public Integer selectCprnpage(String cPrnDt);

    /**
     * 依傳入條件取得 郵遞區號
     * 
     * @param cPrnDt
     * @param payKind
     * @return Integer
     */
    public Integer selectPutfile_page(String cPrnDt, String payKind);
    
    /**
     * 新增(<code>PFPFM</code>) 資料 for 整批收回核定清單
     * 
     * @param pfpfm
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForBatchPaymentReceiveData(Pfpfm pfpfm);
}
