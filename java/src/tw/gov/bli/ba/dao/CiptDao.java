package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Cipt;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 被保險人異動資料檔 (<code>CIPT</code>)
 * 
 * @author Rickychi
 */
public interface CiptDao {
    /**
     * 依傳入條件取得 被保險人異動資料檔(<code>CIPT</code>) 資料清單
     * 
     * @param idn 身分證號
     * @param inTyp 保險別
     * @return 內含 <code>Cipt</code> 物件的 List
     */
    public List<Cipt> selectTxcdDataBy(String apNo,String seqNo, String idn, String inTyp);

    /**
     * 依傳入條件取得 被保險人異動資料檔(<code>CIPT</code>) 承保異動資料 for 勞保老年年金給付受理編審清單
     * 
     * @param idn 身分證號
     * @return
     */
    public List<Cipt> getOldAgeReviewRpt01ChangeListBy(String apNo,String seqNo, String idn);
    
    /**
     * 依傳入的條件取得 被保險人異動資料檔 (<code>CIPT</code>) 的資料<br>
     * 
     * @param inTyp 保險別
     * @param idN 身分證號
     * @param uno 保險證號
     * @return <code>List</code>
     */
    public List<Cipt> selectDataBy(String apNo,String seqNo, String inTyp, String idn, String uno);
    
    /**
     * 依傳入條件取得 被保險人異動資料檔(<code>CIPT</code>) 承保異動資料 for 勞保遺屬年金給付受理編審清單
     * 
     * @param idn 身分證號
     * @return
     */
    public List<Cipt> selectSurvivorReviewRpt01ChangeListBy(String apNo,String seqNo, String idn);
}
