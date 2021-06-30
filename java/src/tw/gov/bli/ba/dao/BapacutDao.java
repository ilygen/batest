package tw.gov.bli.ba.dao;

import tw.gov.bli.ba.domain.Bapacut;
import java.util.List;
import java.math.BigDecimal;


public interface BapacutDao {
	/**
     * 依傳入的條件取得 扣減參數條件參數檔 (<code>BAPACUT</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @return <code>Bapacut</code> 物件
     */
    public List<Bapacut> selectData(String data);
    /**
     * 新增資料到 扣減參數條件參數檔 (<code>BAPACUT</code>)<br>
     * 
     * @param data <code>Bapacut</code> 物件
     */
    public void insertData(Bapacut data);
    /**
     * 刪除 扣減參數條件參數檔 (<code>BAPACUT</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     */
    public void deleteData(String payCode);
    
}
