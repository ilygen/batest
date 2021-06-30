package tw.gov.bli.ba.dao;

import java.util.HashMap;
import java.util.List;

import tw.gov.bli.ba.domain.Cvldtl;

/**
 * DAO for 戶政全戶檔 (<code>CVLDTL</code>)
 * 
 * @author Rickychi
 */
public interface CvldtlDao {
    /**
     * 依傳入條件取得 戶政全戶檔(<code>CVLDTL</code>) 資料清單
     * 
     * @param idn 事故者身分證號
     * @param ebDate 出生日期
     * @return 內含 <code>Cvldtl</code> 物件的 List
     */
    public List<Cvldtl> selectDataBy(String idn, String ebDate);
    
    /**
     * 依傳入條件取得 戶政全戶檔(<code>CVLDTL</code>) 資料清單
     * 
     * @param Evtids 
     * @return String
     */
    public Cvldtl selectHaddrBy(String evtIds);
    
    /**
     * Call StoreProcedure
     * 
     * @param NPIDS = Evtids
     * @param addrdiff
     * @return 
     */
    public Cvldtl selectInfoBy(String evtids, String addrdiff);
    /**
     * 依傳入條件取得 郵遞區號
     * 
     * @param Evtids 
     * @return String
     */
    public String selectZipCodeData(String zipCode);
    
    //Added By LouisChange 20200311 begin
    /**
     * 依傳入條件取得 戶政全戶檔(<code>CVLDTL</code>) 資料清單
     * 
     * @param idn 事故者身分證號
     * @param ebDate 出生日期
     * @return 內含 <code>Cvldtl</code> 物件的 List
     */
    public List<Cvldtl> selectRmpNameBy(String idn, String ebDate);
    //Added By LouisChange 20200311 end
}
