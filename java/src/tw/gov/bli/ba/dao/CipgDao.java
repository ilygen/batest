package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Cipg;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 被保險人投保薪資檔 (<code>CIPG</code>)
 * 
 * @author Evelyn Hsu
 */

public interface CipgDao {

    /**
     * 依傳入的條件取得 被保險人投保薪資檔(<code>CIPG</code>) for 勞保失能年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分証號
     * @return
     */
    public List<Cipg> selectDisableReviewRpt01CipgData(String apNo, String seqNo, String evtIdnNo, String evTyp, String inTyp, String prType, String appMonth);

    /**
     * 依傳入的條件取得 被保險人投保薪資檔(<code>CIPG</code>) for 勞保遺屬年金給付受理編審清單
     * 
     * @param evtIdnNo 事故者身分証號
     * @return
     */
    public List<Cipg> selectSurvivorReviewRpt01CipgData(String apNo, String seqNo, String evtIdnNo, String evTyp, String inTyp, String prType, String appMonth);

    /**
     * 依傳入條件取得 被保險人投保薪資檔(<code>CIPG</code>) 最高60個月平均薪資明細 for 給付查詢
     * 
     * @param idn 身分證號
     * @param avgTyp 投保薪資類別
     * @return
     */
    public List<Cipg> selectPaymentQuerySixtyMonAvgAmtDataBy(String apNo, String seqNo, String idn, String avgTyp,String appMonth);
    
    /**
     * 依傳入條件取得 被保險人投保薪資檔(<code>CIPG</code>) 最高60個月平均薪資明細 for 給付查詢 (老年年金)
     * 
     * @param idn 身分證號
     * @param avgTyp 投保薪資類別
     * @return
     */
    public List<Cipg> selectPaymentQuerySixtyMonAvgAmtDataForOldAgeBy(String apNo, String seqNo, String idn,String appMonth);
    
    /**
     * 依傳入條件取得 被保險人投保薪資檔(<code>CIPG</code>) 最高60個月平均薪資明細 for 勞保老年年金給付受理編審清單
     * 
     * @param idn 身分證號
     * @return
     */
    public List<Cipg> selectOldAgeReviewRpt01SixtyMonthAvgAmtList(String apNo, String seqNo,String idn,String appMonth);

    /**
     * 依傳入條件取得 實際均薪月數(<code>CIPG</code>) For 老年年金
     * 
     * @param idn 身分證號
     * @return
     */
    public String getRealAvgMonForOldAge(String apNo, String seqNo, String idn);
    
    /**
     * 依傳入條件取得 實際均薪月數(<code>CIPG</code>) For 失能遺屬
     * 
     * @param idn 身分證號
     * @return
     */
    public String getRealAvgMonBy(String apNo, String seqNo, String idn, String avgTyp);
    
    /**
     * 依傳入條件取得 實際均薪月數(<code>CIPG</code>) For 失能年金Rpt01
     * 
     * @param idn 身分證號
     * @return
     */
    public String getRealAvgMonForDisableReviewRpt01By(String apNo, String seqNo, String idn, String inTyp, String avgTyp);
    
    /**
     * 依傳入條件取得 實際均薪月數(<code>CIPG</code>) For 遺屬年金Rpt01
     * 
     * @param idn 身分證號
     * @return
     */
    public String getRealAvgMonForSurvivorReviewRpt01By(String apNo, String seqNo, String idn, String inTyp, String avgTyp);
    
    /**
     * 依傳入條件取得 平均薪資(<code>CIPG</code>) for 失能、遺屬年金
     * 
     * @param apNo 受理編號
     * @param seqNo 受理序號
     * @param idn 身分證號
     * @return
     */
    public BigDecimal getAvgWageForKS(String apNo, String seqNo, String idn);    
}
