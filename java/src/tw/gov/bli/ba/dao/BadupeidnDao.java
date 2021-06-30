package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Badupeidn;

/**
 * DAO for 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>)
 * 
 * @author Rickychi
 */
public interface BadupeidnDao {
    /**
     * 依傳入條件取得 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 資料
     * 
     * @param apNo 受理編號
     * @param seqNo 序號
     * @param idnNo 身分證號
     * @param brDate 姓名
     * @return
     */
    public List<Badupeidn> selectDataBy(String apNo, String seqNo, String idnNo, String brDate);

    /**
     * 更新資料到 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>)
     * 
     * @param badupeidn <code>Badupeidn</code> 物件
     */
    public int updateData(Badupeidn badupeidn);

    /**
     * 取得 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Badupeidn getOldAgeReviewRpt01DupeIdnDataBy(String apNo);

    /**
     * 取得 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @return
     */
    public Badupeidn selectSurvivorReviewRpt01DupeIdnDataBy(String apNo);

    /**
     * 依傳入條件取得 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 資料 for 失能年金案件資料更正 (身分證重號資料)
     * 
     * @param apNo 受理編號
     * @param idnNo 身分證號
     * @return
     */
    public List<Badupeidn> getDisabledApplicationDataUpdateDupeIdnListBy(String apNo, String idnNo);

    /**
     * 重置 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 失能年金案件資料更正 (身分證重號資料)
     * 
     * @param data
     */
    public void updateDisabledApplicationDataUpdateResetDupeIdnData(Badupeidn data);

    /**
     * 更新 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 失能年金案件資料更正 (身分證重號資料)
     * 
     * @param data
     */
    public void updateDisabledApplicationDataUpdateDupeIdnData(Badupeidn data);

    /**
     * 依傳入條件取得 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 資料 for 遺屬年金案件資料更正 (身分證重號資料)
     * 
     * @param apNo 受理編號
     * @param idnNo 身分證號
     * @return
     */
    public List<Badupeidn> getSurvivorApplicationDataUpdateDupeIdnListBy(String apNo, String idnNo);

    /**
     * 重置 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 遺屬年金案件資料更正 (身分證重號資料)
     * 
     * @param data
     */
    public void updateSurvivorApplicationDataUpdateResetDupeIdnData(Badupeidn data);

    /**
     * 更新 被保險人承保身分證號重號檔 (<code>BADUPEIDN</code>) 的資料 for 遺屬年金案件資料更正 (身分證重號資料)
     * 
     * @param data
     */
    public void updateSurvivorApplicationDataUpdateDupeIdnData(Badupeidn data);

    /**
     * 依傳入條件取得 被保險人承保身分證號重號檔(<code>BADUPEIDN</code>) 資料 for 老年差額金通知
     * 
     * @param apNo 受理編號
     * @param seqNo
     * @param idnNo
     * @return
     */
    public String selectRptReplaceForNewIdnNoL018(String apNo, String seqNo, String idnNo);

}
