package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Bafailurelist;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 整批收回失敗清單檔 (<code>BAFAILURELIST</code>)
 * 
 * @author KIYOMI
 */
public interface BafailurelistDao {
    /**
     * 依傳入條件取得 整批收回失敗清單檔(<code>BAFAILURELIST</code>) 資料清單
     * 
     * @param payCode 給付別
     * @param chkDate 核收日期
     * @return 內含 <code>Bafailurelist</code> 物件的 List
     */
    public List<Bafailurelist> selectBafailureListDataBy(String payCode,String chkDate);

    /**
     * 新增(<code>BAFAILURELIST</code>) 資料 for 整批收回核定清單
     * 
     * @param bafailurelist
     * @return <code>BigDecimal</code>
     */
    public BigDecimal insertDataForFailureListData(Bafailurelist bafailurelist);
    

}
