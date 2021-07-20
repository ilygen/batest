package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import tw.gov.bli.ba.domain.Bapaissudate;

/**
 * DAO for 月核定日期參數檔 (<code>BAPAISSUDATE</code>)
 * 
 * @author Goston
 */
public interface BapaissudateDao {
    /**
     * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 勞工紓困貸款繳納本息情形查詢單
     * 
     * @param issuYm 核定年月
     * @return
     */
    public Bapaissudate getDataUpdateRpt01DataBy(String issuYm);

    /**
     * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 線上月核定作業
     * 
     * @param payCode 給付別
     * @return
     */
    public String selectMaxIssuYmBy(String payCode);

    /**
     * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 線上月核定作業
     * 
     * @param payCode 給付別
     * @return
     */
    public BigDecimal selectCheckCountForBjMonthBy(String payCode);
    
    /**
	 * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 失能國併勞批次產製媒體作業
	 * 
	 * @param payCode 給付別
	 * @return
	 */
    public BigDecimal selectCheckCountForBjMonthBy2(String payCode);

    /**
     * 依傳入條件判斷是不是於月核核定日期 for 老年、失能、遺屬年金
     * 
     * @param issuYm 核定年月
     * @param chkDate 核定日期
     * @return
     */
    public BigDecimal selectCheckChkdate(String payCode, String issuYm, String chkDate);

    /**
     * 依傳入條件取得 月核定日期參數檔 (<code>BAPAISSUDATE</code>) 資料 for 線上月核定作業
     * 
     * @param payCode 給付別
     * @return
     */
    public String selectIssuDateForOtherRpt10(String payCode, String issuYm);
    
    /**
     * 依傳入條件取得 (<code>BAPAISSUDATE</code>) 資料 for 補正核付作業
     * 
     * @param payCode 給付別
     * @return
     */
    public String qryChkIssuym(String payCode);
}
