package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Bacpirec;
import tw.gov.bli.ba.maint.cases.CpiRecMaintCase;

/**
 * Dao for 物價指數調整明細檔 (<code>BACPIREC</code>)
 *
 * @author Kiyomi
 */
public interface BacpirecDao {
    /**
     * 依傳入的條件取得物價指數調整紀錄檔 (<code>BACPIREC</code>) 的資料
     *
     * @param adjYear 指數年度
     * @return <code>List<CpiRecMaintCase></code> 物件
     */
    public List<Bacpirec> selectData(String issuYear);

    /**
     * 依傳入的條件取得物價指數調整明細檔 (<code>BACPIREC</code>) 的資料
     *
     * @param cpiYear 指數年度
     * @return <code>List<CpiDtlMaintCase></code> 物件
     */
    public List<Bacpirec> checkData(String cpiYear);

    /**
     * 新增資料到 物價指數調整明細檔 (<code>BACPIREC</code>)<br>
     *
     * @param data <code>Bacpirec</code> 物件
     */
    public BigDecimal insertData(Bacpirec data);

    /**
     * 更新資料到 物價指數調整明細檔 (<code>BACPIREC</code>)<br>
     *
     * @param data <code>Bacpirec</code> 物件
     */
    public int updateData(Bacpirec data);

    /**
     * 依傳入的條件取得 物價指數調整明細檔 (<code>BACPIREC</code>) 的資料<br>
     *
     * @param cpiYear      指數年度
     * @param cpIndex      物價指數成長率
     * @param reqRpno      報請核定文號
     * @param issuRpno     核定文號
     * @param issuDesc     核定結果
     * @param updUser      員工編號
     * @param updTime      系統日期時間
     *
     * @return <code>Bacpirec</code> 物件
     */
    public List<Bacpirec> selectSingleForUpdateData(String issuYear);

    /**
     * 刪除 物價指數調整紀錄檔 (<code>BACPIADJ</code>) 資料
     *
     * @param adjYear 指數年度
     * @return <code>BigDecimal</code>
     */
    public void deleteData(String issuYear);
    
    /**
     * 依傳入條件取得 物價指數調整紀錄檔 (<code>BACPIREC</code>) 物價指數調整記錄資料 for 給付查詢
     * 
     * @return
     */
    public List<Bacpirec> selectCpipRecForPaymentQuery();

}
