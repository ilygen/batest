package tw.gov.bli.ba.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import tw.gov.bli.ba.dao.CiptDao;
import tw.gov.bli.ba.domain.Cipt;
import tw.gov.bli.common.annotation.DaoFieldList;
import tw.gov.bli.common.annotation.DaoTable;

/**
 * DAO for 被保險人異動資料檔 (<code>CIPT</code>)
 * 
 * @author Rickychi
 */
@DaoTable("CIPT")
public class CiptDaoImpl extends SqlMapClientDaoSupport implements CiptDao {
    /**
     * 依傳入條件取得 被保險人異動資料檔(<code>CIPT</code>) 資料清單
     * 
     * @param idn 身分證號
     * @param inTyp 保險別
     * @return 內含 <code>Cipt</code> 物件的 List
     */
    @DaoFieldList("APNO,SEQNO,IDN,INTYP")
    public List<Cipt> selectTxcdDataBy(String apNo,String seqNo, String idn, String inTyp) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotBlank(apNo)) {
            map.put("apNo", apNo);
        }
        if (StringUtils.isNotBlank(seqNo)) {
            map.put("seqNo", seqNo);
        }
        if (StringUtils.isNotBlank(idn)) {
            map.put("idn", idn);
        }
        if (StringUtils.isNotBlank(inTyp)) {
            map.put("inTyp", inTyp);
        }
        return getSqlMapClientTemplate().queryForList("CIPT.selectTxcdDataBy", map);
    }

    /**
     * 依傳入條件取得 被保險人異動資料檔(<code>CIPT</code>) 承保異動資料 for 勞保老年年金給付受理編審清單
     * 
     * @param idn 身分證號
     * @return
     */
    @DaoFieldList("APNO,SEQNO,IDN")
    public List<Cipt> getOldAgeReviewRpt01ChangeListBy(String apNo,String seqNo, String idn) {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("apNo", apNo);
        map.put("seqNo", seqNo);
        map.put("idn", idn);

        return getSqlMapClientTemplate().queryForList("CIPT.getOldAgeReviewRpt01ChangeListBy", map);
    }
    
    /**
    * 依傳入的條件取得 被保險人異動資料檔 (<code>CIPT</code>) 的資料<br>
    * 
    * @param inTyp 保險別
    * @param idN 身分證號
    * @param uno 保險證號
    * @return <code>List</code>
    */
    @DaoFieldList("APNO,SEQNO,INTYP,IDN,UNO")
   public List<Cipt> selectDataBy(String apNo, String seqNo, String inTyp, String idn, String uno) {
       HashMap<String, String> map = new HashMap<String, String>();
       map.put("apNo", apNo);
       map.put("seqNo", seqNo);
       map.put("inTyp", inTyp);
       map.put("idn", idn);
       if (StringUtils.isNotBlank(uno))
           map.put("uno", uno);
       return getSqlMapClientTemplate().queryForList("CIPT.selectDataBy", map);
   }
   
   /**
    * 依傳入條件取得 被保險人異動資料檔(<code>CIPT</code>) 承保異動資料 for 勞保遺屬年金給付受理編審清單
    * 
    * @param idn 身分證號
    * @return
    */
   @DaoFieldList("APNO,SEQNO,IDN")
   public List<Cipt> selectSurvivorReviewRpt01ChangeListBy(String apNo,String seqNo, String idn) {
       HashMap<String, String> map = new HashMap<String, String>();
       map.put("apNo", apNo);
       map.put("seqNo", seqNo);
       map.put("idn", idn);
       return getSqlMapClientTemplate().queryForList("CIPT.selectSurvivorReviewRpt01ChangeListBy", map);
   }
}
