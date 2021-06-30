package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Bamarginamtnotify;

/**
 * DAO for 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>)
 * 
 * @author KIYOMI
 */
public interface BamarginamtnotifyDao {

    /**
     * 新增 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>) 資料
     * 
     * @param BAMARGINAMTNOTIFY 老年差額金通知發文紀錄檔
     * @return
     */
    public void insertDataForMonthlyRpt31(final List<Bamarginamtnotify> bamarginamtnotifyList);

    /**
     * 更新 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>) 資料 - 第二次通知函（催辦）
     * 
     * @param
     */
    public void updateDataForMonthlyRpt31U(final List<Bamarginamtnotify> bamarginamtnotifyList);

    /**
     * 更新 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>) 資料 - 第三次通知函（延不補正）
     * 
     * @param
     */
    public void updateDataForMonthlyRpt31P(final List<Bamarginamtnotify> bamarginamtnotifyList);

    /**
     * 查詢 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>) 資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<Bamarginamtnotify> selectDataForMonthlyRpt13(String apNo);

    /**
     * 依傳入條件取得 老年差額金通知發文紀錄檔 (<code>BAMARGINAMTNOTIFY</code>) 發文日期 for report replace 老年差額金通知
     * 
     * @param apNo 受理編號
     * @return
     */
    public HashMap<String, Object> selectRptReplaceForIssueDateL023(String apNo);
}
