package tw.gov.bli.ba.dao;

import java.util.List;
import java.math.BigDecimal;

import tw.gov.bli.ba.bj.cases.BatchRecTemp;
import tw.gov.bli.ba.domain.Bagivedtl;
import tw.gov.bli.ba.framework.domain.UserBean;

/**
 * DAO for 給付入帳媒體明細錄檔 (<code>BAGIVEDTL</code>)
 * 
 * @author swim
 */
public interface BagivedtlDao {
    
    /**
     * 新增資料到 給付入帳媒體明細錄檔(<code>BAGIVEDTL</code>)
     * 
     * @param data <code>BatchRecTemp</code> 物件
     */
    public void insertData(final List<BatchRecTemp> caseList);
    
    public void insertStringData(final String batchRecId, final String mfileName, final String mfileDate, final String[] stringList);
    
    /**
     * 依傳入條件取得 給付入帳媒體明細錄檔(<code>BAGIVEDTL</code>) 資料 FOR 給付媒體回押註記
     * 
     * @param baBatchRecId 
     * @return <code>List<Bagivedtl></code> 物件
     */
    public List<Bagivedtl> selectUpdatePaidMarkList3By(String baBatchRecId);
    
    /**
     * 依傳入條件取得 給付入帳媒體明細錄檔(<code>BAGIVEDTL</code>) 資料 FOR 給付媒體回押註記
     * 
     * @param baBatchRecId 
     * @return <code>Bagivedtl</code> 物件
     */
    public Bagivedtl selectUpdatePaidMark2By(String baBatchRecId,String payCode,String payDate,String taTyp);
    
    /**
     * Call StoreProcedure
     * 
     * @param procTyp 回押註記
     * @param baBatchRecId 
     * @param userData
     * @return procMsg
     */
    public String updatePaidMarkStatus(String procTyp,String payCode, BigDecimal baBatchRecId, UserBean userData);
    
    /**
     * Call StoreProcedure
     * 
     * @param procTyp 回押註記
     * @param baBatchRecId 
     * @param userData
     * @return procMsg
     */
    public String chkPaidMarkStatus(String procTyp, String payCode, BigDecimal baBatchRecId, UserBean userData);
    
    /**
     * 依傳入條件取得 給付別(<code>BAGIVEDTL</code>) 資料 FOR 給付媒體回押註記
     * 
     * @param payDate
     * @param baBatchRecId 
     * @param taTyp
     * 
     * @return <code>List<Bagivedtl></code> 物件
     */
    public String selectUpdatePaidMarkBJPayCodeBy(String payDate, BigDecimal baBatchRecId,String taTyp);
    
}
