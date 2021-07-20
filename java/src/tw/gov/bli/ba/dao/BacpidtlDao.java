package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bacpidtl;

/**
 * Dao for 物價指數調整明細檔 (<code>BACPIDTL</code>)
 * 
 * @author Kiyomi
 */
public interface BacpidtlDao {
    /**
     * 依傳入的條件取得物價指數調整明細檔 (<code>BACPIDTL</code>) 的資料
     * 
     * @param adjYear 指數年度
     * @return <code>List<CpiDtlMaintCase></code> 物件
     */
    public List<Bacpidtl> selectData(String issuYear);

    /**
     * 依傳入的條件取得物價指數調整明細檔 (<code>BACPIDTL</code>) 的資料
     * 
     * @param ADJYEAR 核定年度
     * @return <code>BACPIDTL<CpiDtlMaintCase></code> 物件
     */
    public List<Bacpidtl> selectListForForCpiRec(String issuYear);

    /**
     * 依傳入的條件取得物價指數調整明細檔 (<code>BACPIDTL</code>) 的資料
     * 
     * @param ADJYEAR 核定年度
     * @return <code>BACPIDTL<CpiDtlMaintCase></code> 物件
     */
    public Bacpidtl selectSingleForCheckSaveDataForDtl(String issuYear, String adjYear);

    /**
     * 取得物價指數調整明細檔 (<code>BACPIDTL</code>) 的資料 For CPIREC使用
     * 
     * @return <code>BACPIDTL<CpiDtlMaintCase></code> 物件
     */
    public List<Bacpidtl> selectSingleForCpiDltForCpiRecData();

    /**
     * 取得物價指數調整明細檔 (<code>BACPIDTL</code>) 的資料 For CPIREC使用
     * 
     * @return <code>BACPIDTL<CpiDtlMaintCase></code> 物件
     */
    public List<Bacpidtl> selectInsertDataForCpiRec(String issuYear);

    /**
     * 新增資料到 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * 
     * @param data <code>Bacpidtl</code> 物件
     */
    public BigDecimal insertData(Bacpidtl data);

    /**
     * 更新資料到 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * 
     * @param data <code>Bacpidtl</code> 物件
     */
    public int updateData(Bacpidtl data);

    /**
     * 刪除 物價指數調整明細檔 (<code>BACPIDTL</code>) 資料
     * 
     * @param cpiYear 指數年度
     * @return <code>BigDecimal</code>
     */
    public void deleteData(String issuYear, String eqType, String[] adjYear, String cpiYear, String cpiYearE, BigDecimal cpiB, BigDecimal cpiE, BigDecimal cpIndex);

    /**
     * 更新資料到 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * 
     * @param data <code>Bacpidtl</code> 物件
     */
    public int updateAdjMk(Bacpidtl data);

    /**
     * 依傳入條件取得 物價指數調整明細檔 (<code>BACPIDTL</code>)<br>
     * SEQNO
     * 
     * @return <code>BigDecimal</code> 物件
     */
    public BigDecimal selectSeqNoForCpiDtl(Bacpidtl data);

    /**
     * 依傳入條件取得 物價指數調整明細檔 (<code>BACPIDTL</code>)
     * 
     * @return <code>List<Bacpidtl></code> 物件
     */
    public List<Bacpidtl> selectAdjYearDataBy(Bacpidtl data);

    /**
     * 依傳入條件取得 物價指數調整明細檔 (<code>BACPIDTL</code>)
     * 
     * @return <code>List<Bacpidtl></code> 物件
     */
    public List<Bacpidtl> selectUpdDelDataBy(Bacpidtl data);

    /**
     * 依傳入條件取得 物價指數調整明細檔 (<code>BACPIDTL</code>)
     * 
     * @return <code>List<Bacpidtl></code> 物件
     */
    public List<Bacpidtl> selectDetailUserDataBy(Bacpidtl data);
}
