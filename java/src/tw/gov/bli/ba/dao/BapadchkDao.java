package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bapadchk;
import tw.gov.bli.ba.maint.cases.CheckMarkMaintCase;

/**
 * DAO for 編審註記參數檔 (<code>BAPADCHK</code>)
 * 
 * @author swim
 */
public interface BapadchkDao {
    /**
     * 依傳入的條件取得 編審註記參數檔 (<code>BAPADCHK</code>) 的資料<br>
     * 
     * @param chkObj 給付對象
     * @param chkGroup 編審註記類別
     * @param chkCode 編審註記代號
     * 
     * @return <code>List<AdviceMaintCase></code> 物件
     */
    public List<Bapadchk> selectData(String chkObj, String chkGroup, String chkCode);

    /**
     * 新增資料到 編審註記參數檔 (<code>BAPADCHK</code>)<br>
     * 
     * @param data <code>Bapadchk</code> 物件
     */
    public BigDecimal insertData(Bapadchk data);

    /**
     * 更新資料到 編審註記參數檔 (<code>BAPADCHK</code>)<br>
     * 
     * @param data <code>Bapadchk</code> 物件
     */
    public int updateData(Bapadchk data);

    /**
     * 依傳入的條件取得 編審註記參數檔 (<code>BAPADCHK</code>) 的資料<br>
     * 
     * @param chkTyp 編審註記種類
     * @param chkObj 給付對象
     * @param chkGroup 編審註記類別
     * @param chkCode 資料區分
     * @param valibDate 資料區分
     * 
     * @return <code>Bapadchk</code> 物件
     */
    public Bapadchk selectSingleForUpdateData(String chkTyp, String chkObj, String chkGroup, String chkCode, String valibDate);
}
