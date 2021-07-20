package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;

import tw.gov.bli.ba.domain.Baappbase;
import tw.gov.bli.ba.domain.Bbcmf09;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 現金給付分案尾碼原則檔 (<code>BBCMF09</code>)
 * 
 * @author Rickychi
 */
public interface Bbcmf09Dao {
    /**
     * 取得 現金給付分案尾碼原則檔(<code>BBCMF09</code>) 承辦人分機號碼資料
     * 
     * @param apNo 受理編號
     * @return <code>data2</code> String物件
     */
    public String selectData2By(String apNo);

    /**
     * 取得 現金給付分案尾碼原則檔(<code>BBCMF09</code>) 員工編號
     * 
     * @param apNo 受理編號
     * @return <code>prpNo</code> String物件
     */
    public String selectPrpNoBy(String apNo);
    
    /**
     * 取得 現金給付分案尾碼原則檔(<code>BBCMF09</code>) 詳細資料
     * 
     * @param apNo 受理編號
     * @return <code>Bbcmf09</code> 物件
     */
    public Bbcmf09 selectComTelForMonthlyRpt05By(String apNo);
}
