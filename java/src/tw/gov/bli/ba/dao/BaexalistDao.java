package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import tw.gov.bli.ba.domain.Baexalist;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAO for 審核決行清單紀錄檔 (<code>BAEXALIST</code>)
 * 
 * @author Rickychi
 */
public interface BaexalistDao {
    /**
     * 依傳入條件取得 審核決行清單紀錄檔(<code>BAEXALIST</code>) 資料清單
     * 
     * @param rptDate 列印日期
     * @param pageNo 頁次
     * @param payTyp 給付別
     * @return 內含 <code>Baexalist</code> 物件的 List
     */
    public List<Baexalist> selectReviewDataBy(String rptDate, BigDecimal pageNo, String payTyp);

    // /**
    // * 依入條件取得 審核決行清單紀錄檔(<code>BAAPPBASE</code>) 給付決行資料
    // *
    // * @param rptDate 列印日期
    // * @param pageNo 頁次
    // * @param chkMan 審核人員
    // * @param empId 員工編號
    // * @return 內含 <code>Baexalist</code> 物件的 List
    // */
    // public List<Baexalist> selectDecisionDataBy(String rptDate, BigDecimal pageNo, String chkMan, String empId);

    /**
     * 紀錄 Baexalist (多筆)
     * 
     * @param BaexalistList 內含 <code>Baexalist</code> 物件的 List
     */
    public void insertData(final List<Baexalist> BaexalistList);

    /**
     * 依傳入的條件計算 (<code>BigDecimal</code>)
     * 
     * @param rptTyp
     * @param rptDate
     * @return
     */
    public String selectMaxVersion(String rptTyp, String rptDate);

    /**
     * 依入條件取得 審核決行清單紀錄檔(<code>BAEXALIST</code>) 給付決行資料
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @return 內含 <code>Baexalist</code> 物件的 List
     */
    public List<Baexalist> selectMonthlyRpt02DataBy(String payTyp, String issuYm, String mtestMk);
    
    /**
     * 依入條件取得 審核決行清單紀錄檔(<code>BAEXALIST</code>) 月處理異動清單
     * 
     * @param payTyp 給付別
     * @param issuYm 核定年月
     * @param mtestMk 月核定別
     * @param chkDate 核定日期
     * @return 內含 <code>Baexalist</code> 物件的 List
     */
    public List<Baexalist> selectMonthlyBatchRptDataBy(String payTyp, String issuYm, String mtestMk, String rptDate);

    /**
     * 依入條件取得 審核決行清單紀錄檔(<code>BAEXALIST</code>) for 審核給付清單
     * 
     * @param chkMan 審核人員
     * @param chkDate 審核日期
     * @param payCode 給付別
     * @return 內含 <code>Baexalist</code> 物件的 List
     */
    public List<Baexalist> selectOldAgeReviewRpt02DataBy(String payTyp, String chkMan, String chkDate);
}
