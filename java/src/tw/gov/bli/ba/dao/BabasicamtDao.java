package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Babasicamt;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.domain.Bacpirec;
import tw.gov.bli.ba.maint.cases.CpiRecMaintCase;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * Dao for 老年年金加計金額調整作業檔 (<code>BABASICAMT</code>)
 *
 * @author frank
 */
public interface BabasicamtDao {
    /**
     * 取得 老年年金加計金額調整作業檔 (<code>BABASICAMT</code>) 的資料
     *
     * @param
     * @return <code>List<BasicAmtMaintCase></code> 物件
     */
    public List<Babasicamt> selectData(String payYmB,String payCode);
    
    /**
     * 取得 老年年金加計金額調整作業檔 最後一筆資料 取得payYmE判斷是否有值(<code>BABASICAMT</code>) 的資料
     *
     * @param
     * @return <code>List<BasicAmtMaintCase></code> 物件
     */
    public Babasicamt checkPayYmEValue(String payCode);
    
    /**
     * 新增資料到 老年年金加計金額調整明細檔 (<code>BABASICAMT</code>)<br>
     *
     * @param data <code>Babasicamt</code> 物件
     */
    public BigDecimal insertData(Babasicamt data);
    
    /**
     * 刪除 老年年金加計金額調整明細檔 (<code>BABASICAMT</code>) 資料
     * 
     * @param payYmB 給付年度起月
     * @return <code>BigDecimal</code>
     */
    public void deleteData(String payCode,String cpiYear1,String cpiYear2);
    
    /**
     * 依傳入的條件取得老年年金加計金額調整明細檔 (<code>BABASICAMT</code>) 的資料
     * 
     * @param payYmB 指數年度
     * @return <code>List<CpiDtlMaintCase></code> 物件
     */
    public Babasicamt checkData(String payYmB);  
    
    /**
     * 依傳入的條件取得 老年年金加計金額調整明細檔 (<code>BABASICAMT</code>) 的資料<br>
     *
     * @param PAYYMB
     *
     * @return <code>Babasicamt</code> 物件
     */
    public  Babasicamt selectSingleForUpdateData(String payYmB,String payCode);
    
    /**
     * 更新資料到 物價指數調整明細檔 (<code>BABASICAMT</code>)<br>
     * 
     * @param data <code>Babasicamt</code> 物件
     */
    public int updateData(Babasicamt data);    
    
    /**
     * 依傳入的條件取得 老年年金加計金額調整明細檔 (<code>BABASICAMT</code>) 的資料<br>
     *
     *@param  CPIYEAR1
     *@param  CPIYEAR2
     * @return <code>Babasicamt</code> 物件
     */
    public  Babasicamt selectSingleForCheckSaveData(String payCode,String cpiYear1,String cpiYear2);
    
    /**
     * 取得給付年月對應之基本金額
     * @param payCode
     * @return
     */
    public List<Babasicamt> selectBasicAmtBy(String payCode);
    
    /**
     * 依傳入的條件取得 老年年金加計金額調整紀錄檔 (<code>BABASICAMT</code>) 的資料<br>
     * 
     * @param payCode 給付別
     * @param payYm 給付年月
     * @return <code>BigDecimal</code> 物件
     */
    public BigDecimal selectBasicAmtForPaymentQuery(String payCode, String payYm);
}
