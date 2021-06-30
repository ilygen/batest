package tw.gov.bli.ba.dao;

import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;

import tw.gov.bli.ba.bj.cases.BatchRecTemp;
import tw.gov.bli.ba.domain.Bagivedtl;
import tw.gov.bli.ba.framework.domain.UserBean;

/**
 * DAO for 國保資料 (<code>NBEXCEP</code>)
 * 
 * @author Noctis
 */
public interface NbexcepDao {
    
	 /**
     * 依傳入條件取得(<code>NBEXCEP</code>)
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    public Integer selectCountForNbexcep(String evtIds);
    
    /**
     * 依傳入條件取得(<code>NBEXCEP</code>)
     * 
     * @param evtIds 事故者社福識別碼
     * @return
     */
    public String selectCtrlBdtBy(String evtIds);
    
}
