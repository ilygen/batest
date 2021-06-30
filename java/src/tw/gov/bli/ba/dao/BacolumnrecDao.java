package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;

import tw.gov.bli.ba.domain.Bacolumnrec;

/**
 * DAO for 眷屬遺屬重編欄位更正記錄檔 (<code>BACOLUMNREC</code>)
 * 
 * @author Rickychi
 */
public interface BacolumnrecDao {
    /**
     * 新增資料到 眷屬遺屬重編欄位更正記錄檔(<code>BACOLUMNREC</code>)
     * 
     * @param data <code>Bacolumnrec</code> 物件
     */
    public void insertData(Bacolumnrec data);
    
    /**
     * 新增 眷屬遺屬重編欄位更正記錄檔(<code>BACOLUMNREC</code>) 資料
     * 
     * @param bacolumnrecList
     */
    public void insertData(final List<Bacolumnrec> bacolumnrecList);
}
