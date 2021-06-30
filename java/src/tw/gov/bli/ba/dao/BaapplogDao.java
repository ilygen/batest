package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.bj.cases.CompPaymentCase;
import tw.gov.bli.ba.domain.Baapplog;

/**
 * DAO for 給付資料更正記錄檔 (<code>BAAPPLOG</code>)
 * 
 * @author Rickychi
 */
public interface BaapplogDao {

    /**
     * 依傳入的條件取得 給付資料更正記錄檔(<code>BAAPPLOG</code>) 的資料
     * 
     * @param payCode 給付別
     * @prarm updateDateBegin 處理時間 (起)
     * @param updateDateEnd 處理時間 (迄)
     * @param apNo 受理編號
     * @param benIdnNo 身分證號
     * @param updUser 更正人員
     * @return
     */
    public List<Baapplog> selectLogListBy(String payCode, String updateDateBegin, String updateDateEnd, String apNo, String benIdnNo, String updUser);

    /**
     * 新增 給付資料更正記錄檔(<code>BAAPPLOG</code>) 資料
     * 
     * @param baapplog 給付資料更正記錄檔
     */
    public void insertData(Baapplog baapplog);

    /**
     * 新增 給付資料更正記錄檔(<code>BAAPPLOG</code>) 資料
     * 
     * @param baapplogList
     */
    public void insertData(final List<Baapplog> baapplogList);
    
    /**
     * 新增 老年、失能年金補正核付作業更正記錄檔(<code>BAAPPLOG</code>) 資料
     * 
     * @param baapplog 給付資料更正記錄檔
     */
    public void insertLogForCompPayment(CompPaymentCase compPaymentCase);
}
