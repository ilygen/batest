package tw.gov.bli.ba.dao;

import tw.gov.bli.ba.domain.Bapainspect;

/**
 * DAO for 先抽對象條件參數檔 (<code>BAPAINSPECT</code>)
 * 
 * @author Goston
 */
public interface BapainspectDao {
    /**
     * 依傳入的條件取得 先抽對象條件參數檔 (<code>BAPAINSPECT</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @return <code>Bapainspect</code> 物件
     */
    public Bapainspect selectDataBy(String payCode);

    /**
     * 新增資料到 先抽對象條件參數檔 (<code>BAPAINSPECT</code>)<br>
     * 
     * @param data <code>Bapainspect</code> 物件
     */
    public void insertData(Bapainspect data);

    /**
     * 刪除 先抽對象條件參數檔 (<code>BAPAINSPECT</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     */
    public void deleteData(String payCode);
}
