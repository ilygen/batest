package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Barxf;

public interface BarxfDao {

    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BARXF</code>) 資料
     * 
     * @param APNO 受理編號-撥付
     * @return RXFAPNO 受理編號-應收
     */
    public List<Barxf> selectRxfApNoByApNo(String apNo);

    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BARXF</code>) 資料
     * 
     * @param RXFAPNO 受理編號-應收
     * @param APNO 受理編號-撥付
     * @param SEQNO 序號-撥付
     * @return
     */
    public Barxf selectDataBy(String rxfApNo, String apNo, String seqNo);

    /**
     * 新增 給付主檔(<code>BARXF</code>) 資料
     * 
     * @param Barxf 另案扣減工作檔
     * @return
     */
    public void insertData(List<Barxf> barxfList);

    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BARXF</code>) 另案扣減資料 - 一次給付 for 勞保老年年金給付受理編審清單
     * 
     * @param benIdnNo 受款人身分證號 - 撥付
     * @param benBrDate 受款人出生日期 - 撥付
     * @return
     */
    public List<Barxf> selectOldAgeReviewRpt01DeductOnceListBy(String benIdnNo, String benBrDate);

    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BARXF</code>) 另案扣減資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param APNO 受理編號-撥付
     * @param ISSUYM 核定年月
     * @param BENIDNNO 受益人身分證號
     * @return
     */
    public List<Barxf> selectSurvivorReviewRpt01DeductListBy(String benIdnNo, String benBrDate);

    /**
     * 依傳入條件取得 另案扣減工作檔(<code>BARXF</code>) 資料 for 勞保失能年金給付受理編審清單
     * 
     * @param APNO 受理編號-撥付
     * @param ISSUYM 核定年月
     * @param BENIDNNO 受益人身分證號
     * @return
     */
    public List<Barxf> selectDisableReviewRpt01DeductListBy(String benIdnNo, String benBrDate);

}
