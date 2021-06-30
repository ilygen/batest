package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Bachkcontrl;

/**
 * DAO for 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>)
 * 
 * @author Goston
 */
public interface BachkcontrlDao {
    /**
     * 新增資料到 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>) <br>
     * 
     * @param data <code>Bachkcontrl</code> 物件
     */
    public void insertData(Bachkcontrl data);

    /**
     * 新增資料到 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>) <br>
     * 
     * @param dataList
     */
    public void insertData(final List<Bachkcontrl> dataList);

    /**
     * 刪除 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>) 的資料
     * 
     * @param apNo 受理編號
     */
    public void deleteData(String apNo);
    
    /**
     * 依傳入條件取得 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>) 的資料
     * 
     * @param apNo 受理編號
     * @param issuYm 核定年月
     */
    public List<Bachkcontrl> selectDataBy(String apNo, String issuYm);
    
    /**
     * 刪除 編審註記程度調整管制檔 (<code>BACHKCONTRL</code>) 的資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     */
    public void deleteDataBySeqNo(String apNo, String seqNo);
}
