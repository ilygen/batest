package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.List;
import tw.gov.bli.ba.domain.Cipb;
import tw.gov.bli.ba.domain.Cipg;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 被保險人基本資料檔 (<code>CIPB</code>)
 * 
 * @author KIYOMI
 */

public interface CipbDao {

    /**
     * Call StoreProcedure
     * 
     * @param famIdnNo 眷屬身分證號
     * @param evtJobDate 事故日期
     * @return
     */
    public void doGetCipb(String apNo, String seqNo, String appDate, String famIdnNo, String evtJobDate);

    /**
     * 依傳入條件取得 一次平均薪資 (<code>CIPB</code>)
     * 
     * @param apNo 受理編號
     * @param seqNo
     * @param idnNo
     * @return
     */
    public BigDecimal getAvgWg(String apNo, String seqNo, String idnNo, String type);

    /**
     * 依傳入條件取得 被保險人基本資料檔(<code>CIPB</code>) 一次平均薪資 for 勞保老年年金給付受理編審清單
     * 
     * @param apNo 受理編號
     * @param seqNo
     * @param idn 身分證號
     * @return
     */
    public List<Cipb> selectOldAgeReviewRpt01OnceAvgAmt(String apNo, String seqNo, String idn);

    /**
     * 依傳入條件取得 被保險人基本資料檔(<code>CIPB</code>) for 年金統計查詢
     * 
     * @return
     */
    public List<Cipb> qryCipbFmkList();
}
