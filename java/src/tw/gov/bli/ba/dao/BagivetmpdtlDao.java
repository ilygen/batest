package tw.gov.bli.ba.dao;

import java.util.List;
import tw.gov.bli.ba.domain.Bagivetmpdtl;

/**
 * DAO for 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>)
 * 
 * @author Goston
 */
public interface BagivetmpdtlDao {

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 所輸入的 給付別 及 核定年月 所含有之 出帳類別 List
     */
    public List<String> getMonthlyRpt07TaTyp2List(String payCode, String issuYm, String chkDate);
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 所輸入的 給付別 及 核定年月 所含有之 出帳類別 List
     */
    public List<String> getMonthlyRptK07TaTyp2List(String payCode, String issuYm, String chkDate, String paySeqNo);
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @return 所輸入的 給付別 及 核定年月 所含有之 出帳類別 List
     */
    public List<String> getMonthlyRptS07TaTyp2List(String payCode, String issuYm, String chkDate);    

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    public List<Bagivetmpdtl> getMonthlyRpt07HeaderDataBy(String payCode, String issuYm, String taTyp2, String chkDate);
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    public List<Bagivetmpdtl> getMonthlyRpt07mFileNameBy(String payCode, String issuYm, String taTyp2, String chkDate);
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    public List<Bagivetmpdtl> getMonthlyRptK07HeaderDataBy(String payCode, String issuYm, String taTyp2, String chkDate,String paySeqNo);
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    public List<Bagivetmpdtl> getMonthlyRptS07HeaderDataBy(String payCode, String issuYm, String taTyp2, String chkDate);    

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    public List<Bagivetmpdtl> getMonthlyRpt07MiddleDataBy(String payCode, String issuYm, String taTyp2, String chkDate);
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    public List<Bagivetmpdtl> getMonthlyRptK07MiddleDataBy(String payCode, String issuYm, String taTyp2, String chkDate,String paySeqNo);
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    public List<Bagivetmpdtl> getMonthlyRptS07MiddleDataBy(String payCode, String issuYm, String taTyp2, String chkDate);    

    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（老年）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    public List<Bagivetmpdtl> getMonthlyRpt07FooterDataBy(String payCode, String issuYm, String taTyp2, String chkDate);
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（失能）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    public List<Bagivetmpdtl> getMonthlyRptK07FooterDataBy(String payCode, String issuYm, String taTyp2, String chkDate, String paySeqNo);
    
    /**
     * 依傳入條件取得 給付媒體明細錄檔 (<code>BAGIVETMPDTL</code>) for 月核定給付撥款總額表（遺屬）
     * 
     * @param payCode 給付別
     * @param issuYm 核定年月
     * @param taTyp2 出帳類別
     * @return
     */
    public List<Bagivetmpdtl> getMonthlyRptS07FooterDataBy(String payCode, String issuYm, String taTyp2, String chkDate);    
    /**
	 * 依傳入條件取得該核定日期、核定年月、給付別的對應下載清單
	 * 
	 * @param payCode 給付別
	 * @param issuYm  核定年月
	 * @param chkDate 核定日期
	 * @param paySeqNo 欄位值(皆預設為1第一次出媒體)
	 * @return 
	 */
    public List<Bagivetmpdtl> getMedaiFileOnlineDownloadDataBy(String payCode, String issuYm, String chkDate, String paySeqNo);
}
