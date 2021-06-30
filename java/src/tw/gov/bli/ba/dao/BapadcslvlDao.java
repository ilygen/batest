package tw.gov.bli.ba.dao;

import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;

import tw.gov.bli.ba.domain.Bapadcslvl;
import tw.gov.bli.ba.maint.cases.CheckMarkMaintCase;

/**
 * DAO for 決行層級參數檔 (<code>BAPADCSLVL</code>)
 * 
 * @author swim
 */
public interface BapadcslvlDao {
    /**
     * 依傳入的條件取得 決行層級參數檔 (<code>BAPADCSLVL</code>) 的資料<br>
     * 
     * @param payCode 給付別
     * @param payKind 給付種類
     * @param rechklvl 決行層級代碼
     * 
     * @return <code>List<Bapadcslvl></code> 物件
     */
    public List<Bapadcslvl> selectData(String payCode,String payKind,String rechklvl);
    
    /**
     * 新增資料到 決行層級參數檔 (<code>BAPADCSLVL</code>)<br>
     * 
     * @param data <code>Bapadcslvl</code> 物件
     */
    public BigDecimal insertData(Bapadcslvl data);
    
    /**
     * 更新資料到 決行層級參數檔 (<code>BAPADCSLVL</code>)<br>
     * 
     * @param data <code>Bapadcslvl</code> 物件
     */
    public int updateData(Bapadcslvl data);
    
    /**
     * 依傳入的條件取得 決行層級參數檔 (<code>BAPADCSLVL</code>) 的資料<br>
     * 
     * @param payCode 給付別
     * @param payKind 給付種類
     * @param rechklvl 決行層級代碼
     * 
     * @return <code>Bapadcslvl</code> 物件
     */
    public Bapadcslvl selectSingleForUpdateData(String payCode,String payKind,String rechklvl);
    
    /**
     * 刪除 決行層級參數檔 (<code>BAPADCSLVL</code>) 的資料<br>
     * 
     * @param <code>Bapadcslvl</code> 物件
     */
    public void deleteData(Bapadcslvl detailData);
}
