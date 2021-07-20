package tw.gov.bli.ba.dao;

import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.domain.Banotify;
import tw.gov.bli.ba.maint.cases.AdviceMaintCase;

/**
 * DAO for 核定通知書格式檔 (<code>BANOTIFY</code>)
 * 
 * @author swim
 */
public interface BanotifyDao {
    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @param dataSeqNo 資料序號
     * @return <code>List<Banotify></code> 物件
     */
    public List<Banotify> selectDataBy(String payCode, String authTyp, String dataTyp, String dataSeqNo);

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @param dataSeqNo 資料序號
     * @return <code>List<AdviceMaintCase></code> 物件
     */
    public List<AdviceMaintCase> selectData(String payCode, String authTyp, String dataTyp, String dataSeqNo);

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @param dataSeqNo 資料序號
     * @return <code>AdviceMaintCase</code> 物件
     */
    public AdviceMaintCase selectDataContExplain(String payCode, String authTyp, String dataTyp, String dataSeqNo);

    /**
     * 新增資料到 核定通知書格式檔 (<code>BANOTIFY</code>)<br>
     * 
     * @param data <code>AdviceMaintCase</code> 物件
     */
    public BigDecimal insertData(AdviceMaintCase data);

    /**
     * 更新資料到 核定通知書格式檔 (<code>BANOTIFY</code>)<br>
     * 
     * @param data <code>AdviceMaintCase</code> 物件
     */
    public int updateData(AdviceMaintCase data);

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料<br>
     * 
     * @param payCode 給付種類
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return <code>AdviceMaintCase</code> 物件
     */
    public List<AdviceMaintCase> selectSingleForUpdateData(String payCode, String authTyp, String dataTyp);

    /**
     * 刪除 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料<br>
     * 
     * @param AdviceMaintCase
     */
    public void deleteData(AdviceMaintCase data);

    /**
     * 更正作業報表 給付函核定通知書
     */
    public List<Banotify> selectMonthlyRpt05Report(String payCode, String authTyp);

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料 for 勞保老年年金給付受理編審清單
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return
     */
    public List<Banotify> getOldAgeReviewRpt01NotifyListBy(String payCode, String authTyp, String dataTyp);

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料 for 給付審核/決行
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return
     */
    public List<Banotify> getNotifyListForReviewBy(String payCode, String authTyp, String dataTyp);

    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 核定通知書格式資料
     * 
     * @param apNo 受理編號
     * @return
     */
    public List<String> selectNotifyFormBy(String apNo);
    
    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料 for 勞保失能年金給付受理編審清單
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return
     */
    public List<Banotify> getDisableReviewRpt01NotifyListBy(String payCode, String authTyp, String dataTyp);
    
    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return
     */
    public List<Banotify> getSurvivorReviewRpt01NotifyListBy(String payCode, String authTyp, String dataTyp);
    
    /**
     * 依傳入的條件取得 核定通知書格式檔 (<code>BANOTIFY</code>) 的資料 for 複檢費用
     * 
     * @param payCode 給付別
     * @param authTyp 核定格式
     * @param dataTyp 資料區分
     * @return
     */
    public List<Banotify> getReviewFeeReceiptwRpt01NotifyListBy(String payCode, String authTyp, String dataTyp);
}
